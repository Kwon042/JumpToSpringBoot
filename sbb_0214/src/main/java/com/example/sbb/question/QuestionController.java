package com.example.sbb.question;

import com.example.sbb.answer.AnswerForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/question")
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Question> questionList = this.questionService.getList();
        model.addAttribute("questionList", questionList);
        return "question_list";
    }

    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) {
        return "question_form";
    }

    @PostMapping("/create")
    //BingingResult 매개변수는 항상 @Valid 매개변수 바로 뒤에 위치
    //만약 두 매개변수의 위치가 정확하지 않으면 >> @Valid 만 적용되어 입력값 검증 실패 시 400 오류 발생
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult) {
        //hasErrors()를 호출하여 오류가 있는 경우에는 다시 제목과 내용을 작성하는 화면으로 돌아가게
        if(bindingResult.hasErrors()) {
            return "question_form";
        }
        this.questionService.create(questionForm.getSubject(), questionForm.getContent());
        //질문 저장 후 질문목록으로 이동 (redirect:/ >> 주로 수정, 삭제에서
        return "redirect:/question/list";
    }

}
