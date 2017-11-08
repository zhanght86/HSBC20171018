package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.tb.ProductSaleControlBL;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import java.text.*;
import java.util.*;


/**
 * <p>Title: Web业务系统 </p>
 * <p>Description: BL层业务逻辑处理类 </p>
 * <p>Copyright: Copyright (c) 2002 </p>
 * <p>Company: Sinosoft </p>
 * @author HST
 * @version 1.0
 * @date 2002-09-25
 */
public class GrpEdorNPDetailBL {
private static Logger logger = Logger.getLogger(GrpEdorNPDetailBL.class);
  /** 传入数据的容器 */
  private VData mInputData = new VData();
  private FDate fDate = new FDate();


  /** 往前面传输数据的容器 */
  private VData mResult = new VData();
  private MMap map = new MMap();
  TransferData tTransferData = new TransferData();

  /** 数据操作字符串 */
  private String mOperate;


  /** 错误处理类 */
  public CErrors mErrors = new CErrors();


  /** 业务处理相关变量 */
  private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();
  private LCGrpPolSet mLCGrpPolSet = new LCGrpPolSet();
  private LCGrpPolSet mOLDLCGrpPolSet = new LCGrpPolSet();
  private GlobalInput mGlobalInput = new GlobalInput();
  private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();


  // @Constructor
  public GrpEdorNPDetailBL() {}


  /**
   * 数据提交的公共方法
   * @param: cInputData 传入的数据
   *		  cOperate 数据操作字符串
   * @return:
   */
  public boolean submitData(VData cInputData, String cOperate) {
    // 将传入的数据拷贝到本类中
    mInputData = (VData) cInputData.clone();
    this.mOperate = cOperate;

    // 将外部传入的数据分解到本类的属性中，准备处理
    if (this.getInputData() == false) {
      return false;
    }
    logger.debug("---getInputData---");

    // 校验传入的数据
    if (!mOperate.equals("DELETE||GROUPRISK")) {
      if (this.checkData() == false) {
        return false;
      }
      logger.debug("---checkData---");
    }

    // 根据业务逻辑对数据进行处理
    if (!mOperate.equals("DELETE||GROUPRISK")) {
      if (this.dealData() == false) {
        return false;
      }
    } else {
      if (this.deleteData() == false) {
        return false;
      }
    }

    // 装配处理好的数据，准备给后台进行保存
    this.prepareOutputData();
    logger.debug("---prepareOutputData---");
    //　数据提交、保存
    PubSubmit tPubSubmit = new PubSubmit();
    logger.debug("Start GroupRiskBL Submit...");

    if (!tPubSubmit.submitData(mInputData, mOperate)) {
      // @@错误处理
      this.mErrors.copyAllErrors(tPubSubmit.mErrors);

      CError tError = new CError();
      tError.moduleName = "ContBL";
      tError.functionName = "submitData";
      tError.errorMessage = "数据提交失败!";

      this.mErrors.addOneError(tError);
      return false;
    }

    logger.debug("---commitData---");

    return true;
  }


