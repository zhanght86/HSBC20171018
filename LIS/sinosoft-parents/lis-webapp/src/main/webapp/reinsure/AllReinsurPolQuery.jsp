<%@include file="/i18n/language.jsp"%>

<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
   GlobalInput tG = new GlobalInput();
   tG=(GlobalInput)session.getAttribute("GI"); //���ҳ��ؼ��ĳ�ʼ����
   System.out.println("�������-----"+tG.ComCode);
   
   	String tDisplay = "";
	try
	{
		tDisplay = request.getParameter("display");
	}
	catch( Exception e )
	{
		tDisplay = "";
	}
%>   

<script>
  var comCode = <%=tG.ComCode%>
</script>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT><SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT><SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT>
  <SCRIPT src="AllReinsurPolQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css><LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="AllReinsurPolQueryInit.jsp"%>
  
  <title>�ֱ�������ѯ</title>
</head>
<body  onload="initForm();" >
  <form method=post name=fm target="fraSubmit">
    <!-- ������Ϣ���� -->
    <table class= common border=0 width=100%>
    	<tr>
			<td class= titleImg align= center>������ֱ�������ѯ������</td>
		</tr>
	</table>
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title5>��������</TD>
          <TD  class= input5> <Input class= common name=PolNo > </TD>
          <TD  class= title5>���屣������</TD>
          <TD  class= input5> <Input class= "code" name=GrpPolNo CodeData="0|^00000000000000000000|�Ǽ��嵥�µĸ��˵�" ondblclick="showCodeListEx('GrpPolNo',[this],[0]);" onkeyup="showCodeListKeyEx('GrpPolNo',[this],[0]);"> </TD>
          <TD  class= title5>���ֱ���</TD>
          <TD  class= input5> <Input class="code" name=RiskCode ondblclick="return showCodeList('RiskCode',[this]);" onkeyup="return showCodeListKey('RiskCode',[this]);"></TD>

        </TR>
        <TR  class= common>
          <TD  class= title5>�ֱ�����</TD>
          <TD  class= input5> <Input class= common name=CessStart ></TD>
          <TD  class= title5>�ٱ���Ŀ</TD>
          <TD  class= input5> <Input class= code name=ReinsurItem CodeData="0|^L|�����ֱ�|^C|��ҵ�ֱ�" ondblclick="showCodeListEx('ReinsurItem',[this],[0]);" onkeyup="showCodeListKeyEx('ReinsurItem',[this],[0]);"> </TD>
          <TD  class= title5>Э������</TD>
          <TD  class= input5> <Input class= code name=ProtItem CodeData="0|^T|��ͬ�ֱ�|^F|��ʱ�ֱ�" ondblclick="showCodeListEx('ProtItem',[this],[0]);" onkeyup="showCodeListKeyEx('ProtItem',[this],[0]);"> </TD>
        </TR>
       <TR  class= common>
          <TD  class= title5>����������</TD>
          <TD  class= input5><Input class= common name=InsuredName ></TD>
          <TD  class= title5>�����˿ͻ���</TD>
          <TD  class= input5> <Input class= common name=InsuredNo > </TD>
          <TD  class= title5>�����˳�������</TD>
          <TD  class= input5><Input class= common name=InsuredBirthday ></TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>�������</TD>
          <TD  class= input5><Input class= common name=InsuredYear ></TD>
        </TR>
    </table>
          <INPUT VALUE="��ѯ"             class = common TYPE=button onclick="easyQueryClick();"> 
          <!--INPUT VALUE="������ϸ" TYPE=button onclick="getQueryDetail();"--> 					
          <INPUT VALUE="������ȫ������Ϣ" class = common TYPE=button onclick="PolClick();">
          <INPUT VALUE="����" name=Return class = common TYPE=button STYLE="display:none" onclick="returnParent();"> 					
          <INPUT VALUE="��ӡ"             class = common TYPE=button onclick="easyQueryPrint(2,'PolGrid','turnPage');">
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
������Ϣ
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLCPol1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td style="text-align:left;" colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>

      <INPUT VALUE="��ҳ" class = common TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT VALUE="��һҳ" class = common TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT VALUE="��һҳ" class = common TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT VALUE="βҳ" class = common TYPE=button onclick="turnPage.lastPage();">				
  	</div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
