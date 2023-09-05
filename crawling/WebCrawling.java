package crawling;

import java.io.IOException;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

public class WebCrawling {

    String naverNewsCategoryURL;
    NewsPage[] newsPages;

    WebCrawling(String s) {
        naverNewsCategoryURL = s;
        getNewsPageURL();
    }

    void getNewsPageURL() {
        Document doc;
        Elements links;

        try {

            doc = Jsoup.connect(naverNewsCategoryURL).get();
            links = doc.select("dl > dt.photo > a");

                        
            int i = 0;
            newsPages = new NewsPage[links.size()];
            for(Element link : links) {
                newsPages[i++] = new NewsPage(link.attr("href"));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class NewsPage {
    private Document doc;
    private String URL;
    private String title;
    private String journalist;

    //단어 횟수를 담을 HashMap
    HashMap<String, Integer> map;

    NewsPage(String URL) {
        this.URL = URL;

        try {
            doc = Jsoup.connect(URL).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        title = doc.title();
        journalist = doc.select("em.media_end_head_journalist_name").text();
    }

    public Document getDoc() {
        return this.doc;
    }

    public String getURL() {
        return this.URL;
    }

    public String getTitle() {
        return this.title;
    }

    public String getJournalist() {
        return this.journalist;
    }
}