package com.loono.android.moviestage;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageAdapter extends BaseAdapter{
    private Context context;

    public ImageAdapter(Context context) {
        this.context = context;
        new GetSelectedMovies().execute("");
    }

    @Override
    public int getCount() {
        return 12;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
        } else {
            imageView = (ImageView) convertView;
        }

//        imageView.setImageResource(mThumbIds[position]);
        Picasso.with(context).load("http://image.tmdb.org/t/p/w500/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg")
                .into(imageView);
        return imageView;
    }

    private class GetSelectedMovies extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String res = null;
            try {
                File file = new File("APIkey");
                BufferedReader br = new BufferedReader(new FileReader(file));
                String key = br.readLine();
                URL url = new URL("http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key="+key);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                InputStreamReader inputStream = new InputStreamReader(connection.getInputStream());
                char[] buffer = new char[1024];
                inputStream.read(buffer);
                res = new String(buffer);
            } catch (Exception exception) {
                Log.e("inback", exception.toString());
            }
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            String res = result;
        }
    }
}
