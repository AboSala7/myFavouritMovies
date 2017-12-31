package com.example.mohamed.popularmoviesstage1;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MovieReviews extends AppCompatActivity {

    ArrayList movieReviewsUrl=new ArrayList();
    ListView myListView ;
    WebView webView;
    Context context ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_reviews);
        myListView = (ListView)findViewById(R.id.rlistview);
        webView = (WebView)findViewById(R.id.wv2);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
            movieReviewsUrl = bundle.getStringArrayList("movieReviewsUrl");
        ReviewsAdapter myAdapter = new ReviewsAdapter(context,movieReviewsUrl);
        myListView.setAdapter(myAdapter);
    }
    private class ReviewsAdapter extends BaseAdapter{
        Context context;
        ArrayList movieReviewsUrl;

        public ReviewsAdapter(Context context, ArrayList movieReviewsUrl) {
            this.context = context;
            this.movieReviewsUrl = movieReviewsUrl;
        }

        @Override
        public int getCount() {
            return movieReviewsUrl.size();
        }

        @Override
        public Object getItem(int i) {
            return movieReviewsUrl.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            View item = getLayoutInflater().inflate(R.layout.reviews,null);
            Button button = (Button)item.findViewById(R.id.button3);
            button.setText("Review"+(i+1));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   showReview(movieReviewsUrl.get(i).toString());
                  //  showReview("https://www.tutorialspoint.com/android/android_list_view.htm");
                }
            });
            return item;
        }
    }
    public void showReview(String review){

        if (review!= null)
            webView.loadUrl(review);
    }
}
