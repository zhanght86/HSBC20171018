<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�UWCustomerQuality.jsp
//�����ܣ��ͻ�Ʒ�ʹ���
//�������ڣ�2005-06-18 11:10:36
//������  ��ccvip
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<html>
<%
  //�����¸���
	String tGrpPolNo = "00000000000000000000";
	String tContNo = "00000000000000000000";
	
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>

<script>
	var contNo = "<%=tContNo%>";          //���˵��Ĳ�ѯ����.
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
	
	var ContNo = "<%=request.getParameter("ContNo")%>";
	var tContNo = "<%=request.getParameter("ContNo")%>";
	//alert("ContNo="+ContNo);
	var PrtNo = "<%=request.getParameter("PrtNo")%>";
//	var OperatePos = "<%=request.getParameter("OperatePos")%>";
</script>

<head >
<title>ҵ��ԱƷ�ʹ��� </title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <SCRIPT src="UWAgentQuality.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <%@include file="UWAgentQualityInit.jsp"%>
  
</head>

<body  onload="initForm();" >
  <form method=post name=fm id="fm" target="fraSubmit" action="./UWAgentQualitySave.jsp">
   <div class="maxbox"> 
    <table class= common>
    	<TR  class= common>
    	  
    	  <td class=title>
           ��ѡ��ҵ��Ա
         </td>
                                
         <td class=input>
         <Input class="codeno" name=agent id="agent" style="background: url(../common/images/select--bg_03.png) no-repeat right center; " onClick="return showCodeList('agent',[this,agentname],[0,1],null);" onDblClick="return showCodeList('agent',[this,agentname],[0,1],null);" onKeyUp="return showCodeListKey('agent',[this,agentname],[0,1]);" ><Input class="codename" name=agentname id="agentname" readonly elementtype=nacessary>
         </td>
         <td class=title></td>
         <td class=input></td>
         <td class=title></td>
         <td class=input></td>
      </TR>
    
   <!-- </table>
    
    <table class= common>
    	-->
    	<tr class= common>
        	<TD  class= title>
          ��������
        </TD>
        <TD  class= input>
          <Input class="common wid" name=ManageCom id="ManageCom" readonly=true>
        </TD>
        <TD  class= title>
          ����
        </TD>
        <TD  class= input>
          <Input class="common wid" name=Name id="Name" readonly=true>
        </TD>
        <TD  class= title>
          ǩԼ����
        </TD>
        <TD  class= input>
          <Input class="coolDatePicker" dateFormat="short" name=EmployDate id="EmployDate" onClick="laydate({elem:'#EmployDate'});"><span class="icon"><a onClick="laydate({elem: '#EmployDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD>
       
    	</tr>
    	
    	<tr>
         <TD  class= title>
          �Ա�
        </TD>
        <TD  class= input>
          <Input class="common wid" name=Sex id="Sex" readonly=true>
        </TD>
        <TD  class= title>
          ֤������
        </TD>
        <TD  class= input>
          <Input class="common wid"  name=IDType id="IDType" readonly=true>
        </TD>
        <TD  class= title>
          ֤������
        </TD>
        <TD  class= input>
          <Input class="common wid" name=IDNumber id="IDNumber" readonly=true>
        </TD>
    	</tr>
    	<!--</table>
    	
      <table class= common>
      	-->
    	  <tr>
    	    <TD  class= title>
          Ʒ�ʼ�¼
        </TD>
        <TD  class= input>
            <Input class="codeno" name=QualityFlag id="QualityFlag" style="background: url(../common/images/select--bg_03.png) no-repeat right center; "  verify ="Ʒ�ʼ�¼|notnull" onClick="return showCodeList('QualityFlag',[this,QualityFlagName],[0,1]);" onDblClick="return showCodeList('QualityFlag',[this,QualityFlagName],[0,1]);" onKeyUp="return showCodeListKey('QualityFlag',[this,QualityFlagName],[0,1]);"><input class=codename id="QualityFlagName" name=QualityFlagName readonly=true>
          </TD> 
    	    <TD  class= title>
          �쳣Ʒ�����
        </TD>
        <TD  class= input>
            <Input class="codeno" name=UnusualType id="UnusualType" style="background: url(../common/images/select--bg_03.png) no-repeat right center; " onClick="return showCodeList('UnusualType',[this,UnusualTypeName],[0,1]);" onDblClick="return showCodeList('UnusualType',[this,UnusualTypeName],[0,1]);" onKeyUp="return showCodeListKey('UnusualType',[this,UnusualTypeName],[0,1]);"><input class=codename name=UnusualTypeName id="UnusualTypeName" readonly=true>
          </TD> 
         <td></td>
         <td></td>
    	  </tr>	  
    	
    </table>


  
  <table class= common>
    <TR  class= common> 
      <TD   class= title> ��ע��Ϣ </TD>
    </TR>
    <TR  class= common>
      <TD colspan="6" style="padding-left:16px"><textarea name="Remark" id="Remark" cols="160" rows="4" class="common" ></textarea></TD>
    </TR>
  </table></div>
  
  <input type="hidden" id="PrtNo" name= "PrtNo" value= "">
  <input type="hidden" id="ContNo" name= "ContNo" value= "">


  <INPUT CLASS=cssButton VALUE="ȷ  ��" TYPE=button onClick="InsertClick()">
  <!-- modified by liuyuxiao 2011-05-24 ��ȥ���أ���tab������ -->
  <INPUT CLASS=cssButton VALUE="��  ��" TYPE=button onClick="returnParent()" style= "display: none">
  
</form>
<br><br><br><br><br>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span> 

</body>
</html>
