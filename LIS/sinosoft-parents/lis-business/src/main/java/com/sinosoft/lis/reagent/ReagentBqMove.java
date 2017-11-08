package com.sinosoft.lis.reagent;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.vschema.*;

import com.sinosoft.utility.*;

import java.sql.*;

/**
 * <p>Title: </p>
 * <p>Description:保全保单迁移中进行孤儿单归属 </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author wk
 * @version 1.0
 */
public class ReagentBqMove
{
private static Logger logger = Logger.getLogger(ReagentBqMove.class);

    public CErrors mErrors = new CErrors();
    private Connection mConn = null;
    private String mContNo = "";
    private String mManageCom = ""; //目标机构
    private String mfManageCom = ""; //原机构
	private String main_xb_flag ="0";//主险续保标记 0，非主险续保，1，主险续保
	private String main_12_flag ="0";//主险年交标记 0，非主险年交保，1，主险年交
    private LAAgentSchema mLAAgentSchema = new LAAgentSchema(); //目标续收员信息
    private LRAscriptionSet mLRAscriptionSet = new LRAscriptionSet();
    private LRAscriptionBSet mLRAscriptionBSet = new LRAscriptionBSet();
    private LRAdimAscriptionSet mLRAdimAscriptionSet = new LRAdimAscriptionSet();
    private LRAdimAscriptionBSet mLRAdimAscriptionBSet = new LRAdimAscriptionBSet();
    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();

    /**
     * decscription:保全项目中关于个险保单迁移的处理
     * （如果发生夸6位机构迁移自动归属给目标机构的客户服务组）
     * @param conn 从外界传入的连接
     * @param tPrtno 迁移保单的印刷号
     * @param tManagecom 目标机构编码（8位）
     * @param fManagecom 原机构编码（8位）
     * @return boolean
     */
    public ReagentBqMove(Connection conn, String tPrtno, String tManagecom,
        String fManagecom)
    {
        mConn = conn;
        mContNo = tPrtno;
        mManageCom = tManagecom;
        mfManageCom = fManagecom;
    }

    /**
     * Discription 业务处理
     * @return boolean
     */
    public boolean submitData()
    {
        if (/*(mConn == null) || */mContNo.equals("") || (mContNo == null)
                || mManageCom.equals("") || (mManageCom == null)
                || mfManageCom.equals("") || (mfManageCom == null))
        {
            return false;
        }
        try{
              if (!JudePolType())
              {
                    String agent_sql = "select agentcode from lcpol where ContNo='"
                                     + "?ContNo?" + "'";
                    SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
                    sqlbv1.sql(agent_sql);
                    sqlbv1.put("ContNo", this.mContNo);
                    ExeSQL tExe = new ExeSQL();
                    LAAgentDB tLAAgentDB = new LAAgentDB();
                    tLAAgentDB.setAgentCode(tExe.getOneValue(sqlbv1));
                    if (!tLAAgentDB.getInfo())
                    {
                          this.buildError("submitData", tLAAgentDB.mErrors.getFirstError());
                          return false;
                    }

                    //如果不需要归属（非个险保单）直接取原来的代理人
                    this.mLAAgentSchema.setSchema(tLAAgentDB.getSchema());
                    return true;
              }
           }
         catch(Exception e)
         {
           logger.debug("逻辑处理前校验出现异常");
           return false;
         }
         ///////////////////////////////////////////////////////////////////////////////
         if (!mManageCom.substring(0, 6).equals(mfManageCom.substring(0, 6)))  //跨6位机构迁移
         {
            if (!dealData1())
            {
               return false;
            }
         }
         else   //8位机构迁移
         {
           if (!dealData2())
           {
             return false;
           }
         }

         //提供给父程序对象 commment by jiaqiangli 2008-10-28
//         if (!upDate())
//         {
//           return false;
//         }
        return true;
    }

    /**
     * Discription：取得目标代理人schema信息
     * @return LAAgentSchema
     */
    public LAAgentSchema getLAAgentSchema()
    {
        return this.mLAAgentSchema;
    }
    
    //add by jiaqiangli 2008-10-08
    //LRAscriptionSet delete&insert
    public LRAscriptionSet getLRAscriptionSet()
    {
        return this.mLRAscriptionSet;
    }
    //LRAscriptionBSet insert
    public LRAscriptionBSet getLRAscriptionBSet()
    {
        return this.mLRAscriptionBSet;
    }
    
    //LRAdimAscriptionSet delete&insert
    public LRAdimAscriptionSet getLRAdimAscriptionSet()
    {
        return this.mLRAdimAscriptionSet;
    }
    //LRAdimAscriptionBSet insert
    public LRAdimAscriptionBSet getLRAdimAscriptionBSet()
    {
        return this.mLRAdimAscriptionBSet;
    }

