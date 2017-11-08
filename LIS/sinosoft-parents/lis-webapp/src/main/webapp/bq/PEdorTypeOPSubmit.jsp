<%
//程序名称：PEdorTypeRCSubmit.jsp
//程序功能：个人保全-万能险部分领取
//创建日期：2007-05－24
//创建人  ：ZengYG
%>
<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.bq.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.Date"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.service.*" %> 

<%
 //接收信息，并作校验处理。
  //输入参数
  //个人批改信息

	LPPersonSchema tLPPersonSchema   = new LPPersonSchema();
	LPEdorItemSchema tLPEdorItemSchema   = new LPEdorItemSchema();
 	PEdorOPDetailBL tPEdorOPDetailBL = new PEdorOPDetailBL();
//	 String busiNameOP="tPEdorOPDetailBL";
//	 BusinessDelegate tBusinessDelegateOP=BusinessDelegate.getBusinessDelegate();
//	EdorDetailUI tEdorDetailUI   = new EdorDetailUI();
	 String busiName="EdorDetailUI";
	 BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	CErrors tError = null;
	//后面要执行的动作：添加，修改

  String InsuAccBala = "";
  String CanGetMoney = "";
  String WorkNoteFee = "";
	String FlagStr = "";
	String Content = "";
	String transact = "";
	String Result="";
  try
	{
	   transact = request.getParameter("fmtransact");
	   GlobalInput tG = new GlobalInput();
	   tG = (GlobalInput)session.getValue("GI");

	   //个人保单批改信息
	   tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
	   tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
	   tLPEdorItemSchema.setStandbyFlag3(request.getParameter("Remark"));


	   TransferData tTransferData = new TransferData();
	   tTransferData.setNameAndValue("GetMoney",request.getParameter("GetMoney"));
	   tTransferData.setNameAndValue("InsuAccBala",request.getParameter("InsuAccBala"));

	   // 准备传输数据 VData
     VData tVData = new VData();
     tVData.addElement(tG);
	   if("OP||QUERY".equals(transact))
	   {
	       tVData.addElement(tLPEdorItemSchema);
	       tPEdorOPDetailBL.submitData(tVData,transact);
//	       tBusinessDelegateOP.submitData(tVData,transact,busiNameOP);
	       tError = tPEdorOPDetailBL.getErrors();
//	       tError = tBusinessDelegateOP.getCErrors();
	   }
	   else if("OP||MAIN".equals(transact))
     {
         tVData.addElement(tLPEdorItemSchema);
         tVData.addElement(tTransferData);
//         tEdorDetailUI.submitData(tVData,transact);
         tBusinessDelegate.submitData(tVData, transact ,busiName);
//         tError = tEdorDetailUI.getErrors();
         tError = tBusinessDelegate.getCErrors(); 
     }
  }
  catch(Exception ex)
  {
	   Content = "保存失败，原因是:" + ex.toString();
     FlagStr = "Fail";
  }
  //如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr=="")
  {
	    if (!tError.needDealError())
	    {
   			 Content = " 保存成功!<br>";
		     FlagStr = "Success";
		     if("OP||QUERY".equals(transact))
		     {

			        TransferData tTransferData = tPEdorOPDetailBL.getTransferResult();
				      if (tTransferData !=null)
				      {
				          InsuAccBala = (String)tTransferData.getValueByName("InsuAccBala");
				          CanGetMoney = (String)tTransferData.getValueByName("CanGetMoney");
				          WorkNoteFee = (String)tTransferData.getValueByName("WorkNoteFee");
				      }
		     }else
		     	{
		     	    TransferData tTransferData = tPEdorOPDetailBL.getTransferResult();
				      if (tTransferData !=null)
				      {
				          WorkNoteFee = (String)tTransferData.getValueByName("WorkNoteFee");
				      }
		     	}
	    }
	    else
	    {
   			Content = " 保存失败，原因是:" + tError.getFirstError();
		    FlagStr = "Fail";
	    }
    }
    //添加各种预处理

%>
<!-- XInYQ modified on 2005-12-22 : 提交之后再次查询 : BGN -->
<html>
    <script language="JavaScript">
        try
        {


            if('<%=transact%>' =="OP||QUERY")
            {   
            	  if('<%=FlagStr%>' =="Fail")
            	  {
            	  	  parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>', '<%=Result%>');
            	  }
            	  else
            	  {
            	  	  parent.fraInterface.afterCalData('<%=InsuAccBala%>', '<%=CanGetMoney%>', '<%=WorkNoteFee%>');
            	  }
            }
            else
        	  {
        	  	  parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>', '<%=Result%>');
                parent.fraInterface.queryBackFee();
                
            }
        }
        catch (ex)
        {
        }
    </script>
</html>
<!-- XInYQ modified on 2005-12-22 : 提交之后再次查询 : END -->
