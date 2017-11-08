package com.sinosoft.lis.f1print;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;

import com.sinosoft.lis.db.LCBnfDB;
import com.sinosoft.lis.db.LCCSpecDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.db.LDSysErrLogDB;
import com.sinosoft.lis.db.LMCalModeDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.EasyScanQueryBL;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LAComSchema;
import com.sinosoft.lis.schema.LCBnfSchema;
import com.sinosoft.lis.schema.LCCSpecSchema;
import com.sinosoft.lis.schema.LCContPrintSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LDComSchema;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LCCSpecSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LMCalModeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.CMySQLBlob;
import com.sinosoft.utility.COracleBlob;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XMLDataBlob;
import com.sinosoft.utility.XMLDataTag;
import com.sinosoft.utility.XMLDataset;
import com.sinosoft.utility.XMLDatasets;

/**
 * <p>
 * ClassName: LCPolF1P_IDGFBL
 * </p>
 * <p>
 * Description: LCPolF1P_IDGFBL类文件 保单重新打印---重新查询相关信息，生成XML文件 保单补打
 * ---直接从LCContPrint中读取XML文件信息
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @Database: LIS
 * @CreateDate：2006-10-10
 * @ReWiriter pst
 * @ReWiriteDate：2009-03-15
 *            <p>
 *            保单打印从险种层升级到保单层
 *            </p>
 */
