package com.sinosoft.lis.acc;
import org.apache.log4j.Logger;

import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;

/**
 * <p>Title: </p>
 *
 * <p>Description:投连计价处理 </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company:sinosoft </p>
 *
 * @author:ck
 * @version 1.0
 */
public class DealInsuAccPrice
{
private static Logger logger = Logger.getLogger(DealInsuAccPrice.class);

    private GlobalInput _GlobalInput = new GlobalInput();
    /*计价时间*/
    private String _DealDate = PubFun.getCurrentDate();
    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();
    private DealInsuAccLog tDealInsuAccLog = new DealInsuAccLog();
    /**/
    private LCInsureAccSet _LCInsureAccSet = new LCInsureAccSet();
    private LCInsureAccClassSet _LCInsureAccClassSet = new LCInsureAccClassSet();
    private LCInsureAccTraceSet _LCInsureAccTraceSet = new LCInsureAccTraceSet();
    /*结果集*/
    private LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet();
    private LCInsureAccClassSet tLCInsureAccClassSet = new LCInsureAccClassSet();
    private LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet();
    private LCInsureAccFeeSet tLCInsureAccFeeSet;
    private LCInsureAccClassFeeSet tLCInsureAccClassFeeSet;
    private LCInsureAccFeeTraceSet tLCInsureAccFeeTraceSet;
    private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();

    /*默认当天计价*/
    public DealInsuAccPrice(GlobalInput aGlobalInput)
    {
        _GlobalInput = aGlobalInput;
    }

    /*aDealDate为指定的计价时间*/
    public DealInsuAccPrice(GlobalInput aGlobalInput, String aDealDate)
    {
        _GlobalInput = aGlobalInput;
        _DealDate = aDealDate;
    }

