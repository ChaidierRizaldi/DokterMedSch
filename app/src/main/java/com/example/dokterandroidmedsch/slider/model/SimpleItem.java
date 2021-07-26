package com.example.dokterandroidmedsch.slider.model;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.dokterandroidmedsch.R;
import com.example.dokterandroidmedsch.slider.DrawerAdapter;

public class SimpleItem extends DrawerItem<SimpleItem.ViewHolder> {
    private int selectedItemIcon;
    private int selectedItemText;

    private int normalItemIcon;
    private int normalItemText;

    private Drawable icon;
    private String title;

    public SimpleItem(Drawable icon, String title_toolbar){
        this.icon = icon;
        this.title = title_toolbar;
    }

    @Override
    public ViewHolder createViewHolder(ViewGroup parent) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_sidebar, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void bindViewHolder(ViewHolder holder) {
        holder.icon.setImageDrawable(icon);

        holder.icon.setColorFilter(isChecked? selectedItemText: normalItemIcon);
    }

    public SimpleItem withSelectedText(int selectedItemText){
        this.selectedItemText = selectedItemText;
        return this;
    }

    public SimpleItem withSelectedIcon(int selectedItemIcon){
        this.selectedItemIcon = selectedItemIcon;
        return this;
    }

    public SimpleItem withIcon(int normalItemIcon){
        this.normalItemIcon = normalItemIcon;
        return this;
    }

    public SimpleItem withText(int normalItemText){
        this.normalItemText = normalItemText;
        return this;
    }

    static class ViewHolder extends DrawerAdapter.ViewHolder {
        private ImageView icon;
        private TextView title;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            icon = itemView.findViewById(R.id.btn_menu_sidebar);
            title = itemView.findViewById(R.id.title_toolbar);
        }
    }
}
