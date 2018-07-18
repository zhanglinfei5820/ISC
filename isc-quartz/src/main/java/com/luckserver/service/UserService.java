package com.luckserver.service;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.luckserver.dao.UserDao;
import com.luckserver.entity.User;


@Service
@Transactional
public class UserService {
	@Autowired
	private UserDao userDao;
	
	public List<User> findAll() {
		return userDao.findAll();
	}
}
