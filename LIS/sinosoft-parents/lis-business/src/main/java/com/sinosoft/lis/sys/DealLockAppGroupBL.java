package com.sinosoft.lis.sys;
import org.apache.log4j.Logger;

import java.util.Hashtable;

import com.sinosoft.lis.db.LockBaseDB;
import com.sinosoft.lis.db.LockConfigDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LockBaseSchema;
import com.sinosoft.lis.schema.LockConfigSchema;
import com.sinosoft.lis.schema.LockGroupSchema;
import com.sinosoft.lis.vschema.LockAppGroupSet;
import com.sinosoft.lis.vschema.LockBaseSet;
import com.sinosoft.lis.vschema.LockConfigSet;
import com.sinosoft.lis.vschema.LockGroupSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;
/**
 * <p>
 * Title:公共并发控制程序 - 模块配置,并发组配置,解锁功能
 * </p>
 * <p>
 * Description:
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

public class DealLockAppGroupBL implements BusinessService{
private static Logger logger = Logger.getLogger(DealLockAppGroupBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	
	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private VData mData = new VData();
	private MMap map = new MMap();
	/** 数据操作字符串 */
	private String mOperate;

	/** 业务处理相关变量 */
	public DealLockAppGroupBL() {
	}

	public static void main(String[] args) {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		this.mInputData = cInputData;
		mGlobalInput = (GlobalInput) cInputData.get(1);
		// 按照操作符分支
		if (mOperate.equals("UnLockManual")) {
			// 手工解锁功能
			if (!dealUnLock()) {
				return false;
			}
		} else if (mOperate.equals("UnlLockAuto")) {
			// 自动解锁功能
		} else if (mOperate.equals("addNewLockBase")) {
			// 增加新模块
			if (!dealAddNewLockBase()) {
				return false;
			}
		} else if (mOperate.equals("AddLockGroup")) {
			// 增加新并发控制组
			if (!addNewLockGroup()) {
				return false;
			}
		} else if (mOperate.equals("DeleteLockGroup")) {
			// 删除并发控制组
			if (!deleteLockGroup()) {
				return false;
			}
		} else if (mOperate.equals("SaveLockGroupConfig")) {
			// 配置并发控制组
			if (!saveLockGroupConfig()) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 保存并发控制组配置
	 * 
	 * @return
	 */
	private boolean saveLockGroupConfig() {
		String currDate = PubFun.getCurrentDate();
		String currTime = PubFun.getCurrentTime();
		String tOperator = mGlobalInput.Operator;
		LockConfigSet tLockConfigSet = new LockConfigSet();
		LockConfigSet fLockConfigSet = new LockConfigSet();
		Hashtable tKeyTable = new Hashtable();
		tLockConfigSet = (LockConfigSet) mInputData.get(5);

		for (int i = 1; i <= tLockConfigSet.size(); i++) {
			LockConfigSchema tLockConfigSchema = new LockConfigSchema();
			tLockConfigSchema.setSchema(tLockConfigSet.get(i));
			if (tKeyTable.containsKey(tLockConfigSchema.getLockModule())) {
				CError.buildErr(this, "同一个并发控制组中不允许输入重复的控制模块!");
				return false;
			} else {
				tKeyTable.put(tLockConfigSchema.getLockModule(),
						tLockConfigSchema.getLockModule());
			}
			LockConfigDB tLockConfigDB = new LockConfigDB();
			tLockConfigDB.setSchema(tLockConfigSchema);
			if (tLockConfigDB.getInfo()) {
				// 如果查询有值,那么只修改操作员和修改时间
				tLockConfigSchema.setSchema(tLockConfigDB.getSchema());
				tLockConfigSchema.setOperator(tOperator);
				tLockConfigSchema.setModifyDate(currDate);
				tLockConfigSchema.setModifyTime(currTime);
			} else {
				// 查询没有值,全部重新增加
				tLockConfigSchema.setOperator(tOperator);
				tLockConfigSchema.setModifyDate(currDate);
				tLockConfigSchema.setModifyTime(currTime);
				tLockConfigSchema.setMakeDate(currDate);
				tLockConfigSchema.setMakeTime(currTime);
			}
			fLockConfigSet.add(tLockConfigSchema);
		}
		if (fLockConfigSet.size() > 0) {
			String tGroupID = fLockConfigSet.get(1).getLockGroup();
			if (tGroupID == null || tGroupID.equals("")) {
				CError.buildErr(this, "并发控制组编码为空!");
				return false;
			}
			String tSQL = "delete from LockConfig where lockgroup='" + "?tGroupID?"
					+ "' ";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSQL);
			sqlbv.put("tGroupID", tGroupID);
			map.put(sqlbv, "DELETE");
			map.put(fLockConfigSet, "INSERT");
			return toSaveData();
		}
		return true;
	}

	/**
	 * 删除并发控制组
	 * 
	 * @return
	 */
	private boolean deleteLockGroup() {
		String currDate = PubFun.getCurrentDate();
		String currTime = PubFun.getCurrentTime();
		String tOperator = mGlobalInput.Operator;
		map = new MMap();
		LockGroupSet tLockGroupSet = new LockGroupSet();
		LockGroupSet fLockGroupSet = new LockGroupSet();
		LockConfigSet fLockConfigSet = new LockConfigSet();
		tLockGroupSet = (LockGroupSet) mInputData.get(4);
		for (int i = 1; i <= tLockGroupSet.size(); i++) {
			LockGroupSchema tLockGroupSchema = new LockGroupSchema();
			tLockGroupSchema.setSchema(tLockGroupSet.get(i));
			tLockGroupSchema.setOperator(tOperator);
			tLockGroupSchema.setMakeDate(currDate);
			tLockGroupSchema.setMakeTime(currTime);
			tLockGroupSchema.setModifyDate(currDate);
			tLockGroupSchema.setModifyTime(currTime);
			fLockGroupSet.add(tLockGroupSchema);

			// 查询所有LockConfig 记录,同时删除
			String tSQL = "select * from lockconfig where lockgroup='"
					+ "?lockgroup?" + "'";
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(tSQL);
			sqlbv1.put("lockgroup", tLockGroupSchema.getLockGroup());
			LockConfigSet tLockConfigSet = new LockConfigSet();
			LockConfigDB tLockConfigDB = new LockConfigDB();
			tLockConfigSet = tLockConfigDB.executeQuery(sqlbv1);
			if (tLockConfigSet.size() >= 1) {
				fLockConfigSet.add(tLockConfigSet);
			}
		}
		if (fLockGroupSet.size() > 0) {
			map.put(fLockGroupSet, "DELETE");
			if (fLockConfigSet.size() > 0) {
				map.put(fLockConfigSet, "DELETE");
			}
			return toSaveData();
		}
		return true;
	}

	/**
	 * 增加新的并发控制组
	 * 
	 * @return
	 */
	private boolean addNewLockGroup() {
		String currDate = PubFun.getCurrentDate();
		String currTime = PubFun.getCurrentTime();
		String tOperator = mGlobalInput.Operator;
		map = new MMap();
		LockGroupSet tLockGroupSet = new LockGroupSet();
		LockGroupSet fLockGroupSet = new LockGroupSet();
		tLockGroupSet = (LockGroupSet) mInputData.get(4);
		for (int i = 1; i <= tLockGroupSet.size(); i++) {
			LockGroupSchema tLockGroupSchema = new LockGroupSchema();
			tLockGroupSchema.setSchema(tLockGroupSet.get(i));
			String tGroupID = PubFun1.CreateMaxNo("LOCKGROUPID", 10);
			tLockGroupSchema.setLockGroup(tGroupID);
			tLockGroupSchema.setOperator(tOperator);
			tLockGroupSchema.setMakeDate(currDate);
			tLockGroupSchema.setMakeTime(currTime);
			tLockGroupSchema.setModifyDate(currDate);
			tLockGroupSchema.setModifyTime(currTime);
			fLockGroupSet.add(tLockGroupSchema);
		}
		if (fLockGroupSet.size() > 0) {
			map.put(fLockGroupSet, "INSERT");
			return toSaveData();
		}
		return true;
	}

	/**
	 * 处理手工解锁
	 * 
	 * @return
	 */
	private boolean dealUnLock() {
		String tOperator = mGlobalInput.Operator;
		String tReason = (String) mInputData.get(0);
		if (tReason == null || tReason.trim().equals("")) {
			CError.buildErr(this, "手工解锁需要输入解锁原因!");
			return false;
		}
		LockAppGroupSet tLockAppGroupSet = new LockAppGroupSet();
		tLockAppGroupSet = (LockAppGroupSet) mInputData.get(2);
		for (int i = 1; i <= tLockAppGroupSet.size(); i++) {
			PubConcurrencyLock tPubConcurrencyLock = new PubConcurrencyLock();
			if (!tPubConcurrencyLock.unLockManual(tLockAppGroupSet.get(i)
					.getOperatedNo(), tLockAppGroupSet.get(i).getLockGroup(),
					tOperator, tReason)) {
				CError tError = new CError();
				tError.moduleName = "PubLock";
				tError.functionName = "lock";
				tError.errorMessage = tLockAppGroupSet.get(i).getOperatedNo()
						+ "解锁失败";
				this.mErrors.addOneError(tError);
				return false;
			}
		}
		return true;
	}

	/**
	 * 新增模块
	 * 
	 * @return
	 */
	private boolean dealAddNewLockBase() {
		String currDate = PubFun.getCurrentDate();
		String currTime = PubFun.getCurrentTime();
		String tOperator = mGlobalInput.Operator;
		LockBaseSet tLockBaseSet = new LockBaseSet();
		tLockBaseSet = (LockBaseSet) mInputData.get(3);
		map = new MMap();
		for (int i = 1; i <= tLockBaseSet.size(); i++) {
			LockBaseSchema tLockBaseSchema = new LockBaseSchema();
			tLockBaseSchema.setSchema(tLockBaseSet.get(i));
			LockBaseDB tLockBaseDB = new LockBaseDB();
			tLockBaseDB.setSchema(tLockBaseSchema);
			// 判断是否有重复编码
			if (tLockBaseDB.getInfo()) {
				CError.buildErr(this, "模块编码已存在!");
				return false;
			}
			tLockBaseSchema.setOperator(tOperator);
			tLockBaseSchema.setMakeDate(currDate);
			tLockBaseSchema.setMakeTime(currTime);
			tLockBaseSchema.setModifyDate(currDate);
			tLockBaseSchema.setModifyTime(currTime);
			map.put(tLockBaseSchema, "INSERT");
		}
		return toSaveData();
	}

	/**
	 * 统一保存数据
	 * 
	 * @return
	 */
	private boolean toSaveData() {
		mData.clear();
		mData.add(map);

		PubSubmit tPubSubmit = new PubSubmit();

		if (!tPubSubmit.submitData(mData, "")) {
			mErrors.addOneError(new CError("数据保存出错！"));
			logger.debug("数据保存出错！");
			return false;
		}
		return true;
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

	public VData getResult() {
		// TODO Auto-generated method stub
		return mResult;
	}
}
