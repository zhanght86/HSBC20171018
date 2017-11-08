<html>
<%@page import="com.sinosoft.lis.certify.*"%>
<%
//程序名称：
//程序功能：
//创建日期：2002-10-14 10:20:43
//创建人  ：kevin
//更新记录：  更新人    更新日期     更新原因/内容

  GlobalInput tG = (GlobalInput)session.getValue("GI");
  String Branch =tG.ComCode;
	String strCertifyClass = (String)session.getAttribute("CertifyClass");
	
	session.removeAttribute("CertifyClass");
	
	if( strCertifyClass == null || strCertifyClass.equals("") ) {
		strCertifyClass = CertifyFunc.CERTIFY_CLASS_CERTIFY;
	}
	
	String strCertifyCode = "";
	String codeSql = "1";
	if(!Branch.equals("86"))
	{
		codeSql = "1  and ( managecom = '86' or managecom like '"+ Branch +"%') ";
	}

	if( strCertifyClass.equals(	CertifyFunc.CERTIFY_CLASS_CERTIFY ) ) {
		strCertifyCode = "<input class=\"code\" name=\"CertifyCode\" " +
											" ondblclick=\"return showCodeList('CertifyCode',[this],null,null,codeSql,'1',null,250);\" " +
											" onkeyup=\"return showCodeList('CertifyCode',[this],null,null,codeSql,'1',null,250);\" " +
											" verify=\"单证编码|NOTNULL\">";
	} else {
		strCertifyCode = "<input class=\"code\" name=\"CertifyCode\" " +
											" ondblclick=\"return showCodeList('CardCode', [this],null,null,codeSql,'1',null,250);\" " +
											" onkeyup=\"return showCodeList('CardCode', [this],null,null,codeSql,'1',null,250);\"" +
											" verify=\"单证编码|NOTNULL\">";
	}
	loggerDebug("CertifyPrintInput",strCertifyCode);
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="CertifyPrintInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <%@include file="CertifyPrintInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form action="./CertifyPrintSave.jsp" method=post name=fm id=fm target="fraSubmit">
    
    <table class="common">
	    <tr class="common">
		    <td class=common>
                 <img  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCertifyPrintList);"></img>
			</td>
			<td class=titleImg>请输入查询条件：</td>
		</tr>
	</table>
    <div id= "divCertifyPrintList" class=maxbox style= "display: ''">
      <table class="common">
        <tr class="common">
          <td class="title5">印刷批次号</td>
          <td class="input5" title="请查询出要提单的印刷批次号">
          	<input class="readonly wid" readonly name="PrtNo" id=PrtNo>
          	<input class="cssButton" type="button" value="?" style="padding-right:10px" onclick="query()" style="width:20"></td>
          
          <td class="title5"></td>
          <td class="input5"></td>
          
        <tr class="common">
          <td class="title5">单证编码</td>
          <td class="input5"><%= strCertifyCode %></td>     
          
          <td class="title5">最大日期</td>
          <td class="input5"><input class="coolDatePicker wid" name="MaxDate" id=MaxDate verify="最大日期|DATE&NOTNULL"></td></tr>
          
        <tr class="common">
          <td class="title5">单证价格</td>
          <td class="input5"><input class="common wid" name="CertifyPrice" id=CertifyPrice verify="单证价格|NUM&NOTNULL"></td>
          
          <td class="title5">管理机构</td>
          <td class="input5"><input class="readonly wid" readonly name="ManageCom" id=ManageCom></td>
<%
				if(Branch.equals("86"))
				{
%>				
        <tr class="common">
          <td class="title5">授权子公司印刷</td>
          <td class= input5><Input class="code" name=subManageCom id=subManageCom style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
          		ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);"></td>
          <td></td></tr>
<%
				}
%>        
        
        <tr class="common">
          <td class="title5">起始号</td>
          <td class="input5"><input class="common wid" name="StartNo" id=StartNo></td>
          
          <td class="title5">终止号</td>
          <td class="input5"><input class="common wid" name="EndNo" id=EndNo></td></tr>
          
        <tr class="common">
          <td class="title5">数量</td>
          <td class="input5"><input class="common wid" name="SumCount" id=SumCount></td>
          
          <td class="title5">状态</td>
          <td class="input5"><input class="readonly wid" readonly name="State" id=State></td></tr>

        <tr class="common" height=20><td></td></tr>
          
        <tr class="common">
          <td class="title5">厂商编码</td>
          <td class="input5"><input class="common wid" name="ComCode" id=ComCode></td>
          
          <td class="title5">印刷依据</td>
          <td class="input5"><input class="common wid" MAXLENGTH=25 name="Phone" id=Phone verify="印刷依据|NOTNULL"></td></tr>

        <tr class="common">
          <td class="title5">定单操作员</td>
          <td class="input5"><input class="readonly wid" readonly name="OperatorInput" id=OperatorInput></td>
          
          <td class="title5">联系人</td>
          <td class="input5"><input class="common wid" name="LinkMan" id=LinkMan></td></tr>
          
        <tr class="common">
          <td class="title5">定单日期</td>
          <td class="input5"><input class="coolDatePicker wid" name="InputDate" id=InputDate verify="定单日期|DATE&NOTNULL"></td>

          <td class="title5">定单操作日期</td>
          <td class="input5"><input class="readonly wid" readonly name="InputMakeDate" id=InputMakeDate></td></tr>
          
        <tr class="common" height=20><td></td></tr>
          
        <tr class="common">
          <td class="title5">提单操作员</td>
          <td class="input5"><input class="readonly wid" readonly name="OperatorGet" id=OperatorGet></td>
          
          <td class="title5">提单人</td>
          <td class="input5"><input class="common wid" name="GetMan" id=GetMan></td></tr>
          
        <tr class="common">
          <td class="title5">提单日期</td>
          <td class="input5"><input class="coolDatePicker wid" name="GetDate" id=GetDate></td>
          
          <td class="title5">提单操作日期</td>
          <td class="input5"><input class="readonly wid" readonly name="GetMakeDate" id=GetMakeDate></td></tr>
          
      </table>
	  <table class="common">
    	<tr class="common">
    		<input class="cssButton" type="button" value="定单" onclick="requestClick()" >
    		<input class="cssButton" type="button" value="提单" onclick="confirmClick()" >
    		<input class="cssButton" type="button" value="查询" onclick="queryClick()" >
		</tr>
    
      </table>
    </div>
    <input type=hidden name=hideOperate id=hideOperate value=''>
    <input type=hidden name=sql_where id=sql_where value=''>
    <input type=hidden name=CertifyClass id=CertifyClass value='<%= strCertifyClass %>'>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
<script>
	<!--选择机构：只能查询本身和下级机构-->
	var codeSql = "1";
	if('<%=Branch%>' != '86')
	{
		var codeSql = "1  and ( managecom = #86# or managecom like #"+<%=Branch%>+"%#) ";
	}
</script>