    /*对保单账户进行计价处理 */
    public void calPrice(LCInsureAccSet aLCInsureAccSet)
    {
        /*计价逻辑处理*/
        _LCInsureAccSet = aLCInsureAccSet;
        for(int i = 1; i <= _LCInsureAccSet.size(); i++)
        {
            tLCInsureAccSet = new LCInsureAccSet();
            tLCInsureAccClassSet = new LCInsureAccClassSet();
            tLCInsureAccTraceSet = new LCInsureAccTraceSet();
            tLCInsureAccFeeSet = new LCInsureAccFeeSet();
            tLCInsureAccClassFeeSet = new LCInsureAccClassFeeSet();
            tLCInsureAccFeeTraceSet = new LCInsureAccFeeTraceSet();

            //标记帐户中有没有错误的Trace记录或class记录
            int flag = 0;

            LCInsureAccSchema tLCInsureAccSchema = new LCInsureAccSchema();
            tLCInsureAccSchema = _LCInsureAccSet.get(i);
            LCInsureAccClassSet _ttLCInsureAccClassSet = new
                    LCInsureAccClassSet(); //要计价的集合
            LCInsureAccClassDB ttLCInsureAccClassDB = new LCInsureAccClassDB();
            SQLwithBindVariables sqlbv = new SQLwithBindVariables();
            String tSQL = "select * from LCInsureAccClass where PolNo='" +
                    		"?polno?" + "' and InsuAccNo='" +
                    		"?insuaccno?" + "'";
            sqlbv.sql(tSQL);
            sqlbv.put("polno", tLCInsureAccSchema.getPolNo());
            sqlbv.put("insuaccno", tLCInsureAccSchema.getInsuAccNo());
            _ttLCInsureAccClassSet = ttLCInsureAccClassDB.executeQuery(sqlbv);
            logger.debug(tSQL);
            //循环账户分类表
            for(int j = 1; j <= _ttLCInsureAccClassSet.size(); j++)
            {
                LCInsureAccClassSchema tLCInsureAccClassSchema =
                        _ttLCInsureAccClassSet.get(j);
                //循环LCInsureAccTrace表
                LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
                LCInsureAccTraceSet _ttLCInsureAccTraceSet = new
                        LCInsureAccTraceSet(); //要计价的集合
                LCInsureAccTraceSet ttLCInsureAccTraceSet = new
                        LCInsureAccTraceSet(); //结果集
                SQLwithBindVariables tsqlbv = new SQLwithBindVariables();
                String ttSQL ="select * from LCInsureAccTrace  where insuaccno='" +
                        "?insuaccno?" +
                        "' and PolNo='" + "?polno?"
                        + "' and PayPlanCode='" +
                        "?payplanCode?"
                        + "' and AccAscription='" +
                        "?accascription?"
                        +
                        "' and state='0' and paydate is not null order by paydate ";
                tsqlbv.sql(ttSQL);
                tsqlbv.put("insuaccno", tLCInsureAccClassSchema.getInsuAccNo());
                tsqlbv.put("polno", tLCInsureAccClassSchema.getPolNo());
                tsqlbv.put("payplanCode", tLCInsureAccClassSchema.getPayPlanCode());
                tsqlbv.put("accascription", tLCInsureAccClassSchema.getAccAscription());
                _ttLCInsureAccTraceSet = tLCInsureAccTraceDB.executeQuery(tsqlbv);
                for(int k = 1; k <= _ttLCInsureAccTraceSet.size(); k++)
                { //如果class对应下的Trace记录有出错情况，则清空对象、跳出循环
                    /*   if (flag != 0) {
                           _ttLCInsureAccTraceSet.clear();
                           ttLCInsureAccTraceSet.clear();
                           break;
                       }*/
                    LCInsureAccTraceSchema tLCInsureAccTraceSechma = new
                            LCInsureAccTraceSchema();
                    tLCInsureAccTraceSechma = _ttLCInsureAccTraceSet.get(k);
//                    String mSQL =
//                            "Select min(StartDate) from LOAccUnitPrice where InsuAccNo='" +
//                            tLCInsureAccTraceSechma.getInsuAccNo()
//                            + "' and StartDate>='" +
//                            tLCInsureAccTraceSechma.getPayDate() +
//                            "' and state='0'";
//                    String lastdate = "";
//                    ExeSQL tExeSQL = new ExeSQL();
//                    SSRS tSSRS = new SSRS();
//                    tSSRS = tExeSQL.execSQL(mSQL);
//
//                    if(tSSRS.GetText(1, 1) == null ||
//                       tSSRS.GetText(1, 1).equals("") ||
//                       tSSRS.GetText(1, 1).equals(null))
//                    { //出错没有合理的报价日期
//                        tDealInsuAccLog.createLOAccLog(_DealDate,
//                                tLCInsureAccTraceSechma,
//                                "01", "paydate之后没有报价日期");
//                        //      ttLCInsureAccTraceSet.clear();
//                        //flag++;
//                        break;
//                    }
//                    else
//                    {
//                        lastdate = tSSRS.GetText(1, 1);
//                    }
                    //获取下次计价日期，T+N计价处理，T为业务发生日期，N为T之后的N单位的计价日，不包含本次计价日
                    String lastdate = PubInsuAccFun.getNextStartDate(tLCInsureAccTraceSechma.getPayDate(), tLCInsureAccTraceSechma.getInsuAccNo());
                    if(lastdate == null || "".equals(lastdate) || lastdate.equals(null)){
                    	tDealInsuAccLog.createLOAccLog(_DealDate,tLCInsureAccTraceSechma,
                                "01", "paydate之后没有报价日期");
                    	continue;
                    }                    
                    
                    //比较lastdate和系统时间_DealDate
                    if(lastdate.compareTo(_DealDate) > 0)
                    {
                        //当前批处理计价日期尚未到账户应该交易日期，跳过不做交易处理
                        //出错跳过，不做不做业务处理，但要加入错误记录
                        tDealInsuAccLog.createLOAccLog(_DealDate,
                                tLCInsureAccTraceSechma,
                                "01", "当前批处理计价日期尚未到账户应该交易日期");
                        //   ttLCInsureAccTraceSet.clear();
                        //flag++;
                        continue;//break;
                    }
                    else
                    {
                        //数据通过检验，进行计价处理
                        tLCInsureAccTraceSechma.setSchema(PubInsuAccFun.
                                getNewLCInsureAccTraceSchema(
                                        tLCInsureAccTraceSechma, _DealDate));
                    }
                    //add by snowman  保全交易通知书
                    //对真正计过价的保单才插入保全交易通知书
                    if(tLCInsureAccTraceSechma.getAccAlterNo()!=null && !tLCInsureAccTraceSechma.getAccAlterNo().equals(""))
                    {
                    	DealInsuAccTerNotice sDealInsuAccTerNotice = new DealInsuAccTerNotice(_GlobalInput);
                    	sDealInsuAccTerNotice.CheckBarginBQValue(tLCInsureAccTraceSechma.getAccAlterNo(),_DealDate);
                    }
                    ttLCInsureAccTraceSet.add(tLCInsureAccTraceSechma);
                }
                if(ttLCInsureAccTraceSet.size() > 0)
                {
                    for(int k = 1; k <= ttLCInsureAccTraceSet.size(); k++)
                    {
                        LCInsureAccTraceSchema tLCInsureAccTraceSchema = new
                                LCInsureAccTraceSchema();
                        tLCInsureAccTraceSchema = ttLCInsureAccTraceSet.get(k);

                        if(tLCInsureAccTraceSchema.getMoneyType().equals(
                                "GL"))
                        {
                            tLCInsureAccFeeTraceSet.add(PubInsuAccFun.
                                    createFeeTraceByAccTrace(
                                            tLCInsureAccTraceSchema));
                        }
                    }

                    tLCInsureAccTraceSet.add(ttLCInsureAccTraceSet);
                }
            }

            /*金额试算，对金额是否超出账户进行判断*/
            LCInsureAccTraceSet tempLCInsureAccTraceSet = new
                    LCInsureAccTraceSet();
            tempLCInsureAccTraceSet = dealAccBala(tLCInsureAccTraceSet);
            if(tempLCInsureAccTraceSet == null)
                continue;
            if(tLCInsureAccTraceSet.size() > 0)
            {
                tLCInsureAccClassSet.add(PubInsuAccFun.createAccClassByTrace(
                        tempLCInsureAccTraceSet));
                tLCInsureAccSet.add(PubInsuAccFun.createAccByTrace(
                        tempLCInsureAccTraceSet));

                //生成交易通知书数据 gaoht add
//                mLOPRTManagerSet.add(PubInsuAccFun.creatPrtManagerSet(tempLCInsureAccTraceSet,AccPrintManagerBL.TL_CODE_BUSINESS));

                if(tLCInsureAccFeeTraceSet.size() > 0)
                {
                    tLCInsureAccFeeSet.add(PubInsuAccFun.createAccFeeByTrace(
                            tLCInsureAccFeeTraceSet));
                    tLCInsureAccClassFeeSet.add(PubInsuAccFun.
                                                createAccFeeClassByTrace(
                            tLCInsureAccFeeTraceSet));
                }
            }
            /*
             if (flag != 0) { ////标记帐户中出现错误的Trace记录或class记录，则从下一个帐户开初。
                continue;
                         }
             */

            if(tLCInsureAccFeeSet.size() > 0)
            {
                if(!updateAccInfo())
                {
                    CError.buildErr(this, "账户信息更新失败!");
                    tDealInsuAccLog.createLOAccLog(_DealDate,
                            _LCInsureAccSet.get(i),
                            "01", "账户信息更新失败!");
                }
                else
                {
                    LOAccLogSchema tmpLOAccLogSchema = new LOAccLogSchema();
                    tmpLOAccLogSchema.setPolNo(_LCInsureAccSet.get(i).getPolNo());
                    tmpLOAccLogSchema.setDealDate(_DealDate);
                    tDealInsuAccLog.DeleteLOAccLog(tmpLOAccLogSchema);
                }
            }
            else
            {
                if(!Submit())
                {
                    CError.buildErr(this, "账户信息更新失败!");
                    tDealInsuAccLog.createLOAccLog(_DealDate,
                            _LCInsureAccSet.get(i),
                            "01", "账户信息更新失败!");
                }
                else
                {
                    LOAccLogSchema tmpLOAccLogSchema = new LOAccLogSchema();
                    tmpLOAccLogSchema.setPolNo(_LCInsureAccSet.get(i).getPolNo());
                    tmpLOAccLogSchema.setDealDate(_DealDate);
                    tDealInsuAccLog.DeleteLOAccLog(tmpLOAccLogSchema);

                }
            }
            DealInsuAccTerNotice tDealInsuAccTerNotice = new DealInsuAccTerNotice(_GlobalInput);
            //add by snowman  交易通知书
            LCPolSet _LCPolSet = new LCPolSet();
            LCPolDB _LCPolDB = new LCPolDB();
            _LCPolDB.setPolNo(_LCInsureAccSet.get(i).getPolNo());
            _LCPolSet = _LCPolDB.query();
            LCPolSchema aLCPolSchema = _LCPolSet.get(1);
            tDealInsuAccTerNotice.CheckBarginrValue(aLCPolSchema,_DealDate);
        }
    }

