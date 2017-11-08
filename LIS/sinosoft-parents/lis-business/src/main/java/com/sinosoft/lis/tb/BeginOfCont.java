package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import java.util.Hashtable;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;
/**
 * 功能：本类为初始化界面时生成模板用
 * 
 * @param 
 *            
 * @return 
 */
public class BeginOfCont {
private static Logger logger = Logger.getLogger(BeginOfCont.class);
	GlobalInput mGI = new GlobalInput();

	String mPrtNo = "";

	String mManageCom = "";

	String mInputTime = "";

	String mInputNo = "";

	String mOperator = "";

	private String theCurrentDate = PubFun.getCurrentDate();

	private String theCurrentTime = PubFun.getCurrentTime();

	private MMap map = new MMap();

	private VData mInputData = new VData();

	public BeginOfCont() {

	}
	/**
	 * 功能：本类为初始化界面时生成模板用
	 * 
	 * @param 印刷号、GlobalInput、InputTime
	 *            
	 * @return true||false
	 */
	public boolean submitData(String tPrtno, GlobalInput tGI, String tInputTime) {
		if (!getInputData(tPrtno, tGI, tInputTime)) {
			return false;
		}

		if (!dealdata()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}
		PubSubmit tPubSubmit = new PubSubmit();
		logger.debug("Start PubSubmit...");
		if (!tPubSubmit.submitData(mInputData, "")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ContBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			return false;
		}
		return true;
	}

	private boolean prepareOutputData() {
		mInputData.clear();
		mInputData.add(map);
		return true;
	}

	private boolean getInputData(String tPrtNo, GlobalInput tGI,
			String tInputTime) {
		mPrtNo = tPrtNo;
		mGI = tGI;
		mManageCom = mGI.ManageCom;
		mOperator = mGI.Operator;
		mInputTime = tInputTime;

		if (mInputTime.equals("0")) {
			mInputNo = "1";
		}
		if (mInputTime.equals("1")) {
			mInputNo = "2";
		}
		if (mInputTime.equals("2")) {
			mInputNo = "3";
		}
		if (mPrtNo == null || mPrtNo.equals("")) {
			logger.debug("传入印刷号失败！");
			return false;
		}

		return true;
	}

