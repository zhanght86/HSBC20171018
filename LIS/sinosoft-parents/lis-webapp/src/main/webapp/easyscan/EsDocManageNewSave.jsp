<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：EsDocManageSave.jsp
//程序功能：
//创建日期：2009-09-09
//创建人  yanglh
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.lis.easyscan.*"%>
  <%@page import="java.util.HashSet"%>

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
	loggerDebug("EsDocManageNewSave","--DocID:"+request.getParameter("DOC_ID"));
	loggerDebug("EsDocManageNewSave","--PrtNoA:"+request.getParameter("DOC_CODE"));
	loggerDebug("EsDocManageNewSave","--PrtNoB:"+request.getParameter("PrtNo"));
	loggerDebug("EsDocManageNewSave","--Reason:"+request.getParameter("DelReason"));
	//接收信息，并作校验处理。
  EsDocManageUI tManageUI   = new EsDocManageUI();

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
  loggerDebug("EsDocManageNewSave","--EsDocManageNewSave.jsp--transact:"+transact);
  
  if(transact.equals("UPDATE||PAGES") || transact.equals("DELETE||PAGES"))
  {
	  String tGridNo[] = request.getParameterValues("CodeGridNo");  //得到序号列的所有值
	  String tChk[] = request.getParameterValues("InpCodeGridChk");    //参数格式="MulLine对象名+Chk"
	  String tGrid1  [] = request.getParameterValues("CodeGrid1");  
	  String tGrid2  [] = request.getParameterValues("CodeGrid2");  
	  String tGrid3  [] = request.getParameterValues("CodeGrid3");  
	  String tGrid4  [] = request.getParameterValues("CodeGrid4");  
	  String tGrid5  [] = request.getParameterValues("CodeGrid5");  
	  String tGrid6  [] = request.getParameterValues("CodeGrid6");  
	  String tGrid7  [] = request.getParameterValues("CodeGrid7");  
	  String tGrid8  [] = request.getParameterValues("CodeGrid8");  
	  String tGrid9  [] = request.getParameterValues("CodeGrid9");  
	  String tGrid10 [] = request.getParameterValues("CodeGrid10"); 
	  String tGrid11 [] = request.getParameterValues("CodeGrid11"); 
	  //String tGrid12 [] = request.getParameterValues("CodeGrid12"); 
	  String tGrid13 [] = request.getParameterValues("CodeGrid13");
	  
	  int count = tChk.length; //得到接受到的记录数
	  loggerDebug("EsDocManageNewSave","--EsDocManageNewSave.jsp--20:" + count + ":" + tGridNo.length);  
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
		    
		    loggerDebug("EsDocManageNewSave","pageCOde="+tGrid3[index]);
	
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
	  
	  loggerDebug("EsDocManageNewSave","--EsDocManageSave.jsp--20:" + count + ":" + tGridNo.length);  
	  
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
	
	    loggerDebug("EsDocManageNewSave","--EsDocManageSave.jsp--before submitData");
	    tManageUI.submitData(tVData,transact);
	    loggerDebug("EsDocManageNewSave","--EsDocManageSave.jsp--after  submitData");
  }
  catch(Exception ex)
  {
    Content = "保存失败，原因是:" + ex.toString();
    FlagStr = "Fail";
  }

//如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr=="")
  {
    tError = tManageUI.mErrors;
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
