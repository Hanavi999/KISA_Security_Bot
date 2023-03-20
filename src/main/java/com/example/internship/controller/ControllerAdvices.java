package com.example.internship.controller;

import com.example.internship.Properties.MattermostProperties;
import com.example.internship.domain.dto.Attachment;
import com.example.internship.domain.dto.Attachments;
import com.example.internship.domain.dto.Props;
import com.example.internship.webCrawling.InfoSecurity;
import com.example.internship.webCrawling.NumberCrawling;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.Scanner;
@EnableScheduling
@RestController
@Slf4j
public class ControllerAdvices {

    @Value("${notification.mattermost.webhook-url}")
    private String webhookUrl;

    @Autowired
    private MattermostProperties mmProperties;

    @Autowired
    private InfoSecurity infoSecurity;

    @Autowired
    private NumberCrawling numberCrawling;

    @Scheduled(fixedDelay = 1000)
    @GetMapping("/security")
    public void test() throws InterruptedException {
        RestTemplate restTemplate = new RestTemplate();
        String fileRead = "";
        //파일 읽기
        try {
            String projectPath = System.getProperty("user.dir") + "//src//main//resources//NoFile//Security.txt";
            File file = new File(projectPath);
            Scanner scan = new Scanner(file);

            while (scan.hasNextLine()) {
                fileRead = scan.nextLine();
            }
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //파일쓰기
        try {
            String projectPath = System.getProperty("user.dir") + "//src//main//resources//NoFile//Security.txt";
            FileWriter fileWriter = new FileWriter(projectPath);


            if (!fileRead.equals(numberCrawling.number())) {
                fileWriter.write(numberCrawling.number());
            } else {
                fileWriter.write(fileRead);
            }
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!fileRead.equals(numberCrawling.number())) {
            Attachment attachment = Attachment.builder()
                    .channel(mmProperties.getChannel())
                    .authorIcon(mmProperties.getAuthorIcon())
                    .authorName(mmProperties.getAuthorName())
                    .color(mmProperties.getColor())
                    .pretext(mmProperties.getPretext())
                    .title(mmProperties.getTitle())
                    .text(mmProperties.getText())
                    .footer(mmProperties.getFooter())
                    .build();

            Attachments attachments = new Attachments(attachment);
            if (infoSecurity.Keyword().equals("키워드")) {
                attachments.setProps(new Props(infoSecurity.process()
                        .replaceAll("chanline", "\n\n")
                        .replaceAll("tapk", "\t")
                        .replaceAll("□", "###")
                        .replaceAll("트위터", "")
                        .replaceAll("페이스북", "")
                        .replaceFirst("\t", "")
                        .replaceFirst("\t", "")
                ));

            } else {
                attachments.setProps(new Props(infoSecurity.process()
                        .replaceAll("chanline", "\n\n")
                        .replaceAll("tapk", "\t")
                        .replaceAll("□", "###")
                        .replaceAll("트위터", "")
                        .replaceAll("페이스북", "")
                        .replaceFirst("\t", "")
                        .replaceFirst("\t", "") + "\n\n[첨부파일 다운로드](" + infoSecurity.attFile() + ")"));

            }

            String payload = new Gson().toJson(attachments);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(payload, headers);

            restTemplate.exchange(webhookUrl, HttpMethod.POST, entity, String.class);

        }
        log.info("renewal task security");
        Thread.sleep(60000 * 5);
    }
}
