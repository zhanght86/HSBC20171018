package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LJSGetDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.db.LPEdorMainDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.lis.vschema.LJSGetSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LPEdorAppSet;
import com.sinosoft.lis.vschema.LPEdorMainSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全申请确认逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Minim
 * @version 2.0
 */
public class PEdorAcceptConfirmBL {
private static Logger logger = Logger.getLogger(PEdorAcceptConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	private VData pInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	/**  */
	LPEdorAppSchema mLPEdorAppSchema = new LPEdorAppSchema();
	LPEdorAppSet mLPEdorAppSet = new LPEdorAppSet();
	LPGrpEdorItemSet mLPGrpEdorItemSet = new LPGrpEdorItemSet();
	LPEdorMainSchema mLPEdorMainSchema = new LPEdorMainSchema();
	LPEdorMainSet mLPEdorMainSet = new LPEdorMainSet();
	String mStrTemplatePath = "";

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private MMap map = new MMap();

	public PEdorAcceptConfirmBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("---End getInputData---");

		if (!checkData()) {
			return false;
		}
		logger.debug("---End checkData---");

		// 数据准备操作，检查团体保全主表数据，看是否通过了保全核保
		if (!prepareData()) {
			return false;
		}
		logger.debug("---End prepareData---");

		// 数据操作业务处理
		if (cOperate.equals("INSERT||EDORACPTCONFIRM")) {
			if (!dealData()) {
				return false;
			}

		}
		// 数据提交、保存
		mInputData.add(map);

		PubSubmit tPubSubmit = new PubSubmit();
		logger.debug("Start tPRnewManualDunBLS Submit...");

		if (!tPubSubmit.submitData(mInputData, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);

			CError tError = new CError();
			tError.moduleName = "PEdorAcceptConfirmBL";
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
	 * @return
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 业务逻辑处理方法
	 * 
	 * @return boolean
	 */
	public boolean dealData() {
		pInputData = new VData();

		EdorConfirmBL aEdorConfirmBL = new EdorConfirmBL();
		// mLPEdorAppSchema.setSchema(mLPEdorAppSet.get(i));
		LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
		tLPEdorMainDB.setEdorAcceptNo(mLPEdorAppSchema.getEdorAcceptNo());
		mLPEdorMainSet = tLPEdorMainDB.query();
		if (tLPEdorMainDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询个人保全失败！");
		}
		for (int i = 1; i <= mLPEdorMainSet.size(); i++) {
			pInputData.clear();
			pInputData.addElement(mGlobalInput);
			pInputData.addElement("I");
			pInputData.addElement(mLPEdorAppSchema);
			pInputData.addElement(mStrTemplatePath);

			logger.debug("处理个人保全主表");
			pInputData.addElement(mLPEdorMainSet.get(i));
			if (!aEdorConfirmBL.submitData(pInputData, mOperate)) {
				this.mErrors.copyAllErrors(aEdorConfirmBL.mErrors);
				return false;
			} else {
				VData rVData = aEdorConfirmBL.getResult();
				MMap tMap = new MMap();
				tMap = (MMap) rVData.getObjectByObjectName("MMap", 0);
				if (tMap == null) {
					CError
							.buildErr(this, "得到个人保单为:"
									+ mLPEdorMainSet.get(i).getContNo()
									+ "的保全确认结果时失败！");
					return false;

				} else {
					map.add(tMap);
				}
			}
		}
		String tEdorAcceptNo = mLPEdorAppSchema.getEdorAcceptNo();
		// 下面要增加处理保全申请主表的逻辑
		mLPEdorAppSchema.setEdorState("0");
		mLPEdorAppSchema.setConfOperator(mGlobalInput.Operator);
		mLPEdorAppSchema.setConfDate(PubFun.getCurrentDate());
		mLPEdorAppSchema.setConfTime(PubFun.getCurrentTime());
		mLPEdorAppSchema.setModifyDate(PubFun.getCurrentDate());
		mLPEdorAppSchema.setModifyTime(PubFun.getCurrentTime());
		map.put(mLPEdorAppSchema, "UPDATE");
		String wherePart = "where EdorAcceptNo='" + "?tEdorAcceptNo?" + "'";
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql("update LPEdorApp set ChgPrem= (select sum(ChgPrem) from LPEdorMain "
				+ wherePart + ") " + wherePart);
		sbv1.put("tEdorAcceptNo", tEdorAcceptNo);
		map.put(sbv1, "UPDATE");
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql("update LPEdorApp set ChgAmnt= (select sum(ChgAmnt) from LPEdorMain "
				+ wherePart + ") " + wherePart);
		sbv2.put("tEdorAcceptNo", tEdorAcceptNo);
		map.put(sbv2, "UPDATE");
		SQLwithBindVariables sbv3=new SQLwithBindVariables();
		sbv3.sql("update LPEdorApp set GetMoney= (select sum(GetMoney) from LPEdorMain "
				+ wherePart + ") " + wherePart);
		sbv3.put("tEdorAcceptNo", tEdorAcceptNo);
		map.put(sbv3, "UPDATE");
		SQLwithBindVariables sbv4=new SQLwithBindVariables();
		sbv4.sql("update LPEdorApp set GetInterest= (select sum(GetInterest) from LPEdorMain "
				+ wherePart + ") " + wherePart);
		sbv4.put("tEdorAcceptNo", tEdorAcceptNo);
		map.put(sbv4, "UPDATE");

		return true;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return
	 */
	private boolean getInputData() {
		try {
			mStrTemplatePath = (String) mInputData.get(0);
			mLPEdorAppSchema = (LPEdorAppSchema) mInputData
					.getObjectByObjectName("LPEdorAppSchema", 0);
			mGlobalInput.setSchema((GlobalInput) mInputData
					.getObjectByObjectName("GlobalInput", 0));
		} catch (Exception e) {
			CError.buildErr(this, "接收数据失败");
			return false;
		}

		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean prepareData() {

		mInputData.clear();

		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setSchema(mLPEdorAppSchema);
		if (!tLPEdorAppDB.getInfo()) {
			CError.buildErr(this, "查询保全申请主表时失败！");
			return false;
		} else {
			mLPEdorAppSchema.setSchema(tLPEdorAppDB.getSchema());
		}

		// for (int i=1; i<=tLPGrpEdorItemSet.size(); i++)
		// {
		// tLPEdorAppSchema = new LPGrpEdorItemSchema();
		// tLPEdorAppSchema = tLPGrpEdorItemSet.get(i);
		// if (i==1)
		// {
		// mLPEdorAppSet.add(tLPEdorAppSchema);
		//
		// tEdorNo = tLPEdorAppSchema.getEdorNo();
		//
		// LPGrpEdorItemSet iLPEdorAppSet = new LPGrpEdorItemSet();
		// String tSql = "select * from LPGrpedoritem where Edorno='"+tEdorNo+"'
		// and edorstate ='2' and uwstate<>'9'";
		// LPGrpEdorItemDB iLPEdorAppDB = new LPGrpEdorItemDB();
		// iLPEdorAppSet = iLPEdorAppDB.executeQuery(tSql);
		// if (iLPEdorAppSet.size()>0)
		// {
		// // @@错误处理
		// CError tError = new CError();
		// tError.moduleName = "PEdorAutoUWBL";
		// tError.functionName = "prepareData";
		// tError.errorMessage = "批单号:"+tEdorNo+"有个别项目未通过核保!";
		// this.mErrors .addOneError(tError) ;
		// return false;
		// }
		// }
		// else
		// {
		// if (tLPEdorAppSchema.getEdorNo().equals(tEdorNo))
		// continue;
		// mLPEdorAppSet.add(tLPEdorAppSchema);
		// tEdorNo = tLPEdorAppSchema.getEdorNo();
		//
		// LPGrpEdorItemSet iLPEdorAppSet = new LPGrpEdorItemSet();
		// String tSql = "select * from LPEdorApp where Edorno='"+tEdorNo+"' and
		// edorstate ='2' and uwstate<>'9'";
		// LPGrpEdorItemDB iLPEdorAppDB = new LPGrpEdorItemDB();
		// iLPEdorAppSet = iLPEdorAppDB.executeQuery(tSql);
		// if (iLPEdorAppSet.size()>0)
		// {
		// // @@错误处理
		// CError tError = new CError();
		// tError.moduleName = "PEdorAutoUWBL";
		// tError.functionName = "prepareData";
		// tError.errorMessage = "批单号:"+tEdorNo+"有个别项目未通过核保!";
		// this.mErrors .addOneError(tError) ;
		// return false;
		// }
		// }
		// }

		// if (mLPGrpEdorItemSet.size()>0)
		// {
		// mInputData.addElement(mLPGrpEdorItemSet);
		// }
		// else
		// return false;

		mInputData.addElement(mGlobalInput);
		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		logger.debug("start check data .....");
		boolean flag = true;

		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		String sql = "select * from LPEdorAPp where EdorAcceptNo='"
				+ "?EdorAcceptNo?"
				+ "' and uwstate in ('0','5')";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("EdorAcceptNo", mLPEdorAppSchema.getEdorAcceptNo());
		LPEdorAppSet tLPEdorAppSet = tLPEdorAppDB.executeQuery(sqlbv);
		if (tLPEdorAppSet != null && tLPEdorAppSet.size() > 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EdorConfirmBL";
			tError.functionName = "checkData";
			tError.errorMessage = "该申请核保没有通过!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// mGlobalInput.ManageCom = mLPEdorAppSchema.getManageCom();

		LJSGetDB tLJSGetDB = new LJSGetDB();
		LJSGetSet tLJSGetSet = new LJSGetSet();

		tLJSGetDB.setOtherNo(mLPEdorAppSchema.getEdorAcceptNo());
		tLJSGetDB.setOtherNoType("10");
		tLJSGetSet = tLJSGetDB.query();

		logger.debug("tLJGetSet size :" + tLJSGetSet.size());

		if (tLJSGetSet.size() > 0) {
			String aGetNoticeNo = tLJSGetSet.get(1).getGetNoticeNo();
			LJFinaConfirm tLJFinaConfirm = new LJFinaConfirm(aGetNoticeNo, "O");
			tLJFinaConfirm.setOperator(mGlobalInput.Operator);
			tLJFinaConfirm.setLimit(PubFun.getNoLimit(mGlobalInput.ManageCom));

			if (!tLJFinaConfirm.submitData()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "EdorConfirmBL";
				tError.functionName = "checkData";
				tError.errorMessage = "保全退费核销失败!";
				this.mErrors.addOneError(tError);
				return false;
			}

		}
		LJSPayDB tLJSPayDB = new LJSPayDB();
		LJSPaySet tLJSPaySet = new LJSPaySet();

		tLJSPayDB.setOtherNo(mLPEdorAppSchema.getEdorAcceptNo());
		tLJSPayDB.setOtherNoType("10");
		tLJSPaySet = tLJSPayDB.query();

		if (tLJSPaySet.size() > 0) {
			String aGetNoticeNo = tLJSPaySet.get(1).getGetNoticeNo();
			LJFinaConfirm tLJFinaConfirm = new LJFinaConfirm(aGetNoticeNo, "I");
			tLJFinaConfirm.setOperator(mGlobalInput.Operator);
			tLJFinaConfirm.setLimit(PubFun.getNoLimit(mGlobalInput.ManageCom));
			logger.debug("start LJFinaConfirm...");
			if (!tLJFinaConfirm.submitData()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "EdorConfirmBL";
				tError.functionName = "checkData";
				tError.errorMessage = "保全交费核销失败！请确认是否已交费！";
				this.mErrors.addOneError(tError);
				return false;
			}
		}

		return flag;
	}

	public static void main(String[] args) {
		VData tInputData = new VData();
		GlobalInput tGlobalInput = new GlobalInput();
		PEdorAcceptConfirmBL aPEdorAcceptConfirmBL = new PEdorAcceptConfirmBL();
		LPEdorAppSchema tLPEdorAppSchema = new LPEdorAppSchema();
		tGlobalInput.ManageCom = "86";
		tGlobalInput.Operator = "001";

		tLPEdorAppSchema.setEdorAcceptNo("86000000000410");
		// tLPEdorAppSchema.setEdorNo("410110000000157");
		String strTemplatePath = "xerox/printdata/";

		tInputData.addElement(strTemplatePath);
		tInputData.addElement(tLPEdorAppSchema);
		tInputData.addElement(tGlobalInput);

		if (!aPEdorAcceptConfirmBL.submitData(tInputData,
				"INSERT||EDORACPTCONFIRM")) {
			logger.debug(aPEdorAcceptConfirmBL.mErrors.getErrContent());
		}
	}

}
