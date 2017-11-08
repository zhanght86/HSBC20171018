package com.sinosoft.easyscan5.core.service.querydocpages.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.sinosoft.lis.db.ES_DOC_MAINDB;
import com.sinosoft.lis.db.ES_DOC_PAGESDB;
import com.sinosoft.lis.db.ES_SERVER_INFODB;
import com.sinosoft.lis.schema.ES_DOC_PAGESSchema;
import com.sinosoft.lis.schema.ES_DOC_PAGESSchema;
import com.sinosoft.lis.schema.ES_SERVER_INFOSchema;
import com.sinosoft.lis.vschema.ES_DOC_MAINSet;
import com.sinosoft.lis.vschema.ES_DOC_PAGESSet;
import com.sinosoft.lis.vschema.ES_DOC_PAGESSet;
import com.sinosoft.lis.vschema.ES_SERVER_INFOSet;


import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.easyscan5.base.service.impl.BaseServiceImpl;
import com.sinosoft.easyscan5.common.Constants;
import com.sinosoft.easyscan5.core.service.querydocpages.IQueryPagesService;
import com.sinosoft.easyscan5.core.vo.ImagePathVo;
import com.sinosoft.easyscan5.core.vo.easyscan.EsPageVo;
import com.sinosoft.easyscan5.core.vo.easyscan.QueryPageVo;


public class QueryPagesServiceImpl extends BaseServiceImpl implements IQueryPagesService{

	private ES_DOC_PAGESSet resultES_DOC_PAGESSet;

	private String queryType;
	private HashMap serverMap = new HashMap();
	private String[] strPage_URL;
	private List<EsPageVo> esPageVoList = new ArrayList<EsPageVo>();
	private List allPageId = new ArrayList();
	private String returnStr = null;
	private QueryPageVo queryPageVo;
	public String queryPages(List queryList) throws Exception {
		queryPageVo = (QueryPageVo) queryList.get(0);
		String docId = queryPageVo.getDocId();
			
		queryType = queryPageVo.getType();
		String dataSource = queryPageVo.getDataSource();
		queryServer();
		returnStr = resultToVoList(dataSource, docId);
		return returnStr;
	}
	public String resultToVoList(String dataSource,String docId) throws Exception{
			ES_DOC_MAINDB es_doc_maindb = new ES_DOC_MAINDB();
			es_doc_maindb.setDocID(docId);
			ES_DOC_MAINSet nES_DOC_MAINSet = es_doc_maindb.query();
			ES_DOC_PAGESDB es_doc_pagesdb = new ES_DOC_PAGESDB();
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql("select * from es_doc_pages where docid='?docId?' order by pagecode");
			sqlbv.put("docId", docId);
			ES_DOC_PAGESSet nES_DOC_PAGESSet = es_doc_pagesdb.executeQuery(sqlbv);
			resultES_DOC_PAGESSet = nES_DOC_PAGESSet;
			returnStr = getPageUrl();
			if(returnStr != null){
				return returnStr;
			}
			EsPageVo.qcDocAndPageToPageVoList(nES_DOC_MAINSet.get(1),
					nES_DOC_PAGESSet,strPage_URL,esPageVoList);
		return null;
	}
	public String getPageUrl() throws Exception{
		boolean isQc = true;
		if(resultES_DOC_PAGESSet != null && resultES_DOC_PAGESSet.size() !=0){
			isQc = false;
			strPage_URL = new String[resultES_DOC_PAGESSet.size()];
		}
		
		ImagePathVo imagePathVo = new ImagePathVo();
		
		if(!isQc) {
			int start = 1;
			int end = resultES_DOC_PAGESSet.size();
			getPageLimit(start,end,queryPageVo);
			for(int i = start;i<= end;i++){
				ES_DOC_PAGESSchema esDocPages = resultES_DOC_PAGESSet.get(i);
				if(!serverMap.containsKey(esDocPages.getHostName())){
					return "找不到影像文件的服务器信息";
				}
				allPageId.add((int)esDocPages.getPageID() + "");
				ES_SERVER_INFOSchema eServer_Info = (ES_SERVER_INFOSchema)serverMap.
						get(esDocPages.getHostName());
				
				String path = Constants.HTTP + eServer_Info.getServerPort() +"/"
						+ esDocPages.getPicPath()
						+ esDocPages.getPageName() + esDocPages.getPageSuffix();
				strPage_URL[i-1] = path;
			}
		} 
		return null;
	}
	/**
	 * 获取查询页码限制
	 * @param start
	 * @param end
	 * @param queryPageVo2
	 */
	public void getPageLimit(int start, int end, QueryPageVo queryPageVo2){
		String startPage = queryPageVo.getStartPage();
		String endPage = queryPageVo.getEndPage();
		if(!"".equals(startPage) && !"-1".equals(startPage)
					&& !"".equals(endPage) && "-1".equals(endPage)){
			if((Integer.parseInt(startPage) < Integer.parseInt(endPage))
						&& (Integer.parseInt(startPage) <= end )
						&& (Integer.parseInt(endPage) <= end)){
				start = Integer.parseInt(startPage);
				end = Integer.parseInt(endPage);
			}
		}
	}
	/**
	 * 查询服务器信息
	 * @return
	 * @throws Exception
	 */
	private HashMap queryServer() throws Exception {
		ES_SERVER_INFODB es_server_infodb = new ES_SERVER_INFODB();
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql("select * from es_server_info");
		ES_SERVER_INFOSet eSet = es_server_infodb.executeQuery(sqlbv);
		
		for(int i = 1;i<= eSet.size();i++){
			ES_SERVER_INFOSchema eSchema = eSet.get(i);
			serverMap.put(eSchema.getHostName(), eSchema);
		}
		return serverMap;
	}
	public List getResultList(){
		return esPageVoList;
	}
}
