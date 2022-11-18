package com.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AddController {

	@RequestMapping("/add")
	public ModelAndView add(HttpServletRequest req, HttpServletResponse res) {
		int i= Integer.parseInt(req.getParameter("t1"));
		int j = Integer.parseInt(req.getParameter("t2"));
		ModelAndView mv = new ModelAndView();
		mv.setViewName("Show.jsp");
		mv.addObject("result", i+j);
		return mv;
	}
}
