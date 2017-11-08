package com.sinosoft.lis.bank;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import com.sinosoft.lis.customer.FICustomer;
import com.sinosoft.lis.customer.FICustomerRN;
import com.sinosoft.lis.db.LDBankDB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LJTempFeeClassDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubLock;
import com.sinosoft.lis.pubfun.ReportPubFun;
import com.sinosoft.lis.schema.LJSPaySchema;
import com.sinosoft.lis.schema.LJTempFeeClassSchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.schema.LYBankLogSchema;
import com.sinosoft.lis.schema.LYSendToBankSchema;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.lis.vschema.LYBankLogSet;
import com.sinosoft.lis.vschema.LYSendToBankSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 业务数据转换到银行系统
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Minim
 * @version 1.0
 */

public class GetSendToBankBL {
private static Logger logger = Logger.getLogger(GetSendToBankBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	// 业务数据
	private String startDate = "";
	private String endDate = "";
	private String bankCode = "";
	private String bankComCode = "";
	private String mOtherno = "";
	private double totalMoney = 0;
	private int sumNum = 0;
	private String serialNo = "";
	private GlobalInput mGlobalInput = new GlobalInput();

	private LJSPaySet outLJSPaySet = new LJSPaySet();
	private LYSendToBankSet outLYSendToBankSet = new LYSendToBankSet();
	private LYBankLogSet outLYBankLogSet = new LYBankLogSet();
	private PubConcurrencyLock mLock = new PubConcurrencyLock();

	public GetSendToBankBL() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括"GETMONEY"和"PAYMONEY"
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData())
			return false;
		logger.debug("---End getInputData---");
		// 增加并发校验
		String key = "SendS" + bankCode;
		PubLock tPubLock = new PubLock();
		if (!tPubLock.lock(key, "准备" + bankCode + "代收发盘数据")) {
			this.mErrors.copyAllErrors(tPubLock.mErrors);
			return false;
		}
		try {
			// 进行业务处理
			if (!dealData())
				return false;
			logger.debug("---End dealData---");

			// 银行代收
			if (mOperate.equals("GETMONEY")) {
				// 准备往后台的数据
				if (!prepareOutputData())
					return false;
				logger.debug("---End prepareOutputData---");

				logger.debug("Start GetSendToBank BLS Submit...");
				GetSendToBankBLS tGetSendToBankBLS = new GetSendToBankBLS();
				if (tGetSendToBankBLS.submitData(mInputData, cOperate) == false) {
					// @@错误处理
					this.mErrors.copyAllErrors(tGetSendToBankBLS.mErrors);
					mResult.clear();
					return false;
				}
				logger.debug("End GetSendToBank BLS Submit...");

				// 如果有需要处理的错误，则返回
				if (tGetSendToBankBLS.mErrors.needDealError()) {
					this.mErrors.copyAllErrors(tGetSendToBankBLS.mErrors);
				}
			}
			// 银行代付
			else if (mOperate.equals("PAYMONEY")) {
			}

			return true;
		} finally {
			mLock.unLock();
			if (!tPubLock.unLock(key)) {
				CError.buildErr(this, "银行" + bankCode + "解锁失败:"
						+ tPubLock.mErrors.getFirstError());
				return false;
			}
		}
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			TransferData tTransferData = (TransferData) mInputData
					.getObjectByObjectName("TransferData", 0);
			startDate = (String) tTransferData.getValueByName("startDate");
			endDate = (String) tTransferData.getValueByName("endDate");
			bankCode = (String) tTransferData.getValueByName("bankCode");
			mOtherno = (String) tTransferData.getValueByName("Otherno");

			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GetSendToBankBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 获取交费日期在设置的日期区间内的总应付表记录
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private LJSPaySet getLJSPayByPaydate(String startDate, String endDate) {
		String tSql = "";
		String tSql2 = "";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		String maxSendToBankCount = "0";

		LDSysVarDB tLDSysVarDB = new LDSysVarDB();
		tLDSysVarDB.setSysVar("MaxSendToBankCount");
		if (tLDSysVarDB.getInfo()) {
			maxSendToBankCount = tLDSysVarDB.getSysVarValue();
		}

		// 不指定起始日期
		if (startDate == null || startDate.equals("")) {
			throw new IllegalArgumentException("不支持的操作");
		} else {
			// 非续期划帐
			String appOtherNoType = "9";  //首期收费类型
			String bqothernotype = "10";
			String claimothernotype = "5";
			tSql = "select * from LJSPay where StartPayDate <= '"
					+ "?date1?"
					+ "' and BankCode = '"
					+ "?BankCode?"
					+ "' and ManageCom like concat('"
					+ "?like?"
					+ "','%') and sumduepaymoney>0 and (BankOnTheWayFlag = '0' or BankOnTheWayFlag is null)"
					+ " and (BankAccNo is not null) "
					+ " and (OtherNoType not in ('?OtherNoType?') or (OtherNoType='"
					+ "?in3?"
					+ "' and getnoticeno not in (select tempfeeno from ljtempfee) and SendBankCount<3"
					+ " and exists(select 1 from lpedorapp where edoracceptno=LJSPay.otherno and othernotype in ('1','3','4')))"
					+ " or (OtherNoType='" + "?in4?" + "' and getnoticeno not in (select tempfeeno from ljtempfee) and SendBankCount<3)"
					+ " or (OtherNoType='" + "?in5?" + "' and not exists (select 1 from LCCont where ContNo=LJSPay.OtherNo and NewAutoSendBankFlag='0')))"
					+ " and SendBankCount <= " + "?in6?"
					+ ReportPubFun.getWherePart("otherno", ReportPubFun.getParameterStr(mOtherno, "?otherno?"));
                ArrayList<String> strArr=new ArrayList<String>();
                strArr.add("2");
                strArr.add(bqothernotype);
                strArr.add(claimothernotype);
                strArr.add(appOtherNoType);
                    sqlbv1.sql(tSql);
                    sqlbv1.put("date1",  PubFun.getCurrentDate());
                    sqlbv1.put("BankCode", bankCode);
                    sqlbv1.put("like", mGlobalInput.ComCode);
                    sqlbv1.put("OtherNoType",strArr );
                    sqlbv1.put("in3", bqothernotype);
                    sqlbv1.put("in4", claimothernotype);
                    sqlbv1.put("in5", appOtherNoType);
                    sqlbv1.put("in6", maxSendToBankCount);
                    sqlbv1.put("otherno", mOtherno);
			// 续期划帐
			// 提取所有续期续保数据，还需要去掉有暂收费的应收记录（比如暂收费一直没有核销，也不应该再次发盘）
			tSql2 = "select * from LJSPay where BankCode = '"
					+ "?a1?"
					+ "' and ManageCom like concat('"
					+ "?a2?"
					+ "','%')  and sumduepaymoney>0 and ((StartPayDate <= '"
					+ "?a3?"
					+ "' and StartPayDate >= '"
					+ "?a4?"
					+ "') or StartPayDate='"+ "?a5?"
					+"') and PayDate >= '"
					+ "?a6?"
					// + " and SendBankCount <= " + maxSendToBankCount
					+ "' and (BankOnTheWayFlag = '0' or BankOnTheWayFlag is null) "
					+ "and (BankAccNo is not null) and OtherNoType='2' "
					+ "and riskcode not in (select riskcode from lmrisk where rnewflag='Y') "
					+ "and getnoticeno not in (select tempfeeno from ljtempfee) "
					+ "and exists (select 1 from lccont where contno=LJSPay.otherno and appflag='1' "
					+ "and not exists(select 1 from lccontstate where contno=LJSPay.otherno and statetype in ('Available','PayPrem') and state='1' and enddate is null)) "
					+ "?a7?"
					+ "union "
					+ "select * from LJSPay where BankCode = '"
					+ "?a8?"
					+ "' and ManageCom like concat('"
					+ "?a9?"
					+ "','%') and sumduepaymoney>0 and ((StartPayDate <= '"
					+ "?endDate?"
					+ "' and StartPayDate >= '"
					+ "a10"
					+ "') or StartPayDate='"+ "?a11?"
					+"') and PayDate >= '"
					+ "a12"
					+ "' and (BankOnTheWayFlag = '0' or BankOnTheWayFlag is null) "
					+ "and (BankAccNo is not null) and OtherNoType='2' "
					+ "and paytypeflag = '1' and riskcode in (select riskcode from lmrisk where rnewflag='Y') " // 可以续保的险种，续保情况下必须经过转帐确认
					+ "and exists(select 1 from lyrenewbankinfo where getnoticeno = ljspay.getnoticeno and contno = ljspay.otherno and state = '0') "
					+ "and getnoticeno not in (select tempfeeno from ljtempfee) "
					+ "and exists (select 1 from lccont where contno=LJSPay.otherno and appflag='1' " 
					+ "and not exists(select 1 from lccontstate where contno=LJSPay.otherno and statetype in ('Available','PayPrem') and state='1' and enddate is null)) "
					+ "?a13?"
					+ "union "
					+ "select * from ljspay where BankCode = '"
					+ "?a15?"
					+ "' and ManageCom like concat('"
					+ "a14"
					+ "','%')  and sumduepaymoney>0 and (BankOnTheWayFlag = '0' or BankOnTheWayFlag is null) "
					+ "and (BankAccNo is not null) and OtherNoType='2' "
					+ "and getnoticeno not in (select tempfeeno from ljtempfee) "
					+ "and exists (select 1 from lccont where contno=LJSPay.otherno and appflag='1' "
					+ "and not exists(select 1 from lccontstate where contno=LJSPay.otherno and statetype in ('Available','PayPrem') and state='1' and enddate is null)) "
					+ "and payform='1'"
					+ ReportPubFun.getWherePart("otherno", mOtherno);
			sqlbv2.sql(tSql2);
	         sqlbv2.put("a1", bankCode);
	         sqlbv2.put("a2", mGlobalInput.ComCode);
	         sqlbv2.put("a3", endDate);
	         sqlbv2.put("a4", startDate);
	         sqlbv2.put("a5", PubFun.getCurrentDate());
	         sqlbv2.put("a6", PubFun.getCurrentDate());
	         sqlbv2.put("a7", ReportPubFun.getWherePart("otherno", mOtherno));
	         sqlbv2.put("a8", endDate);
	         sqlbv2.put("a9", mGlobalInput.ComCode);  
	         sqlbv2.put("a10", startDate);
	         sqlbv2.put("a11", PubFun.getCurrentDate());
	         sqlbv2.put("a12", PubFun.getCurrentDate());
	         sqlbv2.put("a13", ReportPubFun.getWherePart("otherno", mOtherno));
	         sqlbv2.put("a14", mGlobalInput.ComCode);
	         sqlbv2.put("a15", bankCode);
		}

		logger.debug(tSql);
		logger.debug(tSql2);

		LJSPayDB tLJSPayDB = new LJSPayDB();
		LJSPaySet tLJSPaySet = tLJSPayDB.executeQuery(sqlbv1);
		tLJSPaySet.add(tLJSPayDB.executeQuery(sqlbv2));

		if (tLJSPayDB.mErrors.getErrorCount() > 0)
		{
			return null;
		}
		
		//tongmeng 2011-01-24 modify
		//新契约收费不产生应收,直接从暂收中获取
		LJSPaySet tProposal_LJSPaySet = new LJSPaySet(); 
		tProposal_LJSPaySet = getLjspayForProposal(startDate, endDate);
		
		if(tProposal_LJSPaySet!=null&&tProposal_LJSPaySet.size()>0)
		{
			tLJSPaySet.add(tProposal_LJSPaySet);
		}
			
		return tLJSPaySet;
	}

