<%
//�������ƣ�BankSerialNoQuery.jsp
//�����ܣ����з��̻������β�ѯ
//�������ڣ�2010-04-12
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
    GlobalInput tG1 = (GlobalInput)session.getValue("GI");
    String tComCode =tG1.ComCode;
%>
<script>
	var comCode= <%=tComCode%>;
	var comCodeLen = <%=tComCode.toString().length()%>;
	var Str = "1 and comcode like #<%=tComCode%>%#";
</script>
<html>    
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT>
 <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="./BankSerialNoQuery.js"></SCRIPT>   
  <%@include file="./BankSerialNoQueryInit.jsp"%>   
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>���з��̻������β�ѯ</title>
</head>      
 
<body  onload="initForm();" >
<table class= common border=0 width=100%>
  <tr>
   <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divInvAssBuildInfo);">
            </TD>
	<td class= titleImg align= center>�������ѯ������</td>
  </tr>
</table>
<form method=post name=fm id=fm>
<div id="divInvAssBuildInfo">
       <div class="maxbox1" >
<table class= common>
   <TR class= common> 
          <TD class=title5>�������</TD>
	  <TD  class= input5>
	      <Input class=codeno NAME=Managecom VALUE="" MAXLENGTH=10  
          style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
            onclick="return showCodeList('comcode',[this,ManageComName],[0,1],null,Str,'1',null,250);"
          ondblclick="return showCodeList('comcode',[this,ManageComName],[0,1],null,Str,'1',null,250);"
           onKeyUp="return showCodeListKey('comcode',[this,ManageComName],[0,1],null,Str,'1',null,250);"
            verify="�������|code:comcode&notnull" ><input class=codename name=ManageComName readonly=true>
	  </TD>
	  <TD  class= title5>���д���</TD>
          <TD  class= input5>
              <Input class=codeno name=Bankcode
               style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
               onclick="return showCodeList('bank',[this,BankName],[0,1],null,'1 and comcode like #'+comCode+'%#','1');"  
               onDblClick="return showCodeList('bank',[this,BankName],[0,1],null,'1 and comcode like #'+comCode+'%#','1');"  
               onkeyup="return showCodeListKey('bank',[this,BankName],[0,1],null,'1 and comcode like #'+comCode+'%#','1');" 
               verify="���д���|notnull&code:bank"><input class=codename name=BankName readonly=true>
          </TD>      
   </TR>
   <TR class= common> 
       <TD  class= title5>��ʼ����</TD>
       <TD  class= input5>
       <Input class="multiDatePicker laydate-icon"   onClick="laydate({elem: '#Sdate'});"dateFormat="short" name=Sdate id=Sdate><span class="icon"><a onClick="laydate({elem: '#Sdate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
       <TD  class= title5>��ֹ����</TD>
       <TD  class= input5>
        <Input class="multiDatePicker laydate-icon"   onClick="laydate({elem: '#Edate'});"dateFormat="short" name=Edate id=Edate><span class="icon"><a onClick="laydate({elem: '#Edate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
       </TD>
   </TR> 
   <TR> 
       <TD  class= title5>���ո���־("S"�շ�-"F"����)</TD>
       <TD  class= input5><Input class="common wid" name=YStatus></TD>               
   </TR>       
</table>        
</div>
</div>
    <!--������-->
    <a href="javascript:void(0);" class="button"onClick="easyQuery();" >��   ѯ</a>
    <a href="javascript:void(0);" class="button"onClick="easyPrint();" >�������غʹ�ӡ����</a>
    
   <!-- <INPUT VALUE="��ѯ" class= Button TYPE=button onClick="easyQuery();"> 	
    <INPUT VALUE="�������غʹ�ӡ����" class= Button TYPE=button onClick="easyPrint();"> 	-->

    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCodeGrid);">
    		</td>
    		<td class= titleImg>
    			 ���з��̻������β�ѯ
    		</td>
    	</tr>
    </table>
  	<Div  id= "divCodeGrid" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
									<span id="spanCodeGrid" >
									</span> 
		  				</td>
					</tr>
    		</table> 
             <div align="center">
      	<INPUT VALUE="��ҳ" class="cssButton90" TYPE=button onClick="getFirstPage();"> 
      	<INPUT VALUE="��һҳ" class="cssButton91" TYPE=button onClick="getPreviousPage();"> 					
      	<INPUT VALUE="��һҳ" class="cssButton92" TYPE=button onClick="getNextPage();"> 
      	<INPUT VALUE="βҳ" class="cssButton93" TYPE=button onClick="getLastPage();"> 
        </div>					
  	</div>
    <br><br><br><br>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
