package ru.pestryakov.mobdev.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ru.pestryakov.mobdev.R;
import ru.pestryakov.mobdev.activity.ArtistInfoActivity;
import ru.pestryakov.mobdev.model.Artist;

public class ArtistListAdapter extends RecyclerView.Adapter<ArtistListAdapter.ViewHolder> {
  private Context context;
  private Artist[] artists;

  public ArtistListAdapter(Context context, Artist[] artists) {
    this.context = context;
    this.artists = artists;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.artist_list_item, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, int position) {
    final Artist artist = artists[position];
    final String url = artist.getCover().getSmall();
    holder.name.setText(artist.getName());
    String genres = "";
    for (String genre : artist.getGenres()) {
      if (!genres.isEmpty()) {genres += ", ";}
      genres += genre;
    }
    String short_info = "albums: " + artist.getAlbums() + ", tracks: " + artist.getTracks();
    holder.short_info.setText(short_info);
    holder.genres.setText(genres);
    Picasso.with(context).load(url).into(holder.cover);
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(context, ArtistInfoActivity.class);
        intent.putExtra("artist", artist);
        context.startActivity(intent);
      }
    });
  }

  @Override
  public int getItemCount() {
    return artists.length;
  }

  class ViewHolder extends RecyclerView.ViewHolder {
    private View itemView;
    private TextView genres;
    private TextView name;
    private TextView short_info;
    private ImageView cover;

    public ViewHolder(View itemView) {
      super(itemView);
      this.itemView = itemView;
      name = (TextView) itemView.findViewById(R.id.name);
      genres = (TextView) itemView.findViewById(R.id.genres);
      short_info = (TextView) itemView.findViewById(R.id.short_info);
      cover = (ImageView) itemView.findViewById(R.id.cover_small);
    }
  }
}

