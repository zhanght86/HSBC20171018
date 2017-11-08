package com.sinosoft.easyscan5.core.service.queryissuedoc;

import java.util.List;
import java.util.Map;

import com.sinosoft.easyscan5.base.service.BaseService;
import com.sinosoft.easyscan5.core.vo.ExpandPropertyVo;
import com.sinosoft.easyscan5.core.vo.easyscan.QueryIssueVo;


public interface QueryIssueDocService extends BaseService {
	/**
	 * 查询问题件
	 * @param queryIssueVo
	 * @return
	 * @throws Exception
	 */
	public String queryIssue(QueryIssueVo queryIssueVo) throws Exception;
	/**
	 * 获取问题件返回结果
	 * @return
	 */
	public Map getReturnIssue();
	/**
	 * 获取单证扩展属性
	 * @return
	 */
	public Map getReturnProp();
	/**
	 * 查询单证扩展属性
	 * @param docId
	 * @param expPropVoList
	 * @throws Exception
	 */
	public void queryDocProp(String docId, List<ExpandPropertyVo> expPropVoList) throws Exception;
}