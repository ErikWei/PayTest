package com.yxtek.paytest.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yxtek.paytest.R;
import com.yxtek.paytest.common.constant.Appconfig;
import com.yxtek.paytest.common.constant.PayType;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    /**
     *
     */
    private int payType = PayType.ALIPAY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_pay:
                startPay();
                break;
            case R.id.rb_alipay:
                payType = PayType.ALIPAY;
                break;
            case R.id.rb_wxpay:
                payType = PayType.WXPAY;
                break;
        }
    }

    private void initListener(){
        findViewById(R.id.btn_pay).setOnClickListener(this);
        findViewById(R.id.rb_alipay).setOnClickListener(this);
        findViewById(R.id.rb_wxpay).setOnClickListener(this);
    }

    private void startPay(){
        Log.i("","start pay");
        switch (payType){
            case PayType.ALIPAY:
                PayTask payTask = new PayTask(this);
                break;
            case PayType.WXPAY:
                IWXAPI iwxapi = WXAPIFactory.createWXAPI(this,null);
                iwxapi.registerApp(Appconfig.WX_APPID);

                PayReq request = new PayReq();
                request.appId = Appconfig.WX_APPID;
                request.partnerId = "1900000109";
                request.prepayId= "1101000000140415649af9fc314aa427";
                request.packageValue = "Sign=WXPay";
                request.nonceStr= "1101000000140429eb40476f8896f4c9";
                request.timeStamp= "1398746574";
                request.sign= "7FFECB600D7157C5AA49810D2D8F28BC2811827B";
                iwxapi.sendReq(request);

                break;
        }
    }
}
