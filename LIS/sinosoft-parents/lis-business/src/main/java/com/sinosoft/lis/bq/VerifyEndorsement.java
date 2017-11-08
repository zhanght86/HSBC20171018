package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LMEdorItemVertifyDB;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.vschema.LMEdorItemVertifySet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 承保暂交费业务逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Tjj
 * @version 1.0
 */
public class VerifyEndorsement {
private static Logger logger = Logger.getLogger(VerifyEndorsement.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	public VerifyEndorsement() {
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
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 校验保全明细
	 * 
	 * @param cInputData
	 * @param cOperate
	 * @return
	 */
	public boolean verifyEdorDetail(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		this.setOperate(cOperate);
		mResult.clear();

		if (this.getOperate().equals("VERIFY||DETAIL")) {
			LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
			BqCalBase tBqCalBase = new BqCalBase();
			tBqCalBase.setEdorNo(tLPEdorItemSchema.getEdorNo());
			tBqCalBase.setEdorType(tLPEdorItemSchema.getEdorType());
			tBqCalBase.setPolNo(tLPEdorItemSchema.getPolNo());

			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(tBqCalBase.getPolNo());
			if (!tLCPolDB.getInfo()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "VerifyEndorsement";
				tError.functionName = "verifyEdorDetail";
				tError.errorMessage = "校验保单不存在!";
				this.mErrors.addOneError(tError);
				return false;
			}
			LMEdorItemVertifySet tLMEdorItemVertifySet = new LMEdorItemVertifySet();
			LMEdorItemVertifyDB tLMEdorItemVertifyDB = new LMEdorItemVertifyDB();

			tLMEdorItemVertifyDB.setEdorCode(tLPEdorItemSchema.getEdorType());
			tLMEdorItemVertifyDB.setRiskCode(tLCPolDB.getRiskCode());
			tLMEdorItemVertifyDB.setAppObj("I");
			String tSql = "select * from LMEdorItemVertify where EdorCode='?EdorCode?' and RiskCode='000000' and AppObj='I' order by VertifyOrder";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSql);
			sqlbv.put("EdorCode", tLPEdorItemSchema.getEdorType());
			tLMEdorItemVertifySet = tLMEdorItemVertifyDB.executeQuery(sqlbv);
			if (tLMEdorItemVertifySet.size() > 0) {
				for (int i = 1; i <= tLMEdorItemVertifySet.size(); i++) {
					String tCalCode = tLMEdorItemVertifySet.get(i).getCalCode();
					BqCalBL tBqCalBL = new BqCalBL(tBqCalBase, tCalCode, "");
					if (!tBqCalBL.calValiEndorse("Y")) {
						mResult.add(tLMEdorItemVertifySet.get(i)
								.getVertifyInfo());
					}
				}
			}
		} else if (this.getOperate().equals("VERIFY||GRPDETAIL")) {
			LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
			tLPGrpEdorItemSchema = (LPGrpEdorItemSchema) mInputData
					.getObjectByObjectName("LPGrpEdorItemSchema", 0);
			BqCalBase tBqCalBase = new BqCalBase();
			tBqCalBase.setEdorNo(tLPGrpEdorItemSchema.getEdorNo());
			tBqCalBase.setEdorType(tLPGrpEdorItemSchema.getEdorType());
			tBqCalBase.setGrpContNo(tLPGrpEdorItemSchema.getGrpContNo());

			LCGrpContDB tLCGrpContDB = new LCGrpContDB();
			tLCGrpContDB.setGrpContNo(tBqCalBase.getGrpContNo());
			if (!tLCGrpContDB.getInfo()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "VerifyEndorsement";
				tError.functionName = "verifyEdorDetail";
				tError.errorMessage = "校验集体保单不存在!";
				this.mErrors.addOneError(tError);
				return false;
			}
			LMEdorItemVertifySet tLMEdorItemVertifySet = new LMEdorItemVertifySet();
			LMEdorItemVertifyDB tLMEdorItemVertifyDB = new LMEdorItemVertifyDB();

			tLMEdorItemVertifyDB
					.setEdorCode(tLPGrpEdorItemSchema.getEdorType());
			// tLMEdorItemVertifyDB.setRiskCode(tLCGrpContDB.getRiskCode());
			tLMEdorItemVertifyDB.setAppObj("G");
			String tSql = "select * from LMEdorItemVertify where EdorCode='?EdorCode?' and RiskCode='000000' and AppObj='G' order by VertifyOrder";
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(tSql);
			sqlbv1.put("EdorCode", tLPGrpEdorItemSchema.getEdorType());
			tLMEdorItemVertifySet = tLMEdorItemVertifyDB.executeQuery(sqlbv1);
			if (tLMEdorItemVertifySet.size() > 0) {
				for (int i = 1; i <= tLMEdorItemVertifySet.size(); i++) {
					String tCalCode = tLMEdorItemVertifySet.get(i).getCalCode();
					BqCalBL tBqCalBL = new BqCalBL(tBqCalBase, tCalCode, "");
					if (!tBqCalBL.calValiEndorse("Y")) {
						mResult.add(tLMEdorItemVertifySet.get(i)
								.getVertifyInfo());
					}
				}
			}
		} else {

		}
		return true;
	}

	public static void main(String[] args) {
		VData tInputData = new VData();
		GlobalInput tGlobalInput = new GlobalInput();
		VerifyEndorsement aVerifyEndorsement = new VerifyEndorsement();
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		tGlobalInput.ManageCom = "86110000";
		tGlobalInput.Operator = "Admin";

		tLPEdorItemSchema.setPolNo("86110020020210000222");

		tInputData.addElement(tLPEdorItemSchema);
		tInputData.addElement(tGlobalInput);

		aVerifyEndorsement.submitData(tInputData, "INSERT||EDORAPPCONFIRM");
	}

}
