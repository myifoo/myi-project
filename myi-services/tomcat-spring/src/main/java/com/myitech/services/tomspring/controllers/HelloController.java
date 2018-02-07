package com.myitech.services.tomspring.controllers;

import com.myitech.services.tomspring.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class HelloController {

    @Autowired
    private HelloService helloService;

    @RequestMapping(value="/hi")
    public String say() {
        return helloService.sayHello();
    }

//	@RequestMapping(value="/hello",method = RequestMethod.GET)
//	public String printWelcome(ModelMap model) {
//		model.addAttribute("message",helloService.sayHello());
//		return "hello.ftl";
//	}
//
//    @RequestMapping(value="/test", method = RequestMethod.GET)
//    public String printTest(ModelMap model) {
//        model.addAttribute("message",helloService.test);
//        return "hello.ftl";
//    }
}