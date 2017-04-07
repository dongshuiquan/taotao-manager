package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.ItemService;
/**
 * ItemService 管理类
 * @author dong
 *
 */
@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private TbItemMapper itemMapper;
	
	@Autowired
	private TbItemDescMapper itemDescMapper;
	
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;
	
	@Override
	public TbItem getItemById(long itemId) {
		TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);
		return tbItem;
	}

	@Override
	public EUDataGridResult getItemList(int page, int rows) {
		TbItemExample example = new TbItemExample();
		PageHelper.startPage(page, rows);
		List<TbItem> list = itemMapper.selectByExample(example);
		PageInfo pageInfo = new PageInfo(list);
		Long total = pageInfo.getTotal();
		EUDataGridResult result = new EUDataGridResult();
		result.setTotal(total);
		result.setRows(list);
		return result;
	}

	@Override
	public TaotaoResult createItem(TbItem item, String desc, String itemParams) {
		//id 生成
		Long itemId =IDUtils.genItemId();
		item.setId(itemId);
		//'商品状态，1-正常，2-下架，3-删除',
		item.setStatus((byte)1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		itemMapper.insert(item);
		insertItemParamItem(itemId,itemParams);
		insertItemDesc(item.getId(), desc);
		return TaotaoResult.ok();
	}
	
	/*
	 *添加商品描述 
	 */
	private TaotaoResult insertItemDesc(long itemId, String desc){
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		Date date = new Date();
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		itemDescMapper.insert(itemDesc);
		return TaotaoResult.ok();
	}
	
	/*
	 *添加商品参数 
	 */
	private TaotaoResult insertItemParamItem(long itemId, String itemParam){
		TbItemParamItem itemParamItem = new TbItemParamItem();
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(itemParam);
		Date date = new Date();
		itemParamItem.setCreated(date);
		itemParamItem.setUpdated(date);
		itemParamItemMapper.insert(itemParamItem);
		return TaotaoResult.ok();
	}
}
