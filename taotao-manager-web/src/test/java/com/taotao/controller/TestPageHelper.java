package com.taotao.controller;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-*.xml")
public class TestPageHelper {
	
	@Resource
	TbItemMapper mapper;
	
	@Test
	public void testPageHelper(){
		TbItemExample example = new TbItemExample();
		PageHelper.startPage(1, 10);
		List<TbItem> list = mapper.selectByExample(example);
		//取商品列表
		for(TbItem item : list){
			System.out.println(item.getSellPoint());
		}
		//取分页信息
		PageInfo pageInfo = new PageInfo(list);
		Long total = pageInfo.getTotal();
		System.out.println("共有商品 ： " + total);
	}
}
