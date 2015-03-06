package com.drr.glocal.web.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Master Controller for all User Pages in WIX
 * @author rohitman
 *
 */
@Controller
public class UserPagesController {

    @RequestMapping("/user/console")
    public ModelAndView getUserConsole() {
        return new ModelAndView("user/console");
    }

}
