package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.pubfun.*;

/**
 * <p>Title: Life Information System</p>
 * <p>Description: 单证管理模块中的公用类</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Kevin
 * @version 1.0
 */

public class CertifyBO {
private static Logger logger = Logger.getLogger(CertifyBO.class);

  public CertifyBO() {
  }

  public final void logError(LZCertLogSchema aLZCertLogSchema) {
    String strSeq = PubFun1.CreateMaxNo("SERIALNO", "CertifyLog");

    LZCertLogDB tLZCertLogDB = new LZCertLogDB();

    tLZCertLogDB.setSchema(aLZCertLogSchema);
    tLZCertLogDB.setLogSeq(strSeq);

    // 对数据做一些处理
    if( tLZCertLogDB.getCertifyCode() == null
        || tLZCertLogDB.getCertifyCode().equals("") ) {
      tLZCertLogDB.setCertifyCode(" ");
    }

    if( tLZCertLogDB.getSendOutCom() == null
        || tLZCertLogDB.getSendOutCom().equals("") ) {
      tLZCertLogDB.setSendOutCom(" ");
    }

    if( tLZCertLogDB.getReceiveCom() == null
        || tLZCertLogDB.getReceiveCom().equals("") ) {
      tLZCertLogDB.setReceiveCom(" ");
    }

    if( tLZCertLogDB.getStartNo() == null
        || tLZCertLogDB.getStartNo().equals("") ) {
      tLZCertLogDB.setStartNo("0");
    }

    if( tLZCertLogDB.getEndNo() == null
        || tLZCertLogDB.getEndNo().equals("") ) {
      tLZCertLogDB.setEndNo("0");
    }

    if( !tLZCertLogDB.insert() ) {
      logger.debug(tLZCertLogDB.mErrors.getFirstError());
    }
  }

  public final void logError(LZCardSchema aLZCardSchema, String strErr) {
    LZCertLogSchema tLZCertLogSchema = new LZCertLogSchema();

    tLZCertLogSchema.setCertifyCode( aLZCardSchema.getCertifyCode() );
    tLZCertLogSchema.setStartNo( aLZCardSchema.getStartNo() );
    tLZCertLogSchema.setEndNo( aLZCardSchema.getEndNo() );
    tLZCertLogSchema.setSumCount( aLZCardSchema.getSumCount() );
    tLZCertLogSchema.setSendOutCom( aLZCardSchema.getSendOutCom() );
    tLZCertLogSchema.setReceiveCom( aLZCardSchema.getReceiveCom() );
    tLZCertLogSchema.setTakeBackNo( aLZCardSchema.getTakeBackNo() );
    tLZCertLogSchema.setLogContent( strErr );

    logError( tLZCertLogSchema );
  }

  public final void clearLog() {
    ExeSQL exeSQL = new ExeSQL();
    SQLwithBindVariables sqlbv = new SQLwithBindVariables();
    sqlbv.sql("DELETE FROM LZCertLog");
    exeSQL.execUpdateSQL(sqlbv);
  }
}
