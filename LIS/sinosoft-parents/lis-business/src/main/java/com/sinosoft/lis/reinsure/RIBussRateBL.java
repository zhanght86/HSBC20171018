

/**
 * <p>ClassName: RIBussRateBL.java </p>
 * <p>Description: 费率维护 </p>
 * <p>Copyright: Copyright (c) 2009 </p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 * @CreateDate：2011/6/17
 */

package com.sinosoft.lis.reinsure;

import com.sinosoft.lis.db.RIExchangeRateDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.RIExchangeRateSchema;
import com.sinosoft.lis.vschema.RIExchangeRateSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class RIBussRateBL {
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
	private RIExchangeRateSchema mRIExchangeRateSchema = new RIExchangeRateSchema();

	private MMap map = new MMap();

	public RIBussRateBL() {
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
				buildError("insertData", "保存汇率表信息时出现错误!");
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
		this.mRIExchangeRateSchema = (RIExchangeRateSchema) mInputData
				.getObjectByObjectName("RIExchangeRateSchema", 0);
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
			RIExchangeRateDB db = new RIExchangeRateDB();
			RIExchangeRateSet set = new RIExchangeRateSet();
			String strSQL = "select * from riexchangerate where state = '01' and Currency = '"
					+ mRIExchangeRateSchema.getCurrency()
					+ "' order by startdate desc";
			set = db.executeQuery(strSQL);
			if (set.size() > 0) {
				RIExchangeRateSchema lastExchangeRate = set.get(1);
				String tDate = PubFun.calDate(
						mRIExchangeRateSchema.getStartDate(), -1, "D", null);
				lastExchangeRate.setEndDate(tDate);
				map.put(lastExchangeRate, "UPDATE");
			}

			mRIExchangeRateSchema.setSerialNo("E"
					+ PubFun1.CreateMaxNo("RIEXCHANGERATE", 9));
			mRIExchangeRateSchema.setState("01");

			map.put(mRIExchangeRateSchema, "INSERT");
		}
		if ("DELETE".equals(mOperate)) {
			mRIExchangeRateSchema.setState("02");

			RIExchangeRateDB db = new RIExchangeRateDB();
			RIExchangeRateSet set = new RIExchangeRateSet();
			String strSQL = "select * from riexchangerate where state = '01' and Currency = '"
					+ mRIExchangeRateSchema.getCurrency()
					+ "' order by startdate desc";
			set = db.executeQuery(strSQL);
			if (set.size() > 1) {
				RIExchangeRateSchema lastExchangeRate = set.get(2);
				lastExchangeRate.setEndDate("");
				map.put(lastExchangeRate, "UPDATE");
			}
			System.out.println(mRIExchangeRateSchema.getSerialNo()
					+ "+++++++++++++" + mRIExchangeRateSchema.getState());
			map.put(mRIExchangeRateSchema, "UPDATE");
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

		cError.moduleName = "RIBussRateBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}
}
