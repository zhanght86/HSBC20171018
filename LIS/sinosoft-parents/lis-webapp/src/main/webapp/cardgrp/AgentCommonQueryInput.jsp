<%
//�������ƣ�AgentQueryInput.jsp
//�����ܣ�
//�������ڣ�2003-04-8
//������  ��lh
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<%
 	String tManageCom = "";
 	String tQueryFlag = "";    //������������Լ��ҵ��Ա¼��ʱ���õĲ�ѯ
 	//������Լ��ҵ��Ա����¼��ʱ��tQueryFlag="1"
	try
	{
		tManageCom = request.getParameter( "ManageCom" );
		loggerDebug("AgentCommonQueryInput","---tManageCom:"+tManageCom);
		tQueryFlag = request.getParameter("queryflag");
		loggerDebug("AgentCommonQueryInput","---tQueryFlag:"+tQueryFlag);
	}
	catch( Exception e1 )
	{
			loggerDebug("AgentCommonQueryInput","---Exception:"+e1);
	}
%>

<script>
	var ManageCom = "<%= tManageCom%>";
	var branchtype="<%=request.getParameter("branchtype")%>";
	var queryFlag = "<%=tQueryFlag%>";	
</script>	
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="./AgentCommonQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="./AgentCommonQueryInit.jsp"%>
  <title>ҵ��Ա��ѯ </title>
</head>
<body  onload="initForm();" >
  <form action="./AgentCommonQuerySubmit.jsp" method=post name=fm target="fraSubmit">
  <!--ҵ��Ա��ѯ���� -->
    <table>
    	<tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLAAgent);">
            </td>
            <td class= titleImg>
                ҵ��Ա��ѯ����
            </td>            
    	</tr>
    </table>
  <Div  id= "divLAAgent" style= "display: ''">
  <table  class= common>
      <TR  class= common> 
        <TD class= title>   ҵ��Ա����  </TD>
        <TD  class= input>  <Input class=common  name=AgentCode > </TD>
        <TD  class= title> ���� </TD>
        <TD  class= input>   <Input name=Name class= common >  </TD>
        <TD class= title>  �������� </TD>
        <TD class= input> <Input name=ManageCom class='codeno' id="ManageCom" ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input name=ManageComName class=codename readonly=true>   </TD>
      </TR>
      
   
    </table>
          
          <!--INPUT class=common VALUE="��ѯ" TYPE=button onclick="easyQueryClick();"--> 
	   <table> 
		    <tr>
		    <td><INPUT class=cssButton VALUE="��  ѯ" TYPE=button onclick="easyQueryClick();"> </td>
		    <td><INPUT class=cssButton VALUE="��  ��" TYPE=button onclick="returnParent();"> 	</td>

			<td>  
			 <!--INPUT class=common VALUE="ҵ��Ա��Ϣ" TYPE=button onclick="returnParent();"-->  </td>
		   <td><!--INPUT class=common VALUE="Ͷ������Ϣ" TYPE=button onclick="ProposalClick();"-->  </td>
			<td> <!--INPUT class=common VALUE="������Ϣ" TYPE=button onclick="PolClick();"-->  </td>
			<td>  
			<!--INPUT class=common VALUE="����������Ϣ" TYPE=button onclick="DesPolClick();"-->  </td>
		    </tr> 
	   </table> 
    </Div>      
          				
    <table>
    	<tr>
        <td class=common>
		<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAgentGrid);">
    		</td>
    		<td class= titleImg>
    			 ҵ��Ա���
    		</td>
    	</tr>
    </table>
  	<Div  id= "divAgentGrid" style= "display: ''" align =center>
      <table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanAgentGrid" align=center>
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	<table>
    		<tr>
    			<td>
			      <INPUT class=cssButton VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"> 
			    </td>
			    <td>  
			      <INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
			    </td>
			    <td> 			      
			      <INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
			    </td>
			    <td> 			      
			      <INPUT class=cssButton VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();"> 						
			    </td>  			
  			</tr>
  		</table>
  	</div>
      <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  </form>
</body>
</html>