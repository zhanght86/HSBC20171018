<html> 
<% 
//�������ƣ�PEdorTypeIPInput.jsp
//�����ܣ�
//�������ڣ�2007-04-16
//������  ��tp
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
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
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="./PEdor.js"></SCRIPT>
  <SCRIPT src="./PEdorTypeIPInput.js"></SCRIPT>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@include file="PEdorTypeIPInit.jsp"%>
  <Title>111</Title>

</head>
<body  onload="initForm();" >
  <form action="./PEdorTypeIPSubmit.jsp" method=post name=fm id=fm target="fraSubmit">
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
        <input class = "readonly wid" readonly name=ContNo id=ContNo>
      </TD>
    </TR>
    <TR  class= common>
         <TD class =title>��������</TD>
          <TD class = input>
            <input class="coolDatePicker" readonly name=EdorItemAppDate onClick="laydate({elem: '#EdorItemAppDate'});" id="EdorItemAppDate"><span class="icon"><a onClick="laydate({elem: '#EdorItemAppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
         <TD class =title>��Ч����</TD>
          <TD class = input>
            <input class="coolDatePicker" readonly name=EdorValiDate onClick="laydate({elem: '#EdorValiDate'});" id="EdorValiDate"><span class="icon"><a onClick="laydate({elem: '#EdorValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
            <TD  class= title > </TD>
            <TD  class= title > </TD>
      </TR>
  </TABLE>
  </div>
  

        <!--Div  id= "divInsuredInfo" style= "display: none">
        <table>
            <tr>
                <td class="common">
                 <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCGrpPol);">
                </td>
            <td class= titleImg>
                �ͻ���Ϣ
            </td>
        </tr>
        </table>
        <Div  id= "divInsuredGrid" style= "display: ''">
            <table  class= common>
                <tr  class= common>
                    <td text-align: left colSpan=1>
                        <span id="spanInsuredGrid" >
                        </span>
                    </td>
                </tr>
            </table>
    </div>
    </Div-->
    <table>
    <tr>
      <td class="common">
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPolGrid1);">
      </td>
      <td class= titleImg>
        �ͻ�������Ϣ
      </td>
    </tr>
   </table>
  <Div  id= "divPolGrid1" style= "display: ''">
        <table  class= common>
            <tr  class= common>
                <td text-align: left colSpan=1>
                    <span id="spanCustomerGrid" >
                    </span>
                </td>
            </tr>
        </table>
    </Div>
    
        <Div  id= "divLPInsured" class=maxbox1 style= "display: ''">
      <table  class= common>
        <TR class = common>
            <TD  class= title5 > ������Ч���� </TD>
            <TD  class= input5 ><input class="coolDatePicker" readonly name=CvaliDate onClick="laydate({elem: '#CvaliDate'});" id="CvaliDate"><span class="icon"><a onClick="laydate({elem: '#CvaliDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
            <TD  class= title5 > �ͻ�ǩ������ </TD>
            <TD  class= input5 ><input class="coolDatePicker" readonly name=CustomGetPolDate onClick="laydate({elem: '#CustomGetPolDate'});" id="CustomGetPolDate"><span class="icon"><a onClick="laydate({elem: '#CustomGetPolDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>            
        </TR>
        
        <TR class = common >        	  
            <TD  class= title5 > ���Ѷ�Ӧ�� </TD>
            <TD  class= input5 ><input class="coolDatePicker" readonly name=PayToDate onClick="laydate({elem: '#PayToDate'});" id="PayToDate"><span class="icon"><a onClick="laydate({elem: '#PayToDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
            <TD  class= title5 > ��ԥ�ڱ�־ </TD>
            <TD  class= input5 ><input class="readonly wid" readonly name=CTType id=CTType ></TD>
            <!--
            <TD  class= title > �˱������ѱ�׼</TD>
            <TD  class= title ><input class="readonly" readonly name=Poundage ></TD>
            -->
        </TR>      
     </table>
   </Div>
        <table>
            <tr>
                <td class="common">
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCGrpPol);">
                </td>
                <td class= titleImg>
                    ����������Ϣ
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
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divInsuAcc);">
        </td>
        <td class= titleImg>
          �˻���Ϣ
        </td>
      </tr>
    </table>
    
    <Div  id= "divInsuAcc" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					<span id="spanInsuAccGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	<Div  id= "divPage1" align=center style= "display: none ">
        <INPUT CLASS=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage1.firstPage();"> 
        <INPUT CLASS=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage1.previousPage();"> 					
        <INPUT CLASS=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage1.nextPage();"> 
        <INPUT CLASS=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage1.lastPage();"> 			
      </Div>
      </Div>	
      
  
   <Input  class= cssButton type=Button value=" ׷ �� " onclick="edorTypeCTSave()">
   
   <table>
            <tr>
                <td class="common">
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCGrpPolb);">
                </td>
                <td class= titleImg>
                    ׷�Ӽ�¼
                </td>
            </tr>
        </table>
    <Div  id= "divLCGrpPolb" style= "display: ''">
        <table  class= common>
            <tr  class= common>
                <td text-align: left colSpan=1>
                    <span id="spanPolGridb" >
                    </span>
                </td>
            </tr>
        </table>

    </DIV>
   
   
   
   
   
   <Div id= "divEdorQuery" style="display: ''">
            
             <Input  class= cssButton type=Button value="ɾ  ��" onclick="deleterecord()">
             
             
    </Div><BR>
     <Input  class= cssButton type=Button value="��  ��" onclick="edorIPSave()">
    <Input  class= cssButton type=Button value="��  ��" onclick="returnParent()">
    <BR>
    
    <input type=hidden id="fmtransact" name="fmtransact">
    <input type=hidden id="ContType" name="ContType">
    <input type=hidden name="EdorNo">
    <input type=hidden name="PolNo">

  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br /> <br /> <br /> <br />
</body>
<script language="javascript">
    var splFlag = "<%=request.getParameter("splflag")%>";

</script>
</html>