    //判断保单是否需要处理
    private boolean JudePolType() throws Exception
    {
        //判断保单是否是个险渠道，非个险渠道不处理 排除MS员工特殊保单
        String Flag_sql = "select 1 from lcpol a where ContNo='" + "?ContNo?"
            + "' and salechnl='02'"
            + " and exists(select 1 from laagent where agentcode =a.agentcode and branchtype in ('1','4','5'))"
            + " and not exists (select 1 from labranchgroup where state = '1' and branchtype = '1' and agentgroup = a.agentgroup)";
        SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
        sqlbv2.sql(Flag_sql);
        sqlbv2.put("ContNo", this.mContNo);
        ExeSQL tExe = new ExeSQL();
        if (!"1".equals(tExe.getOneValue(sqlbv2)))
        {
            return false;
        }

        //增加对本身工号就是客户服务组保单的剔除判断
        //当保单为孤儿单时，外勤为客服，校验后剔除
        //当保单为在职单时，跨3级机构迁移不走该校验；跨四级机构迁移，外勤为客服，校验后剔除。
        String judge_agent_sql = "select agentcode from laagent where agentgroup in "
                + " (select agentgroup from labranchgroup where managecom like concat('"
                + "?ContNo?" + "','%')"
                + " and state=1 and branchtype='4' and branchlevel='41') ";
        SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
        sqlbv3.sql(judge_agent_sql);
        sqlbv3.put("ContNo", mManageCom.substring(0, 4));
        String judge_agentcode=tExe.getOneValue(sqlbv3);

        String judge_sql=" select '1' from lrascription a,laagent b where a.agentnew=b.agentcode "
                        +" and a.ContNo='"+"?ContNo?"+"'  and b.agentcode='"+"?agentcode?"+"'"
                        +" union"
                        +" select '1' from lradimascription a,laagent b where a.agentcode=b.agentcode "
                        +" and a.ContNo='"+"?ContNo?"+"'  and b.agentcode='"+"?agentcode?"+"'"
                        +" and exists(select 1 from dual where substr('"+"?mManageCom?"+"',1, 6)=substr('"+"?mfManageCom?"+"',1, 6) )";
        SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
        sqlbv4.sql(judge_agent_sql);
        sqlbv4.put("ContNo",this.mContNo);
        sqlbv4.put("agentcode",judge_agentcode);
        sqlbv4.put("mManageCom", mManageCom);
        sqlbv4.put("mfManageCom", mfManageCom);
        if(tExe.getOneValue(sqlbv4).equals("1"))
         {
            logger.debug("保单本身工号就是客户服务组，剔除");
            return false;
         }
        
        //置上主险的续保收费标记
		ExeSQL check_ExeSQL = new ExeSQL();
		String Rnew_check_Sql = "select count(*) from LCPol a where AppFlag='1' "
            + "and PayToDate = PayEndDate "
            + "and RnewFlag = '-1' "
            + "and (StopFlag='0' or StopFlag is null) and GrpPolNo='00000000000000000000' "
            + "and contno='"+ "?mContNo?" +"' and polno=mainpolno "
            + "and exists (select 1 from lmrisk where riskcode=a.riskcode and rnewflag='Y') " ;
        SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
        sqlbv5.sql(Rnew_check_Sql);
        sqlbv5.put("mContNo",mContNo);
		int RnewCheck = Integer.parseInt( check_ExeSQL.getOneValue(sqlbv5) );
		if(RnewCheck>0)
		{
			main_xb_flag="1";
		}
		
        //增加校验，仅仅归属主险年交件以及趸交件(含续保) add by xiongzh 09-12-29
        main_12_flag="";
		String main_12_Sql = "select count(*) from LCPol a where AppFlag='1' "
            + "and payintv=12 and (StopFlag='0' or StopFlag is null) and GrpPolNo='00000000000000000000' "
            + "and contno='"+ "?mContNo?" +"' and polno=mainpolno ";
		SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
        sqlbv6.sql(main_12_Sql);
        sqlbv6.put("mContNo",mContNo);
		int main_12_Check = Integer.parseInt( check_ExeSQL.getOneValue(sqlbv6) );
		if(main_12_Check>0)
		{
			main_12_flag="1";
		}
        if( !"1".equals(main_12_flag) && !"1".equals(main_xb_flag) )
        {
        	logger.debug("保单迁移时仅仅归属符合主险为年交或者趸交(含续保)的保单。");
        	return false;
        }
		
        //取出当前归属月的应收月以及当月归属日期 modify by xiongzh 09-12-16
		String tPayToDate="";
		String tAscriptDate="";
		if(main_xb_flag.equals("1"))  //主险续保件,往后加2个月
		{ 
			SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
	        sqlbv7.sql("select to_char(add_months('"+"?can?"+"',2),'yyyymm') from dual");
	        sqlbv7.put("can",this.CurrentDate);
			tPayToDate = tExe.getOneValue(sqlbv7);
			//续保件归属日为每个月最后一天
			SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
	        sqlbv8.sql("select to_char(last_day('"+"?cany?"+"'),'yyyy-mm-dd') from dual ");
	        sqlbv8.put("cany",this.CurrentDate);
			tAscriptDate = tExe.getOneValue(sqlbv8);
		}
		else  //续期件，往后加1月
		{
			SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
	        sqlbv9.sql("select to_char(add_months('"+"?date?"+"',1),'yyyymm') from dual");
	        sqlbv9.put("date",this.CurrentDate);
			tPayToDate = tExe.getOneValue(sqlbv9);
			
			LAStatSegmentDB tLAStatSegmentDB = new LAStatSegmentDB();
	        tLAStatSegmentDB.setYearMonth(this.CurrentDate.substring(0,4)+this.CurrentDate.subSequence(5,7));
	        tLAStatSegmentDB.setStatType("9");
	        if(!tLAStatSegmentDB.getInfo())
	        {
	             throw new Exception("tLAStatSegmentDB.getInfo()执行失败");
	        }
	        tAscriptDate = tLAStatSegmentDB.getStartDate();
		}

        String pol_paytodate_sql ="select to_char(paytodate,'yyyymm') from lcpol where ContNo='"+"?mContNo?"+"' and polno=mainpolno and appflag='1'";
        SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
        sqlbv10.sql(pol_paytodate_sql);
        sqlbv10.put("mContNo",this.mContNo);
        String pol_paytodate = tExe.getOneValue(sqlbv10);

        /**
         *    保单应交年月    －－   当月归属月对应的应收月，比如当前为10月，即归属月为10月，10月份归属的保单的应收月应该是：续期件--11月;续保件--12月
         *    当前系统时间    －－   当月归属时间
         *    1.保单应交年月 > 当月归属月对应的应收月 ：说明还没进行保单归属，此处迁移不作孤儿单归属
         *    2.保单应交年月 = 当月归属月对应的应收月
         *      2.1 当前系统时间 >= 当月归属时间 ：已进行过归属，保单迁移变化后需要作孤儿单归属给特殊组
         *      2.2 当前系统时间 < 当月归属时间  ：说明还没进行保单归属，此处迁移不作孤儿单归属
         *    3.保单应交年月 < 当月归属月对应的应收月 ：已进行过归属，保单迁移变化后需要作孤儿单归属给特殊组
         */
        if(pol_paytodate.compareTo(tPayToDate)>0)
        {
              return false;
        }
        else if(pol_paytodate.compareTo(tPayToDate)==0)
        {
              if(this.CurrentDate.compareTo(tAscriptDate)>=0)
              {
                    return true;
              }
              else
              {
                    return false;
              }
        }
        else if(pol_paytodate.compareTo(tPayToDate)<0)
        {
              return true;
        }

        return true;
    }

