package ru.pestryakov.mobdev.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ru.pestryakov.mobdev.R;
import ru.pestryakov.mobdev.model.Artist;

public class ArtistInfoActivity extends AppCompatActivity {
  private Artist artist;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_artist_info);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    Intent intent = getIntent();
    artist = (Artist) intent.getSerializableExtra("artist");
    setTitle(artist.getName());
    TextView description = (TextView) findViewById(R.id.description);
    description.setText(artist.getDescription());
    ImageView cover = (ImageView) findViewById(R.id.cover);
    Picasso.with(this).load(artist.getCover().getBig()).into(cover);
  }
}
