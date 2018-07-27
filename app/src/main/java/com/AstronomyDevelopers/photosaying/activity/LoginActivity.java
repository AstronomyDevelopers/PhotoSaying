package com.AstronomyDevelopers.photosaying.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.AstronomyDevelopers.photosaying.R;
import com.AstronomyDevelopers.photosaying.database.GreenDaoHelper;
import com.AstronomyDevelopers.photosaying.utils.HttpUtils;
import com.AstronomyDevelopers.photosaying.utils.ReplaceViewHelper;


public class LoginActivity extends AppCompatActivity {

    private UserLoginTask mAuthTask = null;
    private UserRegisterTask mRegisterTask = null;

    private HttpUtils httpUtils = null;
    private GreenDaoHelper greenDaoHelper;

    // Login
    private EditText mPhoneView;
    private EditText mPasswordView;
    // Register
    private EditText mCodeView;

    private View mProgressView;
    private View mLoginFormView;
    private View mRegisterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        httpUtils = HttpUtils.getInstance();
        greenDaoHelper = GreenDaoHelper.getInstance();

        Button button1 = findViewById(R.id.bt_login_forget);
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // 启动WebViewActivity跳转到更改密码页面
                Intent intent = new Intent(LoginActivity.this, WebViewActivity.class);
                startActivity(intent);
            }
        });

        Button button2 = findViewById(R.id.bt_login_register);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // 用户点击注册按钮时，将登陆的表格换成注册的表格
                ReplaceViewHelper replaceViewHelper = new ReplaceViewHelper(getApplicationContext());
                View loginForm = findViewById(R.id.cv_login_form);
                View registerForm = getLayoutInflater().inflate(R.layout.card_register, null);
                replaceViewHelper.toReplaceView(loginForm, registerForm);
                processRegister();
            }
        });

        Button button3 = findViewById(R.id.bt_login);
        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // 尝试进行登录
                attemptLogin();
            }
        });

        mPhoneView = findViewById(R.id.et_login_phone);
        mPasswordView = findViewById(R.id.et_login_password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        // 实现提交登录时隐藏表单展示进度条
        mLoginFormView = findViewById(R.id.cv_login_form);
        mProgressView = findViewById(R.id.pb_login);
    }


    // 当用户点击了快速注册时处理用户的注册流程
    private void processRegister() {
        mPhoneView = findViewById(R.id.et_register_phone);
        mPasswordView = findViewById(R.id.et_register_password);
        mCodeView = findViewById(R.id.et_register_valicode);

       TextView sendMessage = findViewById(R.id.tv_register_message);
       sendMessage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               // 用户点击按钮调用验证码发送接口，如果填写信息完整则可以发送验证码
               if(isPhoneValid(mPhoneView.getText().toString()) && isPasswordValid(mPasswordView.getText().toString())) {
                   // 调用接口发送验证码


               } else {
                   Toast.makeText(getApplicationContext(), "请先填写注册信息", Toast.LENGTH_SHORT);
               }
               Toast.makeText(getApplicationContext(),"验证码已发送", Toast.LENGTH_SHORT).show();

           }
       });

        Button button4 = findViewById(R.id.bt_register_sign_up);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegister();
            }
        });
    }


    // 尝试利用用户给出的数据进行登录
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // 重设错误提示
        mPhoneView.setError(null);
        mPasswordView.setError(null);

        // 存储用户输入的数据
        String phone = mPhoneView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // 检查密码合理性
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // 检查手机号的合法性
        if (TextUtils.isEmpty(phone)) {
            mPhoneView.setError(getString(R.string.error_field_required));
            focusView = mPhoneView;
            cancel = true;
        } else if (!isPhoneValid(phone)) {
            mPhoneView.setError(getString(R.string.error_invalid_phone));
            focusView = mPhoneView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            mAuthTask = new UserLoginTask(phone, password);
            mAuthTask.execute((Void) null);
        }
    }

    // 尝试利用用户给出的数据进行注册
    private void attemptRegister() {
        if (mAuthTask != null) {
            return;
        }

        // 重设错误提示
        mPhoneView.setError(null);
        mPasswordView.setError(null);
        mCodeView.setError(null);

        // 存储用户输入的数据
        String phone = mPhoneView.getText().toString();
        String password = mPasswordView.getText().toString();
        String code = mCodeView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // 检查密码合理性
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // 检查手机号的合法性
        if (TextUtils.isEmpty(phone)) {
            mPhoneView.setError(getString(R.string.error_field_required));
            focusView = mPhoneView;
            cancel = true;
        } else if (!isPhoneValid(phone)) {
            mPhoneView.setError(getString(R.string.error_invalid_phone));
            focusView = mPhoneView;
            cancel = true;
        }

        if(!isCodeValid(code)) {
            focusView = mCodeView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            mRegisterTask = new UserRegisterTask(phone, password, code);
            mRegisterTask.execute((Void) null);
        }
    }

    // 验证用户输入的手机号是否合法
    private boolean isPhoneValid(String phone) {
        return phone.length() == 11||phone.length() == 14;
    }

    // 验证密码是否大于8位
    private boolean isPasswordValid(String password) {
        return password.length() > 8;
    }

    // 检查验证码是否为6位
    private boolean isCodeValid(String code) {
        return code.length() == 6;
    }

    // 切换进度条状态
    private void showProgress(final boolean show) {

        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
    }


    // 用户进行密码登录的后台任务
    private class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mPhone;
        private final String mPassword;

        UserLoginTask(String phone, String password) {
            mPhone = phone;
            mPassword = password;
        }

        // 登录逻辑
        @Override
        protected Boolean doInBackground(Void... params) {



            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }


    // 用户进行注册的后台任务
    private class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

        private final String mPhone;
        private final String mPassword;
        private final String mCode;

        UserRegisterTask(String phone, String password, String code) {
            mPhone = phone;
            mPassword = password;
            mCode = code;
        }

        // 注册逻辑
        @Override
        protected Boolean doInBackground(Void... params) {



            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                mCodeView.setError(getString(R.string.error_incorrect_valicode));
                mCodeView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}


