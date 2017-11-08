

/**
 * Copyright (c) 2006 sinosoft  Co. Ltd.
 * All right reserved.
 */

/*
 * <p>ClassName: OrderDescUI </p>
 * <p>Description: OrderDescUI类文件 </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: sinosoft </p>
 * @Database: Zhang Bin
 * @CreateDate：2006-07-30
 */
package com.sinosoft.lis.reinsure.stat.zhongyi;

import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.vschema.*;

public class GrpOutMedicCESSTabBL {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	/** 前台传入的公共变量 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	private TransferData mTransferData = new TransferData();
	private String mRValidate = "";
	private String mRInvalidate = "";
	private String mContNo = "";
	private String mRIcomCode = "";
	private String mReRiskCode = "";
	private String mTempType = "";

	/** 数据操作字符串 */
	private String strOperate = "";
	private MMap mMap = new MMap();
	private PubSubmit tPubSubmit = new PubSubmit();

	// 业务处理相关变量
	/** 全局数据 */
	public GrpOutMedicCESSTabBL() {
	}

	/**
	 * 提交数据处理方法
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		this.strOperate = cOperate;
		if (strOperate.equals("")) {
			buildError("verifyOperate", "不支持的操作字符串");
			return false;
		}
		if (!getInputData(cInputData)) {
			return false;
		}

		/** 准备所有要打印的数据 */
		if (!getPrintData()) {
			return false;
		}
		return true;
	}

	private String getEndDate(String aSerialNo) {
		String yStr = aSerialNo.substring(0, 4);
		int y = Integer.parseInt(yStr);
		String mStr = aSerialNo.substring(4, 6);
		String dStr = "";
		switch (Integer.parseInt(mStr)) {
		case 1:
			dStr = "31";
			break;
		case 2:
			if (y % 4 == 0 && y % 100 != 0 || y % 400 == 0)
				dStr = "29";
			else
				dStr = "28";
			break;
		case 3:
			dStr = "31";
			break;
		case 4:
			dStr = "30";
			break;
		case 5:
			dStr = "31";
			break;
		case 6:
			dStr = "30";
			break;
		case 7:
			dStr = "31";
			break;
		case 8:
			dStr = "31";
			break;
		case 9:
			dStr = "30";
			break;
		case 10:
			dStr = "31";
			break;
		case 11:
			dStr = "30";
			break;
		case 12:
			dStr = "31";
			break;
		}
		String tEndDate = yStr + "-" + mStr + "-" + dStr;
		return tEndDate;
	}

	public static void main(String[] args) {
		GlobalInput globalInput = new GlobalInput();
		globalInput.ComCode = "8611";
		globalInput.Operator = "001";
		GrpOutMedicCESSTabBL t = new GrpOutMedicCESSTabBL();
	}

	private boolean getPrintData() {
		TextTag texttag = new TextTag();
		ListTable multTable = new ListTable();
		multTable.setName("MULT");
		String[] Title = { "col1", "col2", "col3", "col4", "col5", "col6",
				"col7", "col8", "col9", "col10", "col11", "col12", "col13",
				"col14", "col15", "col16", "col17", "col18", "col19" };
		try {
			SSRS tSSRS = new SSRS();
			String sql = "";
			sql = " select X.A18,X.A0,X.A1,X.A2,X.A3,X.A4,X.A5,X.A6,X.A7,X.A8,X.A9,X.A10,X.A11,X.A12,X.A13,X.A14,X.A15,X.A16,X.A14-X.A15 "
					+ " from (select (select shortname from ldcom where comcode=a.StandbyString3) A0 ,max(a.InsuredName) A1,(case max(a.InsuredSex) when '0' then '男' else '女' end) A2,max(a.InsuredBirthday) A3,max(a.InsuredIDNo) A4,max(a.OccupationType) A5, a.GrpContno A6,max(a.CValiDate) A7,max(a.enddate) A8,a.RiskCode  A9,(select riskname from lmrisk where riskcode=a.RiskCode) A10,(select nvl(sum(c.Amnt),0) from ripolrecordbake c where c.riskcode=a.riskcode and c.insuredno=a.insuredno and c.SerialNo=a.SerialNo and c.EventType in ('01','03') and (c.FeeOperationType is null or c.FeeOperationType='') and c.GrpContno=a.GrpContno )  A11, nvl(sum(b.cessionamount),0) A12,nvl(max(b.CessionRate),0) A13,nvl(sum(b.PremChang),0) A14,nvl(sum(b.ReturnComm),0) A15,nvl(max(b.StandbyDoubleC),0) A16, (select  ComPanyName from ReInsuranceComPanyInfo  where companyno=b.companyno) A18 from RIPolRecordBake a, reinsurerecordtrace b where a.eventno=b.eventno and a.SerialNo=b.SerialNo and a.EventType in ('01','03') and (a.FeeOperationType is null or a.FeeOperationType='') and a.dutycode in (select distinct associatedcode from accumulaterdcode where accumulatedefno in (select Accumulatedefno from riprecept where ricontno = 'C000000003')) and a.cvalidate >= '"
					+ mRValidate + "' and a.enddate<='" + mRInvalidate + "' ";
			if (mReRiskCode != null && !mReRiskCode.equals("")) {
				sql = sql + " and a.RiskCode='" + mReRiskCode + "' ";
			}
			if (mRIcomCode != null && !mRIcomCode.equals("")) {
				sql = sql + " and b.ComPanyNo = '" + mRIcomCode + "' ";
			}
			if (mTempType.equals("2")) {
				sql = sql + " and a.ReinsreFlag in ('00','03') ";
			} else if (mTempType.equals("1")) {
				sql = sql
						+ " and (a.ReinsreFlag not in ('00','03') or a.ReinsreFlag is null) ";
			}

			String sql1 = " group by a.serialno,b.companyno,a.GrpContno,a.StandbyString3,a.RiskCode,a.InsuredNo order by a.StandbyString3,a.GrpContno,a.InsuredNo,a.RiskCode ) X where rownum <=1000 ";
			sql = sql + sql1;
			System.out.println("zb: SQL: " + sql);
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(sql);
			/** 查询结果的记录条数 */
			int count = tSSRS.getMaxRow();
			System.out.println("该sql执行后的记录条数为：" + count);

			/** 将查询结果存放到临时 二维数组中 */
			String temp[][] = tSSRS.getAllData();
			String[] strCol;
			for (int i = 0; i < count; i++) {
				strCol = new String[19];
				strCol[0] = temp[i][0];
				strCol[1] = temp[i][1];
				strCol[2] = temp[i][2];
				strCol[3] = temp[i][3];
				strCol[4] = temp[i][4];
				strCol[5] = temp[i][5];
				strCol[6] = temp[i][6];
				strCol[7] = temp[i][7];
				strCol[8] = temp[i][8];
				strCol[9] = temp[i][9];
				strCol[10] = temp[i][10];
				strCol[11] = temp[i][11];
				strCol[12] = temp[i][12];
				strCol[13] = temp[i][13];
				strCol[14] = temp[i][14];
				strCol[15] = temp[i][15];
				strCol[16] = temp[i][16];
				strCol[17] = temp[i][17];
				strCol[18] = temp[i][18];
				multTable.add(strCol);
			}
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "OrderCollectBL";
			tError.functionName = "";
			tError.errorMessage = "客户基本信息查询失败";
			this.mErrors.addOneError(tError);
		}
		/** LRExportCess3.vts//新建一个XmlExport的实例 */
		XmlExport xmlexport = new XmlExport();
		/** 最好紧接着就初始化xml文档 */
		xmlexport.createDocument("LRExportCess3.vts", "printer");
		SSRS cSSRS = new SSRS();
		ExeSQL cExeSQL = new ExeSQL();
		String tSQL = " select name from ldcom where comcode='"
				+ mGlobalInput.ComCode + "' ";
		cSSRS = cExeSQL.execSQL(tSQL);
		String com = cSSRS.GetText(1, 1);
		texttag.add("MakeOrganization", com);
		texttag.add("Operator", mGlobalInput.Operator);
		String currentdate = PubFun.getCurrentDate();
		texttag.add("MakeDate", currentdate);

		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}
		/** 保存信息 */
		xmlexport.addListTable(multTable, Title);
		mResult.clear();
		mResult.addElement(xmlexport);

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		/** 全局变量 */
		this.mGlobalInput.setSchema((GlobalInput) cInputData
				.getObjectByObjectName("GlobalInput", 0));
		mTransferData = ((TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0));

		if (mTransferData == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}
		mRValidate = (String) mTransferData.getValueByName("RValidate");
		mRInvalidate = (String) mTransferData.getValueByName("RInvalidate");
		mContNo = (String) mTransferData.getValueByName("ContNo");
		mRIcomCode = (String) mTransferData.getValueByName("RIcomCode");
		mReRiskCode = (String) mTransferData.getValueByName("ReRiskCode");
		mTempType = (String) mTransferData.getValueByName("TempType");

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/*
	 * add by kevin, 2002-10-14
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "ReComManageBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}
}
