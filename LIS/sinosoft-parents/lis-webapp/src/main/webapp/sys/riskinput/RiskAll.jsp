
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html> 
<head >
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">

</head>

<body  onload="" >
  <form name=fm id=fm target="fraTitle">
	<Div  id= "divButton" style= "display: ''" class=maxbox1>
    <table class=common>
       <tr>
          <TD  class= title>  ���ֱ��� </TD>
          <TD  class= input> <Input class="code" name=RiskCode id=RiskCode style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick=" showCodeList('RiskInd',[this]);" onkeyup="return showCodeListKey('RiskInd',[this]);"> </TD>
          <TD  class= title> ���ձ������� </TD>
          <TD  class= input> <Input class="readonly wid" readonly name=MainPolNo id=MainPolNo verify="����Ͷ��������|notnull" >  </TD>     
          <TD  class= title>   ��ͬ��  </TD>
          <TD  class= input>  <Input class= "readonly wid" name=ContNo id=ContNo > </TD>
		</tr>        
     </table>
    </Div>
	 	<table>
    		<tr>
    		<td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divRiskInfo);"></td>
    		<td class= titleImg> ������Ϣ </td>
    		</tr>
    	</table>
    <Div  id= "divRiskInfo" style= "display: ''" class=maxbox>
	 <table class= common>
	
	   	<TR class=common>	
			<TD  class= title> �������� </TD>
        	<TD  class= input> <Input class="readonly wid" readonly name=PolNo  id=PolNo> </TD>
        	<TD  class= title> ���屣������  </TD>
            <TD  class= input> <Input class="readonly wid" readonly name=GrpPolNo id=GrpPolNo > </TD>
            <TD  class= title> �ܵ�/��ͬ���� </TD>
            <TD  class= input> <Input class="readonly wid" readonly name=ContNo id=GrpPolNo  > </TD>
        </TR>
        <TR class= common>
          <TD  class= title> ӡˢ���� </TD>
          <TD  class= input> <Input name=PrtNo id=PrtNo class="readonly wid" readonly > </TD>
          <TD  class= title> �����˱��� </TD>
          <TD  class= input> <Input class="code" name=AgentCode id=AgentCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="�����˱���|notnull&code:AgentCode" ondblclick="return showCodeList('AgentCode',[this],null,null,null,null,1);" onkeyup="return showCodeListKey('AgentCode',[this],null,null,null,null,1);"> </TD>  
          <TD  class= title> ��������� </TD>
          <TD  class= input> <Input class="readonly wid" readonly name=AgentGroup id=AgentGroup  > </TD>  
        </TR>       
        <TR class= common>
          <TD  class= title> ������� </TD>
          <TD  class= input> <Input class="code"  name=ManageCom verify="�������|code:comcode" ondblclick="return showCodeList('comcode',[this],null,null,null,null,1);" onkeyup="return showCodeListKey('comcode',[this],null,null,null,null,1);"> </TD>        
          <TD  class= title> ������� </TD>
          <TD  class= input> <Input name=AgentCom class="readonly wid" readonly > </TD>
          <TD  class= title>  �������� </TD>
          <TD  class= input> <Input class="code" name=SaleChnl id=SaleChnl style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('SaleChnl',[this]);"  onkeyup="return showCodeListKey('SaleChnl',[this]);"> </TD>
        </TR>
        <!--TR class= common>
          <TD  class= title>  �Ƿ�¶�����ʾ?  </TD>
          <TD  class= input> <Input class= readonly name=SignDateFlag > </TD>
          <TD  class= title> �����շ�Ա���? </TD>
          <TD  class= input> <Input class= readonly name=FirstPayDateFlag > </TD>
          <TD  class= title>  �����շ�Ա?  </TD>
          <TD  class= input>  <Input class= readonly name=PayEndDateFlag >  </TD>
        </TR-->
        <TR class= common>
          <TD  class= title> ����״̬ </TD>
          <TD  class= input> <Input class="code" name=PolState id=PolState style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="����״̬|NOTNULL" CodeData="0|^0|��Ч^1|ʧЧ^2|��Լ" ondblClick="showCodeListEx('PolState_1',[this],[0,1,2]);" onkeyup="showCodeListKeyEx('PolState_1',[this],[0,1,2]);"></TD>
          <TD  class= title>  ǩ������  </TD>
          <TD  class= input> <Input class= "coolDatePicker" name=SignDate id=SignDate onClick="laydate({elem: '#SignDate'});" id="SignDate"><span class="icon"><a onClick="laydate({elem: '#SignDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>

		  </TD>
        </TR>
        <TR class= common>
          <TD  class= title> ������Ч���� </TD>
          <TD  class= input> <input class="coolDatePicker" dateFormat="short" name="CValiDate" verify="������Ч����|NOTNULL" onClick="laydate({elem: '#CValiDate'});" id="CValiDate"><span class="icon"><a onClick="laydate({elem: '#CValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>

		  </TD>	
          <TD  class= title>  �����Ч����   </TD>
          <TD  class= input>  <Input class= coolDatePicker name=LastRevDate onClick="laydate({elem: '#LastRevDate'});" id="LastRevDate"><span class="icon"><a onClick="laydate({elem: '#LastRevDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>

		  </TD>           
          <TD  class= title> �����ʹ����� </TD>
          <TD  class= input> <Input class= coolDatePicker name=CustomGetPolDate onClick="laydate({elem: '#CustomGetPolDate'});" id="CustomGetPolDate"><span class="icon"><a onClick="laydate({elem: '#CustomGetPolDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>

		  </TD>
        </TR>

        <TR class= common>
          <TD  class= title> ��������/(��) </TD>
          <TD  class= input> <Input class= readonly name=PayYears  id=PayYears>
		  </TD> 
          <TD  class= title> ���ڽ������� </TD>
          <TD  class= input> <Input class=coolDatePicker name=FirstPayDate onClick="laydate({elem: '#FirstPayDate'});" id="FirstPayDate"><span class="icon"><a onClick="laydate({elem: '#FirstPayDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>

		  </TD>                           
          <TD  class= title> �������� </TD>
          <TD  class= input> <Input class=coolDatePicker name=PaytoDate onClick="laydate({elem: '#PaytoDate'});" id="PaytoDate"><span class="icon"><a onClick="laydate({elem: '#PaytoDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>

		  </TD>          
        </TR>
        
        <TR class= common>
          <TD  class= title>  �ս�����  </TD>
          <TD  class= input>  <Input class=coolDatePicker name=PayEndDate onClick="laydate({elem: '#PayEndDate'});" id="PayEndDate"><span class="icon"><a onClick="laydate({elem: '#PayEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>

		  </TD>
          <TD  class= title>  �ս��ڼ� </TD>
          <TD  class= input> <input class="coolDatePicker" name="PayEndYear" onClick="laydate({elem: '#PayEndYear'});" id="PayEndYear"><span class="icon"><a onClick="laydate({elem: '#PayEndYear'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>

		  </TD>
          <TD  class= title>  �ս��ڼ䵥λ </TD>
          <TD  class= input> <Input class="code" name=PayEndYearFlag id=PayEndYearFlag style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('PeriodUnit',[this]);" onkeyup="return showCodeListKey('PeriodUnit',[this]);"> </TD>
        </TR>
      <TR class= common>
		 <TD  class= title> ���Ѽ�� </TD>
         <TD  class= input> <Input class="code" name=PayIntv id=PayIntv style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('PayIntv',[this]);" onkeyup="return showCodeListKey('PayIntv',[this]);"> </TD>
         <TD  class= title>  �շѷ�ʽ </TD>
         <TD  class= input> <Input class="code" name=PayLocation id=PayLocation style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="�շѷ�ʽ|code:PayLocation" ondblclick="return showCodeList('PayLocation',[this]);" onkeyup="return showCodeListKey('PayLocation',[this]);"> </TD>       
		  <!--TD  class= title> ����ί������� </TD>
          <TD  class= input> <Input class="readonly" name=ConsignNo> </TD-->	        
       </TR>  
        <TR CLASS=common>
          <TD CLASS=title> ������   </TD>
          <TD CLASS=input COLSPAN=1> <Input NAME=BankCode id=BankCode VALUE="" MAXLENGTH=10 CLASS=code style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('bank', [this]);" onkeyup="return showCodeListKey('bank', [this]);" verify="������|code:bank" > </TD>
          <TD CLASS=title> �����ʺ�   </TD>
          <TD CLASS=input COLSPAN=1>  <Input NAME=BankAccNo id=BankAccNo class="readonly wid" MAXLENGTH=40 > </TD>
          <TD CLASS=title> ����  </TD>
          <TD CLASS=input COLSPAN=1> <Input NAME=AccName id=AccName class="readonly wid" MAXLENGTH=20 > </TD>
        </TR>
        <TR class=common>
          <TD  class= title>  �����ڼ� </TD>
          <TD  class= input> <Input class= "readonly wid" name=InsuYear id=InsuYear > </TD>         
          <TD  class= title> �����ڼ䵥λ </TD>
          <TD  class= input>  <Input class="code" name=InsuYearFlag id=InsuYearFlag style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('PeriodUnit1',[this]);" onkeyup="return showCodeListKey('PeriodUnit1',[this]);"> </TD>
          <TD  class= title>  ����������ֹ���� </TD>
          <TD  class= input>  <Input class="readonly wid" name=EndDate id=EndDate > </TD>
        </TR>
        <TR class= common>
          <TD  class= title> ����  </TD>
          <TD  class= input>  <Input class= "readonly wid" name=Prem id=Prem >  </TD>
          <TD  class= title>  ���� </TD>
          <TD  class= input>  <Input class= "readonly wid" name=Amnt id=Amnt > </TD>
          <TD  class= title>  ����  </TD>
          <TD  class= input>  <Input class= "readonly wid" name=Mult id=Mult > </TD>
        </TR>
        <TR class= common>
          <TD class= title> �ۼƱ��� </TD>
          <TD  class= input> <Input class="readonly wid" name=SumPrem id=SumPrem >  </TD>
          <TD  class= title> ��� </TD>
          <TD  class= input> <Input class="readonly wid" name=LeavingMoney id=LeavingMoney >   </TD>
          <TD  class= title> ������ȡ��ʽ</TD>
          <TD  class= input> <Input class="code" name=BonusGetMode id=BonusGetMode style="background:url(../common/images/select--bg_03.png) no-repeat right center"
 ondblclick="return showCodeList('livegetmode',[this]);" onkeyup="return showCodeListKey('livegetmode',[this]);"> </TD>
        </TR>
        <TR class= common>
		  <TD  class= title> �������ȡ��ʽ </TD>
          <TD  class= input><Input class="code" name=LiveGetMode id=LiveGetMode style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="����|NOTNULL" CodeData="0|^1|�ۻ���Ϣ^2|��ȡ�ֽ�^3|�ֽɱ���^4|����^5|�����" ondblClick="showCodeListEx('SC_1',[this],[1,2,3,4,9]);" onkeyup="showCodeListKeyEx('SC_1',[this],[1,2,3,4,9]);"></TD>
          <TD  class= title>  �������ʽ  </TD>
          <TD  class= input> <Input class=code name=InterestDifFlag id=InterestDifFlag style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('InterestDifFlag',[this]);" onkeyup="return showCodeListKey('InterestDifFlag',[this]);"> </TD>
          <TD class= title> �����ȡ��ʽ</TD>
          <TD class= input> <Input class= "code" name=GetDutyKind id=GetDutyKind style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="��������|NOTNULL" CodeData="0|^1|һ����ȡ��|^2|���춨����|^3|���춨����|^4|����ʮ��̶�������|^5|����ʮ��̶�������|^6|��������������|^7|��������������|^8|���켸��������|^9|���켸��������" ondblClick="showCodeListEx('GetIntv212401',[this],[0]);" onkeyup="showCodeListKeyEx('GetIntv212401',[this],[0]);">  </TD>        
        </TR> 
        <TR class = common>
          <TD  class= title>����������� </TD>
          <TD  class= input><Input class= "readonly wid" name=GetYear id=GetYear > </TD>	
          <TD  class= title>����������ڱ�־ </TD>
          <TD  class= input> <Input class="code" name=GetYearFlag id=GetYearFlag style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('PeriodUnit',[this]);" onkeyup="return showCodeListKey('PeriodUnit',[this]);"> </TD>  
		  <TD  class= title>����������ڲ��� </TD>
          <TD  class= input> <Input class="code" name=GetStartType id=GetStartType style="background:url(../common/images/select--bg_03.png) no-repeat right center" CodeData="0|^S|�����ڶ�Ӧ��|^B|�������ڶ�Ӧ��" ondblClick="showCodeListEx('StartDateCalRef212401',[this],[0]);" onkeyup="showCodeListKeyEx('StartDateCalRef212401',[this],[0]);" > </TD>  
         </TR>
        <TR class= common>
		  <TD  class= title> �Զ��潻��־ </TD>
          <TD  class= input> <Input class="code" name=AutoPayFlag id=AutoPayFlag style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="�潻��־|NOTNULL" CodeData="0|^0|����^1|�潻" ondblClick="showCodeListEx('AutoPayFlag_1',[this],[0,1]);" onkeyup="showCodeListKeyEx('AutoPayFlag_1',[this],[0,1]);"> </TD>        
          <!--TD  class= title> ��������־? </TD>
          <TD  class= input> <Input class="readonly" name=ConsignNoflag> </TD-->	
          <TD  class= title> ������־  </TD>
          <TD  class= input>  <Input class="code" name=SubFlag id=SubFlag style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="����|NOTNULL" CodeData="0|^0|����^1|����" ondblClick="showCodeListEx('JE_1',[this],[0,1]);" onkeyup="showCodeListKeyEx('JE_1',[this],[0,1]);">  </TD>
         <TD  class= title>  ����־ </TD>
          <TD  class= input> <Input class="code" name=HealthCheckFlag id=HealthCheckFlag style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="���|NOTNULL" CodeData="0|^0|�����^1|���" ondblClick="showCodeListEx('TJ_1',[this],[0,1]);" onkeyup="showCodeListKeyEx('TJ_1',[this],[0,1]);"> </TD>                          
        </TR>
        <TR class= common>
          <TD  class= title>  ������ӡ���� </TD>
		  <TD  class= input>  <Input class="readonly wid" name=PrintCount id=PrintCount >  </TD>
          <TD  class= title>  ����������� </TD>
		  <TD  class= input>  <Input class="readonly wid" name=LostTimes id=LostTimes >  </TD>		  
        </TR>   
      </table>    
    </Div>
    <!-- ��������Ϣ���� -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCInsured1);">
    		</td>
    		<td class= titleImg>
    			 ��������Ϣ���ͻ��ţ�<Input class="readonly wid" readonly name=CustomerNo id=CustomerNo > ��
    		</td>
    	</tr>
    </table>
    <Div  id= "divLCInsured1" style= "display: ''" class=maxbox>
      <table  class= common>
        <TR  class= common>        
          <TD  class= title> ���� </TD>
          <TD  class= input> <Input class="readonly wid" readonly name=Name id=Name verify="����������|notnull&len<=20" >  </TD>
          <TD  class= title>  �Ա�  </TD>
          <TD  class= input>  <Input class="code" readonly name=Sex id=Sex style="background:url(../common/images/select--bg_03.png) no-repeat right center"  verify="�������Ա�|notnull&code:Sex" ondblclick="return showCodeList('Sex',[this]);" onkeyup="return showCodeListKey('Sex',[this]);"> </TD>
          <TD  class= title> �������� </TD>
          <TD  class= input>  <input class="readonly wid" readonly name="Birthday" id=Birthday verify="�����˳�������|notnull&date" > </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>  Ͷ������  </TD>
          <TD  class= input> <input class="readonly wid" readonly name="Age" id=Age >  </TD>
          <TD  class= title>   ֤������  </TD>
          <TD  class= input>  <Input class="code" readonly name="IDType" verify="������֤������|code:IDType" ondblclick="return showCodeList('IDType',[this]);" onkeyup="return showCodeListKey('IDType',[this]);">  </TD>
          <TD  class= title>  ֤������ </TD>
          <TD  class= input>  <Input class="readonly wid" readonly name="IDNo" id=IDNo verify="������֤������|len<=20" >  </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
          <input class="code" readonly name="NativePlace" id=NativePlace verify="�����˹���|code:NativePlace" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('NativePlace',[this]);" onkeyup="return showCodeListKey('NativePlace',[this]);">
          </TD>
          <TD  class= title>
            �������ڵ�
          </TD>
          <TD  class= input>
            <Input class="readonly wid"  readonly name="RgtAddress" id=RgtAddress verify="�����˻������ڵ�|len<=80" >
          </TD>
          <TD  class= title>
            ����״��
          </TD>
          <TD  class= input>
            <Input class="code"  name="Marriage " id=Marriage style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="�����˻���״��|code:Marriage" ondblclick="return showCodeList('Marriage',[this]);" onkeyup="return showCodeListKey('Marriage',[this]);">
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
          <input class="code" readonly name="Nationality" id=Nationality style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="����������|code:Nationality" ondblclick="return showCodeList('Nationality',[this]);" onkeyup="return showCodeListKey('Nationality',[this]);">
          </TD>
          <TD  class= title>
            ѧ��
          </TD>
          <TD  class= input>
            <Input class="code"  name="Degree" id=Degree style="background:url(../common/images/select--bg_03.png) no-repeat right center"
 verify="������ѧ��|code:Degree" ondblclick="return showCodeList('Degree',[this]);" onkeyup="return showCodeListKey('Degree',[this]);">
          </TD>
          <TD  class= title>
            �Ƿ�����
          </TD>
          <TD  class= input>
            <Input class="code" readonly name="SmokeFlag" id=SmokeFlag style="background:url(../common/images/select--bg_03.png) no-repeat right center"
 verify="�������Ƿ�����|code:YesNo" ondblclick="return showCodeList('YesNo',[this]);" onkeyup="return showCodeListKey('YesNo',[this]);">
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ��ϵ��ַ
          </TD>
          <TD  class= input colspan=3 >
            <Input class="readonly3 wid" readonly name="PostalAddress" id=PostalAddress verify="��������ϵ��ַ|len<=80" >
          </TD>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name="ZipCode" id=PostalAddress verify="��������������|zipcode" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ��ͥ�绰
          </TD>
          <TD  class= input>
          <input class="readonly wid" readonly name="Phone" id=Phone verify="�����˼�ͥ�绰|len<=18" >
          </TD>
          <TD  class= title>
            �ƶ��绰
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name="Mobile" id=Mobile verify="�������ƶ��绰|len<=15" >
          </TD>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name="EMail" id=Mobile verify="�����˵�������|len<=20" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ������λ
          </TD>
          <TD  class= input colspan=3 >
            <Input class="readonly3 wid" readonly name="GrpName" id=GrpName verify="�����˹�����λ|len<=60" >
          </TD>
          <TD  class= title>
            ��λ�绰
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name="GrpPhone" id=GrpPhone verify="�����˵�λ�绰|len<=18" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ��λ��ַ
          </TD>
          <TD  class= input colspan=3 >
            <Input class="readonly3 wid" readonly name="GrpAddress" id=GrpAddress verify="�����˵�λ��ַ|len<=80" >
          </TD>
          <TD  class= title>
            ��λ��������
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name="GrpZipCode" id=GrpZipCode verify="�����˵�λ��������|zipcode" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ְҵ�����֣�
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name="WorkType" id=WorkType verify="������ְҵ�����֣�|len<=10" >
          </TD>
          <TD  class= title>
            ��ְ�����֣�
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name="PluralityType" id=PluralityType verify="�����˼�ְ�����֣�|len<=10" >
          </TD>
          <TD  class= title>
            ְҵ���
          </TD>
          <TD  class= input>
            <Input class="code"  name="OccupationType" id=OccupationType style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="������ְҵ���|notnull&code:OccupationType" ondblclick="return showCodeList('OccupationType',[this]);" onkeyup="return showCodeListKey('OccupationType',[this]);">
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ְҵ����
          </TD>
          <TD  class= input>
            <Input class="code" readonly name="OccupationCode" id=OccupationCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="������ְҵ����|code:OccupationCode" ondblclick="return showCodeList('OccupationCode',[this]);" onkeyup="return showCodeListKey('OccupationCode',[this]);">
          </TD>
          <TD  class= title>
            ��ϵ�绰2
          </TD>
          <TD  class= input>
          <input class="readonly wid" readonly name="Phone2" id=Phone2 verify="��ϵ�绰2|len<=18" >
          </TD>
        </TR>
      </table>
    </Div>    
    <!-- ������Ϣ -->
    <Div  id= "divLCPol01" style= "display: ''" class=maxbox1>
          <TD  class= title>
            ����״��
          </TD>
          <TD  class= input>
            <Input class="readonly wid" name=Health id=Health >
          </TD>
    </Div> 
    
    <!-- Ͷ������Ϣ���� -->
    <table>
    	<tr>
        	<td class=common>
    	  		<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCAppntInd1);">
    		</td>
    		<td class= titleImg>
    			 Ͷ������Ϣ���ͻ��ţ�<Input class="readonly wid" readonly  name=AppntCustomerNo id=AppntCustomerNo > ��
 			 
    		</td>
    	</tr>
    </table>
    <Div  id= "divLCAppntInd1" style= "display: ''" class=maxbox>
      <table  class= common>
        <TR  class= common>        
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name=AppntName id=AppntName verify="Ͷ��������|len<=20" >
          </TD>
          <TD  class= title>
            �Ա�
          </TD>
          <TD  class= input>
            <Input class="code" name=AppntSex id=AppntSex style="background:url(../common/images/select--bg_03.png) no-repeat right center"  verify="�������Ա�|notnull&code:Sex" ondblclick="return showCodeList('Sex',[this]);" onkeyup="return showCodeListKey('Sex',[this]);">
          </TD>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
          <input class="readonly wid" readonly name="AppntBirthday" id=AppntBirthday verify="Ͷ���˳�������|date" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
          <input class="readonly wid" readonly name="AppntAge" id=AppntAge >
          </TD>
          <TD  class= title>
            ֤������
          </TD>
          <TD  class= input>
            <Input class="code" readonly name="AppntIDType" id=AppntIDType style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="������֤������|code:IDType" ondblclick="return showCodeList('IDType',[this]);" onkeyup="return showCodeListKey('IDType',[this]);">
          </TD>
          <TD  class= title>
            ֤������
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name="AppntIDNo" id=AppntIDNo verify="Ͷ����֤������|len<=20" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
          <input class="code" readonly name="AppntNativePlace" id=AppntNativePlace style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="�����˹���|code:NativePlace" ondblclick="return showCodeList('NativePlace',[this]);" onkeyup="return showCodeListKey('NativePlace',[this]);">
          </TD>
          <TD  class= title>
            �������ڵ�
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name="AppntRgtAddress" id=AppntRgtAddress verify="Ͷ���˻������ڵ�|len<=80" >
          </TD>
          <TD  class= title>
            ����״��
          </TD>
          <TD  class= input>
            <Input class="code" readonly name="AppntMarriage" id=AppntMarriage style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="�����˻���״��|code:Marriage" ondblclick="return showCodeList('Marriage',[this]);" onkeyup="return showCodeListKey('Marriage',[this]);">
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
          <input class="code" readonly name="AppntNationality" id=AppntNationality style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="����������|code:Nationality" ondblclick="return showCodeList('Nationality',[this]);" onkeyup="return showCodeListKey('Nationality',[this]);">
          </TD>
          <TD  class= title>
            ѧ��
          </TD>
          <TD  class= input>
            <Input class="code" readonly name="AppntDegree" id=AppntDegree style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="������ѧ��|code:Degree" ondblclick="return showCodeList('Degree',[this]);" onkeyup="return showCodeListKey('Degree',[this]);">
          </TD>
          <TD  class= title>
            �뱻�����˹�ϵ
          </TD>
          <TD  class= input>
            <Input class="code"  name="AppntRelationToInsured" id=AppntRelationToInsured style="background:url(../common/images/select--bg_03.png) no-repeat right center"  verify="�����˹�ϵ|code:Relation" ondblclick="return showCodeList('Relation',[this]);" onkeyup="return showCodeListKey('Relation',[this]);">
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ��ϵ��ַ
          </TD>
          <TD  class= input colspan=3 > 
            <Input class="readonly3 wid" readonly name="AppntPostalAddress" id=AppntPostalAddress verify="Ͷ������ϵ��ַ|len<=80" >
          </TD>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name="AppntZipCode" id=AppntZipCode verify="Ͷ������������|zipcode" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ��ͥ�绰
          </TD>
          <TD  class= input>
          <input class="readonly wid" readonly name="AppntPhone" id=AppntPhone verify="Ͷ���˼�ͥ�绰|len<=18" >
          </TD>
          <TD  class= title>
            �ƶ��绰
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name="AppntMobile" id=AppntMobile verify="Ͷ�����ƶ��绰|len<=15" >
          </TD>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name="AppntEMail" id=AppntEMail verify="Ͷ���˵�������|len<=20" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ������λ
          </TD>
          <TD  class= input colspan=3 >
            <Input class="readonly3 wid" readonly name="AppntGrpName" id=AppntGrpName verify="Ͷ���˹�����λ|len<=60" >
          </TD>
          <TD  class= title>
            ��λ�绰
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name="AppntGrpPhone" id=AppntGrpPhone verify="Ͷ���˵�λ�绰|len<=18" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ��λ��ַ
          </TD>
          <TD  class= input colspan=3 >
            <Input class="readonly3 wid" readonly name="AppntGrpAddress" id=AppntGrpAddress verify="Ͷ���˵�λ��ַ|len<=80" >
          </TD>
          <TD  class= title>
            ��λ��������
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name="AppntGrpZipCode" id=AppntGrpZipCode verify="Ͷ���˵�λ��������|zipcode" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ְҵ�����֣�
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name="AppntWorkType" id=AppntWorkType verify="Ͷ����ְҵ�����֣�|len<=10" >
          </TD>
          <TD  class= title>
            ��ְ�����֣�
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name="AppntPluralityType" id=AppntPluralityType verify="Ͷ���˼�ְ�����֣�|len<=10" >
          </TD>
          <TD  class= title>
            ְҵ���
          </TD>
          <TD  class= input>
            <Input class="code" readonly name="AppntOccupationType" id=AppntOccupationType style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="������ְҵ���|notnull&code:OccupationType" ondblclick="return showCodeList('OccupationType',[this]);" onkeyup="return showCodeListKey('OccupationType',[this]);">
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ְҵ����
          </TD>
          <TD  class= input>
            <Input class="code" readonly name="AppntOccupationCode" id=AppntOccupationCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="������ְҵ����|code:OccupationCode" ondblclick="return showCodeList('OccupationCode',[this]);" onkeyup="return showCodeListKey('OccupationCode',[this]);">
          </TD>
          
          <TD  class= title>
            �Ƿ�����
          </TD>
          <TD  class= input>
            <Input class="code" readonly name="AppntSmokeFlag" id=AppntSmokeFlag style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="�������Ƿ�����|code:YesNo" ondblclick="return showCodeList('YesNo',[this]);" onkeyup="return showCodeListKey('YesNo',[this]);">
          </TD>
          
           <TD  class= title>
            ��ϵ�绰2
          </TD>
          <TD  class= input>
          <input class="readonly wid" readonly name="Phone2" id=Phone2 verify="��ϵ�绰2|len<=18" >
          </TD>

        </TR>
      </table>   
    </Div>
    
    <!-- ����Ͷ������Ϣ���� �޸������ֶ�����-->
    <Div  id= "divLCAppntGrp0" style= "display: none ">
    <table>
    	<tr>
        	<td class=common>
    	  		<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCAppntGrp1);">
    		</td>
    		<td class= titleImg>
    			 Ͷ������Ϣ
    		</td>
    	</tr>
    </table>
    <Div  id= "divLCAppntGrp1" style= "display: ''" class=maxbox>
      <table  class= common>
        <TR class= common>
          <TD  class= title>
            Ͷ���˿ͻ���
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name=ColGrpNo id=ColGrpNo >
          </TD>
          <TD  class= title>
            Ͷ��������
          </TD>
          <TD  class= input>
            <Input class= "readonly wid" name=ColGrpName id=ColGrpName >
          </TD>
          <TD  class= title>
            ��ϵ��
          </TD>
          <TD  class= input>
            <input class= "readonly wid" name=ColLinkMan id=ColLinkMan >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            �뱻���˹�ϵ
          </TD>
          <TD  class= input>
            <Input class="readonly wid" name=ColGrpRelation id=ColGrpRelation >
          </TD>
          <TD  class= title>
            �绰
          </TD>
          <TD  class= input>
            <Input class= "readonly wid" name=ColGrpPhone id=ColGrpPhone >
          </TD>
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
            <Input class= "readonly wid" name=ColGrpFax  id=ColGrpFax>
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            e_mail
          </TD>
          <TD  class= input>
            <Input class= "readonly wid" name=ColGrpEMail id=ColGrpEMail >
          </TD>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
            <Input class= "readonly wid" name=ColGrpZipCode id=ColGrpZipCode >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            ��ϵ��ַ
          </TD>
          <TD  class= input colspan=3 >
            <Input class= "readonly wid" name=ColGrpAddress id=ColGrpAddress >
          </TD>
        </TR>
      </table>
    </Div>
    </Div>

      </Div>
      <!-- ���� -->
    <Div  id= "divLCKind0" style= "display: ''">
      <table  class= common>


	      
    <!-- ������������Ϣ���֣��б� -->
	<Div  id= "divLCInsured0" style= "display: ''">
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCInsured2);">
    		</td>
    		<td class= titleImg>
    			 ������������Ϣ
    		</td>
    	</tr>
      </table>
	  <Div  id= "divLCInsured2" style= "display: ''">
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1>
					<span id="spanSubInsuredGrid" >
					</span> 
				</td>
			</tr>
		</table>
	  </div>
	</div>
	
    <!-- ��������Ϣ���֣��б� -->
	<Div  id= "divLCSubInsured0" style= "display: ''">
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCBnf1);">
    		</td>
    		<td class= titleImg>
    			 ��������Ϣ
    		</td>
    	</tr>
    </table>
	<Div  id= "divLCBnf1" style= "display: ''">
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1>
					<span id="spanBnfGrid" >
					</span> 
				</td>
			</tr>
		</table>
	</div>
	</div>
    <!-- ��֪��Ϣ���֣��б� -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCImpart1);">
    		</td>
    		<td class= titleImg>
    			 ��֪��Ϣ
    		</td>
    	</tr>
    </table>
	<Div  id= "divLCImpart1" style= "display: ''">
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1>
					<span id="spanImpartGrid" >
					</span> 
				</td>
			</tr>
		</table>
	</div>
    <!-- ��Լ��Ϣ���֣��б� -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCSpec1);">
    		</td>
    		<td class= titleImg>
    			 ��Լ��Ϣ
    		</td>
    	</tr>
    </table>
	<Div  id= "divLCSpec1" style= "display: ''">
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1>
					<span id="spanSpecGrid">
					</span> 
				</td>
			</tr>
		</table>
	</div>

    <!--����ѡ������β��֣��ò���ʼ������-->
	<Div  id= "divChooseDuty0" style= "display: ''">
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divDutyGrid);">
    		</td>
    		<td class= titleImg>
    			 ��ѡ������Ϣ
    		</td>
    	</tr>
    </table>
	<Div  id= "divDutyGrid" style= "display: ''">
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1>
					<span id="spanDutyGrid" >
					</span> 
				</td>
			</tr>
		</table>
		<!--ȷ���Ƿ���Ҫ������Ϣ-->
	</div>
	</div>
	<div class=maxbox1>
	   <table  class= common>
        <TR  class= common>
          <TD  class= title>
            ¼����Ա
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name=Operator id=Operator >
          </TD>
          <TD  class= title>
            ������Ա
          </TD>
          <TD  class= input>
            <Input class= "readonly wid" readonly name=ApproveCode id=ApproveCode >
          </TD>
          <TD  class= title>
            �˱���Ա
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name=UWCode id=UWCode >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            ¼������
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name=MakeDate id=MakeDate >
          </TD>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
            <Input class= "readonly wid" readonly name=ApproveDate id=ApproveDate >
          </TD>
          <TD  class= title>
            �˱�����
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name=UWDate id=UWDate >
          </TD>
        </TR>
    	</table>
		</div>
		<input type=hidden id="inpNeedDutyGrid" name="inpNeedDutyGrid" value="0">
		<input type=hidden id="fmAction" name="fmAction">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br/><br/><br/><br/>
</body>
</html>
<script>
function returnParent()
{
	var isDia=0;
	var haveMenu=0;
	var callerWindowObj;
	try
	{
		callerWindowObj = dialogArguments; 
		isDia=1;
	}
	catch(ex1)
	{ isDia=0;}
	try
	{
		if(isDia==0) //����Ǵ�һ���µĴ��ڣ���ִ������Ĵ���
		{
			top.opener.parent.document.body.innerHTML=window.document.body.innerHTML;
			top.opener.parent.showCodeName();
		}
		else//�����һ��ģ̬�Ի������������Ĵ���
		{
			callerWindowObj.document.body.innerHTML=window.document.body.innerHTML;
			haveMenu = 1;
			callerWindowObj.parent.frames("fraMenu").Ldd = 0;
			callerWindowObj.parent.frames("fraMenu").Go();
			callerWindowObj.parent.showCodeName();
		}
	}
	catch(ex)
	{
		if( haveMenu != 1 ) 
		{
			alert("Riskxxx.jsp:��������"+ex.name);
		}
	}
	top.close();
}  

returnParent();
</script>


