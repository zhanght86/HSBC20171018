package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LPAppntBL;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPAddressSchema;
import com.sinosoft.lis.schema.LPAppntSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LPAddressSet;
import com.sinosoft.lis.vschema.LPAppntSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPPersonSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
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
 * @ReWrite ZhangRong
 * @version 1.0
 */
public class PEdorADDetailBL {
private static Logger logger = Logger.getLogger(PEdorADDetailBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap mMap = new MMap();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 全局数据 */
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private LPAppntSchema mLPAppntSchema = new LPAppntSchema();
	private LPAppntSet saveLPAppntSet = new LPAppntSet();
	private LPAddressSchema mLPAddressSchema = new LPAddressSchema();
	private LPAddressSet mLPAddressSet = new LPAddressSet();
	private LPPersonSet mLPPersonSet = new LPPersonSet();
	private LPContSet mLPContSet = new LPContSet();

	private LPContSet saveLPContSet = new LPContSet();

	private LPPolSet mLPPolSet = new LPPolSet();
	private GlobalInput mGlobalInput = new GlobalInput();
	private String currDate = PubFun.getCurrentDate();
	private String currTime = PubFun.getCurrentTime();

	public PEdorADDetailBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}

		// 数据查询业务处理
		if (cOperate.equals("QUERY||MAIN") || cOperate.equals("QUERY||DETAIL")) {
			if (!queryData()) {
				return false;
			} else {
				return true;
			}
		}

		// 数据校验操作
		if (!checkData()) {
			return false;
		}

		// 数据准备操作
		if (!dealData()) {
			return false;
		}

