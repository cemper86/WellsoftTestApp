package ru.stairenx.wellsofttestapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.json.JSONObject;

import ru.stairenx.wellsofttestapp.item.LoginItem;
import ru.stairenx.wellsofttestapp.server.ConnectAPI;
import ru.stairenx.wellsofttestapp.server.ConstantsAPI;

public class LoginActivity extends AppCompatActivity {

    private EditText etLogin, etPassword;
    private Button btnSubmit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        checkPermissionOnApp();
        initView();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void initView() {
        etLogin = findViewById(R.id.et_login);
        etPassword = findViewById(R.id.et_password);
        btnSubmit = findViewById(R.id.btn_submit);
        clickBtnSubmit();
    }

    private void clickBtnSubmit(){
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = etLogin.getText().toString();
                String pass = etPassword.getText().toString();
                if(!login.isEmpty()&&!pass.isEmpty()){
                    LoginItem item = new LoginItem(login,pass);
                    ConnectAPI.queryLogin(getApplicationContext(), item, new ConnectAPI.ResponseServer() {
                        @Override
                        public void onSuccess(JSONObject jsonResponse) {
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            intent.putExtra("token",jsonResponse.optString(ConstantsAPI.JSON_ACCESS_TOKEN));
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onFailure(JSONObject jsonResponse) {
                            if(!jsonResponse.isNull(ConstantsAPI.JSON_ERROR)){
                                Toast.makeText(LoginActivity.this, jsonResponse.optString(ConstantsAPI.JSON_ERROR), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else Toast.makeText(LoginActivity.this, "Введите логин и пароль", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkPermissionOnApp(){
        int permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET, Manifest.permission.CAMERA}, 1);
        }
    }

}
