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
 * @date     : 2006-01-18
 * @direction: 保全抽检修改工作流
 ******************************************************************************/


import com.sinosoft.lis.db.LPSpotCheckTrackDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LPSpotCheckTrackSchema;
import com.sinosoft.lis.vschema.LPSpotCheckTrackSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

// ******************************************************************************

public class PEdorSpotCheckModifyBL {
private static Logger logger = Logger.getLogger(PEdorSpotCheckModifyBL.class);
	// public PEdorSpotCheckModifyBL() { }

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
	private String rModifyRemark;
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
		// logger.debug("\t@> PEdorSpotCheckModifyBL.submitData() 开始");

		// 接收参数
		if (cInputData == null) {
			CError.buildErr(this, "无法获取提交的数据, 请确认数据不为空！");
			logger.debug("\t@> PEdorSpotCheckModifyBL.submitData() : InputData = null ！");
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
		if (!dealData())
			return false;
		if (!prepareOutputData())
			return false;
		// 垃圾处理
		collectGarbage();

		// logger.debug("\t@> PEdorSpotCheckModifyBL.submitData() 成功");
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
		// logger.debug("\t@> PEdorSpotCheckModifyBL.getInputData() 开始");
		// GlobalInput
		rGlobalInput = new GlobalInput();
		rGlobalInput = (GlobalInput) rInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (rGlobalInput == null) {
			CError.buildErr(this, "无法获取用户登录机构信息！");
			logger.debug("\t@> PEdorSpotCheckModifyBL.getInputData() : 无法获取 GlobalInput 数据！");
			return false;
		}
		// TransferData
		rTransferData = new TransferData();
		rTransferData = (TransferData) rInputData.getObjectByObjectName(
				"TransferData", 0);
		if (rTransferData == null) {
			CError.buildErr(this, "无法获取传输数据信息！");
			logger.debug("\t@> PEdorSpotCheckModifyBL.getInputData() : 无法获取 TransferData 数据！");
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
		// ModifyRemark()
		rModifyRemark = (String) rTransferData.getValueByName("ModifyRemark");
		// return to submitData
		// logger.debug("\t@> PEdorSpotCheckModifyBL.getInputData() 成功");
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
		// logger.debug("\t@> PEdorSpotCheckModifyBL.checkData() 开始");
		// 如果并不处于抽检修改状态, 则不允许下修改结论
		String QuerySQL = "select ActivityStatus " + "from LWMission "
				+ "where ActivityID = '0000000081' " + "and MissionID = '?rMissionID?' " + "and MissionProp1 = '?rEdorAcceptNo?' ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(QuerySQL);
		sqlbv.put("rMissionID", rMissionID);
		sqlbv.put("rEdorAcceptNo", rEdorAcceptNo);
		ExeSQL tExeSQL = new ExeSQL();
		String sCurrentStatus = tExeSQL.getOneValue(sqlbv);
		// 垃圾处理
		tExeSQL = null;
		if (!sCurrentStatus.trim().equals("2")) {
			CError.buildErr(this, "当前项目并不处于抽检修改状态，不允许下修改结论！");
			return false;
		}
		// logger.debug("\t@> PEdorSpotCheckModifyBL.checkData() 成功");
		return true;
	} // function checkData end

	// ==========================================================================

	/**
	 * 本类的核心业务处理过程
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean dealData() {
		// logger.debug("\t@> PEdorSpotCheckModifyBL.dealData() 开始");
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
			logger.debug("\t@> PEdorSpotCheckModifyBL.dealData() : 执行 SQL 查询出错");
			logger.debug("\t                                     : 错误原因 : "
							+ tLPSpotCheckTrackDB.mErrors.getFirstError());
			CError.buildErr(this, "查询保全抽检轨迹表失败！");
			return false;
		}
		if (tLPSpotCheckTrackSet.size() <= 0) {
			logger.debug("\t@> PEdorSpotCheckModifyBL.dealData() : 执行 SQL 查询出错");
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
		tLPSpotCheckTrackSchema.setModifyEndDate(sCurrentDate);
		// 这里只有一个修改结论: EdorSpotModifyFlag: 已修改处理完毕
		tLPSpotCheckTrackSchema.setModifyFlag("1");
		tLPSpotCheckTrackSchema.setModifyOperator(rGlobalInput.Operator);
		if (rModifyRemark != null && !rModifyRemark.trim().equals(""))
			tLPSpotCheckTrackSchema.setModifyRemark(rModifyRemark.trim());
		// 常规字段赋值
		tLPSpotCheckTrackSchema.setModifyDate(sCurrentDate);
		tLPSpotCheckTrackSchema.setModifyTime(sCurrentTime);
		// 放入 Map, 提交数据库变更
		rMap = new MMap();
		rMap.put(tLPSpotCheckTrackSchema, "UPDATE");
		// 创建一条新的抽检轨迹
		LPSpotCheckTrackSchema tNextLPSpotCheckTrackSchema = new LPSpotCheckTrackSchema();
		tNextLPSpotCheckTrackSchema.setEdorAcceptNo(rEdorAcceptNo);
		tNextLPSpotCheckTrackSchema.setCheckTimes(tLPSpotCheckTrackSchema
				.getCheckTimes() + 1);
		tNextLPSpotCheckTrackSchema.setCheckStartDate(sCurrentDate);
		tNextLPSpotCheckTrackSchema.setOperator(rGlobalInput.Operator);
		tNextLPSpotCheckTrackSchema.setMakeDate(sCurrentDate);
		tNextLPSpotCheckTrackSchema.setMakeTime(sCurrentTime);
		tNextLPSpotCheckTrackSchema.setModifyDate(sCurrentDate);
		tNextLPSpotCheckTrackSchema.setModifyTime(sCurrentTime);
		rMap.put(tNextLPSpotCheckTrackSchema, "INSERT");
		// 垃圾处理
		tLPSpotCheckTrackSchema = null;
		tNextLPSpotCheckTrackSchema = null;
		// 更新 LWMission 状态
		String UpdateSQL = "update LWMission " + "set ActivityStatus = '3' "
				+ "where ActivityID = '0000000081' " + "and MissionID = '?rMissionID?' " + "and MissionProp1 = '?rEdorAcceptNo?' ";
		SQLwithBindVariables sbv=new SQLwithBindVariables();
		sbv.sql(UpdateSQL);
		sbv.put("rMissionID", rMissionID);
		sbv.put("rEdorAcceptNo", rEdorAcceptNo);
		rMap.put(sbv, "UPDATE");
		// return to submitData
		// logger.debug("\t@> PEdorSpotCheckModifyBL.dealData() 成功");
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


} // class PEdorSpotCheckModifyBL end
