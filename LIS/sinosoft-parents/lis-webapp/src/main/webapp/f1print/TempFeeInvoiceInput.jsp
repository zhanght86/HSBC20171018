<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>    
<% 
//�������ƣ�
//�����ܣ�
//�������ڣ�2009-10-27 15:39:06
//������  yanglh
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%
    GlobalInput tGI = new GlobalInput();
		tGI = (GlobalInput)session.getValue("GI");
		String tCurrentDate = PubFun.getCurrentDate();
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
  <SCRIPT src="TempFeeInvoiceInput.js"></SCRIPT>    
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <%@include file="TempFeeInvoiceInit.jsp"%>
</head>
<body  onload="initForm();" >    
  <form  method=post name=fm id=fm target="fraSubmit">
    <table class= common border=0 width=100%>
    	<tr>  
         <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divFeeInv);">
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
         	 <TD  class= title5> ��ͬ����  </TD>
          <TD  class= input5> <Input class="common wid" name=ContNo > </TD>
           </TR>
          <TR  class= common>
           <TD  class= title5> ��Ա����  </TD> 
          <TD  class= input5> <Input class="common wid" name=AgentCode  verify="�����˱���|code:AgentCode" 
           style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="return queryAgent(comcode);" 
          onDblClick="return queryAgent(comcode);" 
          onBlur="return queryAgentAll(comcode);" >  </TD>
         <TD  class= title5> ��Ա����  </TD> 
          <TD  class= input5> <Input class="readonly wid "  tabindex=-1 name=AgentName readonly=true > </TD>
        </TR>
          <TR  class= common>

          <TD  class= title5>   Ӧ�����ڣ���ʼ�� </TD>
          <TD  class= input5>	
          <input class="coolDatePicker" dateFormat="short" id="StartDate"  name="StartDate" onClick="laydate
({elem:'#StartDate'});" > <span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img 
src="../common/laydate/skins/default/icon.png" /></a></span>
          
            </TD> 
			    <TD  class= title5>   Ӧ�����ڣ������� </TD>
          <TD  class= input5>	<input class="coolDatePicker" dateFormat="short" id="EndDate"  name="EndDate" onClick="laydate({elem:'#EndDate'});"> <span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          
           </TD> 

          </TR>
        </table> 
    	<table class= common border=0 width=100%>
    	<tr>     		
    		 <td class= titleImg>
        		�����¼���д�����Ϣ, ����״̬��Ϊ�����ɷ���Ч
       		 </td>   		      
    	</tr>
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
        <input type=hidden name="MngCom" value=<%=tGI.ManageCom%>>
        <br/>
		<!--<INPUT VALUE="��Ʊ��ӡ" class="cssButton" TYPE=button onClick="Print();"> -->
        <a href="javascript:void(0);" class="button"onClick="Print();">��Ʊ��ӡ</a>
        <br><br><br><br>
		
  </form>
</body>
</html> 