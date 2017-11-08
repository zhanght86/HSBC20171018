

package com.sinosoft.lis.reinsure;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.RIBigRateFeeSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class RIBigRateFeeBL {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 数据操作字符串 */
	private String strOperate;

	/** 业务处理相关变量 */
	RIBigRateFeeSchema tRiBigRateFeeSchema = new RIBigRateFeeSchema();
	private PubSubmit tPubSubmit = new PubSubmit();
	private MMap map = new MMap();

	public RIBigRateFeeBL() {

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
		this.strOperate = cOperate;

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

		System.out.println("Start RIBsnsBillCalBL Submit...");

		if (!tPubSubmit.submitData(mInputData, null)) {
			if (tPubSubmit.mErrors.needDealError()) {
				buildError("insertData", "保存统计信息时出现错误!");
				return false;
			}
		}

		mInputData = null;
		System.out.println("End RIBsnsBillCalBL Submit...");
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 */
	private boolean getInputData() {
		this.mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		this.tRiBigRateFeeSchema = (RIBigRateFeeSchema) mInputData
				.getObjectByObjectName("RIBigRateFeeSchema", 0);
		return true;
	}

	private boolean checkData() {
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 进行插入数据
		if (this.strOperate.equals("INSERT")) {
			if (!insertData()) {
				return false;
			}
		}
		// 进行插入数据
		if (this.strOperate.equals("UPDATE")) {
			if (!updateData()) {
				return false;
			}
		}
		// 进行插入数据
		if (this.strOperate.equals("DELETE")) {
			if (!deleteData()) {
				return false;
			}
		}
		return true;
	}

	private boolean deleteData() {
		map.put(tRiBigRateFeeSchema, "DELETE");
		if (!prepareOutputData()) {
			return false;
		}
		if (!tPubSubmit.submitData(this.mInputData, "")) {
			if (tPubSubmit.mErrors.needDealError()) {
				buildError("insertData", "保存再保合同信息时出现错误!");
				return false;
			}
		}
		map = null;
		tPubSubmit = null;

		return true;
	}

	private boolean updateData() {
		return true;
	}

	private boolean insertData() {
		// TODO Auto-generated method stub
		// String sql =
		// "delete from RiBigRateFee r where r.MakeDate='"+tRiBigRateFeeSchema.getMakeDate()+"'";
		// map.put(sql,"DELETE");
		// 插入再保方案表
		PubFun tPubFun = new PubFun();
		String currentDate = tPubFun.getCurrentDate();
		String currentTime = tPubFun.getCurrentTime();
		tRiBigRateFeeSchema.setSerialNo(PubFun1.CreateMaxNo("RIBIGRATE", 10));
		tRiBigRateFeeSchema.setMakeDate(currentDate);
		tRiBigRateFeeSchema.setMakeTime(currentTime);
		tRiBigRateFeeSchema.setModifyDate(currentDate);
		tRiBigRateFeeSchema.setModifyTime(currentTime);
		tRiBigRateFeeSchema.setOperator(mGlobalInput.Operator);
		tRiBigRateFeeSchema.setManageCom(mGlobalInput.ManageCom);
		map.put(tRiBigRateFeeSchema, "INSERT");
		if (!prepareOutputData()) {
			return false;
		}
		if (!tPubSubmit.submitData(this.mInputData, "")) {
			if (tPubSubmit.mErrors.needDealError()) {
				buildError("insertData", "保存再保合同信息时出现错误!");
				return false;
			}
		}
		map = null;
		tPubSubmit = null;

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

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "RIBsnsBillCalBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public VData getResult() {
		return null;
	}
}
