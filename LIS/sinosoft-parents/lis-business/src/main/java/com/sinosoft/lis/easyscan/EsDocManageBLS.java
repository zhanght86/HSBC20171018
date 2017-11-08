package com.sinosoft.lis.easyscan;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.db.ES_DOC_MAINDB;
import com.sinosoft.lis.db.ES_DOC_PAGESDB;
import com.sinosoft.lis.db.ES_DOC_RELATIONDB;
import com.sinosoft.lis.db.LCDelTraceDB;
import com.sinosoft.lis.schema.ES_DOC_MAINSchema;
import com.sinosoft.lis.schema.LCDelTraceSchema;
import com.sinosoft.lis.vdb.ES_DOC_MAINDBSet;
import com.sinosoft.lis.vdb.ES_DOC_PAGESDBSet;
import com.sinosoft.lis.vdb.ES_DOC_RELATIONDBSet;
import com.sinosoft.lis.vschema.ES_DOC_MAINSet;
import com.sinosoft.lis.vschema.ES_DOC_PAGESSet;
import com.sinosoft.lis.vschema.ES_DOC_RELATIONSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: EasyScan单证索引管理
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Liuqiang
 * @version 1.0
 */
public class EsDocManageBLS {
private static Logger logger = Logger.getLogger(EsDocManageBLS.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();
	private VData mInputData;
	private VData mResult = new VData();

	// 输入参数私有变量
	private ES_DOC_MAINSchema tES_DOC_MAINSchema;
	private ES_DOC_PAGESSet tES_DOC_PAGESSet;
	private ES_DOC_RELATIONSet tES_DOC_RELATIONSet;

	public EsDocManageBLS() {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		boolean tReturn = true;

		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();

		getInputData();

		// DELETE||MAIN UPDATE||MAIN UPDATE||PAGES DELETE||PAGES
		tReturn = saveModify(cOperate);
		/*
		 * if (cOperate.equals("DELETE||MAIN")) { tReturn = deleteMain(); } if
		 * (cOperate.equals("DELETE||PAGES")) { tReturn = deletePages(); } if
		 * (cOperate.equals("UPDATE||MAIN")) { tReturn = updateMain(); } if
		 * (cOperate.equals("UPDATE||PAGES")) { tReturn = updatePages(); }
		 */
		if (tReturn) {
			logger.debug("EsDocManageBLS:Save Successful!");
		} else {
			logger.debug("EsDocManageBLS:Save Failed!");
		}

		mResult.clear();
		mResult = mInputData;
		mInputData = null;

		return tReturn;
	}

	// 获取返回数据
	public VData getResult() {
		return mResult;
	}

	// 入参处理
	private boolean getInputData() {
		// 获取入参
		tES_DOC_MAINSchema = (ES_DOC_MAINSchema) mInputData.getObjectByObjectName("ES_DOC_MAINSchema", 0);
		tES_DOC_PAGESSet = (ES_DOC_PAGESSet) mInputData.getObjectByObjectName("ES_DOC_PAGESSet", 0);
		tES_DOC_RELATIONSet = (ES_DOC_RELATIONSet) mInputData.getObjectByObjectName("ES_DOC_RELATIONSet", 0);
		return true;
	}

	// 保存单证修改操作
	private boolean saveModify(String cOperate) {
		boolean blnReturn = false;
		logger.debug("Start saveModify ...");
		Connection conn = DBConnPool.getConnection();
		try {
			conn.setAutoCommit(false);

			if (conn == null) {
				// @@错误处理
				CError.buildErr(this, "数据库连接失败") ;
				return false;
			}

			conn.setAutoCommit(false);

			// 处理单证主表
			logger.debug("Dealing ES_DOC_MAIN ...");

			ES_DOC_MAINDB tES_DOC_MAINDB = new ES_DOC_MAINDB(conn);
			tES_DOC_MAINDB.setSchema(tES_DOC_MAINSchema);
			blnReturn = true;
			if (cOperate.equals("DELETE||DOCID")) // 删除单证主表数据
			{
				blnReturn = tES_DOC_MAINDB.delete();
				logger.debug("Dealing LCDelTrace ...");
				LCDelTraceSchema tLCDelTraceSchema = (LCDelTraceSchema) mInputData.getObjectByObjectName("LCDelTraceSchema", 0);
				if(tLCDelTraceSchema!=null)
				{
					LCDelTraceDB tLCDelTraceDB = new LCDelTraceDB(conn);
					tLCDelTraceDB.setSchema(tLCDelTraceSchema);
					
					if(blnReturn == true)
						blnReturn = tLCDelTraceDB.insert();
				}
				
			}
			else if (cOperate.equals("UPDATE||DOCID")) // 修改单证主表单证类型
			{
				blnReturn = tES_DOC_MAINDB.update();				
			}
			else if (cOperate.equals("UPDATE||MAIN")) // 更新单证主表数据 --印刷号修改
			{
				ES_DOC_MAINSet tES_DOC_MAINSet = (ES_DOC_MAINSet) mInputData.getObjectByObjectName("ES_DOC_MAINSet", 0);
				ES_DOC_MAINDBSet tES_DOC_MAINDBSet = new ES_DOC_MAINDBSet();
				tES_DOC_MAINDBSet.set(tES_DOC_MAINSet);	
				blnReturn = tES_DOC_MAINDBSet.update();
			}
			else if (cOperate.equals("DELETE||MAIN")) // 删除印刷号下所有单证主表数据
			{
				ES_DOC_MAINSet tES_DOC_MAINSet = (ES_DOC_MAINSet) mInputData.getObjectByObjectName("ES_DOC_MAINSet", 0);
				ES_DOC_MAINDBSet tES_DOC_MAINDBSet = new ES_DOC_MAINDBSet();
				tES_DOC_MAINDBSet.set(tES_DOC_MAINSet);	
				blnReturn = tES_DOC_MAINDBSet.delete();
				logger.debug("Dealing LCDelTrace ...");
				LCDelTraceSchema tLCDelTraceSchema = (LCDelTraceSchema) mInputData.getObjectByObjectName("LCDelTraceSchema", 0);
				if(tLCDelTraceSchema!=null)
				{
					LCDelTraceDB tLCDelTraceDB = new LCDelTraceDB(conn);
					tLCDelTraceDB.setSchema(tLCDelTraceSchema);
					
					if(blnReturn == true)
						blnReturn = tLCDelTraceDB.insert();
				}
			}
			else if(cOperate.equals("DELETE||PAGES"))//删除单证或修改单证页码
			{
				ES_DOC_MAINSet tES_DOC_MAINSetD = (ES_DOC_MAINSet) mInputData.getObjectByObjectName("ES_DOC_MAINSet", 4);
				ES_DOC_MAINSet tES_DOC_MAINSetU = (ES_DOC_MAINSet) mInputData.getObjectByObjectName("ES_DOC_MAINSet", 5);
				ES_DOC_MAINDBSet tES_DOC_MAINDBSetD = new ES_DOC_MAINDBSet();
				ES_DOC_MAINDBSet tES_DOC_MAINDBSetU = new ES_DOC_MAINDBSet();
				if(tES_DOC_MAINSetD!=null && tES_DOC_MAINSetD.size()>0)
				{
					tES_DOC_MAINDBSetD.set(tES_DOC_MAINSetD);
					blnReturn = tES_DOC_MAINDBSetD.delete();
				}
				else if(tES_DOC_MAINSetU!=null && tES_DOC_MAINSetU.size()>0 && blnReturn == true)
				{
					tES_DOC_MAINDBSetU.set(tES_DOC_MAINSetU);
					blnReturn = tES_DOC_MAINDBSetU.update();
				}
					
			}
			if (blnReturn == false) {
				// @@错误处理
				CError.buildErr(this, "保存数据库ES_DOC_MAIN表的修改出现错误") ;
				conn.rollback();
				conn.close();
				return false;
			}

			// 处理单证页表数据
			logger.debug("Dealing ES_DOC_PAGES ...");
			ES_DOC_PAGESDBSet tES_DOC_PAGESDBSet = new ES_DOC_PAGESDBSet();
			tES_DOC_PAGESDBSet.set(tES_DOC_PAGESSet);
			blnReturn = true;
			if (cOperate.equals("DELETE||MAIN")) // 删除单证同时删除所有页
			{
				blnReturn = tES_DOC_PAGESDBSet.delete();
			}
			else if (cOperate.equals("DELETE||DOCID")) // 删除单证同时删除所有页
			{
				ES_DOC_PAGESDB tES_DOC_PAGESDB = new ES_DOC_PAGESDB();
				tES_DOC_PAGESDB.setDocID(tES_DOC_MAINSchema.getDocID());
				blnReturn = tES_DOC_PAGESDB.deleteSQL();
			}
			else if (cOperate.equals("DELETE||PAGES")) // 删除单证页数据
			{
				blnReturn = tES_DOC_PAGESDBSet.delete();
			}
			else if (cOperate.equals("UPDATE||PAGES")) // 更新单证页数据
			{
				blnReturn = tES_DOC_PAGESDBSet.update();
			}
			if (blnReturn == false) {
				// @@错误处理
				CError.buildErr(this, "保存数据库ES_DOC_PAGES表的修改出现错误") ;
				conn.rollback();
				conn.close();
				return false;
			}
			
			
			// 处理es_doc_relation数据
			logger.debug("Dealing es_doc_relation ...");
			ES_DOC_RELATIONDBSet tES_DOC_RELATIONDBSet = new ES_DOC_RELATIONDBSet();
			tES_DOC_RELATIONDBSet.set(tES_DOC_RELATIONSet);
			blnReturn = true;
			if (cOperate.equals("UPDATE||DOCID")) // 修改单证主表单证类型
			{
				if(tES_DOC_RELATIONSet!=null && tES_DOC_RELATIONSet.size()>0)
				{
					blnReturn = tES_DOC_RELATIONDBSet.update();	
				}							
			}
			else if (cOperate.equals("DELETE||DOCID")) // 删除单证同时删除所有页
			{
				ES_DOC_RELATIONDB tES_DOC_RELATIONDB = new ES_DOC_RELATIONDB();
				tES_DOC_RELATIONDB.setDocID(tES_DOC_MAINSchema.getDocID());
				blnReturn = tES_DOC_RELATIONDB.deleteSQL();
			}
			else if (cOperate.equals("DELETE||MAIN")) // 删除单证主表
			{
				if(tES_DOC_RELATIONSet!=null && tES_DOC_RELATIONSet.size()>0)
				{
					blnReturn = tES_DOC_RELATIONDBSet.delete();	
				}							
			}
			
			if (blnReturn == false) {
				// @@错误处理
				CError.buildErr(this, "保存数据库ES_DOC_PAGES表的修改出现错误") ;
				conn.rollback();
				conn.close();
				return false;
			}
			
			// 提交事务
			// conn.rollback();//测试用
			conn.commit();

			conn.close();
		} catch (Exception ex) {
			logger.debug("Exception in BLS");
			logger.debug("Exception:" + ex.toString());

			// @@错误处理
			CError.buildErr(this, ex.toString()) ;
			try {
				conn.rollback();
				conn.close();
			} catch (Exception e) {
			}
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		EsDocManageBLS tEsDocManageBLS = new EsDocManageBLS();
	}
}
