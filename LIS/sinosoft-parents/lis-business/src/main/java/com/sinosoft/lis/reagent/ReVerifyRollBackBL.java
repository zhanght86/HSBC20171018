package com.sinosoft.lis.reagent;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCContStateDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LCRnewStateHistoryDB;
import com.sinosoft.lis.db.LJAPayDB;
import com.sinosoft.lis.db.LJAPayPersonDB;
import com.sinosoft.lis.operfee.RNHangUp;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LBDutySchema;
import com.sinosoft.lis.schema.LBGetSchema;
import com.sinosoft.lis.schema.LBPolSchema;
import com.sinosoft.lis.schema.LBPremSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCContStateSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LCRnewStateHistorySchema;
import com.sinosoft.lis.schema.LCRollBackLogSchema;
import com.sinosoft.lis.schema.LJAPayPersonSchema;
import com.sinosoft.lis.schema.LJAPaySchema;
import com.sinosoft.lis.schema.LJSPaySchema;
import com.sinosoft.lis.vschema.LBDutySet;
import com.sinosoft.lis.vschema.LBGetSet;
import com.sinosoft.lis.vschema.LBPolSet;
import com.sinosoft.lis.vschema.LBPremSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LCRnewStateHistorySet;
import com.sinosoft.lis.vschema.LCRollBackLogSet;
import com.sinosoft.lis.vschema.LJAPayPersonSet;
import com.sinosoft.lis.vschema.LJAPaySet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author HZM
 * @author Y
 * @version 1.0
 */