    private boolean dealData1()
    {
        try
        {
            LCPolSet tLCPolSet = new LCPolSet();
            LCPolDB tLCPolDB = new LCPolDB();
            String Lcpol_sql = "select * from lcpol a where ContNo='"+"?mContNo?"+"' "
            +" and  paytodate =(select paytodate from lcpol where polno =a.mainpolno) ";
            SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
            sqlbv11.sql(Lcpol_sql);
            sqlbv11.put("mContNo",mContNo);
            //tLCPolDB.setPrtNo(mContNo);
            tLCPolSet = tLCPolDB.executeQuery(sqlbv11);
            if (tLCPolSet.size() <= 0)
            {
                return false;
            }

            LAAgentSchema tLAAgentSchema = new LAAgentSchema();
            LAAgentDB tLAAgentDB = new LAAgentDB();
            String agent_sql = "select * from laagent where agentgroup in "
                + " (select agentgroup from labranchgroup where managecom like concat('"
                + "?managecom?" + "','%')"
                + " and state=1 and branchtype='4' and branchlevel='41') ";
            SQLwithBindVariables sqlbv12=new SQLwithBindVariables();
            sqlbv12.sql(agent_sql);
            sqlbv12.put("managecom", mManageCom.substring(0, 4));
            tLAAgentSchema = tLAAgentDB.executeQuery(sqlbv12).get(1);
            this.mLAAgentSchema.setSchema(tLAAgentSchema);
            Reflections tReflections = new Reflections();
            for (int i = 1; i <= tLCPolSet.size(); i++)
            {
            	LRAscriptionDB tLRAscriptionDB = new LRAscriptionDB();
                LRAscriptionSet tLRAscriptionSet = new LRAscriptionSet();
                LRAscriptionSet xxxLRAscriptionSet = new LRAscriptionSet();
                
            	String inlrascription_sql =
                    "select * from lrascription where riskcode='"
                    + "?riskcode?" + "'" + " and ContNo='"
                    + "?mContNo?" + "' ";
            	 SQLwithBindVariables sqlbv13=new SQLwithBindVariables();
                 sqlbv13.sql(inlrascription_sql);
                 sqlbv13.put("riskcode", tLCPolSet.get(i).getRiskCode());
                 sqlbv13.put("mContNo", mContNo);
                xxxLRAscriptionSet = tLRAscriptionDB.executeQuery(sqlbv13);
                if(xxxLRAscriptionSet.size()>0)  //处理孤儿单
                {
                	tLRAscriptionSet = new LRAscriptionSet();
                    String lrascription_sql ="select * from lrascription where riskcode='"
                      + "?riskcode?" + "'" + " and ContNo='"
                      + mContNo + "' and ascriptiondate='"
                      + "?ContNo?" + "-01'";
                    SQLwithBindVariables sqlbv14=new SQLwithBindVariables();
                    sqlbv14.sql(lrascription_sql);
                    sqlbv14.put("riskcode", tLCPolSet.get(i).getRiskCode());
                    sqlbv14.put("ContNo", tLCPolSet.get(i).getPaytoDate().substring(0, 7));
                    tLRAscriptionSet = tLRAscriptionDB.executeQuery(sqlbv14);
	                if (tLRAscriptionSet.size() > 0)  //处理当期孤儿单
	                {
	                    for (int j = 1; j <= tLRAscriptionSet.size(); j++)
	                    {
	                        LRAscriptionBSchema tLRAscriptionBSchema = new LRAscriptionBSchema();
	                        LRAscriptionSchema tLRAscriptionSchema = tLRAscriptionSet
	                            .get(j);
	
	                        tReflections.transFields(tLRAscriptionBSchema,
	                            tLRAscriptionSchema);
	                        tLRAscriptionBSchema.setModifyDate1(tLRAscriptionBSchema
	                            .getModifyDate());
	                        tLRAscriptionBSchema.setModifyTime1(tLRAscriptionBSchema
	                            .getModifyTime());
	                        tLRAscriptionBSchema.setMakeDate1(tLRAscriptionBSchema
	                            .getMakeDate());
	                        tLRAscriptionBSchema.setMakeTime1(tLRAscriptionBSchema
	                            .getMakeTime());
	                        tLRAscriptionBSchema.setOperator1(tLRAscriptionBSchema
	                            .getOperator());
	                        tLRAscriptionBSchema.setMakeDate(this.CurrentDate);
	                        tLRAscriptionBSchema.setModifyDate(this.CurrentDate);
	                        tLRAscriptionBSchema.setModifyTime(this.CurrentTime);
	                        tLRAscriptionBSchema.setMakeTime(this.CurrentTime);
	                        tLRAscriptionBSchema.setEdorNO(PubFun1.CreateMaxNo(
	                                "EdorNo", 20));
	                        tLRAscriptionBSchema.setOperator("auto_mo");
	                        tLRAscriptionBSchema.setEdorType("06"); //保单迁移
	                        this.mLRAscriptionBSet.add(tLRAscriptionBSchema);
	
	                        tLRAscriptionSchema.setAgentOld(tLRAscriptionSchema
	                            .getAgentNew());
	                        tLRAscriptionSchema.setAgentNew(tLAAgentSchema
	                            .getAgentCode()); //修改为特殊组
	                        tLRAscriptionSchema.setManageCom(this.mManageCom); //修改为新的管理机构
	                        tLRAscriptionSchema.setOperator("auto_mo");
	                        tLRAscriptionSchema.setModifyDate(this.CurrentDate);
	                        tLRAscriptionSchema.setModifyTime(this.CurrentTime);
	                        tLRAscriptionSchema.setAscriptType("03"); //03 - 保单迁移归属
	                        this.mLRAscriptionSet.add(tLRAscriptionSchema);
	                    }
	                }
	                else  //处理本期以前的归属记录，按照保单再次归属备份 B表edortype为02
	                {
	                    //add by wk 2006-11-3 判断是否有本期以前的归属记录，按照保单再次归属备份 B表edortype为02
	                    String old_lrascription_sql =
	                        "select * from lrascription where riskcode='"
	                        + "?riskcode?"  + "'" + " and ContNo='"
	                        + "?mContNo?" + "' ";
	                    SQLwithBindVariables sqlbv15=new SQLwithBindVariables();
	                    sqlbv15.sql(old_lrascription_sql);
	                    sqlbv15.put("riskcode", tLCPolSet.get(i).getRiskCode());
	                    sqlbv15.put("mContNo", mContNo);
	                    tLRAscriptionDB = new LRAscriptionDB();
	                    tLRAscriptionSet = new LRAscriptionSet();
	                    tLRAscriptionSet = tLRAscriptionDB.executeQuery(sqlbv15);
	                    for (int k = 1; k <= tLRAscriptionSet.size(); k++)
	                    {
	                        LRAscriptionBSchema tLRAscriptionBSchema = new LRAscriptionBSchema();
	                        LRAscriptionSchema tLRAscriptionSchema = tLRAscriptionSet
	                            .get(k);
	
	                        tReflections.transFields(tLRAscriptionBSchema,
	                            tLRAscriptionSchema);
	                        tLRAscriptionBSchema.setModifyDate1(tLRAscriptionBSchema
	                            .getModifyDate());
	                        tLRAscriptionBSchema.setModifyTime1(tLRAscriptionBSchema
	                            .getModifyTime());
	                        tLRAscriptionBSchema.setMakeDate1(tLRAscriptionBSchema
	                            .getMakeDate());
	                        tLRAscriptionBSchema.setMakeTime1(tLRAscriptionBSchema
	                            .getMakeTime());
	                        tLRAscriptionBSchema.setOperator1(tLRAscriptionBSchema
	                            .getOperator());
	                        tLRAscriptionBSchema.setMakeDate(this.CurrentDate);
	                        tLRAscriptionBSchema.setModifyDate(this.CurrentDate);
	                        tLRAscriptionBSchema.setModifyTime(this.CurrentTime);
	                        tLRAscriptionBSchema.setMakeTime(this.CurrentTime);
	                        tLRAscriptionBSchema.setEdorNO(PubFun1.CreateMaxNo(
	                                "EdorNo", 20));
	                        tLRAscriptionBSchema.setOperator("auto_mo");
	                        tLRAscriptionBSchema.setEdorType("02"); //保单迁移
	                        this.mLRAscriptionBSet.add(tLRAscriptionBSchema);
	                    }
	
	                    if (((tLCPolSet.get(i).getPayIntv() == 12)
	                            || (tLCPolSet.get(i).getRnewFlag() == -1)))
	                    {
	                        LRAscriptionSchema tLRAscriptionSchema = new LRAscriptionSchema();
	                        tLRAscriptionSchema.setAClass("02");
	                        tLRAscriptionSchema.setAgentNew(tLAAgentSchema
	                            .getAgentCode());  //修改为特殊组
	                        tLRAscriptionSchema.setAgentOld(tLCPolSet.get(i)
	                                                                 .getAgentCode());
	                        tLRAscriptionSchema.setAscriptionDate(tLCPolSet.get(i)
	                                                                       .getPaytoDate()
	                                                                       .substring(0,
	                                7) + "-01");
	                        tLRAscriptionSchema.setAscriptType("03"); //03 - 保单迁移归属
	                        tLRAscriptionSchema.setCValiDate(tLCPolSet.get(i)
	                                                                  .getCValiDate());
	                        tLRAscriptionSchema.setMainPolNo(tLCPolSet.get(i)
	                                                                  .getMainPolNo());	                    
	                        tLRAscriptionSchema.setMakeDate(this.CurrentDate);
	                        tLRAscriptionSchema.setMakeTime(this.CurrentTime);
	                        tLRAscriptionSchema.setModifyDate(this.CurrentDate);
	                        tLRAscriptionSchema.setModifyTime(this.CurrentTime);
	                        tLRAscriptionSchema.setManageCom(this.mManageCom);
	                        if (tLCPolSet.get(i).getRnewFlag() == -1)
	                        {
	                            //                            //续保的保单
	                            //                            String OldPolnoSql =
	                            //                                "select polno from LCRnewStateLog where ProposalNo ='"
	                            //                                + tLCPolSet.get(i).getPolNo() + "'";
	                            //                            ExeSQL tExeSQL = new ExeSQL();
	                            //                            logger.debug("+OldPolnoSql+" + OldPolnoSql);
	                            //                            tLRAscriptionSchema.setOldPolno(tExeSQL.getOneValue(
	                            //                                    OldPolnoSql));
	                            tLRAscriptionSchema.setPayCount("1");
	                        }
	                        else
	                        {
	                            //如果是续期的长险，老保单号为其保单号
	                            //                            tLRAscriptionSchema.setOldPolno(tLCPolSet.get(i)
	                            //                                                                     .getPolNo());
	                            //注意实收表中最大次数加一
	                            String paycount_sql =
	                                "select max(paycount)+1 from ljapayperson where (char_length(trim(dutycode))<>10 or substr(dutycode,7,1)<>'1') and payType = 'ZC'"
	                                + " and polno='" + "?polno?"
	                                + "' and curpaytodate='"
	                                + "?curpaytodate?" + "'";
	                            ExeSQL tExeSQL = new ExeSQL();
	                            SQLwithBindVariables sqlbv16=new SQLwithBindVariables();
	    	                    sqlbv16.sql(paycount_sql);
	    	                    sqlbv16.put("polno", tLCPolSet.get(i).getPolNo());
	    	                    sqlbv16.put("curpaytodate", tLCPolSet.get(i).getPaytoDate());
	                            tLRAscriptionSchema.setPayCount(tExeSQL.getOneValue(
	                            		sqlbv16));
	                        }
	
	                        //对于老保单号由于保单表中此时必定只存在老保单不存在投保单所以直接取保单表中保单号
	                        tLRAscriptionSchema.setOldPolno(tLCPolSet.get(i)
	                                                                 .getPolNo());
	                        tLRAscriptionSchema.setOperator("auto_mo");
	                        tLRAscriptionSchema.setPayToDate(tLCPolSet.get(i)
	                                                                  .getPaytoDate());
	                        tLRAscriptionSchema.setPolNo(tLCPolSet.get(i).getPolNo());
	                        tLRAscriptionSchema.setPrtNo(tLCPolSet.get(i).getPrtNo());
	                        tLRAscriptionSchema.setRiskCode(tLCPolSet.get(i)
	                                                                 .getRiskCode());
	                        tLRAscriptionSchema.setValidFlag(getLongShort(
	                                tLCPolSet.get(i).getRiskCode()));
                            //增加合同号
	                        tLRAscriptionSchema.setContNo(tLCPolSet.get(i).getContNo());
	                        
	                        //添加保单年度
	                        String bdnd_sql="";
	                        bdnd_sql=" select substr('"+"?CurrentDate?"+"',1,4)-substr('"+"?tLCPolSet?"+"',1,4) from dual ";
	                        ExeSQL gExeSQL= new ExeSQL();
	                        SQLwithBindVariables sqlbv17=new SQLwithBindVariables();
    	                    sqlbv17.sql(bdnd_sql);
    	                    sqlbv17.put("CurrentDate", this.CurrentDate);
    	                    sqlbv17.put("tLCPolSet", tLCPolSet.get(i)
  	                              .getCValiDate());
	                        tLRAscriptionSchema.setPayYear(gExeSQL.getOneValue(sqlbv17));
	                        logger.debug("tLRAscriptionSchema:"+tLRAscriptionSchema.encode());
	                        this.mLRAscriptionSet.add(tLRAscriptionSchema);
	                    }
	                }
               }
              else  //处理在职单
              {          	 
            	  LRAdimAscriptionDB tLRAdimAscriptionDB = new LRAdimAscriptionDB();
                  LRAdimAscriptionSet tLRAdimAscriptionSet = new LRAdimAscriptionSet();
            	  String lradimascription_sql ="select * from lradimascription where riskcode='"
                      + "?riskcode?" + "'" + " and ContNo='"
                      + "?mContNo?" + "' ";
            	  SQLwithBindVariables sqlbv17=new SQLwithBindVariables();
                  sqlbv17.sql(lradimascription_sql);
                  sqlbv17.put("riskcode", tLCPolSet.get(i).getRiskCode());
                  sqlbv17.put("mContNo", mContNo);
                  tLRAdimAscriptionSet = tLRAdimAscriptionDB.executeQuery(sqlbv17);
                  String oldagent="";
                  if(tLRAdimAscriptionSet.size()>0)
                  {
                	  oldagent= tLRAdimAscriptionSet.get(1).getAgentCode();
                  }
                  else
                  {
                	  oldagent = tLCPolSet.get(i).getAgentCode();
                  }
                  for(int w=1;w<=tLRAdimAscriptionSet.size();w++)
                  {   //首先往在职单备份表添加记录
                      LRAdimAscriptionSchema tLRAdimAscriptionSchema = new LRAdimAscriptionSchema();
                      LRAdimAscriptionBSchema tLRAdimAscriptionBSchema = new LRAdimAscriptionBSchema();
                      tLRAdimAscriptionSchema=tLRAdimAscriptionSet.get(w);

                      tReflections.transFields(tLRAdimAscriptionBSchema,
                          tLRAdimAscriptionSchema);
                      tLRAdimAscriptionBSchema.setEdorNo(PubFun1.CreateMaxNo(
                          "EdorNo", 20));
                      tLRAdimAscriptionBSchema.setMakeDate1(tLRAdimAscriptionBSchema
                          .getMakeDate());
                      tLRAdimAscriptionBSchema.setMakeTime1(tLRAdimAscriptionBSchema
                          .getMakeTime());
                      tLRAdimAscriptionBSchema.setModifyDate1(tLRAdimAscriptionBSchema
                          .getModifyDate());
                      tLRAdimAscriptionBSchema.setModifyTime1(tLRAdimAscriptionBSchema
                          .getModifyTime());
                      tLRAdimAscriptionBSchema.setOperator1(tLRAdimAscriptionBSchema
                          .getOperator());
                      tLRAdimAscriptionBSchema.setMakeDate(this.CurrentDate);
                      tLRAdimAscriptionBSchema.setMakeTime(this.CurrentTime);
                      tLRAdimAscriptionBSchema.setModifyDate(this.CurrentDate);
                      tLRAdimAscriptionBSchema.setModifyTime(this.CurrentTime);

                      tLRAdimAscriptionBSchema.setOperator("auto_mo");
                      tLRAdimAscriptionBSchema.setEdorType("06"); //保单迁移

                      this.mLRAdimAscriptionBSet.add(tLRAdimAscriptionBSchema);
                   } 
                  
                  //然后是往孤儿单归属表插入新记录
                  LRAscriptionSchema xxxLRAscriptionSchema = new LRAscriptionSchema();
                        
                  xxxLRAscriptionSchema.setAClass("02");
                  xxxLRAscriptionSchema.setAgentNew(tLAAgentSchema
                      .getAgentCode());  //修改为特殊组
                  xxxLRAscriptionSchema.setAgentOld(oldagent);
                  xxxLRAscriptionSchema.setAscriptionDate(tLCPolSet.get(i)
                          .getPaytoDate().substring(0,7) + "-01");
                  xxxLRAscriptionSchema.setAscriptType("03"); //03 - 保单迁移归属
                  xxxLRAscriptionSchema.setCValiDate(tLCPolSet.get(i)
                                                            .getCValiDate());
                  xxxLRAscriptionSchema.setMainPolNo(tLCPolSet.get(i)
                                                            .getMainPolNo());
                  xxxLRAscriptionSchema.setMakeDate(this.CurrentDate);
                  xxxLRAscriptionSchema.setMakeTime(this.CurrentTime);
                  xxxLRAscriptionSchema.setModifyDate(this.CurrentDate);
                  xxxLRAscriptionSchema.setModifyTime(this.CurrentTime);
                  xxxLRAscriptionSchema.setManageCom(this.mManageCom);
                  if (tLCPolSet.get(i).getRnewFlag() == -1)
                  {
                      //                            //续保的保单
                      //                            String OldPolnoSql =
                      //                                "select polno from LCRnewStateLog where ProposalNo ='"
                      //                                + tLCPolSet.get(i).getPolNo() + "'";
                      //                            ExeSQL tExeSQL = new ExeSQL();
                      //                            logger.debug("+OldPolnoSql+" + OldPolnoSql);
                      //                            tLRAscriptionSchema.setOldPolno(tExeSQL.getOneValue(
                      //                                    OldPolnoSql));
                	  xxxLRAscriptionSchema.setPayCount("1");
                  }
                  else
                  {
                      //如果是续期的长险，老保单号为其保单号
                      //                            tLRAscriptionSchema.setOldPolno(tLCPolSet.get(i)
                      //                                                                     .getPolNo());
                      //注意实收表中最大次数加一
                      String paycount_sql =
                          "select max(paycount)+1 from ljapayperson where (char_length(trim(dutycode))<>10 or substr(dutycode,7,1)<>'1') and payType = 'ZC'"
                          + " and polno='" +"?polno?"
                          + "' and curpaytodate='"
                          + "?polno?" + "'";
                      ExeSQL tExeSQL = new ExeSQL();
                      SQLwithBindVariables sqlbv18=new SQLwithBindVariables();
                      sqlbv18.sql(paycount_sql);
                      sqlbv18.put("polno",  tLCPolSet.get(i).getPolNo());
                      xxxLRAscriptionSchema.setPayCount(tExeSQL.getOneValue(
                    		  sqlbv18));
                  }

                  //对于老保单号由于保单表中此时必定只存在老保单不存在投保单所以直接取保单表中保单号
                  xxxLRAscriptionSchema.setOldPolno(tLCPolSet.get(i)
                                                           .getPolNo());
                  xxxLRAscriptionSchema.setOperator("auto_mo");
                  xxxLRAscriptionSchema.setPayToDate(tLCPolSet.get(i)
                                                            .getPaytoDate());
                  xxxLRAscriptionSchema.setPolNo(tLCPolSet.get(i).getPolNo());
                  xxxLRAscriptionSchema.setPrtNo(tLCPolSet.get(i).getPrtNo());
                  xxxLRAscriptionSchema.setRiskCode(tLCPolSet.get(i)
                                                           .getRiskCode());
                  xxxLRAscriptionSchema.setValidFlag(getLongShort(
                          tLCPolSet.get(i).getRiskCode()));
                  //增加合同号
                  xxxLRAscriptionSchema.setContNo(tLCPolSet.get(i).getContNo());
                  
                  //添加保单年度
                  String bdnd_sql="";
                  bdnd_sql=" select substr('"+"?CurrentDate?"+"',1,4)-substr('"+"?tLCPolSet?"+"',1,4) from dual ";
                  SQLwithBindVariables sqlbv19=new SQLwithBindVariables();
                  sqlbv19.sql(bdnd_sql);
                  sqlbv19.put("CurrentDate", this.CurrentDate);
                  sqlbv19.put("tLCPolSet", tLCPolSet.get(i)
                          .getCValiDate());
                  ExeSQL gExeSQL= new ExeSQL();
                  xxxLRAscriptionSchema.setPayYear(gExeSQL.getOneValue(sqlbv19));
                  logger.debug("tLRAscriptionSchema:"+xxxLRAscriptionSchema.encode());
                  this.mLRAscriptionSet.add(xxxLRAscriptionSchema);
                               
              }
           }
        }
        catch (Exception e)
        {
            logger.debug("e!!!!!!!!!!!!!!!!!!!!!!" + e.toString());
            this.buildError("DealData", e.toString());
            return false;
        }

        return true;
    }

