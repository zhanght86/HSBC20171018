package com.sinosoft.lis.xbcheck;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.pubfun.*;

/**
 * <p>Title: Web业务系统承保个人单自动核保部分</p>
 * <p>Description: 数据库功能类</p>
 * <p>Copyright: Copyright (c) 2002 </p>
 * <p>Company: Sinosoft< /p>
 * @author WHN
 * @version 1.0
 */

public class PRnewUWAutoChkBLS
{
private static Logger logger = Logger.getLogger(PRnewUWAutoChkBLS.class);

  //是否存在需要人工核保保单
  int merrno = 0;
  //传输数据类
  private VData mInputData ;
  //错误处理类，每个需要错误处理的类中都放置该类
  public  CErrors mErrors = new CErrors();

  private LCUWErrorSet mLCUWErrorSet = new LCUWErrorSet();

  public PRnewUWAutoChkBLS() {}

  public static void main(String[] args)
  {
  }


  //传输数据的公共方法
  public boolean submitData(VData cInputData,String cOperate)
  {
	//首先将数据在本类中做一个备份
	mInputData=(VData)cInputData.clone() ;

	logger.debug("Start PRnewUWAutoChkBLS Submit...");
	if (!this.saveData())
      return false;
 logger.debug("End PRnewUWAutoChkBLS Submit...");

    mInputData=null;
    return true;
  }

