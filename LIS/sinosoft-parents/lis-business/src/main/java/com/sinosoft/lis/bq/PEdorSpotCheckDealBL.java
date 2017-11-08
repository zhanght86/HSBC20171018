package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : 辛玉强 <XinYQ@sinosoft.com.cn>
 * @version  : 1.00
 * @date     : 2005-12-31
 * @direction: 保全抽检查询待抽检到抽检工作流
 ******************************************************************************/


import com.sinosoft.lis.db.LPSpotCheckTrackDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LPSpotCheckTrackSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vschema.LPSpotCheckTrackSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

// ******************************************************************************

public class PEdorSpotCheckDealBL {
private static Logger logger = Logger.getLogger(PEdorSpotCheckDealBL.class);
	// public PEdorSpotCheckDealBL() { }

	// ==========================================================================

	// 错误处理类, 使用 CError.buildErr 名称、作用域不可更改
	public CErrors mErrors = new CErrors();
	// 输入数据
	private VData rInputData;
	private String rOperation;
	// 全局变量
	private GlobalInput rGlobalInput;
	private TransferData rTransferData;
	// 业务数据
	private String rMissionID;
	private String rSubMissionID;
	private String rEdorAcceptNo;
	private String rCheckFlag;
	private String rDisqualifyReason;
	private String rCheckRemark;
	// 输出数据
	private MMap rMap;
	private VData rResultData;

	// ==========================================================================

