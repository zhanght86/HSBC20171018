<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author ����ǿ <XinYQ@sinosoft.com.cn>
 * @version 1.00
 * @date 2005-11-01
 ******************************************************************************/
%>

<%@ page import="com.sinosoft.lis.pubfun.GlobalInput"%>

<%@ include file = "../common/jsp/UsrCheck.jsp" %>

<%
	String tContNo = "";
	tContNo = request.getParameter("ContNo");
	session.putValue("ContNo", tContNo);
    //�����¸���
    GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput) session.getValue("GI");
%>


<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <script>
  	var ContNo = "<%=tContNo%>";
  	var ttContNo = "<%=request.getParameter("ContNo")%>";
  	var operator = "<%=tGI.Operator%>";   //��¼����Ա
  	var manageCom = "<%=tGI.ManageCom%>"; //��¼�������
  	var comcode = "<%=tGI.ComCode%>";     //��¼��½����
  </script>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <script src="../common/javascript/VerifyInput.js"></script>
  <SCRIPT src="./EdorUWImageQuery.js"></SCRIPT>
  <link href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
  <link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="ImageQueryInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form action="../common/EasyScanQuery/EasyScanQuery.jsp" method=post id="fm" name=fm target="fraPic">

	<div id="DivLCCont" class="maxbox1" >
		<table class="common" id="table2">
			<tr CLASS="common">
				<td CLASS="title5" nowrap>������</td>
				<td CLASS="input5" COLSPAN="1"><input id="ContNo" NAME="ContNo" VALUE CLASS="wid readonly" readonly= TABINDEX="-1" MAXLENGTH="14"></td>
        <td class=title5>Ӱ�����</td>
	    	<td class=input5>
         <Input class="codeno" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" id="subtype" name="subtype" CodeData="0|^UN011|�˱�֪ͨ��^UN003|���֪ͨ��" ondblclick="showCodeListEx('subtype',[this,fm.subtypname],[0,1]);" onkeyup="showCodeListEx('subtype',[this,fm.subtypname],[0,1]);"><Input class="codename" id="subtypname" name="subtypname" readonly elementtype=nacessary>
         </td>
			</tr>
		</table>
  </div>

	</DIV>
	  <INPUT class=cssButton VALUE="��   ѯ" TYPE=button onclick="QueryImage();">

<hr class="line">
<!--Ӱ����Ϣ-->
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol2);">
    		</td>
    		<td class= titleImg>
    			 Ӱ��������Ϣ:
    		</td>
    	    </tr>
        </table>
         <Div  id= "divLCPol2" >
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanImageGrid">
  					</span>
  			  	</td>
  			</tr>
    	</table>
         	<div  id= "divTurnPage" align=center style= "display: '' ">
          <input class=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();">
          <input class=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();">
          <input class=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();">
          <input class=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();">
        </div>
    </Div>


     <div  id= "HiddenValue" style="display:none;float: right">
			<input type="hidden" id="DocID" name="DocID" value= "">
			<input type="hidden" id="QueryType" name="QueryType"  value="0">
    </div>


  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>

</body>
</html>
