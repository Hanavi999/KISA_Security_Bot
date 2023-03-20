package com.example.internship.Properties;

import com.example.internship.webCrawling.NumberCrawling;
import com.example.internship.webCrawling.SecurityNotice;
import com.example.internship.webCrawling.UrlCrawling;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Getter
@Setter
@Primary
public class MattermostProperties {

    @Autowired
    private UrlCrawling urlCrawling;

    @Autowired
    private SecurityNotice securityNotice;

    @Autowired
    private NumberCrawling numberCrawling;

    private String channel;

    private String pretext;

    private String color = "#00BFFE";

    private String authorName;

    private String authorIcon;

    private String title = "보안공지\n\nNo." + numberCrawling.number();

    private String text = "[| "+securityNotice.process().toString()+"]("+urlCrawling.uri()+")";

    private String footer = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));


}
