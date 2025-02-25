package com.example.sbb.question;

import com.example.sbb.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public Question getQuestion(Integer id) {
        Optional<Question> question = this.questionRepository.findById(id);
        if(question.isPresent()) {
            return question.get();
        } else {
            throw new com.example.sbb.question.DataNotFoundException("question not found");
        }
    }
    //getList()메서드를 이렇게 변경
    public Page<Question> getList(int page) {
        //최근에 작성한 게시물이 가장 위에 보이게 만들어 주는
        List<Sort.Order> sorts = new ArrayList<>();
        //작성 일시(createDate)를 역순(desc)으로 조회 // <-> 오름차순(acs)
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.questionRepository.findAll(pageable);
    }

    // 질문 등록 > repository > db로 저장되게
    // SiteUser user : 코드 작성자가 > user 는 author 의 개념보다는 좀 더 넓은 의미로 한 눈에 알아볼 수 있게 글쓴이 user 로 정의
    public void create(String subject, String content, SiteUser user) {
        // 사용자 정보를 담은 user 객체
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        q.setAuthor(user);  // user 는 질문 작성자 > Question 객체의 author 속성에 설정됨
        this.questionRepository.save(q);
    }

    // 질문 수정
    public void modify(Question question, String subject, String content) {
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());
        this.questionRepository.save(question);
    }

}
