package com.sinosoft.workflow.xb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LWFieldMapDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LWFieldMapSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.lis.vschema.LDCodeSet;
import com.sinosoft.lis.vschema.LWFieldMapSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 时效统计
 * </p>
 * <p>
 * Description: 统计工作流任务完成的时效
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author zhangtao
 * @version 1.0
 */

public class RLDContTimeBL {
private static Logger logger = Logger.getLogger(RLDContTimeBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往后面传输的数据库操作 */
	private MMap map = new MMap();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private TransferData mTransferData = new TransferData();

	private LWMissionSchema mLWMissionSchema = new LWMissionSchema();

	/** 数据操作字符串 */
	private String mOperator;
	private String mManageCom;
	private String mOperate;
	private String mMissionID;
	private String mSubMissionID;
	private String mActivityID;
	private String mBusinessType;
	private String mPrtNo;
	private String mContNo;
	private String mTimeSeq;

	public RLDContTimeBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		if (!getInputData(mInputData, mOperate)) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}

		return true;
	}

	/**
	 * dealData 处理数据
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		// 获取业务类型编码
		if (!getBusinessType())
			return false;
		// 获取工作流任务的进入日期和时间
		if (!getLWMission())
			return false;
		// 获取保单号和印刷号
		if (!getContNo())
			return false;
		// 内部流水号暂时置1
		// 考虑以后扩展任务暂停，工作流任务时效分段保存，内部流水号自增
		mTimeSeq = "1";

		/*
		 * 2005-10-20增加 以下增加对插入《新契约工作时间记录表--LDContTime》数据的控制 控制条件：业务类型《 select *
		 * from LDCode where codetype='busitype'》
		 * 1001-----个险新契约无扫描录入；1002-----个险新契约扫描录入；1003-----个险新契约新单复核
		 * 1004-----个险新契约问题件修改；1005-----个险新契约人工核保；
		 * 1006-----个险新契约生调处理；1007-----个险新契约签发保单；1008-----个险新契约自动核保
		 * 2001-----团险新契约无扫描录入；2002-----团险新契约扫描录入； 2003-----团险新契约新单复核
		 * 2004-----团险新契约问题件修改；2005-----团险新契约人工核保
		 * 其中"1006、1007、1008"不需要实效统计，故不用往LDContTime表插入记录
		 */
		String IsLDContTime = "true";// 用于判断是否需要增加时效统计记录,默认需要
		if (mBusinessType.equals("1006") || mBusinessType.equals("1007")
				|| mBusinessType.equals("1008")) {
			IsLDContTime = "false";
			logger.debug("业务类型：" + mBusinessType + "----节点号："
					+ mSubMissionID);
			logger.debug("不需要往LDContTime表插入记录");
		}
		if (mLWMissionSchema.getInDate() == null
				|| mLWMissionSchema.getInTime() == null) {
			IsLDContTime = "false";
			logger.debug("业务类型：" + mBusinessType + "----节点号："
					+ mSubMissionID);
			logger.debug("不需要往LDContTime表插入记录");
		}

		if (IsLDContTime.equals("true")) {
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" INSERT INTO LDContTime ")
			.append(" ( SERIALNO, ContNo, PrtNo, BusinessType, SubBusinessType, ")
			.append("   TimeSeq, Operator, MakeDate, MakeTime,")
			.append("   BackDate, BackTime ) ")
			.append(" VALUES( '")
			.append("?mSerialNo?").append("', '")
			.append("?mContNo?").append("', '")
			.append("?mPrtNo?").append("', '")
			.append("?mBusinessType?").append("', '")
			.append("?mSubMissionID?").append("', '")
			.append("?mTimeSeq?").append("', '")
			.append("?mOperator?").append("', '")
			.append("?InDate?").append("', '")
			.append("?InTime?").append("', '")
			.append("?CurrentDate?").append("', '")
			.append("?CurrentTime?").append("' )");
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(sbSQL.toString());
			sqlbv.put("mSerialNo", PubFun1.CreateMaxNo("TraceNo", 10));
			sqlbv.put("mContNo", mContNo);
			sqlbv.put("mPrtNo", mPrtNo);
			sqlbv.put("mBusinessType", mBusinessType);
			sqlbv.put("mSubMissionID", mSubMissionID);
			sqlbv.put("mTimeSeq", mTimeSeq);
			sqlbv.put("mOperator", mOperator);
			sqlbv.put("InDate", mLWMissionSchema.getInDate());
			sqlbv.put("InTime", mLWMissionSchema.getInTime());
			sqlbv.put("CurrentDate", PubFun.getCurrentDate());
			sqlbv.put("CurrentTime", PubFun.getCurrentTime());
			
			logger.debug("== LDContTimeBL == sbSQL \n" + sbSQL.toString());
			map.put(sqlbv, "INSERT");
		}
		return true;
	}

	/**
	 * prepareOutputData
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(map);
			mResult.clear();
			mResult.add(map);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDContTimeBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错:" + ex.toString();
			mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * getInputData 得到前台传输的数据
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		// 从输入数据中得到所有对象
		// 获得全局公共数据
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "LDContTimeBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得操作员编码
		mOperator = mGlobalInput.Operator;
		if (mOperator == null || mOperator.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "LDContTimeBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据Operate失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "LDContTimeBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据mManageCom失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null || mMissionID.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "LDContTimeBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输数据MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mSubMissionID = (String) mTransferData.getValueByName("SubMissionID");
		if (mSubMissionID == null || mSubMissionID.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "LDContTimeBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输数据SubMissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mActivityID = (String) mTransferData.getValueByName("ActivityID");
		if (mActivityID == null || mActivityID.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "LDContTimeBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输数据ActivityID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		logger.debug("== LDContTimeBL == mMissionID ====" + mMissionID);
		logger.debug("== LDContTimeBL == mSubMissionID =" + mSubMissionID);
		logger.debug("== LDContTimeBL == mActivityID ===" + mActivityID);
		logger.debug("== LDContTimeBL == mOperator =====" + mOperator);
		logger.debug("== LDContTimeBL == mManageCom ====" + mManageCom);
		logger.debug("== LDContTimeBL == mApproveFlag ====" + (String) mTransferData.getValueByName("ApproveFlag"));

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	private boolean getBusinessType() {
		LDCodeSet tLDCodeSet = new LDCodeSet();
		LDCodeDB tLDCodeDB = new LDCodeDB();
		tLDCodeDB.setCodeType("busitype");
		tLDCodeDB.setOtherSign(mActivityID);
		tLDCodeSet.set(tLDCodeDB.query());
		if (tLDCodeDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLDCodeDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDContTimeBL";
			tError.functionName = "getBusinessType";
			tError.errorMessage = "业务类型查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (tLDCodeSet == null || tLDCodeSet.size() == 0) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLDCodeDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDContTimeBL";
			tError.functionName = "getBusinessType";
			tError.errorMessage = "业务类型查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mBusinessType = tLDCodeSet.get(1).getCode();
		return true;
	}

	private boolean getLWMission() {
		LWMissionDB tLWMissionDB = new LWMissionDB();
		tLWMissionDB.setActivityID(mActivityID);
		tLWMissionDB.setMissionID(mMissionID);
		tLWMissionDB.setSubMissionID(mSubMissionID);
		if (!tLWMissionDB.getInfo()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLWMissionDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDContTimeBL";
			tError.functionName = "getLWMission";
			tError.errorMessage = "工作流任务查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLWMissionSchema.setSchema(tLWMissionDB.getSchema());

		return true;
	}

	private boolean getContNo() {

		// 判断业务类型是团单还是个单
		boolean blLCFlag = true;
		if ("1".equals(mBusinessType.substring(0, 1))) {
			blLCFlag = true; // 个单
		}
		if ("2".equals(mBusinessType.substring(0, 1))) {
			blLCFlag = false; // 团单
		}

		boolean blmPrtNoFlag = false;
		boolean blmContNoFlag = false;

		LWFieldMapSet tLWFieldMapSet = new LWFieldMapSet();
		LWFieldMapDB tLWFieldMapDB = new LWFieldMapDB();
		tLWFieldMapDB.setActivityID(mActivityID);
		tLWFieldMapSet.set(tLWFieldMapDB.query());
		if (tLWFieldMapDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLWFieldMapDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDContTimeBL";
			tError.functionName = "getContNo";
			tError.errorMessage = "工作流任务字段映射查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		LWFieldMapSchema tLWFieldMapSchema = new LWFieldMapSchema();
		for (int i = 1; i <= tLWFieldMapSet.size(); i++) {
			tLWFieldMapSchema.setSchema(tLWFieldMapSet.get(i));

			if (tLWFieldMapSchema.getSourFieldName().equals("PrtNo")) {
				mPrtNo = mLWMissionSchema.getV(tLWFieldMapSchema
						.getDestFieldName());
				blmPrtNoFlag = true;
			}

			if (blLCFlag) {
				// 个单
				if (tLWFieldMapSchema.getSourFieldName().equals("ContNo")) {
					mContNo = mLWMissionSchema.getV(tLWFieldMapSchema
							.getDestFieldName());
					blmContNoFlag = true;
				}
			}

			if (!blLCFlag) {
				// 团单
				if (tLWFieldMapSchema.getSourFieldName().equals("GrpContNo")) {
					mContNo = mLWMissionSchema.getV(tLWFieldMapSchema
							.getDestFieldName());
					blmContNoFlag = true;
				}
			}

			if (blmPrtNoFlag && blmContNoFlag) {
				break;
			}
		}

		// 判断 mContNo mPrtNo,查出双值
		if (!blmPrtNoFlag && !blmContNoFlag) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDContTimeBL";
			tError.functionName = "getContNo";
			tError.errorMessage = "获取保单号和印刷号失败!";
			this.mErrors.addOneError(tError);
			return false;
		} else if (blmPrtNoFlag && blmContNoFlag) {
			// 保单号和印刷号都有 DoNothing
		} else if (blmPrtNoFlag) {
			// 根据印刷号查询保单号
			if (blLCFlag) {
				// 个单
				LCContSet tLCContSet = new LCContSet();
				LCContDB tLCContDB = new LCContDB();
				tLCContDB.setPrtNo(mPrtNo);
				tLCContSet.set(tLCContDB.query());
				if (tLCContDB.mErrors.needDealError()) {
					// @@错误处理
					this.mErrors.copyAllErrors(tLCContDB.mErrors);
					CError tError = new CError();
					tError.moduleName = "LDContTimeBL";
					tError.functionName = "getContNo";
					tError.errorMessage = "合同号查询失败!";
					this.mErrors.addOneError(tError);
					return false;
				}
				mContNo = tLCContSet.get(1).getContNo();
			} else {
				// 团单
				LCGrpContSet tLCGrpContSet = new LCGrpContSet();
				LCGrpContDB tLCGrpContDB = new LCGrpContDB();
				tLCGrpContDB.setPrtNo(mPrtNo);
				tLCGrpContSet.set(tLCGrpContDB.query());
				if (tLCGrpContDB.mErrors.needDealError()) {
					// @@错误处理
					this.mErrors.copyAllErrors(tLCGrpContDB.mErrors);
					CError tError = new CError();
					tError.moduleName = "LDContTimeBL";
					tError.functionName = "getContNo";
					tError.errorMessage = "合同号查询失败!";
					this.mErrors.addOneError(tError);
					return false;
				}
				mContNo = tLCGrpContSet.get(1).getGrpContNo();
			}
		} else if (blmContNoFlag) {
			// 根据保单号查询印刷号
			if (blLCFlag) {
				// 个单
				LCContDB tLCContDB = new LCContDB();
				tLCContDB.setContNo(mContNo);
				if (!tLCContDB.getInfo()) {
					// @@错误处理
					this.mErrors.copyAllErrors(tLCContDB.mErrors);
					CError tError = new CError();
					tError.moduleName = "LDContTimeBL";
					tError.functionName = "getContNo";
					tError.errorMessage = "印刷号查询失败!";
					this.mErrors.addOneError(tError);
					return false;
				}
				mPrtNo = tLCContDB.getPrtNo();
			} else {
				// 团单
				LCGrpContDB tLCGrpContDB = new LCGrpContDB();
				tLCGrpContDB.setGrpContNo(mContNo);
				if (!tLCGrpContDB.getInfo()) {
					// @@错误处理
					this.mErrors.copyAllErrors(tLCGrpContDB.mErrors);
					CError tError = new CError();
					tError.moduleName = "LDContTimeBL";
					tError.functionName = "getContNo";
					tError.errorMessage = "印刷号查询失败!";
					this.mErrors.addOneError(tError);
					return false;
				}
				mPrtNo = tLCGrpContDB.getPrtNo();
			}
		}

		return true;
	}

	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "zhangtao";
		tG.ManageCom = "86110000";

		String sMissionID = "00000000000000005485";
		String sSubMissionID = "1";
		String sActivityID = "0000001098";

		LDCodeSet tLDCodeSet = new LDCodeSet();
		LDCodeDB tLDCodeDB = new LDCodeDB();
		tLDCodeDB.setCodeType("busitype");
		tLDCodeDB.setOtherSign(sActivityID);
		tLDCodeSet.set(tLDCodeDB.query());

		if (tLDCodeSet.size() == 1) {
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("MissionID", sMissionID);
			tTransferData.setNameAndValue("SubMissionID", sSubMissionID);
			tTransferData.setNameAndValue("ActivityID", sActivityID);

			VData tVData = new VData();
			tVData.add(tTransferData);
			tVData.add(tG);

			RLDContTimeBL tRLDContTimeBL = new RLDContTimeBL();
			if (tRLDContTimeBL.submitData(tVData, "")) {
				VData tResultData = tRLDContTimeBL.getResult();
				MMap tMap = (MMap) tResultData.getObjectByObjectName("MMap", 0);
				logger.debug("test end");
			}
		}
	}

}
