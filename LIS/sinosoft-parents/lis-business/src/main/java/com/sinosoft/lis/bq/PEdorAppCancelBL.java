package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPEdorMainDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LOBEdorAppSchema;
import com.sinosoft.lis.schema.LOBEdorItemSchema;
import com.sinosoft.lis.schema.LPCancelReasonSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LOBEdorItemSet;
import com.sinosoft.lis.vschema.LPCancelReasonSet;
import com.sinosoft.lis.vschema.LPEdorAppSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPEdorMainSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全删除业务逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author lh Modify Lanjun 2005-04-25
 * @version 1.0
 */

// 功能：查询出个人批改主表中本次申请的批改类型
// 入口参数：个单的保单号、批单号
// 出口参数：每条记录的个单的保单号、批单号和批改类型
public class PEdorAppCancelBL {
private static Logger logger = Logger.getLogger(PEdorAppCancelBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap mMap = new MMap();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 撤销申请原因 */
	private	String CancelReasonContent = "";
	private	String 	SCanclReason = "";

	/** 数据操作字符串 */
	private String mOperate;
	private LPEdorAppSchema mLPEdorAppSchema = new LPEdorAppSchema();
	private GlobalInput mGlobalInput = new GlobalInput();

	private TransferData tTransferData = new TransferData();
	// 统一更新日期，时间
	private String theCurrentDate = PubFun.getCurrentDate();
	private String theCurrentTime = PubFun.getCurrentTime();

	public PEdorAppCancelBL() {
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
		if (!getInputData(cInputData)) {
			return false;
		}
		if (cOperate.equals("EDORAPP")) {
			if (!checkData()) {
				return false;
			}

			logger.debug("after checkData...");

			if (!prepareData()) {
				return false;
			}
			// 装配处理好的数据，准备给后台进行保存
			this.prepareOutputData();
			logger.debug("---prepareOutputData---");

		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 查询条件
		logger.debug("moperator:" + mOperate);
		if (mOperate.equals("EDORAPP")) {
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);

			mLPEdorAppSchema = (LPEdorAppSchema) cInputData
					.getObjectByObjectName("LPEdorAppSchema", 0);
			tTransferData = (TransferData) mInputData.getObjectByObjectName(
					"TransferData", 0);
			// 撤销申请原因
			this.CancelReasonContent = (String) tTransferData.getValueByName("CancelReasonContent");
			this.SCanclReason = (String) tTransferData
			.getValueByName("SCanclReason");
		}
		return true;
	}

	/**
	 * 检查数据的合法性
	 */
	private boolean checkData() {
		logger.debug("start check data");

		String strSql = " select * from LPEdorApp where EdorAcceptNo = '"
				+ "?EdorAcceptNo?" + "'";
		logger.debug(strSql);
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(strSql);
		sqlbv.put("EdorAcceptNo", mLPEdorAppSchema.getEdorAcceptNo());
		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		LPEdorAppSet tLPEdorAppSet = tLPEdorAppDB.executeQuery(sqlbv);

		if ((tLPEdorAppSet == null) || (tLPEdorAppSet.size() < 1)) {
			CError.buildErr(this, "不存在此保全申请号的记录");
			return false;
		}
		mLPEdorAppSchema = tLPEdorAppSet.get(1);
		if (mLPEdorAppSchema.getEdorState().equals("0")) {
			CError.buildErr(this, "保全申请已确认，不可进行申请撤销！");
			return false;
		}

		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean prepareData() {
		// int m;
		// int i;
		// LPEdorMainSchema aLPEdorMainSchema = new LPEdorMainSchema();
		// LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
		// LPEdorMainSet tLPEdorMainSet = new LPEdorMainSet();
		// tLPEdorMainDB.setSchema(mLPEdorAppSchema);
		//
		// // tLPEdorMainDB.setEdorState("1");
		// tLPEdorMainSet.set(tLPEdorMainDB.query());
		// if (tLPEdorMainDB.mErrors.needDealError() == true)
		// {
		// // @@错误处理
		// this.mErrors.copyAllErrors(tLPEdorMainDB.mErrors);
		// CError tError = new CError();
		// tError.moduleName = "PEdorAppCancelBL";
		// tError.functionName = "prepareData";
		// tError.errorMessage = "个人批改主表查询失败!";
		// this.mErrors.addOneError(tError);
		// tLPEdorMainSet.clear();
		// return false;
		// }
		// if (tLPEdorMainSet.size() == 0)
		// {
		// // @@错误处理
		// CError tError = new CError();
		// tError.moduleName = "PEdorAppCancelBL";
		// tError.functionName = "prepareData";
		// tError.errorMessage = "未找到相关数据!";
		// this.mErrors.addOneError(tError);
		// tLPEdorMainSet.clear();
		// return false;
		// }
		// m = tLPEdorMainSet.size();
		// for (i = 1; i <= m; i++)
		// {
		// aLPEdorMainSchema = tLPEdorMainSet.get(i);
		// mInputData.clear();
		// mInputData.add(aLPEdorMainSchema);
		//
		// EdorAppCancelBL tEdorAppCancelBL = new EdorAppCancelBL();
		//
		// if (!tEdorAppCancelBL.submitData(mInputData, mOperate))
		// {
		// return false;
		// }
		// }
		String delSql;
		String tEdorAcceptNo = mLPEdorAppSchema.getEdorAcceptNo();
        SQLwithBindVariables sbv1=new SQLwithBindVariables();
        if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
        sbv1.sql("delete /*+index( loprtmanager IDX_LOPRTMANAGER_OTHERNO)*/ from loprtmanager where (code like 'BQ8%' or code = 'BQ90') "
				//modify by jiaqiangli 2008-12-25 in to =
				+ "and otherno in (select contno from lpedormain where edoracceptno = '"
				+ "?tEdorAcceptNo?" + "')");
        }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
         sbv1.sql("delete from loprtmanager where (code like 'BQ8%' or code = 'BQ90') "
       		   //modify by jiaqiangli 2008-12-25 in to =
       			+ "and otherno in (select contno from lpedormain where edoracceptno = '"
       			+ "?tEdorAcceptNo?" + "')");	
        }
        sbv1.put("tEdorAcceptNo", tEdorAcceptNo);
		mMap
				.put(sbv1, "DELETE"); // 删除核保通知书
																	// （在删除批单记录之前）

		// 删除保全申请下的所有批单
		LPEdorMainSet tLPEdorMainSet = new LPEdorMainSet();
		LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
		tLPEdorMainDB.setEdorAcceptNo(tEdorAcceptNo);
		tLPEdorMainSet = tLPEdorMainDB.query();
		if (tLPEdorMainSet != null && tLPEdorMainSet.size() > 0) {
			for (int i = 1; i <= tLPEdorMainSet.size(); i++) {
				VData tVData = new VData();
				tVData.add(tLPEdorMainSet.get(i));
				tVData.add(mGlobalInput);
				tVData.addElement(tTransferData);
				PEdorMainCancelBL tPEdorMainCancelBL = new PEdorMainCancelBL();
				if (!tPEdorMainCancelBL.submitData(tVData, "EDORMAIN")) {
					CError tError = new CError();
					tError.moduleName = "PEdorItemCancelBL";
					tError.functionName = "dealData";
					tError.errorMessage = "删除保全项目失败!";
					this.mErrors.addOneError(tError);
					return false;
				} else {
					mMap.add(tPEdorMainCancelBL.getMap());
				}

			}
		}

		// 将将数据备份，并存储备份信息
		LPEdorAppSchema cLPEdorAppSchema = new LPEdorAppSchema();
		LOBEdorAppSchema cLOBEdorAppSchema = new LOBEdorAppSchema();
		LPEdorAppDB cLPEdorAppDB = new LPEdorAppDB();
		LPEdorAppSet cLPEdorAppSet = new LPEdorAppSet();

		// 选出当前记录
		String sql = "select * from LpedorApp where edorAcceptNo='"
				+ "?tEdorAcceptNo?" + "'";
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(sql);
		sbv2.put("tEdorAcceptNo", tEdorAcceptNo);
		cLPEdorAppSet = cLPEdorAppDB.executeQuery(sbv2);
		if (cLPEdorAppSet.size() > 0 && cLPEdorAppSet != null) {
			for (int k = 1; k <= cLPEdorAppSet.size(); k++) {
				cLPEdorAppSchema = cLPEdorAppSet.get(k);
				logger.debug("cLPEdorMainSchema:"
						+ cLPEdorAppSchema.getAccName()); // 测试用
				Reflections crf = new Reflections();
				crf.transFields(cLOBEdorAppSchema, cLPEdorAppSchema); // 将一条记录整体复制
				cLOBEdorAppSchema.setReason(CancelReasonContent); // 添加撤销原因，如果原因是其他就保存在此
				cLOBEdorAppSchema.setReasonCode(SCanclReason); //添加撤销原因编码
//				cLOBEdorAppSchema.setMakeDate(theCurrentDate); //为撤销的时间
//				cLOBEdorAppSchema.setMakeTime(theCurrentTime);
				cLOBEdorAppSchema.setModifyDate(theCurrentDate); //为撤销的时间
				cLOBEdorAppSchema.setModifyTime(theCurrentTime);
			}
			mMap.put(cLOBEdorAppSchema, "DELETE&INSERT");
		}
		// 保全撤销不在记录在申请界面的撤销记录，只记录通过菜单的撤销记录
		String sqlItem = "select * from LPEdorItem where edorAcceptNo='"
			+ "?tEdorAcceptNo?" + "'";
		SQLwithBindVariables sbv3=new SQLwithBindVariables();
		sbv3.sql(sqlItem);
		sbv3.put("tEdorAcceptNo", tEdorAcceptNo);
		LOBEdorItemSchema cLOBEdorItemSchema = new LOBEdorItemSchema();
		LPEdorItemSchema cLPEdorItemSchema = new LPEdorItemSchema();
		LOBEdorItemSet cLOBEdorItemSet = new LOBEdorItemSet();
		LPEdorItemDB cLPEdorItemDB = new LPEdorItemDB();
		LPEdorItemSet cLPEdorItemSet = new LPEdorItemSet();
		cLPEdorItemSet = cLPEdorItemDB.executeQuery(sbv3);
		Reflections crf = new Reflections();
		if(cLPEdorItemSet!=null && cLPEdorItemSet.size()>0)
		{
			for (int k = 1; k <= cLPEdorItemSet.size(); k++) {
				cLOBEdorItemSchema = new LOBEdorItemSchema();
				cLPEdorItemSchema = cLPEdorItemSet.get(k);
				crf.transFields(cLOBEdorItemSchema, cLPEdorItemSchema);

				cLOBEdorItemSchema.setReason(CancelReasonContent); // 添加撤销原因，如果原因是其他就保存在此
				cLOBEdorItemSchema.setReasonCode(SCanclReason); //添加撤销原因编码，但是如果在申请界面撤销就没有保存
				//cLOBEdorItemSchema.setMakeDate(theCurrentDate); //为原来保全项目录入的时间
				//cLOBEdorItemSchema.setMakeTime(theCurrentTime);
				cLOBEdorItemSchema.setModifyDate(theCurrentDate); //为撤销的时间
				cLOBEdorItemSchema.setModifyTime(theCurrentTime);
				cLOBEdorItemSet.add(cLOBEdorItemSchema);
			}
			mMap.put(cLOBEdorItemSet, "DELETE&INSERT");
		}	
//		// 增加撤销原因处理
//		if (reasonCode != null && reasonCode.length() > 1) {
//			String[] reasonAtr = reasonCode.split(" ");
//			logger.debug(reasonAtr.length);
//			logger.debug(reasonAtr[0]);
//			LPCancelReasonSet tLPCancelReasonSet = new LPCancelReasonSet();
//			LPCancelReasonSchema tLPCancelReasonSchema = new LPCancelReasonSchema();
//			tLPCancelReasonSchema.setEdorAcceptNo(cLPEdorAppSchema
//					.getEdorAcceptNo());
//			tLPCancelReasonSchema.setReasonCode(reasonAtr[l]);
//			tLPCancelReasonSchema.setMakeDate(theCurrentDate);
//			tLPCancelReasonSchema.setMakeTime(theCurrentTime);
//			tLPCancelReasonSchema.setModifyDate(theCurrentDate);
//			tLPCancelReasonSchema.setModifyTime(theCurrentTime);
//			tLPCancelReasonSchema.setOperator(mGlobalInput.Operator);
//			tLPCancelReasonSet.add(tLPCancelReasonSchema);
//			mMap.put(tLPCancelReasonSet, "DELETE&INSERT");
//		}
		String[] tableForDel = { "LPEdorAppPrint", "LPEdorAppPrint3",
				"LPEdorApp", "LPAppPENoticeItem", "LPAppPENotice",
				"LPAppUWSubMain", "LPAppUWMasterMain" };
		for (int i = 0; i < tableForDel.length; i++) {
			delSql = "delete from  " + tableForDel[i]
					+ " where EdorAcceptNo = '" + "?tEdorAcceptNo?" + "'";
			SQLwithBindVariables sbv4=new SQLwithBindVariables();
			sbv4.sql(delSql);
			sbv4.put("tEdorAcceptNo", tEdorAcceptNo);
			mMap.put(sbv4, "DELETE");
		}
		SQLwithBindVariables sbv5=new SQLwithBindVariables();
		sbv5.sql("delete from LJSGet where otherno='" + "?tEdorAcceptNo?"
				+ "' and othernotype='10'");
		sbv5.put("tEdorAcceptNo", tEdorAcceptNo);
		mMap.put(sbv5, "DELETE");
		SQLwithBindVariables sbv6=new SQLwithBindVariables();
		sbv6.sql("delete from LJSPay where otherno='" + "?tEdorAcceptNo?"
				+ "' and othernotype='10'");
		sbv6.put("tEdorAcceptNo", tEdorAcceptNo);
		mMap.put(sbv6, "DELETE");

		// 保单解挂
		BqContHangUpBL tContHangUpBL = new BqContHangUpBL(mGlobalInput);
		if (!tContHangUpBL.hangUpEdorAccept(mLPEdorAppSchema.getEdorAcceptNo(),
				"0")) {
			CError.buildErr(this, "保全保单解挂失败!");
			return false;
		} else {
			MMap tMap = tContHangUpBL.getMMap();
			mMap.add(tMap); // del because DB hs not update zhangtao 2005-08-02
		}

		return true;
	}

	/**
	 * 准备数据，重新填充数据容器中的内容
	 */
	private void prepareOutputData() {
		// 记录当前操作员
		mResult.clear();
		mResult.add(mMap);
	}

	/**
	 * 取得sql集
	 * 
	 * @return MMap
	 */
	public MMap getMap() {
		return mMap;
	}

	public static void main(String[] args) {
		PEdorAppCancelBL tPEdorAppCancelBL = new PEdorAppCancelBL();
		VData tVData = new VData();
		LPEdorAppSchema tLPEdorAppSchema = new LPEdorAppSchema();
		tLPEdorAppSchema.setEdorAcceptNo("86000000000293");
		tLPEdorAppSchema.setEdorState("1");
		tVData.addElement(tLPEdorAppSchema);
		tPEdorAppCancelBL.submitData(tVData, "DELETE||EDOR");
	}
}
