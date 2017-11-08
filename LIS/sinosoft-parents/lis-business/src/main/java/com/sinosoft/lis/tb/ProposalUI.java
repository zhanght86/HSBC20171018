package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCAppntGrpSchema;
import com.sinosoft.lis.schema.LCAppntIndSchema;
import com.sinosoft.lis.schema.LCBnfSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGrpAppntSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCSpecSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:投保功能类（界面输入）
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author YT
 * @version 1.0
 */
public class ProposalUI implements BusinessService {
private static Logger logger = Logger.getLogger(ProposalUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往前面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	// 业务处理相关变量
	/** 接受前台传输数据的容器 */
	private TransferData mTransferData = new TransferData();
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 保单 */
	private LCPolSchema mLCPolSchema = new LCPolSchema();
	/** 投保人 */
	private LCAppntIndSchema mLCAppntIndSchema = new LCAppntIndSchema();
	private LCAppntGrpSchema mLCAppntGrpSchema = new LCAppntGrpSchema();
	/** 被保人 */
	private LCInsuredSet mLCInsuredSet = new LCInsuredSet();
	/** 受益人 */
	private LCBnfSet mLCBnfSet = new LCBnfSet();
	/** 告知信息 */
	private LCCustomerImpartSet mLCCustomerImpartSet = new LCCustomerImpartSet();
	/** 特别约定 */
	private LCSpecSet mLCSpecSet = new LCSpecSet();
	// 一般的责任信息
	private LCDutySchema mLCDutySchema = new LCDutySchema();

	/** 可选责任信息 */
	private LCDutySet mLCDutySet1 = new LCDutySet();
	private boolean mNeedDuty = false;

	public ProposalUI() {
	}

	public static void main(String[] args) {
		/*
		 * DecimalFormat tDF=new DecimalFormat("0.00"); double t1 = 12.1256f;
		 * String t2=tDF.format(t1);
		 */

		ProposalUI ProposalUI1 = new ProposalUI();
		ProposalUI tProposalUI = new ProposalUI();
		String tDay = "1977-03-07";
		GlobalInput tGlobalInput = new GlobalInput();

		tGlobalInput.Operator = "001";
		tGlobalInput.ComCode = "asd";
		tGlobalInput.ManageCom = "86110000";

		// 合同信息
		LCContSchema tLCContSchema = new LCContSchema();
		tLCContSchema.setContNo("130110000013869");

		// 保单信息部分
		// LCPolSchema tLCPolSchema = new LCPolSchema();
		// tLCPolSchema.setContNo("130110000013869");
		// //tLCPolSchema.setPolNo("110110000001215 ");
		// tLCPolSchema.setProposalNo("130110000013869");
		// tLCPolSchema.setPrtNo("000404000");
		// tLCPolSchema.setGrpPolNo("00000000000000000000");//用于集体下个人投保
		// tLCPolSchema.setGrpContNo("00000000000000000000");
		// //tLCPolSchema.setProposalNo("86110020030110002923");
		// //tLCPolSchema.setMainPolNo("86110020030110004205");
		// //tLCPolSchema.setProposalNo("86110020030110011547");
		// tLCPolSchema.setManageCom("86110000");
		// //tLCPolSchema.setSignCom("001") ;
		// tLCPolSchema.setSaleChnl("02");
		// //tLCPolSchema.setAgentCom("001001");
		// tLCPolSchema.setAgentCode("86110526");
		// //tLCPolSchema.setHandler("OKO");
		// //tLCPolSchema.setBankCode("002");
		// //tLCPolSchema.setBankAccNo("KL8742558745587");
		// tLCPolSchema.setPayLocation("9");
		// tLCPolSchema.setOperator("LOL") ;
		// tLCPolSchema.setLiveGetMode("1");
		// tLCPolSchema.setBonusGetMode("1");
		// //tLCPolSchema.setSpecifyValiDate("N");
		//
		// //用于无名单
		// //tLCPolSchema.setPayLocation("1");
		// //tLCPolSchema.setInsuredAppAge(23);
		// //tLCPolSchema.setPolTypeFlag("1");
		// //tLCPolSchema.setInsuredPeoples(2);
		logger.debug("111保单信息部分初始化完成");
		LCDutySchema tLCDutySchema = new LCDutySchema();
		//
		// tLCPolSchema.setRiskCode("112299");
		// tLCPolSchema.setCValiDate("2005-04-05");
		// tLCPolSchema.setAmnt("2000");
		// tLCPolSchema.setPrem("");
		// tLCDutySchema.setPayIntv("0");//交费间隔-趸交
		// tLCDutySchema.setPayEndYear("1000");//终交年龄年期-1000岁
		// tLCDutySchema.setPayEndYearFlag("A");//终交年龄年期标志

		// mLCDutySchema.setRiskCode("111298");

		// 险种信息部分
		// ----------3位编码2打头的为个险
		// 211672
		// tLCDutySchema.setInsuYear("12");
		// tLCDutySchema.setInsuYearFlag("M");
		// tLCDutySchema.setPremToAmnt("P");
		// tLCDutySchema.setPrem("50");
		// tLCDutySchema.setAmnt("10000");
		// tLCDutySchema.setCalRule("1");

		/*
		 * (-201)MS康顺个人意外伤害保险 tLCPolSchema.setRiskCode("111601");
		 * tLCPolSchema.setCValiDate("2003-6-2");
		 *  // 保费保额互算(A) //tLCPolSchema.setAmnt("8000");
		 * tLCPolSchema.setPrem("200");
		 * //tLCPolSchema.setOccupationType("2");后面已有
		 * tLCPolSchema.setFloatRate("0.8");
		 *  // 其他必填项目：职业等级 //
		 */

		/*
		 * (-202)MS关爱提前给付特约 tLCPolSchema.setRiskCode("131601");
		 * tLCPolSchema.setCValiDate("2003-6-25"); // 保额算保费(G)
		 * tLCPolSchema.setAmnt("2000");
		 * 
		 * tLCDutySchema.setInsuYear("1"); tLCDutySchema.setInsuYearFlag("Y");
		 */

		/*
		 * (-203)高倍给付特约 tLCPolSchema.setRiskCode("131602");
		 * tLCPolSchema.setCValiDate("2002-11-25");
		 *  // 保额算保费(G) tLCPolSchema.setAmnt("30000");
		 * tLCPolSchema.setFloatRate("1"); // 其他必填项目：职业等级
		 */

		/*
		 * （204分红型）hzm-MS长裕终身保险 //1-个人保单表 tLCPolSchema.setRiskCode("112203");
		 * tLCPolSchema.setCValiDate("2003-6-15");//保单生效日期 //计算方向:保费保额互算(A)
		 * tLCPolSchema.setAmnt("2000");//总保额 tLCPolSchema.setPrem(1000); //总保费
		 * tLCPolSchema.setFloatRate("1"); //2-保险责任表
		 * tLCDutySchema.setPayIntv("12");//交费间隔-趸交
		 * tLCDutySchema.setPayEndYear("10");//终交年龄年期-1000岁
		 * tLCDutySchema.setPayEndYearFlag("Y");//终交年龄年期标志 //3-其它必要元素 //1-被保人年龄
		 * 自动算出 //2-被保人性别 后面录入 //3-保单份数 默认是1份 //
		 */

		/*
		 * （205）hzm-MS安康定期保险 //1-个人保单表 tLCPolSchema.setRiskCode("111301");
		 * tLCPolSchema.setCValiDate("2003-6-15");//保单生效日期 //计算方向:保费保额互算(A)
		 * //tLCPolSchema.setAmnt("2000");//总保额 tLCPolSchema.setPrem(500); //总保费
		 * tLCPolSchema.setFloatRate("0.8");
		 * 
		 * //2-保险责任表 tLCDutySchema.setPayIntv("0");//交费间隔
		 * tLCDutySchema.setPayEndYear("70");//终交年龄年期
		 * tLCDutySchema.setPayEndYearFlag("A");//终交年龄年期标志
		 * 
		 * //3-其它必要元素 //1-被保人年龄 自动算出 //2-被保人性别 后面录入 //3-保单份数 默认是1份 //
		 */

		/*
		 * (206)附加每日住院给付保险 tLCPolSchema.setRiskCode("121701");
		 * tLCPolSchema.setCValiDate("2002-12-03");
		 *  // 保额算保费(G) tLCPolSchema.setAmnt("30"); // 只能是30或50 // 其他必填项目：投保年龄
		 */

		/*
		 * (207) 附加意外伤害医疗保险 tLCPolSchema.setRiskCode("121801");
		 * tLCPolSchema.setCValiDate("2003-6-19");
		 *  // 保费保额互算(A) //tLCPolSchema.setAmnt("45000");
		 * tLCPolSchema.setPrem("150"); tLCPolSchema.setFloatRate("0.8");
		 *  // 其他必填项目：职业类别 //
		 */

		/*
		 * (208)附加住院医疗保险 tLCPolSchema.setRiskCode("121702");
		 * tLCPolSchema.setCValiDate("2003-6-08");
		 *  // 保费保额互算(A) tLCPolSchema.setAmnt("30000");
		 * tLCPolSchema.setPrem("60"); tLCPolSchema.setFloatRate("1"); //
		 * 其他必填项目：投保年龄 //
		 */

		/*
		 * (209)MS康吉重大疾病保险 tLCPolSchema.setRiskCode("111501");
		 * tLCPolSchema.setCValiDate("2003-6-2"); // 保费保额互算(A)
		 * //tLCPolSchema.setAmnt("30000"); tLCPolSchema.setPrem("500");
		 * tLCPolSchema.setFloatRate("0.7");
		 * 
		 * tLCDutySchema.setPayEndYear("10");
		 * tLCDutySchema.setPayEndYearFlag("Y"); tLCDutySchema.setPayIntv("3"); //
		 * 其他必填项目：投保年龄、性别 //
		 */

		/*
		 * （210）hzm-MS长宁终生保险 //1-个人保单表 tLCPolSchema.setRiskCode("112101");
		 * tLCPolSchema.setCValiDate("2003-6-15");//保单生效日期 //计算方向:保费保额互算(A)
		 * //tLCPolSchema.setAmnt("2000");//总保额 tLCPolSchema.setPrem(1000);
		 * //总保费 tLCPolSchema.setFloatRate("1"); //2-保险责任表
		 * tLCDutySchema.setPayIntv("0");//交费间隔
		 * tLCDutySchema.setPayEndYear("1000");//终交年龄年期
		 * tLCDutySchema.setPayEndYearFlag("A");//终交年龄年期标志 //3-其它必要元素 //1-被保人年龄
		 * 自动算出 //2-被保人性别 后面录入 //3-保单份数 默认是1份 //
		 */

		/*
		 * （211）hzm-MS长顺终生保险 //1-个人保单表 tLCPolSchema.setRiskCode("112202");
		 * tLCPolSchema.setCValiDate("2003-5-15");//保单生效日期 //计算方向:保费保额互算(A)
		 * tLCPolSchema.setAmnt("2000");//总保额 tLCPolSchema.setPrem(1000); //总保费
		 * tLCPolSchema.setFloatRate("1"); //2-保险责任表
		 * tLCDutySchema.setPayIntv("6");//交费间隔
		 * tLCDutySchema.setPayEndYear("20");//终交年龄年期
		 * tLCDutySchema.setPayEndYearFlag("Y");//终交年龄年期标志 //3-其它必要元素 //1-被保人年龄
		 * 自动算出 //2-被保人性别 后面录入 //3-保单份数 默认是1份 //
		 */

		/*
		 * （212）hzm-MS金榜题名两全分红保险 //1-个人保单表 tLCPolSchema.setRiskCode("112201");
		 * tLCPolSchema.setCValiDate("2003-5-11");//保单生效日期 //计算方向:保费保额互算(A)
		 * tLCPolSchema.setAmnt("20000");//总保额 tLCPolSchema.setPrem(1000); //总保费
		 * tLCPolSchema.setFloatRate("0.2"); //2-保险责任表
		 * tLCDutySchema.setPayIntv("0");//交费间隔
		 * tLCDutySchema.setPayEndYear("25");//终交年龄年期
		 * tLCDutySchema.setPayEndYearFlag("Y");//终交年龄年期标志 //3-其它必要元素 //1-被保人年龄
		 * 自动算出 //2-被保人性别 后面录入 //3-保单份数 默认是1份 //
		 */

		/*
		 * （213）hzm-MS长泰重大疾病终身保险 //1-个人保单表 tLCPolSchema.setRiskCode("112501");
		 * tLCPolSchema.setCValiDate("2002-12-15");//保单生效日期 //计算方向:保费保额互算(A)
		 * tLCPolSchema.setAmnt("2000");//总保额 //tLCPolSchema.setPrem(1000);
		 * //总保费 //2-保险责任表 tLCDutySchema.setPayIntv("1");//交费间隔
		 * tLCDutySchema.setPayEndYear("65");//终交年龄年期
		 * tLCDutySchema.setPayEndYearFlag("A");//终交年龄年期标志 //3-其它必要元素 //1-被保人年龄
		 * 自动算出 //2-被保人性别 后面录入 //3-保单份数 默认是1份
		 */

		/*
		 * (214) 航空旅客人身意外伤害保险 tLCPolSchema.setRiskCode("141801");
		 * tLCPolSchema.setCValiDate("2002-12-18");
		 *  // 其他因素算保费保额(O) //tLCPolSchema.setMult("5");
		 * tLCPolSchema.setMult("8");
		 * 
		 */

		/*
		 * （215）hzm-MS康泰重大疾病两全保险 //1-个人保单表 tLCPolSchema.setRiskCode("111502");
		 * tLCPolSchema.setCValiDate("2003-5-11");//保单生效日期 //计算方向:保费保额互算(A)
		 * tLCPolSchema.setAmnt("10000");//总保额 tLCPolSchema.setPrem(1000); //总保费
		 * tLCPolSchema.setFloatRate("1"); //2-保险责任表
		 * tLCDutySchema.setPayIntv(12);//交费间隔
		 * tLCDutySchema.setPayEndYear(20);//终交年龄年期
		 * tLCDutySchema.setPayEndYearFlag("Y");//终交年龄年期标志
		 * tLCDutySchema.setInsuYear(88); tLCDutySchema.setInsuYearFlag("A");
		 * //3-其它必要元素 //1-被保人年龄 自动算出 //2-被保人性别 后面录入 //3-保单份数 默认是1份
		 * 
		 * ///* /*（216）hzm-MS康顺个人意外伤害保险-(老年计划) //1-个人保单表
		 * tLCPolSchema.setRiskCode("111602");
		 * tLCPolSchema.setCValiDate("2002-12-15");//保单生效日期 //计算方向:保费算保额
		 * tLCPolSchema.setPrem(200); //总保费 //3-保单份数 默认是1份 //4-被保人年龄 有限制
		 */

		/*
		 * （217）hzm-MS长乐两全保险（分红型） //1-个人保单表 tLCPolSchema.setRiskCode("112204");
		 * tLCPolSchema.setCValiDate("2003-7-15");//保单生效日期 //计算方向:保费保额互算(A)
		 * tLCPolSchema.setAmnt("20529.67");//总保额 tLCPolSchema.setPrem(2000);
		 * //总保费 tLCPolSchema.setFloatRate("-1"); //2-保险责任表
		 * tLCDutySchema.setPayIntv("12");//交费间隔 tLCDutySchema.setInsuYear(20);
		 * tLCDutySchema.setInsuYearFlag("Y"); //3-其它必要元素 //1-被保人年龄 自动算出
		 * //2-被保人性别 后面录入 //3-保单份数 默认是1份
		 *  //
		 */

		/*
		 * (218)sxy-MS关爱特种疾病定期寿险保险 tLCPolSchema.setRiskCode("111302");
		 * tLCPolSchema.setCValiDate("2003-5-14");
		 *  // 其他因素算保费保额(O) //tLCPolSchema.setMult("5");
		 * tLCPolSchema.setMult("1"); tLCPolSchema.setFloatRate("0.6");
		 *  //
		 */

		/*
		 * (219)sxy-MS关爱特种疾病定期寿险保险附加险 tLCPolSchema.setRiskCode("121703");
		 * tLCPolSchema.setCValiDate("2003-5-14");
		 *  // 其他因素算保费保额(O) //tLCPolSchema.setMult("5");
		 * tLCPolSchema.setMult("1");
		 *  //
		 */

		/*
		 * （220）hzm-消费信贷 //1-个人保单表 tLCPolSchema.setRiskCode("311301");
		 * tLCPolSchema.setCValiDate("2003-8-15");//保单生效日期 //计算方向:保额算保费(A)
		 * tLCPolSchema.setAmnt(10000);
		 * 
		 * //2-保险责任表 tLCDutySchema.setInsuYear("3");//贷款期限
		 * tLCDutySchema.setInsuYearFlag("Y");//贷款期限单位
		 * tLCDutySchema.setPayIntv("0");
		 * 
		 * //3-其它必要元素 //1-被保人年龄 自动算出 //2-被保人性别 后面录入 //3-保单份数 默认是1份 //
		 */

		/*
		 * （222）hzm-MS康顺个人意外伤害保险-(学生计划) //1-个人保单表
		 * tLCPolSchema.setRiskCode("111603");
		 * tLCPolSchema.setCValiDate("2003-9-8");//保单生效日期 //计算方向:保费算保额
		 * tLCPolSchema.setPrem(50); //总保费 //3-保单份数 默认是1份 //4-被保人年龄 有限制
		 *  //
		 */

		/*
		 * （223）hzm-MS金喜年年 //1-个人保单表 tLCPolSchema.setRiskCode("112205");
		 * tLCPolSchema.setCValiDate("2003-9-8");//保单生效日期 //计算方向:保费算保额
		 * tLCPolSchema.setPrem(1000); //总保费 tLCDutySchema.setPayIntv("0");
		 *  //
		 */

		/*
		 * （225）hzm-MS老年康顺意外伤害保险（折式） //1-个人保单表
		 * tLCPolSchema.setRiskCode("141803");
		 * tLCPolSchema.setCValiDate("2004-3-10");//保单生效日期 //计算方向:保费算保额
		 * tLCPolSchema.setMult(1); //
		 *  //
		 */

		// ---三位编码6打头的为团险------------------
		/*
		 * （601）hzm-MS众悦团体年金保险 //1-个人保单表 tLCPolSchema.setRiskCode("212401");
		 * tLCPolSchema.setCValiDate("2003-12-1");//保单生效日期 //计算方向:保费保额互算(A)
		 * //tLCPolSchema.setPrem(1000); //总保费--从磁盘导入保费项数据，否则会翻倍
		 * //如果这里不注释，且磁盘导入数据还有，那么会翻倍再加上该总保费。
		 * 
		 * //2-保险责任表 tLCDutySchema.setPayIntv(0);
		 * tLCDutySchema.setGetYear(1);//领取年龄
		 * tLCDutySchema.setGetYearFlag("Y");//领取期间单位
		 * tLCDutySchema.setGetStartType("S");//起领日期计算类型
		 * //注意：生存金领取方式-必须在后面transferData处添加
		 * 
		 * //3-其它必要元素 //1-被保人年龄 自动算出 //2-被保人性别 后面录入 //3-保单份数 默认是1份 //
		 */

		/*
		 * (602)团体附加住院医疗保险 tLCPolSchema.setRiskCode("221701");
		 * tLCPolSchema.setCValiDate("2002-12-15");
		 *  // 保费保额互算(A) //tLCPolSchema.setAmnt("25000");
		 * tLCPolSchema.setPrem("300");
		 *  // 其他必填项目：投保年龄
		 */

		/*
		 * (603)团体附加住院津贴健康保险 tLCPolSchema.setRiskCode("221702");
		 * tLCPolSchema.setCValiDate("2002-12-18");
		 *  // 保额算保费(G) tLCPolSchema.setAmnt("50");// 只能填30或50元 // 其他必填项目：投保年龄
		 */

		/*
		 * (604)团体附加意外伤害医疗保险 tLCPolSchema.setRiskCode("221801");
		 * tLCPolSchema.setCValiDate("2002-11-18"); // 保费保额互算(A)
		 * //tLCPolSchema.setAmnt("50500"); tLCPolSchema.setPrem("1500");
		 *  // 其他必填项目：职业类别
		 */

		/*
		 * (605) 团体意外伤害保险 tLCPolSchema.setRiskCode("211601");
		 * tLCPolSchema.setCValiDate("2003-6-18"); // 保费保额互算(A)
		 * tLCPolSchema.setAmnt("1000"); tLCDutySchema.setInsuYear("2");
		 * tLCDutySchema.setInsuYearFlag("M"); // tLCPolSchema.setPrem("300");
		 * //tLCPolSchema.setFloatRate("0.6"); //保单类型:1 无名单
		 * //tLCPolSchema.setPolTypeFlag("1");
		 * //tLCPolSchema.setInsuredAppAge(30);
		 *  // 其他必填项目：职业类别 //
		 */

		/*
		 * （606）hzm-团体重大疾病保险 //1-个人保单表 tLCPolSchema.setRiskCode("211501");
		 * tLCPolSchema.setCValiDate("2002-12-15");//保单生效日期 //计算方向:保费保额互算(A)
		 * tLCPolSchema.setAmnt(2000);//总保额 //tLCPolSchema.setPrem(1000); //总保费
		 * 
		 * //2-保险责任表 //3-其它必要元素 //1-被保人年龄 自动算出 //3-保单份数 默认是1份 //
		 */

		/*
		 * （608）hzm-旅游意外保险 //1-个人保单表 tLCPolSchema.setRiskCode("211603");
		 * tLCPolSchema.setCValiDate("2003-7-15");//保单生效日期 //计算方向:保额算保费(A)
		 * tLCPolSchema.setAmnt(100000);
		 * 
		 * //2-保险责任表 tLCDutySchema.setInsuYear("3");//领取年龄
		 * tLCDutySchema.setInsuYearFlag("D");//领取期间单位
		 * //tLCDutySchema.setPayIntv("2");//交费间隔
		 * //注意：生存金领取方式-必须在后面transferData处添加
		 * 
		 * //3-其它必要元素 //1-被保人年龄 自动算出 //2-被保人性别 后面录入 //3-保单份数 默认是1份 //
		 */

		/*
		 * （609）hzm-MS团体年金保险 //1-个人保单表 tLCPolSchema.setRiskCode("211402");
		 * tLCPolSchema.setCValiDate("2003-9-1");//保单生效日期 //计算方向:保费保额互算(A)
		 * 
		 * tLCPolSchema.setPrem(100); //tLCPolSchema.setPrem((new
		 * Double(7505.1)).floatValue()); //tLCPolSchema.setAmnt(1000);
		 * 
		 * //2-保险责任表 tLCDutySchema.setGetYear("45");//领取年龄
		 * tLCDutySchema.setGetYearFlag("A");//领取期间单位
		 * tLCDutySchema.setPayIntv("0");//交费间隔
		 * //注意：生存金领取方式-必须在后面transferData处添加
		 * 
		 * //3-其它必要元素 //1-被保人年龄 自动算出 //2-被保人性别 后面录入 //3-保单份数 默认是1份 //
		 */

		/*
		 * （610）hzm-团体商业补充保险 //1-个人保单表 tLCPolSchema.setRiskCode("211801");
		 * tLCPolSchema.setCValiDate("2004-3-1");//保单生效日期
		 * tLCPolSchema.setStandbyFlag1("0");////0-在职/1-退休
		 * tLCPolSchema.setPrem(477); tLCPolSchema.setAmnt(0);
		 * tLCPolSchema.setFloatRate(-1); //计算方向:其它方式算保费保额(A) //2-保险责任表
		 * //tLCDutySchema.setPayIntv("0");//交费间隔
		 * 
		 * //3-其它必要元素 //1-被保人年龄 自动算出 //
		 */

		/*
		 * （611）hzm-团体建筑工地施工人员意外伤害保险 //1-个人保单表
		 * tLCPolSchema.setRiskCode("211604");
		 * tLCPolSchema.setCValiDate("2003-11-1");//保单生效日期 //计算方向:保费保额互算(A)
		 * tLCPolSchema.setAmnt(20000); //总保费 tLCPolSchema.setMult(10);
		 * tLCPolSchema.setStandbyFlag1("1000"); //2-保险责任表
		 * tLCDutySchema.setInsuYear("12");//领取年龄
		 * tLCDutySchema.setInsuYearFlag("M");//领取期间单位
		 * //注意：生存金领取方式-必须在后面transferData处添加
		 * 
		 * //3-其它必要元素 //1-被保人年龄 自动算出 //2-被保人性别 后面录入 //3-保单份数 默认是1份 //
		 */

		/*
		 * （615）hst--MS无忧行综合意外伤害保险 tLCPolSchema.setRiskCode("241801");
		 * tLCPolSchema.setCValiDate("2004-4-16");
		 *  // 其他因素算保费保额(O) tLCPolSchema.setFloatRate("1");
		 * tLCPolSchema.setStandbyFlag1("B"); tLCPolSchema.setStandbyFlag2("3");
		 *  // 其他必填项目：投保年龄 //
		 */

		// ---三位编码8打头的为银行险------------------
		// /*（802）MS金玉满堂两全保险
		// tLCPolSchema.setRiskCode("312201");
		// tLCPolSchema.setCValiDate("2004-2-29");
		// tLCPolSchema.setSpecifyValiDate("1");
		// // 其他因素算保费保额(O)
		// tLCPolSchema.setMult("2");
		// tLCDutySchema.setPayEndYear(5);
		// tLCDutySchema.setPayEndYearFlag("Y");
		// tLCDutySchema.setPayIntv("0");
		// tLCPolSchema.setFloatRate("0.8");
		// 其他必填项目：投保年龄、是否自动续保
		// */
		/*
		 * （803）MS同心卡保险 tLCPolSchema.setRiskCode("311603");
		 * tLCPolSchema.setCValiDate("2003-8-12");
		 *  // 其他必填项目：投保年龄、是否自动续保 //
		 */

		logger.debug("222险种信息部分初始化完成");

		// 投保人信息部分
		// LCAppntSchema tLCAppntSchema = new LCAppntSchema();
		// tLCAppntSchema.setAppntNo("0000005010");
		// tLCAppntSchema.setContNo("130110000001112 ");
		// tLCAppntIndSchema.setName("王政明");
		// tLCAppntIndSchema.setSex("1");
		// tLCAppntIndSchema.setBirthday("1973-01-15");
		// tLCAppntIndSchema.setIDType("0");
		// tLCAppntIndSchema.setIDNo("340102650117351");
		// tLCAppntIndSchema.setRelationToInsured("1");
		// tLCAppntIndSchema.setPhone("111");
		// tLCAppntIndSchema.setMobile("123");
		// tLCAppntIndSchema.setPostalAddress("asd");
		// tLCAppntIndSchema.setZipCode("100010");
		// tLCAppntIndSchema.setEMail("asd@263.net");
		// logger.debug("333投保人信息部分初始化完成");
		// // 投保人信息部分
		LCGrpAppntSchema tLCGrpAppntSchema = new LCGrpAppntSchema();
		tLCGrpAppntSchema.setGrpContNo("140110000000143 ");
		tLCGrpAppntSchema.setCustomerNo("0000005010");
		// tLCGrpAppntSchema.setRelationToInsured("1");
		// tLCGrpAppntSchema.setPhone("111");
		// tLCGrpAppntSchema.setEMail("asd@263.net");
		// logger.debug("333");

		// 被保人信息
		// LCInsuredSet tLCInsuredSet = new LCInsuredSet();

		// 主被保人
		LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
		tLCInsuredSchema.setContNo("130110000001114");
		tLCInsuredSchema.setInsuredNo("0000321600");
		/*
		 * Lis5.3 upgrade set tLCInsuredSchema.setName("紫晓梅");
		 * tLCInsuredSchema.setSex("1");
		 * tLCInsuredSchema.setBirthday("1990-01-15");
		 * tLCInsuredSchema.setIDType("0");
		 * tLCInsuredSchema.setIDNo("110102720315181");
		 */
		// tLCInsuredSet.add(tLCInsuredSchema);
		logger.debug("444被保人信息部分初始化完成");
		/*
		 * // 连带被保险人 tLCInsuredSchema = new LCInsuredSchema();
		 * tLCInsuredSchema.setInsuredGrade("S");
		 * 
		 * tLCInsuredSchema.setCustomerNo("22");
		 * tLCInsuredSchema.setName("haha"); tLCInsuredSchema.setSex("0");
		 * tLCInsuredSchema.setBirthday(tDay);
		 * 
		 * tLCInsuredSet.add(tLCInsuredSchema); //
		 */
		logger.debug("555连带被保险人信息初始化完成");

		// 受益人信息
		LCBnfSet tLCBnfSet = new LCBnfSet();

		LCBnfSchema tLCBnfSchema;
		tLCBnfSchema = new LCBnfSchema();
		tLCBnfSchema.setBnfType("0");
		tLCBnfSchema.setName("1");
		tLCBnfSchema.setRelationToInsured("01");
		tLCBnfSchema.setBnfGrade("1");
		tLCBnfSchema.setBnfLot("123.12");

		tLCBnfSet.add(tLCBnfSchema);
		// */
		/*
		 * logger.debug("666"); // 告知信息 LCCustomerImpartSet
		 * tLCCustomerImpartSet = new LCCustomerImpartSet();
		 * 
		 * LCCustomerImpartSchema tLCCustomerImpartSchema;
		 * tLCCustomerImpartSchema = new LCCustomerImpartSchema();
		 * 
		 * tLCCustomerImpartSchema.setCustomerNo("122");
		 * tLCCustomerImpartSchema.setCustomerNoType("I");
		 * tLCCustomerImpartSchema.setImpartCode("aa");
		 * tLCCustomerImpartSchema.setImpartContent("2");
		 * tLCCustomerImpartSchema.setImpartVer("11") ;
		 * 
		 * tLCCustomerImpartSet.add(tLCCustomerImpartSchema); //
		 */
		logger.debug("777");
		// /*
		/*
		 * // 特别约定 LCSpecSet tLCSpecSet = new LCSpecSet(); LCSpecSchema
		 * tLCSpecSchema; tLCSpecSchema = new LCSpecSchema();
		 * 
		 * tLCSpecSchema.setSpecCode("12"); tLCSpecSchema.setSpecType("ad");
		 * tLCSpecSchema.setSpecContent("dd");
		 * 
		 * tLCSpecSet.add(tLCSpecSchema); logger.debug("888"); //
		 */
		/*
		 * //可选责任信息--用于团体商业补充保险--必须在后面VData中填加该责任项 LCDutySet tLCDutySet=new
		 * LCDutySet(); LCDutySchema tLCDutySchema1 = new LCDutySchema();
		 * LCDutySchema tLCDutySchema2 = new LCDutySchema(); LCDutySchema
		 * tLCDutySchema3 = new LCDutySchema(); LCDutySchema tLCDutySchema4 =
		 * new LCDutySchema(); LCDutySchema tLCDutySchema5 = new LCDutySchema();
		 * LCDutySchema tLCDutySchema6 = new LCDutySchema(); LCDutySchema
		 * tLCDutySchema7 = new LCDutySchema(); LCDutySchema tLCDutySchema8 =
		 * new LCDutySchema(); LCDutySchema tLCDutySchema9 = new LCDutySchema();
		 * 
		 * tLCDutySchema1.setDutyCode("610001");
		 * tLCDutySchema1.setPayEndYear("10"); tLCDutySchema1.setInsuYear("10");
		 * tLCDutySchema1.setPayEndYearFlag("M");
		 * tLCDutySchema1.setInsuYearFlag("M"); tLCDutySchema1.setPayIntv("0");
		 * 
		 * tLCDutySchema2.setDutyCode("610002");
		 * tLCDutySchema2.setPayEndYear("10"); tLCDutySchema2.setInsuYear("10");
		 * tLCDutySchema2.setPayEndYearFlag("M");
		 * tLCDutySchema2.setInsuYearFlag("M"); tLCDutySchema2.setPayIntv("0"); //
		 * tLCDutySchema2.setGetYearFlag("Y"); // tLCDutySchema2.setGetYear(0);
		 * 
		 * tLCDutySchema3.setDutyCode("610003");
		 * tLCDutySchema3.setPayEndYear("10"); tLCDutySchema3.setInsuYear("10");
		 * tLCDutySchema3.setPayEndYearFlag("M");
		 * tLCDutySchema3.setInsuYearFlag("M"); tLCDutySchema3.setPayIntv("0"); //
		 * tLCDutySchema3.setGetYearFlag("Y"); // tLCDutySchema3.setGetYear(0);
		 * 
		 * tLCDutySchema4.setDutyCode("610004");
		 * tLCDutySchema4.setPayEndYear("10"); tLCDutySchema4.setInsuYear("10");
		 * tLCDutySchema4.setPayEndYearFlag("M");
		 * tLCDutySchema4.setInsuYearFlag("M"); tLCDutySchema4.setPayIntv("0"); //
		 * tLCDutySchema4.setGetYearFlag("Y"); // tLCDutySchema4.setGetYear(0);
		 * 
		 * tLCDutySchema5.setDutyCode("610005");
		 * tLCDutySchema5.setPayEndYear("10"); tLCDutySchema5.setInsuYear("10");
		 * tLCDutySchema5.setPayEndYearFlag("M");
		 * tLCDutySchema5.setInsuYearFlag("M"); tLCDutySchema5.setPayIntv("0");
		 * 
		 * tLCDutySchema6.setDutyCode("610006");
		 * tLCDutySchema6.setPayEndYear("12"); tLCDutySchema6.setInsuYear("12");
		 * tLCDutySchema6.setPayEndYearFlag("M");
		 * tLCDutySchema6.setInsuYearFlag("M"); tLCDutySchema6.setPayIntv("0");
		 * 
		 * tLCDutySchema7.setDutyCode("610007");
		 * tLCDutySchema7.setPayEndYear("12"); tLCDutySchema7.setInsuYear("12");
		 * tLCDutySchema7.setPayEndYearFlag("M");
		 * tLCDutySchema7.setInsuYearFlag("M"); tLCDutySchema7.setPayIntv("0");
		 * 
		 * tLCDutySchema8.setDutyCode("610008");
		 * tLCDutySchema8.setPayEndYear("12"); tLCDutySchema8.setInsuYear("12");
		 * tLCDutySchema8.setPayEndYearFlag("M");
		 * tLCDutySchema8.setInsuYearFlag("M"); tLCDutySchema8.setPayIntv("0");
		 * 
		 * tLCDutySchema9.setDutyCode("610009");
		 * tLCDutySchema9.setPayEndYear("12"); tLCDutySchema9.setInsuYear("12");
		 * tLCDutySchema9.setPayEndYearFlag("M");
		 * tLCDutySchema9.setInsuYearFlag("M"); tLCDutySchema9.setPayIntv("0");
		 * 
		 * tLCDutySet.add(tLCDutySchema1); tLCDutySet.add(tLCDutySchema2);
		 * tLCDutySet.add(tLCDutySchema3); tLCDutySet.add(tLCDutySchema4);
		 * tLCDutySet.add(tLCDutySchema5); // tLCDutySet.add(tLCDutySchema6); //
		 * tLCDutySet.add(tLCDutySchema7); // tLCDutySet.add(tLCDutySchema8); //
		 * tLCDutySet.add(tLCDutySchema9);
		 */
		// 准备传输数据 VData
		VData tVData = new VData();

		/** 如果是磁盘投保并且测试集体和个人分别交费的集体险，譬如：MS众悦团体年金保险（601） */
		/*
		 * LCPremSet tLCPremSet = new LCPremSet(); LCPremSchema tLCPremSchema =
		 * new LCPremSchema(); tLCPremSchema.setRate("0");
		 * tLCPremSchema.setStandPrem(0); tLCPremSchema.setPrem(0);
		 * tLCPremSchema.setDutyCode("601001");
		 * tLCPremSchema.setPayPlanCode("601101");
		 * tLCPremSet.add(tLCPremSchema);
		 * 
		 * LCPremSchema tLCPremSchema2 = new LCPremSchema();
		 * tLCPremSchema2.setRate("1"); tLCPremSchema2.setStandPrem(1000);
		 * tLCPremSchema2.setPrem(1000); tLCPremSchema2.setDutyCode("601001");
		 * tLCPremSchema2.setPayPlanCode("601102");
		 * tLCPremSet.add(tLCPremSchema2); tVData.add(tLCPremSet); //
		 */

		/** 传递变量 */
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("countName", "hzm");
		// tTransferData.setNameAndValue("GetDutyKind","1");
		tTransferData.setNameAndValue("samePersonFlag", "1");

		tVData.addElement(tGlobalInput);
		tVData.addElement(tLCContSchema);
		// tVData.addElement(tLCPolSchema);
		// tVData.addElement(tLCAppntSchema);
		// LCAppntGrpSchema tLCAppntGrpSchema= new LCAppntGrpSchema();
		tVData.addElement(tLCGrpAppntSchema);
		tVData.addElement(tLCInsuredSchema);
		tVData.addElement(tLCBnfSet);
		// tVData.addElement(tLCCustomerImpartSet);
		// tVData.addElement(tLCSpecSet);
		tVData.addElement(tLCDutySchema);
		// LCDutySet tLCDutySet= new LCDutySet();
		// tVData.addElement(tLCDutySet) ;//--用于责任可以选择--用于团体商业补充保险
		tVData.addElement(tTransferData);
		// 数据传输
		tProposalUI = new ProposalUI();
		// if (!tProposalUI.submitData(tVData,"INSERT||PROPOSAL"))
		// if (!tProposalUI.submitData(tVData,"UPDATE||PROPOSAL"))
		if (!tProposalUI.submitData(tVData, "DELETE||PROPOSAL")) {
			logger.debug(tProposalUI.mErrors.getFirstError());
		}

	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		// this.mOperate =cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		// if (!getInputData(cInputData))
		// return false;

		// 进行业务处理
		// if (!dealData())
		// return false;

		// 准备往后台的数据
		// if (!prepareOutputData())
		// return false;

		ProposalBL tProposalBL = new ProposalBL();
		if (!tProposalBL.submitData(cInputData, cOperate)) {
			this.mErrors.copyAllErrors(tProposalBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "ProposalUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mInputData = null;
		mResult = tProposalBL.getResult();

		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(mGlobalInput);
			mInputData.add(mTransferData);
			mInputData.add(mLCPolSchema);
			mInputData.add(mLCAppntIndSchema);
			mInputData.add(mLCAppntGrpSchema);
			mInputData.add(mLCInsuredSet);
			mInputData.add(mLCBnfSet);
			mInputData.add(mLCCustomerImpartSet);
			mInputData.add(mLCSpecSet);
			mInputData.add(mLCDutySchema);

			if (this.mNeedDuty) {
				mInputData.add(mLCDutySet1);
			}
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ProposalBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行UI逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		boolean tReturn = false;
		// 此处增加一些校验代码
		tReturn = true;
		return tReturn;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		if (mOperate.equals("DELETE||PROPOSAL")) {
			// 全局变量
			mGlobalInput.setSchema((GlobalInput) cInputData
					.getObjectByObjectName("GlobalInput", 0));
			// 保单
			mLCPolSchema.setSchema((LCPolSchema) cInputData
					.getObjectByObjectName("LCPolSchema", 0));
		} else {
			// 全局变量
			mGlobalInput.setSchema((GlobalInput) cInputData
					.getObjectByObjectName("GlobalInput", 0));
			// 前台向后台传递的数据容器：放置一些不能通过Schema传递的变量
			mTransferData = (TransferData) cInputData.getObjectByObjectName(
					"TransferData", 0);
			// 保单
			mLCPolSchema.setSchema((LCPolSchema) cInputData
					.getObjectByObjectName("LCPolSchema", 0));
			// 投保人
			mLCAppntIndSchema.setSchema((LCAppntIndSchema) cInputData
					.getObjectByObjectName("LCAppntIndSchema", 0));
			mLCAppntGrpSchema.setSchema((LCAppntGrpSchema) cInputData
					.getObjectByObjectName("LCAppntGrpSchema", 0));
			// 被保人
			mLCInsuredSet.set((LCInsuredSet) cInputData.getObjectByObjectName(
					"LCInsuredSet", 0));
			// 受益人
			mLCBnfSet.set((LCBnfSet) cInputData.getObjectByObjectName(
					"LCBnfSet", 0));
			// 告知信息
			mLCCustomerImpartSet.set((LCCustomerImpartSet) cInputData
					.getObjectByObjectName("LCCustomerImpartSet", 0));
			// 特别约定
			mLCSpecSet.set((LCSpecSet) cInputData.getObjectByObjectName(
					"LCSpecSet", 0));

			// 一般责任信息
			if ((LCDutySchema) cInputData.getObjectByObjectName("LCDutySchema",
					0) != null) {
				mLCDutySchema.setSchema((LCDutySchema) cInputData
						.getObjectByObjectName("LCDutySchema", 0));
			}

			// 可选责任

			LCDutySet tLCDutySet = (LCDutySet) cInputData
					.getObjectByObjectName("LCDutySet", 0);

			if (tLCDutySet == null) {
				mNeedDuty = false;
			} else {
				mNeedDuty = true;
			}
			mLCDutySet1.set(tLCDutySet);
			if (mTransferData == null) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "ProposalUI";
				tError.functionName = "getInputData";
				tError.errorMessage = "在接受数据时没有得到TransferData的数据!";
				this.mErrors.addOneError(tError);
				return false;
			}
			if (mLCPolSchema == null || mLCAppntIndSchema == null
					|| mLCInsuredSet == null || mLCBnfSet == null
					|| mLCCustomerImpartSet == null || mLCSpecSet == null) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "ProposalBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "在投保数据接受时没有得到足够的数据，请您确认有：保单,投保人,被保人,受益人,告知信息,特别约定的信息!";
				this.mErrors.addOneError(tError);
				return false;
			}
		} // end of if
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

}
