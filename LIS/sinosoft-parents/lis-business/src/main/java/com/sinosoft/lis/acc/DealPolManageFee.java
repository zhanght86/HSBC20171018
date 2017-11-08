package com.sinosoft.lis.acc;
import org.apache.log4j.Logger;

import java.util.Date;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.finfee.FinFeePubFun;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.f1print.*;

/**
 * <p>Title: </p>
 *
 * <p>Description:投连管理费收取 </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company:sinosoft </p>
 *
 * @author:ck
 * @version 1.0
 * @Cleaned QianLy 2009-7-16
 */
public class DealPolManageFee
{
private static Logger logger = Logger.getLogger(DealPolManageFee.class);

    private GlobalInput _GlobalInput = new GlobalInput();
    public DealInsuAccLog tDealInsuAccLog = new DealInsuAccLog();
    public CErrors mErrors = new CErrors();
    /*管理费收取时间*/
    private String _DealDate = PubFun.getCurrentDate();
    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();

    /*结果集*/
    private LCInsureAccFeeSet tLCInsureAccFeeSet;
    private LCInsureAccClassFeeSet tLCInsureAccClassFeeSet;
    private LCInsureAccFeeTraceSet tLCInsureAccFeeTraceSet;
    private LCInsureAccSet tLCInsureAccSet;
    private LCInsureAccClassSet tLCInsureAccClassSet;
    private LCInsureAccTraceSet tLCInsureAccTraceSet;
//    private LOArrearageSet tLOArrearageSet = new LOArrearageSet();//add by nandd temp
//    private LBArrearageSet tLBArrearageSet = new LBArrearageSet();

    /*默认当天计算管理费*/
    public DealPolManageFee(GlobalInput aGlobalInput)
    {
        _GlobalInput = aGlobalInput;
    }

    /*aDealDate为指定的管理费收取时间*/
    public DealPolManageFee(GlobalInput aGlobalInput, String aDealDate)
    {
        _GlobalInput = aGlobalInput;
        _DealDate = aDealDate;
    }

