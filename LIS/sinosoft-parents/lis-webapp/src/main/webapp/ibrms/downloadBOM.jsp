<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�download.jsp
//�����ܣ�BOM����
//�������ڣ�2008-8-26
//������  ��
//���¼�¼��  
%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
	<SCRIPT src="downloadBOM.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
   <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="downloadBOMInit.jsp"%>


</head>
<body  onload="initForm();" >

<form action="downloadBOMSave.jsp" method=post name=fm id=fm target="fraSubmit">
      <Table>
	<TR>
		<TD class=input style="display: none">
         <Input class="common" name=Action>
       </TD>	  
		<TD class=common><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,divQueryCondition);"></TD>
		<TD class=titleImg>BOM�����ѯ</TD>
	</TR>
</Table>
<Div  id= "divQueryCondition" style= "display: ''" class="maxbox">
<table class=common>
	<TR class=common>
		<TD class=title5>BOMӢ����</TD>
		<TD class=input5><Input class="wid" class=common name=eName id=eName
			elementtype=nacessary verify="BOMӢ����|NOTNULL" ></TD>
		<TD class=title5>BOM������</TD>
		<TD class=input5><Input class="wid" class=common name=cName id=cName
			elementtype=nacessary verify="BOM������|NOTNULL"></TD>

	</TR>
	<TR class=common>
		<TD class=title5>BOMҵ��ģ��</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=Business id=Business class=codeno
			ondblclick="return showCodeList('IbrmsBusiness',[this,BusinessName],[0,1]);"
            onclick="return showCodeList('IbrmsBusiness',[this,BusinessName],[0,1]);"
			onkeyup="return showCodeListKey('IbrmsBusiness',[this,BusinessName],[0,1]);"><input
			class=codename name="BusinessName" id="BusinessName" readonly=true></TD>
		<TD class=title5>BOM��Ч��</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=Valid id=Valid class=codeno
			ondblclick="return showCodeList('IbrmsValid',[this,ValidName],[0,1]);"
            onclick="return showCodeList('IbrmsValid',[this,ValidName],[0,1]);"
			onkeyup="return showCodeListKey('IbrmsValid',[this,ValidName],[0,1]);"><input
			class=codename name="ValidName" id="ValidName" readonly=true></TD>
	</TR>
</Table>
</Div>
  <!--<p>
  <Div id= divCmdButton style="display: ''">-->
     <!--<INPUT VALUE="��ѯBOM" TYPE=button class="cssButton" onclick="queryClick()">
     <INPUT VALUE="����JAVA�ļ�" TYPE=button class="cssButton" onclick="creatPartClick()">
	<INPUT VALUE="����ȫ��JAVA�ļ�" TYPE=button class="cssButton" onclick="creatAllClick()">--><br>
     <a href="javascript:void(0);" class="button" onClick="queryClick();">��ѯBOM</a>
     <a href="javascript:void(0);" class="button" onClick="creatPartClick();">����JAVA�ļ�</a>
     <a href="javascript:void(0);" class="button" onClick="creatAllClick();">����ȫ��JAVA�ļ�</a>
<!--  </Div>-->

  <Div  id= "divQueryGrp" style= "display: ''">
    <table>
      <td class= titleImg>
    	 BOM�б�
      </td>
    </table>

     <table  class= common>
		<tr class="common">
			<td><input type=hidden name=creatFlag></td>
		</tr>
        <tr>
    	  	<td text-align: left colSpan=1>
	         <span id="spanQueryGrpGrid" ></span>
		</td>
		<TD class=input style="display: none">					
			
		</TD>
	</tr>
     </table>

		<!--<INPUT VALUE=" BOM �� ��   " TYPE=button class="cssButton" onclick="downClick()">-->	<br>
        <a href="javascript:void(0);" class="button" onClick="downClick();">BOM ����</a>
</div>
<br/><br/><br/>
</div>
</form>

	

 <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
