package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LJAGetDB;
import com.sinosoft.lis.db.LJAGetDrawDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPGetDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LJAGetDrawSchema;
import com.sinosoft.lis.schema.LJAGetSchema;
import com.sinosoft.lis.schema.LJSGetDrawSchema;
import com.sinosoft.lis.schema.LJSGetSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LJAGetDrawSet;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.lis.vschema.LJSGetDrawSet;
import com.sinosoft.lis.vschema.LJSGetSet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 年金，满期金给付回退确认BL
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Nicholas
 * @version 1.0
 */

public class PEdorAGBackConfirmBL implements EdorBack {
private static Logger logger = Logger.getLogger(PEdorAGBackConfirmBL.class);
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
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	public PEdorAGBackConfirmBL() {
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
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setSchema(mLPEdorItemSchema);
		if (!tLPEdorItemDB.getInfo()) {
			// @@错误处理
			return this.makeError("checkData", "无保全数据！");
		}

		mLPEdorItemSchema.setSchema(tLPEdorItemDB.getSchema());
		if (!"0".equals(tLPEdorItemDB.getEdorState())) {
			// @@错误处理
			return this.makeError("checkData", "此项目尚未确认生效！");
		}
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

			// 数据交换工具
			Reflections tRef = new Reflections();

			// 恢复LCGet表和LJSGetDraw表数据
			// 说明：目前此项目不会增加LCGet表的数据，所以认为P表和C表一一对应
			// 要更新数据声明
			LCGetSet tLCGetSet = new LCGetSet();
			LPGetSet tLPGetSet = new LPGetSet();
			LJSGetDrawSet tLJSGetDrawSet = new LJSGetDrawSet();
			// LJAGetDrawSet tLJAGetDrawSet = new LJAGetDrawSet();
			LJSGetSet tLJSGetSet = new LJSGetSet();
			LJAGetSet tLJAGetSet = new LJAGetSet();
			// 查询P表数据
			LPGetDB tLPGetDB = new LPGetDB();
			tLPGetDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPGetDB.setEdorType(mLPEdorItemSchema.getEdorType());
			LPGetSet tempLPGetSet = tLPGetDB.query();
			if (tempLPGetSet == null || tempLPGetSet.size() <= 0) {
				return this.makeError("dealData", "查询保全领取项表数据失败！");
			}
			LCGetDB tLCGetDB = new LCGetDB();
			LJAGetDrawDB tLJAGetDrawDB = new LJAGetDrawDB();
			LJAGetDB tLJAGetDB = new LJAGetDB();
			for (int i = 1; i <= tempLPGetSet.size(); i++) {
				LCGetSchema tLCGetSchema = new LCGetSchema();
				tRef.transFields(tLCGetSchema, tempLPGetSet.get(i));
				tLCGetSchema.setOperator(mGlobalInput.Operator);
				tLCGetSchema.setModifyDate(strCurrentDate);
				tLCGetSchema.setModifyTime(strCurrentTime);
				tLCGetSet.add(tLCGetSchema);
				// 查询C表对应数据更新到P表
//				tLCGetDB.setPolNo(tLCGetSchema.getPolNo());
//				tLCGetDB.setDutyCode(tLCGetSchema.getDutyCode());
//				tLCGetDB.setGetDutyCode(tLCGetSchema.getGetDutyCode());
//				if (tLCGetDB.getInfo()) {
//					LPGetSchema tLPGetSchema = new LPGetSchema();
//					tRef.transFields(tLPGetSchema, tLCGetDB.getSchema());
//					tLPGetSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
//					tLPGetSchema.setEdorType(mLPEdorItemSchema.getEdorType());
//					tLPGetSchema.setOperator(mGlobalInput.Operator);
//					tLPGetSchema.setModifyDate(strCurrentDate);
//					tLPGetSchema.setModifyTime(strCurrentTime);
//					tLPGetSet.add(tLPGetSchema);
//				}
				// 查询LJAGetDraw数据恢复到LJSGetDraw，要用到老的GetToDate，就是GetDate
				tLJAGetDrawDB.setPolNo(tLCGetSchema.getPolNo());
				tLJAGetDrawDB.setDutyCode(tLCGetSchema.getDutyCode());
				tLJAGetDrawDB.setGetDutyKind(tLCGetSchema.getGetDutyKind());
				tLJAGetDrawDB.setGetDutyCode(tLCGetSchema.getGetDutyCode());
				tLJAGetDrawDB.setGetDate(tLCGetSchema.getGettoDate());
				LJAGetDrawSet tempLJAGetDrawSet = tLJAGetDrawDB.query();
				if (tempLJAGetDrawSet != null && tempLJAGetDrawSet.size() > 0) {
					for (int j = 1; j <= tempLJAGetDrawSet.size(); j++) {
						// 删除A表（不删了）
						LJAGetDrawSchema tLJAGetDrawSchema = new LJAGetDrawSchema();
						tLJAGetDrawSchema.setSchema(tempLJAGetDrawSet.get(j));
						// tLJAGetDrawSet.add(tLJAGetDrawSchema);
						// 插入S表
						LJSGetDrawSchema tLJSGetDrawSchema = new LJSGetDrawSchema();
						tRef.transFields(tLJSGetDrawSchema, tempLJAGetDrawSet
								.get(j));
						tLJSGetDrawSchema.setOperator(mGlobalInput.Operator);
						tLJSGetDrawSchema.setModifyDate(strCurrentDate);
						tLJSGetDrawSchema.setModifyTime(strCurrentTime);
						tLJSGetDrawSet.add(tLJSGetDrawSchema);
						// 总表
						boolean tHaveOne = false;
						for (int k = 1; k <= tLJAGetSet.size(); k++) {
							if (StrTool.cTrim(tLJAGetDrawSchema.getActuGetNo())
									.equals(
											StrTool.cTrim(tLJAGetSet.get(k)
													.getActuGetNo()))) {
								tHaveOne = true;
								break; // 跳出最内层循环
							}
						}
						if (!tHaveOne) {
							// 还没遇到此实付号
							tLJAGetDB.setActuGetNo(tLJAGetDrawSchema
									.getActuGetNo());
							if (tLJAGetDB.getInfo()) {
								// 删除A表（不删了）
								LJAGetSchema tLJAGetSchema = new LJAGetSchema();
								tLJAGetSchema.setSchema(tLJAGetDB.getSchema());
								tLJAGetSet.add(tLJAGetSchema);
								// 插入S表
								LJSGetSchema tLJSGetSchema = new LJSGetSchema();
								tRef.transFields(tLJSGetSchema, tLJAGetDB
										.getSchema());
								tLJSGetSchema.setOtherNo(mLPEdorItemSchema
										.getContNo());
								tLJSGetSchema.setOtherNoType("2"); // 参考PayPlanBL里的设置
								tLJSGetSchema
										.setOperator(mGlobalInput.Operator);
								tLJSGetSchema.setModifyDate(strCurrentDate);
								tLJSGetSchema.setModifyTime(strCurrentTime);
								tLJSGetSet.add(tLJSGetSchema);
							}
						}
					}
				}
			}

			// 恢复LCPol表
			// 要更新数据声明
			LCPolSet tLCPolSet = new LCPolSet();
			LPPolSet tLPPolSet = new LPPolSet();
			// 查询P表数据
			LPPolDB tLPPolDB = new LPPolDB();
			tLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType());
			LPPolSet tempLPPolSet = tLPPolDB.query();
			if (tempLPPolSet == null || tempLPPolSet.size() <= 0) {
				return this.makeError("dealData", "查询保全保单险种表数据失败！");
			}
			LCPolDB tLCPolDB = new LCPolDB();
			for (int i = 1; i <= tempLPPolSet.size(); i++) {
				LCPolSchema tLCPolSchema = new LCPolSchema();
				tRef.transFields(tLCPolSchema, tempLPPolSet.get(i));
				tLCPolSchema.setOperator(mGlobalInput.Operator);
				tLCPolSchema.setModifyDate(strCurrentDate);
				tLCPolSchema.setModifyTime(strCurrentTime);
				tLCPolSet.add(tLCPolSchema);
				// 查询C表对应数据更新到P表
//				tLCPolDB.setPolNo(tLCPolSchema.getPolNo());
//				if (tLCPolDB.getInfo()) {
//					LPPolSchema tLPPolSchema = new LPPolSchema();
//					tRef.transFields(tLPPolSchema, tLCPolDB.getSchema());
//					tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
//					tLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
//					tLPPolSchema.setOperator(mGlobalInput.Operator);
//					tLPPolSchema.setModifyDate(strCurrentDate);
//					tLPPolSchema.setModifyTime(strCurrentTime);
//					tLPPolSet.add(tLPPolSchema);
//				}
			}

			// 数据加入到map
			mMap.put(tLCGetSet, "DELETE&INSERT");
			mMap.put(tLPGetSet, "DELETE&INSERT");
			mMap.put(tLJSGetDrawSet, "DELETE&INSERT");
			// mMap.put(tLJAGetDrawSet,"DELETE"); //只是删除
			mMap.put(tLJSGetSet, "DELETE&INSERT");
			// mMap.put(tLJAGetSet,"DELETE"); //只是删除
			mMap.put(tLCPolSet, "DELETE&INSERT");
//			mMap.put(tLCPolSet, "DELETE&INSERT");

			mResult.clear();
			mResult.add(mMap);
		} catch (Exception e) {
			// @@错误处理
			return this.makeError("dealData", "数据处理错误！" + e.getMessage());
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
		tError.moduleName = "PEdorAGBackConfirmBL";
		tError.functionName = vFuncName;
		tError.errorMessage = vErrMsg;
		this.mErrors.addOneError(tError);
		return false;
	}

	public static void main(String[] args) {
		PEdorAGBackConfirmBL tPEdorAGBackConfirmBL = new PEdorAGBackConfirmBL();
	}
}