    /*计算保单管理费*/
    public void calPolManageFee(LCPolSet aLCPolSet)
    {
        if (aLCPolSet != null && aLCPolSet.size() > 0)
        {
            DealInsuAccManageFee tDealInsuAccManageFee = new DealInsuAccManageFee(_GlobalInput, _DealDate);
            for (int i = 1; i <= aLCPolSet.size(); i++)
            {
                LCPolSchema aLCPolSchema = aLCPolSet.get(i);
                //1.1 判断有无欠款，第一次欠款日+60天后不扣风险保费，做失效处理 Add BY Gaoht
                //如果失效继续收分险管理费，但计入欠费
                if (PubInsuAccFun.CheckAccValue(aLCPolSchema, _DealDate))
                {
                    LCInsureAccSet aLCInsureAccSet = new LCInsureAccSet();
                    LCInsureAccDB aLCInsureAccDB = new LCInsureAccDB();
                    aLCInsureAccDB.setPolNo(aLCPolSchema.getPolNo());
                    aLCInsureAccSet = aLCInsureAccDB.query();
                    //PolAccBala:保单总价值
                    double PolAccBala = 0.00;
                    for (int k = 1; k <= aLCInsureAccSet.size(); k++)
                    {
                    	double preCurBala = - PubInsuAccFun.calPrice( -aLCInsureAccSet.get(k).getUnitCount(),
                                _DealDate,aLCInsureAccSet.get(k).getInsuAccNo());
                    	//币种转换
                    	LDExch ex = new LDExch();
                    	double midCurBala = ex.toBaseCur(aLCInsureAccSet.get(k).getCurrency(), SysConst.BaseCur, _DealDate, preCurBala);
                    	if(midCurBala == -1){//转换出错
                    		midCurBala = preCurBala;
                    	}
                        PolAccBala += midCurBala;
                    }

                    tDealInsuAccManageFee.calInsuAccManageFee(aLCInsureAccSet,PolAccBala);
                    
                    //add by nandd temp
//                    tLOArrearageSet = tDealInsuAccManageFee.getLOArrearageSet();
//                    tLBArrearageSet.clear();
//                    if (tLOArrearageSet.size() > 0)
//                    {
//                    	MMap mmap = new MMap();
//                    	LOArrearageDB aLOArrearageDB = new LOArrearageDB();
//                    	LBArrearageSchema aLBArrearageSchema = new LBArrearageSchema();
//                    	ExeSQL tExeSQL = new ExeSQL();
//                    	Reflections tRef = new Reflections();
//                        for (int q = 1; q <= tLOArrearageSet.size(); q++)
//                        {
//                        	String tSql ="select managecom from lcpol where polno ='"+tLOArrearageSet.get(q).getPolNo()+"'";
//                        	String tLimit=tExeSQL.getOneValue(tSql);
//                        	aLBArrearageSchema = new LBArrearageSchema();
//                        	String mSerialNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
//                        	aLOArrearageDB.setPolNo(tLOArrearageSet.get(q).getPolNo());
//                        	aLOArrearageDB.setEdorNo(tLOArrearageSet.get(q).getEdorNo());
//                        	aLOArrearageDB.setFeeCode(tLOArrearageSet.get(q).getFeeCode());
//                        	aLOArrearageDB.setPayDate(tLOArrearageSet.get(q).getPayDate());
//                        	if(!aLOArrearageDB.getInfo())
//                        	{
//                        		tRef.transFields(aLBArrearageSchema, tLOArrearageSet.get(q));
//                        		aLBArrearageSchema.setSerialNo(mSerialNo);
//                        	}
//                        	else
//                        	{
//                        		tRef.transFields(aLBArrearageSchema, aLOArrearageDB.getSchema());
//                        		aLBArrearageSchema.setSumMoney(tLOArrearageSet.get(q).getSumMoney()-aLOArrearageDB.getSchema().getSumMoney());
//                        		aLBArrearageSchema.setSerialNo(mSerialNo);
//                        	}
//                        	aLBArrearageSchema.setMakeDate(CurrentDate);
//                        	aLBArrearageSchema.setMakeTime(CurrentTime);
//                        	aLBArrearageSchema.setModifyDate(CurrentDate);
//                        	aLBArrearageSchema.setModifyTime(CurrentTime);
//                        	tLBArrearageSet.add(aLBArrearageSchema);
//                            if (Math.abs(tLOArrearageSet.get(q).getSumMoney()) <= 0.01)
//                            {
//                            	tLOArrearageSet.get(q).setPayOffFlag("1");
//                            	tLOArrearageSet.get(q).setPayOffDate(_DealDate);
//                            	tLOArrearageSet.get(q).setModifyDate(CurrentDate);
//                            	tLOArrearageSet.get(q).setModifyTime(CurrentTime);
//                            }
//                        }
//                        mmap.put(tLOArrearageSet, "DELETE&INSERT");
//                        mmap.put(tLBArrearageSet, "DELETE&INSERT");
//                        for(int q = 1; q <= tLOArrearageSet.size(); q++)
//                        {
//                        	if (Math.abs(tLOArrearageSet.get(q).getSumMoney()) <= 0.01)
//							{
//								String tSql = "update LBArrearage set payoffflag='1' ,payoffdate='" + _DealDate
//										    + "' where polno='" + tLOArrearageSet.get(q).getPolNo() + "' and feecode ='"
//										    + tLOArrearageSet.get(q).getFeeCode() + "' and edorno ='"
//										    + tLOArrearageSet.get(q).getEdorNo() + "' and insuredno='"
//										    + tLOArrearageSet.get(q).getInsuredNo() + "'";
//								mmap.put(tSql, "UPDATE");
//							}
//                        }
//                        if (!updateAccInfo(mmap))
//                        {
//                            CError.buildErr(this, "欠费信息更新出错!");
//                            tDealInsuAccLog.createLOAccLog(_DealDate,aLCPolSchema, "02","欠费信息更新出错");
//                            continue;
//                        }
//                    }
                    
                    //先看看有结算过的再预警呀
                    String haveBala = BqNameFun.getAnother(null,"lcinsureacc", "unitcount <>0 and PolNo", aLCPolSchema.getPolNo(), "distinct 'X'");
                    if(haveBala != null && "X".equals(haveBala)){
                        DealInsuAccTerNotice tDealInsuAccTerNotice = new DealInsuAccTerNotice(_GlobalInput);
                        //add by snowman  预警通知书
                        tDealInsuAccTerNotice.CheckAccValue(aLCPolSchema,_DealDate);
                    }
                    
                }
//              1.1 判断有无欠款，第一次欠款日+60天后不扣风险保费，做失效处理 Add BY Gaoht
                if (!PubInsuAccFun.CheckAccValue(aLCPolSchema, _DealDate))
                {
                    if (aLCPolSchema.getPolNo().equals(aLCPolSchema.getMainPolNo()))
                    {
                        if(!CreateLCContState(aLCPolSchema,_DealDate))
                        {
                           CError.buildErr(this, "保单状态信息生成出错!");
                           tDealInsuAccLog.createLOAccLog(_DealDate,aLCPolSchema, "02","保单状态信息生成出错");
                           continue;
                        }
                        //add by snowman 
                        
                        String haveBala = BqNameFun.getAnother(null,"lcinsureacc", "unitcount <>0 and PolNo", aLCPolSchema.getPolNo(), "distinct 'X'");
                        if(haveBala != null && "X".equals(haveBala)){
                            DealInsuAccTerNotice tDealInsuAccTerNotice = new DealInsuAccTerNotice(_GlobalInput);
                            if(!tDealInsuAccTerNotice.CheckAccDISValue(aLCPolSchema, _DealDate))
                            {
                                CError.buildErr(this, "插入停效通知书失败");
                                tDealInsuAccLog.createLOAccLog(_DealDate,aLCPolSchema, "02","插入停效通知书失败");
                                continue;
                            }
                        }
                    }
                }
            }
        }
    }

