package com.sinosoft.lis.get;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.BqCalBL;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCInsureAccClassDB;
import com.sinosoft.lis.db.LCInsureAccDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LJSGetDB;
import com.sinosoft.lis.db.LJSGetDrawDB;
import com.sinosoft.lis.db.LMDutyGetDB;
import com.sinosoft.lis.db.LMRiskInsuAccDB;
import com.sinosoft.lis.pubfun.AccountManage;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.InsuAccBala;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCInsureAccClassSchema;
import com.sinosoft.lis.schema.LCInsureAccSchema;
import com.sinosoft.lis.schema.LCInsureAccTraceSchema;
import com.sinosoft.lis.schema.LJSGetDrawSchema;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCInsureAccTraceSet;
import com.sinosoft.lis.vschema.LJSGetDrawSet;
import com.sinosoft.lis.vschema.LJSGetSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class PersonPaytoAccBL {
private static Logger logger = Logger.getLogger(PersonPaytoAccBL.class);

	public CErrors mErrors = new CErrors();
	private VData mInputData ;
	private String mGetNoticeNO ; 
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();
	private GlobalInput mGlobalInput ;
	private LCInsureAccSet mLCInsureAccSet_insert = new LCInsureAccSet();
	private LCInsureAccClassSet mLCInsureAccClassSet_insert =  new LCInsureAccClassSet();
	private LCInsureAccSet mLCInsureAccSet_update = new LCInsureAccSet();
	private LCInsureAccClassSet mLCInsureAccClassSet_update =  new LCInsureAccClassSet();
	private LCInsureAccTraceSet mLCInsureAccTraceSet_insert = new LCInsureAccTraceSet();
	private LCGetSet mLCGetSet = new LCGetSet();
	private LJSGetSet mLJSGetSet = new LJSGetSet();
	private LJSGetDrawSet mLJSGetDrawSet = new LJSGetDrawSet();
	private MMap mMap = new MMap();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public boolean submitData(VData cInputData, String cOperate)
	{
		this.mInputData = cInputData;
		if(!getInputData(cInputData))
		{
			return false;
		}
		if(!checkData())
		{
			System.out.println("ss");
			return false;
		}
		if(!dealData())
		{
			return false;
		}
		
		if(!prepareOutputData())
		{
			return false;
		}
		PubSubmit tPubSubmit = new PubSubmit();
		if(!tPubSubmit.submitData(mInputData))
		{
			CError.buildErr(this, tPubSubmit.mErrors.getFirstError());
			return false;
		}
		return true;
	}
	
	private boolean prepareOutputData()
	{
		if(this.mLCInsureAccSet_insert.size()>0&&this.mLCInsureAccClassSet_insert.size()>0)
		{
			this.mMap.put(this.mLCInsureAccSet_insert, "INSERT");
			this.mMap.put(this.mLCInsureAccClassSet_insert, "INSERT");
			
		}
		else if(this.mLCInsureAccSet_update.size()>0&&this.mLCInsureAccClassSet_update.size()>0)
		{
		   this.mMap.put(this.mLCInsureAccSet_update, "UPDATE");
		   this.mMap.put(this.mLCInsureAccClassSet_update, "UPDATE");
		}
		else
		{
			CError.buildErr(this, "帐户信息获取失败");
			return false;
		}
		
		this.mMap.put(this.mLCInsureAccTraceSet_insert, "INSERT");
		this.mMap.put(this.mLCGetSet, "UPDATE");
		this.mMap.put(this.mLJSGetDrawSet, "DELETE");
		this.mMap.put(this.mLJSGetSet, "DELETE");
		
		mInputData.clear();
		mInputData.add(this.mMap);
		
		return true;
	}
	
	private boolean dealData()
	{
	
		//begin zbx 20110517
		
		LJSGetDB tLJSGetDB = new LJSGetDB();
		tLJSGetDB.setGetNoticeNo(this.mGetNoticeNO);
		LJSGetSet tLJSGetSet=tLJSGetDB.query();
		if(tLJSGetSet==null ||tLJSGetSet.size()<1)
		{
			CError.buildErr(this, "应付总表查询失败");
			return false;
		}
//		if(!tLJSGetDB.getInfo())
//		{
//			CError.buildErr(this, "应付总表查询失败");
//			return false;
//		}
		
		String gettype1_sql = "select b.polno,a.GetType1 from LMDutyGet a,ljsgetdraw b where a.getdutycode=b.getdutycode  and  b. getnoticeno='?getnoticeno?' "
		+" group by b.polno,a.gettype1 ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(gettype1_sql);
		sqlbv.put("getnoticeno", this.mGetNoticeNO);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = tExeSQL.execSQL(sqlbv);
		for (int k=1;k<=tSSRS.getMaxRow();k++)
		{
			String tInsuaccNo = "";
			//String tCurrency = tSSRS.GetText(k, 3);
			if(tSSRS.GetText(k, 2).equals("1"))      //年金
			{
				tInsuaccNo = "000005";//暂时用
			}
			else if(tSSRS.GetText(k, 2).equals("0"))  //满期金
			{
				tInsuaccNo = "000009";
			}
			else
			{
				CError.buildErr(this, "生存领取类型获取失败");
				return false;
			}
			
			LMRiskInsuAccDB tLMRiskInsuAccDB = new LMRiskInsuAccDB();
			tLMRiskInsuAccDB.setInsuAccNo(tInsuaccNo);
			if(!tLMRiskInsuAccDB.getInfo())
			{
				CError.buildErr(this, "险种帐户信息查询失败！");
				return false;
			}
			
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(tSSRS.GetText(k, 1));
			if(!tLCPolDB.getInfo())
			{
				CError.buildErr(this, "险种信息查询失败！");
				return false;
			}
			
			LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
			tLCInsureAccDB.setPolNo(tLCPolDB.getPolNo());
			tLCInsureAccDB.setInsuAccNo(tInsuaccNo);
			LCInsureAccSchema tLCInsureAccSchema = new LCInsureAccSchema();
			if(!tLCInsureAccDB.getInfo())
			{
				
//				保单险种号码	PolNo
				tLCInsureAccSchema.setPolNo(tLCPolDB.getPolNo());
//				保险帐户号码	InsuAccNo
				tLCInsureAccSchema.setInsuAccNo(tInsuaccNo);
//				合同号码	ContNo
				tLCInsureAccSchema.setContNo(tLCPolDB.getContNo());
//				集体合同号码	GrpContNo
				tLCInsureAccSchema.setGrpContNo(tLCPolDB.getGrpContNo());
//				集体保单险种号码	GrpPolNo
				tLCInsureAccSchema.setGrpPolNo(tLCPolDB.getGrpPolNo());
//				印刷号码	PrtNo
				tLCInsureAccSchema.setPrtNo(tLCPolDB.getPrtNo());
//				险种编码	RiskCode
				tLCInsureAccSchema.setRiskCode(tLCPolDB.getRiskCode());
//				账户类型	AccType
				tLCInsureAccSchema.setAccType(tLMRiskInsuAccDB.getAccType());//待定
//				投资类型	InvestType
				tLCInsureAccSchema.setInvestType(tLMRiskInsuAccDB.getInvestType());  //无
//				基金公司代码	FundCompanyCode                    //无
				tLCInsureAccSchema.setFundCompanyCode(tLMRiskInsuAccDB.getFundCompanyCode());
//				被保人客户号码	InsuredNo
				tLCInsureAccSchema.setInsuredNo(tLCPolDB.getInsuredNo());
//				投保人客户号码	AppntNo
				tLCInsureAccSchema.setAppntNo(tLCPolDB.getAppntNo());
//				账户所有者	Owner
				tLCInsureAccSchema.setOwner(tLMRiskInsuAccDB.getOwner());
//				账户结算方式	AccComputeFlag
				tLCInsureAccSchema.setAccComputeFlag(tLMRiskInsuAccDB.getAccComputeFlag());
//				账户成立日期	AccFoundDate
				tLCInsureAccSchema.setAccFoundDate(this.mCurrentDate);
//				账户成立时间	AccFoundTime
				tLCInsureAccSchema.setAccFoundTime(this.mCurrentTime);
//				结算日期	BalaDate
				tLCInsureAccSchema.setBalaDate(tLJSGetSet.get(1).getGetDate());
//				结算时间	BalaTime
				//计算时间应该是应领日的00：00：00
				tLCInsureAccSchema.setBalaTime("00:00:00");
//				累计交费	SumPay
				tLCInsureAccSchema.setSumPay(0);
//				累计领取	SumPaym
				tLCInsureAccSchema.setSumPay(0);
//				期初账户现金余额	LastAccBala
				tLCInsureAccSchema.setLastAccBala(0);
//				期初账户单位数	LastUnitCount
				tLCInsureAccSchema.setLastUnitCount(0);
//				期初账户单位价格	LastUnitPrice
				tLCInsureAccSchema.setLastUnitPrice(0);
//				帐户现金余额	InsuAccBala
				tLCInsureAccSchema.setInsuAccBala(0);
//				帐户单位数	UnitCount
				tLCInsureAccSchema.setUnitCount(0);
//				帐户单位价格	UnitPrice
				tLCInsureAccSchema.setUnitPrice(0);
//				账户可领金额	InsuAccGetMoney
				tLCInsureAccSchema.setInsuAccGetMoney(0);
//				冻结金额	FrozenMoney
				tLCInsureAccSchema.setFrozenMoney(0);
//				状态	State
				tLCInsureAccSchema.setState("0");
//				管理机构	ManageCom
				tLCInsureAccSchema.setManageCom(tLCPolDB.getManageCom());
				tLCInsureAccSchema.setMakeDate(tLCPolDB.getManageCom());
//				操作员	Operator
				tLCInsureAccSchema.setOperator(mGlobalInput.Operator);
//				入机日期	MakeDate
				tLCInsureAccSchema.setMakeDate(this.mCurrentDate);
//				入机时间	MakeTime
				tLCInsureAccSchema.setMakeTime(this.mCurrentTime);
//				最后一次修改日期	ModifyDate
				tLCInsureAccSchema.setModifyDate(this.mCurrentDate);
//				最后一次修改时间	ModifyTime
				tLCInsureAccSchema.setModifyTime(this.mCurrentTime);
				
				tLCInsureAccSchema.setCurrency(tLCPolDB.getCurrency());
				mLCInsureAccSet_insert.add(tLCInsureAccSchema);
			}
			else
			{
				mLCInsureAccSet_update.add(tLCInsureAccDB.getSchema());
			}
			
			LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
			tLCInsureAccClassDB.setPolNo(tLCPolDB.getPolNo());
			tLCInsureAccClassDB.setInsuAccNo(tInsuaccNo);
			LCInsureAccClassSet tLCInsureAccClassSet = tLCInsureAccClassDB.query();
			if(tLCInsureAccClassSet.size()<=0)
			{
				LCInsureAccClassSchema tLCInsureAccClassSchema = new LCInsureAccClassSchema();
//				集体合同号码	GrpContNo
				tLCInsureAccClassSchema.setGrpContNo(tLCPolDB.getGrpContNo());
//				集体保单号码	GrpPolNo
				tLCInsureAccClassSchema.setGrpPolNo(tLCPolDB.getGrpPolNo());
//				合同号码	ContNo
				tLCInsureAccClassSchema.setContNo(tLCPolDB.getContNo());
//				管理机构	ManageCom
				tLCInsureAccClassSchema.setManageCom(tLCPolDB.getManageCom());
//				保单险种号码	PolNo
				tLCInsureAccClassSchema.setPolNo(tLCPolDB.getPolNo());
//				保险帐户号码	InsuAccNo
				tLCInsureAccClassSchema.setInsuAccNo(tInsuaccNo);
//				交费计划编码	PayPlanCode
				tLCInsureAccClassSchema.setPayPlanCode("000000");
//				对应其它号码	OtherNo
				tLCInsureAccClassSchema.setOtherNo(tLCPolDB.getPolNo());
//				对应其它号码类型	OtherType
				tLCInsureAccClassSchema.setOtherType("1");
//				账户归属属性	AccAscription
				tLCInsureAccClassSchema.setAccAscription("1");
//				险种编码	RiskCode
				tLCInsureAccClassSchema.setRiskCode(tLCPolDB.getRiskCode());
//				被保人客户号码	InsuredNo
				tLCInsureAccClassSchema.setInsuredNo(tLCPolDB.getInsuredNo());
//				投保人客户号码	AppntNo
				tLCInsureAccClassSchema.setAppntNo(tLCPolDB.getAppntNo());
//				账户类型	AccType
				tLCInsureAccClassSchema.setAccType(tLCInsureAccSchema.getAccType());
//				账户结算方式	AccComputeFlag
				tLCInsureAccClassSchema.setAccComputeFlag(tLCInsureAccSchema.getAccComputeFlag());
//				账户成立日期	AccFoundDate
				tLCInsureAccClassSchema.setAccFoundDate(tLCInsureAccSchema.getAccFoundDate());
//				账户成立时间	AccFoundTime
				tLCInsureAccClassSchema.setAccFoundTime(tLCInsureAccSchema.getAccFoundTime());
//				结算日期	BalaDate
				tLCInsureAccClassSchema.setBalaDate(tLCInsureAccSchema.getBalaDate());
//				结算时间	BalaTime
				//生成的结息时点应该是应领日的00：00：00
				tLCInsureAccClassSchema.setBalaTime("00:00:00");
//				累计交费	SumPay
				tLCInsureAccClassSchema.setSumPay(0);
//				累计领取	SumPaym
				tLCInsureAccClassSchema.setSumPaym(0);
//				期初账户现金余额	LastAccBala
				tLCInsureAccClassSchema.setLastAccBala(0);
//				期初账户单位数	LastUnitCount
				tLCInsureAccClassSchema.setLastUnitCount(tLCInsureAccSchema.getLastUnitCount());
//				期初账户单位价格	LastUnitPrice
				tLCInsureAccClassSchema.setLastUnitPrice(tLCInsureAccSchema.getLastUnitPrice());
//				保险帐户现金余额	InsuAccBala
				tLCInsureAccClassSchema.setInsuAccBala(0);
//				保险帐户单位数	UnitCount
				tLCInsureAccClassSchema.setUnitCount(tLCInsureAccSchema.getUnitCount());
//				保险账户可领金额	InsuAccGetMoney
				tLCInsureAccClassSchema.setInsuAccGetMoney(0);
//				冻结金额	FrozenMoney
				tLCInsureAccClassSchema.setFrozenMoney(0);
//				状态	State
				tLCInsureAccClassSchema.setState("0");
//				操作员	Operator
				tLCInsureAccClassSchema.setOperator(this.mGlobalInput.Operator);
//				入机日期	MakeDate
				tLCInsureAccClassSchema.setMakeDate(tLCInsureAccSchema.getMakeDate());
//				入机时间	MakeTime
				tLCInsureAccClassSchema.setMakeTime(tLCInsureAccSchema.getMakeTime());
//				最后一次修改日期	ModifyDate
				tLCInsureAccClassSchema.setModifyDate(tLCInsureAccSchema.getModifyDate());
//				最后一次修改时间	ModifyTime
				tLCInsureAccClassSchema.setModifyTime(tLCInsureAccSchema.getModifyTime());
//				归属比例	AscriptRate
				tLCInsureAccClassSchema.setAscriptRate(0);
//				归属类型	AscriptType
//				tLCInsureAccClassSchema.setAscriptType(aAscriptType)
//				保险帐户单位价格	UnitPrice
				tLCInsureAccClassSchema.setUnitPrice(0);
				
				tLCInsureAccClassSchema.setCurrency(tLCPolDB.getCurrency());
				this.mLCInsureAccClassSet_insert.add(tLCInsureAccClassSchema);
			}
			else
			{
				this.mLCInsureAccClassSet_update.add(tLCInsureAccClassSet);
			}
		}
		
		LJSGetDrawDB tLJSGetDrawDB = new LJSGetDrawDB();
		tLJSGetDrawDB.setGetNoticeNo(this.mGetNoticeNO);
		LJSGetDrawSet tLJSGetDrawSet = tLJSGetDrawDB.query(); 
		
		for(int i=1;i<=tLJSGetDrawSet.size();i++)
		{
			LJSGetDrawSchema tLJSGetDrawSchema = tLJSGetDrawSet.get(i);
			LCInsureAccTraceSchema tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
//			集体合同号码	GrpContNo
			tLCInsureAccTraceSchema.setGrpContNo(tLJSGetDrawSchema.getGrpContNo());
//			集体保单险种号码	GrpPolNo
			tLCInsureAccTraceSchema.setGrpPolNo(tLJSGetDrawSchema.getGrpPolNo());
//			合同号码	ContNo
			tLCInsureAccTraceSchema.setContNo(tLJSGetDrawSchema.getContNo());
//			保单险种号码	PolNo
			tLCInsureAccTraceSchema.setPolNo(tLJSGetDrawSchema.getPolNo());
//			流水号	SerialNo
			String tLimit = PubFun.getNoLimit(tLJSGetDrawSchema.getManageCom());
            String serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
			tLCInsureAccTraceSchema.setSerialNo(serNo);
//			保险帐户号码	InsuAccNo
			LMDutyGetDB tLMDutyGetDB = new LMDutyGetDB();
			tLMDutyGetDB.setGetDutyCode(tLJSGetDrawSchema.getGetDutyCode());
			if(!tLMDutyGetDB.getInfo())
			{
				CError.buildErr(this, "给付责任查询失败");
				return false;
			}
			if(tLMDutyGetDB.getGetType1().equals("1"))      //年金
			{
				tLCInsureAccTraceSchema.setInsuAccNo("000005");//暂时用
//				金额类型	MoneyType
				String finType = BqCalBL.getFinType_HL_SC("SC", tLCInsureAccTraceSchema.getInsuAccNo(), "YF");
				if(finType.equals(""))
				{
					CError.buildErr(this, "生存领取财务类型获取失败");
					return false;
				}
				tLCInsureAccTraceSchema.setMoneyType(finType);
			}
			else if(tLMDutyGetDB.getGetType1().equals("0"))  //满期金
			{
//				tInsuaccNo = "000009";
				tLCInsureAccTraceSchema.setInsuAccNo("000009");
//				金额类型	MoneyType
				String finType = BqCalBL.getFinType_HL_SC("SC", tLCInsureAccTraceSchema.getInsuAccNo(), "EF");
				if(finType.equals(""))
				{
					CError.buildErr(this, "生存领取财务类型获取失败");
					return false;
				}
				tLCInsureAccTraceSchema.setMoneyType(finType);
			}
			else
			{
				CError.buildErr(this, "生存领取类型获取失败");
				return false;
			}
//			险种编码	RiskCode
			tLCInsureAccTraceSchema.setRiskCode(tLJSGetDrawSchema.getRiskCode());
//			交费计划编码	PayPlanCode
			tLCInsureAccTraceSchema.setPayPlanCode("000000");
//			对应其它号码	OtherNo
			tLCInsureAccTraceSchema.setOtherNo(tLJSGetDrawSchema.getGetNoticeNo());
//			对应其它号码类型	OtherType
			tLCInsureAccTraceSchema.setOtherType("2");
//			账户归属属性	AccAscription
			tLCInsureAccTraceSchema.setAccAscription("1");

//			本次金额	Money
			tLCInsureAccTraceSchema.setMoney(tLJSGetDrawSchema.getGetMoney());
//			本次单位数	UnitCount
//			交费日期	PayDate
			tLCInsureAccTraceSchema.setPayDate(tLJSGetDrawSchema.getGetDate());
//			状态	State
			tLCInsureAccTraceSchema.setState("0");
//			管理机构	ManageCom
			tLCInsureAccTraceSchema.setManageCom(tLJSGetDrawSchema.getManageCom());
//			操作员	Operator
			tLCInsureAccTraceSchema.setOperator(this.mGlobalInput.Operator);
//			入机日期	MakeDate
			tLCInsureAccTraceSchema.setMakeDate(this.mCurrentDate);
			/*入机时间	MakeTime
    		 * 此字段在结息时会和paydate结合使用计算利息    注意注意
			 * 生存金应领时点应该是paydate的00：00：00时
			 * */
			tLCInsureAccTraceSchema.setMakeTime("00:00:00"); 			
//			最后一次修改日期	ModifyDate
			tLCInsureAccTraceSchema.setModifyDate(this.mCurrentDate);
//			最后一次修改时间	ModifyTime
			tLCInsureAccTraceSchema.setModifyTime(this.mCurrentTime);
//			管理费编码	FeeCode
			tLCInsureAccTraceSchema.setFeeCode("000000");
//			账户批单号码	AccAlterNo
//			账户批单号码类型	AccAlterType
//			交费收据号	PayNo
			tLCInsureAccTraceSchema.setPayNo(tLJSGetDrawSchema.getSerialNo());
			//ADD BY 2011-11-12 这里设置只支持领取币种必须和险种币种一致
			LCPolDB tepLCPolDB = new LCPolDB();
			tepLCPolDB.setPolNo(tLJSGetDrawSchema.getPolNo());
			if(!tepLCPolDB.getInfo())
			{
				CError.buildErr(this, "险种信息查询失败！");
				return false;
			}
			tLCInsureAccTraceSchema.setCurrency(tepLCPolDB.getCurrency());
//			批改类型	EdorType
//			账户业务类型	BusyType
//			应该计价日期	ShouldValueDate
//			实际计价日期	ValueDate
			tLCInsureAccTraceSchema.setDutyCode(tLJSGetDrawSchema.getDutyCode());
			this.mLCInsureAccTraceSet_insert.add(tLCInsureAccTraceSchema);
			
			
			LCGetDB tLCGetDB = new LCGetDB();
			tLCGetDB.setPolNo(tLJSGetDrawSchema.getPolNo());
			tLCGetDB.setGetDutyCode(tLJSGetDrawSchema.getGetDutyCode());
			tLCGetDB.setDutyCode(tLJSGetDrawSchema.getDutyCode());
			if(!tLCGetDB.getInfo())
			{
				CError.buildErr(this, "查询给付信息失败！");
				return false;
			}
			tLCGetDB.setSumMoney(tLCGetDB.getSumMoney()+tLJSGetDrawSchema.getGetMoney());
			tLCGetDB.setGettoDate(tLJSGetDrawSchema.getCurGetToDate());
			tLCGetDB.setModifyDate(this.mCurrentDate);
			tLCGetDB.setModifyTime(this.mCurrentTime);
			mLCGetSet.add(tLCGetDB.getSchema());
		}
		LCInsureAccSet jx_LCInsureAccSet = new LCInsureAccSet();
		LCInsureAccClassSet jx_LCInsureAccClassSet = new LCInsureAccClassSet();
		LCInsureAccTraceSet jx_LCInsureAccTraceSet = new LCInsureAccTraceSet();
		for(int i=1;i<=this.mLCInsureAccTraceSet_insert.size();i++)
		{
			LCInsureAccTraceSchema tLCInsureAccTraceSchema = mLCInsureAccTraceSet_insert.get(i);
			//初次创建帐户，需要将轨迹合计入帐户余额
			if(this.mLCInsureAccSet_insert.size()>0&&this.mLCInsureAccClassSet_insert.size()>0)
			{
				for(int j=1 ;j<=mLCInsureAccSet_insert.size();j++)
				{
					LCInsureAccSchema tLCInsureAccSchema = mLCInsureAccSet_insert.get(i);
					if(tLCInsureAccTraceSchema.getInsuAccNo().equals(tLCInsureAccSchema.getInsuAccNo()))
					{
						tLCInsureAccSchema.setInsuAccBala(tLCInsureAccSchema.getInsuAccBala()
								+tLCInsureAccTraceSchema.getMoney());
					}
				}
				for(int j=1;j<=this.mLCInsureAccClassSet_insert.size();j++)
				{
					LCInsureAccClassSchema tLCInsureAccClassSchema = mLCInsureAccClassSet_insert.get(j);
					if(tLCInsureAccTraceSchema.getInsuAccNo().equals(tLCInsureAccClassSchema.getInsuAccNo()))
					{
						tLCInsureAccClassSchema.setInsuAccBala(tLCInsureAccClassSchema.getInsuAccBala()
								+tLCInsureAccTraceSchema.getMoney());
					}
				}
			}
			else if(this.mLCInsureAccSet_update.size()>0&&this.mLCInsureAccClassSet_update.size()>0)
			{
				//如果trace中的paydate在帐户结息日前，对其计息，然后生成利息记录，汇入到class表
//				if(tLCInsureAccTraceSchema.getPayDate().compareTo(mLCInsureAccClassSet_update.get(1).getBalaDate())<0)
//				{
//					double aGetInterest = 
//					AccountManage.calMultiRateMS(tLCInsureAccTraceSchema.getPayDate(), mLCInsureAccClassSet_update.get(1).getBalaDate(), "000000",
//							"00", "R", "C", "Y");//存款利率，复利
//					LCInsureAccTraceSchema tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
//				}
				if(tLCInsureAccTraceSchema.getPayDate().compareTo(mLCInsureAccClassSet_update.get(1).getBalaDate())<=0)
				{
					LMRiskInsuAccDB tLMRiskInsuAccDB = new LMRiskInsuAccDB();
					tLMRiskInsuAccDB.setInsuAccNo(tLCInsureAccTraceSchema.getInsuAccNo());
					if(!tLMRiskInsuAccDB.getInfo())
					{
						CError.buildErr(this, "获取保险帐户信息失败！");
						return false;
					}
					//此处计息用AccountManage.calMultiRateForAccMS 保险帐户计息
					double rate= AccountManage.calMultiRateForAccMS(tLMRiskInsuAccDB.getSchema(), 
							tLCInsureAccTraceSchema.getPayDate(), mLCInsureAccClassSet_update.get(1).getBalaDate(),mLCInsureAccClassSet_update.get(1).getCurrency());
					if(rate==-1)
					{
						CError.buildErr(this, "计算利率失败！");
						return false;
					}
					double aGetInterest = tLCInsureAccTraceSchema.getMoney()*rate;
					
//						AccountManage.calMultiRateMS(tLCInsureAccTraceSchema.getPayDate(), mLCInsureAccClassSet_update.get(1).getBalaDate(), "000000",
//								"00", "R", "C", "Y");//存款利率，复利
					LCInsureAccTraceSchema LX_LCInsureAccTraceSchema = new LCInsureAccTraceSchema();
					PubFun.copySchema(LX_LCInsureAccTraceSchema, tLCInsureAccTraceSchema);
					LX_LCInsureAccTraceSchema.setPayDate(mLCInsureAccClassSet_update.get(1).getBalaDate());
					LX_LCInsureAccTraceSchema.setMoney(aGetInterest);
					String finType = BqCalBL.getFinType_HL_SC("SC", LX_LCInsureAccTraceSchema.getInsuAccNo(), "LX");
					if(finType.equals(""))
					{
						CError.buildErr(this, "生存领取利息的财务类型获取失败");
						return false;
					}
//					LX_LCInsureAccTraceSchema.setMoneyType(LX_LCInsureAccTraceSchema.getMoneyType()+"LX");
					LX_LCInsureAccTraceSchema.setMoneyType(finType);
					LX_LCInsureAccTraceSchema.setMakeTime("00:00:00");
					
					LX_LCInsureAccTraceSchema.setSerialNo(PubFun1.CreateMaxNo("SERIALNO", PubFun.getNoLimit(LX_LCInsureAccTraceSchema.getManageCom())));
					jx_LCInsureAccTraceSet.add(LX_LCInsureAccTraceSchema);
					for(int j=1 ;j<=mLCInsureAccSet_update.size();j++)
					{
						LCInsureAccSchema tLCInsureAccSchema = mLCInsureAccSet_update.get(i);
						if(tLCInsureAccTraceSchema.getInsuAccNo().equals(tLCInsureAccSchema.getInsuAccNo()))
						{
							tLCInsureAccSchema.setInsuAccBala(tLCInsureAccSchema.getInsuAccBala()
									+tLCInsureAccTraceSchema.getMoney()+aGetInterest);
						}
					}
					for(int j=1;j<=this.mLCInsureAccClassSet_update.size();j++)
					{
						LCInsureAccClassSchema tLCInsureAccClassSchema = mLCInsureAccClassSet_update.get(j);
						if(tLCInsureAccTraceSchema.getInsuAccNo().equals(tLCInsureAccClassSchema.getInsuAccNo()))
						{
							tLCInsureAccClassSchema.setInsuAccBala(tLCInsureAccClassSchema.getInsuAccBala()
									+tLCInsureAccTraceSchema.getMoney()+aGetInterest);
						}
					}
					
				}
				else if(tLCInsureAccTraceSchema.getPayDate().compareTo(mLCInsureAccClassSet_update.get(1).getBalaDate())>0)
				{
					if(i==1)
					{
						TransferData tTransferData = new TransferData();
				    	tTransferData.setNameAndValue("InsuAccNo",mLCInsureAccSet_update.get(1).getInsuAccNo());
				    	tTransferData.setNameAndValue("PolNo", mLCInsureAccSet_update.get(1).getPolNo());
				    	//注意此处修改结算日期，当结算日期大于等于满期日期，都不再计息
				    	tTransferData.setNameAndValue("BalaDate", tLCInsureAccTraceSchema.getPayDate());
						VData tVData = new VData();
						tVData.add(mGlobalInput);
						tVData.add(tTransferData);
						//非万能险的账户型结算
						InsuAccBala tInsuAccBala = new InsuAccBala();
						if (!tInsuAccBala.submitData(tVData, "NonUniversal")) {
							CError.buildErr(this, "账户结息失败！");
							return false;
						}
						VData tResult = new VData();
						tResult = tInsuAccBala.getResult();
						jx_LCInsureAccSet = (LCInsureAccSet) tResult.getObjectByObjectName("LCInsureAccSet", 0);   	
						jx_LCInsureAccClassSet = (LCInsureAccClassSet)tResult.getObjectByObjectName("LCInsureAccClassSet", 0);
						jx_LCInsureAccTraceSet = (LCInsureAccTraceSet)tResult.getObjectByObjectName("LCInsureAccTraceSet", 0);
						for (int k=1;k<=jx_LCInsureAccTraceSet.size();k++)
						{
							jx_LCInsureAccTraceSet.get(k).setMakeTime("00:00:00");
							String finType = BqCalBL.getFinType_HL_SC("SC", jx_LCInsureAccTraceSet.get(k).getInsuAccNo(), "LX");
							if(finType.equals(""))
							{
								CError.buildErr(this, "生存领取利息的财务类型获取失败");
								return false;
							}
//							jx_LCInsureAccTraceSet.get(k).setMoneyType(tLCInsureAccTraceSchema.getMoneyType()+"LX");
							jx_LCInsureAccTraceSet.get(k).setMoneyType(finType);
						}
						for (int k=1;k<=jx_LCInsureAccClassSet.size();k++)
						{
							jx_LCInsureAccClassSet.get(k).setBalaTime("00:00:00");
						}
						for (int k=1;k<=jx_LCInsureAccSet.size();k++)
						{
							jx_LCInsureAccSet.get(k).setBalaTime("00:00:00");
						}
					}
					for(int j=1;j<=jx_LCInsureAccSet.size();j++)
					{
						LCInsureAccSchema tLCInsureAccSchema =jx_LCInsureAccSet.get(j);
						if(tLCInsureAccTraceSchema.getInsuAccNo().equals(tLCInsureAccSchema.getInsuAccNo()))
						{
							tLCInsureAccSchema.setInsuAccBala(tLCInsureAccSchema.getInsuAccBala()+tLCInsureAccTraceSchema.getMoney());
						}							
					}
					for(int j=1;j<=jx_LCInsureAccClassSet.size();j++)
					{
						LCInsureAccClassSchema tLCInsureAccClassSchema = jx_LCInsureAccClassSet.get(j);
						if(tLCInsureAccTraceSchema.getInsuAccNo().equals(tLCInsureAccClassSchema.getInsuAccNo()))
						{
							tLCInsureAccClassSchema.setInsuAccBala(tLCInsureAccClassSchema.getInsuAccBala()
									+tLCInsureAccTraceSchema.getMoney());
						}
					}
				}
			}
			else
			{
				CError.buildErr(this, "帐户信息获取失败");
				return false;
			}
		}
		
		if(jx_LCInsureAccSet.size()>0&&jx_LCInsureAccClassSet.size()>0)
		{
			this.mLCInsureAccSet_update.clear();
			this.mLCInsureAccSet_update.add(jx_LCInsureAccSet);
			this.mLCInsureAccClassSet_update.clear();
			this.mLCInsureAccClassSet_update.add(jx_LCInsureAccClassSet);
		}
		
		this.mLCInsureAccTraceSet_insert.add(jx_LCInsureAccTraceSet);
		
		this.mLJSGetSet.add(tLJSGetSet.get(1).getSchema());
		this.mLJSGetDrawSet.add(tLJSGetDrawSet);
		
		return true;
	}
	
	private boolean checkData()
	{
		//保单交费期内存在生存领取，应领始点应小于等于保费交至日期!
		LJSGetDrawDB tLJSGetDrawDB = new LJSGetDrawDB();
		tLJSGetDrawDB.setGetNoticeNo(this.mGetNoticeNO);
		LJSGetDrawSet tLJSGetDrawSet = tLJSGetDrawDB.query();
		if(tLJSGetDrawSet.size()<=0)
		{
			CError.buildErr(this, "获取生存金明细数据失败！");
			return false;
		}
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(tLJSGetDrawSet.get(1).getPolNo());
		if(!tLCPolDB.getInfo())
		{
			CError.buildErr(this, "查询险种信息失败！");
			return false;
		}
		FDate tFDate = new FDate();
		if(tFDate.getDate(tLCPolDB.getPayEndDate()).after(tFDate.getDate(tLCPolDB.getPaytoDate())))
		{
			if(tFDate.getDate(tLJSGetDrawSet.get(1).getLastGettoDate()).after(
					tFDate.getDate(tLCPolDB.getPaytoDate())))
			{
				CError.buildErr(this, "险种缴至日期小于应领日期、生存金不能入帐户！");
			    //日志监控,过程监控        
		    	PubFun.logTrack(mGlobalInput,mGetNoticeNO,"险种缴至日期小于应领日期、生存金不能入帐户");
				return false;
			}
		}

		return true;
	}
	
	private boolean getInputData(VData tInputData)
	{
		try
		{
			TransferData tTransferData = (TransferData)tInputData.getObjectByObjectName("TransferData", 0);
			mGlobalInput =  (GlobalInput)tInputData.getObjectByObjectName("GlobalInput", 0);
			this.mGetNoticeNO = (String)tTransferData.getValueByName("GetNoticeNo");
		}catch(Exception ex)
		{
			logger.debug("获取传入数据出错！");
			CError.buildErr(this, "获取传入数据出错！");
			return false;
		}
		
		return true;
	}
}
