package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.finfee.*;

import java.util.Date;

import com.sinosoft.lis.f1print.*;
import com.sinosoft.lis.finfee.FinFeePubFun;
import com.sinosoft.lis.operfee.RnAccountDeal;

/**
 * <p>Title: 保全项目投连万能复缴明细</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: Sinosoft</p>
 * @author gaoht
 * @version 1.0
 */

public class PEdorTSDetailBL
{
private static Logger logger = Logger.getLogger(PEdorTSDetailBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;
    private int mPaySTime;
    private String remark="";
    /** 全局数据 */
    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();
    private TransferData mTransferData = new TransferData();
    private MMap map = new MMap();
    private BqCalBase mBqCalBase = new BqCalBase();
    private LCContSchema mLCContSchema = new LCContSchema();
    private LPContSchema mLPContSchema = new LPContSchema();
    private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
    private GlobalInput mGlobalInput = new GlobalInput();
    private LPInsureAccClassFeeSet mLPInsureAccClassFeeSet = new
            LPInsureAccClassFeeSet();
    private LPInsureAccFeeTraceSet mLPInsureAccFeeTraceSet = new
            LPInsureAccFeeTraceSet();
    private LPInsureAccFeeSet mLPInsureAccFeeSet = new LPInsureAccFeeSet();
    private LPInsureAccClassSet mLPInsureAccClassSet = new LPInsureAccClassSet();
    private LPInsureAccTraceSet mLPInsureAccTraceSet = new LPInsureAccTraceSet();
    private LPInsureAccSet mLPInsureAccSet = new LPInsureAccSet();
    private LJSGetEndorseSet mLJSGetEndorseSet = new LJSGetEndorseSet();
    private LJSPayPersonSet mLJSPayPersonSet = new LJSPayPersonSet();
    private LPContStateSet mLPContStateSet = new LPContStateSet();
    private boolean mChangeFlag = false;
    private boolean mNeedReFlag = false;
    private LPDutySet mLPDutySet = new LPDutySet();
    private LPPremSet mLPPremSet = new LPPremSet();
    private LPGetSet mLPGetSet = new LPGetSet();
    private LPPolSet mLPPolSet = new LPPolSet();
    private String mPolNos = "";
    private double mSumMoney = 0;
    private double mSumAccMoney = 0; //此次进帐户的金额
    private String mPaytoDate;
    private String mNextPayToDate = "";
    private int mActuPaytime = 0;
    private String mGetNoticeNo = "";
    private String mBankCode = "";
    private String mBankAccNo = "";
    private String mBankAccName = "";
    private String mPayDate = "";
    double mRate = 0;
    private String state; // 保单停效原因代码，在LCCont.State

    /**
     * 数据提交的公共方法
     *
     * @param: cInputData 传入的数据 cOperate 数据操作字符串
     * @return:
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        // 将操作数据拷贝到本类中
        mInputData = (VData) cInputData.clone();
        mOperate = cOperate;
        // 得到外部传入的数据,将数据备份到本类中
        if (!getInputData())
        {
            return false;
        }
        // 数据处理
        if (!dealData())
        {
            return false;
        }
        // 数据准备操作
        if (!PrepareData())
        {
            return false;
        }
        return true;
    }

    /**
     * 从输入数据中得到所有对象
     *
     * @return boolean
     */
    private boolean getInputData()
    {
        try
        {
            logger.debug("============进入投连及期交万能复缴处理==============");
            mLPEdorItemSchema = (LPEdorItemSchema) mInputData.getObjectByObjectName("LPEdorItemSchema", 0);
            mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);
            mTransferData = (TransferData) mInputData.getObjectByObjectName("TransferData", 0);
        }catch (Exception e){
            CError.buildErr(this, "接收数据失败");
            mErrors.addOneError("接收数据失败!");
            return false;
        }
        if (mLPEdorItemSchema == null)
        {
            mErrors.addOneError("接受数据无效!");
            return false;
        }
        remark = mLPEdorItemSchema.getStandbyFlag3();
        LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
        LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
        tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
        tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
        tLPEdorItemSet = tLPEdorItemDB.query();
        if (tLPEdorItemSet == null || tLPEdorItemSet.size() == 0)
        {
            CError.buildErr(this, "查询保全项目信息失败!");
            return false;
        }

        mLPEdorItemSchema = tLPEdorItemSet.get(1);
        mLPEdorItemSchema.setStandbyFlag3(remark);

        LCContDB tLCContDB = new LCContDB();
        tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
        if (!tLCContDB.getInfo())
        {
            mErrors.addOneError("查询保单信息失败!");
            return false;
        }
        mLCContSchema = tLCContDB.getSchema();
        if (mLCContSchema.getPayLocation() != null &&
            mLCContSchema.getPayLocation().equals("0"))
        {
            mBankCode = mLCContSchema.getBankCode();
            mBankAccNo = mLCContSchema.getBankAccNo();
            mBankAccName = mLCContSchema.getAccName();
        }
        return true;
    }

    public VData getResult()
    {
        return mResult;
    }

    private String GetStateData(String cPolNo, String cStateType,
                                String cConlum)
    {
        String tStateReason = "";
        String tSql = "select "+cConlum+" from lccontstate where polno='?cPolNo?' and statetype='?cStateType?' and enddate is null ";
        ExeSQL tExeSQL = new ExeSQL();
        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
        sqlbv.sql(tSql);
        sqlbv.put("cPolNo", cPolNo);
        sqlbv.put("cStateType", cStateType);
        tStateReason = tExeSQL.getOneValue(sqlbv);
        if (tStateReason == null || tStateReason.equals(""))
        {
            tStateReason = "FAILED";
        }
        return tStateReason;
    }

    private boolean GetLPDuty(String cPolNo, String cState)
    {
        LCDutyDB tLCDutyDB = new LCDutyDB();
        tLCDutyDB.setPolNo(cPolNo);
        LCDutySet tLCDutySet = new LCDutySet();
        tLCDutySet = tLCDutyDB.query();
        if (tLCDutySet.size() == 0)
        {
            CError.buildErr(this, "该保单不能复缴!未能找到有效的责任信息");
            return false;
        }
        LPDutySet tLPDutySet = new LPDutySet();
        for (int i = 1; i <= tLCDutySet.size(); i++)
        {
            LCDutySchema tLCDutySchema = new LCDutySchema();
            tLCDutySchema = tLCDutySet.get(i).getSchema();
            LPDutySchema tLPDutySchema = new LPDutySchema();
            Reflections tReflections = new Reflections();
            tReflections.transFields(tLPDutySchema, tLCDutySchema);
            tLPDutySet.add(tLPDutySchema);
        }
        mLPDutySet.add(tLPDutySet);
        return true;
    }

