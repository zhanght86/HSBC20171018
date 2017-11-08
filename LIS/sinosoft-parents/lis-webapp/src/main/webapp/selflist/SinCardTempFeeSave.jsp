 <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：SinCardTempFeeSave.jsp
//程序功能：
//创建日期：2008-3-10
//创建人  ：zy
//更新记录：  更新人    更新日期     更新原因/内容

%>

<SCRIPT src="SinCardTempFeeInput.js">
	alert("SinCardTempFeeInput");
</SCRIPT>
<%@page import="java.lang.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.list.*"%>
<%
  loggerDebug("SinCardTempFeeSave"," save------"); 
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");
  if(tG==null)
  {
    loggerDebug("SinCardTempFeeSave","页面失效,请重新登陆");   
    FlagStr = "Fail";        
    Content = "页面失效,请重新登陆";  
  }
  
  else
	{
	
			String Operator  = tG.Operator ;  //保存登陆管理员账号
		 	 String ManageCom = tG.ComCode  ; //保存登陆区站,ManageCom=内存中登陆区站代码
		     // 暂收表信息记录
			String tStartNo        = request.getParameter("fmStartNo");                     //卡单起始号
			String tEndNo          = request.getParameter("fmEndNo");                      //卡单终止号
			String tPayDate    = request.getParameterValues("TempToGrid3")[0]; 	          //交费日期
			String tPayMoney      = request.getParameterValues("TempToGrid4")[0];         //交费金额        
		  String tEnterAccDate = request.getParameterValues("TempToGrid5")[0];
		  String tManageCom    = request.getParameterValues("TempToGrid6")[0]; 				         //交费机构
			String tRiskCode      = request.getParameterValues("TempToGrid7")[0];          //险种编码
		  String tAgentGroup   = request.getParameterValues("TempToGrid8")[0];
		  String tAgentCode    = request.getParameterValues("TempToGrid9")[0];
	    String tPayIntv      = request.getParameterValues("TempToGrid12")[0]; //缴费方式
		 	String tPayYears     = request.getParameterValues("TempToGrid13")[0];	
	   
		  // 暂收分类表记录
		           
			String tCPayMode       = request.getParameterValues("TempClassToGrid2")[0];
		  String tCPayDate       = request.getParameterValues("TempClassToGrid3")[0];
		  String tCPayMoney      = request.getParameterValues("TempClassToGrid4")[0];
		  String tCEnterAccDate  = request.getParameterValues("TempClassToGrid5")[0];
		  String tCManageCom     = request.getParameterValues("TempClassToGrid6")[0];
		
	  	String tCChequeNo      = request.getParameterValues("TempClassToGrid7")[0];
		  String tChequeDate     = request.getParameterValues("TempClassToGrid8")[0];
		  String tBankcode       = request.getParameterValues("TempClassToGrid9")[0];
		  String tBankaccno      = request.getParameterValues("TempClassToGrid10")[0];
		  String tAccname        = request.getParameterValues("TempClassToGrid11")[0];
		  String tIDType         = request.getParameterValues("TempClassToGrid18")[0];
		  String tIDNo           = request.getParameterValues("TempClassToGrid19")[0];

      //传递前台参数
			TransferData tTransferData =new TransferData();
			tTransferData.setNameAndValue("tStartNo",tStartNo);
			tTransferData.setNameAndValue("tEndNo",tEndNo);
			tTransferData.setNameAndValue("tPayDate",tPayDate);
			tTransferData.setNameAndValue("tPayMoney",tPayMoney);
			tTransferData.setNameAndValue("tEnterAccDate",tEnterAccDate);
			tTransferData.setNameAndValue("tManageCom",tManageCom);
			tTransferData.setNameAndValue("tRiskCode",tRiskCode);
			tTransferData.setNameAndValue("tAgentGroup",tAgentGroup);	
			tTransferData.setNameAndValue("tAgentCode",tAgentCode);		
			tTransferData.setNameAndValue("tPayIntv",tPayIntv);
			tTransferData.setNameAndValue("tPayYears",tPayYears);	
			
			tTransferData.setNameAndValue("tCPayMode",tCPayMode);
			tTransferData.setNameAndValue("tCPayDate",tCPayDate);
			tTransferData.setNameAndValue("tCPayMoney",tCPayMoney);
			tTransferData.setNameAndValue("tCEnterAccDate",tCEnterAccDate);
			tTransferData.setNameAndValue("tCManageCom",tCManageCom);		
			tTransferData.setNameAndValue("tCChequeNo",tCChequeNo);
			tTransferData.setNameAndValue("tChequeDate",tChequeDate);			
			tTransferData.setNameAndValue("tBankcode",tBankcode);
			tTransferData.setNameAndValue("tBankaccno",tBankaccno);
			tTransferData.setNameAndValue("tAccname",tAccname);
			tTransferData.setNameAndValue("tIDType",tIDType);
			tTransferData.setNameAndValue("tIDNo",tIDNo);

			
		  // 准备传输数据 VData
		  VData tVData = new VData();
			tVData.add(tTransferData);
			tVData.add(tG);
		    
		        // 数据传输
		  SinCardTempFeeUI tSinCardTempFeeUI   = new SinCardTempFeeUI();
		  try
		  {
			  loggerDebug("SinCardTempFeeSave"," 开始提交SinCardTempFeeUI处理了------");      
			  
				if(!tSinCardTempFeeUI.submitData(tVData,"INSERT"))
				{
				 
				    Content = " 交费保存失败，原因:" + tSinCardTempFeeUI.mErrors.getFirstError();
			     	FlagStr = "Fail"; 
				}   
			
				if (FlagStr == "Fail")
				{
				    tError = tSinCardTempFeeUI.mErrors;
				    if (!tError.needDealError())
				    {
				    	Content = "交费保存成功! ";
				    	FlagStr = "Succ";
				    }
				    else
				    {
				    	FlagStr = "Fail";
				    }
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				Content = Content.trim()+"提示：异常终止!";
			}
		
	} //end of else

%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

