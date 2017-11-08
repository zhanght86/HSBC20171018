<%@ page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：OtoFAccSave.jsp
//程序功能：
//创建日期：2008-01-02 
//创建人  ：zy
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@ include file="../common/jsp/UsrCheck.jsp"%>
<%@ page import="com.sinosoft.utility.*"%>
<%@ page import="com.sinosoft.lis.schema.*"%>
<%@ page import="com.sinosoft.lis.vschema.*"%>
<%@ page import="com.sinosoft.lis.otof.*"%>
<%@ page import="com.sinosoft.lis.pubfun.*"%>
<%
    loggerDebug("OtoFAccSave","\n\n---OtoFAccSave Start---");

    //输出参数
    CErrors tError = null;
    String FlagStr = "Fail";
    String Content = "";
    String strResult = "";

    GlobalInput tG = new GlobalInput();
    tG = (GlobalInput) session.getValue("GI");
    VData mResult = new VData();

    String mAction = request.getParameter("fmAction");
    String tManageCom = request.getParameter("ManageCom");
    String bDate = request.getParameter("bDate");
    String eDate = request.getParameter("eDate");
    String tRiskCode = request.getParameter("RiskCode");


    TransferData tTransferData = new TransferData();
    tTransferData.setNameAndValue("ManageCom", tManageCom);
    tTransferData.setNameAndValue("bDate", bDate);
    tTransferData.setNameAndValue("eDate", eDate);
    tTransferData.setNameAndValue("RiskCode", tRiskCode);




    // 准备传输数据 VData
    VData tVData = new VData();
    tVData.add(tTransferData);
    tVData.add(tG);

    // 数据传输
    if(mAction.equals("Print"))
    {
       loggerDebug("OtoFAccSave","\n\n---OtoFAccUI Start---");
	    OtoFAccUI tOtoFAccUI = new OtoFAccUI();
	   try
	   {
			if( !tOtoFAccUI.submitData(tVData, mAction) )
			  {
					 FlagStr = "Fail";
					 Content = "提数失败，原因是：" + tOtoFAccUI.mErrors.getFirstError();
			  }
			else
			  {
			    loggerDebug("OtoFAccSave","\n\n---OtoFAccUI end---");
				mResult = tOtoFAccUI.getResult();
				XmlExport txmlExport = new XmlExport();
				txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
				if (txmlExport==null)
				{
				  loggerDebug("OtoFAccSave","null");
				  Content="没有得到要显示的数据文件";
				}
			else
				{
						FlagStr = "True";
		    		Content = "提数成功！";
				}
				loggerDebug("OtoFAccSave","put session value");
				session.putValue("PrintStream", txmlExport.getInputStream());
			}
		}
		catch(Exception ex)
		{
			Content = "失败，原因是:" + ex.toString();
	    	FlagStr = "Fail";
	    }
    }
    
  if(mAction.equals("Certifi"))
  {  
      String tVouchNo = request.getParameter("VouchNo");
      Integer itemp = new Integer(tVouchNo) ;
      String tAccountDate = request.getParameter("AccountDate");
      tTransferData.setNameAndValue("tVouchNo", itemp);
      tTransferData.setNameAndValue("tAccountDate", tAccountDate);
      loggerDebug("OtoFAccSave","\n\n---OtoFAccUI Start---");
	    OtoFAccUI tOtoFAccUI = new OtoFAccUI();
		   try
		   {
				if( !tOtoFAccUI.submitData(tVData, mAction) )
				  {
						 FlagStr = "Fail";
						 Content = "提数失败，原因是：" + tOtoFAccUI.mErrors.getFirstError();
				  }
				else
					{
							FlagStr = "True";
			    		Content = "提数成功！";
					}			
			}
			catch(Exception ex)
			{
				Content = "失败，原因是:" + ex.toString();
		    	FlagStr = "Fail";
		  }
  }
			
%>
<html>
<script language="javascript">
  parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');
<%
if(mAction.equals("Print"))
{
%>
  window.open("../f1print/GetF1Print.jsp");
<%}%>
</script>
</html>
