<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>投保人变更</title>
    <!-- 检查访问地址 -->
    <script language="JavaScript">
        if (top.location == self.location)
        {
            top.location = "PEdorTypeAE.jsp";
        }
    </script>
    <!-- 公共引用样式 -->
    <link href="../common/css/Project.css" type="text/css" rel="stylesheet">
    <link href="../common/css/Project3.css" type="text/css" rel="stylesheet">
    <link href="../common/css/mulLine.css" type="text/css" rel="stylesheet">
    <LINK href="../common/css/Occupation.css" rel=stylesheet type=text/css>
    <!-- 公共引用脚本 -->
    <SCRIPT src="../common/javascript/Occupation.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <script language="JavaScript" src="../common/javascript/Common.js"></script>
    <script language="JavaScript" src="../common/cvar/CCodeOperate.js"></script>
    <script language="JavaScript" src="../common/javascript/MulLine.js"></script>
    <script language="JavaScript" src="../common/javascript/EasyQuery.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryCache.js"></script>
    <script language="JavaScript" src="../common/javascript/VerifyInput.js"></script>
    <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
    <!-- 私有引用脚本 -->
    <script language="JavaScript" src="PEdorTypeAE.js"></script>
    <%@ include file="PEdorTypeAEInit.jsp" %>
</head>
<BODY  onload="initForm();initElementtype();">
 <FORM id=fm name=fm method=post target="fraSubmit">
 <div class=maxbox1>
  <TABLE class=common>
    <TR  class= common>
      <TD  class= title >保全受理号</TD>
      <TD  class= input >
        <input class="readonly wid" readonly id="EdorAcceptNo" name="EdorAcceptNo">
      </TD>
      <TD class = title > 批改类型 </TD>
      <TD class = input >
        <Input class=codeno  readonly id=EdorType name=EdorType><input class=codename id=EdorTypeName name=EdorTypeName readonly=true>
      </TD>
      <TD class = title > 保单号 </TD>
      <TD class = input >
        <input class="readonly wid" readonly id=ContNo name=ContNo>
      </TD>
    </TR>
    <TR class=common>
        <TD class = title>柜面受理日期</TD>
            <TD class = input>
                <input class = "coolDatePicker" readonly name=EdorItemAppDate onClick="laydate({elem: '#EdorItemAppDate'});" id="EdorItemAppDate"><span class="icon"><a onClick="laydate({elem: '#EdorItemAppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </TD>
        <TD class = title>生效日期</TD>
          <TD class = input>
            <input class = "coolDatePicker" readonly name=EdorValiDate onClick="laydate({elem: '#EdorValiDate'});" id="EdorValiDate"><span class="icon"><a onClick="laydate({elem: '#EdorValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD class= title>

          </TD>
          <TD class= input>
            
          </TD>
    </TR>

  </TABLE>
 </div>
   <TABLE>
    <TR>
      <TD class="common">
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divDetail);">
      </td>
      <TD class= titleImg>
        现投保人信息
      </TD>
   </TR>
   </TABLE>
<Div  id= "divDetail" class=maxbox1 style= "display: ''">
      <table  class= common>
        <TR  class= common>
          <TD  class= title>
            客户号
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly id=PCustomerNo name=PCustomerNo >
          </TD>
          <TD  class= title>
            客户姓名
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly id=PName name=PName >
          </TD>
          <TD  class= title>
              性别
          </TD>
          <TD  class= input>
                 <Input class="readonly wid" readonly id=PSex name=PSex >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
           出生日期
          </TD>
          <TD  class= input>
          <Input class= "coolDatePicker" readonly id=PBirthday name=PBirthday onClick="laydate({elem: '#PBirthday'});" id="PBirthday"><span class="icon"><a onClick="laydate({elem: '#PBirthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD  class= title>
            证件类型
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly id=PIDType name=PIDType>
          </TD>
          <TD  class= title>
            证件号码
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly id=PIDNo name=PIDNo >
          </TD>
        </TR>
        </table>
</Div>
<TABLE style= "display: none">
    <TR>
      <TD class="common">
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLC);">
      </td>
      <TD class= titleImg>
        变更原因
      </TD>
   </TR>
</TABLE>
<Div  id= "divLC" class=maxbox1 style= "display: none">
        <table class="common">
            <tr class="common">
                <td class="title">变更原因</td>
                <td class="input"><input type="text" class="codeno" id=EdorReason name="EdorReason" verify="变更原因|Code:EdorReason" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeList('EdorReason',[this,EdorReasonName],[0,1])" onkeyup="showCodeListKey('EdorReason',[this,EdorReasonName],[0,1])"><input type="text" class="codename" id=EdorReasonName name="EdorReasonName" readonly></td>
                <td class="title">&nbsp;</td>
				<td class="input"><input type="text" class="readonly wid" id=CustomerNo name="CustomerNo" readonly></td>
                <td class="title">&nbsp;</td>
                <td class="input">&nbsp;</td>
            </tr>
        </table>
