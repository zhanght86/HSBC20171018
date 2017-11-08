<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
//程序名称：VersionRuleQuery.jsp
//程序功能：版本信息查询页面
//创建日期：2008-08-18
//创建人  ：FanXin
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="./VersionRuleQuery.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="./VersionRuleQueryInit.jsp"%>

<title>版本信息查询</title>
</head>
<body onload="initForm();initElementtype();">
  <form  method=post name=fm id=fm target="fraSubmit">
  
  <table>
    <tr class=common>
    <td class=common>
    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFIRulesVersion1);">
    </IMG>
    <td class=titleImg>
      查询条件
      </td>
    </td>
    </tr>
  </table>
    <Div  id= "divFIRulesVersion1" style= "display: ''">
    <div class="maxbox">
  <table  class= common>
		<tr class= common>
         <TD class= title5>
					  版本编号
				 </TD>
				 <TD class=input5>
				 	 <Input class="wid" class=common name=VersionNo id=VersionNo >
				 </TD>
         <TD  class= title5>
          申请日期
         </TD>
         <TD  class= input5>
            <!--<Input class="coolDatePicker" dateFormat="short" name=AppDate >-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#AppDate'});" verify="有效开始日期|DATE" dateFormat="short" name=AppDate id="AppDate"><span class="icon"><a onClick="laydate({elem: '#AppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
         </TD>          
		</tr>
				
		<tr class= common>
         <TD  class= title5>
          生效日期
         </TD>
         <TD  class= input5>
            <!--<Input class="coolDatePicker" dateFormat="short" name=StartDate >-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="有效开始日期|DATE" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
         </TD>     
         <TD  class= title5>
          失效日期
         </TD>
         <TD  class= input5>
            <!--<Input class="coolDatePicker" dateFormat="short" name=EndDate >-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="有效开始日期|DATE" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
         </TD>          
		</tr>		
		
		<tr class= common>
				 <TD class= title5>
					  版本状态
				 </TD>
				 <TD class=input5>
				 	 <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name= VersionState id= VersionState verify="版本状态|notnull" CodeData="0|^01|正常^02|维护^03|删除"  ondblclick="return showCodeListEx('VersionState',[this,VersionStateName],[0,1],null,null,null,[1]);" onMouseDown="return showCodeListEx('VersionState',[this,VersionStateName],[0,1],null,null,null,[1]);" onkeyup="return showCodeListKeyEx('VersionState',[this,VersionStateName],[0,1],null,null,null,[1]);"  ><input class=codename name=VersionStateName id=VersionStateName readonly=true></TD>  
				</TD>
		</tr>

      </table>
         
         </div> 
     </div>	
        <!--<INPUT VALUE="查  询" TYPE=button onclick="easyQueryClick();" class="cssButton">-->   
         <a href="javascript:void(0);" class="button" onClick="easyQueryClick();">查    询</a>
   <table>    	
    <tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFIRulesVersion2);">
    		</td>
    		<td class= titleImg>
    			 版本信息查询结果
    		</td>
    	</tr>
   </table>

    
  	<Div  id= "divFIRulesVersion2" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					 <span id="spanRulesVersionGrid" >
  					 </span>
  			  	</td>
  			</tr>
    	</table>
        
      <!--<INPUT VALUE="首  页" TYPE=button onclick="turnPage.firstPage();" class="cssButton90">
      <INPUT VALUE="上一页" TYPE=button onclick="turnPage.previousPage();" class="cssButton91">
      <INPUT VALUE="下一页" TYPE=button onclick="turnPage.nextPage();" class="cssButton92">
      <INPUT VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();" class="cssButton93">-->
  	</div>  
<!--<div align="left"><INPUT VALUE="返  回" TYPE=button onclick="returnParent();" class="cssButton"></div>-->
<a href="javascript:void(0);" class="button" onClick="returnParent();">返    回</a>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>

