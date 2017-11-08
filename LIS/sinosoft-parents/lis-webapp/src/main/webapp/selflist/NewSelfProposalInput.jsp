<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//程序名称：NewSelfProposalInput.jsp
//程序功能：新自助卡单-客户投保信息录入激活页面
//创建日期：2010-01-25 
//创建人  ：yanglh
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%
	String tCertifyNo = "";
	try
	{
		tCertifyNo = request.getParameter("CertifyNo");
		loggerDebug("NewSelfProposalInput","客户信息录入界面获得卡号="+tCertifyNo);
	}
	catch( Exception e )
	{
		tCertifyNo = "";
	}
	
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");

%>
<head >
<script>
    var tCertifyNo="<%=tCertifyNo%>"
	var Operator = "<%=tGI.Operator%>";   //记录操作员
	var ManageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
</script>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	
	<%@include file="NewSelfProposalInit.jsp"%>
	<SCRIPT src="NewSelfProposalInput.js"></SCRIPT>
	
</head>

<body  onload="initForm(); ">
  <form action="./NewSelfProposalSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <Div  id= "divButton" style= "display: none">
    <%@include file="../common/jsp/OperateButton.jsp"%>
    <%@include file="../common/jsp/InputButton.jsp"%>
    </DIV>
    
    <!-- 保单信息部分 -->
    <table>
    	<tr>
		<td class=common>
			<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAllNotice);">
		</td>
    		<td class= titleImg>保单信息</td>
    	</tr>
    </table>  
    <Div  id= "divALL0" class=maxbox1 >
	 <table  class= common>
        <TR  class= common>
          <TD  class= title>
            待激活卡单卡号
          </TD>
          <TD  class= input>
            <Input class="common wid" name=CertifyNo id=CertifyNo  readonly>
          </TD>
          <TD  class= title>
             指定生效日期     
          </TD>
		  <TD class= input><input type="checkbox" value="01" name="CValiDateType" onclick="callCValiDate(value);">
		  </TD>
          	<TD class= title>生效日期</TD>
          	<TD class= input>
			<input class="coolDatePicker" dateFormat="short" name=CValiDate  onClick="laydate({elem: '#CValiDate'});" id="CValiDate"><span class="icon"><a onClick="laydate({elem: '#CValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
			</TD>
          </TR>  
    </table>

    <!-- 投保人信息部分 -->
    <table>
    	<tr>
    		<td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this, divLCAppnt1)"></td>
    		<td class= titleImg>
    			 投保人信息
    	    </td>
    	</tr>
    </table>
    <Div  id= "divLCAppnt1" style= "display: ''" class=maxbox>
      <table  class= common>
        <TR  class= common>        
          <TD  class= title>
            姓名
          </TD>
          <TD  class= input>
            <Input class="common wid" name=AppntName id=AppntName verify="投保人姓名|notnull&len<=20" >
          </TD>
          <TD  class= title>
            性别
          </TD>
          <TD  class= input>
            <Input class="code" name=AppntSex id=AppntSex  style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="投保人性别|notnull&code:Sex" ondblclick="return showCodeList('Sex',[this]);" onkeyup="return showCodeListKey('Sex',[this]);">
          </TD>
          <TD  class= title>
            出生日期
          </TD>
          <TD  class= input>
          <input class="coolDatePicker" dateFormat="short" name="AppntBirthday" verify="投保人出生日期|notnull&date" onClick="laydate({elem: '#AppntBirthday'});" id="AppntBirthday"><span class="icon"><a onClick="laydate({elem: '#AppntBirthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            证件类型
          </TD>
          <TD  class= input>
            <Input class="code" name="AppntIDType" id=AppntIDType style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="投保人证件类型|code:IDType" ondblclick="return showCodeList('IDType',[this]);" onkeyup="return showCodeListKey('IDType',[this]);">
          </TD>
          <TD  class= title>
            证件号码
          </TD>
          <TD  class= input>
            <Input class="common wid" name="AppntIDNo" id=AppntIDNo verify="投保人证件号码|len<=20" >
          </TD>
          <TD  class= title>
            联系电话
          </TD>
          <TD  class= input>
            <Input class="common wid" name="AppntPhone" id=AppntPhone verify="投保人联系电话|len<=15" >
          </TD>
        </TR>

        <TR  class= common>
          <TD  class= title>
            联系地址
          </TD>
          <TD  class= input colspan=3 >
            <Input class="common3 wid" name="AppntPostalAddress" id=AppntPostalAddress verify="投保人联系地址|len<=80" >
          </TD>
          <TD  class= title>
            邮政编码
          </TD>
          <TD  class= input>
            <Input class="common wid" name="AppntZipCode" id=AppntZipCode verify="投保人邮政编码|zipcode" >
          </TD>
        </TR>
        
        <TR  class= common>
          <!--TD class=title>
             与被保人关系
          </TD>
          <TD  class= input>
            <Input class="code" name=RelationToLCInsured readonly CodeData="0|^00|本人|^03|父亲|^04|母亲|" ondblclick="return showCodeListEx('RelationToLCInsured',[this]);" onkeyup="return showCodeListKeyEx('RelationToLCInsured',[this]);">
          </TD--> 
          
         <TD  class= title>
            电子邮箱
          </TD>
          <TD  class= input>
            <Input class= "common wid" name="AppntEMail" id=AppntEMail verify="投保人电子邮箱|len<=20" >
          </TD>

       </TR>
        </table>
        <table  class= common>
       	 <tr>
		 
