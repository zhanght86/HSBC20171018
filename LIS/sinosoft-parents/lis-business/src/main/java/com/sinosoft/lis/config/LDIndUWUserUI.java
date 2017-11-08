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

public class LDIndUWUserUI {
private static Logger logger = Logger.getLogger(LDIndUWUserUI.class);
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

	public LDIndUWUserUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		LDIndUWUserBL tLDIndUWUserBL = new LDIndUWUserBL();

		// logger.debug("Start OLDUWUser UI Submit...");
		tLDIndUWUserBL.submitData(cInputData, mOperate);
		// logger.debug("End OLDUWUser UI Submit...");
		// 如果有需要处理的错误，则返回
		if (tLDIndUWUserBL.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLDIndUWUserBL.mErrors);
			return false;
		}
		if (mOperate.equals("INSERT||MAIN")) {
			this.mResult.clear();
			this.mResult = tLDIndUWUserBL.getResult();
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
	public VData getResult() {
		return this.mResult;
	}
}
