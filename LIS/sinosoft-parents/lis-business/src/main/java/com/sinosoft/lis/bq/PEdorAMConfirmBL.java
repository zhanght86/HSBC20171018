package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;


import com.sinosoft.lis.db.LCAddressDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDAddressDB;
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
import com.sinosoft.lis.schema.LDAddressSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.schema.LPAppntSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.schema.LPPersonSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vschema.LCAppntSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LPAddressSet;
import com.sinosoft.lis.vschema.LPAppntSet;
import com.sinosoft.lis.vschema.LPInsuredSet;
import com.sinosoft.lis.vschema.LPPersonSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 客户基本信息变更保全申请确认处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author pst
 * @version 1.0
 */
public class PEdorAMConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(PEdorAMConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	//public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	/**  */
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private MMap map = new MMap();
	boolean newaddrFlag = false;
	
	/**本客户原来的地址编号*/
	private String tAddressNo="";
	
	/**新地址编号*/
	private  String aAddressNo = "";

	public PEdorAMConfirmBL() {
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
		mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("---End getInputData---");

		// 数据准备操作
		if (!prepareData()) {
			return false;
		}
		logger.debug("---End prepareData---");
		if (!prepareOutputData()) {
			return false;
		}


		return true;
	}

	public VData getResult() {
		return mResult;
	}

	private boolean prepareOutputData() {

		mResult.clear();
		mResult.add(map);

		return true;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return
	 */
	private boolean getInputData() {
		try {
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
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
		String tCurrentDate = PubFun.getCurrentDate();
		String tCurrentTime = PubFun.getCurrentTime();

		Reflections tRef = new Reflections();

		String tEdorNo = mLPEdorItemSchema.getEdorNo();
		String tEdorType = mLPEdorItemSchema.getEdorType();

		String tContNo = mLPEdorItemSchema.getContNo();
		String tCustomerNo = mLPEdorItemSchema.getInsuredNo();
        //原来的地址号
		tAddressNo= mLPEdorItemSchema.getStandbyFlag2();
	    //新的地址号
		aAddressNo= mLPEdorItemSchema.getStandbyFlag3();

		LPAppntDB tLPAppntDB = new LPAppntDB();
		tLPAppntDB.setContNo(tContNo);
		tLPAppntDB.setEdorNo(tEdorNo);
		tLPAppntDB.setEdorType(tEdorType);
		tLPAppntDB.setAppntNo(tCustomerNo);
		if (tLPAppntDB.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tLPAppntDB.mErrors);
			return false;
		}

		LPInsuredDB tLPInsuredDB = new LPInsuredDB();
		tLPInsuredDB.setEdorType(tEdorType);
		tLPInsuredDB.setEdorNo(tEdorNo);
		tLPInsuredDB.setContNo(tContNo);
		tLPInsuredDB.setInsuredNo(tCustomerNo);
		if (tLPInsuredDB.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tLPInsuredDB.mErrors);
			return false;
		}

		
		
		LCAppntSet afterLCAppntSet = new LCAppntSet();
		LPAppntSet afterLPAppntSet = new LPAppntSet();
		LPAppntSet tempLPAppntSet = new LPAppntSet();

		LCInsuredSet afterLCInsuredSet = new LCInsuredSet();			
		LPInsuredSet afterLPInsuredSet = new LPInsuredSet();
		LPInsuredSet tempLPInsuredSet = new LPInsuredSet();
	


		afterLPInsuredSet = tLPInsuredDB.query();
		if (afterLPInsuredSet.size() > 0) {
			for (int i = 1; i <= afterLPInsuredSet.size(); i++) {
				LCInsuredSchema afterLCInsuredSchema = new LCInsuredSchema();
				tRef.transFields(afterLCInsuredSchema, afterLPInsuredSet.get(i));
				afterLCInsuredSchema.setModifyDate(tCurrentDate);
				afterLCInsuredSchema.setModifyTime(tCurrentTime);
				afterLCInsuredSet.add(afterLCInsuredSchema);
			}
			map.put(afterLCInsuredSet, "UPDATE");

			LCInsuredDB tLCInsuredDB = new LCInsuredDB();
			LCInsuredSet tLCInsuredSet = new LCInsuredSet();
			tLCInsuredDB.setContNo(tContNo);
			tLCInsuredDB.setInsuredNo(tCustomerNo);
			tLCInsuredSet = tLCInsuredDB.query();
			for (int i = 1; i <= tLCInsuredSet.size(); i++) {
				LPInsuredSchema afterLPInsuredSchema = new LPInsuredSchema();
				afterLPInsuredSchema.setEdorNo(tEdorNo);
				afterLPInsuredSchema.setEdorType(tEdorType);
				tRef.transFields(afterLPInsuredSchema, tLCInsuredSet.get(i));
				afterLPInsuredSchema.setModifyDate(tCurrentDate);
				afterLPInsuredSchema.setModifyTime(tCurrentTime);
				tempLPInsuredSet.add(afterLPInsuredSchema);
			}
			map.put(tempLPInsuredSet, "UPDATE");

		}	


		//
		afterLPAppntSet = tLPAppntDB.query();
		if (afterLPAppntSet.size() > 0) {
			for (int i = 1; i <= afterLPAppntSet.size(); i++) {
				LCAppntSchema afterLCAppntSchema = new LCAppntSchema();
				tRef.transFields(afterLCAppntSchema, afterLPAppntSet.get(i));
				afterLCAppntSchema.setModifyDate(tCurrentDate);
				afterLCAppntSchema.setModifyTime(tCurrentTime);
				afterLCAppntSet.add(afterLCAppntSchema);
			}
			map.put(afterLCAppntSet, "UPDATE");

			LCAppntDB tLCAppntDB = new LCAppntDB();
			LCAppntSet tLCAppntSet = new LCAppntSet();
			tLCAppntDB.setContNo(tContNo);
			tLCAppntDB.setAppntNo(tCustomerNo);
			tLCAppntSet = tLCAppntDB.query();
			for (int i = 1; i <= tLCAppntSet.size(); i++) {
				LPAppntSchema afterLPAppntSchema = new LPAppntSchema();
				afterLPAppntSchema.setEdorNo(tEdorNo);
				afterLPAppntSchema.setEdorType(tEdorType);
				tRef.transFields(afterLPAppntSchema, tLCAppntSet.get(i));
				afterLPAppntSchema.setModifyDate(tCurrentDate);
				afterLPAppntSchema.setModifyTime(tCurrentTime);
				tempLPAppntSet.add(afterLPAppntSchema);
			}
			map.put(tempLPAppntSet, "UPDATE");

		}
		
		LPAddressDB tLPAddressDB = new LPAddressDB();
		tLPAddressDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPAddressDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPAddressDB.setAddressNo(aAddressNo);
		tLPAddressDB.setCustomerNo(tCustomerNo);
		LPAddressSet tLPAddressSet = new LPAddressSet();
		tLPAddressSet = tLPAddressDB.query();
		logger.debug("The LPAddress SIZE :" + tLPAddressSet.size());
		for (int j = 1; j <= tLPAddressSet.size(); j++) {
				LCAddressSchema tLCAddressSchema = new LCAddressSchema();
				tRef.transFields(tLCAddressSchema, tLPAddressSet.get(j));
				tLCAddressSchema.setModifyDate(tCurrentDate);
				tLCAddressSchema.setModifyTime(tCurrentTime);
				map.put(tLCAddressSchema, "DELETE&INSERT");
				//删除以前存下
				map.put(tLPAddressSet.get(j), "DELETE");
		}
		
		return true;

	}


	public CErrors getErrors() {
		return mErrors;
	}

}