	private boolean dealdata() {

		try {/**三码录入时直接将二码的数据复制过去，然后将错误信息表中记录的需确认的字段置空*/
			if (mInputNo.equals("3")) {
				Hashtable tHashtable = new Hashtable();
				LBPODataDetailErrorDB tLBPODataDetailErrorDB = new LBPODataDetailErrorDB();
				LBPODataDetailErrorSet tLBPODataDetailErrorSet = new LBPODataDetailErrorSet();
				String tSql = "select * from lbpodatadetailerror where bussno ='"+"?mPrtNo?"+"' ";
				SQLwithBindVariables sqlbv = new SQLwithBindVariables();
				sqlbv.sql(tSql);
				sqlbv.put("mPrtNo",mPrtNo);
				tLBPODataDetailErrorSet = tLBPODataDetailErrorDB
						.executeQuery(sqlbv);
				/**查询字典表，将需要置空的字段及错误信息放入Hashtable中*/
				for (int i = 1; i <= tLBPODataDetailErrorSet.size(); i++) {
					LBPODataDetailErrorSchema tLBPODataDetailErrorSchema = new LBPODataDetailErrorSchema();
					tLBPODataDetailErrorSchema = tLBPODataDetailErrorSet.get(i);
					String tKey=tLBPODataDetailErrorSchema.getTableName()+"."+tLBPODataDetailErrorSchema.getErrorTag()+"."+tLBPODataDetailErrorSchema.getOtherNo();
					tHashtable.put(tKey,
							tLBPODataDetailErrorSchema.getOtherNo());
				}
				LBPOContDB tLBPOContDB = new LBPOContDB();
				LBPOContSet tLBPOContSet = new LBPOContSet();
				LBPOAppntDB tLBPOAppntDB = new LBPOAppntDB();
				LBPOAppntSet tLBPOAppntSet = new LBPOAppntSet();
				LBPOInsuredDB tLBPOInsuredDB = new LBPOInsuredDB();
				LBPOInsuredSet tLBPOInsuredSet = new LBPOInsuredSet();
				LBPOCustomerImpartDB tLBPOCustomerImpartDB = new LBPOCustomerImpartDB();
				LBPOCustomerImpartSet tLBPOCustomerImpartSet = new LBPOCustomerImpartSet();
				LBPOPolDB tLBPOPolDB = new LBPOPolDB();
				LBPOPolSet tLBPOPolSet = new LBPOPolSet();
				LBPOBnfDB tLBPOBnfDB = new LBPOBnfDB();
				LBPOBnfSet tLBPOBnfSet = new LBPOBnfSet();
				tLBPOContDB.setContNo(mPrtNo);
				tLBPOContDB.setInputNo("2");
				tLBPOContSet = tLBPOContDB.query();
				/**将错误信息表中记录的相应字段置空*/
				for (int i = 1; i <= tLBPOContSet.size(); i++) {
					for (int a = 0; a < tLBPOContSet.get(i).getFieldCount(); a++) {
						String tFieldName = tLBPOContSet.get(i).getFieldName(a);
						//String tErrCode = (String)tHashtable2.get(tFieldName);
						//String tErrMsg = (String)tHashtable.get(tFieldName);
						String tClassName = tLBPOContSet.get(i).getClass().getName();
						String tCheckName = tClassName .substring(tClassName.lastIndexOf(".") + 1);
						tCheckName = tCheckName.substring(0, tCheckName.lastIndexOf("Schema"));
						String tFieldKey =  tCheckName + "." + tFieldName+"."+tLBPOContSet.get(i).getFillNo() ;
						//String tFieldName = tLBPOContSet.get(i).getFieldName(a);
						if (tHashtable.containsKey(tFieldKey)) {
							if (tLBPOContSet.get(i).getFillNo().equals(
									(String)tHashtable.get(tFieldKey)))
								tLBPOContSet.get(i)
										.setV(
												tLBPOContSet.get(i)
														.getFieldName(a), "");
						}
					}
					tLBPOContSet.get(i).setInputNo("3");
				}
				tLBPOAppntDB.setContNo(mPrtNo);
				tLBPOAppntDB.setInputNo("2");
				tLBPOAppntSet = tLBPOAppntDB.query();
				for (int i = 1; i <= tLBPOAppntSet.size(); i++) {
					for (int a = 0; a < tLBPOAppntSet.get(i).getFieldCount(); a++) {
						String tFieldName = tLBPOAppntSet.get(i).getFieldName(a);
						//String tErrCode = (String)tHashtable2.get(tFieldName);
						//String tErrMsg = (String)tHashtable.get(tFieldName);
						String tClassName = tLBPOAppntSet.get(i).getClass().getName();
						String tCheckName = tClassName .substring(tClassName.lastIndexOf(".") + 1);
						tCheckName = tCheckName.substring(0, tCheckName.lastIndexOf("Schema"));
						String tFieldKey =  tCheckName + "." + tFieldName+"."+tLBPOAppntSet.get(i).getFillNo() ;
						//String tFieldName = tLBPOContSet.get(i).getFieldName(a);
						if (tHashtable.containsKey(tFieldKey)) {
							if (tLBPOAppntSet.get(i).getFillNo().equals(
									(String)tHashtable.get(tFieldKey)))
								tLBPOAppntSet.get(i).setV(
										tLBPOAppntSet.get(i).getFieldName(a),
										"");
						}
					}
					tLBPOAppntSet.get(i).setInputNo("3");
				}
				tLBPOInsuredDB.setContNo(mPrtNo);
				tLBPOInsuredDB.setInputNo("2");
				tLBPOInsuredSet = tLBPOInsuredDB.query();
				for (int i = 1; i <= tLBPOInsuredSet.size(); i++) {
					for (int a = 0; a < tLBPOInsuredSet.get(i).getFieldCount(); a++) {
						String tFieldName = tLBPOInsuredSet.get(i).getFieldName(a);
						//String tErrCode = (String)tHashtable2.get(tFieldName);
						//String tErrMsg = (String)tHashtable.get(tFieldName);
						String tClassName = tLBPOInsuredSet.get(i).getClass().getName();
						String tCheckName = tClassName .substring(tClassName.lastIndexOf(".") + 1);
						tCheckName = tCheckName.substring(0, tCheckName.lastIndexOf("Schema"));
						String tFieldKey =  tCheckName + "." + tFieldName+"."+tLBPOInsuredSet.get(i).getFillNo() ;
						//String tFieldName = tLBPOContSet.get(i).getFieldName(a);
						if (tHashtable.containsKey(tFieldKey)) {
							if (tLBPOInsuredSet.get(i).getSequenceNo().equals(
									(String)tHashtable.get(tFieldKey)))
								tLBPOInsuredSet.get(i).setV(
										tLBPOInsuredSet.get(i).getFieldName(a),
										"");
						}
					}
					tLBPOInsuredSet.get(i).setInputNo("3");
				}
				tLBPOCustomerImpartDB.setContNo(mPrtNo);
				tLBPOCustomerImpartDB.setInputNo("2");
				tLBPOCustomerImpartSet = tLBPOCustomerImpartDB.query();
				for (int i = 1; i <= tLBPOCustomerImpartSet.size(); i++) {
					for (int a = 0; a < tLBPOCustomerImpartSet.get(i)
							.getFieldCount(); a++) {
						String tFieldName = tLBPOCustomerImpartSet.get(i).getFieldName(a);
						//String tErrCode = (String)tHashtable2.get(tFieldName);
						//String tErrMsg = (String)tHashtable.get(tFieldName);
						String tClassName = tLBPOCustomerImpartSet.get(i).getClass().getName();
						String tCheckName = tClassName .substring(tClassName.lastIndexOf(".") + 1);
						tCheckName = tCheckName.substring(0, tCheckName.lastIndexOf("Schema"));
						String tFieldKey =  tCheckName + "." + tFieldName+"."+tLBPOCustomerImpartSet.get(i).getFillNo() ;
						//String tFieldName = tLBPOContSet.get(i).getFieldName(a);
						if (tHashtable.containsKey(tFieldKey)) {
							if (tLBPOCustomerImpartSet.get(i).getFillNo()
									.equals((String)tHashtable.get(tFieldKey)))
								tLBPOCustomerImpartSet.get(i).setV(
										tLBPOCustomerImpartSet.get(i)
												.getFieldName(a), "");
						}
					}
					tLBPOCustomerImpartSet.get(i).setInputNo("3");
				}
				tLBPOPolDB.setContNo(mPrtNo);
				tLBPOPolDB.setInputNo("2");
				tLBPOPolSet = tLBPOPolDB.query();
				for (int i = 1; i <= tLBPOPolSet.size(); i++) {
					for (int a = 0; a < tLBPOPolSet.get(i).getFieldCount(); a++) {
						String tFieldName = tLBPOPolSet.get(i).getFieldName(a);
						//String tErrCode = (String)tHashtable2.get(tFieldName);
						//String tErrMsg = (String)tHashtable.get(tFieldName);
						String tClassName = tLBPOPolSet.get(i).getClass().getName();
						String tCheckName = tClassName .substring(tClassName.lastIndexOf(".") + 1);
						tCheckName = tCheckName.substring(0, tCheckName.lastIndexOf("Schema"));
						String tFieldKey =  tCheckName + "." + tFieldName+"."+tLBPOPolSet.get(i).getFillNo() ;
						//String tFieldName = tLBPOContSet.get(i).getFieldName(a);
						if (tHashtable.containsKey(tFieldKey)) {
							if (tLBPOPolSet.get(i).getFillNo().equals(
									(String)tHashtable.get(tFieldKey)))
								tLBPOPolSet.get(i).setV(
										tLBPOPolSet.get(i).getFieldName(a), "");
						}
					}
					tLBPOPolSet.get(i).setInputNo("3");
				}
				tLBPOBnfDB.setContNo(mPrtNo);
				tLBPOBnfDB.setInputNo("2");
				tLBPOBnfSet = tLBPOBnfDB.query();
				for (int i = 1; i <= tLBPOBnfSet.size(); i++) {
					for (int a = 0; a < tLBPOBnfSet.get(i).getFieldCount(); a++) {
						String tFieldName = tLBPOBnfSet.get(i).getFieldName(a);
						//String tErrCode = (String)tHashtable2.get(tFieldName);
						//String tErrMsg = (String)tHashtable.get(tFieldName);
						String tClassName = tLBPOBnfSet.get(i).getClass().getName();
						String tCheckName = tClassName .substring(tClassName.lastIndexOf(".") + 1);
						tCheckName = tCheckName.substring(0, tCheckName.lastIndexOf("Schema"));
						String tFieldKey =  tCheckName + "." + tFieldName+"."+tLBPOBnfSet.get(i).getFillNo() ;
						//String tFieldName = tLBPOContSet.get(i).getFieldName(a);
						if (tHashtable.containsKey(tFieldKey)) {
							if (tLBPOBnfSet.get(i).getFillNo().equals((String)tHashtable.get(tFieldKey)))
								tLBPOBnfSet.get(i).setV(
										tLBPOBnfSet.get(i).getFieldName(a), "");
						}
					}
					tLBPOBnfSet.get(i).setInputNo("3");
				}
				map.put(tLBPOContSet, "INSERT");
				map.put(tLBPOAppntSet, "INSERT");
				map.put(tLBPOInsuredSet, "INSERT");
				map.put(tLBPOCustomerImpartSet, "INSERT");
				map.put(tLBPOPolSet, "INSERT");
				map.put(tLBPOBnfSet, "INSERT");
			} else {
				String tAppntNo = "";
				String tInsuredNo = "";
				String tSplitPrtNo = "";
				String tPolno1 = "";
				String tPolno2 = "";
				String tPolno3 = "";
				logger.debug("印刷号为：" + mPrtNo);
				tSplitPrtNo = mPrtNo.substring(0, 4);
				/**61为多主险  51为家庭单  21为中介  35、15为银代  16为简易  3110为新银代投保单*/
				if (!tSplitPrtNo.equals("8661") && !tSplitPrtNo.equals("8651")&& !tSplitPrtNo.equals("8621")
						&& !tSplitPrtNo.equals("8635")&& !tSplitPrtNo.equals("8616")&&!tSplitPrtNo.equals("8615")
						&&!tSplitPrtNo.equals("8611")&& !tSplitPrtNo.equals("8625")&& !tSplitPrtNo.equals("3110")) {
					logger.debug("解析印刷号失败！");
					return false;
				}

				LBPOContSchema tLBPOContSchema = new LBPOContSchema();
				tLBPOContSchema.setContNo(mPrtNo);
				tLBPOContSchema.setPrtNo(mPrtNo);
				tLBPOContSchema.setGrpContNo("00000000000000000000");
				tLBPOContSchema.setInputNo(mInputNo);
				tLBPOContSchema.setFillNo("1");
				tLBPOContSchema.setProposalContNo(mPrtNo);
				tLBPOContSchema.setManageCom(mManageCom);
				tLBPOContSchema.setOperator(mOperator);
				tLBPOContSchema.setMakeDate(theCurrentDate);
				tLBPOContSchema.setMakeTime(theCurrentTime);
				tLBPOContSchema.setModifyDate(theCurrentDate);
				tLBPOContSchema.setModifyTime(theCurrentTime);

				LBPOInsuredSet tLBPOInsuredSet = new LBPOInsuredSet();
				LBPOCustomerImpartSet tLBPOCustomerImpartSet = new LBPOCustomerImpartSet();
				LBPOCustomerImpartSet ttLBPOCustomerImpartSet = new LBPOCustomerImpartSet();
				LBPOPolSet tLBPOPolSet = new LBPOPolSet();
				LBPOBnfSet tLBPOBnfSet = new LBPOBnfSet();
//				LBPOPerInvestPlanSet tLBPOPerInvestPlanSet = new LBPOPerInvestPlanSet();
				int a = 0;
				int b = 0;
				/**
				 * 特殊保单特殊处理
				 * */
				/**家庭单为多被保人，并且投保人为第一被保人*/
				if(tSplitPrtNo.equals("8651")){
					for (int i = 1; i <= 3; i++) {
						LBPOInsuredSchema tLBPOInsuredSchema = new LBPOInsuredSchema();
						tLBPOInsuredSchema.setContNo(mPrtNo);
						tInsuredNo = PubFun1.CreateMaxNo("CUSTOMERNO", "SN");
						tLBPOInsuredSchema.setGrpContNo("00000000000000000000");
						tLBPOInsuredSchema.setPrtNo(mPrtNo);
						tLBPOInsuredSchema.setInputNo(mInputNo);
						tLBPOInsuredSchema.setFillNo(String.valueOf(i));
						tLBPOInsuredSchema.setSequenceNo(String.valueOf(i));
						tLBPOInsuredSchema.setInsuredNo(tInsuredNo);
						tLBPOInsuredSchema.setManageCom(mManageCom);
						tLBPOInsuredSchema.setOperator(mOperator);
						tLBPOInsuredSchema.setMakeDate(theCurrentDate);
						tLBPOInsuredSchema.setMakeTime(theCurrentTime);
						tLBPOInsuredSchema.setModifyDate(theCurrentDate);
						tLBPOInsuredSchema.setModifyTime(theCurrentTime);
						tLBPOInsuredSet.add(tLBPOInsuredSchema);
	
						for (int j = 1; j <= 4; j++) {
							LBPOPolSchema tLBPOPolSchema = new LBPOPolSchema();
							tPolno1 = PubFun1.CreateMaxNo("ProposalNo", mManageCom);
							tLBPOPolSchema.setPolNo(tPolno1);
							tLBPOPolSchema.setPrtNo(mPrtNo);
							tLBPOPolSchema.setInputNo(mInputNo);
							tLBPOPolSchema.setContNo(mPrtNo);
							tLBPOPolSchema.setGrpContNo("00000000000000000000");
							tLBPOPolSchema.setGrpPolNo("00000000000000000000");
							tLBPOPolSchema.setProposalNo(mPrtNo);
							tLBPOPolSchema.setInsuredNo(tInsuredNo);
							tLBPOPolSchema.setMainPolNo(tPolno1);
							tLBPOPolSchema.setFillNo(String.valueOf(a = a + 1));
							tLBPOPolSchema.setRiskSequence(String.valueOf(a));
							tLBPOPolSchema.setManageCom(mManageCom);
							tLBPOPolSchema.setOperator(mOperator);
							tLBPOPolSchema.setMakeDate(theCurrentDate);
							tLBPOPolSchema.setMakeTime(theCurrentTime);
							tLBPOPolSchema.setModifyDate(theCurrentDate);
							tLBPOPolSchema.setModifyTime(theCurrentTime);
							tLBPOPolSet.add(tLBPOPolSchema);
						}
	
						for (int k = 1; k <= 2; k++) {
							LBPOBnfSchema tLBPOBnfSchema = new LBPOBnfSchema();
							tLBPOBnfSchema.setContNo(mPrtNo);
							tLBPOBnfSchema.setInputNo(mInputNo);
							tLBPOBnfSchema.setFillNo(String.valueOf(b = b + 1));
							tLBPOBnfSchema.setInsuredNo(tInsuredNo);
							tLBPOBnfSchema.setPolNo("0");
							tLBPOBnfSchema.setOperator(mOperator);
							tLBPOBnfSchema.setMakeDate(theCurrentDate);
							tLBPOBnfSchema.setMakeTime(theCurrentTime);
							tLBPOBnfSchema.setModifyDate(theCurrentDate);
							tLBPOBnfSchema.setModifyTime(theCurrentTime);
							tLBPOBnfSet.add(tLBPOBnfSchema);
						}
					}
				}else if(tSplitPrtNo.equals("8661")){
					/**多主险*/
					LBPOInsuredSchema tLBPOInsuredSchema = new LBPOInsuredSchema();
					tLBPOInsuredSchema.setContNo(mPrtNo);
					tInsuredNo = PubFun1.CreateMaxNo("CUSTOMERNO", "SN");
					tLBPOInsuredSchema.setGrpContNo("00000000000000000000");
					tLBPOInsuredSchema.setPrtNo(mPrtNo);
					tLBPOInsuredSchema.setInputNo(mInputNo);
					tLBPOInsuredSchema.setFillNo("1");
					tLBPOInsuredSchema.setSequenceNo("1");
					tLBPOInsuredSchema.setInsuredNo(tInsuredNo);
					tLBPOInsuredSchema.setManageCom(mManageCom);
					tLBPOInsuredSchema.setOperator(mOperator);
					tLBPOInsuredSchema.setMakeDate(theCurrentDate);
					tLBPOInsuredSchema.setMakeTime(theCurrentTime);
					tLBPOInsuredSchema.setModifyDate(theCurrentDate);
					tLBPOInsuredSchema.setModifyTime(theCurrentTime);
					tLBPOInsuredSet.add(tLBPOInsuredSchema);
					
					for (int j = 1; j <= 3; j++) {
						/**多主险时适用主险附加险公用一个mainPolNo、RiskSequence*/
						LBPOPolSchema tLBPOPolSchema = new LBPOPolSchema();
						tPolno1 = PubFun1.CreateMaxNo("ProposalNo", mManageCom);
						tLBPOPolSchema.setPolNo(tPolno1);
						tLBPOPolSchema.setPrtNo(mPrtNo);
						tLBPOPolSchema.setInputNo(mInputNo);
						tLBPOPolSchema.setContNo(mPrtNo);
						tLBPOPolSchema.setGrpContNo("00000000000000000000");
						tLBPOPolSchema.setGrpPolNo("00000000000000000000");
						tLBPOPolSchema.setProposalNo(mPrtNo);
						tLBPOPolSchema.setInsuredNo(tInsuredNo);
						tLBPOPolSchema.setMainPolNo(tPolno1);
						tLBPOPolSchema.setFillNo(String.valueOf(a = a + 1));
						tLBPOPolSchema.setRiskSequence(String.valueOf(j));
						tLBPOPolSchema.setManageCom(mManageCom);
						tLBPOPolSchema.setOperator(mOperator);
						tLBPOPolSchema.setMakeDate(theCurrentDate);
						tLBPOPolSchema.setMakeTime(theCurrentTime);
						tLBPOPolSchema.setModifyDate(theCurrentDate);
						tLBPOPolSchema.setModifyTime(theCurrentTime);
						tLBPOPolSet.add(tLBPOPolSchema);
						
						for(int k=1;k<=3;k++){
							LBPOPolSchema ttLBPOPolSchema = new LBPOPolSchema();
							tPolno2 = PubFun1.CreateMaxNo("ProposalNo", mManageCom);
							ttLBPOPolSchema.setPolNo(tPolno2);
							ttLBPOPolSchema.setPrtNo(mPrtNo);
							ttLBPOPolSchema.setInputNo(mInputNo);
							ttLBPOPolSchema.setContNo(mPrtNo);
							ttLBPOPolSchema.setGrpContNo("00000000000000000000");
							ttLBPOPolSchema.setGrpPolNo("00000000000000000000");
							ttLBPOPolSchema.setProposalNo(mPrtNo);
							ttLBPOPolSchema.setInsuredNo(tInsuredNo);
							ttLBPOPolSchema.setMainPolNo(tPolno1);
							ttLBPOPolSchema.setRiskSequence(String.valueOf(j));
							ttLBPOPolSchema.setFillNo(String.valueOf(a = a + 1));
							ttLBPOPolSchema.setManageCom(mManageCom);
							ttLBPOPolSchema.setOperator(mOperator);
							ttLBPOPolSchema.setMakeDate(theCurrentDate);
							ttLBPOPolSchema.setMakeTime(theCurrentTime);
							ttLBPOPolSchema.setModifyDate(theCurrentDate);
							ttLBPOPolSchema.setModifyTime(theCurrentTime);
							tLBPOPolSet.add(ttLBPOPolSchema);
						}
						
						LBPOBnfSchema tLBPOBnfSchema = new LBPOBnfSchema();
						tLBPOBnfSchema.setContNo(mPrtNo);
						tLBPOBnfSchema.setInputNo(mInputNo);
						tLBPOBnfSchema.setFillNo(String.valueOf(b = b + 1));
						tLBPOBnfSchema.setInsuredNo(tInsuredNo);
						tLBPOBnfSchema.setPolNo(tPolno1);
						tLBPOBnfSchema.setOperator(mOperator);
						tLBPOBnfSchema.setMakeDate(theCurrentDate);
						tLBPOBnfSchema.setMakeTime(theCurrentTime);
						tLBPOBnfSchema.setModifyDate(theCurrentDate);
						tLBPOBnfSchema.setModifyTime(theCurrentTime);
						tLBPOBnfSet.add(tLBPOBnfSchema);
					}
					/**预留投资账户计划*/
					for(int i=1;i<=3;i++){
						LBPOPerInvestPlanSchema tLBPOPerInvestPlanSchema = new LBPOPerInvestPlanSchema();
						
					}
				}else if(tSplitPrtNo.equals("8621")||tSplitPrtNo.equals("8635")||tSplitPrtNo.equals("8615")
						||tSplitPrtNo.equals("8611")||tSplitPrtNo.equals("8625")||tSplitPrtNo.equals("3110")){
					/**中介、银代投保单*/
					LBPOInsuredSchema tLBPOInsuredSchema = new LBPOInsuredSchema();
					tLBPOInsuredSchema.setContNo(mPrtNo);
					tInsuredNo = PubFun1.CreateMaxNo("CUSTOMERNO", "SN");
					tLBPOInsuredSchema.setGrpContNo("00000000000000000000");
					tLBPOInsuredSchema.setPrtNo(mPrtNo);
					tLBPOInsuredSchema.setInputNo(mInputNo);
					tLBPOInsuredSchema.setFillNo("1");
					tLBPOInsuredSchema.setSequenceNo("1");
					tLBPOInsuredSchema.setInsuredNo(tInsuredNo);
					tLBPOInsuredSchema.setManageCom(mManageCom);
					tLBPOInsuredSchema.setOperator(mOperator);
					tLBPOInsuredSchema.setMakeDate(theCurrentDate);
					tLBPOInsuredSchema.setMakeTime(theCurrentTime);
					tLBPOInsuredSchema.setModifyDate(theCurrentDate);
					tLBPOInsuredSchema.setModifyTime(theCurrentTime);
					tLBPOInsuredSet.add(tLBPOInsuredSchema);
					
					/**为中介、银代投保单生成险种*/
					//
					tPolno1 = PubFun1.CreateMaxNo("ProposalNo", mManageCom);
					for(int i=1;i<=6;i++){
						LBPOPolSchema tLBPOPolSchema = new LBPOPolSchema();
						if(i==1){
							tLBPOPolSchema.setPolNo(tPolno1);
						}else{
							String tSubPolNo = PubFun1.CreateMaxNo("ProposalNo", mManageCom);
							tLBPOPolSchema.setPolNo(tSubPolNo);
						}
						tLBPOPolSchema.setPrtNo(mPrtNo);
						tLBPOPolSchema.setInputNo(mInputNo);
						tLBPOPolSchema.setContNo(mPrtNo);
						tLBPOPolSchema.setGrpContNo("00000000000000000000");
						tLBPOPolSchema.setGrpPolNo("00000000000000000000");
						tLBPOPolSchema.setProposalNo(mPrtNo);
						tLBPOPolSchema.setInsuredNo(tInsuredNo);
						tLBPOPolSchema.setMainPolNo(tPolno1);
						tLBPOPolSchema.setFillNo(String.valueOf(i));
						tLBPOPolSchema.setRiskSequence(String.valueOf(i));
						tLBPOPolSchema.setManageCom(mManageCom);
						tLBPOPolSchema.setOperator(mOperator);
						tLBPOPolSchema.setMakeDate(theCurrentDate);
						tLBPOPolSchema.setMakeTime(theCurrentTime);
						tLBPOPolSchema.setModifyDate(theCurrentDate);
						tLBPOPolSchema.setModifyTime(theCurrentTime);
						tLBPOPolSet.add(tLBPOPolSchema);
					}
					/**为中介、银代投保单生成受益人*/
					if(tSplitPrtNo.equals("8635")||tSplitPrtNo.equals("8615")||tSplitPrtNo.equals("8625")||tSplitPrtNo.equals("3110")){
						LBPOBnfSchema tLBPOBnfSchema = new LBPOBnfSchema();
						tLBPOBnfSchema.setContNo(mPrtNo);
						tLBPOBnfSchema.setInputNo(mInputNo);
						tLBPOBnfSchema.setFillNo("1");
						tLBPOBnfSchema.setBnfType("1");
						tLBPOBnfSchema.setInsuredNo(tInsuredNo);
						tLBPOBnfSchema.setPolNo("0");
						tLBPOBnfSchema.setOperator(mOperator);
						tLBPOBnfSchema.setMakeDate(theCurrentDate);
						tLBPOBnfSchema.setMakeTime(theCurrentTime);
						tLBPOBnfSchema.setModifyDate(theCurrentDate);
						tLBPOBnfSchema.setModifyTime(theCurrentTime);
						tLBPOBnfSet.add(tLBPOBnfSchema);
					}else{
						//个险投保单的受益人都为身故受益人
						for(int i=1;i<=4;i++){
							LBPOBnfSchema tLBPOBnfSchema = new LBPOBnfSchema();
							tLBPOBnfSchema.setContNo(mPrtNo);
							tLBPOBnfSchema.setInputNo(mInputNo);
							tLBPOBnfSchema.setFillNo(String.valueOf(i));
							tLBPOBnfSchema.setBnfType("1");
							tLBPOBnfSchema.setInsuredNo(tInsuredNo);
							tLBPOBnfSchema.setPolNo("0");
							tLBPOBnfSchema.setOperator(mOperator);
							tLBPOBnfSchema.setMakeDate(theCurrentDate);
							tLBPOBnfSchema.setMakeTime(theCurrentTime);
							tLBPOBnfSchema.setModifyDate(theCurrentDate);
							tLBPOBnfSchema.setModifyTime(theCurrentTime);
							tLBPOBnfSet.add(tLBPOBnfSchema);
						}
					}
					
				}else{
					/**简易投保单*/
					LBPOInsuredSchema tLBPOInsuredSchema = new LBPOInsuredSchema();
					tLBPOInsuredSchema.setContNo(mPrtNo);
					tInsuredNo = PubFun1.CreateMaxNo("CUSTOMERNO", "SN");
					tLBPOInsuredSchema.setGrpContNo("00000000000000000000");
					tLBPOInsuredSchema.setPrtNo(mPrtNo);
					tLBPOInsuredSchema.setInputNo(mInputNo);
					tLBPOInsuredSchema.setFillNo("1");
					tLBPOInsuredSchema.setSequenceNo("1");
					tLBPOInsuredSchema.setInsuredNo(tInsuredNo);
					tLBPOInsuredSchema.setManageCom(mManageCom);
					tLBPOInsuredSchema.setOperator(mOperator);
					tLBPOInsuredSchema.setMakeDate(theCurrentDate);
					tLBPOInsuredSchema.setMakeTime(theCurrentTime);
					tLBPOInsuredSchema.setModifyDate(theCurrentDate);
					tLBPOInsuredSchema.setModifyTime(theCurrentTime);
					tLBPOInsuredSet.add(tLBPOInsuredSchema);
					
					LBPOPolSchema tLBPOPolSchema = new LBPOPolSchema();
					tPolno1 = PubFun1.CreateMaxNo("ProposalNo", mManageCom);
					tLBPOPolSchema.setPolNo(tPolno1);
					tLBPOPolSchema.setPrtNo(mPrtNo);
					tLBPOPolSchema.setInputNo(mInputNo);
					tLBPOPolSchema.setContNo(mPrtNo);
					tLBPOPolSchema.setGrpContNo("00000000000000000000");
					tLBPOPolSchema.setGrpPolNo("00000000000000000000");
					tLBPOPolSchema.setProposalNo(mPrtNo);
					tLBPOPolSchema.setInsuredNo(tInsuredNo);
					tLBPOPolSchema.setMainPolNo(tPolno1);
					tLBPOPolSchema.setFillNo("1");
					tLBPOPolSchema.setRiskSequence("1");
					tLBPOPolSchema.setManageCom(mManageCom);
					tLBPOPolSchema.setOperator(mOperator);
					tLBPOPolSchema.setMakeDate(theCurrentDate);
					tLBPOPolSchema.setMakeTime(theCurrentTime);
					tLBPOPolSchema.setModifyDate(theCurrentDate);
					tLBPOPolSchema.setModifyTime(theCurrentTime);
					tLBPOPolSet.add(tLBPOPolSchema);
					
					for(int i=1;i<=2;i++){
						LBPOBnfSchema tLBPOBnfSchema = new LBPOBnfSchema();
						tLBPOBnfSchema.setContNo(mPrtNo);
						tLBPOBnfSchema.setInputNo(mInputNo);
						tLBPOBnfSchema.setFillNo(String.valueOf(i));
						tLBPOBnfSchema.setInsuredNo(tInsuredNo);
						tLBPOBnfSchema.setPolNo("0");
						tLBPOBnfSchema.setOperator(mOperator);
						tLBPOBnfSchema.setMakeDate(theCurrentDate);
						tLBPOBnfSchema.setMakeTime(theCurrentTime);
						tLBPOBnfSchema.setModifyDate(theCurrentDate);
						tLBPOBnfSchema.setModifyTime(theCurrentTime);
						tLBPOBnfSet.add(tLBPOBnfSchema);
					}
				}
				
				
				if(tSplitPrtNo.equals("8611") || tSplitPrtNo.equals("8621")){					
					LDImpartSchema tLDImpartSchema = new LDImpartSchema();
					LDImpartDB tLDImpartDB = new LDImpartDB();
					LDCode1Set tLDCode1Set = new LDCode1Set();
					LDCode1Set tLDCode1Set1 = new LDCode1Set();//业务员告知
					LDCode1DB tLDCode1DB = new LDCode1DB();
					//查询健康告知、财务告知
					String tImpartSql = "SELECT * FROM LDCode1 WHERE codetype='agentcont' and code in"
						+ " ('A05','A06') " 
						+ " order by code,code1";
					SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
					sqlbv1.sql(tImpartSql);
					tLDCode1Set = tLDCode1DB.executeQuery(sqlbv1);	
					for (int i = 1; i <= tLDCode1Set.size(); i++) {
						tLDImpartSchema = new LDImpartSchema();
						tLDImpartDB = new LDImpartDB();
						tLDImpartDB.setImpartVer(tLDCode1Set.get(i).getCode());
						tLDImpartDB.setImpartCode(tLDCode1Set.get(i).getCode1());
						if(!tLDImpartDB.getInfo())
						{
							CError.buildErr(this, "查询告知版本表失败！");
							return false;
						}
						tLDImpartSchema= tLDImpartDB.getSchema();
	
					    if(tLDImpartSchema!=null){
							LBPOCustomerImpartSchema tLBPOCustomerImpartSchema = new LBPOCustomerImpartSchema();
							tLBPOCustomerImpartSchema.setContNo(mPrtNo);
							tLBPOCustomerImpartSchema
									.setGrpContNo("00000000000000000000");
							tLBPOCustomerImpartSchema.setInputNo(mInputNo);
							tLBPOCustomerImpartSchema.setProposalContNo(mPrtNo);
							tLBPOCustomerImpartSchema.setPrtNo(mPrtNo);
							tLBPOCustomerImpartSchema.setFillNo(String.valueOf(i));
							tLBPOCustomerImpartSchema.setCustomerNoType("0");
							tLBPOCustomerImpartSchema.setImpartCode(tLDImpartSchema
									.getImpartCode());
							tLBPOCustomerImpartSchema.setImpartVer(tLDImpartSchema
									.getImpartVer());
							tLBPOCustomerImpartSchema.setImpartContent(tLDImpartSchema.getImpartContent());
							tLBPOCustomerImpartSchema.setImpartParamModle(tLDImpartSchema.getDSImpartParam());
							tLBPOCustomerImpartSchema.setOperator(mOperator);
							tLBPOCustomerImpartSchema.setMakeDate(theCurrentDate);
							tLBPOCustomerImpartSchema.setMakeTime(theCurrentTime);
							tLBPOCustomerImpartSchema.setModifyDate(theCurrentDate);
							tLBPOCustomerImpartSchema.setModifyTime(theCurrentTime);
							tLBPOCustomerImpartSet.add(tLBPOCustomerImpartSchema);
					    }
					}
					//业务员告知
					tImpartSql = "SELECT * FROM LDCode1 WHERE codetype='agentcont' and code='A03' " +
					" order by code,code1";
					SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
					sqlbv2.sql(tImpartSql);
					tLDCode1Set1 = tLDCode1DB.executeQuery(sqlbv2);
					for (int i = 1; i <= tLDCode1Set1.size(); i++) {
						tLDImpartSchema = new LDImpartSchema();
						tLDImpartDB = new LDImpartDB();
						tLDImpartDB.setImpartVer(tLDCode1Set1.get(i).getCode());
						tLDImpartDB.setImpartCode(tLDCode1Set1.get(i).getCode1());
						if(!tLDImpartDB.getInfo())
						{
							CError.buildErr(this, "查询告知版本表失败！");
							return false;
						}
						tLDImpartSchema= tLDImpartDB.getSchema();
	
					    if(tLDImpartSchema!=null){
							LBPOCustomerImpartSchema tLBPOCustomerImpartSchema = new LBPOCustomerImpartSchema();
							tLBPOCustomerImpartSchema.setContNo(mPrtNo);
							tLBPOCustomerImpartSchema
									.setGrpContNo("00000000000000000000");
							tLBPOCustomerImpartSchema.setInputNo(mInputNo);
							tLBPOCustomerImpartSchema.setProposalContNo(mPrtNo);
							tLBPOCustomerImpartSchema.setPrtNo(mPrtNo);
							tLBPOCustomerImpartSchema.setFillNo(String.valueOf(i+tLDCode1Set.size()));
							tLBPOCustomerImpartSchema.setCustomerNoType("0");
							tLBPOCustomerImpartSchema.setImpartCode(tLDImpartSchema
									.getImpartCode());
							tLBPOCustomerImpartSchema.setImpartVer(tLDImpartSchema
									.getImpartVer());
							tLBPOCustomerImpartSchema.setImpartContent(tLDImpartSchema.getImpartContent());
							tLBPOCustomerImpartSchema.setImpartParamModle(tLDImpartSchema.getDSImpartParam());
							tLBPOCustomerImpartSchema.setOperator(mOperator);
							tLBPOCustomerImpartSchema.setMakeDate(theCurrentDate);
							tLBPOCustomerImpartSchema.setMakeTime(theCurrentTime);
							tLBPOCustomerImpartSchema.setModifyDate(theCurrentDate);
							tLBPOCustomerImpartSchema.setModifyTime(theCurrentTime);
							tLBPOCustomerImpartSet.add(tLBPOCustomerImpartSchema);
					    }
					}
					
				}else if(tSplitPrtNo.equals("8616")){
					/**简易投保单的告知信息要特殊处理*/
					LDImpartSchema tLDImpartSchema = new LDImpartSchema();
					LDImpartDB tLDImpartDB = new LDImpartDB();
					LDCode1Set tLDCode1Set = new LDCode1Set();
					LDCode1DB tLDCode1DB = new LDCode1DB();
					String tImpartSql = "SELECT * FROM LDCode1 WHERE codetype='easycont'" +
					" order by code,code1";
					SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
					sqlbv3.sql(tImpartSql);
					tLDCode1Set = tLDCode1DB.executeQuery(sqlbv3);
					for (int i = 1; i <= tLDCode1Set.size(); i++) {
						tLDImpartSchema = new LDImpartSchema();
						tLDImpartDB = new LDImpartDB();
						tLDImpartDB.setImpartVer(tLDCode1Set.get(i).getCode());
						tLDImpartDB.setImpartCode(tLDCode1Set.get(i).getCode1());
						if(!tLDImpartDB.getInfo())
						{
							CError.buildErr(this, "查询告知版本表失败！");
							return false;
						}
						tLDImpartSchema= tLDImpartDB.getSchema();
	
					    if(tLDImpartSchema!=null){
							LBPOCustomerImpartSchema tLBPOCustomerImpartSchema = new LBPOCustomerImpartSchema();
							tLBPOCustomerImpartSchema.setContNo(mPrtNo);
							tLBPOCustomerImpartSchema
									.setGrpContNo("00000000000000000000");
							tLBPOCustomerImpartSchema.setInputNo(mInputNo);
							tLBPOCustomerImpartSchema.setProposalContNo(mPrtNo);
							tLBPOCustomerImpartSchema.setPrtNo(mPrtNo);
							tLBPOCustomerImpartSchema.setFillNo(String.valueOf(i));
							tLBPOCustomerImpartSchema.setCustomerNoType("0");
							tLBPOCustomerImpartSchema.setImpartCode(tLDImpartSchema
									.getImpartCode());
							tLBPOCustomerImpartSchema.setImpartVer(tLDImpartSchema
									.getImpartVer());
							tLBPOCustomerImpartSchema.setImpartContent(tLDImpartSchema.getImpartContent());
							tLBPOCustomerImpartSchema.setImpartParamModle(tLDImpartSchema.getDSImpartParam());
							tLBPOCustomerImpartSchema.setOperator(mOperator);
							tLBPOCustomerImpartSchema.setMakeDate(theCurrentDate);
							tLBPOCustomerImpartSchema.setMakeTime(theCurrentTime);
							tLBPOCustomerImpartSchema.setModifyDate(theCurrentDate);
							tLBPOCustomerImpartSchema.setModifyTime(theCurrentTime);
							tLBPOCustomerImpartSet.add(tLBPOCustomerImpartSchema);
					    }
					}					
					
				}else if(tSplitPrtNo.equals("8635")||tSplitPrtNo.equals("8615")||tSplitPrtNo.equals("8625")
						||tSplitPrtNo.equals("3110")){
					/**银代投保单的告知信息要特殊处理*/
					LDImpartSchema tLDImpartSchema = new LDImpartSchema();
					LDImpartDB tLDImpartDB = new LDImpartDB();
					LDCode1Set tLDCode1Set = new LDCode1Set();
					LDCode1DB tLDCode1DB = new LDCode1DB();
					String tImpartSql = "SELECT * FROM LDCode1 WHERE codetype='bankcont'" +
					" order by code,code1";
					SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
					sqlbv4.sql(tImpartSql);
					tLDCode1Set = tLDCode1DB.executeQuery(sqlbv4);
					for (int i = 1; i <= tLDCode1Set.size(); i++) {
						tLDImpartSchema = new LDImpartSchema();
						tLDImpartDB = new LDImpartDB();
						tLDImpartDB.setImpartVer(tLDCode1Set.get(i).getCode());
						tLDImpartDB.setImpartCode(tLDCode1Set.get(i).getCode1());
						if(!tLDImpartDB.getInfo())
						{
							CError.buildErr(this, "查询告知版本表失败！");
							return false;
						}
						tLDImpartSchema= tLDImpartDB.getSchema();
	
					    if(tLDImpartSchema!=null){
							LBPOCustomerImpartSchema tLBPOCustomerImpartSchema = new LBPOCustomerImpartSchema();
							tLBPOCustomerImpartSchema.setContNo(mPrtNo);
							tLBPOCustomerImpartSchema
									.setGrpContNo("00000000000000000000");
							tLBPOCustomerImpartSchema.setInputNo(mInputNo);
							tLBPOCustomerImpartSchema.setProposalContNo(mPrtNo);
							tLBPOCustomerImpartSchema.setPrtNo(mPrtNo);
							tLBPOCustomerImpartSchema.setFillNo(String.valueOf(i));
							tLBPOCustomerImpartSchema.setCustomerNoType("0");
							tLBPOCustomerImpartSchema.setImpartCode(tLDImpartSchema
									.getImpartCode());
							tLBPOCustomerImpartSchema.setImpartVer(tLDImpartSchema
									.getImpartVer());
							tLBPOCustomerImpartSchema.setImpartContent(tLDImpartSchema.getImpartContent());
							tLBPOCustomerImpartSchema.setImpartParamModle(tLDImpartSchema.getDSImpartParam());
							tLBPOCustomerImpartSchema.setOperator(mOperator);
							tLBPOCustomerImpartSchema.setMakeDate(theCurrentDate);
							tLBPOCustomerImpartSchema.setMakeTime(theCurrentTime);
							tLBPOCustomerImpartSchema.setModifyDate(theCurrentDate);
							tLBPOCustomerImpartSchema.setModifyTime(theCurrentTime);
							tLBPOCustomerImpartSet.add(tLBPOCustomerImpartSchema);
					    }
					}
				}else if(tSplitPrtNo.equals("8651")){
					/**家庭单的告知信息要特殊处理*/
					LDImpartSchema tLDImpartSchema = new LDImpartSchema();
					LDImpartDB tLDImpartDB = new LDImpartDB();
					LDCode1Set tLDCode1Set = new LDCode1Set();
					LDCode1Set tLDCode1Set1 = new LDCode1Set();//业务员告知
					LDCode1DB tLDCode1DB = new LDCode1DB();
					String tImpartSql = "SELECT * FROM LDCode1 WHERE codetype='homecont' and code<>'D04' " +
					" order by code,code1";
					SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
					sqlbv5.sql(tImpartSql);
					tLDCode1Set = tLDCode1DB.executeQuery(sqlbv5);
					for (int i = 1; i <= tLDCode1Set.size(); i++) {
						tLDImpartSchema = new LDImpartSchema();
						tLDImpartDB = new LDImpartDB();
						tLDImpartDB.setImpartVer(tLDCode1Set.get(i).getCode());
						tLDImpartDB.setImpartCode(tLDCode1Set.get(i).getCode1());
						if(!tLDImpartDB.getInfo())
						{
							CError.buildErr(this, "查询告知版本表失败！");
							return false;
						}
						tLDImpartSchema= tLDImpartDB.getSchema();
	
					    if(tLDImpartSchema!=null){
							LBPOCustomerImpartSchema tLBPOCustomerImpartSchema = new LBPOCustomerImpartSchema();
							tLBPOCustomerImpartSchema.setContNo(mPrtNo);
							tLBPOCustomerImpartSchema
									.setGrpContNo("00000000000000000000");
							tLBPOCustomerImpartSchema.setInputNo(mInputNo);
							tLBPOCustomerImpartSchema.setProposalContNo(mPrtNo);
							tLBPOCustomerImpartSchema.setPrtNo(mPrtNo);
							tLBPOCustomerImpartSchema.setFillNo(String.valueOf(i));
							tLBPOCustomerImpartSchema.setCustomerNoType("0");
							tLBPOCustomerImpartSchema.setImpartCode(tLDImpartSchema
									.getImpartCode());
							tLBPOCustomerImpartSchema.setImpartVer(tLDImpartSchema
									.getImpartVer());
							tLBPOCustomerImpartSchema.setImpartContent(tLDImpartSchema.getImpartContent());
							tLBPOCustomerImpartSchema.setImpartParamModle(tLDImpartSchema.getDSImpartParam());
							tLBPOCustomerImpartSchema.setOperator(mOperator);
							tLBPOCustomerImpartSchema.setMakeDate(theCurrentDate);
							tLBPOCustomerImpartSchema.setMakeTime(theCurrentTime);
							tLBPOCustomerImpartSchema.setModifyDate(theCurrentDate);
							tLBPOCustomerImpartSchema.setModifyTime(theCurrentTime);
							tLBPOCustomerImpartSet.add(tLBPOCustomerImpartSchema);
					    }
					}
					tImpartSql = "SELECT * FROM LDCode1 WHERE codetype='homecont' and code='D04' " +
					" order by code,code1";
					SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
					sqlbv6.sql(tImpartSql);
					tLDCode1Set1 = tLDCode1DB.executeQuery(sqlbv6);
					for (int i = 1; i <= tLDCode1Set1.size(); i++) {
						tLDImpartSchema = new LDImpartSchema();
						tLDImpartDB = new LDImpartDB();
						tLDImpartDB.setImpartVer(tLDCode1Set1.get(i).getCode());
						tLDImpartDB.setImpartCode(tLDCode1Set1.get(i).getCode1());
						if(!tLDImpartDB.getInfo())
						{
							CError.buildErr(this, "查询告知版本表失败！");
							return false;
						}
						tLDImpartSchema= tLDImpartDB.getSchema();
	
					    if(tLDImpartSchema!=null){
							LBPOCustomerImpartSchema tLBPOCustomerImpartSchema = new LBPOCustomerImpartSchema();
							tLBPOCustomerImpartSchema.setContNo(mPrtNo);
							tLBPOCustomerImpartSchema
									.setGrpContNo("00000000000000000000");
							tLBPOCustomerImpartSchema.setInputNo(mInputNo);
							tLBPOCustomerImpartSchema.setProposalContNo(mPrtNo);
							tLBPOCustomerImpartSchema.setPrtNo(mPrtNo);
							tLBPOCustomerImpartSchema.setFillNo(String.valueOf(i+tLDCode1Set.size()));
							tLBPOCustomerImpartSchema.setCustomerNoType("0");
							tLBPOCustomerImpartSchema.setImpartCode(tLDImpartSchema
									.getImpartCode());
							tLBPOCustomerImpartSchema.setImpartVer(tLDImpartSchema
									.getImpartVer());
							tLBPOCustomerImpartSchema.setImpartContent(tLDImpartSchema.getImpartContent());
							tLBPOCustomerImpartSchema.setImpartParamModle(tLDImpartSchema.getDSImpartParam());
							tLBPOCustomerImpartSchema.setOperator(mOperator);
							tLBPOCustomerImpartSchema.setMakeDate(theCurrentDate);
							tLBPOCustomerImpartSchema.setMakeTime(theCurrentTime);
							tLBPOCustomerImpartSchema.setModifyDate(theCurrentDate);
							tLBPOCustomerImpartSchema.setModifyTime(theCurrentTime);
							tLBPOCustomerImpartSet.add(tLBPOCustomerImpartSchema);
					    }
					}
				}else if(tSplitPrtNo.equals("8661")){
					/**多主险的告知信息要特殊处理*/
					LDImpartSchema tLDImpartSchema = new LDImpartSchema();
					LDImpartDB tLDImpartDB = new LDImpartDB();
					LDCode1Set tLDCode1Set = new LDCode1Set();
					LDCode1Set tLDCode1Set1 = new LDCode1Set();//业务员告知
					LDCode1DB tLDCode1DB = new LDCode1DB();
					String tImpartSql = "SELECT * FROM LDCode1 WHERE codetype='multicont' and code<>'A03' " +
					" order by code,code1";
					SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
					sqlbv7.sql(tImpartSql);
					tLDCode1Set = tLDCode1DB.executeQuery(sqlbv7);
					for (int i = 1; i <= tLDCode1Set.size(); i++) {
						tLDImpartSchema = new LDImpartSchema();
						tLDImpartDB = new LDImpartDB();
						tLDImpartDB.setImpartVer(tLDCode1Set.get(i).getCode());
						tLDImpartDB.setImpartCode(tLDCode1Set.get(i).getCode1());
						if(!tLDImpartDB.getInfo())
						{
							CError.buildErr(this, "查询告知版本表失败！");
							return false;
						}
						tLDImpartSchema= tLDImpartDB.getSchema();
	
					    if(tLDImpartSchema!=null){
							LBPOCustomerImpartSchema tLBPOCustomerImpartSchema = new LBPOCustomerImpartSchema();
							tLBPOCustomerImpartSchema.setContNo(mPrtNo);
							tLBPOCustomerImpartSchema
									.setGrpContNo("00000000000000000000");
							tLBPOCustomerImpartSchema.setInputNo(mInputNo);
							tLBPOCustomerImpartSchema.setProposalContNo(mPrtNo);
							tLBPOCustomerImpartSchema.setPrtNo(mPrtNo);
							tLBPOCustomerImpartSchema.setFillNo(String.valueOf(i));
							tLBPOCustomerImpartSchema.setCustomerNoType("0");
							tLBPOCustomerImpartSchema.setImpartCode(tLDImpartSchema
									.getImpartCode());
							tLBPOCustomerImpartSchema.setImpartVer(tLDImpartSchema
									.getImpartVer());
							tLBPOCustomerImpartSchema.setImpartContent(tLDImpartSchema.getImpartContent());
							tLBPOCustomerImpartSchema.setImpartParamModle(tLDImpartSchema.getDSImpartParam());
							tLBPOCustomerImpartSchema.setOperator(mOperator);
							tLBPOCustomerImpartSchema.setMakeDate(theCurrentDate);
							tLBPOCustomerImpartSchema.setMakeTime(theCurrentTime);
							tLBPOCustomerImpartSchema.setModifyDate(theCurrentDate);
							tLBPOCustomerImpartSchema.setModifyTime(theCurrentTime);
							tLBPOCustomerImpartSet.add(tLBPOCustomerImpartSchema);
					    }
					}
					tImpartSql = "SELECT * FROM LDCode1 WHERE codetype='multicont' and code='A03' " +
					" order by code,code1";
					SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
					sqlbv8.sql(tImpartSql);
					tLDCode1Set1 = tLDCode1DB.executeQuery(sqlbv8);
					for (int i = 1; i <= tLDCode1Set1.size(); i++) {
						tLDImpartSchema = new LDImpartSchema();
						tLDImpartDB = new LDImpartDB();
						tLDImpartDB.setImpartVer(tLDCode1Set1.get(i).getCode());
						tLDImpartDB.setImpartCode(tLDCode1Set1.get(i).getCode1());
						if(!tLDImpartDB.getInfo())
						{
							CError.buildErr(this, "查询告知版本表失败！");
							return false;
						}
						tLDImpartSchema= tLDImpartDB.getSchema();
	
					    if(tLDImpartSchema!=null){
							LBPOCustomerImpartSchema tLBPOCustomerImpartSchema = new LBPOCustomerImpartSchema();
							tLBPOCustomerImpartSchema.setContNo(mPrtNo);
							tLBPOCustomerImpartSchema
									.setGrpContNo("00000000000000000000");
							tLBPOCustomerImpartSchema.setInputNo(mInputNo);
							tLBPOCustomerImpartSchema.setProposalContNo(mPrtNo);
							tLBPOCustomerImpartSchema.setPrtNo(mPrtNo);
							tLBPOCustomerImpartSchema.setFillNo(String.valueOf(i+tLDCode1Set.size()));
							tLBPOCustomerImpartSchema.setCustomerNoType("0");
							tLBPOCustomerImpartSchema.setImpartCode(tLDImpartSchema
									.getImpartCode());
							tLBPOCustomerImpartSchema.setImpartVer(tLDImpartSchema
									.getImpartVer());
							tLBPOCustomerImpartSchema.setImpartContent(tLDImpartSchema.getImpartContent());
							tLBPOCustomerImpartSchema.setImpartParamModle(tLDImpartSchema.getDSImpartParam());
							tLBPOCustomerImpartSchema.setOperator(mOperator);
							tLBPOCustomerImpartSchema.setMakeDate(theCurrentDate);
							tLBPOCustomerImpartSchema.setMakeTime(theCurrentTime);
							tLBPOCustomerImpartSchema.setModifyDate(theCurrentDate);
							tLBPOCustomerImpartSchema.setModifyTime(theCurrentTime);
							tLBPOCustomerImpartSet.add(tLBPOCustomerImpartSchema);
					    }
					}
				}
				
				LBPOAppntSchema tLBPOAppntSchema = new LBPOAppntSchema();
				tLBPOAppntSchema.setGrpContNo("00000000000000000000");
				tLBPOAppntSchema.setContNo(mPrtNo);
				tLBPOAppntSchema.setInputNo(mInputNo);
				tLBPOAppntSchema.setFillNo("1");
				tLBPOAppntSchema.setPrtNo(mPrtNo);
				tAppntNo = PubFun1.CreateMaxNo("CUSTOMERNO", "SN");
				tLBPOAppntSchema.setAppntNo(tAppntNo);
				for(int i=1;i<=tLBPOInsuredSet.size();i++){
					tLBPOInsuredSet.get(i).setAppntNo(tAppntNo);
				}
				tLBPOAppntSchema.setManageCom(mManageCom);
				tLBPOAppntSchema.setOperator(mOperator);
				tLBPOAppntSchema.setMakeDate(theCurrentDate);
				tLBPOAppntSchema.setMakeTime(theCurrentTime);
				tLBPOAppntSchema.setModifyDate(theCurrentDate);
				tLBPOAppntSchema.setModifyTime(theCurrentTime);

				map.put(tLBPOContSchema, "INSERT");
				map.put(tLBPOAppntSchema, "INSERT");
				map.put(tLBPOInsuredSet, "INSERT");
				map.put(tLBPOPolSet, "INSERT");
				map.put(tLBPOBnfSet, "INSERT");
				map.put(tLBPOCustomerImpartSet, "INSERT");
				map.put(ttLBPOCustomerImpartSet, "INSERT");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}		
		return true;
	}

	
	public static void main(String args[]) {
		String theCurrentDate1 = PubFun.getCurrentDate();
		String theCurrentTime1 = PubFun.getCurrentTime();
		BeginOfCont tBeginOfCont = new BeginOfCont();
		LBPOCustomerImpartSet tLBPOCustomerImpartSet = new LBPOCustomerImpartSet();
		LDImpartSet tLDImpartSet = new LDImpartSet();
		LDImpartDB tLDImpartDB = new LDImpartDB();
		String tImpartSql = "SELECT * FROM LDImpart WHERE ImpartVer = '101' order by impartver,ImpartCode";
		SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
		sqlbv9.sql(tImpartSql);
		tLDImpartSet = tLDImpartDB.executeQuery(sqlbv9);
		for (int i = 1; i <= 27; i++) {
			LBPOCustomerImpartSchema tLBPOCustomerImpartSchema = new LBPOCustomerImpartSchema();
			tLBPOCustomerImpartSchema.setContNo("86519000000002");
			tLBPOCustomerImpartSchema.setGrpContNo("00000000000000000000");
			tLBPOCustomerImpartSchema.setInputNo(1);
			tLBPOCustomerImpartSchema.setProposalContNo("86519000000002");
			tLBPOCustomerImpartSchema.setPrtNo("86519000000002");
			tLBPOCustomerImpartSchema.setFillNo(String.valueOf(i));
			tLBPOCustomerImpartSchema.setImpartCode(tLDImpartSet.get(i)
					.getImpartCode());
			tLBPOCustomerImpartSchema.setImpartVer(tLDImpartSet.get(i)
					.getImpartVer());
			tLBPOCustomerImpartSchema.setImpartContent(tLDImpartSet.get(i)
					.getImpartContent());
			tLBPOCustomerImpartSchema.setOperator("001");
			tLBPOCustomerImpartSchema.setMakeDate(theCurrentDate1);
			tLBPOCustomerImpartSchema.setMakeTime(theCurrentTime1);
			tLBPOCustomerImpartSchema.setModifyDate(theCurrentDate1);
			tLBPOCustomerImpartSchema.setModifyTime(theCurrentTime1);
			tLBPOCustomerImpartSet.add(tLBPOCustomerImpartSchema);

		}

		LBPOCustomerImpartSet ttLBPOCustomerImpartSet = new LBPOCustomerImpartSet();
		LDImpartSet ttLDImpartSet = new LDImpartSet();
		LDImpartDB ttLDImpartDB = new LDImpartDB();
		String ttImpartSql = "SELECT * FROM LDImpart WHERE ImpartVer = '103' order by impartver,ImpartCode";
		SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
		sqlbv10.sql(ttImpartSql);
		ttLDImpartSet = ttLDImpartDB.executeQuery(sqlbv10);
		for (int i = 1; i <= 7; i++) {
			LBPOCustomerImpartSchema tLBPOCustomerImpartSchema = new LBPOCustomerImpartSchema();
			tLBPOCustomerImpartSchema.setContNo("86519000000002");
			tLBPOCustomerImpartSchema.setGrpContNo("00000000000000000000");
			tLBPOCustomerImpartSchema.setInputNo(1);
			tLBPOCustomerImpartSchema.setProposalContNo("86519000000002");
			tLBPOCustomerImpartSchema.setPrtNo("86519000000002");
			tLBPOCustomerImpartSchema.setFillNo(String.valueOf(i + 27));
			tLBPOCustomerImpartSchema.setImpartCode(ttLDImpartSet.get(i)
					.getImpartCode());
			tLBPOCustomerImpartSchema.setImpartVer(ttLDImpartSet.get(i)
					.getImpartVer());
			tLBPOCustomerImpartSchema.setImpartContent(ttLDImpartSet.get(i)
					.getImpartContent());
			tLBPOCustomerImpartSchema.setOperator("001");
			tLBPOCustomerImpartSchema.setMakeDate(theCurrentDate1);
			tLBPOCustomerImpartSchema.setMakeTime(theCurrentTime1);
			tLBPOCustomerImpartSchema.setModifyDate(theCurrentDate1);
			tLBPOCustomerImpartSchema.setModifyTime(theCurrentTime1);
			ttLBPOCustomerImpartSet.add(tLBPOCustomerImpartSchema);
		}
		MMap map1 = new MMap();
		map1.put(tLBPOCustomerImpartSet, "INSERT");
		map1.put(ttLBPOCustomerImpartSet, "INSERT");
		VData mInputData1 = new VData();
		mInputData1.add(map1);
		PubSubmit tPubSubmit = new PubSubmit();
		logger.debug("Start PubSubmit...");
		if (!tPubSubmit.submitData(mInputData1, "")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ContBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
		}
	}
}
