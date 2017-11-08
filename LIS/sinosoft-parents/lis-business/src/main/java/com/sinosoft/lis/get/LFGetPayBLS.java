package com.sinosoft.lis.get;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.vschema.*;
import java.sql.*;
import com.sinosoft.lis.pubfun.*;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author TJJ
 * @version 1.0
 */
public class LFGetPayBLS  {
private static Logger logger = Logger.getLogger(LFGetPayBLS.class);
  /** 错误处理类，每个需要错误处理的类中都放置该类 */
  public  CErrors mErrors=new CErrors();
  /** 数据操作字符串 */
  private String mOperate;
  public LFGetPayBLS() {
  }
  public static void main(String[] args) {
    //  LFGetPayBLS LFGetPayBLS1 = new LFGetPayBLS();
  }

  /**
  传输数据的公共方法
  */
  public boolean submitData(VData cInputData,String cOperate)
  {
    boolean tReturn =true;
    //将操作数据拷贝到本类中
    this.mOperate =cOperate;

    logger.debug("Start LFGetPay BLS Submit...");
    if(this.mOperate.equals("INSERT||PERSON"))
    {
      if (!saveLFGetPay(cInputData)) {
      	logger.debug("saveLFGetPay failure");
        return false;
      }
    }

    logger.debug("End LFGetPay BLS Submit...-----");

    return tReturn;
  }

  /**
   * 生存领取的数据的保存函数
   */
  private boolean saveLFGetPay(VData mInputData)
  {	
    LJSGetSet mLJSGetSet = (LJSGetSet)mInputData.getObjectByObjectName("LJSGetSet",0);
    LJAGetSet mLJAGetSet = (LJAGetSet)mInputData.getObjectByObjectName("LJAGetSet",0);
    LJSGetDrawSet mLJSGetDrawSet = (LJSGetDrawSet)mInputData.getObjectByObjectName("LJSGetDrawSet",0);
    LJAGetDrawSet mLJAGetDrawSet = (LJAGetDrawSet)mInputData.getObjectByObjectName("LJAGetDrawSet",0);
    LCGetSet mLCGetSet = (LCGetSet)mInputData.getObjectByObjectName("LCGetSet",0);
 
    LJFIGetSet mLJFIGetSet = (LJFIGetSet)mInputData.getObjectByObjectName("LJFIGetSet",0);    
    LJTempFeeSet mLJTempFeeSet = (LJTempFeeSet)mInputData.getObjectByObjectName("LJTempFeeSet",0);
    LJTempFeeClassSet mLJTempFeeClassSet = (LJTempFeeClassSet)mInputData.getObjectByObjectName("LJTempFeeClassSet",0);
    LJAPaySet mLJAPaySet = (LJAPaySet)mInputData.getObjectByObjectName("LJAPaySet",0);
    LJAPayPersonSet mLJAPayPersonSet = (LJAPayPersonSet)mInputData.getObjectByObjectName("LJAPayPersonSet",0);
    LCInsureAccSet mLCInsureAccSet = (LCInsureAccSet)mInputData.getObjectByObjectName("LCInsureAccSet",0);
    LCInsureAccTraceSet mLCInsureAccTraceSet = (LCInsureAccTraceSet)mInputData.getObjectByObjectName("LCInsureAccTraceSet",0);
    
    
    for (int i = 1; i <= mLJSGetDrawSet.size(); i++) {
    	LJAGetDrawSchema tLJSGetDrawSchema = mLJAGetDrawSet.get(i);
    	
        //查询保单号，得到相应的付费方式
        LCPolDB tLCPolDB = new LCPolDB();
        tLCPolDB.setPolNo(tLJSGetDrawSchema.getPolNo());
        if (!tLCPolDB.getInfo()) {
             this.mErrors.copyAllErrors(tLCPolDB.mErrors);
             return false;
        }
        LCPolSchema tLCPolSchema = new LCPolSchema();
        tLCPolSchema.setSchema(tLCPolDB.getSchema());
      
        String flag = tLCPolSchema.getLiveGetMode();
        //默认的领取方式为现金方式
        if (flag == null || flag.trim().equals("")) {
      	  flag = "1";
        }       
         
        if (flag.equals("1")) {  //现金交费
            if (saveCash(mInputData))
                return true;
            else 
                return false;	        	
        }

        if (flag.equals("2")) {  //现金交费
            if (savePrem(mInputData))
                return true;
            else 
                return false;	        	
        }
   
    }
    return true;
  } 
  
