package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpContStateDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCGrpContStateSchema;
import com.sinosoft.lis.schema.LPGrpContStateSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.vschema.LCGrpContStateSet;
import com.sinosoft.lis.vschema.LPGrpContStateSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全－保单挂失与解挂
 * </p>
 */
public class GEdorPLDetailBL implements BusinessService{
private static Logger logger = Logger.getLogger(GEdorPLDetailBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private MMap mMap = new MMap();
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;
	private String mPLType = "";// RLC:挂失；SLC：取消挂失

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 业务数据 */
	private TransferData mTransferData = new TransferData();
	private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
	private LPGrpContStateSet mLPGrpContStateSet = new LPGrpContStateSet();

	private String mLostTypeCode = "";// 挂失原因编码
	private String mLostType = ""; // 挂失原因
	private String mStateReason = ""; // 挂失类型
	private String mRemark = ""; // 丢失日期
	private String mGrpContNo = "";
	private String mStartDate = "";
	private String mEdorNo = "";
	private String mEdorType = "";

	public GEdorPLDetailBL() {

	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public void setOperate(String cOperate) {
		this.mOperate = cOperate;
	}

	public String getOperate() {
		return this.mOperate;
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.setOperate(cOperate);

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		// 数据校验操作（checkdata)
		if (!checkData()) {
			return false;
		}

		// 数据操作业务处理(新增insertData();修改updateData();删除deletedata())
		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}
		
		PubSubmit tSubmit = new PubSubmit();
        if (!tSubmit.submitData(mResult, "")) {
            // @@错误处理
            this.mErrors.copyAllErrors(tSubmit.mErrors);
            CError tError = new CError();
            tError.moduleName = "GEdorPLDetailBL";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors.addOneError(tError);
            return false;
        }

        mResult.clear();
        return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {

		mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) cInputData
				.getObjectByObjectName("LPGrpEdorItemSchema", 0);
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (mTransferData == null || mLPGrpEdorItemSchema == null
				|| mGlobalInput == null) {
			mErrors.addOneError(new CError("数据传输不完全！"));
			return false;
		}

		mGrpContNo = mLPGrpEdorItemSchema.getGrpContNo();
		mLostTypeCode = mLPGrpEdorItemSchema.getEdorReasonCode();
		mLostType = mLPGrpEdorItemSchema.getEdorReason();
		if (mGrpContNo == null || mGrpContNo.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorPLDetailBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输数据ContNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {

		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		tLPGrpEdorItemDB.setEdorAcceptNo(mLPGrpEdorItemSchema.getEdorAcceptNo());
		tLPGrpEdorItemDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGrpEdorItemDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());

		LPGrpEdorItemSet tLPGrpEdorItemSet = tLPGrpEdorItemDB.query();
		if (tLPGrpEdorItemSet == null || tLPGrpEdorItemSet.size() <= 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorPLDetailBL";
			tError.functionName = "checkdata";
			tError.errorMessage = "无保全申请数据!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLPGrpEdorItemSchema.setSchema(tLPGrpEdorItemSet.get(1));
		//mStartDate = mLPEdorItemSchema.getEdorValiDate();
		//modify by jiaqiangli 挂失与解挂 2009-03-10 去CurrentDate
		mStartDate = PubFun.getCurrentDate();
		
		if (mStartDate == null || mStartDate.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorPLDetailBL";
			tError.functionName = "checkData";
			tError.errorMessage = "保全生效日期为空!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mEdorNo = mLPGrpEdorItemSchema.getEdorNo();
		if (mEdorNo == null || mEdorNo.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorPLDetailBL";
			tError.functionName = "checkData";
			tError.errorMessage = "保全批单号为空!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mEdorType = mLPGrpEdorItemSchema.getEdorType();
		if (mEdorType == null || mEdorType.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorPLDetailBL";
			tError.functionName = "checkData";
			tError.errorMessage = "保全批改类型为空!";
			this.mErrors.addOneError(tError);
			return false;
		}
		
		mPLType = mLPGrpEdorItemSchema.getEdorTypeCal();
		if (mPLType == null || mPLType.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorPLDetailBL";
			tError.functionName = "checkData";
			tError.errorMessage = "解挂类型为空!";
			this.mErrors.addOneError(tError);
			return false;
		}
		
		if (mPLType.trim().equals("RLC")) {
			mStateReason = (String) mTransferData.getValueByName("StateReason");
			mRemark = (String) mTransferData.getValueByName("Remark");
			

		}
		return true;
		
		
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean dealData() {

		String tOldEndDate = PubFun.calDate(mStartDate, -1,"D", null);
		// 如果保单状态c表中已有初始状态数据，则保全确认时需要更新此旧状态记录，
		// 于是p表中保存更新其旧状态的数据.否则保存要插入的新状态数据
		LCGrpContStateSet tLCGrpContStateSet = new LCGrpContStateSet();
		LCGrpContStateSchema tLCGrpContStateSchema = null;
		LPGrpContStateSchema tLPGrpContStateSchema = null;
		LCGrpContStateDB tLCGrpContStateDB = new LCGrpContStateDB();
		
		
		Reflections tRef = new Reflections();
		
		//挂失,查询是否已经处理挂失状态
		String strSql = "select * from lcgrpcontstate where statetype = 'Lost' "
			+ "and state = '1' and enddate is null and grpcontno = '" + mGrpContNo + "'";
		tLCGrpContStateSet = tLCGrpContStateDB.executeQuery(strSql);
		logger.debug("strSql[" + strSql + "]");
		
//		// 判断传入的mPLType如果＝RLC ，说明要挂失，上面SQL返回结果已经挂失过，报错，提示系统已经挂失
//		// 判断传入的mPLType如果＝SLC ，说明要解除，上面SQL返回没有结果，报错，提示系统没有挂失

		if("RLC".equals(mPLType))
		{
			
			if (tLCGrpContStateSet != null && tLCGrpContStateSet.size() >= 1) {
				mErrors.addOneError(new CError(mGrpContNo + "已经处于挂失状态！"));
				return false;
			}
			//先查询是否存在state='0'的这条记录 保单初始可能并没有生成Lost的state='0'的记录
			strSql = "select * from lcgrpcontstate where statetype = 'Lost' "
				+ "and state = '0' and enddate is null and gprcontno = '" + mGrpContNo + "'";
			tLCGrpContStateSet = tLCGrpContStateDB.executeQuery(strSql);
			if (tLCGrpContStateSet.size() > 0) {
				tLPGrpContStateSchema = new LPGrpContStateSchema();
				//先置上旧状态
				tRef.transFields(tLPGrpContStateSchema, tLCGrpContStateSet.get(1));
				//同一天挂失与解挂最多允许出现一次
				if (mStartDate.compareTo(tLPGrpContStateSchema.getStartDate()) < 0) {
					mErrors.addOneError(new CError(mGrpContNo + "一天之内出现多次挂失与解挂！"));
					return false;
				}
				//置上enddate
				if (tLPGrpContStateSchema.getStartDate().compareTo(tOldEndDate) > 0)
					tLPGrpContStateSchema.setEndDate(tLPGrpContStateSchema.getStartDate());
				else 
					tLPGrpContStateSchema.setEndDate(tOldEndDate);
				tLPGrpContStateSchema.setEdorNo(mEdorNo);
				tLPGrpContStateSchema.setEdorType(mEdorType);
				tLPGrpContStateSchema.setModifyDate(PubFun.getCurrentDate());
				tLPGrpContStateSchema.setModifyTime(PubFun.getCurrentTime());
				mLPGrpContStateSet.add(tLPGrpContStateSchema);

				//生成新状态
				tLPGrpContStateSchema = new LPGrpContStateSchema();
				//先置上旧状态
				tRef.transFields(tLPGrpContStateSchema, tLCGrpContStateSet.get(1));
				tLPGrpContStateSchema.setState("1");
				if (tLPGrpContStateSchema.getStartDate().compareTo(mStartDate) == 0)
					tLPGrpContStateSchema.setStartDate(PubFun.calDate(mStartDate,1,"D",null));
				else
					tLPGrpContStateSchema.setStartDate(mStartDate);
				tLPGrpContStateSchema.setEdorNo(mEdorNo);
				tLPGrpContStateSchema.setEdorType(mEdorType);
				tLPGrpContStateSchema.setModifyDate(PubFun.getCurrentDate());
				tLPGrpContStateSchema.setModifyTime(PubFun.getCurrentTime());
				tLPGrpContStateSchema.setStateReason(mStateReason);
				tLPGrpContStateSchema.setRemark(mRemark);
				mLPGrpContStateSet.add(tLPGrpContStateSchema);

			}else {
				//新生成状态
				tLPGrpContStateSchema = new LPGrpContStateSchema();
				tLPGrpContStateSchema.setEdorNo(mEdorNo);
				tLPGrpContStateSchema.setEdorType(mEdorType);
				tLPGrpContStateSchema.setGrpContNo(mGrpContNo);
				tLPGrpContStateSchema.setGrpPolNo("000000");
				//tLPContStateSchema.setPolNo("000000");
				tLPGrpContStateSchema.setStateType("Lost");
				tLPGrpContStateSchema.setState("1");
				tLPGrpContStateSchema.setStartDate(mStartDate);
				tLPGrpContStateSchema.setOperator(mGlobalInput.Operator);
				tLPGrpContStateSchema.setMakeDate(PubFun.getCurrentDate());
				tLPGrpContStateSchema.setMakeTime(PubFun.getCurrentTime());
				tLPGrpContStateSchema.setModifyDate(PubFun.getCurrentDate());
				tLPGrpContStateSchema.setModifyTime(PubFun.getCurrentTime());

				tLPGrpContStateSchema.setStateReason(mStateReason);
				tLPGrpContStateSchema.setRemark(mRemark);

				mLPGrpContStateSet.add(tLPGrpContStateSchema);
				

				mLPGrpEdorItemSchema.setEdorReasonCode(mLostTypeCode);
				mLPGrpEdorItemSchema.setEdorReason(mLostType);

			}

		}else if ("SLC".equals(mPLType)){
			//解挂
			if (tLCGrpContStateSet != null && tLCGrpContStateSet.size() < 1) {
				mErrors.addOneError(new CError(mGrpContNo + "没有处于挂失状态！"));
				return false;
			}
			
			tLCGrpContStateSchema = tLCGrpContStateSet.get(1);
			tLPGrpContStateSchema = new LPGrpContStateSchema();
			tRef.transFields(tLPGrpContStateSchema, tLCGrpContStateSchema);// 先将c表数据copy到p表中
			tLPGrpContStateSchema.setEdorNo(mEdorNo);
			tLPGrpContStateSchema.setEdorType(mEdorType);
			if (tLPGrpContStateSchema.getStartDate().compareTo(tOldEndDate) > 0)
				tLPGrpContStateSchema.setEndDate(tLPGrpContStateSchema.getStartDate());
			else
				tLPGrpContStateSchema.setEndDate(tOldEndDate); // 置此状态结束时间
			tLPGrpContStateSchema.setModifyDate(PubFun.getCurrentDate());
			tLPGrpContStateSchema.setModifyTime(PubFun.getCurrentTime());

			mLPGrpContStateSet.add(tLPGrpContStateSchema);
			
			//先将state='1'的置上enddate 后重新生成新状态

			tLPGrpContStateSchema = new LPGrpContStateSchema();
			tRef.transFields(tLPGrpContStateSchema, tLCGrpContStateSchema);
			tLPGrpContStateSchema.setEdorNo(mEdorNo);
			tLPGrpContStateSchema.setEdorType(mEdorType);
			tLPGrpContStateSchema.setStateReason("");
			tLPGrpContStateSchema.setRemark("");
			tLPGrpContStateSchema.setState("0");
			if (tLPGrpContStateSchema.getStartDate().compareTo(mStartDate) == 0)
				tLPGrpContStateSchema.setStartDate(PubFun.calDate(mStartDate,1,"D",null));
			else
				tLPGrpContStateSchema.setStartDate(mStartDate);
			tLPGrpContStateSchema.setModifyDate(PubFun.getCurrentDate());
			tLPGrpContStateSchema.setModifyTime(PubFun.getCurrentTime());
			mLPGrpContStateSet.add(tLPGrpContStateSchema);
			
		}


		mLPGrpEdorItemSchema.setEdorState("1"); 

		mLPGrpEdorItemSchema.setOperator(mGlobalInput.Operator);
		mLPGrpEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
		mLPGrpEdorItemSchema.setModifyTime(PubFun.getCurrentTime());

		return true;
	}

	private boolean prepareOutputData() {
		// 删除 LPGrpContState
		String DeleteSQL = "delete from LPGrpContState " + "where 1 = 1 "
				+ "and EdorNo = '" + mLPGrpEdorItemSchema.getEdorNo() + "' "
				+ "and EdorType = '" + mLPGrpEdorItemSchema.getEdorType()
				+ "' " + "and GrpContNo = '"
				+ mLPGrpEdorItemSchema.getGrpContNo() + "' "
				+ "and StateType = 'Lost'";
		// logger.debug(DeleteSQL);
		mMap.put(DeleteSQL, "DELETE");
		mMap.put(mLPGrpEdorItemSchema, "UPDATE");
		mMap.put(mLPGrpContStateSet, "DELETE&INSERT");

		mResult.clear();
		mResult.add(mMap);

		return true;
	}

	public static void main(String[] args) {
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return this.mErrors;
	}
}
