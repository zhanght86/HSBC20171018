package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vschema.*;
/**
 * <p>Title: 理赔人工核保</p>
 * <p>Description: 在人工核保时为总单下核保结论 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: SinoSoft</p>
 * @author zhangxing
 * @version 1.0
 */

public class LLSecondManuUWBL
{
private static Logger logger = Logger.getLogger(LLSecondManuUWBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    /** 往工作流引擎中传输数据的容器 */
    private GlobalInput mG = new GlobalInput();
    private LLCUWMasterSchema mLLCUWMasterSchema = new LLCUWMasterSchema();
    private MMap map = new MMap();
    private String mCurrentDate =PubFun.getCurrentDate();
    private String mCurrentTime =PubFun.getCurrentTime();

    /**业务类操作*/
    public LLSecondManuUWBL()
    {
    }

    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     *         cOperate 数据操作
     * @return:
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData, cOperate))
        {
            return false;
        }

        if (!checkData())
        {
            return false;
        }

        //进行业务处理
        if (!dealData())
        {
            return false;
        }
//        if(!checkPolConfirm())
//        {
//            return false;
//        }

        //准备往后台的数据
        if (!prepareOutputData())
        {
            return false;
        }

        //提交数据

        PubSubmit tPubSubmit = new PubSubmit();
        if (!tPubSubmit.submitData(mResult, ""))
        {
                // @@错误处理
            this.mErrors.copyAllErrors(tPubSubmit.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLSecondManuUWBL";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }


    /**
     * 从输入数据中得到所有对象
     *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData, String cOperate)
    {
        //从输入数据中得到所有对象，获得全局公共数据
        mG = ((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
        mLLCUWMasterSchema = (LLCUWMasterSchema) cInputData.
                             getObjectByObjectName("LLCUWMasterSchema", 0);
        mInputData = cInputData;
        if(mG==null)
        {
            CError tError = new CError();
            tError.moduleName = "LLSecondManuUWBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "接受公共登陆数据信息失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        if(mLLCUWMasterSchema==null)
        {
            CError tError = new CError();
            tError.moduleName = "LLSecondManuUWBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "前台传入核保数据信息失败!";
            this.mErrors.addOneError(tError);
            return false;
        }

        return true;
    }
    /**
     * 校验业务数据
     * @return
     */
    private boolean checkData()
    {

       return true;
    }

   private boolean checkPolConfirm()
   {

      return true;
   }

    /**
     * 根据前面的输入数据，进行BL逻辑处理
     * 如果在处理过程中出错，则返回false,否则返回true
     */
    private boolean dealData()
    {
      //所有校验暂时不做---以后补上(体检通知书、问卷通知书、索要资料通知书)的回销检测
      //校验是否有未打印的体检通知书----暂时不做

      //(CaseNo--分案号、BatNo--批次号、ContNo--合同号、PassFlag---结论、UWIdea---核保意见)由前台传入

      //校验保单信息
      LCContSchema tLCContSchema = new LCContSchema();
      LCContDB tLCContDB = new LCContDB();
      tLCContDB.setContNo(mLLCUWMasterSchema.getContNo());//合同号
      if (!tLCContDB.getInfo()||tLCContDB.getCount()==0)
      {
          CError tError = new CError();
          tError.moduleName = "LLSecondManuUWBL";
          tError.functionName = "dealData";
          tError.errorMessage = "合同" + mLLCUWMasterSchema.getContNo() + "信息查询失败!";
          this.mErrors.addOneError(tError);
          return false;
      }
      tLCContSchema.setSchema(tLCContDB.getSchema());//合同表---用于提取数据


      //检验险种结论是否完成，根据合同号，从个人险种表---LCPol 取出该合同下所有 保单险种号码---PolNo
      //然后以“分案号 and 保单险种号码”查询 个人理陪险种核保最近结果表--- LLUWMaster 是否存在记录
      LCPolSet tLCPolSet=new LCPolSet();
      LCPolDB tLCPolDB=new LCPolDB();
      tLCPolDB.setContNo(mLLCUWMasterSchema.getContNo());//合同号
      tLCPolSet.set(tLCPolDB.query());
      logger.debug("-----------该合同下保单险种号码数目---"+tLCPolSet.size());

      String tstate="complete";//险种结论全部下完标志----自定义
      String struncomplete="该合同下未下核保结论的保单险种号码:";
      for(int i=1;i<=tLCPolSet.size();i++)
      {
          LLUWMasterDB tLLUWMasterDB = new LLUWMasterDB();
          tLLUWMasterDB.setCaseNo(mLLCUWMasterSchema.getCaseNo());
          tLLUWMasterDB.setCaseNo(mLLCUWMasterSchema.getCaseNo());
          tLLUWMasterDB.setBatNo(mLLCUWMasterSchema.getBatNo());
          tLLUWMasterDB.setPolNo(tLCPolSet.get(i).getPolNo());
          tLLUWMasterDB.getInfo();
          if(tLLUWMasterDB.getCount()==0)
          {
              struncomplete=struncomplete+"--"+tLCPolSet.get(1).getPolNo();
              tstate="uncomplete";
          }
      }

      if (tstate.equals("uncomplete"))
      {
          CError tError = new CError();
          tError.moduleName = "LLSecondManuUWBL";
          tError.functionName = "dealtData";
          tError.errorMessage = struncomplete;
          this.mErrors.addOneError(tError);
          return false;
      }

//      LLUWMasterDB tLLUWMasterDB = new LLUWMasterDB();
//      tLLUWMasterDB.setCaseNo(mLLCUWMasterSchema.getCaseNo());
//      tLLUWMasterDB.setCaseNo(mLLCUWMasterSchema.getCaseNo());
//      tLLUWMasterDB.setBatNo(mLLCUWMasterSchema.getBatNo());
//      tLLUWMasterDB.setContNo(mLLCUWMasterSchema.getContNo());
//      LLUWMasterSet tLLUWMasterSet = new LLUWMasterSet();
//      tLLUWMasterSet.set(tLLUWMasterDB.query());
//      logger.debug("-----------已经下完核保结论的保单险种号码数目---"+tLLUWMasterSet.size());
//      if (tLLUWMasterSet.size() < tLCPolSet.size())

      int tUWNo=0;//核保顺序号----计算得出
      //个人合同理赔核保核保轨迹表（LLCUWSub）
      LLCUWSubSet tLLCUWSubSet = new LLCUWSubSet();
      LLCUWSubDB tLLCUWSubDB=new LLCUWSubDB();
      tLLCUWSubDB.setCaseNo(mLLCUWMasterSchema.getCaseNo());
      tLLCUWSubDB.setBatNo(mLLCUWMasterSchema.getBatNo());
      tLLCUWSubDB.setContNo(mLLCUWMasterSchema.getContNo());
      tLLCUWSubSet.set(tLLCUWSubDB.query());
      if (tLLCUWSubSet.size() == 0)
      {
          tUWNo = 1; //核保顺序
      }
      else
      {
          tUWNo = tLLCUWSubSet.size() + 1;
      }
      logger.debug("-----------核保顺序---"+tUWNo);

      //准备个人理陪险种核保最近结果表----[LLCUWMaster]
      LLCUWMasterSchema tLLCUWMasterSchema = new LLCUWMasterSchema();
      tLLCUWMasterSchema.setSchema(mLLCUWMasterSchema);
      tLLCUWMasterSchema.setGrpContNo(tLCContSchema.getGrpContNo());
      tLLCUWMasterSchema.setProposalContNo(tLCContSchema.getProposalContNo());
      tLLCUWMasterSchema.setUWNo(tUWNo);//核保顺序号----计算得出
      tLLCUWMasterSchema.setInsuredNo(tLCContSchema.getInsuredNo());
      tLLCUWMasterSchema.setInsuredName(tLCContSchema.getInsuredName());
      tLLCUWMasterSchema.setAppntNo(tLCContSchema.getAppntNo());
      tLLCUWMasterSchema.setAppntName(tLCContSchema.getAppntName());
      tLLCUWMasterSchema.setAgentCode(tLCContSchema.getAgentCode());
      tLLCUWMasterSchema.setAgentGroup(tLCContSchema.getAgentGroup());
      tLLCUWMasterSchema.setOperator(mG.Operator);
      tLLCUWMasterSchema.setMakeDate(mCurrentDate);
      tLLCUWMasterSchema.setMakeTime(mCurrentTime);
      tLLCUWMasterSchema.setModifyDate(mCurrentDate);
      tLLCUWMasterSchema.setModifyTime(mCurrentTime);
      map.put(tLLCUWMasterSchema,"DELETE&INSERT");

      //个人合同理赔核保核保轨迹表（LLCUWSub）-----数据结构与（LLCUWMaster）相同，由LLCUWMaster表拷贝得出
      LLCUWSubSchema tLLCUWSubSchema = new LLCUWSubSchema();
      Reflections tRef = new Reflections(); //[声明复制表的数据的对象]
      //[LLCUWMaster(个人合同核保最近结果表)---->LLCUWSub(个人合同核保核保轨迹表)]
      tRef.transFields(tLLCUWSubSchema, tLLCUWMasterSchema);
      map.put(tLLCUWSubSchema,"INSERT");


        return true;
    }

    /**
     * 返回结果
     * @return VData
     */
    public VData getResult()
    {
        return mResult;
    }
    /**
     * 准备返回前台统一存储数据
     * 输出：如果发生错误则返回false,否则返回true
     */
    private boolean prepareOutputData()
    {
        mResult.clear();
        mResult.add(map);
        return true;
    }
    /**
     * 返回错误
     * @return VData
     */
    public CErrors getErrors()
    {
        return mErrors;
    }


}
