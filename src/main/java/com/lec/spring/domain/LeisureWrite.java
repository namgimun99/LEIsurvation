package com.lec.spring.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeisureWrite {
    private long id;
    private long company_id;
    private String name;
    private long price;
    private String content;
    private String address;
    private double avgstar;

    @ToString.Exclude
    @Builder.Default
    private List<LeisureFileDTO> files = new ArrayList<>();

}
