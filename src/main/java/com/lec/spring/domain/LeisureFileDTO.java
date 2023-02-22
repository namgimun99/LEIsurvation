package com.lec.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeisureFileDTO {
    private Long id;
    private Long leisure_id;
    private String source;
    private String file;
    private boolean isImg;
}
