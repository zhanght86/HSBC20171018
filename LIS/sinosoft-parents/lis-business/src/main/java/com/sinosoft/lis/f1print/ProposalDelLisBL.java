package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;
/**
 * <p>Title:承保单删除清单 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author ChenRong
 * @version 6.0
 */

public class ProposalDelLisBL {
private static Logger logger = Logger.getLogger(ProposalDelLisBL.class);
	/**
	 * 错误处理类，每个需要错误处理的类中都放置该类
	 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	private TransferData mTransferData = new TransferData();

	// 全局数据
	private String mManageCom = "";
	private String mOperator = "";
	private String mDelReason = "";
	private String mStartDate = "";
	private String mEndDate = "";

	private GlobalInput mGlobalInput = new GlobalInput();

	public ProposalDelLisBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {

		if (!cOperate.equals("CONFIRM") && !cOperate.equals("PRINT")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}

		// 得到外部传入的数据，将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		mResult.clear();

		// 准备所有要打印的数据
		if (!getPrintData()) {
			return false;
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		if (mGlobalInput == null) {
			buildError("mGlobalInput", "没有得到足够的信息！");
			return false;
		}

		mTransferData = ((TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0));
		if (mTransferData == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}

		mStartDate = ((String) mTransferData.getValueByName("StartDate"))
				.trim();
		mEndDate = ((String) mTransferData.getValueByName("EndDate")).trim();
		mManageCom = ((String) mTransferData.getValueByName("ManageCom"))
				.trim();
		mDelReason = ((String) mTransferData.getValueByName("DelReason"))
				.trim();
		mOperator = ((String) mTransferData.getValueByName("Operator")).trim();
		return true;
	}

	/**
	 * 打印处理
	 * 
	 * @return boolean
	 */
	private boolean getPrintData() {
		int tResultCount = 0;
		int tTotalPolCount = 0;
		ListTable ListTable = new ListTable();
		ListTable.setName("RISK");
		String[] Title = new String[19];
		Title[0] = "";
		Title[1] = "";
		Title[2] = "";
		Title[3] = "";
		Title[4] = "";
		Title[5] = "";
		Title[6] = "";
		Title[7] = "";
		Title[8] = "";
		Title[9] = "";
		Title[10] = "";
		Title[11] = "";
		Title[12] = "";
		Title[13] = "";
		Title[14] = "";
		Title[15] = "";
		Title[16] = "";
		Title[17] = "";
		Title[18] = "";
		String tSql = "";

		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";

		ExeSQL exeSql = new ExeSQL();
		SSRS ManageTypeSSRS = new SSRS();
		String tManageTypeSQL = "";
		String tName = "";

		tManageTypeSQL = "select comcode,name from ldcom where comcode = '"
				+ "?mManageCom?" + "' order by comcode asc";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tManageTypeSQL);
		sqlbv1.put("mManageCom", mManageCom);
		ManageTypeSSRS = exeSql.execSQL(sqlbv1);

		if (ManageTypeSSRS.getMaxRow() > 0) {
			tName = ManageTypeSSRS.GetText(1, 2);
		}

		/**
		 * @todo 如果录入的机构为总公司，则循环取总公司下的所有分公司，按分公司输出。 如果录入的机构为分公司，则按中支公司进行输出
		 */

		if (mManageCom.length() <= 6) {
			tManageTypeSQL = " select comcode,name from ldcom where upcomcode = '"
					+ "?mManageCom?" + "' order by comcode asc";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(tManageTypeSQL);
			sqlbv2.put("mManageCom", mManageCom);
			ManageTypeSSRS = exeSql.execSQL(sqlbv2);
		}