  /**
   * 将外部传入的数据分解到本类的属性中
   * @param: 无
   * @return: boolean
   */
  private boolean getInputData() {

    mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
        "GlobalInput", 0));
    mLCGrpPolSet.set((LCGrpPolSet) mInputData.
                     getObjectByObjectName("LCGrpPolSet", 0));
    mLPGrpEdorItemSchema.setSchema((LPGrpEdorItemSchema) mInputData.
                                   getObjectByObjectName("LPGrpEdorItemSchema",
        0));
    tTransferData = (TransferData) mInputData.
                    getObjectByObjectName("TransferData", 0);

    if (mLCGrpPolSet.size() == 0) {
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "GroupRiskBL";
      tError.functionName = "getInputData";
      tError.errorMessage = "在接受数据时没有得到团体体险种!";
      this.mErrors.addOneError(tError);
      return false;
    }
    LCGrpContDB mLCGrpContDB = new LCGrpContDB();
    mLCGrpContDB.setGrpContNo(mLCGrpPolSet.get(1).getGrpContNo());
    mLCGrpContSchema.setSchema(mLCGrpContDB.query().get(1));
    if (mLCGrpContSchema == null) {
      CError tError = new CError();
      tError.moduleName = "GrpEdorNPDetailBL";
      tError.functionName = "getInputData";
      tError.errorMessage = "没有查到该团单信息!";
      this.mErrors.addOneError(tError);
      return false;
    }
    logger.debug("团单号＝＝＝＝＝＝＝" + mLCGrpContSchema.getGrpContNo());
    LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
    tLPGrpEdorItemDB.setEdorAcceptNo(mLPGrpEdorItemSchema.getEdorAcceptNo());
    tLPGrpEdorItemDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
    tLPGrpEdorItemDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
    tLPGrpEdorItemDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
    LPGrpEdorItemSet tLPGrpEdorItemSet = tLPGrpEdorItemDB.query();
    if (tLPGrpEdorItemSet == null || tLPGrpEdorItemSet.size() < 1) {
      CError tError = new CError();
      tError.moduleName = "GrpEdorNPDetailBL";
      tError.functionName = "getInputData";
      tError.errorMessage = "没有查到该保全项目信息!";
      this.mErrors.addOneError(tError);
      return false;
    }
    mLPGrpEdorItemSchema.setSchema(tLPGrpEdorItemSet.get(1));

    return true;
  }


  /**
   * 校验传入的数据
   * @param: 无
   * @return: boolean
   */
  private boolean checkData() {
    if (!mOperate.equals("INSERT||GROUPRISK")
        && !mOperate.equals("DELETE||GROUPRISK")) {
      CError tError = new CError();
      tError.moduleName = "GroupRiskBL";
      tError.functionName = "getInputData";
      tError.errorMessage = "只能新增或者删除!";
      this.mErrors.addOneError(tError);
      return false;

    }
    if (mOperate.equals("INSERT||GROUPRISK")) {
      LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
      tLCGrpPolDB.setGrpContNo(mLCGrpContSchema.getGrpContNo());
      tLCGrpPolDB.setRiskCode(mLCGrpPolSet.get(1).getRiskCode());
      if (tLCGrpPolDB.getCount() > 0) {
        CError tError = new CError();
        tError.moduleName = "GroupRiskBL";
        tError.functionName = "checkData";
        tError.errorMessage = "该团单下已经添加过该险种!";
        this.mErrors.addOneError(tError);
        return false;
      }
    }
    LMRiskDB tLMRiskDB = new LMRiskDB();
    tLMRiskDB.setRiskCode(mLCGrpPolSet.get(1).getRiskCode());
    if (tLMRiskDB.getInfo() == false) {
      CError tError = new CError();
      tError.moduleName = "GroupRiskBL";
      tError.functionName = "checkData";
      tError.errorMessage = "没有查到险种信息!";
      this.mErrors.addOneError(tError);
      return false;
    }
//    tTransferData.setNameAndValue("RiskName", tLMRiskDB.getRiskName());

    return true;
  }


  /**
   * 根据业务逻辑对数据进行处理
   * @param: 无
   * @return: boolean
   */
  private boolean dealData() {
    //产生集体投保单号码
    if (mOperate.equals("INSERT||GROUPRISK")) {
      LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
      LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
      tLCGrpPolDB.setGrpContNo(mLCGrpContSchema.getGrpContNo());
      Reflections mRef = new Reflections();
      mRef.transFields(tLCGrpPolSchema,tLCGrpPolDB.query().get(1));
      mRef.transFields(tLCGrpPolSchema,mLCGrpPolSet.get(1));
      tLCGrpPolSchema.setCValiDate(mLCGrpContSchema.getCValiDate());
      String tLimit = PubFun.getNoLimit(mLCGrpContSchema.getManageCom());
      String tNo = PubFun1.CreateMaxNo("GrpProposalNo", tLimit);
      tLCGrpPolSchema.setGrpPolNo(tNo); //如果是新增
      tLCGrpPolSchema.setGrpProposalNo(tNo);
      tLCGrpPolSchema.setPrtNo("");//为了避免增人能查询到还未生效的险种，故先置空
      tLCGrpPolSchema.setGrpContNo(mLCGrpContSchema.getGrpContNo());
      tLCGrpPolSchema.setSaleChnl(mLCGrpContSchema.getSaleChnl());
      tLCGrpPolSchema.setManageCom(mLCGrpContSchema.getManageCom());
      tLCGrpPolSchema.setAgentCom(mLCGrpContSchema.getAgentCom());
      tLCGrpPolSchema.setAgentType(mLCGrpContSchema.getAgentType());
      tLCGrpPolSchema.setAgentCode(mLCGrpContSchema.getAgentCode());
      tLCGrpPolSchema.setAgentGroup(mLCGrpContSchema.getAgentGroup());
      tLCGrpPolSchema.setCustomerNo(mLCGrpContSchema.getAppntNo());
      tLCGrpPolSchema.setAddressNo(mLCGrpContSchema.getAddressNo());
      tLCGrpPolSchema.setGrpName(mLCGrpContSchema.getGrpName());
      tLCGrpPolSchema.setAppFlag("2");
      tLCGrpPolSchema.setUWFlag("9");
      tLCGrpPolSchema.setUWDate(PubFun.getCurrentDate());
      tLCGrpPolSchema.setUWTime(PubFun.getCurrentTime());
      tLCGrpPolSchema.setUWOperator(mGlobalInput.Operator);
//      tLCGrpPolSchema.setApproveFlag("0");
      tLCGrpPolSchema.setApproveFlag("9"); //设置复核为通过
      tLCGrpPolSchema.setOutPayFlag("1");
      tLCGrpPolSchema.setState("0");
      tLCGrpPolSchema.setApproveDate(PubFun.getCurrentDate());
      tLCGrpPolSchema.setApproveTime(PubFun.getCurrentTime());
      tLCGrpPolSchema.setApproveCode(mGlobalInput.Operator);
      tLCGrpPolSchema.setModifyDate(PubFun.getCurrentDate());
      tLCGrpPolSchema.setModifyTime(PubFun.getCurrentTime());
      tLCGrpPolSchema.setOperator(mGlobalInput.Operator);
      tLCGrpPolSchema.setMakeDate(PubFun.getCurrentDate());
      tLCGrpPolSchema.setMakeTime(PubFun.getCurrentTime());
      tLCGrpPolSchema.setStandbyFlag3("1"); //保全新增险种标志
      tLCGrpPolSchema.setFirstPayDate(mLPGrpEdorItemSchema.getEdorValiDate());
      Date baseDate = fDate.getDate(tLCGrpPolSchema.getFirstPayDate());
      Date tDate = PubFun.calDate(baseDate, 365, "D", null);
      LCGrpPolDB aLCGrpPolDB = new LCGrpPolDB();
      LCGrpPolSchema aLCGrpPolSchema = new LCGrpPolSchema();
      String sql = "Select max(paytodate) from LCGrpPol where GrpContNo='" +
                   tLCGrpPolSchema.getGrpContNo() + "' and appflag='1'";
      logger.debug(sql);
      ExeSQL tExeSQL = new ExeSQL();
      String tpaytodate = "";
      tpaytodate = tExeSQL.getOneValue(sql);
      if (tpaytodate == null || tpaytodate.equals("")) {
        tLCGrpPolSchema.setPaytoDate(tDate);
      } else if (tpaytodate.compareTo(mLPGrpEdorItemSchema.getEdorValiDate()) <
                 0) {
        tLCGrpPolSchema.setPaytoDate(tDate);
      }else{
        tLCGrpPolSchema.setPaytoDate(tpaytodate);
      }
      tLCGrpPolSchema.setPayEndDate(tLCGrpPolSchema.getPaytoDate());
      LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
      VData PolVData = new VData();
      tLCGrpPolSet.add(tLCGrpPolSchema);
	  ProductSaleControlBL tProductSaleControlBL = new ProductSaleControlBL();
	  if(!tProductSaleControlBL.submitData(PolVData, "")){
	  int tCount = tProductSaleControlBL.mErrors.getErrorCount();
			for(int i=1; i <= tCount;i++){
				CError.buildErr(this, tProductSaleControlBL.mErrors.getFirstError().toString());
			}
	  }
	  VData Result = tProductSaleControlBL.getResult();
	  if(Result.size()>0){
		  CError.buildErr(this, "停售或当前机构不允许销售的险种!");
		 return false;
	  }
      logger.debug(tLCGrpPolSchema.getPayEndDate());
      LMRiskDB tLMRiskDB = new LMRiskDB();
      LMRiskSchema tLMRiskSchema = new LMRiskSchema();
      tLMRiskDB.setRiskCode(tLCGrpPolSchema.getRiskCode());
      tLMRiskSchema = tLMRiskDB.query().get(1);
      if (tLMRiskSchema.getInsuAccFlag().trim().equals("Y")) {
        tLCGrpPolSchema.setPaytoDate(tLCGrpPolSchema.getFirstPayDate());
        Date payendDate = null;
        payendDate = PubFun.calDate(baseDate, 100, "Y", null);
        tLCGrpPolSchema.setPayEndDate(payendDate);
      }
      map.put(tLCGrpPolSchema, "DELETE&INSERT");

      LPGrpPolSchema mLPGrpPolSchema = new LPGrpPolSchema();
      mRef.transFields(mLPGrpPolSchema,tLCGrpPolSchema);
      mLPGrpPolSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
      mLPGrpPolSchema.setEdorType("NP");
      mLPGrpPolSchema.setMakeDate(PubFun.getCurrentDate());
      mLPGrpPolSchema.setMakeTime(PubFun.getCurrentTime());
      map.put(mLPGrpPolSchema, "DELETE&INSERT");

      mLPGrpEdorItemSchema.setEdorState("1");
      mLPGrpEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
      mLPGrpEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
      map.put(mLPGrpEdorItemSchema, "DELETE&INSERT");
    }

    return true;
  }


  /**
   * 删除传入的险种
   * @param: 无
   * @return: void
   */
  private boolean deleteData() {

    for (int i = 1; i <= mLCGrpPolSet.size(); i++) {
      String riskcode = mLCGrpPolSet.get(i).getRiskCode();
      String GrpContNo = mLCGrpPolSet.get(i).getGrpContNo();
      String wherepart = "RiskCode ='" + riskcode + "' and GrpContNo = '"
                         + GrpContNo + "'";

      LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
      tLCGrpPolDB.setGrpContNo(GrpContNo);
      tLCGrpPolDB.setRiskCode(riskcode);
      LCGrpPolSet tLCGrpPolSet = tLCGrpPolDB.query();
      if (tLCGrpPolSet == null || tLCGrpPolSet.size() <= 0) {
        CError tError = new CError();
        tError.moduleName = "GrpContBL";
        tError.functionName = "dealData";
        tError.errorMessage = "没有找到需要删除的险种!";
        this.mErrors.addOneError(tError);
        return false;
      }
      if (!tLCGrpPolSet.get(1).getAppFlag().equals("2")) {
        CError tError = new CError();
        tError.moduleName = "GrpContBL";
        tError.functionName = "dealData";
        tError.errorMessage = "已经生效的险种不能删除!";
        this.mErrors.addOneError(tError);
        return false;
      }

      map.put("delete from LCGrpPol where " + wherepart, "DELETE");
      map.put("delete from LPGrpPol where " + wherepart + " and edorno='"
	      +mLPGrpEdorItemSchema.getEdorNo()+"' and edortype='NP'", "DELETE");
      map.put("update lpgrpedoritem set edorstate='3' where edorno='" +
              mLPGrpEdorItemSchema.getEdorNo() +
              "' and edortype='NP' and 0=(select count(*) from lcgrppol where grpcontno='" +
              mLPGrpEdorItemSchema.getGrpContNo() + "' and appflag='2')",
              "UPDATE");

    }

    return true;
  }


  /**
   * 根据业务逻辑对数据进行处理
   * @param: 无
   * @return: void
   */
  private void prepareOutputData() {
    mResult.clear();
    mResult.add(mLCGrpPolSet);
    mResult.add(tTransferData);
    mInputData.clear();
    mInputData.add(map);

  }


  /**
   * 得到处理后的结果集
   * @return 结果集
   */

  public VData getResult() {
    return mResult;
  }

}
