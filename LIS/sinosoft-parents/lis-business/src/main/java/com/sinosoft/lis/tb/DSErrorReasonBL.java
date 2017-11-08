package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.BPOErrLogDB;
import com.sinosoft.lis.db.LBPODataDetailErrorDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.BPOErrLogSchema;
import com.sinosoft.lis.schema.LBPODataDetailErrorSchema;
import com.sinosoft.lis.vschema.BPOErrLogSet;
import com.sinosoft.lis.vschema.LBPODataDetailErrorSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class DSErrorReasonBL {
private static Logger logger = Logger.getLogger(DSErrorReasonBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	private LBPODataDetailErrorSet mLBPODataDetailErrorSet = new LBPODataDetailErrorSet();// 接收从前台传入的set

	private LBPODataDetailErrorSet tLBPODataDetailErrorSet = new LBPODataDetailErrorSet();// 存储数据库中已存在相同的记录，既必须先删除相同的记录，再插入的记录

	private LBPODataDetailErrorSet pLBPODataDetailErrorSet = new LBPODataDetailErrorSet();// 存储数据库中存在相同的记录，直接插入的新记录

	private String mOperate;// 操作变量

	private String mOperateType = "";// 操作类型
	
	MMap map = new MMap();

	/** 存储全局变量 */
	private GlobalInput mGlobalInput = new GlobalInput();

	public DSErrorReasonBL() {

	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */

	public boolean submitData(VData cInputData, String cOperate) {

		logger.debug("DSErrorReasonBL.java");

		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();

		if (!getInputData(cInputData)) {
			return false;
		}

		// 将操作类型赋值给操作变量
		this.mOperate = mOperateType;

		// 数据操作业务处理
		if (cOperate.equals("INSERT")) {
			if (!checkData()) {
				return false;
			}

			if (!dealData()) {
				return false;
			}

		}

		if (!prepareOutputData()) {
			CError.buildErr(this, "保存数据失败!");
		}

		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, mOperate)) {

			CError.buildErr(this, "保存数据失败!"
					+ tPubSubmit.mErrors.getFirstError());
			return false;
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	private boolean prepareOutputData() {
		mInputData.clear();
		mInputData.add(map);
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {

		for(int i=1;i<=mLBPODataDetailErrorSet.size();i++){
			LBPODataDetailErrorSchema tLBPODataDetailErrorSchema = new LBPODataDetailErrorSchema();
			LBPODataDetailErrorSchema ttLBPODataDetailErrorSchema = new LBPODataDetailErrorSchema();
			tLBPODataDetailErrorSchema=mLBPODataDetailErrorSet.get(i);
			LBPODataDetailErrorDB tLBPODataDetailErrorDB = new LBPODataDetailErrorDB();
			tLBPODataDetailErrorDB.setBussNo(tLBPODataDetailErrorSchema.getBussNo());
			tLBPODataDetailErrorDB.setBussNoType(tLBPODataDetailErrorSchema.getBussNoType());
			tLBPODataDetailErrorDB.setErrorCount(tLBPODataDetailErrorSchema.getErrorCount());
			tLBPODataDetailErrorDB.setSerialNo(tLBPODataDetailErrorSchema.getSerialNo());
			if(!tLBPODataDetailErrorDB.getInfo()){
				CError.buildErr(this, "查询LBPODataDetailError表信息失败!");
				return false;
			}
			ttLBPODataDetailErrorSchema=tLBPODataDetailErrorDB.getSchema();
			ttLBPODataDetailErrorSchema.setErrorFlag(tLBPODataDetailErrorSchema.getErrorFlag());
			pLBPODataDetailErrorSet.add(ttLBPODataDetailErrorSchema);
		}
		map.put(pLBPODataDetailErrorSet, "UPDATE");
		return true;
	}

	private boolean getInputData(VData cInputData) {

		try {
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			mOperateType = (String) cInputData.get(0);// 接收操作类型
			logger.debug("所要执行的操作类型是*********" + mOperateType);

			// 获得传入的BPOErrLogSet
			mLBPODataDetailErrorSet = ((LBPODataDetailErrorSet) cInputData
					.getObjectByObjectName("LBPODataDetailErrorSet", 0));
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AbnormityErrAndRecordErrBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 校验所有记录的差错类别和差错编码是否为空 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		return true;
	}

	public static void main(String[] args) {
		BPOErrLogSet dBPOErrLogSet = new BPOErrLogSet();
		BPOErrLogSchema aBPOErrLogSchema = new BPOErrLogSchema();
		aBPOErrLogSchema.setBussNo("86110100005054");
		aBPOErrLogSchema.setBussNoType("TB");
		aBPOErrLogSchema.setErrCode("00");
		aBPOErrLogSchema.setErrVer("3");
		dBPOErrLogSet.add(aBPOErrLogSchema);

		String xOperateType = "INSERT";
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "DEV";

		VData tVData = new VData();

		// 将数据提交给后台UI,参数是VData和操作类型
		AbnormityErrAndRecordErrBL mAbnormityErrAndRecordErrBL = new AbnormityErrAndRecordErrBL();
		try {
			// 将操作类型，管理机构，操作员添加到容器中
			tVData.addElement(xOperateType);
			tVData.addElement(dBPOErrLogSet);
			tVData.addElement(tGlobalInput);

			mAbnormityErrAndRecordErrBL.submitData(tVData, xOperateType);
			// mAbnormityErrAndRecordErrBL.isInteger("x");
		} catch (Exception ex) {
			String Content = "失败，原因是:" + ex.toString();
		}
	}
}
