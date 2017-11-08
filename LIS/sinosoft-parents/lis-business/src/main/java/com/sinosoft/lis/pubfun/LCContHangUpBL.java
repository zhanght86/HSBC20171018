package com.sinosoft.lis.pubfun;
import org.apache.log4j.Logger;

import com.sinosoft.lis.claim.LLClaimPubFunBL;
import com.sinosoft.lis.db.LCContHangUpStateDB;
import com.sinosoft.lis.schema.LCContHangUpStateSchema;
import com.sinosoft.lis.vschema.LCContHangUpStateSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 保单挂起服务类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author 续涛，2006-01-20
 * @version 1.0
 */

public class LCContHangUpBL {
private static Logger logger = Logger.getLogger(LCContHangUpBL.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private MMap mMMap = new MMap();

	private GlobalInput mGlobalInput = new GlobalInput();
	/** 全局数据 */
	private LLClaimPubFunBL mLLClaimPubFunBL = new LLClaimPubFunBL();

	private String mHunpUpNo = ""; // 申请号,保全受理号，赔案号，续期合同号
	private String mHunpUpType = ""; // 查询模块号:1新契约,2保全,3续期,4理赔,,5渠道
	private String mContNo = ""; // 合同号
	private String mPolNo = ""; // 险种号
	private String mInsuredNo = ""; // 被保人号
	private String mQryFlag = ""; // 查询模块号:1新契约,2保全,3续期,4理赔,,5渠道
	private String mPosFlag = ""; // 保全挂起标志:0未挂,1挂起
	private String mClmFlag = ""; // 理赔挂起标志:0未挂,1挂起
	private String mRnFlag = ""; // 续期挂起标志:0未挂,1挂起

	private String mStandFlag1 = "";
	private String mStandFlag2 = "";
	private String mStandFlag3 = "";

	public LCContHangUpBL(GlobalInput pGlobalInput) {
		mGlobalInput = pGlobalInput;
		setInitFlag();
	}

	/**
	 * 
	 * @param pGlobalInput:
	 * @param pAppNo:申请号,保全受理号，赔案号，续期保单号
	 * @param mHunpUpType:申请模块号:1：新契约,2保全,3理赔,4续期,5渠道
	 * @param pContNo:合同号
	 */
	public LCContHangUpBL(GlobalInput pGlobalInput, String pHunpUpNo,
			String pHunpUpType, String pContNo) {
		mGlobalInput = pGlobalInput;
		mHunpUpNo = StrTool.cTrim(pHunpUpNo);
		mHunpUpType = StrTool.cTrim(pHunpUpType);
		mContNo = StrTool.cTrim(pContNo);
		setInitFlag();
	}

	/**
	 * 申请号,保全受理号，赔案号，续期保单号
	 * 
	 * @return
	 */
	public String getHunpUpNo() {
		return mHunpUpNo;
	}

	public void setHunpUpNo(String pHunpUpNo) {
		mHunpUpNo = StrTool.cTrim(pHunpUpNo);
	}

	/**
	 * 申请模块号:1新契约,2保全,3续期,4理赔,5渠道
	 * 
	 * @return
	 */
	public String getHunpUpType() {
		return mHunpUpType;
	}

	public void setHunpUpType(String pHunpUpType) {
		mHunpUpType = StrTool.cTrim(pHunpUpType);
	}

	/**
	 * 合同号
	 * 
	 * @return
	 */
	public String getContNo() {
		return mContNo;
	}

	public void setContNo(String pContNo) {
		mContNo = StrTool.cTrim(pContNo);
	}

	/**
	 * 险种号
	 * 
	 * @return
	 */
	public String getPolNo() {
		return mPolNo;
	}

	public void setPolNo(String pPolNo) {
		mPolNo = StrTool.cTrim(pPolNo);
	}

	/**
	 * 被保人号
	 * 
	 * @return
	 */
	public String getInsuredNo() {
		return mInsuredNo;
	}

	public void setInsuredNo(String pInsuredNo) {
		mInsuredNo = StrTool.cTrim(pInsuredNo);
	}

	/**
	 * 查询模块号:1新契约,2保全,3续期,4理赔,5渠道
	 * 
	 * @return
	 */
	public String getQryFlag() {
		return mQryFlag;
	}

	public void setQryFlag(String pQryFlag) {
		mQryFlag = StrTool.cTrim(pQryFlag);
	}

	/**
	 * 保全挂起标志:0未挂,1挂起
	 * 
	 * @return
	 */
	public String getPosFlag() {
		return mPosFlag;
	}

	public void setPosFlag(String pPosFlag) {
		mPosFlag = StrTool.cTrim(pPosFlag);
	}

	/**
	 * 理赔挂起标志:0未挂,1挂起
	 * 
	 * @return
	 */
	public String getClmFlag() {
		return mClmFlag;
	}

	public void setClmFlag(String pClmFlag) {
		mClmFlag = StrTool.cTrim(pClmFlag);
	}

	/**
	 * 续期挂起标志:0未挂,1挂起
	 * 
	 * @return
	 */
	public String getRnFlag() {
		return mRnFlag;
	}

	public void setRnFlag(String pRnFlag) {
		pRnFlag = StrTool.cTrim(pRnFlag);
		if (pRnFlag.length() == 0) {
			mRnFlag = "0";
		} else {
			mRnFlag = StrTool.cTrim(pRnFlag);
		}
	}

	public String getStandFlag1() {
		return mStandFlag1;
	}

	public void setStandFlag1(String pStandFlag) {
		mStandFlag1 = StrTool.cTrim(pStandFlag);
	}

	public String getStandFlag2() {
		return mStandFlag2;
	}

	public void setStandFlag2(String pStandFlag) {
		mStandFlag2 = StrTool.cTrim(pStandFlag);
	}

	public String getStandFlag3() {
		return mStandFlag3;
	}

	public void setStandFlag3(String pStandFlag) {
		mStandFlag3 = StrTool.cTrim(pStandFlag);
	}

	/**
	 * 设置空值的状态
	 * 
	 */
	private void setInitFlag() {
		if (mQryFlag.length() == 0) {
			mQryFlag = mHunpUpType;
		}

		if (mPosFlag.length() == 0) {
			mPosFlag = "0";
		}

		if (mClmFlag.length() == 0) {
			mClmFlag = "0";
		}
		if (mRnFlag.length() == 0) {
			mRnFlag = "0";
		}
		if (mPolNo.length() == 0) {
			mPolNo = "000000";
		}
		if (mInsuredNo.length() == 0) {
			mInsuredNo = "000000";
		}

	}

	/**
	 * 查询合同是否被挂起
	 * 
	 * @return
	 */
	public boolean queryHungUpForContNo() {
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0
		 * 如果没有挂起的查询标志，则默认申请模块是否被挂起 如果 有挂起的查询标志，则判断查询模块是否被挂起
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 设置查询条件
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LCContHangUpStateDB tLCContHangUpStateDB = new LCContHangUpStateDB();
		tLCContHangUpStateDB.setContNo(mContNo);
		if (mQryFlag.equals("2")) {
			tLCContHangUpStateDB.setPosFlag("1");
		}
		if (mQryFlag.equals("3")) {
			tLCContHangUpStateDB.setRNFlag("1");
		}
		if (mQryFlag.equals("4")) {
			tLCContHangUpStateDB.setClaimFlag("1");
		}

		LCContHangUpStateSet tLCContHangUpStateSet = tLCContHangUpStateDB
				.query();

		if (tLCContHangUpStateDB.mErrors.needDealError()) {
			mErrors.addOneError("合同号[" + mContNo + "]的查询出现问题!!!");
			return false;
		}
		
	
		
		/**
		 * 2008-12-10
		 * zhangzheng
		 * 如果是理赔赔案挂起的合同,则形同没有被挂起,避开在报案阶段挂起的保单在后面的阶段被限制无法执行下去的限制;
		 */
		
		if (tLCContHangUpStateSet.size() > 0) {
			
			boolean flag=false;
			
			for (int i = 1; i <= tLCContHangUpStateSet.size(); i++) {
				
				//如果是本次赔案挂起,则形同没有被挂起,不能影响本次赔案操作
				if(tLCContHangUpStateSet.get(i).getHangUpType().equals("4")&&tLCContHangUpStateSet.get(i).getHangUpNo().equals(mHunpUpNo)){
					
					flag=true;
				}
				else{
					flag=false;
				}
			}
			
			//如果不是本次赔案挂起,则返回false，并给出提示信息
			if(flag==false){
				
				String tMsg = "";
				for (int i = 1; i <= tLCContHangUpStateSet.size(); i++) {					

					if (tLCContHangUpStateSet.get(i).getHangUpType().equals("2")) {
						tMsg = tMsg + "[保全-" + tLCContHangUpStateSet.get(i).getHangUpNo() + "]";
					}

					if (tLCContHangUpStateSet.get(i).getHangUpType().equals("3")) {
						tMsg = tMsg + "[续期-" + tLCContHangUpStateSet.get(i).getHangUpNo() + "]";
					}

					if (tLCContHangUpStateSet.get(i).getHangUpType().equals("4")) {
						tMsg = tMsg + "[理赔-" + tLCContHangUpStateSet.get(i).getHangUpNo() + "]";
					}

				}
				mErrors.addOneError("合同号:[" + mContNo + "]，被" + tMsg + "挂起!!!");
				return false;
			}
		}

		return true;
	}

	/**
	 * 插入保单挂起记录
	 * 
	 * @return
	 */
	public boolean insertHungUp() {
		LCContHangUpStateSchema tLCContHangUpStateSchema = new LCContHangUpStateSchema();
		tLCContHangUpStateSchema.setContNo(mContNo);
		tLCContHangUpStateSchema.setInsuredNo(mPolNo);
		tLCContHangUpStateSchema.setPolNo(mInsuredNo);
		tLCContHangUpStateSchema.setHangUpType(mHunpUpType); // 存储申请模块号
		tLCContHangUpStateSchema.setHangUpNo(mHunpUpNo); // 存储申请受理号

		tLCContHangUpStateSchema.setNBFlag("0"); // 承保挂起标志
		tLCContHangUpStateSchema.setAgentFlag("0"); // 渠道挂起标志
		tLCContHangUpStateSchema.setRNFlag(mRnFlag); // 续期挂起标志
		tLCContHangUpStateSchema.setPosFlag(mPosFlag); // 保全挂起标志
		tLCContHangUpStateSchema.setClaimFlag(mClmFlag); // 理赔挂起标志

		tLCContHangUpStateSchema.setStandFlag1(mStandFlag1);
		tLCContHangUpStateSchema.setStandFlag2(mStandFlag2);
		tLCContHangUpStateSchema.setStandFlag3(mStandFlag3);

		tLCContHangUpStateSchema.setMakeDate(PubFun.getCurrentDate());
		tLCContHangUpStateSchema.setMakeTime(PubFun.getCurrentTime());
		tLCContHangUpStateSchema.setOperator(mGlobalInput.Operator);
		tLCContHangUpStateSchema.setModifyDate(PubFun.getCurrentDate());
		tLCContHangUpStateSchema.setModifyTime(PubFun.getCurrentTime());

		MMap tMMap = new MMap();
		mMMap.put(tLCContHangUpStateSchema, "DELETE&INSERT");
		return true;
	}

	/**
	 * 插入保单挂起记录
	 * 
	 * @return
	 */
	public LCContHangUpStateSchema getHungUp() {
		LCContHangUpStateSchema tLCContHangUpStateSchema = new LCContHangUpStateSchema();
		tLCContHangUpStateSchema.setContNo(mContNo);
		tLCContHangUpStateSchema.setInsuredNo(mPolNo);
		tLCContHangUpStateSchema.setPolNo(mInsuredNo);
		tLCContHangUpStateSchema.setHangUpType(mHunpUpType); // 存储申请模块号
		tLCContHangUpStateSchema.setHangUpNo(mHunpUpNo); // 存储申请受理号

		tLCContHangUpStateSchema.setNBFlag("0"); // 承保挂起标志
		tLCContHangUpStateSchema.setAgentFlag("0"); // 渠道挂起标志
		tLCContHangUpStateSchema.setRNFlag(mRnFlag); // 续期挂起标志
		tLCContHangUpStateSchema.setPosFlag(mPosFlag); // 保全挂起标志
		tLCContHangUpStateSchema.setClaimFlag(mClmFlag); // 理赔挂起标志

		tLCContHangUpStateSchema.setStandFlag1(mStandFlag1);
		tLCContHangUpStateSchema.setStandFlag2(mStandFlag2);
		tLCContHangUpStateSchema.setStandFlag3(mStandFlag3);

		tLCContHangUpStateSchema.setMakeDate(PubFun.getCurrentDate());
		tLCContHangUpStateSchema.setMakeTime(PubFun.getCurrentTime());
		tLCContHangUpStateSchema.setOperator(mGlobalInput.Operator);
		tLCContHangUpStateSchema.setModifyDate(PubFun.getCurrentDate());
		tLCContHangUpStateSchema.setModifyTime(PubFun.getCurrentTime());

		return tLCContHangUpStateSchema;
	}

	/**
	 * 根据主键删除挂起记录
	 * 
	 * @return
	 */
	public boolean deleteHungUpForKey() {
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 根据主键删除挂起记录
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LCContHangUpStateDB tLCContHangUpStateDB = new LCContHangUpStateDB();
		tLCContHangUpStateDB.setContNo(mContNo); // 合同号
		tLCContHangUpStateDB.setHangUpType(mHunpUpType); // 存储申请模块号
		tLCContHangUpStateDB.setHangUpNo(mHunpUpNo); // 存储申请受理号

		LCContHangUpStateSet tLCContHangUpStateSet = tLCContHangUpStateDB
				.query();

		if (tLCContHangUpStateDB.mErrors.needDealError()) {
			mErrors.addOneError("合同号[" + mContNo + "],受理号[" + mHunpUpNo
					+ "]的查询出现问题!!!");
			return false;
		}
		logger.debug("----------------------------------------------------------");
		logger.debug("--准备删除的挂起记录[" + tLCContHangUpStateSet.size() + "]");
		logger.debug("----------------------------------------------------------");
		mMMap.put(tLCContHangUpStateSet, "DELETE");
		return true;
	}

	/**
	 * 根据申请受理号删除挂起记录
	 * 
	 * @return
	 */
	public boolean delteHungUpForHunpUpNo() {
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 根据申请受理号删除挂起记录
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		LCContHangUpStateDB tLCContHangUpStateDB = new LCContHangUpStateDB();
		tLCContHangUpStateDB.setHangUpType(mHunpUpType); // 存储申请模块号
		tLCContHangUpStateDB.setHangUpNo(mHunpUpNo); // 存储申请受理号

		LCContHangUpStateSet tLCContHangUpStateSet = tLCContHangUpStateDB
				.query();

		if (tLCContHangUpStateDB.mErrors.needDealError()) {
			mErrors.addOneError("受理号[" + mHunpUpNo + "]的查询出现问题!!!");
			return false;
		}
		logger.debug("----------------------------------------------------------");
		logger.debug("--准备删除的挂起记录[" + tLCContHangUpStateSet.size() + "]");
		logger.debug("----------------------------------------------------------");
		mMMap.put(tLCContHangUpStateSet, "DELETE");
		return true;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		mInputData.clear();
		mInputData.add(mMMap);
		mResult.clear();
		mResult.add(mMMap);
		return true;
	}

	/**
	 * 提交数据
	 * 
	 * @return
	 */
	private boolean pubSubmit() {
		// 进行数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			this.mErrors.addOneError("数据提交失败,原因"
					+ tPubSubmit.mErrors.getError(0).errorMessage);
			return false;
		}
		return true;

	}

	/**
	 * 返回的结果集
	 * 
	 * @return
	 */
	public VData getResult() {
		mResult.clear();
		mResult.add(mMMap);
		return mResult;
	}

	public static void main(String[] args) {

		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";
		tG.ComCode = "96";

		String tHungUpNo = "90000002939";
		String tHungUpType = "4";
		String tContNo = "2301190000002387";

		LCContHangUpBL tLCContHangUpBL = new LCContHangUpBL(tG, tHungUpNo,
				tHungUpType, tContNo);

		tLCContHangUpBL.queryHungUpForContNo();
		tLCContHangUpBL.delteHungUpForHunpUpNo();

		VData tempVData = tLCContHangUpBL.getResult();
		MMap tMMap = (MMap) tempVData.getObjectByObjectName("MMap", 0);

		tLCContHangUpBL.prepareOutputData();
		tLCContHangUpBL.pubSubmit();
		//
		logger.debug("---------------后台提示信息打印------开始----------------");
		int n = tLCContHangUpBL.mErrors.getErrorCount();
		for (int i = 0; i < n; i++) {
			String Content = "";
			Content = Content + "提示信息: "
					+ tLCContHangUpBL.mErrors.getError(i).errorMessage;
			logger.debug(Content);
		}
		logger.debug("----------------后台提示信息打印------结束----------------");

	}

}
