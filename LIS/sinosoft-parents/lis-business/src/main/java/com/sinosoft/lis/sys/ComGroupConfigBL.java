package com.sinosoft.lis.sys;
import org.apache.log4j.Logger;
import com.sinosoft.lis.db.LDComGroupDB;
import com.sinosoft.lis.db.LDComToComGroupDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LDComGroupBSchema;
import com.sinosoft.lis.schema.LDComGroupSchema;
import com.sinosoft.lis.schema.LDComToComGroupBSchema;
import com.sinosoft.lis.schema.LDComToComGroupSchema;
import com.sinosoft.lis.vschema.LDComGroupBSet;
import com.sinosoft.lis.vschema.LDComGroupSet;
import com.sinosoft.lis.vschema.LDComToComGroupBSet;
import com.sinosoft.lis.vschema.LDComToComGroupSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
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

public class ComGroupConfigBL {
private static Logger logger = Logger.getLogger(ComGroupConfigBL.class);
  public CErrors mErrors = new CErrors();
  private String CurrentDate = PubFun.getCurrentDate();
  private String CurrentTime = PubFun.getCurrentTime();
  private GlobalInput mGlobalInput = new GlobalInput();

  private String mCalSQL = "";
  private String mOperator = "";
  //传入BLS的VData
  //LDRiskRule规则定义表
  //界面传入的
  private LDComGroupSet mLDComGroupSet_Input = new LDComGroupSet();
  private LDComToComGroupSet mLDComToComGroupSet_Input = new LDComToComGroupSet();
  //最终结果
  private LDComGroupSet mLDComGroupSet = new LDComGroupSet();
  private LDComGroupBSet mLDComGroupBSet = new LDComGroupBSet();
  
  private LDComToComGroupSet mLDComToComGroupSet = new LDComToComGroupSet();
  private LDComToComGroupBSet mLDComToComGroupBSet = new LDComToComGroupBSet();
  
  private MMap mMMap = new MMap();
  
