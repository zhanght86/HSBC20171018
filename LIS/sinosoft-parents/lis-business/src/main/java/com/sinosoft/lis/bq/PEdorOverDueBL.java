/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 保全-保全逾期终止处理类</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author：zhangtao
 * @version：1.0
 * @CreateDate：2005-08-23
 */

package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.db.LJSGetDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LJTempFeeClassDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.operfee.IndiDueFeePartUI;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vschema.LJSGetSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.ActivityOperator;

public class PEdorOverDueBL implements BusinessService{
private static Logger logger = Logger.getLogger(PEdorOverDueBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mInputData = new VData();
	private VData mResult = new VData();
	private MMap map = new MMap();
	/** 用户登陆信息 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();
	// 系统当前时间
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	public PEdorOverDueBL() {
		
	}
	public PEdorOverDueBL(GlobalInput tg) {
		mGlobalInput = tg;
	}

	/**
	 * 更新保全核保的逾期终止状态 XinYQ added on 2005-12-23
	 * 
	 * @param sEdorAcceptNo
	 * @return boolean
	 */
	public boolean setUWOverDue(String sEdorAcceptNo) {
		// 查询保全申请号码类型
		// select * from LDCode where CodeType = 'edornotype'
		String QuerySQL = "select OtherNoType " + "from LPEdorApp "
				+ "where EdorAcceptNo = '?EdorAcceptNo?'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(QuerySQL);
		sqlbv.put("EdorAcceptNo", sEdorAcceptNo.trim());
		ExeSQL tExeSQL = new ExeSQL();
		String sActivityStatus = new String("");
		try {
			sActivityStatus = tExeSQL.getOneValue(sqlbv);
		} catch (Exception ex) {
			logger.debug("\t@> PEdorOverDueBL.setUWOverDue() : 查询保全申请号码类型失败！");
			CError.buildErr(this, "查询保全申请号码类型失败！");
			return false;
		}
		// 垃圾处理
		tExeSQL = null;
		// 如果是客户层的变更
		if (sActivityStatus.trim().equals("1")) {
			String UpdateSQL = "update LWMission "
					+ "set ActivityStatus = '5' "
					+ "where ActivityID = '0000000005' "
					+ "and MissionProp1 = '?MissionProp1?'";
			SQLwithBindVariables sbv1=new SQLwithBindVariables();
			sbv1.sql(UpdateSQL);
			sbv1.put("MissionProp1", sEdorAcceptNo.trim());
			map.put(sbv1, "UPDATE");
			VData tVData = new VData();
			tVData.add(mGlobalInput);
			tVData.add(map);
			PubSubmit tPubSubmit = new PubSubmit();
			if (!tPubSubmit.submitData(tVData, "")) {
				CError.buildErr(this, "保全逾期终止: 客户层逾期更新状态失败！");
				return false;
			}
			// 垃圾处理
			tVData = null;
			tPubSubmit = null;
		}
		// 如果是保单层的变更
		else {
//			// 删除 LOPrtManager 核保通知书
//			String DeleteBQ80SQL = new String("");
//			DeleteBQ80SQL = "delete from LOPrtManager a " + "where a.Code in "
//					+ "(select Code " + "from LDCode "
//					+ "where CodeType = 'bquwnotice') " + "and exists "
//					+ "(select 'X' " + "from LPEdorMain "
//					+ "where EdorAcceptNo = a.StandByFlag4 " + "and EdorAcceptNo = '" //由于发放核保通知书的字段做了调整
//					+ sEdorAcceptNo.trim() + "')";
//			map.put(DeleteBQ80SQL, "DELETE");
//			// 删除 LOPrtManager 体检通知书
//			String DeleteBQ90SQL = new String("");
//			DeleteBQ90SQL = "delete from LOPrtManager "
//					+ "where Code = 'BQ90' " + "and StandbyFlag3 in "
//					+ "(select MissionID " + "from LWMission "
//					+ "where ActivityID = '0000000005' "
//					+ "and MissionProp1 = '" + sEdorAcceptNo.trim() + "') ";
//			map.put(DeleteBQ90SQL, "DELETE");
			// 调用公用接口操作
			if (!setOverDue(sEdorAcceptNo, "8")) {
				CError.buildErr(this, "更新保全核保的逾期终止状态失败！");
				return false;
			}
		}
		return true;
	} // function setUWOverDue end

	/**
	 * 统一更新保全受理、保全申请批单、保全项目的批改状态为逾期终止
	 * 
	 * @param sEdorAcceptNo
	 * @param sEdorState
	 */
	public boolean setOverDue(String sEdorAcceptNo, String sEdorState) {
		// map = new MMap(); //XinYQ modified on 2005-12-23 : setUWOverDue 需要
		mInputData.clear();

		// 校验是否已经有暂交费尚未核销或是银行在途
		if (!checkPayGet(sEdorAcceptNo)) {
			return false;
		}

		// 更新保全批改状态
		updEdorState(sEdorAcceptNo, sEdorState);

		// 删除工作流节点
		if(!delMission(sEdorAcceptNo))
		{
			CError.buildErr(this, "删除工作流节点失败! ");
			return false;			
		}

		// 删除财务数据
		delFinFee(sEdorAcceptNo);

		// 删除C表数据（加人、新增附加险、附加险加保等）
		delLCPol(sEdorAcceptNo);

		// 保单解挂
		BqContHangUpBL tContHangUpBL = new BqContHangUpBL(mGlobalInput);
		if (!tContHangUpBL.hangUpEdorAccept(sEdorAcceptNo, "0")) {
			CError.buildErr(this, "保全保单解挂失败! ");
			return false;
		} else {
			MMap tMap = tContHangUpBL.getMMap();
			map.add(tMap);
		}

		mInputData.add(map);
		// 数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			CError.buildErr(this, "保全逾期终止数据提交失败");
			return false;
		}

		// add by jiaqiangli 2009-05-23 逾期终止与满期终止，保全确认都要对过期催收进行善后处理，否则导致不能正常垫缴而失效
		// add by jiaqiangli 2009-05-23 垫缴依赖于催收 同时把逾期终止放在垫缴的前面 其实出现了也没关系，走特殊复效即可
		//add by jiaqiangli 2009-04-27 paytodate+60d<=sysdate 重新催收处理
		LDSysVarDB tLDSysVarDB = new LDSysVarDB();
		tLDSysVarDB.setSysVar("aheaddays");
		int tAheadDays = 0;
		if (tLDSysVarDB.getInfo() == false) {
			tAheadDays = 60;//默认为60天
		} 
		else {
			tAheadDays = Integer.parseInt(tLDSysVarDB.getSysVarValue());
		}
		String tLJSPaySQL="select contno from lcpol where contno=(select contno from lpedoritem where EDORacceptno='?sEdorAcceptNo?') and appflag='1' "
		                 +"and polno=mainpolno and paytodate+?tAheadDays? <= to_date('?date?','YYYY-MM-DD') ";
		logger.debug("tLJSPaySQL"+tLJSPaySQL);
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(tLJSPaySQL);
		sbv2.put("sEdorAcceptNo", sEdorAcceptNo);
		sbv2.put("tAheadDays", tAheadDays);
		sbv2.put("date", PubFun.getCurrentDate());
		ExeSQL tExeSQL = new ExeSQL();
		String tLJSPaySQLFlag =  tExeSQL.getOneValue(sbv2);
		if (tLJSPaySQLFlag != null && !"".equals(tLJSPaySQLFlag)) {
			String tStartDate = "";
			String tEndDate = PubFun.calDate(PubFun.getCurrentDate(), -tAheadDays, "D", null);
			TransferData tVTransferData = new TransferData();
			tVTransferData.setNameAndValue("StartDate", tStartDate);
			tVTransferData.setNameAndValue("EndDate", tEndDate);
			tVTransferData.setNameAndValue("Contno", tLJSPaySQLFlag);		
			logger.debug("StartDate==" + tStartDate);
			logger.debug("EndDate==" + tEndDate);
			logger.debug("ContNo==" + tLJSPaySQLFlag);
			
			VData tTVData = new VData();
			tTVData.add(mGlobalInput);
			tTVData.add(tVTransferData);
			
			IndiDueFeePartUI tIndiDueFeePartUI = new IndiDueFeePartUI();
			tIndiDueFeePartUI.submitData(tTVData, "ZC"); //添加续期催收标记
			
			if (tIndiDueFeePartUI.mErrors.needDealError() == true) {
				CError.buildErr(this, "保全逾期终止后的过期催收失败，原因是"+tIndiDueFeePartUI.mErrors.getFirstError()); 
				return false;
			}
		}
		//add by jiaqiangli 2009-04-27 paytodate+60d<=sysdate 重新催收处理
		// add by jiaqiangli 2009-05-23 逾期终止与满期终止，保全确认都要对过期催收进行善后处理，否则导致不能正常垫缴而失效
		
		return true;
	}

