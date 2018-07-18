package com.luckserver.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.luckserver.entity.File;
import com.luckserver.entity.MD5Util;
import com.luckserver.service.FileService;

/**
 * 
* @ClassName: FileUploadController  
* @Description: 图片上传和下载 
* @author zlf  
* @date 2018年5月31日  
* @modified By
* @modified Date
*
 */
public class FileUploadController {
	
	@Resource
	FileService fileService;
	
	/**
	 * 
	* @Title: serveFileOnline  
	* @Description: 在线显示文件
	* @param @param id
	* @param @return    参数  
	* @return ResponseEntity    返回类型  
	* @throws
	 */
    @SuppressWarnings("rawtypes")
	@GetMapping("/view/{id}")
    @ResponseBody
    public ResponseEntity serveFileOnline(@PathVariable String id) {

        File file = fileService.getFileById(id);

        if (file != null) {
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "fileName=\"" + file.getName() + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, file.getContentType() )
                    .header(HttpHeaders.CONTENT_LENGTH, file.getSize()+"")
                    .header("Connection",  "close") 
                    .body( file.getContent());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File was not fount");
        }

    }

    /**
     * 
    * @Title: handleFileUpload  
    * @Description: 照片上传
    * @param @param file
    * @param @param request
    * @param @return    参数  
    * @return ResponseEntity<String>    返回类型  
    * @throws
     */
	@RequestMapping("/upload")
    @ResponseBody
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file  ,HttpServletRequest request) {
    	File returnFile = null;
        try {
        	 
        	File f = new File(file.getOriginalFilename(),  file.getContentType(), file.getSize(),file.getBytes());
        	f.setMd5( MD5Util.getMD5(file.getInputStream()) );
        	returnFile = fileService.saveFile(f);
        	String avatar = "";
        	if(returnFile != null) {
        		avatar = "http://39.106.101.203:8090/u/view/" + returnFile.getId();
        	}else {
        		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("上传失败,请重新上传！");
        	}
        	return ResponseEntity.status(HttpStatus.OK).body(avatar);
 
        } catch (IOException | NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }
}
