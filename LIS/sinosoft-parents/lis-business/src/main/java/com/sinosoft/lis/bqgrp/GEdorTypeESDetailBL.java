package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.BqCalBL;
import com.sinosoft.lis.bq.BqCode;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LLCasePolicyDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.lis.vschema.LPEdorMainSet;
import com.sinosoft.lis.schema.LPGrpContSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpPolSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.vschema.LLCasePolicySet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.lis.vschema.LPGrpPolSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;



/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 团体保全集体下减人功能类</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author  sunsx 2008-12-27
 * @version 1.0
 */
public class GEdorTypeESDetailBL implements BusinessService{
private static Logger logger = Logger.getLogger(GEdorTypeESDetailBL.class);
  /** 错误处理类，每个需要错误处理的类中都放置该类 */
  public CErrors mErrors = new CErrors();


  /** 往后面传输数据的容器 */
  private VData mInputData;


  /** 往界面传输数据的容器 */
  private VData mResult = new VData();


  /** 数据操作字符串 */
  private String mOperate;


  private LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet();
  private LPEdorItemSet saveLPEdorItemSet = new LPEdorItemSet();
  private LPGrpPolSet mLPGrpPolSet = new LPGrpPolSet();
  private LJSGetEndorseSet mLJSGetEndorseSet = new LJSGetEndorseSet();
  private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();

  private LPEdorMainSet mLPEdorMainSet= new LPEdorMainSet();
  /** 全局数据 */
  private GlobalInput mGlobalInput = new GlobalInput();
  private Reflections ref = new Reflections();
  private GrpEdorCalZT mGrpEdorCalZT = new GrpEdorCalZT();
  private String currDate = PubFun.getCurrentDate();
  private String currTime = PubFun.getCurrentTime();
  private MMap map = new MMap();
  public GEdorTypeESDetailBL() {
  }

  public boolean submitData(VData cInputData, String cOperate) {
    //将操作数据拷贝到本类中
    mInputData = (VData) cInputData.clone();
    mOperate = cOperate;

    //得到外部传入的数据
    if (!getInputData()) {
      return false;
    }
    logger.debug("---End getInputData---");

    //数据校验操作
    if (!checkData()) {
      return false;
    }
    logger.debug("---End checkData---");

    //数据准备操作
    if (mOperate.equals("INSERT||MUlTIEDOR")) {
      if (!dealData()) {
        return false;
      }
    }
    logger.debug("---End prepareData---");
    //数据准备操作
    if (mOperate.equals("DELETE||EDOR")) {
      if (!deleteData()) {
        return false;
      }
    }
    if (mOperate.equals("INSERT||SAVEEDOR")) {
        if (!saveData()) {
          return false;
        }
     }
    if (mOperate.equals("INSERT||GRPEDOR")) {
        if (!dealGrpData()) {
          return false;
        }
      }


    PubSubmit tPubSubmit = new PubSubmit();

    if (!tPubSubmit.submitData(mResult, mOperate)) {
      // @@错误处理
      this.mErrors.copyAllErrors(tPubSubmit.mErrors);
      CError tError = new CError();
      tError.moduleName = "GEdorDetailBL";
      tError.functionName = "submitData";
      tError.errorMessage = "数据提交失败!";
      this.mErrors.addOneError(tError);
      mResult.clear();
      return false;
    }
    mResult.clear();
    return true;
  }


