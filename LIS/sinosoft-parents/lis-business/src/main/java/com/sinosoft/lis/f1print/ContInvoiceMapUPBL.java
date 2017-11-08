package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LDContInvoiceMapSchema;
import com.sinosoft.lis.vschema.LDContInvoiceMapSet;
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
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class ContInvoiceMapUPBL {
private static Logger logger = Logger.getLogger(ContInvoiceMapUPBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private VData mInputData = new VData();
	private LDContInvoiceMapSet tSet = new LDContInvoiceMapSet();

	private MMap map = new MMap();

	public ContInvoiceMapUPBL() {
	}

	public boolean submitData(VData cInputData, String cOperator) {
		mInputData = (VData) cInputData.clone();
		if (!getInputData()) {
			return false;
		}
		if (!dealData()) {
			return false;
		}
		mInputData.clear();
		mInputData.add(map);
		PubSubmit tsubmit = new PubSubmit();
		if (!tsubmit.submitData(mInputData, "")) {
			buildError("getInputData", tsubmit.mErrors.getFirstError());
			return false;
		}
		return true;
	}

	private boolean dealData() {
		LDContInvoiceMapSchema tSchema;
		try {
			String batchNo = tSet.get(1).getBatchNo();
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			String sql = "delete from ldcontinvoicemap where batchno = '"
					+ "?batchNo?" + "'";
			sqlbv.sql(sql);
			sqlbv.put("batchNo", batchNo);
			map.put(sqlbv, "DELETE");
			for (int i = 1; i <= tSet.size(); i++) {
				tSchema = tSet.get(i);
				tSchema.setModifyDate(PubFun.getCurrentDate());
				tSchema.setModifyTime(PubFun.getCurrentTime());
				map.put(tSchema, "INSERT");
			}
		} catch (Exception e) {
			e.printStackTrace();
			buildError("getInputData", "数据处理错误");
			return false;
		}
		return true;
	}

	private boolean getInputData() {
		tSet = (LDContInvoiceMapSet) mInputData.getObjectByObjectName(
				"LDContInvoiceMapSet", 0);
		if (tSet == null || tSet.size() == 0) {
			buildError("getInputData", "提取输入数据错误");
			return false;
		}
		return true;
	}

	/**
	 * 出错处理
	 * 
	 * @param szFunc
	 *            String
	 * @param szErrMsg
	 *            String
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "LCContF1PBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
		ContInvoiceMapUPBL continvoicemapupbl = new ContInvoiceMapUPBL();
	}
}
