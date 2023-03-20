package com.example.internship.domain.dto;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@Builder
@ToString
public class Attachment {
    private String channel;

    private String pretext;

    private String color;

    @SerializedName("author_name")
    private String authorName;

    @SerializedName("author_icon")
    private String authorIcon;

    private String title;

    private String text;

    private String footer;




}
