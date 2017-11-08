package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCInsureAccClassDB;
import com.sinosoft.lis.db.LCInsureAccDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPInsureAccClassDB;
import com.sinosoft.lis.db.LPInsureAccDB;
import com.sinosoft.lis.db.LPInsureAccTraceDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCInsureAccClassSchema;
import com.sinosoft.lis.schema.LCInsureAccSchema;
import com.sinosoft.lis.schema.LCInsureAccTraceSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPInsureAccClassSchema;
import com.sinosoft.lis.schema.LPInsureAccSchema;
import com.sinosoft.lis.schema.LPInsureAccTraceSchema;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCInsureAccTraceSet;
import com.sinosoft.lis.vschema.LPInsureAccClassSet;
import com.sinosoft.lis.vschema.LPInsureAccSet;
import com.sinosoft.lis.vschema.LPInsureAccTraceSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

// import com.sinosoft.lis.operfee.*;
/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 部分领取回退确认BL
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007-06-01
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Nicholas
 * @version 1.0
 */
public class PEdorOPConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(PEdorOPConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	//public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap mMap = new MMap();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	private Reflections mReflections = new Reflections();
	/** 业务数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	public PEdorOPConfirmBL() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括""和""
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("---End getInputData---");
		// 得到外部传入的数据,将数据备份到本类中
		if (!checkData()) {
			return false;
		}
		logger.debug("---End checkData---");
		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		// 准备提交后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("---End dealData---");
		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return this.mErrors;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			// 需要回退的保全项目
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
		} catch (Exception e) {
			e.printStackTrace();
			return this.makeError("getInputData", "接收前台数据失败！");
		}
		if (mGlobalInput == null || mLPEdorItemSchema == null) {
			return this.makeError("getInputData", "传入数据有误！");
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
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
		mLPEdorItemSchema.setSchema(tLPEdorItemDB.query().get(1));

		return true;
	}

	private boolean prepareOutputData() {
		try {
			mResult.clear();
			mResult.add(mMap);
		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr(this, "在准备往后层处理所需要的数据时出错!");
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		try {
			// 获得此时的日期和时间
			String strCurrentDate = PubFun.getCurrentDate();
			String strCurrentTime = PubFun.getCurrentTime();
			String tSql = "";

			// 处理Trace表
			tSql = "delete from LPInsureAccTrace where edortype ='OP' and edorno ='?edorno?'";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSql);
			sqlbv.put("edorno", mLPEdorItemSchema.getEdorNo());
			mMap.put(sqlbv, "DELETE");
			
			LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet();
			LPInsureAccTraceDB tLPInsureAccTraceDB = new LPInsureAccTraceDB();
			LPInsureAccTraceSchema tLPInsureAccTraceSchema = new LPInsureAccTraceSchema();
			LPInsureAccTraceSet tLPInsureAccTraceSet = new LPInsureAccTraceSet();
			tLPInsureAccTraceDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPInsureAccTraceDB.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPInsureAccTraceDB.setContNo(mLPEdorItemSchema.getContNo());
			tLPInsureAccTraceSet = tLPInsureAccTraceDB.query();
			for (int i = 1; i <= tLPInsureAccTraceSet.size(); i++) {
				tLPInsureAccTraceSchema = tLPInsureAccTraceSet.get(i);
				LCInsureAccTraceSchema tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
				mReflections.transFields(tLCInsureAccTraceSchema,
						tLPInsureAccTraceSchema);
				tLCInsureAccTraceSchema.setModifyDate(strCurrentDate);
				tLCInsureAccTraceSchema.setModifyTime(strCurrentTime);
				tLCInsureAccTraceSchema.setOperator(mGlobalInput.Operator);
				tLCInsureAccTraceSet.add(tLCInsureAccTraceSchema);
			}
			mMap.put(tLCInsureAccTraceSet, "DELETE&INSERT");
		} catch (Exception e) {
			// @@错误处理
			return this.makeError("dealData", "数据处理错误！" + e.getMessage());
		}

		return true;
	}

	/**
	 * 创建一个错误
	 * 
	 * @param vFuncName
	 *            发生错误的函数名
	 * @param vErrMsg
	 *            错误信息
	 * @return 布尔值（false--永远返回此值）
	 */
	private boolean makeError(String vFuncName, String vErrMsg) {
		CError tError = new CError();
		tError.moduleName = "PEdorOPBackConfirmBL";
		tError.functionName = vFuncName;
		tError.errorMessage = vErrMsg;
		this.mErrors.addOneError(tError);
		return false;
	}
}
