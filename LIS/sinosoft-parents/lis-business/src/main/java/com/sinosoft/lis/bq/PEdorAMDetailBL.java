package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LPAddressBL;
import com.sinosoft.lis.bl.LPAppntBL;
import com.sinosoft.lis.bl.LPContBL;
import com.sinosoft.lis.bl.LPInsuredBL;
import com.sinosoft.lis.bl.LPPersonBL;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPAddressSchema;
import com.sinosoft.lis.schema.LPAppntSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.schema.LPPersonSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vdb.LDPersonDBSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LDPersonSet;
import com.sinosoft.lis.vschema.LPAddressSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPInsuredSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 客户联系方式基本信息变更
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
public class PEdorAMDetailBL {
private static Logger logger = Logger.getLogger(PEdorAMDetailBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	private MMap mMap = new MMap();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/**客户身份标志，投保人还是被保人，0，投保人，1，被保人，2，两种兼之*/
	private String tAppntIsInsuredFlag = "";
	
	/**本客户原来的地址编号*/
	private String tAddressNo="";
	
	/**新地址编号*/
	private  String aAddressNo = "";

	/** 计算要素 */
	private BqCalBase mBqCalBase = new BqCalBase();

	/** 全局数据 */
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	private LPInsuredSchema mLPInsuredSchema = new LPInsuredSchema();

	private LPAppntSchema mLPAppntSchema = new LPAppntSchema();

	private LPAddressSchema mLPAddressSchema = new LPAddressSchema();
	private LPAddressSet mLPAddressSet = new LPAddressSet();
	
	private GlobalInput mGlobalInput = new GlobalInput();
	
	// 获得此时的日期和时间
	private String strCurrentDate = PubFun.getCurrentDate();
	private String strCurrentTime = PubFun.getCurrentTime();

	public PEdorAMDetailBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据
	 * @param: cOperate 数据操作
	 * @return:
	 */

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据
		if (!getInputData()) {
			return false;
		}

		logger.debug("after getInputData...");

		// 数据校验
		if (!checkData()) {
			return false;
		}

		logger.debug("after checkData...");

		// 数据操作业务处理
		if (!dealData()) {
			return false;
		}

		logger.debug("after dealData...");

//		PubSubmit tSubmit = new PubSubmit();
//		if (!tSubmit.submitData(mResult, "")) {
//			// @@错误处理
//			this.mErrors.copyAllErrors(tSubmit.mErrors);
//		}
		return true;
	}

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
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
			mLPAddressSchema = (LPAddressSchema) mInputData
			.getObjectByObjectName("LPAddressSchema", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);

			if (mLPEdorItemSchema == null || mLPAddressSchema==null) {
				return false;
			}
			tAppntIsInsuredFlag = mLPEdorItemSchema.getStandbyFlag1();
			tAddressNo= mLPEdorItemSchema.getStandbyFlag2();
			// 获得mLPEdorItemSchema的其它信息
			LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
			tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
			tLPEdorItemDB.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
			tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
			mLPEdorItemSchema.setSchema(tLPEdorItemDB.query().get(1));
			
