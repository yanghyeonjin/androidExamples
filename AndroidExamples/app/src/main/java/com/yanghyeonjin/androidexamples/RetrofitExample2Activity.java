package com.yanghyeonjin.androidexamples;

import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.yanghyeonjin.androidexamples.model.Contributor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitExample2Activity extends AppCompatActivity {

    private Toolbar tbRetrofitExample2;
    private FloatingActionButton fabRetrofitExample2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_example2);

        /* 아이디 연결 */
        tbRetrofitExample2 = findViewById(R.id.tb_retrofit_example2);
        fabRetrofitExample2 = findViewById(R.id.fab_retrofit_example2);


        setSupportActionBar(tbRetrofitExample2);


        fabRetrofitExample2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Super fast hello world", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        /* 동기 */
        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... voids) {
                GitHubService gitHubService = GitHubService.retrofit.create(GitHubService.class);
                Call<List<Contributor>> call = gitHubService.repoContributors("square", "retrofit");

                try {
                    return call.execute().body().toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                TextView tvRetrofitExample2 = findViewById(R.id.tv_retrofit_example2);
                tvRetrofitExample2.setText(s);
            }
        }.execute();

        /* 비동기 */
        /*GitHubService gitHubService = GitHubService.retrofit.create(GitHubService.class);
        Call<List<Contributor>> call = gitHubService.repoContributors("square", "retrofit");
        call.enqueue(new Callback<List<Contributor>>() {
            @Override
            public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {
                TextView tvRetrofitExample2 =  findViewById(R.id.tv_retrofit_example2);
                tvRetrofitExample2.setText(response.body().toString());
            }

            @Override
            public void onFailure(Call<List<Contributor>> call, Throwable t) {

            }
        });*/
    }

}
