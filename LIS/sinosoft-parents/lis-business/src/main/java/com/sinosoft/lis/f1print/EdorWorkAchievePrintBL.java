package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全工作绩效统计打印类
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
 * @CreateDate：2005-12-03
 */
public class EdorWorkAchievePrintBL implements BqBill {
private static Logger logger = Logger.getLogger(EdorWorkAchievePrintBL.class);
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
	private String mUsrType = "";
	private String mUsrCode = "";
	private String mEdorType = "";
	private String mEdorState = "";
	public static final String VTS_NAME = "WorkAchieve.vts"; // 模板名称

	public EdorWorkAchievePrintBL() {
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
			tError.moduleName = "EdorWorkAchievePrintBL";
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
			mErrors.addOneError(new CError("没有得到足够的信息:管理机构不能为空！"));
			return false;
		}
		mUsrType = (String) mTransferData.getValueByName("UsrType");
		if (mUsrType == null || mUsrType.trim().equals("")) {
			mUsrType = "0";// 如果传入用户类型为空，则认为统计所有类型用户
		}
		mUsrCode = (String) mTransferData.getValueByName("UsrCode");
		mEdorState = (String) mTransferData.getValueByName("EdorState");
		if (mEdorState == null || mEdorState.trim().equals("")) {
			mEdorState = "0";// 如果传入处理状态为空，则认为统计所有类型状态
		}
		mEdorType = (String) mTransferData.getValueByName("EdorType");

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

