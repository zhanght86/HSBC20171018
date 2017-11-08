package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.bq.*;
import com.sinosoft.lis.db.LDSysVarDB;

/** 
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: </p>
 * @author pst
 * @version 1.0
 * @CreateDate：2009-04-11
 * 清单名称：借款利息数据统计
 */

public class EdorLNLXBillPrintBL implements BqBill {
private static Logger logger = Logger.getLogger(EdorLNLXBillPrintBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	private TransferData mTransferData = new TransferData();

	private VData mInputData = new VData();

	private String mOperate = "";

	private GlobalInput mGlobalInput = new GlobalInput();

	public static final String VTS_NAME = "EdorLNLXBill.vts";

	private TextTag texttag = new TextTag();

	private String mManageCom = "";

	private String mSaleChnl = "";

	private String mStartDate = "";

	private String mEndDate = "";

	private String mUserCode = "";

	private String mEdorType = "";

	private String mSaleChlSQL = "";

	private String mUserCodeSQL = "";

	private String mEdorTypeSQL = "";
	/**文件生成名称*/
	private String mExCelFile="";
	/**模板路径*/
	private String strTemplatePath="";

	private String mOrderSQL = "order by a.managecom,a.edortype";

	private ListTable tBonusListTable = new ListTable();

	private XmlExport tXmlExport = new XmlExport();

	public EdorLNLXBillPrintBL() {
	}

	/**
	 * 传输数据的公共方法
	 * @param: cInputData 输入的数据
	 * @param: cOperate 数据操作 
	 * @return: boolean */
	public boolean submitData(VData cInputData, String cOperate) {
		//将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		//将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		if (!mOperate.equals("PRINT")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EdorLNLXBillPrintBL";
			tError.functionName = "submitData";
			tError.errorMessage = "不支持的操作字符串！";
			this.mErrors.addOneError(tError);
			return false;
		}

		//得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		//准备需要打印的数据
		if (!preparePrintData()) {
			return false;
		}

		return true;
	}

	private boolean getInputData(VData cInputData) {
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		if (mGlobalInput == null || mTransferData == null) {
			mErrors.addOneError(new CError("数据传输不完全！"));
			return false;
		}
		mStartDate = (String) mTransferData.getValueByName("StartDate");
		if (mStartDate == null || mStartDate.trim().equals("")) {
			mErrors.addOneError(new CError("没有得到足够的信息:统计起期不能为空！"));
			return false;
		}
		mEndDate = (String) mTransferData.getValueByName("EndDate");
		if (mEndDate == null || mEndDate.trim().equals("")) {
			mErrors.addOneError(new CError("没有得到足够的信息:统计止期不能为空！"));
			return false;
		}
		mManageCom = (String) mTransferData.getValueByName("ManageCom");
		if (mManageCom == null || mManageCom.trim().equals("")) {
			mErrors.addOneError(new CError("没有得到足够的信息:机构不能为空！"));
			return false;
		}
		mExCelFile = (String) mTransferData.getValueByName("ExCelFile"); // ExCel文件名称
		strTemplatePath = (String) mTransferData
				.getValueByName("ExCelTemplatePath"); // 模板路径

		if (strTemplatePath == null || mExCelFile == null) {
			mErrors.addOneError(new CError("模板或者输出文件名为空！"));
			return false;
		}

		return true;
	}

