package com.woshiku.albumlibrary.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.woshiku.albumlibrary.R;
import com.woshiku.albumlibrary.activity.base.BaseActivity;
import com.woshiku.albumlibrary.common.Global;
import com.woshiku.albumlibrary.domain.AlbumFolderInfo;
import com.woshiku.albumlibrary.fragment.impleBase.ContentAlbumFragment;
import com.woshiku.albumlibrary.fragment.impleBase.LeftAlbumFragment;
import com.woshiku.albumlibrary.imageloader.UniversalAndroidImageLoader;
import com.woshiku.albumlibrary.utils.StatusBarCompat;
import com.xinlan.imageeditlibrary.editimage.EditImageActivity;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
public class AlbumActivity extends BaseActivity implements View.OnClickListener {
    ImageView return_bt;
    LinearLayout preview_bt;
    RelativeLayout main_bottom;
    TextView allpics_bt;
    Button picsend_bt;
    ImageView bactBt;
    FrameLayout fm;
    Context context;
    //侧滑栏
    public static final String Left_Menu = "Left_Menu";
    private LeftAlbumFragment leftAlbumFragment;
    //主界面
    public static final String Content_Menu = "content_menu";
    public ContentAlbumFragment contentFragment;
    public static final int ACTION_REQUEST_EDITIMAGE = 9;
    public static final int ACTION_REQUEST_PREIMAGE = 10;

    public static final String IMAGE_SELECTED_ADDR = "imagesAddr";
    ArrayList<String> selectedList;
    @Override
    protected void init() {
        super.init();
        context = AlbumActivity.this;
        UniversalAndroidImageLoader.init(context);
    }

    @Override
    protected void initView() {
        StatusBarCompat.compat(this, this.getResources().getColor(R.color.state_color));
        setContentView(R.layout.activity_album);
        fm = (FrameLayout)findViewById(R.id.frame_all);
        main_bottom = (RelativeLayout)findViewById(R.id.main_bottom);
        return_bt = (ImageView)findViewById(R.id.back_bt);
        preview_bt = (LinearLayout)findViewById(R.id.preview_pics_bt);
        allpics_bt = (TextView)findViewById(R.id.all_pics_bt);
        picsend_bt = (Button)findViewById(R.id.pic_send_bt);
        bactBt = (ImageView)findViewById(R.id.back_bt);
        allpics_bt.setOnClickListener(AlbumActivity.this);
        bactBt.setOnClickListener(AlbumActivity.this);
        picsend_bt.setOnClickListener(AlbumActivity.this);
        initFragment(AlbumActivity.this);
        getData();
    }
    public void getData(){
        Bundle bd = getIntent().getExtras();
        if(bd != null){
            int mode = bd.getInt("mode");
            if(mode == 1){
                Global.modeChoose = Global.ModeChoose.SINGLE;
                Global.maxPicsAmount = 1;
            }
            Log.e("lookat","mode"+mode);
        }
    }
    private void initFragment(FragmentActivity context){
        leftAlbumFragment = new LeftAlbumFragment(context);
        contentFragment = new ContentAlbumFragment(context);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();
        trans.replace(R.id.frame_left, leftAlbumFragment, Left_Menu);
        trans.replace(R.id.frame_content, contentFragment, Content_Menu);
        trans.commit();
    }
    public void selectedPicAmount(List<String> imagesAddr){
        //拿到地址数据
        int count = imagesAddr.size();
        selectedList = (ArrayList)imagesAddr;
        if(count <= 0){
            picsend_bt.setEnabled(false);
            picsend_bt.setText("确定");
        }else{
            picsend_bt.setEnabled(true);
            picsend_bt.setText("确定"+"("+count+")");
        }
    }
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.all_pics_bt) {
            List<AlbumFolderInfo> albumFoldInfo = contentFragment.getAlbumFolderInfo();
            leftAlbumFragment.showData(albumFoldInfo);
            leftAlbumFragment.jumpOrHide();
        }else if(i == R.id.back_bt){
            finish();
        }else if(i == R.id.pic_send_bt){
            Intent returnIntent = new Intent();
            Bundle bd = new Bundle();
            bd.putStringArrayList(IMAGE_SELECTED_ADDR, selectedList);
            returnIntent.putExtras(bd);
            setResult(RESULT_OK, returnIntent);
            sendPicResult(bd);
            finish();
        }
    }
    public void enterEditActivty(String imageAddr,String saveAddr){
        Intent it = new Intent(AlbumActivity.this, EditImageActivity.class);
        it.putExtra(EditImageActivity.FILE_PATH, imageAddr);
        File outputFile = new File(saveAddr);
        it.putExtra(EditImageActivity.EXTRA_OUTPUT,
                outputFile.getAbsolutePath());
        startActivityForResult(it,
                ACTION_REQUEST_EDITIMAGE);
    }
    public void enterPicPreview(String picAddr){
        Intent it = new Intent(AlbumActivity.this, PreviewActivity.class);
        it.putExtra(PreviewActivity.filePath, picAddr);
        startActivityForResult(it,
                ACTION_REQUEST_PREIMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ACTION_REQUEST_EDITIMAGE://
                    //用户保存图片返回成功
                    //contentFragment.reScanPics();
                    Toast.makeText(AlbumActivity.this,"照片返回一下",Toast.LENGTH_SHORT).show();
                    break;
                case ACTION_REQUEST_PREIMAGE:

                    break;
            }
        }
    }
    //退出应用
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK) )
        {
            if(leftAlbumFragment.getLineVisible() == View.VISIBLE){
                leftAlbumFragment.hide();
                return true;
            }else{
                finish();
                return true;
            }
        }else{
            return true;
        }
    }
}
