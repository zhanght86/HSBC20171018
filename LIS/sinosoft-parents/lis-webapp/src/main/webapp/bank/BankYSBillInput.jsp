<html>
<%
//程序名称：银行代收对帐清单
//程序功能：
//创建日期：2003-3-25
//创建人  ：刘岩松程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
	<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
	<!--用户校验类-->
	<%@page import = "com.sinosoft.utility.*"%>
	<%@page import = "com.sinosoft.lis.schema.*"%>
	<%@page import = "com.sinosoft.lis.vschema.*"%>
	<%@page import = "com.sinosoft.lis.bank.*"%>
	<%@include file="../common/jsp/UsrCheck.jsp"%>

	<head >
  	<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  	
  	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  	   <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  	<SCRIPT src="BankYSBillInput.js"></SCRIPT>
  	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  	<%@include file="BankYSBillInit.jsp"%>
	</head>

	<body  onload="initForm();" >
  	<form action="./PrintBill.jsp" method=post name=fm  id=fm target="fraSubmit">
		<%@include file="../common/jsp/InputButton.jsp"%>
        <!-- 显示或隐藏LLReport1的信息 -->
    		
    	<Div  id= "divLLReport1" style= "display: ''">
    	<table class=common>
          <tr class= titleImg>
           <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divInvAssBuildInfo);">
            </TD>
            <TD class= titleImg > 银行转账代收失败清单 </TD>
          </tr>
        </table>
      <div id="divInvAssBuildInfo">
       <div class="maxbox1" >
      	<table  class= common>
        	<TR  class= common>
          	<TD  class= title5> 开始日期 </TD>
          	<TD  class= input5><input class="coolDatePicker" dateFormat="short" id="StartDate"  name="StartDate" onClick="laydate
({elem:'#StartDate'});" verify="提数日期|Date"> <span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img 
src="../common/laydate/skins/default/icon.png" /></a></span>
 <font color='red'>*</font></TD>
          	<TD  class= title5> 结束日期 </TD>
          	<TD  class= input5> <input class="coolDatePicker" dateFormat="short" id="EndDate"  name="EndDate" onClick="laydate({elem:'#EndDate'});" verify="提数日期|Date"> <span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span> <font color='red'>*</font></TD>
		      </TR>
		      
		      <TR class= common>
					  <TD  class= title5> 首续期标志 </TD>
            <TD  class= input5> <Input class="common wid" name=SXFlag CodeData="0|^S|首期^X|续期^B|保全^L|理赔" 
             style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="showCodeListEx('SXFlag1',[this],[0,1]);"
            ondblClick="showCodeListEx('SXFlag1',[this],[0,1]);"
            onkeyup="showCodeListKeyEx('SXFlag1',[this],[0,1]);"> </TD>
						<TD  class= title5> 管理机构 </TD>
          	<TD  class= input5> <Input class="common wid" name=Station  id=Station 
            style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="return showCodeList('Station',[this]);"
            onDblClick="return showCodeList('Station',[this]);"
             onKeyUp="return showCodeListKey('Station',[this]);"> </TD>
          	<TD> <Input Type= hidden name=Flag > </TD>   <!--代收代付标志-->
          	<TD> <Input Type= hidden name=TFFlag > </TD> <!--正确清单还是错误清单的标志-->
					</tr>

 					<TR  class= common>
         		<TD  class= title5> 银行代码 </TD>
		       <TD  class= input5>
				 <Input class=codeno name=BankCode  id=BankCode 
                 style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="return showCodeList('bank',[this,BankName],[0,1],null,'1 and comcode like #'+comCode+'%#','1');"
                 onDblClick="return showCodeList('bank',[this,BankName],[0,1],null,'1 and comcode like #'+comCode+'%#','1');"
                   onkeyup="return showCodeListKey('bank',[this,BankName],[0,1],null,'1 and comcode like #'+comCode+'%#','1');"
                    verify="银行代码|notnull&code:bank"><input class=codename name=BankName readonly=true>
		      </TD>
        	</TR>
            </table>
        	</div>
</div>
<br>
	<a href="javascript:void(0);" class="button"onClick="showSerialNo()">列出打印批号</a>	
    <a href="javascript:void(0);" class="button"onClick="PrintBill()">打  印  批  单</a>			
		
        	
 				
       <!--         			 <TR  class= common>
            <TD>
          	<input class=cssButton type=button value="列出打印批号" onClick="showSerialNo()">
          </TD>
            <TD class=input width="23%">
          	<input class=cssButton type=button value="打  印  批  单" onClick="PrintBill()">
          </TD>-->
          <table>
    	<tr>
          <TD  class= input>
            	<input type=hidden  name="BillNo" >
            	<input type=hidden  name="selBankCode" >
          	</TD>
        	</TR>
            </table>

    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLReport2);">
    		</td>
    		<td class= titleImg>
    			 清单列表
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLLReport2" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					<span id="spanBillGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	
      <Div  id= "divPage" align=center style= "display: 'none' ">     
      <INPUT CLASS=cssButton90 VALUE="首页" TYPE=button onClick="turnPage.firstPage();"> 
      <INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onClick="turnPage.previousPage();"> 					
      <INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button onClick="turnPage.nextPage();"> 
      <INPUT CLASS=cssButton93 VALUE="尾页" TYPE=button onClick="turnPage.lastPage();">
	 </Div>
	</div>
    </div>
    <br><br><br><br>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
