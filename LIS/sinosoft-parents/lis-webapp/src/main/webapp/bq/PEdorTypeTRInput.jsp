<html> 
<% 
/*******************************************************************************
 * <p>Title: Lis 6.5</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : pst <pengst@sinosoft.com.cn>
 * @version  : 1.00, 
 * @date     : 2008-11-26
 * @direction: ��������
 ******************************************************************************/
%>
<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>


<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="./PEdorTypeTR.js"></SCRIPT>
  <%@include file="PEdorTypeTRInit.jsp"%> 
</head>

<body  onload="initForm();" >
  <form action="./PEdorTypeTRSubmit.jsp" method=post name=fm id=fm target="fraSubmit">    
  <div class=maxbox1>
  <TABLE class=common>
    <TR  class= common> 
      <TD  class= title >��ȫ�����</TD>
      <TD  class= input > 
        <input class="readonly wid" readonly name=EdorAcceptNo id=EdorAcceptNo >
      </TD>
      <TD class = title >��������</TD>
      <TD class = input >
      	<Input class=codeno  readonly name=EdorType id=EdorType><input class=codename name=EdorTypeName id=EdorTypeName readonly=true>
      </TD>    
      <TD class = title >������</TD>
      <TD class = input >
      	<input class="readonly wid" readonly name=ContNo id=ContNo>
      </TD>
      <TR class=common>
    	<TD class =title>������������</TD>
    	<TD class = input>    		
    		<input class = "coolDatePicker" readonly name=EdorItemAppDate onClick="laydate({elem: '#EdorItemAppDate'});" id="EdorItemAppDate"><span class="icon"><a onClick="laydate({elem: '#EdorItemAppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
    	</TD>
    	<TD class =title>��Ч����</TD>
    	<TD class = input>
    		<input class = "coolDatePicker" readonly name=EdorValiDate onClick="laydate({elem: '#EdorValiDate'});" id="EdorValiDate"><span class="icon"><a onClick="laydate({elem: '#EdorValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
    	</TD>
    </TR>   
    </TR>
  </TABLE> 
  </div>
  <!--���ֵ���ϸ��Ϣ-->
  <TABLE>
    <TR>
      <TD class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAGInfo1);">
      </td>
      <TD class= titleImg>
        �ͻ�������Ϣ 
      </TD>
   </TR>
   </TABLE>
   <Div  id= "divAGInfo1" class=maxbox1 style= "display: ''">
   <TABLE class=common>
    <TR  class= common> 
      <TD  class= title > Ͷ��������</TD>
      <TD  class= input > 
        <input class="readonly wid" readonly name=AppntName id=AppntName >
      </TD>
      <TD class = title > ֤������ </TD>
      <TD class = input >
      	<input class="readonly wid" readonly name=APPNTIDTYPE id=APPNTIDTYPE>
      </TD>
     
      <TD class = title > ֤������ </TD>
      <TD class = input >
      	<input class="readonly wid" readonly name=AppntIDNo id=AppntIDNo>
      </TD>   
    </TR>
    <TR class=common>
    	<TD class =title>����������</TD>
    	<TD class = input>    		
    		<Input class="readonly wid" readonly name=InsuredName id=InsuredName ></TD>
    	</TD>
    	<TD class =title>֤������</TD>
    	<TD class = input>
    		<Input class="readonly wid" readonly name=InsuredIDType id=InsuredIDType ></TD>
    	</TD>
    	<TD class = title>֤������</TD>
    	<TD class = input>
    	<input class="readonly wid" readonly name = InsuredIDNo id=InsuredIDNo ></TD>
    </TR>
  </TABLE>
