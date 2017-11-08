/*
* <p>ClassName: ALABranchGroupBL </p>
* <p>Description: ALABranchGroupBL类文件 </p>
* <p>Copyright: Copyright (c) 2002</p>
* <p>Company: sinosoft </p>
* @Database: 销售管理
* @CreateDate：2003-01-09
 */
package com.sinosoft.lis.wagecal;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.wagecal.*;

public class LABKBranchGroupBL  {
private static Logger logger = Logger.getLogger(LABKBranchGroupBL.class);
  /** 错误处理类，每个需要错误处理的类中都放置该类 */
  public  CErrors mErrors=new CErrors();
  private VData mResult = new VData();
  /** 往后面传输数据的容器 */
  private VData mInputData;
  /** 全局数据 */
  private GlobalInput mGlobalInput =new GlobalInput() ;
  /** 数据操作字符串 */
  private String mOperate;
  private String currentDate = PubFun.getCurrentDate();
  private String currentTime = PubFun.getCurrentTime();
  /** 业务处理相关变量 */
  private LABranchGroupSchema mLABranchGroupSchema=new LABranchGroupSchema();
  private LABranchGroupBSchema mLABranchGroupBSchema=new LABranchGroupBSchema();
  private LATreeSchema mLATreeSchema=new LATreeSchema();
  private LATreeBSchema mLATreeBSchema=new LATreeBSchema();
  private LAAgentSchema mLAAgentSchema=new LAAgentSchema();
  private LABranchGroupSet mLABranchGroupSet=new LABranchGroupSet();
  public LABKBranchGroupBL() {
  }
  public static void main(String[] args) {
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
    if (!dealData())
    {
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "ALABranchGroupBL";
      tError.functionName = "submitData";
      tError.errorMessage = "数据处理失败ALABranchGroupBL-->dealData!";
      this.mErrors .addOneError(tError) ;
      return false;
    }
    //准备往后台的数据
    if (!prepareOutputData())
      return false;
    if (this.mOperate.equals("QUERY||MAIN"))
    {
      this.submitquery();
    }
    else
    {
      logger.debug("Start ALABranchGroupBL Submit...");
      ALABranchGroupBLS tALABranchGroupBLS=new ALABranchGroupBLS();
      tALABranchGroupBLS.submitData(mInputData,cOperate);
      logger.debug("End ALABranchGroupBL Submit...");
      //如果有需要处理的错误，则返回
      if (tALABranchGroupBLS.mErrors.needDealError())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tALABranchGroupBLS.mErrors);
        CError tError = new CError();
        tError.moduleName = "ALABranchGroupBL";
        tError.functionName = "submitDat";
        tError.errorMessage ="数据提交失败!";
        this.mErrors .addOneError(tError) ;
        return false;
      }
    }
    mInputData=null;
    return true;
  }
  /**
   * 根据前面的输入数据，进行BL逻辑处理
   * 如果在处理过程中出错，则返回false,否则返回true
   */
  private boolean dealData()
  {
    boolean tReturn =true;
    int tCount=0;
    String tAgentGroup = "";
    if(this.mOperate.equals("INSERT||MAIN"))
    {
      logger.debug("LABKBranchGroupBL........");
      //生成内部机构代码
      tAgentGroup = PubFun1.CreateMaxNo("AgentGroup",12);
      logger.debug("AgentGroup:"+tAgentGroup);
//        //确定出上级机构的内部代码
//        String tUpBranch =this.mLABranchGroupSchema.getUpBranchAttr().trim();
//        tUpBranch = getUpBranch(tUpBranch);
//        if (tUpBranch == null)
//          return false;
//        this.mLABranchGroupSchema.setUpBranch(tUpBranch);
      this.mLABranchGroupSchema.setUpBranchAttr("0");//银行代理部分不涉及直属关系
      logger.debug("UpBranch:"+this.mLABranchGroupSchema.getUpBranch().trim());
      this.mLABranchGroupSchema.setAgentGroup(tAgentGroup);
      this.mLABranchGroupSchema.setOperator(mGlobalInput.Operator);
      this.mLABranchGroupSchema.setMakeDate(currentDate);
      this.mLABranchGroupSchema.setMakeTime(currentTime);
      this.mLABranchGroupSchema.setModifyDate(currentDate);
      this.mLABranchGroupSchema.setModifyTime(currentTime);
    }
    if (this.mOperate.equals("UPDATE||MAIN"))
    {
      if(this.mLABranchGroupSchema.getAgentGroup().equals(""))
      {
        // @@错误处理
        CError tError = new CError();
        tError.moduleName = "ALABranchGroupBL";
        tError.functionName = "dealData()";
        tError.errorMessage = "不存在所操作的销售单位!(隐式机构代码为空)";
        this.mErrors .addOneError(tError);
        return false;
      }


      //获取‘入机日期和时间'
      LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
      tLABranchGroupDB.setAgentGroup(this.mLABranchGroupSchema.getAgentGroup());
      if (!tLABranchGroupDB.getInfo())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tLABranchGroupDB.mErrors);
        CError tError = new CError();
        tError.moduleName = "ALABranchGroupBL";
        tError.functionName = "dealData()";
        tError.errorMessage = "不存在所要修改的机构!";
        this.mErrors .addOneError(tError);
        return false;
      }

      //开始备份机构的信息
     Reflections tReflections = new Reflections();
     String tEdorNo = PubFun1.CreateMaxNo("EdorNo",20);
     tReflections.transFields(mLABranchGroupBSchema,tLABranchGroupDB.getSchema() );
     mLABranchGroupBSchema.setEdorNo(tEdorNo) ;
     mLABranchGroupBSchema.setEdorType("05"); //主管任命
     mLABranchGroupBSchema.setMakeDate2(mLABranchGroupSchema.getMakeDate());
     mLABranchGroupBSchema.setMakeTime2(mLABranchGroupSchema.getMakeTime());
     mLABranchGroupBSchema.setModifyDate2(mLABranchGroupSchema.getModifyDate());
     mLABranchGroupBSchema.setModifyTime2(mLABranchGroupSchema.getModifyTime());
     mLABranchGroupBSchema.setOperator2(mLABranchGroupSchema.getOperator());
     mLABranchGroupBSchema.setMakeDate(currentDate);
     mLABranchGroupBSchema.setMakeTime(currentTime);
     mLABranchGroupBSchema.setModifyDate(currentDate);
     mLABranchGroupBSchema.setModifyTime(currentTime);
      mLABranchGroupBSchema.setOperator(this.mGlobalInput.Operator);