		// // 数据操作业务处理(新增insertData();修改updateData();删除deletedata())
		// if (mOperate.equals("INSERT||EDORAC"))
		// {
		// if (!insertData())
		// {
		// return false;
		// }
		// }
		//
		// if (mOperate.equals("INSERT||GRPEDORAC"))
		// {
		// PEdorADDetailBLS aPEdorADDetailBLS = new PEdorADDetailBLS();
		// mInputData.clear();
		// mInputData.addElement(mLPEdorItemSchema);
		// mInputData.addElement(mLPAppntSchema);
		// mInputData.addElement(mLPGrpEdorMainSchema);
		//
		// if (!aPEdorADDetailBLS.submitData(mInputData, cOperate))
		// {
		// return false;
		// }
		// }
		//
		// if (mOperate.equals("UPDATE||EDORAC"))
		// {
		// if (!updateData())
		// {
		// return false;
		// }
		// }
		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorICDetailBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	public boolean getSubmitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}

		// 数据查询业务处理
		if (cOperate.equals("QUERY||MAIN") || cOperate.equals("QUERY||DETAIL")) {
			if (!queryData()) {
				return false;
			} else {
				return true;
			}
		}

		// 数据准备操作
		if (!dealData()) {
			return false;
		}

		return true;
	}

	public VData getResult() {
		mResult.clear();
		return mResult;
	}

	/**
	 * 数据查询业务处理(queryData())
	 */
	private boolean queryData() {
		if (mOperate.equals("QUERY||MAIN")) {
			LPAppntBL tLPAppntBL = new LPAppntBL();
			LPAppntSet tLPAppntSet = new LPAppntSet();
			tLPAppntBL.setSchema(mLPAppntSchema);

			tLPAppntSet = tLPAppntBL.queryLPAppnt(mLPEdorItemSchema);

			String tReturn;
			tReturn = tLPAppntSet.encode();
			tReturn = "0|" + tLPAppntSet.size() + "^" + tReturn;

			mResult.clear();
			mResult.add(tReturn);
		} else if (mOperate.equals("QUERY||DETAIL")) {
			LPAppntSchema tLPAppntSchema = new LPAppntSchema();
			LPAppntBL tLPAppntBL = new LPAppntBL();
			tLPAppntBL.setSchema(mLPAppntSchema);

			if (!tLPAppntBL.queryLPAppnt(mLPAppntSchema)) {
				return false;
			}

			tLPAppntSchema = tLPAppntBL.getSchema();

			String tReturn = tLPAppntSchema.encode();
			mResult.clear();
			mResult.add(tReturn);
		} else {
		}

		return true;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return
	 */
	private boolean getInputData() {
		try {
			mLPAppntSchema = (LPAppntSchema) mInputData.getObjectByObjectName(
					"LPAppntSchema", 0);
			mLPAddressSchema = (LPAddressSchema) mInputData
					.getObjectByObjectName("LPAddressSchema", 0);
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);

			LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
			tLPEdorItemDB.setSchema(mLPEdorItemSchema);
			if (!tLPEdorItemDB.getInfo()) {
				CError.buildErr(this, "查询保全项目失败！");
				return false;
			}
			mLPEdorItemSchema.setSchema(tLPEdorItemDB.getSchema());

			mLPContSet = (LPContSet) mInputData.getObjectByObjectName(
					"LPContSet", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);

			if (mLPAppntSchema == null || mLPAddressSchema == null
					|| mLPEdorItemSchema == null || mLPContSet == null) {
				return false;
			}
		} catch (Exception e) {
			CError.buildErr(this, "接收数据失败");
			return false;
		}

		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		boolean flag = true;
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setSchema(mLPEdorItemSchema);

		if (!tLPEdorItemDB.getInfo()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PAppntIndBL";
			tError.functionName = "checkData";
			tError.errorMessage = "无保全申请数据!";
			logger.debug("------" + tError);
			this.mErrors.addOneError(tError);

			return false;
		}

		if (!tLPEdorItemDB.getEdorState().trim().equals("1")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PAppntIndBL";
			tError.functionName = "checkData";
			tError.errorMessage = "该保全已经申请确认不能修改!";
			logger.debug("------" + tError);
			this.mErrors.addOneError(tError);

			return false;
		}

		return flag;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean dealData() {

		// 首先更新其后的保全项目的状态
		mMap.put(UpdateEdorState.getUpdateEdorStateSql(mLPEdorItemSchema),
				"UPDATE");
		// 准备个人批改主表的信息

		LPAddressSchema tLPAddressSchema = new LPAddressSchema();
		int m;

		if (StrTool.compareString(String.valueOf(mLPAddressSchema
				.getAddressNo()), "")) {
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			String sql = "Select Case When max(AddressNo) Is Null Then 1 Else max(AddressNo)  End from LCAddress where CustomerNo='"
					+ "?CustomerNo?" + "'";
            SQLwithBindVariables sqlbv=new SQLwithBindVariables();
            sqlbv.sql(sql);
            sqlbv.put("CustomerNo", mLPAppntSchema.getAppntNo());
			tSSRS = tExeSQL.execSQL(sqlbv);
			Integer firstinteger = Integer.valueOf(tSSRS.GetText(1, 1));
			int ttNo = firstinteger.intValue() + 1;
			// Integer integer = new Integer(ttNo);
			logger.debug("得到的地址码是：" + ttNo);

			mLPAddressSchema.setAddressNo("" + ttNo);

		}
		mLPAddressSchema.setOperator(mGlobalInput.Operator);
		mLPAddressSchema.setMakeDate(PubFun.getCurrentDate());
		mLPAddressSchema.setMakeTime(PubFun.getCurrentTime());
		mLPAddressSchema.setModifyDate(PubFun.getCurrentDate());
		mLPAddressSchema.setModifyTime(PubFun.getCurrentTime());
		mLPAddressSet.add(mLPAddressSchema);

		logger.debug("00000");
		for (int i = 1; i <= mLPContSet.size(); i++) {
			LPAppntSchema tLPAppntSchema = new LPAppntSchema();
			Reflections tReflections = new Reflections();
			logger.debug("00001");
			LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
			logger.debug("00002");
			tLPEdorItemSchema.setSchema(mLPEdorItemSchema);
			logger.debug("00003");
			LPContSchema tLPContSchema = new LPContSchema();
			tLPContSchema = mLPContSet.get(i).getSchema();
			logger.debug("00006");
			tLPEdorItemSchema.setContNo(tLPContSchema.getContNo());
			logger.debug("00004");
			LPAppntBL tLPAppntBL = new LPAppntBL();
			logger.debug("00005");
			LPAppntSet tLPAppntSet = new LPAppntSet();
			logger.debug("111111" + mLPContSet.get(i).getContNo());
			tLPAppntBL.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPAppntBL.setContNo(mLPContSet.get(i).getContNo());
			tLPAppntBL.setAppntNo(mLPAddressSchema.getCustomerNo());
			tLPAppntBL.queryLPAppnt(tLPAppntBL.getSchema());

			if (tLPAppntBL.mErrors.needDealError()) {
				// @@错误处理
				CError.buildErr(this, "查询投保人信息失败！");
				return false;
			} else {
				tLPAppntSchema.setSchema(tLPAppntBL.getSchema());
			}
			tLPAppntSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPAppntSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPAppntSchema.setAddressNo(String.valueOf(mLPAddressSchema
					.getAddressNo()));
			saveLPAppntSet.add(tLPAppntSchema);
		}
		logger.debug("222222");
		mLPEdorItemSchema.setEdorState("1");// 录入完成
		mLPEdorItemSchema.setModifyDate(currDate);
		mLPEdorItemSchema.setModifyTime(currTime);
		String sql = "delete from LPAppnt where edorno in (select edorno from lpedormain where edoracceptno='"
				+ "?edoracceptno?"
				+ "') and edortype='"
				+ "?edortype?" + "'";
		SQLwithBindVariables sbv=new SQLwithBindVariables();
		sbv.sql(sql);
		sbv.put("edoracceptno", mLPEdorItemSchema.getEdorAcceptNo());
		sbv.put("edortype", mLPEdorItemSchema.getEdorType());
		mMap.put(sbv, "DELETE");
		mMap.put(saveLPAppntSet, "DELETE&INSERT");
		mMap.put(mLPAddressSet, "DELETE&INSERT");
		mMap.put(mLPEdorItemSchema, "UPDATE");

		mResult.clear();
		mResult.add(mMap);

		return true;
	}
}
