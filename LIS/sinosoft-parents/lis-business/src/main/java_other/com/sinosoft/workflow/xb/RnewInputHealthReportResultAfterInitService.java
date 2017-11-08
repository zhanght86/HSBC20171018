package com.sinosoft.workflow.xb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.cbcheck.DisDesbBL;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.db.RnewPENoticeDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.schema.RnewPENoticeSchema;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.lis.vschema.RnewPENoticeReplySet;
import com.sinosoft.lis.vschema.RnewPENoticeResultSet;
import com.sinosoft.lis.vschema.RnewPENoticeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterInitService;

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
 * Company: Sinosoft
 * </p>
 * 
 * @author HL modify by zhangxing 解决体检合并问题
 * @version 6.0
 */
public class RnewInputHealthReportResultAfterInitService implements
		AfterInitService {
private static Logger logger = Logger.getLogger(RnewInputHealthReportResultAfterInitService.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private TransferData mTransferData = new TransferData();

	/** 数据操作字符串 */
	private String mOperater;
	private String mOperate;

	/** 业务数据操作字符串 */
	private String mContNo;
	private String mMissionID;
	private String mPrtSeq; // 问题件打印流水号

	/** 保单表 */
	private LCContSchema mLCContSchema = new LCContSchema();
	private MMap mMap;
	private RnewPENoticeResultSet mRnewPENoticeResultSet = new RnewPENoticeResultSet();
	private RnewPENoticeReplySet mRnewPENoticeReplySet = new RnewPENoticeReplySet();
	private RnewPENoticeSet mRnewPENoticeSet = new RnewPENoticeSet();
	private RnewPENoticeSet mRnewPENoticeSet1 = new RnewPENoticeSet();
	
	private LWMissionSet mLWMissionSet = new LWMissionSet();

	public RnewInputHealthReportResultAfterInitService() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            输入的数据
	 * @param cOperate
	 *            数据操作
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		// 校验是否有未打印的体检通知书
		if (!checkData())
			return false;

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

//		if(!dealMission())
//			return false;
		// 为工作流下一节点属性字段准备数据
		if (!prepareTransferData()) {
			return false;
		}

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("Start  Submit...");

		return true;
	}

	private boolean dealMission(){
		//给发送通知书任务置上操作员
		LWMissionSchema xLWMissionSchema = new LWMissionSchema();
		LWMissionDB xLWMissionDB = new LWMissionDB();
		LWMissionSet xLWMissionSet = new LWMissionSet();
		xLWMissionDB.setMissionID(mMissionID);
		//tLWMissionDB.setSubMissionID(mSubMissionID);
		xLWMissionDB.setActivityID("0000007013");
		xLWMissionSet=xLWMissionDB.query();
		if(xLWMissionSet.size()!=1){
//			if(!tLWMissionDB.getInfo()){
			CError.buildErr(this, "查询LWMission失败！");
			return false;
		}
		xLWMissionSchema = xLWMissionSet.get(1);
		xLWMissionSchema.setDefaultOperator(this.mGlobalInput.Operator);
		xLWMissionSchema.setModifyDate(PubFun.getCurrentDate());
		xLWMissionSchema.setModifyTime(PubFun.getCurrentTime());
		mLWMissionSet.add(xLWMissionSchema);
		return true;
	}
	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();
		map.put(this.mLWMissionSet, "UPDATE");
		if (this.mRnewPENoticeResultSet!=null && this.mRnewPENoticeResultSet.size()>0)
			map.put(this.mRnewPENoticeResultSet, "INSERT");
		if (this.mRnewPENoticeReplySet!=null && this.mRnewPENoticeReplySet.size()>0)
			map.put(this.mRnewPENoticeReplySet, "INSERT");
		if (this.mRnewPENoticeSet1!=null && this.mRnewPENoticeSet1.size()>0)
			map.put(this.mRnewPENoticeSet1, "UPDATE");
		
		//map.add(this.mMap);

		mResult.add(map);
		return true;
	}

	/**
	 * 校验业务数据
	 * 
	 * @return boolean
	 */

	private boolean checkData() {
		// 校验保单信息
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mRnewPENoticeSet.get(1).getContNo());
		if (!tLCContDB.getInfo()) {
			CError.buildErr(this, "保单" + mRnewPENoticeSet.get(1).getContNo() + "信息查询失败!");
			return false;
		}
		mLCContSchema.setSchema(tLCContDB);

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            输入数据
	 * @param cOperate
	 *            数据操作
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		// 从输入数据中得到所有对象
		// 获得全局公共数据
		mInputData = cInputData;
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);

		mRnewPENoticeResultSet = (RnewPENoticeResultSet) cInputData
				.getObjectByObjectName("RnewPENoticeResultSet", 0);
		if (mRnewPENoticeResultSet == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据RnewPENoticeResultSet失败!");
			return false;
		}

		mRnewPENoticeReplySet = (RnewPENoticeReplySet) cInputData
				.getObjectByObjectName("RnewPENoticeReplySet", 0);
		if (mRnewPENoticeReplySet == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据RnewPENoticeItemSet失败!");
			return false;
		}

		mRnewPENoticeSet = (RnewPENoticeSet) cInputData.getObjectByObjectName(
				"RnewPENoticeSet", 0);
		if (mRnewPENoticeSet == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据RnewPENoticeSet失败!");
			return false;
		}

		if (mGlobalInput == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据失败!");
			return false;
		}

		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据Operate失败!");
			return false;
		}

		mOperate = cOperate;
		// 获得当前工作任务的任务ID
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输业务数据中MissionID失败!");
			return false;
		}

		// ********************业务处理相关Java类**************************************
		// mRnewPENoticeResultSet = (RnewPENoticeResultSet) mTransferData
		// .getValueByName("RnewPENoticeResultSet");
		// if (mRnewPENoticeResultSet == null) {
		// // @@错误处理
		// CError.buildErr(this, "前台传输全局公共数据RnewPENoticeResultSet失败!");
		// return false;
		// }
		//
		// mRnewPENoticeItemSet = (RnewPENoticeItemSet) mTransferData
		// .getValueByName("RnewPENoticeItemSet");
		// if (mRnewPENoticeItemSet == null) {
		// // @@错误处理
		// CError.buildErr(this, "前台传输全局公共数据RnewPENoticeItemSet失败!");
		// return false;
		// }
		//
		// mRnewPENoticeSet = (RnewPENoticeSet) mTransferData
		// .getValueByName("RnewPENoticeSet");
		// if (mRnewPENoticeSet == null) {
		// // @@错误处理
		// CError.buildErr(this, "前台传输全局公共数据RnewPENoticeSet失败!");
		// return false;
		// }

		// ////////////////////////////////////////////////////////////////////////////

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean dealData() {

		try {
			logger.debug("come in  dealData()");
			// 保存既往病史
			int tSerialNo = 1;
			for (int i = 1; i <= mRnewPENoticeResultSet.size(); i++) {
				mRnewPENoticeResultSet.get(i).setSerialNo("" + tSerialNo);
				mRnewPENoticeResultSet.get(i).setDisDesb("" + tSerialNo);
				mRnewPENoticeResultSet.get(i).setICDCode("" + tSerialNo);
				mRnewPENoticeResultSet.get(i).setOperator(mOperater);
				mRnewPENoticeResultSet.get(i)
						.setMakeDate(PubFun.getCurrentDate());
				mRnewPENoticeResultSet.get(i)
						.setMakeTime(PubFun.getCurrentTime());
				mRnewPENoticeResultSet.get(i).setModifyDate(
						PubFun.getCurrentDate());
				mRnewPENoticeResultSet.get(i).setModifyTime(
						PubFun.getCurrentTime());
			}

			for (int i = 1; i <= mRnewPENoticeReplySet.size(); i++) {
				mRnewPENoticeReplySet.get(i).setModifyDate(
						PubFun.getCurrentDate());
				mRnewPENoticeReplySet.get(i).setModifyTime(
						PubFun.getCurrentTime());

			}
			RnewPENoticeDB tRnewPENoticeDB = new RnewPENoticeDB();
			RnewPENoticeSchema tRnewPENoticeSchema = new RnewPENoticeSchema();
			//tRnewPENoticeDB.setProposalContNo(mRnewPENoticeSet.get(1).getProposalContNo());
			tRnewPENoticeDB.setPrtSeq(mRnewPENoticeSet.get(1).getPrtSeq());
			tRnewPENoticeDB.setCustomerNo(mRnewPENoticeSet.get(1).getCustomerNo());
			
			if (tRnewPENoticeDB.query().size()==0) {
				CError.buildErr(this, "RnewPENotice表查询失败!");
				return false;
			}
			tRnewPENoticeSchema.setSchema(tRnewPENoticeDB.query().get(1));
			for (int i = 1; i <= mRnewPENoticeSet.size(); i++) {
				tRnewPENoticeSchema.setPEDoctor(mOperater);//用作体检结果录入人
				tRnewPENoticeSchema.setModifyDate(PubFun.getCurrentDate());
				tRnewPENoticeSchema.setModifyTime(PubFun.getCurrentTime());
				tRnewPENoticeSchema.setPEResult(mRnewPENoticeSet.get(i).getPEResult());
				//tRnewPENoticeSchema.setHospitCode(mRnewPENoticeSet.get(i).getHospitCode());//2009-01-05 ln add 体检医院代码
				tRnewPENoticeSchema.setPEAddress(mRnewPENoticeSet.get(i).getPEAddress());
				tRnewPENoticeSchema.setAgentName(mRnewPENoticeSet.get(i).getAgentName());
				tRnewPENoticeSchema.setREPEDate(mRnewPENoticeSet.get(i).getREPEDate());
				mRnewPENoticeSet1.add(tRnewPENoticeSchema);

			}
		} catch (Exception ex) {
			CError.buildErr(this, ex.toString());
			return false;
		} finally {
		}
		return true;

	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean dealData1() {

		DisDesbBL tDisDesbBL = new DisDesbBL();

		logger.debug("--------DisDesb Start!---------");
		boolean tResult = tDisDesbBL.submitData(mInputData, mOperate);
		logger.debug("--------DisDesb End!---------");

		// 如果有需要处理的错误，则返回
		if (tDisDesbBL.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tDisDesbBL.mErrors);
		}
		logger.debug("error num=" + mErrors.getErrorCount());
		mInputData = null;
		mResult = tDisDesbBL.getResult();

		if (tResult) {
			mMap = (MMap) tDisDesbBL.getResult().getObjectByObjectName("MMap",
					0);
		} else {
			this.mErrors.copyAllErrors(tDisDesbBL.mErrors);
			return false;
		}

		return true;
	}

	/**
	 * 为公共传输数据集合中添加工作流下一节点属性字段数据
	 * 
	 * @return boolean
	 */
	private boolean prepareTransferData() {
		mTransferData.setNameAndValue("ManageCom", mLCContSchema.getManageCom());
		mTransferData.setNameAndValue("AppntName", mLCContSchema.getAppntName());
		mTransferData.setNameAndValue("AgentCode", mLCContSchema.getAgentCode());
		
		return true;
	}

	/**
	 * 返回处理后的结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回工作流中的Lwfieldmap所描述的值
	 * 
	 * @return TransferData
	 */
	public TransferData getReturnTransferData() {
		return mTransferData;
	}

	/**
	 * 返回错误对象
	 * 
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}
}
