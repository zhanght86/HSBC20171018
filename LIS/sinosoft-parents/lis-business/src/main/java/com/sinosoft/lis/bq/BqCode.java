package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

/**
 * <p>
 * Title: 业务系统
 * </p>
 * <p>
 * Description: 保全-静态编码统计类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author zhangtao
 * @version 1.0
 */

public class BqCode {
private static Logger logger = Logger.getLogger(BqCode.class);

	// *******************************补费子业务类型***** ADD by zhangtao *********
	// 补缴保费
	public static final String Pay_Prem = "P001";
	// 补缴被保险人健康加费
	public static final String Pay_InsurAddPremHealth = "P002";
	// 补缴被保险人职业加费
	public static final String Pay_InsurAddPremOccupation = "P003";
	// 补缴投保人健康加费
	public static final String Pay_AppntAddPremHealth = "P004";
	// 补缴投保人职业加费
	public static final String Pay_AppntAddPremOccupation = "P005";
	// 生存金补费
	public static final String Pay_GetDraw = "P006";
	// 补交保费利息
	public static final String Pay_PremInterest = "P007";
	// 贷款本金清偿
	public static final String Pay_LoanCorpus = "P008";
	// 贷款利息清偿
	public static final String Pay_LoanCorpusInterest = "P009";
	// 自垫本金清偿
	public static final String Pay_AutoPayPrem = "P010";
	// 自垫利息清偿
	public static final String Pay_AutoPayPremInterest = "P011";
	// 工本费
	public static final String Pay_WorkNoteFee = "P012";
	// 退保手续费
	public static final String Pay_TBFee = "P013";

	// 贷款扣除印花税 add by zhangtao 2006-06-19
	public static final String Pay_Revenue = "P014";
	// 补交保费利息--复效
	public static final String Pay_PremInterest_Re = "P015";
	// 加费补缴保费
	public static final String Pay_addPrem = "P016";
	// 加费补缴保费利息
	public static final String Pay_addPremInterest = "P017";
    //风险保费补费
    public static final String Pay_MangeFee = "P023";
    //为了使帐面冲平引入的调整金 Add by QianLy on 2007-11-6
    public static final String Pos_Adjust = "MPOS";

	// *******************************退费子业务类型****** ADD by zhangtao ********
	// 保费退费金额
	public static final String Get_Prem = "G001";
	// 健康加费退费金额
	public static final String Get_AddPremHealth = "G002";
	// 职业加费退费金额
	public static final String Get_AddPremOccupation = "G003";
	// 领取金额（适用于年金、满期金给付和利差返还）
	public static final String Get_GetDraw = "G004";
	// 累计红利保额应退金额
	public static final String Get_BonusCashValue = "G005";
	// 基本保额应退金额
	public static final String Get_BaseCashValue = "G006";
	// 终了红利||MS-包含累计生息红利、现金红利、抵交保费红利
	public static final String Get_FinaBonus = "G007";
	// 多收保费退还
	public static final String Get_MorePrem = "G008";
	// 保单质押贷款本金
	public static final String Get_LoanCorpus = "G009";
	// 保单质押贷款利息
	public static final String Get_LoanCorpusInterest = "G010";
	// 自垫本金
	public static final String Get_AutoPayPrem = "G011";
	// 自垫利息
	public static final String Get_AutoPayPremInterest = "G012";
	// 领取投资账户价值
	public static final String Get_InvestAccValue = "G013";
	// 领取账户价值时手续费
	public static final String Get_InvestAccFee = "G018";
	// 利差帐户余额
	public static final String Get_AccBala = "G014";
	// 已领取应扣除金额
	public static final String Get_HasGet = "G015";
	// 红利补派
	public static final String Get_FillBonus = "G016";
	// 临时红利
	public static final String Get_TempBonus = "G017";
	
	//保全清算坏账
	public static final String Get_BDMoney = "G019";
	
	 //投连趸缴帐户部分领取手续费
	public static final String Get_TLBQARFee = "G020";
	
	//风险管理费
    public static final String Get_InsuAccRiskFee = "G021";
    
  //账户初始费用退费
    public static final String Get_InsuAccInFee = "G021";
    //风险保费退费
    public static final String Get_MangeFee = "G023";

	// *******************************万能险相关常量******* ADD by zhangtao *******
	public static String Acc_YearBalaDate = "12-31"; // 年终结算日期
	public static double Acc_EnsuredRate = 0.025; // 保证利率
	public static String Acc_EnsuredRateIntv = "Y"; // 保证利率间隔
	public static double Acc_ZTRate = 0.03; // 万能险退保手续费比例

	// *******************************个险保全相关常量***** ADD by zhangtao *******
	public static int PEdor_PayOverDueDays = 22; // 保全交费逾期终止天数
	public static int PEdor_AppOverDueDays = 15; // 保全受理逾期终止天数
	public static String LJ_OtherNoType = "10"; // 财务总表保全受理号OtherNoType值
	public static double FM_ManageFeeLRate = 0.1; // 缴费期限变更 长期变短期按10%收取管理费
	public static double FM_ManageFeeSRate = 0.05; // 缴费期限变更 期交变趸交按5%收取管理费
	
	//add by jiaqiangli 2008-10-16 方便程序的可读性与可移植性
	//保全失效中止批处理原因代码规则
	//非垫交保单应交未交失效
	public static final String BQ_InvalidationStateReason_Nomal = "01";
	//垫交保单宽限期内失效（即不够垫宽限期67天）
	public static final String BQ_InvalidationStateReason_InGracePayPrem = "02";
	//垫交保单宽限期外失效（即够垫宽限期67天但不够整期）整期垫交后续的失效由自垫批处理控制 
	public static final String BQ_InvalidationStateReason_OutGracePayPrem = "03";
	//借款失效 
	public static final String BQ_InvalidationStateReason_Loan = "04";
	
	//折扣类型
	public static final String Dis_Product="D_PR";
	public static final String Dis_Staff="D_ST";
	public static final String Dis_Campaign="D_CP";
	public static final String Dis_Special="D_SP";
	//add by jiaqiangli 2008-10-16 方便程序的可读性与可移植性
	

	public BqCode() {
	}
}
