package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.service.ContentCategoryService;

/**
 * 内容分类管理
 * @author dong
 *
 */
@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {
	@Autowired
	private ContentCategoryService contentCategoryService;
	
	@RequestMapping("/list")
	@ResponseBody
	public List<EUTreeNode> getContengCatList(@RequestParam(value="id", defaultValue="0")long parentId){
		List<EUTreeNode> list = contentCategoryService.getCotegoryList(parentId);
		return list;
	}
	
	@RequestMapping("/create")
	@ResponseBody
	public TaotaoResult createContentCategory(Long parentId, String name){
		return contentCategoryService.insertContentCategory(parentId, name);
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public TaotaoResult createContentCategory(Long id){
		return contentCategoryService.remoteContentCategory(id);
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public TaotaoResult updateContentCategory(Long id, String name){
		return contentCategoryService.updateContentCategory(id, name);
	}
}
