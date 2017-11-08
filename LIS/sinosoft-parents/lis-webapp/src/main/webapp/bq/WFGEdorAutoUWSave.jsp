<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%

//程序功能：保全工作流-自动核保
//      
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>

  <%@page import="com.sinosoft.service.*" %>
<%

  String busiName="EdorWorkFlowUI";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //输出参数
  String FlagStr = "";
  String Content = ""; 
  CErrors tError = null;
  
 
  LPEdorAppSchema tLPEdorAppSchema;
  TransferData tTransferData;
  TransferData tResultData;
  GlobalInput tG = new GlobalInput(); 
  tG = (GlobalInput) session.getValue("GI");

    //===================
    String tEdorAcceptNo[]  = request.getParameterValues("SelfGrid1");
    String tOtherNo[]       = request.getParameterValues("SelfGrid2");
    String tOtherNoType[]   = request.getParameterValues("SelfGrid12");
    String tEdorAppName[]   = request.getParameterValues("SelfGrid4");
    String tApptype[]       = request.getParameterValues("SelfGrid13");  
    String tManageCom[]     = request.getParameterValues("SelfGrid14");
    String tAppntName[]     = request.getParameterValues("SelfGrid4");
    String tPaytoDate[]     = request.getParameterValues("SelfGrid15");
    //===================
    String tMissionID[]     = request.getParameterValues("SelfGrid9");
    String tSubMissionID[]  = request.getParameterValues("SelfGrid10");
    String tChk[] = request.getParameterValues("InpSelfGridChk");
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
    tTransferData.setNameAndValue("EdorAcceptNo", tEdorAcceptNo[i]);
    tTransferData.setNameAndValue("OtherNo", tOtherNo[i]);
    tTransferData.setNameAndValue("OtherNoType", tOtherNoType[i]);
    tTransferData.setNameAndValue("EdorAppName", tEdorAppName[i]);
    tTransferData.setNameAndValue("Apptype", tApptype[i]); 
    tTransferData.setNameAndValue("ManageCom", tManageCom[i]);
    tTransferData.setNameAndValue("AppntName", tAppntName[i]);
    tTransferData.setNameAndValue("PaytoDate", tPaytoDate[i]);
        
    //
    tLPEdorAppSchema = new LPEdorAppSchema();
    tLPEdorAppSchema.setEdorAcceptNo(tEdorAcceptNo[i]); 
        
   
    VData tVData = new VData();
    
    try
    {          
        tVData.add(tLPEdorAppSchema);
        tVData.add(tTransferData);
        tVData.add(tG);
        tBusinessDelegate.submitData(tVData, "0000008004",busiName);
    }
    catch(Exception ex)
    {
          Content = "保全申请"+ tEdorAcceptNo[i] + "核保失败，原因是:" + ex.toString();
          FlagStr = "Fail";
    }
    if ("".equals(FlagStr))
    {
            tError = tBusinessDelegate.getCErrors();
            if (!tError.needDealError())
            {               
                    VData mResult = new VData();
                    mResult = tBusinessDelegate.getResult();  
                                       
                    
                        
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
   
   
   
 