package com.sinosoft.lis.finfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.operfee.IndiFinUrgeVerifyUI;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LJTempFeeClassSchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.lis.vschema.LJFIGetSet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Company:sinosoft
 * </p>
 */
public class TempFeeBLF {
private static Logger logger = Logger.getLogger(TempFeeBLF.class);
	// 错误处理
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap mMap = new MMap();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	private LJTempFeeSet mLJTempFeeSet = new LJTempFeeSet();
	private LJTempFeeClassSet mLJTempFeeClassSetNew = new LJTempFeeClassSet();
	private LJTempFeeClassSet mLJTempFeeClassSetDel = new LJTempFeeClassSet();
	private LCContSet mLCContSet = new LCContSet();
	private LJFIGetSet mLJFIGetSet = new LJFIGetSet();
	private LJAGetSet mLJAGetSet = new LJAGetSet();
	private GlobalInput tGI = new GlobalInput();

	public TempFeeBLF() {
	}

	public VData getResult() {
		return mResult;
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		// 数据准备操作
		if (!dealData()) {
			return false;
		}

		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "TempFeeBLF";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		/**
		 * ******************Del By Pst on 2007-11-29
		 * 由于在MS项目组不要自动保全确认****************************
		 */
		// if (mLJTempFeeSet.get(1).getTempFeeType().equals("4"))
		// {
		// logger.debug("开始自动保全确认");
		// logger.debug("保全申请号=====" + mLJTempFeeSet.get(1).getOtherNo());
		// if (mLJTempFeeSet.get(1).getEnterAccDate() == null ||
		// mLJTempFeeSet.get(1).getEnterAccDate().equals(null) ||
		// mLJTempFeeSet.get(1).equals(""))
		// {
		// logger.debug("该交费并未到帐，到帐确认时进行保全确认！");
		// // this.mErrors.copyAllErrors(tSubmit.mErrors);
		// // CError tError = new CError();
		// // tError.moduleName = "TempFeeBLF";
		// // tError.functionName = "submitData";
		// // tError.errorMessage = "暂收成功,但暂交费并未到帐,无法自动保全确认,到帐后进行该业务处理";
		// // this.mErrors.addOneError(tError);
		// // return false;
		// }
		// else
		// {
		// PEdorAutoConfirmBL t = new PEdorAutoConfirmBL(tGI);
		// if (!t.AutoConfirm(mLJTempFeeSet.get(1).getOtherNo()))
		// {
		// // @@错误处理
		// this.mErrors.copyAllErrors(tSubmit.mErrors);
		// // CError tError = new CError();
		// // tError.moduleName = "TempFeeBLF";
		// // tError.functionName = "submitData";
		// // tError.errorMessage = "保全确认失败!";
		// // this.mErrors.addOneError(tError);
		// return false;
		// }
		// }
		//
		// }
		/** ************************************************************************************************** */
		if (mLJTempFeeSet.get(1).getTempFeeType().equals("2")) {
			logger.debug("开始续期自动核销");
			logger.debug("保单号=====" + mLJTempFeeSet.get(1).getOtherNo());
			LJSPayDB tLJSPayDB = new LJSPayDB();
			tLJSPayDB.setGetNoticeNo(mLJTempFeeSet.get(1).getTempFeeNo());
			tLJSPayDB.getInfo();
			if (!tLJSPayDB.getOtherNoType().equals("")
					&& !tLJSPayDB.getOtherNoType().equals("1")) {
				if (mLJTempFeeSet.get(1).getEnterAccDate() == null
						|| mLJTempFeeSet.get(1).getEnterAccDate().equals(null)
						|| mLJTempFeeSet.get(1).equals("")) {
					logger.debug("该交费并未到帐，到帐确认时进行续期核销！");
				} else {
					String ContNo = mLJTempFeeSet.get(1).getOtherNo();
					LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
					tLJTempFeeSchema.setOtherNo(ContNo);
					VData tVData = new VData();
					tVData.add(tLJTempFeeSchema);
					tVData.add(tGI);

					IndiFinUrgeVerifyUI tIndiFinUrgeVerifyUI = new IndiFinUrgeVerifyUI();
					if (!tIndiFinUrgeVerifyUI.submitData(tVData, "VERIFY")) {
						// @@错误处理
						this.mErrors.copyAllErrors(tSubmit.mErrors);
						CError tError = new CError();
						tError.moduleName = "TempFeeBLF";
						tError.functionName = "submitData";
						tError.errorMessage = "暂收成功,但无法自动核销,原因是:"
								+ tIndiFinUrgeVerifyUI.mErrors.getFirstError();
						this.mErrors.addOneError(tError);
						// this.mErrors.copyAllErrors(tIndiFinUrgeVerifyUI.mErrors);
						// tIndiFinUrgeVerifyUI.mErrors.getFirstError();
						// this.mErrors.addOneError(tError);
						return false;
					}
				}
			}

		}
		// 本来打算团险自动核销 WL增加
		// if
		// (mLJTempFeeSet.get(1).getTempFeeType().equals("2")&&!mLJTempFeeSet.get(1).getOtherNoType().equals("2"))
		// {
		// logger.debug("开始续期自动核销");
		// logger.debug("保单号@@@@@@@====="+mLJTempFeeSet.get(1).getOtherNo());
		// if(mLJTempFeeSet.get(1).getEnterAccDate()==null||mLJTempFeeSet.get(1).getEnterAccDate().equals(null)||mLJTempFeeSet.get(1).equals(""))
		// {
		// logger.debug("该交费并未到帐，到帐确认时进行续期核销！");
		// // this.mErrors.copyAllErrors(tSubmit.mErrors);
		// // CError tError = new CError();
		// // tError.moduleName = "TempFeeBLF";
		// // tError.functionName = "submitData";
		// // tError.errorMessage = "暂收成功,但暂交费并未到帐,无法自动核销,到帐后进行该业务处理";
		// // this.mErrors.addOneError(tError);
		// // return false;
		// }
		// else
		// {
		// String GrpContNo = mLJTempFeeSet.get(1).getOtherNo();
		// LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
		// tLJTempFeeSchema.setOtherNo(GrpContNo);
		// VData tVData = new VData();
		// tVData.add(tLJTempFeeSchema);
		// tVData.add(tGI);
		//
		// IndiFinUrgeVerifyGrpUI tIndiFinUrgeVerifyGrpUI = new
		// IndiFinUrgeVerifyGrpUI();
		// if (!tIndiFinUrgeVerifyGrpUI.submitData(tVData,"VERIFY")) {
		// // @@错误处理
		// this.mErrors.copyAllErrors(tSubmit.mErrors);
		// CError tError = new CError();
		// tError.moduleName = "TempFeeBLF";
		// tError.functionName = "submitData";
		// tError.errorMessage =
		// "暂收成功,但无法自动核销@@@@@@,原因是:"+tIndiFinUrgeVerifyGrpUI.mErrors.getFirstError();
		// this.mErrors.addOneError(tError);
		// //this.mErrors.copyAllErrors(tIndiFinUrgeVerifyUI.mErrors);
		// //tIndiFinUrgeVerifyUI.mErrors.getFirstError();
		// //this.mErrors.addOneError(tError);
		// return false;
		// }
		// }
		//
		// }

		return true;
	}