		/**
		 * @todo 循环下属公司
		 */
		for (int i = 0; i < ManageTypeSSRS.getMaxRow(); i++) {
			// 取得公司名称
			String tManageName = ManageTypeSSRS.GetText(i + 1, 2);
			String[] str1 = new String[19];
			if (mManageCom.equals("86")) {
				str1[0] = "总公司:";
				str1[3] = "分公司：";
			} else if (mManageCom.length() == 4) {
				str1[0] = "分公司：";
				str1[3] = "中心支公司：";
			} else if (mManageCom.length() == 6) {
				str1[0] = "中心支公司：";
				str1[3] = "营销服务部：";
			}

			str1[1] = tName;
			str1[2] = "";
			str1[4] = tManageName;
			;
			str1[5] = "";
			str1[6] = "合计件数：";
			str1[7] = "";
			str1[8] = "";
			str1[9] = "";
			str1[10] = "";
			str1[11] = "";
			str1[12] = "";
			str1[13] = "";
			str1[14] = "";
			str1[15] = "";
			str1[16] = "";
			str1[17] = "";
			str1[18] = "";

			String[] str3 = new String[19];
			str3[0] = "保单号";
			str3[1] = "投保单号";
			str3[2] = "险种";
			str3[3] = "投保日期";
			str3[4] = "收费日期";
			str3[5] = "生效日期";
			str3[6] = "签单日期";
			str3[7] = "回单日期";
			str3[8] = "删除日期";
			str3[9] = "投保人";
			str3[10] = "被保人";
			str3[11] = "保费";
			str3[12] = "缴费期限";
			str3[13] = "业务员";
			str3[14] = "业务号";
			str3[15] = "营销服务部";
			str3[16] = "营业部";
			str3[17] = "删除原因";
			str3[18] = "备注";
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				tSql = " select a.contno,a.prtno,a.riskcode,a.makedate,"
						+ "(select makedate from ljtempfeeclass j where j.otherno=a.prtno and rownum=1),"
						+ "a.cvalidate,"
						+ "(select c.signdate from lbcont c where c.contno=b.otherno) s,"
						+ "(select case when d.makedate is null then '' else to_char(d.makedate,'yyyy-mm-dd') end from lzsyscertify d "
						+ "where trim(d.certifycode)='9995' and trim(d.certifyno)=trim(b.otherno)),"
						+ "b.makedate,a.appntname,"
						+ "a.insuredname,a.prem,a.payendyear,"
						+ "(select d.name from laagent d where d.agentcode=trim(a.agentcode)),"
						+ "a.agentcode,"
						+ "(select lc.name from ldcom lc where lc.comcode=a.managecom),"
						+ "(select c.branchseries from labranchgroup c"
						+ " where c.agentgroup = trim(a.agentgroup)),"
						+ "(select cd.codename from ldcode cd "
						+ "where cd.codetype='proposaldelreason' and trim(cd.code)=trim(b.delreason))"
						+ ",b.remark " + "from lbpol a,lcdelpollog b"
						+ " where a.prtno=b.prtno and a.contno=b.otherno and"
						+ " b.othernotype='12' and b.ispolflag='1'"
						+ " and b.makedate between '" + "?mStartDate?" + "' and '"
						+ "?mEndDate?" + "' and a.managecom like concat('"
						+ "?managecom?" + "','%')";
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				tSql = " select a.contno,a.prtno,a.riskcode,a.makedate,"
						+ "(select makedate from ljtempfeeclass j where j.otherno=a.prtno limit 0,1),"
						+ "a.cvalidate,"
						+ "(select c.signdate from lbcont c where c.contno=b.otherno) s,"
						+ "(select case when d.makedate is null then '' else to_char(d.makedate,'yyyy-mm-dd') end from lzsyscertify d "
						+ "where trim(d.certifycode)='9995' and trim(d.certifyno)=trim(b.otherno)),"
						+ "b.makedate,a.appntname,"
						+ "a.insuredname,a.prem,a.payendyear,"
						+ "(select d.name from laagent d where d.agentcode=trim(a.agentcode)),"
						+ "a.agentcode,"
						+ "(select lc.name from ldcom lc where lc.comcode=a.managecom),"
						+ "(select c.branchseries from labranchgroup c"
						+ " where c.agentgroup = trim(a.agentgroup)),"
						+ "(select cd.codename from ldcode cd "
						+ "where cd.codetype='proposaldelreason' and trim(cd.code)=trim(b.delreason))"
						+ ",b.remark " + "from lbpol a,lcdelpollog b"
						+ " where a.prtno=b.prtno and a.contno=b.otherno and"
						+ " b.othernotype='12' and b.ispolflag='1'"
						+ " and b.makedate between '" + "?mStartDate?" + "' and '"
						+ "?mEndDate?" + "' and a.managecom like concat('"
						+ "?managecom?" + "','%')";
			}
			
