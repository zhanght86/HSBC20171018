package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import java.util.Enumeration;
import java.util.Hashtable;

import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LJSPayPersonDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.TaxCalculator;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LJSPayPersonSchema;
import com.sinosoft.lis.schema.LJSPaySchema;
import com.sinosoft.lis.vbl.LCPremBLSet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

public class TBPrepareLJS {
private static Logger logger = Logger.getLogger(TBPrepareLJS.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mVData = new VData();
	private String mGetNoticeNo="";
	/** 数据操作字符串 */
	private String mOperate;

	// 统一更新日期，时间
	private String theCurrentDate = PubFun.getCurrentDate();
	private String theCurrentTime = PubFun.getCurrentTime();
	
	public TBPrepareLJS() {
	}

	/* 根据保费项计算折扣 */
	public boolean prepare(VData tVData,String tOperate)
	{
		// 将操作数据拷贝到本类中
		this.mOperate = tOperate;
		
		LCPremBLSet tLCPremBLSet = new LCPremBLSet();
		LJSPayPersonSet tLJSPayPersonSet = new LJSPayPersonSet();
		LCPolSchema tLCPolSchema = new LCPolSchema();
				
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
		tLJSPayPersonSet = (LJSPayPersonSet)tVData.getObjectByObjectName("LJSPayPersonSet", 0);
		if (tLJSPayPersonSet == null) {
			// @@错误处理
			CError.buildErr(this, "在接受数据时没有得到LJSPayPersonSet的数据!");
			return false;
		}
		mGetNoticeNo = (String)tVData.getObjectByObjectName("String", 0);
		if (mGetNoticeNo == null) {
			// @@错误处理
			CError.buildErr(this, "在接受数据时没有得到LCPolSchema的数据!");
			return false;
		}		
		
		LJSPayPersonSet rLJSPayPersonSet = new LJSPayPersonSet();
		LJSPaySet rLJSPaySet = new LJSPaySet();
		LCPremSchema tLCPremSchema = new LCPremSchema();
		for(int j=1;j<=tLCPremBLSet.size();j++)
		{			
			tLCPremSchema = new LCPremSchema();
			tLCPremSchema.setSchema(tLCPremBLSet.get(j));
			//生成应收子表
			rLJSPayPersonSet.add(PreperaLjspayperson(tLCPolSchema, tLCPremSchema,"ZC"));
		}
		
		if(tLJSPayPersonSet.size()>0)

			rLJSPayPersonSet.add(tLJSPayPersonSet);//折扣交费信息
		
		// Update by YaoYi for grp in 2011-9-16 TODO
		
		//营改增 add zhangyingfeng 2016-07-06
		//价税分离
		TaxCalculator.calBySchemaSet(rLJSPayPersonSet);
	    //end zhangyingfeng 2016-07-06
		rLJSPaySet = dealJSPay(rLJSPayPersonSet, tLCPolSchema.getContType());
		
		if(rLJSPayPersonSet!=null && rLJSPayPersonSet.size()>0)
			mVData.add(rLJSPayPersonSet);
		if(rLJSPaySet!=null && rLJSPaySet.size()>0)
			mVData.add(rLJSPaySet);
		
		return true;
	}
	
	private LJSPayPersonSchema PreperaLjspayperson(LCPolSchema tLCPolSchema,LCPremSchema tLCPremSchema,String tPayType) 
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
		nLJSPayPersonSchema.setSumActuPayMoney(tLCPremSchema.getPrem());
		nLJSPayPersonSchema.setSumDuePayMoney(tLCPremSchema.getPrem());
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
	
	/**
	 * 处理实收保费
	 * 目前只生成实收总表
	 * @return
	 */
	private LJSPaySet dealJSPay(LJSPayPersonSet cLJSPayPersonSet, String contType) {
		LJSPayPersonSet tLJSPayPersonSet = new LJSPayPersonSet();	
		LJSPayPersonSet oLJSPayPersonSet = new LJSPayPersonSet();
		//查找其他险种的应收子表
		LJSPayPersonDB tLJSPayPersonDB=new LJSPayPersonDB();
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		String sql = "select * from ljspayperson where contno='"+"?contno?"+"' ";
		sqlbv1.put("contno", cLJSPayPersonSet.get(1).getContNo());
		if(mOperate.equals("1")){//签单前：增加修改一个险种的数据
			sql=sql+ "and polno <>'"+"?polno?"+"'";
		sqlbv1.put("polno", cLJSPayPersonSet.get(1).getPolNo());
		}
		if(mOperate.equals("2")){//只是加费操作
			sql=sql+" and (polno <>'"+"?polno1?"+"' or (polno ='"+"?polno2?"+"' and substr(payplancode,1,6) <> '000000'))";
		sqlbv1.put("polno1", cLJSPayPersonSet.get(1).getPolNo());
		sqlbv1.put("polno2", cLJSPayPersonSet.get(1).getPolNo());
		}
		if(mOperate.equals("3")){//签单时操作：汇总一个合同，cLJSPayPersonSet已经包含所有子表，不需要再查询其他子表
			sql=sql+ "and 1=2";
		}
		sqlbv1.sql(sql);
		oLJSPayPersonSet = tLJSPayPersonDB.executeQuery(sqlbv1);
		
		if(oLJSPayPersonSet.size()>0)
			tLJSPayPersonSet.add(oLJSPayPersonSet);
		
		tLJSPayPersonSet.add(cLJSPayPersonSet);
		
		LJSPaySet tLJSPaySet = new LJSPaySet();
		LJSPaySchema tLJSPaySchema = new LJSPaySchema();
		//按照币种汇总LJSpay
		Hashtable tCurrencyMap = new Hashtable();
		for(int i=1;i<=tLJSPayPersonSet.size();i++)
		{
//			//按照币种汇总
//			String tCurrency = tLJSPayPersonSet.get(i).getCurrency();
//			// TODO: 为了演示，团险如果没有币种，默认人民币
//			//if (null == tCurrency) tCurrency = "01";
//			if(!tCurrencyMap.containsKey(tCurrency))
//			{
//				tCurrencyMap.put(tCurrency, tLJSPayPersonSet.get(i).getSumDuePayMoney());
//			}
//			else
//			{
//				double tempSumpay = PubFun.round((Double)tCurrencyMap.get(tCurrency), 2) + PubFun.round(tLJSPayPersonSet.get(i).getSumDuePayMoney(),2);
//				tCurrencyMap.put(tCurrency, tempSumpay);
//			}	
			
			//营改增  add zhangyingfeng 2016-07-06
			//注释掉原来的，以后需要可以改回
			//需要汇总 总额 总净额  总税额
			String tCurrency = tLJSPayPersonSet.get(i).getCurrency();			
			if(!tCurrencyMap.containsKey(tCurrency))
			{
				Hashtable tSumLjSPayMap = new Hashtable<String, Double>();  //放置  总净额  总税额  总额
				tSumLjSPayMap.put(tCurrency+"Sum", tLJSPayPersonSet.get(i).getSumActuPayMoney());
				tSumLjSPayMap.put(tCurrency+"NetAm", tLJSPayPersonSet.get(i).getNetAmount());
				tSumLjSPayMap.put(tCurrency+"TaxAm", tLJSPayPersonSet.get(i).getTaxAmount());
				tSumLjSPayMap.put(tCurrency+"Tax", tLJSPayPersonSet.get(i).getTax());   //税率暂时取第一条， 系统后续具体业务类型可能税率不同，汇总不能统一
				tCurrencyMap.put(tCurrency, tSumLjSPayMap);
			}
			else
			{
				double tempSumpay = PubFun.round((Double)((Hashtable)tCurrencyMap.get(tCurrency)).get(tCurrency+"Sum"), 2) + PubFun.round(tLJSPayPersonSet.get(i).getSumActuPayMoney(),2);
				double tempSumpayNetAm = PubFun.round((Double)((Hashtable)tCurrencyMap.get(tCurrency)).get(tCurrency+"NetAm"), 2) + PubFun.round(tLJSPayPersonSet.get(i).getNetAmount(),2);
				double tempSumpayTaxAm = PubFun.round((Double)((Hashtable)tCurrencyMap.get(tCurrency)).get(tCurrency+"TaxAm"), 2) + PubFun.round(tLJSPayPersonSet.get(i).getTaxAmount(),2);
				double tempSumpayTax=(Double)((Hashtable)tCurrencyMap.get(tCurrency)).get(tCurrency+"Tax");
				Hashtable tSumLjSPayMap = new Hashtable<String, Double>();  //放置  总净额  总税额  总额
				tSumLjSPayMap.put(tCurrency+"Sum", tempSumpay);
				tSumLjSPayMap.put(tCurrency+"NetAm", tempSumpayNetAm);
				tSumLjSPayMap.put(tCurrency+"TaxAm", tempSumpayTaxAm);
				tSumLjSPayMap.put(tCurrency+"Tax",tempSumpayTax);
				tCurrencyMap.put(tCurrency, tSumLjSPayMap);
			}
			//营改增  end zhangyingfeng 2016-07-06
		}	
		
		Enumeration eKey=tCurrencyMap.keys(); 
		while(eKey.hasMoreElements()) 
		{ 
			String key=(String)eKey.nextElement();
//			double tValue = PubFun.round(((Double)tCurrencyMap.get(key)),2);
			
			//营改增 add zhangyingfeng  2016-07-06
			//需要增加净额 税额 税率   取值集合原来的单一值改为map
			double tempSumpay = PubFun.round((Double)((Hashtable)tCurrencyMap.get(key)).get(key+"Sum"), 2);
			double tempSumpayNetAm = PubFun.round((Double)((Hashtable)tCurrencyMap.get(key)).get(key+"NetAm"), 2);
			double tempSumpayTaxAm = PubFun.round((Double)((Hashtable)tCurrencyMap.get(key)).get(key+"TaxAm"), 2);
			double tempSumpayTax=(Double)((Hashtable)tCurrencyMap.get(key)).get(key+"Tax");
			//营改增  end zhangyingfeng 2016-07-06
			tLJSPaySchema = new LJSPaySchema();
			tLJSPaySchema.setGetNoticeNo(mGetNoticeNo); // 通知书号
			tLJSPaySchema.setOtherNo(tLJSPayPersonSet.get(1).getContNo());
			tLJSPaySchema.setOtherNoType("2");
			
			tLJSPaySchema.setAppntNo(tLJSPayPersonSet.get(1).getAppntNo());
			tLJSPaySchema.setPayDate(tLJSPayPersonSet.get(1).getPayDate());
			tLJSPaySchema.setStartPayDate(tLJSPayPersonSet.get(1)
					.getLastPayToDate()); // 交费最早应缴日期保存上次交至日期
			tLJSPaySchema.setBankOnTheWayFlag("0");
			tLJSPaySchema.setBankSuccFlag("0");
			tLJSPaySchema.setSendBankCount(0); // 送银行次数
			tLJSPaySchema.setApproveCode(tLJSPayPersonSet.get(1).getApproveCode());
			tLJSPaySchema.setApproveDate(tLJSPayPersonSet.get(1).getApproveDate());
			tLJSPaySchema.setRiskCode("000000");
			tLJSPaySchema.setSendBankCount(0);
			tLJSPaySchema.setSerialNo(""); // 流水号
			tLJSPaySchema.setOperator(tLJSPayPersonSet.get(1).getOperator());
			//新增加投保人证件类型及证件号
			// Update by YaoYi for grp in 2011-9-16 TODO
			if (!"2".equals(contType)) {
				LCAppntSchema tLCAppntSchema = new LCAppntSchema();
				LCAppntDB tLCAppntDB = new LCAppntDB();
				tLCAppntDB.setContNo(tLJSPayPersonSet.get(1).getContNo());
				if(!tLCAppntDB.getInfo())
				{
					this.mErrors.addOneError("保单号:" + tLJSPayPersonSet.get(1).getContNo() + "投保人信息查询失败！");
					return null;
				}
				tLCAppntSchema = tLCAppntDB.getSchema();
				tLJSPaySchema.setIDType(tLCAppntSchema.getIDType());
				tLJSPaySchema.setIDNo(tLCAppntSchema.getIDNo());
			}
			tLJSPaySchema.setManageCom(tLJSPayPersonSet.get(1).getManageCom());
			tLJSPaySchema.setAgentCom(tLJSPayPersonSet.get(1).getAgentCom());
			tLJSPaySchema.setAgentCode(tLJSPayPersonSet.get(1).getAgentCode());
			tLJSPaySchema.setAgentType(tLJSPayPersonSet.get(1).getAgentType());
			tLJSPaySchema.setAgentGroup(tLJSPayPersonSet.get(1).getAgentGroup());
			tLJSPaySchema.setMakeDate(theCurrentDate);
			tLJSPaySchema.setMakeTime(theCurrentTime);
			tLJSPaySchema.setModifyDate(theCurrentDate);
			tLJSPaySchema.setModifyTime(theCurrentTime);
			tLJSPaySchema.setCurrency(key);
//			tLJSPaySchema.setSumDuePayMoney(tValue);
			//营改增 add zhangyingfeng  2016-07-06
			//需要增加净额 税额 税率  
			tLJSPaySchema.setSumDuePayMoney(tempSumpay);  //总额
			tLJSPaySchema.setTaxAmount(tempSumpayTaxAm);  //税额
			tLJSPaySchema.setNetAmount(tempSumpayNetAm); //净额
			tLJSPaySchema.setTax(tempSumpayTax); //税率
			//营改增  end zhangyingfeng 2016-07-06
			tLJSPaySet.add(tLJSPaySchema);
		}
		
		return tLJSPaySet;
	}
	
	public VData getResult() {
		return mVData;
	}
}
