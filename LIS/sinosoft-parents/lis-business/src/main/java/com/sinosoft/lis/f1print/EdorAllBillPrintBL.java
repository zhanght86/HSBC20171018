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
 * 清单名称：保全操作统计一览表
 */

public class EdorAllBillPrintBL implements BqBill {
private static Logger logger = Logger.getLogger(EdorAllBillPrintBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	private TransferData mTransferData = new TransferData();

	private VData mInputData = new VData();

	private String mOperate = "";

	private GlobalInput mGlobalInput = new GlobalInput();

	public static final String VTS_NAME = "EdorAllBill.vts";

	private TextTag texttag = new TextTag();

	private String mManageCom = "";

	private String mSaleChnl = "";

	private String mStartDate = "";

	private String mEndDate = "";

	private String mUserCode = "";
    /**保全状态*/
	private String mEdorState="";
	
	private String mEdorType = "";

	private String mSaleChlSQL = "";

	private String mUserCodeSQL = "";

	private String mEdorTypeSQL = "";
	
	private String mEdorStateSQL="";
	
	private String mPayFlag= "";
	
	private String mPayFlagSQL= "";
	private String mScanFlag= "";	
	private String mScanFlagSQL= "";	
	private String mRePortFlag= "";
	private String mRePortFlagSQL= "";	
	
	private int tComLength=0;
			

	private String mOrderSQL = "order by a.managecom,a.otherno";

	private ListTable tBonusListTable = new ListTable();

	private XmlExport tXmlExport = new XmlExport();

	public EdorAllBillPrintBL() {
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
			tError.moduleName = "EdorAllBillPrintBL";
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
		if(mManageCom.length()==2)
		{
			tComLength=4;
		}else
		if(mManageCom.length()==4)
		{
			tComLength=6;
		}else
		{
			tComLength=6;
		}
		mSaleChnl = (String) mTransferData.getValueByName("SaleChnl");
		//处理渠道
		if (mSaleChnl != null && !"".equals(mSaleChnl)) {
			mSaleChlSQL = "and exists (select 'X'" + "from lccont p "
					+ "where p.contno = a.otherno " + " and p.salechnl = '"
					+ "?mSaleChnl?" + "')";
		}
		mUserCode = (String) mTransferData.getValueByName("UserCode");
		if (mUserCode != null && !"".equals(mUserCode)) {
			mUserCodeSQL = " and a.operator = '" + "?mUserCode?" + "'";
		}
		mEdorType = (String) mTransferData.getValueByName("EdorType");
		if (mEdorType != null && !"".equals(mEdorType)) {
			mEdorTypeSQL = " and  exists (select 'X'" + " from lpedoritem p "
					+ " where p.contno = a.otherno "
					+ " and p.edoracceptno=a.edoracceptno "
					+ " and p.edortype = '" + "?mEdorType?" + "')";
		} else {
			mEdorTypeSQL = " and  exists (select 'X'"
					+ " from lpedoritem p "
					+ " where p.contno = a.otherno "
					+ " and p.edoracceptno=a.edoracceptno "
					+ " and p.edortype in (select code from ldcode where codetype='edortype' ))";
		}
		mEdorState = (String) mTransferData.getValueByName("EdorState");
		if (mEdorState != null && !"".equals(mEdorState)) {
			mEdorStateSQL = " and a.EdorState = '" + "?mEdorState?" + "'";
		}
	
		mPayFlag = (String) mTransferData.getValueByName("PayFlag");
		if (mPayFlag != null && !"".equals(mPayFlag)) {
			switch(Integer.parseInt(mPayFlag))
			{
			   case 1: //付费清单
					mPayFlagSQL = " and  exists ( select 1" + " from ljtempfee c"
					+ " where c.otherno = a.edoracceptno"
					+ " and c.othernotype = '10')";
					break;
			   case 2: //未付费清单
					mPayFlagSQL = " and not exists ( select 1" + " from ljtempfee c"
					+ " where c.otherno = a.edoracceptno"
					+ " and c.othernotype = '10') and  exists ( select 1" + " from ljspay c"
					+ " where c.otherno = a.edoracceptno"
					+ " and c.othernotype = '10' and  SumDuePayMoney >0)";
					break;
			}

		}
		
		mScanFlag = (String) mTransferData.getValueByName("ScanFlag");
		if (mScanFlag != null && !"".equals(mScanFlag)) {
			switch(Integer.parseInt(mScanFlag))
			{
			   case 1: //扫描清单
				   if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
					   mScanFlagSQL = " and  exists ( select 1" + " from es_doc_main"
								+ " where doccode = a.edoracceptno"
								+ " and subtype = 'BQ001' and rownum=1)";
				   }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
					   mScanFlagSQL = " and  exists ( select 1" + " from es_doc_main"
								+ " where doccode = a.edoracceptno"
								+ " and subtype = 'BQ001'  limit 0,1)";
				   }
					break;
			   case 2: //未扫描清单
				   if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
					   mScanFlagSQL = " and not exists ( select 1" + " from es_doc_main"
								+ " where doccode = a.edoracceptno"
								+ " and subtype = 'BQ001' and rownum=1)";
				   }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
					   mScanFlagSQL = " and not exists ( select 1" + " from es_doc_main"
								+ " where doccode = a.edoracceptno"
								+ " and subtype = 'BQ001'  limit 0,1)";
				   }
					break;
			}
		}
		mRePortFlag = (String) mTransferData.getValueByName("RePortFlag");
		if (mRePortFlag != null && !"".equals(mRePortFlag)) {
		
			switch(Integer.parseInt(mRePortFlag))
			{
			   case 1: //退保清单
				    //mEdorTypeSQL="";
				   if("".equals(mEdorTypeSQL))
				   {
						mRePortFlagSQL = " and  exists (select 'X'"
							+ " from lpedoritem p "
							+ " where p.contno = a.otherno "
							+ " and p.edoracceptno=a.edoracceptno "
							+ " and p.edortype in ('CT','XT'))";
				   }
		
					break;
			   case 2: //契撤清单
				    //mEdorTypeSQL="";
				   if("".equals(mEdorTypeSQL))
				   {
						mRePortFlagSQL = " and  exists (select 'X'"
							+ " from lpedoritem p "
							+ " where p.contno = a.otherno "
							+ " and p.edoracceptno=a.edoracceptno "
							+ " and p.edortype in ('WT'))";
				   }

					break;
			   case 3: //红利给付清单
				    //mEdorTypeSQL="";
				   if("".equals(mEdorTypeSQL))
				   {
						mRePortFlagSQL = " and  exists (select 'X'"
							+ " from lpedoritem p "
							+ " where p.contno = a.otherno "
							+ " and p.edoracceptno=a.edoracceptno "
							+ " and p.edortype in ('DB'))";
				   }
				   
					break;
			   case 4://保全未收费清单
				    mPayFlagSQL=""; //如果是保全未收费清单,则不再需要判断是否收费
					mRePortFlagSQL =" and not exists ( select 1" + " from ljtempfee c"
					+ " where c.otherno = a.edoracceptno"
					+ " and c.othernotype = '10') and  exists ( select 1" + " from ljspay c"
					+ " where c.otherno = a.edoracceptno"
					+ " and c.othernotype = '10' and  SumDuePayMoney >0)";
					break;
			   case 5://保全未确认清单
					mRePortFlagSQL = " and a.EdorState = '3'";
					break;
			   case 6://档案扫描清单

//    			    if("".equals(mScanFlag)||mScanFlag==null)
//				    {
//					     mErrors.addOneError(new CError("档案扫描清单,扫描标志不能为空！"));
//				        return false;					    	
//				    }
//					mRePortFlagSQL =" and  exists ( select 1" + " from es_doc_main"
//					+ " where doccode = a.edoracceptno"
//					+ " and subtype = 'BQ001' and rownum=1 )";
					break;
			}

		}	