    private boolean CreatPAcc()
    {
        for (int i = 1; i <= mLJSPayPersonSet.size(); i++)
        {
            LJSPayPersonSchema tLJSPayPersonSchema = new LJSPayPersonSchema();
            tLJSPayPersonSchema = mLJSPayPersonSet.get(i).getSchema();
            Reflections tRef = new Reflections();
            LJAPayPersonSchema tLJAPayPersonSchema = new LJAPayPersonSchema();
            tRef.transFields(tLJAPayPersonSchema, tLJSPayPersonSchema);
            tLJAPayPersonSchema.setPayNo(mLPEdorItemSchema.getEdorNo());
            LJAPayPersonSet tLJAPayPersonSet = new LJAPayPersonSet();
            tLJAPayPersonSet.add(tLJAPayPersonSchema);
            VData tVData = new VData();
            tVData.add(tLJAPayPersonSet);
            tVData.add(mGlobalInput);
            RnAccountDeal tRnAccountDeal = new RnAccountDeal();
            if (!tRnAccountDeal.submitData(tVData, ""))
            {
                CError.buildErr(this, tRnAccountDeal.mErrors.getFirstError());
                return false;
            }
            tVData = new VData();
            tVData = tRnAccountDeal.getResult();
            LCInsureAccClassFeeSet tLCInsureAccClassFeeSet = new
                    LCInsureAccClassFeeSet();
            tLCInsureAccClassFeeSet = (LCInsureAccClassFeeSet) tVData.
                                      getObjectByObjectName(
                                              "LCInsureAccClassFeeSet", 0);
            LCInsureAccFeeTraceSet tLCInsureAccFeeTraceSet = new
                    LCInsureAccFeeTraceSet();
            tLCInsureAccFeeTraceSet = (LCInsureAccFeeTraceSet) tVData.
                                      getObjectByObjectName(
                                              "LCInsureAccFeeTraceSet", 0);
            double  tInitFee=0;
            for(int j=1;j<=tLCInsureAccFeeTraceSet.size();j++)
            {
            	tInitFee +=tLCInsureAccFeeTraceSet.get(j).getFee();
            }

            LCInsureAccFeeSet tLCInsureAccFeeSet = new LCInsureAccFeeSet();
            tLCInsureAccFeeSet = (LCInsureAccFeeSet) tVData.
                                 getObjectByObjectName("LCInsureAccFeeSet", 0);
            LCInsureAccClassSet tLCInsureAccClassSet = new LCInsureAccClassSet();
            tLCInsureAccClassSet = (LCInsureAccClassSet) tVData.
                                   getObjectByObjectName("LCInsureAccClassSet",
                    0);
            LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet();
            tLCInsureAccTraceSet = (LCInsureAccTraceSet) tVData.
                                   getObjectByObjectName("LCInsureAccTraceSet",
                    0);
            LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet();
            tLCInsureAccSet = (LCInsureAccSet) tVData.
                              getObjectByObjectName("LCInsureAccSet", 0);
            for(int j=1;j<=tLCInsureAccTraceSet.size();j++)
            {
            	mSumAccMoney +=tLCInsureAccTraceSet.get(j).getMoney();
            }


            tRef = new Reflections();
            LPInsureAccClassFeeSet tLPInsureAccClassFeeSet = new
                    LPInsureAccClassFeeSet();
            LPInsureAccFeeTraceSet tLPInsureAccFeeTraceSet = new
                    LPInsureAccFeeTraceSet();
            LPInsureAccFeeSet tLPInsureAccFeeSet = new LPInsureAccFeeSet();
            LPInsureAccClassSet tLPInsureAccClassSet = new LPInsureAccClassSet();
            LPInsureAccTraceSet tLPInsureAccTraceSet = new LPInsureAccTraceSet();
            LPInsureAccSet tLPInsureAccSet = new LPInsureAccSet();
            for (int t = 1; t <= tLCInsureAccClassFeeSet.size(); t++)
            {
                LCInsureAccClassFeeSchema tLCInsureAccClassFeeSchema = new
                        LCInsureAccClassFeeSchema();
                LPInsureAccClassFeeSchema tLPInsureAccClassFeeSchema = new
                        LPInsureAccClassFeeSchema();
                tLCInsureAccClassFeeSchema = tLCInsureAccClassFeeSet.get(t);
                tRef = new Reflections();
                tRef.transFields(tLPInsureAccClassFeeSchema,
                                 tLCInsureAccClassFeeSchema);
                tLPInsureAccClassFeeSchema.setEdorNo(mLPEdorItemSchema.
                        getEdorNo());
                tLPInsureAccClassFeeSchema.setEdorType("TS");
                tLPInsureAccClassFeeSchema.setMakeDate(CurrentDate);
                tLPInsureAccClassFeeSchema.setModifyDate(CurrentDate);
                tLPInsureAccClassFeeSchema.setMakeTime(CurrentTime);
                tLPInsureAccClassFeeSchema.setModifyTime(CurrentTime);
                tLPInsureAccClassFeeSet.add(tLPInsureAccClassFeeSchema);
            }
            mLPInsureAccClassFeeSet.set(tLPInsureAccClassFeeSet);
            for (int t = 1; t <= tLCInsureAccFeeTraceSet.size(); t++)
            {
                LCInsureAccFeeTraceSchema tLCInsureAccFeeTraceSchema = new
                        LCInsureAccFeeTraceSchema();
                LPInsureAccFeeTraceSchema tLPInsureAccFeeTraceSchema = new
                        LPInsureAccFeeTraceSchema();
                tLCInsureAccFeeTraceSchema = tLCInsureAccFeeTraceSet.get(t);
                tRef = new Reflections();
                tRef.transFields(tLPInsureAccFeeTraceSchema,
                                 tLCInsureAccFeeTraceSchema);
                tLPInsureAccFeeTraceSchema.setEdorNo(mLPEdorItemSchema.
                        getEdorNo());
                tLPInsureAccFeeTraceSchema.setEdorType("TS");
                tLPInsureAccFeeTraceSchema.setMakeDate(CurrentDate);

                tLPInsureAccFeeTraceSchema.setModifyDate(CurrentDate);
                tLPInsureAccFeeTraceSchema.setMakeTime(CurrentTime);
                tLPInsureAccFeeTraceSchema.setModifyTime(CurrentTime);
                tLPInsureAccFeeTraceSet.add(tLPInsureAccFeeTraceSchema);
            }
            mLPInsureAccFeeTraceSet.add(tLPInsureAccFeeTraceSet);
            for (int t = 1; t <= tLCInsureAccFeeSet.size(); t++)
            {
                LCInsureAccFeeSchema tLCInsureAccFeeSchema = new
                        LCInsureAccFeeSchema();
                LPInsureAccFeeSchema tLPInsureAccFeeSchema = new
                        LPInsureAccFeeSchema();
                tLCInsureAccFeeSchema = tLCInsureAccFeeSet.get(t);
                tRef = new Reflections();
                tRef.transFields(tLPInsureAccFeeSchema, tLCInsureAccFeeSchema);
                tLPInsureAccFeeSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
                tLPInsureAccFeeSchema.setEdorType("TS");
                tLPInsureAccFeeSchema.setMakeDate(CurrentDate);
                tLPInsureAccFeeSchema.setModifyDate(CurrentDate);
                tLPInsureAccFeeSchema.setMakeTime(CurrentTime);
                tLPInsureAccFeeSchema.setModifyTime(CurrentTime);
                tLPInsureAccFeeSet.add(tLPInsureAccFeeSchema);
            }

            mLPInsureAccFeeSet.set(tLPInsureAccFeeSet);
            for (int t = 1; t <= tLCInsureAccClassSet.size(); t++)
            {
                LCInsureAccClassSchema tLCInsureAccClassSchema = new
                        LCInsureAccClassSchema();
                LPInsureAccClassSchema tLPInsureAccClassSchema = new
                        LPInsureAccClassSchema();
                tLCInsureAccClassSchema = tLCInsureAccClassSet.get(t);
                tRef = new Reflections();
                tRef.transFields(tLPInsureAccClassSchema,
                                 tLCInsureAccClassSchema);
                tLPInsureAccClassSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
                tLPInsureAccClassSchema.setEdorType("TS");
                tLPInsureAccClassSchema.setMakeDate(CurrentDate);
                tLPInsureAccClassSchema.setModifyDate(CurrentDate);
                tLPInsureAccClassSchema.setMakeTime(CurrentTime);
                tLPInsureAccClassSchema.setModifyTime(CurrentTime);
                tLPInsureAccClassSet.add(tLPInsureAccClassSchema);
            }
            mLPInsureAccClassSet.set(tLPInsureAccClassSet);
            for (int t = 1; t <= tLCInsureAccTraceSet.size(); t++)
            {
                LCInsureAccTraceSchema tLCInsureAccTraceSchema = new
                        LCInsureAccTraceSchema();
                LPInsureAccTraceSchema tLPInsureAccTraceSchema = new
                        LPInsureAccTraceSchema();
                tLCInsureAccTraceSchema = tLCInsureAccTraceSet.get(t);
                tRef = new Reflections();
                tRef.transFields(tLPInsureAccTraceSchema,
                                 tLCInsureAccTraceSchema);
                tLPInsureAccTraceSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
                tLPInsureAccTraceSchema.setEdorType("TS");
                tLPInsureAccTraceSchema.setMakeDate(CurrentDate);
                tLPInsureAccTraceSchema.setBusyType("TS");
                tLPInsureAccTraceSchema.setModifyDate(CurrentDate);
                tLPInsureAccTraceSchema.setMakeTime(CurrentTime);
                tLPInsureAccTraceSchema.setModifyTime(CurrentTime);
                tLPInsureAccTraceSet.add(tLPInsureAccTraceSchema);
            }
            mLPInsureAccTraceSet.add(tLPInsureAccTraceSet);
            for (int t = 1; t <= tLCInsureAccSet.size(); t++)
            {
                LCInsureAccSchema tLCInsureAccSchema = new LCInsureAccSchema();
                LPInsureAccSchema tLPInsureAccSchema = new LPInsureAccSchema();
                tLCInsureAccSchema = tLCInsureAccSet.get(t);
                tRef = new Reflections();
                tRef.transFields(tLPInsureAccSchema, tLCInsureAccSchema);
                tLPInsureAccSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
                tLPInsureAccSchema.setEdorType("TS");
                tLPInsureAccSchema.setMakeDate(CurrentDate);
                tLPInsureAccSchema.setModifyDate(CurrentDate);
                tLPInsureAccSchema.setMakeTime(CurrentTime);
                tLPInsureAccSchema.setModifyTime(CurrentTime);
                tLPInsureAccSet.add(tLPInsureAccSchema);
            }
            mLPInsureAccSet.set(tLPInsureAccSet);
        }
        return true;
    }

