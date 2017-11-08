package com.sinosoft.lis.f1print;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.db.RnewIndUWMasterDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.RnewIndUWMasterSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author kevin
 * @version 1.0
 */

public class RnewRefusePBL extends NoticeF1PBO {
private static Logger logger = Logger.getLogger(RnewRefusePBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();

	private double fPremSum = 0;
	private double fPremAddSum = 0;
	private String mOperate = "";
	private String CurrentDate = PubFun.getCurrentDate();
	private String FORMATMODOL = "0.00";
    // 数字转换对象
	private DecimalFormat mDecimalFormat = new DecimalFormat(FORMATMODOL);

	public RnewRefusePBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 * @param cOperate
	 * @return
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		mOperate = cOperate;
		try {
			if (!cOperate.equals("CONFIRM") && !cOperate.equals("PRINT")) {
				buildError("submitData", "不支持的操作字符串");
				return false;
			}

			// 得到外部传入的数据，将数据备份到本类中
			if (!getInputData(cInputData)) {
				return false;
			}
			if (cOperate.equals("CONFIRM")) {
				mResult.clear();

				// 准备所有要打印的数据
				getPrintData();
			} else if (cOperate.equals("PRINT")) {
				if (!saveData(cInputData)) {
					return false;
				}
			}

			return true;

		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("submitData", ex.toString());
			return false;
		}
	}

	public static void main(String[] args) {
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLOPRTManagerSchema.setSchema((LOPRTManagerSchema) cInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0));

