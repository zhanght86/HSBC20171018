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

public class ALABranchGroupBL {
private static Logger logger = Logger.getLogger(ALABranchGroupBL.class);
  /** 错误处理类，每个需要错误处理的类中都放置该类 */
  public CErrors mErrors = new CErrors();
  private VData mResult = new VData();
  /** 往后面传输数据的容器 */
  private VData mInputData;
  /** 全局数据 */
  private GlobalInput mGlobalInput = new GlobalInput();
  /** 数据操作字符串 */
  private String mOperate;
  private String currentDate = PubFun.getCurrentDate();
  private String currentTime = PubFun.getCurrentTime();
  /** 业务处理相关变量 */
  private LABranchGroupSchema mLABranchGroupSchema = new LABranchGroupSchema();
  private LABranchGroupBSchema mLABranchGroupBSchema = new LABranchGroupBSchema();
  private LATreeSchema mLATreeSchema = new LATreeSchema();
  private LATreeBSchema mLATreeBSchema = new LATreeBSchema();
  private LAAgentSchema mLAAgentSchema = new LAAgentSchema();
  private LATreeSet mLATreeSet = new LATreeSet();
  private LAAgentSet mLAAgentSet = new LAAgentSet();
  private LATreeAccessorySet mLATreeAccessorySet = new LATreeAccessorySet();
  private LABranchGroupSet mLABranchGroupSet = new LABranchGroupSet();

  public ALABranchGroupBL() {
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
    //tongmeng 2005-12-19 add
    //增加校验方法
    if(!checkData())
    {
      return false;
    }
    if (mOperate.equals("INSERT||MAIN")) {
      LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
      tLABranchGroupDB.setBranchAttr(mLABranchGroupSchema.getBranchAttr());
      //tongmeng 2005-08-23 modify
      //add branchtype
      tLABranchGroupDB.setBranchType(this.mLABranchGroupSchema.getBranchType());
      tLABranchGroupDB.query();
    }

    //进行业务处理
    if (!dealData()) {
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "ALABranchGroupBL";
      tError.functionName = "submitData";
      tError.errorMessage = "数据处理失败ALABranchGroupBL-->dealData!";
      this.mErrors.addOneError(tError);
      return false;
    }
    //准备往后台的数据
    if (!prepareOutputData())
      return false;
    if (this.mOperate.equals("QUERY||MAIN")) {
      this.submitquery();
    }
    else {
      logger.debug("Start ALABranchGroupBL Submit...");
      ALABranchGroupBLS tALABranchGroupBLS = new ALABranchGroupBLS();
      tALABranchGroupBLS.submitData(mInputData, cOperate);
      logger.debug("End ALABranchGroupBL Submit...");
      //如果有需要处理的错误，则返回
      if (tALABranchGroupBLS.mErrors.needDealError()) {
        // @@错误处理
        this.mErrors.copyAllErrors(tALABranchGroupBLS.mErrors);
        CError tError = new CError();
        tError.moduleName = "ALABranchGroupBL";
        tError.functionName = "submitDat";
        tError.errorMessage = "数据提交失败!";
        this.mErrors.addOneError(tError);
        return false;
      }
    }
    mInputData = null;
    return true;
  }
  /**
   * 校验方法
   */
  private boolean checkData()
  {
    LABranchGroupSchema aLABranchGroupSchema = new LABranchGroupSchema();
    aLABranchGroupSchema.setSchema(this.mLABranchGroupSchema);
    String aEnddate = aLABranchGroupSchema.getEndDate();
    String aEndFlag = aLABranchGroupSchema.getEndFlag();
    logger.debug("aEnddate:"+aEnddate);
    logger.debug("aEndFlag:"+aEndFlag);
    if(aEnddate!=null&&!aEnddate.equals("")&&aEndFlag.equals("N"))
    {
      CError tError = new CError();
      tError.moduleName = "ALABranchGroupBL";
      tError.functionName = "checkData()";
      tError.errorMessage = "机构未停业但停业日期不为空!";
      this.mErrors.addOneError(tError);
      return false;
    }
    return true;
  }

