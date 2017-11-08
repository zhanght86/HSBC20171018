package com.sinosoft.easyscan5.core.service.queryissuedoc.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.f1j.ui.ew;
import com.sinosoft.easyscan5.base.service.impl.BaseServiceImpl;
import com.sinosoft.easyscan5.common.Constants;
import com.sinosoft.easyscan5.common.EsDocMainPToEsQcMain;
import com.sinosoft.easyscan5.core.service.queryissuedoc.QueryIssueDocService;
import com.sinosoft.easyscan5.core.vo.EsIssueDocVO;
import com.sinosoft.easyscan5.core.vo.ExpandPropertyVo;
import com.sinosoft.easyscan5.core.vo.easyscan.QueryIssueVo;
import com.sinosoft.easyscan5.entity.EsQcMain;
import com.sinosoft.easyscan5.util.ExpandPropertyUtilImpl;
import com.sinosoft.easyscan5.util.FDate;
import com.sinosoft.lis.db.ES_DOC_MAINDB;
import com.sinosoft.lis.db.ES_DOC_PAGESDB;
import com.sinosoft.lis.schema.ES_DOC_MAINSchema;
import com.sinosoft.lis.schema.ES_DOC_PAGESSchema;
import com.sinosoft.lis.vschema.ES_DOC_MAINSet;
import com.sinosoft.lis.vschema.ES_DOC_PAGESSet;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;

