<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
<title>�ݽ�����Ϣ��ѯ</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>  
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT> 
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT>   
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <script src="./TempFeeQuery.js"></script> 
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>   
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <%@include file="TempFeeQueryInit.jsp"%>


</head>
<body  onload="initForm();">
<!--��¼������-->
<form name=fm id="fm" action="./TempFeeQueryResult.jsp" target=fraSubmit method=post>
    <table>
    	<tr>
    		<td class="common">
    		     <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" onMouseDown= "showPage(this,maxbox);">
    		</td>
    		 <td class= titleImg>
        		 �������ѯ������
       		 </td> 
    	</tr>
    </table>
    <div class="maxbox1" id="maxbox">
    <table  class= common align=center>
      	<TR  class= common>
      	  <TD  class= title>
            �������
          </TD>          
          <TD  class= input>
				<Input class=codeno name=ManageCom id="ManageCom" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center right;" onMouseDown="return showCodeList('station',[this,ManageComName],[0,1]);" onDblClick="return showCodeList('station',[this,ManageComName],[0,1]);" onKeyUp="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName readonly=true>
		  </TD>        		
          <TD  class= title>
          �ݽ���״̬
          </TD>
          <TD  class= input>
            <Input class= code name=TempFeeStatus id="TempFeeStatus" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center right;" verify="��������|NOTNULL" CodeData="0|^0|δ����^1|δ����^2|�Ѻ���^3|���˷�^4|�±�δ����^5|����δ����^6|ȫ��" onMouseDown="showCodeListEx('TempFeeStatus',[this],[0,1,2,3,4,5,6]);" ondblClick="showCodeListEx('TempFeeStatus',[this],[0,1,2,3,4,5,6]);" onKeyUp="showCodeListKeyEx('TempFeeStatus',[this],[0,1,2,3,4,5,6]);" >
          </TD> 
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
            <Input class= code name=RiskCode id="RiskCode"  style="background: url(../common/images/guanliyuan-bg.png) no-repeat center right;" onMouseDown="return showCodeListKey('RiskCode',[this]);"  onDblClick="return showCodeList('RiskCode',[this]);" onKeyUp="return showCodeListKey('RiskCode',[this]);">
          </TD>         
       </TR>                     
      	<TR  class= common>
          <TD  class= title>
          ��ʼ����
          </TD>
          <TD  class= input>
            <Input class="coolDatePicker" dateFormat="short" name=StartDate id="StartDate" onClick="laydate({elem:'#StartDate'});"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>

          </TD>       	
          <TD  class= title>
          ��������
          </TD>
          <TD  class= input>
            <Input class="coolDatePicker" dateFormat="short" name=EndDate id="EndDate" onClick="laydate({elem:'#EndDate'});"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>

          </TD>
          <TD  class= title>
          ����Ա
          </TD>
          <TD  class= input>
            <Input class="common wid" name=Operator id="Operator" >
          </TD>   
       </TR>
       <!--TR  class= common>      	
          <TD  class= title>
          ��С���:
          </TD>
          <TD  class= input>
            <Input class=common name=MinMoney >
          </TD> 
          <TD  class= title>
          �����:
          </TD>
          <TD class= input>
            <Input class=common name=MaxMoney >
          </TD>               	
         <TD  class= title>
          ҵ��Ա:
          </TD>
          <TD  class= input>
            <Input class=common name=AgentCode >
          </TD>       	
       </TR-->
       
       <TR  class= common>
          <TD  class= title>
          ���վݺ�:
          </TD>
          <TD  class= input>
            <Input class="common wid" name=TempFeeNo id="TempFeeNo" >
          </TD>  
          <TD class = title>
          Ͷ����ӡˢ��/������
          </TD>
          <TD class=input>
            <Input class="common wid" name=PrtNo id="PrtNo">
          </TD>
          <TD  class= title>
          ҵ��Ա
          </TD>
          <TD  class= input>
            <Input class="common wid" name=AgentCode id="AgentCode" >
          </TD> 
        </TR>
      </table>
      </div>
      <div id=divChequeNo style='display:none'>
      <table class=common align=center>
        <TR class=common>
          <TD class=title>
            ֧Ʊ����:
          </TD>
          <TD class=input>
            <Input class="common wid" name=ChequeNo id="ChequeNo">
          </TD>
          
        </TR>
      </table>
      </div>
            <INPUT VALUE="��  ѯ" Class=cssButton TYPE=button onMouseDown="submitForm();" name="QUERYName">  
            <!-- <INPUT CLASS=cssButton VALUE="��  ӡ" TYPE=button onMouseDown="easyprint();" name="PrintName"> -->
   </div>
   <BR><BR>
     <!--INPUT VALUE="����" Class=cssButton TYPE=button onMouseDown="returnParent();"-->   
    
   
    <Table>
    	<TR>
        	<TD class=common>
	           <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" onMouseDown= "showPage(this,divTempFee1);">
    		</TD>
    		<TD class= titleImg>
    			 �ݽ�����Ϣ
    		</TD>
    	</TR>
    </Table>    	
 <Div  id= "divTempFee1" style= "display: ''">
   <Table  class= common>
       <TR  class= common>
        <TD text-align: left colSpan=1>
            <span id="spanTempQueryGrid" ></span> 
  	</TD>
      </TR>
    </Table>
      
	<center>			
      <INPUT VALUE="��ҳ" class="cssButton90" TYPE=button onMouseDown="getFirstPage();"> 
      <INPUT VALUE="��һҳ" class="cssButton91" TYPE=button onMouseDown="getPreviousPage();"> 					
      <INPUT VALUE="��һҳ" class="cssButton92" TYPE=button onMouseDown="getNextPage();"> 
      <INPUT VALUE="βҳ" class="cssButton93" TYPE=button onMouseDown="getLastPage();"> 					
    </center>
 <input type=hidden id="fmAction" name="fmAction">
 <br><br><br><br>
 </Div>					
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>					
</Form>
</body>
</html>

