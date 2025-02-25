package com.example.sbb.question;


import com.example.sbb.answer.Answer;
import com.example.sbb.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;

    // 글쓴이 저장 > @ManyToOne : 사용자 한 명이 질문을 여러 개 작성할 수 있기 때문에
    @ManyToOne
    private SiteUser author;

    // 질문이나 답변이 언제 수정되었는지를 확인할 수 있다
    private LocalDateTime modifyDate;
}
