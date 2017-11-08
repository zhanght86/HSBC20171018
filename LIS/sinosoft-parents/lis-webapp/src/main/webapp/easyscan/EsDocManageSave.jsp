<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：EsDocManageSave.jsp
//程序功能：
//创建日期：2004-06-02
//创建人  ：LiuQiang
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.easyscan.*"%>
<%@page import="com.sinosoft.service.*" %>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
GlobalInput tG = new GlobalInput(); 
tG=(GlobalInput)session.getValue("GI");
TransferData tTransferData=new TransferData();
tTransferData.setNameAndValue("DocID",request.getParameter("DOC_ID"));
tTransferData.setNameAndValue("PrtNoA",request.getParameter("DOC_CODE")); //变化后
tTransferData.setNameAndValue("PrtNoB",request.getParameter("PrtNo")); //变化前	 
tTransferData.setNameAndValue("Reason",request.getParameter("DelReason")); //删除单证原因
tTransferData.setNameAndValue("ReasonCode",request.getParameter("DelReasonCode")); //删除单证原因代码
tTransferData.setNameAndValue("serialno",request.getParameter("serialno"));
loggerDebug("EsDocManageSave","--DocID:"+request.getParameter("DOC_ID"));
loggerDebug("EsDocManageSave","--PrtNoA:"+request.getParameter("DOC_CODE"));
loggerDebug("EsDocManageSave","--PrtNoB:"+request.getParameter("PrtNo"));
loggerDebug("EsDocManageSave","--Reason:"+request.getParameter("DelReason"));
//接收信息，并作校验处理。
  //EsDocManageUI tManageUI   = new EsDocManageUI();
