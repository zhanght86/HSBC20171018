<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�ProposalNoRespQuery.jsp
//�����ܣ���ѯ����δ�ظ��嵥
//�������ڣ�2007-03-20 18:02
//������  ��Fuqx
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
    GlobalInput tG1 = (GlobalInput)session.getValue("GI");
    String tComCode =tG1.ComCode;
%>
<script>
	var comCode= <%=tComCode%>;
</script>
<html>    
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT>
  <SCRIPT src="./ProposalNoRespQuery.js"></SCRIPT>   
  <%@include file="./ProposalNoRespQueryInit.jsp"%>   
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>��ѯ���������δ�ظ��嵥</title>
</head>      
 
<body  onload="initForm();initElementtype();" >
<form action="./ProposalNoRespQuerySave.jsp" method=post name=fm id="fm" target="fraSubmit">
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
					  <Input  style="background:url(../common/images/select--bg_03.png)    no-repeat right center" class="codeno" name=ManageCom id="ManageCom" onclick="showCodeList('station',[this,ManageComName],[0,1],null,codeSql,'1');"  ondblclick="showCodeList('station',[this,ManageComName],[0,1],null,codeSql,'1');" onkeyup="showCodeListKey('station',[this,ManageComName],[0,1],null,codeSql,'1');"><input class=codename name=ManageComName id="ManageComName" readonly=true>
				  </TD>
          <TD  class= title5>
            ���ֱ���
          </TD>
          <TD  class= input5>
            <Input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" class="codeno" name=RiskCode id="RiskCode" onclick="return showCodeList('RiskCode',[this,RiskCodeName],[0,1]);" ondblclick="return showCodeList('RiskCode',[this,RiskCodeName],[0,1]);" onkeyup="return showCodeListKey('RiskCode',[this,RiskCodeName],[0,1]);"><input class=codename name=RiskCodeName id="RiskCodeName" readonly=true>
          </TD>
      </TR>
    <TR class= common> 
           <TD  class=title5>
           ��������
           </TD>
				  <TD  class=input5>
					  <input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" class="codeno" name=SaleChnl id="SaleChnl" onclick="showCodeList('SaleChnl',[this,SaleChnlName],[0,1]);" ondblclick="showCodeList('SaleChnl',[this,SaleChnlName],[0,1]);" onkeyup="showCodeListKey('SaleChnl',[this,SaleChnlName],[0,1]);"><input class=codename name=SaleChnlName id="SaleChnlName" readonly=true>
				  </TD>  
          <TD  class= title5>
            �����˱���
          </TD>
          <TD  class= input5>
          <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" MAXLENGTH=10 name=AgentCode id="AgentCode" onclick="return queryAgent(comCode);" ondblclick="return queryAgent(comCode);" onkeyup="return queryAgent2();">
          </TD>
      </TR>
    <TR class= common> 
          <TD  class= title5>
            ��������
          </TD>
          <TD  class= input5><Input class='common wid' name=InsuredName id="InsuredName"></TD>
  				<TD  class=title5>
           ���������
           </TD>
				  <TD  class=input5>
					  <input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" class="codeno" name=IssueNoticeType id="IssueNoticeType" onclick="showCodeList('IssueNoticeType',[this,IssueNoticeTypeName],[0,1]);" ondblclick="showCodeList('IssueNoticeType',[this,IssueNoticeTypeName],[0,1]);" onkeyup="showCodeListKey('IssueNoticeType',[this,IssueNoticeTypeName],[0,1]);"><input class=codename name=IssueNoticeTypeName id="IssueNoticeTypeName" readonly=true>
				  </TD>  
     </TR>
     <TR  class= common>
       		<TD  class= title5>
       		  ����ʼ����
       		</TD>
       		<TD  class= input5 >
            <Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="��ʼ����|NOTNULL" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
       		</TD>
       		<TD  class= title5 >
       		  �����������
       		</TD>
       		<TD  class= input5 >
            <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" erify="��������|NOTNULL" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
       		</TD>
      </TR>
    <TR class= common>   
       		<TD  class=title5>
           	����λ��
           </TD>
				  <TD  class=input5>
					  <input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" class="codeno" name=OperatePos id="OperatePos" onclick="showCodeList('OperatePos',[this,OperatePosName],[0,1]);" ondblclick="showCodeList('OperatePos',[this,OperatePosName],[0,1]);" onkeyup="showCodeListKey('OperatePos',[this,OperatePosName],[0,1]);"><input class=codename name=OperatePosName id="OperatePosName" readonly=true>
				  </TD> 
      </TR>
    <TR class= common>
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
      </TR>
    <TR class= common>  
       		<TD  class= title5>
       		  ������·���ʼʱ��
       		</TD>
       		<TD  class= input5 >
            <Input class="coolDatePicker" onClick="laydate({elem: '#IssueStartDate'});" erify="������·���ʼʱ��|NOTNULL" elementtype=nacessary dateFormat="short" name=IssueStartDate id="IssueStartDate"><span class="icon"><a onClick="laydate({elem: '#IssueStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
       		</TD> 
       		<TD  class= title5 >
       		  ������·�����ʱ��
       		</TD>
       		<TD  class= input5 >
            <Input class="coolDatePicker" onClick="laydate({elem: '#IssueEndDate'});" verify="������·�����ʱ��|NOTNULL" elementtype=nacessary dateFormat="short" name=IssueEndDate id="IssueEndDate"><span class="icon"><a onClick="laydate({elem: '#IssueEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
       		</TD>  
   	 </tr>
   	</Table>
    </Div>  
    </div>
    <br>
    <!--������-->
    <a href="javascript:void(0)" class=button onclick="easyQuery();">��  ѯ</a>
    <a href="javascript:void(0)" class=button onclick="easyPrint();">��ӡ����</a>
    <!-- <INPUT VALUE="��    ѯ" class= cssButton TYPE=button onclick="easyQuery()"> 	
    <INPUT VALUE="��ӡ����" class= cssButton TYPE=button onclick="easyPrint()">  -->	
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCodeGrid);">
    		</td>
    		<td class= titleImg>
    			 �����δ�ظ�����
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
      	<INPUT VALUE="��  ҳ" class= cssButton90 TYPE=button onclick="getFirstPage();"> 
      	<INPUT VALUE="��һҳ" class= cssButton91 TYPE=button onclick="getPreviousPage();"> 					
      	<INPUT VALUE="��һҳ" class= cssButton92 TYPE=button onclick="getNextPage();"> 
      	<INPUT VALUE="β  ҳ" class= cssButton93 TYPE=button onclick="getLastPage();"> 					
  	</div>

  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html> 	
<script>
	<!--ѡ�������ֻ�ܲ�ѯ������¼�����-->
	var codeSql = "1  and code like #"+<%=tComCode%>+"%#";
</script>
