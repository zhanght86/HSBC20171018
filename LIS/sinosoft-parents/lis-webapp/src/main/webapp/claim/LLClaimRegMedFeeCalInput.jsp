

<html>
 <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
 <!--�û�У����-->
 <%@page import = "com.sinosoft.utility.*"%>
 <%@page import = "com.sinosoft.lis.schema.*"%>
 <%@page import = "com.sinosoft.lis.vschema.*"%>
 <%@include file="../common/jsp/UsrCheck.jsp"%>
 
<head >
<%
//=============================================================BGN
	  GlobalInput tG = new GlobalInput();
	  tG = (GlobalInput)session.getValue("GI");
	  
	  String tClmNo = request.getParameter("claimNo");	//�ⰸ��
	  String tCaseNo = request.getParameter("caseNo");  
	  String tCustNo = request.getParameter("custNo");  //�ͻ���
//=============================================================END
%>
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="LLClaimRegMedFeeCal.js"></SCRIPT>
    <%@include file="LLClaimRegMedFeeCalInit.jsp"%>
</head>

<body  onload="initForm();"> 	
<form action="" method=post name=fm id=fm target="fraSubmit">   	

	<!--=========================================================================
	    �޸�״̬����ʼ
	    �޸�ԭ������Ϊ���ü�����Ϣ
	    �� �� �ˣ�����
	    �޸����ڣ�2005.05.13
	    =========================================================================
	-->
                      
    <!--���ü�����Ϣ-->
        

            
	<table>
	     <tr><td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;"></td>
	          <td class= titleImg>���ü�����Ϣ</td>
	     </tr>
	</table>
  
        

                           
    <Div  id= "MedFeeCal" style= "display:''">                                
		<Table  class= common>
		    <tr><td text-align: left colSpan=1>
		        <span id="spanMedFeeCalGrid"></span> 
		    </td></tr>
		</Table>
	</div>
        

        

                
	<!--=========================================================================
	    �޸�״̬������
	    �޸�ԭ������Ϊ���ü�����Ϣ
	    �� �� �ˣ�����
	    �޸����ڣ�2005.05.13
	    =========================================================================
	-->
                              
<input type=hidden name=claimNo value=''>
<input type=hidden name=caseNo value=''>
<input type=hidden name=custNo value=''>
<input type=hidden id="fmtransact" name="fmtransact">
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
