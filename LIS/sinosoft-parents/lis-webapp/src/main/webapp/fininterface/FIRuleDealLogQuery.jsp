<html>
<%
//�������� :FIRuleDealLogQuery.jsp
//������ :У����־��Ϣ��ѯ
//������ :���
//�������� :2008-08-18
//
%>
	<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
	<!--�û�У����-->
	<%@page import = "com.sinosoft.utility.*"%>
	<%@page import = "com.sinosoft.lis.schema.*"%>
	<%@page import = "com.sinosoft.lis.vschema.*"%>
	<%@page import = "com.sinosoft.lis.pubfun.*"%>
	<%@include file="../common/jsp/UsrCheck.jsp"%>
	<%
  GlobalInput tGI1 = new GlobalInput();
  tGI1=(GlobalInput)session.getValue("GI");//���ҳ��ؼ��ĳ�ʼ����
  
 	%>
<script>
  var comcode = "<%=tGI1.ComCode%>";
  var VersionNo = <%=request.getParameter("VersionNo")%>;
</script>
<head >
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>  
<SCRIPT src = "FIRuleDealLogQuery.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="FIRuleDealLogQueryinit.jsp"%>
</head>
<body  onload="initForm();initElementtype();">
  <form action="" method=post name=fm id=fm target="fraSubmit">
  <Div id= "divLLReport1" style= "display: ''">
  
  	<table>
    	<tr>
    		 <td class= titleImg>
        		��ѯ����
       		 </td>   		 
    	</tr>
    </table>
    <div class="maxbox1">
  	<table class= common border=0 width=100%>
  <table class=common>
		<tr class= common>
				 <TD class= title>
					  �汾���
				 </TD>
				 <TD class=input>
				 	<input class="wid" class=readonly name=VersionNo id=VersionNo readonly=true >
				</TD>
				<TD  class= title>
            ��ʼʱ��
          </TD>
          <TD  class= input>
            <!--<Input class= "coolDatePicker" verify="��ʼʱ��|notnull&date" dateFormat="short" name=StartDay >-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#StartDay'});" verify="��ʼʱ��|notnull&date" dateFormat="short" name=StartDay id="StartDay"><span class="icon"><a onClick="laydate({elem: '#StartDay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            
          </TD>
          <TD  class= title>
            ����ʱ��
          </TD>
          <TD  class= input>
            <!--<Input class= "coolDatePicker" verify="����ʱ��|notnull&date"  dateFormat="short" name=EndDay >-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#EndDay'});" verify="����ʱ��|notnull&date" dateFormat="short" name=EndDay id="EndDay"><span class="icon"><a onClick="laydate({elem: '#EndDay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
		</tr>
	</table> 
	
      <!--<input class="cssButton" type=button value="��  ѯ" onclick="queryFIRuleDealLog()">-->
      <a href="javascript:void(0);" class="button" onClick="queryFIRuleDealLog();">��    ѯ</a> 
   		</div>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick="showPage(this,divFIRuleDealLogGrid);">
    		</td>
    		<td class= titleImg>
    			 У����־��Ϣ��ѯ���
    		</td>
    	</tr>
    </table>
<Div  id= "divFIRuleDealLogGrid" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanFIRuleDealLogGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
        <!--<div align="left"><input class="cssButton" type=button value="��  ��" onclick="ReturnData()"></div>-->
        <center>
		<INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onclick="turnPage.firstPage();"> 
		<INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="turnPage.previousPage();"> 					
		<INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="turnPage.nextPage();"> 
		<INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="turnPage.lastPage();"> </center>					
  <br>
</table>
</Div>
<a href="javascript:void(0);" class="button" onClick="ReturnData();">��    ��</a>
   <input type=hidden id="OperateType" name="OperateType">
</table>
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
