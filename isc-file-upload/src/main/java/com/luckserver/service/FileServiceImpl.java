package com.luckserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luckserver.entity.File;
import com.luckserver.repository.FileRepository;




/**
 * File 服务.
 * 
 */
@Service
public class FileServiceImpl implements FileService {
	
	@Autowired
	public FileRepository fileRepository;

	/* (non-Javadoc)
	 * @see com.waylau.spring.boot.fileserver.service.FileService#saveFile(com.waylau.spring.boot.fileserver.domain.File)
	 */
	@Override
	public File saveFile(File file) {
		return fileRepository.save(file);
	}

	/* (non-Javadoc)
	 * @see com.waylau.spring.boot.fileserver.service.FileService#removeFile(java.lang.Long)
	 */
	@Override
	public void removeFile(String id) {
		fileRepository.delete(id);
	}

	/* (non-Javadoc)
	 * @see com.waylau.spring.boot.fileserver.service.FileService#getFileById(java.lang.Long)
	 */
	@Override
	public File getFileById(String id) {
		return fileRepository.findOne(id);
	}

	/* (non-Javadoc)
	 * @see com.waylau.spring.boot.fileserver.service.FileService#listFiles()
	 */
	@Override
	public List<File> listFiles() {
		return fileRepository.findAll();
	}

}
