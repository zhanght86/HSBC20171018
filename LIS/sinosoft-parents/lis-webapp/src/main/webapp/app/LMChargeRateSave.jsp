<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�LMChargeRatesave.jsp
//�����ܣ�
//�������ڣ�2006-4-17
//������  ��lwj
//���¼�¼��  ������    ��������     ����ԭ��/����
//      
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%> 
  <%@page import="com.sinosoft.service.*" %>
<%
  //������Ϣ������У�鴦��
  //�������

   LCGrpPolSchema tLCGrpPolSchema =new  LCGrpPolSchema();
   LMGrpChargeSchema tLMGrpChargeSchema =new LMGrpChargeSchema();
   CErrors tError = null;
   //LMGrpChargeBL tLMGrpChargeBL = new LMGrpChargeBL();
   String busiName="tbLMGrpChargeBL";
   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  GlobalInput tG = new GlobalInput(); 
  tG=(GlobalInput)session.getValue("GI");
  VData tVData = new VData();
  
  //�������
  String FlagStr = "";
  String Content = "";
    
  String strOperate = request.getParameter("mOperate");
  loggerDebug("LMChargeRateSave","==== strOperate == " + strOperate);
  LMGrpChargeSchema mLMGrpChargeSchema = new LMGrpChargeSchema();
  LCGrpPolSchema mLCGrpPolSchema = new LCGrpPolSchema();
  
  tLCGrpPolSchema.setGrpContNo(request.getParameter("GrpContNo"));     //�����ͬ����
  tLCGrpPolSchema.setRiskCode(request.getParameter("RiskCode"));     //���ֱ���

  tLMGrpChargeSchema.setPayCountFrom(0);	//��ʼ���Ѵ���
  tLMGrpChargeSchema.setPayCountTo(0);          //��ֹ���Ѵ���
  tLMGrpChargeSchema.setChargeType("11");  	//����������
  tLMGrpChargeSchema.setChargeRate(request.getParameter("ChargeRate"));		//�����ѱ���

    try
    {		   
       tVData.add(tLCGrpPolSchema);
       tVData.add(tLMGrpChargeSchema);
       tVData.add(tG);		   
       tBusinessDelegate.submitData(tVData,strOperate,busiName);
    }    
    catch(Exception ex)
    {
      Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
      loggerDebug("LMChargeRateSave","aaaa"+ex.toString());
      FlagStr = "Fail";
    }  
 	if (FlagStr=="")
	{
	    tError =tBusinessDelegate.getCErrors();
	    if (!tError.needDealError())
	    {                          
	      Content ="����ɹ���";
	    	FlagStr = "Succ";
	    	tVData.clear();
			tVData =tBusinessDelegate.getResult();
	    }
	    else                                                                           
	    {
	    	Content = "����ʧ�ܣ�ԭ����:" + tError.getFirstError();
	    	FlagStr = "Fail";
	    }
	}      
  %>
  <%=Content%> 		

   
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
   
   
   
 
