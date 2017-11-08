package com.sinosoft.lis.bq;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.acc.*;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.utility.*;

/**
 * <p>
 * Title: 投连随时部分领取批单
 * </p>
 * 
 * <p>
 * Description: 生成客户资料批单打印所需要的数据
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * </p>
 * 
 * @author guqc
 * 
 * @version 1.0
 */

public class PEdorTIPrintBL implements EdorPrint {
	private static Logger logger = Logger.getLogger(PEdorTIPrintBL.class);

	// 公共数据
	private VData mResult = new VData();

	public CErrors mErrors = new CErrors();

	String CurrentDate = PubFun.getCurrentDate();

	// 全局变量
	private VData mInputData = new VData();

	private XmlExportNew xmlexport = new XmlExportNew();

	private TextTag mTextTag = new TextTag();

	private ListTable mListTableOut = new ListTable();

	private ListTable mListTableIn = new ListTable();

	private LPEdorAppSchema mLPEdorAppSchema = new LPEdorAppSchema();

	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	private LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();

	private String[] strNSS;

	private String[] strNSSin;

	public PEdorTIPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();

		// 数据传输
		if (!getInputData()) {
			mErrors.addOneError("TI数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			mErrors.addOneError("TI传入数据无效!");
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			mErrors.addOneError("TI数据打印失败!");
			return false;
		}

		// 准备数据
		if (!prepareData()) {
			mErrors.addOneError("TI准备数据失败!");
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
			mErrors.addOneError("TI得到数据失败!");
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
		mTextTag.add("EdorAcceptNo", mLPEdorItemSchema.getEdorAcceptNo()); // 批单号
		mTextTag.add("EdorNo", mLPEdorItemSchema.getEdorNo()); // 批单号
		mTextTag.add("ContNo", mLPEdorItemSchema.getContNo()); // 保单号
		mTextTag.add("AppDate", BqNameFun.getFDate(mLPEdorItemSchema
				.getEdorAppDate()));

		/** 投连提前通知领取 */
		// 暂时不知道取哪两个字段，先找两个字段代替下
		String strSQL = "";
		strSQL = "select '',OutInsuAccNo,(select b.insuaccname from lmriskinsuacc b where b.insuaccno=a.OutInsuAccNo), '','','','',sum(AccOutUnit),'' from LPInsuAccOut a where a.edorno = '?edorno?' group by OutInsuAccNo";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(strSQL);
		sqlbv1.put("edorno", mLPEdorItemSchema.getEdorNo());
		// "select SerialNo,OutInsuAccNo,(select b.insuaccname from
		// lmriskinsuacc b where b.insuaccno=a.OutInsuAccNo),
		// OutPayPlanCode,(select distinct c.payplanname from lmriskaccpay c
		// where
		// c.payplancode=a.OutPayPlanCode),AccOutType,AccOutBala,AccOutUnit,AccOutRate
		// from LPInsuAccOut a where a.EdorNo= '" +
		// mLPEdorItemSchema.getEdorNo() + "'";
		String strSQLIn = "select '',InInsuAccNo,(select b.insuaccname from lmriskinsuacc b where b.insuaccno=a.InInsuAccNo), '','','','','',sum(AccInRate) from LPInsuAccin a where a.EdorNo='?EdorNo?' group by InInsuAccNo";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(strSQLIn);
		sqlbv2.put("EdorNo", mLPEdorItemSchema.getEdorNo());
		// "select SerialNo,InInsuAccNo,(select b.insuaccname from lmriskinsuacc
		// b where b.insuaccno=a.InInsuAccNo), InPayPlanCode,(select distinct
		// c.payplanname from lmriskaccpay c where
		// c.payplancode=a.InPayPlanCode),AccInType,AccInBala,AccInUnit,AccInRate
		// from LPInsuAccin a where a.EdorNo= '"+
		// mLPEdorItemSchema.getEdorNo() + "'";

		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sqlbv1);
		logger.debug(strSQL);
		if (tSSRS != null || tSSRS.MaxRow > 0) {
			for (int j = 1; j <= tSSRS.MaxRow; j++) {
				mListTableOut.setName("TIout");
				strNSS = new String[9];
				strNSS[0] = tSSRS.GetText(j, 1);
				strNSS[1] = tSSRS.GetText(j, 2);
				strNSS[2] = tSSRS.GetText(j, 3);
				strNSS[3] = tSSRS.GetText(j, 4);
				strNSS[4] = tSSRS.GetText(j, 5);
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
					xmlexport.addDisplayControl("displayTI");
				}
			}
		}
		SSRS ttSSRS = new SSRS();
		ExeSQL ttExeSQL = new ExeSQL();
		ttSSRS = ttExeSQL.execSQL(sqlbv2);
		logger.debug(strSQLIn);
		ttSSRS = tExeSQL.execSQL(sqlbv2);
		if (ttSSRS != null || ttSSRS.MaxRow > 0) {
			for (int j = 1; j <= ttSSRS.MaxRow; j++) {
				mListTableIn.setName("TIin");
				strNSSin = new String[9];
				strNSSin[0] = ttSSRS.GetText(j, 1);
				strNSSin[1] = ttSSRS.GetText(j, 2);
				strNSSin[2] = ttSSRS.GetText(j, 3);
				strNSSin[3] = ttSSRS.GetText(j, 4);
				strNSSin[4] = ttSSRS.GetText(j, 5);
				strNSSin[5] = ttSSRS.GetText(j, 6);
				// if( ttSSRS.GetText(j, 6).equals("1"))
				// {
				strNSSin[5] = "转入";
				// }
				// if( ttSSRS.GetText(j, 6).equals("2"))
				// {
				// strNSSin[5] ="2-金额";
				// }
				// if( ttSSRS.GetText(j, 6).equals("3"))
				// {
				// strNSSin[5] ="3-比例";
				// }

				strNSSin[6] = ttSSRS.GetText(j, 7);
				strNSSin[7] = ttSSRS.GetText(j, 8);
				strNSSin[8] = ttSSRS.GetText(j, 9);

				if (!"".equals(strNSSin[1])) {
					mListTableIn.add(strNSSin);
					xmlexport.addDisplayControl("displayTI");
				}
			}
		}

