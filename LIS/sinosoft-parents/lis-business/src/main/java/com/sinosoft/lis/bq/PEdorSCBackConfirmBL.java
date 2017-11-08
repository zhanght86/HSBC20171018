/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 个险SC项目保全回退确认处理类</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @version 1.0
 */
package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCCSpecDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPEdorMainDB;
import com.sinosoft.lis.db.LPCSpecDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCCSpecSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.lis.schema.LPCSpecSchema;
import com.sinosoft.lis.vschema.LCCSpecSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPCSpecSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

public class PEdorSCBackConfirmBL implements EdorBack {
private static Logger logger = Logger.getLogger(PEdorSCBackConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	//public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/**  */

	private LCCSpecSchema mLCCSpecSchema = new LCCSpecSchema();

	private LPEdorMainSchema mLPEdorMainSchema = new LPEdorMainSchema();

	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	private MMap map = new MMap();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private String mCurrentDate = PubFun.getCurrentDate();

	private String mCurrentTime = PubFun.getCurrentTime();

	private ValidateEdorData2 mValidateEdorData = null;

	public PEdorSCBackConfirmBL() {
	}

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
		// 业务逻辑处理(实现C,P互换)
		if (!dealData()) {
			return false;
		}
		// 数据准备操作)
		if (!prepareData()) {
			return false;
		}

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		try {
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
			mGlobalInput.setSchema((GlobalInput) mInputData
					.getObjectByObjectName("GlobalInput", 0));
		} catch (Exception e) {
			CError.buildErr(this, "接收数据失败");
			return false;
		}
		if (mLPEdorItemSchema == null || mGlobalInput == null) {
			CError tError = new CError();
			CError.buildErr(this, "接收数据失败");
			return false;
		}
		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean dealData() {

		Reflections tReflections = new Reflections();

		LPCSpecDB tLPCSpecDB = new LPCSpecDB();
		LPCSpecSet tLPCSpecSet = new LPCSpecSet();

		LCCSpecDB tLCCSpecDB = new LCCSpecDB();
		LCCSpecSet rLCCSpecSet = new LCCSpecSet();
		LCCSpecSet dLCCSpecSet = new LCCSpecSet();

		tLPCSpecDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPCSpecDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPCSpecDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPCSpecSet = tLPCSpecDB.query();
		int tSize = tLPCSpecSet.size();
		if (tLPCSpecSet == null || tLPCSpecSet.size() < 1) {

			//本次只有新增，直接删除
			tLCCSpecDB.setContNo(mLPEdorItemSchema.getContNo());
			//tLCCSpecDB.sets(mLPEdorItemSchema.getPolNo());
			LCCSpecSet tLCCSpecSet = new LCCSpecSet();
			tLCCSpecSet = tLCCSpecDB.query();
			int tLength = tLCCSpecSet.size();
			if (tLength < 1) // 说明以前是首次增加特约
			{
				mErrors.copyAllErrors(tLPCSpecDB.mErrors);
				mErrors.addOneError(new CError("查询保全特约信息失败！"));
				return false;
			} else {
				for (int i = 1; i <= tLength; i++) {
					LCCSpecSchema rLCCSpecSchema = new LCCSpecSchema();
					rLCCSpecSchema = tLCCSpecSet.get(i);
					dLCCSpecSet.add(rLCCSpecSchema);
				}

			}

		} else {
			
			//本次修改或者删除的情况
			for (int k = 1; k <= tSize; k++) //直接把Ｃ 表的数据由 P表覆盖
			{
				LCCSpecSchema tLCCSpecSchema = new LCCSpecSchema();
				LPCSpecSchema mLPCSpecSchema = new LPCSpecSchema();
				mLPCSpecSchema = tLPCSpecSet.get(k);
				tReflections.transFields(tLCCSpecSchema, mLPCSpecSchema);

				tLCCSpecSchema.setModifyDate(mCurrentDate);
				tLCCSpecSchema.setModifyTime(mCurrentTime);
				tLCCSpecSchema.setOperator(mGlobalInput.Operator);
				rLCCSpecSet.add(tLCCSpecSchema);
			}
			//本次新增的情况，将其删除
			LCCSpecSet cLCCSpecSet = getFirstAdd(mLPEdorItemSchema);
			if(cLCCSpecSet!=null && cLCCSpecSet.size()>0)
			{
				map.put(cLCCSpecSet, "DELETE");
			}
			
		}
		if (rLCCSpecSet.size() > 0) {
			map.put(rLCCSpecSet, "DELETE&INSERT");
		}
		if (dLCCSpecSet.size() > 0) {
			map.put(dLCCSpecSet, "DELETE");
		}
		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean prepareData() {

		//		// 得到当前LPEdorMain保单信息主表的状态，并更新状态为申请确认。
		//		mLPEdorMainSchema.setEdorState("0");
		//		// 修改保全生效日期至保全确认次日零时 add by ck
		//		mLPEdorMainSchema.setEdorValiDate(PubFun.getCurrentDate());
		//		mLPEdorMainSchema.setConfDate(PubFun.getCurrentDate());
		//		mLPEdorMainSchema.setConfOperator(mGlobalInput.Operator);
		//
		//		map.put(mLPEdorMainSchema, "UPDATE");
		mResult.clear();
		mResult.add(map);

		return true;
	}

 private LCCSpecSet	getFirstAdd(LPEdorItemSchema tLPEdorItemSchema)
 {
	 LCCSpecSet tLCCSpecSet = new LCCSpecSet();
	 LCCSpecDB tLCCSpecDB = new LCCSpecDB();
	 String tSQL="select * from lccspec a where not exists (select 'X' from lpcspec b where  a.contno=b.contno and a.serialno=b.serialno) and Endorsementno='?Endorsementno?' and contno='?contno?'";
	 SQLwithBindVariables sqlbv=new SQLwithBindVariables();
	 sqlbv.sql(tSQL);
	 sqlbv.put("Endorsementno", tLPEdorItemSchema.getEdorNo());
	 sqlbv.put("contno", tLPEdorItemSchema.getContNo());
	 tLCCSpecSet = tLCCSpecDB.executeQuery(sqlbv);
	 return tLCCSpecSet;
	 
 }
	// 测试用
	public static void main(String[] args) {
		VData tInputData = new VData();
		GlobalInput tGlobalInput = new GlobalInput();
		PEdorSCBackConfirmBL tPEdorSCBackConfirmBL = new PEdorSCBackConfirmBL();

		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();

		tGlobalInput.ManageCom = "001";
		tGlobalInput.Operator = "Admin";
		tLPEdorItemSchema.setEdorNo("86110020040410010703");
		tLPEdorItemSchema.setEdorAppDate("2002-10-30");
		tLPEdorItemSchema.setEdorValiDate("2002-10-31");
		tLPEdorItemSchema.setOperator("Admin");

		tInputData.addElement(tLPEdorItemSchema);
		tInputData.addElement(tGlobalInput);

		tPEdorSCBackConfirmBL.submitData(tInputData, "INSERT||EDORCONFIRM");
	}

	public CErrors getErrors() {
		return mErrors;
	}
}
