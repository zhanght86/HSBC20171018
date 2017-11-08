package com.sinosoft.lis.get;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vbl.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 投保业务逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author TJJ
 * @version 1.0
 */
public class LFGetPayBL
{
private static Logger logger = Logger.getLogger(LFGetPayBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();

    /** 往后面传输数据的容器 */
    private VData mInputData;

    /** 数据操作字符串 */
    private String mOperate;

    /** 全局数据 */
    private GlobalInput mGlobalInput = new GlobalInput();

    /** 业务处理相关变量 */
    private LJSGetDrawSet mLJSGetDrawSet = new LJSGetDrawSet();
    private LJSGetSet aLJSGetSet = new LJSGetSet();
    private LJAGetDrawSet aLJAGetDrawSet = new LJAGetDrawSet();
    private LJAGetSet aLJAGetSet = new LJAGetSet();
    private LJSGetDrawSet aLJSGetDrawSet = new LJSGetDrawSet();
    private LCGetSet aLCGetSet = new LCGetSet();
    private LJFIGetSet aLJFIGetSet = new LJFIGetSet();
    private LJTempFeeClassSet aLJTempFeeClassSet = new LJTempFeeClassSet();
    private LJTempFeeSet aLJTempFeeSet = new LJTempFeeSet();
    private LJAPaySet aLJAPaySet = new LJAPaySet();
    private LJAPayPersonSet aLJAPayPersonSet = new LJAPayPersonSet();
    private LCInsureAccSet aLCInsureAccSet = new LCInsureAccSet();
    private LCInsureAccTraceSet aLCInsureAccTraceSet = new LCInsureAccTraceSet();
    private String mActuGetNo = "";

    public LFGetPayBL()
    {
    }

    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     *         cOperate 数据操作
     * @return:
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        //将操作数据拷贝到本类中
        this.mOperate = cOperate;

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData))
        {
            return false;
        }
        if (!checkData())
        {
          return false;
        }
        //进行业务处理
        if (!dealData())
        {
            return false;
        }

        logger.debug("after dealData....");

        for (int i = 1; i <= this.mLJSGetDrawSet.size(); i++)
        {
            aLJSGetDrawSet.clear();
            aLJAGetDrawSet.clear();
            aLJSGetSet.clear();
            aLJAGetSet.clear();
            aLCGetSet.clear();
            aLJFIGetSet.clear();
            aLJTempFeeClassSet.clear();
            aLJTempFeeSet.clear();
            aLJAPaySet.clear();
            aLJAPayPersonSet.clear();
            aLCInsureAccSet.clear();
            aLCInsureAccTraceSet.clear();

            LJSGetDrawSchema tLJSGetDrawSchema = mLJSGetDrawSet.get(i);


            //得到所有的必需的存储和修改数据
            if (!processOneLJSGetDraw(tLJSGetDrawSchema))
            {
                return false;
            }
            logger.debug("afterOneLJSGetDraw....");

            //准备往后台的数据
            if (!prepareOutputData())
            {
                return false;
            }

            //催付核销数据库操作
            logger.debug("Start LFGetPay BLS Submit...");
            LFGetPayBLS tLFGetPayBLS = new LFGetPayBLS();
            tLFGetPayBLS.submitData(mInputData, cOperate);

            //如果有需要处理的错误，则返回
            if (tLFGetPayBLS.mErrors.needDealError())
            {
                // @@错误处理
                CError.buildErr(this,"数据提交失败!",tLFGetPayBLS.mErrors);
                return false;
            }
            logger.debug("End LFGetPay BLS Submit...");

            logger.debug("Start 销户操作....");
            logger.debug(aLJAGetDrawSet.size());

            //销户操作
            String aPolNo = "";
            for (int j = 1; j <= aLJAGetDrawSet.size(); j++)
            {
                if ((j == 1) || aLJAGetDrawSet.get(j).getPolNo().equals(aPolNo))
                {
                    aPolNo = aLJAGetDrawSet.get(j).getPolNo();
                    String destroyFlag = aLJAGetDrawSet.get(j).getDestrayFlag();
                    logger.debug("---" + destroyFlag);
                    if ((destroyFlag != null) && destroyFlag.equals("1"))
                    {
                        //sxy-2003-09-10修改该保单状态为终止.
                        String tsql = "select * from lcpol where polno = '?aPolNo?' ";
                        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
                        sqlbv.sql(tsql);
                        sqlbv.put("aPolNo", aPolNo);
                        LCPolDB tLCPolDB = new LCPolDB();
                        LCPolSet tLCPolSet = new LCPolSet();
                        tLCPolSet = tLCPolDB.executeQuery(sqlbv);
                        if (tLCPolSet.size() == 1)
                        {
                            LCPolSchema tLCPolSchema = new LCPolSchema();
                            VData tVData = new VData();
                            tLCPolSchema.setPolNo(tLCPolSet.get(1).getPolNo());
                            tLCPolSchema.setPolState("0303"
                                + tLCPolSet.get(1).getPolState().substring(0, 3));

                            tVData.add(tLCPolSchema);
                            ChangPolStateBLS tChangPolStateBLS = new ChangPolStateBLS();
                            if (!tChangPolStateBLS.submitData(tVData, "UPDATA"))
                            {
                                // @@错误处理
                                CError.buildErr(this,"保单状态数据修改失败!");
                                return false;
                            }
                        }
                        else
                        {
                            // @@错误处理
                            CError.buildErr(this,"保单数据查询出错!");
                            return false;
                        }

                        //sxy-2003-09-10
                        ContCancel aContCancel = new ContCancel(aPolNo,
                                aLJAGetDrawSet.get(j).getActuGetNo());
                        if (!aContCancel.submitData())
                        {
                            return false;
                        }
                    }
                }
            } //销户操作
        }

        return true;
    }

    public String getActuGetNo()
    {
        return this.mActuGetNo;
    }

    private boolean processOneLJSGetDraw(LJSGetDrawSchema tLJSGetDrawSchema)
    {

        if(tLJSGetDrawSchema.getGetDate().compareTo(PubFun.getCurrentDate())>0)
        {
            CError.buildErr(this,"未到应领日期，不能进行领取!");
            return false;
        }

        LJSGetDB tLJSGetDB = new LJSGetDB();
        tLJSGetDB.setGetNoticeNo(tLJSGetDrawSchema.getGetNoticeNo());
        if (!tLJSGetDB.getInfo())
        {
            this.mErrors.copyAllErrors(tLJSGetDB.mErrors);
            return false;
        }

        
        LJSGetSchema tLJSGetSchema = tLJSGetDB.getSchema();

        LCGetSchema tLCGetSchema = new LCGetSchema();
        LCGetDB tLCGetDB = new LCGetDB();
        tLCGetDB.setPolNo(tLJSGetDrawSchema.getPolNo());
        tLCGetDB.setDutyCode(tLJSGetDrawSchema.getDutyCode());
        tLCGetDB.setGetDutyCode(tLJSGetDrawSchema.getGetDutyCode());
        if (!tLCGetDB.getInfo())
        {
            this.mErrors.copyAllErrors(tLCGetDB.mErrors);
            return false;
        }
        tLCGetSchema.setSchema(tLCGetDB.getSchema());
        //del by JL at 2005-8-1
        //tLCGetSchema.setGettoDate(tLJSGetDrawSchema.getCurGetToDate());
        //tLCGetSchema.setSumMoney(tLCGetSchema.getSumMoney()
        //    + tLJSGetDrawSchema.getGetMoney());
        //end del

        //查询保单号，得到相应的付费方式
        LCPolSchema tLCPolSchema = new LCPolSchema();
        LCPolDB tLCPolDB = new LCPolDB();
        tLCPolDB.setPolNo(tLJSGetDrawSchema.getPolNo());
        if (!tLCPolDB.getInfo())
        {
            this.mErrors.copyAllErrors(tLCPolDB.mErrors);
            return false;
        }
        tLCPolSchema.setSchema(tLCPolDB.getSchema());
        logger.debug("after LCPolSchema get...");
        String flag = tLCPolSchema.getLiveGetMode();

        //默认的领取方式为现金方式
        if ((flag == null) || flag.trim().equals(""))
        {
            flag = "2";
        }

        if (flag.equals("2"))
        { //现金方式
            if (!CashProcess(tLCPolSchema, tLJSGetDrawSchema, tLJSGetSchema,
                        tLCGetSchema))
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        else
        {
            CError.buildErr(this,"暂时不支持非现金方式领取");
            return false;
        }
/*
        if (flag.equals("3"))
        { //抵交保费
            if (!PremProcess(tLCPolSchema, tLJSGetDrawSchema, tLJSGetSchema,
                        tLCGetSchema))
            {
                return false;
            }
            else
            {
                return true;
            }
        }

        if (flag.equals("1"))
        { //累计生息
            if (!AccProcess(tLCPolSchema, tLJSGetDrawSchema, tLJSGetSchema,
                        tLCGetSchema))
            {
                return false;
            }
            else
            {
                return true;
            }
        }
*/
        //return true;
    }



    private boolean CashProcess(LCPolSchema tLCPolSchema,
        LJSGetDrawSchema tLJSGetDrawSchema, LJSGetSchema tLJSGetSchema,
        LCGetSchema tLCGetSchema)
    {
        logger.debug("start CashProcess");

        String tActuGetNo = PubFun1.CreateMaxNo("GETNO",
                PubFun.getNoLimit(tLJSGetDrawSchema.getManageCom())); //参数为机构代码
        mActuGetNo = tActuGetNo;
        logger.debug("==>tActuGetNo:" + mActuGetNo);

        LJAGetDrawSchema tLJAGetDrawSchema = new LJAGetDrawSchema();
        tLJAGetDrawSchema = initLJAGetDrawSchema(tLJSGetDrawSchema);

        LJAGetSchema tLJAGetSchema = new LJAGetSchema();
        tLJAGetSchema = initLJAGetSchema(tLJSGetSchema);

        tLCGetSchema.setGettoDate(tLJSGetDrawSchema.getCurGetToDate());
        tLCGetSchema.setSumMoney(tLCGetSchema.getSumMoney()
            + tLJSGetDrawSchema.getGetMoney());
        tLCGetSchema.setActuGet(tLJSGetDrawSchema.getGetMoney());

        aLJSGetDrawSet.add(tLJSGetDrawSchema);
        aLJAGetDrawSet.add(tLJAGetDrawSchema);

        aLJSGetSet.add(tLJSGetSchema);
        aLJAGetSet.add(tLJAGetSchema);
        aLCGetSet.add(tLCGetSchema);

        return true;
    }

    /**
     * 根据前面的输入数据，进行BL逻辑处理
     * 如果在处理过程中出错，则返回false,否则返回true
     */
    private boolean dealData()
    {
        boolean tReturn = true;
        double aActGetMoney = 0;
        String tActuGetNo = "";

        //自动运行接口
        if (mGlobalInput.Operator.equals("AUTOBATCH")
                && ((mLJSGetDrawSet == null) || (mLJSGetDrawSet.size() == 0)))
        {
            mLJSGetDrawSet.clear();
            String sql = "select * from lJSGetDraw where GetDate<='?GetDate?' and GetMoney <> 0 and ManageCom like concat('?ManageCom?','%')";
            SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
            sqlbv1.sql(sql);
            sqlbv1.put("GetDate", PubFun.getCurrentDate());
            sqlbv1.put("ManageCom", mGlobalInput.ManageCom);
            logger.debug("----sql" + sql);
            LJSGetDrawDB tLJSGetDrawDB = new LJSGetDrawDB();
            mLJSGetDrawSet = tLJSGetDrawDB.executeQuery(sqlbv1);
        }

        return tReturn;
    }

    /**
     * 从输入数据中得到所有对象
     *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData)
    {
        //全局变量
        mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
                "GlobalInput", 0));
        mLJSGetDrawSet.set((LJSGetDrawSet) cInputData.getObjectByObjectName(
                "LJSGetDrawSet", 0));

        return true;
    }

    /**
     * 准备往后层输出所需要的数据
     * 输出：如果准备数据时发生错误则返回false,否则返回true
     */
    private boolean prepareOutputData()
    {
        //mInputData=new VData();
        try
        {
            mInputData = new VData();
            mInputData.addElement(mGlobalInput);
            mInputData.addElement(aLJSGetSet);
            mInputData.addElement(aLJAGetSet);
            mInputData.addElement(aLJSGetDrawSet);
            mInputData.addElement(aLJAGetDrawSet);
            mInputData.addElement(aLCGetSet);

            mInputData.addElement(aLJFIGetSet);
            mInputData.addElement(aLJTempFeeClassSet);
            mInputData.addElement(aLJTempFeeSet);
            mInputData.addElement(aLJAPaySet);
            mInputData.addElement(aLJAPayPersonSet);
            mInputData.addElement(aLCInsureAccSet);
            mInputData.addElement(aLCInsureAccTraceSet);
        }
        catch (Exception ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LFGetPayBL";
            tError.functionName = "prepareData";
            tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    private boolean checkData()
    {
        if (mGlobalInput == null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LFGetPayBL";
            tError.functionName = "prepareData";
            tError.errorMessage = "请重新登陆页面。";
            this.mErrors.addOneError(tError);
            return false;
        }
        if ((mLJSGetDrawSet == null) || (mLJSGetDrawSet.size() == 0))
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LFGetPayBL";
            tError.functionName = "prepareData";
            tError.errorMessage = "请选择领取责任。";
            this.mErrors.addOneError(tError);
            return false;
        }
        //add by ck:如果有借款和垫交未还清的情况不允许领取(校验同一主险的所有保单)
        for (int i = 1; i <= mLJSGetDrawSet.size(); i++)
        {
          String PolNo = mLJSGetDrawSet.get(i).getPolNo();
          String strsql = "select Mainpolno from lcpol where polno='?PolNo?'";
          SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
          sqlbv2.sql(strsql);
          sqlbv2.put("PolNo", PolNo);
          ExeSQL cExeSQL = new ExeSQL();
          String MainPolNo = cExeSQL.getOneValue(sqlbv2);
          LCPolDB cLCPolDB = new LCPolDB();
          cLCPolDB.setPolNo(MainPolNo);

          LCPolSet cLCPolSet = new LCPolSet();
          cLCPolSet = cLCPolDB.query();
          logger.debug("cLCPolSet:"+cLCPolSet.size());
          for (int j = 1; j <= cLCPolSet.size(); j++)
          {
            LOLoanSet cLOLoanSet = new LOLoanSet();
            LOLoanDB cLOLoanDB = new LOLoanDB();
            cLOLoanDB.setPolNo(cLCPolSet.get(j).getPolNo());
            cLOLoanDB.setPayOffFlag("0");
            cLOLoanSet = cLOLoanDB.query();
            logger.debug("cLOLoanSet.size():"+cLOLoanSet.size());
            if (cLOLoanSet.size() > 0)
            {
              // @@错误处理
              CError.buildErr(this,
                              "主险或附加险:" + cLCPolSet.get(j).getPolNo() +
                              "有借款或垫交，请先还欠款再做领取!");

              return false;
            }
          }
        }
        
        return true;
    }

    private LJAGetSchema initLJAGetSchema(LJSGetSchema aLJSGetSchema)
    {
        LJAGetSchema aLJAGetSchema = new LJAGetSchema();
        Reflections tReflections = new Reflections();

        //此处增加一些计算代码
        tReflections.transFields(aLJAGetSchema, aLJSGetSchema);
        aLJAGetSchema.setActuGetNo(mActuGetNo);
        aLJAGetSchema.setShouldDate(aLJSGetSchema.getGetDate());
        aLJAGetSchema.setMakeDate(PubFun.getCurrentDate());
        aLJAGetSchema.setMakeTime(PubFun.getCurrentTime());
        aLJAGetSchema.setModifyDate(PubFun.getCurrentDate());
        aLJAGetSchema.setModifyTime(PubFun.getCurrentTime());
        return aLJAGetSchema;
    }

    private LJAGetDrawSchema initLJAGetDrawSchema(
        LJSGetDrawSchema aLJSGetDrawSchema)
    {
        LJAGetDrawSchema aLJAGetDrawSchema = new LJAGetDrawSchema();
        Reflections tReflections = new Reflections();

        //此处增加一些计算代码
        tReflections.transFields(aLJAGetDrawSchema, aLJSGetDrawSchema);
        aLJAGetDrawSchema.setActuGetNo(mActuGetNo);
        //aLJAGetDrawSchema.setGetDate(PubFun.getCurrentDate());
        aLJAGetDrawSchema.setMakeDate(PubFun.getCurrentDate());
        aLJAGetDrawSchema.setMakeTime(PubFun.getCurrentTime());
        aLJAGetDrawSchema.setModifyDate(PubFun.getCurrentDate());
        aLJAGetDrawSchema.setModifyTime(PubFun.getCurrentTime());
        logger.debug("---modifyTime:" + aLJAGetDrawSchema.getModifyTime());
        return aLJAGetDrawSchema;
    }

    public static void main(String[] args)
    {
        VData tVData = new VData();
        GlobalInput tG = new GlobalInput();

        LJSGetSchema tLJSGetSchema = new LJSGetSchema();

        //        tG.Operator = "AUTOBATCH";
        //        tG.ManageCom = "001";
        tG.Operator = "001";
        tG.ManageCom = "86110000";
        tVData.addElement(tG);

        //tVData.addElement(tLJSGetSchema);
        LFGetPayBL tLFGetPayBL = new LFGetPayBL();
        tLFGetPayBL.submitData(tVData, "INSERT||PERSON");
    }
}