			// + " and b.delreason='" + mDelReason
			// + " order by a.contno,a.polno,b.makedate,s,a.managecom,"
			// + "a.agentgroup,a.agentcode desc";
			StringBuffer strSQL = new StringBuffer();
			strSQL.append(tSql);
			tSql = "";
			if (mDelReason != null && !mDelReason.equals("")) {
				tSql = " and b.delreason='" + "?mDelReason?" + "'";
				strSQL.append(tSql);
			}
			tSql = "";
			tSql = " order by b.makedate desc,s desc,a.managecom,"
					+ "a.agentgroup,a.agentcode,a.contno,a.polno asc";
			strSQL.append(tSql);
			logger.debug("strSql" + strSQL.toString());
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(strSQL.toString());
			sqlbv3.put("mStartDate", mStartDate);
			sqlbv3.put("mEndDate", mEndDate);
			sqlbv3.put("managecom", ManageTypeSSRS.GetText(i + 1, 1));
			sqlbv3.put("mDelReason", mDelReason);
			SSRS temp2SSRS = new SSRS();
			temp2SSRS = exeSql.execSQL(sqlbv3);

			if (temp2SSRS != null && temp2SSRS.getMaxRow() > 0
					&& temp2SSRS.getMaxCol() > 0) {
				int tPolCount = 0;
				for (int k = 1; k <= temp2SSRS.getMaxRow(); k++) {
					if (k == 1) {
						tPolCount = 1;
					} else {
						if (!temp2SSRS.GetText(k, 1).equals(
								temp2SSRS.GetText(k - 1, 1)))
							tPolCount = tPolCount + 1;
					}
				}
				tTotalPolCount = tTotalPolCount + tPolCount;
				str1[7] = new Integer(tPolCount).toString();
				ListTable.add(str1);
				ListTable.add(str3);
				logger.debug("temp2SSRS.getMaxRow()"
						+ temp2SSRS.getMaxRow());
				tResultCount = tResultCount + temp2SSRS.getMaxRow();
				for (int l = 1; l <= temp2SSRS.getMaxRow(); l++) {
					String[] stra = new String[19];
					String tBranchSeries = temp2SSRS.GetText(l, 17);
					stra[0] = temp2SSRS.GetText(l, 1);
					stra[1] = temp2SSRS.GetText(l, 2);
					stra[2] = temp2SSRS.GetText(l, 3);
					stra[3] = temp2SSRS.GetText(l, 4);
					stra[4] = temp2SSRS.GetText(l, 5);
					stra[5] = temp2SSRS.GetText(l, 6);
					stra[6] = temp2SSRS.GetText(l, 7);
					stra[7] = temp2SSRS.GetText(l, 8);
					stra[8] = temp2SSRS.GetText(l, 9);
					stra[9] = temp2SSRS.GetText(l, 10);
					stra[10] = temp2SSRS.GetText(l, 11);
					stra[11] = temp2SSRS.GetText(l, 12);
					stra[12] = temp2SSRS.GetText(l, 13);
					stra[13] = temp2SSRS.GetText(l, 14);
					stra[14] = temp2SSRS.GetText(l, 15);
					stra[15] = temp2SSRS.GetText(l, 16);
					stra[16] = getBranchName(tBranchSeries);
					stra[17] = temp2SSRS.GetText(l, 18);
					stra[18] = temp2SSRS.GetText(l, 19);
					ListTable.add(stra);
				}
			}