//		else
//			{mErrors.addOneError(new CError("没有得到足够的信息:报表类型不能为空！"));
//		        return false;	
//		}
		
				
		return true;
	}

	private boolean preparePrintData() {
		tXmlExport.createDocument(VTS_NAME, "printer"); //初始化xml文档
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		SSRS tssrs = new SSRS();
		ExeSQL texesql = new ExeSQL();
		String strsql = "";
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			strsql = " select /*rule*/"
				    + " "
				    + " substr(a.managecom,1,"+tComLength+"),"
				    + " (select min(c.riskcode)"  //防止多主险子查询返回多个行
				    + " from lcpol c"
				    + " where c.contno = a.otherno"
				    + " and c.polno = c.mainpolno and rownum=1),"
				    + " a.otherno,"
				    + " a.edoracceptno,"
				    + " a.edorconfno,"
				    + " (select (select codename from ldcode where codetype='edortype' and code=c.edortype) from lpedoritem c where c.edoracceptno = a.edoracceptno and rownum=1) edortype,"
				    + " a.edorappdate,"
				    + " (select username from lduser where usercode = a.operator),"
				     ////nvl(sum(decode(a.getflag,'1',a.getmoney,'0',-a.getmoney)),0)
				    + " (select  getmoney from lpedoritem c where c.edoracceptno = a.edoracceptno  and rownum=1),"
				    + " (select codename"
			        + " from ldcode"
			        + " where codetype = 'edorgetpayform'"
				    + " and code = a.getform),"
					+ " (case (select 1"
					+ " from ljtempfee c"
					+ " where c.otherno = a.edoracceptno"
					+ " and c.othernotype = '10')"
					+ " when 1 then '已缴费' else '未缴费' end),"
					+ " (select codename"
					+ " from ldcode d"
					+ " where d.code = a.edorstate"
					+ " and d.codetype = 'edorstate'),"
					+ " (select edorreason from lpedoritem c where c.edoracceptno = a.edoracceptno and rownum=1) edorreason,"
					+ " (case (select 1"
					+ " from es_doc_main"
					+ " where doccode = a.edoracceptno"
					+ " and subtype = 'BQ001' and rownum=1) when 1 then '已扫描' else '未扫描' end),"
					+ " (select l.appntname from lccont l where l.contno = a.otherno),"
					+ " (select concat(concat(r.phone,' '),r.mobile)" + " from lcaddress r"
					+ " where (r.customerno, r.addressno) ="
					+ " (select s.appntno, s.addressno" + " from lcappnt s"
					+ " where s.contno = a.otherno))," + " (select f.name"
					+ " from laagent f, lccont b"
					+ " where f.agentcode = b.agentcode"
					+ " and b.contno = a.otherno)," + " (select f.mobile"
					+ " from laagent f, lccont b"
					+ " where f.agentcode = b.agentcode"
					+ " and b.contno = a.otherno)" + " From lpedorapp a"
					+ " where 1 = 1" 
					+ " and othernotype = '3'" + " and a.edorappdate >= '"
					+ "?mStartDate?" + "'" + " and a.edorappdate <= '" + "?mEndDate?" + "'"
					+ " and a.managecom like concat('" + "?mManageCom?" + "','%')" + mSaleChlSQL
					+ mEdorTypeSQL + mUserCodeSQL+mRePortFlagSQL+mScanFlagSQL+mPayFlagSQL+mEdorStateSQL+mOrderSQL;
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			strsql = " select "
					+ " "
					+ " substr(a.managecom,1,"+tComLength+"),"
					+ " (select min(c.riskcode)"  //防止多主险子查询返回多个行
					+ " from lcpol c"
					+ " where c.contno = a.otherno"
					+ " and c.polno = c.mainpolno  limit 0,1),"
					+ " a.otherno,"
					+ " a.edoracceptno,"
					+ " a.edorconfno,"
					+ " (select (select codename from ldcode where codetype='edortype' and code=c.edortype) from lpedoritem c where c.edoracceptno = a.edoracceptno  limit 0,1) edortype,"
					+ " a.edorappdate,"
					+ " (select username from lduser where usercode = a.operator),"
					////nvl(sum(decode(a.getflag,'1',a.getmoney,'0',-a.getmoney)),0)
					+ " (select  getmoney from lpedoritem c where c.edoracceptno = a.edoracceptno   limit 0,1),"
					+ " (select codename"
					+ " from ldcode"
					+ " where codetype = 'edorgetpayform'"
					+ " and code = a.getform),"
					+ " select (case (select 1"
					+ " from ljtempfee c"
					+ " where c.otherno = a.edoracceptno"
					+ " and c.othernotype = '10'),"
					+ " when 1 then '已缴费' else '未缴费' end),"
					+ " (select codename"
					+ " from ldcode d"
					+ " where d.code = a.edorstate"
					+ " and d.codetype = 'edorstate'),"
					+ " (select edorreason from lpedoritem c where c.edoracceptno = a.edoracceptno  limit 0,1) edorreason,"
					+ " (case (select 1"
					+ " from es_doc_main"
					+ " where doccode = a.edoracceptno"
					+ " and subtype = 'BQ001'  limit 0,1) when 1 then '已扫描' else '未扫描' end),"
					+ " (select l.appntname from lccont l where l.contno = a.otherno),"
					+ " (select concat(concat(r.phone,' '),r.mobile)" + " from lcaddress r"
					+ " where (r.customerno, r.addressno) ="
					+ " (select s.appntno, s.addressno" + " from lcappnt s"
					+ " where s.contno = a.otherno))," + " (select f.name"
					+ " from laagent f, lccont b"
					+ " where f.agentcode = b.agentcode"
					+ " and b.contno = a.otherno)," + " (select f.mobile"
					+ " from laagent f, lccont b"
					+ " where f.agentcode = b.agentcode"
					+ " and b.contno = a.otherno)" + " From lpedorapp a"
					+ " where 1 = 1" 
					+ " and othernotype = '3'" + " and a.edorappdate >= '"
					+ "?mStartDate?" + "'" + " and a.edorappdate <= '" + "?mEndDate?" + "'"
					+ " and a.managecom like concat('" + "?mManageCom?" + "','%')" + mSaleChlSQL
					+ mEdorTypeSQL + mUserCodeSQL+mRePortFlagSQL+mScanFlagSQL+mPayFlagSQL+mEdorStateSQL+mOrderSQL;
		}
		
		sqlbv.sql(strsql);
		sqlbv.put("mStartDate", mStartDate);
		sqlbv.put("mEndDate", mEndDate);
		sqlbv.put("mManageCom", mManageCom);
		sqlbv.put("mSaleChnl", mSaleChnl);
		sqlbv.put("mEdorType", mEdorType);
		sqlbv.put("mUserCode", mUserCode);
		sqlbv.put("mEdorState", mEdorState);
		
		logger.debug("SQL:" + strsql);
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
			strLine = new String[19];
			strLine[0]=String.valueOf(i);
			for (j = 1; j < 19; j++) {
				strLine[j] = tssrs.GetText(i, j );
			}
			tGetPayMoney += Double.parseDouble(tssrs.GetText(i, 9));
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
		texttag.add("SumPayMoney", PubFun.round(tGetPayMoney, 2));
		texttag
				.add("PrintDate", BqNameFun
						.getChDate((PubFun.getCurrentDate())));

		if (texttag.size() > 0) {
			tXmlExport.addTextTag(texttag);
		}
		tXmlExport.addListTable(tContListTable, strLine);
		//tXmlExport.outputDocumentToFile("c:\\XML\\", "EdorAllBill.vts"); 
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
