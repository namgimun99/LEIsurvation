package com.lec.spring.domain.company;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyWrite {
    private long id;
    private long user_id;
    private String name;
    private String address;
    private String companyname;
}
