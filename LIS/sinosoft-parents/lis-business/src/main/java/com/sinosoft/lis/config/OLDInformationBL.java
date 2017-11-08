/*
 * <p>ClassName: OLDInformationBL </p>
 * <p>Description: OLDInformationBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: testcompany </p>
 * @Database:
 * @CreateDate：2005-01-25 11:56:54
 */
package com.sinosoft.lis.config;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDInformationDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LDInformationSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class OLDInformationBL {
private static Logger logger = Logger.getLogger(OLDInformationBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	private MMap map = new MMap();
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 数据操作字符串 */
	private String mOperate;
	/** 业务处理相关变量 */
	private LDInformationSchema mLDInformationSchema = new LDInformationSchema();

	// private LDInformationSet mLDInformationSet=new LDInformationSet();
	public OLDInformationBL() {
	}

	public static void main(String[] args) {
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
		// 进行业务处理
		if (!dealData()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "OLDInformationBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败OLDInformationBL-->dealData!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		if (this.mOperate.equals("QUERY||MAIN")) {
			this.submitquery();
		} else {
			logger.debug("Start OLDInformationBL Submit...");
			/*
			 * OLDInformationBLS tOLDInformationBLS=new OLDInformationBLS();
			 * tOLDInformationBLS.submitData(mInputData,mOperate);
			 * logger.debug("End OLDInformationBL Submit...");
			 * //如果有需要处理的错误，则返回 if (tOLDInformationBLS.mErrors.needDealError()) { //
			 * @@错误处理 this.mErrors.copyAllErrors(tOLDInformationBLS.mErrors);
			 * CError tError = new CError(); tError.moduleName =
			 * "OLDInformationBL"; tError.functionName = "submitDat";
			 * tError.errorMessage ="数据提交失败!"; this.mErrors .addOneError(tError) ;
			 * return false; }
			 */
			PubSubmit tPubSubmit = new PubSubmit();
			if (!tPubSubmit.submitData(mInputData, mOperate)) {
				// @@错误处理
				this.mErrors.copyAllErrors(tPubSubmit.mErrors);
				CError tError = new CError();
				tError.moduleName = "OLDInformationBL";
				tError.functionName = "OLDInformationBL";
				tError.errorMessage = "数据提交失败!";

				this.mErrors.addOneError(tError);
				return false;
			}
			logger.debug("End OLDInformationBL Submit...");

		}
		mInputData = null;
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		if (this.mOperate.equals("INSERT||MAIN")) {
			mLDInformationSchema.setSerialNo(PubFun1.CreateMaxNo("InforSerNo",
					20));
			map.put(mLDInformationSchema, "INSERT");
		}
		if (this.mOperate.equals("UPDATE||MAIN")) {
			mLDInformationSchema.setSerialNo(PubFun1.CreateMaxNo("InforSerNo",
					20));
			map.put(mLDInformationSchema, "DELETE&INSERT");
		}
		if (this.mLDInformationSchema.equals("DELETE||MAIN")) {
			map.put(mLDInformationSchema, "DELETE");
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean updateData() {
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean deleteData() {
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		this.mLDInformationSchema.setSchema((LDInformationSchema) cInputData
				.getObjectByObjectName("LDInformationSchema", 0));
		// this.mGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean submitquery() {
		this.mResult.clear();
		LDInformationDB tLDInformationDB = new LDInformationDB();
		tLDInformationDB.setSchema(this.mLDInformationSchema);
		// 如果有需要处理的错误，则返回
		if (tLDInformationDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLDInformationDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDInformationBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mInputData = null;
		return true;
	}

	private boolean prepareOutputData() {
		try {
			this.mInputData.clear();
			this.mInputData.add(this.mLDInformationSchema);
			mInputData.add(map);
			mResult.clear();
			mResult.add(this.mLDInformationSchema);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDInformationBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}
}
