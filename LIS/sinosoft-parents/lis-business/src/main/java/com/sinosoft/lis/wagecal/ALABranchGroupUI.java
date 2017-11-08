/*
* <p>ClassName: ALABranchGroupUI </p>
* <p>Description: ALABranchGroupUI类文件 </p>
* <p>Copyright: Copyright (c) 2002</p>
* <p>Company: sinosoft </p>
* @Database: 销售管理
* @CreateDate：2003-01-09
 */
package com.sinosoft.lis.wagecal;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;


public class ALABranchGroupUI implements BusinessService {
private static Logger logger = Logger.getLogger(ALABranchGroupUI.class);
  /** 错误处理类，每个需要错误处理的类中都放置该类 */
  public  CErrors mErrors=new CErrors();
  private VData mResult = new VData();
  /** 往后面传输数据的容器 */
  private VData mInputData =new VData();
  /** 数据操作字符串 */
  private String mOperate;
//业务处理相关变量
/** 全局数据 */
  private GlobalInput mGlobalInput =new GlobalInput() ;
  private LABranchGroupSchema mLABranchGroupSchema=new LABranchGroupSchema();
  private LABranchGroupSet mLABranchGroupSet=new LABranchGroupSet();
  public ALABranchGroupUI ()
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
    logger.debug("getinputdata");
    if (!getInputData(cInputData))
      return false;
    if (mOperate.equals("INSERT||MAIN"))
    {
      logger.debug("校验机构代码");
      String sql="Select branchattr from labranchgroup where branchattr='"+"?branchattr?"+"' and branchtype='3'";
      SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
	  sqlbv1.sql(sql);
	  sqlbv1.put("branchattr", mLABranchGroupSchema.getBranchAttr());
      ExeSQL a= new ExeSQL();
      String aa=a.getOneValue(sqlbv1);
      if(!(aa==null||aa.equals("")))
      {
        CError tError = new CError();
        tError.moduleName = "ALABranchGroupUI";
        tError.functionName = "saveData";
        tError.errorMessage = "分支机构代码重复!";
        this.mErrors.addOneError(tError) ;
        return false;
      }
    }
    //进行业务处理
    logger.debug("dealdata");
    if (!dealData())
      return false;
    //准备往后台的数据
    if (!prepareOutputData())
      return false;
    ALABranchGroupBL tALABranchGroupBL=new ALABranchGroupBL();
    LABKBranchGroupBL tLABKBranchGroupBL=new LABKBranchGroupBL();
    logger.debug("Start LABranchGroup UI Submit...");
    logger.debug("BranchType:"+mLABranchGroupSchema.getBranchType());
    if (mLABranchGroupSchema.getBranchType().equals("3"))//add by jiangcx
    {
      tLABKBranchGroupBL.submitData(mInputData, mOperate);
      logger.debug("start ALBKGroupBL............");
    }
    else
      tALABranchGroupBL.submitData(mInputData,mOperate);
    logger.debug("End LABranchGroup UI Submit...");
    //如果有需要处理的错误，则返回
    if (tALABranchGroupBL.mErrors .needDealError() )
    {
      // @@错误处理
      this.mErrors.copyAllErrors(tALABranchGroupBL.mErrors);
      CError tError = new CError();
      tError.moduleName = "ALABranchGroupUI";
      tError.functionName = "submitData";
      tError.errorMessage = "数据提交失败!";
      this.mErrors .addOneError(tError) ;
      return false;
    }
    if (mOperate.equals("QUERY||MAIN"))
    {
      this.mResult.clear();
      this.mResult=tALABranchGroupBL.getResult();
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
      mInputData.clear();
      mInputData.add(this.mGlobalInput);
      mInputData.add(this.mLABranchGroupSchema);
    }
    catch(Exception ex)
    {
      // @@错误处理
      CError tError =new CError();
      tError.moduleName="ALABranchGroupUI";
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
    String SQL="";
    //此处增加一些校验代码
    logger.debug("-----------"+mLABranchGroupSchema.getUpBranch()+"----------");
    if(mLABranchGroupSchema.getUpBranch()!=null&&!mLABranchGroupSchema.getUpBranch().equals(""))
    {
      SQL="select 'X' From dual where '"+"?dual?"
         +"' like concat((select trim(branchattr) from labranchgroup where agentgroup='"+"?agentgroup?"+"'),'%')";
      SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
	  sqlbv2.sql(SQL);
	  sqlbv2.put("dual", mLABranchGroupSchema.getBranchAttr());
	  sqlbv2.put("agentgroup", mLABranchGroupSchema.getUpBranch());
      ExeSQL tExeSQL = new ExeSQL();
      if(!tExeSQL.getOneValue(SQL).equals("X"))
      {
        logger.debug("-----------"+tExeSQL.getOneValue(sqlbv2)+"----------");
        CError tError =new CError();
        tError.moduleName="ALABranchgroupUI";
        tError.functionName="dealData";
        tError.errorMessage="上级机构代码录入有误！";
        this.mErrors .addOneError(tError) ;
        return false;
      }
    }
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
    this.mLABranchGroupSchema.setSchema((LABranchGroupSchema)cInputData.getObjectByObjectName("LABranchGroupSchema",0));
    if(mGlobalInput==null )
    {
      // @@错误处理
      CError tError =new CError();
      tError.moduleName="ALABranchGroupUI";
      tError.functionName="getInputData";
      tError.errorMessage="没有得到足够的信息！";
      this.mErrors .addOneError(tError) ;
      return false;
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
