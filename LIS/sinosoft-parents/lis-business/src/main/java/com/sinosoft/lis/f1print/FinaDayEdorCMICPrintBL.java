package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.CodeNameQuery;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/*******************************************************************************
 * <p>
 * Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved
 * </p>
 * <p>
 * WebSite: http://www.sinosoft.com.cn
 * </p>
 * 
 * @todo : 保全-要件变更日结
 * @author : liuxiaosong
 * @version : 1.00
 * @date : 2006-11-28
 ******************************************************************************/
public class FinaDayEdorCMICPrintBL implements PrintService {
private static Logger logger = Logger.getLogger(FinaDayEdorCMICPrintBL.class);
	public CErrors mErrors = new CErrors();
	private VData mInputData;
	private VData mResult = new VData();

	// 数据操作字符串
	private String mOperate;

	/** 业务数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();
	private String mManageCom = "";
	private String mStartDate = "";
	private String mEndDate = "";
	private String mCurrentDate = PubFun.getCurrentDate(); // 当前时间
	public String VTS_NAME = "EdorCMICDayFina.vts"; // 模板名称

	public FinaDayEdorCMICPrintBL() {
	}

	/**
	 * 高层类调用本类接口
	 * 
	 * @param: cInputData 输入的数据
	 * @param: cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		if (!mOperate.equals("PRINT")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FinaDayEdorCMICPrintBL";
			tError.functionName = "submitData";
			tError.errorMessage = "不支持的操作字符串！"; // 验证这个有意义吗
			this.mErrors.addOneError(tError);

			return false;
		}

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		// 准备需要打印的数据
		if (!preparePrintData()) {
			return false;
		}

		return true;
	}

	/**
	 * 获得外部数据
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);

		if (mGlobalInput == null || mTransferData == null) {
			mErrors.addOneError(new CError("无法获得输入数据！"));
			return false;
		}

		// 开始日期 startdate
		mStartDate = (String) mTransferData.getValueByName("StartDate");
		if (mStartDate == null || mStartDate.trim().equals("")) {
			mErrors.addOneError(new CError("统计起期不能为空！"));
			return false;
		}
		// 结束日期 enddate
		mEndDate = (String) mTransferData.getValueByName("EndDate");
		if (mEndDate == null || mEndDate.trim().equals("")) {
			mErrors.addOneError(new CError("统计止期不能为空！"));
			return false;
		}

		// 管理机构 managecom
		mManageCom = (String) mTransferData.getValueByName("ManageCom");
		if (mManageCom == null || mManageCom.trim().equals("")) {
			mManageCom = mGlobalInput.ManageCom;
		}
		return true;
	}

	/**
	 * 准备打印数据
	 * 
	 * @return boolean
	 */
	private boolean preparePrintData() {
		// 初始化打印数据容器------------------------------------------------------
		XmlExport tXmlExport = new XmlExport();
		tXmlExport.createDocument(VTS_NAME, "printer");
		TextTag texttag = new TextTag();
		ListTable tListContentTable = new ListTable();
		tListContentTable.setName("LJAGetEdorsList");
		// ----------------------------------------------------------------------

		// 填充业务数据-----------------------------------------------------------
		String strResult[] = null; // 结果
		String strTitle[] = { "contno", "BF", "TF", "LX", "EF", "YF", "Total" }; // 标题

		SSRS tSSRS = new SSRS();
		ExeSQL texesql = new ExeSQL();
		String strSQL = "";
		// 按保单号明细记录
		// strSQL = " select contno, "
		// + " (select nvl(sum(t.getmoney), 0) from ljagetendorse t "
		// + " where t.endorsementno = c.endorsementno and t.contno = c.contno
		// and t.feeoperationtype = c.feeoperationtype and t.feefinatype =
		// 'BF'), "
		// + " (select nvl(sum(t.getmoney), 0) from ljagetendorse t "
		// + " where t.endorsementno = c.endorsementno and t.contno = c.contno
		// and t.feeoperationtype = c.feeoperationtype and t.feefinatype =
		// 'TF'), "
		// + " (select nvl(sum(t.getmoney), 0) from ljagetendorse t "
		// + " where t.endorsementno = c.endorsementno and t.contno = c.contno
		// and t.feeoperationtype = c.feeoperationtype and t.feefinatype =
		// 'LX'), "
		// + " (select nvl(sum(t.getmoney), 0) from ljagetendorse t "
		// + " where t.endorsementno = c.endorsementno and t.contno = c.contno
		// and t.feeoperationtype = c.feeoperationtype and t.feefinatype =
		// 'EF'), "
		// + " (select nvl(sum(t.getmoney), 0) from ljagetendorse t "
		// + " where t.endorsementno = c.endorsementno and t.contno = c.contno
		// and t.feeoperationtype = c.feeoperationtype and t.feefinatype =
		// 'YF'), "
		// + " (select nvl(sum(t.getmoney), 0) from ljagetendorse t "
		// + " where t.endorsementno = c.endorsementno and t.contno = c.contno
		// and t.feeoperationtype = c.feeoperationtype ) "
		// + " from ljagetendorse c "
		// +
		// " where c.feeoperationtype in ('CM','IC') and c.feefinatype
		// in('YF','EF') "
		// + " and c.makedate >= date '" + mStartDate + "' "
		// + " and c.makedate <= date '" + mEndDate + "' "
		// + " and c.managecom like '" + mManageCom + "%' "
		// + " order by c.endorsementno ";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		strSQL = "select contno,"
				+ "sum(case when a.feefinatype = 'BF' then a.money else 0 end),"
				+ "sum(case when a.feefinatype = 'TF' then a.money else 0 end),"
				+ "sum(case when a.feefinatype = 'LX' then a.money else 0 end),"
				+ "sum(case when a.feefinatype = 'EF' then a.money else 0 end),"
				+ "sum(case when a.feefinatype = 'YF' then a.money else 0 end),"
				+ "sum(a.money) " + " from "
				+ " (select contno, feefinatype, sum(getmoney) money"
				+ " from ljagetendorse"
				+ " where feeoperationtype in ('CM', 'IC')"
				+ " and makedate >= '"
				+ "?mStartDate?"
				+ "'"
				+ " and makedate <= '"
				+ "?mEndDate?"
				+ "'"
				+ " and managecom like concat('"
				+ "?mManageCom?"
				+ "','%')"
				+ " and contno =any(select contno"
				+ " from ljagetendorse"
				+ " where feeoperationtype in ('CM', 'IC')"
				+ " and makedate >= '"
				+ "?mStartDate?"
				+ "'"
				+ " and makedate <= '"
				+ "?mEndDate?"
				+ "'"
				+ " and managecom like concat('"
				+ "?mManageCom?"
				+ "','%')"
				+ " and feefinatype in ('YF', 'EF'))"
				+ " group by contno, feefinatype) a" + " group by a.contno";
		logger.debug("\t@>==========按保单明细SQL：");
		sqlbv1.sql(strSQL);
		sqlbv1.put("mStartDate", mStartDate);
		sqlbv1.put("mEndDate", mEndDate);
		sqlbv1.put("mManageCom", mManageCom);
		tSSRS = texesql.execSQL(sqlbv1);
		if (texesql.mErrors.needDealError()) {
			CError.buildErr(this, "打印数据查询出错！");
			return false;
		}

		int tCount = tSSRS.getMaxRow();
		if (tSSRS == null || tCount <= 0) {
			CError.buildErr(this, mStartDate + "至" + mEndDate + "之间没有收付费记录！");
			return false;
		}

		// 获取查询结果装入模板打印容器
		try {
			double dResult_All[] = new double[6]; // 合计

			// 装入明细记录
			for (int i = 1; i <= tCount; i++) {
				strResult = new String[7]; // 模板共七列
				for (int j = 1; j <= tSSRS.getMaxCol(); j++) {
					strResult[j - 1] = tSSRS.GetText(i, j);
					if (j != 1) {
						dResult_All[j - 2] += Double
								.parseDouble(strResult[j - 1]);
					}
				}
				tListContentTable.add(strResult);
			}

			// 装入总计记录
			String strResult_All[] = new String[7];
			strResult_All[0] = "总计";
			for (int j = 1; j <= 6; j++) {
				strResult_All[j] = String.valueOf(dResult_All[j - 1]);
			}
			tListContentTable.add(strResult_All); // 将总计信息数组加入，方便模板中统一处理
		} catch (Exception ex) {
			logger.debug("\t@>===========CMIC日结后台错误：" + ex.toString());
			CError.buildErr(this, "生成打印数据时出错！");
			return false;
		}

		// ----------------------------------------------------------------------

		// 填充其他信息------------------------------------------------------------
		String tManageCom = BqNameFun.getCBranch(mManageCom);
		texttag.add("ManageCom", tManageCom);
		texttag.add("StartDate", mStartDate);
		texttag.add("EndDate", mEndDate);
		texttag.add("CurrentDate", mCurrentDate);
		texttag.add("CurrentTime", StrTool.getHour() + "时"
				+ StrTool.getMinute() + "分" + StrTool.getSecond() + "秒");
		texttag.add("Operator", CodeNameQuery
				.getOperator(mGlobalInput.Operator));
		if (texttag.size() > 0) {
			tXmlExport.addTextTag(texttag);
		}

		// ----------------------------------------------------------------------

		// 组装打印数据-----------------------------------------------------------
		if (tListContentTable.size() > 0) {
			tXmlExport.addListTable(tListContentTable, strTitle);
		}

		if (texttag.size() > 0) {
			tXmlExport.addTextTag(texttag);
		}

		// ----------------------------------------------------------------------
		mResult.clear();
		mResult.addElement(tXmlExport);

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}
}
