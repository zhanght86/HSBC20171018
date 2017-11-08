
<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：EdorUWManuSpecSave.jsp
//程序功能：人工核保特约承保
//创建日期：2005-06-24 17:10:36
//创建人  ：liurongxiao
//更新记录：  更新人 钱林燕   更新日期  2006-10-27   更新原因/内容 大修

%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.BusinessDelegate"%>
<%
  //输出参数
    CErrors tError = null;
    String FlagStr = "Fail";
    String Content = "";

    boolean flag = true;
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");
    if(tG == null) {
        out.println("session has expired");
        return;
    }
    //接收信息
    LPSpecSchema tLPSpecSchema = new LPSpecSchema();
    LPUWMasterSchema tLPUWMasterSchema = new LPUWMasterSchema();
    TransferData tTransferData = new TransferData();

  String tContNo = request.getParameter("ContNo");
    String tRemark = request.getParameter("Remark");
    String tSpecReason = request.getParameter("SpecReason");
    String tEdorNo = request.getParameter("EdorNo");
    String tEdorType = request.getParameter("EdorType");
    String tMissionID = request.getParameter("MissionID");
    String tSubMissionID = request.getParameter("SubMissionID");
    String tPolNo = request.getParameter("PolNo");
    String tPrtNo = request.getParameter("PrtNo");
  String tOperateType = request.getParameter("operate");
  String tSerialNo = request.getParameter("serialno");

    loggerDebug("EdorUWManuSpecSave","EdorNo:"+tEdorNo);
    loggerDebug("EdorUWManuSpecSave","EdorType:"+tEdorType);
    loggerDebug("EdorUWManuSpecSave","ContNo:"+tContNo);
    loggerDebug("EdorUWManuSpecSave","remark:"+tRemark);
    loggerDebug("EdorUWManuSpecSave","PolNo:"+tPolNo);
  loggerDebug("EdorUWManuSpecSave","PrtNo:"+tPrtNo);

    if (tContNo == "" || (tRemark == "" ) )
    {
        Content = "请录入特别约定信息或者续保备注信息!";
        FlagStr = "Fail";
        flag = false;
    }

          if(tContNo != null && tEdorNo != null && tMissionID != null && tSubMissionID != null)
          {
            //准备特约信息
            tLPSpecSchema.setContNo(tContNo);
            tLPSpecSchema.setEdorNo(tEdorNo);
            tLPSpecSchema.setEdorType(tEdorType);
            tLPSpecSchema.setPolNo(tPolNo);

            tLPSpecSchema.setSpecContent(tRemark);
            tLPSpecSchema.setSpecType("1");
            tLPSpecSchema.setSpecCode("1");
            //准备特约原因
            tLPUWMasterSchema.setContNo(tContNo);
            tLPUWMasterSchema.setEdorNo(tEdorNo);
            tLPUWMasterSchema.setEdorType(tEdorType);
            tLPUWMasterSchema.setPolNo(tPolNo);

            tLPUWMasterSchema.setSpecReason(tSpecReason);


           }
           else
           {
             Content = "传输数据失败!";
             flag = false;
           }

try
{
    if (flag == true)
    {
        // 准备传输数据 VData

        VData tVData = new VData();

          tTransferData.setNameAndValue("ContNo",tContNo);
        tTransferData.setNameAndValue("EdorNo",tEdorNo);
        tTransferData.setNameAndValue("EdorType",tEdorType);
        tTransferData.setNameAndValue("OperateType",tOperateType);
        tTransferData.setNameAndValue("SerialNo",tSerialNo);
      tTransferData.setNameAndValue("PolNo",tPolNo);
      tTransferData.setNameAndValue("PrtNo",tPrtNo);
        tTransferData.setNameAndValue("MissionID",tMissionID );
        tTransferData.setNameAndValue("SubMissionID",tSubMissionID );
      tTransferData.setNameAndValue("LPSpecSchema",tLPSpecSchema);
      tTransferData.setNameAndValue("LPUWMasterSchema",tLPUWMasterSchema);

        tVData.add( tTransferData );
        tVData.add( tG );

        // 数据传输
        //EdorUWSpecUI tEdorUWSpecUI   = new EdorUWSpecUI();
        BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
        //if (!tEdorUWSpecUI.submitData(tVData,""))
        if(!tBusinessDelegate.submitData(tVData, "", "EdorUWSpecUI"))
          {

            int n = tBusinessDelegate.getCErrors().getErrorCount();
            Content = " 核保特约失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
            FlagStr = "Fail";
        }
        //如果在Catch中发现异常，则不从错误类中提取错误信息
        if (FlagStr == "Fail")
        {
            tError = tBusinessDelegate.getCErrors();
            if (!tError.needDealError())
            {
                Content = " 核保特约成功! ";
                FlagStr = "Succ";
            }
            else
            {
                FlagStr = "Fail";
            }
        }
    }
}
catch(Exception e)
{
    e.printStackTrace();
    Content = Content.trim()+"提示：异常终止!";
}
%>

<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

