package com.sinosoft.lis.bank;
import org.apache.log4j.Logger;

import java.io.File;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCGrpImportLogDB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.f1print.FYDate;
import com.sinosoft.lis.finfee.TempFeeBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PrintJavaToExcel;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.pubfun.ReadFromExcel;
import com.sinosoft.lis.schema.LCGrpImportLogSchema;
import com.sinosoft.lis.schema.LJTempFeeClassSchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCGrpImportLogSet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/*
 * <p>Title: Web业务系统</p>
 * <p>ClassName:ParserGuidIn </p>
 * <p>Description: 现金批量导入交费界面 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Sinosoft </p>
 * @author：yangyf
 * @version：6.0
 * @CreateDate：2004-12-13
 */

public class CashSendBankBL {
private static Logger logger = Logger.getLogger(CashSendBankBL.class);
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData inTransferData = new TransferData();
	private String serialNo = "";
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();
	private String fileName = "";
	private String FilePath = "";
	private String mEFail = "失败";
	private String mESucc = "成功";
	private String mFilePath = "";
	private PrintJavaToExcel tPrintJavaToExcel = new PrintJavaToExcel();
	/** 传出数据的容器 */
	private VData mResult = new VData();

	// @Constructors
	public CashSendBankBL() {

	}

	public CashSendBankBL(String fileName) {

	}

	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("开始时间:" + PubFun.getCurrentTime());
		// 校验传入数据是否有空
		if (!getInputData(cInputData)) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ParseGuideIn";
			tError.functionName = "checkData";
			tError.errorMessage = "提取值错误，无法正常提交，请重新提交";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 做数据导入业务表中
		if (!InsertInToTable()) {
			CError tError = new CError();
			tError.moduleName = "ParseGuideIn";
			tError.functionName = "checkData";
			tError.errorMessage = "插入时发生错误。请确认后重新返回";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 做业务上的处理
		if (!DealData()) {
			CError tError = new CError();
			tError.moduleName = "ParseGuideIn";
			tError.functionName = "checkData";
			tError.errorMessage = "业务处理失败。请联系电脑部";
			this.mErrors.addOneError(tError);
			return false;
		}
		tPrintJavaToExcel.endCreate();
		return true;
	}

	/**
	 * 得到传入数据
	 */
	private boolean getInputData(VData mInputData) {
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (mGlobalInput == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ParseGuideIn";
			tError.functionName = "checkData";
			tError.errorMessage = "无操作员信息，请重新登录!";
			this.mErrors.addOneError(tError);
			return false;
		}

		inTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		fileName = (String) inTransferData.getValueByName("fileName");
		fileName = fileName.replace('\\', '/');

		mFilePath = (String) inTransferData.getValueByName("filePath");
		mFilePath = mFilePath.replace('\\', '/');

		if (inTransferData == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ParseGuideIn";
			tError.functionName = "checkData";
			tError.errorMessage = "无导入文件信息，请重新导入!";
			this.mErrors.addOneError(tError);
			return false;
		}
		serialNo = PubFun1.CreateMaxNo("1", 20);
		tPrintJavaToExcel.CreateExcel(mFilePath + serialNo);
		tPrintJavaToExcel.CreateSheet(mEFail);
		tPrintJavaToExcel.CreateSheet(mESucc);

		return true;
	}

	public String getFileName() {
		String t = serialNo + ".xls";
		return t;
	}

