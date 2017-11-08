/*
* <p>ClassName: LAComToAgentBL </p>
* <p>Description: LAContBL类文件 </p>
* <p>Copyright: Copyright (c) 2002</p>
* <p>Company: sinosoft </p>
* @Database: 销售管理
* @CreateDate：2003-02-27
 */
package com.sinosoft.lis.wagecal;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import java.sql.*;

public class LABranchGroup  {
private static Logger logger = Logger.getLogger(LABranchGroup.class);
  /** 错误处理类，每个需要错误处理的类中都放置该类 */
  public  CErrors mErrors=new CErrors();
  private VData mResult = new VData();
  /** 往后面传输数据的容器 */
  private VData mInputData;
  /** 全局数据 */
  private GlobalInput mGlobalInput =new GlobalInput() ;
 /*时间变量*/
  private String currentDate = PubFun.getCurrentDate();
  private String currentTime = PubFun.getCurrentTime();
  /** 数据操作字符串 */
  private String mOperate;
  /** 业务处理相关变量 */
  private LAComToAgentSchema mLAComToAgentSchema=new LAComToAgentSchema();
  private LAComToAgentSet mLAComToAgentSet=new LAComToAgentSet();
//
  //当更新和删除的时候备份表
  private LAComToAgentBSet mLAComToAgentBSet=new LAComToAgentBSet();
  public LABranchGroup() {
  }
  public static void main(String[] args) {
  }
  /**
   * 传输数据的公共方法
   * @param: cInputData 输入的数据
   *         cOperate 数据操作
   * @return:
   */
  public boolean submitData(VData cInputData,String cOperate) throws Exception
  {
    //将操作数据拷贝到本类中
    logger.debug("操作符"+cOperate);
    this.mOperate =cOperate;
    logger.debug("come in BL");
    //得到外部传入的数据,将数据备份到本类中
    if (!getInputData(cInputData))
      return false;
    logger.debug("come to check!");
    logger.debug("end check");
    //进行业务处理
    if(cOperate.equals("aa"))
    {
      if (!dealData())
      {
        // @@错误处理
        CError tError = new CError();
        tError.moduleName = "LAComToAgentBL";
        tError.functionName = "submitData";
        tError.errorMessage = "数据处理失败LAContBL-->dealData!";
        this.mErrors .addOneError(tError) ;
        return false;
      }
    }
    else
    {
      if (!dealDatbb())
      {
        // @@错误处理
        CError tError = new CError();
        tError.moduleName = "LAComToAgentBL";
        tError.functionName = "submitData";
        tError.errorMessage = "数据处理失败LAContBL-->dealData!";
        this.mErrors .addOneError(tError) ;
        return false;
      }
    }

    //准备往后台的数据
    mInputData=null;
    return true;
  }
  /**
   * 根据前面的输入数据，进行BL逻辑处理
   * 如果在处理过程中出错，则返回false,否则返回true
   */
  private boolean dealData()
  {
    logger.debug("开始处理");
    boolean tReturn =true;
    for(int i=1;i<=this.mLAComToAgentSet.size();i++)
    {
      this.mLAComToAgentSchema=this.mLAComToAgentSet.get(i);
      logger.debug("中介机构："+this.mLAComToAgentSchema.getAgentCom());
      if (this.mLAComToAgentSchema.getRelaType().equals("0"))
      {
        logger.debug("负责对应渠道组2:"+this.mLAComToAgentSchema.getAgentGroup());
        LAComToAgentBDB tLAComToAgentBDB =new LAComToAgentBDB();
        tLAComToAgentBDB.setAgentCode(mLAComToAgentSchema.getAgentCode());
        tLAComToAgentBDB.setAgentCom(mLAComToAgentSchema.getAgentCom());
        String bsql="select agentgroup from lacomtoagent where AgentCom='"+"?AgentCom?"+"' and RelaType='0'";
        SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
  	    sqlbv1.sql(bsql);
  	    sqlbv1.put("AgentCom", mLAComToAgentSchema.getAgentCom());
        ExeSQL aExeSQL = new ExeSQL();
        tLAComToAgentBDB.setAgentGroup(aExeSQL.getOneValue(sqlbv1));
        logger.debug("Agaentgroup:"+tLAComToAgentBDB.getAgentGroup());
        tLAComToAgentBDB.setEdorType("01");
        String tEdorNo = PubFun1.CreateMaxNo("EdorNo",20);
        tLAComToAgentBDB.setEdorNo(tEdorNo);
        tLAComToAgentBDB.setStartDate(mLAComToAgentSchema.getStartDate());
        tLAComToAgentBDB.setEndDate(mLAComToAgentSchema.getEndDate());
        tLAComToAgentBDB.setMakeDate(currentDate);
        tLAComToAgentBDB.setModifyDate(currentDate);
        tLAComToAgentBDB.setOperator(mGlobalInput.Operator);
        tLAComToAgentBDB.setRelaType("0");
        tLAComToAgentBDB.setMakeTime(currentTime);
        tLAComToAgentBDB.setModifyTime(currentTime);
        tLAComToAgentBDB.insert();
        String asql="delete from LAComToAgent where AgentCom='"+"?AgentCom?"+"' and RelaType='0'";
        SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
  	    sqlbv2.sql(asql);
  	    sqlbv2.put("AgentCom", mLAComToAgentSchema.getAgentCom());
        logger.debug("delete LAComToAgent:"+asql);
        ExeSQL tExeSQL = new ExeSQL();
        tReturn = tExeSQL.execUpdateSQL(sqlbv2);
        logger.debug("Return" + tReturn);
        logger.debug("删除branchattr……");
      }
    }
    tReturn=true;
    return tReturn ;
  }

