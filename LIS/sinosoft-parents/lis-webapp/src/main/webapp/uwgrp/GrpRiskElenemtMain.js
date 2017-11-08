

var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var k = 0;
var strOperate = "like";
function save()
{
	if(isNumeric(fm.coorate1.value)==false && trim(fm.coorate1.value)!="")
	{
		alert("请正确填写职业类别分布调整比例！");
		fm.coorate1.focus();
		return;
	}
	if(isNumeric(fm.coorate2.value)==false && trim(fm.coorate2.value)!="")
	{
		alert("请正确填写年龄分布调整比例！");
		fm.coorate2.focus();
		return;
	}
	if(isNumeric(fm.coorate3.value)==false && trim(fm.coorate3.value)!="")
	{
		alert("请正确填写性别分布调整比例！");
		fm.coorate3.focus();
		return;
	}
	if(isNumeric(fm.coorate4.value)==false && trim(fm.coorate4.value)!="")
	{
		alert("请正确填写投保单位性质调整比例！");
		fm.coorate4.focus();
		return;
	}
	if(isNumeric(fm.coorate5.value)==false && trim(fm.coorate5.value)!="")
	{
		alert("请正确填写既往理赔率调整比例！");
		fm.coorate5.focus();
		return;
	}
	if(isNumeric(fm.coorate6.value)==false && trim(fm.coorate6.value)!="")
	{
		alert("请正确填写上年/平均赔付率！");
		fm.coorate6.focus();
		return;
	}

	fm.submit();
}
function easyQueryClick()
{
	//alert(contNo);
	//alert(riskcode);
	var strSQL = "select count(*) from lcpol where grpcontno='"+contNo+"' and occupationtype='1' and  riskcode='"+riskcode+"'";
	var varity1 = easyExecSql(strSQL);
	strSQL = "select count(*) from lcpol where grpcontno='"+contNo+"' and occupationtype='2' and riskcode='"+riskcode+"'";
	var varity2=easyExecSql(strSQL);
	strSQL = "select count(*) from lcpol where grpcontno='"+contNo+"' and occupationtype='3' and riskcode='"+riskcode+"'";
	var varity3=easyExecSql(strSQL);
	strSQL = "select count(*) from lcpol where grpcontno='"+contNo+"' and occupationtype='4' and riskcode='"+riskcode+"'";
	var varity4=easyExecSql(strSQL);
	strSQL = "select count(*) from lcpol where grpcontno='"+contNo+"' and occupationtype='5' and riskcode='"+riskcode+"'";
	var varity5=easyExecSql(strSQL);
	strSQL = "select count(*) from lcpol where grpcontno='"+contNo+"' and occupationtype='6' and riskcode='"+riskcode+"'";
	var varity6=easyExecSql(strSQL);
	strSQL = "select count(*) from lcpol where grpcontno='"+contNo+"' and (occupationtype is null or occupationtype='T') and riskcode='"+riskcode+"'";
	var varity7=easyExecSql(strSQL);
	strSQL = "select count(*) from lcpol where grpcontno='"+contNo+"' and riskcode='"+riskcode+"'";
	var varity=easyExecSql(strSQL);
	if(varity!="0" && varity!="null")
	{		
			varity1=((varity1/varity)*100).toString()+"%";
			varity2=((varity2/varity)*100).toString()+"%";  
			varity3=((varity3/varity)*100).toString()+"%"; 
			varity4=((varity4/varity)*100).toString()+"%";
  		varity5=((varity5/varity)*100).toString()+"%";
			varity6=((varity6/varity)*100).toString()+"%";    
			varity7=((varity7/varity)*100).toString()+"%";       
	}else{
	           varity1="0"+"%";
	           varity2="0"+"%";
	           varity3="0"+"%";
	           varity4="0"+"%";
	           varity5="0"+"%";
	           varity6="0"+"%";
	           varity7="0"+"%";
         }
	GrpGrid.setRowColData(0, 1, "1类职业");
	GrpGrid.setRowColData(0, 2, varity1);
	GrpGrid.setRowColData(1, 1, "2类职业");
	GrpGrid.setRowColData(1, 2, varity2);
	GrpGrid.setRowColData(2, 1, "3类职业");
	GrpGrid.setRowColData(2, 2, varity3);
	GrpGrid.setRowColData(3, 1, "4类职业");
	GrpGrid.setRowColData(3, 2, varity4);
	GrpGrid.setRowColData(4, 1, "5类职业");
	GrpGrid.setRowColData(4, 2, varity5);
	GrpGrid.setRowColData(5, 1, "6类职业");
	GrpGrid.setRowColData(5, 2, varity6);
	GrpGrid.setRowColData(6, 1, "特殊职业");
	GrpGrid.setRowColData(6, 2, varity7);
	///投保人员年龄分布
	strSQL = "Select count(*) from lcpol where months_between(sysdate,insuredbirthday)>6 and months_between(sysdate,insuredbirthday)<=48 and grpcontno='"+contNo+"' and riskcode='"+riskcode+"'";
  varity1=easyExecSql(strSQL);
	strSQL = "Select count(*) from lcpol where months_between(sysdate,insuredbirthday)>48 and months_between(sysdate,insuredbirthday)<=180 and grpcontno='"+contNo+"' and riskcode='"+riskcode+"'";
  varity2=easyExecSql(strSQL);
  strSQL = "Select count(*) from lcpol where months_between(sysdate,insuredbirthday)>180 and months_between(sysdate,insuredbirthday)<=360 and grpcontno='"+contNo+"' and riskcode='"+riskcode+"'";
  varity3=easyExecSql(strSQL);
  strSQL = "Select count(*) from lcpol where months_between(sysdate,insuredbirthday)>360 and months_between(sysdate,insuredbirthday)<=480 and grpcontno='"+contNo+"' and riskcode='"+riskcode+"'";
  varity4=easyExecSql(strSQL);
  strSQL = "Select count(*) from lcpol where months_between(sysdate,insuredbirthday)>480 and months_between(sysdate,insuredbirthday)<=600 and grpcontno='"+contNo+"' and riskcode='"+riskcode+"'";
  varity5=easyExecSql(strSQL);
  strSQL = "Select count(*) from lcpol where months_between(sysdate,insuredbirthday)>600 and months_between(sysdate,insuredbirthday)<=720 and grpcontno='"+contNo+"' and riskcode='"+riskcode+"'";
  varity6=easyExecSql(strSQL);
  strSQL = "Select count(*) from lcpol where months_between(sysdate,insuredbirthday)>720 and months_between(sysdate,insuredbirthday)<=780 and grpcontno='"+contNo+"' and riskcode='"+riskcode+"'";
  varity7=easyExecSql(strSQL);
  strSQL = "Select count(*) from lcpol where months_between(sysdate,insuredbirthday)>780 and grpcontno='"+contNo+"' and riskcode='"+riskcode+"'";
  varity8=easyExecSql(strSQL);
  strSQL = "Select count(*) from lcpol where grpcontno='"+contNo+"' and riskcode='"+riskcode+"'";
  varity=easyExecSql(strSQL);
  if(varity!=0)
  {
  	  varity1=((varity1/varity)*100).toString()+"%";
			varity2=((varity2/varity)*100).toString()+"%";  
			varity3=((varity3/varity)*100).toString()+"%"; 
			varity4=((varity4/varity)*100).toString()+"%";
  		varity5=((varity5/varity)*100).toString()+"%";
			varity6=((varity6/varity)*100).toString()+"%";    
			varity7=((varity7/varity)*100).toString()+"%";  
			varity8=((varity8/varity)*100).toString()+"%";  
  	
}else{
             varity1="0"+"%";
	           varity2="0"+"%";
	           varity3="0"+"%";
	           varity4="0"+"%";
	           varity5="0"+"%";
	           varity6="0"+"%";
	           varity7="0"+"%";
             varity8="0"+"%";

}
  SelfGrpGrid.setRowColData(0, 1, "6个月-4岁");
	SelfGrpGrid.setRowColData(0, 2, varity1);
	SelfGrpGrid.setRowColData(1, 1, "5-15岁");
	SelfGrpGrid.setRowColData(1, 2, varity2);
	SelfGrpGrid.setRowColData(2, 1, "16-30岁");
	SelfGrpGrid.setRowColData(2, 2, varity3);
	SelfGrpGrid.setRowColData(3, 1, "31－40岁");
	SelfGrpGrid.setRowColData(3, 2, varity4);
	SelfGrpGrid.setRowColData(4, 1, "41－50岁");
	SelfGrpGrid.setRowColData(4, 2, varity5);
	SelfGrpGrid.setRowColData(5, 1, "51－60岁");
	SelfGrpGrid.setRowColData(5, 2, varity6);
	SelfGrpGrid.setRowColData(6, 1, "61-64岁");
	SelfGrpGrid.setRowColData(6, 2, varity7);
	SelfGrpGrid.setRowColData(6, 1, "65以上");
	SelfGrpGrid.setRowColData(6, 2, varity8);
	//投保人员性别分布
	strSQL = "Select count(*) from lcpol where months_between(sysdate,insuredbirthday)>240 and months_between(sysdate,insuredbirthday)<=420 and grpcontno='"+contNo+"' and insuredsex='1' and riskcode='"+riskcode+"'";
  varity1=easyExecSql(strSQL);
  strSQL = "Select count(*) from lcpol where insuredsex='1' and grpcontno='"+contNo+"' and riskcode='"+riskcode+"'";
  varity2=easyExecSql(strSQL);
  strSQL = "Select count(*) from lcpol where grpcontno='"+contNo+"' and riskcode='"+riskcode+"'";
  varity=easyExecSql(strSQL);
  
  if(varity!=0 && varity!=null)
  {
  	varity1=((varity1/varity)*100).toString()+"%";
		varity2=((varity2/varity)*100).toString()+"%";  
  }else{
  varity1="0"+"%";
	varity2="0"+"%";
}
  SexGrpGrid.setRowColData(0, 1, "20-35岁女员工占比");
	SexGrpGrid.setRowColData(0, 2, varity1);
	SexGrpGrid.setRowColData(1, 1, "女性员工占比");
	SexGrpGrid.setRowColData(1, 2, varity2);
	//投保单位性质
	strSQL = "select code,codename From ldcode where codetype='grpnature' and code in (select grpnature From LDGrp where customerno in (select customerno from lcgrpappnt where grpcontno='"+contNo+"'))";
  varity=easyExecSql(strSQL);
  fm.BusinessType.value=varity[0][0];
  fm.BusinessTypeName.value=varity[0][1]
  //其他风险要素
  strSQL = "select RiskElementRate From LCGrpPolRiskElement where grpcontno='"+contNo+"' and RiskElement='1'";
  varity=easyExecSql(strSQL);
  if(varity!=null)
  {
  	fm.coorate1.value=varity;
  }
  strSQL = "select RiskElementRate From LCGrpPolRiskElement where grpcontno='"+contNo+"' and RiskElement='2'";
  varity=easyExecSql(strSQL);
  if(varity!=null)
  {
  	fm.coorate2.value=varity;
  }
  strSQL = "select RiskElementRate From LCGrpPolRiskElement where grpcontno='"+contNo+"' and RiskElement='3'";
  varity=easyExecSql(strSQL);
  if(varity!=null)
  {
  	fm.coorate3.value=varity;
  }
  strSQL = "select RiskElementRate From LCGrpPolRiskElement where grpcontno='"+contNo+"' and RiskElement='4'";
  varity=easyExecSql(strSQL);
  if(varity!=null)
  {
  	fm.coorate4.value=varity;
  }
  strSQL = "select RiskElementRate From LCGrpPolRiskElement where grpcontno='"+contNo+"' and RiskElement='5'";
  varity=easyExecSql(strSQL);
  if(varity!=null)
  {
  	fm.coorate5.value=varity;
  }
  strSQL = "select RiskElementRate From LCGrpPolRiskElement where grpcontno='"+contNo+"' and RiskElement='6'";
  varity=easyExecSql(strSQL);
  if(varity!=null)
  {
  	fm.coorate6.value=varity;
  }
  strSQL = "select sum(RiskElementRate) From LCGrpPolRiskElement where grpcontno='"+contNo+"' group by RiskElementRate";
  varity=easyExecSql(strSQL);
  if(varity!=null)
  {
  	fm.coorate7.value=varity;
  }
	return true;
}












