package com.sinosoft.lis.pubfun;
import org.apache.log4j.Logger;



import java.sql.Connection;
import java.util.Vector;

import com.sinosoft.lis.db.OFinaLogDB;
import com.sinosoft.lis.schema.OFinaLogSchema;
import com.sinosoft.lis.vschema.OFinaLogSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;





public class OtoFLockBL
{
private static Logger logger = Logger.getLogger(OtoFLockBL.class);


	/**
	 * @财务接口自动运行并发控制和记录
	 * 2008-4-17 zy
	 */
	
	
	public CErrors mErrors = new CErrors();
	private boolean mflag = false;
	private Connection con;
    private Vector mLockedNos = new Vector();  //同一OtoFLockBL对象已经锁定的号码
    private int mMatchID = 0;                  //借贷关系key值

    
    
    
    
    /**
      * 准备锁表的数据
      * @parameter tRecoredNo 需要锁定的号码
	  * @parameter tManageCom 加锁管理机构
	  * @parameter tVouchertype 记录凭证类型
	  * @parameter tTransDate 记录事务日期
     **/
	  public boolean prapLockData(Connection conn,String tRecordNo,String tManageCom,String tVoucherType,String tTransDate,GlobalInput mGlobalInput)
	  {
	    try
	    {
	      OFinaLogSchema mFinaLogSchema = new OFinaLogSchema();
	      OFinaLogSet tFinaLogSet = new OFinaLogSet();
	      OFinaLogDB tOFinaLogDB = new OFinaLogDB(conn);
	      tOFinaLogDB.setRecordNo(tRecordNo);

	      //如果不存在任何应用模块对该记录加锁，则直接插入加锁信息
	      if(!tOFinaLogDB.getInfo())
	      {
	    	  //批次号的处理
	    	  //可以改成查询FinaLog的记录
			  String mBatchNo="";
			  //增加手工提取的并发
			  String sql= "select batchno from OFinaLog where transdate='"+"?tTransDate?"+"' "
			  			+ "and vouchertype='"+"?tVoucherType?"+"' and makedate='"+"?makedate?"+"'";
			  ExeSQL tExeSQL = new ExeSQL();
			  SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			  sqlbv1.sql(sql);
			  sqlbv1.put("tTransDate", tTransDate);
			  sqlbv1.put("tVoucherType", tVoucherType);
			  sqlbv1.put("makedate", PubFun.getCurrentDate());
			  //如果事务日期为tTransDate，凭证类型为tVoucherType的凭证没有提过，则按照规则产生新的批次号
			  if(tExeSQL.execSQL(sqlbv1).MaxRow<=0 )
				  mBatchNo = PubFun1.CreateMaxNo("OTOF","20");
			  //如果已经提取过该天该类型的凭证，则批次号不再改变
			  else if(tExeSQL.execSQL(sqlbv1).MaxRow>0)
			  {
				  mBatchNo= tExeSQL.execSQL(sqlbv1).GetText(1, 1);
				  String mSql="select max(MatchID) from OFinaLog where transdate='"+"?tTransDate?"+"' and vouchertype='"+"?tVoucherType?"+"' " 
				  			 +"and batchno='"+"?batchno?"+"' and makedate='"+"?makedate?"+"'";
				  SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
				  sqlbv2.sql(mSql);
				  sqlbv2.put("tTransDate", tTransDate);
				  sqlbv2.put("tVoucherType", tVoucherType);
				  sqlbv2.put("batchno", mBatchNo);
				  sqlbv2.put("makedate", PubFun.getCurrentDate());
				  mMatchID = Integer.parseInt(tExeSQL.execSQL(sqlbv2).GetText(1, 1));
			  }
			  //除以上两种情况外，批次号置为特殊的号码
			  else
				  mBatchNo="0";
			  
			  
	    	  mFinaLogSchema.setRecordNo(tRecordNo);
	    	  mFinaLogSchema.setBatchNo(mBatchNo);
	    	  mFinaLogSchema.setMatchID(mMatchID);
	    	  mFinaLogSchema.setState("1");
	    	  mFinaLogSchema.setTransDate(tTransDate);
	    	  mFinaLogSchema.setVoucherType(tVoucherType);
	    	  mFinaLogSchema.setMakeDate(PubFun.getCurrentDate());
	    	  mFinaLogSchema.setMakeTime(PubFun.getCurrentTime());
	    	  mFinaLogSchema.setModifyDate(PubFun.getCurrentDate());
	    	  mFinaLogSchema.setModifyTime(PubFun.getCurrentTime());
	    	  mFinaLogSchema.setOperator(mGlobalInput.Operator);
	    	  mFinaLogSchema.setManageCom(mGlobalInput.ComCode);
	    	  mFinaLogSchema.setRemark("对自动提取管理机构为"+tManageCom+"财务凭证进行加锁");
	    	  tOFinaLogDB.setSchema(mFinaLogSchema);
	        if(!tOFinaLogDB.insert())
	        {
	          CError.buildErr(this, "OFinaLog插入失败");
	          conn.rollback() ;
	          conn.close();
	          return false;
	        }
	      }
	      else
	      {
	        String tLockSQL ="select * from OFinaLog where RecordNo = '"+"?tRecordNo?"+"' for update nowait";
	        SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
			  sqlbv3.sql(tLockSQL);
			  sqlbv3.put("tRecordNo", tRecordNo);
	        logger.debug("LockSQL: "+tLockSQL);
	        OFinaLogDB  mOFinaLogDB = new OFinaLogDB(conn);
	        tFinaLogSet = mOFinaLogDB.executeQuery(sqlbv3);
	        if(tFinaLogSet == null || tFinaLogSet.size()==0)
	        {

	          CError.buildErr(this, "操作号："+tRecordNo+"对表FinaLog中记录加锁失败!");
	          conn.rollback() ;
	          conn.close();
	          return false;
	        }
	        mFinaLogSchema = tFinaLogSet.get(1);
	        if(!mFinaLogSchema.getState().equals("0"))
	        {
	          CError.buildErr(this, "操作号："+tRecordNo+"已经被加锁！!");
	          conn.rollback() ;
	          conn.close();
	          return false;
	        }
	        mFinaLogSchema.setState("1");
	    	  mFinaLogSchema.setModifyDate(PubFun.getCurrentDate());
	    	  mFinaLogSchema.setModifyTime(PubFun.getCurrentTime());
	    	  mFinaLogSchema.setOperator(mGlobalInput.Operator);
	    	  mFinaLogSchema.setManageCom(mGlobalInput.ComCode);
	    	  mFinaLogSchema.setRemark("对自动提取管理机构为"+tManageCom+"财务凭证进行加锁");
	    	  tOFinaLogDB.setSchema(mFinaLogSchema);
	        if(!tOFinaLogDB.update())
	        {
	        	CError.buildErr(this, "表FinaLog更新失败!");

	          conn.rollback() ;
	          conn.close();
	          return false;
	        }
	      }
	    }catch(Exception ex)
	    {
	    	CError.buildErr(this, "准备加锁数据失败！");
	      try{ conn.rollback();conn.close(); } catch( Exception e ){}
	      return false;
	    }
	    return true;
	  }

