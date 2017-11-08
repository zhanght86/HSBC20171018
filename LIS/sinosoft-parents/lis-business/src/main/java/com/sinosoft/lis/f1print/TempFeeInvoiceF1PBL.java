package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author lh
 * @version 1.0
 */

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

import java.util.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class TempFeeInvoiceF1PBL {
private static Logger logger = Logger.getLogger(TempFeeInvoiceF1PBL.class);

	/** 传入数据的容器 */
	private VData mInputData = new VData();
	private MMap map = new MMap();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private String mOperate = "";

	private TransferData inTransferData = new TransferData();

	private String mContNo = "";//合同号

	private String mAppntName = "";//投保人姓名

	private String mGetNoticeNo = "";//缴费通知书号

	private String mPolNo = "";//银行代码

	private String mPayYears = "";//缴费期

	private String mPayEndYearFlag = "";//终交年龄年期标志

	private int PRTNUM = 7;//发票中可打印的险种信息条数

	public TempFeeInvoiceF1PBL() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		try {
			// 得到外部传入的数据，将数据备份到本类中
			if (!getInputData(cInputData)) {
				return false;
			}
			mResult.clear();
			// 业务处理
			if (!dealData()) {
				return false;
			}
			return true;
		} catch (Exception e) {
			// @@错误处理
			CError.buildErr(this, "Exception in TempFeeInvoiceBL");
			return false;
		}

	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
//		 准备所有要打印的数据
		if (!getPrintData()) {
			return false;
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData mInputData) {
		inTransferData = (TransferData) mInputData.getObjectByObjectName("TransferData", 0);
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);

		mContNo = (String) inTransferData.getValueByName("ContNo");
		if ("".equals(mContNo) || mContNo == null) {
			CError.buildErr(this, "合同号为空,请检查是否正确输入了保单号");
			return false;
		}
		
		mAppntName = (String) inTransferData.getValueByName("AppntName");
		mGetNoticeNo = (String) inTransferData.getValueByName("GetNoticeNo");
		if ("".equals(mGetNoticeNo) || mGetNoticeNo == null) {
			CError.buildErr(this, "缴费通知书号为空,请检查是否存在应收记录");
			return false;
		}
		mPolNo = (String) inTransferData.getValueByName("PolNo");

		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private boolean getPrintData() {
		TextTag texttag = new TextTag();// 新建一个TextTag的实例
		XmlExport xmlexport = new XmlExport();// 新建一个XmlExport的实例
		xmlexport.createDocument("TempFeeInvoice.vts", "printer");// 最好紧接着就初始化xml文档
		//校验合同/保单信息是否存在
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mContNo);
		if (!tLCContDB.getInfo()) {
			CError.buildErr(this, "合同不存在");
			return false;
		}
		texttag.add("ContNo", mContNo);
		texttag.add("AppntName", mAppntName);

		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(mPolNo);
		if (!tLCPolDB.getInfo()) {
			CError.buildErr(this, "保单不存在");
			return false;
		}
		mPayEndYearFlag = tLCPolDB.getPayEndYearFlag();
		if("Y".equals(mPayEndYearFlag)){
			mPayYears = tLCPolDB.getPayEndYear()+"年";
		}else if("M".equals(mPayEndYearFlag)){
			mPayYears = tLCPolDB.getPayEndYear()+"月";
		}else if("D".equals(mPayEndYearFlag)){
			mPayYears = tLCPolDB.getPayEndYear()+"天";
		}else if("A".equals(mPayEndYearFlag)){//对于终交年期标志为“A-年龄”：PayYears存放将根据年龄折算成的需要交费的年数。
			mPayYears = tLCPolDB.getPayEndYear()+"岁";
		}
		texttag.add("PayYears", mPayYears);
		
        String CurrentDate = PubFun.getCurrentDate();
        int a = CurrentDate.lastIndexOf("-");
        String tYear = CurrentDate.substring(0,4);
        String tMonth = CurrentDate.substring(5,a);
        String tDay = CurrentDate.substring(a+1,CurrentDate.length());
        
        texttag.add("Year",tYear);
        texttag.add("Month",tMonth);
        texttag.add("Day",tDay);
		
		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(tLCPolDB.getAgentCode());
		if (!tLAAgentDB.getInfo()) {
			CError.buildErr(this, "该保单的代理人不存在");
			return false;
		}
		texttag.add("AgentName", tLAAgentDB.getName());
		texttag.add("AgentCode", tLAAgentDB.getAgentCode());
		//校验应收信息
		LJSPayDB tLJSPayDB = new LJSPayDB();
		tLJSPayDB.setGetNoticeNo(mGetNoticeNo);
		tLJSPayDB.setCurrency("01");
		if (!tLJSPayDB.getInfo()) {
			CError.buildErr(this, "此笔交易不存在应收信息");
			return false;
		}
		
		double SumMoney = (double) tLJSPayDB.getSumDuePayMoney();
		java.text.DecimalFormat format = new java.text.DecimalFormat("0.00");
		String DSumMoney = getChnMoney(SumMoney).replaceFirst("人民币", "");
		texttag.add("DSumMoney", DSumMoney);
		texttag.add("XSumMoney", format.format(SumMoney));
		//准备应收明细信息（合同下各险种）
		String ljsSql = "select (select riskname from lmriskapp where riskcode=a.riskcode),sum(a.sumactupaymoney) t from ljspayperson a where getnoticeno='" + "?mGetNoticeNo?" + "' and paytype='ZC' group by riskcode Order By t Desc";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(ljsSql);
		sqlbv1.put("mGetNoticeNo", mGetNoticeNo);
		ExeSQL nExeSQL = new ExeSQL();
		SSRS nSSRS = nExeSQL.execSQL(sqlbv1);
		if (nSSRS.MaxRow <= 0) {
			CError.buildErr(this, "此笔交易不存在应收明细信息");
			return false;
		} else {
			for (int i = 1; i <= nSSRS.MaxRow; i++) {
				if (PRTNUM < i) {
					break;
				}
				double aMoney = Double.parseDouble(nSSRS.GetText(i, 2));
				texttag.add("FEE_ROW" + i + "COL1", nSSRS.GetText(i, 1));
				texttag.add("FEE_ROW" + i + "COL2", format.format(aMoney));
				
			}
		}

		//向LOPRTManager2表中插入一条记录
		if(!dealLOPRTManager2(tLJSPayDB.getSchema())){
			return false;
		}
		if (texttag.size() > 0)
			xmlexport.addTextTag(texttag);
		mResult.clear();
		mResult.addElement(xmlexport);
		return true;

	}

	public boolean dealLOPRTManager2(LJSPaySchema tLJSPaySchema) {
		LOPRTManager2Schema tLOPRTManager2Schema = new LOPRTManager2Schema();
		try {
			String tLimit = PubFun.getNoLimit(tLJSPaySchema.getManageCom());
			String prtSeqNo = PubFun1.CreateMaxNo("PRTSEQNO", tLimit);
			tLOPRTManager2Schema.setPrtSeq(prtSeqNo);
			tLOPRTManager2Schema.setOtherNo(tLJSPaySchema.getOtherNo());
			tLOPRTManager2Schema.setOtherNoType("02");//其他号码类型 02 --- 合同号
			tLOPRTManager2Schema.setCode(PrintManagerBL.CODE_PINVOICE);
			tLOPRTManager2Schema.setManageCom(tLJSPaySchema.getManageCom());
			tLOPRTManager2Schema.setAgentCode(tLJSPaySchema.getAgentCode());
			tLOPRTManager2Schema.setReqCom(tLJSPaySchema.getManageCom());
			tLOPRTManager2Schema.setReqOperator(tLJSPaySchema.getOperator());
			tLOPRTManager2Schema.setPrtType("0");//打印方式 0 -- 前台打印
			tLOPRTManager2Schema.setStateFlag("0");//打印状态 0 -- 提交
			tLOPRTManager2Schema.setMakeDate(PubFun.getCurrentDate());
			tLOPRTManager2Schema.setMakeTime(PubFun.getCurrentTime());
			map.put(tLOPRTManager2Schema,"INSERT");
			  //提交数据库
			mInputData.clear();
			mInputData.add(map);
			PubSubmit tPubSubmit = new PubSubmit();
			if (!tPubSubmit.submitData(mInputData, ""))
		    {
				CError.buildErr(this, "提交数据失败");
				return false;
		    }
		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		PFeeInvoiceF1PBL tPFeeInvoiceF1PBL1 = new PFeeInvoiceF1PBL();

		logger.debug(PubFun.setPrecision(0.00, "100.1"));
		java.text.DecimalFormat format = new java.text.DecimalFormat("0.00");
		logger.debug(format.format(300));
		logger.debug(format.format(Double.parseDouble("-0.01")));
		logger.debug(PubFun.getChnMoney(10.30));
		logger.debug(tPFeeInvoiceF1PBL1.getChnMoney(10.30));
		
	}

	/**
	 * 添加仟、佰、拾等单位信息
	 * 
	 * @param strUnit
	 *            String
	 * @param digit
	 *            String
	 * @return String
	 */
	public static String getChnM(String strUnit, String digit) {
		String sMoney = "";
		boolean flag = false;

		if (strUnit.equals("0000")) {
			sMoney += "0";
			return sMoney;
		}
		if (!strUnit.substring(0, 1).equals("0")) {
			sMoney += getNum(strUnit.substring(0, 1)) + "仟";
		} else {
			sMoney += "0";
			flag = true;
		}
		if (!strUnit.substring(1, 2).equals("0")) {
			sMoney += getNum(strUnit.substring(1, 2)) + "佰";
			flag = false;
		} else {
			if (!flag) {
				sMoney += "0";
				flag = true;
			}
		}
		if (!strUnit.substring(2, 3).equals("0")) {
			sMoney += getNum(strUnit.substring(2, 3)) + "拾";
			flag = false;
		} else {
			if (!flag) {
				sMoney += "0";
				flag = true;
			}
		}
		if (!strUnit.substring(3, 4).equals("0")) {
			sMoney += getNum(strUnit.substring(3, 4));
		} else {
			if (!flag) {
				sMoney += "0";
				flag = true;
			}
		}

		if (sMoney.substring(sMoney.length() - 1, sMoney.length()).equals("0")) {
			sMoney = sMoney.substring(0, sMoney.length() - 1) + digit.trim();
			// + "0"; //为什么要在结尾加0
		} else {
			sMoney += digit.trim();
		}
		return sMoney;
	}

	private static String getNum(String value) {
		String sNum = "";
		Integer I = new Integer(value);
		int iValue = I.intValue();
		switch (iValue) {
		case 0:
			sNum = "零";
			break;
		case 1:
			sNum = "壹";
			break;
		case 2:
			sNum = "贰";
			break;
		case 3:
			sNum = "叁";
			break;
		case 4:
			sNum = "肆";
			break;
		case 5:
			sNum = "伍";
			break;
		case 6:
			sNum = "陆";
			break;
		case 7:
			sNum = "柒";
			break;
		case 8:
			sNum = "捌";
			break;
		case 9:
			sNum = "玖";
			break;
		}
		return sNum;
	}
	//	PubFun.getChnMoney()存在以下问题：转换10.30的结果为人民币壹拾零元叁角整（本类修改后getChnMoney()执行结果为的人民币壹拾元叁角整）
	public static String getChnMoney(double money) {
		String ChnMoney = "";
		String s0 = "";

		// 在原来版本的程序中，getChnMoney(585.30)得到的数据是585.29。

		if (money == 0.0) {
			ChnMoney = "人民币零元整";
			return ChnMoney;
		}

		if (money < 0) {
			s0 = "负";
			money *= (-1);
		}

		String sMoney = new DecimalFormat("0").format(money * 100);

		int nLen = sMoney.length();
		String sInteger;
		String sDot;
		if (nLen < 2) {
			// add by JL at 2004-9-14
			sInteger = "";
			if (nLen == 1) {
				sDot = "0" + sMoney.substring(nLen - 1, nLen);
			} else {
				sDot = "0";
			}
		} else {
			sInteger = sMoney.substring(0, nLen - 2);
			sDot = sMoney.substring(nLen - 2, nLen);
		}

		String sFormatStr = formatStr(sInteger);

		String s1 = getChnM(sFormatStr.substring(0, 4), "亿");

		String s2 = getChnM(sFormatStr.substring(4, 8), "万");

		String s3 = getChnM(sFormatStr.substring(8, 12), "");

		String s4 = getDotM(sDot);

		if (s1.length() > 0 && s1.substring(0, 1).equals("0")) {
			s1 = s1.substring(1, s1.length());
		}
		if (s1.length() > 0
				&& s1.substring(s1.length() - 1, s1.length()).equals("0")
				&& s2.length() > 0 && s2.substring(0, 1).equals("0")) {
			s1 = s1.substring(0, s1.length() - 1);
		}
		if (s2.length() > 0
				&& s2.substring(s2.length() - 1, s2.length()).equals("0")
				&& s3.length() > 0 && s3.substring(0, 1).equals("0")) {
			s2 = s2.substring(0, s2.length() - 1);
		}
		if (s4.equals("00")) {
			s4 = "";
			if (s3.length() > 0
					&& s3.substring(s3.length() - 1, s3.length()).equals("0")) {
				s3 = s3.substring(0, s3.length() - 1);
			}
		}
		if (s3.length() > 0
				&& s3.substring(s3.length() - 1, s3.length()).equals("0")
				&& s4.length() > 0 && s4.substring(0, 1).equals("0")) {
			s3 = s3.substring(0, s3.length() - 1);
		}
		if (s4.length() > 0
				&& s4.substring(s4.length() - 1, s4.length()).equals("0")) {
			s4 = s4.substring(0, s4.length() - 1);
		}
		if (s3.equals("0")) {
			s3 = "";
			s4 = "0" + s4;
		}

		ChnMoney = s0 + s1 + s2 + s3 + "元" + s4;
		if (ChnMoney.substring(0, 1).equals("0")) {
			ChnMoney = ChnMoney.substring(1, ChnMoney.length());
		}
		for (int i = 0; i < ChnMoney.length(); i++) {
			if (ChnMoney.substring(i, i + 1).equals("0")) {
				ChnMoney = ChnMoney.substring(0, i) + "零"
						+ ChnMoney.substring(i + 1, ChnMoney.length());
			}
		}

		if (sDot.substring(1, 2).equals("0")) {
			ChnMoney += "整";
		}

		return "人民币" + ChnMoney;
	}

	private static String formatStr(String sIn) {
		int n = sIn.length();
		String sOut = sIn;
		// int i = n % 4;

		for (int k = 1; k <= 12 - n; k++) {
			sOut = "0" + sOut;
		}
		return sOut;
	}

	private static String getDotM(String sIn) {
		String sMoney = "";
		if (!sIn.substring(0, 1).equals("0")) {
			sMoney += getNum(sIn.substring(0, 1)) + "角";
		} else {
			sMoney += "0";
		}
		if (!sIn.substring(1, 2).equals("0")) {
			sMoney += getNum(sIn.substring(1, 2)) + "分";
		} else {
			sMoney += "0";
		}

		return sMoney;
	}
}
