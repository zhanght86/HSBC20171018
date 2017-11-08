package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;

public class CCBDailyBalance extends TaskThread{
private static Logger logger = Logger.getLogger(CCBDailyBalance.class);

	public CCBDailyBalance() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CCBDailyBalance tCCBDailyBalance = new CCBDailyBalance();
		tCCBDailyBalance.dealMain();
		// TODO Auto-generated method stub
//		MMap mapDel = new MMap();
//		mapDel.add(tCCBDailyBalance.DataBakup("86360020090210000011"));
//		VData tInputData = new VData();
//		tInputData.add(mapDel);
//		PubSubmit tPubSubmit = new PubSubmit();
//		if (!tPubSubmit.submitData(tInputData, "")) 
//		{
//			logger.debug("failed!!!!");	
//		}
	}
	
	ExeSQL tExeSQL = new ExeSQL();

	PubSubmit tPubSubmit = new PubSubmit();
	/*
	 * lktransstatus中的temp=0表示没有下一个对账文件，temp=1表示还有下一个对账文件，对账完成后将descr置上一个常量
	 * LKBalanceDetail表中关键字段释义：
	   ConfirmFlag：1 -- 包含在银行发送的对账明细文件中的记录	0 -- 不包含在银行发送的对账明细文件中的记录
	   Temp1： 0 -- 对账失败	1 -- 银行传来的未对账的交易	2 -- 系统中成功，而银行失败的交易
	   Temp2：对账结果文字说明，原因
	    对账流程：
	   1、查找不包含在对账文件中，但是做过核保交易且核保成功的记录
	   2、判断LCCont中是否存在（保单号来判断即可），若不存在，continue；若存在，将数据插入LKBalanceDetail中
	   3、依次核对LKBalanceDetail表中的每笔交易：	
	   If(confirmflag=1&&Temp1==1 即银行出单)
	   {
	      Flag = Select count(*) from lccont where contno='保单号' and appflag=’1’;
	      If(LCCont中已经签单，即Flag=1)
	      {
	           比对交易金额是否正确：
	         mLKTransStatusSchema.getTransAmnt()与mLKBalanceDetailSchema.getTranAmnt()是否一致，
	         mLJAPaySchema.getSumActuPayMoney()与mLKBalanceDetailSchema.getTranAmnt()是否一致
	      } 
	      Else 
	      {
	           判断此单是否做过冲正交易且冲正成功，若是，则此单对账失败：保单冲正但银行出单
	      }
	   }
	   Else if(ConfirmFlag=0即银行未出单，但保险公司做过承保交易的)
	   {
	       判断是否签单（插入对账日志表中的ConfirmFlag=0的数据在LCCont中都有记录，插入之前都有判断，此处不在重复）
	      Select appflag from lcpol where contno = '"+tLKTransStatusSchema.getPolNo()+"';
	      If(appflag==1)
	      {
	           删除保单数据；在交易日志表中插入一条记录，以便于以后查数；funcflag=’04’,temp=’1’,transcode=***04
	      }
	      Else if(appflag==0)
	      {
	           删除投保单数据；在交易日志表中插入一条数据，以便于以后查数；funcflag=’00’,temp=’0’,transcode=***00
	      }
	   } 
	 */
	public boolean dealMain()
	{
		try
		{
			logger.debug("start CCBDailyBalance....");
			//从交易日志表中查出今天需要对账的分公司(有可能有延迟对账的情况，即今天对前几天的帐)
			String CurrentDay = PubFun.getCurrentDate();
			String CurrentTime = PubFun.getCurrentTime();
			String sql = "select managecom,transdate from lktransstatus where bankcode='03'" +
					" and funcflag = '17' and rcode='1' and makedate>=(date'"+"?CurrentDay?"+"'-30) " +
							"and makedate<=date'"+"?CurrentDay?"+"'  and descr is null ";
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(CurrentTime);
			sqlbv1.put("CurrentDay", CurrentDay);
            SSRS tSSRS = new SSRS();
            tSSRS = tExeSQL.execSQL(sqlbv1);
            logger.debug("tSSRS.getMaxRow()="+tSSRS.getMaxRow());
            for(int i=1; i<=tSSRS.getMaxRow(); i++)
            {
            	//判断此分公司的对账文件是否全部传送完毕，temp='1' 表示还有，temp='0'表示没有了，那么一个分公司至少有一条对账日志表的temp=0
            	String sql1=" select count(*) from lktransstatus where bankcode='03' and managecom = '"+"?managecom?"+"'"
            	           +" and funcflag='17' and temp='0' and transdate='"+"?transdate?"+"' and descr is null";
            	logger.debug("判断此分公司的对账文件是否全部传送完毕:"+sql1);
            	SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
    			sqlbv2.sql(sql1);
    			sqlbv2.put("managecom", tSSRS.GetText(i,1));
    			sqlbv2.put("transdate", tSSRS.GetText(i,2));
            	if(tExeSQL.getOneValue(sqlbv2).equals("0"))
            	{
            		logger.debug("分公司"+tSSRS.GetText(i,1)+"的对账文件还未传送完毕，请稍后再对账...");
            		continue;
            	}
            	else
            	{
            		String tTransDate = tSSRS.GetText(i,2);
            		logger.debug("tTransDate: "+tTransDate);
            		LKTransStatusDB tLKTransStatusDB = new LKTransStatusDB();
        			LKTransStatusSet tempLKTransStatusSet = new LKTransStatusSet();
            		String sql2="select * from lktransstatus where bankcode='03' and managecom='"+"?managecom?"+"' " +
            				"and funcflag='17' and transdate='"+"?transdate?"+"' and rcode='1' and descr is null";
            		SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
            		sqlbv4.sql(sql2);
            		sqlbv4.put("managecom", tSSRS.GetText(i,1));
            		sqlbv4.put("transdate", tSSRS.GetText(i,2));
            		tempLKTransStatusSet = tLKTransStatusDB.executeQuery(sqlbv4);
            		LKTransStatusSchema tempLKTransStatusSchema = tempLKTransStatusSet.get(1);
            		//查找不包含在对账文件中，但是保险公司做过承保交易的日志
            		String sql3=" select a.* from LKTransStatus a, LKCodeMapping c where a.bankcode = c.bankcode and a.BankBranch = c.ZoneNo"
	                           +" and a.banknode = c.banknode and a.TransDate = '"+"?TransDate?"+"' and a.BankCode = '"+"?BankCode?"+ "'"
	                           +" and c.zoneno = '"+"?zoneno?"+"' and c.Remark5 = '0' and a.FuncFlag = '01'	and a.TransStatus = '2' and a.RCode = '1'"
	                           +" and a.transno not in (select b.transrno from lkbalancedetail b where b.tranDate = '"+"?tranDate?"+"'"
					           +" and b.BankCode = '"+"?BankCode?"+"' and b.BankZoneCode = '"+"?BankZoneCode?"+"') order by a.transno";
            		logger.debug("balancedetail sql is :" + sql3);
            		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
        			sqlbv3.sql(sql3);
        			sqlbv3.put("TransDate", tempLKTransStatusSchema.getTransDate());
        			sqlbv3.put("BankCode", tempLKTransStatusSchema.getBankCode());
        			sqlbv3.put("zoneno", tempLKTransStatusSchema.getBankBranch());
        			sqlbv3.put("tranDate", tempLKTransStatusSchema.getTransDate());
        			sqlbv3.put("BankCode", tempLKTransStatusSchema.getBankCode());
        			sqlbv3.put("BankZoneCode", tempLKTransStatusSchema.getBankBranch());
            		LKTransStatusSet tLKTransStatusSet = new LKTransStatusSet();
    		        tLKTransStatusSet = tLKTransStatusDB.executeQuery(sqlbv3);
    		        if (tLKTransStatusDB.mErrors.needDealError()) 
    		        {
    		        	this.dealError("","查询交易日志数据失败！");		
    		            return false;		
    		        }
    		        LKBalanceDetailSet InsertLKBalanceDetailSet = new LKBalanceDetailSet();
    		        for(int j=1;j<=tLKTransStatusSet.size();j++)
    		        {    		        	
    		        	LKTransStatusSchema tLKTransStatusSchema = new LKTransStatusSchema();
		                tLKTransStatusSchema.setSchema(tLKTransStatusSet.get(j));
		
		                //检查是否在LCCont表中是否存在,如果不存在，则不处理该保单
		                String tSQL = "select Count(*) from LCCont where contno = '"+"?contno?"+"'";
		                SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
	        			sqlbv5.sql(tSQL);
	        			sqlbv5.put("contno", tLKTransStatusSchema.getPolNo());
		                logger.debug("检查是否在LCCont表中是否存在"+tSQL);	                
		                int tExistsCount = Integer.parseInt(tExeSQL.getOneValue(sqlbv5));
		                if(tExistsCount == 0)
		                {
		                  continue;
		                }
		                logger.debug("LCCont表中存在数据，需要插入对账明细表中");
		                LKBalanceDetailSchema tempLKBalanceDetailSchema = new LKBalanceDetailSchema();
		                tempLKBalanceDetailSchema.setTransrNo(tLKTransStatusSchema.getTransNo());
		                tempLKBalanceDetailSchema.setBankCode(tLKTransStatusSchema.getBankCode());
		                tempLKBalanceDetailSchema.setBankZoneCode(tLKTransStatusSchema.getBankBranch());
		                tempLKBalanceDetailSchema.setBrNo(tLKTransStatusSchema.getBankNode());
		                tempLKBalanceDetailSchema.setTellerNo(tLKTransStatusSchema.getBankOperator());
		                tempLKBalanceDetailSchema.setFuncFlag(tLKTransStatusSchema.getPrtNo());//存放保单印刷号
		                tempLKBalanceDetailSchema.setCardNo(tLKTransStatusSchema.getPolNo());
		                //tempLKBalanceDetailSchema.setAppntName(""); //作为对帐流水号
		                tempLKBalanceDetailSchema.setTranDate(tLKTransStatusSchema.getTransDate());
		                tempLKBalanceDetailSchema.setTranAmnt(tLKTransStatusSchema.getTransAmnt());
		                tempLKBalanceDetailSchema.setConfirmFlag("0");
		                tempLKBalanceDetailSchema.setMakeDate(CurrentDay);
		                tempLKBalanceDetailSchema.setMakeTime(CurrentTime);
		                tempLKBalanceDetailSchema.setModifyDate(CurrentDay);
		                tempLKBalanceDetailSchema.setModifyTime(CurrentTime);
		                tempLKBalanceDetailSchema.setTemp1("2"); // 系统中成功银行认为失败对帐的交易
		                InsertLKBalanceDetailSet.add(tempLKBalanceDetailSchema);
		                logger.debug("InsertLKBalanceDetailSet.size()="+InsertLKBalanceDetailSet.size());		                
    		        }
    		        if(InsertLKBalanceDetailSet.size()>0)
    		        {
    		        	MMap tmap = new MMap();
    		            tmap.put(InsertLKBalanceDetailSet, "INSERT");
    		            VData tVData = new VData();
    		            tVData.add(tmap);
    		            if (!tPubSubmit.submitData(tVData, ""))
    		            {
    		            	this.dealError("", "向对账明细表中插入数据失败");
    		            	continue;		            	
    		            }
    		        }
    		        
    		        logger.debug("LKBalanceDetail is done!Balance detail begin....");
    		        String sql4=" select * from lkbalancedetail where bankcode='"+"?bankcode?"+"'" +
    		        		" and bankzonecode='"+"?bankzonecode?"+"'"
    		                   +" and trandate ='"+"?trandate?"+"'";
    		        SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
        			sqlbv5.sql(sql4);
        			sqlbv5.put("bankcode", tempLKTransStatusSchema.getBankCode());
        			sqlbv5.put("bankzonecode", tempLKTransStatusSchema.getBankBranch());
        			sqlbv5.put("trandate", tempLKTransStatusSchema.getTransDate());
    		        logger.debug("取所有的对账明细："+sql4);
    		        LKBalanceDetailDB mLKBalanceDetailDB = new LKBalanceDetailDB();
    		        LKBalanceDetailSet updateLKBalanceDetailSet = new LKBalanceDetailSet();//存放对账完成后需要更新的数据
    		        LKBalanceDetailSet mLKBalanceDetailSet = mLKBalanceDetailDB.executeQuery(sqlbv5); //取全部对帐数据
    		        for(int m=1; m<=mLKBalanceDetailSet.size(); m++)
    		        {
    		        	LKBalanceDetailSchema mLKBalanceDetailSchema = new LKBalanceDetailSchema();
    		        	mLKBalanceDetailSchema = mLKBalanceDetailSet.get(m);
    		        	LKTransStatusSchema mLKTransStatusSchema1 = new LKTransStatusSchema();
    		        	LKTransStatusDB mLKTransStatusDB = new LKTransStatusDB();
    		            mLKTransStatusSchema1.setBankCode(mLKBalanceDetailSchema.getBankCode());
    		            mLKTransStatusSchema1.setBankBranch(mLKBalanceDetailSchema.getBankZoneCode());
    		            mLKTransStatusSchema1.setTransNo(mLKBalanceDetailSchema.getTransrNo());
    		            mLKTransStatusSchema1.setBankNode(mLKBalanceDetailSchema.getBrNo());
    		            mLKTransStatusSchema1.setTransDate(mLKBalanceDetailSchema.getTranDate());
    		            mLKTransStatusDB.setSchema(mLKTransStatusSchema1);
    		            LKTransStatusSet mLKTransStatusSet = new LKTransStatusSet();
    		            mLKTransStatusSet = mLKTransStatusDB.query();
    		            if (mLKTransStatusDB.mErrors.needDealError() || mLKTransStatusSet.size() < 1 || mLKTransStatusSet == null)
    		            { 
    		            	this.dealError("", "取交易日志失败，银行编号："+mLKTransStatusSchema1.getBankCode() +
    		                                    "地区码："+mLKBalanceDetailSchema.getBankZoneCode()+"交易号：" + mLKTransStatusSchema1.getTransNo());
    	
    		                mLKBalanceDetailSchema.setTemp1("0"); //对帐处理失败
    		                mLKBalanceDetailSchema.setTemp2("未查到当日交易信息");
    		                updateLKBalanceDetailSet.add(mLKBalanceDetailSchema);	
    		                continue;
    		            }
    		            else
    		            {
    		            	mLKTransStatusSchema1.setSchema(mLKTransStatusSet.get(1));
    		            }
    		            if(mLKBalanceDetailSchema.getConfirmFlag().trim().equals("1")&&mLKBalanceDetailSchema.getTemp1().trim().equals("1"))
    		            {
    		            	//银行已经出单的数据，判断在系统中是否存在，且金额相符
    		            	 SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
    		        			sqlbv6.sql("select count(*) from lccont where contno='"+"?contno?"+"' and appflag in ('1','4')");
    		        			sqlbv6.put("contno", mLKBalanceDetailSchema.getCardNo());
    		            	String count = tExeSQL.getOneValue(sqlbv6);
    		        		if(count.equals("1"))
    		        		{
    		        			logger.debug("开始比交易金额是否正确");
    		                    if (mLKTransStatusSchema1.getTransAmnt() != mLKBalanceDetailSchema.getTranAmnt()) 
    		                    {
    		                    	this.dealError("", "对账金额不符");
    		                        mLKBalanceDetailSchema.setTemp1("0");
    		                        mLKBalanceDetailSchema.setTemp2("对帐金额与日志中不符");
    		                        updateLKBalanceDetailSet.add(mLKBalanceDetailSchema);
    		                        continue;
    		                    }
    		                    LJAPayDB mLJAPayDB = new LJAPayDB();
    		                    LJAPaySet mLJAPaySet = new LJAPaySet();
    		                    LJAPaySchema mLJAPaySchema = new LJAPaySchema();
    		                    mLJAPayDB.setIncomeNo(mLKTransStatusSchema1.getPolNo());
    		                    mLJAPaySet = mLJAPayDB.query();
    		                    if (mLJAPaySet != null && mLJAPaySet.size() > 0)
    		                    {
    		                      mLJAPaySchema = mLJAPaySet.get(1);
    		                      if (mLJAPaySchema.getSumActuPayMoney() != mLKBalanceDetailSchema.getTranAmnt())
    		                      {
    		                        mLKBalanceDetailSchema.setTemp1("0");
    		                        mLKBalanceDetailSchema.setTemp2("金额对比失败");
    		                        updateLKBalanceDetailSet.add(mLKBalanceDetailSchema);
    		                        continue;
    		                      }
    		                    }
    		        		}
    		        		else
    		        		{
    		        			mLKBalanceDetailSchema.setTemp1("0");
		                        mLKBalanceDetailSchema.setTemp2("银行出单系统未签单");
		                        updateLKBalanceDetailSet.add(mLKBalanceDetailSchema);
		                        continue;
    		        		}
    		            }
    		            else if(mLKBalanceDetailSchema.getConfirmFlag().trim().equals("0"))
    		            {
    		            	//不包含在对账文件中，但是存在于LCCont表中，判断是否签单
    		            	String tempsql = "select appflag from lccont where contno='"+"?contno?"+"'";
    		            	SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
    		            	sqlbv7.sql(tempsql);
    		            	sqlbv7.put("contno", mLKTransStatusSchema1.getPolNo());
    		            	String mContNo = mLKTransStatusSchema1.getPolNo();
    		            	if(tExeSQL.getOneValue(sqlbv7).equals("1"))
    		            	{
    		            		logger.debug("已经签单，删除保单数据，在交易日志表中插入一条数据");    		            		
    		            		MMap mapDel = new MMap();
    		            		
    		            		//删除之前首先备份各个表
    		            		mapDel.add(DataBakup(mContNo));
    		            		//mapDel.put("delete from LACommisionDetail where grpcontno = '" + mContNo + "'", "DELETE");
    		            		//mapDel.put("delete from LCCustomerImpart where contno = '" + mContNo + "'", "DELETE");
    		            		//mapDel.put("delete from LCCustomerImpartParams where contno = '" + mContNo + "'", "DELETE");
    		            		SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
        		            	sqlbv8.sql("delete from LCAppnt where contno = '" + "?mContNo?" + "'");
        		            	sqlbv8.put("mContNo", mContNo);
    		            		mapDel.put(sqlbv8, "DELETE");
    		            		SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
        		            	sqlbv9.sql("delete from LCInsured where contno = '" + "?mContNo?" + "'");
        		            	sqlbv9.put("mContNo", mContNo);    		            		
    		            		mapDel.put(sqlbv9, "DELETE");    		
    		            		SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
        		            	sqlbv10.sql("delete from LCPrem where contno = '" + "?mContNo?" + "'");
        		            	sqlbv10.put("mContNo", mContNo);  
    		            		mapDel.put(sqlbv10, "DELETE");
    		            		SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
    		            		sqlbv11.sql("delete from LCGet where contno = '" + "?mContNo?" + "'");
        		            	sqlbv11.put("mContNo", mContNo); 
    		            		mapDel.put(sqlbv11, "DELETE");
    		            		SQLwithBindVariables sqlbv12=new SQLwithBindVariables();
    		            		sqlbv12.sql("delete from LCDuty where contno = '" + "?mContNo?" + "'");
        		            	sqlbv12.put("mContNo", mContNo); 
    		            		mapDel.put(sqlbv12, "DELETE");
    		            		SQLwithBindVariables sqlbv13=new SQLwithBindVariables();
    		            		sqlbv13.sql("delete from LCBnf where contno = '" + "?mContNo?" + "'");
        		            	sqlbv13.put("mContNo", mContNo); 
    		            		mapDel.put(sqlbv13, "DELETE");
    		            		SQLwithBindVariables sqlbv14=new SQLwithBindVariables();
    		            		sqlbv14.sql("delete from LCCUWMaster where contno = '" + "?mContNo?" + "'");
        		            	sqlbv14.put("mContNo", mContNo); 
    		            		mapDel.put(sqlbv14, "DELETE");
    		            		SQLwithBindVariables sqlbv15=new SQLwithBindVariables();
    		            		sqlbv15.sql("delete from LCCUWSub where contno = '" + "?mContNo?" + "'");
        		            	sqlbv15.put("mContNo", mContNo); 
    		            		mapDel.put(sqlbv15, "DELETE");
    		            		//mapDel.put("delete from LCCUWError where contno = '" + mContNo + "'", "DELETE");
    		            		SQLwithBindVariables sqlbv16=new SQLwithBindVariables();
    		            		sqlbv16.sql("delete from LCUWMaster where contno = '" + "?mContNo?" + "'");
        		            	sqlbv16.put("mContNo", mContNo); 
    		            		mapDel.put(sqlbv16, "DELETE");	
    		            		SQLwithBindVariables sqlbv17=new SQLwithBindVariables();
    		            		sqlbv17.sql("delete from LCUWSub where contno = '" + "?mContNo?" + "'");
        		            	sqlbv17.put("mContNo", mContNo); 
    		            		mapDel.put(sqlbv17, "DELETE");
    		            		//mapDel.put("delete from LCUWError where contno = '" + mContNo + "'", "DELETE");
    		            		//mapDel.put("delete from LCIndUWMaster where contno = '" + mContNo + "'", "DELETE");
    		            		//mapDel.put("delete from LCIndUWSub where contno = '" + mContNo + "'", "DELETE");	
    		            		//mapDel.put("delete from LCIndUWError where contno = '" + mContNo + "'", "DELETE");
    		            		SQLwithBindVariables sqlbv18=new SQLwithBindVariables();
    		            		sqlbv18.sql("delete from LJTempFeeClass where exists(select 1 from ljtempfee where ljtempfee.tempfeeno=ljtempfeeclass.tempfeeno " +
    		            				"and ljtempfee.otherno='" + "?mContNo?" + "')");
        		            	sqlbv18.put("mContNo", mContNo); 
    		            		mapDel.put(sqlbv18, "DELETE");
    		            		SQLwithBindVariables sqlbv19=new SQLwithBindVariables();
    		            		sqlbv19.sql("delete from LJTempFee where otherno = '" + "?mContNo?" + "'");
        		            	sqlbv19.put("mContNo", mContNo); 
    		            		mapDel.put(sqlbv19, "DELETE");
    		            		SQLwithBindVariables sqlbv20=new SQLwithBindVariables();
    		            		sqlbv20.sql("delete from LJAPay where incomeno = '" + "?mContNo?" + "'");
        		            	sqlbv20.put("mContNo", mContNo); 
    		            		mapDel.put(sqlbv20, "DELETE");
    		            		SQLwithBindVariables sqlbv21=new SQLwithBindVariables();
    		            		sqlbv21.sql("delete from LJAPayPerson where contno = '" + "?mContNo?" + "'");
        		            	sqlbv21.put("mContNo", mContNo); 
    		            		mapDel.put(sqlbv21, "DELETE");
    		            		//mapDel.put("delete from ljaget where actugetno in (select actugetno from ljagetother where otherno ='" + mContNo + "')", "DELETE");
    		            		//mapDel.put("delete from ljagetother where otherno ='" + mContNo	+ "'", "DELETE");// 若有溢缴也删除
    		            		SQLwithBindVariables sqlbv22=new SQLwithBindVariables();
    		            		sqlbv22.sql("delete from lcpol where contno = '" + "?mContNo?" + "'");
        		            	sqlbv22.put("mContNo", mContNo); 
    		            		mapDel.put(sqlbv22, "DELETE");
    		            		SQLwithBindVariables sqlbv23=new SQLwithBindVariables();
    		            		sqlbv23.sql("delete from LCContState where contno = '" + "?mContNo?" + "'");
        		            	sqlbv23.put("mContNo", mContNo); 
    		            		mapDel.put(sqlbv23, "DELETE");
    		            		SQLwithBindVariables sqlbv24=new SQLwithBindVariables();
    		            		sqlbv24.sql("delete from lcinsureacc where contno = '" + "?mContNo?" + "'");
        		            	sqlbv24.put("mContNo", mContNo);
    		            		mapDel.put(sqlbv24, "DELETE");
    		            		SQLwithBindVariables sqlbv25=new SQLwithBindVariables();
    		            		sqlbv25.sql("delete from lcinsureaccclass where contno = '" + "?mContNo?" + "'");
        		            	sqlbv25.put("mContNo", mContNo);
    		            		mapDel.put(sqlbv25, "DELETE");
    		            		SQLwithBindVariables sqlbv26=new SQLwithBindVariables();
    		            		sqlbv26.sql("delete from lcinsureaccclassfee where contno = '" + "?mContNo?" + "'");
        		            	sqlbv26.put("mContNo", mContNo);
    		            		mapDel.put(sqlbv26, "DELETE");
    		            		SQLwithBindVariables sqlbv27=new SQLwithBindVariables();
    		            		sqlbv27.sql("delete from lcinsureaccfee where contno = '" + "?mContNo?" + "'");
        		            	sqlbv27.put("mContNo", mContNo);
    		            		mapDel.put(sqlbv27, "DELETE");
    		            		SQLwithBindVariables sqlbv28=new SQLwithBindVariables();
    		            		sqlbv28.sql("delete from lcinsureacctrace where contno = '" + "?mContNo?" + "'");
        		            	sqlbv28.put("mContNo", mContNo);
    		            		mapDel.put(sqlbv28, "DELETE");
    		            		SQLwithBindVariables sqlbv29=new SQLwithBindVariables();
    		            		sqlbv29.sql("delete from lcinsureaccfeetrace where contno = '" + "?mContNo?" + "'");
        		            	sqlbv29.put("mContNo", mContNo);
    		            		mapDel.put(sqlbv29, "DELETE");
    		            		SQLwithBindVariables sqlbv30=new SQLwithBindVariables();
    		            		sqlbv30.sql("delete from lccont where contno = '" + "?mContNo?" + "'");
        		            	sqlbv30.put("mContNo", mContNo);
    		            		mapDel.put(sqlbv30, "DELETE");		
    		            		//mapDel.put("delete from LRResultDetail where serialno = (select serialno from LRResultMain where contno = '"+mContNo+"')", "DELETE");
    		            		//mapDel.put("delete from LRResultMain where contno = '" + mContNo + "'", "DELETE"); 	
    		                    
    		                    //向交易日志表中插入一条记录
    		                    LKTransStatusSchema InsertLKTransStatusSchema = new LKTransStatusSchema();
    		                    InsertLKTransStatusSchema.setTransCode(mLKTransStatusSchema1.getTransCode()+"04");
    		                    InsertLKTransStatusSchema.setBankCode(mLKTransStatusSchema1.getBankCode());
    		                    InsertLKTransStatusSchema.setBankBranch(mLKTransStatusSchema1.getBankBranch());
    		                    InsertLKTransStatusSchema.setBankNode(mLKTransStatusSchema1.getBankNode());
    		                    InsertLKTransStatusSchema.setBankOperator("AutoBalance");
    		                    InsertLKTransStatusSchema.setTransNo(mLKTransStatusSchema1.getTransCode()+"04");
    		                    InsertLKTransStatusSchema.setFuncFlag("04");
    		                    InsertLKTransStatusSchema.setTransDate(mLKTransStatusSchema1.getTransDate());
    		                    InsertLKTransStatusSchema.setPrtNo(mLKTransStatusSchema1.getPrtNo());
    		                    InsertLKTransStatusSchema.setPolNo(mLKTransStatusSchema1.getPolNo());
    		                    InsertLKTransStatusSchema.setProposalNo(mLKTransStatusSchema1.getProposalNo());
    		                    InsertLKTransStatusSchema.setTransAmnt(mLKTransStatusSchema1.getTransAmnt());
    		                    InsertLKTransStatusSchema.setRCode("1");
    		                    InsertLKTransStatusSchema.setTransStatus("2");
    		                    InsertLKTransStatusSchema.setTemp("1");
    		                    InsertLKTransStatusSchema.setDescr("自动对账做撤单");
    		                    InsertLKTransStatusSchema.setMakeDate(CurrentDay);
    		                    InsertLKTransStatusSchema.setMakeTime(CurrentTime);
    		                    InsertLKTransStatusSchema.setModifyDate(CurrentDay);
    		                    InsertLKTransStatusSchema.setModifyTime(CurrentTime);
    		                    mapDel.put(InsertLKTransStatusSchema, "INSERT");
    		                    
    		                    VData tInputData = new VData();
    		            		tInputData.add(mapDel);
    		            		PubSubmit tPubSubmit = new PubSubmit();
    		            		if (!tPubSubmit.submitData(tInputData, "")) 
    		            		{
    		            			this.dealError("", "数据提交失败");
    		            			mLKBalanceDetailSchema.setTemp1("0");
    		                        mLKBalanceDetailSchema.setTemp2("系统签单银行未出单");
    		                        updateLKBalanceDetailSet.add(mLKBalanceDetailSchema);
    				            	continue;	
    		            		}
    		            	}
    		            	else if(tExeSQL.getOneValue(sqlbv7).equals("0"))
    		            	{
    		            		logger.debug("未签单，删除投保数据");
    		            		String tempfeeno = "";
    		            		SQLwithBindVariables sqlbv31=new SQLwithBindVariables();
    		            		sqlbv31.sql("select tempfeeno from lccont where contno='"+"?mContNo?"+"'");
    		            		sqlbv31.put("mContNo", mContNo);
    		            		tempfeeno = tExeSQL.getOneValue(sqlbv31);
    		            		MMap mapDel = new MMap();
    		            		//删除之前首先备份各个表
    		            		mapDel.add(DataBakup(mContNo));
    		            		//mapDel.put("delete from LACommisionDetail where grpcontno = '" + mContNo + "'", "DELETE");
    		            		//mapDel.put("delete from LCCustomerImpart where contno = '" + mContNo + "'", "DELETE");
    		            		//mapDel.put("delete from LCCustomerImpartParams where contno = '" + mContNo + "'", "DELETE");
    		            		SQLwithBindVariables sqlbv32=new SQLwithBindVariables();
    		            		sqlbv32.sql("delete from LCAppnt where contno = '" + "?mContNo?" + "'");
    		            		sqlbv32.put("mContNo", mContNo);
    		            		mapDel.put(sqlbv32, "DELETE");
    		            		SQLwithBindVariables sqlbv33=new SQLwithBindVariables();
    		            		sqlbv33.sql("delete from LCInsured where contno = '" + "?mContNo?" + "'");
    		            		sqlbv33.put("mContNo", mContNo);
    		            		mapDel.put(sqlbv33, "DELETE");
    		            		SQLwithBindVariables sqlbv34=new SQLwithBindVariables();
    		            		sqlbv34.sql("delete from LCPrem where contno = '" + "?mContNo?" + "'");
    		            		sqlbv34.put("mContNo", mContNo);
    		            		mapDel.put(sqlbv34, "DELETE");
    		            		SQLwithBindVariables sqlbv35=new SQLwithBindVariables();
    		            		sqlbv35.sql("delete from LCGet where contno = '" + "?mContNo?" + "'");
    		            		sqlbv35.put("mContNo", mContNo);
    		            		mapDel.put(sqlbv35, "DELETE");
    		            		SQLwithBindVariables sqlbv36=new SQLwithBindVariables();
    		            		sqlbv36.sql("delete from LCDuty where contno = '" + "?mContNo?" + "'");
    		            		sqlbv36.put("mContNo", mContNo);
    		            		mapDel.put(sqlbv36, "DELETE");
    		            		SQLwithBindVariables sqlbv37=new SQLwithBindVariables();
    		            		sqlbv37.sql("delete from LCBnf where contno = '" + "?mContNo?" + "'");
    		            		sqlbv37.put("mContNo", mContNo);
    		            		mapDel.put(sqlbv37, "DELETE");
    		            		SQLwithBindVariables sqlbv38=new SQLwithBindVariables();
    		            		sqlbv38.sql("delete from LCCUWMaster where contno = '" + "?mContNo?" + "'");
    		            		sqlbv38.put("mContNo", mContNo);
    		            		mapDel.put(sqlbv38, "DELETE");
    		            		SQLwithBindVariables sqlbv39=new SQLwithBindVariables();
    		            		sqlbv39.sql("delete from LCCUWSub where contno = '" + "?mContNo?" + "'");
    		            		sqlbv39.put("mContNo", mContNo);
    		            		mapDel.put(sqlbv39, "DELETE");
    		            		//mapDel.put("delete from LCCUWError where contno = '" + mContNo + "'", "DELETE");
    		            		SQLwithBindVariables sqlbv40=new SQLwithBindVariables();
    		            		sqlbv40.sql("delete from LCUWMaster where contno = '" + "?mContNo?" + "'");
    		            		sqlbv40.put("mContNo", mContNo);
    		            		mapDel.put(sqlbv40, "DELETE");		
    		            		SQLwithBindVariables sqlbv41=new SQLwithBindVariables();
    		            		sqlbv41.sql("delete from LCUWSub where contno = '" + "?mContNo?" + "'");
    		            		sqlbv41.put("mContNo", mContNo);
    		            		mapDel.put(sqlbv41, "DELETE");
    		            		//mapDel.put("delete from LCUWError where contno = '" + mContNo + "'", "DELETE");
    		            		//mapDel.put("delete from LCIndUWMaster where contno = '" + mContNo + "'", "DELETE");
    		            		//mapDel.put("delete from LCIndUWSub where contno = '" + mContNo + "'", "DELETE");	
    		            		//mapDel.put("delete from LCIndUWError where contno = '" + mContNo + "'", "DELETE");
    		            		SQLwithBindVariables sqlbv42=new SQLwithBindVariables();
    		            		sqlbv42.sql("delete from LJTempFeeClass where tempfeeno ='" + "?tempfeeno?" + "'");
    		            		sqlbv42.put("tempfeeno", tempfeeno);
    		            		mapDel.put(sqlbv42, "DELETE");
    		            		SQLwithBindVariables sqlbv43=new SQLwithBindVariables();
    		            		sqlbv43.sql("delete from LJTempFee where tempfeeno = '" + "?tempfeeno?" + "'");
    		            		sqlbv43.put("tempfeeno", tempfeeno);
    		            		mapDel.put(sqlbv43, "DELETE");
    		            		SQLwithBindVariables sqlbv44=new SQLwithBindVariables();
    		            		sqlbv44.sql("delete from LJAPay where incomeno = '" + "?mContNo?" + "'");
    		            		sqlbv44.put("mContNo", mContNo);
    		            		mapDel.put(sqlbv44, "DELETE");
    		            		SQLwithBindVariables sqlbv45=new SQLwithBindVariables();
    		            		sqlbv45.sql("delete from LJAPayPerson where contno = '" + "?mContNo?" + "'");
    		            		sqlbv45.put("mContNo", mContNo);
    		            		mapDel.put(sqlbv45, "DELETE");
    		            		//mapDel.put("delete from ljaget where actugetno in (select actugetno from ljagetother where otherno ='" + mContNo + "')", "DELETE");
    		            		//mapDel.put("delete from ljagetother where otherno ='" + mContNo	+ "'", "DELETE");// 若有溢缴也删除
    		            		SQLwithBindVariables sqlbv46=new SQLwithBindVariables();
    		            		sqlbv46.sql("delete from lcpol where contno = '" + "?mContNo?" + "'");
    		            		sqlbv46.put("mContNo", mContNo);
    		            		mapDel.put(sqlbv46, "DELETE");
    		            		SQLwithBindVariables sqlbv47=new SQLwithBindVariables();
    		            		sqlbv47.sql("delete from LCContState where contno = '" + "?mContNo?" + "'");
    		            		sqlbv47.put("mContNo", mContNo);
    		            		mapDel.put(sqlbv47, "DELETE");
    		            		SQLwithBindVariables sqlbv48=new SQLwithBindVariables();
    		            		sqlbv48.sql("delete from lcinsureacc where contno = '" + "?mContNo?" + "'");
    		            		sqlbv48.put("mContNo", mContNo);
    		            		mapDel.put(sqlbv48, "DELETE");
    		            		SQLwithBindVariables sqlbv49=new SQLwithBindVariables();
    		            		sqlbv49.sql("delete from lcinsureaccclass where contno = '" + "?mContNo?" + "'");
    		            		sqlbv49.put("mContNo", mContNo);
    		            		mapDel.put(sqlbv49, "DELETE");
    		            		SQLwithBindVariables sqlbv50=new SQLwithBindVariables();
    		            		sqlbv50.sql("delete from lcinsureaccclassfee where contno = '" + "?mContNo?" + "'");
    		            		sqlbv50.put("mContNo", mContNo);
    		            		mapDel.put(sqlbv50, "DELETE");
    		            		SQLwithBindVariables sqlbv51=new SQLwithBindVariables();
    		            		sqlbv51.sql("delete from lcinsureaccfee where contno = '" + "?mContNo?" + "'");
    		            		sqlbv51.put("mContNo", mContNo);
    		            		mapDel.put(sqlbv51, "DELETE");
    		            		SQLwithBindVariables sqlbv52=new SQLwithBindVariables();
    		            		sqlbv52.sql("delete from lcinsureacctrace where contno = '" + "?mContNo?" + "'");
    		            		sqlbv52.put("mContNo", mContNo);
    		            		mapDel.put(sqlbv52, "DELETE");
    		            		SQLwithBindVariables sqlbv53=new SQLwithBindVariables();
    		            		sqlbv53.sql("delete from lcinsureaccfeetrace where contno = '" + "?mContNo?" + "'");
    		            		sqlbv53.put("mContNo", mContNo);
    		            		mapDel.put(sqlbv53, "DELETE");
    		            		SQLwithBindVariables sqlbv54=new SQLwithBindVariables();
    		            		sqlbv54.sql("delete from lccont where contno = '" + "?mContNo?" + "'");
    		            		sqlbv54.put("mContNo", mContNo);
    		            		mapDel.put(sqlbv54, "DELETE");		
    		            		//mapDel.put("delete from LRResultDetail where serialno = (select serialno from LRResultMain where contno = '"+mContNo+"')", "DELETE");
    		            		//mapDel.put("delete from LRResultMain where contno = '" + mContNo + "'", "DELETE");	

                                //向交易日志表中插入一条记录
    		                    LKTransStatusSchema InsertLKTransStatusSchema = new LKTransStatusSchema();
    		                    InsertLKTransStatusSchema.setTransCode(mLKTransStatusSchema1.getTransCode()+"00");
    		                    InsertLKTransStatusSchema.setBankCode(mLKTransStatusSchema1.getBankCode());
    		                    InsertLKTransStatusSchema.setBankBranch(mLKTransStatusSchema1.getBankBranch());
    		                    InsertLKTransStatusSchema.setBankNode(mLKTransStatusSchema1.getBankNode());
    		                    InsertLKTransStatusSchema.setBankOperator("AutoBalance");
    		                    InsertLKTransStatusSchema.setTransNo(mLKTransStatusSchema1.getTransCode()+"00");
    		                    InsertLKTransStatusSchema.setFuncFlag("00");
    		                    InsertLKTransStatusSchema.setTransDate(mLKTransStatusSchema1.getTransDate());
    		                    InsertLKTransStatusSchema.setPrtNo(mLKTransStatusSchema1.getPrtNo());
    		                    InsertLKTransStatusSchema.setPolNo(mLKTransStatusSchema1.getPolNo());
    		                    InsertLKTransStatusSchema.setProposalNo(mLKTransStatusSchema1.getProposalNo());
    		                    InsertLKTransStatusSchema.setTransAmnt(mLKTransStatusSchema1.getTransAmnt());
    		                    InsertLKTransStatusSchema.setRCode("1");
    		                    InsertLKTransStatusSchema.setTransStatus("2");
    		                    InsertLKTransStatusSchema.setTemp("0");
    		                    InsertLKTransStatusSchema.setDescr("自动对账做承保取消");
    		                    InsertLKTransStatusSchema.setMakeDate(CurrentDay);
    		                    InsertLKTransStatusSchema.setMakeTime(CurrentTime);
    		                    InsertLKTransStatusSchema.setModifyDate(CurrentDay);
    		                    InsertLKTransStatusSchema.setModifyTime(CurrentTime);
    		                    mapDel.put(InsertLKTransStatusSchema, "INSERT");
    		                    
    		                    VData tInputData = new VData();
    		            		tInputData.add(mapDel);
    		            		PubSubmit tPubSubmit = new PubSubmit();
    		            		if (!tPubSubmit.submitData(tInputData, "")) 
    		            		{
    		            			this.dealError("", "数据提交失败！");
    		            			mLKBalanceDetailSchema.setTemp1("0");
    		                        mLKBalanceDetailSchema.setTemp2("承保取消失败");
    		                        updateLKBalanceDetailSet.add(mLKBalanceDetailSchema);
    				            	continue;
    		            		}    		            		
    		            	}
    		            }    		            
    		        }
    		        logger.debug("对账完成，修改对账交易日志表中数据");
    				MMap newmap = new MMap();
    				newmap.put(updateLKBalanceDetailSet, "UPDATE");
    				VData tVData = new VData();
    				tVData.add(newmap);
    				if (!tPubSubmit.submitData(tVData, ""))
    	            {
    	            	this.dealError("", "修改LKBalanceDetail标志位出错!");
    	            	continue;		            	
    	            }
    				
    				//对账成功，修改交费表中的到账日期
    				newmap = new MMap();
    				String sql_fee = " select * from lkbalancedetail where bankcode='"+"?bankcode?"+"' " +
    						"and bankzonecode='"+"?bankzonecode?"+"'"
	                   +" and trandate ='"+"?trandate?"+"' and confirmflag='1' and temp1<>'0'";
    				SQLwithBindVariables sqlbv55=new SQLwithBindVariables();
    				sqlbv55.sql(sql_fee);
    				sqlbv55.put("bankcode", tempLKTransStatusSchema.getBankCode());
    				sqlbv55.put("bankzonecode", tempLKTransStatusSchema.getBankBranch());
    				sqlbv55.put("trandate", tempLKTransStatusSchema.getTransDate());
    				LKBalanceDetailDB ttLKBalanceDetailDB = new LKBalanceDetailDB();
    				LKBalanceDetailSet ttLKBalanceDetailSet = ttLKBalanceDetailDB.executeQuery(sqlbv55);
    				for (int k = 1; k <= ttLKBalanceDetailSet.size(); k++)
    				{
    					String transno = ttLKBalanceDetailSet.get(k).getTransrNo();
		                ExeSQL tExeSQL = new ExeSQL();
		            	SQLwithBindVariables sqlbv56=new SQLwithBindVariables();
	    				sqlbv56.sql("select polno from lktransstatus where " +
		                		"bankcode='"+"?bankcode?"+"'"
		                	+" and bankbranch ='"+"?bankbranch?"+"' and banknode " +
		                			"= '"+"?banknode?"+"' and transno='"+"?transno?"+"'");
	    				sqlbv56.put("bankcode", ttLKBalanceDetailSet.get(k).getBankCode());
	    				sqlbv56.put("bankbranch", ttLKBalanceDetailSet.get(k).getBankZoneCode());
	    				sqlbv56.put("banknode", ttLKBalanceDetailSet.get(k).getBrNo());
	    				sqlbv56.put("transno", transno);
	    				
		                String mContNo = tExeSQL.getOneValue(sqlbv56);
		                //根据合同号的前四位与管理机构是对应的
		                String tCom = mContNo.substring(0, 4);
		                String bankcode = "";
		                SQLwithBindVariables sqlbv57=new SQLwithBindVariables();
		                sqlbv57.sql("select code from ldcode where codetype='bank' and comcode='"+"?tCom?"+"' and code like '03%'");
		                sqlbv57.put("tCom", tCom);
		                bankcode = tExeSQL.getOneValue(sqlbv57);
		                SQLwithBindVariables sqlbv58=new SQLwithBindVariables();
		                sqlbv58.sql("update LJTempFee set ConfMakeDate='"+"?tTransDate?"+"',EnterAccDate='"+"?tTransDate?"+"'," +
		                		"ConfMakeTime='"+"?CurrentTime?"+"'," +
		                		"ModifyDate='"+"?CurrentDay?"+"',ModifyTime='"+"?CurrentTime?"+"' " +
		                				"where otherno = '" + "?mContNo?" + "'");
		                sqlbv58.put("tTransDate", tTransDate);
		                sqlbv58.put("CurrentTime", CurrentTime);
		                sqlbv58.put("CurrentDay", CurrentDay);
		                sqlbv58.put("mContNo", mContNo);
		                newmap.put(sqlbv58, "UPDATE");
		                SQLwithBindVariables sqlbv59=new SQLwithBindVariables();
		                sqlbv59.sql("update LJTempFeeClass set ConfMakeDate='"+"?tTransDate?"+"',EnterAccDate='"+"?tTransDate?"+"'," +
		                		"ConfMakeTime='"+"?CurrentTime?"+"',ModifyDate='"+"?CurrentDay?"+"',ModifyTime='"+"?CurrentTime?"+"'," +
		                				"BankCode='"+"?bankcode?"+"' "
			                	   +"where exists(select 1 from ljtempfee where ljtempfee.tempfeeno=ljtempfeeclass.tempfeeno " +
			                	   "and ljtempfee.otherno= '" + "?mContNo?" + "')");
		                sqlbv59.put("tTransDate", tTransDate);
		                sqlbv59.put("CurrentTime", CurrentTime);
		                sqlbv59.put("CurrentDay", CurrentDay);
		                sqlbv59.put("bankcode", bankcode);
		                sqlbv59.put("mContNo", mContNo);
		                newmap.put(sqlbv59, "UPDATE");
		                SQLwithBindVariables sqlbv60=new SQLwithBindVariables();
		                sqlbv60.sql("update LJAPayPerson set EnterAccDate='"+"?tTransDate?"+"',ModifyDate='"+"?CurrentDay?"+"'," +
		                		"ModifyTime='"+"?CurrentTime?"+"' where contno='"+"?mContNo?"+"'");
		                sqlbv60.put("tTransDate", tTransDate);
		                sqlbv60.put("CurrentDay", CurrentDay);
		                sqlbv60.put("CurrentTime", CurrentTime);
		                sqlbv60.put("mContNo", mContNo);
		                newmap.put(sqlbv60,"UPDATE");
		                SQLwithBindVariables sqlbv61=new SQLwithBindVariables();
		                sqlbv61.sql("update LJAPay set EnterAccDate='"+"?tTransDate?"+"',ModifyDate='"+"?CurrentDay?"+"'," +
		                		"ModifyTime='"+"?CurrentTime?"+"' where incomeno='"+"?mContNo?"+"'");
		                sqlbv61.put("tTransDate", tTransDate);
		                sqlbv61.put("CurrentDay", CurrentDay);
		                sqlbv61.put("CurrentTime", CurrentTime);
		                sqlbv61.put("mContNo", mContNo);
		                newmap.put(sqlbv61, "UPDATE");
    				}
    				SQLwithBindVariables sqlbv62=new SQLwithBindVariables();
	                sqlbv62.sql("update lktransstatus set descr='对账成功!',modifydate='"+"?CurrentDay?"+"',modifytime='"+"?CurrentTime?"+"'" +
	                		" where bankcode='03' "
  						  +"and managecom='"+"?managecom?"+"' and funcflag='17' and transdate='"+"?transdate?"+"'" +
  						  		" and descr is null  ");
	                sqlbv62.put("CurrentDay", CurrentDay);
	                sqlbv62.put("CurrentTime", CurrentTime);
	                sqlbv62.put("managecom", tSSRS.GetText(i,1));
	                sqlbv62.put("transdate", tSSRS.GetText(i,2));
    				newmap.put(sqlbv62, "UPDATE");
    				VData ttVData = new VData();
    				ttVData.add(newmap);
    				if (!tPubSubmit.submitData(ttVData, ""))
    	            {
    	            	this.dealError("", "修改财务表到账日期出错!");
    	            	continue;		            	
    	            }
    					
	
            	}
            }			
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}	


	private MMap DataBakup(String contNo) {
		// TODO Auto-generated method stub
		MMap mapBak = new MMap();
		Reflections tRef = new Reflections();   		
    	
		LCAppntDB tLCAppntDB = new LCAppntDB();
		LCAppntSet tLCAppntSet = new LCAppntSet();
		LCAppntBakSet tLCAppntBakSet = new LCAppntBakSet();    		            		
		tLCAppntDB.setContNo(contNo);
		tLCAppntSet = tLCAppntDB.query();
		if(tLCAppntSet.size()>0)
		{
			for(int k=1;k<=tLCAppntSet.size();k++)
			{
				LCAppntBakSchema tLCAppntBakSchema = new LCAppntBakSchema();
				tRef.transFields(tLCAppntBakSchema, tLCAppntSet.get(k));    		            		
				tLCAppntBakSet.add(tLCAppntBakSchema);				
			}
			mapBak.put(tLCAppntBakSet, "INSERT");
		}
		
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		LCInsuredSet tLCInsuredSet = new LCInsuredSet();
		LCInsuredBakSet tLCInsuredBakSet = new LCInsuredBakSet();    		            		
		tLCInsuredDB.setContNo(contNo);
		tLCInsuredSet = tLCInsuredDB.query();
		if(tLCInsuredSet.size()>0)
		{
			for(int k=1;k<=tLCInsuredSet.size();k++)
			{
				LCInsuredBakSchema tLCInsuredBakSchema = new LCInsuredBakSchema();
				tRef.transFields(tLCInsuredBakSchema, tLCInsuredSet.get(k));    		            		
				tLCInsuredBakSet.add(tLCInsuredBakSchema);				
			}
			mapBak.put(tLCInsuredBakSet, "INSERT");
		}
		
		LCPremDB tLCPremDB = new LCPremDB();
		LCPremSet tLCPremSet = new LCPremSet();
		LCPremBakSet tLCPremBakSet = new LCPremBakSet();    		            		
		tLCPremDB.setContNo(contNo);
		tLCPremSet = tLCPremDB.query();
		if(tLCPremSet.size()>0)
		{
			for(int k=1;k<=tLCPremSet.size();k++)
			{
				LCPremBakSchema tLCPremBakSchema = new LCPremBakSchema();
				tRef.transFields(tLCPremBakSchema, tLCPremSet.get(k));    		            		
				tLCPremBakSet.add(tLCPremBakSchema);				
			}
			mapBak.put(tLCPremBakSet, "INSERT");
		}
		
		LCGetDB tLCGetDB = new LCGetDB();
		LCGetSet tLCGetSet = new LCGetSet();
		LCGetBakSet tLCGetBakSet = new LCGetBakSet();    		            		
		tLCGetDB.setContNo(contNo);
		tLCGetSet = tLCGetDB.query();
		if(tLCGetSet.size()>0)
		{
			for(int k=1;k<=tLCGetSet.size();k++)
			{
				LCGetBakSchema tLCGetBakSchema = new LCGetBakSchema();
				tRef.transFields(tLCGetBakSchema, tLCGetSet.get(k));    		            		
				tLCGetBakSet.add(tLCGetBakSchema);				
			}
			mapBak.put(tLCGetBakSet, "INSERT");
		}
		
		LCDutyDB tLCDutyDB = new LCDutyDB();
		LCDutySet tLCDutySet = new LCDutySet();
		LCDutyBakSet tLCDutyBakSet = new LCDutyBakSet();    		            		
		tLCDutyDB.setContNo(contNo);
		tLCDutySet = tLCDutyDB.query();
		if(tLCDutySet.size()>0)
		{
			for(int k=1;k<=tLCDutySet.size();k++)
			{
				LCDutyBakSchema tLCDutyBakSchema = new LCDutyBakSchema();
				tRef.transFields(tLCDutyBakSchema, tLCDutySet.get(k));    		            		
				tLCDutyBakSet.add(tLCDutyBakSchema);				
			}
			mapBak.put(tLCDutyBakSet, "INSERT");
		}
		
		LCBnfDB tLCBnfDB = new LCBnfDB();
		LCBnfSet tLCBnfSet = new LCBnfSet();
		LCBnfBakSet tLCBnfBakSet = new LCBnfBakSet();    		            		
		tLCBnfDB.setContNo(contNo);
		tLCBnfSet = tLCBnfDB.query();
		if(tLCBnfSet.size()>0)
		{
			for(int k=1;k<=tLCBnfSet.size();k++)
			{
				LCBnfBakSchema tLCBnfBakSchema = new LCBnfBakSchema();
				tRef.transFields(tLCBnfBakSchema, tLCBnfSet.get(k));    		            		
				tLCBnfBakSet.add(tLCBnfBakSchema);				
			}
			mapBak.put(tLCBnfBakSet, "INSERT");
		}
		
		LCCUWMasterDB tLCCUWMasterDB = new LCCUWMasterDB();
		LCCUWMasterSet tLCCUWMasterSet = new LCCUWMasterSet();
		LCCUWMasterBakSet tLCCUWMasterBakSet = new LCCUWMasterBakSet();    		            		
		tLCCUWMasterDB.setContNo(contNo);
		tLCCUWMasterSet = tLCCUWMasterDB.query();
		if(tLCCUWMasterSet.size()>0)
		{
			for(int k=1;k<=tLCCUWMasterSet.size();k++)
			{
				LCCUWMasterBakSchema tLCCUWMasterBakSchema = new LCCUWMasterBakSchema();
				tRef.transFields(tLCCUWMasterBakSchema, tLCCUWMasterSet.get(k));    		            		
				tLCCUWMasterBakSet.add(tLCCUWMasterBakSchema);				
			}
			mapBak.put(tLCCUWMasterBakSet, "INSERT");
		}
		
		LCCUWSubDB tLCCUWSubDB = new LCCUWSubDB();
		LCCUWSubSet tLCCUWSubSet = new LCCUWSubSet();
		LCCUWSubBakSet tLCCUWSubBakSet = new LCCUWSubBakSet();    		            		
		tLCCUWSubDB.setContNo(contNo);
		tLCCUWSubSet = tLCCUWSubDB.query();
		if(tLCCUWSubSet.size()>0)
		{
			for(int k=1;k<=tLCCUWSubSet.size();k++)
			{
				LCCUWSubBakSchema tLCCUWSubBakSchema = new LCCUWSubBakSchema();
				tRef.transFields(tLCCUWSubBakSchema, tLCCUWSubSet.get(k));    		            		
				tLCCUWSubBakSet.add(tLCCUWSubBakSchema);				
			}
			mapBak.put(tLCCUWSubBakSet, "INSERT");
		}
		
		LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB();
		LCUWMasterSet tLCUWMasterSet = new LCUWMasterSet();
		LCUWMasterBakSet tLCUWMasterBakSet = new LCUWMasterBakSet();    		            		
		tLCUWMasterDB.setContNo(contNo);
		tLCUWMasterSet = tLCUWMasterDB.query();
		if(tLCUWMasterSet.size()>0)
		{
			for(int k=1;k<=tLCUWMasterSet.size();k++)
			{
				LCUWMasterBakSchema tLCUWMasterBakSchema = new LCUWMasterBakSchema();
				tRef.transFields(tLCUWMasterBakSchema, tLCUWMasterSet.get(k));    		            		
				tLCUWMasterBakSet.add(tLCUWMasterBakSchema);				
			}
			mapBak.put(tLCUWMasterBakSet, "INSERT");
		}
		
		LCUWSubDB tLCUWSubDB = new LCUWSubDB();
		LCUWSubSet tLCUWSubSet = new LCUWSubSet();
		LCUWSubBakSet tLCUWSubBakSet = new LCUWSubBakSet();    		            		
		tLCUWSubDB.setContNo(contNo);
		tLCUWSubSet = tLCUWSubDB.query();
		if(tLCUWSubSet.size()>0)
		{
			for(int k=1;k<=tLCUWSubSet.size();k++)
			{
				LCUWSubBakSchema tLCUWSubBakSchema = new LCUWSubBakSchema();
				tRef.transFields(tLCUWSubBakSchema, tLCUWSubSet.get(k));    		            		
				tLCUWSubBakSet.add(tLCUWSubBakSchema);				
			}
			mapBak.put(tLCUWSubBakSet, "INSERT");
		}
		
		String tempfeeno = "";
		SQLwithBindVariables sqlbv63=new SQLwithBindVariables();
		sqlbv63.sql("select tempfeeno from lccont where contno='"+"?contNo?"+"'");
		sqlbv63.put("contNo", contNo);
		tempfeeno = tExeSQL.getOneValue(sqlbv63);
		LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
		LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();
		LJTempFeeBakSet tLJTempFeeBakSet = new LJTempFeeBakSet();    		            		
		tLJTempFeeDB.setTempFeeNo(tempfeeno);
		tLJTempFeeSet = tLJTempFeeDB.query();
		if(tLJTempFeeSet.size()>0)
		{
			for(int k=1;k<=tLJTempFeeSet.size();k++)
			{
				LJTempFeeBakSchema tLJTempFeeBakSchema = new LJTempFeeBakSchema();
				tRef.transFields(tLJTempFeeBakSchema, tLJTempFeeSet.get(k));    		            		
				tLJTempFeeBakSet.add(tLJTempFeeBakSchema);				
			}
			mapBak.put(tLJTempFeeBakSet, "INSERT");
		}
		
		LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
		LJTempFeeClassSet tLJTempFeeClassSet = new LJTempFeeClassSet();
		LJTempFeeClassBakSet tLJTempFeeClassBakSet = new LJTempFeeClassBakSet();    		            		
		tLJTempFeeClassDB.setTempFeeNo(tempfeeno);
		tLJTempFeeClassSet = tLJTempFeeClassDB.query();
		if(tLJTempFeeClassSet.size()>0)
		{
			for(int k=1;k<=tLJTempFeeClassSet.size();k++)
			{
				LJTempFeeClassBakSchema tLJTempFeeClassBakSchema = new LJTempFeeClassBakSchema();
				tRef.transFields(tLJTempFeeClassBakSchema, tLJTempFeeClassSet.get(k));    		            		
				tLJTempFeeClassBakSet.add(tLJTempFeeClassBakSchema);				
			}
			mapBak.put(tLJTempFeeClassBakSet, "INSERT");
		}
		
		LJAPayDB tLJAPayDB = new LJAPayDB();
		LJAPaySet tLJAPaySet = new LJAPaySet();
		LJAPayBakSet tLJAPayBakSet = new LJAPayBakSet();    		            		
		tLJAPayDB.setIncomeNo(contNo);
		tLJAPaySet = tLJAPayDB.query();
		if(tLJAPaySet.size()>0)
		{
			for(int k=1;k<=tLJAPaySet.size();k++)
			{
				LJAPayBakSchema tLJAPayBakSchema = new LJAPayBakSchema();
				tRef.transFields(tLJAPayBakSchema, tLJAPaySet.get(k));    		            		
				tLJAPayBakSet.add(tLJAPayBakSchema);				
			}
			mapBak.put(tLJAPayBakSet, "INSERT");
		}
		
		LJAPayPersonDB tLJAPayPersonDB = new LJAPayPersonDB();
		LJAPayPersonSet tLJAPayPersonSet = new LJAPayPersonSet();
		LJAPayPersonBakSet tLJAPayPersonBakSet = new LJAPayPersonBakSet();    		            		
		tLJAPayPersonDB.setContNo(contNo);
		tLJAPayPersonSet = tLJAPayPersonDB.query();
		if(tLJAPayPersonSet.size()>0)
		{
			for(int k=1;k<=tLJAPayPersonSet.size();k++)
			{
				LJAPayPersonBakSchema tLJAPayPersonBakSchema = new LJAPayPersonBakSchema();
				tRef.transFields(tLJAPayPersonBakSchema, tLJAPayPersonSet.get(k));    		            		
				tLJAPayPersonBakSet.add(tLJAPayPersonBakSchema);				
			}
			mapBak.put(tLJAPayPersonBakSet, "INSERT");
		}
		
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = new LCPolSet();
		LCPolBakSet tLCPolBakSet = new LCPolBakSet();    		            		
		tLCPolDB.setContNo(contNo);
		tLCPolSet = tLCPolDB.query();
		if(tLCPolSet.size()>0)
		{
			for(int k=1;k<=tLCPolSet.size();k++)
			{
				LCPolBakSchema tLCPolBakSchema = new LCPolBakSchema();
				tRef.transFields(tLCPolBakSchema, tLCPolSet.get(k));    		            		
				tLCPolBakSet.add(tLCPolBakSchema);				
			}
			mapBak.put(tLCPolBakSet, "INSERT");
		}
		
		LCContStateDB tLCContStateDB = new LCContStateDB();
		LCContStateSet tLCContStateSet = new LCContStateSet();
		LCContStateBakSet tLCContStateBakSet = new LCContStateBakSet();    		            		
		tLCContStateDB.setContNo(contNo);
		tLCContStateSet = tLCContStateDB.query();
		if(tLCContStateSet.size()>0)
		{
			for(int k=1;k<=tLCContStateSet.size();k++)
			{
				LCContStateBakSchema tLCContStateBakSchema = new LCContStateBakSchema();
				tRef.transFields(tLCContStateBakSchema, tLCContStateSet.get(k));    		            		
				tLCContStateBakSet.add(tLCContStateBakSchema);				
			}
			mapBak.put(tLCContStateBakSet, "INSERT");
		}
		
		LCContDB tLCContDB = new LCContDB();
		LCContSet tLCContSet = new LCContSet();
		LCContBakSet tLCContBakSet = new LCContBakSet();    		            		
		tLCContDB.setContNo(contNo);
		tLCContSet = tLCContDB.query();
		if(tLCContSet.size()>0)
		{
			for(int k=1;k<=tLCContSet.size();k++)
			{
				LCContBakSchema tLCContBakSchema = new LCContBakSchema();
				tRef.transFields(tLCContBakSchema, tLCContSet.get(k));    		            		
				tLCContBakSet.add(tLCContBakSchema);				
			}
			mapBak.put(tLCContBakSet, "INSERT");
		}
		
		LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
		LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet();
		LCInsureAccBakSet tLCInsureAccBakSet = new LCInsureAccBakSet();    		            		
		tLCInsureAccDB.setContNo(contNo);
		tLCInsureAccSet = tLCInsureAccDB.query();
		if(tLCInsureAccSet.size()>0)
		{
			for(int k=1;k<=tLCInsureAccSet.size();k++)
			{
				LCInsureAccBakSchema tLCInsureAccBakSchema = new LCInsureAccBakSchema();
				tRef.transFields(tLCInsureAccBakSchema, tLCInsureAccSet.get(k));    		            		
				tLCInsureAccBakSet.add(tLCInsureAccBakSchema);				
			}
			mapBak.put(tLCInsureAccBakSet, "INSERT");
		}
		
		LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
		LCInsureAccClassSet tLCInsureAccClassSet = new LCInsureAccClassSet();
		LCInsureAccClassBakSet tLCInsureAccClassBakSet = new LCInsureAccClassBakSet();    		            		
		tLCInsureAccClassDB.setContNo(contNo);
		tLCInsureAccClassSet = tLCInsureAccClassDB.query();
		if(tLCInsureAccClassSet.size()>0)
		{
			for(int k=1;k<=tLCInsureAccClassSet.size();k++)
			{
				LCInsureAccClassBakSchema tLCInsureAccClassBakSchema = new LCInsureAccClassBakSchema();
				tRef.transFields(tLCInsureAccClassBakSchema, tLCInsureAccClassSet.get(k));    		            		
				tLCInsureAccClassBakSet.add(tLCInsureAccClassBakSchema);				
			}
			mapBak.put(tLCInsureAccClassBakSet, "INSERT");
		}
		
		LCInsureAccClassFeeDB tLCInsureAccClassFeeDB = new LCInsureAccClassFeeDB();
		LCInsureAccClassFeeSet tLCInsureAccClassFeeSet = new LCInsureAccClassFeeSet();
		LCInsureAccClassFeeBakSet tLCInsureAccClassFeeBakSet = new LCInsureAccClassFeeBakSet();    		            		
		tLCInsureAccClassFeeDB.setContNo(contNo);
		tLCInsureAccClassFeeSet = tLCInsureAccClassFeeDB.query();
		if(tLCInsureAccClassFeeSet.size()>0)
		{
			for(int k=1;k<=tLCInsureAccClassFeeSet.size();k++)
			{
				LCInsureAccClassFeeBakSchema tLCInsureAccClassFeeBakSchema = new LCInsureAccClassFeeBakSchema();
				tRef.transFields(tLCInsureAccClassFeeBakSchema, tLCInsureAccClassFeeSet.get(k));    		            		
				tLCInsureAccClassFeeBakSet.add(tLCInsureAccClassFeeBakSchema);				
			}
			mapBak.put(tLCInsureAccClassFeeBakSet, "INSERT");
		}
		
		LCInsureAccFeeDB tLCInsureAccFeeDB = new LCInsureAccFeeDB();
		LCInsureAccFeeSet tLCInsureAccFeeSet = new LCInsureAccFeeSet();
		LCInsureAccFeeBakSet tLCInsureAccFeeBakSet = new LCInsureAccFeeBakSet();    		            		
		tLCInsureAccFeeDB.setContNo(contNo);
		tLCInsureAccFeeSet = tLCInsureAccFeeDB.query();
		if(tLCInsureAccFeeSet.size()>0)
		{
			for(int k=1;k<=tLCInsureAccFeeSet.size();k++)
			{
				LCInsureAccFeeBakSchema tLCInsureAccFeeBakSchema = new LCInsureAccFeeBakSchema();
				tRef.transFields(tLCInsureAccFeeBakSchema, tLCInsureAccFeeSet.get(k));    		            		
				tLCInsureAccFeeBakSet.add(tLCInsureAccFeeBakSchema);				
			}
			mapBak.put(tLCInsureAccFeeBakSet, "INSERT");
		}
		
		LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
		LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet();
		LCInsureAccTraceBakSet tLCInsureAccTraceBakSet = new LCInsureAccTraceBakSet();    		            		
		tLCInsureAccTraceDB.setContNo(contNo);
		tLCInsureAccTraceSet = tLCInsureAccTraceDB.query();
		if(tLCInsureAccTraceSet.size()>0)
		{
			for(int k=1;k<=tLCInsureAccTraceSet.size();k++)
			{
				LCInsureAccTraceBakSchema tLCInsureAccTraceBakSchema = new LCInsureAccTraceBakSchema();
				tRef.transFields(tLCInsureAccTraceBakSchema, tLCInsureAccTraceSet.get(k));    		            		
				tLCInsureAccTraceBakSet.add(tLCInsureAccTraceBakSchema);				
			}
			mapBak.put(tLCInsureAccTraceBakSet, "INSERT");
		}
		
		LCInsureAccFeeTraceDB tLCInsureAccFeeTraceDB = new LCInsureAccFeeTraceDB();
		LCInsureAccFeeTraceSet tLCInsureAccFeeTraceSet = new LCInsureAccFeeTraceSet();
		LCInsureAccFeeTraceBakSet tLCInsureAccFeeTraceBakSet = new LCInsureAccFeeTraceBakSet();    		            		
		tLCInsureAccFeeTraceDB.setContNo(contNo);
		tLCInsureAccFeeTraceSet = tLCInsureAccFeeTraceDB.query();
		if(tLCInsureAccFeeTraceSet.size()>0)
		{
			for(int k=1;k<=tLCInsureAccFeeTraceSet.size();k++)
			{
				LCInsureAccFeeTraceBakSchema tLCInsureAccFeeTraceBakSchema = new LCInsureAccFeeTraceBakSchema();
				tRef.transFields(tLCInsureAccFeeTraceBakSchema, tLCInsureAccFeeTraceSet.get(k));    		            		
				tLCInsureAccFeeTraceBakSet.add(tLCInsureAccFeeTraceBakSchema);				
			}
			mapBak.put(tLCInsureAccFeeTraceBakSet, "INSERT");
		}
		return mapBak;
	}

	/**
	   * 生成错误信息
	   * @param FuncName
	   * @param ErrMsg
	   */
	private void dealError(String FuncName,String ErrMsg)
	{
		CError tError = new CError();
	    tError.moduleName = "LAGrpAutoInner";
	    tError.functionName = FuncName;
	    tError.errorMessage = ErrMsg;	  
		mErrors.addOneError(tError);		
	}
}
