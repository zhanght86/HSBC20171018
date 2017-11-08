package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LPInsuredBL;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LPInsuredSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 团体保单被保险人资料变更项目明细功能类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Tjj
 * @ReWrite ZhangRong
 * @version 1.0
 */
public class PGrpEdorICDetailBL {
private static Logger logger = Logger.getLogger(PGrpEdorICDetailBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	/**  */
	private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	private LPInsuredSchema mLPInsuredSchema = new LPInsuredSchema();
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	public PGrpEdorICDetailBL() {
	}

	/**
	 * 设置操作方法
	 */
	public void setOperate(String cOperate) {
		this.mOperate = cOperate;
	}

	/**
	 * 获取操作方法
	 */
	public String getOperate() {
		return this.mOperate;
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return: 操作成功返回true，失败返回false
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.setOperate(cOperate);

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		// 数据查询业务处理(queryData())
		if (cOperate.equals("QUERY||MAIN") || cOperate.equals("QUERY||DETAIL")) {
			if (!queryData()) {
				return false;
			} else {
				return true;
			}
		}

		// 数据校验操作（checkdata)
		if (!checkData()) {
			return false;
		}
		// 数据准备操作（preparedata())
		if (!prepareData()) {
			return false;
		}

		cOperate = this.getOperate();

		// 数据操作业务处理(新增insertData();修改updateData();删除deletedata())
		if (cOperate.equals("INSERT||GRPMAIN")) {
			PEdorICDetailBL aPEdorICDetailBL = new PEdorICDetailBL();
			if (!aPEdorICDetailBL.submitData(mInputData, cOperate)) {
				this.mErrors.copyAllErrors(aPEdorICDetailBL.mErrors);
				return false;
			}
		}
		return true;
	}

	/**
	 * 获得操作结果
	 * 
	 * @param: void
	 * @return:结果数据对象集合
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @param: cInputData 输入数据
	 * @return:如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		mLPInsuredSchema = (LPInsuredSchema) cInputData.getObjectByObjectName(
				"LPInsuredSchema", 0);
		mLPEdorItemSchema = (LPEdorItemSchema) cInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) cInputData
				.getObjectByObjectName("LPGrpEdorItemSchema", 0);
		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);
		return true;
	}

	/**
	 * 数据查询业务处理(queryData())
	 * 
	 * @param: void
	 * @return:如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean queryData() {
		if (this.getOperate().equals("QUERY||DETAIL")) {
			LPInsuredBL tLPInsuredBL = new LPInsuredBL();

			LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();

			tLPInsuredBL.queryLPInsured(mLPInsuredSchema);
			tLPInsuredSchema.setSchema(tLPInsuredBL.getSchema());

			String tReturn = tLPInsuredSchema.encode();
			mInputData.clear();
			mInputData.add(tLPInsuredSchema);

			mResult.clear();
			mResult.add(tReturn);
		} else if (this.getOperate().equals("QUERY||MAIN")) {
			String tSql = "select * from LCInsured where ContNo in ( select ContNo from LCPol where GrpContNo='"
					+ mLPGrpEdorItemSchema.getGrpContNo()
					+ "' and appflag='1')";
			String tReturn;
			LPInsuredSet tLPInsuredSet = new LPInsuredSet();

			if (mLPEdorItemSchema.getContNo() != null
					&& !mLPEdorItemSchema.getContNo().trim().equals("")) {
				tSql = tSql.trim() + " and ContNo='"
						+ mLPEdorItemSchema.getContNo() + "'";
			}

			if (mLPEdorItemSchema.getInsuredNo() != null
					&& !mLPEdorItemSchema.getInsuredNo().trim().equals("")) {
				tSql = tSql.trim() + " and CustomerNo='"
						+ mLPEdorItemSchema.getInsuredNo() + "'";
			}

			LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema.setSchema(mLPEdorItemSchema);
			Reflections tRef = new Reflections();
			LCInsuredDB tLCInsuredDB = new LCInsuredDB();
			LCInsuredSet tLCInsuredSet = new LCInsuredSet();
			tLCInsuredSet = tLCInsuredDB.executeQuery(tSql);

			for (int i = 1; i <= tLCInsuredSet.size(); i++) {
				LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
				tRef.transFields(tLPInsuredSchema, tLCInsuredSet.get(i));
				tLPInsuredSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
				tLPInsuredSchema
						.setEdorType(mLPGrpEdorItemSchema.getEdorType());

				LPInsuredBL tLPInsuredBL = new LPInsuredBL();
				if (tLPInsuredBL.queryLPInsured(tLPInsuredSchema)) {
					tLPInsuredSchema = tLPInsuredBL.getSchema();
				}
				tLPInsuredSet.add(tLPInsuredBL.getSchema());

			}
			tReturn = tLPInsuredSet.encode();
			if (tReturn != null && !tReturn.equals("")) {
				tReturn = "0|" + tLPInsuredSet.size() + "^" + tReturn;
			}
			mInputData.clear();
			mInputData.add(tLPInsuredSet);

			mResult.clear();
			mResult.add(tReturn);
		} else {
		}
		return true;
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
			tError.moduleName = "PGrpEdorICDetailBL";
			tError.functionName = "Preparedata";
			tError.errorMessage = "无保全申请数据!";
			logger.debug("------" + tError);
			this.mErrors.addOneError(tError);
			return false;
		}
		mLPGrpEdorItemSchema.setSchema(tLPGrpEdorItemDB.getSchema());

		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setSchema(mLPEdorItemSchema);
		if (tLPEdorItemDB.getInfo()) {
			mLPEdorItemSchema.setSchema(tLPEdorItemDB.getSchema());
			if (!tLPEdorItemDB.getEdorState().trim().equals("1")) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PGrpEdorICDetailBL";
				tError.functionName = "Preparedata";
				tError.errorMessage = "该保全已经申请确认不能修改!";
				logger.debug("------" + tError);
				this.mErrors.addOneError(tError);
				return false;
			}
		}
		return flag;

	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean prepareData() {
		mLPEdorItemSchema = this.initLPEdorItemSchema();

		mInputData.clear();
		mInputData.add(mLPEdorItemSchema);
		mInputData.add(mLPInsuredSchema);
		mInputData.add(mGlobalInput);
		mResult.clear();
		mResult.add(mLPEdorItemSchema);
		mResult.add(mLPInsuredSchema);
		mResult.add(mGlobalInput);
		return true;

	}

	private LPEdorItemSchema initLPEdorItemSchema() {
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		tLPEdorItemSchema.setEdorAcceptNo(mLPGrpEdorItemSchema
				.getEdorAcceptNo());
		tLPEdorItemSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPEdorItemSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		tLPEdorItemSchema.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		tLPEdorItemSchema.setEdorAppNo(mLPGrpEdorItemSchema.getEdorAppNo());
		tLPEdorItemSchema.setEdorValiDate(mLPGrpEdorItemSchema
				.getEdorValiDate());
		tLPEdorItemSchema.setEdorAppDate(mLPGrpEdorItemSchema.getEdorAppDate());
		tLPEdorItemSchema.setContNo(mLPInsuredSchema.getContNo());
		tLPEdorItemSchema.setPolNo("000000");
		tLPEdorItemSchema.setInsuredNo(mLPInsuredSchema.getInsuredNo());
		return tLPEdorItemSchema;
	}
}
