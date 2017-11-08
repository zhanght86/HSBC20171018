package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.BPOErrLogDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.BPOErrLogSchema;
import com.sinosoft.lis.vschema.BPOErrLogSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author HST
 * @version 1.0
 */
public class AbnormityErrAndRecordErrBL {
private static Logger logger = Logger.getLogger(AbnormityErrAndRecordErrBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private BPOErrLogSet mBPOErrLogSet = new BPOErrLogSet();// 接收从前台传入的set
	private BPOErrLogSet tBPOErrLogSet = new BPOErrLogSet();// 存储数据库中已存在相同的记录，既必须先删除相同的记录，再插入的记录
	private BPOErrLogSet pBPOErrLogSet = new BPOErrLogSet();// 存储数据库中存在相同的记录，直接插入的新记录
	private String mOperate;// 操作变量
	private String mOperateType = "";// 操作类型
	/** 存储全局变量 */
	private GlobalInput mGlobalInput = new GlobalInput();

	public AbnormityErrAndRecordErrBL() {

	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */

	public boolean submitData(VData cInputData, String cOperate) {

		logger.debug("开始执行AbnormityErrAndRecordErrBL.java");

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

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {

		// 首先需要判断是否是修改操作，如果是修改操作，则删除保存到数据库中的数据，再插入本次的数据
		VData tVData = new VData();
		PubSubmit ps = new PubSubmit();
		MMap map = new MMap();

		// 如果在BPOErrLog(复核抽检算法表)中查寻不到数据，则表明新增操作
		if (tBPOErrLogSet.size() == 0) {
			tVData.clear();
			map.put(mBPOErrLogSet, "INSERT");
			tVData.add(map);
		} else {
			// 删除对应BPOErrLog(复核抽检算法表)中的记录
			map.put(tBPOErrLogSet, "DELETE");

			// 插入新的记录
			map.put(mBPOErrLogSet, "INSERT");
			tVData.clear();
			tVData.add(map);
		}

		// 将数据放入VData中,调用PubSubmit进行数据库的访问
		if (!ps.submitData(tVData, null)) {

			CError.buildErr(this, "保存数据失败!" + ps.mErrors.getFirstError());
			return false;
		}

		return true;
	}

	private boolean getInputData(VData cInputData) {

		try {
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			mOperateType = (String) cInputData.get(0);// 接收操作类型
			logger.debug("所要执行的操作类型是*********" + mOperateType);

			// 获得传入的BPOErrLogSet
			mBPOErrLogSet = ((BPOErrLogSet) cInputData.getObjectByObjectName(
					"BPOErrLogSet", 0));
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
		// 循环获得所有的schema，然后进行校验
		BPOErrLogSchema tBPOErrLogSchema = new BPOErrLogSchema();
		BPOErrLogDB tBPOErrLogDB = new BPOErrLogDB();
		for (int i = 0; i < mBPOErrLogSet.size(); i++) {
			tBPOErrLogSchema = mBPOErrLogSet.get(i + 1);
			// 进行非空的校验

			logger.debug(i + "*差错类别=" + tBPOErrLogSchema.getErrVer());

			if (tBPOErrLogSchema.getErrVer() == null
					|| tBPOErrLogSchema.getErrVer().equals("")) {
				CError tError = new CError();
				tError.moduleName = "AbnormityErrAndRecordErrBL";
				tError.functionName = "checkData";
				tError.errorMessage = "所有的差错类别都不能为空";
				this.mErrors.addOneError(tError);

				return false;
			}

			logger.debug(i + "*差错编码=" + tBPOErrLogSchema.getErrCode());

			if (tBPOErrLogSchema.getErrCode() == null
					|| tBPOErrLogSchema.getErrCode().equals("")) {
				CError tError = new CError();
				tError.moduleName = "AbnormityErrAndRecordErrBL";
				tError.functionName = "checkData";
				tError.errorMessage = "所有的差错编码都不能为空";
				this.mErrors.addOneError(tError);

				return false;
			}

			tBPOErrLogDB.setBussNo(tBPOErrLogSchema.getBussNo());
			tBPOErrLogDB.setBussNoType(tBPOErrLogSchema.getBussNoType());
			tBPOErrLogDB.setErrCode(tBPOErrLogSchema.getErrCode());
			tBPOErrLogDB.setErrVer(tBPOErrLogSchema.getErrVer());

			// 补充其他字段的值
			tBPOErrLogSchema.setOperator(mGlobalInput.Operator);
			tBPOErrLogSchema.setMakeDate(PubFun.getCurrentDate());
			tBPOErrLogSchema.setMakeTime(PubFun.getCurrentTime());

			if (tBPOErrLogDB.getInfo() == true) {
				// 证明存在相应的记录
				tBPOErrLogSet.add(tBPOErrLogSchema);
			} else {
				pBPOErrLogSet.add(tBPOErrLogSchema);
			}

		}

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
