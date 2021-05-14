package com.example.hotplego.ui.manager.menu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.hotplego.ImageGetterImpl;
import com.example.hotplego.PostRun;
import com.example.hotplego.databinding.MenuInfoBinding;
import com.example.hotplego.domain.MenuVO;
import com.google.gson.Gson;

import org.apache.http.HttpStatus;
import org.json.JSONException;

public class MenuInfo extends AppCompatActivity {
    private MenuInfoBinding binding;
    private final int PICK_IMAGE = 1;
    private PostRun postRun;
    private Uri uri;

    private TextView menu_name;
    private TextView menu_price;
    private TextView menu_cnt;
    private TextView menu_cate;
    private TextView menu_hashtag;
    private ImageView menu_img;

    private MenuVO vo = null;

    private void init() {
        menu_name = binding.menuName;
        menu_price = binding.menuPrice;
        menu_cnt = binding.menuCnt;
        menu_cate = binding.menuCateory;
        menu_hashtag = binding.menuHashtag;
        menu_img = binding.menuImg;
    }

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MenuInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        vo = (MenuVO) getIntent().getSerializableExtra("menu");
        init();

        if (vo != null) {
            postRun = new PostRun("update_menu", getParent(), PostRun.IMAGES);
            menu_name.setText(vo.getMeName());
            menu_price.setText(String.valueOf(vo.getMePrice()));
            menu_cnt.setText(vo.getMeIntr());
            menu_cate.setText(vo.getMeCate());
            menu_hashtag.setText(vo.getMeHashTag());
            if (vo.getUuid() != null) Glide.with(this).load(PostRun.getImageUrl(vo.getUploadPath(), vo.getUuid(), vo.getFileName())).into(menu_img);
        } else {
            postRun  = new PostRun("add_menu", getParent(), PostRun.IMAGES);
        }

        binding.menuImg.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, PICK_IMAGE);
        });

        binding.menuModifySave.setOnClickListener(v -> {
            if (vo == null) vo = new MenuVO();
            vo.setMeCode("5");  // TODO 겟쉐얼드 핫플
            vo.setMeName(menu_name.getText().toString());
            vo.setMePrice(Long.parseLong(menu_price.getText().toString()));
            vo.setMeIntr(menu_cnt.getText().toString());
            vo.setMeCate(menu_cate.getText().toString());
            vo.setMeHashTag(menu_hashtag.getText().toString());

            if (vo.getMeCate().isEmpty() || vo.getMeHashTag().isEmpty() || vo.getMeIntr().isEmpty() ||
                    vo.getMeName().isEmpty() || vo.getMePrice() < 0) {
                Toast.makeText(this, "빈 입력값이 존재합니다.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (uri != null) postRun.addImage("upload", getApplicationContext(), uri);
            postRun.setRunUI(() -> {
                try {
                    if (postRun.obj.getBoolean("message"))
                        Toast.makeText(this, "완료 하였습니다.", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(this, "다시 시도해주십시오.", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                postRun = new PostRun("update_menu", getParent(), PostRun.IMAGES);
            });
            postRun.addData("menu", new Gson().toJson(vo))
                    .start();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            if (resultCode == RESULT_OK) {
                uri = data.getData();
                menu_img.setImageURI(uri);
            } else {
                Toast.makeText(this, "이미지를 선택하지 않았습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
