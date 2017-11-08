/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

import com.sinosoft.lis.vschema.LLParaDeformitySet;
import com.sinosoft.lis.db.LLParaDeformityDB;
import com.sinosoft.lis.schema.LLParaDeformitySchema;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description:提交理赔伤残等级参数信息类</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author:
 * @version 1.0
 */

public class LLParaDeformityBL
{
private static Logger logger = Logger.getLogger(LLParaDeformityBL.class);



    public  CErrors mErrors = new CErrors();        /** 错误处理类，每个需要错误处理的类中都放置该类 */
    private VData mInputData = new VData();         /** 往后面传输数据的容器 */
    private VData mResult = new VData();            /** 往界面传输数据的容器 */
    private String mOperate;                        /** 数据操作字符串 */


    private MMap map = new MMap();                  /** 往后面传输的数据库操作 */
    private MMap mapDel = new MMap();               /** 执行删除的数据库操作，放在最后 */


    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();

    private GlobalInput mGlobalInput = new GlobalInput();    /** 全局数据 */
    private TransferData mTransferData = new TransferData();

//    private LLClaimPopedomDB mLLClaimPopedomDB = new LLClaimPopedomDB();

    private LLParaDeformitySchema mLLParaDeformitySchema = new LLParaDeformitySchema();


    public LLParaDeformityBL(){}

    /**
    * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
    * @param cInputData 传入的数据,VData对象
    * @param cOperate 数据操作字符串
    * @return 布尔值（true--提交成功, false--提交失败）
    */

    public boolean submitData(VData cInputData,String cOperate)
    {
        logger.debug("----------LLParaDeformityBL Begin----------");
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
            tError.moduleName = "LLParaDeformityBL";
            tError.functionName = "PubSub mit.submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        mInputData = null;

        logger.debug("----------LLParaDeformityBL End----------");
        return true;
    }



    /**
     * 从输入数据中得到所有对象
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData,String cOperate)
    {

        mInputData = cInputData;
        this.mOperate = cOperate;

        logger.debug("LLParaDeformityBL测试--"+this.mOperate);

        mGlobalInput.setSchema((GlobalInput)mInputData.getObjectByObjectName("GlobalInput", 0));

        //mTransferData = (TransferData)mInputData.getObjectByObjectName("TransferData", 0);
        //mLCUWSendTraceSchema = (LCUWSendTraceSchema) mTransferData.getValueByName("LCUWSendTraceSchema");

        mLLParaDeformitySchema = (LLParaDeformitySchema) mInputData.getObjectByObjectName("LLParaDeformitySchema", 0);
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
            tError.moduleName = "LLParaDeformityBL";
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
            tError.moduleName = "LLParaDeformityBL";
            tError.functionName = "checkData";
            tError.errorMessage = "前台传输全局公共数据[操作员编码]失败!";
            this.mErrors.addOneError(tError);
            return false;
        }

        if (mLLParaDeformitySchema == null )
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLParaDeformityBL";
            tError.functionName = "checkData";
            tError.errorMessage = "接受的Schema信息全部为空!";
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
        boolean tReturn = false;

        //  进行伤残等级参数的录入
        if (this.mOperate.equals("Deformity||INSERT"))
        {
            if (!DeformityInsert())
            {
                return false;
            }
        }

        //  进行伤残等级参数的删除
        if (this.mOperate.equals("Deformity||DELETE"))
        {
            if (!DeformityDelete())
            {
                return false;
            }
        }
        return true;
    }

    /**
     * 进行伤残等级参数的录入
     * @return
     */
    private boolean DeformityInsert()
    {
        //  进行伤残等级参数表的数据查询
        LLParaDeformityDB tLLParaDeformityDB = new LLParaDeformityDB();

		tLLParaDeformityDB.setDefoType(mLLParaDeformitySchema.getDefoType());          //伤残类型
		tLLParaDeformityDB.setDefoCode(mLLParaDeformitySchema.getDefoCode());          //伤残代码


		LLParaDeformitySet tLLParaDeformitySet = new LLParaDeformitySet();
		tLLParaDeformitySet = tLLParaDeformityDB.query();

        if (tLLParaDeformityDB.mErrors.needDealError() == true)
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLLParaDeformityDB.mErrors );
            CError tError = new CError();
            tError.moduleName = "LLParaDeformityBL";
            tError.functionName = "DeformityInsert";
            tError.errorMessage = "伤残等级参数表查询失败!";
            this.mErrors.addOneError(tError);
            return false;
        }

        //  如果伤残等级参数表无数据，则添加数据
        if (tLLParaDeformitySet == null || tLLParaDeformitySet.size() == 0)
        {
            LLParaDeformitySchema tLLParaDeformitySchema = new LLParaDeformitySchema();

			tLLParaDeformitySchema.setDefoType(mLLParaDeformitySchema.getDefoType());
			tLLParaDeformitySchema.setDefoGrade(mLLParaDeformitySchema.getDefoGrade());
			tLLParaDeformitySchema.setDefoGradeName(mLLParaDeformitySchema.getDefoGradeName());
			tLLParaDeformitySchema.setDefoCode(mLLParaDeformitySchema.getDefoCode());
			tLLParaDeformitySchema.setDefoName(mLLParaDeformitySchema.getDefoName());
			tLLParaDeformitySchema.setDefoRate(mLLParaDeformitySchema.getDefoRate());

            map.put(tLLParaDeformitySchema, "INSERT");

        }
        else
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLParaDeformityBL";
            tError.functionName = "DeformityInsert";
            tError.errorMessage = "该条信息已存在!";
            this.mErrors.addOneError(tError);
            return false;
        }



        return true;
    }

    /**
     * 进行伤残等级参数的删除
     * @return
     */
    private boolean DeformityDelete()
    {
        LLParaDeformitySchema tLLParaDeformitySchema = new LLParaDeformitySchema();
        tLLParaDeformitySchema.setSchema(mLLParaDeformitySchema);

        map.put(tLLParaDeformitySchema, "DELETE");
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
            tError.moduleName = "LLReportBL";
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
