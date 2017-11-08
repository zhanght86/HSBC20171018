var turnPage = new turnPageClass();
function easyQueryClick(flag1)
{
	var strSQL = "";
	var strSQL1 = "";
	var strSQL2 = "";
	var strOrder = "";
	var strInsuredNo = "";
	var strInsuredName = "";
	var strRiskCode = "";
	strSQL1 = "select a.contno,a.insuredno,a.insuredname,c.riskcode,(select b.riskname from lmrisk b where b.riskcode=c.riskcode),a.uwno,a.uwerror,a.modifydate from LCUWError a,LCPol c where 1=1 and a.grpcontno = '" +fm.GrpContNo.value+ "' and a.polno=c.polno";
	//strOrder = " order by a.contno,c.riskcode";
	strInsuredNo = " a.insuredno='"+fm.InsuredNo.value+"'";
	strInsuredName = " a.insuredname like '%%"+fm.InsuredName.value+"%%'";
	strRiskCode = " substr(a.uwrulecode,1,3)='"+fm.RiskCode.value+"'";
	strSQL2 = " union select a.contno,a.insuredno,a.insuredname,'','',a.uwno,a.uwerror,a.modifydate from LCCUWError a,LCCont c where 1=1 and a.grpcontno = '" +fm.GrpContNo.value+ "' and a.contno=c.contno";
	if (flag1=="0"){	
	     strSQL = strSQL1+" and a.uwno='1'"+strSQL2+" and a.uwno='1'";
	}
  if (flag1=="1"){
  	   strSQL = strSQL1+" and"+strInsuredNo+" and a.uwno='1'"+strSQL2+" and"+strInsuredNo+" and a.uwno='1'";
  }
  if (flag1=="2"){
  	   strSQL = strSQL1+" and"+strInsuredName+" and a.uwno='1'"+strSQL2+" and"+strInsuredName+" and a.uwno='1'";
  }
  if (flag1=="3"){
  	   strSQL = strSQL1+" and"+strRiskCode+" and a.uwno='1'"+strSQL2+" and"+strRiskCode+" and a.uwno='1'";
  }
  if (flag1=="4"){
  	   strSQL = strSQL1+" and"+strInsuredNo+" and"+strInsuredName+" and a.uwno='1'"+strSQL2+" and"+strInsuredNo+" and"+strInsuredName+" and a.uwno='1'";
  }
  if (flag1=="5"){
  	   strSQL = strSQL1+" and"+strInsuredNo+" and"+strRiskCode+" and a.uwno='1'"+strSQL2+" and"+strInsuredNo+" and"+strRiskCode+" and a.uwno='1'";
  }
  if (flag1=="6"){
  	   strSQL = strSQL1+" and"+strInsuredName+" and"+strRiskCode+" and a.uwno='1'"+strSQL2+" and"+strInsuredName+" and"+strRiskCode+" and a.uwno='1'";
  }
  if (flag1=="7"){
  	   strSQL = strSQL1+" and"+strInsuredNo+" and"+strInsuredName+" and"+strRiskCode+" and a.uwno='1'"+strSQL2+" and"+strInsuredNo+" and"+strInsuredName+" and"+strRiskCode+" and a.uwno='1'";
  }
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1); 
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("无个人自核未通过原因！");
    initUWReasonGrid();
    return false;
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = UWReasonGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  turnPage.pageIndex = 0;
  displayMultiline(turnPage.arrDataCacheSet, turnPage.pageDisplayGrid);
  return true;

}
function QueryForm() {
	 var flag="0";
	 if (fm.InsuredNo.value!=""&&fm.InsuredName.value==""&&fm.RiskCode.value=="") {
	 	  flag="1";  
   }
   if (fm.InsuredNo.value==""&&fm.InsuredName.value!=""&&fm.RiskCode.value=="") {
      flag="2";   	
   }
	 if (fm.InsuredNo.value==""&&fm.InsuredName.value==""&&fm.RiskCode.value!="") {
	 	  flag="3";
   }
   if (fm.InsuredNo.value!=""&&fm.InsuredName.value!=""&&fm.RiskCode.value=="") {
      flag="4";   	
   }
   if (fm.InsuredNo.value!=""&&fm.InsuredName.value==""&&fm.RiskCode.value!="") {
      flag="5";   	
   }      
   if (fm.InsuredNo.value==""&&fm.InsuredName.value!=""&&fm.RiskCode.value!="") {
      flag="6";   	
   }           
   if (fm.InsuredNo.value!=""&&fm.InsuredName.value!=""&&fm.RiskCode.value!="") {
      flag="7";   	
   }
   easyQueryClick(flag); 
}
function excel(){
	
   fm.action = "./UWReasonExcel.jsp?prt="+fm.prtno.value;
	//fm.action = "./UWReasonExcel.jsp?prt="+fm.GrpContNo.value; 
	
   fm.target=".";
   fm.submit();
}