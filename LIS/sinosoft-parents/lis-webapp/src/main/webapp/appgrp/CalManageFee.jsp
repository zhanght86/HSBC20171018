<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>

<head>
	
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<script src="CalManageFee.js"></script>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>

<script>
		var prtNo ="<%=request.getParameter("grpcontno")%>";
    var turnPage = new turnPageClass();  
     
		function getaddresscodedata()
{
	//alert(prtNo);
  //初始化险种
  var sql="  select riskcode,riskname from lmrisk where insuaccflag='Y' and riskcode in (select trim(RiskCode) from lcgrppol where grpcontno='"+prtNo+"')";
  var tCodeData = "0|";
  turnPage.strQueryResult  = easyQueryVer3(sql, 1, 0, 1);
    if (turnPage.strQueryResult != "")
    {
      turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
      m = turnPage.arrDataCacheSet.length;
      for (i = 0; i < m; i++)
      {
        j = i + 1;
        tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
      }
    }
    
    fm.all("RiskCode").CodeData=tCodeData;
  
}

function getcodedata2()
{
  var tCodeData = "0|";
  tCodeData = tCodeData + "^" + "0" + "|" + "个人帐户"+ "^" + "1" + "|" + "公共帐户";
  fm.all("AccType").CodeData=tCodeData;
}

function getcodedata3()
{
 if(fm.all("RiskCode").value=="")
 {
  alert("请先选择险种！");
  return;
 }
 var sql="select insuaccno,insuaccname From lmrisktoacc where riskcode='"+fm.all("RiskCode").value+"'";
 //alert(sql);
  var tCodeData = "0|";
  turnPage.strQueryResult  = easyQueryVer3(sql, 1, 0, 1);
    if (turnPage.strQueryResult != "")
    {
      turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
      m = turnPage.arrDataCacheSet.length;
      for (i = 0; i < m; i++)
      {
        j = i + 1;
        tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
      }
    }
    
    fm.all("FeeCode").CodeData=tCodeData;
}

function getcodedata4()
{
 var sql="select code,codename From ldcode where codetype='feecalmode' and (code='03' or code='04' or code='07') order by code";
 //alert(sql);
  var tCodeData = "0|";
  turnPage.strQueryResult  = easyQueryVer3(sql, 1, 0, 1);
    if (turnPage.strQueryResult != "")
    {
      turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
      m = turnPage.arrDataCacheSet.length;
      for (i = 0; i < m; i++)
      {
        j = i + 1;
        tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
      }
    }
    
    fm.all("CalMode").CodeData=tCodeData;

}

function CalFee()
{

if(fm.RiskCode.value=="")
{
 alert("请选择险种编码！");
 return;
}

if(fm.FeeCode.value=="")
{
 alert("请选择管理费代码！");
 return;
}

if(fm.CalMode.value=="")
{
 alert("请选择计算方式！");
 return;
}

if(fm.AccType.value=="")
{
 alert("请选择帐户类型！");
 return;
}

if(fm.AccType.value=="0")
{
  if(fm.ContNo.value=="")
  {
   alert("您选择的帐户类型是个人帐户，请输入个人合同号！");
  }
}
fm.submit();
}
	</script>
</head>
<body>
	<form action="./CalManageFeeSave.jsp" method=post name=fm target="fraSubmit">
		<hr>
		<table class=common>
			<TR>
				<td CLASS="title">险种编码
    	    </td>
			    <td CLASS="input">
			      <Input class="codeno" name="RiskCode"  ondblclick="getaddresscodedata();return showCodeListEx('GetAddressNo',[this,AddressNoName],[0,1],'', '', '', true);" onkeyup="getaddresscodedata();return showCodeListKeyEx('GetAddressNo',[this,AddressNoName],[0,1],'', '', '', true);"><input class=codename name=AddressNoName readonly=true><font color=red>*</font>
    	    </td>
    	    
    	    <td CLASS="title">管理费代码
    	    </td>
			    <td CLASS="input">
			    	<Input name="FeeCode" class='codeno' id="FeeCode" ondblclick="getcodedata3();return showCodeListEx('appcontract',[this,appcontractName],[0,1],'', '', '', true);" onkeyup="getcodedata3();return showCodeListEx('appcontract',[this,cooperateName],[0,1]);"><input name=appcontractName class=codename readonly=true><font color=red>*</font>  
    	    </td>
    	    
			</TR>
			
			<TR>
				<td CLASS="title">计算方式
    	    </td>
			    <td CLASS="input">
			    	
			      <Input name="CalMode" class='codeno' id="CalMode" ondblclick="getcodedata4();return showCodeListEx('appcontract',[this,appcontractName2],[0,1],'', '', '', true);" onkeyup="getcodedata4();return showCodeListEx('appcontract',[this,cooperateName2],[0,1]);"><input name=appcontractName2 class=codename readonly=true><font color=red>*</font>  
    	    </td>
    	    
    	    <td class="title">帐户类型</td>
					<td class="input">
            <Input name=AccType class='codeno' id="AccType" ondblclick="getcodedata2();return showCodeListEx('riskgrade',[this,riskgradeName],[0,1],'', '', '', true);" onkeyup="getcodedata2();return showCodeListEx('riskgrade',[this,cooperateName],[0,1]);"><input name=riskgradeName class=codename readonly=true><font color=red>*</font>             
					</td>
			</TR>
			
			<TR>
				
    	    
    	    <td class="title">个人合同号</td>
					<td class="input">
            <input class=common name=ContNo>          
					</td>
					
					<td class="title">管理费金额</td>
					<td class="input">
            <input class="common" name="Fee" readonly="readonly">
            <input type="hidden" name=PrtNo class=common value="<%=request.getParameter("grpcontno")%>">
					</td>
			</TR>
		</table>
		<hr>
		<table>
			        	<tr>
							<td>
								<input type =button class=cssButton value="计  算" onclick="CalFee();">
							</td>
							
						</tr>
					</table>
	</form>
	<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
