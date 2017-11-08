/*
* <p>ClassName: ALAAgentBL </p>
* <p>Description: ALAAgentBL类文件 </p>
* <p>Copyright: Copyright (c) 2002</p>
* <p>Company: sinosoft </p>
* @Database: 销售管理
* @CreateDate：2003-01-09
 */
package com.sinosoft.lis.naagent;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
//import com.sinosoft.lis.agent.*;
import com.sinosoft.lis.design.*;
public class NAALAAgentBL{
private static Logger logger = Logger.getLogger(NAALAAgentBL.class);
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
  /** 业务处理相关变量 */
  private LAAssuMoneySchema  mLLAAssuMoneySchema=new LAAssuMoneySchema();
  private LAAddControlDB mLAADDCONTROLDB=new LAAddControlDB();
  private LAAddControlSet mLAADDCONTROLSET=new LAAddControlSet(); 
  private LAAgentModCtlDB mLAAgentModCtlDB=new LAAgentModCtlDB();
  private LAAgentModCtlSet mLAAgentModCtlSET=new LAAgentModCtlSet(); 

  private LAAssuMoneyDB  mLLAAssuMoneyDB=new LAAssuMoneyDB();
  private LAAgentSchema mLAAgentSchema = new LAAgentSchema();
  private LAAgentBSchema mLAAgentBSchema = new LAAgentBSchema();
  private LATreeSchema mLATreeSchema = new LATreeSchema();
  private LATreeBSchema mLATreeBSchema = new LATreeBSchema();
  private LATreeAccessorySet mLATreeAccessorySet = new LATreeAccessorySet();
  private LABranchGroupSchema mLABranchGroupSchema = new LABranchGroupSchema();
  private LABranchGroupSet mLABranchGroupSet = new LABranchGroupSet();
  private LAWarrantorSet mLAWarrantorSet = new LAWarrantorSet();
  private LAAgentSet mLAAgentSet = new LAAgentSet();
  private LARelationSet mAddLARelationSet = new LARelationSet();
  private LARelationSet mDeleteLARelationSet = new LARelationSet();
  private LARelationBSet mBakLARelationBSet = new LARelationBSet();
  private LARelationSet mRelaLARelationSet = new LARelationSet();
  private TransferData mTransferData=new TransferData();
  public NAALAAgentBL() {
  }

  public static void main(String[] args) {
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
     if (!getInputData(cInputData))
      return false;
    //校验该代理人是否在黑名单中存在
    //校验该代理人是否交过押金
    if (!checkData())
      return false;

     //add by tongmeng 2005-06-23
    //增加防止连续点击造成数据库中有一个代理人身份证号对应两个代理人编码的问题出现
    if (this.mOperate.equals("INSERT||MAIN"))
    {
      String tNoType="ZY";
      String tNoLimit = this.mLAAgentSchema.getIDNo();
      if(!PubCheckLock.CheckInsert(tNoType,tNoLimit))
      {
        CError tError = new CError();
        tError.moduleName = "ALAAgentBL";
        tError.functionName = "dealData";
        tError.errorMessage = "系统忙，请稍后重试!";
        this.mErrors.addOneError(tError);
        return false;
      }
    }

    //进行业务处理
    if (!dealData()) {
      //add by tongmeng 2005-08-11
      //防止连续点击
      if (this.mOperate.equals("INSERT||MAIN")) {
        if(!PubCheckLock.CheckDelete("ZY",this.mLAAgentSchema.getIDNo()))
        {
          CError tError = new CError();
          tError.moduleName = "ALAAgentBL";
          tError.functionName = "submitData";
          tError.errorMessage = "系统忙，请稍后重试!";
          this.mErrors.addOneError(tError);
          return false;
        }
      }
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "ALAAgentBL";
      tError.functionName = "submitData";
      tError.errorMessage = "数据处理失败ALAAgentBL-->dealData!";
      this.mErrors.addOneError(tError);
      return false;
    }
    logger.debug("over dealData");
    //准备往后台的数据
    if (!prepareOutputData())
      return false;
    if (this.mOperate.equals("QUERY||MAIN")) {
      this.submitquery();
    }
    else {
      logger.debug("Start ALAAgentBL Submit...");
      NAALAAgentBLS tALAAgentBLS = new NAALAAgentBLS();
      tALAAgentBLS.submitData(mInputData, cOperate);
      logger.debug("End ALAAgentBL Submit...");
      //如果有需要处理的错误，则返回
      if (tALAAgentBLS.mErrors.needDealError()) {
      //add by tongmeng 2005-08-11
      //防止连续点击
        if (this.mOperate.equals("INSERT||MAIN")) {
          if(!PubCheckLock.CheckDelete("ZY",this.mLAAgentSchema.getIDNo()))
          {
            CError tError = new CError();
            tError.moduleName = "ALAAgentBL";
            tError.functionName = "submitData";
            tError.errorMessage = "系统忙，请稍后重试!";
            this.mErrors.addOneError(tError);
            return false;
          }
        }
        // @@错误处理
        this.mErrors.copyAllErrors(tALAAgentBLS.mErrors);
        CError tError = new CError();
        tError.moduleName = "ALAAgentBL";
        tError.functionName = "submitDat";
        tError.errorMessage = "数据提交失败!";
        this.mErrors.addOneError(tError);
        return false;
      }
    if (this.mOperate.equals("INSERT||MAIN")) {
      if(!PubCheckLock.CheckDelete("ZY",this.mLAAgentSchema.getIDNo()))
      {
        CError tError = new CError();
        tError.moduleName = "ALAAgentBL";
        tError.functionName = "submitData";
        tError.errorMessage = "系统忙，请稍后重试!";
        this.mErrors.addOneError(tError);
        return false;
      }
    }
      this.mResult.add(this.mLAAgentSchema);
    }
    mInputData = null;
    return true;
  }

