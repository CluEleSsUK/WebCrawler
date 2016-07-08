package com.patrick.main;

import com.patrick.utils.LinkTree;
import com.patrick.utils.TreePrinter;
import com.patrick.web.ContentResolver;
import com.patrick.web.WebContentResolver;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class Application {

    public static void main(String[] args) {
        String domain = args[0];
        String startingUrl = args[1];

        CloseableHttpClient client = HttpClientBuilder.create().build();
        ContentResolver contentResolver = new WebContentResolver(client);
        WebCrawler webCrawler = new WebCrawler(contentResolver, domain);

        try {
            LinkTree<String> siteMap = webCrawler.crawl(startingUrl);
            TreePrinter.print(siteMap);
        } catch(Exception e) {
            System.out.println("The requested URL does not exist or is not responding");
            e.printStackTrace();
        }
    }
}
