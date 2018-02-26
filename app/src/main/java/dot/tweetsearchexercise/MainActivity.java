package dot.tweetsearchexercise;

import android.content.Intent;
import android.nfc.Tag;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.*;
import twitter4j.*;
import twitter4j.conf.*;

public class MainActivity extends AppCompatActivity {
    ConfigurationBuilder cb = new ConfigurationBuilder();

    Twitter twitter;
    List<Status> statuses = null;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listview);

        if(listView != null)
        {}
        else
        {
            Log.d("VOICE says", "listview is null");
        }

        initializeCb();
        twitter = new TwitterFactory(cb.build()).getInstance();

        getTweets("google");
    }

    protected void getTweets(String user)
    {
        Paging paging = new Paging(1, 20);

        try {
            statuses = twitter.getUserTimeline(user, paging);
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        Log.d("TWITTER SAYS: ", "Total: "+statuses.size());
        printTweets();
    }

    protected void printTweets()
    {
        //String[] stringArray;

        String[] listItems = new String[statuses.size()];

        for(int i = 0; i < statuses.size(); i++)
        {
            Status status = statuses.get(i);
            listItems[i] = status.getText();
            // status.toString();
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems);

        listView.setAdapter(adapter);

        //&&listView.setAdapter();

        /*

        */
    }

    protected void initializeCb()
    {
        cb.setOAuthAuthenticationURL("https://api.twitter.com/oauth/request_token");

        cb.setOAuthConsumerKey(getString(R.string.consumername));
        cb.setOAuthConsumerSecret(getString(R.string.consumersecret));
        cb.setOAuthAccessToken(getString(R.string.accesstoken));
        cb.setOAuthAccessTokenSecret(getString(R.string.accessecret));
    }

}