<td class=common>
    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAppntOccupation1);">
</td>
       	    <td class= titleImg>
              投保人职业信息(下一行的后两项是模糊查询条件)
    	   </td>
    	</tr> 
    	</table>

	<div id="divAppntOccupation1" style="display: ''" class=maxbox1>
       <table  class= common>
         <TR  class= common>
          <TD  class= title>
            职业类别
          </TD>
          <TD  class= input>
            <Input class="common wid" name="AppntOccupationType" id=AppntOccupationType readonly >
          </TD>
          
          <TD  class= title>
            职业名称
          </TD>
          <TD  class= input>
            <Input class= "common wid" name="AppntOccupationName" id=AppntOccupationName  >
          </TD>
          
          <TD  class= title>
            行业名称
          </TD>
          <TD  class= input>
            <Input class= "common wid" name="AppntWorkName" id=AppntWorkName  >
          </TD>
        </TR>
        
            	
    	<TR class=common>       	
          <td>
         	<INPUT VALUE = "职业模糊查询" class=cssButton type=button onclick="queryAppntOccupation()">
          </td>
          
          <TD  >
            <Input type=hidden name="AppntOccupationCode" readonly >
          </TD>
       </TR>
       </table>
       
    
    <table>
    <tr>
     <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this, divAppntCodeGrid)"></td>
    </tr>
    </table>
    
  	<div id="divAppntCodeGrid" style="display: ''; text-align:center;">
      <table class="common">
        <tr class="common">
      	  <td text-align: left colSpan=1><span id="spanAppntCodeGrid"></span></td></tr>
      </table>
      
      <input VALUE=" 首页 " TYPE="button" class="cssButton90" onclick="turnPage.firstPage();"> 
      <input VALUE="上一页" TYPE="button" class="cssButton91" onclick="turnPage.previousPage();"> 					
      <input VALUE="下一页" TYPE="button" class="cssButton92" onclick="turnPage.nextPage();"> 
      <input VALUE=" 尾页 " TYPE="button" class="cssButton93" onclick="turnPage.lastPage();"> 					
  	</div>
     </div>
     
    </Div>    


    <!--被保人信息,只有与被保人关系中选择为父母时才显示需要客户录入 -->
     <Div  id= "divLCInsure1" style= "display: ''">
      <table>
    	<tr>
    		<td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this, divLCInsure2)"></td>
    		<td class= titleImg>
    			被保人信息(请按照被保人顺序依次录入)
    	    </td>
    	</tr>
      </table>
      
