package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class PEdorAADetailBLF implements EdorDetail {
private static Logger logger = Logger.getLogger(PEdorAADetailBLF.class);

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
	LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	GlobalInput mGlobalInput = new GlobalInput();
	LPDutySchema mLPDutySchema = new LPDutySchema();
	LCCustomerImpartSet mLCCustomerImpartSet = new LCCustomerImpartSet();

	public VData getResult() {
		return mResult;
	}

	public PEdorAADetailBLF() {
	}

	public boolean submitData(VData aInputData, String aOperator) {
		mInputData = (VData) aInputData.clone();
		mOperate = aOperator;

		if (!getinputdata(mInputData)) {
			return false;
		}

		if (!checkdata()) {
			return false;
		}

		if (mOperate.trim().equals("DELETE")) {
			String delcont = "delete from lpcont where edorno = '"
					+ "?edorno?" + "' and edortype = 'AA'";
			String delpol = "delete from lppol where edorno = '"
					+ "?edorno?" + "' and edortype = 'AA'";
			String delduty = "delete from lpduty where edorno = '"
					+ "?edorno?" + "' and edortype = 'AA'";
			String delprem = "delete from lpprem where edorno = '"
					+ "?edorno?" + "' and edortype = 'AA'";
			String delget = "delete from lpget where edorno = '"
					+ "?edorno?" + "' and edortype = 'AA'";
			String delendorse = "delete from ljsgetendorse where endorsementno = '"
					+ "?endorsementno?" + "'";
			String delpayperson = "delete from ljspayperson where contno = '"
					+ "?contno?" + "' and paytype = 'AA'";
		    SQLwithBindVariables sbv1=new SQLwithBindVariables();
		    sbv1.sql(delcont);
		    sbv1.put("edorno", mLPEdorItemSchema.getEdorNo());
		    SQLwithBindVariables sbv2=new SQLwithBindVariables();
		    sbv2.sql(delpol);
		    sbv2.put("edorno", mLPEdorItemSchema.getEdorNo());
		    SQLwithBindVariables sbv3=new SQLwithBindVariables();
		    sbv3.sql(delduty);
		    sbv3.put("edorno", mLPEdorItemSchema.getEdorNo());
		    SQLwithBindVariables sbv4=new SQLwithBindVariables();
		    sbv4.sql(delprem);
		    sbv4.put("edorno", mLPEdorItemSchema.getEdorNo());
		    SQLwithBindVariables sbv5=new SQLwithBindVariables();
		    sbv5.sql(delget);
		    sbv5.put("edorno", mLPEdorItemSchema.getEdorNo());
		    SQLwithBindVariables sbv6=new SQLwithBindVariables();
		    sbv6.sql(delendorse);
		    sbv6.put("endorsementno", mLPEdorItemSchema.getEdorNo());
		    SQLwithBindVariables sbv7=new SQLwithBindVariables();
		    sbv7.sql(delpayperson);
		    sbv7.put("contno", mLPEdorItemSchema.getEdorNo());
			MMap map = new MMap();
			map.put(sbv1, "DELETE");
			map.put(sbv2, "DELETE");
			map.put(sbv3, "DELETE");
			map.put(sbv4, "DELETE");
			map.put(sbv5, "DELETE");
			map.put(sbv6, "DELETE");
			map.put(sbv7, "DELETE");
			mResult.clear();
			mResult.add(map);
		} else {
			if (!dealdata()) {
				return false;
			}
		}
		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorPTDetailBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	private boolean getinputdata(VData cInputData) {
		try {
			mLPEdorItemSchema = (LPEdorItemSchema) cInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
			mLPDutySchema = (LPDutySchema) cInputData.getObjectByObjectName(
					"LPDutySchema", 0);
			mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
					"GlobalInput", 0);
			mLCCustomerImpartSet = (LCCustomerImpartSet) cInputData
					.getObjectByObjectName("LCCustomerImpartSet", 0);
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "PEdorAADetailBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	private boolean checkdata() {
		return true;
	}

	private boolean dealdata() {
		PEdorAADetailBL tPEdorAADetailBL = new PEdorAADetailBL();
		if (!tPEdorAADetailBL.submitData(mInputData, mOperate)) {
			this.mErrors.copyAllErrors(tPEdorAADetailBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorLRDetailBLF";
			tError.functionName = "dealData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mResult.clear();
		mResult = tPEdorAADetailBL.getResult();
		return true;
	}

	public CErrors getErrors() {
		return mErrors;
	}

}
