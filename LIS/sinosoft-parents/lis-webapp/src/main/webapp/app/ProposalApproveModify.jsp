<%
//�������ƣ�ProposalApproveModify.jsp
//�����ܣ�������޸�
//�������ڣ�2002-11-23 17:06:57
//������  ������
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>
<%
  //�����¸���
	String tGrpPolNo = "00000000000000000000";
	
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var grpPolNo = "<%=tGrpPolNo%>";      //���˵��Ĳ�ѯ����.
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
	var comcode = "<%=tGI.ComCode%>";
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <script src="../common/javascript/Common.js" ></SCRIPT>
  <script src="../common/javascript/MulLine.js"></SCRIPT>
  <script src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
 
  <link href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <script src="../common/javascript/jquery.workpool.js"></script>
  <script src="ProposalApproveModify.js"></SCRIPT>
  <%@include file="ProposalApproveModifyInit.jsp"%>
  <script src="../common/javascript/MultiCom.js"></script>
  
  <title>������޸ı�����ѯ </title>
</head>
<body  onload="initForm();initElementtype();" >
  <form method=post name=fm id="fm" target="fraTitle">
    <!-- ������Ϣ���� -->
    <!-- <table class= common border=0 width=100%>
    	<tr>
			<td class= titleImg align= center>�������ѯ������</td>
		</tr>
	</table>
	<Div  id= "divSearch" style= "display: ''">
    <table  class= common>
      	<TR  class= common>
          <td  class=title>
            ӡˢ��
          </td>
          <td  class=input>
            <input class= common name=ContNo >
            <input class= common name=PrtNo type="hidden">
          </td>  
          <td  class=title>
            �������
          </td>
          <td  class=input>
            <input class="codeno" name=ManageCom ondblclick="showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName readonly=true>
          </td>          
          <td  class=title>
            ��������
          </td>
          <td  class=input>
            <input class="codeno" name=SaleChnl ondblclick="showCodeList('SaleChnl',[this,SaleChnlName],[0,1]);" onkeyup="showCodeListKey('SaleChnl',[this,SaleChnlName],[0,1]);"><input class=codename name=SaleChnlName readonly=true>
          </td>  
        </tr>      
        <tr>
          <TD  class= title>
            ҵ��Ա����
          </TD>
			    <td class="input">
			      <input NAME="AgentCode" VALUE="" MAXLENGTH="10" CLASS="code" elementtype=nacessary verifyorder="1" Aonkeyup="return queryAgent();" onblur="return queryAgent();" ondblclick="return queryAgent();" >
          </td>
          <td  class= title>
            �ظ�����
          </td>
          <td class= input>
            <input class="multiDatePicker" dateFormat="short" name=MakeDate >
          </td>
          <td  class=title>
           �ظ�״̬
          </td>
          <td  class=input>
            <input class="codeno" name=State value='1' CodeData ="0|^1|ȫ���ظ�^2|δ�ظ�" ondblclick="return showCodeListEx('State',[this,StateName],[0,1]);" onkeyup="return showCodeListKeyEx('State',[this,StateName],[0,1]);"><input class=codename name=StateName value='ȫ���ظ�' readonly=true>
          </td>  
        </tr>
	<tr class=common>
		<td class=title>���������</td>
		<td><Input class="codeno" name=BackObj readonly CodeData=""
			; ondblclick="return showCodeList('backobj',[this,BackObjName],[0,1]);"
			onkeyup="return showCodeListKey('backobj',[this,BackObjName],[0,1]);"><Input
			class=codename name=BackObjName readonly=true></td>
		<TD class=title>���Ȼ���</TD>
		<TD class=input><Input class="codeno" name=PreCom
			CodeData="0|^a|���Ż���Ͷ����^b|�ǰ��Ż���Ͷ����"
			ondblClick="showCodeListEx('PreCom',[this,PreComName],[0,1]);"
			onkeyup="showCodeListKeyEx('PreCom',[this,PreComName],[0,1]);"><input
			class=codename name=PreComName value='' readonly=true></TD>
	</tr>

	<!--
          <td  class= title>
            ӡˢ����
          </td>
          <td  class= input>
            <input class= common name=PrtNo >
          </td>
-->
<!--
          <td  class= title>
            Ͷ��������
          </td>
          <td  class= input>
            <input class=common name=AppntName >
          </td>          \
-->
<!--
          <td  class= title>
            ¼������/��������
          </td>
          <td class= input>
            <input class="coolDatePicker" dateFormat="short" name=MakeDate >
          </td>

        </tr>
        <tr class="common">
          <td  class= title8>
            �������
          </td>
          <td  class= input8>
            <input class="codeno" name=ManageCom ondblclick="showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName readonly=true>
          </td>          
         <td  class= title>
            ҵ��Ա����
          </td>
          <td  class= input>
            <input class="codeno" name=AgentCode ondblclick="showCodeList('AgentCode',[this, AgentName], [0, 1]);" onkeyup="showCodeListKey('AgentCode', [this, AgentName], [0, 1]);"><input class=codename name=AgentName readonly=true>
          </td>
          <td  class= title>
            Ӫҵ����Ӫҵ��
          </td>
          <td  class= input>
            <input class="codeno" name=AgentGroup ondblclick="return showCodeList('AgentGroup',[this,AgentGroupName],[0,1]);" onkeyup="return showCodeListKey('AgentGroup',[this,AgentGroupName],[0,1]);"><input class="codename" name=AgentGroupName readonly=true>
          </td>  
        </tr>
