//程序名称：DirectContInput.js
//程序功能：直销险种录入页面需要处理的函数和事件
//创建日期： 2006-1-20 10:13
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
// 该文件中包含客户端需要处理的函数和事件

var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();
var queryoperate="";
var showInfo;
var approvefalg;

//清除页面表单数据-----合同信息栏、投保人栏、交费方式信息、被保人信息、被保人告知信息、险种信息栏
function clearPageData()
{
	
}

//初始化时，查询合同信息、投保人信息(包括交费信息)、被保人信息(包括告知信息)，险种信息，并显示在界面
function initQuery()
{
	initQueryCont();//【初始化时查询合同信息】
	initqueryAppnt();//【查询投保人信息】－－－包括投保人的地址信息
	initqueryAccount();//【缴费信息查询】------查询保单的缴费信息，包括缴费方式及帐号信息等。
	initqueryInsured();//【查询被保人信息】－－－包括被保人的地址信息
	DivLCInsuredInfo.style.display="";//将被保人信息查询出来
	initqueryImpartInsuredGrid(); //【查询被保人告知信息】－－－包括被保人的告知信息
	initqueryPolGrid();//【查询保单的险种信息】－－－查询保单的险种信息
	getInputFieldName();	//根据页面输入框的代码带出汉字，并显示出来。
	if (LoadFlag == "5" && scantype == "scan")
	    initQueryRollSpeed();
}
//【初始化时查询合同信息】
function initQueryCont()
{
	var sSQL="";
	if(document.all('ContNo').value==null ||document.all('ContNo').value=="" || document.all('ContNo').value=="null")
	{
//		var sSQL="select contno,proposalcontno,prtno,polapplydate,selltype,salechnl,managecom,agentcode,cvalidate,getpolmode"
//			+" from lccont where prtno='"+fm.PrtNo.value+"' ";
		
		var sqlid0="DirectContInputSql0";
		var mySql0=new SqlClass();
		mySql0.setResourceName("app.DirectContInputSql"); //指定使用的properties文件名
		mySql0.setSqlId(sqlid0);//指定使用的Sql的id
		mySql0.addSubPara(fm.PrtNo.value);//指定传入的参数
		var sSQL=mySql0.getString();
		
	}
	else
	{
//		var sSQL="select contno,proposalcontno,prtno,polapplydate,selltype,salechnl,managecom,agentcode,cvalidate,getpolmode"
//			+" from lccont where contno='"+fm.ContNo.value+"' ";
		
		var sqlid1="DirectContInputSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("app.DirectContInputSql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(fm.ContNo.value);//指定传入的参数
		var sSQL=mySql1.getString();
		
	}
	var ArrCont=easyExecSql(sSQL);
	if(ArrCont!=null)
	{
	fm.ContNo.value=ArrCont[0][0];	
	fm.ProposalContNo.value=ArrCont[0][1];	
	fm.PrtNo.value=ArrCont[0][2];	
	fm.PolApplyDate.value=ArrCont[0][3];	
	fm.SellType.value=ArrCont[0][4];	
	fm.SaleChnl.value=ArrCont[0][5];	
	fm.ManageCom.value=ArrCont[0][6];	
	//fm.AgentCode.value=ArrCont[0][7];	
	fm.CValiDate.value=ArrCont[0][8];	
	fm.CSplit.value=ArrCont[0][9];	
	showOneCodeName('sellType','SellType','SellTypeName');
	showOneCodeName('comcode','ManageCom','ManageComName');
	showOneCodeName('commisionratio','CSplit','CSplitName');
	initqueryAgent();//【初始化时查询合同业务员信息】－－－包括业务员及话务员信息
	}
}
//【初始化时查询合同业务员信息】－－－－－－－－－包括业务员及话务员信息
function initqueryAgent()
{
	if(fm.ContNo.value=="") {return true;}
	//查询外务员信息 laagent.branchtype = '5' and laagent.branchtype2 = '02'--外务员
//	var strSQL = "select a.agentcode,a.agentgroup,a.managecom,a.name,c.branchmanager,"
//	    +" b.introagency,b.agentseries,b.agentgrade,c.branchattr,b.ascriptseries,c.name "
//		+" from laagent a,latree b,labranchgroup c where 1=1 "
//		+" and a.agentcode = b.agentcode and a.agentgroup = c.agentgroup "
//		+" and a.branchtype='5' and a.branchtype2='02'"
//		+" and a.agentcode in "
//		+" (select agentcode from lacommisiondetail where grpcontno='"+fm.ContNo.value+"')";
	
	var sqlid2="DirectContInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName("app.DirectContInputSql"); //指定使用的properties文件名
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	mySql2.addSubPara(fm.ContNo.value);//指定传入的参数
	var strSQL=mySql2.getString();
	
	var arrAgent = easyExecSql(strSQL); 
	if (arrAgent != null)
	{
	fm.AgentCode.value = arrAgent[0][0];
	fm.AgentBranchAttr.value = arrAgent[0][8];
	fm.AgentGroup.value = arrAgent[0][1];
	fm.AgentName.value = arrAgent[0][3];
	fm.AgentManageCom.value = arrAgent[0][2];
	}
//	//查询外务员信息 laagent.branchtype = '5' and laagent.branchtype2 = '01'--话务员
//	var sSQL = "select a.agentcode,a.agentgroup,a.managecom,a.name,c.branchmanager,"
//	    +" b.introagency,b.agentseries,b.agentgrade,c.branchattr,b.ascriptseries,c.name "
//		+" from laagent a,latree b,labranchgroup c where 1=1 "
//		+" and a.agentcode = b.agentcode and a.agentgroup = c.agentgroup "
//		+" and a.branchtype='5' and a.branchtype2='01'"
//		+" and a.agentcode in "
//		+" (select agentcode from lacommisiondetail where grpcontno='"+fm.ContNo.value+"')";
	
	var sqlid3="DirectContInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName("app.DirectContInputSql"); //指定使用的properties文件名
	mySql3.setSqlId(sqlid3);//指定使用的Sql的id
	mySql3.addSubPara(fm.ContNo.value);//指定传入的参数
	var sSQL=mySql3.getString();
	
	var arrTelephon=easyExecSql(sSQL); 
	if (arrTelephon!= null)
	{
		fm.TelephonistCode.value = arrTelephon[0][0];
		fm.TelephBranchAttr.value = arrTelephon[0][8];
		fm.TelephGroup.value = arrTelephon[0][1];
		fm.TelephonistName.value = arrTelephon[0][3];
		fm.TelephManageCom.value = arrTelephon[0][2];
	}
	
	if((fm.AgentCode.value==null ||fm.AgentCode.value=="") 
	   && (fm.TelephonistCode.value==null ||fm.TelephonistCode.value=="") )
	{
//		var stSQL = "select a.agentcode,a.agentgroup,a.managecom,a.name,c.branchmanager,"
//	    +" b.introagency,b.agentseries,b.agentgrade,c.branchattr,b.ascriptseries,c.name "
//		+" from laagent a,latree b,labranchgroup c where 1=1 "
//		+" and a.agentcode = b.agentcode and a.agentgroup = c.agentgroup "
//		+" and a.agentcode in "
//		+" (select agentcode from lacommisiondetail where grpcontno='"+fm.ContNo.value+"')";
		
		var sqlid4="DirectContInputSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName("app.DirectContInputSql"); //指定使用的properties文件名
		mySql4.setSqlId(sqlid4);//指定使用的Sql的id
		mySql4.addSubPara(fm.ContNo.value);//指定传入的参数
		var stSQL=mySql4.getString();
		
		var arrSAgent = easyExecSql(stSQL); 
		if (arrSAgent != null)
		{
		fm.AgentCode.value = arrSAgent[0][0];
		fm.AgentBranchAttr.value = arrSAgent[0][8];
		fm.AgentGroup.value = arrSAgent[0][1];
		fm.AgentName.value = arrSAgent[0][3];
		fm.AgentManageCom.value = arrSAgent[0][2];
		}
	}   
	
}

//【查询投保人信息】－－－包括投保人的地址信息
function initqueryAppnt()
{
	if(fm.ContNo.value=="") {return true;}
	clearAppntInfoData();//清除投保人信息
	//根据"合同号"查询出投保人信息，并填入相应输入栏
//	var sSQL="select contno,appntno,addressno,(select vipvalue from ldperson where customerno=appntno),"
//			+"appntname,appntsex,appntbirthday,idtype,idno,"
//			+"marriage,nativeplace,licensetype,occupationcode,occupationtype,"
//			+"makedate,maketime,modifydate,modifytime "
//			+" from lcappnt where contno='"+fm.ContNo.value+"'";
	
	var sqlid5="DirectContInputSql5";
	var mySql5=new SqlClass();
	mySql5.setResourceName("app.DirectContInputSql"); //指定使用的properties文件名
	mySql5.setSqlId(sqlid5);//指定使用的Sql的id
	mySql5.addSubPara(fm.ContNo.value);//指定传入的参数
	var sSQL=mySql5.getString();
	
	var arrApp=easyExecSql(sSQL); 
	if(arrApp!=null)
	{
		document.all("AppntNo").value=arrApp[0][1];
		document.all("AppntAddressNo").value=arrApp[0][2];
		//queryAppntNo(trim(document.all("AppntNo").value));
		fm.AppntVIPFlag.value=arrApp[0][3];
		fm.AppntName.value=arrApp[0][4];
		fm.AppntSex.value=arrApp[0][5];
		fm.AppntBirthday.value=arrApp[0][6];
		fm.AppntIDType.value=arrApp[0][7];
		fm.AppntIDNo.value=arrApp[0][8];
		fm.AppntMarriage.value=arrApp[0][9];
		fm.AppntNativePlace.value=arrApp[0][10];
		fm.AppntLicenseType.value=arrApp[0][11];
		fm.AppntOccupationCode.value=arrApp[0][12];
		fm.AppntOccupationType.value=arrApp[0][13];
		fm.AppntMakeDate.value=arrApp[0][14];
		fm.AppntMakeTime.value=arrApp[0][15];
		fm.AppntModifyDate.value=arrApp[0][16];
		fm.AppntModifyTime.value=arrApp[0][17];
		//查询投保人地址信息
		if(document.all("AppntNo").value=="" || document.all("AppntAddressNo").value=="") {return true;}
//		var sAppAddSQL="select customerno,addressno,province,city,county,postaladdress,zipcode,"
//			+ " mobile,companyphone,homephone,phone,fax,grpname,email"
//			+ " from lcaddress where customerno='"+document.all("AppntNo").value+"' "
//			+ " and addressno='"+document.all("AppntAddressNo").value+"'";
		
		var sqlid6="DirectContInputSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName("app.DirectContInputSql"); //指定使用的properties文件名
		mySql6.setSqlId(sqlid6);//指定使用的Sql的id
		mySql6.addSubPara(document.all("AppntNo").value);//指定传入的参数
		mySql6.addSubPara(document.all("AppntAddressNo").value);//指定传入的参数
		var sAppAddSQL=mySql6.getString();
		
		var arrAppAdd=easyExecSql(sAppAddSQL); 
		if(arrAppAdd!=null)
		{
			fm.AppntAddressNoName.value=arrAppAdd[0][5];  
	     	fm.AppntProvince.value=arrAppAdd[0][2];  
			fm.AppntProvinceName.value=arrAppAdd[0][2];
			fm.AppntCity.value=arrAppAdd[0][3];  
			fm.AppntCityName.value=arrAppAdd[0][3];
			fm.AppntDistrict.value=arrAppAdd[0][4];  
			fm.AppntDistrictName.value=arrAppAdd[0][4];
			fm.AppntPostalAddress.value=arrAppAdd[0][5];  
			fm.AppntZipCode.value=arrAppAdd[0][6];  
			fm.AppntMobile.value=arrAppAdd[0][7];  
			fm.AppntGrpPhone.value=arrAppAdd[0][8];  
			fm.AppntHomePhone.value=arrAppAdd[0][9];  
			fm.AppntPhone.value=arrAppAdd[0][10];  
			fm.AppntFax.value=arrAppAdd[0][11];  
			fm.AppntGrpName.value=arrAppAdd[0][12];  
			fm.AppntEMail.value=arrAppAdd[0][13]; 
		}
	}
}

//【缴费信息查询】------查询保单的缴费信息，包括缴费方式及帐号信息等。
function initqueryAccount()
{
	if(fm.ContNo.value==""){return true;}
	clearPayFeeInfoData();//清除缴费信息输入框
//	var sAccSQL="select contno,newpaymode,newbankcode,newaccname,newbankaccno,paylocation,bankcode,accname,bankaccno "
//		+" from lccont where contno='"+fm.ContNo.value+"'";
	
	var sqlid7="DirectContInputSql7";
	var mySql7=new SqlClass();
	mySql7.setResourceName("app.DirectContInputSql"); //指定使用的properties文件名
	mySql7.setSqlId(sqlid7);//指定使用的Sql的id
	mySql7.addSubPara(fm.ContNo.value);//指定传入的参数
	var sAccSQL=mySql7.getString();
	
	var arrAcc=easyExecSql(sAccSQL);	
	if(arrAcc!=null)
	{
		fm.NewPayMode.value=arrAcc[0][1];
		fm.NewBankCode.value=arrAcc[0][2];
		fm.NewAccName.value=arrAcc[0][3];
		fm.NewBankAccNo.value=arrAcc[0][4];
		fm.SecPayMode.value=arrAcc[0][5];
		fm.SecBankCode.value=arrAcc[0][6];
		fm.SecAccName.value=arrAcc[0][7];
		fm.SecBankAccNo.value=arrAcc[0][8];
	}	
}
//【查询被保人信息】－－－包括被保人的地址信息
function initqueryInsured()
{
	//清除被保人信息栏,并将被保人信息栏显示出来
	clearInsurdInfo();
	DivLCInsuredInfo.style.display="";
	if(fm.ContNo.value==""){return true;}
//	var strSQL ="select insuredno,name,sex,birthday,relationtomaininsured,sequenceno "
//			+" from lcinsured where contno='"+fm.ContNo.value+"'";
	
	var sqlid8="DirectContInputSql8";
	var mySql8=new SqlClass();
	mySql8.setResourceName("app.DirectContInputSql"); //指定使用的properties文件名
	mySql8.setSqlId(sqlid8);//指定使用的Sql的id
	mySql8.addSubPara(fm.ContNo.value);//指定传入的参数
	var strSQL=mySql8.getString();
	
	var turnInsurePage = new turnPageClass();
	turnInsurePage.queryModal(strSQL,InsuredGrid);
	var InsuredNo="";
	if (InsuredGrid.mulLineCount>0)
	{
	try{document.all(InsuredGrid.findSpanID(0)).all('InsuredGridSel').checked =true;} catch(ex) {}
	try{InsuredNo=InsuredGrid.getRowColData(0,1);} catch(ex) {}
	}
	if(fm.ContNo.value=="" || InsuredNo=="") {return true;}
	//根据"合同号"查询出被保人信息，并填入相应输入栏
//	var sSQL="select contno,insuredno,sequenceno,relationtomaininsured,relationtoappnt,addressno,"
//			+"(select vipvalue from ldperson where customerno=insuredno),"
//			+"name,sex,birthday,idtype,idno,"
//			+"marriage,nativeplace,licensetype,occupationcode,occupationtype"
//			+" from lcinsured where contno='"+fm.ContNo.value+"' and insuredno='"+InsuredNo+"' ";
	
	var sqlid9="DirectContInputSql9";
	var mySql9=new SqlClass();
	mySql9.setResourceName("app.DirectContInputSql"); //指定使用的properties文件名
	mySql9.setSqlId(sqlid9);//指定使用的Sql的id
	mySql9.addSubPara(fm.ContNo.value);//指定传入的参数
	mySql9.addSubPara(InsuredNo);//指定传入的参数
	var sSQL=mySql9.getString();
	
	var arrInsured=easyExecSql(sSQL); 
	if(arrInsured!=null)
	{
		document.all("InsuredNo").value=arrInsured[0][1];
		document.all("InsuredAddressNo").value=arrInsured[0][5];
		document.all("SequenceNo").value=arrInsured[0][2];
		document.all("RelationToMainInsured").value=arrInsured[0][3];
		document.all("RelationToAppnt").value=arrInsured[0][4];
		//根据"被保人客户号"查询出其信息，并填入相应输入栏
		//queryInsuredNo(trim(document.all("InsuredNo").value));
		fm.InsuredVIPFlag.value=arrInsured[0][6];
		fm.InsuredName.value=arrInsured[0][7];
		fm.InsuredSex.value=arrInsured[0][8];
		fm.InsuredBirthday.value=arrInsured[0][9];
		fm.InsuredIDType.value=arrInsured[0][10];
		fm.InsuredIDNo.value=arrInsured[0][11];
		fm.InsuredMarriage.value=arrInsured[0][12];
		fm.InsuredNativePlace.value=arrInsured[0][13];
		fm.InsuredLicenseType.value=arrInsured[0][14];
		fm.InsuredOccupationCode.value=arrInsured[0][15];
		fm.InsuredOccupationType.value=arrInsured[0][16];
		//查询被保人的地址信息
		if(document.all("InsuredNo").value=="" || document.all("InsuredAddressNo").value=="") {return;}
//		var sInsuredAddSQL="select customerno,addressno,province,city,county,postaladdress,zipcode,"
//				+ " mobile,companyphone,homephone,phone,fax,grpname,email"
//				+ " from lcaddress where customerno='"+document.all("InsuredNo").value+"' "
//				+ " and addressno='"+document.all("InsuredAddressNo").value+"'";
		
		var sqlid10="DirectContInputSql10";
		var mySql10=new SqlClass();
		mySql10.setResourceName("app.DirectContInputSql"); //指定使用的properties文件名
		mySql10.setSqlId(sqlid10);//指定使用的Sql的id
		mySql10.addSubPara(document.all("InsuredNo").value);//指定传入的参数
		mySql10.addSubPara(document.all("InsuredAddressNo").value);//指定传入的参数
		var sInsuredAddSQL=mySql10.getString();
		
		
		var arrInsuredAdd=easyExecSql(sInsuredAddSQL); 
		if(arrInsuredAdd!=null)
		{
			fm.InsuredAddressNoName.value=arrInsuredAdd[0][5];  
	     	fm.InsuredProvince.value=arrInsuredAdd[0][2];  
			fm.InsuredProvinceName.value=arrInsuredAdd[0][2];
			fm.InsuredCity.value=arrInsuredAdd[0][3];  
			fm.InsuredCityName.value=arrInsuredAdd[0][3];
			fm.InsuredDistrict.value=arrInsuredAdd[0][4];  
			fm.InsuredDistrictName.value=arrInsuredAdd[0][4];
			fm.InsuredPostalAddress.value=arrInsuredAdd[0][5];  
			fm.InsuredZipCode.value=arrInsuredAdd[0][6];  
			fm.InsuredMobile.value=arrInsuredAdd[0][7];  
			fm.InsuredGrpPhone.value=arrInsuredAdd[0][8];  
			fm.InsuredHomePhone.value=arrInsuredAdd[0][9];  
			fm.InsuredPhone.value=arrInsuredAdd[0][10];  
			fm.InsuredFax.value=arrInsuredAdd[0][11];  
			fm.InsuredGrpName.value=arrInsuredAdd[0][12];  
			fm.InsuredEMail.value=arrInsuredAdd[0][13]; 
		}
	}

}