		SSRS tssrs = new SSRS();
		ExeSQL texesql = new ExeSQL();
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		String strsql = "";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		String groupsql = "";// 小计
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		String statisql = "";// 合计
		String edorStateSub = "";
		if (mEdorState != null && mEdorState.trim().equals("1")) {
			edorStateSub = " and edorstate = '0' ";
		} else if (mEdorState != null && mEdorState.trim().equals("2")) {
			edorStateSub = " and edorstate <> '0' ";
		}
		String edorTypeSub = (mEdorType != null && !mEdorType.trim().equals("")) ? (" and edortype = '"
				+ "?mEdorType?" + "' ")
				: "";
		if (mUsrType.trim().equals("1") || mUsrType.trim().equals("2")) {
			String tOperate = (mUsrType.trim().equals("1")) ? " a.operator "
					: " a.approveoperator ";
			String operateSub = (mUsrCode != null && !mUsrCode.trim()
					.equals("")) ? (" and " + tOperate + " = '" + "?mUsrCode?" + "' ")
					: "";
			strsql = " select a.managecom,"
					+ tOperate
					+ ", "
					+ " (case when (select username from lduser where trim(usercode) = "
					+ tOperate
					+ ") is not null then (select username from lduser where trim(usercode) = "
					+ tOperate
					+ ")  else "+ tOperate+ " end), "
					+ " (select edorname from lmedoritem where edorcode = a.edortype and appobj in('I','B')),"
					+ " a.edoracceptno,a.contno, "
					+ " makedate,(select codename from ldcode where trim(code) = a.edorstate and codetype = 'edorstate'),(case when (select username from lduser where trim(usercode) = a.operator) is not null then (select username from lduser where trim(usercode) = a.operator)  else a.operator end),"
					+ " (case when (select username from lduser where trim(usercode) = a.approveoperator) is not null then (select username from lduser where trim(usercode) = a.approveoperator)  else a.approveoperator end) "
					+ " from lpedoritem a where 1 = 1 " + " and " + tOperate
					+ " is not null " + operateSub + edorTypeSub + edorStateSub
					+ " and a.managecom like concat('" + "?mManageCom?"
					+ "','%') and a.makedate between '" + "?mStartDate?" + "' and '"
					+ "?mEndDate?" + "' " + " order by " + tOperate
					+ ",a.edortype ";
			groupsql = " select "
					+ tOperate
					+ ", "
					+ " (select edorname from lmedoritem where edorcode = a.edortype and appobj in('I','B')),"
					+ " count(distinct edoracceptno) "
					+ " from lpedoritem a where 1 = 1 " + " and " + tOperate
					+ " is not null " + operateSub + edorTypeSub + edorStateSub
					+ " and a.managecom like concat('" + "?mManageCom?"
					+ "','%') and a.makedate between '" + "?mStartDate?" + "' and '"
					+ "?mEndDate?" + "' " + " group by " + tOperate
					+ ",a.edortype ";
			statisql = " select " + tOperate + ", "
					+ " count(distinct edoracceptno) "
					+ " from lpedoritem a where 1 = 1 " + " and " + tOperate
					+ " is not null " + operateSub + edorTypeSub + edorStateSub
					+ " and a.managecom like concat('" + "?mManageCom?"
					+ "','%') and a.makedate between '" + "?mStartDate?" + "' and '"
					+ "?mEndDate?" + "' " + " group by " + tOperate;
		} else {
			String operateSub1 = "";
			String operateSub2 = "";
			if (mUsrCode != null && !mUsrCode.trim().equals("")) {
				operateSub1 = " and a.operator = '" + "?mUsrCode?" + "' ";
				operateSub2 = " and b.approveoperator = '" + "?mUsrCode?" + "' ";
			}
			strsql = "select a.managecom,a.operator usercode, "
					+ " (case when (select username from lduser where trim(usercode) = a.operator) is not null then (select username from lduser where trim(usercode) = a.operator)  else a.operator end), "
					+ " (select edorname from lmedoritem where edorcode = a.edortype and appobj in('I','B')) edoritem,"
					+ " a.edoracceptno,a.contno, "
					+ " makedate,(select codename from ldcode where trim(code) = a.edorstate and codetype = 'edorstate'),(case when (select username from lduser where trim(usercode) = a.operator) is not null then (select username from lduser where trim(usercode) = a.operator)  else a.operator end),"
					+ " (case when (select username from lduser where trim(usercode) = a.approveoperator) is not null then (select username from lduser where trim(usercode) = a.approveoperator)  else a.approveoperator end) "
					+ " from lpedoritem a where 1 = 1 "
					+ operateSub1
					+ edorTypeSub
					+ edorStateSub
					+ " and a.managecom like concat('"
					+ "?mManageCom?"
					+ "','%') and a.makedate between '"
					+ "?mStartDate?"
					+ "' and '"
					+ "?mEndDate?"
					+ "' "
					+ " union "
					+ " select b.managecom,b.approveoperator usercode, "
					+ " (case when (select username from lduser where trim(usercode) = b.approveoperator) is not null then (select username from lduser where trim(usercode) = b.approveoperator)  else b.approveoperator end), "
					+ " (select edorname from lmedoritem where edorcode = b.edortype and appobj in('I','B')) edoritem,"
					+ " b.edoracceptno,b.contno, "
					+ " makedate,(select codename from ldcode where trim(code) = b.edorstate and codetype = 'edorstate'),(case when (select username from lduser where trim(usercode) = b.operator) is not null then (select username from lduser where trim(usercode) = b.operator)  else b.operator end),"
					+ " (case when (select username from lduser where trim(usercode) = b.approveoperator) is not null then (select username from lduser where trim(usercode) = b.approveoperator)  else b.approveoperator end) "
					+ " from lpedoritem b where 1 = 1 "
					+ " and b.approveoperator is not null "
					+ operateSub2
					+ edorTypeSub
					+ edorStateSub
					+ " and b.managecom like concat('"
					+ "?mManageCom?"
					+ "','%') and b.makedate between '"
					+ "?mStartDate?"
					+ "' and '"
					+ "?mEndDate?"
					+ "' "
					+ " order by usercode,edoritem ";
			groupsql = " select c.usercode,c.edoritem,count(distinct c.edoracceptno) from ("
					+ " select a.operator usercode, "
					+ " (select edorname from lmedoritem where edorcode = a.edortype and appobj in('I','B')) edoritem,"
					+ "  a.edorno,a.edortype,a.contno,a.polno,a.edoracceptno "
					+ " from lpedoritem a where 1 = 1 "
					+ operateSub1
					+ edorTypeSub
					+ edorStateSub
					+ " and a.managecom like concat('"
					+ "?mManageCom?"
					+ "','%') and a.makedate between '"
					+ "?mStartDate?"
					+ "' and '"
					+ "?mEndDate?"
					+ "' "
					+ " union "
					+ " select b.approveoperator usercode, "
					+ " (select edorname from lmedoritem where edorcode = b.edortype and appobj in('I','B')) edoritem,"
					+ " b.edorno,b.edortype,b.contno,b.polno,b.edoracceptno "
					+ " from lpedoritem b where 1 = 1 "
					+ " and b.approveoperator is not null "
					+ operateSub2
					+ edorTypeSub
					+ edorStateSub
					+ " and b.managecom like concat('"
					+ "?mManageCom?"
					+ "','%') and b.makedate between '"
					+ "?mStartDate?"
					+ "' and '"
					+ "?mEndDate?"
					+ "' "
					+ ") c group by c.usercode,c.edoritem ";
			statisql = " select c.usercode,count(distinct c.edoracceptno) from ("
					+ " select a.operator usercode, "
					+ "  a.edorno,a.edortype,a.contno,a.polno,a.edoracceptno "
					+ " from lpedoritem a where 1 = 1 "
					+ operateSub1
					+ edorTypeSub
					+ edorStateSub
					+ " and a.managecom like concat('"
					+ "?mManageCom?"
					+ "','%') and a.makedate between '"
					+ "?mStartDate?"
					+ "' and '"
					+ "?mEndDate?"
					+ "' "
					+ " union "
					+ " select b.approveoperator usercode, "
					+ " b.edorno,b.edortype,b.contno,b.polno,b.edoracceptno "
					+ " from lpedoritem b where 1 = 1 "
					+ " and b.approveoperator is not null "
					+ operateSub2
					+ edorTypeSub
					+ edorStateSub
					+ " and b.managecom like concat('"
					+ "?mManageCom?"
					+ "','%') and b.makedate between '"
					+ "?mStartDate?"
					+ "' and '" + "?mEndDate?"+ "' " + ") c group by c.usercode ";

		}
		sqlbv1.sql(strsql);
		sqlbv1.put("mUsrCode", mUsrCode);
		sqlbv1.put("mEdorType", mEdorType);
		sqlbv1.put("mManageCom", mManageCom);
		sqlbv1.put("mStartDate", mStartDate);
		sqlbv1.put("mEndDate", mEndDate);
		tssrs = texesql.execSQL(sqlbv1);
		if (tssrs == null || tssrs.getMaxRow() <= 0) {
			CError.buildErr(this, mStartDate + "至" + mEndDate + "之间无待打印清单！");
			return false;
		}
		if (tssrs.getMaxRow() > 6300) {
			CError.buildErr(this, "对不起，返回记录数太多，请您缩小查询范围！");
			return false;
		}
		String[] tContListTitle = new String[10];
		for (int iTitle = 0; iTitle < 10; iTitle++) {
			tContListTitle[iTitle] = "Risk" + String.valueOf(iTitle);
		}

