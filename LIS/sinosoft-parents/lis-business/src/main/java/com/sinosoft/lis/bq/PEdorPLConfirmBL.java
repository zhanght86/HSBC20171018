package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContStateDB;
import com.sinosoft.lis.db.LPContStateDB;
import com.sinosoft.lis.db.LPEdorItemDB;
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
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全－保单挂失与解挂
 */
public class PEdorPLConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(PEdorPLConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	//public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private MMap mMap = new MMap();
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 业务数据 */
	private String mContNo = "";

	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	public PEdorPLConfirmBL() {
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
		if (!getInputData()) {
			return false;
		}

		// 数据校验
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

		this.setOperate("CONFIRM||PL");

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		mLPEdorItemSchema = (LPEdorItemSchema) mInputData.getObjectByObjectName("LPEdorItemSchema", 0);
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0));

		if (mLPEdorItemSchema == null || mGlobalInput == null) {
			mErrors.addOneError(new CError("传入数据不完全！"));
			return false;
		}

		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {

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
		// 得到保全批改信息
		mLPEdorItemSchema.setSchema(tLPEdorItemSet.get(1));
		mContNo = mLPEdorItemSchema.getContNo();
		if (mContNo == null || mContNo.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorPLConfirmBL";
			tError.functionName = "checkData";
			tError.errorMessage = "前台传输数据ContNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean dealData() {
		Reflections tRef = new Reflections();

		LPContStateDB tLPContStateDB = new LPContStateDB();
		LPContStateSet tLPContStateSet = new LPContStateSet();
		tLPContStateDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContStateDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPContStateDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPContStateDB.setStateType("Lost");
		
		tLPContStateSet = tLPContStateDB.query();
		// p表数据不可能为空
		if (tLPContStateSet.size() <= 0) {
			CError.buildErr(this, "查询保单状态表失败!");
			return false;
		}
		LCContStateSet tInsertLCContStateSet = new LCContStateSet();
		LCContStateSchema tInsertLCContStateSchema = new LCContStateSchema();
		tInsertLCContStateSet.add(tInsertLCContStateSchema);
		tRef.transFields(tInsertLCContStateSet, tLPContStateSet);

		String tState = mLPEdorItemSchema.getStandbyFlag1().equals("0") ? "1" : "0";
		// 反向查询
		String tLCContStateSQL = "select * from lccontstate where contno='?contno?' and statetype='Lost' and state='?tState?' and enddate is null ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tLCContStateSQL);
		sqlbv.put("contno", mLPEdorItemSchema.getContNo());
		sqlbv.put("tState", tState);
		LCContStateSet tTmppLCContStateSet = new LCContStateSet();
		LCContStateDB tLCContStateDB = new LCContStateDB();
		tTmppLCContStateSet = tLCContStateDB.executeQuery(sqlbv);
		// 此处可能为0 lost的保单初始状态没有生成
		LPContStateSet tInsertLPContStateSet = new LPContStateSet();
		LPContStateSchema tInsertLPContStateSchema = new LPContStateSchema();
		tInsertLPContStateSet.add(tInsertLPContStateSchema);
		if (tTmppLCContStateSet.size() > 0) {
			tRef.transFields(tInsertLPContStateSet, tTmppLCContStateSet);
			tInsertLPContStateSet.get(1).setEdorNo(mLPEdorItemSchema.getEdorNo());
			tInsertLPContStateSet.get(1).setEdorType(mLPEdorItemSchema.getEdorType());
			tInsertLPContStateSet.get(1).setModifyDate(PubFun.getCurrentDate());
			tInsertLPContStateSet.get(1).setModifyTime(PubFun.getCurrentTime());
		}

		mMap.put(tInsertLCContStateSet, "DELETE&INSERT");
		//数据记录数不一致必须先删除后插入
		SQLwithBindVariables sbv=new SQLwithBindVariables();
		sbv.sql("delete from lpcontstate where edorno='?edorno?' and edortype='?edortype?' and contno='?contno?'");
		sbv.put("edorno", mLPEdorItemSchema.getEdorNo());
		sbv.put("edortype", mLPEdorItemSchema.getEdorType());
		sbv.put("contno", mLPEdorItemSchema.getContNo());
		mMap.put(sbv, "DELETE");
		if (tTmppLCContStateSet.size() > 0)
			mMap.put(tInsertLPContStateSet, "INSERT");
		
		return true;
	}

	private boolean prepareOutputData() {
		mResult.clear();
		mResult.add(mMap);
		return true;
	}

	public static void main(String[] args) {
	}
}
