package com.sinosoft.lis.finfee;
import org.apache.log4j.Logger;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.utility.COracleBlob;
import com.sinosoft.utility.VData;

import org.jdom.*;
import org.jdom.output.*;
import java.sql.Connection;
import com.sinosoft.utility.*;
import java.io.*;

/**
 * <p>Title: </p>
 * <p>Description: 财务外包数据公共提交功能 </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: SinoSoft</p>
 * @author ln
 * @version 1.0
 */

public class FineBPODealInputDataBLS
{
private static Logger logger = Logger.getLogger(FineBPODealInputDataBLS.class);

  public CErrors mErrors = new CErrors();
  //统一更新日期，时间
  private String theCurrentDate = PubFun.getCurrentDate();
  private String theCurrentTime = PubFun.getCurrentTime();
  /** 往后面传输的数据库操作 */
  public FineBPODealInputDataBLS()
  {
  }

  /**
   *
   * @param tResult  数据
   * @param tOpetate   操作类型
   * @return
   */
  public boolean submitData(VData tResult,String tOpetate)
  {
    BPOMissionStateSchema tBPOMissionStateSchema = (BPOMissionStateSchema)tResult.getObjectByObjectName("BPOMissionStateSchema",0);
    BPOMissionDetailStateSchema tBPOMissionDetailStateSchema = (BPOMissionDetailStateSchema)tResult.getObjectByObjectName("BPOMissionDetailStateSchema",0);
    Connection conn = DBConnPool.getConnection();
    try
    {
      if (conn == null)
      {
        // @@错误处理
        CError.buildErr(this, "数据库连接失败");
        return false;
      }

      conn.setAutoCommit(false);

      logger.debug("Start BPOMissionState...");
      BPOMissionStateDB tBPOMissionStateDB = new BPOMissionStateDB( conn );
      tBPOMissionStateDB.setSchema(tBPOMissionStateSchema);

      //先删除，后插入
      if(tBPOMissionStateDB.getInfo())
      {
        if (tBPOMissionStateDB.delete() == false)
        {
          // @@错误处理
          CError.buildErr(this, "任务处理状态主表删除失败!"+ tBPOMissionStateSchema.getBPOID() +" ***  "+ tBPOMissionStateSchema.getBussNo());
          conn.rollback() ;
          conn.close();
          return false;
        }
      }
      tBPOMissionStateDB.setSchema(tBPOMissionStateSchema);
      if (tBPOMissionStateDB.insert() == false)
      {
        // @@错误处理
        CError.buildErr(this, "任务处理状态主表插入失败!"+ tBPOMissionStateSchema.getBPOID() +" ***  "+ tBPOMissionStateSchema.getBussNo());
        conn.rollback() ;
        conn.close();
        return false;
      }

      logger.debug("Start BPOMissionDetailState...");
      BPOMissionDetailStateDB tBPOMissionDetailStateDB = new BPOMissionDetailStateDB( conn );
      tBPOMissionDetailStateDB.setSchema(tBPOMissionDetailStateSchema);

      if (tBPOMissionDetailStateDB.insert() == false)
      {
        // @@错误处理
        CError.buildErr(this, "任务处理状态子表插入失败!"+ tBPOMissionDetailStateSchema.getBPOID() +" ***  "+ tBPOMissionDetailStateSchema.getBussNo());
        conn.rollback() ;
        conn.close();
        return false;
      }

      //如果已经成功导入系统，更新扫描状态
      if(tBPOMissionStateSchema != null && "1".equals(tBPOMissionStateSchema.getState()))
      {
        ES_DOC_MAINSchema tES_DOC_MAINSchema = new ES_DOC_MAINSchema();
        ES_DOC_MAINSet tES_DOC_MAINSet = new ES_DOC_MAINSet();
        ES_DOC_MAINDB tES_DOC_MAINDB = new ES_DOC_MAINDB(conn);
        tES_DOC_MAINDB.setDocCode(tBPOMissionStateSchema.getBussNo());
        tES_DOC_MAINSet = tES_DOC_MAINDB.query();
        if(tES_DOC_MAINSet == null || tES_DOC_MAINSet.size()==0)
        {
          conn.rollback();
          conn.close();
          CError.buildErr(this, "扫描信息查询失败！");
          return false;
        }
        tES_DOC_MAINSchema = tES_DOC_MAINSet.get(1).getSchema();
        tES_DOC_MAINSchema.setInputState("1");
        tES_DOC_MAINSchema.setOperator(tBPOMissionStateSchema.getOperator());
        tES_DOC_MAINSchema.setInputStartDate(PubFun.getCurrentDate());
        tES_DOC_MAINSchema.setInputStartTime(PubFun.getCurrentTime());
        tES_DOC_MAINDB = new ES_DOC_MAINDB(conn);

        tES_DOC_MAINDB.setSchema(tES_DOC_MAINSchema);
        if(!tES_DOC_MAINDB.update())
        {
          conn.rollback();
          conn.close();
          CError.buildErr(this, "扫描件录入状态更新失败！");
          return false;
        }
      }

      conn.commit();
      conn.close();

      }catch(Exception ex)
      {
        try
        {
          conn.rollback();
          conn.close();
          } catch(Exception ex1){}
          ex.printStackTrace();
          CError.buildErr(this, "修改数据失败！" + ex.toString());
          return false;

      }
      return true;
  }

