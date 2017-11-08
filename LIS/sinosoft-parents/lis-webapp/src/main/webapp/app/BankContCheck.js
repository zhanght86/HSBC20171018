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
var cacheWin=null;
var mShowCustomerDetail = "GROUPPOL";
this.window.onfocus=myonfocus;
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";

//该文件中包含客户端需要处理的函数和事件
var showInfo1;

//存放添加动作执行的次数
var addAction = 0;
//暂交费总金额
var sumTempFee = 0.0;
//暂交费信息中交费金额累计
var tempFee = 0.0;
//暂交费分类信息中交费金额累计
var tempClassFee = 0.0;
//单击确定后，该变量置为真，单击添加一笔时，检验该值是否为真，为真继续，然后再将该变量置假
var confirmFlag = false;
//
var arrCardRisk;

//工作流flag
var mWFlag = 0;

//初始化时，查询合同信息、投保人信息(包括交费信息)、被保人信息(包括告知信息)，险种信息，并显示在界面
function initQuery()
{//alert(36);
    initQueryCont();//【初始化时查询合同信息】-----包括代理人信息
	initQueryAgentImpartGrid();//查询 业务员告知信息
	initQueryAppnt();//【查询投保人信息】－－－包括投保人的地址信息
	initQueryAccount();//【缴费信息查询】------查询保单的缴费信息，包括缴费方式及帐号信息等。
    initQueryAppntImpartInfo();	//查询投保人告知信息
	initQueryInsuredInfo();//【查询被保人信息】----放入列表
    //alert("错没错？");
	getInsuredInfo();//被保人基本信息及地址信息
	getInsuredImpartInfo();//被保人告知信息
	getInsuredPolInfo();//被保人险种信息
	showCodeName();	//根据页面输入框的代码带出汉字，并显示出来。
	//判断系统中是否存在姓名、性别、出生日期相同或证件类型证件号码相同的投保人信息。
//	alert('1');
    AppntChkNew();
	// 在初始化body时自动效验被保人信息
//	alert('2');
    InsuredChkNew();
	//判断记事本中的记录数量
//	alert('3');
	checkNotePad(prtNo,LoadFlag);
//	alert('4');
	if (LoadFlag == "5" && scantype == "scan")
        initQueryRollSpeed();
//	 alert('5');
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

//初始化时查询合同信息
function initQueryCont()
{
	var sSQL="";
	if(document.all('ContNo').value=="")
	{
		
		var sqlid1="BankContCheckSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(fm.PrtNo.value);//指定传入的参数
	    var sSQL=mySql1.getString();	
		
//		var sSQL="select contno,proposalcontno,prtno,polapplydate,selltype,salechnl,managecom,agentcode,cvalidate"
//			+" ,agentcom,agentbankcode,bankagent,remark,signname"
//			+" from lccont where prtno='"+fm.PrtNo.value+"' ";
	}
	else
	{
		
		var sqlid2="BankContCheckSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(fm.ContNo.value);//指定传入的参数
	    var sSQL=mySql2.getString();	
		
//		var sSQL="select contno,proposalcontno,prtno,polapplydate,selltype,salechnl,managecom,agentcode,cvalidate"
//			+" ,agentcom,agentbankcode,bankagent,remark,signname"
//			+" from lccont where contno='"+fm.ContNo.value+"' ";
	}
	var ArrCont=easyExecSql(sSQL);
	if(ArrCont!=null)
	{
	document.all('ContNo').value=ArrCont[0][0];	
	document.all('ProposalContNo').value=ArrCont[0][1];	
	document.all('PrtNo').value=ArrCont[0][2];	
	document.all('PolAppntDate').value=ArrCont[0][3];	
	document.all('SellType').value=ArrCont[0][4];	
	document.all('SaleChnl').value=ArrCont[0][5];	
	document.all('ManageCom').value=ArrCont[0][6];	
	document.all('AgentCode').value=ArrCont[0][7];	
	document.all('CValiDate').value=ArrCont[0][8];
	document.all('AgentCom').value=ArrCont[0][9];
	//tongmeng 2007-09-10 modify
	//去掉界面录入的银行类型
	//document.all('AgentBankCode').value=ArrCont[0][10];
	document.all('CounterCode').value=ArrCont[0][11];
	//alert(ArrCont[0][12]);
	document.all('Remark').value=ArrCont[0][12];
	document.all('SignName').value=ArrCont[0][13];
	document.all('FirstTrialDate').value=ArrCont[0][14];
	document.all('XQremindFlag').value=ArrCont[0][15];
	showOneCodeName('XQremindFlag','XQremindFlag','XQremindFlagName');
	//alert(document.all('Remark').value);
	}
	queryAgent();//查询 代理人信息
}
//查询代理人信息
function queryAgent()
{
	if(document.all('AgentCode').value != "")
	{
		
		var cAgentCode = fm.AgentCode.value;  //保单号码
		//如果业务员代码长度为8则自动查询出相应的代码名字等信息
		//tongmeng 2007-09-10 modify
		//MS使用代理人员编码长度为10
		//if(cAgentCode.length!=8){return;}
		if(cAgentCode.length!=10){return;}
		
	    var sqlid3="BankContCheckSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql3.setSqlId(sqlid3);//指定使用的Sql的id
		mySql3.addSubPara(cAgentCode);//指定传入的参数
	    var strSQL=mySql3.getString();	
		
//		var strSQL = "select a.AgentCode,a.AgentGroup,a.ManageCom,a.Name,c.BranchManager,b.IntroAgency,b.AgentSeries,b.AgentGrade,c.BranchAttr,b.AscriptSeries,c.name from LAAgent a,LATree b,LABranchGroup c where 1=1 "
//		 + "and a.AgentCode = b.AgentCode and a.agentstate<>'03' and a.agentstate<>'04' and a.AgentGroup = c.AgentGroup and a.AgentCode='"+cAgentCode+"'";
		
		var arrResult = easyExecSql(strSQL);
		if (arrResult != null) 
		{
			document.all('AgentCode').value = arrResult[0][0];
			document.all('BranchAttr').value = arrResult[0][10];
			document.all('AgentGroup').value = arrResult[0][1];
			document.all('AgentName').value = arrResult[0][3];
		}
		//alert("document.all('AgentCom').value=="+document.all('AgentCom').value);
		
		var sqlid4="BankContCheckSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql4.setSqlId(sqlid4);//指定使用的Sql的id
		mySql4.addSubPara(document.all('AgentCom').value);//指定传入的参数
	    var strSQL=mySql4.getString();	
		
//		var strSQL = "select bankcode,name from lacom where agentcom='"+ document.all('AgentCom').value+"'";
		arrResult = easyExecSql(strSQL,1,0);
		if(arrResult!==null)
		{ 
			//tongmeng 2007-09-10 本地化去掉银行编码
			document.all('InputAgentComName').value=arrResult[0][1]; 
			//document.all('BankCode1').value=arrResult[0][0]; 
			//document.all('BankCodeName').value=arrResult[0][1]; 
		}
	}
}
//查询 业务员告知信息
function initQueryAgentImpartGrid()
{
	var turnPageAgent = new turnPageClass();
	var tContNo=fm.ContNo.value;
	var tAgentCode=fm.AgentCode.value;
	
		var sqlid5="BankContCheckSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql5.setSqlId(sqlid5);//指定使用的Sql的id
		mySql5.addSubPara(tAgentCode);//指定传入的参数
		mySql5.addSubPara(tContNo);//指定传入的参数
	    var sSQL=mySql5.getString();	
	
//	var sSQL="select impartver,impartcode,impartcontent,impartparammodle from lccustomerimpart"
//		+" where customernotype='2' and customerno='"+tAgentCode+"' and contno='"+tContNo+"'";
   	turnPageAgent.queryModal(sSQL, AgentImpartGrid);
}

//查询投保人信息－－－包括投保人的地址信息
function initQueryAppnt()
{
	//根据"合同号"查询出投保人信息，并填入相应输入栏
	
		var sqlid6="BankContCheckSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql6.setSqlId(sqlid6);//指定使用的Sql的id
		mySql6.addSubPara(document.all('ContNo').value);//指定传入的参数
	    var sSQL=mySql6.getString();	
	
//	var sSQL="select contno,appntno,addressno,(select vipvalue from ldperson where customerno=appntno),"
//			+"appntname,appntsex,appntbirthday,idtype,idno,"
//			+"marriage,nativeplace,licensetype,occupationcode,occupationtype,"
//			+"makedate,maketime,modifydate,modifytime,relationtoinsured,worktype "
//			+" from lcappnt where contno='"+document.all('ContNo').value+"'";
	var arrApp=easyExecSql(sSQL); //prompt("",sSQL);
	if(arrApp!=null)
	{
		document.all('AppntNo').value=arrApp[0][1];
		document.all('AppntAddressNo').value=arrApp[0][2];
		document.all('AppntVIPValue').value=arrApp[0][3];
		document.all('AppntName').value=arrApp[0][4];
		document.all('AppntSex').value=arrApp[0][5];
		document.all('AppntBirthday').value=arrApp[0][6];
		document.all('AppntIDType').value=arrApp[0][7];
		document.all('AppntIDNo').value=arrApp[0][8];
		document.all('AppntMarriage').value=arrApp[0][9];
		document.all('AppntNativePlace').value=arrApp[0][10];
		document.all('AppntLicenseType').value=arrApp[0][11];
		document.all('AppntOccupationCode').value=arrApp[0][12];
		document.all('AppntOccupationType').value=arrApp[0][13];
		document.all('AppntMakeDate').value=arrApp[0][14];
		document.all('AppntMakeTime').value=arrApp[0][15];
		document.all('AppntModifyDate').value=arrApp[0][16];
		document.all('AppntModifyTime').value=arrApp[0][17];
		document.all('RelationToInsured').value=arrApp[0][18];
		document.all('AppntWorkType').value=arrApp[0][19];
		document.all('AppIDPeriodOfValidity').value=arrApp[0][20];
		getAge(); //计算投保人年龄
		getAppntAddressInfo();	//查询投保人地址信息	
	}
}

//【缴费信息查询】------查询保单的缴费信息，包括缴费方式及帐号信息等。
function initQueryAccount()
{
	
		var sqlid7="BankContCheckSql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql7.setSqlId(sqlid7);//指定使用的Sql的id
		mySql7.addSubPara(document.all('ContNo').value);//指定传入的参数
	    var sSQL=mySql7.getString();	
	
//	var sSQL="select contno,newpaymode,newbankcode,newaccname,newbankaccno,paylocation,bankcode,accname,bankaccno "
//		+" from lccont where contno='"+fm.ContNo.value+"'";
	//var sSQL="select bankcode,bankaccno,accname from LCAppnt where PrtNo='"+fm.PrtNo.value+"'"
	var arrAcc=easyExecSql(sSQL);	
	if(arrAcc!=null)
	{
		document.all('PayMode').value=arrAcc[0][5];
		document.all('AppntBankCode').value=arrAcc[0][6];
		document.all('AppntAccName').value=arrAcc[0][7];
		document.all('AppntBankAccNo').value=arrAcc[0][8];
		
		document.all('SecPayMode').value=arrAcc[0][5];
		document.all('SecAppntBankCode').value=arrAcc[0][6];
	    document.all('SecAppntAccName').value=arrAcc[0][7];
 	    document.all('SecAppntBankAccNo').value=arrAcc[0][8];
	}		
}

//查询投保人告知信息
function initQueryAppntImpartInfo()
{
	//查询 投保人收入、收入来源、身高、体重等告知信息，并填入相应的输入框中
	var tContNo=fm.ContNo.value;
	var tAppntNo=fm.AppntNo.value;
    //var strSQL0="select ImpartParam from LCCustomerImpartparams where CustomerNoType='0' and CustomerNo='"+tAppntNo+"' and ContNo='"+tContNo+"' and impartver='01' and impartcode='001' and ImpartParamNo = '1'";
    //var strSQL1="select ImpartParam from LCCustomerImpartparams where CustomerNoType='0' and CustomerNo='"+tAppntNo+"' and ContNo='"+tContNo+"' and impartver='01' and impartcode='001' and ImpartParamNo = '2'";
    //var strSQL2="select ImpartParam from LCCustomerImpartparams where CustomerNoType='0' and CustomerNo='"+tAppntNo+"' and ContNo='"+tContNo+"' and impartver='02' and impartcode='000' and ImpartParamNo = '1'";
    //var strSQL3="select ImpartParam from LCCustomerImpartparams where CustomerNoType='0' and CustomerNo='"+tAppntNo+"' and ContNo='"+tContNo+"' and impartver='02' and impartcode='000' and ImpartParamNo = '2'";
    //var arrResult0 = easyExecSql(strSQL0,1,0);
    //var arrResult1 = easyExecSql(strSQL1,1,0);
    //var arrResult2 = easyExecSql(strSQL2,1,0);
    //var arrResult3 = easyExecSql(strSQL3,1,0);
	//try{document.all('Income0').value= arrResult0[0][0];}catch(ex){};
	//try{document.all('IncomeWay0').value= arrResult1[0][0];}catch(ex){};
	//try{document.all('AppntStature').value= arrResult2[0][0];}catch(ex){};
	//try{document.all('AppntAvoirdupois').value= arrResult3[0][0];}catch(ex){};
	//查询投保人告知，并放入表格中
	
		var sqlid8="BankContCheckSql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql8.setSqlId(sqlid8);//指定使用的Sql的id
		mySql8.addSubPara(tAppntNo);//指定传入的参数
		mySql8.addSubPara(tContNo);//指定传入的参数
	    var strSQL=mySql8.getString();	
	
//	var strSQL="select impartver,impartcode,impartcontent,impartparammodle "
//		+" from lccustomerimpart where customernotype='0'"
//		+" and ((impartver ='01' and impartcode<>'001') or (impartver ='02' and impartcode<>'000'))"
//		+" and customerno='"+tAppntNo+"' and contno='"+tContNo+"'";
	turnPage.queryModal(strSQL,ImpartGrid);	

	//查询其它声明（200个汉字以内）
	
		var sqlid9="BankContCheckSql9";
		var mySql9=new SqlClass();
		mySql9.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql9.setSqlId(sqlid9);//指定使用的Sql的id
		mySql9.addSubPara(tContNo);//指定传入的参数
	    var remarkSQL=mySql9.getString();	
	
	//var remarkSQL="select remark from lccont where contno='"+tContNo+"'";
	var remarkArr= easyExecSql(remarkSQL,1,0);
	//alert('111'+remarkArr[0][0]);
	try{document.all('Remark').value= remarkArr[0][0];}catch(ex){};
}

//【查询被保人信息】－－－
function initQueryInsuredInfo()
{
	
		var sqlid10="BankContCheckSql10";
		var mySql10=new SqlClass();
		mySql10.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql10.setSqlId(sqlid10);//指定使用的Sql的id
		mySql10.addSubPara(fm.ContNo.value);//指定传入的参数
	    var sSQL=mySql10.getString();	
	
//	var sSQL ="select insuredno,name,sex,birthday,relationtomaininsured,sequenceno "
//			+" from lcinsured where contno='"+fm.ContNo.value+"'";
	var turnInsurePage = new turnPageClass();
	turnInsurePage.queryModal(sSQL,InsuredGrid);
	if (InsuredGrid.mulLineCount>0)
	{
	    try{document.all(InsuredGrid.findSpanID(0)).all('InsuredGridSel').checked =true;} catch(ex) {}
	    try{fm.InsuredNo.value=InsuredGrid.getRowColData(0,1);} catch(ex) {}
	}
}

/*********************************************************************
 *  投保人地址信息
 **********************************************************************/
function getAppntAddressInfo()
{//alert(254);
	var tAppntAddressNo=document.all('AppntAddressNo').value;
	var tAppntNo=document.all('AppntNo').value;
    if(tAppntAddressNo=="" && tAppntNo=="")
	{
		return;
	}
	
		var sqlid11="BankContCheckSql11";
		var mySql11=new SqlClass();
		mySql11.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql11.setSqlId(sqlid11);//指定使用的Sql的id
		mySql11.addSubPara(tAppntNo);//指定传入的参数
		mySql11.addSubPara(tAppntAddressNo);//指定传入的参数
	    var sSQL=mySql11.getString();	
	
//	var sSQL="select customerno,addressno,province,city,county,postaladdress,zipcode,mobile,phone,homephone,fax,grpname,email"
//	+ " from lcaddress where customerno='"+tAppntNo+"' "
//	+ " and addressno='"+tAppntAddressNo+"'";
	
	var arrAppAdd=easyExecSql(sSQL); //prompt("",sSQL);
	if(arrAppAdd!=null)
	{
     	document.all('AppntProvince').value=arrAppAdd[0][2];  
		document.all('AppntCity').value=arrAppAdd[0][3];  
		document.all('AppntDistrict').value=arrAppAdd[0][4];  
		document.all('AppntPostalAddress').value=arrAppAdd[0][5];  
		document.all('AppntZipCode').value=arrAppAdd[0][6];  
		document.all('AppntPhone').value=arrAppAdd[0][8];  
		//document.all('AppntGrpPhone').value=arrAppAdd[0][8];  
		document.all('AppntHomePhone').value=arrAppAdd[0][9];  
		document.all('AppntFax').value=arrAppAdd[0][10];  
		document.all('AppntGrpName').value=arrAppAdd[0][11];  
		document.all('AppntEMail').value=arrAppAdd[0][12]; 
	}//alert(278);
}

/*********************************************************************
 *  InsuredGrid的RadioBox点击事件，获得被保人详细信息，填入被保人信息表
 **********************************************************************/
function getInsuredDetail(parm1,parm2)
{
    emptyInsured();//清除被保人录入区域，重新赋值
    fm.InsuredNo.value=document.all(parm1).all('InsuredGrid1').value;
    //查询选中的被保人信息
	getInsuredInfo();//被保人基本信息及地址信息
	getInsuredImpartInfo();//被保人告知信息
	getInsuredPolInfo();//被保人险种信息
}

//被保人基本信息及地址信息
function getInsuredInfo()
{
	var tContNo=fm.ContNo.value;
	var tInsuredNo=document.all('InsuredNo').value;
	if(tContNo=="" || tInsuredNo=="")
	{
		return false;
	}
	//根据"合同号"和 "被保人号码"查询出被保人信息，并填入相应输入栏
	
		var sqlid12="BankContCheckSql12";
		var mySql12=new SqlClass();
		mySql12.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql12.setSqlId(sqlid12);//指定使用的Sql的id
		mySql12.addSubPara(tContNo);//指定传入的参数
		mySql12.addSubPara(tInsuredNo);//指定传入的参数
	    var sSQL=mySql12.getString();	
	
//	var sSQL="select contno,insuredno,sequenceno,relationtomaininsured,relationtoappnt,addressno,"
//			+"(select vipvalue from ldperson where customerno=insuredno),"
//			+"name,sex,birthday,idtype,idno,"
//			+"marriage,nativeplace,licensetype,occupationcode,occupationtype,worktype"
//			+" from lcinsured where contno='"+tContNo+"' and insuredno='"+tInsuredNo+"' ";
	var arrInsured=easyExecSql(sSQL); 
	if(arrInsured!=null)
	{
		document.all('InsuredNo').value=arrInsured[0][1];
		document.all('InsuredAddressNo').value=arrInsured[0][5];
		document.all('SequenceNo').value=arrInsured[0][2];
		document.all('RelationToMainInsured').value=arrInsured[0][3];
		document.all('RelationToAppnt').value=arrInsured[0][4];
		
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
		document.all('IDPeriodOfValidity').value=arrInsured[0][18];
	    getAge2(); //取得被保人年龄
		getInsuredAddressInfo();//取被保人地址信息
	}
}


/*********************************************************************
 *  被保人地址信息
 **********************************************************************/
function getInsuredAddressInfo()
{
	var tInsuredNo=document.all('InsuredNo').value;
	var tInsuredAddressNo=document.all('InsuredAddressNo').value;
	if(tInsuredNo=="" && tInsuredAddressNo=="")
	{
		retrun;
	}
	//查询被保人的地址信息
	
		var sqlid13="BankContCheckSql13";
		var mySql13=new SqlClass();
		mySql13.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql13.setSqlId(sqlid13);//指定使用的Sql的id
		mySql13.addSubPara(tInsuredNo);//指定传入的参数
		mySql13.addSubPara(tInsuredAddressNo);//指定传入的参数
	    var sInsuredAddSQL=mySql13.getString();	
	
//	var sInsuredAddSQL="select customerno,addressno,province,city,county,postaladdress,zipcode,mobile,phone,homephone,fax,grpname,email"
//			+ " from lcaddress where customerno='"+tInsuredNo+"' "
//			+ " and addressno='"+tInsuredAddressNo+"'";
	var arrInsuredAdd=easyExecSql(sInsuredAddSQL); 
	if(arrInsuredAdd!=null)
	{
		fm.InsuredAddressNoName.value=arrInsuredAdd[0][5];  
     	fm.InsuredProvince.value=arrInsuredAdd[0][2];  
		fm.InsuredProvinceName.value=arrInsuredAdd[0][2];
		fm.InsuredCity.value=arrInsuredAdd[0][3];  
		fm.InsuredDistrict.value=arrInsuredAdd[0][4];  
		fm.PostalAddress.value=arrInsuredAdd[0][5];  
		fm.ZIPCODE.value=arrInsuredAdd[0][6];  
		fm.Phone.value=arrInsuredAdd[0][8];  
		//fm.GrpPhone.value=arrInsuredAdd[0][8];  
		fm.HomePhone.value=arrInsuredAdd[0][9];  
		fm.Fax.value=arrInsuredAdd[0][10];  
		fm.GrpName.value=arrInsuredAdd[0][11];  
		fm.EMail.value=arrInsuredAdd[0][12]; 
	}
}

//查询被保人告知信息信息
function getInsuredImpartInfo()
{
	//查询 被保人收入、收入来源、身高、体重等告知信息，并填入相应的输入框中
	var tContNo=document.all('ContNo').value;
	var tInsuredNo=document.all('InsuredNo').value;
	if(tContNo=="" || tInsuredNo=="")
	{
		return false;
	}
    //var strSQL0="select ImpartParam from LCCustomerImpartparams where CustomerNoType='1' and CustomerNo='"+tInsuredNo+"' and ContNo='"+tContNo+"' and impartver='01' and impartcode='001' and ImpartParamNo = '1'";
    //var strSQL1="select ImpartParam from LCCustomerImpartparams where CustomerNoType='1' and CustomerNo='"+tInsuredNo+"' and ContNo='"+tContNo+"' and impartver='01' and impartcode='001' and ImpartParamNo = '2'";
    //var strSQL2="select ImpartParam from LCCustomerImpartparams where CustomerNoType='1' and CustomerNo='"+tInsuredNo+"' and ContNo='"+tContNo+"' and impartver='02' and impartcode='000' and ImpartParamNo = '1'";
    //var strSQL3="select ImpartParam from LCCustomerImpartparams where CustomerNoType='1' and CustomerNo='"+tInsuredNo+"' and ContNo='"+tContNo+"' and impartver='02' and impartcode='000' and ImpartParamNo = '2'";
    //var arrResult0 = easyExecSql(strSQL0,1,0);
    //var arrResult1 = easyExecSql(strSQL1,1,0);
    //var arrResult2 = easyExecSql(strSQL2,1,0);
    //var arrResult3 = easyExecSql(strSQL3,1,0);
	//try{document.all('Income').value= arrResult0[0][0];}catch(ex){};
	//try{document.all('IncomeWay').value= arrResult1[0][0];}catch(ex){};
	//try{document.all('Stature').value= arrResult2[0][0];}catch(ex){};
	//try{document.all('Avoirdupois').value= arrResult3[0][0];}catch(ex){};
	//查询被保人告知，并放入表格中
	
		var sqlid14="BankContCheckSql14";
		var mySql14=new SqlClass();
		mySql14.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql14.setSqlId(sqlid14);//指定使用的Sql的id
		mySql14.addSubPara(tInsuredNo);//指定传入的参数
		mySql14.addSubPara(tContNo);//指定传入的参数
	    var sInsuredAddSQL=mySql14.getString();	
	
//	var strSQL="select impartver,impartcode,impartcontent,impartparammodle "
//		+" from lccustomerimpart where customernotype='1'"
//		+" and customerno='"+tInsuredNo+"' and contno='"+tContNo+"'";
	turnPage.queryModal(sInsuredAddSQL,ImpartInsuredGrid);	
	
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
	
			var sqlid15="BankContCheckSql15";
		var mySql15=new SqlClass();
		mySql15.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql15.setSqlId(sqlid15);//指定使用的Sql的id
		mySql15.addSubPara(tInsuredNo);//指定传入的参数
		mySql15.addSubPara(tContNo);//指定传入的参数
	    var strSQL=mySql15.getString();	
	
//     var strSQL ="select polno,riskcode,(select riskname from lmriskapp where riskcode=lcpol.riskcode), "
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

function getPolDetail(parm1,parm2)
{
    fm.SelPolNo.value=document.all(parm1).all('PolGrid1').value;
    //查询受益人信息
    getLCBnfInfo();
}

//获得受益人信息
function getLCBnfInfo()
{
	initLCBnfGrid();
	var tPolNo=document.all('SelPolNo').value;
	var tContNo=document.all('ContNo').value;
	
		var sqlid16="BankContCheckSql16";
		var mySql16=new SqlClass();
		mySql16.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql16.setSqlId(sqlid16);//指定使用的Sql的id
		mySql16.addSubPara(tContNo);//指定传入的参数
		mySql16.addSubPara(tPolNo);//指定传入的参数
	    var sSQL=mySql16.getString();	
	
//	var sSQL="select bnftype,name,idtype,idno,relationtoinsured,bnfgrade,bnflot"
//		+" ,(select name from ldperson where customerno=insuredno),'',insuredno"
//		+" from lcbnf where contno='"+tContNo+"' and polno='"+tPolNo+"'";
	turnPage.queryModal(sSQL,LCBnfGrid);
}

function afterCodeSelect( cCodeName, Field )
{
 //alert("afdasdf");
    if(cCodeName=="GetAddressNo"){
		
		var sqlid17="BankContCheckSql17";
		var mySql17=new SqlClass();
		mySql17.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql17.setSqlId(sqlid17);//指定使用的Sql的id
		mySql17.addSubPara(fm.AppntAddressNo.value);//指定传入的参数
		mySql17.addSubPara(fm.AppntNo.value);//指定传入的参数
	    var strSQL=mySql17.getString();	
		
//        var strSQL="select b.* from LCAddress b where b.AddressNo='"+fm.AppntAddressNo.value+"' and b.CustomerNo='"+fm.AppntNo.value+"'";
            arrResult=easyExecSql(strSQL);
        try{document.all('AppntNo').value= arrResult[0][0];}catch(ex){};
        try{document.all('AppntAddressNo').value= arrResult[0][1];}catch(ex){};
        try{document.all('AppntAddressNoName').value= arrResult[0][2];}catch(ex){};
        try{document.all('AppntPostalAddress').value= arrResult[0][2];}catch(ex){};
        try{document.all('AppntZipCode').value= arrResult[0][3];}catch(ex){};
        try{document.all('AppntPhone').value= arrResult[0][4];}catch(ex){};
        try{document.all('AppntFax').value= arrResult[0][5];}catch(ex){};
        try{document.all('AppntHomeAddress').value= arrResult[0][6];}catch(ex){};
        try{document.all('AppntHomeZipCode').value= arrResult[0][7];}catch(ex){};
        try{document.all('AppntHomePhone').value= arrResult[0][8];}catch(ex){};
        try{document.all('AppntHomeFax').value= arrResult[0][9];}catch(ex){};
        //try{document.all('CompanyAddress').value= arrResult[0][10];}catch(ex){};
        try{document.all('AppntGrpZipCode').value= arrResult[0][11];}catch(ex){};
        try{document.all('AppntGrpPhone').value= arrResult[0][12];}catch(ex){};
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
        try{document.all('AppntGrpName').value= arrResult[0][27];}catch(ex){};
        getAddressName('province','AppntProvince','AppntProvinceName');
        getAddressName('city','AppntCity','AppntCityName');
        getAddressName('district','AppntDistrict','AppntDistrictName');
        return;
    }
    if(cCodeName=="GetInsuredAddressNo"){
		
		var sqlid18="BankContCheckSql18";
		var mySql18=new SqlClass();
		mySql18.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql18.setSqlId(sqlid18);//指定使用的Sql的id
		mySql18.addSubPara(fm.InsuredAddressNo.value);//指定传入的参数
		mySql18.addSubPara(fm.InsuredNo.value);//指定传入的参数
	    var strSQL=mySql18.getString();	
		
//        var strSQL="select b.* from LCAddress b where b.AddressNo='"+fm.InsuredAddressNo.value+"' and b.CustomerNo='"+fm.InsuredNo.value+"'";
            arrResult=easyExecSql(strSQL);
        try{document.all('InsuredAddressNo').value= arrResult[0][1];}catch(ex){};
        try{document.all('InsuredAddressNoName').value= arrResult[0][2];}catch(ex){};
        
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
        try{document.all('GrpPhone').value= arrResult[0][12];}catch(ex){};
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
 
	if(cCodeName=="BankAgentCode")
	{     
		var tAgentCode=document.all('AgentCode').value;
		//alert(tAgentCode); 
		
		var sqlid19="BankContCheckSql19";
		var mySql19=new SqlClass();
		mySql19.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql19.setSqlId(sqlid19);//指定使用的Sql的id
		mySql19.addSubPara(tAgentCode);//指定传入的参数
	    var sqlstr=mySql19.getString();	
		
//		var sqlstr="select name,agentgroup,managecom from labranchgroup where agentgroup=(select agentgroup from laagent where agentcode='" + tAgentCode+"')" ;
		arrResult = easyExecSql(sqlstr,1,0);
		if(arrResult==null)
		{
			alert("专管员信息查询出错");
			return false;
		}
		else
		{
			try{document.all('ManageCom').value= arrResult[0][2];}catch(ex){};//管理机构
			try{document.all('BranchAttr').value= arrResult[0][0];}catch(ex){};//营业部、营业组 
			try{document.all('AgentGroup').value= arrResult[0][1];}catch(ex){};//
			showOneCodeName('comcode','ManageCom','ManageComName');
		}

	} 

	afterCodeSelect2( cCodeName, Field );
}

function haveMultiAgent(){
	//alert("aa＝＝"+document.all("multiagentflag").checked);
    if(document.all("multiagentflag").checked)
    {
        DivMultiAgent.style.display="";
    }
    else
    {
        DivMultiAgent.style.display="none";
    }

}

//Muline 表单发佣分配表 parm1
function queryAgentGrid(Field)
{
	//alert("Field=="+Field);
	tField=Field;
	if(document.all('ManageCom').value=="")
	{
		 alert("请先录入管理机构信息！");
		 return;
	}
	if(document.all(Field).all('MultiAgentGrid1').value=="")
	{
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom=&queryflag=1","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0;'+sFeatures);
	}

	if(document.all( Field ).all('MultiAgentGrid1').value != "")	 
	{
    	var cAgentCode = document.all( Field ).all('MultiAgentGrid1').value;  //保单号码
    	
		var sqlid20="BankContCheckSql20";
		var mySql20=new SqlClass();
		mySql20.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql20.setSqlId(sqlid20);//指定使用的Sql的id
		mySql20.addSubPara(cAgentCode);//指定传入的参数
	    var strSql=mySql20.getString();	
		
//    	var strSql = "select AgentCode from LAAgent where AgentCode='" + cAgentCode +"'";
        var arrResult = easyExecSql(strSql);
           //alert(arrResult);
        if (arrResult == null) {
            alert("编码为:["+document.all( Field ).all('MultiAgentGrid1').value+"]的代理人不存在，请确认!");
        }
    }
}

//进行两次输入的校验
function confirmSecondInput1(aObject,aEvent){
	if(aEvent=="onkeyup"){
	  var theKey = window.event.keyCode;
	  if(theKey=="13"){
	    if(theFirstValue!=""){
      	theSecondValue = aObject.value;
	      if(theSecondValue==""){
	      	alert("请再次录入！");
	        aObject.value="";
	        aObject.focus();
	        return;
	      }
	      if(theSecondValue==theFirstValue){
	        aObject.value = theSecondValue;
	        return;
	      }
	      else{
	        alert("两次录入结果不符，请重新录入！");
	        theFirstValue="";
	        theSecondValue="";
	        aObject.value="";
	        aObject.focus();
	        return;
	      }
	    }
      else{
      	theFirstValue = aObject.value;
      	if(theFirstValue==""){
      		theSecondValue="";
      	  alert("请录入内容！");
      	  return;
      	}
      	aObject.value="";
      	aObject.focus();
        return;
      }
    }
  }
  else if(aEvent=="onblur"){
	  //alert("theFirstValue="+theFirstValue);
	  if(theFirstValue!=""){
    	theSecondValue = aObject.value;
	    if(theSecondValue==""){
	    	alert("请再次录入！");
	      aObject.value="";
	      aObject.focus();
	      return;
	    }
	    if(theSecondValue==theFirstValue){
	      aObject.value = theSecondValue;
	      theSecondValue="";
	      theFirstValue="";
	      return;
	    }
	    else{
	      alert("两次录入结果不符，请重新录入！");
	      theFirstValue="";
	      theSecondValue="";
	      aObject.value="";
	      aObject.focus();
	      return;
	    }
	  }
    else{
    	theFirstValue = aObject.value;
     	theSecondValue="";
    	if(theFirstValue==""){
      	//alert("aa");
    	  return;
    	}
    	aObject.value="";
    	aObject.focus();
      return;
    }
  }
}

//查询是否已经添加过投保人
function checkAppnt(){
    var ContNo=document.all("ContNo").value;
	
		var sqlid21="BankContCheckSql21";
		var mySql21=new SqlClass();
		mySql21.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql21.setSqlId(sqlid21);//指定使用的Sql的id
		mySql21.addSubPara(ContNo);//指定传入的参数
	    var sqlstr=mySql21.getString();	
	
//    var sqlstr="select forceuwflag from lccont where contno='"+ContNo+"'" ;
    arrResult = easyExecSql(sqlstr,1,0);
    if(arrResult==null)
    {
        return false;
    }
    else
    {
        return true;
    }

}

//校验投保日期
function checkapplydate()
{
	if(fm.PolAppntDate.value.length==8)
	{
		if(fm.PolAppntDate.value.indexOf('-')==-1)
		{ 
			var Year =     fm.PolAppntDate.value.substring(0,4);
			var Month =    fm.PolAppntDate.value.substring(4,6);
			var Day =      fm.PolAppntDate.value.substring(6,8);
			fm.PolAppntDate.value = Year+"-"+Month+"-"+Day;
			if(Year=="0000"||Month=="00"||Day=="00")
			{
			 alert("您输入的投保日期有误!");
			fm.PolAppntDate.value = "";  
			return;
			}
		}
	}
	else 
	{
		var Year =     fm.PolAppntDate.value.substring(0,4);
		var Month =    fm.PolAppntDate.value.substring(5,7);
		var Day =      fm.PolAppntDate.value.substring(8,10);
		if(Year=="0000"||Month=="00"||Day=="00")
		{
    		alert("您输入的投保日期有误!");
    		fm.PolAppntDate.value = "";
    		return;
		}
	}
		//增加系统对录入的投保日期校验
	if(checkPolDate(fm.ProposalContNo.value,fm.PolAppntDate.value)==false)
	{
    	fm.PolAppntDate.value="";
    	return;
	}

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
	if(calAge(tPolAppntDate)<0)
	{
		alert("投保日期只能为当前日期以前!");
		return false;
	}
	var tMakeDate="";//根据合同号获取录单日期<查询合同表，存在则取之，否则取系统当前日期>
	
			var sqlid22="BankContCheckSql22";
		var mySql22=new SqlClass();
		mySql22.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql22.setSqlId(sqlid22);//指定使用的Sql的id
		mySql22.addSubPara(tContNo);//指定传入的参数
	    var makedatesql=mySql22.getString();	
	
//	var makedatesql="select contno,prtno,makedate from lccont where prtno='"+tContNo+"'";
	var makedatearr=easyExecSql(makedatesql);
	if(makedatearr!=null )
	{
		tMakeDate=makedatearr[0][2];
	}
	else
	{
		
		var sqlid23="BankContCheckSql23";
		var mySql23=new SqlClass();
		mySql23.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql23.setSqlId(sqlid23);//指定使用的Sql的id
//		mySql23.addSubPara(tContNo);//指定传入的参数
	    var sysdatearr=mySql23.getString();	
		
//		var sysdatearr=easyExecSql("select to_date(sysdate) from dual");//取系统当前日期
		tMakeDate=sysdatearr[0][0];
	}
	var Days=dateDiff(tPolAppntDate,tMakeDate,"D");
	if(Days>60 || Days<0)
	{
		var strInfo="投保日期应在录单的系统当期日期60天内。而录单日期（"+tMakeDate+"）—— 投保日期（"+tPolAppntDate+"）== "+Days+" 天";
		strInfo=strInfo+",请重新填写投保日期！！！";
		alert(strInfo);
		return false;
	}
}

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

//查询记事本
function checkNotePad(prtNo,LoadFlag)
{
	var strSQL="select count(*) from LWNotePad where otherno='"+prtNo+"'";
	var arrResult = easyExecSql(strSQL);
	eval("document.all('NotePadButton"+LoadFlag+"').value=' 记事本查看(共"+arrResult[0][0]+"条)'");
}
//在初始化body时自动效验投保人信息，
//判断系统中是否存在姓名、性别、出生日期相同或证件类型证件号码相同的被保人信息。
function AppntChkNew()
{
	var Sex=fm.AppntSex.value;
	
		var sqlid24="BankContCheckSql24";
		var mySql24=new SqlClass();
		mySql24.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql24.setSqlId(sqlid24);//指定使用的Sql的id
		mySql24.addSubPara(fm.AppntName.value);//指定传入的参数
		mySql24.addSubPara(Sex);//指定传入的参数
		mySql24.addSubPara(fm.AppntBirthday.value);//指定传入的参数
		
		mySql24.addSubPara(fm.AppntNo.value);//指定传入的参数
		mySql24.addSubPara(fm.AppntIDNo.value);//指定传入的参数
		mySql24.addSubPara(fm.AppntNo.value);//指定传入的参数
	    var sqlstr=mySql24.getString();	
	
//	var sqlstr="select * from ldperson where Name='"+fm.AppntName.value
//	+ "' and Sex='"+Sex+"' and Birthday='"+fm.AppntBirthday.value
//	+ "' and CustomerNo<>'"+fm.AppntNo.value+"'"
//	+ " union select * from ldperson where 1=1 "
//	+ " and IDNo = '"+fm.AppntIDNo.value
//	+ "' and IDNo is not null and CustomerNo<>'"+fm.AppntNo.value+"'" ;
	arrResult = easyExecSql(sqlstr,1,0);
	if(arrResult==null)
	{
		//disabled"投保人效验"按钮   //判断是否存在重复被保人？
		fm.AppntChkButton.disabled = true;
		//fm.AppntChkButton2.disabled = true;
	}
	else
	{
		fm.AppntChkButton.disabled = "";
		//fm.AppntChkButton2.disabled = "";
	}
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
	
			var sqlid25="BankContCheckSql25";
		var mySql25=new SqlClass();
		mySql25.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql25.setSqlId(sqlid25);//指定使用的Sql的id
		mySql25.addSubPara(tInsuredName);//指定传入的参数
		mySql25.addSubPara(tInsuredSex);//指定传入的参数
		mySql25.addSubPara(tBirthday);//指定传入的参数
		
		mySql25.addSubPara(tInsuredNo);//指定传入的参数
		mySql25.addSubPara(fm.IDNo.value);//指定传入的参数
		mySql25.addSubPara(tInsuredNo);//指定传入的参数
	    var sqlstr=mySql25.getString();	
	
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

/*********************************************************************
 *  清空投保人数据
 *  参数  ：  无
 *  返回值：  无
 **********************************************************************/
function emptyAppnt()
{
    try{document.all('AppntVIPValue').value= ""; }catch(ex){};
    try{document.all('AppntVIPFlagname').value= ""; }catch(ex){};
    try{document.all('AppntName').value= ""; }catch(ex){};
    try{document.all('AppntIDNo').value= ""; }catch(ex){};
    try{document.all('AppntSex').value= ""; }catch(ex){};
    try{document.all('AppntSexName').value= ""; }catch(ex){};
    try{document.all('AppntBirthday').value= ""; }catch(ex){};
    try{document.all('AppntAge').value= ""; }catch(ex){};
    try{document.all('AppntMarriage').value= ""; }catch(ex){};
    try{document.all('AppntMarriageName').value= ""; }catch(ex){};
    try{document.all('AppntNativePlace').value= ""; }catch(ex){};
    try{document.all('AppntNativePlaceName').value= ""; }catch(ex){};
    try{document.all('AppntOccupationCode').value= ""; }catch(ex){};
    try{document.all('AppntOccupationCodeName').value= ""; }catch(ex){};
    try{document.all('AppntLicenseType').value= ""; }catch(ex){};
    try{document.all('AppntLicenseTypeName').value= ""; }catch(ex){};
    try{document.all('AppntAddressNo').value= ""; }catch(ex){};
    try{document.all('AddressNoName').value= ""; }catch(ex){};
    try{document.all('AppntProvince').value= ""; }catch(ex){};
    try{document.all('AppntProvinceName').value= ""; }catch(ex){};
    try{document.all('AppntCity').value= ""; }catch(ex){};
    try{document.all('AppntCityName').value= ""; }catch(ex){};
    try{document.all('AppntDistrict').value= ""; }catch(ex){};
    try{document.all('AppntDistrictName').value= ""; }catch(ex){};
    try{document.all('AppntPostalAddress').value= ""; }catch(ex){};
    try{document.all('AppntZipCode').value= ""; }catch(ex){};
    try{document.all('AppntMobile').value= ""; }catch(ex){};
    try{document.all('AppntGrpPhone').value= ""; }catch(ex){};
    try{document.all('AppntFax').value= ""; }catch(ex){};
    try{document.all('AppntHomePhone').value= ""; }catch(ex){};
    try{document.all('AppntGrpName').value= ""; }catch(ex){};
    try{document.all('AppntEMail').value= ""; }catch(ex){};
    try{document.all('PayMode').value= ""; }catch(ex){};
    try{document.all('FirstPayModeName').value= ""; }catch(ex){};
    try{document.all('AppntBankCode').value= ""; }catch(ex){};
    try{document.all('FirstBankCodeName').value= ""; }catch(ex){};
    try{document.all('AppntAccName').value= ""; }catch(ex){};
    try{document.all('AppntBankAccNo').value= ""; }catch(ex){};
    try{document.all('SecPayMode').value= ""; }catch(ex){};
    try{document.all('PayModeName').value= ""; }catch(ex){};
    try{document.all('SecAppntBankCode').value= ""; }catch(ex){};
    try{document.all('AppntBankCodeName').value= ""; }catch(ex){};
    try{document.all('SecAppntAccName').value= ""; }catch(ex){};
    try{document.all('SecAppntBankAccNo').value= ""; }catch(ex){};
    try{document.all('Income0').value= ""; }catch(ex){};
    try{document.all('IncomeWay0').value= ""; }catch(ex){};
    try{document.all('IncomeWayName0').value= ""; }catch(ex){};
    ImpartGrid.clearData();
	ImpartGrid.addOne();

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
	try{document.all('GrpPhone').value= "";  }catch(ex){};
	try{document.all('GrpAddress').value= ""; }catch(ex){};
	try{document.all('GrpZipCode').value= "";  }catch(ex){};
	ImpartInsuredGrid.clearData();
	PolGrid.clearData();
	LCBnfGrid.clearData();
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
		var Year = fm.AppntBirthday.value.substring(0,4);
		var Month = fm.AppntBirthday.value.substring(5,7);
		var Day =  fm.AppntBirthday.value.substring(8,10);
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
		var Year =fm.Birthday.value.substring(0,4);
		var Month = fm.Birthday.value.substring(5,7);
		var Day = fm.Birthday.value.substring(8,10);
		if(Year=="0000"||Month=="00"||Day=="00")
		{
    		alert("您输入的被保人出生日期有误!");
    		fm.Birthday.value = "";
    		return;
		}
	}
}
//投保人证件号码及类型
function checkidtype()
{
	if(fm.AppntIDType.value=="" && fm.AppntIDNo.value!="")
	{
		alert("请先选择证件类型！");
		fm.AppntIDNo.value="";
	}
}
//被保人证件号码及类型
function checkidtype2()
{
	if(fm.IDType.value=="" && fm.IDNo.value!="")
	{
		alert("请先选择证件类型！");
		fm.IDNo.value="";
    }
}

/****************投保人  根据身份证号取得出生日期和性别**************************/
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
			document.all('AppntBirthday').value=tBirthday;
			document.all('AppntSex').value=tSex;
		}
	}
}

