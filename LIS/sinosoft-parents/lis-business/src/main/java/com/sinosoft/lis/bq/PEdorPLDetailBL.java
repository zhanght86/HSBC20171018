package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import java.util.Date;

import com.sinosoft.lis.db.LCContStateDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContStateSchema;
import com.sinosoft.lis.schema.LPContStateSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LPContStateSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
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
public class PEdorPLDetailBL {
private static Logger logger = Logger.getLogger(PEdorPLDetailBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private MMap mMap = new MMap();
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;
	private String mState = "";// 1:挂失；0：取消挂失

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 业务数据 */
	private TransferData mTransferData = new TransferData();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private LPContStateSet mLPContStateSet = new LPContStateSet();

	private String mLostTypeCode = "";// 挂失原因编码
	private String mLostType = ""; // 挂失原因
	private String mStateReason = ""; // 挂失类型
	private String mRemark = ""; // 丢失日期
	private String mLostDate = "";
	private String mContNo = "";
	private String mStartDate = "";
	private String mEdorNo = "";
	private String mEdorType = "";

	public PEdorPLDetailBL() {

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

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {

		mLPEdorItemSchema = (LPEdorItemSchema) cInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (mTransferData == null || mLPEdorItemSchema == null
				|| mGlobalInput == null) {
			mErrors.addOneError(new CError("数据传输不完全！"));
			return false;
		}
		mState = (String) mTransferData.getValueByName("LostFlag");
		if (mState == null
				|| !(mState.trim().equals("1") || mState.trim().equals("0"))) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorPLDetailBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输数据LostFlag失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mState.trim().equals("1")) {
			mStateReason = (String) mTransferData.getValueByName("StateReason");
			mRemark = (String) mTransferData.getValueByName("Remark");
			mLostTypeCode = mLPEdorItemSchema.getEdorReasonCode();
			mLostType = mLPEdorItemSchema.getEdorReason();
			this.mLostDate = (String) mTransferData.getValueByName("LostDate");
		}
		mContNo = mLPEdorItemSchema.getContNo();
		if (mContNo == null || mContNo.trim().equals("")) {
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

		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());

		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemSet == null || tLPEdorItemSet.size() <= 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorPLDetailBL";
			tError.functionName = "checkdata";
			tError.errorMessage = "无保全申请数据!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLPEdorItemSchema.setSchema(tLPEdorItemSet.get(1));
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
		mEdorNo = mLPEdorItemSchema.getEdorNo();
		if (mEdorNo == null || mEdorNo.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorPLDetailBL";
			tError.functionName = "checkData";
			tError.errorMessage = "保全批单号为空!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mEdorType = mLPEdorItemSchema.getEdorType();
		if (mEdorType == null || mEdorType.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorPLDetailBL";
			tError.functionName = "checkData";
			tError.errorMessage = "保全批改类型为空!";
			this.mErrors.addOneError(tError);
			return false;
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
		LCContStateSet tLCContStateSet = new LCContStateSet();
		LCContStateSchema tLCContStateSchema;
		LPContStateSchema tLPContStateSchema;
		LCContStateDB tLCContStateDB = new LCContStateDB();
		String strSql = "select * from lccontstate where statetype = 'Lost' "
				+ "and state = '1' and enddate is null and contno = '?mContNo?'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(strSql);
		sqlbv.put("mContNo", mContNo);
		tLCContStateSet = tLCContStateDB.executeQuery(sqlbv);
		logger.debug("strSql[" + strSql + "]");
		
		Reflections tRef = new Reflections();
		
		// 判断传入的mState如果＝1 ，说明要挂失，上面SQL返回结果已经挂失过，报错，提示系统已经挂失
		// 判断传入的mState如果＝2 ，说明要解除，上面SQL返回没有结果，报错，提示系统没有挂失
		if (tLCContStateSet != null && tLCContStateSet.size() >= 1) {
			// add by jiaqiangli 2008-08-05 挂失的校验
			if (mState.trim().equals("1")) {
				mErrors.addOneError(new CError(mContNo + "有未解除的挂失！"));
				return false;
			}
			// add by jiaqiangli 2008-08-05
			
			//此处表示作解挂处理
			for (int i = 1; i <= tLCContStateSet.size(); i++) {
				tLCContStateSchema = new LCContStateSchema();
				tLCContStateSchema.setSchema(tLCContStateSet.get(i));
				tLPContStateSchema = new LPContStateSchema();
				tRef.transFields(tLPContStateSchema, tLCContStateSchema);// 先将c表数据copy到p表中
				tLPContStateSchema.setEdorNo(mEdorNo);
				tLPContStateSchema.setEdorType(mEdorType);
				if (tLPContStateSchema.getStartDate().compareTo(tOldEndDate) > 0)
					tLPContStateSchema.setEndDate(tLPContStateSchema.getStartDate());
				else
					tLPContStateSchema.setEndDate(tOldEndDate); // 置此状态结束时间
				tLPContStateSchema.setModifyDate(PubFun.getCurrentDate());
				tLPContStateSchema.setModifyTime(PubFun.getCurrentTime());
				if (mState.trim().equals("1")) {
					tLPContStateSchema.setStateReason(mStateReason);
					tLPContStateSchema.setRemark(mRemark);
				}
				mLPContStateSet.add(tLPContStateSchema);
				
				//先将state='1'的置上enddate 后重新生成新状态
				tLCContStateSchema = new LCContStateSchema();
				tLCContStateSchema.setSchema(tLCContStateSet.get(i));
				tLPContStateSchema = new LPContStateSchema();
				tRef.transFields(tLPContStateSchema, tLCContStateSchema);
				tLPContStateSchema.setEdorNo(mEdorNo);
				tLPContStateSchema.setEdorType(mEdorType);
				tLPContStateSchema.setStateReason("");
				tLPContStateSchema.setRemark("");
				tLPContStateSchema.setState("0");
				if (tLPContStateSchema.getStartDate().compareTo(mStartDate) == 0)
					tLPContStateSchema.setStartDate(PubFun.calDate(mStartDate,1,"D",null));
				else
					tLPContStateSchema.setStartDate(mStartDate);
				tLPContStateSchema.setModifyDate(PubFun.getCurrentDate());
				tLPContStateSchema.setModifyTime(PubFun.getCurrentTime());
				mLPContStateSet.add(tLPContStateSchema);
			}
		} else {
			// add by jiaqiangli 2008-08-05 解挂的校验
			if (mState.trim().equals("0")) {
				mErrors.addOneError(new CError(mContNo + "没有进行过挂失！"));
				return false;
			}
			// add by jiaqiangli 2008-08-05
			
			//挂失的处理
			//此处应该先查询是否存在state='0'的这条记录 保单初始可能并没有生成Lost的state='0'的记录
			strSql = "select * from lccontstate where statetype = 'Lost' "
					+ "and state = '0' and enddate is null and contno = '?mContNo?'";
			SQLwithBindVariables sbv=new SQLwithBindVariables();
			sbv.sql(strSql);
			sbv.put("mContNo", mContNo);
			tLCContStateSet = tLCContStateDB.executeQuery(sbv);
			logger.debug("strSql[" + strSql + "]");
			if (tLCContStateSet.size() > 0) {
				tLPContStateSchema = new LPContStateSchema();
				//先置上旧状态
				tRef.transFields(tLPContStateSchema, tLCContStateSet.get(1));
				//同一天挂失与解挂最多允许出现一次
				if (mStartDate.compareTo(tLPContStateSchema.getStartDate()) < 0) {
					mErrors.addOneError(new CError(mContNo + "一天之内出现多次挂失与解挂！"));
					return false;
				}
				//置上enddate
				if (tLPContStateSchema.getStartDate().compareTo(tOldEndDate) > 0)
					tLPContStateSchema.setEndDate(tLPContStateSchema.getStartDate());
				else 
					tLPContStateSchema.setEndDate(tOldEndDate);
				tLPContStateSchema.setEdorNo(mEdorNo);
				tLPContStateSchema.setEdorType(mEdorType);
				tLPContStateSchema.setModifyDate(PubFun.getCurrentDate());
				tLPContStateSchema.setModifyTime(PubFun.getCurrentTime());
				mLPContStateSet.add(tLPContStateSchema);
				
				//生成新状态
				tLPContStateSchema = new LPContStateSchema();
				//先置上旧状态
				tRef.transFields(tLPContStateSchema, tLCContStateSet.get(1));
				tLPContStateSchema.setState(mState);
				if (tLPContStateSchema.getStartDate().compareTo(mStartDate) == 0)
					tLPContStateSchema.setStartDate(PubFun.calDate(mStartDate,1,"D",null));
				else
					tLPContStateSchema.setStartDate(mStartDate);
				tLPContStateSchema.setEdorNo(mEdorNo);
				tLPContStateSchema.setEdorType(mEdorType);
				tLPContStateSchema.setModifyDate(PubFun.getCurrentDate());
				tLPContStateSchema.setModifyTime(PubFun.getCurrentTime());
				tLPContStateSchema.setStateReason(mStateReason);
				tLPContStateSchema.setRemark(mRemark);
				mLPContStateSet.add(tLPContStateSchema);
			}
			else {
				//新生成状态
				tLPContStateSchema = new LPContStateSchema();
				tLPContStateSchema.setEdorNo(mEdorNo);
				tLPContStateSchema.setEdorType(mEdorType);
				tLPContStateSchema.setContNo(mContNo);
				tLPContStateSchema.setInsuredNo("000000");
				tLPContStateSchema.setPolNo("000000");
				tLPContStateSchema.setStateType("Lost");
				tLPContStateSchema.setState(mState);
				tLPContStateSchema.setStartDate(mStartDate);
				tLPContStateSchema.setOperator(mGlobalInput.Operator);
				tLPContStateSchema.setMakeDate(PubFun.getCurrentDate());
				tLPContStateSchema.setMakeTime(PubFun.getCurrentTime());
				tLPContStateSchema.setModifyDate(PubFun.getCurrentDate());
				tLPContStateSchema.setModifyTime(PubFun.getCurrentTime());
				if (mState.trim().equals("1")) {
					tLPContStateSchema.setStateReason(mStateReason);
					tLPContStateSchema.setRemark(mRemark);
				}
				mLPContStateSet.add(tLPContStateSchema);
			}

		}
		
		if (mState.trim().equals("1")) {
			mLPEdorItemSchema.setEdorReasonCode(mLostTypeCode);
			mLPEdorItemSchema.setEdorReason(mLostType);
			mLPEdorItemSchema.setStandbyFlag2(this.mLostDate);
		}else{
			mLPEdorItemSchema.setEdorReasonCode("");
			mLPEdorItemSchema.setEdorReason("");
		}
		mLPEdorItemSchema.setEdorState("3"); // 设置状态为未录入状态
		// add by jiaqiangli 2008-08-14 借用StandbyFlag1区分是挂失还是解挂
		// 1为挂失0为解挂
		mLPEdorItemSchema.setStandbyFlag1(mState);
		mLPEdorItemSchema.setOperator(mGlobalInput.Operator);
		mLPEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
		mLPEdorItemSchema.setModifyTime(PubFun.getCurrentTime());

		return true;
	}

	private boolean prepareOutputData() {

		mMap.put(mLPEdorItemSchema, "UPDATE");
		mMap.put(mLPContStateSet, "DELETE&INSERT");

		mResult.clear();
		mResult.add(mMap);

		return true;
	}

	public static void main(String[] args) {
	}
}
