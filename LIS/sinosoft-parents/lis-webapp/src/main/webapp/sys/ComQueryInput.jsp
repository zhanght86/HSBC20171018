<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：ComQueryInput.jsp
//程序功能：
//创建日期：2002-08-16 17:44:45
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<html>
<head>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="./ComQuery.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="./ComQueryInit.jsp"%>
<title>机构信息 </title>
</head>
<body  onload="initForm();" >
  <form action="./ComQuerySubmit.jsp" method=post name=fm id=fm target="fraSubmit">
  <table>
      <tr>
      <td class="common">
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divComGrid1);">
      </td>
      <td class= titleImg>
        请您输入查询条件：
      </td>
    	</tr>
    </table>
  <Div  id= "divComGrid1" style= "display: ''" class="maxbox">
  <table  class= common>
        <TR  class= common>
          <TD  class= title5>
            管理机构代码
          </TD>
          <TD  class= input5>
            <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class='codeno' name=ComCode id=ComCode ondblclick="return showCodeList('comcode',[this,ComCodeName],[0,1],null,null,null,1);" onclick="return showCodeList('comcode',[this,ComCodeName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('comcode',[this,ComCodeName],[0,1],null,null,null,1);" ><input name=ComCodeName id=ComCodeName class="codename"  readonly=true> 

          </TD>
          <TD  class= title5>
            对外公布的机构代码
          </TD>
          <TD  class= input5>
            <Input class="wid" class=common name=OutComCode id=OutComCode>
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>
            管理机构名称
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=Name id=Name>
          </TD>
          <TD  class= title5>
            短名称
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=ShortName id=ShortName >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>
            级别
          </TD>
          <TD  class= input5>
            <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=ComGrade id=ComGrade ondblclick="return showCodeList('comlevel',[this,ComGradeName],[0,1]);" onclick="return showCodeList('comlevel',[this,ComGradeName],[0,1]);" onkeyup="return showCodeListKey('comlevel',[this,ComGradeName],[0,1]);" ><input name=ComGradeName id=ComGradeName class="codename" elementtype=nacessary readonly=true> 
          </TD>        	
          <TD  class= title5>
          	上级机构
          </TD>
          <TD  class= input5>
          	<Input class="wid" class=common name=UpComCode id=UpComCode>
					</TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>
            保监会下批日期
          </TD>
          <TD  class= input5>
 					<!--<Input class="coolDatePicker" dateFormat="short" name=ConfirmDate >-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#ConfirmDate'});" verify="有效开始日期|DATE" dateFormat="short" name=ConfirmDate id="ConfirmDate"><span class="icon"><a onClick="laydate({elem: '#ConfirmDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
				  </TD>        	
          <TD  class= title5>
          	生效年月(如:200808)
          </TD>
          <TD  class= input5>
          	<Input class="wid" class=common name=YearMonth id=YearMonth >
					</TD>
        </TR>
         <TR  class= common>
          <TD  class= title5>
            停效日期
          </TD>
          <TD  class= input5>
 					<!--<Input class="coolDatePicker" dateFormat="short" name=EndDate >-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="有效开始日期|DATE" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
				  </TD>        	
          <TD  class= title5>
          	有效状态
          </TD>
          <TD  class= input5>
            	<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=ValidFlag id=ValidFlag verify="有效状态|notnull" CodeData="0| ^Y|有效 ^N|无效  "  ondblclick="return showCodeListEx('validflag',[this,ValidFlagName],[0,1]);" onclick="return showCodeListEx('validflag',[this,ValidFlagName],[0,1]);" onkeyup="return showCodeListKeyEx('validflag',[this,ValidFlagName],[0,1]);"><input class=codename name=ValidFlagName id=ValidFlagName readonly=true>
          </TD>
        </TR> 

        <TR  class= common>
        	<TD  class= title5>
            地区类别
          </TD>
          <TD  class= input5>
            <!--<Input class=codeno name=ComAreaType ondblclick="return showCodeList('comareatype',[this,ComAreaTypeName],[0,1]);" onkeyup="return showCodeListKey('comareatype',[this,ComAreaTypeName],[0,1]);" ><input name=ComAreaTypeName class="codename" elementtype=nacessary readonly=true> --> 
            <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=ComCitySize id=ComCitySize ondblclick="return showCodeList('comcitysize',[this,ComCitySizeName],[0,1]);" onclick="return showCodeList('comcitysize',[this,ComCitySizeName],[0,1]);" onkeyup="return showCodeListKey('comcitysize',[this,ComCitySizeName],[0,1]);" ><input name=ComCitySizeName id=ComCitySizeName class="codename" elementtype=nacessary readonly=true> 
            <!--Input class= common name=ComCode -->
          </TD>
          <TD  class= title5>
            机构邮编
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=ZipCode id=ZipCode >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>
            机构地址
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=Address id=Address >
          </TD>
          <TD  class= title5>
            机构电话
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=Phone id=Phone >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>
            机构传真
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=Fax id=Fax >
          </TD>
          <TD  class= title5>
            EMail
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=EMail id=EMail>
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>
            网址
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=WebAddress id=WebAddress >
          </TD>
          <TD  class= title5>
            主管人姓名
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=SatrapName id=SatrapName >
          </TD>
        </TR>
      	<TR>
          <TD  class= title5>
            标志
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=Sign id=Sign >
          </TD>
          <TD  class= title5>
            直属属性
          </TD>
          <TD  class= input5>
            <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=IsDirUnder id=IsDirUnder verify="直属属性|notnull&code:comdirectattr" ondblclick="return showCodeList('comdirectattr',[this,IsDirUnderName],[0,1]);" onclick="return showCodeList('comdirectattr',[this,IsDirUnderName],[0,1]);" onkeyup="return showCodeListKey('comdirectattr',[this,ComAreaTypeName],[0,1]);" ><input name=IsDirUnderName id=IsDirUnderName class="codename" readonly=true> 
          </TD>
        </TR>
      </table>
 </Div>
          <!--<INPUT VALUE="查  询" TYPE=button onclick="easyQueryClick();" class="cssButton">
          <INPUT VALUE="返  回" TYPE=button onclick="returnParent();" class="cssButton">-->
          <a href="javascript:void(0);" class="button" onClick="easyQueryClick();">查    询</a>
          
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divComGrid);">
    		</td>
    		<td class= titleImg>
    			 机构信息结果
    		</td>
    	</tr>
    </table>
  	<Div  id= "divComGrid" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanComGrid" >
  					</span>
  			  	</td>
  			</tr>
    	</table>
        <center>
        <INPUT VALUE="首  页" TYPE=button onclick="turnPage.firstPage();" class="cssButton90">
      <INPUT VALUE="上一页" TYPE=button onclick="turnPage.previousPage();" class="cssButton91">
      <INPUT VALUE="下一页" TYPE=button onclick="turnPage.nextPage();" class="cssButton92">
      <INPUT VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();" class="cssButton93"></center>
  	</div><a href="javascript:void(0);" class="button" onClick="returnParent();">返    回</a>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