  /**
   * 根据前面的输入数据，进行BL逻辑处理
   * 如果在处理过程中出错，则返回false,否则返回true
   */
  private boolean dealData() {
    boolean tReturn = true;
    String tAgentCode = "";
    String tAgentGroup = "";
    String tZSGroupCode = "";
    String tSQL = "";
    ExeSQL tExeSQL;
    logger.debug("mOperate:"+mOperate);

    if (this.mOperate.equals("INSERT||MAIN")) {

      //生成代理人编码(分公司＋顺序号)
      String tPrefix = this.mLAAgentSchema.getManageCom().substring(0, 4);
      tAgentCode = tPrefix + PubFun1.CreateMaxNo("AgentCode" + tPrefix, 6);
      //确定代理人系列
      String tAgentGradeSeries = this.mLATreeSchema.getAgentGrade().trim();
      tAgentGradeSeries = getAgentSeries(tAgentGradeSeries);
      if (tAgentGradeSeries == null)
        return false;
      //确定销售机构AgentGroup
      tZSGroupCode = this.mLAAgentSchema.getBranchCode().trim(); //直辖组的隐式代码
      tAgentGroup = this.mLAAgentSchema.getAgentGroup().trim(); //显示代码
      String tAgentGrade = this.mLATreeSchema.getAgentGrade().trim();
      logger.debug(tAgentGrade + " " + tAgentGroup);
      tAgentGroup = getAgentGradevsGroup(tZSGroupCode, tAgentGrade, tAgentGroup);
      if (tAgentGroup == null)
        return false;
      //tongmeng 2006-10-08 add
      //增加二次增员处理
      String sAgentState = this.mLAAgentSchema.getAgentState();
      logger.debug("sAgentState:"+sAgentState);
      if(sAgentState!=null&&sAgentState.equals("02"))
      {
        logger.debug("二次增员,开始备份数据...");
        String tNewAgent = tAgentCode;
        String tOldAgent = this.mLAAgentSchema.getAgentCode();
        String tEdorNo=PubFun1.CreateMaxNo("EdorNo",20);
        //备份代理人信息
        if (!prepareCopyAgent(tOldAgent,tEdorNo))
        {
          return false;
        }
        this.mLAAgentBSchema.setNewAgentCode(tNewAgent);
        this.mLAAgentBSchema.setAgentKind("01"); //客户经理
        this.mLAAgentBSchema.setMakeDate(currentDate);
        this.mLAAgentBSchema.setMakeTime(currentTime);
        this.mLAAgentBSchema.setModifyDate(currentDate);
        this.mLAAgentBSchema.setModifyTime(currentTime);
        this.mLAAgentBSchema.setOperator(mGlobalInput.Operator);
        LAAgentDB sLAAgentDB = new LAAgentDB();
        sLAAgentDB.setAgentCode(tOldAgent);
        if (!sLAAgentDB.getInfo())
        {
          CError tError = new CError();
          tError.moduleName = "ALAAgentBL";
          tError.functionName = "submitDat";
          tError.errorMessage ="查询代理人原信息失败!";
          this.mErrors .addOneError(tError);
          return false;
        }
        //modify by jiaqiangli 2006-12-07 二次增员的转正日期设置
        //二次增员保持原有专正日期不变
        String tInDueFormDate = sLAAgentDB.getInDueFormDate();
        logger.debug("二次增员原转正日期["+tInDueFormDate+"]");
        logger.debug("mLAAgentSchema.getEmployDate()["+mLAAgentSchema.getEmployDate()+"]");
        if (tAgentGrade.compareTo("A01") > 0) {
          if (tInDueFormDate==null||tInDueFormDate.equals("")) {
            this.mLAAgentSchema.setInDueFormDate(currentDate);
          }
          else {
            this.mLAAgentSchema.setInDueFormDate(tInDueFormDate);
          }
        }
        //modify by jiaqiangli 2006-12-07 二次增员的转正日期设置
        //备份离职前的行政信息
        if (!prepareCopyTree(tOldAgent,tEdorNo))
        {
          return false;
        }
        this.mLATreeBSchema.setMakeDate(currentDate);
        this.mLATreeBSchema.setMakeTime(currentTime);
        this.mLATreeBSchema.setModifyDate(currentDate);
        this.mLATreeBSchema.setModifyTime(currentTime);
        this.mLATreeBSchema.setOperator(mGlobalInput.Operator);
      }
      
      //add by renhailong 2008-07-22
      this.mLLAAssuMoneyDB.setAgentName(this.mLAAgentSchema.getName());
      this.mLLAAssuMoneyDB.setIDType(this.mLAAgentSchema.getIDType());
      this.mLLAAssuMoneyDB.setIDNo(this.mLAAgentSchema.getIDNo());
    //bug add by renhl 2008-08-26;
      //必须加branchtype='1'防止跨渠道的情况.
      this.mLLAAssuMoneyDB.setBranchType("1");
      this.mLLAAssuMoneyDB.setAssuCheckState("0");
      mLLAAssuMoneySchema = this.mLLAAssuMoneyDB.query().get(1);
      mLLAAssuMoneySchema.setAgentCode(tAgentCode);
      mLLAAssuMoneySchema.setAssuCheckState("1");
      mLLAAssuMoneySchema.setAssuCheckDate(currentDate);
      //因财务的要求不需要改变operator
     //mLLAAssuMoneySchema.setOperator(mGlobalInput.Operator);
      mLLAAssuMoneySchema.setModifyDate(currentDate);
      mLLAAssuMoneySchema.setModifyTime(currentTime);
      
      if(sAgentState!=null&&sAgentState.equals("01")){  
      
      this.mLAADDCONTROLDB.setZJno(mLAAgentSchema.getIDNo());
      this.mLAADDCONTROLDB.setState("0");
      LAAddControlSet tLAADDCONTROLSET=new LAAddControlSet();
      
      tLAADDCONTROLSET =mLAADDCONTROLDB.query();
      LAAddControlSchema mLAADDCONTROLSchema=new LAAddControlSchema(); 
      for (int i=1;i<=tLAADDCONTROLSET.size();i++)
      {
    	  mLAADDCONTROLSchema=tLAADDCONTROLSET.get(i);
    	  mLAADDCONTROLSchema.setState("1");
    	  mLAADDCONTROLSchema.setModifyDate(currentDate);
    	  mLAADDCONTROLSchema.setModifyTime(currentTime);
    	  mLAADDCONTROLSchema.setOperator(mGlobalInput.Operator);
    	  this.mLAADDCONTROLSET.add(mLAADDCONTROLSchema);
    	  
    	  
    }
      }
      

      

      //代理人基本信息
      this.mLAAgentSchema.setAgentCode(tAgentCode);
      this.mLAAgentSchema.setAgentGroup(tAgentGroup);
      this.mLAAgentSchema.setAgentKind("01");
      this.mLAAgentSchema.setEmployDate(currentDate); //入司日期取系统日期
      //tongmeng 2006-10-08 modify
      //二次增员保证原有信息不变
      if(sAgentState!=null&&sAgentState.equals("01"))
      {
         if (tAgentGrade.compareTo("A01") > 0)
           this.mLAAgentSchema.setInDueFormDate(mLAAgentSchema.getEmployDate());
      }
      this.mLAAgentSchema.setOperator(mGlobalInput.Operator);
      this.mLAAgentSchema.setMakeDate(currentDate);
      this.mLAAgentSchema.setMakeTime(currentTime);
      this.mLAAgentSchema.setModifyDate(currentDate);
      this.mLAAgentSchema.setModifyTime(currentTime);
      //行政信息
      this.mLATreeSchema.setAgentCode(tAgentCode);
      this.mLATreeSchema.setAgentGroup(tAgentGroup);
      String upAgent = this.getUpAgent(tAgentGroup, tAgentGradeSeries);
//      if (upAgent == null||upAgent.trim() .equals("") ) {
//        CError tError = new CError();
//        tError.moduleName = "ALAAgentBL";
//        tError.functionName = "submitDat";
//        tError.errorMessage = "查询"+tAgentCode+"的上级代理人信息失败，请先维护上级代理人信息!";
//        this.mErrors.addOneError(tError);
//        return false;
//
//      }
      this.mLATreeSchema.setUpAgent(upAgent);
      this.mLATreeSchema.setAgentSeries(tAgentGradeSeries);
      this.mLATreeSchema.setAssessType("0"); //正常
      this.mLATreeSchema.setState("0");
      //by jiaqiangli 2006-08-03 没有推荐人时也要增加一条记录
//      if ( (this.mLATreeSchema.getIntroAgency() != null) &&
//           (!this.mLATreeSchema.getIntroAgency().equals(""))) {
        this.mLATreeSchema.setIntroBreakFlag("0");
        this.mLATreeSchema.setIntroCommStart(this.mLAAgentSchema.getEmployDate());
        //tongmeng 2006-04-11 add
        //新版基本法修改,增加增员关系
        LARelationSet tLARelationSet = new LARelationSet();
        AddRelationship tAddRelationship = new AddRelationship();
        tAddRelationship.setAgentCode(tAgentCode);
        tAddRelationship.setCurrentDate(this.currentDate);
        tAddRelationship.setCurrentTime(this.currentTime);
        tAddRelationship.setOperator(this.mGlobalInput.Operator);
        tAddRelationship.setStartDate(this.mLAAgentSchema.getEmployDate());
        tAddRelationship.setAddAgentGroup(tAgentGroup);
        if(!tAddRelationship.setRelationship(this.mLATreeSchema.getIntroAgency()))
        {
          CError tError = new CError();
          tError.moduleName = "ALAAgentBL";
          tError.functionName = "dealdate";
          tError.errorMessage = "处理该代理人增员关系失败!";
          this.mErrors.addOneError(tError);
          return false;
        }
        else
        {
          //取出增员关系集合
          tLARelationSet.set(tAddRelationship.getBefRelation());
          this.mAddLARelationSet.add(tLARelationSet);
        }
//      }
      this.mLATreeSchema.setStartDate(this.mLAAgentSchema.getEmployDate());
      this.mLATreeSchema.setAstartDate(this.mLAAgentSchema.getEmployDate());
      this.mLATreeSchema.setMakeDate(currentDate);
      this.mLATreeSchema.setMakeTime(currentTime);
      this.mLATreeSchema.setModifyDate(currentDate);
      this.mLATreeSchema.setModifyTime(currentTime);
      this.mLATreeSchema.setOperator(mGlobalInput.Operator);
      //行政附属信息
      if (tAgentGrade.compareTo("A04") >= 0) {
        //tongmeng 2006-04-11 add
        //增加育成关系
        LARelationSet dLARelationSet = new LARelationSet();
        String tRelaLevel = getRelaLevelByAgentGrade(this.mLATreeSchema.getAgentGrade());
        DesignRearRelationship tDesignRearRelationship = new DesignRearRelationship();
        tDesignRearRelationship.setAgentCode(tAgentCode,tRelaLevel);
        tDesignRearRelationship.setCurrentDate(this.currentDate);
        tDesignRearRelationship.setCurrentTime(this.currentTime);
        tDesignRearRelationship.setOperator(this.mGlobalInput.Operator);
        tDesignRearRelationship.setStartDate(this.mLATreeSchema.getStartDate());
        tDesignRearRelationship.setRelaAgentGroup(tAgentGroup);
        if(!tDesignRearRelationship.setRelationShipByRelPerXZ(this.mLATreeSchema.getEduManager()))
        {
          CError tError = new CError();
          tError.moduleName = "ALAAgentBL";
          tError.functionName = "dealdate";
          tError.errorMessage = "处理该代理人育成关系失败!";
          this.mErrors.addOneError(tError);
          return false;
        }
        else
        {
          dLARelationSet.set(tDesignRearRelationship.getBefRelation());
          this.mRelaLARelationSet.add(dLARelationSet);
        }
        //此处借用原育成关系表去循环处理各级育成关系
         LATreeAccessorySchema tLATreeAccessorySchema = new
            LATreeAccessorySchema();
        tLATreeAccessorySchema.setAgentCode(tAgentCode);
        tLATreeAccessorySchema.setAgentGrade(this.mLATreeSchema.getAgentGrade());
        tLATreeAccessorySchema.setAgentGroup(tAgentGroup);
        tLATreeAccessorySchema.setManageCom(this.mLATreeSchema.getManageCom());
        if ( (this.mLATreeSchema.getEduManager() != null) &&
             (!this.mLATreeSchema.getEduManager().equals(""))) {
          tLATreeAccessorySchema.setRearAgentCode(this.mLATreeSchema.
              getEduManager());
        }
        tLATreeAccessorySchema.setstartdate(this.mLAAgentSchema.getEmployDate());
        String tAscript = this.mLATreeSchema.getAscriptSeries().trim();
        prepareTreeAccessory(tAscript, tLATreeAccessorySchema, tAgentGrade,"");
      }
      //设置销售机构管理人员信息
      if (this.mIsManager.equals("true")) {
        if (!setZSGroupManager(tZSGroupCode, tAgentGroup, tAgentCode,
                               this.mLAAgentSchema.getName(), tAgentGrade))
          return false;
      }
    }
    if (this.mOperate.equals("UPDATE||ALL")) {
      String tOldAgentGroup = "", tOldAgentGrade = "";
      String tNewAgentGrade = "", tOldMinGroup = "";
      tAgentCode = this.mLAAgentSchema.getAgentCode().trim();
      String tEdorNo = PubFun1.CreateMaxNo("EdorNo", 20);
      //查询出AgentGroup和AgentGrade，判断是否修改
      LAAgentDB tLAAgentDB = new LAAgentDB();
      tLAAgentDB.setAgentCode(tAgentCode);
      if (!tLAAgentDB.getInfo()) {
    	  
        // @@错误处理
        this.mErrors.copyAllErrors(tLAAgentDB.mErrors);
        CError tError = new CError();
        tError.moduleName = "ALAAgentBL";
        tError.functionName = "submitDat";
        tError.errorMessage = "查询原代理人信息失败!";
        this.mErrors.addOneError(tError);
        return false;
      }
    //初始信息修改的核销.  
      this.mLAAgentModCtlDB.setAgentCode(mLAAgentSchema.getAgentCode());
      this.mLAAgentModCtlDB.setState("0");
      this.mLAAgentModCtlDB.setModType("初始信息");
      LAAgentModCtlSet tLAAgentModCtlSet=new LAAgentModCtlSet();
      
      tLAAgentModCtlSet =mLAAgentModCtlDB.query();
      LAAgentModCtlSchema mLAAgentModCtlSchema=new LAAgentModCtlSchema(); 
      for (int i=1;i<=tLAAgentModCtlSet.size();i++)
      {
    	  mLAAgentModCtlSchema=tLAAgentModCtlSet.get(i);
    	  mLAAgentModCtlSchema.setState("1");
    	  mLAAgentModCtlSchema.setModifyDate(currentDate);
    	  mLAAgentModCtlSchema.setModifyTime(currentTime);
    	  mLAAgentModCtlSchema.setOperator(mGlobalInput.Operator);
    	  this.mLAAgentModCtlSET.add(mLAAgentModCtlSchema);
    	  
    	  
    } 
      //add by tongmeng 2005-08-12
      //防止修改后的代理人的管理机构跨六位
     String CheckManagecom = "";
     CheckManagecom = tLAAgentDB.getManageCom().toString();
     if(!CheckManagecom.substring(0,6).equals(this.mLAAgentSchema.getManageCom().substring(0,6)))
     {
       logger.debug("跨6位调动，不允许");
       CError tError = new CError();
       tError.moduleName = "ALAAgentBL";
       tError.functionName = "submitDat";
       tError.errorMessage = "原管理机构与修改后管理机构不在同一个六位机构下!";
       this.mErrors.addOneError(tError);
      return false;
     }

      //备份原代理人信息
      Reflections tReflections = new Reflections();
      tReflections.transFields(this.mLAAgentBSchema, tLAAgentDB.getSchema());
      this.mLAAgentBSchema.setEdorNo(tEdorNo);
      this.mLAAgentBSchema.setEdorType("05");
      this.mLAAgentBSchema.setMakeDate(currentDate);
      this.mLAAgentBSchema.setMakeTime(currentTime);
      this.mLAAgentBSchema.setModifyDate(currentDate);
      this.mLAAgentBSchema.setModifyTime(currentTime);
      this.mLAAgentBSchema.setOperator(this.mGlobalInput.Operator);

      tOldMinGroup = tLAAgentDB.getBranchCode(); //组隐式代码
      tOldAgentGroup = tLAAgentDB.getAgentGroup(); //老的职级对应的机构
      LATreeDB tLATreeDB = new LATreeDB();
      tLATreeDB.setAgentCode(tAgentCode);
      if (!tLATreeDB.getInfo()) {
        // @@错误处理
        this.mErrors.copyAllErrors(tLATreeDB.mErrors);
        CError tError = new CError();
        tError.moduleName = "ALAAgentBL";
        tError.functionName = "dealData";
        tError.errorMessage = "查询原行政信息失败!";
        this.mErrors.addOneError(tError);
        return false;
      }
      //备份行政信息
      tReflections.transFields(this.mLATreeBSchema, tLATreeDB.getSchema());
      this.mLATreeBSchema.setAstartDate(tLATreeDB.getAstartDate());
      this.mLATreeBSchema.setEdorNO(tEdorNo);
      this.mLATreeBSchema.setRemoveType("05");
      this.mLATreeBSchema.setMakeDate2(this.mLATreeBSchema.getMakeDate());
      this.mLATreeBSchema.setMakeTime2(this.mLATreeBSchema.getMakeTime());
      this.mLATreeBSchema.setModifyDate2(this.mLATreeBSchema.getModifyDate());
      this.mLATreeBSchema.setModifyTime2(this.mLATreeBSchema.getModifyTime());
      this.mLATreeBSchema.setOperator2(this.mLATreeBSchema.getOperator());
      this.mLATreeBSchema.setMakeDate(currentDate);
      this.mLATreeBSchema.setMakeTime(currentTime);
      this.mLATreeBSchema.setModifyDate(currentDate);
      this.mLATreeBSchema.setModifyTime(currentTime);
      this.mLATreeBSchema.setOperator(this.mGlobalInput.Operator);

      tOldAgentGrade = tLATreeDB.getAgentGrade();
      tZSGroupCode = this.mLAAgentSchema.getBranchCode().trim(); //直辖组的隐式代码
      tNewAgentGrade = this.mLATreeSchema.getAgentGrade();
      tAgentGroup = this.mLAAgentSchema.getAgentGroup(); //显式代码
      if (tNewAgentGrade.compareTo("A06") < 0) //高级经理以下
        tAgentGroup = tZSGroupCode;
      else {
        tAgentGroup = getAgentGradevsGroup(tZSGroupCode, tNewAgentGrade,
            tAgentGroup);
        if (tAgentGroup == null)
          return false;
      }
      //确定代理人系列
      String tAgentSeries = "";
      if (tNewAgentGrade.equals(tOldAgentGrade)) {
        tAgentSeries = tLATreeDB.getAgentSeries();
      }
      else {
        tAgentSeries = getAgentSeries(tNewAgentGrade);
        if (tAgentSeries == null)
          return false;
      }
      //准备销售机构更新的纪录
      //--将以前设置的机构经理置空
      if (tOldAgentGrade.compareTo("A03") > 0) {
        if (!setZSGroupManager(tOldMinGroup, tOldAgentGroup, "", "",
                               tOldAgentGrade))
          return false;
      }
      logger.debug("------------------over ba ---------");
      //--设置现在的相应机构经理
      if (this.mIsManager.equals("true")) {
        logger.debug("------------------come in le");
        if (!setZSGroupManager(tZSGroupCode, tAgentGroup, tAgentCode,
                               this.mLAAgentSchema.getName(), tNewAgentGrade))
          return false;
      }
      //
      String tNewAscription = "";
      String tOldAscription = "";
      tNewAscription = this.mLATreeSchema.getAscriptSeries()==null?""
      :this.mLATreeSchema.getAscriptSeries().trim();
      tOldAscription = tLATreeDB.getAscriptSeries()==null?""
      :tLATreeDB.getAscriptSeries().trim();
      //代理人信息
      //设置转正日期（当其职级由高于A01职级调为A01时要置空）
//       if ((tLATreeDB.getAgentGrade().compareTo("A01")>0)&&this.mLATreeSchema.getAgentGrade().equals("A01"))
//          this.mLAAgentSchema.setInDueFormDate("");
      if (tNewAgentGrade.equals("A01"))
        mLAAgentSchema.setInDueFormDate("");
      else if (tLAAgentDB.getInDueFormDate() == null ||
               tLAAgentDB.getInDueFormDate().equals(""))
        this.mLAAgentSchema.setInDueFormDate(tLAAgentDB.getEmployDate());
      else{
        //佟盟 2005-03-11 修改
     //修改原因：如果修改入司日期的话，转正日期也应该相应改变
     logger.debug("DB :"+tLAAgentDB.getEmployDate());
     logger.debug("S :"+this.mLAAgentSchema.getEmployDate());
    // if(this.mLATreeSchema.getAgentGrade().compareTo("A01")>0)
        if(!tLAAgentDB.getEmployDate().equals(this.mLAAgentSchema.getEmployDate()))
          this.mLAAgentSchema.setInDueFormDate(this.mLAAgentSchema.getEmployDate());
       else
        //tjj add 1229bug 未置转正日期
      this.mLAAgentSchema.setInDueFormDate(tLAAgentDB.getInDueFormDate());}

      this.mLAAgentSchema.setAgentGroup(tAgentGroup);
      this.mLAAgentSchema.setAgentKind("01"); //客户经理
      this.mLAAgentSchema.setMakeDate(tLAAgentDB.getMakeDate());
      this.mLAAgentSchema.setMakeTime(tLAAgentDB.getMakeTime());
      this.mLAAgentSchema.setModifyDate(currentDate);
      this.mLAAgentSchema.setModifyTime(currentTime);
      this.mLAAgentSchema.setOperator(mGlobalInput.Operator);
      //行政信息
      this.mLATreeSchema.setAgentGroup(tAgentGroup);
      String upAgent = this.getUpAgent(tAgentGroup, tNewAgentGrade);

      this.mLATreeSchema.setUpAgent(upAgent);
      this.mLATreeSchema.setAgentSeries(tAgentSeries);
      this.mLATreeSchema.setAssessType("0"); //正常
      this.mLATreeSchema.setState("0");
      //by jiaqiangli comment 2006-07-26
      //由于推荐人可以由初始信息修改为空,否则的话不予判断
//      if ( (this.mLATreeSchema.getIntroAgency() != null) &&
//           (!this.mLATreeSchema.getIntroAgency().equals(""))) {
        this.mLATreeSchema.setIntroBreakFlag("0");
        this.mLATreeSchema.setIntroCommStart(this.mLAAgentSchema.getEmployDate());
        //tongmeng 2006-04-11 add
        //新版基本法修改,增加增员关系
        String sOldIntroAgentcy = tLATreeDB.getIntroAgency();
        String sNewIntroAgentcy = this.mLATreeSchema.getIntroAgency();
        //add by jiaqiangli 增加sNewIntroAgentcy!=null判断
        if((sOldIntroAgentcy!=null&&!sOldIntroAgentcy.equals("")&&!sOldIntroAgentcy.equals(sNewIntroAgentcy)&&sNewIntroAgentcy!=null)
           ||((sOldIntroAgentcy==null)||sOldIntroAgentcy.equals("")))
        {
          LARelationSet tLARelationSet = new LARelationSet();
          AddRelationship tAddRelationship = new AddRelationship();
          tAddRelationship.setAgentCode(tAgentCode);
          tAddRelationship.setEdorNo(tEdorNo);
          tAddRelationship.setEdorType("05");
          tAddRelationship.setCurrentDate(this.currentDate);
          tAddRelationship.setCurrentTime(this.currentTime);
          tAddRelationship.setOperator(this.mGlobalInput.Operator);
          tAddRelationship.setStartDate(this.mLAAgentSchema.getEmployDate());
          tAddRelationship.setAddAgentGroup(tAgentGroup);
          if(!tAddRelationship.setRelationship(this.mLATreeSchema.getIntroAgency()))
          {
            CError tError = new CError();
            tError.moduleName = "ALAAgentBL";
            tError.functionName = "dealdate";
            tError.errorMessage = "处理该代理人增员关系失败!";
            this.mErrors.addOneError(tError);
            return false;
          }
          else
          {
            //取出增员关系集合
            LARelationSet temp = new LARelationSet();
            temp = tAddRelationship.getBefRelation();
            if(temp.size()>0)
            {
              tLARelationSet.set(temp);
              this.mAddLARelationSet.add(tLARelationSet);
            }
            tLARelationSet.clear();
            if(tAddRelationship.getDeleteLARelationSet().size()>0)
            {
              this.mDeleteLARelationSet.add(tAddRelationship.getDeleteLARelationSet());
            }
            if(tAddRelationship.getBackUpRalation().size()>0)
            {
              this.mBakLARelationBSet.add(tAddRelationship.getBackUpRalation());
            }
          }
        }
//      }
      if (!tOldAgentGrade.equals(tNewAgentGrade)) {
        mLATreeSchema.setAgentLastGrade(tLATreeDB.getAgentGrade());
        mLATreeSchema.setAgentLastSeries(tLATreeDB.getAgentSeries());
        mLATreeSchema.setOldStartDate(tLATreeDB.getOldStartDate());
        //tongmeng 2006-06-17 add
        //记录上次外部职级
        mLATreeSchema.setLastAgentGrade1(tLATreeDB.getAgentGrade1());
        //如果修改前职级为组长以上,修改后为组长以下,那么需先判断该代理人是否有育成的人,
        //如果有被育成人,那么报错,通过初始育成修改功能把该代理人育成链重新指定后再做初始信息修改
        //如果有育成人,那么需要删除这条育成记录
        //同理,如果修改前职级为部长级别,修改后为组长级别,需要判断部长级别是否有育成关系,
        //如果有育成关系,同样保错.
        if(tOldAgentGrade.compareTo("A04")>=0&&tNewAgentGrade.compareTo(tOldAgentGrade)<0)
        {
          ExeSQL sExeSQL = new ExeSQL();
          String sResult = "";
          String tRelaLevel = getRelaLevelByAgentGrade(tLATreeDB.getAgentGrade());
          //add by jiaqiangli 如：A09->A08 只须备份和删除04级别的，否则会出错
          String tNewRelaLevel = getRelaLevelByAgentGrade(tNewAgentGrade);
          logger.debug("tNewRelaLevel["+tNewRelaLevel+"]");
          //add by jiaqiangli 如：A09->A08 只须备份和删除04级别的，否则会出错
          while(Integer.parseInt(tRelaLevel)!=0)
          {
            //add by jiaqiangli 2006-11-24
            //如果主管跨级进行降级初始信息修改了育成人的话，这段程序就有问题 insert larelationb多备份了导致主键冲突
            //如：A09->A08 只须备份和删除04级别的，否则会出错
            logger.debug("tRelaLevel["+tRelaLevel+"]");
            logger.debug("tNewRelaLevel[" + tNewRelaLevel + "]");
            if (tRelaLevel.compareTo(tNewRelaLevel) > 0) {
            //校验有没有向后链
            String sSQL = "select count(*) from larelation where relatype='02' "
                        + " and relaagentcode='?relaagentcode?' "
                        + " and relalevel='?tRelaLevel?' ";
            logger.debug("sSQL:"+sSQL);
            SQLwithBindVariables sqlbv=new SQLwithBindVariables();
            sqlbv.sql(sSQL);
            sqlbv.put("relaagentcode", tLATreeDB.getAgentCode());
            sqlbv.put("tRelaLevel", tRelaLevel);
            sResult = sExeSQL.getOneValue(sqlbv);
            if(Integer.parseInt(sResult)>0)
            {
              // @@错误处理
              CError tError = new CError();
              tError.moduleName = "ALAAgentBL";
              tError.functionName = "dealData";
              tError.errorMessage = "该代理人有被育成人,请做初始育成信息修改后再操作!";
              this.mErrors.addOneError(tError);
              return false;
            }
            //校验有没有向前链
            String dSQL = "select * from larelation where relatype='02' "
                        + " and agentcode='?agentcode?' "
                        + " and relalevel='?tRelaLevel?' ";
            SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
            sqlbv1.sql(dSQL);
            sqlbv1.put("agentcode", tLATreeDB.getAgentCode());
            sqlbv1.put("tRelaLevel", tRelaLevel);
            LARelationSet tempLARelationSet = new LARelationSet();
            LARelationDB tempLARelationDB = new LARelationDB();
            tempLARelationSet = tempLARelationDB.executeQuery(sqlbv1);
            if (tempLARelationSet.size() > 0)
            {
                Reflections tempReflections = new Reflections();
                for (int nn = 1; nn <= tempLARelationSet.size(); nn++)
                {
                  LARelationBSchema tempLARelationBSchema = new LARelationBSchema();
                  LARelationSchema sLARelationSchema = new LARelationSchema();
                  sLARelationSchema = tempLARelationSet.get(nn);
                  tempReflections.transFields(tempLARelationBSchema,sLARelationSchema);
                  tempLARelationBSchema.setMakeDate2(sLARelationSchema.getMakeDate());
                  tempLARelationBSchema.setMakeTime2(sLARelationSchema.getMakeTime());
                  tempLARelationBSchema.setModifyDate2(sLARelationSchema.getModifyDate());
                  tempLARelationBSchema.setModifyTime2(sLARelationSchema.getModifyTime());
                  tempLARelationBSchema.setOperator2(sLARelationSchema.getOperator());
                  tempLARelationBSchema.setMakeDate(currentDate);
                  tempLARelationBSchema.setMakeTime(currentTime);
                  tempLARelationBSchema.setModifyDate(currentDate);
                  tempLARelationBSchema.setModifyTime(currentTime);
                  tempLARelationBSchema.setOperator(this.mGlobalInput.Operator);
                  tempLARelationBSchema.setEdorNo(tEdorNo);
                  tempLARelationBSchema.setEdorType("05");
                  tempLARelationBSchema.setIndexCalNo("");
                  this.mBakLARelationBSet.add(tempLARelationBSchema);
                }
                this.mDeleteLARelationSet.add(tempLARelationSet);
              }
            }
            tRelaLevel = "0"+String.valueOf(Integer.parseInt(tRelaLevel)-1);
          }
        }
      }
      this.mLATreeSchema.setStartDate(this.mLAAgentSchema.getEmployDate());
      this.mLATreeSchema.setAstartDate(this.mLAAgentSchema.getEmployDate());
      this.mLATreeSchema.setMakeDate(tLATreeDB.getMakeDate());
      this.mLATreeSchema.setMakeTime(tLATreeDB.getMakeTime());
      this.mLATreeSchema.setModifyDate(currentDate);
      this.mLATreeSchema.setModifyTime(currentTime);
      this.mLATreeSchema.setOperator(mGlobalInput.Operator);

      //行政附属信息（先删后插方式）
      if (tNewAgentGrade.compareTo("A04") >= 0) {
        //tongmeng 2006-04-11 add
        //增加育成关系
        if(!tNewAscription.equals(tOldAscription))
        {
          LARelationSet dLARelationSet = new LARelationSet();
          String tRelaLevel = getRelaLevelByAgentGrade(this.mLATreeSchema.getAgentGrade());
          //tongmeng 2006-05-19 add
          //如果育成关系没有修改,则不做备份
          LARelationSchema tempLARelationSchema = new LARelationSchema();
          tempLARelationSchema = queryLARelation(tAgentCode,"02",tRelaLevel,"1");
          String tOldRelaAgentCode = "";
          String tNewRelaAgentCode = "";
          //if(tempLARelationSchema!=null)
          {
            tOldRelaAgentCode = tempLARelationSchema.getRelaAgentCode();
            tNewRelaAgentCode = this.mLATreeSchema.getEduManager();
          }
          if(tOldRelaAgentCode==null)
            tOldRelaAgentCode = "";
          if(tNewRelaAgentCode==null)
            tNewRelaAgentCode = "";
          if(!tOldRelaAgentCode.equals(tNewRelaAgentCode))
          {
            DesignRearRelationship tDesignRearRelationship = new DesignRearRelationship();
            tDesignRearRelationship.setAgentCode(tAgentCode,tRelaLevel);
            tDesignRearRelationship.setEdorNo(tEdorNo);
            tDesignRearRelationship.setEdorType("05");//初始信息修改
            tDesignRearRelationship.setCurrentDate(this.currentDate);
            tDesignRearRelationship.setCurrentTime(this.currentTime);
            tDesignRearRelationship.setOperator(this.mGlobalInput.Operator);
            tDesignRearRelationship.setStartDate(this.mLATreeSchema.getStartDate());
            tDesignRearRelationship.setRelaAgentGroup(tAgentGroup);
            if(!tDesignRearRelationship.setRelationShipByRelPerXZ(this.mLATreeSchema.getEduManager()))
            {
              CError tError = new CError();
              tError.moduleName = "ALAAgentBL";
              tError.functionName = "dealdate";
              tError.errorMessage = "处理该代理人育成关系失败!";
              this.mErrors.addOneError(tError);
              return false;
            }
            else
            {
              LARelationSet temp = new LARelationSet();
              temp = tDesignRearRelationship.getBefRelation();
              if(temp.size()>0)
              {
                dLARelationSet.set(temp);
                this.mRelaLARelationSet.add(dLARelationSet);
              }
              if(tDesignRearRelationship.getDeleteLARelationSet().size()>0)
              {
                this.mDeleteLARelationSet.add(tDesignRearRelationship.getDeleteLARelationSet());
              }
              if(tDesignRearRelationship.getBackUpRalation().size()>0)
              {
                this.mBakLARelationBSet.add(tDesignRearRelationship.getBackUpRalation());
              }
            }
          }
          //此处借用原育成关系表去循环处理各级育成关系
          LATreeAccessorySchema tLATreeAccessorySchema = new
              LATreeAccessorySchema();
          tLATreeAccessorySchema.setAgentCode(tAgentCode);
          tLATreeAccessorySchema.setAgentGrade(this.mLATreeSchema.getAgentGrade());
          tLATreeAccessorySchema.setAgentGroup(tAgentGroup);
          tLATreeAccessorySchema.setManageCom(this.mLATreeSchema.getManageCom());
          if ( (this.mLATreeSchema.getEduManager() != null) &&
               (!this.mLATreeSchema.getEduManager().equals(""))) {
            tLATreeAccessorySchema.setRearAgentCode(this.mLATreeSchema.
                getEduManager());
          }
          tLATreeAccessorySchema.setstartdate(this.mLAAgentSchema.getEmployDate());
          tLATreeAccessorySchema.setOperator(mGlobalInput.Operator);
          String tAscript = this.mLATreeSchema.getAscriptSeries().trim();
          prepareTreeAccessory(tAscript, tLATreeAccessorySchema, tNewAgentGrade,tEdorNo);
//        this.mLATreeAccessorySet.add(tLATreeAccessorySchema);
        }
      }
      logger.debug("开始校验循环链");
      //tongmeng 2006-06-17 add
      //增加循环链的校验
      //校验育成链
      for(int i=1;i<=this.mRelaLARelationSet.size();i++)
      {
        LARelationSchema sLARelationSchema = new LARelationSchema();
        sLARelationSchema.setSchema(mRelaLARelationSet.get(i));
        PubCheckField checkField1  = new PubCheckField();
        VData nInputData = new VData();
        //设置计算时要用到的参数值
        TransferData nTransferData = new TransferData();
        nTransferData.setNameAndValue("RelaType",sLARelationSchema.getRelaType());
        nTransferData.setNameAndValue("AgentCode",sLARelationSchema.getAgentCode());
        nTransferData.setNameAndValue("RelaAgentCode",sLARelationSchema.getRelaAgentCode()==null?"":sLARelationSchema.getRelaAgentCode());
        nTransferData.setNameAndValue("RelaLevel",sLARelationSchema.getRelaLevel());
        //通过CKBYSET
        LMCheckFieldSet tLMCheckFieldSet = new LMCheckFieldSet();
        LMCheckFieldDB tLMCheckFieldDB = new LMCheckFieldDB();
        String tSql = "select * from lmcheckfield where riskcode = '000000'"
                    + "  and fieldname = 'CheckLoopRelation' order by serialno asc";
        SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
        sqlbv2.sql(tSql);
        tLMCheckFieldSet = tLMCheckFieldDB.executeQuery(sqlbv2);
        //通过 CKBYFIELD 方式校验
        nInputData.add(0,nTransferData);
        nInputData.add(1,tLMCheckFieldSet);
        if(!checkField1.submitData(nInputData,"CKBYSET"))
        {
          logger.debug("校验代理人是否存在循环链时发现错误");
          //此判断是用来区分是程序处理过程中报的错误，还是校验时报的错误
          if(checkField1.mErrors.needDealError())
          {
            logger.debug("ERROR-S-"+checkField1.mErrors.getFirstError());
            CError tError = new CError();
            tError.moduleName = "NAALAAgentBL";
            tError.functionName = "dealata";
            tError.errorMessage = "校验循环链出错！";
            this.mErrors.addOneError(tError);
            logger.debug("校验循环链出错！！");
            return false;
          }
          else
          {
            VData t = checkField1.getResultMess();
            CError tError = new CError();
            tError.moduleName = "NAALAAgentBL";
            tError.functionName = "dealata";
            tError.errorMessage = "代理人育成关系"+t.get(0).toString();
            this.mErrors.addOneError(tError);
            logger.debug("校验循环链出错:"+t.get(0).toString());
            return false;
          }
        }
      }
      //校验增员链
      for(int i=1;i<=this.mAddLARelationSet.size();i++)
      {
        LARelationSchema sLARelationSchema = new LARelationSchema();
        sLARelationSchema.setSchema(mAddLARelationSet.get(i));
        PubCheckField checkField1  = new PubCheckField();
        VData nInputData = new VData();
        //设置计算时要用到的参数值
        TransferData nTransferData = new TransferData();
        nTransferData.setNameAndValue("RelaType",sLARelationSchema.getRelaType());
        nTransferData.setNameAndValue("AgentCode",sLARelationSchema.getAgentCode());
        nTransferData.setNameAndValue("RelaAgentCode",sLARelationSchema.getRelaAgentCode()==null?"":sLARelationSchema.getRelaAgentCode());
        nTransferData.setNameAndValue("RelaLevel",sLARelationSchema.getRelaLevel());
        //通过CKBYSET
        LMCheckFieldSet tLMCheckFieldSet = new LMCheckFieldSet();
        LMCheckFieldDB tLMCheckFieldDB = new LMCheckFieldDB();
        String tSql = "select * from lmcheckfield where riskcode = '000000'"
                    + "  and fieldname = 'CheckLoopRelation' order by serialno asc";
        SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
        sqlbv3.sql(tSql);
        tLMCheckFieldSet = tLMCheckFieldDB.executeQuery(sqlbv3);
        //通过 CKBYFIELD 方式校验
        nInputData.add(0,nTransferData);
        nInputData.add(1,tLMCheckFieldSet);
        if(!checkField1.submitData(nInputData,"CKBYSET"))
        {
          logger.debug("校验代理人是否参加考核时发现错误");
          //此判断是用来区分是程序处理过程中报的错误，还是校验时报的错误
          if(checkField1.mErrors.needDealError())
          {
            logger.debug("ERROR-S-"+checkField1.mErrors.getFirstError());
            CError tError = new CError();
            tError.moduleName = "NAALAAgentBL";
            tError.functionName = "dealata";
            tError.errorMessage = "校验循环链出错！";
            this.mErrors.addOneError(tError);
            logger.debug("校验循环链出错！！");
            return false;
          }
          else
          {
            VData t = checkField1.getResultMess();
            CError tError = new CError();
            tError.moduleName = "NAALAAgentBL";
            tError.functionName = "dealata";
            tError.errorMessage = "代理人增员关系"+t.get(0).toString();
            this.mErrors.addOneError(tError);
            logger.debug("校验循环链出错:"+t.get(0).toString());
            return false;
          }
        }
      }
      logger.debug("结束校验循环链");
    }
    if (this.mOperate.equals("UPDATE||PART")) {
      tAgentCode = this.mLAAgentSchema.getAgentCode().trim();
      String tEdorNo = PubFun1.CreateMaxNo("EdorNo", 20);
      //查询出AgentGroup和AgentGrade，判断是否修改
      LAAgentDB tLAAgentDB = new LAAgentDB();
      tLAAgentDB.setAgentCode(tAgentCode);
      if (!tLAAgentDB.getInfo()) {
        // @@错误处理
        this.mErrors.copyAllErrors(tLAAgentDB.mErrors);
        CError tError = new CError();
        tError.moduleName = "ALAAgentBL";
        tError.functionName = "submitDat";
        tError.errorMessage = "查询原代理人信息失败!";
        this.mErrors.addOneError(tError);
        return false;
      }
      //备份原代理人信息
      Reflections tReflections = new Reflections();
      tReflections.transFields(this.mLAAgentBSchema, tLAAgentDB.getSchema());
      this.mLAAgentBSchema.setEdorNo(tEdorNo);
      this.mLAAgentBSchema.setEdorType("09");
      this.mLAAgentBSchema.setMakeDate(currentDate);
      this.mLAAgentBSchema.setMakeTime(currentTime);
      this.mLAAgentBSchema.setModifyDate(currentDate);
      this.mLAAgentBSchema.setModifyTime(currentTime);
      this.mLAAgentBSchema.setOperator(this.mGlobalInput.Operator);
      //代理人信息
      //设置转正日期（当其职级由高于A01职级调为A01时要置空）
      this.mLAAgentSchema.setInDueFormDate(tLAAgentDB.getInDueFormDate());
      this.mLAAgentSchema.setAgentGroup(tLAAgentDB.getAgentGroup());
      this.mLAAgentSchema.setAgentKind("01"); //客户经理
      this.mLAAgentSchema.setMakeDate(tLAAgentDB.getMakeDate());
      this.mLAAgentSchema.setMakeTime(tLAAgentDB.getMakeTime());
      this.mLAAgentSchema.setModifyDate(currentDate);
      this.mLAAgentSchema.setModifyTime(currentTime);
      this.mLAAgentSchema.setOperator(mGlobalInput.Operator);

     //基础信息修改核销
      this.mLAAgentModCtlDB.setAgentCode(mLAAgentSchema.getAgentCode());
      this.mLAAgentModCtlDB.setState("0");
      this.mLAAgentModCtlDB.setModType("基础信息");
      LAAgentModCtlSet tLAAgentModCtlSet=new LAAgentModCtlSet();
      
      tLAAgentModCtlSet =mLAAgentModCtlDB.query();
      LAAgentModCtlSchema mLAAgentModCtlSchema=new LAAgentModCtlSchema(); 
      for (int i=1;i<=tLAAgentModCtlSet.size();i++)
       {
    	  mLAAgentModCtlSchema=tLAAgentModCtlSet.get(i);
    	  mLAAgentModCtlSchema.setState("1");
    	  mLAAgentModCtlSchema.setModifyDate(currentDate);
    	  mLAAgentModCtlSchema.setModifyTime(currentTime);
    	  mLAAgentModCtlSchema.setOperator(mGlobalInput.Operator);
    	  this.mLAAgentModCtlSET.add(mLAAgentModCtlSchema);
    	  
    	  
       } 
    
    }
    if (this.mOperate.equals("INSERT||MAIN") ||
        this.mOperate.indexOf("UPDATE") != -1) {
      //担保人信息表
      int aCount = this.mLAWarrantorSet.size();
      logger.debug(Integer.toString(aCount));
      for (int i = 1; i <= aCount; i++) {
        if (this.mOperate.equals("INSERT||MAIN"))
          this.mLAWarrantorSet.get(i).setAgentCode(tAgentCode);
        this.mLAWarrantorSet.get(i).setSerialNo(i);
        this.mLAWarrantorSet.get(i).setOperator(mGlobalInput.Operator);
        this.mLAWarrantorSet.get(i).setMakeDate(currentDate);
        this.mLAWarrantorSet.get(i).setMakeTime(currentTime);
        this.mLAWarrantorSet.get(i).setModifyDate(currentDate);
        this.mLAWarrantorSet.get(i).setModifyTime(currentTime);
      }
    }
    //if (this.mOperate.equals("DELETE||MAIN")&& this.mIsManager.equals("true"))
    if (this.mOperate.equals("DELETE||MAIN")) {
      LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
      tLABranchGroupDB.setBranchManager(this.mLAAgentSchema.getAgentCode());
      this.mLABranchGroupSet = tLABranchGroupDB.query();
      for (int i = 1; i <= mLABranchGroupSet.size(); i++) {
        this.mLABranchGroupSet.get(i).setBranchManager("");
        this.mLABranchGroupSet.get(i).setBranchManagerName("");
      }
    }
    else {
      LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
      tLABranchGroupDB.setBranchManager(this.mLAAgentSchema.getAgentCode());
      LABranchGroupSet tLABranchGroupSet=new LABranchGroupSet();
      tLABranchGroupSet = tLABranchGroupDB.query();
      for (int i = 1; i <= tLABranchGroupSet.size(); i++) {
        LABranchGroupSchema tLABranchGroupSchema=tLABranchGroupSet.get(i) ;
        if (tLABranchGroupSchema.getBranchManagerName()!=null&&tLABranchGroupSchema.equals(mLAAgentSchema.getName()) )
        {
          continue;
        }
        else
        {
          tLABranchGroupSchema.setBranchManagerName(mLAAgentSchema.
              getName());
          logger.debug("before add to LABranchGroupSet....4...."+tLABranchGroupSchema.getBranchAttr() );
//          mLABranchGroupSet.add(tLABranchGroupSchema) ;
        }
      }
    }

    return tReturn;
  }

