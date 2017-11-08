/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.finfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.db.LJTempFeeClassDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LDSysVarSchema;
import com.sinosoft.lis.schema.LJAGetOtherSchema;
import com.sinosoft.lis.schema.LJAGetSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LJAGetOtherSet;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;
/**
 * <p>
 * Title: Lis_New
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author Sinosoft
 * @version 1.0
 */

public class NewGrpContFeeWithdrawBL implements BusinessService{
private static Logger logger = Logger.getLogger(NewGrpContFeeWithdrawBL.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap mMMap =new MMap();

	/** 数据操作字符串 */
	private GlobalInput mGI = new GlobalInput();
	private String mOperate;
	private String tLimit = "";
	private String serNo = ""; // 流水号
	private String getNo = ""; // 给付收据号码
	private String prtSeqNo = ""; // 印刷流水号
	private boolean specWithDrawFlag = false; // 特殊的退费标记。该标记为真时，不考虑outpayflag标志
	private TransferData tTransferData = new TransferData();

	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private LJAGetSchema mLJAGetSchema = new LJAGetSchema(); // 实付总表
	private LJAGetSet mLJAGetSet = new LJAGetSet();

	// private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema(); //集体合同表
	private LCGrpContSet mLCGrpContSet = new LCGrpContSet();
	private LCGrpContSchema mNewLCGrpContSchema = new LCGrpContSchema(); // 集体合同表
	private LCGrpContSet mNewLCGrpContSet = new LCGrpContSet();
	private LCGrpPolSchema mNewLCGrpPolSchema = new LCGrpPolSchema(); // 集体合同表
	private LCGrpPolSet mNewLCGrpPolSet = new LCGrpPolSet();

	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema(); // 打印管理表
	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();
	private LJAGetOtherSchema mLJAGetOtherShema = new LJAGetOtherSchema(); // 其它其他退费实付表
	private LJAGetOtherSet mLJAGetOtherSet = new LJAGetOtherSet(); // 其它其他退费实付表

	private String grpContSign = null; // 签单调用标志

	public NewGrpContFeeWithdrawBL() {
	}

	// 传输数据的公共方法
	public boolean submitData(VData tVData) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(tVData))
			return false;
		logger.debug("After getinputdata");

		// 进行业务处理
		if (!dealData())
			return false;
		logger.debug("After dealData！");

		// 准备往后台的数据
		if (!prepareOutputData())
			return false;

