package com.sinosoft.lis.autopay;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCAppntIndDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 承保暂交费业务逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author LiuYansong
 * @version 1.0
 */
public class PrintAutoDJAllBL {
private static Logger logger = Logger.getLogger(PrintAutoDJAllBL.class);
	public CErrors mErrors = new CErrors();
	private VData mInputData;
	private VData mResult = new VData();
	private String mOperate;

	// 设定全局变量
	String mStartDate = "2000-01-01";
	String mEndDate = "2030-01-01";
	String mManageCom = "86";
	SSRS mSSRS = new SSRS();

	double SumMoney = 0.00;
	String Date3 = "";

	private GlobalInput mG = new GlobalInput();

	public PrintAutoDJAllBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		if (!getInputData(cInputData))
			return false;
		mSSRS = getEdorNo();
		logger.debug("查询的结果集是" + mSSRS.getMaxRow());
		if (mSSRS.getMaxRow() <= 0) {

			this.buildError("PrintAutoDJAllBL", "在该时间段内该管理机构下没用自动垫教的数据！");
			return false;
		}

		// 针对查询的结果来循环
		if (!getPrintData())
			return false;
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	private boolean getInputData(VData cInputData) {
		String aStartDate = "";
		String aEndDate = "";
		String aManageCom = "";
		aStartDate = (String) cInputData.get(0);
		aEndDate = (String) cInputData.get(1);
		aManageCom = (String) cInputData.get(2);
		if (!(aStartDate == null || aStartDate.equals(""))) {
			mStartDate = aStartDate;
		}
		if (!(aEndDate == null || aEndDate.equals(""))) {
			mEndDate = aEndDate;
		}
		if (!(aManageCom == null || aManageCom.equals(""))) {
			mManageCom = aManageCom;
		}
		logger.debug("开始日期是" + mStartDate);

		mG.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		return true;
	}

	private SSRS getEdorNo() {
		// 查询需要打印的信息
		String strSQL = " select LOLoan.PolNo,(select t.tempfeeno from ljtempfeeclass t where t.chequeno=LOLoan.Actugetno ),LOLoan.LoanDate,LOLoan.LeaveMoney "
				+ " from LOLoan,LOPRTManager where LOLoan.PolNo = LOPRTManager.OtherNo "
				+ " and LOPRTManager.remark = LOLoan.EdorNo"
				+ " and LOPRTManager.ManageCom like concat('"
				+ "?like?"
				+ "','%')"
				+ " and LOPRTManager.code = '41' and LOPRTManager.StateFlag = '0'"
				+ " and LOLoan.LoanType = '1' "
				+ " and LOPRTManager.MakeDate>='"
				+ "?MakeDate?"
				+ "'"
				+ " and LOPRTManager.MakeDate <= '" + "?MakeDate1?" + "'";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(strSQL);
		sqlbv1.put("like", mManageCom);
		sqlbv1.put("MakeDate", mStartDate);
		sqlbv1.put("MakeDate1", mEndDate);
		logger.debug("查询的语句是：");
		logger.debug(strSQL);
		ExeSQL aexesql = new ExeSQL();
		SSRS aSSRS = aexesql.execSQL(sqlbv1);
		return aSSRS;
	}

