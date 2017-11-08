package com.sinosoft.ibrms;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.sinosoft.ibrms.bom.BOMAgent;
import com.sinosoft.ibrms.bom.BOMAppnt;
import com.sinosoft.ibrms.bom.BOMBnf;
import com.sinosoft.ibrms.bom.BOMBqAppnt;
import com.sinosoft.ibrms.bom.BOMBqBnf;
import com.sinosoft.ibrms.bom.BOMBqCont;
import com.sinosoft.ibrms.bom.BOMBqInsured;
import com.sinosoft.ibrms.bom.BOMBqPol;
import com.sinosoft.ibrms.bom.BOMCalClaim;
import com.sinosoft.ibrms.bom.BOMCalInsured;
import com.sinosoft.ibrms.bom.BOMCont;
import com.sinosoft.ibrms.bom.BOMDTTable;
import com.sinosoft.ibrms.bom.BOMElement;
import com.sinosoft.ibrms.bom.BOMInsured;
import com.sinosoft.ibrms.bom.BOMMainPol;
import com.sinosoft.ibrms.bom.BOMPol;
import com.sinosoft.ibrms.bom.BOMSubPol;
import com.sinosoft.ibrms.bom.BOMSubPol2;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.PubFun;
/**
 * 本类是所有规则校验的接口
 * 每一个函数代表一种业务规则校验类型
 * main是准备数据的sample
 */

public class RuleUI {
private static Logger logger = Logger.getLogger(RuleUI.class);
	
	//规则模块，根据这个参数来决定执行的业务规则模块
	private String Business;
		