	    /**
	      * 准备解锁的数据
	      * @parameter tRecoredNo 需要解锁的号码
	     **/
	  public boolean prapUnLockData(Connection conn,String tRecordNo)
	  {
	    try
	    {
	    	OFinaLogSchema tFinaLogSchema = new OFinaLogSchema();
	    	OFinaLogSet tFinaLogSet = new OFinaLogSet();
	    	OFinaLogDB tFinaLogDB = new OFinaLogDB();
	    	tFinaLogDB.setRecordNo(tRecordNo);
	      //如果原来没有加锁信息，直接返回
	      if(!tFinaLogDB.getInfo())
	      {
	    	  CError.buildErr(this, "获取"+tRecordNo+"的记录失败");
	        return true;
	      }
	      else
	      {
	        String tLockSQL ="select * from OFinaLog where RecordNo = '"+"?tRecordNo1?"+"' for update nowait";
	        SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
			  sqlbv4.sql(tLockSQL);
			  sqlbv4.put("tRecordNo1", tRecordNo);
	        tFinaLogDB = new OFinaLogDB(conn);
	        tFinaLogSet = tFinaLogDB.executeQuery(sqlbv4);
	        if(tFinaLogSet == null || tFinaLogSet.size()==0)
	        {
	        	CError.buildErr(tFinaLogDB.mErrors.getFirstError(), "记录号为"+tRecordNo+"解锁失败");
	          conn.rollback() ;
	          conn.close();
	          return false;
	        }
	        tFinaLogSchema = tFinaLogSet.get(1);
	        if(tFinaLogSchema.getState().equals("1"))
	        {
	        	tFinaLogSchema.setState("0");
	        	tFinaLogSchema.setModifyDate(PubFun.getCurrentDate());
	        	tFinaLogSchema.setModifyTime(PubFun.getCurrentTime());
	        	tFinaLogDB.setRemark("对该机构自动提取财务凭证进行解锁");
	        	tFinaLogDB.setSchema(tFinaLogSchema);
	          if(!tFinaLogDB.update())
	          {
	        	  CError.buildErr(tFinaLogDB.mErrors.getFirstError(), "记录号为"+tRecordNo+"解锁失败");;
	            conn.rollback() ;
	            conn.close();
	            return false;
	          }
	        }
	      }
	    }catch(Exception ex)
	    {
	      CError.buildErr(this, "准备解锁数据失败！");
	      try{ conn.rollback();conn.close(); } catch( Exception e ){}
	      return false;
	    }
	    return true;
	  }
	  
	  
	  
