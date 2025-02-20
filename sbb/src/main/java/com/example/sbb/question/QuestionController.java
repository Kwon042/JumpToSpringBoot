package com.example.sbb.question;

import com.example.sbb.answer.AnswerForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/question")
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping("/list")
    //defaultValue = "0" > page 파라미터가 클라이언트의 요청에 포함되지 않았을 때, 해당 파라미터의 기본값으로 0을 사용하겠다는 의미
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        Page<Question> paging = this.questionService.getList(page);
        model.addAttribute("paging", paging);
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
