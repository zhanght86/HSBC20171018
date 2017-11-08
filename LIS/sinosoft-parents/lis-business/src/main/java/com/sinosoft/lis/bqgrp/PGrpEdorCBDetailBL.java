package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;



import com.sinosoft.lis.db.LJAGetEndorseDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LJAGetEndorseSchema;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.vschema.LJAGetEndorseSet;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;


/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 保全退保CT业务逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Sinosoft</p>
 * @author sunsx 2008-12-29; modified on 2009-01-13
 * @version 1.0
 */
public class PGrpEdorCBDetailBL implements BusinessService{
private static Logger logger = Logger.getLogger(PGrpEdorCBDetailBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();


	/** 往后面传输数据的容器 */
	private VData mInputData;


	/** 往界面传输数据的容器 */
	private VData mResult = new VData();


	/** 数据操作字符串 */
	private TransferData mTransferData = new TransferData();

	private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
    private LJSGetEndorseSet saveLJSGetEndorseSet = new LJSGetEndorseSet();


	/** 全局数据 */
	private Reflections mRef = new Reflections();
	private GlobalInput mGlobalInput = new GlobalInput();
	private MMap map = new MMap();

	public PGrpEdorCBDetailBL() {}


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

		mResult.clear();
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
		
		String tSQL = "select * from LPGrpEdorItem where GrpContNo = '"+mLPGrpEdorItemSchema.getGrpContNo()+"' and EdorState = '0' order by MakeDate desc,MakeTime desc ";
		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		LPGrpEdorItemSet tLPGrpEdorItemSet = tLPGrpEdorItemDB.executeQuery(tSQL);
		if(tLPGrpEdorItemSet == null || tLPGrpEdorItemSet.size()<1)
		{
			CError.buildErr(this, "该合同在上一次退保操作后已经发生了变动或没有进行过退保操作,不能回退!");
			return false;
		}
		LPGrpEdorItemSchema tLPGrpEdorItemSchema = tLPGrpEdorItemSet.get(1);
		String tLastEdorType = tLPGrpEdorItemSchema.getEdorType();
		if(!"CT".equals(tLastEdorType)&&!"WT".equals(tLastEdorType)&&!"XT".equals(tLastEdorType)&&!"ZT".equals(tLastEdorType)&&!"AT".equals(tLastEdorType)&&!"AX".equals(tLastEdorType)&&!"AZ".equals(tLastEdorType))
		{
			CError.buildErr(this, "该合同在上一次退保操作后已经发生了变动或没有进行过退保操作,不能回退!");
			return false;
		}
		String tLastEdorNo = tLPGrpEdorItemSchema.getEdorNo();
		
		LJAGetEndorseDB tLJAGetEndorseDB = new LJAGetEndorseDB();
		tLJAGetEndorseDB.setEndorsementNo(tLastEdorNo);
		tLJAGetEndorseDB.setFeeOperationType(tLastEdorType);
		LJAGetEndorseSet tLJAGetEndorseSet = tLJAGetEndorseDB.query();
		if(tLJAGetEndorseSet != null && tLJAGetEndorseSet.size()>0){
			LJAGetEndorseSchema tLJAGetEndorseSchema = null;
			LJSGetEndorseSchema tLJSGetEndorseSchema = null;
			for(int i = 1;i<=tLJAGetEndorseSet.size();i++ ){
				tLJAGetEndorseSchema = tLJAGetEndorseSet.get(i);
				tLJSGetEndorseSchema = new LJSGetEndorseSchema();
				mRef.transFields(tLJSGetEndorseSchema, tLJAGetEndorseSchema);
				tLJSGetEndorseSchema.setGetMoney(-tLJSGetEndorseSchema.getGetMoney()); 
				tLJSGetEndorseSchema.setGetNoticeNo(mLPGrpEdorItemSchema.getEdorNo());
				tLJSGetEndorseSchema.setEndorsementNo(mLPGrpEdorItemSchema.getEdorNo());
				tLJSGetEndorseSchema.setFeeOperationType(mLPGrpEdorItemSchema.getEdorType());
				tLJSGetEndorseSchema.setGetDate(mLPGrpEdorItemSchema.getEdorValiDate());
				tLJSGetEndorseSchema.setOtherNo(mLPGrpEdorItemSchema.getEdorNo()); // 其他号码置为保全批单号
				tLJSGetEndorseSchema.setOtherNoType("3"); // 保全给付
				tLJSGetEndorseSchema.setOperator(mGlobalInput.Operator);
				tLJSGetEndorseSchema.setMakeDate(PubFun.getCurrentDate());
				tLJSGetEndorseSchema.setMakeTime(PubFun.getCurrentTime());
				tLJSGetEndorseSchema.setModifyDate(PubFun.getCurrentDate());
				tLJSGetEndorseSchema.setModifyTime(PubFun.getCurrentTime());
				saveLJSGetEndorseSet.add(tLJSGetEndorseSchema);
			}
		}
		//更新批改项目状态信息
		mLPGrpEdorItemSchema.setEdorState("1");
		mLPGrpEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
		mLPGrpEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
		map.put(mLPGrpEdorItemSchema, "UPDATE");
		return true;

	}

	/**
	 * 删除上次保存过的数据
	 * @return boolean
	 */
	private boolean delPData() {
		//清除P表中上次保存过的数据
		String edorno = mLPGrpEdorItemSchema.getEdorNo();
		map.put("delete from LJSGetEndorse " + " where EndorsementNo='" + edorno +"'", "DELETE");
		
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

		map.put(saveLJSGetEndorseSet, "DELETE&INSERT");

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


	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return this.mErrors;
	}


}
