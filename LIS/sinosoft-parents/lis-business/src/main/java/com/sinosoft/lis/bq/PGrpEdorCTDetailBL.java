package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LPEdorMainDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPEdorMainSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
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
public class PGrpEdorCTDetailBL {
private static Logger logger = Logger.getLogger(PGrpEdorCTDetailBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
	private LPEdorMainSet saveLPEdorMainSet = new LPEdorMainSet();
	private LPEdorItemSet saveLPEdorItemSet = new LPEdorItemSet();
	private LPPolSet saveLPPolSet = new LPPolSet();
	private LPContSet saveLPContSet = new LPContSet();

	/** 全局数据 */
	private Reflections ref = new Reflections();
	private GlobalInput mGlobalInput = new GlobalInput();
	MMap map = new MMap();
	private String currDate = PubFun.getCurrentDate();
	private String currTime = PubFun.getCurrentTime();

	boolean newaddrFlag = false;

	public PGrpEdorCTDetailBL() {
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
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		getInputData(cInputData);

		// 数据校验操作（checkdata)
		if (!checkData())
			return false;
		// 数据准备操作（preparedata())
		if (!prepareData())
			return false;

		logger.debug("---oper" + cOperate);

		if (!prepareOutputData())
			return false;
		logger.debug("---prepareOutputData---");

		PubSubmit tPubSubmit = new PubSubmit();
		// tPubSubmit.submitData(mInputData, cOperate);
		if (tPubSubmit.submitData(mResult, cOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			return false;
		}

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		map.put(saveLPEdorMainSet, "DELETE&INSERT");
		map.put(saveLPEdorItemSet, "DELETE&INSERT");
		map.put(saveLPContSet, "DELETE&INSERT");
		map.put(saveLPPolSet, "DELETE&INSERT");
		mResult.clear();
		mResult.add(map);
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private void getInputData(VData cInputData) {

		mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) cInputData
				.getObjectByObjectName("LPGrpEdorItemSchema", 0);

		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {

		boolean flag = true;
		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		tLPGrpEdorItemDB.setSchema(mLPGrpEdorItemSchema);
		if (!tLPGrpEdorItemDB.getInfo()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PAppntGrpBL";
			tError.functionName = "Preparedata";
			tError.errorMessage = "无保全项目数据!";
			logger.debug("------" + tError);
			this.mErrors.addOneError(tError);
			return false;
		}
		if (!tLPGrpEdorItemDB.getEdorState().trim().equals("1")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PAppntGrpBL";
			tError.functionName = "Preparedata";
			tError.errorMessage = "该保全项目已经申请确认不能修改!";
			logger.debug("------" + tError);
			this.mErrors.addOneError(tError);
			return false;
		}
		mLPGrpEdorItemSchema.setSchema(tLPGrpEdorItemDB.getSchema());
		return flag;

	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean prepareData() {
		LCContSet tLCContSet = new LCContSet();
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		tLCContDB.setAppFlag("1");
		tLCContSet = tLCContDB.query();
		if (tLCContDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询该团单下的个人保单失败！");
			return false;
		}
		for (int i = 1; i <= tLCContSet.size(); i++) {
			LPEdorMainSchema tLPEdorMainSchema = new LPEdorMainSchema();
			LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
			LCContSchema tLCContSchema = new LCContSchema();
			tLCContSchema = tLCContSet.get(i);
			ref.transFields(tLPEdorItemSchema, mLPGrpEdorItemSchema);
			tLPEdorItemSchema.setContNo(tLCContSchema.getContNo());
			tLPEdorItemSchema.setInsuredNo("000000");
			tLPEdorItemSchema.setPolNo("000000");
			tLPEdorItemSchema.setManageCom(mGlobalInput.ManageCom);
			tLPEdorItemSchema.setOperator(mGlobalInput.Operator);
			tLPEdorItemSchema.setUWFlag("0");
			tLPEdorItemSchema.setMakeDate(currDate);
			tLPEdorItemSchema.setMakeTime(currTime);
			tLPEdorItemSchema.setModifyDate(currDate);
			tLPEdorItemSchema.setModifyTime(currTime);
			tLPEdorItemSchema.setEdorState("1");
			saveLPEdorItemSet.add(tLPEdorItemSchema);

			LPContSchema tLPContSchema = new LPContSchema();
			ref.transFields(tLPContSchema, tLCContSchema);
			tLPContSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
			tLPContSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
			tLPContSchema.setMakeDate(currDate);
			tLPContSchema.setMakeTime(currTime);
			tLPContSchema.setModifyDate(currDate);
			tLPContSchema.setModifyTime(currTime);
			saveLPContSet.add(tLPContSchema);

			LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
			tLPEdorMainDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
			tLPEdorMainDB.setContNo(tLCContSchema.getContNo());
			LPEdorMainSet tLPEdorMainSet = new LPEdorMainSet();
			tLPEdorMainSet = tLPEdorMainDB.query();
			if (tLPEdorMainDB.mErrors.needDealError()) {
				CError.buildErr(this, "查询个人保全主表失败!");
				return false;
			}
			if (tLPEdorMainSet.size() == 0) {
				ref.transFields(tLPEdorMainSchema, mLPGrpEdorItemSchema);
				tLPEdorMainSchema.setContNo(tLCContSchema.getContNo());
				tLPEdorMainSchema.setManageCom(mGlobalInput.ManageCom);
				tLPEdorMainSchema.setOperator(mGlobalInput.Operator);
				tLPEdorMainSchema.setUWState("0");
				tLPEdorMainSchema.setEdorState("1");
				tLPEdorMainSchema.setMakeDate(currDate);
				tLPEdorMainSchema.setMakeTime(currTime);
				tLPEdorMainSchema.setModifyDate(currDate);
				tLPEdorMainSchema.setModifyTime(currTime);
				saveLPEdorMainSet.add(tLPEdorMainSchema);
			}

		}
		LCPolSet tLCPolSet = new LCPolSet();
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		tLCPolDB.setAppFlag("1");
		tLCPolSet = tLCPolDB.query();
		if (tLCPolDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询该团单下的个人保单险种失败！");
			return false;
		}
		for (int i = 1; i <= tLCPolSet.size(); i++) {

			LCPolSchema tLCPolSchema = new LCPolSchema();
			tLCPolSchema = tLCPolSet.get(i);
			LPPolSchema tLPPolSchema = new LPPolSchema();
			ref.transFields(tLPPolSchema, tLCPolSchema);
			tLPPolSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
			tLPPolSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
			tLPPolSchema.setMakeDate(currDate);
			tLPPolSchema.setMakeTime(currTime);
			tLPPolSchema.setModifyDate(currDate);
			tLPPolSchema.setModifyTime(currTime);
			saveLPPolSet.add(tLPPolSchema);
		}

		return true;

	}

}
