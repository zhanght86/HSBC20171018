/**
 * Copyright (c) 2007 Newchinalife
 * All right reserved.
 */
package com.sinosoft.lis.config;
import org.apache.log4j.Logger;

import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:保险套餐组合流程公用模块
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Newchinalife
 * </p>
 * 
 * @author 裴真
 * @version 1.0
 */

public class PlanFunc {
private static Logger logger = Logger.getLogger(PlanFunc.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	// public static CErrors mErrors = new CErrors();
	// public static VData mResult = new VData();
	/***************************************************************************
	 * @todo 产品组合状态
	 **************************************************************************/
	public static final String PLAN_STATE_APPLY = "0"; // 申请中
	public static final String PLAN_STATE_BEFOREAUDITING = "1"; // 待审核
	public static final String PLAN_STATE_AUDITING = "2"; // 审核中
	public static final String PLAN_STATE_BEFOREAPPROVE = "3"; // 待审批
	public static final String PLAN_STATE_APPROVE = "4"; // 审批中
	public static final String PLAN_STATE_AUDITING_NO = "5"; // 审核不通过
	public static final String PLAN_STATE_AUDITING_Yes = "3"; // 审核通过<即待审批状态>
	public static final String PLAN_STATE_APPROVE_NO = "7"; // 审批不通过
	public static final String PLAN_STATE_APPROVE_YES = "9"; // 审批通过
	/***************************************************************************
	 * @todo 产品组合流程操作类型
	 **************************************************************************/
	public static final String PLAN_OPERATE_APPLY = "01"; // 申请
	public static final String PLAN_OPERATE_AUDIT = "02"; // 审核
	public static final String PLAN_OPERATE_APPROVE = "03"; // 审批

	public PlanFunc() {
	}

	/***************************************************************************
	 * @todo 获取产品组合每个流程中操作序号
	 *       //需传入参数:组合(计划)编码(ContPlanCode)、计划类型(PlanType)、操作类型(OperateType)
	 **************************************************************************/
	public static String getOperateNo(String ContPlanCode, String PlanType,
			String OperateType) {
		String tOperateNo = "";

		String tQuerySQL = " select (case when max(operateno) is not null then max(operateno) else 0 end) from ldplanapprovesub"
				+ " where contplancode='"
				+ "?ContPlanCode?"
				+ "'"
				+ " and plantype='"
				+ "?PlanType?"
				+ "' "
				+ " and operatetype='"
				+ "?OperateType?" + "'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tQuerySQL);
		sqlbv1.put("ContPlanCode",ContPlanCode);
		sqlbv1.put("PlanType",PlanType);
		sqlbv1.put("OperateType",OperateType);
		ExeSQL tQueryExeSQL = new ExeSQL();
		String Str = tQueryExeSQL.getOneValue(sqlbv1);
		if (Str == null || Str.equals("") || Str.equals("0")) {
			tOperateNo = "1";
		} else {
			int sNo = Integer.parseInt(Str) + 1;
			tOperateNo = String.valueOf(sNo);
		}
		return tOperateNo;
	}

}
