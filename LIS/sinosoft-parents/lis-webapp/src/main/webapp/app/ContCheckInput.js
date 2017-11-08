//               该文件中包含客户端需要处理的函数和事件
var showInfo;
var approvefalg;
var arrResult;
var mDebug = "0";
var mOperate = 0;
var mAction = "";
var mSwitch = parent.VD.gVSwitch;
var newturnPage = new turnPageClass();
var turnPage = new turnPageClass();
//this.window.onfocus=myonfocus;
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";


//初始化时，查询合同信息、投保人信息(包括交费信息)、被保人信息(包括告知信息)，险种信息，并显示在界面
function initQuery()
{
	initQueryCont();//【初始化时查询合同信息】-----包括代理人信息
	initqueryAppnt();//【查询投保人信息】－－－包括投保人的地址信息
	initqueryAccount();//【缴费信息查询】------查询保单的缴费信息，包括缴费方式及帐号信息等。
	initQueryAgentImpartGrid();//查询 业务员告知信息
    initQueryAppntImpartInfo();	//查询投保人告知信息
	initqueryInsuredInfo();//【查询被保人信息】----放入列表
	
	if (LoadFlag == "5" && scantype == "scan")
	    initQueryRollSpeed();


	getInsuredInfo();//被保人基本信息及地址信息

	getInsuredImpartInfo();//被保人告知信息
	
	getInsuredPolInfo();//被保人险种信息
	getInputFieldName();	//根据页面输入框的代码带出汉字，并显示出来。
	//判断系统中是否存在姓名、性别、出生日期相同或证件类型证件号码相同的投保人信息。
  
     AppntChkNew();
	 // 在初始化body时自动效验被保人信息
 
     InsuredChkNew();
     displayInsuredButton();
	//判断记事本中的记录数量
	
	checkNotePad(prtNo,LoadFlag);
	//按钮可选判断
    ctrlButtonDisabled(prtNo,LoadFlag);
  //  alert("@@@"+LoadFlag);
	 if(LoadFlag=='5')
	 {
	 	divCustomerUnionButton.style.display="none";  
	 	divApproveContButton.style.display="";  
		}
		 if(LoadFlag=='a')
	 	{
	 		divApproveContButton.style.display="none";  
	 		divCustomerUnionButton.style.display="";  
		}
}

//【初始化时查询合同信息】
function initQueryCont()
{
	var sSQL="";
	if(document.all('ContNo').value=="")
	{
		
	    var sqlid1="ContCheckInputSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("app.ContCheckInputSql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(fm.PrtNo.value);//指定传入的参数
	    var sSQL=mySql1.getString();	
		
//		var sSQL="select contno,proposalcontno,prtno,polapplydate,selltype,salechnl,managecom,agentcode,cvalidate,"
//			+"OutPayFlag,AutoPayFlag,RnewFlag,GetPolMode,agentcom,signname,FirstTrialDate"
//			+" from lccont where prtno='"+fm.PrtNo.value+"' ";
	}
	else
	{
		
		var sqlid2="ContCheckInputSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("app.ContCheckInputSql"); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(fm.ContNo.value);//指定传入的参数
	    var sSQL=mySql2.getString();
		
//		var sSQL="select contno,proposalcontno,prtno,polapplydate,selltype,salechnl,managecom,agentcode,cvalidate,"
//			+"OutPayFlag,AutoPayFlag,RnewFlag,GetPolMode,agentcom,signname,FirstTrialDate"
//			+" from lccont where contno='"+fm.ContNo.value+"' ";
	}
	var ArrCont=easyExecSql(sSQL);//prompt("",sSQL);
	if(ArrCont!=null)
	{
	fm.ContNo.value=ArrCont[0][0];	
	fm.ProposalContNo.value=ArrCont[0][1];	
	fm.PrtNo.value=ArrCont[0][2];	
	fm.PolAppntDate.value=ArrCont[0][3];	
	
	//alert('fm.PolAppntDate.value:'+fm.PolAppntDate.value);
	fm.SellType.value=ArrCont[0][4];	
	fm.SaleChnl.value=ArrCont[0][5];	
	fm.ManageCom.value=ArrCont[0][6];	
	fm.AgentCode.value=ArrCont[0][7];	
	fm.CValiDate.value=ArrCont[0][8];	
	fm.OutPayFlag.value=ArrCont[0][9];	
	fm.AutoPayFlag.value=ArrCont[0][10];	
	fm.RnewFlag.value=ArrCont[0][11];	
	fm.GetPolMode.value=ArrCont[0][12];	
	fm.AgentCom.value=ArrCont[0][13];	
	fm.SignName.value=ArrCont[0][14];	
	fm.FirstTrialDate.value = ArrCont[0][15];	
	fm.XQremindFlag.value = ArrCont[0][16];	
	}
	queryAgent();//查询 代理人信息
	//查询代理人与投保人关系
	
		var sqlid3="ContCheckInputSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("app.ContCheckInputSql"); //指定使用的properties文件名
		mySql3.setSqlId(sqlid3);//指定使用的Sql的id
		mySql3.addSubPara(fm.ContNo.value);//指定传入的参数
		mySql3.addSubPara(fm.AgentCode.value);//指定传入的参数
	    var relaSQL=mySql3.getString();
	
//	var relaSQL="select grpcontno,agentcode,relationship from lacommisiondetail where 1=1"
//    			+ " and grpcontno='"+document.all('ContNo').value+"' "
//    			+ " and agentcode='"+document.all('AgentCode').value+"' ";	
	var arrRela=easyExecSql(relaSQL);
	if(arrRela!=null)
	{
		try { document.all('RelationShip').value =""; } catch(ex) { };
		try { document.all('RelationShipName').value =""; } catch(ex) { };
		try { document.all('RelationShip').value = arrRela[0][2]; } catch(ex) { };
		//showOneCodeName('agentrelatoappnt','RelationShip','RelationShipName');
	}
}
//查询 代理人信息
function queryAgent()
{
	if(document.all('AgentCode').value != "")
	{
		var cAgentCode = fm.AgentCode.value;  //保单号码
		//如果业务员代码长度为8则自动查询出相应的代码名字等信息
		//if(cAgentCode.length!=8){return;}
		
		var sqlid4="ContCheckInputSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName("app.ContCheckInputSql"); //指定使用的properties文件名
		mySql4.setSqlId(sqlid4);//指定使用的Sql的id
		mySql4.addSubPara(cAgentCode);//指定传入的参数
	    var strSQL=mySql4.getString();
		
//		var strSQL = "select a.AgentCode,a.branchcode,a.ManageCom,a.Name,c.BranchManager,c.BranchAttr,c.name from LAAgent a,LABranchGroup c where 1=1 "
//		 + "and a.agentstate<='03' and a.branchcode = c.AgentGroup and a.AgentCode='"+cAgentCode+"'";
		var arrResult = easyExecSql(strSQL);
		if (arrResult != null) 
		{
			fm.AgentCode.value = arrResult[0][0];
			fm.BranchAttr.value = arrResult[0][5];
			fm.AgentGroup.value = arrResult[0][1];
			fm.AgentName.value = arrResult[0][3];
			fm.AgentManageCom.value = arrResult[0][2];
			if(fm.AgentManageCom.value != fm.ManageCom.value)
			{	
				fm.ManageCom.value = fm.AgentManageCom.value;
				fm.ManageComName.value = fm.AgentManageComName.value;
			}
		}
		if(document.all('AgentCom').value!=null&&document.all('AgentCom').value!='')
		{
			
		var sqlid5="ContCheckInputSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName("app.ContCheckInputSql"); //指定使用的properties文件名
		mySql5.setSqlId(sqlid5);//指定使用的Sql的id
		mySql5.addSubPara(document.all('AgentCom').value);//指定传入的参数
	    var strSQL=mySql5.getString();
			
//		var strSQL = "select name from lacom where agentcom='"+ document.all('AgentCom').value+"'";
		var arrResult = easyExecSql(strSQL,1,0);
		if(arrResult!==null)
		{ 
			//tongmeng 2007-09-10 本地化去掉银行编码
			document.all('InputAgentComName').value=arrResult[0][0]; 

		}
		}
		
	}
}
//查询 业务员告知信息
function initQueryAgentImpartGrid()
{
	var turnPageAgent = new turnPageClass();
	var tContNo=fm.ContNo.value;  
	var tAgentCode=fm.AgentCode.value;
	var tAppntNo=fm.AppntNo.value;
	//因为所有业务员告知的 customerno存储的是投保人客户号，所以查询时用投保人客户号
	
		var sqlid6="ContCheckInputSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName("app.ContCheckInputSql"); //指定使用的properties文件名
		mySql6.setSqlId(sqlid6);//指定使用的Sql的id
		mySql6.addSubPara(tAppntNo);//指定传入的参数
		mySql6.addSubPara(tContNo);//指定传入的参数
	    var aSQL=mySql6.getString();
	
//	var aSQL="select impartver,impartcode,impartcontent,impartparammodle from lccustomerimpart"
//		+" where customernotype='2' and customerno='"+tAppntNo+"' and contno='"+tContNo+"'";
   	turnPageAgent.queryModal(aSQL, AgentImpartGrid);
}

//【查询投保人信息】－－－包括投保人的地址信息
function initqueryAppnt()
{
	//根据"合同号"查询出投保人信息，并填入相应输入栏
	
			var sqlid7="ContCheckInputSql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName("app.ContCheckInputSql"); //指定使用的properties文件名
		mySql7.setSqlId(sqlid7);//指定使用的Sql的id
		mySql7.addSubPara(fm.ContNo.value);//指定传入的参数
	    var sSQL=mySql7.getString();
	
//	var sSQL="select contno,appntno,addressno,(select vipvalue from ldperson where customerno=appntno),"
//			+"appntname,appntsex,appntbirthday,idtype,idno,"
//			+"marriage,nativeplace,licensetype,occupationcode,occupationtype,"
//			+"makedate,maketime,modifydate,modifytime,relationtoinsured,worktype,pluralitytype,rgtAddress "
//			+" from lcappnt where contno='"+fm.ContNo.value+"'";
	var arrApp=easyExecSql(sSQL); 
	if(arrApp!=null)
	{
		document.all("AppntNo").value=arrApp[0][1];
		document.all("AppntAddressNo").value=arrApp[0][2];
		//queryAppntNo(trim(document.all("AppntNo").value));
		fm.AppntVIPValue.value=arrApp[0][3];
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
		fm.RelationToInsured.value=arrApp[0][18];
		fm.AppntWorkType.value=arrApp[0][19];
		fm.AppntPluralityType.value=arrApp[0][20];
		fm.AppntPlace.value=arrApp[0][21];
		try{document.all('AppntLastName').value= arrApp[0][22];}catch(ex){};
	try{document.all('AppntFirstName').value= arrApp[0][23];}catch(ex){};
	try{document.all('AppntLastNameEn').value= arrApp[0][24];}catch(ex){};
	try{document.all('AppntFirstNameEn').value= arrApp[0][25];}catch(ex){};
	try{document.all('AppntLastNamePY').value= arrApp[0][26];}catch(ex){};
	try{document.all('AppntFirstNamePY').value= arrApp[0][27];}catch(ex){};
  try{document.all('AppntLanguage').value= arrApp[0][28];}catch(ex){};
  //add by lvshuaiqi 2016-05-12 中科软商务楼 
  fm.AppIDPeriodOfValidity.value=arrApp[0][29];
  fm.AppSocialInsuFlag.value=arrApp[0][30];
  //alert('fm.AppntName.value:'+fm.AppntName.value);
  //alert('fm.AppntLastName.value:'+fm.AppntLastName.value);
		getAge();//投保人年龄
		//查询投保人地址信息
		
		var sqlid8="ContCheckInputSql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName("app.ContCheckInputSql"); //指定使用的properties文件名
		mySql8.setSqlId(sqlid8);//指定使用的Sql的id
		mySql8.addSubPara(fm.AppntNo.value);//指定传入的参数
		mySql8.addSubPara(fm.AppntAddressNo.value);//指定传入的参数
	    var sAppAddSQL=mySql8.getString();
		
//		var sAppAddSQL="select customerno,addressno,province,city,county,postaladdress,zipcode,mobile,phone,homephone,fax,grpname,email,homeaddress,homezipcode"
//		+ " from lcaddress where customerno='"+document.all("AppntNo").value+"' "
//		+ " and addressno='"+document.all("AppntAddressNo").value+"'";
		var arrAppAdd=easyExecSql(sAppAddSQL); 
		if(arrAppAdd!=null)
		{
			fm.AddressNoName.value=arrAppAdd[0][5];  
	     	fm.AppntProvince.value=arrAppAdd[0][2];  
			fm.AppntProvinceName.value=arrAppAdd[0][2];
			fm.AppntCity.value=arrAppAdd[0][3];  
			fm.AppntCityName.value=arrAppAdd[0][3];
			fm.AppntDistrict.value=arrAppAdd[0][4];  
			fm.AppntDistrictName.value=arrAppAdd[0][4];
			fm.AppntPostalAddress.value=arrAppAdd[0][5];  
			fm.AppntZipCode.value=arrAppAdd[0][6];  
			fm.AppntMobile.value=arrAppAdd[0][7];  
			fm.AppntPhone.value=arrAppAdd[0][8];  
			fm.AppntHomePhone.value=arrAppAdd[0][9];  
			fm.AppntFax.value=arrAppAdd[0][10];  
			fm.AppntGrpName.value=arrAppAdd[0][11];  
			fm.AppntEMail.value=arrAppAdd[0][12]; 
			fm.AppntHomeAddress.value=arrAppAdd[0][13];  
			fm.AppntHomeZipCode.value=arrAppAdd[0][14]; 
		}
	}
}

