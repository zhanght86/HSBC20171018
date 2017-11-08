package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import java.util.regex.Pattern;

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
 * 清单名称：红利清单
 */

public class EdorBonusBillPrintBL implements BqBill {
private static Logger logger = Logger.getLogger(EdorBonusBillPrintBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	private TransferData mTransferData = new TransferData();

	private VData mInputData = new VData();

	private String mOperate = "";

	private GlobalInput mGlobalInput = new GlobalInput();

	public static final String VTS_NAME = "EdorBonusBill.vts";

	private TextTag texttag = new TextTag();

	private String mManageCom = "";

	private String mSaleChnl = "";

	private String mStartDate = "";

	private String mEndDate = "";

	private String mFinaYear = "";

	private String mEdorType = "";

	private String mSaleChlSQL = "";

	private String mUserCodeSQL = "";

	private String mEdorTypeSQL = "";

	private String mOrderSQL = "order by a.managecom,a.edortype";

	private ListTable tBonusListTable = new ListTable();

	private XmlExport tXmlExport = new XmlExport();

	public EdorBonusBillPrintBL() {
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
			tError.moduleName = "EdorBonusBillPrintBL";
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
		mSaleChnl = (String) mTransferData.getValueByName("SaleChnl");
		//处理渠道
		if (mSaleChnl != null && !"".equals(mSaleChnl)) {
			mSaleChlSQL = "and exists (select 'X'" + "from lccont p "
					+ "where p.contno = a.otherno " + " and p.salechnl = '"
					+ "?mSaleChnl?" + "')";
		}
		mFinaYear = (String) mTransferData.getValueByName("FinaYear");
		if (mFinaYear != null && !"".equals(mFinaYear)) {
			mUserCodeSQL = " and a.remark = '" + "?mFinaYear?" + "'";
		}
		mEdorTypeSQL= "and exists (select 'X'" + "from lobonuspol p "
		+ "where p.contno = a.otherno " + " and p.fiscalyear =a.remark and p.sgetdate>='"+"?mStartDate?"+"' and p.sgetdate<='"+"?mEndDate?"+"')";
		return true;
	}

	private boolean preparePrintData() {
		tXmlExport.createDocument(VTS_NAME, "printer"); //初始化xml文档

		SSRS tssrs = new SSRS();
		ExeSQL texesql = new ExeSQL();
		String strsql = "";
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			strsql = " select rownum,substr(b.managecom, 1, 6),"//三级机构代码
				    +" (select d.shortname from ldcom d where d.comcode = b.managecom),"//四级机构名称
				    +" (select lacom.name from lacom where lacom.agentcom = b.agentcom),"//代理机构名称
				    +" (select codename from ldcode where code = b.salechnl and codetype = 'salechnl'),"//销售渠道	
					+ " b.appntname,b.insuredname,"//投保人姓名、被保人姓名
					+ " (select r.postaladdress"//投保人地址
					+ " from lcaddress r"
					+ " where (r.customerno, r.addressno) ="
					+ " (select s.appntno, s.addressno"
					+ " from lcappnt s"
					+ " where s.contno = a.otherno)),"
					+ " (select r.zipcode"//投保人邮编
					+ " from lcaddress r"
					+ " where (r.customerno, r.addressno) ="
					+ " (select s.appntno, s.addressno"
					+ " from lcappnt s"
					+ " where s.contno = a.otherno)),"
					+ " (select r.mobile"//投保人手机号
					+ " from lcaddress r"
					+ " where (r.customerno, r.addressno) ="
					+ " (select s.appntno, s.addressno"
					+ " from lcappnt s"
					+ " where s.contno = a.otherno)),"
					+ " a.remark,a.otherno,b.cvalidate,"  //红利分配年度、合同号、生效日期
					+ " (select (case when sum(sumactupaymoney) is not null then sum(sumactupaymoney)  else 0 end) from ljapayperson a where contno=b.contno "//上期保费
					+ " and mainpolyear=(select max(mainpolyear)-1 from ljapayperson where contno=b.contno)),"
					+ " (select (case when sum(BonusMoney) is not null then sum(BonusMoney)  else 0 end) from lobonuspol where contno=b.contno and fiscalyear<'"+"?mFinaYear?"+"'),"//已分配红利总金额
					+ " (select SGetDate from lobonuspol where contno = a.otherno and fiscalyear = a.remark and rownum=1),"//应分配日期
					+ " (select AGetDate from lobonuspol where contno = a.otherno and fiscalyear = a.remark and rownum=1),"//实分配日期
					+ " (select (select riskname"//险种名称
					+ " from lmriskapp m"
					+ " where m.riskcode = d.riskcode)"
					+ " from lcpol d"
					+ " where d.contno = a.otherno"
					+ " and d.polno = d.mainpolno),"				
					+ " ((case (select (case when u.bonusgetmode is not null then u.bonusgetmode  else '1' end)" + " from lobonuspol u"//领取方式
					+ " where u.contno = a.otherno"
					+ " and u.fiscalyear = a.remark and rownum=1) when '1' then '累计生息' when '2' then '现金领取' when '3' then '抵交续期保费' when '5' then '增额交清' else '累计生息' end)),"
					+ " a.standbyflag1,"//本期红利
					+ " a.standbyflag2," //金额延期利息
					+ " a.standbyflag3," //本息合计
					+ " a.standbyflag4,"//累计帐户余额
					+ " a.standbyflag5," //抵交金额
					+ " a.standbyflag6," //购买增额缴清保额
					+ " a.standbyflag7,"//分配后累计保额
					+" (select f.name from laagent f where f.agentcode = b.agentcode), "//服务人员姓名
					+" (select f.agentcode from laagent f where f.agentcode = b.agentcode),"//业务代码
					+" (select f.mobile from laagent f where f.agentcode = b.agentcode) "//服务人员联系方式
					+ " From LOPRTManager a,lccont b where a.otherno=b.contno"
					+ " and a.managecom like concat('" + "?mManageCom?" + "','%')"
					+ mSaleChlSQL 
					+ mUserCodeSQL + mEdorTypeSQL
					+ " and a.code='30'";
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			strsql = " select (@rownum := @rownum + 1) AS rownum,t.* from (select @rownum:=0,substr(b.managecom, 1, 6),"//三级机构代码
				    +" (select d.shortname from ldcom d where d.comcode = b.managecom),"//四级机构名称
				    +" (select lacom.name from lacom where lacom.agentcom = b.agentcom),"//代理机构名称
				    +" (select codename from ldcode where code = b.salechnl and codetype = 'salechnl'),"//销售渠道	
					+ " b.appntname,b.insuredname,"//投保人姓名、被保人姓名
					+ " (select r.postaladdress"//投保人地址
					+ " from lcaddress r"
					+ " where (r.customerno, r.addressno) ="
					+ " (select s.appntno, s.addressno"
					+ " from lcappnt s"
					+ " where s.contno = a.otherno)),"
					+ " (select r.zipcode"//投保人邮编
					+ " from lcaddress r"
					+ " where (r.customerno, r.addressno) ="
					+ " (select s.appntno, s.addressno"
					+ " from lcappnt s"
					+ " where s.contno = a.otherno)),"
					+ " (select r.mobile"//投保人手机号
					+ " from lcaddress r"
					+ " where (r.customerno, r.addressno) ="
					+ " (select s.appntno, s.addressno"
					+ " from lcappnt s"
					+ " where s.contno = a.otherno)),"
					+ " a.remark,a.otherno,b.cvalidate,"  //红利分配年度、合同号、生效日期
					+ " (select (case when sum(sumactupaymoney) is not null then sum(sumactupaymoney)  else 0 end) from ljapayperson a where contno=b.contno "//上期保费
					+ " and mainpolyear=(select max(mainpolyear)-1 from ljapayperson where contno=b.contno)),"
					+ " (select (case when sum(BonusMoney) is not null then sum(BonusMoney)  else 0 end) from lobonuspol where contno=b.contno and fiscalyear<'"+"?mFinaYear?"+"'),"//已分配红利总金额
					+ " (select SGetDate from lobonuspol where contno = a.otherno and fiscalyear = a.remark limit 0,1),"//应分配日期
					+ " (select AGetDate from lobonuspol where contno = a.otherno and fiscalyear = a.remark limit 0,1),"//实分配日期
					+ " (select (select riskname"//险种名称
					+ " from lmriskapp m"
					+ " where m.riskcode = d.riskcode)"
					+ " from lcpol d"
					+ " where d.contno = a.otherno"
					+ " and d.polno = d.mainpolno),"				
					+ " ((case (select (case when u.bonusgetmode is not null then u.bonusgetmode  else '1' end)" + " from lobonuspol u"//领取方式
					+ " where u.contno = a.otherno"
					+ " and u.fiscalyear = a.remark limit 0,1) when '1' then '累计生息' when '2' then '现金领取' when '3' then '抵交续期保费' when '5' then '增额交清' else '累计生息' end)),"
					+ " a.standbyflag1,"//本期红利
					+ " a.standbyflag2," //金额延期利息
					+ " a.standbyflag3," //本息合计
					+ " a.standbyflag4,"//累计帐户余额
					+ " a.standbyflag5," //抵交金额
					+ " a.standbyflag6," //购买增额缴清保额
					+ " a.standbyflag7,"//分配后累计保额
					+" (select f.name from laagent f where f.agentcode = b.agentcode), "//服务人员姓名
					+" (select f.agentcode from laagent f where f.agentcode = b.agentcode),"//业务代码
					+" (select f.mobile from laagent f where f.agentcode = b.agentcode) "//服务人员联系方式
					+ " From LOPRTManager a,lccont b where a.otherno=b.contno"
					+ " and a.managecom like concat('" + "?mManageCom?" + "','%')"
					+ mSaleChlSQL 
					+ mUserCodeSQL + mEdorTypeSQL
					+ " and a.code='30') t ";
		}
		
		logger.debug("SQL:" + strsql);
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(strsql);
		sqlbv.put("mManageCom", mManageCom);
		sqlbv.put("mSaleChnl", mSaleChnl);
		sqlbv.put("mFinaYear", mFinaYear);
		sqlbv.put("mStartDate", mStartDate);
		sqlbv.put("mEndDate", mEndDate);
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
			strLine = new String[29];
			for (j = 0; j < 19; j++) {
				strLine[j] = tssrs.GetText(i, j + 1);
			}
			for (j = 19; j < 26; j++) {
				//   险种	本期红利领取方式	本期红利金额	红利延迟发放利息	本期红利本息合计	累积红利帐户余额（含本期）	本期红利抵交保险费金额	本期红利购买增额交清的保额	红利分配后累计保额（含本期）
				String tPolBonusMoney=tssrs.GetText(i, j + 1);
				Pattern p = Pattern.compile("[|]"); //选出| //用正则切开，也可以直接用字符的split,参数是一样
				String PolBonusMoney[]=null;
				double tSumMoney=0;
				if(!"".equals(tPolBonusMoney) && tPolBonusMoney!=null)
				{
					PolBonusMoney=p.split(tPolBonusMoney);
                    if(PolBonusMoney.length>0 && PolBonusMoney!=null)
                    {
    					for (int k = 0; k < PolBonusMoney.length; k++) {
    						tSumMoney+=Double.parseDouble(PolBonusMoney[k].substring(PolBonusMoney[k].indexOf("-")+1, PolBonusMoney[k].length()));
    					}                    	
                    }
				}
				strLine[j] = String.valueOf(tSumMoney);
			}
			for (j = 26; j < 29; j++) {
				strLine[j] = tssrs.GetText(i, j + 1);
			}
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
		//texttag.add("SumPayMoney", PubFun.round(tGetPayMoney, 2));
		texttag
				.add("PrintDate", BqNameFun
						.getChDate((PubFun.getCurrentDate())));

		if (texttag.size() > 0) {
			tXmlExport.addTextTag(texttag);
		}
		tXmlExport.addListTable(tContListTable, strLine);
		//tXmlExport.outputDocumentToFile("c:\\XML\\", "EdorBonusBill.vts"); 
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
