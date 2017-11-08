var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var k = 0;
var strOperate = "like";
function cooratesave()
{
    for (i = 1; i <= 6; i++)
    {
        
        var tCoorate = document.all('coorate'+i).value;
        tLen = tCoorate.length ;
		if (tCoorate.indexOf('%') == tLen -1 && tLen > 1)
		{
		    tCoorate = tCoorate.substring(0,tLen -1);		       
		}
	    if (isNumeric(tCoorate) == false)
	    { 
   		    alert("请正确填写调整比例！");
    		document.all('coorate' + i).focus();
    		document.all('coorate' + i).select();
    		return;
	    }
    }
	
	var tOrirate = fm.orirate.value;
	if (tOrirate.indexOf('%') == tOrirate.length -1 && tOrirate.length > 1)
	{
	    tOrirate = tOrirate.substring(0,tOrirate.length -1);		       
	}
    if (isNumeric(tOrirate) == false)
    { 
	    alert("请输入正确的既往理赔率！");
		fm.orirate.focus();
		fm.orirate.select();
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
			varity1=(((varity1/varity)+(varity2/varity))*100).toString()+"%";
		//	varity2=((varity2/varity)*100).toString()+"%";  
			varity3=(((varity3/varity)+(varity4/varity))*100).toString()+"%"; 
			//varity4=((varity4/varity)*100).toString()+"%";
  		varity5=(((varity5/varity)+(varity6/varity))*100).toString()+"%";
			//varity6=((varity6/varity)*100).toString()+"%";    
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
	GrpGrid.setRowColData(0, 1, "1,2类职业");
	GrpGrid.setRowColData(0, 2, varity1);
	GrpGrid.setRowColData(1, 1, "3,4类职业");
	GrpGrid.setRowColData(1, 2, varity3);
	GrpGrid.setRowColData(2, 1, "5,6类职业");
	GrpGrid.setRowColData(2, 2, varity5);
  //GrpGrid.setRowColData(3, 1, "4类职业");
  //GrpGrid.setRowColData(3, 2, varity4);
	//GrpGrid.setRowColData(4, 1, "5类职业");
	//GrpGrid.setRowColData(4, 2, varity5);
	//GrpGrid.setRowColData(5, 1, "6类职业");
	//GrpGrid.setRowColData(5, 2, varity6);
	GrpGrid.setRowColData(3, 1, "特殊职业");
	GrpGrid.setRowColData(3, 2, varity7);
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
  strSQL="select round((select avg(insuredappage) from lcpol where grpcontno='"+contNo+"' and riskcode='"+riskcode+"'),2) from dual ";
  varity9=easyExecSql(strSQL);
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
  	  varity9=(varity9).toString();
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
	SelfGrpGrid.setRowColData(7, 1, "65以上");
	SelfGrpGrid.setRowColData(7, 2, varity8);
	SelfGrpGrid.setRowColData(8, 1, "投保人员平均年龄");
	SelfGrpGrid.setRowColData(8, 2, varity9);
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
  strSQL = "select RiskElementRate From LCGrpPolRiskElement where grpcontno='"+contNo+"' and RiskElement='1'" +" and riskcode='"+riskcode+"'";
  varity=easyExecSql(strSQL);
  if(varity!=null)
  {
  	fm.coorate1.value=varity + "%";
  }
  strSQL = "select RiskElementRate From LCGrpPolRiskElement where grpcontno='"+contNo+"' and RiskElement='2'" +" and riskcode='"+riskcode+"'";
  varity=easyExecSql(strSQL);
  if(varity!=null)
  {
  	fm.coorate2.value=varity + "%";
  }
  strSQL = "select RiskElementRate From LCGrpPolRiskElement where grpcontno='"+contNo+"' and RiskElement='3'" +" and riskcode='"+riskcode+"'";
  varity=easyExecSql(strSQL);
  if(varity!=null)
  {
  	fm.coorate3.value=varity + "%";
  }
  strSQL = "select RiskElementRate From LCGrpPolRiskElement where grpcontno='"+contNo+"' and RiskElement='4'"+" and riskcode='"+riskcode+"'";
  varity=easyExecSql(strSQL);
  if(varity!=null)
  {
  	fm.coorate4.value=varity + "%";
  }
  strSQL = "select RiskElementRate,StandByflag1 From LCGrpPolRiskElement where grpcontno='"+contNo+"' and RiskElement='5'"+" and riskcode='"+riskcode+"'";
  varity=easyExecSql(strSQL);
  if(varity!=null)
  {
  	fm.coorate5.value=varity[0][0] + "%";
  	fm.orirate.value=varity[0][1] + "%";
  }
  strSQL = "select RiskElementRate From LCGrpPolRiskElement where grpcontno='"+contNo+"' and RiskElement='6'"+" and riskcode='"+riskcode+"'";
  varity=easyExecSql(strSQL);
  if(varity!=null)
  {
  	fm.coorate6.value=varity + "%";
  }
  strSQL = "select sum(RiskElementRate) From LCGrpPolRiskElement where grpcontno='"+contNo+"' and riskcode='"+riskcode+"' and RiskElement!='6'";
  varity=easyExecSql(strSQL);
  if(varity==null || varity == "null" || varity == "")
  {
  	fm.coorate7.value = "0%";
  }
  else{
    fm.coorate7.value=varity + "%";
  }
  return true;
}

function showTotalCoorate(){
    var tTotalCoorate = 0;
    var tLen = 0;
    for (i = 1; i <= 5; i++)
    {
        var tCoorate = document.all('coorate'+i).value;
        tLen = tCoorate.length ;
		if (tCoorate.indexOf('%') == tLen -1 && tLen > 1)
		{
		    if (isNumeric(tCoorate.substring(0,tLen -1)) == true)
		    { 
		        tPerCoorate = parseFloat(tCoorate.substring(0,tLen -1));
		        tTotalCoorate = tPerCoorate + tTotalCoorate;
		    }		    
		}
		else
		{
   		    if (isNumeric(tCoorate) == true)
		    { 
       		    tTotalCoorate = parseFloat(tCoorate)*100 + tTotalCoorate;
    		    document.all('coorate'+i).value = mathRound(parseFloat(tCoorate)*100) + '%';
		    }
		}
        
    }
    fm.coorate7.value = mathRound(tTotalCoorate) + "%";
}











