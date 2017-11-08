/*
 * <p>ClassName: SysCertSendOutBLS </p>
 * <p>Description: SysCertSendOutBLS类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: LIS
 * @CreateDate：2002-10-29
 */
package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import java.sql.*;
import com.sinosoft.lis.pubfun.*;

import java.util.*;

public class SysCertSendOutBLS {
private static Logger logger = Logger.getLogger(SysCertSendOutBLS.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public  CErrors mErrors = new CErrors();

 	/** 数据操作字符串 */
	private String mOperation;

	public SysCertSendOutBLS() {
	}

	public static void main(String[] args) {
	}

 	/**
 	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData,String cOperate)
	{
		boolean bReturn = false;

		mOperation = verifyOperate(cOperate);

		if( mOperation.equals("") ) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}

    logger.debug("Start SysCertSendOutBLS Submit...");

    try {
	    if( mOperation.equals("INSERT||MAIN") ) {
	    	bReturn = saveLZSysCertify(cInputData);
	    } else {
        buildError("submitData", "不支持的操作字符串");
      }
	  } catch (Exception ex) {
	  	ex.printStackTrace();
	  	buildError("submitData", "发生异常");
	  	return false;
	  }

    if( bReturn ) {
    	logger.debug("Success to deal data");
    } else {
    	logger.debug("Fail to deal data") ;
    }

    logger.debug("End LZSysCertSendOutBLS Submit...");
  	return bReturn;
	}

	/**
	 * 保存函数
	 */
	private boolean saveLZSysCertify(VData mInputData)
	{
  	Connection conn = null;
    logger.debug("Start saving ...");

  	try {
			// 得到数据库连接
			conn = DBConnPool.getConnection();

	  	if( conn == null ) {
	  		buildError("saveLZSysCertify", "连接数据库失败");
	  		return false;
	  	}

	    LZSysCertifyDB dbLZSysCertify = new LZSysCertifyDB(conn);
  		conn.setAutoCommit(false);

      LZSysCertifySet tLZSysCertifySet = null;

      tLZSysCertifySet = (LZSysCertifySet)mInputData.get(1);

	    // 删除旧的记录
			for(int nIndex = 0; nIndex < tLZSysCertifySet.size(); nIndex ++ ) {
	    	dbLZSysCertify.setSchema(tLZSysCertifySet.get(nIndex + 1));

				if( !dbLZSysCertify.delete() ) {
					mErrors.copyAllErrors( dbLZSysCertify.mErrors );
					buildError("saveLZSysCertify", "数据保存失败");
					conn.rollback();
					conn.close();
					return false;
				}
			} // end of for( nIndex = 0; nIndex < tLZSysCertifySet; nIndex ++ )

      tLZSysCertifySet = (LZSysCertifySet)mInputData.get(2);

      // 新增新的记录
      for(int nIndex = 0; nIndex < tLZSysCertifySet.size(); nIndex ++ ) {
        dbLZSysCertify.setSchema(tLZSysCertifySet.get(nIndex + 1));

        StringBuffer sb = new StringBuffer();
        if( CertifyFunc.handleSysCertifyDesc(dbLZSysCertify,
                                 conn,
                                 0,
                                 sb) == false ) {
          buildError("saveLZSysCertify", sb.toString());
          conn.rollback();
          conn.close();
          return false;
        }

        if( !dbLZSysCertify.insert() ) {
          mErrors.copyAllErrors( dbLZSysCertify.mErrors );
          buildError("saveLZSysCertify", "数据保存失败");
          conn.rollback();
          conn.close();
          return false;
        }
      } // end of for( nIndex = 0; nIndex < tLZSysCertifySet; nIndex ++ )

			conn.commit();
			conn.close();

		} catch (Exception ex) {
			ex.printStackTrace();

			try {
				conn.rollback();
				conn.close();
			} catch (Exception e) {
			}

			buildError("saveLZSysCertify", "发生异常");
			return false;
		}

    logger.debug("End of saving ...");
		return true;
	}

  /*
   * add by kevin, 2002-09-23
   */
  private void buildError(String szFunc, String szErrMsg)
  {
    CError cError = new CError( );

    cError.moduleName = "SysCertSendOutBLS";
    cError.functionName = szFunc;
    cError.errorMessage = szErrMsg;
    this.mErrors.addOneError(cError);
  }

  private String verifyOperate(String szOperate)
  {
  	String szReturn = "";
  	String szOperates[] = {"INSERT||MAIN", "DELETE||MAIN", "UPDATE||MAIN", "QUERY||MAIN"};

  	for(int nIndex = 0; nIndex < szOperates.length; nIndex ++) {
			if( szOperate.equals(szOperates[nIndex]) ) {
				szReturn = szOperate;
			}
  	}

  	return szReturn;
  }
}
