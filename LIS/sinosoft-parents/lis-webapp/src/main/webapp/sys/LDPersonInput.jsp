<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
//程序名称：LDPersonInput.jsp
//程序功能：客户管理（新增客户）
//创建日期：2005-06-20
//创建人  ：wangyan
//更新记录：更新人    更新日期     更新原因/内容
%>

<html> 
<head >
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>  
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT> 
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>  
  <SCRIPT src="LDPersonInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="LDPersonInit.jsp"%>
</head>

<body  onload="initForm();initElementtype();" >
  <form action="./LDPersonSave.jsp" method=post name=fm id=fm target="fraSubmit">


    <table>
      <tr>
	      <td class=common>
	        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLDPersonBasic);">
	      </td>
	      <td class= titleImg>
	        客户基本信息
	      </td>
	  </tr>
    </table>

    <Div  id= "divLDPersonBasic" style= "display: ''" class=maxbox>    
      <table  class= common>
        <TR  class= common>           
			    <TD  class= title>
			      客户号码
			    </TD>
			    <TD  class= input>
			      <Input class="readonly wid" name=CustomerNo id=CustomerNo readonly>
			    </TD>
			    <TD  class= title>
			      姓名
			    </TD>
			    <TD  class= input>
			      <Input class= "common wid" name=Name id=Name elementtype=nacessary verify="客户姓名|NOTNULL">
			    </TD>
			    <TD  class= title>
			      性别
			    </TD>
			    <TD  class= input>
			      <Input class="codeno" name=AppntSex id=AppntSex style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" verify="投保人性别|notnull&code:Sex" ondblclick="return showCodeList('Sex',[this,AppntSexName],[0,1]);" onkeyup="return showCodeListKey('Sex',[this,AppntSexName],[0,1]);"><input class=codename name=AppntSexName id=AppntSexName readonly=true elementtype=nacessary>
			    </TD>
			  </TR>
			  
			  <TR  class= common>
			  	<TD  class= title>
			      出生日期
			    </TD>
			    <TD  class= input>
			      <Input class="coolDatePicker" elementtype=nacessary  verify="客户出生日期|DATE&NOTNULL" dateFormat="short" name=Birthday onClick="laydate({elem: '#Birthday'});" id="Birthday"><span class="icon"><a onClick="laydate({elem: '#Birthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
			    </TD>
			    <TD  class= title>
			      证件类型
			    </TD>
			    <TD  class= input>
			      <Input class="codeno" name="AppntIDType" id=AppntIDType style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
 verify="投保人证件类型|code:IDType" ondblclick="return showCodeList('IDType',[this,AppntIDTypeName],[0,1]);" onkeyup="return showCodeListKey('IDType',[this,AppntIDTypeName],[0,1]);"><input class=codename name=AppntIDTypeName id=AppntIDTypeName readonly=true>
			    </TD>
			    <TD  class= title>
			      证件号码
			    </TD>
			    <TD  class= input>
			      <Input class= "common  wid" name=IDNo id=IDNo >
			    </TD>
			  </TR>
			 
			  <TR  class= common>
			    <TD  class= title>
			    	其它证件类型
			    </TD>
			    <TD  class= input>
			   		<Input class="codeno" name="OthIDType" id=OthIDType style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
 verify="证件类型|code:IDType" ondblclick="return showCodeList('IDType',[this,OthIDTypeName],[0,1]);" onkeyup="return showCodeListKey('IDType',[this,OthIDTypeName],[0,1]);"><input class=codename name=OthIDTypeName id=OthIDTypeName readonly=ture>                                                                                                                   
			    </TD>
			    
			     <TD  class= title>
			      其它证件号码
			    </TD>
			    <TD  class= input>
			      <Input class= "common wid" name=OthIDNo id=OthIDNo >
			    </TD>
			    <TD  class= title>
			      学历
			    </TD>
			    <TD  class= input>
			      <Input class="codeno" name="DegreeState" id=DegreeState style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" verify="学历|code:Degree" ondblclick="return showCodeList('Degree',[this,DegreeStateName],[0,1]);" onkeyup="return showCodeListKey('Degree',[this,DegreeStateName],[0,1]);"><input class=codename name=DegreeStateName id=DegreeStateName readonly=ture>
			    </TD>
			  </TR>
			  <TR class=common>
			    <TD  class= title>
			      国籍
			    </TD>
			    <TD  class= input>
			      <input class="codeno" name="AppntNativePlace" id=AppntNativePlace style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
 verify="投保人国籍|code:NativePlace" ondblclick="return showCodeList('NativePlace',[this,AppntNativePlaceName],[0,1]);" onkeyup="return showCodeListKey('NativePlace',[this,AppntNativePlaceName],[0,1]);"><input class=codename name=AppntNativePlaceName id=AppntNativePlaceName readonly=true>
			    </TD>
			    <TD  class= title>
			      民族
			    </TD>
			    <TD  class= input>
			      <input class="codeno" name="AppntNationality" id=AppntNationality style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
 verify="投保人民族|code:Nationality" ondblclick="return showCodeList('Nationality',[this,AppntNationalityName],[0,1]);" onkeyup="return showCodeListKey('Nationality',[this,AppntNationalityName],[0,1]);"><input class=codename name=AppntNationalityName id=AppntNationalityName readonly=true>
			    </TD>
			     <TD  class= title>
			      状态
			    </TD>
			    <TD  class= "common" >
			      <Input class= "readonly wid" readonly name=State id=State >
			   </TR>
			   
			   <TR  class= common>
			  	 <TD  class= title>
			      婚姻状况
			    </TD>
			    <TD  class= input>
			      <Input class="codeno" name="AppntMarriage" id=AppntMarriage style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
 verify="投保人婚姻状况|code:Marriage" ondblclick="return showCodeList('Marriage',[this,AppntMarriageName],[0,1]);" onkeyup="return showCodeListKey('Marriage',[this,AppntMarriageName],[0,1]);"><input class=codename name=AppntMarriageName id=AppntMarriageName readonly=true>
			    </TD>
			    <TD  class= title>
			      健康状况
			    </TD>
			    <TD  class= input>
			       <Input class="codeno" name="HealthState" id=HealthState style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
 verify="健康状况|code:Health" ondblclick="return showCodeList('Health',[this,HealthStateName],[0,1]);" onkeyup="return showCodeListKey('Health',[this,HealthStateName],[0,1]);"><input class=codename name=HealthStateName id=HealthStateName readonly=ture>                                                                          
			    </TD>
			    <TD  class= title>
			      是否吸烟标志
			    </TD>
			    <TD  class= input>
						<Input class="codeno" name="SmokeFlag" id=SmokeFlag style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
 verify="吸烟标识|code:YesNo" ondblclick="return showCodeList('YesNo',[this,SmokeFlagName],[0,1]);" onkeyup="return showCodeListKey('YesNo',[this,SmokeFlagName],[0,1]);"><input class=codename name=SmokeFlagName id=SmokeFlagName readonly=ture> 
			    </TD>
			  </TR>
			  
			  
			  <TR  class= common>
			    <TD  class= title>
			      灰名单标记
			    </TD>
			    <TD  class= input>
			      <Input class="codeno" name="listFlag" id=listFlag style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
 verify="灰名单标识|code:YesNo" ondblclick="return showCodeList('YesNo',[this,listFlagName],[0,1]);" onkeyup="return showCodeListKey('YesNo',[this,listFlagName],[0,1]);"><input class=codename name=listFlagName id=listFlagName readonly=ture> 
			    </TD>
			   <TD  class= title>
			      VIP值
			    </TD>
			    <TD  class= input>
	          <Input class="codeno" name="VIPValueState" id=VIPValueState style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
 verify="VIP标识|code:VIPValue" ondblclick="return showCodeList('VIPValue',[this,VIPValueStateName],[0,1]);" onkeyup="return showCodeListKey('VIPValue',[this,VIPValueStateName],[0,1]);"><input class=codename name=VIPValueStateName id=VIPValueStateName readonly=ture>
			    <TD  class= title>
			      
			    </TD>
			    <TD  class= input>
			      <Input class="readonly wid" name= readonly id=readonly>
	          
			  </TR>              
			</Table>
			
			<table class=common>
    <tr  class= common> 
      <td  class= common>备注</TD>
    </tr>
    <tr  class= common>
      <td  class= common>
        <textarea name="Remark" id=Remark verify="其他声明|len<800" verifyorder="1" cols="110" rows="2" class="common wid" >
        </textarea>
      </td>
    </tr>
  </table>
		</Div>
			    
    
    <table>
      <tr>
	      <td class=common>
	        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLDPersonAddress);">
	      </td>
	      <td class= titleImg>
	        客户联系方式
	      </td>
	  	</tr>
    </table>

    <Div  id= "divLDPersonAddress" style= "display: ''" class=maxbox>    
      <table  class= common>
				
        
        <TR  class= common>
          <TD  class= title>
            省
          </TD>
          <TD  class= input>
            <Input class="codeno" verify="投保人省份|num&LEN=2"  verifyorder="1" name="AppntProvince" id=AppntProvince style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
  ondblclick="return showCodeList('province',[this,AppntProvinceName],[0,1]);" onkeyup="return showCodeListKey('Province',[this,AppntProvinceName],[0,1]);"><input class=codename name=AppntProvinceName id=AppntProvinceName readonly=true>
          </TD>  
          <TD  class= title>
            市
          </TD>
          <TD  class= input>
            <Input class="codeno" verify="投保人城市|num&LEN=4"  verifyorder="1" name="AppntCity" id=AppntCity style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
  ondblclick="showCodeList('city',[this,AppntCityName],[0,1],null,[fm.AppntProvince.value],['upplacename']);" onkeyup="return showCodeListKey('City',[this,AppntCityName],[0,1],null,[fm.AppntProvince.value],['upplacename']);"><input class=codename name=AppntCityName id=AppntCityName readonly=true>
          </TD>  
          <TD  class= title>
            区/县
          </TD>
          <TD  class= input>
            <Input class="codeno" verify="投保人区/县|num&LEN=6"  verifyorder="1" name="AppntDistrict" id=AppntDistrict style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"  ondblclick="showCodeList('district',[this,AppntDistrictName],[0,1],null,[fm.AppntCity.value],['upplacename'],null,'240');" onkeyup="return showCodeListKey('District',[this,AppntDistrictName],[0,1],null,[fm.AppntCity.value],['upplacename'],null,'240');"><input class=codename name=AppntDistrictName id=AppntDistrictName readonly=true>
          </TD>  
        </TR>        
         
        <TR  class= common>
          <TD  class= title>
            街道
          </TD>
          <TD  class= input colspan=3 >
            <Input class="common3 wid" name="AppntPostalAddress" id=AppntPostalAddress verify="投保人联系地址|LEN<=80"  verifyorder="1" >
          </TD>
          <TD  class= title>
            邮政编码
          </TD>
          <TD  class= input>
            <Input class="common wid" name="AppntZipCode" id=AppntZipCode verify="投保人邮政编码|zipcode" verifyorder="1" >
          </TD>
          <TD  class= title> </TD>
          <TD  class= title> </TD>
        </TR>        
        <tr class=common>
          <TD  class= title>
            移动电话
          </TD>
          <TD  class= input>
            <Input class= "common wid" name="AppntMobile" id=AppntMobile verify="投保人移动电话|LEN<=15&PHONE"  verifyorder="1"  >
          </TD>         
          <TD  class= title>
            办公电话
          </TD>
          <TD  class= input>
            <Input class= "common wid" name="AppntGrpPhone" id=AppntGrpPhone verify="投保人办公电话|LEN<=15&PHONE"  verifyorder="1" >
          </TD>       
          <TD  class= title>
            传真电话
          </TD>
          <TD  class= input>
            <Input class= "common wid" name="AppntFax" id=AppntFax verify="投保人传真电话|LEN<=15&PHONE"  verifyorder="1" >
          </TD>           

        </tr>
        <TR  class= common>
          <TD  class= title>
            住宅电话
          </TD>
          <TD  class= input>
          <input class= "common wid" name="AppntHomePhone" id=AppntHomePhone verify="投保人住宅电话|LEN<=18&PHONE"  verifyorder="1" >
          </TD>
          <TD  class= title>
            工作单位
          </TD>
          <TD  class= input>
            <Input class="common wid" name="AppntGrpName" id=AppntGrpName verify="投保人工作单位|LEN<=60"  verifyorder="1" >
          </TD>
          <TD  class= title>
            电子邮箱
          </TD>
          <TD  class= input>
            <Input class= "common wid" name="AppntEMail" id=AppntEMail verify="投保人电子邮箱|EMAIL&LEN<=40"  verifyorder="1" >
          </TD>  
        </TR>         
			</table>
		</Div>   
    
    <table>
      <tr>
	      <td class=common>
	        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLDPersonAccount);">
	      </td>
	      <td class= titleImg>
	        客户账户信息
	      </td>
	  	</tr>
    </table>

    <Div  id= "divLDPersonAccount" style= "display: ''" class=maxbox1>    
      <table  class= common>  
			  <TR  class= common>      
			    
			    <TD  class= title>
			      银行编码
			    </TD>    
			    <TD  class= input>
			      <Input  name=BankCodeState id=BankCodeState style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" CLASS="codeno" MAXLENGTH=20 verify="开户行|code:bank" ondblclick="return showCodeList('bank',[this,BankCodeStateName],[0,1]);" onkeyup="return showCodeListKey('bank',[this,BankCodeStateName],[0,1]);"><input class=codename name=BankCodeStateName id=BankCodeStateName readonly=ture> 
			    </TD>
			    <TD  class= title>
			      银行帐号
			    </TD>
			    <TD  class= input>
			      <Input class=" common wid" name=GetBankAccNo id=GetBankAccNo style="background:url(../common/images/select--bg_03.png) no-repeat right center" type="text"  onkeyup="return confirmSecondInput(this,'onkeyup');"  onblur="return confirmSecondInput(this,'onblur');">
			    </TD>
			    <TD  class= title>
			      银行帐户名
			    </TD>
			    <TD  class= input><Input class= "common wid" name=AccName id=AccName verify="银行帐户名|len<=20" >
			    </TD>
			    </TR>     
			   </table>
    </Div>
      <INPUT class=cssButton VALUE="重  置"  TYPE=button onclick="return resetForm();">
      <INPUT class=cssButton VALUE="保  存"  TYPE=button onclick="return submitForm();"> 
    </Div>        
    <input type=hidden name="Transact">
    
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br/><br/><br/><br/>
</body>
</html>
