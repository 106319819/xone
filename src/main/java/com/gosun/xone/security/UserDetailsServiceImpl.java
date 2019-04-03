package com.gosun.xone.security;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.gosun.common.Util;
import com.gosun.xone.core.entity.Account;
import com.gosun.xone.core.entity.Module;
import com.gosun.xone.core.entity.Person;
import com.gosun.xone.core.service.AccountService;
import com.gosun.xone.core.service.PersonService;
import com.gosun.xone.core.service.RolePersonService;


/**
 * 用户登录认证信息查询
 * @date Nov 20, 2018
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AccountService accountService;
    @Autowired
    private RolePersonService rolePersonService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	JsonNode node = Util.parseJSONEx(username);
        Account account = this.accountService.findByAccountCode(node.get("accountCode").asText());
        if(Util.isNvl(account)) {
            throw new UsernameNotFoundException("该用户不存在");
        }
        // 用户权限列表，根据用户拥有的权限标识与如 @PreAuthorize("hasAuthority('sys:menu:view')") 标注的接口对比，决定是否可以调用接口
        Long personId = node.get("personId").asLong();
//        Person person = personService.findByAccountId(account.getAccountId());        
        List<Module> modules = rolePersonService.findModulesByPersonId(personId);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        Iterator<Module> it = modules.iterator();
        while(it.hasNext()) {
        	Module module = it.next();
        	//如果permission为空，则使用模块id代替
        	String permission = Util.nvl(module.getPermission(),String.valueOf(module.getModuleId()));
        	SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permission);
        	authorities.add(authority);
        }
         return new User(username, account.getPassword(),  authorities);
    }
}