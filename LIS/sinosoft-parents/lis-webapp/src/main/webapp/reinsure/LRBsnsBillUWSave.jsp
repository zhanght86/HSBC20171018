<%@include file="/i18n/language.jsp"%>
<%
//程序名称：LRBsnsBillSave.jsp
//程序功能：
//创建日期：2007-02-28
//创建人  ：zhangbin
//更新记录：  更新人: zhangbin 更新日期  2008-4-14   更新原因/内容
%>
<!--用户校验类-->
	<%@include file="../common/jsp/UsrCheck.jsp"%>
	<%@page import="com.sinosoft.utility.*"%>
	<%@page import="com.sinosoft.lis.schema.*"%>
	<%@page import="com.sinosoft.lis.vschema.*"%>
	<%@page import="com.sinosoft.lis.db.*"%>
	<%@page import="com.sinosoft.lis.vdb.*"%>
	<%@page import="com.sinosoft.lis.sys.*"%>
	<%@page import="com.sinosoft.lis.pubfun.*"%>
	<%@page import="com.sinosoft.lis.reinsure.*"%>
	<%@page contentType="text/html;charset=GBK" %>
	<%@page import="com.sinosoft.service.*" %>
<%	
	GlobalInput globalInput = new GlobalInput();
	CErrors tError = null;	
	globalInput.setSchema( (GlobalInput)session.getAttribute("GI") );
	
	String FlagStr = "";
	String Content = "";
	TransferData tTransferData = new TransferData();
	VData tVData = new VData();
	
	RIBsnsBillBatchSchema tRIBsnsBillBatchSchema = new RIBsnsBillBatchSchema();
	RIBsnsBillDetailsSet tRIBsnsBillDetailsSet = new RIBsnsBillDetailsSet();
	//LRBsnsBillUWUI mLRBsnsBillUWUI = new LRBsnsBillUWUI();
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();	
	//封装前台数据，传到后台	
	//=======================
	String mOperateType = request.getParameter("OperateType");


    
	//分保明细数据下载,向后台传 账单编码
	if("download".equals(mOperateType))
	{
		String tRadio[] = request.getParameterValues("InpBatchListGridSel");  
	    String tBillNo [] = request.getParameterValues("BatchListGrid1"); //账单编号
	    String mBillNo = "";
		for (int index=0; index< tRadio.length;index++)
		{
			if("1".equals(tRadio[index]))
			{			
				mBillNo = tBillNo[index]; //账单编号
				System.out.println("账单编号::"+mBillNo);
				tRIBsnsBillBatchSchema.setBillNo(mBillNo);
			}
		}		
		
	}
	//账单修改  ,传输账单明细数据
	if("billupdate".equals(mOperateType))
	{			
		String tRadio[] = request.getParameterValues("InpBatchListGridSel");  
	    String tBillNo [] = request.getParameterValues("BatchListGrid1"); //账单编号
	    String mBillNo = "";
		for (int index=0; index< tRadio.length;index++)
		{
			if("1".equals(tRadio[index]))
			{			
				mBillNo = tBillNo[index]; //账单编号
				System.out.println("账单编号::"+mBillNo);
				tRIBsnsBillBatchSchema.setBillNo(mBillNo);
			}
		}
	    
		String strNumber[] = request.getParameterValues("BillUpdateGridNo");
		String tDetails[]   = request.getParameterValues("BillUpdateGrid1");
		String tDetailsName[]   = request.getParameterValues("BillUpdateGrid2");
		String tDebSummoney[]   = request.getParameterValues("BillUpdateGrid3");
		String tCreSummoney[]   = request.getParameterValues("BillUpdateGrid4");
		
		RIBsnsBillDetailsSchema debRIBsnsBillDetailsSchema = null;
		RIBsnsBillDetailsSchema creRIBsnsBillDetailsSchema = null;
		for(int i = 0 ;i < strNumber.length ;i++)
		{
			debRIBsnsBillDetailsSchema = new RIBsnsBillDetailsSchema();
			debRIBsnsBillDetailsSchema.setBillNo(mBillNo);
			debRIBsnsBillDetailsSchema.setDebCre("D");
			debRIBsnsBillDetailsSchema.setDetails(tDetails[i]);
			debRIBsnsBillDetailsSchema.setDetailsName(tDetailsName[i]);
			debRIBsnsBillDetailsSchema.setSummoney(tDebSummoney[i]);
			debRIBsnsBillDetailsSchema.setMakeDate(PubFun.getCurrentDate());
			debRIBsnsBillDetailsSchema.setMakeTime(PubFun.getCurrentTime());
			debRIBsnsBillDetailsSchema.setModifyDate(PubFun.getCurrentDate());
			debRIBsnsBillDetailsSchema.setModifyTime(PubFun.getCurrentTime());
			debRIBsnsBillDetailsSchema.setOperator(globalInput.Operator);
			debRIBsnsBillDetailsSchema.setManageCom(globalInput.ComCode);
			
			creRIBsnsBillDetailsSchema = new RIBsnsBillDetailsSchema();
			creRIBsnsBillDetailsSchema.setBillNo(mBillNo);
			creRIBsnsBillDetailsSchema.setDebCre("C");
			creRIBsnsBillDetailsSchema.setDetails(tDetails[i]);
			creRIBsnsBillDetailsSchema.setDetailsName(tDetailsName[i]);
			creRIBsnsBillDetailsSchema.setSummoney(tCreSummoney[i]);
			creRIBsnsBillDetailsSchema.setMakeDate(PubFun.getCurrentDate());
			creRIBsnsBillDetailsSchema.setMakeTime(PubFun.getCurrentTime());
			creRIBsnsBillDetailsSchema.setModifyDate(PubFun.getCurrentDate());
			creRIBsnsBillDetailsSchema.setModifyTime(PubFun.getCurrentTime());
			creRIBsnsBillDetailsSchema.setOperator(globalInput.Operator);
			creRIBsnsBillDetailsSchema.setManageCom(globalInput.ComCode);
			
			if(debRIBsnsBillDetailsSchema.getSummoney()!=0)
			{
				tRIBsnsBillDetailsSet.add(debRIBsnsBillDetailsSchema);
			}
			if(creRIBsnsBillDetailsSchema.getSummoney()!=0)
			{
				tRIBsnsBillDetailsSet.add(creRIBsnsBillDetailsSchema);
			}
		}
		
	}	
	//账单审核结论保存
	if("conclusion".equals(mOperateType))
	{
	    String conclusion = request.getParameter("RIUWReport"); //结论编码
		String tRadio[] = request.getParameterValues("InpAuditBillListGridSel");  
	    String tBillNo [] = request.getParameterValues("AuditBillListGrid1"); //账单编号
	    
		for (int index=0; index< tRadio.length;index++)
		{
			if("1".equals(tRadio[index]))
			{			
				String mBillNo = tBillNo[index]; //账单编号
				System.out.println("账单编号::"+mBillNo);
				tRIBsnsBillBatchSchema.setBillNo(mBillNo);
				tRIBsnsBillBatchSchema.setState(conclusion);
			}
		}
	}
	//=====================	
	try
	{
	  	tTransferData.setNameAndValue("","");
	  	
	  	tVData.addElement(tTransferData);
	  	tVData.addElement(globalInput);
	    tVData.addElement(tRIBsnsBillBatchSchema);
	    tVData.addElement(tRIBsnsBillDetailsSet);
	    
	    tBusinessDelegate.submitData(tVData,mOperateType,"LRBsnsBillUWUI");
	}
	catch(Exception ex)
	{
		Content = ""+"操作失败，原因是:"+"" + ex.toString();
		FlagStr = "Fail";
	}
	
	//如果在Catch中发现异常，则不从错误类中提取错误信息
	if (FlagStr=="")
	{
	    tError = tBusinessDelegate.getCErrors();
	    if (!tError.needDealError())
	    {
			System.out.println("保存成功!");
			Content = ""+"操作成功!"+"";
	    	FlagStr = "Succ";
	    
	    }
	    else
	    {
	    	Content = ""+"操作失败，原因是:"+"" + tError.getFirstError();
	    	FlagStr = "Fail";
	    }
	}
	
%>
<html>
<script type="text/javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>"); 
</script>
</html>