<html>
<%
//�������ƣ�
//�����ܣ����˱�ȫ
//�������ڣ�2007-12-10 16:49:22
//������  ��PST
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.pubfun.*"%>
  <%
     %>

<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="./PEdor.js"></SCRIPT>
  <SCRIPT src="./PEdorTypeSC.js"></SCRIPT>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@include file="PEdorTypeSCInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form action="./PEdorTypeSCSubmit.jsp" method=post name=fm id=fm target="fraSubmit">
  <div class=maxbox1>
  <TABLE class=common>
    <TR  class= common>
      <TD  class= title > ��ȫ�����</TD>
      <TD  class= input >
        <input class="readonly wid" readonly name=EdorAcceptNo id=EdorAcceptNo >
      </TD>
      <TD class = title > �������� </TD>
      <TD class = input >
        <Input class=codeno  readonly name=EdorType id=EdorType><input class=codename name=EdorTypeName id=EdorTypeName readonly=true>
      </TD>

      <TD class = title > ������ </TD>
      <TD class = input >
        <input class="readonly wid" readonly name=ContNo id=ContNo>
      </TD>
    </TR>
       <TR  class= common>
               <TD class =title>������������</TD>
        <TD class = input>
            <input class="coolDatePicker" readonly name=EdorItemAppDate onClick="laydate({elem: '#EdorItemAppDate'});" id="EdorItemAppDate"><span class="icon"><a onClick="laydate({elem: '#EdorItemAppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
        </TD>
        <TD class= title >
        </TD>
        <TD  class= input >
        </TD>
        <TD class= title >
        </TD>
        <TD  class= input >
        </TD>
      </TR>
  </TABLE>
  </div>
  <table>
   <tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPRe);">
      </td>
      <td class= titleImg>
        ȫ����Լ��Ϣ
      </td>
   </tr>
   </table>
    	<Div  id= "divLCPRe" style= "display: ''">
      	<table  class= common>
        	<tr  class= common>
          		<td text-align: left colSpan=1>
        			<span id="spanLCPReSpecGrid" >
        			</span> 
        	  	</td>
        	</tr>
        </table>
    </Div>

  <table>
   <tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCCSpec);">
      </td>
      <td class= titleImg>
        ���޸���Լ��Ϣ
      </td>
   </tr>
   </table>
    	<Div  id= "divLCCSpec" style= "display: ''">
      	<table  class= common>
        	<tr  class= common>
          		<td text-align: left colSpan=1>
        			<span id="spanLCCSpecGrid" >
        			</span> 
        	  	</td>
        	</tr>
        </table>
    </Div>
    
  <table>
    <tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSpeccontent);">
      </td>
      <td class= titleImg>
        �ر�Լ������
      </td>
    </tr>
  </table>
	 
