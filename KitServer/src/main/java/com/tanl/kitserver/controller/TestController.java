package com.tanl.kitserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Administrator on 2016/4/26.
 */
@Controller
@RequestMapping(value = "/")
public class TestController {
	@Autowired
	ApplicationContext context;
	@RequestMapping(value = "/hello")
	public ModelAndView hello(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("index");
		return mv;
	}
}
