package com.sinosoft.workflow.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.cbcheck.DisDesbBL;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterInitService;

public class BQInputHealthReportResultAfterInitService implements
		AfterInitService {
private static Logger logger = Logger.getLogger(BQInputHealthReportResultAfterInitService.class);

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
	private LCPENoticeResultSet mLCPENoticeResultSet = new LCPENoticeResultSet();
	private LCPENoticeReplySet mLCPENoticeReplySet = new LCPENoticeReplySet();
	private LPPENoticeSet mLPPENoticeSet = new LPPENoticeSet();
	private LPPENoticeSet mLPPENoticeSet1 = new LPPENoticeSet();

	public BQInputHealthReportResultAfterInitService() {
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

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();
		
		if (this.mLCPENoticeResultSet!=null && this.mLCPENoticeResultSet.size()>0)
			map.put(this.mLCPENoticeResultSet, "INSERT");
		if (this.mLCPENoticeReplySet!=null && this.mLCPENoticeReplySet.size()>0)
			map.put(this.mLCPENoticeReplySet, "INSERT");
		if (this.mLPPENoticeSet1!=null && this.mLPPENoticeSet1.size()>0)
			map.put(this.mLPPENoticeSet1, "UPDATE");
		
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
		tLCContDB.setContNo(mLPPENoticeSet.get(1).getContNo());
		if (!tLCContDB.getInfo()) {
			CError.buildErr(this, "保单" + mLPPENoticeSet.get(1).getContNo() + "信息查询失败!");
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

		mLCPENoticeResultSet = (LCPENoticeResultSet) cInputData
				.getObjectByObjectName("LCPENoticeResultSet", 0);
		if (mLCPENoticeResultSet == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据LCPENoticeResultSet失败!");
			return false;
		}

		mLCPENoticeReplySet = (LCPENoticeReplySet) cInputData
				.getObjectByObjectName("LCPENoticeReplySet", 0);
		if (mLCPENoticeReplySet == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据LCPENoticeItemSet失败!");
			return false;
		}

		mLPPENoticeSet = (LPPENoticeSet) cInputData.getObjectByObjectName(
				"LPPENoticeSet", 0);
		if (mLPPENoticeSet == null) {
			// @@错误处理
			CError.buildErr(this, "前台传输全局公共数据LPPENoticeSet失败!");
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
		// mLCPENoticeResultSet = (LCPENoticeResultSet) mTransferData
		// .getValueByName("LCPENoticeResultSet");
		// if (mLCPENoticeResultSet == null) {
		// // @@错误处理
		// CError.buildErr(this, "前台传输全局公共数据LCPENoticeResultSet失败!");
		// return false;
		// }
		//
		// mLCPENoticeItemSet = (LCPENoticeItemSet) mTransferData
		// .getValueByName("LCPENoticeItemSet");
		// if (mLCPENoticeItemSet == null) {
		// // @@错误处理
		// CError.buildErr(this, "前台传输全局公共数据LCPENoticeItemSet失败!");
		// return false;
		// }
		//
		// mLCPENoticeSet = (LCPENoticeSet) mTransferData
		// .getValueByName("LCPENoticeSet");
		// if (mLCPENoticeSet == null) {
		// // @@错误处理
		// CError.buildErr(this, "前台传输全局公共数据LCPENoticeSet失败!");
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
			for (int i = 1; i <= mLCPENoticeResultSet.size(); i++) {
				mLCPENoticeResultSet.get(i).setSerialNo("" + tSerialNo);
				mLCPENoticeResultSet.get(i).setDisDesb("" + tSerialNo);
				mLCPENoticeResultSet.get(i).setICDCode("" + tSerialNo);
				mLCPENoticeResultSet.get(i).setOperator(mOperater);
				mLCPENoticeResultSet.get(i)
						.setMakeDate(PubFun.getCurrentDate());
				mLCPENoticeResultSet.get(i)
						.setMakeTime(PubFun.getCurrentTime());
				mLCPENoticeResultSet.get(i).setModifyDate(
						PubFun.getCurrentDate());
				mLCPENoticeResultSet.get(i).setModifyTime(
						PubFun.getCurrentTime());
			}

			for (int i = 1; i <= mLCPENoticeReplySet.size(); i++) {
				mLCPENoticeReplySet.get(i).setModifyDate(
						PubFun.getCurrentDate());
				mLCPENoticeReplySet.get(i).setModifyTime(
						PubFun.getCurrentTime());

			}
			LPPENoticeDB tLPPENoticeDB = new LPPENoticeDB();
			LPPENoticeSchema tLPPENoticeSchema = new LPPENoticeSchema();
			tLPPENoticeDB.setProposalContNo(mLCContSchema.getProposalContNo());
			tLPPENoticeDB.setPrtSeq(mLPPENoticeSet.get(1).getPrtSeq());
			tLPPENoticeDB.setCustomerNo(mLPPENoticeSet.get(1).getCustomerNo());
			LWMissionDB tLWMissionDB = new LWMissionDB();
			LWMissionSet tLWMissionSet = new LWMissionSet();
			String sqlstr="select *from lwmission where missionid='"+"?missionid?"+"' and activityid in(select activityid from lwactivity where functionid='10020305')";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(sqlstr);
			sqlbv1.put("missionid",this.mMissionID);
			tLWMissionSet = tLWMissionDB.executeQuery(sqlbv1);
			ExeSQL tExeSQL = new ExeSQL();
			if(tLWMissionSet.size()<1){
				CError.buildErr(this, "查询工作流信息失败！");
				return false;
			}
//			tLPPENoticeDB.setEdorAcceptNo(tLWMissionSet.get(1).getMissionProp1());
//			String EdorNoSql = "select edorno from lpedormain where edoracceptno='"
//							+tLWMissionSet.get(1).getMissionProp1()+"'";
//			String tEdorNo = tExeSQL.getOneValue(EdorNoSql);
			String EdorAcceptNoSql = "select edoracceptno from lpedormain where edorno='"+"?edorno?"+"'";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(EdorAcceptNoSql);
			sqlbv2.put("edorno",tLWMissionSet.get(1).getMissionProp9());
			String tEdorAcceptNo = tExeSQL.getOneValue(sqlbv2);
			tLPPENoticeDB.setEdorAcceptNo(tEdorAcceptNo);
			tLPPENoticeDB.setEdorNo(tLWMissionSet.get(1).getMissionProp9());
			if (!tLPPENoticeDB.getInfo()) {
				CError.buildErr(this, "LPPENotice表查询失败!");
				return false;
			}
			tLPPENoticeSchema.setSchema(tLPPENoticeDB);
			for (int i = 1; i <= mLPPENoticeSet.size(); i++) {
				//tLPPENoticeSchema.setPEDoctor(mOperater);//用作体检结果录入人!!!!
				tLPPENoticeSchema.setModifyDate(PubFun.getCurrentDate());
				tLPPENoticeSchema.setModifyTime(PubFun.getCurrentTime());
				tLPPENoticeSchema.setPEResult(mLPPENoticeSet.get(i).getPEResult());
				tLPPENoticeSchema.setPEAddress(mLPPENoticeSet.get(i).getPEAddress());
				tLPPENoticeSchema.setAgentName(mLPPENoticeSet.get(i).getAgentName());
				//tLPPENoticeSchema.setREPEDate(mLPPENoticeSet.get(i).getREPEDate());!!!!
				mLPPENoticeSet1.add(tLPPENoticeSchema);

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
