package com.first.dlam.base.fragment;

import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ViewUtils;
import com.first.dlam.R;
import com.first.dlam.ResultUtils;
import com.first.dlam.SharedPreferencesUtil;
import com.first.dlam.base.BaseFragment;
import com.first.dlam.bean.AllBean5;
import com.first.dlam.bean.AllBeanDay;
import com.first.dlam.databinding.Fragment12Binding;
import com.first.dlam.net.Http;
import com.first.dlam.net.LogCallBack;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.Call;

public class Fragment1_2 extends BaseFragment implements View.OnClickListener{

    private Fragment12Binding binding;
    private int mode=0;
    private SharedPreferencesUtil sharedPreferencesUtil;
    private static final String APP_CACAHE_DIRNAME = "/webcache";
    private MainHandler handler;
    @Override
    public void init(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment1_2,container,false);
        sharedPreferencesUtil = new SharedPreferencesUtil(getContext());
        binding.etCode.setText(sharedPreferencesUtil.getCode_name());
        binding.KForm1.setOnClickListener(this);
        binding.KForm2.setOnClickListener(this);
        binding.KForm3.setOnClickListener(this);
        binding.KForm4.setOnClickListener(this);
        binding.getCodeDetail.setOnClickListener(this);
        handler=new MainHandler(this);
    }
    public static class MainHandler extends Handler {
        WeakReference<Fragment1_2> fragment;

        private MainHandler(Fragment1_2 activity) {
            fragment = new WeakReference<>(activity);
        }

        @Override
        public synchronized void handleMessage(Message msg) {
            if (fragment.get() == null) {
                return;
            }
            if(msg.arg1==1){
                fragment.get().initWebView(msg.obj.toString());
            }
        }
    }

    @Override
    public View getLayoutResId() {
        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.K_form_1:
                binding.KForm1.setBackground(getResources().getDrawable(R.drawable.button_press));
                binding.KForm2.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.KForm3.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.KForm4.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.KForm1.setTextColor(getResources().getColor(R.color.write));
                binding.KForm2.setTextColor(getResources().getColor(R.color.back));
                binding.KForm3.setTextColor(getResources().getColor(R.color.back));
                binding.KForm4.setTextColor(getResources().getColor(R.color.back));
                mode=0;
                break;
            case R.id.K_form_2:
                binding.KForm1.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.KForm2.setBackground(getResources().getDrawable(R.drawable.button_press));
                binding.KForm3.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.KForm4.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.KForm1.setTextColor(getResources().getColor(R.color.back));
                binding.KForm2.setTextColor(getResources().getColor(R.color.write));
                binding.KForm3.setTextColor(getResources().getColor(R.color.back));
                binding.KForm4.setTextColor(getResources().getColor(R.color.back));
                mode=1;
                break;
            case R.id.K_form_3:
                binding.KForm1.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.KForm2.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.KForm3.setBackground(getResources().getDrawable(R.drawable.button_press));
                binding.KForm4.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.KForm1.setTextColor(getResources().getColor(R.color.back));
                binding.KForm2.setTextColor(getResources().getColor(R.color.back));
                binding.KForm3.setTextColor(getResources().getColor(R.color.write));
                binding.KForm4.setTextColor(getResources().getColor(R.color.back));
                mode=2;
                break;
            case R.id.K_form_4:
                binding.KForm1.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.KForm2.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.KForm3.setBackground(getResources().getDrawable(R.drawable.button_normal));
                binding.KForm4.setBackground(getResources().getDrawable(R.drawable.button_press));
                binding.KForm1.setTextColor(getResources().getColor(R.color.back));
                binding.KForm2.setTextColor(getResources().getColor(R.color.back));
                binding.KForm3.setTextColor(getResources().getColor(R.color.back));
                binding.KForm4.setTextColor(getResources().getColor(R.color.write));
                mode=3;
                break;
            case R.id.getCodeDetail:
                showLoadingDialog("加载0%");
                initCode(binding.etCode.getText().toString());
                break;
        }

    }

    private void initCode(final String codeName) {
        Http.getBeanByMM(codeName + ",m5,,2", new LogCallBack() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, int code, final String result) {
                ViewUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = JSONObject.parseObject(result);
                            jsonObject.getJSONObject("data").getJSONObject(codeName);
                            JSONObject a = jsonObject.getJSONObject("data").getJSONObject(codeName);
                            AllBean5 bean5 = JSON.parseObject(a.toJSONString(), AllBean5.class);
                            if (bean5.getM5() == null || bean5.getM5().size() == 0) {
                                dismissLoadingDialog();
                                Toast.makeText(getContext(), "code不正确，请确认sz、sh开头", Toast.LENGTH_SHORT).show();
                                return ;
                            }

                            if(mode==0){
                                getDay(codeName);
                            }else if(mode==1){

                                initWebView("http://market.finance.sina.com.cn/pricehis.php?symbol="+codeName+"&startdate="+ResultUtils.getZhou1());
                            }else if(mode==2){

                                initWebView("http://market.finance.sina.com.cn/pricehis.php?symbol="+codeName+"&startdate="+ResultUtils.getYue1());

                            }else if(mode==3){
                                initWebView("http://market.finance.sina.com.cn/pricehis.php?symbol="+codeName+"&startdate="+ResultUtils.getTimeOneMonth());
                            }

                        } catch (ClassCastException e) {
                            dismissLoadingDialog();
                            Toast.makeText(getContext(), "code不正确，请确认sz、sh开头", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void getDay(String codeName){
        Http.getBeanByDay(codeName + ",day,,,4,qfq", new LogCallBack() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, int code, final String result) {

                JSONObject a = JSONObject.parseObject(result).getJSONObject("data").getJSONObject(codeName);
                AllBeanDay beanDay = JSON.parseObject(a.toJSONString(), AllBeanDay.class);
                String data = ResultUtils.D240(beanDay,0,true);
                if (data.isEmpty()) return;
                String[] time = data.split("#");

                Message message =Message.obtain();
                message.arg1=1;
                message.obj="http://market.finance.sina.com.cn/pricehis.php?symbol="+codeName+"&startdate="+time[time.length-2];
                handler.sendMessage(message);
            }
        });
    }


    public void initWebView(String webUrl) {
        clearWebViewCache();
        binding.web.clearCache(true);
        binding.web.setDrawingCacheEnabled(true);
        binding.web.buildDrawingCache();
        binding.web.buildLayer();
        binding.web.resumeTimers();//恢复pauseTimers时的所有操作。
        binding.web.setInitialScale(1);

        binding.web.setNestedScrollingEnabled(true);

        binding.web.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                binding.scrollView.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        WebSettings setting = binding.web.getSettings();
        setting.setTextSize(WebSettings.TextSize.NORMAL);
        setting.setJavaScriptEnabled(true);//设置WebView是否可以运行JavaScript。
        setting.setDomStorageEnabled(true);//启用或禁用DOM缓存。
        setting.setPluginState(WebSettings.PluginState.ON);
        setting.setAllowFileAccess(true);//启用或禁用WebView访问文件数据。
        setting.setLoadWithOverviewMode(true);// 缩放至屏幕的大小
        setting.setUseWideViewPort(true);////将图片调整到适合webview的大小
        setting.setDatabaseEnabled(true);//启用或禁用数据库缓存。
        setting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//指定WebView的页面布局显示形式，调用该方法会引起页面重绘。默认值为LayoutAlgorithm#NARROW_COLUMNS。
        setting.setDefaultTextEncodingName("UTF-8");//设置编码格式
        setting.setBuiltInZoomControls(true);// 设置WebView可触摸放大缩小
        setting.setDisplayZoomControls(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            setting.setMediaPlaybackRequiresUserGesture(true);//设置WebView是否需要用户手势才能播放媒体。默认true。
        }
        //Android 5.0上 Webview 默认不允许加载 Http 与 Https 混合内容，解决办法：
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        binding.web.setWebChromeClient(new WebChromeClient() {
            // 处理定位权限请求
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin,
                                                           GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }

            @Override
            public void onProgressChanged(WebView webView, int newProgress) {
                super.onProgressChanged(webView, newProgress);
                //TODO 显示正在加载
                showLoadingDialog("加载"+newProgress+"%");
            }

            @Override
            public void onReceivedTitle(WebView webView, String title) {
                super.onReceivedTitle(webView, title);

                // android 6.0 以下通过title获取判断
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    if (title.contains("404") || title.contains("500") || title.contains("Error") || title.contains("找不到网页") || title.contains("网页无法打开")) {
                        dismissLoadingDialog();
                        Toast.makeText(getContext(), "加载失败", Toast.LENGTH_LONG).show();

                    }
                }

            }
        });
        binding.web.setWebViewClient(new webViewClient());
        binding.web.loadUrl(webUrl);
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.web.onPause();
        binding.web.pauseTimers();
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.web.resumeTimers();
        binding.web.onResume();
    }


    public class webViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            try {
                if (!url.startsWith("http://")
                        && !url.startsWith("https://")
                ) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
                if (!binding.web.canGoBack()) {
                    view.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                return true;//没有安装该app时，返回true，表示拦截自定义链接，但不跳转，避免弹出上面的错误页面
            }
            if ("".equals(url) || "".equals(url)) {
//                if (TextUtils.isEmpty(getKey())) openActivity(LoginActivity.class);
//                else webView.loadUrl(url+"?token="+getKey());
            } else binding.web.loadUrl(url);

            return true;//返回false

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            view.getSettings().setBlockNetworkImage(false);
            dismissLoadingDialog();
            super.onPageFinished(view, url);
        }

        public void onPageStarted(WebView view, String url) {
            super.onPageStarted(view, url, null);
        }


        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            // 不要使用super，否则有些手机访问不了，因为包含了一条 handler.cancel()
