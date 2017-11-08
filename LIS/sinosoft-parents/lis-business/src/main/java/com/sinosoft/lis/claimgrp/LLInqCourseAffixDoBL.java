/*
 * <p>ClassName: LAAgentDoBL </p>
 * <p>Description: LAAgentDoBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 销售管理
 * @CreateDate：2003-01-09
 */
package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.service.BusinessService;

public class LLInqCourseAffixDoBL implements BusinessService{
private static Logger logger = Logger.getLogger(LLInqCourseAffixDoBL.class);
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    private VData mResult = new VData();

    /** 往后面传输数据的容器 */
    private VData mInputData;

    /** 全局数据 */
    private GlobalInput mGlobalInput = new GlobalInput();

    /** 数据操作字符串 */
    private String mOperate;
    private String mIsManager;
    private String currentDate = PubFun.getCurrentDate();
    private String currentTime = PubFun.getCurrentTime();
    private MMap map = new MMap();

    /** 业务处理相关变量 */
    private LLInqApplySchema mLLInqApplySchema = new LLInqApplySchema();
    public LLInqCourseAffixDoBL() {
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
        if (!getInputData(cInputData)) {
            return false;
        }
        logger.debug("End getInputData!");
        //校验该代理人是否在黑名单中存在
        if (!checkData()) {
            return false;
        }
      if (this.mOperate.equals("UPDATE"))
      {
          //进行业务处理
          if (!dealData()) {
              // @@错误处理
              CError tError = new CError();
              tError.moduleName = "LLInqCourseAffixDoBL";
              tError.functionName = "submitData";
              tError.errorMessage = "LLInqCourseAffixDoBL-->dealData!";
              this.mErrors.addOneError(tError);
              return false;
          }
      }
        logger.debug("over dealData");
        //准备往后台的数据
        if (!prepareOutputData())
        {
            return false;
        }
        if (this.mOperate.equals("SHOW"))
        {
            this.submitquery();
        }else
        {
            PubSubmit mmPubSubmit = new PubSubmit();
            if (!mmPubSubmit.submitData(mInputData, cOperate)) {
                // @@错误处理
                this.mErrors.copyAllErrors(mmPubSubmit.mErrors);
                CError tError = new CError();
                tError.moduleName = "LLInqCourseAffixDoBL";
                tError.functionName = "submitData";
                tError.errorMessage = "数据提交失败!";
                this.mErrors.addOneError(tError);
                return false;
            }
        }
        mInputData = null;
        return true;
    }

    /**
     * 根据前面的输入数据，进行BL逻辑处理
     * 如果在处理过程中出错，则返回false,否则返回true
     */
    private boolean dealData() {

             LLSubAffixSchema tLLSubAffixSchema = new LLSubAffixSchema();
             String tClmNo=mLLInqApplySchema.getClmNo();
             String app1=mLLInqApplySchema.getAffix();
             String app2=mLLInqApplySchema.getAffixName();
             logger.debug("增加附件的赔案："+tClmNo);
             logger.debug("增加附件的路径："+app1);
             logger.debug("增加附件的名称："+app2);

           if (app1!=null && !app1.equals("") && app2!=null && !app2.equals(""))
           {
               tLLSubAffixSchema.setOtherNo(tClmNo);
               tLLSubAffixSchema.setOtherNoType("5");
               tLLSubAffixSchema.setSubOtherNo("1");
               tLLSubAffixSchema.setAffix(app1);
               tLLSubAffixSchema.setAffixName(app2);
               tLLSubAffixSchema.setMakeDate(currentDate);
               tLLSubAffixSchema.setMakeTime(currentTime);
               tLLSubAffixSchema.setOperator(mGlobalInput.Operator);

               map.put(tLLSubAffixSchema,"DELETE&INSERT");
               return true;
           }else
           {
               CError tError = new CError();
               tError.moduleName = "LLInqCourseAffixDoBL";
               tError.functionName = "prepareData";
               tError.errorMessage = "您所上载的文件可能不为Word文件(*.doc)或者上载的文件大小超过最大上限5M，请调整后重新上载！";
               this.mErrors.addOneError(tError);
               return false;
           }

    }

    /**
     * 从输入数据中得到所有对象
     *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData) {
        mGlobalInput.setSchema((GlobalInput) cInputData.
                                    getObjectByObjectName(
                                            "GlobalInput", 0));

        mLLInqApplySchema.setSchema((LLInqApplySchema) cInputData.
                                      getObjectByObjectName("LLInqApplySchema", 0));
        return true;
    }




    private boolean prepareOutputData() {
     try {
           mInputData = new VData();
           mInputData.add(map);
         } catch (Exception ex) {
               // @@错误处理
               CError tError = new CError();
               tError.moduleName = "LLInqCourseAffixDoBL";
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

    private boolean submitquery() {
        this.mResult.clear();
        logger.debug("Start mLLSubAffixSchema Submit...");
        LLSubAffixDB tLLSubAffixDB = new LLSubAffixDB();
        LLSubAffixSet tLLSubAffixSet = new LLSubAffixSet();
        String tsql="select * from LLSubAffix where otherno='"+mLLInqApplySchema.getClmNo()+"' and othernotype='5' ";
        tLLSubAffixSet=tLLSubAffixDB.executeQuery(tsql);
        mResult.add(tLLSubAffixSet);
        logger.debug("End mLLSubAffixSchema Submit...");
        //如果有需要处理的错误，则返回
        if (tLLSubAffixDB.mErrors.needDealError()) {
            // @@错误处理
            this.mErrors.copyAllErrors(tLLSubAffixDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LAAgentDoBL";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        mInputData = null;
        return true;
    }

    //代理人校验
    private boolean checkData() {

        return true;
    }

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}
}
