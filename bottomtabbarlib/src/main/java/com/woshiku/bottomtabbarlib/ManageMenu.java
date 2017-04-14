package com.woshiku.bottomtabbarlib;

import android.content.Context;
import android.widget.LinearLayout;

/**
 * Created by admin on 2017-04-14.
 */

public class ManageMenu {
    LinearLayout parentLine;
    Context context;
    int []tabIcons = {};
    String []titles = {};
    int selectedPos = 0;
    int []selectedColor;
    int []unSelectedColors;
    int tabIconSize,topMargin,textSize;
    private TabbarClickListener tabbarClickListener;
    interface TabbarClickListener{
        void tabbarClick(int pos);
    }

    public void setTabbarClickListener(TabbarClickListener tabbarClickListener) {
        this.tabbarClickListener = tabbarClickListener;
    }

    public ManageMenu(Context context, LinearLayout parentLine) {
        this.context = context;
        this.parentLine = parentLine;
    }

    public void gengerMenus(){
        parentLine.setWeightSum(titles.length);
        parentLine.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT);
        params.weight = 1.0f;
        for(int i=0;i<titles.length;i++){
            MenuItemView menuItemView = new MenuItemView(context);
            menuItemView.setOrientation(LinearLayout.HORIZONTAL);
            menuItemView.generView();
            menuItemView.setUnSelectedColor(unSelectedColors[i]);
            menuItemView.setSelectedColor(selectedColor[i]);
            menuItemView.setText(titles[i]);
            menuItemView.setIcon(tabIcons[i]);
            menuItemView.setTabIconSize(tabIconSize);
            menuItemView.setMarginSize(topMargin);
            menuItemView.setTextSize(textSize);
            menuItemView.setLayoutParams(params);
            final int posIndex = i;
            menuItemView.setMenuItemClickListener(new MenuItemView.MenuItemClickListener() {
                @Override
                public void menuItemClick() {
                    selectedPos = posIndex;
                    setChooseState(posIndex);
                    if(tabbarClickListener != null){
                        tabbarClickListener.tabbarClick(posIndex);
                    }
                }
            });
            parentLine.addView(menuItemView);
        }
        setChooseState(selectedPos);
    }

    private void setChooseState(int index){
        for(int i=0;i<titles.length;i++){
            MenuItemView menuItemView = (MenuItemView)parentLine.getChildAt(i);
            if(i==index){
                menuItemView.setChecked(true);
            }else{
                menuItemView.setChecked(false);
            }
        }
    }
    public void setTabIcons(int[] tabIcons) {
        this.tabIcons = tabIcons;
    }


    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    public void setSelectedColor(int[] selectedColor) {
        this.selectedColor = selectedColor;
    }

    public void setUnSelectedColors(int[] unSelectedColors) {
        this.unSelectedColors = unSelectedColors;
    }

    public void setTabIconSize(int tabIconSize) {
        this.tabIconSize = tabIconSize;
    }

    public void setTopMargin(int topMargin) {
        this.topMargin = topMargin;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }
}
