/**
 * Copyright (c) 2008 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.ibrms.bom;
import org.apache.log4j.Logger;

import java.util.*;
import java.text.SimpleDateFormat;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.ibrms.BOMPreCheck;

/**
 * <p>ClassName: BOMSubPol </p>
 * <p>Description: BOM 类文件 转移条件</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: sinosoft </p>
 * @CreateDate：2012-04-19
 */
public class BOMConTr extends AbstractBOM {
	private static Logger logger = Logger.getLogger(BOMConTr.class);
	// @Field
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	private FDate fDate = new FDate();		// 处理日期

	private String value = "";
	/**任务轨迹ID**/
	   private String Missionid;
	/**活动ID**/
	   private String Activityid;
	/** 1复核状态 */
		private String ApproveFlag;
	/** 1核保状态 */
		private String UWFlag;
	/** 校验标记 */
		private String CheckFlag;
	/** 其它号码类型 */
		private String OtherNoType;
	/**是否存在体检录入的标志，0为不存在不需要发放 否则为1*/
	    private String SendPENoticeFlag = "0";
	/**产生抽检标记, 测试用*/
		private String NeedCheckFlag;
	/** 确认标志*/
		private String ConfirmFlag;
	/**发送生调通知书标志*/
	    private String SendReportNoticeFlag;
	/**发送打印核保通知书标志*/ 
	    private String UWSendFlag;	    
	/** 预付标记 */
		private String BudgetFlag ="";
	/**简易案件权限 理赔*/
	    private String SimpleFlag = "";
	/** 客户意见 */
		private String CustomerIdea ="";		
	/**审批是否通过 1 */
		private String Reject = ""; 
	/**不通过需要审核 0 1*/
	    private String AuditFlag ="";	    
	/**分保标志 1-分保0不分保*/
	    private String ReDisMark = ""; 
	/**发送问题件修改岗标志*/
	    private String ApproveModifyFlag = "";
	/**产品上市停售规则标记，如果不符合规则则置为1*/
	    private String ProductSaleFlag = "0";	    
	/** 处理类型 */
		private String DealType;
	/** 特约类型 */
		private String SpecType;
	/** 录单完成时间 */
		private String InputTime;	
	/**发送机构问题件标志*/
		private String QuesOrgFlag = "";
	/** 打印管理表**/
	    private String QueModFlag = "0";
		private String QueModFlag_UnPrint = "0";
		private String QueModFlag_Agent = "0";
	/** 初审标志位**/
		private boolean FirstTrialFlaog = false;
		private String  ReplyPENoticeFlag = "";
	/**上报标志*/
		private String UWUpReport;
	/**判断问题件修改完毕是否该扭转到下一结点标记*/
		private String OtherNoticeFlag = "";
	/**是否有问题件  0-没有 1-有$*/
	 private String mIssueFlag; 
	/**审计标志*/
	       private String  AuditingFlag = "";
	/**结束标志*/
	        private String FinishFlag = "";
	/**发送业务员通知书标志$*/
	        private String SendOperFlag = "";
	/**客户合并*/
	        private String customerUnion ="";
	/**是否有外部问题件 0-没有 1-有*/
		    private String OutIssueFlag;
	/**生成标志*/
	    private  String CreateFlag = "";
	/** 是否自动核保 */
		private String AutoUWFlag = "";
	/** 产品定义问题件*/
		/**问题件状态*/
	        private String ISSUESTATE;   
	        /**返回岗*/
	        private String backpost;
	        /**险种代码*/
	        private String riskcode;     
	/** 自动初次核保结果*/
	        private String PreUWFlag;
	public static Logger getLogger() {
		return logger;
	}
	public static void setLogger(Logger logger) {
		BOMConTr.logger = logger;
	}
	public String getApproveFlag() {
		return ApproveFlag;
	}
	public void setApproveFlag(String approveFlag) {
		ApproveFlag = approveFlag;
	}
	public String getApproveModifyFlag() {
		return ApproveModifyFlag;
	}
	public void setApproveModifyFlag(String approveModifyFlag) {
		ApproveModifyFlag = approveModifyFlag;
	}
	public String getAuditFlag() {
		return AuditFlag;
	}
	public void setAuditFlag(String auditFlag) {
		AuditFlag = auditFlag;
	}
	public String getAuditingFlag() {
		return AuditingFlag;
	}
	public void setAuditingFlag(String auditingFlag) {
		AuditingFlag = auditingFlag;
	}
	public String getAutoUWFlag() {
		return AutoUWFlag;
	}
	public void setAutoUWFlag(String autoUWFlag) {
		AutoUWFlag = autoUWFlag;
	}
	public String getBackpost() {
		return backpost;
	}
	public void setBackpost(String backpost) {
		this.backpost = backpost;
	}
	public String getBudgetFlag() {
		return BudgetFlag;
	}
	public void setBudgetFlag(String budgetFlag) {
		BudgetFlag = budgetFlag;
	}
	public String getCheckFlag() {
		return CheckFlag;
	}
	public void setCheckFlag(String checkFlag) {
		CheckFlag = checkFlag;
	}
	public String getConfirmFlag() {
		return ConfirmFlag;
	}
	public void setConfirmFlag(String confirmFlag) {
		ConfirmFlag = confirmFlag;
	}
	public String getCreateFlag() {
		return CreateFlag;
	}
	public void setCreateFlag(String createFlag) {
		CreateFlag = createFlag;
	}
	public String getCustomerIdea() {
		return CustomerIdea;
	}
	public void setCustomerIdea(String customerIdea) {
		CustomerIdea = customerIdea;
	}
	public String getCustomerUnion() {
		return customerUnion;
	}
	public void setCustomerUnion(String customerUnion) {
		this.customerUnion = customerUnion;
	}
	public String getDealType() {
		return DealType;
	}
	public void setDealType(String dealType) {
		DealType = dealType;
	}
	public FDate getFDate() {
		return fDate;
	}
	public void setFDate(FDate date) {
		fDate = date;
	}
	public String getFinishFlag() {
		return FinishFlag;
	}
	public void setFinishFlag(String finishFlag) {
		FinishFlag = finishFlag;
	}
	public boolean isFirstTrialFlaog() {
		return FirstTrialFlaog;
	}
	public void setFirstTrialFlaog(boolean firstTrialFlaog) {
		FirstTrialFlaog = firstTrialFlaog;
	}
	public String getInputTime() {
		return InputTime;
	}
	public void setInputTime(String inputTime) {
		InputTime = inputTime;
	}
	public String getISSUESTATE() {
		return ISSUESTATE;
	}
	public void setISSUESTATE(String issuestate) {
		ISSUESTATE = issuestate;
	}
	public CErrors getErrors() {
		return mErrors;
	}
	public void setErrors(CErrors errors) {
		mErrors = errors;
	}
	public String getIssueFlag() {
		return mIssueFlag;
	}
	public void setIssueFlag(String issueFlag) {
		mIssueFlag = issueFlag;
	}
	public String getOutIssueFlag() {
		return OutIssueFlag;
	}
	public void setOutIssueFlag(String outIssueFlag) {
		OutIssueFlag = outIssueFlag;
	}

