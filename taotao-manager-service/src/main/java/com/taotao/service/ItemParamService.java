package com.taotao.service;

import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbItemParam;

public interface ItemParamService {
	
	TaotaoResult getItemParamByCid(long id);
	
	/**
	 * 插入商品参数模板
	 * @param itemParam
	 * @return
	 */
	
	TaotaoResult insertItemParam(TbItemParam itemParam);
}
