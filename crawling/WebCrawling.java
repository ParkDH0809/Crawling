package crawling;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

public class WebCrawling {
    String naverComputerNewsURL;
    
    WebCrawling(String s) {
        naverComputerNewsURL = "https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid1=105&sid2=283";
        crawlingComputerPage();
    }

    void crawlingComputerPage() {
        Document doc;
        try {
            doc = Jsoup.connect(naverComputerNewsURL).get();

            Elements links = doc.select("dl > dt.photo > a");
            int n = 0;
            for(Element link : links) {
                System.out.println(++n + " " + link.attr("href"));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