<Div  id= "divLCInsure2" style= "display: ''" class=maxbox>
     
        <table  class= common>
         <TR  class= common>        
          <TD  class= title>
            姓名
          </TD>
          <TD  class= input>
            <Input class="common wid" name=LCInsuredName id=LCInsuredName verify="被保人姓名|notnull&len<=20" >
          </TD>
          <TD  class= title>
            性别
          </TD>
          <TD  class= input>
            <Input class="code" name=LCInsuredSex id=LCInsuredSex verify="被保人性别|notnull&code:Sex" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('Sex',[this]);" onkeyup="return showCodeListKey('Sex',[this]);">
          </TD>
          <TD  class= title>
            出生日期
          </TD>
          <TD  class= input>
          <input class="coolDatePicker" dateFormat="short" name="LCInsuredBirthday" verify="被保人出生日期|notnull&date"  onClick="laydate({elem: '#LCInsuredBirthday'});" id="LCInsuredBirthday"><span class="icon"><a onClick="laydate({elem: '#LCInsuredBirthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            证件类型
          </TD>
          <TD  class= input>
            <Input class="code" name="LCInsuredIDType" style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="被保人证件类型|code:IDType" ondblclick="return showCodeList('IDType',[this]);" onkeyup="return showCodeListKey('IDType',[this]);">
          </TD>
          <TD  class= title>
            证件号码
          </TD>
          <TD  class= input>
            <Input class= "common wid" name="LCInsuredIDNo" id=LCInsuredIDNo verify="被保人证件号码|len<=20" >
          </TD>
          <TD  class= title>
            联系电话
          </TD>
          <TD  class= input>
            <Input class= "common wid" name="LCInsuredPhone" id=LCInsuredPhone verify="被保人联系电话|len<=15" >
          </TD>
        </TR>

        <TR  class= common>
          <TD  class= title>
            联系地址
          </TD>
          <TD  class= input colspan=3 >
            <Input class= "common3 wid" name="LCInsuredPostalAddress" verify="被保人联系地址|len<=80" >
          </TD>
          <TD  class= title>
            邮政编码
          </TD>
          <TD  class= input>
            <Input class= "common wid" name="LCInsuredZipCode" id=LCInsuredZipCode verify="被保人邮政编码|zipcode" >
          </TD>

        </TR>
        
       <TR  class= common>
          <TD  class= title>
            电子邮箱
          </TD>
          <TD  class= input>
            <Input class= "common wid" name="LCInsuredEMail" id=LCInsuredEMail verify="被保人电子邮箱|len<=20" >
          </TD>
          <TD  class= title>与投保人关系</TD>
					<TD  class= input>
							 <Input class=code name=RelationToAppnt id=RelationToAppnt style="background:url(../common/images/select--bg_03.png) no-repeat right center" CodeData="0|^00|本人^01|丈夫^02|妻子^03|父亲^04|母亲^05|儿子^06|女儿" 
					  				ondblClick="showCodeListEx('RelationToAppnt',[this],[0,1]);"onkeyup="showCodeListKeyEx('RelationToAppnt',[this],[0,1]);">
					
				</TD> 
       </TR>
        </div>
        <table  class= common>
       	<tr>
		<td class=common>
    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCInsuredOccupation1);">
