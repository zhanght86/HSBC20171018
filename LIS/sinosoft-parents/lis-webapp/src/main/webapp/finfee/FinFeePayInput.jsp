<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
   GlobalInput tG = new GlobalInput();
   tG=(GlobalInput)session.getValue("GI"); //���ҳ��ؼ��ĳ�ʼ����
   loggerDebug("FinFeePayInput","�������-----"+tG.ComCode);
%>   

<script>
  var comCode = "<%=tG.ComCode%>";
</script>
<html> 
<%
//�������ƣ�FinFeePayInput.jsp
//�����ܣ����񸶷ѱ������
//�������ڣ�2002-07-12 
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>


<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT> 
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT> 
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>    
  <SCRIPT src="FinFeePayInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="FinFeePayInit.jsp"%>
</head>
<body  onload="initForm();" >
<Form action="./FinFeePayQuery.jsp" method=post name=fm id="fm" target="fraSubmit">

   <table>
    	<tr>
    		<td class="common">
    		     <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,maxbox);">
    		</td>
    		 <td class= titleImg>
        		 �������ѯ������
       		 </td> 
    	</tr>
    </table>
    <div class="maxbox1" id="maxbox">
  <Table class= common>                             
    <TR>  
      <TD  class= title5>
         ʵ������
      </TD>  
      <TD  class= input5>
        <Input class="common wid" name=ActuGetNo id="ActuGetNo" >
      </TD>
      <TD  class= title5></TD>
      <TD  class= input5>    
      </TD>
      <!--TD  class= title5>
        ��ȫ�����/�ⰸ��/Ͷ����ӡˢ��
      </TD>           
      <TD  class= input5>
        <Input class=common name=OtherNo>
      </TD-->              
    </TR> 
         
        <!--TR>
        	<TD class=title nowrap=false >
          	<!--<input type="hidden" name="PrtNo" value="PrtNo">-->
          	<!--input type="checkbox" name="chkPrtNo" onclick="inputCertify(this)">
          	��ظ���
          </TD>
        </TR-->
          
          <!--<td><input type="button" name="btnPrtNo" class="cssButton" value="��  ѯ" onclick="queryPrtNo()" style="display:none"></td>--> 
        
         <!--TR>
          <TD  class= title5>
            ���ѷ�ʽ
          </TD>
          <TD  class= input5>
            <Input class="code" name=PayMode1  ondblclick="return showCodeList('PayMode',[this]);" onkeyup="return showCodeListKey('PayMode',[this]);">
          </TD>            
          <TD  class= title5>
            �����˱���
          </TD>
          <TD  class= input5>
            <Input class="common" name=AgentCode >
          </TD>          
         </TR>
           <td class= title5>
            �������
           </td>  
				   <TD class=input>
				 	    <Input class=codeno name=ManageCom ondblclick="return showCodeList('managecom1',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('managecom1',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName readonly=true elementtype=nacessary>
				   </TD>
           <td class= title5>
            �������
           </td>  
          <TD  class= input5>
            <Input class="coolDatePicker" name=MakeDate dateFormat="short" name=StartDate>
            <!--Input class="common" name=MakeDate >
          </TD>
         <TR>
        <TR>
        </TR>
          <TD  class= title5>
            ��������
          </TD>          
          <TD  class= input5>
            <Input class="codeno" name=OtherNoType CodeData="0|^1|������ȡ^2|��ȫ�շ�^3|�ݽ����˷�^4|�����˷�^5|����ҵ��^6|�ֺ����" ondblclick="return showCodeListEx('Type',[this,OtherNoTypeName],[0,1]);" onkeyup="return showCodeListKeyEx('Type',[this,OtherNoTypeName],[0,1]);" onchange="showBankAccNo();"><input class=codename name=OtherNoTypeName readonly=true></TD>           
          </TD>        
        </TR--> 
                                  
  </Table>
  <Input class=cssButton type=button value="��  ѯ" name=aquery id="aquery" onClick="queryLJAGet();">
  </div>
     <!--Div id="PolicyComInPut" style="display:none">
     		<Table class= common >
	        <TR>
	        	<TD  class= title5>
	          	�������
	          </TD>  
	          <TD  class= input5-->
	            <!--<Input class=common name=ManageCom-->
	            <!--Input class=common name=ManageCom readonly=true>
	          </TD> 
	          <TD  class= title5>
	          	���ѻ���
	          </TD>  
	          <TD  class= input5>
	            <Input class=common name=PolicyCom readonly=true>
	          </TD>
	          
	        </TR>
	   		</Table>
     </Div-->
  
          <!--TD  class= input5>
            <Input class=cssButton type=button value="��ѯ" onclick="easyQueryClick();">
          </TD-->
  <Table>
    <TR>
     	<TD class=common>
	      <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFinFee1);">
    	</TD>
   		<TD class= titleImg>
    		 ʵ���ܱ���Ϣ
  		</TD>
   	</TR>
  </Table>    	  
  <Div  id= "divFinFee1" style= "display: ''" align=center>
    <Table  class= common>
      <TR  class= common>
        <TD text-align: left colSpan=1>
         <span id="spanQueryLJAGetGrid" ></span> 
  	    </TD>
      </TR>
   </Table>					
      <INPUT VALUE="��ҳ" class="cssButton90" TYPE=button onClick="getFirstPage();"> 
      <INPUT VALUE="��һҳ" class="cssButton91" TYPE=button onClick="getPreviousPage();"> 					
      <INPUT VALUE="��һҳ" class="cssButton92" TYPE=button onClick="getNextPage();"> 
      <INPUT VALUE="βҳ" class="cssButton93" TYPE=button onClick="getLastPage();"> 					
 </Div>	
    <TD  class= input5>
      <input type=hidden name=ManageCom > 
    </TD>
