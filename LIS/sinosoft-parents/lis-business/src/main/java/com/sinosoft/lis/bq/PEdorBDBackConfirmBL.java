package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContStateDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContStateSchema;
import com.sinosoft.lis.schema.LPContStateSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LPContStateSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全- 保单质押银行贷款清偿回退确认生效处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author：liurx
 * @version：1.0
 * @CreateDate：2005-10-13
 */
public class PEdorBDBackConfirmBL implements EdorBack {
private static Logger logger = Logger.getLogger(PEdorBDBackConfirmBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	//public CErrors mErrors = new CErrors();
	private MMap map = new MMap();
	/** 全局基础数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	// 需要回退的保全项目
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();
	private String mContNo = "";

	public PEdorBDBackConfirmBL() {
	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param cInputData
	 *            传入的数据对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		if (!getInputData()) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		// 准备往后台的数据
		if (!prepareData()) {
			return false;
		}

		return true;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @return boolean
	 */
	private boolean getInputData() {
		try {
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			mLPEdorItemSchema = // 需要回退的保全项目
			(LPEdorItemSchema) mInputData.getObjectByObjectName(
					"LPEdorItemSchema", 0);
		} catch (Exception e) {
			e.printStackTrace();
			CError.buildErr(this, "接收前台数据失败!");
			return false;
		}
		if (mGlobalInput == null || mLPEdorItemSchema == null) {
			CError.buildErr(this, "传入数据有误!");
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		logger.debug("=== 保单质押银行贷款清偿回退确认生效 ===");
		// 其贷款状态结束时间
		String tEndDate = PubFun.calDate(mLPEdorItemSchema.getEdorValiDate(),
				-1, "D", null);
		mContNo = mLPEdorItemSchema.getContNo();
		if (mContNo == null || mContNo.trim().equals("")) {
			CError.buildErr(this, "保单号为空!");
			return false;
		}
		Reflections tRef = null;
		// 查询贷款清偿状态记录
		LCContStateDB tLCContStateDB = new LCContStateDB();
		LCContStateSet tLCContStateSet = new LCContStateSet();
		String strsql = "select * from lccontstate where contno = '"
				+ "?mContNo?"
				+ "' "
				+ " and statetype = 'BankLoan' and state = '0' and enddate is null "
				+ " and startdate = '" + "?startdate?"
				+ "'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(strsql);
		sqlbv.put("mContNo", mContNo);
		sqlbv.put("startdate", mLPEdorItemSchema.getEdorValiDate());
		tLCContStateSet = tLCContStateDB.executeQuery(sqlbv);
		// 如果存在，则删除此记录，并备份到p表中
		if (tLCContStateSet != null && tLCContStateSet.size() > 0) {
			tRef = new Reflections();
			LPContStateSet tLPContStateSet = new LPContStateSet();
			for (int i = 1; i <= tLCContStateSet.size(); i++) {
				LPContStateSchema tLPContStateSchema = new LPContStateSchema();
				tRef.transFields(tLPContStateSchema, tLCContStateSet.get(i));
				tLPContStateSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPContStateSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPContStateSet.add(tLPContStateSchema);
			}
			map.put(tLPContStateSet, "DELETE&INSERT");
			map.put(tLCContStateSet, "DELETE");
		}
		// 查找贷款状态数据，如果存在，则将其备份到p表中，然后置其enddate为空
		// 由于有清偿必有贷款，所以如果贷款状态数据不存在，则肯定是由于同一天做的贷款和清偿，主键
		// 冲突造成贷款数据被删掉，此时贷款的起始日期就等于清偿的起始日期
		LCContStateDB oldLCContStateDB = new LCContStateDB();
		LCContStateSet oldLCContStateSet = new LCContStateSet();
		strsql = "select * from lccontstate where contno = '" + "?mContNo?" + "' "
				+ " and statetype = 'BankLoan' and state = '1' "
				+ " and enddate = '" + "?tEndDate?" + "'";
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(strsql);
		sbv1.put("mContNo", mContNo);
		sbv1.put("tEndDate", tEndDate);
		oldLCContStateSet = oldLCContStateDB.executeQuery(sbv1);
		if (oldLCContStateSet != null && oldLCContStateSet.size() > 0) {
			tRef = new Reflections();
			LPContStateSet oldLPContStateSet = new LPContStateSet();
			LCContStateSet uptLCContStateSet = new LCContStateSet();
			for (int i = 1; i <= oldLCContStateSet.size(); i++) {
				LPContStateSchema tLPContStateSchema = new LPContStateSchema();
				LCContStateSchema tLCContStateSchema = new LCContStateSchema();
				tLCContStateSchema.setSchema(oldLCContStateSet.get(i));
				tRef.transFields(tLPContStateSchema, oldLCContStateSet.get(i));
				tLPContStateSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPContStateSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				oldLPContStateSet.add(tLPContStateSchema);
				tLCContStateSchema.setEndDate("");
				uptLCContStateSet.add(tLCContStateSchema);
			}
			map.put(oldLPContStateSet, "DELETE&INSERT");
			map.put(uptLCContStateSet, "UPDATE");
		} else {
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			strsql = "select * from lpedoritem where contno = '"
					+ "?mContNo?"
					+ "' "
					+ " and edortype = 'BL' and edorstate = '0' and edorvalidate <= '"
					+ "?edorvalidate?" + "' "
					+ " and rownum = 1 order by edorvalidate desc ";
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				strsql = "select * from lpedoritem where contno = '"
						+ "?mContNo?"
						+ "' "
						+ " and edortype = 'BL' and edorstate = '0' and edorvalidate <= '"
						+ "?edorvalidate?" + "' "
						+ " order by edorvalidate desc limit 0,1";	
			}
			sbv1=new SQLwithBindVariables();
			sbv1.sql(strsql);
			sbv1.put("mContNo", mContNo);
			sbv1.put("edorvalidate", mLPEdorItemSchema.getEdorValiDate());
			LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
			LPEdorItemSet tLPEdorItemset = new LPEdorItemSet();
			tLPEdorItemset = tLPEdorItemDB.executeQuery(sbv1);
			if (tLPEdorItemset == null || tLPEdorItemset.size() <= 0) {
				CError.buildErr(this, "无该保单质押银行贷款数据，无法进行清偿回退!");
				return false;
			}
			LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema.setSchema(tLPEdorItemset.get(1));

			LCContStateSchema cLCContStateSchema = new LCContStateSchema();
			cLCContStateSchema.setContNo(mContNo);
			cLCContStateSchema.setInsuredNo("000000");
			cLCContStateSchema.setPolNo("000000");
			cLCContStateSchema
					.setStartDate(tLPEdorItemSchema.getEdorValiDate());
			cLCContStateSchema.setEndDate("");
			cLCContStateSchema.setStateType("BankLoan");
			cLCContStateSchema.setState("1");
			cLCContStateSchema.setOperator(tLPEdorItemSchema.getOperator());
			cLCContStateSchema.setMakeDate(tLPEdorItemSchema.getMakeDate());
			cLCContStateSchema.setMakeTime(tLPEdorItemSchema.getMakeTime());
			cLCContStateSchema.setModifyDate(mCurrentDate);
			cLCContStateSchema.setModifyTime(mCurrentTime);
			cLCContStateSchema.setStateReason("");
			cLCContStateSchema.setRemark("");

			LPContStateSchema cLPContStateSchema = new LPContStateSchema();
			tRef.transFields(cLPContStateSchema, cLCContStateSchema);
			cLPContStateSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			cLPContStateSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			FDate fdate = new FDate();
			if (fdate.getDate(tEndDate).before(
					fdate.getDate(tLPEdorItemSchema.getEdorValiDate()))) {
				cLPContStateSchema.setEndDate(tLPEdorItemSchema
						.getEdorValiDate());
			} else {
				cLPContStateSchema.setEndDate(tEndDate);
			}
			map.put(cLCContStateSchema, "DELETE&INSERT");
			map.put(cLPContStateSchema, "DELETE&INSERT");
		}
		return true;
	}

	/**
	 * 准备往后层输出所需要的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareData() {
		mResult.clear();
		mResult.add(map);
		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return null;
	}

}