    private boolean AfterDeal()
    {
        for (int i = 1; i <= mLPPremSet.size(); i++)
        {
            mLPPremSet.get(i).setEdorNo(mLPEdorItemSchema.getEdorNo());
            mLPPremSet.get(i).setEdorType("TS");
            mLPPremSet.get(i).setPayTimes(mLPPremSet.get(i).getPayTimes() +
                                          this.mActuPaytime);
            mLPPremSet.get(i).setPaytoDate(mNextPayToDate);
            for (int t = 1; t <= mLJSPayPersonSet.size(); t++)
            {
                if (mLJSPayPersonSet.get(t).getPolNo().equals(mLPPremSet.get(i).
                        getPolNo()) &&
                    mLJSPayPersonSet.get(t).getPayPlanCode().
                    equals(mLPPremSet.get(i).getPayPlanCode()))
                {
                    mLPPremSet.get(i).setSumPrem(mLPPremSet.get(i).getSumPrem() +
                                                 mLJSPayPersonSet.get(t).
                                                 getSumActuPayMoney());
                }
            }
            mLPPremSet.get(i).setModifyDate(CurrentDate);
            mLPPremSet.get(i).setModifyTime(CurrentTime);
        }
        for (int i = 1; i <= mLPDutySet.size(); i++)
        {
            mLPDutySet.get(i).setEdorNo(mLPEdorItemSchema.getEdorNo());
            mLPDutySet.get(i).setEdorType("TS");
            mLPDutySet.get(i).setPaytoDate(mNextPayToDate);
            for (int t = 1; t <= mLJSPayPersonSet.size(); t++)
            {
                if (mLJSPayPersonSet.get(t).getPolNo().equals(mLPDutySet.get(i).
                        getPolNo()) &&
                    mLJSPayPersonSet.get(t).getDutyCode().
                    equals(mLPDutySet.get(i).getDutyCode()))
                {
                    mLPDutySet.get(i).setSumPrem(mLPDutySet.get(i).getSumPrem() +
                                                 mLJSPayPersonSet.get(t).
                                                 getSumActuPayMoney());
                }
            }
            mLPDutySet.get(i).setModifyDate(CurrentDate);
            mLPDutySet.get(i).setModifyTime(CurrentTime);
        }
        for (int i = 1; i <= mLPGetSet.size(); i++)
        {
            mLPGetSet.get(i).setEdorNo(mLPEdorItemSchema.getEdorNo());
            mLPGetSet.get(i).setEdorType("TS");
        }
        for (int i = 1; i <= mLPPolSet.size(); i++)
        {
            mLPPolSet.get(i).setEdorNo(mLPEdorItemSchema.getEdorNo());
            mLPPolSet.get(i).setEdorType("TS");
            mLPPolSet.get(i).setPaytoDate(mNextPayToDate);
            for (int t = 1; t <= mLJSPayPersonSet.size(); t++)
            {
                if (mLJSPayPersonSet.get(t).getPolNo().equals(mLPPolSet.get(i).
                        getPolNo()))
                {
                    mLPPolSet.get(i).setSumPrem(mLPPolSet.get(i).getSumPrem() +
                                                mLJSPayPersonSet.get(t).
                                                getSumActuPayMoney());
                }
            }
            mLPPolSet.get(i).setAppFlag("1");
            mLPPolSet.get(i).setModifyDate(CurrentDate);
            mLPPolSet.get(i).setModifyTime(CurrentTime);
        }
        for (int i = 1; i <= mLJSPayPersonSet.size(); i++)
        {
            mLJSPayPersonSet.get(i).setGetNoticeNo(mLPEdorItemSchema.getEdorNo());
            mLJSPayPersonSet.get(i).setBankCode(mBankCode);
            mLJSPayPersonSet.get(i).setBankAccNo(mBankAccNo);
            mLJSPayPersonSet.get(i).setPayType("TS");
            mLJSPayPersonSet.get(i).setAgentCode(mLPContSchema.getAgentCode());
            mLJSPayPersonSet.get(i).setAgentGroup(mLPContSchema.getAgentGroup());
            mLJSPayPersonSet.get(i).setAgentCom(mLPContSchema.getAgentCom());
            mLJSPayPersonSet.get(i).setOperator(mGlobalInput.Operator);
            mLJSPayPersonSet.get(i).setMakeDate(CurrentDate);
            mLJSPayPersonSet.get(i).setModifyDate(CurrentDate);
            mLJSPayPersonSet.get(i).setMakeTime(CurrentTime);
            mLJSPayPersonSet.get(i).setModifyTime(CurrentTime);
            mSumMoney = mSumMoney + mLJSPayPersonSet.get(i).getSumDuePayMoney();
        }
        mLPContSchema.setPaytoDate(mNextPayToDate);

        mLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
        mLPContSchema.setEdorType("TS");

        if(mChangeFlag && mNeedReFlag){
            mLPContSchema.setState("206");
        }else if(mChangeFlag && !mNeedReFlag){
            mLPContSchema.setState("101");
        }

        mLPContSchema.setAppFlag("1");
        mLPContSchema.setModifyDate(CurrentDate);
        mLPContSchema.setModifyTime(CurrentTime);

        mLPEdorItemSchema.setEdorState("3");
        mLPEdorItemSchema.setGetMoney(mSumMoney);
        mLPEdorItemSchema.setEdorValiDate(PubFun.calDate(mLPEdorItemSchema.
                getEdorAppDate(), 1, "D", null));
        mLPEdorItemSchema.setPolNo("000000");
        mLPEdorItemSchema.setInsuredNo("000000");
        mLPEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
        mLPEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
        mBqCalBase.setContNo(mLPEdorItemSchema.getContNo());
        return true;
    }

