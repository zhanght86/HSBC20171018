<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�GroupUWAuto.jsp
//�����ܣ�����˱�����
//�������ڣ�2004-12-08 11:10:36
//������  ��HEYQ
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<html>
<%
  //�����¸���	
	String tContNo = "";
	
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>	
	var contNo = "<%=tContNo%>";          //���˵��Ĳ�ѯ����.
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
	var comcode = "<%=tGI.ComCode%>"; //��¼��½����
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="GroupUWModify.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="GroupUWModifyInit.jsp"%>
  <title>�Զ��˱� </title>
</head>
<body  onload="initForm();" >
  <form action="./GroupUWModifyChk.jsp" method=post name=fm id="fm" target="fraSubmit">
    <!-- ������Ϣ���� -->
    <table class= common border=0 width=100%>
    	<tr>
        <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
        </td>
		    <td class= titleImg align= center>�������ѯ����������</td>
	    </tr>
    </table>
    <div class="maxbox1">
    <Div  id= "divFCDay" style= "display: ''">
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title5>Ͷ������</TD>
          <TD  class= input5>
            <Input class= "common wid" name=ProposalGrpContNo id="ProposalGrpContNo">
          </TD>
          <TD  class= title5>�������</TD>
          <TD  class= input5>
            <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name=ManageCom id="ManageCom" value="<%=tGI.ManageCom%>" onclick="return showCodeList('station',[this]);" ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);">
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>Ͷ����λ�ͻ���</TD>
          <TD  class= input5>
            <Input class= "common wid" name=GrpNo id="GrpNo">
          </TD>
          <TD  class= title5>Ͷ����λ����</TD>
          <TD  class= input5>
            <Input class= "common wid" name=Name id="Name">
          </TD>        
        </TR>     
    </table>
  </Div>
  </div>
  <a href="javascript:void(0)" class=button onclick="easyQueryClick();">��  ѯ</a>
  <!-- <INPUT VALUE="��  ѯ" class = cssButton TYPE=button onclick="easyQueryClick();">  -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCGrp1);">
    		</td>
    		<td class= titleImg>
    			 ����Ͷ������Ϣ
    		</td>
    	</tr>
    	<tr>
    	    <INPUT  type= "hidden" class= Common name= GrpContNo id="GrpContNo"  value= "">
          <INPUT  type= "hidden" class= Common name= MissionID  id="MissionID" value= "">
          <INPUT  type= "hidden" class= Common name= SubMissionID id="SubMissionID"  value= "">
    	</tr>
    </table>
  	<Div  id= "divLCGrp1" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanGrpGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="��  ҳ"  class = cssButton90 TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="��һҳ"  class = cssButton91  TYPE=button onclick="getPreviousPage();"> 			
      <INPUT VALUE="��һҳ"  class = cssButton92  TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="β  ҳ"  class = cssButton93 TYPE=button onclick="getLastPage();"> 					
  	</div>
  	<p>
      <a href="javascript:void(0)" class=button onclick="GrpUWModify();">�˱�����</a>
      <!-- <INPUT VALUE="�˱�����" class = cssButton TYPE=button onclick="GrpUWModify();">  -->
      <!--INPUT VALUE="���������־" class = cssButton TYPE=button onclick="SetSpecialFlag();"--> 
  	</p>
  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
