package com.sinosoft.lis.sys;
import org.apache.log4j.Logger;
import com.sinosoft.lis.db.LDRiskRuleDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LDRiskRuleBSchema;
import com.sinosoft.lis.schema.LDRiskRuleSchema;
import com.sinosoft.lis.vschema.LDRiskRuleBSet;
import com.sinosoft.lis.vschema.LDRiskRuleSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
/**
 * <p>Title: lis</p>
 * <p>Description: 个单分红计算 - 分红险种数据准备BL</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft</p>
 * @author tm
 * @version 1.0
 */

public class ProductSaleConfigBL {
private static Logger logger = Logger.getLogger(ProductSaleConfigBL.class);
  public CErrors mErrors = new CErrors();
  private String CurrentDate = PubFun.getCurrentDate();
  private String CurrentTime = PubFun.getCurrentTime();
  private GlobalInput mGlobalInput = new GlobalInput();

  private String mCalSQL = "";
  private String mOperator = "";
  //传入BLS的VData
  //LDRiskRule规则定义表
  //界面传入的
  private LDRiskRuleSet mLDRiskRuleSet_Input = new LDRiskRuleSet();
  //最终结果
  private LDRiskRuleSet mLDRiskRuleSet = new LDRiskRuleSet();
  private LDRiskRuleBSet mLDRiskRuleBSet = new LDRiskRuleBSet();
  
  private MMap mMMap = new MMap();
  
  private VData mOutputData = new VData();
  public ProductSaleConfigBL() {
  }