		LCPolSchema mainLCPolSchema = new LCPolSchema();
		LCPolDB mainLCPolDB = new LCPolDB();

		mainLCPolDB.setContNo(mLPEdorItemSchema.getContNo());
		mainLCPolSchema = mainLCPolDB.query().get(1);
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
		String SQL = "select count(*) from lpedoritem where ContNo = '?ContNo?' "
				+ " and edortype = 'TI' and EdorState = '0' and EdorValiDate >='?strStartDate?' and EdorValiDate<='?EdorValiDate?'";
		ExeSQL ex = new ExeSQL();
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(SQL);
		sqlbv3.put("ContNo", mLPEdorItemSchema.getContNo());
		sqlbv3.put("strStartDate", strStartDate);
		sqlbv3.put("EdorValiDate", mLPEdorItemSchema.getEdorValiDate());
		ChangeCount = ex.getOneValue(sqlbv3);
		ChangeCount = String.valueOf((Integer.parseInt(ChangeCount) + 1));
		try {
			douCalFee = tTLbqForFee.GetCalFee(-1000, mLPEdorItemSchema
					.getEdorNo());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (douCalFee == 0) {
			CalFee = "转换无手续费";
		} else {
			CalFee = "转换手续费为" + String.valueOf(douCalFee) + "元";
		}

		CalFee = "这是本保单年度的第" + ChangeCount + "次转换，本次" + CalFee;
		mTextTag.add("AboutThis", CalFee);

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
		String[] c_strNSIn = new String[9];
		xmlexport.addListTable(mListTableOut, c_strNSOut);
		xmlexport.addListTable(mListTableIn, c_strNSIn);
		xmlexport.addTextTag(mTextTag);
		mResult.add(xmlexport);
		return true;
	}

	public VData getResult() {
		return mResult;
	}
}