/****************被保人  根据身份证号取得出生日期和性别**************************/
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
			document.all('Birthday').value=tBirthday;
			document.all('Sex').value=tSex;
		}

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
    	fm.AppntGrpPhone.value="";
    	}
		
		var sqlid26="BankContCheckSql26";
		var mySql26=new SqlClass();
		mySql26.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql26.setSqlId(sqlid26);//指定使用的Sql的id
		mySql26.addSubPara(fm.AppntNo.value);//指定传入的参数
	    strsql=mySql26.getString();	
		
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
     document.all('AppntAddressNo').CodeData=tCodeData;
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
     	fm.GrpPhone.value="";
        fm.Fax.value="";
    	fm.HomePhone.value="";
    	fm.EMail.value="";
    }
	
		var sqlid27="BankContCheckSql27";
		var mySql27=new SqlClass();
		mySql27.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql27.setSqlId(sqlid27);//指定使用的Sql的id
		mySql27.addSubPara(fm.InsuredNo.value);//指定传入的参数
	    strsql=mySql27.getString();	
	
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

    var cContNo = document.all('ContNo').value;  //保单号码
    if(cContNo=="" || cContNo=="null")
    {
        alert("尚无合同投保单号，请先保存!");
    }
    else
    {
        window.open("../uw/MSQuestInputMain.jsp?ContNo="+cContNo+"&Flag="+ LoadFlag+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+ActivityID,"window1",sFeatures);
    }
}
//问题件查询
function QuestQuery()
{
	cContNo = document.all('ContNo').value;  //保单号码
	if(cContNo == "" || cContNo=="null")
	{
	    alert("尚无合同投保单号，请先保存!");
	}
	else
	{
	    window.open("../uw/QuestQueryMain.jsp?ContNo="+cContNo+"&Flag="+LoadFlag,"window1",sFeatures);
	}
}

