<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
//�������ƣ�CutBonus.jsp
//�����ܣ��ֺ촦��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<html>
<%
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	int curYear = Integer.parseInt(PubFun.getCurrentDate().substring(0,4)) - 1;
%>
<script>
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>  
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="GrpCalBonusPrePare.js"></SCRIPT>
  <%@include file="GrpCalBonusPrePareInit.jsp"%>

  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>�ֺ촦��</title>
</head>
<body  onload="initForm();" >
  <form method=post name=fm target="fraSubmit" action= "./GrpCalBonusPrePareChk.jsp">
    <!-- ������Ϣ���� -->
    <table class= common border=0 width=100%>
    	<tr>
        <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,moreq);"></td>
        <td class= titleImg>�������ѯ������</td></tr>
		</table>
        <Div  id= "moreq" style= "display: ''" class="maxbox1">
    <table  class= common>
				<TR  class= common>
          <TD  class= title5>������</TD> 
	      	<TD  class= input5><Input class="wid" class=common name=FiscalYear id=FiscalYear value=<%=curYear%>  verify="������|NOTNULL"></TD>
					<TD  class= title5>�ŵ���</TD>
	        <TD  class= input5><Input class="wid" class=common name=GrpContNo id=GrpContNo ></TD>
            </TR>
           <TR  class= common> 
          <TD  class= title5>���ֱ���</TD>
          <TD  class= input5><Input class="wid" class=common name=RiskCode id=RiskCode value='212401' verify="���ֱ���|NOTNULL"></TD>
          <TD  class= title5>��Ч����</TD>
	        <TD  class= input5><Input class="coolDatePicker" onClick="laydate({elem: '#BDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=BDate id="BDate"><span class="icon"><a onClick="laydate({elem: '#BDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </TD>
				</TR> 	 		  
        <TR  class= common>
            
		  		<TD  class= title5>��Чֹ��</TD>
	      	<TD  class= input5><Input class="coolDatePicker" onClick="laydate({elem: '#EDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=EDate id="EDate"><span class="icon"><a onClick="laydate({elem: '#EDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
					<TD  class= title5>�ֺ�״̬</TD>
	        <TD  class= input5><Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="code" name=State id=State CodeData="0|^0|����������^3|�ŵ����ֺ�������^4|�ŵ����ֺ�������" 
	        		ondblClick="showCodeListEx('State',[this],[0,1]);" onclick="showCodeListEx('State',[this],[0,1]);" onkeyup="showCodeListKeyEx('State',[this],[0,1]);"></TD>     		
        </TR>
    </table>
		</Div>
    <!--<INPUT VALUE="��    ѯ" type =button class=cssButton onclick="easyQueryClick();"> 	
		<INPUT VALUE="�����޸�" type =button class=cssButton onclick="submitForm();" name='update'> --><br>
        <a href="javascript:void(0);" class="button" onClick="easyQueryClick();">��    ѯ</a>
        <a href="javascript:void(0);" class="button" onclick="submitForm();" name='update'>�����޸�</a><br><br><br>
		<p>
  	<Div  id= "divLCPol1" style= "display: ''" align=center>
    	<table  class= common>
     		<tr  class= common>
    	  	<td text-align: left colSpan=1><span id="spanPolGrid" ></span></td>
				</tr>
  		</table>
      <INPUT VALUE="��ҳ" type =button class=cssButton90 onclick="getFirstPage();"> 
      <INPUT VALUE="��һҳ" type =button class=cssButton91 onclick="getPreviousPage();">
      <INPUT VALUE="��һҳ" type =button class=cssButton92 onclick="getNextPage();"> 
      <INPUT VALUE="βҳ" type =button class=cssButton93 onclick="getLastPage();"> 					
  	</div>      
		<p>
    <table class= common border=0 width=100%>
    	<tr>
        <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,kop);"></td>
        <td class= titleImg>��������������</td></tr>
		</table>
        <Div  id= "kop" style= "display: ''" class="maxbox">
    <table  class= common>
				<TR  class= common>
		  		<TD  class= title5>ʵ��Ͷ��������</TD>
	      	<TD  class= input5><Input class="wid" class=common name=ActuRate id=ActuRate value="0.035" ></TD>
					<TD  class= title5>��֤��������</TD>
	        <TD  class= input5><Input class="wid" class=common name=EnsuRateDefault id=EnsuRateDefault value="0.025"></TD></TR>
                <TR  class= common> 		
					<TD  class= title5>�ֺ����</TD>
	        <TD  class= input5><Input class="wid" class=common name=AssignRate id=EnsuRateDefault value="0.7"></TD>     		
				</TR>
    </table>
    </Div>
			<input type=hidden id="fmtransact" name="fmtransact">
			<!--<INPUT VALUE="ȫ������" type =button class=cssButton onclick="saveAll();" name='insAll'> 
			<INPUT VALUE="ȫ���޸�" type =button class=cssButton onclick="updateAll();" name='updAll'>--><br>
            <a href="javascript:void(0);" class="button" onclick="saveAll();" name='insAll'>ȫ������</a>
            <a href="javascript:void(0);" class="button" onclick="updateAll();" name='updAll'>ȫ���޸�</a> 
<!--	    <INPUT VALUE="ɾ������" class=common TYPE=button  onclick = "deleteParm();">  -->

    <table>
      <tr>
					<TD  class= titleImg>ע�⣺�ֺ�״̬Ϊ�ս��в�ѯʱ���Ƕ�δ���в������õı�������ɸѡ��</TD>
      </tr>
    </table> 
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
