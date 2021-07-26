package com.example.dokterandroidmedsch.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.dokterandroidmedsch.R;
import com.example.dokterandroidmedsch.model.get.ResponseLogin;
import com.example.dokterandroidmedsch.model.post.RequestLogin;
import com.example.dokterandroidmedsch.ui.menu.Dashboard;
import com.example.dokterandroidmedsch.utils.Retrofit;
import com.example.dokterandroidmedsch.utils.SharedPreferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginTabFragment extends Fragment {

    LoginAdapter loginAdapter;
    ViewPager viewPager;

    TextView email, pass;
    String email_value, pass_value;
    Button login;
    float v = 0;

    SharedPreferences sp_helper;

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);

        email = root.findViewById(R.id.email_login);
        pass = root.findViewById(R.id.password_login);
        login = root.findViewById(R.id.btn_login);


//        email.setTranslationX(800);
//        pass.setTranslationX(800);
        login.setTranslationX(800);

        sp_helper = new SharedPreferences(LoginTabFragment.this.requireContext());

        email.setAlpha(v);
        pass.setAlpha(v);
        login.setAlpha(v);

        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        pass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        login.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();

        login.setOnClickListener(v -> {

        email_value = email.getText().toString().trim();
        pass_value = pass.getText().toString().trim();

        login(email_value, pass_value);

        });
        return root;
    }

    private void login(String email, String password){
        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            error("Email Kosong atau Email Tidak Valid");
        }else if (password.isEmpty()){
            error("Password Tidak Boleh Kosong");
        }else {
            RequestLogin body_request = new RequestLogin();
            body_request.setEmail(email);
            body_request.setPassword(password);

            Call<ResponseLogin> retrofit_login = Retrofit.endpoints().login(body_request);
            retrofit_login.enqueue(new Callback<ResponseLogin>() {
                @Override
                public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                    if (response.isSuccessful()){
                        String id = String.valueOf(response.body().getIdDokter());
                        String nama_dokter = String.valueOf(response.body().getNama());

                        Log.d("Data Dokter", response.body().toString());

                        sp_helper.loginSession(id);
                        Intent intent = new Intent(LoginTabFragment.this.requireContext(), Dashboard.class);
                        startActivity(intent);
                        LoginTabFragment.this.getActivity().finish();
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    }
                }

                @Override
                public void onFailure(Call<ResponseLogin> call, Throwable t) {
                    Log.e("ERROR LOGIN", t.getMessage());
                }
            });
        }
    }

    private void error(String msg){
        Toast.makeText(LoginTabFragment.this.requireContext(), msg, Toast.LENGTH_SHORT).show();
    }
}

