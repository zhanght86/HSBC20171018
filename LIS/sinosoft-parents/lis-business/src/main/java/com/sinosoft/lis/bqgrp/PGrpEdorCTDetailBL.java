package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;


import com.sinosoft.lis.bq.BqCalBL;
import com.sinosoft.lis.bq.BqCode;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;


/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 保全退保CT业务逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Sinosoft</p>
 * @author sunsx 2008-12-29; modified on 2009-01-13
 * @version 1.0
 */
public class PGrpEdorCTDetailBL {
private static Logger logger = Logger.getLogger(PGrpEdorCTDetailBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();


	/** 往后面传输数据的容器 */
	private VData mInputData;


	/** 往界面传输数据的容器 */
	private VData mResult = new VData();


	/** 数据操作字符串 */
	private TransferData mTransferData = new TransferData();

	
	private LPPolSet saveLPPolSet = new LPPolSet();
	private LPContSet saveLPContSet = new LPContSet();
	private LPGrpPolSet saveLPGrpPolSet = new LPGrpPolSet();
	private LPEdorItemSet saveLPEdorItemSet = new LPEdorItemSet();
    private LPEdorMainSet saveLPEdorMainSet = new LPEdorMainSet();
    private LPContStateSet saveLPContStateSet = new LPContStateSet();
    private LPGrpContStateSet saveLPGrpContStateSet = new LPGrpContStateSet();
    private LJSGetEndorseSet saveLJSGetEndorseSet = new LJSGetEndorseSet();
	private LPInsureAccSet mLPInsureAccSet = new  LPInsureAccSet();
	private LPInsureAccClassSet mLPInsureAccClassSet = new LPInsureAccClassSet();
	private LPInsureAccTraceSet mLPInsureAccTraceSet = new LPInsureAccTraceSet();
	private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();


	/** 全局数据 */
	private Reflections mRef = new Reflections();
	private GlobalInput mGlobalInput = new GlobalInput();
	private MMap map = new MMap();

	public PGrpEdorCTDetailBL() {}


	/**
	 * 传输数据的公共方法
	 * @param: cInputData 输入的数据
	 *         cOperate 数据操作
	 * @return:
	 */

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();

		if (!getInputData()) {
			return false;
		}

		if (!checkData()) {
			return false;
		}

		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}

		PubSubmit tPubSubmit = new PubSubmit();
		if (tPubSubmit.submitData(mResult, cOperate) == false) {
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			return false;
		}

