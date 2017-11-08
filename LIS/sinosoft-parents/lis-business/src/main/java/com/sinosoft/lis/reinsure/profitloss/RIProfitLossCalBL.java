

package com.sinosoft.lis.reinsure.profitloss;

/**
 * <p>ClassName: RIProfitLossCalBL.java </p>
 * <p>Description: 盈余佣金计算 </p>
 * <p>Copyright: Copyright (c) 2009 </p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 * @CreateDate：2011/8/22
 */

import com.sinosoft.lis.db.RIProLossCalDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubCalculator;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.RIProLossCalSchema;
import com.sinosoft.lis.schema.RIProLossRelaSchema;
import com.sinosoft.lis.schema.RIProLossResultSchema;
import com.sinosoft.lis.schema.RIProLossValueSchema;
import com.sinosoft.lis.vschema.RIProLossResultSet;
import com.sinosoft.lis.vschema.RIProLossValueSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.VData;

public class RIProfitLossCalBL {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private String mCalSql = "";
	private String batno = "";
	/** 业务处理相关变量 */
	private RIProLossRelaSchema tRIProLossRelaSchema = new RIProLossRelaSchema();
	private RIProLossCalSchema tRIProLossCalSchema = new RIProLossCalSchema();
	private RIProLossResultSchema tRIProLossResultSchema = new RIProLossResultSchema();
	private RIProLossResultSet tRIProLossResultSet = new RIProLossResultSet();

	private RIProLossValueSet tRIProLossValueSet = new RIProLossValueSet();

	private String currentDate = PubFun.getCurrentDate();
	private String currentTime = PubFun.getCurrentTime();

	private MMap map = new MMap();

