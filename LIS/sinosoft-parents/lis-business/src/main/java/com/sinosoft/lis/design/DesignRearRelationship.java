package com.sinosoft.lis.design;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: 设计育成关系公共类 </p>
 * <p>Copyright: Copyright (c) 200603 </p>
 * <p>Company: Sinosoft</p>
 * @author LL
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

public class DesignRearRelationship {
private static Logger logger = Logger.getLogger(DesignRearRelationship.class);
  //全局业务变量定义
  /** 错误处理类，每个需要错误处理的类中都放置该类 */
  public CErrors mErrors = new CErrors();
  //当前处理人员
  private String mAgentCode = "";
  //育成级别
  private String mRelaLevel = "";
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
  //育成开始时间
  private String mStartDate = "";
  //育成止期
  private String mEndDate = "";
  //考核年月
  private String mIndexCalNo = "";
  //育成组
  private String mRelaAgentGroup = "";
  //操作员编码
  private String mOperator = "";
  //转储类型
  private String mEdorType = "";
  public DesignRearRelationship() {
  }
  public static void main(String[] args) {
    DesignRearRelationship designRearRelationship1 = new DesignRearRelationship();
    designRearRelationship1.setAgentCode("8611000005","01");
    designRearRelationship1.setCurrentDate("2006-4-1");
    designRearRelationship1.setCurrentTime("08:00:00");
    designRearRelationship1.setEdorNo("000000001");
    designRearRelationship1.setEdorType("01");
    designRearRelationship1.setIndexCalNo("200604");
    designRearRelationship1.setOperator("aa");
    designRearRelationship1.setStartDate("2006-5-1");
    designRearRelationship1.setRelationShipByRelPerXZ("8611000003");
//    designRearRelationship1.setPromoteRelationShipByRelPer("8611000003");
//    designRearRelationship1.setDemoteRelationShipByRelPer("8611000001");
    LARelationSet s = designRearRelationship1.getBefRelation();
    logger.debug("处理完育成链");
    for(int i=1 ;i<=s.size();i++)
    {
      logger.debug("AgentCode:"+s.get(i).getAgentCode()+"---RelaAgentCode:"+s.get(i).getRelaAgentCode()+"--Gen:"+s.get(i).getRelaGens()+"--RearFlag:"+s.get(i).getRearFlag()+"--ReCal:"+s.get(i).getReCalFlag()+"--RelaLevel:"+s.get(i).getRelaLevel()+"--StartDate:"+s.get(i).getstartDate());
    }
    LARelationBSet s1 = designRearRelationship1.getBackUpRalation();
    logger.debug("处理完备份链");
    for(int i=1 ;i<=s1.size();i++)
    {
      logger.debug("AgentCode:"+s1.get(i).getAgentCode()+"---RelaAgentCode:"+s1.get(i).getRelaAgentCode()+"--Gen:"+s1.get(i).getRelaGens()+"--RearFlag:"+s1.get(i).getRearFlag()+"--ReCal:"+s1.get(i).getReCalFlag()+"--RelaLevel:"+s1.get(i).getRelaLevel()+"--StartDate:"+s1.get(i).getstartDate());
    }
    LARelationSet s2 = designRearRelationship1.getDeleteLARelationSet();
    logger.debug("处理完需删除链");
    for(int i=1 ;i<=s2.size();i++)
    {
      logger.debug("AgentCode:"+s2.get(i).getAgentCode()+"---RelaAgentCode:"+s2.get(i).getRelaAgentCode()+"--Gen:"+s2.get(i).getRelaGens()+"--RearFlag:"+s2.get(i).getRearFlag()+"--ReCal:"+s2.get(i).getReCalFlag()+"--RelaLevel:"+s2.get(i).getRelaLevel()+"--StartDate:"+s2.get(i).getstartDate());
    }
  }

