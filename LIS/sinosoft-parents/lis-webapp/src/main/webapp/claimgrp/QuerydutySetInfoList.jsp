<html>
<%
//**************************************************************************************************
//Name��QuerydutySetInfoList.jsp
//Function����������__������ͳ�Ʋ�ѯ
//Author��pd
//Date: 2005-12-28
//Desc:
//**************************************************************************************************
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head >
  <SCRIPT src="../common/javascript/Common.js" type="text/javascript"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js" type="text/javascript"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js" type="text/javascript"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js" type="text/javascript"></SCRIPT>
  <SCRIPT src="../common/Calendar/Calendar.js" type="text/javascript"></SCRIPT>
  <SCRIPT src="QuerydutySetInfoList.js" type="text/javascript"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="QuerydutySetInfoListInit.jsp"%>
</head>
<body  onload="initForm();">
  <form action="./RegisterSave.jsp" method=post name=fm id=fm target="fraSubmit">

    <!--���ձ����������ͼ�����Ϣ-->
    <table>
     <tr>
      <td class=common>
      <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,PolDutyKind);">
      </td>
      <td class= titleImg>
      ����������Ϣ
      </td>
     </tr>
    </table>

    <Div  id= "PolDutyKind" style= "display:''">
     <Table  class= common>
      <tr><td text-align: left colSpan=1>
       <span id="spanPolDutyKindGrid" ></span>
      </td></tr>
     </Table>
        <!--<table align = center>
            <tr>
                <td><INPUT VALUE="��  ҳ" class=cssButton TYPE=button onclick="turnPage3.firstPage();"></td>
                <td><INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage3.previousPage();"></td>
                <td><INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage3.nextPage();"></td>
                <td><INPUT VALUE="β  ҳ" class=cssButton TYPE=button onclick="turnPage3.lastPage();"></td>
            </tr>
        </table>-->        
    </div>

   <table>
       <tr>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,AppAffixList);">
       </td>
       <td class= titleImg>
        ���������Ϣ
       </td>
    </tr>
      </table>
         <Div  id= "AppAffixList" style= "display: ''" >
       <table  class= common>
           <tr  class= common>
               <td text-align: left colSpan=1>
               <span id="spanPolDutyCodeGrid" >
               </span>
            </td>
         </tr>
      </table>
        <!--<table align = center>
            <tr>
                <td><INPUT VALUE="��  ҳ" class=cssButton TYPE=button onclick="turnPage2.firstPage();"></td>
                <td><INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage2.previousPage();"></td>
                <td><INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage2.nextPage();"></td>
                <td><INPUT VALUE="β  ҳ" class=cssButton TYPE=button onclick="turnPage2.lastPage();"></td>
            </tr>
        </table>-->        
      </div>


      <input class=cssButton  type=button value=" �� �� " onclick="returnParent()">

   <input type=hidden id="fmtransact" name="fmtransact">
   <Input type=hidden class="readonly" readonly name=CaseNo >
   <Input type=hidden class="readonly" readonly name=customerName >
   <Input type=hidden class="readonly" readonly name=customerNo >
 <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  </form>


</body>
</html>