	private boolean getPrintData() {
		ListTable tlistTable = new ListTable();
		tlistTable.setName("MODE");

		TextTag texttag = new TextTag();// 新建一个TextTag的实例
		XmlExport xmlexport = new XmlExport();// 新建一个XmlExport的实例
		xmlexport.createDocument("PrintAutoAll.vts", "printer");

		ListTable alistTable = new ListTable();
		alistTable.setName("INFO");
		for (int aCount = 1; aCount <= mSSRS.getMaxRow(); aCount++) {
			// 查询的对应的关系。查询出主险和附加险分别的金额
		/*	String main_sql = "select polno ,sum(sumactupaymoney) ,PayDate from ljapayperson where getnoticeno = '"
					+ mSSRS.GetText(aCount, 2)
					+ "' group by polno ,paydate order by polno   ";*/
			String main_sql = "select polno ,sum(sumactupaymoney) ,PayDate from ljapayperson where getnoticeno = '"
					+ "?getnoticeno?"
					+ "' group by polno ,paydate order by polno   ";
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(main_sql);
			sqlbv.put("getnoticeno", mSSRS.GetText(aCount, 2));
			logger.debug("查询主表的查询语句是" + main_sql);
			ExeSQL main_exesql = new ExeSQL();
			SSRS main_ssrs = main_exesql.execSQL(sqlbv);
			if (main_ssrs.getMaxRow() == 0) {
				this.buildError("submitData", "没有主险信息！");
			} else {
				int SumCount = 0;
				// 对主险和附加险查询出明细的信息
				for (int main_count = 1; main_count <= main_ssrs.getMaxRow(); main_count++) {
					SumCount = SumCount + 1;
					SumMoney = SumMoney
							+ Double.parseDouble(main_ssrs.GetText(main_count,
									2));
					logger.debug("总金额是" + SumMoney);
				}
				LCPolDB tLCPolDB = new LCPolDB();
				tLCPolDB.setPolNo(mSSRS.GetText(aCount, 1));

				if (!tLCPolDB.getInfo()) {
					this.mErrors.copyAllErrors(tLCPolDB.mErrors);
					CError tError = new CError();
					tError.moduleName = "RegisterBL";
					tError.functionName = "submitData";
					tError.errorMessage = "数据提交失败!";
					this.mErrors.addOneError(tError);
					return false;
				}
				// Date2 =
				// CaseFunPub.Display_Year(main_ssrs.GetText(main_count,3));//获得交费日期
				LMRiskDB tLMRiskDB = new LMRiskDB();
				tLMRiskDB.setRiskCode(tLCPolDB.getSchema().getRiskCode());
				tLMRiskDB.getInfo();// 险种名称
				
				String  address_sql=" select b.postaladdress,b.zipcode,b.phone,b.mobile  from lcappnt a ,lcaddress b where a.addressno=b.addressno " 
					+"and a.appntno=b.customerno and a.contno='"+"?contno?"+"' ";
				SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
				sqlbv1.sql(address_sql);
				sqlbv1.put("contno", tLCPolDB.getSchema().getContNo());
				String cont_sql =" select (select max(name) from laagent where agentcode =a.agentcode),a.agentcode,a.cvalidate,"
					            +" (select max(makedate) from loloan where contno =a.contno) from lccont a where a.contno ='"+"?contno1?"+"'";
				SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
				sqlbv2.sql(cont_sql);
				sqlbv2.put("contno1",tLCPolDB.getSchema().getContNo());

				SSRS address_ssrs = new SSRS();
				SSRS cont_ssrs = new SSRS();
				address_ssrs = main_exesql.execSQL(sqlbv1);
				cont_ssrs = main_exesql.execSQL(sqlbv2);
				
				String[] cols = new String[14];
				cols[0] = tLCPolDB.getSchema().getAppntName();// 投保人姓名
				cols[1] = tLCPolDB.getSchema().getMainPolNo();// 主险保单号码

				cols[2] = tLMRiskDB.getSchema().getRiskName();// 险种名称
				cols[3] = main_ssrs.GetText(1, 2);// 保费

				double fjmoney = 0.00;
				fjmoney = Double.parseDouble(mSSRS.GetText(aCount, 4))
						- Double.parseDouble(cols[3]);
				cols[4] = String.valueOf(fjmoney);
				cols[5] = mSSRS.GetText(aCount, 4);
				cols[6] = address_ssrs.GetText(1, 1);// 通信人地址
				cols[7] = address_ssrs.GetText(1, 2);// 通信人邮编
				cols[8] = address_ssrs.GetText(1, 3);// 通信人电话
				cols[9] = address_ssrs.GetText(1, 4);// 通信人手机
				cols[10] = cont_ssrs.GetText(1, 1);// 代理人姓名
				cols[11] = cont_ssrs.GetText(1, 2);// 代理人编码
				cols[12] = cont_ssrs.GetText(1, 3);// 生效日期
				cols[13] = cont_ssrs.GetText(1, 4);// 垫交日期
				alistTable.add(cols);
				// Date1 = CaseFunPub.Display_Year(Date1);//借款日期，需要前台传入

			}
		}
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(mG.ManageCom);
		tLDComDB.getInfo();
		Date3 = PubFun.getCurrentDate();

		logger.debug("开始执行最外部分的循环");
		String[] m_col = new String[6];
		xmlexport.addDisplayControl("displayinfo");
		xmlexport.addListTable(alistTable, m_col);
		texttag.add("MngCom", tLDComDB.getName());
		texttag.add("Date3", Date3);

		if (texttag.size() > 0)
			xmlexport.addTextTag(texttag);
		// xmlexport.outputDocumentToFile("e:\\","PrintAutoDJAllBL");//输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);
		return true;
	}

	public void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "PayNoticeF1PBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
		String aStartDate = "2004-9-1"; // 开始日期
		String aEndDate = "2004-9-15";
		String aStation = "86110000";
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86110000";

		VData tVData = new VData();
		tVData.addElement(aStartDate);
		tVData.addElement(aEndDate);
		tVData.addElement(aStation);
		tVData.addElement(tG);

		PrintAutoDJAllBL tPrintAutoDJAllBL = new PrintAutoDJAllBL();
		if (tPrintAutoDJAllBL.submitData(tVData, "PRINT")) {
			logger.debug("执行完毕");
		}
	}
}
