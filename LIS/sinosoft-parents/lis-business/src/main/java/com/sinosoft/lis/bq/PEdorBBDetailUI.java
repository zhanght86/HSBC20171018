package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LPAddressSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPGrpPolSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.schema.LPPersonSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:被保险人资料变更功能类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Tjj
 * @version 1.0
 */
public class PEdorBBDetailUI {
private static Logger logger = Logger.getLogger(PEdorBBDetailUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public PEdorBBDetailUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		PEdorBBDetailBLF tPEdorBBDetailBLF = new PEdorBBDetailBLF();
		logger.debug("---BB UI BEGIN---" + mOperate);
		if (tPEdorBBDetailBLF.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPEdorBBDetailBLF.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorBBDetailUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据查询失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		} else
			mResult = tPEdorBBDetailBLF.getResult();
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		LPGrpPolSchema tLPGrpPolSchema = new LPGrpPolSchema();
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		PEdorBBDetailUI tPEdorBBDetailUI = new PEdorBBDetailUI();

		LPPersonSchema tLPPersonSchema = new LPPersonSchema();
		LPAddressSchema tLPAddressSchema = new LPAddressSchema();
		LPContSchema tLPContSchema = new LPContSchema();
		LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();

		CErrors tError = null;
		// 后面要执行的动作：添加，修改

		String tRela = "";
		String FlagStr = "";
		String Content = "";
		String transact = "";
		String Result = "";

		// 执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
		transact = "INSERT||GRPEDORTYPEBB";
		logger.debug("------transact:" + transact);
		GlobalInput tG = new GlobalInput();
		logger.debug("------------------begin ui");
		tG.ManageCom = "86110000";
		tG.Operator = "001";
		tLPEdorItemDB.setEdorAcceptNo("86110000000063");
		tLPEdorItemDB.setEdorNo("410110000000063");
		tLPEdorItemDB.setInsuredNo("0000389760");
		tLPEdorItemDB.setPolNo("000000");
		tLPEdorItemDB.setGrpContNo("240110000000130");

		tLPEdorItemDB.setEdorType("BB");
		tLPEdorItemDB.setContNo("130110000009363");
		tLPEdorItemDB.getInfo();

		// 团体客户信息 LPGrp
		tLPPersonSchema.setEdorNo("410110000000063");
		tLPPersonSchema.setEdorType("BB");
		tLPPersonSchema.setCustomerNo("0000389760"); // 客户号码
		tLPPersonSchema.setName("asdfasd"); // 单位名称
		tLPPersonSchema.setSex("1"); // 单位性质
		tLPPersonSchema.setBirthday(""); // 行业类别
		tLPPersonSchema.setIDType(""); // 总人数
		tLPPersonSchema.setIDNo(""); // 注册资本
		tLPPersonSchema.setNativePlace(""); // 资产总额
		tLPPersonSchema.setPassword(""); // 净资产收益率
		tLPPersonSchema.setNationality(""); // 主营业务
		tLPPersonSchema.setRgtAddress(""); // 法人
		tLPPersonSchema.setMarriage(""); // 机构分布区域
		tLPPersonSchema.setMarriageDate("asdfasdf"); // 总机

		// 客户地址 LCGrpAddress
		tLPAddressSchema.setEdorNo("410110000000063");
		tLPAddressSchema.setEdorType("BB");
		tLPAddressSchema.setCustomerNo("0000389760"); // 客户号码
		tLPAddressSchema.setAddressNo(""); // 地址号码
		tLPAddressSchema.setHomeAddress(""); // 单位地址
		logger.debug("*******************" + "GrpAddress");
		tLPAddressSchema.setPhone("12312388");
		tLPAddressSchema.setMobile("1231238888888");
		tLPAddressSchema.setHomeFax("");
		tLPAddressSchema.setCompanyAddress("");
		tLPAddressSchema.setCompanyZipCode("");
		tLPAddressSchema.setCompanyPhone("");
		tLPAddressSchema.setCompanyFax("");
		// 个单合同
		tLPContSchema.setEdorNo("410110000000014");
		tLPContSchema.setGrpContNo("240110000000130");
		tLPContSchema.setEdorType("BB");
		tLPContSchema.setInsuredIDType("0");
		tLPContSchema.setInsuredIDNo("4101");

		// 被保人
		tLPInsuredSchema.setEdorNo("410110000000063");
		tLPInsuredSchema.setEdorType("BB");
		tLPInsuredSchema.setGrpContNo("240110000000130");
		tLPInsuredSchema.setContNo("130110000009363");

		tLPInsuredSchema.setInsuredNo("0000389760");
		tLPInsuredSchema.setPrtNo("");
		tLPInsuredSchema.setAppntNo("");
		tLPInsuredSchema.setManageCom("");
		tLPInsuredSchema.setExecuteCom("");

		// 准备传输数据 VData
		VData tVData = new VData();
		// 保存个人保单信息(保全)
		tVData.addElement("MOD");
		tVData.addElement(tG);
		tVData.addElement(tLPEdorItemDB.getSchema());

		tVData.addElement(tLPPersonSchema);
		tVData.addElement(tLPAddressSchema);
		tVData.addElement(tLPContSchema);
		tVData.addElement(tLPInsuredSchema);
		tPEdorBBDetailUI.submitData(tVData, "INSERT|GRPEDORTYPEBB");
		logger.debug("-------test...");
	}
}