//问题件影像查询
function QuestPicQuery()
{
	var ContNo = fm.ContNo.value;
	if (ContNo == "" || ContNo=="null")
	{
	    alert("尚无合同投保单号，请先保存!");
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
	if(PrtNo == "null" || PrtNo == "")
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
	
		var sqlid28="BankContCheckSql28";
		var mySql28=new SqlClass();
		mySql28.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql28.setSqlId(sqlid28);//指定使用的Sql的id
		mySql28.addSubPara(fm.AppntName.value);//指定传入的参数
		mySql28.addSubPara(Sex);//指定传入的参数
		mySql28.addSubPara(fm.AppntBirthday.value);//指定传入的参数
		
		mySql28.addSubPara(fm.AppntNo.value);//指定传入的参数
		mySql28.addSubPara(fm.AppntIDNo.value);//指定传入的参数
		mySql28.addSubPara(fm.AppntNo.value);//指定传入的参数
	    var sqlstr=mySql28.getString();	
	
//	var sqlstr="select * from ldperson where Name='"+fm.AppntName.value
//	+ "' and Sex='"+Sex+"' and Birthday='"+fm.AppntBirthday.value
//	+ "' and CustomerNo<>'"+fm.AppntNo.value+"'"
//	+ " union select * from ldperson where 1=1 " 
//	+ " and IDNo = '"+fm.AppntIDNo.value
//	+ "' and IDNo is not null and CustomerNo<>'"+fm.AppntNo.value+"'" ;
	arrResult = easyExecSql(sqlstr,1,0);
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
	
		var sqlid29="BankContCheckSql29";
		var mySql29=new SqlClass();
		mySql29.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql29.setSqlId(sqlid29);//指定使用的Sql的id
		mySql29.addSubPara(tInsuredName);//指定传入的参数
		mySql29.addSubPara(tInsuredSex);//指定传入的参数
		mySql29.addSubPara(tBirthday);//指定传入的参数
		
		mySql29.addSubPara(tInsuredNo);//指定传入的参数
		mySql29.addSubPara(fm.IDNo.value);//指定传入的参数
		mySql29.addSubPara(tInsuredNo);//指定传入的参数
	    var sqlstr=mySql29.getString();	
	
//    var sqlstr="select * from ldperson where Name='"+tInsuredName
//            +"' and Sex='"+tInsuredSex+"' and Birthday='"+tBirthday
//            +"' and CustomerNo<>'"+tInsuredNo+"'"
//            +" union select * from ldperson where 1=1 "
//            +" and IDNo = '"+fm.IDNo.value
//            +"' and IDNo is not null and CustomerNo<>'"+tInsuredNo+"'" ;
        arrResult = easyExecSql(sqlstr,1,0);

    if(arrResult==null)
    {
	   alert("没有与该被保人相似的客户,无需校验");
	   return false;
    }

    window.open("../uw/AppntChkMain.jsp?ProposalNo1="+fm.ContNo.value+"&Flag=I&LoadFlag="+LoadFlag+"&InsuredNo="+tInsuredNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+ActivityID,"window1",sFeatures);
}

//强制进入人工核保
function forceUW()
{
	//查询是否已经进行选择强制进入人工核保
    var ContNo=document.all("ContNo").value;
    if (ContNo == "" || ContNo=="null")
	{
	    alert("未查询出合同信息,不容许您进行 [强制进入人工核保] ！");
	    return;
	}
	
	
		var sqlid30="BankContCheckSql30";
		var mySql30=new SqlClass();
		mySql30.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql30.setSqlId(sqlid30);//指定使用的Sql的id
		mySql30.addSubPara(ContNo);//指定传入的参数

	    var sqlstr=mySql30.getString();	
	
    var sqlstr="select forceuwflag from lccont where contno='"+ContNo+"'" ;
    arrResult = easyExecSql(sqlstr,1,0);
    if(arrResult==null){
  	    alert("不存在该投保单！");
    }
    else
    {
        window.open("../uw/ForceUWMain.jsp?ContNo="+ContNo,"window1",sFeatures);
    }
}


/*********************************************************************
 *  保存个人投保单的提交后的操作,服务器数据返回后执行的操作
 ********************************************************************/
function afterSubmit( FlagStr, content,contract )
{
	if( FlagStr == "Fail" )
	{
		showInfo.close();
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
		showInfo.close();
		content = "保存成功！";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		showDiv(operateButton, "true");
		top.opener.easyQueryClickSelf();
		top.close();
	}
	mAction = "";
	
}

/*********************************************************************
 *  显示frmSubmit框架，用来调试
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showSubmitFrame(cDebug)
{
	if( cDebug == "1" )
		parent.fraMain.rows = "0,0,50,82,*";
	else
		parent.fraMain.rows = "0,0,0,72,*";
}

/*********************************************************************
 *  Click事件，当点击增加图片时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function addClick()
{
	//下面增加相应的代码
	showDiv( operateButton, "false" );
	showDiv( inputButton, "true" );
	if( verifyInput2() == false ) return false;
}

/*********************************************************************
 *  Click事件，当点击“查询”图片时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function queryClick()
{
        if(this.ScanFlag == "1"){
	  alert( "有扫描件录入不允许查询!" );
          return false;
        }
	if( mOperate == 0 )
	{
		mOperate = 1;
		cContNo = document.all( 'ContNo' ).value;
		showInfo = window.open("./ContQueryMain.jsp?ContNo=" + cContNo,"",sFeatures);
	}
}

/*********************************************************************
 *  显示div
 *  参数  ：  第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
 *  返回值：  无
 *********************************************************************
 */
function showDiv(cDiv,cShow)
{
	if( cShow == "true" )
		cDiv.style.display = "";
	else
		cDiv.style.display = "none";
}

/*********************************************************************
 *  查询返回明细信息时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
 *  参数  ：  查询返回的二维数组
 *  返回值：  无
 *********************************************************************
 */
function afterQuery( arrQueryResult ) {

	if( arrQueryResult != null ) {
		arrResult = arrQueryResult;

		if( mOperate == 1 ){		// 查询投保单
			document.all( 'ContNo' ).value = arrQueryResult[0][0];
			
		var sqlid31="BankContCheckSql31";
		var mySql31=new SqlClass();
		mySql31.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql31.setSqlId(sqlid31);//指定使用的Sql的id
		mySql31.addSubPara(arrQueryResult[0][0]);//指定传入的参数

	    var sqlstr=mySql31.getString();	
			
			arrResult = easyExecSql(sqlstr, 1, 0);
			//arrResult = easyExecSql("select ProposalContNo,PrtNo,ManageCom,SaleChnl,AgentCode,AgentGroup,AgentCode1,AgentCom,AgentType,Remark from LCCont where LCCont.ProposalContNo = '" + arrQueryResult[0][0] + "'", 1, 0);
			//prompt('',"select ProposalContNo,PrtNo,ManageCom,SaleChnl,AgentCode,AgentGroup,AgentCode1,AgentCom,AgentType,Remark from LCCont where LCCont.ProposalContNo = '" + arrQueryResult[0][0] + "'");
			if (arrResult == null) {
			  alert("未查到投保单信息");
			} else {
			   displayLCContPol(arrResult[0]);
			}
		}

		if( mOperate == 2 )	{		// 投保人信息
			//alert("arrQueryResult[0][0]=="+arrQueryResult[0][0]);
			
			arrResult = easyExecSql(sqlstr, 1, 0);
			
		var sqlid32="BankContCheckSql32";
		var mySql32=new SqlClass();
		mySql32.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql32.setSqlId(sqlid32);//指定使用的Sql的id
		mySql32.addSubPara(arrQueryResult[0][0]);//指定传入的参数

	    var sqlstr32=mySql32.getString();	
			
			arrResult = easyExecSql(sqlstr32, 1, 0);
			//arrResult = easyExecSql("select a.*  from LDPerson a where 1=1  and a.CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0);
			if (arrResult == null) {
			  alert("未查到投保人信息");
			} else {
			   displayAppnt(arrResult[0]);
			}
		}
	}

	mOperate = 0;		// 恢复初态

	showCodeName();

}

/*********************************************************************
 *  查询返回后触发
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterQuery21( arrQueryResult )
{
  //alert("1111here:" + arrQueryResult + "\n" + mOperate);

    if( arrQueryResult != null )
    {
        arrResult = arrQueryResult;

        if( mOperate == 1 )
        {		// 查询投保单
            document.all( 'ContNo' ).value = arrQueryResult[0][0];

		var sqlid33="BankContCheckSql33";
		var mySql33=new SqlClass();
		mySql33.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql33.setSqlId(sqlid33);//指定使用的Sql的id
		mySql33.addSubPara(arrQueryResult[0][0]);//指定传入的参数

	    var sqlstr33=mySql33.getString();	

        arrResult = easyExecSql(sqlstr33, 1, 0);
           // arrResult = easyExecSql("select ProposalContNo,PrtNo,ManageCom,SaleChnl,AgentCode,AgentGroup,AgentCode1,AgentCom,AgentType,Remark from LCCont where LCCont.ProposalContNo = '" + arrQueryResult[0][0] + "'", 1, 0);

            if (arrResult == null)
            {
                alert("未查到投保单信息");
            }
            else
            {
                displayLCContPol(arrResult[0]);
            }
        }

        if( mOperate == 3 )
        {		// 被保人信息
        	//arrResult = easyExecSql("select a.*,b.AddressNo,b.PostalAddress,b.ZipCode,b.HomePhone,b.Mobile,b.EMail,a.GrpNo,b.CompanyPhone,b.CompanyAddress,b.CompanyZipCode from LDPerson a,LCAddress b where 1=1 and a.CustomerNo=b.CustomerNo and a.CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0);
        	
		var sqlid34="BankContCheckSql34";
		var mySql34=new SqlClass();
		mySql34.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql34.setSqlId(sqlid34);//指定使用的Sql的id
		mySql34.addSubPara(arrQueryResult[0][0] );//指定传入的参数

	    var sqlstr34=mySql34.getString();	
			
			arrResult = easyExecSql(sqlstr34, 1, 0);
			//arrResult = easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0);
        	if (arrResult == null)
        	{
        	    alert("未查到投保人信息");
        	}
        	else
        	{
        		 //hl 20050503
        	   // displayAppnt(arrResult[0]);
        	   //alert("arrResult[0][35]=="+arrResult[0][35]);
        	   displayInsuredInfo(arrResult[0]);
        	  // showCodeName();
        	}
        }
    }

	mOperate = 0;		// 恢复初态
	showCodeName();
}

/*********************************************************************
 *  把查询返回的投保人数据显示到投保人部分
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function displayAppnt()
{
	//alert("here in 928 col");
	try{document.all('AppntNo').value= arrResult[0][0]; }catch(ex){};
	try{document.all('AppntName').value= arrResult[0][1]; }catch(ex){};
	try{document.all('AppntSex').value= arrResult[0][2]; }catch(ex){};
	try{document.all('AppntBirthday').value= arrResult[0][3]; }catch(ex){};
	try{document.all('AppntIDType').value= arrResult[0][4]; }catch(ex){};
	try{document.all('AppntIDNo').value= arrResult[0][5]; }catch(ex){};
	try{document.all('AppntPassword').value= arrResult[0][6]; }catch(ex){};
	try{document.all('AppntNativePlace').value= arrResult[0][7]; }catch(ex){};
	try{document.all('AppntNationality').value= arrResult[0][8]; }catch(ex){};
	try{document.all('AppntRgtAddress').value= arrResult[0][9]; }catch(ex){};
	try{document.all('AppntMarriage').value= arrResult[0][10];}catch(ex){};
	try{document.all('AppntMarriageDate').value= arrResult[0][11];}catch(ex){};
	try{document.all('AppntHealth').value= arrResult[0][12];}catch(ex){};
	try{document.all('AppntStature').value= arrResult[0][13];}catch(ex){};
	try{document.all('AppntAvoirdupois').value= arrResult[0][14];}catch(ex){};
	try{document.all('AppntDegree').value= arrResult[0][15];}catch(ex){};
	try{document.all('AppntCreditGrade').value= arrResult[0][16];}catch(ex){};
	try{document.all('AppntOthIDType').value= arrResult[0][17];}catch(ex){};
	try{document.all('AppntOthIDNo').value= arrResult[0][18];}catch(ex){};
	try{document.all('AppntICNo').value= arrResult[0][19];}catch(ex){};
	try{document.all('AppntGrpNo').value= arrResult[0][20];}catch(ex){};
	try{document.all('AppntJoinCompanyDate').value= arrResult[0][21];}catch(ex){};
	try{document.all('AppntStartWorkDate').value= arrResult[0][22];}catch(ex){};
	try{document.all('AppntPosition').value= arrResult[0][23];}catch(ex){};
	try{document.all('AppntSalary').value= arrResult[0][24];}catch(ex){};
	try{document.all('AppntOccupationType').value= arrResult[0][25];}catch(ex){};
	try{document.all('AppntOccupationCode').value= arrResult[0][26];}catch(ex){};
	try{document.all('AppntWorkType').value= arrResult[0][27];}catch(ex){};
	try{document.all('AppntPluralityType').value= arrResult[0][28];}catch(ex){};
	try{document.all('AppntDeathDate').value= arrResult[0][29];}catch(ex){};
	try{document.all('AppntSmokeFlag').value= arrResult[0][30];}catch(ex){};
	try{document.all('AppntBlacklistFlag').value= arrResult[0][31];}catch(ex){};
	try{document.all('AppntProterty').value= arrResult[0][32];}catch(ex){};
	//try{document.all('AppntRemark').value= arrResult[0][33];}catch(ex){};
	try{document.all('AppntState').value= arrResult[0][34];}catch(ex){};
	try{document.all('AppntVIPValue').value= arrResult[0][35];}catch(ex){};
	try{document.all('AppntOperator').value= arrResult[0][36];}catch(ex){};
	try{document.all('AppntMakeDate').value= arrResult[0][37];}catch(ex){};
	try{document.all('AppntMakeTime').value= arrResult[0][38];}catch(ex){};
	try{document.all('AppntModifyDate').value= arrResult[0][39];}catch(ex){};
	try{document.all('AppntModifyTime').value= arrResult[0][40];}catch(ex){};
	try{document.all('AppntGrpName').value= arrResult[0][41];}catch(ex){};
	//alert(document.all('AppntGrpName').value);
	try{document.all('AppntLicenseType').value= arrResult[0][43];}catch(ex){};
	
	
	//顺便将投保人地址信息等进行初始化
	try{document.all('AppntPostalAddress').value= "";}catch(ex){}; 
	try{document.all('AppntPostalAddress').value= "";}catch(ex){};
	try{document.all('AppntZipCode').value= "";}catch(ex){};
	try{document.all('AppntPhone').value= "";}catch(ex){};
	try{document.all('AppntFax').value= "";}catch(ex){};
	try{document.all('AppntMobile').value= "";}catch(ex){};
	try{document.all('AppntEMail').value= "";}catch(ex){};
	//try{document.all('AppntGrpName').value= "";}catch(ex){};
	try{document.all('AppntHomeAddress').value= "";}catch(ex){};
	try{document.all('AppntHomeZipCode').value= "";}catch(ex){};
	try{document.all('AppntHomePhone').value= "";}catch(ex){};
	try{document.all('AppntHomeFax').value= "";}catch(ex){};
	try{document.all('AppntGrpPhone').value= "";}catch(ex){};
	try{document.all('CompanyAddress').value= "";}catch(ex){};
	try{document.all('AppntGrpZipCode').value= "";}catch(ex){};
	try{document.all('AppntGrpFax').value= "";}catch(ex){};
}

/**
 *投保单详细内容显示
 */
function displayLCCont() {
	  //alert("aaa");
    try { document.all('GrpContNo').value = arrResult[0][0]; } catch(ex) { };
    try { document.all('ContNo').value = arrResult[0][1]; } catch(ex) { };
    try { document.all('ProposalContNo').value = arrResult[0][2]; } catch(ex) { };
    try { document.all('PrtNo').value = arrResult[0][3]; } catch(ex) { };
    try { document.all('ContType').value = arrResult[0][4]; } catch(ex) { };
    try { document.all('FamilyType').value = arrResult[0][5]; } catch(ex) { };
    try { document.all('FamilyID').value = arrResult[0][6]; } catch(ex) { };
    try { document.all('PolType ').value = arrResult[0][7]; } catch(ex) { };
    try { document.all('CardFlag').value = arrResult[0][8]; } catch(ex) { };
    try { document.all('ManageCom').value = arrResult[0][9]; } catch(ex) { };
    try { document.all('ExecuteCom ').value = arrResult[0][10]; } catch(ex) { };
    try { document.all('AgentCom').value = arrResult[0][11]; } catch(ex) { };
    try { document.all('AgentCode').value = arrResult[0][12]; } catch(ex) { };
    try { document.all('AgentGroup').value = arrResult[0][13]; } catch(ex) { }; 
    try { document.all('AgentCode1 ').value = arrResult[0][14]; } catch(ex) { };
//    try { document.all('AgentType').value = arrResult[0][15]; } catch(ex) { }; 
    try { document.all('SaleChnl').value = arrResult[0][16]; } catch(ex) { };
    try { document.all('Handler').value = arrResult[0][17]; } catch(ex) { };
    try { document.all('Password').value = arrResult[0][18]; } catch(ex) { };
    try { document.all('AppntNo').value = arrResult[0][19]; } catch(ex) { };
    try { document.all('AppntName').value = arrResult[0][20]; } catch(ex) { };
    try { document.all('AppntSex').value = arrResult[0][21]; } catch(ex) { };
    try { document.all('AppntBirthday ').value = arrResult[0][22]; } catch(ex) { };
    try { document.all('AppntIDType').value = arrResult[0][23]; } catch(ex) { };
    try { document.all('AppntIDNo').value = arrResult[0][24]; } catch(ex) { };
    try { document.all('InsuredNo').value = arrResult[0][25]; } catch(ex) { };
    try { document.all('InsuredName').value = arrResult[0][26]; } catch(ex) { };
    try { document.all('InsuredSex').value = arrResult[0][27]; } catch(ex) { };
    try { document.all('InsuredBirthday').value = arrResult[0][28]; } catch(ex) { };
    try { document.all('InsuredIDType ').value = arrResult[0][29]; } catch(ex) { };
    try { document.all('InsuredIDNo').value = arrResult[0][30]; } catch(ex) { };
    try { document.all('PayIntv').value = arrResult[0][31]; } catch(ex) { };
    try { document.all('SecPayMode').value = arrResult[0][32]; } catch(ex) { };
    try { document.all('PayLocation').value = arrResult[0][33]; } catch(ex) { };
    try { document.all('DisputedFlag').value = arrResult[0][34]; } catch(ex) { };
    try { document.all('OutPayFlag').value = arrResult[0][35]; } catch(ex) { };
    try { document.all('GetPolMode').value = arrResult[0][36]; } catch(ex) { };
    try { document.all('SignCom').value = arrResult[0][37]; } catch(ex) { };
    try { document.all('SignDate').value = arrResult[0][38]; } catch(ex) { };
    try { document.all('SignTime').value = arrResult[0][39]; } catch(ex) { };
    try { document.all('ConsignNo').value = arrResult[0][40]; } catch(ex) { };
    try { document.all('SecAppntBankCode').value = arrResult[0][41]; } catch(ex) { };
    try { document.all('SecAppntBankAccNo').value = arrResult[0][42]; } catch(ex) { };
    try { document.all('SecAppntAccName').value = arrResult[0][43]; } catch(ex) { };
    try { document.all('PrintCount').value = arrResult[0][44]; } catch(ex) { };
    try { document.all('LostTimes').value = arrResult[0][45]; } catch(ex) { };
    try { document.all('Lang').value = arrResult[0][46]; } catch(ex) { };
    try { document.all('Currency').value = arrResult[0][47]; } catch(ex) { };
   // alert('qq'+arrResult[0][48]);
    try { document.all('Remark').value = arrResult[0][48]; } catch(ex) { };
    try { document.all('Peoples ').value = arrResult[0][49]; } catch(ex) { };
    try { document.all('Mult').value = arrResult[0][50]; } catch(ex) { };
    try { document.all('Prem').value = arrResult[0][51]; } catch(ex) { };
    try { document.all('Amnt').value = arrResult[0][52]; } catch(ex) { };
    try { document.all('SumPrem').value = arrResult[0][53]; } catch(ex) { };
    try { document.all('Dif').value = arrResult[0][54]; } catch(ex) { };
    try { document.all('PaytoDate').value = arrResult[0][55]; } catch(ex) { };
    try { document.all('FirstPayDate').value = arrResult[0][56]; } catch(ex) { };
    try { document.all('CValiDate').value = arrResult[0][57]; } catch(ex) { };
    try { document.all('InputOperator ').value = arrResult[0][58]; } catch(ex) { };
    try { document.all('InputDate').value = arrResult[0][59]; } catch(ex) { };
    try { document.all('InputTime').value = arrResult[0][60]; } catch(ex) { };
    try { document.all('ApproveFlag').value = arrResult[0][61]; } catch(ex) { };
    try { document.all('ApproveCode').value = arrResult[0][62]; } catch(ex) { };
    try { document.all('ApproveDate').value = arrResult[0][63]; } catch(ex) { };
    try { document.all('ApproveTime').value = arrResult[0][64]; } catch(ex) { };
    try { document.all('UWFlag').value = arrResult[0][65]; } catch(ex) { };
    try { document.all('UWOperator').value = arrResult[0][66]; } catch(ex) { };
    try { document.all('UWDate').value = arrResult[0][67]; } catch(ex) { };
    try { document.all('UWTime').value = arrResult[0][68]; } catch(ex) { };
    try { document.all('AppFlag').value = arrResult[0][69]; } catch(ex) { };
    try { document.all('PolAppntDate').value = arrResult[0][70]; } catch(ex) { };
    try { document.all('GetPolDate').value = arrResult[0][71]; } catch(ex) { };
    try { document.all('GetPolTime').value = arrResult[0][72]; } catch(ex) { };
    try { document.all('CustomGetPolDate').value = arrResult[0][73]; } catch(ex) { };
    try { document.all('State').value = arrResult[0][74]; } catch(ex) { };
    try { document.all('Operator').value = arrResult[0][75]; } catch(ex) { };
    try { document.all('MakeDate').value = arrResult[0][76]; } catch(ex) { };
    try { document.all('MakeTime').value = arrResult[0][77]; } catch(ex) { };
    try { document.all('ModifyDate').value = arrResult[0][78]; } catch(ex) { };
    try { document.all('ModifyTime').value = arrResult[0][79]; } catch(ex) { };
    try { document.all('SellType').value = arrResult[0][87]; } catch(ex) { };

    try { document.all('AppntBankCode').value = arrResult[0][90]; } catch(ex) { };
    try { document.all('AppntBankAccNo').value = arrResult[0][91]; } catch(ex) { };
    try { document.all('AppntAccName').value = arrResult[0][92]; } catch(ex) { };
    try { document.all('PayMode').value = arrResult[0][93]; } catch(ex) { };

}

/*********************************************************************
 *  把查询返回的客户数据显示到投保人部分
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function displayAccount()
{
	try{document.all('AppntBankAccNo').value= arrResult[0][24]; }catch(ex){};
	try{document.all('AppntBankCode').value= arrResult[0][23]; }catch(ex){};
	try{document.all('AppntAccName').value= arrResult[0][25]; }catch(ex){};

}
/*********************************************************************
 *  把查询返回的客户数据显示到投保人部分
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function displayCustomer()
{
	try{document.all('AppntNationality').value= arrResult[0][8]; }catch(ex){};
}
/*********************************************************************
 *  把查询返回的客户地址数据显示到投保人部分
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function displayAddress()
{
	try{document.all('AppntAddressNo').value= arrResult[0][0];}catch(ex){};
	try{document.all('AppntPostalAddress').value= arrResult[0][1];}catch(ex){};
	try{document.all('AppntZipCode').value= arrResult[0][2];}catch(ex){};
	try{document.all('AppntPhone').value= arrResult[0][3];}catch(ex){};
	try{document.all('AppntMobile').value= arrResult[0][4];}catch(ex){};
	try{document.all('AppntEMail').value= arrResult[0][5];}catch(ex){};
	try{document.all('AppntGrpPhone').value= arrResult[0][6];}catch(ex){};
	try{document.all('CompanyAddress').value= arrResult[0][7];}catch(ex){};
	try{document.all('AppntGrpZipCode').value= arrResult[0][8];}catch(ex){};
}

/*********************************************************************
 *  把查询返回的合同中被保人数据显示到页面
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function displayContAppnt()
{
  try{document.all('AppntGrpContNo').value= arrResult[0][0];}catch(ex){};
  try{document.all('AppntContNo').value= arrResult[0][1];}catch(ex){};
  try{document.all('AppntPrtNo').value= arrResult[0][2];}catch(ex){};
  try{document.all('AppntNo').value= arrResult[0][3];}catch(ex){};
  try{document.all('AppntGrade').value= arrResult[0][4];}catch(ex){};
  try{document.all('AppntName').value= arrResult[0][5];}catch(ex){};
  try{document.all('AppntSex').value= arrResult[0][6];}catch(ex){};
  try{document.all('AppntBirthday').value= arrResult[0][7];}catch(ex){};
  try{document.all('AppntType').value= arrResult[0][8];}catch(ex){};
  try{document.all('AppntAddressNo').value= arrResult[0][9]; }catch(ex){};
  try{document.all('AppntIDType').value= arrResult[0][10]; }catch(ex){};
  try{document.all('AppntIDNo').value= arrResult[0][11]; }catch(ex){};
  try{document.all('AppntNativePlace').value= arrResult[0][12];}catch(ex){};
  try{document.all('AppntNationality').value= arrResult[0][13];}catch(ex){};
  try{document.all('AppntRgtAddress').value= arrResult[0][14];}catch(ex){};
  try{document.all('AppntMarriage').value= arrResult[0][15];}catch(ex){};
  try{document.all('AppntMarriageDate').value= arrResult[0][16];}catch(ex){};
  try{document.all('AppntHealth').value= arrResult[0][17];}catch(ex){};
  try{document.all('AppntStature').value= arrResult[0][18];}catch(ex){};
  try{document.all('AppntAvoirdupois').value= arrResult[0][19];}catch(ex){};
  try{document.all('AppntDegree').value= arrResult[0][20];}catch(ex){};
  try{document.all('AppntCreditGrade').value= arrResult[0][21];}catch(ex){};
  try{document.all('AppntBankCode').value= arrResult[0][22];}catch(ex){};
  try{document.all('AppntBankAccNo').value= arrResult[0][23];}catch(ex){};
  try{document.all('AppntAccName').value= arrResult[0][24]; }catch(ex){};
  try{document.all('AppntJoinCompanyDate').value= arrResult[0][25];}catch(ex){};
  try{document.all('AppntStartWorkDate').value= arrResult[0][26];}catch(ex){};
  try{document.all('AppntPosition').value= arrResult[0][27];}catch(ex){};
  try{document.all('AppntSalary').value= arrResult[0][28]; }catch(ex){};
  try{document.all('AppntOccupationType').value= arrResult[0][29];}catch(ex){};
  try{document.all('AppntOccupationCode').value= arrResult[0][30];}catch(ex){};
  try{document.all('AppntWorkType').value= arrResult[0][31]; }catch(ex){};
  try{document.all('AppntPluralityType').value= arrResult[0][32];}catch(ex){};
  try{document.all('AppntSmokeFlag').value= arrResult[0][33];}catch(ex){};
  try{document.all('AppntOperator').value= arrResult[0][34];}catch(ex){};
  try{document.all('AppntManageCom').value= arrResult[0][35];}catch(ex){};
  try{document.all('AppntMakeDate').value= arrResult[0][36];}catch(ex){};
  try{document.all('AppntMakeTime').value= arrResult[0][37]; }catch(ex){};
  try{document.all('AppntModifyDate').value= arrResult[0][38];}catch(ex){};
  try{document.all('AppntModifyTime').value= arrResult[0][39];}catch(ex){};
}

//使得从该窗口弹出的窗口能够聚焦
function myonfocus()
{
	if(showInfo!=null)
	{
	  try
	  {
	    showInfo.focus();
	  }  
	  catch(ex)
	  {
	    showInfo=null;
	  }
	}
}

//投保人客户号查询按扭事件
function queryAppntNo() {

	if (document.all("AppntNo").value == "" && loadFlag == "1")
	{
    showAppnt1();
  }
  else if (loadFlag != "1" && loadFlag != "2")
  {
     alert("只能在投保单录入时进行操作！");
  }
  else
  {
  	
		var sqlid35="BankContCheckSql35";
		var mySql35=new SqlClass();
		mySql35.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql35.setSqlId(sqlid35);//指定使用的Sql的id
		mySql35.addSubPara(arrQueryResult[0][0] );//指定传入的参数

	    var sqlstr35=mySql35.getString();	
	
	 arrResult = easyExecSql(sqlstr35, 1, 0);
   // arrResult = easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo = '" + document.all("AppntNo").value + "'", 1, 0);
    if (arrResult == null) {
      alert("未查到投保人信息");
      displayAppnt(new Array());
      emptyUndefined();
    }
    else
    {
      displayAppnt(arrResult[0]);
    }
  }
}

function showAppnt1()
{
	//alert("here in 1115 row");
	if( mOperate == 0 )
	{
		mOperate = 2;
		showInfo = window.open( "../sys/LDPersonQueryNewMain.jsp?queryFlag=queryAppnt" ,"",sFeatures);
	}
}

//问题件录入
function QuestInput2()
{ 
	cContNo = fm.ContNo.value;  //保单号码
	if(cContNo == "")
	{
		alert("尚无合同投保单号，请先保存!");
	}
	else
	{
		window.open("../uw/QuestInputMain.jsp?ContNo="+cContNo+"&Flag="+ LoadFlag,"window1",sFeatures);
	}
}

/********************************************************************
 *  代码框汉化
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showCodeName()
{

	showAppntCodeName();
	showContCodeName();
	showInsuredCodeName();
}

/*********************************************************************
 *  合同代码框汉化
 *  参数  ：  无
 *  返回值：  无
 **********************************************************************/
 function showContCodeName()
{
	quickGetName('comcode',fm.ManageCom,fm.ManageComName);
	quickGetName('Relation',fm.RelationToInsured,fm.RelationToInsuredName);
	quickGetName('sellType',fm.SellType,fm.sellTypeName);
	//tongmeng 2007-09-10 modify
	//去掉应行编码查询
	//quickGetName('HeadOffice',fm.AgentBankCode,fm.AgentBankCodeName);
}

/*********************************************************************
 *  投保人代码框汉化
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showAppntCodeName()
{

	quickGetName('vipvalue',fm.AppntVIPValue,fm.AppntVIPFlagname);
	quickGetName('IDType',fm.AppntIDType,fm.AppntIDTypeName);
	quickGetName('Sex',fm.AppntSex,fm.AppntSexName);
	quickGetName('Marriage',fm.AppntMarriage,fm.AppntMarriageName);
	quickGetName('NativePlace',fm.AppntNativePlace,fm.AppntNativePlaceName);
	quickGetName('OccupationCode',fm.AppntOccupationCode,fm.AppntOccupationCodeName);
	quickGetName('occupationtype',fm.AppntOccupationType,fm.AppntOccupationTypeName);
	quickGetName('LicenseType',fm.AppntLicenseType,fm.AppntLicenseTypeName);
	getAddressName('province','AppntProvince','AppntProvinceName');
	getAddressName('city','AppntCity','AppntCityName');
	getAddressName('district','AppntDistrict','AppntDistrictName');
	quickGetName('paymode',fm.PayMode,fm.FirstPayModeName);
	quickGetName('continuepaymode',fm.SecPayMode,fm.PayModeName);
	quickGetName('bank',fm.AppntBankCode,fm.FirstBankCodeName);
	quickGetName('bank',fm.SecAppntBankCode,fm.AppntBankCodeName);
}

/*********************************************************************
 *  被保人代码框汉化
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showInsuredCodeName()
{
	quickGetName('SequenceNo',fm.SequenceNo,fm.SequenceNoName);
	quickGetName('vipvalue',fm.VIPValue1,fm.AppntVIPFlagname1);
	quickGetName('Relation',fm.RelationToMainInsured,fm.RelationToMainInsuredName);
	quickGetName('Relation',fm.RelationToAppnt,fm.RelationToAppntName);
	quickGetName('IDType',fm.IDType,fm.IDTypeName);
	quickGetName('Sex',fm.Sex,fm.SexName);
	quickGetName('Marriage',fm.Marriage,fm.MarriageName);
	quickGetName('OccupationCode',fm.OccupationCode,fm.OccupationCodeName);
	quickGetName('occupationtype',fm.OccupationType,fm.OccupationTypeName);
	quickGetName('NativePlace',fm.NativePlace,fm.NativePlaceName);
	quickGetName('LicenseType',fm.LicenseType,fm.LicenseTypeName);
	getAddressName('province','InsuredProvince','InsuredProvinceName');
	getAddressName('city','InsuredCity','InsuredCityName');
	getAddressName('district','InsuredDistrict','InsuredDistrictName');
	quickGetName('incomeway',fm.IncomeWay,fm.IncomeWayName);
}

//根据地址代码查询地址汉字信息,查询地址代码表<ldaddress>
//所需参数,地址级别<province--省;city--市;district--区/县;>,地址代码<代码表单名>,地址汉字信息<汉字表单名>
//返回,直接为--地址汉字信息<汉字表单名>--赋值
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
		
		var sqlid36="BankContCheckSql36";
		var mySql36=new SqlClass();
		mySql36.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql36.setSqlId(sqlid36);//指定使用的Sql的id
		mySql36.addSubPara(PlaceCode );//指定传入的参数
		mySql36.addSubPara(PlaceType);//指定传入的参数

	    var strSQL=mySql36.getString();	
		
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


/**
* 根据代码选择的代码查找并显示名称，显示指定的一个
* strCode - 代码选择的代码
* showObjCode - 代码存放的界面对象
* showObjName - 要显示名称的界面对象
*/
function quickGetName(strCode, showObjc, showObjn) {
	var formsNum = 0;	//窗口中的FORM数
	var elementsNum = 0;	//FORM中的元素数
	var urlStr = "";
	var sFeatures = "";
	var strQueryResult = "";	//代码选择的查询结果集
	var arrCode = null;	//拆分数组
	var strCodeValue = "";	//代码值
	var cacheFlag = false;	//内存中有数据标志
	var showObjn;
	var showObjc;
	//遍历所有FORM


	//如果代码栏的数据不为空，才查询，否则不做任何操作
	if (showObjc.value != "")
	{
		//寻找主窗口
		if(cacheWin==null)
		{
			 cacheWin = searchMainWindow(this);
			if (cacheWin == false) { cacheWin = this; }
		}

		//如果内容中有数据，从内容中取数据
		if(strCode!='OccupationCode')
		{
			if (cacheWin.parent.VD.gVCode.getVar(strCode))
			{
				arrCode = cacheWin.parent.VD.gVCode.getVar(strCode);
				cacheFlag = true;
			}
			else 
			{
				urlStr = "../common/jsp/CodeQueryWindow.jsp?codeType=" + strCode;
				sFeatures = "status:no;help:0;close:0;dialogWidth:150px;dialogHeight:0px;dialogLeft:-1;dialogTop:-1;resizable=1";
				//连接数据库进行CODE查询，返回查询结果给strQueryResult
				//strQueryResult = window.showModalDialog(urlStr, "", sFeatures);
				var name='提示';   //网页名称，可为空; 
				var iWidth=150;      //弹出窗口的宽度; 
				var iHeight=0;     //弹出窗口的高度; 
				var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
				var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
				showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

				showInfo.focus();
			}
		}
		else if(strCode=='OccupationCode')
		{
			
					var sqlid37="BankContCheckSql37";
		var mySql37=new SqlClass();
		mySql37.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql37.setSqlId(sqlid37);//指定使用的Sql的id
		mySql37.addSubPara(showObjc.value );//指定传入的参数

	    var tSQL=mySql37.getString();	
			
//				var tSQL = "select trim(OccupationCode), trim(OccupationName), trim(OccupationType),  "
//				         + " (select codename from ldcode where codetype='occupationtype'  and trim(code)=trim(OccupationType)) from LDOccupation "
//				         + " where 1 = 1and worktype = 'GR' and OccupationCode='"+showObjc.value+"' order by OccupationCode ";
				strQueryResult = easyQueryVer3(tSQL, 1, 0, 1);        
				         
		}
		//拆分成数组
		try {
			if (!cacheFlag)
			{
				try
				{
					arrCode = decodeEasyQueryResult(strQueryResult);
				}
				catch(ex)
				{
					alert("页面缺少引用EasyQueryVer3.js");
				}
				strCodeSelect = "";
				for (i=0; i<arrCode.length; i++)
				{
					strCodeSelect = strCodeSelect + "<option value=" + arrCode[i][0] + ">";
					strCodeSelect = strCodeSelect + arrCode[i][0] + "-" + arrCode[i][1];
					strCodeSelect = strCodeSelect + "</option>";
				}
				//将拆分好的数据放到内存中
				cacheWin.parent.VD.gVCode.addArrVar(strCode, "", arrCode);
				//无论是否有数据从服务器端得到,都设置该变量
				cacheWin.parent.VD.gVCode.addVar(strCode+"Select","",strCodeSelect);
			}
			cacheFlag = false;
			for (i=0; i<arrCode.length; i++)
			{
				if (showObjc.value == arrCode[i][0])
				{
					showObjn.value = arrCode[i][1];
					break;
				}
			}
		}
		catch(ex)
		{}
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
	
	var arrPolAppntDate = PolAppntDate.split("-");
	if (arrPolAppntDate[1].length == 1) arrPolAppntDate[1] = "0" + arrBirthDate[1];
	if (arrPolAppntDate[2].length == 1) arrPolAppntDate[2] = "0" + arrBirthDate[2];

	if(arrPolAppntDate[0]<=99)
	{
		arrBirthDate[0]=	arrBirthDate[0]-1900;
	}
	age = arrPolAppntDate[0] - arrBirthDate[0] - 1;
	
	//当前月大于出生月
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
		alert("出生日期只能为当前日期以前!");
		return;
	}
	fm.AppntAge.value=calPolAppntAge(fm.AppntBirthday.value,fm.PolAppntDate.value);
  	return ;
}

//被保人年龄龄<被保人年龄应该为投保日期与生日之差;2005-11-18修
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

/*********************************************************************
 *  保存集体投保单的提交 
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function submitForm()
{  
	if(fm.PolAppntDate.value.length!=10 || fm.PolAppntDate.value.substring(4,5)!='-' || fm.PolAppntDate.value.substring(7,8)!='-' || fm.PolAppntDate.value.substring(0,1)=='0')
	{
		alert("请输入正确的日期格式！");
		fm.PolAppntDate.focus();
		return;
    }

	var tSql = "select now() from dual";
	turnPage.strQueryResult = easyQueryVer3(tSql, 1, 0, 1);
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	    
	var tSysDate = turnPage.arrDataCacheSet[0][0];    

	if(fm.PolAppntDate.value>tSysDate)
	{
		alert("投保日期应该小于当前日期")
		return ;
	}

	if(fm.AppntIDType.value!=""&&fm.AppntIDNo.value==""||fm.AppntIDType.value==""&&fm.AppntIDNo.value!=""){
		alert("证件类型和证件号码必须同时填写或不填");
		return false;
		}

    //hanlin 由于校验的问题，暂时去掉，回头再加上，hl 20050502
    //校验已经加上 hl 20050512
    if( verifyInputNB("1") == false ) return false;

	/*如果是符合修改时使用，执行更新操作 20041125 wzw*/
	if(LoadFlag=="3"){
	  updateClick();
	  return;
	}

    if(document.all('PayMode').value=='7'){

    }
    if(document.all('SecPayMode').value=='7'){

    }
   //alert(fm.Remark.value);

	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  
	if( mAction == "" )
	{
		//showSubmitFrame(mDebug);
		getdetailwork();
		mAction = "INSERT";
		document.all( 'fmAction' ).value = mAction;
		ImpartGrid.delBlankLine();

		if (document.all('ProposalContNo').value == "") {
		  //alert("查询结果只能进行修改操作！");
		  mAction = "";
		} else {
		  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		  var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();

		  //保全调用会传2过来，否则默认为0，将付值于保单表中的appflag字段
		  tAction = fm.action;
		  //alert("taction==="+tAction);
		  fm.action = fm.action + "?BQFlag=" + BQFlag;
		  //alert("fm==="+fm.action);
		  fm.submit(); //提交
		  fm.action = tAction;
		}
	}
}