<Div  id= "divSpeccontent" class=maxbox1 style= "display: ''">
      <table>
    <TR  class= common>
      <TD  class= title>
      <textarea name="Speccontent" id=Speccontent cols="120" rows="5" class="common" >
      </textarea>
      </TD>
    </TR>
  </table>
 </Div>
 <!-- 
      <table class = common>
			<TR class= common>
         <TD  class= input width="26%"> 
       		 <Input class="cssButton" type=Button value="�����޸�" onclick="edorTypeSCCancel()">
     	 </TD>
     	 <TD  class= input width="26%"> 
       		 <Input class="cssButton" type=Button value="�����Լ" onclick="edorTypeSCDelete()">
     	 </TD>
     	 </TR>
     	</table>

  -->
  <Div  id= "divUWSpeccontent" style= "display: none">
  				<table>
		    	<tr>
		        	<td class=common>
					    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;"">
		    		</td>
		    		<td class= titleImg>
		    			������Լģ��
		    		</td>
		    	    </tr>
		        </table>	
		    	<Div  id= "divTemp" class=maxbox1 style= "display: ''">
		    		<table class=common>
		    		<tr>
		    		<td class=title5>
		         	ѡ�񼲲�ϵͳ
		      	</td>
		      	<td class=input5>
		            <Input class="codeno" name=HealthSpecTemp id=HealthSpecTemp style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('healthspcetemp',[this,HealthSpecTempName],[0,1]);" 
		            onkeyup="return showCodeListKey('healthspcetemp',[this,HealthSpecTempName,],[0,1]);"><Input class="codename" name=HealthSpecTempName id=HealthSpecTempName readonly elementtype=nacessary>
		        </td>
		    		<td class=title5>
		         	ѡ����Լ����
		      	</td>
		      	<td class=input5>
		            <Input class="codeno" name=SpecTemp id=SpecTemp style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return getClickSpecTemp();" onkeyup="return getClickUpSpecTemp();"><Input class="codename" name=SpecTempname id=SpecTempname readonly elementtype=nacessary>
		        </td>
		        </tr>
		        <tr>
		    		<TD  class= title5>
    	  		�Ƿ��·�
    			</TD>
	        	<TD class=input5>
	            <Input class="codeno" name=NeedPrintFlag CodeData = "0|^Y|�·�^N|���·�" ondblclick= "showCodeListEx('IFNeedFlag',[this,IFNeedFlagName],[0,1]);" onkeyup="showCodeListKeyEx('IFNeedFlag',[this,IFNeedFlagName],[0,1]);" ><Input class=codename  name=IFNeedFlagName readonly = true>
	        	</TD>
				<TD class=title5></TD>
				<TD class=input5></TD>
		        </tr>
		        </table>
		    	</Div>
		    <table style= "display: none">
		    	<tr>
		    	    <td class=common>
					    &nbsp;&nbsp;
		    		</td>
		        	<td class=common>
					    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;">
		    		</td>
		    		<td class= titleImg>
		    			��Լ����
		    		</td>
		    	    </tr>
		    </table>
 			<div class=maxbox1>
		    	<table class = common>
		    	<TR  class= common>
		          <TD  class= title>
		            �ر�Լ��
		          </TD>
		          </tr><tr>
		          
		      <TD  class= input> <textarea name="Remark" id=Remark cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
		        </TR>
		    	</table>
		    </div>
    <br>
      </Div>
  <Div id= "divEdorquery" style="display: ''">
            <input type="button" class="cssButton" value=" �� �� " onClick="edorTypeSCModify()">
            <input type="button" class="cssButton" value=" �� �� " onClick="edorTypeSCInert()">
           <input type="button" class="cssButton" value=" ɾ �� " onClick="edorTypeSCDelete()">
            <Input class="cssButton" type=Button value="�����Լ" onclick="edorTypeSCCancel()">
            <input type="button" class="cssButton" value=" �� �� " onClick="returnParent()">
            <input type="button" class="cssButton" value="���±��鿴" onClick="showNotePad()">
  </Div>
    <table>
   <tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCSpec);">
      </td>
      <td class= titleImg>
        �޸ĺ���Լ������Ϣ
      </td>
   </tr>
   </table>
    	<Div  id= "divLPSpec" style= "display: ''">
      	<table  class= common>
        	<tr  class= common>
          		<td text-align: left colSpan=1>
        			<span id="spanLPCSpecGrid" >
        			</span> 
        	  	</td>
        	</tr>
        </table>
    </Div>
  <table>
  
        <input type="hidden" name="AppObj">
     <input type=hidden id="fmtransact" name="fmtransact">
      <input type=hidden id="EdotNo" name="EdorNo">
      <input type=hidden id="ContType" name="ContType">
      <input type=hidden id="GrpContNo" name="GrpContNo">
      <input type=hidden id="InsuredNo" name="InsuredNo">  
      <input type=hidden id="PolNo" name="PolNo">
      <input type=hidden id="EdorValiDate" name="EdorValiDate">
      <input type=hidden id="SpecType" name="SpecType">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br /><br /><br /><br />
</body>
<script language="javascript">
var splFlag = "<%=request.getParameter("splflag")%>";
</script>
</html>
