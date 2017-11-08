

/*
 * <p>ClassName: LRContManageBL </p>
 * <p>Description: LRContManageBL类文件 </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: sinosoft </p>
 * @Database: Zhang Bin
 * @CreateDate：2006-07-30
 */
package com.sinosoft.lis.reinsure;

import com.sinosoft.lis.db.RIAssociateFeeTableDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.RIAssociateFeeTableSchema;
import com.sinosoft.lis.schema.RIFeeRateTableDefSchema;
import com.sinosoft.lis.vschema.RIAssociateFeeTableSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class LRFeeRateBatchRiskBL {

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	/** 前台传入的公共变量 */
	private GlobalInput globalInput = new GlobalInput();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	private RIFeeRateTableDefSchema mRIFeeRateTableDefSchema = new RIFeeRateTableDefSchema();
	private RIAssociateFeeTableSet mRIAssociateFeeTableSet = new RIAssociateFeeTableSet();

	/** 数据操作字符串 */
	private String strOperate;
	private MMap mMap = new MMap();

	private PubSubmit tPubSubmit = new PubSubmit();

	// 业务处理相关变量
	/** 全局数据 */

	public LRFeeRateBatchRiskBL() {
	}

	/**
	 * 提交数据处理方法
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		this.strOperate = cOperate;
		if (strOperate.equals("")) {
			buildError("verifyOperate", "不支持的操作字符串");
			return false;
		}
		if (!getInputData(cInputData)) {
			return false;
		}
		if (!dealData()) {
			return false;
		}

		return true;
	}

	public static void main(String[] args) {
		try {
			RIAssociateFeeTableSchema mRIAssociateFeeTableSchema = new RIAssociateFeeTableSchema();
			RIAssociateFeeTableSchema mRIAssociateFeeTableSchema1 = new RIAssociateFeeTableSchema();

			RIAssociateFeeTableSet mRIAssociateFeeTableSet = new RIAssociateFeeTableSet();

			mRIAssociateFeeTableSchema.setAccumulateDefNO("L000000010");
			mRIAssociateFeeTableSchema.setDeTailFlag("01");
			mRIAssociateFeeTableSchema.setRIPreceptNo("S003001001");
			mRIAssociateFeeTableSchema.setRiskCode("DDCL");
			mRIAssociateFeeTableSchema.setDutyCode("000000");
			mRIAssociateFeeTableSchema.setAreaID("02");
			mRIAssociateFeeTableSchema.setAreaLevel("1");
			mRIAssociateFeeTableSchema.setIncomeCompanyNo("R000000003");
			mRIAssociateFeeTableSchema.setUpperLimit("9999999999");
			mRIAssociateFeeTableSchema.setLowerLimit("0");
			mRIAssociateFeeTableSchema.setPremFeeTableNo("F0002");
			mRIAssociateFeeTableSchema.setComFeeTableNo("");

			mRIAssociateFeeTableSchema1.setAccumulateDefNO("L000000010");
			mRIAssociateFeeTableSchema1.setDeTailFlag("01");
			mRIAssociateFeeTableSchema1.setRIPreceptNo("S003001001");
			mRIAssociateFeeTableSchema.setRiskCode("DDCL");
			mRIAssociateFeeTableSchema.setDutyCode("000000");
			mRIAssociateFeeTableSchema1.setAreaID("02");
			mRIAssociateFeeTableSchema1.setAreaLevel("1");
			mRIAssociateFeeTableSchema1.setIncomeCompanyNo("R000000003");
			mRIAssociateFeeTableSchema1.setUpperLimit("9999999999");
			mRIAssociateFeeTableSchema1.setLowerLimit("0");
			mRIAssociateFeeTableSchema1.setPremFeeTableNo("F000003");
			mRIAssociateFeeTableSchema1.setComFeeTableNo("F000004");

			mRIAssociateFeeTableSet.add(mRIAssociateFeeTableSchema);
			mRIAssociateFeeTableSet.add(mRIAssociateFeeTableSchema1);

			LRFeeRateBatchRiskBL tLRFeeRateBatchRiskBL = new LRFeeRateBatchRiskBL();
			VData tVData = new VData();
			GlobalInput globalInput = new GlobalInput();
			globalInput.Operator = "001";
			tVData.addElement(globalInput);
			tVData.addElement(mRIAssociateFeeTableSet);

			tLRFeeRateBatchRiskBL.submitData(tVData, "INSERT");
			System.out.println(" ex: "
					+ tLRFeeRateBatchRiskBL.mErrors.getFirstError());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		try {
			this.mInputData.clear();
			this.mInputData.add(mMap);

		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LRFeeRateDefBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;

	}

	/**
	 * 根据前面的输入数据，进行UI逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 进行插入数据
		if (this.strOperate.equals("INSERT")) {
			if (!insertData()) {
				return false;
			}
		}
		// 对数据进行修改操作
		if (this.strOperate.equals("UPDATE")) {
			if (!updateData()) {
				return false;
			}
		}
		// 对数据进行删除操作
		if (this.strOperate.equals("DELETE")) {
			if (!deleteData()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * deleteData
	 * 
	 * @return boolean
	 */
	private boolean deleteData() {
		String sql = "delete from RIAssociateFeeTable where ripreceptno='"
				+ this.mRIAssociateFeeTableSet.get(1).getRIPreceptNo()
				+ "' and areaid='"
				+ this.mRIAssociateFeeTableSet.get(1).getAreaID() + "'";
		mMap.put(sql, "DELETE");
		if (!prepareOutputData()) {
			return false;
		}
		if (!tPubSubmit.submitData(this.mInputData, "")) {
			if (tPubSubmit.mErrors.needDealError()) {
				buildError("updateData", "修改时出现错误!");
				return false;
			}
		}
		mMap = null;
		tPubSubmit = null;
		return true;
	}

	/**
	 * updateData
	 * 
	 * @return boolean
	 */
	private boolean updateData() {
		return true;
	}

	/**
	 * insertData
	 * 
	 * @return boolean
	 */
	private boolean insertData() {
		RIAssociateFeeTableDB tRIAssociateFeeTableDB = new RIAssociateFeeTableDB();

		tRIAssociateFeeTableDB.setRIPreceptNo(mRIAssociateFeeTableSet.get(1)
				.getRIPreceptNo());
		tRIAssociateFeeTableDB.setAreaID(mRIAssociateFeeTableSet.get(1)
				.getAreaID());
		RIAssociateFeeTableSet tRIAssociateFeeTableSet = tRIAssociateFeeTableDB
				.query();
		mMap.put(tRIAssociateFeeTableSet, "DELETE");
		RIAssociateFeeTableSet insertRIAssociateFeeTableSet = new RIAssociateFeeTableSet();
		if (mRIAssociateFeeTableSet.size() > 0) {
			for (int i = 1; i <= mRIAssociateFeeTableSet.size(); i++) {
				if ((mRIAssociateFeeTableSet.get(i).getPremFeeTableNo() != null && !mRIAssociateFeeTableSet
						.get(i).getPremFeeTableNo().equals(""))
						|| (mRIAssociateFeeTableSet.get(i).getComFeeTableNo() != null && !mRIAssociateFeeTableSet
								.get(i).getComFeeTableNo().equals(""))) {
					insertRIAssociateFeeTableSet.add(mRIAssociateFeeTableSet
							.get(i));
				}
			}
			mMap.put(insertRIAssociateFeeTableSet, "INSERT");
		}
		if (!prepareOutputData()) {
			return false;
		}
		if (!tPubSubmit.submitData(this.mInputData, "")) {
			if (tPubSubmit.mErrors.needDealError()) {
				buildError("insertData", "保存再保合同信息时出现错误!");
				return false;
			}
		}
		mMap = null;
		tPubSubmit = null;

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		globalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		this.mRIAssociateFeeTableSet.set((RIAssociateFeeTableSet) cInputData
				.getObjectByObjectName("RIAssociateFeeTableSet", 0));
		return true;
	}

	public String getResult() {
		return mRIFeeRateTableDefSchema.getFeeTableNo();
	}

	/*
	 * add by kevin, 2002-10-14
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "ReComManageBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}
}
