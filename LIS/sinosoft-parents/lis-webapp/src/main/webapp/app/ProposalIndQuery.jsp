<%
//�������ƣ�ProposalIndQuery.jsp
//�����ܣ�����״̬�켣��ѯ
//�������ڣ�2007-03-26 10:02
//������  ��Fuqx
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
    GlobalInput tG1 = (GlobalInput)session.getValue("GI");
    String tComCode =tG1.ComCode;
%>
<script>
	<!--ѡ�������ֻ�ܲ�ѯ������¼�����-->
	var codeSql = "1 and length(trim(code))>=6 and code like #"+<%=tComCode%>+"%#";
</script>
<script>
	var comCode= <%=tComCode%>;
</script>
<html>    
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT>
  <SCRIPT src="./ProposalIndQuery.js"></SCRIPT>   
  <%@include file="./ProposalIndQueryInit.jsp"%>   
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>����������̲�ѯ</title>
</head>      
 
<body  onload="initForm();" >
<form action="./ProposalIndQuerySave.jsp" method=post name=fm id="fm" target="fraSubmit">
	<table class= common border=0 width=100%>
  <tr>
    <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
        </td>
	<td class= titleImg align= center>�������ѯ������</td>
	</tr>
	</table>
  <!-- <form method=post name=fm id="fm"> -->
    <div class="maxbox">
    <Div  id= "divFCDay" style= "display: ''">
   <Table class= common>
     <TR class= common> 
          <TD  class= title5>�������</TD>
				  <TD  class= input5>
					  <Input style="background:url(../common/images/select--bg_03.png)     no-repeat right center" class="codeno" name=ManageCom id="ManageCom" onclick="showCodeList('station',[this,ManageComName],[0,1],null,codeSql,'1');"  ondblclick="showCodeList('station',[this,ManageComName],[0,1],null,codeSql,'1');"  onkeyup="showCodeListKey('station',[this,ManageComName],[0,1],null,codeSql,'1');"><input class=codename name=ManageComName id="ManageComName" readonly=true>
				  </TD>
          <TD  class= title5>
            ӡˢ��
          </TD>
          <TD  class= input5><Input class='common wid' name=PrtNo id="PrtNo"></TD>
      </TR>
     <TR class= common>
          <TD  class= title5>
            ������
          </TD>
          <TD  class= input5><Input class='common wid' name=ContNo id="ContNo"></TD>
           <TD  class= title5>
            Ͷ��������
          </TD>
          <TD  class= input5><Input class='common wid' name=AppntName id="AppntName"></TD>
      </TR>
     <TR class= common>
          <TD  class= title5>
            ������������
          </TD>
          <TD  class= input5><Input class='common wid' name=InsuredName id="InsuredName"></TD>
          <TD  class= title5>
            �����˿ͻ�����
          </TD>
          <TD  class= input5><Input class='common wid' name=InsuredNo id="InsuredNo"></TD>
     </TR>
	<TR class=common>
		<TD class=title5>��ʼ����</TD>
		<TD class= input5>
      <Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="��ʼ����|DATE" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
		</TD>
		<TD class=title5>��������</TD>
		<TD class= input5>
      <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="��������|DATE" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
		</TD>
	</TR>
   	</Table> 
    </Div> 
    </div>
    <br>
    <!--������-->
    <a href="javascript:void(0)" class=button onclick="easyQuery();">��  ѯ</a>
    <a href="javascript:void(0)" class=button onclick="easyPrint();">��ӡ����</a>
    <!-- <INPUT VALUE="��    ѯ" class= cssButton TYPE=button onclick="easyQuery()"> 	
    <INPUT VALUE="��ӡ����" class= cssButton TYPE=button onclick="easyPrint()"> --> 	

    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCodeGrid);">
    		</td>
    		<td class= titleImg>
    			 ����������������
    		</td>
    	</tr>
    </table>
  	<Div  id= "divCodeGrid" style= "display: ''"  align = center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
									<span id="spanCodeGrid" >
									</span> 
		  				</td>
					</tr>
    		</table> 
      	<INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onclick="turnPage.firstPage();">
		    <INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="turnPage.previousPage();">
		    <INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="turnPage.nextPage();">
		    <INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="turnPage.lastPage();">				
  	</div>
    
  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
