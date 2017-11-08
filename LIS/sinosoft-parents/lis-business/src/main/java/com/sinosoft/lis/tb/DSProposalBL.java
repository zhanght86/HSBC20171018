/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;

import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.certify.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vbl.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 投保业务逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author 
 * @version 
 */
public class DSProposalBL {
private static Logger logger = Logger.getLogger(DSProposalBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	private VData rResult = new VData(); // add by yaory

	private MMap map = new MMap(); // add by yaory

	/** 数据操作字符串 */
	private String mOperate;

	// 统一更新日期，时间
	private String theCurrentDate = PubFun.getCurrentDate();
	private String theCurrentTime = PubFun.getCurrentTime();
	private String tFamilyType = "";//家庭单标记 1-家庭单 0-非家庭单
	private String tLiveGetMode="";
	private String tBonusGetMode="";
	private String tSequenceNo3="";//被保险人类型
	private String tGetYear="";
	private String tGetLimit="";
	private String tStandbyFlag3="";
	private String tContNo="";
	private String tInputNo="";
	private String tInsuredNo="";

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 合同表 */
	private LBPOPolSet mLBPOPolSet = new LBPOPolSet();
	private LBPOPolSet tLBPOPolSet = new LBPOPolSet();

	/** 保单 */
	private LBPOPolBL mLBPOPolBL = new LBPOPolBL();

	private LBPOBnfSet mLBPOBnfSet = new LBPOBnfSet();
	
	private TransferData tTransferData = new TransferData();

	public DSProposalBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		VData tReturn = null;
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		// 数据操作校验
		if (!checkData()) {
			return false;
		}

		// 进行险种保存业务逻辑处理
		if (!dealData()) {
			// @@错误处理
			return false;
		}

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);

			CError tError = new CError();
			tError.moduleName = "DSProposalBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";

