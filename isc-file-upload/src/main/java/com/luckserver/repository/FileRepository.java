package com.luckserver.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.luckserver.entity.File;



 

/**
 * File 存储库.
 * 
 */
public interface FileRepository extends MongoRepository<File, String> {
}