	/**
	 * 做插入业务表的动作，如果在此时发生数据错误将重新生成Excel并进行数据导出
	 * 
	 */
	public boolean InsertInToTable() {
		logger.debug("=======文件路径========" + fileName);
		ReadFromExcel tReadFromExcel = new ReadFromExcel(fileName);
		tReadFromExcel.setSheet("Sheet1");
		int sRow = tReadFromExcel.getSheetRow();
		int tSerno = 1;
		LCGrpImportLogSchema tLCGrpImportLogSchema = null;
		FYDate tFYDate = null;
		MMap tMap = null;
		for (int i = 0; i < sRow; i++) {
			tMap = new MMap();
			String tContno = tReadFromExcel.ReadValue(i, 0);
			String tPayMoney = tReadFromExcel.ReadValue(i, 1);
			String tDay = tReadFromExcel.ReadValue(i, 2);
			// 校验得到的保单号和金额是否为有效的值
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			String sql = "select contno from lccont where contno = '" + "?contno?"
					+ "'";
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(sql);
			sqlbv1.put("contno", tContno);
			tSSRS = tExeSQL.execSQL(sqlbv1);
			if (tSSRS.getMaxRow() > 0) {

			} else {
				WriteFail(tContno, tPayMoney, "该保单不在系统中，请确认后重新导入该笔数据");
				continue;
			}
			try {
				if (Double.parseDouble(tPayMoney) == 0) {
					WriteFail(tContno, tPayMoney, "金额为零，系统不接收");
					continue;
				}
			} catch (Exception e) {
				WriteFail(tContno, tPayMoney, "金额不是有效的金额");
				continue;
			}
			if (tDay == null || tDay.equals("null") || tDay.equals("")) {
				WriteFail(tContno, tPayMoney, "日期为空，无法正常导入，请确认");
				continue;
			}
			try {
				tFYDate = new FYDate(tDay);
				logger.debug(tFYDate.getOracleDate());
				tDay = tFYDate.getOracleDate();

			} catch (Exception d) {
				WriteFail(tContno, tPayMoney, "日期格式不是有效的日期格式,无法导入");
				continue;
			}

			sql = "select OtherNo from LCGrpImportLog where OtherNo = '"
					+ "?OtherNo?" + "'" + " and StandbyFlag3 = '" + "?StandbyFlag3?"
					+ "' and OtherNoType = 'J'";
			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
			sqlbv2.sql(sql);
			sqlbv2.put("OtherNo", tContno);
			sqlbv2.put("StandbyFlag3", tDay);
			logger.debug(sql);
			tSSRS = tExeSQL.execSQL(sqlbv2);
			if (tSSRS.getMaxRow() > 0) {
				WriteFail(tContno, tPayMoney, "该笔数据现在已数据返回，本次提交为重复提交，请确认");
				continue;
			}

			logger.debug("+++++第+" + i + "++张++++" + tContno);
			logger.debug("==========金额=====" + tPayMoney);
			try {
				tLCGrpImportLogSchema = new LCGrpImportLogSchema();
				tLCGrpImportLogSchema.setGrpContNo("00000000000000000000");
				tLCGrpImportLogSchema.setBatchNo(serialNo);
				tLCGrpImportLogSchema.setOtherNoType("J");
				tLCGrpImportLogSchema.setOtherNo(tContno);

				tLCGrpImportLogSchema.setID(String.valueOf(tSerno));
				tLCGrpImportLogSchema.setStandbyFlag3(tDay);
				tSerno++;
				tLCGrpImportLogSchema.setStandbyFlag2(tPayMoney);
				tLCGrpImportLogSchema.setMakeDate(PubFun.getCurrentDate());
				tLCGrpImportLogSchema.setMakeTime(PubFun.getCurrentTime());
				tLCGrpImportLogSchema.setOperator(mGlobalInput.Operator);
				tLCGrpImportLogSchema.setStandbyFlag1(mGlobalInput.ManageCom);
				tMap.put(tLCGrpImportLogSchema, "INSERT");
			} catch (Exception ex) {
				WriteFail(tContno, tPayMoney, "未读取到EXCEL中的有效值");
				ex.printStackTrace();
				continue;
			}
			PubSubmit(tMap, tContno, tPayMoney, true);

		}
		return true;
	}

	/**
	 * 将错误的信息写在excel中的fail Sheet中
	 * 
	 */
	private void WriteFail(String tContNo, String tPayMoney, String tMessage) {
		String[] str = new String[3];
		str[0] = tContNo;
		str[1] = tPayMoney;
		str[2] = tMessage;
		tPrintJavaToExcel.SetSheet(mEFail);
		tPrintJavaToExcel.addCell(str);

	}

