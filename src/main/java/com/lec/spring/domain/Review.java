package com.lec.spring.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    private Long id;
    private String reservation_id;
    private String subject;
    private String content;
    private LocalDateTime regdate;
    private int star;

    // 글 작성자(FK)
    private User user_id;

    private Long leisure_id; // list용 leisure.id
    private String leisure_name; // 레져이름
    private String reserve_date; // 예약날짜
    private  String reserve_name; // 작성자
    private String min_leisure_file; // leisure banner

    // 첨부파일, 댓글
    @ToString.Exclude
    @Builder.Default
    private List<ReviewFileDTO> files = new ArrayList<>();
}