    /*进行金额试算，判断是否超过账户价值*/
    public LCInsureAccTraceSet dealAccBala(LCInsureAccTraceSet
                                           tLCInsureAccTraceSet)
    {
        LCInsureAccTraceSet mLCInsureAccTraceSet = new LCInsureAccTraceSet();

        LCInsureAccClassSet tLCInsureAccClassSet = new LCInsureAccClassSet();
        for(int i = 1; i <= tLCInsureAccTraceSet.size(); i++)
        {
            LCInsureAccTraceSchema tLCInsureAccTraceSchema =
                    tLCInsureAccTraceSet.get(i);

            String flag = "0";
            for(int j = 1; j <= tLCInsureAccClassSet.size(); j++)
            {
                if(tLCInsureAccTraceSchema.getPolNo().equals(
                        tLCInsureAccClassSet.get(j).
                        getPolNo()) &&
                   tLCInsureAccTraceSchema.getInsuAccNo().equals(
                           tLCInsureAccClassSet.get(
                                   j).getInsuAccNo()) &&
                   tLCInsureAccTraceSchema.getPayPlanCode().
                   equals(tLCInsureAccClassSet.get(j).getPayPlanCode()) &&
                   tLCInsureAccTraceSchema.getAccAscription().equals(
                           tLCInsureAccClassSet.get(j).
                           getAccAscription()))
                {
                    flag = "1";
                    //如果在集合中存在的话，则更新原记录
                    tLCInsureAccClassSet.get(j).setUnitCount(
                            tLCInsureAccClassSet.get(j).getUnitCount() +
                            tLCInsureAccTraceSchema.getUnitCount());
                    /*判断金额是否超出账户余额*/
                    if(tLCInsureAccClassSet.get(j).getUnitCount() < 0)
                    {
                        LMEdorItemDB tLMEdorItemDB = new LMEdorItemDB();
                        tLMEdorItemDB.setEdorCode(tLCInsureAccTraceSchema.
                                                  getMoneyType());
                        if(tLCInsureAccTraceSchema.getGrpPolNo().equals(
                                "00000000000000000000"))
                        {
                            tLMEdorItemDB.setAppObj("I");
                        }
                        else
                        {
                            tLMEdorItemDB.setAppObj("G");
                        }
                        if(tLMEdorItemDB.getInfo())
                        {
                            if(tLMEdorItemDB.getAccBalaFlag() != null &&
                               tLMEdorItemDB.getAccBalaFlag().equals("01"))
                            {
                                //写错误日志
                                tDealInsuAccLog.createLOAccLog(_DealDate,
                                        tLCInsureAccClassSet.get(j),
                                        "01", "申请金额大于账户现有余额!");
                                return null;
                            }
                            if(tLMEdorItemDB.getAccBalaFlag() != null &&
                               tLMEdorItemDB.getAccBalaFlag().equals("02"))
                            {
                                //按照账户余额处理
                                tLCInsureAccTraceSchema.setUnitCount(
                                        tLCInsureAccTraceSchema.getUnitCount() -
                                        tLCInsureAccClassSet.get(j).
                                        getUnitCount()
                                        );
                                tLCInsureAccTraceSchema.setMoney(PubInsuAccFun.
                                        calPrice(
                                                tLCInsureAccTraceSchema.
                                                getUnitCount(),
                                                tLCInsureAccTraceSchema)
                                        );
                            }
                        }
                    }

                    break;
                }
            }
            LCInsureAccClassSchema tLCInsureAccClassSchema = new
                    LCInsureAccClassSchema();

            LCInsureAccClassSet tempLCInsureAccClassSet = new
                    LCInsureAccClassSet();
            LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
            tLCInsureAccClassDB.setPolNo(tLCInsureAccTraceSchema.getPolNo());
            tLCInsureAccClassDB.setInsuAccNo(tLCInsureAccTraceSchema.
                                             getInsuAccNo());
            tLCInsureAccClassDB.setPayPlanCode(tLCInsureAccTraceSchema.
                                               getPayPlanCode());

            tLCInsureAccClassDB.setAccAscription(tLCInsureAccTraceSchema.
                                                 getAccAscription());
            tempLCInsureAccClassSet = tLCInsureAccClassDB.query();
            //如果在集合中不存在的话，则更新class
            if(flag.equals("0"))
            {
                if(tempLCInsureAccClassSet.size() > 0)
                {
                    tLCInsureAccClassSchema = tempLCInsureAccClassSet.get(1);
                    tLCInsureAccClassSchema.setUnitCount(
                            tLCInsureAccClassSchema.
                            getUnitCount() +
                            tLCInsureAccTraceSchema.
                            getUnitCount());

                    /*判断金额是否超出账户余额*/
                    if(tLCInsureAccClassSchema.getUnitCount() < 0)
                    {
                        LMEdorItemDB tLMEdorItemDB = new LMEdorItemDB();
                        tLMEdorItemDB.setEdorCode(tLCInsureAccTraceSchema.
                                                  getMoneyType());
                        if(tLCInsureAccTraceSchema.getGrpPolNo().equals(
                                "00000000000000000000"))
                        {
                            tLMEdorItemDB.setAppObj("I");
                        }
                        else
                        {
                            tLMEdorItemDB.setAppObj("G");
                        }
                        if(tLMEdorItemDB.getInfo())
                        {
                            if(tLMEdorItemDB.getAccBalaFlag() != null &&
                               tLMEdorItemDB.getAccBalaFlag().equals("01"))
                            {
                                //写错误日志
                                tDealInsuAccLog.createLOAccLog(_DealDate,
                                        tLCInsureAccClassSchema,
                                        "01", "申请金额大于账户现有余额!");
                                return null;
                            }
                            if(tLMEdorItemDB.getAccBalaFlag() != null &&
                               tLMEdorItemDB.getAccBalaFlag().equals("02"))
                            {
                                //按照账户余额处理
                                tLCInsureAccTraceSchema.setUnitCount(
                                        tLCInsureAccTraceSchema.getUnitCount() -
                                        tLCInsureAccClassSchema.getUnitCount()
                                        );
                                tLCInsureAccTraceSchema.setMoney(PubInsuAccFun.
                                        calPrice(
                                                tLCInsureAccTraceSchema.
                                                getUnitCount(),
                                                tLCInsureAccTraceSchema));
                            }
                        }
                    }
                }
                else
                {
                    return null;
                }
            }
            mLCInsureAccTraceSet.add(tLCInsureAccTraceSchema);
        }

        return mLCInsureAccTraceSet;
    }

