package com.sinosoft.lis.claim;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LLBnfGatherDB;
import com.sinosoft.lis.db.LLClaimDetailDB;
import com.sinosoft.lis.f1print.PrintService;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LLBnfGatherSchema;
import com.sinosoft.lis.schema.LLClaimUWMainSchema;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LLBnfGatherSet;
import com.sinosoft.lis.vschema.LLClaimDetailSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 单证打印：批单-理赔给付批注 -- PCT002,PdLpjfpzGrC00010.vts
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author 续涛，2005.07.28--2005.07.28
 * @version 1.0
 */
public class LLPRTPostilPerBL implements PrintService {
private static Logger logger = Logger.getLogger(LLPRTPostilPerBL.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private MMap mMMap = new MMap();

	private TransferData mTransferData = new TransferData();
	private LLClaimPubFunBL mLLClaimPubFunBL = new LLClaimPubFunBL();
//	private String mClmNo = ""; // 赔案号
//	private String mCusNo = ""; // 客户号
	private String mPrtSeq = ""; // 流水号
//	private String mAccNo = ""; // 事件号
//	private String mGiveType = ""; // 赔案给付拒付结论
	private String mPrtType = ""; // 单证类型

	public LLPRTPostilPerBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */

	public boolean submitData(VData cInputData, String cOperate) {

		logger.debug("----------批单-理赔给付批注-----LLPRTPostilPerBL测试-----开始----------");

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}
//
//		if (!dealData()) {
//			return false;
//		}
		if (!getPrintData()) {
		return false;
	   }

		if (!prepareOutputData()) {
			return false;
		}

//		if (!pubSubmit()) {
//			return false;
//		}

		logger.debug("----------批单-理赔给付批注-----LLPRTPostilPerBL测试-----结束----------");
		return true;
	}

