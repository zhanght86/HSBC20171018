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
  <SCRIPT src="./UnSignPolQuery.js"></SCRIPT>   
  <%@include file="./UnSignPolQueryInit.jsp"%>   
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>Ͷ��������δ¼����Ϣ��ѯ</title>
</head>      
 
<body  onload="initForm();initElementtype();" >
<form action="./UnSignPolQuerySave.jsp" method=post name=fm id="fm" target="fraSubmit">
	<table class= common border=0 width=100%>
  <tr>
    <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
        </td>
	<td class= titleImg align= center>�������ѯ������</td>
	</tr>
	</table>
  <!-- <form method=post name=fm id="fm"> -->
    <div class="maxbox1">
    <Div  id= "divFCDay" style= "display: ''">
   <Table class= common>
     <TR class= common> 
          <TD  class= title5>
            ӡˢ��
          </TD>
          <TD  class= input5><Input class='common wid' name=prtNo id="prtNo"></TD> 
          <TD  class= title5>�������</TD>      
                <TD  class= Input5> <Input style="background:url(../common/images/select--bg_03.png)     no-repeat right center" class="codeno" name=ManageCom id="ManageCom" verify="�������|LEN>4|code:station" onclick="return showCodeList('station',[this,ManageComName],[0,1]);" ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName id="ManageComName" readonly=true  elementtype=nacessary>
          </TD>        
      </TR>
     <TR  class= common>
          <TD  class= title5>Ͷ����״̬</TD>
	          <TD  class= input5>
	          <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center"   class="code" name="state"  id="state"
	            CodeData="0|^1|���µ�¼��^2|���쳣������^3|�����˳�촦��^4|������^5|��������޸�^6|���Ժ�^7|���˹��˱�^8|��ǩ��^9|��������ظ�"   onclick="showCodeListEx('ModeSelect_0',[this],[0,1]);"
	            ondblClick="showCodeListEx('ModeSelect_0',[this],[0,1]);"
	            onkeyup="showCodeListKeyEx('ModeSelect_0',[this],[0,1]);">
	          </TD> 
       		<TD  class= title5 >
       		  ɨ�迪ʼ����
       		</TD>
       		<TD  class= input5 >
            <Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
       		</TD>
      </TR>
     <TR  class= common>  
       		<TD  class= title5 >
       		  ɨ���������
       		</TD>
       		<TD  class= input5 >
            <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
       		</TD>  
     </TR>
   	</Table>
    </Div>  
    </div>
    <br>
    <!--������-->
    <!--INPUT VALUE="��    ѯ" class= cssButton TYPE=button onclick="easyQuery()"--> 	
    <a href="javascript:void(0)" class=button onclick="easyPrint();">��ӡ����</a>
    <!-- <INPUT VALUE="��ӡ����" class= cssButton TYPE=button onclick="easyPrint()"> 	 -->
<div id="infoDiv" style="display:none">
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCodeGrid);">
    		</td>
    		<td class= titleImg>
    			 δ�б�Ͷ��������
    		</td>
    	</tr>
    </table>
    </div>
  	<Div  id= "divCodeGrid" style= "display: none"  align = center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
									<span id="spanPolGrid" >
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
	var codeSql = "1 and length(trim(code))>=6 and code like #"+<%=tComCode%>+"%#";
</script>
