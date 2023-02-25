package com.lec.spring.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Qna {
    private Long id;
    private Long user_id;
    private String subject;
    private String content;
    private LocalDateTime regdate;

    private User user;

    @ToString.Exclude
    @Builder.Default
    private List<CommentDTO> files = new ArrayList<>();
}