	/**
	 * 财务数据校验
	 * 
	 * @return boolean
	 */
	public boolean checkPayGet(String sEdorAcceptNo) {
		// 查询应收数据
		LJSPayDB tLJSPayDB = new LJSPayDB();
		String tSQL = "select *" + "  from ljspay a " + " where otherno = '?sEdorAcceptNo?'" + "   and othernotype = '10'"
				+ "   and not exists   " + " (select 'X'   "
				+ " from ljtempfee b   " + " where b.otherno = a.otherno   "
				+ "  and b.tempfeeno=a.getnoticeno  "
				+ "  and b.enteraccdate is not null  "
				+ "  and b.confdate is not null)";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSQL);
		sqlbv.put("sEdorAcceptNo", sEdorAcceptNo);

		LJSPaySet tLJSPaySet = tLJSPayDB.executeQuery(sqlbv);
		if (tLJSPayDB.mErrors.needDealError()) {
			CError.buildErr(this, "保全应收信息查询失败!");
			return false;
		}
		if (tLJSPaySet != null && tLJSPaySet.size() > 0) {
			// 判断是否银行在途
			String sBankOnTheWayFlag = tLJSPaySet.get(1).getBankOnTheWayFlag();
			if (sBankOnTheWayFlag != null
					&& sBankOnTheWayFlag.trim().equals("1")) {
				CError.buildErr(this, "保全收费银行在途,不允许终止!");
				return false;
			}

			// 判断是否有暂交费
			String sql = " select * from ljtempfee where 1=1 "
				//comment by jiaqiangli 2009-07-20 此条件在MS没有任何的意义
					+ " and (enteraccdate <> '3000-01-01' or enteraccdate is null) "
					+ " and tempfeetype = '4' " + " and  tempfeeno = '?tempfeeno?' and ConfDate is null ";
			SQLwithBindVariables sbv1=new SQLwithBindVariables();
			sbv1.sql(sql);
			sbv1.put("tempfeeno", tLJSPaySet.get(1).getGetNoticeNo());
			LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
			LJTempFeeSet tLJTempFeeSet = tLJTempFeeDB.executeQuery(sbv1);
			if (tLJTempFeeSet == null || tLJTempFeeSet.size() < 1) {
				//add by jiaqiangli 2009-07-20 MS暂交费退费即为暂交费的核销（置confdate）
				return true; // 没有任何暂交费记录或暂缴费已经被退(3000-01-01)
			}
			String sEnterAccDate;
			String sTempFeeNo;
			for (int i = 1; i <= tLJTempFeeSet.size(); i++) {
				sEnterAccDate = tLJTempFeeSet.get(i).getEnterAccDate();
				sTempFeeNo = tLJTempFeeSet.get(i).getTempFeeNo();
				if (sEnterAccDate != null && !sEnterAccDate.equals("")) {
					// 暂交费已经到帐
					CError.buildErr(this, "保全已经录入暂收费,不允许终止!");
					return false;
				} else {
					// 到帐日期为空,如果是支票交费,则不允许终止.
					sql = " select * from ljtempfeeclass where paymode = '3' and enteraccdate is null and tempfeeno = '?sTempFeeNo?' ";
					SQLwithBindVariables sbv2=new SQLwithBindVariables();
					sbv2.sql(sql);
					sbv2.put("sTempFeeNo", sTempFeeNo);
					LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
					LJTempFeeClassSet tLJTempFeeClassSet = tLJTempFeeClassDB
							.executeQuery(sbv2);
					if (tLJTempFeeClassSet != null
							&& tLJTempFeeClassSet.size() > 0) // 存在支票录入
					{
						// 录入支票尚未到帐
						CError.buildErr(this, "录入支票尚未到帐,不允许终止!");
						return false;
					}
				}
			}

		} else {
			// 查询应付数据
			LJSGetDB tLJSGetDB = new LJSGetDB();
			tLJSGetDB.setOtherNo(sEdorAcceptNo);
			tLJSGetDB.setOtherNoType("10");
			LJSGetSet tLJSGetSet = tLJSGetDB.query();
			if (tLJSGetDB.mErrors.needDealError()) {
				CError.buildErr(this, "保全应付信息查询失败!");
				return false;
			}
			if (tLJSGetSet != null && tLJSGetSet.size() > 0) {
				// 应付数据不用校验
			}
		}