</td>
         <td class= titleImg>
            被保人职业信息(下一行的后两项是模糊查询条件)
    	 </td>  
    	</tr> 
    	</table>
    	
    	
       <div id="divLCInsuredOccupation1" style="display: ''" class=maxbox1>
        <table  class= common>
        <TR  class= common>
          <TD  class= title>
            职业类别
          </TD>
          <TD  class= input>
            <Input class= "common wid" name="LCInsuredOccupationType" id=LCInsuredOccupationType readonly >
          </TD>
          
          <TD  class= title>
            职业名称
          </TD>
          <TD  class= input>
            <Input class= "common wid" name="LCInsuredOccupationName" id=LCInsuredOccupationName  >
          </TD>
          
          <TD  class= title>
            行业名称
          </TD>
          <TD  class= input>
            <Input class= "common wid" name="LCInsuredWorkName" id=LCInsuredWorkName  >
          </TD>
        </TR>
        
           </table> 	
    	<TR class=common>       	
          <td>
         	<INPUT VALUE = "职业模糊查询" class=cssButton type=button onclick="queryLcInsuredOccupation()">
          </td>
          
          <TD  >
            <Input type=hidden name="LCInsuredOccupationCode" id=LCInsuredOccupationCode readonly >
          </TD>
       </TR>
       
       </table>
       
    <table>
    <tr>
     <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this, divLCInsuredCodeGrid)"></td>
    </tr>
    </table>
    
  	<div id="divLCInsuredCodeGrid" style="display: ''; text-align:center;" >
      <table class="common">
        <tr class="common">
      	  <td text-align: left colSpan=1><span id="spanLCInsuredCodeGrid"></span></td></tr>
      </table>
      
      <input VALUE=" 首页 " TYPE="button" class="cssButton90" onclick="turnPage.firstPage();"> 
      <input VALUE="上一页" TYPE="button" class="cssButton91" onclick="turnPage.previousPage();"> 					
      <input VALUE="下一页" TYPE="button" class="cssButton92" onclick="turnPage.nextPage();"> 
      <input VALUE=" 尾页 " TYPE="button" class="cssButton93" onclick="turnPage.lastPage();"> 					
  	</div>
  	<table class=common>
  		   <tr>
		   <td class=common>
				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAllNotice);">
			</td>
         	<td class=titleImg>
            	被保人列表
    	 	</td>  
    	</tr> 
 </table>
<div class=maxbox1 > 
<table class=common> 
    <tr class=common>
     <td class=title> 共有</td>
	 <td class=input><input value="0" class="common" id="Peoples" style="width=60" readonly >名被保人,</td>
	</tr>
		<tr class=common>
     	 <td class=title>>已经录入</td>
		 <td class=input><input value="0" class="common" id="EditPeoples" style="width=60" readonly >名被保人信息。</td>
		</tr>
		<tr class=common>
     	 <td class=title>保额分配比例为</td>
		 <td class=input><input value="0" class="common" id="AmntRate" style="width=140" readonly >
     	</td>
    </tr>
    <tr>
     	<td class=common><input value="增加被保人" TYPE="button" class="cssButton" onclick="AddInsured()">
     	</td>
     	<td class=common><input value="删除被保人" TYPE="button" class="cssButton" onclick="RemoveInsured()">
     	</td>
     	<td class=common><input value="更新被保人信息" TYPE="button" class="cssButton" onclick="UpdateInsured()">
     	</td>
    </tr>
    </table>
  	<div id="divLCInsuredGrid" style="display: ''">
      <table class="common">
        <tr class="common">
      	  <td text-align: left colSpan=1><span id="spanLCInsuredGrid"></span></td></tr>
      </table>
			
  	</div>
  	
  	</div>
  	
  
		<input type=hidden id="fmAction" name="fmAction">
	
  </Div>
  
  <!--确认按钮-->
	<Div  id= "divConfirmButton" style= "display: ''">
      <table class= common>  
        <tr>
		<td class=common>
    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAllNotice);">
</td>
         	<td class= titleImg>
            	激活确认
    	 	</td>  
    	</tr>  
</table>	
<table class= common> 	
     	<TR  class= common>          
          <TD  class= input>
           <Input type=button class=cssButton name="confirmActivate" id=confirmActivate value="激活确认" onclick="submitForm();">
           <!--Input type=button class= common name="confirmActivate" value="险种信息查询" onclick="RiskSubmitForm();"-->
          </TD>                                           
       </TR>
    </table>
	</div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <span id="spanApprove"  style="display: none; position:relative; slategray"></span>
  <br/><br/><br/><br/>
</body>
</html>


