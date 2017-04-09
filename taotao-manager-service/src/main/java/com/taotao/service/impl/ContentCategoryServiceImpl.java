package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;
import com.taotao.service.ContentCategoryService;
/**
 * 内容分类管理
 * @author dong
 *
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService{

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;

	@Override
	public List<EUTreeNode> getCotegoryList(long parentId) {
		//根据parantId查询节点列表
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		//执行查询
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		List<EUTreeNode> resultList = new ArrayList<>();
		EUTreeNode node;
		for(TbContentCategory tbContentCategory : list){
			node=  new EUTreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIsParent() ? "closed" : "open");
			resultList.add(node);
		}
		return resultList;
	}

	@Override
	public TaotaoResult insertContentCategory(long parentId, String name) {
		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setName(name);
		contentCategory.setIsParent(false);
		//'状态。可选值:1(正常),2(删除)'
		contentCategory.setStatus(1);
		contentCategory.setParentId(parentId);
		contentCategory.setSortOrder(1);
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());
		contentCategoryMapper.insert(contentCategory); 
		//更新 parent
		TbContentCategory parentCat = contentCategoryMapper.selectByPrimaryKey(parentId);
		if(!parentCat.getIsParent()){
			parentCat.setIsParent(true);
			contentCategoryMapper.updateByPrimaryKey(parentCat);
		}
		return TaotaoResult.ok(contentCategory);
	}

	@Override
	public TaotaoResult remoteContentCategory(long id) {
		TbContentCategory contentCategory;
		contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
		long parentId = contentCategory.getParentId();
		//删除子节点
		if(contentCategory.getIsParent()){
			remoteContentCategoryById(id);
		}
		//删除自己
		contentCategoryMapper.deleteByPrimaryKey(id);
		//判断父节点是否为 parent
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			TbContentCategory parentCat = contentCategoryMapper.selectByPrimaryKey(parentId);
			parentCat.setIsParent(false);
			contentCategoryMapper.updateByPrimaryKey(parentCat);
		}
		return TaotaoResult.ok();
	}
	//递归删除
	private void remoteContentCategoryById(long id) {
		contentCategoryMapper.deleteByPrimaryKey(id);
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(id);
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		for(TbContentCategory category : list){
			remoteContentCategoryById(category.getId());
		}
	}

	@Override
	public TaotaoResult updateContentCategory(Long id, String name) {
		TbContentCategory record = new TbContentCategory();
		record.setId(id);
		record.setName(name);
		contentCategoryMapper.updateByPrimaryKeySelective(record);
		return TaotaoResult.ok();
	}
	
}
