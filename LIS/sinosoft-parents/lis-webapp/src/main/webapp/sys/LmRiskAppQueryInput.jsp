<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
//程序名称：LmRiskAppQueryInput.jsp
//程序功能：
//创建日期：2005-10-31
//创建人：万泽辉
//更新记录：  更新人    更新日期     更新原因/内容
%>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
	<SCRIPT src="LmRiskAppQuery.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="LmRiskAppQueryInit.jsp"%>

	<title>险种信息查询 </title>
</head>

<body  onload="initLmRiskAppForm();" >
  <form  method=post name=fm id="fm" target="fraSubmit">
  <table >
    	<tr>
    	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    	</td>
			<td class= titleImg>
				险种信息查询 
			</td>
		</tr>
	</table>
	<Div  id= "divLCPol1" class="maxbox1" style= "display: ''">
    <table  class= common align=center>
      <TR class=common>
				<TD class=title>险种类型</TD>
				<TD  class= input><input class=codeno name="RiskProp" id="RiskProp" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " CodeData="0|^G|团体险  ^I|个体险 ^Y|银代险"  onClick="return showCodeListEx('RiskProp', [this,RiskPropName],[0,1],'','','','',100)" onDblClick="return showCodeListEx('RiskProp', [this,RiskPropName],[0,1],'','','','',100)" ; onKeyUp="return                         showCodeListKeyEx('RiskProp',[this,RiskPropName],[0,1],'','','','',100);"><input class=codename name=RiskPropName id="RiskPropName" readonly ></TD>	
				 <TD class= common>
          <input class=cssButton type=button value="查  询" onClick="LmRiskAppQuery()">
          </TD>		
			</TR>
			
     </table>
  </Div>


    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCClaim1);">
    		</td>
    		<td class= titleImg>
    			 险种信息
    		</td>
    	</tr>
    	
    </table>
  	<Div  id= "divLCClaim1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td style=" text-align: left" colSpan=1>
  					<span id="spanLmRiskAppGrid" >
  					</span>
  			  	</td>
  			</tr>
    	</table>
    	
    	
    	<table align="center" style="display:none">
            <tr>
                <td><INPUT class=cssButton90 VALUE="首  页" TYPE=button onClick="turnPage.firstPage();"></td>
                <td><INPUT class=cssButton91 VALUE="上一页" TYPE=button onClick="turnPage.previousPage();"></td>
                <td><INPUT class=cssButton92 VALUE="下一页" TYPE=button onClick="turnPage.nextPage();"></td>
                <td><INPUT class=cssButton93 VALUE="尾  页" TYPE=button onClick="turnPage.lastPage();"></td>
            </tr>
        </table>
 <table>
            <tr>
                <td><INPUT class=cssButton VALUE="返   回" TYPE=button onClick="GoBack();"></td>
                
            </tr>
        </table>      
  	</div>

  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>


