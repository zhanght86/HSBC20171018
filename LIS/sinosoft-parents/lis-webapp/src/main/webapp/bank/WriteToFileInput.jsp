<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html> 
<%
//程序名称：WriteToFileInput.jsp
//程序功能：
//创建日期：2002-11-18 11:10:36
//创建人  ：胡 博
//更新记录：  更新人    更新日期     更新原因/内容
%>
<head > 
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

  <SCRIPT src="WriteToFileInput.js"></SCRIPT> 
  <%@include file="WriteToFileInit.jsp"%>
  
  <title>生成送银行文件 </title>
</head>

<%
  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");
  String tTempBankFlag = request.getParameter("BankFlag");
  String tYBTBankCode = request.getParameter("YBTBankCode");
  loggerDebug("WriteToFileInput","tTempBankFlag: "+tTempBankFlag);
  loggerDebug("WriteToFileInput","tYBTBankCode: "+tYBTBankCode);
%>

<script>
  comCode = "<%=tGlobalInput.ComCode%>";
  dealType = "<%=request.getParameter("DealType")%>";
  var tBankFlag = "0";  //0 正常银行接口,1 银保通批量代付
  var tNewBankFlag = "<%=tTempBankFlag%>";
  var tYBTBankCode = "<%=tYBTBankCode%>";
  if( tNewBankFlag != null && tNewBankFlag != "null" && tNewBankFlag != "" )
  {
    tBankFlag = tNewBankFlag;
  }
  
  //alert("BankFlag: "+tBankFlag);
  
  //银行编码下拉菜单过滤条件
  var tBankCodeInitSQL = "1 and comcode like #"+comCode+"%# and not exists(select 1 from LDCOde1 where codetype=#YBTBatBank# and Code1 = a.BankCode and OtherSign=#1#)";
  //easyQueryClick查询时过滤条件
  var tBankDataSQL = " and not exists(select 1 from LDCOde1 where codetype='YBTBatBank' and Code1 = BankCode and OtherSign='1') ";
  
  if(tBankFlag == "1")   //如果是银保通批量代付
  {
    tBankCodeInitSQL = "1 and comcode like #"+comCode+"%# and exists(select 1 from LDCOde1 where codetype=#YBTBatBank# and Code=#"+tYBTBankCode+"# and Code1 = a.BankCode and OtherSign=#1#)";
    tBankDataSQL = " and exists(select 1 from LDCOde1 where codetype='YBTBatBank' and Code='"+tYBTBankCode+"' and Code1 = BankCode and OtherSign='1') ";
  }

</script>

<body  onload="initForm();" >
  <form action="./WriteToFileSave.jsp" method=post name=fm id=fm target="fraTitle">
    <%@include file="../common/jsp/InputButton.jsp"%>
    
    <!-- 保单信息部分 -->
  <table class= common border=0 width=100%>
    <tr>
    <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage

(this,divInvAssBuildInfo);">
            </TD>
			<td class= titleImg align= center>请输入查询条件：</td>
		</tr>
	</table>
	<div id="divInvAssBuildInfo">
       <div class="maxbox1" >
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title5>
            银行代码
          </TD>
          <TD  class= input5>
          	 <Input class=codeno name=BankCode readonly 
             style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
            onclick="return showCodeList('bank',[this,BankName],[0,1],null,tBankCodeInitSQL,'1');"  
             onDblClick="return showCodeList('bank',[this,BankName],[0,1],null,tBankCodeInitSQL,'1');"  
             onkeyup="return showCodeListKey('bank',[this,BankName],[0,1],null,tBankCodeInitSQL,'1');" verify="银行代码|notnull&code:bank"><input class=codename name=BankName readonly=true>
          </TD>
          <TD  class= title5>
            文件生成日期
          </TD>
          <TD  class= input5>
           <Input class="multiDatePicker laydate-icon"   onClick="laydate({elem: '#StartDate'});"dateFormat="short" 

name=StartDate id=StartDate><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img 

