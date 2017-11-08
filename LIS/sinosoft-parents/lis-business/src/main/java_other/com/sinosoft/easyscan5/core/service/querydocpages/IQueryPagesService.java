package com.sinosoft.easyscan5.core.service.querydocpages;

import java.util.List;

import com.sinosoft.easyscan5.base.service.BaseService;


public interface IQueryPagesService extends BaseService{
	/**
	 * 查询影像页信息
	 * @param queryPageVo
	 * @return
	 * @throws Exception
	 */
	public String queryPages(List queryList) throws Exception;
	public List getResultList();
}
