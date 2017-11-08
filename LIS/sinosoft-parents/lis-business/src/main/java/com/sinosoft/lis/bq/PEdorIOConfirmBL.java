package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.db.LPAppntDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPCustomerImpartDB;
import com.sinosoft.lis.db.LPCustomerImpartParamsDB;
import com.sinosoft.lis.db.LPDutyDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPGetDB;
import com.sinosoft.lis.db.LPInsuredDB;
import com.sinosoft.lis.db.LPPersonDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.db.LPPremDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCCustomerImpartParamsSchema;
import com.sinosoft.lis.schema.LCCustomerImpartSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.schema.LPAppntSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPCustomerImpartParamsSchema;
import com.sinosoft.lis.schema.LPCustomerImpartSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.schema.LPPersonSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.vschema.LCAppntSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCCustomerImpartParamsSet;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LDPersonSet;
import com.sinosoft.lis.vschema.LPAppntSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPCustomerImpartParamsSet;
import com.sinosoft.lis.vschema.LPCustomerImpartSet;
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


public class PEdorIOConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(PEdorIOConfirmBL.class);
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
	
	private String mEdorNo = null;
	private String mEdorType = null;
	private String mContNo = null;

	public PEdorIOConfirmBL() {

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

		this.setOperate("CONFIRM||IO");

		return true;
	}

	public VData getResult() {
		return mResult;
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
		
	    mEdorNo = mLPEdorItemSchema.getEdorNo();
	    mEdorType = mLPEdorItemSchema.getEdorType();
	    mContNo = mLPEdorItemSchema.getContNo();
	    mValidateEdorData = new ValidateEdorData2(mGlobalInput, mEdorNo,mEdorType, mContNo, "ContNo");
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
	 * 准备需要保存的数据
	 */
	private boolean dealData() {
		
//		//采用新的方式进行 CP 互换
//	    String[] chgTables = {"LCCSpec"};
//	    mValidateEdorData.changeData(chgTables);
//	    mMap.add(mValidateEdorData.getMap());
		
	    //处理特约
	    mMap.add(BqNameFun.DealSpecData(mLPEdorItemSchema));
		// C表
		LCContSet aLCContSet = new LCContSet();
		LCPolSet aLCPolSet = new LCPolSet();
		LCDutySet aLCDutySet = new LCDutySet();
		LCPremSet aLCPremSet = new LCPremSet();
		LCGetSet aLCGetSet = new LCGetSet();
		LCInsuredSet aLCInsuredSet = new LCInsuredSet();
		LCAppntSet aLCAppntSet = new LCAppntSet();
		LDPersonSet aLDPersonSet = new LDPersonSet();

		// P表
		LPContSet aLPContSet = new LPContSet();
		LPPolSet aLPPolSet = new LPPolSet();
		LPDutySet aLPDutySet = new LPDutySet();
		LPPremSet aLPPremSet = new LPPremSet();
		LPGetSet aLPGetSet = new LPGetSet();
		LPInsuredSet aLPInsuredSet = new LPInsuredSet();
		LPAppntSet aLPAppntSet = new LPAppntSet();
		LPPersonSet aLPPersonSet = new LPPersonSet();
		
		LPPremSet dLPPremSet = new LPPremSet();

		Reflections tRef = new Reflections();

		// 得到投保人资料信息表
		LPPolSet tLPPolSet = new LPPolSet();
		LPPolDB tLPPolDB = new LPPolDB();
		tLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPPolDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPPolSet = tLPPolDB.query();

		for (int i = 1; i <= tLPPolSet.size(); i++) {
			LPPolSchema tLPPolSchema = new LPPolSchema();
			LCPolSchema tLCPolSchema = new LCPolSchema();
			tRef.transFields(tLCPolSchema, tLPPolSet.get(i));

			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(tLCPolSchema.getPolNo());
			if (!tLCPolDB.getInfo()) {
				mErrors.copyAllErrors(tLCPolDB.mErrors);
				mErrors.addOneError(new CError("查询险种保单表失败！"));
				return false;
			}
			tRef.transFields(tLPPolSchema, tLCPolDB.getSchema());
			tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			aLPPolSet.add(tLPPolSchema);
			aLCPolSet.add(tLCPolSchema);

			// 保费项目表 lcprem - lpprem
			LPPremDB tLPPremDB = new LPPremDB();
			LPPremSchema tLPPremSchema = new LPPremSchema();
			LPPremSet tLPPremSet = new LPPremSet();
			tLPPremSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPPremSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPPremSchema.setContNo(mLPEdorItemSchema.getContNo());
			tLPPremSchema.setPolNo(tLCPolSchema.getPolNo());
			tLPPremDB.setSchema(tLPPremSchema);
			tLPPremSet = tLPPremDB.query();
			if (tLPPremSet.size() < 1) {
				CError.buildErr(this, "查询保费项目表失败!");
				return false;
			}
			for (int j = 1; j <= tLPPremSet.size(); j++) {
				LCPremSchema aLCPremSchema = new LCPremSchema();
				LPPremSchema aLPPremSchema = new LPPremSchema();
				aLPPremSchema = tLPPremSet.get(j);
				tRef.transFields(aLCPremSchema, aLPPremSchema);
				aLCPremSchema.setModifyDate(PubFun.getCurrentDate());
				aLCPremSchema.setModifyTime(PubFun.getCurrentTime());
				LCPremDB tLCPremDB = new LCPremDB();
				tLCPremDB.setPolNo(aLPPremSchema.getPolNo());
				tLCPremDB.setDutyCode(aLPPremSchema.getDutyCode());
				tLCPremDB.setPayPlanCode(aLPPremSchema.getPayPlanCode());
				
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
				}else{
					//C表没有的，新生成的P表，删除P表
					dLPPremSet.add(aLPPremSchema);
				}
			
				aLCPremSet.add(aLCPremSchema);// 只往C表中插数据
			}
			
			// 得到责任信息
			LPDutyDB tLPDutyDB = new LPDutyDB();
			LPDutySet tLPDutySet = new LPDutySet();
			tLPDutyDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPDutyDB.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPDutyDB.setContNo(mLPEdorItemSchema.getContNo());
			tLPDutyDB.setPolNo(tLPPolSet.get(i).getPolNo());
			tLPDutySet = tLPDutyDB.query();
			for (int j = 1; j <= tLPDutySet.size(); j++) {
				LCDutySchema tLCDutySchema = new LCDutySchema();
				tRef.transFields(tLCDutySchema, tLPDutySet.get(j).getSchema());
				aLCDutySet.add(tLCDutySchema);
			}
			LCDutySet tLCDutySet = new LCDutySet();
			// 原责任信息
			if (tLPDutySet != null && tLPDutySet.size() > 0) {
				LCDutyDB tLCDutyDB = new LCDutyDB();
				tLCDutyDB.setPolNo(tLPPolSet.get(i).getPolNo());
				tLCDutySet = tLCDutyDB.query();
				for (int j = 1; j <= tLCDutySet.size(); j++) {
					LPDutySchema tLPDutySchema = new LPDutySchema();
					tRef.transFields(tLPDutySchema, tLCDutySet.get(j).getSchema());
					tLPDutySchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
					tLPDutySchema.setEdorType(mLPEdorItemSchema.getEdorType());
					aLPDutySet.add(tLPDutySchema);
				}
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
			LCGetSet tLCGetSet = new LCGetSet();
			// 原给付项信息
			if (tLPGetSet != null && tLPGetSet.size() > 0) {
				LCGetDB tLCGetDB = new LCGetDB();
				tLCGetDB.setPolNo(tLPPolSet.get(i).getPolNo());
				tLCGetSet = tLCGetDB.query();
				for (int j = 1; j <= tLCGetSet.size(); j++) {
					LPGetSchema tLPGetSchema = new LPGetSchema();
					tRef.transFields(tLPGetSchema, tLCGetSet.get(j).getSchema());
					tLPGetSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
					tLPGetSchema.setEdorType(mLPEdorItemSchema.getEdorType());
					aLPGetSet.add(tLPGetSchema);
				}
			}
		}
		
		mMap.put(aLCPolSet, "UPDATE");
		mMap.put(aLPPolSet, "UPDATE");
		
		mMap.put(aLCDutySet, "UPDATE");
		mMap.put(aLPDutySet, "UPDATE");
		
		mMap.put(dLPPremSet,"DELETE");
		mMap.put(aLPPremSet,"UPDATE");
		mMap.put(aLCPremSet, "DELETE&INSERT");
		
		mMap.put(aLCGetSet, "DELETE&INSERT");
		mMap.put(aLPGetSet, "UPDATE");
		

		LPContSchema tLPContSchema = new LPContSchema();
		LCContSchema tLCContSchema = new LCContSchema();
		LPContSchema aLPContSchema = new LPContSchema();
		LCContSchema aLCContSchema = new LCContSchema();
		LCContSet tLCContSet = new LCContSet();
		LPContSet tLPContSet = new LPContSet();

		tLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPContSchema.setContNo(mLPEdorItemSchema.getContNo());

		LPContDB tLPContDB = new LPContDB();
		tLPContDB.setSchema(tLPContSchema);
		tLPContSet = tLPContDB.query();
		for (int j = 1; j <= tLPContSet.size(); j++) {
			aLPContSchema = tLPContSet.get(j);
			tLCContSchema = new LCContSchema();
			tLPContSchema = new LPContSchema();

			LCContDB aLCContDB = new LCContDB();
			aLCContDB.setContNo(aLPContSchema.getContNo());
			aLCContDB.setInsuredNo(aLPContSchema.getInsuredNo());
			if (!aLCContDB.getInfo()) {
				mErrors.copyAllErrors(aLCContDB.mErrors);
				mErrors.addOneError(new CError("查询被保人信息失败！"));
				return false;
			}
			aLCContSchema = aLCContDB.getSchema();
			tRef.transFields(tLPContSchema, aLCContSchema);
			tLPContSchema.setEdorNo(aLPContSchema.getEdorNo());
			tLPContSchema.setEdorType(aLPContSchema.getEdorType());

			// 转换成保单个人信息。
			tRef.transFields(tLCContSchema, aLPContSchema);
			tLCContSchema.setModifyDate(PubFun.getCurrentDate());
			tLCContSchema.setModifyTime(PubFun.getCurrentTime());

			aLPContSet.add(tLPContSchema);
			tLCContSet.add(tLCContSchema);
			aLCContSet.add(tLCContSchema);
		}
		
		mMap.put(aLCContSet, "UPDATE");
		mMap.put(aLPContSet, "UPDATE");

		// 得到当前保全需要更新的被保人保全数据。(取得主附险变动信息)
		LCInsuredSchema aLCInsuredSchema = new LCInsuredSchema();
		LPInsuredSchema aLPInsuredSchema = new LPInsuredSchema();
		LCInsuredSet tLCInsuredSet = new LCInsuredSet();
		LPInsuredSet tLPInsuredSet = new LPInsuredSet();

		LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
		tLPInsuredSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPInsuredSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPInsuredSchema.setContNo(mLPEdorItemSchema.getContNo());

		LPInsuredDB tLPInsuredDB = new LPInsuredDB();
		tLPInsuredDB.setSchema(tLPInsuredSchema);
		tLPInsuredSet = tLPInsuredDB.query();
		for (int j = 1; j <= tLPInsuredSet.size(); j++) {
			aLPInsuredSchema = tLPInsuredSet.get(j);
			LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
			tLPInsuredSchema = new LPInsuredSchema();

			LCInsuredDB aLCInsuredDB = new LCInsuredDB();
			aLCInsuredDB.setContNo(aLPInsuredSchema.getContNo());
			aLCInsuredDB.setInsuredNo(aLPInsuredSchema.getInsuredNo());
			if (!aLCInsuredDB.getInfo()) {
				mErrors.copyAllErrors(aLCInsuredDB.mErrors);
				mErrors.addOneError(new CError("查询被保人信息失败！"));
				return false;
			}
			aLCInsuredSchema = aLCInsuredDB.getSchema();
			tRef.transFields(tLPInsuredSchema, aLCInsuredSchema);
			tLPInsuredSchema.setEdorNo(aLPInsuredSchema.getEdorNo());
			tLPInsuredSchema.setEdorType(aLPInsuredSchema.getEdorType());

			// 转换成保单个人信息。
			tRef.transFields(tLCInsuredSchema, aLPInsuredSchema);
			tLCInsuredSchema.setModifyDate(PubFun.getCurrentDate());
			tLCInsuredSchema.setModifyTime(PubFun.getCurrentTime());

			aLPInsuredSet.add(tLPInsuredSchema);
			tLCInsuredSet.add(tLCInsuredSchema);
			aLCInsuredSet.add(tLCInsuredSchema);
		}
		mMap.put(aLCInsuredSet, "UPDATE");
		mMap.put(aLPInsuredSet, "UPDATE");

		// 得到当前保单的投保人资料
		LCAppntSchema aLCAppntSchema = new LCAppntSchema();
		LPAppntSchema aLPAppntSchema = new LPAppntSchema();
		LPAppntSet tLPAppntSet = new LPAppntSet();
		LPAppntSchema tLPAppntSchema = new LPAppntSchema();
		tLPAppntSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPAppntSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPAppntSchema.setContNo(mLPEdorItemSchema.getContNo());
		LPAppntDB tLPAppntDB = new LPAppntDB();
		tLPAppntDB.setSchema(tLPAppntSchema);
		tLPAppntSet = tLPAppntDB.query();
		if (tLPAppntSet.size() > 0) {
			for (int j = 1; j <= tLPAppntSet.size(); j++) {
				aLPAppntSchema = tLPAppntSet.get(j);
				LCAppntSchema tLCAppntSchema = new LCAppntSchema();
				tLPAppntSchema = new LPAppntSchema();

				LCAppntDB aLCAppntDB = new LCAppntDB();
				aLCAppntDB.setContNo(aLPAppntSchema.getContNo());
				aLCAppntDB.setAppntNo(aLPAppntSchema.getAppntNo());
				if (!aLCAppntDB.getInfo()) {
					mErrors.copyAllErrors(aLCAppntDB.mErrors);
					mErrors.addOneError(new CError("查询投保人信息失败！"));
					return false;
				}
				aLCAppntSchema = aLCAppntDB.getSchema();
				tRef.transFields(tLPAppntSchema, aLCAppntSchema);
				tLPAppntSchema.setEdorNo(aLPAppntSchema.getEdorNo());
				tLPAppntSchema.setEdorType(aLPAppntSchema.getEdorType());

				// 转换成保单个人信息。
				tRef.transFields(tLCAppntSchema, aLPAppntSchema);
				tLCAppntSchema.setModifyDate(PubFun.getCurrentDate());
				tLCAppntSchema.setModifyTime(PubFun.getCurrentTime());

				aLPAppntSet.add(tLPAppntSchema);
				aLCAppntSet.add(tLCAppntSchema);
			}
			mMap.put(aLCAppntSet, "UPDATE");
			mMap.put(aLPAppntSet, "UPDATE");
		}

		// 得到当前保单的投保人资料
		LDPersonSchema aLCPersonSchema = new LDPersonSchema();
		LPPersonSchema aLPPersonSchema = new LPPersonSchema();
		LPPersonSet tLPPersonSet = new LPPersonSet();
		LPPersonSchema tLPPersonSchema = new LPPersonSchema();
		tLPPersonSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPersonSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		LPPersonDB tLPPersonDB = new LPPersonDB();
		tLPPersonDB.setSchema(tLPPersonSchema);
		tLPPersonSet = tLPPersonDB.query();
		for (int j = 1; j <= tLPPersonSet.size(); j++) {
			aLPPersonSchema = tLPPersonSet.get(j);
			LDPersonSchema tLDPersonSchema = new LDPersonSchema();
			tLPPersonSchema = new LPPersonSchema();

			LDPersonDB aLDPersonDB = new LDPersonDB();
			aLDPersonDB.setCustomerNo(aLPPersonSchema.getCustomerNo());
			if (!aLDPersonDB.getInfo()) {
				mErrors.copyAllErrors(aLDPersonDB.mErrors);
				mErrors.addOneError(new CError("查询投保人信息失败！"));
				return false;
			}
			aLCPersonSchema = aLDPersonDB.getSchema();
			tRef.transFields(tLPPersonSchema, aLCPersonSchema);
			tLPPersonSchema.setEdorNo(aLPPersonSchema.getEdorNo());
			tLPPersonSchema.setEdorType(aLPPersonSchema.getEdorType());

			// 转换成保单个人信息。
			tRef.transFields(tLDPersonSchema, aLPPersonSchema);
			tLDPersonSchema.setModifyDate(PubFun.getCurrentDate());
			tLDPersonSchema.setModifyTime(PubFun.getCurrentTime());

			aLPPersonSet.add(tLPPersonSchema);
			aLDPersonSet.add(tLDPersonSchema);
		}
		mMap.put(aLDPersonSet, "UPDATE");
		mMap.put(aLPPersonSet, "UPDATE");
		
		LCCustomerImpartSet aLCCustomerImpartSet = new LCCustomerImpartSet();
		LCCustomerImpartParamsSet aLCCustomerImpartParamsSet = new LCCustomerImpartParamsSet();
		//处理健康告知
		LPCustomerImpartSet tLPCustomerImpartSet = new LPCustomerImpartSet();
		LPCustomerImpartSchema aLPCustomerImpartSchema = new LPCustomerImpartSchema();
		aLPCustomerImpartSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		aLPCustomerImpartSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		LPCustomerImpartDB tLPCustomerImpartDB = new LPCustomerImpartDB();
		tLPCustomerImpartDB.setSchema(aLPCustomerImpartSchema);
		tLPCustomerImpartSet = tLPCustomerImpartDB.query();
		if (tLPCustomerImpartSet.size() > 0) {
			for (int i = 1; i <= tLPCustomerImpartSet.size(); i++) {
				LCCustomerImpartSchema tLCCustomerImpartSchema = new LCCustomerImpartSchema();
				tRef.transFields(tLCCustomerImpartSchema, tLPCustomerImpartSet.get(i));
				tLCCustomerImpartSchema.setModifyDate(PubFun.getCurrentDate());
				tLCCustomerImpartSchema.setModifyTime(PubFun.getCurrentTime());
				aLCCustomerImpartSet.add(tLCCustomerImpartSchema);
			}
			mMap.put(aLCCustomerImpartSet, "DELETE&INSERT");
		}

		LPCustomerImpartParamsSet tLPCustomerImpartParamsSet = new LPCustomerImpartParamsSet();
		LPCustomerImpartParamsSchema aLPCustomerImpartParamsSchema = new LPCustomerImpartParamsSchema();
		aLPCustomerImpartParamsSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		aLPCustomerImpartParamsSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		LPCustomerImpartParamsDB tLPCustomerImpartParamsDB = new LPCustomerImpartParamsDB();
		tLPCustomerImpartParamsDB.setSchema(aLPCustomerImpartParamsSchema);
		tLPCustomerImpartParamsSet = tLPCustomerImpartParamsDB.query();
		if (tLPCustomerImpartParamsSet.size() > 0) {
			for (int i = 1; i <= tLPCustomerImpartParamsSet.size(); i++) {
				LCCustomerImpartParamsSchema tLCCustomerImpartParamsSchema = new LCCustomerImpartParamsSchema();
				tRef.transFields(tLCCustomerImpartParamsSchema,tLPCustomerImpartParamsSet.get(i));
				tLCCustomerImpartParamsSchema.setModifyDate(PubFun.getCurrentDate());
				tLCCustomerImpartParamsSchema.setModifyTime(PubFun.getCurrentTime());
				aLCCustomerImpartParamsSet.add(tLCCustomerImpartParamsSchema);
			}
			mMap.put(aLCCustomerImpartParamsSet, "DELETE&INSERT");
		}

		// 得到当前LPEdorItem保单信息主表的状态，并更新状态为申请确认。
		mLPEdorItemSchema.setEdorState("0");
		mMap.put(mLPEdorItemSchema, "UPDATE");
		
		return true;
	}

	private boolean prepareOutputData() {
		mResult.clear();
		mResult.add(mMap);
		return true;
	}
}