			this.mErrors.addOneError(tError);
			return false;
		}

		// 检查是否出错
		if (this.mErrors.needDealError() == false) {
			tReturn = mInputData;
		}
		mInputData = null;

		return true;
	}

	/**
	 * 数据处理校验 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean checkData() {

		return true;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		String PolSql="select * from lbpopol where contno='"+"?contno?"+"' " +
				"and inputno='"+"?inputno?"+"' order by polno,fillno";
		LBPOPolDB tLBPOPolDB = new LBPOPolDB();
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(PolSql);
		sqlbv.put("contno", mLBPOPolSet.get(1).getContNo());
		sqlbv.put("inputno", mLBPOPolSet.get(1).getInputNo());
//		tLBPOPolDB.setContNo(mLBPOPolSet.get(1).getContNo());
//		tLBPOPolDB.setInputNo(mLBPOPolSet.get(1).getInputNo());
		LBPOPolSet tLBPOPolSet = new LBPOPolSet();
		LBPOPolSet ttLBPOPolSet = new LBPOPolSet();
		tLBPOPolSet = tLBPOPolDB.executeQuery(sqlbv);
		for (int j = 1; j <= mLBPOPolSet.size(); j++) {
			LBPOPolSchema newLBPOPolSchema = new LBPOPolSchema();
			LBPOPolSchema oldLBPOPolSchema = new LBPOPolSchema();
			newLBPOPolSchema = mLBPOPolSet.get(j);
			
			LBPOPolDB ttLBPOPolDB = new LBPOPolDB();
			ttLBPOPolDB.setContNo(newLBPOPolSchema.getContNo());
			ttLBPOPolDB.setFillNo(newLBPOPolSchema.getFillNo());
			ttLBPOPolDB.setInputNo(newLBPOPolSchema.getInputNo());
			ttLBPOPolDB.setPolNo(newLBPOPolSchema.getPolNo());
			if(!ttLBPOPolDB.getInfo()){
				CError.buildErr(this, "查询险种信息出错！");
				return false;
			}
			oldLBPOPolSchema = ttLBPOPolDB.getSchema();
//			oldLBPOPolSchema.setInputNo(newLBPOPolSchema.getInputNo());
			oldLBPOPolSchema.setRiskCode(newLBPOPolSchema.getRiskCode());
			oldLBPOPolSchema.setGrpPolNo(newLBPOPolSchema.getGrpPolNo());
			oldLBPOPolSchema.setContNo(newLBPOPolSchema.getContNo());
//			oldLBPOPolSchema.setInputNo(newLBPOPolSchema.getInputNo());
			oldLBPOPolSchema.setPolNo(newLBPOPolSchema.getPolNo());
			oldLBPOPolSchema.setProposalNo(newLBPOPolSchema.getProposalNo());
			oldLBPOPolSchema.setPrtNo(newLBPOPolSchema.getPrtNo());
			oldLBPOPolSchema.setManageCom(newLBPOPolSchema.getManageCom());
			oldLBPOPolSchema.setInsuredNo(newLBPOPolSchema.getInsuredNo());
			oldLBPOPolSchema.setAppntNo(newLBPOPolSchema.getAppntNo());
			oldLBPOPolSchema.setAmnt(newLBPOPolSchema.getAmnt());
			oldLBPOPolSchema.setRiskAmnt(newLBPOPolSchema.getRiskAmnt());
			oldLBPOPolSchema.setPrem(newLBPOPolSchema.getPrem());
			oldLBPOPolSchema.setStandPrem(newLBPOPolSchema.getStandPrem());
			oldLBPOPolSchema.setSumPrem(newLBPOPolSchema.getSumPrem());
			oldLBPOPolSchema.setMult(newLBPOPolSchema.getMult());
			oldLBPOPolSchema.setStandbyFlag1(newLBPOPolSchema.getStandbyFlag1());
			oldLBPOPolSchema.setInsuYear(newLBPOPolSchema.getInsuYear());
			oldLBPOPolSchema.setPayYears(newLBPOPolSchema.getPayYears());
			oldLBPOPolSchema.setStandbyFlag2(newLBPOPolSchema.getStandbyFlag2());
			oldLBPOPolSchema.setRemark(newLBPOPolSchema.getRemark());
			oldLBPOPolSchema.setRiskSequence(newLBPOPolSchema.getRiskSequence());
//			oldLBPOPolSchema.setFillNo(newLBPOPolSchema.getFillNo());
//			oldLBPOPolSchema.setMainPolNo(newLBPOPolSchema.getMainPolNo());
			logger.debug("InputNo:"+ newLBPOPolSchema.getInputNo());
			oldLBPOPolSchema.setOperator(mGlobalInput.Operator);
//			oldLBPOPolSchema.setMakeDate(tLBPOPolSet.get(1).getMakeDate());
//			oldLBPOPolSchema.setMakeTime(tLBPOPolSet.get(1).getMakeTime());
			oldLBPOPolSchema.setModifyDate(theCurrentDate);
			oldLBPOPolSchema.setModifyTime(theCurrentTime);
			ttLBPOPolSet.add(oldLBPOPolSchema);
		}
		if(tFamilyType.equals("1")){
			String tForInsured="select insuredno from lbpoinsured where contno='"+"?contno?"+"'" +
					           " and inputno='"+"?inputno?"+"' and sequenceno='"+"?sequenceno?"+"'";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tForInsured);
			sqlbv1.put("contno", tContNo);
			sqlbv1.put("inputno", tInputNo);
			sqlbv1.put("sequenceno", tSequenceNo3);
			
			ExeSQL tAgentExeSQL = new ExeSQL();
			SSRS tAgentSSRS = new SSRS();
			tAgentSSRS = tAgentExeSQL.execSQL(sqlbv1);
			tInsuredNo=tAgentSSRS.GetText(1, 1);
			for (int j = 1; j <= ttLBPOPolSet.size(); j++) {
				if(ttLBPOPolSet.get(j).getInsuredNo().equals(tInsuredNo)){
					ttLBPOPolSet.get(j).setLiveGetMode(tLiveGetMode);
				}
			}
		}
		LBPOBnfSet tLBPOBnfSet = new LBPOBnfSet();
		LBPOBnfDB tLBPOBnfDB = new LBPOBnfDB();
		tLBPOBnfDB.setContNo(mLBPOBnfSet.get(1).getContNo());
		tLBPOBnfDB.setInputNo(mLBPOBnfSet.get(1).getInputNo());
		tLBPOBnfSet=tLBPOBnfDB.query();
		for (int i = 1; i <= mLBPOBnfSet.size(); i++) {
//			String tInsuredNo = "";
//			tInsuredNo = mLBPOBnfSet.get(i).getInsuredNo();
//			for(int j=1;j<=tLBPOPolSet.size();j++){
//				if(tInsuredNo.equals(tLBPOPolSet.get(j).getInsuredNo())){
//					mLBPOBnfSet.get(i).setPolNo(tLBPOPolSet.get(1).getPolNo());
//					break;
//				}
//			}
//			if(mLBPOBnfSet.get(i).getPolNo()==null||mLBPOBnfSet.get(i).equals("")){
//				CError tError = new CError();
//				tError.moduleName = "DSProposalBl";
//				tError.functionName = "dealData";
//				tError.errorMessage = "没有查到相应的被保人的险种信息!";
//				this.mErrors.addOneError(tError);
//				return false;
//			}
			mLBPOBnfSet.get(i).setOperator(mGlobalInput.Operator);
			mLBPOBnfSet.get(i).setModifyDate(theCurrentDate);
			mLBPOBnfSet.get(i).setModifyTime(theCurrentTime);
			mLBPOBnfSet.get(i).setMakeTime(tLBPOBnfSet.get(1).getMakeTime());
			mLBPOBnfSet.get(i).setMakeDate(tLBPOBnfSet.get(1).getMakeDate());
		}

		map.put(mLBPOBnfSet, "UPDATE");
		map.put(ttLBPOPolSet, "UPDATE");

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		mInputData = cInputData;
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLBPOBnfSet = ((LBPOBnfSet) cInputData.getObjectByObjectName(
				"LBPOBnfSet", 0));
		this.mLBPOPolSet = (LBPOPolSet) cInputData.getObjectByObjectName(
				"LBPOPolSet", 0);
		tTransferData = (TransferData) cInputData.getObjectByObjectName("TransferData", 0);
		tFamilyType = (String) tTransferData.getValueByName("FamilyType");
		tLiveGetMode = (String) tTransferData.getValueByName("FamilyType");
		tBonusGetMode = (String) tTransferData.getValueByName("FamilyType");
		tSequenceNo3 = (String) tTransferData.getValueByName("FamilyType");
		tGetYear = (String) tTransferData.getValueByName("FamilyType");
		tGetLimit = (String) tTransferData.getValueByName("FamilyType");
		tStandbyFlag3 = (String) tTransferData.getValueByName("FamilyType");
		tContNo = (String) tTransferData.getValueByName("ContNo");
		tInputNo = (String) tTransferData.getValueByName("InputNo");
		logger.debug("tFamilyType:"+tFamilyType);
		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mInputData.clear();
		mInputData.add(map);
		rResult.clear();
		rResult.add(map);
		mResult.clear();
		mResult.add(tLBPOPolSet);
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public VData getCardResult() {
		return this.rResult;
	}

	/**
	 * 准备处理数据
	 * 
	 * @param cInputData
	 * @param cOperate
	 * @return
	 */
	public boolean PrepareSubmitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (getInputData(cInputData) == false) {
			return false;
		}

		// 数据操作校验
		if (checkData() == false) {
			return false;
		}

		// 进行业务处理
		if (dealData() == false) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ProposalBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败DSProposalBL-->dealData!";
			this.mErrors.addOneError(tError);

			return false;
		}

		if (!this.mOperate.equals("DELETE||PROPOSAL")) {
			if (this.mOperate.equals("INSERT||PROPOSAL")) {
			} else {
			}
		}

		// 准备往后台的数据
		if (prepareOutputData() == false) {
			return false;
		}

		return true;
	}

	/**
	 * 和PrepareSubmitData配合使用，返回准备数据
	 * 
	 * @return
	 */
	public VData getSubmitResult() {
		return mInputData;
	}

	public static void main(String[] args) {
	}

}
