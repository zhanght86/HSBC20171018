<html> 
<% 
//�������ƣ�
//�����ܣ����˱�ȫ
//�������ڣ�2002-07-19 16:49:22
//������  ��Tjj
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.*"%>
  <%
     %>    

<head >
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT> 
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT> 
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  
  <SCRIPT src="./PEdor.js"></SCRIPT>
  <SCRIPT src="./GEdorTypeCT.js"></SCRIPT>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@include file="GEdorTypeCTInit.jsp"%>
  
  
</head>
<body  onload="initForm();" >
  <form action="./GEdorTypeCTSubmit.jsp" method=post name=fm id=fm target="fraSubmit">    
  <div class=maxbox1>
	<TABLE class=common>
    <TR  class= common> 
      <TD  class= title > ������</TD>
      <TD  class= input > 
        <input class="readonly wid" readonly name=EdorNo id=EdorNo>
      </TD>
      <TD class = title > �������� </TD>
      <TD class = input >
      	<Input class=codeno  readonly name=EdorType id=EdorType><input class=codename name=EdorTypeName id=EdorTypeName readonly=true>
      </TD>
     
      <TD class = title > ���屣���� </TD>
      <TD class = input >
      	<input class = "readonly wid" readonly name=GrpContNo id=GrpContNo>
      </TD>   
    </TR>
    <TR  class= common>
    	 <TD class =title>��������</TD>
    	  <TD class = input>    		
    		<input class="coolDatePicker" readonly name=EdorItemAppDate onClick="laydate({elem: '#EdorItemAppDate'});" id="EdorItemAppDate"><span class="icon"><a onClick="laydate({elem: '#EdorItemAppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
		  </TD>
    	 <TD class =title>��Ч����</TD>
    	  <TD class = input>
    		<input class="coolDatePicker" readonly name=EdorValiDate onClick="laydate({elem: '#EdorValiDate'});" id="EdorValiDate"><span class="icon"><a onClick="laydate({elem: '#EdorValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
		  </TD>
    		<TD class = title ></TD>
      <TD class = input ></TD>
      </TR>
	</TABLE> 
	</div>
        <table>
            <tr>
                <td class=common>
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCGrpPol);">
                </td>
                <td class= titleImg>
                    �����º�ͬ������Ϣ
                </td>
            </tr>
        </table>
    <Div  id= "divLCGrpPol" style= "display: ''" align=center>
        <table  class= common>
        	<tr  class= common>
          		<td text-align: left colSpan=1>
        			<span id="spanGrpPolGrid" >
        			</span> 
        	  	</td>
        	</tr>
        </table>	
			
    </DIV>
   


   <Div id= "divEdorquery" style="display: ''" align="center">

       	     <Input  class= cssButton type=Button value="�����ͬ" onclick="edorTypeCTSave()">
       	     <Input type=Button value="������ϸ" class= cssButton onclick="MoneyDetail()">
     		 <Input  class= cssButton type=Button value="��  ��" onclick="returnParent()">
     		 <!--Input  class= cssButton TYPE=button VALUE="���±��鿴" onclick="showNotePad();"-->  
	</Div>
<DIV id="divCTInfo" style="display: 'none'">
    <table>
            <tr>
                <td class=common>
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCTFeePolDetail);">
                </td>
                <td class= titleImg> �����˷���ϸ </td>
            </tr>
	</table>
        
	<Div  id= "divCTFeePolDetail" style= "display: ''" align=center>
        <table  class= common>
        	<tr  class= common>
          		<td text-align: left colSpan=1>
        			<span id="spanCTFeePolGrid" >
        			</span> 
        	  	</td>
        	</tr>
        </table>	
	</Div>
	
	 <table>
            <tr>
                <td class=common>
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCTFeeInfo);">
                </td>
                <td class= titleImg> �˷��б� </td>
            </tr>
	</table>
<Div  id= "divCTFeeInfo" style= "display: ''" >	
	<DIV id="DivLCPol" style="display : ''" align="center">
				<table  class= common>
					<tr  class= common>
				  	<td text-align: left colSpan=1>
					  	<span id="spanLCPolGrid" ></span> 
	  				</td>
	  			</tr>
				  
				</table>	
						<INPUT VALUE="��  ҳ" class="cssButton90" type="button" onclick="turnPage1.firstPage();"> 
				    	<INPUT VALUE="��һҳ" class="cssButton91" type="button" onclick="turnPage1.previousPage();"> 					
				    	<INPUT VALUE="��һҳ" class="cssButton92" type="button" onclick="turnPage1.nextPage();"> 
				    	<INPUT VALUE="β  ҳ" class="cssButton93" type="button" onclick="turnPage1.lastPage();">
				  	
 	</DIV>
        

      <table  class= common>       
        <TR class = common>
            <TD  class= title > �˷Ѻϼƽ�� </TD>
			<TD  class= title ><Input class= "readonly wid" readonly name=GetMoney  id=GetMoney>Ԫ</TD>
            <TD  class= title > </TD>
            <TD  class= title > </TD>
            <TD  class= title > </TD>
            <TD  class= title > </TD>
       </TR>
     </table>
	</DIV>
</DIV>	
        
	<Div  id= "divCTFeeDetail" style= "display: none">
        <table  class= common>
        	<tr  class= common>
          		<td text-align: left colSpan=1>
        			<span id="spanCTFeeDetailGrid" >
        			</span> 
        	  	</td>
        	</tr>
        </table>	
	</Div>
	
	<input type=hidden id="fmtransact" name="fmtransact">
	<input type=hidden id="ContType" name="ContType">
	<input type=hidden id="EdorAcceptNo" name="EdorAcceptNo">
	<input type=hidden id="EdorTypeCal" name="EdorTypeCal">
	<input type=hidden id="EdorState" name="EdorState">
	
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
<script language="javascript">
	var splFlag = "<%=request.getParameter("splflag")%>";
	
</script>
</html>
