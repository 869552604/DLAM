package com.first.dlam.base.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.TrafficStats;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.first.dlam.ConfigUtils;
import com.first.dlam.MainActivity;
import com.first.dlam.R;
import com.first.dlam.SharedPreferencesUtil;
import com.first.dlam.base.BaseFragment;
import com.first.dlam.base.InfoActivity;
import com.first.dlam.base.ShareActivity;
import com.first.dlam.base.UpdateInfoActivity;
import com.first.dlam.bean.AllBean5;
import com.first.dlam.bean.FilterBean;
import com.first.dlam.databinding.Fragment5Binding;
import com.first.dlam.net.Http;
import com.first.dlam.net.LogCallBack;
import com.pgyersdk.update.DownloadFileListener;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.pgyersdk.update.javabean.AppBean;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Call;

import static com.blankj.utilcode.util.ViewUtils.runOnUiThread;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment5 extends BaseFragment implements View.OnClickListener {
    private SharedPreferencesUtil sharedPreferencesUtil;
    private Fragment5Binding binding;
    @Override
    public void init(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_5,container,false);
        sharedPreferencesUtil = new SharedPreferencesUtil(getContext());


        binding.etCode.setText(sharedPreferencesUtil.getCode_name());
        binding.editText3.setText(sharedPreferencesUtil.getET_3());
        binding.editText4.setText(sharedPreferencesUtil.getET_4());
        binding.editText6.setText(sharedPreferencesUtil.getET_6());
        binding.shezhi1.setOnClickListener(this);
        binding.update.setOnClickListener(this);
        binding.QQ.setOnClickListener(this);
        binding.share.setOnClickListener(this);
        binding.info.setOnClickListener(this);
        binding.a.setOnClickListener(this);
    }

    @Override
    public View getLayoutResId() {
        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.shezhi1:
                if (TextUtils.isEmpty(binding.etCode.getText().toString())) {
                    Toast.makeText(getContext(), "??????????????????code???", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(binding.editText3.getText().toString()) || TextUtils.isEmpty(binding.editText4.getText().toString()) || TextUtils.isEmpty(binding.editText6.getText().toString()) ){
                    Toast.makeText(getContext(), "??????????????????", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Integer.parseInt(binding.editText3.getText().toString()) > 20) {
                    Toast.makeText(getContext(), "???????????????????????????20", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Integer.parseInt(binding.editText3.getText().toString()) <= 1) {
                    Toast.makeText(getContext(), "???????????????????????????2", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Integer.parseInt(binding.editText4.getText().toString()) > 20) {
                    Toast.makeText(getContext(), "???????????????????????????20", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Integer.parseInt(binding.editText4.getText().toString()) <= 1) {
                    Toast.makeText(getContext(), "???????????????????????????2", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Integer.parseInt(binding.editText6.getText().toString()) > 20) {
                    Toast.makeText(getContext(), "???????????????????????????20", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Integer.parseInt(binding.editText6.getText().toString()) <= 1) {
                    Toast.makeText(getContext(), "???????????????????????????2", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(Integer.parseInt(binding.editText6.getText().toString())>Integer.parseInt(binding.editText4.getText().toString())
                && Integer.parseInt(binding.editText6.getText().toString())>Integer.parseInt(binding.editText3.getText().toString())
                &&Integer.parseInt(binding.editText4.getText().toString())>Integer.parseInt(binding.editText3.getText().toString())) {

                    showLoadingDialog("?????????...");
                    getCodeDetails(binding.etCode.getText().toString());
                }else{
                    Toast.makeText(getContext(), "?????????????????????????????????????????????", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.update:
                showLoadingDialog("?????????????????????");
                new PgyUpdateManager.Builder()
                        .setForced(true)                //??????????????????????????????,?????????????????????????????????????????????
                        .setUserCanRetry(false)         //?????????????????????????????????????????????????????? apk ?????????????????????
                        .setDeleteHistroyApk(false)     // ??????????????????????????????????????? Apk??? ?????????true
                        .setUpdateManagerListener(new UpdateManagerListener() {
                            @Override
                            public void onNoUpdateAvailable() {
                                //??????????????????????????????
                                Toast.makeText(getContext(), "?????????????????????", Toast.LENGTH_SHORT).show();
                                dismissLoadingDialog();
                            }



                            @Override
                            public void onUpdateAvailable(AppBean appBean) {
                                //????????????????????????
                                Log.d("pgyer", "there is new version can update"
                                        + "new versionCode is " + appBean.getVersionCode());
                                //?????????????????????DownloadFileListener ????????????
                                //?????????????????????????????????????????????????????????DownloadFileListener
                                PgyUpdateManager.downLoadApk(appBean.getDownloadURL());
                                dismissLoadingDialog();
                                Toast.makeText(getContext(), "??????????????????", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void checkUpdateFailed(Exception e) {
                                //????????????????????????
                                //??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                                dismissLoadingDialog();
                            }
                        })
                        //?????? ???
                        //?????????????????? PgyUpdateManager.downLoadApk(appBean.getDownloadURL()); ??????????????????
                        //???????????????????????????????????????????????????????????? UI ???????????????
                        //?????????????????????????????????????????????UI?????????????????????
                        .setDownloadFileListener(new DownloadFileListener() {
                            @Override
                            public void downloadFailed() {
                                //????????????
                                Log.e("pgyer", "download apk failed");
                                dismissLoadingDialog();
                            }

                            @Override
                            public void downloadSuccessful(File file) {
                                Log.e("pgyer", "download apk success");
                                // ???????????????????????????????????????????????? ??????apk
                                PgyUpdateManager.installApk(file);
                                dismissLoadingDialog();
                            }

                            @Override
                            public void onProgressUpdate(Integer... integers) {
                                Log.e("pgyer", "update download apk progress" + integers);
                                dismissLoadingDialog();
                            }})
                        .register();
                break;

            case R.id.QQ:
                if(checkApkExist(getContext(),"com.tencent.mobileqq") || checkApkExist(getContext(),"com.tencent.hd.qq")|| checkApkExist(getContext(),"com.tencent.minihd.qq")){
                    String url = "mqqwpa://im/chat?chat_type=wpa&uin=1770448992";
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                } else {
                    Toast.makeText(getContext(), "????????????QQ???????????????", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.info:
                Intent intent =new Intent(getContext(), InfoActivity.class);
                startActivity(intent);
                break;
            case R.id.share:
                Intent intent1 =new Intent(getContext(), ShareActivity.class);
                startActivity(intent1);
                break;
            case R.id.a:
                Intent intent2 =new Intent(getContext(), UpdateInfoActivity.class);
                startActivity(intent2);
                break;
        }
    }

    private void getCodeDetails(final String codeName) {
        Http.getBeanByMM(codeName + ",m5,,20", new LogCallBack() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, int code, final String result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            dismissLoadingDialog();
                            JSONObject jsonObject = JSONObject.parseObject(result);
                            jsonObject.getJSONObject("data").getJSONObject(codeName);
                            JSONObject a = jsonObject.getJSONObject("data").getJSONObject(codeName);
                            AllBean5 bean5 = JSON.parseObject(a.toJSONString(), AllBean5.class);
                            if (bean5.getM5() == null || bean5.getM5().size() == 0) {
                                Toast.makeText(getContext(), "code?????????????????????sz???sh??????", Toast.LENGTH_SHORT).show();
                                return ;
                            }

                            sharedPreferencesUtil.save_code_name(codeName);
                            sharedPreferencesUtil.save_et_3(binding.editText3.getText().toString());
                            sharedPreferencesUtil.save_et_4(binding.editText4.getText().toString());
                            sharedPreferencesUtil.save_et_6(binding.editText6.getText().toString());
                            Fragment1_3.isDestory=true;
                            Fragment2.isDestory=true;
                            Intent intent =new Intent(getContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);


                        } catch (ClassCastException e) {
                            Toast.makeText(getContext(), "code?????????????????????sz???sh??????", Toast.LENGTH_SHORT).show();
                            dismissLoadingDialog();
                        }
                    }
                });
            }
        });
    }

    public boolean checkApkExist(Context context, String packageName) {
        if (packageName == null || "".equals(packageName)) return false;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {return false;}
    }

    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(FilterBean event) {
        if(event.getPosition()==4){

            binding.tv.setText("????????????WIFI??????????????????:"+((TrafficStats.getUidRxBytes(ConfigUtils.getUid(getContext())) - MainActivity.startMobileRx) / 1024/1024 >0 ?
                    (TrafficStats.getUidRxBytes(ConfigUtils.getUid(getContext())) - MainActivity.startMobileRx) / 1024/1024+"MB":(TrafficStats.getUidRxBytes(ConfigUtils.getUid(getContext())) - MainActivity.startMobileRx) / 1024+"KB"));
        }
    }
}
