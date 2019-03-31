package com.gosun.xone.core.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gosun.common.XoneException;
import com.gosun.common.Error;
import com.gosun.xone.core.entity.Host;
import com.gosun.xone.core.repository.HostRespository;
import com.gosun.xone.core.service.HostService;

@Service
public class HostServiceImpl implements HostService
{

	@Autowired
	private HostRespository hostRespository;
	public void create(Host host)
	{
		//创建人员基本信息
		this.hostRespository.save(host);
		
	}

	public Host findById(Long hostId)
	{
		// TODO Auto-generated method stub
		Optional<Host> result = hostRespository.findById(hostId);
		return result.get();
	}

	public Page<Host> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return hostRespository.findAll(pageable);
	}

	public void deleteById(Long hostId) {
		// TODO Auto-generated method stub
		hostRespository.deleteById(hostId);
	}

	@Override
	public void update(Host host) throws XoneException {
		// TODO Auto-generated method stub
		if(!this.hostRespository.existsById(host.getHostId())) {
			XoneException.throwing(Error.DATA_NO_FOUND);
		}
		this.hostRespository.save(host);
	}

	@Override
	@Transactional
	public void deleteByIdList(List<Long> hostIds) {
		// TODO Auto-generated method stub
		this.hostRespository.deleteByIds(hostIds);
	}

}
