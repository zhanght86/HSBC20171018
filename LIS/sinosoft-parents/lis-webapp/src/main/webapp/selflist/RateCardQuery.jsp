<html>
<%
//name :RateCardQuery.jsp
//Creator :zz
//date :2008-06-20
//�������ʱ��ѯ����
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
%>

	<head >
  	<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  	<SCRIPT src="RateCardQuery.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  	<%@include file="RateCardQueryInit.jsp"%>
	</head>

	<body  onload="initForm();" >
  	<form action="./RateCardQuerySave.jsp" method=post name=fm id="fm" target="fraSubmit">
		<%@include file="../common/jsp/InputButton.jsp"%>
        <!-- ��ʾ������LLReport1����Ϣ -->
    		
   <Div id= "divLLReport1" style= "display: ''">
   <Table class= common>
	<tr>
    <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
        </td>
		<td class=titleImg>�������ʲ�ѯ(һ�����ֵ�һ�ֱ��Ѷ�Ӧ��һ�ֲ�Ʒ��̬)</td>
	</tr>
	</Table>
  <div class="maxbox1">
  <Div  id= "divFCDay" style= "display: ''"> 
   <Table class= common>
   		<TR class= common>
   			<TD class= title5>���ֱ���</TD>
   			<td class= input5>
   				 <Input class="code" name=Riskcode id="Riskcode" readonly CodeData="0|^141814|MS��ͨ���������˺�����|^141815|MS�����ۺ������˺�����|" onMouseDown="return showCodeListEx('Riskcode',[this]);" ondblclick="return showCodeListEx('Riskcode',[this]);" onkeyup="return showCodeListKeyEx('Riskcode',[this]);">
				</td>
				

        	     <td class= title5>��Ʒ�ƻ�</td>
         <td class= input5>
        	  <Input class="code" name=ProductPlan id="ProductPlan" readonly CodeData="0|^1|1��|^2|2��|" onMouseDown="return showCodeListEx('ProductPlan',[this]);"  ondblclick="return showCodeListEx('ProductPlan',[this]);" onkeyup="return showCodeListKeyEx('ProductPlan',[this]);">
         </td>
   		</TR>

       <TR class= common>
						<TD class= title5>��������</TD>
        <TD class= input5><Input class= "common wid" name=InsuYear id="InsuYear" ></TD>
        
        	
	    	<td class= title5>�������ڵ�λ</td>
        <td class= input5>
        	  <Input class="code" name=InsuYearFlag id="InsuYearFlag" readonly CodeData="0|^Y|��|^M|��|^D|��|^A��|"  onMouseDown="return showCodeListEx('RelationToLCInsured',[this]);"  ondblclick="return showCodeListEx('RelationToLCInsured',[this]);" onkeyup="return showCodeListKeyEx('RelationToLCInsured',[this]);">
        </td>
 
      </TR>
      
       <TR class= common>      	
		   <TD class= title5>����</TD>
           <TD class= input5><Input  class= "common wid" name= Prem id="Prem" ></TD>    
  
      </TR>
      
 </Table>
</Div>
</div>
<!--<a href="javascript:void(0)" class=button onclick="EasyQueryClick();">�顡ѯ</a>-->

  	 <input class=cssButton type=button value="��  ѯ" onclick="EasyQueryClick()">
		
    <Input class= common type= hidden name= OperateType id="OperateType">
    <BR>
    <BR>
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
    			 �嵥�б�
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLLReport2" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  	<td style=" text-align: left" colSpan=1>
  					<span id="spanRateCardGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <Div  id= "divPage" align=center style= "display: 'none' ">
      <INPUT CLASS=cssButton90 VALUE="��ҳ" TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT CLASS=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 
      <INPUT CLASS=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT CLASS=cssButton93 VALUE="βҳ" TYPE=button onclick="turnPage.lastPage();">
      </Div>
  	</div>
    <!--<a href="javascript:void(0)" class=button onclick="ReturnData();">��  ��</a>-->
    <input class=cssButton type=button value="��  ��" onclick="ReturnData()">  
  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
