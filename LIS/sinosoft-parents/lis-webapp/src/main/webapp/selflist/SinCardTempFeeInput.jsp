

<%
//�������ƣ�SinCardTempFeeInput.jsp
//�����ܣ����������շѵ�����
//�������ڣ�2008-3-7 
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>

<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>   

<SCRIPT src="SinCardTempFeeInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="SinCardTempFeeInit.jsp"%>
</head>
<body onload="initForm();">
<Form action="./TempFeeQuery.jsp" method=post name=fm id="fm" target="fraSubmit">

<!--���һ��������Ϣ --> 
     <script>
	   var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
     </script>    
     <Table>
    	<tr>
         <td class=common>
         <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,Frame1);">    	 
          <td class= titleImg>
           ������Ϣ
           <a href="javascript:void(0)" class=button onclick="queryTempfee();" style="font-weight:normal;">��ѯ�ݽ���</a>
            <!-- <input type =button class=cssButton value="��ѯ�ݽ���" onclick="queryTempfee();">  	 -->
          </td>
            	  
    	 </td>
    	</tr>
    </Table>
    <div class="maxbox1">  
      <Div  id= "Frame1" style= "display: ''">       
     <Table class= common border=0>
       <TR  class= common> 
          <TD  class= title5>
            ҵ��Ա
          </TD>
         <td class="input5" width="25%">
         <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class=code name=AgentCode id="AgentCode" onclick="queryAgent()" ondblclick="queryAgent()" onblur="GetManageCom();">
         </TD> 
            
          <TD class=title5>
          	ҵ��Ա����
          </TD>          
          <TD  class= input5>
            <Input class="readonly wid"  tabindex=-1 name=AgentName id="AgentName" readonly=true >
          </TD>
        </TR>         
       <TR  class= common>          
         <TD class= title5> 
            ��������
          </TD>
          <TD class= input5 >
            <Input class="coolDatePicker" onClick="laydate({elem: '#PayDate'});" verify="��������|DATE&NOTNULL" dateFormat="short" name=PayDate id="PayDate"><span class="icon"><a onClick="laydate({elem: '#PayDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>                                        
          <TD  class= title5>
             �շѻ���
          </TD>          
          <TD  class= input5>
							<Input class="readonly wid" name=ManageCom id="ManageCom" readonly=true >				 
				  </TD>
        </TR>         
       <TR  class= common>             
          <TD  class= title5>
             �������
          </TD>          
          <TD  class= input5>
							<Input class="readonly wid" name=PolicyCom id="PolicyCom" readonly=true >				 
				  </TD>         
          <TD  class= title5>
            ���������
          </TD>
          <TD  class= input5>
            <Input class="readonly wid" readonly tabindex=-1 name=AgentGroup id="AgentGroup" >
          </TD>
          
      </TR>           	                 	 
     </TABLE> 
 </Div>
 </div>
<!--���һ  --> <!--��ܶ���ѡ���ݽ������Ͳ����� -->
<Table>
	<tr>
		<td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,Frame2);">
		<td class=titleImg>¼���ݽ���</td>
		</td>
	</tr>
</Table>
<Div id="Frame2" style="display: ''"><!-- �µ����� -->
  <Div id="TempFeeType1" style="display: ''">
    <div class="maxbox1">
    <table class=common>
	<TR class=common>
		<TD class=title5>������ʼ����</TD>
		<TD class=input5><Input class="common wid" name=StartNo id="StartNo"></TD>
		<TD class=title5>������ֹ����</TD>
		<TD class=input5><Input class="common wid" name=EndNo id="EndNo"></TD>
	</TR>
		<TR class=common>
		<TD class=title5>��֤����</TD>
		<TD class=input5>
			<Input style="background:url(../common/images/select--bg_03.png)     no-repeat right center" class=codeno name=CertifyCode id="CertifyCode" onclick="showCodeList('CardCode',[this,CertifyCodeName],[0,1]);" ondblclick="showCodeList('CardCode',[this,CertifyCodeName],[0,1]);" onkeyup="return showCodeListKey('CardCode',[this,CertifyCodeName],[0,1]);" ><Input class=codename name=CertifyCodeName id="CertifyCodeName" readonly=true>
		</TD>
	</TR>
    </table>
    </div>
  </Div>
  <div>
    <a href="javascript:void(0)" class=button name="distill" id="distill" onclick="confirm();">ȷ  ��</a>
  </div>
