<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�GrpUWManuRReport.jsp
//�����ܣ�����Լ�˹��˱�������鱨��¼��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������ zhangxing   ��������   2006-09-35  ����ԭ��/����
%>
<html> 
<head >
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="GrpUWManuRReport.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title> ����Լ������鱨�� </title>
  <%@include file="GrpUWManuRReportInit.jsp"%>
</head>
<body  onload="initForm('<%=tContNo%>','<%=tPrtNo%>','<%=tMissionID%>','<%=tSubMissionID%>','<%=tGrpContNo%>');" >
  <form method=post id="fm" name=fm target="fraSubmit" action= "./GrpUWManuRReportChk.jsp">
    <!-- �����˱���¼���֣��б� -->
     <table class= common border=0 width=100%>
    	<tr>
		    <td class= titleImg align= center>����������Ϣ</td>
	    </tr>
    </table>
    <div class="maxbox1">
    	<table  class= common align=center>
    	    <td class= title>   Ͷ������ </td>
          <TD  class= input>  <Input class= "readonly wid" id="GrpContNo" name=GrpContNo readonly>  </TD>
    	     
    	    <td class= title>   �˱���  </td>
          <TD  class= input>   <Input class= "readonly wid" id="Operator" name=Operator readonly>  </TD>
          <TD  class= title> ����ԭ��  </TD>
        
          <td class=input>
          <Input class=codeno id="RReportReason" name=RReportReason style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('rreportreason',[this,RReportReasonname],[0,1]);" onkeyup="return showCodeListKey('rreportreason',[this,RReportReasonname],[0,1]);"><Input class=codename id="RReportReasonname" name=RReportReasonname >                    
          </td>
    	</tr>
    	<tr>
    	    <TD class=title> ���ն��� </TD>  
          <TD  ><Input class= "codeno" id="RReport" name = RReport style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('grprreport',[this,RReportTypeName],[0,1]);" onkeyup="return showCodeListKey('grprreport',[this,RReportTypeName],[0,1]);" ><Input class = codename id="RReportTypeName" name=RReportTypeName readonly = true> </TD>
                     
          <TD  class = title> �ͻ����� </TD>
          <TD  ><Input class="common wid" id="CustomerNo"  name=CustomerNo></TD>
           
          <TD  class = title>	�ͻ����� </TD>
          <TD  ><Input class="common wid" id="CustomerName" name=CustomerName ></TD>
          </TR>     
    	</tr>
    </table>
    </div>
    
    
    <table>
    	<tr>
          <td class=common>    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpec1);"></td>
    		  <td class= titleImg>	 ������Ŀ¼��</td>                            
    	</tr>	
    </table>
    <Div  id= "divUWSpec" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  							<span id="spanInvestigateGrid"></span> 
  		  			</td>
  				</tr>
    	</table>
    </div>
    <div class="maxbox1">
      <table class=common>
         <TR  class= common> 
           <TD  class= common> ����������Ϣ </TD>
         </TR>
         <TR  class= common>
           <TD  class= common>
             <textarea name="Contente" id=Contente cols="120" rows="3" class="common" >
             </textarea>
           </TD>
         </TR>
      </table>
      </div>
      <INPUT type= "hidden" id="ProposalNoHide" name= "ProposalNoHide" value= "">
      <INPUT type= "hidden" id="ContNo" name= "ContNo" value= "">
      <INPUT type= "hidden" id="PrtNo" name= "PrtNo" value= "">
      <INPUT type= "hidden" id="MissionID" name= "MissionID" value= "">
      <INPUT type= "hidden" id="SubMissionID" name= "SubMissionID" value= "">
      <INPUT type= "hidden" id="SubNoticeMissionID" name= "SubNoticeMissionID" value= "">
      <INPUT type= "hidden" id="Flag"��name= "Flag"  value = "">
      <INPUT type= "hidden" id="EdorNo" name= "EdorNo" value= "">
      <INPUT type= "hidden" id="EdorType" name= "EdorType" value= "">
      <INPUT type= "button" id="sure" name= "sure" value="ȷ ��" class= cssButton onclick="submitForm()">			
		
    <!--��ȡ��Ϣ-->
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
