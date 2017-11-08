<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%
//WFEdorAutoUWSave.jsp
//程序功能：保全工作流-自动核保
//创建日期：2005-04-30 14:02:52
//创建人  ：zhangtao
//更新记录：  更新人    更新日期     更新原因/内容
//      
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.workflow.bq.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%

  //输出参数
  String FlagStr = "";
  String Content = ""; 
  CErrors tError = null;
  
//  EdorWorkFlowUI tEdorWorkFlowUI;
  LPEdorAppSchema tLPEdorAppSchema;
  TransferData tTransferData;
  TransferData tResultData;
  GlobalInput tG = new GlobalInput(); 
  tG = (GlobalInput) session.getValue("GI");

    //===================
    String tEdorAcceptNo[]  = request.getParameterValues("PrivateWorkPoolGrid1");
    String tOtherNo[]       = request.getParameterValues("PrivateWorkPoolGrid2");
    String tOtherNoType[]   = request.getParameterValues("PrivateWorkPoolGrid9");
    String tEdorAppName[]   = request.getParameterValues("PrivateWorkPoolGrid10");
    String tApptype[]       = request.getParameterValues("PrivateWorkPoolGrid11");  
    String tManageCom[]     = request.getParameterValues("PrivateWorkPoolGrid12");
    String tAppntName[]     = request.getParameterValues("PrivateWorkPoolGrid4");
    String tPaytoDate[]     = request.getParameterValues("PrivateWorkPoolGrid13");
    //===================
    String tMissionID[]     = request.getParameterValues("PrivateWorkPoolGrid15");
    String tSubMissionID[]  = request.getParameterValues("PrivateWorkPoolGrid16");
    String tChk[] = request.getParameterValues("InpPrivateWorkPoolGridChk");
    String tActivityID[]  = request.getParameterValues("PrivateWorkPoolGrid18");

    int n = tEdorAcceptNo.length;
    
for (int i = 0; i < n; i++)
{
    if (!tChk[i].equals("1"))
    {
        continue;
    }
    //任务信息
    tTransferData = new TransferData();
    tTransferData.setNameAndValue("MissionID", tMissionID[i]);
    tTransferData.setNameAndValue("SubMissionID", tSubMissionID[i]);
    tTransferData.setNameAndValue("ActivityID", tActivityID[i]);
    tTransferData.setNameAndValue("EdorAcceptNo", tEdorAcceptNo[i]);
    tTransferData.setNameAndValue("OtherNo", tOtherNo[i]);
    tTransferData.setNameAndValue("OtherNoType", tOtherNoType[i]);
    tTransferData.setNameAndValue("EdorAppName", tEdorAppName[i]);
    tTransferData.setNameAndValue("Apptype", tApptype[i]); 
    tTransferData.setNameAndValue("ManageCom", tManageCom[i]);
    tTransferData.setNameAndValue("AppntName", tAppntName[i]);
    tTransferData.setNameAndValue("PaytoDate", tPaytoDate[i]);
    tTransferData.setNameAndValue("UWActivityStatus","");
    tTransferData.setNameAndValue("BackTime","");
    tTransferData.setNameAndValue("BackDate","");
    
        
    //
    tLPEdorAppSchema = new LPEdorAppSchema();
    tLPEdorAppSchema.setEdorAcceptNo(tEdorAcceptNo[i]); 
        
//    tEdorWorkFlowUI = new EdorWorkFlowUI();
    VData tVData = new VData();
    ExeSQL yExeSQL = new ExeSQL();
    SSRS ySSRS = new SSRS();
   // String busiName="tWorkFlowUI";
    String busiName="WorkFlowUI";
   
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
	tTransferData.setNameAndValue("ActivityID",request.getParameter("ActivityID"));
	tTransferData.setNameAndValue("BusiType", "1002");
  try
    {          
        tVData.add(tLPEdorAppSchema);
        tVData.add(tTransferData);
        tVData.add(tG);
//        tEdorWorkFlowUI.submitData(tVData, "0000000004");
        tBusinessDelegate.submitData( tVData, "execute",busiName ) ;
    }
    catch(Exception ex)
    {
          Content = "保全申请"+ tEdorAcceptNo[i] + "核保失败，原因是:" + ex.toString();
          FlagStr = "Fail";
    }
    if ("".equals(FlagStr))
    {
//            tError = tEdorWorkFlowUI.mErrors;
            tError = tBusinessDelegate.getCErrors();
            if (!tError.needDealError())
            {               
                    VData mResult = new VData();
//                    mResult = tEdorWorkFlowUI.getResult();  
                    mResult = tBusinessDelegate.getResult();  
                                       
                 //   VData mNData = 
                //        (VData)
                //        mResult.getObjectByObjectName("VData", 0);
                        
                    TransferData mNTransferData = 
                        (TransferData)
                        mResult.getObjectByObjectName("TransferData", 0);
                        
                    String sUWFlag = 
                        (String)
                       mNTransferData.getValueByName("UWFlag");

                System.out.println("========sUWFlag========" + sUWFlag);
                if (!(sUWFlag.equals("9")))
                {
                    Content = "申请批单"+ tEdorAcceptNo[i] + "需要人工核保！";
                    FlagStr = "Fail";
                }   
                else
            	
                {
                    Content ="核保成功！";
                    FlagStr = "Succ";               
                }           
            }
            else                                                                           
            {
                Content = "核保失败，原因是:" + tError.getFirstError();
                FlagStr = "Fail";
            }
    }
}   

%>
   
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
   
   
   
 