<%@include file="../i18n/language.jsp"%>


<%
  //程序名称：PDUMInit.jsp
  //程序功能：险种核保规则定义
  //创建日期：2009-3-14
  //创建人  ：CM
  //更新记录：  更新人    更新日期     更新原因/内容
%>
<script type="text/javascript">

function init()
{
	document.getElementById("ITEMCODE").value ="";
	document.getElementById("CALORDERNO").value = "";
	document.getElementById("CALELEMENT").value = "";
	document.getElementById("ELEMENTPROPERTY").value = "";
	document.getElementById("ADJUSTPOSITION").value = "";
	document.getElementById("VARIABLECODE").value = "";
	document.getElementById("CALSQL").value = "";
	document.getElementById("SQLEXCUTETYPE").value = "";
	document.getElementById("STARTPOINT").value = "";
	document.getElementById("STARTPOINTFLAG").value = "";
	document.getElementById("ENDPOINT").value = "";
	document.getElementById("ENDPOINTFLAG").value = "";
	document.getElementById("INITVALUE").value = "";
	document.getElementById("STEPVALUE").value = "";
	document.getElementById("RESULTPRECISION").value = "";
	document.getElementById("REMARK").value = "";
	
	try{
		if(operator=="update"){
			var sql="select ITEMCODE,CALORDERNO,CALELEMENT,ELEMENTPROPERTY,ADJUSTPOSITION,VARIABLECODE,CALSQL,SQLEXCUTETYPE,STARTPOINT,STARTPOINTFLAG,ENDPOINT,ENDPOINTFLAG,INITVALUE,STEPVALUE,RESULTPRECISION,REMARK from PD_CalcuteElemet where  RISKCODE='"+riskcode+"' and ITEMCODE= '"+itemcodeStr+"' and CALELEMENT= '"+calelementStr+"'";
			var tNameArr = easyExecSql(sql,1,1,1); 
			document.getElementById("ITEMCODE").value = tNameArr[0][0];
			document.getElementById("CALORDERNO").value = tNameArr[0][1];
			document.getElementById("CALELEMENT").value = tNameArr[0][2];
			document.getElementById("ELEMENTPROPERTY").value = tNameArr[0][3];
			document.getElementById("ADJUSTPOSITION").value = tNameArr[0][4];
			document.getElementById("VARIABLECODE").value = tNameArr[0][5];
			document.getElementById("CALSQL").value = tNameArr[0][6];
			document.getElementById("SQLEXCUTETYPE").value = tNameArr[0][7];
			document.getElementById("STARTPOINT").value = tNameArr[0][8];
			document.getElementById("STARTPOINTFLAG").value = tNameArr[0][9];
			document.getElementById("ENDPOINT").value = tNameArr[0][10];
			document.getElementById("ENDPOINTFLAG").value = tNameArr[0][11];
			document.getElementById("INITVALUE").value = tNameArr[0][12];
			document.getElementById("STEPVALUE").value = tNameArr[0][13];
			document.getElementById("RESULTPRECISION").value = tNameArr[0][14];
			document.getElementById("REMARK").value = tNameArr[0][15];
			showOneCodeName('sugeletype','ELEMENTPROPERTY','ELEMENTPROPERTYName');
			showOneCodeName('suglocationtype','ADJUSTPOSITION','ADJUSTPOSITIONName');
			showOneCodeName('sugsqltype','SQLEXCUTETYPE','SQLEXCUTETYPEName');
			showOneCodeName('sugyearflag','STARTPOINTFLAG','STARTPOINTFLAGName');
			showOneCodeName('sugyearflag','ENDPOINTFLAG','ENDPOINTFLAGName');
			document.getElementById("ITEMCODE").setAttribute("readonly",true ,0);
			document.getElementById("CALELEMENT").setAttribute("readonly",true ,0);
			
			
		}
		
	}
	catch(re){
		myAlert(""+"PDSugIncomeDataAlginit.jspInitForm函数中发生异常:初始化界面错误!");
	}
}

</script>

