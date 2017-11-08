package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPCustomerImpartDB;
import com.sinosoft.lis.db.LPCustomerImpartParamsDB;
import com.sinosoft.lis.db.LPDutyDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPGetDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.db.LPPremDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCCustomerImpartParamsSchema;
import com.sinosoft.lis.schema.LCCustomerImpartSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCCustomerImpartParamsSet;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPCustomerImpartParamsSet;
import com.sinosoft.lis.vschema.LPCustomerImpartSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 增补健康告知回退确认BL
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author pst
 * @version 1.0
 */

public class PEdorHIBackConfirmBL implements EdorBack {
private static Logger logger = Logger.getLogger(PEdorHIBackConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	//public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap mMap = new MMap();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	/** 业务数据 */
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	private GlobalInput mGlobalInput = new GlobalInput();

	private LPContSet mLPContSet = new LPContSet();
	private LCContSet mLCContSet = new LCContSet();
	private LPPolSet mLPPolSet = new LPPolSet();
	private LCPolSet mLCPolSet = new LCPolSet();
	private LPDutySet mLPDutySet = new LPDutySet();
	private LCDutySet mLCDutySet = new LCDutySet();
	private LPPremSet mLPPremSet = new LPPremSet();
	private LCPremSet mLCPremSet = new LCPremSet();
	private LPGetSet mLPGetSet = new LPGetSet();
	private LCGetSet mLCGetSet = new LCGetSet();

	private Reflections mReflections = new Reflections();
	
	private ValidateEdorData2 mValidateEdorData = null;

	public PEdorHIBackConfirmBL() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括""和""
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("---End getInputData---");

		// 得到外部传入的数据,将数据备份到本类中
		if (!checkData()) {
			return false;
		}
		logger.debug("---End checkData---");

		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("---End dealData---");

		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return this.mErrors;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			// 需要回退的保全项目
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
		} catch (Exception e) {
			e.printStackTrace();
			return this.makeError("getInputData", "接收前台数据失败！");
		}
		if (mGlobalInput == null || mLPEdorItemSchema == null) {
			return this.makeError("getInputData", "传入数据有误！");
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
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		try {
			// 获得此时的日期和时间
			String strCurrentDate = PubFun.getCurrentDate();
			String strCurrentTime = PubFun.getCurrentTime();

			LPCustomerImpartDB tLPCustomerImpartDB = null;
			LPCustomerImpartSet tLPCustomerImpartSet = new LPCustomerImpartSet();
			LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
			LCCustomerImpartSchema tLCCustomerImpartSchema = null;
			LPCustomerImpartParamsDB tLPCustomerImpartParamsDB = null;
			LPCustomerImpartParamsSet tLPCustomerImpartParamsSet = new LPCustomerImpartParamsSet();
			LCCustomerImpartParamsSet tLCCustomerImpartParamsSet = new LCCustomerImpartParamsSet();
			LCCustomerImpartParamsSchema tLCCustomerImpartParamsSchema = null;

				// 交换客户告知表数据
				tLPCustomerImpartDB = new LPCustomerImpartDB();
				tLPCustomerImpartDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPCustomerImpartDB
						.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPCustomerImpartSet = tLPCustomerImpartDB.query();
				if (tLPCustomerImpartSet!= null
						|| tLPCustomerImpartSet.size() > 0) {
					for (int i = 1; i <= tLPCustomerImpartSet.size(); i++) {
						tLCCustomerImpartSchema = new LCCustomerImpartSchema();
						mReflections.transFields(tLCCustomerImpartSchema,
								tLPCustomerImpartSet.get(i));
						tLCCustomerImpartSchema
								.setOperator(this.mGlobalInput.Operator);
						tLCCustomerImpartSchema.setModifyDate(strCurrentDate);
						tLCCustomerImpartSchema.setModifyTime(strCurrentTime);
						tLCCustomerImpartSet.add(tLCCustomerImpartSchema);
				}
				}

				// 交换客户告知参数表数据
				tLPCustomerImpartParamsDB = new LPCustomerImpartParamsDB();
				tLPCustomerImpartParamsDB.setEdorNo(mLPEdorItemSchema
						.getEdorNo());
				tLPCustomerImpartParamsDB.setEdorType(mLPEdorItemSchema
						.getEdorType());
				tLPCustomerImpartParamsSet = tLPCustomerImpartParamsDB.query();
				if (tLPCustomerImpartParamsSet != null
						&& tLPCustomerImpartParamsSet.size() > 0) {
					for (int i = 1; i <= tLPCustomerImpartParamsSet.size(); i++) {
						tLCCustomerImpartParamsSchema = new LCCustomerImpartParamsSchema();
						mReflections.transFields(tLCCustomerImpartParamsSchema,
								tLPCustomerImpartParamsSet.get(i));
						tLCCustomerImpartParamsSchema
								.setOperator(this.mGlobalInput.Operator);
						tLCCustomerImpartParamsSchema.setModifyDate(strCurrentDate);
						tLCCustomerImpartParamsSchema.setModifyTime(strCurrentTime);
						tLCCustomerImpartParamsSet
								.add(tLCCustomerImpartParamsSchema);
					}

				}
				
			    mValidateEdorData = new ValidateEdorData2(mGlobalInput, mLPEdorItemSchema.getEdorNo(),mLPEdorItemSchema.getEdorType(), mLPEdorItemSchema.getContNo(), "ContNo");
				//采用新的方式进行保全数据回退
			    String[] chgTables = {"LCCont","LCPol","LCDuty","LCGet","LCAppnt","LCInsured","LCCSpec"};
			    mValidateEdorData.backConfirmData(chgTables);
			    mMap.add(mValidateEdorData.getMap());
			    
			    //处理保费表
			    mMap.add(BqNameFun.DealPrem4BackConfirm(mLPEdorItemSchema));
			    
//			    // ***2005-08-30日新增***由于人工核保时可能会加费，所以这里将P表相应数据换回C表
//				if (!changeCPData(mLPEdorItemSchema.getEdorNo(),
//						mLPEdorItemSchema.getEdorType())) {
//					return false;
//				}
			mMap.put(tLCCustomerImpartSet, "DELETE");
			mMap.put(tLCCustomerImpartParamsSet, "DELETE");
//			mMap.put(mLPContSet, "DELETE&INSERT");
//			mMap.put(mLCContSet, "DELETE&INSERT");
//			mMap.put(mLPPolSet, "DELETE&INSERT");
//			mMap.put(mLCPolSet, "DELETE&INSERT");
//			mMap.put(mLPDutySet, "DELETE&INSERT");
//			mMap.put(mLCDutySet, "DELETE&INSERT");
//			mMap.put(mLPPremSet, "DELETE&INSERT");
//			mMap.put(mLCPremSet, "DELETE&INSERT");
//			mMap.put(mLPGetSet, "DELETE&INSERT");
//			mMap.put(mLCGetSet, "DELETE&INSERT");

			mResult.clear();
			mResult.add(mMap);
		} catch (Exception e) {
			// @@错误处理
			return this.makeError("dealData", "数据处理错误！" + e.getMessage());
		}
		return true;
	}

