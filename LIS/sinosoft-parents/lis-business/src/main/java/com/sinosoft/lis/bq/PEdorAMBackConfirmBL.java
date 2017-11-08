package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCAddressDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.db.LPAddressDB;
import com.sinosoft.lis.db.LPAppntDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPInsuredDB;
import com.sinosoft.lis.db.LPPersonDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.schema.LPAddressSchema;
import com.sinosoft.lis.schema.LPAppntSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPPersonSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LPAddressSet;
import com.sinosoft.lis.vschema.LPPersonSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 保全回退处理
 * </p>
 * 
 * <p>
 * Description: 客户基本信息变更保全回退确认类
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
 * @author pst
 * @CreateDate 2008-11-18
 * @version 1.0
 */
public class PEdorAMBackConfirmBL implements EdorBack {
private static Logger logger = Logger.getLogger(PEdorAMBackConfirmBL.class);
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
	/**客户身份标志，投保人还是被保人，0，投保人，1，被保人，2，两种兼之*/
	private String tAppntIsInsuredFlag = "";
	
	/**本客户原来的地址编号*/
	private String tAddressNo="";
	
	/**新地址编号*/
	private  String aAddressNo = "";
	// 需要回退的保全项目
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	public PEdorAMBackConfirmBL() {
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
			tAppntIsInsuredFlag = mLPEdorItemSchema.getStandbyFlag1();
			tAddressNo= mLPEdorItemSchema.getStandbyFlag2();
			aAddressNo= mLPEdorItemSchema.getStandbyFlag3();
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
		logger.debug("=== 被保人基本信息变更回退确认生效 ===");
		Reflections tRef = new Reflections();

		String tCustomerNo = mLPEdorItemSchema.getInsuredNo();
		// Cont

		LCAppntSchema tLCAppntSchema = new LCAppntSchema();
		LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
		if ("0".equals(tAppntIsInsuredFlag)) //投保人
		{

			LPAppntDB tLPAppntDB = new LPAppntDB();
			tLPAppntDB.setContNo(mLPEdorItemSchema.getContNo());
			tLPAppntDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPAppntDB.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPAppntDB.setAppntNo(tCustomerNo);
			if (tLPAppntDB.getInfo()) {
				tRef.transFields(tLCAppntSchema, tLPAppntDB.getSchema());
				tLCAppntSchema.setModifyDate(CurrDate);
				tLCAppntSchema.setModifyTime(CurrTime);
				map.put(tLCAppntSchema, "DELETE&INSERT");
			}	
		}else if ("1".equals(tAppntIsInsuredFlag))//被保人
		{
			LPInsuredDB tLPInsuredDB = new LPInsuredDB();
			tLPInsuredDB.setContNo(mLPEdorItemSchema.getContNo());
			tLPInsuredDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPInsuredDB.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPInsuredDB.setInsuredNo(tCustomerNo);
			if (tLPInsuredDB.getInfo()) {
				tRef.transFields(tLCInsuredSchema, tLPInsuredDB.getSchema());
				tLCInsuredSchema.setModifyDate(CurrDate);
				tLCInsuredSchema.setModifyTime(CurrTime);
				map.put(tLCInsuredSchema, "DELETE&INSERT");
			}
		}
		else if ("2".equals(tAppntIsInsuredFlag))//投被保人兼之
		{
			LPInsuredDB tLPInsuredDB = new LPInsuredDB();
			tLPInsuredDB.setContNo(mLPEdorItemSchema.getContNo());
			tLPInsuredDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPInsuredDB.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPInsuredDB.setInsuredNo(tCustomerNo);
			if (tLPInsuredDB.getInfo()) {
				tRef.transFields(tLCInsuredSchema, tLPInsuredDB.getSchema());
				tLCInsuredSchema.setModifyDate(CurrDate);
				tLCInsuredSchema.setModifyTime(CurrTime);
				map.put(tLCInsuredSchema, "DELETE&INSERT");
			}
			LPAppntDB tLPAppntDB = new LPAppntDB();
			tLPAppntDB.setContNo(mLPEdorItemSchema.getContNo());
			tLPAppntDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPAppntDB.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPAppntDB.setAppntNo(tCustomerNo);
			if (tLPAppntDB.getInfo()) {
				tRef.transFields(tLCAppntSchema, tLPAppntDB.getSchema());
				tLCAppntSchema.setModifyDate(CurrDate);
				tLCAppntSchema.setModifyTime(CurrTime);
				map.put(tLCAppntSchema, "DELETE&INSERT");
			}	
		}

		//回退处理	
		LCAddressDB tLCAddressDB = new LCAddressDB();
		tLCAddressDB.setCustomerNo(mLPEdorItemSchema.getInsuredNo());
		tLCAddressDB.setAddressNo(aAddressNo);
		if (tLCAddressDB.getInfo()) {
			//map.put(tLCAddressSchema, "DELETE&INSERT");
			map.put(tLCAddressDB.getSchema(), "DELETE");
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
		PEdorAMBackConfirmBL PEdorAMBackConfirmBL = new PEdorAMBackConfirmBL();
	}
}
