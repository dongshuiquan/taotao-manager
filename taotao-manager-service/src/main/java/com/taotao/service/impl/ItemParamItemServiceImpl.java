package com.taotao.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.pojo.TbItemParamItemExample.Criteria;
import com.taotao.service.ItemParamItemService;

@Service
public class ItemParamItemServiceImpl implements ItemParamItemService {

	@Autowired
	private TbItemParamItemMapper tbItemParamItemMapper;
	
	@Override
	@SuppressWarnings("unchecked")
	public String getItemParamByItemId(Long itemId) {
		TbItemParamItemExample example = new TbItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		//执行查询
		List<TbItemParamItem> list = tbItemParamItemMapper.selectByExampleWithBLOBs(example);
		if(list == null || list.size() == 0){
			return null;
		}
		TbItemParamItem tbItemParamItem = list.get(0);
		String paramData = tbItemParamItem.getParamData();
		//生成 html
		//把规格参数 json 数据转换成 Java 对象 
		List<Map> jsonList = JsonUtils.jsonToList(paramData, Map.class);
		StringBuilder sb = new StringBuilder(200);
		sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"1\">\n" );
		sb.append("		<tbody>\n" );
		for(Map<?,?> m1 : jsonList){
			sb.append("			<tr>\n" );
			sb.append("				<th colspan=\"2\">" +  m1.get("group") + "</th>\n" );
			sb.append("			</tr>\n" );
			List<Map<?,?>> list2 = (List<Map<?,?>>) m1.get("params");
			for(Map<?,?> m2 : list2){
				sb.append("			<tr>\n" );
				sb.append("				<td>" + m2.get("k") + "</td>\n" );
				sb.append("				<td>" + m2.get("v") + "</td>\n" );
				sb.append("           </tr>\n ");
			}
		}
		sb.append("		</tbody>\n" );
		sb.append("</table>");
		return sb.toString();
	}

}
