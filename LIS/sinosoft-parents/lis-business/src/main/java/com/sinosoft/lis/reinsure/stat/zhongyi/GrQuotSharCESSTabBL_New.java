

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
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.db.*;

public class GrQuotSharCESSTabBL_New {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	/** 前台传入的公共变量 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	private TransferData mTransferData = new TransferData();
	private String mSartDate = "";
	private String mEndDate = "";
	private String mStaTerm = "";
	private String thisSerialNo = "";
	private String lastSerialNo = "";
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

	public GrQuotSharCESSTabBL_New() {
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
		System.out.println(" 团体保障保险成数分保合同.....");
		this.strOperate = cOperate;
		if (strOperate.equals("")) {
			buildError("verifyOperate", " GrQuotSharCESSTabBL->1 不支持的操作字符串");
			return false;
		}
		if (!getInputData(cInputData)) {
			System.out.println("－－获取数据失败－－－");
			return false;
		}
		if (!getLastSerialNo()) {
			return false;
		}
		// 准备所有要打印的数据
		if (!getPrintData()) {
			return false;
		}
		return true;
	}

	private boolean getLastSerialNo() {
		String yStr = mStaTerm.substring(0, 4);
		String mStr = mStaTerm.substring(4, 6);
		int yInt = Integer.parseInt(yStr);
		int mInt = Integer.parseInt(mStr);

		if (mInt == 1) {
			lastSerialNo = (yInt - 1) + "12";
		} else if (mInt >= 2 && mInt <= 10) {
			lastSerialNo = yStr + "0" + (mInt - 1);
		} else if (mInt >= 10 && mInt <= 12) {
			lastSerialNo = yStr + (mInt - 1);
		}

		return true;
	}

	public static void main(String[] args) {
		GlobalInput globalInput = new GlobalInput();
		globalInput.ComCode = "8611";
		globalInput.Operator = "001";
		VData tVData = new VData();
		GrQuotSharCESSTabBL_New t = new GrQuotSharCESSTabBL_New();
		t.mStaTerm = "200710";
		t.getLastSerialNo();
		System.out.println(" aa: " + t.lastSerialNo);
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		try {
			this.mInputData.clear();
			this.mInputData.add(mMap);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrQuotSharCESSTabBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	private boolean getPrintData() {
		// 新建一个TextTag的实例
		TextTag texttag = new TextTag();

		// 统计的起讫时间
		ListTable multTable = new ListTable();
		multTable.setName("MULT");
		// 以下表示在 报表中生成 26列，
		String[] Title = { "col1", "col2", "col3", "col4", "col5", "col6",
				"col7", "col8", "col9", "col10", "col11", "col12", "col13",
				"col14", "col15", "col16", "col17", "col18", "col19", "col20",
				"col21", "col22", "col23", "col24", "col25", "col26", "col27",
				"col28" };
		try {
			SSRS tSSRS = new SSRS();
			// String sql = "";
			// sql =
			// " select X.A0,X.A1,X.A25,(case when X.A24 in ('NMK01','MMK01','NMK02') then 'AD&&D' when X.A24 = 'NAK01' then 'GL' when X.A24 in ('MGK01','MGK02') then 'DD' else 'GME' end),X.A3,X.A4 ,X.A5,X.A6,(case when X.A7='0' or X.A7='12' then '1'  when X.A7='6' then '2' when X.A7='3' then '4' when X.A7='1' then '12' end),X.A8, X.A9||'%', X.A23, X.A24, X.A10 ,X.A11 ,X.A12 ,X.A13,X.A14 ,X.A15,X.A16 ,X.A17 ,X.A18 ,nvl(round(X.A16*X.A19/12,2),0), nvl(round((X.A17+X.A18)*X.A19/12,2),0) ,X.A20 ,X.A21 ,X.A22 , nvl(X.A11-X.A12-X.A13-X.A14-X.A15-((X.A20+X.A21+X.A22)-(X.A16+X.A17+X.A18))+round(X.A16*X.A19/12,2)+round((X.A17+X.A18)*X.A19/12,2),0) "
			// +
			// " from (select (select ComPanyName from ReInsuranceComPanyInfo where ComPanyNo=b.ComPanyNo) A0,(select GrpName from lcgrpcont where grpcontno=a.grpcontno) A1,a.Grpcontno A25,'团体保障保险' A2,(select max(cvalidate) from lcgrpcont where grpcontno= a.grpcontno) A3, max(a.cvalidate) A4,max(a.enddate) A5,max(a.enddate) A6, max(a.PayIntv) A7,max(a.AgentName) A8, (case when max(a.SaleChnl) in ('04','06') then max(a.StandbyDouble1) else 0 end) A9, nvl(sum(a.prem),0) A10,nvl(sum(b.PremChang),0) A11,(select nvl(sum(d.ReturnPay),0) from ripolrecordbake c, reinsurerecordtrace d where c.eventno=d.eventno and c.SerialNo=d.SerialNo and c.eventtype='04' and c.serialno ='"+mStaTerm+"' and c.insuredno=a.insuredno and c.StandbyString1='1' and c.riskcode=a.riskcode ) A12 ,nvl(sum(b.ReturnComm),0) A13, nvl(sum(b.StandbyDouble1),0) A14, nvl(sum(b.ReturnTax),0) A15, (select nvl(sum(d.StandbyDouble2),0) from ripolrecordbake c, reinsurerecordtrace d where c.eventno=d.eventno and c.SerialNo=d.SerialNo and c.eventtype='01' and c.serialno = '"+lastSerialNo+"' and c.insuredno=a.insuredno  and c.StandbyString1='1' and c.riskcode=a.riskcode) A16,(select nvl(sum(d.StandbyDouble3),0) from ripolrecordbake c, reinsurerecordtrace d where c.eventno=d.eventno and c.SerialNo=d.SerialNo  and c.eventtype='01' and c.serialno = '"+lastSerialNo+"' and c.insuredno=a.insuredno and c.StandbyString1='1' and c.riskcode=a.riskcode) A17, (select nvl(sum(d.StandbyDouble4),0) from ripolrecordbake c, reinsurerecordtrace d where c.eventno=d.eventno and c.SerialNo=d.SerialNo and c.eventtype='01' and c.serialno = '"+lastSerialNo+"' and c.insuredno=a.insuredno and c.StandbyString1='1' and c.riskcode=a.riskcode) A18,max(b.SavingRate) A19,nvl(sum(b.StandbyDouble2),0) A20,nvl(sum(b.StandbyDouble3),0) A21,nvl(sum(b.StandbyDouble4),0) A22,max(a.Insuredname) A23,(select riskname from lmrisk where riskcode=a.riskcode) A24 "
			// +
			// " from ripolrecordbake a, reinsurerecordtrace b where a.eventno=b.eventno and a.dutycode in (select distinct associatedcode from accumulaterdcode where accumulatedefno in (select Accumulatedefno from riprecept where ricontno = 'C000000001')) and a.serialno = '"+mStaTerm+"' and a.StandbyString1='1' and a.eventtype='01' "
			// ;
			StringBuffer sqlStrBuf = new StringBuffer();
			sqlStrBuf
					.append(" select X.A0,X.A1,X.A25,(case when X.A24 in ('NMK01','MMK01','NMK02') then 'AD&&D' when X.A24 = 'NAK01' then 'GL' when X.A24 in ('MGK01','MGK02') then 'DD' else 'GME' end),X.A3,X.A4 ,X.A5,X.A6,(case when X.A7='0' or X.A7='12' then '1'  when X.A7='6' then '2' when X.A7='3' then '4' when X.A7='1' then '12' end),X.A8, X.A9||'%', X.A23, X.A24, X.A10 ,X.A11 ,X.A12 ,X.A13,X.A14 ,X.A15,X.A16 ,X.A17 ,X.A18 ,nvl(round(X.A16*X.A19/12,2),0), nvl(round((X.A17+X.A18)*X.A19/12,2),0) ,X.A20 ,X.A21 ,X.A22 , nvl(X.A11-X.A12-X.A13-X.A14-X.A15-((X.A20+X.A21+X.A22)-(X.A16+X.A17+X.A18))+round(X.A16*X.A19/12,2)+round((X.A17+X.A18)*X.A19/12,2),0) ");
			sqlStrBuf.append(" from ( ");
			sqlStrBuf
					.append(" select (select ComPanyName from ReInsuranceComPanyInfo where ComPanyNo=b.ComPanyNo) A0,(select GrpName from lcgrpcont where grpcontno=a.grpcontno) A1,a.Grpcontno A25,'团体保障保险' A2,(select max(cvalidate) from lcgrpcont where grpcontno= a.grpcontno) A3, max(a.cvalidate) A4,max(a.enddate) A5,max(a.enddate) A6, max(a.PayIntv) A7,max(a.AgentName) A8, (case when max(a.SaleChnl) in ('04','06') then max(a.StandbyDouble1) else 0 end) A9, nvl(sum(a.prem),0) A10,nvl(sum(b.PremChang),0) A11,(select nvl(sum(d.ReturnPay),0) from ripolrecordbake c, reinsurerecordtrace d where c.eventno=d.eventno and c.SerialNo=d.SerialNo and c.eventtype='04' and c.serialno ='"
							+ mStaTerm
							+ "' and d.ComPanyNo=b.ComPanyNo and c.insuredno=a.insuredno and c.StandbyString1='1' and c.riskcode=a.riskcode ) A12 ,nvl(sum(b.ReturnComm),0) A13, nvl(sum(b.StandbyDouble1),0) A14, nvl(sum(b.ReturnTax),0) A15, (select nvl(sum(d.StandbyDouble2),0) from ripolrecordbake c, reinsurerecordtrace d where c.eventno=d.eventno and c.SerialNo=d.SerialNo and c.eventtype='01' and c.serialno = '"
							+ lastSerialNo
							+ "' and c.insuredno=a.insuredno  and c.StandbyString1='1' and c.riskcode=a.riskcode) A16,(select nvl(sum(d.StandbyDouble3),0) from ripolrecordbake c, reinsurerecordtrace d where c.eventno=d.eventno and c.SerialNo=d.SerialNo  and c.eventtype='01' and c.serialno = '"
							+ lastSerialNo
							+ "' and c.insuredno=a.insuredno and c.StandbyString1='1' and c.riskcode=a.riskcode) A17, (select nvl(sum(d.StandbyDouble4),0) from ripolrecordbake c, reinsurerecordtrace d where c.eventno=d.eventno and c.SerialNo=d.SerialNo and c.eventtype='01' and c.serialno = '"
							+ lastSerialNo
							+ "' and c.insuredno=a.insuredno and c.StandbyString1='1' and c.riskcode=a.riskcode) A18,max(b.SavingRate) A19,nvl(sum(b.StandbyDouble2),0) A20,nvl(sum(b.StandbyDouble3),0) A21,nvl(sum(b.StandbyDouble4),0) A22,max(a.Insuredname) A23,(select riskname from lmrisk where riskcode=a.riskcode) A24,a.insuredno A30,a.riskcode A31 ");
			sqlStrBuf
					.append(" from ripolrecordbake a, reinsurerecordtrace b where a.eventno=b.eventno and a.dutycode in (select distinct associatedcode from accumulaterdcode where accumulatedefno in (select Accumulatedefno from riprecept where ricontno = 'C000000001')) and a.serialno = '"
							+ mStaTerm
							+ "' and a.StandbyString1='1' and a.eventtype='01' ");

			if (mReRiskCode != null && !mReRiskCode.equals("")) {
				sqlStrBuf.append(" and a.RiskCode='" + mReRiskCode + "' ");
			}
			if (mRIcomCode != null && !mRIcomCode.equals("")) {
				sqlStrBuf.append(" and b.ComPanyNo = '" + mRIcomCode + "' ");
			}
			if (mTempType.equals("2")) {
				sqlStrBuf.append(" and a.ReinsreFlag in ('00','03') ");
			} else if (mTempType.equals("1")) {
				sqlStrBuf
						.append(" and (a.ReinsreFlag not in ('00','03') or a.ReinsreFlag is null) ");
			}
			sqlStrBuf
					.append(" group by b.ComPanyNo,a.grpcontno,a.Insuredno,a.riskcode");
			sqlStrBuf.append(" union all ");

			sqlStrBuf
					.append(" select '中国再保险股份有限公司' A0, (select GrpName from lcgrpcont where grpcontno = a.grpcontno) A1, a.Grpcontno A25,'团体保障保险' A2,(select max(cvalidate) from lcgrpcont where grpcontno = a.grpcontno) A3,max(a.cvalidate) A4,max(a.enddate) A5,max(a.enddate) A6,max(a.PayIntv) A7,(select name from laagent where agentcode=(select AgentCode from lcgrppol where grppolno=a.grppolno)) A8,(case when max(a.SaleChnl) in ('04', '06') then max(10) else 0 end) A9, nvl(sum(a.prem), 0) A10,0 A11,(select nvl(sum(d.ReturnPay), 0) from ripolrecordbake c, reinsurerecordtrace d where c.eventno = d.eventno and c.SerialNo = d.SerialNo and c.eventtype = '04' and c.serialno = '"
							+ mStaTerm
							+ "' and c.StandbyString1 = '1' and c.insuredno = a.insuredno and c.riskcode = a.riskcode and d.ComPanyNo='"
							+ mRIcomCode
							+ "' ) A12,0 A13,0 A14,0 A15,nvl(sum(a.UnExpireReserve),0)*0.8 A16,nvl(sum(a.UnDetermineReserve),0)*0.8 A17,nvl(sum(a.UnReportReserve),0)*0.8 A18,(select cast(factorvalue as number) from RICalFactorValue where factorcode='CurrInterestRate' and ripreceptno='S000000000') A19,(select nvl(sum(c.UnExpireReserve),0)*0.8 from reservebussness c where c.serialno='"
							+ lastSerialNo
							+ "' and c.grpcontno=a.grpcontno and c.insuredno=a.insuredno and c.riskcode=a.riskcode) A20,(select nvl(sum(c.UnDetermineReserve),0)*0.8 from reservebussness c where c.serialno='"
							+ lastSerialNo
							+ "' and c.grpcontno=a.grpcontno and c.insuredno=a.insuredno and c.riskcode=a.riskcode) A21,(select nvl(sum(c.UnReportReserve),0)*0.8 from reservebussness c where c.serialno='"
							+ lastSerialNo
							+ "' and c.grpcontno=a.grpcontno and c.insuredno=a.insuredno and c.riskcode=a.riskcode) A22,(select name from ldperson where customerno=a.insuredno) A23, (select riskname from lmrisk where riskcode = a.riskcode) A24,a.insuredno A30,a.riskcode A31 from reservebussness a where a.serialno='"
							+ mStaTerm
							+ "' and a.StandbyString1='1' and not exists (select 1 from ripolrecordbake c where c.polno=a.polno and c.dutycode=a.dutycode and c.serialno='"
							+ mStaTerm + "')");
			if (mReRiskCode != null && !mReRiskCode.equals("")) {
				sqlStrBuf.append(" and a.RiskCode='" + mReRiskCode + "' ");
			}
			if (mTempType.equals("2")) {
				sqlStrBuf
						.append("and (select state from ridutystate c where c.proposalno=(select proposalno from lcpol where polno=a.polno) and c.dutycode=a.dutycode) in ('00','03') ");
			} else if (mTempType.equals("1")) {
				sqlStrBuf
						.append(" and exists (select * from ridutystate c where c.proposalno=(select proposalno from lcpol where polno=a.polno) and c.dutycode=a.dutycode and (state is null or state not in ('00','03'))) ");
			}
			sqlStrBuf
					.append(" group by a.grpcontno,a.grppolno, a.Insuredno, a.riskcode");

			sqlStrBuf.append(" ) X ");
			sqlStrBuf.append(" order by A25,A30,A31 desc ");

			System.out.println(" SQL: " + sqlStrBuf.toString());
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(sqlStrBuf.toString());
			// 查询结果的记录条数
			int count = tSSRS.getMaxRow();
			System.out.println("该sql执行后的记录条数为：" + count);
			// 将查询结果存放到临时 二维数组中
			String temp[][] = tSSRS.getAllData();
			String[] strCol;
			for (int i = 0; i < count; i++) {
				strCol = new String[28];
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
				strCol[19] = temp[i][19];
				strCol[20] = temp[i][20];
				strCol[21] = temp[i][21];
				strCol[22] = temp[i][22];
				strCol[23] = temp[i][23];
				strCol[24] = temp[i][24];
				strCol[25] = temp[i][25];
				strCol[26] = temp[i][26];
				strCol[27] = temp[i][27];

				multTable.add(strCol);
			}
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "BL";
			tError.functionName = "";
			tError.errorMessage = "再保合同1承保查询失败";
			this.mErrors.addOneError(tError);
		}
		XmlExport xmlexport = new XmlExport();
		xmlexport.createDocument("LRExportCess1.vts", "printer");
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
		// 保存信息
		xmlexport.addListTable(multTable, Title);
		mResult.clear();
		mResult.addElement(xmlexport);

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		this.mGlobalInput.setSchema((GlobalInput) cInputData
				.getObjectByObjectName("GlobalInput", 0));
		mTransferData = ((TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0));

		if (mTransferData == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}
		mStaTerm = (String) mTransferData.getValueByName("StaTerm");
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
