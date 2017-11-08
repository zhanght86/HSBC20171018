
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
          <TD  class= title>  险种编码 </TD>
          <TD  class= input> <Input class="code" name=RiskCode id=RiskCode style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick=" showCodeList('RiskInd',[this]);" onkeyup="return showCodeListKey('RiskInd',[this]);"> </TD>
          <TD  class= title> 主险保单号码 </TD>
          <TD  class= input> <Input class="readonly wid" readonly name=MainPolNo id=MainPolNo verify="主险投保单号码|notnull" >  </TD>     
          <TD  class= title>   合同号  </TD>
          <TD  class= input>  <Input class= "readonly wid" name=ContNo id=ContNo > </TD>
		</tr>        
     </table>
    </Div>
	 	<table>
    		<tr>
    		<td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divRiskInfo);"></td>
    		<td class= titleImg> 险种信息 </td>
    		</tr>
    	</table>
    <Div  id= "divRiskInfo" style= "display: ''" class=maxbox>
	 <table class= common>
	
	   	<TR class=common>	
			<TD  class= title> 保单号码 </TD>
        	<TD  class= input> <Input class="readonly wid" readonly name=PolNo  id=PolNo> </TD>
        	<TD  class= title> 集体保单号码  </TD>
            <TD  class= input> <Input class="readonly wid" readonly name=GrpPolNo id=GrpPolNo > </TD>
            <TD  class= title> 总单/合同号码 </TD>
            <TD  class= input> <Input class="readonly wid" readonly name=ContNo id=GrpPolNo  > </TD>
        </TR>
        <TR class= common>
          <TD  class= title> 印刷号码 </TD>
          <TD  class= input> <Input name=PrtNo id=PrtNo class="readonly wid" readonly > </TD>
          <TD  class= title> 代理人编码 </TD>
          <TD  class= input> <Input class="code" name=AgentCode id=AgentCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="代理人编码|notnull&code:AgentCode" ondblclick="return showCodeList('AgentCode',[this],null,null,null,null,1);" onkeyup="return showCodeListKey('AgentCode',[this],null,null,null,null,1);"> </TD>  
          <TD  class= title> 代理人组别 </TD>
          <TD  class= input> <Input class="readonly wid" readonly name=AgentGroup id=AgentGroup  > </TD>  
        </TR>       
        <TR class= common>
          <TD  class= title> 管理机构 </TD>
          <TD  class= input> <Input class="code"  name=ManageCom verify="管理机构|code:comcode" ondblclick="return showCodeList('comcode',[this],null,null,null,null,1);" onkeyup="return showCodeListKey('comcode',[this],null,null,null,null,1);"> </TD>        
          <TD  class= title> 代理机构 </TD>
          <TD  class= input> <Input name=AgentCom class="readonly wid" readonly > </TD>
          <TD  class= title>  销售渠道 </TD>
          <TD  class= input> <Input class="code" name=SaleChnl id=SaleChnl style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('SaleChnl',[this]);"  onkeyup="return showCodeListKey('SaleChnl',[this]);"> </TD>
        </TR>
        <!--TR class= common>
          <TD  class= title>  是否孤儿单标示?  </TD>
          <TD  class= input> <Input class= readonly name=SignDateFlag > </TD>
          <TD  class= title> 续期收费员编号? </TD>
          <TD  class= input> <Input class= readonly name=FirstPayDateFlag > </TD>
          <TD  class= title>  续期收费员?  </TD>
          <TD  class= input>  <Input class= readonly name=PayEndDateFlag >  </TD>
        </TR-->
        <TR class= common>
          <TD  class= title> 保单状态 </TD>
          <TD  class= input> <Input class="code" name=PolState id=PolState style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="保单状态|NOTNULL" CodeData="0|^0|有效^1|失效^2|解约" ondblClick="showCodeListEx('PolState_1',[this],[0,1,2]);" onkeyup="showCodeListKeyEx('PolState_1',[this],[0,1,2]);"></TD>
          <TD  class= title>  签单日期  </TD>
          <TD  class= input> <Input class= "coolDatePicker" name=SignDate id=SignDate onClick="laydate({elem: '#SignDate'});" id="SignDate"><span class="icon"><a onClick="laydate({elem: '#SignDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>

		  </TD>
        </TR>
        <TR class= common>
          <TD  class= title> 保单生效日期 </TD>
          <TD  class= input> <input class="coolDatePicker" dateFormat="short" name="CValiDate" verify="保单生效日期|NOTNULL" onClick="laydate({elem: '#CValiDate'});" id="CValiDate"><span class="icon"><a onClick="laydate({elem: '#CValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>

		  </TD>	
          <TD  class= title>  最近复效日期   </TD>
          <TD  class= input>  <Input class= coolDatePicker name=LastRevDate onClick="laydate({elem: '#LastRevDate'});" id="LastRevDate"><span class="icon"><a onClick="laydate({elem: '#LastRevDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>

		  </TD>           
          <TD  class= title> 保单送达日期 </TD>
          <TD  class= input> <Input class= coolDatePicker name=CustomGetPolDate onClick="laydate({elem: '#CustomGetPolDate'});" id="CustomGetPolDate"><span class="icon"><a onClick="laydate({elem: '#CustomGetPolDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>

		  </TD>
        </TR>

        <TR class= common>
          <TD  class= title> 交费年期/(年) </TD>
          <TD  class= input> <Input class= readonly name=PayYears  id=PayYears>
		  </TD> 
          <TD  class= title> 首期交费日期 </TD>
          <TD  class= input> <Input class=coolDatePicker name=FirstPayDate onClick="laydate({elem: '#FirstPayDate'});" id="FirstPayDate"><span class="icon"><a onClick="laydate({elem: '#FirstPayDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>

		  </TD>                           
          <TD  class= title> 交至日期 </TD>
          <TD  class= input> <Input class=coolDatePicker name=PaytoDate onClick="laydate({elem: '#PaytoDate'});" id="PaytoDate"><span class="icon"><a onClick="laydate({elem: '#PaytoDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>

		  </TD>          
        </TR>
        
        <TR class= common>
          <TD  class= title>  终交日期  </TD>
          <TD  class= input>  <Input class=coolDatePicker name=PayEndDate onClick="laydate({elem: '#PayEndDate'});" id="PayEndDate"><span class="icon"><a onClick="laydate({elem: '#PayEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>

		  </TD>
          <TD  class= title>  终交期间 </TD>
          <TD  class= input> <input class="coolDatePicker" name="PayEndYear" onClick="laydate({elem: '#PayEndYear'});" id="PayEndYear"><span class="icon"><a onClick="laydate({elem: '#PayEndYear'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>

		  </TD>
          <TD  class= title>  终交期间单位 </TD>
          <TD  class= input> <Input class="code" name=PayEndYearFlag id=PayEndYearFlag style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('PeriodUnit',[this]);" onkeyup="return showCodeListKey('PeriodUnit',[this]);"> </TD>
        </TR>
      <TR class= common>
		 <TD  class= title> 交费间隔 </TD>
         <TD  class= input> <Input class="code" name=PayIntv id=PayIntv style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('PayIntv',[this]);" onkeyup="return showCodeListKey('PayIntv',[this]);"> </TD>
         <TD  class= title>  收费方式 </TD>
         <TD  class= input> <Input class="code" name=PayLocation id=PayLocation style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="收费方式|code:PayLocation" ondblclick="return showCodeList('PayLocation',[this]);" onkeyup="return showCodeListKey('PayLocation',[this]);"> </TD>       
		  <!--TD  class= title> 银行委托书号码 </TD>
          <TD  class= input> <Input class="readonly" name=ConsignNo> </TD-->	        
       </TR>  
        <TR CLASS=common>
          <TD CLASS=title> 开户行   </TD>
          <TD CLASS=input COLSPAN=1> <Input NAME=BankCode id=BankCode VALUE="" MAXLENGTH=10 CLASS=code style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('bank', [this]);" onkeyup="return showCodeListKey('bank', [this]);" verify="开户行|code:bank" > </TD>
          <TD CLASS=title> 银行帐号   </TD>
          <TD CLASS=input COLSPAN=1>  <Input NAME=BankAccNo id=BankAccNo class="readonly wid" MAXLENGTH=40 > </TD>
          <TD CLASS=title> 户名  </TD>
          <TD CLASS=input COLSPAN=1> <Input NAME=AccName id=AccName class="readonly wid" MAXLENGTH=20 > </TD>
        </TR>
        <TR class=common>
          <TD  class= title>  保险期间 </TD>
          <TD  class= input> <Input class= "readonly wid" name=InsuYear id=InsuYear > </TD>         
          <TD  class= title> 保险期间单位 </TD>
          <TD  class= input>  <Input class="code" name=InsuYearFlag id=InsuYearFlag style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('PeriodUnit1',[this]);" onkeyup="return showCodeListKey('PeriodUnit1',[this]);"> </TD>
          <TD  class= title>  保险责任终止日期 </TD>
          <TD  class= input>  <Input class="readonly wid" name=EndDate id=EndDate > </TD>
        </TR>
        <TR class= common>
          <TD  class= title> 保费  </TD>
          <TD  class= input>  <Input class= "readonly wid" name=Prem id=Prem >  </TD>
          <TD  class= title>  保额 </TD>
          <TD  class= input>  <Input class= "readonly wid" name=Amnt id=Amnt > </TD>
          <TD  class= title>  份数  </TD>
          <TD  class= input>  <Input class= "readonly wid" name=Mult id=Mult > </TD>
        </TR>
        <TR class= common>
          <TD class= title> 累计保费 </TD>
          <TD  class= input> <Input class="readonly wid" name=SumPrem id=SumPrem >  </TD>
          <TD  class= title> 余额 </TD>
          <TD  class= input> <Input class="readonly wid" name=LeavingMoney id=LeavingMoney >   </TD>
          <TD  class= title> 红利领取方式</TD>
          <TD  class= input> <Input class="code" name=BonusGetMode id=BonusGetMode style="background:url(../common/images/select--bg_03.png) no-repeat right center"
 ondblclick="return showCodeList('livegetmode',[this]);" onkeyup="return showCodeListKey('livegetmode',[this]);"> </TD>
        </TR>
        <TR class= common>
		  <TD  class= title> 生存金领取方式 </TD>
          <TD  class= input><Input class="code" name=LiveGetMode id=LiveGetMode style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="生存|NOTNULL" CodeData="0|^1|累积生息^2|领取现金^3|抵缴保费^4|其他^5|增额交清" ondblClick="showCodeListEx('SC_1',[this],[1,2,3,4,9]);" onkeyup="showCodeListKeyEx('SC_1',[this],[1,2,3,4,9]);"></TD>
          <TD  class= title>  利差返还方式  </TD>
          <TD  class= input> <Input class=code name=InterestDifFlag id=InterestDifFlag style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('InterestDifFlag',[this]);" onkeyup="return showCodeListKey('InterestDifFlag',[this]);"> </TD>
          <TD class= title> 年金领取方式</TD>
          <TD class= input> <Input class= "code" name=GetDutyKind id=GetDutyKind style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="给付方法|NOTNULL" CodeData="0|^1|一次领取型|^2|年领定额型|^3|月领定额型|^4|年领十年固定定额型|^5|月领十年固定定额型|^6|年领算术增额型|^7|月领算术增额型|^8|年领几何增额型|^9|月领几何增额型" ondblClick="showCodeListEx('GetIntv212401',[this],[0]);" onkeyup="showCodeListKeyEx('GetIntv212401',[this],[0]);">  </TD>        
        </TR> 
        <TR class = common>
          <TD  class= title>年金起领日期 </TD>
          <TD  class= input><Input class= "readonly wid" name=GetYear id=GetYear > </TD>	
          <TD  class= title>年金起领日期标志 </TD>
          <TD  class= input> <Input class="code" name=GetYearFlag id=GetYearFlag style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('PeriodUnit',[this]);" onkeyup="return showCodeListKey('PeriodUnit',[this]);"> </TD>  
		  <TD  class= title>年金起领日期参照 </TD>
          <TD  class= input> <Input class="code" name=GetStartType id=GetStartType style="background:url(../common/images/select--bg_03.png) no-repeat right center" CodeData="0|^S|起保日期对应日|^B|出生日期对应日" ondblClick="showCodeListEx('StartDateCalRef212401',[this],[0]);" onkeyup="showCodeListKeyEx('StartDateCalRef212401',[this],[0]);" > </TD>  
         </TR>
        <TR class= common>
		  <TD  class= title> 自动垫交标志 </TD>
          <TD  class= input> <Input class="code" name=AutoPayFlag id=AutoPayFlag style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="垫交标志|NOTNULL" CodeData="0|^0|正常^1|垫交" ondblClick="showCodeListEx('AutoPayFlag_1',[this],[0,1]);" onkeyup="showCodeListKeyEx('AutoPayFlag_1',[this],[0,1]);"> </TD>        
          <!--TD  class= title> 保单借款标志? </TD>
          <TD  class= input> <Input class="readonly" name=ConsignNoflag> </TD-->	
          <TD  class= title> 减额交清标志  </TD>
          <TD  class= input>  <Input class="code" name=SubFlag id=SubFlag style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="减额|NOTNULL" CodeData="0|^0|正常^1|减额" ondblClick="showCodeListEx('JE_1',[this],[0,1]);" onkeyup="showCodeListKeyEx('JE_1',[this],[0,1]);">  </TD>
         <TD  class= title>  体检标志 </TD>
          <TD  class= input> <Input class="code" name=HealthCheckFlag id=HealthCheckFlag style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="体检|NOTNULL" CodeData="0|^0|不体检^1|体检" ondblClick="showCodeListEx('TJ_1',[this],[0,1]);" onkeyup="showCodeListKeyEx('TJ_1',[this],[0,1]);"> </TD>                          
        </TR>
        <TR class= common>
          <TD  class= title>  保单打印次数 </TD>
		  <TD  class= input>  <Input class="readonly wid" name=PrintCount id=PrintCount >  </TD>
          <TD  class= title>  保单补打次数 </TD>
		  <TD  class= input>  <Input class="readonly wid" name=LostTimes id=LostTimes >  </TD>		  
        </TR>   
      </table>    
    </Div>
    <!-- 被保人信息部分 -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCInsured1);">
    		</td>
    		<td class= titleImg>
    			 被保人信息（客户号：<Input class="readonly wid" readonly name=CustomerNo id=CustomerNo > ）
    		</td>
    	</tr>
    </table>
    <Div  id= "divLCInsured1" style= "display: ''" class=maxbox>
      <table  class= common>
        <TR  class= common>        
          <TD  class= title> 姓名 </TD>
          <TD  class= input> <Input class="readonly wid" readonly name=Name id=Name verify="被保人姓名|notnull&len<=20" >  </TD>
          <TD  class= title>  性别  </TD>
          <TD  class= input>  <Input class="code" readonly name=Sex id=Sex style="background:url(../common/images/select--bg_03.png) no-repeat right center"  verify="被保人性别|notnull&code:Sex" ondblclick="return showCodeList('Sex',[this]);" onkeyup="return showCodeListKey('Sex',[this]);"> </TD>
          <TD  class= title> 出生日期 </TD>
          <TD  class= input>  <input class="readonly wid" readonly name="Birthday" id=Birthday verify="被保人出生日期|notnull&date" > </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>  投保年龄  </TD>
          <TD  class= input> <input class="readonly wid" readonly name="Age" id=Age >  </TD>
          <TD  class= title>   证件类型  </TD>
          <TD  class= input>  <Input class="code" readonly name="IDType" verify="被保人证件类型|code:IDType" ondblclick="return showCodeList('IDType',[this]);" onkeyup="return showCodeListKey('IDType',[this]);">  </TD>
          <TD  class= title>  证件号码 </TD>
          <TD  class= input>  <Input class="readonly wid" readonly name="IDNo" id=IDNo verify="被保人证件号码|len<=20" >  </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            国籍
          </TD>
          <TD  class= input>
          <input class="code" readonly name="NativePlace" id=NativePlace verify="被保人国籍|code:NativePlace" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('NativePlace',[this]);" onkeyup="return showCodeListKey('NativePlace',[this]);">
          </TD>
          <TD  class= title>
            户口所在地
          </TD>
          <TD  class= input>
            <Input class="readonly wid"  readonly name="RgtAddress" id=RgtAddress verify="被保人户口所在地|len<=80" >
          </TD>
          <TD  class= title>
            婚姻状况
          </TD>
          <TD  class= input>
            <Input class="code"  name="Marriage " id=Marriage style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="被保人婚姻状况|code:Marriage" ondblclick="return showCodeList('Marriage',[this]);" onkeyup="return showCodeListKey('Marriage',[this]);">
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            民族
          </TD>
          <TD  class= input>
          <input class="code" readonly name="Nationality" id=Nationality style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="被保人民族|code:Nationality" ondblclick="return showCodeList('Nationality',[this]);" onkeyup="return showCodeListKey('Nationality',[this]);">
          </TD>
          <TD  class= title>
            学历
          </TD>
          <TD  class= input>
            <Input class="code"  name="Degree" id=Degree style="background:url(../common/images/select--bg_03.png) no-repeat right center"
 verify="被保人学历|code:Degree" ondblclick="return showCodeList('Degree',[this]);" onkeyup="return showCodeListKey('Degree',[this]);">
          </TD>
          <TD  class= title>
            是否吸烟
          </TD>
          <TD  class= input>
            <Input class="code" readonly name="SmokeFlag" id=SmokeFlag style="background:url(../common/images/select--bg_03.png) no-repeat right center"
 verify="被保人是否吸烟|code:YesNo" ondblclick="return showCodeList('YesNo',[this]);" onkeyup="return showCodeListKey('YesNo',[this]);">
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            联系地址
          </TD>
          <TD  class= input colspan=3 >
            <Input class="readonly3 wid" readonly name="PostalAddress" id=PostalAddress verify="被保人联系地址|len<=80" >
          </TD>
          <TD  class= title>
            邮政编码
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name="ZipCode" id=PostalAddress verify="被保人邮政编码|zipcode" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            家庭电话
          </TD>
          <TD  class= input>
          <input class="readonly wid" readonly name="Phone" id=Phone verify="被保人家庭电话|len<=18" >
          </TD>
          <TD  class= title>
            移动电话
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name="Mobile" id=Mobile verify="被保人移动电话|len<=15" >
          </TD>
          <TD  class= title>
            电子邮箱
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name="EMail" id=Mobile verify="被保人电子邮箱|len<=20" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            工作单位
          </TD>
          <TD  class= input colspan=3 >
            <Input class="readonly3 wid" readonly name="GrpName" id=GrpName verify="被保人工作单位|len<=60" >
          </TD>
          <TD  class= title>
            单位电话
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name="GrpPhone" id=GrpPhone verify="被保人单位电话|len<=18" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            单位地址
          </TD>
          <TD  class= input colspan=3 >
            <Input class="readonly3 wid" readonly name="GrpAddress" id=GrpAddress verify="被保人单位地址|len<=80" >
          </TD>
          <TD  class= title>
            单位邮政编码
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name="GrpZipCode" id=GrpZipCode verify="被保人单位邮政编码|zipcode" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            职业（工种）
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name="WorkType" id=WorkType verify="被保人职业（工种）|len<=10" >
          </TD>
          <TD  class= title>
            兼职（工种）
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name="PluralityType" id=PluralityType verify="被保人兼职（工种）|len<=10" >
          </TD>
          <TD  class= title>
            职业类别
          </TD>
          <TD  class= input>
            <Input class="code"  name="OccupationType" id=OccupationType style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="被保人职业类别|notnull&code:OccupationType" ondblclick="return showCodeList('OccupationType',[this]);" onkeyup="return showCodeListKey('OccupationType',[this]);">
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            职业代码
          </TD>
          <TD  class= input>
            <Input class="code" readonly name="OccupationCode" id=OccupationCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="被保人职业代码|code:OccupationCode" ondblclick="return showCodeList('OccupationCode',[this]);" onkeyup="return showCodeListKey('OccupationCode',[this]);">
          </TD>
          <TD  class= title>
            联系电话2
          </TD>
          <TD  class= input>
          <input class="readonly wid" readonly name="Phone2" id=Phone2 verify="联系电话2|len<=18" >
          </TD>
        </TR>
      </table>
    </Div>    
    <!-- 隐藏信息 -->
    <Div  id= "divLCPol01" style= "display: ''" class=maxbox1>
          <TD  class= title>
            健康状况
          </TD>
          <TD  class= input>
            <Input class="readonly wid" name=Health id=Health >
          </TD>
    </Div> 
    
    <!-- 投保人信息部分 -->
    <table>
    	<tr>
        	<td class=common>
    	  		<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCAppntInd1);">
    		</td>
    		<td class= titleImg>
    			 投保人信息（客户号：<Input class="readonly wid" readonly  name=AppntCustomerNo id=AppntCustomerNo > ）
 			 
    		</td>
    	</tr>
    </table>
    <Div  id= "divLCAppntInd1" style= "display: ''" class=maxbox>
      <table  class= common>
        <TR  class= common>        
          <TD  class= title>
            姓名
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name=AppntName id=AppntName verify="投保人姓名|len<=20" >
          </TD>
          <TD  class= title>
            性别
          </TD>
          <TD  class= input>
            <Input class="code" name=AppntSex id=AppntSex style="background:url(../common/images/select--bg_03.png) no-repeat right center"  verify="被保人性别|notnull&code:Sex" ondblclick="return showCodeList('Sex',[this]);" onkeyup="return showCodeListKey('Sex',[this]);">
          </TD>
          <TD  class= title>
            出生日期
          </TD>
          <TD  class= input>
          <input class="readonly wid" readonly name="AppntBirthday" id=AppntBirthday verify="投保人出生日期|date" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            年龄
          </TD>
          <TD  class= input>
          <input class="readonly wid" readonly name="AppntAge" id=AppntAge >
          </TD>
          <TD  class= title>
            证件类型
          </TD>
          <TD  class= input>
            <Input class="code" readonly name="AppntIDType" id=AppntIDType style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="被保人证件类型|code:IDType" ondblclick="return showCodeList('IDType',[this]);" onkeyup="return showCodeListKey('IDType',[this]);">
          </TD>
          <TD  class= title>
            证件号码
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name="AppntIDNo" id=AppntIDNo verify="投保人证件号码|len<=20" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            国籍
          </TD>
          <TD  class= input>
          <input class="code" readonly name="AppntNativePlace" id=AppntNativePlace style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="被保人国籍|code:NativePlace" ondblclick="return showCodeList('NativePlace',[this]);" onkeyup="return showCodeListKey('NativePlace',[this]);">
          </TD>
          <TD  class= title>
            户口所在地
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name="AppntRgtAddress" id=AppntRgtAddress verify="投保人户口所在地|len<=80" >
          </TD>
          <TD  class= title>
            婚姻状况
          </TD>
          <TD  class= input>
            <Input class="code" readonly name="AppntMarriage" id=AppntMarriage style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="被保人婚姻状况|code:Marriage" ondblclick="return showCodeList('Marriage',[this]);" onkeyup="return showCodeListKey('Marriage',[this]);">
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            民族
          </TD>
          <TD  class= input>
          <input class="code" readonly name="AppntNationality" id=AppntNationality style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="被保人民族|code:Nationality" ondblclick="return showCodeList('Nationality',[this]);" onkeyup="return showCodeListKey('Nationality',[this]);">
          </TD>
          <TD  class= title>
            学历
          </TD>
          <TD  class= input>
            <Input class="code" readonly name="AppntDegree" id=AppntDegree style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="被保人学历|code:Degree" ondblclick="return showCodeList('Degree',[this]);" onkeyup="return showCodeListKey('Degree',[this]);">
          </TD>
          <TD  class= title>
            与被保险人关系
          </TD>
          <TD  class= input>
            <Input class="code"  name="AppntRelationToInsured" id=AppntRelationToInsured style="background:url(../common/images/select--bg_03.png) no-repeat right center"  verify="被保人关系|code:Relation" ondblclick="return showCodeList('Relation',[this]);" onkeyup="return showCodeListKey('Relation',[this]);">
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            联系地址
          </TD>
          <TD  class= input colspan=3 > 
            <Input class="readonly3 wid" readonly name="AppntPostalAddress" id=AppntPostalAddress verify="投保人联系地址|len<=80" >
          </TD>
          <TD  class= title>
            邮政编码
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name="AppntZipCode" id=AppntZipCode verify="投保人邮政编码|zipcode" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            家庭电话
          </TD>
          <TD  class= input>
          <input class="readonly wid" readonly name="AppntPhone" id=AppntPhone verify="投保人家庭电话|len<=18" >
          </TD>
          <TD  class= title>
            移动电话
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name="AppntMobile" id=AppntMobile verify="投保人移动电话|len<=15" >
          </TD>
          <TD  class= title>
            电子邮箱
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name="AppntEMail" id=AppntEMail verify="投保人电子邮箱|len<=20" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            工作单位
          </TD>
          <TD  class= input colspan=3 >
            <Input class="readonly3 wid" readonly name="AppntGrpName" id=AppntGrpName verify="投保人工作单位|len<=60" >
          </TD>
          <TD  class= title>
            单位电话
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name="AppntGrpPhone" id=AppntGrpPhone verify="投保人单位电话|len<=18" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            单位地址
          </TD>
          <TD  class= input colspan=3 >
            <Input class="readonly3 wid" readonly name="AppntGrpAddress" id=AppntGrpAddress verify="投保人单位地址|len<=80" >
          </TD>
          <TD  class= title>
            单位邮政编码
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name="AppntGrpZipCode" id=AppntGrpZipCode verify="投保人单位邮政编码|zipcode" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            职业（工种）
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name="AppntWorkType" id=AppntWorkType verify="投保人职业（工种）|len<=10" >
          </TD>
          <TD  class= title>
            兼职（工种）
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name="AppntPluralityType" id=AppntPluralityType verify="投保人兼职（工种）|len<=10" >
          </TD>
          <TD  class= title>
            职业类别
          </TD>
          <TD  class= input>
            <Input class="code" readonly name="AppntOccupationType" id=AppntOccupationType style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="被保人职业类别|notnull&code:OccupationType" ondblclick="return showCodeList('OccupationType',[this]);" onkeyup="return showCodeListKey('OccupationType',[this]);">
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            职业代码
          </TD>
          <TD  class= input>
            <Input class="code" readonly name="AppntOccupationCode" id=AppntOccupationCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="被保人职业代码|code:OccupationCode" ondblclick="return showCodeList('OccupationCode',[this]);" onkeyup="return showCodeListKey('OccupationCode',[this]);">
          </TD>
          
          <TD  class= title>
            是否吸烟
          </TD>
          <TD  class= input>
            <Input class="code" readonly name="AppntSmokeFlag" id=AppntSmokeFlag style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="被保人是否吸烟|code:YesNo" ondblclick="return showCodeList('YesNo',[this]);" onkeyup="return showCodeListKey('YesNo',[this]);">
          </TD>
          
           <TD  class= title>
            联系电话2
          </TD>
          <TD  class= input>
          <input class="readonly wid" readonly name="Phone2" id=Phone2 verify="联系电话2|len<=18" >
          </TD>

        </TR>
      </table>   
    </Div>
    
    <!-- 集体投保人信息部分 修改所有字段名称-->
    <Div  id= "divLCAppntGrp0" style= "display: none ">
    <table>
    	<tr>
        	<td class=common>
    	  		<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCAppntGrp1);">
    		</td>
    		<td class= titleImg>
    			 投保人信息
    		</td>
    	</tr>
    </table>
    <Div  id= "divLCAppntGrp1" style= "display: ''" class=maxbox>
      <table  class= common>
        <TR class= common>
          <TD  class= title>
            投保人客户号
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name=ColGrpNo id=ColGrpNo >
          </TD>
          <TD  class= title>
            投保人名称
          </TD>
          <TD  class= input>
            <Input class= "readonly wid" name=ColGrpName id=ColGrpName >
          </TD>
          <TD  class= title>
            联系人
          </TD>
          <TD  class= input>
            <input class= "readonly wid" name=ColLinkMan id=ColLinkMan >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            与被保人关系
          </TD>
          <TD  class= input>
            <Input class="readonly wid" name=ColGrpRelation id=ColGrpRelation >
          </TD>
          <TD  class= title>
            电话
          </TD>
          <TD  class= input>
            <Input class= "readonly wid" name=ColGrpPhone id=ColGrpPhone >
          </TD>
          <TD  class= title>
            传真
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
            邮政编码
          </TD>
          <TD  class= input>
            <Input class= "readonly wid" name=ColGrpZipCode id=ColGrpZipCode >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            联系地址
          </TD>
          <TD  class= input colspan=3 >
            <Input class= "readonly wid" name=ColGrpAddress id=ColGrpAddress >
          </TD>
        </TR>
      </table>
    </Div>
    </Div>

      </Div>
      <!-- 隐藏 -->
    <Div  id= "divLCKind0" style= "display: ''">
      <table  class= common>


	      
    <!-- 连带被保人信息部分（列表） -->
	<Div  id= "divLCInsured0" style= "display: ''">
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCInsured2);">
    		</td>
    		<td class= titleImg>
    			 连带被保人信息
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
	
    <!-- 受益人信息部分（列表） -->
	<Div  id= "divLCSubInsured0" style= "display: ''">
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCBnf1);">
    		</td>
    		<td class= titleImg>
    			 受益人信息
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
    <!-- 告知信息部分（列表） -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCImpart1);">
    		</td>
    		<td class= titleImg>
    			 告知信息
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
    <!-- 特约信息部分（列表） -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCSpec1);">
    		</td>
    		<td class= titleImg>
    			 特约信息
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

    <!--可以选择的责任部分，该部分始终隐藏-->
	<Div  id= "divChooseDuty0" style= "display: ''">
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divDutyGrid);">
    		</td>
    		<td class= titleImg>
    			 可选责任信息
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
		<!--确定是否需要责任信息-->
	</div>
	</div>
	<div class=maxbox1>
	   <table  class= common>
        <TR  class= common>
          <TD  class= title>
            录入人员
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name=Operator id=Operator >
          </TD>
          <TD  class= title>
            复核人员
          </TD>
          <TD  class= input>
            <Input class= "readonly wid" readonly name=ApproveCode id=ApproveCode >
          </TD>
          <TD  class= title>
            核保人员
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name=UWCode id=UWCode >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            录入日期
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name=MakeDate id=MakeDate >
          </TD>
          <TD  class= title>
            复核日期
          </TD>
          <TD  class= input>
            <Input class= "readonly wid" readonly name=ApproveDate id=ApproveDate >
          </TD>
          <TD  class= title>
            核保日期
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
		if(isDia==0) //如果是打开一个新的窗口，则执行下面的代码
		{
			top.opener.parent.document.body.innerHTML=window.document.body.innerHTML;
			top.opener.parent.showCodeName();
		}
		else//如果打开一个模态对话框，则调用下面的代码
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
			alert("Riskxxx.jsp:发生错误："+ex.name);
		}
	}
	top.close();
}  

returnParent();
</script>


