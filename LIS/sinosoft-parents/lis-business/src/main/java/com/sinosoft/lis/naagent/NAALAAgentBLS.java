/*
* <p>ClassName: ALAAgentBLS </p>
* <p>Description: ALAAgentBLS类文件 </p>
* <p>Copyright: Copyright (c) 2002</p>
* <p>Company: sinosoft </p>
* @Database: 销售管理
* @CreateDate：2003-01-09
 */
package com.sinosoft.lis.naagent;
import org.apache.log4j.Logger;

import com.sinosoft.lis.vbl.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;

import java.sql.*;

import com.sinosoft.lis.pubfun.*;

public class NAALAAgentBLS {
private static Logger logger = Logger.getLogger(NAALAAgentBLS.class);
  /** 错误处理类，每个需要错误处理的类中都放置该类 */
  public  CErrors mErrors=new CErrors();
  /** 数据操作字符串 */
  private String mOperate;
  private String currentDate = PubFun.getCurrentDate();
  private String currentTime = PubFun.getCurrentTime();
  public NAALAAgentBLS() {
  }
  public static void main(String[] args) {
  }
  /**
   *传输数据的公共方法
   */
  public boolean submitData(VData cInputData,String cOperate)
  {
    boolean tReturn =false;
    //将操作数据拷贝到本类中
    this.mOperate =cOperate;

    logger.debug("Start ALAAgentBLS Submit...");
    if(this.mOperate.equals("INSERT||MAIN"))
        {tReturn=saveLAAgent(cInputData);
       
        
        }
    
    if (this.mOperate.equals("DELETE||MAIN"))
        {tReturn=deleteLAAgent(cInputData);}
    if (this.mOperate.equals("UPDATE||ALL"))
        {tReturn=updateLAAgent(cInputData);}
    if (this.mOperate.equals("UPDATE||PART"))
        {tReturn=updatePartLAAgent(cInputData);}
    if (tReturn)
      logger.debug(" sucessful");
    else
      logger.debug("Save failed") ;
    logger.debug("End ALAAgentBLS Submit...");
    return tReturn;
  }
  /**
   * 保存函数
   * 
   * 
   * 
   * 
   * 
   */
  
  

  private boolean saveLAAgent(VData mInputData)
  {
    boolean tReturn =true;
    logger.debug("Start Save...");
    Connection conn;
    conn=null;
    conn=DBConnPool.getConnection();
    if (conn==null)
    {
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "ALAAgentBLS";
      tError.functionName = "saveData";
      tError.errorMessage = "数据库连接失败!";
      this.mErrors .addOneError(tError) ;
      return false;
    }
    try{
      conn.setAutoCommit(false);
      logger.debug("Start 代理人信息保存...");
      LAAgentDB tLAAgentDB=new LAAgentDB(conn);
      tLAAgentDB.setSchema((LAAgentSchema)mInputData.getObjectByObjectName("LAAgentSchema",0));
      String sAgentState = "";
      sAgentState = tLAAgentDB.getAgentState();
      logger.debug("sAgentState:"+sAgentState);
      if (!tLAAgentDB.insert())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tLAAgentDB.mErrors);
        CError tError = new CError();
        tError.moduleName = "ALAAgentBLS";
        tError.functionName = "saveAgentData";
        tError.errorMessage = "数据保存失败!";
        this.mErrors .addOneError(tError) ;
        conn.rollback();
        conn.close();
        return false;
      }
      logger.debug("end 代理人信息保存...");
      //tongmeng 2006-10-08 add
      //二次增员备份基本信息
      if(sAgentState!=null&&sAgentState.equals("02"))
      {
        logger.debug("Start 代理人备份信息保存...");
        LAAgentBDB tLAAgentBDB = new LAAgentBDB(conn);
        tLAAgentBDB.setSchema((LAAgentBSchema)mInputData.getObjectByObjectName("LAAgentBSchema",0));
        if (!tLAAgentBDB.insert())
        {
          // @@错误处理
          this.mErrors.copyAllErrors(tLAAgentBDB.mErrors);
          CError tError = new CError();
          tError.moduleName = "ALAAgentBLS";
          tError.functionName = "saveData";
          tError.errorMessage = "数据保存失败!";
          this.mErrors .addOneError(tError) ;
          conn.rollback();
          conn.close();
          return false;
        }
        logger.debug("end 代理人备份信息保存...");
      }