	public String getReplyPENoticeFlag() {
		return ReplyPENoticeFlag;
	}
	public void setReplyPENoticeFlag(String replyPENoticeFlag) {
		ReplyPENoticeFlag = replyPENoticeFlag;
	}
	public String getNeedCheckFlag() {
		return NeedCheckFlag;
	}
	public void setNeedCheckFlag(String needCheckFlag) {
		NeedCheckFlag = needCheckFlag;
	}
	public String getOtherNoticeFlag() {
		return OtherNoticeFlag;
	}
	public void setOtherNoticeFlag(String otherNoticeFlag) {
		OtherNoticeFlag = otherNoticeFlag;
	}
	public String getOtherNoType() {
		return OtherNoType;
	}
	public void setOtherNoType(String otherNoType) {
		OtherNoType = otherNoType;
	}
	public String getPreUWFlag() {
		return PreUWFlag;
	}
	public void setPreUWFlag(String preUWFlag) {
		PreUWFlag = preUWFlag;
	}
	public String getProductSaleFlag() {
		return ProductSaleFlag;
	}
	public void setProductSaleFlag(String productSaleFlag) {
		ProductSaleFlag = productSaleFlag;
	}
	public String getQuesOrgFlag() {
		return QuesOrgFlag;
	}
	public void setQuesOrgFlag(String quesOrgFlag) {
		QuesOrgFlag = quesOrgFlag;
	}
	public String getReDisMark() {
		return ReDisMark;
	}
	public void setReDisMark(String reDisMark) {
		ReDisMark = reDisMark;
	}
	public String getReject() {
		return Reject;
	}
	public void setReject(String reject) {
		Reject = reject;
	}
	public String getRiskcode() {
		return riskcode;
	}
	public void setRiskcode(String riskcode) {
		this.riskcode = riskcode;
	}
	public String getSendOperFlag() {
		return SendOperFlag;
	}
	public void setSendOperFlag(String sendOperFlag) {
		SendOperFlag = sendOperFlag;
	}
	public String getSendPENoticeFlag() {
		return SendPENoticeFlag;
	}
	public void setSendPENoticeFlag(String sendPENoticeFlag) {
		SendPENoticeFlag = sendPENoticeFlag;
	}
	public String getSendReportNoticeFlag() {
		return SendReportNoticeFlag;
	}
	public void setSendReportNoticeFlag(String sendReportNoticeFlag) {
		SendReportNoticeFlag = sendReportNoticeFlag;
	}
	public String getSimpleFlag() {
		return SimpleFlag;
	}
	public void setSimpleFlag(String simpleFlag) {
		SimpleFlag = simpleFlag;
	}
	public String getSpecType() {
		return SpecType;
	}
	public void setSpecType(String specType) {
		SpecType = specType;
	}
	public String getUWFlag() {
		return UWFlag;
	}
	public void setUWFlag(String flag) {
		UWFlag = flag;
	}
	public String getUWSendFlag() {
		return UWSendFlag;
	}
	public void setUWSendFlag(String sendFlag) {
		UWSendFlag = sendFlag;
	}
	public String getUWUpReport() {
		return UWUpReport;
	}
	public void setUWUpReport(String upReport) {
		UWUpReport = upReport;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getQueModFlag() {
		return QueModFlag;
	}
	public void setQueModFlag(String queModFlag) {
		QueModFlag = queModFlag;
	}
	public String getQueModFlag_Agent() {
		return QueModFlag_Agent;
	}
	public void setQueModFlag_Agent(String queModFlag_Agent) {
		QueModFlag_Agent = queModFlag_Agent;
	}
	public String getQueModFlag_UnPrint() {
		return QueModFlag_UnPrint;
	}
	public void setQueModFlag_UnPrint(String queModFlag_UnPrint) {
		QueModFlag_UnPrint = queModFlag_UnPrint;
	}
	
	/**
	* 设置对应传入参数的String形式的字段值
	* @param: FCode String 需要赋值的对象
	* @param: FValue String 要赋的值
	* @return: boolean
	**/
	public boolean setV(String FCode ,String FValue)
	{

	  if( StrTool.cTrim( FCode ).equals( "" ))
	      return false;

	if("ApproveFlag".equals(FCode))
		{
		    setApproveFlag(FValue);
		}

	if("UWFlag".equals(FCode))
		{
			setUWFlag(FValue);
		}

	if("CheckFlag".equals(FCode))
		{
			setCheckFlag(FValue);
		}

	if("OtherNoType".equals(FCode))
		{
		    setOtherNoType(FValue);
		}

	if("SendPENoticeFlag".equals(FCode))
		{
		    setSendPENoticeFlag(FValue);
		}

	if("NeedCheckFlag".equals(FCode))
		{
		    setNeedCheckFlag(FValue);
		}

	if("ConfirmFlag".equals(FCode))
		{
		    setConfirmFlag(FValue);
		}

	if("SendReportNoticeFlag".equals(FCode))
		{
			setSendReportNoticeFlag(FValue);
		}

	if("UWSendFlag".equals(FCode))
		{
			setUWSendFlag(FValue);
		}

	if("BudgetFlag".equals(FCode))
		{
			setBudgetFlag(FValue);
		}

	if("SimpleFlag".equals(FCode))
		{
			setSimpleFlag(FValue);
		}

	if("CustomerIdea".equals(FCode))
		{
		setCustomerIdea(FValue);
		}

	if("Reject".equals(FCode))
		{
			setReject(FValue);
		}

	if("AuditFlag".equals(FCode))
		{
		    setAuditFlag(FValue);
		}

	if("SendOperFlag".equals(FCode))
		{
		    setSendOperFlag(FValue);
		}

	if("SendOperFlag".equals(FCode))
		{
		    setSendOperFlag(FValue);
		}

	if("ReDisMark".equals(FCode))
		{
		    setReDisMark(FValue);
		}

	if(" ApproveModifyFlag".equals(FCode))
		{
		    setApproveModifyFlag(FValue);
		}

	if("ProductSaleFlag".equals(FCode))
		{
		    setProductSaleFlag(FValue);
		}

	if("DealType".equals(FCode))
		{
		    setDealType(FValue);
		}

	if("SpecType".equals(FCode))
		{
		    setSpecType(FValue);
		}

	if("InputTime".equals(FCode))
		{
		    setInputTime(FValue);
		}

	if("QuesOrgFlag".equals(FCode))
		{
			setQuesOrgFlag(FValue);
		}

	if("QueModFlag".equals(FCode))
		{
			setQueModFlag(FValue);
		}

	if("QueModFlag_UnPrint".equals(FCode))
		{
		    setQueModFlag_UnPrint(FValue);
		}

	if("QueModFlag_Agent".equals(FCode))
		{
			setQueModFlag_Agent(FValue);
		}

	
	
	
	if("ReplyPENoticeFlag".equals(FCode))
	{
	    setReplyPENoticeFlag(FValue);
	}

if("UWUpReport".equals(FCode))
	{
	    setUWUpReport(FValue);
	}

if("OtherNoticeFlag".equals(FCode))
	{
	    setOtherNoticeFlag(FValue);
	}

if("IssueFlag".equals(FCode))
	{
	    setIssueFlag(FValue);
	}

if("AuditingFlag".equals(FCode))
	{
	    setAuditingFlag(FValue);
	}

if("FinishFlag".equals(FCode))
	{
	    setFinishFlag(FValue);
	}

if("SendOperFlag".equals(FCode))
	{
	    setSendOperFlag(FValue);
	}

if("QuesOrgFlag".equals(FCode))
	{
		setQuesOrgFlag(FValue);
	}

if("customerUnion".equals(FCode))
	{
		setCustomerUnion(FValue);
	}

if("OutIssueFlag".equals(FCode))
	{
	    setOutIssueFlag(FValue);
	}

if("CreateFlag".equals(FCode))
	{
		setCreateFlag(FValue);
	}

	if("AutoUWFlag".equals(FCode))
	{
	    setAutoUWFlag(FValue);
	}

if("ISSUESTATE".equals(FCode))
	{
		setISSUESTATE(FValue);
	}

if("backpost".equals(FCode))
	{
		setBackpost(FValue);
	}

if("riskcode".equals(FCode))
	{
	    setRiskcode(FValue);
	}

if("PreUWFlag".equals(FCode))
	{
		setCreateFlag(FValue);
	}
	    return true;
	}
	
	
	/**
	* 取得对应传入参数的String形式的字段值
	* @param: FCode String 希望取得的字段名
	* @return: String
	* 如果没有对应的字段，返回""
	* 如果字段值为空，返回"null"
	**/
	public String getV(String FCode)
	{
	 String strReturn = "";
	  if (FCode.equalsIgnoreCase("ApproveFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getApproveFlag()));
	  }
	  if (FCode.equalsIgnoreCase("UWFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getUWFlag()));
	  }
	  if (FCode.equalsIgnoreCase("CheckFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getCheckFlag()));
	  }
	  if (FCode.equalsIgnoreCase("OtherNoType"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOtherNoType()));
	  }
	  if (FCode.equalsIgnoreCase("SendPENoticeFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSendPENoticeFlag()));
	  }
	  if (FCode.equalsIgnoreCase("NeedCheckFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getNeedCheckFlag()));
	  }
	  if (FCode.equalsIgnoreCase("ConfirmFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getConfirmFlag()));
	  }
	  if (FCode.equalsIgnoreCase("SendReportNoticeFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSendReportNoticeFlag()));
	  }
	  if (FCode.equalsIgnoreCase("UWSendFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getUWSendFlag()));
	  }
	  if (FCode.equalsIgnoreCase("BudgetFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getBudgetFlag()));
	  }
	  if (FCode.equalsIgnoreCase("SimpleFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSimpleFlag()));
	  }
	  if (FCode.equalsIgnoreCase("CustomerIdea"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getCustomerIdea()));
	  }
	  if (FCode.equalsIgnoreCase("Reject"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getReject()));
	  }
	  if (FCode.equalsIgnoreCase("AuditFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAuditFlag()));
	  }
	  if (FCode.equalsIgnoreCase("SendOperFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSendOperFlag()));
	  }
	  if (FCode.equalsIgnoreCase("ReDisMark"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getReDisMark()));
	  }
	  if (FCode.equalsIgnoreCase("ProductSaleFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getProductSaleFlag()));
	  }
	  if (FCode.equalsIgnoreCase("DealType"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getDealType()));
	  }
	  if (FCode.equalsIgnoreCase("SpecType"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSpecType()));
	  }
	  if (FCode.equalsIgnoreCase("InputTime"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getInputTime()));
	  }
	  if (FCode.equalsIgnoreCase("QuesOrgFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getQuesOrgFlag()));
	  }
	  if (FCode.equalsIgnoreCase("SendOperFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSendOperFlag()));
	  }
	  if (FCode.equalsIgnoreCase("QueModFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getQueModFlag()));
	  }
	  if (FCode.equalsIgnoreCase("QueModFlag_UnPrint"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getQueModFlag_UnPrint()));
	  }
	  if (FCode.equalsIgnoreCase("QueModFlag_Agent"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getQueModFlag_Agent()));
	  }
	  if (FCode.equalsIgnoreCase("ReplyPENoticeFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getReplyPENoticeFlag()));
	  }
	  if (strReturn.equals(""))
	  {
	     strReturn = "null";
	  }
	  
	  
	  
	  if (FCode.equalsIgnoreCase("UWUpReport"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getUWUpReport()));
	  }
	  if (FCode.equalsIgnoreCase("OtherNoticeFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOtherNoticeFlag()));
	  }
	  if (FCode.equalsIgnoreCase("IssueFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getIssueFlag()));
	  }
	  if (FCode.equalsIgnoreCase("AuditingFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAuditingFlag()));
	  }
	  if (FCode.equalsIgnoreCase("FinishFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getFinishFlag()));
	  }
	  if (FCode.equalsIgnoreCase("SendOperFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSendOperFlag()));
	  }
	  if (FCode.equalsIgnoreCase("QuesOrgFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getQuesOrgFlag()));
	  }
	  if (FCode.equalsIgnoreCase("customerUnion"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getCustomerUnion()));
	  }
	  if (FCode.equalsIgnoreCase("OutIssueFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getOutIssueFlag()));
	  }
	  if (FCode.equalsIgnoreCase("CreateFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getCreateFlag()));
	  }
	  if (FCode.equalsIgnoreCase("AutoUWFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getAutoUWFlag()));
	  }
	  if (FCode.equalsIgnoreCase("ISSUESTATE"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getISSUESTATE()));
	  }
	  if (FCode.equalsIgnoreCase("backpost"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getBackpost()));
	  }
	  if (FCode.equalsIgnoreCase("riskcode"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getRiskcode()));
	  }
	  if (FCode.equalsIgnoreCase("SendOperFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getSendOperFlag()));
	  }
	  if (FCode.equalsIgnoreCase("PreUWFlag"))
	  {
	     strReturn = StrTool.GBKToUnicode(String.valueOf(getPreUWFlag()));
	  }

	  return strReturn;
	}
	public String getActivityid() {
		return Activityid;
	}
	public void setActivityid(String activityid) {
		Activityid = activityid;
	}
	public CErrors getMErrors() {
		return mErrors;
	}
	public void setMErrors(CErrors errors) {
		mErrors = errors;
	}
	public String getMissionid() {
		return Missionid;
	}
	public void setMissionid(String missionid) {
		Missionid = missionid;
	}
	public String getMIssueFlag() {
		return mIssueFlag;
	}
	public void setMIssueFlag(String issueFlag) {
		mIssueFlag = issueFlag;
	}
}
