package com.sist.lastproject.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;

import java.io.IOException;

public class WordParser {
    public static void main(String[] args) {
        try {
            Document doc = Jsoup.connect("https://www.youtube.com/watch?v=8AMBslo1zng").get();
            Elements elements = doc.select("yt-formatted-string#content-text");
            for (int i = 0; i < elements.size(); i++) {
                System.out.println(elements.get(i).text());
            }

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void parseWords(String words) {
        KeywordExtractor keywordExtractor = new KeywordExtractor();
        KeywordList keywordList = keywordExtractor.extractKeyword(words, true);
        keywordList.stream().forEach(k -> System.out.println(k.getString()+"="+k.getCnt()));
    }
}
