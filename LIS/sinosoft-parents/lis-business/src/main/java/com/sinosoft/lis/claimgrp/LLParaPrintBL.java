/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.LLParaPrintSchema;
import com.sinosoft.lis.schema.LLPRTManagerSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LLParaPrintSet;
import com.sinosoft.lis.vschema.LLPRTManagerSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.f1print.PrintService;


/**
 * <p>Title: Web业务系统</p>
 * <p>Description:结案时单证打印业务类</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author: yuejw
 * @version 1.0
 */
public class LLParaPrintBL
{
private static Logger logger = Logger.getLogger(LLParaPrintBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public  CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData ;
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    private MMap map = new MMap();
    /** 数据操作字符串 */
    private String mOperate;
    private GlobalInput mG = new GlobalInput();
    private TransferData mTransferData = new TransferData();
    private LLParaPrintSchema mLLParaPrintSchema = new LLParaPrintSchema();
    private LLPRTManagerSchema mLLPRTManagerSchema = new LLPRTManagerSchema();
    private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
    private LLParaPrintSet mLLParaPrintSet = new LLParaPrintSet();
    private LLPRTManagerSet mLLPRTManagerSet = new LLPRTManagerSet();
    private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();

    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();
    private String mClmNo;  //赔案号
    private String mCustomerNo;//客户号
    private String mPrtCode;//单证号
    private String mPath;//模版路径
    private String mPrtSeq;//流水号
    public LLParaPrintBL()
    {

    }

    public static void main(String[] args)
    {

    }

    /**
    * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
    * @param cInputData 传入的数据,VData对象
    * @param cOperate 数据操作字符串
    * @return 布尔值（true--提交成功, false--提交失败）
    */
    public boolean submitData(VData cInputData,String cOperate)
    {
        logger.debug("----------LLParaPrintBL begin submitData----------");
        //将操作数据拷贝到本类中
        mInputData = (VData)cInputData.clone() ;
        mOperate = cOperate;

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData())
            return false;
        logger.debug("----------after getInputData----------");

        //检查数据合法性
        if (!checkInputData())
        {
            return false;
        }
        logger.debug("----------after checkInputData----------");
        //进行业务处理
        if (!dealData(cOperate))
        {
            return false;
        }
        logger.debug("----------after dealData----------");
        //准备往后台的数据
        if (!prepareOutputData())
        {
            return false;
        }
        logger.debug("----------after prepareOutputData----------");
        PubSubmit tPubSubmit = new PubSubmit();
        if (!tPubSubmit.submitData(mInputData, cOperate))
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tPubSubmit.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLParaPrintBL";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors.addOneError(tError);
            return false;
        }

