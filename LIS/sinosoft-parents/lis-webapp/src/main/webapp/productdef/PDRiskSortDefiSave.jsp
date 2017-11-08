<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//程序名称：PDRiskSortDefiSave.jsp
//程序功能：险种分类定义
//创建日期：2009-3-12
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>   
<%
 //接收信息，并作校验处理。
 //输入参数
 

 //PDRiskSortDefiBL pDRiskSortDefiBL = new PDRiskSortDefiBL();
 
 CErrors tError = null;
 String tRela  = "";                
 String FlagStr = "";
 String Content = "";
 String operator = "";
 TransferData transferData = new TransferData();
 GlobalInput tG = new GlobalInput(); 
 tG=(GlobalInput)session.getAttribute("GI");
 
 //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
 operator = request.getParameter("operator");
 if("save".equals(operator)){
    String values[] = request.getParameterValues("Mulline9Grid1");
    String values2[] = request.getParameterValues("Mulline9Grid2");
    String values3[] = request.getParameterValues("Mulline9Grid3");
    java.util.ArrayList list = new java.util.ArrayList();
    java.util.ArrayList list2 = new java.util.ArrayList();
    java.util.ArrayList list3 = new java.util.ArrayList();
    for(int i = 0; i < values.length; i++)
    {
     	list.add(values[i]);
     	list2.add(values2[i]);
     	list3.add(values3[i]);
    }
	  transferData.setNameAndValue("list",list);
	  transferData.setNameAndValue("list2",list2);
	  transferData.setNameAndValue("list3",list3);
	}
	transferData.setNameAndValue("RiskCode",request.getParameter("RiskCode"));
	transferData.setNameAndValue("tableName",request.getParameter("tableName"));
 	try
 	{
  	// 准备传输数据 VData
  	VData tVData = new VData();

  	tVData.add(tG);
  	tVData.add(transferData);
  //	pDRiskSortDefiBL.submitData(tVData,operator);
  	String busiName="PDRiskSortDefiBL";
    
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    //String tDiscountCode = "";
    if (!tBusinessDelegate.submitData(tVData, operator,busiName)) {
  	  VData rVData = tBusinessDelegate.getResult();
      Content = " "+"处理失败，原因是:"+"" + tBusinessDelegate.getCErrors().getFirstError();
    	FlagStr = "Fail";
    }
    else {
      Content = " "+"处理成功!"+" ";
    	FlagStr = "Succ";
    }
   
   }
   catch(Exception ex)
   {
    Content = ""+"保存失败，原因是:"+"" + ex.toString();
    FlagStr = "Fail";
   }
  /*
 }
 catch(Exception ex)
 {
  Content = "保存失败，原因是:" + ex.toString();
  FlagStr = "Fail";
 }

 //如果在Catch中发现异常，则不从错误类中提取错误信息
 if (FlagStr=="")
 {
  tError = pDRiskSortDefiBL.mErrors;
  if (!tError.needDealError())
  {                          
   Content = " 保存成功! ";
   FlagStr = "Success";
   RiskState.setState(request.getParameter("RiskCode"),"契约业务控制->险种分类定义","1");
  }
  else                                                                           
  {
   Content = " 保存失败，原因是:" + tError.getFirstError();
   FlagStr = "Fail";
  }
 }*/

 //添加各种预处理
%>                      
<%=Content%>
<html>
<script type="text/javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

