package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LPGrpEdorMainBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPGrpEdorMainSchema;
import com.sinosoft.lis.vschema.LPGrpEdorMainSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 承保暂交费业务逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author HST
 * @version 1.0
 */
public class PGrpEdorMainBL {
private static Logger logger = Logger.getLogger(PGrpEdorMainBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 判断条件 */
	private String mEdorType;

	/**  */
	// 保全申请主表
	private LPEdorAppSchema mLPEdorAppSchema = new LPEdorAppSchema();
	// 团险保全批改表集合
	private LPGrpEdorMainSet mLPGrpEdorMainSet = new LPGrpEdorMainSet();

	// private LPGrpEdorMainBL mLPGrpEdorMainBL = new LPGrpEdorMainBL();
	// private LPGrpEdorMainBLSet mLPGrpEdorMainBLSet = new
	// LPGrpEdorMainBLSet();
	// private LCGrpPolBL mLCGrpPolBL = new LCGrpPolBL();
	// private LCGrpPolBLSet mLCGrpPolBLSet = new LCGrpPolBLSet();
	// private LPGrpPolBL mLPGrpPolBL = new LPGrpPolBL();
	// private LPGrpPolBLSet mLPGrpPolBLSet = new LPGrpPolBLSet();
	// private LCPolSchema mLCPolSchema = new LCPolSchema();
	// private LPEdorMainSchema mLPEdorMainSchema = new LPEdorMainSchema();
	// private LCPolSet mLCPolSet = new LCPolSet();
	// private LPGrpEdorMainSchema aLPGrpEdorMainSchema = new
	// LPGrpEdorMainSchema();
	// private LPEdorMainSet mLPEdorMainSet = new LPEdorMainSet();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private MMap map = new MMap();
	// 统一更新日期，时间
	private String theCurrentDate = PubFun.getCurrentDate();
	private String theCurrentTime = PubFun.getCurrentTime();

	public PGrpEdorMainBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData))
			return false;

		// 校验传入的是否合法
		if (!checkData())
			return false;

		// 数据操作业务处理
		if (cOperate.equals("INSERT||EDOR")) {
			if (!dealData())
				return false;
			logger.debug("---dealData---");
		}
		if (cOperate.equals("UPDATE||EDOR")) {
			if (!updateData())
				return false;
			logger.debug("---updateData---");
		}
		if (cOperate.equals("DELETE||EDOR")) {
			if (!deleteData())
				return false;
			logger.debug("----deleteData---");
		}
		// 准备给后台的数据
		if (prepareOutputData() == false) { // 错误处理
			return false;
		}
		PubSubmit tPubSubmit = new PubSubmit();

		if (!tPubSubmit.submitData(mInputData, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);

			CError tError = new CError();
			tError.moduleName = "PGrpEdorMainBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";

			this.mErrors.addOneError(tError);
			return false;
		}

		mInputData = null;
		return true;

	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {

		logger.debug("End prepareOutputData...");
		LPGrpEdorMainBL tLPGrpEdorMainBL = new LPGrpEdorMainBL();
		// int m;
		// 准备个人保单（保全）的信息

		// int n = mLPGrpEdorMainBLSet.size();
		if (mLPEdorAppSchema.getEdorAcceptNo() == null
				|| mLPEdorAppSchema.getEdorAcceptNo().equals("")) {
			String strLimit = PubFun
					.getNoLimit(mLPEdorAppSchema.getManageCom());
			String strEdorAcceptNo = PubFun1.CreateMaxNo("EdorAcceptNo",
					strLimit);
			if (StrTool.compareString(strEdorAcceptNo, "")) {
				CError.buildErr(this, "生成保全申请号错误！");
				return false;
			} else {
				mLPEdorAppSchema.setEdorAcceptNo(strEdorAcceptNo);
			}
		}
		mLPEdorAppSchema.setEdorState("1");
		mLPEdorAppSchema.setEdorConfNo(mLPEdorAppSchema.getEdorAcceptNo());
		mLPEdorAppSchema.setOperator(mGlobalInput.Operator);
		mLPEdorAppSchema.setMakeDate(theCurrentDate);
		mLPEdorAppSchema.setMakeTime(theCurrentTime);
		mLPEdorAppSchema.setModifyDate(theCurrentDate);
		mLPEdorAppSchema.setModifyTime(theCurrentTime);

		int n = mLPGrpEdorMainSet.size();
		for (int i = 1; i <= n; i++) {
			LPGrpEdorMainSchema tLPGrpEdorMainSchema = mLPGrpEdorMainSet.get(i);
			tLPGrpEdorMainSchema.setEdorAcceptNo(mLPEdorAppSchema
					.getEdorAcceptNo());
			if (tLPGrpEdorMainSchema.getEdorAppNo() == null
					|| tLPGrpEdorMainSchema.getEdorAppNo().equals("")) {
				String strLimit = PubFun.getNoLimit(tLPGrpEdorMainSchema
						.getManageCom());
				String strEdorAppNo = PubFun1.CreateMaxNo("EDORGRPAPPNO",
						strLimit);
				if (StrTool.compareString(strEdorAppNo, "")) {
					CError.buildErr(this, "生成保全申请号错误！");
					return false;
				} else {
					tLPGrpEdorMainSchema.setEdorAppNo(strEdorAppNo);
					tLPGrpEdorMainSchema.setEdorNo(strEdorAppNo);
				}
			}
			tLPGrpEdorMainSchema.setEdorState("1");
			tLPGrpEdorMainSchema.setUWState("0");
			tLPGrpEdorMainSchema.setOperator(mGlobalInput.Operator);
			tLPGrpEdorMainSchema.setMakeDate(theCurrentDate);
			tLPGrpEdorMainSchema.setMakeTime(theCurrentTime);
			tLPGrpEdorMainSchema.setModifyDate(theCurrentDate);
			tLPGrpEdorMainSchema.setModifyTime(theCurrentTime);
		}

		map.put(mLPGrpEdorMainSet, "INSERT");
		map.put(mLPEdorAppSchema, "INSERT");

		return true;

	}

	/**
	 * mResult.clear(); mLPGrpEdorMainBLSet=new LPGrpEdorMainBLSet(); VData
	 * tVData=new VData(); LPGrpEdorMainSet tLPGrpEdorMainSet=new
	 * LPGrpEdorMainSet(); tVData=tPGrpEdorMainBLS.getResult();
	 * tLPGrpEdorMainSet=(LPGrpEdorMainSet)tVData.getObjectByObjectName("LPGrpEdorMainBLSet",0);
	 * mLPGrpEdorMainBLSet.set(tLPGrpEdorMainSet);
	 * mResult.add(mLPGrpEdorMainBLSet); return true; }
	 */
	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));

		if (mOperate.equals("INSERT||EDOR") || mOperate.equals("UPDATE||EDOR")
				|| mOperate.equals("DELETE||EDOR")) {
			mLPGrpEdorMainSet.set((LPGrpEdorMainSet) cInputData
					.getObjectByObjectName("LPGrpEdorMainSet", 0));
			mLPEdorAppSchema.setSchema((LPEdorAppSchema) cInputData
					.getObjectByObjectName("LPEdorAppSchema", 0));
			// tLPGrpEdorMainSchema.setSchema(mLPGrpEdorMainBLSet.get(1));
		}

		return true;

	}

	private boolean updateData() {

		// LPGrpEdorMainDB tLPGrpEdorMainDB = new LPGrpEdorMainDB();
		// LPGrpEdorMainDB vLPGrpEdorMainDB = new LPGrpEdorMainDB();
		//
		// LPGrpEdorMainBL tLPGrpEdorMainBL = new LPGrpEdorMainBL();
		// LPGrpEdorMainSchema tLPGrpEdorMainSchema = new LPGrpEdorMainSchema();
		// LPGrpEdorMainSchema eLPGrpEdorMainSchema = new LPGrpEdorMainSchema();
		//
		// tLPGrpEdorMainSchema = mLPGrpEdorMainBLSet.get(1);
		// tLPGrpEdorMainBL.setSchema(tLPGrpEdorMainSchema);
		// tLPGrpEdorMainDB.setSchema(tLPGrpEdorMainSchema);
		// logger.debug("-----" + tLPGrpEdorMainSchema.getEdorAppDate());
		// logger.debug("-----" + tLPGrpEdorMainBL.getEdorAppDate());
		// logger.debug("-----" + tLPGrpEdorMainDB.getEdorAppDate());
		//
		// if (!tLPGrpEdorMainDB.getInfo())
		// {
		// // @@错误处理
		// this.mErrors.copyAllErrors(tLPGrpEdorMainDB.mErrors);
		// CError tError = new CError();
		// tError.moduleName = "tLPGrpEdorMainDB";
		// tError.functionName = "updateData";
		// tError.errorMessage = "查询保全信息失败!";
		// this.mErrors.addOneError(tError);
		// mLPGrpEdorMainBLSet.clear();
		// return false;
		// }
		// tLPGrpEdorMainSchema = tLPGrpEdorMainDB.getSchema();
		// mLPGrpEdorMainBL.setSchema(tLPGrpEdorMainSchema);
		// logger.debug("-----" + tLPGrpEdorMainSchema.getEdorAppDate());
		//
		// mLPGrpEdorMainBL.setEdorAppDate(tLPGrpEdorMainBL.getEdorAppDate());
		// mLPGrpEdorMainBL.setEdorValiDate(tLPGrpEdorMainBL.getEdorValiDate());
		// mLPGrpEdorMainBL.setUpdateFields();
		// tLPGrpEdorMainSchema = mLPGrpEdorMainBL.getSchema();
		//
		// if (mEdorType.equals("ZT") || mEdorType.equals("WT") ||
		// mEdorType.equals("GT"))
		// {
		// mInputData.clear();
		// mInputData.add(tLPGrpEdorMainSchema);
		// if (prepareData() == false)
		// { //错误处理
		// return false;
		// }
		// //数据提交
		// PGrpEdorMainBLS tPGrpEdorMainBLS = new PGrpEdorMainBLS();
		// logger.debug("Start LPGrpEdorMain BL Submit...");
		// logger.debug("Start LPGrpEdorMain BL Submit..." + mOperate);
		// if (!tPGrpEdorMainBLS.submitData(mInputData, mOperate))
		// {
		// // @@错误处理
		// this.mErrors.copyAllErrors(tPGrpEdorMainBLS.mErrors);
		// CError tError = new CError();
		// tError.moduleName = "PGrpEdorMainBL";
		// tError.functionName = "submitData";
		// tError.errorMessage = "数据提交失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		// }
		// else
		// {
		// tLPGrpEdorMainDB.setSchema(tLPGrpEdorMainSchema);
		// vLPGrpEdorMainDB.setSchema(tLPGrpEdorMainSchema);
		//
		// //mLPGrpEdorMainBL.setDefaultFields();
		// vLPGrpEdorMainDB.update();
		// if (vLPGrpEdorMainDB.mErrors.needDealError() == true)
		// {
		// // @@错误处理
		// this.mErrors.copyAllErrors(vLPGrpEdorMainDB.mErrors);
		// CError tError = new CError();
		// tError.moduleName = "LPGrpEdorMainBL";
		// tError.functionName = "updateData";
		// tError.errorMessage = "更新失败!";
		// this.mErrors.addOneError(tError);
		// mLPGrpEdorMainBLSet.clear();
		// return false;
		// }
		// }
		return true;

	}

	/**
	 * 只是撤销本次申请的某个保全项目
	 */
	private boolean deleteData() {
		// /**LPGrpEdorMainDB tLPGrpEdorMainDB = new LPGrpEdorMainDB();
		// tLPGrpEdorMainSchema = mLPGrpEdorMainBLSet.get(1);
		// if
		// (mEdorType.equals("ZT")||mEdorType.equals("WT")||mEdorType.equals("GT"))
		// {
		// mInputData.clear();
		// mInputData.add(tLPGrpEdorMainSchema);
		// if(prepareData()==false)
		// {//错误处理
		// return false;
		// }
		// //数据提交
		// PGrpEdorMainBLS tPGrpEdorMainBLS = new PGrpEdorMainBLS();
		// logger.debug("Start LPGrpEdorMain BL Submit...");
		// logger.debug("Start LPGrpEdorMain BL Submit..."+mOperate);
		// if (!tPGrpEdorMainBLS.submitData(mInputData,mOperate))
		// {
		// // @@错误处理
		// this.mErrors.copyAllErrors(tPGrpEdorMainBLS.mErrors);
		// CError tError = new CError();
		// tError.moduleName = "PGrpEdorMainBL";
		// tError.functionName = "submitData";
		// tError.errorMessage = "数据提交失败!";
		// this.mErrors .addOneError(tError) ;
		// return false;
		// }
		// }
		// else
		// {
		// tLPGrpEdorMainDB.setSchema(tLPGrpEdorMainSchema);
		//
		// if (!tLPGrpEdorMainDB.delete())
		// {
		// // @@错误处理
		// this.mErrors.copyAllErrors(tLPGrpEdorMainDB.mErrors);
		// CError tError = new CError();
		// tError.moduleName = "LPGrpEdorMainBL";
		// tError.functionName = "DeleteData";
		// tError.errorMessage = "删除失败!";
		// this.mErrors.addOneError(tError);
		// mLPGrpEdorMainBLSet.clear();
		// return false;
		// }
		// }
		// */
		// LPGrpEdorMainSchema tLPGrpEdorMainSchema = new LPGrpEdorMainSchema();
		// GrpEdorAppCancelBL tGrpEdorAppCancelBL = new GrpEdorAppCancelBL();
		// VData tInputData = new VData();
		// tLPGrpEdorMainSchema = mLPGrpEdorMainBLSet.get(1);
		//
		// logger.debug("edorNo" + tLPGrpEdorMainSchema.getEdorNo());
		// logger.debug("polNo" + tLPGrpEdorMainSchema.getGrpPolNo());
		// logger.debug("EdorType" + tLPGrpEdorMainSchema.getEdorType());
		//
		// tInputData.clear();
		// tInputData.addElement(tLPGrpEdorMainSchema);
		// logger.debug("---Operate" + mOperate);
		// if (!tGrpEdorAppCancelBL.submitData(tInputData, mOperate))
		// { // @@错误处理
		// this.mErrors.copyAllErrors(tGrpEdorAppCancelBL.mErrors);
		// return false;
		// }
		return true;
	}

	/**
	 * 校验传入的是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {

		// boolean flag = true;
		// LPEdorAppSchema tLPEdorAppSchema = new LPEdorAppSchema();
		// LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		// LPGrpEdorMainSet tLPGrpEdorMainSet = new LPGrpEdorMainSet();
		//
		// tLPGrpEdorMainSchema = mLPGrpEdorMainSet.get(1).getSchema();
		// tLPEdorAppDB.setGrpPolNo(tLPGrpEdorMainSchema.getGrpPolNo());
		//
		// if (this.mOperate.equals("INSERT||EDORTYPE"))
		// tLPEdorAppDB.setEdorType(tLPGrpEdorMainSchema.getEdorType());
		//
		// tLPEdorAppDB.setEdorState("1");
		//
		// tLPGrpEdorMainSet.clear();
		// tLPGrpEdorMainSet = tLPEdorAppDB.query();
		//
		// if (tLPGrpEdorMainSet != null)
		// {
		// logger.debug("----chksize:" + tLPGrpEdorMainSet.size());
		// if (mOperate.equals("INSERT||EDOR") ||
		// mOperate.equals("INSERT||EDORTYPE"))
		// {
		// if (tLPGrpEdorMainSet.size() > 0)
		// {
		// // @@错误处理
		// CError tError = new CError();
		// tError.moduleName = "PGrpEdorMainBL";
		// tError.functionName = "Chkdata";
		// tError.errorMessage = "此申请已经存在,不需再次申请!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		// }
		// }
		//
		// if (mOperate.equals("INSERT||EDOR") ||
		// mOperate.equals("INSERT||EDORTYPE") ||
		// mOperate.equals("UPDATE||EDOR"))
		// {
		// String tSql = "select * from LPGrpedormain where polno='" +
		// tLPGrpEdorMainSchema.getGrpPolNo() + "' and EdorNo='" +
		// tLPGrpEdorMainSchema.getEdorNo() +
		// "' and EdorState in ('2','0')";
		// logger.debug("---sql:" + tSql);
		// LPEdorAppDB iLPGrpEdorMainDB = new LPEdorAppDB();
		// LPGrpEdorMainSet iLPGrpEdorMainSet = new LPGrpEdorMainSet();
		// iLPGrpEdorMainSet = iLPGrpEdorMainDB.executeQuery(tSql);
		// if (iLPGrpEdorMainSet != null && iLPGrpEdorMainSet.size() > 0)
		// {
		// // @@错误处理
		// CError tError = new CError();
		// tError.moduleName = "PEdorMainBL";
		// tError.functionName = "Chkdata";
		// tError.errorMessage = "此申请已经申请确认,不能添加/修改项目!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		// }
		//
		// logger.debug("--------check_data");
		return true;

	}

	/**
	 * 准备需要保存的集体数据
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(map);
			mResult.clear();
			mResult.add(mLPEdorAppSchema);
			mResult.add(mLPGrpEdorMainSet);

		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PGrpEdorMainBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;

	}

}
