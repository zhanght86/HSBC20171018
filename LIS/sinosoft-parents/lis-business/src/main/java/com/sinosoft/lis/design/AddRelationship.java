package com.sinosoft.lis.design;
import org.apache.log4j.Logger;
/**
 * <p>Title: AddRelationship.java</p>
 * <p>Description: 增员关系公共类 实现增员纪录的添加和修改（删除、备份）</p>
 * <p>Copyright: Copyright (c) 200603 </p>
 * <p>Company: Sinosoft</p>
 * @author ZC
 * @version 1.0
 */
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import java.lang.*;
import java.sql.*;
import java.util.*;

public class AddRelationship {
private static Logger logger = Logger.getLogger(AddRelationship.class);
  //全局业务变量定义
  /** 错误处理类，每个需要错误处理的类中都放置该类 */
  public CErrors mErrors = new CErrors();
  //当前处理人员
  private String mAgentCode = "";
  /** 全局数据 */
  private GlobalInput mGlobalInput =new GlobalInput() ;
  //存放当前处理人员向前链
  private LARelationSet mBefLARelationSet = new LARelationSet();
  //存放当前处理人员向后链
  private LARelationSet mAfterLARelationSet = new LARelationSet();
  //存放相关备份信息
  private LARelationBSet mAfterLARelationBSet = new LARelationBSet();
  //存放要删除的主表信息
  private LARelationSet  mDeleteLARelationSet = new LARelationSet();
  //存放转储号
  private String mEdorNo = "";
  //生成日期
  private String mCurrentDate = "";
  //生成时间
  private String mCurrentTime = "";
  //增员关系开始时间
  private String mStartDate = "";
  //考核年月
  private String mIndexCalNo = "";
  //育成组
  private String mRelaAgentGroup = "";
  //操作员编码
  private String mOperator = "";
  //转储类型
  private String mEdorType = "";

  public AddRelationship() {
  }

  public static void main(String[] args) {
    AddRelationship addRelationship1 = new AddRelationship();
    addRelationship1.setAgentCode("8611000013");
    addRelationship1.setCurrentDate("2006-4-1");
    addRelationship1.setCurrentTime("08:00:00");
    addRelationship1.setEdorNo("000000001");
    addRelationship1.setEdorType("01");
    addRelationship1.setIndexCalNo("200604");
    addRelationship1.setOperator("aa");
    addRelationship1.setStartDate("2006-5-1");
    addRelationship1.setRelationship("8611000011");
    LARelationSet s = addRelationship1.getBefRelation();
    logger.debug("处理完增员链");
    for(int i=1 ;i<=s.size();i++)
    {
      logger.debug("AgentCode:"+s.get(i).getAgentCode()+"---RelaAgentCode:"+s.get(i).getRelaAgentCode()+"--Gen:"+s.get(i).getRelaGens()+"--StartDate:"+s.get(i).getstartDate());
    }
    LARelationBSet s1 = addRelationship1.getBackUpRalation();
    logger.debug("处理完备份链");
    for(int i=1 ;i<=s1.size();i++)
    {
      logger.debug("AgentCode:"+s1.get(i).getAgentCode()+"---RelaAgentCode:"+s1.get(i).getRelaAgentCode()+"--Gen:"+s1.get(i).getRelaGens()+"--StartDate:"+s1.get(i).getstartDate());
    }
    LARelationSet s2 = addRelationship1.getDeleteLARelationSet();
    logger.debug("处理完需删除链");
    for(int i=1 ;i<=s2.size();i++)
    {
      logger.debug("AgentCode:"+s2.get(i).getAgentCode()+"---RelaAgentCode:"+s2.get(i).getRelaAgentCode()+"--Gen:"+s2.get(i).getRelaGens()+"--StartDate:"+s2.get(i).getstartDate());
    }
  }
  /**
   * 函数功能：设置被增员人所在组内部编码
   * @param tRelaAgentGroup 组内部编码
   */
  public void setAddAgentGroup(String tRelaAgentGroup){
    this.mRelaAgentGroup = tRelaAgentGroup;
  }
  /**
   * 函数功能：设置操作员编码
   * @param tOperator 育成组内部编码
   */
  public void setOperator(String tOperator){
    this.mOperator = tOperator;
  }
  /**
   * 函数功能：设置转储类型
   * @param tEdorType 转储类型
   */
  public void setEdorType(String tEdorType){
    this.mEdorType = tEdorType;
  }
  /**
   * 函数功能：设置当前处理人员
   */
  public void setAgentCode(String tAgentCode){
    this.mAgentCode = tAgentCode;
  }
  /**
   * 函数功能:设置生成日期
   * @param tCurrentDate 生成日期
   */
  public void setCurrentDate(String tCurrentDate){
    this.mCurrentDate = tCurrentDate;
  }
  /**
   * 函数功能:设置生成时间
   * @param tCurrentTime 生成时间
   */
  public void setCurrentTime(String tCurrentTime){
    this.mCurrentTime = tCurrentTime;
  }
  /**
   * 函数功能:设置考核年月
   * @param tIndexCalNo 考核年月
   */
  public void setIndexCalNo(String tIndexCalNo){
    this.mIndexCalNo = tIndexCalNo;
  }
  /**
   * 函数功能:设置增员关系开始时间
   * @param tStartDate 增员关系开始时间
   */
  public void setStartDate(String tStartDate){
    this.mStartDate = tStartDate;
  }
  /**
   * 函数功能：设置转储号
   * @param tEdorNo 转储号
   */
  public void setEdorNo(String tEdorNo){
    this.mEdorNo = tEdorNo;
  }
  /**
   * 函数功能：设置当前处理人员的向前增员链
   */
  public boolean setBefRelation(){
    String sqlBeforeRelation="";
    LARelationDB tLARelationDB=new LARelationDB();
    sqlBeforeRelation = "select * from LARelation where AgentCode='"+"?AgentCode?" +"' "
                      + " and RelaType='01' order by RelaGens asc ";
    logger.debug("sqlBeforeRelation:"+sqlBeforeRelation);
    SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
    sqlbv1.sql(sqlBeforeRelation);
    sqlbv1.put("AgentCode", this.mAgentCode);
    LARelationSet tempLARelationSet = new LARelationSet();
    tempLARelationSet=tLARelationDB.executeQuery(sqlbv1);
    if(tempLARelationSet.size()>0)
    this.mBefLARelationSet.set(tempLARelationSet);
    return true;
  }