//【查询被保人告知信息】－－－包括被保人的告知信息
function initqueryImpartInsuredGrid() 
{
	var InsuredNo=document.all("InsuredNo").value;
    var ContNo=document.all("ContNo").value;
    if(ContNo=="" || InsuredNo=="") {return true;}
//    var strSQL ="select impartver,impartcode,impartcontent,impartparammodle from lccustomerimpart"
//    		+" where 1=1 and customernotype='1' "
//    		+" and customerno='"+InsuredNo+"' and proposalcontno='"+ContNo+"'";
  
	var sqlid11="DirectContInputSql11";
	var mySql11=new SqlClass();
	mySql11.setResourceName("app.DirectContInputSql"); //指定使用的properties文件名
	mySql11.setSqlId(sqlid11);//指定使用的Sql的id
	mySql11.addSubPara(InsuredNo);//指定传入的参数
	mySql11.addSubPara(ContNo);//指定传入的参数
	var strSQL=mySql11.getString();
    
	var turnPage = new turnPageClass();
	turnPage.queryModal(strSQL,ImpartInsuredGrid);
}
//【查询保单的险种信息】－－－查询保单的险种信息
function initqueryPolGrid()
{
	var InsuredNo=fm.InsuredNo.value;
    var ContNo=document.all("ContNo").value;
    if(ContNo=="" || InsuredNo=="") {return true;}
//   	var strSQL ="select polno,riskcode,(select riskname from lmriskapp where riskcode=lcpol.riskcode),prem,amnt from lcpol where 1=1 "
//   		+" and insuredno='"+InsuredNo+"' and contno='"+ContNo+"'"; 
   	
	var sqlid12="DirectContInputSql12";
	var mySql12=new SqlClass();
	mySql12.setResourceName("app.DirectContInputSql"); //指定使用的properties文件名
	mySql12.setSqlId(sqlid12);//指定使用的Sql的id
	mySql12.addSubPara(InsuredNo);//指定传入的参数
	mySql12.addSubPara(ContNo);//指定传入的参数
	var strSQL=mySql12.getString();
   	
	var turnPolPage = new turnPageClass();
	turnPolPage.queryModal(strSQL,PolGrid);
}

/*************************************************************************************
########################合同页面上需要用到校验函数#########################################
**************************************************************************************/
//清除合同信息	
function clearContInfoData()
{
	//fm.ProposalContNo.value="";   
	//fm.PrtNo.value=""; 
	fm.PolApplyDate.value=""; 
	fm.CValiDate.value="";   
	//fm.SellType.value="";   
	//fm.SellTypeName.value="";  
	fm.SaleChnl.value="";   
	//fm.ManageCom.value="";   
	//fm.ManageComName.value="";  
	fm.highAmntFlag.value="";   
	fm.highAmntFlagName.value="";   
	fm.AgentCode.value=""; 
	fm.AgentBranchAttr.value=""; 
	fm.AgentGroup.value=""; 
	fm.AgentName.value=""; 
	fm.AgentManageCom.value=""; 
	fm.AgentManageComName.value=""; 
	fm.starAgent.value="";
	fm.starAgentName.value="";
	fm.TelephonistCode.value=""; 
	fm.TelephBranchAttr.value=""; 
	fm.TelephGroup.value=""; 
	fm.TelephonistName.value=""; 
	fm.TelephManageCom.value=""; 
	fm.TelephManageComName.value ="";
	fm.StarTelephonist.value=""; 
	fm.StarTelephonistName.value="";
}

//校验投保日期
function checkapplydate()
{
	var tPolApplyDate=trim(fm.PolApplyDate.value);
	if(tPolApplyDate=="") 
	{
		fm.PolApplyDate.value="";
		return ;
	}
	else
	{
		if(getFormatDate(tPolApplyDate)!=null)
		{
			fm.PolApplyDate.value=getFormatDate(tPolApplyDate);
		}
	    else
		{
			alert("录入的合同投保日期有误！");
			fm.PolApplyDate.value="";
		    return ;
		}
	}
	//增加系统对录入的投保日期校验
	if(checkPolDate(fm.ProposalContNo.value,fm.PolApplyDate.value)==false)
	{
		fm.PolApplyDate.value="";
		fm.PolApplyDate.focus();
		return;
	}
}

/*************************************************************************************
########################投保人页面上需要用到校验函数#########################################
**************************************************************************************/
//清除投保人信息	
function clearAppntInfoData()
{
	fm.AppntNo.value="";  
	fm.AppntVIPFlag.value="";
	fm.AppntVIPFlagName.value="";
	fm.AppntName.value="";  
	//fm.AppntIDType.value="";  
	//fm.AppntIDTypeName.value=""; 
	fm.AppntIDNo.value="";  
	fm.AppntSex.value="";  
	fm.AppntSexName.value=""; 
	fm.AppntBirthday.value="";  
	fm.AppntAge.value="";  
	fm.AppntMarriage.value=""; 
	fm.AppntMarriageName.value=""; 
	fm.AppntNativePlace.value="";  
	fm.AppntNativePlaceName.value="";  
	fm.AppntLicenseType.value="";
	fm.AppntLicenseTypeName.value="";  
	fm.AppntOccupationCode.value="";  
	fm.AppntOccupationCodeName.value=""; 
	fm.AppntOccupationType.value="";  
	fm.AppntOccupationTypeName.value="";  
	fm.AppntAddressNo.value="";  
	fm.AppntAddressNoName.value="";
	fm.AppntProvince.value="";  
	fm.AppntProvinceName.value="";
	fm.AppntCity.value="";  
	fm.AppntCityName.value="";
	fm.AppntDistrict.value="";  
	fm.AppntDistrictName.value="";
	fm.AppntPostalAddress.value="";  
	fm.AppntZipCode.value="";  
	fm.AppntMobile.value="";  
	fm.AppntGrpPhone.value="";  
	fm.AppntHomePhone.value="";  
	fm.AppntFax.value="";  
	fm.AppntGrpName.value="";  
	fm.AppntEMail.value="";       
}	
//清除交费信息
function clearPayFeeInfoData()
{
	fm.NewPayMode.value="";
	fm.NewPayModeName.value="";
	fm.NewBankCode.value="";
	fm.NewBankCodeName.value="";
	fm.NewAccName.value="";
	fm.NewBankAccNo.value="";
	fm.SecPayMode.value="";
	fm.SecPayModeName.value="";
	fm.SecBankCode.value="";
	fm.SecBankCodeName.value="";
	fm.SecAccName.value="";
	fm.SecBankAccNo.value="";
}
//获取投保人 职业编码名称和职业类别
function getAppOpName()
{
	fm.AppntOccupationCodeName.value = "";
	showOneCodeName('occupationcode','AppntOccupationCode','AppntOccupationCodeName');
//	var strSql = "select occupationcode,occupationname,occupationtype from ldoccupation where occupationcode='" + fm.AppntOccupationCode.value+"'";
	
	var sqlid13="DirectContInputSql13";
	var mySql13=new SqlClass();
	mySql13.setResourceName("app.DirectContInputSql"); //指定使用的properties文件名
	mySql13.setSqlId(sqlid13);//指定使用的Sql的id
	mySql13.addSubPara(fm.AppntOccupationCode.value);//指定传入的参数
	var strSql=mySql13.getString();
	
	var arrAppOp = easyExecSql(strSql);
	if (arrAppOp != null) 
	{
	fm.AppntOccupationCodeName.value = arrAppOp[0][1];
	fm.AppntOccupationType.value = arrAppOp[0][2];
	showOneCodeName('occupationtype','AppntOccupationType','AppntOccupationTypeName');
	}
	else
	{
	fm.AppntOccupationType.value ="";
	fm.AppntOccupationTypeName.value ="";
	}
}


//获取投保人的地址号
function getAppntAddressNoData()
{
	var tCodeData = "0|";
//	strsql = "select addressno,postaladdress from lcaddress where customerno ='"+fm.AppntNo.value+"'";
	
	var sqlid14="DirectContInputSql14";
	var mySql14=new SqlClass();
	mySql14.setResourceName("app.DirectContInputSql"); //指定使用的properties文件名
	mySql14.setSqlId(sqlid14);//指定使用的Sql的id
	mySql14.addSubPara(fm.AppntNo.value);//指定传入的参数
	strsql=mySql14.getString();
	
    turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);
    if (turnPage.strQueryResult != "")
    {
    	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    	var m = turnPage.arrDataCacheSet.length;
    	var i=0;
    	var j=0;
    	for (i = 0; i < m; i++)
    	{
    		j = i + 1;
    		tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
    	}
    }
    document.all("AppntAddressNo").CodeData=tCodeData;
}
//校验投保人证件号码
function checkAppntIDNo()
{
	if(fm.AppntIDType.value=="" && fm.AppntIDNo.value!="")
	{
		alert("请先选择证件类型！");
		fm.AppntIDNo.value="";
		return ;
	}
	//根据身份证号取出生日和性别
	if(document.all('AppntIDType').value=="0")
	{
		var tBirthday=getBirthdatByIdNo(fm.AppntIDNo.value);
		var tSex=getSexByIDNo(fm.AppntIDNo.value);
		if(tBirthday=="输入的身份证号位数错误"||tSex=="输入的身份证号位数错误")
		{
			alert("输入的身份证号位数错误");
			return ;
		}
		else
		{
			document.all('AppntBirthday').value=tBirthday;
			document.all('AppntSex').value=tSex;
			fm.AppntAge.value=calPolAppntAge(fm.AppntBirthday.value,fm.PolApplyDate.value);
		}
	}
}
//投保人出生日期校验函数
function checkAppBirthDay()
{
	var tAppBirthDay=trim(fm.AppntBirthday.value);
	if(tAppBirthDay=="") 
	{
		fm.AppntBirthday.value="";
		return ;
	}
    else 
	{
		if(getFormatDate(tAppBirthDay)!=null)
		{
			fm.AppntBirthday.value=getFormatDate(tAppBirthDay);
			//获取投保人年龄<投保日期 —— 投保人出生日期>
			fm.AppntAge.value=calPolAppntAge(fm.AppntBirthday.value,fm.PolApplyDate.value);
		}
	    else
		{
			alert("录入的投保人出生日期有误！");
			fm.AppntBirthday.value="";
			fm.AppntAge.value="";
		    return ;
		}
	}
}

//【首、续期账号一致】多选钮---------如果选中则将首期帐号信息赋值给续期帐号
function SameToFirstAccount()
{
	fm.SecPayMode.value="";
	fm.SecBankCode.value="";
	fm.SecAccName.value="";
	fm.SecBankAccNo.value="";
	if(fm.theSameAccount.checked==true)
	{		
	//fm.SecPayMode.value=fm.NewPayMode.value;
	fm.SecBankCode.value=fm.NewBankCode.value;
	fm.SecAccName.value=fm.NewAccName.value;
	fm.SecBankAccNo.value=fm.NewBankAccNo.value;
	}
}

/*************************************************************************************
########################被保人页面上需要用到校验函数#########################################
**************************************************************************************/
//获取被保人 职业编码名称和职业类别
function getInsuredOpName()
{
	fm.InsuredOccupationCodeName.value = "";
	showOneCodeName('occupationcode','InsuredOccupationCode','InsuredOccupationCodeName');
//	var strSql = "select occupationcode,occupationname,occupationtype from ldoccupation where occupationcode='" + fm.InsuredOccupationCode.value+"'";
	
	var sqlid15="DirectContInputSql15";
	var mySql15=new SqlClass();
	mySql15.setResourceName("app.DirectContInputSql"); //指定使用的properties文件名
	mySql15.setSqlId(sqlid15);//指定使用的Sql的id
	mySql15.addSubPara(fm.InsuredOccupationCode.value);//指定传入的参数
	var strSql=mySql15.getString();
	
	var arrInsuredOp = easyExecSql(strSql);
	if (arrInsuredOp != null) 
	{
	fm.InsuredOccupationCodeName.value = arrInsuredOp[0][1];
	fm.InsuredOccupationType.value = arrInsuredOp[0][2];
	showOneCodeName('occupationtype','InsuredOccupationType','InsuredOccupationTypeName');
	}
	else
	{
	fm.InsuredOccupationCodeName.value="";	
	fm.InsuredOccupationType.value ="";
	fm.InsuredOccupationTypeName.value ="";
	}
}

//获取被保人的地址号
function getInsuredAddressNoData()
{
	var tCodeData = "0|";
//	strsql = "select addressno,postaladdress from lcaddress where customerno ='"+fm.InsuredNo.value+"'";
	
	var sqlid16="DirectContInputSql16";
	var mySql16=new SqlClass();
	mySql16.setResourceName("app.DirectContInputSql"); //指定使用的properties文件名
	mySql16.setSqlId(sqlid16);//指定使用的Sql的id
	mySql16.addSubPara(fm.InsuredNo.value);//指定传入的参数
	strsql=mySql16.getString();
	
    turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);
    if (turnPage.strQueryResult != "")
    {
    	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    	var m = turnPage.arrDataCacheSet.length;
    	var i=0;
    	var j=0;
    	for (i = 0; i < m; i++)
    	{
    		j = i + 1;
    		tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
    	}
    }
    document.all("InsuredAddressNo").CodeData=tCodeData;
}

//清除被保人信息
function clearInsurdInfo()
{
	fm.InsuredNo.value=""; 
	fm.InsuredVIPFlag.value=""; 
	fm.InsuredVIPFlagName.value=""; 
	fm.InsuredName.value=""; 
	//fm.InsuredIDType.value=""; 
	//fm.InsuredIDTypeName.value=""; 
	fm.InsuredIDNo.value=""; 
	fm.InsuredSex.value=""; 
	fm.InsuredSexName.value=""; 
	fm.InsuredBirthday.value=""; 
	fm.InsuredAppAge.value=""; 
	fm.InsuredMarriage.value=""; 
	fm.InsuredMarriageName.value=""; 
	fm.InsuredNativePlace.value=""; 
	fm.InsuredNativePlaceName.value=""; 
	fm.InsuredLicenseType.value=""; 
	fm.InsuredLicenseTypeName.value=""; 
	fm.InsuredOccupationCode.value=""; 
	fm.InsuredOccupationCodeName.value=""; 
	fm.InsuredOccupationType.value=""; 
	fm.InsuredOccupationTypeName.value=""; 
	fm.InsuredAddressNo.value=""; 
	fm.InsuredAddressNoName.value=""; 
	fm.InsuredProvince.value=""; 
	fm.InsuredProvinceName.value=""; 
	fm.InsuredCity.value=""; 
	fm.InsuredCityName.value=""; 
	fm.InsuredDistrict.value=""; 
	fm.InsuredDistrictName.value=""; 
	fm.InsuredPostalAddress.value=""; 
	fm.InsuredZipCode.value=""; 
	fm.InsuredMobile.value=""; 
	fm.InsuredGrpPhone.value=""; 
	fm.InsuredHomePhone.value=""; 
	fm.InsuredFax.value=""; 
	fm.InsuredGrpName.value=""; 
	fm.InsuredEMail.value=""; 
}
//校验被保人证件号码
function checkInsuredIDNo()
{
	if(fm.InsuredIDType.value=="" && fm.InsuredIDNo.value!="")
	{
		alert("请先选择证件类型！");
		fm.InsuredIDNo.value="";
		return ;
	}
	//根据身份证号取出生日和性别
	if(document.all('InsuredIDType').value=="0")
	{
		var tBirthday=getBirthdatByIdNo(fm.InsuredIDNo.value);
		var tSex=getSexByIDNo(fm.InsuredIDNo.value);
		if(tBirthday=="输入的身份证号位数错误"||tSex=="输入的身份证号位数错误")
		{
			alert("输入的身份证号位数错误");
			return ;
		}
		else
		{
			document.all('InsuredBirthday').value=tBirthday;
			document.all('InsuredSex').value=tSex;
			fm.InsuredAppAge.value=calPolAppntAge(fm.InsuredBirthday.value,fm.PolApplyDate.value);
		}
	}
}