  /**
   * 从输入数据中得到所有对象
   *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
   */
  private boolean getInputData(VData cInputData) {
     this.mGlobalInput.setSchema( (GlobalInput) cInputData.getObjectByObjectName(
        "GlobalInput", 0));
    mIsManager = (String) cInputData.get(1);
    logger.debug("mIsManager"+mIsManager);
     this.mLAAgentSchema.setSchema( (LAAgentSchema) cInputData.
                                   getObjectByObjectName("LAAgentSchema", 0));
     this.mLATreeSchema.setSchema( (LATreeSchema) cInputData.
                                  getObjectByObjectName("LATreeSchema", 0));
     this.mLAWarrantorSet.set( (LAWarrantorSet) cInputData.getObjectByObjectName(
        "LAWarrantorSet", 0));
    
    mTransferData=(TransferData)cInputData.getObjectByObjectName(
            "TransferData", 0);
     return true;
  }

  /**
   * 准备往后层输出所需要的数据
   * 输出：如果准备数据时发生错误则返回false,否则返回true
   */
  private boolean submitquery() {
    this.mResult.clear();
    logger.debug("Start ALAAgentBLQuery Submit...");
    LAAgentDB tLAAgentDB = new LAAgentDB();
    tLAAgentDB.setSchema(this.mLAAgentSchema);
    this.mLAAgentSet = tLAAgentDB.query();
    this.mResult.add(this.mLAAgentSet);
    logger.debug("End ALAAgentBLQuery Submit...");
    //如果有需要处理的错误，则返回
    if (tLAAgentDB.mErrors.needDealError()) {
      // @@错误处理
      this.mErrors.copyAllErrors(tLAAgentDB.mErrors);
      CError tError = new CError();
      tError.moduleName = "ALAAgentBL";
      tError.functionName = "submitData";
      tError.errorMessage = "数据提交失败!";
      this.mErrors.addOneError(tError);
      return false;
    }
    mInputData = null;
    return true;
  }

