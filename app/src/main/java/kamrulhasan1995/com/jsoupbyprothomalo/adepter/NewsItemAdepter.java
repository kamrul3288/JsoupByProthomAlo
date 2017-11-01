package kamrulhasan1995.com.jsoupbyprothomalo.adepter;


import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import kamrulhasan1995.com.jsoupbyprothomalo.R;
import kamrulhasan1995.com.jsoupbyprothomalo.model.NewsItem;

public class NewsItemAdepter extends RecyclerView.Adapter<NewsItemAdepter.NewsItemViewHolder>{

    private ArrayList<NewsItem> newsItemList = new ArrayList<>();
    private Context context;

    public NewsItemAdepter(ArrayList<NewsItem> newsItemList, Context context) {
        this.newsItemList = newsItemList;
        this.context = context;
    }

    @Override
    public NewsItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_list,parent,false);
        return new NewsItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsItemViewHolder holder, int position) {
        final NewsItem item = newsItemList.get(position);
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/kalpurush.ttf");
        holder.title.setTypeface(typeface);
        holder.title.setText(item.getTitle());
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"http://www.prothom-alo.com"+item.getNewsUrl(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsItemList.size();
    }

    public class NewsItemViewHolder extends RecyclerView.ViewHolder{

        private TextView title;
        public NewsItemViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.mews_item_newsTitle);
        }
    }
}