	/**
	 * 将错误的信息写在excel中的fail Sheet中
	 * 
	 */
	private void WriteSucc(String tContNo, String tPayMoney) {
		String[] str = new String[3];
		str[0] = tContNo;
		str[1] = tPayMoney;
		tPrintJavaToExcel.SetSheet(mESucc);
		tPrintJavaToExcel.addCell(str);

	}

	/**
	 * DealData 做为业务处理的表提交
	 * 
	 */
	public boolean DealData() {
		String tSql = "select * from LCGrpImportLog where BatchNo = '"
				+ "?BatchNo?" + "'";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(tSql);
		sqlbv3.put("BatchNo", serialNo);
		LCGrpImportLogSet tLCGrpImportLogSet = new LCGrpImportLogSet();
		LCGrpImportLogDB tLCGrpImportLogDB = new LCGrpImportLogDB();
		tLCGrpImportLogSet = tLCGrpImportLogDB.executeQuery(sqlbv3);
		for (int i = 0; i < tLCGrpImportLogSet.size(); i++) {

			String tContNo = tLCGrpImportLogSet.get(i + 1).getOtherNo();
			String tPayMoney = String.valueOf(tLCGrpImportLogSet.get(i + 1)
					.getStandbyFlag2());
			logger.debug("=============" + tContNo
					+ "===================");
			logger.debug("=============" + tPayMoney
					+ "===================");
			tSql = "select * from lccont where contno = '" + "?contno?" + "'";
			SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
			sqlbv4.sql(tSql);
			sqlbv4.put("contno", tContNo);
			LCContSet tLCContSet = new LCContSet();
			LCContDB tLCContDB = new LCContDB();
			tLCContSet = tLCContDB.executeQuery(sqlbv4);
			LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();
			LJTempFeeClassSet tLJTempFeeClassSet = new LJTempFeeClassSet();
			LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
			LJTempFeeClassSchema tLJTempFeeClassSchema = new LJTempFeeClassSchema();
			String tSalenl = tLCContSet.get(1).getSaleChnl();
			String tOthernotype = "";
			if (tSalenl.equals("1")) {
				tOthernotype = "02";

			} else if (tSalenl.equals("3")) {
				tOthernotype = "03";
			}

			tLJTempFeeSchema.setOtherNo(tContNo);
			tLJTempFeeSchema.setRiskCode("000000");
			tLJTempFeeSchema.setAgentCode(tLCContSet.get(1).getAgentCode());
			tLJTempFeeSchema.setTempFeeType("3");
			tLJTempFeeSchema.setPayDate(PubFun.getCurrentDate());
			tLJTempFeeSchema.setPayIntv("12");
			tLJTempFeeSchema.setPayMoney(tPayMoney);
			tLJTempFeeSchema.setOperator(mGlobalInput.Operator);
			tLJTempFeeSchema.setEnterAccDate(PubFun.getCurrentDate());
			tLJTempFeeSchema.setAgentGroup(tLCContSet.get(1).getAgentGroup());
			tLJTempFeeSchema.setManageCom(tLCContSet.get(1).getManageCom());
			tLJTempFeeSchema.setPolicyCom(tLCContSet.get(1).getManageCom());
			tLJTempFeeSchema.setOperator(mGlobalInput.Operator);
			tLJTempFeeSchema.setOtherNoType(tOthernotype);
			tLJTempFeeSchema.setSaleChnl(tSalenl);
			tLJTempFeeSet.add(tLJTempFeeSchema);
			//
			tLJTempFeeClassSchema.setPayMode("1");
			tLJTempFeeClassSchema.setChequeNo("00000000");
			tLJTempFeeClassSchema.setPayMoney(tPayMoney);
			tLJTempFeeClassSchema.setEnterAccDate(PubFun.getCurrentDate());
			tLJTempFeeClassSchema
					.setManageCom(tLCContSet.get(1).getManageCom());
			tLJTempFeeClassSchema
					.setPolicyCom(tLCContSet.get(1).getManageCom());

			// tLJTempFeeClassSchema.setBankAccNo("77777777777777");
			// tLJTempFeeClassSchema.setBankCode("01");
			// tLJTempFeeClassSchema.setAccName("月亮");
			tLJTempFeeClassSchema.setOperator(mGlobalInput.Operator);
			// tLJTempFeeClassSchema.setManageCom("86");
			tLJTempFeeClassSchema.setOtherNo(tContNo);
			tLJTempFeeClassSchema.setOtherNoType(tOthernotype);

			tLJTempFeeClassSet.add(tLJTempFeeClassSchema);
			VData tVData = new VData();
			tVData.addElement(tLJTempFeeSet);
			tVData.addElement(tLJTempFeeClassSet);
			// tVData.addElement(tTransferData);
			tVData.addElement(mGlobalInput);

			TempFeeBL tTempFeeBL = new TempFeeBL();

			if (!tTempFeeBL.submitData(tVData, "INSERT")) {
				logger.debug(tTempFeeBL.mErrors.getErrContent());
			}
			VData mVData = new VData();
			mVData = tTempFeeBL.getResult();
			PubSubmit tPubSubmit = new PubSubmit();
			if (!tPubSubmit.submitData(mVData, "")) {
				WriteFail(tContNo, tPayMoney, "财务确认失败，请做最后确认是否发生财务交费！");
			} else {
				WriteSucc(tContNo, tPayMoney);
			}

		}
		return true;
	}