  private boolean dealDatbb()
  {
    logger.debug("开始处理");
    boolean tReturn =true;
    for(int i=1;i<=this.mLAComToAgentSet.size();i++)
    {
      this.mLAComToAgentSchema=this.mLAComToAgentSet.get(i);
      logger.debug("中介机构："+this.mLAComToAgentSchema.getAgentCom());
      if (this.mLAComToAgentSchema.getRelaType().equals("1"))
      {
        logger.debug("负责对应渠道组2:"+this.mLAComToAgentSchema.getAgentCode());
        String bsql="select agentcode from LAComToAgent where AgentCom='"+"?AgentCom?"+"' and RelaType='1'";
        SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
  	    sqlbv3.sql(bsql);
  	    sqlbv3.put("AgentCom", mLAComToAgentSchema.getAgentCom());
        ExeSQL aExeSQL = new ExeSQL();
        String Agentcode=aExeSQL.getOneValue(sqlbv3);
        logger.debug("代理人组别:"+Agentcode);
        String asql="update LAComToAgent set agentcode=''"+
                    ",MakeDate='"+"?currentDate?"+"',ModifyDate='"+"?currentDate?"+"',Operator='"+"?Operator?"+"'  where Agentcode='"+"?Agentcode?"+"'";
        SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
  	    sqlbv4.sql(asql);
  	    sqlbv4.put("currentDate", currentDate);
  	    sqlbv4.put("Operator", mGlobalInput.Operator);
  	    sqlbv4.put("Agentcode", Agentcode);
  	    logger.debug("update agentcode:"+asql);
        ExeSQL tExeSQL = new ExeSQL();
        tReturn = tExeSQL.execUpdateSQL(sqlbv4);
        logger.debug("Return" + tReturn);
        logger.debug("删除agentcode……");
      }
    }
    tReturn=true;
    return tReturn ;
  }

  /**
   * 从输入数据中得到所有对象
   *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
   */
  private boolean getInputData(VData cInputData)
  {
    this.mLAComToAgentSet.set((LAComToAgentSet)cInputData.getObjectByObjectName("LAComToAgentSet",1));
    this.mGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
    return true;
  }

}
