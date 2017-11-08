<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：GEdorAppItemSave.jsp
//程序功能：团单保全申请添加保全项目
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
 
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>

<%
  
   String busiName="bqGEdorAppItemBL";
   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //接收信息，并作校验处理。
  //输入参数

  TransferData tTransferData = new TransferData();

  //输出参数
  String FlagStr = "";
  CErrors tError = null;
  String Content = "";
  boolean fieldChecked=true;

  GlobalInput tGI = new GlobalInput(); //repair:
  tGI=(GlobalInput)session.getValue("GI");

    String fmAction    = request.getParameter("fmtransact");
    String displayType = request.getParameter("DisplayType");
    String MissionID   = request.getParameter("MissionID");
    String SubMissionID   = request.getParameter("SubMissionID");
    tTransferData.setNameAndValue("DisplayType", displayType);
    tTransferData.setNameAndValue("MissionID", MissionID);
    tTransferData.setNameAndValue("SubMissionID", SubMissionID);

    String sTransact    = request.getParameter("Transact");

if(sTransact.equals("GRPEDORITEM"))
{
    LPGrpEdorItemSet mLPGrpEdorItemSet = new LPGrpEdorItemSet();

    LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
    tLPGrpEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
    tLPGrpEdorItemSchema.setDisplayType(displayType);
    tLPGrpEdorItemSchema.setEdorTypeCal(request.getParameter("EdorTypeCal"));
    tLPGrpEdorItemSchema.setEdorType(request.getParameter("EdorType"));
    tLPGrpEdorItemSchema.setGrpContNo(request.getParameter("OtherNo"));    //团单保全没有客户层变更
    tLPGrpEdorItemSchema.setEdorAppDate(request.getParameter("EdorItemAppDate"));
    //tLPEdorItemSchema.setEdorValiDate(request.getParameter("EdorValiDate"));
    tLPGrpEdorItemSchema.setAppReason(request.getParameter("AppReason"));
    //loggerDebug("GEdorAppItemSave",tLPGrpEdorItemSchema.deCode());
    mLPGrpEdorItemSet.add(tLPGrpEdorItemSchema);

    // 准备传输数据 VData
    VData tVData = new VData();
    

    try
    {
         tVData.add(mLPGrpEdorItemSet);
         tVData.add(tTransferData);
         tVData.add(tGI);
         tBusinessDelegate.submitData(tVData,"GRPEDORITEM",busiName);
    }
    catch(Exception ex)
    {
          Content = "添加失败，原因是:" + ex.toString();
          FlagStr = "Fail";
    }
    if ("".equals(FlagStr))
    {
            tError = tBusinessDelegate.getCErrors();
            if (!tError.needDealError())
            {
                Content ="添加成功！";
                FlagStr = "Succ";
            }
            else
            {
                Content = "添加失败，原因是:" + tError.getFirstError().toString();
                FlagStr = "Fail";
            }
    }
}

if(sTransact.equals("PEDORITEM"))
{
    LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet();

    LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
    tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
    tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
    tLPEdorItemSchema.setEdorAppNo(request.getParameter("EdorNo"));
    tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
    tLPEdorItemSchema.setGrpContNo(request.getParameter("GrpContNo"));
    tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
    tLPEdorItemSchema.setInsuredNo(request.getParameter("CustomerNo"));
    tLPEdorItemSchema.setPolNo("000000");
    mLPEdorItemSet.add(tLPEdorItemSchema);

    // 准备传输数据 VData
    VData tVData = new VData();
    

    try
    {
         tVData.add(mLPEdorItemSet);
         tVData.add(tTransferData);
         tVData.add(tGI);
         tBusinessDelegate.submitData(tVData,"PEDORITEM",busiName);
    }
    catch(Exception ex)
    {
          Content = "添加失败，原因是:" + ex.toString();
          FlagStr = "Fail";
    }
    if ("".equals(FlagStr))
    {
            tError = tBusinessDelegate.getCErrors();
            if (!tError.needDealError())
            {
                Content ="添加成功！";
                FlagStr = "Succ";
            }
            else
            {
                Content = "添加失败，原因是:" + tError.getFirstError().toString();
                FlagStr = "Fail";
            }
    }
}

