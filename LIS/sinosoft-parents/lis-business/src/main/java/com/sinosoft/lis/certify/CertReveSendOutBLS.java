package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.pubfun.*;
import java.lang.*;
import java.sql.*;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description:单证管理普通单证回收操作</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author kevin
 * @version 1.0
 */

public class CertReveSendOutBLS  {
private static Logger logger = Logger.getLogger(CertReveSendOutBLS.class);
  //错误处理类，每个需要错误处理的类中都放置该类
  public  CErrors mErrors = new CErrors();

  public CertReveSendOutBLS() {
  }

  public static void main(String[] args) {
    CertReveSendOutBLS certifyTakeBackBLS = new CertReveSendOutBLS();

    certifyTakeBackBLS.submitData(null, "INSERT");
  }

  //传输数据的公共方法
  public boolean submitData(VData cInputData, String cOperate)
  {
  	boolean bReturn = true;

    if( cOperate.equals("INSERT") ) {
      Connection conn = DBConnPool.getConnection();

      if( conn == null ) {
        buildError("saveData", "连接数据库失败");
        return false;
      }

      try {
        // 开始事务
        conn.setAutoCommit(false);

        for(int nIndex = 0; nIndex < cInputData.size(); nIndex ++) {
          if( !saveData( (VData)cInputData.get(nIndex), conn ) ) {
            bReturn = false;
            break;
          }
        }

        if( bReturn ) {
          conn.commit();
        } else {
          conn.rollback();
        }

        conn.close();
      } catch (Exception ex) {
        try {
          if( conn != null ) {
            conn.rollback();
            conn.close();
          }
        } catch (Exception e) {
          // do nothing
        }
        bReturn = false;
      }
    } else {
      buildError("submitData", "不支持的操作字符串");
      bReturn = false;
    }

  	if( !bReturn ) {
  		if( CertifyFunc.mErrors.needDealError() ) {
  			mErrors.copyAllErrors( CertifyFunc.mErrors );
  		} else {
  			buildError("submitData", "发生错误，但是CertifyFunc没有提供详细的信息");
  		}
  		logger.debug(mErrors.getFirstError( ));
  	}

  	return bReturn;
  }

