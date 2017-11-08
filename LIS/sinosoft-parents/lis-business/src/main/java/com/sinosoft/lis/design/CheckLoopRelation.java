package com.sinosoft.lis.design;
import org.apache.log4j.Logger;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import java.text.*;
import javax.swing.*;
import java.util.*;
import java.sql.*;
/**
 * <p>Title: lis</p>
 * <p>Description:校验是否会产生循环链,主要用于组织归属晋升程序校验 </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: sinosoft</p>
 * @author tm
 * @version 1.0
 */

public class CheckLoopRelation {
private static Logger logger = Logger.getLogger(CheckLoopRelation.class);
  /** 错误处理类，每个需要错误处理的类中都放置该类 */
  public CErrors mErrors=new CErrors();
  private String mOperate = "";
  private LARelationSet mLARelationSet = new LARelationSet();
  private LAAssessSet mLAAssessSet = new LAAssessSet();
//  private
  public CheckLoopRelation() {
  }
 /**
 * 传输数据的公共方法
 * @param cInputData VData
 * @param cOperate String
 * @return boolean
 */
 public boolean submitData(VData cInputData, String cOperate)
 {
   this.mOperate = cOperate;
   // 得到外部传入的数据，将数据备份到本类中
   if( !getInputData(cInputData) ) {
     return false;
   }
   if( !dealData() ) {
     return false;
   }
   return true;
 }
   /**
    * 从输入数据中得到所有对象
    * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
    */
  private boolean getInputData(VData cInputData)
  {
//    this.mLARelationSet.set((LARelationSet)cInputData.getObjectByObjectName("LARelationSet",0));
    this.mLAAssessSet.set((LAAssessSet)cInputData.getObjectByObjectName("LAAssessSet",0));
    return true;
  }
  /**
   * 根据前面的输入数据，进行BL逻辑处理
   * 如果在处理过程中出错，则返回false,否则返回true
   */
  private boolean dealData()
  {
    for(int i=1;i<=this.mLAAssessSet.size();i++)
    {
      LAAssessSchema tLAAssessSchema = new LAAssessSchema();
      tLAAssessSchema = mLAAssessSet.get(i);
      //1 - 处理向前链
      //-----------------------------------------------------------//
      String tAgentCode = "";
      String tAgentGrade = "";
      String tLevel = "";
      ExeSQL tExeSQL = new ExeSQL();
      tAgentCode = tLAAssessSchema.getAgentCode();
      tAgentGrade = tLAAssessSchema.getAgentGrade();
      //-----------------------------------------------------------//
      if(tAgentGrade.compareTo(tLAAssessSchema.getAgentGrade1())>=0)
      {
        //降级人员不做校验,只校验需晋升人员
        continue;
      }
      if(tAgentGrade.equals("A02"))
      {
        tLevel ="01";
      }
      else if(tAgentGrade.equals("A04")||tAgentGrade.equals("A05"))
      {
        tLevel ="02";
      }
      else if(tAgentGrade.equals("A06")||tAgentGrade.equals("A07"))
      {
        tLevel ="03";
      }
      else if(tAgentGrade.equals("A08"))
      {
        tLevel ="04";
      }
      else
      {
        continue;
      }
      String tBranchLevel = "01";
      if(tAgentGrade.equals("A05"))
        tBranchLevel = "01";
      else if(tAgentGrade.equals("A07"))
        tBranchLevel = "02";
      else if(tAgentGrade.equals("A08"))
        tBranchLevel = "03";
      //-----------------------------------------------------------//
      //查询上级主管
      String tSQL = "select branchmanager from labranchgroup where agentgroup "
                  + " = (select upbranch from labranchgroup where branchmanager='"+"?tAgentCode?"+"' and branchlevel='"+"?tBranchLevel?"+"' and endflag<>'Y') "
                  + " and '"+"?tAgentGrade?"+"'>='A04' "
                  + " union "
                  + " select branchmanager from labranchgroup where agentgroup "
                  + " = (select agentgroup from latree where agentcode='"+"?tAgentCode?"+"') "
                  + " and '"+"?tAgentGrade?"+"'<'A04' "
                  ;
      String tManager = "";
      SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
      sqlbv1.sql(tSQL);
      sqlbv1.put("tAgentCode", tAgentCode);
      sqlbv1.put("tBranchLevel", tBranchLevel);
      sqlbv1.put("tAgentGrade", tAgentGrade);
      tManager = tExeSQL.getOneValue(sqlbv1);
      //-----------------------------------------------------------//
      ArrayList tArrayList = new ArrayList();
      //查询该上级主管的向前育成关系
      tSQL = "select * from larelation where agentcode = '"+"?tManager?"+"' "
           + " and relatype='02' and relalevel='"+"?tLevel?"+"' "
           + " order by relagens ";
      SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
      sqlbv2.sql(tSQL);
      sqlbv2.put("tManager", tManager);
      sqlbv2.put("tLevel", tLevel);
      LARelationSet tBeforeLARelationSet = new LARelationSet();
      LARelationDB tLARelationDB = new LARelationDB();
      tBeforeLARelationSet = tLARelationDB.executeQuery(sqlbv2);
      for(int n=1;n<=tBeforeLARelationSet.size();n++)
      {
        String temp = tBeforeLARelationSet.get(n).getRelaAgentCode();
        if(temp==null)
        {
          temp = "";
        }
        if(temp.equals(tAgentCode))
        {
          buildError("check","校验发现循环链:AgentCode:"+tManager);
          return false;
        }
        else
        {
          tArrayList.add(temp);
        }
      }
      //-----------------------------------------------------------//
      //2 - 处理向后链
    }
    return true;
  }
  /**
   *
   */
  private void buildError(String szFunc, String szErrMsg)
  {
    CError cError = new CError( );
    cError.moduleName = "CheckLoopRelation";
    cError.functionName = szFunc;
    cError.errorMessage = szErrMsg;
    this.mErrors.addOneError(cError);
  }
  public static void main(String[] args) {
    CheckLoopRelation checkLoopRelation1 = new CheckLoopRelation();
  }
}
