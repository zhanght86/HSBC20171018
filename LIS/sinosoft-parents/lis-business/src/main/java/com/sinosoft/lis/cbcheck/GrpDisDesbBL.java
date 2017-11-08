package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCPENoticeDB;
import com.sinosoft.lis.db.LCPENoticeItemDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vschema.LCDiseaseResultSet;
import com.sinosoft.lis.vschema.LCPENoticeItemSet;
import com.sinosoft.lis.vschema.LCPENoticeResultSet;
import com.sinosoft.lis.vschema.LCPENoticeSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author HYQ
 * @version 1.0
 */

public class GrpDisDesbBL {
private static Logger logger = Logger.getLogger(GrpDisDesbBL.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private VData mInputData;
	private GlobalInput tGI = new GlobalInput();

	/** 数据操作字符串 */
	private String mOperate;
	private String mOperator;
	private String mManageCom;
	private String mPrtNo;
	/** 业务操作类 */
	private LCPENoticeResultSet mLCPENoticeResultSet = new LCPENoticeResultSet();
	private LCPENoticeResultSet mLCPENoticeResultSet1 = new LCPENoticeResultSet();
	private LCDiseaseResultSet mLCDiseaseResultSet = new LCDiseaseResultSet();
	private LCPENoticeItemSet mLCPENoticeItemSet = new LCPENoticeItemSet();
	private LCPENoticeItemSet mLCPENoticeItemSet1 = new LCPENoticeItemSet();
	private LCPENoticeItemSet mLCPENoticeItemSet2 = new LCPENoticeItemSet();
	private LCPENoticeSet mLCPENoticeSet = new LCPENoticeSet();
	private LCPENoticeSet mLCPENoticeSet1 = new LCPENoticeSet();
	private LCPENoticeSet mLCPENoticeSet2 = new LCPENoticeSet();
	private TransferData mTransferData = new TransferData();
	private LWMissionSchema mInitLWMissionSchema = new LWMissionSchema();
	/** 业务数据 */
	private String mName = "";
	private String mDelSql = "";
	private String mUpDateSql = "";
	private String mCustomerNo = "";
	private String mPrtSeq = "";
	private String mContNo = "";

	public GrpDisDesbBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		logger.debug("Operate==" + cOperate);
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		logger.debug("After getinputdata");

		if (!checkData()) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("After dealData！");
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("After prepareOutputData");

		logger.debug("Start DisDesbBL Submit...");

		PubSubmit tSubmit = new PubSubmit();

		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);

			CError tError = new CError();
			tError.moduleName = "GrpUWAutoChkBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		logger.debug("DisDesbBL end");

