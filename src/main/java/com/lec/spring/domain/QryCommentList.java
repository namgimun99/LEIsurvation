package com.lec.spring.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.stream.events.Comment;
import java.util.List;

@Data
@NoArgsConstructor
public class QryCommentList extends QryResult{

    @JsonProperty("data") //JSON 변환 시 "data" 란 이름의 property 로 변환됨
    List<Qna_comment> list;
}
