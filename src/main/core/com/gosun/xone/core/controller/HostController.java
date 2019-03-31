package com.gosun.xone.core.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gosun.common.XoneException;
import com.gosun.common.Result;
import com.gosun.common.Util;
import com.gosun.xone.core.entity.Host;
import com.gosun.xone.core.service.HostService;

@RestController
@RequestMapping("/admin/host")
public class HostController {

	@Autowired
	private HostService hostService;
	@RequestMapping("/create")
	public Result<?> create(@RequestBody Host host){
		hostService.create(host);
		
		return Result.success(host);
	}
	@RequestMapping("/find-all")
	public Result<?> findAll(@RequestBody String content) throws IOException, XoneException{
		Pageable pageable = Util.parse(content);
		Page<Host> result = hostService.findAll(pageable);
		return Result.success(result, result.getContent());
	}
	
	@RequestMapping("/find-by-id/{hostId}")
	public Result<?> findById(@PathVariable("hostId") Long hostId){
		Host result = hostService.findById(hostId);
		return Result.success(result);
	}
	
	@RequestMapping("/delete-by-id/{hostId}")
	public Result<?> delete(@PathVariable("hostId") Long hostId){
		hostService.deleteById(hostId);
		return Result.success(hostId);
	}
	
	@PostMapping("/delete-batch")
	public Result<?> deleteBatch(@RequestBody List<Long> hostIds){
		hostService.deleteByIdList(hostIds);
		return Result.success(hostIds);
	}
	
	@PostMapping("/update")
	public Result<?> update(@RequestBody Host host) throws XoneException{
		hostService.update(host);
		return Result.success(host);
	}
	
	
}
