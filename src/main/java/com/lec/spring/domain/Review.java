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
    private String subject;
    private String content;
    private LocalDateTime regdate;
    private int star;

    private Long leisure_id; // list용 leisure.id
    private String leisure_name; // 레져이름
    private String reserve_date; // 예약날짜
    private  String reserve_name; // 작성자
    private String min_leisure_file; // leisure banner
}
