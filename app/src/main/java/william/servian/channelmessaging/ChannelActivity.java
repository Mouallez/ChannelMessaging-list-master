package william.servian.channelmessaging;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by sacquind on 06/03/2018.
 */
public class ChannelActivity extends AppCompatActivity implements View.OnClickListener,OnDownloadListener
{
    private ListView listeVue;
    private Button btnValider;
    private EditText editText;
    SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);
        listeVue = (ListView) findViewById(R.id.listViewMessage);
        editText = (EditText) findViewById(R.id.editTextMessage);
        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        HttpPostHandler http = new HttpPostHandler();
        http.addOnDownloadListener(this);
        HashMap<String, String> paramsPost = new HashMap<>();
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        String id = (String) bd.get("channelId");
        String myToken = sharedPreferences.getString("accessToken", null);
        paramsPost.put("accesstoken", myToken);
        paramsPost.put("channelid", id);
        http.execute(new PostRequest("http://www.raphaelbischof.fr/messaging/?function=getmessages", paramsPost));
        btnValider = (Button) findViewById(R.id.buttonMessage);
        btnValider.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        System.out.println(editText.getText());
        HttpPostHandler http = new HttpPostHandler();
        http.addOnDownloadListener(this);
        HashMap<String, String> paramsPost = new HashMap<>();
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        String id = (String) bd.get("channelId");
        String myToken = sharedPreferences.getString("accessToken", null);
        paramsPost.put("accesstoken", myToken);
        paramsPost.put("channelid", id);
        paramsPost.put("message", editText.getText().toString());
        http.execute(new PostRequest("http://www.raphaelbischof.fr/messaging/?function=sendmessage", paramsPost));
    }

    @Override
    public void onDownloadComplete(String downloadedContent) {
        System.out.println(downloadedContent);
        Gson gson = new Gson();
        MessageReponse obj = gson.fromJson(downloadedContent, MessageReponse.class);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        for (Message msg : obj.getMessages()
                ) {
            arrayAdapter.add(msg.getMessage());

        }
        listeVue.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDownloadError(String error) {

    }

}
