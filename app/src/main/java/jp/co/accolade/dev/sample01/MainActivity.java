package jp.co.accolade.dev.sample01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import jp.co.accolade.dev.sample01.cnst.Constellation;

/**
 * メイン画面.
 */
public class MainActivity extends AppCompatActivity {

    /** 選択値. */
    private String selectedValue;

    // Activity初期化時の処理
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 選択肢を作る
        List<String> list = new ArrayList<>();
        for (Constellation c : Constellation.values()) {
            list.add(c.name);
        }
        ArrayAdapter<String> selects = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list);
        Spinner spn = this.findViewById(R.id.spinner);
        spn.setOnItemSelectedListener(new OnItemSelectedListener(){
            // 未選択だった場合 - 今回は考慮不要
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }

            // 選択されたタイミングで値を保持.
            @Override
            public void onItemSelected(AdapterView parent, View view, int pos, long id) {
                selectedValue = (String)parent.getItemAtPosition(pos);
                Log.d("selected", selectedValue);
            }
        });
        spn.setAdapter(selects);
    }

    /**
     * ボタンクリック時の処理.
     * @param view
     */
    public void onClick_btn_run(View view) {
        SlackCommandTask task = new SlackCommandTask(this); // 自作した取得クラス
        String value = null;
        try {
            value = "text=" + URLEncoder.encode(this.selectedValue, "UTF-8");
        } catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
        }
        // ULRとパラメーターを渡す
        task.execute("https://script.google.com/macros/s/AKfycbx0GU7BalY-E_OJ5M3sImqHa98ya6EzakauNqb2GlzCrICcWNCS/exec", value);
        return;
    }
}
