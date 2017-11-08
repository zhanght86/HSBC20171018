package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCBnfDB;
import com.sinosoft.lis.db.LCCUWMasterDB;
import com.sinosoft.lis.db.LCCUWSubDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCContStateDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCInsureAccClassDB;
import com.sinosoft.lis.db.LCInsureAccClassFeeDB;
import com.sinosoft.lis.db.LCInsureAccDB;
import com.sinosoft.lis.db.LCInsureAccFeeDB;
import com.sinosoft.lis.db.LCInsureAccFeeTraceDB;
import com.sinosoft.lis.db.LCInsureAccTraceDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LCUWMasterDB;
import com.sinosoft.lis.db.LCUWSubDB;
import com.sinosoft.lis.db.LJAPayDB;
import com.sinosoft.lis.db.LJAPayPersonDB;
import com.sinosoft.lis.db.LJTempFeeClassDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.db.LKBalanceDetailDB;
import com.sinosoft.lis.db.LKTransStatusDB;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCAppntBakSchema;
import com.sinosoft.lis.schema.LCBnfBakSchema;
import com.sinosoft.lis.schema.LCCUWMasterBakSchema;
import com.sinosoft.lis.schema.LCCUWSubBakSchema;
import com.sinosoft.lis.schema.LCContBakSchema;
import com.sinosoft.lis.schema.LCContStateBakSchema;
import com.sinosoft.lis.schema.LCDutyBakSchema;
import com.sinosoft.lis.schema.LCGetBakSchema;
import com.sinosoft.lis.schema.LCInsureAccBakSchema;
import com.sinosoft.lis.schema.LCInsureAccClassBakSchema;
import com.sinosoft.lis.schema.LCInsureAccClassFeeBakSchema;
import com.sinosoft.lis.schema.LCInsureAccFeeBakSchema;
import com.sinosoft.lis.schema.LCInsureAccFeeTraceBakSchema;
import com.sinosoft.lis.schema.LCInsureAccTraceBakSchema;
import com.sinosoft.lis.schema.LCInsuredBakSchema;
import com.sinosoft.lis.schema.LCPolBakSchema;
import com.sinosoft.lis.schema.LCPremBakSchema;
import com.sinosoft.lis.schema.LCUWMasterBakSchema;
import com.sinosoft.lis.schema.LCUWSubBakSchema;
import com.sinosoft.lis.schema.LJAPayBakSchema;
import com.sinosoft.lis.schema.LJAPayPersonBakSchema;
import com.sinosoft.lis.schema.LJAPaySchema;
import com.sinosoft.lis.schema.LJTempFeeBakSchema;
import com.sinosoft.lis.schema.LJTempFeeClassBakSchema;
import com.sinosoft.lis.schema.LKBalanceDetailSchema;
import com.sinosoft.lis.schema.LKTransStatusSchema;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.lis.vschema.LCAppntBakSet;
import com.sinosoft.lis.vschema.LCAppntSet;
import com.sinosoft.lis.vschema.LCBnfBakSet;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LCCUWMasterBakSet;
import com.sinosoft.lis.vschema.LCCUWMasterSet;
import com.sinosoft.lis.vschema.LCCUWSubBakSet;
import com.sinosoft.lis.vschema.LCCUWSubSet;
import com.sinosoft.lis.vschema.LCContBakSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCContStateBakSet;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LCDutyBakSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetBakSet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCInsureAccBakSet;
import com.sinosoft.lis.vschema.LCInsureAccClassBakSet;
import com.sinosoft.lis.vschema.LCInsureAccClassFeeBakSet;
import com.sinosoft.lis.vschema.LCInsureAccClassFeeSet;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LCInsureAccFeeBakSet;
import com.sinosoft.lis.vschema.LCInsureAccFeeSet;
import com.sinosoft.lis.vschema.LCInsureAccFeeTraceBakSet;
import com.sinosoft.lis.vschema.LCInsureAccFeeTraceSet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCInsureAccTraceBakSet;
import com.sinosoft.lis.vschema.LCInsureAccTraceSet;
import com.sinosoft.lis.vschema.LCInsuredBakSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCPolBakSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremBakSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LCUWMasterBakSet;
import com.sinosoft.lis.vschema.LCUWMasterSet;
import com.sinosoft.lis.vschema.LCUWSubBakSet;
import com.sinosoft.lis.vschema.LCUWSubSet;
import com.sinosoft.lis.vschema.LJAPayBakSet;
import com.sinosoft.lis.vschema.LJAPayPersonBakSet;
import com.sinosoft.lis.vschema.LJAPayPersonSet;
import com.sinosoft.lis.vschema.LJAPaySet;
import com.sinosoft.lis.vschema.LJTempFeeBakSet;
import com.sinosoft.lis.vschema.LJTempFeeClassBakSet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.lis.vschema.LKBalanceDetailSet;
import com.sinosoft.lis.vschema.LKTransStatusSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * 邮储总局自动对账程序
 * @author duanyh 
 * 2009-01-10
 *
 */
