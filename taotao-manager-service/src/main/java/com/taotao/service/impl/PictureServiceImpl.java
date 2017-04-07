package com.taotao.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.utils.IDUtils;
import com.taotao.service.PictureService;
/**
 * 图片上传服务
 * @author dong
 *
 */
@Service
public class PictureServiceImpl implements PictureService {
	@Value("${pic_store}")
	private String storeFile;
	@Value("${pic_baseUrl}")
	private String baseUrl;
	@Override
	public Map<?, ?> uploadPicture(MultipartFile uploadFile) {
		String oldName = uploadFile.getOriginalFilename();
		String newName = IDUtils.genImageName();
		newName += oldName.substring(oldName.lastIndexOf("."));
		String filePath = new DateTime().toString("yyyy/MM/dd/");
		String storePath = storeFile + filePath;
		File file = new File(storePath);
		if(!file.exists()){
			file.mkdirs();
		}
		File newFile = new File(storePath, newName);
		Map<String, Object> map = new HashMap<>();
		try(
			InputStream input = uploadFile.getInputStream();
			OutputStream output = new FileOutputStream(newFile);){
			byte[] b = new byte[1024];
			int len;
			while((len = input.read(b, 0, b.length)) >0){
				output.write(b, 0, len);
			}
			String url = baseUrl + "/images/" + filePath + newName;
			map.put("error", 0);
			map.put("url", url);
			
		} catch (IOException e) {
			e.printStackTrace();
			map.put("error", 1);
			map.put("message", "文件上传异常");
			return map;
		} 
		return map;
	}

}
