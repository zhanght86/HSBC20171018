package com.sinosoft.lis.bq;

import org.apache.log4j.Logger;

import java.text.DecimalFormat;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.acc.*;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.utility.*;

public class PEdorARPrintBL implements EdorPrint {
	private static Logger logger = Logger.getLogger(PEdorARPrintBL.class);

	// 公共数据
	private VData mResult = new VData();

	public CErrors mErrors = new CErrors();

	// 全局变量
	private VData mInputData = new VData();

	private XmlExportNew xmlexport = new XmlExportNew();

	private TextTag mTextTag = new TextTag();

	private ListTable mListTableOut = new ListTable();

	DecimalFormat df2 = new DecimalFormat("0.00");

	private LPEdorAppSchema mLPEdorAppSchema = new LPEdorAppSchema();

	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	private LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();

	private String[] strNSS;

	public String CurrentTime = PubFun.getCurrentTime();

	public String CurrentDate = PubFun.getCurrentDate();

	public PEdorARPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();

		// 数据传输
		if (!getInputData()) {
			mErrors.addOneError("AR数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			mErrors.addOneError("AR传入数据无效!");
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			mErrors.addOneError("AR数据打印失败!");
			return false;
		}

		// 准备数据
		if (!prepareData()) {
			mErrors.addOneError("AR准备数据失败!");
			return false;
		}

		return true;
	}

	private boolean getInputData() {

		tLPEdorItemSchema = (LPEdorItemSchema) mInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);

		// 由于传进来的LPEdorItemSchema信息并不完整，所以此处重新查询一次
		LPEdorItemDB mLPEdorItemDB = new LPEdorItemDB();
		mLPEdorItemDB.setEdorAcceptNo(tLPEdorItemSchema.getEdorAcceptNo());
		mLPEdorItemDB.setEdorNo(tLPEdorItemSchema.getEdorNo());
		mLPEdorItemSchema = mLPEdorItemDB.query().get(1);

		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setEdorAcceptNo(tLPEdorItemSchema.getEdorAcceptNo());
		tLPEdorAppDB.getInfo();
		mLPEdorAppSchema = tLPEdorAppDB.getSchema();

		xmlexport = (XmlExportNew) mInputData.getObjectByObjectName(
				"XmlExportNew", 0);
		if (mLPEdorItemSchema == null) {
			mErrors.addOneError("AR得到数据失败!");
			return false;
		}

