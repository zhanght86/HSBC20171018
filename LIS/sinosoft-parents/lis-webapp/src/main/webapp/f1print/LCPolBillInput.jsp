<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>    
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2003-1-15
//������  ��lh
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var comcode = "<%=tGI.ComCode%>"; //��¼��½����
</script>
<head>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>  
  <SCRIPT src="LCPolBillInput.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
   <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <%@include file="LCPolBillInit.jsp"%>
</head>
<body  onload="initForm();" >    
  <form  method=post name=fm id=fm target="fraSubmit">
    <table>
    	<tr> 
        
    		<td  class="common">
    		 <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol);">
    		</td>
    		 <td class= titleImg>
        		�����ѯ������
       		 </td>   		 
    	</tr>
    </table>
    <Div  id= "divLCPol" style= "display: ''">
     <div class="maxbox1" >
      <table  class= common>
        <TR  class= common>
         <TD  class= title5>
            �������
          </TD>          
           <TD class= input5><Input type="text" class="codeno" name=ManageCom  id=ManageCom
           style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="return showCodeList('station',[this,ManageComName],[0,1]);"  
           onDblClick="return showCodeList('station',[this,ManageComName],[0,1]);"
            onKeyUp="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class="codename" name="ManageComName" > </TD>
          <TD  class= title5 width="25%">
            ��ʼ����
          </TD>
          <TD  class= input5 width="25%">
            
             <input class="coolDatePicker" dateFormat="short" verify="��ʼʱ��|NOTNULL" id=StartDay  name=StartDay onClick="laydate
({elem:'#StartDay'});" > <span class="icon"><a onClick="laydate({elem: '#StartDay'});"><img 
src="../common/laydate/skins/default/icon.png" /></a></span>

          </TD>
            </TR>
        <TR  class= common>
          <TD  class= title5 width="25%">
            ��������
          </TD>
          <TD  class= input5 width="25%">
           
            
 <input class="coolDatePicker" dateFormat="short" id=EndDay  name=EndDay  onClick="laydate({elem:'#EndDay '});" verify="��������|NOTNULL"> <span class="icon"><a onClick="laydate({elem: '#EndDay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>  
      
	     		<TD  class= title5>
            �������� 
          </TD>
          <TD class= input5><Input type="text" class="codeno" name=SaleChnl  id=SaleChnl verify="��������|code:SaleChnl" 
          style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="return showCodeList('SaleChnl',[this,SaleChnlName],[0,1]);" 

          onDblClick="return showCodeList('SaleChnl',[this,SaleChnlName],[0,1]);" 
          onKeyUp="return showCodeListKey('SaleChnl',[this,SaleChnlName],[0,1]);"><input class="codename" name="SaleChnlName" ></TD>          	
            </TR>
        <TR  class= common>
          <TD  class= title5 width="25%">
            ��ʼʱ��
          </TD>
          <TD  class= input5 width="25%">
            <Input class="common wid" name=StartTime verify="��ʼʱ��|NOTNULL">
           
          </TD>
          <TD  class= title5 width="25%">
            ����ʱ��
          </TD>
          <TD  class= input5 width="25%">
            <Input class="common wid" name=EndTime verify="����ʱ��|NOTNULL">
            

          </TD>  
        </TR>
        <TR  class= common>
 
          <TD  class= title5>
            ��ʼ������
          </TD>
          <TD  class= input5>
            <Input class="common wid" name=StartContNo >
          </TD>
          <TD  class= title5>
            ��ֹ������
          </TD>
          <TD  class= input5>
            <Input class="common wid" name=EndContNo >
          </TD>	 
           </TR>
        <TR  class= common>
          <TD  class= title5> �������� </TD>
          <TD  class= input5>  <Input type="text" class="codeno" name=ManageGrade   id=ManageGrade
           CodeData="0|^4|��������|^6|��������^8|�ļ�����" 
           style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="showCodeListEx('MangeComGrade',[this,MangeComGradeName],[0,1]);" 

           ondblClick="showCodeListEx('MangeComGrade',[this,MangeComGradeName],[0,1]);" 
           onKeyUp="showCodeListKeyEx('MangeComGrade',[this,MangeComGradeName],[0,1]);"><input class="codename" name="MangeComGradeName" ></TD>      	
         </TR>     
  
     <!--     <tr>
        <TD  class= title>
            չҵ�������� 
          </TD>
          <TD  class= input>
            <Input class=common name=AgentGroup >
            <input name="btnQueryCom" class="common" type="button" value="����ѯ" onclick="queryCom()" style="width:100; ">
          </TD> 
          <TD  class= title>
            �����˱���
          </TD>
          <TD  class= input>
            <Input class="code" name=AgentCode  ondblclick="return queryAgent(comcode);" onkeyup="return queryAgent(comcode);">
          </TD>
        </tr>
        <TR  class= common>
          <TD  class= title>
            ��ʼ������
          </TD>
          <TD  class= input>
            <Input class= common name=StartPolNo >
          </TD>
          <TD  class= title>
            ��ֹ������
          </TD>
          <TD  class= input>
            <Input class= common name=EndPolNo >
          </TD>	     		  
        </TR>
        <TR>
          <TD  class= title>
            ���ֱ���
          </TD>
          <TD  class= input>
            <Input class="code" name=RiskCode ondblclick="return showCodeList('RiskCode',[this]);" onkeyup="return showCodeListKey('RiskCode',[this]);">
          </TD>  
          <TD  class= title>
            ӡˢ��
          </TD>
          <TD  class= input>
            <Input class= common name=PrtNo >
          </TD>  
        </TR>
        <TR>
          <TD  class= title>
            Ͷ��������
          </TD>
          <TD  class= input>
            <Input class= common name=AppntName >
          </TD>  
          <TD  class= title>
            ����������
          </TD>
          <TD  class= input>
            <Input class= common name=InsuredName >
          </TD>  
        </TR>     -->
     </table>
     </div></Div> 

<br>
  	<input class="cssButton" type=button value="��ͨ���������嵥" onClick="fnBillPrint()">
    <input class= cssButton type=Button name="BillPrintBank" value="���д�ӡ���������嵥" onclick="fnBillPrintBank()">
  	<!--<input class="cssButton" type=button  name="BillPrintVIP" value="VIP���������嵥" onClick="fnBillPrintVIP()">-->
    <input class="cssButton" type=button  name="BillRePrint" value="���򱣵������嵥" onClick="fnBillPrintLR()">
  	<input class="cssButton" type=button  name="BillLRPrint" value="�ش򱣵������嵥" onClick="fnBillPrintRe()">
        
    </Div>
      
        <input type=hidden id="fmtransact" name="fmtransact">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