//被保人出生日期校验函数
function checkInsuredBirthday()
{
	var tInsuredBirthday=trim(fm.InsuredBirthday.value);
	if(tInsuredBirthday=="") 
	{
		fm.InsuredBirthday.value="";
		return ;
	}
    else 
	{
		if(getFormatDate(tInsuredBirthday)!=null)
		{
			fm.InsuredBirthday.value=getFormatDate(tInsuredBirthday);
			//获取被保人年龄<投保日期 —— 投保人出生日期>
			fm.InsuredAppAge.value=calPolAppntAge(fm.InsuredBirthday.value,fm.PolApplyDate.value);
		}
	    else
		{
			alert("录入的被保人出生日期有误！");
			fm.InsuredBirthday.value="";
			fm.InsuredAppAge.value="";
		    return ;
		}
	}
}
/*************************************************************************************
#####################################################################################
**************************************************************************************/

//【外务员查询】函数－－－－查询外务员相关信息并填入相应的项目
function queryAgent()
{
	if(document.all('ManageCom').value=="")
	{
		alert("请先录入管理机构信息！");
		return;
	}
	if(document.all('AgentCode').value == "")	
	{
		//laagent.branchtype = '5' and laagent.branchtype2 = '02'--外务员
	    //laagent.branchtype = '5' and laagent.branchtype2 = '01'--话务员
		fm.AgentBranchAttr.value = "";
		fm.AgentGroup.value = "";
		fm.AgentName.value = "";
		fm.AgentManageCom.value = "";
		StrAgentURL="../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+"&branchtype=502";
		var newWindow = window.open(StrAgentURL,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}
	else
	{
		var cAgentCode = fm.AgentCode.value;
//		var strSQL = "select a.agentcode,a.agentgroup,a.managecom,a.name,c.branchmanager,b.introagency,b.agentseries,b.agentgrade,c.branchattr,b.ascriptseries,c.name "
//			+" from laagent a,latree b,labranchgroup c where 1=1 "
//			+" and a.agentcode = b.agentcode and a.agentgroup = c.agentgroup "
//			+" and a.branchtype = '5' and a.branchtype2 = '02'"
//			+" and a.agentcode='"+cAgentCode+"'";
		
		var sqlid17="DirectContInputSql17";
		var mySql17=new SqlClass();
		mySql17.setResourceName("app.DirectContInputSql"); //指定使用的properties文件名
		mySql17.setSqlId(sqlid17);//指定使用的Sql的id
		mySql17.addSubPara(cAgentCode);//指定传入的参数
		var strSQL=mySql17.getString();
		
		//alert(strSQL);
		var arrResult = easyExecSql(strSQL); 
		//alert(arrResult);
		if (arrResult != null)
		{
			fm.AgentCode.value = arrResult[0][0];
			fm.AgentBranchAttr.value = arrResult[0][8];
			fm.AgentGroup.value = arrResult[0][1];
			fm.AgentName.value = arrResult[0][3];
			fm.AgentManageCom.value = arrResult[0][2];
			fm.ManageCom.value = arrResult[0][2];
			showOneCodeName('comcode','ManageCom','ManageComName');
			showOneCodeName('comcode','AgentManageCom','AgentManageComName');
		}
		else
		{
			alert("编码为:["+document.all('AgentCode').value+"]的外务员不存在，请确认!");
			fm.AgentCode.value="";
			fm.AgentBranchAttr.value = "";
			fm.AgentGroup.value = "";
			fm.AgentName.value = "";
			fm.AgentManageCom.value = "";
		}
	}
}

//【话务员查询】函数－－－－查询话务员相关信息并填入相应的项目
function queryTelephonist()
{
	if(document.all('ManageCom').value=="")
	{
		alert("请先录入管理机构信息！");
		return;
	}
	if(document.all('TelephonistCode').value == "")	
	{
		//laagent.branchtype = '5' and laagent.branchtype2 = '02'--外务员
	    //laagent.branchtype = '5' and laagent.branchtype2 = '01'--话务员
	    fm.TelephonistCode.value="";
		fm.TelephBranchAttr.value = "";
		fm.TelephGroup.value = "";
		fm.TelephonistName.value = "";
		fm.TelephManageCom.value = "";
		StrTelephURL="../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+"&branchtype=501&queryflag=1";
		var newWindow = window.open(StrTelephURL,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}
	else
	{
		var cAgentCode = fm.TelephonistCode.value;
//		var strSQL = "select a.AgentCode,a.AgentGroup,a.ManageCom,a.Name,c.BranchManager,b.IntroAgency,b.AgentSeries,b.AgentGrade,c.BranchAttr,b.AscriptSeries,c.name "
//			+" from LAAgent a,LATree b,LABranchGroup c where 1=1 "
//			+" and a.AgentCode = b.AgentCode and a.AgentGroup = c.AgentGroup "
//			+" and a.branchtype = '5' and a.branchtype2 = '01'"
//			+" and a.AgentCode='"+cAgentCode+"'";
		
		var sqlid18="DirectContInputSql18";
		var mySql18=new SqlClass();
		mySql18.setResourceName("app.DirectContInputSql"); //指定使用的properties文件名
		mySql18.setSqlId(sqlid18);//指定使用的Sql的id
		mySql18.addSubPara(cAgentCode);//指定传入的参数
		var strSQL=mySql18.getString();
		
		var arrResult = easyExecSql(strSQL); 
		if (arrResult != null)
		{
			fm.TelephonistCode.value = arrResult[0][0];
			fm.TelephBranchAttr.value = arrResult[0][8];
			fm.TelephGroup.value = arrResult[0][1];
			fm.TelephonistName.value = arrResult[0][3];
			fm.TelephManageCom.value = arrResult[0][2];
			fm.ManageCom.value = arrResult[0][2];
			showOneCodeName('comcode','ManageCom','ManageComName');
			showOneCodeName('comcode','TelephManageCom','TelephManageComName');
		}
		else
		{
			alert("编码为:["+document.all('TelephonistCode').value+"]的话务员不存在，请确认!");
			fm.TelephonistCode.value="";
			fm.TelephBranchAttr.value = "";
			fm.TelephGroup.value = "";
			fm.TelephonistName.value = "";
			fm.TelephManageCom.value = "";
		}
	}
}


//查询【业务员】返回时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
function afterQuery2(arrResult)
{
  if(arrResult!=null)
  {
	fm.AgentCode.value = arrResult[0][0];
	fm.AgentBranchAttr.value = arrResult[0][8];
	fm.AgentGroup.value = arrResult[0][1];
	fm.AgentName.value = arrResult[0][3];
	fm.AgentManageCom.value = arrResult[0][2];
	fm.ManageCom.value = arrResult[0][2];
	showOneCodeName('comcode','ManageCom','ManageComName');
	showOneCodeName('comcode','AgentManageCom','AgentManageComName');
  }
}

//查询【话务员】返回时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
function afterQuery3(arrResult)
{
  if(arrResult!=null)
  {
	fm.TelephonistCode.value = arrResult[0][0];
	fm.TelephBranchAttr.value = arrResult[0][8];
	fm.TelephGroup.value = arrResult[0][1];
	fm.TelephonistName.value = arrResult[0][3];
	fm.TelephManageCom.value = arrResult[0][2];
	fm.ManageCom.value = arrResult[0][2];
	showOneCodeName('comcode','ManageCom','ManageComName');
	showOneCodeName('comcode','TelephManageCom','TelephManageComName');
  }
}

//【投保人查询】按钮---------查询投保人
//如果没有投保人客户号，则进入查询界面，否则根据"投保人客户号"查询出其信息，并填入相应输入栏
function queryAppnt()
{
	if (LoadFlag != "1" && LoadFlag != "2")
	{
		alert("只能在投保单录入时进行操作！");
		return ;
	}
	else if (trim(document.all("AppntNo").value) == "" && LoadFlag == "1")
	{	//进入查询界面
		showInfo = window.open( "../sys/LDPersonQueryNewMain.jsp?queryFlag=queryAppnt" );
	}
	else
	{
		queryAppntNo(trim(document.all("AppntNo").value));//根据"投保人客户号"查询出其信息，并填入相应输入栏
	}
}
//【投保人查询】－－－－－－根据"投保人客户号"查询出其信息，并填入相应输入栏
function queryAppntNo(AppntNo)
{
	var tAppntNo=trim(AppntNo);
//	var sSQL="select vipvalue,customerno,name,sex,birthday,idtype,idno,marriage,nativeplace,licensetype,occupationcode,occupationtype"
//	 	+" from ldperson where customerno='"+ tAppntNo +"'";
	
	var sqlid19="DirectContInputSql19";
	var mySql19=new SqlClass();
	mySql19.setResourceName("app.DirectContInputSql"); //指定使用的properties文件名
	mySql19.setSqlId(sqlid19);//指定使用的Sql的id
	mySql19.addSubPara(tAppntNo);//指定传入的参数
	var sSQL=mySql19.getString();
	
	var arrApp=easyExecSql(sSQL);
	if(arrApp==null)
	{
		alert("该被保人号码不存在！");
	}
	else
	{
		fm.AppntVIPFlag.value=arrApp[0][0];
		fm.AppntNo.value=arrApp[0][1];
		fm.AppntName.value=arrApp[0][2];
		fm.AppntSex.value=arrApp[0][3];
		showOneCodeName('sex','AppntSex','AppntSexName');
		fm.AppntBirthday.value=arrApp[0][4];
		fm.AppntIDType.value=arrApp[0][5];
		showOneCodeName('idtype','AppntIDType','AppntIDTypeName');
		fm.AppntIDNo.value=arrApp[0][6];
		fm.AppntMarriage.value=arrApp[0][7];
		fm.AppntNativePlace.value=arrApp[0][8];
		fm.AppntLicenseType.value=arrApp[0][9];
		fm.AppntOccupationCode.value=arrApp[0][10];
		fm.AppntOccupationType.value=arrApp[0][11];
		getAppOpName();//获取投保人职业编码-名称,职业类别编码-名称
		getAppntAge();	
	}
}

//【被保人查询】按钮---------查询被保人
//如果没有被保人客户号，则进入查询界面，否则根据"被保人客户号"查询出其信息，并填入相应输入栏
function queryInsured()
{
	
	if (LoadFlag != "1" && LoadFlag != "2")
	{
		alert("只能在投保单录入时进行操作！");
		return ;
	}
	else if (trim(document.all("InsuredNo").value) == "" && LoadFlag == "1")
	{   //进入查询界面
		showInfo = window.open( "../sys/LDPersonQueryNewMain.jsp?queryFlag=queryInsured" );
	}
	else
	{
		queryInsuredNo(trim(document.all("InsuredNo").value));//根据"投保人客户号"查询出其信息，并填入相应输入栏
	}
}

//【投保人查询】－－－－－－根据"投保人客户号"查询出其信息，并填入相应输入栏
function queryInsuredNo(InsuredNo)
{
	var tInsuredNo=trim(InsuredNo);
//	var sSQL="select vipvalue,customerno,name,sex,birthday,idtype,idno,marriage,nativeplace,licensetype,occupationcode,occupationtype"
//	 	+" from ldperson where customerno='"+ tInsuredNo +"'";
	
	var sqlid20="DirectContInputSql20";
	var mySql20=new SqlClass();
	mySql20.setResourceName("app.DirectContInputSql"); //指定使用的properties文件名
	mySql20.setSqlId(sqlid20);//指定使用的Sql的id
	mySql20.addSubPara(tInsuredNo);//指定传入的参数
	var sSQL=mySql20.getString();
	
	var arrInsured=easyExecSql(sSQL);
	if(arrInsured==null)
	{
		alert("该被保人号码不存在！");
	}
	else
	{
		fm.InsuredVIPFlag.value=arrInsured[0][0];
		fm.InsuredNo.value=arrInsured[0][1];
		fm.InsuredName.value=arrInsured[0][2];
		fm.InsuredSex.value=arrInsured[0][3];
		showOneCodeName('sex','InsuredSex','InsuredSexName');
		fm.InsuredBirthday.value=arrInsured[0][4];
		fm.InsuredIDType.value=arrInsured[0][5];
		showOneCodeName('idtype','InsuredIDType','InsuredIDTypeName');
		fm.InsuredIDNo.value=arrInsured[0][6];
		fm.InsuredMarriage.value=arrInsured[0][7];
		fm.InsuredNativePlace.value=arrInsured[0][8];
		fm.InsuredLicenseType.value=arrInsured[0][9];
		fm.InsuredOccupationCode.value=arrInsured[0][10];
		fm.InsuredOccupationType.value=arrInsured[0][11];
		getInsuredOpName();		
		getInsuredAppAge();	
	}
}
//别的页面返回时的结果处理----目前只有 投保人查询返回的结果,将逐步增加
function afterQuery(arrQueryResult)
{
	document.all("AppntNo").value=arrQueryResult[0][0];
	queryAppntNo(trim(document.all("AppntNo").value));
}
//别的页面返回时的结果处理----目前只有 被保人查询返回的结果,将逐步增加
function afterQuery21( arrQueryResult )
{
	document.all("InsuredNo").value=arrQueryResult[0][0];
	queryInsuredNo(trim(document.all("InsuredNo").value));
}

//如点击选择 投保人为被保险人本人项 时的响应函数
function IsSamePerson()
{
	var SamePersonFlag=fm.SamePersonFlag.checked;
	if(SamePersonFlag==true)
	{
		//被保人同投保人，则将投保人信息填入被保人信息栏中，并将被保人信息栏收缩
		fm.RelationToAppnt.value="00";
		showOneCodeName('Relation','RelationToAppnt','RelationToAppntName');
		getInsureFromAppnt();
		DivLCInsuredInfo.style.display="none";
	}
	else
	{
		//否则，清除被保人信息栏,并将被保人信息栏显示出来
		fm.RelationToAppnt.value="";
		fm.RelationToAppntName.value="";
		clearInsurdInfo();
		DivLCInsuredInfo.style.display="";
	}
}

//将投保人信息填入被保人信息栏中，并将被保人信息栏收缩
function getInsureFromAppnt()
{
	fm.InsuredNo.value=fm.AppntNo.value;  
	fm.InsuredVIPFlag.value=fm.AppntVIPFlag.value;
	fm.InsuredVIPFlagName.value=fm.AppntVIPFlagName.value;
	fm.InsuredName.value=fm.AppntName.value;  
	fm.InsuredIDType.value=fm.AppntIDType.value;  
	fm.InsuredIDTypeName.value=fm.AppntIDTypeName.value; 
	fm.InsuredIDNo.value=fm.AppntIDNo.value;  
	fm.InsuredSex.value=fm.AppntSex.value;  
	fm.InsuredSexName.value=fm.AppntSexName.value; 
	fm.InsuredBirthday.value=fm.AppntBirthday.value;  
	fm.InsuredAppAge.value=fm.AppntAge.value;  
	fm.InsuredMarriage.value=fm.AppntMarriage.value; 
	fm.InsuredMarriageName.value=fm.AppntMarriageName.value; 
	fm.InsuredNativePlace.value=fm.AppntNativePlace.value;  
	fm.InsuredNativePlaceName.value=fm.AppntNativePlaceName.value;  
	fm.InsuredLicenseType.value=fm.AppntLicenseType.value;
	fm.InsuredLicenseTypeName.value=fm.AppntLicenseTypeName.value;  
	fm.InsuredOccupationCode.value=fm.AppntOccupationCode.value;  
	fm.InsuredOccupationCodeName.value=fm.AppntOccupationCodeName.value; 
	fm.InsuredOccupationType.value=fm.AppntOccupationType.value;  
	fm.InsuredOccupationTypeName.value=fm.AppntOccupationTypeName.value;  
	fm.InsuredAddressNo.value=fm.AppntAddressNo.value;  
	fm.InsuredAddressNoName.value=fm.AppntAddressNoName.value;
	fm.InsuredProvince.value=fm.AppntProvince.value;  
	fm.InsuredProvinceName.value=fm.AppntProvinceName.value;
	fm.InsuredCity.value=fm.AppntCity.value;  
	fm.InsuredCityName.value=fm.AppntCityName.value;
	fm.InsuredDistrict.value=fm.AppntDistrict.value;  
	fm.InsuredDistrictName.value=fm.AppntDistrictName.value;
	fm.InsuredPostalAddress.value=fm.AppntPostalAddress.value;  
	fm.InsuredZipCode.value=fm.AppntZipCode.value;  
	fm.InsuredMobile.value=fm.AppntMobile.value;  
	fm.InsuredGrpPhone.value=fm.AppntGrpPhone.value;  
	fm.InsuredHomePhone.value=fm.AppntHomePhone.value;  
	fm.InsuredFax.value=fm.AppntFax.value;  
	fm.InsuredGrpName.value=fm.AppntGrpName.value;  
	fm.InsuredEMail.value==fm.AppntEMail.value;  
}

//下拉列表双击后的响应函数
function afterCodeSelect(CodeName, Field)
{
	//获取投保人地址
	if(CodeName=="GetAppntAddressNo")
	{
//		var strSQL=" select customerno,addressno,province,city,county,postaladdress,zipcode,"
//		    +" mobile,companyphone,homephone,phone,fax,grpname,email "
//			+" from lcaddress where addressno='"+fm.AppntAddressNo.value+"' and customerno='"+fm.AppntNo.value+"'";
		
		var sqlid21="DirectContInputSql21";
		var mySql21=new SqlClass();
		mySql21.setResourceName("app.DirectContInputSql"); //指定使用的properties文件名
		mySql21.setSqlId(sqlid21);//指定使用的Sql的id
		mySql21.addSubPara(fm.AppntAddressNo.value);//指定传入的参数
		mySql21.addSubPara(fm.AppntNo.value);//指定传入的参数
		var strSQL=mySql21.getString();
		
        var arrAppAddress=easyExecSql(strSQL);
		fm.AppntAddressNo.value=arrAppAddress[0][1]; 
		fm.AppntAddressNoName.value=arrAppAddress[0][5];   
		fm.AppntProvince.value=arrAppAddress[0][2];  
		fm.AppntProvinceName.value="";
		fm.AppntCity.value=arrAppAddress[0][3];    
		fm.AppntCityName.value="";
		fm.AppntDistrict.value=arrAppAddress[0][4];  
		fm.AppntDistrictName.value="";
		fm.AppntPostalAddress.value=arrAppAddress[0][5];   
		fm.AppntZipCode.value=arrAppAddress[0][6];    
		fm.AppntMobile.value=arrAppAddress[0][7]; 
		fm.AppntGrpPhone.value=arrAppAddress[0][8];  
		fm.AppntHomePhone.value=arrAppAddress[0][9];  
		fm.AppntPhone.value=arrAppAddress[0][10];  
		fm.AppntFax.value=arrAppAddress[0][11];
		fm.AppntGrpName.value=arrAppAddress[0][12];  
		fm.AppntEMail.value=arrAppAddress[0][13];   
		getAddressName('province','AppntProvince','AppntProvinceName');
		getAddressName('city','AppntCity','AppntCityName');
		getAddressName('district','AppntDistrict','AppntDistrictName'); 
	}
	
	//获取被保人地址
	if(CodeName=="GetInsuredAddressNo")
	{
		
//		var strSQL=" select customerno,addressno,province,city,county,postaladdress,zipcode,"
//		    +" mobile,companyphone,homephone,phone,fax,grpname,email "
//			+" from lcaddress where addressno='"+fm.InsuredAddressNo.value+"' and customerno='"+fm.InsuredNo.value+"'";
        
		var sqlid22="DirectContInputSql22";
		var mySql22=new SqlClass();
		mySql22.setResourceName("app.DirectContInputSql"); //指定使用的properties文件名
		mySql22.setSqlId(sqlid22);//指定使用的Sql的id
		mySql22.addSubPara(fm.InsuredAddressNo.value);//指定传入的参数
		mySql22.addSubPara(fm.InsuredNo.value);//指定传入的参数
		var strSQL=mySql22.getString();
		
		var arrInsuredAddress=easyExecSql(strSQL);
		fm.InsuredAddressNo.value=arrInsuredAddress[0][1]; 
		fm.InsuredAddressNoName.value=arrInsuredAddress[0][5];   
		fm.InsuredProvince.value=arrInsuredAddress[0][2];  
		fm.InsuredProvinceName.value="";
		fm.InsuredCity.value=arrInsuredAddress[0][3];    
		fm.InsuredCityName.value="";
		fm.InsuredDistrict.value=arrInsuredAddress[0][4];  
		fm.InsuredDistrictName.value="";
		fm.InsuredPostalAddress.value=arrInsuredAddress[0][5];   
		fm.InsuredZipCode.value=arrInsuredAddress[0][6];    
		fm.InsuredMobile.value=arrInsuredAddress[0][7]; 
		fm.InsuredGrpPhone.value=arrInsuredAddress[0][8];  
		fm.InsuredHomePhone.value=arrInsuredAddress[0][9];  
		fm.InsuredPhone.value=arrInsuredAddress[0][10]; 
		fm.InsuredFax.value=arrInsuredAddress[0][11]; 
		fm.InsuredGrpName.value=arrInsuredAddress[0][12];   
		fm.InsuredEMail.value=arrInsuredAddress[0][13];       
		getAddressName('province','InsuredProvince','InsuredProvinceName');
		getAddressName('city','InsuredCity','InsuredCityName');
		getAddressName('district','InsuredDistrict','InsuredDistrictName');   
	}
}
/*****************************************************************************************
        以下为界面上的按钮处理区域
******************************************************************************************/
//判断代理人是否录入<外务员与话务员必须任意录入一个>,并做佣金比例的调整
function checkAgentInput()
{
	//判断代理人是否录入<外务员与话务员必须任意录入一个>
	if(fm.AgentCode.value=="" && fm.TelephonistCode.value=="")
	{
		alert("外务员与话务员代码均为空。\n外务员与话务员必须任意录入一个！");
		fm.AgentBusiRate.value ="0.00";//外务员佣金比例
		fm.TeleBusiRate.value  ="0.00";//话务员佣金比例
		return false;
	}
	else if(fm.AgentCode.value!="" && fm.TelephonistCode.value!="")
	{//外务员与话务员都录入的情况
		fm.AgentBusiRate.value ="0.50";//外务员佣金比例
		fm.TeleBusiRate.value  ="0.50";//话务员佣金比例
	}
	else if(fm.AgentCode.value!="" && fm.TelephonistCode.value=="")
	{//如果只录入外务员的情况
		fm.AgentBusiRate.value ="1.00";//外务员佣金比例
		fm.TeleBusiRate.value  ="0.00";//话务员佣金比例
	}
	else if(fm.AgentCode.value=="" && fm.TelephonistCode.value!="")
	{//如果只录入话务员的情况
	
		fm.AgentBusiRate.value ="0.00";//外务员佣金比例
		fm.TeleBusiRate.value  ="1.00";//话务员佣金比例
	}
	else {}
	return true;
}

//【保存】按钮－－－－保存录单是的合同信息，投保人信息，交费信息
function addClick()
{
	if(verifyWorkFlow(MissionID,SubMissionID,ActivityID)==false)
	{
		return false;
	}/////////////////校验公共流是否已经流转完毕
	if( verifyInputNB("1") == false ) {return false;}
	//判断代理人是否录入<外务员与话务员必须任意录入一个>,并做佣金比例的调整
	if(checkAgentInput()==false) {return false;}
	//判断是否已经添加过合同信息及投保人
	var ContNo=document.all("ContNo").value;
//	var sqlstr="select forceuwflag from lccont where contno='"+ContNo+"'" ;
	
	var sqlid23="DirectContInputSql23";
	var mySql23=new SqlClass();
	mySql23.setResourceName("app.DirectContInputSql"); //指定使用的properties文件名
	mySql23.setSqlId(sqlid23);//指定使用的Sql的id
	mySql23.addSubPara(ContNo);//指定传入的参数
	var sqlstr=mySql23.getString();
	
	arrResult = easyExecSql(sqlstr,1,0);
	if(arrResult!=null)
	{
		alert("合同信息及投保人信息已经保存！");
		return false;
	}
	//投保人客户号为空，不能有地址号码
	if(fm.AppntNo.value==''&&fm.AppntAddressNo.value!='')
	{
	alert("投保人客户号为空，不能有地址号码");
	return false;
	}
	var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");	
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	document.all( 'fmAction' ).value="INSERT";
	fm.action="./DirectContSave.jsp"
	fm.submit(); //提交
}

//【修改】按钮－－－－保存录单是的合同信息，投保人信息，交费信息
function updateClick()
{
	if(verifyWorkFlow(MissionID,SubMissionID,ActivityID)==false)
	{
		return false;
	}/////////////////校验公共流是否已经流转完毕
	if( verifyInputNB("1") == false ) return false;	
	//判断代理人是否录入<外务员与话务员必须任意录入一个>,并做佣金比例的调整
	if(checkAgentInput()==false) {return false;}
	//判断是否已经添加过合同信息及投保人
	var ContNo=document.all("ContNo").value;
//	var sqlstr="select forceuwflag from lccont where contno='"+ContNo+"'" ;
	
	var sqlid24="DirectContInputSql24";
	var mySql24=new SqlClass();
	mySql24.setResourceName("app.DirectContInputSql"); //指定使用的properties文件名
	mySql24.setSqlId(sqlid24);//指定使用的Sql的id
	mySql24.addSubPara(ContNo);//指定传入的参数
	var sqlstr=mySql24.getString();
	
	arrResult = easyExecSql(sqlstr,1,0);
	if(arrResult==null)
	{
		alert("请先添加合同信息及投保人信息！");
		return false;
	}
	//投保人客户号为空，不能有地址号码
	if(fm.AppntNo.value==''&&fm.AppntAddressNo.value!='')
	{
	alert("投保人客户号为空，不能有地址号码");
	return false;
	}
	
	var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");	
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	document.all( 'fmAction' ).value="UPDATE";
	fm.action="./DirectContSave.jsp"
	fm.submit(); //提交
}

//【删除】按钮－－－－删除录单是的合同信息，投保人信息，交费信息
function deleteClick()
{
	if(verifyWorkFlow(MissionID,SubMissionID,ActivityID)==false)
	{
		return false;
	}/////////////////校验公共流是否已经流转完毕
	//判断是否已经添加过合同信息及投保人
	var ContNo=document.all("ContNo").value;
//	var sqlstr="select forceuwflag from lccont where contno='"+ContNo+"'" ;
	
	var sqlid25="DirectContInputSql25";
	var mySql25=new SqlClass();
	mySql25.setResourceName("app.DirectContInputSql"); //指定使用的properties文件名
	mySql25.setSqlId(sqlid25);//指定使用的Sql的id
	mySql25.addSubPara(ContNo);//指定传入的参数
	var sqlstr=mySql25.getString();
	
	arrResult = easyExecSql(sqlstr,1,0);
	if(arrResult==null)
	{
		alert("请先添加合同信息及投保人信息！");
		return false;
	}
	var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");	
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	
	document.all( 'fmAction' ).value="DELETE";
	fm.action="./DirectContSave.jsp"
	fm.submit(); //提交
}

//【添加被保人】
function addRecord()
{
	if(verifyWorkFlow(MissionID,SubMissionID,ActivityID)==false)
	{
		return false;
	}/////////////////校验公共流是否已经流转完毕
	//判断是否已经添加过合同信息及投保人
	var ContNo=document.all("ContNo").value;
//	var sqlstr="select forceuwflag from lccont where contno='"+ContNo+"'" ;
	
	var sqlid26="DirectContInputSql26";
	var mySql26=new SqlClass();
	mySql26.setResourceName("app.DirectContInputSql"); //指定使用的properties文件名
	mySql26.setSqlId(sqlid26);//指定使用的Sql的id
	mySql26.addSubPara(ContNo);//指定传入的参数
	var sqlstr=mySql26.getString();
	
	arrResult = easyExecSql(sqlstr,1,0);
	if(arrResult==null)
	{
		alert("请先添加合同信息及投保人信息！");
		return false;
	}	
	//被保险人客户号为空，不能有地址号码
	if(fm.InsuredNo.value==''&&fm.InsuredAddressNo.value!='')
	{
	alert("被保险人客户号为空，不能有地址号码");
	return false;
	}
	//校验必录项目
	if( verifyInputNB('2') == false ) return false;
	//判读被保人信息是否保存
//	var sSQL="select 'X' from lcinsured where 1=1 and sequenceno='"+fm.SequenceNo.value+"'"
//		+" and contno='"+fm.ContNo.value+"' and insuredno='"+fm.InsuredNo.value+"'";
	
	var sqlid27="DirectContInputSql27";
	var mySql27=new SqlClass();
	mySql27.setResourceName("app.DirectContInputSql"); //指定使用的properties文件名
	mySql27.setSqlId(sqlid27);//指定使用的Sql的id
	mySql27.addSubPara(fm.SequenceNo.value);//指定传入的参数
	mySql27.addSubPara(fm.ContNo.value);//指定传入的参数
	mySql27.addSubPara(fm.InsuredNo.value);//指定传入的参数
	var sSQL=mySql27.getString();
	
	var arrInsured = easyExecSql(sSQL,1,0);
	if(arrInsured!=null)
	{
		alert("该被保人信息已经保存 或 该客户内部号码已经存在！");
		return false;
	}	
	var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");	
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	
	document.all('ContType').value=ContType;
	document.all( 'BQFlag' ).value = BQFlag;
	fm.action="DirectContInsuredSave.jsp";
	document.all('fmAction').value="INSERT||CONTINSURED";
	fm.submit();
}

//【修改被保人】
function modifyRecord()
{
	if(verifyWorkFlow(MissionID,SubMissionID,ActivityID)==false)
	{
		return false;
	}/////////////////校验公共流是否已经流转完毕
	//判断是否已经添加过合同信息及投保人
	var ContNo=document.all("ContNo").value;
//	var sqlstr="select forceuwflag from lccont where contno='"+ContNo+"'" ;
	
	var sqlid28="DirectContInputSql28";
	var mySql28=new SqlClass();
	mySql28.setResourceName("app.DirectContInputSql"); //指定使用的properties文件名
	mySql28.setSqlId(sqlid28);//指定使用的Sql的id
	mySql28.addSubPara(ContNo);//指定传入的参数
	var sqlstr=mySql28.getString();
	
	arrResult = easyExecSql(sqlstr,1,0);
	if(arrResult==null)
	{
		alert("请先添加合同信息及投保人信息！");
		return false;
	}	
	//校验必录项目
	if (InsuredGrid.mulLineCount==0)
	{
		alert("该被保险人还没有保存，无法进行修改！");
		return false;
	}
	if(fm.InsuredNo.value==''&&fm.InsuredAddressNo.value!='')
	{
	alert("被保险人客户号为空，不能有地址号码");
	return false;
	}
	if( verifyInputNB('2') == false ) return false;

	var tselno=InsuredGrid.getSelNo();
	if(tselno==0)
	{
		alert("请选中需要修改被保人记录");
		return false;
	}
	var tOldInsuredNo=InsuredGrid.getRowColData(tselno-1,1);
	//判断是否更改了被保人的关键信息
//	var SSQL=" select insuredno,name,sex,birthday,idtype,idno,occupationcode"
//		+" from lcinsured where contno='"+fm.ContNo.value+"' and insuredno='"+tOldInsuredNo+"'";
	
	var sqlid29="DirectContInputSql29";
	var mySql29=new SqlClass();
	mySql29.setResourceName("app.DirectContInputSql"); //指定使用的properties文件名
	mySql29.setSqlId(sqlid29);//指定使用的Sql的id
	mySql29.addSubPara(fm.ContNo.value);//指定传入的参数
	mySql29.addSubPara(tOldInsuredNo);//指定传入的参数
	var SSQL=mySql29.getString();
	
	var tOldArr=easyExecSql(SSQL,1,0);
	//获取准备修改的信息,同查询出的信息比较，如果有一项不同，那么查询是否有险种，有则提示需先删除险种信息
	if( fm.InsuredName.value!=tOldArr[0][1] || fm.InsuredIDType.value!=tOldArr[0][4] || fm.InsuredIDNo.value!=tOldArr[0][5] 
	  || fm.InsuredSex.value!=tOldArr[0][2] || fm.InsuredBirthday.value!=tOldArr[0][3] || fm.InsuredOccupationCode.value!=tOldArr[0][6])
	{
//		var sqlstr="select polno from lcpol where contno='"+fm.ContNo.value+"' and insuredno='"+tOldInsuredNo+"' ";
		
		var sqlid30="DirectContInputSql30";
		var mySql30=new SqlClass();
		mySql30.setResourceName("app.DirectContInputSql"); //指定使用的properties文件名
		mySql30.setSqlId(sqlid30);//指定使用的Sql的id
		mySql30.addSubPara(fm.ContNo.value);//指定传入的参数
		mySql30.addSubPara(tOldInsuredNo);//指定传入的参数
		var sqlstr=mySql30.getString();
		
		arrResult=easyExecSql(sqlstr,1,0);
		if(arrResult!=null)
		{
			alert("你可能修改了该被保人关键信息:\n<姓名、性别、证件类型或号码、出生日期>或职业类别。\n请先删除该被保人下的险种信息。");
			return false;    
		}
	}

	var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");	
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
		
	document.all('ContType').value=ContType;
	document.all( 'BQFlag' ).value = BQFlag;
	document.all('fmAction').value="UPDATE||CONTINSURED";
	fm.action="DirectContInsuredSave.jsp";
	fm.submit();
}

//【删除被保人】
function deleteRecord()
{
	if(verifyWorkFlow(MissionID,SubMissionID,ActivityID)==false)
	{
		return false;
	}/////////////////校验公共流是否已经流转完毕
	//判断该被保人是否已经保存
//	var sSQL="select 'X' from lcinsured where contno='"+fm.ContNo.value+"' and insuredno='"+fm.InsuredNo.value+"'";
	
	var sqlid31="DirectContInputSql31";
	var mySql31=new SqlClass();
	mySql31.setResourceName("app.DirectContInputSql"); //指定使用的properties文件名
	mySql31.setSqlId(sqlid31);//指定使用的Sql的id
	mySql31.addSubPara(fm.ContNo.value);//指定传入的参数
	mySql31.addSubPara(fm.InsuredNo.value);//指定传入的参数
	var sSQL=mySql31.getString();
	
	var arrInsure = easyExecSql(sSQL,1,0);
	if(arrInsure==null)
	{
		alert("没有被保人或被保人信息还未保存，请先保存被保人信息！");
		return false;
	}	
	if(PolGrid.mulLineCount>0)
	{
		alert("该被保人下还有险种信息，如果要删除被保人，请先删除险种信息！");
		return false;
	}
	//提交		
	var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");	
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	
	document.all('ContType').value=ContType;
	document.all( 'BQFlag' ).value = BQFlag;
	document.all('fmAction').value="DELETE||CONTINSURED";
	fm.action="DirectContInsuredSave.jsp";
	fm.submit();
}


//问题件影像查询
function QuestPicQuery()
{
	var ContNo = fm.ContNo.value;
	if (ContNo == "") return;
	window.open("../uw/ImageQueryMain.jsp?ContNo="+ContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");
}
//问题件录入
function QuestInput()
{
	if(verifyWorkFlow(MissionID,SubMissionID,ActivityID)==false)
	{
		return false;
	}/////////////////校验公共流是否已经流转完毕
	cContNo = fm.ContNo.value;  //保单号码 
	if(cContNo == "")
	{
		alert("尚无合同投保单号，请先保存!");
		return;
	}
	else
	{
		window.open("../uw/QuestInputMain.jsp?ContNo="+cContNo+"&Flag="+ LoadFlag+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID,"window1");
	}
}
//问题件查询
function QuestQuery()
{
	cContNo = fm.ContNo.value;  //保单号码 
	if(cContNo == "")
	{
		alert("尚无合同投保单号，请先保存!");
	}
	else
	{
		window.open("../uw/QuestQueryMain.jsp?ContNo="+cContNo+"&Flag="+LoadFlag,"window1");
	}
}
//投保人校验
function AppntChk()
{
	var Sex=fm.AppntSex.value;
//	var sqlstr="select customerno from ldperson where 1=1 "
//	+" and name='"+fm.AppntName.value+ "' and customerno<>'"+fm.AppntNo.value+"'" 
//	+" and sex='"+Sex+"' and birthday='"+fm.AppntBirthday.value+ "' "
//	+ " union "
//	+" select customerno from ldperson where 1=1"
//	//+ " and idtype = '"+fm.AppntIDType.value+ "' and idtype is not null "
//	+ " and idno = '"+fm.AppntIDNo.value+ "' and IDNo is not null"
//	+ " and customerno<>'"+fm.AppntNo.value+"'" ;
	
	var sqlid32="DirectContInputSql32";
	var mySql32=new SqlClass();
	mySql32.setResourceName("app.DirectContInputSql"); //指定使用的properties文件名
	mySql32.setSqlId(sqlid32);//指定使用的Sql的id
	mySql32.addSubPara(fm.AppntName.value);//指定传入的参数
	mySql32.addSubPara(fm.AppntNo.value);//指定传入的参数
	mySql32.addSubPara(Sex);//指定传入的参数
	mySql32.addSubPara(fm.AppntBirthday.value);//指定传入的参数
	mySql32.addSubPara(fm.AppntIDNo.value);//指定传入的参数
	mySql32.addSubPara(fm.AppntNo.value);//指定传入的参数
	var sqlstr=mySql32.getString();
	
	arrResult = easyExecSql(sqlstr,1,0);
	if(arrResult==null)
	{
	alert("该没有与该投保人相似的客户,无需校验");
	return false;
	}
	else
	{
	window.open("../uw/AppntChkMain.jsp?ProposalNo1="+fm.ContNo.value+"&Flag=A&LoadFlag="+LoadFlag,"window1");
	}
}

function InsuredChk()
{
	var tSel =InsuredGrid.getSelNo();
	if( tSel == 0 || tSel == null )
	{
		alert("请先选择被保险人！");
		return false;
	}
	var tRow = InsuredGrid.getSelNo() - 1;
	var tInsuredNo=InsuredGrid.getRowColData(tRow,1);
	var tInsuredName=InsuredGrid.getRowColData(tRow,2);
	var tInsuredSex=InsuredGrid.getRowColData(tRow,3);
	var tBirthday=InsuredGrid.getRowColData(tRow,4);
//	var sqlstr= "select customerno from ldperson where 1=1 "
//		+" and name='"+tInsuredName+ "' and customerno<>'"+tInsuredNo+"'"
//		+" and sex='"+tInsuredSex+"' and birthday='"+tBirthday+ "'"
//		+ " union "
//		+ " select customerno from ldperson where 1=1 "
//	    //+ " and idtype = '"+fm.InsuredIDType.value+ "' and idtype is not null "
//		+ " and idno = '"+fm.InsuredIDNo.value+"' and idno is not null "
//		+ " and customerno<>'"+tInsuredNo+"'" ;
	
	var sqlid33="DirectContInputSql33";
	var mySql33=new SqlClass();
	mySql33.setResourceName("app.DirectContInputSql"); //指定使用的properties文件名
	mySql33.setSqlId(sqlid33);//指定使用的Sql的id
	mySql33.addSubPara(tInsuredName);//指定传入的参数
	mySql33.addSubPara(tInsuredNo);//指定传入的参数
	mySql33.addSubPara(tInsuredSex);//指定传入的参数
	mySql33.addSubPara(tBirthday);//指定传入的参数
	mySql33.addSubPara(fm.InsuredIDNo.value);//指定传入的参数
	mySql33.addSubPara(tInsuredNo);//指定传入的参数
	var sqlstr=mySql33.getString();
	
	var arrResult = easyExecSql(sqlstr,1,0);
	if(arrResult==null)
	{
	alert("该没有与该被保人保人相似的客户,无需校验");
	return false;
	}
	window.open("../uw/AppntChkMain.jsp?ProposalNo1="+fm.ContNo.value+"&Flag=I&LoadFlag="+LoadFlag+"&InsuredNo="+tInsuredNo,"window1");
}

/*********************************************************************
 *  PolGrid的RadioBox点击事件，获得被保人险种详细信息
 ********************************************************************/
function getPolDetail(parm1,parm2)
{
    var PolNo=document.all(parm1).all('PolGrid1').value
    try{mSwitch.deleteVar('PolNo')}catch(ex){};
    try{mSwitch.addVar('PolNo','',PolNo);}catch(ex){};
    fm.SelPolNo.value=PolNo;
}

/*********************************************************************
 *  进入险种信息录入
 ********************************************************************/
function intoRiskInfo()
{
	//校验合同信息、投保人信息及被保人信息是否已经保存（）
//	var strSQL="select 'X' from dual where 1=1" 
//  		+" and exists (select 'X' from lccont where contno='"+fm.ContNo.value+"')"
//  		+" and exists (select 'X' from lcappnt where contno='"+fm.ContNo.value+"')" 
//  		+" and exists (select 'X' from lcinsured where contno='"+fm.ContNo.value+"' and insuredno='"+fm.InsuredNo.value+"')";  
	
	var sqlid34="DirectContInputSql34";
	var mySql34=new SqlClass();
	mySql34.setResourceName("app.DirectContInputSql"); //指定使用的properties文件名
	mySql34.setSqlId(sqlid34);//指定使用的Sql的id
	mySql34.addSubPara(fm.ContNo.value);//指定传入的参数
	mySql34.addSubPara(fm.ContNo.value);//指定传入的参数
	mySql34.addSubPara(fm.ContNo.value);//指定传入的参数
	mySql34.addSubPara(fm.InsuredNo.value);//指定传入的参数
 	var strSQL=mySql34.getString();
	
	var arrResult = easyExecSql(strSQL); 
	if(arrResult==null)
	{
		var errInfo="可能发生如下错误：\n 1、合同信息和投保人信息没有保存。\n 2、被保人信息没有保存。";
		alert(errInfo);
		return false;
	}
	////把合同信息和被保人信息放入内存
	mSwitch = parent.VD.gVSwitch;  //桢容错
	delContVar();
	addIntoCont();
	delInsuredVar();
	addInsuredVar();
	try{mSwitch.deleteVar('mainRiskPolNo');}catch(ex){}	
	try{mSwitch.deleteVar('ContNo');}catch(ex){}
	try{mSwitch.addVar('ContNo','',fm.ContNo.value);}catch(ex){}
	try{mSwitch.deleteVar('CValiDate');}catch(ex){}
	try{mSwitch.addVar('CValiDate','',document.all('PolApplyDate').value);}catch(ex){}
	//选择险种单进入险种界面带出已保存的信息
	try{mSwitch.deleteVar('PolNo')}catch(ex){};
    try{mSwitch.addVar('PolNo','',fm.SelPolNo.value);}catch(ex){};
	if ((LoadFlag=='5'||LoadFlag=='4'||LoadFlag=='6'||LoadFlag=='16' || LoadFlag=='25')&&(mSwitch.getVar( "PolNo" ) == null || mSwitch.getVar( "PolNo" ) == ""))
	{
		alert("请先选择被保险人险种信息！");
		return;
	}
	else if((LoadFlag=='1'||LoadFlag=='3') && (PolGrid.mulLineCount>0))
	{	//加入控制：在录单和问题件修改时，如果存在险种必须先选择一个险种后才能进入险种信息
		var tSelPol=PolGrid.getSelNo();
		if(tSelPol==null || tSelPol==0)
		{
			alert("请先选择一个险种后再进入险种信息页面！");
			return;
		}
	}
	else
	{}

	//以下准备进入险种界面
	var strUrl="./ProposalInput.jsp?LoadFlag=" + LoadFlag+"&ContType="+ContType+"&scantype=" + scantype;
	strUrl=strUrl+"&MissionID=" + MissionID+ "&SubMissionID=" + SubMissionID+"&ActivityID="+ActivityID;
	strUrl=strUrl+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&NoType="+NoType+"&hh=1&checktype="+checktype;
	strUrl=strUrl+"&ProposalContNo="+fm.ProposalContNo.value;
	strUrl=strUrl+"&ScanFlag="+ScanFlag+"&BankFlag=5";
	strUrl=strUrl+"&InsuredChkFlag="+document.all('InsuredChkButton').disabled;
	strUrl=strUrl+"&AppntChkFlag="+document.all('AppntChkButton').disabled;
  	parent.fraInterface.window.location =strUrl;
}
//删除险种
function DelRiskInfo()
{
	if(verifyWorkFlow(MissionID,SubMissionID,ActivityID)==false)
	{
		return false;
	}/////////////////校验公共流是否已经流转完毕
	if(fm.InsuredNo.value=="")
	{
		alert("请先选择被保人");
		return false;
	}
	var tSel =PolGrid.getSelNo();
	if( tSel == 0 || tSel == null )
	{
		alert("该客户没有险种或者您忘记选择了？");
		return false;
	}
	else
	{
		var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
		//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");	
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		var tpolno=PolGrid.getRowColData(tSel-1,1)
		document.all('fmAction').value="DELETE||INSUREDRISK";
		fm.action="./DirectDelRiskSave.jsp?polno="+tpolno;
		fm.submit(); //提交
	}
	
}
//查询记事本
function checkNotePad(prtNo,LoadFlag)
{
//	var strSQL="select count(*) from LWNotePad where otherno='"+prtNo+"'";
	
	var sqlid35="DirectContInputSql35";
	var mySql35=new SqlClass();
	mySql35.setResourceName("app.DirectContInputSql"); //指定使用的properties文件名
	mySql35.setSqlId(sqlid35);//指定使用的Sql的id
	mySql35.addSubPara(prtNo);//指定传入的参数
 	var strSQL=mySql35.getString();
	
	var arrResult = easyExecSql(strSQL);
	eval("document.all('NotePadButton"+LoadFlag+"').value='记事本查看 （共"+arrResult[0][0]+"条）'");
}
//记事本查看
function showNotePad()
{
	var tPrtNo = document.all.PrtNo.value;
	if(tPrtNo == null || tPrtNo == "")
	{
		alert("投保单号为空！");
		return;
	} 
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ tPrtNo + "&NoType=1";
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp" + varSrc,"工作流记事本查看","left");
}


/*********************************************************************
 *  把合同所有信息录入结束确认
*********************************************************************/
function inputConfirm(wFlag)
{
    if(checkOtherQuest()==false)
       return false;
	//校验合同信息、投保人信息及被保人信息是否已经保存（）
//	var strSQL="select 'X' from dual where 1=1" 
//  		+" and exists (select 'X' from lccont where contno='"+fm.ContNo.value+"')"
//  		+" and exists (select 'X' from lcappnt where contno='"+fm.ContNo.value+"')" 
//  		+" and exists (select 'X' from lcinsured where contno='"+fm.ContNo.value+"')"
//  		+" and exists (select 'X' from lcpol where contno='"+fm.ContNo.value+"')" ;
	
	var sqlid36="DirectContInputSql36";
	var mySql36=new SqlClass();
	mySql36.setResourceName("app.DirectContInputSql"); //指定使用的properties文件名
	mySql36.setSqlId(sqlid36);//指定使用的Sql的id
	mySql36.addSubPara(fm.ContNo.value);//指定传入的参数
	mySql36.addSubPara(fm.ContNo.value);//指定传入的参数
	mySql36.addSubPara(fm.ContNo.value);//指定传入的参数
	mySql36.addSubPara(fm.ContNo.value);//指定传入的参数
 	var strSQL=mySql36.getString();
	
	var arrResult = easyExecSql(strSQL); 
	if(arrResult==null)
	{
		var errInfo="可能发生如下错误：\n 1、合同信息和投保人信息没有保存。\n 2、被保人信息没有保存。\n 3、险种信息未保存。";
		alert(errInfo);
		return false;
	}
	
	if (wFlag ==1 ) //录入完毕确认
	{
//		var tStr= "	select * from lwmission where 1=1 and lwmission.activityid in ('0000001001','0000001002','0000001220','0000001230') and lwmission.missionprop1 = '"+fm.ContNo.value+"'";
		
		var sqlid37="DirectContInputSql37";
		var mySql37=new SqlClass();
		mySql37.setResourceName("app.DirectContInputSql"); //指定使用的properties文件名
		mySql37.setSqlId(sqlid37);//指定使用的Sql的id
		mySql37.addSubPara(fm.ContNo.value);//指定传入的参数
	 	var tStr=mySql37.getString();
		
		turnPage.strQueryResult = easyQueryVer3(tStr, 1, 0, 1);
		if (turnPage.strQueryResult) 
		{
			alert("该合同已经做过保存！");
			return;
		}
		if(document.all('ProposalContNo').value == "")
		{
			alert("合同信息未保存,不容许您进行 [录入完毕] 确认！");
			return;
		}
		//添加录单费率错误的校验<收银录入的保费与系统生成的保费不符，系统要进行提示，但仍可保存>
		if(checkpayfee(document.all('ProposalContNo').value)==false)
		{
			return false;	
		}
		if(ScanFlag=="1")
		{
			  fm.WorkFlowFlag.value = "0000001099";
		}
		else
		{
			  fm.WorkFlowFlag.value = "0000001098";
		}
		fm.MissionID.value = MissionID;
		fm.SubMissionID.value = SubMissionID;			//录入完毕
	}
	else if (wFlag ==2)//复核完毕确认
	{
		if(document.all('ProposalContNo').value == "")
		{
		alert("未查询出合同信息,不容许您进行 [复核完毕] 确认！");
		return;
		}
		if(fm.AppntChkButton.disabled==false)
		{ 
//			var strSql = "select * from LCIssuePol where contno = '"+fm.ContNo.value+"' and issuetype = '99' and questionobj = '"+document.all('AppntNo').value+"'";
			
			var sqlid38="DirectContInputSql38";
			var mySql38=new SqlClass();
			mySql38.setResourceName("app.DirectContInputSql"); //指定使用的properties文件名
			mySql38.setSqlId(sqlid38);//指定使用的Sql的id
			mySql38.addSubPara(fm.ContNo.value);//指定传入的参数
			mySql38.addSubPara(document.all('AppntNo').value);//指定传入的参数
		 	var strSql=mySql38.getString();
			
			var brrResult = easyExecSql(strSql);
			if(brrResult==null)	
			{
				if(confirm("是否进行投保人校验？")) {return;}
			}
		}	
		if(fm.InsuredChkButton.disabled==false)
		{
//			strSql = "select * from LCIssuePol where contno = '"+fm.ContNo.value+"' and issuetype = '99' and questionobj in (select insuredno from lcinsured where contno='"+fm.ContNo.value+"')";
			
			var sqlid39="DirectContInputSql39";
			var mySql39=new SqlClass();
			mySql39.setResourceName("app.DirectContInputSql"); //指定使用的properties文件名
			mySql39.setSqlId(sqlid39);//指定使用的Sql的id
			mySql39.addSubPara(fm.ContNo.value);//指定传入的参数
			mySql39.addSubPara(fm.ContNo.value);//指定传入的参数
		 	var strSql=mySql39.getString();
			
			var crrResult = easyExecSql(strSql);
			if(crrResult==null)
			{
			if(confirm("是否进行被保人校验？")) {return;}
			}
		}
		fm.WorkFlowFlag.value = "0000001001";					//复核完毕
		fm.MissionID.value = MissionID;
		fm.SubMissionID.value = SubMissionID;
		approvefalg="2";
	}
	  else if (wFlag ==3)
	{
		if(document.all('ProposalContNo').value == "")
		{
			alert("未查询出合同信息,不容许您进行 [复核修改完毕] 确认！");
			return;
		}
		fm.WorkFlowFlag.value = "0000001002";					//复核修改完毕
		fm.MissionID.value = MissionID;
		fm.SubMissionID.value = SubMissionID;
	}
	else if(wFlag == 4)
	{
		 if(document.all('ProposalContNo').value == "")
	   {
	   	  alert("未查询出合同信息,不容许您进行 [修改完毕] 确认！");
	   	  return;
	   }
		fm.WorkFlowFlag.value = "0000001021";					//问题修改
		fm.MissionID.value = MissionID;
		fm.SubMissionID.value = SubMissionID;
	}
	else
		return;

	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.action = "./InputConfirm.jsp";
	fm.submit(); //提交
}
function checkOtherQuest(){
   if(ActivityID=='0000001002')
   {
//   	var tSql ="select missionprop9 from lwmission where activityid = '0000001002' and missionprop1 ='"+prtNo+"' and defaultoperator='"+operator+"' and  processid = '0000000003'";
//	   	var tSql ="select missionprop9 from lwmission where activityid  in (select activityid from lwactivity  where functionid ='10010004') and missionprop1 ='"+prtNo+"' and defaultoperator='"+operator+"' ";
	   
		var sqlid40="DirectContInputSql40";
		var mySql40=new SqlClass();
		mySql40.setResourceName("app.DirectContInputSql"); //指定使用的properties文件名
		mySql40.setSqlId(sqlid40);//指定使用的Sql的id
		mySql40.addSubPara(prtNo);//指定传入的参数
		mySql40.addSubPara(operator);//指定传入的参数
	 	var tSql=mySql40.getString();
	   	
	   	var tOtherQuestFlag = easyExecSql(tSql);
   	if(tOtherQuestFlag !=""&& tOtherQuestFlag!=null)
  	 {
   	   if(tOtherQuestFlag =="1")
   	   {
  	       //有未回复的问题件
  	       alert("还有未回复的问题件，请将所有的的问题件回复后再进行[问题件修改完毕]操作！");
 	        return false;
  	    }
 	  }else{
  	      alert("没有能够查询到是否还有未回复的问题件！请确认后再进行[问题件修改完毕]操作");
 	      if(confirm("是否确认此操作？")==false)
  	      {
  	        return false;
 	      }
	    }
   }
   return true;
}

//【提交后返回】－－－－－返回后刷新页面
function afterSubmit1(FlagStr , Content )
{
	showInfo.close();
	if( FlagStr == "Fail" )
	{
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + Content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	else
	{
		Content = "保存成功！";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + Content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		initQuery();//刷新页面
	}
}

//【提交后返回】－－－－－返回后刷新页面
function afterSubmit(FlagStr , Content,Contract )
{
	showInfo.close();
	if( FlagStr == "Fail" )
	{
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + Content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	else
	{
		Content = "保存成功！";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + Content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		top.close();
	}
}

/*********************************************************************
 *  把集体信息从变量中删除
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function delContVar()
{
	try { mSwitch.deleteVar( "intoPolFlag" ); } catch(ex) { };
	// body信息
	try { mSwitch.deleteVar( "BODY" ); } catch(ex) { };
	// 集体信息
	//由"./AutoCreatLDGrpInit.jsp"自动生成
   try { mSwitch.deleteVar('ContNo'); } catch(ex) { };
   try { mSwitch.deleteVar('ProposalContNo'); } catch(ex) { };
   try { mSwitch.deleteVar('PrtNo'); } catch(ex) { };
   try { mSwitch.deleteVar('GrpContNo'); } catch(ex) { };
   try { mSwitch.deleteVar('ContType'); } catch(ex) { };
   try { mSwitch.deleteVar('FamilyType'); } catch(ex) { };
   try { mSwitch.deleteVar('PolType'); } catch(ex) { };
   try { mSwitch.deleteVar('CardFlag'); } catch(ex) { };
   try { mSwitch.deleteVar('ManageCom'); } catch(ex) { };
   try { mSwitch.deleteVar('AgentCom'); } catch(ex) { };
   try { mSwitch.deleteVar('AgentCode'); } catch(ex) { };
   try { mSwitch.deleteVar('AgentGroup'); } catch(ex) { };
   try { mSwitch.deleteVar('AgentCode1'); } catch(ex) { };
   try { mSwitch.deleteVar('AgentType'); } catch(ex) { };
   try { mSwitch.deleteVar('SaleChnl'); } catch(ex) { };
   try { mSwitch.deleteVar('Handler'); } catch(ex) { };
   try { mSwitch.deleteVar('Password'); } catch(ex) { };
   try { mSwitch.deleteVar('AppntNo'); } catch(ex) { };
   try { mSwitch.deleteVar('AppntName'); } catch(ex) { };
   try { mSwitch.deleteVar('AppntSex'); } catch(ex) { };
   try { mSwitch.deleteVar('AppntBirthday'); } catch(ex) { };
   try { mSwitch.deleteVar('AppntIDType'); } catch(ex) { };
   try { mSwitch.deleteVar('AppntIDNo'); } catch(ex) { };
   try { mSwitch.deleteVar('InsuredNo'); } catch(ex) { };
   try { mSwitch.deleteVar('InsuredNam'); } catch(ex) { };
   try { mSwitch.deleteVar('InsuredSex'); } catch(ex) { };
   try { mSwitch.deleteVar('InsuredBirthday'); } catch(ex) { };
   try { mSwitch.deleteVar('InsuredIDType'); } catch(ex) { };
   try { mSwitch.deleteVar('InsuredIDNo'); } catch(ex) { };
   try { mSwitch.deleteVar('PayIntv'); } catch(ex) { };
   try { mSwitch.deleteVar('PayMode'); } catch(ex) { };
   try { mSwitch.deleteVar('PayLocation'); } catch(ex) { };
   try { mSwitch.deleteVar('DisputedFlag'); } catch(ex) { };
   try { mSwitch.deleteVar('OutPayFlag'); } catch(ex) { };
   try { mSwitch.deleteVar('GetPolMode'); } catch(ex) { };
   try { mSwitch.deleteVar('SignCom'); } catch(ex) { };
   try { mSwitch.deleteVar('SignDate'); } catch(ex) { };
   try { mSwitch.deleteVar('SignTime'); } catch(ex) { };
   try { mSwitch.deleteVar('ConsignNo'); } catch(ex) { };
   try { mSwitch.deleteVar('BankCode'); } catch(ex) { };
   try { mSwitch.deleteVar('BankAccNo'); } catch(ex) { };
   try { mSwitch.deleteVar('AccName'); } catch(ex) { };
   try { mSwitch.deleteVar('PrintCount'); } catch(ex) { };
   try { mSwitch.deleteVar('LostTimes'); } catch(ex) { };
   try { mSwitch.deleteVar('Lang'); } catch(ex) { };
   try { mSwitch.deleteVar('Currency'); } catch(ex) { };
   try { mSwitch.deleteVar('Remark'); } catch(ex) { };
   try { mSwitch.deleteVar('Peoples'); } catch(ex) { };
   try { mSwitch.deleteVar('Mult'); } catch(ex) { };
   try { mSwitch.deleteVar('Prem'); } catch(ex) { };
   try { mSwitch.deleteVar('Amnt'); } catch(ex) { };
   try { mSwitch.deleteVar('SumPrem'); } catch(ex) { };
   try { mSwitch.deleteVar('Dif'); } catch(ex) { };
   try { mSwitch.deleteVar('PaytoDate'); } catch(ex) { };
   try { mSwitch.deleteVar('FirstPayDate'); } catch(ex) { };
   try { mSwitch.deleteVar('CValiDate'); } catch(ex) { };
   try { mSwitch.deleteVar('InputOperator'); } catch(ex) { };
   try { mSwitch.deleteVar('InputDate'); } catch(ex) { };
   try { mSwitch.deleteVar('InputTime'); } catch(ex) { };
   try { mSwitch.deleteVar('ApproveFlag'); } catch(ex) { };
   try { mSwitch.deleteVar('ApproveCode'); } catch(ex) { };
   try { mSwitch.deleteVar('ApproveDate'); } catch(ex) { };
   try { mSwitch.deleteVar('ApproveTime'); } catch(ex) { };
   try { mSwitch.deleteVar('UWFlag'); } catch(ex) { };
   try { mSwitch.deleteVar('UWOperator'); } catch(ex) { };
   try { mSwitch.deleteVar('UWDate'); } catch(ex) { };
   try { mSwitch.deleteVar('UWTime'); } catch(ex) { };
   try { mSwitch.deleteVar('AppFlag'); } catch(ex) { };
   try { mSwitch.deleteVar('PolApplyDate'); } catch(ex) { };
   try { mSwitch.deleteVar('GetPolDate'); } catch(ex) { };
   try { mSwitch.deleteVar('GetPolTime'); } catch(ex) { };
   try { mSwitch.deleteVar('CustomGetPolDate'); } catch(ex) { };
   try { mSwitch.deleteVar('State'); } catch(ex) { };
   try { mSwitch.deleteVar('Operator'); } catch(ex) { };
   try { mSwitch.deleteVar('MakeDate'); } catch(ex) { };
   try { mSwitch.deleteVar('MakeTime'); } catch(ex) { };
   try { mSwitch.deleteVar('ModifyDate'); } catch(ex) { };
   try { mSwitch.deleteVar('ModifyTime'); } catch(ex) { };

    //新的删除数据处理
	try { mSwitch.deleteVar  ('AppntNo'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntName'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntSex'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntBirthday'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntIDType'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntIDNo'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntPassword'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntNativePlace'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntNationality'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AddressNo');}catch(ex){};
	try { mSwitch.deleteVar  ('AppntRgtAddress'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntMarriage'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntMarriageDate'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntHealth'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntStature'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntAvoirdupois'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntDegree'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntCreditGrade'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntOthIDType'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntOthIDNo'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntICNo'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntGrpNo'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntJoinCompanyDate'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntStartWorkDate'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntPosition') } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntSalary'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntOccupationType'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntOccupationCode'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntWorkType'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntPluralityType');} catch(ex) { };
	try { mSwitch.deleteVar  ('AppntDeathDate'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntSmokeFlag'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntBlacklistFlag'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntProterty'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntRemark'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntState'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntOperator'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntMakeDate'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntMakeTime'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntModifyDate'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntModifyTime'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntHomeAddress'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntHomeZipCode'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntHomePhone'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntHomeFax'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntPostalAddress'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntZipCode'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntPhone'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntFax'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntMobile'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntEMail'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntGrpName'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntGrpPhone'); } catch(ex) { };
	try { mSwitch.deleteVar  ('CompanyAddress'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntGrpZipCode'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntGrpFax'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntBankAccNo'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntBankCode'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntAccName'); } catch(ex) { };
}

/*********************************************************************
 *  把合同信息放入加到变量中
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function addIntoCont()
{
	//try { mSwitch.addVar( "intoPolFlag", "", "GROUPPOL" ); } catch(ex) { };
	// body信息
	try { mSwitch.addVar( "BODY", "", window.document.body.innerHTML ); } catch(ex) { };
	// 集体信息
	//由"./AutoCreatLDGrpInit.jsp"自动生成
	try{mSwitch.addVar('ContNo','',fm.ContNo.value);}catch(ex){};
	try{mSwitch.addVar('ProposalContNo','',fm.ProposalContNo.value);}catch(ex){};
	try{mSwitch.addVar('PrtNo','',fm.PrtNo.value);}catch(ex){};
	try{mSwitch.addVar('GrpContNo','',fm.GrpContNo.value);}catch(ex){};
	try{mSwitch.addVar('ContType','',fm.ContType.value);}catch(ex){};
	try{mSwitch.addVar('FamilyType','',fm.FamilyType.value);}catch(ex){};
	try{mSwitch.addVar('PolType','',fm.PolType.value);}catch(ex){};
	try{mSwitch.addVar('CardFlag','',fm.CardFlag.value);}catch(ex){};
	try{mSwitch.addVar('ManageCom','',fm.ManageCom.value);}catch(ex){};
	try{mSwitch.addVar('AgentCom','',fm.AgentCom.value);}catch(ex){};
	try{mSwitch.addVar('AgentCode','',fm.AgentCode.value);}catch(ex){};
	try{mSwitch.addVar('AgentGroup','',fm.AgentGroup.value);}catch(ex){};
	try{mSwitch.addVar('AgentCode1','',fm.AgentCode1.value);}catch(ex){};
	try{mSwitch.addVar('AgentType','',fm.AgentType.value);}catch(ex){};
	try{mSwitch.addVar('SaleChnl','',fm.SaleChnl.value);}catch(ex){};
	try{mSwitch.addVar('Handler','',fm.Handler.value);}catch(ex){};
	try{mSwitch.addVar('Password','',fm.Password.value);}catch(ex){};
	try{mSwitch.addVar('AppntNo','',fm.AppntNo.value);}catch(ex){};
	try{mSwitch.addVar('AppntName','',fm.AppntName.value);}catch(ex){};
	try{mSwitch.addVar('AppntSex','',fm.AppntSex.value);}catch(ex){};
	try{mSwitch.addVar('AppntBirthday','',fm.AppntBirthday.value);}catch(ex){};
	try{mSwitch.addVar('AppntIDType','',fm.AppntIDType.value);}catch(ex){};
	try{mSwitch.addVar('AppntIDNo','',fm.AppntIDNo.value);}catch(ex){};
	
	try{mSwitch.addVar('InsuredNo','',fm.InsuredNo.value);}catch(ex){};
	try{mSwitch.addVar('InsuredName','',fm.InsuredName.value);}catch(ex){};
	try{mSwitch.addVar('InsuredSex','',fm.InsuredSex.value);}catch(ex){};
	try{mSwitch.addVar('InsuredBirthday','',fm.InsuredBirthday.value);}catch(ex){};
	try{mSwitch.addVar('InsuredIDType','',fm.InsuredIDType.value);}catch(ex){};
	try{mSwitch.addVar('InsuredIDNo','',fm.InsuredIDNo.value);}catch(ex){};
	try{mSwitch.addVar('PayIntv','',fm.PayIntv.value);}catch(ex){};
	try{mSwitch.addVar('PayMode','',fm.PayMode.value);}catch(ex){};
	try{mSwitch.addVar('PayLocation','',fm.PayLocation.value);}catch(ex){};
	try{mSwitch.addVar('DisputedFlag','',fm.DisputedFlag.value);}catch(ex){};
	try{mSwitch.addVar('OutPayFlag','',fm.OutPayFlag.value);}catch(ex){};
	try{mSwitch.addVar('GetPolMode','',fm.GetPolMode.value);}catch(ex){};
	try{mSwitch.addVar('SignCom','',fm.SignCom.value);}catch(ex){};
	try{mSwitch.addVar('SignDate','',fm.SignDate.value);}catch(ex){};
	try{mSwitch.addVar('SignTime','',fm.SignTime.value);}catch(ex){};
	try{mSwitch.addVar('ConsignNo','',fm.ConsignNo.value);}catch(ex){};
	try{mSwitch.addVar('BankCode','',fm.BankCode.value);}catch(ex){};
	try{mSwitch.addVar('BankAccNo','',fm.BankAccNo.value);}catch(ex){};
	try{mSwitch.addVar('AccName','',fm.AccName.value);}catch(ex){};
	try{mSwitch.addVar('PrintCount','',fm.PrintCount.value);}catch(ex){};
	try{mSwitch.addVar('LostTimes','',fm.LostTimes.value);}catch(ex){};
	try{mSwitch.addVar('Lang','',fm.Lang.value);}catch(ex){};
	try{mSwitch.addVar('Currency','',fm.Currency.value);}catch(ex){};
	try{mSwitch.addVar('Remark','',fm.Remark.value);}catch(ex){};
	try{mSwitch.addVar('Peoples','',fm.Peoples.value);}catch(ex){};
	try{mSwitch.addVar('Mult','',fm.Mult.value);}catch(ex){};
	try{mSwitch.addVar('Prem','',fm.Prem.value);}catch(ex){};
	try{mSwitch.addVar('Amnt','',fm.Amnt.value);}catch(ex){};
	try{mSwitch.addVar('SumPrem','',fm.SumPrem.value);}catch(ex){};
	try{mSwitch.addVar('Dif','',fm.Dif.value);}catch(ex){};
	try{mSwitch.addVar('PaytoDate','',fm.PaytoDate.value);}catch(ex){};
	try{mSwitch.addVar('FirstPayDate','',fm.FirstPayDate.value);}catch(ex){};
	try{mSwitch.addVar('CValiDate','',fm.CValiDate.value);}catch(ex){};
	try{mSwitch.addVar('InputOperator','',fm.InputOperator.value);}catch(ex){};
	try{mSwitch.addVar('InputDate','',fm.InputDate.value);}catch(ex){};
	try{mSwitch.addVar('InputTime','',fm.InputTime.value);}catch(ex){};
	try{mSwitch.addVar('ApproveFlag','',fm.ApproveFlag.value);}catch(ex){};
	try{mSwitch.addVar('ApproveCode','',fm.ApproveCode.value);}catch(ex){};
	try{mSwitch.addVar('ApproveDate','',fm.ApproveDate.value);}catch(ex){};
	try{mSwitch.addVar('ApproveTime','',fm.ApproveTime.value);}catch(ex){};
	try{mSwitch.addVar('UWFlag','',fm.UWFlag.value);}catch(ex){};
	try{mSwitch.addVar('UWOperator','',fm.UWOperator.value);}catch(ex){};
	try{mSwitch.addVar('UWDate','',fm.UWDate.value);}catch(ex){};
	try{mSwitch.addVar('UWTime','',fm.UWTime.value);}catch(ex){};
	try{mSwitch.addVar('AppFlag','',fm.AppFlag.value);}catch(ex){};
	try{mSwitch.addVar('PolApplyDate','',fm.PolApplyDate.value);}catch(ex){};
	try{mSwitch.addVar('GetPolDate','',fm.GetPolDate.value);}catch(ex){};
	try{mSwitch.addVar('GetPolTime','',fm.GetPolTime.value);}catch(ex){};
	try{mSwitch.addVar('CustomGetPolDate','',fm.CustomGetPolDate.value);}catch(ex){};
	try{mSwitch.addVar('State','',fm.State.value);}catch(ex){};
	try{mSwitch.addVar('Operator','',fm.Operator.value);}catch(ex){};
	try{mSwitch.addVar('MakeDate','',fm.MakeDate.value);}catch(ex){};
	try{mSwitch.addVar('MakeTime','',fm.MakeTime.value);}catch(ex){};
	try{mSwitch.addVar('ModifyDate','',fm.ModifyDate.value);}catch(ex){};
	try{mSwitch.addVar('ModifyTime','',fm.ModifyTime.value);}catch(ex){};
	
	//新的数据处理
	try { mSwitch.addVar('AppntNo','',fm.AppntNo.value); } catch(ex) { };
	try { mSwitch.addVar('AppntName','',fm.AppntName.value); } catch(ex) { };
	try { mSwitch.addVar('AppntSex','',fm.AppntSex.value); } catch(ex) { };
	try { mSwitch.addVar('AppntBirthday','',fm.AppntBirthday.value); } catch(ex) { };
	try { mSwitch.addVar('AppntIDType','',fm.AppntIDType.value); } catch(ex) { };
	try { mSwitch.addVar('AppntIDNo','',fm.AppntIDNo.value); } catch(ex) { };
	try { mSwitch.addVar('AppntPassword','',fm.AppntPassword.value); } catch(ex) { };
	try { mSwitch.addVar('AppntNativePlace','',fm.AppntNativePlace.value); } catch(ex) { };
	try { mSwitch.addVar('AppntNationality','',fm.AppntNationality.value); } catch(ex) { };
	try { mSwitch.addVar('AddressNo','',fm.AppntAddressNo.value); } catch(ex) { };
	try { mSwitch.addVar('AppntRgtAddress','',fm.AppntRgtAddress.value); } catch(ex) { };
	try { mSwitch.addVar('AppntMarriage','',fm.AppntMarriage.value); } catch(ex) { };
	try { mSwitch.addVar('AppntMarriageDate','',fm.AppntMarriageDate.value); } catch(ex) { };
	try { mSwitch.addVar('AppntHealth','',fm.AppntHealth.value); } catch(ex) { };
	try { mSwitch.addVar('AppntStature','',fm.AppntStature.value); } catch(ex) { };
	try { mSwitch.addVar('AppntAvoirdupois','',fm.AppntAvoirdupois.value); } catch(ex) { };
	try { mSwitch.addVar('AppntDegree','',fm.AppntDegree.value); } catch(ex) { };
	try { mSwitch.addVar('AppntCreditGrade','',fm.AppntCreditGrade.value); } catch(ex) { };
	try { mSwitch.addVar('AppntOthIDType','',fm.AppntOthIDType.value); } catch(ex) { };
	try { mSwitch.addVar('AppntOthIDNo','',fm.AppntOthIDNo.value); } catch(ex) { };
	try { mSwitch.addVar('AppntICNo','',fm.AppntICNo.value); } catch(ex) { };
	try { mSwitch.addVar('AppntGrpNo','',fm.AppntGrpNo.value); } catch(ex) { };
	try { mSwitch.addVar('AppntJoinCompanyDate','',fm.AppntJoinCompanyDate.value); } catch(ex) { };
	try { mSwitch.addVar('AppntStartWorkDate','',fm.AppntStartWorkDate.value); } catch(ex) { };
	try { mSwitch.addVar('AppntPosition','',fm.AppntPosition.value); } catch(ex) { };
	try { mSwitch.addVar('AppntSalary','',fm.AppntSalary.value); } catch(ex) { };
	try { mSwitch.addVar('AppntOccupationType','',fm.AppntOccupationType.value); } catch(ex) { };
	try { mSwitch.addVar('AppntOccupationCode','',fm.AppntOccupationCode.value); } catch(ex) { };
	try { mSwitch.addVar('AppntWorkType','',fm.AppntWorkType.value); } catch(ex) { };
	try { mSwitch.addVar('AppntPluralityType','',fm.AppntPluralityType.value); } catch(ex) { };
	try { mSwitch.addVar('AppntDeathDate','',fm.AppntDeathDate.value); } catch(ex) { };
	try { mSwitch.addVar('AppntSmokeFlag','',fm.AppntSmokeFlag.value); } catch(ex) { };
	try { mSwitch.addVar('AppntBlacklistFlag','',fm.AppntBlacklistFlag.value); } catch(ex) { };
	try { mSwitch.addVar('AppntProterty','',fm.AppntProterty.value); } catch(ex) { };
	try { mSwitch.addVar('AppntRemark','',fm.AppntRemark.value); } catch(ex) { };
	try { mSwitch.addVar('AppntState','',fm.AppntState.value); } catch(ex) { };
	try { mSwitch.addVar('AppntOperator','',fm.AppntOperator.value); } catch(ex) { };
	try { mSwitch.addVar('AppntMakeDate','',fm.AppntMakeDate.value); } catch(ex) { };
	try { mSwitch.addVar('AppntMakeTime','',fm.AppntMakeTime.value); } catch(ex) { };
	try { mSwitch.addVar('AppntModifyDate','',fm.AppntModifyDate.value); } catch(ex) { };
	try { mSwitch.addVar('AppntModifyTime','',fm.AppntModifyTime.value); } catch(ex) { };
	try { mSwitch.addVar('AppntHomeAddress','',fm.AppntHomeAddress.value); } catch(ex) { };
	try { mSwitch.addVar('AppntHomeZipCode','',fm.AppntHomeZipCode.value); } catch(ex) { };
	try { mSwitch.addVar('AppntHomePhone','',fm.AppntHomePhone.value); } catch(ex) { };
	try { mSwitch.addVar('AppntHomeFax','',fm.AppntHomeFax.value); } catch(ex) { };
	try { mSwitch.addVar('AppntGrpName','',fm.AppntGrpName.value); } catch(ex) { };
	try { mSwitch.addVar('AppntGrpPhone','',fm.AppntGrpPhone.value); } catch(ex) { };
	try { mSwitch.addVar('CompanyAddress','',fm.CompanyAddress.value); } catch(ex) { };
	try { mSwitch.addVar('AppntGrpZipCode','',fm.AppntGrpZipCode.value); } catch(ex) { };
	try { mSwitch.addVar('AppntGrpFax','',fm.AppntGrpFax.value); } catch(ex) { };
	try { mSwitch.addVar('AppntPostalAddress','',fm.AppntPostalAddress.value); } catch(ex) { };
	try { mSwitch.addVar('AppntZipCode','',fm.AppntZipCode.value); } catch(ex) { };
	try { mSwitch.addVar('AppntPhone','',fm.AppntPhone.value); } catch(ex) { };
	try { mSwitch.addVar('AppntMobile','',fm.AppntMobile.value); } catch(ex) { };
	try { mSwitch.addVar('AppntFax','',fm.AppntFax.value); } catch(ex) { };
	try { mSwitch.addVar('AppntEMail','',fm.AppntEMail.value); } catch(ex) { };
	try { mSwitch.addVar('AppntBankAccNo','',document.all('AppntBankAccNo').value); } catch(ex) { };
	try { mSwitch.addVar('AppntBankCode','',document.all('AppntBankCode').value); } catch(ex) { };
	try { mSwitch.addVar('AppntAccName','',document.all('AppntAccName').value); } catch(ex) { };
}


/*********************************************************************
 *  删除缓存中被保险人信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function delInsuredVar()
{
    try{mSwitch.deleteVar('ContNo');}catch(ex){};
    try{mSwitch.deleteVar('InsuredNo');}catch(ex){};
    try{mSwitch.deleteVar('PrtNo');}catch(ex){};
    try{mSwitch.deleteVar('GrpContNo');}catch(ex){};
 //   try{mSwitch.deleteVar('AppntNo');}catch(ex){};
 //   try{mSwitch.deleteVar('ManageCom');}catch(ex){};
    try{mSwitch.deleteVar('ExecuteCom');}catch(ex){};
    try{mSwitch.deleteVar('FamilyType');}catch(ex){};
    try{mSwitch.deleteVar('RelationToMainInsure');}catch(ex){};
    try{mSwitch.deleteVar('RelationToAppnt');}catch(ex){};
    try{mSwitch.deleteVar('AddressNo');}catch(ex){};
    try{mSwitch.deleteVar('SequenceNo');}catch(ex){};
    try{mSwitch.deleteVar('Name');}catch(ex){};
    try{mSwitch.deleteVar('Sex');}catch(ex){};
    try{mSwitch.deleteVar('Birthday');}catch(ex){};
    try{mSwitch.deleteVar('IDType');}catch(ex){};
    try{mSwitch.deleteVar('IDNo');}catch(ex){};
    try{mSwitch.deleteVar('RgtAddress');}catch(ex){};
    try{mSwitch.deleteVar('Marriage');}catch(ex){};
    try{mSwitch.deleteVar('MarriageDate');}catch(ex){};
    try{mSwitch.deleteVar('Health');}catch(ex){};
    try{mSwitch.deleteVar('Stature');}catch(ex){};
    try{mSwitch.deleteVar('Avoirdupois');}catch(ex){};
    try{mSwitch.deleteVar('Degree');}catch(ex){};
    try{mSwitch.deleteVar('CreditGrade');}catch(ex){};
    try{mSwitch.deleteVar('BankCode');}catch(ex){};
    try{mSwitch.deleteVar('BankAccNo');}catch(ex){};
    try{mSwitch.deleteVar('AccName');}catch(ex){};
    try{mSwitch.deleteVar('JoinCompanyDate');}catch(ex){};
    try{mSwitch.deleteVar('StartWorkDate');}catch(ex){};
    try{mSwitch.deleteVar('Position');}catch(ex){};
    try{mSwitch.deleteVar('Salary');}catch(ex){};
    try{mSwitch.deleteVar('OccupationType');}catch(ex){};
    try{mSwitch.deleteVar('OccupationCode');}catch(ex){};
    try{mSwitch.deleteVar('WorkType');}catch(ex){};
    try{mSwitch.deleteVar('PluralityType');}catch(ex){};
    try{mSwitch.deleteVar('SmokeFlag');}catch(ex){};
    try{mSwitch.deleteVar('ContPlanCode');}catch(ex){};
    try{mSwitch.deleteVar('Operator');}catch(ex){};
    try{mSwitch.deleteVar('MakeDate');}catch(ex){};
    try{mSwitch.deleteVar('MakeTime');}catch(ex){};
    try{mSwitch.deleteVar('ModifyDate');}catch(ex){};
    try{mSwitch.deleteVar('ModifyTime');}catch(ex){};
}

/*********************************************************************
 *  将被保险人信息加入到缓存中
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function addInsuredVar()
{
    try{mSwitch.addVar('ContNo','',fm.ContNo.value);}catch(ex){};
    //alert("ContNo:"+fm.ContNo.value);
    try{mSwitch.addVar('InsuredNo','',fm.InsuredNo.value);}catch(ex){};
    try{mSwitch.addVar('PrtNo','',fm.PrtNo.value);}catch(ex){};
    try{mSwitch.addVar('GrpContNo','',fm.GrpContNo.value);}catch(ex){};
 //   try{mSwitch.addVar('AppntNo','',fm.AppntNo.value);}catch(ex){};
 //   try{mSwitch.addVar('ManageCom','',fm.ManageCom.value);}catch(ex){};
    try{mSwitch.addVar('ExecuteCom','',fm.ExecuteCom.value);}catch(ex){};
    try{mSwitch.addVar('FamilyType','',fm.FamilyType.value);}catch(ex){};
    try{mSwitch.addVar('RelationToMainInsure','',fm.RelationToMainInsure.value);}catch(ex){};
    try{mSwitch.addVar('RelationToAppnt','',fm.RelationToAppnt.value);}catch(ex){};

    try{mSwitch.addVar('AddressNo','',fm.AppntAddressNo.value);}catch(ex){};
    try{mSwitch.addVar('SequenceNo','',fm.SequenceNo.value);}catch(ex){};
    try{mSwitch.addVar('Name','',fm.InsuredName.value);}catch(ex){};
    try{mSwitch.addVar('Sex','',fm.InsuredSex.value);}catch(ex){};
    try{mSwitch.addVar('Birthday','',fm.InsuredBirthday.value);}catch(ex){};
    try{mSwitch.addVar('IDType','',fm.InsuredIDType.value);}catch(ex){};
    try{mSwitch.addVar('IDNo','',fm.InsuredIDNo.value);}catch(ex){};
    try{mSwitch.addVar('RgtAddress','',fm.InsuredRgtAddress.value);}catch(ex){};
    try{mSwitch.addVar('Marriage','',fm.InsuredMarriage.value);}catch(ex){};
    try{mSwitch.addVar('MarriageDate','',fm.InsuredMarriageDate.value);}catch(ex){};
    try{mSwitch.addVar('Health','',fm.InsuredHealth.value);}catch(ex){};
    try{mSwitch.addVar('Stature','',fm.InsuredStature.value);}catch(ex){};
    try{mSwitch.addVar('Avoirdupois','',Insuredfm.Avoirdupois.value);}catch(ex){};
    try{mSwitch.addVar('Degree','',Insuredfm.Degree.value);}catch(ex){};
    try{mSwitch.addVar('CreditGrade','',fm.InsuredCreditGrade.value);}catch(ex){};
    try{mSwitch.addVar('BankCode','',fm.BankCode.value);}catch(ex){};
    try{mSwitch.addVar('BankAccNo','',fm.BankAccNo.value);}catch(ex){};
    try{mSwitch.addVar('AccName','',fm.AccName.value);}catch(ex){};
    try{mSwitch.addVar('JoinCompanyDate','',fm.JoinCompanyDate.value);}catch(ex){};
    try{mSwitch.addVar('StartWorkDate','',fm.StartWorkDate.value);}catch(ex){};
    try{mSwitch.addVar('Position','',fm.Position.value);}catch(ex){};
    try{mSwitch.addVar('Salary','',fm.Salary.value);}catch(ex){};
    try{mSwitch.addVar('OccupationType','',fm.InsuredOccupationType.value);}catch(ex){};
    try{mSwitch.addVar('OccupationCode','',fm.InsuredOccupationCode.value);}catch(ex){};
    try{mSwitch.addVar('WorkType','',fm.WorkType.value);}catch(ex){};
    try{mSwitch.addVar('PluralityType','',fm.PluralityType.value);}catch(ex){};
    try{mSwitch.addVar('SmokeFlag','',fm.SmokeFlag.value);}catch(ex){};
    try{mSwitch.addVar('ContPlanCode','',fm.ContPlanCode.value);}catch(ex){};
    try{mSwitch.addVar('Operator','',fm.Operator.value);}catch(ex){};
    try{mSwitch.addVar('MakeDate','',fm.MakeDate.value);}catch(ex){};
    try{mSwitch.addVar('MakeTime','',fm.MakeTime.value);}catch(ex){};
    try{mSwitch.addVar('ModifyDate','',fm.ModifyDate.value);}catch(ex){};
    try{mSwitch.addVar('ModifyTime','',fm.ModifyTime.value);}catch(ex){};

}
/*********************************************************************************
**********************************************************************************/
//获取投保人年龄
function getAppntAge()
{
	fm.AppntAge.value=calPolAppntAge(fm.AppntBirthday.value,fm.PolApplyDate.value);
}
//获取被保人年龄
function getInsuredAppAge()
{
	fm.InsuredAppAge.value=calPolAppntAge(fm.InsuredBirthday.value,fm.PolApplyDate.value);
}

//根据地址代码查询地址汉字信息,查询地址代码表<ldaddress>
//所需参数,地址级别<province--省;city--市;district--区/县;>,地址代码<代码表单名>,地址汉字信息<汉字表单名>
//返回,直接为--地址汉字信息<汉字表单名>--赋值
//例： getAddressName('province','AppntProvince','AppntProvinceName');
function getAddressName(strCode, strObjCode, strObjName)
{
	var PlaceType="";
	var PlaceCode="";
	//判断地址级别,<province--省--01;city--市--02;district--区/县--03;>
	switch(strCode)
	{
	   case "province":
	      	PlaceType="01";
	      	break;
	   case "city":
	      	PlaceType="02";
	      	break;
	   case "district":
	      	PlaceType="03";
	      	break;   
	   default:
	   		PlaceType="";   		   	
	}
	//遍历FORM中的所有ELEMENT
	for (formsNum=0; formsNum<window.document.forms.length; formsNum++)
	{
		for (elementsNum=0; elementsNum<window.document.forms[formsNum].elements.length; elementsNum++)
		{
			//寻找代码选择元素
			if (window.document.forms[formsNum].elements[elementsNum].name == strObjCode)
			{
				strObjCode = window.document.forms[formsNum].elements[elementsNum];
			}
			if (window.document.forms[formsNum].elements[elementsNum].name == strObjName)
			{
				strObjName = window.document.forms[formsNum].elements[elementsNum];
				break;
			}
		}
	}
	//如果代码栏的数据长度为[6]才查询，否则不做任何操作	
//	strObjName.value="";
	if(strObjCode.value !="")
	{
		PlaceCode=strObjCode.value;
//		var strSQL="select placecode,placename from ldaddress where placecode='"+PlaceCode+"' and placetype='"+PlaceType+"'";
		
		var sqlid41="DirectContInputSql41";
		var mySql41=new SqlClass();
		mySql41.setResourceName("app.DirectContInputSql"); //指定使用的properties文件名
		mySql41.setSqlId(sqlid41);//指定使用的Sql的id
		mySql41.addSubPara(PlaceCode);//指定传入的参数
		mySql41.addSubPara(PlaceType);//指定传入的参数
	 	var strSQL=mySql41.getString();
		
		var arrAddress=easyExecSql(strSQL);
		if(arrAddress!=null)
		{   
			strObjName.value = arrAddress[0][1];
		}	
			
		else
		{
			strObjName.value="";
		}	
	}	
}

/*************************************************************************************
在录单保存时，系统生成的保费与收银录入的保费进行校验，
如果收银录入的保费与系统生成的保费不符，系统要进行提示，但仍可保存,流程仍可流转
所需参数：合同号<投保单号>;     返回:true or false
*************************************************************************************/
function checkpayfee(ContNo)
{
	var tContNo=ContNo;
	var tTempFee="";//收银录入的保费
	var tPremFee="";//系统生成的保费
	//查询收银录入的保费
//	var tempfeeSQL="select sum(nvl(paymoney,0)) from ljtempfee where tempfeetype='1' and confdate is null "
//		+" and otherno=(select contno from lccont where prtno= '"+tContNo+"')";
	
	var sqlid42="DirectContInputSql42";
	var mySql42=new SqlClass();
	mySql42.setResourceName("app.DirectContInputSql"); //指定使用的properties文件名
	mySql42.setSqlId(sqlid42);//指定使用的Sql的id
	mySql42.addSubPara(tContNo);//指定传入的参数
 	var tempfeeSQL=mySql42.getString();
	
	var TempFeeArr=easyExecSql(tempfeeSQL);	
	if(TempFeeArr!=null)
	{
		tTempFee=TempFeeArr[0][0];
	}
	//查询系统生成的保费
//	var premfeeSQL="select sum(nvl(prem,0)) from lcpol where 1=1 "
//		+" and contno=(select contno from lccont where prtno= '"+tContNo+"')";	
	
	var sqlid43="DirectContInputSql43";
	var mySql43=new SqlClass();
	mySql43.setResourceName("app.DirectContInputSql"); //指定使用的properties文件名
	mySql43.setSqlId(sqlid43);//指定使用的Sql的id
	mySql43.addSubPara(tContNo);//指定传入的参数
 	var premfeeSQL=mySql43.getString();
	
	var PremFeeArr=easyExecSql(premfeeSQL);	
	tPremFee=PremFeeArr[0][0];	
	if(PremFeeArr!=null)
	{
		tPremFee=PremFeeArr[0][0];
		if(tPremFee==null || tPremFee=="" || tPremFee=="null")
		{
		alert("查询该投保单下险种信息及生成保费数据失败！！！");
		return false;	
		}
	}
	//比较“查询收银录入的保费” 和 “查询系统生成的保费”是否相等，如不相等则弹出信息提示
	if(tTempFee!="" && tPremFee!="" && tTempFee!="null" && tPremFee!="null" && (tTempFee!=tPremFee))
	{
		var ErrInfo="注意：收银录入的保费["+tTempFee+"]与系统生成的保费["+tPremFee+"]不等。\n";
		ErrInfo=ErrInfo+"确定需要保存吗？确需保存请按“确定”，否则按“取消”。";
		if(confirm(ErrInfo)==false)
		{
		   return false;	
		}
	}
	return true;
}

/******************************************************************************
* 添加校验，增加系统对录入的投保日期校验，校验规则为：录入的投保日期控制在录单的系统当期日期60天内
* 所需参数：ContNo---合同号（印刷号）；  PolAppntDate---投保日期
*******************************************************************************/
function checkPolDate(ContNo,PolAppntDate)
{
  	var tContNo=ContNo;//合同号
	var tPolAppntDate=PolAppntDate;//投保日期
	if(tContNo=="" || tPolAppntDate=="") {return false;}
	//投保日期只能为当前日期以前
    if (calAge(tPolAppntDate) < 0)
    {
        alert("投保日期只能为当前日期以前!");
        return false;
    }
	var DayIntv=60;//录单日期与投保日期的标准间隔天数，默认为60天
    //查询录单日期与投保日期的标准间隔天数
//    var DayIntvArr = easyExecSql("select sysvarvalue from ldsysvar where sysvar='input_poldate_intv'");
    
	var sqlid44="DirectContInputSql44";
	var mySql44=new SqlClass();
	mySql44.setResourceName("app.DirectContInputSql"); //指定使用的properties文件名
	mySql44.setSqlId(sqlid44);//指定使用的Sql的id
 	var strSQL44=mySql44.getString();
 	var DayIntvArr = easyExecSql(strSQL44);
    
    if (DayIntvArr != null) { DayIntv = DayIntvArr[0][0]; }
    //根据合同号获取录单日期<查询合同表，存在则取之，否则取系统当前日期>
    var tMakeDate = "";//录单的系统当期日期
    if(tContNo!=null && tContNo!="")
    {
//		var makedatesql = "select contno,prtno,makedate from lccont where prtno='" + tContNo + "'";
		
		var sqlid45="DirectContInputSql45";
		var mySql45=new SqlClass();
		mySql45.setResourceName("app.DirectContInputSql"); //指定使用的properties文件名
		mySql45.setSqlId(sqlid45);//指定使用的Sql的id
		mySql45.addSubPara(tContNo);//指定传入的参数
	 	var makedatesql=mySql45.getString();
		
		var makedatearr = easyExecSql(makedatesql);
		if (makedatearr != null){ tMakeDate = makedatearr[0][2];}
    }
    if(tMakeDate=="")  //录单的系统当期日期为空，则 默认系统日期
    {    
//    	var sysdatearr = easyExecSql("select to_date(sysdate) from dual");
    	
		var sqlid46="DirectContInputSql46";
		var mySql46=new SqlClass();
		mySql46.setResourceName("app.DirectContInputSql"); //指定使用的properties文件名
		mySql46.setSqlId(sqlid46);//指定使用的Sql的id
	 	var strsql46=mySql46.getString();
	 	var sysdatearr = easyExecSql(strsql46);
	 	
    	tMakeDate = sysdatearr[0][0];//录单的系统当期日期，默认系统日期。
    }
    var Days = dateDiff(tPolAppntDate, tMakeDate, "D");//录单日期与投保日期的间隔
    if (Days > DayIntv || Days < 0)
    {
        var strInfo = "投保日期应在录单的系统当期日期 "+DayIntv+" 天内。";
        strInfo = strInfo +"\n而录单日期["+tMakeDate+"] — 投保日期["+tPolAppntDate+"]=="+Days+" 天。";
		strInfo = strInfo +"\n请重新填写投保日期。";
        alert(strInfo);
        return false;
    }
    return true;
}
/*****************************************************************************************
        以下为本页面的公共方法函数区域
******************************************************************************************/
//录入校验方法
//传入参数：verifyOrder校验的顺序
//业务程序调用接口，如果通过校验返回true，否则返回false
function verifyInputNB(verifyOrder)  
{
	var formsNum = 0;	//窗口中的FORM数
	var elementsNum = 0;	//FORM中的元素数
	var passFlag = true;	//校验通过标志
	//遍历所有FORM
	for (formsNum=0; formsNum<window.document.forms.length; formsNum++)
	{
		//遍历FORM中的所有ELEMENT
		for (elementsNum=0; elementsNum<window.document.forms[formsNum].elements.length; elementsNum++)
		{
			//元素校验属性verify不为NULL
			if (window.document.forms[formsNum].elements[elementsNum].verify != null && window.document.forms[formsNum].elements[elementsNum].verify != "" && window.document.forms[formsNum].elements[elementsNum].verifyorder == verifyOrder)
			{
				//进行校验verifyElement
				if (!verifyElementWrap2(window.document.forms[formsNum].elements[elementsNum].verify, window.document.forms[formsNum].elements[elementsNum].value,window.document.forms[formsNum].name+"."+window.document.forms[formsNum].elements[elementsNum].name))
				{
					passFlag = false;
					break;
				}
			}
		}
		if (!passFlag) break;
	}
	return passFlag;
}

/********************************************************************
校验日期格式，如果成功，则返回格式化后的日期字符串；如果格式化失败，则返回 null。
############输入：日期字符串     ##############输出：格式化后的日期字符串
*********************************************************************/
function getFormatDate(mDate)
{
	var TDate;
	if(mDate.length==0){return null;}
	else if(mDate.length==8)
	{
		if(mDate.indexOf('-')==-1)
		{ 
			var Year =     mDate.substring(0,4);
			var Month =    mDate.substring(4,6);
			var Day =      mDate.substring(6,8);
			if(Year=="0000"||Month=="00"||Day=="00"){return null;}
		    else{ TDate = Year+"-"+Month+"-"+Day;}
		}
		else  {return null;}
	}
	else if(mDate.length==10)
	{
		var Year =     mDate.substring(0,4);
		var Month =    mDate.substring(5,7);
		var Day =      mDate.substring(8,10);
		if(Year=="0000"||Month=="00"||Day=="00"){return null;}
		else{ TDate = Year+"-"+Month+"-"+Day;}
	}
	else { return null;}
	return TDate;
}


//根据页面输入框的代码带出汉字，并显示出来。
function getInputFieldName()
{
	//合同信息部分
	showOneCodeName('sellType','SellType','SellTypeName');
	showOneCodeName('comcode','ManageCom','ManageComName');
	showOneCodeName('highAmntFlag','highAmntFlag','highAmntFlagName');
	showOneCodeName('comcode','AgentManageCom','AgentManageComName');
	showOneCodeName('comcode','TelephManageCom','TelephManageComName');
	//showOneCodeName('directstyle','DirectStyle','DirectStyleName');
	showOneCodeName('commisionratio','CSplit','CSplitName');
	//投保人信息部分
	showOneCodeName('vipvalue','AppntVIPFlag','AppntVIPFlagName');
	showOneCodeName('IDType','AppntIDType','AppntIDTypeName');
	showOneCodeName('Sex','AppntSex','AppntSexName');
	getAppntAge();
	showOneCodeName('Marriage','AppntMarriage','AppntMarriageName');
	showOneCodeName('NativePlace','AppntNativePlace','AppntNativePlaceName');
	showOneCodeName('LicenseType','AppntLicenseType','AppntLicenseTypeName');
	showOneCodeName('OccupationCode','AppntOccupationCode','AppntOccupationCodeName');
	showOneCodeName('OccupationType','AppntOccupationType','AppntOccupationTypeName');
	//showOneCodeName('province','AppntProvince','AppntProvinceName');
	//showOneCodeName('city','AppntCity','AppntCityName');
	//showOneCodeName('district','AppntDistrict','AppntDistrictName');
	getAddressName('province','AppntProvince','AppntProvinceName');
	getAddressName('city','AppntCity','AppntCityName');
	getAddressName('district','AppntDistrict','AppntDistrictName');
	//缴费信息部分
	showOneCodeName('paymode','NewPayMode','NewPayModeName');
	showOneCodeName('bank','NewBankCode','NewBankCodeName');
	showOneCodeName('continuepaymode','SecPayMode','SecPayModeName');
	showOneCodeName('bank','SecBankCode','SecBankCodeName');
	//被保人信息部分
	showOneCodeName('SequenceNo','SequenceNo','SequenceNoName');
	showOneCodeName('Relation','RelationToMainInsured','RelationToMainInsuredName');
	showOneCodeName('Relation','RelationToAppnt','RelationToAppntName');
	showOneCodeName('vipvalue','InsuredVIPFlag','InsuredVIPFlagName');
	showOneCodeName('IDType','InsuredIDType','InsuredIDTypeName');
	showOneCodeName('Sex','InsuredSex','InsuredSexName');
	getInsuredAppAge();
	showOneCodeName('Marriage','InsuredMarriage','InsuredMarriageName');
	showOneCodeName('NativePlace','InsuredNativePlace','InsuredNativePlaceName');
	showOneCodeName('LicenseType','InsuredLicenseType','InsuredLicenseTypeName');
	showOneCodeName('OccupationCode','InsuredOccupationCode','InsuredOccupationCodeName');
	showOneCodeName('OccupationType','InsuredOccupationType','InsuredOccupationTypeName');
	//showOneCodeName('province','InsuredProvince','InsuredProvinceName');
	//showOneCodeName('city','InsuredCity','InsuredCityName');
	//showOneCodeName('district','InsuredDistrict','InsuredDistrictName');
	getAddressName('province','InsuredProvince','InsuredProvinceName');
	getAddressName('city','InsuredCity','InsuredCityName');
	getAddressName('district','InsuredDistrict','InsuredDistrictName'); 
}



/**
* 计算投保人被保人年龄《投保日期与生日之差=投保人被保人年龄》,2005-11-18日添加
* 参数，出生日期yy－mm－dd；投保日期yy－mm－dd
* 返回  年龄
*/
function calPolAppntAge(BirthDate,PolAppntDate)
{
	var age ="";
	if(BirthDate=="" || PolAppntDate=="")
	{
		age ="";
		return age;
	}
	var arrBirthDate = BirthDate.split("-");
	if (arrBirthDate[1].length == 1) arrBirthDate[1] = "0" + arrBirthDate[1];
	if (arrBirthDate[2].length == 1) arrBirthDate[2] = "0" + arrBirthDate[2];
//	alert("生日"+arrBirthDate);	
	var arrPolAppntDate = PolAppntDate.split("-");
	if (arrPolAppntDate[1].length == 1) arrPolAppntDate[1] = "0" + arrBirthDate[1];
	if (arrPolAppntDate[2].length == 1) arrPolAppntDate[2] = "0" + arrBirthDate[2];
//	alert("投保日期"+arrPolAppntDate);
	if(arrPolAppntDate[0]<=99)
	{
		arrBirthDate[0]=	arrBirthDate[0]-1900;
	}
	age = arrPolAppntDate[0] - arrBirthDate[0] - 1;
	//当前月大于出生月
	//alert(arrPolAppntDate[1] + " | " + arrBirthDate[1] + " | " + (arrPolAppntDate[1] > arrBirthDate[1]));
	if (arrPolAppntDate[1] > arrBirthDate[1]) 
	{
		age = age + 1;
		return age;
	}
	//当前月小于出生月
	else if (arrPolAppntDate[1] < arrBirthDate[1]) 
	{
		return age;
	}
  	//当前月等于出生月的时候，看出生日
	else if (arrPolAppntDate[2] >= arrBirthDate[2])
	{
		age = age + 1;
		return age;
  	}
	else
	{
		return age;
	}
}

//------------------------Beg
//Add By Chenrong
//Date:2006.5.15

//查询操作员随动速度
function initQueryRollSpeed()
{
//    var strSQL = "select SYSVAR,SYSVARVALUE from LDSysvar where SYSVAR like 'ROLLSPEED%25' order by SYSVAR ASC";
	
	var sqlid47="DirectContInputSql47";
	var mySql47=new SqlClass();
	mySql47.setResourceName("app.DirectContInputSql"); //指定使用的properties文件名
	mySql47.setSqlId(sqlid47);//指定使用的Sql的id
 	var strSQL=mySql47.getString();
    
    var arrSpeed = easyExecSql(strSQL); 
	if (arrSpeed != null)
	{
    	fm.RollSpeedBase.value = arrSpeed[0][1];
    	fm.RollSpeedSelf.value = arrSpeed[1][1];
	}

//    strSQL = "select rollspeed from LDRollSpeed where UserCode='" + fm.Operator.value + "'";
    
	var sqlid48="DirectContInputSql48";
	var mySql48=new SqlClass();
	mySql48.setResourceName("app.DirectContInputSql"); //指定使用的properties文件名
	mySql48.setSqlId(sqlid48);//指定使用的Sql的id
	mySql48.addSubPara(fm.Operator.value);//指定传入的参数
 	strSQL=mySql48.getString();
    
	arrSpeed1 = null;
	arrSpeed1 = easyExecSql(strSQL); 
	if (arrSpeed1 != null)
	{
    	fm.RollSpeedOperator.value = arrSpeed1[0][0];
	}
    else
        fm.RollSpeedOperator.value = 1;
}

//需自动获得焦点的控件名称
var autoFieldArray = new Array
autoFieldArray = new Array
	(
		"ProposalContNo", "PolApplyDate",
		"AgentCode", "AgentName", 
		"TelephonistCode", "TelephonistName", "CSplit", 
		"AppntName", "AppntIDType", "AppntIDNo", "AppntSex", "AppntBirthday", 
		"AppntAge", "AppntOccupationCode", "AppntOccupationType", 
		"AppntProvince", "AppntCity", "AppntDistrict", "AppntPostalAddress", "AppntZipCode", 
		 "AppntGrpPhone", "AppntHomePhone", "AppntMobile",
		"NewPayMode", "NewBankCode", "NewAccName", "NewBankAccNo", 
		"SecPayMode", "SecBankCode", "SecAccName", "SecBankAccNo", 
		"RelationToAppnt", 
		"InsuredName", "InsuredIDType", "InsuredIDNo", "InsuredSex", "InsuredBirthday", 
		"InsuredAppAge", "InsuredOccupationCode", "InsuredOccupationType", 
		"InsuredProvince", "InsuredCity", "InsuredDistrict", "InsuredPostalAddress", "InsuredZipCode", 
		"InsuredPhone",
		"HiddenInsuredImpart"
	);  


//自动获得焦点
function AutoTab()
{
    if (LoadFlag != "5" || scantype != "scan")
        return;
    var tObject=document.activeElement;
    if (tObject.tagName == "INPUT")
        return;
	initAutoValue(autoFieldArray,fm.RollSpeedOperator.value,fm.RollSpeedSelf.value,fm.RollSpeedBase.value);
}
//-----------------------End





