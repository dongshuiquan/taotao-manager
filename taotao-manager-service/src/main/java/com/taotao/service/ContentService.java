package com.taotao.service;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbContent;

public interface ContentService {
	TaotaoResult insertContent(TbContent content);

	EUDataGridResult getContentList(Long categoryId, Integer page, Integer rows);
}