  /**
   * 函数功能：获得当前处理人员的向前增员链
   */
  public LARelationSet getBefRelation(){
    return mBefLARelationSet;
  }

  /**
   * 函数功能：设置当前处理人员的向后增员链
   */
  public boolean setAfterRelation(){
    String sqlAfterRelation="";
    LARelationDB tLARelationDB=new LARelationDB();
    sqlAfterRelation = "select * from LARelation where RelaAgentCode='"+"?RelaAgentCode?" +"' "
                     + " and Relatype='01' order by RelaGens asc ";
    SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
    sqlbv2.sql(sqlAfterRelation);
    sqlbv2.put("RelaAgentCode", this.mAgentCode);
    LARelationSet tempLARelationSet = new LARelationSet();
    tempLARelationSet=tLARelationDB.executeQuery(sqlbv2) ;
    if(tempLARelationSet.size()>0)
      this.mAfterLARelationSet.set(tempLARelationSet);
    return true;
  }
  /**
   * 函数功能：获得当前处理人员的向后增员链
   */
  public LARelationSet getAfterRelation(){
    return mAfterLARelationSet;
  }
  /**
   * 函数功能：获得要备份到LARelationB表中的信息，可能包括向前链或向后链
   * @return LARelationBSet
   */
  public LARelationBSet getBackUpRalation(){
    return mAfterLARelationBSet;
  }
  /**
   * 函数功能:返回需要删除的育成关系
   * @return LARelationSet
   */
  public LARelationSet getDeleteLARelationSet(){
    return this.mDeleteLARelationSet;
  }