/*********************************************************************
 *  Click事件，当点击“修改”图片时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function updateClick()
{
	
  if(fm.AppntIDType.value!=""&&fm.AppntIDNo.value==""||fm.AppntIDType.value==""&&fm.AppntIDNo.value!=""){
	  alert("证件类型和证件号码必须同时填写或不填");
	  return false;
	}
  if((fm.PayMode.value=="4"||fm.PayMode.value=="7")&&fm.AppntBankCode.value==""&&fm.AppntAccName.value==""&&fm.AppntBankAccNo.value=="")
	{

		alert("请填入首期转帐开户行、首期帐户姓名、首期帐户号！");
		return false;
	}
  if(fm.SecPayMode.value=="3"&&fm.SecAppntBankCode.value==""&&fm.SecAppntAccName.value==""&&fm.SecAppntBankAccNo.value=="")
	{

		alert("请填入续期转帐开户行、续期帐户姓名、续期帐户号！");
		return false;
	}
	
	if(fm.AppntNo.value==''&&fm.AppntAddressNo.value!='')
    {
        alert("投保人客户号为空，不能有地址号码");
        return false;
    }
    if( verifyInputNB("1") == false ) return false;
	var tGrpProposalNo = "";
	tGrpProposalNo = document.all( 'ProposalContNo' ).value;
	if( tGrpProposalNo == null || tGrpProposalNo == "" )
		alert( "请先做投保单查询操作，再进行修改!" );
	else
	{
		ImpartGrid.delBlankLine();
		var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;

		if( mAction == "" )
		{
			//showSubmitFrame(mDebug);
			getdetailwork();
			//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
			mAction = "UPDATE";
			document.all( 'fmAction' ).value = mAction;
			fm.action="BankContSave.jsp"; 
			fm.submit(); //提交
		}
	}
}

function getdetail()
{
	//alert("fm.AppntNo.value=="+fm.AppntNo.value);
	
		var sqlid38="BankContCheckSql38";
		var mySql38=new SqlClass();
		mySql38.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql38.setSqlId(sqlid38);//指定使用的Sql的id
		mySql38.addSubPara(fm.AppntBankAccNo.value );//指定传入的参数
        mySql38.addSubPara(fm.AppntNo.value );//指定传入的参数
	    var strSql=mySql38.getString();	
	
//	var strSql = "select BankCode,AccName from LCAccount where BankAccNo='" + fm.AppntBankAccNo.value+"' and customerno='"+fm.AppntNo.value+"'";
	var arrResult = easyExecSql(strSql);
	if (arrResult != null)
	{
		fm.AppntBankCode.value = arrResult[0][0];
		fm.AppntAccName.value = arrResult[0][1];
	}
	else
	{
	     //fm.AppntBankCode.value = '';
	     //fm.AppntAccName.value = '';
	}
}

function getdetailwork()
{
	
			var sqlid39="BankContCheckSql39";
		var mySql39=new SqlClass();
		mySql39.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql39.setSqlId(sqlid39);//指定使用的Sql的id
		mySql39.addSubPara(fm.AppntOccupationCode.value );//指定传入的参数
	    var strSql=mySql39.getString();	
	
//  var strSql = "select OccupationType from LDOccupation where OccupationCode='" + fm.AppntOccupationCode.value+"'";
  var arrResult = easyExecSql(strSql);
  if (arrResult != null) 
  {
    fm.AppntOccupationType.value = arrResult[0][0];
	quickGetName('occupationtype',fm.AppntOccupationType,fm.AppntOccupationTypeName);
  }
  else
  {
    fm.AppntOccupationType.value ="";
    fm.AppntOccupationTypeName.value ="";
  }
}

/*********************************************************************
 *  Click事件，当点击“删除”图片时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function deleteClick()
{
	var tGrpProposalNo = "";
	var tProposalContNo = "";
	if(LoadFlag==1)
	{
		tGrpProposalNo = document.all( 'GrpContNo' ).value;
	if( tGrpProposalNo == null || tGrpProposalNo == "" )
		alert( "请先做投保单查询操作，再进行删除!" );
	else
	{
		var showStr = "正在删除数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;

		if( mAction == "" )
		{
			//showSubmitFrame(mDebug);
			//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
			mAction = "DELETE";
			document.all( 'fmAction' ).value = mAction;
			fm.action="BankContSave.jsp"; 
			fm.submit(); //提交
		}
	 }
      }
      //top.close();
}

/*********************************************************************
 *  添加被保险人
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function addRecord()
{ 
	fm.SequenceNo.value="1";
	fm.RelationToMainInsured.value="00";
	//判断是否已经添加过投保人，没有的话不允许添加被保人
	if(!checkAppnt()){
	  alert("请先添加合同信息及投保人信息！");
	  fm.AppntName.focus();
	  return;
	}

  //2005.03.18 chenhq 对此进行修改
  if(LoadFlag==1){

	 if(fm.SamePersonFlag.checked==true&&fm.RelationToAppnt.value!="00")
	  {
	   alert("与投保人关系只能选择本人！");
	   fm.RelationToAppnt.value="00";
	   fm.RelationToAppntName.value="本人";
	   return ;
	  }
	  var tPrtNo=document.all("PrtNo").value;

		var sqlid40="BankContCheckSql40";
		var mySql40=new SqlClass();
		mySql40.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql40.setSqlId(sqlid40);//指定使用的Sql的id
		mySql40.addSubPara(tPrtNo );//指定传入的参数
	    var sqlstr=mySql40.getString();	

//	  var sqlstr="select SequenceNo from LCInsured where PrtNo='"+tPrtNo+"'";

    arrResult=easyExecSql(sqlstr,1,0);

    if(arrResult!=null){
      for(var sequencenocout=0; sequencenocout<arrResult.length;sequencenocout++ )
      {
        if(fm.SequenceNo.value==arrResult[sequencenocout][0]){
	        alert("已经存在该客户内部号");
	        fm.SequenceNo.focus();
	        return false;
	      }
	    }
	  }
	}

  //2005.03.18 chenhq 对此进行修改
  if(LoadFlag==3){

	  var tPrtNo=document.all("PrtNo").value;

		var sqlid41="BankContCheckSql41";
		var mySql41=new SqlClass();
		mySql41.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql41.setSqlId(sqlid41);//指定使用的Sql的id
		mySql41.addSubPara(tPrtNo );//指定传入的参数
	    var sqlstr=mySql41.getString();	

	  //var sqlstr="select SequenceNo from LCInsured where PrtNo='"+tPrtNo+"'";

    arrResult=easyExecSql(sqlstr,1,0);

    if(arrResult!=null){
      for(var sequencenocout=0; sequencenocout<arrResult.length;sequencenocout++ )
      {
        if(fm.SequenceNo.value==arrResult[sequencenocout][0]){
	        alert("已经存在该客户内部号");
	        fm.SequenceNo.focus();
	        return false;
	      }
	    }
	  }
	}


  //2005.03.18 chenhq 对此进行修改
  if(LoadFlag==5){

	  var tPrtNo=document.all("PrtNo").value;

		var sqlid42="BankContCheckSql42";
		var mySql42=new SqlClass();
		mySql42.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql42.setSqlId(sqlid42);//指定使用的Sql的id
		mySql42.addSubPara(tPrtNo );//指定传入的参数
	    var sqlstr=mySql42.getString();	

//	  var sqlstr="select SequenceNo from LCInsured where PrtNo='"+tPrtNo+"'";

    arrResult=easyExecSql(sqlstr,1,0);

    if(arrResult!=null){
      for(var sequencenocout=0; sequencenocout<arrResult.length;sequencenocout++ )
      {
        if(fm.SequenceNo.value==arrResult[sequencenocout][0]){
	        alert("已经存在该客户内部号");
	        fm.SequenceNo.focus();
	        return false;
	      }
	    }
	  }
	}


  //2005.03.18 chenhq 对此进行修改
  if(LoadFlag==6){

	  var tPrtNo=document.all("PrtNo").value;

		var sqlid43="BankContCheckSql43";
		var mySql43=new SqlClass();
		mySql43.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql43.setSqlId(sqlid43);//指定使用的Sql的id
		mySql43.addSubPara(tPrtNo );//指定传入的参数
	    var sqlstr=mySql43.getString();	

//	  var sqlstr="select SequenceNo from LCInsured where PrtNo='"+tPrtNo+"'";

    arrResult=easyExecSql(sqlstr,1,0);

    if(arrResult!=null){
      for(var sequencenocout=0; sequencenocout<arrResult.length;sequencenocout++ )
      {
        if(fm.SequenceNo.value==arrResult[sequencenocout][0]){
	        alert("已经存在该客户内部号");
	        fm.SequenceNo.focus();
	        return false;
	      }
	    }
	  }
	}


  if (document.all('PolTypeFlag').value==0)
  {
    if( verifyInputNB('2') == false ) return false;
  }

  if(document.all('IDType').value=="0")
  {
    var strChkIdNo = chkIdNo(trim(document.all('IDNo').value),trim(document.all('Birthday').value),trim(document.all('Sex').value));
    if (strChkIdNo != "")
    {
      alert(strChkIdNo);
	    return false;
    }
  }

  if(!checkself()) return false;

  if(!checkrelation()) return false;

  if(ImpartGrid.checkValue2(ImpartGrid.name,ImpartGrid)== false) return false;

  //if(ImpartDetailGrid.checkValue2(ImpartDetailGrid.name,ImpartDetailGrid)== false)return false;
	ImpartGrid.delBlankLine();
	//  ImpartDetailGrid.delBlankLine();
	//alert("fm.AddressNo.value=="+fm.InsuredAddressNo.value);
  if(fm.InsuredNo.value==''&&fm.InsuredAddressNo.value!='')
  {
    alert("被保险人客户号为空，不能有地址号码");
    return false;
  }

  //hanlin 20050504
  fm.action="BankContInsuredSave.jsp";
	getdetailwork2();
  //end hanlin
  document.all('ContType').value=ContType;
  document.all( 'BQFlag' ).value = BQFlag;
  document.all('fmAction').value="INSERT||CONTINSURED";
  fm.submit();
}

/*********************************************************************
 *  修改被选中的被保险人
 *  此处添加被保人修改校验，如果修改了关键信息<姓名、性别、证件类型或号码、出生日期>或者 职业类别，
 *  则需先删除险种信息，因为这可能会变更客户号或重新计算保费
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function modifyRecord()
{
	if (InsuredGrid.mulLineCount==0)
	{
		alert("该被保险人还没有保存，无法进行修改！");
		return false;
	}
	var tselno=InsuredGrid.getSelNo();
	if(tselno==0)
	{
		alert("请选中需要修改被保人记录");
		return false;
	}
	var tOldInsuredNo=InsuredGrid.getRowColData(tselno-1,1);
	
		var sqlid44="BankContCheckSql44";
		var mySql44=new SqlClass();
		mySql44.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql44.setSqlId(sqlid44);//指定使用的Sql的id
		mySql44.addSubPara(fm.ContNo.value );//指定传入的参数
		mySql44.addSubPara(tOldInsuredNo );//指定传入的参数
	    var SSQL=mySql44.getString();	
	
//	var SSQL=" select t.insuredno,t.name,t.sex,t.birthday,t.idtype,t.idno,t.occupationcode"
//		+" from lcinsured t where t.contno='"+fm.ContNo.value+"' and t.insuredno='"+tOldInsuredNo+"'";
	var tOldArr=easyExecSql(SSQL,1,0);
	//获取准备修改的信息,同查询出的信息比较，如果有一项不同，那么查询是否有险种，有则提示需先删除险种信息
	if( fm.Name.value!=tOldArr[0][1] || fm.IDType.value!=tOldArr[0][4] || fm.IDNo.value!=tOldArr[0][5] 
	  || fm.Sex.value!=tOldArr[0][2] || fm.Birthday.value!=tOldArr[0][3] || fm.OccupationCode.value!=tOldArr[0][6])
	{
		var sqlstr="select polno from lcpol where contno='"+fm.ContNo.value+"' and insuredno='"+tOldInsuredNo+"' ";
		arrResult=easyExecSql(sqlstr,1,0);
		if(arrResult!=null)
		{
			alert("你可能修改了该被保人关键信息<姓名、性别、证件类型或号码、出生日期>或职业类别。请先删除该被保人下的险种信息。");
			return false;    
		}
	}	
	if(fm.SamePersonFlag.checked==true&&fm.RelationToAppnt.value!="00")
	{
		alert("与投保人关系只能选择本人！");
		fm.RelationToAppnt.value="00";
		fm.RelationToAppntName.value="本人";
		return ;
	}
    if (document.all('PolTypeFlag').value==0)
    {
        if( verifyInputNB('2') == false ) return false;
    }
    //校验被保险人与主被保险人关系
    if(!checkself()) { return false;}
    if (fm.InsuredNo.value==''&&fm.InsuredAddressNo.value!='')
    {
        alert("被保险人客户号为空，不能有地址号码");
        return false;
    }
    if(ImpartGrid.checkValue2(ImpartGrid.name,ImpartGrid)== false)
    {
      return false;
    }
	ImpartGrid.delBlankLine();
	getdetailwork2();
	document.all('ContType').value=ContType;
	document.all('fmAction').value="UPDATE||CONTINSURED";
	fm.action="BankContInsuredSave.jsp";
	fm.submit();
}
/*********************************************************************
 *  删除被选中的被保险人
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
 function deleteRecord()
{
    if (fm.InsuredNo.value=='')
    {
        alert("请选中需要删除的客户！")
        return false;
    }
    if (InsuredGrid.mulLineCount==0)
    {
        alert("该被保险人还没有保存，无法进行修改！");
        return false;
    }
     if (fm.InsuredNo.value==''&&fm.InsuredAddressNo.value!='')
    {
        alert("被保险人客户号为空，不能有地址号码");
        return false;
    }
	
		var sqlid45="BankContCheckSql45";
		var mySql45=new SqlClass();
		mySql45.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql45.setSqlId(sqlid45);//指定使用的Sql的id
		mySql45.addSubPara(fm.ContNo.value );//指定传入的参数
	    var sqlstr=mySql45.getString();	
	
//    var sqlstr="select polno from  lcpol where contNo='"+fm.ContNo.value+"'";
    arrResult=easyExecSql(sqlstr,1,0);
    
    if(arrResult!=null)
    {
    	alert("请先删除被保人险种信息！");
        return false;    
    }
    document.all('ContType').value=ContType;
    document.all('fmAction').value="DELETE||CONTINSURED";
    fm.action="BankContInsuredSave.jsp";
    fm.submit();
}

/*********************************************************************
 *  查询职业类别
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function getdetailwork2()
{
	
			var sqlid46="BankContCheckSql46";
		var mySql46=new SqlClass();
		mySql46.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql46.setSqlId(sqlid46);//指定使用的Sql的id
		mySql46.addSubPara(fm.OccupationCode.value );//指定传入的参数
	    var strSql=mySql46.getString();
	
//    var strSql = "select OccupationType from LDOccupation where OccupationCode='" + fm.OccupationCode.value+"'";
    var arrResult = easyExecSql(strSql);
    if (arrResult != null)
    {
        fm.OccupationType.value = arrResult[0][0];
        showOneCodeName('occupationtype','OccupationType','OccupationTypeName');
    }
    else
    {
        fm.OccupationType.value = "";
        fm.OccupationTypeName.value = "";
    }
}


/*********************************************************************
 *  进入险种信息录入
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function intoRiskInfo()
{
  
	//把合同信息和被保人信息放入内存
	mSwitch = parent.VD.gVSwitch;  //桢容错
	putCont();   //注意该函数在BankContInput.js中
	if(fm.InsuredNo.value==""||fm.ContNo.value==""||InsuredGrid.mulLineCount=="0")
	{
		alert("请先添加，选择被保人");
		return false;
	}
	delInsuredVar();
	addInsuredVar();

	try{mSwitch.deleteVar('PolNo')}catch(ex){};
	try{mSwitch.addVar('PolNo','',fm.SelPolNo.value);}catch(ex){}
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
  
  	parent.fraInterface.window.location = "./ProposalInput.jsp?LoadFlag=" + LoadFlag+"&ContType="+ContType+"&scantype=" + scantype+ "&MissionID=" + MissionID+ "&SubMissionID=" + SubMissionID+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&ActivityID="+ActivityID+"&NoType="+NoType+"&hh=1&checktype="+checktype+"&ProposalContNo="+fm.ProposalContNo.value+"&ScanFlag="+ScanFlag+"&BankFlag=1"+"&InsuredChkFlag="+document.all('InsuredChkButton').disabled+"&AppntChkFlag="+document.all('AppntChkButton').disabled;
}

/*********************************************************************
 *  把合同信息放入内存
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function putCont()
{
	delContVar();
	addIntoCont();
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
//    alert(fm.Remark.value);
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
	//try { mSwitch.addVar('AppntRemark','',fm.AppntRemark.value); } catch(ex) { };
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
	try { mSwitch.deleteVar  ('AppntPluralityType');} catch(ex) { };
	try { mSwitch.deleteVar  ('AppntDeathDate'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntSmokeFlag'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntBlacklistFlag'); } catch(ex) { };
	try { mSwitch.deleteVar  ('AppntProterty'); } catch(ex) { };
	//try { mSwitch.deleteVar  ('AppntRemark'); } catch(ex) { };
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
    try{mSwitch.addVar('PluralityType','',fm.PluralityType.value);}catch(ex){};
    try{mSwitch.addVar('SmokeFlag','',fm.SmokeFlag.value);}catch(ex){};
    try{mSwitch.addVar('ContPlanCode','',fm.ContPlanCode.value);}catch(ex){};
    try{mSwitch.addVar('Operator','',fm.Operator.value);}catch(ex){};
    try{mSwitch.addVar('MakeDate','',fm.MakeDate.value);}catch(ex){};
    try{mSwitch.addVar('MakeTime','',fm.MakeTime.value);}catch(ex){};
    try{mSwitch.addVar('ModifyDate','',fm.ModifyDate.value);}catch(ex){};
    try{mSwitch.addVar('ModifyTime','',fm.ModifyTime.value);}catch(ex){};

}

/*********************************************************************
 *  进入险种计划界面
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
 function grpRiskPlanInfo()
{
    var newWindow = window.open("../app/GroupRiskPlan.jsp","",sFeatures);
}
/*********************************************************************
 *  代码选择后触发时间
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
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
		        param="121";
		        fm.pagename.value="121";
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
            document.all('Phone').value= document.all('GrpPhone').value;

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

/*********************************************************************
 *  根据家庭单类型，隐藏界面控件
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function choosetype(){
	if(fm.FamilyType.value=="1")
	divTempFeeInput.style.display="";
	if(fm.FamilyType.value=="0")
	divTempFeeInput.style.display="none";
}
/*********************************************************************
 *  校验被保险人与主被保险人关系
 *  参数  ：  无
 *  返回值：  true or false
 *********************************************************************
 */
