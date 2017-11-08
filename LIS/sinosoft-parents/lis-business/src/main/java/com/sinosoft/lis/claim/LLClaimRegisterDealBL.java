/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LWLockDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.db.LLRegisterDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LLRegisterSchema;
import com.sinosoft.lis.schema.LWLockSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 理赔延迟立案逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author zl
 * @version 1.0
 */
public class LLClaimRegisterDealBL {
private static Logger logger = Logger.getLogger(LLClaimRegisterDealBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	// 全局变量
	private MMap map = new MMap();
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private GlobalInput mG = new GlobalInput();
	TransferData mTransferData = new TransferData();

	// 立案相关
	private LLRegisterSchema mLLRegisterSchema = new LLRegisterSchema();
	private LWMissionSchema mLWMissionSchema = new LWMissionSchema();

	private String mConclusionSave = "";
	private String mRptNo = "";
	private String mMissionID;
	private String mSubMissionID;

	public LLClaimRegisterDealBL() {
	}

	public static void main(String[] args) {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("----------LLClaimRegisterDealBL begin submitData----------");
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("----------LLClaimRegisterDealBL after getInputData----------");

		// 检查数据合法性
		if (!checkInputData()) {
			return false;
		}
		logger.debug("----------LLClaimRegisterDealBL after checkInputData----------");
		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("----------LLClaimRegisterDealBL after dealData----------");
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("----------LLClaimRegisterDealBL after prepareOutputData----------");
		
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, cOperate)) {
			// @@错误处理
			CError.buildErr(this, "数据提交失败,"+tPubSubmit.mErrors.getLastError());
			return false;
		}
		mInputData = null;
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		logger.debug("---LLClaimRegisterDealBL start getInputData()...");
		// 获取页面报案信息
		mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0); // 按类取值
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		mRptNo = (String) mTransferData.getValueByName("RptNo");
		mConclusionSave = (String) mTransferData
				.getValueByName("RgtConclusion");
		mMissionID = (String) mTransferData.getValueByName("MissionID");// 获得当前工作任务的任务ID
		mSubMissionID = (String) mTransferData.getValueByName("SubMissionID");

		return true;
	}

	/**
	 * 校验传入的报案信息是否合法
	 * 
	 * @return：如果发生错误则返回false,否则返回true
	 */
	private boolean checkInputData() {
		return true;
	}

	/**
	 * 数据操作类业务处理
	 * 
	 * @return：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 更新工作流
		if (!updateMyMission()) {
			// @@错误处理
			CError.buildErr(this, "更新工作流失败!");
			
			return false;
		}

	//延迟立案，在LLRegister.RgtConfDate仍记录立案通过日期，为报表统计做准备
		LLRegisterDB tLLRegisterDB = new LLRegisterDB();
		tLLRegisterDB.setRgtNo(mRptNo);
		tLLRegisterDB.getInfo();
		mLLRegisterSchema = tLLRegisterDB.getSchema();
		mLLRegisterSchema.setRgtConfDate(CurrentDate);
		map.put(mLLRegisterSchema, "DELETE&INSERT");
		
		// 解除保单挂起
		LLLcContReleaseBL tLLLcContReleaseBL = new LLLcContReleaseBL();
		if (!tLLLcContReleaseBL.submitData(mInputData, "")) {
			// @@错误处理
			CError.buildErr(this, "解除保单挂起失败,"+tLLLcContReleaseBL.mErrors.getLastError());
			return false;
		} else {
			VData tempVData = new VData();
			tempVData = tLLLcContReleaseBL.getResult();
			MMap tMap = new MMap();
			tMap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
			map.add(tMap);
		}

		return true;
	}

	/**
	 * 更改工作流LWMission表中当前节点的相关属性
	 * 
	 * @return boolean
	 */
	private boolean updateMyMission() {
		// 更新LWMission表
		LWMissionDB tLWMissionDB = new LWMissionDB();
		tLWMissionDB.setMissionID(mMissionID);
		tLWMissionDB.setSubMissionID(mSubMissionID);
		tLWMissionDB.setActivityID("0000005015");
		tLWMissionDB.getInfo();
		mLWMissionSchema = tLWMissionDB.getSchema();

	//	mLWMissionSchema.setDefaultOperator(null);
		mLWMissionSchema.setMissionProp2("25");// 延迟立案

		map.put(mLWMissionSchema, "DELETE&INSERT");

		// 删除工作流锁表数据(2005-12-27 周磊)
		LWLockDB tLWLockDB = new LWLockDB();
		tLWLockDB.setMissionID(mMissionID);
		tLWLockDB.setSubMissionID(mSubMissionID);
		tLWLockDB.setLockActivityID("0000005015");
		tLWLockDB.setLockType("1");
		tLWLockDB.getInfo();
		LWLockSchema mLWLockSchema = tLWLockDB.getSchema();
		if (mLWLockSchema != null) {
			map.put(mLWLockSchema, "DELETE");
		}

		return true;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(map);
			mResult.add(mLLRegisterSchema);

		} catch (Exception ex) {
			// @@错误处理
			CError.buildErr(this, "在准备往后层处理所需要的数据时出错!");
			return false;
		}
		return true;
	}

}
