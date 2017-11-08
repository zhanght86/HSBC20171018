package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LPAddressSchema;
import com.sinosoft.lis.schema.LPAppntSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;

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
public class PEdorADDetailUI implements BusinessService {
private static Logger logger = Logger.getLogger(PEdorADDetailUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public PEdorADDetailUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		PEdorADDetailBL tPEdorADDetailBL = new PEdorADDetailBL();
		logger.debug("---BB UI BEGIN---" + mOperate);
		if (tPEdorADDetailBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPEdorADDetailBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorADDetailUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据查询失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		} else
			mResult = tPEdorADDetailBL.getResult();
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {

		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		PEdorADDetailUI tPEdorADDetailUI = new PEdorADDetailUI();

		LPAddressSchema tLPAddressSchema = new LPAddressSchema();
		LPContSchema tLPContSchema = new LPContSchema();
		LPContSet tLPContSet = new LPContSet();

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
		tLPEdorItemDB.setEdorAcceptNo("86000000000295");
		tLPEdorItemDB.setEdorNo("410000000000305");
		tLPEdorItemDB.setContNo("230110000000458");

		tLPEdorItemDB.setInsuredNo("000000");
		tLPEdorItemDB.setPolNo("000000");
		tLPEdorItemDB.setEdorType("AD");

		tLPEdorItemDB.getInfo();
		LPAppntSchema tLPAppntSchema = new LPAppntSchema();
		tLPAppntSchema.setContNo("230110000000458");
		tLPAppntSchema.setEdorNo("410000000000305");
		tLPAppntSchema.setEdorType("AD");

		tLPAppntSchema.setAppntNo("0000490790");

		// 客户地址 LCGrpAddress
		tLPAddressSchema.setEdorNo("410000000000305");
		tLPAddressSchema.setEdorType("AD");
		tLPAddressSchema.setCustomerNo("0000490790"); // 客户号码
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

		tLPContSchema.setContNo("230110000000458");

		tLPContSet.add(tLPContSchema);

		// 准备传输数据 VData
		VData tVData = new VData();
		// 保存个人保单信息(保全)
		tVData.addElement("MOD");
		tVData.addElement(tG);
		tVData.addElement(tLPEdorItemDB.getSchema());

		tVData.addElement(tLPAddressSchema);
		tVData.addElement(tLPContSet);
		tVData.addElement(tLPAppntSchema);

		tPEdorADDetailUI.submitData(tVData, "INSERT|GRPEDORTYPEBB");
		logger.debug("-------test...");
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}
}
