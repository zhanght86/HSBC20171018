<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
  GlobalInput GI = new GlobalInput();
	GI = (GlobalInput)session.getValue("GI");
%>
<script>
	var manageCom = "<%=GI.ManageCom%>"; //��¼�������
	var ComCode = "<%=GI.ComCode%>"; //��¼��½����
</script>

<html>    
<% 
//�������ƣ��Զ��潻��ӡ����
//�����ܣ�
//�������ڣ�2004-5-20
//������  ��CrtHtml���򴴽�
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="PrintAutoDJInput.js"></SCRIPT>    
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <%@include file="PrintAutoDJInit.jsp"%>
</head>
<body  onload="initForm();" >    
  <form  method=post name=fm id="fm"  target="fraSubmit">
    <table class= common border=0 width=100%>
    	<tr> 
        <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divInvAssBuildInfo);">
            </TD>    		
    		 <td class= titleImg>
        		�����ѯ����
       		 </td>   		      
    	</tr>
    </table>
        <Div  id= "divFeeInv" style= "display: ''">
         <div class="maxbox1" >
      <table class= common border=0 width=100%>
        <TR  class= common>
          <TD  class= title5>  ��ʼ����  </TD>
          <TD  class= input5>  <input class="coolDatePicker" dateFormat="short" id="StartDate"  name="StartDate" onClick="laydate
({elem:'#StartDate'});" verify="��������|Date"> <span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img 
src="../common/laydate/skins/default/icon.png" /></a></span>
 </TD> 
          <TD  class= title5>   �������� </TD>
          <TD  class= input5>	 <input class="coolDatePicker" dateFormat="short" id="EndDate"  name="EndDate" onClick="laydate({elem:'#EndDate'});" verify="��������|Date"> <span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span> </TD> 
          </TR>
          <TR  class= common>
          <TD  class= title5>   ������� </TD>
          <TD  class= input5>   <Input class="common wid" name=Station   id=Station 
           style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="return showCodeList('Station',[this]);" 
          onDblClick="return showCodeList('Station',[this]);" 
          onKeyUp="return showCodeListKey('Station',[this]);">	</TD>
						<Input type=hidden name=Date>
          <TD  class= title5>   ������ </TD>
          <TD  class= input5>   <Input class="common wid" name=TRFlag id=TRFlag CodeData="0|^0|δ����^1|�ѻ���" 
           style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="return showCodeListEx('TRFlag',[this],[0]);" 
          onDblClick="return showCodeListEx('TRFlag',[this],[0]);"
           onKeyUp="return showCodeListKeyEx('TRFlag',[this],[0]);"> </TD>
        </TR>
      </table> 
      </div></Div>
    <!--<INPUT VALUE="δ�����嵥��ӡ" class= cssButton TYPE=button onClick="PrintQD();">
		<INPUT VALUE="��    ѯ" class= cssButton TYPE=button onClick="QueryAutoDJ();">-->
        <a href="javascript:void(0);" class="button"onClick="PrintQD();">δ�����嵥��ӡ</a>
       <a href="javascript:void(0);" class="button"onClick="QueryAutoDJ();">��   ѯ</a>

     
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLJAPay1);">
    		</td>
    		<td class= titleImg>
    			 �Զ��潻����ϸ��Ϣ
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLJAPay1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
        <div align="center">
    	<INPUT VALUE="��  ҳ" class= cssButton90 TYPE=button onClick="turnPage.firstPage();"> 
      <INPUT VALUE="��һҳ" class= cssButton91 TYPE=button onClick="turnPage.previousPage();"> 					
      <INPUT VALUE="��һҳ" class= cssButton92 TYPE=button onClick="turnPage.nextPage();"> 
      <INPUT VALUE="β  ҳ" class= cssButton93 TYPE=button onClick="turnPage.lastPage();">
      	</div>				
  	</div>
    </div>
        <input type=hidden id="fmtransact" name="fmtransact">
        
        <br/>
		<!--<INPUT VALUE="��ӡ�Զ��潻֪ͨ��" class= cssButton TYPE=button onClick="PrintAutoDJ();"> -->
		 <a href="javascript:void(0);" class="button"onClick="PrintAutoDJ();">��ӡ�Զ��潻֪ͨ��</a>
         <br><br><br><br>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html> 
