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
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <SCRIPT src="GrpCustomerQualityManage.js"></SCRIPT>
  <%@include file="GrpCustomerQualityManageInit.jsp"%>
  
</head>

<body  onload="initForm();" >
  <form method=post name=fm id=fm target="fraSubmit" action="./GrpCustomerQualityManageSave.jsp">
    <div class="maxbox">
    <table class= common>
    	
    	<tr class= common>
        <TD  class= title5>
          �ͻ�����
          </TD>
         <td class="input5">
         <Input class="wid" class=code name=CustomerNo id=CustomerNo ondblclick="showAppnt();" onclick="showAppnt();" >
         </TD> 
        <TD  class= title5>
          �ͻ�����
        </TD>
        <TD  class= input5>
          <Input class="wid" class=common name=Name id=Name >
        </TD>
        </tr>
        <tr class= common>
        <TD  class= title5>
          ��λ����
          </TD>
          <TD  class= input5>
            <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="code" name=GrpNature id=GrpNature ondblclick="return showCodeList('GrpNature',[this]);"  onclick="return showCodeList('GrpNature',[this]);" onkeyup="return showCodeListKey('GrpNature',[this]);">            
          </TD>
          <TD style="display:none"  class= title5>
          ��λ����
        </TD>
        <TD style="display:none"  class= input5>
          <!--<Input class="wid" class="coolDatePicker" dateFormat="short" name=Birthday id=Birthday  >-->
          <Input class="coolDatePicker" onClick="laydate({elem: '#Birthday'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=Birthday id="Birthday"><span class="icon"><a onClick="laydate({elem: '#Birthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD>
        <TD  class= title5>�ͻ�Ʒ��״̬</TD>
        <TD  class= input5>
            <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=QualityFlag id=QualityFlag verify ="�ͻ�Ʒ��״̬|notnull" ondblclick="return showCodeList('GrpCQualityFlag',[this,QualityFlagName],[0,1]);" onclick="return showCodeList('GrpCQualityFlag',[this,QualityFlagName],[0,1]);" onkeyup="return showCodeListKey('GrpCQualityFlag',[this,QualityFlagName],[0,1]);"><input class=codename name=QualityFlagName id=QualityFlagName readonly=true>
          </TD> 
    	</tr>
   <!-- </Table>
    <div id="notPerson" style="display:'none'">
    <Table class=common>	-->
    	<tr style="display:none" class= common>
    	<TD  class= title5>
          �Ա�
        </TD>
        <TD  class= input5>
          <Input class="wid" class=common name=Sex id=Sex readonly=true>
        </TD>
        <TD  class= title5>
          ֤������
        </TD>
        <TD  class= input5>
          <Input class="wid" class=common name=IDType id=IDType readonly=true>
        </TD>
        </tr>
        <tr class= common>
        <TD style="display:none"  class= title5>
          ֤������
        </TD>
        <TD style="display:none"  class= input5>
          <Input class="wid" class=common name=IDNumber id=IDNumber readonly=true>
        </TD>
        
    	</tr>
   
    	</table></div>
    	<!--<INPUT CLASS=cssButton VALUE="��   ѯ" TYPE=button onclick="easyQueryClick()">-->
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
    <div id="noDetailInfo" style="display:none">
   <table>
    <tr class=common>
    <td class=common>
    <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,lpo);"></td>
    <td class=titleImg>��������ϸ</td>
    </td>
    </tr>
    </table> 
  
    <Div  id= "lpo" style= "display: ''">	
       <Table  class= common>
      	<tr>
    	 		<td text-align: left colSpan=1>
	 					<span id="spanCustomerQualityGrid" >
	 					</span> 
					</td>
           </tr>
       </Table><center>
            <INPUT CLASS=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"> 
            <INPUT CLASS=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
            <INPUT CLASS=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
            <INPUT CLASS=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();"></center>
    </Div>  
    </div>
    <br>
    <div class="maxbox1">
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
          �ͻ�Ʒ�ʸ���״̬
        </TD>
        <TD  class= input5>
            <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=CQualityFlag id=CQualityFlag verify ="Ʒ��״̬ά��|notnull" ondblclick="return showCodeList('GrpCQualityFlag',[this,GrpCQualityFlagName],[0,1]);" onclick="return showCodeList('GrpCQualityFlag',[this,GrpCQualityFlagName],[0,1]);" onkeyup="return showCodeListKey('GrpCQualityFlag',[this,GrpCQualityFlagName],[0,1]);"><input class=codename name=GrpCQualityFlagName id=GrpCQualityFlagName readonly=true>
          </TD> 
    	<!--TD  class= title>
          ԭ�����
        </TD>
        <TD  class= input>
            <Input class="codeno" name=CReasonType ondblclick="return showCodeList('CReasonType',[this,CReasonTypeName],[0,1]);" onkeyup="return showCodeListKey('CReasonType',[this,CReasonTypeName],[0,1]);"><input class=codename name=CReasonTypeName readonly=true>
          </TD--> 
        <TD  class= title5></TD>
        <TD  class= input5></TD>
    	</tr>	  
    	<!--</table>
    
  <table width="20%" height="2%" class= common>-->
    <TR  class= common> 
      <TD class= title5> ԭ�� </TD>
    
      <TD class="input" colspan="3"><textarea name="Reason" id="Reason" cols="146" rows="4" class="common" ></textarea></TD>
    </TR>
  </table>
  </div>
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
