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
  
  <SCRIPT src="CustomerQualityManage.js"></SCRIPT>
  <%@include file="CustomerQualityManageInit.jsp"%>
  
</head>

<body  onload="initForm();" >
  <form method=post name=fm id=fm target="fraSubmit" action="./CustomerQualityManageSave.jsp">
    <div class="maxbox1">
    <table class= common>
    	
    	<tr class= common>
        <TD  class= title5>
          �ͻ�����
          </TD>
         <td class="input5">
         <Input class="wid" class=code name=CustomerNo id=CustomerNo ondblclick="showCustomer();" onclick="showCustomer();" >
         </TD> 
        <TD  class= title5>
          ��������
        </TD>
        <TD  class= input5>
          
          <Input class="coolDatePicker" onClick="laydate({elem: '#Birthday'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=Birthday id="Birthday"><span class="icon"><a onClick="laydate({elem: '#Birthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD>
        </tr>
        <tr class= common>
        <TD  class= title5>�ͻ�Ʒ��״̬</TD>
        <TD  class= input5>
            <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=QualityFlag id=QualityFlag verify ="�ͻ�Ʒ��״̬|notnull" ondblclick="return showCodeList('CQualityFlag',[this,QualityFlagName],[0,1]);" onclick="return showCodeList('CQualityFlag',[this,QualityFlagName],[0,1]);" onkeyup="return showCodeListKey('CQualityFlag',[this,QualityFlagName],[0,1]);"><input class=codename name=QualityFlagName id=QualityFlagName readonly=true>
          </TD> 
		 <TD  class= title5>
          ��ʼ����
        </TD>
        <TD  class= input5>
          
          <Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD>
    	</tr>
    <tr class= common>
        <TD  class= title5>
          ��ֹ����
        </TD>
        <TD  class= input5>
          
          <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD>
        <TD  class= title5>
          
        </TD>
<!--        <TD  class= input5>
          <Input class= readonly >
        </TD>-->
    </tr>
    </table>
    	</div>
    	<!--<INPUT CLASS=cssButton VALUE="��   ѯ" TYPE=button onclick="easyQueryClick()"><br>-->
        <a href="javascript:void(0);" class="button" onClick="easyQueryClick();">��    ѯ</a><br><br><br>
       <Div  id= "divAgentQuality" style= "display: ''" align=center>	
       <Table  class= common>
      	<tr>
    	 		<td text-align: left colSpan=1>
	 					<span id="spanCustomerGrid" >
	 					</span> 
					</td>
           </tr>
       </Table>
           
    </Div>
   <table>
    <tr class=common>
    <td class=common>
    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" >
    <td class=titleImg>��������ϸ</td>
    </td>
    </tr>
    </table> 
  
    <Div  id= "divAgentQuality" style= "display: ''" align=center>	
       <Table  class= common>
      	<tr>
    	 		<td text-align: left colSpan=1>
	 					<span id="spanCustomerQualityGrid" >
	 					</span> 
					</td>
           </tr>
       </Table>
            
    </Div>  
    
    <div class="maxbox">
      <table class= common>
    	
    	<tr class= common>
        <TD  class= title5>
          �ͻ�����
        </TD>
        <TD  class= input5>
          <Input class="wid" class=common name=NameSel id=NameSel readonly=true>
        </TD>
        <TD  class= title5>
          �ͻ���
          </TD>
         <td class="input5">
            <Input class="wid" class=common name=CustomerNoSel id=CustomerNoSel readonly=true>
         </TD> 
         <!--  
        <TD class=title>
         �������
        </TD>
		<TD class=input>
		    <Input class=codeno name=ManageComSel verify="�������|code:station" ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName readonly=true elementtype=nacessary>
		</TD>
		-->
    	</tr>
    	  <tr>
    	    <TD  class= title5>
          �ͻ�Ʒ��״̬
        </TD>
        <TD  class= input5>
            <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=CQualityFlag id=CQualityFlag verify ="Ʒ��״̬ά��|notnull" ondblclick="return showCodeList('CQualityFlag',[this,CQualityFlagName],[0,1]);" onclick="return showCodeList('CQualityFlag',[this,CQualityFlagName],[0,1]);" onkeyup="return showCodeListKey('CQualityFlag',[this,CQualityFlagName],[0,1]);"><input class=codename name=CQualityFlagName id=CQualityFlagName readonly=true>
          </TD> 
    	<!--TD  class= title>
          ԭ�����
        </TD>
        <TD  class= input>
            <Input class="codeno" name=CReasonType ondblclick="return showCodeList('CReasonType',[this,CReasonTypeName],[0,1]);" onkeyup="return showCodeListKey('CReasonType',[this,CReasonTypeName],[0,1]);"><input class=codename name=CReasonTypeName readonly=true>
          </TD--> 

    	</tr>	  
    <TR  class= common> 
      <TD class= title5> ԭ�� </TD>
   
      <TD class= input colspan="3"><textarea name="Reason" id="Reason" cols="146" rows="4" class="common" ></textarea></TD>
    </TR>
  </table>
  
  <input type="hidden" name= "PrtNo" value= "">
  <input type="hidden" name= "ContNo" value= "">


  <!--<INPUT CLASS=cssButton VALUE="ȷ   ��" TYPE=button onclick="InsertClick()">-->
  <a href="javascript:void(0);" class="button" onClick="InsertClick();">ȷ    ��</a>
  <!--INPUT CLASS=cssButton VALUE="��  ��" TYPE=button onclick="UpdateClick()"-->
  
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span> 
<br><br><br><br>
</body>
</html>
