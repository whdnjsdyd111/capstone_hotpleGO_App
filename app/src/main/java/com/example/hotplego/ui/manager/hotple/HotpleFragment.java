package com.example.hotplego.ui.manager.hotple;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.hotplego.PostRun;
import com.example.hotplego.R;
import com.example.hotplego.UserSharedPreferences;
import com.example.hotplego.databinding.ManagerHotpleBinding;
import com.example.hotplego.domain.HotpleVO;
import com.example.hotplego.domain.ManagerVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

public class HotpleFragment extends Fragment {
    private ManagerHotpleBinding binding;
    private Spinner bank;
    private Spinner cate_big;
    private Spinner cate_small;
    private ImageView hotple_img;
    private ArrayAdapter<String> bank_adapter;
    private ArrayAdapter<String> cate_big_adapter;
    private ArrayAdapter<String> cate_small_adapter;
    private Uri uri;
    private final int PICK_IMAGE = 1;
    private final int ADDRESS_RESULT = 2;

    private String address;
    private String zip;
    private Double lat;
    private Double lng;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = ManagerHotpleBinding.inflate(inflater, container, false);



        hotple_img = binding.hotpleImg;
        hotple_img.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, PICK_IMAGE);
        });

        bank = binding.managerBank;
        bank_adapter = new ArrayAdapter<String>(getContext(),
                R.layout.spinner_textview, getResources().getStringArray(R.array.bank_array));
        bank_adapter.setDropDownViewResource(R.layout.spinner_item);
        bank.setAdapter(bank_adapter);

        cate_big = binding.hotpleCateBig;
        cate_big_adapter = new ArrayAdapter<>(getContext(),
                R.layout.spinner_textview, getResources().getStringArray(R.array.category));
        cate_big_adapter.setDropDownViewResource(R.layout.spinner_item);
        cate_big.setAdapter(cate_big_adapter);

        cate_big.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                initSpinner(parent.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        cate_small = binding.hotpleCateSmall;

        binding.managerBankBtn.setOnClickListener(v -> {
            PostRun postRun = new PostRun("bank_update", getActivity(), PostRun.DATA);
            postRun.setRunUI(() -> {
                try {
                    if (postRun.obj.getBoolean("message")) Toast.makeText(getActivity(), "수정 완료하였습니다.", Toast.LENGTH_SHORT).show();
                    else Toast.makeText(getActivity(), "다시 시도해주십시오.", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) { e.printStackTrace(); }
            });
            postRun.addData("uCode", UserSharedPreferences.user.getUCode())
                    .addData("account", binding.managerAccount.getText().toString())
                    .addData("bank", binding.managerBank.getSelectedItem().toString())
                    .start();
        });

        binding.managerNickBtn.setOnClickListener(v -> {
            PostRun postRun = new PostRun("nick_update", getActivity(), PostRun.DATA);
            postRun.setRunUI(() -> {
                try {
                    if (postRun.obj.getBoolean("message")) Toast.makeText(getActivity(), "수정 완료하였습니다.", Toast.LENGTH_SHORT).show();
                    else Toast.makeText(getActivity(), "다시 시도해주십시오.", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) { e.printStackTrace(); }
            });
            postRun.addData("uCode", UserSharedPreferences.user.getUCode())
                    .addData("nick", binding.managerNick.getText().toString())
                    .start();
        });

        binding.hotpleUpdate.setOnClickListener(v -> {
            HotpleVO vo = new HotpleVO();
            vo.setHtId(UserSharedPreferences.hotple.getHtId());
            vo.setBusnName(binding.hotpleName.getText().toString());
            vo.setBusnNum(binding.hotpleBusn.getText().toString());
            vo.setHtAddr(binding.hotpleAddress.getText().toString());
            vo.setHtAddrDet(binding.hotpleAddrDetail.getText().toString());
            vo.setHtZip(Long.parseLong(binding.hotpleZip.getText().toString()));
            vo.setHtCont(binding.hotpleCont.getText().toString());
            vo.setHtTel(binding.managerPhone.getText().toString());
            vo.setHtLng(lng);
            vo.setHtLat(lat);
            int big = cate_big.getSelectedItemPosition() * 10;
            int small = cate_small.getSelectedItemPosition();
            vo.setCategory((long) (big + small));
            PostRun postRun = new PostRun("comp_update", getActivity(), PostRun.IMAGES);
            postRun.setRunUI(() -> {
                try {
                    if (postRun.obj.getBoolean("message")) Toast.makeText(getActivity(), "수정 완료하였습니다.", Toast.LENGTH_SHORT).show();
                    else Toast.makeText(getActivity(), "다시 시도해주십시오.", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) { e.printStackTrace(); }
            });
            postRun.addData("hotple", new Gson().toJson(vo));
            if (uri != null) postRun.addImage("upload", getContext(), uri);
            postRun.start();
        });

        binding.jusoPopup.setOnClickListener(v -> {
            startActivityForResult(new Intent(getContext(), JusoPopupActivity.class), 2);
        });

        return binding.getRoot();
    }

    private void loadView() {
        PostRun postRun = new PostRun("manager_hotple", getActivity(), PostRun.DATA);
        postRun.setRunUI(() -> {
            try {
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss.SSS").create();
                HotpleVO hotple = gson.fromJson(postRun.obj.getString("hotple"), HotpleVO.class);
                ManagerVO manager = gson.fromJson(postRun.obj.getString("manager"), ManagerVO.class);

                binding.managerEmail.setText(manager.getUCode().split("/")[0]);
                binding.managerRegister.setText(PostRun.timestampToStr(manager.getRegDate()));
                binding.managerName.setText(manager.getMName());
                binding.managerAccount.setText(manager.getMAccount());
                bank.setSelection(bank_adapter.getPosition(manager.getMBank()));
                binding.managerNick.setText(manager.getNick());

                if (hotple.getHtImg() != null) Glide.with(getActivity()).load(PostRun.getImageUrl(
                        hotple.getUploadPath(), hotple.getHtImg(), hotple.getFileName()
                )).into(hotple_img);
                binding.hotpleName.setText(hotple.getBusnName());
                binding.hotpleBusn.setText(hotple.getBusnNum());
                binding.hotpleAddress.setText(hotple.getHtAddr());
                binding.hotpleAddrDetail.setText(hotple.getHtAddrDet());
                binding.hotpleZip.setText(String.valueOf(hotple.getHtZip()));
                binding.hotpleCont.setText(hotple.getHtCont());
                binding.managerPhone.setText(hotple.getHtTel());
                lat = hotple.getHtLat();
                lng = hotple.getHtLng();

                int selected = hotple.getCategory().intValue();
                cate_big.setSelection(selected / 10);
                initSpinner(cate_big.getSelectedItem().toString());
                cate_small.setSelection(selected % 10);

                if (address != null && zip != null) result();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        postRun.addData("uCode", UserSharedPreferences.user.getUCode())
                .start();
    }

    private void initSpinner(String seledted) {
        switch (seledted) {
            case "먹거리" :
                cate_small_adapter = new ArrayAdapter<>(getContext(),
                        R.layout.spinner_textview, getResources().getStringArray(R.array.food));
                break;
            case "디저트" :
                cate_small_adapter = new ArrayAdapter<>(getContext(),
                        R.layout.spinner_textview, getResources().getStringArray(R.array.dessert));
                break;
            case "놀이/취미" :
                cate_small_adapter = new ArrayAdapter<>(getContext(),
                        R.layout.spinner_textview, getResources().getStringArray(R.array.play));
                break;
            case "음주" :
                cate_small_adapter = new ArrayAdapter<>(getContext(),
                        R.layout.spinner_textview, getResources().getStringArray(R.array.alcohol));
                break;
            case "보기" :
                cate_small_adapter = new ArrayAdapter<>(getContext(),
                        R.layout.spinner_textview, getResources().getStringArray(R.array.watch));
                break;
            case "걷기" :
                cate_small_adapter = new ArrayAdapter<>(getContext(),
                        R.layout.spinner_textview, getResources().getStringArray(R.array.walk));
        }
        cate_small_adapter.setDropDownViewResource(R.layout.spinner_item);
        cate_small.setAdapter(cate_small_adapter);
    }

    @Override
    public void onResume() {
        loadView();
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                uri = data.getData();
                hotple_img.setImageURI(uri);
            } else {
                Toast.makeText(getActivity(), "이미지를 선택하지 않았습니다.", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == ADDRESS_RESULT) {
            if (resultCode == Activity.RESULT_OK) {
                address = data.getStringExtra("address");
                zip = data.getStringExtra("zip");
                lng = Double.valueOf(data.getStringExtra("lng"));
                lat = Double.valueOf(data.getStringExtra("lat"));
                binding.hotpleAddrDetail.setVisibility(View.GONE);
                binding.hotpleAddrDetailEdit.setVisibility(View.VISIBLE);
            }
        }
    }

    private void result() {
        binding.hotpleAddress.setText(address);
        binding.hotpleZip.setText(zip);
    }
}
