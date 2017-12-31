package com.example.mohamed.popularmoviesstage1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MovieTrailers extends AppCompatActivity {

    ListView tlistView ;
    ArrayList movieKeys ;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_trailers);
        tlistView = (ListView)findViewById(R.id.tlistview);
        Bundle b = getIntent().getExtras();
        if (b != null)
            movieKeys = b.getStringArrayList("movieKeys");
        tlistView.setAdapter(new TrailersAdapter(context,movieKeys));
    }
    private class TrailersAdapter extends BaseAdapter{
        Context context;
        ArrayList movieKeys ;

        public TrailersAdapter(Context context, ArrayList movieKeys) {
            this.context = context;
            this.movieKeys = movieKeys;
        }

        @Override
        public int getCount() {
            return movieKeys.size();
        }

        @Override
        public Object getItem(int i) {
            return movieKeys.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            View item = getLayoutInflater().inflate(R.layout.trailers,null);
            ImageView imageView = (ImageView)item.findViewById(R.id.imageView5);
            TextView textView = (TextView)item.findViewById(R.id.textView5);
            textView.setText("Trailer"+(i+1));
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    playTrailer(movieKeys.get(i).toString());
                }
            });
            return item;
        }
    }
    public void playTrailer(String s){
        String url = "https://www.youtube.com/watch?v="+s;

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
