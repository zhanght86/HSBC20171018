<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>  
<%
   
    String CurrentDate = PubFun.getCurrentDate();
    loggerDebug("ProductSaleConfigInput",CurrentDate);   
    //
   
%>


<head>

<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>  
  <SCRIPT src="ProductSaleConfig.js"></SCRIPT> 
  <%@include file="ProductSaleConfigInit.jsp"%> 
  <%@include file="../common/jsp/ManageComLimit.jsp"%>

</head>
<body  onload="initForm();" >

<form action="./ProductSaleConfigSave.jsp" method=post name=fm id=fm target="fraSubmit">
<table>
    <tr class=common>
   <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divBonusRisk);"></td>
    <td class=titleImg>��Ʒ����ͣ�ۻ�����Ϣ</td>
    </td>
    </tr>
    </table>
  <Div  id= "divBonusRisk" style= "display: ''" class="maxbox1">      
    <table  class= common>
      <TR class= common>
      		<TD  class= title5>���ֱ���</TD>
          <TD  class= input5>
	            <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=RiskCode id=RiskCode  verify="���ִ���|NOTNUlL&CODE:llclaimrisk" ondblclick="showCodeList('llclaimrisk',[this,RiskCodeName],[0,1],null,null,null,1,'400');" onclick="showCodeList('llclaimrisk',[this,RiskCodeName],[0,1],null,null,null,1,'400');" onkeyup="showCodeListKey('llclaimrisk',[this,RiskCodeName],[0,1],null,null,null,1,'400');"><input class="codename" name="RiskCodeName" id="RiskCodeName" readonly>
          </TD>  
          <td class="title5" style="display:''">��������</td>
					<td class="input5" style="display:''">
						<input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name="SaleChnl" id="SaleChnl" verify="��������|notnull&CODE:salechnl"  verifyorder="1"  ondblclick="showCodeList('salechnl',[this,SaleChnlName],[0,1],null,null,null,1,'150');" onclick="showCodeList('salechnl',[this,SaleChnlName],[0,1],null,null,null,1,'150');" onkeyup="showCodeListKey('salechnl',[this,SaleChnlName],[0,1],null,null,null,1,'150');" ><input class="codename" name="SaleChnlName" id="SaleChnlName" readonly>
					</td>        
      </TR> 
       <TR class= common>
       	
      	<TD  class= title5>
            ���������
          </TD>          
          <TD class= input5>    
          <input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name="ComGroup" id="ComGroup" verify="���������|notnull&CODE:comgroup"  verifyorder="1"  ondblclick="showCodeList('comgroup',[this,ComGroupName],[0,1],null,null,null,1);" onclick="showCodeList('comgroup',[this,ComGroupName],[0,1],null,null,null,1);" onkeyup="showCodeListKey('comgroup',[this,ComGroupName],[0,1],null,null,null,1);" ><input class="codename" name="ComGroupName" id="ComGroupName" readonly>  
          </TD> 
      </TR>
 </table></div>
