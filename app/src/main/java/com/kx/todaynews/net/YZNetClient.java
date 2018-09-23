package com.kx.todaynews.net;

import android.content.Context;
import android.text.TextUtils;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.GsonBuilder;
import com.kx.todaynews.YZBaseConstants;


import java.io.File;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class YZNetClient {

    private static final int TIMEOUT_VALUE = 30*1000;

    //okhttp的缓存大小，现为50M
    public static final long CATCH_SIZE = 50*1024*1024;

    private static WeakReference<Context> contextRef;

    private static YZNetClient instance;
    private HttpLoggingInterceptor.Level logLevel = HttpLoggingInterceptor.Level.BODY;
    private Retrofit retrofit;
    private Map<Class,Object> apiMap;
  //  private String cookies;
    private String userAgent;
    private OkHttpClient okHttpClient;
    private ClearableCookieJar cookieJar;
    public static String HOST_1F = "http://lf.snssdk.com";
    public static  String HOST_A3 = "http://a3.pstatp.com";

    private YZNetClient(){
      //  cookies = getCookie();
        userAgent = System.getProperty("http.agent");
        createClient();
    }

    public static void init(Context context){
        contextRef = new WeakReference<Context>(context);
       // FileDownloader.setup(context);
    }

    public static YZNetClient getInstance(){
        if (instance==null){
            instance = new YZNetClient();
        }
        return instance;
    }

    private void createClient(){
        if (contextRef==null){
            throw new IllegalArgumentException("YZNetClient not init");
        }
        Context context = contextRef.get();
        if (context==null){
            throw new IllegalArgumentException("YZNetClient was release");
        }
        if (cookieJar == null){
            cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
        }
        if (okHttpClient==null){
            okHttpClient = getOkHttpClient();
        }
        retrofit = new Retrofit.Builder()
                .baseUrl(HOST_1F)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().setLenient().create()))
                .build();
        if (apiMap==null){
            apiMap = new HashMap<>();
        }
        apiMap.clear();
    }

    private synchronized OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder newBuilder = new OkHttpClient().newBuilder();
        Interceptor requestInterceptor = chain -> {

            Request.Builder builder = chain.request().newBuilder();
            if (!TextUtils.isEmpty(userAgent)) {
                builder.addHeader("User-Agent", userAgent);
            }
           // if (!TextUtils.isEmpty(cookies)) {
                builder.addHeader("Cookie", "qh[360]=1");
                builder.addHeader("Cookie", "install_id=44267707161");
                builder.addHeader("Cookie", "ttreq=1$3f444d47ef856e20e0f985771f5f89ccdde0d70c");
                builder.addHeader("Cookie", "alert_coverage=32");
           // }
          //  builder.addHeader("Connection", "close");
            return chain.proceed(builder.build());
        };
        newBuilder.addNetworkInterceptor(requestInterceptor);
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(logLevel);
        newBuilder.addNetworkInterceptor(loggingInterceptor);
        //设置缓存路径跟大小
        newBuilder.cache(new Cache(new File(YZBaseConstants.getNetCacheDir()), CATCH_SIZE));
        newBuilder.connectTimeout(TIMEOUT_VALUE, TimeUnit.MILLISECONDS);
        newBuilder.readTimeout(TIMEOUT_VALUE, TimeUnit.MILLISECONDS);
        newBuilder.writeTimeout(TIMEOUT_VALUE, TimeUnit.MILLISECONDS);
        newBuilder.retryOnConnectionFailure(true);

       // return newBuilder.build();
       return RetrofitUrlManager.getInstance().with(newBuilder)
               .cookieJar(cookieJar)
                .build();
    }

    public <T> T get(Class<T> apiClass){
        if (apiMap==null){
            apiMap = new HashMap<>();
        }
        T obj = (T) apiMap.get(apiClass);
        if (obj==null){
            obj  = retrofit.create(apiClass);
            apiMap.put(apiClass,obj);
        }
        return obj;
    }

//    public static void setServer(String serverAddress){
//        Context context = contextRef.get();
//        YZServerConstants.setServer(context,serverAddress);
//        if (instance!=null){
//            instance.createClient();
//        }
//    }

//    public static String getServer(){
//        Context context = contextRef.get();
//        return YZServerConstants.getServer(context);
//    }

//    public static void setCookie(List<String> cookies){
//        Context context = contextRef.get();
//        StringBuilder strCookies = new StringBuilder();
//        if (cookies != null && cookies.size() > 0) {
//            for (String cookie:cookies){
//                strCookies.append(cookie).append(";");
//            }
//        }
//        YZServerConstants.setCookie(context,strCookies.toString());
//        if (instance!=null){
//            instance.cookies = strCookies.toString();
//        }
//    }

//    public static String getCookie(){
//        Context context = contextRef.get();
//        return YZServerConstants.getCookie(context);
//    }
//
//    public static String getPicUrl(String id){
//       return getServer()+YZServerConstants.API_VERSION+YZServerConstants.PIC_URL+id;
//    }
//
//    public static String getFileUrl(String id){
//        return getServer()+YZServerConstants.API_VERSION+YZServerConstants.FILE_URL+id;
//    }

}
