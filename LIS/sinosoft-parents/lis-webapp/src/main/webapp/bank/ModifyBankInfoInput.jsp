<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html> 
<%
//�������ƣ�ModifyBankInfoInput.jsp
//�����ܣ�
//�������ڣ�2002-11-18 11:10:36
//������  ���� ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<head > 
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

  <SCRIPT src="ModifyBankInfoInput.js"></SCRIPT> 
  <%@include file="ModifyBankInfoInit.jsp"%>
  
  <title>�޸��˻���Ϣ </title>
</head>

<%
  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");
%>

<script>
  comCode = "<%=tGlobalInput.ComCode%>";
</script>

<body  onload="initForm();" >
  <form action="./ModifyBankInfoSave.jsp" method=post name=fm id="fm" target="fraTitle">
    <%@include file="../common/jsp/InputButton.jsp"%>
    
    <!-- ������Ϣ���� -->
   
  <table class= common border=0 width=100%>
    <tr>
          <td class=common>
             <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,maxbox);">
          </td>
		  <td class= titleImg align= center>�������ѯ������</td>
		</tr>
	</table>
	<div class="maxbox1" id="maxbox">
    <table  class= common align=center>
    	  <TR  class= common>
    	   <TD  class= title5>
            �����վݺ�/���л���Э�����
         </TD>
         <TD  class= input5>
           <Input NAME=TempfeeNo class="common wid">
        </TD>
        <TD  class= title5>
            �������
        </TD>  
        <TD  class= input5>
            <Input class="common wid" name=FManageCom  value="<%=tGlobalInput.ComCode%>" readonly >
        </TD> 
       </TR>
     </table> 
     
    	  <!--TD  class= title>
            ӡˢ��
         </TD>
         <TD  class= input>
           <Input NAME=PrtNo class=common>
         </TD--> 
         <!--ȡ����ȫ�շѵ����⴦����ȫ�ı���ڱ�ȫ��ת���������ع��������-->
          <!--TD  class= title>
            �շ�����
          </TD>
          <TD  class= input>
          	<Input class="codeno" name=FeeType verify="�շ�����|notnull" CodeData="0|^1|��ȫ�շ�^2|�����շ�" ondblclick="return showCodeListEx('FeeType',[this,FeeTypeName],[0,1]);" onkeyup="return showCodeListKeyEx('FeeType',[this,FeeTypeName],[0,1]);" ><input class=codename name=FeeTypeName readonly=true>
          </TD>
        </TR>
    </table>    
      <Div id='divFeeType1' style='display:none'>
       <table class=common align=center>            
       <TR class=common >                                  
         <TD  class= title>
            ����֪ͨ���
         </TD>
          <TD  class= input>
      			<Input class=common name=GetNoticeNo >
          </TD>          
        </TR> 
        </table> 
      </Div>        

     <Div id='divFeeType2' style='display:none'>
       <table class=common align=center>
       <TR class=common>                                  
         <TD  class= title>
            ӡˢ��
         </TD>
         <TD  class= input>
           <Input NAME=PrtNo class=common>
         </TD>        
        </TR>
        </table> 
      </Div-->           
      <INPUT VALUE="��  ѯ" TYPE=Button class=cssButton name=butQuery onClick="easyQueryClick();">
      </div>
    <table class= common border=0 width=100%>
    	<tr>
        <td class=common>
    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,maxbox2);">
</td>
			<td class= titleImg align= center> 
				��ȷ���ݽ�����¼������ݣ�
			</td>
  		</tr>
  	</table>   
    <div class="maxbox" id="maxbox2">   
    <!-- �ݽ�����Ϣ���б� -->
    <table>
    	<tr>
    		<td class= titleImg>
    			 �ݽ�����Ϣ
    		</td>
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
  	
    <Div id= "divPage" align=center style= "display:none ">
      <INPUT VALUE="��ҳ" class="cssButton90" TYPE=button onClick="getFirstPage();"> 
      <INPUT VALUE="��һҳ" class="cssButton91" TYPE=button onClick="getPreviousPage();"> 					
      <INPUT VALUE="��һҳ" class="cssButton92" TYPE=button onClick="getNextPage();"> 
      <INPUT VALUE="βҳ" class="cssButton93" TYPE=button onClick="getLastPage();"> 					
    </Div>
   </div>
    <br><br>
   
    <table class= common border=0 width=100%>
    <tr>
            <td class=common>
    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,maxbox3);">
</td>
			<td class= titleImg align= center>¼��Ҫ�޸ĵ�������Ϣ������ȫ��¼�룺</td>
		</tr>
	  </table> 
	 <div class="maxbox1" id="maxbox3">	
    <table  class= common align=center>
      <TR CLASS=common>
        <!--����Ϊ���л���Э�����-->
        <TD  class= title>
          �վݺ�
        </TD>
        <TD  class= input>
          <Input readonly  NAME=TempfeeNo1 class="common wid">
        </TD>
        <!--TD  class= title>
          ����֪ͨ���
        </TD>
        <TD  class= input>
          <Input readonly class=readonly NAME=GetNoticeNo2 class=common>
        </TD>
        
        <TD  class= title>
          ���ѷ�ʽ
        </TD>
        <TD  class= input>
          <Input class="codeno" name=PayMode CodeData="0|^1|�ֽ�^4|����ת��" ondblclick="return showCodeListEx('PayMode',[this,PayModeName],[0,1]);" onkeyup="return showCodeListKeyEx('PayMode',[this,PayModeName],[0,1]);" ><input class=codename name=PayModeName readonly=true>
        </TD-->
      </TR>
      
    	<TR CLASS=common>
        <TD CLASS=title>
          ���д��� 
        </TD>
        <TD CLASS=input COLSPAN=1>
          <Input class=codeno name=BankCode id="BankCode" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onMouseDown="return showCodeList('bank',[this,BankName],[0,1],null,mSql,'1');" onDblClick="return showCodeList('bank',[this,BankName],[0,1],null,mSql,'1');"  onkeyup="return showCodeListKey('bank',[this,BankName],[0,1],null,mSql,'1');" verify="���д���|notnull&code:bank"><input class=codename name=BankName readonly=true>
        </TD>
        <TD CLASS=title>
          �˻����� 
        </TD>
        <TD CLASS=input COLSPAN=1>
          <Input NAME=AccName VALUE="" CLASS="common wid" MAXLENGTH=20 >
        </TD>
        <TD CLASS=title>
          �����˺� 
        </TD>
        <TD CLASS=input COLSPAN=1>
          <Input NAME=AccNo VALUE="" CLASS="coolConfirmBox wid" MAXLENGTH=40 onBlur="checkBank(BankCode,AccNo);">
        </TD>
      </TR>
    </table>
    
    <br>
    <INPUT VALUE="��������" class= cssButton TYPE=Button name=butSave onClick="submitForm()">
    <INPUT VALUE="" TYPE=hidden name=serialNo>
  </div>
  <br><br><br><br>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  
</body>
</html>
<script>
var bCom=comCode.trim();
if(bCom.length >4)
{
	bCom=bCom.substring(0,4);
}
var mSql="1 and comcode like #"+bCom+"%#" ;	
</script>

