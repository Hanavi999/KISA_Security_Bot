package com.example.internship.webCrawling;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SecurityNotice {
    private static final String url = "https://www.krcert.or.kr/data/secNoticeList.do";
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

        String list = getDataList(document);

        return list;

    }

    private static String getDataList(Document document) {
        Elements colTit = document.select("tr td a");
        Elements gray = document.select(".gray");
        String parser = "";

        int i = 0;
        int cnt = 1;
        for(Element select : gray) {
            if(select.text().equals("[공지]")) {
                cnt++;
            }
        }

        for(Element select : colTit) {
            i++;
            if(i == cnt) {
                parser = select.text();
                //System.out.println(parser);
            }
        }

        return parser;
    }

}
