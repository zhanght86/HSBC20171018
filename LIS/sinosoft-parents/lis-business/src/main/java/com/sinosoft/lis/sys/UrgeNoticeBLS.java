package com.sinosoft.lis.sys;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.vdb.LOPRTManagerDBSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author lh
 * @version 1.0
 */

public class UrgeNoticeBLS {
private static Logger logger = Logger.getLogger(UrgeNoticeBLS.class);

	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();
	private VData mInputData;

	public UrgeNoticeBLS() {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		boolean tReturn = false;
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();

		logger.debug("Start UrgeNotice BLS Submit...");
		// //执行添加纪录活动
		// if(cOperate.equals("INSERT"))
		// {
		// tReturn=save();
		// if (tReturn)
		// logger.debug("Save sucessful");
		// else
		// logger.debug("Save failed") ;
		// }
		// 执行更新纪录活动
		if (cOperate.equals("UPDATE")) {
			tReturn = save();
			if (tReturn) {
				logger.debug("Update sucessful");
			} else {
				logger.debug("Update failed");
			}
		}
		if (cOperate.equals("INSERT")) {
			tReturn = save();
			if (tReturn) {
				logger.debug("INSERT sucessful");
			} else {
				logger.debug("INSERT failed");
			}
		}
		// //执行删除纪录活动
		// if(cOperate.equals("DELETE"))
		// {
		// tReturn=delete();
		// if (tReturn)
		// logger.debug("Delete sucessful");
		// else
		// logger.debug("Delete failed") ;
		// }

		logger.debug("End UrgeNotice BLS Submit...");

		// 如果有需要处理的错误，则返回
		// if (tLDGrpBLS.mErrors .needDealError() )
		// this.mErrors .copyAllErrors(tLDGrpBLS.mErrors ) ;

		mInputData = null;
		return tReturn;
	}

	// private boolean save()
	// {
	// boolean tReturn =true;
	// logger.debug("Start Save...");
	//
	// Connection conn=DBConnPool.getConnection();
	// if (conn==null)
	// {
	// // @@错误处理
	// CError tError = new CError();
	// tError.moduleName = "LDGrpBLS";
	// tError.functionName = "save";
	// tError.errorMessage = "数据库连接失败!";
	// this.mErrors .addOneError(tError) ;
	// return false;
	// }
	// try{
	// /** 集体信息 */
	// logger.debug("Start 集体信息...");
	//
	// LDGrpDB tLDGrpDB=new LDGrpDB(conn);
	// tLDGrpDB.setSchema((LDGrpSchema)mInputData.getObjectByObjectName("LDGrpSchema",0));
	// tLDGrpDB.insert() ;
	// conn.close();
	// }
	// catch (Exception ex)
	// {
	// logger.debug("Exception in BLS");
	// logger.debug("Exception:"+ex.toString());
	// // @@错误处理
	// CError tError =new CError();
	// tError.moduleName="LDGrpBLS";
	// tError.functionName="submitData";
	// tError.errorMessage=ex.toString() ;
	// this.mErrors .addOneError(tError) ;
	// try{
	// conn.close();
	// } catch(Exception e){}
	// tReturn=false;
	// }
	//
	// return tReturn;
	// }

