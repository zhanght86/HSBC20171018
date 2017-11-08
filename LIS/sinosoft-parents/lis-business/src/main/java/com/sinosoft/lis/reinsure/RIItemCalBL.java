

package com.sinosoft.lis.reinsure;

import com.sinosoft.lis.db.RIAccumulateDefDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.RIAccumulateDefSchema;
import com.sinosoft.lis.schema.RICalDefSchema;
import com.sinosoft.lis.vschema.RIAccumulateDefSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class RIItemCalBL {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private MMap mMap = new MMap();

	private PubSubmit tPubSubmit = new PubSubmit();
	private GlobalInput globalInput = new GlobalInput();

	private RICalDefSchema mRICalDefSchema = new RICalDefSchema();
	private RIAccumulateDefSchema mRIAccumulateDefSchema = new RIAccumulateDefSchema();
	private TransferData mTransferData = new TransferData();
	private String mArithType = "";

	public RIItemCalBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		System.out.println("getInputData");

		if (!getInputData(cInputData)) {
			return false;
		}

		System.out.println("dealData");

		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {
		globalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mArithType = (String) mTransferData.getValueByName("ArithType");
		mRIAccumulateDefSchema = (RIAccumulateDefSchema) cInputData
				.getObjectByObjectName("RIAccumulateDefSchema", 0);
		mRICalDefSchema = (RICalDefSchema) cInputData.getObjectByObjectName(
				"RICalDefSchema", 0);

		return true;
	}

	/**
	 * 根据前面的输入数据，进行UI逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 * 
	 * @return
	 */
	private boolean dealData() {
		if ("add".equals(mOperate)) {

			if ("L".equals(mArithType) && mRIAccumulateDefSchema != null) {
				RIAccumulateDefDB tRIAccumulateDefDB = new RIAccumulateDefDB();
				String tSQL = "select * from RIAccumulateDef where accumulatedefno = '"
						+ mRIAccumulateDefSchema.getAccumulateDefNO() + "'";
				RIAccumulateDefSet tRIAccumulateDefSet = tRIAccumulateDefDB
						.executeQuery(tSQL);
				if (tRIAccumulateDefSet != null
						&& tRIAccumulateDefSet.size() > 0) {
					RIAccumulateDefSchema tRIAccumulateDefSchema = tRIAccumulateDefSet
							.get(1);
					tRIAccumulateDefSchema
							.setArithmeticID(mRIAccumulateDefSchema
									.getAccumulateDefNO());

					mMap.put(tRIAccumulateDefSchema, "UPDATE");
				}
			}

			mMap.put(mRICalDefSchema, "INSERT");
		} else if ("delete".equals(mOperate)) {

			if ("L".equals(mArithType) && mRIAccumulateDefSchema != null) {
				RIAccumulateDefDB tRIAccumulateDefDB = new RIAccumulateDefDB();
				String tSQL = "select * from RIAccumulateDef where accumulatedefno = '"
						+ mRIAccumulateDefSchema.getAccumulateDefNO() + "'";
				RIAccumulateDefSet tRIAccumulateDefSet = tRIAccumulateDefDB
						.executeQuery(tSQL);
				if (tRIAccumulateDefSet != null
						&& tRIAccumulateDefSet.size() > 0) {
					RIAccumulateDefSchema tRIAccumulateDefSchema = tRIAccumulateDefSet
							.get(1);
					tRIAccumulateDefSchema.setArithmeticID("");

					mMap.put(tRIAccumulateDefSchema, "UPDATE");
				}
			}
			mMap.put(mRICalDefSchema, "DELETE");

		} else if ("update".equals(mOperate)) {
			mMap.put(mRICalDefSchema, "UPDATE");
		}

		return true;
	}

	private boolean prepareOutputData() {
		this.mInputData.clear();
		this.mInputData.add(mMap);
		if (!tPubSubmit.submitData(this.mInputData, "")) {
			if (tPubSubmit.mErrors.needDealError()) {
				// @@错误处理
				buildError("insertData", "保存演算法时出现错误!");
				return false;
			}
		}
		mMap = null;
		tPubSubmit = null;

		return true;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "RIItemCalBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public VData getResult() {
		return this.mResult;

	}

	public static void main(String[] args) {

	}

}
