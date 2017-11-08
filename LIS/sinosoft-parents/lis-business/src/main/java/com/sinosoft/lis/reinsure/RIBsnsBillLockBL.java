

package com.sinosoft.lis.reinsure;

/**
 * <p>ClassName: RIBsnsBillLockBL.java </p>
 * <p>Description: 数据加锁 </p>
 * <p>Copyright: Copyright (c) 2009 </p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 * @CreateDate：2011/8/11
 */

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.RIBsnsBillLockSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class RIBsnsBillLockBL {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 全局数据 */
	private GlobalInput globalInput = new GlobalInput();
	/** 数据操作字符串 */
	private String mOperate;

	/** 业务处理相关变量 */
	private RIBsnsBillLockSchema mRIBsnsBillLockSchema = new RIBsnsBillLockSchema();

	private MMap map = new MMap();

	public RIBsnsBillLockBL() {

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
		if (!getInputData(cInputData)) {
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

		System.out.println("Start RIBsnsBillLockBL Submit...");

		if (!tPubSubmit.submitData(mInputData, null)) {

			if (tPubSubmit.mErrors.needDealError()) {
				buildError("insertData", "保存信息时出现错误!");
				return false;
			}
		}

		mInputData = null;
		return true;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "RIBsnsBillLockBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 */
	private boolean getInputData(VData cInputData) {

		this.mInputData = cInputData;
		this.mRIBsnsBillLockSchema.setSchema((RIBsnsBillLockSchema) cInputData
				.getObjectByObjectName("RIBsnsBillLockSchema", 0));

		return true;
	}

	private boolean checkData() {
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		System.out
				.println("dealdata..........................................");
		if ("INSERT".equals(mOperate)) {
			mRIBsnsBillLockSchema.setSerial("CZ" + "11"
					+ PubFun1.CreateMaxNo("RIBILLLOCK", 6));

			map.put(mRIBsnsBillLockSchema, "INSERT");
		}
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
}