  public boolean submitData(VData tResult,Element tOnePolData)
  {
    String tDealType = "";   //处理类型
    BPOMissionStateSchema tBPOMissionStateSchema = (BPOMissionStateSchema)tResult.getObjectByObjectName("BPOMissionStateSchema",0);
    BPOMissionDetailErrorSet tBPOMissionDetailErrorSet = (BPOMissionDetailErrorSet)tResult.getObjectByObjectName("BPOMissionDetailErrorSet",0);
    BPOMissionDetailStateSchema tBPOMissionDetailStateSchema = (BPOMissionDetailStateSchema)tResult.getObjectByObjectName("BPOMissionDetailStateSchema",0);
    Document doc = new Document(tOnePolData);
    COracleBlob tCOracleBlob = new COracleBlob();
    CMySQLBlob tCMySQLBlob = new CMySQLBlob();
    
    Connection conn = DBConnPool.getConnection();
    try
    {
      if (conn == null)
      {
        // @@错误处理
        CError.buildErr(this, "数据库连接失败");
        return false;

      }

      conn.setAutoCommit(false);

      //00－清洁件，01－抽检件，02－外包方返回可处理异常件，03－外包方无法处理的异常件（如整个扫描件无法识别），
      //04－清洁件导入系统出错所致的异常件，05－重复导入
      tDealType = tBPOMissionStateSchema.getDealType();
      logger.debug("tDealType: "+tDealType);
      if(tDealType == null || "".equals(tDealType) ||(!"00".equals(tDealType)
          && !"01".equals(tDealType)
          && !"02".equals(tDealType)
          && !"03".equals(tDealType)
          && !"04".equals(tDealType)
          && !"05".equals(tDealType)
          ) )
      {
        // @@错误处理
        CError.buildErr(this, "处理类型错误!"+ tBPOMissionStateSchema.getBPOID() +" ***  "+ tBPOMissionStateSchema.getBussNo());
        conn.rollback() ;
        conn.close();
        return false;
      }

      logger.debug("Start BPOMissionState...");
      BPOMissionStateDB tBPOMissionStateDB = new BPOMissionStateDB( conn );
      tBPOMissionStateDB.setSchema(tBPOMissionStateSchema);

      //先删除，后插入
      if(tBPOMissionStateDB.getInfo())
      {
        if (tBPOMissionStateDB.delete() == false)
        {
          // @@错误处理
          CError.buildErr(this, "任务处理状态主表删除失败!"+ tBPOMissionStateSchema.getBPOID() +" ***  "+ tBPOMissionStateSchema.getBussNo());
          conn.rollback() ;
          conn.close();
          return false;
        }
      }
      tBPOMissionStateDB.setSchema(tBPOMissionStateSchema);
      if (tBPOMissionStateDB.insert() == false)
      {
        // @@错误处理
        CError.buildErr(this, "任务处理状态主表插入失败!"+ tBPOMissionStateSchema.getBPOID() +" ***  "+ tBPOMissionStateSchema.getBussNo());
        conn.rollback() ;
        conn.close();
        return false;
      }

      logger.debug("Start BPOMissionDetailState...");
      BPOMissionDetailStateDB tBPOMissionDetailStateDB = new BPOMissionDetailStateDB( conn );
      tBPOMissionDetailStateDB.setSchema(tBPOMissionDetailStateSchema);

      if (tBPOMissionDetailStateDB.insert() == false)
      {
        // @@错误处理
        CError.buildErr(this, "任务处理状态子表插入失败!"+ tBPOMissionDetailStateSchema.getBPOID() +" ***  "+ tBPOMissionDetailStateSchema.getBussNo());
        conn.rollback() ;
        conn.close();
        return false;
      }

      //如果是异常件，插入任务处理错误信息表
      if("02".equals(tDealType) || "03".equals(tDealType) || "04".equals(tDealType))
      {
        logger.debug("Start BPOMissionDetailError...");
        BPOMissionDetailErrorDBSet tBPOMissionDetailErrorDBSet = new BPOMissionDetailErrorDBSet(conn);
        tBPOMissionDetailErrorDBSet.set(tBPOMissionDetailErrorSet);
        if(tBPOMissionDetailErrorDBSet.insert() == false)
        {
          // @@错误处理
          CError.buildErr(this, "任务处理错误信息表插入失败!"+ tBPOMissionDetailStateSchema.getBPOID() +" ***  "+ tBPOMissionDetailStateSchema.getBussNo());
          conn.rollback() ;
          conn.close();
          return false;
        }
      }

      //如果是异常件和抽检件，插入外包投保数据表
      if("01".equals(tDealType) || "02".equals(tDealType) || "03".equals(tDealType) || "04".equals(tDealType))
      {
        String szSQL = "INSERT INTO BPOPolData(BussNo, BussNoType, PolData,SerialNo, Operator, MakeDate,MakeTime) VALUES('"
                     + tBPOMissionStateSchema.getBussNo() + "', '"
                     + tBPOMissionStateSchema.getBussNoType() + "', "
                     + " empty_blob(),'"
                     + tBPOMissionStateSchema.getSerialNo() + "', '"
                     + tBPOMissionStateSchema.getOperator() + "', '"
                     + theCurrentDate + "','" + theCurrentTime + "')";
//        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
//        sqlbv.sql(szSQL);
//        sqlbv.put("BussNo", tBPOMissionStateSchema.getBussNo());
//        sqlbv.put("BussNoType", tBPOMissionStateSchema.getBussNoType());
//        sqlbv.put("SerialNo", tBPOMissionStateSchema.getSerialNo());
//        sqlbv.put("Operator", tBPOMissionStateSchema.getOperator());
//        sqlbv.put("MakeDate", theCurrentDate);
//        sqlbv.put("MakeTime", theCurrentTime);
        logger.debug("###########: "+szSQL);
        
        if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
        	if (!tCOracleBlob.InsertBlankBlobRecord(szSQL, conn))
            {
              conn.rollback();
              conn.close();
              // @@错误处理
              CError.buildErr(this, "外包投保数据表插入失败！" + szSQL);
              return false;
            }
        }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
        	if (!tCMySQLBlob.InsertBlankBlobRecord(szSQL, conn))
            {
              conn.rollback();
              conn.close();
              // @@错误处理
              CError.buildErr(this, "外包投保数据表插入失败！" + szSQL);
              return false;
            }		
        }
        szSQL = " and BussNo = '" +  tBPOMissionStateSchema.getBussNo() +
                "' and BussNoType = 'OF'";

        XMLOutputter outputter = new XMLOutputter("  ", true, "GB2312");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        outputter.output(doc, baos);
        baos.close();
        if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
        	 if (!tCOracleBlob.UpdateBlob(doc, "BPOPolData", "PolData", szSQL, conn)){
				conn.rollback();
				conn.close();
				CError.buildErr(this, "外包投保数据表修改数据失败！" + szSQL);
				return false;
				}		
        }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
        	if (!tCMySQLBlob.UpdateBlob(doc, "BPOPolData", "PolData", szSQL, conn)){
				conn.rollback();
				conn.close();
				CError.buildErr(this, "外包投保数据表修改数据失败！" + szSQL);
				return false;
				}	
        }
      }

      //如果已经成功导入系统，更新扫描状态
      if(tBPOMissionStateSchema != null && "1".equals(tBPOMissionStateSchema.getState()))
      {
          ES_DOC_MAINSchema tES_DOC_MAINSchema = new ES_DOC_MAINSchema();
          ES_DOC_MAINSet tES_DOC_MAINSet = new ES_DOC_MAINSet();
          ES_DOC_MAINDB tES_DOC_MAINDB = new ES_DOC_MAINDB(conn);
          tES_DOC_MAINDB.setDocCode(tBPOMissionStateSchema.getBussNo());
          tES_DOC_MAINSet = tES_DOC_MAINDB.query();
          if(tES_DOC_MAINSet == null || tES_DOC_MAINSet.size()==0)
          {
            conn.rollback();
            conn.close();
            CError.buildErr(this, "扫描信息查询失败！");
            return false;
          }
          tES_DOC_MAINSchema = tES_DOC_MAINSet.get(1).getSchema();
          tES_DOC_MAINSchema.setInputState("1");
          tES_DOC_MAINSchema.setOperator(tBPOMissionStateSchema.getOperator());
          tES_DOC_MAINSchema.setInputStartDate(PubFun.getCurrentDate());
          tES_DOC_MAINSchema.setInputStartTime(PubFun.getCurrentTime());
          tES_DOC_MAINDB = new ES_DOC_MAINDB(conn);

          tES_DOC_MAINDB.setSchema(tES_DOC_MAINSchema);
          if(!tES_DOC_MAINDB.update())
          {
            conn.rollback();
            conn.close();
            CError.buildErr(this, "扫描件录入状态更新失败！");
            return false;
          }
      }

      conn.commit();
      conn.close();
    }
    catch (Exception ex)
    {
      try
      {
        conn.rollback();
        conn.close();
        } catch(Exception ex1){}
        ex.printStackTrace();
        CError.buildErr(this, "修改数据失败！" + ex.toString());
        return false;

    }

    return true;
  }
  public static void main(String[] args)
  {
	  VData tResult = new VData();
	FineBPODealInputDataBLS FineBPODealInputDataBLS1 = new FineBPODealInputDataBLS();
    FineBPODealInputDataBLS1.submitData(tResult, "");
  }
}