</Form> 

<Form action="./FinFeePaySave.jsp" method=post name=fmSave id="fmSave" target="fraSubmit">
     
     <Table class= common>                 
       <TR  class= title5>
       <TD class=common>
	      <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,maxbox);">
    	</TD>
          <TD class= titleImg>
            ������д������ϸ��Ϣ
          </TD>      
      </TR> 
      </Table>
      <div class="maxbox" id="maxbox">
      <table class="common"> 
      <TR  class= common> 
          <TD  class= title5>
            ��������
          </TD>
          <TD  class= input5>
            <Input class="common wid" name=PolNo id="PolNo" verify="��������|notnull" >
          </TD>                       
          <TD  class= title5>
            ���ѷ�ʽ
          </TD>          
          <TD  class= input5>
          	<!--zy 2009-04-09 20:55 ���и��ѷ�ʽ�Ŀ���-->
            <!--Input class="codeno" name=PayMode verify="���ѷ�ʽ|notnull" CodeData="0|^1|�ֽ�^2|�ֽ�֧Ʊ^3|ת��֧Ʊ^6|POS����^7|��������" ondblclick="return showCodeListEx('Mode',[this,PayModeName],[0,1]);" onkeyup="return showCodeListKeyEx('Mode',[this,PayModeName],[0,1]);" onchange="showBankAccNo();"><input class=codename name=PayModeName readonly=true></TD-->
            <Input class="codeno" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " name=PayMode id="PayMode" verify="���ѷ�ʽ|notnull" CodeData="0|^1|�ֽ�^2|�ֽ�֧Ʊ^3|ת��֧Ʊ^9|��������" onMouseDown="return showCodeListEx('Mode',[this,PayModeName],[0,1]);" onDblClick="return showCodeListEx('Mode',[this,PayModeName],[0,1]);" onKeyUp="return showCodeListKeyEx('Mode',[this,PayModeName],[0,1]);" onChange="showBankAccNo();" readonly ><input class=codename name=PayModeName readonly=true>                      
          </TD>           
      </TR>
