
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�CommandMain.jsp
//�����ܣ��������
//�������ڣ�2008-9-17
//������  ��
//���¼�¼��  
%>
<%
//==================================================================BEGIN
	String flag= request.getParameter("flag");
    loggerDebug("RuleQuery","FLAG::"+flag);
//===================================================================END
%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT type="text/JavaScript" src="RuleQuery.js"></SCRIPT>
  <script src="../common/javascript/MultiCom.js"></script>
  <SCRIPT type="text/JavaScript">
  
    var flag=<%=flag%>;
  </SCRIPT>
  <%@include file="RuleQueryInit.jsp"%>
<%
 //flag��Ӧ��ҳ�棺 1�������� 2 �������� 3 ������� 4�������޸� 5���������� 6�����򷢲� 7���������� 8�������ѯ 9�������ӡ
%>

</head>
<body  onload="initForm(<%=flag%>);initElementtype();" >

<form action="./RuleQuerySave.jsp" method=post name=fm id=fm target="fraSubmit">
     <Table>
	<TR>
		<TD class=input style="display: none">
         <Input class="common" name=Transact>
       </TD>	  
		<TD class=common><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,divQueryCondition);"></TD>
		<TD class=titleImg>�����ѯ</TD>
	</TR>
</Table>
<Div  id= "divQueryCondition" style= "display: ''" class="maxbox">
<table class=common>
	<TR class=common>
		<TD class=title5>������</TD>
		<TD class=input5><Input class="wid" class=common name=RuleName id=RuleName ></TD>
			
		<TD class=title5>����</TD>
		<TD class=input5><Input class="wid" class=common name=description id=description></TD>	
		</TR>
        <TR class=common>
       <TD class=title5>ҵ��ģ��</TD>
        <TD  class= input5>
	          <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=Business id=Business class="codeno" 
	          ondblclick="return showCodeList('ibrmsbusiness',[this,ibrmsbusinessName],[0,1]);" 
              onclick="return showCodeList('ibrmsbusiness',[this,ibrmsbusinessName],[0,1]);" 
	          onkeyup="return showCodeListKey('LTibrmsbusiness',[this,ibrmsbusinessName],[0,1]); "  ><input name=ibrmsbusinessName id=ibrmsbusinessName  class='codename' onchange="return Clean();" readonly=true>
	    </TD> 
        <TD class=title5>
                          ��Ч���ڣ�
        </TD>	  
      
         <TD  class= input5>
          
          <Input class="coolDatePicker" onClick="laydate({elem: '#RuleStartDate'});" verify="��ʼ����|Date" dateFormat="short" name=RuleStartDate id="RuleStartDate"><span class="icon"><a onClick="laydate({elem: '#RuleStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD> 
	</TR>
	<TR class=common>
	
		<TD class=title5>
                         ʧЧ���ڣ�
        </TD>	  
         <TD  class= input5>
          
           <Input class="coolDatePicker" onClick="laydate({elem: '#RuleEndDate'});" verify="��ʼ����|Date" dateFormat="short" name=RuleEndDate id="RuleEndDate"><span class="icon"><a onClick="laydate({elem: '#RuleEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD> 
        
         <TD class=title5>
                         ״̬��
        </TD>	  
        <TD  class= input5>
	          <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=IbrmsState id=IbrmsState class="codeno" 
	          ondblclick="return showCodeList('ibrmsstate',[this,ibrmsstateName],[0,1]);"
              onclick="return showCodeList('ibrmsstate',[this,ibrmsstateName],[0,1]);" 
	          onkeyup="return showCodeListKey('LTibrmsstate',[this,ibrmsstateName],[0,1]); " ><input name=ibrmsstateName id=ibrmsstateName  class='codename' onchange="return Clean();" >
	        </TD> 
	</TR>
<TR>
</Table>
</Div>
<p>
  <Div id= DivSelect style="display: ''">
    <INPUT VALUE="��  ѯ" TYPE=button class="cssButton" onclick="easyQuery(<%=flag %>)">
     <input class=cssButton type=button  value='�鿴��ϸ' onclick="RuleDetails()"> 
     
 </Div>

  <Div  id= "divQueryGrp" style= "display: ''">
    <table>
      <td class= titleImg>
    	�����б�
      </td>
    </table>

     <table  class= common>
        <tr>
    	  	<td text-align: left colSpan=1>
	        <span id="spanQueryGrpGrid" ></span>
		</td>
		<TD class=input style="display: none">						
		</TD>
	</tr>

     </table>
     
        <br>
	    <div id="CopyDiv" style="display:none; ">
         <input class=cssButton type="submit" name='Copy' value='��  ��' onclick="copy()">
          <!--<a href="javascript:void(0);" name='Copy' class="button" onClick="copy();">��    ��</a> -->
        </div> <br>

        <div id="TestDiv" style="display:none">
        <input class=cssButton type="button" name='test' value='��  ��' onclick="testRule();">
         <input class=cssButton type="button" name='test1' value='����ͨ��' onclick="testApprove(true);">
         <input class=cssButton type="button" name='test1' value='���Բ�ͨ��' onclick="testApprove(false);">
         <!--<a href="javascript:void(0);" name='test' class="button" onClick="testRule();">��    ��</a>
         <a href="javascript:void(0);" name='test1' class="button" onClick="testApprove(true);">����ͨ��</a>
         <a href="javascript:void(0);" name='test1' class="button" onClick="testApprove(false);">���Բ�ͨ��</a>    -->    
         <input id="TemplateId" type="hidden" name='TemplateId'> 
        </div> 
        
        <div id="ModifyDiv" style="display:none">
          <input class=cssButton type="button" name='Modify' value='��  ��' onclick="modify()">
          <!--<a href="javascript:void(0);" name='Modify' class="button" onClick="modify();">��    ��</a>-->
		</div> 
         
        <div id="ApproveDiv" style="display:none">
         <input class=cssButton type="button" name='Approve' value='����ͨ��' onclick="approve()">
          <input class=cssButton type="button" name='UnApprove' value='������ͨ��' onclick="unapprove()">
          <!--<a href="javascript:void(0);" name='Approve' class="button" onClick="approve();">����ͨ��</a>
          <a href="javascript:void(0);" name='UnApprove' class="button" onClick="unapprove();">������ͨ��</a>-->
        </div> 
         
        <div id="DeployDiv" style="display:none">
         <input class=cssButton type="button" name='Deploy' value='��   ��' onclick="deploy()">
          <!--<a href="javascript:void(0);" name='Deploy' class="button" onClick="deploy();">��    ��</a>-->
         
        </div>
         
        <div id="DropDiv" style="display:none">
          <input class=cssButton type="button" name='Drop' value='��   ��' onclick="drop()">
          <!--<a href="javascript:void(0);" name='Drop' class="button" onClick="drop();">��    ��</a>-->
        </div> 
        
        <div id="PrintDiv" style="display:none">
         <input class=cssButton type="submit" name='Print' value='��  ӡ' onclick="RuleDetails()">
          <!--<a href="javascript:void(0);" name='Print' class="button" onClick="RuleDetails();">��    ӡ</a>-->
        </div> 
       <input type=hidden id="BtnFlag" name= "BtnFlag"> 
       <!-- BtnFlag�ĸ�ֵ:����״̬��ת����һ��-->
</div>
</form>
    
 <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
