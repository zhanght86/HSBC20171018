package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.sinosoft.ibrms.bom.BOMAgent;
import com.sinosoft.ibrms.bom.BOMAppnt;
import com.sinosoft.ibrms.bom.BOMCont;
import com.sinosoft.ibrms.bom.BOMElement;
import com.sinosoft.ibrms.bom.BOMInsured;
import com.sinosoft.ibrms.bom.BOMPol;
import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LMDiscountDB;
import com.sinosoft.lis.db.LPAppntDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPInsuredDB;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LJSPayPersonSchema;
import com.sinosoft.lis.schema.LMDiscountSchema;
import com.sinosoft.lis.schema.LPAppntSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPDiscountSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LPDiscountSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class PEdorDiscountCalBL {
private static Logger logger = Logger.getLogger(PEdorDiscountCalBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mVData = new VData();
	private String mPayCount="";
	private String mPayIntv="";//变更前的交费间隔
	private String mOperator="";
	private String mPayAimClass="";
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private TransferData mTransferData = new TransferData();

	// 统一更新日期，时间
	private String theCurrentDate = PubFun.getCurrentDate();
	private String theCurrentTime = PubFun.getCurrentTime();
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private String theDate = "";
	
	public PEdorDiscountCalBL() {
	}

	/* 根据保费项计算折扣 */
	public boolean calculate(VData tVData)
	{		
		LPDiscountSet tLPDiscountSet = new LPDiscountSet();
		LPPremSet tLPPremSet = new LPPremSet();
		LPPolSchema tLPPolSchema = new LPPolSchema();
		
		logger.debug(tVData.size());
		tLPDiscountSet = (LPDiscountSet)tVData.getObjectByObjectName("LPDiscountSet", 0);
		if (tLPDiscountSet == null) {
			// @@错误处理
			CError.buildErr(this, "在接受数据时没有得到LPDiscountSet的数据!");
			return false;
		}
		tLPPremSet = (LPPremSet)tVData.getObjectByObjectName("LPPremSet", 0);
		if (tLPPremSet == null) {
			// @@错误处理
			CError.buildErr(this, "在接受数据时没有得到LPPremSet的数据!");
			return false;
		}
		tLPPolSchema = (LPPolSchema)tVData.getObjectByObjectName("LPPolSchema", 0);
		if (tLPPolSchema == null) {
			// @@错误处理
			CError.buildErr(this, "在接受数据时没有得到LPPolSchema的数据!");
			return false;
		}
		mLPEdorItemSchema = (LPEdorItemSchema)tVData.getObjectByObjectName("LPEdorItemSchema", 0);
		if (mLPEdorItemSchema == null) {
			// @@错误处理
			CError.buildErr(this, "在接受数据时没有得到LPEdorItemSchema的数据!");
			return false;
		}
		mTransferData = (TransferData)tVData.getObjectByObjectName("TransferData", 0);
		if (mTransferData == null) {
			// @@错误处理
			CError.buildErr(this, "在接受数据时没有得到TransferData的数据!");
			return false;
		}
		mPayCount = (String)mTransferData.getValueByName("PayCount");
		mPayIntv = (String)mTransferData.getValueByName("PayIntv");
		mOperator = (String)mTransferData.getValueByName("Operator");
		mPayAimClass = (String)mTransferData.getValueByName("PayAimClass");
		if(mPayAimClass==null)
			mPayAimClass="1";
		//tongmeng 2011-04-26 
		//没有折扣.直接返回
		if(tLPDiscountSet.size()<=0)
		{
			return true;
		}
		LJSPayPersonSet rLJSPayPersonSet = new LJSPayPersonSet();
		LPDiscountSet rLPDiscountSet = new LPDiscountSet();
		LPDiscountSchema rLPDiscountSchema = new LPDiscountSchema();
		LPPremSchema tLPPremSchema = new LPPremSchema();
		String sql ="select corder,discountcode from (";//用于排列折扣顺序
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		for(int i=1;i<=tLPDiscountSet.size();i++)
		{
			sql = sql+"select '"+tLPDiscountSet.get(i).getCOrder()+"' corder,'"+tLPDiscountSet.get(i).getDiscountCode()+"' discountcode "
				+ "from dual";

			
			if(i!=tLPDiscountSet.size())
				sql = sql+" union all ";

		}
		sql = sql+" ) g order by corder";
		sqlbv.sql(sql);
		logger.debug("排序sql: "+sql);

		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(sqlbv);
		double tKZK= 0.00;//用于辅助折扣计算
		double tKPrem=0.00;//用于记录原始保费
		//按责任处理折扣
		for(int j=1;j<=tLPPremSet.size();j++)		
		{				
			tLPPremSchema = new LPPremSchema();
			tLPPremSchema.setSchema(tLPPremSet.get(j));
			tKZK= 0.00;//ini
			tKPrem = tLPPremSchema.getPrem();
			//按责任处理折扣
			for(int i=1;i<=tSSRS.getMaxRow();i++)
			{
				String corder=tSSRS.GetText(i, 1);
				String discountcode=tSSRS.GetText(i, 2);
				//根据折扣编码计算折扣
				LMDiscountSchema tLMDiscountSchema = new LMDiscountSchema();
				LMDiscountDB tLMDiscountDB = new LMDiscountDB();
				tLMDiscountDB.setDiscountCode(discountcode);
				if(!tLMDiscountDB.getInfo())
				{
					CError.buildErr(this, "查询折扣描述失败！");
					return false;
				}
				tLMDiscountSchema = tLMDiscountDB.getSchema();
				String calcode = tLMDiscountSchema.getCalCode();//算法	
				String dutycode = tLMDiscountSchema.getDutyCode();
				String paytype = tLMDiscountSchema.getDiscountType();//折扣类型
				
				if((dutycode.equals("000000")||dutycode.equals(tLPPremSchema.getDutyCode()))
						&&((tLPPremSchema.getPayPlanCode().substring(0, 6)
								.equals("000000")&&tLMDiscountSchema.getAddFeeDiscFlag().equals("Y"))//存在加费，并且加费需要折扣
								||!tLPPremSchema.getPayPlanCode().substring(0, 6)
								.equals("000000")))
				{
					//生成折扣记录
					rLPDiscountSchema = new LPDiscountSchema();
					rLPDiscountSchema.setEdorNo(tLPPolSchema.getEdorNo());
					rLPDiscountSchema.setEdorType(tLPPolSchema.getEdorType());
					rLPDiscountSchema.setContNo(tLPPremSchema.getContNo());
					rLPDiscountSchema.setPolNo(tLPPremSchema.getPolNo());
					rLPDiscountSchema.setDutyCode(tLPPremSchema.getDutyCode());					
					rLPDiscountSchema.setDiscountCode(discountcode);					
					rLPDiscountSchema.setCOrder(corder);
					rLPDiscountSchema.setOperator(mOperator);
					rLPDiscountSchema.setMakeDate(theCurrentDate);
					rLPDiscountSchema.setMakeTime(theCurrentTime);
					rLPDiscountSchema.setModifyDate(theCurrentDate);
					rLPDiscountSchema.setModifyTime(theCurrentTime);
					rLPDiscountSet.add(rLPDiscountSchema);
					
					//计算折扣
					VData cVData=new VData();
					TransferData tTransferData = new TransferData();
					tTransferData.setNameAndValue("CalCode", calcode);
					cVData.add(tLPPolSchema);
					cVData.add(tTransferData);
					cVData.add(tLPPremSchema);
					
					String tDiscount = cal(cVData);//折扣后的保费
					logger.debug("计算的折扣:"+tDiscount);
					if(tDiscount==null)
					{
						CError.buildErr(this, "折扣计算失败！");
						return false;
					}
					
					if(tDiscount.equals("")||tDiscount.equals("0"))
					{
						continue;//如果没有折扣则继续计算
					}					
					
					//生成应收子表
					double tZK = PubFun.round(tKPrem-Double.parseDouble(tDiscount)-tKZK,2);
					rLJSPayPersonSet.add(PreperaLjspayperson(tLPPolSchema, tLPPremSchema, tZK, paytype));
					
					//为下一条规则计算做准备
					tKZK = tKZK+tZK;//防止汇总ljspay时重复减折扣，累计下次折扣之前的所有折扣，在下次计算时减去
					tLPPremSchema.setPrem(tDiscount);//下一条规则在此规则结果上进行
				}
			}		
			
		}
		
		if(rLPDiscountSet!=null && rLPDiscountSet.size()>0)
			mVData.add(rLPDiscountSet);
		if(rLJSPayPersonSet!=null && rLJSPayPersonSet.size()>0)
			mVData.add(rLJSPayPersonSet);
		
		return true;
	}
	
	private LJSPayPersonSchema PreperaLjspayperson(LPPolSchema tLPPolSchema,LPPremSchema tLPPremSchema,double tZK,String tPayType) 
	{		
		LJSPayPersonSchema tLJSPayPersonSchema = new LJSPayPersonSchema();
		tLJSPayPersonSchema.setPolNo(tLPPolSchema.getPolNo());
		tLJSPayPersonSchema.setGrpContNo(tLPPolSchema.getGrpContNo());
		tLJSPayPersonSchema.setGrpPolNo(tLPPolSchema.getGrpPolNo());
		tLJSPayPersonSchema.setGrpContNo(tLPPolSchema.getGrpContNo());
		tLJSPayPersonSchema.setGrpPolNo(tLPPolSchema.getGrpPolNo());
		tLJSPayPersonSchema.setContNo(tLPPolSchema.getContNo());
		tLJSPayPersonSchema.setManageCom(tLPPolSchema.getManageCom());
		tLJSPayPersonSchema.setRiskCode(tLPPolSchema.getRiskCode());
		tLJSPayPersonSchema.setAppntNo(tLPPolSchema.getAppntNo());
		tLJSPayPersonSchema.setPayAimClass(mPayAimClass);
		tLJSPayPersonSchema.setDutyCode(tLPPremSchema.getDutyCode());
		tLJSPayPersonSchema.setPayPlanCode(tLPPremSchema.getPayPlanCode());
		//此处的paytintv应该置多少 是否应该lcpol.payintv还是12还是0
		tLJSPayPersonSchema.setPayIntv(mPayIntv);
		tLJSPayPersonSchema.setPayDate(mLPEdorItemSchema.getEdorValiDate());
		tLJSPayPersonSchema.setPayType(tPayType);
		tLJSPayPersonSchema.setCurrency(tLPPremSchema.getCurrency());

		tLJSPayPersonSchema.setLastPayToDate(mLPEdorItemSchema.getEdorValiDate());
		tLJSPayPersonSchema.setCurPayToDate(tLPPolSchema.getPaytoDate());

		tLJSPayPersonSchema.setAgentCode(tLPPolSchema.getAgentCode());
		tLJSPayPersonSchema.setAgentGroup(tLPPolSchema.getAgentGroup());
		tLJSPayPersonSchema.setGetNoticeNo(mLPEdorItemSchema.getEdorNo());
		
		//保费项的保存 SumActuPayMoney = SumDuePayMoney
		tLJSPayPersonSchema.setSumActuPayMoney((-1)*tZK);
		tLJSPayPersonSchema.setSumDuePayMoney((-1)*tZK);
		
		tLJSPayPersonSchema.setPayCount(mPayCount);
		tLJSPayPersonSchema.setBankAccNo("");
		tLJSPayPersonSchema.setBankCode("");
		tLJSPayPersonSchema.setOperator(mOperator);
		tLJSPayPersonSchema.setMakeDate(theCurrentDate);
		tLJSPayPersonSchema.setMakeTime(theCurrentTime);
		tLJSPayPersonSchema.setModifyDate(theCurrentDate);
		tLJSPayPersonSchema.setModifyTime(theCurrentTime);		

		return tLJSPayPersonSchema;
	}	
	
	/* 根据保费项计算折扣 */
	private String cal(VData tVData)
	{		
		TransferData tTransferData = new TransferData();
		LPPolSchema tLPPolSchema = new LPPolSchema();
		LPPremSchema tLPPremSchema = new LPPremSchema();
		tTransferData = (TransferData)tVData.getObjectByObjectName("TransferData", 0);
		if (tTransferData == null) {
			// @@错误处理
			CError.buildErr(this, "在接受数据时没有得到TransferData的数据!");
			return null;
		}
		tLPPolSchema = (LPPolSchema)tVData.getObjectByObjectName("LPPolSchema", 0);
		if (tLPPolSchema == null) {
			// @@错误处理
			CError.buildErr(this, "在接受数据时没有得到LPPolSchema的数据!");
			return null;
		}
		tLPPremSchema = (LPPremSchema)tVData.getObjectByObjectName("LPPremSchema", 0);
		if (tLPPremSchema == null) {
			// @@错误处理
			CError.buildErr(this, "在接受数据时没有得到LPPremSchema的数据!");
			return null;
		}

		String calcode = (String) tTransferData.getValueByName("CalCode");
		
		String edorno = tLPPolSchema.getEdorNo();
		String edornotype = tLPPolSchema.getEdorType();
		String contno = tLPPolSchema.getContNo();
		String insuredno = tLPPolSchema.getInsuredNo();
		String agentcode = tLPPolSchema.getAgentCode();
		tLPPolSchema.setPrem(tLPPremSchema.getPrem());
		
		//准备计算信息
		LPContDB tLPContDB = new LPContDB();
		tLPContDB.setEdorNo(edorno);
		tLPContDB.setEdorType(edornotype);
		tLPContDB.setContNo(contno);
		if(!tLPContDB.getInfo()){
			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setContNo(contno);
			if(!tLCContDB.getInfo()){				
				CError.buildErr(this, "查询合同信息失败！");
				return null;
			}
		}
		
		LPAppntDB tLPAppntDB = new LPAppntDB();
		tLPAppntDB.setEdorNo(edorno);
		tLPAppntDB.setEdorType(edornotype);
		tLPAppntDB.setContNo(contno);
		if(!tLPAppntDB.getInfo()){
			LCAppntDB tLCAppntDB = new LCAppntDB();
			tLCAppntDB.setContNo(contno);
			if(!tLCAppntDB.getInfo()){
				CError.buildErr(this, "查询投保人信息失败！");
				return null;
			}
		}
		
		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(agentcode);
		if(!tLAAgentDB.getInfo()){
			CError.buildErr(this, "查询代理人信息失败！");
			return null;
		}
		
		LPInsuredDB tLPInsuredDB = new LPInsuredDB();
		tLPInsuredDB.setEdorNo(edorno);
		tLPInsuredDB.setEdorType(edornotype);
		tLPInsuredDB.setContNo(contno);
		tLPInsuredDB.setInsuredNo(insuredno);
		if(!tLPInsuredDB.getInfo()){
			LCInsuredDB tLCInsuredDB = new LCInsuredDB();
			tLCInsuredDB.setContNo(contno);
			tLCInsuredDB.setInsuredNo(insuredno);
			if(!tLCInsuredDB.getInfo()){
				CError.buildErr(this, "查询被保人信息失败！");
				return null;
			}
		}
		//准备BOMCont数据
		BOMCont cont = new BOMCont();
		cont = DealBOMCont(tLPContDB.getSchema());
		
		//准备被保人BOMAppnt数据
		BOMAppnt appnt = new BOMAppnt();		
		appnt = DealBOMAppnt(tLPAppntDB.getSchema());
		
		//准备代理人BOMAgent数据
		BOMAgent agent = new BOMAgent();
		agent = DealBOMAgent(tLAAgentDB.getSchema());
		
		//准备被保人BOMInsured数据
		BOMInsured insured = new BOMInsured();
		insured = DealBOMInsured(tLPInsuredDB.getSchema());
		
		//准备险种BOMPol数据
		BOMPol pol = new BOMPol();//一个险种
		pol = DealBOMPol(tLPPolSchema);
		
		//准备受益人BOMBnf数据
		
		BOMElement element = new BOMElement();
		List list = new ArrayList();
		list.add(cont);
		list.add(appnt);
		list.add(agent);
		list.add(insured);
		list.add(pol);
		
		list.add(element);
		
		//计算
		Calculator tCalculator = new Calculator();
		tCalculator.setBOMList(list);
		tCalculator.addBasicFactor("Prem", String.valueOf(tLPPremSchema.getPrem()));
		tCalculator.setCalCode(calcode);
		logger.debug("算法： "+calcode);
		String rResult = tCalculator.calculate();
		logger.debug("$$$$:"+rResult);
		
		return rResult;
	}
	
	private BOMCont DealBOMCont(LPContSchema tLPContSchema){
		BOMCont cont = new BOMCont();
		try{			
			cont.setSellType(tLPContSchema.getSellType());//出单方式
			//tongmeng 2009-02-19
			//问题件信息先设置默认值
			cont.setComIssueFlag("0");//机构问题件标记
			cont.setCustomerIssueFlag("0");//客户问题件标记
			cont.setAppntIssueFlag("0");//投保人问题件标记
			cont.setAgentIssueFlag("0");//业务员问题件标记			
			cont.setAutoPayFlag(tLPContSchema.getAutoPayFlag());
			cont.setBankAccNo(tLPContSchema.getBankAccNo());
			cont.setBankCode(tLPContSchema.getBankCode());
			cont.setCardFlag(tLPContSchema.getCardFlag());
			cont.setContNo(tLPContSchema.getContNo());			
			cont.setManageCom(tLPContSchema.getManageCom());			
			cont.setOutPayFlag(tLPContSchema.getOutPayFlag());			
		}catch(Exception e){
			CError.buildErr(this, "准备BOMCont时出错！");
			e.printStackTrace();
		}
		return cont;
	}
	
	private BOMAppnt DealBOMAppnt(LPAppntSchema tLPAppntSchema){
		BOMAppnt appnt = new BOMAppnt();
		ExeSQL tempExeSQL = new ExeSQL();
		try{			
			appnt.setAppntName(tLPAppntSchema.getAppntName());
			appnt.setAppntNo(tLPAppntSchema.getAppntNo());
			appnt.setAppntSex(tLPAppntSchema.getAppntSex());		
			
			appnt.setNationality(tLPAppntSchema.getNationality());
			appnt.setNativePlace(tLPAppntSchema.getNativePlace());
			appnt.setOccupationCode(tLPAppntSchema.getOccupationCode());
			appnt.setOccupationType(tLPAppntSchema.getOccupationType());
			appnt.setPosition(tLPAppntSchema.getPosition());
			appnt.setRelationToInsured(tLPAppntSchema.getRelationToInsured());
			appnt.setRgtAddress(tLPAppntSchema.getRgtAddress());
			appnt.setSalary(new Double(tLPAppntSchema.getSalary()));
			
		}catch(Exception e){
			CError.buildErr(this, "准备BOMAppnt时出错");
		}
		return appnt;
	}
	
	private BOMAgent DealBOMAgent(LAAgentSchema tLAAgentSchema){
		BOMAgent agent = new BOMAgent();
		String tBlackList="select blacklisflag from latree where agentcode='?agentcode?'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tBlackList);
		sqlbv.put("agentcode", tLAAgentSchema.getAgentCode());
		ExeSQL tempExeSQL = new ExeSQL();
		String tBlackFlag = tempExeSQL.getOneValue(sqlbv);
		agent.setAgentBlankFlag(tBlackFlag);//黑名单标记
		agent.setAgentCode(tLAAgentSchema.getAgentCode());
		agent.setAgentKind(tLAAgentSchema.getAgentKind());
		agent.setAgentState(tLAAgentSchema.getAgentState());
		agent.setBranchType(tLAAgentSchema.getBranchType());
		agent.setInsideFlag(tLAAgentSchema.getInsideFlag());
		agent.setManageCom(tLAAgentSchema.getManageCom());
		agent.setQuafNo(tLAAgentSchema.getQuafNo());
		agent.setSaleQuaf(tLAAgentSchema.getSaleQuaf());		
		return agent;
	}
	
	private BOMInsured DealBOMInsured(LPInsuredSchema tLPInsuredSchema){
		BOMInsured Insured = new BOMInsured();
		ExeSQL tempExeSQL = new ExeSQL();
		try{
			
		}catch(Exception e){
			CError.buildErr(this, "准备BOMAppnt时出错");
		}
		return Insured;
	}
	
	private BOMPol DealBOMPol(LPPolSchema tLPPolSchema){
		BOMPol Pol = new BOMPol();
		ExeSQL tempExeSQL = new ExeSQL();
		try{			
			Pol.setAmnt(Double.valueOf(String.valueOf(tLPPolSchema.getAmnt())));
			Pol.setUWFlag(tLPPolSchema.getUWFlag());
			Pol.setPrem(new Double(tLPPolSchema.getPrem()));
			Pol.setInsuredNo(tLPPolSchema.getInsuredNo());
			Pol.setMainPolNo(tLPPolSchema.getMainPolNo());
			Pol.setMult(new Double(tLPPolSchema.getMult()));
			
			Pol.setPayYears(Double.valueOf(String.valueOf(tLPPolSchema.getPayYears())));
			//Pol.setPayYears(Double.valueOf(String.valueOf(tLPPolSchema.getInsuYear())));
			Pol.setPolNo(tLPPolSchema.getPolNo());
			Pol.setInsuYear(Double.valueOf(String.valueOf(tLPPolSchema.getInsuYear())));
			Pol.setInsuYearFlag(tLPPolSchema.getInsuYearFlag());
			Pol.setCurrency(tLPPolSchema.getCurrency());
			Pol.setLiveGetMode(tLPPolSchema.getLiveGetMode());
			Pol.setBonusGetMode(tLPPolSchema.getBonusGetMode());
			Pol.setRiskCode(tLPPolSchema.getRiskCode());
			if(!(tLPPolSchema.getCValiDate()==null||"".equals(tLPPolSchema.getCValiDate()))){
				theDate=tLPPolSchema.getCValiDate()+" 00:00:00";
				Pol.setCValiDate(sdf.parse(theDate));
			}
			Pol.setFloatRate(new Double(tLPPolSchema.getFloatRate()));
			
		}catch(Exception e){
			CError.buildErr(this, "准备BOMAppnt时出错");
		}
		return Pol;
	}
	
	public VData getResult() {
		return mVData;
	}
	
	public static void main(String[] args) {
		PEdorDiscountCalBL tPEdorDiscountCalBL = new PEdorDiscountCalBL();
		VData tVData = new VData();
		TransferData tTransferData=new TransferData();
		tTransferData.setNameAndValue("PayCount", "1");
		tTransferData.setNameAndValue("PayIntv", "12");
		//tTransferData.setNameAndValue("PayAimClass", "4");
		tTransferData.setNameAndValue("Operator", "001");
		tVData.add(tTransferData);
		tPEdorDiscountCalBL.calculate(tVData);
	}
}