//【缴费信息查询】------查询保单的缴费信息，包括缴费方式及帐号信息等。
function initqueryAccount()
{
	
		var sqlid9="ContCheckInputSql9";
		var mySql9=new SqlClass();
		mySql9.setResourceName("app.ContCheckInputSql"); //指定使用的properties文件名
		mySql9.setSqlId(sqlid9);//指定使用的Sql的id
		mySql9.addSubPara(fm.ContNo.value);//指定传入的参数
	    var sAccSQL=mySql9.getString();
	
//	var sAccSQL="select contno,newpaymode,newbankcode,newaccname,newbankaccno,paylocation,bankcode,accname,bankaccno "
//		+" from lccont where contno='"+fm.ContNo.value+"'";
	var arrAcc=easyExecSql(sAccSQL);	
	if(arrAcc!=null)
	{
		fm.PayMode.value=arrAcc[0][1];
		fm.AppntBankCode.value=arrAcc[0][2];
		fm.AppntAccName.value=arrAcc[0][3];
		fm.AppntBankAccNo.value=arrAcc[0][4];
		fm.SecPayMode.value=arrAcc[0][5];
		fm.SecAppntBankCode.value=arrAcc[0][6];
		fm.SecAppntAccName.value=arrAcc[0][7];
		fm.SecAppntBankAccNo.value=arrAcc[0][8];
	}	
}

 //查询投保人告知信息
function initQueryAppntImpartInfo()
{
	//查询 投保人收入、收入来源、身高、体重等告知信息，并填入相应的输入框中
	var tContNo=fm.ContNo.value;
	var tAppntNo=fm.AppntNo.value;
	
		var sqlid10="ContCheckInputSql10";
		var mySql10=new SqlClass();
		mySql10.setResourceName("app.ContCheckInputSql"); //指定使用的properties文件名
		mySql10.setSqlId(sqlid10);//指定使用的Sql的id
		mySql10.addSubPara(tAppntNo);//指定传入的参数
		mySql10.addSubPara(tContNo);//指定传入的参数
	    var strSQL0=mySql10.getString();
	
			var sqlid11="ContCheckInputSql11";
		var mySql11=new SqlClass();
		mySql11.setResourceName("app.ContCheckInputSql"); //指定使用的properties文件名
		mySql11.setSqlId(sqlid11);//指定使用的Sql的id
		mySql11.addSubPara(tAppntNo);//指定传入的参数
		mySql11.addSubPara(tContNo);//指定传入的参数
	    var strSQL1=mySql11.getString();
	
		var sqlid12="ContCheckInputSql12";
		var mySql12=new SqlClass();
		mySql12.setResourceName("app.ContCheckInputSql"); //指定使用的properties文件名
		mySql12.setSqlId(sqlid12);//指定使用的Sql的id
		mySql12.addSubPara(tAppntNo);//指定传入的参数
		mySql12.addSubPara(tContNo);//指定传入的参数
	    var strSQL2=mySql12.getString();
		
		var sqlid13="ContCheckInputSql13";
		var mySql13=new SqlClass();
		mySql13.setResourceName("app.ContCheckInputSql"); //指定使用的properties文件名
		mySql13.setSqlId(sqlid13);//指定使用的Sql的id
		mySql13.addSubPara(tAppntNo);//指定传入的参数
		mySql13.addSubPara(tContNo);//指定传入的参数
	    var strSQL3=mySql13.getString();
	
//    var strSQL0="select ImpartParam from LCCustomerImpartparams where CustomerNoType='0' and CustomerNo='"+tAppntNo+"' and ContNo='"+tContNo+"' and impartver='01' and impartcode='001' and ImpartParamNo = '1'";
//    var strSQL1="select ImpartParam from LCCustomerImpartparams where CustomerNoType='0' and CustomerNo='"+tAppntNo+"' and ContNo='"+tContNo+"' and impartver='01' and impartcode='001' and ImpartParamNo = '2'";
//    var strSQL2="select ImpartParam from LCCustomerImpartparams where CustomerNoType='0' and CustomerNo='"+tAppntNo+"' and ContNo='"+tContNo+"' and impartver='02' and impartcode='000' and ImpartParamNo = '1'";
//    var strSQL3="select ImpartParam from LCCustomerImpartparams where CustomerNoType='0' and CustomerNo='"+tAppntNo+"' and ContNo='"+tContNo+"' and impartver='02' and impartcode='000' and ImpartParamNo = '2'";
    var arrResult0 = easyExecSql(strSQL0,1,0);
    var arrResult1 = easyExecSql(strSQL1,1,0);
    var arrResult2 = easyExecSql(strSQL2,1,0);
    var arrResult3 = easyExecSql(strSQL3,1,0);
	try{document.all('Income0').value= arrResult0[0][0];}catch(ex){};
	try{document.all('IncomeWay0').value= arrResult1[0][0];}catch(ex){};
	try{document.all('AppntStature').value= arrResult2[0][0];}catch(ex){};
	try{document.all('AppntAvoirdupois').value= arrResult3[0][0];}catch(ex){};
	//查询投保人告知，并放入表格中
//	var turnPage1 = new turnPageClass();
/*	var strSQL="select impartver,impartcode,impartcontent,impartparammodle "
		+" from lccustomerimpart where customernotype='0'"
		+" and ((impartver ='01' and impartcode<>'001') or (impartver ='02' and impartcode<>'000'))"
		+" and customerno='"+tAppntNo+"' and contno='"+tContNo+"'";
	turnPage.queryModal(strSQL,ImpartGrid);	
*/

		var sqlid14="ContCheckInputSql14";
		var mySql14=new SqlClass();
		mySql14.setResourceName("app.ContCheckInputSql"); //指定使用的properties文件名
		mySql14.setSqlId(sqlid14);//指定使用的Sql的id
		mySql14.addSubPara(tAppntNo);//指定传入的参数
		mySql14.addSubPara(tContNo);//指定传入的参数
	    var tSQL=mySql14.getString();

//    var tSQL = "select impartver,impartcode,impartcontent,impartparammodle from LCCustomerImpart where CustomerNoType='0' and CustomerNo='"+tAppntNo+"' and ContNo='"+tContNo+"'"
//             + " and ((impartver='01' and impartcode<>'001') or impartver<>'01')";
	turnPage.queryModal(tSQL,ImpartGrid);	
	//查询其它声明（200个汉字以内）
	
			var sqlid15="ContCheckInputSql15";
		var mySql15=new SqlClass();
		mySql15.setResourceName("app.ContCheckInputSql"); //指定使用的properties文件名
		mySql15.setSqlId(sqlid15);//指定使用的Sql的id
		mySql15.addSubPara(tContNo);//指定传入的参数
	    var remarkSQL=mySql15.getString();
	
//	var remarkSQL="select remark from lccont where contno='"+tContNo+"'";
	var remarkArr= easyExecSql(remarkSQL,1,0);
	try{document.all('Remark').value= remarkArr[0][0];}catch(ex){};
}

//【查询被保人信息】－－－
function initqueryInsuredInfo()
{
	
		var sqlid16="ContCheckInputSql16";
		var mySql16=new SqlClass();
		mySql16.setResourceName("app.ContCheckInputSql"); //指定使用的properties文件名
		mySql16.setSqlId(sqlid16);//指定使用的Sql的id
		mySql16.addSubPara(fm.ContNo.value);//指定传入的参数
	    var strSQL=mySql16.getString();
	
//	var strSQL ="select insuredno,name,sex,birthday,relationtomaininsured,sequenceno "
//			+" from lcinsured where contno='"+fm.ContNo.value+"' order by sequenceno";
	var turnInsurePage = new turnPageClass();
	turnInsurePage.queryModal(strSQL,InsuredGrid);
	if (InsuredGrid.mulLineCount>0)
	{
	try{
		//document.all(InsuredGrid.findSpanID(0)).all('InsuredGridSel').checked =true;
		var selectRowNum = InsuredGrid.findSpanID(0).replace(/spanInsuredGrid/g,"");
		document.all('InsuredGridSel'+selectRowNum).checked =true;
		} catch(ex) {}
	try{fm.InsuredNo.value=InsuredGrid.getRowColData(0,1);} catch(ex) {}
	//查询选中的被保人信息
	//getInsuredInfo();//被保人基本信息及地址信息
	//getInsuredImpartInfo();//被保人告知信息
	//getInsuredPolInfo();//被保人险种信息
	}
}

/*********************************************************************
 *  InsuredGrid的RadioBox点击事件，获得被保人详细信息，填入被保人信息表
 **********************************************************************/
function getInsuredDetail(parm1,parm2)
{
    emptyInsured();//清除被保人录入区域，重新赋值
	var selectRowNum = parm1.replace(/spanPolGrid/g,"");
    fm.InsuredNo.value = document.all('InsuredGrid'+'r'+selectRowNum).value;
    fm.InsuredNo.value=document.all(parm1).all('InsuredGrid1').value;
    //查询选中的被保人信息
	getInsuredInfo();//被保人基本信息及地址信息
	getInsuredImpartInfo();//被保人告知信息
	getInsuredPolInfo();//被保人险种信息
	getInputFieldName();//显示汉字信息
}

