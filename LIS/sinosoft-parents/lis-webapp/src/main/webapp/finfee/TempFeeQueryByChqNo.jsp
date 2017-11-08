<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>

<html>
<head>
<title>暂交费信息查询</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js" type="text/javascript" ></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <script src="./TempFeeQueryByChqNo.js"></script>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="TempFeeQueryByChqNoInit.jsp"%>


</head>
<body  onload="initForm();">
<!--登录画面表格-->
<form name=fm id=fm action=./TempFeeQueryResult.jsp target=fraSubmit method=post>
<script>
	var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
</script>
     <table>
      <tr>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" >
        </td>
        <td class= titleImg>
          请输入查询条件：         
        </td>
      </tr>
    </table>
	<div class=maxbox1>
     <table  class= common >
      	<TR  class= common>
      	  <TD  class= title>
             收费机构
          </TD>
          <TD  class= input>
			<Input class=codeno name=ManageCom id=ManageCom verify="收费机构|code:comcode" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('comcode',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1]);" >
			<input class=codename name=ManageComName id=ManageComName readonly=true>
		  </TD>
		  <!--<TD  class= title>
             收费机构
          </TD>          
          <TD  class= input>
							<Input class="readonly" name=ManageCom readonly=true >				 
		  </TD> -->
		  <!--<TD  class= title>
            收费机构
          </TD>
		  <TD class=input>
			     <Input class=codeno name=ManageCom verify="收费机构|notnull&code:station" ondblclick="return showCodeList('Station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName readonly=true>
		  </TD>-->
          <TD  class= title>
            管理机构
          </TD>
          <TD  class= input>
				    <Input class=codeno name=PolicyCom id=PolicyCom style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('comcode',[this,PolicyComName],[0,1]);" onkeyup="return showCodeListKey('comcode',[this,PolicyComName],[0,1]);" >
					<input class=codename name=PolicyComName id=PolicyComName readonly=true>
          </TD>
          <!--<TD  class= title>
             管理机构
          </TD>          
          <TD  class= input>
							<Input class="readonly" name=PolicyCom readonly=true >				 
		  </TD>-->
		  <!--<TD  class= title>
            管理机构
          </TD>
		  <TD class=input>
			     <Input class=codeno name=PolicyCom verify="管理机构|notnull&code:station" ondblclick="return showCodeList('Station',[this,PolicyComName],[0,1]);" onkeyup="return showCodeListKey('comcode',[this,PolicyComName],[0,1]);"><input class=codename name=PolicyComName readonly=true>
		  </TD>-->
          <TD class=title>
            交费方式
          </TD>
          <TD class=input>
            <Input class=code name=PayMode id=PayMode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('paymodequery',[this],null,null,null,null,1);" onkeyup="return showCodeListKey('paymodequery',[this],null,null,null,null,1);">
          </TD>


          <!--TD  class= title>
            险种
          </TD>
          <TD  class= input>
            <Input class= code name=RiskCode ondblclick="return showCodeList('RiskCode',[this]);" onkeyup="return showCodeListKey('RiskCode',[this]);">
          </TD-->
       </TR>
      	<TR  class= common>
          <TD  class= title>
          交费开始日期
          </TD>
          <TD  class= input>
            <Input class="coolDatePicker" dateFormat="short" name=StartDate onClick="laydate({elem: '#StartDate'});" id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD  class= title>
          交费结束日期
          </TD>
          <TD  class= input>
            <Input class="coolDatePicker" dateFormat="short" name=EndDate onClick="laydate({elem: '#EndDate'});" id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD  class= title>
          操作员
          </TD>
          <TD  class= input>
            <Input class="common wid" name=Operator id=Operator >
          </TD>
       </TR>
       <TR  class= common>
          <TD  class= title>
          最小金额
          </TD>
          <TD  class= input>
            <Input class="common wid" name=MinMoney id=MinMoney >
          </TD>

          <TD  class= title>
            最大金额
          </TD>
          <TD class= input>
            <Input class="common wid" name=MaxMoney id=MaxMoney >
          </TD>
          <TD class = title>
            其他号码
          </TD>
          <TD class=input>
            <Input class="common wid" name=PrtNo id=PrtNo>
          </TD>
       </TR>

       <TR  class= common>
          <TD  class= title>
          到账开始日期
          </TD>
          <TD  class= input>
            <Input class="coolDatePicker" dateFormat="short" name=EnterAccStartDate onClick="laydate({elem: '#EnterAccStartDate'});" id="EnterAccStartDate"><span class="icon"><a onClick="laydate({elem: '#EnterAccStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD  class= title>
          到账结束日期
          </TD>
          <TD  class= input>
            <Input class="coolDatePicker" dateFormat="short" name=EnterAccEndDate onClick="laydate({elem: '#EnterAccEndDate'});" id="EnterAccEndDate"><span class="icon"><a onClick="laydate({elem: '#EnterAccEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD  class= title>
          暂收据号
          </TD>
          <TD  class= input>
            <Input class="common wid" name=TempFeeNo id=TempFeeNo >
          </TD>
        </TR>

       <TR  class= common>
          <TD  class= title>
             交费类型
          </TD>
          <TD  class= input>
             <Input class=codeno name=PayType id=PayType style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('paytype',[this,PayTypeName],[0,1]);" onkeyup="return showCodeListKey('paytype',[this,PayTypeName],[0,1]);">
			 <input class=codename name=PayTypeName id=PayTypeName readonly=true>
          </TD>
          <TD  class= title>
          	暂交费状态
          </TD>
          <TD class= input>
            <Input class= code name=TempFeeStatus1 id=TempFeeStatus1  CodeData="0|^0|到账^1|未到账^2|核销^3|未核销^4|全部" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblClick="showCodeListEx('TempFeeStatus',[this],[0,1,2,3,4,5,6]);" onkeyup="showCodeListKeyEx('TempFeeStatus',[this],[0,1,2,3,4,5,6]);">
          </TD>
          <TD class = title>
          </TD>
          <TD class=input>
          </TD>
       </TR>

      </table>

      <div id=divBankCode style="display:none">
        <table class=common align=center>
        <TR class=common>
          <TD class=title>
            开户银行
          </TD>
    			<TD class=input>
					<Input class=codeno name=BankCode9 id=BankCode9 style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('HeadOffice',[this,BankCode9Name],[0,1],null,'3','Branchtype',1);" onkeyup="return showCodeListKey('HeadOffice',[this,BankCode9Name],[0,1],[0,1],null,'3','Branchtype','1');">
					<input class=codename name=BankCode9Name id=BankCode9Name readonly=true>
								
    			</TD>
          <TD class=title>

          </TD>
          <TD class=input>

          </TD>
          <TD class=title>

          </TD>
          <TD class=input>

          </TD>
         </table>
        </TR>

      </div>

        <div id=divBankCode7 style="display:none">
        	<table  class= common align=center>
        	<TR class=common>
            <TD class=title>
            开户银行
            </TD>
    			  <TD class=input>
					<Input class=codeno name=BankCode7 id=BankCode7 style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('bank',[this,BankCode7Name],[0,1]);" onkeyup="return showCodeListKey('finbank',[this,BankCode7Name],[0,1]);">
					<input class=codename name=BankCode7Name id=BankCode7Name readonly=true>
    			  </TD>
    			<TD class=title>

          </TD>
          <TD class=input>

          </TD>
          <TD class=title>

          </TD>
          <TD class=input>

          </TD>
        	</TR>
          </table>
        </div>

        <div id=InBankCode style='display:none'>
        	<table  class= common align=center>
        	<TR class=common>
            <TD class=title>
            开户银行
            </TD>
    			  <TD class=input>
					<Input class=codeno name=InBankCode id=InBankCode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('comtobank',[this,InBankCodeName],[0,1]);" onkeyup="return showCodeListKey('comtobank',[this,InBankCodeName],[0,1]);">
					<input class=codename name=InBankCodeName id=InBankCodeName readonly=true>
    			  </TD>
    			<TD class=title>

          </TD>
          <TD class=input>

          </TD>
          <TD class=title>

          </TD>
          <TD class=input>

          </TD>
        	</TR>
          </table>
        </div>

      <div id=divChequeNo style='display:none'>
      <table class=common align=center>
        <TR class=common>
          <TD class=title>
            支票号码
          </TD>
          <TD class=input>
            <Input class="common wid" name=ChequeNo id=ChequeNo>
          </TD>
          <TD class=title>
            支票日期
          </TD>
          <TD class=input>
            <Input class="common wid" name=ChequeDate id=ChequeDate>
          </TD>

        </TR>

      </table>
      </div>

      <table align=right>
        <TR >

          <TD >
            <INPUT VALUE="查  询" Class=cssButton TYPE=button onclick="easyQueryClick();">
          </TD>

       </TR>
      </Table>
   <BR><BR>
     <INPUT VALUE="返回" Class=cssButton TYPE=button onclick="returnParent();">


    <Table>
    	<TR>
        	<TD class=common>
	           <IMG  src= "../common/images/butExpand.gif" style= "cursorhand;" OnClick= "showPage(this,divTempFee1);">
    		</TD>
    		<TD class= titleImg>
    			 暂交费信息
    		</TD>
    	</TR>
    </Table>
 <Div  id= "divTempFee1" style= "display ''">
   <Table  class= common>
       <TR  class= common>
        <TD text-align left colSpan=1>
            <span id="spanTempQueryByChqNoGrid" ></span>
  	</TD>
      </TR>
    </Table>

	<center>
      <INPUT CLASS=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage.firstPage();">
      <INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();">
      <INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();">
      <INPUT CLASS=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();">
    </center>
    <table class=common>
          <TD  class= title>
          	金额统计
          </TD>
          <TD  class= input>
          	<Input class="readonly wid" name=SumMoney id=SumMoney readonly=true>
          </TD>
          <TD  class= title>

          </TD>
          <TD  class= input>

          </TD>
          <TD  class= title>

          </TD>
          <TD  class= input>

          </TD>

      <tr>
        <td>
          <INPUT CLASS=cssButton VALUE="打  印" TYPE=button onclick="easyQueryPrint(2,'TempQueryByChqNoGrid','turnPage');">
        </td>
      </tr>
      <tr>
        <TD >
            <INPUT VALUE="   导出收费明细   " Class=cssButton TYPE=button onclick="SFDetailExecel();">
        </TD>
        <!--TD >
            <INPUT VALUE="导出新契约承保明细" Class=cssButton TYPE=button onclick="NCDetailExecel();">
        </TD-->
      </tr>

      <!------------------------------------------------Beg-->
	  <!--添加该清单或报表的说明-->
	  <!--修改人：chenrong-->
	  <!--修改日期：2006-02-14-->
      <tr>
         <td colspan=6 class=common>
         	 <Table>
		        <TR>
		            <TD class=common>
		            	<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivShow);">
		            </TD>
		            <TD class= titleImg>用途说明:</TD>
		        </TR>
		    </Table>
		    <Div id= "DivShow" class=maxbox1 style= "display: ''">
		        <Table class=common>
		          <TR class=common>
		          	列出查询时间段内按交费方式区分的交费方式查询
		          </TR>
		        </Table>
	        </Div>
		  </td>
      </tr>
      <!--------------------------------------------End-->

    </table>

    <input type=hidden id="fmAction" name="fmAction">
 </Div>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
<br /><br /><br /><br />
</Form>
</body>
</html>

