<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2003-1-22
//������  ��zhangxing
//���¼�¼��  ������    ��������     ����ԭ��/����

//�޸����ڣ�2004-2-17
%>


<head >

	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="RReportDealEach.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="RReportDealEachInit.jsp"%>

	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<title>������ѯ </title>
</head>


	<body  onload="initForm('<%=tContNo%>','<%=tMissionID%>','<%=tSubMissionID%>','<%=tCustomerNo%>','<%=tPrtSeq%>');" >
		 <form action="./RReportDealEachSave.jsp" method=post id="fm" name=fm target="fraSubmit">
		
  <form  name=fm > 
  
  
    <table>
    	<tr>
        <td class=common>
			<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpec1);">
    		</td>
    		<td class= titleImg>
    			��ͬ��Ϣ����
    		</td>
    	</tr>
    </table>
  
  
   <div id="DivLCCont" class="maxbox1" STYLE="display:''">
	<table class="common" id="table2">
		<tr CLASS="common">
			<td CLASS="title">Ͷ������
    		        </td>
			<td CLASS="input" COLSPAN="1">
			<input id="GrpContNo" NAME="GrpContNo" type=hidden VALUE CLASS="common wid" TABINDEX="-1"MAXLENGTH="20">
    		       
			<input id="ContNo1" NAME="ContNo1" VALUE CLASS="common wid" TABINDEX="-1"MAXLENGTH="20">
    		         </td>
			
			<td CLASS="title">�������
    		         </td>
			<td CLASS="input" COLSPAN="1">
			<input id="ManageCom" NAME="ManageCom" VALUE MAXLENGTH="10" CLASS="code wid" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('comcode',[this],null,null,'#1# andLength(trim(comcode))=8','1',1);" onkeyup="returnshowCodeListKey('comcode',[this],null,null,'#1# andLength(trim(comcode))=8','1',1);" verify="�������|code:station&amp;notnull">
    		         </td>
    		         
     <td CLASS="title">��������
    		         </td>
			<td CLASS="input" COLSPAN="1">
			<input id="SaleChnl" NAME="SaleChnl" VALUE CLASS="common wid" MAXLENGTH="2">
    		           </td>
		</tr>
		<tr CLASS="common">
		
			<td CLASS="title">ҵ��Ա����
    		           </td>
			<td CLASS="input" COLSPAN="1">
			<input id="AgentCode" NAME="AgentCode" VALUE MAXLENGTH="10" CLASS="code wid" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return queryAgent();" onkeyup="return queryAgent2();">
    		         </td>
			<td CLASS="title">���������
    		          </td>
			<td CLASS="input" COLSPAN="1">
			<input id="AgentGroup" NAME="AgentGroup" VALUE CLASS="readonly wid" readonlyTABINDEX="-1" MAXLENGTH="12" verify="���������|notnull">
    		        </td>
		</tr>
		
         <INPUT  type= "hidden" class= Common id="ContNo" name = ContNo value= "">
         <INPUT  type= "hidden" class= Common id="MissionID" name = MissionID value= "">
         <INPUT  type= "hidden" class= Common id="SubMissionID" name = SubMissionID value= "">
         <input type = "hidden" class = common id="CustomerNo" name = CustomerNo value = "">
         <input type = "hidden" class = common id="PrtSeq" name = PrtSeq value = "">
        
       </table>
   </div>
  
   <table>
    	<tr>
        <td class=common>
			<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpec1);">
    		</td>
    		<td class= titleImg>
    			������Ϣ����
    		</td>
    	</tr>
    </table>
    <div class="maxbox1">
  <table class= common align=center>
  	 <TD  class = title> �ͻ����� </TD>
          <TD  ><Input class="common wid" id="CustomerNo1"  name=CustomerNo1 ></TD>
           
          <TD  class = title>	�ͻ����� </TD>
          <TD  ><Input class="common wid" id="CustomerName"  name=CustomerName ></TD>
            <TD  class= title> ����ԭ��  </TD>
        
           <td class=input>
                    <Input class="codeno" id="RReportReason" name=RReportReason style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('rreportreason',[this,RReportReasonname],[0,1]);" onkeyup="return showCodeListKey('rreportreason',[this,RReportReasonname],[0,1]);"><Input class=codename id="RReportReasonname" name=RReportReasonname >                    
                </td>
  </table>
   </div>
   
     <table>
    	<tr>
        	<td class=common>    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpec1);"></td>
    		<td class= titleImg>	������Ŀ�б�</td>                            
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
   
    <table>
    	<tr>
        	<td class=common>    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpec1);"></td>
    		<td class= titleImg>	 �ʾ�����ѡ��</td>                            
    	</tr>	
    </table>
    <Div  id= "divUWSpec" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  							<span id="spanQuestionTypeGrid"></span> 
  		  			</td>
  				</tr>
    	</table>
    <p>
    	  <INPUT type= "button" id="sure" name= "sure" value="ȷ  ��" class= cssButton onclick="submitForm()">		
    </p>
  	
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br/><br/><br/><br/>
</body>

</html>