//被保人基本信息及地址信息
function getInsuredInfo()
{
	var tContNo=fm.ContNo.value;
	var tInsuredNo=fm.InsuredNo.value;
	if(tContNo=="" || tInsuredNo=="")
	{
		return false;
	}
	//根据"合同号"和 "被保人号码"查询出被保人信息，并填入相应输入栏
	
			var sqlid17="ContCheckInputSql17";
		var mySql17=new SqlClass();
		mySql17.setResourceName("app.ContCheckInputSql"); //指定使用的properties文件名
		mySql17.setSqlId(sqlid17);//指定使用的Sql的id
		mySql17.addSubPara(tContNo);//指定传入的参数
		mySql17.addSubPara(tInsuredNo);//指定传入的参数
	    var sSQL=mySql17.getString();
	
//	var sSQL="select contno,insuredno,sequenceno,relationtomaininsured,relationtoappnt,addressno,"
//			+"(select vipvalue from ldperson where customerno=insuredno),"
//			+"name,sex,birthday,idtype,idno,"
//			+"marriage,nativeplace,licensetype,occupationcode,occupationtype,worktype,pluralitytype,rgtaddress"
//			+" from lcinsured where contno='"+tContNo+"' and insuredno='"+tInsuredNo+"' ";
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
		fm.VIPValue1.value=arrInsured[0][6];
		fm.Name.value=arrInsured[0][7];
		fm.Sex.value=arrInsured[0][8];
		fm.Birthday.value=arrInsured[0][9];
		fm.IDType.value=arrInsured[0][10];
		fm.IDNo.value=arrInsured[0][11];
		fm.Marriage.value=arrInsured[0][12];
		fm.NativePlace.value=arrInsured[0][13];
		fm.LicenseType.value=arrInsured[0][14];
		fm.OccupationCode.value=arrInsured[0][15];
		fm.OccupationType.value=arrInsured[0][16];
		fm.WorkType.value=arrInsured[0][17];
		fm.PluralityType.value=arrInsured[0][18];
		fm.InsuredPlace.value=arrInsured[0][19];
		 try{document.all('InsuredLastName').value= arrInsured[0][20];}catch(ex){};
  try{document.all('InsuredFirstName').value= arrInsured[0][21];}catch(ex){};
  try{document.all('InsuredLastNameEn').value= arrInsured[0][22];}catch(ex){};
  try{document.all('InsuredFirstNameEn').value= arrInsured[0][23];}catch(ex){};
  try{document.all('InsuredLastNamePY').value= arrInsured[0][24];}catch(ex){};
  try{document.all('InsuredFirstNamePY').value= arrInsured[0][25];}catch(ex){};
  try{document.all('InsuredLanguage').value= arrInsured[0][26];}catch(ex){};
  		//add by lvshuaiqi 2016-05-12      中科软商务楼
  		fm.IDPeriodOfValidity.value=arrInsured[0][27];
  		fm.SocialInsuFlag.value=arrInsured[0][28];
  		
		getInsuredSequencename();//根据客户内部号码显示标题，用于随动处理
		getAge2();//被保人年龄龄
		//查询被保人的地址信息
		
		var sqlid18="ContCheckInputSql18";
		var mySql18=new SqlClass();
		mySql18.setResourceName("app.ContCheckInputSql"); //指定使用的properties文件名
		mySql18.setSqlId(sqlid18);//指定使用的Sql的id
		mySql18.addSubPara(document.all("InsuredNo").value);//指定传入的参数
		mySql18.addSubPara(document.all("InsuredAddressNo").value);//指定传入的参数
	    var sInsuredAddSQL=mySql18.getString();
		
//		var sInsuredAddSQL="select customerno,addressno,province,city,county,postaladdress,zipcode,mobile,phone,homephone,fax,grpname,email"
//				+ " from lcaddress where customerno='"+document.all("InsuredNo").value+"' "
//				+ " and addressno='"+document.all("InsuredAddressNo").value+"'";
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
			fm.PostalAddress.value=arrInsuredAdd[0][5];  
			fm.ZIPCODE.value=arrInsuredAdd[0][6];  
			fm.Mobile.value=arrInsuredAdd[0][7];  
			fm.Phone.value=arrInsuredAdd[0][8];  
			fm.HomePhone.value=arrInsuredAdd[0][9];  
			fm.Fax.value=arrInsuredAdd[0][10];  
			fm.GrpName.value=arrInsuredAdd[0][11];  
			fm.EMail.value=arrInsuredAdd[0][12]; 
		}
	}
}

//根据客户内部号码显示标题，用于随动处理
function getInsuredSequencename()
{
	if(fm.SequenceNo.value=="1")
    {
		param="11";
		fm.pagename.value="11";
		fm.InsuredSequencename.value="被保险人资料";
    }
	else if(fm.SequenceNo.value=="2")
	{
		param="122";
		fm.pagename.value="122";
		fm.InsuredSequencename.value="第二被保险人资料";
	}
	else if(fm.SequenceNo.value=="3")
	{
		param="123";
		fm.pagename.value="123";
		fm.InsuredSequencename.value="第三被保险人资料";
	}
	else 
	{}
	if (scantype== "scan") {  setFocus(); }
}

//查询被保人告知信息信息
function getInsuredImpartInfo()
{
	//查询 被保人收入、收入来源、身高、体重等告知信息，并填入相应的输入框中
	var tContNo=fm.ContNo.value;
	var tInsuredNo=fm.InsuredNo.value;
	if(tContNo=="" || tInsuredNo=="")
	{
		return false;
	}
	
		var sqlid19="ContCheckInputSql19";
		var mySql19=new SqlClass();
		mySql19.setResourceName("app.ContCheckInputSql"); //指定使用的properties文件名
		mySql19.setSqlId(sqlid19);//指定使用的Sql的id
		mySql19.addSubPara(tInsuredNo);//指定传入的参数
		mySql19.addSubPara(tContNo);//指定传入的参数
	    var strSQL0=mySql19.getString();
		
		var sqlid20="ContCheckInputSql20";
		var mySql20=new SqlClass();
		mySql20.setResourceName("app.ContCheckInputSql"); //指定使用的properties文件名
		mySql20.setSqlId(sqlid20);//指定使用的Sql的id
		mySql20.addSubPara(tInsuredNo);//指定传入的参数
		mySql20.addSubPara(tContNo);//指定传入的参数
	    var strSQL1=mySql20.getString();
		
		var sqlid21="ContCheckInputSql21";
		var mySql21=new SqlClass();
		mySql21.setResourceName("app.ContCheckInputSql"); //指定使用的properties文件名
		mySql21.setSqlId(sqlid21);//指定使用的Sql的id
		mySql21.addSubPara(tInsuredNo);//指定传入的参数
		mySql21.addSubPara(tContNo);//指定传入的参数
	    var strSQL2=mySql21.getString();
		
		var sqlid22="ContCheckInputSql22";
		var mySql22=new SqlClass();
		mySql22.setResourceName("app.ContCheckInputSql"); //指定使用的properties文件名
		mySql22.setSqlId(sqlid22);//指定使用的Sql的id
		mySql22.addSubPara(tInsuredNo);//指定传入的参数
		mySql22.addSubPara(tContNo);//指定传入的参数
	    var strSQL3=mySql22.getString();
	
//    var strSQL0="select ImpartParam from LCCustomerImpartparams where CustomerNoType='1' and CustomerNo='"+tInsuredNo+"' and ContNo='"+tContNo+"' and impartver='01' and impartcode='001' and ImpartParamNo = '1'";
//    var strSQL1="select ImpartParam from LCCustomerImpartparams where CustomerNoType='1' and CustomerNo='"+tInsuredNo+"' and ContNo='"+tContNo+"' and impartver='01' and impartcode='001' and ImpartParamNo = '2'";
//    var strSQL2="select ImpartParam from LCCustomerImpartparams where CustomerNoType='1' and CustomerNo='"+tInsuredNo+"' and ContNo='"+tContNo+"' and impartver='02' and impartcode='000' and ImpartParamNo = '1'";
//    var strSQL3="select ImpartParam from LCCustomerImpartparams where CustomerNoType='1' and CustomerNo='"+tInsuredNo+"' and ContNo='"+tContNo+"' and impartver='02' and impartcode='000' and ImpartParamNo = '2'";
    
	var arrResult0 = easyExecSql(strSQL0,1,0);
    var arrResult1 = easyExecSql(strSQL1,1,0);
    var arrResult2 = easyExecSql(strSQL2,1,0);
    var arrResult3 = easyExecSql(strSQL3,1,0);
	try{document.all('Income').value= arrResult0[0][0];}catch(ex){};
	try{document.all('IncomeWay').value= arrResult1[0][0];}catch(ex){};
	try{document.all('Stature').value= arrResult2[0][0];}catch(ex){};
	try{document.all('Avoirdupois').value= arrResult3[0][0];}catch(ex){};
	//查询被保人告知，并放入表格中
//	var turnPage1 = new turnPageClass();

		var sqlid23="ContCheckInputSql23";
		var mySql23=new SqlClass();
		mySql23.setResourceName("app.ContCheckInputSql"); //指定使用的properties文件名
		mySql23.setSqlId(sqlid23);//指定使用的Sql的id
		mySql23.addSubPara(tInsuredNo);//指定传入的参数
		mySql23.addSubPara(tContNo);//指定传入的参数
	    var strSQL=mySql23.getString();

//	var strSQL="select impartver,impartcode,impartcontent,impartparammodle "
//		+" from lccustomerimpart a where customernotype='1'"
//		+ " and impartcode<>'001'"
//		+" and customerno='"+tInsuredNo+"' and contno='"+tContNo+"'";
		
	//	prompt('',strSQL);
	turnPage.queryModal(strSQL,ImpartInsuredGrid);	
	
}

/*********************************************************************
 *  获得被保人险种信息，写入MulLine
 ****************************************************************** */
function getInsuredPolInfo()
{
    var tInsuredNo=document.all("InsuredNo").value;
    var tContNo=document.all("ContNo").value;
    if(tContNo=="" || tInsuredNo=="")
	{
		return false;
	}
	
		var sqlid24="ContCheckInputSql24";
		var mySql24=new SqlClass();
		mySql24.setResourceName("app.ContCheckInputSql"); //指定使用的properties文件名
		mySql24.setSqlId(sqlid24);//指定使用的Sql的id
		mySql24.addSubPara(tInsuredNo);//指定传入的参数
		mySql24.addSubPara(tContNo);//指定传入的参数
	    var strSQL=mySql24.getString();
	
//    var strSQL ="select polno,riskcode,(select riskname from lmriskapp where riskcode=lcpol.riskcode), "
//    	+" prem,amnt,mult,years,decode(sign(payintv), 1, to_char(payyears), -1, to_char(payyears), ' ')"
//    	+" from lcpol "
//    	+" where insuredno='"+tInsuredNo+"' and ContNo='"+tContNo+"'";
		
    turnPage.queryModal(strSQL,PolGrid);
    if (PolGrid.mulLineCount>0)
	{
	try{document.all(PolGrid.findSpanID(0)).all('PolGridSel').checked =true;} catch(ex) {}
	try{fm.SelPolNo.value=PolGrid.getRowColData(0,1);} catch(ex) {}
	getLCBnfInfo();
	}		
}
//获得受益人信息
function getLCBnfInfo()
{
	var tPolNo=fm.SelPolNo.value;
	var tContNo=fm.ContNo.value;
	
		var sqlid25="ContCheckInputSql25";
		var mySql25=new SqlClass();
		mySql25.setResourceName("app.ContCheckInputSql"); //指定使用的properties文件名
		mySql25.setSqlId(sqlid25);//指定使用的Sql的id
		mySql25.addSubPara(tContNo);//指定传入的参数
	    var lcbnfSQL=mySql25.getString();
	
//	var lcbnfSQL="select bnftype,name,idtype,idno,relationtoinsured,bnfgrade,bnflot"
//		+" ,(select name from ldperson where customerno=insuredno),'',insuredno"
//		+" from lcbnf where contno='"+tContNo+"'";
	turnPage.queryModal(lcbnfSQL,LCBnfGrid);
}

//控制界面按钮的明暗显示
function ctrlButtonDisabled(tContNo,tLoadFlag)
{
	var tSQL = "";
	var arrResult;
	var arrButtonAndSQL = new Array;
	
	if(tLoadFlag==5)
	{
		
		var sqlid26="ContCheckInputSql26";
		var mySql26=new SqlClass();
		mySql26.setResourceName("app.ContCheckInputSql"); //指定使用的properties文件名
		mySql26.setSqlId(sqlid26);//指定使用的Sql的id
		mySql26.addSubPara(tContNo);//指定传入的参数
	    var lcbnfSQL=mySql26.getString();
		
	arrButtonAndSQL[0] = new Array;
	arrButtonAndSQL[0][0] = "ApproveQuestQuery";
	arrButtonAndSQL[0][1] = "问题件查询";
	arrButtonAndSQL[0][2] = lcbnfSQL;//"select * from lcissuepol where 2 = 2 and OperatePos in ('0', '1', '5', 'A', 'I') and ContNo = '"+tContNo+"'";
	}
if(tLoadFlag!='a')
{
	for(var i=0; i<arrButtonAndSQL.length; i++)
	{
		if(arrButtonAndSQL[i][2]!=null&&arrButtonAndSQL[i][2]!="")
		{
			arrResult = easyExecSql(arrButtonAndSQL[i][2]);
			if(arrResult!=null)
			{
				eval("document.all('"+arrButtonAndSQL[i][0]+"').disabled=''");	
			}
			else
			{
				eval("document.all('"+arrButtonAndSQL[i][0]+"').disabled='true'");	
			}
		}
		else
		{
			continue;
		}	
	}
}
  	
}

