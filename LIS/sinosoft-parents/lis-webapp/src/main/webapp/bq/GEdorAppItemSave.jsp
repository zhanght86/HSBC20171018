<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�GEdorAppItemSave.jsp
//�����ܣ��ŵ���ȫ������ӱ�ȫ��Ŀ
%>
<!--�û�У����-->
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
  //������Ϣ������У�鴦��
  //�������

  TransferData tTransferData = new TransferData();

  //�������
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
    tLPGrpEdorItemSchema.setGrpContNo(request.getParameter("OtherNo"));    //�ŵ���ȫû�пͻ�����
    tLPGrpEdorItemSchema.setEdorAppDate(request.getParameter("EdorItemAppDate"));
    //tLPEdorItemSchema.setEdorValiDate(request.getParameter("EdorValiDate"));
    tLPGrpEdorItemSchema.setAppReason(request.getParameter("AppReason"));
    //loggerDebug("GEdorAppItemSave",tLPGrpEdorItemSchema.deCode());
    mLPGrpEdorItemSet.add(tLPGrpEdorItemSchema);

    // ׼���������� VData
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
          Content = "���ʧ�ܣ�ԭ����:" + ex.toString();
          FlagStr = "Fail";
    }
    if ("".equals(FlagStr))
    {
            tError = tBusinessDelegate.getCErrors();
            if (!tError.needDealError())
            {
                Content ="��ӳɹ���";
                FlagStr = "Succ";
            }
            else
            {
                Content = "���ʧ�ܣ�ԭ����:" + tError.getFirstError().toString();
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

    // ׼���������� VData
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
          Content = "���ʧ�ܣ�ԭ����:" + ex.toString();
          FlagStr = "Fail";
    }
    if ("".equals(FlagStr))
    {
            tError = tBusinessDelegate.getCErrors();
            if (!tError.needDealError())
            {
                Content ="��ӳɹ���";
                FlagStr = "Succ";
            }
            else
            {
                Content = "���ʧ�ܣ�ԭ����:" + tError.getFirstError().toString();
                FlagStr = "Fail";
            }
    }
}

if(sTransact.equals("GPEDORITEM")) //����ͬʱ���
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

    // ׼���������� VData
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
        Content = "���ʧ�ܣ�ԭ����:" + ex.toString();
        FlagStr = "Fail";
    }
    if ("".equals(FlagStr))
    {
          tError = tBusinessDelegate.getCErrors();
          if (!tError.needDealError())
          {
            Content ="��ӳɹ���";
            FlagStr = "Succ";
          }
          else
          {
            Content = "���ʧ�ܣ�ԭ����:" + tError.getFirstError().toString();
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
             //��StandbyFlag1�����漰���������ֺ�
             tLPEdorItemSchema.setStandbyFlag1(request.getParameter("GrpPolNo"));
             mLPEdorItemSet.add(tLPEdorItemSchema);
        }
    }

    // ׼���������� VData
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
          Content = "���ʧ�ܣ�ԭ����:" + ex.toString();
          FlagStr = "Fail";
    }
    if ("".equals(FlagStr))
    {
            tError = tBusinessDelegate.getCErrors();
            if (!tError.needDealError())
            {
                Content ="��ӳɹ���";
                FlagStr = "Succ";
            }
            else
            {
                Content = "���ʧ�ܣ�ԭ����:" + tError.getFirstError().toString();
                FlagStr = "Fail";
            }
    }
}



//ҳ����Ч��
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=Content%>");
</script>
</html>