	/**
	 * 提交生成的数据
	 * 
	 */
	public boolean PubSubmit(MMap tMap, String tContNo, String tPayMoney,
			boolean Flag) {
		VData tVData = new VData();
		tVData.add(tMap);
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(tVData, "")) {
			// 写入成功的保单信息
		} else {
			// 写入失败的保单信息
		}
		return true;
	}

	/**
	 * 得到生成文件路径
	 * 
	 * @return
	 */
	private boolean getFilePath() {
		LDSysVarDB tLDSysVarDB = new LDSysVarDB();
		tLDSysVarDB.setSysVar("XmlPath2");
		if (!tLDSysVarDB.getInfo()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ParseGuideIn";
			tError.functionName = "checkData";
			tError.errorMessage = "缺少文件导入路径!";
			this.mErrors.addOneError(tError);
			return false;
		} else
			FilePath = tLDSysVarDB.getSysVarValue();

		return true;
	}

	/**
	 * 检验文件是否存在
	 * 
	 * @return
	 */
	private boolean checkXmlFileName(String tFilePath) {
		// XmlFileName = (String)mTransferData.getValueByName("FileName");
		File tFile = new File(tFilePath);
		if (!tFile.exists()) {

			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ParseGuideIn";
			tError.functionName = "checkData";
			tError.errorMessage = "缺少文件导入路径!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	// 程序入口
	public static void main(String arg[]) {
		CashSendBankBL tCashSendBankBL = new CashSendBankBL();
		GlobalInput tmGlobalInput = new GlobalInput();
		VData tVData = new VData();
		tmGlobalInput.Operator = "001";
		tmGlobalInput.ManageCom = "865100";
		tmGlobalInput.ComCode = "865100";
		String fileName = "D:\\apse.xls";
		String mFilePath = "D:\\";
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("fileName", fileName);
		tTransferData.setNameAndValue("filePath", mFilePath);
		tVData.add(tTransferData);
		tVData.add(tmGlobalInput);
		if (!tCashSendBankBL.submitData(tVData, "")) {
			logger.debug("===========失败==============");
		} else {
			logger.debug("===========成功==============");
		}
		// try
		// {
		// FYDate t = new FYDate("2006-02-03");
		//    	
		// logger.debug(t.getOracleDate());
		// }catch(Exception ex)
		// {
		// ex.printStackTrace();
		// }
		// //t.add();
		//    	

	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mResult;
	}

}
