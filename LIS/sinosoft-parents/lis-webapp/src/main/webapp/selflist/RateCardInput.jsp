<html>
<%
//name :RateCardInput.jsp
//Creator :zz
//date :2008-06-19
//�������ʱ���˵�
//
%>
	<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
	<!--�û�У����-->
	<%@page import = "com.sinosoft.utility.*"%>
	<%@page import = "com.sinosoft.lis.schema.*"%>
	<%@page import = "com.sinosoft.lis.vschema.*"%>
	<%@page import = "com.sinosoft.lis.certify.*"%>
	<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
    GlobalInput tGlobalInput = (GlobalInput)session.getValue("GI");
    String Branch =tGlobalInput.ComCode;
    loggerDebug("RateCardInput","��ҳ���õ�½������"+Branch);
%>

	<head >
  	<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src = "RateCardInput.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  	<%@include file="RateCardInit.jsp"%>
	</head>

<body  onload="initForm();" >
  <form action="./RateCardSave.jsp" method=post name=fm id="fm" target="fraSubmit">
    <Table class= common>
	     <tr>
          <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
        </td>
	         <td class=titleImg>�������ʶ���(һ�����ֵ�һ�ֱ��Ѷ�Ӧһ�ֲ�Ʒ��̬)</td>
	     </tr>
	  </Table>
    <div class="maxbox1"> 
      <Div  id= "divFCDay" style= "display: ''">
   	    <Table class= common>
   		    <TR class= common>
   			    <TD class= "title5">���ֱ���</TD>
   			    <td class= "input5">
   				    <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name=Riskcode id="Riskcode" readonly CodeData="0|^141814|MS��ͨ���������˺�����|^141815|MS�����ۺ������˺�����|" onClick="return showCodeListEx('Riskcode',[this]);" onDblClick="return showCodeListEx('Riskcode',[this]);" onKeyUp="return showCodeListKeyEx('Riskcode',[this]);" >
				    </td>
            <td class= "title5">��Ʒ�ƻ�</td>
            <td class= "input5">
        	    <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name=ProductPlan id="ProductPlan" readonly CodeData="0|^1|1��|^2|2��|" onClick="return showCodeListEx('ProductPlan',[this]);" onDblClick="return showCodeListEx('ProductPlan',[this]);" onKeyUp="return showCodeListKeyEx('ProductPlan',[this]);">
            </td>
   		    </TR>
          <TR class= common>
				    <TD class= "title5">��������</TD>
            <TD class= "input5"><Input class= "common wid" name=InsuYear id="InsuYear" ></TD>	
	    	    <td class= "title5">�������ڵ�λ</td>
            <td class= "input5">
        	    <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name=InsuYearFlag id="InsuYearFlag" readonly CodeData="0|^Y|��|^M|��|^D|��|^A|��|" onClick="return showCodeListEx('RelationToLCInsured',[this]);" onDblClick="return showCodeListEx('RelationToLCInsured',[this]);" onKeyUp="return showCodeListKeyEx('RelationToLCInsured',[this]);">
            </td>        
          </TR>     
          <TR class= common>      	
		        <TD class= "title5">����</TD>
            <TD class= "input5"><Input class= "common wid" name= Prem  id="Prem"></TD>
           
            <TD class= "title5">����</TD>
            <TD class= "input5"><Input class= "common wid" name= Mult id="Mult" value="1"></TD>
          </TR>     
        </Table>
      </Div>
    </div>
</form>
<%@include file="../common/jsp/OperateButton.jsp"%>
  <%@include file="../common/jsp/InputButton.jsp"%>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
