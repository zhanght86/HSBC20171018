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
 * @version  : 1.00, 1.01, 1.02
 * @date     : 2005-12-31, 2006-02-22, 2006-08-17
 * @direction: 保全抽检查询待抽检到抽检工作流
 ******************************************************************************/


import java.util.Random;

import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflow.bq.EdorWorkFlowUI;

// ******************************************************************************

public class PEdorSpotCheckQueryBL {
private static Logger logger = Logger.getLogger(PEdorSpotCheckQueryBL.class);
	// public PEdorSpotCheckQueryBL() {}

	// ==========================================================================

	// 错误处理类, 使用 CError.buildErr 名称、作用域不可更改
	public CErrors mErrors = new CErrors();
	// 输入数据
	private VData rInputData;
	private String rOperation;
	private GlobalInput rGlobalInput;
	private TransferData rTransferData;
	private LWMissionSet rLWMissionSet;
	private float rProportion = 0.0F;
	// 输出数据
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
		// logger.debug("\t@> PEdorSpotCheckQueryBL.submitData() 开始");

		// 接收参数
		if (cInputData == null) {
			logger.debug("\t@> PEdorSpotCheckQueryBL.submitData() : 无法获取 InputData 数据！");
			CError.buildErr(this, "无法获取提交的数据, 请确认数据不为空！");
			return false;
		} else {
			rInputData = new VData();
			rInputData = (VData) cInputData.clone();
		}
		rOperation = (cOperate != null) ? cOperate.trim() : "";

		// ----------------------------------------------------------------------

		// 业务处理
		if (!getInputData())
			return false;
		if (!prepareData())
			return false;
		if (!dealData())
			return false;
		// 垃圾处理
		collectGarbage();

		// logger.debug("\t@> PEdorSpotCheckQueryBL.submitData() 结束");
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
		// logger.debug("\t@> PEdorSpotCheckQueryBL.getInputData() 开始");

		// GlobalInput
		rGlobalInput = new GlobalInput();
		rGlobalInput = (GlobalInput) rInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (rGlobalInput == null) {
			CError.buildErr(this, "无法获取用户登录机构信息！");
			logger.debug("\t@> PEdorSpotCheckQueryBL.getInputData() : 无法获取 GlobalInput 数据！");
			return false;
		}

		// TransferData
		rTransferData = new TransferData();
		rTransferData = (TransferData) rInputData.getObjectByObjectName(
				"TransferData", 0);
		if (rTransferData == null) {
			CError.buildErr(this, "无法获取传输数据信息！");
			logger.debug("\t@> PEdorSpotCheckQueryBL.getInputData() : 无法获取 TransferData 数据！");
			return false;
		}

		// ----------------------------------------------------------------------

		// 抽检比例
		String sProportion = (String) rTransferData
				.getValueByName("Proportion");
		if (sProportion == null || sProportion.trim().equals("")) {
			CError.buildErr(this, "无法获取抽检比例信息！");
			return false;
		} else {
			sProportion = sProportion.trim();
			try {
				rProportion = Float.parseFloat(sProportion);
			} catch (Exception ex) {
				CError.buildErr(this, "抽检比例不是一个有效的数字！");
				return false;
			}
			if (!(rProportion >= 0 && rProportion <= 100)) {
				CError.buildErr(this, "抽检比例的取值范围是大于等于 0 且小于等于 100！");
				return false;
			}
		}

