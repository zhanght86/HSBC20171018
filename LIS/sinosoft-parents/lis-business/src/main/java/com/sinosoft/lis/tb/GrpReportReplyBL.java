package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCRReportSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: BL层业务逻辑处理类
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
 * @date 2005-08-18
 */

public class GrpReportReplyBL {
private static Logger logger = Logger.getLogger(GrpReportReplyBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;
	private String mContNo;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private String mUpDateSql = "";
	private MMap map = new MMap();

	/** 业务处理相关变量 */
	private LCRReportSchema mLCRReportSchema = new LCRReportSchema();
	private LWMissionSchema mInitLWMissionSchema = new LWMissionSchema(); // 保全人工核保工作流起始节点

	public boolean submitData(VData cInputData, String cOperate) {
		// 将传入的数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		logger.debug("now in ContBL submit");
		// 将外部传入的数据分解到本类的属性中，准备处理
		if (this.getInputData() == false) {
			return false;
		}
		logger.debug("---getInputData---");
		// 校验传入的数据

		if (this.checkData() == false) {
			return false;
		}
		logger.debug("---checkData---");
		// }

		// 根据业务逻辑对数据进行处理

		if (this.dealData() == false) {
			return false;
		}

		// 装配处理好的数据，准备给后台进行保存
		this.prepareOutputData();
		logger.debug("---prepareOutputData---");

		// 数据提交、保存

		PubSubmit tPubSubmit = new PubSubmit();
		logger.debug("Start tPRnewManualDunBLS Submit...");

		if (!tPubSubmit.submitData(mResult, "INSERT")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);

			CError tError = new CError();
			tError.moduleName = "ContBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";

			this.mErrors.addOneError(tError);
			return false;
		}

		logger.debug("---commitData---");

		return true;
	}

	private boolean checkData() {

		return true;
	}

	private boolean dealData() {

		LCRReportSchema tLCRReportSchema = new LCRReportSchema();
		tLCRReportSchema.setSchema(mLCRReportSchema);
		tLCRReportSchema.setOperator(mGlobalInput.Operator);
		tLCRReportSchema.setReplyOperator(mGlobalInput.Operator);
		tLCRReportSchema.setReplyDate(PubFun.getCurrentDate());
		tLCRReportSchema.setReplyTime(PubFun.getCurrentTime());
		tLCRReportSchema.setModifyDate(PubFun.getCurrentDate());
		tLCRReportSchema.setModifyTime(PubFun.getCurrentTime());
		mUpDateSql = "update loprtmanager set stateflag ='2' where prtseq='"
				+ mLCRReportSchema.getPrtSeq() + "'";
		map.put(tLCRReportSchema, "UPDATE");
		map.put(mUpDateSql, "UPDATE");
		if (dealActivityStatus() == false) {
			return false;
		}

		return true;
	}

	private boolean getInputData() {
		try {
			// 全局变量
			mGlobalInput.setSchema((GlobalInput) mInputData
					.getObjectByObjectName("GlobalInput", 0));

			this.mLCRReportSchema = (LCRReportSchema) mInputData
					.getObjectByObjectName("LCRReportSchema", 0);

			if (mGlobalInput == null) {
				// @@错误处理
				// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
				CError tError = new CError();
				tError.moduleName = "TbWorkFlowBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "前台传输全局公共数据失败!";
				this.mErrors.addOneError(tError);

				return false;
			}
		} catch (Exception ex) {
			CError tError = new CError();
			tError.moduleName = "ContBL";
			tError.functionName = "checkData";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			return false;

		}
		return true;
	}

	private boolean prepareOutputData() {
		try {
			if (mInitLWMissionSchema != null) {
				map.put(mInitLWMissionSchema, "UPDATE");
			}

			mResult.clear();
			mResult.add(map);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDUWUserBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;

	}

	private boolean dealActivityStatus() {
		String tStr = "Select count(*) from LWMission where MissionProp1 = '"
				+ mLCRReportSchema.getGrpContNo()
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

		tStr = " select count(*) from loprtmanager where code ='G03' and (stateflag='1' or stateflag='0') and standbyflag3='"
				+ mLCRReportSchema.getGrpContNo() + "'";

		String count = tExeSQL.getOneValue(tStr);
		if (Integer.parseInt(tReSult) > 0 || Integer.parseInt(count) > 0) { // 处于核保未回复状态,不用修改承保人工核保的起始节点状
			mInitLWMissionSchema = null;
		} else {
			LWMissionSet tLWMissionSet = new LWMissionSet();
			LWMissionDB tLWMissionDB = new LWMissionDB();
			tStr = "Select * from LWMission where MissionProp1 = '"
					+ mLCRReportSchema.getGrpContNo() + "'"
					+ "and ActivityID = '0000002004'";

			tLWMissionSet = tLWMissionDB.executeQuery(tStr);
			if (tLWMissionSet == null || tLWMissionSet.size() != 1) {
				return false;
			}
			mInitLWMissionSchema = tLWMissionSet.get(1);
			mInitLWMissionSchema.setActivityStatus("3");
		}
		return true;
	}

}