function checkself()
{
	if(fm.FamilyType.value=="0"&&fm.RelationToMainInsured.value=="")
	{
	  fm.RelationToMainInsured.value="00";
	  return true;
  }
	else if(fm.FamilyType.value=="0"&&fm.RelationToMainInsured.value!="00")
	{
	  alert("个人单中'与主被保险人关系'只能是'本人'");
	  fm.RelationToMainInsured.value="00";
	  return false;
  }
	else if(fm.FamilyType.value=="1"&&fm.RelationToMainInsured.value==""&&InsuredGrid.mulLineCount==0)
	{
	  fm.RelationToMainInsured.value="00";
	  return true;
  }
  else if(fm.FamilyType.value=="1"&&fm.RelationToMainInsured.value!="00"&&InsuredGrid.mulLineCount==0)
  {
	  alert("家庭单中第一位被保险人的'与主被保险人关系'只能是'本人'");
	  fm.RelationToMainInsured.value="00";
	  return false;
  }
  else{
    return true;
  }
}
/*********************************************************************
 *  校验保险人
 *  参数  ：  无
 *  返回值：  true or false
 *********************************************************************
 */
function checkrelation()
{
	if(LoadFlag==2||LoadFlag==7)
	{
        if (document.all('ContNo').value != "")
        {
            alert("团单的个单不能有多被保险人");
            return false;
        }
        else
            return true;
    }
    else
    {
        if (document.all('ContNo').value != ""&&fm.FamilyType.value=="0")
        {
            var strSQL="select * from LCInsured where contno='"+document.all('ContNo').value +"'";
            turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
            if(turnPage.strQueryResult)
            {
                alert("个单不能有多被保险人");
                return false;
            }
            else
                return true;
        }
        else if (document.all('ContNo').value != ""&&fm.FamilyType.value=="1"&&InsuredGrid.mulLineCount>0&&fm.RelationToMainInsured.value=="00")
        {
            alert("家庭单只能有一个主被保险人");
            return false;
        }
        else if (document.all('ContNo').value != ""&&fm.FamilyType.value=="1"&&fm.RelationToAppnt.value=="00")
        {
            var strSql="select * from LCInsured where contno='"+document.all('ContNo').value +"' and RelationToAppnt='00' ";
            turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1);
            if(turnPage.strQueryResult)
		    {
                alert("投保人已经是该合同号下的被保险人");
                return false;
            }
		    else
		        return true;
        }
        else
            return true;
    }
	//select count(*) from ldinsured

}
/*********************************************************************
 *  投保人与被保人相同选择框事件
 *  参数  ：  无
 *  返回值：  true or false
 *********************************************************************
 */
