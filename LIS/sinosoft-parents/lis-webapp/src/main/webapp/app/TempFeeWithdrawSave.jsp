<%@ page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
    //�������ƣ�TempFeeWithdrawSave.jsp
//�����ܣ�
//�������ڣ�2002-11-18 11:10:36
//������  ���� ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@ include file="../common/jsp/UsrCheck.jsp" %>

<%@ page import="com.sinosoft.utility.*" %>
<%@ page import="com.sinosoft.lis.schema.*" %>
<%@ page import="com.sinosoft.lis.vschema.*" %>
<%@ page import="com.sinosoft.lis.tb.*" %>
<%@ page import="com.sinosoft.lis.pubfun.*" %>
<%@ page import="com.sinosoft.lis.finfee.*" %>
<%@page import="com.sinosoft.service.*" %>

<%
    loggerDebug("TempFeeWithdrawSave","\n\n---TempFeeWithdrawSave Start---");

    //�������
    CErrors tError = null;
    String FlagStr = "Fail";
    String Content = "";
    String strResult = "";

    GlobalInput tG = new GlobalInput();
    tG = (GlobalInput) session.getValue("GI");

    LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();
    LJAGetTempFeeSet tLJAGetTempFeeSet = new LJAGetTempFeeSet();

    LWMissionSet tLWMissionSet = new LWMissionSet();

    String tTempFeeNo[] = request.getParameterValues("FeeGrid2");
    String tTempFeeType[] = request.getParameterValues("FeeGrid3");
    //String tRiskCode[] = request.getParameterValues("FeeGrid4");
    String tGetReasonCode[] = request.getParameterValues("FeeGrid1");
    String tPrtNo[] = request.getParameterValues("FeeGrid5");
    String tChk[] = request.getParameterValues("InpFeeGridChk");
    loggerDebug("TempFeeWithdrawSave","------------------=" + tChk[0]);
    //prepare TempfeeType  add by heyq 2005-12-24 Christmas Eve ^_^
    TransferData tTransferData = new TransferData();
    tTransferData.setNameAndValue("OtherNoFlag", tTempFeeType[0]);
    loggerDebug("TempFeeWithdrawSave","-----" + tTempFeeNo[0]);
    loggerDebug("TempFeeWithdrawSave","-----" + tTempFeeType[0]);


    //ѭ���������н���ѡ�����
    for (int index = 0; index < tChk.length; index++) {
        if (tChk[index].equals("1")) {
            LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
            tLJTempFeeSchema.setTempFeeNo(tTempFeeNo[index]);
            tLJTempFeeSchema.setTempFeeType(tTempFeeType[index]);
            //tLJTempFeeSchema.setRiskCode(tRiskCode[index]);
            tLJTempFeeSet.add(tLJTempFeeSchema);

            LJAGetTempFeeSchema tLJAGetTempFeeSchema = new LJAGetTempFeeSchema();
            tLJAGetTempFeeSchema.setGetReasonCode(tGetReasonCode[index]);
                loggerDebug("TempFeeWithdrawSave","tGetReasonCode[index]-----" + tGetReasonCode[index]);

//MS���⻯�������˷Ѳ����ߺ˱�����������
//            LWMissionSchema tLWMissionSchema = new LWMissionSchema();
//
//            tLWMissionSchema.setActivityID("0000001149");
//            tLWMissionSchema.setMissionProp1(tPrtNo[index]);

            tLJAGetTempFeeSet.add(tLJAGetTempFeeSchema);

//            tLWMissionSet.add(tLWMissionSchema);
        }
    }
    loggerDebug("TempFeeWithdrawSave","tLJTempFeeSet:" + tLJTempFeeSet.encode());

    // ׼���������� VData
    VData tVData = new VData();
    tVData.add(tLJTempFeeSet);
    tVData.add(tLJAGetTempFeeSet);
    tVData.add(tTransferData);
    tVData.add(tG);

    // ���ݴ���
    //TempFeeWithdrawUI tTempFeeWithdrawUI = new TempFeeWithdrawUI();
    String busiName="finfeeTempFeeWithdrawUI";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    tBusinessDelegate.submitData(tVData, "WITHDRAW",busiName);

    tError = tBusinessDelegate.getCErrors();
    loggerDebug("TempFeeWithdrawSave","needDeal:" + tError.needDealError());
    if (!tError.needDealError()) {
        Content = " �˷ѳɹ�! ";
        FlagStr = "Succ";
        loggerDebug("TempFeeWithdrawSave","----------after submitData");
        //strResult = (String)tTempFeeWithdrawUI.getResult().get(0);
        //loggerDebug("TempFeeWithdrawSave","strResult:" + strResult);
        loggerDebug("TempFeeWithdrawSave","---------------------111-------hyq--FlagStr--");
    } else {
        Content = " �˷�ʧ�ܣ�ԭ����:" + tError.getFirstError();
        FlagStr = "Fail";
        loggerDebug("TempFeeWithdrawSave","--------------------222--------hyq--FlagStr--");
    }
    loggerDebug("TempFeeWithdrawSave","----------------------------hyq--FlagStr--" + FlagStr + "---------Content---" + Content);
    loggerDebug("TempFeeWithdrawSave",Content + "\n" + FlagStr + "\n---TempFeeWithdrawSave End---\n\n");

    //���˷Ѻ�ɾ���˱������ڵ�
    //loggerDebug("TempFeeWithdrawSave","LWMissionSet.size()="+tLWMissionSet.size());
   // loggerDebug("TempFeeWithdrawSave","LWMissionSet.missionprop1()="+tLWMissionSet.get(1).getMissionProp1());
   // VData aVData = new VData();
   // aVData.add(tLWMissionSet);
  //  WorkFlowOperateUI tWorkFlowOperateUI = new WorkFlowOperateUI();
  //  tWorkFlowOperateUI.submitData(aVData, "submit");
  //  tError = tWorkFlowOperateUI.mErrors;



%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit('<%=FlagStr%>', '<%=Content%>');
    //parent.fraInterface.tempFeeNoQuery(1);

    //parent.fraInterface.open("./TempFeeWithdrawPrintMain.jsp?prtData=<%=strResult%>");
    //parent.fraInterface.fm2.PrtData.value = "<%=strResult%>";
    //parent.fraInterface.fm2.submit();
</script>
</html>
