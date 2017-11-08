package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCInsureAccClassDB;
import com.sinosoft.lis.db.LCInsureAccClassFeeDB;
import com.sinosoft.lis.db.LCInsureAccDB;
import com.sinosoft.lis.db.LCInsureAccFeeDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LPAppntDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPGetDB;
import com.sinosoft.lis.db.LPInsureAccClassDB;
import com.sinosoft.lis.db.LPInsureAccClassFeeDB;
import com.sinosoft.lis.db.LPInsureAccDB;
import com.sinosoft.lis.db.LPInsureAccFeeDB;
import com.sinosoft.lis.db.LPInsuredDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.db.LPPremDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCInsureAccClassFeeSchema;
import com.sinosoft.lis.schema.LCInsureAccClassSchema;
import com.sinosoft.lis.schema.LCInsureAccFeeSchema;
import com.sinosoft.lis.schema.LCInsureAccSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LPAppntSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPInsureAccClassFeeSchema;
import com.sinosoft.lis.schema.LPInsureAccClassSchema;
import com.sinosoft.lis.schema.LPInsureAccFeeSchema;
import com.sinosoft.lis.schema.LPInsureAccSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCInsureAccClassFeeSet;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LCInsureAccFeeSet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPInsureAccClassFeeSet;
import com.sinosoft.lis.vschema.LPInsureAccClassSet;
import com.sinosoft.lis.vschema.LPInsureAccFeeSet;
import com.sinosoft.lis.vschema.LPInsureAccSet;
import com.sinosoft.lis.vschema.LPInsuredSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 保全回退处理
 * </p>
 * 
 * <p>
 * Description: 保单迁移回退处理确认类
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Lizhuo
 * @version 1.0
 * @CreateDate 2005-10-10
 */
public class PEdorPRBackConfirmBL implements EdorBack {
private static Logger logger = Logger.getLogger(PEdorPRBackConfirmBL.class);
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