  /**
   * 函数功能：设置育成组内部编码
   * @param tRelaAgentGroup 育成组内部编码
   */
  public void setRelaAgentGroup(String tRelaAgentGroup){
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
   * 函数功能：设置当前处理人员,育成级别
   * @param tAgentCode 代理人编码,tRelaLevel 育成级别
   */
  public void setAgentCode(String tAgentCode,String tRelaLevel){
    this.mAgentCode = tAgentCode;
    this.mRelaLevel = tRelaLevel;
  }
  /**
   * 函数功能：设置育成止期
   * @param tEndDate 育成止期
   */
  public void setEndDate(String tEndDate){
     this.mEndDate = tEndDate;
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
   * 函数功能:设置育成开始时间
   * @param tStartDate 育成开始时间
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
   * 函数功能：设置当前处理人员的向前育成链
   * @param tFlag 有效无效标记
   * @return boolean
   */
  public boolean setBefRelation(String tFlag){
    //此处根据当前mAgentCode 去查询出他的所有向前链,并给mBefLARelationSet赋值
    String sSQL = "";
    sSQL = "select * from LARelation where RelaType='02' and RelaLevel='"+"?RelaLevel?"+"' "
         + " and AgentCode='"+"?AgentCode?"+"' ";
    if(!tFlag.equals(""))
    {
      sSQL = sSQL+" and RearFlag = '"+"?tFlag?"+"' ";
    }
    logger.debug("sSQL:"+sSQL);
    SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
    sqlbv1.sql(sSQL);
    sqlbv1.put("RelaLevel", this.mRelaLevel);
    sqlbv1.put("AgentCode", this.mAgentCode);
    sqlbv1.put("tFlag", tFlag);
    LARelationSet sLARelationSet = new LARelationSet();
    LARelationDB sLARelationDB = new LARelationDB();
    sLARelationSet = sLARelationDB.executeQuery(sqlbv1);
    if(sLARelationSet.size()>0)
      this.mBefLARelationSet.set(sLARelationSet);
    return true;
  }
  /**
   * 函数功能：获得当前处理人员的向前育成链
   * @return LARelationSet
   */
  public LARelationSet getBefRelation(){
    //此处返回当前处理人员的向前链
    //注:1-如果是先调用setBefRelation方法,再执行此方法后,返回的是mAgentCode向前链
    //   2-如果是通过其他方法如 setPromoteRelationShipByRelPer,后再执行此方法后
    //     返回的不只包括mAgentCode 的向前链,还包括他的向后链中需要修改的数据
    return mBefLARelationSet;
  }
  /**
   * 函数功能：设置当前处理人员的向后育成链
   * @
   * @return boolean
   */
  public boolean setAfterRelation(String tFlag){
    //此处根据当前mAgentCode 去查询出他的所有向后链,并给mAfterLARelationSet赋值
    String sSQL = "";
    if(!tFlag.equals(""))
      sSQL = "select * from larelation where RelaAgentCode='"+"?RelaAgentCode?"+"' "
      + " and RelaLevel='"+"?RelaLevel?"+"' and RelaType='02' and RearFlag = '"+tFlag+"' ";
    else
      sSQL = "select * from larelation where RelaAgentCode='"+"?RelaAgentCode?"+"' "
      + " and RelaLevel='"+"?RelaLevel?"+"' and RelaType='02' ";
    
    SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
    sqlbv2.sql(sSQL);
    sqlbv2.put("RelaLevel", this.mRelaLevel);
    sqlbv2.put("RelaAgentCode", this.mAgentCode);
    logger.debug("sSQL:"+sSQL);
    LARelationDB sLARelationDB = new LARelationDB();
    LARelationSet sLARelationSet = new LARelationSet();
    sLARelationSet = sLARelationDB.executeQuery(sqlbv2);
    if(sLARelationSet.size()>0)
      this.mAfterLARelationSet.set(sLARelationSet);
    return true;
  }
  /**
   * 函数功能：获得当前处理人员的向后育成链
   * @return LARelationSet
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
  /**************************************************************************/
  /***************  下述几个操作是否需要完成最终对数据库的修改还待进一步确认  **********/
  /**************************************************************************/
  /**
   * 函数功能：当前处理人员晋升，指定一个育成人，处理其相关育成关系，在此需要考虑如下两种情况：
   *         1. 当前处理人员晋升时不涉及到恢复向后链问题；
   *         2. 当前处理人员晋升时涉及到恢复向后链问题；
   * 适用模块：组织归属晋升
   */
  public boolean setPromoteRelationShipByRelPer(String tAgentCode ){
    Reflections tReflections = new Reflections();
    //记录mAgentCode的直接育成人
    DesignRearRelationship tDesignRearRelationship = new DesignRearRelationship();
    tDesignRearRelationship.setAgentCode(tAgentCode,this.mRelaLevel);
    //查询向前育成链
    if(!tDesignRearRelationship.setBefRelation("")){
      logger.debug("查询向前育成链失败");
    }
    //记录直接育成人的向前育成链
    LARelationSet tBefLARelationSet = new LARelationSet();
    tBefLARelationSet.set(tDesignRearRelationship.getBefRelation());

    //查询需要删除的当前被育成人的向前育成链
    LARelationSet tDelBefLARelationSet = new LARelationSet();
    //查询当前被育成人的向前所有育成链
    LARelationSet tCurrBefLARelationSet = new LARelationSet();
    if(!this.setBefRelation("")){
      logger.debug("查询当前处理人员向前育成链失败");
    }
    tDelBefLARelationSet.set(this.getBefRelation());
    tCurrBefLARelationSet.set(this.getBefRelation());
    //把当前被育成人的向前育成链清空
    this.mBefLARelationSet.clear();

    if(tDelBefLARelationSet.size()>0)
    {
      logger.debug("开始备份当前育成人的向前链");
      this.mDeleteLARelationSet.add(tDelBefLARelationSet);
      for(int i=1;i<=tDelBefLARelationSet.size();i++)
      {
        LARelationBSchema sLARelationBSchema = new LARelationBSchema();
        LARelationSchema sLARelationSchema = new LARelationSchema();
        sLARelationSchema = tDelBefLARelationSet.get(i);
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
    logger.debug("开始处理当前人的向前育成链");
    for(int i=0;i<=tBefLARelationSet.size();i++)
    {
      LARelationSchema sLARelationSchema = new LARelationSchema();
      if(i==0)
      {
        //处理直接育成关系,新生成
        logger.debug("开始处理直接育成关系");
        sLARelationSchema.setAgentCode(this.mAgentCode);
        sLARelationSchema.setRelaAgentCode(tAgentCode);
        sLARelationSchema.setRelaLevel(this.mRelaLevel);
        sLARelationSchema.setRelaGens(1);
        sLARelationSchema.setRelaType("02");
        sLARelationSchema.setAgentGroup(this.mRelaAgentGroup);
        sLARelationSchema.setMakeDate(this.mCurrentDate);
        sLARelationSchema.setMakeTime(this.mCurrentTime);
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
        //处理间接育成关系,保持当前处理人员的育成人的向前链关系不变,只把育成代数增加1代
        //此处备份已经在前面做处理
        sLARelationSchema.setSchema(tBefLARelationSet.get(i));
        sLARelationSchema.setAgentCode(this.mAgentCode);
        sLARelationSchema.setRelaGens(sLARelationSchema.getRelaGens()+1);
        sLARelationSchema.setAgentGroup(this.mRelaAgentGroup);
        sLARelationSchema.setstartDate(this.mStartDate);
        sLARelationSchema.setRearCommFlag("1");
        sLARelationSchema.setRearStartYear("1");
        sLARelationSchema.setMakeDate(this.mCurrentDate);
        sLARelationSchema.setMakeTime(this.mCurrentTime);
        sLARelationSchema.setModifyDate(this.mCurrentDate);
        sLARelationSchema.setModifyTime(this.mCurrentTime);
        sLARelationSchema.setOperator(this.mOperator);
      }
      this.mBefLARelationSet.add(sLARelationSchema);
    }
    //处理向后链
    //记录当前人的向后育成链
    LARelationSet tCurrAfterLARelationSet = new LARelationSet();
    //暂存当前人的向前育成链
    LARelationSet tTempLARelationSet = new LARelationSet();
    //查询当前人的所有向后育成链,并恢复育成关系
    //此处只恢复育成关系,其他不用修改
    this.setAfterRelation("");
    tCurrAfterLARelationSet.set(this.getAfterRelation());
    tTempLARelationSet.set(this.mBefLARelationSet);
    for(int j=1;j<=tCurrAfterLARelationSet.size();j++)
    {
      LARelationSchema tTempLARelationSchema = new LARelationSchema();
      tTempLARelationSchema.setSchema(tCurrAfterLARelationSet.get(j));
      int tempGen = tTempLARelationSchema.getRelaGens();
      String tempRela = tTempLARelationSchema.getRearFlag();
      //把需要删除的数据添加到集合中
      this.mDeleteLARelationSet.add(tTempLARelationSchema);
      //备份
      LARelationDB tTempLARelationDB = new LARelationDB();
      LARelationSet sTempLARelationSet = new LARelationSet();
      String sSQL = "select * from LARelation where RelaType='02' and RelaLevel='"+"?RelaLevel?"+"' "
                  + " and RelaGens="+"?RelaGens?"+" and AgentCode='"+"?AgentCode?"+"'";
      SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
      sqlbv3.sql(sSQL);
      sqlbv3.put("RelaLevel", tTempLARelationSchema.getRelaLevel());
      sqlbv3.put("RelaGens", tTempLARelationSchema.getRelaGens());
      sqlbv3.put("AgentCode",tTempLARelationSchema.getAgentCode());
      sTempLARelationSet = tTempLARelationDB.executeQuery(sqlbv3);
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
      //tongmeng 2006-09-29 modify
      //如果向后链的代理人离职或者职级小于当前育成级别，那么不恢复向后链
      String tSQL = "select a.agentstate,b.agentgrade from laagent a,latree b where a.agentcode=b.agentcode "
                  + " and a.agentcode ='"+"?agentcode?"+"' ";
      String tempAgentState = "";
      String tempAgentGrade = "";
      String standAgentGrade = "";
      if(tTempLARelationSchema.getRelaLevel().equals("01"))
      {
        standAgentGrade = "A05";
      }
      else if(tTempLARelationSchema.getRelaLevel().equals("02"))
      {
        standAgentGrade = "A07";
      }
      else if(tTempLARelationSchema.getRelaLevel().equals("03"))
      {
        standAgentGrade = "A08";
      }
      else if(tTempLARelationSchema.getRelaLevel().equals("04"))
      {
        standAgentGrade = "A09";
      }
      SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
      sqlbv4.sql(tSQL);
      sqlbv4.put("agentcode", tTempLARelationSchema.getAgentCode());
      ExeSQL tExeSQL = new ExeSQL();
      SSRS tSSRS = new SSRS();
      tSSRS = tExeSQL.execSQL(sqlbv4);
      if(tSSRS.getMaxRow()<=0)
      {
        logger.debug("查询代理人状态和职级信息出错");
        return false;
      }
      else
      {
        tempAgentState = tSSRS.GetText(1,1);
        tempAgentGrade = tSSRS.GetText(1,2);
      }
      if(tempAgentState.compareTo("02")<=0&&tempAgentGrade.compareTo(standAgentGrade)>=0)
      {
        tTempLARelationSchema.setRearFlag("1");//恢复向后链的育成关系
      }
//      tTempLARelationSchema.setstartDate(this.mStartDate);不修改育成时间
      tTempLARelationSchema.setModifyDate(this.mCurrentDate);
      tTempLARelationSchema.setModifyTime(this.mCurrentTime);
      tTempLARelationSchema.setOperator(this.mOperator);
      this.mBefLARelationSet.add(tTempLARelationSchema);
    }
    //处理当前人的向下级别.断开回算标记
    //把下级育成关系的回算标记全置为无效
    //tongmeng 2006-07-24 modify
    //组织归属晋升不对下级育成级别进行处理
    /*
    for(int i=Integer.parseInt(this.mRelaLevel)-1; i>0; i--)
    {
      logger.debug("当前处理育成级别:0"+i);
      LARelationSet tempLARelationSet = new LARelationSet();
      LARelationDB tempLARelationDB = new LARelationDB();
      String sSQL = " select * from larelation where relatype='02' and RelaLevel='0"+i+"' "
                  + " and agentcode = '"+this.mAgentCode+"' and RearFlag='1' "
                  + " union all "
                  + " select * from larelation where relatype='02' and RelaLevel='0"+i+"' "
                  + " and RelaAgentCode = '"+this.mAgentCode+"' and RearFlag='1' ";
      tempLARelationSet = tempLARelationDB.executeQuery(sSQL);
      //放到需要删除的育成关系集合中
      this.mDeleteLARelationSet.add(tempLARelationSet);
      for(int j=1; j<=tempLARelationSet.size(); j++)
      {
        LARelationSchema tempLARelationSchema = new LARelationSchema();
        tempLARelationSchema.setSchema(tempLARelationSet.get(j));
        //备份
        LARelationBSchema sLARelationBSchema = new LARelationBSchema();
        tReflections.transFields(sLARelationBSchema,tempLARelationSchema);
        sLARelationBSchema.setMakeDate2(tempLARelationSchema.getMakeDate());
        sLARelationBSchema.setMakeTime2(tempLARelationSchema.getMakeTime());
        sLARelationBSchema.setModifyDate2(tempLARelationSchema.getModifyDate());
        sLARelationBSchema.setModifyTime2(tempLARelationSchema.getModifyTime());
        sLARelationBSchema.setOperator2(tempLARelationSchema.getOperator());
        sLARelationBSchema.setMakeDate(this.mCurrentDate);
        sLARelationBSchema.setMakeTime(this.mCurrentTime);
        sLARelationBSchema.setModifyDate(this.mCurrentDate);
        sLARelationBSchema.setModifyTime(this.mCurrentTime);
        sLARelationBSchema.setOperator(this.mOperator);
        sLARelationBSchema.setEdorNo(this.mEdorNo);
        sLARelationBSchema.setEdorType(this.mEdorType);
        sLARelationBSchema.setIndexCalNo(this.mIndexCalNo);
        this.mAfterLARelationBSet.add(sLARelationBSchema);
        //断开回算标记
        tempLARelationSchema.setReCalFlag("0");
        tempLARelationSchema.setModifyDate(this.mCurrentDate);
        tempLARelationSchema.setModifyTime(this.mCurrentTime);
        tempLARelationSchema.setOperator(this.mOperator);
        this.mBefLARelationSet.add(tempLARelationSchema);
      }
    }
    */
    return true;
  }
  /**
   * 函数功能：当前处理人员晋升，指定一个育成人，处理其相关育成关系，在此需要考虑如下两种情况：
   *         1. 当前处理人员晋升时不涉及到恢复向后链问题；
   *         2. 当前处理人员晋升时涉及到恢复向后链问题；
   * 适用模块：组织归属晋升
   */
  public boolean setPromoteRelationShipByRelPer(String tAgentCode,Connection conn ){
    LARelationSet tLARelationSet = new LARelationSet();
    LARelationDBSet tLARelationDBSet = new LARelationDBSet(conn);
    LARelationBDBSet tLARelationBDBSet = new LARelationBDBSet(conn);
    if(!this.setPromoteRelationShipByRelPer(tAgentCode))
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
  /**
   * 函数功能：当前处理人员降级，指定一个育成人，处理其相关育成关系：
   *         1. 删除其整个向前链；--暂时先保留向前链
   *         2. 把相关向后链设置为无效；
   * 适用模块：组织归属降级
   * 注:如果是部降级,则育成级别需要设置为部级别
   */
  public boolean setDemoteRelationShipByRelPer(String tAgentCode){
    Reflections tReflections = new Reflections();
    //降级需要处理其本身级别及其向下级别的育成关系,主要是切断回算标记
    LARelationSet sCurrLARelationSet = new LARelationSet();//记录当前人的向前育成链
    //tongmeng 2006-11-1 modify
    //降级归属不管其前后育成关系有效无效，都做备份
    //this.setBefRelation("1");
    this.setBefRelation("");
    sCurrLARelationSet.set(this.getBefRelation());
    this.mBefLARelationSet.clear();
    //记录需要删除的向前育成链
    this.mDeleteLARelationSet.add(sCurrLARelationSet);
    //处理当前育成级别的向前育成链
    for(int i=1;i<=sCurrLARelationSet.size();i++)
    {
      LARelationSchema tempLARelationSchema = new LARelationSchema();
      tempLARelationSchema.setSchema(sCurrLARelationSet.get(i));
      //备份
      LARelationBSchema sLARelationBSchema = new LARelationBSchema();
      tReflections.transFields(sLARelationBSchema,tempLARelationSchema);
      sLARelationBSchema.setMakeDate2(tempLARelationSchema.getMakeDate());
      sLARelationBSchema.setMakeTime2(tempLARelationSchema.getMakeTime());
      sLARelationBSchema.setModifyDate2(tempLARelationSchema.getModifyDate());
      sLARelationBSchema.setModifyTime2(tempLARelationSchema.getModifyTime());
      sLARelationBSchema.setOperator2(tempLARelationSchema.getOperator());
      sLARelationBSchema.setMakeDate(this.mCurrentDate);
      sLARelationBSchema.setMakeTime(this.mCurrentTime);
      sLARelationBSchema.setModifyDate(this.mCurrentDate);
      sLARelationBSchema.setModifyTime(this.mCurrentTime);
      sLARelationBSchema.setOperator(this.mOperator);
      sLARelationBSchema.setEdorNo(this.mEdorNo);
      sLARelationBSchema.setEdorType(this.mEdorType);
      sLARelationBSchema.setIndexCalNo(this.mIndexCalNo);
      this.mAfterLARelationBSet.add(sLARelationBSchema);
      //切断育成关系,切断回算标记
      tempLARelationSchema.setRearFlag("0");
      //tongmeng 2006-11-02 modify
      //对于离职程序，不切断回算关系
      if(!this.mEdorType.equals("08"))
      {
        tempLARelationSchema.setReCalFlag("0");
      }
      //tongmeng 2006-08-21 add
      //增加育成止期
      tempLARelationSchema.setEndDate(this.mEndDate);
      tempLARelationSchema.setModifyDate(this.mCurrentDate);
      tempLARelationSchema.setModifyTime(this.mCurrentTime);
      tempLARelationSchema.setOperator(this.mOperator);
      this.mBefLARelationSet.add(tempLARelationSchema);
    }
    //处理当前育成级别的向后育成链
    //查询所有有效向后育成链
    //tongmeng 2006-11-1 modify
    //降级归属不管其前后育成关系有效无效，都做备份
    //this.setAfterRelation("1");
    this.setAfterRelation("");
    LARelationSet sCurrAftersLARelationSet = new LARelationSet();//记录当前人的向后育成链
    sCurrAftersLARelationSet.add(this.mAfterLARelationSet);
    this.mDeleteLARelationSet.add(this.mAfterLARelationSet);
    for(int i=1;i<=sCurrAftersLARelationSet.size();i++)
    {
      LARelationSchema sLARelationSchema = new LARelationSchema();
      sLARelationSchema.setSchema(sCurrAftersLARelationSet.get(i));
      //备份
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
      //把后链有效改为无效
      sLARelationSchema.setRearFlag("0");
      //回算标志置为无效
      //tongmeng 2006-11-02 modify
      //对于离职程序，不切断回算关系
      if(!this.mEdorType.equals("08"))
      {
        sLARelationSchema.setReCalFlag("0");
      }
      //tongmeng 2006-08-21 add
      //增加育成止期
      sLARelationSchema.setEndDate(this.mEndDate);
      sLARelationSchema.setModifyDate(this.mCurrentDate);
      sLARelationSchema.setModifyTime(this.mCurrentTime);
      sLARelationSchema.setOperator(this.mOperator);
      this.mBefLARelationSet.add(sLARelationSchema);
    }
    return true;
  }
  /**
   * 函数功能：当前处理人员降级，指定一个育成人，处理其相关育成关系：
   *         1. 删除其整个向前链；
   *         2. 把相关向后链设置为无效；
   * 适用模块：组织归属降级
   */
  public boolean setDemoteRelationShipByRelPer(String tAgentCode,Connection conn ){
    LARelationSet tLARelationSet = new LARelationSet();
    LARelationDBSet tLARelationDBSet = new LARelationDBSet(conn);
    LARelationBDBSet tLARelationBDBSet = new LARelationBDBSet(conn);
    if(!this.setDemoteRelationShipByRelPer(tAgentCode))
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
  /**
   *  函数功能：通过增员功能或初始、行政信息修改功能修改育成人，需考虑如下几中情况：
   *          1. 当前处理人员没有向后链，只需修改此人向前链；
   *          2. 当前处理人员有向后链，除了修改本人向前链还需修改其向后链人员的向前链；
   *  适用模块：增员功能或初始、行政信息修改功能
   *
   *  注意:查询时,查询前链取所有存在的记录,查询后链只取有效的记录
   */
  public boolean setRelationShipByRelPerXZ(String tAgentCode){
    Reflections tReflections = new Reflections();
    //记录mAgentCode的直接育成人
    DesignRearRelationship tDesignRearRelationship = new DesignRearRelationship();
    tDesignRearRelationship.setAgentCode(tAgentCode,this.mRelaLevel);
    //查询向前育成链,查询所有育成链
    if(!tDesignRearRelationship.setBefRelation("")){
      logger.debug("查询向前育成链失败");
    }
    //记录直接育成人的向前育成链
    LARelationSet tBefLARelationSet = new LARelationSet();
    tBefLARelationSet.set(tDesignRearRelationship.getBefRelation());
    //查询需要删除的当前被育成人的向前育成链
    LARelationSet tDelBefLARelationSet = new LARelationSet();
    //查询当前被育成人的向前育成链
    LARelationSet tCurrBefLARelationSet = new LARelationSet();
    if(!this.setBefRelation("")){
      logger.debug("查询当前处理人员向前育成链失败");
    }
    tDelBefLARelationSet.set(this.getBefRelation());
    tCurrBefLARelationSet.set(this.getBefRelation());
    //把当前被育成人的向前育成链清空
    this.mBefLARelationSet.clear();
    if(tDelBefLARelationSet.size()>0)
    {
      logger.debug("开始备份当前育成人的向前链");
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
    logger.debug("开始处理当前人的向前育成链");
    for(int i=0;i<=tBefLARelationSet.size();i++)
    {
      LARelationSchema sLARelationSchema = new LARelationSchema();
      if(i==0)
      {
        //处理直接育成关系,新生成
        logger.debug("开始处理直接育成关系");
        sLARelationSchema.setAgentCode(this.mAgentCode);
        sLARelationSchema.setRelaAgentCode(tAgentCode);
        sLARelationSchema.setRelaLevel(this.mRelaLevel);
        sLARelationSchema.setRelaGens(1);
        sLARelationSchema.setRelaType("02");
        sLARelationSchema.setAgentGroup(this.mRelaAgentGroup);
        String sSQL = "select makedate,maketime from larelation where agentcode='"+"?agentcode?"+"' "
                    + " and relalevel='"+"?relalevel?"+"' and relagens=1 and relatype='02' ";
        SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
        sqlbv5.sql(sSQL);
        sqlbv5.put("relalevel", this.mRelaLevel);
        sqlbv5.put("agentcode", this.mAgentCode);
        SSRS sSSRS = new SSRS();
        ExeSQL sExeSQL = new ExeSQL();
        sSSRS =  sExeSQL.execSQL(sqlbv5);
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
        //处理间接育成关系,保持当前处理人员的育成人的向前链关系不变,只把育成代数增加1代
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
    //记录当前人的向后育成链
    LARelationSet tCurrAfterLARelationSet = new LARelationSet();
    //暂存当前人的向前育成链
    LARelationSet tTempLARelationSet = new LARelationSet();
    //查询当前人的向后育成链,查询所有有效和无效的
    this.setAfterRelation("");
    tCurrAfterLARelationSet.set(this.getAfterRelation());
    tTempLARelationSet.set(this.mBefLARelationSet);
    for(int i=1;i<=tTempLARelationSet.size();i++)
    {
      /////////////////////////////////???????????????????????????????
      //有问题,还没有修改!!!!!!!!!!!!!!!!!!!!!!!!!
      for(int j=1;j<=tCurrAfterLARelationSet.size();j++)
      {
        LARelationSchema tTempLARelationSchema = new LARelationSchema();
        tTempLARelationSchema.setSchema(tCurrAfterLARelationSet.get(j));
        tTempLARelationSchema.setRelaGens(tCurrAfterLARelationSet.get(j).getRelaGens()+tTempLARelationSet.get(i).getRelaGens());
        if(tTempLARelationSet.get(i).getRearFlag().equals("0")||
           tCurrAfterLARelationSet.get(j).getRearFlag().equals("0"))
          tTempLARelationSchema.setRearFlag("0");
        //备份
        LARelationDB tTempLARelationDB = new LARelationDB();
        LARelationSet sTempLARelationSet = new LARelationSet();
        tTempLARelationDB.setSchema(tTempLARelationSchema);
        tTempLARelationDB.setRelaAgentCode("");
        String sSQL = "select * from LARelation where RelaType='02' and RelaLevel='"+"?RelaLevel?"+"' "
                    + " and RelaGens="+"?RelaGens?"+" and AgentCode='"+"?AgentCode?"+"'";
        SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
        sqlbv6.sql(sSQL);
        sqlbv6.put("RelaLevel", tTempLARelationSchema.getRelaLevel());
        sqlbv6.put("RelaGens", tTempLARelationSchema.getRelaGens());
        sqlbv6.put("AgentCode", tTempLARelationSchema.getAgentCode());
        sTempLARelationSet = tTempLARelationDB.executeQuery(sqlbv6);
        if(sTempLARelationSet.size()>0)
        {
          //把需要删除的数据添加到集合中
          this.mDeleteLARelationSet.add(sTempLARelationSet);
        }
        for(int m=1;m<=sTempLARelationSet.size();m++)
        {
          LARelationBSchema sLARelationBSchema = new LARelationBSchema();
          LARelationSchema sLARelationSchema = new LARelationSchema();
          sLARelationSchema = sTempLARelationSet.get(m).getSchema();
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
//        tTempLARelationSchema.setstartDate(this.mStartDate); //育成时间不做修改
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
   *  函数功能：通过增员功能或初始、行政信息修改功能修改育成人，需考虑如下几中情况：
   *          1. 当前处理人员没有向后链，那只需修改此人向前链；
   *          2. 当前处理人员有向后链，除了修改本人向前链还需修改其向后链人员的向前链；
   *  适用模块：增员功能或初始、行政信息修改功能
   *  此功能可以直接在当前Class中提交数据库
   */
  public boolean setRelationShipByRelPerXZ(String tAgentCode,Connection conn){
    LARelationSet tLARelationSet = new LARelationSet();
    LARelationDBSet tLARelationDBSet = new LARelationDBSet(conn);
    LARelationBDBSet tLARelationBDBSet = new LARelationBDBSet(conn);
    if(!this.setRelationShipByRelPerXZ(tAgentCode))
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
  //tongmeng 2006-10-27 add
  //离职恢复恢复育成关系
  /**
   *
   * @return
   */
  public boolean setDimissionReRelation()
  {
    Reflections tReflections = new Reflections();
    LARelationSet tLARelationSet = new LARelationSet();
    LARelationSet tDealLARelationSet = new LARelationSet();
    LARelationSet tDelLARelationSet = new LARelationSet();
    LARelationDB tLARelationDB = new LARelationDB();
    //开始处理向前链
    logger.debug("开始处理向前链...");
    String tSQL = "select * from LARelation where RelaType='02' and RelaLevel='"+"?RelaLevel?"+"' "
         + " and AgentCode='"+"?AgentCode?"+"' ";
    logger.debug("处理向前链SQL1:"+tSQL);
    SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
    sqlbv7.sql(tSQL);
    sqlbv7.put("RelaLevel", this.mRelaLevel);
    sqlbv7.put("AgentCode", this.mAgentCode);
    tLARelationSet = tLARelationDB.executeQuery(sqlbv7);
    if(tLARelationSet.size()>0)
    {
      this.mDeleteLARelationSet.add(tLARelationSet);
      tDealLARelationSet.add(tLARelationSet);
      for(int i=1;i<=tDealLARelationSet.size();i++)
      {
        Reflections tRf = new Reflections();
        //开始备份需要修改的数据
        LARelationSchema sLARelationSchema = new LARelationSchema();
        LARelationBSchema sLARelationBSchema = new LARelationBSchema();
        sLARelationSchema.setSchema(tDealLARelationSet.get(i));
        tRf.transFields(sLARelationBSchema,sLARelationSchema);
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
      for(int i=1;i<=tDealLARelationSet.size();i++)
      {
        //开始修改需要数据,恢复向前链的育成关系
        LARelationSchema sLARelationSchema = new LARelationSchema();
        sLARelationSchema.setSchema(tDealLARelationSet.get(i));
        sLARelationSchema.setModifyDate(this.mCurrentDate);
        sLARelationSchema.setModifyTime(this.mCurrentTime);
        sLARelationSchema.setOperator(this.mOperator);
        //判断是否该恢复育成关系
        String tempSQL = "";
        String tRelaAgentCode = sLARelationSchema.getRelaAgentCode();
        if(tRelaAgentCode==null||tRelaAgentCode.equals(""))
        {
          sLARelationSchema.setRearFlag("1");
        }
        else
        {
         String tComparGrade = "";
         if(this.mRelaLevel.equals("01"))
         {
           tComparGrade = "A05";
         }
         else if(this.mRelaLevel.equals("02"))
         {
           tComparGrade = "A07";
         }
         else if(this.mRelaLevel.equals("03"))
         {
           tComparGrade = "A08";
         }
         else if(this.mRelaLevel.equals("04"))
         {
           tComparGrade = "A09";
         }
         tempSQL = "select a.agentstate,b.agentgrade from laagent a,latree b where a.agentcode=b.agentcode and a.agentcode='"+"?tRelaAgentCode?"+"' ";
         SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
         sqlbv8.sql(tempSQL);
         sqlbv8.put("tRelaAgentCode", tRelaAgentCode);
         ExeSQL tExeSQL = new ExeSQL();
         SSRS tSSRS = new SSRS();
         tSSRS = tExeSQL.execSQL(sqlbv8);
         if(tSSRS.getMaxRow()<=0)
         {
           sLARelationSchema.setRearFlag("0");
         }
         else
         {
           if(tSSRS.GetText(1,1).compareTo("02")>0||tSSRS.GetText(1,2).compareTo(tComparGrade)<0)
           {
             sLARelationSchema.setRearFlag("0");
           }
           else
           {
             sLARelationSchema.setRearFlag("1");
           }
         }
        }
        this.mBefLARelationSet.add(sLARelationSchema);
      }
    }
    logger.debug("开始处理向后链...");
    //开始处理向后链
    tSQL = "select * from LARelation where RelaType='02' and RelaLevel='"+"?RelaLevel?"+"' "
         + " and RelaAgentCode='"+"?RelaAgentCode?"+"' ";
    logger.debug("处理向后链SQL2:"+tSQL);
    SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
    sqlbv9.sql(tSQL);
    sqlbv9.put("RelaLevel", this.mRelaLevel);
    sqlbv9.put("RelaAgentCode", this.mAgentCode);
    LARelationDB tAfLARelationDB = new LARelationDB();
    LARelationSet tAfLARelationSet = new LARelationSet();
    LARelationSet tAfDealLARelationSet = new LARelationSet();
    tAfLARelationSet = tAfLARelationDB.executeQuery(sqlbv9);
    if(tAfLARelationSet.size()>0)
    {
      //保存需要删除的数据
      this.mDeleteLARelationSet.add(tAfLARelationSet);
      tAfDealLARelationSet.add(tAfLARelationSet);
      for(int i=1;i<=tAfDealLARelationSet.size();i++)
      {
        //开始备份需要处理的数据
        Reflections tRf = new Reflections();
        //开始备份需要修改的数据
        LARelationSchema sLARelationSchema = new LARelationSchema();
        LARelationBSchema sLARelationBSchema = new LARelationBSchema();
        sLARelationSchema.setSchema(tAfDealLARelationSet.get(i));
        tRf.transFields(sLARelationBSchema,sLARelationSchema);
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
      logger.debug("tAfDealLARelationSet.size():"+tAfDealLARelationSet.size());
      for(int i=1;i<=tAfDealLARelationSet.size();i++)
      {
        logger.debug("i:"+i);
        //开始修改需要处理的数据
        LARelationSchema sLARelationSchema = new LARelationSchema();
        sLARelationSchema.setSchema(tAfDealLARelationSet.get(i));
        sLARelationSchema.setModifyDate(this.mCurrentDate);
        sLARelationSchema.setModifyTime(this.mCurrentTime);
        sLARelationSchema.setOperator(this.mOperator);
        //判断是否该恢复育成关系
        String tempSQL = "";
        String tAgentCode = sLARelationSchema.getAgentCode();
        if(tAgentCode==null||tAgentCode.equals(""))
        {
          sLARelationSchema.setRearFlag("1");
        }
        else
        {
          String tComparGrade = "";
          if(this.mRelaLevel.equals("01"))
          {
            tComparGrade = "A05";
          }
          else if(this.mRelaLevel.equals("02"))
          {
            tComparGrade = "A07";
          }
          else if(this.mRelaLevel.equals("03"))
          {
            tComparGrade = "A08";
          }
          else if(this.mRelaLevel.equals("04"))
          {
            tComparGrade = "A09";
          }
          tempSQL = "select a.agentstate,b.agentgrade from laagent a,latree b where a.agentcode=b.agentcode and a.agentcode='"+"?tAgentCode?"+"' ";
          SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
          sqlbv10.sql(tSQL);
          sqlbv10.put("tAgentCode", tAgentCode);
          ExeSQL tExeSQL = new ExeSQL();
          SSRS tSSRS = new SSRS();
          tSSRS = tExeSQL.execSQL(sqlbv10);
          if(tSSRS.getMaxRow()<=0)
          {
            sLARelationSchema.setRearFlag("0");
          }
          else
          {
            if(tSSRS.GetText(1,1).compareTo("02")>0||tSSRS.GetText(1,2).compareTo(tComparGrade)<0)
            {
              sLARelationSchema.setRearFlag("0");
            }
            else
            {
              sLARelationSchema.setRearFlag("1");
            }
          }
        }
        this.mBefLARelationSet.add(sLARelationSchema);
      }
    }
    return true;
  }
  //tongmeng 2006-10-27 add
  //离职恢复恢复育成关系
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
      logger.debug("离职恢复恢复育成关系失败");
      dealError("updateLATree","离职恢复恢复育成关系失败!");
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
      logger.debug("tLARelationSet.size()["+tLARelationSet.size()+"]");
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