		ListTable tContListTable = new ListTable();
		tContListTable.setName("Risk");
		String strLine[] = null;
		int j;// 计数
		for (int i = 1; i <= tssrs.getMaxRow(); i++) {
			strLine = new String[10];
			for (j = 0; j < 10; j++) {
				strLine[j] = tssrs.GetText(i, j + 1);
			}
			tContListTable.add(strLine);
		}
		sqlbv2.sql(groupsql);
		sqlbv2.put("mUsrCode", mUsrCode);
		sqlbv2.put("mEdorType", mEdorType);
		sqlbv2.put("mManageCom", mManageCom);
		sqlbv2.put("mStartDate", mStartDate);
		sqlbv2.put("mEndDate", mEndDate);
		// 小计
		tssrs = texesql.execSQL(sqlbv2);

		String[] tTotalListTitle = new String[3];
		for (int tTitle = 0; tTitle < 3; tTitle++) {
			tTotalListTitle[tTitle] = "TOTAL" + String.valueOf(tTitle);
		}

		ListTable tTotalListTable = new ListTable();
		tTotalListTable.setName("TOTAL");
		String totalLine[] = null;
		int k;// 计数
		for (int t = 1; t <= tssrs.getMaxRow(); t++) {
			totalLine = new String[3];
			totalLine[0] = tssrs.GetText(t, 1);
			totalLine[1] = tssrs.GetText(t, 2);
			totalLine[2] = tssrs.GetText(t, 3);
			tTotalListTable.add(totalLine);
		}
		sqlbv3.sql(statisql);
		sqlbv3.put("mUsrCode", mUsrCode);
		sqlbv3.put("mEdorType", mEdorType);
		sqlbv3.put("mManageCom", mManageCom);
		sqlbv3.put("mStartDate", mStartDate);
		sqlbv3.put("mEndDate", mEndDate);
		// 合计
		tssrs = texesql.execSQL(sqlbv3);