//查询记事本
function checkNotePad(prtNo,LoadFlag)
{
	
	 if (typeof(LoadFlag)=="undefined")
	 {
	 	LoadFlag='a';
	 	}
	 //	alert(LoadFlag);
	 
	 	var sqlid27="ContCheckInputSql27";
		var mySql27=new SqlClass();
		mySql27.setResourceName("app.ContCheckInputSql"); //指定使用的properties文件名
		mySql27.setSqlId(sqlid27);//指定使用的Sql的id
		mySql27.addSubPara(prtNo);//指定传入的参数
	    var strSQL=mySql27.getString();
	 
	//var strSQL="select count(*) from LWNotePad where otherno='"+prtNo+"'";
	var arrResult = easyExecSql(strSQL);
	eval("document.all('NotePadButton"+LoadFlag+"').value=' 记事本查看(共"+arrResult[0][0]+"条)'");
}
//在初始化body时自动效验投保人信息，
//判断系统中是否存在姓名、性别、出生日期相同或证件类型证件号码相同的被保人信息。
function AppntChkNew()
{
}

// 在初始化body时自动效验被保人信息
function InsuredChkNew()
{	
	var tSel = InsuredGrid.getSelNo();
	if(tSel==null || tSel==0 ){ return ;}
	var tRow = InsuredGrid.getSelNo() - 1;
	var tInsuredNo=InsuredGrid.getRowColData(tRow,1);
	var tInsuredName=InsuredGrid.getRowColData(tRow,2);
	var tInsuredSex=InsuredGrid.getRowColData(tRow,3);
	var tBirthday=InsuredGrid.getRowColData(tRow,4);
	
	    var sqlid29="ContCheckInputSql29";
		var mySql29=new SqlClass();
		mySql29.setResourceName("app.ContCheckInputSql"); //指定使用的properties文件名
		mySql29.setSqlId(sqlid29);//指定使用的Sql的id
		mySql29.addSubPara(tInsuredName);//指定传入的参数
		mySql29.addSubPara(tInsuredSex);//指定传入的参数
		mySql29.addSubPara(tBirthday);//指定传入的参数
		
		mySql29.addSubPara(tInsuredNo);//指定传入的参数
		mySql29.addSubPara(fm.IDNo.value);//指定传入的参数
		mySql29.addSubPara(tInsuredNo);//指定传入的参数
	    var sqlstr=mySql29.getString();
	
//	var sqlstr="select * from ldperson where Name='"+tInsuredName
//	        +"' and Sex='"+tInsuredSex+"' and Birthday='"+tBirthday
//	        +"' and CustomerNo<>'"+tInsuredNo+"'"
//	        +" union select * from ldperson where 1=1 "
//	        +" and IDNo = '"+fm.IDNo.value
//	        +"' and IDNo is not null and CustomerNo<>'"+tInsuredNo+"'" ;
	arrResult = easyExecSql(sqlstr,1,0);
	if(arrResult==null)
	{
	fm.InsuredChkButton.disabled = true;
	}
	else
	{
	fm.InsuredChkButton.disabled = "";	
	}
}

//清除被保人录入区域，重新赋值
function emptyInsured()
{
	try{document.all('InsuredNo').value= ""; }catch(ex){};
	try{document.all('VIPValue1').value= ""; }catch(ex){};
	try{document.all('AppntVIPFlagname1').value= ""; }catch(ex){};
	try{document.all('SequenceNo').value= ""; }catch(ex){};
	try{document.all('ExecuteCom').value= ""; }catch(ex){};
	try{document.all('FamilyID').value= ""; }catch(ex){};
	try{document.all('RelationToMainInsured').value= ""; }catch(ex){};
	try{document.all('RelationToAppnt').value= ""; }catch(ex){};
	try{document.all('RelationToAppntName').value= ""; }catch(ex){};
	try{document.all('InsuredAddressNo').value= ""; }catch(ex){};
	//try{document.all('SequenceNo').value= ""; }catch(ex){};
	try{document.all('Name').value= ""; }catch(ex){};
	try{document.all('Sex').value= ""; }catch(ex){};
	try{document.all('SexName').value= ""; }catch(ex){};
	try{document.all('RelationToMainInsuredName').value= ""; }catch(ex){};
	try{document.all('SequenceNoName').value= ""; }catch(ex){};
	try{document.all('Birthday').value= ""; }catch(ex){};
	try{document.all('InsuredAppAge').value= ""; }catch(ex){};
	try{document.all('IDType').value= ""; }catch(ex){};
	try{document.all('IDNo').value= ""; }catch(ex){};
	try{document.all('IncomeWayName').value= ""; }catch(ex){};
	try{document.all('InsuredProvince').value= ""; }catch(ex){};
	try{document.all('InsuredProvinceName').value= ""; }catch(ex){};
	try{document.all('InsuredCity').value= ""; }catch(ex){};
	try{document.all('InsuredCityName').value= ""; }catch(ex){};
	try{document.all('InsuredDistrict').value= ""; }catch(ex){};
	try{document.all('InsuredDistrictName').value= ""; }catch(ex){};
	try{document.all('Income').value= ""; }catch(ex){};
	try{document.all('LicenseType').value= ""; }catch(ex){};
	try{document.all('LicenseTypeName').value= ""; }catch(ex){};
	try{document.all('IDTypeName').value= ""; }catch(ex){};
	try{document.all('InsuredAddressNoName').value= ""; }catch(ex){};
	try{document.all('IncomeWay').value= ""; }catch(ex){};
	try{document.all('NativePlace').value= ""; }catch(ex){};
	try{document.all('NativePlaceName').value= ""; }catch(ex){};
	try{document.all('Nationality').value= ""; }catch(ex){};
	try{document.all('RgtAddress').value= ""; }catch(ex){};
	try{document.all('Marriage').value= ""; }catch(ex){};
	try{document.all('MarriageName').value= ""; }catch(ex){};
	try{document.all('MarriageDate').value= ""; }catch(ex){};
	try{document.all('Health').value= ""; }catch(ex){};
	try{document.all('Stature').value= ""; }catch(ex){};
	try{document.all('Avoirdupois').value= ""; }catch(ex){};
	try{document.all('Degree').value= ""; }catch(ex){};
	try{document.all('CreditGrade').value= ""; }catch(ex){};
	try{document.all('BankCode').value= ""; }catch(ex){};
	try{document.all('BankAccNo').value= ""; }catch(ex){};
	try{document.all('AccName').value= ""; }catch(ex){};
	try{document.all('JoinCompanyDate').value= ""; }catch(ex){};
	try{document.all('StartWorkDate').value= ""; }catch(ex){};
	try{document.all('Position').value= ""; }catch(ex){};
	try{document.all('Salary').value= ""; }catch(ex){};
	try{document.all('OccupationType').value= ""; }catch(ex){};
	try{document.all('OccupationTypeName').value= ""; }catch(ex){};
	try{document.all('OccupationCodeName').value= ""; }catch(ex){};
	try{document.all('OccupationCode').value= ""; }catch(ex){};
	try{document.all('WorkType').value= ""; }catch(ex){};
	try{document.all('PluralityType').value= ""; }catch(ex){};
	try{document.all('InsuredPlace').value= ""; }catch(ex){};
	try{document.all('SmokeFlag').value= ""; }catch(ex){};
	try{document.all('ContPlanCode').value= ""; }catch(ex){};
	try{document.all('GrpName').value= ""; }catch(ex){};
	try{document.all('HomeAddress').value= ""; }catch(ex){};
	try{document.all('HomeZipCode').value= ""; }catch(ex){};
	try{document.all('HomePhone').value= ""; }catch(ex){};
	try{document.all('HomeFax').value= ""; }catch(ex){};
	try{document.all('GrpFax').value= ""; }catch(ex){};
	try{document.all('Fax').value= ""; }catch(ex){};
	try{document.all('PostalAddress').value= "";  }catch(ex){};
	try{document.all('ZipCode').value= "";  }catch(ex){};
	try{document.all('Phone').value= "";  }catch(ex){};
	try{document.all('Mobile').value= "";  }catch(ex){};
	try{document.all('EMail').value= "";  }catch(ex){};
	try{document.all('Phone').value= "";  }catch(ex){};
	try{document.all('GrpAddress').value= ""; }catch(ex){};
	try{document.all('GrpZipCode').value= "";  }catch(ex){};
	ImpartInsuredGrid.clearData();
	PolGrid.clearData();
	LCBnfGrid.clearData();
}

function getInputFieldName()
{
	//合同信息部分
	showOneCodeName('sellType','SellType','sellTypeName');
	showOneCodeName('salechnl','SaleChnl','SaleChnlName');
	showOneCodeName('comcode','ManageCom','ManageComName');
	showOneCodeName('highAmntFlag','highAmntFlag','highAmntFlagName');
	showOneCodeName('comcode','AgentManageCom','AgentManageComName');
	showOneCodeName('agentrelatoappnt','RelationShip','RelationShipName');
	//投保人信息部分
	showOneCodeName('vipvalue','AppntVIPValue','AppntVIPValueName');
	showOneCodeName('IDType','AppntIDType','AppntIDTypeName');
	showOneCodeName('Sex','AppntSex','AppntSexName');
	//getAppntAge();
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
	//投保人告知
	//showOneCodename('incomeway','IncomeWay0','IncomeWayName0');
	//缴费信息部分
	showOneCodeName('paylocation','PayMode','PayModeName');
	showOneCodeName('bank','AppntBankCode','AppntBankCodeName');
	showOneCodeName('paylocation','SecPayMode','SecPayModeName');
	showOneCodeName('bank','SecAppntBankCode','SecAppntBankCodeName');
	//被保人信息部分
	showOneCodeName('SequenceNo','SequenceNo','SequenceNoName');
	showOneCodeName('Relation','RelationToMainInsured','RelationToMainInsuredName');
	showOneCodeName('Relation','RelationToAppnt','RelationToAppntName');
	showOneCodeName('vipvalue','VIPValue1','VIPValue1Name');
	showOneCodeName('IDType','IDType','IDTypeName');
	showOneCodeName('Sex','Sex','SexName');
	//getAppAge();
	showOneCodeName('Marriage','Marriage','MarriageName');
	showOneCodeName('NativePlace','NativePlace','NativePlaceName');
	showOneCodeName('LicenseType','LicenseType','LicenseTypeName');
	showOneCodeName('OccupationCode','OccupationCode','OccupationCodeName');
	showOneCodeName('OccupationType','OccupationType','OccupationTypeName');
	//showOneCodeName('province','InsuredProvince','InsuredProvinceName');
	//showOneCodeName('city','InsuredCity','InsuredCityName');
	//showOneCodeName('district','InsuredDistrict','InsuredDistrictName');
	getAddressName('province','InsuredProvince','InsuredProvinceName');
	getAddressName('city','InsuredCity','InsuredCityName');
	getAddressName('district','InsuredDistrict','InsuredDistrictName'); 
	//被保人告知
	//showOneCodename('incomeway','IncomeWay','IncomeWayName');
}

//校验投保日期
function checkapplydate()
{	
}

//校验投保人出生日期
function checkappntbirthday()
{
	if(fm.AppntBirthday.value.length==8)
	{
		if(fm.AppntBirthday.value.indexOf('-')==-1)
		{
			var Year =     fm.AppntBirthday.value.substring(0,4);
			var Month =    fm.AppntBirthday.value.substring(4,6);
			var Day =      fm.AppntBirthday.value.substring(6,8);
			fm.AppntBirthday.value = Year+"-"+Month+"-"+Day;
			if(Year=="0000"||Month=="00"||Day=="00")
			{
			alert("您输入的投保人出生日期有误!");
			fm.AppntBirthday.value = "";
			return;
			}
		}
	}
	else 
	{
		var Year =     fm.AppntBirthday.value.substring(0,4);
		var Month =    fm.AppntBirthday.value.substring(5,7);
		var Day =      fm.AppntBirthday.value.substring(8,10);
		if(Year=="0000"||Month=="00"||Day=="00")
		{
		alert("您输入的投保人出生日期有误!");
		fm.AppntBirthday.value = "";
		return;
		}
	}
}


