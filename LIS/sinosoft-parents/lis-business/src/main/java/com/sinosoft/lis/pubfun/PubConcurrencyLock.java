package com.sinosoft.lis.pubfun;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import com.sinosoft.lis.db.LockAppGroupDB;
import com.sinosoft.lis.db.LockConfigDB;
import com.sinosoft.lis.db.UnLockLogDB;
import com.sinosoft.lis.schema.LockAppGroupSchema;
import com.sinosoft.lis.schema.UnLockLogSchema;
import com.sinosoft.lis.vschema.LockAppGroupSet;
import com.sinosoft.lis.vschema.LockConfigSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:公共并发控制程序
 * </p>
 * <p>
 * Description: 主要控制点并发
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author tongmeng
 * @version 1.0
 */

public class PubConcurrencyLock implements BusinessService{
private static Logger logger = Logger.getLogger(PubConcurrencyLock.class);
	public CErrors mErrors = new CErrors(); // 错误信息
	private Connection con;
	// private String mReason = "锁定应用模块不明确！";

	// 保存已经锁定的业务
	private TransferData mLockTransferData = new TransferData();
	//tongmeng 2008-10-20 modify
	//使用hashtable控制解锁.
	private Hashtable mLockHashtable = new Hashtable();
	private String mOpeartor = "AutoLock";
	private ExeSQL mExeSQL = new ExeSQL();

	public PubConcurrencyLock() {
	}

	/**
	 * 锁表操作
	 * 
	 * @param conn
	 * @param tOperatedNo
	 * @return
	 */
	public boolean pareLockData(Connection conn, String tOperatedNo,
			String tLockModule) {
		String tMakeDate = PubFun.getCurrentDate();
		String tMakeTime = PubFun.getCurrentTime();
		try {
			LockAppGroupSchema tLockAppGroupSchema = new LockAppGroupSchema();
			LockAppGroupSet tLockAppGroupSet = new LockAppGroupSet();

			// 获取当前业务模块所属的并发控制组.
			LockConfigSet tLockConfigSet = new LockConfigSet();
			tLockConfigSet = this.getLockConfig(conn, tLockModule);
			if (tLockConfigSet.size() <= 0) {
				// 没有为模块配置并发控制组.不做锁定,直接返回true;
				logger.debug("没有配置并发控制组,不做锁定");
				return true;
			}
			for (int i = 1; i <= tLockConfigSet.size(); i++) {
				LockAppGroupDB tLockAppGroupDB = new LockAppGroupDB(conn);
				tLockAppGroupDB.setOperatedNo(tOperatedNo);
				tLockAppGroupDB.setLockGroup(tLockConfigSet.get(i)
						.getLockGroup());
				tLockAppGroupDB.setState("1");
				tLockAppGroupDB.setOperator(mOpeartor);
				tLockAppGroupDB.setReason(tLockModule);
				tLockAppGroupDB.setMakeDate(tMakeDate);
				tLockAppGroupDB.setMakeTime(tMakeTime);
				tLockAppGroupDB.setModifyDate(tMakeDate);
				tLockAppGroupDB.setModifyTime(tMakeTime);
				if (!tLockAppGroupDB.insert()) {
					CError.buildErr(this, "业务号" + tOperatedNo + "所属业务组"
							+ tLockConfigSet.get(i).getLockGroup()
							+ "当前被锁定,请稍后重试!");
					// 插入失败,认为已经被锁定
					// 在外面做连接关闭和回滚操作
					// 清除所有和tLockModule 有关的数据
					//tongmeng 2008-10-20 modify
					//使用hashtable
					Enumeration eKey=mLockHashtable.keys(); 
					while(eKey.hasMoreElements()) 
					{ 
						String key=(String)eKey.nextElement();
						String tValue = (String)mLockHashtable.get(key);
						if ((tValue).equals(tLockModule + tOperatedNo)) {
							mLockHashtable.remove(key);
						}
					}

//					Vector tLockClear = this.mLockTransferData.getValueNames();
//					for (int n = 0; n < tLockClear.size(); n++) {
//						String tGroupModuleNo = (String) tLockClear.get(n);
//						String tempModule = (String) this.mLockTransferData
//								.getValueByName(tGroupModuleNo);
//						if ((tempModule).equals(tLockModule + tOperatedNo)) {
//							this.mLockTransferData.removeByName(tGroupModuleNo);
//						}
//					}
					return false;
				}
				// 锁定成功,将锁定成功的业务号和业务组保存起来,便于后面解锁
				//tongmeng 2008-10-20 modify
				//使用hashtable
				this.mLockHashtable.put(tOperatedNo + ":" + tLockConfigSet.get(i).getLockGroup(), tLockModule + tOperatedNo);
//				this.mLockTransferData.setNameAndValue(tOperatedNo + ":"
//						+ tLockConfigSet.get(i).getLockGroup(), tLockModule
//						+ tOperatedNo);
			}
		} catch (Exception ex) {
			CError.buildErr(this, ex.toString());

			return false;
		}
		return true;
	}