  /**
   * Kevin, 2003-03-19
   * 保存数据。在传入的VData中。第一到第四个元素都是LZCardSchema，第一个元素是要删除的数据，
   * 其它的数据是要插入的数据。第五个元素是要插入到LZCardTrack表中的数据。
   * @param vData
   * @return
   */
  private boolean saveData(VData vData, Connection conn) {
    LZCardDB tLZCardDB = new LZCardDB(conn);
    LZCardTrackDB tLZCardTrackDB = new LZCardTrackDB(conn);

    LZCardSchema tLZCardSchema = null;
    LZCardTrackSchema tLZCardTrackSchema = null;
    
    String startno="";//本次发放回退的单证起始号
    String endno="";//本次发放回退的单证终止号
    
    //首先取得本次发放的单证起始和终止号,保存,以便下面进行校验
    tLZCardSchema = (LZCardSchema)vData.get(3);
    startno=tLZCardSchema.getStartNo();
    endno=tLZCardSchema.getEndNo();
    logger.debug("CertReveSendOutBLS:********1本次要发放回退的单证查询到存在于数据库中的记录的起始号="+startno);
	logger.debug("CertReveSendOutBLS:********1本次要发放回退的单证查询到存在于数据库中的记录的终止号="+endno);

    try 
    {
      tLZCardSchema = (LZCardSchema)vData.get(0);
      
      
      if( tLZCardSchema != null ) 
      {
    	  
    	  //防止一个进程刚执行完对本次单证的操作，本次操作的进程在BL层进行查询的时候并没有发现本次需要发放的单证发生了变化而导致的并发现象
    	  logger.debug("CertReveSendOutBLS:本次在数据库中查询到的包括待发放回退记录的单证类型="+tLZCardSchema.getCertifyCode());
    	  logger.debug("CertReveSendOutBLS:本次在数据库中查询到的包括待发放回退记录的起始号="+tLZCardSchema.getStartNo());
    	  logger.debug("CertReveSendOutBLS:本次在数据库中查询到的包括待发放回退记录的终止号="+tLZCardSchema.getEndNo());
    	  //由于在lzcard表中一张单证只能出现在一条记录里，所以在BLS层中再根据BL层中查询的到的记录再查询一遍数据库，看查询结果是否发生了变化，如果查询到记录，说明没有进程对这条记录进行操作，就进行加锁
    	  String sql="select 1 from lzcard where certifycode='"+"?certifycode?"+"'"
    	             +" and startno='"+"?startno?"+"' and endno='"+"?endno?"+"' for update nowait";
    	  logger.debug("CertReveSendOutBLS:校验是否有其他进行对本次发放回退的单证进行过操作的sql是"+sql);
    	  SQLwithBindVariables sqlbv = new SQLwithBindVariables();
    	  sqlbv.sql(sql);
    	  sqlbv.put("certifycode", tLZCardSchema.getCertifyCode());
    	  sqlbv.put("startno", tLZCardSchema.getStartNo());
    	  sqlbv.put("endno", tLZCardSchema.getEndNo());
    	  
    	  ExeSQL tExeSql = new ExeSQL(conn);
    	  String rs = tExeSql.getOneValue(sqlbv);
    	  logger.debug("CertReveSendOutBLS:校验是否有其他进行对本次发放回退的单证进行过操作的返回结果是"+rs);
    	  if (rs.equals("")||rs==null)
          {
	        	CError tError = new CError();
	          	tError.moduleName = "CertReveSendOutBLS";
	          	tError.functionName = "saveData";
	          	tError.errorMessage = "单证号："+tLZCardSchema.getCertifyCode()+"起始号为"+tLZCardSchema.getStartNo()+",终止号为"+tLZCardSchema.getEndNo()+"的记录已经被其他用户操作，本次操作终止!";
	          	this.mErrors.addOneError(tError) ;
	          	conn.rollback() ;
	          	conn.close();
	          	return false;
          }
    	  
          
          if (tExeSql.mErrors.needDealError()) 
          {
        	  mErrors.copyAllErrors(tExeSql.mErrors);
        	  throw new Exception("资源忙，请稍候！");
          }
          
          //校验本次发放的单证记录的在lzcard中的唯一性
          if(startno.equals("")||startno==null)
          {
        	  CError tError = new CError();
	          tError.moduleName = "CertReveSendOutBLS";
	          tError.functionName = "saveData";
	          tError.errorMessage = "传递的需要发放的单证起始号为空,请重新检查执行！";
	          this.mErrors.addOneError(tError) ;
	          conn.rollback() ;
	          conn.close();
	          return false;
          }
          else if(endno.equals("")||endno==null)
          {
        	  CError tError = new CError();
	          tError.moduleName = "CertReveSendOutBLS";
	          tError.functionName = "saveData";
	          tError.errorMessage = "传递的需要发放的单证起始号为空,请重新检查执行！";
	          this.mErrors.addOneError(tError) ;
	          conn.rollback() ;
	          conn.close();
	          return false;
          }
          else
          {
        	  String querySql="select count(*) from lzcard where certifycode='"+"?certifycode?"+"'"
	             +" and startno<='"+"?startno?"+"' and endno>='"+"?startno?"+"' ";
	          logger.debug("\nCertReveSendOutBLS:校验待发放的单证类型为"+startno+"的起始单证号为"+startno+"的单证记录是否只存在于LZCard中的一条记录的SQL="+querySql);
	          SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
	          sqlbv1.sql(querySql);
	          sqlbv1.put("certifycode", tLZCardSchema.getCertifyCode());
	          sqlbv1.put("startno", startno);
	          SSRS tSSRS=new SSRS();
	          tSSRS= tExeSql.execSQL(sqlbv1);
	          logger.debug("tSSRS1="+tSSRS.GetText(1,1));
	          if (!tSSRS.GetText(1,1).equals("1"))
	          {
	        	  	CError tError = new CError();
			    	tError.moduleName = "CertReveSendOutBLS";
			    	tError.functionName = "saveData";
			    	tError.errorMessage = "单证类型:"+tLZCardSchema.getCertifyCode()+",起始单证号为"+startno+"的单证存在重复记录,请查明原因再进行发放";
			    	this.mErrors.addOneError(tError) ;
			    	conn.rollback() ;
			    	conn.close();
			    	return false;
	          }
	  
	          querySql="select count(*) from lzcard where certifycode='"+"?certifycode?"+"'"
	          			+" and startno<='"+"?endno?"+"' and endno>='"+"?endno?"+"' ";
	          logger.debug("\nCertReveSendOutBLS:校验待发放的单证类型为"+tLZCardSchema.getCertifyCode()+"的终止单证号为"+endno+"的单证记录是否只存在于LZCard中的一条记录的SQL="+querySql);
	          SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
	          sqlbv2.sql(querySql);
	          sqlbv2.put("certifycode", tLZCardSchema.getCertifyCode());
	          sqlbv2.put("endno", endno);
	          tSSRS= tExeSql.execSQL(sqlbv2);
	          logger.debug("ps2="+tSSRS.GetText(1,1));
	          if (!tSSRS.GetText(1,1).equals("1"))
	          {
				  	CError tError = new CError();
				    tError.moduleName = "CertReveSendOutBLS";
				    tError.functionName = "saveData";
				    tError.errorMessage = "单证类型:"+tLZCardSchema.getCertifyCode()+",终止单证号为"+endno+"的单证存在重复记录,请查明原因再进行发放";
				    this.mErrors.addOneError(tError) ;
				    conn.rollback() ;
				    conn.close();
				    return false;
	          }
           }
  
      }

      if( tLZCardSchema != null ) 
      {
    	  logger.debug("CertReveSendOutBLS:本次要删除的单证起始号为"+tLZCardSchema.getStartNo()); 
     	  logger.debug("CertReveSendOutBLS:本次要删除的单证终止号为"+tLZCardSchema.getEndNo()); 
          tLZCardDB.setSchema(tLZCardSchema);
          if( !tLZCardDB.delete() ) 
          {
            mErrors.copyAllErrors(tLZCardDB.mErrors);
            throw new Exception("删除旧的LZCard数据时出错");
          }
      }

      tLZCardSchema = (LZCardSchema)vData.get(1);

      if( tLZCardSchema != null )
      {
    	logger.debug("CertReveSendOutBLS:插入拆分后的第一部分LZCard的起始号和终止号: " + tLZCardSchema.getStartNo() + " - " + tLZCardSchema.getEndNo());
        tLZCardDB.setSchema(tLZCardSchema);
        if( !tLZCardDB.insert() ) {
          mErrors.copyAllErrors(tLZCardDB.mErrors);
          throw new Exception("插入拆分后的第一部分LZCard出错");
        }
      }

      tLZCardSchema = (LZCardSchema)vData.get(2);

      if( tLZCardSchema != null ) 
      {
    	logger.debug("CertReveSendOutBLS:插入拆分后的第二部分LZCard的起始号和终止号: " + tLZCardSchema.getStartNo() + " - " + tLZCardSchema.getEndNo());
        tLZCardDB.setSchema(tLZCardSchema);
        if( !tLZCardDB.insert() ) {
          mErrors.copyAllErrors(tLZCardDB.mErrors);
          throw new Exception("插入拆分后的第二部分LZCard出错");
        }
      }

      tLZCardSchema = (LZCardSchema)vData.get(3);

      if( tLZCardSchema != null )
      {
    	logger.debug("CertReveSendOutBLS:插入拆分后的第三部分LZCard的起始号和终止号: " + tLZCardSchema.getStartNo() + " - " + tLZCardSchema.getEndNo());
        tLZCardDB.setSchema(tLZCardSchema);
        if( !tLZCardDB.insert() ) {
          mErrors.copyAllErrors(tLZCardDB.mErrors);
          throw new Exception("插入新的LZCard出错");
        }
      }

      tLZCardTrackSchema = (LZCardTrackSchema)vData.get(4);

      if( tLZCardTrackSchema != null ) 
      {
          logger.debug("CertReveSendOutBLS:插入单证轨迹 : " + tLZCardSchema.getStartNo() + " - " + tLZCardSchema.getEndNo());
          tLZCardTrackDB.setSchema(tLZCardTrackSchema);
          if( !tLZCardTrackDB.insert() ) {
            mErrors.copyAllErrors(tLZCardTrackDB.mErrors);
            throw new Exception("插入单证轨迹时出错");
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      return false;
    }

    return true;
  }

  private void buildError(String szFunc, String szErrMsg)
  {
    CError cError = new CError( );

    cError.moduleName = "CertReveSendOutBLS";
    cError.functionName = szFunc;
    cError.errorMessage = szErrMsg;
    this.mErrors.addOneError(cError);
  }
}
