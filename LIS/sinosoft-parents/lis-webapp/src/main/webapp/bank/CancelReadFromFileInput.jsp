<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html> 
<%
//�������ƣ�ReadFromFileInput.jsp
//�����ܣ�
//�������ڣ�2009-9-2 11:10:36
//������  �����ֺ�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<head > 
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT> 
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
   <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

  <SCRIPT src="CancelReadFromFileInput.js"></SCRIPT>
  <%@include file="CancelReadFromFileInit.jsp"%>
  
  <title>�����ļ����� </title>
</head>

<%
  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");
  String tTempBankFlag = request.getParameter("BankFlag");
  String tYBTBankCode = request.getParameter("YBTBankCode");
  loggerDebug("CancelReadFromFileInput","tTempBankFlag: "+tTempBankFlag);
  loggerDebug("CancelReadFromFileInput","tYBTBankCode: "+tYBTBankCode);
%>

<script>
  comCode = "<%=tGlobalInput.ComCode%>";
  dealType = "<%=request.getParameter("DealType")%>";
  var tBankFlag = "0";  //0 �������нӿ�,1 ����ͨ��������
  var tNewBankFlag = "<%=tTempBankFlag%>";
  var tYBTBankCode = "<%=tYBTBankCode%>";
  if( tNewBankFlag != null && tNewBankFlag != "null" && tNewBankFlag != "" )
  {
    tBankFlag = tNewBankFlag;
  }
  
  //alert("BankFlag: "+tBankFlag);
  
  //���б��������˵���������
  var tBankCodeInitSQL = "1 and comcode like #"+comCode+"%# and not exists(select 1 from LDCOde1 where codetype=#YBTBatBank# and Code1 = a.BankCode and OtherSign=#1#)";
  //easyQueryClick��ѯʱ��������
  var tBankDataSQL = " and not exists(select 1 from LDCOde1 where codetype='YBTBatBank' and Code1 = BankCode and OtherSign='1') ";
  
  if(tBankFlag == "1")   //���������ͨ��������
  {
    tBankCodeInitSQL = "1 and comcode like #"+comCode+"%# and exists(select 1 from LDCOde1 where codetype=#YBTBatBank# and Code=#"+tYBTBankCode+"# and Code1 = a.BankCode and OtherSign=#1#)";
    tBankDataSQL = " and exists(select 1 from LDCOde1 where codetype='YBTBatBank' and Code='"+tYBTBankCode+"' and Code1 = BankCode and OtherSign='1') ";
  }
</script>

<body  onload="initForm();" >
  <form action="./CancelReadFromFileSave.jsp"  method=post name=fm id=fm target="fraTitle" >
    <%@include file="../common/jsp/InputButton.jsp"%>
    
    <table class= common border=0 width=100%>
      <tr>
       <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage

(this,divInvAssBuildInfo);">
            </TD>
  			<td class= titleImg align= center>�������ѯ������</td>
  		</tr>
  	</table>
<div id="divInvAssBuildInfo">
       <div class="maxbox1" >	
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title5>
            ���б���
          </TD>
          <TD  class= input5>
			 <Input class=codeno name=BankCode readonly 
             style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
            onclick="return showCodeList('bank',[this,BankName],[0,1],null,tBankCodeInitSQL,'1');" 
             ondblclick="return showCodeList('bank',[this,BankName],[0,1],null,tBankCodeInitSQL,'1');" 
              onkeyup="return showCodeListKey('bank',[this,BankName],[0,1],null,tBankCodeInitSQL,'1');"
               verify="���д���|notnull&code:bank" readonly=true><input class=codename name=BankName readonly=true>
          </TD>
          <TD  class= title5>
            ���κ�
          </TD>
          <TD  class= input5>
            <Input class="common wid" name=SerialNo >
          </TD>

        </TR>
        <TR  class= common>
          <TD  class= title5>
            ����ʱ��
          </TD>
          <TD  class= input5>
            <Input class="common wid" dateFormat="short" name=ReturnDate >
          </TD>
					<TD  class= title5>
            �����ļ����ƣ�ģ����ѯ��
          </TD>
          <TD  class= input5>
			 			<Input class="common wid" name=InFile>
          </TD>
        </TR>
    </table>
    </div>
    </div>
<!--<INPUT VALUE="��  ѯ" TYPE=button class=cssButton onClick="easyQueryClick();">-->
 <a href="javascript:void(0);" class="button"onClick="easyQueryClick();">��   ѯ</a>

<br><br>  
    <!-- �����������ļ� fraSubmit-->
    
    <table class= common border=0 width=100%>
    	<tr>
         <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage

(this,divBank1);">
            </TD>
			<td class= titleImg align= center>�����ļ���Ϣ</td>
  		</tr>
  	</table>   
        
   
  	<Div  id= "divBank1" style= "display: ''">
      	<table  class= common>
          	<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanBankGrid" >
  					</span> 
  				</td>
  			</tr>
  		</table>
  	</div>
  	
   <!-- <Div id= "divPage" align=center style= "display: none ">
    <INPUT CLASS="cssButton90" VALUE="��ҳ" TYPE=button onClick="turnPage.firstPage();"> 
    <INPUT CLASS="cssButton91" VALUE="��һҳ" TYPE=button onClick="turnPage.previousPage();"> 					
    <INPUT CLASS="cssButton92" VALUE="��һҳ" TYPE=button onClick="turnPage.nextPage();"> 
    <INPUT CLASS="cssButton93" VALUE="βҳ" TYPE=button onClick="turnPage.lastPage();">
    </Div>-->
    
    <br>
     
     <table class= common border=0 width=100%>
      <tr>
       <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage

(this,divInvAssBuildInfo1);">
            </TD>

  			<td class= titleImg align= center>��¼��ع�ԭ��</td>
  		</tr>
        	</table>
      <div id="divInvAssBuildInfo1">
       <div class="maxbox1" >	      
       <table>     
  		<tr>
  			<td><textarea rows="4" cols="100" name=backReason class="common" ></textarea></td>
  	  </tr>
  	</table>
   </div>
   </div> 
    


   <!-- <INPUT VALUE="����ȷ��" class= cssButton TYPE=button onClick="submitForm()">   --> 
    <a href="javascript:void(0);" class="button"onClick="submitForm()">����ȷ��</a>  										
  <br> <br> <br> <br>
  </form>
  
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  
</body>
</html>
