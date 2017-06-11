package example.thread;


import example.Crawler;
import example.CrawlerWrapper;

import java.util.ArrayList;
import java.util.List;

public class Monitor extends Thread {

    private List<String> listUrl;
    private Crawler crawler;
    private List<CrawlerWrapper> listCrawlerWrapper;

    public Monitor(List<String> listUrl, Crawler crawler){
        this.listUrl = listUrl;
        this.crawler = crawler;
    }

    public void run() {
        listCrawlerWrapper = new ArrayList<>();
        for(String e: listUrl){
            CrawlerWrapper tmp = new CrawlerWrapper();
            tmp.setUrl(e);
            tmp.setCrawler(this.crawler);
            listCrawlerWrapper.add(tmp);
            tmp.start();
        }
    }
}
