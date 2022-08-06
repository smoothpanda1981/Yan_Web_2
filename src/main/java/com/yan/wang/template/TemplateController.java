package com.yan.wang.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    @GetMapping("/template")
    @ResponseStatus(value = HttpStatus.OK)
    public ModelAndView getGoogleSheetContent() {
        System.out.println("1");
        String content = templateService.getTemplateContent();
        ModelAndView modelAndView = new ModelAndView("template/tp");
        modelAndView.addObject("content", content);
        return modelAndView;
    }
}
