<%
//�������ƣ�PEdorTypePUCount.jsp
//�����ܣ�
//�������ڣ�2005-07-25
//������  ��Nicholas
//˵��    ����ҳ�������ǳ�ʼ�����棬������Ϣ����Ӻ�̨��� 
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page contentType="text/html;charset=GBK" %>
<%
  //����Ҫִ�еĶ�������ѯ
  CErrors tError = new CErrors();
  String FlagStr = "";
  String Content = "";
  String StrPolGrid = ""; //ƴ�ӳ�mulline
  
  TransferData tTransferData = new TransferData();
    
    //���˱���������Ϣ
    LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
    tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
    tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
    tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
    tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
    tLPEdorItemSchema.setInsuredNo(request.getParameter("InsuredNo"));
    tLPEdorItemSchema.setPolNo(request.getParameter("PolNo"));
    tLPEdorItemSchema.setEdorAppDate(request.getParameter("EdorAppDate")); 
    tLPEdorItemSchema.setEdorValiDate(request.getParameter("EdorValiDate")); 
    
    //�������ִ���
    tTransferData.setNameAndValue("InsuYear",request.getParameter("InsuYear"));
    tTransferData.setNameAndValue("GetDutyKind",request.getParameter("GetDutyKind"));
    //����������ֵĽ����ʼ����ѯ�����ߺ�ֻ̨��ѯ������Ϣ
    tTransferData.setNameAndValue("SpecialRiskInitFlag",request.getParameter("SpecialRiskInitFlag"));
    
    PEdorPUDetailBLF tPEdorPUDetailBLF = new PEdorPUDetailBLF();
    try 
    {
        // ׼���������� VData
        VData tVData = new VData();  
        tVData.add(tLPEdorItemSchema);
        tVData.add(tTransferData);
        boolean tag = tPEdorPUDetailBLF.submitData(tVData,"QUERY||MAIN");
    } 
    catch(Exception ex) 
    {
        Content = "��ѯ��Ϣ��������:" + ex.toString();
        FlagStr = "Fail";
    }           
    
  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr.equals("")) 
  {
    tError = tPEdorPUDetailBLF.mErrors;
    if (!tError.needDealError()) 
    {
        FlagStr = "Success";

        if (tPEdorPUDetailBLF.getResult()!=null && tPEdorPUDetailBLF.getResult().size()>0)
        {
          VData tResult = new VData();
          tResult = tPEdorPUDetailBLF.getResult();
          if (tResult==null) 
          {
                FlagStr = "Fail"; 
                Content = "��ȡ��̨��Ϣʧ�ܣ�";
          }
          tTransferData = (TransferData)tResult.getObjectByObjectName("TransferData", 0);
          
          String sRiskCount = (String) tTransferData.getValueByName("RiskCount");
          int iRiskCount = Integer.parseInt(sRiskCount);
          System.out.println("�������ָ�����" + iRiskCount);

          
          String sPolNo;
          String sOldRiskCode;
          String sOldRiskName;
          String sNewRiskCode;
          String sNewRiskName;
          Double DSumBonus;
          Double DFinaleBonus;
          Double DCashValue;
          Double DNewAmnt;
          String sPayToDate;
          Double DPrem;
          Double DAmnt;
          Double DMult;
          for (int i = 1; i <= iRiskCount; i++)
          {
              sPolNo = (String) tTransferData.getValueByName("PolNo" + i);
              sOldRiskCode = (String) tTransferData.getValueByName("OldRiskCode" + i);
              sOldRiskName = (String) tTransferData.getValueByName("OldRiskName" + i);
              sNewRiskCode = (String) tTransferData.getValueByName("NewRiskCode" + i);
              sNewRiskName = (String) tTransferData.getValueByName("NewRiskName" + i);
              DSumBonus = (Double) tTransferData.getValueByName("SumBonus" + i);
              DFinaleBonus = (Double) tTransferData.getValueByName("FinaleBonus" + i);
              DCashValue = (Double) tTransferData.getValueByName("CashValue" + i);
              DNewAmnt = (Double) tTransferData.getValueByName("NewAmnt" + i);
              sPayToDate = (String) tTransferData.getValueByName("PayToDate" + i);
              DPrem = (Double) tTransferData.getValueByName("Prem" + i);
              DAmnt = (Double) tTransferData.getValueByName("Amnt" + i);
              DMult = (Double) tTransferData.getValueByName("Mult" + i);
              StrPolGrid += "^" + sPolNo + "|" + sOldRiskCode + "|" + sOldRiskName + "|" + sNewRiskCode + "|" + sNewRiskName + "|" + DSumBonus + "|" + DFinaleBonus + "|" + DCashValue + "|" + DNewAmnt + "|" + sPayToDate + "|" + DPrem + "|" + DAmnt + "|" + DMult;
          	  System.out.println(StrPolGrid);
          }
          System.out.println(StrPolGrid);
      }
    } 
    else  
    {
        Content = tError.getFirstError();
        FlagStr = "Fail";
    }
  }

%>
<html>
<script language="javascript">
	parent.fraInterface.fm.StrPolGrid.value = "<%=StrPolGrid%>";
	parent.fraInterface.afterCount("<%=FlagStr%>", "<%=Content%>");
</script>
</html>

