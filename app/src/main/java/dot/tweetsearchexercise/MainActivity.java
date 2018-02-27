package dot.tweetsearchexercise;

import android.content.Intent;
import android.nfc.Tag;
import android.os.AsyncTask;
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
    MyTwitterTask myTwitterTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listview);

        initializeCb();
        //

        myTwitterTask = new MyTwitterTask();
        myTwitterTask.execute("google");
        //getTweets("google");
    }



    protected void initializeCb()
    {
        cb.setOAuthAuthenticationURL("https://api.twitter.com/oauth/request_token");

        cb.setOAuthConsumerKey(getString(R.string.consumername));
        cb.setOAuthConsumerSecret(getString(R.string.consumersecret));
        cb.setOAuthAccessToken(getString(R.string.accesstoken));
        cb.setOAuthAccessTokenSecret(getString(R.string.accessecret));
    }


    private class MyTwitterTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try
            {
                if(twitter != null)
                {
                }
                else
                {
                    twitter = new TwitterFactory(cb.build()).getInstance();
                }

                String user = params[0];

                getTweets(user);
                return "OK";
            }
            catch (TwitterException e) {
                e.printStackTrace();
                return "Error";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            printTweets();
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(Void... values) {}


        protected void getTweets(String user) throws TwitterException {
            Paging paging = new Paging(1, 20);

            statuses = twitter.getUserTimeline(user, paging);

            Log.d("TWITTER SAYS: ", "Total: "+statuses.size());
        }

        protected void printTweets()
        {
            String[] listItems = new String[statuses.size()];

            for(int i = 0; i < statuses.size(); i++)
            {
                twitter4j.Status status = statuses.get(i);
                listItems[i] = status.getText();
            }

            ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, listItems);

            listView.setAdapter(adapter);
        }
    }

}
