package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpEdorMainSchema;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全删除业务逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author lh
 * @version 1.0
 */
// 功能：查询出个人批改主表中本集体单下的个单
// 入口参数：集体单的批改类型、批单号、集体保单号
// 出口参数：每条记录的个单的保单号、批单号和批改类型

public class GrpEdorAppCancelBL {
private static Logger logger = Logger.getLogger(GrpEdorAppCancelBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	private VData pInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	private LPGrpEdorMainSchema mLPGrpEdorMainSchema = new LPGrpEdorMainSchema();
	private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
	private GlobalInput mGlobalInput = new GlobalInput();

	public GrpEdorAppCancelBL() {
		logger.debug("BL");
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
		logger.debug("moperator:" + mOperate);
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData))
			return false;
		if (cOperate.equals("DELETE||EDOR")) {
			if (!checkData())
				return false;

			if (!prepareData())
				return false;
			if (!prepareGrpData())
				return false;
			GrpEdorAppCancelBLS tGrpEdorAppCancelBLS = new GrpEdorAppCancelBLS();
			if (!tGrpEdorAppCancelBLS.submitData(pInputData, mOperate))
				return false;

			logger.debug("----submit---");
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {

		// 查询条件
		logger.debug("moperator:" + mOperate);
		if (mOperate.equals("DELETE||EDOR")) {
			mLPGrpEdorItemSchema.setSchema((LPGrpEdorItemSchema) cInputData
					.getObjectByObjectName("LPGrpEdorItemSchema", 0));
		}

		return true;

	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean prepareData() {
		int m;
		int i;
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemSchema aLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		tLPEdorItemSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPEdorItemSchema.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		tLPEdorItemSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		// tLPEdorMainSchema.setEdorState("1");
		tLPEdorItemDB.setSchema(tLPEdorItemSchema);
		tLPEdorItemSet.set(tLPEdorItemDB.query());

		m = tLPEdorItemSet.size();

		for (i = 1; i <= m; i++) {
			aLPEdorItemSchema = tLPEdorItemSet.get(i);
			mInputData.clear();
			mInputData.add(aLPEdorItemSchema);
			EdorAppCancelBL tEdorAppCancelBL = new EdorAppCancelBL();
			if (!tEdorAppCancelBL.submitData(mInputData, mOperate)) {
				this.mErrors.copyAllErrors(tEdorAppCancelBL.mErrors);
				return false;
			}

		}

		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		tLPGrpEdorItemDB.setSchema(mLPGrpEdorItemSchema);
		tLPGrpEdorItemDB.setEdorState("1");
		if (!tLPGrpEdorItemDB.getInfo()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpEdorAppCancelBL";
			tError.functionName = "Checkdata";
			tError.errorMessage = "此保全已经经过了申请确认,不可进行删除!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 准备需要保存的集体单数据
	 */
	private boolean prepareGrpData() {

		String tGrpContNo, tEdorNo, tEdorType;

		tGrpContNo = mLPGrpEdorItemSchema.getGrpContNo();
		tEdorNo = mLPGrpEdorItemSchema.getEdorNo();
		tEdorType = mLPGrpEdorItemSchema.getEdorType();

		/*
		 * LPGrpPolSchema tLPGrpPolSchema=new LPGrpPolSchema();
		 * tLPGrpPolSchema.setGrpPolNo(tGrpPolNo);
		 * tLPGrpPolSchema.setEdorNo(tEdorNo);
		 * tLPGrpPolSchema.setEdorType(tEdorType); pInputData.clear();
		 * pInputData.add(tLPGrpPolSchema);
		 * 
		 * LPGrpSchema tLPGrpSchema=new LPGrpSchema();
		 * tLPGrpSchema.setEdorNo(tEdorNo); tLPGrpSchema.setEdorType(tEdorType);
		 * pInputData.add(tLPGrpSchema);
		 * 
		 * LPAppntGrpSchema tLPAppntGrpSchema=new LPAppntGrpSchema();
		 * tLPAppntGrpSchema.setEdorNo(tEdorNo);
		 * tLPAppntGrpSchema.setEdorType(tEdorType);
		 * pInputData.add(tLPAppntGrpSchema);
		 * 
		 * LPGUWErrorSchema tLPGrpUWErrorSchema = new LPGUWErrorSchema();
		 * tLPGrpUWErrorSchema.setEdorNo(tEdorNo);
		 * tLPGrpUWErrorSchema.setEdorType(tEdorType);
		 * pInputData.add(tLPGrpUWErrorSchema);
		 * 
		 * LPInsureAccSchema tLPInsureAccSchema=new LPInsureAccSchema();
		 * tLPInsureAccSchema.setEdorNo(tEdorNo);
		 * tLPInsureAccSchema.setEdorType(tEdorType);
		 * pInputData.add(tLPInsureAccSchema);
		 * 
		 * LPLoanSchema tLPLoanSchema=new LPLoanSchema();
		 * tLPLoanSchema.setEdorNo(tEdorNo);
		 * tLPLoanSchema.setEdorType(tEdorType); pInputData.add(tLPLoanSchema);
		 * 
		 * LPInsureAccTraceSchema tLPInsureAccTraceSchema=new
		 * LPInsureAccTraceSchema(); tLPInsureAccTraceSchema.setEdorNo(tEdorNo);
		 * tLPInsureAccTraceSchema.setEdorType(tEdorType);
		 * pInputData.add(tLPInsureAccTraceSchema);
		 * 
		 * LPGUWSubSchema tLPGrpUWSubSchema=new LPGUWSubSchema();
		 * tLPGrpUWSubSchema.setEdorNo(tEdorNo);
		 * tLPGrpUWSubSchema.setEdorType(tEdorType);
		 * pInputData.add(tLPGrpUWSubSchema);
		 * 
		 * LPGUWMasterSchema tLPGrpUWMasterSchema = new LPGUWMasterSchema();
		 * tLPGrpUWMasterSchema.setGrpPolNo(tGrpPolNo);
		 * tLPGrpUWMasterSchema.setEdorNo(tEdorNo);
		 * tLPGrpUWMasterSchema.setEdorType(tEdorType);
		 * pInputData.add(tLPGrpUWMasterSchema);
		 */

		LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
		tLPGrpEdorItemSchema.setGrpContNo(tGrpContNo);
		tLPGrpEdorItemSchema.setEdorNo(tEdorNo);
		tLPGrpEdorItemSchema.setEdorType(tEdorType);
		pInputData.add(tLPGrpEdorItemSchema);

		logger.debug(" prepare GrpData");
		// 新保加人, add by Minim
		if (tEdorType.equals("NI")) {
			logger.debug("开始处理新保加人...");
			LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
			tLCGrpPolDB.setGrpPolNo(tGrpContNo);
			if (!tLCGrpPolDB.getInfo())
				return false;
			logger.debug("End get lcgrppol...");

			LCPolSchema tLCPolSchema = new LCPolSchema();
			// tLCPolSchema.setGrpPolNo(tLCGrpPolDB.getGrpProposalNo());
			String strSql = "select * from lcpol where prtno='"
					+ tLCGrpPolDB.getPrtNo() + "' and appflag='2'";
			LCPolSet tLCPolSet = tLCPolSchema.getDB().executeQuery(strSql);
			pInputData.add(tLCPolSet);
		}

		// 新增附加险, add by Minim at 2004-2-5
		if (tEdorType.equals("NS")) {
			logger.debug("开始处理新增附加险...");
			LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
			tLCGrpPolDB.setGrpPolNo(tGrpContNo);
			if (!tLCGrpPolDB.getInfo())
				return false;
			logger.debug("End get lcgrppol...");

			LCGrpPolDB LCGrpPolDB2 = new LCGrpPolDB();
			LCGrpPolDB2.setPrtNo(tLCGrpPolDB.getPrtNo());
			LCGrpPolDB2.setAppFlag("2");
			LCGrpPolSet tLCGrpPolSet = LCGrpPolDB2.query();

			pInputData.add(tLCGrpPolSet);
		}

		return true;
	}

	public static void main(String[] args) {
		GrpEdorAppCancelBL tGrpEdorAppCancelBL = new GrpEdorAppCancelBL();
		VData tVData = new VData();
		LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
		tLPGrpEdorItemSchema.setEdorNo("00000120020420000103");
		tLPGrpEdorItemSchema.setGrpContNo("86110020020220000035");
		tLPGrpEdorItemSchema.setEdorType("BB");
		tLPGrpEdorItemSchema.setEdorState("1");
		tVData.addElement(tLPGrpEdorItemSchema);
		tGrpEdorAppCancelBL.submitData(tVData, "DELETE||EDOR");
	}
}
