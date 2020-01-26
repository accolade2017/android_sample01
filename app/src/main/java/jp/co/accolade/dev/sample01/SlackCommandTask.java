package jp.co.accolade.dev.sample01;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import com.google.gson.Gson;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * ネットワーク接続タスク.
 * <pre>
 *     ネットワーク接続処理はメインスレッドと異なるスレッドで実行される必要がある。
 * </pre>
 */
public class SlackCommandTask extends AsyncTask<String, Integer, String> {

    /** 出力先テキストを弱参照で持つ. */
    private WeakReference<TextView> txtResult;

    /** コンストラクタ. */
    public SlackCommandTask(Context context) {
        super();
        MainActivity activity = (MainActivity)context;
        this.txtResult = new WeakReference<>((TextView)activity.findViewById(R.id.txtResult));
    }

    @Override
    protected String doInBackground(String... strings) {

        StringBuffer sb = new StringBuffer();
        // 基本はこの形だが、便利なユーティリティがある
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setDoOutput(true);

            // サーバーに送る値の設定
            OutputStream os = con.getOutputStream();
            PrintStream ps = new PrintStream(os);
            ps.print(strings[1]);

            // 結果の読み取り
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
            String line = null;
            while( (line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    // 接続終了時の処理
    @Override
    protected void onPostExecute(String jsonString) {
        Gson gson = new Gson();
        Result result = gson.fromJson(jsonString, Result.class);
        txtResult.get().setText(result.getText()); // 値を設定する
    }
}
