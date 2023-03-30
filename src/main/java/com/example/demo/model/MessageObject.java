package com.example.demo.model;

import lombok.*;

import java.io.Serializable;

@Data@Builder@AllArgsConstructor@NoArgsConstructor
public class MessageObject implements Serializable {
    private int id;
    private String message;
}
