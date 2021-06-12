package com.example.hotplego.ui.local;

import android.os.Bundle;
import android.view.View;

import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.example.hotplego.GpsTracker;
import com.example.hotplego.R;

public class LocalHotpleActivity extends AppCompatActivity {

    Toolbar myToolbar;
    private final int Fragmentlocalw =1;
    private final int Fragmentlocalm =2;
    private final int Fragmentlocalt =3;

    private GpsTracker gpsTracker;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_hotplace);

        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentView(Fragmentlocalw);
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentView(Fragmentlocalm);
            }
        });

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentView(Fragmentlocalt);
            }
        });

//        if(!checkLocationServicesStatus()) {
//            showDialogForLocationServiceSetting();
//        } else {
//            checkRunTimePermission();
//        }
//        final TextView view_address = (TextView)findViewById(R.id.my_location);


//        ImageButton LocationButton = (ImageButton) findViewById(R.id.LocationButton);
//        LocationButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                gpsTracker = new GpsTracker(LocalHotpleActivity.this);
//
//                double latitude = gpsTracker.getLatitude();
//                double longitude = gpsTracker.getLongitude();
//
//                String address = getCurrentAddress(latitude, longitude);
//                view_address.setText(address);
//
//                Toast.makeText(LocalHotpleActivity.this, "현재위치 \n위도 " + latitude + "\n경도 " + longitude, Toast.LENGTH_LONG).show();
//            }
//        });

    }

    private void FragmentView(int fragment) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (fragment) {
            case 1:
                Fragment_Hotple_w fragmentlocalw = new Fragment_Hotple_w();
                transaction.replace(R.id.fragment_container, fragmentlocalw);
                transaction.commit();
                break;

            case 2:
                Fragment_Hotple_m fragmentlocalm = new Fragment_Hotple_m();
                transaction.replace(R.id.fragment_container, fragmentlocalm);
                transaction.commit();
                break;

            case 3:
                Fragment_Hotple_t fragmentlocalt = new Fragment_Hotple_t();
                transaction.replace(R.id.fragment_container, fragmentlocalt);
                transaction.commit();

                break;
        }

    }

//    @Override
//    public void onRequestPermissionsResult(int permsRequestCode,
//                                           @NonNull String[] permissions,
//                                           @NonNull int[] grandResults) {
//        if ( permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {
//            boolean check_result = true;
//            for (int result : grandResults) {
//                if (result != PackageManager.PERMISSION_GRANTED) {
//                    check_result = false;
//                    break;
//                }
//            }
//
//            if ( check_result ) {
//            }
//            else {
//                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
//                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {
//                    Toast.makeText(LocalHotpleActivity.this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
//                    finish();
//
//                }else {
//                    Toast.makeText(LocalHotpleActivity.this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();
//                }
//            }
//        }
//    }

//    void checkRunTimePermission(){
//        int hasFineLocationPermission = ContextCompat.checkSelfPermission(LocalHotpleActivity.this,
//                Manifest.permission.ACCESS_FINE_LOCATION);
//        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(LocalHotpleActivity.this,
//                Manifest.permission.ACCESS_COARSE_LOCATION);
//
//
//        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
//                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {
//
//
//        } else {
//
//            if (ActivityCompat.shouldShowRequestPermissionRationale(LocalHotpleActivity.this, REQUIRED_PERMISSIONS[0])) {
//                Toast.makeText(LocalHotpleActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
//                ActivityCompat.requestPermissions(LocalHotpleActivity.this, REQUIRED_PERMISSIONS,
//                        PERMISSIONS_REQUEST_CODE);
//
//            } else {
//                ActivityCompat.requestPermissions(LocalHotpleActivity.this, REQUIRED_PERMISSIONS,
//                        PERMISSIONS_REQUEST_CODE);
//            }
//        }
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu, menu);
//        SearchView searchView = (SearchView)menu.findItem(R.id.search).getActionView();
//        searchView.setMaxWidth(Integer.MAX_VALUE);
//        searchView.setQueryHint("검색어를 입력하세요");
//        return super.onCreateOptionsMenu(menu);
//    }
}
