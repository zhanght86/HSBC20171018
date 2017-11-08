/*
 * <p>ClassName: LLGrpReceiptClassInpBL </p>
 * <p>Description: LLGrpReceiptClassInpBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: testcompany </p>
 * @Database:
 * @Author：pd
 * @CreateDate：2005-11-10
 */
package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.LLCaseReceiptClassSchema;

public class LLGrpReceiptClassInpBL {
private static Logger logger = Logger.getLogger(LLGrpReceiptClassInpBL.class);
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    private VData mResult = new VData();
    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();
    private MMap map = new MMap();
    /** 全局数据 */
    private GlobalInput mGlobalInput = new GlobalInput();
    /** 数据操作字符串 */
    private String mOperate;
    /** 业务处理相关变量 */
    private LLCaseReceiptClassSchema mLLCaseReceiptClassSchema = new LLCaseReceiptClassSchema();

    public LLGrpReceiptClassInpBL() {
    }

    public static void main(String[] args) {
    }

    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     *         cOperate 数据操作
     * @return:
     */
    public boolean submitData(VData cInputData, String cOperate) {
        //将操作数据拷贝到本类中
        this.mOperate = cOperate;
        //得到外部传入的数据,将数据备份到本类中
        logger.debug("begin getinputdata");
        if (!getInputData(cInputData)) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLGrpReceiptClassInpBL";
            tError.functionName = "submitData";
            tError.errorMessage = "数据处理失败LLGrpReceiptClassInpBL-->getInputData!";
            this.mErrors.addOneError(tError);
            return false;

        }
        //进行业务处理
        if (!dealData()) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLGrpReceiptClassInpBL";
            tError.functionName = "submitData";
            tError.errorMessage = "数据处理失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        //准备往后台的数据
        if (!prepareOutputData()) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLGrpReceiptClassInpBL";
            tError.functionName = "submitData";
            tError.errorMessage = "数据处理失败LLGrpReceiptClassInpBL-->submitquery!";
            this.mErrors.addOneError(tError);
            return false;
        }
        logger.debug(
                "----------LLGrpMedicalFeeInpBL after prepareOutputData----------");
        PubSubmit tPubSubmit = new PubSubmit();
        if (this.mOperate.equals("IMPORT||MAIN")) {
          return true;
      } else
        if (!tPubSubmit.submitData(mInputData, cOperate)) {
            // @@错误处理
            this.mErrors.copyAllErrors(tPubSubmit.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimRegisterDealBL";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        logger.debug(
                "----------LLGrpMedicalFeeInpBL after tPubSubmit.submitData----------");
        mInputData = null;
        return true;
    }

    /**
     * 根据前面的输入数据，进行BL逻辑处理
     * 如果在处理过程中出错，则返回false,否则返回true
     */
    private boolean dealData()
    {
        if (this.mOperate.equals("IMPORT||MAIN"))
        {
            //  生成社保账单费用明细流水号
            String tLLCaseRecordNo = PubFun.CreateSeq("LLCaseRecordNo");
            mLLCaseReceiptClassSchema.setID(tLLCaseRecordNo);
            mLLCaseReceiptClassSchema.setOperator(this.mGlobalInput.Operator);
            mLLCaseReceiptClassSchema.setMngCom(this.mGlobalInput.ManageCom);
            mLLCaseReceiptClassSchema.setMakeDate(PubFun.getCurrentDate());
            mLLCaseReceiptClassSchema.setMakeTime(PubFun.getCurrentTime());
            mLLCaseReceiptClassSchema.setModifyDate(PubFun.getCurrentDate());
            mLLCaseReceiptClassSchema.setModifyTime(PubFun.getCurrentTime());

            String SQL2 = "delete from llcasereceiptclass where ClmNo = '" +
                          mLLCaseReceiptClassSchema.getClmNo() +
                          "' and BillNo = '" +
                          mLLCaseReceiptClassSchema.getBillNo() + "'" +
                          " and InsuredNo = '" +
                          mLLCaseReceiptClassSchema.getInsuredNo() + "'";
            map.put(SQL2, "DELETE");

            //先删除原先的记录在从新写入
            map.put(mLLCaseReceiptClassSchema, "INSERT");

        }

        return true;
    }

    /**
     * 从输入数据中得到所有对象
     *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData) {
        this.mLLCaseReceiptClassSchema.setSchema((LLCaseReceiptClassSchema) cInputData.getObjectByObjectName( "LLCaseReceiptClassSchema", 0));
        this.mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
        return true;
    }

    /**
     * 准备往后层输出所需要的数据
     * 输出：如果准备数据时发生错误则返回false,否则返回true
     */
    private boolean prepareOutputData() {
        try {
            this.mInputData.clear();
            this.mInputData.add(map);
        } catch (Exception ex) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLBnfBL";
            tError.functionName = "prepareData";
            tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    public VData getResult() {
        return this.mResult;
    }
    public MMap getMMap() {
        return this.map;
    }
}