</Div>
<Div id = "divdeath" style = "display: none">
    <table class = common>
        <tr class = common>
            <td class = title>
          投保人死亡时间
      </td>
      <td class = input>
          <input class="coolDatePicker" elementtype=nacessary name="deathdate" onchange="compareTwoDate2() "; onClick="laydate({elem: '#deathdate'});" id="deathdate"><span class="icon"><a onClick="laydate({elem: '#deathdate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
      </td>
      <td class="title">&nbsp;</td>
	  <td class="input"></td>
      <td class="title">&nbsp;</td>
      <td class="input">&nbsp;</td>
    </tr>
  </table>
</Div>
<DIV id=DivLCAppntIndButton STYLE="display:''">
<!-- 投保人信息部分 -->
<table>
<tr>
<td class="common">
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCAppntInd);">
</td>
<td class= titleImg>
投保人信息(客户号)：<Input class= common  id=AppntNo name=AppntNo >
<INPUT id="butBack" VALUE="查  询" TYPE=button class= cssButton onclick="queryAppntNo();">
（首次投保客户无需填写客户号）
</td>
</tr>
</table>

</DIV>

<DIV id=DivLCAppntInd class=maxbox STYLE="display:''">
 <table  class= common>
 	<TR  class= common>
 	 	<TD  class= title>姓</TD>
        <TD  class= input>
            <Input class="common wid" id=LastName name=LastName elementtype=nacessary verify="姓|notnull&len<=120">
        </TD>
        <TD  class= title>名</TD>
        <TD  class= input>
            <Input class="common wid" id=FirstName name=FirstName elementtype=nacessary verify="名|notnull&len<=120">
        </TD>
        <TD  class= title>姓名</TD>
        <TD  class= input>
            <Input class=readonly id=AppntName name=AppntName readonly=true>
        </TD>
 	</TR>
    <TR  class= common>
 	 	<TD  class= title>英文姓</TD>
        <TD  class= input>
            <Input class="common wid" id=LastNameEn name=LastNameEn>
        </TD>
        <TD  class= title> 英文名</TD>
        <TD  class= input>
            <Input class="common wid" id=FirstNameEn name=FirstNameEn>
        </TD>
        <TD  class= title> 拼音姓</TD>
        <TD  class= input>
            <Input class="common wid" id=LastNamePY name=LastNamePY>
        </TD>
 	</TR>
    <TR  class= common>
        <TD  class= title>拼音名</TD>
        <TD  class= input>
            <Input class="common wid" id=FirstNamePY name=FirstNamePY>
        </TD>
          <TD  class= title>
            证件类型
          </TD>
          <TD  class= input>
            <Input class="codeno" id=AppntIDType name="AppntIDType"  verify="投保人证件类型|notnull&code:IDType" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('IDType',[this,AppntIDTypeName],[0,1]);" onkeyup="return showCodeListKey('IDType',[this,AppntIDTypeName],[0,1]);"><input class=codename id=AppntIDTypeName name=AppntIDTypeName readonly=true elementtype=nacessary>
          </TD>
          <TD  class= title>
            证件号码
          </TD>
          <TD  class= input>
            <input type="text" class="coolConfirmBox wid" id=AppntIDNo name="AppntIDNo" elementtype="nacessary" verify="投保人证件号码|notnull&LEN<=20" onblur="checkidtype(); getBirthdaySexByIDNo(this.value); showOneCodeName('Sex','AppntSex','AppntSexName');">
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            性别
          </TD>
          <TD  class= input>
            <Input class="codeno" id=AppntSex name=AppntSex verify="投保人性别|notnull&code:Sex" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('Sex',[this,AppntSexName],[0,1]);" onkeyup="return showCodeListKey('Sex',[this,AppntSexName],[0,1]);"><input class=codename id=AppntSexName name=AppntSexName readonly=true elementtype=nacessary>
          </TD>
          <TD  class= title>
            出生日期
          </TD>
          <TD  class= input>
          <input class="coolDatePicker" dateFormat="short" elementtype=nacessary onblur="checkappntbirthday();getAge();" elementtype=nacessary name="AppntBirthday" verify="投保人出生日期|NOTNUlL&DATE" onClick="laydate({elem: '#AppntBirthday'});" id="AppntBirthday"><span class="icon"><a onClick="laydate({elem: '#AppntBirthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD  class= title>
            婚姻状况
          </TD>
          <TD  class= input>
            <Input class="codeno" id=AppntMarriage name="AppntMarriage" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"  ondblclick="return showCodeList('Marriage',[this,AppntMarriageName],[0,1]);" onkeyup="return showCodeListKey('Marriage',[this,AppntMarriageName],[0,1]);"><input class=codename id=AppntMarriageName name=AppntMarriageName readonly=true >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            语言
          </TD>
          <TD  class= input>
            <Input class="codeno" id=AppntLanguage name="AppntLanguage"  verify="语言|CODE:language" verifyorder="1" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('language',[this,AppntLanguageName],[0,1]);" onkeyup="return showCodeListKey('language',[this,AppntLanguageName],[0,1]);"><input class=codename id=AppntLanguageName name=AppntLanguageName readonly=true elementtype=nacessary>
          </TD>
          <TD  class= title>
            
          </TD>
          <TD  class= input>
          
          </TD>
          <TD  class= title>
            
          </TD>
          <TD  class= input>
           
          </TD>
        </TR>

        <TR  class= common>
          <TD  class= title>
            国籍
          </TD>
          <TD  class= input>
          <input class="codeno" id=AppntNativePlace name="AppntNativePlace" verify="投保人国籍|code:NativePlace" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('NativePlace',[this,AppntNativePlaceName],[0,1]);" onkeyup="return showCodeListKey('NativePlace',[this,AppntNativePlaceName],[0,1]);"><input class=codename id=AppntNativePlaceName name=AppntNativePlaceName readonly=true>
          </TD>
          <TD  class= title>
            户口所在地
          </TD>
          <TD  class= input>
            <Input class="common wid" id=AppntRgtAddress name="AppntRgtAddress" verify="投保人户口所在地|len<=80" >
          </TD>
         <TD  class= title>
		        职业
		      </TD>
		      <TD  class= input>
		        <Input class="common wid" id=AppntWorkType name="AppntWorkType"  value="" >
		      </TD>
        <!--  <TD  class= title>
          	民族
          </TD>
          <TD  class= input>
          <input class="codeno" id= name="AppntNationality" verify="投保人民族|code:Nationality" ondblclick="return showCodeList('Nationality',[this,AppntNationalityName],[0,1]);" onkeyup="return showCodeListKey('Nationality',[this,AppntNationalityName],[0,1]);"><input class=codename id= name=AppntNationalityName readonly=true>
          </TD>
          -->
        </TR>

        <TR  class= common>
          <TD  class= title>
            职业代码
          </TD>
          <TD  class= input>
            <Input class=codeno id=AppntOccupationCode name=AppntOccupationCode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
            ondblclick="return showCodeList('OccupationCode',[this,AppntOccupationCodeName,AppntOccupationType,AppntOccupationTypeName],[0,1,2,3],null,null,null,null,'240');"
            ><input class=codename id=AppntOccupationCodeName name=AppntOccupationCodeName readonly=true>
          </TD>
          <TD  class= title>
            职业类别
          </TD>
          <TD  class= input>
            <Input class=codeno id=AppntOccupationType name=AppntOccupationType ><input class=codename id=AppntOccupationTypeName name=AppntOccupationTypeName readonly=true>
          </TD>
         <TD  class= title>
            兼职
          </TD>
          <TD  class= input>
            <Input class="common wid" id=PluralityType name=PluralityType >
          </TD>
         <!-- <TD  class= title>
            是否吸烟
          </TD>
          <TD  class= input>
            <Input class="codeno" id=AppntSmokeFlag name="AppntSmokeFlag" verify="投保人是否吸烟|code:YesNo" ondblclick="return showCodeList('YesNo',[this,AppntSmokeFlagName],[0,1]);" onkeyup="return showCodeListKey('YesNo',[this,AppntSmokeFlagName],[0,1]);"><input class=codename id=AppntSmokeFlagName name=AppntSmokeFlagName readonly=true>
          </TD>
         -->
        </TR>
          <!--      <TR>
          <TD  class= title>
            地址代码
          </TD>
          <TD  class= input>
            <Input class="codeno" id=AddressNo name="AddressNo"  ondblclick="getaddresscodedata();return showCodeListEx('GetAddressNo',[this,AddressNoName],[0,1],'', '', '', true);" onkeyup="getaddresscodedata();return showCodeListKeyEx('GetAddressNo',[this,AddressNoName],[0,1],'', '', '', true);"><input class=codename id=AddressNoName name=AddressNoName readonly=true>
          </TD>
            <TD class = title>

            </TD>
            <TD class = input>

            </TD>
             <TD  class= title>

          </TD>
          <TD  class= input>

          </TD>
                </TR>
                -->
