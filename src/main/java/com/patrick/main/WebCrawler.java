package com.patrick.main;

import com.patrick.utils.LinkTree;
import com.patrick.utils.Utils;
import com.patrick.web.ContentResolver;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebCrawler {

    private final ContentResolver contentResolver;
    private final String domain;

    private static final Set<String> visited = new HashSet<>();

    public WebCrawler(ContentResolver contentResolver, String domain) {
        this.contentResolver = contentResolver;
        this.domain = domain;
    }

    public LinkTree<String> crawl(String url) throws Exception {
        visited.add(url);
        return crawl(url, new LinkTree<>(url));
    }

    private LinkTree<String> crawl(String url, LinkTree<String> links) throws Exception {
        String content = contentResolver.getUrlAsString(url);
        if (content == null || content.isEmpty()) {
            return links;
        }
        return stringToTree(content, links);
    }

    public LinkTree<String> stringToTree(String content, LinkTree<String> parent) throws Exception {
        Pattern urlFromDomain = Pattern.compile("https?://[A-Za-z/.]+[ <\"]");
        Matcher matcher = urlFromDomain.matcher(content);

        while (matcher.find()) {
            String url = Utils.stripLastChar(matcher.group());
            if (url.contains(domain)) {
                addAndCrawlMatch(parent, url);
            } else {
                addMatch(parent, url);
            }
        }
        return parent;
    }

    private LinkTree<String> addMatch(LinkTree<String> parent, String url) {
        if (!visited.add(url)) {
            return null;
        }

        return parent.addChild(url);
    }

    private void addAndCrawlMatch(LinkTree<String> parent, String matchedUrl) throws Exception {
        LinkTree<String> child = addMatch(parent, matchedUrl);
        if (child != null) {
            crawl(matchedUrl, child);
        }
    }

}