		// logger.debug("\t@> PEdorSpotCheckQueryBL.getInputData() 结束");
		return true;
	} // function getInputData end

	// ==========================================================================

	/**
	 * 按照提交的条件进行查询, 产生可供抽检的结果集
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean prepareData() {
		// logger.debug("\t@> PEdorSpotCheckQueryBL.prepareData() 开始");

		// 接收查询条件
		String sManageCom = (String) rTransferData.getValueByName("ManageCom");
		String sEdorType = (String) rTransferData.getValueByName("EdorType");
		String sOperator = (String) rTransferData.getValueByName("Operator");
		String sApproveOperator = (String) rTransferData
				.getValueByName("ApproveOperator");
		String sStartDate = (String) rTransferData.getValueByName("StartDate");
		String sEndDate = (String) rTransferData.getValueByName("EndDate");

		// 字段映射
		// select * from LWFieldMap where ActivityID = '0000000080'

		// 拼写SQL语句
		String QuerySQL = new String("");
		QuerySQL = "select * " + "from LWMission "
				+ "where ActivityID = '0000000080' "
				+ "and ActivityStatus = '2' ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		if (sManageCom != null && !sManageCom.trim().equals("")){
			QuerySQL = QuerySQL + "and MissionProp7 like concat('?sManageCom?','%') ";
		     sqlbv.put("sManageCom", sManageCom.trim());
		}
		if (sEdorType != null && !sEdorType.trim().equals("")){
			QuerySQL = QuerySQL + "and MissionProp13 = '?sEdorType?' ";
		      sqlbv.put("sEdorType", sEdorType.trim());
		}
		if (sOperator != null && !sOperator.trim().equals("")){
			QuerySQL = QuerySQL + "and MissionProp16 = '?sOperator?' ";
		     sqlbv.put("sOperator", sOperator.trim());
		}
		if (sApproveOperator != null && !sApproveOperator.trim().equals("")){
			QuerySQL = QuerySQL + "and MissionProp17 = '?sApproveOperator?' ";
		     sqlbv.put("sApproveOperator", sApproveOperator.trim());
		}
		if (sStartDate != null && !sStartDate.trim().equals("")){
			QuerySQL = QuerySQL
					+ "and to_date(MissionProp15, 'yyyy-mm-dd') >= to_date('?sStartDate?', 'YYYY-MM-DD') ";
		     sqlbv.put("sStartDate", sStartDate.trim());
		}
		if (sEndDate != null && !sEndDate.trim().equals("")){
			QuerySQL = QuerySQL
					+ "and to_date(MissionProp15, 'yyyy-mm-dd') <= to_date('?sEndDate?', 'YYYY-MM-DD') ";
			sqlbv.put("sEndDate", sEndDate.trim());
		}
	
		QuerySQL = QuerySQL + "order by MissionProp15 desc, MissionProp20 desc";
		sqlbv.sql(QuerySQL);
		// logger.debug(QuerySQL);

		// ----------------------------------------------------------------------

		// 查询 LWMission
		LWMissionDB tLWMissionDB = new LWMissionDB();
		rLWMissionSet = new LWMissionSet();
		try {
			rLWMissionSet = tLWMissionDB.executeQuery(sqlbv);
		} catch (Exception ex) {
			logger.debug("\t@> PEdorSpotCheckQueryBL.prepareData() : 执行 SQL 查询出错");
			logger.debug("\t   错误原因 : "
					+ tLWMissionDB.mErrors.getFirstError());
			CError.buildErr(this, "查询待抽检数据失败！");
			return false;
		}
		if (rLWMissionSet == null || rLWMissionSet.size() <= 0) {
			CError.buildErr(this, "没有符合条件的待抽检数据！");
			return false;
		}
		tLWMissionDB = null;

		// logger.debug("\t@> PEdorSpotCheckQueryBL.prepareData() 结束");
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
		// logger.debug("\t@> PEdorSpotCheckQueryBL.dealData() 开始");

		// 循环调用工作流
		for (int i = 0; i < rLWMissionSet.size(); i++) {
			LWMissionSchema tLWMissionSchema = new LWMissionSchema();
			tLWMissionSchema = rLWMissionSet.get(i + 1);
			// 注意下面 TransferData 需要传递的数据都不能为空
			TransferData tTransferData = new TransferData();
			// 工作流需要数据
			tTransferData.setNameAndValue("MissionID", tLWMissionSchema
					.getMissionID());
			tTransferData.setNameAndValue("SubMissionID", tLWMissionSchema
					.getSubMissionID());
			// 业务需要数据
			String sMissionProp1 = tLWMissionSchema.getMissionProp1();
			if (sMissionProp1 != null && !sMissionProp1.trim().equals("")) {
				tTransferData.setNameAndValue("EdorAcceptNo", sMissionProp1);
			}
			String sMissionProp2 = tLWMissionSchema.getMissionProp2();
			if (sMissionProp2 != null && !sMissionProp2.trim().equals("")) {
				tTransferData.setNameAndValue("OtherNo", sMissionProp2);
			}
			String sMissionProp3 = tLWMissionSchema.getMissionProp3();
			if (sMissionProp3 != null && !sMissionProp3.trim().equals("")) {
				tTransferData.setNameAndValue("OtherNoType", sMissionProp3);
			}
			String sMissionProp4 = tLWMissionSchema.getMissionProp4();
			if (sMissionProp4 != null && !sMissionProp4.trim().equals("")) {
				tTransferData.setNameAndValue("EdorAppName", sMissionProp4);
			}
			String sMissionProp5 = tLWMissionSchema.getMissionProp5();
			if (sMissionProp5 != null && !sMissionProp5.trim().equals("")) {
				tTransferData.setNameAndValue("AppType", sMissionProp5);
			}
			String sMissionProp7 = tLWMissionSchema.getMissionProp7();
			if (sMissionProp7 != null && !sMissionProp7.trim().equals("")) {
				tTransferData.setNameAndValue("ManageCom", sMissionProp7);
			}
			String sMissionProp11 = tLWMissionSchema.getMissionProp11();
			if (sMissionProp11 != null && !sMissionProp11.trim().equals("")) {
				tTransferData.setNameAndValue("AppntName", sMissionProp11);
			}
			String sMissionProp12 = tLWMissionSchema.getMissionProp12();
			if (sMissionProp12 != null && !sMissionProp12.trim().equals("")) {
				tTransferData.setNameAndValue("PayToDate", sMissionProp12);
			}
			String sMissionProp13 = tLWMissionSchema.getMissionProp13();
			if (sMissionProp13 != null && !sMissionProp13.trim().equals("")) {
				tTransferData.setNameAndValue("EdorType", sMissionProp13);
			}
			String sMissionProp14 = tLWMissionSchema.getMissionProp14();
			if (sMissionProp14 != null && !sMissionProp14.trim().equals("")) {
				tTransferData.setNameAndValue("GetMoney", sMissionProp14);
			}
			String sMissionProp15 = tLWMissionSchema.getMissionProp15();
			if (sMissionProp15 != null && !sMissionProp15.trim().equals("")) {
				tTransferData.setNameAndValue("EdorValiDate", sMissionProp15);
			}
			String sMissionProp16 = tLWMissionSchema.getMissionProp16();
			if (sMissionProp16 != null && !sMissionProp16.trim().equals("")) {
				tTransferData.setNameAndValue("Operator", sMissionProp16);
			}
			String sMissionProp17 = tLWMissionSchema.getMissionProp17();
			if (sMissionProp17 != null && !sMissionProp17.trim().equals("")) {
				tTransferData
						.setNameAndValue("ApproveOperator", sMissionProp17);
			}
			String sMissionProp18 = tLWMissionSchema.getMissionProp18();
			if (sMissionProp18 != null && !sMissionProp18.trim().equals("")) {
				tTransferData.setNameAndValue("ApproveDate", sMissionProp18);
			}
			String sMissionProp19 = tLWMissionSchema.getMissionProp19();
			if (sMissionProp19 != null && !sMissionProp19.trim().equals("")) {
				tTransferData.setNameAndValue("EdorAppDate", sMissionProp19);
			}
			String sMissionProp20 = tLWMissionSchema.getMissionProp20();
			if (sMissionProp20 != null && !sMissionProp20.trim().equals("")) {
				tTransferData.setNameAndValue("OperateDate", sMissionProp20);
			}
			tLWMissionSchema = null;
			// 产生抽检标记, 测试用
			// tTransferData.setNameAndValue("NeedCheckFlag", "1");
			// 产生抽检标记, 业务用
			if (rProportion == 0) {
				tTransferData.setNameAndValue("NeedCheckFlag", "0");
			} else if (rProportion == 100) {
				tTransferData.setNameAndValue("NeedCheckFlag", "1");
			} else {
				Random tRandom = new Random();
				float fCheckFlag = Math.abs(tRandom.nextFloat()) * 100;
				tRandom = null;
				if (fCheckFlag >= rProportion) {
					tTransferData.setNameAndValue("NeedCheckFlag", "0");
				} else {
					tTransferData.setNameAndValue("NeedCheckFlag", "1");
				}
			}

			// VData
			VData tVData = new VData();
			tVData.add(rGlobalInput);
			tVData.add(tTransferData);
			tTransferData = null;

			// ------------------------------------------------------------------

			// EdorWorkFlowUI
			EdorWorkFlowUI tEdorWorkFlowUI = new EdorWorkFlowUI();
			if (!tEdorWorkFlowUI.submitData(tVData, rOperation)) {
				logger.debug("\t@> PEdorSpotCheckQueryBL.dealData() : 调用 EdorWorkFlowUI 工作流提交数据失败");
				logger.debug("\t   保全受理号是 : "
						+ tLWMissionSchema.getMissionProp1());
				logger.debug("\t   错误原因 : "
						+ tEdorWorkFlowUI.mErrors.getFirstError());
				CError.buildErr(this, "保存提交的信息到数据库失败！");
				return false;
			}
			tEdorWorkFlowUI = null;
			tVData = null;
		} // end for

		// logger.debug("\t@> PEdorSpotCheckQueryBL.dealData() 结束");
		return true;
	} // function dealData end

	// ==========================================================================

	/**
	 * 返回经过本类处理的数据结果
	 * 
	 * @param null
	 * @return VData
	 */
	public VData getResult() {
		return rResultData;
	} // function getResult end

	// ==========================================================================

	/**
	 * 返回传入本类的操作类型
	 * 
	 * @param null
	 * @return String
	 */
	public String getOperation() {
		return rOperation;
	} // function getOperation end

	// ==========================================================================

	/**
	 * 返回本类运行时产生的错误信息
	 * 
	 * @param null
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
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
		if (rLWMissionSet != null)
			rLWMissionSet = null;
	} // function collectGarbage end

	// ==========================================================================

	/**
	 * 调试主程序业务方法
	 * 
	 * @param String[]
	 */
	// public static void main(String[] args)
	// {
	// //GlobalInput
	// GlobalInput tGlobalInput = new GlobalInput();
	// tGlobalInput.Operator = "XinYQ";
	// tGlobalInput.ManageCom = "86";
	// //TransferData
	// TransferData tTransferData = new TransferData();
	// tTransferData.setNameAndValue("ManageCom", "862100");
	// tTransferData.setNameAndValue("StartDate", "2005-01-01");
	// tTransferData.setNameAndValue("EndDate", "2006-02-25");
	// tTransferData.setNameAndValue("Proportion", "48");
	// //VData
	// VData tVData = new VData();
	// tVData.addElement(tGlobalInput);
	// tVData.addElement(tTransferData);
	// //PEdorSpotCheckQueryBL
	// PEdorSpotCheckQueryBL tPEdorSpotCheckQueryBL = new
	// PEdorSpotCheckQueryBL();
	// //submitData
	// if(!tPEdorSpotCheckQueryBL.submitData(tVData, "0000000080"))
	// logger.debug("\t@> PEdorSpotCheckQueryBL.main() : 提交数据失败");
	// else
	// logger.debug("\t@> PEdorSpotCheckQueryBL.main() : 提交数据成功");
	// } //function main end
	// ==========================================================================

} // class PEdorSpotCheckQueryBL end
