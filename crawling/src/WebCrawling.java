package crawling.src;

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
        NewsPage.printWords();
    }

    void getNewsPageURL() {
        new StopWord();
        
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

    //단어 횟수를 담을 HashMap
    static HashMap<String, Integer> map = new HashMap<>();

    private Document doc;
    private String URL;
    private String title;
    private String journalist;
    private String content;

    NewsPage(String URL) {
        this.URL = URL;

        try {
            doc = Jsoup.connect(URL).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        title = doc.title();
        journalist = doc.select("em.media_end_head_journalist_name").text();
        content = doc.select("article#dic_area").text();

        getWords();
        printNewsInfo();
    }

    public void getWords() {
        HashSet<String> stopWord = StopWord.getHash();
        
        Iterator<String> it = stopWord.iterator();
        while(it.hasNext()) {
            content = content.replaceAll(it.next(), "");
        }

        for(String s : content.split(" ")) {
            map.put(s, map.getOrDefault(s, 0) + 1);
        }

        //불용어 확인
        // Iterator<String> it = stopWord.iterator();
        // while(it.hasNext())
        //     System.out.println(it.next());
    }

    static void printWords() {
        System.out.println("많이 사용된 단어: ");
        for(String s : map.keySet()) {
            if(map.get(s) >= 10)
                System.out.print(s + ": " + map.get(s) + "회, ");
        }
    }

    void printNewsInfo() {
        System.out.println("Title: " + title);
        System.out.println("Journalist: " + journalist);
        System.out.println("URL : " + URL);
        System.out.println("*****************************");
    }
}