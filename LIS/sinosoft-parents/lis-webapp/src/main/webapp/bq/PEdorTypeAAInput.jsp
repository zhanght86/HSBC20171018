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
 * @direction: �����ռӱ�
 ******************************************************************************/
%>
<%@page contentType="text/html;charset=GBK" %>
   
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT> 

  <SCRIPT src="./PEdor.js"></SCRIPT>
  <SCRIPT src="./PEdorTypeAA.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@include file="PEdorTypeAAInit.jsp"%>
  
  <title> ���ڸ����� </title> 
</head>
<body  onload="initForm();" >
  <form action="./PEdorTypeAASubmit.jsp" method=post name=fm id=fm target="fraSubmit">  
  <div class="maxbox1">  
 <TABLE class=common>
    <TR  class= common> 
      <TD  class= title > ��ȫ�����</TD>
      <TD  class= input > 
        <input class="readonly wid" readonly name=EdorAcceptNo id=EdorAcceptNo >
      </TD>
      <TD class = title > �������� </TD>
      <TD class = input >
      	<Input class=codeno  readonly name=EdorType id=EdorType><input class=codename name=EdorTypeName id=EdorTypeName readonly=true>
      </TD>
     
      <TD class = title > ������ </TD>
      <TD class = input >
      	<input class = "readonly wid" readonly name=ContNo id=ContNo>
      </TD>   
    </TR>
    <TR class=common>
    	<TD class =title>������������</TD>
    	<TD class = input>    		
    		<input class = "readonly wid" readonly name=EdorItemAppDate id=EdorItemAppDate ></TD>
    	</TD>
    	<TD class =title>��Ч����</TD>
    	<TD class = input>
    		<input class = "readonly wid" readonly name=EdorValiDate id=EdorValiDate ></TD>
    </TR>
  </TABLE></div> 
<!-- ������ʾ��ʼ --->   
<div id = "divPEdor" style = "display: ">
   <!--���ֵ���ϸ��Ϣ-->
   <table>
   <tr>
      <td class="common">
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCustomerGrid);">
      </td>
      <td class= titleImg>
        �ͻ�������Ϣ
      </td>
   </tr>
   </table>
	 
    <Div  id= "divCustomerGrid" style= "display: ''">
      	<table  class= common>
        	<tr  class= common>
          		<td text-align: left colSpan=1>
        			<span id="spanCustomerGrid" >
        			</span> 
        	  	</td>
        	</tr>
        </table>
    </Div>
        				
        <table>
            <tr>
                <td class="common">
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divMainPolGrid);">
                </td>
                <td class= titleImg>
                    ���ջ�����Ϣ
                </td>
            </tr>
        </table>
    <Div  id= "divMainPolGrid" style= "display: ''">
        <table  class= common>
        	<tr  class= common>
          		<td text-align: left colSpan=1>
        			<span id="spanMainPolGrid" >
        			</span> 
        	  	</td>
        	</tr>
        </table>	
			
    </DIV>
    	
  <table>
   <tr>
      <td class="common">
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPolGrid);">
      </td>
      <td class= titleImg>
        �����ջ�����Ϣ
      </td>
   </tr>
   </table>
    	<Div  id= "divPolGrid" style= "display: ''">
      	<table  class= common>
        	<tr  class= common>
          		<td text-align: left colSpan=1>
        			<span id="spanPolGrid" >
        			</span> 
        	  	</td>
        	</tr>
        </table>
    </Div>
    
        <Div id= "divEdorquery" style="display: ''">   
   <table class = common>
     <tr class = common>
      <td class = titleImg>
        ���ּӱ� <font color = red>(һ�ζ�һ�����ּӱ�)</font>
      </td>

     </tr>	
     <tr class = common>
   		<td  class = title>�ӱ�����/����</td>
			<td  class = input><input class = common name=AAMoney></td>		
			<td class = title></td>
						  
	   </tr>
   </table>



	  <TABLE>	
		  <TR>
	       <TD> 
	         <!--<Input class= cssButton type=Button value="  ��  ��  " onclick="addpolamnt()">	-->

	         						  
	     		 <Input class= cssButton type=Button value="  ��  ��  " onclick="edorTypeAASave()">	  
	        <Input class= cssButton type=Button value=" ��ԭ���� " onclick="reback()">	   	 
	       	 <Input class= cssButton type=Button value="  ��  ��  " onclick="returnParent()">
	       	 <input class= cssButton TYPE=button VALUE="���±��鿴" onclick="showNotePad()">
	     	 </TD>
	     </TR>
     </TABLE>
    </Div>
</div>
<!--  ������ʾ���� -->
	  
	 <input type=hidden id="CalMode" name = "CalMode">
	 <input type=hidden id="fmtransact" name="fmtransact">
	 <input type=hidden id="ContType" value= 1 name="ContType">	 
	 <input type=hidden id="InsuredNo" name= "InsuredNo">
	 <input type=hidden id="EdorNo" name= "EdorNo">
  <%@ include file="PEdorFeeDetail.jsp" %> 	 
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
 <script language="javascript">
	var splFlag = "<%=request.getParameter("splflag")%>";
</script>
</html>
