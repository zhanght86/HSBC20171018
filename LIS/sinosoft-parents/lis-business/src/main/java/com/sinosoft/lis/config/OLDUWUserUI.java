/*
 * <p>ClassName: OLDUWUserUI </p>
 * <p>Description: OLDUWUserUI类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: testcompany </p>
 * @Database:
 * @CreateDate：2005-01-24 18:15:01
 */
package com.sinosoft.lis.config;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LDUWAmntGradeSchema;
import com.sinosoft.lis.schema.LDUWGradeSchema;
import com.sinosoft.lis.schema.LDUWUserSchema;
import com.sinosoft.lis.vschema.LDUWAmntGradeSet;
import com.sinosoft.lis.vschema.LDUWGradeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class OLDUWUserUI {
private static Logger logger = Logger.getLogger(OLDUWUserUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	// 业务处理相关变量
	/** 全局数据 */
	private LDUWUserSchema mLDUWUserSchema = new LDUWUserSchema();

	public OLDUWUserUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		/*
		 * //得到外部传入的数据,将数据备份到本类中 if (!getInputData(cInputData)) return false;
		 * 
		 * //进行业务处理 if (!dealData()) return false;
		 * 
		 * //准备往后台的数据 if (!prepareOutputData()) return false;
		 */
		OLDUWUserBL tOLDUWUserBL = new OLDUWUserBL();

		// logger.debug("Start OLDUWUser UI Submit...");
		tOLDUWUserBL.submitData(cInputData, mOperate);
		// logger.debug("End OLDUWUser UI Submit...");
		// 如果有需要处理的错误，则返回
		if (tOLDUWUserBL.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tOLDUWUserBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "OLDUWUserUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mOperate.equals("INSERT||MAIN")) {
			this.mResult.clear();
			this.mResult = tOLDUWUserBL.getResult();
		}
		mInputData = null;
		return true;
	}

	public static void main(String[] args) {
		LDUWUserSchema tLDUWUserSchema = new LDUWUserSchema();
		LDUWUserSchema tLDUWUserSchema1 = new LDUWUserSchema();

		LDUWGradeSet tLDUWGradeSet = new LDUWGradeSet();
		LDUWGradeSchema tLDUWGradeSchema = new LDUWGradeSchema();
		LDUWGradeSchema tLDUWGradeSchema1 = new LDUWGradeSchema();
		LDUWAmntGradeSet tLDUWAmntGradeSet = new LDUWAmntGradeSet();
		LDUWAmntGradeSchema tLDUWAmntGradeSchema = new LDUWAmntGradeSchema();

		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";
		tLDUWUserSchema.setUserCode("2");
		tLDUWUserSchema.setUWType("1");
		tLDUWUserSchema.setUWPopedom("H1");
		tLDUWUserSchema.setUpUWPopedom("A1");
		tLDUWUserSchema.setClaimPopedom("1");
		tLDUWUserSchema.setEdorPopedom("1");
		tLDUWUserSchema.setAddPoint("8");

		tLDUWGradeSchema.setUserCode("1");
		tLDUWGradeSchema.setUWType("1");
		tLDUWGradeSchema.setUWPopedomName("核报师一级");
		tLDUWGradeSchema.setUWPopedom("H1");
		tLDUWGradeSchema.setUWStateName("标准体");
		tLDUWGradeSchema.setUWState("4");

		tLDUWGradeSchema1.setUserCode("1");
		tLDUWGradeSchema1.setUWType("1");
		tLDUWGradeSchema1.setUWPopedomName("核报师一级");
		tLDUWGradeSchema1.setUWPopedom("H1");
		tLDUWGradeSchema1.setUWStateName("yaqi");
		tLDUWGradeSchema1.setUWState("2");

		tLDUWAmntGradeSchema.setUserCode("1");
		tLDUWAmntGradeSchema.setUWType("1");
		tLDUWAmntGradeSchema.setUWPopedom("H1");
		tLDUWAmntGradeSchema.setUWPopedomName("核报师一级");
		tLDUWAmntGradeSchema.setMaxAmnt("100000");
		tLDUWAmntGradeSchema.setUWKind("nj");
		tLDUWAmntGradeSet.add(tLDUWAmntGradeSchema);
		tLDUWGradeSet.add(tLDUWGradeSchema);
		tLDUWGradeSet.add(tLDUWGradeSchema1);
		tLDUWUserSchema1.setUserCode("6");
		tLDUWUserSchema1.setUWType("1");
		tLDUWUserSchema1.setUWPopedom("G2");

		VData tVData = new VData();
		tVData.add(tLDUWUserSchema);
		tVData.add(tLDUWGradeSet);
		tVData.add(tLDUWAmntGradeSet);
		tVData.add(tLDUWUserSchema1);
		tVData.add(tG);

		OLDUWUserUI tOLDUWUserUI = new OLDUWUserUI();
		tOLDUWUserUI.submitData(tVData, "UPDATE||MAIN");

	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(this.mLDUWUserSchema);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDUWUserUI";
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
		this.mLDUWUserSchema.setSchema((LDUWUserSchema) cInputData
				.getObjectByObjectName("LDUWUserSchema", 0));
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}
}
