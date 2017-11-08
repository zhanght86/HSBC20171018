package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.JdbcUrl;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.vbl.*;
import java.sql.*;
import com.sinosoft.lis.pubfun.*;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author HZM
 * @version 1.0
 */

public class NormPayGrpChooseOperBLS  {
private static Logger logger = Logger.getLogger(NormPayGrpChooseOperBLS.class);
  //错误处理类，每个需要错误处理的类中都放置该类
  public  CErrors mErrors=new CErrors();
  /** 数据操作字符串 */
  private String mOperate;

  public NormPayGrpChooseOperBLS() {
  }
  public static void main(String[] args) {
    NormPayGrpChooseOperBLS mNormPayGrpChooseOperBLS1 = new NormPayGrpChooseOperBLS();
  }

  //传输数据的公共方法
  public boolean submitData(VData cInputData,String cOperate)
  {
    boolean tReturn =false;
    //将操作数据拷贝到本类中
    this.mOperate =cOperate;
    logger.debug("Start NormPayGrpChooseOper BLS Submit...");
    //信息保存
    if(this.mOperate.equals("VERIFY")||this.mOperate.equals("HEATHVERIFY"))
    {tReturn=verify(cInputData);}

    if (tReturn)
      logger.debug("Verify sucessful");
    else
      logger.debug("Verify failed") ;

      logger.debug("End NormPayGrpChooseOper BLS Submit...");

    return tReturn;
  }

//核销事务操作
  private boolean verify(VData mInputData)
  {
    boolean tReturn =true;
    logger.debug("Start Verify...");
    Connection conn = DBConnPool.getConnection();
    if (conn==null)
    {
    		// @@错误处理
    		CError tError = new CError();
		tError.moduleName = "NormPayGrpChooseOperBLS";
                tError.functionName = "verifyData";
	        tError.errorMessage = "数据库连接失败!";
		this.mErrors .addOneError(tError) ;
 		return false;
    }
    try{
      conn.setAutoCommit(false);

// 暂交费分类表更新
      logger.debug("Start 暂交费分类表...");
      LJTempFeeClassDBSet tLJTempFeeClassDBSet=new LJTempFeeClassDBSet(conn);
      tLJTempFeeClassDBSet.set((LJTempFeeClassSet)mInputData.getObjectByObjectName("LJTempFeeClassSet",0));
      if (!tLJTempFeeClassDBSet.update())
      {
    		// @@错误处理
		this.mErrors.copyAllErrors(tLJTempFeeClassDBSet.mErrors);
    		CError tError = new CError();
	        tError.moduleName = "NormPayGrpChooseOperBLS";
	        tError.functionName = "updateData";
	        tError.errorMessage = "暂交费表数据保存失败!";
	        this.mErrors .addOneError(tError) ;
        conn.rollback() ;
        conn.close();
 	        return false;
      }
      logger.debug("暂交费分类表更新");

// 暂交费表更新
      logger.debug("Start 暂交费表...");
      LJTempFeeDB tLJTempFeeDB=new LJTempFeeDB(conn);
      tLJTempFeeDB.setSchema((LJTempFeeBL)mInputData.getObjectByObjectName("LJTempFeeBL",0));
      if (!tLJTempFeeDB.update())
      {
    		// @@错误处理
		this.mErrors.copyAllErrors(tLJTempFeeDB.mErrors);
    		CError tError = new CError();
		tError.moduleName = "NormPayGrpChooseOperBLS";
		tError.functionName = "updateData";
		tError.errorMessage = "暂交费表数据保存失败!";
		this.mErrors .addOneError(tError) ;
        conn.rollback() ;
        conn.close();
 		return false;
      }
logger.debug("暂交费表更新");


//实收总表添加
logger.debug("Start 实收总表...");
      LJAPayDB tLJAPayDB=new LJAPayDB(conn);
      tLJAPayDB.setSchema((LJAPayBL)mInputData.getObjectByObjectName("LJAPayBL",0));
      if (!tLJAPayDB.insert())
      {
    		// @@错误处理
		this.mErrors.copyAllErrors(tLJAPayDB.mErrors);
    		CError tError = new CError();
		tError.moduleName = "NormPayGrpChooseOperBLS";
		tError.functionName = "saveData";
		tError.errorMessage = "实收总表数据保存失败!";
		this.mErrors .addOneError(tError) ;
        conn.rollback() ;
        conn.close();
 		return false;
      }
logger.debug("实收总表添加");



//实收个人交费表添加
logger.debug("Start 实收个人交费表...");
      LJAPayPersonDBSet tLJAPayPersonDBSet=new LJAPayPersonDBSet(conn);
      tLJAPayPersonDBSet.set((LJAPayPersonSet)mInputData.getObjectByObjectName("LJAPayPersonSet",0));
      if (!tLJAPayPersonDBSet.insert())
      {
    		// @@错误处理
		this.mErrors.copyAllErrors(tLJAPayPersonDBSet.mErrors);
    		CError tError = new CError();
	        tError.moduleName = "NormPayGrpChooseOperBLS";
	        tError.functionName = "saveData";
	        tError.errorMessage = "实收个人交费表数据保存失败!";
	        this.mErrors .addOneError(tError) ;
        conn.rollback() ;
        conn.close();
 	        return false;
      }
logger.debug("实收个人交费表添加");

//集体保单表更新
      logger.debug("Start 集体保单表...");
      LCGrpPolDB tLCGrpPolDB=new LCGrpPolDB(conn);
      tLCGrpPolDB.setSchema((LCGrpPolBL)mInputData.getObjectByObjectName("LCGrpPolBL",0));
      if (!tLCGrpPolDB.update())
      {
    		// @@错误处理
		this.mErrors.copyAllErrors(tLCGrpPolDB.mErrors);
    		CError tError = new CError();
		tError.moduleName = "NormPayGrpChooseOperBLS";
		tError.functionName = "updateData";
		tError.errorMessage = "集体保单表数据保存失败!";
		this.mErrors .addOneError(tError) ;
        conn.rollback() ;
        conn.close();
 		return false;
      }
logger.debug("集体保单表更新");

//实收集体表添加
logger.debug("Start 实收集体表...");
      LJAPayGrpDB tLJAPayGrpDB=new LJAPayGrpDB(conn);
      tLJAPayGrpDB.setSchema((LJAPayGrpBL)mInputData.getObjectByObjectName("LJAPayGrpBL",0));
      if (!tLJAPayGrpDB.insert())
      {
    		// @@错误处理
		this.mErrors.copyAllErrors(tLJAPayGrpDB.mErrors);
    		CError tError = new CError();
		tError.moduleName = "NormPayGrpChooseOperBLS";
		tError.functionName = "saveData";
		tError.errorMessage = "实收集体表数据保存失败!";
		this.mErrors .addOneError(tError) ;
        conn.rollback() ;
        conn.close();
 		return false;
      }
logger.debug("实收集体表添加");

//个人保单表更新
      logger.debug("Start 个人保单表...");
      LCPolDBSet tLCPolDBSet=new LCPolDBSet(conn);
      tLCPolDBSet.set((LCPolSet)mInputData.getObjectByObjectName("LCPolSet",0));
      if (!tLCPolDBSet.update())
      {
    		// @@错误处理
		this.mErrors.copyAllErrors(tLCPolDBSet.mErrors);
    		CError tError = new CError();
		tError.moduleName = "NormPayCollOperBLS";
		tError.functionName = "updateData";
		tError.errorMessage = "个人保单表数据保存失败!";
		this.mErrors .addOneError(tError) ;
        conn.rollback() ;
        conn.close();
 		return false;
      }
logger.debug("个人保单表更新");

//LCDuty 保险责任表更新
logger.debug("Start 保险责任表...");
      LCDutyDBSet tLCDutyDBSet=new LCDutyDBSet(conn);
      tLCDutyDBSet.set((LCDutySet)mInputData.getObjectByObjectName("LCDutySet",0));
      if (!tLCDutyDBSet.update())
      {
    		// @@错误处理
		this.mErrors.copyAllErrors(tLCDutyDBSet.mErrors);
    		CError tError = new CError();
	        tError.moduleName = "IndiNormalPayVerifyBLS";
	        tError.functionName = "saveData";
	        tError.errorMessage = "保险责任表数据保存失败!";
	        this.mErrors .addOneError(tError) ;
        conn.rollback() ;
        conn.close();
 	        return false;
      }
logger.debug("保险责任表添加");

//保费项表更新
logger.debug("Start 保费项表...");
      LCPremDBSet tLCPremDBSet=new LCPremDBSet(conn);
      tLCPremDBSet.set((LCPremSet)mInputData.getObjectByObjectName("LCPremSet",0));
      if (!tLCPremDBSet.update())
      {
    		// @@错误处理
		this.mErrors.copyAllErrors(tLCPremDBSet.mErrors);
    		CError tError = new CError();
	        tError.moduleName = "NormPayGrpChooseOperBLS";
	        tError.functionName = "saveData";
	        tError.errorMessage = "保费项表数据更新失败!";
	        this.mErrors .addOneError(tError) ;
        conn.rollback() ;
        conn.close();
 	        return false;
      }
logger.debug("保费项表更新");

//删除应收个人交费表
      logger.debug("Start 删除个人交费表...");
      LJSPayPersonDBSet tLJSPayPersonDBSet=new LJSPayPersonDBSet(conn);
      tLJSPayPersonDBSet.set((LJSPayPersonSet)mInputData.getObjectByObjectName("LJSPayPersonSet",0));
      if (!tLJSPayPersonDBSet.delete())
      {
    		// @@错误处理
		this.mErrors.copyAllErrors(tLJSPayPersonDBSet.mErrors);
    		CError tError = new CError();
	        tError.moduleName = "NormPayGrpChooseOperBLS";
	        tError.functionName = "deleteData";
	        tError.errorMessage = "应收个人交费表数据删除失败!";
	        this.mErrors .addOneError(tError) ;
        conn.rollback() ;
        conn.close();
 	        return false;
      }

logger.debug("删除应收个人交费表");


//删除保险帐户表
      logger.debug("Start 保险帐户表...");
      LCInsureAccDBSet tLCInsureAccDBSet=new LCInsureAccDBSet(conn);
      tLCInsureAccDBSet.set((LCInsureAccSet)mInputData.getObjectByObjectName("LCInsureAccSet",0));
      if (!tLCInsureAccDBSet.delete())
      {
                    // @@错误处理
                this.mErrors.copyAllErrors(tLCInsureAccDBSet.mErrors);
                    CError tError = new CError();
                tError.moduleName = "IndiFinUrgeVerifyBLS";
                tError.functionName = "deleteData";
                tError.errorMessage = "保险帐户表数据删除失败!";
                this.mErrors .addOneError(tError) ;
        conn.rollback() ;
        conn.close();
                 return false;
      }
logger.debug("删除保险帐户表");

//插入保险帐户表
      logger.debug("Start 保险帐户表...");
      LCInsureAccDBSet pLCInsureAccDBSet=new LCInsureAccDBSet(conn);
      pLCInsureAccDBSet.set((LCInsureAccSet)mInputData.getObjectByObjectName("LCInsureAccSet",0));
      if (!pLCInsureAccDBSet.insert())
      {
                    // @@错误处理
                this.mErrors.copyAllErrors(pLCInsureAccDBSet.mErrors);
                CError tError = new CError();
                tError.moduleName = "IndiFinUrgeVerifyBLS";
                tError.functionName = "deleteData";
                tError.errorMessage = "插入保险帐户表失败!";
                this.mErrors .addOneError(tError) ;
        conn.rollback() ;
        conn.close();
                 return false;
      }
logger.debug("插入保险帐户表");

//插入保险帐户表记价履历表
      logger.debug("Start 保险帐户表记价履历表...");
      LCInsureAccTraceDBSet pLCInsureAccTraceDBSet=new LCInsureAccTraceDBSet(conn);
      pLCInsureAccTraceDBSet.set((LCInsureAccTraceSet)mInputData.getObjectByObjectName("LCInsureAccTraceSet",0));
      if (!pLCInsureAccTraceDBSet.insert())
      {
                    // @@错误处理
                this.mErrors.copyAllErrors(pLCInsureAccTraceDBSet.mErrors);
                CError tError = new CError();
                tError.moduleName = "IndiFinUrgeVerifyBLS";
                tError.functionName = "deleteData";
                tError.errorMessage = "插入保险帐户表记价履历表失败!";
                this.mErrors .addOneError(tError) ;
        conn.rollback() ;
        conn.close();
                 return false;
      }
logger.debug("插入保险帐户表记价履历表");

      conn.commit() ;
      conn.close();
    }
    catch (Exception ex)
    {
      // @@错误处理
      CError tError =new CError();
      tError.moduleName="NormPayGrpChooseOperBLS";
      tError.functionName="submitData";
      tError.errorMessage=ex.toString();
      this.mErrors .addOneError(tError);
      try{conn.rollback() ;} catch(Exception e){}
      tReturn=false;
    }
    return tReturn;
  }
}
