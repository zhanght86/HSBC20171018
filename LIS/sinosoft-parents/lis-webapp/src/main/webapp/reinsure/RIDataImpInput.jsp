<%@include file="/i18n/language.jsp"%>
<html>
<%
//name :LRFeeRateBatchInput.jsp
//function :LRFeeRateBatchInput.jsp
//Creator :
//date :2008-06-04
%>
	<%@page contentType="text/html;charset=GBK" %>
	<!--用户校验类-->
	<%@page import = "com.sinosoft.utility.*"%>
	<%@page import = "com.sinosoft.lis.schema.*"%>
	<%@page import = "com.sinosoft.lis.vschema.*"%>
	<%@page import = "com.sinosoft.lis.reinsure.*"%>
	<%@include file="../common/jsp/UsrCheck.jsp"%>
	
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT><SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT><SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css><LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>

<SCRIPT src = "./RIDataImpInput.js"></SCRIPT> 
<%@include file="./RIDataImpInit.jsp"%>
</head>
<body  onload="initElementtype();initForm();" >
 <form action="" name=fmImport target="fraSubmit" method=post ENCTYPE="multipart/form-data"> 
  	<Table class= common>
  			
  	 		<TR class= common>
  	 			<TD class= title5>
选择导入的文件：
			    </TD>     
			    <TD>
			      <Input type="file" name=FileName class=common >
			      <INPUT VALUE="导  入" class=cssButton TYPE=button onclick="FeeRateTableImp();">
			    </TD>
			   
			  </TR>
		</Table>
	</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
