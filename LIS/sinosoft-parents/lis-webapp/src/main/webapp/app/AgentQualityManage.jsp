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
  
  <SCRIPT src="AgentQualityManage.js"></SCRIPT>
  <%@include file="AgentQualityManageInit.jsp"%>
  
</head>

<body  onload="initForm();" >
  <form method=post name=fm id=fm target="fraSubmit" action="./AgentQualityManageSave.jsp">
    <div class="maxbox1">
    <table class= common>
    	
    	<tr class= common>
        <TD  class= title5>
          ҵ��Ա����
          </TD>
         <td class="input5">
         <Input class="wid" class=code name=AgentCode id=AgentCode ondblclick="queryAgent();" onclick="queryAgent();" >
         </TD> 
        <!--TD  class= title>
          ҵ��Ա����
        </TD>
        <TD  class= input>
          <Input class=common name=Name readonly=true>
        </TD-->
        <TD class=title5>
         �������
        </TD>
		<TD class=input5>
		    <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=ManageCom id=ManageCom verify="�������|code:station" ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onclick="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName id=ManageComName readonly=true elementtype=nacessary>
		</TD>

    	</tr>
    	<tr class= common>
        <TD  class= title5>Ʒ�ʼ�¼</TD>
        <TD  class= input5>
            <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=QualityFlag id=QualityFlag verify ="Ʒ�ʼ�¼|notnull" ondblclick="return showCodeList('QualityFlag',[this,QualityFlagName],[0,1]);" onclick="return showCodeList('QualityFlag',[this,QualityFlagName],[0,1]);" onkeyup="return showCodeListKey('QualityFlag',[this,QualityFlagName],[0,1]);"><input class=codename name=QualityFlagName id=QualityFlagName readonly=true>
          </TD> 
        <TD  class= title5>
          ¼������
        </TD>
        <TD  class= input5>
          
          <Input class="coolDatePicker" onClick="laydate({elem: '#InputDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=InputDate id="InputDate"><span class="icon"><a onClick="laydate({elem: '#InputDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD>
    	</tr>
    </table></div>
    	
    	<!--<INPUT CLASS=cssButton VALUE="��   ѯ" TYPE=button onclick="easyQueryClick()">-->
        <a href="javascript:void(0);" class="button" onClick="easyQueryClick();">��    ѯ</a><br><br><br>
    <Div  id= "divAgentQuality" style= "display: ''" align=center>	
       <Table  class= common>
      	<tr>
    	 		<td text-align: left colSpan=1>
	 					<span id="spanAgentGrid" >
	 					</span> 
					</td>
           </tr>
       </Table>
           
    </Div>  
 <table>
    <tr class=common>
    <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,peer);"></td>
    <td class=titleImg>��������ϸ</td>
    </td>
    </tr>
    </table>   
    <Div  id= "peer" style= "display: ''" align=center>	
       <Table  class= common>
      	<tr>
    	 		<td text-align: left colSpan=1>
	 					<span id="spanAgentQualityGrid" >
	 					</span> 
					</td>
           </tr>
       </Table>
           
    </Div>  
    
    <div class="maxbox1">
      <table class= common>
    	
    	<tr class= common>
        <TD  class= title5>
          ҵ��Ա����
          </TD>
         <td class="input5">
            <Input class="wid" class=common name=AgentCodeSel id=AgentCodeSel readonly=true>
         </TD> 
        <TD  class= title5>
          ҵ��Ա����
        </TD>
        <TD  class= input5>
          <Input class="wid" class=common name=NameSel id=NameSel readonly=true>
        </TD>
        </tr>
        <tr class= common>
        <TD class=title5>
         �������
        </TD>
		<TD class=input5>
		    <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=ManageComSel id=ManageComSel verify="�������|code:station" ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onclick="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName id=ManageComName readonly=true elementtype=nacessary>
		</TD>
    	</tr>
    	<tr class= common>
        <TD  class= title5>
          ֤������
        </TD>
        <TD  class= input5>
          <Input class="wid" class=common name=IDTypeSel id=IDTypeSel readonly=true>
        </TD>
        <TD  class= title5>
          ֤������
        </TD>
        <TD  class= input5>
          <Input class="wid" class=common name=IDNumberSel id=IDNumberSel readonly=true>
        </TD>
       </tr>
    	  <tr class= common>
    	    <TD  class= title5>
          Ʒ��״̬ά��
        </TD>
        <TD  class= input5>
            <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=QualityState id=QualityState verify ="Ʒ��״̬ά��|notnull" ondblclick="return showCodeList('QualityState',[this,QualityStateName],[0,1]);" onclick="return showCodeList('QualityState',[this,QualityStateName],[0,1]);" onkeyup="return showCodeListKey('QualityState',[this,QualityStateName],[0,1]);"><input class=codename name=QualityStateName id=QualityStateName readonly=true>
          </TD> 
    	<TD  class= title5>
          ԭ�����
        </TD>
        <TD  class= input5>
            <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=ReasonType id=ReasonType ondblclick="return showCodeList('ReasonType',[this,ReasonTypeName],[0,1]);" onclick="return showCodeList('ReasonType',[this,ReasonTypeName],[0,1]);" onkeyup="return showCodeListKey('ReasonType',[this,ReasonTypeName],[0,1]);"><input class=codename name=ReasonTypeName id=ReasonTypeName readonly=true>
          </TD> 
        
    	</tr>	  
    
  
    <TR  class= common> 
      <TD class= title5> ԭ�� </TD>
    
      <TD class= input colspan="3"><textarea name="Reason" cols="146" rows="4" class="common" ></textarea></TD>
    </TR>
  </table></div>
  
  <input type="hidden" name= "PrtNo" value= "">
  <input type="hidden" name= "ContNo" value= "">


 <!-- <INPUT CLASS=cssButton VALUE="ȷ   ��" TYPE=button onclick="InsertClick()">-->
  <a href="javascript:void(0);" class="button" onClick="InsertClick();">ȷ    ��</a>
  <!--INPUT CLASS=cssButton VALUE="��  ��" TYPE=button onclick="UpdateClick()"-->
  
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span> 
<br><br><br><br>
</body>
</html>
