package com.lec.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewFileDTO {

    private Long id;
    private Long review_id;

    private String source; //업로드 파일명
    private String file; //저장된 파일명

    private boolean isImage; //이미지 여부
}