public class LCPolF1P_IDGFBL {
private static Logger logger = Logger.getLogger(LCPolF1P_IDGFBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 容器类，向前台传递数据 */
	private VData mResult = new VData();

	// 业务处理相关变量

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private LCPolSet mLCPolSet = new LCPolSet();

	private LCContSet tLCContSet = new LCContSet();

	private String mPrtNo = "";

	private XMLDatasets mXMLDatasets = new XMLDatasets();

	private String mOperate = "";

	private String tCurMakeDate = PubFun.getCurrentDate();

	private String tCurMakeTime = PubFun.getCurrentTime();

	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();

	/** 记录本次保单成功和是失败总计 */
	public int tSucc = 0, tFail = 0;

	private ExeSQL tExeSQL = new ExeSQL();
	

	/**
	 * 模板的版本编号
	 */
	private String tFormVersion = "";

	public String mContent = "";

	public LCPolF1P_IDGFBL() {

	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		try {
			mOperate = cOperate;

			if (!mOperate.equals("PRINT") && !mOperate.equals("REPRINT")
					&& !mOperate.equals("PRINTEX")) {
				CError.buildErr(this, "不支持的打印操作字符串");
				return false;
			}

			// 得到外部传入的数据，将数据备份到本类中
			if (!getInputData(cInputData)) {
				return false;
			}

			// 打印保单的操作
			if (mOperate.equals("PRINT")) {
				// 准备所有要打印的数据
				if (!getPrintData()) {
					return false;
				}
			}

			// 补打保单的操作
			if (mOperate.equals("REPRINT")) {
				if (!getPrintData()) {
					return false;
				}
			}

			// 前台保单打印的操作
			if (mOperate.equals("PRINTEX")) {
				// 准备所有要打印的数据
				if (!getPrintData()) {
					return false;
				}

				// 因为数据格式不同，进行一下转换。
				Document doc = mXMLDatasets.getDocument();
				Element ele = doc.getRootElement().getChild("DATASET");
				doc = new Document(ele);
				// countPrint(doc);
				mResult.clear();

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				XMLOutputter outputter = new XMLOutputter("", false, "UTF-8");
				outputter.output(doc, baos);

				mResult.add(new ByteArrayInputStream(baos.toByteArray()));
			}

			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr(this, "批量打印保单失败!");
			return false;
		}
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		if (mOperate.equals("PRINT")) { // 打印保单
			mGlobalInput.setSchema((GlobalInput) cInputData
					.getObjectByObjectName("GlobalInput", 0));
			tLCContSet.set((LCContSet) cInputData.getObjectByObjectName(
					"LCContSet", 0));
		} else if (mOperate.equals("REPRINT")) { // 补打保单
			mGlobalInput.setSchema((GlobalInput) cInputData
					.getObjectByObjectName("GlobalInput", 0));
			tLCContSet.set((LCContSet) cInputData.getObjectByObjectName(
					"LCContSet", 0));
		} else if (mOperate.equals("PRINTEX")) {
			mGlobalInput.setSchema((GlobalInput) cInputData
					.getObjectByObjectName("GlobalInput", 0));
			tLCContSet.set((LCContSet) cInputData.getObjectByObjectName(
					"LCContSet", 0));
		}

		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private boolean getPrintData() {

		try {
			// 循环处理选定打印保单集合
			String LockNo = "";
			TransferData tTransferData = new TransferData();
			if(tLCContSet.size()>0)
			{
				mXMLDatasets.createDocument();
				for (int nIndex = 0; nIndex < tLCContSet.size(); nIndex++) {

					logger.debug("nIndex"+tFail+"---"+nIndex);
					mErrors.clearErrors();


					LCContSchema tLCContSchema = new LCContSchema();
					LCContDB tLCContDB = new LCContDB();
					tLCContDB.setContNo(tLCContSet.get(nIndex + 1).getContNo());
					if (tLCContDB.mErrors.needDealError()) {
						CError.buildErr(this, "保单" + tLCContSchema.getContNo()
								+ "保单数据不完整");
						// 插入错误日志
						insertErrLog("保单" + tLCContSchema.getContNo() + "保单数据不完整",
								tLCContSchema);
						tFail++;
						continue;
					}
					if (!tLCContDB.getInfo()) {
						CError.buildErr(this, "保单" + tLCContSchema.getContNo()
								+ "保单数据不完整");
						// 插入错误日志
						insertErrLog("保单" + tLCContSchema.getContNo() + "保单数据不完整",
								tLCContSchema);
						tFail++;
						continue;
					}

					tLCContSchema = tLCContDB.getSchema();

					LockNo = "";
					LockNo = tLCContSchema.getContNo();
					if (!mPubLock.lock(LockNo, "LC0006", mGlobalInput.Operator)) {
						CError tError = new CError(mPubLock.mErrors.getLastError());
						this.mErrors.addOneError(tError);
						insertErrLog("保单" + tLCContSchema.getContNo()
								+ "号被锁住", tLCContSchema);
						tFail++;
						continue;
					}

					LCInsuredSet nLCInsuredSet = new LCInsuredSet();
					LCInsuredDB nLCInsuredDB = new LCInsuredDB();
					String tSQL = "select * from lcinsured where  prtno='"
							+ "?prtno?" + "'";
					SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
					sqlbv1.sql(tSQL);
					sqlbv1.put("prtno", tLCContSchema.getPrtNo());
					nLCInsuredSet = nLCInsuredDB.executeQuery(sqlbv1);
					if (nLCInsuredSet.size() < 1 || nLCInsuredSet == null) {
						CError.buildErr(this, tLCContSchema.getContNo()
								+ "号保单被保人信息不完整");
						insertErrLog("保单" + tLCContSchema.getContNo()
								+ "号保单被保人信息不完整", tLCContSchema);
						tFail++;
						continue;
					}
					boolean bFlag = false;
					
					XMLDatasets tXMLDatasets = new XMLDatasets();
					tXMLDatasets.createDocument();

					XMLDataset tXMLDataset = null;
					// 生成打单张保单打印接口XML文件
					if (tLCContSchema.getPrintCount() == -1) { // 如果是重打
						bFlag = getPolDataSetEx(tLCContSchema);
						if (!bFlag) {
							CError.buildErr(this, "保单 " + tLCContSchema.getContNo()
									+ ")准备重新打印数据失败!");
							insertErrLog(mErrors.getFirstError(), tLCContSchema);
							tFail++;
							continue;
						}
						if (!SaveOneContData(tLCContSchema)) {
							CError.buildErr(this, "提交数据库出错！");
							insertErrLog(mErrors.getFirstError(), tLCContSchema);
							tFail++;
							continue;
						}

					} else {
						tXMLDataset = getContDataSet(nLCInsuredSet, tLCContSchema);
						if (tXMLDataset == null) {
							CError.buildErr(this, "保单(" + tLCContSchema.getContNo()
									+ ")准备打印数据失败!");

							insertErrLog(mErrors.getFirstError(), tLCContSchema);
							tFail++;
							continue;
						} else {
							// 添加到局部变量，实现每单独立提交
							tXMLDatasets.addXMLDataset(tXMLDataset);
							if (mOperate.equals("PRINT")
									|| mOperate.equals("REPRINT")) {
								tLCContSchema.setPrintCount(1);
							}
							if (!SaveOneContData(tLCContSchema, tXMLDatasets)) {
								CError.buildErr(this, "提交数据库出错！");
								insertErrLog(mErrors.getFirstError(), tLCContSchema);
								tFail++;
								continue;
							}
						}
					}
					if (tXMLDataset != null) {
						mXMLDatasets.addXMLDataset(tXMLDataset);
					}

					tSucc++;
					mPubLock.unLock();
				}
				// 向页面返回保单集合打印XML数据，用于在页面生成服务器端打印数据文件
				// 准备要保存的数据
				mResult.add(mXMLDatasets);
				mResult.add(mGlobalInput);

				// 测试用，可惜是乱码 ,用GBK的才可以
				// mXMLDatasets.output("D:\\2009\\03\\17\\86131120070210037209.xml");

				mResult.clear();
				mResult.add(mXMLDatasets.getInputStream());
				tTransferData.setNameAndValue("Succ", tSucc);
				tTransferData.setNameAndValue("Fail", tFail);
				mResult.add(tTransferData);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr(this, "保单打印出现异常！");
			tFail++;
		} finally {
			mPubLock.unLock();
		}
		return true;
	}

	/** 重新打印提交 */
	private boolean SaveOneContData(LCContSchema tLCContSchema) {
		MMap map = new MMap();
		VData mResult = new VData();
		PubSubmit tPubSubmit = new PubSubmit();

		// 家庭单财务 查询 家庭单号,跟新此保单下所有的保单
		String rContNo = "";
		String rSQLAdd = "";
		if (("2".equals(tLCContSchema.getFamilyType())
				&& !"".equals(tLCContSchema.getFamilyContNo()) && tLCContSchema
				.getFamilyContNo() != null)
				|| ("1".equals(tLCContSchema.getFamilyType())
						&& !"".equals(tLCContSchema.getFamilyContNo()) && tLCContSchema
						.getFamilyContNo() != null)) {
			rContNo = tLCContSchema.getFamilyContNo();
			rSQLAdd = "' where  FamilyContNo='" + "?rContNo?" + "'";
		} else {
			rContNo = tLCContSchema.getContNo();
			rSQLAdd = "' where  contno='" + "?rContNo?" + "'";
		}
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql("update lccont set printcount='1',ModifyDate='" + "?tCurMakeDate?"
				+ "',ModifyTime='" + "?tCurMakeTime?" + "',Operator='"
				+ "?Operator?" + rSQLAdd);
		sqlbv2.put("tCurMakeDate", tCurMakeDate);
		sqlbv2.put("tCurMakeTime", tCurMakeTime);
		sqlbv2.put("Operator", mGlobalInput.Operator);
		sqlbv2.put("rContNo", rContNo);
		map.put(sqlbv2, "UPDATE");

		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql("update LCContPrintTrace set RePrintFlag='1',MakeDate='" + "?tCurMakeDate?"
				+ "',MakeTime='" + "?tCurMakeTime?" + "',Operator='"   //更新Trace 表的MakeDate 作为重新打印的记录日期
				+ "?Operator?" + "' where  contno='" + "?rContNo?" + "'");
		sqlbv3.put("tCurMakeDate", tCurMakeDate);
		sqlbv3.put("tCurMakeTime", tCurMakeTime);
		sqlbv3.put("Operator", mGlobalInput.Operator);
		sqlbv3.put("rContNo", rContNo);
		map.put(sqlbv3, "UPDATE");
		
//		LCContPrintDB tLCContPrintDB = new LCContPrintDB();
//		tLCContPrintDB.setContNo(rContNo);
//		tLCContPrintDB.getInfo();
//
//		LCContPrintSchema tLLCContPrintSchema = new LCContPrintSchema();
//		tLLCContPrintSchema = tLCContPrintDB.getSchema();
//		
//		LCContRePrintTraceSchema tLCContRePrintTraceSchema= new LCContRePrintTraceSchema();
//		tLCContRePrintTraceSchema.setContNo(rContNo);
//		tLCContRePrintTraceSchema.setPrtNo(tLCContSchema.getPrtNo());
//		tLCContRePrintTraceSchema.setRePrintTimes("1");
//		tLCContRePrintTraceSchema.setContType("1");//个单
//		tLCContRePrintTraceSchema.setMakeDate(tCurMakeDate);
//		tLCContRePrintTraceSchema.setMakeTime(tCurMakeTime);
//		tLCContRePrintTraceSchema.setOperator(mGlobalInput.Operator);
//		tLCContRePrintTraceSchema.setManageCom(tLCContSchema.getManageCom());
//		tLCContRePrintTraceSchema.setModifyDate(tLLCContPrintSchema.getMakeDate()); //保存第一次打印时间
//		tLCContRePrintTraceSchema.setModifyTime(tLLCContPrintSchema.getMakeTime());
//		map.put(tLCContRePrintTraceSchema, "DELETE&INSERT");
		mResult.clear();
		mResult.add(map);
		// 数据提交
		if (!tPubSubmit.submitData(mResult, "")) {
			// @@错误处理
			mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError.buildErr(this, "数据提交失败");
			return false;
		}
		return true;
	}

	/** 正常打印提交 */
	private boolean SaveOneContData(LCContSchema tLCContSchema,
			XMLDatasets tXMLDatasets) {
		
		
//		if(!save(tXMLDatasets))
//		{
//			CError.buildErr(this, "保存保单数据失败");
//			return false;
//		}
		
		MMap map = new MMap();
		VData mResult = new VData();
		PubSubmit tPubSubmit = new PubSubmit();

		// 家庭单财务 查询 家庭单号,跟新此保单下所有的保单
		String rContNo = "";
		String rSQLAdd = "";
		if (("2".equals(tLCContSchema.getFamilyType())
				&& !"".equals(tLCContSchema.getFamilyContNo()) && tLCContSchema
				.getFamilyContNo() != null)
				|| ("1".equals(tLCContSchema.getFamilyType())
						&& !"".equals(tLCContSchema.getFamilyContNo()) && tLCContSchema
						.getFamilyContNo() != null)) {
			rContNo = tLCContSchema.getFamilyContNo();
			rSQLAdd = "' where  FamilyContNo='" + "?rContNo?" + "'";
		} else {
			rContNo = tLCContSchema.getContNo();
			rSQLAdd = "' where  contno='" + "?rContNo?" + "'";
		}
		LCContPrintSchema tLCContPrintSchemaMain = new LCContPrintSchema();
		tLCContPrintSchemaMain.setContNo(rContNo);
		tLCContPrintSchemaMain.setManageCom(tLCContSchema.getManageCom());
		tLCContPrintSchemaMain.setPrtFlag("N");
		tLCContPrintSchemaMain.setPrtTimes(1);
		tLCContPrintSchemaMain.setContType("1");// 个单
		tLCContPrintSchemaMain.setMakeDate(tCurMakeDate);
		tLCContPrintSchemaMain.setMakeTime(tCurMakeTime);
		tLCContPrintSchemaMain.setOperator(mGlobalInput.Operator);
		tLCContPrintSchemaMain.setModifyDate(tCurMakeDate);
		tLCContPrintSchemaMain.setModifyTime(tCurMakeTime);

		InputStream ins = getXMLInputStream(tXMLDatasets);

		tLCContPrintSchemaMain.setContInfo(ins);
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql("delete from LCContPrint where ContNo='"
				+ "?rContNo?" + "'");
		sqlbv4.put("rContNo", rContNo);
		map.put(sqlbv4, "DELETE");
		map.put(tLCContPrintSchemaMain, "BLOBINSERT");
		
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql("update lccont set printcount='1',ModifyDate='" + "?tCurMakeDate?"
				+ "',ModifyTime='" + "?tCurMakeTime?" + "',Operator='"
				+"?Operator?"+ rSQLAdd);
		sqlbv5.put("tCurMakeDate", tCurMakeDate);
		sqlbv5.put("tCurMakeTime", tCurMakeTime);
		sqlbv5.put("Operator", mGlobalInput.Operator);
		sqlbv5.put("rContNo", rContNo);
		map.put(sqlbv5, "UPDATE");
		
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql("update LCContPrintTrace set RePrintFlag='1',MakeDate='" + "?tCurMakeDate?"
				+ "',MakeTime='" + "?tCurMakeTime?" + "',Operator='"   
				+ "?Operator?" + "' where  contno='" + "?rContNo?" + "'");
		sqlbv6.put("tCurMakeDate", tCurMakeDate);
		sqlbv6.put("tCurMakeTime", tCurMakeTime);
		sqlbv6.put("Operator", mGlobalInput.Operator);
		sqlbv6.put("rContNo", rContNo);
		
		map.put(sqlbv6, "UPDATE");

		mResult.clear();
		mResult.add(map);
		// 数据提交
		if (!tPubSubmit.submitData(mResult, "")) {
			// @@错误处理
			mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError.buildErr(this, "数据提交失败");
			return false;
		}
		return true;
	}

	/** *得到XMLDataset的输入流 */
	private InputStream getXMLInputStream(XMLDatasets tXMLDatasets) {
		try {
			Document doc = tXMLDatasets.getDocument();
			Element ele = doc.getRootElement().getChild("DATASET");
			doc = new Document(ele);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			if (!this.output(baos, doc)) {
				baos.close();
				return null;
			}
			baos.close();

			return new ByteArrayInputStream(baos.toByteArray());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 将XML文件的内容输出到一个输出流中
	 * 
	 * @param outputStream
	 *            指定的输出流
	 * @return boolean
	 */
	public boolean output(OutputStream outputStream, Document tDocument) {
		if (outputStream == null) {
			return false;
		}
		try {
			XMLOutputter outputter = new XMLOutputter("", false, "UTF-8");
			outputter.output(tDocument, outputStream);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * 取得暂收费信息
	 * 
	 * @param xmlDataset
	 * @param aLCPolSet
	 */
	private boolean getReceipt(XMLDataset xmlDataset, LCPolSet aLCPolSet,
			LCContSchema tLCContSchema) {
		if (tLCContSchema.getLostTimes() >= 1) { // 遗失补发保单不打印收据
			return false;
		}
		Element table = new Element("Receipt");
		xmlDataset.getRealElement().addContent(table);
		Element head = new Element("HEAD");
		table.addContent(head);

		double dPremSum = 0;
		for (int i = 0; i < aLCPolSet.size(); i++) {
			dPremSum += aLCPolSet.get(i + 1).getPrem();
		}

		head.addContent(new Element("LJTempFee.PayMoneySum")
				.addContent(format(dPremSum)));
		head.addContent(new Element("LJTempFee.PayMoneySum1").addContent(PubFun
				.getChnMoney(dPremSum).substring(3,
						PubFun.getChnMoney(dPremSum).length())));

		// 家庭单财务 查询 家庭单号
		String rContNo = "";
		if (("2".equals(tLCContSchema.getFamilyType())
				&& !"".equals(tLCContSchema.getFamilyContNo()) && tLCContSchema
				.getFamilyContNo() != null)
				|| ("1".equals(tLCContSchema.getFamilyType())
						&& !"".equals(tLCContSchema.getFamilyContNo()) && tLCContSchema
						.getFamilyContNo() != null)) {
			rContNo = tLCContSchema.getFamilyContNo();
		} else {
			rContNo = tLCContSchema.getContNo();
		}
		String strSQL = "SELECT TempFeeNo, Operator FROM LJTempFee WHERE (  OtherNoType = '0' OR OtherNoType = '4' or OtherNoType = '3' ) AND OtherNo = '"
				+ "?rContNo?" + "'";
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql(strSQL);
		sqlbv7.put("rContNo", rContNo);
		SSRS ssRs = new ExeSQL().execSQL(sqlbv7);

		if (ssRs.ErrorFlag || (ssRs.MaxRow == 0)) {
			CError.buildErr(this, "找不到该个人保单的暂交费数据!");
			return false;
		}
		head.addContent(new Element("LJTempFee.Handler").addContent(ssRs
				.GetText(1, 2)));
		head.addContent(new Element("LJTempFee.TempFeeNo").addContent(ssRs
				.GetText(1, 1)));

		for (int i = 1; i <= aLCPolSet.size(); i++) {
			Element row = new Element("ROW");
			table.addContent(row);

			row.addContent(new Element("COL1").addContent(this
					.getRiskName(aLCPolSet.get(i))));
			row.addContent(new Element("COL2").addContent(Integer
					.toString(aLCPolSet.get(i).getPayIntv())));
			row.addContent(new Element("COL3").addContent(format(aLCPolSet.get(
					i).getPrem())));
		}
		return true;
	}

	/**
	 * 正常打印的取数流程
	 * 
	 * @param nLCInsuredSet
	 *            主险附加险保单数据。其中，第一个元素是主险保单，其它元素是附加险保单。
	 * 
	 * @return
	 */
	private XMLDataset getContDataSet(LCInsuredSet nLCInsuredSet,
			LCContSchema tLCContSchema) {

		try {
			LCPolDB tLCPolDB = new LCPolDB();
			LCPolSchema mLCPolSchema = new LCPolSchema();
			tLCPolDB.setContNo(tLCContSchema.getContNo());

			String Sql = "SELECT * FROM LCPol WHERE ContNo='"
					+"?ContNo?"
					+ "' and MainPolNo=PolNo order by polno";
			SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
			sqlbv8.sql(Sql);
			sqlbv8.put("ContNo", tLCContSchema.getContNo());
			LCPolSet rLCPolSet = tLCPolDB.executeQuery(sqlbv8);
			if (rLCPolSet.size() <= 0 || rLCPolSet == null) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LCPolF1P_IDGFBL";
				tError.functionName = "getPolDataSet";
				tError.errorMessage = "查询主险号时产生错误！";
				this.mErrors.addOneError(tError);
				return null;
			} else {
				mLCPolSchema = rLCPolSet.get(1);
			}

			XMLDataset xmlDataset = new XMLDataset("CONTROL");

			/**
			 * *********************************CONTROL
			 * START************************************
			 */
			String tSQL = "select 1 from lcpol where contno='"
					+ "?contno?"
					+ "' and  riskcode in (select riskcode from lmriskapp where risktype3='4')";
			SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
			sqlbv9.sql(tSQL);
			sqlbv9.put("contno", tLCContSchema.getContNo());
			String tFlag = tExeSQL.getOneValue(sqlbv9);
			// <!--用于区分是否银代万能 C-非银代万能保单,Y-银代万能保单-->
			if ("".equals(tFlag)) {
				xmlDataset.setRiskType("C");
			} else {
				xmlDataset.setRiskType("Y");
			}
			// <FamilyType>保单类型</FamilyType><!--0-普通保单，1-家庭单，2-多主险保单-->
			String tContNo = "";
			if (("1".equals(tLCContSchema.getFamilyType())
					&& !"".equals(tLCContSchema.getFamilyContNo()) && tLCContSchema
					.getFamilyContNo() != null)) {
				xmlDataset.setFamilyType("1");
				tContNo = tLCContSchema.getFamilyContNo();
			} else if (("2".equals(tLCContSchema.getFamilyType())
					&& !"".equals(tLCContSchema.getFamilyContNo()) && tLCContSchema
					.getFamilyContNo() != null)) {
				xmlDataset.setFamilyType("2");
				tContNo = tLCContSchema.getFamilyContNo();
			} else {
				xmlDataset.setFamilyType("0");
				tContNo = tLCContSchema.getContNo();
			}

			xmlDataset.setSaleChnl(tLCContSchema.getSaleChnl());

			/**
			 * *********************************CONTROL
			 * END************************************
			 */

			xmlDataset.addDataObject(new XMLDataTag("LCCont.ContNo", tContNo));
			xmlDataset.addDataObject(new XMLDataTag("LCCont.PrtNo",
					tLCContSchema.getPrtNo()));
			xmlDataset.addDataObject(new XMLDataTag("LCCont.ManageCom",
					tLCContSchema.getManageCom()));

			xmlDataset.addDataObject(new XMLDataTag("LCCont.SignCom", BqNameFun
					.getCBranch(tLCContSchema.getManageCom().substring(0, 4))));
			xmlDataset.addDataObject(new XMLDataTag("LCCont.SignDate",
					BqNameFun.getChDate(tLCContSchema.getSignDate())));
			xmlDataset.addDataObject(new XMLDataTag("LCCont.SignTime",
					tLCContSchema.getSignTime()));
			xmlDataset.addDataObject(new XMLDataTag("PrintCount", tLCContSchema
					.getPrintCount()));
			
	        if (tLCContSchema.getLostTimes() > 0)
	        {
	            xmlDataset.addDataObject(new XMLDataTag("PrintDateRemark", "保单补发日期:"));
	            xmlDataset.addDataObject(new XMLDataTag("PrintDate",
	                    changeDate(PubFun.getCurrentDate())));
	            xmlDataset.addDataObject(new XMLDataTag("LostTimesRemark", "保单补发次数:"));
	            xmlDataset.addDataObject(new XMLDataTag("LostTimes",
	            		tLCContSchema.getLostTimes()));
	        }else
	        {
	            xmlDataset.addDataObject(new XMLDataTag("LostTimes",
	            		"0"));
	        }
			String strSQL1 = "select (case when (select 1 From lcpol where polno='"
					+"?polno?"
					+ "' and polapplydate<to_Date((Select trim(sysvarvalue) "
					+ "From LDSysVar Where SysVar='PrtTermTime'),'YYYY-MM-DD')) is not null then (select 1 From lcpol where polno='"
					+"?polno?"
					+ "' and polapplydate<to_Date((Select trim(sysvarvalue) "
					+ "From LDSysVar Where SysVar='PrtTermTime'),'YYYY-MM-DD'))  else 0 end) from dual";
			SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
			sqlbv10.sql(strSQL1);
			sqlbv10.put("polno", mLCPolSchema.getPolNo());
			ExeSQL tExeSQL1 = new ExeSQL();
			if (Integer.parseInt(tExeSQL1.getOneValue(sqlbv10)) == 0) {
				strSQL1 = "select (case when (select 1 From lcpol where polno='"
					+ "?polno?"
					+ "' and polapplydate<to_Date((Select trim(sysvarvalue) "
					+ "From LDSysVar Where SysVar='PrtTermTimeNew'),'YYYY-MM-DD')) is not null then (select 1 From lcpol where polno='"
					+ "?polno?"
					+ "' and polapplydate<to_Date((Select trim(sysvarvalue) "
					+ "From LDSysVar Where SysVar='PrtTermTimeNew'),'YYYY-MM-DD'))  else 0 end) from dual";
				SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
				sqlbv11.sql(strSQL1);
				sqlbv11.put("polno", mLCPolSchema.getPolNo());
				if(Integer.parseInt(tExeSQL1.getOneValue(sqlbv11)) == 1){
					xmlDataset.addDataObject(new XMLDataTag("PrtTermFlag", "NEW"));
				}else{
					//2010-2-1部分险种启用第四版条款  首先判断该主险是否启用第四版
					strSQL1 = "select (case when (select 1 from ldcode where codetype='PrtTermTimeFOU'"
								+ " and code='"+"?code?"+"') is not null then (select 1 from ldcode where codetype='PrtTermTimeFOU'"
								+ " and code='"+"?code?"+"') else 0 end) "
								+ " from dual";
					SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
					sqlbv12.sql(strSQL1);
					sqlbv12.put("code", mLCPolSchema.getRiskCode());
					if(Integer.parseInt(tExeSQL1.getOneValue(sqlbv12)) == 1){
						strSQL1 = "select (case when (select 1 From lcpol where polno='"
							+"?polno?"
							+ "' and polapplydate>=to_Date((Select trim(sysvarvalue) "
							+ "From LDSysVar Where SysVar='PrtTermTimeFOU'),'YYYY-MM-DD')) is not null then (select 1 From lcpol where polno='"
							+"?polno?"
							+ "' and polapplydate>=to_Date((Select trim(sysvarvalue) "
							+ "From LDSysVar Where SysVar='PrtTermTimeFOU'),'YYYY-MM-DD'))  else 0 end) from dual";
						SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
						sqlbv13.sql(strSQL1);
						sqlbv13.put("polno", mLCPolSchema.getPolNo());
						if(Integer.parseInt(tExeSQL1.getOneValue(sqlbv13)) == 1){
							xmlDataset.addDataObject(new XMLDataTag("PrtTermFlag", "FOU"));
						} else { 
							xmlDataset.addDataObject(new XMLDataTag("PrtTermFlag", "TRD"));
						}
					} else {
						xmlDataset.addDataObject(new XMLDataTag("PrtTermFlag", "TRD"));
					}
				}
			} else {
				xmlDataset.addDataObject(new XMLDataTag("PrtTermFlag", "OLD"));
			}
			xmlDataset.addDataObject(new XMLDataTag("LCCont.AppntName",
					tLCContSchema.getAppntName()));
			xmlDataset.addDataObject(new XMLDataTag("LCCont.AppntNo",
					tLCContSchema.getAppntNo()));
			xmlDataset.addDataObject(new XMLDataTag("LCCont.PayIntv",
					mLCPolSchema.getPayIntv()));
			String tCValiDateSql = "select (case when (select 1 From lccont where prtno='"
				+ "?prtno?"
				+ "' and polapplydate<to_Date((Select trim(sysvarvalue) "
				+ "From LDSysVar Where SysVar='ContOrganizeDate'),'YYYY-MM-DD')) is not null then (select 1 From lccont where prtno='"
				+ "?prtno?"
				+ "' and polapplydate<to_Date((Select trim(sysvarvalue) "
				+ "From LDSysVar Where SysVar='ContOrganizeDate'),'YYYY-MM-DD'))  else 0 end) from dual";
			SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
			sqlbv13.sql(tCValiDateSql);
			sqlbv13.put("prtno", mLCPolSchema.getPrtNo());
			if(Integer.parseInt(tExeSQL1.getOneValue(sqlbv13)) == 1){
				xmlDataset.addDataObject(new XMLDataTag("LCCont.CValiDate",
						BqNameFun.getChDate(tLCContSchema.getCValiDate()) + "零时"));
			}else{
				//2009年10月1日后去掉零时两个字
				xmlDataset.addDataObject(new XMLDataTag("LCCont.CValiDate",
						BqNameFun.getChDate(tLCContSchema.getCValiDate())));
			}
			if(tLCContSchema.getOrganizeDate()!=null&&!"".equals(tLCContSchema.getOrganizeDate())){
				//09-09-16  增加条件 投保申请日期如果为10月1日后才会传入合同成立日期
				String tOrgaDateSql = "select (case when (select 1 From lccont where prtno='"
					+ "?prtno?"
					+ "' and polapplydate<to_Date((Select trim(sysvarvalue) "
					+ "From LDSysVar Where SysVar='ContOrganizeDate'),'YYYY-MM-DD')) is not null then (select 1 From lccont where prtno='"
					+ "?prtno?"
					+ "' and polapplydate<to_Date((Select trim(sysvarvalue) "
					+ "From LDSysVar Where SysVar='ContOrganizeDate'),'YYYY-MM-DD'))  else 0 end) from dual";
				SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
				sqlbv14.sql(tOrgaDateSql);
				sqlbv14.put("prtno", mLCPolSchema.getPrtNo());
				if(Integer.parseInt(tExeSQL1.getOneValue(sqlbv14)) == 1){
					//2009年10月1日前传入为空值
					xmlDataset.addDataObject(new XMLDataTag("LCCont.OrganizeDate","" ));//合同成立日期
				}else{
					xmlDataset.addDataObject(new XMLDataTag("LCCont.OrganizeDate",
							BqNameFun.getChDate(tLCContSchema.getOrganizeDate()) ));//合同成立日期
				}
			}else{
				xmlDataset.addDataObject(new XMLDataTag("LCCont.OrganizeDate","" ));//合同成立日期
			}
			xmlDataset.addDataObject(new XMLDataTag("LCCont.FirstPayDate",
					(tLCContSchema.getFirstPayDate())));

			// 5.3系统的处理逻辑
			if (mLCPolSchema.getPayIntv() == 0) {
				mLCPolSchema.setPaytoDate(mLCPolSchema.getCValiDate());
			} else {
				mLCPolSchema.setPaytoDate(prevDay(mLCPolSchema.getPaytoDate()));
			}

			if (mLCPolSchema.getPayIntv() != 0) {
				mLCPolSchema
						.setPayEndDate(prevDay(mLCPolSchema.getPayEndDate()));
			} else {
				mLCPolSchema
						.setPayEndDate(prevDay(mLCPolSchema.getCValiDate()));
			}
			xmlDataset.addDataObject(new XMLDataTag("LCCont.PaytoDate",
					BqNameFun.getChDate((mLCPolSchema.getPaytoDate()))));
			xmlDataset.addDataObject(new XMLDataTag("LCCont.PayEndDate",
					(mLCPolSchema.getPayEndDate()))); // 存放主险的缴费止期

			String tSunPremSQL="select sum(prem) from lcpol where appflag='1' and prtno='"+"?prtno?"+"'";
			SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
			sqlbv15.sql(tSunPremSQL);
			sqlbv15.put("prtno", tLCContSchema.getPrtNo());
			xmlDataset.addDataObject(new XMLDataTag("LCCont.SumPrem", String
					.valueOf(BqNameFun.getFormat(tExeSQL1.getOneValue(sqlbv15)))));
			xmlDataset.addDataObject(new XMLDataTag("LCCont.AgentCode",
					tLCContSchema.getAgentCode()));

			xmlDataset.addDataObject(new XMLDataTag("LCCont.AgentName",
					BqNameFun.getAgentName(tLCContSchema.getAgentCode())));
			if (!"".equals(tLCContSchema.getAgentCom())
					&& tLCContSchema.getAgentCom() != null) {

				LAComSchema tLAComSchema = BqNameFun
						.getAgentComName(tLCContSchema.getAgentCom());
				if (tLAComSchema == null) {
					CError.buildErr(this, "获取保单代理信息失败!");
					return null;
				}
				xmlDataset.addDataObject(new XMLDataTag("LCCont.AgentCom",
						tLAComSchema.getName()));
			} else {
				xmlDataset.addDataObject(new XMLDataTag("LCCont.AgentCom", ""));
			}

			LDComSchema rLDComSchema = BqNameFun.getBranchComInfo(tLCContSchema
					.getManageCom());

			if (rLDComSchema == null) {
				CError.buildErr(this, "获取保单管理机构信息失败!");
				return null;
			}

			xmlDataset.addDataObject(new XMLDataTag("ManageCom.Phone",
					rLDComSchema.getPhone()));
			xmlDataset.addDataObject(new XMLDataTag("LCCont.Handler",
					tLCContSchema.getHandler()));

			LDComSchema tLDComSchema = new LDComSchema();
			if (tLCContSchema.getManageCom().length() >= 4) {
				tLDComSchema = BqNameFun.getBranchComInfo(tLCContSchema
						.getManageCom().substring(0, 4));
			} else {
				// 获取总公司
				tLDComSchema = BqNameFun.getBranchComInfo(tLCContSchema
						.getManageCom());
			}

			if (tLDComSchema == null) {
				CError.buildErr(this, "获取公司信息失败!");
				return null;
			}
			xmlDataset.addDataObject(new XMLDataTag("LCCont.BranchAddress",
					tLDComSchema.getAddress()));
			xmlDataset.addDataObject(new XMLDataTag("LCCont.Branch",
					tLDComSchema.getName()));

			String strInjuryGetFlag = "0";
			String strHospitalFlag = "0";
			String strRiskCode = "";
			String strTemp = "";

			for (int i = 0; i < rLCPolSet.size(); i++) {

				strRiskCode = rLCPolSet.get(i + 1).getRiskCode();

				LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();

				tLMRiskAppDB.setRiskCode(strRiskCode);
				if (!tLMRiskAppDB.getInfo()) {
					mErrors.copyAllErrors(tLMRiskAppDB.mErrors);
					return null;
				}

				strTemp = tLMRiskAppDB.getNeedPrintGet();
				if ((strTemp != null) && strTemp.equals("1")) {
					strInjuryGetFlag = "1";
				}

				strTemp = tLMRiskAppDB.getNeedPrintHospital();
				if ((strTemp != null) && strTemp.equals("1")) {
					strHospitalFlag = "1";
				}
			}

			// 加入医院列表和伤残给付责任表的信息
			xmlDataset.addDataObject(new XMLDataTag("InjuryGetFlag",
					strInjuryGetFlag));
			xmlDataset.addDataObject(new XMLDataTag("HospitalFlag",
					strHospitalFlag));
			String tSql = "SELECT * FROM LCPol WHERE ContNo='"
					+ "?ContNo?"
					+ "' and appflag='1' order by polno";
			SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
			sqlbv16.sql(tSql);
			sqlbv16.put("ContNo", tLCContSchema.getContNo());
			LCPolSet oLCPolSet = tLCPolDB.executeQuery(sqlbv16);
			boolean tExistsFlag = false;
			if (oLCPolSet.size() > 1) {
				for (int k = 1; k <= oLCPolSet.size(); k++) {
					LCPolSchema rLCPolSchema = oLCPolSet.get(k);
					// 如果存在如下附加险则不允许给出附加提前特约
					if ("121508".equals(rLCPolSchema.getRiskCode())
							|| "121509".equals(rLCPolSchema.getRiskCode())
							|| "121510".equals(rLCPolSchema.getRiskCode())) {
						xmlDataset.addDataObject(new XMLDataTag(
								"PrintForeGetSpec", "0"));
						tExistsFlag = true;
						break;
					}
				}
			}
			if (!tExistsFlag) {
				//09-12-23  留下原逻辑以供以后查询
				//本次修改为：  优先判断产品是否描述为可以取消 1-可以取消
				//然后判断人工核保是否取消了打印1-取消
				//标签的值的含义为：1-打印  0-不打印(与展鹏那边保持一致)
//				if ("".equals(mLCPolSchema.getCancleForegetSpecFlag())
//						|| mLCPolSchema.getCancleForegetSpecFlag() == null) {
//					LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
//					tLMRiskAppDB.setRiskCode(mLCPolSchema.getRiskCode());
//					tLMRiskAppDB.getInfo();
//					if ((tLMRiskAppDB.getCancleForeGetSpecFlag() != null)
//							&& tLMRiskAppDB.getCancleForeGetSpecFlag().equals(
//									"1")) {
//						if ((tLCContSchema.getPassword() != null)
//								&& tLCContSchema.getPassword().equals("1")) {
//							xmlDataset.addDataObject(new XMLDataTag(
//									"PrintForeGetSpec", "0"));
//						} else {
//							xmlDataset.addDataObject(new XMLDataTag(
//									"PrintForeGetSpec", "1"));
//						}
//					} else {
//						xmlDataset.addDataObject(new XMLDataTag(
//								"PrintForeGetSpec", "0"));
//					}
//				} else {
//					xmlDataset.addDataObject(new XMLDataTag("PrintForeGetSpec",
//							mLCPolSchema.getCancleForegetSpecFlag()));
//				}
				LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
				tLMRiskAppDB.setRiskCode(mLCPolSchema.getRiskCode());
				tLMRiskAppDB.getInfo();
				if ((tLMRiskAppDB.getCancleForeGetSpecFlag() != null)
						&& tLMRiskAppDB.getCancleForeGetSpecFlag().equals(
						"1")) {
					if ("1".equals(mLCPolSchema.getCancleForegetSpecFlag())) {
						xmlDataset.addDataObject(new XMLDataTag(
									"PrintForeGetSpec", "0"));
					} else {
						xmlDataset.addDataObject(new XMLDataTag("PrintForeGetSpec",
								"1"));
					}
				} else {
					xmlDataset.addDataObject(new XMLDataTag(
							"PrintForeGetSpec", "0"));
				}
			}

			Element elementLCInsureds = new Element("LCInsureds");

			for (int k = 1; k <= nLCInsuredSet.size(); k++) {
				LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
				tLCInsuredSchema = nLCInsuredSet.get(k);
				Element elementLCInsured = new Element("LCInsured");

				Element elementLCInsuredContNo = new Element("LCInsured.ContNo");
				elementLCInsured.addContent(elementLCInsuredContNo);
				elementLCInsured.getChild("LCInsured.ContNo").addContent(
						tLCInsuredSchema.getContNo());

				Element elementInsuredSequence = new Element(
						"LCInsured.InsuredSequence");
				elementLCInsured.addContent(elementInsuredSequence);
				elementLCInsured.getChild("LCInsured.InsuredSequence")
						.addContent(String.valueOf(k));

				// 被保人信息
				Element eleLCInsuredInfo = new Element("LCInsuredInfo");

				eleLCInsuredInfo.addContent(new Element(
						"LCInsured.InsuredGrade").addContent(tLCInsuredSchema
						.getRelationToMainInsured())); // 00是主被保险人，否则为其他
				eleLCInsuredInfo.addContent(new Element(
						"LCInsured.RelationToAppnt")
						.addContent(tLCInsuredSchema.getRelationToAppnt())); // 00是投保人自己人，否则为其他

				eleLCInsuredInfo.addContent(new Element("LCInsured.InsuredNo")
						.addContent(tLCInsuredSchema.getInsuredNo())); //

				eleLCInsuredInfo.addContent(new Element("LCInsured.Name")
						.addContent(tLCInsuredSchema.getName())); //		

				eleLCInsuredInfo.addContent(new Element("LCInsured.IDType")
						.addContent(tLCInsuredSchema.getIDType())); //

				eleLCInsuredInfo.addContent(new Element("LCInsured.IDNo")
						.addContent(tLCInsuredSchema.getIDNo())); //	

				elementLCInsured.addContent(eleLCInsuredInfo);

				Element eleLCBnfDes = new Element("LCBnfDes");

				Element eleLCBnfInfoAlive = new Element("LCBnfInfoAlive");

				LCBnfDB tLCBnfDB = new LCBnfDB();
				LCBnfSet tLCBnfSet = new LCBnfSet();
				
				// 生存受益人 以主险为准
				String tBnfTypeScSQL="select * from lcbnf  a where a.contno='"+"?contno?"
				                     +"' and a.InsuredNo='"+"?InsuredNo?"+"' and a.bnftype='0' and "
				                     +" exists (select 'X' from lcpol b where  b.polno=b.mainpolno and a.polno=b.polno and b.appflag='1' and b.contno='"+"?contno?"+"')";				
				SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
				sqlbv17.sql(tBnfTypeScSQL);
				sqlbv17.put("contno", tLCInsuredSchema.getContNo());
				sqlbv17.put("InsuredNo", tLCInsuredSchema.getInsuredNo());
				tLCBnfSet = tLCBnfDB.executeQuery(sqlbv17);
				for (int i = 1; i <= tLCBnfSet.size(); i++) {
					LCBnfSchema tLCBnfSchema = new LCBnfSchema();
					tLCBnfSchema = tLCBnfSet.get(i);
					Element eleLCBnfInfo = new Element("LCBnfInfo");
					eleLCBnfInfo.addContent(new Element("LCBnf.BnfLot")
							.addContent(String
									.valueOf(tLCBnfSchema.getBnfLot()))); //				
					eleLCBnfInfo.addContent(new Element("LCBnf.Name")
							.addContent(tLCBnfSchema.getName())); //					
					eleLCBnfInfo.addContent(new Element("LCBnf.BnfType")
							.addContent(tLCBnfSchema.getBnfType())); //				
					eleLCBnfInfo.addContent(new Element("LCBnf.BnfGrade")
							.addContent(tLCBnfSchema.getBnfGrade())); //
					eleLCBnfInfoAlive.addContent(eleLCBnfInfo);
				}
				eleLCBnfDes.addContent(eleLCBnfInfoAlive);

				Element eleLCBnfInfoDead = new Element("LCBnfInfoDead");

				LCBnfSet rLCBnfSet = new LCBnfSet();
				LCBnfDB cLCBnfDB = new LCBnfDB();
				
				// 身故受益人 以主险为准
				String tBnfTypeSWSQL="select * from lcbnf  a where a.contno='"+"?contno?"
				                     +"' and a.InsuredNo='"+"?InsuredNo?"+"' and a.bnftype='1' and "
				                     +" exists ( select 'X' from lcpol b where  b.polno=b.mainpolno and a.polno=b.polno and b.appflag='1' and b.contno='"+"?contno?"+"')";				
				SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
				sqlbv18.sql(tBnfTypeSWSQL);
				sqlbv18.put("contno", tLCInsuredSchema.getContNo());
				sqlbv18.put("InsuredNo", tLCInsuredSchema.getInsuredNo());
				rLCBnfSet = cLCBnfDB.executeQuery(sqlbv18);
				for (int i = 1; i <= rLCBnfSet.size(); i++) {
					LCBnfSchema tLCBnfSchema = new LCBnfSchema();
					tLCBnfSchema = rLCBnfSet.get(i);
					Element eleLCBnfInfo = new Element("LCBnfInfo");
					eleLCBnfInfo.addContent(new Element("LCBnf.BnfLot")
							.addContent(String
									.valueOf(tLCBnfSchema.getBnfLot()))); //				
					eleLCBnfInfo.addContent(new Element("LCBnf.Name")
							.addContent(tLCBnfSchema.getName())); //					
					eleLCBnfInfo.addContent(new Element("LCBnf.BnfType")
							.addContent(tLCBnfSchema.getBnfType())); //				
					eleLCBnfInfo.addContent(new Element("LCBnf.BnfGrade")
							.addContent(tLCBnfSchema.getBnfGrade())); //
					eleLCBnfInfoDead.addContent(eleLCBnfInfo);
					// eleLCBnfDes.addChild(eleLCBnfInfoDead);
				}
				eleLCBnfDes.addContent(eleLCBnfInfoDead);
				// 添加受益人信息
				elementLCInsured.addContent(eleLCBnfDes);

				// 领取信息,体现主险的领取信息
				Element eleGetDes = new Element("GetDes");

				String strSQL = "select distinct gettodate,getenddate from lcget  a where getdutycode in "
						+ "(select getdutycode from lmdutyget where gettype1='1') "
						+ " and exists (select 'X' "
						+" from lcpol b  where contno = '" +"?contno?"+ "' and a.contno=b.contno "
						+" and  b.polno=b.mainpolno and  riskcode in (select riskcode from lmriskapp where RiskType4 = '4'))";
				SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
				sqlbv19.sql(strSQL);
				sqlbv19.put("contno", tLCInsuredSchema.getContNo());
				ExeSQL tExeSQL = new ExeSQL();
				SSRS tSSRS = new SSRS();
				tSSRS = tExeSQL.execSQL(sqlbv19);
				if (tSSRS.getMaxRow() > 0) {
					eleGetDes.addContent(new Element("GetAliveAge")
							.addContent(String.valueOf(PubFun.calInterval(
									tLCInsuredSchema.getBirthday(), tSSRS
											.GetText(1, 1), "Y"))));
					eleGetDes
							.addContent(new Element("GetAliveYears")
									.addContent(String.valueOf(PubFun
											.calInterval(tSSRS.GetText(1, 1),
													tSSRS.GetText(1, 2), "Y"))));
					eleGetDes.addContent(new Element("GetYear").addContent(String
							.valueOf(mLCPolSchema.getGetYear()))); //	

					eleGetDes.addContent(new Element("GetYearFlag")
							.addContent(BqNameFun.NVL(
									mLCPolSchema.getGetYearFlag(), "Y"))); //

				}

				// if ((tLMRiskAppDB.getRiskType2() == null)
				// || !tLMRiskAppDB.getRiskType2().equals("Y"))
				// {
				// tLCPolSchema.setGetYear(-100);
				// tLCPolSchema.setGetYearFlag(null);
				// }


				eleGetDes.addContent(new Element("LiveGetMode")
						.addContent(BqNameFun.NVL(
								mLCPolSchema.getLiveGetMode(), "1"))); //	

				String tHasBonusFlag = "select (case when bonusgetmode is not null then bonusgetmode  else '1' end) from lcpol where contno='"
						+ "?contno?"
						+ "' and riskcode in (select riskcode from LMRiskApp where bonusflag = '1') ";
				SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
				sqlbv20.sql(tHasBonusFlag);
				sqlbv20.put("contno", tLCInsuredSchema.getContNo());
				String tBonusGetMode = tExeSQL.getOneValue(sqlbv20);
				if (!"".equals(tBonusGetMode)) {
					eleGetDes.addContent(new Element("BonusFlag")
							.addContent("1")); //				
				} else {
					eleGetDes.addContent(new Element("BonusFlag")
							.addContent("0")); //						
				}

				eleGetDes.addContent(new Element("BonusGetMode")
						.addContent(tBonusGetMode)); //	

				elementLCInsured.addContent(eleGetDes);

				// 险种信息
				Element eleRiskDes = new Element("RiskDes");

				LCPolSet tLCPolSet = new LCPolSet();
				LCPolDB cLCPolDB = new LCPolDB();
				String tPSQL = "select * from lcpol where appflag='1' and contno='"
						+ "?contno?"
						+ "' and InsuredNo='"
						+ "?InsuredNo?" + "' and riskcode not in ('111801','121601') order by polno";
				SQLwithBindVariables sqlbv21 = new SQLwithBindVariables();
				sqlbv21.sql(tPSQL);
				sqlbv21.put("contno", tLCInsuredSchema.getContNo());
				sqlbv21.put("InsuredNo", tLCInsuredSchema.getInsuredNo());
				tLCPolSet = cLCPolDB.executeQuery(sqlbv21);
				
				String tcSQL = "select * from lcpol where appflag='1' and contno='"
					+ "?contno?"
					+ "' and InsuredNo='"
					+ "?InsuredNo?" + "' and riskcode in ('111801','121601') order by polno";
				SQLwithBindVariables sqlbv22 = new SQLwithBindVariables();
				sqlbv22.sql(tcSQL);
				sqlbv22.put("contno", tLCInsuredSchema.getContNo());
				sqlbv22.put("InsuredNo", tLCInsuredSchema.getInsuredNo());
				//为了实现下种排序的要求，主险总是在第一位，后续都是附加险，对于特殊的险种111801，121601 排在最后			
				LCPolSet qLCPolSet = cLCPolDB.executeQuery(sqlbv22);
				
				tLCPolSet.add(qLCPolSet);
				for (int i = 1; i <= tLCPolSet.size(); i++) {
					LCPolSchema tLCPolSchema = new LCPolSchema();
					tLCPolSchema = tLCPolSet.get(i);
					Element eleRiskInfo = new Element("RiskInfo");
					eleRiskInfo.addContent(new Element("LCPol.RiskCode")
							.addContent(String.valueOf(tLCPolSchema
									.getRiskCode()))); //				
					eleRiskInfo.addContent(new Element("LCPol.RiskName")
							.addContent(BqNameFun.getRiskShortName(tLCPolSchema
									.getRiskCode()))); //

		            LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
		            tLMRiskAppDB.setRiskCode(tLCPolSchema.getRiskCode());
		            if (!tLMRiskAppDB.getInfo())
		            {
						CError.buildErr(this, "险种描述取数失败!");
						return null;
		            }

					eleRiskInfo.addContent(new Element("LCPol.SubRiskFlag")
							.addContent(tLMRiskAppDB.getSubRiskFlag())); //				
					eleRiskInfo.addContent(new Element("LCPol.RiskVersion")
							.addContent(tLCPolSchema.getRiskVersion())); //

					String tInsuYear = "";
					String tIsurYearFLag="";
					if (tLCPolSchema.getInsuYear() == 1000&&"A".equals(tLCPolSchema.getInsuYearFlag())) {
						tInsuYear = "终身";
						tIsurYearFLag="";
					} else {
						tInsuYear = String.valueOf(tLCPolSchema.getInsuYear());
						tIsurYearFLag=tLCPolSchema.getInsuYearFlag();
					}

					eleRiskInfo.addContent(new Element("LCPol.InsuYear")
							.addContent(tInsuYear)); //
					eleRiskInfo.addContent(new Element("LCPol.InsuYearFlag")
							.addContent(tIsurYearFLag)); //
					

					
		            // 依据需求，缴费年期全部统一为年，如果按岁则折算成年
		            if (tLCPolSchema.getPayIntv() != 0)
		            {
						eleRiskInfo.addContent(new Element("LCPol.PayEndYear")
						.addContent(String.valueOf(PubFun.calInterval(tLCPolSchema.getCValiDate(), tLCPolSchema.getPayEndDate(), "Y")))); //
				        eleRiskInfo.addContent(new Element("LCPol.PayEndYearFlag")
						.addContent("Y")); 
		            }
		            else if(tLCPolSchema.getPayIntv()==0 && tLCPolSchema.getRnewFlag()==-1)//对趸交的保单，但续保的也要计算年期
		            {
						eleRiskInfo.addContent(new Element("LCPol.PayEndYear")
						.addContent(String.valueOf(PubFun.calInterval(tLCPolSchema.getCValiDate(), tLCPolSchema.getPayEndDate(), "Y")))); //
				        eleRiskInfo.addContent(new Element("LCPol.PayEndYearFlag")
						.addContent("Y")); 
		            }else   //对趸交的保单，将交费期满日设为保单的生效日期
		            {
						eleRiskInfo.addContent(new Element("LCPol.PayEndYear")
						.addContent("趸交")); //
				        eleRiskInfo.addContent(new Element("LCPol.PayEndYearFlag")
						.addContent("")); 		            	
		            }

					eleRiskInfo.addContent(new Element("LCPol.Amnt")
							.addContent(String.valueOf(format(tLCPolSchema
									.getAmnt())))); //
					eleRiskInfo.addContent(new Element("LCPol.Prem")
							.addContent(String.valueOf(format(tLCPolSchema
									.getPrem())))); //
					if (tLCPolSchema.getPayIntv() != 0) {
						tLCPolSchema.setPayEndDate(prevDay(tLCPolSchema
								.getPayEndDate()));
					} else {
						tLCPolSchema.setPayEndDate(prevDay(tLCPolSchema
								.getCValiDate()));
					}
					eleRiskInfo.addContent(new Element("LCPol.PayEndDate")
							.addContent((tLCPolSchema.getPayEndDate()))); //
					eleRiskInfo.addContent(new Element("LCPol.PayIntv")
							.addContent(String.valueOf(tLCPolSchema
									.getPayIntv()))); //
					// eleRiskDes.addChild(eleRiskInfo);
					eleRiskDes.addContent(eleRiskInfo);
				}

				elementLCInsured.addContent(eleRiskDes);
				elementLCInsureds.addContent(elementLCInsured);
				// 添加一个被保人信息
			}

			xmlDataset.getRealElement().addContent(elementLCInsureds);

			Element eleLCCSpec = new Element("LCCSpec");
			LCCSpecSet tLCCSpecSet = new LCCSpecSet();
			LCCSpecDB tLCCSpecDB = new LCCSpecDB();
			
			String tSpecSQL="SELECT * FROM LCCSpec WHERE  contno='"+"?contno?"+"'"
				+ " ORDER BY ModifyDate, ModifyTime DESC";
			SQLwithBindVariables sqlbv23 = new SQLwithBindVariables();
			sqlbv23.sql(tSpecSQL);
			sqlbv23.put("contno", tLCContSchema.getContNo());
			tLCCSpecSet = tLCCSpecDB.executeQuery(sqlbv23);
			if (tLCCSpecSet.size() > 0) {
				for (int i = 1; i <= tLCCSpecSet.size(); i++) {
					LCCSpecSchema tLCCSpecSchema = new LCCSpecSchema();
					tLCCSpecSchema = tLCCSpecSet.get(i);
					Element eleSpecInfo = new Element("LCCSpecInfo");
					eleSpecInfo.addContent(new Element("LCCSpec.SpecNo")
							.addContent(String.valueOf(tLCCSpecSchema
									.getSerialNo()))); //				
					eleSpecInfo.addContent(new Element("LCCSpec.CustomerNo")
							.addContent(tLCCSpecSchema.getCustomerNo())); //

					eleSpecInfo.addContent(new Element("LCCSpec.SpecType")
							.addContent(tLCCSpecSchema.getSpecType())); //				
					eleSpecInfo.addContent(new Element("LCCSpec.SpecCode")
							.addContent(tLCCSpecSchema.getSpecCode())); //
					if(tLCCSpecSet.size()>1)
					{
						eleSpecInfo.addContent(new Element("LCCSpec.SpecContent")
						.addContent(String.valueOf(i+"."+tLCCSpecSchema
								.getSpecContent()))); //						
					}else
					{
						eleSpecInfo.addContent(new Element("LCCSpec.SpecContent")
						.addContent(tLCCSpecSchema
								.getSpecContent())); //		
					}

					eleSpecInfo.addContent(new Element("LCCSpec.PrtFlag")
							.addContent(tLCCSpecSchema.getPrtFlag())); //
					eleSpecInfo.addContent(new Element("LCCSpec.Operator")
							.addContent(String.valueOf(tLCCSpecSchema
									.getOperator()))); //
					// eleLCCSpec.addChild(eleSpecInfo);

					eleLCCSpec.addContent(eleSpecInfo);
				}
			}
			xmlDataset.getRealElement().addContent(eleLCCSpec);

			Element elePicFileDes = new Element("PicFileDes");

			// 查出投保单影像件,分类
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();


			Element elePicFile = new Element("PicFile");
			
			StringBuffer sb = new StringBuffer();
			sb.append("select a.docid,a.subtype from ES_DOC_MAIN a,ES_DOC_RELATION b where ");
			sb.append("b.BUSSNO = '" + "?BUSSNO?" + "' and ");
			sb.append("b.BUSSNOTYPE = '11' and ");
			sb.append("b.BUSSTYPE = 'TB' and ");	
			sb.append("b.SubType in  (select codename from ldcode where codetype='polprint') and  ");
			sb.append(" a.DocID = b.DocID ").append(
			" order by a.subtype,a.docid");
			logger.debug("SQL:"+sb.toString());
			SQLwithBindVariables sqlbv24 = new SQLwithBindVariables();
			sqlbv24.sql(sb.toString());
			sqlbv24.put("BUSSNO", mLCPolSchema.getPrtNo());
			tSSRS = tExeSQL.execSQL(sqlbv24);
			int tArrLen = tSSRS.MaxRow;
			if (tArrLen > 0) {
				for (int i = 0; i < tArrLen; i++) {
					String tSubType  = tSSRS.GetText(i + 1, 2); // 获取业务编码
					String tDocId=tSSRS.GetText(i + 1, 1);

					tSQL = "select code from ldcode where codetype='polprint' and codename='"+"?tSubType?"+"'";
					SQLwithBindVariables sqlbv25 = new SQLwithBindVariables();
					sqlbv25.sql(tSQL);
					sqlbv25.put("tSubType", tSubType);
					String tRealSubType=tExeSQL.getOneValue(sqlbv25);//查询出内部编码
					Element elePicType = new Element("PicType");
					elePicType.addContent(tRealSubType);
					elePicFile.addContent(elePicType);
					VData vData = new VData();
					vData.add(tDocId);
					EasyScanQueryBL tEasyScanQueryBL = new EasyScanQueryBL();
					// 只有投保单影像件才报错

					if (!tEasyScanQueryBL.submitData(vData, "QUERY||0")
							&& "TB1001".equals(tRealSubType)) {
						CError.buildErr(this, "查询保单影像件失败!");
						return null;
					} else {
						int nPrintPages = 3;

						// 个人保险投保单 根据印刷号进行打印页数的区分
						String strPrtNoFlag = mLCPolSchema.getPrtNo()
								.substring(0, 4);
						if (("8635".equals(strPrtNoFlag)
								|| "8616".equals(strPrtNoFlag)
								|| "8625".equals(strPrtNoFlag) || "8615"
								.equals(strPrtNoFlag))
								&& "TB1001".equals(tRealSubType)) {
							nPrintPages = 1;
						}

//						TB1011 核保通知书(甲类)       
//						TB1017 投保要约更正申请书(打印)  
//						TB1019 生效日回溯	        
//						TB1021 未成年人身故保险金特别约定
//						TB1003 银邮保通投保单
						//只打印前一页，详细情况见LDCode，codetype='poltype' comcode描叙

						if ("TB1011".equals(tRealSubType)
								|| "TB1017".equals(tRealSubType)
								|| "TB1019".equals(tRealSubType)
								|| "TB1021".equals(tRealSubType)
								|| "TB1003".equals(tRealSubType)) {
							nPrintPages = 1;
						}
						//补充告知问卷 打印前两页
						if ("TB1028".equals(tRealSubType)) {
							nPrintPages = 2;
						}
						vData.clear();
						vData = tEasyScanQueryBL.getResult();
						// ////////changed by pst on 20071025
						// 由于Result()返回的是两个VData只需用其中一个//////////////
						VData tData = new VData();
						tData = (VData) vData.getObjectByObjectName("VData", 0);
						vData.clear();
						String strFileName = "";

						if (tData != null && tData.size() > 0) {
							for (int nIndex = 0; nIndex < tData.size(); nIndex++) {
								if (nIndex >= nPrintPages) {
									continue; // 个人打印前三页，银代打印第一页
								}

								strFileName = (String) tData.get(nIndex);

								strFileName = strFileName.substring(0,
										strFileName.lastIndexOf("."))
										+ ".tif";
								String strRealFileName = strFileName
										.substring(strFileName.lastIndexOf("/") + 1);

								Element elePicInfo = new Element("PicInfo");
								elePicInfo.addContent(new Element("FileUrl")
										.addContent(strFileName));
								elePicInfo.addContent(new Element("PageIndex")
										.addContent(String.valueOf(nIndex)));
								// elePicFile.addChild(elePicInfo);
								elePicFile.addContent(elePicInfo);
							}
						}

					}
				}
			}
			elePicFileDes.addContent(elePicFile);

			// 添加扫描件信息
			xmlDataset.getRealElement().addContent(elePicFileDes);

			Element eleCashValueDes = new Element("CashValueDes");

			double dPremSum = 0;
			LCPolSet qLCPolSet = new LCPolSet();
			LCPolDB rLCPolDB = new LCPolDB();
			
			String tWSQL = "select * from lcpol where appflag='1' and prtno='"
					+"?prtno?"
					+ "'  and riskcode not in ('111801','121601') order by polno";
			SQLwithBindVariables sqlbv26 = new SQLwithBindVariables();
			sqlbv26.sql(tWSQL);
			sqlbv26.put("prtno", tLCContSchema.getPrtNo());
			qLCPolSet = rLCPolDB.executeQuery(sqlbv26);

			String tQSQL = "select * from lcpol where appflag='1' and prtno='"
					+ "?prtno?"
					+ "' and riskcode in ('111801','121601') order by polno";
			SQLwithBindVariables sqlbv27 = new SQLwithBindVariables();
			sqlbv27.sql(tQSQL);
			sqlbv27.put("prtno", tLCContSchema.getPrtNo());
			// 为了实现下种排序的要求，主险总是在第一位，后续都是附加险，对于特殊的险种111801，121601 排在最后
			LCPolSet pLCPolSet = rLCPolDB.executeQuery(sqlbv27);
			qLCPolSet.add(pLCPolSet);
			
			for (int i = 1; i <= qLCPolSet.size(); i++) {
				LCPolSchema tLCPolSchema = qLCPolSet.get(i);

				dPremSum += tLCPolSchema.getPrem();

				Element eleCashValue = new Element("CashValue");
				eleCashValue.addContent(new Element("ContNo")
						.addContent(tLCPolSchema.getContNo()));
				eleCashValue.addContent(new Element("RiskName")
						.addContent(BqNameFun.getRiskShortName(tLCPolSchema
								.getRiskCode())));
				// 得到现金价值的算法描述
				LMCalModeDB tLMCalModeDB = new LMCalModeDB();

				tLMCalModeDB.setRiskCode(tLCPolSchema.getRiskCode());
				tLMCalModeDB.setType("X");

				LMCalModeSet tLMCalModeSet = tLMCalModeDB.query();

				// 解析得到的SQL语句
				String tstrSQL = "";

				if ((tLMCalModeSet.size() == 1)
						&& (tLMCalModeSet.get(1).getCalSQL() != null)
						&& (tLMCalModeSet.get(1).getCalSQL().length() != 0)) {
					tstrSQL = tLMCalModeSet.get(1).getCalSQL();
					tstrSQL = tstrSQL + " order by CurYear";
				} else {
					continue;
				}
				Calculator calculator = new Calculator();

				// 设置基本的计算参数
				//09-12-08  由于涉及到员工单打折且可能存在加费情况 并且系统中有部分错误数据
				//(lcpol中的floatrate小于1并且prem=standprem)并考虑到尽量降低程序的改动
				//此处改为传入polno 在算法中进行计算标准保费
				calculator.addBasicFactor("PolNo", tLCPolSchema
						.getPolNo());
				calculator.addBasicFactor("InsuredSex", tLCPolSchema
						.getInsuredSex());
				calculator.addBasicFactor("InsuredAppAge", String
						.valueOf(tLCPolSchema.getInsuredAppAge()));
				calculator.addBasicFactor("PayIntv", String
						.valueOf(tLCPolSchema.getPayIntv()));
				calculator.addBasicFactor("PayEndYear", String
						.valueOf(tLCPolSchema.getPayEndYear()));
				calculator.addBasicFactor("PayEndYearFlag", String
						.valueOf(tLCPolSchema.getPayEndYearFlag()));
				calculator.addBasicFactor("PayYears", String
						.valueOf(tLCPolSchema.getPayYears()));
				calculator.addBasicFactor("InsuYear", String
						.valueOf(tLCPolSchema.getInsuYear()));
				calculator.addBasicFactor("StandPrem", String.valueOf(tLCPolSchema
						.getStandPrem()));
				calculator.addBasicFactor("Prem", String.valueOf(tLCPolSchema
						.getPrem()));
				calculator.addBasicFactor("Amnt", String.valueOf(tLCPolSchema
						.getAmnt()));
				calculator.addBasicFactor("FloatRate", String
						.valueOf(tLCPolSchema.getFloatRate()));

				// add by yt 2004-3-10
				calculator.addBasicFactor("InsuYearFlag", String
						.valueOf(tLCPolSchema.getInsuYearFlag()));
				calculator.addBasicFactor("GetYear", String
						.valueOf(tLCPolSchema.getGetYear()));
				calculator.addBasicFactor("GetYearFlag", String
						.valueOf(tLCPolSchema.getGetYearFlag()));
				calculator.addBasicFactor("CValiDate", String
						.valueOf(tLCPolSchema.getCValiDate()));
				calculator.addBasicFactor("SignDate", String
						.valueOf(tLCPolSchema.getSignDate()));
				calculator.setCalCode(tLMCalModeSet.get(1).getCalCode());
				tstrSQL = calculator.getCalSQL();
				tstrSQL = tstrSQL + " order by CurYear";
				logger.debug(tstrSQL);

				TransferData CalSQLParams = new  TransferData();
				CalSQLParams = calculator.getCalSQLParams();
				VData tVData = new VData();
				tVData.add(0,tstrSQL);
				tVData.add(1,CalSQLParams);
				SSRS rSSRS = new SSRS();
				rSSRS = tExeSQL.execSQL(tVData);
				int k = 1;
				if (rSSRS.MaxRow > 0 && rSSRS != null) {
					// 现价表查询
					if ("0".equals(rSSRS.GetText(k, 1))
							&& "0".equals(rSSRS.GetText(k, 2))) {
						k = 2;
					}
					for (; k <= rSSRS.MaxRow; k++) {
						Element eleCashValueInfo = new Element("CashValueInfo");

						eleCashValueInfo.addContent(new Element("Age")
								.addContent(rSSRS.GetText(k, 1)));
						eleCashValueInfo.addContent(new Element("Value")
								.addContent(format(Double.parseDouble(rSSRS
										.GetText(k, 2)))));

						eleCashValue.addContent(eleCashValueInfo);
						// eleCashValue.addChild(eleCashValueInfo);
					}
				}
				eleCashValueDes.addContent(eleCashValue);
			}
			// 添加险种现价信息
			xmlDataset.getRealElement().addContent(eleCashValueDes);

			// 家庭单财务 查询 家庭单号
			String rContNo = "";
			if (("2".equals(tLCContSchema.getFamilyType())
					&& !"".equals(tLCContSchema.getFamilyContNo()) && tLCContSchema
					.getFamilyContNo() != null)
					|| ("1".equals(tLCContSchema.getFamilyType())
							&& !"".equals(tLCContSchema.getFamilyContNo()) && tLCContSchema
							.getFamilyContNo() != null)) {
				rContNo = tLCContSchema.getFamilyContNo();
			} else {
				rContNo = tLCContSchema.getContNo();
			}
			String strSQL = "SELECT TempFeeNo, Operator FROM LJTempFee WHERE ( OtherNoType = '0' OR OtherNoType = '4' OR OtherNoType = '3'  ) AND OtherNo = '"
					+ "?rContNo?" + "'";
			SQLwithBindVariables sqlbv28 = new SQLwithBindVariables();
			sqlbv28.sql(strSQL);
			sqlbv28.put("rContNo", rContNo);
			SSRS ssRs = new ExeSQL().execSQL(sqlbv28);

			if (ssRs.ErrorFlag || (ssRs.MaxRow == 0)) {
				CError.buildErr(this, "查询暂收费信息失败!");
				return null;
			}

			xmlDataset.addDataObject(new XMLDataTag("LJTempFee.TempFeeNo", ssRs
					.GetText(1, 1)));
			xmlDataset.addDataObject(new XMLDataTag("LJTempFee.Handler", ssRs
					.GetText(1, 2)));
			xmlDataset
					.addDataObject(new XMLDataTag("PremSum", format(dPremSum)));

			xmlDataset.addDataObject(new XMLDataTag("LJTempFee.PayMoneySum",
					format(dPremSum)));
			xmlDataset.addDataObject(new XMLDataTag("LJTempFee.PayMoneySum1",
					PubFun.getChnMoney(dPremSum).substring(4,
							PubFun.getChnMoney(dPremSum).length())));

			getReceipt(xmlDataset, qLCPolSet, tLCContSchema);

			return xmlDataset;
		} catch (Exception ex) {
			ex.printStackTrace();
			CError.buildErr(this, ex.getMessage());
			return null;
		}
	}

	// add by ck
	// Format参数"yyyy","MM","dd"
	public String changeDate(String tDate) {
		FDate tFDate = new FDate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy" + "年" + "MM" + "月"
				+ "dd" + "日");

		return sdf1.format(tFDate.getDate(tDate));
	}

	/**
	 * 获取保单对应险种名称，支持多版分
	 */
	private String getRiskName(LCPolSchema aLCPolSchema) {
		String rRiskName = "";
		LDCodeDB tLDCodeDB = new LDCodeDB();
		tLDCodeDB.setCodeType("printpolriskname");
		tLDCodeDB.setCode(aLCPolSchema.getRiskCode());
		if (tLDCodeDB.getInfo()) {
			Calculator tC = new Calculator();
			tC.addBasicFactor("PolNo", aLCPolSchema.getPolNo());
			tC.setCalCode(tLDCodeDB.getOtherSign().trim());
			rRiskName = tC.calculate();
		} else {
			LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
			tLMRiskAppDB.setRiskCode(aLCPolSchema.getRiskCode());
			tLMRiskAppDB.getInfo();
			rRiskName = tLMRiskAppDB.getRiskName();
		}

		return rRiskName;
	}

	private void genXMLFile() {
		try {
			FileWriter writer = new FileWriter("LCPolData.xml");
			mXMLDatasets.output(writer);
			writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private String prevDay(String strDate) {
		Date dt = PubFun.calDate(new FDate().getDate(strDate), -1, "D", null);
		return new FDate().getString(dt);
	}

	/**
	 * 保单重打的取数流程
	 * 
	 * @param aLCPolSet
	 * @return
	 */
	private boolean getPolDataSetEx(LCContSchema rLCContSchema) {

		// 取打印数据
		Connection conn = null;
		try {
			conn = DBConnPool.getConnection();
			if (conn == null) {
				throw new Exception("连接数据库失败！");
			}

			// 家庭单 查询 家庭单号
			String rContNo = "";
			if (("2".equals(rLCContSchema.getFamilyType())
					&& !"".equals(rLCContSchema.getFamilyContNo()) && rLCContSchema
					.getFamilyContNo() != null)
					|| ("1".equals(rLCContSchema.getFamilyType())
							&& !"".equals(rLCContSchema.getFamilyContNo()) && rLCContSchema
							.getFamilyContNo() != null)) {
				rContNo = rLCContSchema.getFamilyContNo();
			} else {
				rContNo = rLCContSchema.getContNo();
			}
			
			COracleBlob tCOracleBlob = new COracleBlob();
			CMySQLBlob tCMySQLBlob = new CMySQLBlob();
			String tSQL = "";
			java.sql.Blob tBlob = null;
			tSQL = " and ContNo = '" + "?rContNo?" + "'";
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				tBlob = tCOracleBlob.SelectBlob("LCContPrint", "ContInfo", tSQL,
						conn);
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				tBlob = tCMySQLBlob.SelectBlob("LCContPrint", "ContInfo", tSQL,
						conn);	
			}
			if (tBlob == null) {
				CError.buildErr(this, "找不到打印数据,请重新打印");
				return false;
				//throw new Exception("找不到打印数据");
			}

			// BLOB blob = (oracle.sql.BLOB)tBlob; --Fanym
			logger.debug("Get Blob");
			XMLDataset xmlDataset = mXMLDatasets.createDataset();
			XMLDataBlob xmlDataBlob = new XMLDataBlob(tBlob.getBinaryStream());
			xmlDataset.getRealElement().removeChild("CONTROL");
			if (!xmlDataBlob.addDataTo(xmlDataset)) {
				CError.buildErr(this, "获取XML数据失败,请联系相关技术人员进行处理");
				return false;
				//throw new Exception("获取XML数据失败");
			}
			if (conn != null) {
				conn.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				// do nothing
			}
			CError.buildErr(this, ex.getMessage());
			return false;
		}

		return true;
	}

	/**
	 * 2003-04-28 Kevin 格式化浮点型数据
	 * 
	 * @param dValue
	 * @return
	 */
	private String format(double dValue) {
		return new DecimalFormat("0.00").format(dValue);
	}

	private String getName(String strType, String strCode) {
		LDCodeDB tLDCodeDB = new LDCodeDB();

		tLDCodeDB.setCodeType(strType);
		tLDCodeDB.setCode(strCode);

		if (!tLDCodeDB.getInfo()) {
			return "";
		} else {
			return tLDCodeDB.getCodeName();
		}
	}

	/**
	 * 纪录错误信息
	 * 
	 * @param tSerialNo
	 * @param tPolNo
	 * @param errDescribe
	 * @param tGetMode
	 * @return
	 */
	public boolean insertErrLog(String errDescribe, LCContSchema mLCContSchema) {
		String tLimit = PubFun.getNoLimit(mLCContSchema.getManageCom());
		String tSerialNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);// 产生流水号码
		LDSysErrLogDB tLDSysErrLogDB = new LDSysErrLogDB();
		tLDSysErrLogDB.setSerialNo(tSerialNo);
		tLDSysErrLogDB.setPolNo("00000000000000000000");
		tLDSysErrLogDB.setModule("ICB"); // 承保
		tLDSysErrLogDB.setSubModule("ICBPP");// 保单打印
		tLDSysErrLogDB.setGrpContNo(mLCContSchema.getGrpContNo());
		tLDSysErrLogDB.setContNo(mLCContSchema.getContNo());
		tLDSysErrLogDB.setManageCom(mLCContSchema.getManageCom());
		tLDSysErrLogDB.seterrMsg(errDescribe);
		tLDSysErrLogDB.setMakeDate(tCurMakeDate);
		tLDSysErrLogDB.setMakeTime(tCurMakeTime);
		tLDSysErrLogDB.setManageCom(mLCContSchema.getManageCom());
		if (tLDSysErrLogDB.insert() == false) {
			CError tError = new CError();
			tError.moduleName = "LCPolF1P_IDGFBL";
			tError.functionName = "insertErrLog";
			tError.errorMessage = "纪录错误日志时发生错误："
					+ tLDSysErrLogDB.mErrors.getFirstError() + "；解决该问题后，打印此保单";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * some special process for count print
	 * 
	 * @param doc
	 */
	private void countPrint(Document doc) {
		Element ele = doc.getRootElement();

		String strOrg = "";

		//
		// 终止日期减一天
		//
		strOrg = ele.getChild("LCPol.EndDate").getText();
		strOrg = strOrg.substring(0, 4) + "-" + strOrg.substring(5, 7) + "-"
				+ strOrg.substring(8, 10);
		strOrg = prevDay(strOrg);
		strOrg = strOrg.substring(0, 4) + "年" + strOrg.substring(5, 7) + "月"
				+ strOrg.substring(8, 10) + "日";
		ele.getChild("LCPol.EndDate").setText(strOrg);

		// 代码到名字的转换
		//
		strOrg = ele.getChild("LCAppntInd.Sex").getText();
		ele.getChild("LCAppntInd.Sex").setText(getName("sex", strOrg));

		strOrg = ele.getChild("LCPol.InsuredSex").getText();
		ele.getChild("LCPol.InsuredSex").setText(getName("sex", strOrg));

		List list = ele.getChild("LCBnf").getChildren("ROW");
		for (int n = 0; n < list.size(); n++) {
			Element eleList = (Element) list.get(n);

			// 将受益比例设为百分比的形式
			strOrg = eleList.getChild("COL5").getText();
			strOrg = String.valueOf(Double.parseDouble(strOrg) * 100) + "%";
			eleList.getChild("COL5").setText(strOrg);

			strOrg = eleList.getChild("COL9").getText();
			eleList.getChild("COL9").setText(getName("sex", strOrg));

			strOrg = eleList.getChild("COL4").getText();
			eleList.getChild("COL4").setText(getName("relation", strOrg));
		}

		// 如果没有受益人，打印“此栏空白”。
		if (list.size() == 0) {
			ele.getChild("LCBnf").addContent(
					new Element("ROW").addContent(new Element("COL8")
							.addContent("此栏空白")));
		}

		// 设置管理机构的信息
		strOrg = ele.getChild("LCPol.ManageCom").getText();

		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(strOrg);
		tLDComDB.getInfo();
		ele.addContent(new Element("ManageCom.Phone").setText(tLDComDB
				.getPhone()));

		ele.getChild("LCPol.ManageCom").setText(getName("station", strOrg));

		// 添加管理机构的电话
	}
	  //保存操作
	  private boolean save(XMLDatasets tXMLDatasets)
	  {
	    boolean tReturn =true;
	    logger.debug("Start Save...");
	    Connection conn=DBConnPool.getConnection();

	    if (conn==null)
	    {
	      // @@错误处理
	      CError tError = new CError();
	      tError.moduleName = "LCPolF1P_IDGFBL";
	      tError.functionName = "saveData";
	      tError.errorMessage = "数据库连接失败!";
	      this.mErrors .addOneError(tError) ;
	      return false;
	    }
	    try {
	      conn.setAutoCommit(false);	      
	      // 保存数据流
	      saveDataStream(tXMLDatasets, mGlobalInput, conn);
	      conn.commit() ;
	      conn.close();
	      logger.debug("commit end");
	    }
	    catch (Exception ex)
	    {
	      // @@错误处理
	      CError tError =new CError();
	      tError.moduleName="LCPolF1P_IDGFBL";
	      tError.functionName="submitData";
	      tError.errorMessage=ex.getMessage();
	      this.mErrors.addOneError(tError);
	      try{conn.rollback(); conn.close();} catch(Exception e){}
	      tReturn=false;
	    }

	    return tReturn;
	  }
	  /**
	   * 将所有得到的打印数据按照主险保单号存入数据库，为以后的补打保单做准备
	   * @param aXMLDataSets
	   * @param aGlobalInput
	   * @param conn
	   * @throws Exception
	   */
	  private void saveDataStream(XMLDatasets aXMLDataSets,
	                              GlobalInput aGlobalInput,
	                              Connection conn)
	      throws Exception {

	    Document doc = aXMLDataSets.getDocument();
	    List list = doc.getRootElement().getChildren("DATASET");

	    for(int nIndex = 0; nIndex < list.size(); nIndex ++) {
	      Element ele = (Element)list.get(nIndex);

	      //
	      // Build a new element from the content of old element.
	      // By doing this, we can detach element from document
	      //
	      ele = new Element("DATASET").setMixedContent(ele.getMixedContent());

	      // Document newDoc = new Document(new Element("DATASETS").addContent(ele));
	      Document newDoc = new Document(ele);
	      saveOneDataStream(newDoc, aGlobalInput, conn);
	    }

	  }

	  /**
	   * 保存一条保单打印的数据
	   * @param doc
	   * @param aGlobalInput
	   * @param conn
	   * @throws Exception
	   */
	  private void saveOneDataStream(Document doc,
	                                 GlobalInput aGlobalInput,
	                                 Connection conn)
	      throws Exception {
	    //modify by yt
	    COracleBlob tCOracleBlob = new COracleBlob();
	    CMySQLBlob tCMySQLBlob = new CMySQLBlob();
	  
	    try {
	      String strPolNo =
	          doc.getRootElement().getChildText("LCCont.ContNo");

	      String strCurDate = PubFun.getCurrentDate();
	      String strCurTime = PubFun.getCurrentTime();

	      // 得到数据输出对象
	      String szSQL = "DELETE FROM LCContPrint WHERE ContNo = '" + strPolNo + "'";
	      if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
	    	  if (!tCOracleBlob.DeleteBlobRecord(szSQL,conn))
	    	  {throw  new Exception("删除数据失败！"+szSQL);}
		    }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			 if (!tCMySQLBlob.DeleteBlobRecord(szSQL,conn))
		    	  {throw  new Exception("删除数据失败！"+szSQL);}	
		    }
	      szSQL = "INSERT INTO LCContPrint(ContNo,prtflag, PrtTimes, Operator, MakeDate, MakeTime, ModifyDate, ModifyTime, ContInfo, ContType) VALUES('"
	            + strPolNo + "', "
	            + "'Y', "
	            + "1, '"
	            + aGlobalInput.Operator + "', '"
	            + strCurDate + "', '"
	            + strCurTime + "', '"
	            + strCurDate + "', '"
	            + strCurTime + "', empty_blob(), '1')";

	      
	      if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
	    	  if (!tCOracleBlob.InsertBlankBlobRecord(szSQL,conn))
	          {throw  new Exception("插入数据失败！"+szSQL);}
		    }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			 if (!tCMySQLBlob.InsertBlankBlobRecord(szSQL,conn))
	          {throw  new Exception("插入数据失败！"+szSQL);}
		    }
	      szSQL = " and ContNo = '" + strPolNo + "' ";
	      if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
	    	  if (!tCOracleBlob.UpdateBlob(doc,"LCContPrint","ContInfo",szSQL,conn))
	          {throw  new Exception("修改数据失败！"+szSQL);}
		    }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
		    	  if (!tCMySQLBlob.UpdateBlob(doc,"LCContPrint","ContInfo",szSQL,conn))
		          {throw  new Exception("修改数据失败！"+szSQL);}
		    }
	    } catch (Exception ex) {
	      ex.printStackTrace();
	      throw ex;
	    }
	  }
	public static void main(String[] args) {
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.ManageCom = "86";
		tGlobalInput.Operator = "001";
		LCContSet tLCContSet = new LCContSet();
		LCContDB tLCContDB = new LCContDB();
		// tLCContDB.setAppFlag("1");
		// tLCContDB.setPrintCount(0);
		// tLCContDB.setGrpContNo("00000000000000000000");
		// tLCContDB.setContType("1");
		// tLCContDB.setContNo("86131120070210037207");
		// '86510720070210011428','86131120070210037213'
		String tContSQL = "select * from lccont where contno in ("
				+ "'86510020090210018799')";
		SQLwithBindVariables sqlbv31 = new SQLwithBindVariables();
		sqlbv31.sql(tContSQL);
		tLCContSet = tLCContDB.executeQuery(sqlbv31);
		VData tVData = new VData();
		tVData.add(tGlobalInput);
		tVData.add(tLCContSet);
		LCPolF1P_IDGFBL tLCPolF1P_IDGFBL = new LCPolF1P_IDGFBL();
		if (!tLCPolF1P_IDGFBL.submitData(tVData, "PRINT")) {
			logger.debug("\t@> LCPolF1P_IDGFBL 打印失败");
		}
		Document XMLDoc = tLCPolF1P_IDGFBL.mXMLDatasets.getDocument();
		FileWriter XMLWriter = null;
		try {
			XMLWriter = new FileWriter("C:\\XML\\1.xml");// 根据传入参数确定文件名？？
		} catch (IOException e) {
			e.printStackTrace();
		}
		XMLOutputter XMLOutPuter = new XMLOutputter("  ", true, "GBK");
		try {
			XMLOutPuter.output(XMLDoc, XMLWriter);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} // function main end
}
