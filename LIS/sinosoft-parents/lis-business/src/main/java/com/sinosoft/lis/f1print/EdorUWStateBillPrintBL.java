package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.bq.*;

/** 
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: </p>
 * @author pst
 * @version 1.0
 * @CreateDate：2009-04-11
 * 清单名称：核保状态查询（清单打印）
 */

public class EdorUWStateBillPrintBL implements BqBill {
private static Logger logger = Logger.getLogger(EdorUWStateBillPrintBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	private TransferData mTransferData = new TransferData();

	private VData mInputData = new VData();

	private String mOperate = "";

	private GlobalInput mGlobalInput = new GlobalInput();

	public static final String VTS_NAME = "EdorUWSateBill.vts";

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

	private String mOrderSQL = "order by a.managecom,a.edortype";

	private ListTable tBonusListTable = new ListTable();

	private XmlExport tXmlExport = new XmlExport();

	public EdorUWStateBillPrintBL() {
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
			tError.moduleName = "EdorUWStateBillPrintBL";
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
		mUserCode = (String) mTransferData.getValueByName("UserCode");
		if (mUserCode != null && !"".equals(mUserCode)) {
			mUserCodeSQL = " and  exists (select 'X'" + " from lpedorapp p "
					+ " where p.otherno = a.missionprop2 "
					+ " and p.edoracceptno=a.missionprop1 "
					+ " and p.operator = '" + "?mUserCode?" + "')";
		}

		return true;
	}

	private boolean preparePrintData() {
		tXmlExport.createDocument(VTS_NAME, "printer"); //初始化xml文档
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		SSRS tssrs = new SSRS();
		ExeSQL texesql = new ExeSQL();
		String strsql = "";
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			strsql = " select " + " MissionProp7," + " missionprop1,"
					+ " missionprop2," + " missionprop4,"
					+ " (select b.edorappdate" + " from lpedorapp b"
					+ " where b.edoracceptno = a.missionprop1),"
					+ " (select b.uwoperator" + " from lpedorapp b"
					+ " where b.edoracceptno = a.missionprop1),"
					+ " (case missionprop18 when '1' then '未人工核保' when '2' then '核保已回复' when '3' then '核保未回复' when '4' then '强制人工核保' else '' end),"
					+ " (select concat(concat((select codename" + " from ldcode"
					+ " where codetype = 'rebquwnotice'"
					+ " and code = c.code) , '--'),"
					+ " ((case stateflag when '1' then '打印未回复' when '2' then '已回复' else '' end)))"
					+ " from loprtmanager c"
					+ " where c.standbyflag3 = a.missionid" + " and code = '23'"
					+ " and rownum = 1)," + " (select makedate"
					+ " from loprtmanager c"
					+ " where c.standbyflag3 = a.missionid" + " and code = '23'"
					+ " and rownum = 1)," + " (select concat(concat((select codename"
					+ " from ldcode" + " where codetype = 'rebquwnotice'"
					+ " and code = c.code) , '--') ,"
					+ " ((case stateflag when '1' then '打印未回复' when '2' then '已回复' else '' end)))"
					+ " from loprtmanager c"
					+ " where c.standbyflag3 = a.missionid" + " and code = '24'"
					+ " and rownum = 1)," + " (select makedate"
					+ " from loprtmanager c"
					+ " where c.standbyflag3 = a.missionid" + " and code = '24'"
					+ " and rownum = 1)," + " (select concat(concat( (select codename"
					+ " from ldcode" + " where codetype = 'rebquwnotice'"
					+ " and code = c.code) , '--') ,"
					+ " ((case stateflag when '1' then '打印未回复' when '2' then '已回复' else '' end)))"
					+ " from loprtmanager c"
					+ " where c.standbyflag3 = a.missionid" + " and code = '25'"
					+ " and rownum = 1)," + " (select makedate"
					+ " from loprtmanager c"
					+ " where c.standbyflag3 = a.missionid" + " and code = '25'"
					+ " and rownum = 1)," + " (select concat(concat((select codename"
					+ " from ldcode" + " where codetype = 'rebquwnotice'"
					+ " and code = c.code) , '--') ,"
					+ " ((case stateflag when '1' then '打印未回复' when '2' then '已回复' else '' end)))"
					+ " from loprtmanager c"
					+ " where c.standbyflag3 = a.missionid" + " and code = '27'"
					+ " and rownum = 1)," + " (select makedate"
					+ " from loprtmanager c"
					+ " where c.standbyflag3 = a.missionid" + " and code = '27'"
					+ " and rownum = 1)" + " from lwmission a" + " where 1 = 1"
					+ " and activityid = '0000000005'"
					+ " and processid = '0000000000'"
					+ " and defaultoperator is null" + " and missionprop7 like concat('"
					+ "?mManageCom?" + "','%')" 
					 +" and  exists (select 'X'" + " from lpedorapp p "
					+ " where p.otherno = a.missionprop2 "
					+ " and p.edoracceptno=a.missionprop1 "
					+ " and p.edorappdate >= '" + "?mStartDate?" + "'"
					+ " and p.edorappdate <= '" + "?mEndDate?" + "')"					
					 + mUserCodeSQL +" order by MissionProp7";
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			strsql = " select " + " MissionProp7," + " missionprop1,"
					+ " missionprop2," + " missionprop4,"
					+ " (select b.edorappdate" + " from lpedorapp b"
					+ " where b.edoracceptno = a.missionprop1),"
					+ " (select b.uwoperator" + " from lpedorapp b"
					+ " where b.edoracceptno = a.missionprop1),"
					+ " (case missionprop18 when '1' then '未人工核保' when '2' then '核保已回复' when '3' then '核保未回复' when '4' then '强制人工核保' else '' end),"
					+ " (select concat(concat((select codename" + " from ldcode"
					+ " where codetype = 'rebquwnotice'"
					+ " and code = c.code) , '--'),"
					+ " ((case stateflag when '1' then '打印未回复' when '2' then '已回复' else '' end)))"
					+ " from loprtmanager c"
					+ " where c.standbyflag3 = a.missionid" + " and code = '23'"
					+ " limit 0,1)," + " (select makedate"
					+ " from loprtmanager c"
					+ " where c.standbyflag3 = a.missionid" + " and code = '23'"
					+ " limit 0,1)," + " (select concat(concat((select codename"
					+ " from ldcode" + " where codetype = 'rebquwnotice'"
					+ " and code = c.code) , '--') ,"
					+ " ((case stateflag when '1' then '打印未回复' when '2' then '已回复' else '' end)))"
					+ " from loprtmanager c"
					+ " where c.standbyflag3 = a.missionid" + " and code = '24'"
					+ " limit 0,1)," + " (select makedate"
					+ " from loprtmanager c"
					+ " where c.standbyflag3 = a.missionid" + " and code = '24'"
					+ " limit 0,1)," + " (select concat(concat( (select codename"
					+ " from ldcode" + " where codetype = 'rebquwnotice'"
					+ " and code = c.code) , '--') ,"
					+ " ((case stateflag when '1' then '打印未回复' when '2' then '已回复' else '' end)))"
					+ " from loprtmanager c"
					+ " where c.standbyflag3 = a.missionid" + " and code = '25'"
					+ " limit 0,1)," + " (select makedate"
					+ " from loprtmanager c"
					+ " where c.standbyflag3 = a.missionid" + " and code = '25'"
					+ " limit 0,1)," + " (select concat(concat((select codename"
					+ " from ldcode" + " where codetype = 'rebquwnotice'"
					+ " and code = c.code) , '--') ,"
					+ " ((case stateflag when '1' then '打印未回复' when '2' then '已回复' else '' end)))"
					+ " from loprtmanager c"
					+ " where c.standbyflag3 = a.missionid" + " and code = '27'"
					+ " limit 0,1)," + " (select makedate"
					+ " from loprtmanager c"
					+ " where c.standbyflag3 = a.missionid" + " and code = '27'"
					+ " limit 0,1)" + " from lwmission a" + " where 1 = 1"
					+ " and activityid = '0000000005'"
					+ " and processid = '0000000000'"
					+ " and defaultoperator is null" + " and missionprop7 like concat('"
					+ "?mManageCom?" + "','%')" 
					 +" and  exists (select 'X'" + " from lpedorapp p "
					+ " where p.otherno = a.missionprop2 "
					+ " and p.edoracceptno=a.missionprop1 "
					+ " and p.edorappdate >= '" + "?mStartDate?" + "'"
					+ " and p.edorappdate <= '" + "?mEndDate?" + "')"					
					 + mUserCodeSQL +" order by MissionProp7";
		}
		
		logger.debug("SQL:" + strsql);
		sqlbv.sql(strsql);
		sqlbv.put("mManageCom", mManageCom);
		sqlbv.put("mStartDate", mStartDate);
		sqlbv.put("mEndDate", mEndDate);
		sqlbv.put("mUserCode", mUserCode);
		tssrs = texesql.execSQL(sqlbv);
		if (tssrs == null || tssrs.getMaxRow() <= 0) {
			CError.buildErr(this, mStartDate + "至" + mEndDate + "之间无待打印清单！");
			return false;
		}

		ListTable tContListTable = new ListTable();
		tContListTable.setName("BILL");
		String strLine[] = null;
		int j;//计数

		int tGetPayCount = tssrs.getMaxRow();
		double tGetPayMoney = 0;
		for (int i = 1; i <= tssrs.getMaxRow(); i++) {
			strLine = new String[16];
			for (j = 1; j < 16; j++) {
				strLine[0]=String.valueOf(i);
				strLine[j] = tssrs.GetText(i, j);
			}
			//tGetPayMoney += Double.parseDouble(tssrs.GetText(i, 8));
			tContListTable.add(strLine);
		}

		mManageCom = BqNameFun.getCBranch(mManageCom);
		mStartDate = BqNameFun.getChDate(mStartDate);
		mEndDate = BqNameFun.getChDate(mEndDate);
		//模版自上而下的元素
		texttag.add("ManageCom", mManageCom);
		texttag.add("StartDate", mStartDate);
		texttag.add("EndDate", mEndDate);
		texttag.add("SumPayCount", tGetPayCount);
		texttag.add("Title", "核保状态清单");
		//texttag.add("SumPayMoney", PubFun.round(tGetPayMoney, 2));
		texttag
				.add("PrintDate", BqNameFun
						.getChDate((PubFun.getCurrentDate())));

		if (texttag.size() > 0) {
			tXmlExport.addTextTag(texttag);
		}
		tXmlExport.addListTable(tContListTable, strLine);
		//tXmlExport.outputDocumentToFile("c:\\XML\\", "EdorUWSateBill.vts"); 
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
