package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCBnfDB;
import com.sinosoft.lis.db.LPBnfDB;
import com.sinosoft.lis.schema.LCBnfSchema;
import com.sinosoft.lis.schema.LPBnfSchema;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LPBnfSet;
import com.sinosoft.lis.db.LCAccountDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDCode1DB;
import com.sinosoft.lis.db.LJAGetDB;
import com.sinosoft.lis.db.LJSGetDB;
import com.sinosoft.lis.db.LJSGetDrawDB;
import com.sinosoft.lis.db.LPAccountDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCAccountSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LPAccountSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LDCode1Set;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.lis.vschema.LJSGetDrawSet;
import com.sinosoft.lis.vschema.LJSGetSet;
import com.sinosoft.lis.vschema.LPAccountSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 领取形式变更
 * </p>*
 * <p>
 * Description: 保全确认类
 * </p>*
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>*
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Lihs
 * @version 1.0
 */

public class PEdorGCConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(PEdorGCConfirmBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	//public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private MMap mMap = new MMap();
	/** 数据操作字符串 */
	private String mOperate;

	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	public PEdorGCConfirmBL() {
	}

	public VData getResult() {
		return mResult;
	}

	public void setOperate(String cOperate) {
		this.mOperate = cOperate;
	}

	public String getOperate() {
		return this.mOperate;
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.setOperate(cOperate);
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(mInputData)) {
			return false;
		}
		// 数据准备操作（preparedata())
		if (!prepareData()) {
			return false;
		}
		this.setOperate("CONFIRM||GC");
		logger.debug("---" + mOperate);
		// 数据操作业务处理(新增insertData();修改updateData();删除deletedata())
		// //数据提交
		// PubSubmit tPubSubmit = new PubSubmit();
		// if (!tPubSubmit.submitData(mResult, ""))
		// {
		// // @@错误处理
		// mErrors.copyAllErrors(tPubSubmit.mErrors);
		// CError.buildErr(this, "数据提交失败");
		// return false;
		// }
		// mInputData = null;
		return true;
	}

	private boolean getInputData(VData cInputData) {
		mLPEdorItemSchema = (LPEdorItemSchema) cInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		if (mLPEdorItemSchema == null || mGlobalInput == null) {
			mErrors.addOneError(new CError("传入数据不完全！"));
			return false;
		}
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPEdorItemDB.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
		tLPEdorItemDB.setPolNo(mLPEdorItemSchema.getPolNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemSet == null || tLPEdorItemSet.size() != 1) {
			mErrors.addOneError(new CError("查询批改项目信息失败！"));
			return false;
		}
		mLPEdorItemSchema.setSchema(tLPEdorItemSet.get(1));
		return true;
	}

	private boolean prepareData() {
		Reflections tRef = new Reflections();
		/* LCCont <---> LPCont（互换p，c 表 ） */
		LPContDB tLPContDB = new LPContDB();
		LPContSet aLPContSet = new LPContSet();
		LPContSet tLPContSet = new LPContSet();
		LCContSet aLCContSet = new LCContSet();
		LCContSet tLCContSet = new LCContSet();

		LPContSchema tLPContSchema = new LPContSchema();
		LCContSchema tLCContSchema = new LCContSchema();
		LPContSchema aLPContSchema = new LPContSchema();
		LCContSchema aLCContSchema = new LCContSchema();

		// tLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		// tLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		// tLPContSchema.setContNo(mLPEdorItemSchema.getContNo());
		//
		// tLPContDB.setSchema(tLPContSchema);
		// tLPContSet = tLPContDB.query();
		// for (int j = 1; j <= tLPContSet.size(); j++)
		// {
		// aLPContSchema = tLPContSet.get(j);
		// //更新应付总表
		// updateLJAGet(aLPContSchema);
		// //为p，c表互换做数据准备
		// tLCContSchema = new LCContSchema();
		// tLPContSchema = new LPContSchema();
		//
		// LCContDB aLCContDB = new LCContDB();
		// aLCContDB.setContNo(aLPContSchema.getContNo());
		// aLCContDB.setInsuredNo(aLPContSchema.getInsuredNo());
		// if (!aLCContDB.getInfo())
		// {
		// mErrors.copyAllErrors(aLCContDB.mErrors);
		// mErrors.addOneError(new CError("查询被保人信息失败！"));
		// return false;
		// }
		// aLCContSchema = aLCContDB.getSchema();
		// tRef.transFields(tLPContSchema, aLCContSchema);
		// tLPContSchema.setEdorNo(aLPContSchema.getEdorNo());
		// tLPContSchema.setEdorType(aLPContSchema.getEdorType());
		//
		// //转换成保单个人信息。
		// tRef.transFields(tLCContSchema, aLPContSchema);
		// tLCContSchema.setModifyDate(PubFun.getCurrentDate());
		// tLCContSchema.setModifyTime(PubFun.getCurrentTime());
		//
		// aLPContSet.add(tLPContSchema);
		// tLCContSet.add(tLCContSchema);
		// aLCContSet.add(tLCContSchema);
		// }

		// 得到当前LPEdorItem保单信息主表的状态，并更新状态为申请确认。
		mLPEdorItemSchema.setEdorState("0");

		mMap.put(mLPEdorItemSchema, "UPDATE");

		// mMap.put(aLCContSet, "UPDATE");
		// mMap.put(aLPContSet, "UPDATE");

		/* LCPol <---> LPPol（得到投保人资料信息表p，c 表 ） */
		LPPolSet tLPPolSet = new LPPolSet();
		LPPolDB tLPPolDB = new LPPolDB();
		tLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType());

		String strSql = "select * from LPPol where EdorNo = '?EdorNo?' and EdorType = '?EdorType?'";
 
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(strSql);
		sqlbv.put("EdorNo", mLPEdorItemSchema.getEdorNo());
		sqlbv.put("EdorType", mLPEdorItemSchema.getEdorType());
		tLPPolSet = tLPPolDB.executeQuery(sqlbv);
		LPPolSet aLPPolSet = new LPPolSet();
		LCPolSet aLCPolSet = new LCPolSet();
		for (int i = 1; i <= tLPPolSet.size(); i++) {
			this.updateLJAGet(tLPPolSet.get(i));
			LPPolSchema tLPPolSchema = new LPPolSchema();
			LCPolSchema tLCPolSchema = new LCPolSchema();
			tRef.transFields(tLCPolSchema, tLPPolSet.get(i));
			tLCPolSchema.setModifyTime(PubFun.getCurrentTime());
			tLCPolSchema.setModifyDate(PubFun.getCurrentDate());

			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(tLCPolSchema.getPolNo());
			if (!tLCPolDB.getInfo()) {
				mErrors.copyAllErrors(tLCPolDB.mErrors);
				mErrors.addOneError(new CError("查询险种保单表失败！"));
				return false;
			}

			tRef.transFields(tLPPolSchema, tLCPolDB.getSchema());
			tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPPolSchema.setModifyTime(PubFun.getCurrentTime());
			tLPPolSchema.setModifyDate(PubFun.getCurrentDate());
			// logger.debug("+++++++++++++tLPPolSchema+++++++++++++++++++++++++++"
			// + tLPPolSchema.encode());
			// logger.debug("+++++++++++++tLCPolSchema+++++++++++++++++++++++++++"
			// + tLCPolSchema.encode());
			aLPPolSet.add(tLPPolSchema);
			aLCPolSet.add(tLCPolSchema);

		}
		mMap.put(aLPPolSet, "UPDATE");
		mMap.put(aLCPolSet, "UPDATE");
		
		/*add by jiaqiangli 2008-08-26 lcbnf与lpbnf的互换*/
		// 查询 LPBnf
		logger.debug("start to deal lcbnf and lpbnf");
		
		String DeleteSQL = null;
		
		// 删除 LCBnf
		DeleteSQL = "delete from LCBnf " + "where 1 = 1 " + "and ContNo = '?ContNo?' " + "and PolNo = '"
				//只处理生存金分配信息
				+ "?PolNo?" + "' and bnftype = '0' ";
		SQLwithBindVariables sbv=new SQLwithBindVariables();
		sbv.sql(DeleteSQL);
		sbv.put("ContNo", mLPEdorItemSchema.getContNo());
		sbv.put("PolNo", mLPEdorItemSchema.getPolNo());
		mMap.put(sbv, "DELETE");
		
		LPBnfDB tLPBnfDB = new LPBnfDB();
		tLPBnfDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPBnfDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPBnfDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPBnfDB.setPolNo(mLPEdorItemSchema.getPolNo());
		//此处BnfType("0")这个查询条件不需要加
		LPBnfSet tLPBnfSet = new LPBnfSet();
		try {
			tLPBnfSet = tLPBnfDB.query();
		} catch (Exception ex) {
			CError.buildErr(this, "查询保全受益人表发生异常！");
			return false;
		}
		if (tLPBnfSet != null && tLPBnfSet.size() > 0) {
			// 拷贝 LPBnf 到 LCBnf
			LCBnfSet tLCBnfSetNew = new LCBnfSet();
			for (int i = 1; i <= tLPBnfSet.size(); i++) {
				LPBnfSchema tLPBnfSchema = new LPBnfSchema();
				tLPBnfSchema = tLPBnfSet.get(i);
				LCBnfSchema tLCBnfSchemaNew = new LCBnfSchema();
				PubFun.copySchema(tLCBnfSchemaNew, tLPBnfSchema);
				tLCBnfSchemaNew.setOperator(this.mGlobalInput.Operator);
				tLCBnfSchemaNew.setModifyDate(PubFun.getCurrentDate());
				tLCBnfSchemaNew.setModifyTime(PubFun.getCurrentTime());
				tLCBnfSetNew.add(tLCBnfSchemaNew);
				tLCBnfSchemaNew = null;
				tLPBnfSchema = null;
			}
			mMap.put(tLCBnfSetNew, "INSERT");
			tLCBnfSetNew = null;
		}
		tLPBnfDB = null;
		tLPBnfSet = null;

		// 删除 LPBnf
		DeleteSQL = "delete from LPBnf " + "where 1 = 1 " + "and EdorNo = '?EdorNo?' " + "and EdorType = '?EdorType?' " + "and ContNo = '?ContNo?' " + "and PolNo = '?PolNo?'";
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(DeleteSQL);
		sbv1.put("EdorNo", mLPEdorItemSchema.getEdorNo());
		sbv1.put("EdorType", mLPEdorItemSchema.getEdorType());
		sbv1.put("ContNo", mLPEdorItemSchema.getContNo());
		sbv1.put("PolNo", mLPEdorItemSchema.getPolNo());
		mMap.put(sbv1, "DELETE");

		// 查询 LCBnf
		LCBnfDB tLCBnfDB = new LCBnfDB();
		tLCBnfDB.setContNo(mLPEdorItemSchema.getContNo());
		tLCBnfDB.setPolNo(mLPEdorItemSchema.getPolNo());
		//只是互换生存金分配信息
		//生存受益人，这里必须要加这个条件
		tLCBnfDB.setBnfType("0");
		
		LCBnfSet tLCBnfSet = new LCBnfSet();
		try {
			tLCBnfSet = tLCBnfDB.query();
		} catch (Exception ex) {
			CError.buildErr(this, "查询契约受益人表发生异常！");
			return false;
		}
		if (tLCBnfSet != null && tLCBnfSet.size() > 0) {
			// 拷贝 LCBnf 到 LPBnf
			LPBnfSet tLPBnfSetNew = new LPBnfSet();
			for (int i = 1; i <= tLCBnfSet.size(); i++) {
				LCBnfSchema tLCBnfSchema = new LCBnfSchema();
				LPBnfSchema tLPBnfSchemaNew = new LPBnfSchema();
				tLCBnfSchema = tLCBnfSet.get(i);
				PubFun.copySchema(tLPBnfSchemaNew, tLCBnfSchema);
				tLPBnfSchemaNew.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPBnfSchemaNew.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPBnfSchemaNew.setOperator(this.mGlobalInput.Operator);
				tLPBnfSchemaNew.setModifyDate(PubFun.getCurrentDate());
				tLPBnfSchemaNew.setModifyTime(PubFun.getCurrentTime());
				tLPBnfSetNew.add(tLPBnfSchemaNew);
				tLPBnfSchemaNew = null;
				tLCBnfSchema = null;
			}
			mMap.put(tLPBnfSetNew, "INSERT");
			tLPBnfSetNew = null;
		}
		tLCBnfDB = null;
		tLCBnfSet = null;
		
		DeleteSQL = null;
		/*add by jiaqiangli 2008-08-26 lcbnf与lpbnf的互换*/

		/* LCAccount <---> LPAccount（互换帐户p，c 表 ） */
		LPAccountSet tLPAccountSet = new LPAccountSet();
		LPAccountSchema aLPAccountSchema = new LPAccountSchema();
		LPAccountSchema tLPAccountSchema = new LPAccountSchema();
		LCAccountSchema aLCAccountSchema = new LCAccountSchema();
		tLPAccountSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPAccountSchema.setEdorType(mLPEdorItemSchema.getEdorType());

		LPAccountDB tLPAccountDB = new LPAccountDB();
		tLPAccountDB.setSchema(tLPAccountSchema);
		tLPAccountSet = tLPAccountDB.query();
		if (tLPAccountSet.size() > 0) {
			for (int j = 1; j <= tLPAccountSet.size(); j++) {
				aLPAccountSchema = tLPAccountSet.get(j);
				LCAccountSchema tLCAccountSchema = new LCAccountSchema();
				tLPAccountSchema = new LPAccountSchema();

				LCAccountDB aLCAccountDB = new LCAccountDB();
				aLCAccountDB.setCustomerNo(aLPAccountSchema.getCustomerNo());
				aLCAccountDB.setBankAccNo(aLPAccountSchema.getBankAccNo());
				aLCAccountDB.setBankCode(aLPAccountSchema.getBankCode());
				aLCAccountDB.setAccName(aLPAccountSchema.getAccName());
				if (aLCAccountDB.getInfo()) {
					aLCAccountSchema = aLCAccountDB.getSchema();
					tRef.transFields(tLPAccountSchema, aLCAccountSchema);
					tLPAccountSchema.setEdorNo(aLPAccountSchema.getEdorNo());
					tLPAccountSchema
							.setEdorType(aLPAccountSchema.getEdorType());

					// 转换成保单个人信息。
					tRef.transFields(tLCAccountSchema, aLPAccountSchema);
					tLCAccountSchema.setModifyDate(PubFun.getCurrentDate());
					tLCAccountSchema.setModifyTime(PubFun.getCurrentTime());

					mMap.put(tLPAccountSchema, "UPDATE");
					mMap.put(tLCAccountSchema, "UPDATE");
				} else {
					tRef.transFields(tLCAccountSchema, aLPAccountSchema);
					logger.debug(tLCAccountSchema.encode());
					tLCAccountSchema.setMakeDate(PubFun.getCurrentDate());
					tLCAccountSchema.setModifyTime(PubFun.getCurrentTime());
					tLCAccountSchema.setModifyDate(PubFun.getCurrentDate());
					tLCAccountSchema.setModifyTime(PubFun.getCurrentTime());

					mMap.put(tLCAccountSchema, "INSERT");
					mMap.put(aLPAccountSchema, "DELETE");
				}
			}
		}
		mResult.clear();
		mResult.add(mMap);
		return true;
	}

	/**
	 * 更新生存领取记录
	 * 
	 * @param tLPPolSchema
	 * @return boolean
	 */
	private boolean updateLJAGet(LPPolSchema tLPPolSchema) {
		String sCurrentDate = PubFun.getCurrentDate();
		String sCurrentTime = PubFun.getCurrentTime();

		// 更新应付记录
		LJSGetDB tLJSGetDB = new LJSGetDB();
		tLJSGetDB.setOtherNoType("2");
		tLJSGetDB.setOtherNo(tLPPolSchema.getContNo());
		LJSGetSet updLJSGetSet = tLJSGetDB.query();
		if (tLJSGetDB.mErrors.needDealError()) {
			CError.buildErr(this, "应付记录查询失败!");
			return false;
		}
		if (updLJSGetSet != null && updLJSGetSet.size() > 0) {
			LJSGetDrawSet updLJSGetDrawSet = new LJSGetDrawSet();
			LJSGetDrawDB tLJSGetDrawDB;
			LJSGetDrawSet tLJSGetDrawSet;
			for (int j = 1; j <= updLJSGetSet.size(); j++) {
				if (tLPPolSchema.getGetForm() != null
						&& tLPPolSchema.getGetForm().equals("0")) // 银行转账
				{
					updLJSGetSet.get(j).setBankCode(
							tLPPolSchema.getGetBankCode());
					updLJSGetSet.get(j).setBankAccNo(
							tLPPolSchema.getGetBankAccNo());
					updLJSGetSet.get(j)
							.setAccName(tLPPolSchema.getGetAccName());
					updLJSGetSet.get(j).setModifyDate(sCurrentDate);
					updLJSGetSet.get(j).setModifyTime(sCurrentTime);
				}
				// /***************把lcpol表的领取形式信息置到ljsget总表中**************************\
				if (tLPPolSchema.getGetForm() != null
						&& !tLPPolSchema.getGetForm().equals("")) {
					LDCode1DB tLDCode1DB = new LDCode1DB();
					LDCode1Set tLDCode1Set = new LDCode1Set();
					tLDCode1DB.setCodeType("getform");
					tLDCode1DB.setCodeName("FK");
					tLDCode1DB.setCode(tLPPolSchema.getGetForm());
					tLDCode1Set = tLDCode1DB.query();
					if (tLDCode1Set == null || tLDCode1Set.size() < 1) {
						CError.buildErr(this, "");
						return false;
					}
					updLJSGetSet.get(j).setPayMode(
							tLDCode1Set.get(1).getCode1());
				} else {
					updLJSGetSet.get(j).setPayMode("1");
				}
				// \*******************************2006-07-25****************************************************/

				tLJSGetDrawDB = new LJSGetDrawDB();
				tLJSGetDrawDB.setGetNoticeNo(updLJSGetSet.get(j)
						.getGetNoticeNo());
				tLJSGetDrawSet = tLJSGetDrawDB.query();
				if (tLJSGetDrawDB.mErrors.needDealError()) {
					CError.buildErr(this, "生存领取应付记录查询失败!");
					return false;
				}
				if (tLJSGetDrawSet == null || tLJSGetDrawSet.size() < 1) {
					CError.buildErr(this, "生存领取应付记录查询失败!");
					return false;
				}
				for (int k = 1; k <= tLJSGetDrawSet.size(); k++) {
					if (tLPPolSchema.getGetForm().equals("1")) // 自行领取
					{
						tLJSGetDrawSet.get(k).setComeFlag("1"); // 需要上柜
					} else // 其他领取形式
					{
						tLJSGetDrawSet.get(k).setComeFlag("0"); // 不需要上柜
					}
				}
				updLJSGetDrawSet.add(tLJSGetDrawSet);
			}
			mMap.put(updLJSGetSet, "UPDATE");
			mMap.put(updLJSGetDrawSet, "UPDATE");
		}

		// 更新实付记录
		LJAGetDB tLJAGetDB = new LJAGetDB();
		tLJAGetDB.setOtherNoType("2");
		tLJAGetDB.setOtherNo(tLPPolSchema.getContNo());
		LJAGetSet updLJAGetSet = tLJAGetDB.query();
		if (tLJAGetDB.mErrors.needDealError()) {
			CError.buildErr(this, "实付记录查询失败!");
			return false;
		}
		if (updLJAGetSet != null && updLJAGetSet.size() > 0) {
			// 根据续期领取形式重置银行帐户信息或是领取方式
			for (int j = 1; j <= updLJAGetSet.size(); j++) {
				String sBankOnTheWayFlag = updLJAGetSet.get(j)
						.getBankOnTheWayFlag();
				String sEnterAccDate = updLJAGetSet.get(j).getEnterAccDate();
				if (sBankOnTheWayFlag != null && sBankOnTheWayFlag.equals("1")) // 银行在途
				{
					continue;
				}
				if (sEnterAccDate != null && !sEnterAccDate.trim().equals("")) // 财务到账
				{
					continue;
				}

				if (tLPPolSchema.getGetForm() != null
						&& tLPPolSchema.getGetForm().equals("0")) // 银行转账
				{
					updLJAGetSet.get(j).setPayMode("4");
					updLJAGetSet.get(j).setBankCode(
							tLPPolSchema.getGetBankCode());
					updLJAGetSet.get(j).setBankAccNo(
							tLPPolSchema.getGetBankAccNo());
					updLJAGetSet.get(j)
							.setAccName(tLPPolSchema.getGetAccName());
					updLJAGetSet.get(j).setModifyDate(sCurrentDate);
					updLJAGetSet.get(j).setModifyTime(sCurrentTime);
				}
				if (tLPPolSchema.getGetForm() != null
						&& tLPPolSchema.getGetForm().equals("1")) // 自行领取
				{
					updLJAGetSet.get(j).setPayMode("1"); // 现金
					updLJAGetSet.get(j).setBankCode("");
					updLJAGetSet.get(j).setBankAccNo("");
					updLJAGetSet.get(j).setAccName("");
					updLJAGetSet.get(j).setModifyDate(sCurrentDate);
					updLJAGetSet.get(j).setModifyTime(sCurrentTime);

					// <!-- XinYQ added on 2006-03-03 : 为 GC 领取通知书打印准备数据 : BGN
					// -->
					String sNoneLimitNo = PubFun
							.getNoLimit(mGlobalInput.ManageCom);
					String sResultMaxNo = PubFun1.CreateMaxNo("PRTSEQNO",
							sNoneLimitNo);
					if (sResultMaxNo == null || sResultMaxNo.trim().equals("")) {
						CError.buildErr(this, "生成唯一性的领取通知书打印流水号失败！");
						return false;
					}
					LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
					tLOPRTManagerSchema.setPrtSeq(sResultMaxNo);
					tLOPRTManagerSchema.setOtherNo(mLPEdorItemSchema
							.getEdorAcceptNo());
					tLOPRTManagerSchema.setOtherNoType("06");
					tLOPRTManagerSchema
							.setCode(PrintManagerBL.CODE_PEdorGCGetNotice);
					tLOPRTManagerSchema.setManageCom(mGlobalInput.ManageCom);
					tLOPRTManagerSchema.setReqCom(mGlobalInput.ManageCom);
					tLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);
					tLOPRTManagerSchema.setPrtType("0");
					tLOPRTManagerSchema.setStateFlag("0");
					tLOPRTManagerSchema.setPatchFlag("0");
					tLOPRTManagerSchema.setStandbyFlag1(mLPEdorItemSchema
							.getEdorNo());
					tLOPRTManagerSchema.setStandbyFlag2(mLPEdorItemSchema
							.getContNo());
					tLOPRTManagerSchema.setStandbyFlag3(mLPEdorItemSchema
							.getPolNo());
					tLOPRTManagerSchema.setStandbyFlag4(updLJAGetSet.get(j)
							.getActuGetNo());
					tLOPRTManagerSchema.setMakeDate(sCurrentDate);
					tLOPRTManagerSchema.setMakeTime(sCurrentTime);
					mMap.put(tLOPRTManagerSchema, "DELETE&INSERT");
					tLOPRTManagerSchema = null;
					// <!-- XinYQ added on 2006-03-03 : 为 GC 领取通知书打印准备数据 : END
					// -->
				}
			}
			mMap.put(updLJAGetSet, "UPDATE");
		}
		return true;
	}

	public static void main(String[] args) {
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";
		tGlobalInput.ManageCom = "86";
		tGlobalInput.ComCode = "86";

		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo("6120060724000042");
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		tLPEdorItemSet = tLPEdorItemDB.query();
		tLPEdorItemSchema = tLPEdorItemSet.get(1);

		VData tVData = new VData();
		tVData.add(tGlobalInput);
		tVData.add(tLPEdorItemSchema);

		PEdorGCConfirmBL tPEdorGCConfirmBL = new PEdorGCConfirmBL();
		if (tPEdorGCConfirmBL.submitData(tVData, "")) {
			logger.debug("OK!");
		}

	}
}
