<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：PolStatusChk.jsp
//程序功能：保单状态查询
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  //输出参数
  CErrors tError = null;
  //CErrors tErrors = new CErrors();
  String FlagStr = "Fail";
  String Content = "";
  String tPrtNo = request.getParameter("ProposalContNo");
  loggerDebug("PolStatusChk","/***************/");
  loggerDebug("PolStatusChk","/***************/");
  loggerDebug("PolStatusChk","/***************/");
  loggerDebug("PolStatusChk","tPrtNo="+tPrtNo);
  loggerDebug("PolStatusChk","/***************/");
  loggerDebug("PolStatusChk","/***************/");
  loggerDebug("PolStatusChk","/***************/");

	GlobalInput tG = new GlobalInput();

	tG=(GlobalInput)session.getValue("GI");

  	if(tG == null) {
		out.println("session has expired");
		return;
	}

  //校验处理
  //内容待填充

  	//接收信息
  	// 投保单列表
	LCContSet tLCContSet = new LCContSet();

	String tProposalContNo[] = request.getParameterValues("PolGrid1");
	String tSel[] = request.getParameterValues("InpPolGridSel");
	boolean flag = false;
	int proposalCount = tProposalContNo.length;
        loggerDebug("PolStatusChk","proposalCount=:" + proposalCount);
	for (int i = 0; i < proposalCount; i++)
	{
		if (!tProposalContNo[i].equals("") && tSel[i].equals("1"))
		{
			loggerDebug("PolStatusChk","ProposalContNo:"+i+":"+tProposalContNo[i]);
	  		LCContSchema tLCContSchema = new LCContSchema();

		    tLCContSchema.setContNo( tProposalContNo[i] );

		    tLCContSet.add( tLCContSchema );
		    flag = true;
		}
	}
loggerDebug("PolStatusChk","tLCContSet.size()="+tLCContSet.size());
 
if(tLCContSet.size()==0){
	      LCContSchema tLCContSchema = new LCContSchema();
	      tLCContSchema.setContNo(tPrtNo);
	      tLCContSet.add( tLCContSchema );
	      flag = true;
	  }
   


  	if (flag == true)
  	{
		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.add( tG );
		loggerDebug("PolStatusChk","2342342342:"+tG.Operator);
		tVData.add( tLCContSet );

		// 数据传输
		PolStatusChkUI tPolStatusChkUI   = new PolStatusChkUI();

		if (tPolStatusChkUI.submitData(tVData,"INSERT") == false)
		{

			int n = tPolStatusChkUI.mErrors.getErrorCount();
			for (int i = 0; i < n; i++)
			loggerDebug("PolStatusChk","Error: "+tPolStatusChkUI.mErrors.getError(i).errorMessage);
			Content = "对不起，" + tPolStatusChkUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";

		}
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "Fail")
		{
                  tError = tPolStatusChkUI.mErrors;
                  //tErrors = tPolStatusChkUI.mErrors;
                  if (!tError.needDealError())
                  {
                    FlagStr = "Succ";
                    
                    String[][] Str = null;
                    VData tResult = tPolStatusChkUI.getResult();
                    if(tResult != null)
                    {       
                      TransferData tTransferData = new TransferData();
                      
                      tTransferData = (TransferData)tResult.getObjectByObjectName("TransferData",0);
                      Str = (String[][])tTransferData.getValueByName("Str");
                    }
                     if(Str.length > 0 )
                    {
                      %>
                      <script language="javascript" type="">
                        parent.fraInterface.PolStatuGrid.clearData ();
                        </script>
                        <%
            
                        for(int i = 1;i <= Str.length;i++)
                        {
                          
                          %>
                          <script language="javascript" type="">
                            parent.fraInterface.PolStatuGrid.addOne();
                            parent.fraInterface.PolStatuGrid.setRowColData(<%=i-1%>,1,"<%=Str[i-1][0]%>");
														parent.fraInterface.PolStatuGrid.setRowColData(<%=i-1%>,2,"<%=Str[i-1][1]%>");	
                            </script>
                            <%
                            }
                            
                          }
                          
                          else
                          {
                            FlagStr = "Fail";
                          }
                   
                          
            
                        }
                        }
                      }
      
	else
	{
		Content = "请选择保单！";
	}
 


%>
<html>
<script language="javascript" type="">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
	//parent.fraInterface.showInfo.close();
	//alert("<%=Content%>");	  
</script>
</html>
