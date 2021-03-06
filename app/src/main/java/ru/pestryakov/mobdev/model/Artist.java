package ru.pestryakov.mobdev.model;

import java.io.Serializable;

public class Artist implements Serializable {
  private int id;
  private String name;
  private String[] genres;
  private int tracks;
  private int albums;
  private String link;
  private String description;
  private Cover cover;

  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String[] getGenres() {
    return genres;
  }
  public void setGenres(String[] genres) {
    this.genres = genres;
  }
  public int getTracks() {
    return tracks;
  }
  public void setTracks(int tracks) {
    this.tracks = tracks;
  }
  public int getAlbums() {
    return albums;
  }
  public void setAlbums(int albums) {
    this.albums = albums;
  }
  public String getLink() {
    return link;
  }
  public void setLink(String link) {
    this.link = link;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description.substring(0, 1).toUpperCase() + description.substring(1);
  }
  public Cover getCover() {
    return cover;
  }
  public void setCover(Cover cover) {
    this.cover = cover;
  }

  public static class Cover implements Serializable {
    private String small;
    private String big;

    public String getSmall() {
      return small;
    }
    public void setSmall(String small) {
      this.small = small;
    }
    public String getBig() {
      return big;
    }
    public void setBig(String big) {
      this.big = big;
    }
  }
}