    private boolean PrepareTSPayPerson(LCPremSet cLCPremSet, int cPayCount,
                                       String cRiskCode)
    {
        LJSPayPersonSet tLJSPayPersonSet = new LJSPayPersonSet();
        for (int i = 1; i <= cLCPremSet.size(); i++)
        {
            LCPremSchema tLCPremSchema = new LCPremSchema();
            tLCPremSchema = cLCPremSet.get(i).getSchema();
            String tPayToDate = tLCPremSchema.getPaytoDate();
            FDate tD = new FDate();
            FinFeePubFun tFinFeePubFun = new FinFeePubFun();
            Date PayToDate = tFinFeePubFun.calOFDate(tD.getDate(tPayToDate),
                    tLCPremSchema.getPayIntv() * (cPayCount - 1),
                    "M",
                    tD.getDate(tLCPremSchema.getPayEndDate()));
            tD = new FDate();
            tPayToDate = tD.getString(PayToDate);
            mPaytoDate = tPayToDate;
            String tFreeFlag = tLCPremSchema.getFreeFlag();
            if (tFreeFlag != null && tFreeFlag.equals("1") &&
                tLCPremSchema.getFreeStartDate().compareTo(tPayToDate) <=
                0 &&
                tLCPremSchema.getFreeEndDate().compareTo(tPayToDate) >=
                0
                    )
            {
                tLJSPayPersonSet.add(InitLJSPayPerson(tLCPremSchema, "HM",
                        cPayCount, cRiskCode, tPayToDate));
                tLJSPayPersonSet.add(InitLJSPayPerson(tLCPremSchema, "ZC",
                        cPayCount, cRiskCode, tPayToDate));
            }
            else
            {
                tLJSPayPersonSet.add(InitLJSPayPerson(tLCPremSchema, "ZC",
                        cPayCount, cRiskCode, tPayToDate));
            }
        }
        //营改增 add zhangyingfeng 2016-07-14
        //价税分离 计算器
        TaxCalculator.calBySchemaSet(tLJSPayPersonSet);
        //end zhangyingfeng 2016-07-14
        mLJSPayPersonSet.add(tLJSPayPersonSet);
        return true;
    }

    private LJSPayPersonSet InitLJSPayPerson(LCPremSchema cLCPremSchema,
                                             String cCreatType, int cPayCount,
                                             String cRiskCode,
                                             String cPayToDate)
    {
        LJSPayPersonSet tLJSPayPersonSet = new LJSPayPersonSet();
        LJSPayPersonSchema tLJSPayPersonSchema = new LJSPayPersonSchema();
        tLJSPayPersonSchema.setGrpContNo("00000000000000000000");
        tLJSPayPersonSchema.setContNo(cLCPremSchema.getContNo());
        
        tLJSPayPersonSchema.setPolNo(cLCPremSchema.getPolNo());
        tLJSPayPersonSchema.setAppntNo(cLCPremSchema.getAppntNo());
        tLJSPayPersonSchema.setGetNoticeNo(mLPEdorItemSchema.getEdorNo());
        tLJSPayPersonSchema.setPayCount(cLCPremSchema.getPayTimes() + cPayCount);
        tLJSPayPersonSchema.setDutyCode(cLCPremSchema.getDutyCode());
        tLJSPayPersonSchema.setPayPlanCode(cLCPremSchema.getPayPlanCode());
        tLJSPayPersonSchema.setManageCom(cLCPremSchema.getManageCom());
        tLJSPayPersonSchema.setPayIntv(cLCPremSchema.getPayIntv());
        tLJSPayPersonSchema.setPayAimClass("1");
        tLJSPayPersonSchema.setLastPayToDate(cPayToDate);
        FDate tD = new FDate();
        Date tCurPayToDate = FinFeePubFun.calOFDate(tD.getDate(cPayToDate),
                cLCPremSchema.getPayIntv(),
                "M", tD.getDate(cLCPremSchema.getPayEndDate()));
        tLJSPayPersonSchema.setCurPayToDate(tCurPayToDate);
        mNextPayToDate = tLJSPayPersonSchema.getCurPayToDate();
        if (cCreatType.equals("HM"))
        {
            tLJSPayPersonSchema.setSumActuPayMoney( -cLCPremSchema.getPrem() *
                    cLCPremSchema.getFreeRate());
            tLJSPayPersonSchema.setSumDuePayMoney( -cLCPremSchema.getPrem() *
                                                  cLCPremSchema.getFreeRate());
        }
        else
        {
            tLJSPayPersonSchema.setSumActuPayMoney(cLCPremSchema.getPrem());
            tLJSPayPersonSchema.setSumDuePayMoney(cLCPremSchema.getPrem());
        }
        tLJSPayPersonSchema.setPayType(cCreatType);
        tLJSPayPersonSchema.setPayDate(mPayDate);
        tLJSPayPersonSchema.setBankCode(mBankCode);
        tLJSPayPersonSchema.setBankAccNo(mBankAccNo);
        tLJSPayPersonSchema.setBankOnTheWayFlag("0");
        tLJSPayPersonSchema.setBankSuccFlag("0");
        tLJSPayPersonSchema.setRiskCode(cRiskCode);
        tLJSPayPersonSchema.setOperator(mGlobalInput.Operator);
        tLJSPayPersonSchema.setMakeDate(CurrentDate);
        tLJSPayPersonSchema.setMakeTime(CurrentTime);
        tLJSPayPersonSchema.setModifyDate(CurrentDate);
        tLJSPayPersonSchema.setModifyTime(CurrentTime);
        tLJSPayPersonSchema.setGrpPolNo("00000000000000000000");
        tLJSPayPersonSchema.setCurrency(cLCPremSchema.getCurrency());
        FYDate t = new FYDate(cPayToDate);
        String MainPolYearSql = "Select trunc(Months_between('?Date?',lcpol.CValiDate)/12)+1 From lcpol Where polno = (select mainpolno from lcpol where polno='?polno?')";
        SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
        sqlbv1.sql(MainPolYearSql);
        sqlbv1.put("Date", t.getOracleDate());
        sqlbv1.put("polno", cLCPremSchema.getPolNo());
        SSRS tSSRS = new SSRS();
        ExeSQL tExeSQL = new ExeSQL();
        tSSRS = tExeSQL.execSQL(sqlbv1);
        String tMainPolyear = tSSRS.GetText(1, 1);
        tLJSPayPersonSchema.setMainPolYear(tMainPolyear);
        
        tLJSPayPersonSet.add(tLJSPayPersonSchema);
        return tLJSPayPersonSet;
    }

    private boolean GetLPPrem(LCPolSchema cLCPolSchema, String cState,
                              String cRiskCode)
    {
        LCPremSet tLCPremSet = new LCPremSet();
        LCPremDB tLCPremDB = new LCPremDB();
        String i_sql = "select * from LCPrem where PolNo = '?PolNo?' and PayStartDate <= to_date('?PaytoDate?','YYYY-MM-DD') and PayEndDate >= to_date('?PaytoDate?','YYYY-MM-DD') and UrgePayFlag = 'Y'";
        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
        sqlbv.sql(i_sql);
        sqlbv.put("PolNo", cLCPolSchema.getPolNo());      
        sqlbv.put("PaytoDate", cLCPolSchema.getPaytoDate());
        logger.debug("查询保费项表===============" + i_sql);
        tLCPremSet = tLCPremDB.executeQuery(sqlbv);
        if (tLCPremSet.size() == 0)
        {
            CError.buildErr(this, "保费项表中没有对应催交信息");
            return false;
        }

        int tTimes = 0;
        if (mActuPaytime > mPaySTime)
        {
            CError.buildErr(this, "输入的复缴期数大于欠缴期数");
            return false;
        }
        else if (mActuPaytime <= 0)
        {
            CError.buildErr(this, "输入的复缴期数应大于0");
            return false;
        }
        else
        {
            tTimes = mActuPaytime;
        }
        for (int i = 1; i <= tTimes; i++)
        {
            if (!PrepareTSPayPerson(tLCPremSet, i, cRiskCode))
            {
                return false;
            }
        }

        LPPremSet tLPPremSet = new LPPremSet();
        for (int i = 1; i <= tLCPremSet.size(); i++)
        {
            LCPremSchema tLCPremSchema = new LCPremSchema();
            tLCPremSchema = tLCPremSet.get(i).getSchema();
            LPPremSchema tLPPremSchema = new LPPremSchema();
            Reflections tReflections = new Reflections();
            tReflections.transFields(tLPPremSchema, tLCPremSchema);
            tLPPremSet.add(tLPPremSchema);
        }
        mLPPremSet.add(tLPPremSet);
        return true;
    }

