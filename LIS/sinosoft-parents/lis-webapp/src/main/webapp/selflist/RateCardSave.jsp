<%
//�������ƣ�RateCardSave.jsp
//Creator :zz
//date :2008-06-20
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
  <%@page import="com.sinosoft.lis.vbl.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
 <%@page import="com.sinosoft.lis.list.*"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  


<%
  loggerDebug("RateCardSave","��ʼִ��Saveҳ��");
  //����ȫ�ֱ�������������Ͳ���Ա
  String Operator="";
  String tManageCom="";
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String mDescType = ""; //��������־��Ӣ��ת���ɺ��ֵ���ʽ
  String hiddenRiskCode=""; //���ص�RiskCode,�������޸Ĳ���ʱ��ѯ�������ݿ��е��ϼ�¼
  String hiddenPrem=""; //���ص�Prem,�������޸Ĳ���ʱ��ѯ�������ݿ��е��ϼ�¼

  RateCardSchema tRateCardSchema = new RateCardSchema();

  //�����������
  CErrors tError = null;
  
  loggerDebug("RateCardSave","��ʼ���л�ȡ���ݵĲ���������");
  String mOperateType = request.getParameter("OperateType");
  //���������ǲ��룬��ѯ������
  loggerDebug("RateCardSave","������������"+mOperateType);
  loggerDebug("RateCardSave","ҳ��¼������ֱ�����"+request.getParameter("Riskcode"));
  loggerDebug("RateCardSave","ҳ��¼��ı�����"+request.getParameter("Prem"));
  loggerDebug("RateCardSave","ҳ��¼��ķ�����"+request.getParameter("Mult"));
  loggerDebug("RateCardSave","ҳ��¼��ı���������"+request.getParameter("InsuYear"));
  loggerDebug("RateCardSave","ҳ��¼��ı������ڵ�λ��"+request.getParameter("InsuYearFlag"));
  loggerDebug("RateCardSave","��Ʒ�ƻ���"+request.getParameter("ProductPlan"));  
  loggerDebug("RateCardSave","ҳ�洫����������ֱ�����"+request.getParameter("HiddenRiskCode"));
  loggerDebug("RateCardSave","ҳ�洫������ر�����"+request.getParameter("HiddenPrem"));
  
  
  //��LZCardFeeSchema��ʵ����ֵ,�ѱ���BL���л����Щ����,������Щ������ѯ���ݿ⣬����һЩУ���жϣ��Ƿ���Խ��в���Ȳ���
  tRateCardSchema.setRiskCode(request.getParameter("Riskcode"));
  tRateCardSchema.setPrem(request.getParameter("Prem"));
  tRateCardSchema.setMult(request.getParameter("Mult"));
  tRateCardSchema.setInsuYear(request.getParameter("InsuYear"));
  tRateCardSchema.setInsuYearFlag(request.getParameter("InsuYearFlag"));
  tRateCardSchema.setProductPlan(request.getParameter("ProductPlan"));
  
  
  hiddenRiskCode=request.getParameter("HiddenRiskCode");
  hiddenPrem=request.getParameter("HiddenPrem");
  if(hiddenRiskCode.equals(""))
  {
	  hiddenRiskCode=tRateCardSchema.getRiskCode();
  }
  
  if(hiddenPrem.equals(""))
  {
	  hiddenPrem=request.getParameter("Prem");
  }
  
  loggerDebug("RateCardSave","�������������ֱ�����"+hiddenRiskCode);
  loggerDebug("RateCardSave","���������ر�����"+hiddenPrem);
  
  TransferData tTransferData = new TransferData();
  tTransferData.setNameAndValue("HiddenRiskCode",hiddenRiskCode);
  tTransferData.setNameAndValue("HiddenPrem",hiddenPrem);
  
  
  
  //���ȫ�ֱ���
  GlobalInput tG = new GlobalInput();  
  tG=(GlobalInput)session.getValue("GI");  
  if(tG == null) 
  {
	  out.println("ȫ�ֱ���Ϊ��");
	  return;
  }
  else
  {
      Operator=tG.Operator;
      loggerDebug("RateCardSave","����Ա��"+Operator);
      tManageCom=tG.ManageCom;
      loggerDebug("RateCardSave","���������"+tManageCom);
  }
  
  tRateCardSchema.setManagerCom("86"); //Ŀǰֻ���ܹ�˾�ܶ���
  tRateCardSchema.setOperator(tG.Operator);
 

  if(mOperateType.equals("INSERT"))
  {
      mDescType = "����";
  }
  if(mOperateType.equals("UPDATE"))
  {
      mDescType = "�޸�";
  } 
  if(mOperateType.equals("DELETE"))
  {
      mDescType = "ɾ��";
  }
  if(mOperateType.equals("QUERY"))
  {
      mDescType = "��ѯ";
  }


  VData tVData = new VData();
  
  //�������ύ����̨UI,������VData�Ͳ�������
  RateCardBL tRateCardBL = new RateCardBL();
  try
  {
    //���������ͣ��������������Ա��ӵ�������
    tVData.addElement(tRateCardSchema);
    tVData.addElement(tTransferData);
    if (!tRateCardBL.submitData(tVData,mOperateType))
    {
    	Content = mDescType+"ʧ�ܣ�ԭ����: " + tRateCardBL.mErrors.getError(0).errorMessage;
      	FlagStr = "Fail";
    }
  }
  catch(Exception ex)
  {
    Content = mDescType+"ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
  }

  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr=="")
  {
    tError = tRateCardBL.mErrors;
    if (!tError.needDealError())
    {
      Content = mDescType+" �ɹ�";
    	FlagStr = "Succ";
    }
    else
    {
    	Content = mDescType+" ʧ�ܣ�ԭ����:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
