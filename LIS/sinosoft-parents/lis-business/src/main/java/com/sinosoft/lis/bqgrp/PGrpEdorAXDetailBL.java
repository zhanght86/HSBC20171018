package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.bq.BqCalBL;
import com.sinosoft.lis.bq.BqCode;
import com.sinosoft.lis.db.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;



/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 团体保全集体下个人功能类</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author sunsx 2009-1-8 
 * @version 1.0
 */
public class PGrpEdorAXDetailBL
{
private static Logger logger = Logger.getLogger(PGrpEdorAXDetailBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData;
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;

    private LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet();
    private LPEdorMainSet mLPEdorMainSet = new LPEdorMainSet();
    private LPEdorItemSet saveLPEdorItemSet = new LPEdorItemSet();
    private LPEdorMainSet saveLPEdorMainSet = new LPEdorMainSet();
    private LPContStateSet saveLPContStateSet = new LPContStateSet();
    private LPGrpContStateSet saveLPGrpContStateSet = new LPGrpContStateSet();
    private LJSGetEndorseSet mLJSGetEndorseSet = new LJSGetEndorseSet();
    private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
    private GrpEdorCalZT mGrpEdorCalZT = new GrpEdorCalZT();
    /** 全局数据 */
    private GlobalInput mGlobalInput = new GlobalInput();
    private Reflections ref = new Reflections();


    private String currDate = PubFun.getCurrentDate();
    private String currTime = PubFun.getCurrentTime();
    private MMap map = new MMap();
    public PGrpEdorAXDetailBL()
    {
    }

    public boolean submitData(VData cInputData, String cOperate)
    {
        //将操作数据拷贝到本类中
        mInputData = (VData) cInputData.clone();
        mOperate = cOperate;

        //得到外部传入的数据
        if(!getInputData())
            return false;
        logger.debug("---End getInputData---");

        //数据校验操作
        if (!checkData())
            return false;
        logger.debug("---End checkData---");

        //数据准备操作
        if (mOperate.equals("INSERT||EDOR")) {
            if (!dealData()) {
              return false;
            }
        }
        //数据准备操作
        if (mOperate.equals("DELETE||EDOR"))
        {
            if (!deleteData())
                return false;
        }
        
        if (mOperate.equals("INSERT||SAVE")) {
            if (!saveData()) {
              return false;
            }
        }

        //　数据提交、保存
        PubSubmit tPubSubmit = new PubSubmit();

        if (!tPubSubmit.submitData(mResult, mOperate))
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tPubSubmit.mErrors);

            CError tError = new CError();
            tError.moduleName = "ContBL";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";

            this.mErrors.addOneError(tError);
            return false;
        }

