package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 签单错误记录</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author tuq
 * @version 1.0
 */
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCSignLogSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class LCSignLogBL {
private static Logger logger = Logger.getLogger(LCSignLogBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 传出数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	/** 全局基础数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 数据操作字符串 */
	private String mOperater;
	private String mManageCom;

	LCContSet mLCContSet = new LCContSet();

	String Content = "";

	public LCSignLogBL() {

	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括"INSERT"
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		return true;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			mLCContSet = (LCContSet) mInputData.getObjectByObjectName(
					"LCContSet", 0);

			Content = (String) mInputData.getObjectByObjectName("String", 0);
		} catch (Exception e) {
			// @@错误处理
			e.printStackTrace();

			CError tError = new CError();
			tError.moduleName = "UWNotePadBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!!";
			this.mErrors.addOneError(tError);

			return false;
		}
		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWAutoHealthAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据Operate失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得登陆机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "UWAutoHealthAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据ManageCom失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		if (!saveErrorLogs()) {
			return false;
		}
		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 对输入数据进行验证
	 */
	public boolean checkData() {
		// 校验保单信息
		return true;
	}

	/**
	 * 获取暂交费信息.
	 * 
	 * @param key
	 *            String
	 * @return Object
	 */
	private boolean saveErrorLogs() {
		MMap map = new MMap();
		VData tData = new VData();
		String tNo = "";
		for (int i = 1; i <= mLCContSet.size(); i++) {
			LCSignLogSchema tLCSignLogSchema = new LCSignLogSchema();
			String tLimit = PubFun.getNoLimit(mLCContSet.get(i).getManageCom());
			tNo = "";
			tNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
			if (!tNo.equals("")) {
				tLCSignLogSchema.setSeriNo(tNo);
				tLCSignLogSchema.setGrpContNo(mLCContSet.get(i)
						.getProposalContNo());
				tLCSignLogSchema.setContNo(mLCContSet.get(i).getContNo());
				tLCSignLogSchema.setContType(mLCContSet.get(i).getContType());
				tLCSignLogSchema.setErrInfo(Content);
				tLCSignLogSchema.setMakeDate(PubFun.getCurrentDate());
				tLCSignLogSchema.setMakeTime(PubFun.getCurrentTime());
				if (tLCSignLogSchema != null) {
					map.put(tLCSignLogSchema, "INSERT");
				}
			}
		}
		PubSubmit tpub = new PubSubmit();
		tData.add(map);
		if (!tpub.submitData(tData, "")) {
			return false;
		}
		return true;
	}

}
