<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>    
<% 
//�������ƣ�
//�����ܣ�
//�������ڣ�2002-08-16 15:39:06
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
    GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var managecom = "<%=tGI.ManageCom%>"; //��¼�������
	var comcode = "<%=tGI.ComCode%>"; //��¼��½����
</script>
<head>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="FeeInvoiceInput.js"></SCRIPT>    
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <%@include file="FeeInvoiceInit.jsp"%>
</head>
<body  onload="initForm();" >    
  <form  method=post name=fm id=fm target="fraSubmit">
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
          <TD  class= title5>�����վݺ���  </TD>
          <TD  class= input5>  <Input class="common wid" name=GetNoticeNo > </TD> 
          <TD  class= title5>   �������� </TD>
          <TD  class= input5>	
           <input class="coolDatePicker" dateFormat="short" id="PayDate"  name="PayDate" onClick="laydate({elem:'#PayDate'});"> <span class="icon"><a onClick="laydate({elem: '#PayDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
           </TD> 
          </TR>
        <TR  class= common>
          <TD  class= title5>  �������   </TD>
          <TD  class= input5>
          <Input class="common wid" name=MngCom verify="�������|code:comcode"
           style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="return showCodeList('comcode',[this],null,null,null,null,1);" 

           onDblClick="return showCodeList('comcode',[this],null,null,null,null,1);" 
           onKeyUp="return showCodeListKey('comcode',[this],null,null,null,null,1);" readonly> </TD>
         
          <TD  class= title5> ʵ�ձ��  </TD>
          <TD  class= input5> <Input class="common wid" name=PayNo > </TD>
            </TR>
        <TR  class= common>
           <TD  class= title5> �����˱���  </TD> 
          <TD  class= input5> <Input class="common wid" name=AgentCode  verify="�����˱���|code:AgentCode"
           style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="return queryAgent(comcode);"
           onDblClick="return queryAgent(comcode);"
            onKeyUp="return queryAgent(comcode);" >  </TD>
          <TD  class= title5>  ��ͬ��������  </TD>
          <!--TD  class= input>	<Input class=code name=IncomeType verify="Ӧ��/ʵ�ձ������|NOTNULL" CodeData="0|^1|���屣����^2|���˱�����" ondblClick="showCodeListEx('FeeIncomeType1',[this],[0,1]);" onkeyup="showCodeListKeyEx('FeeIncomeType1',[this],[0,1]);">           </TD--> 
          <TD  class= input5>	<Input class="common wid" name=IncomeType  CodeData="0|^1|�����ͬ��^2|���˺�ͬ��"
          style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="showCodeListEx('FeeIncomeType1',[this]);"
           ondblClick="showCodeListEx('FeeIncomeType1',[this]);"
            onKeyUp="showCodeListKeyEx('FeeIncomeType1',[this]);">           </TD> 

        </TR>
       <TR  class= common>

          <TD  class= title5> ��ͬ����  </TD>
          <TD  class= input5> <Input class="common wid" name=IncomeNo > </TD>
        </TR>
        </table> 
    </div></Div>
		<!--<INPUT VALUE="��ѯ" class="cssButton" TYPE=button onClick="easyQueryClick();"> 	-->				  
      <a href="javascript:void(0);" class="button"onClick="easyQueryClick();">��   ѯ</a>
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLJAPay1);">
    		</td>
    		<td class= titleImg>
    			 ������Ϣ
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
    	<INPUT VALUE="��ҳ" class="cssButton90" TYPE=button onClick="turnPage.firstPage();"> 
      <INPUT VALUE="��һҳ" class="cssButton91" TYPE=button onClick="turnPage.previousPage();"> 					
      <INPUT VALUE="��һҳ" class="cssButton92" TYPE=button onClick="turnPage.nextPage();"> 
      <INPUT VALUE="βҳ" class="cssButton93" TYPE=button onClick="turnPage.lastPage();">
      </div>				
  	</div>
    </div>
        <input type=hidden id="fmtransact" name="fmtransact">
        
        <br/>
		<!--<INPUT VALUE="��Ʊ��ӡ" class="cssButton" TYPE=button onClick="PPrint();"> -->
		<a href="javascript:void(0);" class="button"onClick="PPrint();">��Ʊ��ӡ</a>
        <br><br><br><br>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html> 