  /**
   * 根据前面的输入数据，进行BL逻辑处理
   * 如果在处理过程中出错，则返回false,否则返回true
   */
  private boolean dealData() {
    boolean tReturn = true;
    int tCount = 0;
    String tAgentGroup = "";
    if (this.mOperate.equals("INSERT||MAIN")) {
      logger.debug("进入新增模块！！");
      LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
      tLABranchGroupDB.setBranchAttr(this.mLABranchGroupSchema.getBranchAttr().
                                     trim());
      //tongmeng 2005-08-23 modify
      //add branchtype
      tLABranchGroupDB.setBranchType(this.mLABranchGroupSchema.getBranchType());
      LABranchGroupSet tLABranchGroupSet = tLABranchGroupDB.query();
      if (tLABranchGroupSet.size() != 0) {
        CError tError = new CError();
        tError.moduleName = "ALABranchGroupBL";
        tError.functionName = "insertData()";
        tError.errorMessage = "行政信息表中已经存在该管理机构的信息!";
        this.mErrors.addOneError(tError);
        return false;
      }

      //修改：2004-06-17 LL
      //修改原因：增加机构代码与对应机构级别的校验
      String tFlag = this.judgeGroupLevel(this.mLABranchGroupSchema.
                                          getBranchAttr(),
                                          this.mLABranchGroupSchema.
                                          getBranchLevel());
      logger.debug("调用judgeGroupLevel函数返回结果为：" + tFlag);
      //查询信息时出错
      if (tFlag.equals("0")) {
        CError tError = new CError();
        tError.moduleName = "ALABranchGroupBL";
        tError.functionName = "dealData()";
        tError.errorMessage = "查询机构信息信息出错！";
        this.mErrors.addOneError(tError);
        return false;
      }
      //机构代码对应级别不正确
      if (tFlag.equals("2")) {
        CError tError = new CError();
        tError.moduleName = "ALABranchGroupBL";
        tError.functionName = "dealData()";
        tError.errorMessage = "机构代码与机构级别不一致！";
        this.mErrors.addOneError(tError);
        return false;
      }

      //校验管理机构和展业机构代码的前八位是否一致
      PubCheckField checkField1 = new PubCheckField();
      VData cInputData = new VData();
      TransferData tTransferData = new TransferData();
      //设置计算时要用到的参数值
      String tManageCom = mLABranchGroupSchema.getManageCom();
      String tBranchAttr = mLABranchGroupSchema.getBranchAttr();
      String tBranchType=mLABranchGroupSchema.getBranchType() ;
      tTransferData = new TransferData();
      tTransferData.setNameAndValue("BranchType", tBranchType);
      tTransferData.setNameAndValue("BranchAttr", tBranchAttr);
      tTransferData.setNameAndValue("ManageCom", tManageCom);
      LMCheckFieldDB tLMCheckFieldDB = new LMCheckFieldDB();
      LMCheckFieldSet tLMCheckFieldSet = new LMCheckFieldSet();
      String tSql = "select * from lmcheckfield where riskcode = '000000'"
          + "  and fieldname = 'ALABranchGroupBL' order by serialno asc";
      SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
	  sqlbv7.sql(tSql);
      tLMCheckFieldSet = tLMCheckFieldDB.executeQuery(sqlbv7);
      cInputData.add(tTransferData);
      cInputData.add(tLMCheckFieldSet);
      if (!checkField1.submitData(cInputData, "CKBYSET")) {
        logger.debug("Enter Error Field!");
        //此判断是用来区分是程序处理过程中报的错误，还是校验时报的错误
        if (checkField1.mErrors.needDealError()) {
          this.mErrors.copyAllErrors(checkField1.mErrors);
          return false;
        }
        else {
          VData t = checkField1.getResultMess();
          CError tError = new CError();
          tError.moduleName = "ALABranchGroupBL";
          tError.functionName = "dealData()";
          tError.errorMessage = t.get(0).toString();
          this.mErrors.addOneError(tError);
          return false;
        }
      }

      //生成内部机构代码
      tAgentGroup = PubFun1.CreateMaxNo("AgentGroup", 12);
      logger.debug("AgentGroup:" + tAgentGroup);
      //确定出上级机构的内部代码
      String tUpBranch = this.mLABranchGroupSchema.getBranchAttr().trim();
      String tBranchLevel = this.mLABranchGroupSchema.getBranchLevel();
      logger.debug("branchlevel:" + Integer.parseInt(tBranchLevel));
      tUpBranch = getUpBranch(tUpBranch, tBranchLevel);
      if (tUpBranch == null)
        return false;
      logger.debug("上级机构AgentGroup:" + tUpBranch);
      //修改：2004-06-17 LL
      //修改原因：增加对上级机构是否有直辖机构的判断
      //只有在录入属性为直辖机构时才做此判断
      if (this.mLABranchGroupSchema.getUpBranchAttr().equals("1")) {
        String tFlag2 = this.haveZXGroup(tUpBranch, tAgentGroup);
        logger.debug("调用haveZXGroup函数返回结果为：" + tFlag2);
        //查询信息时出错
        if (tFlag2.equals("0")) {
          CError tError = new CError();
          tError.moduleName = "ALABranchGroupBL";
          tError.functionName = "dealData()";
          tError.errorMessage = "查询机构信息出错！";
          this.mErrors.addOneError(tError);
          return false;
        }
        //此机构的上级机构已经有直辖机构存在
        if (tFlag2.equals("1")) {
          CError tError = new CError();
          tError.moduleName = "ALABranchGroupBL";
          tError.functionName = "dealData()";
          tError.errorMessage = "此机构的上级机构已经有直辖机构存在！";
          this.mErrors.addOneError(tError);
          return false;
        }
      }

      this.mLABranchGroupSchema.setUpBranch(tUpBranch);
      logger.debug("UpBranch:" + tUpBranch);
      this.mLABranchGroupSchema.setAgentGroup(tAgentGroup);
      this.mLABranchGroupSchema.setOperator(mGlobalInput.Operator);
      this.mLABranchGroupSchema.setMakeDate(currentDate);
      this.mLABranchGroupSchema.setMakeTime(currentTime);
      this.mLABranchGroupSchema.setModifyDate(currentDate);
      this.mLABranchGroupSchema.setModifyTime(currentTime);

    }
    if (this.mOperate.equals("UPDATE||MAIN")) {
      logger.debug("进入修改模块！");
      if (this.mLABranchGroupSchema.getAgentGroup().equals("")) {
        // @@错误处理
        CError tError = new CError();
        tError.moduleName = "ALABranchGroupBL";
        tError.functionName = "dealData()";
        tError.errorMessage = "不存在所操作的销售单位!(隐式机构代码为空)";
        this.mErrors.addOneError(tError);
        return false;
      }

      //修改：2004-06-17 LL
      //修改原因：增加机构代码与对应机构级别的校验
      String tFlag = this.judgeGroupLevel(this.mLABranchGroupSchema.
                                          getBranchAttr(),
                                          this.mLABranchGroupSchema.
                                          getBranchLevel());
      logger.debug("调用judgeGroupLevel函数返回结果为：" + tFlag);
      //查询信息时出错
      if (tFlag.equals("0")) {
        CError tError = new CError();
        tError.moduleName = "ALABranchGroupBL";
        tError.functionName = "dealData()";
        tError.errorMessage = "查询机构信息信息出错！";
        this.mErrors.addOneError(tError);
        return false;
      }
      //机构代码对应级别不正确
      if (tFlag.equals("2")) {
        CError tError = new CError();
        tError.moduleName = "ALABranchGroupBL";
        tError.functionName = "dealData()";
        tError.errorMessage = "机构代码与机构级别不一致！";
        this.mErrors.addOneError(tError);
        return false;
      }
      //校验管理机构和展业机构代码的前八位是否一致
     PubCheckField checkField1 = new PubCheckField();
     VData cInputData = new VData();
     TransferData tTransferData = new TransferData();
     //设置计算时要用到的参数值
     String tManageCom = mLABranchGroupSchema.getManageCom();
     String tBranchAttr = mLABranchGroupSchema.getBranchAttr();
     String tBranchType=mLABranchGroupSchema.getBranchType() ;
     tTransferData = new TransferData();
     tTransferData.setNameAndValue("BranchAttr", tBranchAttr);
     tTransferData.setNameAndValue("ManageCom", tManageCom);
     tTransferData.setNameAndValue("BranchType", tBranchType);
     LMCheckFieldDB tLMCheckFieldDB = new LMCheckFieldDB();
     LMCheckFieldSet tLMCheckFieldSet = new LMCheckFieldSet();
     String tSql = "select * from lmcheckfield where riskcode = '000000'"
         + "  and fieldname = 'ALABranchGroupBL' order by serialno asc";
     SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
	 sqlbv1.sql(tSql);
     tLMCheckFieldSet = tLMCheckFieldDB.executeQuery(sqlbv1);
     cInputData.add(tTransferData);
     cInputData.add(tLMCheckFieldSet);
     if (!checkField1.submitData(cInputData, "CKBYSET")) {
       logger.debug("Enter Error Field!");
       //此判断是用来区分是程序处理过程中报的错误，还是校验时报的错误
       if (checkField1.mErrors.needDealError()) {
         this.mErrors.copyAllErrors(checkField1.mErrors);
         return false;
       }
       else {
         VData t = checkField1.getResultMess();
         CError tError = new CError();
         tError.moduleName = "ALABranchGroupBL";
         tError.functionName = "dealData()";
         tError.errorMessage = t.get(0).toString();
         this.mErrors.addOneError(tError);
         return false;
       }
     }

     //修改：2004-09-14 蔡刚
        //如果置机构停业，则停业日期不能为空
        if (this.mLABranchGroupSchema.getEndFlag().equals("Y")&&
          (this.mLABranchGroupSchema.getEndDate() ==null || this.mLABranchGroupSchema.getEndDate() .trim() .equals("") ))
        {
          CError tError = new CError();
          tError.moduleName = "ALABranchGroupBL";
          tError.functionName = "dealData()";
          tError.errorMessage = "机构置停业时停业日期不能为空!";
          this.mErrors.addOneError(tError);
          return false;
        }
        if (this.mLABranchGroupSchema.getEndDate() !=null && this.mLABranchGroupSchema.getBranchType() .equals("1") )
        {
          if (this.mLABranchGroupSchema.getEndFlag()==null||this.mLABranchGroupSchema.getEndFlag().equals("N") )
          {
            CError tError = new CError();
            tError.moduleName = "ALABranchGroupBL";
            tError.functionName = "dealData()";
            tError.errorMessage = "机构未停业，停业日期必须为空!";
            this.mErrors.addOneError(tError);
            return false;

          }
        }


      //修改：2004-06-07 LL
      //增加机构停业的校验：只有机构下没有人才能停业
      //只处理个人情况：BranchType = 1的情况
      if (this.mLABranchGroupSchema.getEndFlag().equals("Y")
          && this.mLABranchGroupSchema.getBranchType().equals("1")) {

        logger.debug("BranchAttr:" +
                           this.mLABranchGroupSchema.getBranchAttr());
        logger.debug("AgentGroup:" +
                           this.mLABranchGroupSchema.getAgentGroup());
        logger.debug("BranchType:" +
                           this.mLABranchGroupSchema.getBranchType());
        String tFlag1 = this.haveMember(this.mLABranchGroupSchema.getBranchAttr(),
                                        this.mLABranchGroupSchema.getBranchType());
        //查询信息时出错
        if (tFlag1.equals("0")) {
          CError tError = new CError();
          tError.moduleName = "ALABranchGroupBL";
          tError.functionName = "dealData()";
          tError.errorMessage = "查询机构信息信息出错！";
          this.mErrors.addOneError(tError);
          return false;
        }
        //机构下有未离职人员
        if (tFlag1.equals("2")) {
          CError tError = new CError();
          tError.moduleName = "ALABranchGroupBL";
          tError.functionName = "dealData()";
          tError.errorMessage = "此机构下还有未离职人员，不能对此机构停业！";
          this.mErrors.addOneError(tError);
          return false;
        }
      }

      //获取‘入机日期和时间'
      LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
      tLABranchGroupDB.setAgentGroup(this.mLABranchGroupSchema.getAgentGroup());
      if (!tLABranchGroupDB.getInfo()) {
        // @@错误处理
        this.mErrors.copyAllErrors(tLABranchGroupDB.mErrors);
        CError tError = new CError();
        tError.moduleName = "ALABranchGroupBL";
        tError.functionName = "dealData()";
        tError.errorMessage = "不存在所要修改的机构!";
        this.mErrors.addOneError(tError);
        return false;
      }

      LABranchGroupSchema tLABranchGroupSchema = new LABranchGroupSchema();
      tLABranchGroupSchema = tLABranchGroupDB.getSchema();
      //备份操作
//      LABranchGroupBSchema tLABranchGroupBSchema=new LABranchGroupBSchema();
      Reflections tReflections = new Reflections();
      tReflections.transFields(mLABranchGroupBSchema, tLABranchGroupSchema);
      String tEdorNo = PubFun1.CreateMaxNo("EdorNo", 20);
      //蔡刚2004-08-18添加，机构主管从库中取
      if(mLABranchGroupBSchema.getName()==null||mLABranchGroupBSchema.getName().equals(""))
        mLABranchGroupBSchema.setName(" ");
      mLABranchGroupBSchema.setEdorNo(tEdorNo);
      mLABranchGroupBSchema.setEdorType("02"); //修改操作
      mLABranchGroupBSchema.setMakeDate2(tLABranchGroupSchema.getMakeDate());
      mLABranchGroupBSchema.setMakeTime2(tLABranchGroupSchema.getMakeTime());
      mLABranchGroupBSchema.setModifyDate2(tLABranchGroupSchema.getModifyDate());
      mLABranchGroupBSchema.setModifyTime2(tLABranchGroupSchema.getModifyTime());
      mLABranchGroupBSchema.setOperator2(tLABranchGroupSchema.getOperator());
      mLABranchGroupBSchema.setMakeDate(currentDate);
      mLABranchGroupBSchema.setMakeTime(currentTime);
      mLABranchGroupBSchema.setModifyDate(currentDate);
      mLABranchGroupBSchema.setModifyTime(currentTime);
      mLABranchGroupBSchema.setOperator(this.mGlobalInput.Operator);

      //重新设置机构的上级机构（因为有可能修改机构代码）
      String tUpBranch = this.mLABranchGroupSchema.getBranchAttr().trim();
      String tBranchLevel = this.mLABranchGroupSchema.getBranchLevel();
      logger.debug("branchlevel:" + Integer.parseInt(tBranchLevel));
      tUpBranch = getUpBranch(tUpBranch, tBranchLevel);
      if (tUpBranch == null)
        return false;

      logger.debug("上级机构AgentGroup:" + tUpBranch);
      //修改：2004-06-17 LL
      //修改原因：增加对上级机构是否有直辖机构的判断
      //只有在录入属性为直辖机构时才做此判断
      if (this.mLABranchGroupSchema.getUpBranchAttr().equals("1")) {
        String tFlag2 = this.haveZXGroup(tUpBranch,
                                         this.mLABranchGroupSchema.getAgentGroup());
        logger.debug("调用haveZXGroup函数返回结果为：" + tFlag2);
        //查询信息时出错
        if (tFlag2.equals("0")) {
          CError tError = new CError();
          tError.moduleName = "ALABranchGroupBL";
          tError.functionName = "dealData()";
          tError.errorMessage = "查询机构信息出错！";
          this.mErrors.addOneError(tError);
          return false;
        }
        //此机构的上级机构已经有直辖机构存在
        if (tFlag2.equals("1")) {
          CError tError = new CError();
          tError.moduleName = "ALABranchGroupBL";
          tError.functionName = "dealData()";
          tError.errorMessage = "此机构的上级机构已经有直辖机构存在！";
          this.mErrors.addOneError(tError);
          return false;
        }
      }
      this.mLABranchGroupSchema.setBranchManager(tLABranchGroupSchema.
                                                 getBranchManager());
      this.mLABranchGroupSchema.setBranchManagerName(tLABranchGroupSchema.
          getBranchManagerName());
      this.mLABranchGroupSchema.setUpBranch(tUpBranch);
      this.mLABranchGroupSchema.setMakeDate(tLABranchGroupDB.getMakeDate());
      this.mLABranchGroupSchema.setMakeTime(tLABranchGroupDB.getMakeTime());
      this.mLABranchGroupSchema.setModifyDate(currentDate);
      this.mLABranchGroupSchema.setModifyTime(currentTime);
      this.mLABranchGroupSchema.setOperator(mGlobalInput.Operator);
    }

    if (this.mOperate.equals("DELETE||MAIN")) {
      if (this.mLABranchGroupSchema.getAgentGroup().equals("")) {
        // @@错误处理
        CError tError = new CError();
        tError.moduleName = "ALABranchGroupBL";
        tError.functionName = "dealData()";
        tError.errorMessage = "不存在所操作的销售单位!(隐式机构代码为空)";
        this.mErrors.addOneError(tError);
        return false;
      }
      //获取‘入机日期和时间'
      LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
      tLABranchGroupDB.setAgentGroup(this.mLABranchGroupSchema.getAgentGroup());
      if (!tLABranchGroupDB.getInfo()) {
        // @@错误处理
        this.mErrors.copyAllErrors(tLABranchGroupDB.mErrors);
        CError tError = new CError();
        tError.moduleName = "ALABranchGroupBL";
        tError.functionName = "dealData()";
        tError.errorMessage = "不存在所要修改的机构!";
        this.mErrors.addOneError(tError);
        return false;
      }

      LABranchGroupSchema tLABranchGroupSchema = new LABranchGroupSchema();
      tLABranchGroupSchema = tLABranchGroupDB.getSchema();
      //备份操作
//      LABranchGroupBSchema tLABranchGroupBSchema=new LABranchGroupBSchema();
      Reflections tReflections = new Reflections();
      tReflections.transFields(mLABranchGroupBSchema, tLABranchGroupSchema);
      String tEdorNo = PubFun1.CreateMaxNo("EdorNo", 20);
      mLABranchGroupBSchema.setEdorNo(tEdorNo);
      mLABranchGroupBSchema.setEdorType("02"); //修改操作
      mLABranchGroupBSchema.setMakeDate2(tLABranchGroupSchema.getMakeDate());
      mLABranchGroupBSchema.setMakeTime2(tLABranchGroupSchema.getMakeTime());
      mLABranchGroupBSchema.setModifyDate2(tLABranchGroupSchema.getModifyDate());
      mLABranchGroupBSchema.setModifyTime2(tLABranchGroupSchema.getModifyTime());
      mLABranchGroupBSchema.setOperator2(tLABranchGroupSchema.getOperator());
      mLABranchGroupBSchema.setMakeDate(currentDate);
      mLABranchGroupBSchema.setMakeTime(currentTime);
      mLABranchGroupBSchema.setModifyDate(currentDate);
      mLABranchGroupBSchema.setModifyTime(currentTime);
      mLABranchGroupBSchema.setOperator(this.mGlobalInput.Operator);

    }
    tReturn = true;
    return tReturn;
  }