	  /**
	  * 对号码为tRecoredNo的财务凭证提取的锁定，在解锁之前，除锁定业务外，其他业务不能对该号码做操作
	  * @parameter tRecoredNo 需要锁定的号码
	  * @parameter tManageCom 加锁管理机构
	  * @parameter tVouchertype 记录凭证类型
	  * @parameter tTransDate 记录事务日期
	  * @return 如果加锁成功，返回true;加锁失败，则返回false
	  */
	  public boolean lock(String tRecoredNo,String tManageCom,String tVouchertype,String tTransDate,GlobalInput mGlobalInput)
	  {
	    try
	    {
	      if(mLockedNos.contains(tRecoredNo))
	      {
	        logger.debug("本批次已经对号码成功锁定:"+tRecoredNo);
	        return true;
	      }

	      if (mflag == false)
	      {
	        con = DBConnPool.getConnection();
	      }
	      if( con == null )
	      {
	    	  CError.buildErr(this, "数据库连接失败");

	        return false;
	      }

	      con.setAutoCommit(false);
	      if(!prapLockData(con,tRecoredNo,tManageCom,tVouchertype,tTransDate,mGlobalInput))
	        return false;
	      if (mflag == false)
	      {
	        con.commit();
	        con.close();
	        mLockedNos.add(tRecoredNo);   //将锁定号码加入到集合中
	      }

	     }catch(Exception ex)
	     {
	       CError.buildErr(this, "加锁失败");
	       try{
	         //如果发生异常以后，关闭连接
	         if (mflag == false && !con.isClosed() )
	         {
	           con.rollback();
	           con.close();
	         }
	        } catch( Exception e ){}
	       return false;
	     }
	    return true;
	  }





