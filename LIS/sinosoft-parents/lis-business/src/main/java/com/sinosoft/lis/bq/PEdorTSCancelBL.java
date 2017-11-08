package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.utility.*;

/**
 * <p>Title: 保全项目投连万能明细</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: Sinosoft</p>
 * @author ck
 * @version 1.0
 */

public class PEdorTSCancelBL implements EdorCancel {
private static Logger logger = Logger.getLogger(PEdorTSCancelBL.class);
    /** 传入数据的容器 */
    private VData mInputData = new VData();
    /** 传出数据的容器 */
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;
    /** 错误处理类 */
    public CErrors mErrors = new CErrors();
    private MMap map = new MMap();
    /** 全局基础数据 */
    private GlobalInput mGlobalInput = new GlobalInput();
    //需要撤销的保全项目
    private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

    public PEdorTSCancelBL() {
    }

    /**
     * 数据提交的公共方法
     * @param cInputData 传入的数据对象
     * @param cOperate 数据操作字符串
     * @return boolean
     */
    public boolean submitData(VData cInputData, String cOperate) {
        mInputData = (VData) cInputData.clone();
        mOperate = cOperate;

        if (!getInputData()) {
            return false;
        }

        //进行业务处理
        if (!dealData()) {
            return false;
        }

        //准备往后台的数据
        if (!prepareData()) {
            return false;
        }

        return true;
    }


    /**
     * 将外部传入的数据分解到本类的属性中
     * @return boolean
     */
    private boolean getInputData() {
        try {
            mGlobalInput =
                    (GlobalInput)
                    mInputData.getObjectByObjectName("GlobalInput", 0);
            mLPEdorItemSchema = //需要撤销的保全项目
                    (LPEdorItemSchema)
                    mInputData.getObjectByObjectName("LPEdorItemSchema", 0);
        } catch (Exception e) {
            e.printStackTrace();
            CError.buildErr(this, "接收传入数据失败!");
            return false;
        }
        if (mGlobalInput == null || mLPEdorItemSchema == null) {
            CError.buildErr(this, "传入数据有误!");
            return false;
        }

        return true;
    }

