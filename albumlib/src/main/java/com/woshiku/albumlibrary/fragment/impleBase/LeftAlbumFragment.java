package com.woshiku.albumlibrary.fragment.impleBase;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.woshiku.albumlibrary.R;
import com.woshiku.albumlibrary.activity.AlbumActivity;
import com.woshiku.albumlibrary.common.Global;
import com.woshiku.albumlibrary.domain.AlbumFolderInfo;
import com.woshiku.albumlibrary.fragment.BaseFragment;
import com.woshiku.albumlibrary.presenter.AlbumListPresenter;
import com.woshiku.albumlibrary.view.AlbumListView;
import com.woshiku.albumlibrary.view.LeftAlbumView;
import java.util.List;

/**
 * Created by Administrator on 2016/8/15.
 */
@SuppressLint("ValidFragment")
public class LeftAlbumFragment extends BaseFragment implements AdapterView.OnItemClickListener,LeftAlbumView, View.OnClickListener {
    View mView;
    RelativeLayout relate_pop;
    AlbumListView albumListView;
    AlbumListPresenter albumListPresenter;
    List<AlbumFolderInfo> dirInfo;
    LinearLayout line_top;
    @SuppressLint("ValidFragment")
    public LeftAlbumFragment(FragmentActivity mActiviy) {
        super(mActiviy);
    }

    @Override
    public View initViews() {
        mView = View.inflate(mActivity, R.layout.album_selected_layout,null);
        relate_pop = (RelativeLayout)mView.findViewById(R.id.relate_pop);
        line_top = (LinearLayout)mView.findViewById(R.id.lin_top);
        albumListView = (AlbumListView)mView.findViewById(R.id.album_listview);
        albumListView.setOnItemClickListener(LeftAlbumFragment.this);
        albumListPresenter = new AlbumListPresenter(LeftAlbumFragment.this);
        line_top.setOnClickListener(LeftAlbumFragment.this);
        return mView;
    }

    public void setData(List<AlbumFolderInfo> dirInfo){
        this.dirInfo = dirInfo;
        setSelected(Global.userSelected);
        albumListView.setData(dirInfo);
    }
    private void setSelected(int pos){
        for(int i=0;i<dirInfo.size();i++){
            AlbumFolderInfo dir = dirInfo.get(i);
            if(pos==i){
                dir.setIsSelected(true);
            }else{
                dir.setIsSelected(false);
            }
            dirInfo.set(i,dir);
        }
    }
    public void jumpOrHide(){
        if(relate_pop.getVisibility() == View.INVISIBLE){
            Animation animation= AnimationUtils.loadAnimation(mActivity, R.anim.trans_anim);
            relate_pop.startAnimation(animation);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation arg0) {
                }   //在动画开始时使用

                @Override
                public void onAnimationRepeat(Animation arg0) {
                }  //在动画重复时使用

                @Override
                public void onAnimationEnd(Animation arg0) {
                    Log.e("lookat", "动画结束");
                    relate_pop.setVisibility(View.VISIBLE);
                }
            });
        }else if (relate_pop.getVisibility() == View.VISIBLE){
            Animation animation= AnimationUtils.loadAnimation(mActivity, R.anim.trans_anim_out);
            relate_pop.startAnimation(animation);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation arg0) {
                }   //在动画开始时使用

                @Override
                public void onAnimationRepeat(Animation arg0) {
                }  //在动画重复时使用

                @Override
                public void onAnimationEnd(Animation arg0) {
                    Log.e("lookat", "动画结束1");
                    relate_pop  .setVisibility(View.INVISIBLE);
                }
            });

        }
    }
    public int getLineVisible(){
        return relate_pop.getVisibility();
    }
    public void hide(){
        if (relate_pop.getVisibility() == View.VISIBLE){
            Animation animation= AnimationUtils.loadAnimation(mActivity, R.anim.trans_anim_out);
            relate_pop.startAnimation(animation);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation arg0) {
                }   //在动画开始时使用

                @Override
                public void onAnimationRepeat(Animation arg0) {
                }  //在动画重复时使用

                @Override
                public void onAnimationEnd(Animation arg0) {
                    relate_pop.setVisibility(View.INVISIBLE);
                }
            });
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Global.userSelected = position;
        albumListPresenter.setListData(dirInfo);
        AlbumActivity mainOne = (AlbumActivity)mActivity;
        ContentAlbumFragment contentFragment = (ContentAlbumFragment)getFragmentManager().findFragmentByTag(mainOne.Content_Menu);
        contentFragment.setCurrentPics();
    }
    public void showData(List<AlbumFolderInfo> dirInfo){
        albumListPresenter.setListData(dirInfo);
    }
    @Override
    public void updateListView(List<AlbumFolderInfo> dirInfo) {
        setData(dirInfo);
    }

    @Override
    public void onClick(View v) {
        hide();
    }
}