<!--
        <tr class='common'>
          <td class="title">
            通讯地址：
          </td>
         <TD  class= input>
            <Input class="codeno" verify="投保人省份|len=6"   id= name="AppntProvince"  ondblclick="return showCodeList('province',[this,AppntProvinceName],[0,1]);" onkeyup="return showCodeListKey('Province',[this,AppntProvinceName],[0,1]);"><input class=codename id= name=AppntProvinceName readonly=true elementtype=nacessary>
          </TD>
        </tr>

        <TR  class= common>
          <TD  class= title>
            省
          </TD>
          <TD  class= input>
            <Input class="codeno" verify="投保人省份|len=6"   id= name="AppntProvince"  ondblclick="return showCodeList('province',[this,AppntProvinceName],[0,1]);" onkeyup="return showCodeListKey('Province',[this,AppntProvinceName],[0,1]);"><input class=codename id= name=AppntProvinceName readonly=true elementtype=nacessary>
          </TD>
          <TD  class= title>
            市
          </TD>
          <TD  class= input>
            <Input class="codeno" verify="投保人城市|len=6"   id= name="AppntCity"  ondblclick="showCodeList('city',[this,AppntCityName],[0,1],null,[fm.AppntProvince.value],['upplacename']);" onkeyup="return showCodeListKey('City',[this,AppntCityName],[0,1],null,[fm.AppntProvince.value],['upplacename']);"><input class=codename id= name=AppntCityName readonly=true elementtype=nacessary>
          </TD>
          <TD  class= title>
            区/县
          </TD>
          <TD  class= input>
            <Input class="codeno" verify="投保人区/县|len=6"   id= name="AppntDistrict"  ondblclick="showCodeList('district',[this,AppntDistrictName],[0,1],null,[fm.AppntCity.value],['upplacename']);" onkeyup="return showCodeListKey('District',[this,AppntDistrictName],[0,1],null,[fm.AppntCity.value],['upplacename']);"><input class=codename id= name=AppntDistrictName readonly=true elementtype=nacessary>
          </TD>
        </TR>