  private boolean prepareOutputData() {
    try {
      logger.debug("###############111111111+"+this.mLATreeSchema.getEmployGrade());
      this.mInputData = new VData();
      this.mInputData.add(0,this.mGlobalInput);
      this.mInputData.add(1,this.mLAAgentSchema);
      this.mInputData.add(2,this.mLAAgentBSchema);
      this.mInputData.add(3,this.mLATreeSchema);
      this.mInputData.add(4,this.mLATreeBSchema);
//      this.mInputData.add(this.mLATreeAccessorySet);
      this.mInputData.add(5,this.mLABranchGroupSet);
      this.mInputData.add(6,this.mLAWarrantorSet);
      this.mInputData.add(7,this.mDeleteLARelationSet);
      this.mInputData.add(8,this.mBakLARelationBSet);
      this.mInputData.add(9,this.mAddLARelationSet);
      this.mInputData.add(10,this.mRelaLARelationSet);
      this.mInputData.add(11,this.mLLAAssuMoneySchema);
      this.mInputData.add(12,this.mLAADDCONTROLSET);
      this.mInputData.add(13,this.mLAAgentModCtlSET);
      
    }
    catch (Exception ex) {
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "ALAAgentBL";
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

  private boolean getInfo(String cAgentGroup) {
    LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
    tLABranchGroupDB.setAgentGroup(cAgentGroup);
    if (!tLABranchGroupDB.getInfo()) {
    	logger.debug("2222222");
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "ALAAgentBL";
      tError.functionName = "getInfo";
      tError.errorMessage = "查询机构信息出错。";
      this.mErrors.addOneError(tError);
      return false;
    }
    //this.mLABranchGroupSchema = null;
    this.mLABranchGroupSchema.setSchema(tLABranchGroupDB.getSchema());
    return true;
  }

  //根据直辖组的显式代码得到代理人职级对应的机构隐式代码
  private String getAgentGradevsGroup(String cZSGroup, String cAgentGrade,
                                      String cBranchAttr) {
    String tGradeGroup = cBranchAttr;
    if (cAgentGrade.compareTo("A05") > 0) {
      logger.debug("come in");
      switch (Integer.parseInt(cAgentGrade.substring(1))) {
        case 6: {
          tGradeGroup = tGradeGroup.substring(0, tGradeGroup.length() - 3); //高级经理
          break;
        }
      case 7: {
        tGradeGroup = tGradeGroup.substring(0, tGradeGroup.length() - 3); //高级经理
        break;
      }
    case 8: {
      tGradeGroup = tGradeGroup.substring(0, tGradeGroup.length() - 6); //督导长
      break;
    }
  case 9: {
    tGradeGroup = tGradeGroup.substring(0, tGradeGroup.length() - 8); //区域督导长
    logger.debug(tGradeGroup);
    break;
  }
      }
      //modify by tongmeng 2005-07-29
      //增加branchtype='1'防止和续收员、法人的branchattr冲突
      String tSQL = "select AgentGroup from laBranchGroup where BranchAttr = '?tGradeGroup?' and branchtype='1' ";
      logger.debug(tSQL);
      SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
      sqlbv4.sql(tSQL);
      sqlbv4.put("tGradeGroup", tGradeGroup);
      ExeSQL tExeSQL = new ExeSQL();
      tGradeGroup = tExeSQL.getOneValue(sqlbv4).trim();
      if (tExeSQL.mErrors.needDealError()) {
        // @@错误处理
        this.mErrors.copyAllErrors(tExeSQL.mErrors);
        CError tError = new CError();
        tError.moduleName = "ALAgentBL";
        tError.functionName = "getAgentGradevsGroup";
        tError.errorMessage = "执行SQL语句：从表中取值失败!";
        this.mErrors.addOneError(tError);
        return null;
      }
    }
    else
      tGradeGroup = cZSGroup;
    return tGradeGroup;
  }

  //根据代理人职级确定代理人系列
  private String getAgentSeries(String cAgentGrade) {
    String tAgentSeries = "";
    String tSQL =
        "select code2 from ldcodeRela where relaType = 'gradeserieslevel' "
        + "and code1 = '?cAgentGrade?'";
    SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
    sqlbv5.sql(tSQL);
    sqlbv5.put("cAgentGrade", cAgentGrade);
    ExeSQL tExeSQL = new ExeSQL();
    tAgentSeries = tExeSQL.getOneValue(sqlbv5).trim();
    if (tExeSQL.mErrors.needDealError()) {
      // @@错误处理
      this.mErrors.copyAllErrors(tExeSQL.mErrors);
      CError tError = new CError();
      tError.moduleName = "ALAgentBL";
      tError.functionName = "dealData";
      tError.errorMessage = "执行SQL语句：从表中取值失败!";
      this.mErrors.addOneError(tError);
      return null;
    }
    return tAgentSeries;
  }

  private boolean clearOldGroupManager(
      String ManagerCode
      ) {
    String sql = "select * from labranchgroup where branchmanager='?ManagerCode?'";
    SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
    sqlbv6.sql(sql);
    sqlbv6.put("ManagerCode", ManagerCode);
    LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
    LABranchGroupSet tLABranchGroupSet = new LABranchGroupSet();
    tLABranchGroupSet = tLABranchGroupDB.executeQuery(sqlbv6);
    for (int i = 1; i <= tLABranchGroupSet.size(); i++) {
      LABranchGroupSchema tLABranchGroupSchema = tLABranchGroupSet.get(i);
      tLABranchGroupSchema.setBranchManager("");
      tLABranchGroupSchema.setBranchManagerName("");
      logger.debug("before add to LABranchGroupSet....1...."+tLABranchGroupSchema.getBranchAttr() ) ;
      this.mLABranchGroupSet.add(tLABranchGroupSchema);
    }
    return true;

  }
  private boolean setZSGroupManager(String cMinGroup, String cMaxGroup,
                                    String ManagerCode,
                                    String ManagerName, String cAgentGrade) {
    String tSQL = "", tZSvalue = "";
    ExeSQL tExeSQL;
    logger.debug("ererer");

    if (!getInfo(cMinGroup))
      return false;
    this.mLABranchGroupSchema.setBranchManager(ManagerCode);
    this.mLABranchGroupSchema.setBranchManagerName(ManagerName);
    this.mLABranchGroupSchema.setModifyDate(currentDate);
    this.mLABranchGroupSchema.setModifyTime(currentTime);
    this.mLABranchGroupSchema.setOperator(this.mGlobalInput.Operator);
    LABranchGroupSchema tLABranchGroupSchema = new LABranchGroupSchema();
    tLABranchGroupSchema.setSchema(this.mLABranchGroupSchema);
    logger.debug("before add to LABranchGroupSet....2...."+tLABranchGroupSchema.getBranchAttr() ) ;
    this.mLABranchGroupSet.add(tLABranchGroupSchema);
    if (cAgentGrade.compareTo("A05") > 0) { //高级经理以上执行
      String tMinGroup = cMinGroup;
      do {
        tSQL = "select UpBranch from LABranchGroup where AgentGroup = '?tMinGroup?'";
        SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
        sqlbv7.sql(tSQL);
        sqlbv7.put("tMinGroup", tMinGroup);
        tExeSQL = new ExeSQL();
        tMinGroup = tExeSQL.getOneValue(sqlbv7).trim();
        if (tExeSQL.mErrors.needDealError()) {
          // @@错误处理
          this.mErrors.copyAllErrors(tExeSQL.mErrors);
          CError tError = new CError();
          tError.moduleName = "ALAgentBL";
          tError.functionName = "setZSGroupManager";
          tError.errorMessage = "执行SQL语句：从表中取值失败!";
          this.mErrors.addOneError(tError);
          return false;
        }
        tSQL = "select UpBranchAttr from LABranchGroup where AgentGroup = '?tMinGroup?'";
        SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
        sqlbv8.sql(tSQL);
        sqlbv8.put("tMinGroup", tMinGroup);
        tExeSQL = new ExeSQL();
        tZSvalue = tExeSQL.getOneValue(sqlbv8).trim();
        if (tExeSQL.mErrors.needDealError()) {
          // @@错误处理
          this.mErrors.copyAllErrors(tExeSQL.mErrors);
          CError tError = new CError();
          tError.moduleName = "ALAgentBL";
          tError.functionName = "setZSGroupManager";
          tError.errorMessage = "执行SQL语句：从表中取值失败!";
          this.mErrors.addOneError(tError);
          return false;
        }
        logger.debug(tMinGroup + "   " + tZSvalue + "  " + cMaxGroup);
        if ( (tZSvalue.equals("0")) && (!tMinGroup.equals(cMaxGroup))) {
          CError tError = new CError();
          tError.moduleName = "ALAgentBL";
          tError.functionName = "setZSGroupManager";
          tError.errorMessage = "所输销售机构不是该代理人的直辖组!";
          this.mErrors.addOneError(tError);
          return false;
        }
        if (!getInfo(tMinGroup))
          return false;
        this.mLABranchGroupSchema.setBranchManager(ManagerCode);
        this.mLABranchGroupSchema.setBranchManagerName(ManagerName);
        this.mLABranchGroupSchema.setModifyDate(currentDate);
        this.mLABranchGroupSchema.setModifyTime(currentTime);
        this.mLABranchGroupSchema.setOperator(this.mGlobalInput.Operator);
        tLABranchGroupSchema = new LABranchGroupSchema();
        tLABranchGroupSchema.setSchema(this.mLABranchGroupSchema);
        logger.debug("before add to LABranchGroupSet....3...."+tLABranchGroupSchema.getBranchAttr() ) ;
        this.mLABranchGroupSet.add(tLABranchGroupSchema);
      }
      while (!tMinGroup.equals(cMaxGroup));
    }
    else if (!ManagerCode.equals("")) {
      tSQL = "select UpBranchAttr from LABranchGroup where AgentGroup = '?cMinGroup?'";
      SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
      sqlbv9.sql(tSQL);
      sqlbv9.put("cMinGroup", cMinGroup);
      tExeSQL = new ExeSQL();
      tZSvalue = tExeSQL.getOneValue(sqlbv9).trim();
      if (tExeSQL.mErrors.needDealError()) {
        // @@错误处理
        this.mErrors.copyAllErrors(tExeSQL.mErrors);
        CError tError = new CError();
        tError.moduleName = "ALAgentBL";
        tError.functionName = "setZSGroupManager";
        tError.errorMessage = "执行SQL语句：从表中取值失败!";
        this.mErrors.addOneError(tError);
        return false;
      }
      logger.debug(cMinGroup + "   " + tZSvalue + "  " + cMaxGroup);
      if ( (tZSvalue.equals("1"))) {
        CError tError = new CError();
        tError.moduleName = "ALAgentBL";
        tError.functionName = "setZSGroupManager";
        tError.errorMessage = "该代理人所输职级与其机构不对应！";
        this.mErrors.addOneError(tError);
        return false;
      }
    }
    return true;
  }

  //育成人的个数决定插入行政附属表中的记录数
  /**
   * 根据界面传入的育成链数据，来决定是否需要往LATreeAccessory表中插入育成记录
   * 蔡刚2004-05-28 修改
   * @param cAscriptSeries
   * @param cTASchema
   * @param cAgentGrade
   */
  private void prepareTreeAccessory(String cAscriptSeries,
                                    LATreeAccessorySchema cTASchema,
                                    String cAgentGrade,String sEdorNo) {
    //tongmeng 2006-04-11 add
    //新版基本法修改,增加育成关系
    String currAgentGrade = cTASchema.getAgentGrade();
    String agentCode = cTASchema.getAgentCode();
    String tAscriptSeries = cAscriptSeries;
    logger.debug("育成链：" + tAscriptSeries);
    //佟盟2005-03-08增加
    //重新构建育成链，把育成人为空的地方标记上"**"
    tAscriptSeries=changeAscriptSeries(tAscriptSeries);
    if (tAscriptSeries.indexOf(":") == -1)
      tAscriptSeries = "";
    if (!tAscriptSeries.equals("")) {
      tAscriptSeries = tAscriptSeries.substring(0,
          tAscriptSeries.lastIndexOf(":"));
      logger.debug("tAscriptSeries:" +tAscriptSeries);
      if (tAscriptSeries.length() >1)
      {
        String[] rearAgentCodes = PubFun.split(tAscriptSeries,":");
        int rearLength = rearAgentCodes.length;
        String agentGroup=cTASchema.getAgentGroup();
        for (int i = rearLength-1; i >=0; i--) {
          //佟盟2005-03-08修改
          //如果该代理人的育成链中的相应信息为空，也要向LATreeAccessory表中插入信息
          if(rearAgentCodes[i].equals("**"))
            rearAgentCodes[i]="";
          logger.debug("rearAgentCode[" + i + "]:" + rearAgentCodes[i]);
          agentGroup = this.getAgentGroupByAgentSeries(agentGroup, i);
          //tongmeng 2006-04-11 add
          //增加育成关系
          LARelationSet dLARelationSet = new LARelationSet();
          LARelationSchema dLARelationSchema = new LARelationSchema();
          String agentGrade=this.getGradeBySeries(i);
          String tRelaLevel = getRelaLevelByAgentGrade(agentGrade);
          dLARelationSchema = queryLARelation(agentCode,"02",tRelaLevel,"1");
          //tongmeng 2005-05-19 add
          //增加对本级育成人是否做修改的校验
          String tOldRelaAgentCode = "";
          String tNewRelaAgentCode = "";
          tOldRelaAgentCode = dLARelationSchema.getRelaAgentCode()==null?"":dLARelationSchema.getRelaAgentCode();
          tNewRelaAgentCode = rearAgentCodes[i];
          if(tOldRelaAgentCode==null)
            tOldRelaAgentCode = "";
          if(tNewRelaAgentCode==null)
            tNewRelaAgentCode = "";
          if(!this.mOperate.equals("INSERT||MAIN"))
          {
            if(tOldRelaAgentCode.equals(tNewRelaAgentCode))
              continue;
          }
          DesignRearRelationship tDesignRearRelationship = new DesignRearRelationship();
          tDesignRearRelationship.setAgentCode(agentCode,tRelaLevel);
          tDesignRearRelationship.setCurrentDate(this.currentDate);
          tDesignRearRelationship.setCurrentTime(this.currentTime);
          tDesignRearRelationship.setEdorNo(sEdorNo);
          tDesignRearRelationship.setEdorType("05");//初始信息修改
          tDesignRearRelationship.setOperator(this.mGlobalInput.Operator);
          //如果有记录,育成时间使用原育成时间,如果没有记录,育成时间与上级育成级别育成时间相同
          if(dLARelationSchema.getstartDate() == null||dLARelationSchema.getstartDate().equals(""))
          {
            tDesignRearRelationship.setStartDate(cTASchema.getstartdate());
          }
          else
          {
            tDesignRearRelationship.setStartDate(dLARelationSchema.getstartDate());
          }
          tDesignRearRelationship.setRelaAgentGroup(agentGroup);
          tDesignRearRelationship.setRelationShipByRelPerXZ(rearAgentCodes[i]);
          if(tDesignRearRelationship.getBefRelation().size()>0)
          {
            dLARelationSet.set(tDesignRearRelationship.getBefRelation());
            this.mRelaLARelationSet.add(dLARelationSet);
          }
          if(tDesignRearRelationship.getDeleteLARelationSet().size()>0)
          {
            this.mDeleteLARelationSet.add(tDesignRearRelationship.getDeleteLARelationSet());
          }
          if(tDesignRearRelationship.getBackUpRalation().size()>0)
          {
            this.mBakLARelationBSet.add(tDesignRearRelationship.getBackUpRalation());
          }
        }
      }
    }
  }
  private LATreeAccessorySchema getLATreeAccessorySchema(String AgentCode,
      int i) {
    String agentgrade = "";
    switch (i) {
      case 0:
        agentgrade = " and ( agentgrade='A04' or agentgrade='A05' )";
        break;
      case 1:
        agentgrade = " and ( agentgrade='A06' or agentgrade='A07') ";
        break;
      case 2:
        agentgrade = " and  agentgrade='A08'  ";
        break;
      case 3:
        agentgrade = " and agentgrade='A09' ";
        break;
    }
    String tSQL = "select * from latreeaccessory where agentcode='?AgentCode?'";
    tSQL = tSQL + agentgrade;
    SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
    sqlbv10.sql(tSQL);
    sqlbv10.put("AgentCode", AgentCode);
    logger.debug(tSQL);
    LATreeAccessoryDB tLATreeAccessoryDB = new LATreeAccessoryDB();
    LATreeAccessorySet tLATreeAccessorySet = new LATreeAccessorySet();
    tLATreeAccessorySet = tLATreeAccessoryDB.executeQuery(sqlbv10);
    if (tLATreeAccessorySet.size() == 0)
      return null;
    else
      return tLATreeAccessorySet.get(1);
  }

  private String getAgentGroupByAgentSeries( String agentGroup,int Series) {
    String branchLevel = "";
    switch (Series) {
      case 0:
        branchLevel = "01";
        break;
      case 1:
        branchLevel = "02";
        break;
      case 2:
        branchLevel = "03";
        break;
      case 3:
        branchLevel = "04";
        break;
    }
    String sql =
        "select (case when agentgroup is not null then agentgroup else 'NotFound' end) from labranchgroup where upBranch='?agentGroup?' and upBranchAttr='" + 1 + "'";
    SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
    sqlbv11.sql(sql);
    sqlbv11.put("agentGroup", agentGroup);
    ExeSQL tExeSQL = new ExeSQL();
    String agentgroup = tExeSQL.getOneValue(sqlbv11);
    logger.debug(sql);
    if (agentgroup.equals("NotFound")) {
      agentgroup = "";
    }
    return agentgroup;
  }

  private String getGradeBySeries(int Series) {
    String agentGrade = "";
    switch (Series) {
      case 0:
        agentGrade = "A04";
        break;
      case 1:
        agentGrade = "A06";
        break;
      case 2:
        agentGrade = "A08";
        break;
      case 3:
        agentGrade = "A09";
        break;
    }
    return agentGrade;
  }

  private String getUpAgent(String agentGroup, String agentGrade) {
    LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
    tLABranchGroupDB.setAgentGroup(agentGroup);
    if (!tLABranchGroupDB.getInfo()) {
      this.mErrors.copyAllErrors(tLABranchGroupDB.mErrors);
      CError tError = new CError();
      tError.moduleName = "ALAgentBL";
      tError.functionName = "getUpAgent";
      tError.errorMessage = "查询不到" + agentGroup + "的机构信息!";
      this.mErrors.addOneError(tError);
      return null;
    }
    String branchManager = "";
    if (agentGrade.compareTo("A04") < 0) {
      branchManager = tLABranchGroupDB.getBranchManager();
    }
    else {
      String upBranch = tLABranchGroupDB.getUpBranch();
      if (upBranch != null && !upBranch.equals("")) {
        tLABranchGroupDB.setAgentGroup(upBranch);
        if (!tLABranchGroupDB.getInfo()) {
          this.mErrors.copyAllErrors(tLABranchGroupDB.mErrors);
          CError tError = new CError();
          tError.moduleName = "ALAgentBL";
          tError.functionName = "getUpAgent";
          tError.errorMessage = "查询不到" + upBranch + "的机构信息!";
          this.mErrors.addOneError(tError);
          return null;

        }
      }
      branchManager=tLABranchGroupDB.getBranchManager() ;
    }
    if (branchManager==null)
      branchManager="";
    return branchManager;

  }
  private boolean checkData() {
    if (this.mOperate .equals("INSERT||MAIN") ||
        this.mOperate.indexOf("UPDATE") != -1)
    {
      String tIDNo=this.mLAAgentSchema.getIDNo() ;
      //add tongmeng 2005-06-28
      //修改原因 增加对本身展业类型身份证的校验
      //modify tongmeng 2005-11-04
      //修改原因 去掉对本身展业类型身份证的校验,同一个身份证只能对应一个在职的工号
      SQLwithBindVariables sqlbv12=new SQLwithBindVariables();
      String sql="select count(*) from laagent where idno='?tIDNo?' and agentstate<'03' ";
      sqlbv12.put("tIDNo", tIDNo);
      String sAgentState = this.mLAAgentSchema.getAgentState();
      //tongmeng 2006-11-09 modify
      //增员时对二次增员不能加代理人编码的条件
      //初始信息修改需要加此条件
      if((sAgentState!=null&&!sAgentState.equals("02"))||this.mOperate.indexOf("UPDATE") != -1)
      {
        if (mLAAgentSchema.getAgentCode() !=null&&!mLAAgentSchema.getAgentCode() .equals("") )
        {
          sql=sql+" and agentcode<>'?agentcode?' ";
          sqlbv12.put("agentcode", mLAAgentSchema.getAgentCode());
        }
      }
      sqlbv12.sql(sql);



      ExeSQL tExeSQL=new ExeSQL();
      logger.debug(sql) ;
      String result=tExeSQL.getOneValue(sqlbv12) ;
      if (Integer.parseInt(result)>0 )
      {
        CError tError = new CError();
        tError.moduleName = "ALAgentBL";
        tError.functionName = "checkData";
        tError.errorMessage = "身份证号"+tIDNo+"已经使用过";
        this.mErrors.addOneError(tError);
        return false;
      }
      if(this.mOperate .equals("INSERT||MAIN")){
      String tsql="select count(assumoney) from laassumoney  a where branchtype='1' and ConfMakeDate is not null and AssuCheckState='0'"
    	   +" and agentname='?agentname?'and IdType='?IdType?' and IDNo='?IDNo?'and not exists " +
    	"(select 1 from lajagetassumoney where a.SerialNo=SerialNo)" ; 
      SQLwithBindVariables sqlbv13=new SQLwithBindVariables();
      sqlbv13.sql(tsql);
      sqlbv13.put("agentname", mLAAgentSchema.getName());
      sqlbv13.put("IdType", mLAAgentSchema.getIDType());
      sqlbv13.put("IDNo", mLAAgentSchema.getIDNo());
     ExeSQL tSQL=new ExeSQL();
     String tresult=tSQL.getOneValue(sqlbv13);
     if (Integer.parseInt(tresult)<=0 )
     {
    	 CError tError = new CError();
         tError.moduleName = "ALAgentBL";
         tError.functionName = "checkData";
         tError.errorMessage = "该代理人没有交押金";
         this.mErrors.addOneError(tError);
         return false; 
    	 
     }
     
     if(sAgentState!=null&&sAgentState.equals("01"))
   {
    	 
    //只有A05及以上职级才参与校验 
     if (mLATreeSchema.getAgentGrade().compareTo("A05")>=0)
     {	 
      String agentcode1=(String)mTransferData.getValueByName("RearAgent");
      String agentcode2=(String)mTransferData.getValueByName("RearDepartAgent");
      String agentcode3=(String)mTransferData.getValueByName("RearSuperintAgent");
      String agentcode4=(String)mTransferData.getValueByName("RearAreaSuperintAgent");
  //modify 2009-12-29 由于在同一次授权当中,有可能有被增员人和育成人同时存在,此时育成人还没有入司,没有工号,因此原来的育成人代码,都需要改为姓名来做校验.
  
      SQLwithBindVariables sqlbv22=new SQLwithBindVariables();
      sqlbv22.sql("select name from laagent where agentcode='?agentcode1?'");
      sqlbv22.put("agentcode1", agentcode1);
      SQLwithBindVariables sqlbv23=new SQLwithBindVariables();
      sqlbv23.sql("select name from laagent where agentcode='?agentcode2?'");
      sqlbv23.put("agentcode2", agentcode2);
      SQLwithBindVariables sqlbv24=new SQLwithBindVariables();
      sqlbv24.sql("select name from laagent where agentcode='?agentcode3?'");
      sqlbv24.put("agentcode3", agentcode3);
      SQLwithBindVariables sqlbv25=new SQLwithBindVariables();
      sqlbv25.sql("select name from laagent where agentcode='?agentcode4?'");
      sqlbv25.put("agentcode4", agentcode4);
      agentcode1= tSQL.getOneValue(sqlbv22);
      agentcode2= tSQL.getOneValue(sqlbv23);
      agentcode3= tSQL.getOneValue(sqlbv24);
      agentcode4= tSQL.getOneValue(sqlbv25);
   logger.debug("agentcode1"+agentcode1);
   logger.debug("agentcode2"+agentcode2); 
   logger.debug("agentcode3"+agentcode3);
   logger.debug("agentcode4"+agentcode4);
      String msql="select * from laaddcontrol where Zjno='?Zjno?'and state='0' ";
      SQLwithBindVariables sqlbv14=new SQLwithBindVariables();
      sqlbv14.sql(msql);
      sqlbv14.put("Zjno", mLAAgentSchema.getIDNo());
     SSRS  tssrs=new SSRS();
     tssrs=tSQL.execSQL(sqlbv14);
      if (tssrs.MaxRow==0)
      {
    	  CError tError = new CError();
          tError.moduleName = "ALAgentBL";
          tError.functionName = "checkData";
          tError.errorMessage = "该代理人未经授权,不允许做增员";
          this.mErrors.addOneError(tError);
          return false; 
    	  
    	  
      } 
      
      else
      
      {int i;
    	for ( i=1;i<=tssrs.MaxRow;i++)
    		
    //前4个字段必录项,后4个字段增员录入不为空的情况下再做比较.如果有一个不成		
    	{
    	//需要将M职级转换为A职级
    		
      String agentgrade="select gradecode from laagentgrade where gradecode like 'A%' and branchtype='1'and gradeid =(select max(gradeid) from laagentgrade where gradecode1='?gradecode1?')";
      SQLwithBindVariables sqlbv15=new SQLwithBindVariables();
      sqlbv15.sql(agentgrade);
      sqlbv15.put("gradecode1", tssrs.GetText(i, 4));
        String aagentgrade= tSQL.getOneValue(sqlbv15);      		
    	
                logger.debug(tssrs.GetText(i, 2));
    		
    		
    		logger.debug(mLAAgentSchema.getManageCom());
    		logger.debug(tssrs.GetText(i, 2).equals(mLAAgentSchema.getManageCom()));
        	logger.debug(tssrs.GetText(i, 3).equals(mLAAgentSchema.getName()));
        	logger.debug(aagentgrade.equals(mLATreeSchema.getAgentGrade()));
        	logger.debug(tssrs.GetText(i, 5).equals(mLAAgentSchema.getIDNo()));
        	logger.debug(agentcode1!=null&&!agentcode1.equals("")&&!agentcode1.equals(tssrs.GetText(i, 6)));
        	logger.debug(agentcode2!=null&&!agentcode2.equals("")&&!agentcode2.equals(tssrs.GetText(i, 7)));
        	logger.debug(agentcode3!=null&&!agentcode3.equals("")&&!agentcode3.equals(tssrs.GetText(i, 8)));
        	logger.debug(agentcode4!=null&&!agentcode4.equals("")&&!agentcode4.equals(tssrs.GetText(i, 9)));
        	
//        	||(agentcode1!=null&&!agentcode1.equals("")&&!agentcode1.equals(tssrs.GetText(i, 6)))||(agentcode2!=null&&!agentcode2.equals("")&&!agentcode2.equals(tssrs.GetText(i, 7)))
//    		||(agentcode3!=null&&!agentcode3.equals("")&&!agentcode3.equals(tssrs.GetText(i, 8)))||(agentcode4!=null&&!agentcode4.equals("")&&!agentcode4.equals(tssrs.GetText(i, 9)))
//        	||!aagentgrade.equals(mLATreeSchema.getAgentGrade())
        	if (
    				  !tssrs.GetText(i, 2).equals(mLAAgentSchema.getManageCom())
    				||!tssrs.GetText(i, 3).equals(mLAAgentSchema.getName())
    				
    				||!tssrs.GetText(i, 5).equals(mLAAgentSchema.getIDNo())
    		   )
    	
	    	   { 
    			    logger.debug("11111111111");
	    			continue;
	    	   }
	    	   else
	    	   {
	    		   logger.debug("222222222222");
	    		   break;
	    	   }
    		}		
    	  
    	if (i>tssrs.MaxRow)
    		
    	{
    		 CError tError = new CError();
             tError.moduleName = "ALAgentBL";
             tError.functionName = "checkData";
             tError.errorMessage = "该代理人未经授权,不允许做增员";
             this.mErrors.addOneError(tError);
             return false; 
    		
    		
    	}	
    	
      }
     
     }
      }
      }
     
    //初始代理人信息修改校验  
      else  if (this.mOperate.equals("UPDATE||ALL"))
    	  
      {   ExeSQL tSQL=new ExeSQL();
	    SSRS  tSSRS=new SSRS();
	    String agentsql="select a.name,a.idno,a.bankaccno from laagent a where a.agentcode='?agentcode?'";
	    SQLwithBindVariables sqlbv16=new SQLwithBindVariables();
	    sqlbv16.sql(agentsql);
	    sqlbv16.put("agentcode", mLAAgentSchema.getAgentCode());
	      
	      tSSRS=tSQL.execSQL(sqlbv16);
	      
	    if (!tSSRS.GetText(1,1).equals(mLAAgentSchema.getName())||!tSSRS.GetText(1,2).equals(mLAAgentSchema.getIDNo())||!tSSRS.GetText(1,3).equals(mLAAgentSchema.getBankAccNo())){	    
    	    String msql="select * from laagentmodctl where agentcode='?agentcode?'and state='0' and modtype='初始信息' ";
    	    SQLwithBindVariables sqlbv17=new SQLwithBindVariables();
    	    sqlbv17.sql(msql);
    	    sqlbv17.put("agentcode", mLAAgentSchema.getAgentCode());
    	
    	    tSSRS=tSQL.execSQL(sqlbv17);
    	      if (tSSRS.MaxRow==0)
    	      {
    	    	  CError tError = new CError();
    	          tError.moduleName = "ALAgentBL";
    	          tError.functionName = "checkData";
    	          tError.errorMessage = "本操作未经授权”，不予以保存相关修改内容";
    	          this.mErrors.addOneError(tError);
    	          return false; 
    	    	  
    	    	  
    	     }  
    	      
	    }    
    	      
    	      
    	  
      }
     //否则为代理人信息修改校验 
      else 
      {
      SSRS tSSRS=new SSRS();
      ExeSQL tSQL=new ExeSQL();
      String agentsql="select a.name,a.idno,a.bankaccno from laagent a where a.agentcode='?agentcode?'";
      SQLwithBindVariables sqlbv18=new SQLwithBindVariables();
      sqlbv18.sql(agentsql);
      sqlbv18.put("agentcode", mLAAgentSchema.getAgentCode());
      
      tSSRS=tSQL.execSQL(sqlbv18);
      
    if (!tSSRS.GetText(1,1).equals(mLAAgentSchema.getName())||!tSSRS.GetText(1,2).equals(mLAAgentSchema.getIDNo())||!tSSRS.GetText(1,3).equals(mLAAgentSchema.getBankAccNo())){	  
  	    String msql="select * from laagentmodctl where agentcode='?agentcode?'and state='0' and modtype='基础信息' ";
  	    SQLwithBindVariables sqlbv19=new SQLwithBindVariables();
  	    sqlbv19.sql(msql);
  	    sqlbv19.put("agentcode", mLAAgentSchema.getAgentCode());
  	 
  
  	     tSSRS=tSQL.execSQL(sqlbv19);
  	      if (tSSRS.MaxRow==0)
  	      {
  	    	  CError tError = new CError();
  	          tError.moduleName = "ALAgentBL";
  	          tError.functionName = "checkData";
  	          tError.errorMessage = "本操作未经授权”，不予以保存相关修改内容";
  	          this.mErrors.addOneError(tError);
  	          return false; 
  	    	  
  	    	  
  	      }  
    } 
  	        
    	  
      }	  
    }
    
    LAAgentBlacklistDB tLAAgentBlackDB = new LAAgentBlacklistDB();
    tLAAgentBlackDB.setName(this.mLAAgentSchema.getName());
    tLAAgentBlackDB.setSex(this.mLAAgentSchema.getSex());
    tLAAgentBlackDB.setIDNo(this.mLAAgentSchema.getIDNo());
    LAAgentBlacklistSet tLAAgentBlackSet = tLAAgentBlackDB.query();
    if (tLAAgentBlackDB.mErrors.needDealError()) {
      CError tError = new CError();
      tError.moduleName = "ALAgentBL";
      tError.functionName = "checkData";
      tError.errorMessage = "查询黑名单表出错！";
      this.mErrors.addOneError(tError);
      return false;
    }
    if (tLAAgentBlackSet.size() > 0) {
      CError tError = new CError();
      tError.moduleName = "ALAgentBL";
      tError.functionName = "checkData()";
      tError.errorMessage = "该代理人已列入黑名单中，无法增员！";
      this.mErrors.addOneError(tError);
      return false;
    }

    return true;
  }

//佟盟2005-03-08增加
//函数功能：扫描育成链，返回育成链中冒号的个数
  private int ScanAscriptSeires(String tAscriptSeries){
    int n,i;
    i=0;
    for(n=0;n<=tAscriptSeries.length()-1;n++){
      if(tAscriptSeries.substring(n,n+1).equals(":"))
        i++;
    }
    return i;
  }
  //佟盟2005-03-08增加
  //记录育成链中最后一个冒号的位置
  private int Scanposition(String tAscriptSeries){
    int i;
    for(i=tAscriptSeries.length()-1;i>=0;i--)
      if(tAscriptSeries.substring(i,i+1).equals(":"))
        break;
    return i;
  }
  //佟盟2005-03-08增加
  //函数功能：重新拼接育成链，如果相应部分的育成人为空的话，则在空的位置上增加"**"
  private String changeAscriptSeries(String tAscriptSeries){
    String newAscriptSeries,temp,str,str1,str2;
    int count0,n,i;
    str=tAscriptSeries;
    logger.debug("开始拼接育成链："+tAscriptSeries);
    if(str.indexOf(":")==-1){
      return tAscriptSeries;
    } else{
      str2=str.substring(str.lastIndexOf(":"),str.length());
      str1=str.substring(0,str.lastIndexOf(":"));
      count0=ScanAscriptSeires(str);
      for(n=0;n<count0;n++){
        i=Scanposition(str1);
        if(!str1.equals(":")&&!str1.equals("")){
          if(i+1==str1.length())
            temp="**";
          else
            temp=str1.substring(i+1,str1.length());}
        else temp="**";
        str2=":"+temp+str2;
        if(str1.indexOf(":")!=-1)
          str1=str1.substring(0,i);
        else str1="";
      }
      logger.debug("拼接结果:" +str2.substring(str2.indexOf(":")+1));
      return str2.substring(str2.indexOf(":")+1);
    }
  }
  //佟盟2005-03-09增加
  //查询代理人该职级下的行政信息附属表中的记录
  private SSRS decideIfDateIsNull(String tAgentCode,String tAgentGrade){
    String tsql="select makedate,maketime from latreeaccessory "
               +"where agentcode='?tAgentCode?' and agentgrade='?tAgentGrade?'";
    SQLwithBindVariables sqlbv20=new SQLwithBindVariables();
    sqlbv20.sql(tsql);
    sqlbv20.put("tAgentCode", tAgentCode);
    sqlbv20.put("tAgentGrade", tAgentGrade);
    SSRS tSSRS=new SSRS();
    logger.debug("执行SQL："+tsql);
    ExeSQL tExeSQL=new ExeSQL();
    tSSRS=tExeSQL.execSQL(sqlbv20);
    return tSSRS;
  }
  /**
   * tongmeng 2006-04-11 add
   * 根据代理人职级判断育成级别
   */
  private String getRelaLevelByAgentGrade(String sAgentGrade)
  {
    String sRelaLevel = "";
    if(sAgentGrade==null||sAgentGrade.equals(""))
      return "";
    else if(sAgentGrade.equals("A04")||sAgentGrade.equals("A05"))
      sRelaLevel = "01";
    else if(sAgentGrade.equals("A06")||sAgentGrade.equals("A07"))
      sRelaLevel = "02";
    else if(sAgentGrade.equals("A08"))
       sRelaLevel = "03";
    else if(sAgentGrade.equals("A09"))
       sRelaLevel = "04";
    return sRelaLevel;
  }
  //查询数据表中的育成或者增员记录
  private LARelationSchema queryLARelation(String sAgentCode,String sRelaType,String sRelaLevel,String sRelaGens)
  {
    LARelationSet dLARelationSet = new LARelationSet();
    LARelationDB dLARelationDB = new LARelationDB();
    String sSql = "select * from larelation where agentcode='?sAgentCode?' "
                + " and relatype='?sRelaType?' and RelaGens = ?sRelaGens? "
                + " and relalevel='?sRelaLevel?' ";
    SQLwithBindVariables sqlbv21=new SQLwithBindVariables();
    sqlbv21.sql(sSql);
    sqlbv21.put("sAgentCode", sAgentCode);
    sqlbv21.put("sRelaType", sRelaType);
    sqlbv21.put("sRelaGens", sRelaGens);
    sqlbv21.put("sRelaLevel", sRelaLevel);
    logger.debug("sSql:"+sSql);
    dLARelationSet = dLARelationDB.executeQuery(sqlbv21);
    if(dLARelationSet.size()<=0)
      return new LARelationSchema();
    else
      return dLARelationSet.get(1);
  }

  /**
   *
   * @param cAgentCode
   * @param tEdorNo
   * @return
   */
  private boolean prepareCopyAgent(String cAgentCode,String tEdorNo)
  {
    Reflections  tReflections = new Reflections();
    LAAgentDB tLAAgentDB = new LAAgentDB();
    tLAAgentDB.setAgentCode(cAgentCode);
    if (!tLAAgentDB.getInfo())
    {
      CError tError = new CError();
      tError.moduleName = "ALAAgentBL";
      tError.functionName = "submitDat";
      tError.errorMessage ="查询代理人原信息失败!";
      this.mErrors .addOneError(tError);
      return false;
    }
    try{
      tReflections.transFields(this.mLAAgentBSchema,tLAAgentDB.getSchema());
      this.mLAAgentBSchema.setAgentCode(cAgentCode);
      this.mLAAgentBSchema.setEdorNo(tEdorNo);
      this.mLAAgentBSchema.setEdorType("04");//转储类型:二次增员
      }catch(Exception ex)
      {
        // @@错误处理
        CError tError =new CError();
        tError.moduleName="LAAgentSecondBL";
        tError.functionName="prepareCopyTree";
        tError.errorMessage="代理人基本信息备份出错。";
        this.mErrors .addOneError(tError) ;
        return false;
      }
      return true;
  }

  /**
   *
   * @param cAgentCode
   * @return
   */
  private boolean prepareCopyTree(String cAgentCode,String tEdorNo)
  {
    Reflections  tReflections = new Reflections();
    LATreeDB tLATreeDB = new LATreeDB();
    tLATreeDB.setAgentCode(cAgentCode);
    if (!tLATreeDB.getInfo())
    {
      // @@错误处理
      this.mErrors.copyAllErrors(tLATreeDB.mErrors);
      CError tError = new CError();
      tError.moduleName = "LAAgentSecondBL";
      tError.functionName = "submitDat";
      tError.errorMessage = "数据提交失败!";
      this.mErrors.addOneError(tError) ;
      return false;
    }
    try
    {
      tReflections.transFields(this.mLATreeBSchema,tLATreeDB.getSchema());
      this.mLATreeBSchema.setAstartDate(tLATreeDB.getAstartDate());
      this.mLATreeBSchema.setEdorNO(tEdorNo);
      this.mLATreeBSchema.setMakeDate2(tLATreeDB.getMakeDate());
      this.mLATreeBSchema.setMakeTime2(tLATreeDB.getMakeTime());
      this.mLATreeBSchema.setManageCom(tLATreeDB.getManageCom());
      this.mLATreeBSchema.setModifyDate2(tLATreeDB.getModifyDate());
      this.mLATreeBSchema.setModifyTime2(tLATreeDB.getModifyTime());
      this.mLATreeBSchema.setOperator2(tLATreeDB.getOperator());
      this.mLATreeBSchema.setRemoveType("04");//转储类型:二次增员
    }
    catch(Exception ex)
    {
      // @@错误处理
      CError tError =new CError();
      tError.moduleName="LAAgentSecondBL";
      tError.functionName="prepareCopyTree";
      tError.errorMessage="行政信息备份出错。";
      this.mErrors .addOneError(tError) ;
      return false;
    }
    return true;
  }
}
