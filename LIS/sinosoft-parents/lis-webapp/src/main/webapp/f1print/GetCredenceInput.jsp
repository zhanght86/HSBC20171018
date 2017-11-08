<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>    
<% 
//程序名称：
//程序功能：
//创建日期：2002-12-20
//创建人  ：lh
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
    GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var managecom = "<%=tGI.ManageCom%>"; //记录管理机构
	var comcode = "<%=tGI.ComCode%>"; //记录登陆机构
</script>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="GetCredenceInput.js"></SCRIPT> 
  <%@include file="GetCredenceInit.jsp"%>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
	<title>给付凭证打印</title>
</head>

<body  onload="initForm();" >    
  <form  method=post name=fm  id=fm target="fraSubmit">
    <table class= common border=0 width=100%>
    	<tr> 
    		<td class=common  width=2% >
    		 <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGetCre);">
    		</td>
    		<td class= titleImg align=left>
        		输入查询条件
       	</td>   		      
    	</tr>
    </table>
    <Div  id= "divGetCre" style= "display: ''">
      <div class="maxbox1" >
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title5>  实付号码  </TD>
          <TD  class= input5>  <Input class="common wid" name=ActuGetNo >  </TD>
          <TD  class= title5>  其它号码 </TD>
          <TD  class= input5> <Input class="common wid"name=OtherNo >  </TD>
            </TR>
        <TR  class= common>
          <TD  class= title5>  其它号码类型 </TD>
          <!--TD  class= input>	<Input class=code name=OtherNoType verify="其它号码类型" CodeData="0|^0|生存领取合同号^1|生存领取集体保单号^2|生存领取个人保单号^3|批改号^4|暂交费退费给付通知书号^5|赔付应收给付通知书号^6|(个单)其他退费给付通知书号^7|红利个人保单号^8|(团单)其他退费给付通知书号" ondblClick="showCodeListEx('OtherNoType',[this]);" onkeyup="showCodeListKeyEx('OtherNoType',[this]);">            </TD-->          
          <TD  class= input5>	<Input class=code name=OtherNoType verify="其它号码类型"
           CodeData="0|^1|客户号^2|生存领取对应的合同号^4|暂交费退费给付通知书号^5|赔付应收给付通知书号^6|溢交退费对应的合同号^7|红利给付对应的合同号^9|续期回退对应的合同号^10|保全对应的批改号" 
           style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="showCodeListEx('OtherNoType',[this]);" 
           ondblClick="showCodeListEx('OtherNoType',[this]);" 
           onKeyUp="showCodeListKeyEx('OtherNoType',[this]);" readonly>            </TD>          
        
          <TD  class= title5> 应付日期  </TD>
          <TD  class= input5>
          <input class="coolDatePicker" dateFormat="short" id="ShouldDate"  name="ShouldDate" onClick="laydate
({elem:'#ShouldDate'});" > <span class="icon"><a onClick="laydate({elem: '#ShouldDate'});"><img 
src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
            </TR>
        <TR  class= common>
          <TD  class= title5> 管理机构 </TD>
          <TD  class= input5><Input class="common wid"name=MngCom
           style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="return showCodeList('comcode',[this]);" 
           onDblClick="return showCodeList('comcode',[this]);"
            onKeyUp="return showCodeListKey('comcode',[this],null,null,null,null,1);" verify="管理机构|code:comcode" readonly> </TD>
          <TD  class= title5> 代理人编码  </TD> 
          <TD  class= input5> <Input class="common wid" name=AgentCode verify="代理人编码|code:AgentCode"
          style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="return queryAgent(comcode);"
           onDblClick="return queryAgent(comcode);"
            onKeyUp="return queryAgent(comcode);" readonly></TD> 
        </TR>
    </table>
    </div></Div>
   
    	<!--<INPUT VALUE="查  询" class= cssButton TYPE=button onClick="easyQueryClick();"> -->
        <a href="javascript:void(0);" class="button"onClick="easyQueryClick();">查   询</a>
    <table>
    	<tr>
        <td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLJAGet1);">
    		</td>
    		<td class= titleImg>
    			 给付信息
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLJAGet1" style= "display: ''">
      <table class= common>
        <TR  class= common>
					<td text-align: left colSpan=1>
		  			<span id="spanPolGrid" >
		  			</span> 
		  		</td>	
				</TR>    	 
      </table>
      <div align=center >
		      <INPUT VALUE="首  页" class= cssButton90 TYPE=button onClick="turnPage.firstPage();"> 
		      <INPUT VALUE="上一页" class= cssButton91  TYPE=button onClick="turnPage.previousPage();"> 					
		      <INPUT VALUE="下一页" class= cssButton92 TYPE=button onClick="turnPage.nextPage();"> 
		      <INPUT VALUE="尾  页" class= cssButton93 TYPE=button onClick="turnPage.lastPage();">				
		
	  </div>  
	 
   <!-- <table align=right>
      <tr>
        <td>	 
	  <input type=Button class= cssButton name="GetPrint" value="给付凭证打印" onClick="GPrint()"> 
	</td>
      </tr>
    </table>		-->
     <a href="javascript:void(0);" class="button"onClick="GPrint()">给付凭证打印</a>
    </Div>
    <br><br><br><br>
        <input type=hidden id="fmtransact" name="fmtransact">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html> 