<br>
<!-- <Input type=button class=cssButton value="ȷ  ��" name="distill" id="distill"	onclick="confirm();"> -->

<Div id="divTempFeeInput" style="display: ''"><!--¼����ݽ��Ѳ��� -->
<Table class=common>
	<tr>
		<td style=" text-align: left" colSpan=1>
			<span id="spanTempGrid"> </span>
		</td>
	</tr>
</Table>

 <br>
<!--¼����ݽ��ѷ��ಿ�� -->
<Table class=common>
	<tr>
		<td style=" text-align: left" colSpan=1>
			<span id="spanTempClassGrid">
		</span></td>
	</tr>
</Table>
<div class="maxbox1">
   <div id= "divTempFeeClassInput" style="display:'none'">
		<table  class= common align=left>
      <tr class= common>
       		<td class=title5 style="width:11%;">
       			���ѷ�ʽ
       			</td>
       		<td class="input5">
							<Input style="background:url(../common/images/select--bg_03.png)     no-repeat right center" class=codeno name=PayMode id="PayMode" onclick="return showCodeList('chargepaymode',[this,PayModeName],[0,1]);" ondblclick="return showCodeList('chargepaymode',[this,PayModeName],[0,1]);"  onkeyup="return showCodeListKey('chargepaymode',[this,PayModeName],[0,1]);"><input class=codename name=PayModeName id="PayModeName" readonly=true>							        			
       		</td>
       		<td class="input5">
            <a href="javascript:void(0)" class=button onclick="addMul();">��  ��</a>
            <a href="javascript:void(0)" class=button onclick="ModMul();">��  ��</a>
       			<!-- <input type =button class=cssButton value="��  ��" onclick="addMul();">  
       			<input type =button class=cssButton value="��  ��" onclick="ModMul();"> -->
       			</td>
            <td class="input5"></td>
       </tr>
    </Table> 
   </div>


  <br><br>
    <div id="divPayMode1" style="display:none" >
    	<Table  class= common>
    		<tr class= common>
    			<td class=title5>
    					���ѽ��
    			</td>
    			<td class="input5">
    				<Input class="common wid" name="PayFee1" id="PayFee1" >
    			</td>
    			<td class=title5></td>
    			<td class="input5"></td>
    		</tr>
    	</table>
    </div>
    <div id="divPayMode2" style="display:none" >
    	<Table  class= common>
    			<tr class= common>
    					<td class=title5>
    							���ѽ��
    					</td>
    					<td class="input5">
    						<Input class="common wid" name="PayFee2" id="PayFee2" >
    					</td>
    					<td class=title5>
    							Ʊ�ݺ���
    					</td>
    					<td class="input5">
    						<Input class="common wid" name="ChequeNo2" id="ChequeNo2" onblur="confirmSecondInput(this,'onblur');">
    					</td>
            </tr>
          <tr class= common>
    					<td class=title5>
    							֧Ʊ���� 
    					</td>
    					<td class="input5">
                <Input class="coolDatePicker" onClick="laydate({elem: '#ChequeDate2'});" verify="֧Ʊ����|DATE" dateFormat="short" name="ChequeDate2" id="ChequeDate2"><span class="icon"><a onClick="laydate({elem: '#ChequeDate2'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
    					</td>
    					<td class=title5>
    							��������
    					</td>
    					<td class="input5">
    						<Input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" class=codeno name="BankCode2" id="BankCode2" verify="��������|code:FINAbank"   onclick="return showCodeList('FINAbank',[this,BankCode2Name],[0,1]);"  ondblclick="return showCodeList('FINAbank',[this,BankCode2Name],[0,1]);" onkeyup="return showCodeListKey('FINAbank',[this,BankCode2Name],[0,1]);"><input class=codename name="BankCode2Name" id="BankCode2Name" readonly=true>
    					</td>
            </tr>
          <tr class= common>
    					<td class=title5>
    							�����˺�
    					</td>
    					<td class="input5">
    						<Input class="common wid" name="BankAccNo2" id="BankAccNo2" onblur="checkBank(BankCode2,BankAccNo2);">
    					</td>
    					<td class=title5>
    							����
    					</td>
    					<td class="input5">
    						<Input class="common wid" name="AccName2" id="AccName2">
    					</td>
    			</tr>    		   			  			
    	</table>
    </div>
    <div id="divPayMode3" style="display:none" >
    	<Table  class= common>
    			<tr class= common>
    					<td class=title5>
    							���ѽ��
    					</td>
    					<td class="input5">
    						<Input class="common wid" name="PayFee3" id="PayFee3">
    					</td>
    					<td class=title5>
    							Ʊ�ݺ���
    					</td>
    					<td class="input5">
    						<Input class="common wid" name="ChequeNo3" id="ChequeNo3" onblur="confirmSecondInput(this,'onblur');">
    					</td>
            </tr>
          <tr class= common>
    					<td class=title5>
    							֧Ʊ����
    					</td>
    					<td class="input5">
                <Input class="coolDatePicker" onClick="laydate({elem: '#ChequeDate3'});" verify="֧Ʊ����|DATE" dateFormat="short" name="ChequeDate3" id="ChequeDate3"><span class="icon"><a onClick="laydate({elem: '#ChequeDate3'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
    					</td>
    					<td class=title5>
    							��������
    					</td>
    					<td class="input5">
								<Input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" name="BankCode3" id="BankCode3" class='codeno' onclick="return showCodeList('FINAbank',[this,BankCode3Name],[0,1]);" ondblclick="return showCodeList('FINAbank',[this,BankCode3Name],[0,1]);"  onkeyup="return showCodeListKey('FINAbank',[this,BankCode3Name],[0,1]);"><input name="BankCode3Name" id="BankCode3Name" class='codename' readonly=true elementtype=nacessary>
    					</td>
            </tr>
          <tr class= common>
    				 <td class=title5>
    							�����˺�
    					</td>
    					<td class="input5">
    						<Input class="common wid" name="BankAccNo3" id="BankAccNo3"  onblur="checkBank(BankCode3,BankAccNo3);">
    					</td>
    					<td class=title5>
    							��  ��
    					</td>
    					<td class="input5">
    						<Input class="common wid" name="AccName3" id="AccName3">
    					</td>
    			</tr>    				  			
    	</table>
    </div>

    <div id="divPayMode4" style="display:none" >
    	<Table  class= common>
    			<tr class= common>
    					<td class=title5>
    							���ѽ��
    					</td>
    					<td class="input5">
    						<Input class="common wid" name="PayFee4" id="PayFee4">
    					</td>
					    <td class=title5>
    							��������
    					</td>
    					<td class="input5">
								<Input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" class=codeno name="BankCode4" id="BankCode4" onclick="return showCodeList('bank',[this,BankCode4Name],[0,1]);" ondblclick="return showCodeList('bank',[this,BankCode4Name],[0,1]);" onkeyup="return showCodeListKey('bank',[this,BankCode4Name],[0,1]);"><input class=codename name="BankCode4Name" id="BankCode4Name" readonly=true>
    					</td>
            </tr>
          <tr class= common>
              <!-- <td class=title>
                  �����˺�
              </td>
              <td class="input3">
                <Input class=common name="BankAccNo4" id="BankAccNo4" onblur="checkBank(BankCode4,BankAccNo4);">
              </td> -->
    					<td class=title5>
    							�����˺�
    					</td>
    					<td class="input5">
    						<Input class="common wid" name="BankAccNo4" id="BankAccNo4" onblur="checkBank(BankCode4,BankAccNo4);">
    					</td>
    					<td class=title5>
    							��  ��
    					</td>
    					<td class="input5">
    						<Input class="common wid" name="AccName4" id="AccName4" >
    					</td>
              <!-- <td class=title>
              ֤������
            </td> -->
            <!-- <td class="input3">
              <Input style="background:url(../common/images/select--bg_03.png)     no-repeat right center" class=codeno name=IDType id="IDType" onclick="return showCodeList('idtype',[this,IDTypeName],[0,1]);" ondblclick="return showCodeList('idtype',[this,IDTypeName],[0,1]);" onkeyup="return showCodeListKey('idtype',[this,IDTypeName],[0,1]);"><input class=codename name=IDTypeName id="IDTypeName" readonly=true>                           
            </td>
            <td class=title>
              ֤������
            </td>
            <td class="input3">
              <Input class=common name=IDNo id="IDNo" >                           
            </td> -->
    			</tr>
    			<tr class= common>
			      <td class=title5>
			       	֤������
			     	</td>
			     	<td class="input5">
							<Input style="background:url(../common/images/select--bg_03.png)     no-repeat right center" class=codeno name=IDType id="IDType" onclick="return showCodeList('idtype',[this,IDTypeName],[0,1]);" ondblclick="return showCodeList('idtype',[this,IDTypeName],[0,1]);" onkeyup="return showCodeListKey('idtype',[this,IDTypeName],[0,1]);"><input class=codename name=IDTypeName id="IDTypeName" readonly=true>							        			
			     	</td>
			  		<td class=title5>
			     		֤������
			   		</td>
			  		<td class="input5">
							<Input class="common wid" name=IDNo id="IDNo" >							        			
			   		</td>
        	</tr> 			
    	</table>
    </div>
    <div id="divPayMode5" style="display:none" >
    	<!--Table  class= common>
    			<tr class= common>
    					<td class=title>
    						 ʵ��֪ͨ���
    					</td>
    					<td class=input>
    						<Input class="common wid" name=ChequeNo5 onblur="confirmSecondInput(this,'onblur');">   						 
    					</td>
    					<td class=title>
    					   ��ȫ�����/�ⰸ��/Ͷ����ӡˢ��	
    					</td>
    					<td class=input>
    					  <Input class="common wid" name=OtherNo5 > 
    					</td>
    					<td class=title>
    					<input class=cssButton type="button" value="��  ѯ" style="width:60" onclick="queryLJAGet()"></td>
    			</tr>
    			
    			<tr class= common>
    					<td class=title>
    							�������
    					</td>
    					<td class=input>
    						<Input class="common wid" name=PayFee5 readonly=true>
    					</td>
    					<td class=title>
    							��ȡ��
    					</td>
    					<td class=input>
    						<Input class="common wid" name=Drawer5 readonly=true>
    					</td>
    			</tr>    		
          <Div  id= "divFinFee1" style= "display: ''" align=center>
             <Table  class= common>
               <TR  class= common>
                <TD style=" text-align: left" colSpan=1>
                  <span id="spanQueryLJAGetGrid" ></span> 
  	            </TD>
              </TR>
             </Table>					
            <INPUT CLASS=cssButton VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"> 
            <INPUT CLASS=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
            <INPUT CLASS=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
            <INPUT CLASS=cssButton VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();">
          </Div>	    			  			
    	</table-->
    </div>
    <div id="divPayMode6" style="display:none" >
     	<Table  class= common>
    		<tr class= common>
    			<td class=title5>
    					���ѽ��
    			</td>
    			<td class="input5">
    				<Input class="common wid" name="PayFee6" id="PayFee6">
    			</td>
          <td class=title5>
                  ��������
          </td>
          <td class="input5">
            <Input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" name="BankCode6" id="BankCode6" class='codeno' onclick="return showCodeList('FINAbank',[this,BankCode6Name],[0,1]);"  ondblclick="return showCodeList('FINAbank',[this,BankCode6Name],[0,1]);"  onkeyup="return showCodeListKey('FINAbank',[this,BankCode6Name],[0,1]);"><input name="BankCode6Name" id="BankCode6Name" class='codename' readonly=true elementtype=nacessary>
          </td>
    		</tr>
    			<tr class= common>
    					<td class=title5>
    							�����˺�
    					</td>
    					<td class="input5">
    						<Input class="common wid" name="BankAccNo6" id="BankAccNo6" onblur="checkBank(BankCode6,BankAccNo6);" >
    					</td>
    					<td class=title5>
    							��  ��
    					</td>
    					<td class="input5">
    						<Input class="common wid" name="AccName6" id="AccName6" >
    					</td>
    			</tr> 
    	</table>
    </div>
    <div id="divPayMode0" style="display:none" >
    	<!--Table  class= common>
    		<tr class= common>
    			<td class=title>
    					���ѽ��
    			</td>
    			<td class=input>
    				<Input class=common name=PayFee0>
    			</td>
    			<td></td>
    			<td></td>
    			<td></td>
    			<td></td>
    		</tr>
    	</table-->
    </div>
