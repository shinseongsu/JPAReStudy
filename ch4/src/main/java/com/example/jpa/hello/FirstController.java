package com.example.jpa.hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FirstController {

    @RequestMapping(value = "/first-url", method = RequestMethod.GET)
    public void first() {
    }

    @ResponseBody   //  문자열이 리턴되게
    @RequestMapping("/helloworld")
    public String helloworld() {

        return "hello world";       // 기본 상태는 view page가 리턴됨.
    }


    public String helloString() {

        return "hello spring";
    }

}
