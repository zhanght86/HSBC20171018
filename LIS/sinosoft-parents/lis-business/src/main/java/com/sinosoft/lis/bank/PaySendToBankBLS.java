package com.sinosoft.lis.bank;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
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
 * <p>Description: 业务数据转换到银行系统</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author Minim
 * @version 1.0
 */

public class PaySendToBankBLS {
private static Logger logger = Logger.getLogger(PaySendToBankBLS.class);
  //错误处理类，每个需要错误处理的类中都放置该类
  public  CErrors mErrors=new CErrors();
  /** 传入数据的容器 */
  private VData mInputData = new VData();
  /** 数据操作字符串 */
  private String mOperate;

  //业务数据
  private LJAGetSet inLJAGetSet = new LJAGetSet();
  private LYSendToBankSet inLYSendToBankSet = new LYSendToBankSet();
  private LYBankLogSet inLYBankLogSet = new LYBankLogSet();

  public PaySendToBankBLS() {
  }

  /**
   * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
   * @param cInputData 传入的数据,VData对象
   * @param cOperate 数据操作字符串，主要包括"GETMONEY"和"PAYMONEY"
   * @return 布尔值（true--提交成功, false--提交失败）
   */
  public boolean submitData(VData cInputData, String cOperate) {
    boolean tReturn = false;

    //将操作数据拷贝到本类中
    this.mInputData = (VData)cInputData.clone();
    this.mOperate =cOperate;

    //得到外部传入的数据,将数据备份到本类中
    if (!getInputData()) return false;
    //logger.debug("---End getInputData---");

    //信息保存
    if(this.mOperate.equals("PAYMONEY")) {
      tReturn = save(cInputData);
    }

    if (tReturn)
      logger.debug("Save sucessful");
    else
      logger.debug("Save failed");

    return tReturn;
  }

  /**
   * 将外部传入的数据分解到本类的属性中
   * @param: 无
   * @return: boolean
   */
  private boolean getInputData()	{
    try {
      inLJAGetSet = (LJAGetSet)mInputData.getObjectByObjectName("LJAGetSet", 0);
      inLYSendToBankSet = (LYSendToBankSet)mInputData.getObjectByObjectName("LYSendToBankSet", 0);
      inLYBankLogSet = (LYBankLogSet)mInputData.getObjectByObjectName("LYBankLogSet", 0);
    }
    catch (Exception e) {
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "PaySendToBankBLS";
      tError.functionName = "getInputData";
      tError.errorMessage = "接收数据失败!!";
      this.mErrors .addOneError(tError) ;
      return false;
    }

    return true;
  }

  //保存操作
  private boolean save(VData mInputData) {
    boolean tReturn =true;
    logger.debug("Start Save...");

    //建立数据库连接
    Connection conn=DBConnPool.getConnection();
    if (conn == null) {
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "PaySendToBankBLS";
      tError.functionName = "saveData";
      tError.errorMessage = "数据库连接失败!";
      this.mErrors .addOneError(tError) ;
      return false;
    }

    try {
      //开始事务，锁表
      conn.setAutoCommit(false);

      //生成送银行数据，插入送银行盘记录表（LYSendToBank）
      //logger.debug("inLYSendToBankSet:" + inLYSendToBankSet.size() + inLYSendToBankSet.encode());
      LYSendToBankDBSet tLYSendToBankDBSet = new LYSendToBankDBSet(conn);
      tLYSendToBankDBSet.set(inLYSendToBankSet);
      if (!tLYSendToBankDBSet.insert()) {
        try{ conn.rollback() ;} catch(Exception e){}
        conn.close();
        CError.buildErr(this, "LYSendToBank Insert Failed");
        return false;
      }

      //修改总实付表的银行在途标志
      //logger.debug("inLJAGetSet:" + inLJAGetSet.size());
      LJAGetDBSet tLJAGetDBSet = new LJAGetDBSet(conn);
      tLJAGetDBSet.set(inLJAGetSet);
      if (!tLJAGetDBSet.update()) {
        try{ conn.rollback() ;} catch(Exception e){}
        conn.close();
        CError.buildErr(this, "LJAGet Update Failed");
        return false;
      }

      //记录银行业务日志，插入新日志数据
      LYBankLogDBSet tLYBankLogDBSet = new LYBankLogDBSet(conn);
      tLYBankLogDBSet.set(inLYBankLogSet);
      if (!tLYBankLogDBSet.insert()) {
        try{ conn.rollback() ;} catch(Exception e){}
        conn.close();
        CError.buildErr(this, "LYBankLog Insert Failed");
        return false;
      }

      conn.commit();
      conn.close();
      logger.debug("End Committed");
    }
    catch (Exception ex) {
      // @@错误处理
      try{ conn.rollback() ;} catch(Exception e){}
      try {
			conn.close();
		} catch (SQLException e) {
		}
      CError.buildErr(this, ex.getMessage());
      tReturn=false;
    }
    return tReturn;
  }

  public static void main(String[] args) {
    PaySendToBankBLS paySendToBankBLS1 = new PaySendToBankBLS();
  }
}
