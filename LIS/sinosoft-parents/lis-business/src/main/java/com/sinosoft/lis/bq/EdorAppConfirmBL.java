package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPEdorMainDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 个单保全申请确认
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
public class EdorAppConfirmBL {
private static Logger logger = Logger.getLogger(EdorAppConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 全局数据 */
	MMap map = new MMap();

	private GlobalInput mGlobalInput = new GlobalInput();
	private LPEdorMainSchema mLPEdorMainSchema = new LPEdorMainSchema();
	private LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet();

	public EdorAppConfirmBL() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 * @param cOperate
	 * @return
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("---End getInputData---");

		// 数据准备操作,查询个人保全主表中，该保单处于申请状态的保全数据
		if (!prepareData()) {
			return false;
		}
		logger.debug("---End prepareData---");

		// 数据操作业务处理
		if (!dealData()) {
			return false;
		}

		// 数据提交、保存
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("---End prepareData---");

		return true;
	}

	private boolean prepareOutputData() {

		mInputData.clear();
		mInputData.add(map);
		mResult.clear();
		mResult.add(map);

		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return
	 */
	public boolean dealData() {
		if (mOperate.equals("INSERT||EDORAPPCONFIRM")
				|| mOperate.equals("INSERT||GEDORAPPCONFIRM")) {
			for (int i = 1; i <= mLPEdorItemSet.size(); i++) {
				LPEdorItemSchema mLPEdorItemSchema = mLPEdorItemSet.get(i);

				// 校验个单保全申请的明细信息是否完整 暂时注掉 by pq
				// if (!checkData(mLPEdorItemSchema))return false;
				// logger.debug("End 校验该个单保全申请的明细信息是否完整");

				String edortype = mLPEdorItemSchema.getEdorType();
				try {
					Class tClass = Class.forName("com.sinosoft.lis.bq.PEdor"
							+ edortype + "AppConfirmBL");
					EdorAppConfirm tPEdorAppConfirm = (EdorAppConfirm) tClass
							.newInstance();
					VData tVData = new VData();

					tVData.add(mLPEdorItemSchema);
					tVData.add(mGlobalInput);

					if (!tPEdorAppConfirm.submitData(tVData, "APPCONFIRM||"
							+ edortype)) {

						CError.buildErr(this, "处理保单号为："
								+ mLPEdorItemSchema.getPolNo() + "保全项目为:"
								+ edortype + "的保全申请确认时失败！");
						return false;
					} else {
						VData rVData = tPEdorAppConfirm.getResult();
						MMap tMap = (MMap) rVData.getObjectByObjectName("MMap",
								0);
						if (tMap == null) {
							CError.buildErr(this, "得到保单号为："
									+ mLPEdorItemSchema.getPolNo() + "保全项目为:"
									+ edortype + "的保全申请确认结果时失败！");
							return false;
						} else {
							map.add(tMap);
						}
					}
				} catch (ClassNotFoundException ex) {
					// 添加默认处理方法
					logger.debug("====PEdor" + edortype
							+ "AppConfirmBL not found====");
				} catch (Exception ex) {
					ex.printStackTrace();
					CError.buildErr(this, "调用保全项目" + edortype + "的申请确认方法时失败！");
					return false;
				}
				mLPEdorItemSchema.setEdorState("2");
				mLPEdorItemSchema.setUWFlag("0");
			}
			String tEdorNo = mLPEdorMainSchema.getEdorNo();
			String tContNo = mLPEdorMainSchema.getContNo();
			// 下面要增加处理个人批单主表的逻辑
			mLPEdorMainSchema.setEdorState("2");
			mLPEdorMainSchema.setUWState("0");
			mLPEdorMainSchema.setConfOperator(mGlobalInput.Operator);
			mLPEdorMainSchema.setConfDate(PubFun.getCurrentDate());
			mLPEdorMainSchema.setConfTime(PubFun.getCurrentTime());
			mLPEdorMainSchema.setOperator(mGlobalInput.Operator);
			mLPEdorMainSchema.setModifyDate(PubFun.getCurrentDate());
			mLPEdorMainSchema.setModifyTime(PubFun.getCurrentTime());
			map.put(mLPEdorMainSchema, "UPDATE");
			String wherePart = "where EdorNo='?tEdorNo?' and ContNo='?tContNo?'";
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql("update LPEdorMain set ChgPrem= (select sum(ChgPrem) from LPEdorItem "
					+ wherePart + ") " + wherePart);
			sqlbv1.put("tEdorNo", tEdorNo);
			sqlbv1.put("tContNo", tContNo);
			map.put(sqlbv1, "UPDATE");
			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
			sqlbv2.sql("update LPEdorMain set ChgAmnt= (select sum(ChgAmnt) from LPEdorItem "
					+ wherePart + ") " + wherePart);
			sqlbv2.put("tEdorNo", tEdorNo);
			sqlbv2.put("tContNo", tContNo);
			map.put(sqlbv2, "UPDATE");
			SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
			sqlbv3.sql("update LPEdorMain set GetMoney= (select sum(GetMoney) from LPEdorItem "
					+ wherePart + ") " + wherePart);
			sqlbv3.put("tEdorNo", tEdorNo);
			sqlbv3.put("tContNo", tContNo);
			map.put(sqlbv3, "UPDATE");
			SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
			sqlbv4.sql("update LPEdorMain set GetInterest= (select sum(GetInterest) from LPEdorItem "
					+ wherePart + ") " + wherePart);
			sqlbv4.put("tEdorNo", tEdorNo);
			sqlbv4.put("tContNo", tContNo);
			map.put(sqlbv4, "UPDATE");
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
	 * 从输入数据中得到所有对象
	 * 
	 * @return
	 */
	private boolean getInputData() {
		try {
			mLPEdorMainSchema = (LPEdorMainSchema) mInputData
					.getObjectByObjectName("LPEdorMainSchema", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.errorMessage = "接收数据失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 校验个单保全申请的明细信息是否完整
	 * 
	 * @return
	 */
	private boolean checkData(LPEdorItemSchema tLPEdorItemSchema) {
		VerifyEndorsement tVer = new VerifyEndorsement();
		VData tVData = new VData();
		tVData.addElement(tLPEdorItemSchema);

		if (!tVer.verifyEdorDetail(tVData, "VERIFY||DETAIL")) {
			this.mErrors.copyAllErrors(tVer.mErrors);
			return false;
		} else {
			if (tVer.getResult() != null && tVer.getResult().size() > 0) {
				String tErr = "";
				for (int i = 0; i < tVer.getResult().size(); i++) {
					tErr = tErr + (String) tVer.getResult().get(i);
				}

				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "VerifyEndorsement";
				tError.functionName = "verifyEdorDetail";
				tError.errorMessage = tErr;
				this.mErrors.addOneError(tError);
				return false;
			}
		}

		return true;
	}

	/**
	 * 准备需要保存的数据,查询个人保全主表中，该保单处于申请状态的保全数据
	 * 
	 * @return
	 */
	private boolean prepareData() {
		LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
		tLPEdorMainDB.setSchema(mLPEdorMainSchema);
		tLPEdorMainDB.getInfo();
		if (tLPEdorMainDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询个人批改主表失败！");
			return false;
		}

		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		// 查询个人保全主表中，该保单处于申请状态的保全数据
		String strSql = " select * from LPEdorItem "
				+ " where EdorState='1' and EdorNo='"
				+ "?EdorNo?" + "' and contno='"
				+ "?contno?"
				+ "' order by Makedate,MakeTime";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(strSql);
		sqlbv.put("EdorNo", mLPEdorMainSchema.getEdorNo());
		sqlbv.put("contno", mLPEdorMainSchema.getContNo());
		mLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv);

		return true;
	}

}
