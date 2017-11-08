package com.sinosoft.lis.acc;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

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
public class DealInsuAccManageFee
{
private static Logger logger = Logger.getLogger(DealInsuAccManageFee.class);

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
    
    private LCInsureAccFeeSet mLCInsureAccFeeSet = new LCInsureAccFeeSet();
    private LCInsureAccClassFeeSet mLCInsureAccClassFeeSet = new LCInsureAccClassFeeSet();
    private LCInsureAccFeeTraceSet mLCInsureAccFeeTraceSet = new LCInsureAccFeeTraceSet();
    private LCInsureAccSet mLCInsureAccSet = new LCInsureAccSet();
    private LCInsureAccClassSet mLCInsureAccClassSet = new LCInsureAccClassSet();
    private LCInsureAccTraceSet mLCInsureAccTraceSet = new LCInsureAccTraceSet();

    /*默认当天计算管理费*/
    public DealInsuAccManageFee(GlobalInput aGlobalInput)
    {
        _GlobalInput = aGlobalInput;
    }

    /*aDealDate为指定的管理费收取时间*/
    public DealInsuAccManageFee(GlobalInput aGlobalInput, String aDealDate)
    {
        _GlobalInput = aGlobalInput;
        _DealDate = aDealDate;
    }

    /*计算账户管理费*/
    public void calInsuAccManageFee(LCInsureAccSet aLCInsureAccSet, double PolAccBala)
    {
        if(aLCInsureAccSet != null && aLCInsureAccSet.size() != 0)
        {
            //1.欠费查询
        	//add by nandd temp
        	String strpolno = aLCInsureAccSet.get(1).getPolNo();
        	String strsql = "select * from loarrearage where edorno='00000000000000000000' and polno='" + "?strpolno?" 
                    + "' and payoffflag='0' and summoney<>0 and feecode<>'000000' order by paydate,feecode";
        	SQLwithBindVariables sqlbv = new SQLwithBindVariables();
        	sqlbv.sql(strsql);
        	sqlbv.put("strpolno", strpolno);
//            LOArrearageDB tempLOArrearageDB = new LOArrearageDB();
//            tLOArrearageSet = tempLOArrearageDB.executeQuery(sqlbv);
//            LOArrearageSet mLOArrearageSet = new LOArrearageSet();
            String dealLOArrearageFlag = "0";
//            if(tLOArrearageSet.size() > 0)
//            {
//                dealLOArrearageFlag = "1";
//                mLOArrearageSet = tempLOArrearageDB.executeQuery(sqlbv);
//            }

            //循环账户表
            for(int i = 1; i <= aLCInsureAccSet.size(); i++)
            {
                tLCInsureAccSet = new LCInsureAccSet();
                tLCInsureAccClassSet = new LCInsureAccClassSet();
                tLCInsureAccTraceSet = new LCInsureAccTraceSet();
                tLCInsureAccFeeSet = new LCInsureAccFeeSet();
                tLCInsureAccClassFeeSet = new LCInsureAccClassFeeSet();
                tLCInsureAccFeeTraceSet = new LCInsureAccFeeTraceSet();

                double feeValue = 0.00;
                double feeUnit = 0.00;
                LCInsureAccSchema aLCInsureAccSchema = aLCInsureAccSet.get(i);

                //如果该账户在管理费计算日没有计价信息，则返回
                LOAccUnitPriceSet aLOAccUnitPriceSet = new LOAccUnitPriceSet();
                LOAccUnitPriceSchema aLOAccUnitPriceSchema = new LOAccUnitPriceDB();
                String state = "1"; //1-计价日管理费收取；0-非计价日管理费收取
                LOAccUnitPriceDB aLOAccUnitPriceDB = new LOAccUnitPriceDB();
                aLOAccUnitPriceDB.setInsuAccNo(aLCInsureAccSchema.getInsuAccNo());
//                aLOAccUnitPriceDB.setRiskCode(aLCInsureAccSchema.getRiskCode());
                aLOAccUnitPriceDB.setStartDate(_DealDate);
                aLOAccUnitPriceDB.setState("0");
                String gSql = "select * from LOAccUnitPrice where InsuAccNo='" +"?InsuAccNo?"
                            + "' and StartDate='" + "?_DealDate?" + "' and state='0'";
                SQLwithBindVariables tsqlbv1 = new SQLwithBindVariables();
                tsqlbv1.sql(gSql);
                tsqlbv1.put("InsuAccNo", aLCInsureAccSchema.getInsuAccNo());
                tsqlbv1.put("_DealDate", _DealDate);

                aLOAccUnitPriceSet = aLOAccUnitPriceDB.executeQuery(tsqlbv1);
                if(aLOAccUnitPriceSet.size() > 0)
                {
                    aLOAccUnitPriceSchema = aLOAccUnitPriceSet.get(1);
                    state = "1";
                }
                else
                {
                    //非计价日不进行管理费处理
                    continue;
                }

                LCInsureAccClassSet aLCInsureAccClassSet = new LCInsureAccClassSet();
                LCInsureAccClassDB aLCInsureAccClassDB = new LCInsureAccClassDB();
                aLCInsureAccClassDB.setPolNo(aLCInsureAccSchema.getPolNo());
                aLCInsureAccClassDB.setInsuAccNo(aLCInsureAccSchema.getInsuAccNo());
                aLCInsureAccClassSet = aLCInsureAccClassDB.query();
                //循环账户分类表
                for(int j = 1; j <= aLCInsureAccClassSet.size(); j++)
                {
                    LCInsureAccClassSchema aLCInsureAccClassSchema = aLCInsureAccClassSet.get(j);
                    double oldClassUnit = aLCInsureAccClassSchema.getUnitCount();
                    double feeClassValue = 0.00;
                    double feeClassUnit = 0.00;
                    //判断该账户是属于个险还是团险
                    if(aLCInsureAccClassSchema.getGrpPolNo() != null && aLCInsureAccClassSchema.getGrpPolNo().equals("00000000000000000000"))
                    {
                    	//add by nandd temp
//                        if(oldClassUnit != 0 && dealLOArrearageFlag.equals("1"))
//                        {
//                        	//1，欠费的处理
//                            double polArrearageValue = 0.00; //欠费金额   
//                            double AccRate = 0.00;
//                            //账户价值占比
//                            if(PolAccBala != 0)
//                            {
//		                    	double preCurBala = PubInsuAccFun.calPrice( -oldClassUnit,_DealDate,aLCInsureAccClassSchema.getInsuAccNo(),aLCInsureAccClassSchema.getRiskCode());
//		                    	//币种转换
//		                    	LDExch ex = new LDExch();
//		                    	double midCurBala = ex.toBaseCur(aLCInsureAccSet.get(k).getCurrency(), SysConst.BaseCur, _DealDate, preCurBala);
//		                    	if(midCurBala == -1){//转换出错
//		                    		midCurBala = preCurBala;
//		                    	}
//                                AccRate = -PubInsuAccFun.calPrice( -oldClassUnit,_DealDate,aLCInsureAccClassSchema.getInsuAccNo(),aLCInsureAccClassSchema.getRiskCode())/ PolAccBala;
//                                AccRate = -midCurBala/ PolAccBala;
//                            }
//                            else
//                            {
//                                ExeSQL tExeSQL = new ExeSQL();
//                                AccRate = Double.parseDouble(tExeSQL.getOneValue("select 1/count(*) from lcinsureaccclass where polno='" +aLCInsureAccClassSchema.getPolNo() +"'"));
//                            }
//
//                            for(int k = 1; k <= tLOArrearageSet.size(); k++)
//                            {
//                                if(aLCInsureAccClassSchema.getUnitCount() == 0)
//                                {
//                                    continue;
//                                }
//                                LOArrearageSchema tLOArrearageSchema = tLOArrearageSet.get(k);
//                                for(int m = 1; m <= mLOArrearageSet.size(); m++)
//                                {
//                                    if(tLOArrearageSet.get(k).getEdorNo().equals(mLOArrearageSet.get(m).getEdorNo()) &&
//                                       tLOArrearageSet.get(k).getPolNo().equals(mLOArrearageSet.get(m).getPolNo()) &&
//                                       tLOArrearageSet.get(k).getFeeCode().equals(mLOArrearageSet.get(m).getFeeCode()) &&
//                                       tLOArrearageSet.get(k).getPayDate().equals(mLOArrearageSet.get(m).getPayDate()))
//                                    {
//                                        polArrearageValue = -mLOArrearageSet.get(m).getSumMoney();
//                                    }
//                                }
//
//                                double traceArrearageValue = polArrearageValue * AccRate;
//                                //计算投资单位,用卖出价
//                                double traceArrearageUnit = -PubInsuAccFun.calUnit( -traceArrearageValue,_DealDate,aLOAccUnitPriceSchema.getInsuAccNo(),aLOAccUnitPriceSchema.getRiskCode());
//
//                                if(traceArrearageUnit > aLCInsureAccClassSchema.getUnitCount())
//                                {
//                                    traceArrearageUnit =aLCInsureAccClassSchema.getUnitCount();
//                                    traceArrearageValue = -PubInsuAccFun.calPrice(-traceArrearageUnit,_DealDate, aLOAccUnitPriceSchema.getInsuAccNo(),aLOAccUnitPriceSchema.getRiskCode());
//                                }
//
//                                //生成Trace记录
//                                LCInsureAccTraceSchema tempLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
//                                tempLCInsureAccTraceSchema = createInsuAccTrace(aLCInsureAccClassSchema,tLOArrearageSchema.getFeeCode(),state, -traceArrearageValue,-traceArrearageUnit);
//                                tempLCInsureAccTraceSchema.setPayDate(tLOArrearageSchema.getPayDate());
//                                tLOArrearageSchema.setSumMoney(tLOArrearageSchema.getSumMoney() -tempLCInsureAccTraceSchema.getMoney());
//                                aLCInsureAccClassSchema.setUnitCount(aLCInsureAccClassSchema.getUnitCount() +tempLCInsureAccTraceSchema.getUnitCount());
//
//                                //生成补缴轨迹表
//                                tLCInsureAccTraceSet.add(tempLCInsureAccTraceSchema);
//                                tLCInsureAccFeeTraceSet.add(createInsuAccFeeTrace(tempLCInsureAccTraceSchema));
//                            }
//                        }

                        //如果账户价值为0，记为欠费
                        String LOArrearageFlag = "0";
                        if(aLCInsureAccClassSchema.getUnitCount() <= 0)
                        {
                            //不对Trace表进行处理,如有欠费记入LOArrearage
                            aLCInsureAccClassSchema.setUnitCount(0);
                            LOArrearageFlag = "0";
                        }
                        else
                        {
                            //对Trace表进行处理,如有欠费记入LOArrearage
                            LOArrearageFlag = "1";
                        }

                        //2.正常管理费收取处理
                        //先获取该账户管理费信息
                        strsql ="select * from (select * from lmriskfee where insuaccno='" +"?insuaccno?" +"' and PayPlanCode='" +"?payplancode?" +"' and feestartdate<='" + "?_DealDate?" +
                                "' union " +"select * from lmriskfee where insuaccno='" +"?inno?" +"' and PayPlanCode='000000' and feestartdate<='" +"?date?" + 
                                "' union " +"select * from lmriskfee where insuaccno='000000' and PayPlanCode='" +"?code?" +"' and feestartdate<='" + "?ddate?" +
                                "' union " +"select * from lmriskfee where insuaccno='000000' and PayPlanCode='000000' and feestartdate<='" +"?dldate?" +"' and feetype='0') f order by FeeNum";
                        SQLwithBindVariables tsqlbv2 = new SQLwithBindVariables();
                        tsqlbv2.sql(strsql);
                        tsqlbv2.put("insuaccno", aLCInsureAccClassSchema.getInsuAccNo());
                        tsqlbv2.put("payplancode", aLCInsureAccClassSchema.getPayPlanCode());
                        tsqlbv2.put("_DealDate", _DealDate);
                        
                        tsqlbv2.put("inno", aLCInsureAccClassSchema.getInsuAccNo());
                        tsqlbv2.put("date", _DealDate);
                        tsqlbv2.put("code", aLCInsureAccClassSchema.getPayPlanCode());
                        tsqlbv2.put("ddate", _DealDate);
                        tsqlbv2.put("dldate", _DealDate);
                        
                        LMRiskFeeSet aLMRiskFeeSet = new LMRiskFeeSet();
                        LMRiskFeeDB aLMRiskFeeDB = new LMRiskFeeDB();
                        aLMRiskFeeSet = aLMRiskFeeDB.executeQuery(tsqlbv2);

                        double feeTraceValue = 0.00; //管理费金额
                        double feeTraceUnit = 0.00; //管理费单位数

                        //对管理费表进行循环处理
                        for(int k = 1; k <= aLMRiskFeeSet.size(); k++)
                        {
                            LMRiskFeeSchema aLMRiskFeeSchema = aLMRiskFeeSet.get(k);
                            //判断是否进行管理费收取
                            //借用LCInsureAccClass的BalaDate设置业务生效日期
                            //默认为计价日，在isTakePlace内部会根据特殊情况进行重置
                            aLCInsureAccClassSchema.setBalaDate(_DealDate);
                            try{
                                if(!PubInsuAccFun.isTakePlace(aLCInsureAccClassSchema,aLMRiskFeeSchema, _DealDate, PolAccBala)){
                                    continue;
                                }
                            }catch(Exception ex){
                                ex.printStackTrace();
                                break;
                            }
                            //计算单位数以及金额
                            if(aLMRiskFeeSchema.getFeeCalModeType().equals("0"))
                            {
                                //取固定值
                                if(aLMRiskFeeSchema.getFeeCalMode().equals("01"))
                                {
                                    //固定值内扣
                                    feeTraceValue = aLMRiskFeeSchema.getFeeValue();
                                }else if(aLMRiskFeeSchema.getFeeCalMode().equals("02"))
                                {
                                    //固定比例内扣
                                    //calPrice算出账户价值
                                    feeTraceValue = -PubInsuAccFun.calPrice( -aLCInsureAccClassSchema.getUnitCount(), _DealDate,aLCInsureAccClassSchema.getInsuAccNo()) *aLMRiskFeeSchema.getFeeValue();
                                }
                                else
                                {
                                    //默认情况
                                    feeTraceValue = aLMRiskFeeSchema.getFeeValue();
                                }
                            }
                            else if(aLMRiskFeeSchema.getFeeCalModeType().equals("1"))
                            {
                                //通过sql计算得到
                                feeTraceValue = PubInsuAccFun.calFeeValue(aLMRiskFeeSchema.getFeeCalCode(),aLCInsureAccClassSchema,aLOAccUnitPriceSchema, PolAccBala);
                            }
                            else if(aLMRiskFeeSchema.getFeeCalModeType().equals("2"))
                            {
                                //通过class计算得到
                                try
                                {
                                    Class tClass = Class.forName(aLMRiskFeeSchema.getInterfaceClassName());
                                    ManageFeeService tManageFeeService = (ManageFeeService) tClass.newInstance();

                                    VData tVData = new VData();
                                    String tOperate = "";
                                    if(!tManageFeeService.submitData(tVData,tOperate))
                                    {
                                        //错处理
                                        continue;
                                    }
                                    feeTraceValue = tManageFeeService.getfeeValue();
                                    //获取金额或单位
                                }
                                catch(Exception ex)
                                {
                                	break;
                                }
                            }
                            if(feeTraceValue == 0){
                                continue;
                            }
                            //计算投资单位,用卖出价
                            feeTraceUnit = -PubInsuAccFun.calUnit( -feeTraceValue,_DealDate,aLOAccUnitPriceSchema.getInsuAccNo());
                            if(feeTraceUnit == 0)
                            {
                                CError.buildErr(this,"账户" +aLOAccUnitPriceSchema.getInsuAccNo() +"尚未计价!");
                                tDealInsuAccLog.createLOAccLog(_DealDate,aLCInsureAccSchema, "02","账户" +aLOAccUnitPriceSchema.getInsuAccNo() +"尚未计价!");
                                continue;
                            }
                            //生成LCInsureAccTrace以及LCInsureAccFeeTrace表结构
                            if(state.equals("1"))
                            {
                                LCInsureAccTraceSchema tempLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
                                tempLCInsureAccTraceSchema = createInsuAccTrace(aLCInsureAccClassSchema,aLMRiskFeeSchema.getFeeCode(),state, -feeTraceValue,-feeTraceUnit);
                                tempLCInsureAccTraceSchema.setPayDate(aLCInsureAccClassSchema.getBalaDate());
                                //判断如果Trace中的扣减单位数>账户单位数，则放入欠费表
                                aLCInsureAccClassSchema.setUnitCount(aLCInsureAccClassSchema.getUnitCount() +tempLCInsureAccTraceSchema.getUnitCount());
                                if(aLCInsureAccClassSchema.getUnitCount() < 0)
                                {
                                	//add by nandd temp
//                                    LOArrearageSchema aLOArrearageSchema = new LOArrearageSchema();
                                    tempLCInsureAccTraceSchema.setUnitCount(tempLCInsureAccTraceSchema.getUnitCount() -aLCInsureAccClassSchema.getUnitCount());
                                    tempLCInsureAccTraceSchema.setMoney(PubInsuAccFun.calPrice(tempLCInsureAccTraceSchema.getUnitCount(),tempLCInsureAccTraceSchema));
                                    //生成欠费记录
                                  //add by nandd temp
//                                    aLOArrearageSchema = PubInsuAccFun.createArrearage(aLCInsureAccClassSchema,tempLCInsureAccTraceSchema);
//                                    aLOArrearageSchema.setOperator(_GlobalInput.Operator);
//                                    aLOArrearageSchema.setSumMoney(aLOArrearageSchema.getSumMoney() +PubInsuAccFun.calPrice(aLCInsureAccClassSchema.getUnitCount(),tempLCInsureAccTraceSchema));
//                                    String Flag = "0";
//                                    for(int q = 1; q <= tLOArrearageSet.size();q++)
//                                    {
//                                        if(aLOArrearageSchema.getEdorNo().equals(tLOArrearageSet.get(q).getEdorNo()) &&
//                                           aLOArrearageSchema.getPolNo().equals(tLOArrearageSet.get(q).getPolNo()) &&
//                                           aLOArrearageSchema.getFeeCode().equals(tLOArrearageSet.get(q).getFeeCode()) &&
//                                           aLOArrearageSchema.getPayDate().equals(tLOArrearageSet.get(q).getPayDate()))
//                                        {
//                                            tLOArrearageSet.get(q).setSumMoney(tLOArrearageSet.get(q).getSumMoney() +aLOArrearageSchema.getSumMoney());
//                                            tLOArrearageSet.get(q).setModifyDate(CurrentDate);
//                                            tLOArrearageSet.get(q).setModifyTime(CurrentTime);
//                                            Flag = "1";
//                                            break;
//                                        }
//                                    }
//                                    //如果有重复记录,则在以前欠费基础上将欠费金额加上
//                                    if(Flag.equals("0"))
//                                    {
//                                        tLOArrearageSet.add(aLOArrearageSchema);
//                                    }
                                    aLCInsureAccClassSchema.setUnitCount(0);
                                }
                                if(LOArrearageFlag.equals("0")){
                                    continue;
                                }
                                tLCInsureAccTraceSet.add(tempLCInsureAccTraceSchema);
                                tLCInsureAccFeeTraceSet.add(createInsuAccFeeTrace(tempLCInsureAccTraceSchema));
                                //账户表更新
                                feeValue += -tempLCInsureAccTraceSchema.getMoney();
                                feeUnit += -tempLCInsureAccTraceSchema.getUnitCount();
                                //账户分类表更新
                                feeClassValue += -tempLCInsureAccTraceSchema.getMoney();
                                feeClassUnit += -tempLCInsureAccTraceSchema.getUnitCount();
                            }
                        }
                    }
                    //准备账户分类表
                    if(getLCInsureAccFeeTraceSet() != null &&
                       getLCInsureAccFeeTraceSet().size() > 0 && state.equals("1"))
                    {
                        tLCInsureAccClassSet = PubInsuAccFun.createAccClassByTrace(getLCInsureAccTraceSet());
                        tLCInsureAccClassFeeSet = PubInsuAccFun.createAccFeeClassByTrace(getLCInsureAccFeeTraceSet());
                    }
                }
                if(getLCInsureAccClassFeeSet() != null &&
                   getLCInsureAccClassFeeSet().size() > 0 && state.equals("1"))
                {
                    //准备账户表
                    tLCInsureAccSet = PubInsuAccFun.createAccByTrace(getLCInsureAccTraceSet());
                    tLCInsureAccFeeSet = PubInsuAccFun.createAccFeeByTrace(getLCInsureAccFeeTraceSet());
                }
                
                //改为统一更新
                mLCInsureAccTraceSet.add(tLCInsureAccTraceSet);
                mLCInsureAccClassSet.add(tLCInsureAccClassSet);
                mLCInsureAccSet.add(tLCInsureAccSet);
                mLCInsureAccFeeTraceSet.add(tLCInsureAccFeeTraceSet);
                mLCInsureAccClassFeeSet.add(tLCInsureAccClassFeeSet);
                mLCInsureAccFeeSet.add(tLCInsureAccFeeSet);
            }
			 if (!updateAccInfo())
			{
				CError.buildErr(this, "账户信息更新出错!");
				tDealInsuAccLog.createLOAccLog(_DealDate, aLCInsureAccSet.get(1), "02", "账户信息更新出错");
			}
        }
    }

