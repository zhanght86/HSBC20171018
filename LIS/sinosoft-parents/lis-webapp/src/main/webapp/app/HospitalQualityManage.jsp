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
<title>���ҽԺƷ�ʹ���ά��</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <SCRIPT src="HospitalQualityManage.js"></SCRIPT>
  <%@include file="HospitalQualityManageInit.jsp"%>
  
</head>

<body  onload="initForm();" >
  <form method=post name=fm id=fm target="fraSubmit" action="./HospitalQualityManageSave.jsp">
   <Div  id= "divAgentQuality" style= "display: ''"  class="maxbox">	 
    <table class= common>
 
    	<tr class= common>
        <TD  class= title5>
          ���ҽԺ����
          </TD>
         <td class="input5" >
         <Input class="wid" class=code name=HospitalCode1 id=HospitalCode1 ondblclick="showHospital();" onclick="showHospital();" >
         </TD> 
        <TD  class= title5>
          ���ҽԺ����
        </TD>
        <TD  class= input5>
          <Input class="wid" class=common name=HospitalName1 id=HospitalName1 >
        </TD>
        </tr>
        <tr class= common>
    		<TD  class= title5>
      		�����������
    		</TD>
    		<TD  class= input5>
      	 <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= 'codeno' name=ManageCom1 id=ManageCom1  ondblclick="return showCodeList('ComCode',[this,ManageComName1],[0,1]);" onclick="return showCodeList('ComCode',[this,ManageComName1],[0,1]);" onkeyup="return showCodeListKey('CodeCode', [this,ManageComName1],[0,1]);"><input class=codename readonly=true name=ManageComName1 id=ManageComName1 elementtype=nacessary>
    		</TD>
    		<TD  class= title5>
          ¼������
        </TD>
        <TD  class= input5>
          <Input class="coolDatePicker" onClick="laydate({elem: '#InputDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=InputDate id="InputDate"><span class="icon"><a onClick="laydate({elem: '#InputDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD>
     	</tr>
     	<tr class= common>
    		<TD  class= title5>���Ʒ��״̬</TD>
        	<TD  class= input5>
            <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=FQualityFlag id=FQualityFlag ondblclick="return showCodeList('hospitalqaflag',[this,FQualityFlagName],[0,1]);" onclick="return showCodeList('hospitalqaflag',[this,FQualityFlagName],[0,1]);" onkeyup="return showCodeListKey('hospitalqaflag',[this,FQualityFlagName],[0,1]);"><input class=codename name=FQualityFlagName id=FQualityFlagName readonly=true>
            </TD>   
            <TD  class= title5></TD>
             	<TD  class= input5></TD>	
     	</tr>
     </Table></Div>
    <!--<INPUT CLASS=cssButton VALUE="��   ѯ" TYPE=button onclick="easyQueryClick()"><br>-->
    <a href="javascript:void(0);" class="button" onClick="easyQueryClick();">��    ѯ</a><br><br><br>
       <Div  id= "divAgentQuality" style= "display: ''" align=center>	
       <Table  class= common>
      	<tr>
    	 		<td text-align: left colSpan=1>
	 					<span id="spanHospitalGrid" >
	 					</span> 
					</td>
           </tr>
       </Table>
            
    </Div><br>
    <div class="maxbox1">
   <table class= common>
   	 <tr class= common>
    	<TD  class= title5>Ʒ��״̬</TD>
        <TD  class= input5>
            <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=QualityFlag id=QualityFlag verify ="���Ʒ��״̬|notnull&code:hospitalqaflag" ondblclick="return showCodeList('hospitalqaflag',[this,QualityFlagName],[0,1]);" onclick="return showCodeList('hospitalqaflag',[this,QualityFlagName],[0,1]);" onkeyup="return showCodeListKey('hospitalqaflag',[this,QualityFlagName],[0,1]);"><input class=codename name=QualityFlagName id=QualityFlagName readonly=true>
        </TD> 
       <TD  class= title5>ԭ�����</TD>
        <TD  class= input5>
            <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=QualityFlagType id=QualityFlagType verify ="ԭ�����|notnull&code:hospitalqaflagsub" ondblclick="getClickHospital();" onclick="getClickHospital();" onkeyup="return getClickUpHospital();"><input class=codename name=QualityFlagTypeName id=QualityFlagTypeName readonly=true>
        </TD> 
    </tr>
  <!-- </Table> 
  <table width="20%" height="2%" class= common>-->
  	

    <TR  class= common> 
      <TD class= title5> ԭ�� </TD>
      <TD class=input colspan="3"><textarea name="Reason" cols="146" rows="4" class="common" ></textarea></TD>
    </TR>
  </table></div>
  
  <input type="hidden" name= "PrtNo" value= "">
  <input type="hidden" name= "ContNo" value= "">
	<input type="hidden" name= "HospitalCode" value= ""  verify ="���ҽԺ����|notnull&code:pehospital">
	<input type="hidden" name= "HospitalName" value= "">
	<input type="hidden" name= "ManageCom" value= "">
	<input type="hidden" name= "ManageComName" value= "">

  <!--<INPUT CLASS=cssButton VALUE="ȷ   ��" TYPE=button onclick="InsertClick()">-->
  <a href="javascript:void(0);" class="button" onClick="InsertClick();">ȷ    ��</a>
  <!--INPUT CLASS=cssButton VALUE="��  ��" TYPE=button onclick="UpdateClick()"-->
  
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span> 
<br><br><br><br>
</body>
</html>