//校验被保人出生日期
function checkinsuredbirthday()
{
	if(fm.Birthday.value.length==8)
	{
		if(fm.Birthday.value.indexOf('-')==-1)
		{
			var Year =     fm.Birthday.value.substring(0,4);
			var Month =    fm.Birthday.value.substring(4,6);
			var Day =      fm.Birthday.value.substring(6,8);
			fm.Birthday.value = Year+"-"+Month+"-"+Day;
			if(Year=="0000"||Month=="00"||Day=="00")
			{
			alert("您输入的被保人出生日期有误!");
			fm.Birthday.value = "";
			return;
			}
		}
	}
	else 
	{
		var Year =     fm.Birthday.value.substring(0,4);
		var Month =    fm.Birthday.value.substring(5,7);
		var Day =      fm.Birthday.value.substring(8,10);
		if(Year=="0000"||Month=="00"||Day=="00")
		{
		alert("您输入的被保人出生日期有误!");
		fm.Birthday.value = "";
		return;
		}
	}
}
//投保人年龄<投保人被保人年龄应该为投保日期与生日之差;2005-11-18修改>
function getAge()
{
	if(fm.AppntBirthday.value=="")
	{
		return;
	}
	if(fm.AppntBirthday.value.indexOf('-')==-1)
	{
		var Year =fm.AppntBirthday.value.substring(0,4);
		var Month =fm.AppntBirthday.value.substring(4,6);
		var Day =fm.AppntBirthday.value.substring(6,8);
		fm.AppntBirthday.value = Year+"-"+Month+"-"+Day;
	}
	if(calAge(fm.AppntBirthday.value)<0)
	{
		alert("出生日期只能为当前日期以前");
		fm.AppntAge.value="";
		return;
	}
//	fm.AppntAge.value=calAge(fm.AppntBirthday.value);
	fm.AppntAge.value=calPolAppntAge(fm.AppntBirthday.value,fm.PolAppntDate.value);
  	return ;
}
//被保人年龄龄<被保人年龄应该为投保日期与生日之差;2005-11-18修改>
function getAge2()
{	
	if(fm.Birthday.value=="")
	{
		return;
	}
	if(fm.Birthday.value.indexOf('-')==-1)
	{
		var Year =     fm.Birthday.value.substring(0,4);
		var Month =    fm.Birthday.value.substring(4,6);
		var Day =      fm.Birthday.value.substring(6,8);
		fm.Birthday.value = Year+"-"+Month+"-"+Day;
  	}
  	else
  	{
    	var Year1 =     fm.Birthday.value.substring(0,4);
	    var Month1 =    fm.Birthday.value.substring(5,7);
	    var Day1 =      fm.Birthday.value.substring(8,10);	
	    fm.Birthday.value = Year1+"-"+Month1+"-"+Day1;	
	}
	if(calAge(fm.Birthday.value)<0)
  	{
		alert("出生日期只能为当前日期以前");
		fm.InsuredAppAge.value="";
		return;
    }
    fm.InsuredAppAge.value=calPolAppntAge(fm.Birthday.value,fm.PolAppntDate.value);
  	return ;
}  
//投保人证件
function checkidtype()
{
	if(fm.AppntIDType.value==""&&fm.AppntIDNo.value!="")
	{
		alert("请先选择证件类型！");
		fm.AppntIDNo.value="";
	}
}
//被保人证件
function checkidtype2()
{
	if(fm.IDType.value=="")
	{
		alert("请先选择证件类型！");
    }
}

/****************投保人  根据身份证号取得出生日期和性别******************/
function getBirthdaySexByIDNo(iIdNo)
{
	if(document.all('AppntIDType').value=="0")
	{
		var tBirthday=getBirthdatByIdNo(iIdNo);
		var tSex=getSexByIDNo(iIdNo);
		if(tBirthday=="输入的身份证号位数错误"||tSex=="输入的身份证号位数错误")
		{
			alert("输入的身份证号位数错误");
			theFirstValue="";
			theSecondValue="";
			//document.all('AppntIDNo').focus();
			return;
		}
		else
		{
			alert("性别输入与身份证件信息不符，已根据身份证件信息自动修改性别！")
			document.all('AppntBirthday').value=tBirthday;
			document.all('AppntSex').value=tSex;
			if("0"==tSex)
			document.all('AppntSexName').value="男";
			if("1"==tSex)
			document.all('AppntSexName').value="女";
		}
	}
}

/****************被保人  根据身份证号取得出生日期和性别******************/
function getBirthdaySexByIDNo2(iIdNo)
{
	if(document.all('IDType').value=="0")
	{
		var tBirthday=getBirthdatByIdNo(iIdNo);
		var tSex=getSexByIDNo(iIdNo);
		if(tBirthday=="输入的身份证号位数错误"||tSex=="输入的身份证号位数错误")
		{
			alert("输入的身份证号位数错误");
			theFirstValue="";
			theSecondValue="";
			//document.all('IDNo').focus();
			return;

		}
		else
		{
			alert("性别输入与身份证件信息不符，已根据身份证件信息自动修改性别！")
			document.all('Birthday').value=tBirthday;
			document.all('Sex').value=tSex;
			if("0"==tSex)
			document.all('SexName').value="男";
			if("1"==tSex)
			document.all('SexName').value="女";
		}

	}
}

function getdetailwork()
{
	
		 var sqlid30="ContCheckInputSql30";
		var mySql30=new SqlClass();
		mySql30.setResourceName("app.ContCheckInputSql"); //指定使用的properties文件名
		mySql30.setSqlId(sqlid30);//指定使用的Sql的id
		mySql30.addSubPara(fm.AppntOccupationCode.value);//指定传入的参数
	    var strSql=mySql30.getString();
	
//	var strSql = "select OccupationType from LDOccupation where OccupationCode='" + fm.AppntOccupationCode.value+"'";
	var arrResult = easyExecSql(strSql);
	if (arrResult != null)
	{
		fm.AppntOccupationType.value = arrResult[0][0];
		fm.AppntOccupationTypeName.value ="";
		showOneCodeName('occupationtype','AppntOccupationType','AppntOccupationTypeName');
	}
	else
	{
		fm.AppntOccupationType.value ="";
		fm.AppntOccupationTypeName.value ="";
	}
}

function getaddresscodedata()
{
    var i = 0;
    var j = 0;
    var m = 0;
    var n = 0;
    var strsql = "";
    var tCodeData = "0|";
    if(fm.AppntAddressNo.value=="")
    {
    	fm.AppntPostalAddress.value="";
    	fm.AddressNoName.value="";
    	fm.AppntProvinceName.value="";
    	fm.AppntProvince.value="";
    	fm.AppntCityName.value="";
    	fm.AppntCity.value="";
    	fm.AppntDistrictName.value="";
    	fm.AppntDistrict.value="";
    	fm.AppntZipCode.value="";
    	fm.AppntFax.value="";
    	fm.AppntEMail.value="";
    	fm.AppntDistrict.value="";
    //	fm.AppntGrpName.value="";
    	fm.AppntHomePhone.value="";
    	fm.AppntMobile.value="";
    	fm.AppntPhone.value="";
    	}
		
		 var sqlid31="ContCheckInputSql31";
		var mySql31=new SqlClass();
		mySql31.setResourceName("app.ContCheckInputSql"); //指定使用的properties文件名
		mySql31.setSqlId(sqlid31);//指定使用的Sql的id
		mySql31.addSubPara(fm.AppntNo.value);//指定传入的参数
	    strsql=mySql31.getString();
		
//    strsql = "select AddressNo,PostalAddress from LCAddress where CustomerNo ='"+fm.AppntNo.value+"'";
    //alert("strsql :" + strsql);
    turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);
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
    //alert ("tcodedata : " + tCodeData);
     document.all("AppntAddressNo").CodeData=tCodeData;
}
function getaddresscodedata2()
{
    var i = 0;
    var j = 0;
    var m = 0;
    var n = 0;
    var strsql = "";
    var tCodeData = "0|";
    if(fm.InsuredAddressNo.value=="")
    {
    	fm.PostalAddress.value="";
    	fm.InsuredAddressNoName.value="";
    	fm.InsuredProvinceName.value="";
    	fm.InsuredProvince.value="";
     	fm.InsuredCityName.value="";
    	fm.InsuredCity.value="";
    	fm.InsuredDistrictName.value="";
    	fm.InsuredDistrict.value="";
    	fm.PostalAddress.value="";
    	fm.ZIPCODE.value="";
      fm.Mobile.value="";
     	fm.Phone.value="";
      fm.Fax.value="";
    	fm.HomePhone.value="";
    	fm.EMail.value="";
    	}
		
		var sqlid32="ContCheckInputSql32";
		var mySql32=new SqlClass();
		mySql32.setResourceName("app.ContCheckInputSql"); //指定使用的properties文件名
		mySql32.setSqlId(sqlid32);//指定使用的Sql的id
		mySql32.addSubPara(fm.InsuredNo.value);//指定传入的参数
	    strsql=mySql32.getString();
		
//    strsql = "select AddressNo,PostalAddress from LCAddress where CustomerNo ='"+fm.InsuredNo.value+"'";
    //alert("strsql :" + strsql);
    turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);
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
    document.all("InsuredAddressNo").CodeData=tCodeData;
}

//问题件录入
function QuestInput()
{
	//alert(LoadFlag);
    cContNo = fm.ContNo.value;  //保单号码
    if(LoadFlag=="2"||LoadFlag=="4"||LoadFlag=="13")
    {
        if(mSwitch.getVar( "ProposalGrpContNo" )=="")
        {
            alert("尚无集体合同投保单号，请先保存!");
        }
		else
		{
            window.open("./GrpQuestInputMain.jsp?GrpContNo="+mSwitch.getVar( "ProposalGrpContNo" )+"&Flag="+LoadFlag,"",sFeatures);
        }
    }
    else
    {
        if(cContNo == "")
        {
            alert("尚无合同投保单号，请先保存!");
        }
        else
        {
            window.open("../uw/MSQuestInputMain.jsp?ContNo="+cContNo+"&Flag="+ LoadFlag+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+ActivityID,"window1",sFeatures);
        }
    }
}
//问题件查询
function QuestQuery()
{
	cContNo = document.all("ContNo").value;  //保单号码
	if(LoadFlag=="2"||LoadFlag=="4"||LoadFlag=="13")
	{
		if(mSwitch.getVar( "ProposalGrpContNo" )==""||mSwitch.getVar( "ProposalGrpContNo" )==null)
		{
		alert("请先选择一个团体主险投保单!");
		return ;
		}
		else
		{
		window.open("./GrpQuestQueryMain.jsp?GrpContNo="+mSwitch.getVar( "ProposalGrpContNo" )+"&Flag="+LoadFlag,"",sFeatures);
		}
	}
	else
	{
		if(cContNo == "")
		{
		alert("尚无合同投保单号，请先保存!");
		}
		else
		{
		window.open("../uw/QuestQueryMain.jsp?ContNo="+cContNo+"&Flag="+LoadFlag,"window1",sFeatures);
		}
	}
}

//问题件影像查询
function QuestPicQuery()
{
	var ContNo = fm.ContNo.value;
	if (ContNo == "")
	{
	return;
	}
	else
	{
	window.open("../uw/ImageQueryMain.jsp?ContNo="+ContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);
	}
}
//记事本查看
function showNotePad()
{
	var ActivityID = document.all.ActivityID.value;
	var PrtNo = document.all.PrtNo.value;
	var NoType = document.all.NoType.value;
	if(PrtNo == null || PrtNo == "")
	{
		alert("投保单号为空！");
		return;
	}
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ PrtNo + "&NoType="+ NoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp" + varSrc,"工作流记事本查看","left");

}