    private boolean GetLPGet(String cPolNo, String cState)
    {
        LCGetDB tLCGetDB = new LCGetDB();
        tLCGetDB.setPolNo(cPolNo);
        LCGetSet tLCGetSet = new LCGetSet();
        tLCGetSet = tLCGetDB.query();
        if (tLCGetSet.size() == 0)
        {
            CError.buildErr(this, "该保单不能复缴!未能找到有效的领取信息");
            return false;
        }
        LPGetSet tLPGetSet = new LPGetSet();
        for (int i = 1; i <= tLCGetSet.size(); i++)
        {
            LCGetSchema tLCGetSchema = new LCGetSchema();
            tLCGetSchema = tLCGetSet.get(i).getSchema();
            LPGetSchema tLPGetSchema = new LPGetSchema();
            Reflections tReflections = new Reflections();
            tReflections.transFields(tLPGetSchema, tLCGetSchema);
            tLPGetSet.add(tLPGetSchema);
        }
        mLPGetSet.add(tLPGetSet);
        return true;
    }

    private boolean GetLPPol(LCPolSchema cLCPolSchema, String cState)
    {
        Reflections tReflections = new Reflections();
        LPPolSchema tLPPolSchema = new LPPolSchema();
        tReflections.transFields(tLPPolSchema, cLCPolSchema);
        tLPPolSchema.setPaytoDate(mPaytoDate);
        mLPPolSet.add(tLPPolSchema);
        return true;
    }


    private boolean GetLJSGetEndose()
    {
        for (int i = 1; i <= mLJSPayPersonSet.size(); i++)
        {
        	LJSPayPersonSchema tLJSPayPersonSchema = new LJSPayPersonSchema();
        	tLJSPayPersonSchema = mLJSPayPersonSet.get(i).getSchema();
            LJSGetEndorseSchema tLJSGetEndoseSchema = new LJSGetEndorseSchema();
            tLJSGetEndoseSchema.setGetNoticeNo(mLPEdorItemSchema.getEdorNo());
            tLJSGetEndoseSchema.setEndorsementNo(mLPEdorItemSchema.getEdorNo());
            tLJSGetEndoseSchema.setFeeOperationType("TS");
            tLJSGetEndoseSchema.setFeeFinaType("BF");
            tLJSGetEndoseSchema.setGrpContNo("00000000000000000000");
            tLJSGetEndoseSchema.setContNo(mLPEdorItemSchema.getContNo());
            tLJSGetEndoseSchema.setPolNo(tLJSPayPersonSchema.getPolNo());
            tLJSGetEndoseSchema.setOtherNo(mLPEdorItemSchema.getEdorNo());
            tLJSGetEndoseSchema.setOtherNoType("3");
            tLJSGetEndoseSchema.setAppntNo(mLPContSchema.getAppntNo());
            tLJSGetEndoseSchema.setInsuredNo(mLPContSchema.getAppntNo());
            tLJSGetEndoseSchema.setGetMoney(tLJSGetEndoseSchema.getGetMoney() +tLJSPayPersonSchema.getSumActuPayMoney());
            tLJSGetEndoseSchema.setKindCode("L");
            tLJSGetEndoseSchema.setGetFlag("0");
            tLJSGetEndoseSchema.setRiskCode(tLJSPayPersonSchema.getRiskCode());
            tLJSGetEndoseSchema.setDutyCode(tLJSPayPersonSchema.getDutyCode());
            tLJSGetEndoseSchema.setPayPlanCode(tLJSPayPersonSchema.getPayPlanCode());
            tLJSGetEndoseSchema.setManageCom(tLJSPayPersonSchema.getManageCom());
            tLJSGetEndoseSchema.setAgentCom(tLJSPayPersonSchema.getAgentCom());
            tLJSGetEndoseSchema.setAgentType(tLJSPayPersonSchema.getAgentType());
            tLJSGetEndoseSchema.setAgentCode(tLJSPayPersonSchema.getAgentCode());
            tLJSGetEndoseSchema.setSubFeeOperationType(BqCode.Pay_Prem+tLJSPayPersonSchema.getPayCount());
            tLJSGetEndoseSchema.setOperator(mGlobalInput.Operator);
            tLJSGetEndoseSchema.setMakeDate(CurrentDate);
            tLJSGetEndoseSchema.setMakeTime(CurrentTime);
            tLJSGetEndoseSchema.setModifyDate(CurrentDate);
            tLJSGetEndoseSchema.setModifyTime(CurrentTime);
            tLJSGetEndoseSchema.setCurrency(tLJSPayPersonSchema.getCurrency());
            //营改增 add zhangyingfeng 2016-07-14
            //价税分离 计算器
            TaxCalculator.calBySchema(tLJSGetEndoseSchema);
            //end zhangyingfeng 2016-07-14
            mLJSGetEndorseSet.add(tLJSGetEndoseSchema);
        }
        return true;
    }

    private boolean GetNotEnoughTimes(String cStartDate, String cCurDate,
                                      int cPayIntv, String cPolNo,
                                      String cState)
    {
        logger.debug("cStartDate=======================" + cStartDate);
        logger.debug("cCurDate=======================" + cCurDate);
        logger.debug("cPayIntv=======================" + cPayIntv);

        cStartDate = BqNameFun.delTime(cStartDate);
        cCurDate = BqNameFun.delTime(cCurDate);

        String sql = "select ceil(ceil(Months_between('?cCurDate?','?cStartDate?'))/?cPayIntv?) from dual";
        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
        sqlbv.sql(sql);
        sqlbv.put("cCurDate", cCurDate);
        sqlbv.put("cStartDate", cStartDate);
        sqlbv.put("cPayIntv", cPayIntv);
        ExeSQL tExeSQL = new ExeSQL();

        String ntimes = tExeSQL.getOneValue(sqlbv);
        if(ntimes == null || "".equals(ntimes)){
            ntimes = "1";
        }
        mPaySTime = Integer.parseInt(ntimes);

//        int NotEnoughTimes = 0;
//        int intv_M = PubFun.calInterval2(cStartDate, cCurDate, "M");
//        NotEnoughTimes = intv_M / cPayIntv;
////        if ((intv_M % cPayIntv) > 0)
////        {
////            NotEnoughTimes++;
////        }
//        if (NotEnoughTimes == 0)
//            mPaySTime = 1;
//        else
//            mPaySTime = NotEnoughTimes;
        return true;
    }

