package com.lec.spring.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RQryCommentList extends RQryResult {

    @JsonProperty("data")  // JSON 변환시 "data" 란 이름의 property 로 변환됨
    List<RComment> list;
}