  private boolean saveAcc(VData mInputData)
  {
    boolean tReturn =true;
    logger.debug("Start Save...");
    Connection conn = null;
    conn = DBConnPool.getConnection();

    if (conn==null)
    {
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "LFGetPayBLS";
      tError.functionName = "saveData";
      tError.errorMessage = "数据库连接失败!";
      this.mErrors .addOneError(tError) ;
      return false;
    }
    try
    {
      conn.setAutoCommit(false);
      logger.debug("Start 领取...");
      LCInsureAccDBSet tLCInsureAccDBSet=new LCInsureAccDBSet(conn);
      tLCInsureAccDBSet.set((LCInsureAccSet)mInputData.getObjectByObjectName("LCInsureAccDBSet",0));
      logger.debug("Get LCInsureAccDBSet");

      if (!tLCInsureAccDBSet.delete())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tLCInsureAccDBSet.mErrors);
        CError tError = new CError();
        tError.moduleName = "LFGetPayBLS";
        tError.functionName = "saveData";
        tError.errorMessage = "应付数据保存失败!";
        this.mErrors .addOneError(tError) ;
        conn.rollback();
        conn.close();
        return false;
      }

      if (!tLCInsureAccDBSet.insert())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tLCInsureAccDBSet.mErrors);
        CError tError = new CError();
        tError.moduleName = "LFGetPayBLS";
        tError.functionName = "saveData";
        tError.errorMessage = "应付数据保存失败!";
        this.mErrors .addOneError(tError) ;
        conn.rollback();
        conn.close();
        return false;
      }
      
      LCInsureAccTraceDBSet tLCInsureAccTraceDBSet=new LCInsureAccTraceDBSet(conn);
      tLCInsureAccTraceDBSet.set((LCInsureAccTraceSet)mInputData.getObjectByObjectName("LCInsureAccTraceSet",0));
      logger.debug("Get tLCInsureAccTraceSet");
      if (!tLCInsureAccTraceDBSet.insert())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tLCInsureAccTraceDBSet.mErrors);
        logger.debug("============"+tLCInsureAccTraceDBSet.mErrors);
        CError tError = new CError();
        tError.moduleName = "LFGetPayBLS";
        tError.functionName = "saveData";
        tError.errorMessage = "应付明细数据保存失败!";
        this.mErrors .addOneError(tError) ;
        conn.rollback();
        conn.close();
        return false;
      }
      
      LJSGetDrawDBSet tLJSGetDrawDBSet=new LJSGetDrawDBSet(conn);
      tLJSGetDrawDBSet.set((LJSGetDrawSet)mInputData.getObjectByObjectName("LJSGetDrawSet",0));
      logger.debug("Get LJSGetDraw");
      if (!tLJSGetDrawDBSet.delete())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tLJSGetDrawDBSet.mErrors);
        logger.debug("============"+tLJSGetDrawDBSet.mErrors);
        CError tError = new CError();
        tError.moduleName = "LFGetPayBLS";
        tError.functionName = "saveData";
        tError.errorMessage = "应付明细数据保存失败!";
        this.mErrors .addOneError(tError) ;
        conn.rollback();
        conn.close();
        return false;
      }

      LJSGetDBSet tLJSGetDBSet=new LJSGetDBSet(conn);
      tLJSGetDBSet.set((LJSGetSet)mInputData.getObjectByObjectName("LJSGetSet",0));
      logger.debug("Get LJSGet");

      if (!tLJSGetDBSet.delete())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tLJSGetDBSet.mErrors);
        CError tError = new CError();
        tError.moduleName = "LFGetPayBLS";
        tError.functionName = "saveData";
        tError.errorMessage = "应付数据保存失败!";
        this.mErrors .addOneError(tError) ;
        conn.rollback();
        conn.close();
        return false;
      }    
      logger.debug("success ----------------- 8888");
      //conn.rollback();
      conn.commit() ;
      conn.close();      
              
     } 
     catch (Exception ex)
     {
      // @@错误处理
      CError tError =new CError();
      tError.moduleName="LFGetPayBLS";
      tError.functionName="submitData";
      tError.errorMessage=ex.toString();
      this.mErrors .addOneError(tError);
      tReturn=false;
      try{conn.rollback() ;
      conn.close();} catch(Exception e){}
     }      
      return true;  	
  }
  
  
    
  private boolean saveCash(VData mInputData)
  {
    boolean tReturn =true;
    logger.debug("Start Save...");
    Connection conn = null;
    conn = DBConnPool.getConnection();

    if (conn==null)
    {
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "PGrpEdorConfirmWTBLS";
      tError.functionName = "saveData";
      tError.errorMessage = "数据库连接失败!";
      this.mErrors .addOneError(tError) ;
      return false;
    }
    try
    {
      conn.setAutoCommit(false);
      logger.debug("Start 领取...");
      LJAGetDBSet tLJAGetDBSet=new LJAGetDBSet(conn);
      tLJAGetDBSet.set((LJAGetSet)mInputData.getObjectByObjectName("LJAGetSet",0));
      logger.debug("Get LJAGet");
      if (!tLJAGetDBSet.insert())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tLJAGetDBSet.mErrors);
        CError tError = new CError();
        tError.moduleName = "LFGetPayBLS";
        tError.functionName = "saveData";
        tError.errorMessage = "应付数据保存失败!";
        this.mErrors .addOneError(tError) ;
        conn.rollback();
        conn.close();
        return false;
      }
      LJAGetDrawDBSet tLJAGetDrawDBSet=new LJAGetDrawDBSet(conn);
      tLJAGetDrawDBSet.set((LJAGetDrawSet)mInputData.getObjectByObjectName("LJAGetDrawSet",0));
      logger.debug("Get LJAGetDraw");
      if (!tLJAGetDrawDBSet.insert())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tLJAGetDrawDBSet.mErrors);
        logger.debug("============"+tLJAGetDrawDBSet.mErrors);
        CError tError = new CError();
        tError.moduleName = "LFGetPayBLS";
        tError.functionName = "saveData";
        tError.errorMessage = "应付明细数据保存失败!";
        this.mErrors .addOneError(tError) ;
        conn.rollback();
        conn.close();
        return false;
      }

      LCGetDBSet tLCGetDBSet = new LCGetDBSet(conn);
      tLCGetDBSet.set((LCGetSet)mInputData.getObjectByObjectName("LCGetSet",0));
      if (!tLCGetDBSet.update())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tLJAGetDrawDBSet.mErrors);
        logger.debug("============"+tLJAGetDrawDBSet.mErrors);
        CError tError = new CError();
        tError.moduleName = "LFGetPayBLS";
        tError.functionName = "saveData";
        tError.errorMessage = "给付项数据更新失败!";
        this.mErrors .addOneError(tError) ;
        conn.rollback();
        conn.close();
        return false;
      }

      LJSGetDrawDBSet tLJSGetDrawDBSet=new LJSGetDrawDBSet(conn);
      tLJSGetDrawDBSet.set((LJSGetDrawSet)mInputData.getObjectByObjectName("LJSGetDrawSet",0));
      logger.debug("Get LJSGetDraw");
      if (!tLJSGetDrawDBSet.delete())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tLJSGetDrawDBSet.mErrors);
        logger.debug("============"+tLJSGetDrawDBSet.mErrors);
        CError tError = new CError();
        tError.moduleName = "LFGetPayBLS";
        tError.functionName = "saveData";
        tError.errorMessage = "应付明细数据保存失败!";
        this.mErrors .addOneError(tError) ;
        conn.rollback();
        conn.close();
        return false;
      }

      LJSGetDBSet tLJSGetDBSet=new LJSGetDBSet(conn);
      tLJSGetDBSet.set((LJSGetSet)mInputData.getObjectByObjectName("LJSGetSet",0));
      logger.debug("Get LJSGet");

      if (!tLJSGetDBSet.delete())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tLJSGetDBSet.mErrors);
        CError tError = new CError();
        tError.moduleName = "LFGetPayBLS";
        tError.functionName = "saveData";
        tError.errorMessage = "应付数据保存失败!";
        this.mErrors .addOneError(tError) ;
        conn.rollback();
        conn.close();
        return false;
      }
      logger.debug("success ----------------- 8888");
      //conn.rollback();
      conn.commit() ;
      conn.close();
    }
    catch (Exception ex)
    {
      // @@错误处理
      CError tError =new CError();
      tError.moduleName="LFGetPayBLS";
      tError.functionName="submitData";
      tError.errorMessage=ex.toString();
      this.mErrors .addOneError(tError);
      tReturn=false;
      try{conn.rollback() ;
      conn.close();} catch(Exception e){}
    }
    return tReturn;
  }
  
  private boolean savePrem(VData mInputData)
  {
    boolean tReturn =true;
    logger.debug("Start Save...");
    Connection conn = null;
    conn = DBConnPool.getConnection();

    if (conn==null)
    {
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "PGrpEdorConfirmWTBLS";
      tError.functionName = "saveData";
      tError.errorMessage = "数据库连接失败!";
      this.mErrors .addOneError(tError) ;
      return false;
    }
    try
    {
      conn.setAutoCommit(false);
      logger.debug("Start 领取...");
      LJAGetDBSet tLJAGetDBSet=new LJAGetDBSet(conn);
      tLJAGetDBSet.set((LJAGetSet)mInputData.getObjectByObjectName("LJAGetSet",0));
      logger.debug("Get LJAGet");
      if (!tLJAGetDBSet.insert())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tLJAGetDBSet.mErrors);
        CError tError = new CError();
        tError.moduleName = "LFGetPayBLS";
        tError.functionName = "saveData";
        tError.errorMessage = "应付数据保存失败!";
        this.mErrors .addOneError(tError) ;
        conn.rollback();
        conn.close();
        return false;
      }
      LJAGetDrawDBSet tLJAGetDrawDBSet=new LJAGetDrawDBSet(conn);
      tLJAGetDrawDBSet.set((LJAGetDrawSet)mInputData.getObjectByObjectName("LJAGetDrawSet",0));
      logger.debug("Get LJAGetDraw");
      if (!tLJAGetDrawDBSet.insert())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tLJAGetDrawDBSet.mErrors);
        logger.debug("============"+tLJAGetDrawDBSet.mErrors);
        CError tError = new CError();
        tError.moduleName = "LFGetPayBLS";
        tError.functionName = "saveData";
        tError.errorMessage = "应付明细数据保存失败!";
        this.mErrors .addOneError(tError) ;
        conn.rollback();
        conn.close();
        return false;
      }

      LCGetDBSet tLCGetDBSet = new LCGetDBSet(conn);
      tLCGetDBSet.set((LCGetSet)mInputData.getObjectByObjectName("LCGetSet",0));
      if (!tLCGetDBSet.update())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tLJAGetDrawDBSet.mErrors);
        logger.debug("============"+tLJAGetDrawDBSet.mErrors);
        CError tError = new CError();
        tError.moduleName = "LFGetPayBLS";
        tError.functionName = "saveData";
        tError.errorMessage = "给付项数据更新失败!";
        this.mErrors .addOneError(tError) ;
        conn.rollback();
        conn.close();
        return false;
      }
      
      LJFIGetDBSet tLJFIGetDBSet = new LJFIGetDBSet(conn);
      tLJFIGetDBSet.set((LJFIGetSet)mInputData.getObjectByObjectName("LJFIGetSet",0));
      logger.debug("Get LJFIGetDBSet");
      if (!tLJFIGetDBSet.insert())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tLJFIGetDBSet.mErrors);
        logger.debug("============"+tLJFIGetDBSet.mErrors);
        CError tError = new CError();
        tError.moduleName = "LFGetPayBLS";
        tError.functionName = "savePrem";
        tError.errorMessage = "应付明细数据保存失败!";
        this.mErrors .addOneError(tError) ;
        conn.rollback();
        conn.close();
        return false;
      }
      
      LJAPayPersonDBSet tLJAPayPersonDBSet=new LJAPayPersonDBSet(conn);
      tLJAPayPersonDBSet.set((LJAPayPersonSet)mInputData.getObjectByObjectName("LJAPayPersonSet",0));
      logger.debug("Get LJAPayPersonSet");
      if (!tLJAPayPersonDBSet.insert())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tLJAPayPersonDBSet.mErrors);
        logger.debug("============"+tLJAPayPersonDBSet.mErrors);
        CError tError = new CError();
        tError.moduleName = "LFGetPayBLS";
        tError.functionName = "savePrem";
        tError.errorMessage = "应付明细数据保存失败!";
        this.mErrors .addOneError(tError) ;
        conn.rollback();
        conn.close();
        return false;
      }
      
      LJAPayDBSet tLJAPayDBSet=new LJAPayDBSet(conn);
      tLJAPayDBSet.set((LJAPaySet)mInputData.getObjectByObjectName("LJAPaySet",0));
      logger.debug("Get LJAPaySet");
      if (!tLJAPayDBSet.insert())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tLJAPayDBSet.mErrors);
        logger.debug("============"+tLJAPayDBSet.mErrors);
        CError tError = new CError();
        tError.moduleName = "LFGetPayBLS";
        tError.functionName = "savePrem";
        tError.errorMessage = "应付明细数据保存失败!";
        this.mErrors .addOneError(tError) ;
        conn.rollback();
        conn.close();
        return false;
      }
      

      LJTempFeeClassDBSet tLJTempFeeClassDBSet=new LJTempFeeClassDBSet(conn);
      tLJTempFeeClassDBSet.set((LJTempFeeClassSet)mInputData.getObjectByObjectName("LJTempFeeClassSet",0));
      logger.debug("Get LJTempFeeClassSet");
      if (!tLJTempFeeClassDBSet.insert())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tLJTempFeeClassDBSet.mErrors);
        logger.debug("============"+tLJTempFeeClassDBSet.mErrors);
        CError tError = new CError();
        tError.moduleName = "LFGetPayBLS";
        tError.functionName = "savePrem";
        tError.errorMessage = "应付明细数据保存失败!";
        this.mErrors .addOneError(tError) ;
        conn.rollback();
        conn.close();
        return false;
      }

      LJTempFeeDBSet tLJTempFeeDBSet=new LJTempFeeDBSet(conn);
      tLJTempFeeDBSet.set((LJTempFeeSet)mInputData.getObjectByObjectName("LJTempFeeSet",0));
      logger.debug("Get LJTempFeeSet");
      if (!tLJTempFeeDBSet.insert())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tLJTempFeeDBSet.mErrors);
        logger.debug("============"+tLJTempFeeDBSet.mErrors);
        CError tError = new CError();
        tError.moduleName = "LFGetPayBLS";
        tError.functionName = "savePrem";
        tError.errorMessage = "应付明细数据保存失败!";
        this.mErrors .addOneError(tError) ;
        conn.rollback();
        conn.close();
        return false;
      }
                  
      //-----------delete--------------------------
      
      LJSGetDrawDBSet tLJSGetDrawDBSet=new LJSGetDrawDBSet(conn);
      tLJSGetDrawDBSet.set((LJSGetDrawSet)mInputData.getObjectByObjectName("LJSGetDrawSet",0));
      logger.debug("Get LJSGetDraw");
      if (!tLJSGetDrawDBSet.delete())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tLJSGetDrawDBSet.mErrors);
        logger.debug("============"+tLJSGetDrawDBSet.mErrors);
        CError tError = new CError();
        tError.moduleName = "LFGetPayBLS";
        tError.functionName = "saveData";
        tError.errorMessage = "应付明细数据保存失败!";
        this.mErrors .addOneError(tError) ;
        conn.rollback();
        conn.close();
        return false;
      }

      LJSGetDBSet tLJSGetDBSet=new LJSGetDBSet(conn);
      tLJSGetDBSet.set((LJSGetSet)mInputData.getObjectByObjectName("LJSGetSet",0));
      logger.debug("Get LJSGet");

      if (!tLJSGetDBSet.delete())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tLJSGetDBSet.mErrors);
        CError tError = new CError();
        tError.moduleName = "LFGetPayBLS";
        tError.functionName = "saveData";
        tError.errorMessage = "应付数据保存失败!";
        this.mErrors .addOneError(tError) ;
        conn.rollback();
        conn.close();
        return false;
      }
      logger.debug("success ----------------- 8888");
      //conn.rollback();
      conn.commit() ;
      conn.close();
    }
    catch (Exception ex)
    {
      // @@错误处理
      CError tError =new CError();
      tError.moduleName="LFGetPayBLS";
      tError.functionName="submitData";
      tError.errorMessage=ex.toString();
      this.mErrors .addOneError(tError);
      tReturn=false;
      try{conn.rollback() ;
      conn.close();} catch(Exception e){}
    }
    return tReturn;
  }
  
  
}
