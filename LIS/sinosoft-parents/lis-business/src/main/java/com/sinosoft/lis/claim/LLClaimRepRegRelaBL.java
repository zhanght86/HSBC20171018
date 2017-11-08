package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;


import com.sinosoft.lis.db.LLRegisterDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCDelPolLogSchema;
import com.sinosoft.lis.schema.LLRegisterSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 6.0
 */
public class LLClaimRepRegRelaBL {
private static Logger logger = Logger.getLogger(LLClaimRepRegRelaBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	/** 传输到后台处理的map */
	private MMap mMap = new MMap();

	private LCDelPolLogSchema mLCDelPolLogSchema = new LCDelPolLogSchema();

	/** 数据操作字符串 */
	private String mOperate;
	private String mOperator;
	private String mManageCom;

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	/** 业务处理相关变量 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();
	private LLRegisterSchema mLLRegisterSchema = null;

	// 保单数据打包字符串
	private String mDelReason = "";
	private String mRemark = "";
	private String mPrtNo = "";
	private String mRgtNo = "";
	private String mRptNo = "";
	// private String mDelReasonName = "";

	public LLClaimRepRegRelaBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {

		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		if (getInputData() == false) {
			return false;
		}

		if (checkData() == false) {
			return false;
		}

		if (dealData() == false) {
			return false;
		}

		this.prepareOutputData();

		try {
			PubSubmit tPubSubmit = new PubSubmit();
			if (!tPubSubmit.submitData(mResult, mOperate)) {
				// 错误处理
				this.mErrors.copyAllErrors(tPubSubmit.mErrors);
				return false;
			}

			return true;
		} catch (Exception ex) {
			this.mErrors.addOneError("删除失败，原因是：" + ex.toString());
			return false;
		}

	}

	/**
	 * 将UI层传输来得数据根据业务还原成具体的类
	 * 
	 * @return boolean
	 */
	private boolean getInputData() {
		logger.debug("ScanContDeleteBL->getInputData()");
		// 全局变量实例
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));

		if (mGlobalInput == null) {
			mErrors.addOneError(new CError("没有得到全局量信息"));
			return false;
		}

		this.mOperator = mGlobalInput.Operator;
		this.mManageCom = mGlobalInput.ManageCom;

		mLLRegisterSchema = (LLRegisterSchema) mInputData.getObjectByObjectName("LLRegisterSchema", 0);
		
        mRgtNo = (String) mLLRegisterSchema.getRgtNo(); //立案号
        mRptNo = (String) mLLRegisterSchema.getRgtObjNo(); //报案号
		
		if (mRgtNo == null || mRgtNo.equals("")) {
			mErrors.addOneError(new CError("没有得到立案号数据！"));
			return false;
		}
		
		if (mRptNo == null || mRptNo.equals("")) {
			mErrors.addOneError(new CError("没有得到报案号数据！"));
			return false;
		}


		return true;
	}

	private boolean checkData() {
//		String tSQL = "select 'X' from lccont where prtno='" + mPrtNo + "'";
//		ExeSQL tExeSQL = new ExeSQL();
//		String tValue = "";
//		tValue = tExeSQL.getOneValue(tSQL);
//		if (tValue != null && !"".equals(tValue)) {
//			mErrors.addOneError(new CError("此保单已经录入，不能删除！"));
//			return false;
//		}
		return true;
	}

	private boolean dealData() {
		LLRegisterDB mLLRegisterDB=new LLRegisterDB();
		mLLRegisterDB.setRgtNo(mRgtNo);
		
		mLLRegisterDB.getInfo();
		mLLRegisterSchema = mLLRegisterDB.getSchema();
				

		//修改立案信息，报案立案关联
		mLLRegisterSchema.setRgtObj("1"); //号码类型  1-个险报案号 2-团险报案号 3-个险立案号
		mLLRegisterSchema.setRgtObjNo(mRptNo);
		mLLRegisterSchema.setModifyDate(PubFun.getCurrentDate());
		mLLRegisterSchema.setModifyTime(PubFun.getCurrentTime());
		mMap.put(mLLRegisterSchema, "DELETE&INSERT");

		return true;
	}

	/**
	 * 准备数据，重新填充数据容器中的内容
	 */
	private void prepareOutputData() {
		// 记录当前操作员
		mResult.clear();
		mResult.add(mMap);
	}

	/**
	 * 操作结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		mResult.clear();
		return mResult;
	}

	public static void main(String[] args) {
		LLClaimRepRegRelaBL tLLClaimRepRegRelaBL = new LLClaimRepRegRelaBL();
	}
}
