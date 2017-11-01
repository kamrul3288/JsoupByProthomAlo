package kamrulhasan1995.com.jsoupbyprothomalo.model;


public class NewsItem {
    private String title;
    private String newsUrl;

    public NewsItem(String title, String newsUrl) {
        this.title = title;
        this.newsUrl = newsUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getNewsUrl() {
        return newsUrl;
    }
}