  private boolean saveData()
  {
	LCPolSet mLCPolSet = (LCPolSet)mInputData.getObjectByObjectName("LCPolSet",0);
	int temp = mLCPolSet.size();
	logger.debug("sizeof"+temp);
	LCUWErrorSet mLCUWErrorSet = (LCUWErrorSet)mInputData.getObjectByObjectName("LCUWErrorSet",0);
	LCUWMasterSet mLCUWMasterSet = (LCUWMasterSet)mInputData.getObjectByObjectName("LCUWMasterSet",0);
	LCUWSubSet mLCUWSubSet = (LCUWSubSet)mInputData.getObjectByObjectName("LCUWSubSet",0);
	
	LCIndUWErrorSet mLCIndUWErrorSet = (LCIndUWErrorSet)mInputData.getObjectByObjectName("LCIndUWErrorSet",0);
	LCIndUWMasterSet mLCIndUWMasterSet = (LCIndUWMasterSet)mInputData.getObjectByObjectName("LCIndUWMasterSet",0);
	LCIndUWSubSet mLCIndUWSubSet = (LCIndUWSubSet)mInputData.getObjectByObjectName("LCIndUWSubSet",0);
	
	LCRnewStateHistorySet mLCRnewStateHistorySet = (LCRnewStateHistorySet)mInputData.getObjectByObjectName("LCRnewStateHistorySet",0);

	try
	{
	  //Connection conn = DBConnPool.getConnection();
	  // 删除部分
	  if(mLCPolSet!=null && mLCPolSet.size() >0)
	  {
		int polCount = mLCPolSet.size();
	  for (int j = 1; j <= polCount; j++)
	  {
		LCPolSchema tLCPolSchema = new LCPolSchema();
		tLCPolSchema = ( LCPolSchema )mLCPolSet.get( j );
		String tProposalNo = tLCPolSchema.getProposalNo();
		String tUWFlag = tLCPolSchema.getUWFlag();

		Connection conn = DBConnPool.getConnection();

		if (conn==null)
		{
		  // @@错误处理
		  CError tError = new CError();
		  tError.moduleName = "PRnewUWAutoChkBLS";
		  tError.functionName = "saveData";
		  tError.errorMessage = tProposalNo+"数据库连接失败!";
		  this.mErrors .addOneError(tError) ;
		  //return false;
		  continue;
		}

		conn.setAutoCommit(false);

		// 核保主表
		LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB( conn );
		tLCUWMasterDB.setProposalNo( tProposalNo );
		if (tLCUWMasterDB.deleteSQL() == false)
		{
		  // @@错误处理
		  this.mErrors.copyAllErrors(tLCUWMasterDB.mErrors);
		  CError tError = new CError();
		  tError.moduleName = "PRnewUWAtuoChkBLS";
		  tError.functionName = "saveData";
		  tError.errorMessage = tProposalNo+"个人核保总表删除失败!";
		  this.mErrors .addOneError(tError) ;
		  conn.rollback() ;
		  conn.close();
		  //return false;
		  continue;
		}

		// 投保单
		LCPolDB tLCPolDB = new LCPolDB( conn );
		// 保存部分
		// 保单
		tLCPolDB.setSchema(tLCPolSchema);

		if(tLCPolDB.update() == false)
		{
		  // @@错误处理
		  this.mErrors.copyAllErrors(tLCPolDB.mErrors);
		  CError tError = new CError();
		  tError.moduleName = "PRnewUWAutoChkBLS";
		  tError.functionName = "saveData";
		  tError.errorMessage = tProposalNo+"个人保单表保存失败!";
		  this.mErrors .addOneError(tError) ;
		  conn.rollback() ;
		  conn.close();
		  //return false;
		  continue;
		}

		logger.debug("-----------DDD--------------");
		// 核保主表
		if(mLCUWMasterSet!=null && mLCUWMasterSet.size() >0)
		{
		  int masterno = mLCUWMasterSet.size();
		for (int i = 1;i <= masterno;i++)
		{
		  LCUWMasterSchema tLCUWMasterSchema = mLCUWMasterSet.get(i);
		  String masterpol = tLCUWMasterSchema.getProposalNo();
		  if (masterpol.equals(tProposalNo))
		  {
			tLCUWMasterDB.setSchema(tLCUWMasterSchema);
			if (tLCUWMasterDB.insert() == false)
			{
			  // @@错误处理
			  this.mErrors.copyAllErrors(tLCUWMasterDB.mErrors);
			  CError tError = new CError();
			  tError.moduleName = "PRnewUWAutoChkBLS";
			  tError.functionName = "saveData";
			  tError.errorMessage = tProposalNo+"个人核保总表保存失败!";
			  this.mErrors .addOneError(tError) ;
			  conn.rollback() ;
			  conn.close();
			  //return false;
			  continue;
			}
		  }
		}
		}
		logger.debug("-----------EEE--------------");
		// 核保子表
		if(mLCUWSubSet!=null&&mLCUWSubSet.size() >0)
		{
		  int subno = mLCUWSubSet.size();
		for (int i = 1;i <= subno;i++)
		{
		  LCUWSubSchema tLCUWSubSchema = mLCUWSubSet.get(i);
		  String subpol = tLCUWSubSchema.getProposalNo();
		  if (subpol.equals(tProposalNo))
		  {
			LCUWSubDB tLCUWSubDB = new LCUWSubDB(conn);
			tLCUWSubDB.setSchema(tLCUWSubSchema);
			if (tLCUWSubDB.insert() == false)
			{
			  // @@错误处理
			  this.mErrors.copyAllErrors(tLCUWSubDB.mErrors);
			  CError tError = new CError();
			  tError.moduleName = "PRnewUWAutoChkBLS";
			  tError.functionName = "saveData";
			  tError.errorMessage = tProposalNo+"个人核保轨迹表保存失败!";
			  this.mErrors .addOneError(tError) ;
			  conn.rollback() ;
			  conn.close();
			  //return false;
			  continue;
			}
		  }
		}
		}
		logger.debug("-----------FFF--------------");
		// 核保错误信息表
		if(mLCUWErrorSet!=null && mLCUWErrorSet.size()>0 )
		{
		  int errno = mLCUWErrorSet.size();
		merrno = merrno + errno;
		for (int i = 1; i<=errno; i++)
		{
		  LCUWErrorSchema tLCUWErrorSchema = new LCUWErrorSchema();
		  tLCUWErrorSchema = mLCUWErrorSet.get(i);
		  String errpol = tLCUWErrorSchema.getPolNo();
		  if (errpol.equals(tProposalNo))
		  {
			LCUWErrorDB tLCUWErrorDB = new LCUWErrorDB(conn);
			tLCUWErrorDB.setSchema(tLCUWErrorSchema);
			if (tLCUWErrorDB.insert() == false)
			{
			  // @@错误处理
			  this.mErrors.copyAllErrors(tLCUWErrorDB.mErrors);
			  CError tError = new CError();
			  tError.moduleName = "PRnewUWAutoChkBLS";
			  tError.functionName = "saveData";
			  tError.errorMessage = tProposalNo+"个人错误信息表保存失败!";
			  this.mErrors .addOneError(tError) ;
			  conn.rollback() ;
			  conn.close();
			  //return false;
			  continue;
			}
		  }
		}
		}

		//开始处理被保人核保表
        //被保人核保主表
		LCIndUWMasterDB tLCIndUWMasterDB = new LCIndUWMasterDB( conn );
		tLCIndUWMasterDB.setContNo(tLCPolSchema.getContNo());
		tLCIndUWMasterDB.setInsuredNo(tLCPolSchema.getInsuredNo());
		if (tLCIndUWMasterDB.deleteSQL() == false)
		{
		  // @@错误处理
		  this.mErrors.copyAllErrors(tLCIndUWMasterDB.mErrors);
		  CError tError = new CError();
		  tError.moduleName = "PRnewUWAtuoChkBLS";
		  tError.functionName = "saveData";
		  tError.errorMessage = "保单号"+tLCPolSchema.getContNo()+"被保人核保总表删除失败!";
		  this.mErrors .addOneError(tError) ;
		  conn.rollback() ;
		  conn.close();
		  //return false;
		  continue;
		}
		
		if(mLCIndUWMasterSet!=null && mLCIndUWMasterSet.size() >0)
		{
		   int masterno2 = mLCIndUWMasterSet.size();
		   for (int i = 1;i <= masterno2;i++)
			{
			  LCIndUWMasterSchema tLCIndUWMasterSchema = mLCIndUWMasterSet.get(i);
			  
				tLCIndUWMasterDB.setSchema(tLCIndUWMasterSchema);
				if (tLCIndUWMasterDB.insert() == false)
				{
				  // @@错误处理
				  this.mErrors.copyAllErrors(tLCIndUWMasterSchema.mErrors);
				  CError tError = new CError();
				  tError.moduleName = "PRnewUWAutoChkBLS";
				  tError.functionName = "saveData";
				  tError.errorMessage = tProposalNo+"被保人核保总表保存失败!";
				  this.mErrors .addOneError(tError) ;
				  conn.rollback() ;
				  conn.close();
				  //return false;
				  continue;
				}
			}
		}
        //被保人核保子表
		if(mLCIndUWSubSet!=null&&mLCIndUWSubSet.size() >0)
		{
		  int subno2 = mLCIndUWSubSet.size();
		  for (int i = 1;i <= subno2;i++)
		   {
			  LCIndUWSubSchema tLCIndUWSubSchema = mLCIndUWSubSet.get(i);		 
			  LCIndUWSubDB tLCIndUWSubDB = new LCIndUWSubDB(conn);
			  tLCIndUWSubDB.setSchema(tLCIndUWSubSchema);
			  if (tLCIndUWSubDB.insert() == false)
			   {
				  // @@错误处理
				  this.mErrors.copyAllErrors(tLCIndUWSubDB.mErrors);
				  CError tError = new CError();
				  tError.moduleName = "PRnewUWAutoChkBLS";
				  tError.functionName = "saveData";
				  tError.errorMessage = tProposalNo+"被保人核保轨迹子表保存失败!";
				  this.mErrors .addOneError(tError) ;
				  conn.rollback() ;
				  conn.close();
				  //return false;
				  continue;
		       }
		   }
		}
		logger.debug("-----------FFF--------------");
		// 被保人核保错误信息表
		if(mLCIndUWErrorSet!=null && mLCIndUWErrorSet.size()>0 )
		{
		   int errno2 = mLCIndUWErrorSet.size();
		   merrno = merrno + errno2;
		   for (int i = 1; i<=errno2; i++)
			{
			  LCIndUWErrorSchema tLCIndUWErrorSchema = new LCIndUWErrorSchema();
			  tLCIndUWErrorSchema = mLCIndUWErrorSet.get(i);
			  
				LCIndUWErrorDB tLCIndUWErrorDB = new LCIndUWErrorDB(conn);
				tLCIndUWErrorDB.setSchema(tLCIndUWErrorSchema);
				if (tLCIndUWErrorDB.insert() == false)
				{
				  // @@错误处理
				  this.mErrors.copyAllErrors(tLCIndUWErrorDB.mErrors);
				  CError tError = new CError();
				  tError.moduleName = "PRnewUWAutoChkBLS";
				  tError.functionName = "saveData";
				  tError.errorMessage = tProposalNo+"被保人核保错误信息表保存失败!";
				  this.mErrors .addOneError(tError) ;
				  conn.rollback() ;
				  conn.close();
				  //return false;
				  continue;
				}
			}
		}
		
        if(mLCRnewStateHistorySet != null && mLCRnewStateHistorySet.size() >0)
		{
		  int teNo = mLCRnewStateHistorySet.size();
		for (int i = 1; i<=teNo; i++)
		{
		  LCRnewStateHistorySchema tLCRnewStateHistorySchema = new LCRnewStateHistorySchema();
		  tLCRnewStateHistorySchema = mLCRnewStateHistorySet.get(i);
		  String tpol = tLCRnewStateHistorySchema.getProposalNo() ;
		  if (tpol.equals(tProposalNo))
		  {
		  LCRnewStateHistoryDB tLCRnewStateHistoryDB = new LCRnewStateHistoryDB(conn);
		  tLCRnewStateHistoryDB.setSchema(mLCRnewStateHistorySet.get(i) );
		  if (!tLCRnewStateHistoryDB.update() )
		  {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PRnewUWAutoChkBLS";
			tError.functionName = "saveData";
			tError.errorMessage = "续保状态记录数据更新失败!";
			this.mErrors .addOneError(tError) ;
			conn.rollback();
			conn.close();
			return false;
		  }
		 }
		}
		}
		conn.commit() ;
	    conn.close();

	   }
	  } // end of for
//	  conn.commit() ;
//	  conn.close();

	  //conn.close();
	} // end of try
	catch (Exception ex)
	{
	  // @@错误处理
	  CError tError = new CError();
	  tError.moduleName = "PRnewUWAutoChkBLS";
	  tError.functionName = "submitData";
	  tError.errorMessage = ex.toString();
	  this.mErrors .addOneError(tError);
	  return false;
	}

	return true;
  }

}
