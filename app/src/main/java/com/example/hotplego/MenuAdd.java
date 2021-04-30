package com.example.hotplego;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

public class MenuAdd extends AppCompatActivity {
    ImageView iv;
    String uriString;

    public static final int REQUEST_GALLERY_CODE = 10;

    public static final int RESULT_CODE_CANCEL = 0;
    public static final int RESULT_CODE_ADD = 1;

    public static final String DATA_MENU_DATA = "menuData";

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.menu_add);

        /*취소하기 버튼 클릭 시*/
        Button bt_back = (Button) findViewById(R.id.menu_add_cancel);
        bt_back.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CODE_CANCEL);
                finish();
            }
        });

        EditText new_name = findViewById(R.id.new_name);
        EditText new_price = findViewById(R.id.new_price);
        EditText new_cnt = findViewById(R.id.new_cnt);

        /*등록하기 버튼 클릭 시*/
        Button bt_submit = (Button) findViewById(R.id.menu_add_submit);
        bt_submit.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuData menuData = new MenuData(
                        new_name.getText().toString(),
                        new_price.getText().toString(),
                        new_cnt.getText().toString(),
                        0);
                if(uriString!=null)
                menuData.setImgUri(getImagePathFromInputStreamUri(Uri.parse(uriString)));
                Intent result = new Intent();
                result.putExtra(DATA_MENU_DATA, menuData);
                setResult(RESULT_CODE_ADD, result);
                finish();
            }
        });

        /*갤러리 접근 코드*/
        iv = findViewById(R.id.new_img);
        iv.setOnClickListener(new View.OnClickListener() { //이미지뷰를 클릭 시 액션
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(MenuAdd.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                Intent gallery = new Intent(); // 인텐트를 통해 접근 코드를 보냄
                //기기 기본 갤러리 접근
                gallery.setType(MediaStore.Images.Media.CONTENT_TYPE);
                //구글 갤러리 접근
//                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(gallery, REQUEST_GALLERY_CODE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_GALLERY_CODE && resultCode == RESULT_OK) {
            try{
                Uri uri = data.getData();
                uriString = uri.toString(); // uri가 받은 데이터를 문자열로 변경.
                Glide.with(getApplicationContext()).load(uriString).into(iv); // 이미지 사진 넣기
            } catch (Exception e) { }
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CODE_CANCEL);
        super.onBackPressed();
    }

    public String getImagePathFromInputStreamUri(Uri uri) {
        InputStream inputStream = null;
        String filePath = null;

        if (uri.getAuthority() != null) {
            try {
                inputStream = getContentResolver().openInputStream(uri); // context needed
                File photoFile = createTemporalFileFrom(inputStream);

                filePath = photoFile.getPath();

            } catch (FileNotFoundException e) {
                // log
            } catch (IOException e) {
                // log
            }finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return filePath;
    }

    private File createTemporalFileFrom(InputStream inputStream) throws IOException {
        File targetFile = null;

        if (inputStream != null) {
            int read;
            byte[] buffer = new byte[8 * 1024];

            targetFile = createTemporalFile();
            OutputStream outputStream = new FileOutputStream(targetFile);

            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            outputStream.flush();

            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return targetFile;
    }

    private File createTemporalFile() {
        return new File(getExternalCacheDir(), new Date().getTime() + ".jpg"); // context needed
    }
}