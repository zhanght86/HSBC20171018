package com.sinosoft.lis.acc;
import org.apache.log4j.Logger;


import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

/**
 * <p>Title: </p>
 *
 * <p>Description:管理费回退处理 </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company:sinosoft </p>
 *
 * @author:ck
 * @version 1.0
 */
public class ManageFeeRollBack {
private static Logger logger = Logger.getLogger(ManageFeeRollBack.class);

    private GlobalInput _GlobalInput = new GlobalInput();
    public CErrors mErrors = new CErrors();
    /*管理费收取时间*/

    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();
    private VData mResult = new VData();
    private MMap map = new MMap();
    private Reflections tRef = new Reflections();
    private PubInsuAccFun tPubInsuAccFun = new PubInsuAccFun();
    /*结果集*/
    LCInsureAccSet updLCInsureAccSet = new LCInsureAccSet();
    LCInsureAccClassSet updLCInsureAccClassSet = new LCInsureAccClassSet();
    LCInsureAccTraceSet updLCInsureAccTraceSet = new LCInsureAccTraceSet();
    LCInsureAccTraceSet insLCInsureAccTraceSet = new LCInsureAccTraceSet();
    LCInsureAccFeeSet updLCInsureAccFeeSet = new LCInsureAccFeeSet();
    LCInsureAccClassFeeSet updLCInsureAccClassFeeSet = new
            LCInsureAccClassFeeSet();
    LCInsureAccFeeTraceSet updLCInsureAccFeeTraceSet = new
            LCInsureAccFeeTraceSet();
    LCInsureAccFeeTraceSet insLCInsureAccFeeTraceSet = new
            LCInsureAccFeeTraceSet();
    public ManageFeeRollBack(GlobalInput aGlobalInput) {
        _GlobalInput = aGlobalInput;
    }

    public boolean RollBackManageFee() {
        return true;
    }

    /*将LCInsureAccTraceSet中的所有记录进行回退*/
    public boolean rollBackManageFee(LCInsureAccTraceSet tLCInsureAccTraceSet) {
        if (tLCInsureAccTraceSet == null || tLCInsureAccTraceSet.size() == 0) {
            CError.buildErr(this, "该时段没有收取管理费!");
            return false;
        }

        LCInsureAccTraceSchema tLCInsureAccTraceSchema;
        for (int i = 1; i <= tLCInsureAccTraceSet.size(); i++) {
            tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();

            LCInsureAccTraceSchema mLCInsureAccTraceSchema = new
                    LCInsureAccTraceSchema();
            mLCInsureAccTraceSchema = tLCInsureAccTraceSet.get(i);
            if (!mLCInsureAccTraceSchema.getState().equals("1"))
                continue;
            tRef.transFields(tLCInsureAccTraceSchema, mLCInsureAccTraceSchema);
            tLCInsureAccTraceSchema.setUnitCount( -tLCInsureAccTraceSchema.
                                                 getUnitCount());
            tLCInsureAccTraceSchema.setMoney( -tLCInsureAccTraceSchema.getMoney());
            String tLimit = PubFun.getNoLimit(tLCInsureAccTraceSet.get(i).
                                              getManageCom());
            String serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
            tLCInsureAccTraceSchema.setSerialNo(serNo);
            tLCInsureAccTraceSchema.setBusyType("EB"); //业务类型为 EB-差错回退
            tLCInsureAccTraceSchema.setState("2"); //直接置为生效
            tLCInsureAccTraceSchema.setValueDate(CurrentDate);
            tLCInsureAccTraceSchema.setAccAlterNo(tLCInsureAccTraceSet.get(i).
                                                  getSerialNo());
            tLCInsureAccTraceSchema.setAccAlterType("6"); //差错处理
            tLCInsureAccTraceSchema.setMakeDate(CurrentDate);
            tLCInsureAccTraceSchema.setMakeTime(CurrentTime);
            tLCInsureAccTraceSchema.setModifyTime(CurrentTime);
            tLCInsureAccTraceSchema.setOperator(_GlobalInput.Operator);
            insLCInsureAccTraceSet.add(tLCInsureAccTraceSchema);

            mLCInsureAccTraceSchema.setState("3");
            updLCInsureAccTraceSet.add(mLCInsureAccTraceSchema);

            //准备账户管理费
            LCInsureAccFeeTraceSchema tLCInsureAccFeeTraceSchema = new
                    LCInsureAccFeeTraceSchema();
            if (mLCInsureAccTraceSchema.getMoneyType().equals("GL")) {
                LCInsureAccFeeTraceDB mLCInsureAccFeeTraceDB = new
                        LCInsureAccFeeTraceDB();
                mLCInsureAccFeeTraceDB.setSerialNo(mLCInsureAccTraceSchema.
                        getSerialNo());
                if (!mLCInsureAccFeeTraceDB.getInfo()) {
                    CError.buildErr(this, "查询管理费履历信息失败!");
                    return false;
                }
                tRef.transFields(tLCInsureAccFeeTraceSchema,
                                 mLCInsureAccFeeTraceDB.getSchema());
                tLCInsureAccFeeTraceSchema.setSerialNo(tLCInsureAccTraceSchema.
                        getSerialNo());
                tLCInsureAccFeeTraceSchema.setState("2");
                tLCInsureAccFeeTraceSchema.setFee( -tLCInsureAccFeeTraceSchema.
                                                  getFee());
                tLCInsureAccFeeTraceSchema.setFeeUnit( -
                        tLCInsureAccFeeTraceSchema.
                        getFeeUnit());
                tLCInsureAccFeeTraceSchema.setOperator(_GlobalInput.Operator);
                tLCInsureAccFeeTraceSchema.setMakeDate(CurrentDate);
                tLCInsureAccFeeTraceSchema.setMakeTime(CurrentTime);
                tLCInsureAccFeeTraceSchema.setModifyDate(CurrentDate);
                tLCInsureAccFeeTraceSchema.setModifyTime(CurrentTime);

                insLCInsureAccFeeTraceSet.add(tLCInsureAccFeeTraceSchema);
                mLCInsureAccFeeTraceDB.setState("3");
                updLCInsureAccFeeTraceSet.add(mLCInsureAccFeeTraceDB.getSchema());
            }
        }

        updLCInsureAccClassSet = tPubInsuAccFun.createAccClassByTrace(
                insLCInsureAccTraceSet);
        updLCInsureAccSet = tPubInsuAccFun.createAccByTrace(
                insLCInsureAccTraceSet);

        updLCInsureAccClassFeeSet = tPubInsuAccFun.createAccFeeClassByTrace(
                insLCInsureAccFeeTraceSet);
        updLCInsureAccFeeSet = tPubInsuAccFun.createAccFeeByTrace(
                insLCInsureAccFeeTraceSet);

        //账户管理费轨迹记录
        map.put(updLCInsureAccTraceSet, "UPDATE");
        map.put(updLCInsureAccFeeTraceSet, "UPDATE");

        map.put(insLCInsureAccTraceSet, "INSERT");
        map.put(insLCInsureAccFeeTraceSet, "INSERT");

        //账户管理费信息更新
        map.put(updLCInsureAccClassFeeSet, "UPDATE");
        map.put(updLCInsureAccFeeSet, "UPDATE");

        //账户信息更新
        map.put(updLCInsureAccClassSet, "UPDATE");
        map.put(updLCInsureAccSet, "UPDATE");

        mResult.add(map);

        return true;
    }

