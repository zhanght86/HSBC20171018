<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//程序名称：YBTFinfeeSureInput.jsp
//程序功能：邮保通对帐
//创建日期：
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>
<html>
<%
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="YBTFinfeeSure.js"></SCRIPT>
  <%@include file="YBTFinfeeInit.jsp"%>
  <title>邮保通对帐</title>
</head>
<body onLoad="initForm();">
  <form ENCTYPE="multipart/form-data"  method=post  name=fm id="fm" target="fraSubmit" action="./YBTFinfeeSureSave.jsp">
  <table class= common border=0 width=100%>
    <tr>
            <td class=common>
              <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,maxbox);">
            </td>
			<td class= titleImg align= center>请输入交费金额、上传结帐清单：</td>
		</tr>
	</table>
  <div class="maxbox1" id="maxbox">
  <TABLE class=common align=center>
    <TR class=common>
      <TD class=title5>
        交费总金额
      </TD>
      <TD class=input5>
        <input class="wid" name=FeeSum id=FeeSum><font color=red>*</font>
      </TD>
      <TD class=title5>
        收款银行
      </TD>
      <TD class=input5>
        <input class="codeno" name=BankCode id="BankCode" style="background: url(../common/images/select--bg_03.png) no-repeat center right;" onMouseDown="checkCom();return showCodeList('FINAbank',[this,BankCodeName],[0,1],null,checkComCode,1,1);" onDblClick="checkCom();return showCodeList('FINAbank',[this,BankCodeName],[0,1],null,checkComCode,1,1);" onKeyUp="checkCom();return showCodeListKey('FINAbank',[this,BankCodeName],[0,1],null,checkComCode,1,1);" readonly>
		<input class=codename name=BankCodeName id=BankCodeName readonly><font color=red>*</font>
      </TD>
    </TR>
    <TR class=common>
      <TD class=title5>
	        文件地址：
	    </TD>
	    <TD  class=input5>
	      <Input  class = "wid1" type="file" name=FileName id="FileName">
      </TD>	 
    </TR>
  </table>
  
  <!--<table>
    <TR>
      <TD><IMG src="../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divContext);">
      </TD>
      <TD class=titleImg>请上传结帐清单</TD>
    </TR>
  </TABLE>
  <Div id="divContext" class="maxbox" style="display:''">
    <table class=common>
    <TR class=common>
      <TD class = title>
	        文件地址：
	    </TD>
	    <TD  >
	      <Input  class = "wid1" type="file" name=FileName>
      </TD>	 
    </TR>
  </table>-->
  </Div>
  <input class=cssButton value="核销应收保费" name=conf id=conf  type=button onClick="submitForm();">
  

  </form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
<script>
function checkCom()
{
	var Com = '<%=tG.ComCode%>' ;
	var bankCom="" ;
	if(Com.length >=4)
	{
		bankCom =Com.substr(0,4);
  }
	else
	{
		bankCom=Com;		
	}
	checkComCode="1 and comcode like #"+bankCom+"%#";
}
</script>