public class ReVerifyRollBackBL {
private static Logger logger = Logger.getLogger(ReVerifyRollBackBL.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	private GlobalInput tGI = new GlobalInput();

	/** 数据操作字符串 */
	private String mOperate = "";

	private String mSubmitFlag = "";

	private String CurrentDate = PubFun.getCurrentDate();

	/** 数据表 保存数据 */
	//保单号
	private String mGetNoticeNo = "";
	private String mContNo = "";
	private String mPrtNo = "";
	private String mReaSonType = "";
	private String mReMark = "";
	private TransferData mTransferData = new TransferData();
	
	private Reflections mReflections = new Reflections();
	
	private ExeSQL tExeSQL = new ExeSQL();


	/** 公共锁定号码类 */
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();
	
	//实收表
	private LJAPaySet mLJAPaySet = new LJAPaySet();
	private LJAPayPersonSet mLJAPayPersonSet = new LJAPayPersonSet();

	// 因收总表
	private LJSPaySchema mLJSPaySchema = new LJSPaySchema();
	// 应收个人交费表
	private LJSPayPersonSet mLJSPayPersonSet = new LJSPayPersonSet();
	
	private LJTempFeeSet mLJTempFeeSet = new LJTempFeeSet();
	
	private LCPremSet mLCPremSet_DEL = new LCPremSet();
	//private LCPremSet mLCPremSet_DELINSERT = new LCPremSet();
	private LCPremSet mLCPremSet_UPDATE = new LCPremSet();
	private LBPremSet mLBPremSet = new LBPremSet();
	
	private LCDutySet mLCDutySet_DEL = new LCDutySet();
	private LCDutySet mLCDutySet_DELINSERT = new LCDutySet();
	private LCDutySet mLCDutySet_UPDATE = new LCDutySet();
	private LBDutySet mLBDutySet = new LBDutySet();
	
	private LCGetSet mLCGetSet_DEL = new LCGetSet();
	private LCGetSet mLCGetSet_DELINSERT = new LCGetSet();
	private LCGetSet mLCGetSet_UPDATE = new LCGetSet();
	private LBGetSet mLBGetSet = new LBGetSet();
	
	private LCRnewStateHistorySchema mLCRnewStateHistorySchema_DEL = new LCRnewStateHistorySchema();
	private LCRnewStateHistorySet mLCRnewStateHistorySet_DEL = new LCRnewStateHistorySet();

	private LCPolSet mLCPolSet_UPDATE = new LCPolSet();
	private LCPolSet mLCPolSet_DELINSERT = new LCPolSet();
	private LCPolSet mLCPolSet_DEL= new LCPolSet();
	private LBPolSet mLBPolSet_INSERT = new LBPolSet();
	private LCContSet mLCContSet_UPDATE = new LCContSet();
	private LCContStateSet mLCContStateSet_UPDATE = new LCContStateSet();
	private LCContStateSet mLCContStateSet_DEL = new LCContStateSet();
	//回滚轨迹表
	private LCRollBackLogSet mLCRollBackLogSet_INSERT = new LCRollBackLogSet();
	
	//系统日期及时间
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	private MMap mMMap = new MMap(); //

	private RNHangUp mrn = new RNHangUp(tGI);

	public ReVerifyRollBackBL() {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		if (!getInputData(cInputData)) 
		{
			return false;
		}

        //加锁contno，LR0002：续期核销
		// if (!mPubLock.lock(mContNo, "LR0003", tGI.Operator)) {
		// CError tError = new CError(mPubLock.mErrors.getLastError());
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		try 
		{
			if(!checkData())
			{
				return false;
			}
			logger.debug("Start ReVerifyRollBack dealDate~~");
			// 进行业务处理
			if (dealData() == false) 
			{
				return false;
			}
	
			// 准备数据
			if (!prepareData()) 
			{
				return false;
			}
	
			// 公共提交	
			PubSubmit tPubSubmit = new PubSubmit();
			if (tPubSubmit.submitData(mResult, "") == false) 
			{
				this.mErrors.copyAllErrors(tPubSubmit.mErrors);
				return false;
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			CError.buildErr(this, e.toString());
			return false;
		} 
		finally 
		{
			mPubLock.unLock();// 解锁
		}
		return true;
	}

	//校验函数
	private boolean checkData()
	{
       //校验是否挂起，保全调用不校验挂起
		if (!mOperate.equals("BQApp") || mOperate == null
				|| mOperate.equals("")) {
			if (!mrn.checkHangUP(mContNo)) {
				CError.buildErr(this, "保单已挂起,不能撤销!");
				return false;
			}
		}
		ExeSQL xExeSQL = new ExeSQL();
		//校验是否为保全的垫交或者复效件，是的话不允许回滚
		String BQ_CHECK="";
		BQ_CHECK="select count(*) from ljapay where getnoticeno = '"+"?getnoticeno?"+"'  and incometype = '2' "; //正常续期是2，保全是10
        SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
        sqlbv1.sql(BQ_CHECK);
        sqlbv1.put("getnoticeno", this.mGetNoticeNo);
		if(Integer.parseInt(xExeSQL.getOneValue(sqlbv1))==0)
        {
			CError.buildErr(this, "只允许对正常续期交费进行回退操作！");
			return false;
        }
		String DJ_CHECK="";
		DJ_CHECK=" select count(*) from ljapayperson a where a.getnoticeno = '"+"?mGetNoticeNo?"+"' and a.InInsuAccState='3' ";
		 SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
	        sqlbv2.sql(DJ_CHECK);
	        sqlbv2.put("mGetNoticeNo", this.mGetNoticeNo);
		if(Integer.parseInt(xExeSQL.getOneValue(sqlbv2))>0)
        {
			CError.buildErr(this, "垫交件交费不允许进行回退操作！");
			return false;
        }
		//当前保单有应收数据的话，不允许回退
		String LJS_CHECK ="";
		LJS_CHECK =" select count(*) from ljspay where otherno='"+"?mContNo?"+"'";
		 SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
	        sqlbv3.sql(LJS_CHECK);
	        sqlbv3.put("mContNo", mContNo);
		if(Integer.parseInt(xExeSQL.getOneValue(sqlbv3))>0)
        {
			CError.buildErr(this, "保单有应收数据不允许进行回退操作，请先做催收撤销！");
			return false;
        }
		//交费之后做过保全或者理赔不允许回退
		String JFDate="";
		String str_JFDate=" select substr(makedate,1,10) from ljapay where getnoticeno='"+"?mGetNoticeNo?"+"' "; 
		 SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
	        sqlbv4.sql(str_JFDate);
	        sqlbv4.put("mGetNoticeNo", mGetNoticeNo);
		JFDate = tExeSQL.getOneValue(sqlbv4);
		if(JFDate==null||JFDate.equals(""))
		{
			CError tError = new CError();
			tError.moduleName = "IndiDueFeeCancelBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "保费实收日期获取失败，请您确认!";
			this.mErrors.addOneError(tError);
		}
		String CHECK_STR ="";
		CHECK_STR ="   select  "
		  +"(select count(*)  from lpedoritem d where  d.contno='"+"?mContNo?"+"' and d.edorappdate>'"+"?JFDate?"+"' and d.edorstate='0' and d.edortype<>'RB')"
		  +" + "
		  +"(select count(*) from llclaimpolicy where contno='"+"?mContNo?"+"' and makedate>'"+"?JFDate?"+"') "
		  +" from dual ";
		 SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
	        sqlbv5.sql(CHECK_STR);
	        sqlbv5.put("mContNo", mContNo);
	        sqlbv5.put("JFDate", JFDate);
		if(Integer.parseInt(xExeSQL.getOneValue(sqlbv5))>0)
        {
			CError.buildErr(this, "保单在交费后有过保全或理赔操作，不允许进行回退操作");
			return false;
        }
		//保单失效，或者终止，不允许回退
		String PolState_STR="";
		PolState_STR=" select count(*) from lccontstate a where a.contno='"+"?mContNo?"+"' and a.statetype in ('PayPrem','Available','Terminate') "
	     +" and a.startdate>'"+"?JFDate?"+"' and to_char(now(),'YYYY-MM-DD')>=a.startdate ";
		 SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
	        sqlbv6.sql(CHECK_STR);
	        sqlbv6.put("mContNo", mContNo);
	        sqlbv6.put("JFDate", JFDate);
		if(Integer.parseInt(xExeSQL.getOneValue(sqlbv6))>0)
        {
			CError.buildErr(this, "保单在交费后保单状态发生过变化，不允许进行回退操作");
			return false;
        }
		
		return true;
	}
	// 根据前面的输入数据，进行逻辑处理
	// 如果在处理过程中出错，则返回false,否则返回true
	private boolean prepareData() 
	{
		// 准备Map数据-提交
		mMMap.put(this.mLJAPayPersonSet, "INSERT");
		mMMap.put(this.mLJAPaySet, "INSERT");
		//mMMap.put(this.mLJTempFeeSet, "UPDATE");
		
		mMMap.put(this.mLCPremSet_DEL, "DELETE");
		mMMap.put(this.mLCPremSet_UPDATE, "UPDATE");
		mMMap.put(this.mLBPremSet, "INSERT");
		
		mMMap.put(this.mLCDutySet_DEL, "DELETE");
		mMMap.put(this.mLCDutySet_UPDATE, "UPDATE");
		mMMap.put(this.mLBDutySet, "INSERT");
		
		mMMap.put(this.mLCGetSet_DEL, "DELETE");
		mMMap.put(this.mLCGetSet_UPDATE, "UPDATE");
		mMMap.put(this.mLBGetSet, "INSERT");
		
		mMMap.put(this.mLCContStateSet_DEL, "DELETE");
		mMMap.put(this.mLCContStateSet_UPDATE, "UPDATE");
		
		mMMap.put(this.mLCPolSet_DEL, "DELETE");
		mMMap.put(this.mLCPolSet_UPDATE, "UPDATE");
		mMMap.put(this.mLBPolSet_INSERT, "INSERT");
		
		mMMap.put(this.mLCContSet_UPDATE, "UPDATE");
			
		mMMap.put(this.mLCRnewStateHistorySet_DEL, "DELETE");
		
		mMMap.put(this.mLCRollBackLogSet_INSERT, "INSERT");
		
		mResult.add(mMMap);
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData mInputData) {
		tGI = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mGetNoticeNo =(String)mTransferData.getValueByName("GetNoticeNo");
		mContNo = (String)mTransferData.getValueByName("ContNo");
		mReaSonType =(String)mTransferData.getValueByName("ReaSonType");
		mReMark = (String)mTransferData.getValueByName("ReMark");
		if(mGetNoticeNo==null||mGetNoticeNo.equals(""))
		{
			CError tError = new CError();
			tError.moduleName = "IndiDueFeeCancelBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "收费号获取失败，请您确认!";
			this.mErrors.addOneError(tError);
		}

		if(mContNo==null||mContNo.equals(""))//前台若是没有传合同号，需要查询
		{
			String str_contno=" select distinct(contno) from ljapayperson where getnoticeno='"+"?mGetNoticeNo?"+"' ";
			 SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
		        sqlbv7.sql(str_contno);
		        sqlbv7.put("mGetNoticeNo", mGetNoticeNo);
			mContNo = tExeSQL.getOneValue(sqlbv7);
			if(mContNo==null||mContNo.equals(""))
			{
				CError tError = new CError();
				tError.moduleName = "IndiDueFeeCancelBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "保单号获取失败，请您确认!";
				this.mErrors.addOneError(tError);
			}
		}
		if (tGI == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "IndiDueFeeCancelBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有得到足够的数据，请您确认!";
			this.mErrors.addOneError(tError);

			return false;
		}
		return true;
	}

	/**
	 * 检查续保续期
	 * 
	 * @return
	 */
	private boolean dealData() {
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mContNo);
		if (tLCContDB.getInfo() == false) 
		{
			CError.buildErr(this, "保单查询失败");
			return false;
		} 
		ExeSQL tExeSQL = new ExeSQL();
		//默认回滚操作都是从最新一期的数据处理。
		//先查询出目前所有appflag='1'的保单记录
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = new LCPolSet();
		tLCPolDB.setContNo(mContNo);
		tLCPolDB.setAppFlag("1");
		tLCPolSet = tLCPolDB.query();
		
		LCPolSchema tLCPolSchema = new LCPolSchema();
		String old_GetNoticeNo = "";//旧的收费号
		double sumprem=0;  //总退费金额
		//计算当期总应交保费
		String calsumprem_sql=" select (case when sum(a.sumactupaymoney) is not null then sum(a.sumactupaymoney) else 0 end) from ljapayperson a where a.paytype='ZC' " 
			+" and a.getnoticeno='"+"?getnoticeno?"+"' ";
		 SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
	        sqlbv8.sql(calsumprem_sql);
	        sqlbv8.put("getnoticeno", this.mGetNoticeNo);
		sumprem=Double.parseDouble(tExeSQL.getOneValue(sqlbv8));
		logger.debug("当期总保费金额为："+sumprem);
		int count =1;
		String mainpol_flag="0";  //0非,1是
		String tLimit = PubFun.getNoLimit(tLCPolSet.get(1).getManageCom());
        //生成该次处理的新交费号
		String new_PayNo = PubFun1.CreateMaxNo("PayNo", tLimit);		
		logger.debug("本次处理交费号:::::::::::::::::::" + new_PayNo);
		for(int i=1;i<=tLCPolSet.size();i++)
		{
			boolean main_special=false;
			//上期交至日期
			String lastpaytodate = "";
			mainpol_flag="0";
			tLCPolSchema = new LCPolSchema();
			tLCPolSchema = tLCPolSet.get(i);
			if(tLCPolSchema.getPolNo().equals(tLCPolSchema.getMainPolNo()))
			{
				mainpol_flag="1";
			}
			//非主险情况下，若为趸交或期满件，直接跳过
            //排除趸交及期满件
			if(mainpol_flag.equals("0")&&tLCPolSchema.getPayIntv()==0&&tLCPolSchema.getRnewFlag()==-2)
			{
				logger.debug(tLCPolSchema.getPolNo()+"为非主险趸交件，不作处理！");
				continue;
			}
			if(mainpol_flag.equals("0")&&tLCPolSchema.getPayIntv()!=0&&tLCPolSchema.getPaytoDate().compareTo(tLCPolSchema.getPayEndDate())>=0)
			{
				logger.debug(tLCPolSchema.getPolNo()+"为非主险期满件，不作处理！");
				continue;
			}
			if(mainpol_flag.equals("1")&&
			  (tLCPolSchema.getPayIntv()==0&&tLCPolSchema.getRnewFlag()==-2
				||tLCPolSchema.getPayIntv()!=0&&tLCPolSchema.getPaytoDate().compareTo(tLCPolSchema.getPayEndDate())>=0))
			{
				//主险为趸交或者期满的情况下，需要特殊处理该lcpol记录的leavingmoney
				tLCPolSchema.setLeavingMoney(tLCPolSchema.getLeavingMoney()+sumprem);
				tLCPolSchema.setModifyDate(mCurrentDate);
				tLCPolSchema.setModifyTime(mCurrentTime);
				tLCPolSchema.setOperator(tGI.Operator);			
				mLCPolSet_UPDATE.add(tLCPolSchema);
				//主险非续保趸交或者期满，有可能会有YEL,YET的应收记录需要处理 add by xiongzh 2010-1-14
				main_special=true;
			}
			String new_polno = "";
			new_polno = tLCPolSchema.getPolNo(); //取出保单号
			
			//首先处理实收表记录，添加保费为负值的记录
			LJAPayPersonDB tLJAPayPersonDB = new LJAPayPersonDB();
			LJAPayPersonSet tLJAPayPersonSet = new LJAPayPersonSet();
			tLJAPayPersonDB.setGetNoticeNo(mGetNoticeNo);
			tLJAPayPersonDB.setPolNo(tLCPolSchema.getPolNo());
            //主险非续保趸交或者期满情况下，实收表可能会有YET,YEL记录，但是CurPayToDate并不等于其自身的paytodate
			//因此需要特殊处理，若main_special为true则不加此限制查询条件
			if(!main_special)
			{
			  tLJAPayPersonDB.setCurPayToDate(tLCPolSchema.getPaytoDate());
			}
			
			tLJAPayPersonSet = tLJAPayPersonDB.query();
			if(tLJAPayPersonSet.size()==0)
			{
				logger.debug("保单下险种"+tLCPolSchema.getRiskCode()+"没有实收，无需处理，跳过");
				continue;
			}
			for(int j=1;j<=tLJAPayPersonSet.size();j++)
			{
				LJAPayPersonSchema tLJAPayPersonSchema = new LJAPayPersonSchema();
				tLJAPayPersonSchema = tLJAPayPersonSet.get(j);
				//定义上期交至日期
				lastpaytodate = "";
				lastpaytodate = tLJAPayPersonSchema.getLastPayToDate();
				
				String old_payno=tLJAPayPersonSchema.getPayNo();
				String currency=tLJAPayPersonSchema.getCurrency();
				tLJAPayPersonSchema.setPayNo(new_PayNo);
				//修改记录中的金额，改为负值
				double actprem =tLJAPayPersonSchema.getSumActuPayMoney();				
				tLJAPayPersonSchema.setSumActuPayMoney(-actprem);
				tLJAPayPersonSchema.setMakeDate(this.mCurrentDate);
				tLJAPayPersonSchema.setMakeTime(this.mCurrentTime);
				tLJAPayPersonSchema.setModifyDate(this.mCurrentDate);
				tLJAPayPersonSchema.setModifyTime(this.mCurrentTime);
				tLJAPayPersonSchema.setConfDate(this.mCurrentDate);
				tLJAPayPersonSchema.setOperator(this.tGI.Operator);
				tLJAPayPersonSchema.setTaxAmount(-tLJAPayPersonSchema.getTaxAmount());
				tLJAPayPersonSchema.setNetAmount(-tLJAPayPersonSchema.getNetAmount());
				
				mLJAPayPersonSet.add(tLJAPayPersonSchema);
				
				if(count==1&&j==1) //确保不重复处理
				{
					//处理实收总表
                    //此处需要取出一些数据为回滚其他表的数据做准备
					old_GetNoticeNo = mGetNoticeNo;  //为接下来查询修改暂收费表做准备
					
					LJAPayDB tLJAPayDB = new LJAPayDB();
					LJAPaySchema tLJAPaySchema = new LJAPaySchema();
					tLJAPayDB.setPayNo(old_payno);
					tLJAPayDB.setCurrency(currency);
					if(!tLJAPayDB.getInfo())
					{
						CError.buildErr(this, "查询保单实收总表信息失败！");
						return false;
					}
					tLJAPaySchema = tLJAPayDB.getSchema();
					tLJAPaySchema.setPayNo(new_PayNo);
					double actprem2 =tLJAPaySchema.getSumActuPayMoney();	
					tLJAPaySchema.setSumActuPayMoney(-actprem2);
					tLJAPaySchema.setMakeDate(this.mCurrentDate);
					tLJAPaySchema.setMakeTime(this.mCurrentTime);
					tLJAPaySchema.setModifyDate(this.mCurrentDate);
					tLJAPaySchema.setModifyTime(this.mCurrentTime);
					tLJAPaySchema.setConfDate(this.mCurrentDate);
					tLJAPaySchema.setOperator(this.tGI.Operator);
					tLJAPaySchema.setTaxAmount(-tLJAPaySchema.getTaxAmount());
					tLJAPaySchema.setNetAmount(-tLJAPaySchema.getNetAmount());
					mLJAPaySet.add(tLJAPaySchema);
				}
			}
			if(main_special) //主险非续保趸交或者期满的，到此即完成处理
			{
				count++;
				continue;
			}
			/*
			//然后处理暂收费表
			logger.debug("旧的收费号为："+old_GetNoticeNo);
			LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
			String TempfeeSql = "select * from LJTempFee where TempfeeNo = '"+ old_GetNoticeNo+ "' " 
			+" and riskcode='"+tLCPolSchema.getRiskCode()+"'  ";
			logger.debug("查询暂缴费记录==" + TempfeeSql);
			LJTempFeeSet tLJTempFeeSet = tLJTempFeeDB.executeQuery(TempfeeSql);
			for(int a=1;a<=tLJTempFeeSet.size();a++) 
			{
				LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
				tLJTempFeeSchema = tLJTempFeeSet.get(a);
				tLJTempFeeSchema.setEnterAccDate("");
				tLJTempFeeSchema.setConfDate("");
				tLJTempFeeSchema.setConfFlag("0");
				tLJTempFeeSchema.setModifyDate(mCurrentDate);
				tLJTempFeeSchema.setModifyTime(mCurrentTime);
				tLJTempFeeSchema.setOperator(tGI.Operator);
				
				mLJTempFeeSet.add(tLJTempFeeSchema);
			}
			*/
			//处理lcprem,lcduty,lcget
			LCPremDB tLCPremDB = new LCPremDB();
			LCPremSet tLCPremSet = new LCPremSet();
			tLCPremDB.setPolNo(tLCPolSchema.getPolNo());
			tLCPremDB.setPaytoDate(tLCPolSchema.getPaytoDate());
			tLCPremSet=tLCPremDB.query();
			for(int b=1;b<=tLCPremSet.size();b++)
			{
				LCPremSchema tLCPremSchema = new LCPremSchema();
				tLCPremSchema=tLCPremSet.get(b);
				if(tLCPolSchema.getPayIntv()==0&&tLCPolSchema.getRnewFlag()==-1)
				{
					//备份当期需要回滚的记录
					LBPremSchema tLBPremSchema = new LBPremSchema();
					mReflections.transFields(tLBPremSchema, tLCPremSchema);
					tLBPremSchema.setEdorNo(PubFun1.CreateMaxNo("LBPrem", 20));
					tLBPremSchema.setMakeDate(mCurrentDate);
					tLBPremSchema.setMakeTime(mCurrentTime);
					tLBPremSchema.setModifyDate(mCurrentDate);
					tLBPremSchema.setModifyTime(mCurrentTime);
					tLBPremSchema.setOperator(tGI.Operator);
					mLBPremSet.add(tLBPremSchema);
                    //然后删除lcprem中最新一笔记录
					mLCPremSet_DEL.add(tLCPremSchema);
				}
				else
				{
                    //先备份
					LBPremSchema tLBPremSchema = new LBPremSchema();
					mReflections.transFields(tLBPremSchema, tLCPremSchema);
					tLBPremSchema.setEdorNo(PubFun1.CreateMaxNo("LBPrem", 20));
					tLBPremSchema.setMakeDate(mCurrentDate);
					tLBPremSchema.setMakeTime(mCurrentTime);
					tLBPremSchema.setModifyDate(mCurrentDate);
					tLBPremSchema.setModifyTime(mCurrentTime);
					tLBPremSchema.setOperator(tGI.Operator);
					mLBPremSet.add(tLBPremSchema);
					//非续保件，需要回溯数据
					//加费怎么处理？？删除？？
					tLCPremSchema.setPaytoDate(lastpaytodate);
					double lcprem_sumprem= tLCPremSchema.getSumPrem()-tLCPremSchema.getPrem();
					tLCPremSchema.setSumPrem(lcprem_sumprem);
					int lcprem_paytimes = tLCPremSchema.getPayTimes()-1;
					tLCPremSchema.setPayTimes(lcprem_paytimes);
					tLCPremSchema.setModifyDate(mCurrentDate);
					tLCPremSchema.setModifyTime(mCurrentTime);
					tLCPremSchema.setOperator(tGI.Operator);
					mLCPremSet_UPDATE.add(tLCPremSchema);
				}
			}
			//处理lcduty
			LCDutyDB tLCDutyDB = new LCDutyDB();
			LCDutySet tLCDutySet = new LCDutySet();
			tLCDutyDB.setPolNo(tLCPolSchema.getPolNo());
			tLCDutyDB.setPaytoDate(tLCPolSchema.getPaytoDate());
			tLCDutySet=tLCDutyDB.query();
			for(int c=1;c<=tLCDutySet.size();c++)
			{
				LCDutySchema tLCDutySchema = new LCDutySchema();
				tLCDutySchema=tLCDutySet.get(c);
				if(tLCPolSchema.getPayIntv()==0&&tLCPolSchema.getRnewFlag()==-1)
				{
                    //续保件，删除最新的记录
					mLCDutySet_DEL.add(tLCDutySchema);
					//备份当期需要回滚的记录
					LBDutySchema tLBDutySchema = new LBDutySchema();
					mReflections.transFields(tLBDutySchema, tLCDutySchema);
					tLBDutySchema.setEdorNo(PubFun1.CreateMaxNo("LBDuty", 20));
					tLBDutySchema.setMakeDate(mCurrentDate);
					tLBDutySchema.setMakeTime(mCurrentTime);
					tLBDutySchema.setModifyDate(mCurrentDate);
					tLBDutySchema.setModifyTime(mCurrentTime);
					tLBDutySchema.setOperator(tGI.Operator);
					mLBDutySet.add(tLBDutySchema);
				}
				else
				{
                    //先备份
					LBDutySchema tLBDutySchema = new LBDutySchema();
					mReflections.transFields(tLBDutySchema, tLCDutySchema);
					tLBDutySchema.setEdorNo(PubFun1.CreateMaxNo("LBDuty", 20));
					tLBDutySchema.setMakeDate(mCurrentDate);
					tLBDutySchema.setMakeTime(mCurrentTime);
					tLBDutySchema.setModifyDate(mCurrentDate);
					tLBDutySchema.setModifyTime(mCurrentTime);
					tLBDutySchema.setOperator(tGI.Operator);
					mLBDutySet.add(tLBDutySchema);
					//非续保件，需要回溯数据
					//加费怎么处理？？删除？？
					tLCDutySchema.setPaytoDate(lastpaytodate);
					double LCDuty_sumprem= tLCDutySchema.getSumPrem()-tLCDutySchema.getPrem();
					tLCDutySchema.setSumPrem(LCDuty_sumprem);
					tLCDutySchema.setModifyDate(mCurrentDate);
					tLCDutySchema.setModifyTime(mCurrentTime);
					tLCDutySchema.setOperator(tGI.Operator);
					mLCDutySet_UPDATE.add(tLCDutySchema);
				}
			}
            //处理lcget
			LCGetDB tLCGetDB = new LCGetDB();
			LCGetSet tLCGetSet = new LCGetSet();
			tLCGetDB.setPolNo(tLCPolSchema.getPolNo());
			tLCGetSet=tLCGetDB.query();
			for(int c=1;c<=tLCGetSet.size();c++)
			{
				LCGetSchema tLCGetSchema = new LCGetSchema();
				tLCGetSchema=tLCGetSet.get(c);

				//接下来修改
				if(tLCPolSchema.getPayIntv()==0&&tLCPolSchema.getRnewFlag()==-1)
				{
                    //续保件，删除最新的记录
					mLCGetSet_DEL.add(tLCGetSchema);
                    //先备份
					LBGetSchema tLBGetSchema = new LBGetSchema();
					mReflections.transFields(tLBGetSchema, tLCGetSchema);
					tLBGetSchema.setEdorNo(PubFun1.CreateMaxNo("LBGet", 20));
					tLBGetSchema.setMakeDate(mCurrentDate);
					tLBGetSchema.setMakeTime(mCurrentTime);
					tLBGetSchema.setModifyDate(mCurrentDate);
					tLBGetSchema.setModifyTime(mCurrentTime);
					tLBGetSchema.setOperator(tGI.Operator);
					mLBGetSet.add(tLBGetSchema);                
				}
				else
				{
					//先备份
					LBGetSchema tLBGetSchema = new LBGetSchema();
					mReflections.transFields(tLBGetSchema, tLCGetSchema);
					tLBGetSchema.setEdorNo(PubFun1.CreateMaxNo("LBGet", 20));
					tLBGetSchema.setMakeDate(mCurrentDate);
					tLBGetSchema.setMakeTime(mCurrentTime);
					tLBGetSchema.setModifyDate(mCurrentDate);
					tLBGetSchema.setModifyTime(mCurrentTime);
					tLBGetSchema.setOperator(tGI.Operator);
					mLBGetSet.add(tLBGetSchema);
					//非续保件，其他不需要处理
				}
			}
            //处理lccontstate表,续保件需要回滚数据，续期件不需要
			if(tLCPolSchema.getPayIntv()==0&&tLCPolSchema.getRnewFlag()==-1)
			{
				//找到续保件上期保单号
				LCPolSchema last_LCPolSchema1 = new LCPolSchema();
			    LCPolDB last_LCPolDB = new LCPolDB();
			    last_LCPolDB.setContNo(tLCPolSchema.getContNo());
			    last_LCPolDB.setRiskCode(tLCPolSchema.getRiskCode());
			    last_LCPolDB.setAppFlag("4");
			    last_LCPolDB.setPaytoDate(lastpaytodate);
			    if(last_LCPolDB.query().size()==0)
			    {
				   CError.buildErr(this, "查询保单"+tLCPolSchema.getContNo()+"下续保险种"+tLCPolSchema.getRiskCode()+"上期保单信息失败！");
				   return false;
			    }
			    last_LCPolSchema1 = last_LCPolDB.query().get(1);	
				   
				//根据合同号及startdate找到上期险种的终止状态记录，删除
				LCContStateDB tLCContStateDB = new LCContStateDB();
				tLCContStateDB.setContNo(tLCPolSchema.getContNo());
				tLCContStateDB.setPolNo(last_LCPolSchema1.getPolNo());
				tLCContStateDB.setStartDate(last_LCPolSchema1.getEndDate());
				tLCContStateDB.setStateType("Terminate");
				if(tLCContStateDB.query().size()<=0)
				{
					CError tError = new CError();
					tError.moduleName = "VerifyBl";
					tError.functionName = "submitData";
					tError.errorMessage = "查询保单"+tLCPolSchema.getContNo()+"险种"+tLCPolSchema.getRiskCode()+"上期保单状态表终止记录失败!";
					this.mErrors.addOneError(tError);
					return false;
				}				
				LCContStateSchema tLCContStateSchema = new LCContStateSchema();
				tLCContStateSchema=tLCContStateDB.query().get(1);
				mLCContStateSet_DEL.add(tLCContStateSchema);
				//还需要删除掉新增加的记录
				LCContStateDB dLCContStateDB = new LCContStateDB();
				dLCContStateDB.setContNo(tLCPolSchema.getContNo());
				dLCContStateDB.setPolNo(tLCPolSchema.getPolNo());
				dLCContStateDB.setStateType("Available");
				dLCContStateDB.setStartDate(tLCPolSchema.getCValiDate());
				if(dLCContStateDB.query().size()<=0)
				{
					CError tError = new CError();
					tError.moduleName = "VerifyBl";
					tError.functionName = "submitData";
					tError.errorMessage = "查询保单"+tLCPolSchema.getContNo()+"险种"+tLCPolSchema.getRiskCode()+"当期保单状态表记录失败!";
					this.mErrors.addOneError(tError);
					return false;
				}
				LCContStateSchema dLCContStateSchema = new LCContStateSchema();
				dLCContStateSchema=dLCContStateDB.query().get(1);
				mLCContStateSet_DEL.add(dLCContStateSchema);
			}
			
			//然后处理保单表
			if(tLCPolSchema.getPayIntv()==0&&tLCPolSchema.getRnewFlag()==-1)
			{
			   new_polno = "";
			   new_polno = tLCPolSchema.getPolNo();
               //续保件，进行续保换号回滚
			   //先查找上期的记录
			   LCPolSchema last_LCPolSchema = new LCPolSchema();
			   LCPolDB last_LCPolDB = new LCPolDB();
			   last_LCPolDB.setContNo(tLCPolSchema.getContNo());
			   last_LCPolDB.setRiskCode(tLCPolSchema.getRiskCode());
			   last_LCPolDB.setAppFlag("4");
			   last_LCPolDB.setPaytoDate(lastpaytodate);
			   if(last_LCPolDB.query().size()==0)
			   {
				   CError.buildErr(this, "查询保单"+tLCPolSchema.getContNo()+"下续保险种"+tLCPolSchema.getRiskCode()+"上期保单信息失败！");
				   return false;
			   }
			   last_LCPolSchema = last_LCPolDB.query().get(1);		   
			   //然后将appflag置为1
			   last_LCPolSchema.setAppFlag("1");
			   if(mainpol_flag.equals("1"))//如果是主险，修改leavingmoney
			   {
				   last_LCPolSchema.setLeavingMoney(last_LCPolSchema.getLeavingMoney()+sumprem);
			   }
			   last_LCPolSchema.setModifyDate(mCurrentDate);
			   last_LCPolSchema.setModifyTime(mCurrentTime);
			   last_LCPolSchema.setOperator(tGI.Operator);
			   
			   mLCPolSet_UPDATE.add(last_LCPolSchema);
			   
			   //删除新纪录
			   mLCPolSet_DEL.add(tLCPolSchema);
			   //备份被删除新记录
			   LBPolSchema tLBPolSchema =new LBPolSchema();
			   mReflections.transFields(tLBPolSchema, tLCPolSchema);
			   tLBPolSchema.setEdorNo(PubFun1.CreateMaxNo("LBPol", 20));
			   tLBPolSchema.setAppFlag("8");//特殊的标记，为回滚的记录
			   tLBPolSchema.setMakeDate(mCurrentDate);
			   tLBPolSchema.setMakeTime(mCurrentTime);
			   tLBPolSchema.setModifyDate(mCurrentDate);
			   tLBPolSchema.setModifyTime(mCurrentTime);
			   tLBPolSchema.setOperator(tGI.Operator);
			   mLBPolSet_INSERT.add(tLBPolSchema);
			   
			   //如果为续保件，还需要删除续保轨迹表中当期记录
			   LCRnewStateHistoryDB tLCRnewStateHistoryDB = new LCRnewStateHistoryDB();
			   tLCRnewStateHistoryDB.setContNo(tLCPolSchema.getContNo());
			   tLCRnewStateHistoryDB.setRiskCode(tLCPolSchema.getRiskCode());
			   tLCRnewStateHistoryDB.setPaytoDate(lastpaytodate);
			   if(tLCRnewStateHistoryDB.query().size()==0)
			   {
				   CError.buildErr(this, "查询保单"+tLCPolSchema.getContNo()+"下续保险种"+tLCPolSchema.getRiskCode()+"当期续保轨迹信息失败！");
				   return false;
			   }
			   mLCRnewStateHistorySchema_DEL = new LCRnewStateHistorySchema();
			   mLCRnewStateHistorySchema_DEL=tLCRnewStateHistoryDB.query().get(1);
			   mLCRnewStateHistorySet_DEL.add(mLCRnewStateHistorySchema_DEL);
			}
			else
			{
				//续期件，备份当前记录，标记为回滚，然后回退
				LBPolSchema tLBPolSchema =new LBPolSchema();
			    mReflections.transFields(tLBPolSchema, tLCPolSchema);
			    tLBPolSchema.setEdorNo(PubFun1.CreateMaxNo("LBPol", 20));
			    tLBPolSchema.setAppFlag("8");//特殊的标记，为回滚的记录
			    tLBPolSchema.setMakeDate(mCurrentDate);
			    tLBPolSchema.setMakeTime(mCurrentTime);
			    tLBPolSchema.setModifyDate(mCurrentDate);
			    tLBPolSchema.setModifyTime(mCurrentTime);
			    tLBPolSchema.setOperator(tGI.Operator);
			    mLBPolSet_INSERT.add(tLBPolSchema);
				//开始回退信息
				tLCPolSchema.setPaytoDate(lastpaytodate);
				double lcpol_sumprem= tLCPolSchema.getSumPrem()-tLCPolSchema.getPrem();
				tLCPolSchema.setSumPrem(lcpol_sumprem);
				if(mainpol_flag.equals("1"))//如果是主险，修改leavingmoney
			    {
					tLCPolSchema.setLeavingMoney(tLCPolSchema.getLeavingMoney()+sumprem);
			    }
				tLCPolSchema.setModifyDate(mCurrentDate);
				tLCPolSchema.setModifyTime(mCurrentTime);
				tLCPolSchema.setOperator(tGI.Operator);			
				mLCPolSet_UPDATE.add(tLCPolSchema);		
			}
			
			//处理lccont
			LCContSchema tLCContSchema = new LCContSchema();
			tLCContSchema = tLCContDB.getSchema();
			tLCContSchema.setSumPrem(tLCContSchema.getSumPrem()-tLCContSchema.getPrem());
			tLCContSchema.setModifyDate(mCurrentDate);
			tLCContSchema.setModifyTime(mCurrentTime);
			tLCContSchema.setOperator(tGI.Operator);			
			mLCContSet_UPDATE.add(tLCContSchema);
			//处理回滚轨迹表
			LCRollBackLogSchema tLCRollBackLogSchema = new LCRollBackLogSchema();
			tLCRollBackLogSchema.setSerialNo(PubFun1.CreateMaxNo("LCRollBackLog", 20));
			tLCRollBackLogSchema.setGrpContNo(tLCPolSchema.getGrpContNo());
			tLCRollBackLogSchema.setGrpPolNo(tLCPolSchema.getGrpPolNo());
			tLCRollBackLogSchema.setPolNo(new_polno);
			tLCRollBackLogSchema.setOtherNo(tLCPolSchema.getContNo());
			tLCRollBackLogSchema.setOtherNoType("2");
			tLCRollBackLogSchema.setType(mReaSonType);
			tLCRollBackLogSchema.setMoney(tLCPolSchema.getPrem());
			tLCRollBackLogSchema.setOldOperator(tLCPolSchema.getOperator());
			tLCRollBackLogSchema.setOldMakeDate(tLCPolSchema.getMakeDate());
			tLCRollBackLogSchema.setOldMakeTime(tLCPolSchema.getMakeTime());
			tLCRollBackLogSchema.setManageCom(tLCPolSchema.getManageCom());
			tLCRollBackLogSchema.setStandbyFlag1(mGetNoticeNo); //记录当前回退收费号
			tLCRollBackLogSchema.setOperator(tGI.Operator);
			tLCRollBackLogSchema.setMakeDate(mCurrentDate);
			tLCRollBackLogSchema.setMakeTime(mCurrentTime);
			tLCRollBackLogSchema.setRemark(mReMark);
			mLCRollBackLogSet_INSERT.add(tLCRollBackLogSchema);
			
			count++;
		}
		return true;
	}

	/**
	 * 返回处理结果
	 * 
	 * @return: VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回处理结果
	 * 
	 * @return: MMap
	 */
	public MMap getMap() {
		return mMMap;
	}

	public static void main(String[] args) {
		ReVerifyRollBackBL tReVerifyRollBackBL = new ReVerifyRollBackBL();
		 TransferData tTransferData = new TransferData();
		 tTransferData.setNameAndValue("GetNoticeNo","86110020090310058707");
	     tTransferData.setNameAndValue("ContNo","86110020040210039433");
	     tTransferData.setNameAndValue("ReaSonType","02");
	     tTransferData.setNameAndValue("ReMark","ceshi");
	   
		GlobalInput tGI = new GlobalInput();
		tGI.Operator = "001";
		tGI.ManageCom = "86";
		tGI.ComCode = "86";

		VData tVData = new VData();
		tVData.add(tTransferData);
		tVData.add(tGI);
		tReVerifyRollBackBL.submitData(tVData, "BQApp");
	}
}
