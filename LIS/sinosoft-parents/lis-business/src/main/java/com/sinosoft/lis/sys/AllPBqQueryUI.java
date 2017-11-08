package com.sinosoft.lis.sys;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author lh
 * @version 1.0
 */
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;
public class AllPBqQueryUI implements BusinessService{
private static Logger logger = Logger.getLogger(AllPBqQueryUI.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往前面传输返回值的容器 */
	private VData mResult = new VData();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPEdorMainSchema mLPEdorMainSchema = new LPEdorMainSchema();
	private LCPolSchema mLCPolSchema = new LCPolSchema();

	public AllPBqQueryUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		AllPBqQueryBL tAllPBqQueryBL = new AllPBqQueryBL();
		logger.debug("Start AllPBqQueryUI Submit...");
		tAllPBqQueryBL.submitData(mInputData, mOperate);
		logger.debug("End AllPBqQueryUI Submit...");
		// 如果有需要处理的错误，则返回
		if (tAllPBqQueryBL.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tAllPBqQueryBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "AllPBqQueryUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mOperate.equals("QUERY|EDOR")) {
			this.mResult.clear();
			this.mResult = tAllPBqQueryBL.getResult();
		}
		mInputData = null;
		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(this.mGlobalInput);
			mInputData.add(this.mLPEdorMainSchema);
			mInputData.add(this.mLCPolSchema);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AllPBqQueryUI";
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
		boolean tReturn = false;
		// 此处增加一些校验代码
		tReturn = true;
		return tReturn;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLPEdorMainSchema.setSchema((LPEdorMainSchema) cInputData
				.getObjectByObjectName("LPEdorMainSchema", 0));
		mLCPolSchema.setSchema((LCPolSchema) cInputData.getObjectByObjectName(
				"LCPolSchema", 0));
		if (mGlobalInput == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AllPBqQueryUI";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有得到足够的信息！";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	public static void main(String[] args) {
		AllPBqQueryUI allPBqQueryUI1 = new AllPBqQueryUI();
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}
}