src="../common/laydate/skins/default/icon.png" /></a></span> 
          </TD>

        </TR>
    </table>
    </div></div>
 <!-- <INPUT VALUE="查  询" TYPE=button class="cssButton" onClick="easyQueryClick();">-->
 <br>
 <a href="javascript:void(0);" class="button"onClick="easyQueryClick();">查   询</a>
 <br><br>  
    
    <!-- 生成送银行文件 fraSubmit-->
    <table class= common border=0 width=100%>
    	<tr>
         <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage

(this,divInvAssBuildInfo1);">
			<td class= titleImg align= center>请选择批次号：</td>
  		</tr>
  	</table>
     <div id="divInvAssBuildInfo1">
     <div class="maxbox1" ><br><br><br><br></div></div>     
        
    <!-- 批次号信息（列表） -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divBank1);">
    		</td>
    		<td class= titleImg>
    			 批次号信息
    		</td>
    	</tr>
    </table>
  	<Div  id= "divBank1" style= "display: ''">
      	<table  class= common>
          	<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanBankGrid" >
  					</span> 
  				</td>
  			</tr>
  		</table>
  	</div>
  	
    <Div id= "divPage" align=center style= "display: 'none' ">
    <INPUT CLASS="cssButton90" VALUE="首  页" TYPE=button onClick="turnPage.firstPage();"> 
    <INPUT CLASS="cssButton91" VALUE="上一页" TYPE=button onClick="turnPage.previousPage();"> 					
    <INPUT CLASS="cssButton92" VALUE="下一页" TYPE=button onClick="turnPage.nextPage();"> 
    <INPUT CLASS="cssButton93" VALUE="尾  页" TYPE=button onClick="turnPage.lastPage();">
    </Div>

    
    <br>
    
    <!--
    <table  class= common>
    <TR  class= common>
      <TD  class= title>
        请输入要生成的文件名称:
      </TD>
      <TD  class= input>
        <Input class= common5 TYPE=file name=FileName verify="文件名称|notnull" > 
      </TD>           
    </TR>
    </table>
    -->
    
    <Div id=DivFileDownload style="display:'none'">
      <A id=fileUrl href=""></A>
    </Div>
    
      
    
    
   <Div id=DivBankFileDeal style="display:''">
   <a href="javascript:void(0);" class="button"onClick="submitForm()">生成文件</a>
   <a href="javascript:void(0);" class="button"onClick="fileDownload()">文件下载</a>
   	<!--<INPUT VALUE="生成文件"  class="cssButton" TYPE=button onClick="submitForm()">
    <INPUT VALUE="文件下载"  class="cssButton" TYPE=button onClick="fileDownload()">
   	<INPUT VALUE="生成文件"  class="cssButton" TYPE=button onClick="submitForm()">
    <INPUT VALUE="文件发送"  class="cssButton" name=sendBankFileButton TYPE=button onClick="sendBankFile()">
    <INPUT VALUE="查看明细"  class="cssButton" TYPE=button onClick="viewBatDetail()">-->
   </Div>
    
    <INPUT VALUE="" TYPE=hidden name=serialNo>
    <INPUT VALUE="" TYPE=hidden name=YBTBankCode>
    <INPUT VALUE="" TYPE=hidden name=Url>
    <input type=hidden id="fmtransact" name="fmtransact">
    
    <br><br>
    <table class= common border=0 width=100%>
    <tr>
    <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage

(this,divInvAssBuildInfo2);">
            </TD>
			<td class= titleImg align= center>银行批次数据统计：</td>
		</tr>
	  </table> 
       <div id="divInvAssBuildInfo2">
       <div class="maxbox1" >
	     		  								
    <table  class= common align=center>
    	<TR  class= common>
        <TD  class= title5>
          批次总金额
        </TD>
        <TD  class= input5>
          <Input NAME=TotalMoney class=common >
        </TD>
        <TD  class= title5>
          批次总笔数
        </TD>
        <TD  class= input5>
          <Input name=TotalNum class=common >
        </TD>

      </TR>
    </table>
  </div></div>
  <br><br><br><br>
  
  
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  
</body>
</html>

