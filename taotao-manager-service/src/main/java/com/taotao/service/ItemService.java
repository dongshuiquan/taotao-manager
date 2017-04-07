package com.taotao.service;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbItem;

public interface ItemService {
	
	/**
	 * 根据 id 查询 item 结果
	 * @param id
	 * @return
	 */
	TbItem getItemById(long id);
	
	/**
	 * 分页查询
	 * @param page 页码
	 * @param rows	每页行数
	 * @return easyUI要求的数据
	 */
	EUDataGridResult getItemList(int page, int rows);
	
	/**
	 * 添加商品， 返回结果
	 * @param item
	 * @return
	 */
	TaotaoResult createItem(TbItem item);
}