	private boolean save() {
		boolean tReturn = true;
		LOPRTManagerSet NewLOPRTManagerSet = new LOPRTManagerSet();
		LOPRTManagerSet OldLOPRTManagerSet = new LOPRTManagerSet();
		NewLOPRTManagerSet.set((LOPRTManagerSet) mInputData.getObject(0));
		OldLOPRTManagerSet.set((LOPRTManagerSet) mInputData.getObject(1));
		logger.debug("Start Update...");
		Connection conn = DBConnPool.getConnection();
		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UrgeNoticeBLS";
			tError.functionName = "update";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		try {
			logger.debug("Start 催收信息...");
			conn.setAutoCommit(false);
			if (OldLOPRTManagerSet.size() != 0
					&& (NewLOPRTManagerSet.size() != 0)) {
				LOPRTManagerDBSet NewLOPRTManagerDBSet = new LOPRTManagerDBSet(
						conn);
				NewLOPRTManagerDBSet.set(NewLOPRTManagerSet);
				LOPRTManagerDBSet OldLOPRTManagerDBSet = new LOPRTManagerDBSet(
						conn);
				OldLOPRTManagerDBSet.set(OldLOPRTManagerSet);
				if (!NewLOPRTManagerDBSet.insert()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "UrgeNoticeBLS";
					tError.functionName = "save";
					tError.errorMessage = "保存所发的催收通知书数据信息失败!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();
					return false;
				}
				if (!OldLOPRTManagerDBSet.update()) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "UrgeNoticeBLS";
					tError.functionName = "save";
					tError.errorMessage = "更改原来通知书打印状态信息失败!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}
			conn.commit();
			conn.close();
		} catch (Exception ex) {
			logger.debug("Exception in BLS");
			logger.debug("Exception:" + ex.toString());
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UrgeNoticeBLS";
			tError.functionName = "submitData";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			try {
				conn.rollback();
				conn.close();
			} catch (Exception e) {
			}
			tReturn = false;
		}
		return tReturn;
	}

	// private boolean delete()
	// {
	// boolean tReturn =true;
	// logger.debug("Start Delete...");
	// Connection conn=DBConnPool.getConnection();
	// if (conn==null)
	// {
	// // @@错误处理
	// CError tError = new CError();
	// tError.moduleName = "UrgeNoticeBLS";
	// tError.functionName = "delete";
	// tError.errorMessage = "数据库连接失败!";
	// this.mErrors .addOneError(tError) ;
	// return false;
	// }
	// try{
	// /** 集体信息 */
	// logger.debug("Start 集体信息...");
	// LDGrpDB tLDGrpDB=new LDGrpDB(conn);
	// tLDGrpDB.setSchema((LDGrpSchema)mInputData.getObjectByObjectName("LDGrpSchema",0));
	// tLDGrpDB.delete() ;
	// conn.close();
	// }
	// catch (Exception ex)
	// {
	// logger.debug("Exception in BLS");
	// logger.debug("Exception:"+ex.toString());
	// // @@错误处理
	// CError tError =new CError();
	// tError.moduleName="UrgeNoticeBLS";
	// tError.functionName="submitData";
	// tError.errorMessage=ex.toString() ;
	// this.mErrors .addOneError(tError) ;
	// try{
	// conn.close();
	// } catch(Exception e){}
	// tReturn=false;
	// }
	// return tReturn;
	// }

	// //查询纪录的公共方法
	// public LDGrpSet queryData(VData cInputData)
	// {
	// //首先将数据在本类中做一个备份
	// mInputData=(VData)cInputData.clone() ;
	// logger.debug("Start LDGrp BLS Query...");
	// //执行查询纪录活动
	// logger.debug("Start Query...");
	// Connection conn=DBConnPool.getConnection();
	// if (conn==null)
	// {
	// // @@错误处理
	// CError tError = new CError();
	// tError.moduleName = "UrgeNoticeBLS";
	// tError.functionName = "queryData";
	// tError.errorMessage = "数据库连接失败!";
	// this.mErrors .addOneError(tError) ;
	// return null;
	// }
	//
	// try{
	// /** 集体信息 */
	// logger.debug("Start 集体信息...");
	// LDGrpDB tLDGrpDB=new LDGrpDB(conn);
	// tLDGrpDB.setSchema((LDGrpSchema)mInputData.getObjectByObjectName("LDGrpSchema",0));
	// mLDGrpSet=(LDGrpSet)tLDGrpDB.query() ;
	// conn.close();
	// }
	// catch (Exception ex)
	// {
	// logger.debug("Exception in BLS");
	// logger.debug("Exception:"+ex.toString());
	// // @@错误处理
	// CError tError =new CError();
	// tError.moduleName="UrgeNoticeBLS";
	// tError.functionName="QueryData";
	// tError.errorMessage=ex.toString() ;
	// this.mErrors .addOneError(tError) ;
	// mLDGrpSet=null;
	// try{
	// conn.close();
	// } catch(Exception e){}
	// return null;
	// }
	// mInputData=null;
	// return mLDGrpSet;
	// }

	public static void main(String[] args) {
		UrgeNoticeBLS urgeNoticeBLS1 = new UrgeNoticeBLS();
	}
}