        mInputData = null;
        return true;
    }



    /**
     * 从输入数据中得到所有对象
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData()
    {
        logger.debug("--start getInputData()");
        //获取页面信息
        mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);//按类取值
        mTransferData = (TransferData)mInputData.getObjectByObjectName("TransferData", 0);
//        mLLParaPrintSchema = (LLParaPrintSchema)mInputData.getObjectByObjectName("LLParaPrintSchema", 0);
        if (mG == null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLParaPrintBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "接受的公共登陆信息全部为空!";
            this.mErrors.addOneError(tError);
            return false;
        }

        if (mTransferData == null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLParaPrintBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "接受的赔案号和客户号信息全部为空!";
            this.mErrors.addOneError(tError);
            return false;
        }
//        if (mOperate.equals("SinglePrt||Print") &&  mLLParaPrintSchema== null)
//        {
//            // @@错误处理
//            CError tError = new CError();
//            tError.moduleName = "LLParaPrintBL";
//            tError.functionName = "getInputData";
//            tError.errorMessage = "接受的单证号信息全部为空!";
//            this.mErrors.addOneError(tError);
//            return false;
//        }

//        mClmNo=(String) mTransferData.getValueByName("ClmNo");
//        mCustomerNo=(String) mTransferData.getValueByName("CustNo");
        mPath=(String) mTransferData.getValueByName("Path");
        mPrtSeq=(String) mTransferData.getValueByName("PrtSeq");
        return true;
    }

    /**
     * 校验传入的报案信息是否合法
     * 输出：如果发生错误则返回false,否则返回true
     */
    private boolean checkInputData()
    {
        logger.debug("----------begin checkInputData----------");
        try
        {
            //非空字段检验
            return true;
        }
        catch (Exception ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLParaPrintBL";
            tError.functionName = "checkInputData";
            tError.errorMessage = "在校验输入的数据时出错!";
            this.mErrors.addOneError(tError);
            return false;
        }

    }

    /**
     * 数据操作类业务处理
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean dealData(String cOperate)
    {
        logger.debug("------start dealData-----"+cOperate);
        try
        {
//            //单个单证打印
//            if(cOperate.equals("SinglePrt||Print"))
//            {
                    //从 打印管理表（LOPRTManager）中查询出“单证类型”传入打印类中
                LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
                LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
                tLOPRTManagerDB.setPrtSeq(mPrtSeq);
                tLOPRTManagerDB.getInfo();
                tLOPRTManagerSchema.setSchema(tLOPRTManagerDB.getSchema());

                //根据“单证类型”从 打印参数表（LLParaPrint）查询需要的打印类
                String tPrtBL = ""; //打印服务类
                LLParaPrintDB tLLParaPrintDB = new LLParaPrintDB();
                tLLParaPrintDB.setPrtCode(tLOPRTManagerSchema.getCode());
                tLLParaPrintDB.getInfo();
                tPrtBL = tLLParaPrintDB.getPrtBL();
                if (tLLParaPrintDB.getCount() != 1 || tPrtBL.equals("")) {
                    CError tError = new CError();
                    tError.moduleName = "LLParaPrintBL";
                    tError.functionName = "dealdata";
                    tError.errorMessage = "在参数表查询打印服务类参数失败!";
                    this.mErrors.addOneError(tError);
                    return false;
                }
                logger.debug("单证" + tLLParaPrintDB.getPrtName() +
                                   "的打印服务类为" + tPrtBL);
                logger.debug("######------在参数表查询打印服务类参数成功------########");

                //为打印服务类准备参数，使用TransferData打包后提交-----用于传送“打印流水号”，“模版路径”
                TransferData tTransferData = new TransferData();
                tTransferData.setNameAndValue("PrtSeq", mPrtSeq); //打印流水号
                tTransferData.setNameAndValue("Path", mPath); //模版路径

                logger.debug("######------以下调用打印服务类------########");
                Class cls = Class.forName(tPrtBL);
                PrintService ps = (PrintService) cls.newInstance();
                VData vData = new VData();
                vData.add(mG);
                vData.add(tTransferData);
                if (!ps.submitData(vData, ""))
                {
                    mErrors.copyAllErrors(ps.getErrors());
                    return false;
                }
                else
                {
                    mResult = ps.getResult();
                }

//            }

            return true;
        }
        catch (Exception ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLParaPrintBL";
            tError.functionName = "dealData";
            tError.errorMessage = "在处理输入的数据时出错!";
            this.mErrors.addOneError(tError);
            return false;
        }
    }

    /**
        * 单个单证打印时，根据传入的“单证代码----PrtCode”获取相应的服务类
        * 批单或清单打印时，根据传入的“单证子类型----PrtSubType”获取相应的服务类
        * @return boolean
     */
    private boolean getPrintService()
    {
//        if(mOperate.equals("SinglePrt||Print"))
//        {
//
//        }
//
//        //“批单”打印（BatchPrtB||Print）；//“清单”打印（BatchPrtC||Print）
//        if(mOperate.equals("BatchPrtB||Print") ||mOperate.equals("BatchPrtC||Print"))
//        {
//            String tPrtSubType="";//单证子类型
//            //“批单”打印（PrtSubType----B）
//            if(mOperate.equals("BatchPrtB||Print")){tPrtSubType="B";}
//            //“清单”打印（PrtSubType----C）
//            if(mOperate.equals("BatchPrtC||Print")){tPrtSubType="C";}
//        }
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
            return true;
        }
        catch (Exception ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLParaPrintBL";
            tError.functionName = "prepareOutputData";
            tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
            this.mErrors.addOneError(tError);
            return false;
        }
    }

    public VData getResult()
    {
        return mResult;
    }

}
