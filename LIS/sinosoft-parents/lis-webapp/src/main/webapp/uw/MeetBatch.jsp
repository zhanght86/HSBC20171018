<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<%
GlobalInput tGI = new GlobalInput();
tGI = (GlobalInput)session.getValue("GI");
%>
<script>
var managecom = "<%=tGI.ManageCom%>"; //��¼��������
var comcode = "<%=tGI.ComCode%>"; //��¼��½����
</script>
<head>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="MeetBatch.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="MeetBatchInit.jsp"%>
<title>��ӡ�б����֪ͨ�� </title>
</head>
<body  onload="initForm();" >
  <form action="./MeetQuery.jsp" method=post name=fm id="fm" target="fraSubmit">
    <!-- Ͷ������Ϣ���� -->
    <table class= common border=0 width=100%>
    	<tr>
         <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage

(this,divInvAssBuildInfo);">
            </TD>
			<td class= titleImg align= center>������Ͷ������ѯ������</td>
		</tr>
	</table>
     <div id="divInvAssBuildInfo">
       <div class="maxbox1" >
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title5> 
           ӡˢ��  
           </TD>
          <TD  class= input5> 
           <Input class="common wid" id="ContNo" name=ContNo >
            </TD>
          <TD class=title5> 
          ��������
           </TD>  
          <TD  class= input5 ><Input class= "codeno" id="ManageCom" name = ManageCom 
          style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="return showCodeList('station',[this,ManageComName],[0,1]);" 

          onDblClick="return showCodeList('station',[this,ManageComName],[0,1]);" 
          onKeyUp="return showCodeListKey('station',[this,ManageComName],[0,1]);" ><Input class = codename id="ManageComName" name=ManageComName readonly = true> </TD>
                   </TR> 
         <TR  class= common>       
          <TD  class= title5> �����˱��� </TD>
          <!--TD  class= input>  <Input class="code" name=AgentCode ondblclick="return showCodeList('AgentCode',[this]);" onkeyup="return showCodeListKey('AgentCode',[this]);">   </TD--> 
           <TD  class= input5><Input class="common wid" id="AgentCode" name=AgentCode >   </TD>

         	<TD class=title5> �������� </TD>  
          <TD  class= input5 ><Input class= "codeno" id="SaleChnl" name = SaleChnl 
          style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="return showCodeList('SaleChnl',[this,SaleChnlName],[0,1]);" 
          onDblClick="return showCodeList('SaleChnl',[this,SaleChnlName],[0,1]);" 
          onKeyUp="return showCodeListKey('SaleChnl',[this,SaleChnlName],[0,1]);" ><Input class = codename id="SaleChnlName" name=SaleChnlName readonly = true> </TD>  	
         
        </TR>  
    </table>
    </div>
    </div>

        <INPUT VALUE="��  ѯ" class= cssButton TYPE=button onClick="easyQueryClick();">
  </form>
  <form action="./MeetF1BatchPSave.jsp" method=post name=fmSave target="f1print">
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
    			 ֪ͨ����Ϣ
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLCPol1" style= "display: ''" align = center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span>
  			  	</td>
  			</tr>
    	</table>
      <!--<INPUT VALUE="��  ҳ" class= cssButton TYPE=button onClick="getFirstPage();">
      <INPUT VALUE="��һҳ" class= cssButton TYPE=button onClick="getPreviousPage();">
      <INPUT VALUE="��һҳ" class= cssButton TYPE=button onClick="getNextPage();">
      <INPUT VALUE="β  ҳ" class= cssButton TYPE=button onClick="getLastPage();">-->
  	</div>
  	<p>
      <INPUT VALUE="��  ӡ" class= cssButton TYPE=button onClick="printPol();" id="printButton" disabled>
  	</p>
  	<input type=hidden id="fmtransact" name="fmtransact">
  	<input type=hidden id="ContNo" name="ContNo">
  	<input type=hidden id="PrtSeq" name="PrtSeq">
  	<input type=hidden id="OldPrtSeq" name="OldPrtSeq">
  	<input type=hidden id="MissionID" name="MissionID">
  	<input type=hidden id="SubMissionID" name="SubMissionID">
  	<input type=hidden id="PrtNo" name="PrtNo">

  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br/><br/><br/><br/>
</body>
</html>