    /*生成管理费轨迹表结构*/
    public void createInsuAccFeeTrace(LCInsureAccClassSchema
                                      tLCInsureAccClassSchema,
                                      String tFeeCode,
                                      double tPrice, double tUnit)
    {
        LCInsureAccFeeTraceSchema mLCInsureAccFeeTraceSchema = new
                LCInsureAccFeeTraceSchema();
        Reflections tReflections = new Reflections();

        tReflections.transFields(mLCInsureAccFeeTraceSchema,
                                 tLCInsureAccClassSchema);

        String tLimit = PubFun.getNoLimit(mLCInsureAccFeeTraceSchema.
                                          getManageCom());
        String SerialNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
        mLCInsureAccFeeTraceSchema.setSerialNo(SerialNo);
        mLCInsureAccFeeTraceSchema.setMoneyType("GL");
        mLCInsureAccFeeTraceSchema.setPayDate(_DealDate);
        mLCInsureAccFeeTraceSchema.setState("1");
        mLCInsureAccFeeTraceSchema.setFee(tPrice);
        mLCInsureAccFeeTraceSchema.setFeeUnit(tUnit);
        mLCInsureAccFeeTraceSchema.setOperator(_GlobalInput.Operator);
        mLCInsureAccFeeTraceSchema.setMakeDate(CurrentDate);
        mLCInsureAccFeeTraceSchema.setMakeTime(CurrentTime);
        mLCInsureAccFeeTraceSchema.setModifyDate(CurrentDate);
        mLCInsureAccFeeTraceSchema.setModifyTime(CurrentTime);
        mLCInsureAccFeeTraceSchema.setFeeCode(tFeeCode);
        tLCInsureAccFeeTraceSet.add(mLCInsureAccFeeTraceSchema);
    }

