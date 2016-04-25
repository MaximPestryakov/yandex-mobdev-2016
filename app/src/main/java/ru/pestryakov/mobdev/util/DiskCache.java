package ru.pestryakov.mobdev.util;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Scanner;

public class DiskCache {
  private Context context;

  public DiskCache(Context context) {
    this.context = context;
  }

  public void put(String key, String value) {
    FileOutputStream outputStream;
    try {
      outputStream = context.openFileOutput(String.valueOf(key.hashCode()), Context.MODE_PRIVATE);
      outputStream.write(value.getBytes());
      outputStream.close();
    } catch (Exception e) {
    }
  }

  public String get(String key) {
    FileInputStream inputStream;
    try {
      StringBuffer data = new StringBuffer();
      inputStream = context.openFileInput(String.valueOf(key.hashCode()));
      Scanner input = new Scanner(inputStream);
      while (input.hasNextLine()) {
        data.append(input.nextLine());
        if (input.hasNextLine())
          data.append('\n');
      }
      inputStream.close();
      return String.valueOf(data);
    } catch (Exception e) {
    }
    return null;
  }
}
