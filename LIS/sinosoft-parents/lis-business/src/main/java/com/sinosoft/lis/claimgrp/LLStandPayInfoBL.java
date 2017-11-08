/*
 * <p>ClassName: LLStandPayInfoBL </p>
 * <p>Description: LLStandPayInfoBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: testcompany </p>
 * @Database:
 * @Author：pd
 * @CreateDate：2005-11-1
 */
package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.service.BusinessService;
public class LLStandPayInfoBL implements BusinessService{
private static Logger logger = Logger.getLogger(LLStandPayInfoBL.class);
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
    private LLStandPayInfoSet mLLStandPayInfoSet = new LLStandPayInfoSet();
    public LLStandPayInfoBL() {
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
            tError.moduleName = "LLStandPayInfoBL";
            tError.functionName = "submitData";
            tError.errorMessage = "数据处理失败LLStandPayInfoBL-->getInputData!";
            this.mErrors.addOneError(tError);
            return false;

        }
        logger.debug(
                "----------LLStandPayInfoBL after getInputData----------");
        //进行业务处理
        if (!dealData()) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLStandPayInfoBL";
            tError.functionName = "submitData";
            tError.errorMessage = "数据处理失败LLStandPayInfoBL-->dealData!";
            this.mErrors.addOneError(tError);
            return false;
        }
        logger.debug(
                "----------LLStandPayInfoBL after dealData----------");
        //准备往后台的数据
        if (!prepareOutputData()) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLStandPayInfoBL";
            tError.functionName = "submitData";
            tError.errorMessage = "数据处理失败LLStandPayInfoBL-->submitquery!";
            this.mErrors.addOneError(tError);
            return false;
        }
        logger.debug(
                "----------LLStandPayInfoBL after prepareOutputData----------");
        PubSubmit tPubSubmit = new PubSubmit();
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

        mInputData = null;
        return true;
    }

    /**
     * 根据前面的输入数据，进行BL逻辑处理
     * 如果在处理过程中出错，则返回false,否则返回true
     */
    private boolean dealData() {
        if (this.mOperate.equals("INSERT||MAIN")) {
            if (mLLStandPayInfoSet != null && mLLStandPayInfoSet.size() > 0) {
                String SQL = "";
                for (int i = 1; i <= mLLStandPayInfoSet.size(); i++) {
                    mLLStandPayInfoSet.get(i).setMakeDate(PubFun.getCurrentDate());
                    mLLStandPayInfoSet.get(i).setMakeTime(PubFun.getCurrentTime());
                    mLLStandPayInfoSet.get(i).setModifyDate(PubFun.getCurrentDate());
                    mLLStandPayInfoSet.get(i).setModifyTime(PubFun.getCurrentTime());
                    mLLStandPayInfoSet.get(i).setOperator(mGlobalInput.Operator);
                    mLLStandPayInfoSet.get(i).setManageCom(mGlobalInput.ManageCom);
                    //mLLStandPayInfoSet.get(i).setCurrency("01");//add liupeng by 20100908
                    //先删除原先的记录在从新写入
                    SQL = "delete from LLStandPayInfo where CaseNo = '" +
                          mLLStandPayInfoSet.get(i).getCaseNo()+ "'";
                }
                map.put(SQL, "DELETE");
                map.put(mLLStandPayInfoSet, "INSERT");
            }
        }
        if (this.mOperate.equals("UPDATE||MAIN")) {
            if (mLLStandPayInfoSet != null && mLLStandPayInfoSet.size() > 0) {
                for (int i = 1; i <= mLLStandPayInfoSet.size(); i++) {
                    mLLStandPayInfoSet.get(i).setMakeDate(PubFun.getCurrentDate());
                    mLLStandPayInfoSet.get(i).setMakeTime(PubFun.getCurrentTime());
                    mLLStandPayInfoSet.get(i).setModifyDate(PubFun.getCurrentDate());
                    mLLStandPayInfoSet.get(i).setModifyTime(PubFun.getCurrentTime());
                    mLLStandPayInfoSet.get(i).setOperator(mGlobalInput.Operator);
                    mLLStandPayInfoSet.get(i).setManageCom(mGlobalInput.ManageCom);
                }
                map.put(mLLStandPayInfoSet, "UPDATE");
            }

        }

        return true;
    }

    /**
     * 从输入数据中得到所有对象
     *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData) {
        this.mLLStandPayInfoSet.set((LLStandPayInfoSet) cInputData.getObjectByObjectName(
                "LLStandPayInfoSet", 0));
        for(int i=1;i<=mLLStandPayInfoSet.size();i++){
        	if(mLLStandPayInfoSet.get(i).getCaseNo() == null ||"".equals(mLLStandPayInfoSet.get(i).getCaseNo())){
        		CError.buildErr(this, "赔案号缺失，无法进行保存操作！");
        		return false;
        	}
        }
        this.mGlobalInput.setSchema((GlobalInput) cInputData.
                                    getObjectByObjectName("GlobalInput", 0));
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
            mResult.clear();
            mResult.add(this.mLLStandPayInfoSet);
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

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}
}