if(sTransact.equals("GPEDORITEM")) //多人同时添加
{
    LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet();
    String tChk[] = request.getParameterValues("InpLCInsuredGridChk");
    String tContNo[] = request.getParameterValues("LCInsuredGrid1");
    String tInsuredNo[] = request.getParameterValues("LCInsuredGrid2");
    for(int i = 0; i < tChk.length; i ++)
    {
        if("1".equals(tChk[i]))
        {
             LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
             tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
             tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
             tLPEdorItemSchema.setEdorAppNo(request.getParameter("EdorNo"));
             tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
             tLPEdorItemSchema.setGrpContNo(request.getParameter("GrpContNo"));
             tLPEdorItemSchema.setEdorAppDate(request.getParameter("EdorItemAppDate"));
             tLPEdorItemSchema.setContNo(tContNo[i]);
             tLPEdorItemSchema.setInsuredNo(tInsuredNo[i]);
             tLPEdorItemSchema.setPolNo("000000");
             mLPEdorItemSet.add(tLPEdorItemSchema);
        }
    }

    // 准备传输数据 VData
    VData tVData = new VData();
   

    try
    {
        tVData.add(mLPEdorItemSet);
        tVData.add(tTransferData);
        tVData.add(tGI);
          tBusinessDelegate.submitData(tVData,"PEDORITEM",busiName);
    }
    catch(Exception ex)
    {
        Content = "添加失败，原因是:" + ex.toString();
        FlagStr = "Fail";
    }
    if ("".equals(FlagStr))
    {
          tError = tBusinessDelegate.getCErrors();
          if (!tError.needDealError())
          {
            Content ="添加成功！";
            FlagStr = "Succ";
          }
          else
          {
            Content = "添加失败，原因是:" + tError.getFirstError().toString();
            FlagStr = "Fail";
          }
    }
}

if(sTransact.equals("POLEDORITEM"))
{
    LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet();
    String tChk[] = request.getParameterValues("InpLCInsuredGridChk");
    String tContNo[] = request.getParameterValues("LCInsuredGrid1");
    String tInsuredNo[] = request.getParameterValues("LCInsuredGrid2");
    String tPolNo[] = request.getParameterValues("LCInsuredGrid15");

    for(int i = 0; i < tChk.length; i ++)
    {
        if("1".equals(tChk[i]))
        {
             LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
             tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
             tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
             tLPEdorItemSchema.setEdorAppNo(request.getParameter("EdorNo"));
             tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
             tLPEdorItemSchema.setGrpContNo(request.getParameter("GrpContNo"));
             tLPEdorItemSchema.setContNo(tContNo[i]);
             tLPEdorItemSchema.setInsuredNo(tInsuredNo[i]);
             tLPEdorItemSchema.setPolNo(tPolNo[i]);
             //用StandbyFlag1保存涉及的团体险种号
             tLPEdorItemSchema.setStandbyFlag1(request.getParameter("GrpPolNo"));
             mLPEdorItemSet.add(tLPEdorItemSchema);
        }
    }

    // 准备传输数据 VData
    VData tVData = new VData();
    

    try
    {
         tVData.add(mLPEdorItemSet);
         tVData.add(tTransferData);
         tVData.add(tGI);
         tBusinessDelegate.submitData(tVData,"PEDORITEM",busiName);
    }
    catch(Exception ex)
    {
          Content = "添加失败，原因是:" + ex.toString();
          FlagStr = "Fail";
    }
    if ("".equals(FlagStr))
    {
            tError = tBusinessDelegate.getCErrors();
            if (!tError.needDealError())
            {
                Content ="添加成功！";
                FlagStr = "Succ";
            }
            else
            {
                Content = "添加失败，原因是:" + tError.getFirstError().toString();
                FlagStr = "Fail";
            }
    }
}



//页面有效区
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>");
</script>
</html>