        return true;
    }

    /**
     * 数据输出方法，供外界获取数据处理结果
     * @return 包含有数据查询结果字符串的VData对象
     */
    public VData getResult()
    {
        return mResult;
    }

    /**
     * 将外部传入的数据分解到本类的属性中
     * @param: 无
     * @return: boolean
     */
    private boolean getInputData()
    {
        try
        {
            mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) mInputData.
                                   getObjectByObjectName("LPGrpEdorItemSchema",
                    0);
            mLPEdorItemSet = (LPEdorItemSet) mInputData.getObjectByObjectName(
                    "LPEdorItemSet", 0);
            
            mLPEdorMainSet = (LPEdorMainSet) mInputData.getObjectByObjectName(
                    "LPEdorMainSet", 0);

            mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
                    "GlobalInput", 0);

        }
        catch (Exception e)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PGrpEdorXTDetailBL";
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
    private boolean checkData()
    {
        LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
        tLPGrpEdorItemDB.setSchema(mLPGrpEdorItemSchema);
        if (!tLPGrpEdorItemDB.getInfo())
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PInsuredBL";
            tError.functionName = "Preparedata";
            tError.errorMessage = "无保全申请数据!";
            logger.debug("------" + tError);
            this.mErrors.addOneError(tError);
            return false;
        }

        //将查询出来的团体保全主表数据保存至模块变量中，省去其它的重复查询
        mLPGrpEdorItemSchema.setSchema(tLPGrpEdorItemDB.getSchema());

        if (tLPGrpEdorItemDB.getEdorState().trim().equals("2"))
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PInsuredBL";
            tError.functionName = "Preparedata";
            tError.errorMessage = "该保全已经申请确认不能修改!";
            logger.debug("------" + tError);
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    /**
     * 准备需要保存的数据
     * @return
     */
    private boolean dealData(){
    	
  	  //待处理的数据
  	  LPContSet tLPContSet = new LPContSet();
  	  LPPolSet aLPPolSet = new LPPolSet();
  	  
  	  LPContSchema tLPContSchema = null;//合同
  	  LCContSchema tLCContSchema = null;

  	  LPPolSchema tLPPolSchema = null;//险种保单
  	  LCPolSchema tLCPolSchema = null;
  	  
  	  LPEdorItemSchema tLPEdorItemSchema = null;
  	  LPEdorMainSchema tLPEdorMainSchema = null;

  	  for (int i = 1; i <= mLPEdorMainSet.size(); i++) {
  		  
  		tLPEdorMainSchema = mLPEdorMainSet.get(i);
  		  ref.transFields(tLPEdorMainSchema, mLPGrpEdorItemSchema);

  		  //----------------合同数据的处理 start----------------
  		  
  		  LCContDB tLCContDB = new LCContDB();
  		  tLCContDB.setContNo(tLPEdorMainSchema.getContNo());
  		  if(!tLCContDB.getInfo())
  		  {
  			  CError.buildErr(this, "查询被保人合同信息失败!");
  			  return false; 
  		  }
  		  tLCContSchema = tLCContDB.getSchema();
  		  tLPContSchema = new LPContSchema();
  		  ref.transFields(tLPContSchema, tLCContSchema);
  		  tLPContSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
  		  tLPContSchema.setAppFlag("4");//终止状态
  		  tLPContSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
  		  tLPContSchema.setModifyDate(PubFun.getCurrentDate());
  		  tLPContSchema.setModifyTime(PubFun.getCurrentTime());
  		  tLPContSet.add(tLPContSchema);
  		  //----------------合同数据的处理 end----------------
  		  
  		  //------------保单险种数据的处理 Start--------------
  		  
  	      for(int j = 1; j <= mLPEdorItemSet.size(); j++)
  	      {
  	    	  
  	    	  tLPEdorItemSchema = mLPEdorItemSet.get(j);
  	    	  if(tLPEdorItemSchema.getContNo().equals(tLPEdorMainSchema.getContNo()))
  	    	  {
  	    		  double tXTMoney = tLPEdorItemSchema.getGetMoney();
  	    		  logger.debug("该险种的退费的金额为:"+tXTMoney);

  	    		  LCPolDB tLCPolDB = new LCPolDB();
  	    		  tLCPolDB.setPolNo(tLPEdorItemSchema.getPolNo());
  	    		  if(!tLCPolDB.getInfo())
  	    		  {
  	    			  CError.buildErr(this, "查询被保人合同信息失败!");
  	    			  return false; 
  	    		  }
  	    		  tLCPolSchema = tLCPolDB.getSchema();

  	    		  tLPPolSchema = new LPPolSchema();
  	    		  ref.transFields(tLPPolSchema, tLCPolSchema);
  	    		  tLPPolSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
  	    		  tLPPolSchema.setAppFlag("4");//终止状态
  	    		  tLPPolSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
  	    		  tLPPolSchema.setModifyDate(PubFun.getCurrentDate());
  	    		  tLPPolSchema.setModifyTime(PubFun.getCurrentTime());
  	    		  
  	    		  aLPPolSet.add(tLPPolSchema);
  	    		  
  	    		  if(tXTMoney>0)
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
  	    			  tLJSGetEndorseSchemaNew.setGetMoney(tXTMoney);
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


  	    		  ref.transFields(tLPEdorItemSchema, mLPGrpEdorItemSchema);
  	    		  tLPEdorItemSchema.setPolNo(tLPPolSchema.getPolNo());
  	    		  tLPEdorItemSchema.setGetMoney(tXTMoney);
  	    		  tLPEdorItemSchema.setManageCom(mLPGrpEdorItemSchema.getManageCom());
  	    		  tLPEdorItemSchema.setOperator(mGlobalInput.Operator);
  	    		  tLPEdorItemSchema.setUWFlag("0");
  	    		  tLPEdorItemSchema.setEdorState("1");
  	    		  tLPEdorItemSchema.setMakeDate(currDate);
  	    		  tLPEdorItemSchema.setMakeTime(currTime);
  	    		  tLPEdorItemSchema.setModifyDate(currDate);
  	    		  tLPEdorItemSchema.setModifyTime(currTime);
  	    		  saveLPEdorItemSet.add(tLPEdorItemSchema);
  	    		  saveLPContStateSet.add(mGrpEdorCalZT.creatContState(tLPEdorItemSchema));
  	    		  
  	    	  }
  	      }
  			  tLPEdorMainSchema.setManageCom(mLPGrpEdorItemSchema.getManageCom());
  			  tLPEdorMainSchema.setOperator(mGlobalInput.Operator);
  			  tLPEdorMainSchema.setUWState("0");
  			  tLPEdorMainSchema.setMakeDate(currDate);
  			  tLPEdorMainSchema.setMakeTime(currTime);
  			  tLPEdorMainSchema.setModifyDate(currDate);
  			  tLPEdorMainSchema.setModifyTime(currTime);
  			  saveLPEdorMainSet.add(tLPEdorMainSchema);
  	  }

  	  map.put(tLPContSet, "DELETE&INSERT");
  	  map.put(aLPPolSet, "DELETE&INSERT");
  	  map.put(saveLPEdorItemSet, "DELETE&INSERT");
  	  map.put(saveLPEdorMainSet, "DELETE&INSERT");
  	  map.put(saveLPContStateSet, "DELETE&INSERT");
  	  map.put(mLJSGetEndorseSet, "DELETE&INSERT");
  	  
  	  mLPGrpEdorItemSchema.setEdorState("3");
  	  mLPGrpEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
  	  mLPGrpEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
  	  
  	  map.put(mLPGrpEdorItemSchema, "DELETE&INSERT");
  	  mResult.clear();
  	  mResult.add(map);

  	  return true;
    }
    
    private boolean saveData() {
    	
  	  map.put("delete from lpgrppol where edorno = '"+mLPGrpEdorItemSchema.getEdorNo()+"' and edortype = '"+mLPGrpEdorItemSchema.getEdorType()+"' and grpcontno = '"+mLPGrpEdorItemSchema.getGrpContNo()+"'", "DELETE");
  	  map.put("delete from lpgrpcont where edorno = '"+mLPGrpEdorItemSchema.getEdorNo()+"' and edortype = '"+mLPGrpEdorItemSchema.getEdorType()+"' and grpcontno = '"+mLPGrpEdorItemSchema.getGrpContNo()+"'", "DELETE");
  	  map.put("delete from lpgrpcontstate where edorno = '"+mLPGrpEdorItemSchema.getEdorNo()+"' and edortype = '"+mLPGrpEdorItemSchema.getEdorType()+"' and grpcontno = '"+mLPGrpEdorItemSchema.getGrpContNo()+"'", "DELETE");
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

  		  logger.debug("该险种退保后在保的人数为:"+newPeoples);
  		  logger.debug("退保后团体险种结余的保费:"+newPrem);
  		  logger.debug("退保后团体险种结余的保额:"+newAmnt);
  		  logger.debug("退保后团体险种结余的份数:"+newMult);

  		  if(newPeoples == 0)
  		  {
  			tLPGrpPolSchema.setAppFlag("4");
  		  }
  		  tLPGrpPolSchema.setPeoples2(newPeoples);
  		  tLPGrpPolSchema.setPrem(newPrem);
  		  tLPGrpPolSchema.setAmnt(newAmnt);
  		  tLPGrpPolSchema.setMult(newMult);
  		  tLPGrpPolSet.add(tLPGrpPolSchema);
  	  }

  	  int OldContPeoples = 0;//合同下在保人数
  	  int ZTContPeoples = 0;

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
  	  LPContDB tLPContDB = new LPContDB();

  	  tLPContDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
  	  tLPContDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
  	  tLPContDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
  	  LPContSet tLPContSet = tLPContDB.query();
  	  if(tLPContSet == null || tLPContSet.size()<1)
  	  {
  		  ZTContPeoples = 0;
  	  }else 
  	  {
  		  ZTContPeoples = tLPContSet.size();
  	  }

  	  int newContPeoples = OldContPeoples-ZTContPeoples;
  	  double newContPrem = PubFun.round((oldContPrem-ZTContPrem), 2);
  	  double newContAmnt = PubFun.round((oldContAmnt-ZTContAmnt), 2);
  	  double newContMult = PubFun.round((oldContMult-ZTContMult), 2);
  	   
  	  logger.debug("该团体保单减人后在保的人数为:"+newContPeoples);
  	  logger.debug("该团体保单减人后的保费:"+newContPrem);
  	  logger.debug("该团体保单减人后的保额:"+newContAmnt);
  	  logger.debug("该团体保单减人后的份数:"+newContMult);

  	  tLPGrpContSchema.setPeoples(newContPeoples);
  	  if(newContPeoples == 0)
  	  {
  		tLPGrpContSchema.setAppFlag("4");
  		saveLPGrpContStateSet.add(mGrpEdorCalZT.creatGrpContState(mLPGrpEdorItemSchema));
  		map.put(saveLPGrpContStateSet, "DELETE&INSERT");
  	  }
  	  tLPGrpContSchema.setPrem(newContPrem);
  	  tLPGrpContSchema.setAmnt(newContAmnt);
  	  tLPGrpContSchema.setMult(newContMult);

  	  map.put(tLPGrpContSchema, "DELETE&INSERT");
  	  map.put(tLPGrpPolSet, "DELETE&INSERT");

  	  mLPGrpEdorItemSchema.setEdorState("1");
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
    private boolean deleteData()
    {
        //按个人保全主表进行处理
    	String edorno = mLPGrpEdorItemSchema.getEdorNo();
    	String edortype = mLPGrpEdorItemSchema.getEdorType();
    	String sqlWhere = " edorno = '" + edorno + "' and edortype = '" + edortype + "'";
        for (int i = 1; i <= mLPEdorItemSet.size(); i++) {
        	
    		map.put("delete from LPEdorMain where contno = "+mLPEdorItemSet.get(i).getContNo()+" and edorno = '" + edorno + "' ","DELETE");
    		map.put("delete from LPEdorItem where contno = "+mLPEdorItemSet.get(i).getContNo()+" and " + sqlWhere, "DELETE");
    		map.put("delete from lppol where contno = "+mLPEdorItemSet.get(i).getContNo()+" and " + sqlWhere, "DELETE");
    		map.put("delete from lpcont where contno = "+mLPEdorItemSet.get(i).getContNo()+" and " + sqlWhere, "DELETE");
    		map.put("delete from lpinsured where contno = "+mLPEdorItemSet.get(i).getContNo()+" and " + sqlWhere, "DELETE");
    		map.put("delete from LPContState where contno = "+mLPEdorItemSet.get(i).getContNo()+" and " + sqlWhere, "DELETE");
    		map.put("delete from LPInsureAcc where contno = "+mLPEdorItemSet.get(i).getContNo()+" and " + sqlWhere, "DELETE");
    		map.put("delete from LPInsureAccClass where contno = "+mLPEdorItemSet.get(i).getContNo()+" and " + sqlWhere, "DELETE");
    		map.put("delete from LPInsureAccTrace where contno = "+mLPEdorItemSet.get(i).getContNo()+" and " + sqlWhere, "DELETE");
    		map.put("delete from LJSGetEndorse " + " where EndorsementNo='" + edorno + "' and FeeOperationType='" + edortype + "' and  contno = '"+mLPEdorItemSet.get(i).getContNo()+"'", "DELETE");

        }
        
    	mLPGrpEdorItemSchema.setEdorState("3");
    	mLPGrpEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
    	mLPGrpEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
    	  
    	map.put(mLPGrpEdorItemSchema, "DELETE&INSERT");
    	
        mResult.clear();
        mResult.add(map);

        return true;
    }

}