	private List mResultList = new ArrayList();
	/**
	 * funciton: 个险自动核保
	 * @param cont
	 * @param appnt
	 * @param agent
	 * @param insureds
	 * @param pols
	 * @param mainpols
	 * @param subpols
	 * @param subpol2s
	 * @param bnfs
	 * @return
	 */
public List AutoUWIndUI(BOMCont cont,BOMAppnt appnt,BOMAgent agent,BOMInsured[] insureds,BOMPol[] pols,BOMMainPol[] mainpols,BOMSubPol[] subpols,BOMSubPol2[] subpol2s,BOMBnf[] bnfs){
		
		
		List list = new ArrayList();
		list.add(cont);
		list.add(appnt);
		list.add(agent);
		list.add(insureds);
		list.add(pols);
		list.add(mainpols);
		list.add(subpols);
		list.add(subpol2s);
		list.add(bnfs);
		Business = "01";
		String currentDate = PubFun.getCurrentDate();
		String currentTime = PubFun.getCurrentTime();
		
		RuleTask ruleTask = new RuleTask(list,Business,"");
		
		//Map map = new HashMap();
		long startTime = System.currentTimeMillis();
		List results = (List) ruleTask.execute(); //execRule(list,sql,parameter,boms);
		long time1 = System.currentTimeMillis()-startTime;
		String contNo = cont.getContNo();
		logger.debug("------ rule task execute time is "+(System.currentTimeMillis()-startTime));
		UpdateRuleResultTask task = new UpdateRuleResultTask();
		task.setBusiness(Business);
		task.setContNo(contNo);
		task.setCurrentDate(currentDate);
		task.setCurrentTime(currentTime);
		task.setResults(results);
		task.setOperator("AutoUWIndUI");
		task.setManageCom(cont.getManageCom());
		task.setTime((int)time1);
		UpdateRuleResultThread.getInstance().addTask(task);
		
		List notPassArrayList=new ArrayList();
		SQLTaskResult rs=null;
		for(int i=0;i<results.size();i++)
		{
			 rs=(SQLTaskResult)results.get(i);
			if(rs.getErrors().getErrorCount()==0)
			{
				notPassArrayList.add(rs);
			}
		}
		
		mResultList = notPassArrayList;
		return notPassArrayList;
	}


//tongmeng 2010-12-15 add
public String AutoUWIndUIForList(List tBomList,String tBusiness,String tCalCode){
	
	
	List list = new ArrayList();
	list = tBomList;
//	list.add(cont);
//	list.add(appnt);
//	list.add(agent);
//	list.add(insureds);
//	list.add(pols);
//	list.add(mainpols);
//	list.add(subpols);
//	list.add(subpol2s);
//	list.add(bnfs);
	Business = tBusiness;
	String currentDate = PubFun.getCurrentDate();
	String currentTime = PubFun.getCurrentTime();
	
	RuleTask ruleTask = new RuleTask(list,Business,tCalCode);
	
	//Map map = new HashMap();
	long startTime = System.currentTimeMillis();
	List results = (List) ruleTask.execute(); //execRule(list,sql,parameter,boms);
	long time1 = System.currentTimeMillis()-startTime;
	String contNo = "00000000000000000000";
	logger.debug("------ rule task execute time is "+(System.currentTimeMillis()-startTime));
	UpdateRuleResultTask task = new UpdateRuleResultTask();
	if(Business==null||Business.equals(""))
	{
		task.setBusiness("a");
	}
	else
	{
		task.setBusiness(Business);
	}
	task.setContNo(contNo);
	task.setCurrentDate(currentDate);
	task.setCurrentTime(currentTime);
	task.setResults(results);
	task.setOperator("AutoUWIndUI");
	task.setManageCom("99999999");
	task.setTime((int)time1);
	UpdateRuleResultThread.getInstance().addTask(task);
	
	/*
	 SQLTaskResult SQLResult=(SQLTaskResult)tList.get(i);
					String result=SQLResult.getResult();
					String templateId=SQLResult.getTemplateId();//模板号
					String tRuleid = SQLResult.getRuleid();//
					String tUWGrade1 = SQLResult.getUWLevel();//规则定制时的核保级别
					String tRuleName = SQLResult.getRulename();//规则名 
	 */
	List notPassArrayList=new ArrayList();

	SQLTaskResult rs=null;
	for(int i=0;i<results.size();i++)
	{
		 rs=(SQLTaskResult)results.get(i);
		if(rs.getErrors().getErrorCount()==0)
		{
			notPassArrayList.add(rs);
		}
	}
	
	mResultList = notPassArrayList;
	if(notPassArrayList.size()<=0)
	{
		return "0";
	}
	else
	{
		return((SQLTaskResult)notPassArrayList.get(0)).getResult();
	}
}

public List getResultList()
{
	return this.mResultList;
}


public List AutoUWIndUIForTest(BOMCont cont,BOMAppnt appnt,BOMAgent agent,BOMInsured[] insureds,BOMCalInsured[] calInsured,BOMPol[] pols,BOMMainPol[] mainpols,BOMSubPol[] subpols,BOMSubPol2[] subpol2s,BOMBnf[] bnfs,BOMBqCont tBOMBqCont,BOMBqAppnt tBOMBqAppnt,BOMBqInsured[] BOMBqInsureds,BOMBqPol[] BOMBqPols,BOMBqBnf[] BOMBqBnfs,BOMCalClaim tBOMCalClaim,BOMElement tBOMElement,BOMDTTable tBOMDTTable){
	
	
	List list = new ArrayList();
	return list;
}
	
public static void main(String[] args){
	
	//------------创建线程池实例----------------
	String sql = "select result from dt001 where ?LCCont.Amnt? > col1 and ?LCAppnt.AppntName? = col2" +
			" and ?LCInsured.age? > col3 and ?LCBnf.BnfType?=col4 " ;
	String parameter = "LCCont.Amnt;LCAppnt.AppntName;LCInsured.age;LCBnf.BnfType";
	String boms = "LCCont;LCAppnt;LCInsured;LCBnf";
	
	BOMCont cont = new BOMCont();
	cont.setContNo("ew34");
	cont.setAmnt(new Double(2345));
	cont.setManageCom("86110007");
	cont.setContPrem(new Double(2345));
	cont.setAutoUWTimes(new Double(3));
	
	BOMAppnt appnt = new BOMAppnt();
	appnt.setAppntName("wangjz");
	Calendar cal = Calendar.getInstance();
	cal.set(Calendar.YEAR, 2005);
	appnt.setAppntBirthday(cal.getTime());
	
	BOMAgent agent = new BOMAgent();
	
	BOMInsured[] insureds = new  BOMInsured[3];
	
	BOMInsured insured = new BOMInsured();
	insured.setInsuredAppAge(new Double(12));
	insureds[0] = insured;
	
	insured = new BOMInsured();
	insured.setInsuredAppAge(new Double(24));;
	insureds[1] = insured;
	
	insured = new BOMInsured();
	insured.setInsuredAppAge(new Double(32));
	insureds[2] = insured;		
	
	BOMPol[] pols = new BOMPol[5];
	
	BOMPol pol = new BOMPol();
	pol.setRiskCode("2");
	pol.setFatherBOM(insureds[0]);
	pols[0] = pol;
		
	pol = new BOMPol();
	pol.setRiskCode("3");
	pol.setFatherBOM(insureds[0]);
	pols[1] = pol;
	
	pol = new BOMPol();
	pol.setRiskCode("4");
	pol.setFatherBOM(insureds[1]);
	pols[2] = pol;
	
	pol = new BOMPol();
	pol.setRiskCode("5");
	pol.setFatherBOM(insureds[2]);
	pols[3] = pol;
	
	pol = new BOMPol();
	pol.setRiskCode("6");
	pol.setFatherBOM(insureds[2]);
	pols[4] = pol;
	
	BOMBnf[] bnfs = new BOMBnf[4];
	
	BOMBnf  bnf = new BOMBnf();
	bnf.setBnfType("aaa");
	bnf.setFatherBOM(insureds[0]);
	bnfs[0] = bnf;
	
	bnf = new BOMBnf();
	bnf.setBnfType("bbb");
	bnf.setFatherBOM(insureds[0]);
	bnfs[1] = bnf;
	
	bnf = new BOMBnf();
	bnf.setBnfType("ccc");
	bnf.setFatherBOM(insureds[1]);
	bnfs[2] = bnf;
	
	bnf = new BOMBnf();
	bnf.setBnfType("ddd");
	bnf.setFatherBOM(insureds[2]);
	bnfs[3] = bnf;
	
	BOMMainPol[] mainpols = new BOMMainPol[2];	
	BOMMainPol mainpol = new BOMMainPol();
	mainpol.setRiskCode("2");
	mainpol.setFatherBOM(insureds[0]);
	mainpol.setAmnt(new Double(15000));
	mainpol.setMainPolNo("110670");
	
	mainpols[0] = mainpol;
		
	mainpol = new BOMMainPol();
	mainpol.setRiskCode("2");
	mainpol.setFatherBOM(insureds[1]);
	mainpol.setMainPolNo("120670");
	mainpol.setAmnt(new Double(24000));
	
	mainpols[1] = mainpol;
	
	
	BOMSubPol[] subpols = new BOMSubPol[2];	
	BOMSubPol subpol = new BOMSubPol();
	subpol.setRiskCode("2");
	subpol.setPolNo("123090");
	subpol.setFatherBOM(mainpols[0]);
	
	subpols[0] = subpol;
		
	subpol = new BOMSubPol();
	subpol.setRiskCode("3");
	subpol.setPolNo("123070");
	subpol.setFatherBOM(mainpols[1]);
	subpols[1] = subpol;
	
	BOMSubPol2[] subpol2s = new BOMSubPol2[2];	
	BOMSubPol2 subpol2 = new BOMSubPol2();
	subpol2.setRiskCode("2");
	subpol2.setFatherBOM(mainpols[0]);
	subpol2s[0] = subpol2;
		
	subpol2 = new BOMSubPol2();
	subpol2.setRiskCode("3");
	subpol2.setFatherBOM(mainpols[0]);
	subpol2s[1] = subpol2;
	
	BOMElement element = new BOMElement();
	element.setPrem(100.00);
	element.setAmnt(10000.00);
	element.setAppAge(19.00);
	element.setMult(10.00);
	List list = new ArrayList();
	list.add(cont);
	list.add(appnt);
	list.add(agent);
	list.add(insureds);
	list.add(pols);
	list.add(mainpols);
	list.add(subpols);
	list.add(subpol2s);
	list.add(bnfs);
	
	list.add(element);
	RuleUI rule = new RuleUI();
	//List listSql = rule.AutoUWIndUI(cont,appnt,agent,insureds, pols,mainpols,subpols,subpol2s,bnfs);
	
	
	Calculator tCalculator = new Calculator();
	tCalculator.setBOMList(list);
	tCalculator.setCalCode("RU03");
	logger.debug("$$$$:"+tCalculator.calculate());
	
	
	tCalculator = new Calculator();
	tCalculator.setBOMList(list);
	tCalculator.setCalCode("RU03");
	
//	logger.debug(listSql.size());
//	for(int i=0;i<listSql.size();i++)
//	
//		logger.debug(listSql.get(i));
//	

 // String res = rule.AutoUWIndUI(list, "99","R_01");
  //logger.debug("res:"+res);
  }
}
