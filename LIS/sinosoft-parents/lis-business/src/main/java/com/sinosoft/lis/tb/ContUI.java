package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCAccountSchema;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCCustomerImpartSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.lis.vschema.LDPersonSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: UI功能类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author HST
 * @version 1.0
 * @date 2002-09-25
 */
public class ContUI implements BusinessService {
private static Logger logger = Logger.getLogger(ContUI.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 往前面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	// @Constructor
	public ContUI() {
	}

	// @Main
	public static void main(String[] args) {
		ContUI tGroupPolUI = new ContUI();
		LCContSchema mLCContSchema = new LCContSchema();
		LCAppntSchema mLCAppntSchema = new LCAppntSchema();
		LDPersonSet mLDPersonSet = new LDPersonSet();
		LDPersonSchema tLDPersonSchema = new LDPersonSchema();
		LCAddressSchema mLCAddressSchema = new LCAddressSchema();
		LCAccountSchema mLCAccountSchema = new LCAccountSchema();
		// LCInsuredSchema mLCInsuredSchema = new LCInsuredSchema();
		mLCAddressSchema.setHomeAddress("asdfasdf");
		mLCContSchema.setPrtNo("20041117000014");
		mLCContSchema.setAgentCode("8611000433");
		mLCContSchema.setAgentGroup("000000000448");
		mLCContSchema.setManageCom("8611");
		mLCContSchema.setSignCom("8611");
		mLCContSchema.setPolType("0");
		mLCContSchema.setContType("1");
		mLCContSchema.setPolApplyDate("0000-00-00");
		mLCAppntSchema.setAppntName("aaaaaaa");
		mLCAppntSchema.setAppntSex("0");
		mLCAppntSchema.setAppntBirthday("1979-01-01");
		mLCAppntSchema.setIDType("0");
		mLCAppntSchema.setIDNo("422103197901010870");
		// mLDPersonSet.add(tLDPersonSchema);

		// LDPersonSchema tLDPersonSchema1=new LDPersonSchema();
		// tLDPersonSchema1.setName("BBBBa");
		// tLDPersonSchema1.setSex("1");
		// tLDPersonSchema1.setBirthday("1979-01-01");
		// tLDPersonSchema1.setIDType("0");
		// tLDPersonSchema1.setIDNo("422103197901010871");
		//
		// mLDPersonSet.add(tLDPersonSchema1);
		LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
		LCCustomerImpartSchema tLCCustomerImpartSchema = new LCCustomerImpartSchema();
		tLCCustomerImpartSchema.setImpartCode("010");
		tLCCustomerImpartSchema.setImpartVer("001");
		tLCCustomerImpartSchema.setCustomerNoType("A");
		tLCCustomerImpartSchema.setImpartContent("175,20");
		tLCCustomerImpartSet.add(tLCCustomerImpartSchema);
		VData tInputData = new VData();
		GlobalInput tGI = new GlobalInput();
		tGI.ManageCom = "86110000";
		tGI.Operator = "000006";
		// tInputData.add(tLDPersonSchema);
		tInputData.add(tLCCustomerImpartSet);
		tInputData.add(mLCContSchema);
		tInputData.add(mLCAppntSchema);
		tInputData.add(mLCAddressSchema);
		tInputData.add(mLCAccountSchema);
		// tInputData.add( mLCInsuredSchema);
		tInputData.add(tGI);
		tGroupPolUI.submitData(tInputData, "INSERT||CONT");
	}

	// @Method
	/**
	 * 数据提交方法
	 * 
	 * @param: cInputData 传入的数据 cOperate 数据操作字符串
	 * @return: boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 数据操作字符串拷贝到本类中
		this.mOperate = cOperate;

		ContBL tContBL = new ContBL();

		logger.debug("---UI BEGIN---");
		if (tContBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tContBL.mErrors);
			return false;
		} else {
			mResult = tContBL.getResult();
		}
		logger.debug(mErrors.toString());
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

}