</div>
    <table class=common>
    <TR class=common>    
     <TD class=common align=right>
      <a href="javascript:void(0)" class=button name=butAdd id="butAdd" onclick="addRecord();">���һ��</a>
      <!-- <input type =button class=cssButton value="���һ��" onclick="addRecord();" name=butAdd id="butAdd">       -->
     </TD>
    </TR> 
    </table>
    <br>
  <hr class="line"></hr>
    <Table class=common>
      <TR>
        <TD  class= title5>
          �ݽ��ѹ�����&nbsp;
        <input width='10px' class=common  name=TempFeeCount id="TempFeeCount" readonly tabindex=-1 >
          ��
       </TD>
       <TD  class= title5>
          �ݽ����ܼ�&nbsp;
        <input width='10px' class=common  name=SumTempFee id="SumTempFee" readonly tabindex=-1 >
       </TD>
      </TR>
    </Table> 
   </Div> 
   </Div>  
</Form>
<!--��ܶ� -->

<!--������������ύ������ -->
<Form action="./SinCardTempFeeSave.jsp" method=post name=fmSave id="fmSave" 	target="fraSubmit">
<Table>
	<tr>
		<td class=common>
			<IMG src="../common/images/butExpand.gif"	style="cursor: hand;" OnClick="showPage(this,divTempFeeSave);">
		<td class=titleImg>
			�洢����
		</td>
		</td>
	</tr>
