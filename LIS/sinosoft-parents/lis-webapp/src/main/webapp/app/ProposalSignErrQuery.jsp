<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�ProposalSignErrQuery.jsp
//�����ܣ���ѯ����ǩ��ʧ��ԭ��
//�������ڣ�2007-03-09 11:42
//������  ��Fuqx
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.db.*" %>
<%@page import="com.sinosoft.lis.schema.*" %>
<%@page import="com.sinosoft.lis.vschema.*" %>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
    GlobalInput tG1 = (GlobalInput)session.getValue("GI");
    String tComCode =tG1.ComCode;
%>

<script>
	var comcode= <%=tComCode%>;
	
</script>
<script>
	<!--ѡ�������ֻ�ܲ�ѯ������¼�����-->
	var codeSql = "1  and code like #"+<%=tComCode%>+"%#";
</script>
<html>    
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT>
  
  <SCRIPT src="ProposalSignErrQuery.js"></SCRIPT>   
  <%@include file="ProposalSignErrQueryInit.jsp"%>   
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>��ѯ����ǩ��ʧ��ԭ�� </title>
</head>      
 
<body  onload="initForm();" >
<form action="./ProposalSignErrQuerySave.jsp" method=post name=fm id="fm" target="fraSubmit">
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
					  <Input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" class="codeno" name=ManageCom id="ManageCom" onclick="showCodeList('station',[this,ManageComName],[0,1],null,codeSql,'1');"  ondblclick="showCodeList('station',[this,ManageComName],[0,1],null,codeSql,'1');" onkeyup="showCodeListKey('station',[this,ManageComName],[0,1],null,codeSql,'1');"><input class=codename name=ManageComName id="ManageComName" readonly=true>
				  </TD>
          <TD  class= title5>
            ���ֱ���
          </TD>
          <TD  class= input5>
            <Input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" class="codeno" name=RiskCode id="RiskCode" onclick="return showCodeList('RiskCode',[this,RiskCodeName],[0,1]);" ondblclick="return showCodeList('RiskCode',[this,RiskCodeName],[0,1]);" onkeyup="return showCodeListKey('RiskCode',[this,RiskCodeName],[0,1]);"><input class=codename name=RiskCodeName id="RiskCodeName" readonly=true>
          </TD>
        </TR>
        <TR class= common> 
          <TD  class=title5>��������</TD>
				  <TD  class=input5>
					  <input style="background:url(../common/images/select--bg_03.png)     no-repeat right center" class="codeno" name=SaleChnl id="SaleChnl" onclick="showCodeList('SaleChnl',[this,SaleChnlName],[0,1]);" ondblclick="showCodeList('SaleChnl',[this,SaleChnlName],[0,1]);" onkeyup="showCodeListKey('SaleChnl',[this,SaleChnlName],[0,1]);"><input class=codename name=SaleChnlName id="SaleChnlName" readonly=true>
				  </TD>  
          <TD  class= title5>
            �����˱���
          </TD>
          <TD  class= input5>
          <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" MAXLENGTH=10 name=AgentCode id="AgentCode" onclick="return queryAgent(comcode);" ondblclick="return queryAgent(comcode);" onkeyup="return queryAgent2();">
          </TD>
        </TR>
        <TR class= common> 
          <TD  class= title5>
            ��������
          </TD>
          <TD  class= input5><Input class='common wid' name=InsuredName id="InsuredName"></TD>
     </TR>
     <TR  class= common>
       		<TD  class= title5 >
       		  ¼����ʼ����
       		</TD>
       		<TD  class= input5 >
            <Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="¼����ʼ����|NOTNULL" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
       		</TD>
       		<TD  class= title5 >
       		  ¼����������
       		</TD>
       		<TD  class= input5>
            <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="¼����������|NOTNULL" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
       		</TD>  
     </TR>
   	 <tr class=common>
       		<TD  class= title5 >
       		  ɨ�迪ʼʱ��
       		</TD>
       		<TD  class= input5 >
            <Input class="coolDatePicker" onClick="laydate({elem: '#ScanStartDate'});" verify="ɨ�迪ʼʱ��|NOTNULL" dateFormat="short" name=ScanStartDate id="ScanStartDate"><span class="icon"><a onClick="laydate({elem: '#ScanStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
       		</TD>
       		<TD  class= title5 >
       		  ɨ�����ʱ��
       		</TD>
       		<TD  class= input5 >
            <Input class="coolDatePicker" onClick="laydate({elem: '#ScanEndDate'});" verify="ɨ�����ʱ��|NOTNULL" dateFormat="short" name=ScanEndDate id="ScanEndDate"><span class="icon"><a onClick="laydate({elem: '#ScanEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
       		</TD>  
   	 </tr>
   	 <tr class=common>
       		<TD  class= title5 >
       		  ���һ��ǩ����ʼʱ��
       		</TD>
       		<TD  class= input5 >
            <Input class="coolDatePicker" onClick="laydate({elem: '#SignStartDate'});" verify="���һ��ǩ����ʼʱ��|NOTNULL" dateFormat="short" name=SignStartDate id="SignStartDate"><span class="icon"><a onClick="laydate({elem: '#SignStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
       		</TD>
       		<TD  class= title5 >
       		  ���һ��ǩ������ʱ��
       		</TD>
       		<TD  class= input5 >
            <Input class="coolDatePicker" onClick="laydate({elem: '#SignEndDate'});" verify="���һ��ǩ������ʱ��|NOTNULL" dateFormat="short" name=SignEndDate id="SignEndDate"><span class="icon"><a onClick="laydate({elem: '#SignEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
       		</TD>  
   	 </tr>
   	</table>
   </div>
   </div>
    <br>
    <!--������-->
    <a href="javascript:void(0)" class=button onclick="easyQuery();">��  ѯ</a>
    <a href="javascript:void(0)" class=button onclick="easyPrint();">��ӡ����</a>
    <!-- <INPUT VALUE="��    ѯ" class=cssButton TYPE=button onclick="easyQuery()"> 	
    <INPUT VALUE="��ӡ����" class=cssButton TYPE=button onclick="easyPrint()"> 	 -->

    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivCodeGrid);">
    		</td>
    		<td class= titleImg>
    			 ǩ��ʧ��ԭ��
    		</td>
    	</tr>
    </table>
  	<Div  id= "DivCodeGrid" style= "display: ''" align = center>
		<Table  class= common >
			<TR  class=common>
				<TD text-align: left colSpan=1 >
					<span id="spanCodeGrid" ></span>
			  	</TD>
			</TR>
		</Table>
		<INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onclick="turnPage.firstPage();">
		<INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="turnPage.previousPage();">
		<INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="turnPage.nextPage();">
		<INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="turnPage.lastPage();">
	</Div>
    
  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html> 	