	/**
	 * @param startDate
	 * @param endDate
	 * @param tProposal_LJSPaySet
	 */
	private LJSPaySet getLjspayForProposal(String startDate, String endDate) {
		
		LJSPaySet tLJSPaySet = new LJSPaySet();
		LJTempFeeClassSet xLJTempFeeClassSet = new LJTempFeeClassSet();
		xLJTempFeeClassSet = QueryLJTempfeeClass(startDate, endDate, mGlobalInput.ComCode,
				bankCode);
		if (xLJTempFeeClassSet == null)
			return null;

		LJTempFeeClassSchema xLJTempFeeClassSchema = new LJTempFeeClassSchema();
		for (int n = 1; n <= xLJTempFeeClassSet.size(); n++) {
			xLJTempFeeClassSchema = xLJTempFeeClassSet.get(n); // 保存后用
			// 1-找到对应的主表
			LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
			tLJTempFeeDB.setTempFeeNo(xLJTempFeeClassSchema.getTempFeeNo());
			LJTempFeeSet tempLJTempFeeSet = tLJTempFeeDB.query();
			if (tempLJTempFeeSet == null || tempLJTempFeeSet.size() == 0) {
				mErrors.addOneError("没有找到暂交费号"
						+ xLJTempFeeClassSchema.getTempFeeNo() + "对应的暂交费表纪录!");
				return null;
			}

			LJTempFeeSchema tempLJTempFeeSchema = tempLJTempFeeSet.get(1);// 默认是1
			LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
			for (int m = 1; m <= tempLJTempFeeSet.size(); m++) {
				tLMRiskAppDB.setRiskCode(tempLJTempFeeSet.get(m).getRiskCode());
				if (tLMRiskAppDB.getInfo() == false) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "NewIndiDueFeeMultiBL";
					tError.functionName = "dealPrtNo";
					tError.errorMessage = "险种描述取出失败!";
					this.mErrors.addOneError(tError);
					return null;
					//return false;
				}
				// 是主险的情况
				if (StrTool.cTrim(tLMRiskAppDB.getSubRiskFlag()).equals("M")) {
					tempLJTempFeeSchema = tempLJTempFeeSet.get(m);// 保存后用
					break;
				}

			}

			

			double sumPay = xLJTempFeeClassSchema.getPayMoney();

			LJSPaySchema tLJSPaySchema = new LJSPaySchema();

			FDate fDate = new FDate();
			Date paytoDate = new Date(); // 交费日期
			paytoDate = fDate.getDate(tempLJTempFeeSchema.getMakeDate());
			String strPayDate = fDate.getString(PubFun.calDate(paytoDate, 2,
					"M", null));

			// 如果有多条暂交费纪录，其暂交费号是相同的
			tLJSPaySchema.setGetNoticeNo(tempLJTempFeeSchema.getTempFeeNo());
			tLJSPaySchema.setOtherNo(tempLJTempFeeSchema.getOtherNo());
			tLJSPaySchema.setOtherNoType("9");
			tLJSPaySchema.setPayDate(strPayDate);
			tLJSPaySchema.setStartPayDate(tempLJTempFeeSchema.getMakeDate());
			tLJSPaySchema.setBankOnTheWayFlag("0");
			tLJSPaySchema.setBankSuccFlag("0");
			tLJSPaySchema.setSendBankCount(0);// 送银行次数
			tLJSPaySchema.setSumDuePayMoney(sumPay);
			tLJSPaySchema.setRiskCode(tempLJTempFeeSchema.getRiskCode());
			tLJSPaySchema.setBankAccNo(xLJTempFeeClassSchema.getBankAccNo());// 从暂交费子表中取
			tLJSPaySchema.setBankCode(xLJTempFeeClassSchema.getBankCode());
			tLJSPaySchema.setAccName(xLJTempFeeClassSchema.getAccName());
			tLJSPaySchema.setManageCom(tempLJTempFeeSchema.getManageCom());
			tLJSPaySchema.setAgentCode(tempLJTempFeeSchema.getAgentCode());
			tLJSPaySchema.setAgentGroup(tempLJTempFeeSchema.getAgentGroup());
			//tLJSPaySchema.setSerialNo(serNo); // 流水号
//			tLJSPaySchema.setOperator(Operator);
//			tLJSPaySchema.setMakeDate(CurrentDate);
//			tLJSPaySchema.setMakeTime(CurrentTime);
//			tLJSPaySchema.setModifyDate(CurrentDate);
//			tLJSPaySchema.setModifyTime(CurrentTime);
			tLJSPaySchema.setIDNo(xLJTempFeeClassSchema.getIDNo());
			tLJSPaySchema.setIDType(xLJTempFeeClassSchema.getIDType());
			
			tLJSPaySet.add(tLJSPaySchema);
		}
		
