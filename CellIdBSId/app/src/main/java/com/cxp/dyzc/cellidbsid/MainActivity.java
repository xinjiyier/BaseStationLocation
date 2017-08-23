package com.cxp.dyzc.cellidbsid;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cxp.dyzc.cellidbsid.utils.GetIdUtil;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int UPDATE_ADDRESS = 1;
    private GetIdUtil gu;
    private TextView tvShowResult;
    //private OkHttpClient client = new OkHttpClient();

    private String address;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case UPDATE_ADDRESS:
                    tvShowResult.setText(address);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gu = new GetIdUtil(this);
        initViews();
    }

    private void initViews() {
        tvShowResult = (TextView) findViewById(R.id.tv_show_result);
        findViewById(R.id.bt_get_cellid).setOnClickListener(this);
        findViewById(R.id.bt_get_address).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.bt_get_cellid:
                tvShowResult.setText(gu.getCellId());
                break;
            case R.id.bt_get_address:
                //getAddressFromInternet();
                break;
        }
    }

    /*private void getAddressFromInternet() {
        Request request = new Request.Builder()
                .url("http://api.cellocation.com/cell/?mcc=460&mnc=13824&lac=1&ci=56769&output=xml")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                address = "访问网络失败！";
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                Headers responseHeaders = response.headers();
                for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }

                System.out.println(response.body().string());
                address = response.body().string();
                handler.sendEmptyMessage(UPDATE_ADDRESS);
            }
        });
    }*/

}
