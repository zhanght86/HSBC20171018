
<%
  GlobalInput GI = new GlobalInput();
	GI = (GlobalInput)session.getValue("GI");
%>
<script>
	var manageCom = "<%=GI.ManageCom%>"; //��¼��������
	var ComCode = "<%=GI.ComCode%>"; //��¼��½����
</script>

<html>
<%
//�������ƣ�
//�����ܣ����ڱ�������ת�˳ɹ��嵥
//�������ڣ�2004-5-24
//������  �������ɳ��򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
	<%@page contentType="text/html;charset=GBK" %>
	<!--�û�У����-->
	<%@page import = "com.sinosoft.utility.*"%>
	<%@page import = "com.sinosoft.lis.schema.*"%>
	<%@page import = "com.sinosoft.lis.vschema.*"%>
	<%@page import = "com.sinosoft.lis.bank.*"%>
	<%@include file="../common/jsp/UsrCheck.jsp"%>

	<head >
  	<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  	 <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  	
  	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  	<SCRIPT src="PPolPauseInput.js"></SCRIPT>
  	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  	<%@include file="PPolPauseInit.jsp"%>
	</head>

	<body  onload="initForm();" >
  	<form action="./PPolPausePrint.jsp" method=post name=fm id=fm target="fraSubmit">
		<%@include file="../common/jsp/InputButton.jsp"%>
        <!-- ��ʾ������LLReport1����Ϣ -->
    		
    	<Div  id= "divLLReport1" style= "display: ''">
    	<table class=common>
          <tr class= common>
           <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divInvAssBuildInfo);">
            </TD>
            <TD  class= titleImg>
              ����Ч����ֹ
            </TD>
          </tr>
        </table>
        <div id="divInvAssBuildInfo">
       <div class="maxbox1" >
      	<table  class= common>
        	<TR  class= common>
          	<TD  class= title5>
            	��ʼ����
          	</TD>
          	<TD  class= input5>
            	<input class="coolDatePicker" dateFormat="short" id="StartDate"  name="StartDate" onClick="laydate
({elem:'#StartDate'});" verify="��������|Date"> <span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img 
src="../common/laydate/skins/default/icon.png" /></a></span>
          	</TD>
          	<TD  class= title5>
            	��������
          	</TD>
          	<TD  class= input5>
            	 <input class="coolDatePicker" dateFormat="short" id="EndDate"  name="EndDate" onClick="laydate({elem:'#EndDate'});" verify="��������|Date"> <span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          	</TD>
		      </TR>
<!--
 					<TR  class= common>
         			
         		<TD  class= title>
            	���д���
          	</TD>
          	<TD  class= input>
-->
            	<Input  name=AccName type= hidden>
            	<Input  name=AppntName type= hidden>
            	<Input  name=Date type= hidden>
            	<Input  name=BankAccNo type= hidden>
<!--
          	</TD>
    				<TD class= title>
    				��������
    				</TD>
    				<TD class=input >
          		<Input class="readonly" readonly name=BankName verify="��������|notnull&len<=12" >
						</TD>
						
        	</TR>
        	-->
					<TR class= common>
					<TD  class= title5>
            ��������
          </TD>
          <TD  class= input5>
	          <Input class= "common wid" name=Station  id=Station 
               style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="return showCodeList('Station',[this]);"
              onDblClick="return showCodeList('Station',[this]);"
               onKeyUp="return showCodeListKey('Station',[this]);">
						</TD>
						<!--
						<TD class= title>
					    �嵥����
					  </td>
					  <TD  class= code>
  					  <Input class=code name=AgentState verify="�嵥����|NOTNULL"CodeData="0|^0|�¶���^1|��ְ��" ondblClick="showCodeListEx('AgentState1',[this],[0,1]);"onkeyup="showCodeListKeyEx('AgentState1',[this],[0,1]);">
  				  </TD>
  				  -->
          <TD>
					<Input Type= hidden name=Flag >
					</TD>

					</tr></table>
                    </div></div>
                    
					
          <!--  <TD>
          	  <input class=cssButton type=button value="��ѯ����Ч����ֹ�嵥" onClick="showSerialNo()">
           </TD>
           <TD class=input width="23%">
            	<input class=cssButton type=button value="��ӡ����Ч����ֹ�嵥" onClick="QueryBill()">
          </TD>-->
          <a href="javascript:void(0);" class="button"onClick="showSerialNo()">��ѯ����Ч����ֹ�嵥</a>
          <a href="javascript:void(0);" class="button"onClick="QueryBill()">��ӡ����Ч����ֹ�嵥</a>
           <table>
           <TR  class= common>
          <TD  class= input>
            	<input type=hidden  name="PremType" >
              <input type=hidden  name="Flag" >
              <Input type=hidden name=Date>
              <Input type=hidden name=PrintType>
          	</TD>
        	</TR>
        	
 				

    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
    			 �嵥�б�
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLLReport2" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					<span id="spanBillGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	
    	
      <Div  id= "divPage"  style= "display:''" align="center">     
       <INPUT CLASS=cssButton90 VALUE="��  ҳ" TYPE=button onClick="turnPage.firstPage();"> 
       <INPUT CLASS=cssButton91 VALUE="��һҳ" TYPE=button onClick="turnPage.previousPage();"> 					
       <INPUT CLASS=cssButton92 VALUE="��һҳ" TYPE=button onClick="turnPage.nextPage();"> 
       <INPUT CLASS=cssButton93 VALUE="β  ҳ" TYPE=button onClick="turnPage.lastPage();">
  	 </Div>
   </Div>
   <br/>
   <!--<TR  class= common> 
         <TD class=input width="23%">
          	<input class=cssButton type=button value="��ӡ���պ�ͬЧ����ֹ֪ͨ��" onClick="PrintBill()">
         </TD>      
     </TR>-->
     <a href="javascript:void(0);" class="button"onClick="PrintBill()">��ӡ���պ�ͬЧ����ֹ֪ͨ��</a>
    <br><br><br><br>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>