<tr class= common>
    					<td class=title5>����</td>
    					<td class=input5><Input class=codeno name=Currency id="Currency" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onMouseDown="return showCodeList('currency',[this,CurrencyName],[0,1]);" onDblClick="return showCodeList('currency',[this,CurrencyName],[0,1]);" onKeyUp="return showCodeListKey('currency',[this,CurrencyName],[0,1]);"><input class=codename name=CurrencyName readonly=true></td>
    					<td></td>
    			<td></td>
    			</tr>
      <TR  class= common>                    
          <TD  class= title5>
            �������
          </TD>           
          <TD  class= input5>
            <Input class="common wid" name=GetMoney id="GetMoney" verify="�������|notnull&num" readonly >
          </TD>          
          <TD  class= title5>
            ����������
          </TD>
          <TD  class= input5>
            <Input class="coolDatePicker"   name=EnterAccDate verify="����������|notnull&date" id="EnterAccDate" onClick="laydate({elem:'#EnterAccDate'});"><span class="icon"><a onClick="laydate({elem: '#EnterAccDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>  
       </TR>               
       <TR  class= common>
           <TD  class= title5>
            ��ȡ��
           </TD>  
          <TD  class= input5>
            <Input class="common wid" verify="��ȡ��|notnull" name=SDrawer id="SDrawer" >
          </TD>                                   
          <TD  class= title5>
            ��ȡ�����֤��
          </TD>
          <TD  class= input5>
            <Input class="common wid" verify="��ȡ�����֤��|notnull" name=SDrawerID id="SDrawerID" >
          </TD>
        </TR>                           
      <TR  class= common>
          <!--TD  class= title5>
            ʵ����ȡ��
          </TD>  
          <TD  class= input5>
            <Input class= common  name=Drawer >
          </TD--> 
          <TD  class= title5>
            �������
          </TD>  
          <TD  class= input5>
            <Input class="common wid" name=FManageCom id="FManageCom"  value="<%=tG.ComCode%>" readonly >
          </TD>  
        </TR>
        <!--TR  class= common>
           <td class=title>
			       	ʵ����ȡ��֤������
			     	</td>
			     	<td class=input>
							<Input class=codeno name=DrawerIDType ondblclick="return showCodeList('idtype',[this,IDTypeName],[0,1]);" onkeyup="return showCodeListKey('idtype',[this,IDTypeName],[0,1]);"><input class=codename name=IDTypeName readonly=true>							        			
			     	</td>                            
          <TD  class= title5>
            ʵ����ȡ��֤��֤��
          </TD>
          <TD  class= input5>
            <Input class= common  name=DrawerID >
          </TD>                         
        </TR-->      
                                                     	                  	 
     </TABLE> 
   <Div id='divBankAccNo' style="display:none">
      <table class=common>       
    		<tr class= common>
    					<td class=title5>
    						��������
    					</td>
    					<td class=input5>
    						<Input class=codeno name=BankCode style="background: url(../common/images/select--bg_03.png) no-repeat center right; " id="BankCode" onClick="return showCodeList('FINAbank',[this,BankName],[0,1],null,mSql,'1');" onDblClick="return showCodeList('FINAbank',[this,BankName],[0,1],null,mSql,'1');"  onkeyup="return showCodeListKey('FINAbank',[this,BankName],[0,1],null,mSql,'1');"><input class=codename name=BankName readonly=true> 
    					</td>
             <TD class= title5>
                ���л���
             </TD>
             <TD class= input5>
    				    <Input class="common wid" name=BankAccName id="BankAccName">
            
             </TD>
    		</tr>             
       <TR class=common>                                  
         <TD  class= title5>
            �����˺�
         </TD>
          <TD  class= input5>
     				    <!--Input class=code name=BankAccNo ondblclick="return showCodeList('AccNo2',[this],null,null,[fmSave.BankCode.value],['bankcode']);" onkeyup="return showCodeListKey('AccNo2',[this],null,null,[fm.BankCode.value],['bankcode']);" readonly=true-->
     				    <Input class="common wid" name=BankAccNo id="BankAccNo" onBlur="confirmSecondInput(this,'onblur');"><font size=1 color='#ff0000'><b>*</b></font>
          </TD>          
          <TD class=title5>
          </TD>
          <TD class=input5>
          </TD>     
        </TR> 
        </table> 
      </Div>   
      <Div id='divChequeNo' style="display:none">
       <table class=common>
       
    		<tr class= common>
    					<td class=title5>
    						��������
    					</td>
    					<td class=input5>
    						<Input class=codeno name=BankCode2 id="BankCode2" style="background: url(../common/images/select--bg_03.png) no-repeat center right; "
 onMouseDown="return showCodeList('FINAbank',[this,BankName2],[0,1],null,mSql,'1');" onDblClick="return showCodeList('FINAbank',[this,BankName2],[0,1],null,mSql,'1');"  onkeyup="return showCodeListKey('FINAbank',[this,BankName2],[0,1],null,mSql,'1');"><input class=codename name=BankName2 readonly=true> 
    					</td>
             <TD class= title5>
                Ʊ�ݺ���
             </TD>
             <TD class= input5>
    				    <Input class="coolConfirmBox wid" name=ChequeNo id="ChequeNo">
            
             </TD>
    		</tr>        
        </table> 
      </Div>     
      <Div id='divNetBankAccNo' style="display:none">
       <table class=common>
       
    		<tr class= common>
    					<td class=title5>
    						��������
    					</td>
    					<td class=input5>
    						<Input class=codeno name=BankCode9 id="BankCode9" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onMouseDown="return showCodeList('FINAbank',[this,BankName9],[0,1],null,mSql,'1');"
 onDblClick="return showCodeList('FINAbank',[this,BankName9],[0,1],null,mSql,'1');"  onkeyup="return showCodeListKey('FINAbank',[this,BankName9],[0,1],null,mSql,'1');"><input class=codename name=BankName9 readonly=true> 
    					</td>
            </TR>
            <TR class=common>
            <TD class= title5>
                �����˺�
             </TD>
             <TD class= input5>
    				    <Input class="coolConfirmBox wid" name=BankAccNo9 id="BankAccNo9" onBlur="checkBank(BankCode9,BankAccNo9);">           
             </TD>
             <TD class= title5>
                ���л���
             </TD>
             <TD class= input5>
    				    <Input class="common wid" name=BankAccName9 id="BankAccName9">           
             </TD>
    		</tr>        
        </table> 
      </Div>
      </div>  
     <!--Div id='divInSureBankAccNo' style="display:none">
       <table class=common>
       <TR class=common>                                  
         <TD  class= title5>
            �ͻ�����
         </TD>
          <TD  class= input5>
    				<Input class=codeno name=InSureBankCode ondblclick="return showCodeList('HeadOffice',[this,InSureBankName],[0,1],null,'3','Branchtype',1);" onkeyup="return showCodeList('HeadOffice',[this,InSureBankName],[0,1],null,'3','Branchtype','1');"><input class=codename name=InSureBankName readonly=true> 
          	
          </TD>          
          <TD class=title>
            �ͻ��˺�
          </TD>
          <TD class= input5>
    				    <Input class=common name=InSureAccNo onblur="confirmSecondInput(this,'onblur');">   
          </TD>       
        </TR> 
        <TR>
          <TD class=title>
            �ͻ��˻���
          </TD>
          <TD class= input5>
    				    <Input class=common name=InSureAccName>   
          </TD>            
        </TR>
        </table> 
      </Div> 

     <Div id='divPayByBank' style="display:none">
       <table class=common>
       <TR class=common>                                  
         <TD  class= title5>
            ��������
         </TD>
          <TD  class= input5>
    				<Input class=codeno name=BankCode4 ondblclick="return showCodeList('bank',[this,BankName4],[0,1]);" onkeyup="return showCodeList('bank',[this,BankName4],[0,1]);"><input class=codename name=BankName4 readonly=true> 
          </TD>          
          <TD class=title>
            �ͻ��˺�
          </TD>
          <TD class= input5>
    				 <Input class=common name=AccNo4 onblur="confirmSecondInput(this,'onblur');">   
          </TD>       
        </TR> 
        <TR>
          <TD class=title>
            �ͻ��˻���
          </TD>
          <TD class= input5>
    				    <Input class=common name=AccName4>   
          </TD>            
        </TR>
        </table> 
      </Div>
      
     <Div style="display:none">
       <TR  class= common>
          <TD  class= title5>
            �����˱���
          </TD>
          <TD  class= input5>
             <Input class="code" name=AgentCode >
          </TD>           
          <TD  class= title5>
            ���������
          </TD>
          <TD  class= input5>
            <Input class="readonly" readonly tabindex=-1 name=AgentGroup  >
          </TD>
      </TR>      
        <TR  class= common>          
          <TD  class= title5>
            ����Ա
          </TD> 
          <TD  class= input5>
            <Input class="readonly"  readonly tabindex=-1 name=Operator  verify="����Ա|notnull">
          </TD>                             
          <TD  class= title5>
            ����ʱ��
          </TD>
          <TD  class= input5>
            <Input class="readonly" readonly tabindex=-1 name=ModifyDate   verify="����ʱ��|notnull">
          </TD>
       </TR> 
     </Div-->
          <TD  class= input5>
            <input type=hidden name=ActuGetNo > 
          </TD>  
          <TD  class= input5>
           <Input class=cssButton type=button value="����ȷ��" name=btnSave id="btnSave" onClick="submitForm();">
          </TD>  
       <br><br><br><br>       
</Form>    

  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>

<Form action="./FinFeePayPrint.jsp" method=post name=fmprint id="fmprint" target="fraSubmit">
     <!--Table class= common>                 
       <TR  class= common>
          <TD  class= input5>
            <Input class=cssButton type=button value="��ӡƾ֤" onclick="Print();">
          </TD>
          <TD  class= input5>
            <Input class= common name=ActuGetNo1 type=hidden>
          </TD>
       
       </TR>
     </Table-->   
     
</Form>

</html>
<script>
var bCom=comCode.trim();
if(bCom.length >4)
{
	bCom=bCom.substring(0,4);
}
var mSql="1 and comcode like #"+bCom+"%#" ;	
</script>
 