  private boolean saveData() {

	  map.put("delete from lpgrppol where edorno = '"+mLPGrpEdorItemSchema.getEdorNo()+"' and edortype = '"+mLPGrpEdorItemSchema.getEdorType()+"' and grpcontno = '"+mLPGrpEdorItemSchema.getGrpContNo()+"'", "DELETE");
	  map.put("delete from lpgrpcont where edorno = '"+mLPGrpEdorItemSchema.getEdorNo()+"' and edortype = '"+mLPGrpEdorItemSchema.getEdorType()+"' and grpcontno = '"+mLPGrpEdorItemSchema.getGrpContNo()+"'", "DELETE");
	  map.put("delete from lpcont where edorno = '"+mLPGrpEdorItemSchema.getEdorNo()+"' and edortype = '"+mLPGrpEdorItemSchema.getEdorType()+"' and grpcontno = '"+mLPGrpEdorItemSchema.getGrpContNo()+"'", "DELETE");
	  //准备团单数据

	  LCGrpContDB tLCGrpContDB = new LCGrpContDB();
	  tLCGrpContDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
	  LCGrpContSchema tLCGrpContSchema = null;
	  if(!tLCGrpContDB.getInfo())
	  {
		  CError.buildErr(this, "查询团单信息失败!");
		  return false;
	  }
	  tLCGrpContSchema = tLCGrpContDB.getSchema();
	  LPGrpContSchema tLPGrpContSchema = new LPGrpContSchema();
	  ref.transFields(tLPGrpContSchema, tLCGrpContSchema);
	  tLPGrpContSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
	  tLPGrpContSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
	  tLPGrpContSchema.setModifyDate(PubFun.getCurrentDate());
	  tLPGrpContSchema.setModifyTime(PubFun.getCurrentTime());

	  //合同退保的金额
	  double oldContPrem = 0.0;
	  double oldContAmnt = 0.0;
	  double oldContMult = 0.0;

	  double ZTContPrem = 0.0;
	  double ZTContAmnt = 0.0;
	  double ZTContMult = 0.0;

	  //准备团单险种数据GrpPol
	  LPGrpPolSet tLPGrpPolSet = new LPGrpPolSet();
	  LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
	  tLCGrpPolDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
	  tLCGrpPolDB.setAppFlag("1");
	  LCGrpPolSet tLCGrpPolSet = tLCGrpPolDB.query();
	  LCGrpPolSchema tLCGrpPolSchema = null;
	  LPGrpPolSchema tLPGrpPolSchema = null;
	  if(tLCGrpPolSet == null || tLCGrpPolSet.size()<1)
	  {
		  CError.buildErr(this, "查询团单险种信息失败!");
		  return false;
	  }
	  for(int i = 1; i<=tLCGrpPolSet.size();i++)
	  {
		  tLCGrpPolSchema = tLCGrpPolSet.get(i);
		  tLPGrpPolSchema = new LPGrpPolSchema();
		  ref.transFields(tLPGrpPolSchema, tLCGrpPolSchema);
		  tLPGrpPolSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		  tLPGrpPolSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		  tLPGrpPolSchema.setModifyDate(PubFun.getCurrentDate());
		  tLPGrpPolSchema.setModifyTime(PubFun.getCurrentTime());

		  //统计原保费,保额,份数,人数
		  double oldPrem = 0.0;
		  double oldAmnt = 0.0;
		  double oldMult = 0.0;
		  int oldPeoples = 0;


		  LCPolDB tLCPolDB = new LCPolDB();
		  tLCPolDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		  tLCPolDB.setGrpPolNo(tLPGrpPolSchema.getGrpPolNo());
		  tLCPolDB.setAppFlag("1");
		  LCPolSet tLCPolSet = tLCPolDB.query();
		  oldPeoples = tLCPolSet.size();

		  if(tLCPolSet == null || tLCPolSet.size()<1)
		  {
			  //空险种
			  continue;
		  }
		  for(int j = 1; j<=oldPeoples;j++)
		  {
			  oldPrem += tLCPolSet.get(j).getPrem();
			  oldAmnt += tLCPolSet.get(j).getAmnt();
			  oldMult += tLCPolSet.get(j).getMult();
		  }

		  oldContPrem += oldPrem;
		  oldContAmnt += oldAmnt;
		  oldContMult += oldMult;

		  double ZTPrem = 0.0;
		  double ZTAmnt = 0.0;
		  double ZTMult = 0.0;
		  int ZTPeoples = 0;

		  LPPolDB tLPPolDB = new LPPolDB();
		  tLPPolDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		  tLPPolDB.setGrpPolNo(tLPGrpPolSchema.getGrpPolNo());
		  tLPPolDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		  tLPPolDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		  LPPolSet tLPPolSet = tLPPolDB.query();
		  ZTPeoples = tLPPolSet.size();
		  if(tLPPolSet == null || tLPPolSet.size()<1)
		  {
			  //险种没有退保的,跳过
			  continue;
		  }

		  for(int j = 1; j<=ZTPeoples;j++)
		  {
			  ZTPrem += tLPPolSet.get(j).getPrem();
			  ZTAmnt += tLPPolSet.get(j).getAmnt();
			  ZTMult += tLPPolSet.get(j).getMult();

		  }

		  ZTContPrem += ZTPrem;
		  ZTContAmnt += ZTAmnt;
		  ZTContMult += ZTMult;

		  int newPeoples = oldPeoples-ZTPeoples;
		  double newPrem = PubFun.round((oldPrem-ZTPrem), 2);
		  double newAmnt = PubFun.round((oldAmnt-ZTAmnt), 2);
		  double newMult = PubFun.round((oldMult-ZTMult), 2);
		  if(newPeoples <= 0){
			  tLPGrpPolSchema.setAppFlag("4");
		  }
		  logger.debug("该险种退保后在保的人数为:"+newPeoples);
		  logger.debug("退保后团体险种结余的保费:"+newPrem);
		  logger.debug("退保后团体险种结余的保额:"+newAmnt);
		  logger.debug("退保后团体险种结余的份数:"+newMult);

		  tLPGrpPolSchema.setPeoples2(newPeoples);
		  tLPGrpPolSchema.setPrem(newPrem);
		  tLPGrpPolSchema.setAmnt(newAmnt);
		  tLPGrpPolSchema.setMult(newMult);
		  tLPGrpPolSet.add(tLPGrpPolSchema);
	  }

	  int OldContPeoples = 0;//合同下在保人数
	  int ZTContPeoples = 0;//有可能退保的人数
	  LPContSet tLPContSet = new LPContSet();
	  LCContDB tLCContDB = new LCContDB();
	  tLCContDB.setAppFlag("1");
	  tLCContDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
	  LCContSet tLCContSet = tLCContDB.query();
	  if(tLCContSet == null || tLCContSet.size()<1)
	  {
		  CError.buildErr(this, "查询团单下个人有效合同信息失败!");
		  return false;
	  }
	  OldContPeoples = tLCContSet.size();

	  String tSQL = "Select distinct ContNo From LPEdorItem Where EdorNo = '"+mLPGrpEdorItemSchema.getEdorNo()+"'  and EdorType = '"+mLPGrpEdorItemSchema.getEdorType()+"'";
	  ExeSQL tExeSQL = new ExeSQL();
	  SSRS tSSRS = new SSRS();
	  
	  tSSRS = tExeSQL.execSQL(tSQL);
	  if (tSSRS == null || tSSRS.MaxRow < 1) {
			CError.buildErr(this, "无终止记录,请添加终止记录,然后保存");
			return false;
	  }
	  int tArrLen = tSSRS.MaxRow;
	  LCContSchema tLCContSchema = null;
	  LPContSchema tLPContSchema = null;
	  for (int i = 1; i <= tArrLen; i++) {
		  String tContNo = tSSRS.GetText(i, 1);
		  tLCContDB = new LCContDB();
		  tLCContDB.setContNo(tContNo);
		  if(!tLCContDB.getInfo()){
			  CError.buildErr(this, "查询个人合同记录失败!");
			  return false;
		  }
		  tLCContSchema = tLCContDB.getSchema();
		  tLPContSchema = new LPContSchema();
		  ref.transFields(tLPContSchema, tLCContSchema);
		  tLPContSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		  tLPContSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		  tLPContSchema.setModifyDate(PubFun.getCurrentDate());
		  tLPContSchema.setModifyTime(PubFun.getCurrentTime());
		  LCPolDB tLCPolDB = new LCPolDB();
		  tSQL = "Select * From LCPol Where ContNo = '"+tContNo+"' and AppFlag = '1'  and not Exists (Select 'X' From LPEdorItem Where EdorNo = '"+mLPGrpEdorItemSchema.getEdorNo()+"' "
		  	   + "and EdorType = '"+mLPGrpEdorItemSchema.getEdorType()+"' and PolNo = LCPol.Polno)";
		  LCPolSet tLCPolSet = tLCPolDB.executeQuery(tSQL);
		  if(tLCPolSet == null || tLCPolSet.size()<1)
		  {
			  //失效终止了
			  tLPContSchema.setAppFlag("4");
			  ZTContPeoples++;
			  
		  }else{
			  tLPContSchema.setAppFlag("1");
			  //仍继续有效
			  tSQL = " select nvl(sum(amnt),0),nvl(sum(mult),0),nvl(sum(prem),0) from lcpol where contno = '"+tContNo+"' and appflag = '1' "
			  	   + " and not exists (select 'X' from lpedoritem where edorno = '"+mLPGrpEdorItemSchema.getEdorNo()+"'  and edortype = '"+mLPGrpEdorItemSchema.getEdorType()+"' and polno = lcpol.polno)";
			  ExeSQL rExeSQL = new ExeSQL();
			  SSRS rSSRS = new SSRS();
			  rSSRS = rExeSQL.execSQL(tSQL);
			  String tAmnt = rSSRS.GetText(1,1);
			  String tMult = rSSRS.GetText(1,2);
			  String tPrem = rSSRS.GetText(1,3);
			  tLPContSchema.setAmnt(tAmnt);
			  tLPContSchema.setMult(tMult);
			  tLPContSchema.setPrem(tPrem);
			  
		  }
		  tLPContSet.add(tLPContSchema);
	  }
			
	  int newContPeoples = OldContPeoples-ZTContPeoples;
	  double newContPrem = PubFun.round((oldContPrem-ZTContPrem), 2);
	  double newContAmnt = PubFun.round((oldContAmnt-ZTContAmnt), 2);
	  double newContMult = PubFun.round((oldContMult-ZTContMult), 2);
	  logger.debug("该团体保单保存后在保的人数为:"+newContPeoples);
	  logger.debug("该团体保单保存后的保费:"+newContPrem);
	  logger.debug("该团体保单保存后的保额:"+newContAmnt);
	  logger.debug("该团体保单保存后的份数:"+newContMult);
	  tLPGrpContSchema.setPeoples(newContPeoples);
	  tLPGrpContSchema.setPrem(newContPrem);
	  tLPGrpContSchema.setAmnt(newContAmnt);
	  tLPGrpContSchema.setMult(newContMult);
	  map.put(tLPGrpContSchema, "DELETE&INSERT");
	  map.put(tLPGrpPolSet, "DELETE&INSERT");
	  map.put(tLPContSet, "DELETE&INSERT");
	  mLPGrpEdorItemSchema.setEdorState("1");
	  mLPGrpEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
	  mLPGrpEdorItemSchema.setModifyTime(PubFun.getCurrentTime());

	  map.put(mLPGrpEdorItemSchema, "DELETE&INSERT");

	  mResult.clear();
	  mResult.add(map);

	  return true;
}

/**
   * 数据输出方法，供外界获取数据处理结果
   * @return 包含有数据查询结果字符串的VData对象
   */
  public VData getResult() {
    return mResult;
  }