-->
        <TR  class= common>
          <TD  class= title>
            通讯地址
          </TD>
          <TD  class= input colspan=3 >
            <Input class= "common3" id=PostalAddress name="PostalAddress" verify="投保人通讯地址|notnull&len<=80"   elementtype=nacessary>
          </TD>
          <TD  class= title>
            通讯地址邮政编码
          </TD>
          <TD  class= input>
            <Input class= "common wid"  id=ZipCode name="ZipCode" verify="投保人通讯地址邮政编码|zipcode&notnull&len=6" elementtype=nacessary  >
          </TD>

        </TR>
        <TR  class= common>
          <TD  class= title>
            住址
          </TD>
          <TD  class= input colspan=3 >
            <Input class= "common3" id=HomeAddress name="HomeAddress" verify="投保人住址|len<=80"   >
          </TD>
          <TD  class= title>
            住址邮政编码
          </TD>
          <TD  class= input>
            <Input class= "common wid"  id=HomeZipCode name="HomeZipCode" verify="投保人住址邮政编码|zipcode&len=6"  >
          </TD>

        </TR>
        <tr class=common>
          <TD  class= title>
            首选回访电话(联系电话一)           
          </TD>
          <TD  class= input>
            <Input class="common wid"  id=Mobile name="Mobile" verify="首选回访电话(联系电话一)|TEL&len<=15">
          </TD>
          <TD  class= title>
            其他联系电话(联系电话二)
          </TD>
          <TD  class= input>
            <Input class="common wid"  id=GrpPhone name="GrpPhone" verify="其他联系电话(联系电话二)|TEL&len<=18"   >
          </TD>
          <TD  class= title>
            电子邮箱
          </TD>
          <TD  class= common>
            <Input class="common wid"  id=EMail name="EMail" verify="投保人电子邮箱|EMAIL&LEN<=40"  >
          </TD>
     <!--     <TD  class= title>
            传真电话
          </TD>
          <TD  class= input>
            <Input class="common wid"  id= name="Fax" verify="传真|len<=15"   >
          </TD>
      -->
        </tr>
         <TR  class= common>
          <TD  class= title  >
            工作单位
          </TD>
          <TD  class=input colspan=3 >
            <Input class=common3  id=GrpName name="GrpName" verify="投保人工作单位|notnull&len<=80"   elementtype=nacessary >
          </TD>
       	<TD  class= title>系被保人的</TD>
			  <TD  class= input>
			 	<Input class="codeno" id=RelationToInsured name="RelationToInsured" verify="与被保人关系|NOTNULL&CODE:Relation" verifyorder='1' style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('Relation', [this,RelationToInsuredName],[0,1]);" onkeyup="return showCodeListKey('Relation', [this,RelationToInsuredName],[0,1]);"><Input class=codename id=RelationToInsuredName name=RelationToInsuredName readonly=true elementtype=nacessary>
		   	</TD>           
        </TR>
    <!--    <TR  class= common>
          <TD  class= title>
            住宅电话
          </TD>
          <TD  class= common>
          <input class="common wid"  id= name="HomePhone" verify="投保人家庭电话|len<=18" >
          </TD>
          <TD  class= title>
            工作单位
          </TD>
          <TD  class= common>
            <Input class=common  id= name="GrpName" verify="投保人工作单位|notnull&len<=60"   elementtype=nacessary >
          </TD>
          <TD  class= title>
            电子邮箱
          </TD>
          <TD  class= common>
            <Input class="common wid"  id= name="EMail" verify="投保人电子邮箱|EMAIL&LEN<=40"  >
          </TD>
        </TR>
       --> 
          <TR class = common >
          <TD  class= title >开户银行</TD>
          <TD  class= input >
                <Input class="codeno" id=GetBankCode name="GetBankCode" verify="开户行|CODE:bank" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeList('bank',[this, BankName], [0, 1]);"onkeyup="showCodeListKey('bank', [this, BankName], [0, 1]);"><input class=codename id=BankName name=BankName readonly=true>
          </TD>
          <TD  class= title >
          银行帐户
          </TD>
          <TD  class= input >
              <input type="text" class="coolConfirmBox wid" id=GetBankAccNo name="GetBankAccNo">
            </TD>
           <TD  class= title >
          户   名
          </TD>
          <TD  class= input >
                 <Input class="common wid"  id=GetAccName name="GetAccName">
          </TD>
        </TR>
      </table>
    </Div>
