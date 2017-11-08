package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
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
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDOccupationDB;
import com.sinosoft.lis.db.LMDiscountDB;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.LDExch;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCDiscountSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LJSPaySchema;
import com.sinosoft.lis.schema.LJSPayPersonSchema;
import com.sinosoft.lis.schema.LMDiscountSchema;
import com.sinosoft.lis.vbl.LCPremBLSet;
import com.sinosoft.lis.vschema.LCDiscountSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class DiscountCalBL {
private static Logger logger = Logger.getLogger(DiscountCalBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mVData = new VData();
	private String mGetNoticeNo="";

	// 统一更新日期，时间
	private String theCurrentDate = PubFun.getCurrentDate();
	private String theCurrentTime = PubFun.getCurrentTime();
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private String theDate = "";
	
	public DiscountCalBL() {
	}

	/* 根据保费项计算折扣 */
	public boolean calculate(VData tVData)
	{		
		LCDiscountSet tLCDiscountSet = new LCDiscountSet();
		LCPremBLSet tLCPremBLSet = new LCPremBLSet();
		LCPolSchema tLCPolSchema = new LCPolSchema();
		
		logger.debug(tVData.size());
		tLCDiscountSet = (LCDiscountSet)tVData.getObjectByObjectName("LCDiscountSet", 0);
		if (tLCDiscountSet == null) {
			// @@错误处理
			CError.buildErr(this, "在接受数据时没有得到LCDiscountSet的数据!");
			return false;
		}
		tLCPremBLSet = (LCPremBLSet)tVData.getObjectByObjectName("LCPremBLSet", 0);
		if (tLCPremBLSet == null) {
			// @@错误处理
			CError.buildErr(this, "在接受数据时没有得到LCPremSet的数据!");
			return false;
		}
		tLCPolSchema = (LCPolSchema)tVData.getObjectByObjectName("LCPolSchema", 0);
		if (tLCPolSchema == null) {
			// @@错误处理
			CError.buildErr(this, "在接受数据时没有得到LCPolSchema的数据!");
			return false;
		}
		mGetNoticeNo = (String)tVData.getObjectByObjectName("String", 0);
		if (mGetNoticeNo == null) {
			// @@错误处理
			CError.buildErr(this, "在接受数据时没有得到LCPolSchema的数据!");
			return false;
		}
		
		LJSPayPersonSet rLJSPayPersonSet = new LJSPayPersonSet();
		LCDiscountSet rLCDiscountSet = new LCDiscountSet();
		LCDiscountSchema rLCDiscountSchema = new LCDiscountSchema();
		LCPremSchema tLCPremSchema = new LCPremSchema();
		String sql ="select a.corder,a.discountcode from (";//用于排列折扣顺序
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		for(int i=1;i<=tLCDiscountSet.size();i++)
		{
			sql = sql+"select '"+"?corder"+i+"?"+"' corder,'"+"?discountcode"+i+"?"+"' discountcode "
				+ "from dual";
			if(i!=tLCDiscountSet.size())
				sql = sql+" union all ";
			sqlbv.put("corder"+i,tLCDiscountSet.get(i).getCOrder());
			sqlbv.put("discountcode"+i,tLCDiscountSet.get(i).getDiscountCode());
		}
		sql = sql+" ) a order by corder";
		logger.debug("排序sql: "+sql);
		
		sqlbv.sql(sql);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(sqlbv);
		double tKZK= 0.00;//用于辅助折扣计算
		double tKPrem=0.00;//用于记录原始保费
		//按责任处理折扣
		for(int j=1;j<=tLCPremBLSet.size();j++)		
		{				
			tLCPremSchema = new LCPremSchema();
			tLCPremSchema.setSchema(tLCPremBLSet.get(j));
			tKZK= 0.00;//ini
			tKPrem = tLCPremSchema.getPrem();
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
				
				if((dutycode.equals("000000")||dutycode.equals(tLCPremSchema.getDutyCode()))
						&&((tLCPremSchema.getPayPlanCode().substring(0, 6)
								.equals("000000")&&tLMDiscountSchema.getAddFeeDiscFlag().equals("Y"))//存在加费，并且加费需要折扣
								||!tLCPremSchema.getPayPlanCode().substring(0, 6)
								.equals("000000")))
				{
					//生成折扣记录
					rLCDiscountSchema = new LCDiscountSchema();
					rLCDiscountSchema.setContNo(tLCPremSchema.getContNo());
					rLCDiscountSchema.setPolNo(tLCPremSchema.getPolNo());
					rLCDiscountSchema.setDutyCode(tLCPremSchema.getDutyCode());					
					rLCDiscountSchema.setDiscountCode(discountcode);					
					rLCDiscountSchema.setCOrder(corder);
					rLCDiscountSchema.setOperator(tLCPremSchema.getOperator());
					rLCDiscountSchema.setMakeDate(theCurrentDate);
					rLCDiscountSchema.setMakeTime(theCurrentTime);
					rLCDiscountSchema.setModifyDate(theCurrentDate);
					rLCDiscountSchema.setModifyTime(theCurrentTime);
					rLCDiscountSet.add(rLCDiscountSchema);
					
					//计算折扣
					VData cVData=new VData();
					TransferData tTransferData = new TransferData();
					tTransferData.setNameAndValue("CalCode", calcode);
					cVData.add(tLCPolSchema);
					cVData.add(tTransferData);
					cVData.add(tLCPremSchema);
					
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
					rLJSPayPersonSet.add(PreperaLjspayperson(tLCPolSchema, tLCPremSchema, tZK, paytype));
					
					//为下一条规则计算做准备
					tKZK = tKZK+tZK;//防止汇总ljspay时重复减折扣，累计下次折扣之前的所有折扣，在下次计算时减去
					tLCPremSchema.setPrem(tDiscount);//下一条规则在此规则结果上进行
				}
			}		
			
		}
		
		if(rLCDiscountSet!=null && rLCDiscountSet.size()>0)
			mVData.add(rLCDiscountSet);
		if(rLJSPayPersonSet!=null && rLJSPayPersonSet.size()>0)
			mVData.add(rLJSPayPersonSet);
		
		return true;
	}
	
	private LJSPayPersonSchema PreperaLjspayperson(LCPolSchema tLCPolSchema,LCPremSchema tLCPremSchema,double tZK,String tPayType) 
	{		
		LJSPayPersonSchema nLJSPayPersonSchema = new LJSPayPersonSchema();
		nLJSPayPersonSchema.setGrpContNo(tLCPremSchema.getGrpContNo());
		nLJSPayPersonSchema.setMainPolYear(1); // 新契约保单年度为“1”
		nLJSPayPersonSchema.setContNo(tLCPremSchema.getContNo());
		nLJSPayPersonSchema.setPolNo(tLCPremSchema.getPolNo());
		nLJSPayPersonSchema.setAppntNo(tLCPremSchema.getAppntNo());
		nLJSPayPersonSchema.setPayIntv(tLCPolSchema.getPayIntv());
		nLJSPayPersonSchema.setGetNoticeNo(mGetNoticeNo);
		nLJSPayPersonSchema.setPayCount("1");
		nLJSPayPersonSchema.setDutyCode(tLCPremSchema.getDutyCode());
		nLJSPayPersonSchema.setPayPlanCode(tLCPremSchema.getPayPlanCode());
		nLJSPayPersonSchema.setRiskCode(tLCPolSchema.getRiskCode());
		nLJSPayPersonSchema.setManageCom(tLCPolSchema.getManageCom());
		nLJSPayPersonSchema.setAgentCode(tLCPolSchema.getAgentCode());
		nLJSPayPersonSchema.setGrpPolNo(tLCPolSchema.getGrpPolNo());
		nLJSPayPersonSchema.setAgentCom(tLCPolSchema.getAgentCom());
		nLJSPayPersonSchema.setAgentGroup(tLCPolSchema.getAgentGroup());
		nLJSPayPersonSchema.setAgentType(tLCPolSchema.getAgentType());
		nLJSPayPersonSchema.setPayAimClass("1");
		nLJSPayPersonSchema.setCurrency(tLCPremSchema.getCurrency());
		nLJSPayPersonSchema.setSumActuPayMoney((-1)* tZK);
		nLJSPayPersonSchema.setSumDuePayMoney((-1)* tZK);
		nLJSPayPersonSchema.setLastPayToDate("1899-12-31");
		nLJSPayPersonSchema.setCurPayToDate(tLCPremSchema
				.getPaytoDate());
		nLJSPayPersonSchema.setPayType(tPayType);
		nLJSPayPersonSchema.setPayDate("");
		nLJSPayPersonSchema.setApproveCode(tLCPolSchema.getApproveCode());
		nLJSPayPersonSchema.setApproveDate(tLCPolSchema.getApproveDate());
		nLJSPayPersonSchema.setApproveTime(tLCPolSchema.getApproveTime());
		nLJSPayPersonSchema.setRiskCode(tLCPolSchema.getRiskCode());
		nLJSPayPersonSchema.setOperator(tLCPremSchema.getOperator());
		nLJSPayPersonSchema.setMakeDate(theCurrentDate);
		nLJSPayPersonSchema.setMakeTime(theCurrentTime);
		nLJSPayPersonSchema.setModifyDate(theCurrentDate);
		nLJSPayPersonSchema.setModifyTime(theCurrentTime);		

		return nLJSPayPersonSchema;
	}	
	
	/* 根据保费项计算折扣 */
	private String cal(VData tVData)
	{		
		TransferData tTransferData = new TransferData();
		LCPolSchema tLCPolSchema = new LCPolSchema();
		LCPremSchema tLCPremSchema = new LCPremSchema();
		tTransferData = (TransferData)tVData.getObjectByObjectName("TransferData", 0);
		if (tTransferData == null) {
			// @@错误处理
			CError.buildErr(this, "在接受数据时没有得到TransferData的数据!");
			return null;
		}
		tLCPolSchema = (LCPolSchema)tVData.getObjectByObjectName("LCPolSchema", 0);
		if (tLCPolSchema == null) {
			// @@错误处理
			CError.buildErr(this, "在接受数据时没有得到LCPolSchema的数据!");
			return null;
		}
		tLCPremSchema = (LCPremSchema)tVData.getObjectByObjectName("LCPremSchema", 0);
		if (tLCPremSchema == null) {
			// @@错误处理
			CError.buildErr(this, "在接受数据时没有得到LCPremSchema的数据!");
			return null;
		}

		String calcode = (String) tTransferData.getValueByName("CalCode");
		
		String contno = tLCPolSchema.getContNo();
		String insuredno = tLCPolSchema.getInsuredNo();
		String agentcode = tLCPolSchema.getAgentCode();
		tLCPolSchema.setPrem(tLCPremSchema.getPrem()); //多重折扣时，在上一折扣的基础上再折扣
		
		//准备计算信息
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(contno);
		if(!tLCContDB.getInfo()){
			CError.buildErr(this, "查询合同信息失败！");
			return null;
		}
		
		LCAppntDB tLCAppntDB = new LCAppntDB();
		tLCAppntDB.setContNo(contno);
		if(!tLCAppntDB.getInfo()){
			CError.buildErr(this, "查询投保人信息失败！");
			return null;
		}
		
		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(agentcode);
		if(!tLAAgentDB.getInfo()){
			CError.buildErr(this, "查询代理人信息失败！");
			return null;
		}
		
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		tLCInsuredDB.setContNo(contno);
		tLCInsuredDB.setInsuredNo(insuredno);
		if(!tLCInsuredDB.getInfo()){
			CError.buildErr(this, "查询被保人信息失败！");
			return null;
		}
		//准备BOMCont数据
		BOMCont cont = new BOMCont();
		cont = DealBOMCont(tLCContDB.getSchema());
		
		//准备被保人BOMAppnt数据
		BOMAppnt appnt = new BOMAppnt();		
		appnt = DealBOMAppnt(tLCAppntDB.getSchema());
		
		//准备代理人BOMAgent数据
		BOMAgent agent = new BOMAgent();
		agent = DealBOMAgent(tLAAgentDB.getSchema());
		
		//准备被保人BOMInsured数据
		BOMInsured insured = new BOMInsured();
		insured = DealBOMInsured(tLCInsuredDB.getSchema());
		
		//准备险种BOMPol数据
		BOMPol pol = new BOMPol();//一个险种
		pol = DealBOMPol(tLCPolSchema);
		
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
		tCalculator.addBasicFactor("Prem", String.valueOf(tLCPremSchema.getPrem()));
		tCalculator.setCalCode(calcode);
		logger.debug("算法： "+calcode);
		String rResult = tCalculator.calculate();
		logger.debug("$$$$:"+rResult);
		
		return rResult;
	}
	
	private BOMCont DealBOMCont(LCContSchema tLCContSchema){
		BOMCont cont = new BOMCont();
		try{			
			cont.setSellType(tLCContSchema.getSellType());//出单方式
			//tongmeng 2009-02-19
			//问题件信息先设置默认值
			cont.setComIssueFlag("0");//机构问题件标记
			cont.setCustomerIssueFlag("0");//客户问题件标记
			cont.setAppntIssueFlag("0");//投保人问题件标记
			cont.setAgentIssueFlag("0");//业务员问题件标记			
			cont.setAutoPayFlag(tLCContSchema.getAutoPayFlag());
			cont.setBankAccNo(tLCContSchema.getBankAccNo());
			cont.setBankCode(tLCContSchema.getBankCode());
			cont.setCardFlag(tLCContSchema.getCardFlag());
			cont.setContNo(tLCContSchema.getContNo());			
			cont.setManageCom(tLCContSchema.getManageCom());			
			cont.setOutPayFlag(tLCContSchema.getOutPayFlag());			
		}catch(Exception e){
			CError.buildErr(this, "准备BOMCont时出错！");
			e.printStackTrace();
		}
		return cont;
	}
	
	private BOMAppnt DealBOMAppnt(LCAppntSchema tLCAppntSchema){
		BOMAppnt appnt = new BOMAppnt();
		ExeSQL tempExeSQL = new ExeSQL();
		try{			
			appnt.setAppntName(tLCAppntSchema.getAppntName());
			appnt.setAppntNo(tLCAppntSchema.getAppntNo());
			appnt.setAppntSex(tLCAppntSchema.getAppntSex());		
			
			appnt.setNationality(tLCAppntSchema.getNationality());
			appnt.setNativePlace(tLCAppntSchema.getNativePlace());
			appnt.setOccupationCode(tLCAppntSchema.getOccupationCode());
			appnt.setOccupationType(tLCAppntSchema.getOccupationType());
			appnt.setPosition(tLCAppntSchema.getPosition());
			appnt.setRelationToInsured(tLCAppntSchema.getRelationToInsured());
			appnt.setRgtAddress(tLCAppntSchema.getRgtAddress());
			appnt.setSalary(new Double(tLCAppntSchema.getSalary()));
			
		}catch(Exception e){
			CError.buildErr(this, "准备BOMAppnt时出错");
		}
		return appnt;
	}
	
	private BOMAgent DealBOMAgent(LAAgentSchema tLAAgentSchema){
		BOMAgent agent = new BOMAgent();
		String tBlackList="select blacklisflag from latree where agentcode='"+"?agentcode?"+"'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tBlackList);
		sqlbv1.put("agentcode", tLAAgentSchema.getAgentCode());
		ExeSQL tempExeSQL = new ExeSQL();
		String tBlackFlag = tempExeSQL.getOneValue(sqlbv1);
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
	
	private BOMInsured DealBOMInsured(LCInsuredSchema tLCInsuredSchema){
		BOMInsured Insured = new BOMInsured();
		ExeSQL tempExeSQL = new ExeSQL();
		try{
			//参考AutoUWCheckBL.DealBOMInsured
			Insured.setInsuredNo(tLCInsuredSchema.getInsuredNo());
			
			String polApplyDateSql = "select PolApplyDate from lccont where contno='"+"?contno?"+"'";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(polApplyDateSql);
			sqlbv2.put("contno", tLCInsuredSchema.getContNo());
			String tpolApplyDate = tempExeSQL.getOneValue(sqlbv2);
			int tInsAge = PubFun.calInterval(tLCInsuredSchema.getBirthday(), tpolApplyDate, "Y");
			Insured.setInsuredAppAge(Double.valueOf(String.valueOf(tInsAge)));//投保年龄
			
			Insured.setInsuredStat(tLCInsuredSchema.getInsuredStat());
			Insured.setMarriage(tLCInsuredSchema.getMarriage());
			Insured.setRelationToAppnt(tLCInsuredSchema.getRelationToAppnt());
			Insured.setSalary(Double.valueOf(String.valueOf(tLCInsuredSchema.getSalary())));
			Insured.setSex(tLCInsuredSchema.getSex());

			String sumMultSql = "select (case when sum( case when mult=0 then 1 else mult end) is null then 0 else sum( case when mult=0 then 1 else mult end) end) from lcpol where insuredno='"+"?insuredno?"+"'";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(sumMultSql);
			sqlbv3.put("insuredno", tLCInsuredSchema.getInsuredNo());
			String tsumMult = tempExeSQL.getOneValue(sqlbv3);
			Insured.setSumMult(tsumMult);//累计投保份数

			//既往异常投保/理赔史 OuncommonConTent
			String tOuncommonConTent_sql = "select count(*) from lccustomerimpart where impartcode in('A0117','A0118','D0115','D0116','D0117','C0108','A0528','A0529')"
				+" and contno='"+"?contno?"+"' and customerno='"+"?customerno?"+"' ";//and CustomerNoType='1'";
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(tOuncommonConTent_sql);
			sqlbv4.put("contno", tLCInsuredSchema.getContNo());
			sqlbv4.put("customerno", tLCInsuredSchema.getInsuredNo());
			String tOuncommonConTent = "";
			tOuncommonConTent = tempExeSQL.getOneValue(sqlbv4);
			if(tOuncommonConTent!=null&&!tOuncommonConTent.equals("")&&Integer.parseInt(tOuncommonConTent)>0){
				Insured.setOuncommonConTent("1");
			}else{
				Insured.setOuncommonConTent("0");
			}

		}catch(Exception e){
			CError.buildErr(this, "准备BOMAppnt时出错");
		}
		return Insured;
	}
	
	private BOMPol DealBOMPol(LCPolSchema tLCPolSchema){
		BOMPol Pol = new BOMPol();
		ExeSQL tempExeSQL = new ExeSQL();
		try{			
			Pol.setAmnt(Double.valueOf(String.valueOf(tLCPolSchema.getAmnt())));
			Pol.setUWFlag(tLCPolSchema.getUWFlag());
			Pol.setPrem(new Double(tLCPolSchema.getPrem()));
			Pol.setInsuredNo(tLCPolSchema.getInsuredNo());
			Pol.setMainPolNo(tLCPolSchema.getMainPolNo());
			Pol.setMult(new Double(tLCPolSchema.getMult()));
			Pol.setPayYears(new Double(tLCPolSchema.getPayYears()));
			Pol.setPolNo(tLCPolSchema.getPolNo());
			Pol.setInsuYear(Double.valueOf(String.valueOf(tLCPolSchema.getInsuYear())));
			Pol.setInsuYearFlag(tLCPolSchema.getInsuYearFlag());
			Pol.setCurrency(tLCPolSchema.getCurrency());
			Pol.setLiveGetMode(tLCPolSchema.getLiveGetMode());
			Pol.setBonusGetMode(tLCPolSchema.getBonusGetMode());
			Pol.setRiskCode(tLCPolSchema.getRiskCode());
			if(!(tLCPolSchema.getCValiDate()==null||"".equals(tLCPolSchema.getCValiDate()))){
				theDate=tLCPolSchema.getCValiDate()+" 00:00:00";
				Pol.setCValiDate(sdf.parse(theDate));
			}
			Pol.setFloatRate(new Double(tLCPolSchema.getFloatRate()));
			
		}catch(Exception e){
			CError.buildErr(this, "准备BOMAppnt时出错");
		}
		return Pol;
	}
	
	public VData getResult() {
		return mVData;
	}
}