		return true;
	}

	/**
	 * 统一更新保全受理、保全申请批单、保全项目的批改状态为逾期终止
	 * 
	 * @param sEdorAcceptNo
	 * @param sEdorState
	 */
	private void updEdorState(String sEdorAcceptNo, String sEdorState) {

		String wherePart = "where EdorAcceptNo='?sEdorAcceptNo?'";

		StringBuffer sbSQL = new StringBuffer();

		// 保全项目
		sbSQL.setLength(0);
		sbSQL.append(" UPDATE LPEdorItem set EdorState = '").append("?sEdorState?")
				.append("', ModifyDate = '").append("?mCurrentDate?").append(
						"', ModifyTime = '").append("?mCurrentTime?").append("' ")
				.append(wherePart);
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(sbSQL.toString());
		sbv1.put("mCurrentDate", mCurrentDate);
		sbv1.put("sEdorState", sEdorState);
		sbv1.put("mCurrentTime", mCurrentTime);
		sbv1.put("sEdorAcceptNo", sEdorAcceptNo);
		map.put(sbv1, "UPDATE");

		// 保全批单
		sbSQL.setLength(0);
		sbSQL.append(" UPDATE LPEdorMain set EdorState = '").append("?sEdorState?")
				.append("', ModifyDate = '").append("?mCurrentDate?").append(
						"', ModifyTime = '").append("?mCurrentTime?").append("' ")
				.append(wherePart);
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(sbSQL.toString());
		sbv2.put("mCurrentDate", mCurrentDate);
		sbv2.put("sEdorState", sEdorState);
		sbv2.put("mCurrentTime", mCurrentTime);
		sbv2.put("sEdorAcceptNo", sEdorAcceptNo);
		map.put(sbv2, "UPDATE");

		// 团体保全项目
		sbSQL.setLength(0);
		sbSQL.append(" UPDATE LPGrpEdorItem set EdorState = '").append(
				"?sEdorState?").append("', ModifyDate = '").append("?mCurrentDate?")
				.append("', ModifyTime = '").append("?mCurrentTime?").append("' ")
				.append(wherePart);
		SQLwithBindVariables sbv3=new SQLwithBindVariables();
		sbv3.sql(sbSQL.toString());
		sbv3.put("mCurrentDate", mCurrentDate);
		sbv3.put("sEdorState", sEdorState);
		sbv3.put("mCurrentTime", mCurrentTime);
		sbv3.put("sEdorAcceptNo", sEdorAcceptNo);
		map.put(sbv3, "UPDATE");

		// 团体保全批单
		sbSQL.setLength(0);
		sbSQL.append(" UPDATE LPGrpEdorMain set EdorState = '").append(
				"?sEdorState?").append("', ModifyDate = '").append("?mCurrentDate?")
				.append("', ModifyTime = '").append("?mCurrentTime?").append("' ")
				.append(wherePart);
		SQLwithBindVariables sbv4=new SQLwithBindVariables();
		sbv4.sql(sbSQL.toString());
		sbv4.put("mCurrentDate", mCurrentDate);
		sbv4.put("sEdorState", sEdorState);
		sbv4.put("mCurrentTime", mCurrentTime);
		sbv4.put("sEdorAcceptNo", sEdorAcceptNo);
		map.put(sbv4, "UPDATE");

		// 保全申请
		sbSQL.setLength(0);
		sbSQL.append(" UPDATE LPEdorApp set EdorState = '").append("?sEdorState?")
				.append("', ModifyDate = '").append("?mCurrentDate?").append(
						"', ModifyTime = '").append("?mCurrentTime?").append("' ")
				.append(wherePart);
		SQLwithBindVariables sbv5=new SQLwithBindVariables();
		sbv5.sql(sbSQL.toString());
		sbv5.put("mCurrentDate", mCurrentDate);
		sbv5.put("sEdorState", sEdorState);
		sbv5.put("mCurrentTime", mCurrentTime);
		sbv5.put("sEdorAcceptNo", sEdorAcceptNo);
		map.put(sbv5, "UPDATE");

	}

	/**
	 * 备份并删除保全受理和无扫描受理节点任务
	 * 
	 * @return boolean
	 */
	private boolean delMission(String sEdorAcceptNo) {
		// 备份 LWMission
		String QueryLWMissionSQL;
		String sWhere = " where  "
				+ "  missionid =  "
				+ "( select missionid from lwmission where missionprop1 =  '?sEdorAcceptNo?'" + " union"
				+ " select missionid from lbmission where missionprop1 = '?sEdorAcceptNo?'" + ")";
		QueryLWMissionSQL = " select * from LWMission " + sWhere;
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(QueryLWMissionSQL);
		sqlbv.put("sEdorAcceptNo", sEdorAcceptNo);
		LWMissionDB tLWMissionDB = new LWMissionDB();
		LWMissionSet tLWMissionSet = new LWMissionSet();
		tLWMissionSet = tLWMissionDB.executeQuery(sqlbv);
		VData tVData = new VData();
		TransferData tTransferData = new TransferData();
		tVData.add(tTransferData);
		if (tLWMissionSet != null && tLWMissionSet.size() > 0) {
			for (int i = 1; i <= tLWMissionSet.size(); i++) {		
		LWMissionSchema tLWMissionSchema = tLWMissionSet.get(i);
		
			ActivityOperator tActivityOperator = new ActivityOperator();
			if (!tActivityOperator.DeleteMission(tLWMissionSchema
					.getMissionID(), tLWMissionSchema.getSubMissionID(),
					tLWMissionSchema.getActivityID(), tVData)) {
				CError tError = new CError();
				tError.errorMessage = "工作流保全撤销节点删除失败!";
				mErrors.addOneError(tError);
				return false;
			}

			VData tempVData = tActivityOperator.getResult();
			if ((tempVData != null) && (tempVData.size() > 0)) {
				MMap tmap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
				map.add(tmap);
			}
			}
		}
		return true;

	}

	/**
	 * 删除财务数据 XinYQ added on 2006-04-30
	 * 
	 * @param sEdorAcceptNo
	 * @return null
	 */
	public MMap delFinFee(String sEdorAcceptNo) {
		MMap tMap = new MMap();
		String DeleteSQL = new String("");

		// 先删除 LJSGetEndorse
		DeleteSQL = "delete from LJSGetEndorse " + "where GetNoticeNo in "
				+ "(select GetNoticeNo " + "from LJSPay " + "where 1 = 1 "
				+ "and OtherNo = '?sEdorAcceptNo?' "
				+ "and OtherNoType = '10')";
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(DeleteSQL);
		sbv1.put("sEdorAcceptNo", sEdorAcceptNo);
		tMap.put(sbv1, "DELETE");
		
		DeleteSQL = "delete from LJSGetEndorse " + "where GetNoticeNo in "
		+ "(select GetNoticeNo " + "from ljsget " + "where 1 = 1 "
		+ "and OtherNo = '?sEdorAcceptNo?' "
		+ "and OtherNoType = '10')";
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(DeleteSQL);
		sbv2.put("sEdorAcceptNo", sEdorAcceptNo);
		tMap.put(sbv2, "DELETE");
		
        //删除NS，FM等项目产生的险种应收信息
		String delLJSPayPerson = " delete from ljspayperson where getnoticeno in (select edorno from LPEdorItem p where p.edoracceptno='?sEdorAcceptNo?')";
		SQLwithBindVariables sbv3=new SQLwithBindVariables();
		sbv3.sql(delLJSPayPerson);
		sbv3.put("sEdorAcceptNo", sEdorAcceptNo);
		tMap.put(sbv3, "DELETE");
		
		// 再删除 LJSPay
		DeleteSQL = "delete from LJSPay " + "where 1 = 1 " + "and OtherNo = '?sEdorAcceptNo?' " + "and OtherNoType = '10'";
		SQLwithBindVariables sbv4=new SQLwithBindVariables();
		sbv4.sql(DeleteSQL);
		sbv4.put("sEdorAcceptNo", sEdorAcceptNo);
		tMap.put(sbv4, "DELETE");
		
		DeleteSQL = "delete from LJSGet " + "where 1 = 1 " + "and OtherNo = '?sEdorAcceptNo?' " + "and OtherNoType = '10'";
		SQLwithBindVariables sbv5=new SQLwithBindVariables();
		sbv5.sql(DeleteSQL);
		sbv5.put("sEdorAcceptNo", sEdorAcceptNo);
		tMap.put(sbv5, "DELETE");
		
//		tMap.add(map);
		map.add(tMap);
		return tMap;
        

	} // function delFinFee end

	/**
	 * 删除C表数据（加人、新增附加险、附加险加保等） zhangtao added on 2007-03-29
	 * 
	 * @param sEdorAcceptNo
	 * @return MMap
	 */
	public MMap delLCPol(String sEdorAcceptNo) {
		MMap tMap = new MMap();
		String DeleteSQL;
		// 个险
		DeleteSQL = " delete from lcprem where contno in "
				+ " (select contno from lcpol where contno in ( select contno from lpedoritem where edoracceptno = '?sEdorAcceptNo?') and appflag = '2' ) ";
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(DeleteSQL);
		sbv1.put("sEdorAcceptNo", sEdorAcceptNo);
		tMap.put(sbv1, "DELETE");
		
		DeleteSQL = " delete from lcget where contno in "
				+ " (select contno from lcpol where contno in ( select contno from lpedoritem where edoracceptno = '?sEdorAcceptNo?') and appflag = '2' ) ";
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(DeleteSQL);
		sbv2.put("sEdorAcceptNo", sEdorAcceptNo);
		tMap.put(sbv2, "DELETE");
		
		DeleteSQL = " delete from lcduty where contno in "
				+ " (select contno from lcpol where contno in ( select contno from lpedoritem where edoracceptno = '?sEdorAcceptNo?') and appflag = '2' ) ";
		SQLwithBindVariables sbv3=new SQLwithBindVariables();
		sbv3.sql(DeleteSQL);
		sbv3.put("sEdorAcceptNo", sEdorAcceptNo);
		tMap.put(sbv3, "DELETE");
		
		DeleteSQL = " delete from lcpol where contno in "
				+ " (select contno from lpedoritem where edoracceptno = '?sEdorAcceptNo?') " + " and appflag = '2'";
		SQLwithBindVariables sbv4=new SQLwithBindVariables();
		sbv4.sql(DeleteSQL);
		sbv4.put("sEdorAcceptNo", sEdorAcceptNo);
		tMap.put(sbv4, "DELETE");   
	   
	   
		// 团险
		DeleteSQL = " delete from lcgrppol where grpcontno in "
				+ " (select grpcontno from lpgrpedoritem where edoracceptno = '?sEdorAcceptNo?') " + " and appflag = '2'";
		SQLwithBindVariables sbv5=new SQLwithBindVariables();
		sbv5.sql(DeleteSQL);
		sbv5.put("sEdorAcceptNo", sEdorAcceptNo);
		tMap.put(sbv5, "DELETE");

		DeleteSQL = " delete from lcinsured where contno in "
				+ " (select contno from lccont where grpcontno in "
				+ "   (select grpcontno from lpgrpedoritem where edoracceptno = '?sEdorAcceptNo?')" + "   and appflag = '2')";
		SQLwithBindVariables sbv6=new SQLwithBindVariables();
		sbv6.sql(DeleteSQL);
		sbv6.put("sEdorAcceptNo", sEdorAcceptNo);
		tMap.put(sbv6, "DELETE");

		DeleteSQL = " delete from lccont where grpcontno in "
				+ " (select grpcontno from lpgrpedoritem where edoracceptno = '?sEdorAcceptNo?') " + " and appflag = '2'";
		SQLwithBindVariables sbv7=new SQLwithBindVariables();
		sbv7.sql(DeleteSQL);
		sbv7.put("sEdorAcceptNo", sEdorAcceptNo);
		tMap.put(sbv7, "DELETE");

		DeleteSQL = " delete from lcprem where contno in "
				+ " (select contno from lcpol where grpcontno in "
				+ " (select grpcontno from lpgrpedoritem where edoracceptno = '?sEdorAcceptNo?') and appflag = '2') " ;
		SQLwithBindVariables sbv8=new SQLwithBindVariables();
		sbv8.sql(DeleteSQL);
		sbv8.put("sEdorAcceptNo", sEdorAcceptNo);
		tMap.put(sbv8, "DELETE");		
		
		DeleteSQL = " delete from lcget where contno in "
				+ " (select contno from lcpol where grpcontno in "
				+ " (select grpcontno from lpgrpedoritem where edoracceptno = '?sEdorAcceptNo?') and appflag = '2') " ;
		SQLwithBindVariables sbv9=new SQLwithBindVariables();
		sbv9.sql(DeleteSQL);
		sbv9.put("sEdorAcceptNo", sEdorAcceptNo);
		tMap.put(sbv9, "DELETE");
		
		DeleteSQL = " delete from lcduty where contno in "
				+ " (select contno from lcpol where grpcontno in "
				+ " (select grpcontno from lpgrpedoritem where edoracceptno = '?sEdorAcceptNo?') and appflag = '2') " ;
		SQLwithBindVariables sbv10=new SQLwithBindVariables();
		sbv10.sql(DeleteSQL);
		sbv10.put("sEdorAcceptNo", sEdorAcceptNo);
		tMap.put(sbv10, "DELETE");
		
		DeleteSQL = " delete from lcpol where grpcontno in "
				+ " (select grpcontno from lpgrpedoritem where edoracceptno = '?sEdorAcceptNo?') " + " and appflag = '2'";
		SQLwithBindVariables sbv11=new SQLwithBindVariables();
		sbv11.sql(DeleteSQL);
		sbv11.put("sEdorAcceptNo", sEdorAcceptNo);
		tMap.put(sbv11, "DELETE");	   
	   

		map.add(tMap);

		return tMap;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	public VData getResult() {
		return mResult;
	}

	public boolean submitData(VData data, String Operater) {
		
		if (!getInputData(data)) {
			return false;
		}
		String EdorAcceptNo = (String)mTransferData.getValueByName("EdorAcceptNo");
		String EdorState = (String)mTransferData.getValueByName("EdorState");
		
		return setOverDue(EdorAcceptNo,EdorState);
	}
	private boolean getInputData(VData data) {
		mGlobalInput = (GlobalInput) data.getObjectByObjectName("GlobalInput", 0);
		mTransferData = (TransferData) data.getObjectByObjectName("TransferData", 0);
		if(mTransferData == null){
			CError.buildErr(this, "数据传入不完整");
			return false;
		}
		
		return true;
	}
}
