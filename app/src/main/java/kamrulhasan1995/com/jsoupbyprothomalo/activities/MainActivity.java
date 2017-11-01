package kamrulhasan1995.com.jsoupbyprothomalo.activities;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import kamrulhasan1995.com.jsoupbyprothomalo.R;
import kamrulhasan1995.com.jsoupbyprothomalo.adepter.NewsItemAdepter;
import kamrulhasan1995.com.jsoupbyprothomalo.model.NewsItem;

public class MainActivity extends AppCompatActivity {

    private ImageView newsImageIV;
    private TextView newsTitleTV,newsDetailsTV;
    private RecyclerView recyclerView;

    private NewsItemAdepter adepter;
    private ArrayList<NewsItem> newsItemList  = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       initializeView();
        downloadData();

    }

    private void downloadData() {
        final String url = "http://www.prothom-alo.com/international/article/1356221/%E0%A6%85%E0%A6%A4%E0%A6%BF%E0%A6%B0%E0%A6%BF%E0%A6%95%E0%A7%8D%E0%A6%A4-%E0%A6%95%E0%A6%BE%E0%A6%9C-%E0%A6%95%E0%A6%B0%E0%A7%87-%E0%A6%9A%E0%A6%BE%E0%A6%95%E0%A6%B0%E0%A6%BF-%E0%A6%B9%E0%A6%BE%E0%A6%B0%E0%A6%BE%E0%A6%B2%E0%A7%87%E0%A6%A8-%E0%A6%A4%E0%A6%BF%E0%A6%A8%E0%A6%BF";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/kalpurush.ttf");
                Document document = Jsoup.parse(response);

                Elements newsImageUrlElement = document.select("[property=og:image]");
                Elements resultLink = document.select("[itemprop=articleBody] p");

                String newsTitle = document.title();
                newsTitleTV.setTypeface(typeface);
                newsTitleTV.setText(newsTitle);

                //-----------------get new description-----------
                StringBuilder builder = new StringBuilder();
                for (Element element : resultLink){
                   String newsDescription = element.text();
                   builder.append(newsDescription+"\n\n");
                }
                if (builder != null){
                    newsDetailsTV.setTypeface(typeface);
                    newsDetailsTV.setText(builder.toString());
                }

                String newsImageUrl = newsImageUrlElement.attr("content");
                Picasso.with(getApplicationContext()).load(newsImageUrl).into(newsImageIV);

                Elements lastNewsAllData = document.select("div.each_tab.tab_latest.oh.db ul li");

                //--------------get last news url---------------
                ArrayList<String>lastNewsUrlList = new ArrayList<>();
                Elements lastNewsImageUrls = lastNewsAllData.select(" > a");
                for (int i = 0 ; i<lastNewsImageUrls.size() ;i++){
                    String lastNewsImageUrl = lastNewsImageUrls.get(i).attr("href");
                    lastNewsUrlList.add(lastNewsImageUrl);
                }

                //-----------get last news title---------------------
                ArrayList<String>lastNewsTitleList = new ArrayList<>();
                Elements lastNewsTitles = lastNewsAllData.select("span.tab_list_title");
                for (int i = 0 ; i<lastNewsTitles.size() ; i++){
                    String  lastNewsTitle = lastNewsTitles.get(i).text();
                    lastNewsTitleList.add(lastNewsTitle);
                }

                //--------------add data on array list------------------
                for (int i = 0 ; i<lastNewsTitleList.size() ; i++){
                    NewsItem item = new NewsItem(lastNewsTitleList.get(i),lastNewsUrlList.get(i));
                    newsItemList.add(item);
                }

                //----------------------set adepter-------------------------
                adepter = new NewsItemAdepter(newsItemList,getApplicationContext());
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(adepter);
                adepter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(this).add(request);
    }

    private void initializeView() {
        newsImageIV = findViewById(R.id.newsImage);
        newsTitleTV = findViewById(R.id.newsTitle);
        newsDetailsTV = findViewById(R.id.newsDetails);
        recyclerView = findViewById(R.id.recyclerView);
    }
}