    /*更新保单状态*/
    public boolean UpdateStateInfo(LCContStateSet cLCContStateSet)
    {
        VData tVData = new VData();
        MMap tMap = new MMap();
        tMap.put(cLCContStateSet, "DELETE&INSERT");
        SQLwithBindVariables sqlbv = new SQLwithBindVariables();
        String strcontno = cLCContStateSet.get(1).getContNo();
        sqlbv.sql("update lccont set state='205' where contno='"+"?strcontno?"+"'");
        sqlbv.put("strcontno", strcontno);
        tMap.put(sqlbv, "UPDATE");
        if (tMap != null && tMap.keySet().size() > 0)
            tVData.add(tMap);
        PubSubmit tPubSubmit = new PubSubmit();
        if (!tPubSubmit.submitData(tVData, ""))
        {
            return false;
        }
        return true;
    }

    /*更新账户信息*/
    public boolean updateAccInfo(MMap aMap)
    {
        VData tVData = new VData();
        //准备公共提交数据

        if (aMap != null && aMap.keySet().size() > 0){
            tVData.add(aMap);
        }
        PubSubmit tPubSubmit = new PubSubmit();
        if (!tPubSubmit.submitData(tVData, ""))
        {
            return false;
        }
        return true;
    }

    /*************
     * gaoht Add
     * 生成失效保单状态
     * 2007-12-21
     * ******/
    public boolean CreateLCContState(LCPolSchema cLCPolSchema,String cDealDate)
    {
        LCContStateSet tLCContStateSet = new LCContStateSet();
        String tSql = "select * from lccontstate where polno='?polno?' and statetype in ('Available','Terminate') and state='1' and enddate is null ";
        SQLwithBindVariables tsqlbv1 = new SQLwithBindVariables();
        tsqlbv1.sql(tSql);
        tsqlbv1.put("polno",  cLCPolSchema.getPolNo());
        LCContStateDB tLCContStateDB = new LCContStateDB();
        tLCContStateSet = tLCContStateDB.executeQuery(tsqlbv1);
        Date tStartDate = new Date();
        if (tLCContStateSet.size() == 0)
        {
            /*-------------------add by snowman------------------------------*/
        	SQLwithBindVariables tsqlbv = new SQLwithBindVariables();
        	String tSql0 = "select * from LOArrearage where polno='" +
                "?polno?" + "' and Payoffflag='0' and paydate = (select min(paydate) from LOArrearage where polno='" +
                "?polno?" + "' and Payoffflag='0')";
        	tsqlbv.sql(tSql0);
        	tsqlbv.put("polno", cLCPolSchema.getPolNo());
//            LOArrearageDB tLOArrearageDB = new LOArrearageDB();
//            LOArrearageSet tLOArrearageSet = new LOArrearageSet();
//            tLOArrearageSet = tLOArrearageDB.executeQuery(tsqlbv);
//            String tFirstPayDate = tLOArrearageSet.get(1).getPayDate();
            String tFirstPayDate = "";
            //查询系统帐户宽限期 ， sysvartype='1' 表示开关 1--开 0--关
            SQLwithBindVariables tsqlbv2 = new SQLwithBindVariables();
            tSql0 = "select (case when sysvarvalue is not null then sysvarvalue  else '0' end) from ldsysvar where sysvar='Acc_Avail_Period' and sysvartype='1'";
            tsqlbv2.sql(tSql0);
            ExeSQL tExeSQL = new ExeSQL();
            int tAAP = 0;
            String tAAP_SYS = tExeSQL.getOneValue(tsqlbv2);
            if (tAAP_SYS == null || tAAP_SYS.equals("")) {
	            tAAP_SYS = "60";
            }
            tAAP = Integer.parseInt(tAAP_SYS);
            Date tEndDate = new Date();
            FDate tD = new FDate();
            //得到宽限期最后一天
            tEndDate = FinFeePubFun.calOFDate(tD.getDate(tFirstPayDate), tAAP, "D",tD.getDate(tFirstPayDate));
            String EndDate = tD.getString(tEndDate);

            //计价日如大于宽限期最后一天，则返回false;
            FDate tFDate = new FDate();
            if (tFDate.getDate(cDealDate).after(tFDate.getDate(EndDate)))
            {
            	tStartDate = FinFeePubFun.calOFDate(tEndDate, 1, "D",tStartDate);
            }
            else
            {
            	return false;
            }
            /*---------------------------------------------------------------*/
            SQLwithBindVariables tqsqlbv = new SQLwithBindVariables();
            String tQueryOldState = "select * from lccontstate where polno='" +
                    "?polno?" +"' and statetype='Available' and state='0' and enddate is null";
            tqsqlbv.sql(tQueryOldState);
            tqsqlbv.put("polno", cLCPolSchema.getPolNo());
            tLCContStateDB = new LCContStateDB();
            LCContStateSet OldLCContStateSet = new LCContStateSet();
            OldLCContStateSet = tLCContStateDB.executeQuery(tqsqlbv);
            for (int i = 1; i <= OldLCContStateSet.size(); i++)
            {
                LCContStateSchema tLCContStateSchema = new LCContStateSchema();
                tLCContStateSchema = OldLCContStateSet.get(i).getSchema();
                tLCContStateSchema.setEndDate(tEndDate);
                tLCContStateSchema.setStateReason("205");
                tLCContStateSchema.setModifyDate(CurrentDate);
                tLCContStateSchema.setModifyTime(CurrentTime);
                tLCContStateSet.add(tLCContStateSchema);
            }
            LCContStateSchema tLCContStateSchema = new LCContStateSchema();
            tLCContStateSchema.setContNo(cLCPolSchema.getContNo());
            tLCContStateSchema.setInsuredNo("000000");
            tLCContStateSchema.setPolNo(cLCPolSchema.getPolNo());
            tLCContStateSchema.setStateType("Available");
            tLCContStateSchema.setStateReason("205");
            tLCContStateSchema.setState("1");
            tLCContStateSchema.setOperator(_GlobalInput.Operator);
            tLCContStateSchema.setStartDate(tStartDate);
            tLCContStateSchema.setMakeDate(CurrentDate);
            tLCContStateSchema.setMakeTime(CurrentTime);
            tLCContStateSchema.setModifyDate(CurrentDate);
            tLCContStateSchema.setModifyTime(CurrentTime);
            tLCContStateSet.add(tLCContStateSchema);
            /*-------------------------------------------------------------*/
            VData tVData = new VData();
            MMap tMap = new MMap();
            tMap.put(tLCContStateSet, "DELETE&INSERT");
            SQLwithBindVariables ttsqlbv = new SQLwithBindVariables();
            String strcontno = tLCContStateSet.get(1).getContNo();
            ttsqlbv.sql("update lccont set state='205' where contno='"+"?strcontno?"+"'");
            ttsqlbv.put("strcontno", strcontno);
            tMap.put(ttsqlbv, "UPDATE");
            if (tMap != null && tMap.keySet().size() > 0){
                tVData.add(tMap);
            }
            PubSubmit tPubSubmit = new PubSubmit();
            if (!tPubSubmit.submitData(tVData, ""))
            {
                return false;
            }
        }
        else
        {
            tLCContStateSet = new LCContStateSet();
        }

        return true;
    }