public class YCZJDailyBalance extends TaskThread{
private static Logger logger = Logger.getLogger(YCZJDailyBalance.class);

	public YCZJDailyBalance() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		YCZJDailyBalance tYCZJDailyBalance = new YCZJDailyBalance();
		tYCZJDailyBalance.dealMain();
		// TODO Auto-generated method stub
	}
	
	ExeSQL tExeSQL = new ExeSQL();
	String CurrentDay = PubFun.getCurrentDate();
	String CurrentTime = PubFun.getCurrentTime();
	PubSubmit tPubSubmit = new PubSubmit();
	/*
	 * 对账完成后将descr置上一个常量
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
			logger.debug("start YCZJDailyBalance....");
			LKTransStatusDB tLKTransStatusDB = new LKTransStatusDB();
			LKTransStatusSet tempLKTransStatusSet = new LKTransStatusSet();
			//从邮保通交易日志表中查出今天对账的日志
			//条件：邮储端的对账明细数据已经插入到数据库中，当前系统日期30日内还未对账(descr is null，对账完成后将此字段置为“对账成功”)
			String sql = "select * from lktransstatus where bankcode in ('1200','1201') and funcflag='17' and rcode='1' and char_length(BankBranch)=4 and makedate>=(subdate(to_date('"+"?CurrentDay?"+"','yyyy-mm-dd'),30)) and makedate<=to_date('"+"?CurrentDay?"+"','yyyy-mm-dd') and descr is null ";
			logger.debug(sql);
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(sql);
			sqlbv1.put("CurrentDay", CurrentDay);
			tempLKTransStatusSet = tLKTransStatusDB.executeQuery(sqlbv1);
			logger.debug("需要对账的记录数：tLKTransStatusSet.size()="+tempLKTransStatusSet.size());
            for(int i=1; i<=tempLKTransStatusSet.size(); i++)
            {
           	    LKTransStatusSchema tempLKTransStatusSchema = new LKTransStatusSchema(); 
           	    tempLKTransStatusSchema = tempLKTransStatusSet.get(i);
			    logger.debug("开始分公司"+tempLKTransStatusSchema.getBankBranch()+"对账......");            	
            
        		//查找不包含在对账文件中，但是保险公司做过承保交易的日志
        		String sql3=" select a.* from LKTransStatus a, LKCodeMapping c where a.bankcode = c.bankcode and a.BankBranch = c.ZoneNo"
                           +" and a.banknode = c.banknode and a.TransDate = '"+"?TransDate?"+"' and a.BankCode = '"+"?BankCode?"+ "'"
                           +" and c.zoneno = '"+"?zoneno?"+"' and c.Remark5 = '0' and a.FuncFlag = '01' and a.TransStatus = '2' and a.RCode = '1'"
                           +" and a.transno not in (select b.transrno from lkbalancedetail b where b.tranDate = '"+"?TransDate?"+"'"
				           +" and b.BankCode = '"+"?BankCode?"+"' and b.BankZoneCode = '"+"?zoneno?"+"') order by a.transno";
        		logger.debug("balancedetail sql is :" + sql3);
        		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
        		sqlbv2.sql(sql3);
        		sqlbv2.put("TransDate", tempLKTransStatusSchema.getTransDate());
        		sqlbv2.put("zoneno", tempLKTransStatusSchema.getBankBranch());
        		sqlbv2.put("BankCode", tempLKTransStatusSchema.getBankCode());
        		LKTransStatusSet tLKTransStatusSet = new LKTransStatusSet();
		        tLKTransStatusSet = tLKTransStatusDB.executeQuery(sqlbv2);
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
	                logger.debug("检查在LCCont表中是否存在"+tSQL);	      
	                SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
	                sqlbv3.sql(tSQL);
	                sqlbv3.put("contno", tLKTransStatusSchema.getPolNo());
	                int tExistsCount = Integer.parseInt(tExeSQL.getOneValue(sqlbv3));
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
	                tempLKBalanceDetailSchema.setCardNo(tLKTransStatusSchema.getPolNo()); //对于银行未确认的，此处存放保单号，有可能投保单号已经被重复使用
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
		        String sql4=" select * from lkbalancedetail where bankcode='"+"?bankcode?"+"' and bankzonecode='"+"?bankzonecode?"+"'"
		                   +" and trandate ='"+"?trandate?"+"'";
		        logger.debug("取所有的对账明细："+sql4);
		        SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		        sqlbv4.sql(sql4);
		        sqlbv4.put("bankcode", tempLKTransStatusSchema.getBankCode());
		        sqlbv4.put("bankzonecode", tempLKTransStatusSchema.getBankBranch());
		        sqlbv4.put("trandate", tempLKTransStatusSchema.getTransDate());
		        LKBalanceDetailDB mLKBalanceDetailDB = new LKBalanceDetailDB();
		        LKBalanceDetailSet updateLKBalanceDetailSet = new LKBalanceDetailSet();//存放对账完成后需要更新的数据
		        LKBalanceDetailSet mLKBalanceDetailSet = mLKBalanceDetailDB.executeQuery(sqlbv4); //取全部对帐数据
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
		            	//邮储总局确认的交易 LKBalanceDetail.CardNo保存的是投保单号
		            	SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		            	sqlbv5.sql("select count(*) from lccont where (proposalcontno='"+"?proposalcontno?"+"' or contno='"+"?proposalcontno?"+"') and appflag in ('1','4')");
		            	sqlbv5.put("proposalcontno", mLKBalanceDetailSchema.getCardNo());
		            	String count = tExeSQL.getOneValue(sqlbv5);
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
		            	// 邮储总局未确认的，LKBalanceDetail.CardNo保存的是交易日志表中的保单号
		            	String tempsql = "select appflag from lccont where contno='"+"?contno?"+"'";
		            	SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		            	sqlbv6.sql(tempsql);
		            	sqlbv6.put("contno", mLKTransStatusSchema1.getPolNo());
		            	String mContNo = mLKTransStatusSchema1.getPolNo();
		            	if(tExeSQL.getOneValue(sqlbv6).equals("1"))
		            	{
		            		logger.debug("已经签单，删除保单数据，在交易日志表中插入一条数据");    		            		
		            		MMap mapDel = new MMap();	
		            		//删除之前首先备份各个表
		            		mapDel.add(DataBakup(mContNo));
		            		//mapDel.put("delete from LACommisionDetail where grpcontno = '" + mContNo + "'", "DELETE");
		            		//mapDel.put("delete from LCCustomerImpart where contno = '" + mContNo + "'", "DELETE");
		            		//mapDel.put("delete from LCCustomerImpartParams where contno = '" + mContNo + "'", "DELETE");
		            		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		            		sqlbv7.sql("delete from LCAppnt where contno = '" + "?mContNo?" + "'");
		            		sqlbv7.put("mContNo", mContNo);
		            		mapDel.put(sqlbv7, "DELETE");
		            		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		            		sqlbv8.sql("delete from LCInsured where contno = '" + "?mContNo?" + "'");
		            		sqlbv8.put("mContNo", mContNo);
		            		mapDel.put(sqlbv8, "DELETE");  
		            		SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
		            		sqlbv9.sql("delete from LCPrem where contno = '" + "?mContNo?" + "'");
		            		sqlbv9.put("mContNo", mContNo);
		            		mapDel.put(sqlbv9, "DELETE");
		            		SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
		            		sqlbv10.sql("delete from LCGet where contno = '" + "?mContNo?" + "'");
		            		sqlbv10.put("mContNo", mContNo);
		            		mapDel.put(sqlbv10, "DELETE");
		            		SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
		            		sqlbv11.sql("delete from LCDuty where contno = '" + "?mContNo?" + "'");
		            		sqlbv11.put("mContNo", mContNo);
		            		mapDel.put(sqlbv11, "DELETE");
		            		SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
		            		sqlbv12.sql("delete from LCBnf where contno = '" + "?mContNo?" + "'");
		            		sqlbv12.put("mContNo", mContNo);
		            		mapDel.put(sqlbv12, "DELETE");
		            		SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
		            		sqlbv13.sql("delete from LCCUWMaster where contno = '" + "?mContNo?" + "'");
		            		sqlbv13.put("mContNo", mContNo);
		            		mapDel.put(sqlbv13, "DELETE");
		            		SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
		            		sqlbv14.sql("delete from LCCUWSub where contno = '" + "?mContNo?" + "'");
		            		sqlbv14.put("mContNo", mContNo);
		            		mapDel.put(sqlbv14, "DELETE");
		            		//mapDel.put("delete from LCCUWError where contno = '" + mContNo + "'", "DELETE");
		            		SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
		            		sqlbv15.sql("delete from LCUWMaster where contno = '" + "?mContNo?" + "'");
		            		sqlbv15.put("mContNo", mContNo);
		            		mapDel.put(sqlbv15, "DELETE");
		            		SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
		            		sqlbv16.sql("delete from LCUWSub where contno = '" + "?mContNo?" + "'");
		            		sqlbv16.put("mContNo", mContNo);
		            		mapDel.put(sqlbv16, "DELETE");
		            		//mapDel.put("delete from LCUWError where contno = '" + mContNo + "'", "DELETE");
		            		//mapDel.put("delete from LCIndUWMaster where contno = '" + mContNo + "'", "DELETE");
		            		//mapDel.put("delete from LCIndUWSub where contno = '" + mContNo + "'", "DELETE");	
		            		//mapDel.put("delete from LCIndUWError where contno = '" + mContNo + "'", "DELETE");
		            		SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
		            		sqlbv17.sql("delete from LJTempFeeClass where exists(select 1 from ljtempfee where ljtempfee.tempfeeno=ljtempfeeclass.tempfeeno and ljtempfee.otherno='" + "?mContNo?" + "')");
		            		sqlbv17.put("mContNo", mContNo);
		            		mapDel.put(sqlbv17, "DELETE");
		            		SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
		            		sqlbv18.sql("delete from LJTempFee where otherno = '" + "?mContNo?" + "'");
		            		sqlbv18.put("mContNo", mContNo);
		            		mapDel.put(sqlbv18, "DELETE");
		            		SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
		            		sqlbv19.sql("delete from LJAPay where incomeno = '" + "?mContNo?" + "'");
		            		sqlbv19.put("mContNo", mContNo);
		            		mapDel.put(sqlbv19, "DELETE");
		            		SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
		            		sqlbv20.sql("delete from LJAPayPerson where contno = '" + "?mContNo?" + "'");
		            		sqlbv20.put("mContNo", mContNo);
		            		mapDel.put(sqlbv20, "DELETE");
		            		//mapDel.put("delete from ljaget where actugetno in (select actugetno from ljagetother where otherno ='" + mContNo + "')", "DELETE");
		            		//mapDel.put("delete from ljagetother where otherno ='" + mContNo	+ "'", "DELETE");// 若有溢缴也删除
		            		SQLwithBindVariables sqlbv21 = new SQLwithBindVariables();
		            		sqlbv21.sql("delete from lcpol where contno = '" + "?mContNo?" + "'");
		            		sqlbv21.put("mContNo", mContNo);
		            		mapDel.put(sqlbv21, "DELETE");
		            		SQLwithBindVariables sqlbv22 = new SQLwithBindVariables();
		            		sqlbv22.sql("delete from LCContState where contno = '" + "?mContNo?" + "'");
		            		sqlbv22.put("mContNo", mContNo);
		            		mapDel.put(sqlbv22, "DELETE");
		            		SQLwithBindVariables sqlbv23 = new SQLwithBindVariables();
		            		sqlbv23.sql("delete from lcinsureacc where contno = '" + "?mContNo?" + "'");
		            		sqlbv23.put("mContNo", mContNo);
		            		mapDel.put(sqlbv23, "DELETE");
		            		SQLwithBindVariables sqlbv24 = new SQLwithBindVariables();
		            		sqlbv24.sql("delete from lcinsureaccclass where contno = '" + "?mContNo?" + "'");
		            		sqlbv24.put("mContNo", mContNo);
		            		mapDel.put(sqlbv24, "DELETE");
		            		SQLwithBindVariables sqlbv25 = new SQLwithBindVariables();
		            		sqlbv25.sql("delete from lcinsureaccclassfee where contno = '" + "?mContNo?" + "'");
		            		sqlbv25.put("mContNo", mContNo);
		            		mapDel.put(sqlbv25, "DELETE");
		            		SQLwithBindVariables sqlbv26 = new SQLwithBindVariables();
		            		sqlbv26.sql("delete from lcinsureaccfee where contno = '" + "?mContNo?" + "'");
		            		sqlbv26.put("mContNo", mContNo);
		            		mapDel.put(sqlbv26, "DELETE");
		            		SQLwithBindVariables sqlbv27 = new SQLwithBindVariables();
		            		sqlbv27.sql("delete from lcinsureacctrace where contno = '" + "?mContNo?" + "'");
		            		sqlbv27.put("mContNo", mContNo);
		            		mapDel.put(sqlbv27, "DELETE");
		            		SQLwithBindVariables sqlbv28 = new SQLwithBindVariables();
		            		sqlbv28.sql("delete from lcinsureaccfeetrace where contno = '" + "?mContNo?" + "'");
		            		sqlbv28.put("mContNo", mContNo);
		            		mapDel.put(sqlbv28, "DELETE");
		            		SQLwithBindVariables sqlbv29 = new SQLwithBindVariables();
		            		sqlbv29.sql("delete from lccont where contno = '" + "?mContNo?" + "'");
		            		sqlbv29.put("mContNo", mContNo);
		            		mapDel.put(sqlbv29, "DELETE");		
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
		            	else if(tExeSQL.getOneValue(sqlbv6).equals("0"))
		            	{
		            		logger.debug("未签单，删除投保数据");
		            		String tempfeeno = "";
		            		SQLwithBindVariables sqlbv30 = new SQLwithBindVariables();
		            		sqlbv30.sql("select tempfeeno from lccont where contno='"+"?mContNo?"+"'");
		            		sqlbv30.put("mContNo", mContNo);
		            		tempfeeno = tExeSQL.getOneValue(sqlbv30);
		            		MMap mapDel = new MMap();	
		            		//删除之前首先备份各个表
		            		mapDel.add(DataBakup(mContNo));
		            		//mapDel.put("delete from LACommisionDetail where grpcontno = '" + mContNo + "'", "DELETE");
		            		//mapDel.put("delete from LCCustomerImpart where contno = '" + mContNo + "'", "DELETE");
		            		//mapDel.put("delete from LCCustomerImpartParams where contno = '" + mContNo + "'", "DELETE");
		            		SQLwithBindVariables sqlbv31 = new SQLwithBindVariables();
		            		sqlbv31.sql("delete from LCAppnt where contno = '" + "?mContNo?" + "'");
		            		sqlbv31.put("mContNo", mContNo);
		            		mapDel.put(sqlbv31, "DELETE");
		            		SQLwithBindVariables sqlbv32 = new SQLwithBindVariables();
		            		sqlbv32.sql("delete from LCInsured where contno = '" + "?mContNo?" + "'");
		            		sqlbv32.put("mContNo", mContNo);
		            		mapDel.put(sqlbv32, "DELETE");
		            		SQLwithBindVariables sqlbv33 = new SQLwithBindVariables();
		            		sqlbv33.sql("delete from LCPrem where contno = '" + "?mContNo?" + "'");
		            		sqlbv33.put("mContNo", mContNo);
		            		mapDel.put(sqlbv33, "DELETE");
		            		SQLwithBindVariables sqlbv34 = new SQLwithBindVariables();
		            		sqlbv34.sql("delete from LCGet where contno = '" + "?mContNo?" + "'");
		            		sqlbv34.put("mContNo", mContNo);
		            		mapDel.put(sqlbv34, "DELETE");
		            		SQLwithBindVariables sqlbv35 = new SQLwithBindVariables();
		            		sqlbv35.sql("delete from LCDuty where contno = '" + "?mContNo?" + "'");
		            		sqlbv35.put("mContNo", mContNo);
		            		mapDel.put(sqlbv35, "DELETE");
		            		SQLwithBindVariables sqlbv36 = new SQLwithBindVariables();
		            		sqlbv36.sql("delete from LCBnf where contno = '" + "?mContNo?" + "'");
		            		sqlbv36.put("mContNo", mContNo);
		            		mapDel.put(sqlbv36, "DELETE");
		            		SQLwithBindVariables sqlbv37 = new SQLwithBindVariables();
		            		sqlbv37.sql("delete from LCCUWMaster where contno = '" + "?mContNo?" + "'");
		            		sqlbv37.put("mContNo", mContNo);
		            		mapDel.put(sqlbv37, "DELETE");
		            		SQLwithBindVariables sqlbv38 = new SQLwithBindVariables();
		            		sqlbv38.sql("delete from LCCUWSub where contno = '" + "?mContNo?" + "'");
		            		sqlbv38.put("mContNo", mContNo);
		            		mapDel.put(sqlbv38, "DELETE");
		            		//mapDel.put("delete from LCCUWError where contno = '" + mContNo + "'", "DELETE");
		            		SQLwithBindVariables sqlbv39 = new SQLwithBindVariables();
		            		sqlbv39.sql("delete from LCUWMaster where contno = '" + "?mContNo?" + "'");
		            		sqlbv39.put("mContNo", mContNo);
		            		mapDel.put(sqlbv39, "DELETE");	
		            		SQLwithBindVariables sqlbv40 = new SQLwithBindVariables();
		            		sqlbv40.sql("delete from LCUWSub where contno = '" + "?mContNo?" + "'");
		            		sqlbv40.put("mContNo", mContNo);
		            		mapDel.put(sqlbv40, "DELETE");
		            		//mapDel.put("delete from LCUWError where contno = '" + mContNo + "'", "DELETE");
		            		//mapDel.put("delete from LCIndUWMaster where contno = '" + mContNo + "'", "DELETE");
		            		//mapDel.put("delete from LCIndUWSub where contno = '" + mContNo + "'", "DELETE");	
		            		//mapDel.put("delete from LCIndUWError where contno = '" + mContNo + "'", "DELETE");
		            		SQLwithBindVariables sqlbv41 = new SQLwithBindVariables();
		            		sqlbv41.sql("delete from LJTempFeeClass where tempfeeno ='" + "?tempfeeno?" + "'");
		            		sqlbv41.put("tempfeeno", tempfeeno);
		            		mapDel.put(sqlbv41, "DELETE");
		            		SQLwithBindVariables sqlbv42 = new SQLwithBindVariables();
		            		sqlbv42.sql("delete from LJTempFee where tempfeeno = '" + "?tempfeeno?" + "'");
		            		sqlbv42.put("tempfeeno", tempfeeno);
		            		mapDel.put(sqlbv42, "DELETE");
		            		SQLwithBindVariables sqlbv43 = new SQLwithBindVariables();
		            		sqlbv43.sql("delete from LJAPay where incomeno = '" + "?mContNo?" + "'");
		            		sqlbv43.put("mContNo", mContNo);
		            		mapDel.put(sqlbv43, "DELETE");
		            		SQLwithBindVariables sqlbv44 = new SQLwithBindVariables();
		            		sqlbv44.sql("delete from LJAPayPerson where contno = '" + "?mContNo?" + "'");
		            		sqlbv44.put("mContNo", mContNo);
		            		mapDel.put(sqlbv44, "DELETE");
		            		//mapDel.put("delete from ljaget where actugetno in (select actugetno from ljagetother where otherno ='" + mContNo + "')", "DELETE");
		            		//mapDel.put("delete from ljagetother where otherno ='" + mContNo	+ "'", "DELETE");// 若有溢缴也删除
		            		SQLwithBindVariables sqlbv45 = new SQLwithBindVariables();
		            		sqlbv45.sql("delete from lcpol where contno = '" + "?mContNo?" + "'");
		            		sqlbv45.put("mContNo", mContNo);
		            		mapDel.put(sqlbv45, "DELETE");
		            		SQLwithBindVariables sqlbv46 = new SQLwithBindVariables();
		            		sqlbv46.sql("delete from LCContState where contno = '" + "?mContNo?" + "'");
		            		sqlbv46.put("mContNo", mContNo);
		            		mapDel.put(sqlbv46, "DELETE");
		            		SQLwithBindVariables sqlbv47 = new SQLwithBindVariables();
		            		sqlbv47.sql("delete from lcinsureacc where contno = '" + "?mContNo?" + "'");
		            		sqlbv47.put("mContNo", mContNo);
		            		mapDel.put(sqlbv47, "DELETE");
		            		SQLwithBindVariables sqlbv48 = new SQLwithBindVariables();
		            		sqlbv48.sql("delete from lcinsureaccclass where contno = '" + "?mContNo?" + "'");
		            		sqlbv48.put("mContNo", mContNo);
		            		mapDel.put(sqlbv48, "DELETE");
		            		SQLwithBindVariables sqlbv49 = new SQLwithBindVariables();
		            		sqlbv49.sql("delete from lcinsureaccclassfee where contno = '" + "?mContNo?" + "'");
		            		sqlbv49.put("mContNo", mContNo);
		            		mapDel.put(sqlbv49, "DELETE");
		            		SQLwithBindVariables sqlbv50 = new SQLwithBindVariables();
		            		sqlbv50.sql("delete from lcinsureaccfee where contno = '" + "?mContNo?" + "'");
		            		sqlbv50.put("mContNo", mContNo);
		            		mapDel.put(sqlbv50, "DELETE");
		            		SQLwithBindVariables sqlbv51 = new SQLwithBindVariables();
		            		sqlbv51.sql("delete from lcinsureacctrace where contno = '" + "?mContNo?" + "'");
		            		sqlbv51.put("mContNo", mContNo);
		            		mapDel.put(sqlbv51, "DELETE");
		            		SQLwithBindVariables sqlbv52 = new SQLwithBindVariables();
		            		sqlbv52.sql("delete from lcinsureaccfeetrace where contno = '" + "?mContNo?" + "'");
		            		sqlbv52.put("mContNo", mContNo);
		            		mapDel.put(sqlbv52, "DELETE");
		            		SQLwithBindVariables sqlbv53 = new SQLwithBindVariables();
		            		sqlbv53.sql("delete from lccont where contno = '" + "?mContNo?" + "'");
		            		sqlbv53.put("mContNo", mContNo);
		            		mapDel.put(sqlbv53, "DELETE");		
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
				tempLKTransStatusSchema.setDescr("对账成功!");
				tempLKTransStatusSchema.setModifyDate(CurrentDay);
				tempLKTransStatusSchema.setModifyTime(CurrentTime);
				newmap.put(tempLKTransStatusSchema, "UPDATE");
				
				VData tVData = new VData();
				tVData.add(newmap);
				if (!tPubSubmit.submitData(tVData, ""))
	            {
	            	this.dealError("", "修改LKBalanceDetail标志位出错!");
	            	continue;		            	
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
			SQLwithBindVariables sqlbv54 = new SQLwithBindVariables();
			sqlbv54.sql("select tempfeeno from lccont where contno='"+"?contNo?"+"'");
			sqlbv54.put("contNo", contNo);
			tempfeeno = tExeSQL.getOneValue(sqlbv54);
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
	    tError.moduleName = "YCZJDailyBalance";
	    tError.functionName = FuncName;
	    tError.errorMessage = ErrMsg;	  
		mErrors.addOneError(tError);		
	}
}
