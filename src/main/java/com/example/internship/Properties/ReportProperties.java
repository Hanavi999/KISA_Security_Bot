package com.example.internship.Properties;

import com.example.internship.webCrawling.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Component
@Getter
@Setter
@Primary
public class ReportProperties {

    @Autowired
    private UrlReport urlCrawling;

    @Autowired
    private SecurityReport securityNotice;

    @Autowired
    private NumberReport numberCrawling;

    private String channel;

    private String pretext;

    private String color = "#00BFFE";

    private String authorName;

    private String authorIcon;

    private String title = "보고서\n\nNo." + numberCrawling.number();

    private String text = "[| "+securityNotice.process().toString()+"]("+urlCrawling.uri()+")";

    private String footer = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));


}