//            super.onReceivedSslError(view, handler, error);
            // 接受所有网站的证书，忽略SSL错误，执行访问网页
            handler.proceed();
            dismissLoadingDialog();
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            dismissLoadingDialog();

        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }
    }


    /**
     * 清除WebView缓存
     */
    public void clearWebViewCache() {

        //清理Webview缓存数据库
        try {
            getContext().deleteDatabase("webview.db");
            getContext().deleteDatabase("webviewCache.db");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //WebView 缓存文件
        File appCacheDir = new File(getContext().getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME);
//        Log.e(TAG, "appCacheDir path="+appCacheDir.getAbsolutePath());

        File webviewCacheDir = new File(getContext().getCacheDir().getAbsolutePath() + "/webviewCache");
//        Log.e(TAG, "webviewCacheDir path="+webviewCacheDir.getAbsolutePath());

        //删除webview 缓存目录
        if (webviewCacheDir.exists()) {
            deleteFile(webviewCacheDir);
        }
        //删除webview 缓存 缓存目录
        if (appCacheDir.exists()) {
            deleteFile(appCacheDir);
        }
    }

    /**
     * 递归删除 文件/文件夹
     *
     * @param file
     */
    public void deleteFile(File file) {
//        Log.i(TAG, "delete file path=" + file.getAbsolutePath());
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        } else {
//            Log.e(TAG, "delete file no exists " + file.getAbsolutePath());
        }
    }
}
