package com.lec.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    private long id;
    private String reservation_id;
    private String user_id;
    private String leisure_id;
    private String subject;
    private String content;
    private LocalDateTime regdate;
    private int star;
}
