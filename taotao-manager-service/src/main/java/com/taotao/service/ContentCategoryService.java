package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.utils.TaotaoResult;

public interface ContentCategoryService {
	
	List<EUTreeNode> getCotegoryList(long parentId);
	TaotaoResult insertContentCategory(long parentId, String name);
	TaotaoResult remoteContentCategory(long id);
	TaotaoResult updateContentCategory(Long id, String name);
}