	  /**
	  * 对号码为tRecoredNo的财务凭证提取的解锁，在解锁之后，其他业务可以继续对该号码做操作
	  * @parameter tRecoredNo 需要解锁的号码
	  * @return 如果解锁成功，返回true;解锁失败，则返回false
	  */
	  public boolean unLock(String tRecordNo)
	  {
	    try
	    {
	      if (mflag == false)
	      {
	        con = DBConnPool.getConnection();
	      }
	      if( con == null )
	      {
	        // @@错误处理
	        CError.buildErr(this, "连接数据库失败");
	        return false;
	      }
	      
	      con.setAutoCommit(false);
	      //解锁数据准备
	      if(!prapUnLockData(con,tRecordNo))
	         return false;
	      if (mflag == false)
	      {
	        con.commit();
	        con.close();
	        //当解锁成功,删除集合中的号码
	        if(mLockedNos.contains(tRecordNo))
	        {
	          mLockedNos.remove(tRecordNo);
	        }
	      }

	      }catch(Exception ex)
	      {
	        CError.buildErr(this, "解锁失败！");
	        try{
	          //如果发生异常以后，关闭连接
	          if (mflag == false && !con.isClosed() )
	          {
	            con.rollback();
	            con.close();
	          }
	          } catch( Exception e ){}
	          return false;
	      }
	    return true;
	  }
	 
	    
	    /**
	      * 为了减少不必要批次号的生成，则判断是否存在业务数据	  
		  * @parameter tManageCom 加锁管理机构
		  * @parameter tTransDate 记录事务日期
		  * 如果存在返回true;否则返回false
	     **/
	  private boolean isExist(String tManageCom,String mVouchertype,String mTransDate)
	  {
		  String Sql = "";
		  int i = Integer.parseInt(mVouchertype);
		  switch(i)
		  {
		  case 1:
			  Sql="select count(*) from ljtempfee where confmakedate='"+"?mTransDate?"+"' and tempfeetype in ('1','5','6') and managecom like concat('"+"?tManageCom?"+"','%') ";
			  break;
		  case 2:
			  Sql="select count(*) from ljtempfee where confmakedate='"+"?mTransDate?"+"' and tempfeetype not in ('1','5','6')  and managecom like concat('"+"?tManageCom?"+"','%') ";
			  break;
		  case 3:
			  Sql="select count(*) from LJAPayPerson a where confdate='"+"?mTransDate?"+"' and not exists (select payno from ljapay where incometype='3' and payno=a.payno)  and managecom like concat('"+"?tManageCom?"+"','%') ";
			  break;
		  case 4:
			  Sql="select count(*) from LJAGetClaim  where MakeDate='"+"?mTransDate?"+"' and ManageCom like concat('"+"?tManageCom?"+"','%') ";
			  break;
		  case 5:
			  Sql = "select count(*) from LJAGetEndorse where MakeDate='"+"?mTransDate?"+"' and ManageCom like concat('"+"?tManageCom?"+"','%') ";
			  break;
		  case 6:
			  Sql = "select count(*) from LJAGetDraw where MakeDate='"+"?mTransDate?"+"' and ManageCom like concat('"+"?tManageCom?"+"','%') ";
			  break;
		  case 7:
			  Sql = "select sum(num) from (select count(*) num from LJAGetTempFee where MakeDate='"+"?mTransDate?"+"' and ManageCom like concat('"+"?tManageCom?"+"','%')"
			      + " union select count(*) num  from LJABonusGet where MakeDate='"+"?mTransDate?"+"' and ManageCom like concat('"+"?tManageCom?"+"','%')"
			      + "union select count(*) num from LJAGetOther where MakeDate='"+"?mTransDate?"+"' and ManageCom like concat('"+"?tManageCom?"+"','%')) g";
			  break;
		  case 8:
			  Sql = "select count(*) from LJAGet where ConfDate='" + "?mTransDate?" + "' and ManageCom like concat('"+"?tManageCom?"+"','%') ";
			  break;
		  case 9:
			  Sql = "select sum(num) from (select count(*) num from LJAPay where ConfDate='"+"?mTransDate?"+"' and ManageCom like concat('"+"?tManageCom?"+"','%') "
                  + "union select count(*) num from LJAGet where MakeDate = '"+"?mTransDate?"+"' and ManageCom like concat('"+"?tManageCom?"+"','%') and Othernotype = '3') g";
			  break;
	   }
		  SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
		  sqlbv5.sql(Sql);
		  sqlbv5.put("mTransDate", mTransDate);
		  sqlbv5.put("tManageCom", tManageCom);
		  ExeSQL tExeSQL = new ExeSQL();
		  if(!tExeSQL.execSQL(sqlbv5).GetText(1, 1).equals("0") && tExeSQL.execSQL(sqlbv5).MaxRow>0)
			  return true;
		  else			  
		  return false;

	  }

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
