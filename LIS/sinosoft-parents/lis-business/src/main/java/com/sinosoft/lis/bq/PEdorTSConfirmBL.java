package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.tb.*;

import java.util.*;

import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.operfee.IndiDueFeeCancelBL;

/**
 * <p>Title: 保全项目投连万能明细</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: Sinosoft</p>
 * @author ck gaoht
 * @version 1.0
 */

public class PEdorTSConfirmBL
        implements EdorConfirm
{
private static Logger logger = Logger.getLogger(PEdorTSConfirmBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData;
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;
    /**  */
    private LJSPaySchema mLJSPaySchema = new LJSPaySchema();
    private LJSPaySet mLJSPaySet = new LJSPaySet();
    private LJSPayPersonSet mLJSPayPersonSet = new LJSPayPersonSet();
    private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
    private LCPolSchema mLCPolSchema = new LCPolSchema();
    private LCPremSet mLCPremSet = new LCPremSet();
    private LCPremSet mLCPremNewSet = new LCPremSet();
    private LCDutySet mLCDutySet = new LCDutySet();
    private LCDutySet mLCDutyNewSet = new LCDutySet();
    private LJAGetEndorseSet mLJAGetEndorseSet = new LJAGetEndorseSet();
    private LPPolSchema mLPPolSchema = new LPPolSchema();
    private LCInsureAccSet mLCInsureAccSet = new LCInsureAccSet();
    private LCInsureAccTraceSet mLCInsureAccTraceSet = new LCInsureAccTraceSet();
    private LJAPayPersonSet mLJAPayPersonSet = new LJAPayPersonSet();
    private LJAPaySchema mLJAPaySchema = new LJAPaySchema();
    private LJAPaySet mLJAPaySet = new LJAPaySet();
    private Reflections mRef = new Reflections();
    private LCContSchema mLCContSchema = new LCContSchema();
    private Reflections mReflections = new Reflections();
    // private String mPaytoDate; //新的交至日期
    /** 全局数据 */
    private GlobalInput mGlobalInput = new GlobalInput();
    private String mLimit;
    private String mSerialNo = "";
    private String mActGetNo = "";
    private MMap mMap = new MMap();

    public PEdorTSConfirmBL()
    {
    }

    /**
     * 传输数据的公共方法
     *
     * @param: cInputData 输入的数据 cOperate 数据操作
     * @return:
     */
    public void setOperate(String cOperate)
    {
        this.mOperate = cOperate;
    }

    public String getOperate()
    {
        return this.mOperate;
    }

    public boolean submitData(VData cInputData, String cOperate)
    {
        // 将操作数据拷贝到本类中
        mInputData = (VData) cInputData.clone();
        this.setOperate(cOperate);
        // 得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData))
        {
            CError.buildErr(this, "外部传入数据有误!");
            return false;
        }
        // 数据业务处理(queryData())
        if (!dealData())
        {
            return false;
        }
        // 数据准备操作（preparedata())
        // if (!prepareData()) {
        // return false;
        // }
        this.setOperate("CONFIRM||RE");
        logger.debug("---" + mOperate);
        return true;
    }

    public VData getResult()
    {
        return mResult;
    }

    /**
     * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData)
    {
        mLPEdorItemSchema = (LPEdorItemSchema) cInputData.getObjectByObjectName(
                "LPEdorItemSchema", 0);
        mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
                "GlobalInput", 0));
        LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
        tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
        tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
        tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
        tLPEdorItemDB.setPolNo(mLPEdorItemSchema.getPolNo());
        tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
        LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
        if (tLPEdorItemSet == null || tLPEdorItemSet.size() != 1)
        {
            mErrors.addOneError(new CError("查询批改项目信息失败！"));
            return false;
        }
        mLPEdorItemSchema.setSchema(tLPEdorItemSet.get(1));
        mLimit = PubFun.getNoLimit(mLPEdorItemSchema.getManageCom());
        return true;
    }

    /**
     * 准备需要保存的数据
     */
    private boolean dealData()
    {
        // 处理应付表信息
        String i_sql = "select * from LCPol where ContNo = '?ContNo?' and payintv > 0 and appflag='1' and polno=mainpolno";
        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
        sqlbv.sql(i_sql);
        sqlbv.put("ContNo", mLPEdorItemSchema.getContNo());
        LCPolDB aLCPolDB = new LCPolDB();
        LCPolSet tLCPolSet = new LCPolSet();
        tLCPolSet = aLCPolDB.executeQuery(sqlbv);
        LCPolSchema tLCPolSchema = new LCPolSchema();
        if (tLCPolSet.size() < 1)
        {
            CError.buildErr(this, "查询险种表信息失败!");
            return false;
        }
        for (int i = 1; i <= tLCPolSet.size(); i++)
        {
            tLCPolSchema = tLCPolSet.get(i);
            // 删除顺序 先删分表再删总表
            String delLJSPayPerson = " delete from ljspayperson where polno='?polno?' and paytype='ZC'";
            String delLJSPay =
                    " update ljspay set sumduepaymoney = sumduepaymoney-?sumduepaymoney?  where othernotype in ('2','3') and otherno = '?otherno?'";
            SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
            sqlbv1.sql(delLJSPayPerson);
            sqlbv1.put("polno", tLCPolSchema.getPolNo());
            SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
            sqlbv2.sql(delLJSPay);
            sqlbv2.put("sumduepaymoney", tLCPolSchema.getPrem());
            sqlbv2.put("otherno", tLCPolSchema.getContNo());
            mMap.put(sqlbv1, "DELETE");
            mMap.put(sqlbv2, "UPDATE");
        }
        // P表与C表互换
        Reflections tRef = new Reflections();
        LPPolSet aLPPolSet = new LPPolSet();
        LCPolSet aLCPolSet = new LCPolSet();
        LPDutySet aLPDutySet = new LPDutySet();
        LCDutySet aLCDutySet = new LCDutySet();
        LPGetSet aLPGetSet = new LPGetSet();
        LCGetSet aLCGetSet = new LCGetSet();
        LPPremSet aLPPremSet = new LPPremSet();
        LCPremSet aLCPremSet = new LCPremSet();
        LCContStateSet aLCContStateSet = new LCContStateSet();
        LPContStateSet aLPContStateSet = new LPContStateSet();
        LPContSchema aLPContSchema = new LPContSchema();
        LCContSchema aLCContSchema = new LCContSchema();
        // 保单状态处理
        LPContStateDB tLPContStateDB = new LPContStateDB();
        LPContStateSet tLPContStateSet = new LPContStateSet();
        tLPContStateDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
        tLPContStateDB.setEdorType(mLPEdorItemSchema.getEdorType());
        tLPContStateSet = tLPContStateDB.query();
        if (tLPContStateSet == null || tLPContStateSet.size() < 1)
        {
            mErrors.addOneError("查询更新保单状态表失败!");
            return false;
        }
        for (int j = 1; j <= tLPContStateSet.size(); j++)
        {
            LPContStateSchema tLPContStateSchema = new LPContStateSchema();
            LPContStateSchema cLPContStateSchema = new LPContStateSchema();
            LCContStateSchema tLCContStateSchema = new LCContStateSchema();
            tLPContStateSchema = tLPContStateSet.get(j);
            LCContStateDB tLCContStateDB = new LCContStateDB();
            tLCContStateDB.setContNo(tLPContStateSchema.getContNo());
            tLCContStateDB.setInsuredNo(tLPContStateSchema.getInsuredNo());
            tLCContStateDB.setPolNo(tLPContStateSchema.getPolNo());
            tLCContStateDB.setStateType(tLPContStateSchema.getStateType());
            tLCContStateDB.setStateReason(tLPContStateSchema.getStateReason());
            tLCContStateDB.setStartDate(tLPContStateSchema.getStartDate());
            LCContStateSet tLCContStateSet = tLCContStateDB.query();
            if (tLCContStateSet.size() >= 1)
            {
                tRef.transFields(cLPContStateSchema, tLCContStateSet.get(1));
                cLPContStateSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
                cLPContStateSchema.setEdorType(mLPEdorItemSchema.getEdorType());
                cLPContStateSchema.setModifyDate(PubFun.getCurrentDate());
                cLPContStateSchema.setModifyTime(PubFun.getCurrentTime());
                tRef.transFields(tLCContStateSchema, tLPContStateSchema);
                tLCContStateSchema.setModifyDate(PubFun.getCurrentDate());
                tLCContStateSchema.setModifyTime(PubFun.getCurrentTime());
                aLCContStateSet.add(tLCContStateSchema);
                aLPContStateSet.add(cLPContStateSchema);
            }
            else
            {
                tRef.transFields(tLCContStateSchema, tLPContStateSchema);
                tLCContStateSchema.setModifyDate(PubFun.getCurrentDate());
                tLCContStateSchema.setModifyTime(PubFun.getCurrentTime());
                aLCContStateSet.add(tLCContStateSchema);
            }
        }
        mMap.put(aLCContStateSet, "DELETE&INSERT");
        mMap.put(aLPContStateSet, "DELETE&INSERT");
        // 删除失效通知书打印管理表记录
        String delLOPrtManager =
                " delete from loprtmanager where code = '42' and otherno = '?otherno?'";
        SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
        sqlbv3.sql(delLOPrtManager);
        sqlbv3.put("otherno", tLCPolSchema.getContNo());
        mMap.put(sqlbv3, "DELETE");
        // 险种表处理
        // DispatchBonusBL tDispatchBonusBL = new DispatchBonusBL();
        LPPolDB tLPPolDB = new LPPolDB();
        LPPolSchema tLPPolSchema = new LPPolSchema();
        LPPolSet tLPPolSet = new LPPolSet();
        tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
        tLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
        tLPPolDB.setSchema(tLPPolSchema);
        tLPPolSet = tLPPolDB.query();
        if (tLPPolSet.size() < 1)
        {
            CError tError = new CError();
            tError.buildErr(this, "查询险种表出错!");
            return false;
        }

        String APolNo = "";
        ArrayList<String> strArr=new ArrayList<String>();
        for (int i = 1; i <= tLPPolSet.size(); i++)
        {
        	
        	strArr.add(tLPPolSet.get(i).getPolNo());
//            if (i == 1)
//            {
//                APolNo = "'" + tLPPolSet.get(i).getPolNo() + "'";
//            }
//            else
//            {
//                APolNo += ",'" + tLPPolSet.get(i).getPolNo() + "'";
//            }
            LCPolSchema aLCPolSchema = new LCPolSchema();
            LPPolSchema aLPPolSchema = new LPPolSchema();
            LPPolSchema cLPPolSchema = new LPPolSchema();
            aLPPolSchema.setSchema(tLPPolSet.get(i));
            tRef.transFields(aLCPolSchema, aLPPolSchema);
            aLCPolSchema.setModifyDate(PubFun.getCurrentDate());
            aLCPolSchema.setModifyTime(PubFun.getCurrentTime());
            LCPolDB tLCPolDB = new LCPolDB();
            LCPolSet tempLCPolSet = new LCPolSet();
            tLCPolDB.setPolNo(aLPPolSchema.getPolNo());
            tempLCPolSet = tLCPolDB.query();
            if (tempLCPolSet.size() >= 1)
            {
                tRef.transFields(cLPPolSchema, tempLCPolSet.get(1));
                cLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
                cLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
                aLPPolSet.add(cLPPolSchema);
            }
            aLCPolSet.add(aLCPolSchema);

            // 险种责任表
            LPDutyDB tLPDutyDB = new LPDutyDB();
            LPDutySchema tLPDutySchema = new LPDutySchema();
            LPDutySet tLPDutySet = new LPDutySet();
            tLPDutySchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
            tLPDutySchema.setEdorType(mLPEdorItemSchema.getEdorType());
            tLPDutySchema.setPolNo(aLCPolSchema.getPolNo());
            tLPDutyDB.setSchema(tLPDutySchema);
            tLPDutySet = tLPDutyDB.query();
            for (int m = 1; m <= tLPDutySet.size(); m++)
            {
                LCDutySchema aLCDutySchema = new LCDutySchema();
                LPDutySchema aLPDutySchema = new LPDutySchema();

                aLPDutySchema = tLPDutySet.get(m);
                tRef.transFields(aLCDutySchema, aLPDutySchema);
                aLCDutySchema.setModifyDate(PubFun.getCurrentDate());
                aLCDutySchema.setModifyTime(PubFun.getCurrentTime());
                LCDutyDB tLCDutyDB = new LCDutyDB();
                LCDutySet tempLCDutySet = new LCDutySet();
                tLCDutyDB.setPolNo(aLPDutySchema.getPolNo());
                tLCDutyDB.setDutyCode(aLPDutySchema.getDutyCode());
                tempLCDutySet = tLCDutyDB.query();
                if (tempLCDutySet.size() >= 1)
                {
                    tRef.transFields(aLPDutySchema, tempLCDutySet.get(1));
                    aLPDutySchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
                    aLPDutySchema.setEdorType(mLPEdorItemSchema.getEdorType());
                }
                aLPDutySet.add(aLPDutySchema);
                aLCDutySet.add(aLCDutySchema);
            }
            mMap.put(aLPDutySet, "DELETE&INSERT");
            mMap.put(aLCDutySet, "DELETE&INSERT");

            // 险种责任表
            LPGetDB tLPGetDB = new LPGetDB();
            LPGetSchema tLPGetSchema = new LPGetSchema();
            LPGetSet tLPGetSet = new LPGetSet();
            tLPGetSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
            tLPGetSchema.setEdorType(mLPEdorItemSchema.getEdorType());
            tLPGetSchema.setPolNo(aLCPolSchema.getPolNo());
            tLPGetDB.setSchema(tLPGetSchema);
            tLPGetSet = tLPGetDB.query();

            for (int m = 1; m <= tLPGetSet.size(); m++)
            {
                LCGetSchema aLCGetSchema = new LCGetSchema();
                LPGetSchema aLPGetSchema = new LPGetSchema();
                // tLPGetSet.get(i).setPaytoDate(mPaytoDate);
                aLPGetSchema = tLPGetSet.get(m);
                tRef.transFields(aLCGetSchema, aLPGetSchema);
                aLCGetSchema.setModifyDate(PubFun.getCurrentDate());
                aLCGetSchema.setModifyTime(PubFun.getCurrentTime());
                LCGetDB tLCGetDB = new LCGetDB();
                LCGetSet tempLCGetSet = new LCGetSet();
                tLCGetDB.setPolNo(aLPGetSchema.getPolNo());
                tLCGetDB.setGetDutyCode(aLPGetSchema.getGetDutyCode());
                tLCGetDB.setDutyCode(aLPGetSchema.getDutyCode());
                tempLCGetSet = tLCGetDB.query();
                if (tempLCGetSet.size() >= 1)
                {
                    tRef.transFields(aLPGetSchema, tempLCGetSet.get(1));
                    aLPGetSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
                    aLPGetSchema.setEdorType(mLPEdorItemSchema.getEdorType());
                }
                aLPGetSet.add(aLPGetSchema);
                aLCGetSet.add(aLCGetSchema);
            }
            mMap.put(aLPGetSet, "DELETE&INSERT");
            mMap.put(aLCGetSet, "DELETE&INSERT");

            String sPolNo = mLPEdorItemSchema.getPolNo().trim();
            // 保费项表
            LPPremDB tLPPremDB = new LPPremDB();
            LPPremSet tLPPremSet = new LPPremSet();
            LCPremSet tLCPremSet = new LCPremSet();
            tLPPremDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
            tLPPremDB.setEdorType(mLPEdorItemSchema.getEdorType());
            tLPPremDB.setContNo(mLPEdorItemSchema.getContNo());
            tLPPremDB.setPolNo(aLCPolSchema.getPolNo());
            tLPPremSet = tLPPremDB.query();

            for (int j = 1; j <= tLPPremSet.size(); j++)
            {
                LCPremSchema tLCPremSchema = new LCPremSchema();
                tRef.transFields(tLCPremSchema, tLPPremSet.get(j).getSchema());
                tLCPremSchema.setUrgePayFlag("Y");
                aLCPremSet.add(tLCPremSchema);
                LCPremDB tLCPremDB = new LCPremDB();
                tLCPremDB.setPolNo(tLCPremSchema.getPolNo());
                tLCPremDB.setPayPlanCode(tLCPremSchema.getPayPlanCode());
                tLCPremDB.setDutyCode(tLCPremSchema.getDutyCode());
                tLCPremSet = tLCPremDB.query();
                if (tLCPremSet != null && tLCPremSet.size() > 0)
                {
                    LPPremSchema tLPPremSchema = new LPPremSchema();
                    tRef.transFields(tLPPremSchema, tLCPremSet.get(1).getSchema());
                    tLPPremSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
                    tLPPremSchema.setEdorType(mLPEdorItemSchema.getEdorType());
                    aLPPremSet.add(tLPPremSchema);
                }
            }

            mMap.put(aLCPremSet, "DELETE&INSERT");
            mMap.put(aLPPremSet, "DELETE&INSERT");

            LPInsureAccClassFeeSet tLPInsureAccClassFeeSet = new
                    LPInsureAccClassFeeSet();
            LPInsureAccClassFeeDB tLPInsureAccClassFeeDB = new
                    LPInsureAccClassFeeDB();
            tLPInsureAccClassFeeDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
            tLPInsureAccClassFeeDB.setEdorType(mLPEdorItemSchema.getEdorType());
            tLPInsureAccClassFeeDB.setContNo(mLPEdorItemSchema.getContNo());
            tLPInsureAccClassFeeDB.setPolNo(aLCPolSchema.getPolNo());
            tLPInsureAccClassFeeSet = tLPInsureAccClassFeeDB.query();

            LCInsureAccClassFeeSet aLCInsureAccClassFeeSet = new
                    LCInsureAccClassFeeSet();
            LPInsureAccClassFeeSet aLPInsureAccClassFeeSet = new
                    LPInsureAccClassFeeSet();
            for (int t = 1; t <= tLPInsureAccClassFeeSet.size(); t++)
            {
                LCInsureAccClassFeeSchema tLCInsureAccClassFeeSchema = new
                        LCInsureAccClassFeeSchema();
                tRef = new Reflections();
                tRef.transFields(tLCInsureAccClassFeeSchema,
                                 tLPInsureAccClassFeeSet.get(t).getSchema());
                aLCInsureAccClassFeeSet.add(tLCInsureAccClassFeeSchema);
                LCInsureAccClassFeeDB tLCInsureAccClassFeeDB = new
                        LCInsureAccClassFeeDB();
                tLCInsureAccClassFeeDB.setPolNo(tLPInsureAccClassFeeSet.get(t).
                                                getPolNo());
                tLCInsureAccClassFeeDB.setInsuAccNo(tLPInsureAccClassFeeSet.get(
                        t).getInsuAccNo());
                tLCInsureAccClassFeeDB.setPayPlanCode(tLPInsureAccClassFeeSet.
                        get(t).getPayPlanCode());
                tLCInsureAccClassFeeDB.setOtherNo(tLPInsureAccClassFeeSet.get(t).
                                                  getOtherNo());
                tLCInsureAccClassFeeDB.setAccAscription(tLPInsureAccClassFeeSet.
                        get(t).getAccAscription());
                tLCInsureAccClassFeeDB.setFeeCode(tLPInsureAccClassFeeSet.get(t).
                                                  getFeeCode());
                LCInsureAccClassFeeSet tLCInsureAccClassFeeSet = new
                        LCInsureAccClassFeeSet();
                tLCInsureAccClassFeeSet = tLCInsureAccClassFeeDB.query();
                if (tLCInsureAccClassFeeSet != null &&
                    tLCInsureAccClassFeeSet.size() > 0)
                {
                    LPInsureAccClassFeeSchema tLPInsureAccClassFeeSchema = new
                            LPInsureAccClassFeeSchema();
                    tRef.transFields(tLPInsureAccClassFeeSchema,
                                     tLCInsureAccClassFeeSet.get(1).getSchema());
                    tLPInsureAccClassFeeSchema.setEdorNo(mLPEdorItemSchema.
                            getEdorNo());
                    tLPInsureAccClassFeeSchema.setEdorType(mLPEdorItemSchema.
                            getEdorType());
                    aLPInsureAccClassFeeSet.add(tLPInsureAccClassFeeSchema);
                }
            }
            mMap.put(aLCInsureAccClassFeeSet, "DELETE&INSERT");
            mMap.put(aLPInsureAccClassFeeSet, "DELETE&INSERT");

            //LPInsureAccFeeTrace
            LPInsureAccFeeTraceSet tLPInsureAccFeeTraceSet = new
                    LPInsureAccFeeTraceSet();
            LPInsureAccFeeTraceDB tLPInsureAccFeeTraceDB = new
                    LPInsureAccFeeTraceDB();
            tLPInsureAccFeeTraceDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
            tLPInsureAccFeeTraceDB.setEdorType(mLPEdorItemSchema.getEdorType());
            tLPInsureAccFeeTraceDB.setContNo(mLPEdorItemSchema.getContNo());
            tLPInsureAccFeeTraceDB.setPolNo(aLCPolSchema.getPolNo());
            tLPInsureAccFeeTraceSet = tLPInsureAccFeeTraceDB.query();

            LCInsureAccFeeTraceSet aLCInsureAccFeeTraceSet = new
                    LCInsureAccFeeTraceSet();
            LPInsureAccFeeTraceSet aLPInsureAccFeeTraceSet = new
                    LPInsureAccFeeTraceSet();

            for (int t = 1; t <= tLPInsureAccFeeTraceSet.size(); t++)
            {
                LCInsureAccFeeTraceSchema tLCInsureAccFeeTraceSchema = new
                        LCInsureAccFeeTraceSchema();
                tRef = new Reflections();
                tRef.transFields(tLCInsureAccFeeTraceSchema,
                                 tLPInsureAccFeeTraceSet.get(t).getSchema());
                aLCInsureAccFeeTraceSet.add(tLCInsureAccFeeTraceSchema);
            }
            mMap.put(aLCInsureAccFeeTraceSet, "DELETE&INSERT");

            //LPInsureAccFee
            LPInsureAccFeeSet tLPInsureAccFeeSet = new
                    LPInsureAccFeeSet();
            LPInsureAccFeeDB tLPInsureAccFeeDB = new
                                                 LPInsureAccFeeDB();
            tLPInsureAccFeeDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
            tLPInsureAccFeeDB.setEdorType(mLPEdorItemSchema.getEdorType());
            tLPInsureAccFeeDB.setContNo(mLPEdorItemSchema.getContNo());
            tLPInsureAccFeeDB.setPolNo(aLCPolSchema.getPolNo());
            tLPInsureAccFeeSet = tLPInsureAccFeeDB.query();

            LCInsureAccFeeSet aLCInsureAccFeeSet = new
                    LCInsureAccFeeSet();
            LPInsureAccFeeSet aLPInsureAccFeeSet = new
                    LPInsureAccFeeSet();

            for (int t = 1; t <= tLPInsureAccFeeSet.size(); t++)
            {
                LCInsureAccFeeSchema tLCInsureAccFeeSchema = new
                        LCInsureAccFeeSchema();
                tRef = new Reflections();
                tRef.transFields(tLCInsureAccFeeSchema,
                                 tLPInsureAccFeeSet.get(t).getSchema());
                aLCInsureAccFeeSet.add(tLCInsureAccFeeSchema);

                LCInsureAccFeeDB tLCInsureAccFeeDB = new LCInsureAccFeeDB();
                tLCInsureAccFeeDB.setPolNo(tLPInsureAccFeeSet.get(t).getPolNo());
                tLCInsureAccFeeDB.setInsuAccNo(tLPInsureAccFeeSet.get(t).
                                               getInsuAccNo());
                LCInsureAccFeeSet tLCInsureAccFeeSet = new LCInsureAccFeeSet();
                tLCInsureAccFeeSet = tLCInsureAccFeeDB.query();
                if (tLCInsureAccFeeSet != null &&
                    tLCInsureAccFeeSet.size() > 0)
                {
                    LPInsureAccFeeSchema tLPInsureAccFeeSchema = new
                            LPInsureAccFeeSchema();
                    tRef.transFields(tLPInsureAccFeeSchema,
                                     tLCInsureAccFeeSet.get(1).getSchema());
                    tLPInsureAccFeeSchema.setEdorNo(mLPEdorItemSchema.
                            getEdorNo());
                    tLPInsureAccFeeSchema.setEdorType(mLPEdorItemSchema.
                            getEdorType());
                    aLPInsureAccFeeSet.add(tLPInsureAccFeeSchema);
                }

            }
            mMap.put(aLCInsureAccFeeSet, "DELETE&INSERT");
            mMap.put(aLPInsureAccFeeSet, "DELETE&INSERT");

            ////////////////LPInsureAccClassSet
            LPInsureAccClassSet tLPInsureAccClassSet = new
                    LPInsureAccClassSet();
            LPInsureAccClassDB tLPInsureAccClassDB = new
                    LPInsureAccClassDB();
            tLPInsureAccClassDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
            tLPInsureAccClassDB.setEdorType(mLPEdorItemSchema.getEdorType());
            tLPInsureAccClassDB.setContNo(mLPEdorItemSchema.getContNo());
            tLPInsureAccClassDB.setPolNo(aLCPolSchema.getPolNo());
            tLPInsureAccClassSet = tLPInsureAccClassDB.query();

            LCInsureAccClassSet aLCInsureAccClassSet = new
                    LCInsureAccClassSet();
            LPInsureAccClassSet aLPInsureAccClassSet = new
                    LPInsureAccClassSet();

            for (int t = 1; t <= tLPInsureAccClassSet.size(); t++)
            {
                LCInsureAccClassSchema tLCInsureAccClassSchema = new
                        LCInsureAccClassSchema();
                tRef = new Reflections();
                tRef.transFields(tLCInsureAccClassSchema,
                                 tLPInsureAccClassSet.get(t).getSchema());
                aLCInsureAccClassSet.add(tLCInsureAccClassSchema);

                LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
                tLCInsureAccClassDB.setPolNo(tLPInsureAccClassSet.get(t).
                                             getPolNo());
                tLCInsureAccClassDB.setInsuAccNo(tLPInsureAccClassSet.get(t).
                                                 getInsuAccNo());
                LCInsureAccClassSet tLCInsureAccClassSet = new
                        LCInsureAccClassSet();
                tLCInsureAccClassSet = tLCInsureAccClassDB.query();
                if (tLCInsureAccClassSet != null &&
                    tLCInsureAccClassSet.size() > 0)
                {
                    LPInsureAccClassSchema tLPInsureAccClassSchema = new
                            LPInsureAccClassSchema();
                    tRef.transFields(tLPInsureAccClassSchema,
                                     tLCInsureAccClassSet.get(1).getSchema());
                    tLPInsureAccClassSchema.setEdorNo(mLPEdorItemSchema.
                            getEdorNo());
                    tLPInsureAccClassSchema.setEdorType(mLPEdorItemSchema.
                            getEdorType());
                    aLPInsureAccClassSet.add(tLPInsureAccClassSchema);
                }

            }
            mMap.put(aLCInsureAccClassSet, "DELETE&INSERT");
            mMap.put(aLPInsureAccClassSet, "DELETE&INSERT");

            //////////////LPInsureAccTrace
            LPInsureAccTraceSet tLPInsureAccTraceSet = new
                    LPInsureAccTraceSet();
            LPInsureAccTraceDB tLPInsureAccTraceDB = new
                    LPInsureAccTraceDB();
            tLPInsureAccTraceDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
            tLPInsureAccTraceDB.setEdorType(mLPEdorItemSchema.getEdorType());
            tLPInsureAccTraceDB.setContNo(mLPEdorItemSchema.getContNo());
            tLPInsureAccTraceDB.setPolNo(aLCPolSchema.getPolNo());
            tLPInsureAccTraceSet = tLPInsureAccTraceDB.query();

            LCInsureAccTraceSet aLCInsureAccTraceSet = new
                    LCInsureAccTraceSet();
            LPInsureAccTraceSet aLPInsureAccTraceSet = new
                    LPInsureAccTraceSet();

            for (int t = 1; t <= tLPInsureAccTraceSet.size(); t++)
            {
                LCInsureAccTraceSchema tLCInsureAccTraceSchema = new
                        LCInsureAccTraceSchema();
                tRef = new Reflections();
                tRef.transFields(tLCInsureAccTraceSchema,
                                 tLPInsureAccTraceSet.get(t).getSchema());
                aLCInsureAccTraceSet.add(tLCInsureAccTraceSchema);
            }
            mMap.put(aLCInsureAccTraceSet, "DELETE&INSERT");

            ///////LPInsureAcc
            LPInsureAccSet tLPInsureAccSet = new
                                             LPInsureAccSet();
            LPInsureAccDB tLPInsureAccDB = new
                                           LPInsureAccDB();
            tLPInsureAccDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
            tLPInsureAccDB.setEdorType(mLPEdorItemSchema.getEdorType());
            tLPInsureAccDB.setContNo(mLPEdorItemSchema.getContNo());
            tLPInsureAccDB.setPolNo(aLCPolSchema.getPolNo());
            tLPInsureAccSet = tLPInsureAccDB.query();

            LCInsureAccSet aLCInsureAccSet = new
                                             LCInsureAccSet();
            LPInsureAccSet aLPInsureAccSet = new
                                             LPInsureAccSet();

            for (int t = 1; t <= tLPInsureAccSet.size(); t++)
            {
                LCInsureAccSchema tLCInsureAccSchema = new
                        LCInsureAccSchema();
                tRef = new Reflections();
                tRef.transFields(tLCInsureAccSchema,
                                 tLPInsureAccSet.get(t).getSchema());
                aLCInsureAccSet.add(tLCInsureAccSchema);

                LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
                tLCInsureAccDB.setPolNo(tLPInsureAccClassSet.get(t).
                                        getPolNo());
                tLCInsureAccDB.setInsuAccNo(tLPInsureAccClassSet.get(t).
                                            getInsuAccNo());
                LCInsureAccSet tLCInsureAccSet = new
                                                 LCInsureAccSet();
                tLCInsureAccSet = tLCInsureAccDB.query();
                if (tLCInsureAccSet != null &&
                    tLCInsureAccSet.size() > 0)
                {
                    LPInsureAccSchema tLPInsureAccSchema = new
                            LPInsureAccSchema();
                    tRef.transFields(tLPInsureAccSchema,
                                     tLCInsureAccSet.get(1).getSchema());
                    tLPInsureAccSchema.setEdorNo(mLPEdorItemSchema.
                                                 getEdorNo());
                    tLPInsureAccSchema.setEdorType(mLPEdorItemSchema.
                            getEdorType());
                    aLPInsureAccSet.add(tLPInsureAccSchema);
                }

            }
            mMap.put(aLCInsureAccSet, "DELETE&INSERT");
            mMap.put(aLPInsureAccSet, "DELETE&INSERT");

        }
        mMap.put(aLPPolSet, "DELETE&INSERT");
        mMap.put(aLCPolSet, "DELETE&INSERT");
        SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
        sqlbv4.sql("delete from ljspayperson where polno not in (?polno?) and contno = '?contno?'");
        sqlbv4.put("contno", mLPEdorItemSchema.getContNo());
        sqlbv4.put("polno", strArr);
        mMap.put(sqlbv4, "DELETE");
        LPContDB tLPContDB = new LPContDB();
        tLPContDB.setContNo(mLPEdorItemSchema.getContNo());
        tLPContDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
        tLPContDB.setEdorType(mLPEdorItemSchema.getEdorType());
        if (!tLPContDB.getInfo())
        {
            mErrors.addOneError("查询批改保单信息失败!");
            return false;
        }
        tRef.transFields(aLCContSchema, tLPContDB.getSchema());
        // aLCContSchema.setPaytoDate(mPaytoDate);
        LCContDB tLCContDB = new LCContDB();
        tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
        if (!tLCContDB.getInfo())
        {
            mErrors.addOneError("查询保单信息失败!");
            return false;
        }
        tRef.transFields(aLPContSchema, tLCContDB.getSchema());
        aLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
        aLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
        mMap.put(aLCContSchema, "UPDATE");
        mMap.put(aLPContSchema, "UPDATE");
        mLCContSchema.setSchema(aLCContSchema);
        /**撤销续期抽档*/
        IndiDueFeeCancelBL tIndiDueFeeCancelBL = new IndiDueFeeCancelBL();
        VData tv = new VData();
        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("SubmitFlag","noSubmit");
        tv.add(mLCContSchema);
        tv.add(mGlobalInput);
        tv.add(tTransferData);
        if(!tIndiDueFeeCancelBL.submitData(tv,"BQApp"))
        {
            mErrors.addOneError(tIndiDueFeeCancelBL.mErrors.getFirstError());
            return false;
        }
        mMap.add(tIndiDueFeeCancelBL.getMap());
        /***********抽档撤销结束**********/
        mLPEdorItemSchema.setEdorState("0");
        mLPEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
        mLPEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
        mMap.put(mLPEdorItemSchema, "UPDATE");
        mResult.clear();
        mResult.add(mMap);
        return true;
    }

    /**
     * 产生个人保全实收表交费收据号码
     *
     * @return String
     */
    private String getPayNo()
    {
        String tPayNo = "";
        tPayNo = PubFun1.CreateMaxNo("PayNo", mLimit);
        return tPayNo;
    }

    /**
     * 产生批改补退费表(实收/实付)的实付号码
     *
     * @return String
     */
    private String getActuGetNo()
    {
        String tActuGetNo = "";
        tActuGetNo = PubFun1.CreateMaxNo("ACTUGETNO", mLimit);
        return tActuGetNo;
    }

    /**
     * 得到保全实交实付号码
     *
     * @return
     */
    private String getPayGetNo()
    {
        String tPayGetNo = "";
        LJAPaySet tLJAPaySet = new LJAPaySet();
        LJAPayDB tLJAPayDB = new LJAPayDB();
        tLJAPayDB.setIncomeNo(mLPEdorItemSchema.getEdorNo());
        tLJAPaySet = tLJAPayDB.query();
        if (tLJAPaySet.size() > 0)
        {
            for (int i = 1; i <= tLJAPaySet.size(); i++)
            {
                tPayGetNo = tLJAPaySet.get(i).getPayNo();
            }
        }
        else
        {
            LJAGetSet tLJAGetSet = new LJAGetSet();
            LJAGetDB tLJAGetDB = new LJAGetDB();
            tLJAGetDB.setOtherNo(mLPEdorItemSchema.getEdorNo());
            tLJAGetSet = tLJAGetDB.query();
            if (tLJAGetSet.size() > 0)
            {
                for (int i = 1; i <= tLJAGetSet.size(); i++)
                {
                    tPayGetNo = tLJAGetSet.get(i).getActuGetNo();
                }
            }
            else
            {
                // 产生交费收据号
                String tLimit = PubFun.getNoLimit(mLPEdorItemSchema.
                                                  getManageCom());
                tPayGetNo = PubFun1.CreateMaxNo("PayNo", tLimit);
            }
        }
        return tPayGetNo;
    }

    /**
     * 模拟应交数据源
     */
    private void initLJSPay()
    {
        LJSPaySet tLJSPaySet = new LJSPaySet();
        LJSPayDB tLJSPayDB = new LJSPayDB();
        tLJSPayDB.setOtherNo(mLPEdorItemSchema.getPolNo());
        tLJSPayDB.setOtherNoType("2");
        tLJSPaySet = tLJSPayDB.query();
        for (int i = 1; i <= tLJSPaySet.size(); i++)
        {
            mLJSPaySchema.setSchema(tLJSPaySet.get(i));
        }
    }

    /**
     * 生成LASPayPerson数据
     *
     * @return boolean
     */
    private boolean CreateLASPayPerson()
    {
        LASPayPersonSet tLASPayPersonSet = new LASPayPersonSet();
        LASPayPersonSchema tLASPayPersonSchema;
        Reflections tRef = new Reflections();
        String sPolType = "";
        String sBranchSeries = "";
        try
        {
            ExeSQL tES = new ExeSQL();
            String QueryPolType =
                    "select poltype from lacommisiondetail where 1=1" +
                    " and grpcontno = '?grpcontno?'" + " and agentcode = '?agentcode?'";
            SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
            sqlbv5.sql(QueryPolType);
            sqlbv5.put("grpcontno", mLCContSchema.getContNo());
            sqlbv5.put("agentcode", mLCContSchema.getAgentCode());
            sPolType = tES.getOneValue(sqlbv5);
            String QuerySeries =
                    "select branchseries from labranchgroup where 1=1" +
                    " and agentgroup = '?agentgroup?'";
            SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
            sqlbv6.sql(QuerySeries);
            sqlbv6.put("agentgroup", mLCContSchema.getAgentGroup());
            sBranchSeries = tES.getOneValue(sqlbv6);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            CError.buildErr(this, "查询代理信息异常!");
            return false;
        }
        for (int i = 1; i <= mLJAPayPersonSet.size(); i++)
        {
            tLASPayPersonSchema = new LASPayPersonSchema();
            tRef.transFields(tLASPayPersonSchema, mLJAPayPersonSet.get(i));
            tLASPayPersonSchema.setActuPayFlag("1");
            tLASPayPersonSchema.setPolType(sPolType);
            tLASPayPersonSchema.setBranchSeries(sBranchSeries);
            tLASPayPersonSchema.setPayType("ZC");
            tLASPayPersonSchema.setOperator(mGlobalInput.Operator);
            tLASPayPersonSchema.setModifyDate(PubFun.getCurrentDate());
            tLASPayPersonSchema.setModifyTime(PubFun.getCurrentTime());
            tLASPayPersonSet.add(tLASPayPersonSchema);
        }
        mMap.put(tLASPayPersonSet, "DELETE&INSERT");
        return true;
    }

    // 准备往后层输出所需要的数据
    // 输出：如果准备数据时发生错误则返回false,否则返回true
    private boolean prepareData()
    {
        Reflections tRef = new Reflections();
        LPPolSchema tLPPolSchema = new LPPolSchema();
        LCPolDB tLCPolDB = new LCPolDB();
        tLCPolDB.setPolNo(mLPEdorItemSchema.getPolNo());
        if (!tLCPolDB.getInfo())
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLCPolDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "PEdorTSConfirmBL";
            tError.functionName = "dealData";
            tError.errorMessage = "个人保单表查询失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        else
        {
            tRef.transFields(tLPPolSchema, tLCPolDB.getSchema());
            tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
            tLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
        }
        LPDutySet tLPDutySet = new LPDutySet();
        for (int i = 1; i <= mLCDutySet.size(); i++)
        {
            LPDutySchema tLPDutySchema = new LPDutySchema();
            tRef.transFields(tLPDutySchema, mLCDutySet.get(i));
            tLPDutySchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
            tLPDutySchema.setEdorType(mLPEdorItemSchema.getEdorType());
            tLPDutySet.add(tLPDutySchema);
        }
        // 保费项表
        LPPremSet aLPPremSet = new LPPremSet();
        LCPremSet aLCPremSet = new LCPremSet();
        LPPremDB tLPPremDB = new LPPremDB();
        LPPremSet tLPPremSet = new LPPremSet();
        LCPremSet tLCPremSet = new LCPremSet();
        tLPPremDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
        tLPPremDB.setEdorType(mLPEdorItemSchema.getEdorType());
        tLPPremDB.setContNo(mLPEdorItemSchema.getContNo());
        tLPPremSet = tLPPremDB.query();
        for (int j = 1; j <= tLPPremSet.size(); j++)
        {
            LCPremSchema tLCPremSchema = new LCPremSchema();
            tRef.transFields(tLCPremSchema, tLPPremSet.get(j).getSchema());
            aLCPremSet.add(tLCPremSchema);
        }
        LCPremDB tLCPremDB = new LCPremDB();
        tLCPremDB.setContNo(mLPEdorItemSchema.getContNo());
        tLCPremSet = tLCPremDB.query();
        for (int j = 1; j <= tLCPremSet.size(); j++)
        {
            LPPremSchema tLPPremSchema = new LPPremSchema();
            tRef.transFields(tLPPremSchema, tLCPremSet.get(j).getSchema());
            tLPPremSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
            tLPPremSchema.setEdorType(mLPEdorItemSchema.getEdorType());
            aLPPremSet.add(tLPPremSchema);
        }
        SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
        sqlbv7.sql("delete from LPPrem where EdorNo = '?EdorNo?' and EdorType = '?EdorType?'");
        sqlbv7.put("EdorNo", mLPEdorItemSchema.getEdorNo());
        sqlbv7.put("EdorType", mLPEdorItemSchema.getEdorType());
        SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
        sqlbv8.sql("delete from LCPrem where ContNo = '?ContNo?'");
        sqlbv8.put("ContNo", mLPEdorItemSchema.getContNo());
        mMap.put(sqlbv7, "DELETE");
        mMap.put(sqlbv8, "DELETE");
        mMap.put(aLCPremSet, "INSERT");
        mMap.put(aLPPremSet, "INSERT");
        mInputData.clear();
        mInputData.add(mLPEdorItemSchema);
        mInputData.add(mLJAPaySet); // 实收总表（插入）
        mInputData.add(mLJSPaySet); // 应收总表（删除）
        mInputData.add(mLJAPayPersonSet); // 实收个人表（插入）
        mInputData.add(mLJSPayPersonSet); // 应收个人交费表（删除）
        mInputData.add(mLJAGetEndorseSet);
        LPPolSet tLPPolSet = new LPPolSet();
        tLPPolSet.add(tLPPolSchema);
        mInputData.add(tLPPolSet);
        mInputData.add(tLPDutySet);
        mInputData.add(tLPPremSet);
        mLCPolSchema = new LCPolSchema();
        tRef.transFields(mLCPolSchema, mLPPolSchema);
        LOLoanDB tLOLoanDB = new LOLoanDB();
        tLOLoanDB.setPolNo(mLPEdorItemSchema.getPolNo());
        tLOLoanDB.setLoanType("1"); // 垫交标志
        tLOLoanDB.setPayOffFlag("0"); // 还清标志，0为未还清
        LOLoanSet tLOLoanSet = tLOLoanDB.query();
        if (tLOLoanSet.size() > 0)
        {
            // 设置为垫交有效状态
            mLCPolSchema.setPolState("0102" +
                                     mLCPolSchema.getPolState().substring(0, 3));
        }
        else
        {
            // 置保单状态(由失效置为有效).add by sxy at 2004-03-29
            mLCPolSchema.setPolState("0002" +
                                     mLCPolSchema.getPolState().substring(0, 3));
        }
        LCPolSet tLCPolSet = new LCPolSet();
        tLCPolSet.add(mLCPolSchema);
        mInputData.add(tLCPolSet); // 个人保单表（更新）
        mInputData.add(mLCPremNewSet); // 保费项表（更新）
        mInputData.add(mLCDutyNewSet); // 保险责任表（更新）
        mInputData.add(mLCInsureAccSet); // 保险帐户表(更新或插入：处理时先删除再插入)
        mInputData.add(mLCInsureAccTraceSet); // 保险帐户表记价履历表（插入）
        return true;
    }

    public static void main(String[] args)
    {
        LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
        LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
        tLPEdorItemDB.setEdorAcceptNo("6120080213000004");
        tLPEdorItemSet = tLPEdorItemDB.query();
        PEdorTSConfirmBL tPEdorTSConfirmBL = new PEdorTSConfirmBL();
        GlobalInput tGI = new GlobalInput();
        tGI.ComCode = "86";
        tGI.Operator = "001";
        tGI.ManageCom = "86";
        VData tVData = new VData();
        tVData.add(tGI);
        tVData.add(tLPEdorItemSet.get(1));
        if (!tPEdorTSConfirmBL.submitData(tVData, "CONFIRM||" + "TS"))
        {

        }
    }
}