		if (mGlobalInput == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}

		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "RANF1PBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	private void getPrintData() throws Exception {
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();

		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);// 将prtseq传给DB，目的查找所有相关信息，然后还要返回给schema
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}

		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
		if (mLOPRTManagerSchema.getStateFlag() == null) {
			buildError("getprintData", "无效的打印状态");
		} else if (!mLOPRTManagerSchema.getStateFlag().equals("0")) {
			buildError("getprintData", "该打印请求不是在请求状态");
		}
		XmlExport xmlExport = new XmlExport();

		if(!dealData(mLOPRTManagerSchema, xmlExport))
		{
			buildError("getprintData", "准备打印数据时出错");
			throw new Exception("准备打印数据时出错");
		}
		
	}

	private boolean saveData(VData mInputData) {
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			buildError("saveData", "在取得打印队列中数据时发生错误");
			return false;
		}
		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
		mLOPRTManagerSchema.setStateFlag("1");
		mLOPRTManagerSchema.setDoneDate(CurrentDate);
		mLOPRTManagerSchema.setExeOperator(mGlobalInput.Operator);
		tLOPRTManagerDB.setSchema((LOPRTManagerSchema) mInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0));

		mResult.add(mLOPRTManagerSchema);
		mResult.add(tLOPRTManagerDB);
		RnewRefusePBLS tRnewRefusePBLS = new RnewRefusePBLS();
		tRnewRefusePBLS.submitData(mResult, mOperate);
		if (tRnewRefusePBLS.mErrors.needDealError()) {
			mErrors.copyAllErrors(tRnewRefusePBLS.mErrors);
			buildError("saveData", "提交数据库出错！");
			return false;
		}
		return true;
	}

	/**
	 * 处理一条实付记录
	 * 
	 * @param aLJAGetSet
	 * @param xmlExport
	 * @param aLJTempFeeClassSchema
	 * @return
	 */
	private boolean dealData(LOPRTManagerSchema mLOPRTManagerSchema, XmlExport xmlExport) throws Exception {
		
		String tContNo=mLOPRTManagerSchema.getOtherNo();
		boolean bHaveFoundBank = false;
		ListTable listTable = new ListTable();
		
        //1-险种信息：
		logger.debug("***************开始查询本次核保前的保单信息***********************");
		ListTable tRiskListTable = new ListTable();
		String[] RiskInfoTitle = new String[9];
		String[] RiskInfo = new String[9];
		tRiskListTable.setName("MAIN"); // 对应模版投保信息部分的行对象名
		RiskInfoTitle[0] = "RiskName"; // 险种名称
		RiskInfoTitle[1] = "Amnt"; // 保险金额
		RiskInfoTitle[2] = "PayYears"; // 缴费年期
		RiskInfoTitle[3] = "PayIntv"; // 缴费方式（间隔）
		RiskInfoTitle[4] = "Prem"; // 保费
		RiskInfoTitle[5] = "JobAddPrem"; // 职业加费
		RiskInfoTitle[6] = "HealthAddPrem"; // 健康加费
		RiskInfoTitle[7] = "LiveAddPrem"; // 居住地加费 //其他加费
//		RiskInfoTitle[8] = "HobbyAddPrem"; // 爱好加费
		
		double oldSumPrem = 0.00; // 合计保费
		double oldSumJobAddFee = 0.00; // 合计加费       
		double oldSumLiveAddFee = 0.00;
		double oldSumHobbyAddFee = 0.00;
		double oldSumHealthAddFee = 0.00; // 最后的合计金额	
		LMRiskDB tLMRiskDB;
		String sTemp;
		ExeSQL tempExeSQL = new ExeSQL();
		SSRS tempSSRS = new SSRS();
		
		String Sql = "select * from LCPol where contno='"
		+ "?tContNo?" + "' and appflag='1' " 
		+ " order by mainpolno,polno ";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(Sql);
		sqlbv1.put("tContNo", tContNo);
		LCPolDB tempLCPolDB = new LCPolDB();
		LCPolSchema tLCPolSchema_sub = new LCPolSchema();
		LCPolSet tLCPolSet_sub = tempLCPolDB.executeQuery(sqlbv1);
		LCPolSet gLCPolSet = new LCPolSet();  // 保存附加险保单集合,后用
	
		if (tLCPolSet_sub != null) 
		{
			gLCPolSet.set(tLCPolSet_sub); // 保存附加险保单集合,后用
	
			for (int n = 1; n <= gLCPolSet.size(); n++) {
				tLCPolSchema_sub = gLCPolSet.get(n);
				RiskInfo = new String[8];
				tLMRiskDB = new LMRiskDB();
				tLMRiskDB.setRiskCode(tLCPolSchema_sub.getRiskCode());
	
				if (!tLMRiskDB.getInfo()) {
					mErrors.copyAllErrors(tLMRiskDB.mErrors);
					CError.buildErr(this, "在取得LMRisk的数据时发生错误");		
					return false;
				}
	
				RiskInfo[0] = tLMRiskDB.getRiskName(); // 险种名称
				RiskInfo[1] = mDecimalFormat.format(tLCPolSchema_sub.getAmnt()); // 保险金额
	
				if ((tLCPolSchema_sub.getPayEndYear() == 1000)
						&& tLCPolSchema_sub.getPayEndYearFlag().equals("A")) {
					RiskInfo[2] = "终生"; // 交费年期
				} else {
					RiskInfo[2] = (new Integer(tLCPolSchema_sub.getPayYears()))
							.toString(); // 交费年期
				}
	
				sTemp = "";
	
				if (tLCPolSchema_sub.getPayIntv() == -1) {
					sTemp = "不定期交费";
				}
	
				if (tLCPolSchema_sub.getPayIntv() == 0) {
					sTemp = "趸交";
				}
	
				if (tLCPolSchema_sub.getPayIntv() == 1) {
					sTemp = "月交";
				}
	
				if (tLCPolSchema_sub.getPayIntv() == 3) {
					sTemp = "季交";
				}
	
				if (tLCPolSchema_sub.getPayIntv() == 6) {
					sTemp = "半年交";
				}
	
				if (tLCPolSchema_sub.getPayIntv() == 12) {
					sTemp = "年交";
				}
	
				RiskInfo[3] = sTemp; // 交费方式
				RiskInfo[4] = mDecimalFormat.format(tLCPolSchema_sub
						.getStandPrem()); // 保费				
				oldSumPrem = oldSumPrem + tLCPolSchema_sub.getStandPrem(); // 原保费合计
	
				// 险种的职业加费
				SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
				sqlbv2.sql("select (case when sum(Prem) is not null then sum(Prem)  else 0 end) from LCPrem where PolNo='"
						+ "?PolNo?"
						+ "' and PayPlanCode like '000000%'  and payplantype='02'");
				sqlbv2.put("PolNo", tLCPolSchema_sub.getPolNo());
				tempSSRS = tempExeSQL.execSQL(sqlbv2);
	
				if (tempSSRS.MaxCol > 0) {
					if (!(tempSSRS.GetText(1, 1).equals("0") || tempSSRS
							.GetText(1, 1).trim().equals(""))) {
						logger.debug("累计职业加费是"
								+ tempSSRS.GetText(1, 1));
						RiskInfo[5] = mDecimalFormat.format(Double
								.parseDouble(tempSSRS.GetText(1, 1))); // 累计加费
						oldSumJobAddFee = oldSumJobAddFee
								+ Double.parseDouble(tempSSRS.GetText(1, 1)); // 加费合计
					} else {
						RiskInfo[5] = "0.00";
					}
				}
				
				// 险种的健康加费
				SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
				sqlbv3.sql("select (case when sum(Prem) is not null then sum(Prem)  else 0 end) from LCPrem where PolNo='"
						+ "?PolNo?"
						+ "' and PayPlanCode like '000000%'  and payplantype='01'");
				sqlbv3.put("PolNo", tLCPolSchema_sub.getPolNo());
				tempSSRS = tempExeSQL.execSQL(sqlbv3);
	
				if (tempSSRS.MaxCol > 0) {
					if (!(tempSSRS.GetText(1, 1).equals("0") || tempSSRS
							.GetText(1, 1).trim().equals(""))) {								
						logger.debug("累计健康加费是"
								+ tempSSRS.GetText(1, 1));
						RiskInfo[6] = mDecimalFormat.format(Double
								.parseDouble(tempSSRS.GetText(1, 1))); // 累计加费
						oldSumHealthAddFee = oldSumHealthAddFee
								+ Double.parseDouble(tempSSRS.GetText(1, 1)); // 加费合计
					} else {
						RiskInfo[6] = "0.00";
					}
				}
				
				// 险种的居住地加费
				/*tempSSRS = tempExeSQL
						.execSQL("select nvl(sum(Prem),0) from LCPrem where PolNo='"
								+ tLCPolSchema_sub.getPolNo()
								+ "' and PayPlanCode like '000000%'  and payplantype='03'");
	
				if (tempSSRS.MaxCol > 0) {
					if (!(tempSSRS.GetText(1, 1).equals("0") || tempSSRS
							.GetText(1, 1).trim().equals(""))) {
						logger.debug("累计居住地加费是"
								+ tempSSRS.GetText(1, 1));
						RiskInfo[7] = mDecimalFormat.format(Double
								.parseDouble(tempSSRS.GetText(1, 1))); // 累计加费
						oldSumLiveAddFee = oldSumLiveAddFee
								+ Double.parseDouble(tempSSRS.GetText(1, 1)); // 加费合计
					} else {
						RiskInfo[7] = "0.00";
					}
				}
				
				// 险种的爱好加费
				tempSSRS = tempExeSQL
						.execSQL("select nvl(sum(Prem),0) from LCPrem where PolNo='"
								+ tLCPolSchema_sub.getPolNo()
								+ "' and PayPlanCode like '000000%'  and payplantype='04'");
	
				if (tempSSRS.MaxCol > 0) {
					if (!(tempSSRS.GetText(1, 1).equals("0") || tempSSRS
							.GetText(1, 1).trim().equals(""))) {
						logger.debug("累计爱好加费是"
								+ tempSSRS.GetText(1, 1));
						RiskInfo[8] = mDecimalFormat.format(Double
								.parseDouble(tempSSRS.GetText(1, 1))); // 累计加费
						oldSumHobbyAddFee = oldSumHobbyAddFee
								+ Double.parseDouble(tempSSRS.GetText(1, 1)); // 加费合计
					} else {
						RiskInfo[8] = "0.00";
					}
				}*/
				//ZY 2009-05-18 合并为其他加费
				SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
				sqlbv4.sql("select (case when sum(Prem) is not null then sum(Prem)  else 0 end) from LCPrem where PolNo='"
						+"?PolNo?"+ "' and PayPlanCode like '000000%'  and payplantype not in ('01','02')");
				sqlbv4.put("PolNo", tLCPolSchema_sub.getPolNo());
				tempSSRS = tempExeSQL.execSQL(sqlbv4);

				if (tempSSRS.MaxCol > 0) {
					if (!(tempSSRS.GetText(1, 1).equals("0") || tempSSRS
							.GetText(1, 1).trim().equals(""))) {
						logger.debug("累计其他加费是"
								+ tempSSRS.GetText(1, 1));
						RiskInfo[7] = mDecimalFormat.format(Double
								.parseDouble(tempSSRS.GetText(1, 1))); // 累计加费
						oldSumLiveAddFee = oldSumLiveAddFee
								+ Double.parseDouble(tempSSRS.GetText(1, 1)); // 加费合计
					} else {
						RiskInfo[7] = "0.00";
					}
				}
				tRiskListTable.add(RiskInfo); // 加入险种信息
			}
		}
	
		oldSumPrem = Double.parseDouble(mDecimalFormat.format(oldSumPrem)); // 转换计算后的保费(规定的精度)		
		oldSumHealthAddFee = Double.parseDouble(mDecimalFormat.format(oldSumHealthAddFee)); // 转换计算后的保费(规定的精度)
		oldSumJobAddFee = Double.parseDouble(mDecimalFormat
				.format(oldSumJobAddFee));
		oldSumLiveAddFee = Double.parseDouble(mDecimalFormat
				.format(oldSumLiveAddFee));
		oldSumHobbyAddFee = Double.parseDouble(mDecimalFormat
				.format(oldSumHobbyAddFee));
		
		double SumPrem = 0.00;
		double SumJobAddFee = 0.00;
		double SumLiveAddFee = 0.00;
		double SumHobbyAddFee = 0.00;
		double SumHealthAddFee = 0.00; // 最后的合计金额
		ListTable tChangePolListTable = new ListTable();
		String[] ChangePol = new String[9];
		String[] ChangePolTitle = new String[9];

		ChangePolTitle[0] = "RiskName"; // 险种名称
		ChangePolTitle[1] = "Amnt"; // 保险金额
		ChangePolTitle[2] = "PayYears"; // 缴费年期
		ChangePolTitle[3] = "PayIntv"; // 缴费方式（间隔）
		ChangePolTitle[4] = "Prem"; // 保费
		ChangePolTitle[5] = "JobAddPrem"; // 职业加费
		ChangePolTitle[6] = "HealthAddPrem"; // 健康加费
		ChangePolTitle[7] = "LivePrem"; // 居住地加费 //其他加费
//		ChangePolTitle[8] = "HobbyPrem"; // 爱好加费
		tChangePolListTable.setName("ChangePol"); // 对应模版加费部分的行对象名
			
		logger.debug("***************开始查询现在的保单信息***********************");	
		//判断是否为主险续保，且整单拒保，此种情况下不再显示变更后保险计划
		ExeSQL tExeSQL = new ExeSQL();
		String main_refuse_flag="N";
		String check_sql=" select count(*) from lcpol where polno=mainpolno and contno='"+"?tContNo?"+"' and appflag='9' and rnewflag='-2'"
		        +" and exists(select 1 from lmrisk where riskcode=lcpol.riskcode and rnewflag='Y')";
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql(check_sql);
		sqlbv5.put("tContNo", tContNo);
		if(Integer.parseInt(tExeSQL.getOneValue(sqlbv5))>0)
		{
			main_refuse_flag="Y";
		}
		//若为续保险种，rnewflag必须为'-1'
		Sql = "select * from LCPol a where a.contno='"
		+ "?tContNo?" + "' and a.appflag='1' and " 
        + " (exists (select 1 from lmrisk where riskcode=a.riskcode and rnewflag='Y' and a.rnewflag='-1') or a.payintv>0)"
	    +" order by mainpolno,polno ";
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql(Sql);
		sqlbv6.put("tContNo", tContNo);
		LCPolDB tempLCPolDB2 = new LCPolDB();
		LCPolSchema tLCPolSchema_sub2 = new LCPolSchema();
		LCPolSet tLCPolSet_sub2 = tempLCPolDB2.executeQuery(sqlbv6);
	
		LCPolSet gLCPolSet2 = new LCPolSet();
		if (tLCPolSet_sub2 != null) {
			gLCPolSet2.set(tLCPolSet_sub2); // 保存附加险保单集合,后用
	
			for (int n = 1; n <= gLCPolSet2.size(); n++) {
				tLCPolSchema_sub2 = gLCPolSet2.get(n);
				ChangePol = new String[8];
				tLMRiskDB = new LMRiskDB();
				tLMRiskDB.setRiskCode(tLCPolSchema_sub2.getRiskCode());
	
				if (!tLMRiskDB.getInfo()) {
					mErrors.copyAllErrors(tLMRiskDB.mErrors);
					CError.buildErr(this, "在取得LMRisk的数据时发生错误");
	
					return false;
				}
	
				ChangePol[0] = tLMRiskDB.getRiskName(); // 险种名称
				ChangePol[1] = mDecimalFormat.format(tLCPolSchema_sub2.getAmnt()); // 保险金额
	
				if ((tLCPolSchema_sub2.getPayEndYear() == 1000)
						&& tLCPolSchema_sub2.getPayEndYearFlag().equals("A")) {
					ChangePol[2] = "终生"; // 交费年期
				} else {
					ChangePol[2] = (new Integer(tLCPolSchema_sub2.getPayYears()))
							.toString(); // 交费年期
				}
	
				sTemp = "";
	
				if (tLCPolSchema_sub2.getPayIntv() == -1) {
					sTemp = "不定期交费";
				}
	
				if (tLCPolSchema_sub2.getPayIntv() == 0) {
					sTemp = "趸交";
				}
	
				if (tLCPolSchema_sub2.getPayIntv() == 1) {
					sTemp = "月交";
				}
	
				if (tLCPolSchema_sub2.getPayIntv() == 3) {
					sTemp = "季交";
				}
	
				if (tLCPolSchema_sub2.getPayIntv() == 6) {
					sTemp = "半年交";
				}
	
				if (tLCPolSchema_sub2.getPayIntv() == 12) {
					sTemp = "年交";
				}
	
				ChangePol[3] = sTemp; // 交费方式
				ChangePol[4] = mDecimalFormat.format(tLCPolSchema_sub2
						.getStandPrem()); // 保费				
				SumPrem = SumPrem + tLCPolSchema_sub2.getStandPrem(); // 原保费合计
	
				// 险种的职业加费
				SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
				sqlbv7.sql("select (case when sum(Prem) is not null then sum(Prem)  else 0 end) from LCPrem where PolNo='"
						+ "?PolNo?"
						+ "' and PayPlanCode like '000000%'  and payplantype='02'");
				sqlbv7.put("PolNo", tLCPolSchema_sub2.getPolNo());
				tempSSRS = tempExeSQL.execSQL(sqlbv7);
	
				if (tempSSRS.MaxCol > 0) {
					if (!(tempSSRS.GetText(1, 1).equals("0") || tempSSRS
							.GetText(1, 1).trim().equals(""))) {
						logger.debug("累计职业加费是"
								+ tempSSRS.GetText(1, 1));
						ChangePol[5] = mDecimalFormat.format(Double
								.parseDouble(tempSSRS.GetText(1, 1))); // 累计加费
						SumJobAddFee = SumJobAddFee
								+ Double.parseDouble(tempSSRS.GetText(1, 1)); // 加费合计
					} else {
						ChangePol[5] = "0.00";
					}
				}
				
				// 险种的健康加费
				SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
				sqlbv8.sql("select (case when sum(Prem) is not null then sum(Prem)  else 0 end) from LCPrem where  PolNo='"
						+ "?PolNo?"
						+ "' and PayPlanCode like '000000%'  and payplantype='01'");
				sqlbv8.put("PolNo", tLCPolSchema_sub2.getPolNo());
				tempSSRS = tempExeSQL.execSQL(sqlbv8);
	
				if (tempSSRS.MaxCol > 0) {
					if (!(tempSSRS.GetText(1, 1).equals("0") || tempSSRS
							.GetText(1, 1).trim().equals(""))) {								
						logger.debug("累计健康加费是"
								+ tempSSRS.GetText(1, 1));
						ChangePol[6] = mDecimalFormat.format(Double
								.parseDouble(tempSSRS.GetText(1, 1))); // 累计加费
						SumHealthAddFee = SumHealthAddFee
								+ Double.parseDouble(tempSSRS.GetText(1, 1)); // 加费合计
					} else {
						ChangePol[6] = "0.00";
					}
				}
				
				// 险种的居住地加费
			/*	tempSSRS = tempExeSQL
						.execSQL("select nvl(sum(Prem),0) from LCPrem where  PolNo='"
								+ tLCPolSchema_sub2.getPolNo()
								+ "' and PayPlanCode like '000000%'  and payplantype='03'");
	
				if (tempSSRS.MaxCol > 0) {
					if (!(tempSSRS.GetText(1, 1).equals("0") || tempSSRS
							.GetText(1, 1).trim().equals(""))) {
						logger.debug("累计居住地加费是"
								+ tempSSRS.GetText(1, 1));
						ChangePol[7] = mDecimalFormat.format(Double
								.parseDouble(tempSSRS.GetText(1, 1))); // 累计加费
						SumLiveAddFee = SumLiveAddFee
								+ Double.parseDouble(tempSSRS.GetText(1, 1)); // 加费合计
					} else {
						ChangePol[7] = "0.00";
					}
				}
				
				// 险种的爱好加费
				tempSSRS = tempExeSQL
						.execSQL("select nvl(sum(Prem),0) from LCPrem where  PolNo='"
								+ tLCPolSchema_sub2.getPolNo()
								+ "' and PayPlanCode like '000000%'  and payplantype='04'");
	
				if (tempSSRS.MaxCol > 0) {
					if (!(tempSSRS.GetText(1, 1).equals("0") || tempSSRS
							.GetText(1, 1).trim().equals(""))) {
						logger.debug("累计爱好加费是"
								+ tempSSRS.GetText(1, 1));
						ChangePol[8] = mDecimalFormat.format(Double
								.parseDouble(tempSSRS.GetText(1, 1))); // 累计加费
						SumHobbyAddFee = SumHobbyAddFee
								+ Double.parseDouble(tempSSRS.GetText(1, 1)); // 加费合计
					} else {
						ChangePol[8] = "0.00";
					}
				}*/
				//zy 2009-05-18 合并为其他加费 
				SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
				sqlbv9.sql("select (case when sum(Prem) is not null then sum(Prem)  else 0 end) from LCPrem where  PolNo='"
						+ "?PolNo?" + "' and PayPlanCode like '000000%'  and payplantype not in ('01','02')");
				sqlbv9.put("PolNo", tLCPolSchema_sub2.getPolNo());
				tempSSRS = tempExeSQL.execSQL(sqlbv9);
		
				if (tempSSRS.MaxCol > 0) {
					if (!(tempSSRS.GetText(1, 1).equals("0") || tempSSRS
							.GetText(1, 1).trim().equals(""))) {
						logger.debug("其他加费是"
								+ tempSSRS.GetText(1, 1));
						ChangePol[7] = mDecimalFormat.format(Double
								.parseDouble(tempSSRS.GetText(1, 1))); // 累计加费
						SumLiveAddFee = SumLiveAddFee
								+ Double.parseDouble(tempSSRS.GetText(1, 1)); // 加费合计
					} else {
						ChangePol[7] = "0.00";
					}
				}
				
				tChangePolListTable.add(ChangePol); // 加入险种信息
			}
		}
	
		SumPrem = Double.parseDouble(mDecimalFormat.format(SumPrem)); // 转换计算后的保费(规定的精度)		
		SumHealthAddFee = Double.parseDouble(mDecimalFormat.format(SumHealthAddFee)); // 转换计算后的保费(规定的精度)
		SumJobAddFee = Double.parseDouble(mDecimalFormat
				.format(SumJobAddFee));
		SumLiveAddFee = Double.parseDouble(mDecimalFormat
				.format(SumLiveAddFee));
		SumHobbyAddFee = Double.parseDouble(mDecimalFormat
				.format(SumHobbyAddFee));
		

        //其它模版上单独不成块的信息
		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		xmlExport.createDocument("RnewRefuse_MS.vts", "printer"); // 最好紧接着就初始化xml文档
		
//		 生成-年-月-日格式的日期
		StrTool tSrtTool = new StrTool();
		String SysDate = tSrtTool.getYear() + "年" + tSrtTool.getMonth() + "月"
				+ tSrtTool.getDay() + "日";

		// 模版自上而下的元素
		logger.debug("mLOPRTManagerSchema code:"
				+ mLOPRTManagerSchema.getCode());	
		texttag.add("BarCode1", mLOPRTManagerSchema.getPrtSeq());
		texttag
				.add(
						"BarCodeParam1",
						"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");

		//查询合同信息
		LCContSchema tLCContSchema = new LCContSchema();
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(tContNo);
		if(!tLCContDB.getInfo())
		{
			mErrors.copyAllErrors(tLCContDB.mErrors);
			CError.buildErr(this, "在取得保单号"+tContNo+"的LCCont的数据时发生错误");		
			return false;
		}
		tLCContSchema= tLCContDB.getSchema();
		texttag.add("AppntName", tLCContSchema.getAppntName()); // 投保人名称		
		texttag.add("ContNo", tLCContSchema.getContNo()); // 保单号
		texttag.add("SysDate", SysDate);		
		texttag.add("xCvalidate", tLCContSchema.getPaytoDate());	
		String date = tLCContSchema.getCValiDate();
		date = date + "-";
		String ldate = StrTool.decodeStr(date, "-", 1) + "年"
		+ StrTool.decodeStr(date, "-", 2) + "月"
		+ StrTool.decodeStr(date, "-", 3) + "日";
		texttag.add("Date", ldate);
		//查询被保人信息
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		tLCInsuredDB.setInsuredNo(tLCContSchema.getInsuredNo());
		tLCInsuredDB.setContNo(tLCContSchema.getContNo());
		if(!tLCInsuredDB.getInfo())
		{
			mErrors.copyAllErrors(tLCInsuredDB.mErrors);
			CError.buildErr(this, "在取得LCInsured的数据时发生错误");		
			return false;
		}
		LCInsuredSchema tLCInsuredSchema = tLCInsuredDB.getSchema();
		texttag.add("InsuredName", tLCInsuredSchema.getName()); // 被保人名称
		texttag.add("InsuredIDNo", tLCInsuredSchema.getIDNo()); // 被保人号码		
		xmlExport.addDisplayControl("displayold"); // 显示投保险种信息
		xmlExport.addListTable(tRiskListTable, RiskInfoTitle); 
		texttag.add("oldSumPrem", mDecimalFormat.format(oldSumPrem)); // 合计保费
		texttag.add("oldSumJobAddFee", mDecimalFormat.format(oldSumJobAddFee)); // 合计职业加费
		texttag.add("oldSumHealthAddFee", mDecimalFormat.format(oldSumHealthAddFee)); // 合计健康加费
		texttag.add("oldSumLiveAddFee", mDecimalFormat.format(oldSumLiveAddFee));
		texttag.add("oldSumHobbyAddFee", mDecimalFormat.format(oldSumHobbyAddFee));
		
		xmlExport.addDisplayControl("display");
		//处理拒保原因及被拒保险种信息
        //获取被保险人核保信息
		RnewIndUWMasterSchema mRnewIndUWMasterSchema = new RnewIndUWMasterSchema();
		RnewIndUWMasterDB mRnewIndUWMasterDB = new RnewIndUWMasterDB();
		mRnewIndUWMasterDB.setContNo(tContNo);
		mRnewIndUWMasterDB.setInsuredNo(tLCContSchema.getInsuredNo());
		mRnewIndUWMasterDB.setPassFlag("1");
		if(mRnewIndUWMasterDB.query().size()==0)
		{
			mErrors.copyAllErrors(mRnewIndUWMasterDB.mErrors);
			CError.buildErr(this, "在取得RnewIndUWMaster的数据时发生错误");		
			return false;
		}
	    mRnewIndUWMasterSchema=mRnewIndUWMasterDB.query().get(1);
		
		// 核保师代码 核保<--核保师代码
		texttag.add("UWCode", mRnewIndUWMasterSchema.getOperator());
		texttag.add("ReaSon", mRnewIndUWMasterSchema.getUWIdea());
		
		LCPolDB xLCPolDB = new LCPolDB();
		xLCPolDB.setContNo(tContNo);
		xLCPolDB.setAppFlag("9");
		xLCPolDB.setCValiDate(mLOPRTManagerSchema.getStandbyFlag5());
		xLCPolDB.setUWFlag("1");
		if(xLCPolDB.query().size()==0)
		{
			mErrors.copyAllErrors(xLCPolDB.mErrors);
			CError.buildErr(this, "在取得保单号"+tContNo+"下被拒保险种的lcpol数据时发生错误");		
			return false;
		}
		String mRefuseRisk="";
		for(int i=1;i<=xLCPolDB.query().size();i++)
		{
			String tRiskCode = xLCPolDB.query().get(i).getRiskCode();
			LMRiskDB xLMRiskDB = new LMRiskDB();
			xLMRiskDB.setRiskCode(tRiskCode);
			xLMRiskDB.getInfo();
			mRefuseRisk=mRefuseRisk+"  "+xLMRiskDB.getSchema().getRiskName();;
		}
		texttag.add("RefuseRisk", mRefuseRisk);
		if(main_refuse_flag.equals("N"))
		{
		   xmlExport.addDisplayControl("displaychangepol"); // 显示承保计划变更信息
		   xmlExport.addListTable(tChangePolListTable, ChangePolTitle); 
		}
		texttag.add("SumPrem", mDecimalFormat.format(SumPrem)); // 合计保费
		texttag.add("SumJobAddFee", mDecimalFormat.format(SumJobAddFee)); // 合计职业加费
		texttag.add("SumHealthAddFee", mDecimalFormat.format(SumHealthAddFee)); // 合计健康加费
		texttag.add("SumLiveAddFee", mDecimalFormat.format(SumLiveAddFee));
		texttag.add("SumHobbyAddFee", mDecimalFormat.format(SumHobbyAddFee));
		
		//获取代理人信息
		LAAgentDB mLAAgentDB = new LAAgentDB();
		mLAAgentDB.setAgentCode(tLCContSchema.getAgentCode());
		if(!mLAAgentDB.getInfo())
		{
			mErrors.copyAllErrors(mLAAgentDB.mErrors);
			CError.buildErr(this, "在取得LAAgentCode的数据时发生错误");		
			return false;
		}
		LAAgentSchema mLAAgentSchema=mLAAgentDB.getSchema();
		texttag.add("AgentName", mLAAgentSchema.getName()); // 代理人姓名
		texttag.add("AgentCode", tLCContSchema.getAgentCode()); // 代理人业务号
		texttag.add("ManageCom", tLCContSchema.getManageCom()); // 营业机构
		texttag.add("PrtSeq", mLOPRTManagerSchema.getPrtSeq()); // 流水号
		
		String phone = "";
		if(mLAAgentSchema.getPhone()!=null && !mLAAgentSchema.getPhone().equals(""))
		    phone = mLAAgentSchema.getPhone();
		else
			phone = mLAAgentSchema.getMobile();
		texttag.add("Phone", phone);		
		
		xmlExport.addTextTag(texttag);
		mResult.clear();
		mResult.addElement(xmlExport);
		return true;
	}
	
	//辅助函数
	/**
	 * 对打印的文字过长一行显示不完时作调整
	 * 
	 * @param tMainPolNo
	 * @return LCPolSchema
	 */
	private void getContent(ListTable tListTable, String content ,int nMaxCharsInOneLine) {
		int nSpecReasonLen = content.length();
		String strSpecReason = content;
		String[] strArr ;
		while (nSpecReasonLen > nMaxCharsInOneLine) {
			content = strSpecReason.substring(0, nMaxCharsInOneLine);
			strSpecReason = strSpecReason.substring(nMaxCharsInOneLine);
			nSpecReasonLen -= nMaxCharsInOneLine;
			
			strArr = new String[1];
			strArr[0] = content;
			tListTable.add(strArr);
		}

		if (nSpecReasonLen > 0) {
			strArr = new String[1];
			strArr[0] = strSpecReason;
			tListTable.add(strArr);
		}
	}
}
