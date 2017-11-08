<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//程序名称：SpePolBankTagInput.jsp
//程序功能：
//创建日期：2002-08-16 16:25:40
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head >
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="SpePolBankTagInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="SpePolBankTagInit.jsp"%>
  <%@include file="../common/jsp/ManageComLimit.jsp"%>
  <%@include file="../agent/SetBranchType.jsp"%>
<title></title>
</head>
<body  onload="initForm();" >
  <form action="./SpePolBankTagSave.jsp" method=post name=fm id="fm" target="fraSubmit">  
     <table>
    <tr class=common>
    <td class=common>
    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLAAscription);">
    <td class=titleImg>
      应收保单查询录入信息
    </td>
    </td>
    </tr>
    </table>
    <div class="maxbox1">
  <Div  id= "divLAAscription" style= "display: ''">      
    <table  class= common>
       <tr  class= common> 
          <td  class= title5>
            主险险种号
          </td>          
          <td  class= input5>
            <Input class="common wid" name=MainPolNo id="MainPolNo" onchange="return clearMulLine();">
          </td>  
          <td  class= title5>
		        保单号
		      </td>
          <td  class= input5> 
            <input class="common wid" name=ContNo  id="ContNo">
		      </td>                                 
       </tr> 
       <tr  class= common> 
          <td  class= title5>
            管理机构
          </td>          
          <td  class= input5>
            <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center"  class="code"  name=ManageCom id="ManageCom" verify="管理机构|code:comcode" 
            onclick="return showCodeList('comcode',[this],null,null,null,null,1);"  ondblclick="return showCodeList('comcode',[this],null,null,null,null,1);" 
            onkeyup="return showCodeListKey('comcode',[this],null,null,null,null,1);">  
          </td>
          <td  class= title5>
		        代理人工号
		      </td>
          <td  class= input5>
		        <Input class="common wid" name=AgentCode  id="AgentCode">
		      </td>                                           
       </tr>
       <tr class=common>
       	 <td  class= title5>
           保单应收起期
          </td>
          <td  class= input5 width="25%">
            <Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="保单应收起期|DATE" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </td>  
          
          <td  class= title5>
           保单应收止期
          </td>
          <td  class= input5 width="25%">
            <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="保单应收止期|DATE" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </td> 
      </tr>
    </table>  
  </Div>
  </div> 
  <a href="javascript:void(0)" class=button onclick="return PolConfirm();">查  询</a> 
   <!-- <input type=button class=common  value='查询' onclick="return PolConfirm()">    -->
    <table>
    <tr class=common>
    <td class=common>
    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLAAscription2);">
    <td class=titleImg>
      应收保单信息
    </td>
    </td>
    </tr>
    </table>
  <Div  id= "divLAAscription2" style= "display: ''">
    <table  class= common>
            <tr  class= common>
                    
        <td text-align: left colSpan=1> 
		  <span id="spanAscriptionGrid" >
		  </span> 
        </td>
  			</tr>
    	</table>
      <div align=center>
      <INPUT class="cssButton90" VALUE="首页" TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT class="cssButton91" VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT class="cssButton92" VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT class="cssButton93" VALUE="尾页" TYPE=button onclick="turnPage.lastPage();">
      </div>	
    </Div>
    <a href="javascript:void(0)" class=button onclick="submitSave();">标  记</a>
    <!-- <input type=button class=common name=saveb value='标记' onclick="submitSave();">           -->
    <input type=hidden name=hideOperate id="hideOperate" value=''>
    <input type=hidden name=confirmflag id="confirmflag" value=''>
  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
