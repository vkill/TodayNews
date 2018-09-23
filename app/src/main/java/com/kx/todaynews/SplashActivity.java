package com.kx.todaynews;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.PrimitiveIterator;

public class SplashActivity extends AppCompatActivity {
    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                finish();
            }
        },500);
    }

  //  http://ib.snssdk.com/service/2/device_register/?ac=wifi&channel=tengxun2&aid=13&app_name=news_article&version_code=691&version_name=6.9.1&device_platform=android&ab_client=a1%2Cc4%2Ce1%2Cf1%2Cg2%2Cf7&ab_feature=94563%2C102749&abflag=3&ssmix=a&device_type=Redmi+Note+4X&device_brand=xiaomi&language=zh&os_api=24&os_version=7.0&openudid=30f2074ddcee24da&manifest_version_code=691&resolution=1080*1920&dpi=480&update_version_code=69111&_rticket=1537629848909&plugin=0&rom_version=miui_v9_v9.6.2.0.ncfcnfd&tt_data=a&config_retry=b&ts=1537629848&as=a27555fa88e94b9ea64531&mas=008ad0c9f325aca73536095e649a377c9a67e40ce2660e4826
  // http://lf.snssdk.com/service/1/z_app_stats/?iid=44267707161&device_id=57548705831&ac=wifi&channel=tengxun2&aid=13&app_name=news_article&version_code=691&version_name=6.9.1&device_platform=android&ab_client=a1%2Cc4%2Ce1%2Cf1%2Cg2%2Cf7&ab_feature=94563%2C102749&abflag=3&device_type=Redmi+Note+4X&device_brand=xiaomi&language=zh&os_api=24&os_version=7.0&openudid=30f2074ddcee24da&manifest_version_code=691&resolution=1080*1920&dpi=480&update_version_code=69111&_rticket=1537629849662&_rticket=1537629849676&plugin=0&rom_version=miui_v9_v9.6.2.0.ncfcnfd&_apps=1&_recent=1&time_first_send_install_app=1537629849663&ssmix=a&ts=1537629849&as=a215f5fac949db2ed66184&mas=0099d92a31c36653ebf26be463928f0bab6a4406a242dce59c

}