public class QueryIssueDocSerivceImpl extends BaseServiceImpl implements
		QueryIssueDocService {
	private LinkedHashMap<String, EsIssueDocVO> returnIssueNoMap = new LinkedHashMap<String, EsIssueDocVO>();
	private Map<String, List> returnPropMap = new HashMap<String, List>();
	private ExeSQL exeSQL = new ExeSQL();
	private String queryChannel = "";
	private ExpandPropertyUtilImpl expandPropertyUtil = new ExpandPropertyUtilImpl();

	public String queryIssue(QueryIssueVo queryIssueVo) throws Exception {
		try {
			String returnStr = null;
			queryChannel = queryIssueVo.getChannel();
			SSRS issueList = queryIssueList(queryIssueVo);

			if (issueList == null || issueList.getMaxRow() == 0) {
				returnStr = "未查询到待处理的问题件";
				return returnStr;
			}

			List docidParam = new ArrayList();

			Map<String, EsIssueDocVO> docIdMap = new HashMap<String, EsIssueDocVO>();

			Map<String, EsIssueDocVO> issueNoMapTemp = new HashMap<String, EsIssueDocVO>();
			for (int i = 1; i <= issueList.getMaxRow(); i++) {

				String issueNo = issueList.GetText(i, 1);
				String docId = issueList.GetText(i, 2);

				docidParam.add(docId);
				EsIssueDocVO esIssueDocVO;
				if (!issueNoMapTemp.containsKey(issueNo)) {
					String issueDesc = issueList.GetText(i, 3) == null ? ""
							: issueList.GetText(i, 3);
					String issueUser = issueList.GetText(i, 4) == null ? ""
							: issueList.GetText(i, 4);
					String issueDate = issueList.GetText(i, 5) + " "
							+ issueList.GetText(i, 6);

					esIssueDocVO = new EsIssueDocVO();
					esIssueDocVO.setIssueNo(issueNo);
					esIssueDocVO.setIssueDesc(issueDesc);
					esIssueDocVO.setIssueUser(issueUser);
					esIssueDocVO.setIssueDate(issueDate);
					esIssueDocVO.setIssueType("");

					issueNoMapTemp.put(issueNo, esIssueDocVO);
				} else {
					esIssueDocVO = issueNoMapTemp.get(issueNo);
				}

				docIdMap.put(docId, esIssueDocVO);
				returnIssueNoMap.put(issueNo, null);
			}
			// List esQcList = queryIssueDocDao.queryDocInfo(docidParam);
			List esQcList = queryDocInfo(docidParam, queryIssueVo, docIdMap);
			if (esQcList == null || esQcList.size() == 0) {
				returnStr = "未查询到待处理的问题件的单证信息";
				return returnStr;
			}
			List<ExpandPropertyVo> expPropVoList = null;
			for (int i = 0; i < esQcList.size(); i++) {
				EsQcMain esQcMain = (EsQcMain) esQcList.get(i);
				String docId = esQcMain.getDocId();

				queryDocProp(docId, expPropVoList);
				EsIssueDocVO tempVO = docIdMap.get(esQcMain.getDocId());
				String issueNo = tempVO.getIssueNo();
				if (!returnIssueNoMap.containsKey(issueNo)) {
					tempVO.getEsQcList().add(esQcMain);
					returnIssueNoMap.put(issueNo, tempVO);
				} else {
					EsIssueDocVO esIssueDocVO = returnIssueNoMap.get(issueNo);
					if(esIssueDocVO!=null&&esIssueDocVO.getEsQcList().size()>0){
						esIssueDocVO.getEsQcList().add(esQcMain);
					}else{
						tempVO.getEsQcList().add(esQcMain);
						returnIssueNoMap.put(issueNo, tempVO);
					}
					
				}
			}
			return returnStr;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "查询问题件出现异常"+e.getMessage();
		}
		
	}

	/**
	 * 查询问题件信息
	 * 
	 * @param docidParam
	 * @param queryIssueVo
	 * @param docIdMap
	 * @return
	 */
	public List queryDocInfo(List docidParam, QueryIssueVo queryIssueVo,
			Map<String, EsIssueDocVO> docIdMap) {
		StringBuffer querySql = new StringBuffer(
				"select a.* from es_doc_main a ");

		querySql.append(" where docid in (?docid?");

		ArrayList<String> docidArr=new ArrayList<String>();
		for (int i = 0; i < docidParam.size(); i++) {
//			if (i == 0) {
//				querySql.append(docidParam.get(i));
//			} else {
//				querySql.append("," + docidParam.get(i));
//			}
			docidArr.add(docidParam.get(i).toString());
		}
		querySql.append(") ");
		List esList = new ArrayList();
		ES_DOC_MAINDB es_doc_maindb = new ES_DOC_MAINDB();
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(querySql.toString());
		sqlbv.put("docid", docidArr);
		ES_DOC_MAINSet eSet = es_doc_maindb.executeQuery(sqlbv);
		for (int j = 1; j <= eSet.size(); j++) {
			ES_DOC_MAINSchema eSchema = eSet.get(j);
			ES_DOC_PAGESDB es_doc_pagesdb = new ES_DOC_PAGESDB();
			es_doc_pagesdb.setDocID(eSchema.getDocID());
			ES_DOC_PAGESSet pagesSet = es_doc_pagesdb.query();
			ES_DOC_PAGESSchema pageSchema = pagesSet.get(1);
			String scandate = pageSchema.getMakeDate();
			String scanTime = pageSchema.getMakeTime();
			Date tscanDate;
			try {
				if (scanTime.length() > 8) {
					tscanDate = FDate.formatYMDHMSSToDate(scandate + " "
							+ scanTime);
				} else {
					tscanDate = FDate.formatYMDHMSToDate(scandate + " "
							+ scanTime);
				}
			} catch (ParseException e) {
				tscanDate = new Date();
				e.printStackTrace();
			}
			EsQcMain esQcMain = new EsQcMain();
			esQcMain.setChannel(queryIssueVo.getChannel());
			EsDocMainPToEsQcMain.esDocMainPToEsQcMain(tscanDate, eSchema,
					esQcMain);
			esList.add(esQcMain);
		}
		return esList;
	}

	/**
	 * 查询问题件列表
	 * 
	 * @param queryIssueVo
	 * @return
	 */
	public SSRS queryIssueList(QueryIssueVo queryIssueVo) {
		StringBuffer querySql = new StringBuffer(
				"select a.issuedocid,c.docid,a.issuedesc,a.promptoperator,"
						+ " a.makedate,a.maketime from  es_issuedoc a, es_doc_relation b,es_doc_main c "
						+ " where 1 = 1 " + " and a.bussno = b.bussno "
						+ " and a.bussnotype = b.bussnotype "
						+ " and a.busstype = b.busstype "
						+ " and a.subtype = b.subtype "
						+ " and b.docid = c.docid " + " and a.status  = '0'"
						+ " and a.result = '0'");
		if (queryIssueVo.getIssueUser() != null
				&& !"".equals(queryIssueVo.getIssueUser())) {
			querySql.append(" and a.promptoperator =  ");
			querySql.append("'?promptoperator?'");
		}
		if (queryIssueVo.getGroupNo() != null
				&& !"".equals(queryIssueVo.getGroupNo())) {
			querySql.append(" and c.doccode =  ");
			querySql.append("'?doccode?'");
		}
		if (queryIssueVo.getIssueStartDate() != null
				&& !"".equals(queryIssueVo.getIssueStartDate())) {
			querySql.append(" and a.makedate >= to_date(");
			querySql.append("'?StartDate?','yyyy-mm-dd')");
		}
		if (queryIssueVo.getIssueEndDate() != null
				&& !"".equals(queryIssueVo.getIssueEndDate())) {
			querySql.append(" and a.makedate < to_date(");
			querySql.append("'?EndDate?','yyyy-mm-dd')");
		}
		if (queryIssueVo.getManageCom() != null
				&& !"".equals(queryIssueVo.getManageCom())) {
			querySql.append(" and c.managecom like  ");
			querySql.append("concat('?managecom?','%')");
		}
		if (queryIssueVo.getBussType() != null
				&& !"".equals(queryIssueVo.getBussType())) {
			querySql.append(" and a.busstype =  ");
			querySql.append("'?busstype?'");
		}
		if (queryIssueVo.getIssueNo() != null
				&& !"".equals(queryIssueVo.getIssueNo())) {
			querySql.append(" and a.issuedocid = ");
			querySql.append("'?issuedocid?'");
		}
		querySql.append("order by a.makedate asc ,a.maketime asc");
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(querySql.toString());
		sqlbv.put("promptoperator", queryIssueVo.getIssueUser());
		sqlbv.put("doccode", queryIssueVo.getGroupNo());
		sqlbv.put("StartDate", queryIssueVo.getIssueStartDate());
		sqlbv.put("EndDate", queryIssueVo.getIssueEndDate());
		sqlbv.put("managecom", queryIssueVo.getManageCom());
		sqlbv.put("busstype", queryIssueVo.getBussType());
		sqlbv.put("issuedocid", queryIssueVo.getIssueNo());
		SSRS nSSRS = exeSQL.execSQL(sqlbv);
		return nSSRS;

	}

	public Map getReturnIssue() {
		return returnIssueNoMap;
	}

	public Map getReturnProp() {
		return returnPropMap;
	}

	public void queryDocProp(String docId, List<ExpandPropertyVo> expPropVoList)
			throws Exception {
		expPropVoList = expandPropertyUtil.queryExpNameAndValueChannel(docId);
		// expPropVoList = null;
		if (expPropVoList != null && expPropVoList.size() > 0) {
			returnPropMap.put(docId, expPropVoList);
		}
	}
}
