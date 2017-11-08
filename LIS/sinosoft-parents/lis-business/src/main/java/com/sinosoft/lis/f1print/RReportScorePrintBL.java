/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.f1print;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCScoreRReportDB;
import com.sinosoft.lis.db.LCScoreRReportSubDB;
import com.sinosoft.lis.db.LDUWUserDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCScoreRReportSchema;
import com.sinosoft.lis.vschema.LCScoreRReportSubSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author zhangxing
 * @version 1.0
 */
public class RReportScorePrintBL {
private static Logger logger = Logger.getLogger(RReportScorePrintBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private String mSql = "";
	private VData mResult = new VData();
	// 保费保额计算出来后的精确位数
	private String FORMATMODOL = "0.00";
	// 数字转换对象
	private DecimalFormat mDecimalFormat = new DecimalFormat(FORMATMODOL);
	String mSumPrem = "";
	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LCScoreRReportSchema mLCScoreRReportSchema = new LCScoreRReportSchema();

	private String mContNo = "";
	private String mCustomerNo = "";
	private String mOperate = "";

	public RReportScorePrintBL() {
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
		mOperate = cOperate;
		try {

			if (!cOperate.equals("PRINT")) {
				buildError( "不支持的操作字符串");
				return false;
			}

			// 得到外部传入的数据，将数据备份到本类中
			if (!getInputData(cInputData)) {
				return false;
			}

			if (cOperate.equals("PRINT")) {

				mResult.clear();

				// 准备所有要打印的数据
				getPrintData();

			}			

			return true;

		} catch (Exception ex) {
			ex.printStackTrace();
			buildError(ex.toString());
			return false;
		}
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
		mLCScoreRReportSchema.setSchema((LCScoreRReportSchema) cInputData
				.getObjectByObjectName("LCScoreRReportSchema", 0));

		if (mGlobalInput == null) {
			buildError("没有得到足够的信息！");
			return false;
		}		
		if (mLCScoreRReportSchema == null) {
			buildError("没有得到足够的信息！");
			return false;
		}
		
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szErrMsg) {
		CError.buildErr(this, szErrMsg) ;
	}

	private void getPrintData() throws Exception {
		mContNo = mLCScoreRReportSchema.getContNo();
		mCustomerNo = mLCScoreRReportSchema.getCustomerNo();
		LCContDB tLCContDB = new LCContDB();
		LCScoreRReportDB tLCScoreRReportDB = new LCScoreRReportDB();
		tLCScoreRReportDB.setContNo(mContNo);
		tLCScoreRReportDB.setCustomerNo(mCustomerNo);

		if (tLCScoreRReportDB.getInfo() == false) {
			mErrors.copyAllErrors(tLCScoreRReportDB.mErrors);
			throw new Exception("在查询LCScoreRReport时发生错误");
		}
		
		String num = "";
		String sql = "select sum(1) from lcscorerreport where customerno='"+ "?mCustomerNo?" +"'" +
				" and substr(AssessDay,1,4)='"+"?AssessDay?"+"'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(sql);
		sqlbv1.put("mCustomerNo", mCustomerNo);
		sqlbv1.put("AssessDay", StrTool.getYear());
		ExeSQL exeSql2 = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = exeSql2.execSQL(sqlbv1);
		num = tSSRS.GetText(1, 1);
		
		LCScoreRReportSubDB tLCScoreRReportSubDB = new LCScoreRReportSubDB();
		LCScoreRReportSubSet tLCScoreRReportSubSet = new LCScoreRReportSubSet();
		sql = "select * from lcscorerreportsub where contno='"+ "?mContNo?" +"' and customerno='"+ "?mCustomerNo?" +"' "
		        + "and assessitem like 'SScore%' order by assessitem";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(sql);
		sqlbv2.put("mContNo", mContNo);
		sqlbv2.put("mCustomerNo", mCustomerNo);
		tLCScoreRReportSubSet = tLCScoreRReportSubDB.executeQuery(sqlbv2);
		if(tLCScoreRReportSubSet==null || tLCScoreRReportSubSet.size()<1)
		{
			CError.buildErr(this, "查询评分具体信息失败");
		}
		
		String[] SScore = new String[8];
		String[] AScore = new String[6];
		for(int i=1; i<=tLCScoreRReportSubSet.size()&& i<=8; i++)
		{
			SScore[i-1] = String.valueOf(tLCScoreRReportSubSet.get(i).getScore());
		}
		
		sql = "select * from lcscorerreportsub where contno='"+ "?mContNo?" +"' and customerno='"+ "?mCustomerNo?" +"' "
        + "and assessitem like 'AScore%' order by assessitem";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(sql);
		sqlbv3.put("mContNo", mContNo);
		sqlbv3.put("mCustomerNo", mCustomerNo);
		tLCScoreRReportSubSet = tLCScoreRReportSubDB.executeQuery(sqlbv3);
		if(tLCScoreRReportSubSet==null || tLCScoreRReportSubSet.size()<1)
		{
			CError.buildErr(this, "查询评分具体信息失败");
		}
		
		for(int i=1; i<=tLCScoreRReportSubSet.size()&& i<=6; i++)
		{
			AScore[i-1] = String.valueOf(tLCScoreRReportSubSet.get(i).getScore());
		}
			// 打印时传入的是主险投保单的投保单号
			/*tLCContDB.setContNo(mContNo);
			if (!tLCContDB.getInfo()) {
				mErrors.copyAllErrors(tLCContDB.mErrors);
				throw new Exception("在获取保单信息时出错！");
			}*/
			
			//查询生调员信息
			LDUWUserDB tLDUWUserDB = new LDUWUserDB();
			tLDUWUserDB.setUWType("1");//个险
			tLDUWUserDB.setUserCode(mCustomerNo);
			if (!tLDUWUserDB.getInfo()) {
				mErrors.copyAllErrors(tLCContDB.mErrors);
				throw new Exception("在获取生调员信息时出错！");
			}

			String remark = "";
			remark = "RReportScore.vts";
			XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例
			xmlExport.createDocument(remark, ""); // 最好紧接着就初始化xml文档
			TextTag texttag = new TextTag();
			String CurrentDay = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
					+ StrTool.getDay() + "日";

			texttag.add("PrtNo", mContNo);
			texttag.add("CurrentDay", CurrentDay);
			texttag.add("ManageCom", tLDUWUserDB.getManageCom());
			
			//查询管理机构名称
			sql = "select codename from ldcode where codetype='station' and code='"+ "?code?" +"'" ;
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(sql);
			sqlbv4.put("code", tLDUWUserDB.getManageCom());
			exeSql2 = new ExeSQL();
			tSSRS = new SSRS();
			tSSRS = exeSql2.execSQL(sqlbv4);
			String  ManageComName = tSSRS.GetText(1, 1);
			
			texttag.add("ManageComName", ManageComName);
			texttag.add("Num", num);
			texttag.add("Name", tLCScoreRReportDB.getName());
			texttag.add("SScore", tLCScoreRReportDB.getSScore());
			texttag.add("AScore", tLCScoreRReportDB.getAScore());
			texttag.add("Score", tLCScoreRReportDB.getScore());
			texttag.add("Conclusion", tLCScoreRReportDB.getConclusion());
			texttag.add("AssessOperator", tLCScoreRReportDB.getAssessOperator());
			texttag.add("AssessDay", tLCScoreRReportDB.getAssessDay());
			
			texttag.add("Subtraction1", SScore[0]);
			texttag.add("Subtraction2", SScore[1]);
			texttag.add("Subtraction3", SScore[2]);
			texttag.add("Subtraction4", SScore[3]);
			texttag.add("Subtraction5", SScore[4]);
			texttag.add("Subtraction6", SScore[5]);
			texttag.add("Subtraction7", SScore[6]);
			texttag.add("Subtraction8", SScore[7]);
			
			texttag.add("Addition1", AScore[0]);
			texttag.add("Addition2", AScore[1]);
			texttag.add("Addition3", AScore[2]);
			texttag.add("Addition4", AScore[3]);
			texttag.add("Addition5", AScore[4]);
			texttag.add("Addition6", AScore[5]);
			
			
			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}

			// xmlExport.outputDocumentToFile("D:/lis/ui/f1print/NCLtemplate/",
			// "testHZ"); //输出xml文档到文件
			mResult.clear();
			mResult.addElement(xmlExport);

	}

}