		return true;
	}


	/**
	 * dealData
	 *
	 * @return boolean
	 */
	private boolean dealData() {

		//删除上次保存过的数据
		if (!delPData()) {
			return false;
		}
		LPPolSchema tLPPolSchema = null;//险种保单
		LCPolSchema tLCPolSchema = null;

		LPContSchema tLPContSchema = null;//合同
		LCContSchema tLCContSchema = null;

		LPGrpPolSchema tLPGrpPolSchema = null;
		LCGrpPolSchema tLCGrpPolSchema = null;

		LPEdorItemSchema tLPEdorItemSchema = null;
		LPEdorMainSchema tLPEdorMainSchema = null;
		
		//合同CONT数据个单处理
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setAppFlag("1");
		tLCContDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		LCContSet tLCContSet = tLCContDB.query();
		if(tLCContSet == null || tLCContSet.size()<1)
		{
			CError.buildErr(this, "查询团单下个人有效合同信息失败!");
			return false;
		}
		
		
		for(int i = 1; i<=tLCContSet.size();i++ )
		{
			tLPEdorMainSchema = new LPEdorMainSchema();
			tLCContSchema = tLCContSet.get(i);
			tLPEdorMainSchema.setContNo(tLCContSchema.getContNo());


			//处理POL
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setContNo(tLCContSchema.getContNo());
			tLCPolDB.setAppFlag("1");			
			LCPolSet tLCPolSet = tLCPolDB.query();
			if(tLCPolSet == null || tLCPolSet.size()<1)
			{
				CError.buildErr(this, "查询团单下个人险种失败!");
				return false;
			}
			for(int j = 1;j <= tLCPolSet.size();j++)
			{
				tLCPolSchema = tLCPolSet.get(j);
				tLPPolSchema = new LPPolSchema();
				mRef.transFields(tLPPolSchema, tLCPolSchema);
				tLPPolSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
				tLPPolSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
				tLPPolSchema.setAppFlag("4");
				tLPPolSchema.setModifyDate(PubFun.getCurrentDate());
				tLPPolSchema.setModifyTime(PubFun.getCurrentTime());
				saveLPPolSet.add(tLPPolSchema);
				
				//产生新的个人批单
				tLPEdorItemSchema = new LPEdorItemSchema();
				mRef.transFields(tLPEdorItemSchema, mLPGrpEdorItemSchema);
				mRef.transFields(tLPEdorItemSchema, tLPPolSchema);
				tLPEdorItemSchema.setPolNo(tLPPolSchema.getPolNo());

				//退保金计算;
				double tInsureGet = 0.0;//被保人领取
				GrpEdorCalZT tGrpEdorCalZT = new GrpEdorCalZT();
				tGrpEdorCalZT.setGlobalInput(mGlobalInput);
				try {
					
					tInsureGet = tGrpEdorCalZT.calZTData(tLPEdorItemSchema);
				} catch (Exception e) {
		    		  CError.buildErr(this, "计算客户号为："+tLCPolSchema.getInsuredNo()+"的退保金失败!\n 其保单号为:"+tLCPolSchema.getPolNo());
					  return false; 
				}
				
				if(tInsureGet < 0 )
				{
					CError.buildErr(this, "计算客户号为："+tLCPolSchema.getInsuredNo()+"的退保金失败!\n 其保单号为:"+tLCPolSchema.getPolNo());
					return false; 
				}
				
				String tHealthFlag = tGrpEdorCalZT.getHealthFlag();
				
				if("Y".equals(tHealthFlag)){
					mLPInsureAccSet.add(tGrpEdorCalZT.getLPInsureAccSchema());
					mLPInsureAccClassSet.add(tGrpEdorCalZT.getLPInsureAccClassSet());
				}
				
				
				if(tInsureGet>0){
					//生成补退费记录
					String sFeeType = BqCalBL.getFinType(tLPPolSchema.getEdorType(), "TB",tLCPolSchema.getPolNo());
					LJSGetEndorseSchema tLJSGetEndorseSchemaNew = new LJSGetEndorseSchema();
					tLJSGetEndorseSchemaNew.setGetNoticeNo(mLPGrpEdorItemSchema.getEdorNo());
					tLJSGetEndorseSchemaNew.setEndorsementNo(mLPGrpEdorItemSchema.getEdorNo());
					tLJSGetEndorseSchemaNew.setFeeOperationType(mLPGrpEdorItemSchema.getEdorType());
					tLJSGetEndorseSchemaNew.setFeeFinaType(sFeeType);
					tLJSGetEndorseSchemaNew.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
					tLJSGetEndorseSchemaNew.setGrpPolNo(tLPPolSchema.getGrpPolNo());
					tLJSGetEndorseSchemaNew.setContNo(tLPEdorItemSchema.getContNo());
					tLJSGetEndorseSchemaNew.setPolNo(tLPPolSchema.getPolNo());
					tLJSGetEndorseSchemaNew.setRiskCode(tLPPolSchema.getRiskCode());
					tLJSGetEndorseSchemaNew.setOtherNo(mLPGrpEdorItemSchema.getEdorNo());
					tLJSGetEndorseSchemaNew.setOtherNoType("3");
					tLJSGetEndorseSchemaNew.setDutyCode("000000");
					tLJSGetEndorseSchemaNew.setPayPlanCode("000000");
					tLJSGetEndorseSchemaNew.setGetDate(mLPGrpEdorItemSchema.getEdorValiDate());
					tLJSGetEndorseSchemaNew.setGetMoney(tInsureGet);
					tLJSGetEndorseSchemaNew.setAgentCode(tLPPolSchema.getAgentCode());
					tLJSGetEndorseSchemaNew.setAgentCom(tLPPolSchema.getAgentCom());
					tLJSGetEndorseSchemaNew.setAgentGroup(tLPPolSchema.getAgentGroup());
					tLJSGetEndorseSchemaNew.setAgentType(tLPPolSchema.getAgentType());
					tLJSGetEndorseSchemaNew.setInsuredNo(tLPPolSchema.getInsuredNo());
					tLJSGetEndorseSchemaNew.setKindCode(tLPPolSchema.getKindCode());
					tLJSGetEndorseSchemaNew.setAppntNo(tLPPolSchema.getAppntNo());
					tLJSGetEndorseSchemaNew.setRiskVersion(tLPPolSchema.getRiskVersion());
					tLJSGetEndorseSchemaNew.setHandler(tLPPolSchema.getHandler());
					tLJSGetEndorseSchemaNew.setApproveCode(tLPPolSchema.getApproveCode());
					tLJSGetEndorseSchemaNew.setApproveDate(tLPPolSchema.getApproveDate());
					tLJSGetEndorseSchemaNew.setApproveTime(tLPPolSchema.getApproveTime());
					tLJSGetEndorseSchemaNew.setManageCom(tLPPolSchema.getManageCom());
					tLJSGetEndorseSchemaNew.setCurrency(tLPPolSchema.getCurrency());
					tLJSGetEndorseSchemaNew.setPolType("1");
					tLJSGetEndorseSchemaNew.setGetFlag("1");
					tLJSGetEndorseSchemaNew.setOperator(mGlobalInput.Operator);
					tLJSGetEndorseSchemaNew.setMakeDate(PubFun.getCurrentDate());
					tLJSGetEndorseSchemaNew.setMakeTime(PubFun.getCurrentTime());
					tLJSGetEndorseSchemaNew.setModifyDate(PubFun.getCurrentDate());
					tLJSGetEndorseSchemaNew.setModifyTime(PubFun.getCurrentTime());
					tLJSGetEndorseSchemaNew.setSubFeeOperationType(BqCode.Get_Prem);
					saveLJSGetEndorseSet.add(tLJSGetEndorseSchemaNew);
				}

				tLPEdorItemSchema.setGetMoney(tInsureGet);
				tLPEdorItemSchema.setManageCom(mLPGrpEdorItemSchema.getManageCom());
				tLPEdorItemSchema.setOperator(mGlobalInput.Operator);
				tLPEdorItemSchema.setUWFlag("0");
				tLPEdorItemSchema.setEdorState("1");
				tLPEdorItemSchema.setMakeDate(PubFun.getCurrentDate());
				tLPEdorItemSchema.setMakeTime(PubFun.getCurrentTime());
				tLPEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
				tLPEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
				saveLPEdorItemSet.add(tLPEdorItemSchema);
				saveLPContStateSet.add(tGrpEdorCalZT.creatContState(tLPEdorItemSchema));

			}

			tLPContSchema = new LPContSchema();
			mRef.transFields(tLPContSchema, tLCContSchema);
			tLPContSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
			tLPContSchema.setAppFlag("4");//终止状态
			tLPContSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
			tLPContSchema.setModifyDate(PubFun.getCurrentDate());
			tLPContSchema.setModifyTime(PubFun.getCurrentTime());
			saveLPContSet.add(tLPContSchema);

			mRef.transFields(tLPEdorMainSchema, mLPGrpEdorItemSchema);
			tLPEdorMainSchema.setManageCom(mLPGrpEdorItemSchema.getManageCom());
			tLPEdorMainSchema.setOperator(mGlobalInput.Operator);
			tLPEdorMainSchema.setUWState("0");
			tLPEdorMainSchema.setMakeDate(PubFun.getCurrentDate());
			tLPEdorMainSchema.setMakeTime(PubFun.getCurrentTime());
			tLPEdorMainSchema.setModifyDate(PubFun.getCurrentDate());
			tLPEdorMainSchema.setModifyTime(PubFun.getCurrentTime());
			saveLPEdorMainSet.add(tLPEdorMainSchema);

		}
		

		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		tLCGrpPolDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		tLCGrpPolDB.setAppFlag("1");
		LCGrpPolSet tLCGrpPolSet = tLCGrpPolDB.query();
		
		if(tLCGrpPolSet == null || tLCGrpPolSet.size()<1)
		{
			CError.buildErr(this, "查询团单下个人险种失败!");
			return false;
		}
		
		for(int i = 1; i<=tLCGrpPolSet.size();i++)
		{
			tLCGrpPolSchema = tLCGrpPolSet.get(i);
			tLPGrpPolSchema = new LPGrpPolSchema();
			mRef.transFields(tLPGrpPolSchema, tLCGrpPolSchema);
			tLPGrpPolSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
			tLPGrpPolSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
			tLPGrpPolSchema.setAppFlag("4");
			tLPGrpPolSchema.setModifyDate(PubFun.getCurrentDate());
			tLPGrpPolSchema.setModifyTime(PubFun.getCurrentTime());
			saveLPGrpPolSet.add(tLPGrpPolSchema);
		}
		//准备团单数据
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		LCGrpContSchema tLCGrpContSchema = null;
		if(!tLCGrpContDB.getInfo())
		{
			CError.buildErr(this, "查询团单信息失败!");
			return false;
		}
		
		tLCGrpContSchema = tLCGrpContDB.getSchema();
		LPGrpContSchema tLPGrpContSchema = new LPGrpContSchema();
		mRef.transFields(tLPGrpContSchema, tLCGrpContSchema);
		tLPGrpContSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGrpContSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		tLPGrpContSchema.setAppFlag("4");
		tLPGrpContSchema.setModifyDate(PubFun.getCurrentDate());
		tLPGrpContSchema.setModifyTime(PubFun.getCurrentTime());
		map.put(tLPGrpContSchema, "DELETE&INSERT");

		//更新批改项目状态信息
		mLPGrpEdorItemSchema.setEdorState("1");
		mLPGrpEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
		mLPGrpEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
		map.put(mLPGrpEdorItemSchema, "UPDATE");
		GrpEdorCalZT tGrpEdorCalZT = new GrpEdorCalZT();
		saveLPGrpContStateSet.add(tGrpEdorCalZT.creatGrpContState(mLPGrpEdorItemSchema));
		return true;

	}

	/**
	 * 删除上次保存过的数据
	 * @return boolean
	 */
	private boolean delPData() {
		//清除P表中上次保存过的数据
		String edorno = mLPGrpEdorItemSchema.getEdorNo();
		String edortype = mLPGrpEdorItemSchema.getEdorType();
		String sqlWhere = " edorno = '" + edorno + "' and edortype = '" +edortype + "'";
		map.put("delete from lpgrppol where" + sqlWhere, "DELETE");
		map.put("delete from lpedoritem where" + sqlWhere, "DELETE");
		map.put("delete from lpedormain where edorno = '"+edorno+"'","DELETE");
		map.put("delete from lpgrpcont where" + sqlWhere, "DELETE");
		map.put("delete from lpgrpcontstate where" + sqlWhere, "DELETE");
		map.put("delete from lppol where" + sqlWhere, "DELETE");
		map.put("delete from lpcont where" + sqlWhere, "DELETE");
		map.put("delete from lpcontstate where" + sqlWhere, "DELETE");
		map.put("delete from lpinsureacc where" + sqlWhere, "DELETE");
		map.put("delete from lpinsureaccclass where" + sqlWhere, "DELETE");
		map.put("delete from lpinsureacctrace where" + sqlWhere, "DELETE");
		map.put("delete from LJSGetEndorse " + " where EndorsementNo='" + edorno +"' and FeeOperationType='" + edortype + "'", "DELETE");
		
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 数据操作类业务处理
	 * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		map.put(mLPInsureAccTraceSet, "DELETE&INSERT");
		map.put(mLPInsureAccClassSet, "DELETE&INSERT");
		map.put(mLPInsureAccSet, "DELETE&INSERT");
		map.put(saveLJSGetEndorseSet, "DELETE&INSERT");
		map.put(saveLPEdorItemSet, "DELETE&INSERT");
		map.put(saveLPEdorMainSet, "DELETE&INSERT");
		map.put(saveLPGrpPolSet, "DELETE&INSERT");
		map.put(saveLPContSet, "DELETE&INSERT");
		map.put(saveLPContStateSet, "DELETE&INSERT");
		map.put(saveLPGrpContStateSet, "DELETE&INSERT");
		map.put(saveLPPolSet, "DELETE&INSERT");
		mResult.clear();
		mResult.add(map);
		mResult.add(mTransferData);

		return true;
	}

	/**
	 * 从输入数据中得到所有对象
	 *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		try {
			mGlobalInput = (GlobalInput)
			mInputData.getObjectByObjectName("GlobalInput",
					0);
			mLPGrpEdorItemSchema = (LPGrpEdorItemSchema)
			mInputData.getObjectByObjectName(
					"LPGrpEdorItemSchema", 0);
			
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PGrpEdorCTDetailBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mGlobalInput == null || mLPGrpEdorItemSchema == null ){
			CError.buildErr(this, "传入数据有误!");
			return false;
		}

		return true;

	}

	/**
	 * 校验传入的数据的合法性
	 * 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		tLPGrpEdorItemDB.setSchema(mLPGrpEdorItemSchema);
		if (!tLPGrpEdorItemDB.getInfo()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PGrpEdorCTDetailBL";
			tError.functionName = "checkData";
			tError.errorMessage = "无保全项目数据!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (tLPGrpEdorItemDB.getEdorState().trim().equals("2")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PGrpEdorCTDetailBL";
			tError.functionName = "checkData";
			tError.errorMessage = "该保全项目已经申请确认不能修改!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLPGrpEdorItemSchema.setSchema(tLPGrpEdorItemDB.getSchema());

		return true;
	}


}
