

/**
 * <p>ClassName: RIBsnsBillUWBL.java </p>
 * <p>Description: 账单审核 </p>
 * <p>Copyright: Copyright (c) 2009 </p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 * @CreateDate：2011/6/14
 */

//包名
//package com.sinosoft.lis.config;
package com.sinosoft.lis.reinsure;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.RIBsnsBillBatchSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class RIBsnsBillUWBL {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 数据操作字符串 */
	private String mOperate;

	/** 业务处理相关变量 */
	private RIBsnsBillBatchSchema mRIBsnsBillBatchSchema = new RIBsnsBillBatchSchema();

	private MMap map = new MMap();

	public RIBsnsBillUWBL() {

	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mInputData = cInputData;
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}

		if (!checkData()) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}

		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, null)) {
			if (tPubSubmit.mErrors.needDealError()) {
				buildError("insertData", "保存信息时出现错误!");
				return false;
			}
		}
		mInputData = null;
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 */
	private boolean getInputData() {
		this.mRIBsnsBillBatchSchema = (RIBsnsBillBatchSchema) mInputData
				.getObjectByObjectName("RIBsnsBillBatchSchema", 0);
		return true;
	}

	private boolean checkData() {
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		String sql1 = "update ribsnsbillbatch set state = '"
				+ mRIBsnsBillBatchSchema.getState() + "' where batchno ='"
				+ mRIBsnsBillBatchSchema.getBatchNo() + "' and billno = '"
				+ mRIBsnsBillBatchSchema.getBillNo() + "' and currency='"
				+ mRIBsnsBillBatchSchema.getCurrency() + "'";
		if ("03".equals(mRIBsnsBillBatchSchema.getState())) {
			String sql2 = "update rirecordtrace a set a.SettleFlag = '01' where a.billbatchno ='"
					+ mRIBsnsBillBatchSchema.getBatchNo()
					+ "' and a.billno = '"
					+ mRIBsnsBillBatchSchema.getBillNo()
					+ "' and exists (SELECT 1 FROM ripolrecordbake b WHERE a.eventno=b.eventno and b.currency='"
					+ mRIBsnsBillBatchSchema.getCurrency() + "')";

			map.put(sql2, "UPDATE");
		}

		map.put(sql1, "UPDATE");
		return true;
	}

	private boolean prepareOutputData() {
		try {
			this.mInputData.clear();
			this.mInputData.add(map);
		} catch (Exception ex) {
			// @@错误处理
			buildError("prepareData", "在准备往后层处理所需要的数据时出错。");
			return false;
		}
		return true;
	}

	public static void main(String[] args) {

	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "RIBsnsBillUWBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}
}
