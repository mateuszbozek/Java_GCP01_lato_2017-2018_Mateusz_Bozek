package example;

import example.exception.CrolwerException;

import java.io.IOException;


public class CrawlerWrapper extends Thread {
    String url;
    Crawler crawler;

    public void setUrl(String url)
    {
        this.url = url;
    }

    public void setCrawler(Crawler crawler)
    {
        this.crawler = crawler;
    }

    public void run()
    {
        this.crawler.setUrl(url);
        try
        {
            this.crawler.run();
        }
        catch (CrolwerException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