  private String mDelComGroupCode = "";
  private VData mOutputData = new VData();
  public ComGroupConfigBL() {
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
	 if (this.mOperator.equals("INSERT")) {
			// update
			boolean tFlag = false;
			if (this.mLDComGroupBSet.size() > 0) {
				tFlag = true;
				mMMap.put(this.mLDComGroupBSet, "INSERT");
			}
			if (tFlag) {
				this.mMMap.put(this.mLDComGroupSet, "UPDATE");
			} else {
				this.mMMap.put(this.mLDComGroupSet, "INSERT");
			}
		}
	 else if(this.mOperator.equals("INSERTSUB"))
	 {
		 if(mDelComGroupCode==null||mDelComGroupCode.equals(""))
		 {
			 CError.buildErr(this,"机构信息编码为空!请查找原因!");
			 return false;
		 }
		 String tSQL = "delete from ldcomtocomgroup where comgroup='"+"?mDelComGroupCode?"+"'";
		 SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		 sqlbv.sql(tSQL);
		 sqlbv.put("mDelComGroupCode", mDelComGroupCode);
		 this.mMMap.put(sqlbv, "DELETE");
		 if(this.mLDComToComGroupBSet.size()>0)
		 {
			 this.mMMap.put(mLDComToComGroupBSet, "INSERT");
			 
		 }
		 if(this.mLDComToComGroupSet.size()>0)
		 {
			 this.mMMap.put(this.mLDComToComGroupSet,"INSERT");
		 }
	 }
	 else
	 {
		 //扩充算法保存
		 if(mDelComGroupCode==null||mDelComGroupCode.equals(""))
		 {
			 CError.buildErr(this,"机构信息编码为空!请查找原因!");
			 return false;
		 }
		 String tSQL = "delete from ldcomtocomgroup where comgroup='"+"?mDelComGroupCode?"+"'";
		 SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		 sqlbv.sql(tSQL);
		 sqlbv.put("mDelComGroupCode", mDelComGroupCode);
		 this.mMMap.put(sqlbv, "DELETE");
		 if(this.mLDComToComGroupBSet.size()>0)
		 {
			 this.mMMap.put(mLDComToComGroupBSet, "INSERT");
			 
		 }
		 if(this.mLDComToComGroupSet.size()>0)
		 {
			 this.mMMap.put(this.mLDComToComGroupSet,"INSERT");
		 }
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
    	  //机构组信息保存
    	  //保存
    	  for(int i=1;i<=this.mLDComGroupSet_Input.size();i++)
    	  {
    		  LDComGroupSchema tLDComGroupSchema = new LDComGroupSchema();
    		  tLDComGroupSchema.setSchema(mLDComGroupSet_Input.get(i));
    		  //查询是否有记录
    		  LDComGroupDB tLDComGroupDB = new LDComGroupDB();
    		  tLDComGroupDB.setSchema(tLDComGroupSchema);
    		  if(tLDComGroupDB.getInfo())
    		  {
    			  //查询有结果，需要备份
    			  Reflections tReflections = new Reflections();
    			  LDComGroupBSchema tLDComGroupBSchema = new LDComGroupBSchema();
    			  tReflections.transFields(tLDComGroupBSchema, tLDComGroupDB.getSchema());
    			  tLDComGroupBSchema.setBakDate(this.CurrentDate);
    			  tLDComGroupBSchema.setBakTime(this.CurrentTime);
    			  String tSelNo = PubFun1.CreateMaxNo("SelNo", 10);
    			  tLDComGroupBSchema.setSerialNo(tSelNo);
    			  this.mLDComGroupBSet.add(tLDComGroupBSchema);
    			  //准备新数据
    			  tLDComGroupSchema.setMakeDate(tLDComGroupDB.getMakeDate());
    			  tLDComGroupSchema.setMakeTime(tLDComGroupDB.getMakeTime());
    			  tLDComGroupSchema.setModifyDate(this.CurrentDate);
    			  tLDComGroupSchema.setModifyTime(this.CurrentTime);
    			  
    		  }
    		  else
    		  {
    			  tLDComGroupSchema.setMakeDate(this.CurrentDate);
    			  tLDComGroupSchema.setMakeTime(this.CurrentTime);
    			  tLDComGroupSchema.setModifyDate(this.CurrentDate);
    			  tLDComGroupSchema.setModifyTime(this.CurrentTime);
    			  //产生BatchNo
    			  String tBatchNo = PubFun1.CreateMaxNo("ComGroupNo", 20);
    			  tLDComGroupSchema.setBatchNo(tBatchNo);
    		  }
    		  
    		  tLDComGroupSchema.setOperator(this.mGlobalInput.Operator);
    		  
    		  //需要比较是否发生过修改，如果没有修改，不需要做处理。稍后加
    		  
    		  //准备当前的数据
    		  this.mLDComGroupSet.add(tLDComGroupSchema);
    		  
    	  }
      }
      else if(this.mOperator.equals("INSERTSUB"))
      {
    	  //正常机构映射关系配置
    	  String tSelNo = PubFun1.CreateMaxNo("SelNo", 10);
    	  for(int i=1;i<=this.mLDComToComGroupSet_Input.size();i++)
    	  {
    		  LDComToComGroupSchema tLDComToComGroupSchema = new LDComToComGroupSchema();
    		  tLDComToComGroupSchema.setSchema(mLDComToComGroupSet_Input.get(i));
    		  mDelComGroupCode = tLDComToComGroupSchema.getComGroup();
    		  if(tLDComToComGroupSchema.getComCode()==null||tLDComToComGroupSchema.getComCode().equals(""))
    		  {
    			  continue;
    		  }
    		  //判断该机构是否在其他机构组中
    		  String tSQL = "select comgroup from ldcomtocomgroup where comcode='"+"?comcode?"+"' "
    		              + " and comgroup <> '"+"?comgroup?"+"' ";
    		  ExeSQL tExeSQL = new ExeSQL();
    		  SQLwithBindVariables sqlbv=new SQLwithBindVariables();
  			  sqlbv.sql(tSQL);
  			  sqlbv.put("comcode", tLDComToComGroupSchema.getComCode());
  			 sqlbv.put("comgroup", tLDComToComGroupSchema.getComGroup());
    		  String tValue = tExeSQL.getOneValue(sqlbv);
//    		  if(tValue!=null&&!tValue.equals(""))
//    		  {
//    			  CError.buildErr(this,"机构编码:"+tLDComToComGroupSchema.getComCode()+"属于机构组:"+tValue+",不能保存!");
//    			  return false;
//    		  }
    		  //查询是否有记录
    		  LDComToComGroupDB tLDComToComGroupDB = new LDComToComGroupDB();
    		  tLDComToComGroupDB.setSchema(tLDComToComGroupSchema);
    		  if(tLDComToComGroupDB.getInfo())
    		  {
    			  //查询有结果，需要备份
    			  Reflections tReflections = new Reflections();
    			  LDComToComGroupBSchema tLDComToComGroupBSchema = new LDComToComGroupBSchema();
    			  tReflections.transFields(tLDComToComGroupBSchema, tLDComToComGroupDB.getSchema());
    			  tLDComToComGroupBSchema.setBakDate(this.CurrentDate);
    			  tLDComToComGroupBSchema.setBakTime(this.CurrentTime);
    			 
    			  tLDComToComGroupBSchema.setSerialNo(tSelNo);
    			  this.mLDComToComGroupBSet.add(tLDComToComGroupBSchema);
    			  //准备新数据
    			  tLDComToComGroupSchema.setMakeDate(tLDComToComGroupDB.getMakeDate());
    			  tLDComToComGroupSchema.setMakeTime(tLDComToComGroupDB.getMakeTime());
    			  tLDComToComGroupSchema.setModifyDate(this.CurrentDate);
    			  tLDComToComGroupSchema.setModifyTime(this.CurrentTime);
    			  
    		  }
    		  else
    		  {
    			  tLDComToComGroupSchema.setMakeDate(this.CurrentDate);
    			  tLDComToComGroupSchema.setMakeTime(this.CurrentTime);
    			  tLDComToComGroupSchema.setModifyDate(this.CurrentDate);
    			  tLDComToComGroupSchema.setModifyTime(this.CurrentTime);
    		  }
    		  
    		  tLDComToComGroupSchema.setOperator(this.mGlobalInput.Operator);
    		 
    		  //需要比较是否发生过修改，如果没有修改，不需要做处理。稍后加
    		  
    		  //准备当前的数据
    		  this.mLDComToComGroupSet.add(tLDComToComGroupSchema);
    		  
    	  } 
    	  if(mDelComGroupCode==null||mDelComGroupCode.equals(""))
 		 {
 			 CError.buildErr(this,"机构信息编码为空!请查找原因!");
 			 return false;
 		 }
		  //判断是否有该机构组编码
		  String tSQL = "select count(*) from LDComGroup where comgroup='"+"?mDelComGroupCode?"+"'";
		  String tValue = "";
		  ExeSQL tExeSQL = new ExeSQL();
		  SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			 sqlbv.sql(tSQL);
			 sqlbv.put("mDelComGroupCode", mDelComGroupCode);
		  tValue = tExeSQL.getOneValue(sqlbv);
		  if(tValue==null||tValue.equals("")||Integer.parseInt(tValue)<=0)
		  {
			  CError.buildErr(this,"机构组"+mDelComGroupCode+"不存在,请先维护机构组!");
			  return false;
		  }
      }
      else if(this.mOperator.equals("OTHERSAVE"))
      {
    	  //扩充算法保存
    	  for(int i=1;i<=this.mLDComGroupSet_Input.size();i++)
    	  {
    		  LDComGroupSchema tLDComGroupSchema = new LDComGroupSchema();
    		  tLDComGroupSchema = mLDComGroupSet_Input.get(i);
    		  String tComGroup = tLDComGroupSchema.getComGroup();
    		  mDelComGroupCode = tComGroup;
    		  String tBatchNo = tLDComGroupSchema.getBatchNo();
    		  String tSQL = tLDComGroupSchema.getCalSQL();
    		  if(tSQL==null||tSQL.trim().equals(""))
    		  {
    			  CError.buildErr(this,"扩充算法为空!");
    			  return false;
    		  }
    		  SSRS tSSRS = new SSRS();
    		  ExeSQL tExeSQL = new ExeSQL();
    		  tSSRS = tExeSQL.execSQL(tSQL);
    		  if(tExeSQL.mErrors.getErrorCount()>0)
    		  {
    			  this.mErrors = tExeSQL.mErrors;
    			  return false;
    		  }
    		  if(tSSRS==null||tSSRS.getMaxRow()<=0)
    		  {
    			  CError.buildErr(this,"扩充算法查询无结果,不进行保存!");
    			  return false;
    		  }
    		  String tSelNo = PubFun1.CreateMaxNo("SelNo", 10);
    		  for(int n=1;n<=tSSRS.getMaxRow();n++)
    		  {
    			  String tComCode = tSSRS.GetText(n,1);
    			  //判断该机构是否在其他机构组中
        		  String tSQL1 = "select comgroup from ldcomtocomgroup where comcode='"+"?tComCode?"+"' "
        		              + " and comgroup <> '"+"?tComGroup?"+"' ";
        		  ExeSQL tExeSQL1 = new ExeSQL();
        		  SQLwithBindVariables sqlbv=new SQLwithBindVariables();
     			   sqlbv.sql(tSQL1);
     			   sqlbv.put("tComCode", tComCode);
     			   sqlbv.put("tComGroup", tComGroup);
        		  String tValue = tExeSQL.getOneValue(sqlbv);
//        		  if(tValue!=null&&!tValue.equals(""))
//        		  {
//        			  CError.buildErr(this,"机构编码:"+tComCode+"属于机构组:"+tValue+",不能保存!");
//        			  return false;
//        		  }
        		  
    			  LDComToComGroupSchema tLDComToComGroupSchema = new LDComToComGroupSchema();
    			  tLDComToComGroupSchema.setComGroup(tComGroup);
    			  tLDComToComGroupSchema.setComCode(tComCode);
    			  tLDComToComGroupSchema.setBatchNo(tBatchNo);
    			  
        		  //查询是否有记录
        		  LDComToComGroupDB tLDComToComGroupDB = new LDComToComGroupDB();
        		  tLDComToComGroupDB.setSchema(tLDComToComGroupSchema);
        		  if(tLDComToComGroupDB.getInfo())
        		  {
        			  //查询有结果，需要备份
        			  Reflections tReflections = new Reflections();
        			  LDComToComGroupBSchema tLDComToComGroupBSchema = new LDComToComGroupBSchema();
        			  tReflections.transFields(tLDComToComGroupBSchema, tLDComToComGroupDB.getSchema());
        			  tLDComToComGroupBSchema.setBakDate(this.CurrentDate);
        			  tLDComToComGroupBSchema.setBakTime(this.CurrentTime);
        			 
        			  tLDComToComGroupBSchema.setSerialNo(tSelNo);
        			  this.mLDComToComGroupBSet.add(tLDComToComGroupBSchema);
        			  //准备新数据
        			  tLDComToComGroupSchema.setMakeDate(tLDComToComGroupDB.getMakeDate());
        			  tLDComToComGroupSchema.setMakeTime(tLDComToComGroupDB.getMakeTime());
        			  tLDComToComGroupSchema.setModifyDate(this.CurrentDate);
        			  tLDComToComGroupSchema.setModifyTime(this.CurrentTime); 
        		  }
        		  else
        		  {
        			  tLDComToComGroupSchema.setMakeDate(this.CurrentDate);
        			  tLDComToComGroupSchema.setMakeTime(this.CurrentTime);
        			  tLDComToComGroupSchema.setModifyDate(this.CurrentDate);
        			  tLDComToComGroupSchema.setModifyTime(this.CurrentTime);
        		  }
        		  
        		  tLDComToComGroupSchema.setOperator(this.mGlobalInput.Operator);
        		  //需要比较是否发生过修改，如果没有修改，不需要做处理。稍后加
        		  //准备当前的数据
        		  this.mLDComToComGroupSet.add(tLDComToComGroupSchema);
    		  }
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
      if(this.mOperator.equals("INSERT")||this.mOperator.equals("OTHERSAVE"))
      {
    	  //机构组信息维护
          this.mLDComGroupSet_Input = sInputData.getObjectByObjectName("LDComGroupSet", 0)==null?(new LDComGroupSet())
        		  :(LDComGroupSet)sInputData.getObjectByObjectName("LDComGroupSet", 0);
          if(mLDComGroupSet_Input.size()<=0)
          {
        	  CError.buildErr(this,"机构组配置信息错误");
        	  return false;
          }
      }
      else
      {
    	  //机构映射配置
    	  this.mLDComToComGroupSet_Input = sInputData.getObjectByObjectName("LDComToComGroupSet", 0)==null?(new LDComToComGroupSet())
        		  :(LDComToComGroupSet)sInputData.getObjectByObjectName("LDComToComGroupSet", 0);
//    	  if(mLDComToComGroupSet_Input.size()<=0)
//          {
//        	  CError.buildErr(this,"机构组映射信息错误");
//        	  return false;
//          }
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
