<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2002-07-22
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@page import="java.lang.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.finfee.*"%>
<%@page import="com.sinosoft.lis.operfee.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
CErrors tError = null;
String FlagStr = "";
String Content = "";
//������
//Modify by lujun 2006-10-18 11:50 ��Ϊ����һ�δ�������˷Ѽ�¼
//Modify by liuxj 2007-12-29 ��Ϊ����һ����¼�����ǿ����޸����е���Ϣ
//NewGrpContFeeWithdrawBL tNewGrpContFeeWithdrawBL ;
try
{
	//session
	GlobalInput tGI = new GlobalInput();
	tGI=(GlobalInput)session.getValue("GI");

	//��ͬ��
	String tGrpContNo[] = request.getParameterValues("FinFeeWithDrawGrid1");
	String tPayNo[] = request.getParameterValues("FinFeeWithDrawGrid7"); //���Ѻ�
	
	//add by liuxj �����޸�������Ϣ�Ĺ��ܣ����޸�Ϊһ�δ���һ��
	String tBankCode = request.getParameter("BankCode");
	String tBankAccNo = request.getParameter("BankAccNo");
	String tRadio[] = request.getParameterValues("InpFinFeeWithDrawGridSel");  
  int index;                  
  for (index=0; index< tRadio.length;index++)
    {
        if(tRadio[index].equals("1"))
           break;
        if(tRadio[index].equals("0"))
           continue;
       }
	

			//tNewGrpContFeeWithdrawBL = new NewGrpContFeeWithdrawBL();
			
			LCGrpContSchema tLCGrpContSchema =new LCGrpContSchema();
			tLCGrpContSchema.setGrpContNo(tGrpContNo[index]);
			
			String busiName="finfeeNewGrpContFeeWithdrawBL";
			BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
			
			
			LCGrpContSet tLCGrpContSet =new LCGrpContSet();
			tLCGrpContSet.add(tLCGrpContSchema);
		
			TransferData tTransferData= new TransferData();
			tTransferData.setNameAndValue("SpecWithDraw","1");
			tTransferData.setNameAndValue("PayNo",tPayNo[index]);
			tTransferData.setNameAndValue("BankCode",tBankCode);
			tTransferData.setNameAndValue("BankAccNo",tBankAccNo);
		
			VData tVData = new VData();
			tVData.addElement(tLCGrpContSet);
			tVData.addElement(tGI);
			tVData.addElement(tTransferData);
		
			if(!tBusinessDelegate.submitData(tVData,"",busiName)){
				tError = tBusinessDelegate.getCErrors();
				Content =" �˷Ѵ���ʧ�ܣ�ԭ����:" + tError.getFirstError();
				FlagStr = "Fail";
			}else{
				Content = " �˷Ѵ������";
				FlagStr = "Succ";
			}

}
catch(Exception ex)
{
	Content = "�˷Ѵ���ʧ�ܣ�ԭ����:" + ex.toString();
	FlagStr = "Fail";
}

%>
<html>
<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
