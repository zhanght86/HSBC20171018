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
	var QueryFlag = "<%=request.getParameter("QueryFlag")%>";
//	var OperatePos = "<%=request.getParameter("OperatePos")%>";
</script>

<head >
<title>�ͻ�Ʒ�ʹ��� </title>
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
  
  <SCRIPT src="UWCustomerQuality.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <%@include file="UWCustomerQualityInit.jsp"%>
  
</head>

<body  onload="initForm();" >
  <form method=post name=fm id="fm" target="fraSubmit" action="./UWCustomerQualitySave.jsp">
   <div class="maxbox"> 
    <table class= common>
    	<TR  class= common>
    	  
    	  <td class=title>
           ��ѡ��ͻ�
         </td>
                                
         <td class=input>
         <Input class="codeno" name=customer id="customer" style="background: url(../common/images/select--bg_03.png) no-repeat right center; " onClick="return showCodeList('customer',[this,customername],[0,1],null,tContNo,'ContNo');" onDblClick="return showCodeList('customer',[this,customername],[0,1],null,tContNo,'ContNo');" onKeyUp="return showCodeListKey('customer',[this,customername],[0,1]);" ><Input class="codename" name="customername" id="customername" readonly elementtype=nacessary>
         </td>
        <TD  class= title>
          �ͻ���
        </TD>
        <TD  class= input>
          <Input class="common wid" name=CustomerNo id="CustomerNo" readonly=true>
        </TD>
        <TD  class= title>
          ����
        </TD>
        <TD  class= input>
          <Input class="common wid" name="Name" id="Name" readonly=true>
        </TD> 
      </TR>
    
   <!-- </table>
    
    <table class= common>-->
    	
    	<tr class= common>
        	
        <TD  class= title>
          ��������
        </TD>
        <TD  class= input>
          <Input class="coolDatePicker" dateFormat="short" name="Birthday"  id="Birthday" onClick="laydate({elem:'#Birthday'});"><span class="icon"><a onClick="laydate({elem: '#Birthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD>
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
          <Input class="common wid" name=IDType id="IDType" readonly=true>
        </TD>
    	</tr>
    	
    	<tr>
        
        <TD  class= title>
          ֤������
        </TD>
        <TD  class= input>
          <Input class="common wid" name=IDNumber id="IDNumber" readonly=true>
        </TD>
         <TD  class= title>
          �ͻ�Ʒ�ʱ��
        </TD>
        <TD  class= input>
            <Input class="codeno" name=QualityFlag id="QualityFlag" style="background: url(../common/images/select--bg_03.png) no-repeat right center; " onClick="return showCodeList('QualityFlag',[this,QualityFlagName],[0,1]);" onDblClick="return showCodeList('QualityFlag',[this,QualityFlagName],[0,1]);" onKeyUp="return showCodeListKey('QualityFlag',[this,QualityFlagName],[0,1]);"><input class=codename name=QualityFlagName id="QualityFlagName" readonly=true>
          </TD> 
           <td class= title></td>
         <td class= input></td>
    	</tr>
    	<!--</table>
    	
      <table class= common>-->
      	
    	   
    	
    </table>


  
  <table class= common>
    <TR  class= common> 
      <TD class= title> ��ע��Ϣ </TD>
    </TR>
    <TR  class= common>
      <TD colspan="6" style="padding-left:16px"><textarea name="Remark" id="Remark" cols="226" rows="4" class="common" ></textarea></TD>
    </TR>
  </table>
  
  <input type="hidden" name= "PrtNo" value= "">
  <input type="hidden" name= "ContNo" value= "">


  <INPUT CLASS=cssButton VALUE="ȷ  ��" name=sure TYPE=button onClick="UpdateClick()">
  <!-- modified by liuyuxiao 2011-05-24 ��ȥ���أ���tab������ -->
  <INPUT CLASS=cssButton VALUE="��  ��" TYPE=button onClick="returnParent()" style= "display: none">
  
</form>
<br><br><br><br><br>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span> 

</body>
</html>
