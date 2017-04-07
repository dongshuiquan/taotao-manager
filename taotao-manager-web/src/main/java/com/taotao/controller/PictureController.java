package com.taotao.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.utils.JsonUtils;
import com.taotao.service.PictureService;

@Controller
@RequestMapping("/")
public class PictureController {
	
	@Autowired
	private PictureService pictureService;
	
	@RequestMapping("/pic/upload")
	@ResponseBody
	private String getCatList(MultipartFile uploadFile){
		Map<?,?> map = pictureService.uploadPicture(uploadFile);
		//为了兼容性， 返回 json 格式的字符串
		String json = JsonUtils.objectToJson(map);
		return json;
	}
	

}