  /**
   * 程序处理入口
   * @param sInputData
   * @param tOperator
   * @return
   */
  public boolean submitData(VData sInputData,String tOperator)
  {
    try {
		this.mOperator = tOperator;
		//开始数据准备
		if(!this.getInputData(sInputData))
		{
		  return false;
		}
		//开始数据校验
		if(!this.checkData(tOperator))
		{
		  return false;
		}
		//业务逻辑处理
		if(!this.dealData())
		{
		  return false;
		}
		//更新数据准备
		if(!this.prepareOutData())
		{
		  return false;
		}
		//数据保存
		PubSubmit tPubSubmit = new PubSubmit();
		if(!tPubSubmit.submitData(this.mOutputData))
		{
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			return false;
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		CError.buildErr(this, e.toString());
		return false;
	}

    return true;
  }

  private boolean prepareOutData()
  {
    //update
    boolean tFlag = false;
   if(this.mLDRiskRuleBSet.size()>0)
   {
	   tFlag = true;
	   mMMap.put(this.mLDRiskRuleBSet,"INSERT"); 
   }
   if(tFlag)
   {
	   this.mMMap.put(this.mLDRiskRuleSet,"UPDATE");
   }
   else
   {
	   this.mMMap.put(this.mLDRiskRuleSet,"INSERT");
   }
   this.mOutputData.add(this.mMMap);
    return true;
  }
  /**
   * 业务逻辑处理
   * @return
   */
  private boolean dealData()
  {
    try {
      if(this.mOperator.equals("INSERT"))
      {
    	  //保存
    	  for(int i=1;i<=this.mLDRiskRuleSet_Input.size();i++)
    	  {
    		  LDRiskRuleSchema tLDRiskRuleSchema = new LDRiskRuleSchema();
    		  tLDRiskRuleSchema.setSchema(mLDRiskRuleSet_Input.get(i));
    		  //查询是否有记录
    		  LDRiskRuleDB tLDRiskRuleDB = new LDRiskRuleDB();
    		  tLDRiskRuleDB.setSchema(tLDRiskRuleSchema);
    		  if(tLDRiskRuleDB.getInfo())
    		  {
    			  
    			  
    			  //查询有结果，需要备份
    			  Reflections tReflections = new Reflections();
    			  LDRiskRuleBSchema tLDRiskRuleBSchema = new LDRiskRuleBSchema();
    			  tReflections.transFields(tLDRiskRuleBSchema, tLDRiskRuleDB.getSchema());
    			  tLDRiskRuleBSchema.setBakDate(this.CurrentDate);
    			  tLDRiskRuleBSchema.setBakTime(this.CurrentTime);
    			  String tSelNo = PubFun1.CreateMaxNo("SelNo", 10);
    			  tLDRiskRuleBSchema.setSerialNo(tSelNo);
    			  this.mLDRiskRuleBSet.add(tLDRiskRuleBSchema);
    			  //准备新数据
    			  tLDRiskRuleSchema.setMakeDate(tLDRiskRuleDB.getMakeDate());
    			  tLDRiskRuleSchema.setMakeTime(tLDRiskRuleDB.getMakeTime());
    			  tLDRiskRuleSchema.setModifyDate(this.CurrentDate);
    			  tLDRiskRuleSchema.setModifyTime(this.CurrentTime);
    			  
    		  }
    		  else
    		  {
    			  tLDRiskRuleSchema.setMakeDate(this.CurrentDate);
    			  tLDRiskRuleSchema.setMakeTime(this.CurrentTime);
    			  tLDRiskRuleSchema.setModifyDate(this.CurrentDate);
    			  tLDRiskRuleSchema.setModifyTime(this.CurrentTime);
    		  }
    		  
    		  tLDRiskRuleSchema.setOperator(this.mGlobalInput.Operator);
    		  
    		  //需要比较是否发生过修改，如果没有修改，不需要做处理。稍后加
    		  
    		  //准备当前的数据
    		  this.mLDRiskRuleSet.add(tLDRiskRuleSchema);
    		  
    	  }
      }
      
    }
    catch (Exception ex) {
      ex.printStackTrace();
      CError.buildErr(this,ex.toString());
      return false;
    }
    return true;
  }
  /**
   * 获得数据
   * @param sInputData
   * @return
   */
  private boolean getInputData(VData sInputData)
  {
    try {
      this.mGlobalInput = sInputData.getObjectByObjectName("GlobalInput", 0)==null?(new GlobalInput())
    		  :(GlobalInput)sInputData.getObjectByObjectName("GlobalInput", 0);
      this.mLDRiskRuleSet_Input = sInputData.getObjectByObjectName("LDRiskRuleSet", 0)==null?(new LDRiskRuleSet())
    		  :(LDRiskRuleSet)sInputData.getObjectByObjectName("LDRiskRuleSet", 0);
      if(mLDRiskRuleSet_Input.size()<=0)
      {
    	  CError.buildErr(this,"配置信息设置错误");
    	  return false;
      }
      
      return true;
    }
    catch (Exception ex) {
      ex.printStackTrace();
      CError.buildErr(this,ex.toString());
      return false;
    }
  }
  /**
   * 数据校验
   * @param tOpeartor
   * @return
   */
  private boolean checkData(String tOpeartor)
  {
    return true;
  }
 
  private String tReplaceSQL(String tSQL)
  {
    String tResult = "";
    int tBegin = 0;
    String tSql,tStr="",tStr1="";
    tSql=tSQL;
    try {
      tSql=StrTool.replaceEx(tSql,"'","''");
       logger.debug("tSql:"+tSql);
    }
    catch (Exception ex) {
      ex.printStackTrace();
      return tSQL;
    }
    tResult = tSql;
     logger.debug("tResult:"+tResult);
    return tResult;
  }
  /**
   * 解析字符串，替换变量。
   * @param tSQL
   * @param tTransferData
   * @return
   */
  public static String explanSQL(String tSQL,TransferData tTransferData)
  {
    String sResult = "";
    String tSql,tStr="",tStr1="";
    tSql=tSQL;
    try
    {
      while(true)
      {
        tStr=PubFun.getStr(tSql,2,"?");
        if (tStr.equals(""))
          break;
        tStr1="?"+tStr.trim()+"?";
        //替换变量
        tSql=StrTool.replaceEx(tSql,tStr1,getValueByName(tStr,tTransferData));
      }
    }
    catch(Exception ex)
    {
      // @@错误处理
      return "";
    }
    sResult = tSql;
    return sResult;
  }
  /**
   * 根据变量名得到变量的值
   * @return String 如果不正确返回"",否则返回变量值
   */
  public static String getValueByName(String cVarName,TransferData tTransferData)
  {
    String tReturn="";
    String tTemp = "";
    tReturn = (String)tTransferData.getValueByName(cVarName);
    return tReturn;
  }

  public static void main(String[] args) {
  //  BonusRiskPreBL bonusRiskPreBL1 = new BonusRiskPreBL();
  }
}
