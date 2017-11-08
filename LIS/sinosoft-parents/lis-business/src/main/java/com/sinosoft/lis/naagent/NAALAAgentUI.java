/*
 * <p>ClassName: ALAAgentUI </p>
 * <p>Description: ALAAgentUI类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 销售管理
 * @CreateDate：2003-01-09
 */
package com.sinosoft.lis.naagent;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.service.BusinessService;

public class NAALAAgentUI implements BusinessService {
private static Logger logger = Logger.getLogger(NAALAAgentUI.class);
/** 错误处理类，每个需要错误处理的类中都放置该类 */
public  CErrors mErrors=new CErrors();
private VData mResult = new VData();
/** 往后面传输数据的容器 */
private VData mInputData =new VData();
/** 数据操作字符串 */
private String mOperate;
private String mIsManager;
//业务处理相关变量
 /** 全局数据 */
private GlobalInput mGlobalInput =new GlobalInput() ;
private LAAgentSchema mLAAgentSchema=new LAAgentSchema();
private LATreeSchema mLATreeSchema = new LATreeSchema();
private LAWarrantorSet mLAWarrantorSet = new LAWarrantorSet();
private LAAgentSet mLAAgentSet=new LAAgentSet();
private TransferData tTransferData;
public NAALAAgentUI ()
{
}
 /**
传输数据的公共方法
*/
public boolean submitData(VData cInputData,String cOperate)
{
   //将操作数据拷贝到本类中
   this.mOperate =cOperate;
  //得到外部传入的数据,将数据备份到本类中
   if (!getInputData(cInputData))
     return false;
  //进行业务处理
   if (!dealData())
   return false;
  //准备往后台的数据
  if (!prepareOutputData())
   return false;
   NAALAAgentBL tALAAgentBL=new NAALAAgentBL();
   logger.debug("Start NALAAgent UI Submit...");
   tALAAgentBL.submitData(mInputData,mOperate);
   logger.debug("End NALAAgent UI Submit...");
   //如果有需要处理的错误，则返回
   if (tALAAgentBL.mErrors .needDealError() )
   {  
	 // @@错误处理
     this.mErrors.copyAllErrors(tALAAgentBL.mErrors);
     CError tError = new CError();
     tError.moduleName = "ALAAgentUI";
     tError.functionName = "submitData";
     tError.errorMessage = "数据提交失败!";
     this.mErrors .addOneError(tError) ;
     return false;
  }
  if (mOperate.equals("QUERY||MAIN")||mOperate.equals("INSERT||MAIN"))
  {
     this.mResult.clear();
     this.mResult=tALAAgentBL.getResult();
  }
  mInputData=null;
  return true;
  }
  public static void main(String[] args)
  {
  }
  /**
  * 准备往后层输出所需要的数据
  * 输出：如果准备数据时发生错误则返回false,否则返回true
  */
 private boolean prepareOutputData()
 {
     try
     {
       logger.debug("prepareOuptDAte");
         mInputData.clear();
         mInputData.add(this.mGlobalInput);
         mInputData.add(this.mIsManager);
         mInputData.add(this.mLAAgentSchema);
         mInputData.add(this.mLATreeSchema);
         mInputData.add(this.mLAWarrantorSet);
         mInputData.add(this.tTransferData); 
     }
     catch(Exception ex)
     {
        // @@错误处理
        CError tError =new CError();
        tError.moduleName="ALAAgentUI";
        tError.functionName="prepareData";
        tError.errorMessage="在准备往后层处理所需要的数据时出错。";
        this.mErrors .addOneError(tError) ;
        return false;
      }
      return true;
}
/**
 * 根据前面的输入数据，进行UI逻辑处理
 * 如果在处理过程中出错，则返回false,否则返回true
 */
 private boolean dealData()
 {
      boolean tReturn =false;
      //此处增加一些校验代码
      tReturn=true;
      return tReturn ;
 }
 /**
 * 从输入数据中得到所有对象
 *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
 */
 private boolean getInputData(VData cInputData)
 {
      //全局变量
      mGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
      mIsManager = (String)cInputData.getObject(1);
      this.mLAAgentSchema.setSchema((LAAgentSchema)cInputData.getObjectByObjectName("LAAgentSchema",0));
      this.mLATreeSchema.setSchema((LATreeSchema)cInputData.getObjectByObjectName("LATreeSchema",0));
      this.mLAWarrantorSet.set((LAWarrantorSet)cInputData.getObjectByObjectName("LAWarrantorSet",0));
      tTransferData=(TransferData)cInputData.getObjectByObjectName("TransferData",0);
      if(mGlobalInput==null )
      {
          // @@错误处理
          CError tError =new CError();
          tError.moduleName="ALAAgentUI";
          tError.functionName="getInputData";
          tError.errorMessage="没有得到足够的信息！";
          this.mErrors .addOneError(tError) ;
          return false;
      }
      //add by jiaqiangli 2006-10-08 出现职级为空的现象
      //this.mOperate 这个可以不判断 正常情况都不会出现为空
      if (this.mOperate.equals("INSERT||MAIN") || this.mOperate.equals("UPDATE||ALL")) {
        logger.debug("this.mLATreeSchema.getAgentGrade():" +this.mLATreeSchema.getAgentGrade());
        if (this.mLATreeSchema.getAgentGrade() == null ||
            this.mLATreeSchema.getAgentGrade().equals("")) {
          logger.debug("NAALAAgentUI出错:职级为空");
          // @@错误处理
          CError tError = new CError();
          tError.moduleName = "NAALAAgentUI";
          tError.functionName = "getInputData";
          tError.errorMessage = "NAALAAgentUI出错:职级为空";
          this.mErrors.addOneError(tError);
          return false;
        }
      }
      return true;
 }
 public VData getResult()
 {
     return this.mResult;
 }
 public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}
}
