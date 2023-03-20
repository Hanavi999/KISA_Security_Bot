package com.example.internship.webCrawling;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.Key;

@Component
public class InfoSecurity {

    @Autowired
    private static UrlCrawling urlCrawling;

    private static final String url = urlCrawling.uri();

    public static String process() {
        Connection conn = Jsoup.connect(url);
        //jsoup 커넥션

        Document document = null;

        try {
            document = conn.get();
            //url의 내용을 html document 객체로 가져옴.
        } catch(IOException e) {
            e.printStackTrace();
        }
        document.select("br").append("chanline");
        document.select("table").prepend("chanline");
        document.select("tr").prepend("chanline");
        document.select("td").prepend("tapk");
        String list = getDataList(document);


        return list;

    }

    public static String attFile() {
        Connection conn = Jsoup.connect(url);
        //jsoup 커넥션

        Document document = null;

        try {
            document = conn.get();
            //url의 내용을 html document 객체로 가져옴.
        } catch(IOException e) {
            e.printStackTrace();
        }

        String list = attFile(document);


        return list;

    }

    public static String Keyword() {
        Connection conn = Jsoup.connect(url);
        //jsoup 커넥션

        Document document = null;

        try {
            document = conn.get();
            //url의 내용을 html document 객체로 가져옴.
        } catch(IOException e) {
            e.printStackTrace();
        }

        String list = getKeyword(document);


        return list;

    }


    private static String getDataList(Document document) {
        Elements colTit = document.select(".cont");
          String parser = "";
        System.out.println(colTit.text());
        int i = 0;
        parser = colTit.text();
        if(parser.length() > 7000) {
            return parser.substring(1, 7000) + ".....";
        }
        else {
            return parser;
        }

    }

    private static String attFile(Document document) {
        Elements colTit = document.select(".basicView tbody tr td a");
        String parser = "";

        int i = 1;
        for(Element select : colTit) {
            if(i == 3) {
                parser = select.absUrl("href");
                System.out.println(parser);
            }
            i++;
        }

        return parser;
    }

    private static String getKeyword(Document document) {
        Elements colTit = document.select(".basicView tbody tr td span");
        String parser = "";
        int i = 1;
        for(Element select : colTit) {
            if(i == 2) {
                parser = select.text();
                System.out.println(parser);
            }
            i++;
        }

        return parser;
    }

    public static void main(String[] args) {

    }

}
