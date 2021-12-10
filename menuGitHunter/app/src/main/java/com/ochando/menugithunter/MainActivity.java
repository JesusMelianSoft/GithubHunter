package com.ochando.menugithunter;

import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ochando.menugithunter.recyclerview.RepoAdapter;
import com.ochando.menugithunter.utis.NetworkUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements RepoAdapter.ListItemClickListener {

    EditText searchBox;
    TextView urlDisplay;
    TextView searchResult;
    TextView errorMessageDisplay;
    ProgressBar requestProgress;

    RecyclerView repoList;
    RepoAdapter adapter;

    Toast clickToast;

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Log.e("MainActivity","Se ha pulsado sobre " + clickedItemIndex);
        if(clickToast!=null){
            clickToast.cancel();
        }
        String toastMessage = "Se ha pulsado sobre " + clickedItemIndex;
        clickToast = Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);
        clickToast.show();
    }

    public class GithudQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            requestProgress.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String gitHubSearchResults = null;

            try {
                gitHubSearchResults = NetworkUtils.getResponseFromHttpURl(searchUrl);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return gitHubSearchResults;
        }

        @Override
        protected void onPostExecute(String s) {
            requestProgress.setVisibility(View.INVISIBLE);
            if (s != null && !s.equals("")) {
                showJsonData();
                searchResult.setText(s);
            } else {
                showErrorMessage();
            }
            Log.i("MainActivity", "el post");
            super.onPostExecute(s);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        int itemID = item.getItemId();
        if (itemID == R.id.search){
            Log.i("MainActivity", "el usuario ha pulsado seach");
            Context context = MainActivity.this;
            Toast.makeText(context,R.string.search_pressed, Toast.LENGTH_LONG).show();
            URL githubUrl = null;
            try {
                githubUrl = NetworkUtils.buildUrl(searchBox.getText().toString());
                new GithudQueryTask().execute(githubUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }



           /* Esto no lo podemos hacer en la rama principal


           try {
                githubUrl = NetworkUtils.buildUrl(searchBox.getText().toString());
                urlDisplay.setText(githubUrl.toString());
                String response = NetworkUtils.getResponseFromHttpURl(githubUrl);
                Log.i("MainActivity",response);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
   */

        }
        return true;
    }

    private void showJsonData() {
        errorMessageDisplay.setVisibility(View.INVISIBLE);
        searchResult.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        searchResult.setVisibility(View.INVISIBLE);
        errorMessageDisplay.setVisibility(View.VISIBLE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchBox = (EditText) findViewById(R.id.search_box);
        urlDisplay = (TextView) findViewById(R.id.ur_display);
        errorMessageDisplay = (TextView) findViewById(R.id.error_message_display);

        repoList = (RecyclerView) findViewById(R.id.rv_responses);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        requestProgress = (ProgressBar) findViewById(R.id.request_progress);

        repoList.setLayoutManager(layoutManager);

        repoList.setHasFixedSize(true);

        adapter = new RepoAdapter(200, this);

        repoList.setAdapter(adapter);
    }
}