	public RIProfitLossCalBL() {
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

		System.out.println("Start RIProfitLossCalBL Submit...");

		if (!tPubSubmit.submitData(mInputData, null)) {

			if (tPubSubmit.mErrors.needDealError()) {
				buildError("insertData", "保存信息时出现错误!");
				return false;
			}
		}

		mInputData = null;
		System.out.println("End RIProfitLossCalBL Submit...");
		return true;

	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "RIProfitLossCalBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 */
	private boolean getInputData() {
		this.tRIProLossRelaSchema.setSchema((RIProLossRelaSchema) mInputData
				.getObjectByObjectName("RIProLossRelaSchema", 0));
		this.tRIProLossValueSet.set((RIProLossValueSet) mInputData
				.getObjectByObjectName("RIProLossValueSet", 1));
		this.tRIProLossCalSchema.setSchema((RIProLossCalSchema) mInputData
				.getObjectByObjectName("RIProLossCalSchema", 2));
		this.tRIProLossResultSchema
				.setSchema((RIProLossResultSchema) mInputData
						.getObjectByObjectName("RIProLossResultSchema", 3));
		this.mGlobalInput.setSchema((GlobalInput) mInputData
				.getObjectByObjectName("GlobalInput", 4));
		RIProLossCalDB cdb = new RIProLossCalDB();
		cdb.setRIProfitNo(tRIProLossCalSchema.getRIProfitNo());
		cdb.getInfo();
		tRIProLossCalSchema.setSchema(cdb);
		return true;
	}

	private boolean checkData() {
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData(String cOperate) {
		if (cOperate.equals("CALCULATE")) {
			if (tRIProLossValueSet.get(1).getBatchNo().equals("")
					|| tRIProLossValueSet.get(1).getBatchNo() == null) {
				batno = tRIProLossResultSchema.getCalYear()
						+ PubFun1.CreateMaxNo("RIPROLOSSVALUE", 6);
			} else {
				batno = tRIProLossValueSet.get(1).getBatchNo();
			}

			for (int i = 1; i <= tRIProLossValueSet.size(); i++) {
				RIProLossValueSchema tRIProLossValueSchema = tRIProLossValueSet
						.get(i);

				tRIProLossValueSchema.setBatchNo(batno);

				tRIProLossValueSchema.setRIProfitNo(tRIProLossValueSet.get(i)
						.getRIProfitNo());
				tRIProLossValueSchema.setCalYear(tRIProLossValueSet.get(i)
						.getCalYear());
				tRIProLossValueSchema.setCurrency(tRIProLossValueSet.get(i)
						.getCurrency());
				tRIProLossValueSchema.setReComCode(tRIProLossValueSet.get(i)
						.getReComCode());
				tRIProLossValueSchema.setRIContNo(tRIProLossValueSet.get(i)
						.getRIContNo());
				tRIProLossValueSchema.setFactorCode(tRIProLossValueSet.get(i)
						.getFactorCode());
				tRIProLossValueSchema.setFactorName(tRIProLossValueSet.get(i)
						.getFactorName());
				tRIProLossValueSchema.setFactorValue(tRIProLossValueSet.get(i)
						.getFactorValue());
				tRIProLossValueSchema.setMakeDate(currentDate);
				tRIProLossValueSchema.setMakeTime(currentTime);
				tRIProLossValueSchema.setModifyDate(currentDate);
				tRIProLossValueSchema.setModifyTime(currentTime);
				tRIProLossValueSchema.setManageCom(mGlobalInput.ManageCom);
				tRIProLossValueSchema.setOperator(mGlobalInput.Operator);
				System.out.println("====="
						+ tRIProLossValueSchema.getFactorValue());
				// tRIProLossValueSet.add(tRIProLossValueSchema);
			}
			map.put(tRIProLossValueSet, "DELETE&INSERT");
			System.out.println(tRIProLossValueSet.size());
			System.out.println(tRIProLossCalSchema.getRIProfitNo());
			System.out.println("========开始计算==========");
			if (tRIProLossCalSchema.getItemCalType().equals("1")) {
			} else if (tRIProLossCalSchema.getItemCalType().equals("2")) {
				mCalSql = tRIProLossCalSchema.getCalSQL();
				PubCalculator tPubCalculator = new PubCalculator();
				tPubCalculator.setCalSql(mCalSql);
				System.out.println(" calSql: " + mCalSql);
				for (int i = 1; i <= tRIProLossValueSet.size(); i++) {
					String clumncode = tRIProLossValueSet.get(i)
							.getFactorCode();
					String clumnvalue = tRIProLossValueSet.get(i)
							.getFactorValue();
					tPubCalculator.addBasicFactor(clumncode, clumnvalue);
				}
				String tCalSql = tPubCalculator.calculateEx();
				System.out.println("纯溢手续费计算sql为：" + tCalSql);
				ExeSQL tExeSQL = new ExeSQL();
				String temp = tExeSQL.getOneValue(tCalSql);
				if (tExeSQL.mErrors.needDealError()) {
					buildError("verifyOperate", "纯溢手续费sql计算出错：");
					return false;
				} else if (temp.equals("")) {
					buildError("verifyOperate", "纯溢手续费sql计算出错：");
					return false;
				} else {
					tRIProLossResultSchema.setProLosAmnt(Double
							.parseDouble(temp));
				}
				System.out.println(tRIProLossResultSchema.getProLosAmnt());
			} else if (tRIProLossCalSchema.getItemCalType().equals("3")) {

			}
			tRIProLossResultSchema.setBatchNo(tRIProLossValueSet.get(1)
					.getBatchNo());
			tRIProLossResultSchema.setRIProfitNo(tRIProLossRelaSchema
					.getRIProfitNo());
			tRIProLossResultSchema.setState("02");
			tRIProLossResultSchema.setCurrency(tRIProLossValueSet.get(1)
					.getCurrency());
			tRIProLossResultSchema.setCalYear(tRIProLossValueSet.get(1)
					.getCalYear());
			tRIProLossResultSchema.setReComCode(tRIProLossValueSet.get(1)
					.getReComCode());
			tRIProLossResultSchema.setRIContNo(tRIProLossValueSet.get(1)
					.getRIContNo());
			tRIProLossResultSchema.setMakeDate(currentDate);
			tRIProLossResultSchema.setMakeTime(currentTime);
			tRIProLossResultSchema.setModifyDate(currentDate);
			tRIProLossResultSchema.setModifyTime(currentTime);
			tRIProLossResultSchema.setManageCom(mGlobalInput.ManageCom);
			tRIProLossResultSchema.setOperator(mGlobalInput.Operator);
			tRIProLossResultSet.add(tRIProLossResultSchema);

			map.put(tRIProLossResultSet, "DELETE&INSERT");

		}

		return true;
	}

	private boolean prepareOutputData() {
		try {
			this.mInputData.clear();
			this.mInputData.add(map);

		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIProfitLossCalBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
	}

	public VData getResult() {
		// TODO Auto-generated method stub
		return null;
	}
}