		return tLJSPaySet;
	}

	/**
	 * 生成送银行表数据
	 * 
	 * @param tLJSPaySet
	 * @return
	 */
	private LYSendToBankSet getSendToBank(LJSPaySet tLJSPaySet) {
		double dTotalMoney = 0;
		serialNo = PubFun1.CreateMaxNo("1", 20);
		LYSendToBankSet tLYSendToBankSet = new LYSendToBankSet();

		for (int i = 0; i < tLJSPaySet.size(); i++) {
			LJSPaySchema tLJSPaySchema = tLJSPaySet.get(i + 1);

			// 获取银行账号信息，账户信息不一定存在于账户表中了
			// LCBankAccSchema tLCBankAccSchema = getBankAcc(tLJSPaySchema);
			// if (tLCBankAccSchema == null) return null;

			// 生成送银行表数据
			LYSendToBankSchema tLYSendToBankSchema = new LYSendToBankSchema();
			tLYSendToBankSchema.setSerialNo(serialNo);
			tLYSendToBankSchema.setDealType("S");
			tLYSendToBankSchema.setPayCode(tLJSPaySchema.getGetNoticeNo());
			tLYSendToBankSchema.setBankCode(bankCode);
			// tLYSendToBankSchema.setAccType(tLCBankAccSchema.getAccType());
			tLYSendToBankSchema.setAccName(tLJSPaySchema.getAccName());
			tLYSendToBankSchema.setAccNo(tLJSPaySchema.getBankAccNo());

			// 因为改为前台录入财务数据，保单表中不一定有数据，所以不再从中取信息
			// if (tLJSPaySchema.getOtherNoType().equals("9")) {
			// LCPolDB tLCPolDB = new LCPolDB();
			// tLCPolDB.setPrtNo(tLJSPaySchema.getOtherNo());
			// tLCPolDB.setRiskCode(tLJSPaySchema.getRiskCode());
			// LCPolSchema tLCPolSchema = tLCPolDB.query().get(1);
			//
			// tLYSendToBankSchema.setPolNo(tLCPolSchema.getPolNo());
			// }
			// else {
			// tLYSendToBankSchema.setPolNo(tLJSPaySchema.getOtherNo());
			// }

			tLYSendToBankSchema.setPolNo(tLJSPaySchema.getOtherNo());
			tLYSendToBankSchema.setNoType(tLJSPaySchema.getOtherNoType());
			tLYSendToBankSchema.setComCode(tLJSPaySchema.getManageCom());
			tLYSendToBankSchema.setAgentCode(tLJSPaySchema.getAgentCode());
			//客户账户处理
			//如果有余额,先扣除余额再收费.
			double payMoney = tLJSPaySchema.getSumDuePayMoney();
			double dif = 0;
			FICustomer tFICustomer = new FICustomerRN();
			dif = tFICustomer.queryAccount(tLJSPaySchema.getOtherNo(), "2",tLJSPaySchema.getCurrency());
			// 若账户余额大于或等于应收
			if (dif >= payMoney)
			{
				continue;
			}
			// 若账户余额小于应收
			else
			{
				payMoney = PubFun.round((payMoney-dif), 2);
			}
			
			
			
			//tLYSendToBankSchema.setPayMoney(tLJSPaySchema.getSumDuePayMoney());
			tLYSendToBankSchema.setPayMoney(payMoney);
			tLYSendToBankSchema.setSendDate(PubFun.getCurrentDate());
			tLYSendToBankSchema.setDoType("1");
			// 因为没有为发送银行盘表设计操作员字段，所以暂时保存在备注字段中，add by Minim at 2004-2-5
			tLYSendToBankSchema.setRemark(mGlobalInput.Operator);
			tLYSendToBankSchema.setModifyDate(PubFun.getCurrentDate());
			tLYSendToBankSchema.setModifyTime(PubFun.getCurrentTime());
			tLYSendToBankSchema.setRiskCode(tLJSPaySchema.getRiskCode());
			tLYSendToBankSchema.setIDNo(tLJSPaySchema.getIDNo());
			tLYSendToBankSchema.setIDType(tLJSPaySchema.getIDType());
//			if ("2".equals(tLJSPaySchema.getOtherNoType())) {// 续期
//				String sql = "select idtype,idno from lcappntind where polno='"
//						+ tLJSPaySchema.getOtherNo() + "'";
//				SSRS tSSRS = new ExeSQL().execSQL(sql);
//				if (tSSRS.MaxRow > 0) {
//					tLYSendToBankSchema.setIDType(tSSRS.GetText(1, 1));
//					tLYSendToBankSchema.setIDNo(tSSRS.GetText(1, 2));
//				} else {
//					sql = "select idtype,idno from lbappntind where polno='"
//							+ tLJSPaySchema.getOtherNo() + "'";
//					tSSRS = new ExeSQL().execSQL(sql);
//					if (tSSRS.MaxRow > 0) {
//						tLYSendToBankSchema.setIDType(tSSRS.GetText(1, 1));
//						tLYSendToBankSchema.setIDNo(tSSRS.GetText(1, 2));
//					}
//				}
//			} else if ("10".equals(tLJSPaySchema.getOtherNoType())) {// 保全
//				String sql = "select idtype,idno from lcappntind where polno in(select polno from lpedormain where edorno='"
//						+ tLJSPaySchema.getOtherNo() + "')";
//				SSRS tSSRS = new ExeSQL().execSQL(sql);
//				if (tSSRS.MaxRow > 0) {
//					tLYSendToBankSchema.setIDType(tSSRS.GetText(1, 1));
//					tLYSendToBankSchema.setIDNo(tSSRS.GetText(1, 2));
//				} else {
//					sql = "select idtype,idno from lbappntind where polno in(select polno from lpedormain where edorno='"
//							+ tLJSPaySchema.getOtherNo() + "')";
//					tSSRS = new ExeSQL().execSQL(sql);
//					if (tSSRS.MaxRow > 0) {
//						tLYSendToBankSchema.setIDType(tSSRS.GetText(1, 1));
//						tLYSendToBankSchema.setIDNo(tSSRS.GetText(1, 2));
//					}
//				}
//			} else {// 首期
//				String sql = "select '0',idno from ljtempfeeclass where tempfeeno='"
//						+ tLJSPaySchema.getGetNoticeNo() + "'";
//				SSRS tSSRS = new ExeSQL().execSQL(sql);
//				if (tSSRS.MaxRow > 0) {
//					tLYSendToBankSchema.setIDType(tSSRS.GetText(1, 1));
//					tLYSendToBankSchema.setIDNo(tSSRS.GetText(1, 2));
//				}
//			}
			tLYSendToBankSet.add(tLYSendToBankSchema);

			// 累加总金额和总数量
			//dTotalMoney = dTotalMoney + tLJSPaySchema.getSumDuePayMoney();
			dTotalMoney = dTotalMoney + payMoney;
			
			// 转换精度
			dTotalMoney = Double.parseDouble((new DecimalFormat("0.00"))
					.format(dTotalMoney));
			sumNum = sumNum + 1;
		}
		totalMoney = Double.parseDouble((new DecimalFormat("0.00"))
				.format(dTotalMoney));

		return tLYSendToBankSet;
	}

	/**
	 * 修改应收表银行在途标志
	 * 
	 * @param tLJSPaySet
	 * @return
	 */
	private LJSPaySet modifyBankFlag(LJSPaySet tLJSPaySet) {
		int i;

		for (i = 0; i < tLJSPaySet.size(); i++) {
			LJSPaySchema tLJSPaySchema = tLJSPaySet.get(i + 1);

			tLJSPaySchema.setBankOnTheWayFlag("1");
			// 记录发送银行次数
			tLJSPaySchema
					.setSendBankCount(tLJSPaySchema.getSendBankCount() + 1);
			tLJSPaySet.set(i + 1, tLJSPaySchema);
		}

		return tLJSPaySet;
	}

	/**
	 * 生成银行日志表数据
	 * 
	 * @return
	 */
	private LYBankLogSchema getBankLog() {
		LYBankLogSchema tLYBankLogSchema = new LYBankLogSchema();

		tLYBankLogSchema.setSerialNo(serialNo);
		tLYBankLogSchema.setBankCode(bankCode);
		tLYBankLogSchema.setLogType("S");
		tLYBankLogSchema.setStartDate(PubFun.getCurrentDate());
		tLYBankLogSchema.setMakeDate(PubFun.getCurrentDate());
		tLYBankLogSchema.setTotalMoney(totalMoney);
		tLYBankLogSchema.setTotalNum(sumNum);
		tLYBankLogSchema.setModifyDate(PubFun.getCurrentDate());
		tLYBankLogSchema.setModifyTime(PubFun.getCurrentTime());
		tLYBankLogSchema.setComCode(bankComCode);

		return tLYBankLogSchema;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		try {
			// 银行代收（应收总表LJSPay）
			if (mOperate.equals("GETMONEY")) {
				bankComCode=getBankComCode();				

				// 总应收表处理（获取交费日期在设置的日期区间内的记录；获取银行在途标志为N的记录）
				LJSPaySet tLJSPaySet = getLJSPayByPaydate(startDate, endDate);
				if (tLJSPaySet == null)
					throw new NullPointerException("总应收表处理失败！");
				if (tLJSPaySet.size() == 0)
					throw new NullPointerException("总应收表无数据！");

				//校验建行不能超过10000条
				String sql = "select 1 from ldcode1 where codetype ='YBTBatBank' and code='03' and othersign='1' and code1='"+"?code1?"+"'";
				SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
				sqlbv2.sql(sql);
				sqlbv2.put("code1", bankCode);
				SSRS tSSRS = new ExeSQL().execSQL(sqlbv2);
				if (tSSRS.MaxRow > 0 && tLJSPaySet.size()>10000) {
					throw new NullPointerException("数据不能超过10000条！");
				}
				
				logger.debug("---End getLJSPayByPaydate---");
				if (!lockpol(tLJSPaySet))
					return false;

				// 校验银行授权
				// tLJSPaySet = verifyBankAuth(tLJSPaySet);
				// if (tLJSPaySet == null) throw new
				// NullPointerException("校验银行授权处理失败！无数据！");
				// logger.debug("---End verifyBankAuth---");

				// 生成送银行表数据
				LYSendToBankSet tLYSendToBankSet = getSendToBank(tLJSPaySet);
				if (tLYSendToBankSet == null)
					throw new Exception("生成送银行表数据失败！");

				logger.debug("---End getSendToBank---");

				// 修改应收表银行在途标志
				tLJSPaySet = modifyBankFlag(tLJSPaySet);
				logger.debug("---End modifyBankFlag---");

				// 生成银行日志表数据
				LYBankLogSchema tLYBankLogSchema = getBankLog();
				logger.debug("---End getBankLog---");

				
				//tongmeng 2011-01-24 add
				//新契约直接从暂收获取数据,不处理应收
				for(int i=1;i<=tLJSPaySet.size();i++)
				{
					if(tLJSPaySet.get(i).getOtherNoType()!=null&&
							!tLJSPaySet.get(i).getOtherNoType().equals("9")		
					)
					{
						outLJSPaySet.add(tLJSPaySet.get(i));
					}
				}
				
				
				//outLJSPaySet.set(tLJSPaySet);
				outLYSendToBankSet.set(tLYSendToBankSet);
				outLYBankLogSet.add(tLYBankLogSchema);
			}

			// 银行代付
			else if (mOperate.equals("PAYMONEY")) {

			}
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GetSendToBankBL";
			tError.functionName = "dealData";
			tError.errorMessage = "数据处理错误! " + e.getMessage();
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	private String getBankComCode() {
		LDBankDB tLDBankDB=new LDBankDB();
		tLDBankDB.setBankCode(bankCode);
		if(!tLDBankDB.getInfo()){
			throw new IllegalArgumentException("BankCode error");
		}
		String comcode=tLDBankDB.getComCode();
		if(comcode ==null || "".equals(comcode)){
			throw new IllegalArgumentException("BankComCode error");
		}
		return comcode;
	}

	private boolean lockpol(LJSPaySet paySet) {
		String[] ljspols = new String[paySet.size() + paySet.size()];
		for (int i = 0; i < paySet.size(); i++) {
			ljspols[i * 2] = paySet.get(i + 1).getGetNoticeNo();
			ljspols[i * 2 + 1] = paySet.get(i + 1).getOtherNo();
		}

		if (!mLock.lock(ljspols, "LB0001", mGlobalInput.Operator)) {
			this.mErrors.copyAllErrors(mLock.mErrors);
			return false;
		}
		return true;
	}

	/**
	 * 准备往后层输出所需要的数据
	 * 
	 * @return 如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mInputData = new VData();
		try {
			mInputData.add(outLJSPaySet);
			mInputData.add(outLYSendToBankSet);
			mInputData.add(outLYBankLogSet);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GetSendToBankBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mResult;
	}
	
	private LJTempFeeClassSet QueryLJTempfeeClass(String StartDate,
			String EndDate, String tManageCom, String tBankCode) {
		String strSQL="";
		
		strSQL="select * from LJTempFeeClass "
	          +" where (MakeDate>='"+"?MakeDate?"+"' and MakeDate<='"+"?MakeDate1?"+"') "
	          +" and PayMode='4'"
	          +" and Enteraccdate is  null "
	          +" and ManageCom like concat('"+"?MakeDate2?"+"','%')"
	          +" and exists(select 1 from ljtempfee where tempfeeno=ljtempfeeclass.tempfeeno "
	          +" and not exists(select 1 from lcpol where prtno=ljtempfee.otherno and uwflag='a')) "
	          +" and not exists(select 1 from lysendtobank where paycode=tempfeeno)";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		
		sqlbv3.put("MakeDate", StartDate);
		sqlbv3.put("MakeDate1", EndDate);
		sqlbv3.put("MakeDate2", tManageCom);
		if (tBankCode != null && !tBankCode.equals("")) {
			strSQL = strSQL + " and BankCode='" + "?BankCode?" + "'";
			sqlbv3.put("BankCode", tBankCode);
		}
		sqlbv3.sql(strSQL);
		logger.debug(strSQL);
		LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
		LJTempFeeClassSet tLJTempFeeClassSet = new LJTempFeeClassSet();
		tLJTempFeeClassSet = tLJTempFeeClassDB.executeQuery(sqlbv3);
		if (tLJTempFeeClassDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLJTempFeeClassDB.mErrors);
			CError.buildErr(this, "数据库查询失败");
			tLJTempFeeClassSet.clear();
			return null;
		}
		if (tLJTempFeeClassSet.size() == 0) {
			return null;
		}
		return tLJTempFeeClassSet;
	}
	

	public static void main(String[] args) {
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.ComCode = "8611";
		tGlobalInput.Operator = "TEST";

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("startDate", "2005-06-20");
		tTransferData.setNameAndValue("endDate", "2005-06-30");
		tTransferData.setNameAndValue("bankCode", "0101");
		tTransferData.setNameAndValue("typeFlag", "ALLXQ");

		VData tVData = new VData();
		tVData.add(tGlobalInput);
		tVData.add(tTransferData);

		GetSendToBankBL getSendToBankBL1 = new GetSendToBankBL();
		logger.debug(getSendToBankBL1.submitData(tVData, "GETMONEY"));
	}
}
