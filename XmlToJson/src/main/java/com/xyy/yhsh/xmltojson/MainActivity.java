package com.xyy.yhsh.xmltojson;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends Activity {
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String lrc = (String) msg.obj;//获取到的xml歌词数据
            showToast(lrc);
            et_xml.setText(lrc);
        }
    };
    private EditText et_xml;
    private EditText et_json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        et_xml = findViewById(R.id.et_xml);
        et_json = findViewById(R.id.et_json);
        Button bt_xml_json = findViewById(R.id.bt_xml_json);
        initXMLData();
        bt_xml_json.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取编辑框里面的xml歌词文本数据
                String trim = et_xml.getText().toString().trim();
                if (!TextUtils.isEmpty(trim)) {
                    //不为空，就将xml转换成json格式数据
//                    showToast(xml2JSON(trim));
                    if (trim.contains("<!")) {
                        String replace = trim.replace("<!", "");
                        String substring_1 = replace.substring(40, 1373);
                        String substring_2 = replace.substring(1374, 1382);
                        et_json.setText(xml2JSON(substring_1 + substring_2));
                        Log.e("打印xml", xml2JSON(substring_1 + substring_2));
                    } else {
                        et_json.setText(xml2JSON(trim));
                    }
                } else {
                    showToast("xml编辑框为空，请输入xml格式文本");
                }
            }
        });
    }

    private void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }

    private void initXMLData() {
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://music.qq.com/miniportal/static/lyric/14/101369814.xml");
                    HttpURLConnection hc = (HttpURLConnection) url.openConnection();
                    hc.setRequestMethod("GET");
                    hc.setConnectTimeout(20000);
                    hc.setReadTimeout(20000);
                    if (hc.getResponseCode() == 200) {
                        InputStream inputStream = hc.getInputStream();
                        String network_json = inputStream2String(inputStream);
                        Log.e("打印获取数据", network_json);
                        Message obtain = Message.obtain();
                        obtain.obj = network_json;
                        handler.sendMessage(obtain);//发送消息
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public String inputStream2String(InputStream in) throws IOException {
        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1; ) {
            out.append(new String(b, 0, n, "GB2312"));//将编码设置成GBK否则会乱码
        }
        return out.toString();
    }

    public String xml2JSON(String xml) {
        try {
            JSONObject obj = XML.toJSONObject(xml);
            return obj.toString();
        } catch (JSONException e) {
            System.out.println("xml->json失败" + e.getLocalizedMessage());
            return "";
        }
    }
}