    public boolean Submit()
    {
        VData tVData = new VData();
        MMap mmap = new MMap();
        //准备公共提交数据
        mmap.put(tLCInsureAccTraceSet, "UPDATE");
        mmap.put(tLCInsureAccClassSet, "UPDATE");
        mmap.put(mLOPRTManagerSet, "DELETE&INSERT");
        mmap.put(tLCInsureAccSet, "UPDATE");
        if(mmap != null && mmap.keySet().size() > 0)
            tVData.add(mmap);
        PubSubmit tPubSubmit = new PubSubmit();
        if(!tPubSubmit.submitData(tVData, ""))
        {
            return false;
        }
        return true;
    }

    /*更新账户信息*/
    public boolean updateAccInfo()
    {
        VData tVData = new VData();
        MMap mmap = new MMap();

        //准备公共提交数据
        mmap.put(tLCInsureAccTraceSet, "UPDATE");
        mmap.put(tLCInsureAccClassSet, "UPDATE");
        mmap.put(tLCInsureAccSet, "UPDATE");
        mmap.put(tLCInsureAccFeeTraceSet, "DELETE&INSERT");
        mmap.put(tLCInsureAccClassFeeSet, "DELETE&INSERT");
        mmap.put(tLCInsureAccFeeSet, "DELETE&INSERT");
        mmap.put(mLOPRTManagerSet, "DELETE&INSERT");

        if(mmap != null && mmap.keySet().size() > 0)
            tVData.add(mmap);
        PubSubmit tPubSubmit = new PubSubmit();
        if(!tPubSubmit.submitData(tVData, ""))
        {
            return false;
        }
        return true;
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
    
    /*对保单账户进行计价处理 */
    public double calOnePrice(String _PolNo,String _DutyCode,String _DealDate,String _Currency)
    {
        /*计价逻辑处理*/
    	PubInsuAccFun.isTN = "L";
    	double PolAccBala = 0.0;
        tLCInsureAccTraceSet = new LCInsureAccTraceSet();
    	LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
    	SQLwithBindVariables sqlbv = new SQLwithBindVariables();
    	String Sql = "select * from lcinsureacc a ,lmriskapp b where a.riskcode=b.riskcode and b.risktype3='3' and a.polno = '"+"?_PolNo?"+"'";
    	sqlbv.sql(Sql);
    	sqlbv.put("_PolNo", _PolNo);
        _LCInsureAccSet = tLCInsureAccDB.executeQuery(sqlbv);
        logger.debug(Sql);
        for(int i = 1; i <= _LCInsureAccSet.size(); i++)
        {
            LCInsureAccClassSet _ttLCInsureAccClassSet = new LCInsureAccClassSet(); //要计价的集合
            LCInsureAccClassDB ttLCInsureAccClassDB = new LCInsureAccClassDB();
            SQLwithBindVariables tsqlbv = new SQLwithBindVariables();
            String strpolno = _LCInsureAccSet.get(i).getPolNo();
            String strinsuaccno = _LCInsureAccSet.get(i).getInsuAccNo();
            String tSQL = "select * from LCInsureAccClass a where PolNo='" + "?strpolno?" 
                    + "' and InsuAccNo='" +"?strinsuaccno?" + "' and exists(Select 'X' From lmdutypayrela Where payplancode = a.payplancode And dutycode = '"+"?_DutyCode?"+"' )";
            tsqlbv.sql(tSQL);
            tsqlbv.put("strpolno", strpolno);
            tsqlbv.put("strinsuaccno", strinsuaccno);
            tsqlbv.put("_DutyCode", _DutyCode);
            _ttLCInsureAccClassSet = ttLCInsureAccClassDB.executeQuery(tsqlbv);
            logger.debug(tSQL);
            //循环账户分类表
            for(int j = 1; j <= _ttLCInsureAccClassSet.size(); j++)
            {
                LCInsureAccClassSchema tLCInsureAccClassSchema = _ttLCInsureAccClassSet.get(j);
                //循环LCInsureAccTrace表
                LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
                LCInsureAccTraceSet _ttLCInsureAccTraceSet = new LCInsureAccTraceSet(); //要计价的集合
                LCInsureAccTraceSet ttLCInsureAccTraceSet = new LCInsureAccTraceSet(); //结果集
                SQLwithBindVariables tsqlbv0 = new SQLwithBindVariables();
                String ttSQL = "select * from LCInsureAccTrace where insuaccno='" +
                        "?insuaccno?" +
                        "' and PolNo='" + "?polno?"
                        + "' and PayPlanCode='" +"?payplancode?"
                        + "' and AccAscription='" +"?accascription?"+
                        "' and state='0' and paydate is not null order by paydate ";
                tsqlbv0.sql(ttSQL);
                tsqlbv0.put("insuaccno", tLCInsureAccClassSchema.getInsuAccNo());
                tsqlbv0.put("polno", tLCInsureAccClassSchema.getPolNo());
                tsqlbv0.put("payplancode", tLCInsureAccClassSchema.getPayPlanCode());
                tsqlbv0.put("accascription", tLCInsureAccClassSchema.getAccAscription());
                _ttLCInsureAccTraceSet = tLCInsureAccTraceDB.executeQuery(tsqlbv0);
                for(int k = 1; k <= _ttLCInsureAccTraceSet.size(); k++)
                { 
                    LCInsureAccTraceSchema tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
                    tLCInsureAccTraceSchema = _ttLCInsureAccTraceSet.get(k);
                    tLCInsureAccTraceSchema.setUnitCount(-tLCInsureAccTraceSchema.getUnitCount());
                    tLCInsureAccTraceSchema.setMoney(-tLCInsureAccTraceSchema.getMoney());
                    tLCInsureAccTraceSchema.setSchema(PubInsuAccFun.getNewLCInsureAccTraceSchema(tLCInsureAccTraceSchema, _DealDate));
                    ttLCInsureAccTraceSet.add(tLCInsureAccTraceSchema);
                }
                Reflections ref = new Reflections();
                LCInsureAccTraceSchema tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
                ref.transFields(tLCInsureAccTraceSchema, _ttLCInsureAccClassSet.get(j));
                tLCInsureAccTraceSchema.setMoneyType("TB");  //金额类型为 TB-退保金
                tLCInsureAccTraceSchema.setState("0");
                tLCInsureAccTraceSchema.setPayDate(_DealDate);
                tLCInsureAccTraceSchema.setUnitCount(-_ttLCInsureAccClassSet.get(j).getUnitCount());
                tLCInsureAccTraceSchema.setSchema(PubInsuAccFun.getNewLCInsureAccTraceSchema(tLCInsureAccTraceSchema, _DealDate));
                ttLCInsureAccTraceSet.add(tLCInsureAccTraceSchema);
                if(ttLCInsureAccTraceSet.size() > 0)
                {
                    tLCInsureAccTraceSet.add(ttLCInsureAccTraceSet);
                }
            }
        }
        for(int k=1;k <= tLCInsureAccTraceSet.size();k++){
        	logger.debug(tLCInsureAccTraceSet.get(k).getInsuAccNo()+":"+tLCInsureAccTraceSet.get(k).getMoney());
        	LDExch ex = new LDExch();
        	PolAccBala += ex.toOtherCur(tLCInsureAccTraceSet.get(k).getCurrency(), (_Currency == null || "".equals(_Currency))?SysConst.BaseCur:_Currency, _DealDate, tLCInsureAccTraceSet.get(k).getMoney());
        }
        logger.debug("结算后总金额为："+-PolAccBala);
        return -PolAccBala;
    }

}