<TABLE>
    <TR>
        <TD class=common>
            <IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divInsured);">
        </TD>
        <TD class = titleImg>
            相关被保人信息
        </TD>
    </TR>
</TABLE>
<Div id = "divInsured" style= "display: ''">
    <TABLE class= common>
        <TR class= common>
            <TD text-align:left colSpan = 1>
                <span id = "spanInsuredGrid"></span>
            </TD>
        </TR>
    </TABLE>
</Div>
<Div id= "divCustomer" style= "display: ">
<TABLE>
    <TR>
      <TD class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCImpart1);">
      </td>
      <TD class= titleImg>
        新投保人健康告知
      </TD>
   </TR>
   </TABLE>

<Div  id= "divLCImpart1" style= "display: ''">
        <table  class= common>
            <tr  class= common>
                <td text-align: left colSpan=1>
                    <span id="spanImpartGrid" >
                    </span>
                </td>
            </tr>
        </table>
</Div>
</Div>

<br>

<Div  id= "divLRInfo" style= "display: none">
      <table  class= common>
        <TR  class = common>
            <TD  class= title> 保单补发次数 </TD>
            <TD  class= input ><input class="common wid" type="text" id=LostTimes name="LostTimes"></TD>
			<TD  class= title></TD>
            <TD  class= input></TD>
			<TD  class= title></TD>
            <TD  class= input></TD>
            <input type=hidden id="TrueLostTimes" name="TrueLostTimes">
        </TR>
     </table>
</Div>
 
<Div id="divEdorquery" style="display: ''">
     <Input class= cssButton type=Button value=" 保 存 " onclick="edorTypeAESave()">
   <Input class= cssButton type=Button value=" 重 置 " onclick="resetForm()">
   <Input type=Button class= cssButton value=" 返 回 " onclick="returnParent()">
   <Input class= cssButton TYPE=button VALUE="记事本查看" onclick="showNotePad();">
</Div>
     <input type=hidden id="ContType" name="ContType">
     <input type=hidden id="EdorNo" name="EdorNo">
     <input type=hidden id="addrFlag" name="addrFlag">
     <input type=hidden id="AddressNo" name="AddressNo">
     <input type=hidden id="fmtransact" name="fmtransact">

     </FORM>
	 <br /><br /><br /><br />
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</BODY>
 <script language="javascript">
    var splFlag = "<%=request.getParameter("splflag")%>";

</script>
</HTML>
