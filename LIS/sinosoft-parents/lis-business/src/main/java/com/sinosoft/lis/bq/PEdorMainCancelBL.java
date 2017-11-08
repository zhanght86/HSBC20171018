package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPEdorMainDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCDelPolLogSchema;
import com.sinosoft.lis.schema.LOBEdorMainSchema;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPEdorMainSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:团单整单删除BL层
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @author zhangrong modify by Alex
 * @version 1.0
 */
public class PEdorMainCancelBL {
private static Logger logger = Logger.getLogger(PEdorMainCancelBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	/** 撤销申请原因 */
	private	String CancelReasonContent = "";
	private	String 	SCanclReason = "";


	/** 传输到后台处理的map */
	private MMap mMap = new MMap();

	/** 数据操作字符串 */
	private String mOperate;
	private String mOperator;
	private String mManageCom;

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	/** 业务处理相关变量 */
	private GlobalInput mGlobalInput = new GlobalInput();
	// private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();
	private LCDelPolLogSchema mLCDelPolLog = new LCDelPolLogSchema();

	private LPEdorMainSchema mLPEdorMainSchema = new LPEdorMainSchema(); //
	private TransferData tTransferData = new TransferData();
	// 统一更新日期，时间
	private String theCurrentDate = PubFun.getCurrentDate();
	private String theCurrentTime = PubFun.getCurrentTime();

	public PEdorMainCancelBL() {
	}

	/**
	 * 处理实际的业务逻辑。
	 * 
	 * @param cInputData
	 *            VData 从前台接收的表单数据
	 * @param cOperate
	 *            String 操作字符串
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将数据取到本类变量中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		if (!getInputData()) {
			return false;
		}

		if (!dealData()) {
			return false;
		}

		prepareOutputData();

		return true;
	}

	/**
	 * 将UI层传输来得数据根据业务还原成具体的类
	 * 
	 * @return boolean
	 */
	private boolean getInputData() {
		// 全局变量实例
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);

		tTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		// 撤销申请原因
		this.CancelReasonContent = (String) tTransferData.getValueByName("CancelReasonContent");
		this.SCanclReason = (String) tTransferData
		.getValueByName("SCanclReason");
		
		if (mGlobalInput == null) {
			mErrors.addOneError(new CError("没有得到全局量信息"));
			return false;
		}

		// 团体保单实例
		mLPEdorMainSchema.setSchema((LPEdorMainSchema) mInputData
				.getObjectByObjectName("LPEdorMainSchema", 0));

		if (mLPEdorMainSchema == null) {
			this.mErrors.addOneError(new CError("传入的信息不完全！"));

			return false;
		}

		// 根据已有数据取得数据库中数据
		LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
		tLPEdorMainDB.setEdorNo(mLPEdorMainSchema.getEdorNo());
		tLPEdorMainDB.setContNo(mLPEdorMainSchema.getContNo());
		tLPEdorMainDB.setEdorAcceptNo(mLPEdorMainSchema.getEdorAcceptNo());
		if (!tLPEdorMainDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "PEdorMainCancelBL";
			tError.functionName = "dealData";
			tError.errorMessage = "没有相应的批单信息,请确认传递的数据的完整性!";
			this.mErrors.addOneError(tError);
			logger.debug(tError);
			return false;

		}
		mLPEdorMainSchema = tLPEdorMainDB.getSchema();

		return true;
	}

