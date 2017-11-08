

package com.sinosoft.lis.reinsure;

/**
 * <p>ClassName: RIAccRDBL.java </p>
 * <p>Description: 分出责任定义 </p>
 * <p>Copyright: Copyright (c) 2009 </p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 * @CreateDate：2011/6/16
 */

//包名
//package com.sinosoft.lis.config;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.RIAccumulateDefSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class RIAccRDBL {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 数据操作字符串 */
	private String mOperate;

	private RIAccumulateDefSchema mRIAccumulateDefSchema = new RIAccumulateDefSchema();

	private MMap mMap = new MMap();

	private PubSubmit tPubSubmit = new PubSubmit();

	/** 业务处理相关变量 */
	// private ××××Schema t****Schema=new ****Schema();

	private MMap map = new MMap();

	public RIAccRDBL() {

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
		if (!dealData(cOperate)) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}

		PubSubmit tPubSubmit = new PubSubmit();

		System.out.println("Start RIAccRDBL Submit...");

		if (!tPubSubmit.submitData(mInputData, null)) {

			if (tPubSubmit.mErrors.needDealError()) {
				buildError("insertData", "保存信息时出现错误!");
				return false;
			}
		}

		mInputData = null;
		System.out.println("End RIAccRDBL Submit...");
		return true;

	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "RIAccRDBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 */
	private boolean getInputData() {
		this.mRIAccumulateDefSchema
				.setSchema((RIAccumulateDefSchema) mInputData
						.getObjectByObjectName("RIAccumulateDefSchema", 0));
		return true;
	}

	private boolean checkData() {
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData(String cOperate) {
		if (cOperate.equals("INSERT")) {
			mMap.put(mRIAccumulateDefSchema, "INSERT");
		}
		if (cOperate.equals("UPDATE")) {
			mMap.put(mRIAccumulateDefSchema, "UPDATE");
		}
		if (cOperate.equals("DELETE")) {
			mMap.put(mRIAccumulateDefSchema, "DELETE");
			String strSQL = "delete from RIAccumulateRDCode where AccumulateDefNO='"
					+ mRIAccumulateDefSchema.getAccumulateDefNO() + "'";
			String strSQL2 = "delete from RIAccumulateGetDuty where AccumulateDefNO='"
					+ mRIAccumulateDefSchema.getAccumulateDefNO() + "'";
			mMap.put(strSQL, "DELETE");
			mMap.put(strSQL2, "DELETE");
		}

		return true;
	}

	private boolean prepareOutputData() {
		try {
			this.mInputData.clear();
			this.mInputData.add(mMap);

		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIAccRDBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
	}
}
