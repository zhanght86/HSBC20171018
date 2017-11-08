

/**
 * <p>Title: PDRequRisk</p>
 * <p>Description: 产品申请与查询BL.java</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 * @CreateDate：2009-3-12
 */
 
package com.sinosoft.productdef;

import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.workflowengine.*;


public class PDRequRiskBL  {
 /** 错误处理类，每个需要错误处理的类中都放置该类 */
 public  CErrors mErrors=new CErrors();
 private VData mResult = new VData();
 /** 往后面传输数据的容器 */
 private VData mInputData= new VData();
 /** 全局数据 */
 private GlobalInput mGlobalInput =new GlobalInput() ;
 /** 数据操作字符串 */
 private String mOperate;
 /** 业务处理相关变量 */
 //private LDCodeSchema mLDCodeSchema=new LDCodeSchema();
 private MMap map=new MMap();
 private TransferData mTrans = new TransferData();
 
 public PDRequRiskBL() {
 }

/**
* 传输数据的公共方法
 * @param: cInputData 输入的数据
   *         cOperate 数据操作
* @return:
*/
 public boolean submitData(VData cInputData,String cOperate)
 {
    //将操作数据拷贝到本类中
    this.mOperate =cOperate;
    //得到外部传入的数据,将数据备份到本类中
    if (!getInputData(cInputData))
         return false;
    //进行业务处理
    if (!dealData(cOperate, cInputData))
    {
          // @@错误处理
          CError tError = new CError();
          tError.moduleName = "OLDCodeBL";
          tError.functionName = "submitData";
          tError.errorMessage = "数据处理失败OLDCodeBL-->dealData!";
          this.mErrors .addOneError(tError) ;
          return false;
    }
    //准备往后台的数据
    if (!prepareOutputData())
      return false;

      System.out.println("Start OLDCodeBL Submit...");
      
      
      PubSubmit tPubSubmit = new PubSubmit();

	 if (!tPubSubmit.submitData(mInputData, cOperate))
	 {
	    // @@错误处理
	    this.mErrors.copyAllErrors(tPubSubmit.mErrors);
	
	    CError tError = new CError();
	    tError.moduleName = "LDPersonBL";
	    tError.functionName = "submitData";
	    tError.errorMessage = "数据提交失败!";
	
	     this.mErrors.addOneError(tError);
	     return false;
	 }
    mInputData=null;
    return true;
}
 /**
 * 根据前面的输入数据，进行BL逻辑处理
 * 如果在处理过程中出错，则返回false,否则返回true
*/
private boolean dealData(String cOperate, VData cInputData)
{
	//String insert = "insert into PD_LMRisk(riskcode,riskname,makedate,maketime,operator,modifydate,modifytime)";
	
	ActivityOperator mActivityOperator = new ActivityOperator();
	
    try {
      System.out.println("ActivityOperator name:" +
                         mActivityOperator.getClass());

      // 产生第一个节点
      if (mActivityOperator.CreateStartMission("pd00000011", "pd00000000",
    		  cInputData)) {
        VData tempVData = new VData();
        tempVData = mActivityOperator.getResult();
        this.mInputData.add(tempVData.getObjectByObjectName("MMap", 0));
        tempVData = null;
      }
      else {
        // @@错误处理
        this.mErrors.copyAllErrors(mActivityOperator.mErrors);
        return false;
      }
      
//      // test 产生下一层节点
//      if (mActivityOperator.CreateNextMission("00000000000000007198", "", "pd00000000", cInputData)) {
//        VData tempVData = new VData();
//        tempVData = mActivityOperator.getResult();
//        this.mInputData.add(tempVData.getObjectByObjectName("MMap", 0));
//        tempVData = null;
//      }
//      else {
//        // @@错误处理
//        this.mErrors.copyAllErrors(mActivityOperator.mErrors);
//        return false;
//      }
    }
    catch (Exception ex) {
      // @@错误处理
      this.mErrors.copyAllErrors(mActivityOperator.mErrors);
      CError tError = new CError();
      tError.moduleName = "GrpTbWorkFlowBL";
      tError.functionName = "Execute7999999999";
      tError.errorMessage = "工作流引擎工作出现异常!";
      this.mErrors.addOneError(tError);
      return false;
    }
	
	
	return true;
}
/**
* 根据前面的输入数据，进行BL逻辑处理
* 如果在处理过程中出错，则返回false,否则返回true
*/
private boolean updateData()
{
   return true;
}
/**
* 根据前面的输入数据，进行BL逻辑处理
* 如果在处理过程中出错，则返回false,否则返回true
*/
private boolean deleteData()
{
  return true;
}
 /**
 * 从输入数据中得到所有对象
 *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
 */
private boolean getInputData(VData cInputData)
{
	this.mGlobalInput = (GlobalInput)cInputData.getObjectByObjectName("GlobalInput", 0);
	this.mTrans = (TransferData)cInputData.getObjectByObjectName("TransferData", 0);
         return true;
}
/**
* 准备往后层输出所需要的数据
* 输出：如果准备数据时发生错误则返回false,否则返回true
*/
 private boolean submitquery()
{    return true;
}
 private boolean prepareOutputData()
 {
 return true;
 }
 public VData getResult()
 {
   return this.mResult;
 }
 
 public static void main(String[] args) {
 }
}
