

/**
 * <p>ClassName: RIRateMaintBL.java </p>
 * <p>Description: 费率维护 </p>
 * <p>Copyright: Copyright (c) 2009 </p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 * @CreateDate：2011/6/17
 */

package com.sinosoft.lis.reinsure;

import com.sinosoft.lis.db.RIInterestDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.RIInterestSchema;
import com.sinosoft.lis.vschema.RIInterestSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class RIRateMaintBL {
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
	private RIInterestSchema mRIInterestSchema = new RIInterestSchema();

	private MMap map = new MMap();

	public RIRateMaintBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
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
		if (!tPubSubmit.submitData(mInputData, null)) {
			if (tPubSubmit.mErrors.needDealError()) {
				buildError("insertData", "保存利率表信息时出现错误!");
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
	private boolean getInputData(VData cInputData) {
		this.mInputData = cInputData;
		this.mRIInterestSchema = (RIInterestSchema) mInputData
				.getObjectByObjectName("RIInterestSchema", 0);
		return true;
	}

	private boolean checkData() {
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		if ("INSERT".equals(mOperate)) {
			RIInterestDB db = new RIInterestDB();
			RIInterestSet set = new RIInterestSet();
			String strSQL = "select * from riinterest where state = '01' and riskcode='"
					+ mRIInterestSchema.getRiskCode()
					+ "' and interesttype = '"
					+ mRIInterestSchema.getInterestType()
					+ "' order by startdate desc";
			set = db.executeQuery(strSQL);
			if (set.size() > 0) {
				RIInterestSchema lastInterest = set.get(1);
				String tDate = PubFun.calDate(mRIInterestSchema.getStartDate(),
						-1, "D", null);
				lastInterest.setEndDate(tDate);
				map.put(lastInterest, "UPDATE");
			}

			mRIInterestSchema.setSerialNo("I"
					+ PubFun1.CreateMaxNo("RIINTEREST", 9));
			mRIInterestSchema.setState("01");

			map.put(mRIInterestSchema, "INSERT");
		}
		/*
		 * if ("UPDATE".equals(mOperate)) { if
		 * (currentDate.compareTo(mRIInterestSchema.getStartDate()) < 0) {
		 * mRIInterestSchema.setState("02"); } else if
		 * (mRIInterestSchema.getEndDate() != null &&
		 * !"".equals(mRIInterestSchema.getEndDate()) &&
		 * currentDate.compareTo(mRIInterestSchema.getEndDate()) > 0) {
		 * mRIInterestSchema.setState("02"); } else {
		 * mRIInterestSchema.setState("01"); } map.put(mRIInterestSchema,
		 * "UPDATE"); }
		 */
		if ("DELETE".equals(mOperate)) {
			mRIInterestSchema.setState("02");

			RIInterestDB db = new RIInterestDB();
			RIInterestSet set = new RIInterestSet();
			String strSQL = "select * from riinterest where state = '01' and riskcode='"
					+ mRIInterestSchema.getRiskCode()
					+ "' and interesttype = '"
					+ mRIInterestSchema.getInterestType()
					+ "' order by startdate desc";
			set = db.executeQuery(strSQL);
			if (set.size() > 1) {
				RIInterestSchema lastInterest = set.get(2);
				lastInterest.setEndDate("");
				map.put(lastInterest, "UPDATE");
			}
			map.put(mRIInterestSchema, "UPDATE");
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

	/*
	 * add by kevin, 2002-10-14
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "RIRateMaintBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}
}
