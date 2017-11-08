package com.sinosoft.lis.bq;
import java.util.ArrayList;

import org.apache.log4j.Logger;

//import antlr.collections.List;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LPEdorMainDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPEdorMainSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 团体保全集体下个人功能类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Minim
 * @version 1.0
 */
public class GEdorRiskBL {
private static Logger logger = Logger.getLogger(GEdorRiskBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	LCGrpPolSet mLCGrpPolSet = new LCGrpPolSet();
	private LPEdorMainSet mLPEdorMainSet = new LPEdorMainSet();
	private LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet();
	private LPEdorMainSet saveLPEdorMainSet = new LPEdorMainSet();
	private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private Reflections ref = new Reflections();
	private String currDate = PubFun.getCurrentDate();
	private String currTime = PubFun.getCurrentTime();
	private MMap map = new MMap();

	public GEdorRiskBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据
		if (!getInputData())
			return false;
		logger.debug("---End getInputData---");

		// 数据校验操作
		if (!checkData())
			return false;
		logger.debug("---End checkData---");

		// 数据准备操作
		if (mOperate.equals("INSERT||EDORRISK")) {
			if (!prepareData())
				return false;
			logger.debug("---End prepareData---");
		}
		// 数据准备操作
		if (mOperate.equals("DELETE||EDORRISK")) {
			if (!deleteData())
				return false;
			logger.debug("---End prepareData---");
		}

		// 数据提交、保存

		PubSubmit tPubSubmit = new PubSubmit();
		logger.debug("Start tPRnewManualDunBLS Submit...");

		if (!tPubSubmit.submitData(mResult, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);

			CError tError = new CError();
			tError.moduleName = "ContBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";

			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			mLCGrpPolSet = (LCGrpPolSet) mInputData.getObjectByObjectName(
					"LCGrpPolSet", 0);
			mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) mInputData
					.getObjectByObjectName("LPGrpEdorItemSchema", 0);

			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GEdorRiskBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 校验传入的数据的合法性
	 * 
	 * @return
	 */
	private boolean checkData() {
		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		tLPGrpEdorItemDB.setSchema(mLPGrpEdorItemSchema);
		if (!tLPGrpEdorItemDB.getInfo()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PInsuredBL";
			tError.functionName = "Preparedata";
			tError.errorMessage = "无保全申请数据!";
			logger.debug("------" + tError);
			this.mErrors.addOneError(tError);
			return false;
		}

		// 将查询出来的团体保全主表数据保存至模块变量中，省去其它的重复查询
		mLPGrpEdorItemSchema.setSchema(tLPGrpEdorItemDB.getSchema());

		if (!tLPGrpEdorItemDB.getEdorState().trim().equals("1")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PInsuredBL";
			tError.functionName = "Preparedata";
			tError.errorMessage = "该保全已经申请确认不能修改!";
			logger.debug("------" + tError);
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return
	 */
	private boolean prepareData() {

		String grpPolNoStr = "";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		ArrayList<String> arrStr=new ArrayList<String>();
		// 按个人保全主表进行处理
		for (int i = 1; i <= mLCGrpPolSet.size(); i++) {
			arrStr.add(mLCGrpPolSet.get(i).getGrpPolNo());			
//  初始			
//			if (i != mLCGrpPolSet.size()) {
//				grpPolNoStr = grpPolNoStr + "'"
//						+ mLCGrpPolSet.get(i).getGrpPolNo() + "',";
//			} else
//				grpPolNoStr = grpPolNoStr + "'"
//						+ mLCGrpPolSet.get(i).getGrpPolNo() + "'";		
			
//   zhangyingfeng  使用追加拼接的绑定变量	2016-03-14		
//			if (i != mLCGrpPolSet.size()) {
//				grpPolNoStr = grpPolNoStr + "'?grpPolNoStr"+i+"?',";
//				sqlbv.put("grpPolNoStr"+i, mLCGrpPolSet.get(i).getGrpPolNo());
//			} else{
//				grpPolNoStr = grpPolNoStr + "'?grpPolNoStr"+i+"?'";
//				sqlbv.put("grpPolNoStr"+i, mLCGrpPolSet.get(i).getGrpPolNo());
//			}
			
			
		}
		// 查出个人保单集合，对应每一个个人合同，需要建立LpEdorMain
		String sql = "Select * from lccont where contno in "
				+ "(select contno from lcpol where GrpPolNo in (?grpPolNoStr?))";
		sqlbv.sql(sql);
		sqlbv.put("grpPolNoStr", arrStr);
		logger.debug(sql);
		LCContSet tLCContSet = new LCContSet();
		LCContDB tLCContDB = new LCContDB();
		tLCContSet = tLCContDB.executeQuery(sqlbv);
		if (tLCContDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询个人保单失败!");
			return false;
		}
		for (int j = 1; j <= tLCContSet.size(); j++) {
			LPEdorMainSchema tLPEdorMainSchema = new LPEdorMainSchema();
			LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
			tLPEdorMainDB.setContNo(tLCContSet.get(j).getContNo());
			tLPEdorMainDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
			LPEdorMainSet tLPEdorMainSet = new LPEdorMainSet();
			tLPEdorMainSet = tLPEdorMainDB.query();
			if (tLPEdorMainDB.mErrors.needDealError()) {
				CError.buildErr(this, "查询个人保全主表失败!");
				return false;
			}
			if (tLPEdorMainSet.size() == 0) {
				ref.transFields(tLPEdorMainSchema, mLPGrpEdorItemSchema);
				tLPEdorMainSchema.setContNo(tLCContSet.get(j).getContNo());
				tLPEdorMainSchema.setManageCom(mGlobalInput.ManageCom);
				tLPEdorMainSchema.setOperator(mGlobalInput.Operator);
				tLPEdorMainSchema.setUWState("0");
				tLPEdorMainSchema.setMakeDate(currDate);
				tLPEdorMainSchema.setMakeTime(currTime);
				tLPEdorMainSchema.setModifyDate(currDate);
				tLPEdorMainSchema.setModifyTime(currTime);
				saveLPEdorMainSet.add(tLPEdorMainSchema);
			}
		}
		// 查出个人险种保单集合，对应每一个个人险种，需要建立LPEdorItem
		sql = "select * from lcpol where GrpPolNo in (?grpPolNoStr?)";
		sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("grpPolNoStr", arrStr);
		LCPolSet tLCPolSet = new LCPolSet();
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolSet = tLCPolDB.executeQuery(sqlbv);
		if (tLCPolDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询个人险种保单失败!");
			return false;
		}
		for (int j = 1; j <= tLCPolSet.size(); j++) {
			LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();

			ref.transFields(tLPEdorItemSchema, mLPGrpEdorItemSchema);
			tLPEdorItemSchema.setContNo(tLCPolSet.get(j).getContNo());
			tLPEdorItemSchema.setInsuredNo(tLCPolSet.get(j).getInsuredNo());
			tLPEdorItemSchema.setPolNo(tLCPolSet.get(j).getPolNo());
			tLPEdorItemSchema.setManageCom(mGlobalInput.ManageCom);
			tLPEdorItemSchema.setOperator(mGlobalInput.Operator);
			tLPEdorItemSchema.setUWFlag("0");
			tLPEdorItemSchema.setMakeDate(currDate);
			tLPEdorItemSchema.setMakeTime(currTime);
			tLPEdorItemSchema.setModifyDate(currDate);
			tLPEdorItemSchema.setModifyTime(currTime);
			mLPEdorItemSet.add(tLPEdorItemSchema);

		}

		map.put(mLPEdorItemSet, "INSERT");
		map.put(saveLPEdorMainSet, "INSERT");
		mResult.clear();
		mResult.add(map);

		return true;
	}

	/**
	 * 准备需要删除的数据
	 * 
	 * @return
	 */
	private boolean deleteData() {

		String grpPolNoStr = "";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		ArrayList<String> arrStr=new ArrayList<String>();
		// 按个人保全主表进行处理
		for (int i = 1; i <= mLCGrpPolSet.size(); i++) {
			arrStr.add(mLCGrpPolSet.get(i).getGrpPolNo());
//  初始		
//			if (i != mLCGrpPolSet.size()) {
//				grpPolNoStr = grpPolNoStr + "'"
//						+ mLCGrpPolSet.get(i).getGrpPolNo() + "',";
//			} else
//				grpPolNoStr = grpPolNoStr + "'"
//						+ mLCGrpPolSet.get(i).getGrpPolNo() + "'";

//   zhangyingfeng  使用追加拼接的绑定变量	2016-03-14
//			if (i != mLCGrpPolSet.size()) {
//				grpPolNoStr = grpPolNoStr + "'?grpPolNoStr"+i+"?',";
//				sqlbv.put("grpPolNoStr"+i, mLCGrpPolSet.get(i).getGrpPolNo());
//			} else{
//				grpPolNoStr = grpPolNoStr + "'?grpPolNoStr"+i+"?'";
//			    sqlbv.put("grpPolNoStr"+i, mLCGrpPolSet.get(i).getGrpPolNo());
//			}
		}
		// 删除个人批改项目
		String sql = "delete from LPEdorItem where edorno='"
				+ "?edorno?" + "' and edortype='"
				+ "?edortype?" + "' and polno in "
				+ "(select polno from lcpol where GrpPolNo in (?grpPolNoStr?))";
		sqlbv.sql(sql);
		sqlbv.put("edorno", mLPGrpEdorItemSchema.getEdorNo());
		sqlbv.put("edortype", mLPGrpEdorItemSchema.getEdorType());
		sqlbv.put("grpPolNoStr", arrStr);
		map.put(sqlbv, "UPDATE");
		// 当个人批单主表没有批改项目时需要删掉个人批改主表
		String sql1 = "delete from LPEdorMain where LPEdorMain.edorno='"
				+ "?edorno?"
				+ "' and LPEdorMain.contno in "
				+ "(select contno from lcpol where GrpPolNo in (?grpPolNoStr?)) and 0=(select count(1) from lpedoritem b where b.contno=LPEdorMain.contno "
				+ "and b.edorNo='" + "?edorno?" + "') ";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(sql1);
		sqlbv1.put("edorno", mLPGrpEdorItemSchema.getEdorNo());
		sqlbv1.put("grpPolNoStr", arrStr);
		map.put(sqlbv1, "UPDATE");
		mResult.clear();
		mResult.add(map);

		return true;
	}

}