function isSamePerson()
{
    //对应未选同一人，又打钩的情况
//    alert(fm.AppntGrpName.value);
    if ( fm.SamePersonFlag.checked==true&&fm.RelationToAppnt.value!="00")
    {
      DivLCInsured.style.display = "none";
      divLCInsuredPerson.style.display = "none";
      divSalary.style.display = "none";
      fm.SamePersonFlag.checked = true;
      fm.RelationToAppnt.value="00"
      displayissameperson();
    }
    //对应是同一人，又打钩的情况
    else if (fm.SamePersonFlag.checked == true)
    {
      document.all('DivLCInsured').style.display = "none";
      divLCInsuredPerson.style.display = "none";
      divSalary.style.display = "none";

      displayissameperson();
    }
    //对应不选同一人的情况
    else if (fm.SamePersonFlag.checked == false)
    {
      document.all('DivLCInsured').style.display = "";
      divLCInsuredPerson.style.display = "";
      divSalary.style.display = "";
      try{document.all('InsuredNo').value="";}catch(ex){};
      try{document.all('Name').value=""; }catch(ex){};
      try{document.all('Sex').value= ""; }catch(ex){};
      try{document.all('Birthday').value= ""; }catch(ex){};
      try{document.all('IDType').value= "0"; }catch(ex){};
      try{document.all('IDNo').value= ""; }catch(ex){};
      try{document.all('Password').value= ""; }catch(ex){};
      try{document.all('NativePlace').value= ""; }catch(ex){};
      try{document.all('Nationality').value=""; }catch(ex){};
      try{document.all('RgtAddress').value= ""; }catch(ex){};
      try{document.all('Marriage').value= "";}catch(ex){};
      try{document.all('MarriageDate').value= "";}catch(ex){};
      try{document.all('Health').value= "";}catch(ex){};
      try{document.all('Stature').value= "";}catch(ex){};
      try{document.all('Avoirdupois').value= "";}catch(ex){};
      try{document.all('Degree').value= "";}catch(ex){};
      try{document.all('CreditGrade').value= "";}catch(ex){};
      try{document.all('OthIDType').value= "";}catch(ex){};
      try{document.all('OthIDNo').value= "";}catch(ex){};
      try{document.all('ICNo').value="";}catch(ex){};
      try{document.all('GrpNo').value= "";}catch(ex){};
      try{document.all('JoinCompanyDate').value= "";}catch(ex){}
      try{document.all('StartWorkDate').value= "";}catch(ex){};
      try{document.all('Position').value= "";}catch(ex){};
      try{document.all('Salary').value= "";}catch(ex){};
      try{document.all('OccupationType').value= "";}catch(ex){};
      try{document.all('OccupationCode').value= "";}catch(ex){};
      try{document.all('WorkType').value= "";}catch(ex){};
      try{document.all('PluralityType').value= "";}catch(ex){};
      try{document.all('DeathDate').value= "";}catch(ex){};
      try{document.all('SmokeFlag').value= "";}catch(ex){};
      try{document.all('BlacklistFlag').value= "";}catch(ex){};
      try{document.all('Proterty').value= "";}catch(ex){};
      try{document.all('Remark').value= "";}catch(ex){};
      try{document.all('State').value= "";}catch(ex){};
      try{document.all('Operator').value= "";}catch(ex){};
      try{document.all('MakeDate').value= "";}catch(ex){};
      try{document.all('MakeTime').value="";}catch(ex){};
      try{document.all('ModifyDate').value= "";}catch(ex){};
      try{document.all('ModifyTime').value= "";}catch(ex){};
      try{document.all('PostalAddress').value= "";}catch(ex){};
      try{document.all('PostalAddress').value= "";}catch(ex){};
      try{document.all('ZipCode').value= "";}catch(ex){};
      try{document.all('Phone').value= "";}catch(ex){};
      try{document.all('Mobile').value= "";}catch(ex){};
      try{document.all('EMail').value="";}catch(ex){};
      try{document.all('GrpName').value= "";}catch(ex){};
      try{document.all('GrpPhone').value= "";}catch(ex){};
      try{document.all('GrpAddress').value="";}catch(ex){};
      try{document.all('GrpZipCode').value= "";}catch(ex){};
      try{document.all('RelationToAppnt').value= "";}catch(ex){};
      try{document.all('Fax').value= "";}catch(ex){};
      try{document.all('HomePhone').value= "";}catch(ex){};
	  try{document.all('InsuredAddressNo').value="";}catch(ex){};
	　try{document.all('InsuredProvince').value= "";}catch(ex){};
	　try{document.all('InsuredCity').value="";}catch(ex){};
	　try{document.all('InsuredDistrict').value="";}catch(ex){};
      try{document.all('IncomeWay').value=  "";}catch(ex){};
      try{document.all('Income').value=  "";}catch(ex){};

    }
}
/*********************************************************************
 *  投保人客户号查询按扭事件
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function queryInsuredNo()
{
    if (document.all("InsuredNo").value == "")
    {
      //showAppnt1();
      showInsured1();
    }
    else
    {
        //arrResult = easyExecSql("select a.*,b.AddressNo,b.PostalAddress,b.ZipCode,b.HomePhone,b.Mobile,b.EMail,a.GrpNo,b.CompanyPhone,b.CompanyAddress,b.CompanyZipCode from LDPerson a,LCAddress b where 1=1 and a.CustomerNo=b.CustomerNo and a.CustomerNo = '" + document.all("InsuredNo").value + "'", 1, 0);
      
	  	var sqlid47="BankContCheckSql47";
		var mySql47=new SqlClass();
		mySql47.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql47.setSqlId(sqlid47);//指定使用的Sql的id
		mySql47.addSubPara(fm.InsuredNo.value );//指定传入的参数
	    var strSql=mySql47.getString();
	  
	  arrResult = easyExecSql(strSql, 1, 0);
	    //arrResult = easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo = '" + document.all("InsuredNo").value + "'", 1, 0);
        if (arrResult == null)
        {
          alert("未查到被保人信息");
          //displayAppnt(new Array());
          displayInsuredInfo(new Array());
          emptyUndefined();
        }
        else
        {
          //displayAppnt(arrResult[0]);
          displayInsuredInfo(arrResult[0]);
        }
    }
}

function showInsured1()
{
	if( mOperate == 0 )
	{
		//mOperate = 2;
		mOperate = 3;  //被保人信息查询　mOperate = 3; hl20050503
		showInfo = window.open( "../sys/LDPersonQueryNewMain.jsp?queryFlag=queryInsured" ,"",sFeatures);
	}
}

/*********************************************************************
 *  返回上一页面
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function returnparent()
{
  	//alert("LoadFlag=="+LoadFlag);
  	var backstr=document.all("ContNo").value;

	mSwitch.addVar("PolNo", "", backstr);
	mSwitch.updateVar("PolNo", "", backstr);
	try
	{
	    mSwitch.deleteVar('ContNo');
	}
	catch(ex){};
	if(LoadFlag=="1"||LoadFlag=="3")
	{
		//alert(document.all("PrtNo").value);
  	location.href="../app/ContInput.jsp?LoadFlag="+ LoadFlag + "&prtNo=" + document.all("PrtNo").value;
  }
  if(LoadFlag=="5"||LoadFlag=="25")
	{
		//alert(document.all("PrtNo").value);
    location.href="../app/ContInput.jsp?LoadFlag="+ LoadFlag + "&prtNo=" + document.all("PrtNo").value;
  }

	if(LoadFlag=="2")
	{
    location.href="../app/ContGrpInsuredInput.jsp?LoadFlag="+ LoadFlag + "&polNo=" + document.all("GrpContNo").value+"&scantype="+scantype;
  }
	else if (LoadFlag=="6")
	{
	    location.href="ContInput.jsp?LoadFlag="+ LoadFlag + "&ContNo=" + backstr+"&prtNo="+document.all("PrtNo").value;
	    return;
	}
	else if (LoadFlag=="7")
	{
    location.href="../bq/GEdorTypeNI.jsp?BQFlag="+BQFlag;
	  return;
	}
	else if(LoadFlag=="4"||LoadFlag=="16"||LoadFlag=="13"||LoadFlag=="14"||LoadFlag=="23")
	{
	  if(Auditing=="1")
	  {
	  	top.close();
	  }
	  else
	  {
	    mSwitch.deleteVar("PolNo");
      parent.fraInterface.window.location = "../app/ContGrpInsuredInput.jsp?LoadFlag="+LoadFlag+"&scantype="+scantype;
	  }
	}
	else if (LoadFlag=="99")
	{
	  location.href="ContPolInput.jsp?LoadFlag="+ LoadFlag+"&scantype="+scantype;
	  return;
	}
/*    else
    {
        location.href="ContInput.jsp?LoadFlag="+ LoadFlag;
	}  针对	各种情况的不同暂不支持else方式
*/
}