    private boolean ChangeState(String cPolNo, String cStateReason,
                                String cStateType, boolean cFlag){
        Reflections tReflections = new Reflections();
        String tSql = "select * from lccontstate where statetype='?cStateType?' and statereason='?cStateReason?' and polno='?cPolNo?' and enddate is null and startdate <> '?mNextPayToDate?'" ;
        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
        sqlbv.sql(tSql);
        sqlbv.put("cStateType", cStateType);
        sqlbv.put("cStateReason", cStateReason);
        sqlbv.put("cPolNo", cPolNo);
        sqlbv.put("mNextPayToDate", mNextPayToDate);
        LCContStateDB tLCContStateDB = new LCContStateDB();
        LCContStateSet tLCContStateSet = new LCContStateSet();
        tLCContStateSet = tLCContStateDB.executeQuery(sqlbv);
        for (int i = 1; i <= tLCContStateSet.size(); i++){
            LCContStateSchema tLCContStateSchema = new LCContStateSchema();
            tLCContStateSchema = tLCContStateSet.get(i).getSchema();
            LPContStateSchema tLPContStateSchema = new LPContStateSchema();
            tReflections.transFields(tLPContStateSchema, tLCContStateSchema);
            mLPContStateSet.add(tLPContStateSchema);
        }
        LPContStateSet xLPContStateSet = new LPContStateSet();
        for (int i = 1; i <= mLPContStateSet.size(); i++){
            mLPContStateSet.get(i).setEdorNo(mLPEdorItemSchema.getEdorNo());
            mLPContStateSet.get(i).setEdorType(mLPEdorItemSchema.getEdorType());
            if (cFlag){
                mLPContStateSet.get(i).setEndDate(PubFun.calDate(mLPEdorItemSchema.getEdorValiDate(), -1, "D", null));
            }else{
                mLPContStateSet.get(i).setEndDate(PubFun.calDate(mLPEdorItemSchema.getEdorValiDate(), -1, "D", null));
                mLPContStateSet.get(i).setModifyDate(CurrentDate);
                mLPContStateSet.get(i).setModifyTime(CurrentTime);
                LPContStateSchema xLPContStateSchema = new LPContStateSchema();
                tReflections.transFields(xLPContStateSchema,mLPContStateSet.get(i));
                xLPContStateSchema.setStartDate(mNextPayToDate);
                xLPContStateSchema.setEndDate("");
                xLPContStateSchema.setMakeDate(CurrentDate);
                xLPContStateSchema.setMakeTime(CurrentTime);
                xLPContStateSchema.setModifyDate(CurrentDate);
                xLPContStateSchema.setModifyTime(CurrentTime);
                xLPContStateSet.add(xLPContStateSchema);
            }
        }
        mLPContStateSet.add(xLPContStateSet);
        return true;
    }

    /**
     * 数据计算处理
     *
     * @return boolean
     */
    private boolean dealData()
    {
        //删除上次保存的数据

        deleteData();
        LCContDB tLCContDB = new LCContDB();
        tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
        tLCContDB.getInfo();
        LCContSchema tLCContSchema = new LCContSchema();
        tLCContSchema = tLCContDB.getSchema();
        mLPContSchema = new LPContSchema();
        Reflections tRef = new Reflections();
        tRef.transFields(mLPContSchema, tLCContSchema);

        mPolNos = (String) mTransferData.getValueByName("PolNos");
        if (mPolNos == null || "".equals(mPolNos))
        {
            CError.buildErr(this, "传输复缴保单险种失败(LCCont.PolNo)!");
            return false;
        }

        String tActuPaytime = (String) mTransferData.getValueByName(
                "ActuPaytime");
        logger.debug("实际复缴期数共=================" + tActuPaytime);
        mActuPaytime = Integer.parseInt(tActuPaytime);

        mLPEdorItemSchema.setStandbyFlag2(tActuPaytime);

        String tSql = "select * from lcpol where contno='?contno?' and polno in (?mPolNos?)";
        SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
        sqlbv1.sql(tSql);
        sqlbv1.put("contno", mLPEdorItemSchema.getContNo());
        sqlbv1.put("mPolNos", mPolNos);
        LCPolSet tLCPolSet = new LCPolSet();
        LCPolDB tLCPolDB = new LCPolDB();
        tLCPolSet = tLCPolDB.executeQuery(sqlbv1);
        logger.debug("查询险种信息: " + tSql);
        if (tLCPolSet.size() == 0)
        {
            CError.buildErr(this, "未能查到复交的险种信息");
            return false;
        }
        for (int i = 1; i <= tLCPolSet.size(); i++)
        {
            LCPolSchema tLCPolSchema = new LCPolSchema();
            tLCPolSchema = tLCPolSet.get(i).getSchema();
            String tStateType = "";
            if (tLCPolSchema.getPolNo().equals(tLCPolSchema.getMainPolNo()))
            {
                tStateType = "PH";
            }
            else
            {
                tStateType = "Available";
            }
            state = GetStateData(tLCPolSchema.getPolNo(), tStateType,
                                 "StateReason");
            if (state == "FAILED")
            {
                CError.buildErr(this, "未能查到保单停效原因(LCCont.State)!");
                return false;
            }
            try
            {
                //查询所欠期数
                String tStartDate = "";
                String tEndDate = "";
                int tPayIntv = 0;
                tStartDate = tLCPolSchema.getPaytoDate();
                FDate tFDate = new FDate();
                tPayIntv = tLCPolSchema.getPayIntv();
                if (tLCPolSchema.getRnewFlag() == -1)
                {
                    String tempSql =
                            "select payenddate from lcpol where polno=mainpolno and contno='?contno?'";
                    SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
                    sqlbv2.sql(tempSql);
                    sqlbv2.put("contno", mLPEdorItemSchema.getContNo());
                    ExeSQL tExeSQL = new ExeSQL();
                    String tPayEndDate = tExeSQL.getOneValue(sqlbv2);
                    if (tFDate.getDate(mLPEdorItemSchema.getEdorValiDate()).
                        before(tFDate.getDate(tPayEndDate)))
                    {
                        tEndDate = mLPEdorItemSchema.getEdorValiDate();
                    }
                    else
                    {
                        tEndDate = tPayEndDate;
                    }
                }
                else
                {
                    if (tFDate.getDate(mLPEdorItemSchema.getEdorValiDate()).
                        before(
                                tFDate.getDate(tLCPolSchema.getPayEndDate())))
                    {
                        tEndDate = mLPEdorItemSchema.getEdorValiDate();
                    }
                    else
                    {
                        tEndDate = tLCPolSchema.getPayEndDate();
                    }
                }
                logger.debug( "=================GetNotEnoughTimes==================");
                if (!GetNotEnoughTimes(tStartDate, tEndDate, tPayIntv,
                                       tLCPolSchema.getPolNo(), state))
                {
                    CError.buildErr(this, "计算实际欠缴期数失败");
                    return false;
                }

                if (!GetLPDuty(tLCPolSchema.getPolNo(), state))
                {
                    return false;
                }
                logger.debug( "=================GetLPPrem==================");
                if (!GetLPPrem(tLCPolSchema, state,
                               tLCPolSchema.getRiskCode()))
                {
                    return false;
                }
                if (!GetLPGet(tLCPolSchema.getPolNo(), state))
                {
                    return false;
                }

                //LPPol
                if (!GetLPPol(tLCPolSchema, state))
                {
                    return false;
                }

                if (!NeedReCheck(tLCPolSchema))
                {
                    return false;
                }

                logger.debug("实际复缴期数================" + mActuPaytime);
                logger.debug("应复缴期数================" + mPaySTime);

                if (mActuPaytime - mPaySTime >= 0)
                {
                    mChangeFlag = true;
                }
                else
                {
                    mChangeFlag = false;
                }

                logger.debug(  "=================AfterDeal==================");
                if (!AfterDeal())
                {
                    CError.buildErr(this, "后续处理失败");
                    return false;
                }

                if (!GetLJSGetEndose())
                {
                    CError.buildErr(this, "生成批改补退费表失败");
                    return false;
                }

                if (!CreatPAcc())
                {
                    CError.buildErr(this, "处理帐户备份表失败");
                    return false;
                }

                ChangeState(tLCPolSchema.getPolNo(), state, "PH", mChangeFlag);

            }catch (Exception e){
                return false;
            }

            String isTL = BqNameFun.getAnother(null,"LMRiskApp", "RiskType3 = '3' and riskcode", tLCPolSchema.getRiskCode(), "'X'");
            //投连才校验
            if(!mChangeFlag && "X".equals(isTL)){//如果一次不还清的话要校验，还清就不校验了
              //复缴后账户值估算余额应大于12倍保单月费用且同时大于1000元
              //取保全申请日上次的价格估算帐户估值
              ExeSQL tExeSQL = new ExeSQL();
              SSRS tSSRS = new SSRS();
              String tStartDate = "";
              tSql = "select min(Startdate)  from loaccunitprice where riskcode= (select riskcode from Lcpol where  polno='?polno?') and startdate>='?startdate?'";
              SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
              sqlbv3.sql(tSql);
              sqlbv3.put("polno", tLCPolSchema.getPolNo());
              sqlbv3.put("startdate", mLPEdorItemSchema.getEdorAppDate());
              tSSRS = tExeSQL.execSQL(sqlbv3);
              if (tSSRS != null && tSSRS.MaxRow > 0 && !tSSRS.GetText(1, 1).equals("")) {
                tStartDate = tSSRS.GetText(1, 1);
              }
              else {
                tSql = "select max(Startdate)  from loaccunitprice where riskcode= (select riskcode from Lcpol where  polno='?polno?') and startdate<='?startdate?'";
                SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
                sqlbv4.sql(tSql);
                sqlbv4.put("polno", tLCPolSchema.getPolNo());
                sqlbv4.put("startdate", mLPEdorItemSchema.getEdorAppDate());
                tSSRS = new SSRS();
                tSSRS = tExeSQL.execSQL(sqlbv4);
                if (tSSRS != null && tSSRS.MaxRow > 0 && !tSSRS.GetText(1, 1).equals("")) {
                  tStartDate = tSSRS.GetText(1, 1);
                }
                else {
                  tStartDate = PubFun.getCurrentDate();
                }
              }
              tSql = "select sum(round(a.UnitPriceSell*b.unitcount,2)) from Loaccunitprice a, Lcinsureacc b where  a.StartDate='?tStartDate?' and  b.polno='?polno?' and   a.insuaccno=b.insuaccno and a.riskcode=b.riskcode";
              SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
              sqlbv5.sql(tSql);
              sqlbv5.put("tStartDate", tStartDate);
              sqlbv5.put("polno", tLCPolSchema.getPolNo());         
              if ("".equals(tExeSQL.getOneValue(sqlbv5))) {
                // 如果计算不出来，只能取出帐户的现金值。这种情况一般不会出现，除非价格表Loaccunitprice没有数据
                tSql = "select (case when sum(insuaccbala) is not null then sum(insuaccbala) else 0 end) from LCInsureAcc where polno ='?polno?'";
                sqlbv5.sql(tSql);
                sqlbv5.put("polno", tLCPolSchema.getPolNo());
              }
              mSumAccMoney += Double.parseDouble(tExeSQL.getOneValue(sqlbv5));

              // 取上月保单月费用
              tSql = "select sum(fee),paydate  from lcinsureaccfeetrace where contno ='?contno?' and feecode in (select code1 from ldcode1 where codetype='TS' and code='riskfee') group by paydate order by paydate desc";
              SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
              sqlbv6.sql(tSql);
              sqlbv6.put("contno", mLPEdorItemSchema.getContNo());
              tSSRS = tExeSQL.execSQL(sqlbv6, 1, 1);
              double tGLMoney = 0;
              if (!tExeSQL.mErrors.needDealError() && tSSRS.getMaxRow() > 0) {
                  tGLMoney = 12 * Double.parseDouble(tSSRS.GetText(1, 1));
              }

              if (tGLMoney < 1000) {
                tGLMoney = 1000;
              }
              if (mSumAccMoney < tGLMoney) {
                CError.buildErr(this, "复缴后，账户值估算余额应大于12倍保单月费用且同时大于1000元");
                return false;
              }
            }
        }
        mResult.clear();
        return true;
    }