			mLPInsuredSchema.setContNo(mLPEdorItemSchema.getContNo());
		} catch (Exception e) {
			CError.buildErr(this, "接收数据失败！");
			return false;
		}

		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean dealData() {

		
		LPAddressBL tLPAddressBL = new LPAddressBL();
		LPAddressSchema tLPAddressSchema = new LPAddressSchema();
		tLPAddressSchema.setEdorNo(this.mLPEdorItemSchema.getEdorNo());
		tLPAddressSchema.setEdorType(this.mLPEdorItemSchema.getEdorType());
		tLPAddressSchema.setCustomerNo(this.mLPEdorItemSchema.getInsuredNo());
		tLPAddressSchema.setAddressNo(tAddressNo);
		if (!tLPAddressBL.queryLastLPAddress(this.mLPEdorItemSchema, tLPAddressSchema)) {
			return false;
		}
		tLPAddressSchema.setSchema(tLPAddressBL.getSchema());
		


        ExeSQL tExeSQL= new ExeSQL();
		String i_sql = "";
		i_sql = "select (case when Max(AddressNo) is not null then Max(AddressNo) else 0 end) + 1 from LCAddress where CustomerNo = '"
				+ "?CustomerNo?" + "'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(i_sql);
		sqlbv.put("CustomerNo", mLPEdorItemSchema.getInsuredNo());
		aAddressNo = tExeSQL.getOneValue(sqlbv).toString();
		// 设置地址表信息
		logger.debug("新的地址编码:"+aAddressNo);
		
		tLPAddressSchema.setAddressNo(aAddressNo);
		tLPAddressSchema.setPostalAddress(mLPAddressSchema.getPostalAddress());
		tLPAddressSchema.setZipCode(mLPAddressSchema.getZipCode());
		tLPAddressSchema.setHomeAddress(mLPAddressSchema.getHomeAddress());
		tLPAddressSchema.setHomeZipCode(mLPAddressSchema.getHomeZipCode());
		tLPAddressSchema.setMobile(mLPAddressSchema.getMobile()); //首选电话
		tLPAddressSchema.setEMail(mLPAddressSchema.getEMail());
		tLPAddressSchema.setGrpName(mLPAddressSchema.getGrpName());
		tLPAddressSchema.setPhone(mLPAddressSchema.getPhone()); //其他电话

		tLPAddressSchema.setOperator(this.mGlobalInput.Operator);
		tLPAddressSchema.setModifyDate(strCurrentDate);
		tLPAddressSchema.setModifyTime(strCurrentTime);
		mMap.put(tLPAddressSchema, "DELETE&INSERT"); // 加入Map


		
		// 准备要更新的LPInsured表的最新信息
		LPInsuredBL tLPInsuredBL = new LPInsuredBL();
		LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
		if ("1".equals(tAppntIsInsuredFlag)) {
			tLPInsuredSchema.setEdorNo(this.mLPEdorItemSchema.getEdorNo());
			tLPInsuredSchema.setEdorType(this.mLPEdorItemSchema.getEdorType());
			tLPInsuredSchema.setContNo(this.mLPEdorItemSchema.getContNo());
			tLPInsuredSchema.setInsuredNo(this.mLPEdorItemSchema.getInsuredNo());
			if (!tLPInsuredBL.queryLastLPInsured(this.mLPEdorItemSchema,
					tLPInsuredSchema)) {
				return false;
			}
			tLPInsuredSchema = tLPInsuredBL.getSchema();
			// 更新保全相关信息
			tLPInsuredSchema.setAddressNo(aAddressNo);
			tLPInsuredSchema.setOperator(this.mGlobalInput.Operator);
			tLPInsuredSchema.setModifyDate(strCurrentDate);
			tLPInsuredSchema.setModifyTime(strCurrentTime);
			mMap.put(tLPInsuredSchema, "DELETE&INSERT");		
			
		}

		LPAppntBL tLPAppntBL = new LPAppntBL();
		LPAppntSchema tLPAppntSchema = new LPAppntSchema();

		if ("0".equals(tAppntIsInsuredFlag)) {
			tLPAppntSchema.setEdorNo(this.mLPEdorItemSchema.getEdorNo());
			tLPAppntSchema.setEdorType(this.mLPEdorItemSchema.getEdorType());
			tLPAppntSchema.setContNo(this.mLPEdorItemSchema.getContNo());
			tLPAppntSchema.setAppntNo(this.mLPEdorItemSchema.getInsuredNo());
			if (!tLPAppntBL.queryLastLPAppnt(this.mLPEdorItemSchema,
					tLPAppntSchema)) {
				return false;
			}
			tLPAppntSchema.setSchema(tLPAppntBL.getSchema());

			tLPAppntSchema.setAddressNo(aAddressNo);
			tLPAppntSchema.setOperator(this.mGlobalInput.Operator);
			tLPAppntSchema.setModifyDate(strCurrentDate);
			tLPAppntSchema.setModifyTime(strCurrentTime);
			mMap.put(tLPAppntSchema, "DELETE&INSERT");			
		}

		if ("2".equals(tAppntIsInsuredFlag))
		{
			tLPInsuredSchema.setEdorNo(this.mLPEdorItemSchema.getEdorNo());
			tLPInsuredSchema.setEdorType(this.mLPEdorItemSchema.getEdorType());
			tLPInsuredSchema.setContNo(this.mLPEdorItemSchema.getContNo());
			tLPInsuredSchema.setInsuredNo(this.mLPEdorItemSchema.getInsuredNo());
			if (!tLPInsuredBL.queryLastLPInsured(this.mLPEdorItemSchema,
					tLPInsuredSchema)) {
				return false;
			}

			tLPInsuredSchema = tLPInsuredBL.getSchema();
			// 更新保全相关信息
			tLPInsuredSchema.setAddressNo(aAddressNo);
			tLPInsuredSchema.setOperator(this.mGlobalInput.Operator);
			tLPInsuredSchema.setModifyDate(strCurrentDate);
			tLPInsuredSchema.setModifyTime(strCurrentTime);
			mMap.put(tLPInsuredSchema, "DELETE&INSERT");

			tLPAppntSchema.setEdorNo(this.mLPEdorItemSchema.getEdorNo());
			tLPAppntSchema.setEdorType(this.mLPEdorItemSchema.getEdorType());
			tLPAppntSchema.setContNo(this.mLPEdorItemSchema.getContNo());
			tLPAppntSchema.setAppntNo(this.mLPEdorItemSchema.getInsuredNo());
			if (!tLPAppntBL.queryLastLPAppnt(this.mLPEdorItemSchema,
					tLPAppntSchema)) {
				return false;
			}
			tLPAppntSchema.setSchema(tLPAppntBL.getSchema());

			tLPAppntSchema.setAddressNo(aAddressNo);
			tLPAppntSchema.setOperator(this.mGlobalInput.Operator);
			tLPAppntSchema.setModifyDate(strCurrentDate);
			tLPAppntSchema.setModifyTime(strCurrentTime);
			mMap.put(tLPAppntSchema, "DELETE&INSERT");			
		}

		// 修改“个险保全项目表”相应信息
		mLPEdorItemSchema.setEdorState("3");
		mLPEdorItemSchema.setOperator(this.mGlobalInput.Operator);
		mLPEdorItemSchema.setStandbyFlag1(tAppntIsInsuredFlag);
		//存放旧的地址号
		mLPEdorItemSchema.setStandbyFlag2(tAddressNo);
		//存放新的地址号
		mLPEdorItemSchema.setStandbyFlag3(aAddressNo);
		mLPEdorItemSchema.setModifyDate(strCurrentDate);
		mLPEdorItemSchema.setModifyTime(strCurrentTime);
		mMap.put(mLPEdorItemSchema, "UPDATE");

		mResult.clear();
		mResult.add(mMap);
		mBqCalBase.setContNo(mLPEdorItemSchema.getContNo());
		mBqCalBase.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
		mResult.add(mBqCalBase);

		return true;
	}

}
