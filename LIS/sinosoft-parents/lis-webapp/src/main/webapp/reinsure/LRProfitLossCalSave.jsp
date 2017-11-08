<%@include file="/i18n/language.jsp"%>
<%
//程序名称：LRProfitLossCalSave.jsp
//程序功能：
//创建日期：2007-03-14
//创建人  ：张斌
//更新记录：  更新人    更新日期     更新原因/内容
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
 	<%@page import="com.sinosoft.service.*" %>
	<%@page contentType="text/html;charset=GBK" %>

<%
	System.out.println("开始执行Save页面");
  
	GlobalInput globalInput = new GlobalInput();
	globalInput.setSchema( (GlobalInput)session.getAttribute("GI") );
	RIProLossValueSet mRIProLossValueSet = new RIProLossValueSet();
 	RIProLossResultSchema mRIProLossResultSchema = new RIProLossResultSchema();
	//LRProfitLossCalBL mLRProfitLossCalBL = new LRProfitLossCalBL();
  	BusinessDelegate blBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  
	CErrors tError = null;
	String mOperateType = request.getParameter("OperateType");
	System.out.println("操作的类型是"+mOperateType);
	String tRela  = "";
	String FlagStr = "";
	String Content = "";
	String mDescType = ""; //将操作标志的英文转换成汉字的形式
  
	System.out.println("开始进行获取数据的操作! ");
	
	//盈余佣金审核
	if("conclusion".equals(mOperateType))
	{
	    String tRadio[] = request.getParameterValues("InpLossUWListGridSel");  
	    String tSerialNo [] = request.getParameterValues("LossUWListGrid1"); //序号
	    String tReComCode[] = request.getParameterValues("LossUWListGrid4"); //再保公司
	    String tRIContNo[] = request.getParameterValues("LossUWListGrid2"); //再保合同	 
	    String tCalYear [] = request.getParameterValues("LossUWListGrid6"); //计算年度
	    
	    String tConclusion = request.getParameter("RILossUWReport");
	    
	    //参数格式=” Inp+MulLine对象名+Sel” 
		for (int index=0; index< tRadio.length;index++)
		{
			if("1".equals(tRadio[index]))
			{			
				String mSerialNo = tSerialNo[index]; //序列号
				String mRIContNo=tRIContNo[index]; //合同编码
				String mReComCode=tReComCode[index]; //分保公司
				String mCalYear = tCalYear[index]; //年度
				
				System.out.println("序列号::"+mSerialNo);
				System.out.println("合同编号::"+mRIContNo);
				System.out.println("分保公司::"+mReComCode); 
				System.out.println("年度::"+mCalYear);

				mRIProLossResultSchema.setSerialNo( mSerialNo);
				mRIProLossResultSchema.setCalYear(mCalYear);
				mRIProLossResultSchema.setReComCode(mReComCode);
				mRIProLossResultSchema.setRIContNo(mRIContNo);
				mRIProLossResultSchema.setStandbyString1(tConclusion);
			}
		}		
		mDescType = ""+"盈余佣金审核操作成功!"+"";
	}
	else
	{
		mRIProLossResultSchema.setSerialNo( request.getParameter("SerialNo"	));
		mRIProLossResultSchema.setCalYear(	request.getParameter("CalYear"	));
		mRIProLossResultSchema.setReComCode(request.getParameter("RIcomCode"));
		mRIProLossResultSchema.setRIContNo( request.getParameter("ContNo"		));
		mRIProLossResultSchema.setStartDate(request.getParameter("StartDate"));
		mRIProLossResultSchema.setEndDate(	request.getParameter("EndDate"	));		
	}

	String[] incomNum					=request.getParameterValues("IncomeGridNo");
	String[] incomFactorCode	=request.getParameterValues("IncomeGrid1");
	String[] incomFactorName	=request.getParameterValues("IncomeGrid2");
	String[] incomInOutType		=request.getParameterValues("IncomeGrid3");
	String[] incomFactorValue	=request.getParameterValues("IncomeGrid4");
  
	if(incomNum!=null)
	{
	  	RIProLossValueSchema tRIProLossValueSchema;
	  	for(int i=0;i<incomNum.length;i++)
	  	{
	  		tRIProLossValueSchema=new RIProLossValueSchema();
	  			
	  	    tRIProLossValueSchema.setReComCode(	request.getParameter("RIcomCode") );
	  	    tRIProLossValueSchema.setRIContNo(	request.getParameter("ContNo"));
	  	    tRIProLossValueSchema.setSerialNo(	""	);
	  	    tRIProLossValueSchema.setFactorCode(	incomFactorCode[i]);
	  	    tRIProLossValueSchema.setFactorName(	incomFactorName[i]	);
			System.out.println(" cccccc: "+incomFactorName[i]);
	  	    tRIProLossValueSchema.setInOutType(		"01"	);
	  	    tRIProLossValueSchema.setFactorValue(	incomFactorValue[i]	);
	  	    mRIProLossValueSet.add(tRIProLossValueSchema);
	  	}
	}	
	if(mOperateType.equals("CALCULATE"))
	{
		mDescType = ""+"纯溢手续费计算完成"+"";
	}
	if(mOperateType.equals("UPDATE"))
	{
		mDescType = ""+"修改再保合同信息"+"";
	}
	if(mOperateType.equals("DELETE"))
	{
		mDescType = ""+"删除再保合同"+"";
	}
	if(mOperateType.equals("QUERY"))
	{
		mDescType = ""+"查询再保合同"+"";
	}
	VData tVData = new VData(); 
	//try
	//{
		tVData.addElement(globalInput); 
		tVData.addElement(mRIProLossResultSchema); 
		tVData.addElement(mRIProLossValueSet); 
	//	mLRProfitLossCalBL.submitData(tVData,mOperateType); 
	//}
	//catch(Exception ex)
	//{
	//	Content = mDescType+"失败，原因是:" + ex.toString();
	//	FlagStr = "Fail";
	//}
  if(!blBusinessDelegate.submitData(tVData,mOperateType,"LRProfitLossCalBL"))
   {    
        if(blBusinessDelegate.getCErrors()!=null&&blBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
				   Content = mDescType+""+"失败，原因是:"+"" + blBusinessDelegate.getCErrors().getFirstError();
				   FlagStr = "Fail";
				}
				else
				{
				   Content = ""+"保存失败"+"";
				   FlagStr = "Fail";				
				}
   }
	//如果在Catch中发现异常，则不从错误类中提取错误信息
	double result = 0;
	if (FlagStr=="")
	{
	    tError = blBusinessDelegate.getCErrors();
	    if (!tError.needDealError())
	    {
	    	System.out.println("保存成功!");
	    	TransferData sTransferData = (TransferData)blBusinessDelegate.getResult().getObjectByObjectName("TransferData",0);
			result = (Double)sTransferData.getValueByName("Double");
	      	Content = mDescType+""+"成功，"+""+" "+"盈余佣金："+""+result;
	    	FlagStr = "Succ";
	    	if("conclusion".equals(mOperateType))
	    	{
	    		Content = mDescType;
	    	}
	    }
	    else
	    {
	    	Content = mDescType+" "+"失败，原因是:"+"" + tError.getFirstError();
	    	FlagStr = "Fail";
	    }
	}
%>
<html>
<script type="text/javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=result%>"); 
</script>
</html>