package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;

/**
 * <p>Title: 理赔人工核保险种结论 </p>
 * <p>Description: 人工核保险种结论保存 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: SinoSoft</p>
 * @author 张星
 * @version 1.0
 */

public class LLSecondUWRiskBL
{
private static Logger logger = Logger.getLogger(LLSecondUWRiskBL.class);

    //错误处理类，每个需要错误处理的类中都放置该类
    public CErrors mErrors = new CErrors();
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    private VData mInputData;
    private GlobalInput tGI = new GlobalInput();
    private MMap map = new MMap();
    private String mOperate="";
    /** 业务操作类 */
    private LLUWMasterSchema mLLUWMasterSchema = new LLUWMasterSchema();
    /** 业务操作字符串 */
    private String mCurrentDate= PubFun.getCurrentDate();
    private String mCurrentTime= PubFun.getCurrentTime();
    public LLSecondUWRiskBL()
    {
    }

    /**
     * 传输数据的公共方法
     * @param cInputData VData
     * @param cOperate String
     * @return boolean
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        //将操作数据拷贝到本类中
        mOperate = cOperate;
        logger.debug("Operate==" + cOperate);
        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData))
        {
            return false;
        }
        logger.debug("----------After getinputdata----------------");

        if (!checkData())
        {
            return false;
        }
        logger.debug("-------------After checkData---------------");
        //进行业务处理
        if (!dealData())
        {
            return false;
        }
        logger.debug("-----------------After dealData！-----------------");
        //准备往后台的数据
        if (!prepareOutputData())
        {
            return false;
        }
        logger.debug("----------------After prepareOutputData----------------");
        PubSubmit tPubSubmit = new PubSubmit();
        if (!tPubSubmit.submitData(mResult, cOperate))
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tPubSubmit.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLSecondUWRiskBL";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        logger.debug("----------PubSubmit end------------");
        return true;
    }

    /**
         * checkData
         *
         * @return boolean
         */
        private boolean checkData()
        {
            return true;
        }


        /**
         * getInputData
         * ** @param cInputData VData
         * * @return boolean
         */
        private boolean getInputData(VData cInputData)
        {
            // 公用变量
            tGI = (GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0);
            mLLUWMasterSchema = (LLUWMasterSchema) cInputData.
                                getObjectByObjectName("LLUWMasterSchema", 0);
            if (tGI == null )
            {
                // @@错误处理
                //this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
                CError tError = new CError();
                tError.moduleName = "LLSecondUWRiskBL";
                tError.functionName = "getInputData";
                tError.errorMessage = "前台传输全局公共数据tGI失败!";
                this.mErrors.addOneError(tError);
                return false;
            }

            if (mLLUWMasterSchema == null)
            {
                // @@错误处理
                //this.mErrors.copyAllErrors( tLCGrpContDB.mErrors );
                CError tError = new CError();
                tError.moduleName = "LLSecondUWRiskBL";
                tError.functionName = "getInputData";
                tError.errorMessage = "前台传输数据LLUWMasterSchema失败!";
                this.mErrors.addOneError(tError);
                return false;
            }
            return true;
    }


    /**
     * dealData
     *
     * @return boolean
     */
    private boolean dealData()
    {
        //[个人险种表---（LCPol）]
        LCPolDB tLCPolDB = new LCPolDB();
        tLCPolDB.setPolNo(mLLUWMasterSchema.getPolNo());
        if (!tLCPolDB.getInfo())
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLSecondUWRiskBL";
            tError.functionName = "dealData";
            tError.errorMessage = "查询险种保单表失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
//        mLCPolSchema = tLCPolDB.getSchema();
      LLUWMasterSchema tLLUWMasterSchema= new LLUWMasterSchema();
      tLLUWMasterSchema.setSchema(mLLUWMasterSchema);
      //分案号<CaseNo>、批次号<BatNo>、保单号码<PolNo>、险种核保结论《PassFlag》 、核保意见《UWIdea》 由前台传入
      //计算核保顺序号
      //个人理陪险种核保核保轨迹表<LLUWSub>
      LLUWSubSet tLLUWSubSet=new LLUWSubSet();
      LLUWSubDB tLLUWSubDB = new LLUWSubDB();
      tLLUWSubDB.setPolNo(mLLUWMasterSchema.getPolNo());//合同号码
      tLLUWSubDB.setCaseNo(mLLUWMasterSchema.getCaseNo());//分案号
      tLLUWSubDB.setBatNo(mLLUWMasterSchema.getBatNo());//批次号
//      tLLUWSubDB.getInfo();
      tLLUWSubSet.set(tLLUWSubDB.query());
      if(tLLUWSubSet.size()==0)
      {
          tLLUWMasterSchema.setUWNo("1");//核保顺序
      }
      else
      {
          int tUWNo=tLLUWSubSet.size()+1;
          tLLUWMasterSchema.setUWNo(tUWNo);//核保顺序
      }

      tLLUWMasterSchema.setGrpContNo(tLCPolDB.getGrpContNo());
      tLLUWMasterSchema.setContNo(tLCPolDB.getContNo());
      tLLUWMasterSchema.setProposalContNo(tLCPolDB.getProposalNo());
      tLLUWMasterSchema.setPolNo(tLCPolDB.getPolNo());
      tLLUWMasterSchema.setProposalNo(tLCPolDB.getProposalNo());
      tLLUWMasterSchema.setInsuredNo(tLCPolDB.getInsuredNo());
      tLLUWMasterSchema.setInsuredName(tLCPolDB.getInsuredName());
      tLLUWMasterSchema.setAppntNo(tLCPolDB.getAppntNo());
      tLLUWMasterSchema.setAppntName(tLCPolDB.getAppntName());
      tLLUWMasterSchema.setAgentCode(tLCPolDB.getAgentCode());
      tLLUWMasterSchema.setAgentGroup(tLCPolDB.getAgentGroup());
      tLLUWMasterSchema.setManageCom(tGI.ManageCom);
      tLLUWMasterSchema.setOperator(tGI.Operator);
      tLLUWMasterSchema.setMakeDate(mCurrentDate);
      tLLUWMasterSchema.setMakeTime(mCurrentTime);
      tLLUWMasterSchema.setModifyDate(mCurrentDate);
      tLLUWMasterSchema.setModifyTime(mCurrentTime);
      map.put(tLLUWMasterSchema,"DELETE&INSERT");

      //个人理陪险种核保核保轨迹表<LLUWSub>
      LLUWSubSchema tLLUWSubSchema=new LLUWSubSchema();
      Reflections tRef = new Reflections(); //[声明转移表的数据的对象]
      //[LLUWMaster(个人理陪险种核保最近结果表)---->LLUWSub(个人理陪险种核保核保轨迹表)]
      tRef.transFields(tLLUWSubSchema, tLLUWMasterSchema);
      map.put(tLLUWSubSchema, "INSERT");
      return true;
    }

    /**
      * prepareOutputData
      *
      * @return boolean
      */
     private boolean prepareOutputData()
     {
       try
       {
           mResult.clear();
           mResult.add(tGI);
           mResult.add(map);
           return true;
       }
       catch (Exception ex)
       {
           // @@错误处理
           CError tError = new CError();
           tError.moduleName = "LLSecondUWRiskBL";
           tError.functionName = "prepareOutputData";
           tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
           this.mErrors.addOneError(tError);
           return false;
       }

    }


}
