package com.example.travelplan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    /** 判断是登录状态还是注册状态，0是登录1是注册*/
    int status = 0;

    TextView switchLink, reminder, warning;
    RelativeLayout relative;
    Button loginButton;
    EditText usernameArea, passwordArea, rePasswordArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        switchLink = findViewById(R.id.switch_link);
        reminder = findViewById(R.id.account_reminder);
        warning = findViewById(R.id.warning);
        relative = findViewById(R.id.root_layout);
        loginButton = findViewById(R.id.login_button);
        usernameArea = findViewById(R.id.username);
        passwordArea = findViewById(R.id.password);
        rePasswordArea = findViewById(R.id.re_password);
        addUnderline(switchLink);
        autoLoseBlur(findViewById(R.id.root_layout));
        autoLoseBlur(findViewById(R.id.switch_link));
        bindListenerForSwitch();
        bindListenerToButton();
    }

    /**
     * 给文字添加下划线
     * @param text
     */
    private void addUnderline(TextView text) {
        text.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    /**
     * 点击别处自动取消editText的焦点并收起小键盘
     */
    private void autoLoseBlur(View v) {
        v.setOnTouchListener((View view, MotionEvent event) -> {
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                v.requestFocus();
                InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(this.getWindow().getDecorView().getWindowToken(), 0);
                return false;
            }
        );
    }

    /**
     * 添加登录和注册切换的监听器
     */
    private void bindListenerForSwitch() {
        switchLink.setOnClickListener((View v) -> {
            switchLink.setFocusable(true);
            switchLink.setFocusableInTouchMode(true);
            switchLink.requestFocus();
            if (status == 0) {
                switchToRegister();
            } else {
                switchToLogin();
            }
            clearInput();
        });
    }

    /**
     * 切换到注册界面
     */
    private void switchToRegister() {
        switchLink.setText(R.string.to_login);
        reminder.setText(R.string.already_account);
        loginButton.setText(R.string.register);
        usernameArea.setHint(R.string.username_hint_register);
        usernameArea.setTextSize(16);
        passwordArea.setHint(R.string.password_hint_register);
        passwordArea.setTextSize(16);
        rePasswordArea.setVisibility(View.VISIBLE);
        warning.setVisibility(View.GONE);
        status = 1;
    }

    /**
     * 切换到登陆界面
     */
    private void switchToLogin() {
        switchLink.setText(R.string.to_register);
        reminder.setText(R.string.no_account);
        loginButton.setText(R.string.login);
        usernameArea.setHint(R.string.username_hint_login);
        usernameArea.setTextSize(20);
        passwordArea.setHint(R.string.password_hint_login);
        passwordArea.setTextSize(20);
        rePasswordArea.setVisibility(View.GONE);
        warning.setVisibility(View.GONE);
        status = 0;
    }

    /**
     * 清空输入框
     */
    private void clearInput() {
        usernameArea.setText("");
        passwordArea.setText("");
        rePasswordArea.setText("");
    }

    /**
     * 给登录/注册按钮绑定点击事件
     */
    private void bindListenerToButton () {
        loginButton.setOnClickListener((View v) -> {
            warning.setVisibility(View.GONE);
            if (status == 0) {
                if (usernameArea.getText().length() <= 0 || passwordArea.getText().length() <= 0) {
                    warning.setText(R.string.warning1);
                    warning.setVisibility(View.VISIBLE);
                } else {
//                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                    startActivity2();
                }
            } else {
                String username = usernameArea.getText().toString();
                String password = passwordArea.getText().toString();
                String password2 = rePasswordArea.getText().toString();
                String patternStr = "[0-9, a-zA-Z]+";
                if (Pattern.matches(patternStr, username) && Pattern.matches(patternStr, password)) {
                    if (password.equals(password2)) {
                        Toast.makeText(this, "Register successful, returning to login page.", Toast.LENGTH_SHORT).show();
                        new Thread(() -> {
                            try {
                                Thread.sleep(2000);
                                runOnUiThread(() -> {
                                    switchToLogin();
                                    clearInput();
                                });
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }).start();
                    } else {
                        warning.setText(R.string.warning3);
                        warning.setVisibility(View.VISIBLE);
                    }
                } else {
                    warning.setText(R.string.warning2);
                    warning.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    public void startActivity2(){
        startActivity(new Intent(this,HomePage.class));
    }
}