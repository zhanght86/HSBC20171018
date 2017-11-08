/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LLReportSchema;
import com.sinosoft.lis.schema.LLSubReportSchema;
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
 * Description:理赔报案信息提交类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author zl
 * @todo 1 提交BL层处理的数据 2 修改工作流当前节点属性
 * @version 1.0
 */
public class LLReportBLF {
private static Logger logger = Logger.getLogger(LLReportBLF.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 提交数据容器 */
	private MMap map = new MMap();

	private TransferData mTransferData = new TransferData();
	private LWMissionSchema mLWMissionSchema = new LWMissionSchema();

	private String mMissionID;
	private String mSubMissionID;
	private String mCustomerSex;
	private String mRptNo;
	private String mRptor;
	private String mCustomerNo;
	private String mCustomerName;
	private String mAccDate;

	public LLReportBLF() {
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
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("----------BLF after getInputData----------");
		// 检查数据合法性
		if (!checkInputData()) {
			return false;
		}
		logger.debug("----------BLF after checkInputData----------");
		// 进行业务处理,得到业务处理返回值
		if (!dealData()) {
			return false;
		}
		logger.debug("----------BLF after dealData----------");
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("----------BLF after prepareOutputData----------");

		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			// @@错误处理
			CError.buildErr(this, "数据提交失败");
			return false;
		}
		mInputData = null;
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		logger.debug("--start getInputData()");

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		// *********************************************************************
		// 所有需要更新的LWMission表数据都在这里取出
		// *********************************************************************
		mRptNo = (String) mTransferData.getValueByName("RptNo");
		mRptor = (String) mTransferData.getValueByName("RptorName");
		mCustomerNo = (String) mTransferData.getValueByName("CustomerNo");
		mCustomerName = (String) mTransferData.getValueByName("CustomerName");
		mCustomerSex = (String) mTransferData.getValueByName("CustomerSex");
		mAccDate = (String) mTransferData.getValueByName("AccDate");

		return true;
	}

	/**
	 * 校验传入的信息是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkInputData() {
		return true;
	}

	/**
	 * 提交到BL处理返回处理完数据
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		logger.debug("----------BLF dealData BEGIN----------");
		boolean tReturn = false;
		
		logger.debug("mOperate:"+mOperate);

		LLReportBL tLLReportBL = new LLReportBL();
		if (!tLLReportBL.submitData(mInputData, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLLReportBL.mErrors);
			mResult.clear();
			mInputData = null;
			tReturn = false;
		} else {
			logger.debug("-----start BLF getData from BL");
			VData tempVData = new VData();
			tempVData = tLLReportBL.getResult();
			map = (MMap) tempVData.getObjectByObjectName("MMap", 0);
			tempVData.remove(0);
			mResult=tempVData;
			
			// 更新LWMission
//			if (!updateMyMission()) {
//				tReturn = false;
//			}
			tReturn = true;
		}
		return tReturn;
	}

	public VData getResult() {
		return mResult;
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
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLReportBLF";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
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
		tLWMissionDB.setActivityID("0000005005");
		tLWMissionDB.getInfo();
		mLWMissionSchema = tLWMissionDB.getSchema();

		mLWMissionSchema.setMissionProp1(mRptNo);
		mLWMissionSchema.setMissionProp2(mRptor);
		mLWMissionSchema.setMissionProp3(mCustomerNo);
		mLWMissionSchema.setMissionProp4(mCustomerName);
		mLWMissionSchema.setMissionProp5(mCustomerSex);
		mLWMissionSchema.setMissionProp6(mAccDate);

		map.put(mLWMissionSchema, "DELETE&INSERT");

		return true;
	}
}