    private boolean dealData2()
    {
        try
        {
            LCPolSet tLCPolSet = new LCPolSet();
            LCPolDB tLCPolDB = new LCPolDB();
            String Lcpol_sql = "select * from lcpol a where ContNo='"+"?mContNo?"+"' "
            +" and  paytodate =(select paytodate from lcpol where polno =a.mainpolno) ";
            SQLwithBindVariables sqlbv20=new SQLwithBindVariables();
            sqlbv20.sql(Lcpol_sql);
            sqlbv20.put("mContNo", mContNo);
            //tLCPolDB.setPrtNo(mContNo);
            tLCPolSet = tLCPolDB.executeQuery(sqlbv20);
            if (tLCPolSet.size() <= 0)
            {
                return false;
            }

            LAAgentSchema tLAAgentSchema = new LAAgentSchema();
            LAAgentDB tLAAgentDB = new LAAgentDB();
            String agent_sql = "select * from laagent where agentgroup in "
                + " (select agentgroup from labranchgroup where managecom like concat('"
                + "?managecom?" + "','%')"
                + " and state=1 and branchtype='4' and branchlevel='41') ";
            SQLwithBindVariables sqlbv21=new SQLwithBindVariables();
            sqlbv21.sql(agent_sql);
            sqlbv21.put("managecom", mManageCom.substring(0, 4));
            tLAAgentSchema = tLAAgentDB.executeQuery(sqlbv21).get(1);
            this.mLAAgentSchema.setSchema(tLAAgentSchema);
            Reflections tReflections = new Reflections();
            for (int i = 1; i <= tLCPolSet.size(); i++)
            {
                LRAscriptionDB tLRAscriptionDB = new LRAscriptionDB();
                LRAscriptionSet tLRAscriptionSet = new LRAscriptionSet();
                LRAdimAscriptionDB tLRAdimAscriptionDB = new LRAdimAscriptionDB();
                LRAdimAscriptionSet tLRAdimAscriptionSet = new LRAdimAscriptionSet();

                String inlrascription_sql =
                        "select * from lrascription where riskcode='"
                        + "?riskcode?" + "'" + " and ContNo='"
                        + "?mContNo?" + "' ";
                SQLwithBindVariables sqlbv22=new SQLwithBindVariables();
                sqlbv22.sql(inlrascription_sql);
                sqlbv22.put("riskcode", tLCPolSet.get(i).getRiskCode());
                sqlbv22.put("mContNo", mContNo);
                tLRAscriptionSet = tLRAscriptionDB.executeQuery(sqlbv22);
                if(tLRAscriptionSet.size()>0)  //孤儿单
                {
                   LRAscriptionDB mLRAscriptionDB = new LRAscriptionDB();
                   LRAscriptionSet xLRAscriptionSet = new LRAscriptionSet();

                    String lrascription_sql ="select * from lrascription where riskcode='"
                    + "?riskcode?" + "'" + " and ContNo='"
                    + "?mContNo?" + "' and ascriptiondate='"
                    + "?ascriptiondate?" + "-01'";
                    SQLwithBindVariables sqlbv23=new SQLwithBindVariables();
                    sqlbv23.sql(lrascription_sql);
                    sqlbv23.put("riskcode", tLCPolSet.get(i).getRiskCode());
                    sqlbv23.put("mContNo", mContNo);
                    sqlbv23.put("ascriptiondate", tLCPolSet.get(i).getPaytoDate().substring(0, 7));
                    xLRAscriptionSet = mLRAscriptionDB.executeQuery(sqlbv23);
                    if (xLRAscriptionSet.size() > 0)  //当期孤儿单
                    {
                      for (int j = 1; j <= xLRAscriptionSet.size(); j++)
                      {
                        LRAscriptionBSchema tLRAscriptionBSchema = new LRAscriptionBSchema();
                        LRAscriptionSchema tLRAscriptionSchema = xLRAscriptionSet
                            .get(j);

                        tReflections.transFields(tLRAscriptionBSchema,
                            tLRAscriptionSchema);
                        tLRAscriptionBSchema.setModifyDate1(tLRAscriptionBSchema
                            .getModifyDate());
                        tLRAscriptionBSchema.setModifyTime1(tLRAscriptionBSchema
                            .getModifyTime());
                        tLRAscriptionBSchema.setMakeDate1(tLRAscriptionBSchema
                            .getMakeDate());
                        tLRAscriptionBSchema.setMakeTime1(tLRAscriptionBSchema
                            .getMakeTime());
                        tLRAscriptionBSchema.setOperator1(tLRAscriptionBSchema
                            .getOperator());
                        tLRAscriptionBSchema.setMakeDate(this.CurrentDate);
                        tLRAscriptionBSchema.setModifyDate(this.CurrentDate);
                        tLRAscriptionBSchema.setModifyTime(this.CurrentTime);
                        tLRAscriptionBSchema.setMakeTime(this.CurrentTime);
                        tLRAscriptionBSchema.setEdorNO(PubFun1.CreateMaxNo(
                            "EdorNo", 20));
                        tLRAscriptionBSchema.setOperator("auto_mo");
                        tLRAscriptionBSchema.setEdorType("06"); //保单迁移
                        this.mLRAscriptionBSet.add(tLRAscriptionBSchema);

                        tLRAscriptionSchema.setAgentOld(tLRAscriptionSchema
                            .getAgentNew());
                        tLRAscriptionSchema.setAgentNew(tLAAgentSchema
                            .getAgentCode()); //修改为特殊组
                        tLRAscriptionSchema.setManageCom(this.mManageCom); //修改为新的管理机构
                        tLRAscriptionSchema.setOperator("auto_mo");
                        tLRAscriptionSchema.setModifyDate(this.CurrentDate);
                        tLRAscriptionSchema.setModifyTime(this.CurrentTime);
                        tLRAscriptionSchema.setAscriptType("03"); //03 - 保单迁移归属
                        this.mLRAscriptionSet.add(tLRAscriptionSchema);
                      }
                    }
                    else  //往期孤儿单
                    {
                      //add by wk 2006-11-3 判断是否有本期以前的归属记录，按照保单再次归属备份 B表edortype为02

                      for (int k = 1; k <= tLRAscriptionSet.size(); k++)
                      {
                        LRAscriptionBSchema tLRAscriptionBSchema = new LRAscriptionBSchema();
                        LRAscriptionSchema tLRAscriptionSchema = tLRAscriptionSet
                            .get(k);

                        tReflections.transFields(tLRAscriptionBSchema,
                            tLRAscriptionSchema);
                        tLRAscriptionBSchema.setModifyDate1(tLRAscriptionBSchema
                            .getModifyDate());
                        tLRAscriptionBSchema.setModifyTime1(tLRAscriptionBSchema
                            .getModifyTime());
                        tLRAscriptionBSchema.setMakeDate1(tLRAscriptionBSchema
                            .getMakeDate());
                        tLRAscriptionBSchema.setMakeTime1(tLRAscriptionBSchema
                            .getMakeTime());
                        tLRAscriptionBSchema.setOperator1(tLRAscriptionBSchema
                            .getOperator());
                        tLRAscriptionBSchema.setMakeDate(this.CurrentDate);
                        tLRAscriptionBSchema.setModifyDate(this.CurrentDate);
                        tLRAscriptionBSchema.setModifyTime(this.CurrentTime);
                        tLRAscriptionBSchema.setMakeTime(this.CurrentTime);
                        tLRAscriptionBSchema.setEdorNO(PubFun1.CreateMaxNo(
                            "EdorNo", 20));
                        tLRAscriptionBSchema.setOperator("auto_mo");
                        tLRAscriptionBSchema.setEdorType("02"); //保单迁移
                        this.mLRAscriptionBSet.add(tLRAscriptionBSchema);
                      }

                      if (((tLCPolSet.get(i).getPayIntv() == 12)
                           || (tLCPolSet.get(i).getRnewFlag() == -1)))
                      {
                        LRAscriptionSchema tLRAscriptionSchema = new LRAscriptionSchema();
                        tLRAscriptionSchema.setAClass("02");
                        tLRAscriptionSchema.setAgentNew(tLAAgentSchema
                            .getAgentCode());  //修改为特殊组
                        tLRAscriptionSchema.setAgentOld(tLCPolSet.get(i)
                            .getAgentCode());
                        tLRAscriptionSchema.setAscriptionDate(tLCPolSet.get(i)
                            .getPaytoDate()
                            .substring(0,
                            7) + "-01");
                        tLRAscriptionSchema.setAscriptType("03"); //03 - 保单迁移归属
                        tLRAscriptionSchema.setCValiDate(tLCPolSet.get(i)
                            .getCValiDate());
                        tLRAscriptionSchema.setMainPolNo(tLCPolSet.get(i)
                            .getMainPolNo());
                        tLRAscriptionSchema.setMakeDate(this.CurrentDate);
                        tLRAscriptionSchema.setMakeTime(this.CurrentTime);
                        tLRAscriptionSchema.setModifyDate(this.CurrentDate);
                        tLRAscriptionSchema.setModifyTime(this.CurrentTime);
                        tLRAscriptionSchema.setManageCom(this.mManageCom);
                        if (tLCPolSet.get(i).getRnewFlag() == -1)
                        {
                          //                            //续保的保单
                          //                            String OldPolnoSql =
                          //                                "select polno from LCRnewStateLog where ProposalNo ='"
                          //                                + tLCPolSet.get(i).getPolNo() + "'";
                          //                            ExeSQL tExeSQL = new ExeSQL();
                          //                            logger.debug("+OldPolnoSql+" + OldPolnoSql);
                          //                            tLRAscriptionSchema.setOldPolno(tExeSQL.getOneValue(
                          //                                    OldPolnoSql));
                          tLRAscriptionSchema.setPayCount("1");
                        }
                        else
                        {
                          //如果是续期的长险，老保单号为其保单号
                          //                            tLRAscriptionSchema.setOldPolno(tLCPolSet.get(i)
                          //                                                                     .getPolNo());
                          //注意实收表中最大次数加一
                          String paycount_sql =
                              "select max(paycount)+1 from ljapayperson where (char_length(trim(dutycode))<>10 or substr(dutycode,7,1)<>'1') and payType = 'ZC'"
                              + " and polno='" + "?polno?"
                              + "' and curpaytodate='"
                              + "?curpaytodate?" + "'";
                          ExeSQL tExeSQL = new ExeSQL();
                          SQLwithBindVariables sqlbv24=new SQLwithBindVariables();
                          sqlbv24.sql(lrascription_sql);
                          sqlbv24.put("polno", tLCPolSet.get(i).getPolNo());
                          sqlbv24.put("curpaytodate", tLCPolSet.get(i).getPaytoDate());
                          tLRAscriptionSchema.setPayCount(tExeSQL.getOneValue(
                        		  sqlbv24));
                        }

                        //对于老保单号由于保单表中此时必定只存在老保单不存在投保单所以直接取保单表中保单号
                        tLRAscriptionSchema.setOldPolno(tLCPolSet.get(i)
                            .getPolNo());
                        tLRAscriptionSchema.setOperator("auto_mo");
                        tLRAscriptionSchema.setPayToDate(tLCPolSet.get(i)
                            .getPaytoDate());
                        tLRAscriptionSchema.setPolNo(tLCPolSet.get(i).getPolNo());
                        tLRAscriptionSchema.setPrtNo(tLCPolSet.get(i).getPrtNo());
                        tLRAscriptionSchema.setRiskCode(tLCPolSet.get(i)
                            .getRiskCode());
                        tLRAscriptionSchema.setValidFlag(getLongShort(
                            tLCPolSet.get(i).getRiskCode()));
                        //添加合同号
                        tLRAscriptionSchema.setContNo(tLCPolSet.get(i).getContNo());
                        //添加保单年度
                        String bdnd_sql="";
                        bdnd_sql=" select substr('"+"?tLCPol?"+"',1,4)-substr('"+"?str?"+"',1,4) from dual ";
                        SQLwithBindVariables sqlbv25=new SQLwithBindVariables();
                        sqlbv25.sql(bdnd_sql);
                        sqlbv25.put("tLCPol", this.CurrentDate);
                        sqlbv25.put("str", tLCPolSet.get(i)
                                .getCValiDate());
                        ExeSQL gExeSQL= new ExeSQL();
                        tLRAscriptionSchema.setPayYear(gExeSQL.getOneValue(sqlbv25));
                        logger.debug("tLRAscriptionSchema:"+tLRAscriptionSchema.encode());
                        this.mLRAscriptionSet.add(tLRAscriptionSchema);
                      }
                    }
                }   //孤儿单处理完毕
                else  //处理在职单
                {

                    String lradimascription_sql ="select * from lradimascription where riskcode='"
                        + "?riskcode?" + "'" + " and ContNo='"
                        + "?mContNo?" + "' and ascriptiondate='"
                        + "?datee?" + "-01'";
                    SQLwithBindVariables sqlbv26=new SQLwithBindVariables();
                    sqlbv26.sql(lradimascription_sql);
                    sqlbv26.put("riskcode",tLCPolSet.get(i).getRiskCode());
                    sqlbv26.put("mContNo", mContNo);
                    sqlbv26.put("datee",tLCPolSet.get(i).getPaytoDate().substring(0, 7));
                    LRAdimAscriptionDB mLRAdimAscriptionDB = new LRAdimAscriptionDB();
                    LRAdimAscriptionSet xLRAdimAscriptionSet = new LRAdimAscriptionSet();
                    xLRAdimAscriptionSet = mLRAdimAscriptionDB.executeQuery(sqlbv26);
                    if(xLRAdimAscriptionSet.size()>0)  //当期在职单
                    {
                       for(int g=1;g<=xLRAdimAscriptionSet.size();g++)
                       {
                           LRAdimAscriptionSchema tLRAdimAscriptionSchema = new LRAdimAscriptionSchema();
                           LRAdimAscriptionBSchema tLRAdimAscriptionBSchema = new LRAdimAscriptionBSchema();
                           tLRAdimAscriptionSchema=xLRAdimAscriptionSet.get(g);

                           tReflections.transFields(tLRAdimAscriptionBSchema,
                               tLRAdimAscriptionSchema);
                           tLRAdimAscriptionBSchema.setEdorNo(PubFun1.CreateMaxNo(
                               "EdorNo", 20));
                           tLRAdimAscriptionBSchema.setMakeDate1(tLRAdimAscriptionBSchema
                               .getMakeDate());
                           tLRAdimAscriptionBSchema.setMakeTime1(tLRAdimAscriptionBSchema
                               .getMakeTime());
                           tLRAdimAscriptionBSchema.setModifyDate1(tLRAdimAscriptionBSchema
                               .getModifyDate());
                           tLRAdimAscriptionBSchema.setModifyTime1(tLRAdimAscriptionBSchema
                               .getModifyTime());
                           tLRAdimAscriptionBSchema.setOperator1(tLRAdimAscriptionBSchema
                               .getOperator());
                           tLRAdimAscriptionBSchema.setMakeDate(this.CurrentDate);
                           tLRAdimAscriptionBSchema.setMakeTime(this.CurrentTime);
                           tLRAdimAscriptionBSchema.setModifyDate(this.CurrentDate);
                           tLRAdimAscriptionBSchema.setModifyTime(this.CurrentTime);

                           tLRAdimAscriptionBSchema.setOperator("auto_mo");
                           tLRAdimAscriptionBSchema.setEdorType("06"); //保单迁移

                           this.mLRAdimAscriptionBSet.add(tLRAdimAscriptionBSchema);

                           tLRAdimAscriptionSchema.setAgentCode(tLAAgentSchema
                               .getAgentCode()); //修改为特殊组

                           tLRAdimAscriptionSchema.setOperator("auto_mo");
                           tLRAdimAscriptionSchema.setModifyDate(this.CurrentDate);
                           tLRAdimAscriptionSchema.setModifyTime(this.CurrentTime);
                           tLRAdimAscriptionSchema.setAscriptionType("5"); //03 - 保单迁移归属
                           this.mLRAdimAscriptionSet.add(tLRAdimAscriptionSchema);
                       }

                    }//当期在职单处理完毕
                    else  //处理非当期在职单
                    {
                        String inlradimascription_sql ="select * from lradimascription where riskcode='"
                            + "?code?" + "'" + " and ContNo='"
                            + "?mContNo?" + "' ";
                        SQLwithBindVariables sqlbv27=new SQLwithBindVariables();
                        sqlbv27.sql(inlradimascription_sql);
                        sqlbv27.put("code",tLCPolSet.get(i).getRiskCode());
                        sqlbv27.put("mContNo", mContNo);
                        tLRAdimAscriptionSet = tLRAdimAscriptionDB.executeQuery(sqlbv27);
                        for(int w=1;w<=tLRAdimAscriptionSet.size();w++)
                        {
                            LRAdimAscriptionSchema tLRAdimAscriptionSchema = new LRAdimAscriptionSchema();
                            LRAdimAscriptionBSchema tLRAdimAscriptionBSchema = new LRAdimAscriptionBSchema();
                            tLRAdimAscriptionSchema=tLRAdimAscriptionSet.get(w);

                            tReflections.transFields(tLRAdimAscriptionBSchema,
                                tLRAdimAscriptionSchema);
                            tLRAdimAscriptionBSchema.setEdorNo(PubFun1.CreateMaxNo(
                                "EdorNo", 20));
                            tLRAdimAscriptionBSchema.setMakeDate1(tLRAdimAscriptionBSchema
                                .getMakeDate());
                            tLRAdimAscriptionBSchema.setMakeTime1(tLRAdimAscriptionBSchema
                                .getMakeTime());
                            tLRAdimAscriptionBSchema.setModifyDate1(tLRAdimAscriptionBSchema
                                .getModifyDate());
                            tLRAdimAscriptionBSchema.setModifyTime1(tLRAdimAscriptionBSchema
                                .getModifyTime());
                            tLRAdimAscriptionBSchema.setOperator1(tLRAdimAscriptionBSchema
                                .getOperator());
                            tLRAdimAscriptionBSchema.setMakeDate(this.CurrentDate);
                            tLRAdimAscriptionBSchema.setMakeTime(this.CurrentTime);
                            tLRAdimAscriptionBSchema.setModifyDate(this.CurrentDate);
                            tLRAdimAscriptionBSchema.setModifyTime(this.CurrentTime);

                            tLRAdimAscriptionBSchema.setOperator("auto_mo");
                            tLRAdimAscriptionBSchema.setEdorType("06"); //保单迁移

                            this.mLRAdimAscriptionBSet.add(tLRAdimAscriptionBSchema);
                        }
                        if (((tLCPolSet.get(i).getPayIntv() == 12)
                           || (tLCPolSet.get(i).getRnewFlag() == -1)))
                         {
                            LRAdimAscriptionSchema wLRAdimAscriptionSchema = new LRAdimAscriptionSchema();
                            wLRAdimAscriptionSchema.setPolNo(tLCPolSet.get(i).getPolNo());
                            wLRAdimAscriptionSchema.setMainPolno(tLCPolSet.get(i).getMainPolNo());
                            wLRAdimAscriptionSchema.setPrtNo(tLCPolSet.get(i).getPrtNo());
                            wLRAdimAscriptionSchema.setRiskCode(tLCPolSet.get(i).getRiskCode());
                            //增加合同号
                            wLRAdimAscriptionSchema.setContNo(tLCPolSet.get(i).getContNo());
                            
                            wLRAdimAscriptionSchema.setAgentCode(tLAAgentSchema.getAgentCode());   //修改为特殊组
                            wLRAdimAscriptionSchema.setPayToDate(tLCPolSet.get(i).getPaytoDate());
                            wLRAdimAscriptionSchema.setCValiDate(tLCPolSet.get(i).getCValiDate());
                            wLRAdimAscriptionSchema.setAscriptionDate(tLCPolSet.get(i)
                                      .getPaytoDate().substring(0,7) + "-01");
                            wLRAdimAscriptionSchema.setAscriptionType("5"); //保单迁移归属
                            wLRAdimAscriptionSchema.setFlag("1"); //暂时先置为1
                            wLRAdimAscriptionSchema.setOperator("auto_mo");
                            wLRAdimAscriptionSchema.setMakeDate(this.CurrentDate);
                            wLRAdimAscriptionSchema.setMakeTime(this.CurrentTime);
                            wLRAdimAscriptionSchema.setModifyDate(this.CurrentDate);
                            wLRAdimAscriptionSchema.setModifyTime(this.CurrentTime);


                            //新增字段，保单年度，长短险标志，缴费次数
                            ExeSQL xExeSQL = new ExeSQL();
                            String longshort2 = getLongShort(tLCPolSet.get(i).getRiskCode()); //长短险
                            wLRAdimAscriptionSchema.setLongShortFlag(longshort2);

                            if(longshort2.equals("1")&&(tLCPolSet.get(i).getPayIntv()!=0))  //长险且非趸交
                            {
                              String sqlPayCount =
                                  "select  paytimes+1 from lcprem where polno ='"
                                  + "?polno?" +"'"
                              ;
                              logger.debug("+sqlPayCount+:" + sqlPayCount);
                              SQLwithBindVariables sqlbv28=new SQLwithBindVariables();
                              sqlbv28.sql(sqlPayCount);
                              sqlbv28.put("polno",tLCPolSet.get(i).getPolNo());
                              wLRAdimAscriptionSchema.setPayCount(xExeSQL.getOneValue(
                            		  sqlbv28));
                            }
                            else
                            {
                              wLRAdimAscriptionSchema.setPayCount("1");
                            }
                            //添加保单年度
                            String bdnd_sql2="";
                            bdnd_sql2=" select substr('"+"?CurrentDate?"+"',1,4)-substr('"+"?tLCPolSet?"+"',1,4) from dual ";
                            xExeSQL= new ExeSQL();
                            SQLwithBindVariables sqlbv29=new SQLwithBindVariables();
                            sqlbv29.sql(bdnd_sql2);
                            sqlbv29.put("CurrentDate",this.CurrentDate);
                            sqlbv29.put("tLCPolSet",tLCPolSet.get(i)
                                    .getCValiDate());
                            wLRAdimAscriptionSchema.setPayYear(xExeSQL.getOneValue(sqlbv29));


                            mLRAdimAscriptionSet.add(wLRAdimAscriptionSchema);
                         }

                    }

                    //其他信息不变,返回的this.mLAAgentSchema为保单原来的值
                    String agent_sql2 = "select agentcode from lcpol where ContNo='"
                                      + "?ContNo?" + "'";
                    ExeSQL mExe = new ExeSQL();
                    LAAgentDB xLAAgentDB = new LAAgentDB();
                    SQLwithBindVariables sqlbv30=new SQLwithBindVariables();
                    sqlbv30.sql(agent_sql2);
                    sqlbv30.put("ContNo",this.mContNo);
                    xLAAgentDB.setAgentCode(mExe.getOneValue(sqlbv30));
                    if (!xLAAgentDB.getInfo())
                    {
                      this.buildError("submitData", xLAAgentDB.mErrors.getFirstError());
                      return false;
                    }

                    //如果不需要归属（非个险保单）直接取原来的代理人
                    this.mLAAgentSchema.setSchema(xLAAgentDB.getSchema());

                }
            }
        }
        catch (Exception e)
        {
            logger.debug("e!!!!!!!!!!!!!!!!!!!!!!" + e.toString());
            this.buildError("DealData", e.toString());
            return false;
        }

        return true;
    }


