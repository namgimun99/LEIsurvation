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
public class ReserveWrite {
    private Long id;
    private String name;
    private String phone;
    private LocalDateTime regdate;
    private String date;
    private int leisure_id;
    private int user_id;

    private LeisureWrite leisureWrite; //여기에서는 name, address, price 를 가져오면 됨

}