	private boolean preparePrintData() {
		tXmlExport.createDocument(VTS_NAME, "printer"); //初始化xml文档

		SSRS tssrs = new SSRS();
		ExeSQL texesql = new ExeSQL();
		String strsql = "";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		strsql = " select (select l.managecom from lccont l where l.contno = a.contno),"
				+ " contno,"
				+ " (select l.appntname from lccont l where l.contno = a.contno),"
				+ " (select l.insuredname from lccont l where l.contno = a.contno),"
				+ " a.loandate,"
				+ " a.leavemoney,"
				+"  a.newloandate,a.Currency"
				+ " from loloan a"
				+ " where substr(contno, 11, 3) = '021'"
				+ " and loantype = '0'"
				+ " and payoffflag = '0'"
				+ " and a.loandate >= '"
				+ "?mStartDate?"
				+ "'"
				+ " and a.loandate <= '"
				+ "?mEndDate?"
				+ "'"
				+ " and exists (select 'X'"
				+ " from lccont l"
				+ " where l.contno = a.contno"
				+ " and l.managecom like concat('"+ "?mManageCom?" + "','%')"
				+ " )";
		logger.debug("SQL:" + strsql);
		sqlbv.sql(strsql);
		sqlbv.put("mStartDate", mStartDate);
		sqlbv.put("mEndDate", mEndDate);
		sqlbv.put("mManageCom", mManageCom);
		tssrs = texesql.execSQL(sqlbv);
		if (tssrs == null || tssrs.getMaxRow() <= 0) {
			CError.buildErr(this, mStartDate + "至" + mEndDate + "之间无待打印清单！");
			return false;
		}

		ListTable tContListTable = new ListTable();
		tContListTable.setName("BILL");
		String strLine[] = null;
		int j;//计数
        int tMaxRow=0;
		int tGetPayCount = tssrs.getMaxRow();
		double tGetPayMoney = 0;
		//机构代码	保单号	投保人	被保险人	借款生效日期	
		//借款金额	截止到统计结束日期产生的利息	借款金额合计	借款利息合计
		for (int i = 1; i <= tssrs.getMaxRow(); i++) {
			strLine = new String[9];
			for (j = 0; j < 6; j++) {
				strLine[j] = tssrs.GetText(i, j + 1);
			}
			if("".equals(tssrs.GetText(i, 7))|| tssrs.GetText(i, 7)==null 
					|| tssrs.GetText(i, 6)==null || "".equals(tssrs.GetText(i, 6)))
			{
				continue;
			}
			// 分段计息
			double tRate = AccountManage.calMultiRateMS(tssrs.GetText(i, 7),  mEndDate, "000000","LN","L","C","Y",tssrs.GetText(i, 8));
			if (tRate+1==0) {
				CError.buildErr(this, "利息系数计算错误！");
				return false;
			}
			//累计计息
			String tIntrest=BqNameFun.getFormat(Double.parseDouble(tssrs.GetText(i, 6))*tRate);
			strLine[6]=String.valueOf(tIntrest);
			strLine[7]=tssrs.GetText(i, 6);
			strLine[8]=String.valueOf(tIntrest);
			tMaxRow++;
			//tGetPayMoney += Double.parseDouble(tssrs.GetText(i, 8));
			tContListTable.add(strLine);
		}
        try
        {
    		String tData[][] = new String[tMaxRow][9];
        	for(int k=0;k<tContListTable.size();k++)
        	{
        		tData[k]=tContListTable.get(k);
        	}
            HashReport tHashReport = new HashReport();
            String tpath = "";
            LDSysVarDB tLDSysVarDB = new LDSysVarDB();
            tLDSysVarDB.setSysVar("BQExcelPath");
            if(!tLDSysVarDB.getInfo())
             {
                return false;
             }
            tpath = tLDSysVarDB.getSysVarValue();
            //tpath="C:\\XML\\";
            tHashReport.outputArrayToFile1(tpath+mExCelFile+".xls",strTemplatePath+"EdorLNLXBill.xls",tData);
        }
        catch(Exception ex)
        {
           CError cError = new CError();
           cError.moduleName = "ReRateDetail";
           cError.functionName = "dealData";
           cError.errorMessage = "生成excel表时出错";
           this.mErrors.addOneError(cError);
           return false;
        }
		mManageCom = BqNameFun.getCBranch(mManageCom);
		mStartDate = BqNameFun.getChDate(mStartDate);
		mEndDate = BqNameFun.getChDate(mEndDate);
		//模版自上而下的元素
		texttag.add("ManageCom", mManageCom);
		texttag.add("StartDate", mStartDate);
		texttag.add("EndDate", mEndDate);
		texttag.add("SumPayCount", tGetPayCount);
//		texttag.add("SumPayMoney", PubFun.round(tGetPayMoney, 2));
		texttag
				.add("PrintDate", BqNameFun
						.getChDate((PubFun.getCurrentDate())));

		if (texttag.size() > 0) {
			tXmlExport.addTextTag(texttag);
		}
		tXmlExport.addListTable(tContListTable, strLine);
		//tXmlExport.outputDocumentToFile("c:\\XML\\", "EdorLNLXBill.vts"); 
		mResult.clear();
		mResult.addElement(tXmlExport);

		return true;

	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}
}
