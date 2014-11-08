package com.drr.glocal.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by racastur on 08-11-2014.
 */
@Controller
public class HelloWorldController {

    @RequestMapping("/helloworld")
    public ModelAndView helloWorld(Model model) {
        model.addAttribute("message", "Hello World!");
        return new ModelAndView("helloworld");
    }

}