		String[] tStatiListTitle = new String[2];
		for (int vTitle = 0; vTitle < 2; vTitle++) {
			tStatiListTitle[vTitle] = "Stati" + String.valueOf(vTitle);
		}

		ListTable tStatiListTable = new ListTable();
		tStatiListTable.setName("Statistic");
		String statiLine[] = null;
		for (int v = 1; v <= tssrs.getMaxRow(); v++) {
			statiLine = new String[2];
			statiLine[0] = tssrs.GetText(v, 1);
			statiLine[1] = tssrs.GetText(v, 2);
			tStatiListTable.add(statiLine);
		}

		String tManageCom = BqNameFun.getCBranch(mManageCom);
		// String tStartDate = BqNameFun.getFDate(mStartDate);
		// String tEndDate = BqNameFun.getFDate(mEndDate);
		String tStatement = getStatement(tManageCom);// 显示统计条件
		// 模版自上而下的元素
		texttag.add("Statement", tStatement);
		// texttag.add("ManageCom", tManageCom);
		// texttag.add("StartDate", tStartDate);
		// texttag.add("EndDate", tEndDate);
		if (texttag.size() > 0) {
			tXmlExport.addTextTag(texttag);
		}
		tXmlExport.addListTable(tContListTable, tContListTitle);
		tXmlExport.addListTable(tTotalListTable, tTotalListTitle);
		tXmlExport.addListTable(tStatiListTable, tStatiListTitle);
		// tXmlExport.outputDocumentToFile("d:\\", "P001260"); //测试用
		mResult.clear();
		mResult.addElement(tXmlExport);

		return true;
	}

	private String getStatement(String tManageCom) {
		String str = "";
		FDate fDate = new FDate();
		if (fDate.getDate(mStartDate).equals(fDate.getDate(mEndDate))) {
			str += BqNameFun.getFDate(mStartDate);
		} else {
			str += BqNameFun.getFDate(mStartDate) + "至"
					+ BqNameFun.getFDate(mEndDate);
		}
		str += tManageCom;
		if (mUsrCode == null || mUsrCode.trim().equals("")) {
			str += "所有用户";
		} else {
			str += "用户" + getUsrName(mUsrCode);
		}
		if (mUsrType != null && mUsrType.trim().equals("1")) {
			str += "经办的";
		} else if (mUsrType != null && mUsrType.trim().equals("2")) {
			str += "复核的";
		} else {
			str += "经办或复核的";
		}
		if (mEdorState != null && mEdorState.trim().equals("1")) {
			str += "已生效的";
		} else if (mUsrType != null && mUsrType.trim().equals("2")) {
			str += "未生效的";
		} else {
			str += "所有状态的";
		}
		if (mEdorType == null || mEdorType.trim().equals("")) {
			str += "所有保全项目";
		} else {
			str += getEdorName(mEdorType);
		}
		str += "统计如下：";
		return str;
	}

	private String getUsrName(String tCode) {
		String tName = "";
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		String strsql = "select username from lduser where usercode = '"
				+ "?tCode?" + "'";
		sqlbv7.sql(strsql);
		sqlbv7.put("tCode", tCode);
		ExeSQL q_exesql = new ExeSQL();
		SSRS tssrs = new SSRS();
		tssrs = q_exesql.execSQL(sqlbv7);
		if (tssrs != null && tssrs.getMaxRow() >= 1) {
			tName = tssrs.GetText(1, 1);
		}
		return tName;
	}

	private String getEdorName(String tCode) {
		String tName = "";
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		String strsql = "select edorname from lmedoritem where appobj in('I','B') and edorcode = '"
				+ "?tCode?" + "'";
		sqlbv8.sql(strsql);
		sqlbv8.put("tCode", tCode);
		ExeSQL q_exesql = new ExeSQL();
		SSRS tssrs = new SSRS();
		tssrs = q_exesql.execSQL(sqlbv8);
		if (tssrs != null && tssrs.getMaxRow() >= 1) {
			tName = tssrs.GetText(1, 1);
		}
		return tName;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}
}
