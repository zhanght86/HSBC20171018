<%@include file="/i18n/language.jsp"%>
<html>
<%
//name :LRFeeRateInput.jsp
//function :FeeRate Table Import
//Creator :Zhang Bin
//date :2008-1-4
%>
	<%@page contentType="text/html;charset=GBK" %>
	<!--�û�У����-->
	<%@page import = "com.sinosoft.utility.*"%>
	<%@page import = "com.sinosoft.lis.schema.*"%>
	<%@page import = "com.sinosoft.lis.vschema.*"%>
	<%@page import = "com.sinosoft.lis.reinsure.*"%>
	<%@include file="../common/jsp/UsrCheck.jsp"%>
	
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">


<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT><SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT><SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css><LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>

<SCRIPT src = "./LRFeeRateInput.js"></SCRIPT> 
<%@include file="./LRFeeRateInit.jsp"%>
</head>
<body  onload="initElementtype();initForm()" >
  <form action="" name=fm target="fraSubmit" method=post>
  <%@include file="../common/jsp/OperateButton.jsp"%>
  <%@include file="../common/jsp/InputButton.jsp"%>
  
   <table>
   	  <tr>
        <td class=common><IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" 
        	OnClick= "showPage(this,divLRCont1);"></td>
    	<td class= titleImg>���ʱ���</td></tr>
    </table>
  
  <Div id= "divLRCont1" style= "display: ''" >
	<br>
   	<Table class= common>
   		<TR class= common>
   			<TD class= title5>
���ʱ���
   			</TD>
   			<TD class= input5>
   				<Input class= common name="FeeTableNo" id="FeeTableNoId" elementtype=nacessary verify="���ʱ���|NOTNULL"> 
   			</TD>
   			<TD class= title5>
���ʱ�����
   			</TD>
   			<TD class= input5>
   				<Input class= common  name="FeeTableName" id="FeeTableNameId" elementtype=nacessary> 
   			</TD>   			
   			
   			<TD class= title5>
���ʱ�����
        </TD>
   			<td class="input5">
          <input class=codeno readonly="readonly" name="FeeTableType" CodeData="0|^01|һ��|^02|����|" 
          ondblclick="return showCodeListEx('State', [this,FeeTableTypeName],[0,1],null,null,null,1);" 
          onkeyup="return showCodeListKeyEx('State', [this,FeeTableTypeName],[0,1],null,null,null,1);" verify="���ʱ�����|NOTNULL"><input 
          class=codename name=FeeTableTypeName readonly="readonly" elementtype=nacessary>
        </td>
      </TR>
   		<TR class= common>
   			<TD class= title5>
���ʱ�״̬
   				</TD>
    	  <TD class= input5 > 
    	    	<Input class=codeno readonly="readonly" NAME="State" CodeData="0|^01|��Ч|^02|δ��Ч" 
    	    	ondblClick="showCodeListEx('',[this,stateName],[0,1],null,null,null,1);" 
    	    	onkeyup="showCodeListKeyEx('',[this,stateName],[0,1],null,null,null,1);"  verify="���ʱ�״̬|NOTNULL"><input 
    	    	class=codename name=stateName readonly="readonly"  elementtype=nacessary>
   			</TD>
   			
   			<TD class= title5>
   			</TD>
   			<TD class= input5>
   			</TD>   			
   
   			<TD class= title5>
        </TD>
   			<td class="input5">
        </td>
      </TR> 
   	</Table>
  </Div>
  <br>
  <div id="divHidden" style= "display:none;">
	  <table>
	    <tr>
	      <td class=common><IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" 
	      	OnClick= "showPage(this,divLRCont2);"></td>
	  	<td class= titleImg>��ͬ�ֱ���Ϣ</td></tr>
	  </table>
	  <Div id= "divLRCont2" style= "display: ''">
			  <table class="common">		
					<tr class="common">
						<td style="text-align:left;" colSpan=1><span id="spanContCessGrid"></span></td>
					</tr>
				</table>
		    </div>
	  </Div> 
	
  <br>
    <table>
   	  <tr>
        <td class=common><IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" 
        	OnClick= "showPage(this,divTableClumnDef);"></td>
    	<td class= titleImg>���ʱ��ֶζ���</td></tr>
    </table>
    
   	<Div  id= "divTableClumnDef" style= "display: ''">
      <table  class= common>
          <tr  class= common>
            <td text-align:left colSpan=1>
          		<span id="spanTableClumnDefGrid" ></span>
        		</td>
      		</tr>
    	</table>
	</div>
	<br>
	
   <table>
    <tr>
      <td class=common>
      	<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSerialRemark);">
      </td>
  	<td class= titleImg>���ʱ�������Ϣ</td></tr>
  </table>
  
	<Div  id= "divSerialRemark" style= "display: ''">
		<TR  class= common>
			<TD  class= input5 colspan="6">
			    <textarea name="ReMark" cols="100%" rows="3"  class="common">
			    </textarea>
			</TD>
		</TR>
  </Div>
  <br>
  <hr>
  <input type="hidden" name="OperateType" >
	</form>
	
	<input class="cssButton" type="button" value="��һ��" onclick="nextStep()">
	
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>