</Div>
   <table>
   <tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPol);">
      </td>
      <td class= titleImg>
        ���ջ�����Ϣ
      </td>
   </tr>
   </table>
	 
    <Div  id= "divPol" class=maxbox1 style= "display: ''">
      	<table  class= common>
      		<tr class common>
      			<td class = title>���ִ���</td>
      			<td class = input>
      				<input class="readonly wid" readonly name=RiskCode id=RiskCode>
      			</td>
      			<td class = title>��������</td>
      			<td class = input>
      				<input class="readonly wid" readonly name=RiskName id=RiskName>
      			</td>
		       <TD  class= title > ���Ѷ�Ӧ��</TD>
		       <TD  class= input > 
               <input class="coolDatePicker" readonly name=PaytoDate onClick="laydate({elem: '#PaytoDate'});" id="PaytoDate"><span class="icon"><a onClick="laydate({elem: '#PaytoDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
               </TD>
					</tr>     		
		     <tr>				
       		 <TD  class= title >�� �� </TD>
		       <TD  class= input > 
               <input class="readonly wid" readonly name=Mult id=Mult >
               </TD>  
         
		       <TD  class= title >��������</TD>
		       <TD  class= input > 
               <input class="readonly wid" readonly name=Amnt id=Amnt >
           </TD>

		       <TD  class= title >���ѱ�׼</TD>
		       <TD  class= input > 
               <input class="readonly wid" readonly name=Prem id=Prem>
           </TD>          
  			</tr>  			
    	  </table>   
    </Div>
    
    <table>
   <tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLoan);">
      </td>
      <td class= titleImg>
        �����Ե��峥��Ϣ ���Ƿ���㻹����Ϣ<Input name="isCalInterest" type=checkbox checked onclick="ChangCalInterest();" verify="�Ƿ������Ϣ|code:YesNo">��<font color = red >���˴���Ϣ��ʾ���ǰ���Ϣ��ʽ��������汾Ϣ�ͣ�</font>
      </td>
   </tr>
   </table>
	 
   <Div  id= "divLoan" style= "display: ''">
    <table  class= common >
     		<tr  class= common>
    	  	<td text-align: left colSpan=1>
					<span id="spanLoanGrid" >
					</span> 
			  	</td>
  			</tr>
    </table>
    <!--<table align='center'>
      <INPUT VALUE="��ҳ" TYPE=button class=cssButton onclick="getFirstPage();"> 
      <INPUT VALUE="��һҳ" TYPE=button class=cssButton onclick="getPreviousPage();"> 					
      <INPUT VALUE="��һҳ" TYPE=button class=cssButton onclick="getNextPage();"> 
      <INPUT VALUE="βҳ" TYPE=button class=cssButton onclick="getLastPage();"> 					
    </table>-->
  
  <!--  <table class= common>
    	<div align="right">
    	<tr>
    		<td class = title>�潻����ϼ�</td>
    		<td class = input>	<input class= input name=AllShouldPay ></td>
    		<td class = title>�潻��Ϣ�ϼ�</td>
    		<td class = input><input  class= input  name=AllShouldPayIntrest > </td>
    		<TD class= title>�潻��Ϣ�ϼƽ��</td>
    	  <td class= input>	<input class= input name=AllSumShouldPay ></td>
    	</tr>
    </div>
    </table>-->
  </div>  
<Div id= "divEdorquery" style="display: ''">
    <table class = common  >
		  <TR class = common>
       <TD class= input width="26%"> 
     		 <Input class=cssButton  type=Button value="��  ��" onclick="edorTypeTRSave()">     	
       	 <Input class=cssButton  type=Button value="��  ��" onclick="returnParent()">
       	 <Input class= cssButton TYPE=button VALUE="���±��鿴" onclick="showNotePad();">
     	 </TD>
     	</TR>
    </table>
 </Div>
	 <input type=hidden id="fmtransact" name="fmtransact">
	 <input type=hidden id="ContType" name="ContType">
	 <input type=hidden id="PolNo" name="PolNo">
	 <input type=hidden id="InsuredNo" name="InsuredNo">
	 <input type=hidden id="AReLoan" name="AReLoan">
	 <input type=hidden id="SReLoan" name="SReLoan">	 
	 <input type=hidden id="EdorNo" name="EdorNo">
    <%@ include file="PEdorFeeDetail.jsp" %>	 
  </form>
  <!--<span id="spanCode"  style="display: none; position:absolute; slategray"></span>-->
</body>
 <script language="javascript">
	var splFlag = "<%=request.getParameter("splflag")%>";	
</script>
</html>
