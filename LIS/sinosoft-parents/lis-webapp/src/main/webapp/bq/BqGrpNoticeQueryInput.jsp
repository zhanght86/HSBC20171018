
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>
<html>
<%
    //���ҳ��ؼ��ĳ�ʼ����
    GlobalInput tGI = new GlobalInput();
    tGI = (GlobalInput) session.getValue("GI");
%>
<script>
    var operator = "<%=tGI.Operator%>";   //��¼����Ա
    var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
    var comcode = "<%=tGI.ComCode%>"; //��¼��½����
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="./BqGrpNoticeQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@include file="BqGrpNoticeQueryInit.jsp"%>
  <title>�������ѯ </title>
</head>
<body  onload="initForm();" >
  <form action="./AgentCommonQuerySubmit.jsp" method=post name=fm id=fm target="fraSubmit">
  <!--ҵ��Ա��ѯ���� -->
    <table>
    	<tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;">
            </td>
            <td class= titleImg>
                �������ѯ����
            </td>            
    	</tr>
    </table>
  <Div  id= "divLAAgent" style= "display: ''">
  <div class="maxbox1">
  <table  class= common>
      <TR  class= common> 
        <TD class= title> �������ˮ��</TD>
        <TD  class= input>  <Input class="wid" class=common  name=PrtSeq11 id=PrtSeq11> </TD>
        <TD class= title> ��ȫ����� </TD>
        <TD  class= input>  <Input class="wid" class=common  name=EdorAcceptNo id=EdorAcceptNo> </TD>
        <TD class= title> �����ͬ��</TD>
        <TD class= input> <Input class="wid" class=common name=GrpContNo id=GrpContNo>  </TD>
		
      </TR>
       <TR  class= common> 

        	<TD  class= title>���������</TD>
			<TD  class= input><Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 	no-repeat right center" class="codeno" name=AskType id=AskType CodeData="0|^1|�˹��˱������^2|��ȫ���������" ondblclick="showCodeListEx('AskType',[this,AskTypeName],[0,1]);" onMouseDown="showCodeListEx('AskType',[this,AskTypeName],[0,1]);" onkeyup="showCodeListKeyEx('AskType',[this,AskTypeName],[0,1]);"><input class=codename name="AskTypeName" id="AskTypeName" readonly=true> </TD>   
			<TD  class= title>������</TD>
			<TD  class= input>
				<input class="wid" class=common name=Asker id=Asker>     	        	 
			</TD>
			<TD  class= title> �н��� </TD>
			<TD  class= input> 
				<input class="wid" class=readonly readonly   name=DispPer id=DispPer value="<%=tGI.Operator%>">     	
			</TD>
          
        
      </TR>
      
    </table>
      </div>   </Div>             

		<!--<INPUT class=cssButton VALUE="��  ѯ" TYPE=button onclick="easyQueryClick();"> -->
        <a href="javascript:void(0);" class="button" onClick="easyQueryClick();">��    ѯ</a>

        
          				
    <table>
    	<tr>
        <td class=common>
		<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAgentGrid);">
    		</td>
    		<td class= titleImg>
    			 ��Ҫ�һظ��������
    		</td>
    	</tr>
    </table>
  	<Div  id= "divAgentGrid" style= "display: ''" >
      <table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanAgentGrid" align=center>
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	 <table align="center">
    		<tr>
    			<td>
			      <INPUT class=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"> 
			    </td>
			    <td>  
			      <INPUT class=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
			    </td>
			    <td> 			      
			      <INPUT class=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
			    </td>
			    <td> 			      
			      <INPUT class=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();"> 						
			    </td>  			
  			</tr>
  		</table>
        
  		 <table>
	        <TR>	        	
	         	<TD class= titleImg>��������</TD>
	       	</TR>
	       	<TR>	        	
	         <TD class= input> <textarea style="margin-left:16px" readonly=true name="DispIdeaTrace" id="DispIdeaTrace" cols="199" rows="4" witdh=25% class="common"></textarea></TD>
	       	</TR>
	     </table>
         
	      <table>
          
	        <TR>	        	
	         	<TD class= titleImg>�ҵĻظ�</TD>
	       	</TR>
	       	<TR>	        	
	         <TD class= input> <textarea style="margin-left:16px" name="MyReply" id="MyReply" cols="199" rows="4" witdh=25% class="common"></textarea></TD>
	       	</TR>
	     </table>

  	</div>

	     <!--<INPUT class=cssButton VALUE="�� ��" TYPE=button onclick="replyAsk();">-->
         <a href="javascript:void(0);" class="button" onClick="replyAsk();">��    ��</a> 
				<input type="hidden" id="PrtSeq" name="PrtSeq">
      <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
     
  </form>
  <br><br><br><br>
</body>
</html>