function DelRiskInfo()
{
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
	var tRow = PolGrid.getSelNo() - 1;
	var tpolno=PolGrid.getRowColData(tRow,1)
	document.all('fmAction').value="DELETE||INSUREDRISK";
	fm.action="./DelIsuredRisk.jsp?polno="+tpolno;
	fm.submit(); //提交

}

/*********************************************************************
 *  把合同所有信息录入结束确认
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function inputConfirm()
{
    if(document.all('ProposalContNo').value == "" || document.all('ProposalContNo').value == "null")
    {
   	    alert("未查询出合同信息,不容许您进行 [复核完毕] 确认！");
   	    return;
    }
	fm.WorkFlowFlag.value = "0000001001";					//复核完毕
	fm.MissionID.value = tMissionID;
	fm.SubMissionID.value = tSubMissionID;
	approvefalg="2";
	
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
	
		 var sqlid48="BankContCheckSql48";
		var mySql48=new SqlClass();
		mySql48.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql48.setSqlId(sqlid48);//指定使用的Sql的id
		mySql48.addSubPara(tContNo );//指定传入的参数
	    var tempfeeSQL=mySql48.getString();
	
//	var tempfeeSQL="select sum(nvl(paymoney,0)) from ljtempfee where tempfeetype='1' and confdate is null "
//		+" and otherno=(select contno from lccont where prtno= '"+tContNo+"')";
	var TempFeeArr=easyExecSql(tempfeeSQL);	
	if(TempFeeArr!=null)
	{
		tTempFee=TempFeeArr[0][0];
	}
	//查询系统生成的保费
	
		var sqlid49="BankContCheckSql49";
		var mySql49=new SqlClass();
		mySql49.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql49.setSqlId(sqlid49);//指定使用的Sql的id
		mySql49.addSubPara(tContNo );//指定传入的参数
	    var premfeeSQL=mySql49.getString();
	
//	var premfeeSQL="select sum(nvl(prem,0)) from lcpol where 1=1 "
//		+" and contno=(select contno from lccont where prtno= '"+tContNo+"')";	
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

function exitAuditing(){
  if(confirm("确认该投保单的复核界面？")){
    top.close();
  }
  else{
    return;
  }
}


function exitAuditing2(){
  if(confirm("确认离开该投保单问题件修改界面？"))
  {
    top.close();
  }
  else{
    return;
  }
}

//控制界面按钮的明暗显示
function ctrlButtonDisabled(tContNo,tLoadFlag){
  var tSQL = "";
  var arrResult;
  var arrButtonAndSQL = new Array();
  
  if(tLoadFlag==5){
  	
			var sqlid50="BankContCheckSql50";
		var mySql50=new SqlClass();
		mySql50.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql50.setSqlId(sqlid50);//指定使用的Sql的id
		mySql50.addSubPara(tContNo );//指定传入的参数
	    var premfeeSQL=mySql50.getString();
	
    arrButtonAndSQL[0] = new Array();
    arrButtonAndSQL[0][0] = "ApproveQuestQuery";
    arrButtonAndSQL[0][1] = "问题件查询";
    arrButtonAndSQL[0][2] =premfeeSQL;// "select * from lcissuepol where 2 = 2 and OperatePos in ('0', '1', '5', 'A', 'I') and ContNo ='"+tContNo+"'";
  }


  for(var i=0; i<arrButtonAndSQL.length; i++){
    if(arrButtonAndSQL[i][2]!=null&&arrButtonAndSQL[i][2]!=""){
      arrResult = easyExecSql(arrButtonAndSQL[i][2]);
      if(arrResult!=null){
        eval("document.all('"+arrButtonAndSQL[i][0]+"').disabled=''");	
      }
      else{
        eval("document.all('"+arrButtonAndSQL[i][0]+"').disabled='true'");	
      }
    }
    else{
      continue;
    }	
  }
  	
}

//------------------------Beg
//Add By Chenrong
//Date:2006.5.12

//查询操作员随动速度
function initQueryRollSpeed()
{
	
		var sqlid51="BankContCheckSql51";
		var mySql51=new SqlClass();
		mySql51.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql51.setSqlId(sqlid51);//指定使用的Sql的id
//		mySql51.addSubPara(tContNo );//指定传入的参数
	    var strSQL=mySql51.getString();
	
//    var strSQL = "select SYSVAR,SYSVARVALUE from LDSysvar where SYSVAR like 'ROLLSPEED%25' order by SYSVAR ASC";
	var arrSpeed = easyExecSql(strSQL); 
	if (arrSpeed != null)
	{
    	fm.RollSpeedBase.value = arrSpeed[0][1];
    	fm.RollSpeedSelf.value = arrSpeed[1][1];
	}


		var sqlid51="BankContCheckSql51";
		var mySql51=new SqlClass();
		mySql51.setResourceName("app.BankContCheckSql"); //指定使用的properties文件名
		mySql51.setSqlId(sqlid51);//指定使用的Sql的id
		mySql51.addSubPara(fm.Operator.value );//指定传入的参数
	    strSQL=mySql51.getString();

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
		"ProposalContNo", "PolAppntDate",
		"ManageCom", "AgentCom", 
		"AgentCode", "BranchAttr","CounterCode","Remark",
		"AppntName", "AppntIDType", "AppntIDNo", "AppntSex", "AppntBirthday", 
		"AppntAge", "AppntOccupationCode", "AppntOccupationType", 
		"AppntProvince", "AppntCity", "AppntDistrict", "AppntPostalAddress", "AppntZipCode", 
		"AppntMobile","AppntGrpPhone", 
		"AppntBankCode", "AppntAccName", "AppntBankAccNo", 
		"Name", "IDType", "IDNo", "Sex", "Birthday", 
		"InsuredAppAge","RelationToAppnt","OccupationCode", "OccupationType", 
		"InsuredProvince", "InsuredCity", "InsuredDistrict", "PostalAddress", "ZIPCODE", 
		"GrpPhone",
		"RiskButton", "BnfButton"
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
	  var tAppntName = document.all('AppntName').value ;
	  var tAppntNo = document.all('AppntNo').value ;
    	var strReturn="1";
    	sFeatures = "";
    	window.open("./CustomerMergeMain.jsp?ScanFlag=1&LoadFlag=1&prtNo="+ContNo+"&AppntName="+tAppntName+"&AppntNo="+tAppntNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&CustomerType=A;"+sFeatures);        
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
    	window.open("./CustomerMergeMain.jsp?ScanFlag=1&LoadFlag=1&prtNo="+ContNo+"&InsuredNo="+tInsuredNo+"&InsuredName="+tInsuredName+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&CustomerType=I;"+sFeatures);        
    //}
}

function UnionConfirm(){

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
	fm.action = "./UnionConfirmSave.jsp";
	fm.submit(); //提交
}

function afterSubmit2( FlagStr, content )
{
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
		top.opener.easyQueryClick();// 刷新页面
		top.opener.easyQueryClickSelf();// 刷新页面
		top.opener.easyQueryClick();// 刷新页面
		top.opener.easyQueryClickSelf();// 刷新页面
		window.close();
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
