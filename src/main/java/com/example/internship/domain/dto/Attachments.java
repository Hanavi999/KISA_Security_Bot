package com.example.internship.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Attachments {
    private Props props;
    private List<Attachment> attachments;

    public Attachments() {
        attachments = new ArrayList<>();
    }

    public Attachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public Attachments(Attachment attachment) {
        this();
        this.attachments.add(attachment);
    }



}
