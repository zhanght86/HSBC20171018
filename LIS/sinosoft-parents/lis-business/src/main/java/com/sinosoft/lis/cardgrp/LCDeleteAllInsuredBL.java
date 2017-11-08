package com.sinosoft.lis.cardgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCAccountSchema;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCBnfSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.vschema.LACommisionDetailSet;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class LCDeleteAllInsuredBL {
private static Logger logger = Logger.getLogger(LCDeleteAllInsuredBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private MMap mapContBL = new MMap();
	private MMap mapInsuredBL = new MMap();
	private MMap mapLcpolBL = new MMap();
	private MMap total = new MMap();
	private MMap map = new MMap();
	private String contno;
	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData;
	private VData rInputData; // 被保险人
	private VData aInputData; // 合同信息与投保人
	/** 数据操作字符串 */
	private String mOperate;

	// 统一更新日期，时间

	/** 业务处理相关变量 */
	/** 接受前台传输数据的容器 */

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 其他相关表 */
	private LCContSchema mLCContSchema = new LCContSchema();

	public LCDeleteAllInsuredBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		// 得到外部传入的数据，将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		// 准备往后台的数据
		this.prepareOutputData();

		PubSubmit tPubSubmit = new PubSubmit();

		if (!tPubSubmit.submitData(mInputData, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);

			CError tError = new CError();
			tError.moduleName = "ContBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";

			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 * 
	 * @param vData
	 *            VData
	 * @return boolean
	 */
	private void prepareOutputData() {
		logger.debug("操作方式===== in cardproposalbl=" + mOperate);
		mInputData.clear();

		mInputData.add(map);
	}

	private boolean prepareOutputData(VData vData) {
		try {

		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("prepareOutputData", "发生异常");
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行UI逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		logger.debug("contno:" + contno);
		map.put("delete from lcinsured where grpcontno='" + contno + "'",
				"DELETE");
		map.put("delete from LCCustomerImpartParams where grpcontno='" + contno
				+ "'", "DELETE");
		map
				.put("delete from LCCustomerImpart where grpcontno='" + contno
						+ "'", "DELETE");
		map.put("delete from LCCustomerImpartDetail where grpcontno='" + contno
				+ "'", "DELETE");
		map.put(
				"delete from lcduty where polno in (select polno from lcpol where grpcontno='"
						+ contno + "')", "DELETE");
		map
				.put("delete from lcprem where grpcontno='" + contno + "'",
						"DELETE");
		map
				.put("delete from lcget where  grpcontno='" + contno + "'",
						"DELETE");
		map
				.put("delete from lccont where grpcontno='" + contno + "'",
						"DELETE");
		//add by liuqh 2009-02-11 删除被保人时删除连带被保人
		map.put("delete from lcinsuredrelated where polno in (select polno from lcpol where grpcontno = '"+contno+"')", "DELETE");
		//删除连带放在lcpol上面 否则lcinsuredrelated查不到数据
		map.put("delete from lcpol where grpcontno='" + contno + "'", "DELETE");
		map
				.put(
						"update lcgrppol set peoples2=0,prem=0,sumprem=0,amnt=0,paytodate='',payenddate='' where grpcontno='"
								+ contno + "'", "UPDATE");
		map.put(
				"update lcgrpcont set peoples=0,prem=0,sumprem=0,amnt=0 where grpcontno='"
						+ contno + "'", "UPDATE");
		map.put("delete from llaccount where grpcontno='" + contno + "'",
				"DELETE");
		map.put("delete from LCGrpImportLog where grpcontno='" + contno + "'",
				"DELETE"); // 导入日志表，如果不删，总是提示导入成功。
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));

		if (mGlobalInput == null) {
			CError tError = new CError();
			tError.moduleName = "CardBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "处理mGlobalInput出错!";
			this.mErrors.addOneError(tError);

		}

		mLCContSchema.setSchema((LCContSchema) cInputData
				.getObjectByObjectName("LCContSchema", 0));
		if (mLCContSchema == null) {
			CError tError = new CError();
			tError.moduleName = "CardBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "数据提交失败，您的网页可能已超时!";
			this.mErrors.addOneError(tError);

		}
		contno = mLCContSchema.getGrpContNo();
		if (contno.equals("false") || contno.equals("")) {
			CError tError = new CError();
			tError.moduleName = "CardBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "数据提交失败，您的网页可能已超时!";
			this.mErrors.addOneError(tError);

		}
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "DerferAppF1PUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * 测试函数
	 * 
	 * @param args
	 *            String[]
	 */
	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.ManageCom = "86110000";
		tG.Operator = "001";
		tG.ComCode = "86110000";

		// 合同信息
		LCContSchema tLCContSchema = new LCContSchema();
		tLCContSchema.setProposalContNo("00200508270000");
		tLCContSchema.setPrtNo("00200508270000");
		tLCContSchema.setManageCom("86110000");
		tLCContSchema.setSellType("01");
		tLCContSchema.setAgentCode("00000039");
		tLCContSchema.setAgentGroup("86110000");
		tLCContSchema.setAgentCom("86110000");
		// 投保人信息
		LCAppntSchema tLCAppntSchema = new LCAppntSchema();

		tLCAppntSchema.setAppntName("xx");
		tLCAppntSchema.setAppntSex("1");
		tLCAppntSchema.setAppntBirthday("1981-01-01");
		tLCAppntSchema.setIDType("8");
		tLCAppntSchema.setIDNo("123");
		tLCAppntSchema.setOccupationType("B001001");
		tLCAppntSchema.setOccupationCode("B001001");
		// 地址信息
		LCAddressSchema mLCAddressSchema = new LCAddressSchema();
		mLCAddressSchema.setProvince("213");
		mLCAddressSchema.setCity("213");
		mLCAddressSchema.setCounty("313251");

		// 被保险人
		LCInsuredSchema tCInsuredSchema = new LCInsuredSchema();
		tCInsuredSchema.setName("xx");
		tCInsuredSchema.setSex("1");
		tCInsuredSchema.setBirthday("1981-01-01");
		tCInsuredSchema.setIDType("8");
		tCInsuredSchema.setIDNo("123");
		tCInsuredSchema.setOccupationType("B001001");
		tCInsuredSchema.setOccupationCode("B001001");

		LCAccountSchema mLCAccountSchema = new LCAccountSchema();
		LCCustomerImpartSet mLCCustomerImpartSet = new LCCustomerImpartSet();
		LACommisionDetailSet mLACommisionDetailSet = new LACommisionDetailSet();

		// 受益人信息
		LCBnfSet tLCBnfSet = new LCBnfSet();
		LCBnfSchema tLCBnfSchema = new LCBnfSchema();
		tLCBnfSchema.setBnfType("1");
		tLCBnfSchema.setName("xx");
		tLCBnfSchema.setIDType("8");
		tLCBnfSchema.setIDNo("123");
		tLCBnfSet.add(tLCBnfSchema);
		VData tVData = new VData();
		tVData.add(tLCContSchema);
		tVData.add(tCInsuredSchema);
		tVData.add(tLCAppntSchema);
		tVData.add(tLCBnfSet);
		tVData.addElement(tG);
		tVData.add(mLCAddressSchema);
		tVData.add(mLCAccountSchema);
		tVData.add(mLCCustomerImpartSet);
		tVData.add(mLACommisionDetailSet);
		// CardProposalBL tCardProposalBL = new CardProposalBL();
		// tCardProposalBL.submitData(tVData, "");

	}

}