  /**
   *  函数功能：通过增员功能，需考虑如下几中情况：
   *          1. 当前处理人员没有向后链，只需修改此人向前链；
   *          2. 当前处理人员有向后链，除了修改本人向前链还需修改其向后链人员的向前链；
   *  适用模块：增员功能
   */
  public boolean setRelationship(String tAgentCode){
    Reflections tReflections = new Reflections();
    //记录mAgentCode的直接增员人
    AddRelationship tAddRelationship = new AddRelationship();
    tAddRelationship.setAgentCode(tAgentCode);
    //查询向前增员链,查询所有增员链
    if(!tAddRelationship.setBefRelation()){
      logger.debug("查询向前增员链失败");
    }
    //记录直接增员人的向前增员链
    LARelationSet tBefLARelationSet = new LARelationSet();
    tBefLARelationSet.set(tAddRelationship.getBefRelation());
    //查询需要删除的当前被增员人的向前增员链
    LARelationSet tDelBefLARelationSet = new LARelationSet();
    //查询当前被增员人的向前增员链
    LARelationSet tCurrBefLARelationSet = new LARelationSet();
    if(!this.setBefRelation()){
      logger.debug("查询当前处理人员向前增员链失败");
    }
    tDelBefLARelationSet.set(this.getBefRelation());
    tCurrBefLARelationSet.set(this.getBefRelation());
    //把当前被增员人的向前增员链清空
    this.mBefLARelationSet.clear();
    if(tDelBefLARelationSet.size()>0)
    {
      logger.debug("开始备份当前增员人的向前链");
      this.mDeleteLARelationSet.add(tDelBefLARelationSet);//需要删除的向前链
      for(int i=1;i<=tDelBefLARelationSet.size();i++)
      {
        LARelationSchema sLARelationSchema = new LARelationSchema();
        sLARelationSchema = tDelBefLARelationSet.get(i);
        LARelationBSchema sLARelationBSchema = new LARelationBSchema();
        tReflections.transFields(sLARelationBSchema,sLARelationSchema);
        sLARelationBSchema.setMakeDate2(sLARelationSchema.getMakeDate());
        sLARelationBSchema.setMakeTime2(sLARelationSchema.getMakeTime());
        sLARelationBSchema.setModifyDate2(sLARelationSchema.getModifyDate());
        sLARelationBSchema.setModifyTime2(sLARelationSchema.getModifyTime());
        sLARelationBSchema.setOperator2(sLARelationSchema.getOperator());
        sLARelationBSchema.setMakeDate(this.mCurrentDate);
        sLARelationBSchema.setMakeTime(this.mCurrentTime);
        sLARelationBSchema.setModifyDate(this.mCurrentDate);
        sLARelationBSchema.setModifyTime(this.mCurrentTime);
        sLARelationBSchema.setOperator(this.mOperator);
        sLARelationBSchema.setEdorNo(this.mEdorNo);
        sLARelationBSchema.setEdorType(this.mEdorType);
        sLARelationBSchema.setIndexCalNo(this.mIndexCalNo);
        this.mAfterLARelationBSet.add(sLARelationBSchema);
      }
    }
    //处理向前链
    logger.debug("开始处理当前人的向前增员链");
    for(int i=0;i<=tBefLARelationSet.size();i++)
    {
      LARelationSchema sLARelationSchema = new LARelationSchema();
      if(i==0)
      {
        //处理直接育成关系,新生成
        logger.debug("开始处理直接增员关系");
        sLARelationSchema.setAgentCode(this.mAgentCode);
        sLARelationSchema.setRelaAgentCode(tAgentCode);
        sLARelationSchema.setRelaLevel("10");
        sLARelationSchema.setRelaGens(1);
        sLARelationSchema.setRelaType("01");
        sLARelationSchema.setAgentGroup(this.mRelaAgentGroup);
        String sSQL = "select makedate,maketime from larelation where agentcode='"+"?agentcode?"+"' "
                   + "and relalevel='10' and relagens=1 and relatype='01' ";
        SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
        sqlbv3.sql(sSQL);
        sqlbv3.put("agentcode", this.mAgentCode);
       SSRS sSSRS = new SSRS();
       ExeSQL sExeSQL = new ExeSQL();
       sSSRS =  sExeSQL.execSQL(sqlbv3);
        if(sSSRS.getMaxRow()>0)
        {
          sLARelationSchema.setMakeDate(sSSRS.GetText(1,1));
          sLARelationSchema.setMakeTime(sSSRS.GetText(1,2));
        }
        else
        {
          sLARelationSchema.setMakeDate(this.mCurrentDate);
          sLARelationSchema.setMakeTime(this.mCurrentTime);
        }
        sLARelationSchema.setModifyDate(this.mCurrentDate);
        sLARelationSchema.setModifyTime(this.mCurrentTime);
        sLARelationSchema.setOperator(this.mOperator);
        sLARelationSchema.setRearCommFlag("1");
        sLARelationSchema.setRearFlag("1");
        sLARelationSchema.setstartDate(this.mStartDate);
        sLARelationSchema.setEndDate("");
        sLARelationSchema.setRearStartYear("1");
        sLARelationSchema.setReCalFlag("1");
      }
      else
      {
        //处理间接增员关系,保持当前处理人员的增员人的向前链关系不变,只把增员代数增加1代
        sLARelationSchema.setSchema(tBefLARelationSet.get(i));
        sLARelationSchema.setAgentCode(this.mAgentCode);
        sLARelationSchema.setRelaGens(sLARelationSchema.getRelaGens()+1);
        sLARelationSchema.setAgentGroup(this.mRelaAgentGroup);
        sLARelationSchema.setstartDate(this.mStartDate);
//        sLARelationSchema.setMakeDate(this.mCurrentDate);
//        sLARelationSchema.setMakeTime(this.mCurrentTime);
        sLARelationSchema.setModifyDate(this.mCurrentDate);
        sLARelationSchema.setModifyTime(this.mCurrentTime);
        sLARelationSchema.setOperator(this.mOperator);
      }
      this.mBefLARelationSet.add(sLARelationSchema);
    }
    //处理向后链
    //记录当前人的向后增员链
    LARelationSet tCurrAfterLARelationSet = new LARelationSet();
    //暂存当前人的向前增员链
    LARelationSet tTempLARelationSet = new LARelationSet();
    //查询当前人的向后增员链
    this.setAfterRelation();
    tCurrAfterLARelationSet.set(this.getAfterRelation());
    tTempLARelationSet.set(this.mBefLARelationSet);
    for(int i=1;i<=tTempLARelationSet.size();i++)
    {
      for(int j=1;j<=tCurrAfterLARelationSet.size();j++)
      {
        LARelationSchema tTempLARelationSchema = new LARelationSchema();
        tTempLARelationSchema.setSchema(tCurrAfterLARelationSet.get(j));
        tTempLARelationSchema.setRelaGens(tCurrAfterLARelationSet.get(j).getRelaGens()+tTempLARelationSet.get(i).getRelaGens());
        //备份
        LARelationDB tTempLARelationDB = new LARelationDB();
        LARelationSet sTempLARelationSet = new LARelationSet();
        tTempLARelationDB.setSchema(tTempLARelationSchema);
        tTempLARelationDB.setRelaAgentCode("");
        String sSQL = "select * from LARelation where RelaType='01' "
                    + " and RelaGens="+"?RelaGens?"+" and AgentCode='"+"?AgentCode?"+"'";
        logger.debug("sSQL:"+sSQL);
        SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
        sqlbv4.sql(sSQL);
        sqlbv4.put("RelaGens", tTempLARelationSchema.getRelaGens());
        sqlbv4.put("AgentCode", tTempLARelationSchema.getAgentCode());
        sTempLARelationSet = tTempLARelationDB.executeQuery(sqlbv4);
        if(sTempLARelationSet.size()>0)
        {
          //把需要删除的数据添加到集合中
          this.mDeleteLARelationSet.add(sTempLARelationSet.get(1).getSchema());
          //
          LARelationBSchema sLARelationBSchema = new LARelationBSchema();
          LARelationSchema sLARelationSchema = new LARelationSchema();
          sLARelationSchema = sTempLARelationSet.get(1).getSchema();
          tReflections.transFields(sLARelationBSchema,sLARelationSchema);
          sLARelationBSchema.setMakeDate2(sLARelationSchema.getMakeDate());
          sLARelationBSchema.setMakeTime2(sLARelationSchema.getMakeTime());
          sLARelationBSchema.setModifyDate2(sLARelationSchema.getModifyDate());
          sLARelationBSchema.setModifyTime2(sLARelationSchema.getModifyTime());
          sLARelationBSchema.setOperator2(sLARelationSchema.getOperator());
          sLARelationBSchema.setMakeDate(this.mCurrentDate);
          sLARelationBSchema.setMakeTime(this.mCurrentTime);
          sLARelationBSchema.setModifyDate(this.mCurrentDate);
          sLARelationBSchema.setModifyTime(this.mCurrentTime);
          sLARelationBSchema.setOperator(this.mOperator);
          sLARelationBSchema.setEdorNo(this.mEdorNo);
          sLARelationBSchema.setEdorType(this.mEdorType);
          sLARelationBSchema.setIndexCalNo(this.mIndexCalNo);
          this.mAfterLARelationBSet.add(sLARelationBSchema);
        }
        //开始修改其他字段数据
        tTempLARelationSchema.setRelaAgentCode(tTempLARelationSet.get(i).getRelaAgentCode());
//        tTempLARelationSchema.setMakeDate(this.mCurrentDate);
//        tTempLARelationSchema.setMakeTime(this.mCurrentTime);
        tTempLARelationSchema.setModifyDate(this.mCurrentDate);
        tTempLARelationSchema.setModifyTime(this.mCurrentTime);
        tTempLARelationSchema.setOperator(this.mOperator);
        this.mBefLARelationSet.add(tTempLARelationSchema);
      }
    }
    return true;
  }
  /**
   * 离职人员切断增员关系
   *
   */
  public boolean setDimissionRelation()
  {
    Reflections tReflections = new Reflections();
    //1- 处理向前增员链
    if(!this.setBefRelation()){
      logger.debug("查询当前处理人员向前增员链失败");
    }
    //记录需要删除的当前被增员人的向前增员链
    LARelationSet tDelBefLARelationSet = new LARelationSet();
    //查询当前被增员人的向前增员链
    LARelationSet tCurrBefLARelationSet = new LARelationSet();
    //查询当前被增员人的向前增员链
    LARelationSet tTempBefLARelationSet = new LARelationSet();
    tDelBefLARelationSet.set(this.getBefRelation());
    //tCurrBefLARelationSet.set(this.getBefRelation());
    //把当前被增员人的向前增员链清空
    this.mBefLARelationSet.clear();
    if(tDelBefLARelationSet.size()>0)
    {
      logger.debug("开始备份当前增员人的向前链");
      for(int m=1;m<=tDelBefLARelationSet.size();m++)
      {
        //增员链有效的记录为需要删除的记录
        if(tDelBefLARelationSet.get(m).getRearFlag().equals("1"))
        {
           this.mDeleteLARelationSet.add(tDelBefLARelationSet.get(m));
           tTempBefLARelationSet.add(tDelBefLARelationSet.get(m));
           //tCurrBefLARelationSet.add(tDelBefLARelationSet.get(m));
        }
      }

      for(int i=1;i<=tTempBefLARelationSet.size();i++)
      {
        LARelationSchema sLARelationSchema = new LARelationSchema();
        sLARelationSchema = tTempBefLARelationSet.get(i);
        LARelationBSchema sLARelationBSchema = new LARelationBSchema();
        tReflections.transFields(sLARelationBSchema,sLARelationSchema);
        sLARelationBSchema.setMakeDate2(sLARelationSchema.getMakeDate());
        sLARelationBSchema.setMakeTime2(sLARelationSchema.getMakeTime());
        sLARelationBSchema.setModifyDate2(sLARelationSchema.getModifyDate());
        sLARelationBSchema.setModifyTime2(sLARelationSchema.getModifyTime());
        sLARelationBSchema.setOperator2(sLARelationSchema.getOperator());
        sLARelationBSchema.setMakeDate(this.mCurrentDate);
        sLARelationBSchema.setMakeTime(this.mCurrentTime);
        sLARelationBSchema.setModifyDate(this.mCurrentDate);
        sLARelationBSchema.setModifyTime(this.mCurrentTime);
        sLARelationBSchema.setOperator(this.mOperator);
        sLARelationBSchema.setEdorNo(this.mEdorNo);
        sLARelationBSchema.setEdorType(this.mEdorType);
        sLARelationBSchema.setIndexCalNo(this.mIndexCalNo);
        this.mAfterLARelationBSet.add(sLARelationBSchema);
      }
    }
    for(int i=1;i<=tTempBefLARelationSet.size();i++)
    {
      if(tTempBefLARelationSet.get(i).getRearFlag().equals("1"))
      {
        LARelationSchema sLARelationSchema = new LARelationSchema();
        sLARelationSchema.setSchema(tTempBefLARelationSet.get(i));
        sLARelationSchema.setRearFlag("0");
        sLARelationSchema.setModifyDate(this.mCurrentDate);
        sLARelationSchema.setModifyTime(this.mCurrentTime);
        sLARelationSchema.setOperator(this.mOperator);
        sLARelationSchema.setRearCommFlag("0");
        sLARelationSchema.setEndDate(this.mCurrentDate);
        sLARelationSchema.setRearStartYear("0");
        sLARelationSchema.setReCalFlag("0");
        this.mBefLARelationSet.add(sLARelationSchema);
      }
    }
    //2- 处理向后增员链
    //处理向后链
    //记录当前人的向后增员链
    LARelationSet tCurrAfterLARelationSet = new LARelationSet();
    //暂存需要删除当前人的向后增员链
    LARelationSet tAfterDelLARelationSet = new LARelationSet();
    LARelationSet tTempLARelationSet = new LARelationSet();
    //查询当前人的向后增员链
    this.setAfterRelation();
    tCurrAfterLARelationSet.set(this.getAfterRelation());
    tAfterDelLARelationSet.set(this.getAfterRelation());
    for(int i=1;i<=tAfterDelLARelationSet.size();i++)
    {
      //增员链有效的记录为需要删除的记录
      if(tAfterDelLARelationSet.get(i).getRearFlag().equals("1"))
      {
        this.mDeleteLARelationSet.add(tAfterDelLARelationSet.get(i));
        tTempLARelationSet.add(tAfterDelLARelationSet.get(i));
      }
    }
    for(int i=1;i<=tTempLARelationSet.size();i++)
    {
      LARelationSchema sLARelationSchema = new LARelationSchema();
      sLARelationSchema = tTempLARelationSet.get(i);
      LARelationBSchema sLARelationBSchema = new LARelationBSchema();
      tReflections.transFields(sLARelationBSchema,sLARelationSchema);
      sLARelationBSchema.setMakeDate2(sLARelationSchema.getMakeDate());
      sLARelationBSchema.setMakeTime2(sLARelationSchema.getMakeTime());
      sLARelationBSchema.setModifyDate2(sLARelationSchema.getModifyDate());
      sLARelationBSchema.setModifyTime2(sLARelationSchema.getModifyTime());
      sLARelationBSchema.setOperator2(sLARelationSchema.getOperator());
      sLARelationBSchema.setMakeDate(this.mCurrentDate);
      sLARelationBSchema.setMakeTime(this.mCurrentTime);
      sLARelationBSchema.setModifyDate(this.mCurrentDate);
      sLARelationBSchema.setModifyTime(this.mCurrentTime);
      sLARelationBSchema.setOperator(this.mOperator);
      sLARelationBSchema.setEdorNo(this.mEdorNo);
      sLARelationBSchema.setEdorType(this.mEdorType);
      sLARelationBSchema.setIndexCalNo(this.mIndexCalNo);
      this.mAfterLARelationBSet.add(sLARelationBSchema);
    }
    for(int i=1;i<=tTempLARelationSet.size();i++)
    {
      if(tTempLARelationSet.get(i).getRearFlag().equals("1"))
      {
         LARelationSchema sLARelationSchema = new LARelationSchema();
         sLARelationSchema.setSchema(tTempLARelationSet.get(i));
         sLARelationSchema.setRearFlag("0");
         sLARelationSchema.setModifyDate(this.mCurrentDate);
         sLARelationSchema.setModifyTime(this.mCurrentTime);
         sLARelationSchema.setOperator(this.mOperator);
         sLARelationSchema.setRearCommFlag("0");
         sLARelationSchema.setEndDate(this.mCurrentDate);
         sLARelationSchema.setRearStartYear("0");
         sLARelationSchema.setReCalFlag("0");
         this.mBefLARelationSet.add(sLARelationSchema);
      }
    }
//    this.mBefLARelationSet.add(tTempLARelationSet);

    return true;
  }
  /**
   * 离职人员切断增员关系
   *
   */
  public boolean setDimissionRelation(Connection conn)
  {
    LARelationSet tLARelationSet = new LARelationSet();
    LARelationDBSet tLARelationDBSet = new LARelationDBSet(conn);
    LARelationBDBSet tLARelationBDBSet = new LARelationBDBSet(conn);
    if(!this.setDimissionRelation())
    {
      logger.debug("处理增员关系失败");
      dealError("updateLATree","处理增员关系失败!");
      return false;
    }
    else
    {
      tLARelationSet.set(this.getBefRelation());
      if(this.getDeleteLARelationSet().size()>0)
      {
        tLARelationDBSet.add(this.getDeleteLARelationSet());
        if(!tLARelationDBSet.delete())
        {
          logger.debug("删除育成关系失败");
          dealError("updateLATree","删除育成关系失败!");
          return false;
        }
      }
      if(this.getBackUpRalation().size()>0)
      {
        tLARelationBDBSet.add(this.getBackUpRalation());
        if(!tLARelationBDBSet.insert())
        {
          logger.debug("备份育成关系失败");
          dealError("updateLATree","备份育成关系失败!");
          return false;
        }
      }
      tLARelationDBSet.clear();
      tLARelationDBSet.add(tLARelationSet);
      if(tLARelationDBSet.size()>0)
      {
        if(!tLARelationDBSet.insert())
        {
          logger.debug("更新育成关系失败");
          dealError("updateLATree","更新育成关系失败!");
          return false;
        }
      }
    }
    return true;
  }

