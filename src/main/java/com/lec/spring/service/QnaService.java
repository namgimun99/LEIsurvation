package com.lec.spring.service;

import com.lec.spring.common.C;
import com.lec.spring.domain.Qna;
import com.lec.spring.domain.ReserveWrite;
import com.lec.spring.repository.QnaRepository;
import com.lec.spring.repository.ReserveRepository;
import com.lec.spring.util.U;
import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class QnaService {
        private QnaRepository qnaRepository;


    @Autowired
    public QnaService(SqlSession sqlSession) {
        qnaRepository = sqlSession.getMapper(QnaRepository.class);
        System.out.println("QnaService() 생성");
    }

    public List<Qna> list() {
        return qnaRepository.findAll();
    }


//    페이징-------------------------------
    public List<Qna> list(Integer page, Model model){
        //현재 페이지 parameter
        if(page==null) page=1;
        if(page < 1) page=1;

        HttpSession session = U.getSession();  //git 으로 옮길 때는 주석 해제
        Integer writePages=(Integer)session.getAttribute("writePages"); //git 으로 옮길 때는 주석 해제
//        Integer writePages = null; //git 으로 옮길때는 삭제
        if(writePages == null) writePages = C.WRITE_PAGES;
//        Integer pageRows = null; //git 으로 옮길때는 삭제
        Integer pageRows=(Integer)session.getAttribute("pageRows"); //git 으로 옮길 때는 주석 해제
        if(pageRows == null) pageRows = C.PAGE_ROWS;

        session.setAttribute("page", page);   //git 으로 옮길 때는 주석 해제

        long cnt = qnaRepository.countAll();  // 글 목록 전체의 개수
        int totalPage = (int)Math.ceil(cnt/(double)pageRows); //총 몇 페이지 분량인가

        // page 값 보정
        if(page > totalPage) page = totalPage;

        // 몇 번째 데이터부터 fromRow
        int fromRow = (page - 1) * pageRows;

        // [페이징] 에 표시할 '시작페이지' 와 '마지막페이지' 계산
        int startPage = ((int)((page - 1) / writePages) * writePages) + 1;
        int endPage = startPage + writePages - 1;
        if (endPage >= totalPage) endPage = totalPage;

        model.addAttribute("cnt", cnt);  // 전체 글 개수
        model.addAttribute("page", page); // 현재 페이지
        model.addAttribute("totalPage", totalPage);  // 총 '페이지' 수
        model.addAttribute("pageRows", pageRows);  // 한 '페이지' 에 표시할 글 개수

        // [페이징]
        model.addAttribute("url", U.getRequest().getRequestURI());  // 목록 url //git 으로 옮길 때는 주석 해제
        model.addAttribute("writePages", writePages); // [페이징] 에 표시할 숫자 개수
        model.addAttribute("startPage", startPage);  // [페이징] 에 표시할 시작 페이지
        model.addAttribute("endPage", endPage);   // [페이징] 에 표시할 마지막 페이지

        //해당 페이지의 글 목록 읽어오기
        List<Qna> list = qnaRepository.selectFromRow(fromRow,pageRows);
        model.addAttribute("list",list);
        return list;

    }



    public int write(Qna qna){
        return qnaRepository.save(qna);
    }

    public List<Qna> detail(Long id) {
        List<Qna> list = new ArrayList<>();
        Qna qna = qnaRepository.findById(id);

        if (qna != null) {
            list.add(qna);
        }
        return list;

    }



    public List<Qna> selectById(long id) {
        List<Qna> list = new ArrayList<>();
        Qna qna = qnaRepository.findById(id);

        if (qna != null) {
            list.add(qna);
        }
        return list;

    }


    public int update(Qna qna) {
        return qnaRepository.update(qna);
    }


    public int deleteById(Long id){
        int result = 0;
        Qna qna = qnaRepository.findById(id);
        if (qna != null) {
            result = qnaRepository.delete(qna);
        }

        return result;
    }


}