</Table>
<Div id="divTempFeeSave" style="display: ''"><!--�ݽ��Ѳ��� -->
<Table class=common>
	<tr>
		<td style=" text-align: left" colSpan=1>
		 <span id="spanTempToGrid">
		 </span>
		</td>
	</tr>
</Table>

<br>
<!--�ݽ��ѷ��ಿ�� -->
<Table class=common>
	<tr>
		<td style=" text-align: left" colSpan=1><span id="spanTempClassToGrid">
		</span></td>
	</tr>
</Table>
<a href="javascript:void(0)" class=button onclick="submitForm();">ȫ���ύ</a>
<a href="javascript:void(0)" class=button onclick="clearFormData();">����ȫ������</a>
<!-- <input type=button class=cssButton value="ȫ���ύ" 	onclick="submitForm();"> 
<input type=button class=cssButton value="����ȫ������" onclick="clearFormData();"> -->
</Div>
<input type=hidden name=fmStartNo id="fmStartNo"> 
<input type=hidden name=fmEndNo id="fmEndNo"> 
<br>
<br>
<br>
<br>
</Form>
<!--����� -->
<span id="spanCode" style="display: none; position: absolute;"></span>

<!--����-��Ų�ѯ���� -->
<Form action="./TempFeeTypeQuery.jsp" method=post name=fmTypeQuery id="fmTypeQuery" target="fraSubmit">
	<input type=hidden name=QueryNo id="QueryNo"> 
	<input type=hidden name=QueryType id="QueryType"></Form>

</body>
</html>
