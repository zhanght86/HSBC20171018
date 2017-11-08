<%
//程序名称：PEdorTypePUCount.jsp
//程序功能：
//创建日期：2005-07-25
//创建人  ：Nicholas
//说明    ：本页面作用是初始化界面，其中信息必须从后台查出 
//更新记录：  更新人    更新日期     更新原因/内容
%>

  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.bq.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page contentType="text/html;charset=GBK" %>
<%
  //后面要执行的动作：查询
  CErrors tError = new CErrors();
  String FlagStr = "";
  String Content = "";
  String StrPolGrid = ""; //拼接成mulline
  
  TransferData tTransferData = new TransferData();
    
    //个人保单批改信息
    LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
    tLPEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
    tLPEdorItemSchema.setEdorNo(request.getParameter("EdorNo"));
    tLPEdorItemSchema.setEdorType(request.getParameter("EdorType"));
    tLPEdorItemSchema.setContNo(request.getParameter("ContNo"));
    tLPEdorItemSchema.setInsuredNo(request.getParameter("InsuredNo"));
    tLPEdorItemSchema.setPolNo(request.getParameter("PolNo"));
    tLPEdorItemSchema.setEdorAppDate(request.getParameter("EdorAppDate")); 
    tLPEdorItemSchema.setEdorValiDate(request.getParameter("EdorValiDate")); 
    
    //个别险种处理
    tTransferData.setNameAndValue("InsuYear",request.getParameter("InsuYear"));
    tTransferData.setNameAndValue("GetDutyKind",request.getParameter("GetDutyKind"));
    //标记特殊险种的界面初始化查询，告诉后台只查询险种信息
    tTransferData.setNameAndValue("SpecialRiskInitFlag",request.getParameter("SpecialRiskInitFlag"));
    
    PEdorPUDetailBLF tPEdorPUDetailBLF = new PEdorPUDetailBLF();
    try 
    {
        // 准备传输数据 VData
        VData tVData = new VData();  
        tVData.add(tLPEdorItemSchema);
        tVData.add(tTransferData);
        boolean tag = tPEdorPUDetailBLF.submitData(tVData,"QUERY||MAIN");
    } 
    catch(Exception ex) 
    {
        Content = "查询信息产生错误:" + ex.toString();
        FlagStr = "Fail";
    }           
    
  //如果在Catch中发现异常，则不从错误类中提取错误信息
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
                Content = "获取后台信息失败！";
          }
          tTransferData = (TransferData)tResult.getObjectByObjectName("TransferData", 0);
          
          String sRiskCount = (String) tTransferData.getValueByName("RiskCount");
          int iRiskCount = Integer.parseInt(sRiskCount);
          System.out.println("缴清险种个数：" + iRiskCount);

          
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

