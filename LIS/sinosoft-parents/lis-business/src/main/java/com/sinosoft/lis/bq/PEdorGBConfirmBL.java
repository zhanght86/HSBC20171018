package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LPDutyDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPGetDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vschema.LCAppntSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LDPersonSet;
import com.sinosoft.lis.vschema.LPAppntSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPInsuredSet;
import com.sinosoft.lis.vschema.LPPersonSet;
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
 * Description: 领取年龄变更（保全项目代码：GB）保全确认处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author WuHao
 * @version 1.0
 */
public class PEdorGBConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(PEdorGBConfirmBL.class);
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

	public PEdorGBConfirmBL() {
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

		this.setOperate("CONFIRM||GB");

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
		tLPEdorItemDB.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
		tLPEdorItemDB.setPolNo(mLPEdorItemSchema.getPolNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemSet == null || tLPEdorItemSet.size() != 1) {
			mErrors.addOneError(new CError("查询批改项目信息失败！"));
			return false;
		}
		mLPEdorItemSchema.setSchema(tLPEdorItemSet.get(1));
		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		boolean flag = true;
		// LCPolDB tLCPolDB = new LCPolDB();
		//
		// tLCPolDB.setPolNo(mLPEdorItemSchema.getPolNo());
		// if (!tLCPolDB.getInfo()){
		// return false;
		// }
		return flag;
	}

	/**
	 * 准备需要保存的数据 C、P表数据互换
	 */
	private boolean dealData() {
		// C表
		LCContSet aLCContSet = new LCContSet();

		LCPolSet aLCPolSet = new LCPolSet();
		LCDutySet aLCDutySet = new LCDutySet();
		LCGetSet aLCGetSet = new LCGetSet();

		LCPremSet aLCPremSet = new LCPremSet();
		LCInsuredSet aLCInsuredSet = new LCInsuredSet();
		LCAppntSet aLCAppntSet = new LCAppntSet();
		LDPersonSet aLDPersonSet = new LDPersonSet();

		// P表
		LPContSet aLPContSet = new LPContSet();

		LPPolSet aLPPolSet = new LPPolSet();
		LPDutySet aLPDutySet = new LPDutySet();
		LPGetSet aLPGetSet = new LPGetSet();

		LPPremSet aLPPremSet = new LPPremSet();
		LPInsuredSet aLPInsuredSet = new LPInsuredSet();
		LPAppntSet aLPAppntSet = new LPAppntSet();
		LPPersonSet aLPPersonSet = new LPPersonSet();

		Reflections tRef = new Reflections();

		// 查询险种表
		LPPolSet tLPPolSet = new LPPolSet();
		LPPolDB tLPPolDB = new LPPolDB();
		tLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPPolSet = tLPPolDB.query();

		for (int i = 1; i <= tLPPolSet.size(); i++) {
			// 将P表中数据放到C表中[险种表]
			LPPolSchema tLPPolSchema = new LPPolSchema();
			LCPolSchema tLCPolSchema = new LCPolSchema();
			tRef.transFields(tLCPolSchema, tLPPolSet.get(i));
			aLCPolSet.add(tLCPolSchema);

			// 查询C表数据[险种表]
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(tLCPolSchema.getPolNo());
			if (!tLCPolDB.getInfo()) {
				mErrors.copyAllErrors(tLCPolDB.mErrors);
				mErrors.addOneError(new CError("查询险种保单表失败！"));
				return false;
			}

			// 将C表中数据放到P表中[险种表]
			tRef.transFields(tLPPolSchema, tLCPolDB.getSchema());
			tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			aLPPolSet.add(tLPPolSchema);

			// 得到责任信息
			LPDutyDB tLPDutyDB = new LPDutyDB();
			LPDutySet tLPDutySet = new LPDutySet();
			tLPDutyDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPDutyDB.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPDutyDB.setPolNo(tLPPolSet.get(i).getPolNo());
			tLPDutySet = tLPDutyDB.query();
			for (int j = 1; j <= tLPDutySet.size(); j++) {
				LCDutySchema tLCDutySchema = new LCDutySchema();
				tRef.transFields(tLCDutySchema, tLPDutySet.get(j).getSchema());
				aLCDutySet.add(tLCDutySchema);
			}

			// 得到给付项信息
			LPGetDB tLPGetDB = new LPGetDB();
			LPGetSet tLPGetSet = new LPGetSet();
			tLPGetDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPGetDB.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPGetDB.setPolNo(tLPPolSet.get(i).getPolNo());
			tLPGetSet = tLPGetDB.query();
			for (int j = 1; j <= tLPGetSet.size(); j++) {
				LCGetSchema tLCGetSchema = new LCGetSchema();
				tRef.transFields(tLCGetSchema, tLPGetSet.get(j).getSchema());
				aLCGetSet.add(tLCGetSchema);
			}

			LCDutySet tLCDutySet = new LCDutySet();
			LCGetSet tLCGetSet = new LCGetSet();

			// 原责任信息
			if (tLPDutySet != null && tLPDutySet.size() > 0) {
				LCDutyDB tLCDutyDB = new LCDutyDB();
				tLCDutyDB.setPolNo(tLPPolSet.get(i).getPolNo());
				tLCDutySet = tLCDutyDB.query();
				for (int j = 1; j <= tLCDutySet.size(); j++) {
					LPDutySchema tLPDutySchema = new LPDutySchema();
					tRef.transFields(tLPDutySchema, tLCDutySet.get(j)
							.getSchema());
					tLPDutySchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
					tLPDutySchema.setEdorType(mLPEdorItemSchema.getEdorType());
					aLPDutySet.add(tLPDutySchema);
				}
			}

			// 原给付项信息
			if (tLPGetSet != null && tLPGetSet.size() > 0) {
				LCGetDB tLCGetDB = new LCGetDB();
				tLCGetDB.setPolNo(tLPPolSet.get(i).getPolNo());
				tLCGetSet = tLCGetDB.query();
				for (int j = 1; j <= tLCGetSet.size(); j++) {
					LPGetSchema tLPGetSchema = new LPGetSchema();
					tRef
							.transFields(tLPGetSchema, tLCGetSet.get(j)
									.getSchema());
					tLPGetSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
					tLPGetSchema.setEdorType(mLPEdorItemSchema.getEdorType());
					aLPGetSet.add(tLPGetSchema);
				}
			}
		}

		// //得到当前LPEdorItem保单信息主表的状态，并更新状态为申请确认。
		// mLPEdorItemSchema.setEdorState("0");
		// mLPEdorItemSchema.setOperator(mGlobalInput.Operator);
		// mLPEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
		// mLPEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
		// mMap.put(mLPEdorItemSchema, "UPDATE");

		mMap.put(aLCPolSet, "UPDATE");
		mMap.put(aLCDutySet, "UPDATE");
		mMap.put(aLCGetSet, "UPDATE");
		mMap.put(aLPPolSet, "UPDATE");
		mMap.put(aLPDutySet, "UPDATE");
		mMap.put(aLPGetSet, "UPDATE");
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
			tError.moduleName = "PEdorGBConfirmBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错:" + ex.toString();
			mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		PEdorGBConfirmBL pedorgbcomfirmbl = new PEdorGBConfirmBL();
	}
}
