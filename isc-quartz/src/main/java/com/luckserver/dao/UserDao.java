package com.luckserver.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.luckserver.entity.User;


@Repository
public interface UserDao extends JpaRepository<User, Long> {

}