    private boolean dealData() {
        String info = " edorno = '?edorno?'"
                      + " and edortype = '?edortype?'"
                      + " and contno = '?contno?'";
        String edorinfo = " edorno = '?edorno?'"
                          + " and edortype = '?edortype?'";
        SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
        sqlbv1.sql("delete from lppol where " + info);
        sqlbv1.put("edorno", mLPEdorItemSchema.getEdorNo());
        sqlbv1.put("edortype", mLPEdorItemSchema.getEdorType());
        sqlbv1.put("contno", mLPEdorItemSchema.getContNo());
        SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
        sqlbv2.sql("delete from lpduty where " + info);
        sqlbv2.put("edorno", mLPEdorItemSchema.getEdorNo());
        sqlbv2.put("edortype", mLPEdorItemSchema.getEdorType());
        sqlbv2.put("contno", mLPEdorItemSchema.getContNo());
        SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
        sqlbv3.sql("delete from lpprem where " + info);
        sqlbv3.put("edorno", mLPEdorItemSchema.getEdorNo());
        sqlbv3.put("edortype", mLPEdorItemSchema.getEdorType());
        sqlbv3.put("contno", mLPEdorItemSchema.getContNo());
        SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
        sqlbv4.sql("delete from lpget where " + info);
        sqlbv4.put("edorno", mLPEdorItemSchema.getEdorNo());
        sqlbv4.put("edortype", mLPEdorItemSchema.getEdorType());
        sqlbv4.put("contno", mLPEdorItemSchema.getContNo());
        SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
        sqlbv5.sql("delete from lpcontstate where " + info);
        sqlbv5.put("edorno", mLPEdorItemSchema.getEdorNo());
        sqlbv5.put("edortype", mLPEdorItemSchema.getEdorType());
        sqlbv5.put("contno", mLPEdorItemSchema.getContNo());
        map.put(sqlbv1, "DELETE");
        map.put(sqlbv2, "DELETE");
        map.put(sqlbv3, "DELETE");
        map.put(sqlbv4, "DELETE");
        map.put(sqlbv5, "DELETE");

        ExeSQL tES = new ExeSQL();
        SSRS tSSRS = new SSRS();
        String QueryPayCount =
                "select (case when min(paycount) is not null then min(paycount) else 0 end) from ljspayperson where contno = '?contno?' and paytype = 'RE'";
        SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
        sqlbv6.sql(QueryPayCount);
        sqlbv6.put("contno", mLPEdorItemSchema.getContNo());
        tSSRS = tES.execSQL(sqlbv6);
        if (tSSRS != null && tSSRS.getMaxRow() > 0) {
            if (tSSRS.GetText(1, 1) != null &&
                !tSSRS.GetText(1, 1).trim().equals("") && !tSSRS.GetText(1, 1).trim().equals("0")) {
                int minPayCount = Integer.parseInt(tSSRS.GetText(1, 1));
                tSSRS.Clear();
                String QueryStr =
                        "select 1 from ljspayperson a where paytype = 'RE'"
                        + " and exists (select 2 from ljspayperson "
                        + " where contno = a.contno "
                        + " and polno = a.polno"
                        + " and paycount = a.paycount"
                        + " and riskcode = a.riskcode"
                        + " and paytype = 'ZC')"
                        + " and contno = '?contno?'"
                        + " and paycount = ?minPayCount?";
                SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
                sqlbv7.sql(QueryStr);
                sqlbv7.put("contno", mLPEdorItemSchema.getContNo());
                sqlbv7.put("minPayCount", minPayCount);
                tSSRS = tES.execSQL(sqlbv7);
                if (tSSRS != null && tSSRS.MaxRow > 0) {
                    String delpayperson = "delete from ljspayperson where 1=1 "
                                          + " and contno = '?contno?'"
                                          + " and paytype = 'RE'";
                    SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
                    sqlbv8.sql(delpayperson);
                    sqlbv8.put("contno", mLPEdorItemSchema.getContNo());
                    map.put(sqlbv8, "DELETE");
                }
                else{
                    //兼容以前数据
                    String updatepayperson = "update ljspayperson set paytype = 'ZC',paydate = lastpaytodate where paytype = 'RE'"
                                           + " and contno = '?contno?'"
                                           + " and paycount = ?minPayCount?";
                    SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
                    sqlbv8.sql(updatepayperson);
                    sqlbv8.put("contno", mLPEdorItemSchema.getContNo());
                    sqlbv8.put("minPayCount", minPayCount);
                    map.put(sqlbv8,"UPDATE");
                    String delpayperson = "delete from ljspayperson where 1=1"
                                        + " and contno = '?contno?'"
                                        + " and paycount > ?minPayCount? and paytype = 'RE'";
                    SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
                    sqlbv9.sql(delpayperson);
                    sqlbv9.put("contno", mLPEdorItemSchema.getContNo());
                    sqlbv9.put("minPayCount", minPayCount);
                    map.put(sqlbv9, "DELETE");
                }
            }
        }

        SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
        sqlbv10.sql("delete from LPCustomerImpart where " + info);
        sqlbv10.put("edorno", mLPEdorItemSchema.getEdorNo());
        sqlbv10.put("edortype", mLPEdorItemSchema.getEdorType());
        sqlbv10.put("contno", mLPEdorItemSchema.getContNo());
        SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
        sqlbv11.sql("delete from LPCustomerImpartParams where " + info);
        sqlbv11.put("edorno", mLPEdorItemSchema.getEdorNo());
        sqlbv11.put("edortype", mLPEdorItemSchema.getEdorType());
        sqlbv11.put("contno", mLPEdorItemSchema.getContNo());
        SQLwithBindVariables sqlbv12=new SQLwithBindVariables();
        sqlbv12.sql("delete from lpcont where " + info);
        sqlbv12.put("edorno", mLPEdorItemSchema.getEdorNo());
        sqlbv12.put("edortype", mLPEdorItemSchema.getEdorType());
        sqlbv12.put("contno", mLPEdorItemSchema.getContNo());
        SQLwithBindVariables sqlbv13=new SQLwithBindVariables();
        sqlbv13.sql("delete from LPIssuePol where " + edorinfo);
        sqlbv13.put("edorno", mLPEdorItemSchema.getEdorNo());
        sqlbv13.put("edortype", mLPEdorItemSchema.getEdorType());
        SQLwithBindVariables sqlbv14=new SQLwithBindVariables();
        sqlbv14.sql("delete from LPCUWSub where " + info);
        sqlbv14.put("edorno", mLPEdorItemSchema.getEdorNo());
        sqlbv14.put("edortype", mLPEdorItemSchema.getEdorType());
        sqlbv14.put("contno", mLPEdorItemSchema.getContNo());
        SQLwithBindVariables sqlbv15=new SQLwithBindVariables();
        sqlbv15.sql("delete from LPCUWMaster where " + info);
        sqlbv15.put("edorno", mLPEdorItemSchema.getEdorNo());
        sqlbv15.put("edortype", mLPEdorItemSchema.getEdorType());
        sqlbv15.put("contno", mLPEdorItemSchema.getContNo());
        SQLwithBindVariables sqlbv16=new SQLwithBindVariables();
        sqlbv16.sql("delete from LPCUWError where " + info);
        sqlbv16.put("edorno", mLPEdorItemSchema.getEdorNo());
        sqlbv16.put("edortype", mLPEdorItemSchema.getEdorType());
        sqlbv16.put("contno", mLPEdorItemSchema.getContNo());
        SQLwithBindVariables sqlbv17=new SQLwithBindVariables();
        sqlbv17.sql("delete from LPSpec where " + info);
        sqlbv17.put("edorno", mLPEdorItemSchema.getEdorNo());
        sqlbv17.put("edortype", mLPEdorItemSchema.getEdorType());
        sqlbv17.put("contno", mLPEdorItemSchema.getContNo());
        map.put(sqlbv10, "DELETE");
        map.put(sqlbv11, "DELETE");
        map.put(sqlbv12, "DELETE");
        map.put(sqlbv13, "DELETE");
        map.put(sqlbv14, "DELETE");
        map.put(sqlbv15, "DELETE");
        map.put(sqlbv16, "DELETE");
        map.put(sqlbv17, "DELETE");

        String delgetendorse = "delete from ljsgetendorse where 1=1"
                               + " and endorsementno = '?endorsementno?'"
                               + " and FeeOperationType = '?FeeOperationType?'"
                               + " and contno = '?contno?'";
        SQLwithBindVariables sqlbv18=new SQLwithBindVariables();
        sqlbv18.sql(delgetendorse);
        sqlbv18.put("endorsementno", mLPEdorItemSchema.getEdorNo());
        sqlbv18.put("FeeOperationType", mLPEdorItemSchema.getEdorType());
        sqlbv18.put("contno", mLPEdorItemSchema.getContNo());
        map.put(sqlbv18, "DELETE");

        SQLwithBindVariables sqlbv19=new SQLwithBindVariables();
        sqlbv19.sql("delete from LPEngBonusPol where " + info);
        sqlbv19.put("edorno", mLPEdorItemSchema.getEdorNo());
        sqlbv19.put("edortype", mLPEdorItemSchema.getEdorType());
        sqlbv19.put("contno", mLPEdorItemSchema.getContNo());
        SQLwithBindVariables sqlbv20=new SQLwithBindVariables();
        sqlbv20.sql("delete from lpedoritem where " + info);
        sqlbv20.put("edorno", mLPEdorItemSchema.getEdorNo());
        sqlbv20.put("edortype", mLPEdorItemSchema.getEdorType());
        sqlbv20.put("contno", mLPEdorItemSchema.getContNo());
        map.put(sqlbv19, "DELETE");
        map.put(sqlbv20, "DELETE");

        String deledor = "delete from lpedormain where 1=1 "
                         + " and edorno = '?edorno?'"
                         + " and contno = '?contno?'";
        SQLwithBindVariables sqlbv21=new SQLwithBindVariables();
        sqlbv21.sql(deledor);
        sqlbv21.put("edorno", mLPEdorItemSchema.getEdorNo());
        sqlbv21.put("contno", mLPEdorItemSchema.getContNo());
        map.put(sqlbv21, "DELETE");

        logger.debug("after PEdorTSCancelBL =========");

        return true;
    }


    /**
     * 准备往后层输出所需要的数据
     * @return boolean
     */
    private boolean prepareData() {
        mResult.clear();
        mResult.add(map);
        return true;
    }

    /**
     * 数据输出，供外层获取数据处理结果
     * @return VData
     */
    public VData getResult() {
        return mResult;
    }

    /**
     * 错误返回，供外层获取处理错误信息
     * @return CErrors
     */
    public CErrors getErrors() {
        return mErrors;
    }

    public static void main(String[] args) {
        VData tVD = new VData();
        GlobalInput tGI = new GlobalInput();
        tGI.Operator = "lee";
        tGI.ManageCom = "86";
        LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
        tLPEdorItemSchema.setEdorNo("6020051229000095");
        tLPEdorItemSchema.setEdorType("RE");
        tLPEdorItemSchema.setContNo("NJ020126011000957");
        tVD.add(tGI);
        tVD.add(tLPEdorItemSchema);
        PEdorTSCancelBL tLPEdorTSCancelBL = new PEdorTSCancelBL();
        tLPEdorTSCancelBL.submitData(tVD, "");

        PubSubmit tPubSubmit = new PubSubmit();
        tPubSubmit.submitData(tLPEdorTSCancelBL.getResult(), "");

    }

}