		if (grpContSign == null) {
			PubSubmit tPubSubmit =new PubSubmit();
			VData ttVData =new VData();
			ttVData.add(mMMap);
			if(!tPubSubmit.submitData(ttVData)){
				CError.buildErr(this, "数据提交失败！");
				return false;
			}
//			logger.debug("After prepareOutputData");
//
//			logger.debug("Start NewGrpContFeeWithdrawBL BL Submit...");
//
//			NewGrpContFeeWithdrawBLS tNewGrpContFeeWithdrawBLS = new NewGrpContFeeWithdrawBLS();
//			tNewGrpContFeeWithdrawBLS.submitData(mInputData);
//
//			logger.debug("End NewGrpContFeeWithdrawBL BL Submit...");
//
//			// 如果有需要处理的错误，则返回
//			if (tNewGrpContFeeWithdrawBLS.mErrors.needDealError()) {
//				this.mErrors.copyAllErrors(tNewGrpContFeeWithdrawBLS.mErrors);
//			}
//
//			mInputData = null;
		}
		return true;
	}

	public VData getVResult() {
		return this.mInputData;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData tVData) {
		mLCGrpContSet = (LCGrpContSet) tVData.getObjectByObjectName(
				"LCGrpContSet", 0);
		mGI = (GlobalInput) tVData.getObjectByObjectName("GlobalInput", 0);
		tTransferData = (TransferData) tVData.getObjectByObjectName(
				"TransferData", 0);

		if (mLCGrpContSet == null || mGI == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "NewGrpContFeeWithdrawBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有得到足够的数据，请您确认!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (tTransferData != null) {
			String inStr = (String) tTransferData
					.getValueByName("SpecWithDraw");
			if (inStr != null) {
				if (inStr.equals("1")) {
					specWithDrawFlag = true;
				}
			}
			// 团单签单标记
			grpContSign = (String) tTransferData.getValueByName("GrpContSign");

		}

		return true;
	}

	// 根据前面的输入数据，进行逻辑处理
	// 如果在处理过程中出错，则返回false,否则返回true
	private boolean dealData() {
		// 产生流水号
		tLimit = PubFun.getNoLimit(mGI.ManageCom);
		serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);

		LCGrpContSet tLCGrpContSet = new LCGrpContSet();
		LCGrpContDB tLCGrpContDB = null;
		for (int index = 1; index <= mLCGrpContSet.size(); index++) {
			mNewLCGrpContSchema = mLCGrpContSet.get(index);
			// 1-查询投保单号对应的已签单的保单，如果没有则跳过,否则保存查询到的纪录
			// 如果是签单调用，则不用查询
			if (grpContSign == null) {
				tLCGrpContDB = new LCGrpContDB();
				tLCGrpContDB.setGrpContNo(mNewLCGrpContSchema.getGrpContNo());
				tLCGrpContSet = tLCGrpContDB.query();
				if (tLCGrpContSet == null)
					continue;
				if (tLCGrpContSet.size() == 0)
					continue;
				mNewLCGrpContSchema = tLCGrpContSet.get(1);

				//modify by jiaqiangli 既然是没有签单返回为啥不直接判断appflag='0'
				//采用这种否定判断的话，岂不是将满期终止也排除在外了
//				if (mNewLCGrpContSchema.getAppFlag() == null
//						|| !mNewLCGrpContSchema.getAppFlag().equals("1")) // 如果没有签单返回
//					continue;
				
				//not in ('1','4') = not (1 or 4) = not 1 and not 4
				if (mNewLCGrpContSchema.getAppFlag() == null
						|| (!mNewLCGrpContSchema.getAppFlag().equals("1") && !mNewLCGrpContSchema.getAppFlag().equals("4"))) // 如果没有签单返回
					continue;

			}

			if (mNewLCGrpContSchema.getOutPayFlag() == null) // 溢交处理方式：1退费，2充当续期保费
			{
				mNewLCGrpContSchema.setOutPayFlag("1");
			}
			if (mNewLCGrpContSchema.getPayIntv() == 0) // 如果交费方式是趸交，退费
			{
				mNewLCGrpContSchema.setOutPayFlag("1");
			}
			if (specWithDrawFlag) // 如果特殊退费标记为真，那么默认退费
			{
				// mNewLCGrpPolSchema.setOutPayFlag("1");
				if (!(mNewLCGrpContSchema.getDif() > 0))
					continue;
			} else {
				if (!(mNewLCGrpContSchema.getDif() > 0)
						|| !mNewLCGrpContSchema.getOutPayFlag().equals("1")) // 如果没有余额或者溢交处理方式不是退费,返回
					continue;
			}
			// 2-准备实付表纪录,产生实付号码
			tLimit = PubFun.getNoLimit(mNewLCGrpContSchema.getManageCom());
			getNo = PubFun1.CreateMaxNo("GETNO", tLimit);

			// 3-准备打印数据,生成印刷流水号
			// tLimit=PubFun.getNoLimit(mNewLCGrpContSchema.getManageCom());
			prtSeqNo = PubFun1.CreateMaxNo("PRTSEQNO", tLimit);
			mLOPRTManagerSchema.setPrtSeq(prtSeqNo);
			mLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_GET); //
			// mLOPRTManagerSchema.setOtherNo(mNewLCGrpContSchema.getGrpContNo());
			// //实付号码
			logger.debug("集体合同号" + mNewLCGrpContSchema.getGrpContNo());
			logger.debug("集体投保单号"
					+ mNewLCGrpContSchema.getProposalGrpContNo());
