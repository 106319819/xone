package com.gosun.xone.core.controller;

import java.io.IOException;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gosun.common.XoneException;
import com.gosun.common.Result;
import com.gosun.common.Util;
import com.gosun.xone.core.utils.XoneSessionListener;

@RestController
@RequestMapping("/admin/session")
public class SessionController {

	
	@RequestMapping("/onlines")
	public Result<?> onlines(){
		return Result.success(XoneSessionListener.getOnlines());
	}
	@RequestMapping("/list-onlines")
	public Result<?> findAll(@RequestBody String content) throws IOException, XoneException{
		Pageable pageable = Util.parse(content);
//		Page<Account> result = accountService.findAll(pageable);
//		return Result.success(result, result.getContent());
		return Result.success();
	}
	
}