	/**
	 * 解锁操作
	 * 
	 * @param conn
	 * @param tOperatedNo
	 * @return
	 */
	public boolean pareUnLockData(Connection conn) {
		try {
			//tongmeng 2008-10-20 modify
			//使用hashtable
			Enumeration eKey=mLockHashtable.keys(); 
			while(eKey.hasMoreElements()) 
			{ 
				String key=(String)eKey.nextElement();
				String tValue = (String)mLockHashtable.get(key);
				//获取后从hashtable中移除 add by wk
				mLockHashtable.remove(key);
				
				String[] tempNo = key.split(":");
				String tOperatedNo = tempNo[0];
				String tGroupNo = tempNo[1];
				LockAppGroupDB tLockAppGroupDB = new LockAppGroupDB(conn);
				tLockAppGroupDB.setOperatedNo(tOperatedNo);
				tLockAppGroupDB.setLockGroup(tGroupNo);

				if (!tLockAppGroupDB.delete()) {
					// 在外面做连接关闭和回滚操作
					return false;
				}
			}
			
//			Vector tLockGroup = this.mLockTransferData.getValueNames();
//			for (int i = 0; i < tLockGroup.size(); i++) {
//				String tGroupModuleNo = (String) tLockGroup.get(i);
//				String[] tempNo = tGroupModuleNo.split(":");
//				String tOperatedNo = tempNo[0];
//				String tGroupNo = tempNo[1];
//				LockAppGroupDB tLockAppGroupDB = new LockAppGroupDB(conn);
//				tLockAppGroupDB.setOperatedNo(tOperatedNo);
//				tLockAppGroupDB.setLockGroup(tGroupNo);
//
//				if (!tLockAppGroupDB.delete()) {
//					// 在外面做连接关闭和回滚操作
//					return false;
//				}
//				//this.mLockTransferData.removeByName(tGroupModuleNo);
//			}

		} catch (Exception ex) {
			CError.buildErr(this, ex.toString());
			return false;
		}
		return true;
	}
	