		return true;
	}

	private boolean checkData() {
		return true;
	}

	private boolean dealData() {

		// xmlexport.createDocument("PrtEndorsement.vts", "IGPrinter");

		/** 要显示的部分 */
		xmlexport.addDisplayControl("displayHead1");
		xmlexport.addDisplayControl("displayTail1");

		LCContDB tLCContDB = new LCContDB();
		LCContSchema tLCContSchema = new LCContSchema();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			mErrors.addOneError("打印生成数据时，取保单信息失败!");
			return false;
		}
		tLCContSchema = tLCContDB.getSchema();

		/** 批单头部 */
		mTextTag.add("AppntName", tLCContSchema.getAppntName()); // 原投保人姓名
		mTextTag.add("InsuredName", tLCContSchema.getInsuredName()); // 被保人姓名
		mTextTag.add("EdorAcceptNo", mLPEdorItemSchema.getEdorAcceptNo()); // 保全受理号
		mTextTag.add("EdorNo", mLPEdorItemSchema.getEdorNo()); // 批单号
		mTextTag.add("ContNo", mLPEdorItemSchema.getContNo()); // 保单号

		mTextTag.add("LCCont.AppntName", tLCContSchema.getAppntName());// 投保人
		mTextTag.add("AppDate", BqNameFun.getFDate(mLPEdorItemSchema
				.getEdorAppDate()));// 保全申请年
		/** 投连提前通知领取 */
		// 暂时不知道取哪两个字段，先找两个字段代替下
		String strSQL = "";
		strSQL = "select '1',(select b.insuaccname from lmriskinsuacc b where b.insuaccno=a.OutInsuAccNo),OutInsuAccNo, '4','5','6','7',sum(AccOutUnit),'9' from LPInsuAccOut a where a.EdorNo='"
				+ "?EdorNo?" + "' group by OutInsuAccNo ";
		// "select SerialNo,(select b.insuaccname from lmriskinsuacc b where
		// b.insuaccno=a.OutInsuAccNo),OutInsuAccNo, OutPayPlanCode,(select
		// distinct c.payplanname from lmriskaccpay c where
		// c.payplancode=a.OutPayPlanCode),AccOutType,AccOutBala,AccOutUnit,AccOutRate
		// from LPInsuAccOut a where a.EdorNo= '" +
		// mLPEdorItemSchema.getEdorNo() + "'";
		// String strSQLIn="select SerialNo,(select b.insuaccname from
		// lmriskinsuacc b where b.insuaccno=a.InInsuAccNo),InInsuAccNo,
		// InPayPlanCode,(select distinct c.payplanname from lmriskaccpay c
		// where
		// c.payplancode=a.InPayPlanCode),AccInType,AccInBala,AccInUnit,AccInRate
		// from LPInsuAccin a where a.EdorNo= '"+
		// mLPEdorItemSchema.getEdorNo() + "'";
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(strSQL);
		sbv1.put("EdorNo", mLPEdorItemSchema.getEdorNo());
		ExeSQL tExeSQL = new ExeSQL();
		String strRisknameSQL = "select b.riskname from lmriskapp b, lcpol c where c.contno = '"
				+ "?contno?"
				+ "' and c.riskcode = b.riskcode";
        SQLwithBindVariables sbv2=new SQLwithBindVariables();
        sbv2.sql(strRisknameSQL);
        sbv2.put("contno", mLPEdorItemSchema.getContNo());
		String strRiskname = tExeSQL.getOneValue(sbv2);

		SSRS tSSRS = new SSRS();

		tSSRS = tExeSQL.execSQL(sbv1);
		logger.debug(strSQL);
		if (tSSRS != null || tSSRS.MaxRow > 0) {
			for (int j = 1; j <= tSSRS.MaxRow; j++) {
				mListTableOut.setName("PGout");
				strNSS = new String[9];
				strNSS[0] = strRiskname;
				strNSS[2] = tSSRS.GetText(j, 2);
				strNSS[1] = tSSRS.GetText(j, 3);

				// 查询价格
				SSRS tSSRS2 = new SSRS();
				String tStartDate = "";
				String SQL1 = "select min(startdate) from loaccunitprice where  startdate>='"
						+ "?startdate?" + "'";
				SQLwithBindVariables sbv3=new SQLwithBindVariables();
				sbv3.sql(SQL1);
				sbv3.put("startdate", mLPEdorItemSchema.getEdorValiDate());
				tSSRS2 = tExeSQL.execSQL(sbv3);
				tStartDate = tSSRS2.GetText(1, 1);

				if (tStartDate.equals("") || tStartDate == null) {
					String SQL2 = "select max(startdate) from loaccunitprice where startdate<'"
							+ "?startdate?" + "'";
					SQLwithBindVariables sbv4=new SQLwithBindVariables();
					sbv4.sql(SQL2);
					sbv4.put("startdate", mLPEdorItemSchema.getEdorValiDate());
					tSSRS2 = tExeSQL.execSQL(sbv4);
					tStartDate = tSSRS2.GetText(1, 1);
				}
				String SQL3 = "select UnitPriceSell from loaccunitprice where insuaccno='"
						+ "?insuaccno?"
						+ "' and startdate='"
						+ "?tStartDate?" + "'";
                SQLwithBindVariables sbv5=new SQLwithBindVariables();
                sbv5.sql(SQL3);
                sbv5.put("insuaccno", tSSRS.GetText(j, 3));
                sbv5.put("tStartDate", tStartDate);
				double Price = Double.parseDouble(tExeSQL.getOneValue(sbv5));

				strNSS[3] = df2.format(Double.parseDouble(tSSRS.GetText(j, 8))
						* Price);
				strNSS[4] = tSSRS.GetText(j, 5);
				strNSS[5] = tSSRS.GetText(j, 6);
				strNSS[5] = tSSRS.GetText(j, 6);
				// if( tSSRS.GetText(j, 6).equals("1"))
				// {
				strNSS[5] = "转出";
				// }
				// if( tSSRS.GetText(j, 6).equals("2"))
				// {
				// strNSS[5] ="2-金额";
				// }
				// if( tSSRS.GetText(j, 6).equals("3"))
				// {
				// strNSS[5] ="3-比例";
				// }
				strNSS[6] = tSSRS.GetText(j, 7);
				strNSS[7] = tSSRS.GetText(j, 8);
				strNSS[8] = tSSRS.GetText(j, 9);

				if (!"".equals(strNSS[1])) {
					mListTableOut.add(strNSS);
					xmlexport.addDisplayControl("displayAR");
				}
			}
		}
		/*
		 * SSRS ttSSRS = new SSRS(); ExeSQL ttExeSQL = new ExeSQL(); ttSSRS =
		 * ttExeSQL.execSQL(strSQLIn); logger.debug(strSQLIn); ttSSRS =
		 * ttExeSQL.execSQL(strSQLIn); if (ttSSRS != null || ttSSRS.MaxRow > 0) {
		 * for (int j = 1; j <= ttSSRS.MaxRow; j++) {
		 * mListTableIn.setName("PGin"); strNSSin = new String[9]; strNSSin[0] =
		 * ttSSRS.GetText(j, 1); strNSSin[1] = ttSSRS.GetText(j, 2); strNSSin[2] =
		 * ttSSRS.GetText(j, 3); strNSSin[3] = ttSSRS.GetText(j, 4); strNSSin[4] =
		 * ttSSRS.GetText(j, 5); strNSSin[5] = ttSSRS.GetText(j, 6); strNSSin[6] =
		 * ttSSRS.GetText(j, 7); strNSSin[7] = ttSSRS.GetText(j, 8); strNSSin[8] =
		 * ttSSRS.GetText(j, 9);
		 * 
		 * if (!"".equals(strNSSin[1])) { mListTableIn.add(strNSSin);
		 * xmlexport.addDisplayControl("displayRD"); } } }
		 */

		// /**批单尾部*/
		// mTextTag.add("LPEdor.ValiDate", mLPEdorItemSchema.getEdorValiDate());
		// //生效日
		// mTextTag.add("Operator",
		// CodeNameQuery.getOperator(mLPEdorItemSchema.getOperator())); //经办人
		// mTextTag.add("ApproveOperator",
		// CodeNameQuery.getOperator(mLPEdorItemSchema.
		// getApproveOperator())); //复核人
		//
		// String[] allArr =
		// BqNameFun.getAllNames(tLCContSchema.getAgentCode());
		// mTextTag.add("ManageCom", allArr[BqNameFun.SALE_SERVICE]);
		// mTextTag.add("LABranchGroup.Name", allArr[BqNameFun.DEPART]);
		// mTextTag.add("LAAgent.Name", allArr[BqNameFun.AGENT_NAME]);
		// mTextTag.add("LAAgent.AgentCode", tLCContSchema.getAgentCode());
		// 手续费信息
		LCPolSchema mainLCPolSchema = new LCPolSchema();
		LCPolDB mainLCPolDB = new LCPolDB();
		String SQL0 = "select * from lcpol where contno = '"
				+ "?contno?"
				+ "' and riskcode in (select riskcode from lmriskapp where risktype3 = '3')";
		logger.debug(SQL0);
        SQLwithBindVariables sbv6=new SQLwithBindVariables();
        sbv6.sql(SQL0);
        sbv6.put("contno", mLPEdorItemSchema.getContNo());
		mainLCPolSchema = mainLCPolDB.executeQuery(sbv6).get(1);
		TLbqForFee tTLbqForFee = new TLbqForFee();
		double douCalFee = -1;
		String CalFee = "";
		String strStartDate = "";

		try {
			strStartDate = tTLbqForFee.GetStartDate(mainLCPolSchema
					.getCValiDate(), mLPEdorItemSchema.getEdorValiDate());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String ChangeCount = "12";
		String SQL = "select count(*) from lpedoritem where ContNo = '"
				+ "?ContNo?"
				+ "' "
				+ " and edortype = 'AR' and EdorState = '0' and EdorValiDate >='"
				+ "?strStartDate?" + "' and EdorValiDate<='"
				+ "?EdorValiDate?" + "'";
		SQLwithBindVariables sbv7=new SQLwithBindVariables();
		sbv7.sql(SQL);
		sbv7.put("ContNo", mainLCPolSchema.getContNo());
		sbv7.put("strStartDate", strStartDate);
		sbv7.put("EdorValiDate", mLPEdorItemSchema.getEdorValiDate());
		ExeSQL ex = new ExeSQL();
		ChangeCount = ex.getOneValue(sbv7);
		ChangeCount = String.valueOf((Integer.parseInt(ChangeCount) + 1));

		String strMessage = "";
		if (mainLCPolSchema.getRiskCode().equals("RPUL"))// 测试修改
		{
			try {
				douCalFee = tTLbqForFee.GetCalFee(-1000, mLPEdorItemSchema
						.getEdorNo());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (douCalFee == 0) {
				CalFee = "本次领取无手续费";
			} else {
				CalFee = "本次领取手续费为" + String.valueOf(douCalFee) + "元";
			}
			strMessage = strMessage + "这是本保单年度的第" + ChangeCount + "次领取，"
					+ CalFee;
		} else// 趸缴产品
		{

			// ///////////////////////////鲁哲luzhe20070917注释\\\\\\\\\\\\\\\\\\\\\\\\\\
			// 得到第几保单年
			// int YearCount =
			// tTLbqForFee.getYearCount(mainLCPolSchema.getCValiDate());
			// String MessHead = "";
			// String MessMid = "";
			// String MessEnd = "%收取手续费";
			// //YearCount = 2;//测试用的数,第N保单年
			// //ChangeCount = "2";//测试用的数,第N次领取
			// //判断本年度领取次数
			// if(Integer.parseInt(ChangeCount)>1)
			// {
			// MessHead = "本保单年度有过领取，这是本保单年度的第"+ChangeCount+"次领取，";
			// MessMid = "本次领取以领取单位数在领取申请后下一个账户评估日的价值的";
			// }
			// else
			// {
			// MessHead = "本保单年度没有做过领取，这是本保单年度的第1次领取，";
			// MessMid = "您可以免费领取账户价值的10%，超过10%的部分按照领取单位数在领取申请后下一个账户评估日的价值的";
			// }
			//    			
			//    			  
			// if(YearCount==1)
			// {
			// strMessage = MessHead+"本次领取手续费为领取单位数在领取申请后下一个账户评估日的价值的10%";
			// }
			// else if(YearCount>1&&YearCount<6)
			// {
			// strMessage = MessHead+MessMid+(6-YearCount)*2+MessEnd;
			// }
			// else
			// {
			// strMessage = MessHead+"领取无手续费";
			// }
			// ///////////////////////////鲁哲luzhe20070917注释完毕\\\\\\\\\\\\\\\\\\\\\\\\\\
			String MessHead = "";
			String MessMid = "";
			if (Integer.parseInt(ChangeCount) > 1) {
				MessHead = "本保单年度有过领取，这是本保单年度的第" + ChangeCount + "次领取，";
			} else {
				MessHead = "本保单年度没有做过领取，这是本保单年度的第1次领取，";
			}
			try {
				douCalFee = tTLbqForFee.GetCalFee(1000, mLPEdorItemSchema
						.getEdorNo());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (douCalFee == 0) {
				MessMid = "本次领取无手续费";
			} else {
				double bili = douCalFee / 10;
				MessMid = "本次领取的手续费按照领取单位数在领取申请后下一个账户评估日的价值的" + bili + "%收取";

			}

			strMessage = MessHead + MessMid;
		}

		mTextTag.add("AboutThis", strMessage);

		/*
		 * 设置displayTail1中的信息
		 */
		// BqNameFun.getDisPlayTail(mLPEdorItemSchema, mTextTag);
		BqNameFun.AddEdorValiDatePart(mLPEdorItemSchema, mLPEdorAppSchema,
				xmlexport);

		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean prepareData() {
		if (mTextTag.size() < 1) {
			mErrors.addOneError("生成数据失败!");
			return false;
		}
		String[] c_strNSOut = new String[9];
		// String[] c_strNSIn = new String[9];
		xmlexport.addListTable(mListTableOut, c_strNSOut);
		// xmlexport.addListTable(mListTableIn,c_strNSIn);
		xmlexport.addTextTag(mTextTag);
		mResult.add(xmlexport);
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	// public static void main(String[] args)
	// {
	//
	// try
	// {
	// LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
	// tLPEdorItemSchema.setEdorNo("6020070523000002");
	// LPEdorItemDB tt = tLPEdorItemSchema.getDB();
	// tt.query().get(1); //不tt.query()可能得到null或则size为0的*Set,此风格代码只能用于调试
	// tLPEdorItemSchema.setSchema(tt.query().get(1).getSchema());
	// VData tVData = new VData();
	// tVData.add(tLPEdorItemSchema);
	// PEdorPGPrintBL tBl = new PEdorPGPrintBL();
	// tBl.submitData(tVData, "");
	// VData rVDta = tBl.getResult();
	// XmlExport xmlexport = (XmlExport) rVDta.getObjectByObjectName(
	// "XmlExport", 0);
	// /**暂时用于调试把信息暂存在文件中，应该放在表lpedorprint的edorinfo字段，日后该之*/
	// xmlexport.outputDocumentToFile("E:\\work", "myPG.xml", false);
	//
	// }
	// catch (Exception ex)
	// {
	// ex.printStackTrace();
	// }
	//
	// }

}