  /**
   * 从输入数据中得到所有对象
   *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
   */
  private boolean getInputData(VData cInputData) {
    this.mLABranchGroupSchema.setSchema( (LABranchGroupSchema) cInputData.
                                        getObjectByObjectName(
        "LABranchGroupSchema", 0));
    this.mGlobalInput.setSchema( (GlobalInput) cInputData.getObjectByObjectName(
        "GlobalInput", 0));
    if (this.mGlobalInput == null) {
      CError tError = new CError();
      tError.moduleName = "ALABranchGroupBL";
      tError.functionName = "getInputData";
      tError.errorMessage = "mGlobalInput为空！";
      this.mErrors.addOneError(tError);
      return false;
    }
    if (this.mLABranchGroupSchema == null) {
      CError tError = new CError();
      tError.moduleName = "ALABranchGroupBL";
      tError.functionName = "getInputData";
      tError.errorMessage = "mLABranchGroupSchema为空！";
      this.mErrors.addOneError(tError);
      return false;
    }
    return true;
  }

  /**
   * 准备往后层输出所需要的数据
   * 输出：如果准备数据时发生错误则返回false,否则返回true
   */
  private boolean submitquery() {
    this.mResult.clear();
    logger.debug("Start ALABranchGroupBLQuery Submit...");
    LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
    tLABranchGroupDB.setSchema(this.mLABranchGroupSchema);
    this.mLABranchGroupSet = tLABranchGroupDB.query();
    this.mResult.add(this.mLABranchGroupSet);
    logger.debug("End ALABranchGroupBLQuery Submit...");
    //如果有需要处理的错误，则返回
    if (tLABranchGroupDB.mErrors.needDealError()) {
      // @@错误处理
      this.mErrors.copyAllErrors(tLABranchGroupDB.mErrors);
      CError tError = new CError();
      tError.moduleName = "ALABranchGroupBL";
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
      this.mInputData = new VData();
      this.mInputData.add(this.mGlobalInput);
      this.mInputData.add(this.mLABranchGroupSchema);
      this.mInputData.add(this.mLABranchGroupBSchema);
      //this.mInputData.add(this.mLATreeSchema);
      //this.mInputData.add(this.mLATreeBSchema);
      //this.mInputData.add(this.mLAAgentSchema);
    }
    catch (Exception ex) {
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "ALABranchGroupBL";
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

  public boolean prepareCopyTree(LATreeSchema cLATreeSchema) {
    String tEdorNo = PubFun1.CreateMaxNo("EdorNo", 20);
    try {
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
      this.mLATreeBSchema.setRemoveType("03"); //转储类型:新增机构
      this.mLATreeBSchema.setStartDate(cLATreeSchema.getState());
      this.mLATreeBSchema.setState(cLATreeSchema.getState());
      this.mLATreeBSchema.setUpAgent(cLATreeSchema.getUpAgent());
      this.mLATreeBSchema.setOperator(this.mGlobalInput.Operator);
      this.mLATreeBSchema.setMakeDate(currentDate);
      this.mLATreeBSchema.setMakeTime(currentTime);
      this.mLATreeBSchema.setModifyDate(currentDate);
      this.mLATreeBSchema.setModifyTime(currentTime);
    }
    catch (Exception ex) {
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "AdjustAgentBL";
      tError.functionName = "prepareCopy";
      tError.errorMessage = "备份调动人员信息出错！";
      this.mErrors.addOneError(tError);
      return false;
    }
    return true;
  }

  private String getManagerGrade() {
    //查询LDCodeRELA表，取出该机构级别对应的职级、系列
    String tSQL =
        "select code1 from LDCodeRELA where relaType = 'gradeserieslevel' "
        + "and code3 = '" + "?code3?" + "' "
        + "and othersign = '1' order by code1";
    SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
	 sqlbv2.sql(tSQL);
	 sqlbv2.put("code3", this.mLABranchGroupSchema.getBranchLevel());
    ExeSQL tExeSQL = new ExeSQL();
    String tGrade = tExeSQL.getOneValue(sqlbv2);
    tGrade = tGrade.trim();
    if ( (tGrade == null) || (tGrade.equals(""))) {
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "ALABranchGroupBL";
      tError.functionName = "getManagerGrade";
      tError.errorMessage = "查询机构负责人对应的职级失败!";
      this.mErrors.addOneError(tError);
      return null;
    }
    logger.debug("职级：" + tGrade);
    return tGrade;
  }

  private String getUpBranch(String cUpBranch, String cBranchLevel) {
    String tUpBranch = cUpBranch;
    logger.debug("tUpBranch:" + tUpBranch);
    if (this.mLABranchGroupSchema.getBranchType().equals("1")) {
      cBranchLevel = Integer.parseInt(cBranchLevel) == 1 ? "2" : cBranchLevel;
    }
    switch (Integer.parseInt(cBranchLevel)) {
      case 1: {
        tUpBranch = "";
        break;
      }
      case 2: {
        tUpBranch = tUpBranch.substring(0, tUpBranch.length() - 3);
        break;
      }
      case 3: {
        tUpBranch = tUpBranch.substring(0, tUpBranch.length() - 2);
        break;
      }
      /** 最高机构为区域督导部 不是支公司了 **/
//            case 4:
//            {
//                tUpBranch = tUpBranch.substring(0,tUpBranch.length()-2);
//                break;
//            }
      default:
        tUpBranch = "";
    }
    if (!tUpBranch.equals("")) {
      //tongmeng 2005-08-23 modify
      //add branchtype='1'
      String tSQL = "select AgentGroup from LABranchGroup where BranchAttr = '"
          + "?tUpBranch?" + "' and branchtype='1'";
      SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
 	  sqlbv3.sql(tSQL);
 	  sqlbv3.put("tUpBranch", tUpBranch);
      ExeSQL tExeSQL = new ExeSQL();
      tUpBranch = tExeSQL.getOneValue(sqlbv3).trim();
      if ( (tUpBranch == null) || tUpBranch.equals("")) {
        CError tError = new CError();
        tError.moduleName = "ALABranchGroupBL";
        tError.functionName = "submitDat";
        tError.errorMessage = "所输机构代码的上级机构不存在！";
        this.mErrors.addOneError(tError);
        return null;
      }
    }
    return tUpBranch;
  }

  /**
   * 编写日期：2004-06-07 LL
   * 功能说明：判断某个机构下是否存在没有离职人员
   * 返回值说明；
   *    0 - 程序出错；
   *    1 - 此机构下没有离职人员；
   *    2 - 此机构下有未离职人员；
   */
  private String haveMember(String tBranchAttr, String tBranchType) {
    //通过机构外部编码，获得相应组号
    String tSQL = "select * from labranchgroup where BranchAttr like concat('" +
        "?tBranchAttr?"
        + "','%') and BranchType = '" + "?tBranchType?" + "' and BranchLevel = '01'"
        + " and endflag = 'N' ";
    SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
	  sqlbv4.sql(tSQL);
	  sqlbv4.put("tBranchAttr", tBranchAttr);
	  sqlbv4.put("tBranchType", tBranchType);
    LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
    LABranchGroupSet tLABranchGroupSet = new LABranchGroupSet();

    tLABranchGroupSet = tLABranchGroupDB.executeQuery(sqlbv4);
    //校验是否出错
    if (tLABranchGroupDB.mErrors.needDealError()) {
      this.mErrors.copyAllErrors(tLABranchGroupDB.mErrors);
      CError tError = new CError();
      tError.moduleName = "LABranchGroupBL";
      tError.functionName = "haveMember";
      tError.errorMessage = "查询LABranchGroup表中信息出错！";
      this.mErrors.addOneError(tError);
      logger.debug("查询LABranchGroup表中信息出错！");
      return "0";
    }
    //查看是否有符合条件的记录存在
    if (tLABranchGroupSet.size() == 0) {
      logger.debug("管理机构:" + tBranchAttr + "下没有符合条件的机构存在！");
      return "1";
    }

    //修改：2004-06-23 LL
    //修改原因：原来采用循环处理方式，这样处理起来会多次连接数据库，
    //现在改为用 in 方法处理

    //循环处理每一个组
    ExeSQL tExeSQL;
    String str = "";
    int tCount = 0;

    tExeSQL = new ExeSQL();
    //查找组下没离职人员
    String tSQL1 = "select count(*) from laagent where BranchCode in ("
        + " select agentgroup from labranchgroup where BranchAttr like concat('" +
        "?tBranchAttr?"
        + "','%') and BranchType = '" + "?tBranchType?" + "' and BranchLevel = '01'"
        + " and endflag = 'N' )"
        + " and AgentState < '03'";
    SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
	  sqlbv5.sql(tSQL1);
	  sqlbv5.put("tBranchAttr", tBranchAttr);
	  sqlbv5.put("tBranchType", tBranchType);
    logger.debug("组管理机构查询SQL:" + tSQL1);
    str = tExeSQL.getOneValue(sqlbv5).trim();
    logger.debug("组管理机构下在职人员人数为：" + str);
    tCount = Integer.parseInt(str);
    //有人存在组中，返回
    if (tCount > 0)
      return "2";
    //此机构下没有离职人员
    return "1";
  }

  /**
   * 编写日期：2004-06-17 LL
   * 功能说明：判断机构代码与机构级别之间的对应关系
   * 返回值说明；
   *    0 - 程序出错；
   *    1 - 对应关系正确；
   *    2 - 对应关系错误；
   */
  private String judgeGroupLevel(String tBranchAttr, String tBranchLevel) {
    //获得界面录入的机构代码长度
    int len1 = tBranchAttr.trim().length();
    //获得机构级别对应的机构代码长度
    int len2 = 0;

    LABranchLevelDB tLABranchLevelDB = new LABranchLevelDB();
    tLABranchLevelDB.setBranchLevelCode(tBranchLevel);

    //校验是否出错
    if (!tLABranchLevelDB.getInfo()) {
      this.mErrors.copyAllErrors(tLABranchLevelDB.mErrors);
      CError tError = new CError();
      tError.moduleName = "LABranchGroupBL";
      tError.functionName = "judgeGroupLevel";
      tError.errorMessage = "查询LABranchLevel表中信息出错！";
      this.mErrors.addOneError(tError);
      logger.debug("查询LABranchLevel表中信息出错！");
      return "0";
    }
    String length = tLABranchLevelDB.getBranchLevelProperty2();
    len2 = Integer.parseInt(length);
    logger.debug("录入的机构代码长度为：" + len1);
    logger.debug("级别" + tBranchLevel + "对应的机构代码长度为：" + len2);
    //判断长度是否相等
    if (len1 == len2)
      return "1";
    else
      return "2";
  }

  /**
   * 编写日期：2004-06-17 LL
   * 功能说明：判断此机构下是否有直辖机构存在
   * 返回值说明；
   *    0 - 程序出错；
   *    1 - 有直辖机构存在；
   *    2 - 没有直辖机构存在；
   */
  private String haveZXGroup(String tUpAgentGroup, String tAgentGroup) {
    LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
    LABranchGroupSet tLABranchGroupSet = new LABranchGroupSet();
    String tSql = "select * from labranchgroup where UpBranch = '" +
        "?tUpAgentGroup?" + "' "
        + " and EndFlag = 'N' and UpBranchAttr = '1'";
    SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
	  sqlbv6.sql(tSql);
	  sqlbv6.put("tUpAgentGroup", tUpAgentGroup);
    tLABranchGroupSet = tLABranchGroupDB.executeQuery(sqlbv6);
    //校验是否出错
    if (tLABranchGroupDB.mErrors.needDealError()) {
      this.mErrors.copyAllErrors(tLABranchGroupDB.mErrors);
      CError tError = new CError();
      tError.moduleName = "LABranchGroupBL";
      tError.functionName = "haveZXGroup";
      tError.errorMessage = "在LABranchGroup表中查上级机构信息出错！";
      this.mErrors.addOneError(tError);
      logger.debug("在LABranchGroup表中查上级机构信息出错！！");
      return "0";
    }
    logger.debug("管理机构" + tUpAgentGroup + "下直辖机构个数为：" +
                       tLABranchGroupSet.size());

    //处理有直辖机构存在的情况，这样是错误的，只可能有一个直辖机构存在
    if (tLABranchGroupSet.size() > 1) {
      this.mErrors.copyAllErrors(tLABranchGroupDB.mErrors);
      CError tError = new CError();
      tError.moduleName = "LABranchGroupBL";
      tError.functionName = "haveZXGroup";
      tError.errorMessage = "有多个直辖机构存在！";
      this.mErrors.addOneError(tError);
      logger.debug("有多个直辖机构存在！！");
      return "0";
    }

    //修改：2004-06-23 LL
    //修改内容：如果要修改的机构既为直辖机构，则直接返回 - 2
    if (tLABranchGroupSet.size() == 1
        && tAgentGroup.equals(tLABranchGroupSet.get(1).getAgentGroup()))
      return "2";
    //判断是否有没有停业的直辖机构存在
    if (tLABranchGroupSet.size() != 0)
      return "1";
    else
      return "2";
  }
}
