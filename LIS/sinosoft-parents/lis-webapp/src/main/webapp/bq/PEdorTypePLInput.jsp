<html> 
<% 
//�������ƣ�
//�����ܣ�������ʧ�����
//�������ڣ�2005-5-24 11:10
//������  ��sinosoft
//���¼�¼��  ������    ��������     ����ԭ��/����     
%>
<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.pubfun.*"%>


<head >
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT> 
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT> 
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="./PEdor.js"></SCRIPT>
  <SCRIPT src="./PEdorTypePL.js"></SCRIPT>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@include file="PEdorTypePLInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form action="./PEdorTypePLSubmit.jsp" method=post name=fm id=fm target="fraSubmit">    
  <div class=maxbox1>
  <TABLE class=common>
    <TR  class= common> 
      <TD  class= title >��ȫ�����</TD>
      <TD  class= input > 
        <input class="readonly wid" readonly name=EdorAcceptNo id=EdorAcceptNo>
      </TD>
      <TD class = title >��������</TD>
      <TD class = input >
      	<Input class=codeno name=EdorType id=EdorType readonly><input class=codename name=EdorTypeName id=EdorTypeName readonly>
      </TD>
      <TD class = title >������</TD>
      <TD class = input >
      	<input class="readonly wid" readonly name=ContNo id=ContNo>
      </TD>   
    </TR>
    <TR  class= common> 
    	  <TD class =title>������������</TD>
    	     <TD class = input>    		
    		<input class="coolDatePicker" readonly name=EdorItemAppDate onClick="laydate({elem: '#EdorItemAppDate'});" id="EdorItemAppDate"><span class="icon"><a onClick="laydate({elem: '#EdorItemAppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
    	<TD class =title>��Ч����</TD>
    	<TD class = input>
    		<input class="coolDatePicker" readonly name=EdorValiDate onClick="laydate({elem: '#EdorValiDate'});" id="EdorValiDate"><span class="icon"><a onClick="laydate({elem: '#EdorValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
    	<TD class =title></TD>
    	<TD class = input></TD>
      </TR>
  </TABLE> 
  </div>
  <table>
   	<tr>
      <td class="common">
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPolGrid);">
      </td>
      <td class= titleImg>
        �ͻ�������Ϣ
      </td>
   	</tr>
   </table> 
  <Div  id= "divPolGrid" style= "display: ''">
        <table  class= common>
        	<tr  class= common>
          		<td text-align: left colSpan=1>
        			<span id="spanCustomerGrid" >
        			</span> 
        	  	</td>
        	</tr>
        </table>
     	

    </Div>
   
    <Div  id= "divPolInfo" style= "display: ''">
        <table>
            <tr>
                <td class="common">
                    <IMG  src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divLCGrpPol);">
                </td>
                <td class= titleImg>
                    ����������Ϣ
                </td>
            </tr>
        </table>
    <Div  id= "divLCGrpPol" style= "display: ''">
        <table  class= common>
        	<tr  class= common>
          		<td text-align: left colSpan=1>
        			<span id="spanPolGrid" >
        			</span> 
        	  	</td>
        	</tr>
        </table>					
    </Div>
    </DIV>
<Div  id= "divReportLostInfo" style= "display: ''">
     <table>
     	<tr>
      <td class="common">
        <IMG  src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divReportByLoseInfo);">
      </td>
      <td class= titleImg>
        ������ʧ������Ϣ
      </td>
   </tr>
   </table>

    <Div  id= "divReportByLoseInfo" class=maxbox1 style= "display: ''">
    	<table  class= common>
      	<TR class = common >
      		
      		<TD 	class = title>
           ��������
          </TD>
          <TD class = input>
           <input class="codeno" name=LostFlag id=LostFlag style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeList('lostflag',[this,LostFlagName],[0,1]);" onkeyup="showCodeListKey('lostflag', [this,LostFlagName],[0,1]);"><input class="codename" name=LostFlagName id=LostFlagName readonly=true>
          </TD>
         <TD 	class = title>
           &nbsp;
          </TD>
          <TD class = input>
           &nbsp;
          </TD>
          <TD  class= title>
          &nbsp;
        </TD>
          <TD  class= input>
           &nbsp;
          </TD>
        </TR>
      </table>
      <!--add by jiaqiangli 2008-08-05 ֻ��ѡ���ʧʱ����ʾ��Div-->
      <Div  id= "divLostDiv" style= "display: none">
      <table  class= common>
      	<TR class = common >
      		
      		<TD 	class = title>
           ��ʧ����
          </TD>
          <TD class = input>
           <!--input class="codeno" name=ReportByLoseForm readonly value="1" ondblclick="showCodeList('reportlosttype',[this,ReportByLoseFormName],[0,1]);" onkeyup="showCodeListKey('reportlosttype', [this,ReportByLoseFormName],[0,1]);"><input class="codename" name=ReportByLoseFormName value="�����ʧ" readonly=true-->
           <input class="codeno" name=ReportByLoseForm id=ReportByLoseForm readonly value="1" ><input class="codename" name=ReportByLoseFormName id=ReportByLoseFormName value="�����ʧ" readonly=true>
          </TD>
         <TD 	class = title>
           ��ʧԭ��
          </TD>
          <TD class = input>
           <input class="codeno" name=ReportByLoseReason id=ReportByLoseReason style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeList('lostreason',[this,ReportByLoseReasonName],[0,1]);" onkeyup="showCodeListKey('lostreason', [this,ReportByLoseReasonName],[0,1]);"><input class="codename" name=ReportByLoseReasonName id=ReportByLoseReasonName readonly=true>
          </TD>
          <TD  class= title>
          ��ʧ����
        </TD>
          <TD  class= input>
            <Input class="coolDatePicker" name=ReportByLoseDate onClick="laydate({elem: '#ReportByLoseDate'});" id="ReportByLoseDate"><span class="icon"><a onClick="laydate({elem: '#ReportByLoseDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
        </TR>
      </table>
      </Div>
      <!--add by jiaqiangli 2008-08-05 ֻ��ѡ���ʧʱ����ʾ��Div-->
    </Div>
</Div>
<Div id= "divEdorquery" style="display: ''">
<table class =common>
     	     <Input type=Button class = cssButton value=" �� �� " name=ReportByLose onclick="edorTypePLSave()">
     	     <Input type=Button class = cssButton value=" �� �� " onclick="returnParent()">
       		 <Input class= cssButton TYPE=button VALUE="���±��鿴" onclick="showNotePad();">
</table>
</Div>

  <input type=hidden id="fmtransact" name="fmtransact">
  <input type=hidden id="ContType" name="ContType">
  <input type=hidden name="EdorNo">
  <input type=hidden name="InsuredNo">
  <input type=hidden name="PolNo">
  <!--input type=hidden name="LostFlag"--> <!--��ʧΪtrue��ȡ����ʧΪfalse-->
  <input type=hidden name="SignDate">
  <input type=hidden name="Flag"><!--�������ϸ��ѯ���ã���Ϊ"1"-->
</form>
 <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
 <br /><br /><br /><br />
</body>
<script language="javascript">
	var splFlag = "<%=request.getParameter("splflag")%>";
	
</script>
</html>
