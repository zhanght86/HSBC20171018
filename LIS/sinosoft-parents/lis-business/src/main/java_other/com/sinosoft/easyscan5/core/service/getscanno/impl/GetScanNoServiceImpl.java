package com.sinosoft.easyscan5.core.service.getscanno.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.sinosoft.easyscan5.base.service.impl.BaseServiceImpl;
import com.sinosoft.easyscan5.common.Constants;
import com.sinosoft.easyscan5.core.service.getscanno.IGetScanNoService;
import com.sinosoft.easyscan5.core.vo.easyscan.GetScanNoVo;
import com.sinosoft.easyscan5.core.vo.easyscan.QueryHisScanNoResVo;
import com.sinosoft.lis.db.ES_DOC_ScanNoDB;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.ES_DOC_ScanNoSchema;
import com.sinosoft.lis.vschema.ES_DOC_ScanNoSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

public class GetScanNoServiceImpl extends BaseServiceImpl implements
		IGetScanNoService {

	private Logger logger = Logger.getLogger(GetScanNoServiceImpl.class);
	private GetScanNoVo tempGetScanNoVo;
	private String newScanNo;
	List scanNoCountList = new ArrayList();
	private ExeSQL exeSQL = new ExeSQL();

	/**
	 * ��ȡ��ų���
	 * 
	 * @param paramMap
	 * @return
	 */
	public String getNewScanNo(GetScanNoVo getScanNoVo) {
		tempGetScanNoVo = getScanNoVo;
		String returnStr = null;
		try {

			List list = findUntappedScanNo(getScanNoVo.getManageCom());
			if (list != null && list.size() > 0) {
				returnStr = "存在未使用的箱号：" + (list.get(0).toString())
						+ "，请使用历史箱号查询!";
				return returnStr;
			}
			// ��ȡ������
			newScanNo = getNewMaxScanNo(getScanNoVo.getManageCom(),
					getScanNoVo.getChannel());
			if (newScanNo == null || "".equals(newScanNo)) {
				returnStr = "生成新箱号失败!";
				return returnStr;
			}
			if (!saveEsScanNo()) {
				returnStr = "生成箱号失败";
				logger.error("生成箱号失败");
				return returnStr;
			}
		} catch (Exception e) {
			returnStr = "生成箱号失败";
			logger.error("生成箱号失败:" + e.toString());
		}
		return returnStr;
	}

	/**
	 * 查询未使用的箱号
	 * 
	 * @param manageCom
	 * @return
	 */
	public List findUntappedScanNo(String manageCom) {
		StringBuffer strSQL = new StringBuffer(
				"select scanno from es_doc_scanno where " + " managecom='?manageCom?'");
		strSQL.append(" and scanno not in"
				+ " (select distinct scanno from es_doc_main where managecom='?manageCom?') order by makedate desc,maketime desc");
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(strSQL.toString());
		sqlbv.put("manageCom", manageCom);
		String countScanNo = exeSQL.getOneValue(sqlbv);
		List list = new ArrayList();
		if (countScanNo != null && !"".equals(countScanNo)) {
			list.add(countScanNo);
		}
		return list;
	}

	public boolean saveEsScanNo() {

		ES_DOC_ScanNoSchema tES_DOC_ScanNoSchema = new ES_DOC_ScanNoSchema();
		tES_DOC_ScanNoSchema.setScanNo(newScanNo);
		tES_DOC_ScanNoSchema.setScanOperator(tempGetScanNoVo.getScanUser());
		tES_DOC_ScanNoSchema.setManageCom(tempGetScanNoVo.getManageCom());
		tES_DOC_ScanNoSchema.setOperator(tempGetScanNoVo.getScanUser());
		String currentDate = PubFun.getCurrentDate();
		String currentTime = PubFun.getCurrentTime();
		tES_DOC_ScanNoSchema.setMakeDate(currentDate);
		tES_DOC_ScanNoSchema.setMakeTime(currentTime);
		tES_DOC_ScanNoSchema.setModifyDate(currentDate);
		tES_DOC_ScanNoSchema.setModifyTime(currentTime);
		MMap tMMap = new MMap();
		tMMap.put(tES_DOC_ScanNoSchema, "INSERT");
		VData tVData = new VData();
		tVData.add(tMMap);
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(tVData, "INSERT")) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param mManageCom
	 * @return
	 */
	public String getNewMaxScanNo(String mManageCom, String channel) {
		String tManageCom = mManageCom;
		String strNo = "";

		strNo = PubFun1.CreateMaxNo(Constants.SEQ_SCANNO, tManageCom);

		return strNo;
	}

	/**
	 * ��ȡ����Ž��
	 * 
	 * @return
	 */
	public String getNewScanNoResult() {
		return newScanNo;
	}

	/**
	 * 查询历史箱号
	 * 
	 * @param getScanNoVo
	 * @return
	 */
	public String queryHisScanNo(GetScanNoVo getScanNoVo) {
		tempGetScanNoVo = getScanNoVo;
		String strMessage = null;
		try {
			// List scanNoList = getScanNoDao.queryHisScanNo(getScanNoVo);
			ES_DOC_ScanNoSet eScanNoSet = findHisScanNo(getScanNoVo);
			if (eScanNoSet == null || eScanNoSet.size() == 0) {
				strMessage = "未查询到历史箱号，请确认查询条件！";
				return strMessage;
			}
			// List scanCountList = getScanNoDao.findScanNoCount(getScanNoVo,
			// scanNoList);
			SSRS ssrs = findScanNoDocCount(getScanNoVo, eScanNoSet);
			if (ssrs != null && ssrs.getMaxRow() > 0) {
				String strScanNo = "";
				String strScanNoDocCount = "";
				String strScanNoPageCount = "";

				for (int i = 1; i <= ssrs.getMaxRow(); i++) {
					QueryHisScanNoResVo queryHisScanNoResVo = new QueryHisScanNoResVo();
					strScanNoDocCount = ssrs.GetText(i, 1) == null ? "" : ssrs
							.GetText(i, 1);
					strScanNoPageCount = ssrs.GetText(i, 2) == null ? "" : ssrs
							.GetText(i, 2);
					strScanNo = ssrs.GetText(i, 3) == null ? "" : ssrs.GetText(
							i, 3);
					queryHisScanNoResVo.setScanNo(strScanNo);
					queryHisScanNoResVo.setPageCount(strScanNoPageCount);
					queryHisScanNoResVo.setDocCount(strScanNoDocCount);
					scanNoCountList.add(queryHisScanNoResVo);
				}
			}else{
				for(int i =1;i<=eScanNoSet.size();i++){
					QueryHisScanNoResVo queryHisScanNoResVo = new QueryHisScanNoResVo();
					ES_DOC_ScanNoSchema es_DOC_ScanNoSchema= eScanNoSet.get(i);
					queryHisScanNoResVo.setScanNo(es_DOC_ScanNoSchema.getScanNo());
					queryHisScanNoResVo.setPageCount("0");
					queryHisScanNoResVo.setDocCount("0");
					scanNoCountList.add(queryHisScanNoResVo);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			strMessage = "查询历史箱号失败";
			logger.error("查询历史箱号失败，异常：" + e.toString());
		}
		return strMessage;
	}

	public SSRS findScanNoDocCount(GetScanNoVo getScanNoVo,
			ES_DOC_ScanNoSet eScanNoSet) {
		StringBuffer scanNoParam = new StringBuffer();
		ArrayList<String> scanNoParamArr=new ArrayList<String>();
		for (int i = 1; i <= eScanNoSet.size(); i++) {
//			if (i != 1) {
//				scanNoParam.append(",'" + eScanNoSet.get(i).getScanNo() + "'");
//			} else {
//				scanNoParam.append("'" + eScanNoSet.get(i).getScanNo() + "'");
//			}
			scanNoParamArr.add(eScanNoSet.get(i).getScanNo());
		}
		StringBuffer querySql = new StringBuffer(
				"select count(*) docs,"
						+ " sum(numpages) pages, scanno from es_doc_main where scanno in (?scanNoParam?)"
						+ " group by scanno ");
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(querySql.toString());
		sqlbv.put("scanNoParam", scanNoParamArr);
		SSRS ssrs = exeSQL.execSQL(sqlbv);
		return ssrs;
	}

	/**
	 * ��ѯ��ʷ���
	 * 
	 * @param getScanNoVo
	 * @return
	 */
	public ES_DOC_ScanNoSet findHisScanNo(GetScanNoVo getScanNoVo) {
		StringBuffer queryScanNoBuffer = new StringBuffer();
		queryScanNoBuffer.append("select * from es_doc_scanno where 1=1 ");
		if (getScanNoVo.getScanNo() != null
				&& !"".equals(getScanNoVo.getScanNo())) {
			queryScanNoBuffer.append("and scanNo = '?scanNo?'");
		}
		if (getScanNoVo.getScanUser() != null
				&& !"".equals(getScanNoVo.getScanUser())) {
			queryScanNoBuffer.append("and scanoperator = '?scanoperator?'");
		}
		queryScanNoBuffer.append("and managecom = '?managecom?'");
		if (getScanNoVo.getStartDate() != null
				&& !"".equals(getScanNoVo.getStartDate())) {
			if (getScanNoVo.getEndDate() != null) {
				queryScanNoBuffer.append(" and makedate between ");
				queryScanNoBuffer
						.append("'?StartDate?'");
				queryScanNoBuffer.append("and '?EndDate?'");
			} else {
				queryScanNoBuffer.append(" and makedate>=");
				queryScanNoBuffer
				.append("'?StartDate?'");
			}
		} else {
			if (getScanNoVo.getEndDate() != null
					&& !"".equals(getScanNoVo.getEndDate())) {
				queryScanNoBuffer.append(" AND makedate<=");
				queryScanNoBuffer.append("'?EndDate?'");
			}
		}
		queryScanNoBuffer.append("order by makedate desc,maketime desc");
		ES_DOC_ScanNoDB eScanNoDB = new ES_DOC_ScanNoDB();
		ES_DOC_ScanNoSet eScanNoSet;
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(queryScanNoBuffer.toString());
		sqlbv.put("scanNo", getScanNoVo.getScanNo());
		sqlbv.put("scanoperator", getScanNoVo.getScanUser());
		sqlbv.put("managecom", getScanNoVo.getManageCom());
		sqlbv.put("StartDate", getScanNoVo.getStartDate());
		sqlbv.put("EndDate", getScanNoVo.getEndDate());
		eScanNoSet = eScanNoDB.executeQuery(sqlbv);

		return eScanNoSet;
	}

	public List getHisScanNoResult() {
		return scanNoCountList;
	}

	public static void main(String[] args) {
		while (true) {
			String pattern = "HH:mm:ss";
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			Date today = new Date();
			String tString = df.format(today);
			System.out.println(tString);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