			String[] str5 = new String[19]; // 空行
			str5[0] = "  ";
			str5[1] = "  ";
			str5[2] = "  ";
			str5[3] = "  ";
			str5[4] = "  ";
			str5[5] = "  ";
			str5[6] = "  ";
			str5[7] = "  ";
			str5[8] = "  ";
			str5[9] = "  ";
			str5[10] = "  ";
			str5[11] = "  ";
			str5[12] = "  ";
			str5[13] = "  ";
			str5[14] = "  ";
			str5[15] = "  ";
			str5[16] = "  ";
			str5[17] = "  ";
			str5[18] = "  ";

			String[] str6 = new String[19]; // 空行
			str6[0] = "  ";
			str6[1] = "  ";
			str6[2] = "  ";
			str6[3] = "  ";
			str6[4] = "  ";
			str6[5] = "  ";
			str6[6] = "  ";
			str6[7] = "  ";
			str6[8] = "  ";
			str6[9] = "  ";
			str6[10] = "  ";
			str6[11] = "  ";
			str6[12] = "  ";
			str6[13] = "  ";
			str6[14] = "  ";
			str6[15] = "  ";
			str6[16] = "  ";
			str6[17] = "  ";
			str6[18] = "  ";

			if (temp2SSRS.getMaxRow() > 0) {
				ListTable.add(str5);
				ListTable.add(str6);
			}
		}

		if (tResultCount == 0) {
			CError tError = new CError();
			tError.moduleName = "ProposalDelLisBL";
			tError.functionName = "getPrintData";
			tError.errorMessage = "在该查询条件内没有打印信息，请确认查询条件是否正确！！！";
			this.mErrors.addOneError(tError);
			return false;
		}

		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
		xmlexport.createDocument("ProposalDelLis.vts", "");

		texttag.add("ListTitle", "个人业务整单删除清单");

		texttag.add("PrintDate", SysDate);

		texttag.add("Date", mStartDate + "至" + mEndDate);

		texttag.add("TotalCount", tTotalPolCount);

		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
			xmlexport.addListTable(ListTable, Title);
		}
		// xmlexport.outputDocumentToFile("d:\\", "testHZM"); //输出xml文档到文件

		mResult.clear();

		mResult.addElement(xmlexport);

		logger.debug("xmlexport=" + xmlexport);

		return true;
	}

	private String getBranchName(String mBranchSeries) {
		int tInd = 0;
		String tBranchSeries = mBranchSeries;
		String tSql = "";
		SSRS tempSSRS = new SSRS();
		ExeSQL exeSql = new ExeSQL();
		tInd = tBranchSeries.indexOf(":");
		tBranchSeries = tBranchSeries.substring(tInd + 1, tBranchSeries
				.length());
		tInd = tBranchSeries.indexOf(":");
		if (tInd > 0) {
			tBranchSeries = tBranchSeries.substring(0, tInd);
		}
		tSql = " select name from labranchgroup where agentgroup='"
				+ "?tBranchSeries?" + "'";
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(tSql);
		sqlbv4.put("tBranchSeries", tBranchSeries);
		tempSSRS = exeSql.execSQL(sqlbv4);
		if (tempSSRS.getMaxRow() > 0)
			return tempSSRS.GetText(1, 1);
		else
			return "";
	}

	/**
	 * 返回处理信息
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回错误信息
	 * 
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}

	/**
	 * 错误构建
	 * 
	 * @param mFunctionName
	 *            String
	 * @param mErrorMsg
	 *            String
	 */
	private void buildError(String mFunctionName, String mErrorMsg) {
		CError cError = new CError();
		cError.moduleName = "ProposalDelLisBL";
		cError.functionName = mFunctionName;
		cError.errorMessage = mErrorMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
		ProposalDelLisBL proposaldellisbl = new ProposalDelLisBL();
	}
}
