<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>

<%
//�������ƣ�PDRiskSortDefiSave.jsp
//�����ܣ����ַ��ඨ��
//�������ڣ�2009-3-12
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>   
<%
 //������Ϣ������У�鴦��
 //�������
 

 //PDRiskSortDefiBL pDRiskSortDefiBL = new PDRiskSortDefiBL();
 
 CErrors tError = null;
 String tRela  = "";                
 String FlagStr = "";
 String Content = "";
 String operator = "";
 TransferData transferData = new TransferData();
 GlobalInput tG = new GlobalInput(); 
 tG=(GlobalInput)session.getAttribute("GI");
 
 //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
 operator = request.getParameter("operator");
 if("save".equals(operator)){
    String values[] = request.getParameterValues("Mulline9Grid1");
    String values2[] = request.getParameterValues("Mulline9Grid2");
    String values3[] = request.getParameterValues("Mulline9Grid3");
    java.util.ArrayList list = new java.util.ArrayList();
    java.util.ArrayList list2 = new java.util.ArrayList();
    java.util.ArrayList list3 = new java.util.ArrayList();
    for(int i = 0; i < values.length; i++)
    {
     	list.add(values[i]);
     	list2.add(values2[i]);
     	list3.add(values3[i]);
    }
	  transferData.setNameAndValue("list",list);
	  transferData.setNameAndValue("list2",list2);
	  transferData.setNameAndValue("list3",list3);
	}
	transferData.setNameAndValue("RiskCode",request.getParameter("RiskCode"));
	transferData.setNameAndValue("tableName",request.getParameter("tableName"));
 	try
 	{
  	// ׼���������� VData
  	VData tVData = new VData();

  	tVData.add(tG);
  	tVData.add(transferData);
  //	pDRiskSortDefiBL.submitData(tVData,operator);
  	String busiName="PDRiskSortDefiBL";
    
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    //String tDiscountCode = "";
    if (!tBusinessDelegate.submitData(tVData, operator,busiName)) {
  	  VData rVData = tBusinessDelegate.getResult();
      Content = " "+"����ʧ�ܣ�ԭ����:"+"" + tBusinessDelegate.getCErrors().getFirstError();
    	FlagStr = "Fail";
    }
    else {
      Content = " "+"����ɹ�!"+" ";
    	FlagStr = "Succ";
    }
   
   }
   catch(Exception ex)
   {
    Content = ""+"����ʧ�ܣ�ԭ����:"+"" + ex.toString();
    FlagStr = "Fail";
   }
  /*
 }
 catch(Exception ex)
 {
  Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
  FlagStr = "Fail";
 }

 //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
 if (FlagStr=="")
 {
  tError = pDRiskSortDefiBL.mErrors;
  if (!tError.needDealError())
  {                          
   Content = " ����ɹ�! ";
   FlagStr = "Success";
   RiskState.setState(request.getParameter("RiskCode"),"��Լҵ�����->���ַ��ඨ��","1");
  }
  else                                                                           
  {
   Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
   FlagStr = "Fail";
  }
 }*/

 //��Ӹ���Ԥ����
%>                      
<%=Content%>
<html>
<script type="text/javascript">
 parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

