package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LLBnfGatherDB;
import com.sinosoft.lis.db.LLCaseDB;
import com.sinosoft.lis.db.LLClaimDetailDB;
import com.sinosoft.lis.db.LLSubReportDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.schema.LLBnfGatherSchema;
import com.sinosoft.lis.schema.LLBnfSchema;
import com.sinosoft.lis.schema.LLCaseSchema;
import com.sinosoft.lis.schema.LLSubReportSchema;
import com.sinosoft.lis.vschema.LJSGetClaimSet;
import com.sinosoft.lis.vschema.LJSGetSet;
import com.sinosoft.lis.vschema.LLBnfGatherSet;
import com.sinosoft.lis.vschema.LLBnfSet;
import com.sinosoft.lis.vschema.LLCaseSet;
import com.sinosoft.lis.vschema.LLClaimDetailSet;
import com.sinosoft.lis.vschema.LLSubReportSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class LLGrpBnf {
private static Logger logger = Logger.getLogger(LLGrpBnf.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	private GlobalInput mG = new GlobalInput();
	private TransferData mTransferData = new TransferData();
	private MMap mMap = new MMap();

	private LLBnfSet mLLBnfSet = new LLBnfSet();
	private LLBnfSet saveLLBnfSet = new LLBnfSet();
	private LLBnfGatherSet mLLBnfGatherSet = new LLBnfGatherSet();
	private LJSGetSet mLJSGetSet = new LJSGetSet();
	private LJSGetClaimSet mLJSGetClaimSet = new LJSGetClaimSet();

	private String mClmNo;

	public LLGrpBnf() {
	}

	public static void main(String[] args) {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 首先将数据在本类中做一个备份
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("----------LLBnfBL after getInputData----------");

		// 检查数据合法性
		if (!checkInputData()) {
			return false;
		}
		logger.debug("----------LLBnfBL after checkInputData----------");

		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("----------LLBnfBL after dealData----------");

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("----------LLBnfBL after prepareOutputData----------");

		// PubSubmit tPubSubmit = new PubSubmit();
		// if (!tPubSubmit.submitData(mInputData, cOperate)) {
		// // @@错误处理
		// CError
		// .buildErr(this, "数据提交失败,"
		// + tPubSubmit.mErrors.getLastError());
		// return false;
		// }
		mInputData = null;
		return true;

	}

	private boolean getInputData() {
		mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0); // 按类取值
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mClmNo = (String) mTransferData.getValueByName("ClmNo");
		return true;
	}

	private boolean checkInputData() {
		if (mClmNo == null || mClmNo.equals("")) {

			// @@错误处理
			CError.buildErr(this, "传入的赔案号为空!");
			return false;
		}
		return true;
	}

	private boolean dealData() {
		
		
		LLCaseDB tLLCaseDB = new LLCaseDB();
		LLCaseSet tLLCaseSet = new LLCaseSet();
		tLLCaseDB.setCaseNo(mClmNo);
		tLLCaseSet=tLLCaseDB.query();
		
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		VData tVData = new VData();
		
		LLBnfSchema tLLBnfSchema = null;// 受益人明细表

		int No=1;
		
		for(int k=1;k<=tLLCaseSet.size();k++){
			
			/**
			 * 查询初始化待分配的记录
			 */
			String sql="select GrpContNo,GrpPolNo,ContNo,polno,sum(pay),currency from LLBalance a where 1=1 "
	               + " and clmno='"+mClmNo+"'"
	               + " and customerno='"+tLLCaseSet.get(k).getCustomerNo()+"'"
	               + " group by GrpContNo,GrpPolNo,ContNo,polno,currency"
	               + " order by GrpContNo,GrpPolNo,ContNo,polno,currency";
			
			logger.debug("--查询案件:"+mClmNo+"批量受益人分配sql:"+sql);
			
			tSSRS =  tExeSQL.execSQL(sql);
			
			for (int i = 1; i <= tSSRS.getMaxRow(); i++) {			

				tLLBnfSchema = new LLBnfSchema();
				tLLBnfSchema.setClmNo(mClmNo);
				tLLBnfSchema.setCaseNo(mClmNo);
				tLLBnfSchema.setBatNo("0");
				tLLBnfSchema.setGrpContNo(tSSRS.GetText(i,1));
				tLLBnfSchema.setGrpPolNo(tSSRS.GetText(i,2));
				tLLBnfSchema.setContNo(tSSRS.GetText(i,3));
				tLLBnfSchema.setBnfKind("A");
				tLLBnfSchema.setPolNo(tSSRS.GetText(i,4));
				tLLBnfSchema.setInsuredNo(tLLCaseSet.get(k).getCustomerNo());				
				tLLBnfSchema.setBnfNo(String.valueOf(No));
				tLLBnfSchema.setCustomerNo("0");
				tLLBnfSchema.setName(tLLCaseSet.get(k).getBnfName());
				tLLBnfSchema.setPayeeNo("0");
				tLLBnfSchema.setPayeeName(tLLCaseSet.get(k).getBnfName());
				tLLBnfSchema.setBnfType(tLLCaseSet.get(k).getBnfIDType());
				tLLBnfSchema.setBnfGrade("1");
				tLLBnfSchema.setRelationToInsured(tLLCaseSet.get(k).getRelationToInsured());
				tLLBnfSchema.setSex(tLLCaseSet.get(k).getBnfSex());
				tLLBnfSchema.setBirthday(tLLCaseSet.get(k).getBirthday());
				tLLBnfSchema.setIDType(tLLCaseSet.get(k).getBnfIDType());
				tLLBnfSchema.setIDNo(tLLCaseSet.get(k).getBnfIDNo());
				tLLBnfSchema.setRelationToPayee(tLLCaseSet.get(k).getRelationToInsured());
				tLLBnfSchema.setPayeeSex(tLLCaseSet.get(k).getBnfSex());
				tLLBnfSchema.setPayeeBirthday(tLLCaseSet.get(k).getBirthday());
				tLLBnfSchema.setPayeeIDType(tLLCaseSet.get(k).getBnfIDType());
				tLLBnfSchema.setPayeeIDNo(tLLCaseSet.get(k).getBnfIDNo());
				tLLBnfSchema.setGetMoney(tSSRS.GetText(i,5));
				tLLBnfSchema.setBnfLot(100);
				tLLBnfSchema.setCasePayMode(tLLCaseSet.get(k).getCaseGetMode());
				tLLBnfSchema.setCasePayFlag("0");// 保险金支付标志
				tLLBnfSchema.setBankCode(tLLCaseSet.get(k).getBankCode());
				tLLBnfSchema.setBankAccNo(tLLCaseSet.get(k).getBankAccNo());
				tLLBnfSchema.setAccName(tLLCaseSet.get(k).getBnfAccName());
				tLLBnfSchema.setFeeOperationType("A");
				tLLBnfSchema.setCurrency(tSSRS.GetText(i,6));

				if (tLLBnfSchema.getOBankCode() == null || tLLBnfSchema.equals("")) {
					tLLBnfSchema.setOBankCode(tLLBnfSchema.getBnfNo());
				}

				mLLBnfSet.add(tLLBnfSchema);
				

				
				tLLBnfSchema=null;
			}
			
			No++;
			

			
			mTransferData.setNameAndValue("PolNo", tSSRS.GetText(k,4));
			mTransferData.setNameAndValue("BnfKind", "A");
			mTransferData.setNameAndValue("CustomerNo", tLLCaseSet.get(k).getCustomerNo());
			mTransferData.setNameAndValue("FeeOperationType", "A");
			
			tVData.add(mLLBnfSet);
			tVData.add(mG);
			tVData.add(mTransferData);
		}
		
		LLGrpBnfBL tLLGrpBnfBL = new LLGrpBnfBL();
		
		// 模仿个险的BNF处理方式，在本类中准备相应数据提交到LLGrpBnfBL中 -_-!!
		if (!tLLGrpBnfBL.submitData(tVData, "INSERT")) {
			CError.buildErr(this, "执行LLGrpBnfBL出错！");
			return false;
		}
		
		VData tempVData = null;
		MMap tempMMap = null;
		LLBnfSet tempLLBnfSet = new LLBnfSet();
		LLBnfGatherSet tempLLBnfGatherSet = new LLBnfGatherSet();
		LJSGetSet tempLJSGetSet = new LJSGetSet();
		LJSGetClaimSet temLJSGetClaimSet = new LJSGetClaimSet();
		tempVData = tLLGrpBnfBL.getResult();
		tempMMap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
		tempLLBnfSet = (LLBnfSet) tempVData.getObjectByObjectName(
				"LLBnfSet", 0);
		tempLLBnfGatherSet = (LLBnfGatherSet) tempVData.getObjectByObjectName(
				"LLBnfGatherSet", 0);
		tempLJSGetSet = (LJSGetSet) tempVData.getObjectByObjectName(
				"LJSGetSet", 0);
		temLJSGetClaimSet = (LJSGetClaimSet) tempVData.getObjectByObjectName(
				"LJSGetClaimSet", 0);
		saveLLBnfSet.add(tempLLBnfSet);
//		mLLBnfGatherSet.add(tempLLBnfGatherSet);
//		mLJSGetSet.add(tempLJSGetSet);
//		mLJSGetClaimSet.add(temLJSGetClaimSet);
		mLLBnfSet.clear();
		mMap.add(tempMMap);

		return true;
	}

	private boolean prepareOutputData() {
		mMap.put(saveLLBnfSet, "DELETE&INSERT");
//		mMap.put(mLLBnfGatherSet, "DELETE&INSERT");
//		mMap.put(mLJSGetSet, "DELETE&INSERT");
//		mMap.put(mLJSGetClaimSet, "DELETE&INSERT");
		mResult.clear();
		mResult.add(mMap);
		return true;
	}

	public VData getResult() {
		return mResult;
	}

}
