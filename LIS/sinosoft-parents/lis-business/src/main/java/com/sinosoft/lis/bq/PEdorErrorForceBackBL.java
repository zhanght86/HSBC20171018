package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LJSGetDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LJTempFeeClassDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LBMissionSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vschema.LBMissionSet;
import com.sinosoft.lis.vschema.LJSGetSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.ActivityOperator;
import com.sinosoft.service.BusinessService;

public class PEdorErrorForceBackBL implements BusinessService {
private static Logger logger = Logger.getLogger(PEdorErrorForceBackBL.class);


	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 往后面传输的数据库操作 */
	private MMap map = new MMap();
	/** 用户登陆信息 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();

	private LPEdorAppSchema mLPEdorAppSchema = new LPEdorAppSchema();

	/** 传输数据 */
	private String mPrtSerNo;
	private String mBackReason;
	private String mOtherReason;
	private String mEdorAcceptNo;
	private String mLetterType;

	// 系统当前时间
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();


	public PEdorErrorForceBackBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据
	 * @param: cOperate 数据操作符
	 * @return: boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据
		if (!getInputData()) {
			return false;
		}

		logger.debug("after getInputData...");

		// 数据操作业务处理
		if (!dealData()) {
			return false;
		}

		mInputData.clear();
		mInputData.add(map);
		// 数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			CError.buildErr(this, "保全逾期终止数据提交失败");
			return false;
		}
		return true;
	}




	/**
	 * 得到外部传入的数据
	 * 
	 * @return: boolean
	 */
	private boolean getInputData() {
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		// 获得保全受理号
		
		mEdorAcceptNo = (String) mTransferData.getValueByName("EdorAcceptNo");
		if (mEdorAcceptNo == null || mEdorAcceptNo.equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorErrorForceBackBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中EdorAcceptNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		

		mPrtSerNo = (String) mTransferData.getValueByName("PrtSerNo");
		if (mPrtSerNo == null || mPrtSerNo.equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorErrorForceBackBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中EdorAcceptNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得复核结论
		mBackReason = (String) mTransferData.getValueByName("BackReason");
//		if (mBackReason == null || mBackReason.equals("")) {
//			// @@错误处理
//			CError tError = new CError();
//			tError.moduleName = "PEdorErrorForceBackBL";
//			tError.functionName = "getInputData";
//			tError.errorMessage = "前台传输业务数据中BackReason失败!";
//			this.mErrors.addOneError(tError);
//			return false;
//		}
		mOtherReason = (String) mTransferData.getValueByName("OtherReason");
//		if (mOtherReason == null || mOtherReason.equals("")) {
//			// @@错误处理
//			CError tError = new CError();
//			tError.moduleName = "PEdorErrorForceBackBL";
//			tError.functionName = "getInputData";
//			tError.errorMessage = "前台传输业务数据中OtherReason失败!";
//			this.mErrors.addOneError(tError);
//			return false;
//		}
		mLetterType = (String) mTransferData.getValueByName("LetterType");
//		if (mOtherReason == null || mOtherReason.equals("")) {
//			// @@错误处理
//			CError tError = new CError();
//			tError.moduleName = "PEdorErrorForceBackBL";
//			tError.functionName = "getInputData";
//			tError.errorMessage = "前台传输业务数据中OtherReason失败!";
//			this.mErrors.addOneError(tError);
//			return false;
//		}
		if(!"LP01".equals(mLetterType))
		{
			if(!getLPEdorApp())
			{
				CError tError = new CError();
				tError.moduleName = "PEdorErrorForceBackBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "获取保全受理信息失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
				
		}
		return true;
	}

	private boolean dealData()
	{		
		if("BQ37".equals(mLetterType)&&"force".equals(mOperate))
		{
			String tSQL="update LOPRTManager set StateFlag='2',remark='?remark?' where prtseq='?mPrtSerNo?'";
			String tReason=mBackReason+"-"+mOtherReason;
			SQLwithBindVariables sbv1=new SQLwithBindVariables();
			sbv1.sql(tSQL);
			sbv1.put("remark", mBackReason+"-"+mOtherReason);
			sbv1.put("mPrtSerNo", mPrtSerNo);
			map.put(sbv1, "UPDATE");
			
//			// 删除 LOPrtManager 核保通知书
//			String DeleteBQ80SQL = new String("");
//			DeleteBQ80SQL = "delete from LOPrtManager a " + "where a.Code in "
//					+ "(select Code " + "from LDCode "
//					+ "where CodeType = 'bquwnotice') " + "and exists "
//					+ "(select 'X' " + "from LPEdorMain "
//					+ "where EdorAcceptNo = a.StandByFlag4 " + "and EdorAcceptNo = '" //由于发放核保通知书的字段做了调整
//					+ mEdorAcceptNo + "')";
//			map.put(DeleteBQ80SQL, "DELETE");
//			// 删除 LOPrtManager 体检通知书
//			String DeleteBQ90SQL = new String("");
//			DeleteBQ90SQL = "delete from LOPrtManager "
//					+ "where Code = 'BQ90' " + "and StandbyFlag3 in "
//					+ "(select MissionID " + "from LWMission "
//					+ "where ActivityID = '0000000005' "
//					+ "and MissionProp1 = '" + mEdorAcceptNo + "') ";
//			map.put(DeleteBQ90SQL, "DELETE");
			//保全强制终止
			if (!setOverDue(mEdorAcceptNo, "4"))
			{
				CError tError = new CError();
				tError.errorMessage = "保全终止处理失败";
				mErrors.addOneError(tError);
				return false;		
			}
			if(!InsertCancelPRT(tReason))
			{
				CError tError = new CError();
				tError.errorMessage = "插入保单管理表数据失败";
				mErrors.addOneError(tError);
				return false;			
			}	

		}else if(("BQ37".equals(mLetterType)|| "BQ99".equals(mLetterType)||"LP01".equals(mLetterType))&&"normal".equals(mOperate))
		{
			String tSQL="update LOPRTManager set StateFlag='2',remark='?remark?' where prtseq='?mPrtSerNo?'";
			SQLwithBindVariables sbv2=new SQLwithBindVariables();
			sbv2.sql(tSQL);
			sbv2.put("remark", mBackReason+"-"+mOtherReason);
			sbv2.put("mPrtSerNo", mPrtSerNo);
			map.put(sbv2, "UPDATE");
			
		}

		return true;
	}

	/**
	 * 校验保全受理是否已经保全确认
	 * 
	 * @return: boolean
	 */
	private boolean getLPEdorApp() {
		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setEdorAcceptNo(mEdorAcceptNo);
		if (!tLPEdorAppDB.getInfo()) {
			mErrors.copyAllErrors(tLPEdorAppDB.mErrors);
			CError tError = new CError();
			tError.errorMessage = "保全受理信息查询失败!" + "保全受理号：" + mEdorAcceptNo;
			mErrors.addOneError(tError);
			return false;
		}
		mLPEdorAppSchema.setSchema(tLPEdorAppDB.getSchema());
		return true;
	}
	/**
	 * 往打印管理表插入个险审批通知书
	 * 
	 * @return boolean
	 */
	private boolean InsertCancelPRT(String tReason) {
		LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();
		LOPRTManagerSchema mmLOPRTManagerSchema;
		try {
			String Code = PrintManagerBL.CODE_PEdorCancel;// 个险保全撤销通知书

			mmLOPRTManagerSchema = new LOPRTManagerSchema();
			String strNoLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
			String sPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit); // 生成打印流水号
			mmLOPRTManagerSchema.setPrtSeq(sPrtSeq);
			mmLOPRTManagerSchema.setOtherNo(mLPEdorAppSchema.getOtherNo());
			mmLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT); // 保单号
			mmLOPRTManagerSchema.setCode(Code); // 打印类型
			mmLOPRTManagerSchema.setManageCom(mLPEdorAppSchema.getManageCom()); // 管理机构
			mmLOPRTManagerSchema.setAgentCode(""); // 代理人编码
			mmLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
			mmLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);
			mmLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); // 前台打印
			mmLOPRTManagerSchema.setStateFlag("0"); // 打印状态
			mmLOPRTManagerSchema.setPatchFlag("0"); // 补打标志
			mmLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
			mmLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
			mmLOPRTManagerSchema.setOldPrtSeq(sPrtSeq);
			mmLOPRTManagerSchema.setStandbyFlag1(mLPEdorAppSchema.getEdorAcceptNo());//受理号
			mmLOPRTManagerSchema.setStandbyFlag2(tReason);//保存撤销原因
			mLOPRTManagerSet.add(mmLOPRTManagerSchema);
			map.put(mLOPRTManagerSet, "DELETE&INSERT");
		} catch (Exception e) {
			CError.buildErr(this, "插入保全撤销通知书失败!");
			return false;
		}

		return true;
	}
	
	/**
	 * 统一更新保全受理、保全申请批单、保全项目的批改状态为逾期终止
	 * 
	 * @param sEdorAcceptNo
	 * @param sEdorState
	 */
	public boolean setOverDue(String sEdorAcceptNo, String sEdorState) {

		// 校验是否已经有暂交费尚未核销或是银行在途
		if (!checkPayGet(sEdorAcceptNo)) {
			return false;
		}

		// 更新保全批改状态
		updEdorState(sEdorAcceptNo, sEdorState);

		// 删除工作流节点
		delMission(sEdorAcceptNo);

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
        SQLwithBindVariables sbv1=new SQLwithBindVariables();
        sbv1.sql(tSQL);
        sbv1.put("sEdorAcceptNo", sEdorAcceptNo);
		LJSPaySet tLJSPaySet = tLJSPayDB.executeQuery(sbv1);
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
					+ " and (enteraccdate <> '3000-01-01' or enteraccdate is null) "
					+ " and tempfeetype = '4' " + " and  tempfeeno = '"
					//add by jiaqiangli 2009-07-20
					+ "?tempfeeno?" + "' and ConfDate is null ";
			SQLwithBindVariables sbv2=new SQLwithBindVariables();
			sbv2.sql(sql);
			sbv2.put("tempfeeno", tLJSPaySet.get(1).getGetNoticeNo());
			LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
			LJTempFeeSet tLJTempFeeSet = tLJTempFeeDB.executeQuery(sbv2);
			if (tLJTempFeeSet == null || tLJTempFeeSet.size() < 1) {
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
				} 
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
		sbv1.put("sEdorState", sEdorState);
		sbv1.put("mCurrentDate", mCurrentDate);
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
		sbv2.put("sEdorState", sEdorState);
		sbv2.put("mCurrentDate", mCurrentDate);
		sbv2.put("mCurrentTime", mCurrentTime);
		sbv2.put("sEdorAcceptNo", sEdorAcceptNo);
		map.put(sbv2, "UPDATE");


		// 保全申请
		sbSQL.setLength(0);
		sbSQL.append(" UPDATE LPEdorApp set EdorState = '").append("?sEdorState?")
				.append("', ModifyDate = '").append("?mCurrentDate?").append(
						"', ModifyTime = '").append("?mCurrentTime?").append("' ")
				.append(wherePart);
		SQLwithBindVariables sbv3=new SQLwithBindVariables();
		sbv3.sql(sbSQL.toString());
		sbv3.put("sEdorState", sEdorState);
		sbv3.put("mCurrentDate", mCurrentDate);
		sbv3.put("mCurrentTime", mCurrentTime);
		sbv3.put("sEdorAcceptNo", sEdorAcceptNo);
		map.put(sbv3, "UPDATE");

	}

	/**
	 * 备份并删除保全受理和无扫描受理节点任务
	 * 
	 * @return boolean
	 */
	private boolean delMission(String sEdorAcceptNo) {
		// 备份 LWMission
		String QueryLWMissionSQL;
		String sWhere = " where ProcessID = '0000000000' "
				+ " and missionid =  "
				+ "( select missionid from lwmission where missionprop1 =  '?sEdorAcceptNo?'" + " union"
				+ " select missionid from lbmission where missionprop1 = '?sEdorAcceptNo?'" + ")";
		QueryLWMissionSQL = " select * from LWMission " + sWhere;
		LWMissionDB tLWMissionDB = new LWMissionDB();
		LWMissionSet tLWMissionSet = new LWMissionSet();
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(QueryLWMissionSQL);
		sbv1.put("sEdorAcceptNo", sEdorAcceptNo);
		tLWMissionSet = tLWMissionDB.executeQuery(sbv1);
		
		VData tInputData = new VData();
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("BusiOutDate", mCurrentDate);
		tTransferData.setNameAndValue("BusiOutTime", mCurrentTime);
		tInputData.add(tTransferData);
		
		if (tLWMissionSet != null && tLWMissionSet.size() > 0) {
			for (int i = 1; i <= tLWMissionSet.size(); i++) {
				LWMissionSchema tLWMissionSchema = tLWMissionSet.get(i);
				ActivityOperator tActivityOperator = new ActivityOperator();
				if (!tActivityOperator.DeleteMission(tLWMissionSchema
						.getMissionID(), tLWMissionSchema.getSubMissionID(),
						tLWMissionSchema.getActivityID(), tInputData)) {
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
        //删除NS，FM等项目产生的险种应收信息
		String delLJSPayPerson = " delete from ljspayperson where getnoticeno in (select edorno from LPEdorItem p where p.edoracceptno='?sEdorAcceptNo?')";
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(delLJSPayPerson);
		sbv2.put("sEdorAcceptNo", sEdorAcceptNo);
		tMap.put(sbv2, "DELETE");
		
		// 再删除 LJSPay
		DeleteSQL = "delete from LJSPay " + "where 1 = 1 " + "and OtherNo = '?sEdorAcceptNo?' " + "and OtherNoType = '10'";
		SQLwithBindVariables sbv3=new SQLwithBindVariables();
		sbv3.sql(DeleteSQL);
		sbv3.put("sEdorAcceptNo", sEdorAcceptNo);
		tMap.put(sbv3, "DELETE");
		
		DeleteSQL = "delete from LJSGet " + "where 1 = 1 " + "and OtherNo = '?sEdorAcceptNo?' " + "and OtherNoType = '10'";
		SQLwithBindVariables sbv4=new SQLwithBindVariables();
		sbv4.sql(DeleteSQL);
		sbv4.put("sEdorAcceptNo", sEdorAcceptNo);
		tMap.put(sbv4, "DELETE");
		
		tMap.add(map);
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
		DeleteSQL = " delete from lcpol where contno in "
				+ " (select contno from lpedoritem where edoracceptno = '?sEdorAcceptNo?') " + " and appflag = '2'";
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(DeleteSQL);
		sbv1.put("sEdorAcceptNo", sEdorAcceptNo);
		tMap.put(sbv1, "DELETE");
		
		DeleteSQL = " delete from lcduty where contno in "
			+ " (select contno from lcpol where contno in ( select contno from lpedoritem where edoracceptno = '?sEdorAcceptNo?') and appflag = '2' ) ";
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(DeleteSQL);
		sbv2.put("sEdorAcceptNo", sEdorAcceptNo);
	   tMap.put(sbv2, "DELETE");
	   
	   
		DeleteSQL = " delete from lcprem where contno in "
			+ " (select contno from lcpol where contno in ( select contno from lpedoritem where edoracceptno = '?sEdorAcceptNo?') and appflag = '2' ) ";
		SQLwithBindVariables sbv3=new SQLwithBindVariables();
		sbv3.sql(DeleteSQL);
		sbv3.put("sEdorAcceptNo", sEdorAcceptNo);
	   tMap.put(sbv3, "DELETE");
	   
	   
		DeleteSQL = " delete from lcget where contno in "
			+ " (select contno from lcpol where contno in ( select contno from lpedoritem where edoracceptno = '?sEdorAcceptNo?') and appflag = '2' ) ";
		SQLwithBindVariables sbv4=new SQLwithBindVariables();
		sbv4.sql(DeleteSQL);
		sbv4.put("sEdorAcceptNo", sEdorAcceptNo);
	   tMap.put(sbv4, "DELETE");
	   
		// 团险
		DeleteSQL = " delete from lcgrppol where grpcontno in "
				+ " (select grpcontno from lpgrpedoritem where edoracceptno = '?sEdorAcceptNo?') " + " and appflag = '2'";
		SQLwithBindVariables sbv11=new SQLwithBindVariables();
		sbv11.sql(DeleteSQL);
		sbv11.put("sEdorAcceptNo", sEdorAcceptNo);
		tMap.put(sbv11, "DELETE");

		DeleteSQL = " delete from lcinsured where contno in "
				+ " (select contno from lccont where grpcontno in "
				+ "   (select grpcontno from lpgrpedoritem where edoracceptno = '?sEdorAcceptNo?')" + "   and appflag = '2')";
		SQLwithBindVariables sbv5=new SQLwithBindVariables();
		sbv5.sql(DeleteSQL);
		sbv5.put("sEdorAcceptNo", sEdorAcceptNo);
		tMap.put(sbv5, "DELETE");

		DeleteSQL = " delete from lccont where grpcontno in "
				+ " (select grpcontno from lpgrpedoritem where edoracceptno = '?sEdorAcceptNo?') " + " and appflag = '2'";
		SQLwithBindVariables sbv6=new SQLwithBindVariables();
		sbv6.sql(DeleteSQL);
		sbv6.put("sEdorAcceptNo", sEdorAcceptNo);
		tMap.put(sbv6, "DELETE");

		DeleteSQL = " delete from lcpol where grpcontno in "
				+ " (select grpcontno from lpgrpedoritem where edoracceptno = '?sEdorAcceptNo?') " + " and appflag = '2'";
		SQLwithBindVariables sbv7=new SQLwithBindVariables();
		sbv7.sql(DeleteSQL);
		sbv7.put("sEdorAcceptNo", sEdorAcceptNo);
		tMap.put(sbv7, "DELETE");
		
		DeleteSQL = " delete from lcduty where contno in "
			+ " (select contno from lcpol where grpcontno in "
			+ " (select grpcontno from lpgrpedoritem where edoracceptno = '?sEdorAcceptNo?') and appflag = '2') " ;
		SQLwithBindVariables sbv8=new SQLwithBindVariables();
		sbv8.sql(DeleteSQL);
		sbv8.put("sEdorAcceptNo", sEdorAcceptNo);
	   tMap.put(sbv8, "DELETE");
	   
	   
		DeleteSQL = " delete from lcprem where contno in "
			+ " (select contno from lcpol where grpcontno in "
			+ " (select grpcontno from lpgrpedoritem where edoracceptno = '?sEdorAcceptNo?') and appflag = '2') " ;
		SQLwithBindVariables sbv9=new SQLwithBindVariables();
		sbv9.sql(DeleteSQL);
		sbv9.put("sEdorAcceptNo", sEdorAcceptNo);
	   tMap.put(sbv9, "DELETE");
	   
	   
		DeleteSQL = " delete from lcget where contno in "
			+ " (select contno from lcpol where grpcontno in "
			+ " (select grpcontno from lpgrpedoritem where edoracceptno = '?sEdorAcceptNo?') and appflag = '2') " ;
		SQLwithBindVariables sbv10=new SQLwithBindVariables();
		sbv10.sql(DeleteSQL);
		sbv10.put("sEdorAcceptNo", sEdorAcceptNo);
	   tMap.put(sbv10, "DELETE");

		map.add(tMap);

		return tMap;
	}
	/**
	 * 返回处理结果
	 * 
	 * @return: VData
	 */
	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "zhangtao";
		tG.ManageCom = "86110000";

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("EdorAcceptNo", "6120070319000021");
		tTransferData.setNameAndValue("ApproveFlag", "1");
		tTransferData.setNameAndValue("ApproveContent", "zhangtao test");
		tTransferData.setNameAndValue("MissionID", "00000000000002891264");

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);

		PEdorErrorForceBackBL tPEdorErrorForceBackBL = new PEdorErrorForceBackBL();

		if (!tPEdorErrorForceBackBL.submitData(tVData, "")) {
			// logger.debug(tPEdorErrorForceBackBL.mErrors.getError(0).errorMessage);
		}

	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return this.mErrors;
	}





}
