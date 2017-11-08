

<html>
 <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
 <!--用户校验类-->
 <%@page import = "com.sinosoft.utility.*"%>
 <%@page import = "com.sinosoft.lis.schema.*"%>
 <%@page import = "com.sinosoft.lis.vschema.*"%>
 <%@include file="../common/jsp/UsrCheck.jsp"%>
 
<head >
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
    <SCRIPT src="LLClaimRegMedFeeCal.js"></SCRIPT>
    <%@include file="LLClaimRegMedFeeCalInit.jsp"%>
</head>

<body  onload="initForm();"> 	
<form action="" method=post name=fm target="fraSubmit">   	

	<!--=========================================================================
	    修改状态：开始
	    修改原因：以下为费用计算信息
	    修 改 人：续涛
	    修改日期：2005.05.13
	    =========================================================================
	-->
                      
    <!--费用计算信息-->
        
    <hr>
            
	<table>
	     <tr><td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;"></td>
	          <td class= titleImg>费用计算信息</td>
	     </tr>
	</table>
  
        

                           
    <Div  id= "MedFeeCal" style= "display:''">                                
		<Table  class= common>
		    <tr><td text-align: left colSpan=1>
		        <span id="spanMedFeeCalGrid"></span> 
		    </td></tr>
		</Table>
	</div>
        
    <hr>
        

                
	<!--=========================================================================
	    修改状态：结束
	    修改原因：以上为费用计算信息
	    修 改 人：续涛
	    修改日期：2005.05.13
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
