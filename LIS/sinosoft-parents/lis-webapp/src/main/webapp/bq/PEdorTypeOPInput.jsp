<html> 
<%
//�������ƣ�PEdorTypeRCInput.jsp
//�����ܣ����˱�ȫ-�����ղ�����ȡ
//�������ڣ�2007-05��24
//������  ��ZengYG
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
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="./PEdor.js"></SCRIPT>
  <SCRIPT src="./PEdorTypeOP.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@include file="PEdorTypeOPInit.jsp"%>
   
</head>
<body  onload="initForm();" >
  <form action="./PEdorTypeOPSubmit.jsp" method=post name=fm id=fm target="fraSubmit">   
<div class=maxbox1> 
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
      	<input class="readonly wid" readonly name=ContNo id=ContNo>
      </TD>   
    </TR>
    <TR class=common>
    	<TD class =title>������������</TD>
    	<TD class = input>    		
    		<Input class="readonly wid" readonly name=EdorItemAppDate id=EdorItemAppDate ></TD>
    	</TD>
    	<TD class =title>��Ч����</TD>
    	<TD class = input>
    		<Input class="readonly wid" readonly name=EdorValiDate id=EdorValiDate ></TD>
    	</TD>
    	<TD class = title></TD>
    	<TD class = input></TD>
    </TR>
  </TABLE>
  </div>
        <table>
            <tr>
                <td class=common>
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCGrpPol);">
                </td>
                <td class= titleImg>
                    ���ֻ�����Ϣ
                </td>
            </tr>
        </table>
    <Div  id= "divLCGrpPol" style= "display: ''">
        <table  class= common>
        	<tr  class= common>
          		<td text-align: left colSpan=1>
        			<span id="spanPolGrid" >
        			</span> 
        	  	</td>
        	</tr>
        </table>	
			
    </DIV>
  <table>
   	<tr>
      <td class="common">
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAccInfo);">
      </td>
      <td class= titleImg>
        ���ʻ���Ϣ
      </td>
   	</tr>
   </table>
  <Div  id= "divAccInfo" class=maxbox1 style= "display: ''">
  <table>
    <TR class=common>
    	<TD class =title>�ʻ���ֵ</TD>
    	<TD class = input>    		
    		<Input class="readonly wid" readonly name=InsuAccBala id=InsuAccBala></TD>
    
    	<TD class =title>�������</TD>
    	<TD class = input>
    		<Input class="readonly wid" readonly name=CanGetMoney id=CanGetMoney></TD>
    	
    	<TD class = title></TD>
    	<TD class = input></TD>
    </TR>  
    </table>	
  </Div>
   <table>
   <tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGetMoneyInfo);">
      </td>
      <td class= titleImg>
        ��ȡ��Ϣ¼��
      </td>
   </tr>
   </table>
    <Div id= "divGetMoneyInfo" class=maxbox1 style= "display: ''">
    <table>
    <TR class=common>
    	<TD class =title>��ȡ���</TD>
    	<TD class = input>    		
    		<Input class="common wid" name=GetMoney id=GetMoney></TD>
    	
    	<TD class =title>�� �� ��</TD>
    	<TD class = input>
    		<Input class="readonly wid" readonly name=WorkNoteFee id=WorkNoteFee></TD>
    	
    	
    	<TD class = title></TD>
    	<TD class = input></TD>
    </TR> 
    </table> 	    	
    </DIV>

 	        <!-- ��ע��Ϣ�۵�չ�� -->
    <table>
        <tr>
            <td class="common"><img src="../common/images/butExpand.gif" style= "cursor:hand" onclick="showPage(this,divItemComment)"></td>
            <td class="titleImg">��ע��Ϣ</td>
       </tr>
    </table>
    <!-- ��ע��Ϣ¼���� -->
    <div id="divItemComment" class=maxbox1 style="display:''">
        <table class="common">
            <tr class="common">
                <td><textarea class="common" name="Remark" id=Remark cols="107" rows="3" verify="��ע��Ϣ|Len<1000"></textarea></td>
            </tr>
        </table>
    </div>
    
    
   <Div id= "divEdorquery" style="display: ''">
          <Input class= button type=Button value=" �� �� " onclick="edorTypeOPSave()">
          <Input class= button type=Button value=" �� �� " onclick="returnParent()">
   </Div>
      
   <input type=hidden id="fmtransact" name="fmtransact">
   <input type=hidden name="EdorNo">
   <input type=hidden name="PolNo">
   <input type=hidden name="RiskCode">
   <input type=hidden id="tAAType" name= "tAAType">
   <br>
  <%@ include file="PEdorFeeDetail.jsp" %>    <!-- XinYQ added on 2005-12-21 --> 
  <br><br><br><br>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