	private boolean getInputData(VData mInputData) {
		mLJTempFeeSet.set((LJTempFeeSet) mInputData.getObjectByObjectName(
				"LJTempFeeSet", 0));
		tGI = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);
		return true;
	}

	private boolean dealData() {
		TempFeeBL tTempFeeBL = new TempFeeBL();
		logger.debug("!!!----GOGOGOGO----!!!");
		if (!tTempFeeBL.submitData(mInputData, mOperate)) {
			this.mErrors.copyAllErrors(tTempFeeBL.mErrors);

			return false;
		}
		mResult.clear();
		mResult = tTempFeeBL.getResult();
		return true;
	}

	public static void main(String[] args) {
		LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();
		LJTempFeeClassSet tLJTempFeeClassSet = new LJTempFeeClassSet();
		LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
		LJTempFeeClassSchema tLJTempFeeClassSchema = new LJTempFeeClassSchema();
		GlobalInput tGI = new GlobalInput();
		tGI.ComCode = "86110000";
		tGI.Operator = "001";
		tGI.ManageCom = "86110000";
		// 1st record:
		tLJTempFeeSchema = new LJTempFeeSchema();
		tLJTempFeeSchema.setTempFeeNo("3100000001079");
		tLJTempFeeSchema.setTempFeeType("4");
		tLJTempFeeSchema.setPayDate("2005-8-20");
		tLJTempFeeSchema.setPayMoney(30000);
		// tLJTempFeeSchema.setOperator("00000228");
		tLJTempFeeSchema.setEnterAccDate("2005-8-3");
		tLJTempFeeSchema.setRiskCode("00000000");
		tLJTempFeeSchema.setAgentCode("001001");
		tLJTempFeeSchema.setAgentGroup("010101");
		tLJTempFeeSchema.setManageCom("86110000");
		tLJTempFeeSchema.setOperator("001");
		tLJTempFeeSchema.setOtherNo("6120050901000002");
		tLJTempFeeSchema.setOtherNoType("10");
		tLJTempFeeSet.add(tLJTempFeeSchema);
		//
		tLJTempFeeClassSchema = new LJTempFeeClassSchema();
		tLJTempFeeClassSchema.setTempFeeNo("3100000001079");
		tLJTempFeeClassSchema.setPayMode("1");
		// tLJTempFeeClassSchema.setChequeNo("86000020030370000002");
		tLJTempFeeClassSchema.setPayDate("2005-8-22");
		tLJTempFeeClassSchema.setPayMoney(30000);
		tLJTempFeeClassSchema.setEnterAccDate("2005-8-20");
		tLJTempFeeClassSchema.setManageCom("86110000");
		// tLJTempFeeClassSchema.setBankAccNo("77777777777777");
		// tLJTempFeeClassSchema.setBankCode("0101");
		// tLJTempFeeClassSchema.setAccName("月亮");
		tLJTempFeeClassSchema.setOperator("001");
		tLJTempFeeClassSchema.setManageCom("86110000");
		tLJTempFeeClassSchema.setOtherNo("6120050901000002");
		tLJTempFeeClassSchema.setOtherNoType("10");
		tLJTempFeeClassSet.add(tLJTempFeeClassSchema);
		// logger.debug(tob.toString());

		/*
		 * tLJTempFeeClassSchema = new LJTempFeeClassSchema();
		 * tLJTempFeeClassSchema.setTempFeeNo("12222222222222222223");
		 * tLJTempFeeClassSchema.setPayMode("1");
		 * tLJTempFeeClassSchema.setPayDate("2003-3-5");
		 * tLJTempFeeClassSchema.setPayMoney(10);
		 * tLJTempFeeClassSchema.setEnterAccDate("2003-3-5");
		 * tLJTempFeeClassSchema.setManageCom("86110000");
		 * tLJTempFeeClassSet.add(tLJTempFeeClassSchema); //2nd record:
		 * tLJTempFeeSchema = new LJTempFeeSchema();
		 * tLJTempFeeSchema.setTempFeeNo("12222222222222222224");
		 * tLJTempFeeSchema.setTempFeeType("1");
		 * tLJTempFeeSchema.setPayDate("2003-3-5");
		 * tLJTempFeeSchema.setPayMoney(10);
		 * tLJTempFeeSchema.setEnterAccDate("2003-3-5");
		 * tLJTempFeeSchema.setRiskCode("111301");
		 * tLJTempFeeSchema.setAgentCode("0000000001");
		 * tLJTempFeeSchema.setAgentGroup("010101");
		 * tLJTempFeeSchema.setManageCom("86110000");
		 * tLJTempFeeSchema.setOperator("001");
		 * tLJTempFeeSet.add(tLJTempFeeSchema);
		 * 
		 * tLJTempFeeClassSchema = new LJTempFeeClassSchema();
		 * tLJTempFeeClassSchema.setTempFeeNo("12222222222222222224");
		 * tLJTempFeeClassSchema.setPayMode("1");
		 * tLJTempFeeClassSchema.setPayDate("2003-3-5");
		 * tLJTempFeeClassSchema.setPayMoney(10);
		 * tLJTempFeeClassSchema.setEnterAccDate("2003-3-5");
		 * tLJTempFeeClassSchema.setManageCom("86110000");
		 * tLJTempFeeClassSet.add(tLJTempFeeClassSchema); //3rd record:
		 * tLJTempFeeSchema = new LJTempFeeSchema();
		 * tLJTempFeeSchema.setTempFeeNo("12222222222222222225");
		 * tLJTempFeeSchema.setTempFeeType("1");
		 * tLJTempFeeSchema.setPayDate("2003-3-5");
		 * tLJTempFeeSchema.setPayMoney(10);
		 * tLJTempFeeSchema.setEnterAccDate("2003-3-5");
		 * tLJTempFeeSchema.setRiskCode("111301");
		 * tLJTempFeeSchema.setAgentCode("0000000001");
		 * tLJTempFeeSchema.setAgentGroup("010101");
		 * tLJTempFeeSchema.setManageCom("86110000");
		 * tLJTempFeeSchema.setOperator("001");
		 * tLJTempFeeSet.add(tLJTempFeeSchema);
		 * 
		 * tLJTempFeeSchema = new LJTempFeeSchema();
		 * tLJTempFeeSchema.setTempFeeNo("12222222222222222225");
		 * tLJTempFeeSchema.setTempFeeType("1");
		 * tLJTempFeeSchema.setPayDate("2003-3-5");
		 * tLJTempFeeSchema.setPayMoney(10);
		 * tLJTempFeeSchema.setEnterAccDate("2003-3-5");
		 * tLJTempFeeSchema.setRiskCode("111501");
		 * tLJTempFeeSchema.setAgentCode("0000000001");
		 * tLJTempFeeSchema.setAgentGroup("010101");
		 * tLJTempFeeSchema.setManageCom("86110000");
		 * tLJTempFeeSchema.setOperator("001");
		 * tLJTempFeeSet.add(tLJTempFeeSchema);
		 * 
		 * tLJTempFeeClassSchema = new LJTempFeeClassSchema();
		 * tLJTempFeeClassSchema.setTempFeeNo("12222222222222222225");
		 * tLJTempFeeClassSchema.setPayMode("1");
		 * tLJTempFeeClassSchema.setPayDate("2003-3-5");
		 * tLJTempFeeClassSchema.setPayMoney(20);
		 * tLJTempFeeClassSchema.setEnterAccDate("2003-3-5");
		 * tLJTempFeeClassSchema.setManageCom("86110000");
		 * tLJTempFeeClassSet.add(tLJTempFeeClassSchema);
		 */
		// 5th record:
		// tLJTempFeeSchema = new LJTempFeeSchema();
		// tLJTempFeeSchema.setTempFeeNo("12222222222222222226");
		// tLJTempFeeSchema.setTempFeeType("1");
		// tLJTempFeeSchema.setPayDate("2003-3-5");
		// tLJTempFeeSchema.setPayMoney(10);
		// tLJTempFeeSchema.setEnterAccDate("2003-3-5");
		// tLJTempFeeSchema.setRiskCode("111301");
		// tLJTempFeeSchema.setAgentCode("0000000001");
		// tLJTempFeeSchema.setAgentGroup("010101");
		// tLJTempFeeSchema.setManageCom("86110000");
		// tLJTempFeeSchema.setOperator("001");
		// tLJTempFeeSet.add(tLJTempFeeSchema);
		//
		// tLJTempFeeSchema = new LJTempFeeSchema();
		// tLJTempFeeSchema.setTempFeeNo("1222222222226");
		// tLJTempFeeSchema.setTempFeeType("1");
		// tLJTempFeeSchema.setOtherNo("130000000000026");
		// tLJTempFeeSchema.setOtherNoType("1");
		// tLJTempFeeSchema.setPayDate("2003-3-5");
		// tLJTempFeeSchema.setPayMoney(10);
		// tLJTempFeeSchema.setEnterAccDate("2003-3-5");
		// tLJTempFeeSchema.setRiskCode("111501");
		// tLJTempFeeSchema.setAgentCode("00000228");
		// tLJTempFeeSchema.setRiskCode("000000");
		// tLJTempFeeSchema.setAgentGroup("010101");
		// tLJTempFeeSchema.setManageCom("86110000");
		// tLJTempFeeSchema.setOperator("001");
		// tLJTempFeeSet.add(tLJTempFeeSchema);
		//
		// tLJTempFeeClassSchema = new LJTempFeeClassSchema();
		// tLJTempFeeClassSchema.setTempFeeNo("12222222222222222226");
		// tLJTempFeeClassSchema.setPayMode("1");
		// tLJTempFeeClassSchema.setPayDate("2005-7-5");
		// tLJTempFeeClassSchema.setPayMoney(10);
		// tLJTempFeeClassSchema.setEnterAccDate("2005-7-5");
		// tLJTempFeeClassSchema.setManageCom("86110000");
		// tLJTempFeeClassSchema.setOperator("001");
		// tLJTempFeeClassSet.add(tLJTempFeeClassSchema);
		/*
		 * //6th record: tLJTempFeeSchema = new LJTempFeeSchema();
		 * tLJTempFeeSchema.setTempFeeNo("12222222222222222227");
		 * tLJTempFeeSchema.setTempFeeType("1");
		 * tLJTempFeeSchema.setPayDate("2003-3-5");
		 * tLJTempFeeSchema.setPayMoney(10);
		 * tLJTempFeeSchema.setEnterAccDate("2003-3-5");
		 * tLJTempFeeSchema.setRiskCode("111301");
		 * tLJTempFeeSchema.setAgentCode("0000000001");
		 * tLJTempFeeSchema.setAgentGroup("010101");
		 * tLJTempFeeSchema.setManageCom("86110000");
		 * tLJTempFeeSchema.setOperator("001");
		 * tLJTempFeeSet.add(tLJTempFeeSchema);
		 * 
		 * tLJTempFeeClassSchema = new LJTempFeeClassSchema();
		 * tLJTempFeeClassSchema.setTempFeeNo("12222222222222222227");
		 * tLJTempFeeClassSchema.setPayMode("1");
		 * tLJTempFeeClassSchema.setPayDate("2003-3-5");
		 * tLJTempFeeClassSchema.setPayMoney(10);
		 * tLJTempFeeClassSchema.setEnterAccDate("2003-3-5");
		 * tLJTempFeeClassSchema.setManageCom("86110000");
		 * tLJTempFeeClassSet.add(tLJTempFeeClassSchema); //7th record:
		 * tLJTempFeeSchema = new LJTempFeeSchema();
		 * tLJTempFeeSchema.setTempFeeNo("12222222222222222229");
		 * tLJTempFeeSchema.setTempFeeType("1");
		 * tLJTempFeeSchema.setPayDate("2003-3-5");
		 * tLJTempFeeSchema.setPayMoney(10);
		 * tLJTempFeeSchema.setEnterAccDate("2003-3-5");
		 * tLJTempFeeSchema.setRiskCode("111301");
		 * tLJTempFeeSchema.setAgentCode("0000000001");
		 * tLJTempFeeSchema.setAgentGroup("010101");
		 * tLJTempFeeSchema.setManageCom("86110000");
		 * tLJTempFeeSchema.setOperator("001");
		 * tLJTempFeeSet.add(tLJTempFeeSchema);
		 * 
		 * tLJTempFeeSchema = new LJTempFeeSchema();
		 * tLJTempFeeSchema.setTempFeeNo("12222222222222222229");
		 * tLJTempFeeSchema.setTempFeeType("1");
		 * tLJTempFeeSchema.setPayDate("2003-3-5");
		 * tLJTempFeeSchema.setPayMoney(10);
		 * tLJTempFeeSchema.setEnterAccDate("2003-3-5");
		 * tLJTempFeeSchema.setRiskCode("111501");
		 * tLJTempFeeSchema.setAgentCode("0000000001");
		 * tLJTempFeeSchema.setAgentGroup("010101");
		 * tLJTempFeeSchema.setManageCom("86110000");
		 * tLJTempFeeSchema.setOperator("001");
		 * tLJTempFeeSet.add(tLJTempFeeSchema);
		 * 
		 * tLJTempFeeClassSchema = new LJTempFeeClassSchema();
		 * tLJTempFeeClassSchema.setTempFeeNo("12222222222222222229");
		 * tLJTempFeeClassSchema.setPayMode("1");
		 * tLJTempFeeClassSchema.setPayDate("2003-3-5");
		 * tLJTempFeeClassSchema.setPayMoney(20);
		 * tLJTempFeeClassSchema.setEnterAccDate("2003-3-5");
		 * tLJTempFeeClassSchema.setManageCom("86110000");
		 * tLJTempFeeClassSet.add(tLJTempFeeClassSchema);
		 */
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("CertifyFlag", "1");
		VData tVData = new VData();
		tVData.addElement(tLJTempFeeSet);
		tVData.addElement(tLJTempFeeClassSet);
		tVData.addElement(tGI);
		tVData.addElement(tTransferData);
		TempFeeBLF tTempFeeBL = new TempFeeBLF();

		if (!tTempFeeBL.submitData(tVData, "INSERT")) {
			logger.debug(tTempFeeBL.mErrors.getErrContent());
		}

	}
}