//          //重新设置机构的上级机构（因为有可能修改机构代码）
//          String tUpBranch =this.mLABranchGroupSchema.getUpBranchAttr().trim();
//          tUpBranch = getUpBranch(tUpBranch);
//          if (tUpBranch == null)
//            return false;
//          this.mLABranchGroupSchema.setUpBranch(tUpBranch);
      this.mLABranchGroupSchema.setUpBranchAttr("0");//银行代理部分不涉及直属关系
      this.mLABranchGroupSchema.setMakeDate(tLABranchGroupDB.getMakeDate());
      this.mLABranchGroupSchema.setMakeTime(tLABranchGroupDB.getMakeTime());
      this.mLABranchGroupSchema.setModifyDate(currentDate);
      this.mLABranchGroupSchema.setModifyTime(currentTime);
      this.mLABranchGroupSchema.setOperator(mGlobalInput.Operator);
    }
    tReturn=true;
    return tReturn;
  }
  /**
   * 从输入数据中得到所有对象
   *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
   */
  private boolean getInputData(VData cInputData)
  {
    this.mLABranchGroupSchema.setSchema((LABranchGroupSchema)cInputData.getObjectByObjectName("LABranchGroupSchema",0));
    this.mGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
    return true;
  }
  /**
   * 准备往后层输出所需要的数据
   * 输出：如果准备数据时发生错误则返回false,否则返回true
   */
  private boolean submitquery()
  {
    this.mResult.clear();
    logger.debug("Start ALABranchGroupBLQuery Submit...");
    LABranchGroupDB tLABranchGroupDB=new LABranchGroupDB();
    tLABranchGroupDB.setSchema(this.mLABranchGroupSchema);
    this.mLABranchGroupSet=tLABranchGroupDB.query();
    this.mResult.add(this.mLABranchGroupSet);
    logger.debug("End ALABranchGroupBLQuery Submit...");
    //如果有需要处理的错误，则返回
    if (tLABranchGroupDB.mErrors.needDealError())
    {
      // @@错误处理
      this.mErrors.copyAllErrors(tLABranchGroupDB.mErrors);
      CError tError = new CError();
      tError.moduleName = "ALABranchGroupBL";
      tError.functionName = "submitData";
      tError.errorMessage = "数据提交失败!";
      this.mErrors .addOneError(tError) ;
      return false;
    }
    mInputData=null;
    return true;
  }
  private boolean prepareOutputData()
  {
    try
    {
      this.mInputData=new VData();
      this.mInputData.add(this.mGlobalInput);
      this.mInputData.add(this.mLABranchGroupSchema);
      this.mInputData.add(this.mLABranchGroupBSchema);

    }
    catch(Exception ex)
    {
      // @@错误处理
      CError tError =new CError();
      tError.moduleName="ALABranchGroupBL";
      tError.functionName="prepareData";
      tError.errorMessage="在准备往后层处理所需要的数据时出错。";
      this.mErrors .addOneError(tError) ;
      return false;
    }
    return true;
  }
  public VData getResult()
  {
    return this.mResult;
  }
  public boolean prepareCopyTree(LATreeSchema cLATreeSchema)
  {
    String tEdorNo =  PubFun1.CreateMaxNo("EdorNo",20);
    try{
      this.mLATreeBSchema.setAgentCode(cLATreeSchema.getAgentCode());
      this.mLATreeBSchema.setAgentGrade(cLATreeSchema.getAgentGrade());
      this.mLATreeBSchema.setAgentGroup(cLATreeSchema.getAgentGroup());
      this.mLATreeBSchema.setAgentLastGrade(cLATreeSchema.getAgentLastGrade());
      this.mLATreeBSchema.setAgentLastSeries(cLATreeSchema.getAgentLastSeries());
      this.mLATreeBSchema.setAgentSeries(cLATreeSchema.getAgentSeries());
      this.mLATreeBSchema.setAssessType(cLATreeSchema.getAssessType());
      this.mLATreeBSchema.setAstartDate(cLATreeSchema.getAstartDate());
      this.mLATreeBSchema.setEdorNO(tEdorNo);
      this.mLATreeBSchema.setEduManager(cLATreeSchema.getEduManager());
      this.mLATreeBSchema.setIntroAgency(cLATreeSchema.getIntroAgency());
      this.mLATreeBSchema.setIntroBreakFlag(cLATreeSchema.getIntroBreakFlag());
      this.mLATreeBSchema.setIntroCommEnd(cLATreeSchema.getIntroCommEnd());
      this.mLATreeBSchema.setIntroCommStart(cLATreeSchema.getIntroCommStart());
      this.mLATreeBSchema.setMakeDate2(cLATreeSchema.getMakeDate());
      this.mLATreeBSchema.setMakeTime2(cLATreeSchema.getMakeTime());
      this.mLATreeBSchema.setManageCom(cLATreeSchema.getManageCom());
      this.mLATreeBSchema.setModifyDate2(cLATreeSchema.getModifyDate());
      this.mLATreeBSchema.setModifyTime2(cLATreeSchema.getModifyTime());
      this.mLATreeBSchema.setOldEndDate(cLATreeSchema.getOldEndDate());
      this.mLATreeBSchema.setOldStartDate(cLATreeSchema.getOldStartDate());
      this.mLATreeBSchema.setOperator2(cLATreeSchema.getOperator());
      this.mLATreeBSchema.setOthUpAgent(cLATreeSchema.getOthUpAgent());
      this.mLATreeBSchema.setRearBreakFlag(cLATreeSchema.getRearBreakFlag());
      this.mLATreeBSchema.setRearCommEnd(cLATreeSchema.getRearCommEnd());
      this.mLATreeBSchema.setRearCommStart(cLATreeSchema.getRearCommStart());
      this.mLATreeBSchema.setRemoveType("03");//转储类型:新增机构
      this.mLATreeBSchema.setStartDate(cLATreeSchema.getState());
      this.mLATreeBSchema.setState(cLATreeSchema.getState());
      this.mLATreeBSchema.setUpAgent(cLATreeSchema.getUpAgent());
      this.mLATreeBSchema.setOperator(this.mGlobalInput.Operator);
      this.mLATreeBSchema.setMakeDate(currentDate);
      this.mLATreeBSchema.setMakeTime(currentTime);
      this.mLATreeBSchema.setModifyDate(currentDate);
      this.mLATreeBSchema.setModifyTime(currentTime);
    }
    catch(Exception ex)
    {
      // @@错误处理
      CError tError =new CError();
      tError.moduleName="AdjustAgentBL";
      tError.functionName="prepareCopy";
      tError.errorMessage="备份调动人员信息出错！";
      this.mErrors .addOneError(tError) ;
      return false;
    }
    return true;
  }
  private String getManagerGrade()
  {
    //查询LDCodeRELA表，取出该机构级别对应的职级、系列
    String tSQL = "select code1 from LDCodeRELA where relaType = 'gradeserieslevel' "
                +"and code3 = '"+"?code3?"+"' "
                +"and othersign = '1' order by code1";
    SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
	  sqlbv1.sql(tSQL);
	  sqlbv1.put("code3", this.mLABranchGroupSchema.getBranchLevel());
    ExeSQL tExeSQL = new ExeSQL();
    String tGrade = tExeSQL.getOneValue(sqlbv1);
    tGrade = tGrade.trim();
    if ((tGrade == null)||(tGrade.equals("")))
    {
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "ALABranchGroupBL";
      tError.functionName = "getManagerGrade";
      tError.errorMessage = "查询机构负责人对应的职级失败!";
      this.mErrors .addOneError(tError) ;
      return null;
    }
    logger.debug("职级："+tGrade);
    return tGrade;
  }
  private String getUpBranch(String tUpBranch)
  {
    logger.debug("tUpBranch-------->"+tUpBranch);
    if (!tUpBranch.equals("")||tUpBranch==null)
    {
      String tSQL = "select AgentGroup from LABranchGroup where BranchAttr = '"
                  + "?tUpBranch?" + "'";
      SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
	  sqlbv2.sql(tSQL);
	  sqlbv2.put("tUpBranch", tUpBranch);
      ExeSQL tExeSQL = new ExeSQL();
      tUpBranch = tExeSQL.getOneValue(sqlbv2).trim();
      if ((tUpBranch == null)||tUpBranch.equals(""))
      {
        CError tError = new CError();
        tError.moduleName = "ALABranchGroupBL";
        tError.functionName = "submitDat";
        tError.errorMessage ="所输机构代码的上级机构不存在！";
        this.mErrors .addOneError(tError) ;
        return null;
      }
    }
    else
      tUpBranch="";
    return tUpBranch;
  }
}