      logger.debug("start 行政及其附属信息保存...");
      LATreeDB tLATreeDB = new LATreeDB(conn);
      tLATreeDB.setSchema((LATreeSchema)mInputData.getObjectByObjectName("LATreeSchema",0));
      if (!tLATreeDB.insert())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tLATreeDB.mErrors);
        CError tError = new CError();
        tError.moduleName = "ALAAgentBLS";
        tError.functionName = "saveAgentData";
        tError.errorMessage = "数据保存失败!";
        this.mErrors .addOneError(tError) ;
        conn.rollback();
        conn.close();
        return false;
      }
//      LATreeAccessorySet tLATreeAccessorySet = new LATreeAccessorySet();
//      tLATreeAccessorySet.set((LATreeAccessorySet)mInputData.getObjectByObjectName("LATreeAccessorySet",0));
//      if (tLATreeAccessorySet.size()!=0)
//      {
//        LATreeAccessoryDBSet tLATreeAccessoryDBSet = new LATreeAccessoryDBSet(conn);
//        tLATreeAccessoryDBSet.set(tLATreeAccessorySet);
//        if (!tLATreeAccessoryDBSet.insert())
//        {
//          // @@错误处理
//          this.mErrors.copyAllErrors(tLATreeAccessoryDBSet.mErrors);
//          CError tError = new CError();
//          tError.moduleName = "ALAAgentBLS";
//          tError.functionName = "saveAgentData";
//          tError.errorMessage = "行政附属信息保存失败!";
//          this.mErrors .addOneError(tError) ;
//          conn.rollback();
//          conn.close();
//          return false;
//        }
//      }
      logger.debug("end 行政信息保存...");

      //tongmeng 2006-10-08 add
      //二次增员备份行政信息
      if(sAgentState!=null&&sAgentState.equals("02"))
      {
        logger.debug("start 备份代理人行政信息....");
        LATreeBDB tLATreeBDB = new LATreeBDB(conn);
        tLATreeBDB.setSchema((LATreeBSchema)mInputData.getObjectByObjectName("LATreeBSchema",0));
        if (!tLATreeBDB.insert())
        {
          // @@错误处理
          this.mErrors.copyAllErrors(tLATreeBDB.mErrors);
          CError tError = new CError();
          tError.moduleName = "ALAAgentBLS";
          tError.functionName = "saveData";
          tError.errorMessage = "LATreeB数据保存失败!";
          this.mErrors .addOneError(tError) ;
          conn.rollback();
          conn.close();
          return false;
        }
        logger.debug("end 备份代理人行政信息....");
      }
      logger.debug("begin 销售机构管理人员更新...");
      LABranchGroupSet tLABranchGroupSet = new LABranchGroupSet();
      tLABranchGroupSet.set((LABranchGroupSet)mInputData.getObjectByObjectName("LABranchGroupSet",0));
      if (tLABranchGroupSet.size()>0)
      {
        LABranchGroupDBSet tLABranchGroupDBSet = new LABranchGroupDBSet(conn);
        tLABranchGroupDBSet.set(tLABranchGroupSet);
        if (!tLABranchGroupDBSet.update())
        {
          // @@错误处理
          this.mErrors.copyAllErrors(tLABranchGroupDBSet.mErrors);
          CError tError = new CError();
          tError.moduleName = "ALAAgentBLS";
          tError.functionName = "saveAgentData";
          tError.errorMessage = "销售机构管理人员更新失败!";
          this.mErrors .addOneError(tError) ;
          conn.rollback();
          conn.close();
          return false;
        }
      }
      logger.debug("end 销售机构管理人员更新...");
      logger.debug("start 担保人信息保存...");
      LAWarrantorDBSet tLAWarrantorDBSet = new LAWarrantorDBSet(conn);
      tLAWarrantorDBSet.set((LAWarrantorSet)mInputData.getObjectByObjectName("LAWarrantorSet",0));
      if (!tLAWarrantorDBSet.insert())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tLAWarrantorDBSet.mErrors);
        CError tError = new CError();
        tError.moduleName = "ALAAgentBLS";
        tError.functionName = "saveWarrantorData";
        tError.errorMessage = "数据保存失败!";
        this.mErrors .addOneError(tError) ;
        conn.rollback();
        conn.close();
        return false;
      }
      logger.debug("end 担保人信息保存...");
      //tongmeng 2006-04-11 add
      //增加更新增员关系和育成关系
      logger.debug("Strat 更新增员关系...");
      LARelationDBSet tAddLARelationDBSet = new LARelationDBSet(conn);
      tAddLARelationDBSet.set((LARelationSet)mInputData.getObjectByObjectName("LARelationSet",9));
      if(tAddLARelationDBSet.size()>0)
      {
        if(!tAddLARelationDBSet.insert())
        {
          // @@错误处理
          CError tError = new CError();
          tError.moduleName = "ALAAgentBLS";
          tError.functionName = "saveAgentData";
          tError.errorMessage = "更新增员关系失败!";
          this.mErrors .addOneError(tError) ;
          conn.rollback();
          conn.close();
          return false;
        }
      }
      logger.debug("end 更新增员关系...");
      logger.debug("Strat 更新育成关系...");
      LARelationDBSet tRelaLARelationDBSet = new LARelationDBSet(conn);
      tRelaLARelationDBSet.set((LARelationSet)mInputData.getObjectByObjectName("LARelationSet",10));
      if(tRelaLARelationDBSet.size()>0)
      {
        if(!tRelaLARelationDBSet.insert())
        {
          // @@错误处理
          CError tError = new CError();
          tError.moduleName = "ALAAgentBLS";
          tError.functionName = "saveAgentData";
          tError.errorMessage = "更新育成关系失败!";
          this.mErrors .addOneError(tError) ;
          conn.rollback();
          conn.close();
          return false;
        }
      }
      logger.debug("end 更新育成关系...");

      logger.debug("start update LAAgentAuthorize ....");
      String tidno=tLAAgentDB.getIDNo().trim();
      String asql="select * from laagentauthorize where idno='?tidno?' and flag='1'";
      SQLwithBindVariables sqlbv=new SQLwithBindVariables();
      sqlbv.sql(asql);
      sqlbv.put("tidno", tidno);
      LAAgentAuthorizeDB tLAAgentAuthorizeDB = new LAAgentAuthorizeDB(conn);
      LAAgentAuthorizeSet tLAAgentAuthorizeSet = new LAAgentAuthorizeSet();
      LAAgentAuthorizeSet mLAAgentAuthorizeSet = new LAAgentAuthorizeSet();
      tLAAgentAuthorizeSet = tLAAgentAuthorizeDB.executeQuery(sqlbv);
      for(int i=1;i<=tLAAgentAuthorizeSet.size();i++)
      {
        LAAgentAuthorizeSchema tLAAgentAuthorizeSchema=tLAAgentAuthorizeSet.get(i);
        tLAAgentAuthorizeSchema.setFlag("0");
        tLAAgentAuthorizeSchema.setModifyDate(currentDate);
        tLAAgentAuthorizeSchema.setModifyTime(currentTime);
        mLAAgentAuthorizeSet.add(tLAAgentAuthorizeSchema);
      }
      if (mLAAgentAuthorizeSet.size()>0)
      {
        LAAgentAuthorizeDBSet tLAAgentAuthorizeDBSet = new LAAgentAuthorizeDBSet(conn);
        tLAAgentAuthorizeDBSet.set(mLAAgentAuthorizeSet);
        if (!tLAAgentAuthorizeDBSet.update())
        {
          // @@错误处理
          this.mErrors.copyAllErrors(mLAAgentAuthorizeSet.mErrors);
          CError tError = new CError();
          tError.moduleName = "ALAAgentBLS";
          tError.functionName = "saveAgentData";
          tError.errorMessage = "授权管理更新失败!";
          this.mErrors .addOneError(tError) ;
          conn.rollback();
          conn.close();
          return false;
        }
      }
      LAAssuMoneyDB tLAAssuMoneyDB=new LAAssuMoneyDB(conn);
      LAAssuMoneySchema tLAAssuMoneySchema=new LAAssuMoneySchema();
      tLAAssuMoneySchema=(LAAssuMoneySchema)mInputData.getObjectByObjectName("LAAssuMoneySchema",8);
      tLAAssuMoneyDB=tLAAssuMoneySchema.getDB();
      if(! tLAAssuMoneyDB.update())
      	
      {  this.mErrors.copyAllErrors(tLAAssuMoneyDB.mErrors);
      CError tError = new CError();
      tError.moduleName = "NAALAAgentBLS";
      tError.functionName = "saveData";
      tError.errorMessage = "更新代理人押金管理收费表纪录失败!";
      this.mErrors .addOneError(tError) ;
      
     
          conn.rollback();
          conn.close();
      
      	
       return false;	}
      
      if(sAgentState!=null&&sAgentState.equals("01")){
      LAAddControlDBSet tLAADDCONTROLDBSet=new LAAddControlDBSet(conn);
      LAAddControlSet tLAADDCONTROLSet=new LAAddControlSet();
      tLAADDCONTROLSet=(LAAddControlSet)mInputData.getObjectByObjectName("LAAddControlSet",8);
      tLAADDCONTROLDBSet.set(tLAADDCONTROLSet);
      if(! tLAADDCONTROLDBSet.update())
      	
      {  this.mErrors.copyAllErrors(tLAADDCONTROLDBSet.mErrors);
      CError tError = new CError();
      tError.moduleName = "NAALAAgentBLS";
      tError.functionName = "saveData";
      tError.errorMessage = "更新增员授权表纪录失败!";
      this.mErrors .addOneError(tError) ;
      
     
          conn.rollback();
          conn.close();
      
      	
       return false;	} 
      }
      
      conn.commit() ;
      conn.close();
    }
    catch (Exception ex)
    {
      // @@错误处理
      CError tError =new CError();
      tError.moduleName="ALAAgentBLS";
      tError.functionName="submitData";
      tError.errorMessage=ex.toString();
      this.mErrors .addOneError(tError);
      tReturn=false;
      try{
        conn.rollback();
        conn.close();
        }catch(Exception e){}
    }
    return tReturn;
  }
  /**
   * 保存函数
   */
  private boolean deleteLAAgent(VData mInputData)
  {
    boolean tReturn =true;
    logger.debug("Start Delete...");
    Connection conn;
    conn=null;
    conn=DBConnPool.getConnection();
    if (conn==null)
    {
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "ALAAgentBLS";
      tError.functionName = "saveData";
      tError.errorMessage = "数据库连接失败!";
      this.mErrors .addOneError(tError) ;
      return false;
    }
    try{
      conn.setAutoCommit(false);
      logger.debug("Start 删除代理人信息...");
      LAAgentDB tLAAgentDB=new LAAgentDB(conn);
      LAAgentSchema tLAAgentSchema = new LAAgentSchema();
      tLAAgentSchema = (LAAgentSchema)mInputData.getObjectByObjectName("LAAgentSchema",0);
      tLAAgentDB.setSchema(tLAAgentSchema);
      if (!tLAAgentDB.delete())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tLAAgentDB.mErrors);
        CError tError = new CError();
        tError.moduleName = "ALAAgentBLS";
        tError.functionName = "saveData";
        tError.errorMessage = "代理人数据删除失败!";
        this.mErrors .addOneError(tError) ;
        conn.rollback();
        conn.close();
        return false;
      }
      logger.debug("end 代理人信息删除...");
      logger.debug("start 删除行政信息...");
      LATreeDB tLATreeDB = new LATreeDB(conn);
      tLATreeDB.setSchema((LATreeSchema)mInputData.getObjectByObjectName("LATreeSchema",0));
      if (!tLATreeDB.delete())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tLATreeDB.mErrors);
        CError tError = new CError();
        tError.moduleName = "ALAAgentBLS";
        tError.functionName = "saveData";
        tError.errorMessage = "数据保存失败!";
        this.mErrors .addOneError(tError) ;
        conn.rollback();
        conn.close();
        return false;
      }
      LATreeAccessoryDB tLATreeAccessoryDB = new LATreeAccessoryDB(conn);
      tLATreeAccessoryDB.setAgentCode(tLATreeDB.getAgentCode());
      //tLATreeAccessoryDB.setAgentGrade(tLATreeDB.getAgentGrade());
      //tLATreeAccessoryDB.setSchema((LATreeAccessorySchema)mInputData.getObjectByObjectName("LATreeAccessorySchema",0));
      if (!tLATreeAccessoryDB.deleteSQL())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tLATreeAccessoryDB.mErrors);
        CError tError = new CError();
        tError.moduleName = "ALAAgentBLS";
        tError.functionName = "saveAgentData";
        tError.errorMessage = "行政附属信息删除失败!";
        this.mErrors .addOneError(tError) ;
        conn.rollback();
        conn.close();
        return false;
      }
      logger.debug("end 行政信息删除...");
      logger.debug("start 删除担保人信息...");
      LAWarrantorDB tLAWarrantorDB = new LAWarrantorDB(conn);
      //LAWarrantorSchema tLAWarrantorSchema = new LAWarrantorSchema();
      tLAWarrantorDB.setAgentCode(tLAAgentSchema.getAgentCode());
      if (!tLAWarrantorDB.deleteSQL())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tLAWarrantorDB.mErrors);
        CError tError = new CError();
        tError.moduleName = "ALAAgentBLS";
        tError.functionName = "saveData";
        tError.errorMessage = "担保人数据删除失败!";
        this.mErrors .addOneError(tError) ;
        conn.rollback();
        conn.close();
        return false;
      }
      logger.debug("end 担保人信息删除...");
      LABranchGroupSet tLABranchGroupSet = new LABranchGroupSet();
      tLABranchGroupSet.set((LABranchGroupSet)mInputData.getObjectByObjectName("LABranchGroupSet",0));
      if (tLABranchGroupSet.size()>0)
      {
        LABranchGroupDBSet tLABranchGroupDBSet = new LABranchGroupDBSet(conn);
        tLABranchGroupDBSet.set(tLABranchGroupSet);
        if (!tLABranchGroupDBSet.update())
        {
          // @@错误处理
          this.mErrors.copyAllErrors(tLABranchGroupDBSet.mErrors);
          CError tError = new CError();
          tError.moduleName = "ALAAgentBLS";
          tError.functionName = "saveData";
          tError.errorMessage = "担保人数据删除失败!";
          this.mErrors .addOneError(tError) ;
          conn.rollback();
          conn.close();
          return false;
        }
      }
      conn.commit() ;
      //conn.rollback();
      conn.close();
    }
    catch (Exception ex)
    {
      // @@错误处理
      CError tError =new CError();
      tError.moduleName="ALAAgentBLS";
      tError.functionName="submitData";
      tError.errorMessage=ex.toString();
      this.mErrors .addOneError(tError);
      tReturn=false;
      try{conn.rollback() ;
      conn.close();} catch(Exception e){}
    }
    return tReturn;
  }
  /**
   * 保存函数
   */
  private boolean updateLAAgent(VData mInputData)
  {
    boolean tReturn =true;
    logger.debug("Start Save...");
    Connection conn;
    conn=null;
    conn=DBConnPool.getConnection();
    if (conn==null)
    {
      CError tError = new CError();
      tError.moduleName = "ALAAgentBLS";
      tError.functionName = "updateData";
      tError.errorMessage = "数据库连接失败!";
      this.mErrors .addOneError(tError) ;
      return false;
    }
    try{
      conn.setAutoCommit(false);
      logger.debug("Start 代理人信息保存...");
      LAAgentDB tLAAgentDB=new LAAgentDB(conn);
      LAAgentSchema tLAAgentSchema=(LAAgentSchema)mInputData.getObjectByObjectName("LAAgentSchema",0);
      tLAAgentDB.setSchema(tLAAgentSchema);
      if (!tLAAgentDB.update())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tLAAgentDB.mErrors);
        CError tError = new CError();
        tError.moduleName = "ALAAgentBLS";
        tError.functionName = "saveData";
        tError.errorMessage = "数据保存失败!";
        this.mErrors .addOneError(tError) ;
        conn.rollback();
        conn.close();
        return false;
      }
      logger.debug("Start 代理人备份信息保存...");
      LAAgentBDB tLAAgentBDB = new LAAgentBDB(conn);
      tLAAgentBDB.setSchema((LAAgentBSchema)mInputData.getObjectByObjectName("LAAgentBSchema",0));
      if (!tLAAgentBDB.insert())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tLAAgentBDB.mErrors);
        CError tError = new CError();
        tError.moduleName = "ALAAgentBLS";
        tError.functionName = "saveData";
        tError.errorMessage = "数据保存失败!";
        this.mErrors .addOneError(tError) ;
        conn.rollback();
        conn.close();
        return false;
      }
      logger.debug("Start 行政信息保存...");
      LATreeDB tLATreeDB = new LATreeDB(conn);
      tLATreeDB.setSchema((LATreeSchema)mInputData.getObjectByObjectName("LATreeSchema",0));
      if (!tLATreeDB.update())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tLATreeDB.mErrors);
        CError tError = new CError();
        tError.moduleName = "ALAAgentBLS";
        tError.functionName = "saveData";
        tError.errorMessage = "LATree数据保存失败!";
        this.mErrors .addOneError(tError) ;
        conn.rollback();
        conn.close();
        return false;
      }
      LATreeBDB tLATreeBDB = new LATreeBDB(conn);
      tLATreeBDB.setSchema((LATreeBSchema)mInputData.getObjectByObjectName("LATreeBSchema",0));
      if (!tLATreeBDB.insert())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tLATreeBDB.mErrors);
        CError tError = new CError();
        tError.moduleName = "ALAAgentBLS";
        tError.functionName = "saveData";
        tError.errorMessage = "LATreeB数据保存失败!";
        this.mErrors .addOneError(tError) ;
        conn.rollback();
        conn.close();
        return false;
      }
      logger.debug("begin 销售机构管理人员更新...");
      LABranchGroupSet tLABranchGroupSet = new LABranchGroupSet();
      //LABranchGroupSchema tLABranchGroupSchema = new LABranchGroupSchema();
      tLABranchGroupSet.set((LABranchGroupSet)mInputData.getObjectByObjectName("LABranchGroupSet",0));
      if (tLABranchGroupSet.size()>0)
      {
        LABranchGroupDBSet tLABranchGroupDBSet = new LABranchGroupDBSet(conn);
        tLABranchGroupDBSet.set(tLABranchGroupSet);
        if (!tLABranchGroupDBSet.update())
        {
          // @@错误处理
          this.mErrors.copyAllErrors(tLABranchGroupDBSet.mErrors);
          CError tError = new CError();
          tError.moduleName = "ALAAgentBLS";
          tError.functionName = "saveAgentData";
          tError.errorMessage = "销售机构管理人员更新失败!";
          this.mErrors .addOneError(tError) ;
          conn.rollback();
          conn.close();
          return false;
        }
      }
      logger.debug("end 销售机构管理人员更新...");
      logger.debug("Start 担保人信息保存...");
      //删除原有记录
      LAWarrantorDB tLAWarrantorDB=new LAWarrantorDB(conn);
      LAWarrantorSchema tLAWarrantorSchema = new LAWarrantorSchema();
      tLAWarrantorSchema.setAgentCode(tLAAgentSchema.getAgentCode());
      tLAWarrantorDB.setSchema(tLAWarrantorSchema);
      if (!tLAWarrantorDB.deleteSQL())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tLAWarrantorDB.mErrors);
        CError tError = new CError();
        tError.moduleName = "ALAAgentBLS";
        tError.functionName = "deleteWarrantorData";
        tError.errorMessage = "数据保存失败!";
        this.mErrors .addOneError(tError) ;
        conn.rollback();
        conn.close();
        return false;
      }
      LAWarrantorDBSet tLAWarrantorDBSet=new LAWarrantorDBSet(conn);
      tLAWarrantorDBSet.set((LAWarrantorSet)mInputData.getObjectByObjectName("LAWarrantorSet",0));
      if (!tLAWarrantorDBSet.insert())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tLAAgentDB.mErrors);
        CError tError = new CError();
        tError.moduleName = "ALAAgentBLS";
        tError.functionName = "saveWarrantorData";
        tError.errorMessage = "数据保存失败!";
        this.mErrors .addOneError(tError) ;
        conn.rollback();
        conn.close();
        return false;
      }
      logger.debug("start 备份育成及增员关系数据...");
      LARelationBDBSet tLARelationBDBSet = new LARelationBDBSet(conn);
      tLARelationBDBSet.set((LARelationBSet)mInputData.getObjectByObjectName("LARelationBSet",8));
      if(tLARelationBDBSet.size()>0)
      {//by jiaqiangli 2006-07-26
        logger.debug("start 备份");
        if(!tLARelationBDBSet.insert())
        {
          // @@错误处理
          CError tError = new CError();
          tError.moduleName = "ALAAgentBLS";
          tError.functionName = "saveWarrantorData";
          tError.errorMessage = "育成及增员备份数据保存失败!";
          this.mErrors .addOneError(tError) ;
          conn.rollback();
          conn.close();
          return false;
        }
      }
      logger.debug("end 备份育成及增员关系数据...");
      logger.debug("start 删除育成及增员关系数据...");
      LARelationDBSet tLARelationDBSet = new LARelationDBSet(conn);
      tLARelationDBSet.set((LARelationSet)mInputData.getObjectByObjectName("LARelationSet",7));
      if(tLARelationDBSet.size()>0)
      {//by jiaqiangli 2006-07-26
        logger.debug("start 删除");
        if(!tLARelationDBSet.deleteSQL())
        {
          // @@错误处理
          CError tError = new CError();
          tError.moduleName = "ALAAgentBLS";
          tError.functionName = "saveWarrantorData";
          tError.errorMessage = "育成及增员备份数据删除失败!";
          this.mErrors .addOneError(tError) ;
          conn.rollback();
          conn.close();
          return false;
        }
      }
      logger.debug("end 删除育成及增员关系数据...");
      logger.debug("start 插入增员关系数据...");
      LARelationDBSet sLARelationDBSet = new LARelationDBSet(conn);
      sLARelationDBSet.set((LARelationSet)mInputData.getObjectByObjectName("LARelationSet",9));
      if(sLARelationDBSet.size()>0)
      {//by jiaqiangli 2006-07-26
        logger.debug("start 插入增员");
        if(!sLARelationDBSet.insert())
        {
          // @@错误处理
          CError tError = new CError();
          tError.moduleName = "ALAAgentBLS";
          tError.functionName = "saveWarrantorData";
          tError.errorMessage = "增员数据保存失败!";
          this.mErrors .addOneError(tError) ;
          conn.rollback();
          conn.close();
          return false;
        }
      }
      logger.debug("end 插入增员关系数据...");
      logger.debug("start 插入育成关系数据...");
      LARelationDBSet dLARelationDBSet = new LARelationDBSet(conn);
      dLARelationDBSet.set((LARelationSet)mInputData.getObjectByObjectName("LARelationSet",10));
      if(dLARelationDBSet.size()>0)
      {//by jiaqiangli 2006-07-26
        logger.debug("start 插入育成");
        if(!dLARelationDBSet.insert())
        {
          // @@错误处理
          CError tError = new CError();
          tError.moduleName = "ALAAgentBLS";
          tError.functionName = "saveWarrantorData";
          tError.errorMessage = "育成数据保存失败!";
          this.mErrors .addOneError(tError) ;
          conn.rollback();
          conn.close();
          return false;
        }
      }
      logger.debug("end 插入育成关系数据...");
      
      //增加代理人信息修改的更新操作
      LAAgentModCtlDBSet tLAAgentModCtlDBSet=new LAAgentModCtlDBSet(conn);
      LAAgentModCtlSet tLAAgentModCtlSet=new LAAgentModCtlSet();
      tLAAgentModCtlSet=(LAAgentModCtlSet)mInputData.getObjectByObjectName("LAAgentModCtlSet",8);
      tLAAgentModCtlDBSet.set(tLAAgentModCtlSet);
      if(! tLAAgentModCtlDBSet.update())
      	
      {  this.mErrors.copyAllErrors(tLAAgentModCtlDBSet.mErrors);
      CError tError = new CError();
      tError.moduleName = "NAALAAgentBLS";
      tError.functionName = "saveData";
      tError.errorMessage = "更新人员信息修改授权表纪录失败!";
      this.mErrors .addOneError(tError) ;
      
     
          conn.rollback();
          conn.close();
      
      	
       return false;	} 
      conn.commit() ;
      conn.close();
    }
    catch (Exception ex)
    {
      // @@错误处理
      CError tError =new CError();
      tError.moduleName="ALAAgentBLS";
      tError.functionName="submitData";
      tError.errorMessage=ex.toString();
      this.mErrors .addOneError(tError);
      tReturn=false;
      try
      {
        conn.rollback();
        conn.close();
      } catch
        (Exception e){}
    }
    return tReturn;
  }
  private boolean updatePartLAAgent(VData mInputData)
  {
    boolean tReturn =true;
    logger.debug("Start Save...");
    Connection conn;
    conn=null;
    conn=DBConnPool.getConnection();
    if (conn==null)
    {
      CError tError = new CError();
      tError.moduleName = "ALAAgentBLS";
      tError.functionName = "updateData";
      tError.errorMessage = "数据库连接失败!";
      this.mErrors .addOneError(tError) ;
      return false;
    }
    try{
      conn.setAutoCommit(false);
      logger.debug("Start 代理人信息保存...");
      LAAgentDB tLAAgentDB=new LAAgentDB(conn);
      LAAgentSchema tLAAgentSchema=(LAAgentSchema)mInputData.getObjectByObjectName("LAAgentSchema",0);
      tLAAgentDB.setSchema(tLAAgentSchema);
      if (!tLAAgentDB.update())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tLAAgentDB.mErrors);
        CError tError = new CError();
        tError.moduleName = "ALAAgentBLS";
        tError.functionName = "saveData";
        tError.errorMessage = "数据保存失败!";
        this.mErrors .addOneError(tError) ;
        conn.rollback();
        conn.close();
        return false;
      }
      logger.debug("Start 代理人备份信息保存...");
      LAAgentBDB tLAAgentBDB = new LAAgentBDB(conn);
      tLAAgentBDB.setSchema((LAAgentBSchema)mInputData.getObjectByObjectName("LAAgentBSchema",0));
      if (!tLAAgentBDB.insert())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tLAAgentBDB.mErrors);
        CError tError = new CError();
        tError.moduleName = "ALAAgentBLS";
        tError.functionName = "saveData";
        tError.errorMessage = "数据保存失败!";
        this.mErrors .addOneError(tError) ;
        conn.rollback();
        conn.close();
        return false;
      }

      logger.debug("Start 担保人信息保存...");
      //删除原有记录
      LAWarrantorDB tLAWarrantorDB=new LAWarrantorDB(conn);
      LAWarrantorSchema tLAWarrantorSchema = new LAWarrantorSchema();
      tLAWarrantorSchema.setAgentCode(tLAAgentSchema.getAgentCode());
      tLAWarrantorDB.setSchema(tLAWarrantorSchema);
      if (!tLAWarrantorDB.deleteSQL())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tLAWarrantorDB.mErrors);
        CError tError = new CError();
        tError.moduleName = "ALAAgentBLS";
        tError.functionName = "deleteWarrantorData";
        tError.errorMessage = "数据保存失败!";
        this.mErrors .addOneError(tError) ;
        conn.rollback();
        conn.close();
        return false;
      }
      logger.debug("begin 销售机构管理人员更新...");
      LABranchGroupSet tLABranchGroupSet = new LABranchGroupSet();
      //LABranchGroupSchema tLABranchGroupSchema = new LABranchGroupSchema();
      tLABranchGroupSet.set((LABranchGroupSet)mInputData.getObjectByObjectName("LABranchGroupSet",0));
      if (tLABranchGroupSet.size()>0)
      {
        LABranchGroupDBSet tLABranchGroupDBSet = new LABranchGroupDBSet(conn);
        tLABranchGroupDBSet.set(tLABranchGroupSet);
        if (!tLABranchGroupDBSet.update())
        {
          // @@错误处理
          this.mErrors.copyAllErrors(tLABranchGroupDBSet.mErrors);
          CError tError = new CError();
          tError.moduleName = "ALAAgentBLS";
          tError.functionName = "saveAgentData";
          tError.errorMessage = "销售机构管理人员更新失败!";
          this.mErrors .addOneError(tError) ;
          conn.rollback();
          conn.close();
          return false;
        }
      }
      logger.debug("end 销售机构管理人员更新...");
      logger.debug("Start 担保人信息保存...");

      LAWarrantorDBSet tLAWarrantorDBSet=new LAWarrantorDBSet(conn);
      tLAWarrantorDBSet.set((LAWarrantorSet)mInputData.getObjectByObjectName("LAWarrantorSet",0));
      if (!tLAWarrantorDBSet.insert())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tLAAgentDB.mErrors);
        CError tError = new CError();
        tError.moduleName = "ALAAgentBLS";
        tError.functionName = "saveWarrantorData";
        tError.errorMessage = "数据保存失败!";
        this.mErrors .addOneError(tError) ;
        conn.rollback();
        conn.close();
        return false;
      }
      
      
      //增加代理人信息修改的更新操作
      LAAgentModCtlDBSet tLAAgentModCtlDBSet=new LAAgentModCtlDBSet(conn);
      LAAgentModCtlSet tLAAgentModCtlSet=new LAAgentModCtlSet();
      tLAAgentModCtlSet=(LAAgentModCtlSet)mInputData.getObjectByObjectName("LAAgentModCtlSet",8);
      tLAAgentModCtlDBSet.set(tLAAgentModCtlSet);
      if(! tLAAgentModCtlDBSet.update())
      	
      {  this.mErrors.copyAllErrors(tLAAgentModCtlDBSet.mErrors);
      CError tError = new CError();
      tError.moduleName = "NAALAAgentBLS";
      tError.functionName = "saveData";
      tError.errorMessage = "更新人员信息修改授权表纪录失败!";
      this.mErrors .addOneError(tError) ;
      
     
          conn.rollback();
          conn.close();
      
      	
       return false;	} 
      conn.commit() ;
      //conn.rollback();
      conn.close();
    }
    catch (Exception ex)
    {
      // @@错误处理
      CError tError =new CError();
      tError.moduleName="ALAAgentBLS";
      tError.functionName="submitData";
      tError.errorMessage=ex.toString();
      this.mErrors .addOneError(tError);
      tReturn=false;
      try
      {
        conn.rollback();
        conn.close();
      } catch
        (Exception e){}
    }
    return tReturn;
  }
}
