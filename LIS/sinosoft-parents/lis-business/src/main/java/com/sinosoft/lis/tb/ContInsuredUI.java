/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;
/*
 * <p>ClassName: ContInsuredUI </p> <p>Description: ContInsuredUI类文件 </p> <p>Copyright:
 * Copyright (c) 2002</p> <p>Company: testcompany </p> @Database:
 * @CreateDate：2004-11-18 10:09:44
 */
public class ContInsuredUI  implements BusinessService{
private static Logger logger = Logger.getLogger(ContInsuredUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	// private VData mInputData = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	// 业务处理相关变量
	/** 全局数据 */
	// private LCInsuredSchema mLCInsuredSchema = new LCInsuredSchema();
	public ContInsuredUI() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		ContInsuredBL tContInsuredBL = new ContInsuredBL();

		// logger.debug("Start OLCInsured UI Submit...");
		tContInsuredBL.submitData(cInputData, mOperate);
		// logger.debug("End OLCInsured UI Submit...");
		// 如果有需要处理的错误，则返回
		if (tContInsuredBL.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tContInsuredBL.mErrors);
			return false;
		}
		if (!mOperate.equals("DELETE||CONTINSURED")) {
			this.mResult.clear();
			this.mResult = tContInsuredBL.getResult();
		}
		// mInputData = null;
		return true;
	}

	/**
	 * 得到处理后的结果集
	 * 
	 * @return 结果集
	 */
	public VData getResult() {
		return this.mResult;
	}

	public static void main(String[] args) {
		// GlobalInput tg = new GlobalInput();
		// tg.ManageCom = "86";
		// tg.Operator = "001";
		// LCContSchema tLCContSchema = new LCContSchema();
		// LDPersonSchema tLDPersonSchema = new LDPersonSchema();
		// LCInsuredSchema tmLCInsuredSchema = new LCInsuredSchema();
		// LCInsuredDB mLCInsuredDB = new LCInsuredDB();
		// //mLCInsuredDB.setContNo("");
		// mLCInsuredDB.setContNo("130110000012886");
		// mLCInsuredDB.setInsuredNo("0000478000");
		// mLCInsuredDB.getInfo();
		// LCCustomerImpartSet mLCCustomerImpartSet = new LCCustomerImpartSet();
		// LCCustomerImpartSchema mLCCustomerImpartSchema = new
		// LCCustomerImpartSchema();

		// LCAddressSchema mLCAddressSchema = new LCAddressSchema();
		// mLCCustomerImpartSchema.setContNo("120110000000151");
		// mLCCustomerImpartSchema.setGrpContNo("00000000000000000000");
		// mLCCustomerImpartSchema.setPrtNo("20022589000089");
		// mLCCustomerImpartSchema.setProposalContNo("130110000000197");
		// mLCCustomerImpartSchema.setCustomerNo("86110000000145");
		// mLCCustomerImpartSchema.setImpartVer("001");
		// mLCCustomerImpartSchema.setImpartCode("000");
		// mLCCustomerImpartSchema.setImpartContent("asdfasdfasd");
		// mLCCustomerImpartSchema.setImpartParamModle("asdfasdf");
		// mLCCustomerImpartSchema.setCustomerNoType("A");

		// mLCCustomerImpartSet.add(mLCCustomerImpartSchema);

		// tLCContSchema.setGrpContNo("140110000000410");
		// tLCContSchema.setPrtNo("234567890");
		// tLCContSchema.setContNo("130110000012886");
		// tmLCInsuredSchema.setContNo("");
		// //tmLCInsuredSchema.setInsuredNo("86110000000082 ");
		// //8tTransferData.setNameAndValue("ContNo","86110020040990000032");
		// tTransferData.setNameAndValue("FamilyType", "0");
		// tTransferData.setNameAndValue("ContType", "2");
		// tLDPersonSchema.setCustomerNo("0000478000");
		// tLDPersonSchema.setName("明明");
		// tLDPersonSchema.setSex("1");
		// tLDPersonSchema.setIDType("8");
		// tLDPersonSchema.setIDNo("0000000000");
		//
		// tLDPersonSchema.setBirthday("2005-1-1");
		// tLDPersonSchema.setOccupationCode("B256");
		// tLDPersonSchema.setOccupationType("4");
		// tmLCInsuredSchema.setRelationToMainInsured("00");
		//
		// tVData.add(tLCContSchema);
		// tVData.add(tLDPersonSchema);
		// tVData.add(mLCInsuredDB);
		// tVData.add(tmLCInsuredSchema);
		// tVData.add(tTransferData);
		// tVData.add(mLCCustomerImpartSet);
		// tVData.add(mLCAddressSchema);

		// TransferData tTransferData = new TransferData();
		// tTransferData.setNameAndValue("InsuredNo","0000392310");
		// tTransferData.setNameAndValue("PolNo","110110000018662");
		// VData tVData = new VData();
		// tVData.add(tg);
		// ContInsuredUI tContInsuredUI = new ContInsuredUI();
		// //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
		// if (tContInsuredUI.submitData(tVData, "DELETE||INSUREDRISK"))
		// {
		// VData tempVData = tContInsuredUI.getResult();
		// }
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return this.mErrors;
	}
}
