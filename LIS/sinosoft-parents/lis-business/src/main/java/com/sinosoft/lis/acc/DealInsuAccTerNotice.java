package com.sinosoft.lis.acc;
import org.apache.log4j.Logger;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.bq.PEdorAADetailBL;
import com.sinosoft.lis.db.LCInsureAccDB;
import com.sinosoft.lis.db.LCInsureAccTraceDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.finfee.FinFeePubFun;
import com.sinosoft.lis.pubfun.CalBase;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.*;
import com.sinosoft.utility.*;
public class DealInsuAccTerNotice {
private static Logger logger = Logger.getLogger(DealInsuAccTerNotice.class);

	/**
	 * @snowman
	 */
	private String _DealDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();
	private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();
	private GlobalInput _GlobalInput = new GlobalInput();
	private int mSumInsuBala = 0;
	private double mAdminFee = 0;
	public DealInsuAccTerNotice(GlobalInput tGlobalInput)
	{
		_GlobalInput = tGlobalInput;
	}
	public void CheckAccValue(LCPolSchema tLCPolSchema, String tDealDate)
	{
		_DealDate = tDealDate;
		LCInsureAccSchema tLCInsureAccSchema = new LCInsureAccSchema();
		LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet();
		LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
		tLCInsureAccDB.setPolNo(tLCPolSchema.getPolNo());
		tLCInsureAccSet = tLCInsureAccDB.query();
		double PolAccBala = 0.00;
		double PolAccGL = 0.00;
		int tsum = 0;
		int tsum1 = 0;
		for(int i=1;i<= tLCInsureAccSet.size();i++)
		{
			tLCInsureAccSchema = tLCInsureAccSet.get(i);
			PolAccBala =
                        -PubInsuAccFun.calPrice( -tLCInsureAccSchema.
                        getUnitCount(),
                        _DealDate,
                        tLCInsureAccSchema.getInsuAccNo());
			mSumInsuBala +=PolAccBala;
			logger.debug("帐户："+tLCInsureAccSchema.getInsuAccNo()+"现金价值为"+PolAccBala);
			PolAccGL  = calGLPrice(tLCInsureAccSchema.getPolNo(),tLCInsureAccSchema.getInsuAccNo(),tLCInsureAccSchema.getRiskCode(),_DealDate);
			mAdminFee+=PolAccGL;
			logger.debug("帐户："+tLCInsureAccSchema.getInsuAccNo()+"本次收取的管理费为"+PolAccGL);
			if((PolAccGL==0 && PolAccBala==0)||(PolAccBala<3*PolAccGL))
			{
				tsum +=1;
			}
			if((PolAccGL==0 && PolAccBala==0)||(PolAccBala<PolAccGL))
			{
				tsum1 +=1;
			}
		}
		logger.debug("余额不足的帐户个数为"+tsum);
		if(tsum > 0 && tsum==tLCInsureAccSet.size())
		{
			
			if(InsertPRT( tLCInsureAccSet.get(1),"3"))
			{
				logger.debug("插入帐户预警信息");
				MMap mMMap = new MMap();
				mMMap.put(mLOPRTManagerSet,"INSERT");
				VData tVData = new VData();
				tVData.add(mMMap);
		        PubSubmit tPubSubmit = new PubSubmit();
		        if (!tPubSubmit.submitData(tVData, ""))
		        {
		            return;
		        }
		        mLOPRTManagerSet.clear();
			}else{
			    logger.debug("保单"+tLCInsureAccSet.get(1).getPolNo()+"已经发出过预警通知");
			}
		}
		// add by nandd temp
//		String tSql = "select * from LOArrearage where polno='" + tLCPolSchema.getPolNo()
//				+ "' and Payoffflag='0'";
//		LOArrearageDB tLOArrearageDB = new LOArrearageDB();
//		LOArrearageSet tLOArrearageSet = new LOArrearageSet();
//		tLOArrearageSet = tLOArrearageDB.executeQuery(tSql);
//		if (tLOArrearageSet.size() > 0)
//		{
//			
//			//有欠费，发失效预警通知书
//			if (InsertPRT(tLCInsureAccSet.get(1), "1"))
//			{
//				logger.debug("插入停效预警信息");
//				MMap mMMap = new MMap();
//				mMMap.put(mLOPRTManagerSet, "INSERT");
//				VData tVData = new VData();
//				tVData.add(mMMap);
//				PubSubmit tPubSubmit = new PubSubmit();
//				if (!tPubSubmit.submitData(tVData, ""))
//				{
//					return;
//				}
//				mLOPRTManagerSet.clear();
//			}
//			else
//			{
//				logger.debug("保单" + tLCInsureAccSet.get(1).getPolNo() + "已经发出过停效预警通知");
//			}
//		}

	}
	public  void CheckBarginrValue(LCPolSchema tLCPolSchema, String tDealDate)
	{
		_DealDate = tDealDate;
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		String tsql = "select * from lcinsureacctrace a where busytype = 'NB' and polno ='"+"?polno?"+"'" +
			      " and valuedate is not null and not exists (select 'x' from lcinsureacctrace where polno = a.polno and moneytype = 'GL'" +
			      " and valuedate <> a.valuedate)";
		sqlbv.sql(tsql);
		sqlbv.put("polno", tLCPolSchema.getPolNo());
		LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
		LCInsureAccTraceSet tLCInsureAccTraceSet =  tLCInsureAccTraceDB.executeQuery(sqlbv);
		if(tLCInsureAccTraceSet.size()!=0)
		{
		//说明是新单	
			if(InsertBarginValue(tLCInsureAccTraceSet.get(1),"1"))
			{
				logger.debug("插入建立帐户通知书");
				MMap mMMap = new MMap();
				mMMap.put(mLOPRTManagerSet,"INSERT");
				VData tVData = new VData();
				tVData.add(mMMap);
		        PubSubmit tPubSubmit = new PubSubmit();
		        if (!tPubSubmit.submitData(tVData, ""))
		        {
		            return;
		        }
			}
		}
		
	}
	public  void CheckBarginBQValue(String edorno, String tDealDate)
	{
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		try {
			MMap mMMap = new MMap();
			String tAgentCode = "";
			String tManagecom = "";
			String tPolNo = "";
			String tContno = "";
			_DealDate = tDealDate;
			SSRS tssrs = new SSRS();
			ExeSQL texesql = new ExeSQL();
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			String strsql = "select edoracceptno,(select managecom from lccont where contno =a.contno),contno from lpedoritem a where edorno = '"
			        + "?edorno?" + "'";
			sqlbv.sql(strsql);
			sqlbv.put("edorno", edorno);
			tssrs = texesql.execSQL(sqlbv);
			if (tssrs != null && tssrs.getMaxRow() > 0) {
				tManagecom = tssrs.GetText(1, 2);
				tContno = tssrs.GetText(1, 3);
			}
			else
			{
				return;
			}
			SQLwithBindVariables tsqlbv = new SQLwithBindVariables();
			String tsql = "select * from LOPRTManager where StandbyFlag4 ='"+"?edorno?"+"'" +
				      " and code='"+"?code?"+"'";
			tsqlbv.sql(tsql);
			tsqlbv.put("edorno", edorno);
			tsqlbv.put("code", AccPrintManagerBL.TL_CODE_BUSINESS);
			SSRS tSSRS  = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(tsqlbv);
			if(tSSRS.getMaxRow()!=0)
			{
				return;
			}
			SQLwithBindVariables tsqlbv1 = new SQLwithBindVariables();
			String tsql1 = "select polno,agentcode from lcpol where contno='"+"?tContno?"+
            "' and polno = mainpolno";
			tsqlbv1.sql(tsql1);
			tsqlbv1.put("tContno", tContno);
			SSRS tSSRS1  = new SSRS();
			ExeSQL tExeSQL1 = new ExeSQL();
			tSSRS1 = tExeSQL1.execSQL(tsqlbv1);
			if(tSSRS1.getMaxRow()!=0)
			{
				tAgentCode = tSSRS1.GetText(1, 2);
				tPolNo = tSSRS1.GetText(1, 1);
			}
			String strNoLimit = PubFun.getNoLimit(tManagecom);
			String sPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit); //生成打印流水号
			tLOPRTManagerSchema.setPrtSeq(sPrtSeq);
			tLOPRTManagerSchema.setOtherNo(tContno);
			tLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_INDPOL); //批单号码
			tLOPRTManagerSchema.setCode(AccPrintManagerBL.TL_CODE_BUSINESS); //打印类型
			tLOPRTManagerSchema.setManageCom(tManagecom); //管理机构
			tLOPRTManagerSchema.setAgentCode(tAgentCode); //代理人编码
			tLOPRTManagerSchema.setReqCom(_GlobalInput.ManageCom);
			tLOPRTManagerSchema.setReqOperator(_GlobalInput.Operator);

