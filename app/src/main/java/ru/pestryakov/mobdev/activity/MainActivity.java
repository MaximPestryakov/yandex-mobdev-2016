package ru.pestryakov.mobdev.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.owlike.genson.Genson;

import ru.pestryakov.mobdev.R;
import ru.pestryakov.mobdev.adapter.ArtistListAdapter;
import ru.pestryakov.mobdev.model.Artist;
import ru.pestryakov.mobdev.util.Utf8StringRequest;

public class MainActivity extends AppCompatActivity {
  private Toolbar toolbar;
  private SwipeRefreshLayout swipeRefresh;
  private LinearLayout internetError;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    toolbar = (Toolbar) findViewById(R.id.main_toolbar);
    setSupportActionBar(toolbar);
    internetError = (LinearLayout) findViewById(R.id.internet_error);
    swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
    swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
    swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        makeRequest();
      }
    });
    swipeRefresh.post(new Runnable() {
      @Override
      public void run() {
        makeRequest();
      }
    });
  }

  public void makeRequest() {
    swipeRefresh.setVisibility(View.VISIBLE);
    swipeRefresh.setRefreshing(true);
    internetError.setVisibility(View.INVISIBLE);
    RequestQueue queue = Volley.newRequestQueue(this);
    String url = "http://download.cdn.yandex.net/mobilization-2016/artists.json";
    StringRequest request =
        new Utf8StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
          @Override
          public void onResponse(String response) {
            Genson genson = new Genson();
            Artist[] artists = genson.deserialize(response, Artist[].class);
            initializeAdapter(artists);
            swipeRefresh.setRefreshing(false);
          }
        }, new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
            swipeRefresh.setRefreshing(false);
            swipeRefresh.setVisibility(View.INVISIBLE);
            internetError.setVisibility(View.VISIBLE);
            Snackbar.make(findViewById(R.id.main_coordinator_layout), R.string.internet_error, Snackbar.LENGTH_INDEFINITE).setAction(R.string.retry, new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                makeRequest();
              }
            }).show();
          }
        });
    queue.add(request);
  }

  public void initializeAdapter(Artist[] artists) {
    RecyclerView artistListView = (RecyclerView) findViewById(R.id.artist_list);
    if (artistListView != null) {
      artistListView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
      artistListView.setAdapter(new ArtistListAdapter(this, artists));
    }
  }
}