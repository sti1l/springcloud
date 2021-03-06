package com.realz.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 访问监测测试Controller类
 * @author sti1l
 *
 */
public @Controller class MonitorController {
	public @RequestMapping("monitorMain") @ResponseBody String MonitorMain(
			HttpServletRequest req,
			HttpServletResponse rep) throws IOException {
		// 输出到页面
		//rep.getWriter().write("1111");
		
		// 访问IP地址
		String remoteAddr = req.getRemoteAddr();
		//rep.getWriter().write(remoteAddr);
		
		// html
		rep.setContentType("text/html;charset=utf-8");
		//PrintWriter writer = rep.getWriter();
		//writer.print("<div style='color:red'>难道说</div>");
		
		return "<div style='color:red'>难道说</div>";
	}
	/**
	 *注解 开发
	 */
	public @RequestMapping("hello") @ResponseBody void hello() {
		System.err.println("hello");
	}
}
