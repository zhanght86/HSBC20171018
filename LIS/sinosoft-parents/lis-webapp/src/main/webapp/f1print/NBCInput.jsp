<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html> 
<%
    GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var managecom = "<%=tGI.ManageCom%>"; //��¼�������
	var comcode = "<%=tGI.ComCode%>"; //��¼��½����
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="NBC.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
   <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="NBCInit.jsp"%>
  <title>��ӡ����Լ�˷�֪ͨ�� </title>
</head>
<body  onload="initForm();" >
  <form action="./NBCQuery.jsp" method=post name=fm id=fm target="fraSubmit">
    <!-- ������Ϣ���� -->
    <table class= common border=0 width=100%>
    	<tr>
        <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divInvAssBuildInfo);">
            </TD>
			<td class= titleImg align= center>�����뱣����ѯ������</td>
		</tr>
	</table>
    <div id="divInvAssBuildInfo">
       <div class="maxbox1" >
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title5>  ������   </TD>
          <TD  class= input5>  <Input class="common wid" name=ContNo > </TD>
           <TD  class= title5> �����˱��� </TD>
          <!--TD  class= input>  <Input class="code" name=AgentCode ondblclick="return showCodeList('AgentCode',[this]);" onkeyup="return showCodeListKey('AgentCode',[this]);">   </TD--> 
           <TD  class= input5><Input class="common wid" name=AgentCode >   </TD>
            </TR> 
         <TR  class= common>
                     <TD class=title5> ������� </TD>  
          <TD class= input5 ><Input class= "codeno" name = ManageCom  id = ManageCom 
          style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="return showCodeList('station',[this,ManageComName],[0,1]);" 
          onDblClick="return showCodeList('station',[this,ManageComName],[0,1]);" 
          onKeyUp="return showCodeListKey('station',[this,ManageComName],[0,1]);" ><Input class = codename name=ManageComName readonly = true> </TD>

        
          <TD  class= title5>  ֪ͨ������ </TD>
          <TD class= input5>
          <!--Input class= "codeno" name = NoticeType ondblclick="return showCodeList('yjtfnoticetype',[this,NoticeTypeName],[0,1]);" onkeyup="return showCodeListKey('yjtfnoticetype',[this,NoticeTypeName],[0,1]);"><Input class = codename name= NoticeTypeName readonly = true-->         
          <Input class= "codeno" name=NoticeType
           CodeData="0|^01|�����罻�˷�֪ͨ��^02|�ŵ��罻�˷�֪ͨ��" 
           style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="return showCodeListEx('NoticeType', [this,NoticeTypeName],[0,1])"
           onDblClick="return showCodeListEx('NoticeType', [this,NoticeTypeName],[0,1])"
           onkeyup="return showCodeListKeyEx('NoticeType', [this,NoticeTypeName],[0,1])"><input class=codename name=NoticeTypeName readonly=true>
          </TD>
           </TR> 
         <TR  class= common> 
         <TD class=title5> �������� </TD>  
         <TD class= input5 ><Input class= "codeno" name = SaleChnl  id = SaleChnl
         style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="return showCodeList('SaleChnl',[this,SaleChnlName],[0,1]);" 
          onDblClick="return showCodeList('SaleChnl',[this,SaleChnlName],[0,1]);" 
          onKeyUp="return showCodeListKey('SaleChnl',[this,SaleChnlName],[0,1]);" ><Input class = codename name=SaleChnlName readonly = true> </TD>  	
        </TR>  
    </table>
    </div></div>
    
  	 <Input type=hidden  name=AgentGroup > 
         <!-- <INPUT VALUE="��  ѯ" class=  "cssButton"  TYPE=button onClick="easyQueryClick();"> -->
         <a href="javascript:void(0);" class="button"onClick="easyQueryClick();">��   ѯ</a>
  </form>
  <form action="./NBCF2PSave.jsp" method=post name=fmSave target="f1print">
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
      <INPUT VALUE="��  ҳ" class=  "cssButton90"  TYPE=button onClick="getFirstPage();"> 
      <INPUT VALUE="��һҳ" class=  "cssButton91"  TYPE=button onClick="getPreviousPage();"> 					
      <INPUT VALUE="��һҳ" class=  "cssButton92"  TYPE=button onClick="getNextPage();"> 
      <INPUT VALUE="β  ҳ" class=  "cssButton93"  TYPE=button onClick="getLastPage();"> 	
  	</div>
  	<!--<p>
      <INPUT VALUE="��  ӡ" class= "cssButton" TYPE=button onClick="printPol();">
  	</p>-->
     <a href="javascript:void(0);" class="button"onClick="printPol();">��  ӡ</a>
     <br><br><br><br>
  	<input type=hidden id="fmtransact" name="fmtransact">
  	<input type = hidden id = "PrtSeq" name = "PrtSeq">
  	<input type=hidden id="ContNo" name="ContNo">
  		<input type=hidden id = "noticetype" name = "noticetype">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
