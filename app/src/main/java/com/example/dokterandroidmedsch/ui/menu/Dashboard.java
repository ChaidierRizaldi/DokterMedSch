package com.example.dokterandroidmedsch.ui.menu;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dokterandroidmedsch.R;
import com.example.dokterandroidmedsch.slider.DrawerAdapter;
import com.example.dokterandroidmedsch.slider.model.DrawerItem;
import com.example.dokterandroidmedsch.slider.model.SimpleItem;
import com.example.dokterandroidmedsch.ui.jadwal.JadwalKuliah;
import com.example.dokterandroidmedsch.ui.konfirmasi.KonfirmasiJadwal;
import com.example.dokterandroidmedsch.ui.login.LoginActivity;
import com.example.dokterandroidmedsch.utils.SharedPreferences;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;

public class Dashboard extends AppCompatActivity implements DrawerAdapter.onItemSelected {
    private static  final int POS_DASHBOARD = 0;
    private static final int POS_PROFIL = 1;
    private static final int POS_LOGOUT = 2;

    private String [] screenTitle;
    private Drawable[] screenIcons;

    SharedPreferences preferences;
    private SlidingRootNav slidingRootNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar = findViewById(R.id.toolbar_dashboard);
        setSupportActionBar(toolbar);

        preferences = new SharedPreferences(this);

        slidingRootNav = new SlidingRootNavBuilder(this)
                .withDragDistance(180)
                .withRootViewScale(0.75f)
                .withRootViewElevation(25)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.drawer_menu_sidebar)
                .inject();

        screenIcons = loadScreenIcons();
        screenTitle = loadScreenTitle();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_DASHBOARD).setChecked(true),
                createItemFor(POS_PROFIL),
                createItemFor(POS_LOGOUT)
        ));
        adapter.setListener(this);

        RecyclerView list_menu = findViewById(R.id.list_menu_side);
        list_menu.setNestedScrollingEnabled(false);
        list_menu.setLayoutManager(new LinearLayoutManager(this));
        list_menu.setAdapter(adapter);

        adapter.setSelected(POS_DASHBOARD);
    }

    @SuppressWarnings("rawtypes")
    private DrawerItem createItemFor(int position){
        return new SimpleItem(screenIcons[position], screenTitle[position])
                .withIcon(color(R.color.black))
                .withText(color(R.color.black))
                .withSelectedText(color(R.color.white))
                .withSelectedIcon(color(R.color.white));
    }

    @ColorInt
    private int color(@ColorRes int res){
        return ContextCompat.getColor(this, res);
    }

    private String[] loadScreenTitle(){
        return getResources().getStringArray(R.array.id_activityScreenTittles);
    }

    private Drawable[] loadScreenIcons(){
        TypedArray ta = getResources().obtainTypedArray(R.array.id_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i=0; i< ta.length(); i++){
            int id = ta.getResourceId(i, 0);
            if (id!= 0){
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    @Override
    public void onItemSelected(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (position == POS_DASHBOARD){
            FragmentDashboard dashboardFragment = new FragmentDashboard();
            transaction.replace(R.id.container_dashboard, dashboardFragment);
        }else if (position == POS_LOGOUT){
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            preferences.logoutUser();
            startActivity(intent);
            finish();
        }
        slidingRootNav.closeMenu();
        transaction.addToBackStack(null);
        transaction.commit();
    }

}