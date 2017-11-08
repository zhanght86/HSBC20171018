<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%	


	String FlagStr="Succ";
	String Content="保存成功!";
	String Result=""; 
	GlobalInput tG = (GlobalInput)session.getValue("GI");
	String tEdorNo = request.getParameter("EdorNo");
	String tEdorType = request.getParameter("EdorType");
	String transact = request.getParameter("fmtransact");

    if (transact.equals("INSERT")||transact.equals("UPDATE"))
    {
		String tContNo = request.getParameter("ContNo");
		String tInsuredNo = request.getParameter("InsuredNo");
   		String tPayMode = request.getParameter("PayMode"); //给付方式 对应BNF的Remark
   		String tDrawer = request.getParameter("Drawer"); //领取人姓名,对应BNF的Name
    	String tIDNo =  request.getParameter("DrawerIDNo"); //领取人的身份证号,对应BNF的IDNo
    	String tBankCode = request.getParameter("BankCode"); //领取人的开户银行编码,对应BNF的BankCode
    	String tBankAccNo = request.getParameter("BankAccNo"); //领取人的开户银行账户,对应BNF的BankAccNo
    	String tAccName = request.getParameter("AccName"); //领取人的开户银行户名,对应BNF的AccName
    	LPBnfSchema tLPBnfSchema = new LPBnfSchema();
        tLPBnfSchema.setEdorNo(tEdorNo);
        tLPBnfSchema.setEdorType(tEdorType);
        tLPBnfSchema.setContNo(tContNo);
        tLPBnfSchema.setPolNo("000000");
        tLPBnfSchema.setInsuredNo(tInsuredNo);
        tLPBnfSchema.setBnfType("1");
        tLPBnfSchema.setBnfNo(1);
        tLPBnfSchema.setBnfGrade("1");
        tLPBnfSchema.setRemark(tPayMode); //给付方式
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
        //数据提交
        PubSubmit tSubmit = new PubSubmit();
        if (!tSubmit.submitData(tVData, "")) {
        	Content = " 保存失败，原因是:" + tSubmit.mErrors.getFirstError();
        	FlagStr = "Fail";   	
        }
    } else if (transact.equals("DELETE")){
    	Content = "删除成功";
    	ExeSQL myExeSQL = new ExeSQL();
		String tContNo = request.getParameter("ContNo");
		String tInsuredNo = request.getParameter("InsuredNo");
		String tDelSql = "DELETE FROM LPBnf WHERE EdorNo = '"+tEdorNo+"' AND EdorType = '"+tEdorType+"' AND ContNo = '"+tContNo+"' AND InsuredNo = '"+tInsuredNo+"'";
		if(!myExeSQL.execUpdateSQL(tDelSql)){
			Content = "删除失败";
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