	/**
	 * 对业务数据进行加工 对于新增的操作，这里需要有生成新合同号和新客户号的操作。
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		String delSql;
		String tEdorNo = mLPEdorMainSchema.getEdorNo();

		// 删除批单下的保全项目
		LPEdorItemSet aLPEdorItemSet = new LPEdorItemSet();
		LPEdorItemDB aLPEdorItemDB = new LPEdorItemDB();
		aLPEdorItemDB.setEdorAcceptNo(mLPEdorMainSchema.getEdorAcceptNo());
		aLPEdorItemDB.setEdorNo(mLPEdorMainSchema.getEdorNo());
		aLPEdorItemDB.setContNo(mLPEdorMainSchema.getContNo());
		aLPEdorItemSet = aLPEdorItemDB.query();
		if (aLPEdorItemSet != null && aLPEdorItemSet.size() != 0) {
			for (int i = 1; i <= aLPEdorItemSet.size(); i++) {
				VData tVData = new VData();
				tVData.add(aLPEdorItemSet.get(i));
				tVData.add(mGlobalInput);
				tVData.addElement(tTransferData);
				PEdorItemCancelBL tPEdorItemCancelBL = new PEdorItemCancelBL();
				if (!tPEdorItemCancelBL.submitData(tVData, "EDORMAIN")) {
					CError tError = new CError();
					tError.moduleName = "PEdorItemCancelBL";
					tError.functionName = "dealData";
					tError.errorMessage = "删除保全项目失败!可能的原因是该批单下有确认的项目.只能删除没有确认的项目!";
					this.mErrors.addOneError(tError);
					// logger.debug(tError);
					return false;
				} else {
					mMap.add(tPEdorItemCancelBL.getMap());
				}

			}
		}

		// 将将数据备份，并存储备份信息
		LPEdorMainSchema cLPEdorMainSchema = new LPEdorMainSchema();
		LOBEdorMainSchema cLOBEdorMainSchema = new LOBEdorMainSchema();
		LPEdorMainDB cLPEdorMainDB = new LPEdorMainDB();
		LPEdorMainSet cLPEdorMainSet = new LPEdorMainSet();

		// 选出当前记录
		String sql = "select * from LpedorMain where edorNo='?tEdorNo?'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("tEdorNo", tEdorNo);
		cLPEdorMainSet = cLPEdorMainDB.executeQuery(sqlbv);

		if(cLPEdorMainSet.size()< 1)
		{
			CError tError = new CError();
			tError.moduleName = "PEdorMainCancelBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询保全项目主表失败";
			this.mErrors.addOneError(tError);
			// logger.debug(tError);
			return false;
		}
		cLPEdorMainSchema = cLPEdorMainSet.get(1);
		logger.debug("cLPEdorMainSchema:" + cLPEdorMainSchema.getContNo()); // 测试用
		Reflections crf = new Reflections();
		crf.transFields(cLOBEdorMainSchema, cLPEdorMainSchema); // 将一条记录整体复制
         
		//只记录保全撤销的原因，但仍然备份
		cLOBEdorMainSchema.setReason(CancelReasonContent); // 添加撤销原因，如果原因是其他就保存在此
		cLOBEdorMainSchema.setReasonCode(SCanclReason); //添加撤销原因编码
		cLOBEdorMainSchema.setMakeDate(theCurrentDate); // 设置修改时间为当前时间
		cLOBEdorMainSchema.setMakeTime(theCurrentTime);
		// cLOBEdorItemSet.add(cLOBEdorItemSchema);

		mMap.put(cLOBEdorMainSchema, "DELETE&INSERT");

		sumChang(mLPEdorMainSchema.getEdorAcceptNo());

		// 删除批改补退费表中相关记录
		delSql = "delete from  LJSGetEndorse where EndorsementNo = '?tEdorNo?'";
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(delSql);
		sbv1.put("tEdorNo", tEdorNo);
		mMap.put(sbv1, "DELETE");
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql("delete from LpedorMain where edorNo='?tEdorNo?'");
		sbv2.put("tEdorNo", tEdorNo);
		mMap.put(sbv2,"DELETE");

		// 如果是团体保全,撤销个人单的同时解挂该个人单
		sql = " delete from lcconthangupstate "
				+ " where exists (select 'X' from lcgrpcont where grpcontno = (select grpcontno from lccont c where c.contno = lcconthangupstate.contno)) "
				+ " and hanguptype = '2' and contno = '?contno?'";
		SQLwithBindVariables sbv3=new SQLwithBindVariables();
		sbv3.sql(sql);
		sbv3.put("contno", mLPEdorMainSchema.getContNo());
		mMap.put(sbv3, "DELETE");

		return true;
	}

	/**
	 * 统计变动保费、变动保额、补/退费金额、补/退费利息
	 * 
	 * @return boolean
	 */
	private void sumChang(String pEdorAcceptNo) {
		String wherePartApp = "where EdorAcceptNo='?pEdorAcceptNo?'";
		StringBuffer sbSQL = new StringBuffer();
		// 保全申请层
		sbSQL.setLength(0);
		sbSQL.append(" UPDATE LPEdorapp set ").append(
				" ChgPrem =(select sum(ChgPrem) from LPEdorItem ").append(
				wherePartApp).append("), ").append(
				" ChgAmnt=(select sum(ChgAmnt) from LPEdorItem ").append(
				wherePartApp).append("), ").append(
				" GetMoney=(select sum(GetMoney) from LPEdorItem ").append(
				wherePartApp).append("), ").append(
				" GetInterest=(select sum(GetInterest) from LPEdorItem ")
				.append(wherePartApp).append(") ").append(wherePartApp);
		SQLwithBindVariables sbv=new SQLwithBindVariables();
		sbv.sql(sbSQL.toString());
		sbv.put("pEdorAcceptNo", pEdorAcceptNo);
		mMap.put(sbv, "UPDATE");

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
	 * 操作结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	public MMap getMap() {
		return mMap;
	}

}