		return true;
	}

	/**
	 * prepareOutputData
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();
		// map.put(mDelSql, "DELETE");
		// if (mLCPENoticeResultSet.size() != 0) {
		// map.put(mLCPENoticeResultSet, "INSERT");
		// }
		if (mInitLWMissionSchema != null) {
			map.put(mInitLWMissionSchema, "UPDATE");
		}
		map.put(mDelSql, "DELETE");
		map.put(mLCPENoticeResultSet1, "INSERT");
		map.put(mLCPENoticeItemSet2, "UPDATE");
		map.put(mLCPENoticeSet2, "UPDATE");
		map.put(mUpDateSql, "UPDATE");

		mResult.add(map);

		return true;
	}

	/**
	 * dealData 业务逻辑处理
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		logger.debug("come in  dealData()");
		// int tDisSerialNo;
		// LCDiseaseResultSchema tLCDiseaseResultSchema;
		// LCDiseaseResultDB tLCDiseaseResultDB = new LCDiseaseResultDB();
		// tLCDiseaseResultDB.setGrpContNo(mLCPENoticeResultSet.get(1).getGrpContNo());
		// tLCDiseaseResultDB.setCustomerNo(mLCPENoticeResultSet.get(1).
		// getCustomerNo());
		// LCDiseaseResultSet tLCDiseaseResultSet = tLCDiseaseResultDB.query();
		// if (tLCDiseaseResultSet.size() == 0)
		// {
		// tDisSerialNo = 0;
		// }
		// else
		// {
		// tDisSerialNo = tLCDiseaseResultSet.size() + 1;
		// }
		// //先删除已经存过的数据
		logger.debug("mLCPENoticeResultSet.get(1).getProposalContNo()******"
						+ mLCPENoticeResultSet.get(1).getProposalContNo());
		mDelSql = "delete from lcpenoticeresult where 1=1 "
				+ " and ProposalContNo= '"
				+ mLCPENoticeResultSet.get(1).getProposalContNo() + "'"
				+ " and prtseq = '" + mLCPENoticeResultSet.get(1).getPrtSeq()
				+ "'";

		int tSerialNo = 0;
		for (int i = 1; i <= mLCPENoticeResultSet.size(); i++) {
			logger.debug("---mLCPENoticeResultSet.size():"
					+ mLCPENoticeResultSet.size());
			mLCPENoticeResultSet.get(i).setName(mName);
			mLCPENoticeResultSet.get(i).setSerialNo("" + tSerialNo);
			mLCPENoticeResultSet.get(i).setOperator(mOperator);
			mLCPENoticeResultSet.get(i).setCustomerNo(mCustomerNo);
			mLCPENoticeResultSet.get(i).setMakeDate(PubFun.getCurrentDate());
			mLCPENoticeResultSet.get(i).setMakeTime(PubFun.getCurrentTime());
			mLCPENoticeResultSet.get(i).setModifyDate(PubFun.getCurrentDate());
			mLCPENoticeResultSet.get(i).setModifyTime(PubFun.getCurrentTime());
			tSerialNo++;

			// tLCDiseaseResultSchema = new LCDiseaseResultSchema();
			// tLCDiseaseResultSchema.setSerialNo("" + tSerialNo);
			// tLCDiseaseResultSchema.setContNo(mLCPENoticeResultSet.get(i).
			// getContNo());
			// tLCDiseaseResultSchema.setProposalContNo(mLCPENoticeResultSet.get(i).
			// getContNo());
			// tLCDiseaseResultSchema.setName(mName);
			// tLCDiseaseResultSchema.setCustomerNo(mLCPENoticeResultSet.get(i).
			// getCustomerNo());
			// tLCDiseaseResultSchema.setDisDesb(mLCPENoticeResultSet.get(i).
			// getDisDesb());
			// tLCDiseaseResultSchema.setDisResult(mLCPENoticeResultSet.get(i).
			// getDisResult());
			// tLCDiseaseResultSchema.setDiseaseSource("2"); //体检结果分析
			// tLCDiseaseResultSchema.setICDCode(mLCPENoticeResultSet.get(i).
			// getICDCode());
			// tLCDiseaseResultSchema.setMakeDate(PubFun.getCurrentDate());
			// tLCDiseaseResultSchema.setMakeTime(PubFun.getCurrentTime());
			// tLCDiseaseResultSchema.setModifyDate(PubFun.getCurrentDate());
			// tLCDiseaseResultSchema.setModifyTime(PubFun.getCurrentTime());
			// tLCDiseaseResultSchema.setOperator(mOperator);
			//
			// mLCDiseaseResultSet.add(tLCDiseaseResultSchema);
			mLCPENoticeResultSet1.add(mLCPENoticeResultSet.get(i));
		}

		LCPENoticeItemDB tLCPENoticeItemDB;

		for (int i = 1; i <= mLCPENoticeItemSet.size(); i++) {
			tLCPENoticeItemDB = new LCPENoticeItemDB();
			tLCPENoticeItemDB.setContNo(mLCPENoticeItemSet.get(i).getContNo());
			tLCPENoticeItemDB.setPrtSeq(mLCPENoticeItemSet.get(i).getPrtSeq());
			tLCPENoticeItemDB.setPEItemCode(mLCPENoticeItemSet.get(i)
					.getPEItemCode());
			mLCPENoticeItemSet1 = tLCPENoticeItemDB.query();
			for (int j = 1; j <= mLCPENoticeItemSet1.size(); j++) {
				if (mLCPENoticeItemSet1.size() != 0) {
					mLCPENoticeItemSet1.get(j).setPEItemResult(
							mLCPENoticeItemSet.get(i).getPEItemResult());
					if (mLCPENoticeItemSet.get(i).getFreePE() != null
							&& !mLCPENoticeItemSet.get(i).getFreePE()
									.equals("")
							&& !mLCPENoticeItemSet.get(i).getFreePE().trim()
									.equals("N")) {
						mLCPENoticeItemSet1.get(j).setFreePE(
								mLCPENoticeItemSet.get(i).getFreePE()
										.substring(0, 1));
					} else {
						mLCPENoticeItemSet1.get(j).setFreePE("");
					}
				}
				mLCPENoticeItemSet2.add(mLCPENoticeItemSet1.get(j));
			}
		}

		LCPENoticeDB tLCPENoticeDB = new LCPENoticeDB();
		for (int i = 1; i <= mLCPENoticeSet.size(); i++) {
			tLCPENoticeDB.setContNo(mLCPENoticeSet.get(i).getContNo());
			tLCPENoticeDB.setPrtSeq(mLCPENoticeSet.get(i).getPrtSeq());
			tLCPENoticeDB.setCustomerNo(mLCPENoticeSet.get(i).getCustomerNo());
			mLCPENoticeSet1 = tLCPENoticeDB.query();

			if (mLCPENoticeSet1.size() != 0) {

				mLCPENoticeSet1.get(1).setModifyDate(PubFun.getCurrentDate());
				mLCPENoticeSet1.get(1).setModifyTime(PubFun.getCurrentTime());
				mLCPENoticeSet1.get(1).setPEResult(
						mLCPENoticeSet.get(i).getPEResult());
				mLCPENoticeSet1.get(1).setPEAddress(
						mLCPENoticeSet.get(i).getPEAddress());
				mLCPENoticeSet1.get(1).setPEDoctor(
						mLCPENoticeSet.get(i).getPEDoctor());
				mLCPENoticeSet1.get(1).setPEDate(
						mLCPENoticeSet.get(i).getPEDate());
				mLCPENoticeSet1.get(1).setREPEDate(
						mLCPENoticeSet.get(i).getREPEDate());

			}
			mLCPENoticeSet2.add(mLCPENoticeSet1.get(1));
		}
		// 修改打印管理表打印状态标志 2表示回复
		mUpDateSql = "update loprtmanager set stateflag ='2' where prtseq='"
				+ mPrtSeq + "'";
		if (dealActivityStatus() == false) {
			return false;
		}
		return true;
	}

	/**
	 * checkData 数据校验
	 * 
	 * @return boolean
	 */
	private boolean checkData() {
		LDPersonDB tLDPersonDB = new LDPersonDB();
		LDPersonSchema tLDPersonSchema = new LDPersonSchema();
		tLDPersonDB.setCustomerNo(mCustomerNo);
		if (!tLDPersonDB.getInfo()) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "DisDesbBL";
			tError.functionName = "checkData";
			tError.errorMessage = "客户信息查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		tLDPersonSchema = tLDPersonDB.getSchema();
		mName = tLDPersonSchema.getName();
		logger.debug("---mName" + mName);
		return true;
	}

	/**
	 * getInputData
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {
		// 公用变量
		tGI = (GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0);

		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mLCPENoticeResultSet = (LCPENoticeResultSet) mTransferData
				.getValueByName("LCPENoticeResultSet");

		mLCPENoticeItemSet = (LCPENoticeItemSet) mTransferData
				.getValueByName("LCPENoticeItemSet");
		mLCPENoticeSet = (LCPENoticeSet) mTransferData
				.getValueByName("LCPENoticeSet");

		mCustomerNo = (String) mTransferData.getValueByName("CustomerNo");
		mContNo = (String) mTransferData.getValueByName("ContNo");
		mPrtSeq = (String) mTransferData.getValueByName("PrtSeq");
		mPrtNo = (String) mTransferData.getValueByName("PrtNo");
		if (mContNo == null || mContNo.equals("")) {
			CError tError = new CError();
			tError.moduleName = "GrpDisDesbBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据ContNo失败!";
			this.mErrors.addOneError(tError);
			return false;

		}

		mOperator = tGI.Operator;

		if (mLCPENoticeSet == null || mLCPENoticeSet.size() <= 0) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "GrpDisDesbBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据Operator失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (mOperator == null || mOperator.length() <= 0) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "GrpDisDesbBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据Operator失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 取得体检信息
		if (mLCPENoticeItemSet == null || mLCPENoticeItemSet.size() <= 0) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "GrpDisDesbBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输数据LCPENoticeItemSet失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 取得疾病信息
		if (mLCPENoticeResultSet == null || mLCPENoticeResultSet.size() <= 0) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "GrpDisDesbBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输数据LCPENoticeResultSet失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	private boolean dealActivityStatus() {
		String tStr = "Select count(*) from LWMission where MissionProp1 = '"
				+ mPrtNo
				+ "'"
				+ "and ActivityID in ('0000002111','0000002106','0000002114','0000002311','0000002306','0000002314')";
		String tReSult = new String();
		ExeSQL tExeSQL = new ExeSQL();
		tReSult = tExeSQL.getOneValue(tStr);
		if (tExeSQL.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tExeSQL.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWTakeBackAutoHealthAfterEndService";
			tError.functionName = "prepareMission";
			tError.errorMessage = "执行SQL语句：" + tStr + "失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		tStr = " select count(*) from loprtmanager where code ='G04' and (stateflag='1' or stateflag='0') and standbyflag2='"
				+ mPrtNo + "'";

		String count = tExeSQL.getOneValue(tStr);
		if (Integer.parseInt(tReSult) > 0 || Integer.parseInt(count) > 0) { // 处于核保未回复状态,不用修改承保人工核保的起始节点状
			mInitLWMissionSchema = null;
		} else {
			LWMissionSet tLWMissionSet = new LWMissionSet();
			LWMissionDB tLWMissionDB = new LWMissionDB();
			tStr = "Select * from LWMission where MissionProp1 = '" + mPrtNo
					+ "'" + "and ActivityID = '0000002004'";

			tLWMissionSet = tLWMissionDB.executeQuery(tStr);
			if (tLWMissionSet == null || tLWMissionSet.size() != 1) {
				return false;
			}
			mInitLWMissionSchema = tLWMissionSet.get(1);
			mInitLWMissionSchema.setActivityStatus("3");
		}
		return true;
	}

	/**
	 * 返回结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

}