			tLOPRTManagerSchema.setStandbyFlag1(tPolNo);
			tLOPRTManagerSchema.setStandbyFlag2(_DealDate); //本次结算日期
			tLOPRTManagerSchema.setStandbyFlag3("10");//保存交易通知书类型
			tLOPRTManagerSchema.setStandbyFlag4(edorno);//批单号码

			tLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); //前台打印
			tLOPRTManagerSchema.setStateFlag("0"); //打印状态
			tLOPRTManagerSchema.setPatchFlag("0"); //补打标志
			tLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
			tLOPRTManagerSchema.setMakeTime(mCurrentTime);
			tLOPRTManagerSchema.setOldPrtSeq(sPrtSeq);
			mLOPRTManagerSet.add(tLOPRTManagerSchema);
			logger.debug("插入保全交易通知书信息");
			mMMap.put(mLOPRTManagerSet,"INSERT");
			VData tVData = new VData();
			tVData.add(mMMap);
	        PubSubmit tPubSubmit = new PubSubmit();
	        if (!tPubSubmit.submitData(tVData, ""))
	        {
	            return;
	        }
		} catch (Exception e) {
			CError.buildErr(this, "插入投连停效通知书失败!");
		}
	}
	public  boolean CheckAccDISValue(LCPolSchema tLCPolSchema, String tDealDate)
	{
		_DealDate = tDealDate;
		double PolAccGL = 0.00;
		LCInsureAccSchema tLCInsureAccSchema = new LCInsureAccSchema();
		LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet();
		LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
		tLCInsureAccDB.setPolNo(tLCPolSchema.getPolNo());
		tLCInsureAccSet = tLCInsureAccDB.query();
		tLCInsureAccSchema = tLCInsureAccSet.get(1);
		for(int i=1;i<= tLCInsureAccSet.size();i++)
		{
			tLCInsureAccSchema = tLCInsureAccSet.get(i);
			PolAccGL  = calGLPrice(tLCInsureAccSchema.getPolNo(),tLCInsureAccSchema.getInsuAccNo(),tLCInsureAccSchema.getRiskCode(),_DealDate);
			mAdminFee= mAdminFee+PolAccGL;
			logger.debug("帐户："+tLCInsureAccSchema.getInsuAccNo()+"本次收取的管理费为"+PolAccGL);
		}
		if(InsertDISPRT(tLCInsureAccSchema))
		{
			logger.debug("插入帐户停效信息");
			MMap mMMap = new MMap();
			mMMap.put(mLOPRTManagerSet,"INSERT");
			VData tVData = new VData();
			tVData.add(mMMap);
	        PubSubmit tPubSubmit = new PubSubmit();
	        if (!tPubSubmit.submitData(tVData, ""))
	        {
	            return false;
	        }
		}
		return true;
	}
	public double calGLPrice(String tPolno,String tInsuAccNo,String tRiskCode,String tPayDate)
	{
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		String tsql = "select (case when sum(money) is not null then sum(money)  else 0 end) from lcinsureacctrace c where polno ='"+"?tPolno?"+"'"+
	             "and riskcode='"+"?tRiskCode?"+"' and insuaccno='"+"?tInsuAccNo?"+"'"+
	             "and valuedate =(select max(valuedate) from lcinsureacctrace where polno = c.polno and valuedate <'"+"?tPayDate?"+"') and moneytype = 'GL'";
		sqlbv.sql(tsql);
		sqlbv.put("tPolno", tPolno);
		sqlbv.put("tRiskCode", tRiskCode);
		sqlbv.put("tInsuAccNo", tInsuAccNo);
		sqlbv.put("tPayDate", tPayDate);
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sqlbv);
		if(tSSRS.getMaxRow()==0)
		return 0.00;
		else
		return -Double.parseDouble(tSSRS.GetText(1, 1));
		
	}
	/*预警通知书*/
	private boolean InsertPRT(LCInsureAccSchema pLCInsureAccSchema,String pCode) {
		LOPRTManagerSchema tLOPRTManagerSchema;
		try {
			String tAgentCode = "";
			String tFirstPayDate="";
			SSRS tssrs = new SSRS();
			ExeSQL texesql = new ExeSQL();
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			String strsql = "select agentcode from lcpol where polno = '"
					+ "?polno?" + "'";
			sqlbv.sql(strsql);
			sqlbv.put("polno", pLCInsureAccSchema.getPolNo());
			tssrs = texesql.execSQL(sqlbv);
			if (tssrs != null && tssrs.getMaxRow() > 0) {
				tAgentCode = tssrs.GetText(1, 1);
			}
			String tSql = "";
			SQLwithBindVariables tsqlbv = new SQLwithBindVariables();
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(pLCInsureAccSchema.getPolNo());
			if(!tLCPolDB.getInfo())
			{
				CError.buildErr(this, "查询保单信息失败!");
			}
			
			// 取上一个保单周月日
            int tInterval = PubFun.calInterval(tLCPolDB.getCValiDate(), _DealDate, "M");
            String tCalDate = PubFun.calDate(tLCPolDB.getCValiDate(), tInterval, "M", null);
			if(pCode.equals("3"))
			{
				tsqlbv = new SQLwithBindVariables();
				tSql = "select * from LOPRTManager where otherno ='" + "?otherno?" + "'"
						+ " and code='" + "?code?" + "' and to_date(standbyflag5,'YYYY-MM-DD') = to_date('" + "?tCalDate?" + "','YYYY-MM-DD')";
				tsqlbv.sql(tSql);
				tsqlbv.put("otherno", pLCInsureAccSchema.getContNo());
				tsqlbv.put("code", AccPrintManagerBL.CODE_InsuAccTLNotice);
				tsqlbv.put("tCalDate", tCalDate);
				tFirstPayDate =tCalDate;
			}
			else if(pCode.equals("1"))
			{
				// add by nandd temp
//				tSql = "select * from LOArrearage where polno='" + pLCInsureAccSchema.getPolNo()
//						+ "' and Payoffflag='0' and paydate = (select min(paydate) from LOArrearage where polno='"
//						+ pLCInsureAccSchema.getPolNo() + "' and Payoffflag='0')";
//				LOArrearageDB tLOArrearageDB = new LOArrearageDB();
//		        LOArrearageSet tLOArrearageSet = new LOArrearageSet();
//		        tLOArrearageSet = tLOArrearageDB.executeQuery(tSql);
//		        //没有欠款说明帐户价值足够
//		        if (tLOArrearageSet.size() == 0)
//		        {
//		            return true;
//		        }
//		        tFirstPayDate = tLOArrearageSet.get(1).getPayDate();
				tsqlbv = new SQLwithBindVariables();
				tSql = "select * from LOPRTManager where otherno ='" + "?otherno?" + "'"
						+ " and code='" + "?code?"
						+ "' and to_date(standbyflag5,'YYYY-MM-DD') = to_date('" + "?tFirstPayDate?" + "','YYYY-MM-DD')";
				tsqlbv.sql(tSql);
				tsqlbv.put("otherno", pLCInsureAccSchema.getContNo());
				tsqlbv.put("code", AccPrintManagerBL.CODE_InsuAccTLTXYJNotice);
				tsqlbv.put("tFirstPayDate", tFirstPayDate);
			}
			
			SSRS tSSRS  = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(tsqlbv);
			if(tSSRS.getMaxRow()!=0)
			{
				return false;
			}
			tLOPRTManagerSchema = new LOPRTManagerSchema();
			String strNoLimit = PubFun.getNoLimit(pLCInsureAccSchema
					.getManageCom());
			String sPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit); //生成打印流水号
			tLOPRTManagerSchema.setPrtSeq(sPrtSeq);
			tLOPRTManagerSchema.setOtherNo(pLCInsureAccSchema.getContNo());
			tLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_INDPOL); //个人保单号
			if(pCode.equals("3"))
			tLOPRTManagerSchema.setCode(AccPrintManagerBL.CODE_InsuAccTLNotice); //打印类型
			else if(pCode.equals("1"))
			tLOPRTManagerSchema.setCode(AccPrintManagerBL.CODE_InsuAccTLTXYJNotice); //打印类型				
			tLOPRTManagerSchema.setManageCom(pLCInsureAccSchema.getManageCom()); //管理机构
			tLOPRTManagerSchema.setAgentCode(tAgentCode); //代理人编码
			tLOPRTManagerSchema.setReqCom(_GlobalInput.ManageCom);
			tLOPRTManagerSchema.setReqOperator(_GlobalInput.Operator);

			tLOPRTManagerSchema.setStandbyFlag1(pLCInsureAccSchema.getPolNo());
			tLOPRTManagerSchema.setStandbyFlag2(_DealDate); //本次结算日期

			//计算保单管理费
			CalBase tCalBase = new CalBase();
			tCalBase.setAmnt(tLCPolDB.getAmnt());
			tCalBase.setAppntAge(PubFun.calAppAge(tLCPolDB.getInsuredBirthday(), tCalDate, "Y"));
			tCalBase.setSex(tLCPolDB.getInsuredSex());
			tCalBase.setOccupation(tLCPolDB.getOccupationType());
			SQLwithBindVariables ttsqlbv = new SQLwithBindVariables();
			ttsqlbv.sql("select (case when sum(SuppRiskScore) is not null then sum(SuppRiskScore)  else 0 end) from lcprem where polno='" + "?polno?"+ "'");
			ttsqlbv.put("polno", tLCPolDB.getPolNo());
			tCalBase.setSuppRiskScore(Double.parseDouble(tExeSQL.getOneValue(ttsqlbv)));
			double tManageFee = PEdorAADetailBL.calInsuAccManageFee(pLCInsureAccSchema, tCalBase, tCalDate);
			tLOPRTManagerSchema.setStandbyFlag3(String.valueOf(mSumInsuBala));//保存本此的帐户余额
			tLOPRTManagerSchema.setStandbyFlag4(String.valueOf(tManageFee));//管理费之和
			tLOPRTManagerSchema.setStandbyFlag5(tFirstPayDate);

			tLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); //前台打印
			tLOPRTManagerSchema.setStateFlag("0"); //打印状态
			tLOPRTManagerSchema.setPatchFlag("0"); //补打标志
			tLOPRTManagerSchema.setMakeDate(_DealDate);
			tLOPRTManagerSchema.setMakeTime(mCurrentTime);
			tLOPRTManagerSchema.setOldPrtSeq(sPrtSeq);
			mLOPRTManagerSet.add(tLOPRTManagerSchema);
		} catch (Exception e) {
			CError.buildErr(this, "插入投连预警通知书失败!");
		}
		return true;
	}
	/*停效通知书*/
	private boolean InsertDISPRT(LCInsureAccSchema pLCInsureAccSchema) {
		LOPRTManagerSchema tLOPRTManagerSchema;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String tAgentCode = "";
			SSRS tssrs = new SSRS();
			ExeSQL texesql = new ExeSQL();
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			String strsql = "select agentcode from lcpol where polno = '"
					+ "?polno?" + "'";
			sqlbv.sql(strsql);
			sqlbv.put("polno", pLCInsureAccSchema.getPolNo());
			tssrs = texesql.execSQL(sqlbv);
			if (tssrs != null && tssrs.getMaxRow() > 0) {
				tAgentCode = tssrs.GetText(1, 1);
			}
			SQLwithBindVariables tsqlbv = new SQLwithBindVariables();
			String tsql = "select * from LOPRTManager where otherno ='"+"?otherno?"+"'" +
				      " and code='"+"?code?"+"'";
			tsqlbv.sql(tsql);
			tsqlbv.put("otherno", pLCInsureAccSchema.getContNo());
			tsqlbv.put("code", AccPrintManagerBL.CODE_InsuAccDISAVLNotice);
			SSRS tSSRS  = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(tsqlbv);
			if(tSSRS.getMaxRow()!=0)
			{
				return false;
			}
            /*-------------------add by snowman------------------------------*/
            // add by nandd temp
			SQLwithBindVariables tsqlbv0 = new SQLwithBindVariables();
			String tSql0 = "select * from LOArrearage where polno='" +
		            "?polno?" + "' and Payoffflag='0' and paydate = (select min(paydate) from LOArrearage where polno='" +
		            "?polno?" + "' and Payoffflag='0')";
			tsqlbv0.sql(tSql0);
			tsqlbv0.put("polno", pLCInsureAccSchema.getPolNo());
//            LOArrearageDB tLOArrearageDB = new LOArrearageDB();
//            LOArrearageSet tLOArrearageSet = new LOArrearageSet();
//            tLOArrearageSet = tLOArrearageDB.executeQuery(tSql0);
//            String tFirstPayDate = tLOArrearageSet.get(1).getPayDate();
            String tFirstPayDate ="";

            //查询系统帐户宽限期 ， sysvartype='1' 表示开关 1--开 0--关
            SQLwithBindVariables tsqlbv00 = new SQLwithBindVariables();
            tSql0 = "select (case when sysvarvalue is not null then sysvarvalue  else '0' end) from ldsysvar where sysvar='Acc_Avail_Period' and sysvartype='1'";
            tsqlbv00.sql(tSql0);
            ExeSQL tExeSQL0 = new ExeSQL();
            int tAAP = 0;
            String tAAP_SYS = tExeSQL0.getOneValue(tsqlbv00);
            if (tAAP_SYS == null || tAAP_SYS.equals("")) {
	         tAAP_SYS = "60";
            }
            tAAP = Integer.parseInt(tAAP_SYS);
            Date tEndDate = new Date();
            FDate tD = new FDate();
            //得到宽限期最后一天
            tEndDate = FinFeePubFun.calOFDate(tD.getDate(tFirstPayDate), tAAP, "D",
                                tD.getDate(tFirstPayDate));
			/*----------------------------------------*/
			tLOPRTManagerSchema = new LOPRTManagerSchema();
			String strNoLimit = PubFun.getNoLimit(pLCInsureAccSchema
					.getManageCom());
			String sPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit); //生成打印流水号
			tLOPRTManagerSchema.setPrtSeq(sPrtSeq);
			tLOPRTManagerSchema.setOtherNo(pLCInsureAccSchema.getContNo());
			tLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_INDPOL); //个人保单号
			tLOPRTManagerSchema.setCode(AccPrintManagerBL.CODE_InsuAccDISAVLNotice); //打印类型
			tLOPRTManagerSchema.setManageCom(pLCInsureAccSchema.getManageCom()); //管理机构
			tLOPRTManagerSchema.setAgentCode(tAgentCode); //代理人编码
			tLOPRTManagerSchema.setReqCom(_GlobalInput.ManageCom);
			tLOPRTManagerSchema.setReqOperator(_GlobalInput.Operator);

			tLOPRTManagerSchema.setStandbyFlag1(pLCInsureAccSchema.getPolNo());
			tLOPRTManagerSchema.setStandbyFlag2(formatter.format(tEndDate)); //本次结算日期---停效日期

			//计算保单管理费
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(pLCInsureAccSchema.getPolNo());
			if(!tLCPolDB.getInfo())
			{
				CError.buildErr(this, "查询保单信息失败!");
			}

			// 组织计算要素
			CalBase tCalBase = new CalBase();
			tCalBase.setAmnt(tLCPolDB.getAmnt());
			tCalBase.setAppntAge(PubFun.calAppAge(tLCPolDB.getInsuredBirthday(), tFirstPayDate, "Y"));
			tCalBase.setSex(tLCPolDB.getInsuredSex());
			tCalBase.setOccupation(tLCPolDB.getOccupationType());
			SQLwithBindVariables tsqlbv11 = new SQLwithBindVariables();
			tsqlbv11.sql("select (case when sum(SuppRiskScore) is not null then sum(SuppRiskScore)  else 0 end) from lcprem where polno='" + "?polno?"+ "'");
			tsqlbv11.put("polno", tLCPolDB.getPolNo());
			tCalBase.setSuppRiskScore(Double.parseDouble(tExeSQL.getOneValue(tsqlbv11)));
			double tManageFee = PEdorAADetailBL.calInsuAccManageFee(pLCInsureAccSchema, tCalBase, tFirstPayDate);
			