  /**
   *  函数功能：通过增员功能或初始、行政信息修改功能修改增员人，需考虑如下几中情况：
   *          1. 当前处理人员没有向后链，那只需修改此人向前链；
   *          2. 当前处理人员有向后链，除了修改本人向前链还需修改其向后链人员的向前链；
   *  适用模块：增员功能或初始、行政信息修改功能
   */
  public boolean setRelationship(String tAgentCode,Connection conn){
    LARelationSet tLARelationSet = new LARelationSet();
    LARelationDBSet tLARelationDBSet = new LARelationDBSet(conn);
    LARelationBDBSet tLARelationBDBSet = new LARelationBDBSet(conn);
    if(!this.setRelationship(tAgentCode))
    {
      logger.debug("处理育成关系失败");
      dealError("updateLATree","处理育成关系失败!");
      return false;
    }
    else
    {
      tLARelationSet.set(this.getBefRelation());
      if(this.getDeleteLARelationSet().size()>0)
      {
        tLARelationDBSet.add(this.getDeleteLARelationSet());
        if(!tLARelationDBSet.delete())
        {
          logger.debug("删除育成关系失败");
          dealError("updateLATree","删除育成关系失败!");
          return false;
        }
      }
      if(this.getBackUpRalation().size()>0)
      {
        tLARelationBDBSet.add(this.getBackUpRalation());
        if(!tLARelationBDBSet.insert())
        {
          logger.debug("备份育成关系失败");
          dealError("updateLATree","备份育成关系失败!");
          return false;
        }
      }
      tLARelationDBSet.clear();
      tLARelationDBSet.add(tLARelationSet);
      if(tLARelationDBSet.size()>0)
      {
        if(!tLARelationDBSet.insert())
        {
          logger.debug("更新育成关系失败");
          dealError("updateLATree","更新育成关系失败!");
          return false;
        }
      }
    }
    return true;
  }
 //tongmeng 2006-10-26 add
 //离职恢复恢复增员关系
 /**
  *
  * @return
  */
 //------------------------------------------------------------------------//
 public boolean setDimissionReRelation()
 {
   logger.debug("开始处理离职恢复程序...");
   Reflections tReflections = new Reflections();
   //1- 处理向前增员链
   if(!this.setBefRelation()){
     logger.debug("查询当前处理人员向前增员链失败");
   }
   //记录需要删除的当前被增员人的向前增员链
   LARelationSet tDelBefLARelationSet = new LARelationSet();
   //查询当前被增员人的向前增员链
   LARelationSet tCurrBefLARelationSet = new LARelationSet();
   //查询当前被增员人的向前增员链
   LARelationSet tTempBefLARelationSet = new LARelationSet();
   tDelBefLARelationSet.set(this.getBefRelation());
   //tCurrBefLARelationSet.set(this.getBefRelation());
   //把当前被增员人的向前增员链清空
   this.mBefLARelationSet.clear();
   if(tDelBefLARelationSet.size()>0)
   {
     logger.debug("开始备份当前增员人的向前链");
     this.mDeleteLARelationSet.add(tDelBefLARelationSet);
     tTempBefLARelationSet.add(tDelBefLARelationSet);
     //把需要删除的数据进行备份
     for(int i=1;i<=tTempBefLARelationSet.size();i++)
     {
       LARelationSchema sLARelationSchema = new LARelationSchema();
       sLARelationSchema = tTempBefLARelationSet.get(i);
       LARelationBSchema sLARelationBSchema = new LARelationBSchema();
       tReflections.transFields(sLARelationBSchema,sLARelationSchema);
       sLARelationBSchema.setMakeDate2(sLARelationSchema.getMakeDate());
       sLARelationBSchema.setMakeTime2(sLARelationSchema.getMakeTime());
       sLARelationBSchema.setModifyDate2(sLARelationSchema.getModifyDate());
       sLARelationBSchema.setModifyTime2(sLARelationSchema.getModifyTime());
       sLARelationBSchema.setOperator2(sLARelationSchema.getOperator());
       sLARelationBSchema.setMakeDate(this.mCurrentDate);
       sLARelationBSchema.setMakeTime(this.mCurrentTime);
       sLARelationBSchema.setModifyDate(this.mCurrentDate);
       sLARelationBSchema.setModifyTime(this.mCurrentTime);
       sLARelationBSchema.setOperator(this.mOperator);
       sLARelationBSchema.setEdorNo(this.mEdorNo);
       sLARelationBSchema.setEdorType(this.mEdorType);
       sLARelationBSchema.setIndexCalNo(this.mIndexCalNo);
       this.mAfterLARelationBSet.add(sLARelationBSchema);
     }
   }
   //开始处理向前增员关系的恢复
   for(int i=1;i<=tTempBefLARelationSet.size();i++)
   {
     LARelationSchema sLARelationSchema = new LARelationSchema();
     sLARelationSchema.setSchema(tTempBefLARelationSet.get(i));
     String tSQL = "select (case count(*) when 0 then 0 else 1 end) from laagent where agentstate<='02' "
                 + " and agentcode = '"+"?agentcode?"+"' ";
     SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
     sqlbv5.sql(tSQL);
     sqlbv5.put("agentcode", sLARelationSchema.getRelaAgentCode());
     ExeSQL tExeSQL = new ExeSQL();
     String tempResult = "";
     if(sLARelationSchema.getRelaAgentCode()!=null&&!sLARelationSchema.getRelaAgentCode().equals(""))
     {
       tempResult = tExeSQL.getOneValue(sqlbv5);
     }
     else
     {
       tempResult = "No";
     }
     //增员人为空或者增员人在职
     if(tempResult.equals("No")||tempResult.compareTo("1")==0)
     {
       //增员人在职，恢复增员关系
       //清空增员止期
       sLARelationSchema.setRearFlag("1");
       sLARelationSchema.setEndDate("");
       sLARelationSchema.setReCalFlag("1");
     }
     else
     {
       //增员人离职，不恢复增员关系
       sLARelationSchema.setRearFlag("0");
       sLARelationSchema.setReCalFlag("0");
     }
     sLARelationSchema.setModifyDate(this.mCurrentDate);
     sLARelationSchema.setModifyTime(this.mCurrentTime);
     sLARelationSchema.setOperator(this.mOperator);
     sLARelationSchema.setRearCommFlag("0");//没有用
     sLARelationSchema.setRearStartYear("0");//没有用
     this.mBefLARelationSet.add(sLARelationSchema);
   }
   //2- 处理向后增员链
   //处理向后链
   //记录当前人的向后增员链
   LARelationSet tCurrAfterLARelationSet = new LARelationSet();
   //暂存需要删除当前人的向后增员链
   LARelationSet tAfterDelLARelationSet = new LARelationSet();
   LARelationSet tTempLARelationSet = new LARelationSet();
   //查询当前人的向后增员链
   this.setAfterRelation();
   tCurrAfterLARelationSet.set(this.getAfterRelation());
   tAfterDelLARelationSet.set(this.getAfterRelation());
   //记录需要删除的记录
   this.mDeleteLARelationSet.add(tAfterDelLARelationSet);
   tTempLARelationSet.add(tAfterDelLARelationSet);
   //备份需要删除的数据
   for(int i=1;i<=tTempLARelationSet.size();i++)
   {
     LARelationSchema sLARelationSchema = new LARelationSchema();
     sLARelationSchema = tTempLARelationSet.get(i);
     LARelationBSchema sLARelationBSchema = new LARelationBSchema();
     tReflections.transFields(sLARelationBSchema,sLARelationSchema);
     sLARelationBSchema.setMakeDate2(sLARelationSchema.getMakeDate());
     sLARelationBSchema.setMakeTime2(sLARelationSchema.getMakeTime());
     sLARelationBSchema.setModifyDate2(sLARelationSchema.getModifyDate());
     sLARelationBSchema.setModifyTime2(sLARelationSchema.getModifyTime());
     sLARelationBSchema.setOperator2(sLARelationSchema.getOperator());
     sLARelationBSchema.setMakeDate(this.mCurrentDate);
     sLARelationBSchema.setMakeTime(this.mCurrentTime);
     sLARelationBSchema.setModifyDate(this.mCurrentDate);
     sLARelationBSchema.setModifyTime(this.mCurrentTime);
     sLARelationBSchema.setOperator(this.mOperator);
     sLARelationBSchema.setEdorNo(this.mEdorNo);
     sLARelationBSchema.setEdorType(this.mEdorType);
     sLARelationBSchema.setIndexCalNo(this.mIndexCalNo);
     this.mAfterLARelationBSet.add(sLARelationBSchema);
   }
   //恢复向后增员链
   for(int i=1;i<=tTempLARelationSet.size();i++)
   {
     LARelationSchema sLARelationSchema = new LARelationSchema();
     sLARelationSchema.setSchema(tTempLARelationSet.get(i));
     //
     String tSQL = "select (case count(*) when 0 then 0 else 1 end) from laagent where agentstate<='02' "
                 + " and agentcode = '"+"?agentcode?"+"' ";
     SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
     sqlbv6.sql(tSQL);
     sqlbv6.put("agentcode",sLARelationSchema.getAgentCode());
     ExeSQL tExeSQL = new ExeSQL();
     String tempResult = "";
     if(sLARelationSchema.getAgentCode()!=null&&!sLARelationSchema.getAgentCode().equals(""))
     {
       tempResult = tExeSQL.getOneValue(sqlbv6);
     }
     else
     {
       tempResult = "No";
     }
     //被增员人为空或者增员人在职
     if(tempResult.equals("No")||tempResult.compareTo("1")==0)
     {
       //增员人在职，恢复增员关系
       //清空增员止期
       sLARelationSchema.setRearFlag("1");
       sLARelationSchema.setEndDate("");
       sLARelationSchema.setReCalFlag("1");
     }
     else
     {
       //增员人离职，不恢复增员关系
       sLARelationSchema.setRearFlag("0");
       sLARelationSchema.setReCalFlag("0");
     }
     //
     sLARelationSchema.setModifyDate(this.mCurrentDate);
     sLARelationSchema.setModifyTime(this.mCurrentTime);
     sLARelationSchema.setOperator(this.mOperator);
     sLARelationSchema.setRearCommFlag("0");
     sLARelationSchema.setRearStartYear("0");
     this.mBefLARelationSet.add(sLARelationSchema);
   }
   return true;
 }
//------------------------------------------------------------------------//
 //tongmeng 2006-10-26 add
 //离职恢复恢复增员关系
 /**
  *
  * @param conn
  * @return
  */
 public boolean setDimissionReRelation(Connection conn)
 {
   LARelationSet tLARelationSet = new LARelationSet();
   LARelationDBSet tLARelationDBSet = new LARelationDBSet(conn);
   LARelationBDBSet tLARelationBDBSet = new LARelationBDBSet(conn);
   if(!this.setDimissionReRelation())
   {
     logger.debug("恢复增员关系失败");
     dealError("updateLATree","恢复增员关系失败!");
     return false;
   }
   else
   {
     tLARelationSet.set(this.getBefRelation());
     if(this.getDeleteLARelationSet().size()>0)
     {
       tLARelationDBSet.add(this.getDeleteLARelationSet());
       if(!tLARelationDBSet.delete())
       {
         logger.debug("删除增员关系失败");
         dealError("updateLATree","删除增员关系失败!");
         return false;
       }
     }
     if(this.getBackUpRalation().size()>0)
     {
       tLARelationBDBSet.add(this.getBackUpRalation());
       if(!tLARelationBDBSet.insert())
       {
         logger.debug("备份增员关系失败");
         dealError("updateLATree","备份增员关系失败!");
         return false;
       }
     }
     tLARelationDBSet.clear();
     logger.debug("tLARelationSet.size()["+tLARelationSet.size()+"]");
     tLARelationDBSet.add(tLARelationSet);
     if(tLARelationDBSet.size()>0)
     {
       if(!tLARelationDBSet.insert())
       {
         logger.debug("更新增员关系失败");
         dealError("updateLATree","更新增员关系失败!");
         return false;
       }
     }
   }
   return true;
 }