	private String CurrDate = PubFun.getCurrentDate();
	private String CurrTime = PubFun.getCurrentTime();
	// 需要回退的保全项目
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	public PEdorPRBackConfirmBL() {
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
		logger.debug("=== 保单迁移回退确认生效 ===");
		Reflections tRef = new Reflections();

		LPContSchema tLPContSchema = new LPContSchema();
		LCContSchema tLCContSchema = new LCContSchema();

		LPContDB tLPContDB = new LPContDB();
		tLPContDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLPContDB.getInfo()) {
			CError.buildErr(this, "查询原保单信息失败!");
			return false;
		}
		tRef.transFields(tLCContSchema, tLPContDB.getSchema());
		tLCContSchema.setModifyDate(CurrDate);
		tLCContSchema.setModifyTime(CurrTime);

		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			CError.buildErr(this, "查询保单信息失败!");
			return false;
		}
		tRef.transFields(tLPContSchema, tLCContDB.getSchema());
		tLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPContSchema.setModifyDate(CurrDate);
		tLPContSchema.setModifyTime(CurrTime);
		map.put(tLCContSchema, "UPDATE");
		map.put(tLPContSchema, "UPDATE");

		// 对LPAppnt表和LCAppnt表的互换，Add By QianLy ----------
		LPAppntSchema tLPAppntSchema = new LPAppntSchema();
		LCAppntSchema tLCAppntSchema = new LCAppntSchema();

		LPAppntDB tLPAppntDB = new LPAppntDB();
		tLPAppntDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPAppntDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPAppntDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLPAppntDB.getInfo()) {
			CError.buildErr(this, "查询原保单信息失败!");
			return false;
		}
		tRef.transFields(tLCAppntSchema, tLPAppntDB.getSchema());
		tLCAppntSchema.setModifyDate(CurrDate);
		tLCAppntSchema.setModifyTime(CurrTime);

		LCAppntDB tLCAppntDB = new LCAppntDB();
		tLCAppntDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCAppntDB.getInfo()) {
			CError.buildErr(this, "查询保单信息失败!");
			return false;
		}
		tRef.transFields(tLPAppntSchema, tLCAppntDB.getSchema());
		tLPAppntSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPAppntSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPAppntSchema.setModifyDate(CurrDate);
		tLPAppntSchema.setModifyTime(CurrTime);
		map.put(tLCAppntSchema, "UPDATE");
		map.put(tLPAppntSchema, "UPDATE");
		// -----------------------------------------------------end

		LCPolSet aLCPolSet = new LCPolSet();
		LPPolSet aLPPolSet = new LPPolSet();

		LPPolDB tLPPolDB = new LPPolDB();
		LPPolSet tLPPolSet = new LPPolSet();
		tLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPPolDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPPolSet = tLPPolDB.query();
		if (tLPPolSet.size() < 1) {
			CError.buildErr(this, "查询险种表出错!");
			return false;
		}
		for (int i = 1; i <= tLPPolSet.size(); i++) {
			LCPolSchema tLCPolSchema = new LCPolSchema();
			tRef.transFields(tLCPolSchema, tLPPolSet.get(i));
			tLCPolSchema.setModifyDate(CurrDate);
			tLCPolSchema.setModifyTime(CurrTime);
			aLCPolSet.add(tLCPolSchema);

			LCPolDB tLCPolDB = new LCPolDB();
			LPPolSchema tLPPolSchema = new LPPolSchema();
			tLCPolDB.setPolNo(tLCPolSchema.getPolNo());
			if (!tLCPolDB.getInfo()) {
				mErrors.copyAllErrors(tLCPolDB.mErrors);
				mErrors.addOneError(new CError("查询险种保单表失败！"));
				return false;
			}
			tRef.transFields(tLPPolSchema, tLCPolDB.getSchema());
			tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPPolSchema.setModifyDate(CurrDate);
			tLPPolSchema.setModifyTime(CurrTime);
			aLPPolSet.add(tLPPolSchema);

			LCPremSet aLCPremSet = new LCPremSet();
			LPPremSet aLPPremSet = new LPPremSet();

			LPPremDB tLPPremDB = new LPPremDB();
			LPPremSet tLPPremSet = new LPPremSet();
			tLPPremDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPPremDB.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPPremDB.setPolNo(tLCPolSchema.getPolNo());
			tLPPremSet = tLPPremDB.query();
			if (tLPPremSet.size() > 0) {
				for (int j = 1; j <= tLPPremSet.size(); j++) {
					LCPremSchema tLCPremSchema = new LCPremSchema();
					tRef.transFields(tLCPremSchema, tLPPremSet.get(j));
					tLCPremSchema.setModifyDate(CurrDate);
					tLCPremSchema.setModifyTime(CurrTime);
					aLCPremSet.add(tLCPremSchema);
				}
				LCPremDB tLCPremDB = new LCPremDB();
				LCPremSet tLCPremSet = new LCPremSet();
				tLCPremDB.setPolNo(tLCPolSchema.getPolNo());
				tLCPremSet = tLCPremDB.query();
				for (int j = 1; j <= tLCPremSet.size(); j++) {
					LPPremSchema tLPPremSchema = new LPPremSchema();
					tRef.transFields(tLPPremSchema, tLCPremSet.get(j));
					tLPPremSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
					tLPPremSchema.setEdorType(mLPEdorItemSchema.getEdorType());
					tLPPremSchema.setModifyDate(CurrDate);
					tLPPremSchema.setModifyTime(CurrTime);
					aLPPremSet.add(tLPPremSchema);
				}
				map.put(aLCPremSet, "UPDATE");
				map.put(aLPPremSet, "UPDATE");
			}

			// Get
			LCGetSet aLCGetSet = new LCGetSet();
			LPGetSet aLPGetSet = new LPGetSet();

			LPGetDB tLPGetDB = new LPGetDB();
			LPGetSet tLPGetSet = new LPGetSet();
			tLPGetDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPGetDB.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPGetDB.setPolNo(tLCPolSchema.getPolNo());
			tLPGetSet = tLPGetDB.query();
			if (tLPGetSet.size() > 0) {
				for (int j = 1; j <= tLPGetSet.size(); j++) {
					LCGetSchema tLCGetSchema = new LCGetSchema();
					tRef
							.transFields(tLCGetSchema, tLPGetSet.get(j)
									.getSchema());
					tLCGetSchema.setModifyDate(CurrDate);
					tLCGetSchema.setModifyTime(CurrTime);
					aLCGetSet.add(tLCGetSchema);
				}
				LCGetDB tLCGetDB = new LCGetDB();
				LCGetSet tLCGetSet = new LCGetSet();
				tLCGetDB.setPolNo(tLCPolSchema.getPolNo());
				tLCGetSet = tLCGetDB.query();
				if (tLCGetSet.size() < 1) {
					CError.buildErr(this, "查询领取项信息失败!");
					return false;
				}
				for (int j = 1; j <= tLCGetSet.size(); j++) {
					LPGetSchema tLPGetSchema = new LPGetSchema();
					tRef
							.transFields(tLPGetSchema, tLCGetSet.get(j)
									.getSchema());
					tLPGetSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
					tLPGetSchema.setEdorType(mLPEdorItemSchema.getEdorType());
					tLPGetSchema.setModifyDate(CurrDate);
					tLPGetSchema.setModifyTime(CurrTime);
					aLPGetSet.add(tLPGetSchema);
				}
				map.put(aLCGetSet, "UPDATE");
				map.put(aLPGetSet, "UPDATE");
			}
		}
		map.put(aLCPolSet, "UPDATE");
		map.put(aLPPolSet, "UPDATE");

		// Insured
		LCInsuredSet aLCInsuredSet = new LCInsuredSet();
		LPInsuredSet aLPInsuredSet = new LPInsuredSet();

		LPInsuredDB tLPInsuredDB = new LPInsuredDB();
		LPInsuredSet tLPInsuredSet = new LPInsuredSet();
		tLPInsuredDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPInsuredDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPInsuredDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPInsuredSet = tLPInsuredDB.query();
		for (int j = 1; j <= tLPInsuredSet.size(); j++) {
			LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
			LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();

			tRef.transFields(tLCInsuredSchema, tLPInsuredSet.get(j));
			tLCInsuredSchema.setModifyDate(CurrDate);
			tLCInsuredSchema.setModifyTime(CurrTime);
			aLCInsuredSet.add(tLCInsuredSchema);

			LCInsuredDB tLCInsuredDB = new LCInsuredDB();
			tLCInsuredDB.setContNo(tLCInsuredSchema.getContNo());
			tLCInsuredDB.setInsuredNo(tLCInsuredSchema.getInsuredNo());
			if (!tLCInsuredDB.getInfo()) {
				mErrors.copyAllErrors(tLCInsuredDB.mErrors);
				mErrors.addOneError(new CError("查询被保人信息失败！"));
				return false;
			}
			tRef.transFields(tLPInsuredSchema, tLCInsuredSchema.getSchema());
			tLPInsuredSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPInsuredSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPInsuredSchema.setModifyDate(CurrDate);
			tLPInsuredSchema.setModifyTime(CurrTime);
			aLPInsuredSet.add(tLPInsuredSchema);
			map.put(aLCInsuredSet, "UPDATE");
			map.put(aLPInsuredSet, "UPDATE");
		}

		// 保险帐户管理费表
		LCInsureAccFeeSet aLCInsureAccFeeSet = new LCInsureAccFeeSet();
		LPInsureAccFeeSet aLPInsureAccFeeSet = new LPInsureAccFeeSet();

		LPInsureAccFeeDB tLPInsureAccFeeDB = new LPInsureAccFeeDB();
		LPInsureAccFeeSet tLPInsureAccFeeSet = new LPInsureAccFeeSet();
		tLPInsureAccFeeDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPInsureAccFeeDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPInsureAccFeeDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPInsureAccFeeSet = tLPInsureAccFeeDB.query();
		LCInsureAccFeeSchema tLCInsureAccFeeSchema;
		LPInsureAccFeeSchema tLPInsureAccFeeSchema;
		for (int i = 1; i <= tLPInsureAccFeeSet.size(); i++) {
			tLCInsureAccFeeSchema = new LCInsureAccFeeSchema();
			tRef.transFields(tLCInsureAccFeeSchema, tLPInsureAccFeeSet.get(i));
			tLCInsureAccFeeSchema.setModifyDate(CurrDate);
			tLCInsureAccFeeSchema.setModifyTime(CurrTime);
			aLCInsureAccFeeSet.add(tLCInsureAccFeeSchema);

			LCInsureAccFeeDB tLCInsureAccFeeDB = new LCInsureAccFeeDB();
			tLCInsureAccFeeDB.setPolNo(tLCInsureAccFeeSchema.getPolNo());
			tLCInsureAccFeeDB
					.setInsuAccNo(tLCInsureAccFeeSchema.getInsuAccNo());
			if (!tLCInsureAccFeeDB.getInfo()) {
				CError.buildErr(this, "查询保险帐户管理费表信息失败!");
				return false;
			}
			tLPInsureAccFeeSchema = new LPInsureAccFeeSchema();
			tRef.transFields(tLPInsureAccFeeSchema, tLCInsureAccFeeDB
					.getSchema());
			tLPInsureAccFeeSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPInsureAccFeeSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPInsureAccFeeSchema.setModifyDate(CurrDate);
			tLPInsureAccFeeSchema.setModifyTime(CurrTime);
			aLPInsureAccFeeSet.add(tLPInsureAccFeeSchema);
			map.put(aLCInsureAccFeeSet, "UPDATE");
			map.put(aLPInsureAccFeeSet, "UPDATE");
		}

		// 保险账户管理费分类表
		LCInsureAccClassFeeSet aLCInsureAccClassFeeSet = new LCInsureAccClassFeeSet();
		LPInsureAccClassFeeSet aLPInsureAccClassFeeSet = new LPInsureAccClassFeeSet();

		LPInsureAccClassFeeDB tLPInsureAccClassFeeDB = new LPInsureAccClassFeeDB();
		LPInsureAccClassFeeSet tLPInsureAccClassFeeSet = new LPInsureAccClassFeeSet();
		tLPInsureAccClassFeeDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPInsureAccClassFeeDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPInsureAccClassFeeDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPInsureAccClassFeeSet = tLPInsureAccClassFeeDB.query();
		LCInsureAccClassFeeSchema tLCInsureAccClassFeeSchema;
		LPInsureAccClassFeeSchema tLPInsureAccClassFeeSchema;
		for (int i = 1; i <= tLPInsureAccClassFeeSet.size(); i++) {
			tLCInsureAccClassFeeSchema = new LCInsureAccClassFeeSchema();
			tRef.transFields(tLCInsureAccClassFeeSchema,
					tLPInsureAccClassFeeSet.get(i));
			tLCInsureAccClassFeeSchema.setModifyDate(CurrDate);
			tLCInsureAccClassFeeSchema.setModifyTime(CurrTime);
			aLCInsureAccClassFeeSet.add(tLCInsureAccClassFeeSchema);

			LCInsureAccClassFeeDB tLCInsureAccClassFeeDB = new LCInsureAccClassFeeDB();
			tLCInsureAccClassFeeDB.setSchema(tLCInsureAccClassFeeSchema);
			if (!tLCInsureAccClassFeeDB.getInfo()) {
				CError.buildErr(this, "查询保险账户管理费分类表信息失败!");
				return false;
			}
			tLPInsureAccClassFeeSchema = new LPInsureAccClassFeeSchema();
			tRef.transFields(tLPInsureAccClassFeeSchema, tLCInsureAccClassFeeDB
					.getSchema());
			tLPInsureAccClassFeeSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPInsureAccClassFeeSchema.setEdorType(mLPEdorItemSchema
					.getEdorType());
			tLPInsureAccClassFeeSchema.setModifyDate(CurrDate);
			tLPInsureAccClassFeeSchema.setModifyTime(CurrTime);
			aLPInsureAccClassFeeSet.add(tLPInsureAccClassFeeSchema);
			map.put(aLCInsureAccClassFeeSet, "UPDATE");
			map.put(aLPInsureAccClassFeeSet, "UPDATE");
		}

		// 保险帐户表
		LCInsureAccSet aLCInsureAccSet = new LCInsureAccSet();
		LPInsureAccSet aLPInsureAccSet = new LPInsureAccSet();

		LPInsureAccDB tLPInsureAccDB = new LPInsureAccDB();
		LPInsureAccSet tLPInsureAccSet = new LPInsureAccSet();
		tLPInsureAccDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPInsureAccDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPInsureAccDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPInsureAccSet = tLPInsureAccDB.query();
		LCInsureAccSchema tLCInsureAccSchema;
		LPInsureAccSchema tLPInsureAccSchema;
		for (int i = 1; i <= tLPInsureAccSet.size(); i++) {
			tLCInsureAccSchema = new LCInsureAccSchema();
			tRef.transFields(tLCInsureAccSchema, tLPInsureAccSet.get(i));
			tLCInsureAccSchema.setModifyDate(CurrDate);
			tLCInsureAccSchema.setModifyTime(CurrTime);
			aLCInsureAccSet.add(tLCInsureAccSchema);

			LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
			tLCInsureAccDB.setSchema(tLCInsureAccSchema);
			if (!tLCInsureAccDB.getInfo()) {
				CError.buildErr(this, "查询保险帐户表信息失败!");
				return false;
			}
			tLPInsureAccSchema = new LPInsureAccSchema();
			tRef.transFields(tLPInsureAccSchema, tLCInsureAccDB.getSchema());
			tLPInsureAccSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPInsureAccSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPInsureAccSchema.setModifyDate(CurrDate);
			tLPInsureAccSchema.setModifyTime(CurrTime);
			aLPInsureAccSet.add(tLPInsureAccSchema);
			map.put(aLCInsureAccSet, "UPDATE");
			map.put(aLPInsureAccSet, "UPDATE");
		}

		// 保险账户分类表
		LCInsureAccClassSet aLCInsureAccClassSet = new LCInsureAccClassSet();
		LPInsureAccClassSet aLPInsureAccClassSet = new LPInsureAccClassSet();

		LPInsureAccClassDB tLPInsureAccClassDB = new LPInsureAccClassDB();
		LPInsureAccClassSet tLPInsureAccClassSet = new LPInsureAccClassSet();
		tLPInsureAccClassDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPInsureAccClassDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPInsureAccClassDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPInsureAccClassSet = tLPInsureAccClassDB.query();
		LCInsureAccClassSchema tLCInsureAccClassSchema;
		LPInsureAccClassSchema tLPInsureAccClassSchema;
		for (int i = 1; i <= tLPInsureAccClassSet.size(); i++) {
			tLCInsureAccClassSchema = new LCInsureAccClassSchema();
			tRef.transFields(tLCInsureAccClassSchema, tLPInsureAccClassSet
					.get(i));
			tLCInsureAccClassSchema.setModifyDate(CurrDate);
			tLCInsureAccClassSchema.setModifyTime(CurrTime);
			aLCInsureAccClassSet.add(tLCInsureAccClassSchema);

			LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
			tLCInsureAccClassDB.setSchema(tLCInsureAccClassSchema);
			if (!tLCInsureAccClassDB.getInfo()) {
				CError.buildErr(this, "查询保险账户分类表信息失败!");
				return false;
			}
			tLPInsureAccClassSchema = new LPInsureAccClassSchema();
			tRef.transFields(tLPInsureAccClassSchema, tLCInsureAccClassDB
					.getSchema());
			tLPInsureAccClassSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPInsureAccClassSchema
					.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPInsureAccClassSchema.setModifyDate(CurrDate);
			tLPInsureAccClassSchema.setModifyTime(CurrTime);
			aLPInsureAccClassSet.add(tLPInsureAccClassSchema);
			map.put(aLCInsureAccClassSet, "UPDATE");
			map.put(aLPInsureAccClassSet, "UPDATE");
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
		return mErrors;
	}

	public static void main(String[] args) {
		PEdorPRBackConfirmBL pedorprbackconfirmbl = new PEdorPRBackConfirmBL();
	}
}