//			String tNoEnoughDate = "select max(valuedate) from lcinsureacctrace where valuedate <'"+_DealDate+"'";
//			SSRS tSSRSNoEnoughDate = new SSRS();
//			ExeSQL tExeSQLNoEnoughDate = new ExeSQL();
//			tSSRSNoEnoughDate = tExeSQLNoEnoughDate.execSQL(tNoEnoughDate);
			tLOPRTManagerSchema.setStandbyFlag3(tFirstPayDate);//不足以支付的日期
			tLOPRTManagerSchema.setStandbyFlag4(String.valueOf(tManageFee));//扣管理费

			tLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); //前台打印
			tLOPRTManagerSchema.setStateFlag("0"); //打印状态
			tLOPRTManagerSchema.setPatchFlag("0"); //补打标志
			tLOPRTManagerSchema.setMakeDate(_DealDate);
			tLOPRTManagerSchema.setMakeTime(mCurrentTime);
			tLOPRTManagerSchema.setOldPrtSeq(sPrtSeq);
			mLOPRTManagerSet.add(tLOPRTManagerSchema);
		} catch (Exception e) {
			CError.buildErr(this, "插入投连停效通知书失败!");
		}
		return true;
	}
	/*交易通知书*/
	private boolean InsertBarginValue(LCInsureAccTraceSchema pLCInsureAccTraceSchema,String othernotype) {
		LOPRTManagerSchema tLOPRTManagerSchema;
		try {
			String tAgentCode = "";
			String tOtherno = "";
			SSRS tssrs = new SSRS();
			ExeSQL texesql = new ExeSQL();
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			String strsql = "select agentcode from lcpol where polno = '"
					+ "?polno?" + "'";
			sqlbv.sql(strsql);
			sqlbv.put("polno", pLCInsureAccTraceSchema.getPolNo());
			tssrs = texesql.execSQL(sqlbv);
			if (tssrs != null && tssrs.getMaxRow() > 0) {
				tAgentCode = tssrs.GetText(1, 1);
			}
			SQLwithBindVariables tsqlbv = new SQLwithBindVariables();
			String tsql = "select * from LOPRTManager where otherno ='"+"?otherno?"+"'" +
				      " and code='"+"?code?"+"'";
			tsqlbv.sql(tsql);
			tsqlbv.put("otherno", pLCInsureAccTraceSchema.getContNo());
			tsqlbv.put("code", AccPrintManagerBL.TL_CODE_BUSINESS);
			SSRS tSSRS  = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(tsqlbv);
			if(tSSRS.getMaxRow()!=0)
			{
				return false;
			}
			LCInsureAccSchema pLCInsureAccSchema = new LCInsureAccSchema();
			LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
			tLCInsureAccDB.setPolNo(pLCInsureAccTraceSchema.getPolNo());
			LCInsureAccSet tLCInsureAccSet =  tLCInsureAccDB.query();
			pLCInsureAccSchema = tLCInsureAccSet.get(1);
			if(othernotype.equals("1"))
			{
				tOtherno = pLCInsureAccSchema.getContNo();
			}
			else
			{
				tOtherno = pLCInsureAccSchema.getContNo();
			}
			SQLwithBindVariables ttsqlbv = new SQLwithBindVariables();
			String Sql = "Select min(StartDate) from LOAccUnitPrice where insuaccno='" +
	                "?insuaccno?" + "' "
	                + " and StartDate >='" + "?startdate?" +
	                "' and state='0'";
			ttsqlbv.sql(Sql);
			ttsqlbv.put("insuaccno", pLCInsureAccTraceSchema.getInsuAccNo());
			ttsqlbv.put("startdate", pLCInsureAccTraceSchema.getPayDate());
            ExeSQL nExeSQL = new ExeSQL();
            SSRS nSSRS = new SSRS();
            nSSRS = nExeSQL.execSQL(ttsqlbv);
            String LastStartDate = nSSRS.GetText(1, 1);
			tLOPRTManagerSchema = new LOPRTManagerSchema();
			String strNoLimit = PubFun.getNoLimit(pLCInsureAccSchema
					.getManageCom());
			String sPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit); //生成打印流水号
			tLOPRTManagerSchema.setPrtSeq(sPrtSeq);
			tLOPRTManagerSchema.setOtherNo(pLCInsureAccSchema.getContNo());
			tLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_INDPOL); //个人保单号
			tLOPRTManagerSchema.setCode(AccPrintManagerBL.TL_CODE_BUSINESS); //打印类型
			tLOPRTManagerSchema.setManageCom(pLCInsureAccSchema.getManageCom()); //管理机构
			tLOPRTManagerSchema.setAgentCode(tAgentCode); //代理人编码
			tLOPRTManagerSchema.setReqCom(_GlobalInput.ManageCom);
			tLOPRTManagerSchema.setReqOperator(_GlobalInput.Operator);

			tLOPRTManagerSchema.setStandbyFlag1(pLCInsureAccSchema.getPolNo());
			tLOPRTManagerSchema.setStandbyFlag2(LastStartDate); //本次结算日期
			tLOPRTManagerSchema.setStandbyFlag3(othernotype);//保存交易通知书类型
			tLOPRTManagerSchema.setStandbyFlag4(tOtherno);//保存本次的所有管理费的三倍

			tLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); //前台打印
			tLOPRTManagerSchema.setStateFlag("0"); //打印状态
			tLOPRTManagerSchema.setPatchFlag("0"); //补打标志
			tLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
			tLOPRTManagerSchema.setMakeTime(mCurrentTime);
			tLOPRTManagerSchema.setOldPrtSeq(sPrtSeq);
			mLOPRTManagerSet.add(tLOPRTManagerSchema);
		} catch (Exception e) {
			CError.buildErr(this, "插入投连停效通知书失败!");
		}
		return true;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