    /*生成账户轨迹表结构*/
    public LCInsureAccTraceSchema createInsuAccTrace(LCInsureAccClassSchema
            tLCInsureAccClassSchema,
            String tFeeCode, String tState,
            double tPrice, double tUnit)
    {
        LCInsureAccTraceSchema mLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
        Reflections tReflections = new Reflections();
        tReflections.transFields(mLCInsureAccTraceSchema, tLCInsureAccClassSchema);
        String tLimit = PubFun.getNoLimit(mLCInsureAccTraceSchema.getManageCom());
        String SerialNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);

        mLCInsureAccTraceSchema.setSerialNo(SerialNo);
        mLCInsureAccTraceSchema.setMoneyType("GL");
        mLCInsureAccTraceSchema.setPayDate(_DealDate);
        mLCInsureAccTraceSchema.setShouldValueDate(_DealDate);
        mLCInsureAccTraceSchema.setValueDate(_DealDate);
        mLCInsureAccTraceSchema.setState(tState);
        mLCInsureAccTraceSchema.setMoney(tPrice);
        mLCInsureAccTraceSchema.setUnitCount(tUnit);
        mLCInsureAccTraceSchema.setOperator(_GlobalInput.Operator);
        mLCInsureAccTraceSchema.setMakeDate(CurrentDate);
        mLCInsureAccTraceSchema.setMakeTime(CurrentTime);
        mLCInsureAccTraceSchema.setModifyDate(CurrentDate);
        mLCInsureAccTraceSchema.setModifyTime(CurrentTime);
        mLCInsureAccTraceSchema.setFeeCode(tFeeCode);
        return mLCInsureAccTraceSchema;
    }

    /*生成管理费轨迹表结构*/
    public LCInsureAccFeeTraceSchema createInsuAccFeeTrace(
            LCInsureAccClassSchema
            tLCInsureAccClassSchema,
            String tFeeCode, String tState,
            double tPrice, double tUnit)
    {
        LCInsureAccFeeTraceSchema mLCInsureAccFeeTraceSchema = new
                LCInsureAccFeeTraceSchema();
        Reflections tReflections = new Reflections();
        tReflections.transFields(mLCInsureAccFeeTraceSchema, tLCInsureAccClassSchema);
        String tLimit = PubFun.getNoLimit(mLCInsureAccFeeTraceSchema.getManageCom());
        String SerialNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
        mLCInsureAccFeeTraceSchema.setSerialNo(SerialNo);
        mLCInsureAccFeeTraceSchema.setMoneyType("GL");
        mLCInsureAccFeeTraceSchema.setPayDate(_DealDate);
        mLCInsureAccFeeTraceSchema.setState(tState);
        mLCInsureAccFeeTraceSchema.setFee(tPrice);
        mLCInsureAccFeeTraceSchema.setFeeUnit(tUnit);
        mLCInsureAccFeeTraceSchema.setOperator(_GlobalInput.Operator);
        mLCInsureAccFeeTraceSchema.setMakeDate(CurrentDate);
        mLCInsureAccFeeTraceSchema.setMakeTime(CurrentTime);
        mLCInsureAccFeeTraceSchema.setModifyDate(CurrentDate);
        mLCInsureAccFeeTraceSchema.setModifyTime(CurrentTime);
        mLCInsureAccFeeTraceSchema.setFeeCode(tFeeCode);
        return mLCInsureAccFeeTraceSchema;
    }
    /*根据LCInsureAccTrace生成管理费轨迹*/
    public LCInsureAccFeeTraceSchema createInsuAccFeeTrace(
            LCInsureAccTraceSchema
            tLCInsureAccTraceSchema)
    {
        LCInsureAccFeeTraceSchema mLCInsureAccFeeTraceSchema = new LCInsureAccFeeTraceSchema();
        Reflections tReflections = new Reflections();
        tReflections.transFields(mLCInsureAccFeeTraceSchema, tLCInsureAccTraceSchema);
        mLCInsureAccFeeTraceSchema.setMoneyType("GL");
        mLCInsureAccFeeTraceSchema.setFee( -tLCInsureAccTraceSchema.getMoney());
        mLCInsureAccFeeTraceSchema.setFeeUnit( -tLCInsureAccTraceSchema.getUnitCount());
        return mLCInsureAccFeeTraceSchema;
    }

    public static void main(String arg[])
    {
        GlobalInput tGlobalInput = new GlobalInput();
        tGlobalInput.Operator = "001";
        tGlobalInput.ManageCom = "86";
        DealPolManageFee tDealPolManageFee = new DealPolManageFee(
                tGlobalInput, "2007-06-14");
        tDealPolManageFee.calPolManageFee(PubInsuAccFun.queryPolManageSet());
    }

    /*准备管理费信息*/
    public LCInsureAccFeeSet getLCInsureAccFeeSet()
    {
        return tLCInsureAccFeeSet;
    }

    public LCInsureAccClassFeeSet getLCInsureAccClassFeeSet()
    {
        return tLCInsureAccClassFeeSet;
    }

    public LCInsureAccFeeTraceSet getLCInsureAccFeeTraceSet()
    {
        return tLCInsureAccFeeTraceSet;
    }

    public LCInsureAccSet getLCInsureAccSet()
    {
        return tLCInsureAccSet;
    }

    public LCInsureAccClassSet getLCInsureAccClassSet()
    {
        return tLCInsureAccClassSet;
    }

    public LCInsureAccTraceSet getLCInsureAccTraceSet()
    {
        return tLCInsureAccTraceSet;
    }

}
