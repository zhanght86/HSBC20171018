<html>
<%
//�������ƣ����д��ն����嵥
//�����ܣ�
//�������ڣ�2003-3-25
//������  �������ɳ��򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
	<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
	<!--�û�У����-->
	<%@page import = "com.sinosoft.utility.*"%>
	<%@page import = "com.sinosoft.lis.schema.*"%>
	<%@page import = "com.sinosoft.lis.vschema.*"%>
	<%@page import = "com.sinosoft.lis.bank.*"%>
	<%@include file="../common/jsp/UsrCheck.jsp"%>

	<head >
  	<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  	
  	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  	<SCRIPT src="BankYSBillRightInput.js"></SCRIPT>
  	 <SCRIPT src="../common/laydate/laydate.js"></SCRIPT> 
  	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  	<%@include file="BankYSBillRightInit.jsp"%>
	</head>

	<body  onload="initForm();" >
  	<form action="./PrintBill.jsp" method=post name=fm target="fraSubmit">
		<%@include file="../common/jsp/InputButton.jsp"%>
        <!-- ��ʾ������LLReport1����Ϣ -->
    		
    	<Div  id= "divLLReport1" style= "display: ''">
    	<table class=common>
          <tr class= titleImg>
          <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divInvAssBuildInfo);">
            </TD>
            <TD class= titleImg > ����ת�˴��ճɹ��嵥 </TD>
          </tr>
        </table>
        <div id="divInvAssBuildInfo">
       <div class="maxbox1" >
      	<table  class= common>
        	<TR  class= common>
          	<TD  class= title5> ��ʼ���� </TD>
          	<TD  class= input5><input class="coolDatePicker" dateFormat="short" id="StartDate"  name="StartDate" onClick="laydate
({elem:'#StartDate'});" verify="��������|Date"> <span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img 
src="../common/laydate/skins/default/icon.png" /></a></span> <font color='red'>*</font></TD>
          	<TD  class= title5> �������� </TD>
          	<TD  class= input5>  <input class="coolDatePicker" dateFormat="short" id="EndDate"  name="EndDate" onClick="laydate({elem:'#EndDate'});" verify="��������|Date"> <span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
<font color='red'>*</font> </TD>
		      </TR>
		      <TR class= common>
					  <TD  class= title5>�����ڱ�־ </TD>
            <TD  class=input5> <Input class="common wid" name=SXFlag   id=SXFlag CodeData="0|^S|����^X|����^B|��ȫ^L|����"
            style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="showCodeListEx('SXFlag1',[this],[0,1]);"
 
            ondblClick="showCodeListEx('SXFlag1',[this],[0,1]);"
            onkeyup="showCodeListKeyEx('SXFlag1',[this],[0,1]);"> </TD>
						<TD  class= title5> ������� </TD>
            <TD  class= input5> <Input class= code name=Station  id=Station
            style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="return showCodeList('Station',[this]);"
            onDblClick="return showCodeList('Station',[this]);"
             onKeyUp="return showCodeListKey('Station',[this]);"> </TD>
						<TD> <Input Type=hidden name=Flag> </TD>     <!--���մ�����־-->
						<TD> <Input Type= hidden name=TFFlag > </TD> <!--��ȷ�嵥���Ǵ����嵥�ı�־-->
					</tr>

 					<TR  class= common>
         		<TD  class= title5> ���д��� </TD>
		       <TD  class= input5>
				 <Input class=codeno name=BankCode  id=BankCode
                 style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="return showCodeList('bank',[this,BankName],[0,1],null,'1 and comcode like #'+comCode+'%#','1');" 

                 onDblClick="return showCodeList('bank',[this,BankName],[0,1],null,'1 and comcode like #'+comCode+'%#','1');" 
                  onkeyup="return showCodeListKey('bank',[this,BankName],[0,1],null,'1 and comcode like #'+comCode+'%#','1');"
                   verify="���д���|notnull&code:bank"><input class=codename name=BankName readonly=true>
		      </TD>
        	</TR>
        	
		
        	
 				</table>
</div></div>
<br>
<a href="javascript:void(0);" class="button"onClick="showSerialNo()">�г���ӡ����</a>

<a href="javascript:void(0);" class="button"onClick="PrintBill()">��ӡ����</a>

			<!--
					 <TR  class= common>
            <TD> <input class=cssButton type=button value="�г���ӡ����" onClick="showSerialNo()"> </TD>
            <TD class=input > <input class=cssButton type=button value="��ӡ����" onClick="PrintBill()"> </TD>-->
          <table>
    	<tr>  
          <TD  class= input>
            	<input type=hidden  name="BillNo" >
            	<input type=hidden  name="selBankCode" >
          	</TD>
        	</TR>
            </table>
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLReport2);">
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
      <Div  id= "divPage" align=center style= "display: 'none' ">
      <INPUT CLASS=cssButton90 VALUE="��ҳ" TYPE=button onClick="turnPage.firstPage();"> 
      <INPUT CLASS=cssButton91 VALUE="��һҳ" TYPE=button onClick="turnPage.previousPage();"> 					
      <INPUT CLASS=cssButton92 VALUE="��һҳ" TYPE=button onClick="turnPage.nextPage();"> 
      <INPUT CLASS=cssButton93 VALUE="βҳ" TYPE=button onClick="turnPage.lastPage();">
	 </Div>
	</div>
    <br><br><br><br>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
