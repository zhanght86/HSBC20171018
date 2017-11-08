/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.LLInqApplySchema;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import com.sinosoft.lis.tb.LCSignLogBL;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.schema.*;
/**
 * <p>Title: 调查申请确认信息</p>
 * <p>Description: 调查申请确认逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author: zl
 * @version 1.0
 */
public class LLInqConfirmBL
{
private static Logger logger = Logger.getLogger(LLInqConfirmBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public  CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData ;
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;

    private MMap map = new MMap();
    private String mIsReportExist;

    private TransferData mTransferData = new TransferData();
    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();
    private LLInqApplySchema mLLInqApplySchema= new LLInqApplySchema();
    private GlobalInput mG = new GlobalInput();

    private String tInqState;//调查状态
    private String tInqConclusion;//调查结论
	private String tempOperate;
    public LLInqConfirmBL()
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
        logger.debug("----------LLInqConfirmBL begin submitData----------");
        //将操作数据拷贝到本类中
        mInputData = (VData)cInputData.clone() ;
        this.mOperate = cOperate;

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
            tError.moduleName = "LLInqConfirmBL";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        mInputData = null;
        return true;
    }

    public VData getResult()
    {
        return mResult;
    }

    /**
     * 从输入数据中得到所有对象
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData()
    {
        logger.debug("--start getInputData()");
        //获取页面报案信息
        mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);//按类取值
        mLLInqApplySchema = (LLInqApplySchema) mInputData.getObjectByObjectName("LLInqApplySchema", 0);
        mTransferData = (TransferData) mInputData.getObjectByObjectName("TransferData",0);
        if (mLLInqApplySchema == null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLInqConfirmBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "接受的信息全部为空!";
            this.mErrors.addOneError(tError);
            return false;
        }
          tInqState=mLLInqApplySchema.getInqState();//调查状态
          tInqConclusion=mLLInqApplySchema.getInqConclusion();//调查结论
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
            if (mLLInqApplySchema.getClmNo() == null)//赔案号
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LLInqConfirmBL";
                tError.functionName = "checkInputData";
                tError.errorMessage = "接受的赔案号为空!";
                this.mErrors.addOneError(tError);
                return false;
            }
            if (mLLInqApplySchema.getInqNo() == null)//调查序号
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LLInqConfirmBL";
                tError.functionName = "checkInputData";
                tError.errorMessage = "接受的调查序号为空!";
                this.mErrors.addOneError(tError);
                return false;
            }
        }
        catch (Exception ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLInqConfirmBL";
            tError.functionName = "checkInputData";
            tError.errorMessage = "在校验输入的数据时出错!";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    /**
     * 数据操作类业务处理
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean dealData(String cOperate)
    {
        logger.debug("----------begin dealData----------"+cOperate);
        boolean tReturn = false;
         //修改纪录
        if (cOperate.equals("UPDATE"))
        {
                //调查申请表
                LLInqApplyDB tLLInqApplyDB = new LLInqApplyDB();
                tLLInqApplyDB.setClmNo(mLLInqApplySchema.getClmNo());
                tLLInqApplyDB.setInqNo(mLLInqApplySchema.getInqNo());
                tLLInqApplyDB.getInfo();
                if (tLLInqApplyDB.getInfo() == false)
                {
                    // @@错误处理
                    CError tError = new CError();
                    tError.moduleName = "LLInqConfirmBL";
                    tError.functionName = "LLInqConfirmBL";
                    tError.errorMessage = "无调查申请信息!";
                    this.mErrors.addOneError(tError);
                    return false;
                }
                mLLInqApplySchema=tLLInqApplyDB.getSchema();
                mLLInqApplySchema.setInqState(tInqState);//调查状态
                mLLInqApplySchema.setInqConclusion(tInqConclusion);//调查结论
                mLLInqApplySchema.setInqEndDate(CurrentDate);//调查结束时间
                mLLInqApplySchema.setConPer(mG.Operator);//结论确定人
                mLLInqApplySchema.setConDate(CurrentDate); //结论确定时间
                mLLInqApplySchema.setModifyTime(CurrentTime);
                mLLInqApplySchema.setModifyDate(CurrentDate);
                map.put(mLLInqApplySchema, "UPDATE");
                tReturn = true;
        }
        /**
		 * （一）若调查人为辅助岗，则由其上级审核人录入。
		 * （二）若调查人为签批人或审核人，则由其本人录入。
		 * */
		tempOperate=mG.Operator;
		String tDefaultOperator = getDefaultOperator();
		if("false".equals(tDefaultOperator)){
			return false;
		}
		mTransferData.setNameAndValue("Operator", tDefaultOperator);
        return tReturn;
    }

    /**
     * 准备需要保存的数据
     * @return boolean
     */
    private boolean prepareOutputData()
    {
        try
        {
            mResult.add(map);
            mResult.add(mTransferData);
            mResult.add(mG);
        }
        catch (Exception ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLInqConfirmBL";
            tError.functionName = "prepareOutputData";
            tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }
    /**
	 * 获得有审核或审批权限的操作员
	 * */
	private String getDefaultOperator(){
		String tDefaultOperator="";
		LLClaimUserDB tLLClaimUserDB = new LLClaimUserDB();
		tLLClaimUserDB.setUserCode(tempOperate);
		if(!tLLClaimUserDB.getInfo()){
			CError.buildErr(this, "当前用户没有调查日志录入权限！");
			return "false";
		}
		if((!"1".equals(tLLClaimUserDB.getCheckFlag()))&&(!"1".equals(tLLClaimUserDB.getUWFlag()))){
			tDefaultOperator="";
//			tempOperate=tLLClaimUserDB.getUserCode();
			//zy 获取上层机构编码，不是本身操作员代码（死循环），
			tempOperate=tLLClaimUserDB.getUpUserCode();
		}else{
			tDefaultOperator = tLLClaimUserDB.getUserCode();
			tempOperate = tLLClaimUserDB.getUserCode();
		}
		if("".equals(tDefaultOperator)){
//			getDefaultOperator();
			tDefaultOperator=getDefaultOperator();
		}
		return tDefaultOperator;
	}
}
