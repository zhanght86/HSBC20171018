<html>
<%
//�������ƣ�����ڼ����
//�����ܣ�
//�������ڣ�2008-8-5
//������  �����
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--�û�У����-->
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head >
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="AccountantPeriodQuery.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="AccountantPeriodQueryInit.jsp"%>
</head>

<body  onload="initForm();" >
<form action="./AccountantPeriodQuerySave.jsp" method=post name=fm id=fm target="fraSubmit">
		<%@include file="../common/jsp/InputButton.jsp"%>
  	<table>
    <tr class=common>
    <td class=common>
    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAccountantPeriodQuery);">
    </IMG>
    <td class=titleImg>
      ��ѯ����
      </td>
    </tr>
   
  </table>
   <Div id= "divAccountantPeriodQuery" style= "display: ''">
		 <div class="maxbox">
   		<Table class= common>
		<TR  class= common>
			<TD  class= title5>���</TD>
			<TD  class= input5><Input class="wid" class=common name=Year id=Year elementtype=nacessary verify="���|NOTNULL&INT&len=4" >(��:2008)</TD>
			<TD  class= title5>�¶�</TD>
			<TD  class= input5><Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class=code name= Month id= Month verify="�¶�ֹ��|NOTNULL"   CodeData="0|^01|1��^02|2��^03|3��^04|4��^05|5��^06|6��^07|7��^08|8��^09|9��^10|10��^11|11��^12|12��" ondblClick="showCodeListEx('Month',[this],[0,1],null,null,null,[1]);" onMouseDown="showCodeListEx('Month',[this],[0,1],null,null,null,[1]);" onkeyup="showCodeListKeyEx('Month',[this],[0,1],null,null,null,[1]);" readonly=true>(��:08)</TD>
		</TR>
  		<TR  class= common>
    		<TD class="title5">����Ա</TD>
          <TD class="input5">
          <input class="wid" class="readonly" readonly name="Operator" id="Operator"></TD>
          <TD class= title5>
          	״̬
        	</TD>
        	<TD class= input5>
          	<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name= State id= State verify="״̬|NOTNULL" CodeData="0|^0|δ����^1|����" ondblClick="showCodeListEx('CertifyStateList',[this,StateName],[0,1],null,null,null,[1]);" onMouseDown="showCodeListEx('CertifyStateList',[this,StateName],[0,1],null,null,null,[1]);" onkeyup="showCodeListKeyEx('CertifyStateList',[this,StateName],[0,1],null,null,null,[1]);" readonly=true><input class=codename name=StateName id=StateName readonly=true>
         	</TD>
  		</TR>
    </Table>

		<input class="cssButton" type=button value="��  ѯ" onclick="submitForm()">
        <!--<a href="javascript:void(0);" class="button" onClick="submitForm();">��    ѯ</a>--> 
		</Div>
	</div>
	
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAccountantPeriodGrid);">
    		</td>
    		<td class= titleImg>
    			 �嵥�б�
    		</td>
    	</tr>
    </table>
  	<Div  id= "divAccountantPeriodGrid" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					<span id="spanAccountantPeriodGrid" >
  					</span>
  			  	</td>
  			</tr>
    	</table>
       
      <Div  id= "divPage" align=center style= "display: none ">
      <INPUT CLASS="cssButton90" VALUE="��ҳ" TYPE=button onclick="turnPage.firstPage();">
      <INPUT CLASS="cssButton91" VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();">
      <INPUT CLASS="cssButton92" VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();">
      <INPUT CLASS="cssButton93" VALUE="βҳ" TYPE=button onclick="turnPage.lastPage();">
      </Div>
  	</div><!--<a href="javascript:void(0);" class="button" onClick="ReturnData();">��    ��</a>--><input class="cssButton" type=button value="��  ��" onclick="ReturnData()">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