//投保人检验
function AppntChk()
{

	var Sex=fm.AppntSex.value;
	//var i=Sex.indexOf("-");
	//Sex=Sex.substring(0,i);
	
		var sqlid33="ContCheckInputSql33";
		var mySql33=new SqlClass();
		mySql33.setResourceName("app.ContCheckInputSql"); //指定使用的properties文件名
		mySql33.setSqlId(sqlid33);//指定使用的Sql的id
		mySql33.addSubPara(fm.AppntName.value);//指定传入的参数
		mySql33.addSubPara(Sex);//指定传入的参数
		mySql33.addSubPara(fm.AppntBirthday.value);//指定传入的参数

		mySql33.addSubPara(fm.AppntNo.value);//指定传入的参数
		mySql33.addSubPara(fm.AppntIDNo.value);//指定传入的参数
		mySql33.addSubPara(fm.AppntNo.value);//指定传入的参数
	
	  var  strsql=mySql33.getString();
	
//	var sqlstr="select * from ldperson where Name='"+fm.AppntName.value
//	+ "' and Sex='"+Sex+"' and Birthday='"+fm.AppntBirthday.value
//	+ "' and CustomerNo<>'"+fm.AppntNo.value+"'"
//	+ " union select * from ldperson where 1=1 " 
//	+ " and IDNo = '"+fm.AppntIDNo.value
//	+ "' and IDNo is not null and CustomerNo<>'"+fm.AppntNo.value+"'" ;
	arrResult = easyExecSql(strsql);
	//alert('arrResult:'+arrResult);
	
	if(document.all('AppntSex').value == "1" && document.all('AppntSexName').value != '女')
	{
		
		alert("录入性别与身份证性别码不符。");
		document.all('AppntSexName').value='女';
		return false;
	}
	if(document.all('AppntSex').value == "0" && document.all('AppntSexName').value != '男')
	{
		
		alert("录入性别与身份证性别码不符。");
		document.all('AppntSexName').value='男';
		return false;
	}

	
	if(arrResult==null)
	{
		alert("该没有与该投保人相似的客户,无需校验");
		return false;
	}
	else
	{
		window.open("../uw/AppntChkMain.jsp?ProposalNo1="+fm.ContNo.value+"&Flag=A&LoadFlag="+LoadFlag+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+ActivityID,"window1",sFeatures);
	}
}
//被保人检验
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
	
		var sqlid34="ContCheckInputSql34";
		var mySql34=new SqlClass();
		mySql34.setResourceName("app.ContCheckInputSql"); //指定使用的properties文件名
		mySql34.setSqlId(sqlid34);//指定使用的Sql的id
		mySql34.addSubPara(tInsuredName);//指定传入的参数
		mySql34.addSubPara(tInsuredSex);//指定传入的参数
		mySql34.addSubPara(tBirthday);//指定传入的参数
		
		mySql34.addSubPara(tInsuredNo);//指定传入的参数
		mySql34.addSubPara(fm.IDNo.value);//指定传入的参数
		mySql34.addSubPara(tInsuredNo);//指定传入的参数
	    var sqlstr=mySql34.getString();
	
//  var sqlstr="select * from ldperson where Name='"+tInsuredName
//            +"' and Sex='"+tInsuredSex+"' and Birthday='"+tBirthday
//            +"' and CustomerNo<>'"+tInsuredNo+"'"
//            +" union select * from ldperson where 1=1 "
//            +" and IDNo = '"+fm.IDNo.value
//            +"' and IDNo is not null and CustomerNo<>'"+tInsuredNo+"'" ;
        arrResult = easyExecSql(sqlstr,1,0);
        if(document.all('AppntSex').value == "1" && document.all('AppntSexName').value != '女')
    	{
    		alert("录入性别与身份证性别码不符。");
    		document.all('AppntSexName').value='女';
    		return false;
    	}
    	if(document.all('AppntSex').value == "0" && document.all('AppntSexName').value != '男')
    	{
    		alert("录入性别与身份证性别码不符。");
    		document.all('AppntSexName').value='男';
    		return false;
    	}

        if(arrResult==null)
        {
	   alert("该没有与该被保人保人相似的客户,无需校验");
	   return false;
        }

    window.open("../uw/AppntChkMain.jsp?ProposalNo1="+fm.ContNo.value+"&Flag=I&LoadFlag="+LoadFlag+"&InsuredNo="+tInsuredNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+ActivityID,"window1",sFeatures);
}

//强制进入人工核保
function forceUW()
{
	//查询是否已经进行选择强制进入人工核保
  var ContNo=document.all("ContNo").value;
  
  		var sqlid35="ContCheckInputSql35";
		var mySql35=new SqlClass();
		mySql35.setResourceName("app.ContCheckInputSql"); //指定使用的properties文件名
		mySql35.setSqlId(sqlid35);//指定使用的Sql的id
		mySql35.addSubPara(ContNo);//指定传入的参数
	    var sqlstr=mySql35.getString();
  
//  var sqlstr="select forceuwflag from lccont where contno='"+ContNo+"'" ;
  arrResult = easyExecSql(sqlstr,1,0);
  if(arrResult==null){
  	alert("不存在该投保单！");
  }
  else
  {
    window.open("../uw/ForceUWMain.jsp?ContNo="+ContNo,"window1",sFeatures);
  }
}

/**************PolGrid的RadioBox点击事件，获得被保人险种详细信息*************/
function getPolDetail(parm1,parm2)
{
    var selectRowNum = parm1.replace(/spanPolGrid/g,"");
    var PolNo = document.all('PolGrid1'+'r'+selectRowNum).value;
	//var PolNo=document.all(parm1).all('PolGrid1').value
    try{mSwitch.deleteVar('PolNo')}catch(ex){};
    try{mSwitch.addVar('PolNo','',PolNo);}catch(ex){};
    fm.SelPolNo.value=PolNo;
}

/************************  进入险种信息录入***********************/
function intoRiskInfo()
{
	//把合同信息和被保人信息放入内存
	mSwitch = parent.VD.gVSwitch;  //桢容错
	delContVar();//把合同信息从变量中删除
	addIntoCont();//把合同信息放入加到变量中
	delInsuredVar();//删除缓存中被保险人信息
	addInsuredVar();//将被保险人信息加入到缓存中
	if(fm.InsuredNo.value==""||fm.ContNo.value==""||InsuredGrid.mulLineCount=="0")
	{
		alert("请先添加，选择被保人");
		return false;
	}
	//alert(InsuredGrid.mulLineCount);
	var tRow = InsuredGrid.getSelNo() - 1;
	var tInsuredNo=InsuredGrid.getRowColData(tRow,1);
	
	  	var sqlid36="ContCheckInputSql36";
		var mySql36=new SqlClass();
		mySql36.setResourceName("app.ContCheckInputSql"); //指定使用的properties文件名
		mySql36.setSqlId(sqlid36);//指定使用的Sql的id
		mySql36.addSubPara(fm.ProposalContNo.value);//指定传入的参数
		mySql36.addSubPara(tInsuredNo);//指定传入的参数
	    var sqlstr=mySql36.getString();
		
		 var sqlid37="ContCheckInputSql37";
		var mySql37=new SqlClass();
		mySql37.setResourceName("app.ContCheckInputSql"); //指定使用的properties文件名
		mySql37.setSqlId(sqlid37);//指定使用的Sql的id
		mySql37.addSubPara(fm.ProposalContNo.value);//指定传入的参数
	    var tSql=mySql37.getString();
	
//	var sqlstr="select sequenceno From lcinsured where (prtno,contno)= (select prtno,contno from lccont where proposalcontno='"+fm.ProposalContNo.value+"') and insuredno='"+tInsuredNo+"'" ;
//	var tSql = "select * from lcpol where contno = '"+fm.ProposalContNo.value+"' and riskcode = '00150000'";//如果险种为150,要特殊处理。
	turnPage.strQueryResult = easyQueryVer3(tSql, 1, 0, 1);      
	var sequence = easyExecSql(sqlstr,1,0);
	if(sequence!="1" && InsuredGrid.mulLineCount>1 && !turnPage.strQueryResult)
	{
		alert("请正确选择第一被保人!");
		return;
	}
	
  	try{mSwitch.addVar('SelPonNo','',fm.SelPolNo.value);}catch(ex){} //选择险种单进入险种界面带出已保存的信息
	if ((LoadFlag=='5'||LoadFlag=='4'||LoadFlag=='6'||LoadFlag=='16')&&(mSwitch.getVar( "PolNo" ) == null || mSwitch.getVar( "PolNo" ) == ""))
	{
		alert("请先选择被保险人险种信息！");
		return;
	}
	try{mSwitch.deleteVar('ContNo');}catch(ex){}
	try{mSwitch.addVar('ContNo','',fm.ContNo.value);}catch(ex){}
	try{mSwitch.updateVar('ContNo','',fm.ContNo.value);}catch(ex){}
	try{mSwitch.deleteVar('mainRiskPolNo');}catch(ex){}
	try{mSwitch.deleteVar('CValiDate');}catch(ex){}
	try{mSwitch.addVar('CValiDate','',document.all('PolAppntDate').value);}catch(ex){}

	parent.fraInterface.window.location = "./ProposalInput.jsp?LoadFlag=" + LoadFlag+"&ContType="+ContType+"&scantype=" + scantype+ "&MissionID=" + MissionID+ "&SubMissionID=" + SubMissionID+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&ActivityID="+ActivityID+"&NoType="+NoType+"&hh=1&checktype="+checktype+"&ProposalContNo="+fm.ProposalContNo.value+"&ScanFlag="+ScanFlag+"&InsuredChkFlag="+document.all('InsuredChkButton').disabled+"&AppntChkFlag="+document.all('AppntChkButton').disabled;
}

/*********************************************************************
 *  把合同所有信息录入结束确认
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function inputConfirm(wFlag)
{ 
	if (wFlag ==1 ) //录入完毕确认
	{
		
		 var sqlid38="ContCheckInputSql38";
		var mySql38=new SqlClass();
		mySql38.setResourceName("app.ContCheckInputSql"); //指定使用的properties文件名
		mySql38.setSqlId(sqlid38);//指定使用的Sql的id
		mySql38.addSubPara(fm.ContNo.value);//指定传入的参数
	    var tStr=mySql38.getString();
		
//		var tStr= "	select * from lwmission where 1=1 and lwmission.activityid in ('0000001001','0000001002','0000001220','0000001230') and lwmission.missionprop1 = '"+fm.ContNo.value+"'";
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
			fm.WorkFlowFlag.value = ActivityID;
		}
		else
		{
			fm.WorkFlowFlag.value = ActivityID;
		}
		fm.MissionID.value = tMissionID;
		fm.SubMissionID.value = tSubMissionID;			//录入完毕
	}
	else if (wFlag ==2)//复核完毕确认
	{
		//由于已经增加了客户关联岗所以注释掉下面的校验2009-01-12
		//if(fm.AppntChkButton.disabled=="")
		//{ 
		//	var strSql = "select * from LCIssuePol where contno = '"+fm.ContNo.value+"' and issuetype = '99' and questionobj = '"+document.all('AppntNo').value+"'";
		//	var brrResult = easyExecSql(strSql);
		//	if(brrResult==null)	
		//	{
		//		if(confirm("是否进行投保人校验？"))
		//		return;
		//	}
		//}	
		//if(fm.InsuredChkButton.disabled=="")
		//{
		//	strSql = "select * from LCIssuePol where contno = '"+fm.ContNo.value+"' and issuetype = '99' and questionobj in (select insuredno from lcinsured where contno='"+fm.ContNo.value+"')";
		//	var crrResult = easyExecSql(strSql);
		//	if(crrResult==null)
		//	{
		//	if(confirm("是否进行被保人校验？"))
		//	return;
		//	}
		//}
		if(document.all('ProposalContNo').value == "")
		{
			alert("未查询出合同信息,不容许您进行 [复核完毕] 确认！");
			return;
		}
		fm.WorkFlowFlag.value = ActivityID;					//复核完毕
		fm.MissionID.value = tMissionID;
		fm.SubMissionID.value = tSubMissionID;
		approvefalg="2";
	}
	else if (wFlag ==3)
	{
		if(document.all('ProposalContNo').value == "")
		{
		alert("未查询出合同信息,不容许您进行 [复核修改完毕] 确认！");
		return;
		}
		fm.WorkFlowFlag.value = ActivityID;					//复核修改完毕
		fm.MissionID.value = tMissionID;
		fm.SubMissionID.value = tSubMissionID;
	}
	else if(wFlag == 4)
	{
		if(document.all('ProposalContNo').value == "")
		{
		alert("未查询出合同信息,不容许您进行 [修改完毕] 确认！");
		return;
		}
		fm.WorkFlowFlag.value = ActivityID;					//问题修改
		fm.MissionID.value = tMissionID;
		fm.SubMissionID.value = tSubMissionID;
	}
	else
		return;
	
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
	var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	fm.action = "./InputConfirm.jsp";
	document.getElementById("fm").submit(); //提交
}

/*********************************************************************
 *  保存个人投保单的提交后的操作,服务器数据返回后执行的操作
 ********************************************************************/