String busiName="EsDocManageUI";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

  //输入参数
  ES_DOC_MAINSchema tES_DOC_MAINSchema   = new ES_DOC_MAINSchema();
  ES_DOC_PAGESSet tES_DOC_PAGESSet   = new ES_DOC_PAGESSet();

  //输出参数
  CErrors tError = null;
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String transact = "";

  //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
  transact = request.getParameter("fmtransact");
  loggerDebug("EsDocManageSave","--EsDocManageSave.jsp--transact:"+transact);
  
  if(transact.equals("UPDATE||MAIN"))
  {	  
	  tES_DOC_MAINSchema.setDocID(request.getParameter("DOC_ID"));
	  tES_DOC_MAINSchema.setDocCode(request.getParameter("DOC_CODE"));
	  tES_DOC_MAINSchema.setNumPages(request.getParameter("NUM_PAGES"));
	  tES_DOC_MAINSchema.setMakeDate(request.getParameter("INPUT_DATE"));
	  tES_DOC_MAINSchema.setMakeTime(request.getParameter("Input_Time"));
	  tES_DOC_MAINSchema.setScanOperator(request.getParameter("ScanOperator"));
	  tES_DOC_MAINSchema.setManageCom(request.getParameter("ManageCom"));
	  tES_DOC_MAINSchema.setInputState(request.getParameter("InputState"));
	  tES_DOC_MAINSchema.setOperator(request.getParameter("Operator"));
	  tES_DOC_MAINSchema.setInputStartDate(request.getParameter("InputStartDate"));
	  tES_DOC_MAINSchema.setInputStartTime(request.getParameter("InputStartTime"));
	  tES_DOC_MAINSchema.setInputEndDate(request.getParameter("InputEndDate"));
	  tES_DOC_MAINSchema.setInputEndTime(request.getParameter("InputEndTime"));
	  tES_DOC_MAINSchema.setDocFlag(request.getParameter("DOC_FLAGE"));
	  tES_DOC_MAINSchema.setBussType(request.getParameter("BussType"));
	  tES_DOC_MAINSchema.setScanNo(request.getParameter("ScanNo"));
	  //tES_DOC_MAINSchema.setDOC_EX_FLAG(request.getParameter("Doc_Ex_Flag"));
	  tES_DOC_MAINSchema.setDocRemark(request.getParameter("DOC_REMARK"));
	  //tES_DOC_MAINSchema.setSubType(request.getParameter("SubType")); //2009-2-10 ln add --修改单证类型
  } 
  
  else if(transact.equals("UPDATE||PAGES") || transact.equals("DELETE||PAGES"))
  {
	  String tGridNo[] = request.getParameterValues("CodeGridNo");  //得到序号列的所有值
	  String tChk[] = request.getParameterValues("InpCodeGridChk");    //参数格式="MulLine对象名+Chk"
	  String tGrid1  [] = request.getParameterValues("CodeGrid1");  //得到第1列的所有值
	  String tGrid2  [] = request.getParameterValues("CodeGrid2");  //得到第2列的所有值
	  String tGrid3  [] = request.getParameterValues("CodeGrid3");  //得到第2列的所有值
	  String tGrid4  [] = request.getParameterValues("CodeGrid4");  //得到第2列的所有值
	  String tGrid5  [] = request.getParameterValues("CodeGrid5");  //得到第2列的所有值
	  String tGrid6  [] = request.getParameterValues("CodeGrid6");  //得到第2列的所有值
	  String tGrid7  [] = request.getParameterValues("CodeGrid7");  //得到第2列的所有值
	  String tGrid8  [] = request.getParameterValues("CodeGrid8");  //得到第2列的所有值
	  String tGrid9  [] = request.getParameterValues("CodeGrid9");  //得到第2列的所有值
	  String tGrid10 [] = request.getParameterValues("CodeGrid10"); //得到第2列的所有值
	  String tGrid11 [] = request.getParameterValues("CodeGrid11"); //得到第2列的所有值
	  //String tGrid12 [] = request.getParameterValues("CodeGrid12"); //得到第2列的所有值
	  String tGrid13 [] = request.getParameterValues("CodeGrid13");
	  
	  int count = tChk.length; //得到接受到的记录数
	  
	  loggerDebug("EsDocManageSave","--EsDocManageSave.jsp--20:" + count + ":" + tGridNo.length);  
	  
	  for(int index=0; index< count; index++)
	  {
	    if(tChk[index].equals("1"))
	    {
	    //选中的行
	    ES_DOC_PAGESSchema tPageSchema = new ES_DOC_PAGESSchema();
	    tPageSchema.setPageID(tGrid1[index]);
	    tPageSchema.setDocID(tGrid2[index]);
	    tPageSchema.setPageCode(tGrid3[index]);
	    tPageSchema.setHostName(tGrid4[index]);
	    tPageSchema.setPageName(tGrid5[index]);
	    tPageSchema.setPageFlag(tGrid6[index]);
	    tPageSchema.setPicPathFTP(tGrid7[index]);
	    tPageSchema.setPicPath(tGrid8[index]);
	    tPageSchema.setOperator(tGrid9[index]);
	    tPageSchema.setMakeDate(tGrid10[index]);
	    tPageSchema.setMakeTime(tGrid11[index]);
	    tPageSchema.setScanNo(tGrid13[index]);
	    //tPageSchema.setDoc_Type(tGrid12[index]);
	    tES_DOC_PAGESSet.add(tPageSchema);
	    }
	  }
  } 
  else if(transact.equals("DELETE||DOCID") || transact.equals("UPDATE||DOCID"))
  {	  
	  String tGridNo[] = request.getParameterValues("CodeGridNo");  //得到序号列的所有值
	  String tChk[] = request.getParameterValues("InpCodeGridChk");    //参数格式="MulLine对象名+Chk"
	  String tGrid2  [] = request.getParameterValues("CodeGrid2");  //得到第2列的所有值
	  String tGrid12 [] = request.getParameterValues("CodeGrid12"); //得到第2列的所有值
	  
	  int count = tChk.length; //得到接受到的记录数
	  
	  loggerDebug("EsDocManageSave","--EsDocManageSave.jsp--20:" + count + ":" + tGridNo.length);  
	  
	  for(int index=0; index< count; index++)
	  {
	    if(tChk[index].equals("1"))
	    {
	    //选中的行
		    tES_DOC_MAINSchema.setDocID(tGrid2[index]);
		  	tES_DOC_MAINSchema.setSubType(tGrid12[index]); //单证类型
		  	break;
	    }
	  }
  }
  
  try
  {
    // 准备传输数据 VData
    VData tVData = new VData();
    tVData.add(tES_DOC_MAINSchema);
    tVData.add(tES_DOC_PAGESSet);
    tVData.add(tTransferData);
    tVData.add(tG);

    loggerDebug("EsDocManageSave","--EsDocManageSave.jsp--before submitData");
    //tManageUI.submitData(tVData,transact);
    if(!tBusinessDelegate.submitData(tVData,transact,busiName))
	{    
	    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
	    { 
			Content = "处理失败，原因是:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
		else
		{
			Content = "处理失败";
			FlagStr = "Fail";				
		}
	}else{
			Content = "处理成功";
			FlagStr = "Success";	
	}
    
    loggerDebug("EsDocManageSave","--EsDocManageSave.jsp--after  submitData");
  }
  catch(Exception ex)
  {
    Content = "保存失败，原因是:" + ex.toString();
    FlagStr = "Fail";
  }

//如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr=="")
  {
    //tError = tManageUI.mErrors;
    tError = tBusinessDelegate.getCErrors();
    if (!tError.needDealError())
    {
      Content = " 保存成功! ";
      FlagStr = "Success";
    }
    else
    {
      Content = " 保存失败，原因是:" + tError.getFirstError();
      FlagStr = "Fail";
    }
  }

  //添加各种预处理

%>
<html>
<script language="javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