-->
    <!-- </table>
    </DIV>
          <input class=cssButton VALUE="��  ѯ" TYPE=button onclick="easyQueryClick();">     
      		<input class=cssButton id="riskbutton" VALUE="��  ��" TYPE=button onClick="ApplyUW();">

    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPolGrid);">
    		</td>
    		<td class= titleImg>
    			 ��������
    		</td>
    	</tr>  	
    </table>
  	<Div  id= "divPolGrid" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <input CLASS=cssButton VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"> 
      <input CLASS=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
      <input CLASS=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
      <input CLASS=cssButton VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();">	    				
  	</div>
  <br>
   	
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSelfPolGrid);">
    		</td>
    		<td class= titleImg>
    			 ��������޸�Ͷ����
    		</td>
    	</tr>  	
    </table>
    	<Div  id= "divSearch1" style= "display: ''">
    <table  class= common>
      	<TR  class= common>
          <td  class=title>
            ӡˢ��
          </td>
          <td  class=input>
            <input class= common name=ContNo1 >
            <input class= common name=PrtNo1 type="hidden">
          </td>  
          <td  class=title>
            �������
          </td>
          <td  class=input>
            <input class="codeno" name=ManageCom1 ondblclick="showCodeList('station',[this,ManageComName1],[0,1]);" onkeyup="showCodeListKey('station',[this,ManageComName1],[0,1]);"><input class=codename name=ManageComName1 readonly=true>
          </td>          
          <td  class=title>
            ��������
          </td>
          <td  class=input>
            <input class="codeno" name=SaleChnl1 ondblclick="showCodeList('SaleChnl',[this,SaleChnlName1],[0,1]);" onkeyup="showCodeListKey('SaleChnl',[this,SaleChnlName1],[0,1]);"><input class=codename name=SaleChnlName1 readonly=true>
          </td>  
        </tr>      
        <tr>
          <td  class= title>
            �ظ�����
          </td>
          <td class= input>
            <input class="multiDatePicker" dateFormat="short" name=MakeDate1 >
          </td>
          <td  class=title>
           �ظ�״̬
          </td>
          <td  class=input>
            <input class="codeno" name=State1 value='1' CodeData ="0|^1|ȫ���ظ�^2|δ�ظ�" ondblclick="return showCodeListEx('State',[this,StateName1],[0,1]);" onkeyup="return showCodeListKeyEx('State',[this,StateName1],[0,1]);"><input class=codename name=StateName1 value='ȫ���ظ�' readonly=true>
          </td> 
          <td  class= title>
	            ���������  
	          </td>
	          <td>
	          <Input class= "codeno" name = BackObj1 readonly CodeData=""; ondblclick="return showCodeList('backobj',[this,BackObj1Name],[0,1]);" onkeyup="return showCodeListKey('backobj',[this,BackObj1Name],[0,1]);"><Input class = codename name=BackObj1Name readonly = true>
	          </td>  
        </tr>  
    </table>
    </DIV>
          <input class=cssButton VALUE="��  ѯ" TYPE=button onclick="initQuerySelf();">  
  	<Div  id= "divSelfPolGrid" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanSelfPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <input CLASS=cssButton VALUE="��  ҳ" TYPE=button onclick="turnPage2.firstPage();"> 
      <input CLASS=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage2.previousPage();"> 					
      <input CLASS=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage2.nextPage();"> 
      <input CLASS=cssButton VALUE="β  ҳ" TYPE=button onclick="turnPage2.lastPage();">	   				
  	</div>  	
  	  	
  	<br> -->
    	
    	<!--input VALUE="�������ѯ" class= cssButton TYPE=button onclick="QuestQuery();"--> 
    	<!--input class=cssButton VALUE="���±��鿴" TYPE=button onclick="showNotePad();"-->
    	<div id="IssuePool"></div> 
    
		<input type=hidden id="GrpPolNo" name="GrpPolNo">
       <input type=hidden id="MissionID" name="MissionID" >
    	 <input type=hidden id="SubMissionID" name="SubMissionID" >
    	 <input type=hidden id="ActivityID" name="ActivityID" >
    	 <input type=hidden id="AgentCode" name="AgentCode" value = "" >	
    	 <INPUT type=hidden id="AgentName" name= AgentName value= "">
    	 <input type=hidden id="ManageCom" name="ManageCom" >	
    	 <input type=hidden id="AgentGroup" name=AgentGroup>		
  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