//			mLOPRTManagerSchema.setOtherNo(mNewLCGrpContSchema
//					.getProposalGrpContNo());
			//09-12-09打印界面是按照prtno去查 所以这里也改成prtno
			mLOPRTManagerSchema.setOtherNo(mNewLCGrpContSchema.getPrtNo());
			logger.debug("otherno:" + getNo);
			mLOPRTManagerSchema.setMakeDate(CurrentDate);
			mLOPRTManagerSchema.setMakeTime(CurrentTime);
			mLOPRTManagerSchema
					.setManageCom(mNewLCGrpContSchema.getManageCom());
			mLOPRTManagerSchema
					.setAgentCode(mNewLCGrpContSchema.getAgentCode());
			mLOPRTManagerSchema.setCode(PrintManagerBL.CODE_GRP_REFUND);
			mLOPRTManagerSchema.setReqCom(mGI.ManageCom);
			mLOPRTManagerSchema.setReqOperator(mGI.Operator);
			mLOPRTManagerSchema.setStandbyFlag1(mNewLCGrpContSchema
					.getProposalGrpContNo());
			mLOPRTManagerSchema.setPrtType("0");
			mLOPRTManagerSchema.setStateFlag("0");
			PrintManagerBL tPrintManagerBL = new PrintManagerBL();
			VData tempVData = new VData();
			tempVData.add(mLOPRTManagerSchema);
			tempVData.add(mGI);
			if (tPrintManagerBL.submitData(tempVData, "REQ")) // 打印数据处理
			{
				tempVData = tPrintManagerBL.getResult();
				mLOPRTManagerSchema = (LOPRTManagerSchema) tempVData
						.getObjectByObjectName("LOPRTManagerSchema", 0);
				mLOPRTManagerSet.add(mLOPRTManagerSchema);
			} else {
				continue;
			}
			
			String tSQL = "select distinct 1 from lcgrppol where riskcode in (select riskcode from lmriskapp where RiskType = 'H' and HealthType = '1') and grpcontno = '"+mNewLCGrpContSchema.getGrpContNo()+"'";
			ExeSQL tExeSQL = new ExeSQL();
			String tRet = tExeSQL.getOneValue(tSQL);
			String tFinFeeType = null;
			if("1".equals(tRet)){
				tFinFeeType = "CM";
			}else{
				tFinFeeType = "YJTF";
			}
			mLJAGetSchema = new LJAGetSchema();

			// 准备SysVar,解析管理机构信息
			String tSysVar = "ComEFT"
					+ mNewLCGrpContSchema.getManageCom().substring(2, 4);
			// 将有代收但没代付的机构信息描述在LDSysVar表中，如果有，且SysVarValue='1',则说明该机构没有代收业务
			LDSysVarDB tLDSysVarDB = new LDSysVarDB();
			tLDSysVarDB.setSysVar(tSysVar);
			String tPayTypeFlag = "";
			if (!tLDSysVarDB.getInfo()) {
				tPayTypeFlag = "0"; // 该机构有代付业务
			} else {
				LDSysVarSchema tLDSysVarSchema = tLDSysVarDB.getSchema();
				tPayTypeFlag = tLDSysVarSchema.getSysVarValue();
			}
			if (tPayTypeFlag.equals("0")) { // 该机构是否有代付业务
				tLDSysVarDB.setSysVar(tSysVar);
				LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
				tLJTempFeeClassDB.setOtherNo(mNewLCGrpContSchema.getGrpContNo());
				LJTempFeeClassSet tLJTempFeeClassSet = tLJTempFeeClassDB
						.query();
				if (tLJTempFeeClassSet == null
						|| tLJTempFeeClassSet.size() <= 0) {
					CError.buildErr(this, "查询暂收费信息失败！");
					return false;
				}

				// 如果全是银行，才是银行转账，否则都是现金
				int tPayModeFlag = 0;
				for (int i = 1; i <= tLJTempFeeClassSet.size(); i++) {
					if (tLJTempFeeClassSet.get(i).getPayMode().equals("4")) {
						tPayModeFlag++;
					}
				}

				if (tPayModeFlag == tLJTempFeeClassSet.size()) {
					mLJAGetSchema.setPayMode("4"); // 银行代付
					mLJAGetSchema.setBankAccNo(tLJTempFeeClassSet.get(1)
							.getBankAccNo());
					mLJAGetSchema.setBankCode(tLJTempFeeClassSet.get(1)
							.getBankCode());
					mLJAGetSchema.setAccName(tLJTempFeeClassSet.get(1)
							.getAccName());
				} else {
					mLJAGetSchema.setPayMode("1"); // 现金付费
					mLJAGetSchema.setBankAccNo("");
					mLJAGetSchema.setBankCode("");
					mLJAGetSchema.setAccName("");
				}
			} else {
				mLJAGetSchema.setPayMode("1"); // 现金付费
				mLJAGetSchema.setBankAccNo("");
				mLJAGetSchema.setBankCode("");
				mLJAGetSchema.setAccName("");
			}

			// 实付总表
			mLJAGetSchema.setActuGetNo(getNo);
			// mLJAGetSchema.setOtherNo(mNewLCGrpContSchema.getGrpContNo());
			mLJAGetSchema
					.setOtherNo(mNewLCGrpContSchema.getGrpContNo());
			mLJAGetSchema.setOtherNoType("6"); // 其它类型退费(溢交保费退费)--集体单
			/*
			 * Lis5.3 upgrade get
			 * mLJAGetSchema.setAppntNo(mNewLCGrpPolSchema.getGrpNo());
			 */
			// mLJAGetSchema.setPayMode("1");
			mLJAGetSchema.setSumGetMoney(mNewLCGrpContSchema.getDif());
			mLJAGetSchema.setSaleChnl(mNewLCGrpContSchema.getSaleChnl());
			mLJAGetSchema.setSerialNo(serNo);
			mLJAGetSchema.setOperator(mNewLCGrpContSchema.getOperator());
			mLJAGetSchema.setManageCom(mNewLCGrpContSchema.getManageCom());
			mLJAGetSchema.setAgentCode(mNewLCGrpContSchema.getAgentCode());
			mLJAGetSchema.setAgentCom(mNewLCGrpContSchema.getAgentCom());
			mLJAGetSchema.setAgentGroup(mNewLCGrpContSchema.getAgentGroup());
			mLJAGetSchema.setShouldDate(CurrentDate);
			mLJAGetSchema.setStartGetDate(CurrentDate);
			mLJAGetSchema.setMakeDate(CurrentDate);
			mLJAGetSchema.setMakeTime(CurrentTime);
			mLJAGetSchema.setModifyDate(CurrentDate);
			mLJAGetSchema.setModifyTime(CurrentTime);
			//add by tansz on 20110926 for 团险契约币种临时处理方式
			//只支持一张保单一种币种类型的缴费，否则有问题。
			LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
			tLJTempFeeClassDB.setOtherNo(mNewLCGrpContSchema.getGrpContNo());
			LJTempFeeClassSet tLJTempFeeClassSet = tLJTempFeeClassDB.query();
			if (tLJTempFeeClassSet == null
					|| tLJTempFeeClassSet.size() <= 0) {
				CError.buildErr(this, "查询暂收费信息获取币种失败！");
				return false;
			}
			mLJAGetSchema.setCurrency(tLJTempFeeClassSet.get(1).getCurrency());
			mLJAGetSet.add(mLJAGetSchema);
			// 实付子表
			mLJAGetOtherShema = new LJAGetOtherSchema();
			mLJAGetOtherShema.setActuGetNo(getNo);
			// mLJAGetOtherShema.setOtherNo(mNewLCGrpContSchema.getGrpContNo());
			mLJAGetOtherShema.setOtherNo(mNewLCGrpContSchema.getGrpContNo());
			mLJAGetOtherShema.setOtherNoType("1"); // 其它类型退费(溢交保费退费)--集体单
			mLJAGetOtherShema.setPayMode(mLJAGetSchema.getPayMode());
			mLJAGetOtherShema.setGetMoney(mNewLCGrpContSchema.getDif());
			mLJAGetOtherShema.setGetDate(CurrentDate);
			mLJAGetOtherShema.setFeeOperationType("YJTF"); // 溢交退费
			mLJAGetOtherShema.setFeeFinaType(tFinFeeType); // 溢交退费(康福的为CM)
			mLJAGetOtherShema.setManageCom(mNewLCGrpContSchema.getManageCom());
			mLJAGetOtherShema.setAgentCom(mNewLCGrpContSchema.getAgentCom());
			mLJAGetOtherShema.setAgentType(mNewLCGrpContSchema.getAgentType());
			mLJAGetOtherShema.setAPPntName(mNewLCGrpContSchema.getGrpName());
			mLJAGetOtherShema
					.setAgentGroup(mNewLCGrpContSchema.getAgentGroup());
			mLJAGetOtherShema.setAgentCode(mNewLCGrpContSchema.getAgentCode());
			mLJAGetOtherShema.setSerialNo(serNo);
			mLJAGetOtherShema.setOperator(mNewLCGrpContSchema.getOperator());
			;
			mLJAGetOtherShema.setMakeTime(CurrentTime);
			mLJAGetOtherShema.setMakeDate(CurrentDate);
			mLJAGetOtherShema.setModifyDate(CurrentDate);
			mLJAGetOtherShema.setModifyTime(CurrentTime);
			//add by tansz on 20110926 for 团险契约币种临时处理方式
			mLJAGetOtherShema.setCurrency(tLJTempFeeClassSet.get(1).getCurrency());
			mLJAGetOtherSet.add(mLJAGetOtherShema);

			// 4-更新集体保单数据
			mNewLCGrpContSchema.setDif(0);
			mNewLCGrpContSchema.setModifyDate(CurrentDate);
			mNewLCGrpContSchema.setModifyTime(CurrentTime);
			mNewLCGrpContSet.add(mNewLCGrpContSchema);

			if (grpContSign == null) {
				LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
				tLCGrpPolDB.setGrpContNo(mNewLCGrpContSchema.getGrpContNo());
				LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
				tLCGrpPolSet = tLCGrpPolDB.query();
				for (int i = 1; i <= tLCGrpPolSet.size(); i++) {
					mNewLCGrpPolSchema = tLCGrpPolSet.get(i);
					mNewLCGrpPolSchema.setDif(0);
					mNewLCGrpPolSet.add(mNewLCGrpPolSchema);
				}
			}

		}
		return true;
	}

	// 准备往后层输出所需要的数据
	// 输出：如果准备数据时发生错误则返回false,否则返回true
	private boolean prepareOutputData() {
		mMMap = new MMap();
		try {
			mMMap.put(mNewLCGrpPolSet,"UPDATE");
			logger.debug("lcgrpContSet.size:" + mNewLCGrpContSet.size());
			//mInputData.add(mNewLCGrpContSet);
			mMMap.put(mNewLCGrpContSet, "UPDATE");
			//mInputData.add(mLJAGetSet);
			mMMap.put(mLJAGetSet, "INSERT");
//			mInputData.add(mLOPRTManagerSet);
			mMMap.put(mLOPRTManagerSet, "INSERT");
			//mInputData.add(mLJAGetOtherSet);
			mMMap.put(mLJAGetOtherSet, "INSERT");

		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "NewGrpContFeeWithdrawBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		NewGrpContFeeWithdrawBL NewGrpContFeeWithdrawBL1 = new NewGrpContFeeWithdrawBL();
		VData tVData = new VData();
		GlobalInput tGI = new GlobalInput();
		tGI.ManageCom = "86110000";
		tGI.ComCode = "86";
		tGI.Operator = "001";
		LCGrpContSet tLCGrpContSet = new LCGrpContSet();
		LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
		tLCGrpContSchema.setGrpContNo("240110000000067");
		tLCGrpContSet.add(tLCGrpContSchema);
		tVData.add(tLCGrpContSet);
		tVData.add(tGI);
		NewGrpContFeeWithdrawBL1.submitData(tVData);
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

	public VData getResult() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean submitData(VData data, String Operater) {
		// TODO Auto-generated method stub
		return submitData(data);
	}
}
