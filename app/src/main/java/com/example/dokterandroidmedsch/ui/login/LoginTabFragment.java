package com.example.dokterandroidmedsch.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.dokterandroidmedsch.R;
import com.example.dokterandroidmedsch.ui.Dashboard;
import com.example.dokterandroidmedsch.ui.login.LoginAdapter;

public class LoginTabFragment extends Fragment {

    LoginAdapter loginAdapter;
    ViewPager viewPager;

    TextView email;
    TextView pass;
    TextView forgetPass;
    Button login;
    ImageView imageView;
    float v = 0;


    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);

        email = root.findViewById(R.id.email);
        pass = root.findViewById(R.id.pass);
        forgetPass = root.findViewById(R.id.forget_Pass);
        login = root.findViewById(R.id.btn_login);
        imageView = root.findViewById(R.id.imageLogin);

        email.setTranslationX(800);
        pass.setTranslationX(800);
        forgetPass.setTranslationX(800);
        login.setTranslationX(800);
        imageView.setTranslationX(800);

        email.setAlpha(v);
        pass.setAlpha(v);
        forgetPass.setAlpha(v);
        login.setAlpha(v);
        imageView.setAlpha(v);

        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        pass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        forgetPass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        login.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        imageView.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(900).start();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Dashboard.class);
                startActivity(intent);
            }
        });
        return root;
    }
}

