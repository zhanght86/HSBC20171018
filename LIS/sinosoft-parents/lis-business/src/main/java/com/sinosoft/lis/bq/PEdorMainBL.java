package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.lis.vschema.LPEdorMainSet;
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
public class PEdorMainBL {
private static Logger logger = Logger.getLogger(PEdorMainBL.class);
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
	private LPEdorMainSet mLPEdorMainSet = new LPEdorMainSet();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private MMap map = new MMap();
	// 统一更新日期，时间
	private String theCurrentDate = PubFun.getCurrentDate();
	private String theCurrentTime = PubFun.getCurrentTime();

	public PEdorMainBL() {
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
		// 准备给后台的数据
		if (prepareOutputData() == false) { // 错误处理
			return false;
		}
		PubSubmit tPubSubmit = new PubSubmit();

		if (!tPubSubmit.submitData(mInputData, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);

			CError tError = new CError();
			tError.moduleName = "PEdorMainBL";
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
		// int m;
		// 准备个人保单（保全）的信息

		// int n = mLPEdorMainBLSet.size();
		if (mLPEdorAppSchema.getEdorAcceptNo() == null
				|| mLPEdorAppSchema.getEdorAcceptNo().equals("")) {

			String strLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
			String strEdorAcceptNo = PubFun1.CreateMaxNo("EdorAcceptNo",
					strLimit);
			if (StrTool.compareString(strEdorAcceptNo, "")) {
				CError.buildErr(this, "生成保全申请号错误！");
				return false;
			} else {
				mLPEdorAppSchema.setEdorAcceptNo(strEdorAcceptNo);
			}
			mLPEdorAppSchema.setEdorState("1");
			mLPEdorAppSchema.setEdorConfNo(mLPEdorAppSchema.getEdorAcceptNo());
			mLPEdorAppSchema.setOperator(mGlobalInput.Operator);
			mLPEdorAppSchema.setManageCom(mGlobalInput.ManageCom);
			mLPEdorAppSchema.setMakeDate(theCurrentDate);
			mLPEdorAppSchema.setMakeTime(theCurrentTime);
			mLPEdorAppSchema.setModifyDate(theCurrentDate);
			mLPEdorAppSchema.setModifyTime(theCurrentTime);
			map.put(mLPEdorAppSchema, "INSERT");
		}

		int n = mLPEdorMainSet.size();
		for (int i = 1; i <= n; i++) {
			LPEdorMainSchema tLPEdorMainSchema = mLPEdorMainSet.get(i);
			tLPEdorMainSchema.setEdorAcceptNo(mLPEdorAppSchema
					.getEdorAcceptNo());
			if (tLPEdorMainSchema.getEdorAppNo() == null
					|| tLPEdorMainSchema.getEdorAppNo().equals("")) {
				String strLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
				String strEdorAppNo = PubFun1
						.CreateMaxNo("EdorAppNo", strLimit);
				if (StrTool.compareString(strEdorAppNo, "")) {
					CError.buildErr(this, "生成保全申请号错误！");
					return false;
				} else {
					tLPEdorMainSchema.setEdorAppNo(strEdorAppNo);
					tLPEdorMainSchema.setEdorNo(strEdorAppNo);
				}
			}
			tLPEdorMainSchema.setEdorState("1");
			tLPEdorMainSchema.setUWState("0");
			tLPEdorMainSchema.setOperator(mGlobalInput.Operator);
			tLPEdorMainSchema.setManageCom(mGlobalInput.ManageCom);
			tLPEdorMainSchema.setMakeDate(theCurrentDate);
			tLPEdorMainSchema.setMakeTime(theCurrentTime);
			tLPEdorMainSchema.setModifyDate(theCurrentDate);
			tLPEdorMainSchema.setModifyTime(theCurrentTime);
		}

		map.put(mLPEdorMainSet, "INSERT");

		return true;

	}

	/**
	 * mResult.clear(); mLPEdorMainBLSet=new LPEdorMainBLSet(); VData tVData=new
	 * VData(); LPEdorMainSet tLPEdorMainSet=new LPEdorMainSet();
	 * tVData=tPEdorMainBLS.getResult();
	 * tLPEdorMainSet=(LPEdorMainSet)tVData.getObjectByObjectName("LPEdorMainBLSet",0);
	 * mLPEdorMainBLSet.set(tLPEdorMainSet); mResult.add(mLPEdorMainBLSet);
	 * return true; }
	 */
	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));

		if (mOperate.equals("INSERT||EDOR")) {
			mLPEdorMainSet.set((LPEdorMainSet) cInputData
					.getObjectByObjectName("LPEdorMainSet", 0));
			mLPEdorAppSchema.setSchema((LPEdorAppSchema) cInputData
					.getObjectByObjectName("LPEdorAppSchema", 0));
			// tLPEdorMainSchema.setSchema(mLPEdorMainBLSet.get(1));
		}

		return true;

	}

	/**
	 * 校验传入的是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {

		// boolean flag = true;
		// LPEdorAppSchema tLPEdorAppSchema = new LPEdorAppSchema();
		// LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		// LPEdorMainSet tLPEdorMainSet = new LPEdorMainSet();
		//
		// tLPEdorMainSchema = mLPEdorMainSet.get(1).getSchema();
		// tLPEdorAppDB.setGrpPolNo(tLPEdorMainSchema.getGrpPolNo());
		//
		// if (this.mOperate.equals("INSERT||EDORTYPE"))
		// tLPEdorAppDB.setEdorType(tLPEdorMainSchema.getEdorType());
		//
		// tLPEdorAppDB.setEdorState("1");
		//
		// tLPEdorMainSet.clear();
		// tLPEdorMainSet = tLPEdorAppDB.query();
		//
		// if (tLPEdorMainSet != null)
		// {
		// logger.debug("----chksize:" + tLPEdorMainSet.size());
		// if (mOperate.equals("INSERT||EDOR") ||
		// mOperate.equals("INSERT||EDORTYPE"))
		// {
		// if (tLPEdorMainSet.size() > 0)
		// {
		// // @@错误处理
		// CError tError = new CError();
		// tError.moduleName = "PEdorMainBL";
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
		// String tSql = "select * from LPEdormain where polno='" +
		// tLPEdorMainSchema.getGrpPolNo() + "' and EdorNo='" +
		// tLPEdorMainSchema.getEdorNo() +
		// "' and EdorState in ('2','0')";
		// logger.debug("---sql:" + tSql);
		// LPEdorAppDB iLPEdorMainDB = new LPEdorAppDB();
		// LPEdorMainSet iLPEdorMainSet = new LPEdorMainSet();
		// iLPEdorMainSet = iLPEdorMainDB.executeQuery(tSql);
		// if (iLPEdorMainSet != null && iLPEdorMainSet.size() > 0)
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
			mResult.add(mLPEdorMainSet);

		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorMainBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;

	}

}