    public LCInsureAccSet getLCInsureAccSet() {
        return updLCInsureAccSet;
    }

    public LCInsureAccClassSet getLCInsureAccClassSet() {
        return updLCInsureAccClassSet;
    }

    public LCInsureAccTraceSet getLCInsureAccTraceSet() {
        return updLCInsureAccTraceSet;
    }

    public LCInsureAccFeeSet getLCInsureAccFeeSet() {
        return updLCInsureAccFeeSet;
    }

    public LCInsureAccClassFeeSet getLCInsureAccClassFeeSet() {
        return updLCInsureAccClassFeeSet;
    }

    public LCInsureAccFeeTraceSet getLCInsureAccFeeTraceSet() {
        return updLCInsureAccFeeTraceSet;
    }

    public VData getResult() {
        return mResult;
    }

    /*查询账户下所有管理费信息*/
    public LCInsureAccTraceSet getManageFee(LCInsureAccClassSet
                                            tLCInsureAccClassSet,
                                            String tStartDate) {
        LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet();
        for (int i = 1; i <= tLCInsureAccClassSet.size(); i++) {
            LCInsureAccClassSchema tLCInsureAccClassSchema =
                    tLCInsureAccClassSet.get(i);
            LCInsureAccTraceSet mLCInsureAccTraceSet = new LCInsureAccTraceSet();
            LCInsureAccTraceDB mLCInsureAccTraceDB = new LCInsureAccTraceDB();
            String strsql = "select * from lcinsureacctrace where polno='" +
                    "?polno?" +
                    "' and insuaccno='" +
                    "?insuaccno?" +
                    "' and payplancode='" +
                    "?payplancode?" +
                    "' and shouldvaluedate>'" + "?tStartDate?" +
                    "' and (moneytype!='GL' or state='0')";
            SQLwithBindVariables sqlbv = new SQLwithBindVariables();
            sqlbv.sql(strsql);
            sqlbv.put("polno", tLCInsureAccClassSchema.getPolNo());
            sqlbv.put("insuaccno", tLCInsureAccClassSchema.getInsuAccNo());
            sqlbv.put("payplancode", tLCInsureAccClassSchema.getPayPlanCode());
            sqlbv.put("tStartDate", tStartDate);
            mLCInsureAccTraceSet = mLCInsureAccTraceDB.executeQuery(sqlbv);

            if (mLCInsureAccTraceSet.size() > 0) {
                CError.buildErr(this, "该时段有其它业务操作，请确认后再进行回退操作!");
                return null;
            }
            mLCInsureAccTraceSet.clear();
            strsql = "select * from lcinsureacctrace where polno='" +
                     "?polno?" +
                     "' and insuaccno='" +
                     "?insuaccno?" +
                     "' and payplancode='" +
                     "?payplancode?" +
                     "' and shouldvaluedate>='" + "?tStartDate?" +
                     "' and moneytype='GL' order by payplancode,insuaccno,shouldvaluedate desc";
            SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
            sqlbv11.sql(strsql);
            sqlbv11.put("polno", tLCInsureAccClassSchema.getPolNo());
            sqlbv11.put("insuaccno", tLCInsureAccClassSchema.getInsuAccNo());
            sqlbv11.put("payplancode", tLCInsureAccClassSchema.getPayPlanCode());
            sqlbv11.put("tStartDate", tStartDate);
            mLCInsureAccTraceSet = mLCInsureAccTraceDB.executeQuery(sqlbv11);

            tLCInsureAccTraceSet.add(mLCInsureAccTraceSet);
        }
        return tLCInsureAccTraceSet;
    }
}
