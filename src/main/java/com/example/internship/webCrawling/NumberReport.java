package com.example.internship.webCrawling;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class NumberReport {

    private static final String url = "https://www.krcert.or.kr/data/reportList.do";

    public static String number() {
        Connection conn = Jsoup.connect(url);
        //jsoup 커넥션

        Document document = null;

        try {
            document = conn.get();
            //url의 내용을 html document 객체로 가져옴.
        } catch(IOException e) {
            e.printStackTrace();
        }

        String list = getNumber(document);

        return list;

    }

    private static String getNumber(Document document) {
        Elements href = document.select(".colTit a");
        Elements gray = document.select(".gray");
        String parser = "";
        String par = "";

        int i = 0;
        int cnt = 1;
        String AnnNum = "";
        String hrefNum = "";
        int ANum;
        int hNum;
        for (Element select : gray) {
            if (select.text().equals("[공지]")) {
                cnt++;
            }
        }

        for (Element select : href) {
            i++;
            if (i == 1) {
                par = select.absUrl("href");
                int idx = par.indexOf("=");
                AnnNum = par.substring(idx + 1);
            }
            if (i == cnt) {
                parser = select.absUrl("href");
                int idx = parser.indexOf("=");
                hrefNum = parser.substring(idx + 1);
            }
        }

        ANum = Integer.parseInt(AnnNum);
        hNum = Integer.parseInt(hrefNum);

        if (ANum > hNum) {
            return AnnNum;
        } else {
            return hrefNum;
        }
    }

}