	/**
	 * 取传入参数信息
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		this.mInputData = cInputData;

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		// this.mClmNo = (String) mTransferData.getValueByName("ClmNo"); //赔案号
		// this.mCusNo = (String) mTransferData.getValueByName("CustNo");
		// //出险人客户号
		this.mPrtSeq = (String) mTransferData.getValueByName("PrtSeq");
		this.mPrtType = (String) mTransferData.getValueByName("PrtType");
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
//	private boolean dealData() {
//		ExeSQL tExeSQL = new ExeSQL();
//		SSRS tSSRS = new SSRS();
//		String mSQL = "";
//		String amontFee = "";// 总给付金额
//		String endcasedate = "";// 结案日期
//
//		// 赔案号
//		mSQL = "select OtherNo from LOPRTManager where PrtSeq='" + mPrtSeq
//				+ "'";
//		logger.debug("根据打印管理表的流水号" + mPrtSeq + "查询赔案号的sql=" + mSQL);
//		mClmNo = tExeSQL.getOneValue(mSQL);
//
//		// 客户号
//		mSQL = "select CustomerNo from LLcase where CaseNo='" + mClmNo + "'";
//		logger.debug("根据赔案号" + mPrtSeq + "查询出险人客户号的sql=" + mSQL);
//		tSSRS = tExeSQL.execSQL(mSQL);
//		mCusNo = tSSRS.GetText(1, 1);
//
//		// 事件号
//		mSQL = "select distinct caserelano from LLCaseRela where caseno='"
//				+ mClmNo + "'";
//		logger.debug("根据赔案号" + mClmNo + "查询事件号的sql=" + mSQL);
//		mAccNo = tExeSQL.getOneValue(mSQL);
//		
//
//		/**
//		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 定义打印
//		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//		 */
//		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
//		XmlExport tXmlExport = new XmlExport(); // 新建一个XmlExport的实例
//		tXmlExport.createDocument("PdLpjfpzGrC00010.vts", "");
//		LLPRTPubFunBL tLLPRTPubFunBL = new LLPRTPubFunBL();
//		// 理赔类型---------------------------------------------------------------
//		String ClaimType = tLLPRTPubFunBL.getSLLReportReason(mClmNo, mCusNo);
//
//		// 出险人姓名--------------------------------------------------------------
//		String nameSql = "select a.name from ldperson a where "
//				+ "a.customerno = '" + mCusNo + "'";
//		String tName = tExeSQL.getOneValue(nameSql);
//
//		// 申请人 姓名Name 根据赔案号（mClmNo）从立案申请登记(LLRegister)中查------------
//		LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();
//		tLLRegisterSchema = tLLPRTPubFunBL.getLLRegister(mClmNo);
//		String rgtname = "";
//		rgtname = tLLRegisterSchema.getRgtantName();
//
//		// 系统时间---------------------------------------------------------------
//		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
//				+ StrTool.getDay() + "日";
//
//		// 批单审核信息
//		LLClaimUWMainSchema tLLClaimUWMainSchema = new LLClaimUWMainSchema();
//		tLLClaimUWMainSchema = tLLPRTPubFunBL.getLLClaimUWMain(mClmNo);
//
//		//----------------------------------------------------------------------
//		tTextTag.add("IndemnityNo", mClmNo);// 赔案号
//		tTextTag.add("AccNo", mAccNo);// 事件号
//		tTextTag.add("IndemnityType", ClaimType);// 理赔类型
//		tTextTag.add("InsuredName", tName);// 出险人
//		tTextTag.add("LCCont.AppntName", rgtname);// 申请人
//		tTextTag.add("SysDate", tLLClaimUWMainSchema.getExamDate());// 系统时间
//		// 判断是否发生过预付以打印批注-----------------------------------------------
//		String pzSQL = "";
//		pzSQL = "select count(*) from llclaimdetail where clmno='" + mClmNo
//				+ "' and PrepayFlag='1'";
//		ExeSQL pzExeSQL = new ExeSQL();
//		String pzName = pzExeSQL.getOneValue(pzSQL);
//		String prePay = "";
//		if (Integer.parseInt(pzName) > 0) {
//			prePay = "此次实付保险金为已扣除预付保险金的金额。";
//			tXmlExport.addDisplayControl("display");// 控制批注是否显示
//		}
//		// 判断是否将身故保险金转换为即期年金进行给付
//		String pzchaSQL = "select GetMode from llregister where RgtNo='"
//				+ mClmNo + "'";
//		ExeSQL pzChaExeSQL = new ExeSQL();
//		String tGetMode = pzChaExeSQL.getOneValue(pzchaSQL);
//		String yearPay = "";
//		if (tGetMode.equals("2")) {
//			yearPay = "本次保险事故确定可以给付的身故保险金将按照保险合同之规定转换为即期年金进行给付。";
//			tXmlExport.addDisplayControl("display");
//			//tTextTag.add("PZ1",prePay+"本次保险事故确定可以给付的身故保险金将按照保险合同之规定转换为即期年金进行给付"
//			// );
//		}
//		tTextTag.add("PZ1", prePay + yearPay);
//
//		//总给付金额和结案日期------------------------------------------------------------
//		// --
//		String realPaySQL = "select RealPay,EndCaseDate from llclaim where clmNo='"
//				+ mClmNo + "'";
//		ExeSQL realPayExeSQL = new ExeSQL();
//		tSSRS = tExeSQL.execSQL(realPaySQL);
//		amontFee = tSSRS.GetText(1, 1);
//		endcasedate = tSSRS.GetText(1, 2);
//		logger.debug("赔案" + mClmNo + "的总给付金额是" + amontFee + ",结案日期是"
//				+ endcasedate);
//		double tZJFJE = Double.parseDouble(amontFee); // 为格式化小数点
//		String DXJE = PubFun.getChnMoney(tZJFJE);
//		amontFee = new DecimalFormat("0.00").format(tZJFJE);
//
//		// 合同号号、理赔金额、剩余有效保额信息 循环输出---------------------------------
//		String tSQL = "select givetype from llclaim where clmNo='" + mClmNo
//				+ "'";
//		mGiveType = tExeSQL.getOneValue(tSQL);
//
//		// 首先查出llbalance表中指定赔案号下的不同的保单号(合同号)的数量作为最外层循环
//
//		String contSQL = "";
//		contSQL = "select * from lccont where contno in "
//				+ "(select distinct contno from llbalance " + " where clmno='"
//				+ mClmNo + "') order by contno";
//		logger.debug(contSQL);
//		LCContDB tLCContDB = new LCContDB();
//		LCContSet tLCContSet = tLCContDB.executeQuery(contSQL);
//
//		ListTable tListTable = new ListTable();
//		tListTable.setName("LPJF");
//
//		String[] Title = new String[3];
//		Title[0] = "";
//		Title[1] = "";
//		Title[2] = "";
//
//		double sumPay = 0.00;
//
//		String tBnfSql = "select distinct bnfno from llbnfgather where clmno='"
//				+ mClmNo + "'";
//		SSRS tBnfSSRS = new SSRS();
//		ExeSQL tExeSql = new ExeSQL();
//		tBnfSSRS = tExeSql.execSQL(tBnfSql);
//		// 循环受益人信息
//		for (int i = 1; i <= tBnfSSRS.MaxRow; i++) {
//			String tBnfNo = tBnfSSRS.GetText(i, 1);
//			LLBnfSchema tLLBnfSchema = new LLBnfSchema();
//			LLBnfDB tLLBnfDB = new LLBnfDB();
//			LLBnfSet tLLBnfSet = new LLBnfSet();
//			tLLBnfDB.setClmNo(mClmNo);
//			tLLBnfDB.setBnfNo(tBnfNo);
//			if("PCT014".equals(mPrtType)){
//				//预付
//				tLLBnfDB.setBnfKind("B");
//			}else{
//				//非预付
//				tLLBnfDB.setBnfKind("A");
//			}
//			tLLBnfSet = tLLBnfDB.query();
//			if (tLLBnfSet.size() > 0) {
//				tLLBnfSchema = tLLBnfSet.get(1);
//			}
//			String[] bnfStr = new String[3];
//			bnfStr[0] = "受益人: " + tLLBnfSchema.getName();
//			String tSex = "";
//			if("0".equals(tLLBnfSchema.getSex())){
//				tSex = "男";
//			}
//			if("1".equals(tLLBnfSchema.getSex())){
//				tSex = "女";
//			}
//			
//			bnfStr[1] = "性别：" + tSex;
//			String tRelation = "";
//			if(tLLBnfSchema.getRelationToInsured()!=null){
//				String tRelationSql = "select codename from ldcode where codetype='relation' and code='"+tLLBnfSchema.getRelationToInsured()
//								+"'";
//				tRelation = tExeSql.getOneValue(tRelationSql);
//			}
//			bnfStr[2] = "与被保险人关系：" + tRelation;
//			tListTable.add(bnfStr);
//			String[] bnfStr2 = new String[3];
//			String tIDType = "";
//			if(tLLBnfSchema.getIDType()!=null){
//				String tIDTypeSql = "select * from ldcode where codetype='idtype' and code='"+tLLBnfSchema.getIDType()
//								+"'";
//				tIDType = tExeSql.getOneValue(tIDTypeSql);
//			}
//			bnfStr2[0] = "证件类型： " + tIDType;
//			bnfStr2[1] = "证件号码：" + tLLBnfSchema.getIDNo();
//			bnfStr2[2] = "";
//			//bnfkind='A' 过滤掉预付
//			String tPolSql = "select distinct polno from llbnf where clmno='"
//					+ mClmNo + "' and bnfno='" + tBnfNo +"'";
//			if("PCT014".equals(mPrtType)){
//				tPolSql+= " and bnfkind='B'";
//			}else{
//				tPolSql+= " and bnfkind='A'";
//			}
//			SSRS tPolSSRS = new SSRS();
//			tPolSSRS = tExeSql.execSQL(tPolSql);
//			for (int k = 1; k <= tPolSSRS.MaxRow; k++) {
//				String tPolNo = tPolSSRS.GetText(k, 1);
//				LCPolDB tLCPolDB = new LCPolDB();
//				tLCPolDB.setPolNo(tPolNo);
//				if (!tLCPolDB.getInfo()) {
//					CError.buildErr(this, "查找保险信息失败!");
//					return false;
//				}
//
//				String tRiskNameSql = "select riskname from lmrisk where riskcode ='"
//						+ tLCPolDB.getRiskCode() + "'";
//				String tRiskName = tExeSql.getOneValue(tRiskNameSql);
//				String[] polStr = new String[3];
//				polStr[0] = "险种：" + tRiskName;
//				polStr[1] = "保单号：" + tLCPolDB.getPolNo();
//				polStr[2] = "保险金额：" + tLCPolDB.getAmnt();
//				tListTable.add(polStr);
//				String bxSQL = "";
//				bxSQL = "select * from LLClaimDetail where clmno='" + mClmNo
//						+ "'" + " and polno='" + tPolNo + "' and givetype='0'";
//				logger.debug("查出合同下的保单险种号为" + tPolNo + "保项数目的sql="
//						+ bxSQL);
//				LLClaimDetailDB bxLLClaimDetailDB = new LLClaimDetailDB();
//				LLClaimDetailSet bxLLClaimDetailSet = bxLLClaimDetailDB
//						.executeQuery(bxSQL);
//
//				// 内层循环保项的数目
//				for (int j = 1; j <= bxLLClaimDetailSet.size(); j++) {
//					//剩余有效保额----------------------------------------------------
//					String feeSQL = "";
//					feeSQL = "select * from lcget where polno='"
//							+ bxLLClaimDetailSet.get(j).getPolNo() + "'"
//							+ " and getdutycode='"
//							+ bxLLClaimDetailSet.get(j).getGetDutyCode() + "'"
//							+ " and dutycode='"
//							+ bxLLClaimDetailSet.get(j).getDutyCode() + "'";
//					logger.debug("查询剩余保额的sql=" + feeSQL);
//					LCGetDB tLCGetDB = new LCGetDB();
//					LCGetSet tLCGetSet = tLCGetDB.executeQuery(feeSQL);
//					double fee = 0;// 剩余有效保额
//
//					// 判断保项是否终止，如果终止 则 剩余有效保额==0
//					LLContStateSet tLLContStateSet = new LLContStateSet();
//					LLContStateDB tLLContStateDB = new LLContStateDB();
//					String strStateSql = "select * from llcontstate where 1=1 "
//							+ " and clmno = '" + mClmNo + "'"
//							+ " and polno = '"
//							+ bxLLClaimDetailSet.get(j).getPolNo() + "'";
//					tLLContStateSet.set(tLLContStateDB
//							.executeQuery(strStateSql));
//					if (tLLContStateSet.size() > 0) {
//						fee = 0;// 保项终止---剩余有效保额==0
//					} else {
//						fee = tLCGetSet.get(1).getStandMoney()
//								- tLCGetSet.get(1).getSumMoney();
////						if (mLLClaimPubFunBL.getLMRiskSort(bxLLClaimDetailSet
////								.get(j).getRiskCode(), "26")) {
////							System.out
////									.println("********---------mLLClaimPubFunBL.getLMRiskSort--------********");
////							fee = mLLClaimPubFunBL.getSumMoney26(mClmNo,
////									bxLLClaimDetailSet.get(j).getPolNo(),
////									bxLLClaimDetailSet.get(j).getDutyCode(),
////									bxLLClaimDetailSet.get(j).getGetDutyCode(),
////									bxLLClaimDetailSet.get(j).getGetDutyKind());
////						}
//						fee = (fee > 0) ? fee : 0;
//					}
//
//					// 根据llclaimdetail中的 给付责任类型和给付责任编码 从LMDutyGetClm中 查询给付责任名称 即
//					// 保项名称
//					String bxNameSQL = "select GetDutyName from lmdutygetclm where getdutycode='"
//							+ bxLLClaimDetailSet.get(j).getGetDutyCode()
//							+ "'"
//							+ " and getdutykind='"
//							+ bxLLClaimDetailSet.get(j).getGetDutyKind() + "'";
//					ExeSQL bxnameExeSQL = new ExeSQL();
//					String bxName = bxnameExeSQL.getOneValue(bxNameSQL);
//
//					double tRealPay = bxLLClaimDetailSet.get(j).getRealPay();
//
//					String[] stra = new String[3];
//					stra[0] = "给付项目：" + bxName;// 保项
//
//					if (tGetMode.equals("2")) {
//						tRealPay = 0.00;
//						fee = 0.00;
//					}
//					stra[1] = "理赔金额："
//							+ new DecimalFormat("0.00").format(tRealPay); // 赔付金额
//					stra[2] = "剩余有效保额：" + new DecimalFormat("0.00").format(fee); // lcget表中standmoney
//																					// -
//																					// summoney
//					tListTable.add(stra);// 将保项信息加入ListTable
//				}
//				
//				//红利结算 FeeOperationType = 'C05' 红利结算
//				String tHongliSql = "select nvl(sum(GetMoney),0) from llbnf where clmno='"+mClmNo+"' and polno='"+tPolNo+"'"
//									+" and bnfno='"+tBnfNo+"' and FeeOperationType = 'C05'";
//				if("PCT014".equals(mPrtType)){
//					tHongliSql+= " and bnfkind='B'";
//				}else{
//					tHongliSql+= " and bnfkind='A'";
//				}
//				ExeSQL tHongLiExeSQL = new ExeSQL();
//				String tHongli = tHongLiExeSQL.getOneValue(tHongliSql);
//				
//				//保单欠款结算
//				String tBdqkjsSql = "select nvl(sum(pay),0) from llbalance a where a.feeoperationtype ='C02'"
//								+" and clmno='"+mClmNo+"' and polno='"+tPolNo+"'";
//				ExeSQL tBdqkjsExeSQL = new ExeSQL();
//				String tBdqkjs = tBdqkjsExeSQL.getOneValue(tBdqkjsSql);
//				
//				//保险费结算
//				String tBxfjsSql = "select nvl(sum(pay),0) from llbalance a where a.feeoperationtype in('C01','C02','C09','D01','D05','D06')"
//									+" and clmno='"+mClmNo+"' and polno='"+tPolNo+"'";
//				ExeSQL tBxfjsExeSQL = new ExeSQL();
//				String tBxfjs = tBxfjsExeSQL.getOneValue(tBxfjsSql);
//				
//				String tJS[] = new String[3];
//				tJS[0] = "红利结算："+tHongli;
//				tJS[1] = "保单欠款结算："+tBdqkjs;
//				tJS[2] = "保险费结算："+tBxfjs;
//				tListTable.add(tJS);
//				
//				//生存金结算
//				String tScjjsSql = "select nvl(sum(GetMoney),0) from llbnf a where FeeOperationType='C04'"
//									+" and clmno='"+mClmNo+"' and polno='"+tPolNo+"' and bnfno='"+tBnfNo+"'";
//				if("PCT014".equals(mPrtType)){
//					tScjjsSql+= " and bnfkind='B'";
//				}else{
//					tScjjsSql+= " and bnfkind='A'";
//				}
//				ExeSQL tScjjsExeSQL = new ExeSQL();
//				String tScjjs = tScjjsExeSQL.getOneValue(tScjjsSql);
//				
//				//合同处理结论
//				String tHtcljlSql = "select 1"
//									+" from lccontstate where polno='"+tPolNo+"' and StateType='Terminate' ";
//				ExeSQL tHtcljlExeSQL = new ExeSQL();
//				
//				String tHtcljl = tHtcljlExeSQL.getOneValue(tHtcljlSql);
//				
//				if(tHtcljl.equals(""))
//				{
//					tHtcljl="有效";
//				}
//				else
//				{
//					tHtcljl="终止";
//				}
//
//				
//				//险种给付金额
//				String tXzjfjeSql = "select sum(realpay) from llclaimdetail"
//									+" where polno='"+tPolNo+"' and clmno='"+mClmNo+"'";
//				ExeSQL tXzjfjeExeSQL = new ExeSQL();
//				String tXzjfje = tXzjfjeExeSQL.getOneValue(tXzjfjeSql);
//				
//				String tJS2[] = new String[3];
//				tJS2[0] = "生存金结算："+tScjjs;
//				tJS2[1] = "合同处理结论："+tHtcljl;
//				tJS2[2] = "险种给付金额："+tXzjfje;
//				tListTable.add(tJS2);
//			}
//			
//			//支付方式
////			String tZffsSql = "select CasePayMode from ";
////			ExeSQL tZffsExeSQL = new ExeSQL();
//			String tZffs = tLLBnfSchema.getCasePayMode();
//			if("1".equals(tZffs)){
//				tZffs = "现金";
//			}
//			if("2".equals(tZffs)){
//				tZffs = "现金支票";
//			}
//			if("3".equals(tZffs)){
//				tZffs = "转账支票";
//			}
//			if("4".equals(tZffs)){
//				tZffs = "银行转账";
//			}
//			if("5".equals(tZffs)){
//				tZffs = "内部转账";
//			}
//			
//			//合计受益金额
//			String tHjsyjeSql = "select otherno,GetMoney from LLBnfGather where bnfno = '"+tBnfNo
//								+"' and clmno='"+mClmNo+"'";
//			if("PCT014".equals(mPrtType)){
//				tHjsyjeSql+= " and bnfkind='B'";
//			}else{
//				tHjsyjeSql+= " and bnfkind='A'";
//			}
//			
//			ExeSQL tHjsyjeExeSQL = new ExeSQL();
//			tSSRS=tHjsyjeExeSQL.execSQL(tHjsyjeSql);
//			//String tHjsyje = tHjsyjeExeSQL.getOneValue(tHjsyjeSql);
//			
//			String tBnfStr3[] = new String[3];
//			tBnfStr3[0] = "支付方式："+tZffs;
//			tBnfStr3[1] = "实付号:"+tSSRS.GetText(1, 1);
//			tBnfStr3[2] = "合计受益金额："+tSSRS.GetText(1, 2);
//			tListTable.add(tBnfStr3);
//			//支付银行
//			String tBankCode = tLLBnfSchema.getBankCode();
//			String tBankSql = "select bankname from ldbank where bankcode='"+tBankCode+"'";
//			ExeSQL tBankExeSQL = new ExeSQL();
//			String tBankName = tBankExeSQL.getOneValue(tBankSql);
//			
//			if(tBankName==null)
//			{
//				tBankName="";
//			}
//			
//			//银行账户名
//			String tAccName = tLLBnfSchema.getAccName();
//			
//			if(tAccName==null)
//			{
//				tAccName="";
//			}
//			
//			//银行账号
//			String tBankAccNo = tLLBnfSchema.getBankAccNo();
//			
//			if(tBankAccNo==null)
//			{
//				tBankAccNo="";
//			}
//			
//			String tBankStr[] = new String[3];
//			tBankStr[0] = "支付银行："+tBankName;
//			tBankStr[1] = "银行账户名："+tAccName;
//			tBankStr[2] = "银行账号："+tBankAccNo;
//			
//			tListTable.add(tBankStr);
//		}
//
//		tTextTag.add("ZJFJE", amontFee);
//		tTextTag.add("DXJE", DXJE);
//		tTextTag.add("ZEndCaseDate", endcasedate);
//		tXmlExport.addListTable(tListTable, Title);
//		/**
//		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 加入单个字段的打印变量
//		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//		 */
//		if (tTextTag.size() > 0) {
//			tXmlExport.addTextTag(tTextTag);
//		}
//		/**
//		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 加入多行数据的打印变量
//		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//		 */
//		// xmlexport.addDisplayControl("Risk"); //模版上的主险附加险部分的控制标记
//		// tXmlExport.outputDocumentToFile("c:\\", "testHZM"); //输出xml文档到文件
//		mResult.clear();
//		mResult.addElement(tXmlExport);
//		mResult.add(mTransferData);
//		logger.debug("xmlexport=" + tXmlExport);
//
//		String updateStateSql = "update loprtmanager set stateflag='1' where otherno='"
//				+ mClmNo + "' and code='PCT010'";
//		logger.debug("更新打印管理表的sql:" + updateStateSql);
//		mMMap.put(updateStateSql, "UPDATE");
//
//		return true;
//	}

	
	private boolean getPrintData()
	{
		 String mClmNo = ""; // 赔案号
		 String mCusNo = ""; // 客户号
		 String mCusNoID= ""; // 客户号证件号


		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		String mSQL = "";
		String amontFee = "";// 总给付金额
		String endcasedate = "";// 结案日期

		// 赔案号
		mSQL = "select OtherNo from LOPRTManager where PrtSeq='" + mPrtSeq+ "'";
		logger.debug("根据打印管理表的流水号" + mPrtSeq + "查询赔案号的sql=" + mSQL);
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(mSQL);
		sqlbv.put("PrtSeq", mPrtSeq);
		mClmNo = tExeSQL.getOneValue(sqlbv);

		// 客户号
		mSQL = "select CustomerNo from LLcase where CaseNo='" + "?clmno?" + "'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(mSQL);
		sqlbv1.put("clmno", mClmNo);
		logger.debug("根据赔案号" + mPrtSeq + "查询出险人客户号的sql=" + mSQL);
		tSSRS = tExeSQL.execSQL(sqlbv1);
		mCusNo = tSSRS.GetText(1, 1);

		// 事件号
//		mSQL = "select distinct caserelano from LLCaseRela where caseno='"
//				+ mClmNo + "'";
//		logger.debug("根据赔案号" + mClmNo + "查询事件号的sql=" + mSQL);
//		mAccNo = tExeSQL.getOneValue(mSQL);


		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 定义打印
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		XmlExportNew tXmlExport = new XmlExportNew(); // 新建一个XmlExport的实例
//		tXmlExport.createDocument("PdLpjfpzGrC00010.vts", "");
		tXmlExport.createDocument("批单-理赔给付批注[个人]");
		LLPRTPubFunBL tLLPRTPubFunBL = new LLPRTPubFunBL();
		// 理赔类型---------------------------------------------------------------
//		String ClaimType = tLLPRTPubFunBL.getSLLReportReason(mClmNo, mCusNo);
		String ClaimType = tLLPRTPubFunBL.getClaimTypeReason(mClmNo, mCusNo);

		// 出险人姓名, 证件号--------------------------------------------------------------
		String nameSql = "select a.name,a.idno from ldperson a where a.customerno = '" + "?cusno?" + "'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(nameSql);
		sqlbv2.put("cusno", mCusNo);
		tSSRS = new SSRS();
		tSSRS=	tExeSQL.execSQL(sqlbv2);
		String tName = tSSRS.GetText(1, 1);
		mCusNoID = tSSRS.GetText(1, 2);

		// 申请人 姓名Name 根据赔案号（mClmNo）从立案申请登记(LLRegister)中查------------
//		LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();
//		tLLRegisterSchema = tLLPRTPubFunBL.getLLRegister(mClmNo);
//		String rgtname = "";
//		rgtname = tLLRegisterSchema.getRgtantName();

		// 系统时间---------------------------------------------------------------
//		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
//				+ StrTool.getDay() + "日";

		// 批单审核信息
		LLClaimUWMainSchema tLLClaimUWMainSchema = new LLClaimUWMainSchema();
		tLLClaimUWMainSchema = tLLPRTPubFunBL.getLLClaimUWMain(mClmNo);

		//----------------------------------------------------------------------
		tTextTag.add("IndemnityNo", mClmNo);// 赔案号
//		tTextTag.add("AccNo", mAccNo);// 事件号
		tTextTag.add("IndemnityType", ClaimType);// 理赔类型
		tTextTag.add("InsuredName", tName);// 出险人
		tTextTag.add("InsuredNo", mCusNoID);// 出险人证件号
//		tTextTag.add("LCCont.AppntName", rgtname);// 申请人
		tTextTag.add("SysDate", tLLClaimUWMainSchema.getExamDate());// 系统时间
		// 判断是否发生过预付以打印批注-----------------------------------------------
		String pzSQL = "";
		pzSQL = "select count(*) from llclaimdetail where clmno='" + "?clmno?"+ "' and PrepayFlag='1'";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(pzSQL);
		sqlbv3.put("clmno", mClmNo);
		ExeSQL pzExeSQL = new ExeSQL();
		String pzName = pzExeSQL.getOneValue(sqlbv3);
		String prePay = "";
		if (Integer.parseInt(pzName) > 0) {
			prePay = "此次实付保险金为已扣除预付保险金的金额。";
			tXmlExport.addDisplayControl("display");// 控制批注是否显示
		}
		// 判断是否将身故保险金转换为即期年金进行给付
		String pzchaSQL = "select GetMode from llregister where RgtNo='"+ "?clmno?" + "'";
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(pzchaSQL);
		sqlbv4.put("clmno", mClmNo);
		ExeSQL pzChaExeSQL = new ExeSQL();
		String tGetMode = pzChaExeSQL.getOneValue(sqlbv4);
		String yearPay = "";
		if (tGetMode.equals("2")) {
			yearPay = "本次保险事故确定可以给付的身故保险金将按照保险合同之规定转换为即期年金进行给付。";
			tXmlExport.addDisplayControl("display");
			//tTextTag.add("PZ1",prePay+"本次保险事故确定可以给付的身故保险金将按照保险合同之规定转换为即期年金进行给付"
			// );
		}
		tTextTag.add("PZ1", prePay + yearPay);

		//总给付金额和结案日期------------------------------------------------------------
		// --
		String realPaySQL = "select RealPay,EndCaseDate from llclaim where clmNo='"+ "?clmno?" + "'";
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql(realPaySQL);
		sqlbv5.put("clmno", mClmNo);
		tSSRS = tExeSQL.execSQL(sqlbv5);
		amontFee = tSSRS.GetText(1, 1);
		endcasedate = tSSRS.GetText(1, 2);
		logger.debug("赔案" + mClmNo + "的总给付金额是" + amontFee + ",结案日期是"+ endcasedate);
		double tZJFJE = Double.parseDouble(amontFee); // 为格式化小数点
		String DXJE = PubFun.getChnMoney(tZJFJE);
		amontFee = new DecimalFormat("0.00").format(tZJFJE);

		// 合同号号、理赔金额、剩余有效保额信息 循环输出---------------------------------
//		String tSQL = "select givetype from llclaim where clmNo='" + mClmNo
//				+ "'";
//		String mGiveType = tExeSQL.getOneValue(tSQL);

		// 首先查出llbalance表中指定赔案号下的不同的保单号(合同号)的数量作为最外层循环

//		String contSQL = "";
//		contSQL = "select * from lccont where contno in "
//				+ "(select distinct contno from llbalance " + " where clmno='"
//				+ mClmNo + "') order by contno";
//		logger.debug(contSQL);
//		LCContDB tLCContDB = new LCContDB();
//		LCContSet tLCContSet = tLCContDB.executeQuery(contSQL);

		ListTable tListTable = new ListTable();
		tListTable.setName("LPJF");
		
		ListTable tListTableD = new ListTable();
		tListTableD.setName("LPJFD");

		String[] Title = new String[3];
		Title[0] = "";
		Title[1] = "";
		Title[2] = "";
		
		String[] TitleD = new String[2];
		TitleD[0] = "";
		TitleD[1] = "";

//		double sumPay = 0.00;

		String tBnfSql = "select * from llbnfgather where clmno='"+ "?clmno?" + "' order by bnfno ";
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql(tBnfSql);
		sqlbv6.put("clmno", mClmNo);
		LLBnfGatherSet tLLBnfGatherSet = new LLBnfGatherSet();
		LLBnfGatherDB tLLBnfGatherDB = new LLBnfGatherDB();
		tLLBnfGatherSet = tLLBnfGatherDB.executeQuery(sqlbv6);
//		SSRS tBnfSSRS = new SSRS();
//		ExeSQL tExeSql = new ExeSQL();
//		tBnfSSRS = tExeSql.execSQL(tBnfSql);
		// 循环受益人信息
		for (int i = 1; i <=tLLBnfGatherSet.size(); i++) {
//			String tBnfNo = tBnfSSRS.GetText(i, 1);
//			LLBnfSchema tLLBnfSchema = new LLBnfSchema();
//			LLBnfDB tLLBnfDB = new LLBnfDB();
//			LLBnfSet tLLBnfSet = new LLBnfSet();
////			tLLBnfDB.setClmNo(mClmNo);
////			tLLBnfDB.setBnfNo(tBnfNo);
//			String tSql = "select * from llbnf where clmno='"+mClmNo+"' and bnfno ='"+tBnfNo+"' ";
//			if("PCT014".equals(mPrtType)){
//				//预付
////				tLLBnfDB.setBnfKind("B");
//				tSql=tSql+"and bnfkind='B' ";
//			}else{
//				//非预付
////				tLLBnfDB.setBnfKind("A");
//				tSql=tSql+"and bnfkind='A' ";
//			}
////			tLLBnfSet = tLLBnfDB.query();
//			tLLBnfSet = tLLBnfDB.executeQuery(tSql);
//			if (tLLBnfSet.size() > 0) {
//				tLLBnfSchema = tLLBnfSet.get(1);
//			}
			LLBnfGatherSchema tLLBnfGatherSchema = new LLBnfGatherSchema();
			ExeSQL lExeSQL = new ExeSQL();
			tLLBnfGatherSchema =tLLBnfGatherSet.get(i); 

//			String tSex = "";
//			if("0".equals(tLLBnfSchema.getSex())){
//				tSex = "男";
//			}
//			if("1".equals(tLLBnfSchema.getSex())){
//				tSex = "女";
//			}
//			
//			bnfStr[1] = "性别：" + tSex;
			String tRelation = "";
			if(tLLBnfGatherSchema.getRelationToInsured()!=null){
				String tRelationSql = "select codename from ldcode where codetype='relation' and code='"+
										"?code?"+"'";
				SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
				sqlbv7.sql(tRelationSql);
				sqlbv7.put("code", tLLBnfGatherSchema.getRelationToInsured());
				tRelation = lExeSQL.getOneValue(sqlbv7);
			}

//			String[] bnfStr2 = new String[3];
//			String tIDType = "";
//			if(tLLBnfSchema.getIDType()!=null){
//				String tIDTypeSql = "select * from ldcode where codetype='idtype' and code='"+tLLBnfSchema.getIDType()
//								+"'";
//				tIDType = tExeSql.getOneValue(tIDTypeSql);
//			}
//			bnfStr2[0] = "证件类型： " + tIDType;
//			bnfStr2[1] = "证件号码：" + tLLBnfSchema.getIDNo();
//			bnfStr2[2] = "";
			//bnfkind='A' 过滤掉预付
			String tPolSql = "select distinct polno from llbnf where clmno='"+ "?clmno?" + "' and bnfno='" + "?bnfno?" +"'";
			if("PCT014".equals(mPrtType)){
				tPolSql+= " and bnfkind='B'";
			}else{
				tPolSql+= " and bnfkind='A'";
			}
			SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
			sqlbv8.sql(tPolSql);
			sqlbv8.put("clmno", mClmNo);
			sqlbv8.put("bnfno", tLLBnfGatherSchema.getBnfNo());
			SSRS tPolSSRS = new SSRS();
			tPolSSRS = lExeSQL.execSQL(sqlbv8);
			for (int k = 1; k <= tPolSSRS.MaxRow; k++) {
				String[] bnfStr = new String[3];
				bnfStr[0] = "受益人: " + tLLBnfGatherSchema.getName();
				bnfStr[2] = "与被保险人关系：" + tRelation;
				
				String tPolNo = tPolSSRS.GetText(k, 1);
				LCPolDB tLCPolDB = new LCPolDB();
				tLCPolDB.setPolNo(tPolNo);
				if (!tLCPolDB.getInfo()) {
					CError.buildErr(this, "查找保险信息失败!");
					return false;
				}
				bnfStr[1] = "保单号：" + tLCPolDB.getContNo();
				tListTable.add(bnfStr);
				
				String tRiskNameSql = "select riskname from lmrisk where riskcode ='"+ "?riskcode?" + "'";
				SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
				sqlbv9.sql(tRiskNameSql);
				sqlbv9.put("riskcode", tLCPolDB.getRiskCode());
				String tRiskName = lExeSQL.getOneValue(sqlbv9);
				String[] polStr = new String[3];
				polStr[0] = "险种：" + tRiskName;
				//合同处理结论
//				String tHtcljlSql = "select polno from lccontstate where polno='"+tPolNo+"' and StateType='Terminate' and state='1' ";				
//				String tHtcljl = lExeSQL.getOneValue(tHtcljlSql);		
				String tHtcljl ="";
				if("4".equals(tLCPolDB.getAppFlag()))
				{
					tHtcljl="终止";
				}
				else
				{
					tHtcljl="有效";
				}
				polStr[1] = "合同处理结论："+tHtcljl;
//				polStr[1] = "保单号：" + tLCPolDB.getPolNo();
				polStr[2] = "保险金额：" + tLCPolDB.getAmnt();
				tListTable.add(polStr);
				String bxSQL = "";
				bxSQL = "select * from LLClaimDetail where clmno='" + "?clmno?"+ "'" + " and polno='" + "?polno?" + "' and givetype='0'";
				SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
				sqlbv10.sql(bxSQL);
				sqlbv10.put("clmno", mClmNo);
				sqlbv10.put("polno", tPolNo);
				logger.debug("查出合同下的保单险种号为" + tPolNo + "保项数目的sql="+ bxSQL);
				LLClaimDetailDB bxLLClaimDetailDB = new LLClaimDetailDB();
				LLClaimDetailSet bxLLClaimDetailSet = bxLLClaimDetailDB.executeQuery(sqlbv10);

				// 内层循环保项的数目
				for (int j = 1; j <= bxLLClaimDetailSet.size(); j++) {
					//剩余有效保额----------------------------------------------------
					String feeSQL = "";
					feeSQL = "select * from lcget where polno='"+ "?polno?" + "'"
							+ " and getdutycode='"+ "?getdutycode?" + "'"
							+ " and dutycode='"+ "?dutycode?" + "'";
					logger.debug("查询剩余保额的sql=" + feeSQL);
					SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
					sqlbv11.sql(feeSQL);
					sqlbv11.put("polno", bxLLClaimDetailSet.get(j).getPolNo());
					sqlbv11.put("getdutycode", bxLLClaimDetailSet.get(j).getGetDutyCode());
					sqlbv11.put("dutycode", bxLLClaimDetailSet.get(j).getDutyCode());
					LCGetDB tLCGetDB = new LCGetDB();
					LCGetSet tLCGetSet = tLCGetDB.executeQuery(sqlbv11);
					double fee = 0;// 剩余有效保额

					// 判断保项是否终止，如果终止 则 剩余有效保额==0
//					LLContStateSet tLLContStateSet = new LLContStateSet();
//					LLContStateDB tLLContStateDB = new LLContStateDB();
					String strStateSql = "select polno from llcontstate where 1=1 "+ " and clmno='" +"?clmno?"+"'"+ " and polno='"+ "?polno?" + "'"
									   + " and statetype='Terminate' and state='1' ";
					SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
					sqlbv12.sql(strStateSql);
					sqlbv12.put("clmno", mClmNo);
					sqlbv12.put("polno", bxLLClaimDetailSet.get(j).getPolNo());
//					tLLContStateSet.set(tLLContStateDB
//							.executeQuery(strStateSql));
					SSRS eSSRS = lExeSQL.execSQL(sqlbv12);
//					if (tLLContStateSet.size() > 0) {
					if(eSSRS.MaxRow>0)
					{
						fee = 0;// 保项终止---剩余有效保额==0
					} 
					else {
						fee = tLCGetSet.get(1).getStandMoney()- tLCGetSet.get(1).getSumMoney();
						fee = (fee > 0) ? fee : 0;
					}

					// 根据llclaimdetail中的 给付责任类型和给付责任编码 从LMDutyGetClm中 查询给付责任名称 即
					// 保项名称
					String bxNameSQL = "select GetDutyName from lmdutygetclm where getdutycode='"+ "?getdutycode?"+ "'"
							+ " and getdutykind='"+ "?getdutykind?" + "'";
					SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
					sqlbv13.sql(bxNameSQL);
					sqlbv13.put("getdutycode", bxLLClaimDetailSet.get(j).getGetDutyCode());
					sqlbv13.put("getdutykind", bxLLClaimDetailSet.get(j).getGetDutyKind());
					ExeSQL bxnameExeSQL = new ExeSQL();
					String bxName = bxnameExeSQL.getOneValue(sqlbv13);

					double tRealPay = bxLLClaimDetailSet.get(j).getRealPay();

					String[] stra = new String[3];
					stra[0] = "给付项目：" + bxName;// 保项

					if (tGetMode.equals("2")) {
						tRealPay = 0.00;
						fee = 0.00;
					}
					stra[1] = "理赔金额："+ new DecimalFormat("0.00").format(tRealPay); // 赔付金额
					stra[2] = "剩余有效保额：" + new DecimalFormat("0.00").format(fee); // lcget表中standmoney
																					// -																					// summoney
					tListTable.add(stra);// 将保项信息加入ListTable
				}
				
				//红利结算 FeeOperationType = 'C05' 红利结算
				String tHongliSql = "select (case when sum(getmoney) is null then 0 else sum(getmoney) end) from llbnf where clmno='"+"?clmno?"+"' and polno='"+"?polno?"+"'  "
								  + "and bnfno='"+"?bnfno?"+"'and feeoperationtype = 'C05'";
				SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
				sqlbv14.sql(tHongliSql);
				sqlbv14.put("clmno", mClmNo);
				sqlbv14.put("polno", tPolNo);
				sqlbv14.put("bnfno", tLLBnfGatherSchema.getBnfNo());
				String tHongli = lExeSQL.getOneValue(sqlbv14);
				
				//保单欠款结算
				String tBdqkjsfSql = "select (case when sum(getmoney) is null then 0 else sum(getmoney) end) money  from llbnf a where clmno='"+"?clmno?"+"' "
						 		  + "and polno='"+"?polno?"+"' and a.feeoperationtype ='C02'  and bnfno='"+"?bnfno?"+"' ";
				String tBdqkjszSql = " select (case when sum(-getmoney) is null then 0 else sum(-getmoney) end) money from llbnf  where clmno='"+"?clmno?"+"' and polno='"+"?polno?"+"'" 
                				  + " and feeoperationtype ='C03' and bnfno='"+"?bnfno?"+"'";

				String tBdqkjsSql ="select sum(money) from ("+tBdqkjsfSql+"union "+tBdqkjszSql+") g";
				SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
				sqlbv15.sql(tBdqkjsSql);
				sqlbv15.put("clmno", mClmNo);
				sqlbv15.put("polno", tPolNo);
				sqlbv15.put("bnfno", tLLBnfGatherSchema.getBnfNo());
				String tBdqkjs = lExeSQL.getOneValue(sqlbv15);
				
				//保险费结算(保单补费于退费)
				String tBxfjsSql = "select (case when sum(getmoney) is null then 0 else sum(getmoney) end) from llbnf a where clmno='"+"?clmno?"+"' and polno='"+"?polno?"+"' "
								 + "and a.feeoperationtype in('C01','C02','C09','D01','D05','D06') and bnfno='"+"?bnfno?"+"'";
				SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
				sqlbv16.sql(tBxfjsSql);
				sqlbv16.put("clmno", mClmNo);
				sqlbv16.put("polno", tPolNo);
				sqlbv16.put("bnfno", tLLBnfGatherSchema.getBnfNo());
				String tBxfjs = lExeSQL.getOneValue(sqlbv16);
				
//				
				//生存金结算
				String tScjjsSql = "select (case when sum(getmoney) is null then 0 else sum(getmoney) end) from llbnf a where feeoperationtype='C04'"
								 + " and clmno='"+"?clmno?"+"' and polno='"+"?polno?"+"' and bnfno='"+"?bnfno?"+"'";
				SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
				sqlbv17.sql(tScjjsSql);
				sqlbv17.put("clmno", mClmNo);
				sqlbv17.put("polno", tPolNo);
				sqlbv17.put("bnfno", tLLBnfGatherSchema.getBnfNo());
				String tScjjs = lExeSQL.getOneValue(sqlbv17);
				
				//延滞利息
				String tYzlxSql ="select (case when sum(pay) is null then 0 else sum(pay) end) from ljagetclaim where actugetno='"+"?actugetno?"
				               +"' and feefinatype='YCLX'  and polno='"+"?polno?"+"' ";
				SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
				sqlbv18.sql(tYzlxSql);
				sqlbv18.put("actugetno", tLLBnfGatherSchema.getOtherNo());
				sqlbv18.put("polno", tPolNo);
				String tYzlx = lExeSQL.getOneValue(sqlbv18);
				//合计受益金额
				String tBSumSql = "select (case when sum(getmoney) is null then 0 else sum(getmoney) end) from llbnf a where clmno='"+"?clmno?"+"' and polno='"+"?polno?"+"'  "
				 				+ "and bnfno='"+"?bnfno?"+"'";
				SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
				sqlbv19.sql(tBSumSql);
				sqlbv19.put("clmno", mClmNo);
				sqlbv19.put("polno", tPolNo);
				sqlbv19.put("bnfno", tLLBnfGatherSchema.getBnfNo());
				String tBSum = lExeSQL.getOneValue(sqlbv19);
				
								
				//险种给付金额
//				String tXzjfjeSql = "select nvl(sum(realpay),0) from llclaimdetail where polno='"+tPolNo+"' and clmno='"+mClmNo+"'";

//				String tXzjfje = lExeSQL.getOneValue(tXzjfjeSql);
				
				String tJS[] = new String[3];
				tJS[0] = "保险费结算："+tBxfjs;
				tJS[1] = "生存金结算："+tScjjs;
				tJS[2] = "延滞利息结算："+tYzlx;
				tListTable.add(tJS);
				String tJS1[] = new String[3];
				tJS1[0] = "红利结算："+tHongli;
				tJS1[1] = "保单欠款结算："+tBdqkjs;
				tJS1[2] = "合计受益金额："+tBSum;
				tListTable.add(tJS1);
				

			String tZffs = tLLBnfGatherSchema.getCasePayMode();
			if("1".equals(tZffs)){
				tZffs = "现金";
			}
			if("2".equals(tZffs)){
				tZffs = "现金支票";
			}
			if("3".equals(tZffs)){
				tZffs = "转账支票";
			}
			if("4".equals(tZffs)){
				tZffs = "银行转账";
			}
			if("5".equals(tZffs)){
				tZffs = "内部转账";
			}
			

			String tPayName = tLLBnfGatherSchema.getPayeeName();//领款人
			String tPayRelationSql = "select codename from ldcode where codetype='relation' and code='"+
			"?code?"+"'";
			SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
			sqlbv20.sql(tPayRelationSql);
			sqlbv20.put("code", tLLBnfGatherSchema.getRelationToPayee());
			String tPayRelation = lExeSQL.getOneValue(sqlbv20);//领款人与受益人关系
			String tActugetNo = tLLBnfGatherSchema.getOtherNo();

			
			//支付银行
			String tBankCode = tLLBnfGatherSchema.getBankCode();
			String tBankSql = "select bankname from ldbank where bankcode='"+"?bankcode?"+"'";
			SQLwithBindVariables sqlbv21 = new SQLwithBindVariables();
			sqlbv21.sql(tBankSql);
			sqlbv21.put("bankcode", tBankCode);
			String tBankName = lExeSQL.getOneValue(sqlbv21);
			
			if(tBankName==null)
			{
				tBankName="";
			}
			
			//银行账户名
			String tAccName = tLLBnfGatherSchema.getAccName();
			
			if(tAccName==null)
			{
				tAccName="";
			}
			
			//银行账号
			String tBankAccNo = tLLBnfGatherSchema.getBankAccNo();
			
			if(tBankAccNo==null)
			{
				tBankAccNo="";
			}
			
			String tBnfStr1[] = new String[3];
			tBnfStr1[0] = "领款人："+tPayName;
			tBnfStr1[1] = "与受益人关系:"+tPayRelation;
			tBnfStr1[2] = "支付方式："+tZffs;
			tListTable.add(tBnfStr1);
			
			String tBnfStr2[] = new String[3];
			tBnfStr2[0] = "实付号："+tActugetNo;
			tBnfStr2[1] = "银行账号:"+tBankAccNo;
			tBnfStr2[2] = "银行账户名："+tAccName;
			tListTable.add(tBnfStr2);
			
			String tBnfStr3[] = new String[1];
			tBnfStr3[0] = "支付银行："+tBankName;
			tListTable.add(tBnfStr3);
			
		 }
		}

		tTextTag.add("ZJFJE", amontFee);
		tTextTag.add("DXJE", DXJE);
		tTextTag.add("ZEndCaseDate", endcasedate);
		tXmlExport.addListTable(tListTable, Title);
		
		String tPSql = "select (select codealias from ldcurrency where currcode=currency) ,(case when sum(money) is null then 0 else sum(money) end) from ( select currency currency,realpay money from llclaimpolicy a where clmno='"+ "?clmno?" + "' and not exists(select 1 from lmrisktoacc c, lmriskinsuacc r "
			  	+ " where r.insuaccno = c.insuaccno and r.acckind = '2' "
				+ " and c.riskcode =a.riskcode) "
				+" union select currency currency,(-1)*(money) money from lcinsureacctrace a where busytype='CL' and accalterno='"+ "?clmno?" + "' and AccAlterType='4' and exists(select 1 from lmrisktoacc c, lmriskinsuacc r "
			  	+ " where r.insuaccno = c.insuaccno and r.acckind = '2' "
				+ " and c.riskcode =a.riskcode)"
				+") g group by currency  order by currency";
		SQLwithBindVariables sqlbv22 = new SQLwithBindVariables();
		sqlbv22.sql(tPSql);
		sqlbv22.put("clmno", mClmNo);
		SSRS tPolSSRS = new SSRS();
		tPolSSRS = tExeSQL.execSQL(sqlbv22);
		for (int k = 1; k <= tPolSSRS.MaxRow; k++) {
			String[] Str = new String[2];
			Str[0] = tPolSSRS.GetText(k, 1);
			Str[1] = new DecimalFormat("0.00").format(Double.parseDouble(tPolSSRS.GetText(k, 2)));
			tListTableD.add(Str);
		}
		tXmlExport.addListTable(tListTableD, TitleD);
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 加入单个字段的打印变量
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 加入多行数据的打印变量
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		// xmlexport.addDisplayControl("Risk"); //模版上的主险附加险部分的控制标记
		// tXmlExport.outputDocumentToFile("c:\\", "testHZM"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(tXmlExport);
		mResult.add(mTransferData);
		logger.debug("xmlexport=" + tXmlExport);

		String updateStateSql = "update loprtmanager set stateflag='1' where otherno='"
				+ "?clmno?" + "' and code='PCT010'";
		logger.debug("更新打印管理表的sql:" + updateStateSql);
		SQLwithBindVariables sqlbv23 = new SQLwithBindVariables();
		sqlbv23.sql(updateStateSql);
		sqlbv23.put("clmno", mClmNo);
		mMMap.put(sqlbv23, "UPDATE");
		mResult.add(mMMap);

		return true;
	
	}
	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		mInputData.clear();
		mInputData.add(mMMap);
		return true;
	}

	/**
	 * 提交数据
	 * 
	 * @return
	 */
	private boolean pubSubmit() {
		// 进行数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimCalCheckBL";
			tError.functionName = "PubSubmit.submitData";
			tError.errorMessage = "数据提交失败!";
			//
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;

	}

	public CErrors getErrors() {
		return mErrors;
	}

	public VData getResult() {
		return mResult;
	}

}