    /*更新账户信息*/
    public boolean updateAccInfo()
    {
        VData tVData = new VData();
        MMap mmap = new MMap();
        //准备公共提交数据
        mmap.put(mLCInsureAccTraceSet, "INSERT");
        mmap.put(mLCInsureAccClassSet, "UPDATE");
        mmap.put(mLCInsureAccSet, "UPDATE");
        mmap.put(mLCInsureAccFeeTraceSet, "INSERT");
        mmap.put(mLCInsureAccClassFeeSet, "DELETE&INSERT");
        mmap.put(mLCInsureAccFeeSet, "DELETE&INSERT");
        if(mmap != null && mmap.keySet().size() > 0){
            tVData.add(mmap);
        }
        PubSubmit tPubSubmit = new PubSubmit();
        if(!tPubSubmit.submitData(tVData, ""))
        {
            return false;
        }
        mLCInsureAccTraceSet.clear();
        mLCInsureAccTraceSet.clear();
        mLCInsureAccSet.clear();
        mLCInsureAccFeeTraceSet.clear();
        mLCInsureAccClassFeeSet.clear();
        mLCInsureAccFeeSet.clear();
        return true;
    }

    /*生成账户轨迹表结构*/
    public LCInsureAccTraceSchema createInsuAccTrace(LCInsureAccClassSchema tLCInsureAccClassSchema, String tFeeCode, String tState, double tPrice, double tUnit)
    {
        LCInsureAccTraceSchema mLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
        Reflections tReflections = new Reflections();
        tReflections.transFields(mLCInsureAccTraceSchema,tLCInsureAccClassSchema);
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
    public LCInsureAccFeeTraceSchema createInsuAccFeeTrace( LCInsureAccClassSchema tLCInsureAccClassSchema, String tFeeCode, String tState, double tPrice, double tUnit)
    {
        LCInsureAccFeeTraceSchema mLCInsureAccFeeTraceSchema = new LCInsureAccFeeTraceSchema();
        Reflections tReflections = new Reflections();
        tReflections.transFields(mLCInsureAccFeeTraceSchema,tLCInsureAccClassSchema);
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
    public LCInsureAccFeeTraceSchema createInsuAccFeeTrace(LCInsureAccTraceSchema tLCInsureAccTraceSchema)
    {
        LCInsureAccFeeTraceSchema mLCInsureAccFeeTraceSchema = new LCInsureAccFeeTraceSchema();
        Reflections tReflections = new Reflections();
        tReflections.transFields(mLCInsureAccFeeTraceSchema,tLCInsureAccTraceSchema);
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
        DealInsuAccManageFee tDealInsuAccManageFee = new DealInsuAccManageFee(tGlobalInput, "2009-07-06");

        tDealInsuAccManageFee.calInsuAccManageFee(PubInsuAccFun.queryInsuAccManageSet(), 0);
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

  //add by nandd temp
//    public LOArrearageSet getLOArrearageSet()
//    {
//        return tLOArrearageSet;
//    }
}
