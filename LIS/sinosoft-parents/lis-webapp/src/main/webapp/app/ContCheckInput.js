//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
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


//��ʼ��ʱ����ѯ��ͬ��Ϣ��Ͷ������Ϣ(����������Ϣ)����������Ϣ(������֪��Ϣ)��������Ϣ������ʾ�ڽ���
function initQuery()
{
	initQueryCont();//����ʼ��ʱ��ѯ��ͬ��Ϣ��-----������������Ϣ
	initqueryAppnt();//����ѯͶ������Ϣ������������Ͷ���˵ĵ�ַ��Ϣ
	initqueryAccount();//���ɷ���Ϣ��ѯ��------��ѯ�����Ľɷ���Ϣ�������ɷѷ�ʽ���ʺ���Ϣ�ȡ�
	initQueryAgentImpartGrid();//��ѯ ҵ��Ա��֪��Ϣ
    initQueryAppntImpartInfo();	//��ѯͶ���˸�֪��Ϣ
	initqueryInsuredInfo();//����ѯ��������Ϣ��----�����б�
	
	if (LoadFlag == "5" && scantype == "scan")
	    initQueryRollSpeed();


	getInsuredInfo();//�����˻�����Ϣ����ַ��Ϣ

	getInsuredImpartInfo();//�����˸�֪��Ϣ
	
	getInsuredPolInfo();//������������Ϣ
	getInputFieldName();	//����ҳ�������Ĵ���������֣�����ʾ������
	//�ж�ϵͳ���Ƿ�����������Ա𡢳���������ͬ��֤������֤��������ͬ��Ͷ������Ϣ��
  
     AppntChkNew();
	 // �ڳ�ʼ��bodyʱ�Զ�Ч�鱻������Ϣ
 
     InsuredChkNew();
     displayInsuredButton();
	//�жϼ��±��еļ�¼����
	
	checkNotePad(prtNo,LoadFlag);
	//��ť��ѡ�ж�
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

//����ʼ��ʱ��ѯ��ͬ��Ϣ��
function initQueryCont()
{
	var sSQL="";
	if(document.all('ContNo').value=="")
	{
		
	    var sqlid1="ContCheckInputSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("app.ContCheckInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
	    var sSQL=mySql1.getString();	
		
//		var sSQL="select contno,proposalcontno,prtno,polapplydate,selltype,salechnl,managecom,agentcode,cvalidate,"
//			+"OutPayFlag,AutoPayFlag,RnewFlag,GetPolMode,agentcom,signname,FirstTrialDate"
//			+" from lccont where prtno='"+fm.PrtNo.value+"' ";
	}
	else
	{
		
		var sqlid2="ContCheckInputSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("app.ContCheckInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(fm.ContNo.value);//ָ������Ĳ���
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
	queryAgent();//��ѯ ��������Ϣ
	//��ѯ��������Ͷ���˹�ϵ
	
		var sqlid3="ContCheckInputSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("app.ContCheckInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
		mySql3.addSubPara(fm.ContNo.value);//ָ������Ĳ���
		mySql3.addSubPara(fm.AgentCode.value);//ָ������Ĳ���
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
//��ѯ ��������Ϣ
function queryAgent()
{
	if(document.all('AgentCode').value != "")
	{
		var cAgentCode = fm.AgentCode.value;  //��������
		//���ҵ��Ա���볤��Ϊ8���Զ���ѯ����Ӧ�Ĵ������ֵ���Ϣ
		//if(cAgentCode.length!=8){return;}
		
		var sqlid4="ContCheckInputSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName("app.ContCheckInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
		mySql4.addSubPara(cAgentCode);//ָ������Ĳ���
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
		mySql5.setResourceName("app.ContCheckInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
		mySql5.addSubPara(document.all('AgentCom').value);//ָ������Ĳ���
	    var strSQL=mySql5.getString();
			
//		var strSQL = "select name from lacom where agentcom='"+ document.all('AgentCom').value+"'";
		var arrResult = easyExecSql(strSQL,1,0);
		if(arrResult!==null)
		{ 
			//tongmeng 2007-09-10 ���ػ�ȥ�����б���
			document.all('InputAgentComName').value=arrResult[0][0]; 

		}
		}
		
	}
}
//��ѯ ҵ��Ա��֪��Ϣ
function initQueryAgentImpartGrid()
{
	var turnPageAgent = new turnPageClass();
	var tContNo=fm.ContNo.value;  
	var tAgentCode=fm.AgentCode.value;
	var tAppntNo=fm.AppntNo.value;
	//��Ϊ����ҵ��Ա��֪�� customerno�洢����Ͷ���˿ͻ��ţ����Բ�ѯʱ��Ͷ���˿ͻ���
	
		var sqlid6="ContCheckInputSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName("app.ContCheckInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
		mySql6.addSubPara(tAppntNo);//ָ������Ĳ���
		mySql6.addSubPara(tContNo);//ָ������Ĳ���
	    var aSQL=mySql6.getString();
	
//	var aSQL="select impartver,impartcode,impartcontent,impartparammodle from lccustomerimpart"
//		+" where customernotype='2' and customerno='"+tAppntNo+"' and contno='"+tContNo+"'";
   	turnPageAgent.queryModal(aSQL, AgentImpartGrid);
}

//����ѯͶ������Ϣ������������Ͷ���˵ĵ�ַ��Ϣ
function initqueryAppnt()
{
	//����"��ͬ��"��ѯ��Ͷ������Ϣ����������Ӧ������
	
			var sqlid7="ContCheckInputSql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName("app.ContCheckInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
		mySql7.addSubPara(fm.ContNo.value);//ָ������Ĳ���
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
  //add by lvshuaiqi 2016-05-12 �п�������¥ 
  fm.AppIDPeriodOfValidity.value=arrApp[0][29];
  fm.AppSocialInsuFlag.value=arrApp[0][30];
  //alert('fm.AppntName.value:'+fm.AppntName.value);
  //alert('fm.AppntLastName.value:'+fm.AppntLastName.value);
		getAge();//Ͷ��������
		//��ѯͶ���˵�ַ��Ϣ
		
		var sqlid8="ContCheckInputSql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName("app.ContCheckInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
		mySql8.addSubPara(fm.AppntNo.value);//ָ������Ĳ���
		mySql8.addSubPara(fm.AppntAddressNo.value);//ָ������Ĳ���
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

//���ɷ���Ϣ��ѯ��------��ѯ�����Ľɷ���Ϣ�������ɷѷ�ʽ���ʺ���Ϣ�ȡ�
function initqueryAccount()
{
	
		var sqlid9="ContCheckInputSql9";
		var mySql9=new SqlClass();
		mySql9.setResourceName("app.ContCheckInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql9.setSqlId(sqlid9);//ָ��ʹ�õ�Sql��id
		mySql9.addSubPara(fm.ContNo.value);//ָ������Ĳ���
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

 //��ѯͶ���˸�֪��Ϣ
function initQueryAppntImpartInfo()
{
	//��ѯ Ͷ�������롢������Դ����ߡ����صȸ�֪��Ϣ����������Ӧ���������
	var tContNo=fm.ContNo.value;
	var tAppntNo=fm.AppntNo.value;
	
		var sqlid10="ContCheckInputSql10";
		var mySql10=new SqlClass();
		mySql10.setResourceName("app.ContCheckInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql10.setSqlId(sqlid10);//ָ��ʹ�õ�Sql��id
		mySql10.addSubPara(tAppntNo);//ָ������Ĳ���
		mySql10.addSubPara(tContNo);//ָ������Ĳ���
	    var strSQL0=mySql10.getString();
	
			var sqlid11="ContCheckInputSql11";
		var mySql11=new SqlClass();
		mySql11.setResourceName("app.ContCheckInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql11.setSqlId(sqlid11);//ָ��ʹ�õ�Sql��id
		mySql11.addSubPara(tAppntNo);//ָ������Ĳ���
		mySql11.addSubPara(tContNo);//ָ������Ĳ���
	    var strSQL1=mySql11.getString();
	
		var sqlid12="ContCheckInputSql12";
		var mySql12=new SqlClass();
		mySql12.setResourceName("app.ContCheckInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql12.setSqlId(sqlid12);//ָ��ʹ�õ�Sql��id
		mySql12.addSubPara(tAppntNo);//ָ������Ĳ���
		mySql12.addSubPara(tContNo);//ָ������Ĳ���
	    var strSQL2=mySql12.getString();
		
		var sqlid13="ContCheckInputSql13";
		var mySql13=new SqlClass();
		mySql13.setResourceName("app.ContCheckInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql13.setSqlId(sqlid13);//ָ��ʹ�õ�Sql��id
		mySql13.addSubPara(tAppntNo);//ָ������Ĳ���
		mySql13.addSubPara(tContNo);//ָ������Ĳ���
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
	//��ѯͶ���˸�֪������������
//	var turnPage1 = new turnPageClass();
/*	var strSQL="select impartver,impartcode,impartcontent,impartparammodle "
		+" from lccustomerimpart where customernotype='0'"
		+" and ((impartver ='01' and impartcode<>'001') or (impartver ='02' and impartcode<>'000'))"
		+" and customerno='"+tAppntNo+"' and contno='"+tContNo+"'";
	turnPage.queryModal(strSQL,ImpartGrid);	
*/

		var sqlid14="ContCheckInputSql14";
		var mySql14=new SqlClass();
		mySql14.setResourceName("app.ContCheckInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql14.setSqlId(sqlid14);//ָ��ʹ�õ�Sql��id
		mySql14.addSubPara(tAppntNo);//ָ������Ĳ���
		mySql14.addSubPara(tContNo);//ָ������Ĳ���
	    var tSQL=mySql14.getString();

//    var tSQL = "select impartver,impartcode,impartcontent,impartparammodle from LCCustomerImpart where CustomerNoType='0' and CustomerNo='"+tAppntNo+"' and ContNo='"+tContNo+"'"
//             + " and ((impartver='01' and impartcode<>'001') or impartver<>'01')";
	turnPage.queryModal(tSQL,ImpartGrid);	
	//��ѯ����������200���������ڣ�
	
			var sqlid15="ContCheckInputSql15";
		var mySql15=new SqlClass();
		mySql15.setResourceName("app.ContCheckInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql15.setSqlId(sqlid15);//ָ��ʹ�õ�Sql��id
		mySql15.addSubPara(tContNo);//ָ������Ĳ���
	    var remarkSQL=mySql15.getString();
	
//	var remarkSQL="select remark from lccont where contno='"+tContNo+"'";
	var remarkArr= easyExecSql(remarkSQL,1,0);
	try{document.all('Remark').value= remarkArr[0][0];}catch(ex){};
}

//����ѯ��������Ϣ��������
function initqueryInsuredInfo()
{
	
		var sqlid16="ContCheckInputSql16";
		var mySql16=new SqlClass();
		mySql16.setResourceName("app.ContCheckInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql16.setSqlId(sqlid16);//ָ��ʹ�õ�Sql��id
		mySql16.addSubPara(fm.ContNo.value);//ָ������Ĳ���
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
	//��ѯѡ�еı�������Ϣ
	//getInsuredInfo();//�����˻�����Ϣ����ַ��Ϣ
	//getInsuredImpartInfo();//�����˸�֪��Ϣ
	//getInsuredPolInfo();//������������Ϣ
	}
}

/*********************************************************************
 *  InsuredGrid��RadioBox����¼�����ñ�������ϸ��Ϣ�����뱻������Ϣ��
 **********************************************************************/
function getInsuredDetail(parm1,parm2)
{
    emptyInsured();//���������¼���������¸�ֵ
	var selectRowNum = parm1.replace(/spanPolGrid/g,"");
    fm.InsuredNo.value = document.all('InsuredGrid'+'r'+selectRowNum).value;
    fm.InsuredNo.value=document.all(parm1).all('InsuredGrid1').value;
    //��ѯѡ�еı�������Ϣ
	getInsuredInfo();//�����˻�����Ϣ����ַ��Ϣ
	getInsuredImpartInfo();//�����˸�֪��Ϣ
	getInsuredPolInfo();//������������Ϣ
	getInputFieldName();//��ʾ������Ϣ
}

//�����˻�����Ϣ����ַ��Ϣ
function getInsuredInfo()
{
	var tContNo=fm.ContNo.value;
	var tInsuredNo=fm.InsuredNo.value;
	if(tContNo=="" || tInsuredNo=="")
	{
		return false;
	}
	//����"��ͬ��"�� "�����˺���"��ѯ����������Ϣ����������Ӧ������
	
			var sqlid17="ContCheckInputSql17";
		var mySql17=new SqlClass();
		mySql17.setResourceName("app.ContCheckInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql17.setSqlId(sqlid17);//ָ��ʹ�õ�Sql��id
		mySql17.addSubPara(tContNo);//ָ������Ĳ���
		mySql17.addSubPara(tInsuredNo);//ָ������Ĳ���
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
		//����"�����˿ͻ���"��ѯ������Ϣ����������Ӧ������
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
  		//add by lvshuaiqi 2016-05-12      �п�������¥
  		fm.IDPeriodOfValidity.value=arrInsured[0][27];
  		fm.SocialInsuFlag.value=arrInsured[0][28];
  		
		getInsuredSequencename();//���ݿͻ��ڲ�������ʾ���⣬�����涯����
		getAge2();//������������
		//��ѯ�����˵ĵ�ַ��Ϣ
		
		var sqlid18="ContCheckInputSql18";
		var mySql18=new SqlClass();
		mySql18.setResourceName("app.ContCheckInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql18.setSqlId(sqlid18);//ָ��ʹ�õ�Sql��id
		mySql18.addSubPara(document.all("InsuredNo").value);//ָ������Ĳ���
		mySql18.addSubPara(document.all("InsuredAddressNo").value);//ָ������Ĳ���
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

//���ݿͻ��ڲ�������ʾ���⣬�����涯����
function getInsuredSequencename()
{
	if(fm.SequenceNo.value=="1")
    {
		param="11";
		fm.pagename.value="11";
		fm.InsuredSequencename.value="������������";
    }
	else if(fm.SequenceNo.value=="2")
	{
		param="122";
		fm.pagename.value="122";
		fm.InsuredSequencename.value="�ڶ�������������";
	}
	else if(fm.SequenceNo.value=="3")
	{
		param="123";
		fm.pagename.value="123";
		fm.InsuredSequencename.value="����������������";
	}
	else 
	{}
	if (scantype== "scan") {  setFocus(); }
}

//��ѯ�����˸�֪��Ϣ��Ϣ
function getInsuredImpartInfo()
{
	//��ѯ ���������롢������Դ����ߡ����صȸ�֪��Ϣ����������Ӧ���������
	var tContNo=fm.ContNo.value;
	var tInsuredNo=fm.InsuredNo.value;
	if(tContNo=="" || tInsuredNo=="")
	{
		return false;
	}
	
		var sqlid19="ContCheckInputSql19";
		var mySql19=new SqlClass();
		mySql19.setResourceName("app.ContCheckInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql19.setSqlId(sqlid19);//ָ��ʹ�õ�Sql��id
		mySql19.addSubPara(tInsuredNo);//ָ������Ĳ���
		mySql19.addSubPara(tContNo);//ָ������Ĳ���
	    var strSQL0=mySql19.getString();
		
		var sqlid20="ContCheckInputSql20";
		var mySql20=new SqlClass();
		mySql20.setResourceName("app.ContCheckInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql20.setSqlId(sqlid20);//ָ��ʹ�õ�Sql��id
		mySql20.addSubPara(tInsuredNo);//ָ������Ĳ���
		mySql20.addSubPara(tContNo);//ָ������Ĳ���
	    var strSQL1=mySql20.getString();
		
		var sqlid21="ContCheckInputSql21";
		var mySql21=new SqlClass();
		mySql21.setResourceName("app.ContCheckInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql21.setSqlId(sqlid21);//ָ��ʹ�õ�Sql��id
		mySql21.addSubPara(tInsuredNo);//ָ������Ĳ���
		mySql21.addSubPara(tContNo);//ָ������Ĳ���
	    var strSQL2=mySql21.getString();
		
		var sqlid22="ContCheckInputSql22";
		var mySql22=new SqlClass();
		mySql22.setResourceName("app.ContCheckInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql22.setSqlId(sqlid22);//ָ��ʹ�õ�Sql��id
		mySql22.addSubPara(tInsuredNo);//ָ������Ĳ���
		mySql22.addSubPara(tContNo);//ָ������Ĳ���
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
	//��ѯ�����˸�֪������������
//	var turnPage1 = new turnPageClass();

		var sqlid23="ContCheckInputSql23";
		var mySql23=new SqlClass();
		mySql23.setResourceName("app.ContCheckInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql23.setSqlId(sqlid23);//ָ��ʹ�õ�Sql��id
		mySql23.addSubPara(tInsuredNo);//ָ������Ĳ���
		mySql23.addSubPara(tContNo);//ָ������Ĳ���
	    var strSQL=mySql23.getString();

//	var strSQL="select impartver,impartcode,impartcontent,impartparammodle "
//		+" from lccustomerimpart a where customernotype='1'"
//		+ " and impartcode<>'001'"
//		+" and customerno='"+tInsuredNo+"' and contno='"+tContNo+"'";
		
	//	prompt('',strSQL);
	turnPage.queryModal(strSQL,ImpartInsuredGrid);	
	
}

/*********************************************************************
 *  ��ñ�����������Ϣ��д��MulLine
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
		mySql24.setResourceName("app.ContCheckInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql24.setSqlId(sqlid24);//ָ��ʹ�õ�Sql��id
		mySql24.addSubPara(tInsuredNo);//ָ������Ĳ���
		mySql24.addSubPara(tContNo);//ָ������Ĳ���
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
//�����������Ϣ
function getLCBnfInfo()
{
	var tPolNo=fm.SelPolNo.value;
	var tContNo=fm.ContNo.value;
	
		var sqlid25="ContCheckInputSql25";
		var mySql25=new SqlClass();
		mySql25.setResourceName("app.ContCheckInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql25.setSqlId(sqlid25);//ָ��ʹ�õ�Sql��id
		mySql25.addSubPara(tContNo);//ָ������Ĳ���
	    var lcbnfSQL=mySql25.getString();
	
//	var lcbnfSQL="select bnftype,name,idtype,idno,relationtoinsured,bnfgrade,bnflot"
//		+" ,(select name from ldperson where customerno=insuredno),'',insuredno"
//		+" from lcbnf where contno='"+tContNo+"'";
	turnPage.queryModal(lcbnfSQL,LCBnfGrid);
}

//���ƽ��水ť��������ʾ
function ctrlButtonDisabled(tContNo,tLoadFlag)
{
	var tSQL = "";
	var arrResult;
	var arrButtonAndSQL = new Array;
	
	if(tLoadFlag==5)
	{
		
		var sqlid26="ContCheckInputSql26";
		var mySql26=new SqlClass();
		mySql26.setResourceName("app.ContCheckInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql26.setSqlId(sqlid26);//ָ��ʹ�õ�Sql��id
		mySql26.addSubPara(tContNo);//ָ������Ĳ���
	    var lcbnfSQL=mySql26.getString();
		
	arrButtonAndSQL[0] = new Array;
	arrButtonAndSQL[0][0] = "ApproveQuestQuery";
	arrButtonAndSQL[0][1] = "�������ѯ";
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

//��ѯ���±�
function checkNotePad(prtNo,LoadFlag)
{
	
	 if (typeof(LoadFlag)=="undefined")
	 {
	 	LoadFlag='a';
	 	}
	 //	alert(LoadFlag);
	 
	 	var sqlid27="ContCheckInputSql27";
		var mySql27=new SqlClass();
		mySql27.setResourceName("app.ContCheckInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql27.setSqlId(sqlid27);//ָ��ʹ�õ�Sql��id
		mySql27.addSubPara(prtNo);//ָ������Ĳ���
	    var strSQL=mySql27.getString();
	 
	//var strSQL="select count(*) from LWNotePad where otherno='"+prtNo+"'";
	var arrResult = easyExecSql(strSQL);
	eval("document.all('NotePadButton"+LoadFlag+"').value=' ���±��鿴(��"+arrResult[0][0]+"��)'");
}
//�ڳ�ʼ��bodyʱ�Զ�Ч��Ͷ������Ϣ��
//�ж�ϵͳ���Ƿ�����������Ա𡢳���������ͬ��֤������֤��������ͬ�ı�������Ϣ��
function AppntChkNew()
{
}

// �ڳ�ʼ��bodyʱ�Զ�Ч�鱻������Ϣ
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
		mySql29.setResourceName("app.ContCheckInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql29.setSqlId(sqlid29);//ָ��ʹ�õ�Sql��id
		mySql29.addSubPara(tInsuredName);//ָ������Ĳ���
		mySql29.addSubPara(tInsuredSex);//ָ������Ĳ���
		mySql29.addSubPara(tBirthday);//ָ������Ĳ���
		
		mySql29.addSubPara(tInsuredNo);//ָ������Ĳ���
		mySql29.addSubPara(fm.IDNo.value);//ָ������Ĳ���
		mySql29.addSubPara(tInsuredNo);//ָ������Ĳ���
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

//���������¼���������¸�ֵ
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
	//��ͬ��Ϣ����
	showOneCodeName('sellType','SellType','sellTypeName');
	showOneCodeName('salechnl','SaleChnl','SaleChnlName');
	showOneCodeName('comcode','ManageCom','ManageComName');
	showOneCodeName('highAmntFlag','highAmntFlag','highAmntFlagName');
	showOneCodeName('comcode','AgentManageCom','AgentManageComName');
	showOneCodeName('agentrelatoappnt','RelationShip','RelationShipName');
	//Ͷ������Ϣ����
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
	//Ͷ���˸�֪
	//showOneCodename('incomeway','IncomeWay0','IncomeWayName0');
	//�ɷ���Ϣ����
	showOneCodeName('paylocation','PayMode','PayModeName');
	showOneCodeName('bank','AppntBankCode','AppntBankCodeName');
	showOneCodeName('paylocation','SecPayMode','SecPayModeName');
	showOneCodeName('bank','SecAppntBankCode','SecAppntBankCodeName');
	//��������Ϣ����
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
	//�����˸�֪
	//showOneCodename('incomeway','IncomeWay','IncomeWayName');
}

//У��Ͷ������
function checkapplydate()
{	
}

//У��Ͷ���˳�������
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
			alert("�������Ͷ���˳�����������!");
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
		alert("�������Ͷ���˳�����������!");
		fm.AppntBirthday.value = "";
		return;
		}
	}
}


//У�鱻���˳�������
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
			alert("������ı����˳�����������!");
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
		alert("������ı����˳�����������!");
		fm.Birthday.value = "";
		return;
		}
	}
}
//Ͷ��������<Ͷ���˱���������Ӧ��ΪͶ������������֮��;2005-11-18�޸�>
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
		alert("��������ֻ��Ϊ��ǰ������ǰ");
		fm.AppntAge.value="";
		return;
	}
//	fm.AppntAge.value=calAge(fm.AppntBirthday.value);
	fm.AppntAge.value=calPolAppntAge(fm.AppntBirthday.value,fm.PolAppntDate.value);
  	return ;
}
//������������<����������Ӧ��ΪͶ������������֮��;2005-11-18�޸�>
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
		alert("��������ֻ��Ϊ��ǰ������ǰ");
		fm.InsuredAppAge.value="";
		return;
    }
    fm.InsuredAppAge.value=calPolAppntAge(fm.Birthday.value,fm.PolAppntDate.value);
  	return ;
}  
//Ͷ����֤��
function checkidtype()
{
	if(fm.AppntIDType.value==""&&fm.AppntIDNo.value!="")
	{
		alert("����ѡ��֤�����ͣ�");
		fm.AppntIDNo.value="";
	}
}
//������֤��
function checkidtype2()
{
	if(fm.IDType.value=="")
	{
		alert("����ѡ��֤�����ͣ�");
    }
}

/****************Ͷ����  �������֤��ȡ�ó������ں��Ա�******************/
function getBirthdaySexByIDNo(iIdNo)
{
	if(document.all('AppntIDType').value=="0")
	{
		var tBirthday=getBirthdatByIdNo(iIdNo);
		var tSex=getSexByIDNo(iIdNo);
		if(tBirthday=="��������֤��λ������"||tSex=="��������֤��λ������")
		{
			alert("��������֤��λ������");
			theFirstValue="";
			theSecondValue="";
			//document.all('AppntIDNo').focus();
			return;
		}
		else
		{
			alert("�Ա����������֤����Ϣ�������Ѹ������֤����Ϣ�Զ��޸��Ա�")
			document.all('AppntBirthday').value=tBirthday;
			document.all('AppntSex').value=tSex;
			if("0"==tSex)
			document.all('AppntSexName').value="��";
			if("1"==tSex)
			document.all('AppntSexName').value="Ů";
		}
	}
}

/****************������  �������֤��ȡ�ó������ں��Ա�******************/
function getBirthdaySexByIDNo2(iIdNo)
{
	if(document.all('IDType').value=="0")
	{
		var tBirthday=getBirthdatByIdNo(iIdNo);
		var tSex=getSexByIDNo(iIdNo);
		if(tBirthday=="��������֤��λ������"||tSex=="��������֤��λ������")
		{
			alert("��������֤��λ������");
			theFirstValue="";
			theSecondValue="";
			//document.all('IDNo').focus();
			return;

		}
		else
		{
			alert("�Ա����������֤����Ϣ�������Ѹ������֤����Ϣ�Զ��޸��Ա�")
			document.all('Birthday').value=tBirthday;
			document.all('Sex').value=tSex;
			if("0"==tSex)
			document.all('SexName').value="��";
			if("1"==tSex)
			document.all('SexName').value="Ů";
		}

	}
}

function getdetailwork()
{
	
		 var sqlid30="ContCheckInputSql30";
		var mySql30=new SqlClass();
		mySql30.setResourceName("app.ContCheckInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql30.setSqlId(sqlid30);//ָ��ʹ�õ�Sql��id
		mySql30.addSubPara(fm.AppntOccupationCode.value);//ָ������Ĳ���
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
		mySql31.setResourceName("app.ContCheckInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql31.setSqlId(sqlid31);//ָ��ʹ�õ�Sql��id
		mySql31.addSubPara(fm.AppntNo.value);//ָ������Ĳ���
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
		mySql32.setResourceName("app.ContCheckInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql32.setSqlId(sqlid32);//ָ��ʹ�õ�Sql��id
		mySql32.addSubPara(fm.InsuredNo.value);//ָ������Ĳ���
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

//�����¼��
function QuestInput()
{
	//alert(LoadFlag);
    cContNo = fm.ContNo.value;  //��������
    if(LoadFlag=="2"||LoadFlag=="4"||LoadFlag=="13")
    {
        if(mSwitch.getVar( "ProposalGrpContNo" )=="")
        {
            alert("���޼����ͬͶ�����ţ����ȱ���!");
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
            alert("���޺�ͬͶ�����ţ����ȱ���!");
        }
        else
        {
            window.open("../uw/MSQuestInputMain.jsp?ContNo="+cContNo+"&Flag="+ LoadFlag+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+ActivityID,"window1",sFeatures);
        }
    }
}
//�������ѯ
function QuestQuery()
{
	cContNo = document.all("ContNo").value;  //��������
	if(LoadFlag=="2"||LoadFlag=="4"||LoadFlag=="13")
	{
		if(mSwitch.getVar( "ProposalGrpContNo" )==""||mSwitch.getVar( "ProposalGrpContNo" )==null)
		{
		alert("����ѡ��һ����������Ͷ����!");
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
		alert("���޺�ͬͶ�����ţ����ȱ���!");
		}
		else
		{
		window.open("../uw/QuestQueryMain.jsp?ContNo="+cContNo+"&Flag="+LoadFlag,"window1",sFeatures);
		}
	}
}

//�����Ӱ���ѯ
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
//���±��鿴
function showNotePad()
{
	var ActivityID = document.all.ActivityID.value;
	var PrtNo = document.all.PrtNo.value;
	var NoType = document.all.NoType.value;
	if(PrtNo == null || PrtNo == "")
	{
		alert("Ͷ������Ϊ�գ�");
		return;
	}
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ PrtNo + "&NoType="+ NoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");

}

//Ͷ���˼���
function AppntChk()
{

	var Sex=fm.AppntSex.value;
	//var i=Sex.indexOf("-");
	//Sex=Sex.substring(0,i);
	
		var sqlid33="ContCheckInputSql33";
		var mySql33=new SqlClass();
		mySql33.setResourceName("app.ContCheckInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql33.setSqlId(sqlid33);//ָ��ʹ�õ�Sql��id
		mySql33.addSubPara(fm.AppntName.value);//ָ������Ĳ���
		mySql33.addSubPara(Sex);//ָ������Ĳ���
		mySql33.addSubPara(fm.AppntBirthday.value);//ָ������Ĳ���

		mySql33.addSubPara(fm.AppntNo.value);//ָ������Ĳ���
		mySql33.addSubPara(fm.AppntIDNo.value);//ָ������Ĳ���
		mySql33.addSubPara(fm.AppntNo.value);//ָ������Ĳ���
	
	  var  strsql=mySql33.getString();
	
//	var sqlstr="select * from ldperson where Name='"+fm.AppntName.value
//	+ "' and Sex='"+Sex+"' and Birthday='"+fm.AppntBirthday.value
//	+ "' and CustomerNo<>'"+fm.AppntNo.value+"'"
//	+ " union select * from ldperson where 1=1 " 
//	+ " and IDNo = '"+fm.AppntIDNo.value
//	+ "' and IDNo is not null and CustomerNo<>'"+fm.AppntNo.value+"'" ;
	arrResult = easyExecSql(strsql);
	//alert('arrResult:'+arrResult);
	
	if(document.all('AppntSex').value == "1" && document.all('AppntSexName').value != 'Ů')
	{
		
		alert("¼���Ա������֤�Ա��벻����");
		document.all('AppntSexName').value='Ů';
		return false;
	}
	if(document.all('AppntSex').value == "0" && document.all('AppntSexName').value != '��')
	{
		
		alert("¼���Ա������֤�Ա��벻����");
		document.all('AppntSexName').value='��';
		return false;
	}

	
	if(arrResult==null)
	{
		alert("��û�����Ͷ�������ƵĿͻ�,����У��");
		return false;
	}
	else
	{
		window.open("../uw/AppntChkMain.jsp?ProposalNo1="+fm.ContNo.value+"&Flag=A&LoadFlag="+LoadFlag+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+ActivityID,"window1",sFeatures);
	}
}
//�����˼���
function InsuredChk()
{
	var tSel =InsuredGrid.getSelNo();
	if( tSel == 0 || tSel == null )
	{
		alert("����ѡ�񱻱����ˣ�");
		return false;
	}
	var tRow = InsuredGrid.getSelNo() - 1;
	var tInsuredNo=InsuredGrid.getRowColData(tRow,1);
	var tInsuredName=InsuredGrid.getRowColData(tRow,2);
	var tInsuredSex=InsuredGrid.getRowColData(tRow,3);
	var tBirthday=InsuredGrid.getRowColData(tRow,4);
	
		var sqlid34="ContCheckInputSql34";
		var mySql34=new SqlClass();
		mySql34.setResourceName("app.ContCheckInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql34.setSqlId(sqlid34);//ָ��ʹ�õ�Sql��id
		mySql34.addSubPara(tInsuredName);//ָ������Ĳ���
		mySql34.addSubPara(tInsuredSex);//ָ������Ĳ���
		mySql34.addSubPara(tBirthday);//ָ������Ĳ���
		
		mySql34.addSubPara(tInsuredNo);//ָ������Ĳ���
		mySql34.addSubPara(fm.IDNo.value);//ָ������Ĳ���
		mySql34.addSubPara(tInsuredNo);//ָ������Ĳ���
	    var sqlstr=mySql34.getString();
	
//  var sqlstr="select * from ldperson where Name='"+tInsuredName
//            +"' and Sex='"+tInsuredSex+"' and Birthday='"+tBirthday
//            +"' and CustomerNo<>'"+tInsuredNo+"'"
//            +" union select * from ldperson where 1=1 "
//            +" and IDNo = '"+fm.IDNo.value
//            +"' and IDNo is not null and CustomerNo<>'"+tInsuredNo+"'" ;
        arrResult = easyExecSql(sqlstr,1,0);
        if(document.all('AppntSex').value == "1" && document.all('AppntSexName').value != 'Ů')
    	{
    		alert("¼���Ա������֤�Ա��벻����");
    		document.all('AppntSexName').value='Ů';
    		return false;
    	}
    	if(document.all('AppntSex').value == "0" && document.all('AppntSexName').value != '��')
    	{
    		alert("¼���Ա������֤�Ա��벻����");
    		document.all('AppntSexName').value='��';
    		return false;
    	}

        if(arrResult==null)
        {
	   alert("��û����ñ����˱������ƵĿͻ�,����У��");
	   return false;
        }

    window.open("../uw/AppntChkMain.jsp?ProposalNo1="+fm.ContNo.value+"&Flag=I&LoadFlag="+LoadFlag+"&InsuredNo="+tInsuredNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+ActivityID,"window1",sFeatures);
}

//ǿ�ƽ����˹��˱�
function forceUW()
{
	//��ѯ�Ƿ��Ѿ�����ѡ��ǿ�ƽ����˹��˱�
  var ContNo=document.all("ContNo").value;
  
  		var sqlid35="ContCheckInputSql35";
		var mySql35=new SqlClass();
		mySql35.setResourceName("app.ContCheckInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql35.setSqlId(sqlid35);//ָ��ʹ�õ�Sql��id
		mySql35.addSubPara(ContNo);//ָ������Ĳ���
	    var sqlstr=mySql35.getString();
  
//  var sqlstr="select forceuwflag from lccont where contno='"+ContNo+"'" ;
  arrResult = easyExecSql(sqlstr,1,0);
  if(arrResult==null){
  	alert("�����ڸ�Ͷ������");
  }
  else
  {
    window.open("../uw/ForceUWMain.jsp?ContNo="+ContNo,"window1",sFeatures);
  }
}

/**************PolGrid��RadioBox����¼�����ñ�����������ϸ��Ϣ*************/
function getPolDetail(parm1,parm2)
{
    var selectRowNum = parm1.replace(/spanPolGrid/g,"");
    var PolNo = document.all('PolGrid1'+'r'+selectRowNum).value;
	//var PolNo=document.all(parm1).all('PolGrid1').value
    try{mSwitch.deleteVar('PolNo')}catch(ex){};
    try{mSwitch.addVar('PolNo','',PolNo);}catch(ex){};
    fm.SelPolNo.value=PolNo;
}

/************************  ����������Ϣ¼��***********************/
function intoRiskInfo()
{
	//�Ѻ�ͬ��Ϣ�ͱ�������Ϣ�����ڴ�
	mSwitch = parent.VD.gVSwitch;  //���ݴ�
	delContVar();//�Ѻ�ͬ��Ϣ�ӱ�����ɾ��
	addIntoCont();//�Ѻ�ͬ��Ϣ����ӵ�������
	delInsuredVar();//ɾ�������б���������Ϣ
	addInsuredVar();//������������Ϣ���뵽������
	if(fm.InsuredNo.value==""||fm.ContNo.value==""||InsuredGrid.mulLineCount=="0")
	{
		alert("������ӣ�ѡ�񱻱���");
		return false;
	}
	//alert(InsuredGrid.mulLineCount);
	var tRow = InsuredGrid.getSelNo() - 1;
	var tInsuredNo=InsuredGrid.getRowColData(tRow,1);
	
	  	var sqlid36="ContCheckInputSql36";
		var mySql36=new SqlClass();
		mySql36.setResourceName("app.ContCheckInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql36.setSqlId(sqlid36);//ָ��ʹ�õ�Sql��id
		mySql36.addSubPara(fm.ProposalContNo.value);//ָ������Ĳ���
		mySql36.addSubPara(tInsuredNo);//ָ������Ĳ���
	    var sqlstr=mySql36.getString();
		
		 var sqlid37="ContCheckInputSql37";
		var mySql37=new SqlClass();
		mySql37.setResourceName("app.ContCheckInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql37.setSqlId(sqlid37);//ָ��ʹ�õ�Sql��id
		mySql37.addSubPara(fm.ProposalContNo.value);//ָ������Ĳ���
	    var tSql=mySql37.getString();
	
//	var sqlstr="select sequenceno From lcinsured where (prtno,contno)= (select prtno,contno from lccont where proposalcontno='"+fm.ProposalContNo.value+"') and insuredno='"+tInsuredNo+"'" ;
//	var tSql = "select * from lcpol where contno = '"+fm.ProposalContNo.value+"' and riskcode = '00150000'";//�������Ϊ150,Ҫ���⴦��
	turnPage.strQueryResult = easyQueryVer3(tSql, 1, 0, 1);      
	var sequence = easyExecSql(sqlstr,1,0);
	if(sequence!="1" && InsuredGrid.mulLineCount>1 && !turnPage.strQueryResult)
	{
		alert("����ȷѡ���һ������!");
		return;
	}
	
  	try{mSwitch.addVar('SelPonNo','',fm.SelPolNo.value);}catch(ex){} //ѡ�����ֵ��������ֽ�������ѱ������Ϣ
	if ((LoadFlag=='5'||LoadFlag=='4'||LoadFlag=='6'||LoadFlag=='16')&&(mSwitch.getVar( "PolNo" ) == null || mSwitch.getVar( "PolNo" ) == ""))
	{
		alert("����ѡ�񱻱�����������Ϣ��");
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
 *  �Ѻ�ͬ������Ϣ¼�����ȷ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function inputConfirm(wFlag)
{ 
	if (wFlag ==1 ) //¼�����ȷ��
	{
		
		 var sqlid38="ContCheckInputSql38";
		var mySql38=new SqlClass();
		mySql38.setResourceName("app.ContCheckInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql38.setSqlId(sqlid38);//ָ��ʹ�õ�Sql��id
		mySql38.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	    var tStr=mySql38.getString();
		
//		var tStr= "	select * from lwmission where 1=1 and lwmission.activityid in ('0000001001','0000001002','0000001220','0000001230') and lwmission.missionprop1 = '"+fm.ContNo.value+"'";
		turnPage.strQueryResult = easyQueryVer3(tStr, 1, 0, 1);
		if (turnPage.strQueryResult) 
		{
			alert("�ú�ͬ�Ѿ��������棡");
			return;
		}
		if(document.all('ProposalContNo').value == "")
		{
			alert("��ͬ��Ϣδ����,������������ [¼�����] ȷ�ϣ�");
			return;
		}
		//���¼�����ʴ����У��<����¼��ı�����ϵͳ���ɵı��Ѳ�����ϵͳҪ������ʾ�����Կɱ���>
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
		fm.SubMissionID.value = tSubMissionID;			//¼�����
	}
	else if (wFlag ==2)//�������ȷ��
	{
		//�����Ѿ������˿ͻ�����������ע�͵������У��2009-01-12
		//if(fm.AppntChkButton.disabled=="")
		//{ 
		//	var strSql = "select * from LCIssuePol where contno = '"+fm.ContNo.value+"' and issuetype = '99' and questionobj = '"+document.all('AppntNo').value+"'";
		//	var brrResult = easyExecSql(strSql);
		//	if(brrResult==null)	
		//	{
		//		if(confirm("�Ƿ����Ͷ����У�飿"))
		//		return;
		//	}
		//}	
		//if(fm.InsuredChkButton.disabled=="")
		//{
		//	strSql = "select * from LCIssuePol where contno = '"+fm.ContNo.value+"' and issuetype = '99' and questionobj in (select insuredno from lcinsured where contno='"+fm.ContNo.value+"')";
		//	var crrResult = easyExecSql(strSql);
		//	if(crrResult==null)
		//	{
		//	if(confirm("�Ƿ���б�����У�飿"))
		//	return;
		//	}
		//}
		if(document.all('ProposalContNo').value == "")
		{
			alert("δ��ѯ����ͬ��Ϣ,������������ [�������] ȷ�ϣ�");
			return;
		}
		fm.WorkFlowFlag.value = ActivityID;					//�������
		fm.MissionID.value = tMissionID;
		fm.SubMissionID.value = tSubMissionID;
		approvefalg="2";
	}
	else if (wFlag ==3)
	{
		if(document.all('ProposalContNo').value == "")
		{
		alert("δ��ѯ����ͬ��Ϣ,������������ [�����޸����] ȷ�ϣ�");
		return;
		}
		fm.WorkFlowFlag.value = ActivityID;					//�����޸����
		fm.MissionID.value = tMissionID;
		fm.SubMissionID.value = tSubMissionID;
	}
	else if(wFlag == 4)
	{
		if(document.all('ProposalContNo').value == "")
		{
		alert("δ��ѯ����ͬ��Ϣ,������������ [�޸����] ȷ�ϣ�");
		return;
		}
		fm.WorkFlowFlag.value = ActivityID;					//�����޸�
		fm.MissionID.value = tMissionID;
		fm.SubMissionID.value = tSubMissionID;
	}
	else
		return;
	
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	fm.action = "./InputConfirm.jsp";
	document.getElementById("fm").submit(); //�ύ
}

/*********************************************************************
 *  �������Ͷ�������ύ��Ĳ���,���������ݷ��غ�ִ�еĲ���
 ********************************************************************/
function afterSubmit( FlagStr, content,contract )
{
	showInfo.close();
	if( FlagStr == "Fail" )
	{
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();

	}
	else
	{
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		top.close();
	}
	
}


/********************************** *  �Ѻ�ͬ��Ϣ����ӵ������� ************************/
function addIntoCont()
{
	//try { mSwitch.addVar( "intoPolFlag", "", "GROUPPOL" ); } catch(ex) { };
	// body��Ϣ
	try { mSwitch.addVar( "BODY", "", window.document.body.innerHTML ); } catch(ex) { };
	// ������Ϣ
	//��"./AutoCreatLDGrpInit.jsp"�Զ�����
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
	//�µ����ݴ���
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

/******************************  �Ѽ�����Ϣ�ӱ�����ɾ��*****************************/
function delContVar()
{
	try { mSwitch.deleteVar( "intoPolFlag" ); } catch(ex) { };
	// body��Ϣ
	try { mSwitch.deleteVar( "BODY" ); } catch(ex) { };
	// ������Ϣ
	//��"./AutoCreatLDGrpInit.jsp"�Զ�����
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

    //�µ�ɾ�����ݴ���
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

/************************* ɾ�������б���������Ϣ******************************/
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

/*********************������������Ϣ���뵽������************************/
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
		mySql39.setResourceName("app.ContCheckInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql39.setSqlId(sqlid39);//ָ��ʹ�õ�Sql��id
		mySql39.addSubPara(fm.InsuredAddressNo.value);//ָ������Ĳ���
		mySql39.addSubPara(fm.InsuredNo.value);//ָ������Ĳ���
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
 *  ����ѡ��󴥷�ʱ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************/
function afterCodeSelect2( cCodeName, Field )
{
    try
    {
        //�����������
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
				fm.InsuredSequencename.value="������������";
				fm.RelationToMainInsured.value="00";
			}
			if(Field.value=="2"&&fm.SamePersonFlag.checked==false)
			{
				if(InsuredGrid.mulLineCount==0)
				{
				alert("������ӵ�һ������");
				fm.SequenceNo.value="1";
				fm.SequenceNoName.value="��������";
				return false;
				}
				//emptyInsured();
				noneedhome();
				param="122";
				fm.pagename.value="122";
				fm.InsuredSequencename.value="�ڶ�������������";
			}
			if(Field.value=="3"&&fm.SamePersonFlag.checked==false)
			{
				if(InsuredGrid.mulLineCount==0)
				{
				alert("������ӵ�һ������");
				Field.value="1";
				fm.SequenceNo.value="1";
				fm.SequenceNoName.value="��������";
				return false;
				}
				if(InsuredGrid.mulLineCount==1)
				{
					alert("������ӵڶ�������");
					Field.value="1";
					fm.SequenceNo.value="2";
					fm.SequenceNoName.value="�ڶ���������";
					return false;
				}
				//emptyInsured();
				noneedhome();
				param="123";
				fm.pagename.value="123";
				fm.InsuredSequencename.value="����������������";
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
		mySql40.setResourceName("app.ContCheckInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql40.setSqlId(sqlid40);//ָ��ʹ�õ�Sql��id
		mySql40.addSubPara(insuredno);//ָ������Ĳ���
		mySql40.addSubPara(fm.ContNo.value);//ָ������Ĳ���
		mySql40.addSubPara(insuredno);//ָ������Ĳ���		
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
* ����Ͷ���˱��������䡶Ͷ������������֮��=Ͷ���˱��������䡷,2005-11-18�����
* ��������������yy��mm��dd��Ͷ������yy��mm��dd
* ����  ����
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
//	alert("����"+arrBirthDate);	
	var arrPolAppntDate = PolAppntDate.split("-");
	if (arrPolAppntDate[1].length == 1) arrPolAppntDate[1] = "0" + arrBirthDate[1];
	if (arrPolAppntDate[2].length == 1) arrPolAppntDate[2] = "0" + arrBirthDate[2];
//	alert("Ͷ������"+arrPolAppntDate);
	if(arrPolAppntDate[0]<=99)
	{
		arrBirthDate[0]=	arrBirthDate[0]-1900;
	}
	age = arrPolAppntDate[0] - arrBirthDate[0] - 1;
	//��ǰ�´��ڳ�����
	//alert(arrPolAppntDate[1] + " | " + arrBirthDate[1] + " | " + (arrPolAppntDate[1] > arrBirthDate[1]));
	if (arrPolAppntDate[1] > arrBirthDate[1]) 
	{
		age = age + 1;
		return age;
	}
	//��ǰ��С�ڳ�����
	else if (arrPolAppntDate[1] < arrBirthDate[1]) 
	{
		return age;
	}
  	//��ǰ�µ��ڳ����µ�ʱ�򣬿�������
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

//���ݵ�ַ�����ѯ��ַ������Ϣ,��ѯ��ַ�����<ldaddress>
//�������,��ַ����<province--ʡ;city--��;district--��/��;>,��ַ����<�������>,��ַ������Ϣ<���ֱ���>
//����,ֱ��Ϊ--��ַ������Ϣ<���ֱ���>--��ֵ
//���� getAddressName('province','AppntProvince','AppntProvinceName');
function getAddressName(strCode, strObjCode, strObjName)
{
	var PlaceType="";
	var PlaceCode="";
	//�жϵ�ַ����,<province--ʡ--01;city--��--02;district--��/��--03;>
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
	//����FORM�е�����ELEMENT
	for (formsNum=0; formsNum<window.document.forms.length; formsNum++)
	{
		for (elementsNum=0; elementsNum<window.document.forms[formsNum].elements.length; elementsNum++)
		{
			//Ѱ�Ҵ���ѡ��Ԫ��
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
	//��������������ݳ���Ϊ[6]�Ų�ѯ���������κβ���	
//	strObjName.value="";
	if(strObjCode.value !="")
	{
		PlaceCode=strObjCode.value;
		
		var sqlid41="ContCheckInputSql41";
		var mySql41=new SqlClass();
		mySql41.setResourceName("app.ContCheckInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql41.setSqlId(sqlid41);//ָ��ʹ�õ�Sql��id
		mySql41.addSubPara(PlaceCode);//ָ������Ĳ���
		mySql41.addSubPara(PlaceType);//ָ������Ĳ���		
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

//��ѯ����Ա�涯�ٶ�
function initQueryRollSpeed()
{
	
		var sqlid42="ContCheckInputSql42";
		var mySql42=new SqlClass();
		mySql42.setResourceName("app.ContCheckInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql42.setSqlId(sqlid42);//ָ��ʹ�õ�Sql��id
//		mySql42.addSubPara(PlaceCode);//ָ������Ĳ���
		mySql42.addSubPara("1");//ָ������Ĳ���		
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
		mySql43.setResourceName("app.ContCheckInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql43.setSqlId(sqlid43);//ָ��ʹ�õ�Sql��id
		mySql43.addSubPara(fm.Operator.value);//ָ������Ĳ���
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

//���Զ���ý���Ŀؼ�����
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


//�Զ���ý���
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
//�ͻ����ֹ��ϲ�
function GoToAppnt(){
	var i = 0;
    var checkFlag = 0;
  	var state = "0";
  var	ContNo = document.all('PrtNo').value;
  //	var selno=OPolGrid.getSelNo();//alert("selno:"+selno)
	//if (selno ==0||selno==null)
//	{
//	      alert("��ѡ��һ����¼��");
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
	      alert("��ѡ��һ����¼��");
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

	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    lockScreen('lkscreen');  
	fm.action = "./UnionConfirmSave.jsp";
	document.getElementById("fm").submit(); //�ύ
}

function afterSubmit2( FlagStr, content )
{
	unlockScreen('lkscreen');  
		showInfo.close();
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
//		top.opener.easyQueryClick();// ˢ��ҳ��
//		top.opener.easyQueryClickSelf();// ˢ��ҳ��
//		top.opener.easyQueryClick();// ˢ��ҳ��
//		top.opener.easyQueryClickSelf();// ˢ��ҳ��
		top.opener.jQuery("#privateSearch").click();;// ˢ��ҳ��
		top.opener.jQuery("#publicSearch").click();// ˢ��ҳ��
		
//		window.close();
		
		top.close();
}


/**
 * У�������˺�  090910����  У�������˺������б���Ĺ���
 * ����˫¼�������ж�LoadFlag��ҳ�������÷���
 * ˫¼�Ҳ����жϵĽ�����ȥ��LoadFlag=='1'��У��
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