    //删除上次保存的数据
    private boolean deleteData()
    {
        String deleteLPPol = "delete from lppol where edorno='?edorno?' and edorType='TS'";
        SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
        sqlbv1.sql(deleteLPPol);
        sqlbv1.put("edorno", mLPEdorItemSchema.getEdorNo());
        String deletelpduty = "delete from lpduty where edorno='?edorno?' and edorType='TS'";
        SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
        sqlbv2.sql(deletelpduty);
        sqlbv2.put("edorno", mLPEdorItemSchema.getEdorNo());
        String deletelpprem = "delete from lpget where edorno='?edorno?' and edorType='TS'";
        SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
        sqlbv3.sql(deletelpprem);
        sqlbv3.put("edorno", mLPEdorItemSchema.getEdorNo());
        String deletelpget = "delete from lpprem where edorno='?edorno?' and edorType='TS'";
        SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
        sqlbv4.sql(deletelpget);
        sqlbv4.put("edorno", mLPEdorItemSchema.getEdorNo());
        String deleteLPContState = "delete from lpcontstate where edorno='?edorno?' and edorType='TS'";
        SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
        sqlbv5.sql(deleteLPContState);
        sqlbv5.put("edorno", mLPEdorItemSchema.getEdorNo());
        String deleteLPInsureAccTrace =
                "delete from LPInsureAccTrace where edorno='?edorno?' and edorType='TS'";
        SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
        sqlbv6.sql(deleteLPInsureAccTrace);
        sqlbv6.put("edorno", mLPEdorItemSchema.getEdorNo());
        String deleteLPInsureAcc = "delete from LPInsureAcc where edorno='?edorno?' and edorType='TS'";
        SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
        sqlbv7.sql(deleteLPInsureAcc);
        sqlbv7.put("edorno", mLPEdorItemSchema.getEdorNo());
        String deleteLPInsureAccClass =
                "delete from LPInsureAccClass where edorno='?edorno?' and edorType='TS'";
        SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
        sqlbv8.sql(deleteLPInsureAccClass);
        sqlbv8.put("edorno", mLPEdorItemSchema.getEdorNo());
        String deletelPInsureAccFeeTrace =
                "delete from lPInsureAccFeeTrace where edorno='?edorno?' and edorType='TS'";
        SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
        sqlbv9.sql(deletelPInsureAccFeeTrace);
        sqlbv9.put("edorno", mLPEdorItemSchema.getEdorNo());
        String deleteLPInsureAccClassFee =
                "delete from LPInsureAccClassFee where edorno='?edorno?' and edorType='TS'";
        SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
        sqlbv10.sql(deleteLPInsureAccClassFee);
        sqlbv10.put("edorno", mLPEdorItemSchema.getEdorNo());
        String deleteLPInsureAccFee =
                "delete from LPInsureAccFee where edorno='?edorno?' and edorType='TS'";
        SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
        sqlbv11.sql(deleteLPInsureAccFee);
        sqlbv11.put("edorno", mLPEdorItemSchema.getEdorNo());

        String deleteLJSGetendors =
                "delete from ljsgetendorse where feeoperationtype ='?feeoperationtype?' and endorsementno='?endorsementno?'";
        SQLwithBindVariables sqlbv12=new SQLwithBindVariables();
        sqlbv12.sql(deleteLJSGetendors);
        sqlbv12.put("feeoperationtype", mLPEdorItemSchema.getEdorType());
        sqlbv12.put("endorsementno", mLPEdorItemSchema.getEdorNo());
        String deleteLJSPayPerson =
                "delete from ljspayperson where paytype like concat('?paytype?','%') and GetNoticeNo='?GetNoticeNo?'";
        SQLwithBindVariables sqlbv13=new SQLwithBindVariables();
        sqlbv13.sql(deleteLJSPayPerson);
        sqlbv13.put("paytype", mLPEdorItemSchema.getEdorType());
        sqlbv13.put("GetNoticeNo", mLPEdorItemSchema.getEdorNo());
        String deletelploan = "delete from lploan where edorno='?edorno?' and edorType='TS'";
        SQLwithBindVariables sqlbv14=new SQLwithBindVariables();
        sqlbv14.sql(deletelploan);
        sqlbv14.put("edorno", mLPEdorItemSchema.getEdorNo());
        String deletelpreturnloan = "delete from lpreturnloan where edorno='?edorno?' and edorType='TS'";
        SQLwithBindVariables sqlbv15=new SQLwithBindVariables();
        sqlbv15.sql(deletelpreturnloan);
        sqlbv15.put("edorno", mLPEdorItemSchema.getEdorNo());
        String deleteLJEndorseDetail =
            "delete from LJEndorseDetail where feeoperationtype ='?feeoperationtype?' and endorsementno='?endorsementno?'";
        SQLwithBindVariables sqlbv16=new SQLwithBindVariables();
        sqlbv16.sql(deleteLJEndorseDetail);
        sqlbv16.put("feeoperationtype", mLPEdorItemSchema.getEdorType());
        sqlbv16.put("endorsementno", mLPEdorItemSchema.getEdorNo());
        map.put(sqlbv1, "DELETE");
        map.put(sqlbv2, "DELETE");
        map.put(sqlbv3, "DELETE");
        map.put(sqlbv4, "DELETE");
        map.put(sqlbv5, "DELETE");
        map.put(sqlbv13, "DELETE");
        map.put(sqlbv12, "DELETE");
        map.put(sqlbv14, "DELETE");
        map.put(sqlbv15, "DELETE");

        map.put(sqlbv6, "DELETE");
        map.put(sqlbv7, "DELETE");
        map.put(sqlbv8, "DELETE");
        map.put(sqlbv9, "DELETE");
        map.put(sqlbv10, "DELETE");
        map.put(sqlbv11, "DELETE");
        return true;
    }

