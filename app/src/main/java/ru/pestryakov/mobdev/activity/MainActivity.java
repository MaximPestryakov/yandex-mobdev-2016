package ru.pestryakov.mobdev.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    makeRequest();
  }

  public void makeRequest() {
    RequestQueue queue = Volley.newRequestQueue(this);
    String url = "http://download.cdn.yandex.net/mobilization-2016/artists.json";
    StringRequest request =
        new Utf8StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
          @Override
          public void onResponse(String response) {
            Genson genson = new Genson();
            Artist[] artists = genson.deserialize(response, Artist[].class);
            initializeAdapter(artists);
          }
        }, new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {

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