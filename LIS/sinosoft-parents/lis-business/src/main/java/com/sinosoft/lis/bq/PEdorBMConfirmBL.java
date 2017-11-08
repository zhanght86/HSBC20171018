package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LPDutyDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.db.LPPremDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
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
 * Description: 红利领取方式变更（保全项目代码：BM）保全确认处理类
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
public class PEdorBMConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(PEdorBMConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	//public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private MMap mMap = new MMap();
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	
	private ValidateEdorData2 mValidateEdorData = null;

	private LCPolSet mLCPolSet = new LCPolSet();
	private LCDutySet mLCDutySet = new LCDutySet();

	private LPPolSet mLPPolSet = new LPPolSet();
	private LPDutySet mLPDutySet = new LPDutySet();//

	public PEdorBMConfirmBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public void setOperate(String cOperate) {
		this.mOperate = cOperate;
	}

	public String getOperate() {
		return this.mOperate;
	}

	public VData getResult() {
		return mResult;
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.setOperate(cOperate);

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		// 检查数据
		if (!checkData()) {
			return false;
		}

		// 数据操作
		if (!dealData()) {
			return false;
		}

		// 输出数据准备
		if (!prepareOutputData()) {
			return false;
		}

		this.setOperate("CONFIRM||BM");

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		mLPEdorItemSchema = (LPEdorItemSchema) cInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		if (mLPEdorItemSchema == null || mGlobalInput == null) {
			mErrors.addOneError(new CError("传入数据不完全！"));
			return false;
		}
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
		// tLPEdorItemDB.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
		// tLPEdorItemDB.setPolNo(mLPEdorItemSchema.getPolNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemSet == null || tLPEdorItemSet.size() != 1) {
			mErrors.addOneError(new CError("查询批改项目信息失败！"));
			return false;
		}
		mLPEdorItemSchema.setSchema(tLPEdorItemSet.get(1));
		
		mValidateEdorData = new ValidateEdorData2(mGlobalInput, mLPEdorItemSchema.getEdorNo(), mLPEdorItemSchema
				.getEdorType(), mLPEdorItemSchema.getContNo(), "ContNo");
		
		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		return true;
	}

	/**
	 * 准备需要保存的数据 C、P表数据互换
	 */
	private boolean dealData() {
		
		String[] chgTables = {"LCAppnt","LCInsured","LCGet"};
	    mValidateEdorData.changeData(chgTables);
	    mMap.add(mValidateEdorData.getMap());
	    //处理特约
	    mMap.add(BqNameFun.DealSpecData(mLPEdorItemSchema));
	    
		Reflections tRef = new Reflections();

		String sCurrentDate = PubFun.getCurrentDate();
		String sCurrentTime = PubFun.getCurrentTime();

		// 检查 LPPol 表
		LPPolDB tLPPolDB = new LPPolDB();
		// tLPPolDB.setPolNo(mLPEdorItemSchema.getPolNo());
		tLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType());
		LPPolSet tLPPolSet = new LPPolSet();
		try {
			tLPPolSet = tLPPolDB.query();
		} catch (Exception ex) {
			CError.buildErr(this, "查询保全险种表险种出错！");
			return false;
		}
		// 拷贝 LPPol 到 LCPol
		if (tLPPolSet.size() > 0) {
			LCPolSchema tLCPolSchema;
			try {
				for (int i = 1; i <= tLPPolSet.size(); i++) {
					tLCPolSchema = new LCPolSchema();
					tRef.transFields(tLCPolSchema, tLPPolSet.get(i));
					tLCPolSchema.setOperator(mGlobalInput.Operator);
					tLCPolSchema.setModifyDate(sCurrentDate);
					tLCPolSchema.setModifyTime(sCurrentTime);
					mLCPolSet.add(tLCPolSchema);

					// 检查 LCPol 表
					LCPolDB tLCPolDB = new LCPolDB();
					tLCPolDB.setPolNo(tLCPolSchema.getPolNo());
					LCPolSet tLCPolSet = new LCPolSet();
					try {
						tLCPolSet = tLCPolDB.query();
					} catch (Exception ex) {
						CError.buildErr(this, "查询契约险种表险种出错！");
						return false;
					}
					// 拷贝 LCPol 到 LPPol
					if (tLCPolSet.size() > 0) {
						LPPolSchema tLPPolSchema;
						try {
							tLPPolSchema = new LPPolSchema();
							tRef.transFields(tLPPolSchema, tLCPolSet.get(1));
							tLPPolSchema.setEdorNo(mLPEdorItemSchema
									.getEdorNo());
							tLPPolSchema.setEdorType(mLPEdorItemSchema
									.getEdorType());
							tLPPolSchema.setOperator(mGlobalInput.Operator);
							tLPPolSchema.setModifyDate(sCurrentDate);
							tLPPolSchema.setModifyTime(sCurrentTime);
							mLPPolSet.add(tLPPolSchema);

						} catch (Exception ex) {
							CError.buildErr(this, "备份契约险种表到保全险种表失败！");
							ex.printStackTrace();
							return false;
						}
						// 垃圾处理
						tLPPolSchema = null;
					}
					// 垃圾处理
					tLCPolSet = null;
					tLCPolDB = null;

					// 检查 LPDuty 表
					LPDutyDB tLPDutyDB = new LPDutyDB();
					tLPDutyDB.setPolNo(tLCPolSchema.getPolNo());
					tLPDutyDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
					tLPDutyDB.setEdorType(mLPEdorItemSchema.getEdorType());
					LPDutySet tLPDutySet = new LPDutySet();
					try {
						tLPDutySet = tLPDutyDB.query();
					} catch (Exception ex) {
						CError.buildErr(this, "查询保全责任表出错！");
						return false;
					}
					// 拷贝 LPDuty 到 LCDuty
					if (tLPDutySet.size() > 0) {
						LCDutySchema tLCDutySchema;
						try {
							for (int n = 1; n <= tLPDutySet.size(); n++) {
								tLCDutySchema = new LCDutySchema();
								tRef.transFields(tLCDutySchema, tLPDutySet
										.get(n));
								tLCDutySchema
										.setOperator(mGlobalInput.Operator);
								tLCDutySchema.setModifyDate(sCurrentDate);
								tLCDutySchema.setModifyTime(sCurrentTime);
								mLCDutySet.add(tLCDutySchema);

							}
						} catch (Exception ex) {
							CError.buildErr(this, "拷贝保全责任表到契约责任表失败！");
							ex.printStackTrace();
							return false;
						}
						// 垃圾处理
						tLCDutySchema = null;
					}
					// 垃圾处理
					tLPDutySet = null;
					tLPDutyDB = null;

					// 检查 LCDuty 表
					LCDutyDB tLCDutyDB = new LCDutyDB();
					tLCDutyDB.setPolNo(tLCPolSchema.getPolNo());
					LCDutySet tLCDutySet = new LCDutySet();
					try {
						tLCDutySet = tLCDutyDB.query();
					} catch (Exception ex) {
						CError.buildErr(this, "查询契约责任表是否含有 128 和 145 险种出错！");
						return false;
					}
					// 拷贝 LCDuty 到 LPDuty
					if (tLCDutySet.size() > 0) {
						LPDutySchema tLPDutySchema;
						try {
							for (int j = 1; j <= tLCDutySet.size(); j++) {
								tLPDutySchema = new LPDutySchema();
								tRef.transFields(tLPDutySchema, tLCDutySet
										.get(j));
								tLPDutySchema.setEdorNo(mLPEdorItemSchema
										.getEdorNo());
								tLPDutySchema.setEdorType(mLPEdorItemSchema
										.getEdorType());
								tLPDutySchema
										.setOperator(mGlobalInput.Operator);
								tLPDutySchema.setModifyDate(sCurrentDate);
								tLPDutySchema.setModifyTime(sCurrentTime);
								mLPDutySet.add(tLPDutySchema);
							}
						} catch (Exception ex) {
							CError.buildErr(this, "备份契约责任表到保全责任表失败！");
							ex.printStackTrace();
							return false;
						}
						// 垃圾处理
						tLPDutySchema = null;
					}
					// 垃圾处理
					tLCDutyDB = null;
				}
			} catch (Exception ex) {
				CError.buildErr(this, "拷贝保全险种表到契约险种表失败！");
				ex.printStackTrace();
				return false;
			}
			// 垃圾处理
			tLCPolSchema = null;
		}
		// 垃圾处理
		
		
		// 保费项目表 lcprem - lpprem
		LPPremDB tLPPremDB = new LPPremDB();
		LPPremSchema tLPPremSchema = new LPPremSchema();
		LPPremSet tLPPremSet = new LPPremSet();
		tLPPremSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPremSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPPremDB.setSchema(tLPPremSchema);
		tLPPremSet = tLPPremDB.query();
		if (tLPPremSet.size() < 1) {
			CError.buildErr(this, "查询保费项目表失败!");
			return false;
		}

		LCPremSet aLCPremSet = new LCPremSet();
		LPPremSet aLPPremSet = new LPPremSet();
		for (int i = 1; i <= tLPPremSet.size(); i++) {
			LCPremSchema aLCPremSchema = new LCPremSchema();
			LPPremSchema aLPPremSchema = new LPPremSchema();
			aLPPremSchema = tLPPremSet.get(i);
			tRef.transFields(aLCPremSchema, aLPPremSchema);
			aLCPremSchema.setModifyDate(PubFun.getCurrentDate());
			aLCPremSchema.setModifyTime(PubFun.getCurrentTime());
			LCPremDB tLCPremDB = new LCPremDB();
			tLCPremDB.setPolNo(aLPPremSchema.getPolNo());
			tLCPremDB.setDutyCode(aLPPremSchema.getDutyCode());
			tLCPremDB.setPayPlanCode(aLPPremSchema.getPayPlanCode());
			
			// add by jiaqiangli 经过二核之后可能产生一条新的加费记录
			// 此时C表数据不存在，而p表数据存在
			boolean tExistsFlag = tLCPremDB.getInfo();
			if (tLCPremDB.mErrors.needDealError() == true) {
				mErrors.copyAllErrors(tLCPremDB.mErrors);
				mErrors.addOneError(new CError("查询保费项目表失败！"));
				return false;
			}
			if (tExistsFlag == true) {
				tRef.transFields(aLPPremSchema, tLCPremDB.getSchema());
				aLPPremSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				aLPPremSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				aLPPremSet.add(aLPPremSchema);
			}
			
			aLCPremSet.add(aLCPremSchema);// 只往C表中插数据
		}
		mMap.put(aLPPremSet, "UPDATE");
		mMap.put(aLCPremSet, "DELETE&INSERT");

		mMap.put(mLPPolSet, "DELETE&INSERT");
		mMap.put(mLCPolSet, "DELETE&INSERT");
		mMap.put(mLPDutySet, "DELETE&INSERT");
		mMap.put(mLCDutySet, "DELETE&INSERT");

		return true;
	}

	/**
	 * 准备提交后台的数据
	 * 
	 * @return: boolean
	 */
	private boolean prepareOutputData() {
		try {
			mResult.clear();
			mResult.add(mMap);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorBMConfirmBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错:" + ex.toString();
			mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		PEdorBMConfirmBL pedorgbcomfirmbl = new PEdorBMConfirmBL();
	}
}
