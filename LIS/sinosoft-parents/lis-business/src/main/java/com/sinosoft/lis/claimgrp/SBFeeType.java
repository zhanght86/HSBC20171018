package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;

public class SBFeeType {
private static Logger logger = Logger.getLogger(SBFeeType.class);
  //测试用main方法
  /*  public static void main(String [] args)
    {

    }
   */

  Statement stmt = null;
  ResultSet rs = null;
  Connection conn = null;
  String state = "0";
  //构造函数
  //数据库连接,并取标题


  //向LDcode1中插入或更新一条记录
  public String UpdateLDcode1(String Sql) {
    try {
      conn = DBConnPool.getConnection();
      stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                                  ResultSet.CONCUR_UPDATABLE);

    } catch (Exception e) {
      Stringtools.log("", e.getMessage(), "");

    }

    try {
      logger.debug(Sql);
      stmt.executeUpdate(Sql);
      state = "ok";
      conn.commit();
      stmt.close();
      conn.close();

    } catch (Exception e) {
      Stringtools.log("", "插入出错" + e.getMessage(), "");
      logger.debug("插入出错：" + e.getMessage());
      state = "fail";
    }
    return state;
  }



  public String InsertSBFeeType(String Code,String CodeName){
    try{
      conn = DBConnPool.getConnection();
      stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                                  ResultSet.CONCUR_UPDATABLE);

    }catch (Exception e) {
      Stringtools.log("", "setTaskStartTime:" + e.getMessage(), "");
    }
    ExeSQL my = new ExeSQL();
     String ColNum = my.getOneValue("select count(*) from ldcode1 where codetype='colname'"
                                    + " and code='" + Code + "'");
     int i = Stringtools.stringtoint(ColNum) + 1;
     String sql = "insert into ldcode1(codetype,code,code1,codename) values('colname','"
                  + Code + "','col" + i + "','"+ CodeName + "')";
     state = UpdateLDcode1(sql);
    return state;
  }

  public String DelSBFeeType(String code, String code1) {
    try {
      conn = DBConnPool.getConnection();
      stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                                  ResultSet.CONCUR_UPDATABLE);

    } catch (Exception e) {
      Stringtools.log("", "setTaskStartTime:" + e.getMessage(), "");
    }
    String sql = "delete from ldcode1 where codetype='colname' and code='" +
                 code + "' and code1='" + code1 + "'";
    try {
      logger.debug(sql);
      stmt.executeUpdate(sql);
      state = "ok";
      conn.commit();
      stmt.close();
      conn.close();

    } catch (Exception e) {
      state = e.getMessage();
      try {
        if (rs != null) {
          rs.close();
        }
        if (stmt != null) {
          //由于描述的问题，导致执行的sql错误百出，因此pstmt的关闭需要特殊处理
          try {
            stmt.close();
          } catch (SQLException ex) {
            ex.printStackTrace();
          } finally {
            try {
              logger.debug("Sql's bug is very big: " + sql);
              stmt.close();
            } catch (SQLException ex) {}
          }
        }

      } catch (SQLException ex) {
        //可能出现连接没有关闭
      }

    }
    return state;

  }

/**
  private int getResultCount(String sql) {
    int iCount = 0;
    //此方法对不同数据库通用
    sql = "select count(1) from (" + sql + ") rsc";
    logger.debug("getResultCount : " + sql);

    try {
      conn = DBConnPool.getConnection();
      stmt = conn.prepareStatement(StrTool.GBKToUnicode(sql),
                                   ResultSet.TYPE_FORWARD_ONLY
                                   , ResultSet.CONCUR_READ_ONLY);
      rs = stmt.executeQuery(sql);
//            rs.next();
      //这样可以保证，没有查询到数据的时候，也返回正常
      while (rs.next()) {
        iCount = rs.getInt(1);
        break;
      }
      rs.close();
      stmt.close();
    } catch (SQLException e) {
      e.printStackTrace();
      // @@错误处理
      CError.buildErr(this, e.toString());
      iCount = 0;
      try {
        if (rs != null) {
          rs.close();
        }
        if (stmt != null) {
          //由于描述的问题，导致执行的sql错误百出，因此pstmt的关闭需要特殊处理
          try {
            stmt.close();
          } catch (SQLException ex) {
            ex.printStackTrace();
          } finally {
            try {
              logger.debug("Sql's bug is very big: " + sql);
              stmt.close();
            } catch (SQLException ex) {}
          }
        }

      } catch (SQLException ex) {
        //可能出现连接没有关闭
      }
    }
    return iCount;
  }

*/
}
