package com.gordon.iRecipe.homepage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomePageController {

    @RequestMapping("/index")
    public String testString(){
        return "We found it";
    }
}