 //tongmeng 2006-10-26 add
 //离职恢复恢复永续经营奖
 /**
  *
  * @return
  */
 public boolean setDimissionReInherit()
 {
   //永续经营奖
   //RelaGens(0);
   //RelaType("03");
   //RelaLevel("20");
   String tSQL = "";
   tSQL = "select * from larelation where relagens=0 and relatype='03' and relalevel='20' "
        + " and agentcode = '"+"?agentcode?"+"' ";
   logger.debug("tSQL:"+tSQL);
   SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
   sqlbv7.sql(tSQL);
   sqlbv7.put("agentcode",this.mAgentCode);
   LARelationSet tQueryLARelationSet = new LARelationSet();
   LARelationDB tQueryLARelationDB = new LARelationDB();
   tQueryLARelationSet = tQueryLARelationDB.executeQuery(sqlbv7);
   if(tQueryLARelationSet.size()>0)
   {
     //保存需要删除的永续经营奖关系
     this.mDeleteLARelationSet.add(tQueryLARelationSet);
     //备份需要删除的永续经营奖关系
     for(int i=1;i<=tQueryLARelationSet.size();i++)
     {
       logger.debug("开始备份需要删除的数据...");
       Reflections tReflections = new Reflections();
       LARelationSchema sLARelationSchema = new LARelationSchema();
       sLARelationSchema = tQueryLARelationSet.get(i);
       LARelationBSchema sLARelationBSchema = new LARelationBSchema();
       tReflections.transFields(sLARelationBSchema,sLARelationSchema);
       sLARelationBSchema.setMakeDate2(sLARelationSchema.getMakeDate());
       sLARelationBSchema.setMakeTime2(sLARelationSchema.getMakeTime());
       sLARelationBSchema.setModifyDate2(sLARelationSchema.getModifyDate());
       sLARelationBSchema.setModifyTime2(sLARelationSchema.getModifyTime());
       sLARelationBSchema.setOperator2(sLARelationSchema.getOperator());
       sLARelationBSchema.setMakeDate(this.mCurrentDate);
       sLARelationBSchema.setMakeTime(this.mCurrentTime);
       sLARelationBSchema.setModifyDate(this.mCurrentDate);
       sLARelationBSchema.setModifyTime(this.mCurrentTime);
       sLARelationBSchema.setOperator(this.mOperator);
       sLARelationBSchema.setEdorNo(this.mEdorNo);
       sLARelationBSchema.setEdorType(this.mEdorType);
       sLARelationBSchema.setIndexCalNo(this.mIndexCalNo);
       this.mAfterLARelationBSet.add(sLARelationBSchema);
     }
   }
   return true;
 }
 //tongmeng 2006-10-26 add
 //离职恢复恢复永续经营奖
 /**
  *
  * @param conn
  * @return
  */
 public boolean setDimissionReInherit(Connection conn)
 {
   LARelationSet tLARelationSet = new LARelationSet();
   LARelationDBSet tLARelationDBSet = new LARelationDBSet(conn);
   LARelationBDBSet tLARelationBDBSet = new LARelationBDBSet(conn);
   logger.debug("开始处理永续经营奖");
   if(!this.setDimissionReInherit())
   {
     logger.debug("恢复永续经营奖关系失败");
     dealError("updateLATree","恢复永续经营奖关系失败!");
     return false;
   }
   else
   {
     logger.debug("开始保存数据...");
     if(this.getDeleteLARelationSet().size()>0)
     {
       tLARelationDBSet.add(this.getDeleteLARelationSet());
       if(!tLARelationDBSet.delete())
       {
         logger.debug("删除永续经营奖关系失败");
         dealError("updateLATree","删除永续经营奖关系失败!");
         return false;
       }
     }
     if(this.getBackUpRalation().size()>0)
     {
       tLARelationBDBSet.add(this.getBackUpRalation());
       if(!tLARelationBDBSet.insert())
       {
         logger.debug("备份永续经营奖关系失败");
         dealError("updateLATree","备份永续经营奖关系失败!");
         return false;
       }
     }
   }
   return true;
 }
  /**
   * 保存错误
   */
  private void dealError(String FuncName, String ErrMsg)
  {
    CError tError = new CError();
    tError.moduleName = "DesignRearRelationship";
    tError.functionName = FuncName;
    tError.errorMessage = ErrMsg;
    mErrors.addOneError(tError);
  }
}
