<%
//�������ƣ�PEdorTypeIPSubmit.jsp
//�����ܣ�
//�������ڣ�2007-04-16
//������  ��tp
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.service.*" %>
<%
 //������Ϣ������У�鴦��
  //�������
  //����������Ϣ
  
  
  CErrors tError = null;
  String FlagStr = "";
  String Content = "";
  String transact = "";
  
  //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
  	GlobalInput tGlobalInput = new GlobalInput();
	tGlobalInput = (GlobalInput)session.getValue("GI");

	transact = request.getParameter("fmtransact");
	String edorAcceptNo = request.getParameter("EdorAcceptNo");
	String edorNo = request.getParameter("EdorNo");
	String edorType = request.getParameter("EdorType");
	String contNo = request.getParameter("ContNo");
    LPPerInvestPlanSet tLPPerInvestPlanSet = new LPPerInvestPlanSet();  
    boolean ret = true;
	if(transact.equals("EDORITEM|INPUT")){
  		int lineCount = 0;
		String tInsuAccNo[] = request.getParameterValues("InsuAccGrid5");
		String tPayPlanCode[] = request.getParameterValues("InsuAccGrid3");
		String tInvestMoney[] = request.getParameterValues("InsuAccGrid13");
		String RiskCode[] = request.getParameterValues("InsuAccGrid1");
		String tChk[] = request.getParameterValues("InpInsuAccGridChk");
		String tPolNo[] = request.getParameterValues("InsuAccGrid9");
		lineCount = tChk.length; //����
		for(int i=0;i<lineCount;i++){
		if (tChk[i].equals("1")) {
           LPPerInvestPlanSchema tLPPerInvestPlanSchema = new LPPerInvestPlanSchema();
           tLPPerInvestPlanSchema.setEdorNo(edorNo);
           tLPPerInvestPlanSchema.setEdorType(edorType);
           tLPPerInvestPlanSchema.setContNo(contNo);
           tLPPerInvestPlanSchema.setPolNo(tPolNo[i]);
           tLPPerInvestPlanSchema.setInputMode("1");
           tLPPerInvestPlanSchema.setGrpContNo("00000000000000000000");
           tLPPerInvestPlanSchema.setGrpPolNo("00000000000000000000");
           tLPPerInvestPlanSchema.setInsuredNo(tInsuAccNo[i]);
           tLPPerInvestPlanSchema.setRiskCode(RiskCode[i]);
	       tLPPerInvestPlanSchema.setInsuAccNo(tInsuAccNo[i]);
           tLPPerInvestPlanSchema.setPayPlanCode( tPayPlanCode[i]);
           if(!tInvestMoney[i].equals("")) {
                  if(!PubFun.isNumeric(tInvestMoney[i]))
                  {
                	  ret = false;
                    FlagStr="Fail";
                    Content = " ����ʧ�ܣ�ԭ����:׷�ӽ���¼���ʽ����";
                  }else{
                    tLPPerInvestPlanSchema.setInvestMoney(tInvestMoney[i]);
                  }
            }else{
                  tLPPerInvestPlanSchema.setInvestMoney(0);
            }

			tLPPerInvestPlanSet.add(tLPPerInvestPlanSchema);
		}
	}
}
	
if(transact.equals("EDORITEM|DELETE")){
  		int lineCount = 0;
		String tInsuAccNo[] = request.getParameterValues("PolGridb5");
		String tPayPlanCode[] = request.getParameterValues("PolGridb3");
		String tPolNo[] = request.getParameterValues("PolGridb9");
		String tChk[] = request.getParameterValues("InpPolGridbChk");
		lineCount = tChk.length; //����
		for(int i=0;i<lineCount;i++){
		if (tChk[i].equals("1")) {
           LPPerInvestPlanSchema tLPPerInvestPlanSchema = new LPPerInvestPlanSchema();
           tLPPerInvestPlanSchema.setPolNo(tPolNo[i]);
	       tLPPerInvestPlanSchema.setInsuAccNo(tInsuAccNo[i]);
           tLPPerInvestPlanSchema.setPayPlanCode(tPayPlanCode[i]);
		   tLPPerInvestPlanSet.add(tLPPerInvestPlanSchema);
		}
	}
}
	if(ret){
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		tLPEdorItemSchema.setEdorAcceptNo(edorAcceptNo);
		tLPEdorItemSchema.setEdorNo(edorNo);
		tLPEdorItemSchema.setContNo(contNo);
		tLPEdorItemSchema.setEdorType(edorType);
		VData tVData = new VData();

		try{		   
			tVData.add(tGlobalInput);
			tVData.add(tLPEdorItemSchema);
			tVData.add(tLPPerInvestPlanSet);
		
		 	String busiName="PEdorIPDetailBL";
		 	BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
  		 	tBusinessDelegate.submitData(tVData,transact,busiName);
  		 	tError = tBusinessDelegate.getCErrors(); 
  		 	if (tError.needDealError()){
  	  			Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
  	  			FlagStr = "Fail";
  	  	 	}
		} catch(Exception ex) {
	      	Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
	      	FlagStr = "Fail";
		}
	}

  	if (!FlagStr.equals("Fail")){
      		Content = " ����ɹ�! ";
      		FlagStr = "Succ";

  	}
 

%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