function afterSubmit( FlagStr, content,contract )
{
	showInfo.close();
	if( FlagStr == "Fail" )
	{
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
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
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
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


/********************************** *  把合同信息放入加到变量中 ************************/
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
    try{mSwitch.addVar('RnewFlag','',fm.RnewFlag.value);}catch(ex){};
    try{mSwitch.addVar('AutoPayFlag','',fm.AutoPayFlag.value);}catch(ex){};
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
	try { mSwitch.addVar('AppntPhone','',fm.AppntPhone.value); } catch(ex) { };
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
	try { mSwitch.addVar('AppntPlace','',fm.AppntPlace.value);}catch(ex){};
}

/******************************  把集体信息从变量中删除*****************************/
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
   try { mSwitch.deleteVar('RnewFlag'); } catch(ex) { };
   try { mSwitch.deleteVar('AutoPayFlag'); } catch(ex) { };
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
	try {mSwitch.deleteVar('AddressNo');}catch(ex){};
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
	try { mSwitch.deleteVar  ('AppntPlace'); } catch(ex) { };
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
	try { mSwitch.deleteVar  ('AppntPhone'); } catch(ex) { };
	try { mSwitch.deleteVar  ('CompanyAddress'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntGrpZipCode'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntGrpFax'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntBankAccNo'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntBankCode'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntAccName'); } catch(ex) { };
}

/************************* 删除缓存中被保险人信息******************************/
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
    try{mSwitch.deleteVar('InsuredPlace');}catch(ex){};
    try{mSwitch.deleteVar('PluralityType');}catch(ex){};
    try{mSwitch.deleteVar('SmokeFlag');}catch(ex){};
    try{mSwitch.deleteVar('ContPlanCode');}catch(ex){};
    try{mSwitch.deleteVar('Operator');}catch(ex){};
    try{mSwitch.deleteVar('MakeDate');}catch(ex){};
    try{mSwitch.deleteVar('MakeTime');}catch(ex){};
    try{mSwitch.deleteVar('ModifyDate');}catch(ex){};
    try{mSwitch.deleteVar('ModifyTime');}catch(ex){};
}

/*********************将被保险人信息加入到缓存中************************/
function addInsuredVar()
{
    try{mSwitch.addVar('ContNo','',fm.ContNo.value);}catch(ex){};
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
    try{mSwitch.addVar('Name','',fm.Name.value);}catch(ex){};
    try{mSwitch.addVar('Sex','',fm.Sex.value);}catch(ex){};
    try{mSwitch.addVar('Birthday','',fm.Birthday.value);}catch(ex){};
    try{mSwitch.addVar('IDType','',fm.IDType.value);}catch(ex){};
    try{mSwitch.addVar('IDNo','',fm.IDNo.value);}catch(ex){};
    try{mSwitch.addVar('RgtAddress','',fm.RgtAddress.value);}catch(ex){};
    try{mSwitch.addVar('Marriage','',fm.Marriage.value);}catch(ex){};
    try{mSwitch.addVar('MarriageDate','',fm.MarriageDate.value);}catch(ex){};
    try{mSwitch.addVar('Health','',fm.Health.value);}catch(ex){};
    try{mSwitch.addVar('Stature','',fm.Stature.value);}catch(ex){};
    try{mSwitch.addVar('Avoirdupois','',fm.Avoirdupois.value);}catch(ex){};
    try{mSwitch.addVar('Degree','',fm.Degree.value);}catch(ex){};
    try{mSwitch.addVar('CreditGrade','',fm.CreditGrade.value);}catch(ex){};
    try{mSwitch.addVar('BankCode','',fm.BankCode.value);}catch(ex){};
    try{mSwitch.addVar('BankAccNo','',fm.BankAccNo.value);}catch(ex){};
    try{mSwitch.addVar('AccName','',fm.AccName.value);}catch(ex){};
    try{mSwitch.addVar('JoinCompanyDate','',fm.JoinCompanyDate.value);}catch(ex){};
    try{mSwitch.addVar('StartWorkDate','',fm.StartWorkDate.value);}catch(ex){};
    try{mSwitch.addVar('Position','',fm.Position.value);}catch(ex){};
    try{mSwitch.addVar('Salary','',fm.Salary.value);}catch(ex){};
    try{mSwitch.addVar('OccupationType','',fm.OccupationType.value);}catch(ex){};
    try{mSwitch.addVar('OccupationCode','',fm.OccupationCode.value);}catch(ex){};
    try{mSwitch.addVar('WorkType','',fm.WorkType.value);}catch(ex){};
    try{mSwitch.addVar('InsuredPlace','',fm.InsuredPlace.value);}catch(ex){};
    try{mSwitch.addVar('PluralityType','',fm.PluralityType.value);}catch(ex){};
    try{mSwitch.addVar('SmokeFlag','',fm.SmokeFlag.value);}catch(ex){};
    try{mSwitch.addVar('ContPlanCode','',fm.ContPlanCode.value);}catch(ex){};
    try{mSwitch.addVar('Operator','',fm.Operator.value);}catch(ex){};
    try{mSwitch.addVar('MakeDate','',fm.MakeDate.value);}catch(ex){};
    try{mSwitch.addVar('MakeTime','',fm.MakeTime.value);}catch(ex){};
    try{mSwitch.addVar('ModifyDate','',fm.ModifyDate.value);}catch(ex){};
    try{mSwitch.addVar('ModifyTime','',fm.ModifyTime.value);}catch(ex){};
   
}

function afterCodeSelect( cCodeName, Field )
{
 	//alert("cCodeName==="+cCodeName);
	if(cCodeName=="GetAddressNo")
	{
		var strSQL="select b.* from LCAddress b where b.AddressNo='"+fm.AppntAddressNo.value+"' and b.CustomerNo='"+fm.AppntNo.value+"'";
		arrResult=easyExecSql(strSQL);
		try{document.all('AppntNo').value= arrResult[0][0];}catch(ex){};
		try{document.all('AppntAddressNo').value= arrResult[0][1];}catch(ex){};
		try{document.all('AppntPostalAddress').value= arrResult[0][2];}catch(ex){};
		try{document.all('AppntZipCode').value= arrResult[0][3];}catch(ex){};
		//try{document.all('AppntPhone').value= arrResult[0][4];}catch(ex){};
		try{document.all('AppntFax').value= arrResult[0][5];}catch(ex){};
		try{document.all('AppntHomeAddress').value= arrResult[0][6];}catch(ex){};
		try{document.all('AppntHomeZipCode').value= arrResult[0][7];}catch(ex){};
		try{document.all('AppntHomePhone').value= arrResult[0][8];}catch(ex){};
		try{document.all('AppntHomeFax').value= arrResult[0][9];}catch(ex){};
		//try{document.all('CompanyAddress').value= arrResult[0][10];}catch(ex){};
		try{document.all('AppntGrpZipCode').value= arrResult[0][11];}catch(ex){};
		try{document.all('AppntPhone').value= arrResult[0][4];}catch(ex){};
		try{document.all('AppntGrpFax').value= arrResult[0][13];}catch(ex){};
		try{document.all('AppntMobile').value= arrResult[0][14];}catch(ex){};
		try{document.all('AppntMobileChs').value= arrResult[0][15];}catch(ex){};
		try{document.all('AppntEMail').value= arrResult[0][16];}catch(ex){};
		try{document.all('AppntBP').value= arrResult[0][17];}catch(ex){};
		try{document.all('AppntMobile2').value= arrResult[0][18];}catch(ex){};
		try{document.all('AppntMobileChs2').value= arrResult[0][19];}catch(ex){};
		try{document.all('AppntEMail2').value= arrResult[0][20];}catch(ex){};
		try{document.all('AppntBP2').value= arrResult[0][21];}catch(ex){};
		try{document.all('AppntProvince').value= arrResult[0][28];}catch(ex){};
		try{document.all('AppntCity').value= arrResult[0][29];}catch(ex){};
		try{document.all('AppntDistrict').value= arrResult[0][30];}catch(ex){};
		//	showOneCodeName('province','AppntProvince','AppntProvinceName');
		//	showOneCodeName('city','AppntCity','AppntCityName');
		//	showOneCodeName('district','AppntDistrict','AppntDistrictName');
		getAddressName('province','AppntProvince','AppntProvinceName');
		getAddressName('city','AppntCity','AppntCityName');
		getAddressName('district','AppntDistrict','AppntDistrictName');	
		return;
	}
	if(cCodeName=="GetInsuredAddressNo")
	{
		var sqlid39="ContCheckInputSql39";
		var mySql39=new SqlClass();
		mySql39.setResourceName("app.ContCheckInputSql"); //指定使用的properties文件名
		mySql39.setSqlId(sqlid39);//指定使用的Sql的id
		mySql39.addSubPara(fm.InsuredAddressNo.value);//指定传入的参数
		mySql39.addSubPara(fm.InsuredNo.value);//指定传入的参数
	    var strSQL=mySql39.getString();
		
//		var strSQL="select b.* from LCAddress b where b.AddressNo='"+fm.InsuredAddressNo.value+"' and b.CustomerNo='"+fm.InsuredNo.value+"'";
		arrResult=easyExecSql(strSQL);
		try{document.all('InsuredAddressNo').value= arrResult[0][1];}catch(ex){};
		try{document.all('PostalAddress').value= arrResult[0][2];}catch(ex){};
		try{document.all('ZIPCODE').value= arrResult[0][3];}catch(ex){};
		//try{document.all('AppntPhone').value= arrResult[0][4];}catch(ex){};
		try{document.all('Fax').value= arrResult[0][5];}catch(ex){};
		//try{document.all('AppntHomeAddress').value= arrResult[0][6];}catch(ex){};
		//try{document.all('AppntHomeZipCode').value= arrResult[0][7];}catch(ex){};
		try{document.all('HomePhone').value= arrResult[0][8];}catch(ex){};
		//try{document.all('AppntHomeFax').value= arrResult[0][9];}catch(ex){};
		//try{document.all('CompanyAddress').value= arrResult[0][10];}catch(ex){};
		//try{document.all('AppntGrpZipCode').value= arrResult[0][11];}catch(ex){};
		try{document.all('Phone').value= arrResult[0][4];}catch(ex){};
		//try{document.all('AppntGrpFax').value= arrResult[0][13];}catch(ex){};
		try{document.all('Mobile').value= arrResult[0][14];}catch(ex){};
		//try{document.all('AppntMobileChs').value= arrResult[0][15];}catch(ex){};
		try{document.all('EMail').value= arrResult[0][16];}catch(ex){};
		//try{document.all('AppntBP').value= arrResult[0][17];}catch(ex){};
		//try{document.all('AppntMobile2').value= arrResult[0][18];}catch(ex){};
		//try{document.all('AppntMobileChs2').value= arrResult[0][19];}catch(ex){};
		//try{document.all('AppntEMail2').value= arrResult[0][20];}catch(ex){};
		//try{document.all('AppntBP2').value= arrResult[0][21];}catch(ex){};
		try{document.all('InsuredProvince').value= arrResult[0][28];}catch(ex){};
		try{document.all('InsuredCity').value= arrResult[0][29];}catch(ex){};
		try{document.all('InsuredDistrict').value= arrResult[0][30];}catch(ex){};
		//	showOneCodeName('province','InsuredProvince','InsuredProvinceName');
		//	showOneCodeName('city','InsuredCity','InsuredCityName');
		//	showOneCodeName('district','InsuredDistrict','InsuredDistrictName');
		getAddressName('province','InsuredProvince','InsuredProvinceName');
		getAddressName('city','InsuredCity','InsuredCityName');
		getAddressName('district','InsuredDistrict','InsuredDistrictName');
		return;
	}
 
	if(cCodeName=="paymode")
	{
		if(document.all('PayMode').value=="4")
		{
		//divLCAccount1.style.display="";
		}
		else
		{
		//divLCAccount1.style.display="none";
		//alert("accountImg===");
		}
	}
	afterCodeSelect2( cCodeName, Field );
}

/*********************************************************************
 *  代码选择后触发时间
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************/
function afterCodeSelect2( cCodeName, Field )
{
    try
    {
        //如果是无名单
        if( cCodeName == "PolTypeFlag")
        {
            if (Field.value!='0')
            {
                document.all('InsuredPeoples').readOnly=false;
                document.all('InsuredAppAge').readOnly=false;
            }
            else
            {
                document.all('InsuredPeoples').readOnly=true;
                document.all('InsuredAppAge').readOnly=true;
            }
        }
        if( cCodeName == "ImpartCode")
        {

        }
        if( cCodeName == "SequenceNo")
        {
			if(Field.value=="1"&&fm.SamePersonFlag.checked==false)
			{
				//emptyInsured();
				param="11";
				fm.pagename.value="11";
				fm.InsuredSequencename.value="被保险人资料";
				fm.RelationToMainInsured.value="00";
			}
			if(Field.value=="2"&&fm.SamePersonFlag.checked==false)
			{
				if(InsuredGrid.mulLineCount==0)
				{
				alert("请先添加第一被保人");
				fm.SequenceNo.value="1";
				fm.SequenceNoName.value="被保险人";
				return false;
				}
				//emptyInsured();
				noneedhome();
				param="122";
				fm.pagename.value="122";
				fm.InsuredSequencename.value="第二被保险人资料";
			}
			if(Field.value=="3"&&fm.SamePersonFlag.checked==false)
			{
				if(InsuredGrid.mulLineCount==0)
				{
				alert("请先添加第一被保人");
				Field.value="1";
				fm.SequenceNo.value="1";
				fm.SequenceNoName.value="被保险人";
				return false;
				}
				if(InsuredGrid.mulLineCount==1)
				{
					alert("请先添加第二被保人");
					Field.value="1";
					fm.SequenceNo.value="2";
					fm.SequenceNoName.value="第二被保险人";
					return false;
				}
				//emptyInsured();
				noneedhome();
				param="123";
				fm.pagename.value="123";
				fm.InsuredSequencename.value="第三被保险人资料";
			}
			if (scantype== "scan")
			{
			setFocus();
			}
        }
        if( cCodeName == "CheckPostalAddress")
        {
	        if(fm.CheckPostalAddress.value=="1")
	        {
				document.all('PostalAddress').value=document.all('GrpAddress').value;
				document.all('ZipCode').value=document.all('GrpZipCode').value;
				document.all('Phone').value= document.all('Phone').value;
	        }
	        else if(fm.CheckPostalAddress.value=="2")
	        {
				document.all('PostalAddress').value=document.all('HomeAddress').value;
				document.all('ZipCode').value=document.all('HomeZipCode').value;
				document.all('Phone').value= document.all('HomePhone').value;
	        }
	        else if(fm.CheckPostalAddress.value=="3")
	        {
				document.all('PostalAddress').value="";
				document.all('ZipCode').value="";
				document.all('Phone').value= "";
	        }
        }

    }
    catch(ex) {}

}
function noneedhome()
{
    var insuredno="";
    if(InsuredGrid.mulLineCount>=1)
    {
        for(var personcount=0;personcount<=InsuredGrid.mulLineCount;personcount++)
        {
        	if(InsuredGrid.getRowColData(personcount,5)=="00")
        	{
        		insuredno=InsuredGrid.getRowColData(personcount,1);
        		break;
        	}
        }
		
		var sqlid40="ContCheckInputSql40";
		var mySql40=new SqlClass();
		mySql40.setResourceName("app.ContCheckInputSql"); //指定使用的properties文件名
		mySql40.setSqlId(sqlid40);//指定使用的Sql的id
		mySql40.addSubPara(insuredno);//指定传入的参数
		mySql40.addSubPara(fm.ContNo.value);//指定传入的参数
		mySql40.addSubPara(insuredno);//指定传入的参数		
	    var strhomea=mySql40.getString();
		
//       var strhomea="select HomeAddress,HomeZipCode,HomePhone from lcaddress where customerno='"+insuredno+"' and addressno=(select addressno from lcinsured where contno='"+fm.ContNo.value+"' and insuredno='"+insuredno+"')";
       arrResult=easyExecSql(strhomea,1,0);
       try{document.all('HomeAddress').value= arrResult[0][0];}catch(ex){};
       try{document.all('HomeZipCode').value= arrResult[0][1];}catch(ex){};
       try{document.all('HomePhone').value= arrResult[0][2];}catch(ex){};
       fm.InsuredAddressNo.value = "";
       fm.InsuredNo.value = "";
    }
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
		
		var sqlid41="ContCheckInputSql41";
		var mySql41=new SqlClass();
		mySql41.setResourceName("app.ContCheckInputSql"); //指定使用的properties文件名
		mySql41.setSqlId(sqlid41);//指定使用的Sql的id
		mySql41.addSubPara(PlaceCode);//指定传入的参数
		mySql41.addSubPara(PlaceType);//指定传入的参数		
	    var strSQL=mySql41.getString();
		
//		var strSQL="select placecode,placename from ldaddress where placecode='"+PlaceCode+"' and placetype='"+PlaceType+"'";
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

//------------------------Beg
//Add By Chenrong
//Date:2006.5.15

//查询操作员随动速度
function initQueryRollSpeed()
{
	
		var sqlid42="ContCheckInputSql42";
		var mySql42=new SqlClass();
		mySql42.setResourceName("app.ContCheckInputSql"); //指定使用的properties文件名
		mySql42.setSqlId(sqlid42);//指定使用的Sql的id
//		mySql42.addSubPara(PlaceCode);//指定传入的参数
		mySql42.addSubPara("1");//指定传入的参数		
	    var strSQL=mySql42.getString();
	
//    var strSQL = "select SYSVAR,SYSVARVALUE from LDSysvar where SYSVAR like 'ROLLSPEED%25' order by SYSVAR ASC";
	var arrSpeed = easyExecSql(strSQL); 
	if (arrSpeed != null)
	{
    	fm.RollSpeedBase.value = arrSpeed[0][1];
    	fm.RollSpeedSelf.value = arrSpeed[1][1];
	}


		var sqlid43="ContCheckInputSql43";
		var mySql43=new SqlClass();
		mySql43.setResourceName("app.ContCheckInputSql"); //指定使用的properties文件名
		mySql43.setSqlId(sqlid43);//指定使用的Sql的id
		mySql43.addSubPara(fm.Operator.value);//指定传入的参数
	    strSQL=mySql43.getString();

//    strSQL = "select rollspeed from LDRollSpeed where UserCode='" + fm.Operator.value + "'";
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
		"ProposalContNo", "PolAppntDate","ManageCom",
		"AgentCode", "AgentName","BranchAttr",
		"RelationShip",
		"AppntName", "AppntIDType", "AppntIDNo", "AppntSex", "AppntBirthday", 
		"AppntAge", "AppntMarriage","AppntNativePlace",
		"AppntOccupationCode", "AppntOccupationType","AppntLicenseType", 
		"AppntProvince", "AppntCity", "AppntDistrict", "AppntPostalAddress", "AppntZipCode", 
		 "AppntMobile","AppntPhone", "AppntFax","AppntHomePhone", "AppntGrpName","AppntEMail",
		"PayMode","AppntBankCode", "AppntAccName", "AppntBankAccNo",
		"SecPayMode", "SecAppntBankCode", "SecAppntAccName", "SecAppntBankAccNo", 
		"Income0","IncomeWay0","AppntStature","AppntAvoirdupois",
		"appntfinaimpart","appnthealimpart","Remark",
		"RelationToAppnt", 
		"Name", "IDType", "IDNo", "Sex", "Birthday", 
		"InsuredAppAge","Marriage","NativePlace","OccupationCode", "OccupationType", 
		"LicenseType",
		"InsuredProvince", "InsuredCity", "InsuredDistrict", "PostalAddress", "ZIPCODE", 
		"Mobile","Phone", "Fax","HomePhone", "GrpName","EMail",
		"Income","IncomeWay","Stature","Avoirdupois",
		"insufinaimpart","insuhealimpart","RiskButton","BnfButton"
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

//tongemng 2009-03-03 Add
//客户号手工合并
function GoToAppnt(){
	var i = 0;
    var checkFlag = 0;
  	var state = "0";
  var	ContNo = document.all('PrtNo').value;
  //	var selno=OPolGrid.getSelNo();//alert("selno:"+selno)
	//if (selno ==0||selno==null)
//	{
//	      alert("请选择一条记录！");
//	      return;
//	}
  
  	//if (checkFlag) { 
    	//var	ContNo = OPolGrid.getRowColData(selno - 1, 1); 	
    	//var prtNo = OPolGrid.getRowColData(selno - 1, 2); 
    	//var RiskCode =OPolGrid.getRowColData(selno-1,3);
    	//var AppntName = OPolGrid.getRowColData(selno - 1, 4);
    	//var InsuredName = OPolGrid.getRowColData(selno - 1, 5);
    	//var ScanMakeDate = OPolGrid.getRowColData(selno - 1, 6);
    	//var AppntNo = OPolGrid.getRowColData(selno - 1, 7);
    	//var InsuredNo = OPolGrid.getRowColData(selno - 1, 8);
	  	//var NoType = type;
	 // 	alert('1');
	  var tAppntName = document.all('AppntName').value ;
	  var tAppntNo = document.all('AppntNo').value ;
    	var strReturn="1";
    	sFeatures = "";
    	//	alert('2');
    	window.open("./CustomerMergeMain.jsp?ScanFlag=1&LoadFlag=1&prtNo="+ContNo+"&AppntName="+tAppntName+"&AppntNo="+tAppntNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&CustomerType=A","",sFeatures);        
    //}
}

function GoToInsured(){
	var i = 0;
    var checkFlag = 0;
  	var state = "0";
  
  	var selno=InsuredGrid.getSelNo();//alert("selno:"+selno)
	if (selno ==0||selno==null)
	{
	      alert("请选择一条记录！");
	      return;
	}
  
  	//if (checkFlag) { 
    	var	ContNo = document.all('PrtNo').value;
    	var tInsuredName = InsuredGrid.getRowColData(selno - 1, 2);
    	var tInsuredNo = InsuredGrid.getRowColData(selno - 1, 1);
	  	var NoType = type;
	  	//alert("missionid=="+fm.MissionID.value+"&&submissionid=="+fm.SubMissionID.value);
    	var strReturn="1";
    	sFeatures = "";//alert("InsuredNo: "+InsuredNo);
    	window.open("./CustomerMergeMain.jsp?ScanFlag=1&LoadFlag=1&prtNo="+ContNo+"&InsuredNo="+tInsuredNo+"&InsuredName="+tInsuredName+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&CustomerType=I","",sFeatures);        
    //}
    displayInsuredButton();
}

function UnionConfirm(){

	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    lockScreen('lkscreen');  
	fm.action = "./UnionConfirmSave.jsp";
	document.getElementById("fm").submit(); //提交
}

function afterSubmit2( FlagStr, content )
{
	unlockScreen('lkscreen');  
		showInfo.close();
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
//		top.opener.easyQueryClick();// 刷新页面
//		top.opener.easyQueryClickSelf();// 刷新页面
//		top.opener.easyQueryClick();// 刷新页面
//		top.opener.easyQueryClickSelf();// 刷新页面
		top.opener.jQuery("#privateSearch").click();;// 刷新页面
		top.opener.jQuery("#publicSearch").click();// 刷新页面
		
//		window.close();
		
		top.close();
}


/**
 * 校验银行账号  090910增加  校验银行账号与银行编码的规则
 * 所有双录并且需判断LoadFlag的页面需加入该方法
 * 双录且不需判断的界面则去掉LoadFlag=='1'的校验
 * */
function checkAccNo(aObject,bObject){
	if(checkBankAccNo(aObject.value,bObject.value)==true){
		if(LoadFlag=='1'){
			confirmSecondInput(bObject,'onblur');
		}
	}else{
		bObject.focus();
	}
}

function displayAppntButton()
{
	fm.AppntChkButton1.disabled='true';
	var CustomerName = document.all('AppntName').value ;
	var Customerno = document.all('AppntNo').value ;
	var CustomerSex = document.all('AppntSex').value;
	var CustomerBrithday = document.all('AppntBirthday').value;
	var CustomerIDno = document.all('AppntIDNo').value;	
	if(checkCustomer(Customerno, CustomerName, CustomerSex, CustomerBrithday,CustomerIDno))
		fm.AppntChkButton1.disabled='';
}

function displayInsuredButton()
{
	fm.InsuredChkButton1.disabled='true';
	var CustomerName = document.all('Name').value ;
	var Customerno = document.all('InsuredNo').value ;
	var CustomerSex = document.all('Sex').value;
	var CustomerBrithday = document.all('Birthday').value;
	var CustomerIDno = document.all('IDNo').value;
	if(checkCustomer(Customerno, CustomerName, CustomerSex, CustomerBrithday,CustomerIDno))
		fm.InsuredChkButton1.disabled='';
}

function checkCustomer(Customerno, CustomerName, CustomerSex, CustomerBrithday,CustomerIDno)
{	
	
	return false;
}