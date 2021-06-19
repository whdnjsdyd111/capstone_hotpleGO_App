package com.example.hotplego.ui.user.home;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.hotplego.GpsTracker;
import com.example.hotplego.PostRun;
import com.example.hotplego.R;
import com.example.hotplego.UserSharedPreferences;
import com.example.hotplego.databinding.LocalHotplaceBinding;
import com.example.hotplego.domain.HotpleVO;
import com.example.hotplego.ui.user.MainActivity;
import com.example.hotplego.ui.user.common.MainActivityLogin;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class HomeFragment extends Fragment {

    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private LocalHotplaceBinding binding;
    private GpsTracker gpsTracker;
    Fragment hotples;
    Fragment courses;
    Fragment mbti;
    private final int MBTI = 1;
    private final int COURSE = 3;
    private final int HOTPLE = 2;
    private int selected = 2;

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = LocalHotplaceBinding.inflate(inflater, container, false);

        firebaseAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity().getApplicationContext(), gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity().getApplicationContext());

        Toolbar myToolbar = binding.toolbar;
        ((MainActivity) this.getActivity()).setSupportActionBar(myToolbar);
        ((MainActivity) this.getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((MainActivity) this.getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(!checkLocationServicesStatus()) {
            showDialogForLocationServiceSetting();
        }

        UserSharedPreferences.getInstance().login(this.getActivity());

        if (UserSharedPreferences.user == null) binding.buttonNotice.setVisibility(View.VISIBLE);
        else binding.bnLogout.setVisibility(View.VISIBLE);

        binding.mbti.setOnClickListener(v -> fragmentView(MBTI));
        binding.hotplace.setOnClickListener(v -> fragmentView(HOTPLE));
        binding.course.setOnClickListener(v -> fragmentView(COURSE));

        binding.buttonNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(getContext(), MainActivityLogin.class);
                    startActivity(intent);
            }
        });

        binding.bnLogout.setOnClickListener(v -> {
            String code[] = UserSharedPreferences.user.getUCode().split("/");
            if (code.length == 3) {
                if (code[2].equals("KA")) {
                    UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {

                        @Override
                        public void onNotSignedUp() {
                            super.onNotSignedUp();
                            Toast.makeText(getContext(), "로그인한 상태가 아닙니다.", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCompleteLogout() {
                            Toast.makeText(getContext(),"로그아웃하였습니다.",Toast.LENGTH_SHORT).show();
                        }
                    });
                } else if (code[2].equals("GO")) {
                    signOut();
                }
            }
            UserSharedPreferences.getInstance().logout(getActivity());
            if (UserSharedPreferences.user == null) {
                binding.bnLogout.setVisibility(View.GONE);
                binding.buttonNotice.setVisibility(View.VISIBLE);
            }
        });

        binding.hotpleSearch.setOnKeyListener((v, code, e) -> {
            if (code == KeyEvent.KEYCODE_ENTER && e.getAction() == KeyEvent.ACTION_UP) {
                if (binding.hotpleSearch.getText().toString().isEmpty())
                    Toast.makeText(getContext(), "키워드를 입력해주십시오.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), SearchActivity.class);
                intent.putExtra("keyword", binding.hotpleSearch.getText().toString());
                startActivity(intent);
            }
            return true;
        });

        gpsTracker = new GpsTracker(getContext());

        double latitude = gpsTracker.getLatitude();
        double longitude = gpsTracker.getLongitude();

        Log.i("let", "" + latitude);
        Log.i("lng", "" + longitude);
        Log.i("주소", getCurrentAddress(latitude, longitude));



        return binding.getRoot();
    }

    private void initView() {
        PostRun postRun = new PostRun("around", getActivity(), PostRun.DATA);
        postRun.setRunUI(() -> {
            try {
                Gson gson = new Gson();
                List<HotpleVO> filteredHotple = gson.fromJson(postRun.obj.getString("hotples"), new TypeToken<List<HotpleVO>>() {}.getType());
                Map<String, List<HotpleVO>> filteredCourse = gson.fromJson(postRun.obj.getString("courses"), new TypeToken<Map<String, List<HotpleVO>>>() {}.getType());

                Log.i("코스들", filteredCourse.toString());
                hotples = new HotpleRecoFragment(filteredHotple);
                fragmentView(selected);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        if (UserSharedPreferences.user != null)
            postRun.addData("uCode", UserSharedPreferences.user.getUCode()).addData("mbti", UserSharedPreferences.user.getMbti());

        postRun.start();
    }

    private void signOut() {
        firebaseAuth.signOut();
        mGoogleSignInClient.signOut().addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        Toast.makeText(getActivity().getApplicationContext(),"로그아웃 성공",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Activity.LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {
                        Log.d("@@@", "onActivityResult : GPS 활성화 되어있음");
                    }
                }
                break;
        }
    }

    // GPS 활성화를 위한 메소드
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    public String getCurrentAddress( double latitude, double longitude) {
        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(getContext(), "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(getContext(), "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";

        }

        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(getContext(), "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        }

        Address address = addresses.get(0);
        return address.getAdminArea() + "  " + address.getLocality() + "  " + address.getThoroughfare();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (UserSharedPreferences.user != null) {
            binding.bnLogout.setVisibility(View.VISIBLE);
            binding.buttonNotice.setVisibility(View.GONE);
        } else {
            binding.bnLogout.setVisibility(View.GONE);
            binding.buttonNotice.setVisibility(View.VISIBLE);
        }
        initView();
    }

    private void fragmentView(int fragment) {

        selected = fragment;

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        switch (fragment) {
            case 1:
                if (mbti != null) {
                    transaction.replace(R.id.fragment_container, mbti);
                    transaction.commit();
                }
                break;

            case 2:
                if (hotples != null) {
                    transaction.replace(R.id.fragment_container, hotples);
                    transaction.commit();
                }
                break;

            case 3:
                if (courses != null) {
                    transaction.replace(R.id.fragment_container, courses);
                    transaction.commit();
                }
        }
    }
}