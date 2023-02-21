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
public class ReserveWrite { //여기에서는 name, phone, regdate, reserve_date 를 받아야함.
    private Long id;
    private String name;
    private int phone;
    private LocalDateTime regdate;
    private int date;
    private int leisure_id;
    private int user_id;

    private LeisureWrite leisureWrite; //여기에서는 name, address, price 를 가져오면 됨

}