<table>
    <tr class=common>
    <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
    <td class=titleImg>��Ʒ���ۼƻ���Ϣ</td>
    </td>
    </tr>
    </table> 
    <Div  id= "divPayPlan1" style= "display: ''" class="maxbox"> 
 <table  class= common>    
      <TR class= common>
         <TD  class= title5>
            Ͷ����������
          </TD>          
          <TD class= input5>    
              <!--<Input class="coolDatePicker" dateFormat="short"  name=PolApplyDateStart verify="Ͷ����������|NOTNUlL&DATE">-->
              <Input class="coolDatePicker" onClick="laydate({elem: '#PolApplyDateStart'});" verify="Ͷ����������|NOTNUlL&DATE" dateFormat="short" name=PolApplyDateStart id="PolApplyDateStart"><span class="icon"><a onClick="laydate({elem: '#PolApplyDateStart'});"><img src="../common/laydate/skins/default/icon.png" /></a></span><font color=red>*</font>(��:2008-01-01)
          </TD> 
          <TD  class= title5>
            Ͷ������ֹ��
          </TD>          
          <TD class= input5>    
              <!--<Input class="coolDatePicker" dateFormat="short"  name=PolApplyDateEnd >-->
              <Input class="coolDatePicker" onClick="laydate({elem: '#PolApplyDateEnd'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=PolApplyDateEnd id="PolApplyDateEnd"><span class="icon"><a onClick="laydate({elem: '#PolApplyDateEnd'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>(��:2008-01-01)
          </TD>   
      </TR>
       <TR class= common>
         <TD  class= title5>
            ɨ����������
          </TD>          
          <TD class= input5>    
              <!--<Input class="coolDatePicker" dateFormat="short"  name=ScanDateStart verify="ɨ����������|NOTNUlL&DATE">-->
              <Input class="coolDatePicker" onClick="laydate({elem: '#ScanDateStart'});" verify="ɨ����������|NOTNUlL&DATE" dateFormat="short" name=ScanDateStart id="ScanDateStart"><span class="icon"><a onClick="laydate({elem: '#ScanDateStart'});"><img src="../common/laydate/skins/default/icon.png" /></a></span><font color=red>*</font>(��:2008-01-01)
          </TD> 
          <TD  class= title5>
            ɨ����ʼʱ��
          </TD>          
          <TD class= input5>    
              <Input class="wid" class=common  name=ScanTimeStart id=ScanTimeStart verify="ɨ����ʼʱ��|NOTNUlL">
              <font color=red>*</font>(��:22��00��00)
          </TD>   
      </TR>
       <TR class= common>
         <TD  class= title5>
            ɨ������ֹ��
          </TD>          
          <TD class= input5>    
              <!--<Input class="coolDatePicker" dateFormat="short"  name=ScanDateEnd >-->
              <Input class="coolDatePicker" onClick="laydate({elem: '#ScanDateEnd'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=ScanDateEnd id="ScanDateEnd"><span class="icon"><a onClick="laydate({elem: '#ScanDateEnd'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>(��:2008-01-01)
          </TD> 
          <TD  class= title5>
            ɨ���ֹʱ��
          </TD>          
          <TD class= input5>    
              <Input class="wid" class=common  name=ScanTimeEnd id=ScanTimeEnd >(��:22��00��00)
          </TD>   
      </TR>

  </Table>  	</Div>
<!-- <Div id= divCmdButton style="display: ''">           
      <TR class = common>    
          <TD  class= common>
          <INPUT VALUE="��ѯ������Ϣ" class= cssButton TYPE=button onclick="queryRiskConfig();">  
	  </TD>
	  <TD  class= common>
          <INPUT VALUE="����������Ϣ" class= cssButton TYPE=button onclick="addData();">  
	  </TD>
      </TR>  
  </div>-->
<a href="javascript:void(0);" class="button" onClick="queryRiskConfig();">��ѯ������Ϣ</a>
<a href="javascript:void(0);" class="button" onClick="addData();">����������Ϣ</a>
<table>
    <tr class=common>
    <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divNoAgentGrid);"></td>
    <td class=titleImg>�ѱ���������Ϣ</td>
    </td>
    </tr>
    </table>


     

</Div>
<Div  id= "divNoAgentGrid" style= "display: ''">
      	<table  >
       		<tr  >
      	  		<td text-align: center colSpan=1>
  					<span id="spanProductSaleConfigGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	<!--<table>
    		<tr>
    			<td>
			      <INPUT  VALUE="��  ҳ" class= cssButton TYPE=button onclick="turnPage1.firstPage();"> 
			    </td>
			    <td>  
			      <INPUT  VALUE="��һҳ" class= cssButton TYPE=button onclick="turnPage1.previousPage();"> 					
			    </td>
			    <td> 		 
			      <INPUT  VALUE="��һҳ" class= cssButton TYPE=button onclick="turnPage1.nextPage();"> 
			    </td>
			    <td> 		 
			      <INPUT  VALUE="β  ҳ" class= cssButton TYPE=button onclick="turnPage1.lastPage();"> 						
			    </td>  			
  			</tr>
  		</table>	-->						
</div>
<table>
    <tr class=common>
    <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAgentGrid);"></td>
    <td class=titleImg>�����켣��Ϣ</td>
    </td>
    </tr>
</table>

        
<Div  id= "divAgentGrid" style= "display: ''">
      	<table  >
       		<tr  >
      	  		<td colSpan=1>
  					<span id="spanProductSaleTraceGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	<table align="center">
    		<tr>
    			<td>
			      <INPUT  VALUE="��  ҳ" TYPE=button class= cssButton90 onclick="turnPage.firstPage();"> 
			    </td>
			    <td>  
			      <INPUT  VALUE="��һҳ" TYPE=button class= cssButton91 onclick="turnPage.previousPage();"> 					
			    </td>
			    <td> 		 
			      <INPUT  VALUE="��һҳ" TYPE=button class= cssButton92 onclick="turnPage.nextPage();"> 
			    </td>
			    <td> 		 
			      <INPUT  VALUE="β  ҳ" TYPE=button class= cssButton93 onclick="turnPage.lastPage();"> 						
			    </td>  			
  			</tr>
  		</table>		</Div>					
<!--  	</div> <Div id= divCmdButton style="display: ''">           
      <TR class = common>    
          <TD  class= common>
          <INPUT VALUE="��ѯ�����켣" class= cssButton TYPE=button onclick="queryTrace();">  
	  			</TD>
      </TR>  
  </div>-->
  <a href="javascript:void(0);" class="button" onClick="queryTrace();">��ѯ�����켣</a>
  <!-- ȷ�϶Ի��� ��ʼ��Ϊ0 ��ȷ�Ͽ��ȷ���޸�Ϊ1 ������Ϻ����³�ʼ��Ϊ0 -->
  <INPUT VALUE="0" TYPE=hidden name=myconfirm >
  
    <!-- ��ǰ���� -->
  <INPUT VALUE=<%=CurrentDate%> TYPE=hidden name=CurrentDay>
  <!-- ��ǰ�·� -->
  <input type=hidden id="hideaction" name="hideaction">  
  <input type=hidden id="hideDelManageCom" name="hideDelManageCom"> 
  <input type=hidden id="hideDelIndexCalNo" name="hideDelIndexCalNo">   
   
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