	public boolean pareUnLockData(Connection conn,String tOperatedNo,String tLockModule) {
		try {
			String tSql = "select lockgroup from lockconfig where lockmodule= '"+"?tLockModule?"+"'";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSql);
			sqlbv.put("tLockModule", tLockModule);
			SSRS tSSRS = mExeSQL.execSQL(sqlbv);
			if(tSSRS != null && tSSRS.MaxRow>0){
				for(int i = 1;i<=tSSRS.MaxRow; i++){
					String tGroupNo = tSSRS.GetText(i, 1);
					LockAppGroupDB tLockAppGroupDB = new LockAppGroupDB(conn);
					tLockAppGroupDB.setOperatedNo(tOperatedNo);
					tLockAppGroupDB.setLockGroup(tGroupNo);
					
					if (!tLockAppGroupDB.delete()) {
						return false;
					}
				}
			}
		} catch (Exception ex) {
			CError.buildErr(this, ex.toString());
			return false;
		}
		return true;
	}

	/**
	 * 按照传入的并发模块获取所有并发控制组
	 * 
	 * @param tLockModule
	 * @return
	 */
	private LockConfigSet getLockConfig(Connection conn, String tLockModule) {
		LockConfigSet tLockConfigSet = new LockConfigSet();
		LockConfigDB tLockConfigDB = new LockConfigDB(conn);
		// 为了降低死锁出现的可能性,查询配置表时按照并发控制组排序
		String tSQL_LockModule = "select * from lockconfig where lockmodule= '"
				+ "?tLockModule1?" + "' order by lockgroup ";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(tSQL_LockModule);
		sqlbv1.put("tLockModule1", tLockModule);
		tLockConfigSet = tLockConfigDB.executeQuery(sqlbv1);
		return tLockConfigSet;
	}

	/**
	 * 解锁操作
	 * 
	 * @return
	 */
	public boolean unLock() {

		con = DBConnPool.getConnection();
		if (con == null) {
			// @@错误处理
			CError.buildErr(this, "数据库连接失败!");
			return false;
		}
		// 设置为非自动提交

		try {
			con.setAutoCommit(false);
			if (!pareUnLockData(con)) {
				con.rollback();
				con.close();
				return false;
			}
			con.commit();
			con.close();
		} catch (Exception ex) {
			CError.buildErr(this, ex.toString());
			try {
				// 如果发生异常以后，关闭连接
				if (con != null) {
					con.rollback();
					con.close();
				}
			} catch (Exception e) {
			}
			return false;
		}
		return true;

	}

	/**
	 * 锁定业务号
	 * 
	 * @param tOperatedNo
	 *            业务号
	 * @param tLockModule
	 *            模块名
	 * @return
	 */
	public boolean lock(String tOperatedNo, String tLockModule) {
		try {
			con = DBConnPool.getConnection();
			if (con == null) {
				// @@错误处理
				CError.buildErr(this, "数据库连接失败!");
				return false;
			}
			// 设置为非自动提交
			con.setAutoCommit(false);
			if (!pareLockData(con, tOperatedNo, tLockModule)) {
				con.rollback();
				con.close();
				return false;
			}
			con.commit();
			con.close();
		} catch (Exception ex) {
			CError.buildErr(this, ex.toString());
			try {
				// 如果发生异常以后，关闭连接
				if (con != null) {
					con.rollback();
					con.close();
				}
			} catch (Exception e) {
			}
			return false;
		}
		return true;
	}

	/**
	 * 锁定业务号
	 * 
	 * @param tOperatedNo
	 * @param tLockModule
	 * @param tOperator
	 * @return
	 */
	public boolean lock(String tOperatedNo, String tLockModule, String tOperator) {
		try {
			mOpeartor = tOperator;
			con = DBConnPool.getConnection();
			if (con == null) {
				// @@错误处理
				CError.buildErr(this, "数据库连接失败!");
				return false;
			}
			// 设置为非自动提交
			con.setAutoCommit(false);
			if (!pareLockData(con, tOperatedNo, tLockModule)) {
				con.rollback();
				con.close();
				return false;
			}
			con.commit();
			con.close();
		} catch (Exception ex) {
			CError.buildErr(this, ex.toString());
			try {
				// 如果发生异常以后，关闭连接
				if (con != null) {
					con.rollback();
					con.close();
				}
			} catch (Exception e) {
			}
			return false;
		}
		return true;
	}
	
	public boolean lock(List tOperatedNo, String tLockModule, String tOperator) {
		HashSet set=new HashSet();
		set.addAll(tOperatedNo);
		return lock(set, tLockModule, tOperator);
	}
	
	public boolean lock(HashSet tOperatedNo, String tLockModule, String tOperator) {
		try {
			mOpeartor = tOperator;
			con = DBConnPool.getConnection();
			if (con == null) {
				// @@错误处理
				CError.buildErr(this, "数据库连接失败!");
				return false;
			}
			// 设置为非自动提交
			con.setAutoCommit(false);
			for (Iterator iterator = tOperatedNo.iterator(); iterator.hasNext();) {
				String str = (String) iterator.next();
				if (!pareLockData(con, str, tLockModule)) {
					con.rollback();
					con.close();
					return false;
				}
			}
			con.commit();
			con.close();
		} catch (Exception ex) {
			CError.buildErr(this, ex.toString());
			try {
				// 如果发生异常以后，关闭连接
				if (con != null) {
					con.rollback();
					con.close();
				}
			} catch (Exception e) {
			}
			return false;
		}
		return true;
	
	}
	
	/**
	 * 
	 * @param tOperatedNo
	 * @param tLockModule
	 * @param tOperator
	 * @return
	 */
	public boolean lock(String[] tOperatedNo, String tLockModule, String tOperator) {
		HashSet set=new HashSet();
		for (int i = 0; i < tOperatedNo.length; i++) {
			set.add(tOperatedNo[i]);
		}
		return lock(set, tLockModule, tOperator);
	}
	
	/**
	 * 手动解锁 主要是为了解决系统异常终止造成的死锁.
	 * 
	 * @param tOperatedNo
	 * @param tLockGroup
	 * @param tOperator
	 * @param tReason
	 * @return
	 */
	public boolean unLockManual(String tOperatedNo, String tLockGroup,
			String tOperator, String tReason) {
		try {
			mOpeartor = tOperator;
			// 获得连接
			con = DBConnPool.getConnection();
			if (con == null) {
				// @@错误处理
				CError.buildErr(this, "数据库连接失败!");
				return false;
			}
			// 设置为非自动提交
			con.setAutoCommit(false);
			// 开始解锁操作
			if (!prepareUnLockManual(con, tOperatedNo, tLockGroup, tOperator,
					tReason)) {
				con.rollback();
				con.close();
			}
			con.commit();
			con.close();
		} catch (Exception ex) {
			CError.buildErr(this, ex.toString());
			try {
				// 如果发生异常以后，关闭连接
				if (con != null) {
					con.rollback();
					con.close();
				}
			} catch (Exception e) {
			}
			return false;
		}
		return true;

	}

	/**
	 * 手工解锁业务处理
	 * 
	 * @param tOperatedNo
	 * @param tLockGroup
	 * @param tOperator
	 * @param tReason
	 * @return
	 */
	private boolean prepareUnLockManual(Connection conn, String tOperatedNo,
			String tLockGroup, String tOperator, String tReason) {
		try {
			// 1-查询是否有要锁定的锁表记录
			LockAppGroupDB tLockAppGroupDB = new LockAppGroupDB(conn);
			tLockAppGroupDB.setLockGroup(tLockGroup);
			tLockAppGroupDB.setOperatedNo(tOperatedNo);
			String tMakeDate = PubFun.getCurrentDate();
			String tMakeTime = PubFun.getCurrentTime();
			if (!tLockAppGroupDB.getInfo()) {
				// 如果查询无结果
				CError.buildErr(this, "没有需要解锁的数据!");
				return false;
			} else {
				String tLockSQL = " select * from LockAppGroup where OperatedNo = '"
						+ "?tOperatedNo?"
						+ "' and LockGroup='"
						+ "?tLockGroup?"
						+ "' "
						+ " for update nowait ";
				logger.debug("LockSQL: " + tLockSQL);
				SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
				sqlbv2.sql(tLockSQL);
				sqlbv2.put("tOperatedNo", tOperatedNo);
				sqlbv2.put("tLockGroup", tLockGroup);
				LockAppGroupDB tempLockAppGroupDB = new LockAppGroupDB(conn);
				LockAppGroupSet tempLockAppGroupSet = new LockAppGroupSet();
				LockAppGroupSchema tempLockAppGroupSchema = new LockAppGroupSchema();
				tempLockAppGroupSet = tempLockAppGroupDB.executeQuery(sqlbv2);
				if (tempLockAppGroupSet == null
						|| tempLockAppGroupSet.size() == 0) {
					CError.buildErr(this, "操作号：" + tOperatedNo
							+ "对表LockAppGroup中记录加锁失败!");
					return false;
				}
				// 因为是按照主键查询,所以只会有一条记录对应
				tempLockAppGroupSchema = tempLockAppGroupSet.get(1);
				// 备份删除日志
				UnLockLogSchema tBakUnLockLogSchema = new UnLockLogSchema();
				UnLockLogDB tBakUnLockLogDB = new UnLockLogDB(conn);
				String tIDX = PubFun1.CreateMaxNo("UNLOCKIDX", 20);
				tBakUnLockLogSchema.setIDX(tIDX);
				tBakUnLockLogSchema.setOperatedNo(tempLockAppGroupSchema
						.getOperatedNo());
				tBakUnLockLogSchema.setLockGroup(tempLockAppGroupSchema
						.getLockGroup());
				tBakUnLockLogSchema.setOperator(this.mOpeartor);

				tBakUnLockLogSchema.setLockDate(tempLockAppGroupSchema
						.getMakeDate());
				tBakUnLockLogSchema.setLockTime(tempLockAppGroupSchema
						.getMakeTime());
				tBakUnLockLogSchema.setUnLockDate(tMakeDate);
				tBakUnLockLogSchema.setUnLockTime(tMakeTime);
				tBakUnLockLogSchema.setUnLockReason(tReason);
				tBakUnLockLogSchema.setMakeDate(tMakeDate);
				tBakUnLockLogSchema.setMakeTime(tMakeTime);
				tBakUnLockLogSchema.setModifyDate(tMakeDate);
				tBakUnLockLogSchema.setModifyTime(tMakeTime);
				tBakUnLockLogDB.setSchema(tBakUnLockLogSchema);
				if (!tBakUnLockLogDB.insert()) {
					CError.buildErr(this, "操作号：" + tOperatedNo + "备份数据失败!");
					return false;
				}
				// 删除解锁数据
				tempLockAppGroupDB.setSchema(tempLockAppGroupSchema);

				if (!tempLockAppGroupDB.delete()) {
					CError.buildErr(this, "操作号：" + tOperatedNo + "删除数据失败!");
					return false;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * 查询所有"超时"的锁表数据
	 * 
	 * @return LockAppGroupSet
	 */
	public static LockAppGroupSet getAllTimeOutLockData() {
		LockAppGroupSet tLockAppGroupSet = new LockAppGroupSet();
		LockAppGroupDB tLockAppGroupDB = new LockAppGroupDB();
		String tCurrDate = PubFun.getCurrentDate();
		String tCurrTime = PubFun.getCurrentTime();
		int tDefTimeOut = 0;
		String tSQL = " select sysvarvalue from ldsysvar where sysvar='MAXLOCKTIME' ";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(tSQL);
		ExeSQL tExeSQL = new ExeSQL();
		String tResult = tExeSQL.getOneValue(sqlbv2);
		if (tResult == null || tResult.equals("")) {
			// 如果没有描述设成无限大
			tDefTimeOut = 999999999;
		} else {
			tDefTimeOut = Integer.parseInt(tResult);
		}
		logger.debug("tDefTimeOut:" + tDefTimeOut);

		String tSQL_Lock = "select * from LockAppGroup where makedate<'"
				+ "?tCurrDate1?"
				+ "' "
				// 在当天之前锁定的认为是死锁
				+ " union " + " select * from LockAppGroup where makedate='"
				+ "?tCurrDate1?" + "' " + " and changeTime('','" + "?tCurrTime?"
				+ "')-changeTime('',maketime)>" + "?tDefTimeOut?" + " ";
		logger.debug("tSQL_Lock:" + tSQL_Lock);
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(tSQL_Lock);
		sqlbv3.put("tCurrDate", tCurrDate);
		sqlbv3.put("tCurrDate1", tCurrDate);
		sqlbv3.put("tCurrTime", tCurrTime);
		sqlbv3.put("tDefTimeOut", tDefTimeOut);
		tLockAppGroupSet = tLockAppGroupDB.executeQuery(sqlbv3);
		return tLockAppGroupSet;

	}
	
	/**
	 * 解锁操作
	 * 
	 * @return
	 */
	public boolean unLockJSP(String tOperatedNo,String tLockModule) {

		con = DBConnPool.getConnection();
		if (con == null) {
			// @@错误处理
			CError.buildErr(this, "数据库连接失败!");
			return false;
		}
		// 设置为非自动提交

		try {
			con.setAutoCommit(false);
			if (!pareUnLockData(con,tOperatedNo,tLockModule)) {
				//TODO
				con.rollback();
				con.close();
				return false;
			}
			con.commit();
			con.close();
		} catch (Exception ex) {
			CError.buildErr(this, ex.toString());
			try {
				// 如果发生异常以后，关闭连接
				if (con != null) {
					con.rollback();
					con.close();
				}
			} catch (Exception e) {
			}
			return false;
		}
		return true;

	}

	/**
	 * 查询所有锁表数据
	 * 
	 * @return SSRS
	 */
	public static String getAllLockData() {
		String tString = "";
		String tCurrDate = PubFun.getCurrentDate();
		String tCurrTime = PubFun.getCurrentTime();
		// 业务号,并发组,锁定日期,锁定时间,已锁时间
		String tSQL = "";
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			tSQL = "select operatedno,lockgroup,makedate,maketime,datediff(to_date('"
					+ "?tCurrDate?"
					+ "','yyyy-mm-dd'),to_date(to_char(makedate,'yyyy-mm-dd'),'yyyy-mm-dd')),changeTime('','"
					+ "?tCurrTime?" + "')-changeTime('',maketime) "
					+ " from LockAppGroup ";
		}else if (SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			tSQL = "select operatedno,lockgroup,makedate,maketime,datediff(to_date('"
					+ "?tCurrDate?"
					+ "','yyyy-mm-dd'),to_date(to_char(makedate,'yyyy-mm-dd'),'yyyy-mm-dd')),changeTime('','"
					+ "?tCurrTime?" + "')-changeTime('',maketime) "
					+ " from LockAppGroup ";
		}
		
		logger.debug("tSQL_SSRS:" + tSQL);
		SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
		sqlbv4.sql(tSQL);
		sqlbv4.put("tCurrDate", tCurrDate);
		sqlbv4.put("tCurrTime", tCurrTime);
		tString = (new ExeSQL()).getEncodedResult(sqlbv4);
		return tString;
	}
	
	
	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		boolean operFlag = false;
		TransferData mTransferData = (TransferData) cInputData.getObjectByObjectName("TransferData", 0);
		
		//通过cOperate 获取要调用的方法
		if("lock3String".equalsIgnoreCase(cOperate)){
			//准备传递参数
			String tOperatedNo=(String)mTransferData.getValueByName("OperatedNo");
			String tLockModule=(String)mTransferData.getValueByName("LockModule");
			String tOperator=(String)mTransferData.getValueByName("Operator");
			
			operFlag = this.lock(tOperatedNo, tLockModule, tOperator);
			
		}else if("unLock".equalsIgnoreCase(cOperate)){
			operFlag = this.unLock();
		}else if("unLockJSP".equalsIgnoreCase(cOperate)){
			String tOperatedNo=(String)mTransferData.getValueByName("OperatedNo");
			String tLockModule=(String)mTransferData.getValueByName("LockModule");
			if(tOperatedNo == null || tOperatedNo.equals("")||tLockModule==null ||tLockModule.equals("")){
				CError.buildErr(this, "需解锁的业务号码传输不完整！");
				return false;
			}
			operFlag = this.unLockJSP(tOperatedNo,tLockModule);
		}
		
		if(operFlag){
			return true;
		}else{
			return false;
		}
	}

	public static void main(String[] args) {
		try {
			PubConcurrencyLock.getAllLockData();
			PubConcurrencyLock tPubConcurrencyLock = new PubConcurrencyLock();
			String tModule = "LC0002";
			String tNo = "001";
			// tPubConcurrencyLock.lock(tNo, tModule);
			// tPubConcurrencyLock.lock("002", tModule);
			tPubConcurrencyLock.unLockManual("001", "0000000002", "001",
					"手工解锁测试");
			tPubConcurrencyLock.unLockManual("001", "0000000001", "001",
					"手工解锁测试");
			// tPubConcurrencyLock.unLock();
		} catch (Exception ex) {

		}

		logger.debug("111");
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return this.mErrors;
	}

	public VData getResult() {
		// TODO Auto-generated method stub
		return null;
	}
}