	/**
	 * 外部调用本类的业务处理接口
	 * 
	 * @param VData
	 * @param String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// logger.debug("\t@> PEdorSpotCheckDealBL.submitData() 开始");

		// 接收参数
		if (cInputData == null) {
			CError.buildErr(this, "无法获取提交的数据, 请确认数据不为空！");
			logger.debug("\t@> PEdorSpotCheckDealBL.submitData() : InputData = null ！");
			return false;
		} else {
			rInputData = new VData();
			rInputData = (VData) cInputData.clone();
		}
		rOperation = (cOperate != null) ? cOperate.trim() : "";

		// 业务处理
		if (!getInputData())
			return false;
		if (!checkData())
			return false;
		if (!prepareData())
			return false;
		if (!dealData())
			return false;
		if (!prepareOutputData())
			return false;
		// 垃圾处理
		collectGarbage();

		// logger.debug("\t@> PEdorSpotCheckDealBL.submitData() 成功");
		return true;
	} // function submitData end

	// ==========================================================================

	/**
	 * 获取外部传入数据和校验必录字段的合法性
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean getInputData() {
		// logger.debug("\t@> PEdorSpotCheckDealBL.getInputData() 开始");
		// GlobalInput
		rGlobalInput = new GlobalInput();
		rGlobalInput = (GlobalInput) rInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (rGlobalInput == null) {
			CError.buildErr(this, "无法获取用户登录机构信息！");
			logger.debug("\t@> PEdorSpotCheckDealBL.getInputData() : 无法获取 GlobalInput 数据！");
			return false;
		}
		// TransferData
		rTransferData = new TransferData();
		rTransferData = (TransferData) rInputData.getObjectByObjectName(
				"TransferData", 0);
		if (rTransferData == null) {
			CError.buildErr(this, "无法获取传输数据信息！");
			logger.debug("\t@> PEdorSpotCheckDealBL.getInputData() : 无法获取 TransferData 数据！");
			return false;
		}
		// MissionID 和 SubMissionID
		rMissionID = (String) rTransferData.getValueByName("MissionID");
		rSubMissionID = (String) rTransferData.getValueByName("SubMissionID");
		if (rMissionID == null || rMissionID.trim().equals("")
				|| rSubMissionID == null || rSubMissionID.trim().equals("")) {
			CError.buildErr(this, "无法获取工作流节点信息！");
			return false;
		} else {
			rMissionID = rMissionID.trim();
			rSubMissionID = rSubMissionID.trim();
		}
		// EdorAcceptNo
		rEdorAcceptNo = (String) rTransferData.getValueByName("EdorAcceptNo");
		if (rEdorAcceptNo == null || rEdorAcceptNo.trim().equals("")) {
			CError.buildErr(this, "无法获取保全受理号信息！");
			return false;
		} else
			rEdorAcceptNo = rEdorAcceptNo.trim();
		// CheckFlag
		rCheckFlag = (String) rTransferData.getValueByName("CheckFlag");
		if (rCheckFlag == null || rCheckFlag.trim().equals("")) {
			CError.buildErr(this, "无法获取抽检结论信息！");
			return false;
		} else
			rCheckFlag = rCheckFlag.trim();
		// DisqualifyReason
		rDisqualifyReason = (String) rTransferData
				.getValueByName("DisqualifyReason");
		// CheckRemark()
		rCheckRemark = (String) rTransferData.getValueByName("CheckRemark");
		// return to submitData
		// logger.debug("\t@> PEdorSpotCheckDealBL.getInputData() 成功");
		return true;
	} // function getInputData end

	// ==========================================================================

	/**
	 * 根据传入的数据进行业务逻辑层的合法性校验
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean checkData() {
		// logger.debug("\t@> PEdorSpotCheckDealBL.checkData() 开始");
		// 如果抽检结论为不合格, 则必须录入原因
		if (rCheckFlag.equals("2")
				&& (rDisqualifyReason == null || rDisqualifyReason.trim()
						.equals(""))) {
			CError.buildErr(this, "抽检结论为不合格，必须录入不合格原因！");
			return false;
		}
		// 如果处于抽检修改状态, 则不允许再次下抽检结论
		String QuerySQL = "select ActivityStatus " + "from LWMission "
				+ "where ActivityID = '0000000081' " + "and MissionID = '?rMissionID?' " + "and SubMissionID = '?rSubMissionID?' " + "and MissionProp1 = '?rEdorAcceptNo?' ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(QuerySQL);
		sqlbv.put("rMissionID", rMissionID);
		sqlbv.put("rSubMissionID", rSubMissionID);
		sqlbv.put("rEdorAcceptNo", rEdorAcceptNo);
		ExeSQL tExeSQL = new ExeSQL();
		String sCurrentStatus = tExeSQL.getOneValue(sqlbv);
		// 垃圾处理
		tExeSQL = null;
		if (sCurrentStatus.trim().equals("2")) {
			CError.buildErr(this, "当前项目处于抽检修改状态, 不允许再次下抽检结论！");
			return false;
		}
		// logger.debug("\t@> PEdorSpotCheckDealBL.checkData() 成功");
		return true;
	} // function checkData end

	// ==========================================================================

	/**
	 * 准备生成下一节点的数据
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean prepareData() {
		// logger.debug("\t@> PEdorSpotCheckDealBL.prepareData() 开始");
		// LWMissionDB
		LWMissionDB tLWMissionDB = new LWMissionDB();
		tLWMissionDB.setMissionID(rMissionID);
		tLWMissionDB.setSubMissionID(rSubMissionID);
		tLWMissionDB.setActivityID("0000000081");
		tLWMissionDB.setMissionProp1(rEdorAcceptNo);
		// LWMissionSet
		LWMissionSet tLWMissionSet = new LWMissionSet();
		// LWMissionSchema
		LWMissionSchema tLWMissionSchema = new LWMissionSchema();
		// 查询获取 Schema
		try {
			tLWMissionSet = tLWMissionDB.query();
		} catch (Exception ex) {
			logger.debug("\t@> PEdorSpotCheckDealBL.prepareData() : 执行 SQL 查询出错");
			logger.debug("\t                                      : 错误原因 : "
							+ tLWMissionDB.mErrors.getFirstError());
			CError.buildErr(this, "查询工作流任务表失败！");
			return false;
		}
		if (tLWMissionSet.size() <= 0) {
			logger.debug("\t@> PEdorSpotCheckDealBL.prepareData() : 执行 SQL 查询出错");
			CError.buildErr(this, "保全受理号 " + rEdorAcceptNo + " 在工作流任务表不存在！");
			return false;
		} else
			tLWMissionSchema = tLWMissionSet.get(1);
		// 垃圾处理
		tLWMissionDB = null;
		tLWMissionSet = null;
		// 放入 TransferData
		if (tLWMissionSchema.getMissionProp2() != null
				&& !tLWMissionSchema.getMissionProp2().trim().equals(""))
			rTransferData.setNameAndValue("OtherNo", tLWMissionSchema
					.getMissionProp2());
		if (tLWMissionSchema.getMissionProp3() != null
				&& !tLWMissionSchema.getMissionProp3().trim().equals(""))
			rTransferData.setNameAndValue("OtherNoType", tLWMissionSchema
					.getMissionProp3());
		if (tLWMissionSchema.getMissionProp4() != null
				&& !tLWMissionSchema.getMissionProp4().trim().equals(""))
			rTransferData.setNameAndValue("EdorAppName", tLWMissionSchema
					.getMissionProp4());
		if (tLWMissionSchema.getMissionProp5() != null
				&& !tLWMissionSchema.getMissionProp5().trim().equals(""))
			rTransferData.setNameAndValue("AppType", tLWMissionSchema
					.getMissionProp5());
		if (tLWMissionSchema.getMissionProp7() != null
				&& !tLWMissionSchema.getMissionProp7().trim().equals(""))
			rTransferData.setNameAndValue("ManageCom", tLWMissionSchema
					.getMissionProp7());
		if (tLWMissionSchema.getMissionProp11() != null
				&& !tLWMissionSchema.getMissionProp11().trim().equals(""))
			rTransferData.setNameAndValue("AppntName", tLWMissionSchema
					.getMissionProp11());
		if (tLWMissionSchema.getMissionProp12() != null
				&& !tLWMissionSchema.getMissionProp12().trim().equals(""))
			rTransferData.setNameAndValue("PayToDate", tLWMissionSchema
					.getMissionProp12());
		if (tLWMissionSchema.getMissionProp13() != null
				&& !tLWMissionSchema.getMissionProp13().trim().equals(""))
			rTransferData.setNameAndValue("EdorType", tLWMissionSchema
					.getMissionProp13());
		if (tLWMissionSchema.getMissionProp14() != null
				&& !tLWMissionSchema.getMissionProp14().trim().equals(""))
			rTransferData.setNameAndValue("GetMoney", tLWMissionSchema
					.getMissionProp14());
		if (tLWMissionSchema.getMissionProp15() != null
				&& !tLWMissionSchema.getMissionProp15().trim().equals(""))
			rTransferData.setNameAndValue("EdorValiDate", tLWMissionSchema
					.getMissionProp15());
		if (tLWMissionSchema.getMissionProp16() != null
				&& !tLWMissionSchema.getMissionProp16().trim().equals(""))
			rTransferData.setNameAndValue("Operator", tLWMissionSchema
					.getMissionProp16());
		if (tLWMissionSchema.getMissionProp17() != null
				&& !tLWMissionSchema.getMissionProp17().trim().equals(""))
			rTransferData.setNameAndValue("ApproveOperator", tLWMissionSchema
					.getMissionProp17());
		if (tLWMissionSchema.getMissionProp18() != null
				&& !tLWMissionSchema.getMissionProp18().trim().equals(""))
			rTransferData.setNameAndValue("ApproveDate", tLWMissionSchema
					.getMissionProp18());
		if (tLWMissionSchema.getMissionProp19() != null
				&& !tLWMissionSchema.getMissionProp19().trim().equals(""))
			rTransferData.setNameAndValue("EdorAppDate", tLWMissionSchema
					.getMissionProp19());
		if (tLWMissionSchema.getMissionProp20() != null
				&& !tLWMissionSchema.getMissionProp20().trim().equals(""))
			rTransferData.setNameAndValue("OperateDate", tLWMissionSchema
					.getMissionProp20());
		// 垃圾处理
		tLWMissionSchema = null;
		// return to submitData
		// logger.debug("\t@> PEdorSpotCheckDealBL.prepareData() 成功");
		return true;
	} // function prepareData end

	// ==========================================================================

	/**
	 * 本类的核心业务处理过程
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean dealData() {
		// logger.debug("\t@> PEdorSpotCheckDealBL.dealData() 开始");
		// LPSpotCheckTrackDB
		LPSpotCheckTrackDB tLPSpotCheckTrackDB = new LPSpotCheckTrackDB();
		tLPSpotCheckTrackDB.setEdorAcceptNo(rEdorAcceptNo);
		tLPSpotCheckTrackDB.setCheckTimes(tLPSpotCheckTrackDB.getCount());
		// LPSpotCheckTrackSet
		LPSpotCheckTrackSet tLPSpotCheckTrackSet = new LPSpotCheckTrackSet();
		// LPSpotCheckTrackSchema
		LPSpotCheckTrackSchema tLPSpotCheckTrackSchema = new LPSpotCheckTrackSchema();
		// 查询获取 Schema
		try {
			tLPSpotCheckTrackSet = tLPSpotCheckTrackDB.query();
		} catch (Exception ex) {
			logger.debug("\t@> PEdorSpotCheckDealBL.dealData() : 执行 SQL 查询出错");
			logger.debug("\t                                   : 错误原因 : "
					+ tLPSpotCheckTrackDB.mErrors.getFirstError());
			CError.buildErr(this, "查询保全抽检轨迹表失败！");
			return false;
		}
		if (tLPSpotCheckTrackSet.size() <= 0) {
			logger.debug("\t@> PEdorSpotCheckDealBL.dealData() : 执行 SQL 查询出错");
			CError.buildErr(this, "保全受理号 " + rEdorAcceptNo + " 在抽检轨迹表中不存在！");
			return false;
		} else
			tLPSpotCheckTrackSchema = tLPSpotCheckTrackSet.get(1);
		// 垃圾处理
		tLPSpotCheckTrackDB = null;
		tLPSpotCheckTrackSet = null;
		// 日期和时间
		String sCurrentDate = PubFun.getCurrentDate();
		String sCurrentTime = PubFun.getCurrentTime();
		// 表字段赋值
		tLPSpotCheckTrackSchema.setCheckEndDate(sCurrentDate);
		tLPSpotCheckTrackSchema.setCheckFlag(rCheckFlag);
		tLPSpotCheckTrackSchema.setCheckOperator(rGlobalInput.Operator);
		if (rCheckRemark != null && !rCheckRemark.trim().equals(""))
			tLPSpotCheckTrackSchema.setCheckRemark(rCheckRemark.trim());
		// 如果结论为"不合格", 置修不合格原因和改开始日期
		if (rCheckFlag.equals("2")) {
			tLPSpotCheckTrackSchema.setDisqualifyReason(rDisqualifyReason
					.trim());
			tLPSpotCheckTrackSchema.setModifyStartDate(sCurrentDate);
		}
		// 常规字段赋值
		tLPSpotCheckTrackSchema.setModifyDate(sCurrentDate);
		tLPSpotCheckTrackSchema.setModifyTime(sCurrentTime);
		// 放入 Map, 提交数据库变更
		rMap = new MMap();
		rMap.put(tLPSpotCheckTrackSchema, "UPDATE");
		// 垃圾处理
		tLPSpotCheckTrackSchema = null;
		// return to submitData
		// logger.debug("\t@> PEdorSpotCheckDealBL.dealData() 成功");
		return true;
	} // function dealData end

	// ==========================================================================

	/**
	 * 本类准备提交后台的数据
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		rResultData = new VData();
		rResultData.clear();
		rResultData.add(rMap);
		rResultData.add(rTransferData);
		return true;
	}

	// ==========================================================================

	/**
	 * 返回经过本类处理的数据结果
	 * 
	 * @param null
	 * @return VData
	 */
	public VData getResult() {
		return this.rResultData;
	} // function getResult end

	// ==========================================================================

	/**
	 * 返回传入本类的操作类型
	 * 
	 * @param null
	 * @return String
	 */
	public String getOperation() {
		return this.rOperation;
	} // function getResult end

	// ==========================================================================

	/**
	 * 返回本类运行时产生的错误信息
	 * 
	 * @param null
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return this.mErrors;
	} // function getErrors end

	// ==========================================================================

	/**
	 * 处理本类运行时产生的垃圾
	 * 
	 * @param null
	 */
	private void collectGarbage() {
		if (rInputData != null)
			rInputData = null;
		if (rGlobalInput != null)
			rGlobalInput = null;
		if (rTransferData != null)
			rTransferData = null;
		if (rMap != null)
			rMap = null;
	} // function collectGarbage end

	// ==========================================================================


} // class PEdorSpotCheckDealBL end
