package com.sinosoft.lis.bq;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCContStateDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LOLoanDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.InsuAccBala;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCContStateSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LOLoanSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LOLoanSet;
import com.sinosoft.service.CovBase;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.RSWrapper;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class ContInvaliBLMultTMThread extends CovBase{
private static Logger logger = Logger.getLogger(ContInvaliBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private MMap mMap = new MMap();
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	private int invalidNum = 0; // 成功失效险种的数目
	private String mEndDate = ""; // 旧状态结束日期
	private double mSPay = 0; // 应收总额
	private double mAPay = 0; // 暂交费
	private String mManageCom = "";
	private LCPolSchema mLCPolSchema = new LCPolSchema();
	private TransferData mTransferData = new TransferData();
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();
	private GlobalInput mGlobalInput = null;
	private Vector mInputDataNew;
	/**保全保单结算计算类 */
	BqPolBalBL tBqPolBalBL = new BqPolBalBL();

	public ContInvaliBLMultTMThread() {
	}

	public void setObject(Object tObject) {
		//多线程的外部参数条件
		mInputDataNew = (Vector) tObject;
	}
	
	public void run() {
		TransferData mTransferData = new TransferData();
		
		MMap tMMap = new MMap();
		for(int i=0;i<this.mInputDataNew.size();i++)
		{
			LCPolSet tLCPolSet=new LCPolSet();
			Map tMap = new HashMap();
			tMap = (Map)mInputDataNew.get(i);
			VData mInputData = new VData();
			mInputData.add(tMap.get("GlobalInput"));
			String InvalidationStateReason = (String)tMap.get("state");
			mGlobalInput=(GlobalInput)tMap.get("GlobalInput");
			if(InvalidationStateReason.equals("01")){
					LCContStateSchema tLCContStateSchema = (LCContStateSchema)tMap.get("LCContStateSchema");
					LCContStateSet tLCContStateSet=new LCContStateSet();
					tLCContStateSet.add(tLCContStateSchema);
					PubFun.logPerformance (mGlobalInput,mGlobalInput.LogID[1],"失效预终止批处理启动","41");

						advancePreValiData(tLCContStateSet);
					
			}else if(InvalidationStateReason.equals("02")){
					LCContStateSchema tLCContStateSchema =  (LCContStateSchema)tMap.get("LCContStateSchema");
					LCContStateSet tLCContStateSet=new LCContStateSet();
					tLCContStateSet.add(tLCContStateSchema);
					PubFun.logPerformance (mGlobalInput,mGlobalInput.LogID[1]," 失效终止批处理启动","42");

					preValiData(tLCContStateSet);
					
					

			}
			else{
					LCPolSchema tLCPolSchema = (LCPolSchema)tMap.get("LCPolSchema");
					tLCPolSet.add(tLCPolSchema);
					PubFun.logPerformance (mGlobalInput,mGlobalInput.LogID[1],"满期终止批处理启动","43");
		
					dealTerminateData(tLCPolSet);

			}
			
			this.close();
		}
	}
//	团单中的lcpol.polno与mainpolno不能用作判断主附险
	private boolean isMainPol(String tPolNo, String tMainPolNo) {
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		String sql = "select 1 from lcpol where polno = '" + "?tPolNo?" + "' and exists(select 1 from lmriskapp where riskcode=lcpol.riskcode and subriskflag='M') ";
		sqlbv.sql(sql);
		sqlbv.put("tPolNo", tPolNo);
		ExeSQL tExeSQL = new ExeSQL();
		String sMaimFlag = tExeSQL.getOneValue(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			return false;
		}
		if (sMaimFlag != null && sMaimFlag.equals("1")) {
			return true;
		}
		else {
			return false;
		}
	}

	//判断是否续保
	private boolean isRiskRnewFlag(String sPolNo) {
		//add by jiaqiangli 2009-06-21 此处只判断产品定义是否支持续保，lcpol.rnewflag不做判断，这样做的好处是允许客户在宽限期内反悔
		String sql = "select 1 from lcpol where polno = '" + "?sPolNo?" + "' and exists(select 1 from lmrisk where riskcode=lcpol.riskcode and rnewflag = 'Y') ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("sPolNo", sPolNo);
		ExeSQL tExeSQL = new ExeSQL();
		String sRnewFlag = tExeSQL.getOneValue(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			return false;
		}
		if (sRnewFlag != null && sRnewFlag.equals("1")) {
			return true;
		}
		else {
			return false;
		}
	}
	
//	取合同下的最大的续保宽末期
	private boolean isOverRnewGrace(String tPolNo,String tPayToDate) {
		FDate tFDate = new FDate();
		//int tGracePeriod = getGracePeriod(tPolNo);
		
		String  tRnewLapseDate;
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(tPolNo);
		if(!tLCPolDB.getInfo())
		{
			return false;
		}
		if("00000000000000000000".equals(tLCPolDB.getGrpContNo()))
		{
			tPolNo = tLCPolDB.getMainPolNo();
		}
		if (isRiskRnewFlag(tPolNo) == true)
		{
			tRnewLapseDate = PubFun.calDate(tPayToDate,15,"D",null);
		}
		else
		{
			tRnewLapseDate = EdorCalZT.CalLapseDate(mLCPolSchema.getRiskCode(),
					tPayToDate);
		}
		
		//add by jiaqiangli 2009-06-21 bug 4 满期终止 此处应该过宽限期止期
		//否则对于垫缴件的附加险满期终止将于宽限期止期当日给满期终止01了，导致次日对主险的垫缴存在问题
		//若次日失效的话，statereason又不对应该是07但被满期置成01了
		// < = before
		// > = after(!after也就是<=) 也就是说这里宁可往后延长一天判断(与失效保持一致宽限期止期的次日)
		//未过宽限期不终止
		if (!tFDate.getDate(mCurrentDate).after(tFDate.getDate(tRnewLapseDate))) {
			mErrors.addOneError(new CError("未到宽限期止期!"));
			return false;
		}
		return true;
	}
	private boolean canTerminate(LCPolSchema tLCPolSchema) {
		
		//主险满期终止
		if (isMainPol(tLCPolSchema.getPolNo(),tLCPolSchema.getMainPolNo()) == true) {
			//交费期满
			if (tLCPolSchema.getPaytoDate().equals(tLCPolSchema.getPayEndDate())) {
				//是否续保
				if (isRiskRnewFlag(tLCPolSchema.getPolNo()) == true) {
					//是否过续保宽末
					if (isOverRnewGrace(tLCPolSchema.getPolNo(),tLCPolSchema.getPaytoDate()) == true) {
						if(!checkTempFee(tLCPolSchema.getContNo()))
						{
							return false;
						}
						return true;
					}
					else {
						return false;
					}
				}
				else {
					return true;
				}
			}
			else {
				return false;
			}
		}
		//附加险满期终止
		else {
			if (isMainTerminate(tLCPolSchema.getContNo()) == true) {
				return true;
			}
			else {
				if (tLCPolSchema.getPaytoDate().equals(tLCPolSchema.getPayEndDate())) {
					//是否续保
					if (isRiskRnewFlag(tLCPolSchema.getPolNo()) == true) {
						//先判断该险种是否允许续保,如果不允许续保，直接返回进行终止操作  未了防止客户反悔，暂时不能判断续保标志
//						if(!"-1".equals(String.valueOf(tLCPolSchema.getRnewFlag())))
//						{
//							return true;
//						}
						//在判断是否过续保宽末
						if (isOverRnewGrace(tLCPolSchema.getPolNo(),tLCPolSchema.getPaytoDate()) == true) {
							if(!checkTempFee(tLCPolSchema.getContNo()))
							{
								return false;
							}
							return true;
						}
						else 
						{
							//如果在宽限期内，就判断附加险的缴至日期是否小于主险的缴至日期，如果是,在判断该险种是否被设置为非续保，如果是，直接终止附加险。
							LCPolDB tLCPolDB = new LCPolDB();
							tLCPolDB.setPolNo(tLCPolSchema.getMainPolNo());
							if(tLCPolDB.getInfo())
							{
								FDate tFDate = new FDate();
								if(tFDate.getDate(tLCPolDB.getPaytoDate()).after(tFDate.getDate(tLCPolSchema.getPaytoDate()))
										&&!"-1".equals(String.valueOf(tLCPolSchema.getRnewFlag())))
								{
									return true;
								}
							}
							return false;
						}
					}
					else {
						return true;
					}
				}
				else {
					return false;
				}
			}
		}
	}
	
	
	//主险是否终止
	private boolean isMainTerminate(String tContNo) {
//		String sql = "select 1 from (select appflag from lcpol where ContNo = '" + tContNo + "'  and exists(select 1 from lmriskapp where riskcode=lcpol.riskcode and subriskflag='M') order by cvalidate desc ) where rownum<=1 and appflag='4'";
		String sql="";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
		sql = "select case (select appflag from (select appflag  from lcpol where ContNo = '" + "?tContNo?" + "' "
					+" and exists (select 1 from lmriskapp 	where riskcode = lcpol.riskcode "
					+" and subriskflag = 'M') and appflag in ('1','4') " 
					+" order by cvalidate desc) where rownum <= 1) when '4' then 1 else 0 end from dual";
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
		sql = "select case (select appflag from (select appflag  from lcpol where ContNo = '" + "?tContNo?" + "' "
					+" and exists (select 1 from lmriskapp 	where riskcode = lcpol.riskcode "
					+" and subriskflag = 'M') and appflag in ('1','4') " 
					+" order by cvalidate desc) a limit 0,1) when '4' then 1 else 0 end from dual";	
		}
		sqlbv.sql(sql);
		sqlbv.put("tContNo", tContNo);
		ExeSQL tExeSQL = new ExeSQL();
		String sMaimFlag = tExeSQL.getOneValue(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			return false;
		}
		if (sMaimFlag != null && sMaimFlag.equals("1")) {
			return true;
		}
		else {
			return false;
		}
	}
	// 校验银行在途和暂交费信息
	private boolean checkTempFee(String sPolNo) {
		String strSql = " select bankonthewayflag, getnoticeno from ljspay "
				+ " where otherno = '" + "?otherno?"
				+ "' and othernotype in('2','3') ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(strSql);
		sqlbv.put("otherno", mLCPolSchema.getContNo());
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tssrs = tExeSQL.execSQL(sqlbv);
//		if (tssrs == null || tssrs.getMaxRow() < 1) {
//			return false;
//		}
		// add by jiaqiangli 2009-04-08 有应收才去判断
		if (tssrs != null && tssrs.getMaxRow() >= 1) {
			String tBankFlag = tssrs.GetText(1, 1);
			String tNoticeNo = tssrs.GetText(1, 2);
			// mSPay = Double.parseDouble(tssrs.GetText(1, 3));
			if (tBankFlag != null && !tBankFlag.trim().equals("") && tBankFlag.trim().equals("1")) {
				logger.debug(sPolNo + "处于银行划款期间");
				return false;
			}
			// 如果已有暂交费，不用判断其金额
			strSql = "select paymoney from ljtempfee " + " where tempfeeno = '" + "?tNoticeNo?" + "' "
					+ " and tempfeetype = '2' ";
			sqlbv=new SQLwithBindVariables();
			sqlbv.sql(strSql);
			sqlbv.put("tNoticeNo", tNoticeNo);
			tssrs = tExeSQL.execSQL(sqlbv);
			if (tssrs != null && tssrs.getMaxRow() > 0) {
				// mAPay = Double.parseDouble(tssrs.GetText(1, 1));
				// if (mAPay >= mSPay)
				// {
				// logger.debug(sPolNo + "已有足够暂交费");
				return false;
				// }
			}
		}
		return true;
	}

	private boolean dealTerminateData(LCPolSet aLCPolSet) {
		String tLimit = "";
		String serNo = "";
		LCContStateDB tLCContStateDB = new LCContStateDB();
		LCContStateSet tLCContStateSet = new LCContStateSet();
		LCContStateSchema tLCContStateSchema = new LCContStateSchema();
		LCContSchema tLCContSchema = new LCContSchema();
		LCPolSchema tLCPolSchema = new LCPolSchema();
		MMap tMap = new MMap();
		VData tData = new VData();
		PubSubmit tPubSubmit = new PubSubmit();
		boolean mainPolFlag = false;
		for (int i = 1; i <= aLCPolSet.size(); i++) {
			tLCContStateSet.clear();
			
			this.mLCPolSchema = aLCPolSet.get(i);
			
			// 判断是否满足剩余的条件
			if (!canTerminate(aLCPolSet.get(i))) {
				continue;
			}
			tMap = new MMap();
			tPubSubmit = new PubSubmit();

			tLCPolSchema = aLCPolSet.get(i);
			//投保单/保单标志 4 - 终止
			tLCPolSchema.setAppFlag("4");
			//满期标志 1 --满期
			tLCPolSchema.setExpiryFlag("1");
			tLCPolSchema.setModifyDate(mCurrentDate);
			tLCPolSchema.setModifyTime(mCurrentTime);
			tMap.put(tLCPolSchema, "UPDATE");
			mainPolFlag = false;
			if (isMainPol(aLCPolSet.get(i).getPolNo(), aLCPolSet.get(i).getMainPolNo())) {
				mainPolFlag = true;
				tLCContSchema = getLCCont(aLCPolSet.get(i).getContNo());
				if (tLCContSchema != null) {
					//4 - 终止
					tLCContSchema.setAppFlag("4");
					tLCContSchema.setModifyDate(mCurrentDate);
					tLCContSchema.setModifyTime(mCurrentTime);
					tMap.put(tLCContSchema, "UPDATE");
				}

			}
			tLCContStateDB.setContNo(aLCPolSet.get(i).getContNo());
			tLCContStateDB.setPolNo(aLCPolSet.get(i).getPolNo());
			tLCContStateDB.setStateType("Terminate");
			tLCContStateSet = tLCContStateDB.query();
			if (!tLCContStateDB.mErrors.needDealError()) {
				if (tLCContStateSet != null && tLCContStateSet.size() > 0) {
					tLCContStateSchema = tLCContStateSet.get(1).getSchema();
					tLCContStateSet.get(1).setEndDate(aLCPolSet.get(i).getEndDate());
					tLCContStateSet.get(1).setModifyDate(mCurrentDate);
					tLCContStateSet.get(1).setModifyTime(mCurrentTime);
					tMap.put(tLCContStateSet.get(1), "DELETE&INSERT");
				} 
				else {
					tLCContStateSchema = new LCContStateSchema();
					tLCContStateSchema.setContNo(aLCPolSet.get(i).getContNo());
					tLCContStateSchema.setPolNo(aLCPolSet.get(i).getPolNo());
				}
				tLCContStateSchema.setInsuredNo("000000");
				tLCContStateSchema.setStateType("Terminate");
				tLCContStateSchema.setState("1");
				tLCContStateSchema.setStateReason("01");
				tLCContStateSchema.setStartDate(aLCPolSet.get(i).getEndDate());
				tLCContStateSchema.setRemark("");
				tLCContStateSchema.setOperator("000");
				tLCContStateSchema.setMakeDate(mCurrentDate);
				tLCContStateSchema.setMakeTime(mCurrentTime);
				tLCContStateSchema.setModifyDate(mCurrentDate);
				tLCContStateSchema.setModifyTime(mCurrentTime);
				tMap.put(tLCContStateSchema, "DELETE&INSERT");

				//comment by jiaqiangli 2009-03-06 暂时不需要LOPRTManager
				// 准备满期终止通知书打印管理表数据
//				if (mainPolFlag && isLongRisk(aLCPolSet.get(i).getRiskCode())) {
//					LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
//					tLimit = PubFun.getNoLimit("86");
//					serNo = PubFun1.CreateMaxNo("PRTSEQNO", tLimit);
//					tLOPRTManagerSchema.setPrtSeq(serNo);
//					tLOPRTManagerSchema.setOtherNo(aLCPolSet.get(i).getPolNo());
//					tLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_INDPOL); // 个人保单号
//					tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_PEdorExpireTerminate); // 满期终止通知书的代码
//					tLOPRTManagerSchema.setManageCom(aLCPolSet.get(i).getManageCom());
//					tLOPRTManagerSchema.setAgentCode(aLCPolSet.get(i).getAgentCode());
//					tLOPRTManagerSchema.setReqCom("86");
//					tLOPRTManagerSchema.setReqOperator("000");
//					tLOPRTManagerSchema.setPrtType("0"); // 前台打印
//					tLOPRTManagerSchema.setStateFlag("0"); // 提交打印
//					tLOPRTManagerSchema.setMakeDate(mCurrentDate);
//					tLOPRTManagerSchema.setMakeTime(mCurrentTime);
//					tMap.put(tLOPRTManagerSchema, "DELETE&INSERT");
//				}
				logger.debug(aLCPolSet.get(i).getPolNo() + "满期终止");
				tData.clear();
				tData.add(tMap);
				tPubSubmit.submitData(tData, "");
			}
//			日志监控,状态监控
			PubFun.logState (mGlobalInput,tLCContStateSchema.getContNo(),"保单"+tLCContStateSchema.getContNo()+"满期终止处理成功","3");
		}
		return true;
	}
	// 对已失效保单发失效预终止通知书
	// 该保单失效时间不在两年之前，且到下下月时失效时间要满23个月
	private boolean preValiData(LCContStateSet tLCContStateSet) {
		if (tLCContStateSet == null || tLCContStateSet.size() < 1) {
			mErrors.addOneError(new CError("传入的失效预终止保单队列为空！"));
			return false;
		}
		LCContStateSchema tLCContStateSchema = new LCContStateSchema();
		LCContSchema tLCContSchema = new LCContSchema();
		LOPRTManagerSchema tLOPRTManagerSchema = null;
		LCPolSchema tLCPolSchema = null;
		LCPolDB tLCPolDB = null;
		String tPolNo = "";
		String mLimit = "";
		String serNo = "";
		MMap tMap = null;
		VData tData = null;
		PubSubmit tPubSubmit = null;
		for (int i = 1; i <= tLCContStateSet.size(); i++) {
			tLCContStateSchema = new LCContStateSchema();
			tLCContStateSchema.setSchema(tLCContStateSet.get(i));
			tPolNo = tLCContStateSchema.getPolNo();

			String tStartDate = tLCContStateSchema.getStartDate();
			//这里可能与oracle的日期函数不一致
			String tDate = PubFun.calDate(tStartDate, 24, "M", null);

			tLCPolDB = new LCPolDB();
			tLCPolSchema = new LCPolSchema();
			tLCPolDB.setPolNo(tPolNo);
			if (!tLCPolDB.getInfo()) {
				mErrors.addOneError(new CError(tPolNo + "保单信息查询失败！"));
				logger.debug(tPolNo + "保单信息查询失败！");
				continue;
			}
			tLCPolSchema = tLCPolDB.getSchema();
			
			//投保单/保单标志 4 - 终止
			tLCPolSchema.setAppFlag("4");
			tLCPolSchema.setModifyDate(mCurrentDate);
			tLCPolSchema.setModifyTime(mCurrentTime);
			tMap = new MMap();
			tMap.put(tLCPolSchema, "UPDATE");
			
			if (isMainPol(tLCPolSchema.getPolNo(), tLCPolSchema.getMainPolNo())) {
				tLCContSchema = getLCCont(tLCPolSchema.getContNo());
				if (tLCContSchema != null) {
					// 4 - 终止
					tLCContSchema.setAppFlag("4");
					tLCContSchema.setModifyDate(mCurrentDate);
					tLCContSchema.setModifyTime(mCurrentTime);
					tMap.put(tLCContSchema, "UPDATE");
				}
			}

			tLCContStateSchema = new LCContStateSchema();
			tLCContStateSchema.setContNo(tLCPolSchema.getContNo());
			tLCContStateSchema.setPolNo(tLCPolSchema.getPolNo());
			tLCContStateSchema.setInsuredNo("000000");
			tLCContStateSchema.setStateType("Terminate");
			tLCContStateSchema.setState("1");
			// 失效终止
			tLCContStateSchema.setStateReason("07");
			tLCContStateSchema.setStartDate(tDate);
			tLCContStateSchema.setRemark("");
			tLCContStateSchema.setOperator("000");
			tLCContStateSchema.setMakeDate(mCurrentDate);
			tLCContStateSchema.setMakeTime(mCurrentTime);
			tLCContStateSchema.setModifyDate(mCurrentDate);
			tLCContStateSchema.setModifyTime(mCurrentTime);
			tMap.put(tLCContStateSchema, "DELETE&INSERT");

//			tLOPRTManagerSchema = new LOPRTManagerSchema();
//			mLimit = PubFun.getNoLimit("86");
//			serNo = PubFun1.CreateMaxNo("PRTSEQNO", mLimit);
//			tLOPRTManagerSchema.setPrtSeq(serNo);
//			tLOPRTManagerSchema.setOtherNo(tLCPolSchema.getPolNo());
//			tLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_INDPOL); // 个人保单号
//			tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_PEdorPreInvali); // 失效预终止通知书的代码
//			tLOPRTManagerSchema.setManageCom(tLCPolSchema.getManageCom());
//			tLOPRTManagerSchema.setAgentCode(tLCPolSchema.getAgentCode());
//			tLOPRTManagerSchema.setReqCom("86");
//			tLOPRTManagerSchema.setReqOperator("000");
//			tLOPRTManagerSchema.setPrtType("0"); // 前台打印
//			tLOPRTManagerSchema.setStateFlag("0"); // 提交打印
//			tLOPRTManagerSchema.setMakeDate(mCurrentDate);
//			tLOPRTManagerSchema.setMakeTime(mCurrentTime);
//			tLOPRTManagerSchema.setStandbyFlag1(tDate);
//			tMap.put(tLOPRTManagerSchema, "INSERT");

			tData = new VData();
			tPubSubmit = new PubSubmit();
			tData.add(tMap);
			if (!tPubSubmit.submitData(tData, "")) // 数据提交
			{
				this.mErrors.copyAllErrors(tPubSubmit.mErrors);
				mErrors.addOneError(new CError(tPolNo + "提交打印数据失败！"));
				logger.debug(tPolNo + "提交打印数据失败！");
				continue;
			} 
			else {
				logger.debug(tPolNo + "提交打印数据成功！");
			}
//			日志监控,状态监控
			PubFun.logState (mGlobalInput,tLCContStateSchema.getContNo(),"保单"+tLCContStateSchema.getContNo()+"失效终止处理成功","2");
		}
		return true;
	}
	

	private LCContSchema getLCCont(String tContNo) {
		LCContDB tLCContDB = new LCContDB();
		LCContSchema tLCContSchema = new LCContSchema();
		tLCContDB.setContNo(tContNo);
		if (!tLCContDB.getInfo()) {
			return null;
		}
		tLCContSchema = tLCContDB.getSchema();
		return tLCContSchema;
	}

	private boolean getInputData(VData cInputData) {
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		if (mTransferData != null) {
			mManageCom = (String) mTransferData.getValueByName("ManageCom");
		}
		mGlobalInput=(GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);
		//mGlobalInput = new GlobalInput();
        //mGlobalInput.Operator = "000"; // 系统自动操作
        //mGlobalInput.ManageCom = "86";
        mGlobalInput.ManageCom = mManageCom;       
	
		return true;
	}
	public boolean submitData(VData cInputData, String cOperate) {
		if (!getInputData(cInputData)) {
			return false;
		}
		if (cOperate != null && cOperate.trim().equals("Available")) {
			
//			日志监控，性能监控
			PubFun.logPerformance (mGlobalInput,mGlobalInput.LogID[1],"续期未交失效批处理","41");
			// 调用失效批处理
//			callInvaliBL();
			
			//add by jiaqiangli 2008-10-16 垫交保单宽限期外失效批处理
//			日志监控，性能监控
			PubFun.logPerformance (mGlobalInput,mGlobalInput.LogID[1]," 垫交保单宽限期外失效批处理","42");
//			callPayPremOutGraceInvaliBL();
			//add by jiaqiangli 2008-10-16 垫交保单宽限期外失效批处理
			
			//add by jiaqiangli 2008-10-16 保单借款失效批处理
//			日志监控，性能监控
			PubFun.logPerformance (mGlobalInput,mGlobalInput.LogID[1],"保单借款失效批处理","43");
//			callLoanInvaliBL();
			//add by jiaqiangli 2008-10-16 保单借款失效批处理			 
		} 
		else {
			mErrors.addOneError(new CError("不支持的操作字符串！"));
			return false;
		}
		return true;
	}
	private boolean advancePreValiData(LCContStateSet tLCContStateSet) {
		if (tLCContStateSet == null || tLCContStateSet.size() < 1) {
			mErrors.addOneError(new CError("传入的失效预终止保单队列为空！"));
			return false;
		}
		LCContStateSchema tLCContStateSchema = new LCContStateSchema();

		LOPRTManagerSchema tLOPRTManagerSchema = null;
		LCPolSchema tLCPolSchema = null;
		LCPolDB tLCPolDB = null;
		String tPolNo = "";
		String mLimit = "";
		String serNo = "";
		MMap tMap = null;
		VData tData = null;
		PubSubmit tPubSubmit = null;
		EdorCalZT tEdorCalZT = new EdorCalZT(mGlobalInput);
		InsuAccBala tInsuAccBala = null;
		TransferData tTransferData = null;
		for (int i = 1; i <= tLCContStateSet.size(); i++) {
			tMap = new MMap();
			tLCContStateSchema = new LCContStateSchema();
			tLCContStateSchema.setSchema(tLCContStateSet.get(i));
			tPolNo = tLCContStateSchema.getPolNo();

			String tStartDate = tLCContStateSchema.getStartDate();
			//这里可能与oracle的日期函数不一致
			String tTermiDate = PubFun.calDate(tStartDate, 23, "M", null);

			tLCPolDB = new LCPolDB();
			tLCPolSchema = new LCPolSchema();
			//tPolNo 这个是mainpolno
			tLCPolDB.setMainPolNo(tPolNo);
			tLCPolDB.setAppFlag("1");
			LCPolSet tLCPolSet = tLCPolDB.query();
			if (tLCPolSet.size() <= 0) {
				mErrors.addOneError(new CError(tPolNo + "保单信息查询失败！"));
				logger.debug(tPolNo + "保单信息查询失败！");
				continue;
			}
			//encode 险种信息
			String tlistInfo = "";
			double tContLeavingMoney = 0.00;
			double tPolCashValue = 0.00;
			String tStrPolCashValue = "";
			double tPolBonusBalace = 0.00;
			String tStrBonusBalace = "";
			LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
			String tAccBalaSQL = "";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			ExeSQL tExeSQL = new ExeSQL();
			for (int j = 1; j <= tLCPolSet.size(); j++) {
				tLCPolSchema = tLCPolSet.get(j);
				
				//借用lpedoritem来调用getContCashValue(LPEdorItemSchema tLPEdorItemSchema)
	          	LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
	          	tLPEdorItemSchema.setContNo(tLCPolSchema.getContNo());
	          	tLPEdorItemSchema.setEdorType("");
	          	tLPEdorItemSchema.setEdorAppDate(mCurrentDate);
	          	tLPEdorItemSchema.setEdorValiDate(mCurrentDate);
	          	tEdorCalZT.setEdorInfo(tLPEdorItemSchema);
//	          日志监控，过程监控			
				PubFun.logTrack (mGlobalInput,tLCPolSchema.getContNo(),"失效预终止，计算"+tLCPolSchema.getContNo()+"保单的现金价值");
				tPolCashValue = tEdorCalZT.getCashValue(tLCPolSchema.getPolNo(),this.mCurrentDate);
				tStrPolCashValue = PubFun.round(tPolCashValue, 2)+"元";
				tLMRiskAppDB.setRiskCode(tLCPolSchema.getRiskCode());
				tLMRiskAppDB.getInfo();
				if (tLMRiskAppDB.getBonusFlag() != null && tLMRiskAppDB.getBonusFlag().equals("1")) {
					//'000001' 需要做一次结息 其他的累加
					tAccBalaSQL = "select (case when sum(insuaccbala) is not null then sum(insuaccbala) else 0 end) from lcinsureacc where insuaccno in ('000007','000008') and contno='"+"?contno?"+"' and polno='"+"?polno?"+"' ";
					sqlbv.sql(tAccBalaSQL);
					sqlbv.put("contno", tLCPolSchema.getContNo());
					sqlbv.put("polno", tLCPolSchema.getPolNo());
					tPolBonusBalace += Double.parseDouble(tExeSQL.getOneValue(sqlbv));
					// 缁撴伅
					tInsuAccBala = new InsuAccBala();
					tTransferData = new TransferData();
					tTransferData.setNameAndValue("InsuAccNo", "000001");
					tTransferData.setNameAndValue("PolNo", tLCPolSchema.getPolNo());
					tTransferData.setNameAndValue("BalaDate", mCurrentDate);
					VData tVData = new VData();
					tVData.add(mGlobalInput);
					tVData.add(tTransferData);
//			          日志监控，过程监控			
					PubFun.logTrack (mGlobalInput,tLCPolSchema.getContNo(),"失效预终止，计算"+tLCPolSchema.getContNo()+"保单的红利及利息");
					//非万能险的账户型结算
					if (!tInsuAccBala.submitData(tVData, "NonUniversal")) {
						CError.buildErr(this, "复利生息分红结算失败！");
						return false;
					}
					VData tResult = new VData();
					tResult = tInsuAccBala.getResult();
					LCInsureAccSet tLCInsureAccSet = (LCInsureAccSet) tResult.getObjectByObjectName("LCInsureAccSet", 0);
					//累加
					tPolBonusBalace += tLCInsureAccSet.get(1).getInsuAccBala();
					//最后结果
					tStrBonusBalace = PubFun.round(tPolBonusBalace,2)+"元";
				}
				//tBonusBalace 计算红利账户余额
				tlistInfo = tlistInfo+tLMRiskAppDB.getRiskName()+"|"+tStrPolCashValue+"|"+tStrBonusBalace+"$";
				tContLeavingMoney += tContLeavingMoney + tLCPolSchema.getLeavingMoney();
			}
//	          日志监控，过程监控			
			PubFun.logTrack (mGlobalInput,tLCPolSchema.getContNo(),"失效预终止，生成"+tLCPolSchema.getContNo()+"保单的失效预终止通知书数据");
			tLOPRTManagerSchema = new LOPRTManagerSchema();
			mLimit = PubFun.getNoLimit("86");
			serNo = PubFun1.CreateMaxNo("PRTSEQNO", mLimit);
			tLOPRTManagerSchema.setPrtSeq(serNo);
			tLOPRTManagerSchema.setOtherNo(tLCContStateSchema.getContNo());
			tLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT); // 个人保单号
			tLOPRTManagerSchema.setCode(PrintManagerBL.CODE_PEdorPreInvali); // 失效预终止通知书的代码
			tLOPRTManagerSchema.setManageCom(tLCPolSchema.getManageCom());
			tLOPRTManagerSchema.setAgentCode(tLCPolSchema.getAgentCode());
			tLOPRTManagerSchema.setReqCom("86");
			tLOPRTManagerSchema.setReqOperator("000");
			tLOPRTManagerSchema.setPrtType("0"); // 前台打印
			tLOPRTManagerSchema.setStateFlag("0"); // 提交打印
			
			tLOPRTManagerSchema.setRemark(tlistInfo);
			
			tLOPRTManagerSchema.setMakeDate(mCurrentDate);
			tLOPRTManagerSchema.setMakeTime(mCurrentTime);
			tLOPRTManagerSchema.setStandbyFlag1(tTermiDate);
			
			//存放保单账户余额 leavingmongey
			tLOPRTManagerSchema.setStandbyFlag2(tContLeavingMoney+"元");
			
			tMap.put(tLOPRTManagerSchema, "INSERT");

			tData = new VData();
			tPubSubmit = new PubSubmit();
			tData.add(tMap);
			if (!tPubSubmit.submitData(tData, "")) // 数据提交
			{
				this.mErrors.copyAllErrors(tPubSubmit.mErrors);
				mErrors.addOneError(new CError(tPolNo + "提交打印数据失败！"));
				logger.debug(tPolNo + "提交打印数据失败！");
				continue;
			} 
			else {
				logger.debug(tPolNo + "提交打印数据成功！");
			}
             //日志监控,状态监控
			PubFun.logState (mGlobalInput,tLCContStateSchema.getContNo(),"保单"+tLCContStateSchema.getContNo()+"失效预终止处理成功","1");
		}
		return true;
	}
	public static void main(String[] args) {
		logger.debug("start ContInvaliBL");
		String tManageCom = "8611";

		VData tVData = new VData();
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ManageCom", tManageCom);
		tVData.add(tTransferData);

		ContInvaliBL tContInvaliBL = new ContInvaliBL();

		logger.debug("Start ContInvaliBL");
		if (!tContInvaliBL.submitData(tVData, "Available")) {
			logger.debug(tContInvaliBL.mErrors.getError(0).errorMessage);
		}

		logger.debug("end ContInvaliBL");
	}
}
