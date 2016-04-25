package ru.pestryakov.mobdev.activity;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import ru.pestryakov.mobdev.R;
import ru.pestryakov.mobdev.model.Artist;

public class ArtistInfoActivity extends AppCompatActivity {
  private Toolbar toolbar;
  private Artist artist;
  private TextView description;
  private ImageView cover;
  private TextView link;
  private TextView artistGenres;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_artist_info);
    artist = (Artist) getIntent().getSerializableExtra("artist");
    toolbar = (Toolbar) findViewById(R.id.artist_toolbar);
    toolbar.setTitle(artist.getName());
    setTitle(artist.getName());
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      getWindow().setStatusBarColor(Color.TRANSPARENT);
    }
    description = (TextView) findViewById(R.id.description);
    description.setText(artist.getDescription());
    link = (TextView) findViewById(R.id.link);
    if (artist.getLink() == null)
      link.setVisibility(View.GONE);
    else
      link.setText(artist.getLink());
    artistGenres = (TextView) findViewById(R.id.artist_genres);
    String genres = "Genres: ";
    for (String genre : artist.getGenres()) {
      if (!genres.equals("Genres: ")) {genres += ", ";}
      genres += genre;
    }
    artistGenres.setText(genres);
    cover = (ImageView) findViewById(R.id.cover_big);
    loadCover();
  }
  private void loadCover() {
    Point size = new Point();
    getWindowManager().getDefaultDisplay().getSize(size);
    cover.setMaxHeight(size.x);
    Picasso.with(this).load(artist.getCover().getBig()).into(cover, new Callback() {
      @Override
      public void onSuccess() {}
      @Override
      public void onError() {
        Snackbar.make(findViewById(R.id.artist_coordinator_layout), R.string.internet_error, Snackbar.LENGTH_INDEFINITE).setAction(R.string.retry, new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            loadCover();
          }
        }).show();
      }
    });
  }
}
