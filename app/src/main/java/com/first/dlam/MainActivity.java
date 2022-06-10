package com.first.dlam;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.net.TrafficStats;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.first.dlam.base.BaseActivity;
import com.first.dlam.base.dialog.UpdateDialog;
import com.first.dlam.base.fragment.Fragment1;
import com.first.dlam.base.fragment.Fragment1_3;
import com.first.dlam.base.fragment.Fragment2;
import com.first.dlam.base.fragment.Fragment3;
import com.first.dlam.base.fragment.Fragment4;
import com.first.dlam.base.fragment.Fragment5;
import com.first.dlam.bean.BaseEvent;
import com.first.dlam.bean.FilterBean;
import com.first.dlam.bean.FirstBean;
import com.first.dlam.databinding.ActivityMainBinding;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.pgyersdk.update.DownloadFileListener;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.pgyersdk.update.javabean.AppBean;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;
    private ArrayList<String> titleRes = new ArrayList<>();                 //tab标题集合
    private ArrayList<Fragment> fsRes = new ArrayList<>();                  //fragment集合
    private ArrayList<CustomTabEntity> data = new ArrayList<>();                 //CommonTabLayout 所需数据集合
    public static long startMobileRx;
    private long firstTime=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.mLayout.setBackgroundColor(Color.argb(255,41,61,85));
        initData();
        startMobileRx = TrafficStats.getUidRxBytes(ConfigUtils.getUid(MainActivity.this));

        initListener();

    }

    private void initListener() {
        new PgyUpdateManager.Builder()
                .setForced(true)                //设置是否强制提示更新,非自定义回调更新接口此方法有用
                .setUserCanRetry(false)         //失败后是否提示重新下载，非自定义下载 apk 回调此方法有用
                .setDeleteHistroyApk(false)     // 检查更新前是否删除本地历史 Apk， 默认为true
                .setUpdateManagerListener(new UpdateManagerListener() {
                    @Override
                    public void onNoUpdateAvailable() {
                        //没有更新是回调此方法
                    }



                    @Override
                    public void onUpdateAvailable(AppBean appBean) {
                        //有更新回调此方法
                        Log.d("pgyer", "there is new version can update"
                                + "new versionCode is " + appBean.getVersionCode());
                        //调用以下方法，DownloadFileListener 才有效；
                        //如果完全使用自己的下载方法，不需要设置DownloadFileListener

                        UpdateDialog dialog =new UpdateDialog(new UpdateDialog.Updata() {
                            @Override
                            public void upData() {
                                Toast.makeText(MainActivity.this,"正在获取资源...",Toast.LENGTH_SHORT).show();
                                PgyUpdateManager.downLoadApk(appBean.getDownloadURL());
                            }
                        });
                        dialog.show(getSupportFragmentManager(),"1");
                    }

                    @Override
                    public void checkUpdateFailed(Exception e) {
                        //更新检测失败回调
                        //更新拒绝（应用被下架，过期，不在安装有效期，下载次数用尽）以及无网络情况会调用此接口

                    }
                })
                //注意 ：
                //下载方法调用 PgyUpdateManager.downLoadApk(appBean.getDownloadURL()); 此回调才有效
                //此方法是方便用户自己实现下载进度和状态的 UI 提供的回调
                //想要使用蒲公英的默认下载进度的UI则不设置此方法
                .setDownloadFileListener(new DownloadFileListener() {
                    @Override
                    public void downloadFailed() {
                        //下载失败
                        Log.e("pgyer", "download apk failed");

                    }

                    @Override
                    public void downloadSuccessful(File file) {
                        Log.e("pgyer", "download apk success");
                        // 使用蒲公英提供的安装方法提示用户 安装apk
                        PgyUpdateManager.installApk(file);

                    }

                    @Override
                    public void onProgressUpdate(Integer... integers) {
                        Log.e("pgyer", "update download apk progress" + integers);

                    }})
                .register();
    }

    private void initData() {

        //标题资源
        titleRes.add("个股");
        titleRes.add("自选");
        titleRes.add("待定");
        titleRes.add("选股");
        titleRes.add("我的");
        //fragment数据
        fsRes.add(new Fragment1());
        fsRes.add(new Fragment2());
        fsRes.add(new Fragment3());
        fsRes.add(new Fragment4());
        fsRes.add(new Fragment5());
        //设置数据
        for (int i = 0; i < titleRes.size(); i++) {
            final int index = i;
            data.add(new CustomTabEntity() {
                @Override
                public String getTabTitle() {
                    return titleRes.get(index);
                }

                @Override
                public int getTabSelectedIcon() {
                    return 0;
                }

                @Override
                public int getTabUnselectedIcon() {
                    return 0;
                }
            });
        }
        //设置数据
        binding.mLayout.setTabData(data, this, R.id.frame_layout, fsRes);

        binding.mLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                EventBus.getDefault().postSticky(new FilterBean(position));
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_DOWN){
            if (System.currentTimeMillis()-firstTime>2000){
                Toast.makeText(MainActivity.this,"再按一次退出程序",Toast.LENGTH_SHORT).show();
                firstTime=System.currentTimeMillis();
            }else{
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