    private boolean NeedReCheck(LCPolSchema cLCPolSchema)
    {
        String tSql =
                "select * from ljspayperson where paytype='ZC' and contno='?contno?' and polno<>'?polno?'";
        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
        sqlbv.sql(tSql);
        sqlbv.put("contno", cLCPolSchema.getContNo());
        sqlbv.put("polno", cLCPolSchema.getMainPolNo());
        LJSPayPersonDB tLJSPayPersonDB = new LJSPayPersonDB();
        LJSPayPersonSet tLJSPayPersonSet = new LJSPayPersonSet();
        tLJSPayPersonSet = tLJSPayPersonDB.executeQuery(sqlbv);
        if (tLJSPayPersonSet.size() > 0)
        {
            for (int i = 1; i <= tLJSPayPersonSet.size(); i++)
            {
                tSql = "select * from lccontstate where contno='?contno?' and exists (select 1 from lcpol where polno=lccontstate.polno and polno<>mainpolno and paytodate='?paytodate?' and riskcode = '?riskcode?') and statetype='Available' and state='1' and enddate is null";
                SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
                sqlbv1.sql(tSql);
                sqlbv1.put("contno", tLJSPayPersonSet.get(i).getContNo());
                sqlbv1.put("paytodate", tLJSPayPersonSet.get(i).getLastPayToDate());
                sqlbv1.put("riskcode", tLJSPayPersonSet.get(i).getRiskCode());
                LCContStateDB tLCContStateDB = new LCContStateDB();
                LCContStateSet tLCContStateSet = new LCContStateSet();
                logger.debug("tSql==================" + tSql);
                tLCContStateSet = tLCContStateDB.executeQuery(sqlbv1);
                if (tLCContStateSet.size() > 0)
                {
                    mNeedReFlag = true;
                }
                else
                {
                    mNeedReFlag = false;
                }
            }
        }
        else
        {
            mNeedReFlag = false;
        }

        return true;
    }

    
    private boolean PrepareData()
    {
//        map.put("delete from ljspayperson where contno='" +
//                mLPEdorItemSchema.getContNo() + "' and paytype='ZC'", "DELETE");
//        map.put("delete from ljspay where otherno='" +
//                mLPEdorItemSchema.getContNo() +
//                "' and othernotype in ('2','3')", "DELETE");
        map.put(mLJSGetEndorseSet, "DELETE&INSERT");
        map.put(mLJSPayPersonSet, "DELETE&INSERT");
        map.put(mLPContSchema, "DELETE&INSERT");
        map.put(mLPContStateSet, "DELETE&INSERT");
        map.put(mLPPolSet, "DELETE&INSERT");
        map.put(mLPDutySet, "DELETE&INSERT");
        map.put(mLPPremSet, "DELETE&INSERT");
        map.put(mLPGetSet, "DELETE&INSERT");
        map.put(mLPEdorItemSchema, "UPDATE");

        /////////////////
        // 帐户表
        /////////////////
        map.put(mLPInsureAccClassFeeSet, "DELETE&INSERT");
		//add zhangyingfeng 2016-08-03
		//营改增 价税分离计算器
		TaxCalculator.calBySchemaSet(mLPInsureAccFeeTraceSet);
		//end zhangyingfeng 2016-08-03
        map.put(mLPInsureAccFeeTraceSet, "DELETE&INSERT");
        map.put(mLPInsureAccFeeSet, "DELETE&INSERT");
        map.put(mLPInsureAccClassSet, "DELETE&INSERT");
        map.put(mLPInsureAccTraceSet, "DELETE&INSERT");
        map.put(mLPInsureAccSet, "DELETE&INSERT");
        ////////////////////
        SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
        sqlbv1.sql("update LPInsureAccClassFee d set d.fee=d.fee + (select sum(g.fee) from LPInsureAccFeeTrace g where edorno=d.edorno and payplancode=d.payplancode and polno=d.polno and INSUACCNO=d.INSUACCNO and feecode=d.feecode) where edorno = '?edorno?'");
        sqlbv1.put("edorno", mLPEdorItemSchema.getEdorNo());
        map.put(sqlbv1, "UPDATE");
        
        SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
        sqlbv2.sql("update LPInsureAccFee d set d.fee= d.fee + (select sum(g.fee) from LPInsureAccFeeTrace g where edorno=d.edorno and polno=d.polno and INSUACCNO=d.INSUACCNO) where edorno = '?edorno?'");
        sqlbv2.put("edorno", mLPEdorItemSchema.getEdorNo());
        map.put(sqlbv2, "UPDATE");
        
        SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
        sqlbv3.sql("update LPInsureAccClass y set y.sumpay=y.sumpay+(select sum(t.money)from LPInsureAccTrace t where edorno = y.edorno and payplancode = y.payplancode and INSUACCNO = y.INSUACCNO and polno=y.polno) where edorno = '?edorno?'");
        sqlbv3.put("edorno", mLPEdorItemSchema.getEdorNo());
        map.put(sqlbv3, "UPDATE");
        
        SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
        sqlbv4.sql("update LPInsureAcc y set y.sumpay=y.sumpay+(select sum(t.money)from LPInsureAccTrace t where edorno = y.edorno and INSUACCNO = y.INSUACCNO and polno=y.polno) where edorno = '?edorno?'");
        sqlbv4.put("edorno", mLPEdorItemSchema.getEdorNo());
        map.put(sqlbv4, "UPDATE");

        mResult.add(map);
        mResult.add(mBqCalBase);
        return true;
    }
}
