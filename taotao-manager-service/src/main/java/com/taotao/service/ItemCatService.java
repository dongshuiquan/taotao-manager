package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.EUTreeNode;

public interface ItemCatService {
	/**
	 * 根据 parentId 查询商品子类列表
	 * @param parentId
	 * @return
	 */
	List<EUTreeNode> getCatList(long parentId);
}
