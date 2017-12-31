package com.example.mohamed.popularmoviesstage1;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.mohamed.popularmoviesstage1.FavMovies.reviewUrl1;
import static com.example.mohamed.popularmoviesstage1.FavMovies.reviewUrl2;
import static com.example.mohamed.popularmoviesstage1.R.id.imagView;

public class Details extends AppCompatActivity  {


    ImageView imageView;
    TextView tx;
    TextView tx2;
    TextView tx3;
    TextView tx4;
    ImageView showTrailers ;
    Button showReviews ;
    ArrayList movieKeys = new ArrayList();
    ArrayList movieReviewsUrl= new ArrayList();
    ArrayList movieData =new ArrayList();
    String originalTitle ;
    String plotSynopsis ;
    String userRating ;
    String releaseDate ;
    String poster_path ;
    MediaController ctlr ;
    WebView webView ;
    VideoView vv;
    String id ;
    String URL = "com.example.mohamed.popularmoviesstage1/favmovies";
    HashMap<String,String> currentMovie = new HashMap<>();
    ListView lv ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        tx = (TextView) findViewById(R.id.textView);
        tx2 = (TextView) findViewById(R.id.textView2);
        tx3 = (TextView) findViewById(R.id.textView3);
        tx4 = (TextView) findViewById(R.id.textView4);
        imageView = (ImageView) findViewById(imagView);
        vv = (VideoView) findViewById(R.id.vv);
        ctlr = new MediaController(this);

        showTrailers = (ImageView)findViewById(R.id.imageView4);
        showReviews = (Button)findViewById(R.id.button);


      Bundle  b = getIntent().getExtras();
        if (b != null){

            id = (String)b.get("id");
            originalTitle = (String)b.get("originalTitle");
            plotSynopsis = (String)b.get("plotSynopsis");
            userRating = (String)b.get("userRating");
            releaseDate = (String)b.get("releaseDate");
            poster_path = (String)b.get("poster_path");
            movieKeys = b.getStringArrayList("movieKeys");
            movieReviewsUrl = b.getStringArrayList("movieReviewsUrl");
            movieData.add(movieKeys);
            movieData.add(movieReviewsUrl);
            currentMovie.put("id",id);
            currentMovie.put("originalTitle",originalTitle);
            currentMovie.put("plotSynopsis",plotSynopsis);
            currentMovie.put("userRating",userRating);
            currentMovie.put("releaseDate",releaseDate);
            currentMovie.put("poster_path",poster_path);
            currentMovie.put("movieReviewsUrl",movieReviewsUrl.toString());
            currentMovie.put("movieKeys",movieKeys.toString());

            tx.setText("Original title : "+originalTitle);
            tx2.setText("PlotSynopsis : "+plotSynopsis);
            tx3.setText("UserRating : "+userRating);
            tx4.setText("ReleaseDate : "+releaseDate);
            Picasso.with(getApplicationContext())
                    .load("https://image.tmdb.org/t/p/w500/"+poster_path)
                    .into(imageView);

        }
        else
            Toast.makeText(getApplicationContext(),"Empty",Toast.LENGTH_LONG).show();

        showReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b =  new Bundle();
                b.putStringArrayList("movieReviewsUrl",movieReviewsUrl);
                Intent intent = new Intent(Details.this,MovieReviews.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        showTrailers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b =  new Bundle();
                b.putStringArrayList("movieKeys",movieKeys);
                Intent intent = new Intent(Details.this,MovieTrailers.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }
    public void saveFav(View view){

        ContentValues cv = new ContentValues();
        cv.put(FavMovies.ID,currentMovie.get("id"));
        cv.put(FavMovies.originalTitle,currentMovie.get("originalTitle"));
        cv.put(FavMovies.plotSynopsis,currentMovie.get("plotSynopsis"));
        cv.put(FavMovies.userRating,currentMovie.get("userRating"));
        cv.put(FavMovies.releaseDate,currentMovie.get("releaseDate"));
        cv.put(FavMovies.poster_path,currentMovie.get("poster_path"));
        cv.put(FavMovies.key1,currentMovie.get("trailerKey1"));
        cv.put(FavMovies.key2,currentMovie.get("trailerKey2"));
        cv.put(reviewUrl1,currentMovie.get("reviewUrl1"));
        cv.put(reviewUrl2,currentMovie.get("reviewUrl2"));
        Uri uri = getContentResolver().insert(FavMovies.CONTENT_URI, cv);
        if(uri != null) {
            Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
        }

    }
    public void playTrailer(String s){
        String url = "https://www.youtube.com/watch?v="+s;

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }



    public class listViewAdapter extends BaseAdapter{
        Context context;
        ArrayList movieKeys;

        public listViewAdapter(Context context, ArrayList movieKeys) {
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
        public View getView(final int i, View item, ViewGroup viewGroup) {
            LayoutInflater inflater = getLayoutInflater() ;
            View item1 = inflater.inflate(R.layout.trailers,viewGroup,false);
         //   ImageView trailer = (ImageView)item1.findViewById(R.id.imageView2);
            TextView trailerText = (TextView) item1.findViewById(R.id.textView5);
            trailerText.setText("trailer"+(i+1));
       /*     trailer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    playTrailer(movieKeys.get(i).toString());
                }
            });
/*
            review.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    showReview(movieReviewsUrl.get(i).toString()
                    );
                }
            });
*/
            return item1 ;

        }
    }
}