    private String getLongShort(String riskcode)
    {
        String longshort = ""; //长短险
        LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
        tLMRiskAppDB.setRiskCode(riskcode);
        if (tLMRiskAppDB.getInfo() == true)
        {
            if (tLMRiskAppDB.getRiskPeriod().equals("L"))
            {
                longshort = "1";
            }
            else if (tLMRiskAppDB.getRiskPeriod().equals("M"))
            {
                longshort = "0";
            }
            else if (tLMRiskAppDB.getRiskPeriod().equals("S"))
            {
                longshort = "0";
            }
        }
        return longshort;
    }

    private boolean upDate()
     {
         try
         {
             //            this.mConn.setAutoCommit(false);
           //孤儿单更新处理
             if (this.mLRAscriptionBSet.size() > 0)
             {
                 LRAscriptionDB tLRAscriptioDB = new LRAscriptionDB(this.mConn);
                 for (int i = 1; i <= this.mLRAscriptionBSet.size(); i++)
                 {
                     tLRAscriptioDB.setPolNo(this.mLRAscriptionBSet.get(i)
                                                                   .getPolNo());
                     if (!tLRAscriptioDB.delete())
                     {
                         buildError("upDate",
                             tLRAscriptioDB.mErrors.getFirstError());
                         return false;
                     }
                 }
             }

             LRAscriptionDBSet tLRAscriptionDBSet = new LRAscriptionDBSet(this.mConn);
             tLRAscriptionDBSet.add(this.mLRAscriptionSet);
             if (!tLRAscriptionDBSet.insert())
             {
                 buildError("upDate", tLRAscriptionDBSet.mErrors.getFirstError());
                 return false;
             }

             LRAscriptionBDBSet tLRAscriptionBDBSet = new LRAscriptionBDBSet(this.mConn);
             tLRAscriptionBDBSet.add(this.mLRAscriptionBSet);
             if (!tLRAscriptionBDBSet.insert())
             {
                 buildError("upDate", tLRAscriptionBDBSet.mErrors.getFirstError());
                 return false;
             }
             //在职单更新处理

             if (this.mLRAdimAscriptionBSet.size() > 0)
             {
                 LRAdimAscriptionDB tLRAdimAscriptioDB = new LRAdimAscriptionDB(this.mConn);
                 for (int i = 1; i <= this.mLRAdimAscriptionBSet.size(); i++)
                 {
                     tLRAdimAscriptioDB.setPolNo(this.mLRAdimAscriptionBSet.get(i)
                                                                   .getPolNo());
                     if (!tLRAdimAscriptioDB.delete())
                     {
                         buildError("upDate",
                             tLRAdimAscriptioDB.mErrors.getFirstError());
                         return false;
                     }
                 }
             }

             LRAdimAscriptionDBSet tLRAdimAscriptionDBSet = new LRAdimAscriptionDBSet(this.mConn);
             tLRAdimAscriptionDBSet.add(this.mLRAdimAscriptionSet);
             if (!tLRAdimAscriptionDBSet.insert())
             {
                 buildError("upDate", tLRAdimAscriptionDBSet.mErrors.getFirstError());
                 return false;
             }

             LRAdimAscriptionBDBSet tLRAdimAscriptionBDBSet = new LRAdimAscriptionBDBSet(this.mConn);
             tLRAdimAscriptionBDBSet.add(this.mLRAdimAscriptionBSet);
             if (!tLRAdimAscriptionBDBSet.insert())
             {
                 buildError("upDate", tLRAdimAscriptionBDBSet.mErrors.getFirstError());
                 return false;
             }

             //此处测试时使用，由保全操作统一提交
             //            mConn.commit();
             //            mConn.close();
//            mConn.rollback();
         }
         catch (Exception e)
         {
             this.buildError("upDate", e.toString());
             return false;
         }

         return true;
    }

    private void buildError(String szFunc, String szErrMsg)
    {
        CError cError = new CError();
        cError.moduleName = "ReagentBqMove";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
    }

    public static void main(String[] args)
    {
        Connection conn = DBConnPool.getConnection();
        String tManageCom = "86110010";  //目标机构
        String tfManagecom = "86110001"; //原机构
        String tContNo = "86110020090210001591";
        try
        {
            conn.setAutoCommit(false);

            ReagentBqMove reagentBqMove1 = new ReagentBqMove(conn, tContNo,
                    tManageCom, tfManagecom);

            if (!reagentBqMove1.submitData())
            {
                logger.debug("error!!");
            }
        }
        catch (Exception e)
        {
            logger.debug("error" + e.toString());
        }
    }
}
