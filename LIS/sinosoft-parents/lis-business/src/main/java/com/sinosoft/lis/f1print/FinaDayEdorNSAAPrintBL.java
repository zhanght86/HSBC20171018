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
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 加保日结打印类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author：liurx
 * @version：1.0
 * @CreateDate：2005-11-14
 */
public class FinaDayEdorNSAAPrintBL implements PrintService {
private static Logger logger = Logger.getLogger(FinaDayEdorNSAAPrintBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();
	private String mManageCom = "";
	private String mStartDate = "";
	private String mEndDate = "";
	private String mCurrentDate = PubFun.getCurrentDate(); // 当前时间
	public static final String VTS_NAME = "EdorNSAADayFina.vts"; // 模板名称

	public FinaDayEdorNSAAPrintBL() {
	}

	/**
	 * 传输数据的公共方法
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
			tError.moduleName = "FinaDayEdorNSAAPrintBL";
			tError.functionName = "submitData";
			tError.errorMessage = "不支持的操作字符串！";
			this.mErrors.addOneError(tError);

			return false;
		}

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		// 数据校验操作（checkdata)
		if (!checkData()) {
			return false;
		}

		// 准备需要打印的数据
		if (!preparePrintData()) {
			return false;
		}

		return true;
	}

	private boolean getInputData(VData cInputData) {
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		if (mGlobalInput == null || mTransferData == null) {
			mErrors.addOneError(new CError("数据传输不完全！"));
			return false;
		}
		mStartDate = (String) mTransferData.getValueByName("StartDate");
		if (mStartDate == null || mStartDate.trim().equals("")) {
			mErrors.addOneError(new CError("没有得到足够的信息:统计起期不能为空！"));
			return false;
		}
		mEndDate = (String) mTransferData.getValueByName("EndDate");
		if (mEndDate == null || mEndDate.trim().equals("")) {
			mErrors.addOneError(new CError("没有得到足够的信息:统计止期不能为空！"));
			return false;
		}
		mManageCom = (String) mTransferData.getValueByName("ManageCom");
		if (mManageCom == null || mManageCom.trim().equals("")) {
			mManageCom = mGlobalInput.ManageCom;
		}
		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		return true;
	}

	private boolean preparePrintData() {
		XmlExport tXmlExport = new XmlExport();
		tXmlExport.createDocument(VTS_NAME, "printer"); // 初始化xml文档

		TextTag texttag = new TextTag();
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		SSRS tssrs = new SSRS();
		ExeSQL texesql = new ExeSQL();
		String strsql = "";
		// 按险种小计
		// strsql = "select (select riskname from lmrisk where riskcode =
		// a.riskcode),"
		// + " nvl(sum(SumActuPayMoney),0),count(distinct polno) "
		// + " from ljapayperson a "
		// + " where paytype in('AA','NS') "
		// + " and managecom like '"+mManageCom+"%' "
		// + " and confdate between '"+mStartDate+"' and '"+mEndDate+"' "
		// + " group by a.riskcode ";
		// 现在将回退也加上
		strsql = "select (select riskname from lmrisk where riskcode = b.riskcode),"
				+ " (case when sum(b.SumActuPayMoney) is not null then sum(b.SumActuPayMoney)  else 0 end),count(*)  "
				+ " from (select riskcode,SumActuPayMoney,polno,payno "
				+ " from ljapayperson "
				+ " where paytype in('AA','NS') "
				+ " and managecom like concat('"
				+ "?mManageCom?"
				+ "','%') "
				+ " and confdate between '"
				+ "?mStartDate?"
				+ "' and '"
				+ "?mEndDate?"
				+ "' "
				+ " union "
				+ " select riskcode,SumActuPayMoney,polno,payno "
				+ " from ljapayperson a "
				+ " where 1 = 1 "
				+ " and exists(select 'X' from lpedoritem where standbyflag3 in('AA','NS') and contno = a.contno and edoracceptno = (select otherno from ljaget where actugetno = a.payno)) "
				+ " and paytype = 'RB' "
				+ " and managecom like concat('"
				+ "?mManageCom?"
				+ "','%') "
				+ " and confdate between '"
				+ "?mStartDate?"
				+ "' and '"
				+ "?mEndDate?" + "' " + " ) b group by b.riskcode";
		sqlbv1.sql(strsql);
		sqlbv1.put("mManageCom", mManageCom);
		sqlbv1.put("mStartDate", mStartDate);
		sqlbv1.put("mEndDate", mEndDate);
		tssrs = texesql.execSQL(sqlbv1);
		if (tssrs == null || tssrs.getMaxRow() <= 0) {
			CError.buildErr(this, mStartDate + "至" + mEndDate + "之间没有加保！");
			return false;
		}
		String[] tContListTitle = new String[4];
		for (int iTitle = 0; iTitle < 4; iTitle++) {
			tContListTitle[iTitle] = "TR" + String.valueOf(iTitle);
		}

		ListTable tContListTable = new ListTable();
		tContListTable.setName("Risk");
		String strLine[] = null;
		int j;// 计数
		double riskPrem = 0;
		int riskNum = 0;
		for (int i = 1; i <= tssrs.getMaxRow(); i++) {
			strLine = new String[4];
			strLine[0] = String.valueOf(i);
			strLine[1] = tssrs.GetText(i, 1);
			strLine[2] = BqNameFun.getRound(tssrs.GetText(i, 2));
			strLine[3] = tssrs.GetText(i, 3);

			riskPrem += Double.parseDouble(tssrs.GetText(i, 2));
			riskNum += Integer.parseInt(tssrs.GetText(i, 3));
			tContListTable.add(strLine);
		}

		String tRiskPrem = BqNameFun.getRound(riskPrem);

		texttag.add("RiskPrem", tRiskPrem);
		texttag.add("RiskNum", riskNum);
		// 按保全项目小计
		double tNSPrem = 0;// 新增附加险保费
		int tNSNum = 0;
		double tAAPrem = 0;// 附加险加保保费
		int tAANum = 0;
		double tItemPrem = 0;// 保费合计
		int tItemNum = 0;
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			strsql = "select a.edoritem,(case when c.prem is not null then c.prem  else 0 end),(case when c.num is not null then c.num  else 0 end) from "
				+ " (select 'NS' edoritem,1 pos from dual union select 'AA' edoritem,2 pos from dual) a  "
				+ " left join( " + " select trim(b.paytype) paytype,"
				+ " (case when sum(b.SumActuPayMoney) is not null then sum(b.SumActuPayMoney)  else 0 end) prem,count(*) num "
				+ " from (select paytype,SumActuPayMoney,polno,payno "
				+ " from ljapayperson " + " where paytype in('AA','NS') "
				+ " and managecom like concat('"
				+ "?mManageCom?"
				+ "','%') "
				+ " and confdate between '"
				+ "?mStartDate?"
				+ "' and '"
				+ "?mEndDate?"
				+ "' "
				+ " union "
				+ " select (select standbyflag3 from lpedoritem where standbyflag3 in('AA','NS') and contno = a.contno and edoracceptno = (select otherno from ljaget where actugetno = a.payno) and rownum=1) paytype,SumActuPayMoney,polno,payno "
				+ " from ljapayperson a "
				+ " where 1 = 1 "
				+ " and exists(select 'X' from lpedoritem where standbyflag3 in('AA','NS') and contno = a.contno and edoracceptno = (select otherno from ljaget where actugetno = a.payno)) "
				+ " and paytype = 'RB' "
				+ " and managecom like concat('"
				+ "?mManageCom?"
				+ "','%') "
				+ " and confdate between '"
				+"?mStartDate?"
				+ "' and '"
				+ "?mEndDate?" + "' " + " ) b group by trim(b.paytype) "
				// + " select paytype,sum(SumActuPayMoney) prem,count(distinct
				// polno) num from ljapayperson where paytype in('AA','NS') and
				// managecom like '"+mManageCom+"%' and confdate between
				// '"+mStartDate+"' and '"+mEndDate+"' group by paytype"
				+ " ) c on a.edoritem = c.paytype " + " order by a.pos asc ";
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			strsql = "select a.edoritem,(case when c.prem is not null then c.prem  else 0 end),(case when c.num is not null then c.num  else 0 end) from "
					+ " (select 'NS' edoritem,1 pos from dual union select 'AA' edoritem,2 pos from dual) a  "
					+ " left join( " + " select trim(b.paytype) paytype,"
					+ " (case when sum(b.SumActuPayMoney) is not null then sum(b.SumActuPayMoney)  else 0 end) prem,count(*) num "
					+ " from (select paytype,SumActuPayMoney,polno,payno "
					+ " from ljapayperson " + " where paytype in('AA','NS') "
					+ " and managecom like concat('"
					+ "?mManageCom?"
					+ "','%') "
					+ " and confdate between '"
					+ "?mStartDate?"
					+ "' and '"
					+ "?mEndDate?"
					+ "' "
					+ " union "
					+ " select (select standbyflag3 from lpedoritem where standbyflag3 in('AA','NS') and contno = a.contno and edoracceptno = (select otherno from ljaget where actugetno = a.payno) limit 1) paytype,SumActuPayMoney,polno,payno "
					+ " from ljapayperson a "
					+ " where 1 = 1 "
					+ " and exists(select 'X' from lpedoritem where standbyflag3 in('AA','NS') and contno = a.contno and edoracceptno = (select otherno from ljaget where actugetno = a.payno)) "
					+ " and paytype = 'RB' "
					+ " and managecom like concat('"
					+ "?mManageCom?"
					+ "','%') "
					+ " and confdate between '"
					+"?mStartDate?"
					+ "' and '"
					+ "?mEndDate?" + "' " + " ) b group by trim(b.paytype) "
					// + " select paytype,sum(SumActuPayMoney) prem,count(distinct
					// polno) num from ljapayperson where paytype in('AA','NS') and
					// managecom like '"+mManageCom+"%' and confdate between
					// '"+mStartDate+"' and '"+mEndDate+"' group by paytype"
					+ " ) c on a.edoritem = c.paytype " + " order by a.pos asc ";
		}
		sqlbv2.sql(strsql);
		sqlbv2.put("mManageCom", mManageCom);
		sqlbv2.put("mStartDate", mStartDate);
		sqlbv2.put("mEndDate", mEndDate);
		tssrs = texesql.execSQL(sqlbv2);
		if (tssrs != null && tssrs.getMaxRow() == 2) {
			tNSPrem = Double.parseDouble(tssrs.GetText(1, 2));
			tNSNum = Integer.parseInt(tssrs.GetText(1, 3));
			tAAPrem = Double.parseDouble(tssrs.GetText(2, 2));
			tAANum = Integer.parseInt(tssrs.GetText(2, 3));
			tItemPrem = tNSPrem + tAAPrem;
			tItemNum = tNSNum + tAANum;
		}
		String NSPrem = BqNameFun.getRound(tNSPrem);
		String AAPrem = BqNameFun.getRound(tAAPrem);
		String ItemPrem = BqNameFun.getRound(tItemPrem);

		texttag.add("NSPrem", NSPrem);
		texttag.add("NSNum", tNSNum);
		texttag.add("AAPrem", AAPrem);
		texttag.add("AANum", tAANum);
		texttag.add("ItemPrem", ItemPrem);
		texttag.add("ItemNum", tItemNum);

		// String tManageCom = BqNameFun.getCBranch(mGlobalInput.ManageCom);
		String tManageCom = BqNameFun.getCBranch(mManageCom);
		mStartDate = BqNameFun.getFDate(mStartDate);
		mEndDate = BqNameFun.getFDate(mEndDate);
		mCurrentDate = BqNameFun.getFDate(mCurrentDate);
		// 模版自上而下的元素
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
		tXmlExport.addListTable(tContListTable, tContListTitle);
		// tXmlExport.outputDocumentToFile("d:\\", "P001260"); //测试用
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