  /**
   * 将外部传入的数据分解到本类的属性中
   * @param: 无
   * @return: boolean
   */
  private boolean getInputData() {
    try {
    	
      mLPGrpPolSet = (LPGrpPolSet) mInputData.getObjectByObjectName("LPGrpPolSet", 0);
      mLPEdorItemSet = (LPEdorItemSet) mInputData.getObjectByObjectName(
          "LPEdorItemSet", 0);
      mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) mInputData.
                             getObjectByObjectName("LPGrpEdorItemSchema",
          0);
      mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
          "GlobalInput", 0);
    } catch (Exception e) {
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "GEdorDetailBL";
      tError.functionName = "getInputData";
      tError.errorMessage = "接收数据失败!!";
      this.mErrors.addOneError(tError);
      return false;
    }

    return true;
  }


  /**
   * 校验传入的数据的合法性
   * @return
   */
  private boolean checkData() {
    LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
    //tLPGrpEdorItemDB.setSchema(mLPGrpEdorItemSchema);
    tLPGrpEdorItemDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
    tLPGrpEdorItemDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
    tLPGrpEdorItemDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
    LPGrpEdorItemSet tLPGrpEdorItemSet = new LPGrpEdorItemSet();
    tLPGrpEdorItemSet = tLPGrpEdorItemDB.query();
    if (tLPGrpEdorItemSet.size() < 1) {
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "GEdorDetailBL";
      tError.functionName = "Preparedata";
      tError.errorMessage = "无保全申请数据!";
      logger.debug("------" + tError);
      this.mErrors.addOneError(tError);
      return false;
    }
    //将查询出来的团体保全主表数据保存至模块变量中，省去其它的重复查询
    mLPGrpEdorItemSchema.setSchema(tLPGrpEdorItemSet.get(1).getSchema());

    if (mLPGrpEdorItemSchema.getEdorState().trim().equals("2")) {
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "GEdorDetailBL";
      tError.functionName = "Preparedata";
      tError.errorMessage = "该保全已经申请确认不能修改!";
      logger.debug("------" + tError);
      this.mErrors.addOneError(tError);
      return false;
    }

    return true;
  }

  private boolean dealGrpData() {


	  //待处理的数据
	  LPPolSet aLPPolSet = new LPPolSet();
	  

	  LPPolSchema tLPPolSchema = null;//险种保单
	  LCPolSchema tLCPolSchema = null;
	  
	  LPEdorItemSchema polLPEdorItemSchema = null;
	  for (int i = 1; i <= mLPGrpPolSet.size(); i++) {

		  LCPolDB tLCPolDB = new LCPolDB();
		  tLCPolDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		  tLCPolDB.setGrpPolNo(mLPGrpPolSet.get(i).getGrpPolNo());
		  tLCPolDB.setAppFlag("1");
		  LCPolSet tLCPolSet = tLCPolDB.query();
		  if(tLCPolSet == null || tLCPolSet.size() < 1)
		  {
			  CError.buildErr(this, "查询被保人保单险种信息失败!");
			  return false; 
		  }

		  for(int j = 1; j <= tLCPolSet.size(); j++)
		  {
			  double polMoney = 0.0;
			  tLCPolSchema = tLCPolSet.get(j);
			  tLPPolSchema = new LPPolSchema();
			  ref.transFields(tLPPolSchema, tLCPolSchema);
			  tLPPolSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
			  tLPPolSchema.setAppFlag("4");//终止状态
			  tLPPolSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
			  tLPPolSchema.setModifyDate(PubFun.getCurrentDate());
			  tLPPolSchema.setModifyTime(PubFun.getCurrentTime());

			  polLPEdorItemSchema = new LPEdorItemSchema();//细化保全明细
			  ref.transFields(polLPEdorItemSchema, mLPGrpEdorItemSchema);
			  ref.transFields(polLPEdorItemSchema, tLPPolSchema);
			  polLPEdorItemSchema.setPolNo(tLPPolSchema.getPolNo());
			  polLPEdorItemSchema.setManageCom(mLPGrpEdorItemSchema.getManageCom());
			  polLPEdorItemSchema.setOperator(mGlobalInput.Operator);
			  polLPEdorItemSchema.setUWFlag("0");
			  polLPEdorItemSchema.setEdorState("1");
			  polLPEdorItemSchema.setMakeDate(currDate);
			  polLPEdorItemSchema.setMakeTime(currTime);
			  polLPEdorItemSchema.setModifyDate(currDate);
			  polLPEdorItemSchema.setModifyTime(currTime);
			  saveLPEdorItemSet.add(polLPEdorItemSchema);

			  //--------------开始计算退保金------------
			  try {
				  polMoney = mGrpEdorCalZT.calZTData(polLPEdorItemSchema);
			  } catch (Exception e) {
				  CError.buildErr(this, "计算客户号为："+tLCPolSchema.getInsuredNo()+"的退保金失败!\n 其保单号为:"+tLCPolSchema.getPolNo());
				  return false; 
			  }

			  if(polMoney < 0 )
			  {
				  CError.buildErr(this, "计算客户号为："+tLCPolSchema.getInsuredNo()+"的退保金失败!\n 其保单号为:"+tLCPolSchema.getPolNo());
				  return false; 
			  }

			  if(polMoney > 0 )
			  {
				  String sFeeType = BqCalBL.getFinType(tLPPolSchema.getEdorType(), "TB",tLCPolSchema.getPolNo());
				  LJSGetEndorseSchema tLJSGetEndorseSchemaNew = new LJSGetEndorseSchema();
				  tLJSGetEndorseSchemaNew.setGetNoticeNo(mLPGrpEdorItemSchema.getEdorNo());
				  tLJSGetEndorseSchemaNew.setEndorsementNo(mLPGrpEdorItemSchema.getEdorNo());
				  tLJSGetEndorseSchemaNew.setFeeOperationType(mLPGrpEdorItemSchema.getEdorType());
				  tLJSGetEndorseSchemaNew.setFeeFinaType(sFeeType);
				  tLJSGetEndorseSchemaNew.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
				  tLJSGetEndorseSchemaNew.setGrpPolNo(tLPPolSchema.getGrpPolNo());
				  tLJSGetEndorseSchemaNew.setContNo(tLPPolSchema.getContNo());
				  tLJSGetEndorseSchemaNew.setPolNo(tLPPolSchema.getPolNo());
				  tLJSGetEndorseSchemaNew.setRiskCode(tLPPolSchema.getRiskCode());
				  tLJSGetEndorseSchemaNew.setOtherNo(mLPGrpEdorItemSchema.getEdorNo());
				  tLJSGetEndorseSchemaNew.setOtherNoType("3");
				  tLJSGetEndorseSchemaNew.setDutyCode("000000");
				  tLJSGetEndorseSchemaNew.setPayPlanCode("000000");
				  tLJSGetEndorseSchemaNew.setGetDate(mLPGrpEdorItemSchema.getEdorValiDate());
				  tLJSGetEndorseSchemaNew.setGetMoney(polMoney);
				  tLJSGetEndorseSchemaNew.setAgentCode(tLPPolSchema.getAgentCode());
				  tLJSGetEndorseSchemaNew.setAgentCom(tLPPolSchema.getAgentCom());
				  tLJSGetEndorseSchemaNew.setAgentGroup(tLPPolSchema.getAgentGroup());
				  tLJSGetEndorseSchemaNew.setAgentType(tLPPolSchema.getAgentType());
				  tLJSGetEndorseSchemaNew.setInsuredNo(tLPPolSchema.getInsuredNo());
				  tLJSGetEndorseSchemaNew.setKindCode(tLPPolSchema.getKindCode());
				  tLJSGetEndorseSchemaNew.setAppntNo(tLPPolSchema.getAppntNo());
				  tLJSGetEndorseSchemaNew.setRiskVersion(tLPPolSchema.getRiskVersion());
				  tLJSGetEndorseSchemaNew.setHandler(tLPPolSchema.getHandler());
				  tLJSGetEndorseSchemaNew.setApproveCode(tLPPolSchema.getApproveCode());
				  tLJSGetEndorseSchemaNew.setApproveDate(tLPPolSchema.getApproveDate());
				  tLJSGetEndorseSchemaNew.setApproveTime(tLPPolSchema.getApproveTime());
				  tLJSGetEndorseSchemaNew.setManageCom(tLPPolSchema.getManageCom());
				  tLJSGetEndorseSchemaNew.setCurrency(tLPPolSchema.getCurrency());
				  tLJSGetEndorseSchemaNew.setPolType("1");
				  tLJSGetEndorseSchemaNew.setGetFlag("1");
				  tLJSGetEndorseSchemaNew.setOperator(mGlobalInput.Operator);
				  tLJSGetEndorseSchemaNew.setMakeDate(PubFun.getCurrentDate());
				  tLJSGetEndorseSchemaNew.setMakeTime(PubFun.getCurrentTime());
				  tLJSGetEndorseSchemaNew.setModifyDate(PubFun.getCurrentDate());
				  tLJSGetEndorseSchemaNew.setModifyTime(PubFun.getCurrentTime());
				  tLJSGetEndorseSchemaNew.setSubFeeOperationType(BqCode.Get_Prem);
				  mLJSGetEndorseSet.add(tLJSGetEndorseSchemaNew);
			  }
			  aLPPolSet.add(tLPPolSchema);
				//	    	mLPEdorMainSet add by huxj 2012-5-31
	    	    Reflections tRef=new    Reflections();
	    	    LPEdorMainSchema  tLPEdorMainSchema=new LPEdorMainSchema();
		      	tRef.transFields(tLPEdorMainSchema, polLPEdorItemSchema);

		      	tLPEdorMainSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		      	tLPEdorMainSchema.setChgAmnt( -tLCPolSchema.getAmnt());
		      	tLPEdorMainSchema.setChgPrem( -tLCPolSchema.getPrem());
		      	tLPEdorMainSchema.setGetMoney(-polMoney);
		      	tLPEdorMainSchema.setManageCom(mLPGrpEdorItemSchema.getManageCom());
		      	tLPEdorMainSchema.setOperator(mGlobalInput.Operator);
		      	tLPEdorMainSchema.setMakeDate(currDate);
		      	tLPEdorMainSchema.setMakeTime(currTime);
		      	tLPEdorMainSchema.setModifyDate(currDate);
		      	tLPEdorMainSchema.setModifyTime(currTime);
		      	tLPEdorMainSchema.setEdorState("1");
		      	mLPEdorMainSet.add(tLPEdorMainSchema);
		  }

	  }

	  map.put(aLPPolSet, "DELETE&INSERT");
	  map.put(saveLPEdorItemSet, "DELETE&INSERT");
	  map.put(mLJSGetEndorseSet, "DELETE&INSERT");
	  //  add by huxj 2012-5-31
	  map.put(mLPEdorMainSet, "DELETE&INSERT");
	  mLPGrpEdorItemSchema.setEdorState("3");
	  mLPGrpEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
	  mLPGrpEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
	  
	  map.put(mLPGrpEdorItemSchema, "DELETE&INSERT");
	  mResult.clear();
	  mResult.add(map);

	  return true;
  
  }
  /**
   * 准备需要保存的数据
   * @return
   */
  private boolean dealData() {

	  //待处理的数据
	  LPPolSet aLPPolSet = new LPPolSet();
	  

	  LPPolSchema tLPPolSchema = null;//险种保单
	  LCPolSchema tLCPolSchema = null;
	  
	  LPEdorItemSchema tLPEdorItemSchema = null;
	  LPEdorItemSchema polLPEdorItemSchema = null;
	  for (int i = 1; i <= mLPEdorItemSet.size(); i++) {
		  
		  tLPEdorItemSchema = mLPEdorItemSet.get(i);
		  ref.transFields(tLPEdorItemSchema, mLPGrpEdorItemSchema);
		  //判断保单是否理赔,如果有理赔,不能作保全项目
		  LLCasePolicyDB tLLCasePolicyDB = new LLCasePolicyDB();
		  tLLCasePolicyDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		  tLLCasePolicyDB.setContNo(tLPEdorItemSchema.getContNo());
		  LLCasePolicySet tLLCasePolicySet = tLLCasePolicyDB.query();
		  if (tLLCasePolicySet.size() > 0) {
			  mErrors.addOneError("该保单发生过理赔，不能申请终止险种!");
			  return false;
		  }
		  //end
		  
		  //-----------------被保人数据的处理 start--------------
		  LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		  tLCInsuredDB.setContNo(tLPEdorItemSchema.getContNo());
		  tLCInsuredDB.setInsuredNo(tLPEdorItemSchema.getInsuredNo());
		  if(!tLCInsuredDB.getInfo())
		  {
			  CError.buildErr(this, "查询被保人信息失败!");
			  return false;
		  }
		  
		  LCContDB tLCContDB = new LCContDB();
		  tLCContDB.setContNo(tLPEdorItemSchema.getContNo());
		  if(!tLCContDB.getInfo())
		  {
			  CError.buildErr(this, "查询被保人合同信息失败!");
			  return false; 
		  }
		  
		  //------------保单险种数据的处理 Start--------------
		  
		  //TODO 查询逻辑需要确认
		  //查询条件：1、有效的保单，2、未发生理赔的，3、未进入领取期的(ZT为短期险,不考虑年金险)
		  LCPolDB tLCPolDB = new LCPolDB();
	      tLCPolDB.setContNo(tLPEdorItemSchema.getContNo());
	      tLCPolDB.setInsuredNo(tLPEdorItemSchema.getInsuredNo());
	      tLCPolDB.setPolNo(tLPEdorItemSchema.getPolNo());
	      tLCPolDB.setAppFlag("1");
	      LCPolSet tLCPolSet = tLCPolDB.query();
	      if(tLCPolSet == null || tLCPolSet.size() < 1)
	      {
			  CError.buildErr(this, "查询被保人保单险种信息失败!");
			  return false; 
	      }
	      
	      for(int j = 1; j <= tLCPolSet.size(); j++)
	      {
	    	  double polMoney = 0.0;
	    	  tLCPolSchema = tLCPolSet.get(j);
	    	  tLPPolSchema = new LPPolSchema();
	    	  ref.transFields(tLPPolSchema, tLCPolSchema);
	    	  tLPPolSchema.setEdorNo(tLPEdorItemSchema.getEdorNo());
	    	  tLPPolSchema.setAppFlag("4");//终止状态
	    	  tLPPolSchema.setEdorType(tLPEdorItemSchema.getEdorType());
	    	  tLPPolSchema.setModifyDate(PubFun.getCurrentDate());
	    	  tLPPolSchema.setModifyTime(PubFun.getCurrentTime());
	    	  
	    	  
	    	  polLPEdorItemSchema = new LPEdorItemSchema();//细化保全明细
	    	  ref.transFields(polLPEdorItemSchema, tLPEdorItemSchema);
	    	  ref.transFields(polLPEdorItemSchema, mLPGrpEdorItemSchema);
	    	  polLPEdorItemSchema.setPolNo(tLPPolSchema.getPolNo());
	    	  polLPEdorItemSchema.setManageCom(mLPGrpEdorItemSchema.getManageCom());
	    	  polLPEdorItemSchema.setOperator(mGlobalInput.Operator);
	    	  polLPEdorItemSchema.setUWFlag("0");
	    	  polLPEdorItemSchema.setEdorState("1");
	    	  polLPEdorItemSchema.setMakeDate(currDate);
	    	  polLPEdorItemSchema.setMakeTime(currTime);
	    	  polLPEdorItemSchema.setModifyDate(currDate);
	    	  polLPEdorItemSchema.setModifyTime(currTime);
	    	  saveLPEdorItemSet.add(polLPEdorItemSchema);
	    	 	
	    	  //--------------开始计算退保金------------
	    	  try {
				polMoney = mGrpEdorCalZT.calZTData(polLPEdorItemSchema);
	    	  } catch (Exception e) {
	    		  CError.buildErr(this, "计算客户号为："+tLCPolSchema.getInsuredNo()+"的退保金失败!\n 其保单号为:"+tLCPolSchema.getPolNo());
				  return false; 
	    	  }
	    	  
	    	  if(polMoney < 0 )
	    	  {
	    		  CError.buildErr(this, "计算客户号为："+tLCPolSchema.getInsuredNo()+"的退保金失败!\n 其保单号为:"+tLCPolSchema.getPolNo());
				  return false; 
	    	  }
	    	  
	    	  if(polMoney > 0 )
	    	  {
	  	  		  	String sFeeType = BqCalBL.getFinType(tLPPolSchema.getEdorType(), "TB",tLCPolSchema.getPolNo());
	  			  	LJSGetEndorseSchema tLJSGetEndorseSchemaNew = new LJSGetEndorseSchema();
	  	  			tLJSGetEndorseSchemaNew.setGetNoticeNo(mLPGrpEdorItemSchema.getEdorNo());
	  	  			tLJSGetEndorseSchemaNew.setEndorsementNo(mLPGrpEdorItemSchema.getEdorNo());
	  	  			tLJSGetEndorseSchemaNew.setFeeOperationType(mLPGrpEdorItemSchema.getEdorType());
	  	  			tLJSGetEndorseSchemaNew.setFeeFinaType(sFeeType);
	  	  			tLJSGetEndorseSchemaNew.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
	  	  			tLJSGetEndorseSchemaNew.setGrpPolNo(tLPPolSchema.getGrpPolNo());
	  	  			tLJSGetEndorseSchemaNew.setContNo(tLPEdorItemSchema.getContNo());
	  	  			tLJSGetEndorseSchemaNew.setPolNo(tLPPolSchema.getPolNo());
	  	  			tLJSGetEndorseSchemaNew.setRiskCode(tLPPolSchema.getRiskCode());
	  	  			tLJSGetEndorseSchemaNew.setOtherNo(mLPGrpEdorItemSchema.getEdorNo());
	  	  			tLJSGetEndorseSchemaNew.setOtherNoType("3");
	  	  			tLJSGetEndorseSchemaNew.setDutyCode("000000");
	  	  			tLJSGetEndorseSchemaNew.setPayPlanCode("000000");
	  	  			tLJSGetEndorseSchemaNew.setGetDate(mLPGrpEdorItemSchema.getEdorValiDate());
	  	  			tLJSGetEndorseSchemaNew.setGetMoney(polMoney);
	  	  			tLJSGetEndorseSchemaNew.setAgentCode(tLPPolSchema.getAgentCode());
	  				tLJSGetEndorseSchemaNew.setAgentCom(tLPPolSchema.getAgentCom());
	  				tLJSGetEndorseSchemaNew.setAgentGroup(tLPPolSchema.getAgentGroup());
	  				tLJSGetEndorseSchemaNew.setAgentType(tLPPolSchema.getAgentType());
	  				tLJSGetEndorseSchemaNew.setInsuredNo(tLPPolSchema.getInsuredNo());
	  				tLJSGetEndorseSchemaNew.setKindCode(tLPPolSchema.getKindCode());
	  				tLJSGetEndorseSchemaNew.setAppntNo(tLPPolSchema.getAppntNo());
	  				tLJSGetEndorseSchemaNew.setRiskVersion(tLPPolSchema.getRiskVersion());
	  				tLJSGetEndorseSchemaNew.setHandler(tLPPolSchema.getHandler());
	  				tLJSGetEndorseSchemaNew.setApproveCode(tLPPolSchema.getApproveCode());
	  				tLJSGetEndorseSchemaNew.setApproveDate(tLPPolSchema.getApproveDate());
	  				tLJSGetEndorseSchemaNew.setApproveTime(tLPPolSchema.getApproveTime());
	  	  			tLJSGetEndorseSchemaNew.setManageCom(tLPPolSchema.getManageCom());
	  	  		    tLJSGetEndorseSchemaNew.setCurrency(tLPPolSchema.getCurrency());
	  	  			tLJSGetEndorseSchemaNew.setPolType("1");
	  	  			tLJSGetEndorseSchemaNew.setGetFlag("1");
	  	  			tLJSGetEndorseSchemaNew.setOperator(mGlobalInput.Operator);
	  	  			tLJSGetEndorseSchemaNew.setMakeDate(PubFun.getCurrentDate());
	  	  			tLJSGetEndorseSchemaNew.setMakeTime(PubFun.getCurrentTime());
	  	  			tLJSGetEndorseSchemaNew.setModifyDate(PubFun.getCurrentDate());
	  	  			tLJSGetEndorseSchemaNew.setModifyTime(PubFun.getCurrentTime());
	  	  			tLJSGetEndorseSchemaNew.setSubFeeOperationType(BqCode.Get_Prem);
	  	  			mLJSGetEndorseSet.add(tLJSGetEndorseSchemaNew);
	    	  }
	    	  aLPPolSet.add(tLPPolSchema);
	    	  	//	    	mLPEdorMainSet add by huxj 2012-5-31
	    	    Reflections tRef=new    Reflections();
	    	    LPEdorMainSchema  tLPEdorMainSchema=new LPEdorMainSchema();
		      	tRef.transFields(tLPEdorMainSchema, polLPEdorItemSchema);

		      	tLPEdorMainSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		      	tLPEdorMainSchema.setChgAmnt( -tLCPolSchema.getAmnt());
		      	tLPEdorMainSchema.setChgPrem( -tLCPolSchema.getPrem());
		      	tLPEdorMainSchema.setGetMoney(-polMoney);
		      	tLPEdorMainSchema.setManageCom(mLPGrpEdorItemSchema.getManageCom());
		      	tLPEdorMainSchema.setOperator(mGlobalInput.Operator);
		      	tLPEdorMainSchema.setMakeDate(currDate);
		      	tLPEdorMainSchema.setMakeTime(currTime);
		      	tLPEdorMainSchema.setModifyDate(currDate);
		      	tLPEdorMainSchema.setModifyTime(currTime);
		      	tLPEdorMainSchema.setEdorState("1");
		      	mLPEdorMainSet.add(tLPEdorMainSchema);
	    	  
	      }


	  }
	  
	  

	  map.put(aLPPolSet, "DELETE&INSERT");
	  map.put(saveLPEdorItemSet, "DELETE&INSERT");
	  map.put(mLJSGetEndorseSet, "DELETE&INSERT");
	  //  add by huxj 2012-5-31
	  map.put(mLPEdorMainSet, "DELETE&INSERT");
	  mLPGrpEdorItemSchema.setEdorState("3");
	  mLPGrpEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
	  mLPGrpEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
	  
	  map.put(mLPGrpEdorItemSchema, "DELETE&INSERT");
	  mResult.clear();
	  mResult.add(map);

	  return true;
  }
  
  
	/**
	 * 删除上次保存过的数据
	 * @return boolean
	 */
	public boolean delPData() {
		//清除P表中上次保存过的数据
		String edorno = mLPGrpEdorItemSchema.getEdorNo();
		String edortype = mLPGrpEdorItemSchema.getEdorType();
		String sqlWhere = " edorno = '" + edorno + "' and edortype = '" + edortype + "'";
		//外键 导致顺序修改
		map.put("delete from LPEdorItem where" + sqlWhere, "DELETE");
		map.put("delete from LPEdorMain where edorno = '" + edorno + "' ","DELETE");
		map.put("delete from lppol where" + sqlWhere, "DELETE");
		map.put("delete from lpcont where" + sqlWhere, "DELETE");
		map.put("delete from lpinsured where" + sqlWhere, "DELETE");
		map.put("delete from LPInsureAcc where" + sqlWhere, "DELETE");
		map.put("delete from LPInsureAccClass where" + sqlWhere, "DELETE");
		map.put("delete from LPInsureAccTrace where" + sqlWhere, "DELETE");
		map.put("delete from LJSGetEndorse " + " where EndorsementNo='" + edorno + "' and FeeOperationType='" + edortype + "'", "DELETE");
		map.put("delete from LPInsureAccFeeTrace " + "where " + sqlWhere, "DELETE");
		
		mLPGrpEdorItemSchema.setEdorState("3");
		mLPGrpEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
		mLPGrpEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
		  
		map.put(mLPGrpEdorItemSchema, "DELETE&INSERT");

		return true;
	}


  /**
   * 准备需要保存的数据
   * @return
   */
  private boolean deleteData() {
    //按个人保全主表进行处理
	String edorno = mLPGrpEdorItemSchema.getEdorNo();
	String edortype = mLPGrpEdorItemSchema.getEdorType();
	String sqlWhere = " edorno = '" + edorno + "' and edortype = '" + edortype + "'";
    for (int i = 1; i <= mLPEdorItemSet.size(); i++) {
		map.put("delete from LPEdorItem where polno = "+mLPEdorItemSet.get(i).getPolNo()+" and " + sqlWhere, "DELETE");
		map.put("delete from lppol where polno = "+mLPEdorItemSet.get(i).getPolNo()+" and " + sqlWhere, "DELETE");
		map.put("delete from LJSGetEndorse " + " where EndorsementNo='" + edorno + "' and FeeOperationType='" + edortype + "' and polno = '"+mLPEdorItemSet.get(i).getPolNo()+"'", "DELETE");
    }
	mLPGrpEdorItemSchema.setEdorState("3");
	mLPGrpEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
	mLPGrpEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
	map.put(mLPGrpEdorItemSchema, "DELETE&INSERT");
    mResult.clear();
    mResult.add(map);

    return true;
  }

public CErrors getErrors() {
	// TODO Auto-generated method stub
	return this.mErrors;
}

}
