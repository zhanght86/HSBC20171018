<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%	


	String FlagStr="Succ";
	String Content="����ɹ�!";
	String Result=""; 
	GlobalInput tG = (GlobalInput)session.getValue("GI");
	String tEdorNo = request.getParameter("EdorNo");
	String tEdorType = request.getParameter("EdorType");
	String transact = request.getParameter("fmtransact");

    if (transact.equals("INSERT")||transact.equals("UPDATE"))
    {
		String tContNo = request.getParameter("ContNo");
		String tInsuredNo = request.getParameter("InsuredNo");
   		String tPayMode = request.getParameter("PayMode"); //������ʽ ��ӦBNF��Remark
   		String tDrawer = request.getParameter("Drawer"); //��ȡ������,��ӦBNF��Name
    	String tIDNo =  request.getParameter("DrawerIDNo"); //��ȡ�˵����֤��,��ӦBNF��IDNo
    	String tBankCode = request.getParameter("BankCode"); //��ȡ�˵Ŀ������б���,��ӦBNF��BankCode
    	String tBankAccNo = request.getParameter("BankAccNo"); //��ȡ�˵Ŀ��������˻�,��ӦBNF��BankAccNo
    	String tAccName = request.getParameter("AccName"); //��ȡ�˵Ŀ������л���,��ӦBNF��AccName
    	LPBnfSchema tLPBnfSchema = new LPBnfSchema();
        tLPBnfSchema.setEdorNo(tEdorNo);
        tLPBnfSchema.setEdorType(tEdorType);
        tLPBnfSchema.setContNo(tContNo);
        tLPBnfSchema.setPolNo("000000");
        tLPBnfSchema.setInsuredNo(tInsuredNo);
        tLPBnfSchema.setBnfType("1");
        tLPBnfSchema.setBnfNo(1);
        tLPBnfSchema.setBnfGrade("1");
        tLPBnfSchema.setRemark(tPayMode); //������ʽ
        tLPBnfSchema.setName(tDrawer);
        tLPBnfSchema.setIDNo(tIDNo);
        tLPBnfSchema.setBankCode(tBankCode);
        tLPBnfSchema.setBankAccNo(tBankAccNo);
        tLPBnfSchema.setAccName(tAccName);
        tLPBnfSchema.setOperator(tG.Operator);
        tLPBnfSchema.setMakeDate(PubFun.getCurrentDate());
        tLPBnfSchema.setMakeTime(PubFun.getCurrentTime());
        tLPBnfSchema.setModifyDate(PubFun.getCurrentDate());
        tLPBnfSchema.setModifyTime(PubFun.getCurrentTime());
        MMap tMap = new MMap();
        tMap.put(tLPBnfSchema, "DELETE&INSERT");
        VData tVData = new VData();
        tVData.add(tMap);
        //�����ύ
        PubSubmit tSubmit = new PubSubmit();
        if (!tSubmit.submitData(tVData, "")) {
        	Content = " ����ʧ�ܣ�ԭ����:" + tSubmit.mErrors.getFirstError();
        	FlagStr = "Fail";   	
        }
    } else if (transact.equals("DELETE")){
    	Content = "ɾ���ɹ�";
    	ExeSQL myExeSQL = new ExeSQL();
		String tContNo = request.getParameter("ContNo");
		String tInsuredNo = request.getParameter("InsuredNo");
		String tDelSql = "DELETE FROM LPBnf WHERE EdorNo = '"+tEdorNo+"' AND EdorType = '"+tEdorType+"' AND ContNo = '"+tContNo+"' AND InsuredNo = '"+tInsuredNo+"'";
		if(!myExeSQL.execUpdateSQL(tDelSql)){
			Content = "ɾ��ʧ��";
    		FlagStr = "Fail";
    	}
    }
    

	
%>                                   

<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>","<%=Result%>");
	//parent.fraInterface.window.location="SubAccPay.jsp";
</script>
</html>