	// --将P表的一组表与C表的一组表交换的函数结果存入一组private的变量里
	private boolean changeCPData(String vEdorNo, String vEdorType) {
		// Cont表
		LPContDB tLPContDB = new LPContDB();
		LCContDB tLCContDB = new LCContDB();
		tLPContDB.setEdorNo(vEdorNo);
		tLPContDB.setEdorType(vEdorType);
		LPContSet tLPContSet = new LPContSet();
		tLPContSet = tLPContDB.query();
		if (tLPContSet == null || tLPContSet.size() <= 0) {
			this.makeError("changeCPData", "查询保全保单表时产生错误！EdorNo=" + vEdorNo);
			return false;
		}
		for (int i = 1; i <= tLPContSet.size(); i++) {
			LCContSchema tLCContSchema = new LCContSchema();
			mReflections.transFields(tLCContSchema, tLPContSet.get(i));
			mLCContSet.add(tLCContSchema);
			// 查询C表匹配数据交换到P表
			tLCContDB.setContNo(tLCContSchema.getContNo());
			if (tLCContDB.getInfo()) {
				LPContSchema tLPContSchema = new LPContSchema();
				mReflections.transFields(tLPContSchema, tLCContDB.getSchema());
				tLPContSchema.setEdorNo(vEdorNo);
				tLPContSchema.setEdorType(vEdorType);
				mLPContSet.add(tLPContSchema);
			}
		}

		// Pol表
		LPPolDB tLPPolDB = new LPPolDB();
		LCPolDB tLCPolDB = new LCPolDB();
		tLPPolDB.setEdorNo(vEdorNo);
		tLPPolDB.setEdorType(vEdorType);
		LPPolSet tLPPolSet = new LPPolSet();
		tLPPolSet = tLPPolDB.query();
		if (tLPPolSet == null || tLPPolSet.size() <= 0) {
			this.makeError("changeCPData", "查询保全险种表时产生错误！EdorNo=" + vEdorNo);
			return false;
		}
		for (int i = 1; i <= tLPPolSet.size(); i++) {
			LCPolSchema tLCPolSchema = new LCPolSchema();
			mReflections.transFields(tLCPolSchema, tLPPolSet.get(i));
			mLCPolSet.add(tLCPolSchema);
			// 查询C表匹配数据交换到P表
			tLCPolDB.setPolNo(tLCPolSchema.getPolNo());
			if (tLCPolDB.getInfo()) {
				LPPolSchema tLPPolSchema = new LPPolSchema();
				mReflections.transFields(tLPPolSchema, tLCPolDB.getSchema());
				tLPPolSchema.setEdorNo(vEdorNo);
				tLPPolSchema.setEdorType(vEdorType);
				mLPPolSet.add(tLPPolSchema);
			}
		}

		// Duty表
		LPDutyDB tLPDutyDB = new LPDutyDB();
		LCDutyDB tLCDutyDB = new LCDutyDB();
		tLPDutyDB.setEdorNo(vEdorNo);
		tLPDutyDB.setEdorType(vEdorType);
		LPDutySet tLPDutySet = new LPDutySet();
		tLPDutySet = tLPDutyDB.query();
		if (tLPDutySet == null || tLPDutySet.size() <= 0) {
			this.makeError("changeCPData", "查询保全责任表时产生错误！EdorNo=" + vEdorNo);
			return false;
		}
		for (int i = 1; i <= tLPDutySet.size(); i++) {
			LCDutySchema tLCDutySchema = new LCDutySchema();
			mReflections.transFields(tLCDutySchema, tLPDutySet.get(i));
			mLCDutySet.add(tLCDutySchema);
			// 查询C表匹配数据交换到P表
			tLCDutyDB.setPolNo(tLCDutySchema.getPolNo());
			tLCDutyDB.setDutyCode(tLCDutySchema.getDutyCode());
			if (tLCDutyDB.getInfo()) {
				LPDutySchema tLPDutySchema = new LPDutySchema();
				mReflections.transFields(tLPDutySchema, tLCDutyDB.getSchema());
				tLPDutySchema.setEdorNo(vEdorNo);
				tLPDutySchema.setEdorType(vEdorType);
				mLPDutySet.add(tLPDutySchema);
			}
		}

		// Prem表
		LPPremDB tLPPremDB = new LPPremDB();
		LCPremDB tLCPremDB = new LCPremDB();
		tLPPremDB.setEdorNo(vEdorNo);
		tLPPremDB.setEdorType(vEdorType);
		LPPremSet tLPPremSet = new LPPremSet();
		tLPPremSet = tLPPremDB.query();
		if (tLPPremSet == null || tLPPremSet.size() <= 0) {
			this.makeError("changeCPData", "查询保全保费项表时产生错误！EdorNo=" + vEdorNo);
			return false;
		}
		for (int i = 1; i <= tLPPremSet.size(); i++) {
			LCPremSchema tLCPremSchema = new LCPremSchema();
			mReflections.transFields(tLCPremSchema, tLPPremSet.get(i));
			mLCPremSet.add(tLCPremSchema);
			// 查询C表匹配数据交换到P表
			tLCPremDB.setPolNo(tLCPremSchema.getPolNo());
			tLCPremDB.setDutyCode(tLCPremSchema.getDutyCode());
			tLCPremDB.setPayPlanCode(tLCPremSchema.getPayPlanCode());
			if (tLCPremDB.getInfo()) {
				LPPremSchema tLPPremSchema = new LPPremSchema();
				mReflections.transFields(tLPPremSchema, tLCPremDB.getSchema());
				tLPPremSchema.setEdorNo(vEdorNo);
				tLPPremSchema.setEdorType(vEdorType);
				mLPPremSet.add(tLPPremSchema);
			}
		}

		// Get表
		LPGetDB tLPGetDB = new LPGetDB();
		LCGetDB tLCGetDB = new LCGetDB();
		tLPGetDB.setEdorNo(vEdorNo);
		tLPGetDB.setEdorType(vEdorType);
		LPGetSet tLPGetSet = new LPGetSet();
		tLPGetSet = tLPGetDB.query();
		if (tLPGetSet == null || tLPGetSet.size() <= 0) {
			this.makeError("changeCPData", "查询保全领取项表时产生错误！EdorNo=" + vEdorNo);
			return false;
		}
		for (int i = 1; i <= tLPGetSet.size(); i++) {
			LCGetSchema tLCGetSchema = new LCGetSchema();
			mReflections.transFields(tLCGetSchema, tLPGetSet.get(i));
			mLCGetSet.add(tLCGetSchema);
			// 查询C表匹配数据交换到P表
			tLCGetDB.setPolNo(tLCGetSchema.getPolNo());
			tLCGetDB.setDutyCode(tLCGetSchema.getDutyCode());
			tLCGetDB.setGetDutyCode(tLCGetSchema.getGetDutyCode());
			if (tLCGetDB.getInfo()) {
				LPGetSchema tLPGetSchema = new LPGetSchema();
				mReflections.transFields(tLPGetSchema, tLCGetDB.getSchema());
				tLPGetSchema.setEdorNo(vEdorNo);
				tLPGetSchema.setEdorType(vEdorType);
				mLPGetSet.add(tLPGetSchema);
			}
		}
		return true;
	}

	/**
	 * 创建一个错误
	 * 
	 * @param vFuncName
	 *            发生错误的函数名
	 * @param vErrMsg
	 *            错误信息
	 * @return 布尔值（false--永远返回此值）
	 */
	private boolean makeError(String vFuncName, String vErrMsg) {
		CError tError = new CError();
		tError.moduleName = "PEdorHIBackConfirmBL";
		tError.functionName = vFuncName;
		tError.errorMessage = vErrMsg;
		this.mErrors.addOneError(tError);
		return false;
	}

	public static void main(String[] args) {
		PEdorHIBackConfirmBL tPEdorHIBackConfirmBL = new PEdorHIBackConfirmBL();
	}
}
