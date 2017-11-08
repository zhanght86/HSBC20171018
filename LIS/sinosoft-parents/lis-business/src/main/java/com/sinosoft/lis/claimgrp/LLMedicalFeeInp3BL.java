/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.vschema.LLFeeMainSet;
import com.sinosoft.lis.db.LLFeeMainDB;
import com.sinosoft.lis.schema.LLFeeMainSchema;
import com.sinosoft.lis.vschema.LLCaseInfoSet;
import com.sinosoft.lis.db.LLCaseInfoDB;
import com.sinosoft.lis.schema.LLCaseInfoSchema;
import com.sinosoft.lis.vschema.LLCommendHospitalSet;
import com.sinosoft.lis.db.LLCommendHospitalDB;
import com.sinosoft.lis.schema.LLCommendHospitalSchema;


/**
 * <p>Title: Web业务系统</p>
 * <p>Description:提交理赔伤残单证类</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author:续涛
 * @version 1.0
 */

public class LLMedicalFeeInp3BL
{
private static Logger logger = Logger.getLogger(LLMedicalFeeInp3BL.class);

    public CErrors mErrors = new CErrors(); /** 错误处理类，每个需要错误处理的类中都放置该类 */
    private VData mInputData = new VData(); /** 往后面传输数据的容器 */
    private VData mResult = new VData(); /** 往界面传输数据的容器 */
    private String mOperate; /** 数据操作字符串 */
    private MMap map = new MMap(); /** 往后面传输的数据库操作 */

    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();

    private GlobalInput mGlobalInput = new GlobalInput(); /** 全局数据 */
    private TransferData mTransferData = new TransferData();

    private LLCaseInfoSchema mLLCaseInfoSchema = new LLCaseInfoSchema();

    public LLMedicalFeeInp3BL()
    {}

    /**
     * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
     * @param cInputData 传入的数据,VData对象
     * @param cOperate 数据操作字符串
     * @return 布尔值（true--提交成功, false--提交失败）
     */

    public boolean submitData(VData cInputData, String cOperate)
    {
        logger.debug("----------LLMedicalFeeInp3BL Begin----------");
        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData, cOperate))
        {
            return false;
        }

        //检查数据合法性
        if (!checkData())
        {
            return false;
        }

        //进行业务处理
        if (!dealData())
        {
            return false;
        }

        //准备往后台的数据
        if (!prepareOutputData())
        {
            return false;
        }

        //  进行数据提交
        PubSubmit tPubSubmit = new PubSubmit();
        if (!tPubSubmit.submitData(mInputData, ""))
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tPubSubmit.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLMedicalFeeInp3BL";
            tError.functionName = "PubSubmit.submitData";
            tError.errorMessage = "PubSubmit数据提交失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        mInputData = null;

        logger.debug("----------LLMedicalFeeInp3BL End----------");
        return true;
    }


    /**
     * 从输入数据中得到所有对象
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData, String cOperate)
    {

        mInputData = cInputData;
        this.mOperate = cOperate;

        mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
                "GlobalInput", 0));
        mLLCaseInfoSchema = (LLCaseInfoSchema) mInputData.
                               getObjectByObjectName("LLCaseInfoSchema", 0);

        return true;
    }

    /**
     * 校验传入的信息是否合法
     * 输出：如果发生错误则返回false,否则返回true
     */
    private boolean checkData()
    {

        if (mGlobalInput == null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLMedicalFeeInp3BL";
            tError.functionName = "checkData";
            tError.errorMessage = "前台传输全局公共数据失败!";
            this.mErrors.addOneError(tError);
            return false;
        }

        //获得操作员编码
        String tOperator = mGlobalInput.Operator;
        if (tOperator == null || tOperator.trim().equals(""))
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLMedicalFeeInp3BL";
            tError.functionName = "checkData";
            tError.errorMessage = "前台传输全局公共数据[操作员编码]失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    /**
     * 数据操作类业务处理
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean dealData()
    {

        //进行医疗费用的录入
        if (this.mOperate.equals("INSERT"))
        {
            if (!medicalInsert())
            {
                return false;
            }
        }

        //进行医疗费用的删除
        if (this.mOperate.equals("DELETE"))
        {
            LLCaseInfoDB tLLCaseInfoDB = new LLCaseInfoDB();
            tLLCaseInfoDB.setClmNo(mLLCaseInfoSchema.getClmNo()); //赔案号
            tLLCaseInfoDB.setCaseNo(mLLCaseInfoSchema.getCaseNo()); //分案号
            tLLCaseInfoDB.setCustomerNo(mLLCaseInfoSchema.getCustomerNo()); //客户号
            tLLCaseInfoDB.setSerialNo(mLLCaseInfoSchema.getSerialNo()); //编号

            LLCaseInfoSet tLLCaseInfoSet = new LLCaseInfoSet();
            tLLCaseInfoSet = tLLCaseInfoDB.query();
            if (tLLCaseInfoSet == null && tLLCaseInfoSet.size() == 0)
            {
                //@@错误处理
                CError tError = new CError();
                tError.moduleName = "LLMedicalFeeInp3BL";
                tError.functionName = "dealData";
                tError.errorMessage = "查询LLCaseInfo表失败!";
                this.mErrors.addOneError(tError);
                return false;
            }
            else
            {
                map.put(tLLCaseInfoSet, "DELETE");
            }
        }
        return true;
    }

    /**
     * 进行医疗费用的录入
     * @return
     */
    private boolean medicalInsert()
    {
        //生成序号流水号
        String tSerialNo = PubFun1.CreateMaxNo("SerialNo3", 10);
        mLLCaseInfoSchema.setSerialNo(tSerialNo);

        mLLCaseInfoSchema.setOperator(mGlobalInput.Operator);
        mLLCaseInfoSchema.setMngCom(mGlobalInput.ManageCom);
        mLLCaseInfoSchema.setMakeDate(CurrentDate);
        mLLCaseInfoSchema.setMakeTime(CurrentTime);
        mLLCaseInfoSchema.setModifyDate(CurrentDate);
        mLLCaseInfoSchema.setModifyTime(CurrentTime);

        map.put(mLLCaseInfoSchema, "DELETE&INSERT");

        return true;
    }

    /**
     * 准备需要保存的数据
     * @return boolean
     */
    private boolean prepareOutputData()
    {
        try
        {
            mInputData.clear();
            mInputData.add(map);
//            mResult.add(mLLAccidentSchema);//供前台界面使用
        }
        catch (Exception ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLCaseInfoBL";
            tError.functionName = "prepareOutputData";
            tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    /**
     * 提供返回前台界面的数据
     * @return
     */
    public VData getResult()
    {
        return mResult;
    }

    /**
     * 用于测试
     * @return
     */
    public static void main(String[] args)
    {
    }
}
