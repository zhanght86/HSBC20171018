//�������ƣ�DirectContInput.js
//�����ܣ�ֱ������¼��ҳ����Ҫ����ĺ������¼�
//�������ڣ� 2006-1-20 10:13
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
// ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();
var queryoperate="";
var showInfo;
var approvefalg;

//���ҳ�������-----��ͬ��Ϣ����Ͷ�����������ѷ�ʽ��Ϣ����������Ϣ�������˸�֪��Ϣ��������Ϣ��
function clearPageData()
{
	
}

//��ʼ��ʱ����ѯ��ͬ��Ϣ��Ͷ������Ϣ(����������Ϣ)����������Ϣ(������֪��Ϣ)��������Ϣ������ʾ�ڽ���
function initQuery()
{
	initQueryCont();//����ʼ��ʱ��ѯ��ͬ��Ϣ��
	initqueryAppnt();//����ѯͶ������Ϣ������������Ͷ���˵ĵ�ַ��Ϣ
	initqueryAccount();//���ɷ���Ϣ��ѯ��------��ѯ�����Ľɷ���Ϣ�������ɷѷ�ʽ���ʺ���Ϣ�ȡ�
	initqueryInsured();//����ѯ��������Ϣ�����������������˵ĵ�ַ��Ϣ
	DivLCInsuredInfo.style.display="";//����������Ϣ��ѯ����
	initqueryImpartInsuredGrid(); //����ѯ�����˸�֪��Ϣ�����������������˵ĸ�֪��Ϣ
	initqueryPolGrid();//����ѯ������������Ϣ����������ѯ������������Ϣ
	getInputFieldName();	//����ҳ�������Ĵ���������֣�����ʾ������
	if (LoadFlag == "5" && scantype == "scan")
	    initQueryRollSpeed();
}
//����ʼ��ʱ��ѯ��ͬ��Ϣ��
function initQueryCont()
{
	var sSQL="";
	if(document.all('ContNo').value==null ||document.all('ContNo').value=="" || document.all('ContNo').value=="null")
	{
//		var sSQL="select contno,proposalcontno,prtno,polapplydate,selltype,salechnl,managecom,agentcode,cvalidate,getpolmode"
//			+" from lccont where prtno='"+fm.PrtNo.value+"' ";
		
		var sqlid0="DirectContInputSql0";
		var mySql0=new SqlClass();
		mySql0.setResourceName("app.DirectContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql0.setSqlId(sqlid0);//ָ��ʹ�õ�Sql��id
		mySql0.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
		var sSQL=mySql0.getString();
		
	}
	else
	{
//		var sSQL="select contno,proposalcontno,prtno,polapplydate,selltype,salechnl,managecom,agentcode,cvalidate,getpolmode"
//			+" from lccont where contno='"+fm.ContNo.value+"' ";
		
		var sqlid1="DirectContInputSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("app.DirectContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(fm.ContNo.value);//ָ������Ĳ���
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
	initqueryAgent();//����ʼ��ʱ��ѯ��ͬҵ��Ա��Ϣ������������ҵ��Ա������Ա��Ϣ
	}
}
//����ʼ��ʱ��ѯ��ͬҵ��Ա��Ϣ������������������������ҵ��Ա������Ա��Ϣ
function initqueryAgent()
{
	if(fm.ContNo.value=="") {return true;}
	//��ѯ����Ա��Ϣ laagent.branchtype = '5' and laagent.branchtype2 = '02'--����Ա
//	var strSQL = "select a.agentcode,a.agentgroup,a.managecom,a.name,c.branchmanager,"
//	    +" b.introagency,b.agentseries,b.agentgrade,c.branchattr,b.ascriptseries,c.name "
//		+" from laagent a,latree b,labranchgroup c where 1=1 "
//		+" and a.agentcode = b.agentcode and a.agentgroup = c.agentgroup "
//		+" and a.branchtype='5' and a.branchtype2='02'"
//		+" and a.agentcode in "
//		+" (select agentcode from lacommisiondetail where grpcontno='"+fm.ContNo.value+"')";
	
	var sqlid2="DirectContInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName("app.DirectContInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(fm.ContNo.value);//ָ������Ĳ���
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
//	//��ѯ����Ա��Ϣ laagent.branchtype = '5' and laagent.branchtype2 = '01'--����Ա
//	var sSQL = "select a.agentcode,a.agentgroup,a.managecom,a.name,c.branchmanager,"
//	    +" b.introagency,b.agentseries,b.agentgrade,c.branchattr,b.ascriptseries,c.name "
//		+" from laagent a,latree b,labranchgroup c where 1=1 "
//		+" and a.agentcode = b.agentcode and a.agentgroup = c.agentgroup "
//		+" and a.branchtype='5' and a.branchtype2='01'"
//		+" and a.agentcode in "
//		+" (select agentcode from lacommisiondetail where grpcontno='"+fm.ContNo.value+"')";
	
	var sqlid3="DirectContInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName("app.DirectContInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	mySql3.addSubPara(fm.ContNo.value);//ָ������Ĳ���
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
		mySql4.setResourceName("app.DirectContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
		mySql4.addSubPara(fm.ContNo.value);//ָ������Ĳ���
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

//����ѯͶ������Ϣ������������Ͷ���˵ĵ�ַ��Ϣ
function initqueryAppnt()
{
	if(fm.ContNo.value=="") {return true;}
	clearAppntInfoData();//���Ͷ������Ϣ
	//����"��ͬ��"��ѯ��Ͷ������Ϣ����������Ӧ������
//	var sSQL="select contno,appntno,addressno,(select vipvalue from ldperson where customerno=appntno),"
//			+"appntname,appntsex,appntbirthday,idtype,idno,"
//			+"marriage,nativeplace,licensetype,occupationcode,occupationtype,"
//			+"makedate,maketime,modifydate,modifytime "
//			+" from lcappnt where contno='"+fm.ContNo.value+"'";
	
	var sqlid5="DirectContInputSql5";
	var mySql5=new SqlClass();
	mySql5.setResourceName("app.DirectContInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
	mySql5.addSubPara(fm.ContNo.value);//ָ������Ĳ���
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
		//��ѯͶ���˵�ַ��Ϣ
		if(document.all("AppntNo").value=="" || document.all("AppntAddressNo").value=="") {return true;}
//		var sAppAddSQL="select customerno,addressno,province,city,county,postaladdress,zipcode,"
//			+ " mobile,companyphone,homephone,phone,fax,grpname,email"
//			+ " from lcaddress where customerno='"+document.all("AppntNo").value+"' "
//			+ " and addressno='"+document.all("AppntAddressNo").value+"'";
		
		var sqlid6="DirectContInputSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName("app.DirectContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
		mySql6.addSubPara(document.all("AppntNo").value);//ָ������Ĳ���
		mySql6.addSubPara(document.all("AppntAddressNo").value);//ָ������Ĳ���
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

//���ɷ���Ϣ��ѯ��------��ѯ�����Ľɷ���Ϣ�������ɷѷ�ʽ���ʺ���Ϣ�ȡ�
function initqueryAccount()
{
	if(fm.ContNo.value==""){return true;}
	clearPayFeeInfoData();//����ɷ���Ϣ�����
//	var sAccSQL="select contno,newpaymode,newbankcode,newaccname,newbankaccno,paylocation,bankcode,accname,bankaccno "
//		+" from lccont where contno='"+fm.ContNo.value+"'";
	
	var sqlid7="DirectContInputSql7";
	var mySql7=new SqlClass();
	mySql7.setResourceName("app.DirectContInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
	mySql7.addSubPara(fm.ContNo.value);//ָ������Ĳ���
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
//����ѯ��������Ϣ�����������������˵ĵ�ַ��Ϣ
function initqueryInsured()
{
	//�����������Ϣ��,������������Ϣ����ʾ����
	clearInsurdInfo();
	DivLCInsuredInfo.style.display="";
	if(fm.ContNo.value==""){return true;}
//	var strSQL ="select insuredno,name,sex,birthday,relationtomaininsured,sequenceno "
//			+" from lcinsured where contno='"+fm.ContNo.value+"'";
	
	var sqlid8="DirectContInputSql8";
	var mySql8=new SqlClass();
	mySql8.setResourceName("app.DirectContInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
	mySql8.addSubPara(fm.ContNo.value);//ָ������Ĳ���
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
	//����"��ͬ��"��ѯ����������Ϣ����������Ӧ������
//	var sSQL="select contno,insuredno,sequenceno,relationtomaininsured,relationtoappnt,addressno,"
//			+"(select vipvalue from ldperson where customerno=insuredno),"
//			+"name,sex,birthday,idtype,idno,"
//			+"marriage,nativeplace,licensetype,occupationcode,occupationtype"
//			+" from lcinsured where contno='"+fm.ContNo.value+"' and insuredno='"+InsuredNo+"' ";
	
	var sqlid9="DirectContInputSql9";
	var mySql9=new SqlClass();
	mySql9.setResourceName("app.DirectContInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql9.setSqlId(sqlid9);//ָ��ʹ�õ�Sql��id
	mySql9.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	mySql9.addSubPara(InsuredNo);//ָ������Ĳ���
	var sSQL=mySql9.getString();
	
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
		//��ѯ�����˵ĵ�ַ��Ϣ
		if(document.all("InsuredNo").value=="" || document.all("InsuredAddressNo").value=="") {return;}
//		var sInsuredAddSQL="select customerno,addressno,province,city,county,postaladdress,zipcode,"
//				+ " mobile,companyphone,homephone,phone,fax,grpname,email"
//				+ " from lcaddress where customerno='"+document.all("InsuredNo").value+"' "
//				+ " and addressno='"+document.all("InsuredAddressNo").value+"'";
		
		var sqlid10="DirectContInputSql10";
		var mySql10=new SqlClass();
		mySql10.setResourceName("app.DirectContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql10.setSqlId(sqlid10);//ָ��ʹ�õ�Sql��id
		mySql10.addSubPara(document.all("InsuredNo").value);//ָ������Ĳ���
		mySql10.addSubPara(document.all("InsuredAddressNo").value);//ָ������Ĳ���
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

//����ѯ�����˸�֪��Ϣ�����������������˵ĸ�֪��Ϣ
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
	mySql11.setResourceName("app.DirectContInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql11.setSqlId(sqlid11);//ָ��ʹ�õ�Sql��id
	mySql11.addSubPara(InsuredNo);//ָ������Ĳ���
	mySql11.addSubPara(ContNo);//ָ������Ĳ���
	var strSQL=mySql11.getString();
    
	var turnPage = new turnPageClass();
	turnPage.queryModal(strSQL,ImpartInsuredGrid);
}
//����ѯ������������Ϣ����������ѯ������������Ϣ
function initqueryPolGrid()
{
	var InsuredNo=fm.InsuredNo.value;
    var ContNo=document.all("ContNo").value;
    if(ContNo=="" || InsuredNo=="") {return true;}
//   	var strSQL ="select polno,riskcode,(select riskname from lmriskapp where riskcode=lcpol.riskcode),prem,amnt from lcpol where 1=1 "
//   		+" and insuredno='"+InsuredNo+"' and contno='"+ContNo+"'"; 
   	
	var sqlid12="DirectContInputSql12";
	var mySql12=new SqlClass();
	mySql12.setResourceName("app.DirectContInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql12.setSqlId(sqlid12);//ָ��ʹ�õ�Sql��id
	mySql12.addSubPara(InsuredNo);//ָ������Ĳ���
	mySql12.addSubPara(ContNo);//ָ������Ĳ���
	var strSQL=mySql12.getString();
   	
	var turnPolPage = new turnPageClass();
	turnPolPage.queryModal(strSQL,PolGrid);
}

/*************************************************************************************
########################��ͬҳ������Ҫ�õ�У�麯��#########################################
**************************************************************************************/
//�����ͬ��Ϣ	
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

//У��Ͷ������
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
			alert("¼��ĺ�ͬͶ����������");
			fm.PolApplyDate.value="";
		    return ;
		}
	}
	//����ϵͳ��¼���Ͷ������У��
	if(checkPolDate(fm.ProposalContNo.value,fm.PolApplyDate.value)==false)
	{
		fm.PolApplyDate.value="";
		fm.PolApplyDate.focus();
		return;
	}
}

/*************************************************************************************
########################Ͷ����ҳ������Ҫ�õ�У�麯��#########################################
**************************************************************************************/
//���Ͷ������Ϣ	
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
//���������Ϣ
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
//��ȡͶ���� ְҵ�������ƺ�ְҵ���
function getAppOpName()
{
	fm.AppntOccupationCodeName.value = "";
	showOneCodeName('occupationcode','AppntOccupationCode','AppntOccupationCodeName');
//	var strSql = "select occupationcode,occupationname,occupationtype from ldoccupation where occupationcode='" + fm.AppntOccupationCode.value+"'";
	
	var sqlid13="DirectContInputSql13";
	var mySql13=new SqlClass();
	mySql13.setResourceName("app.DirectContInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql13.setSqlId(sqlid13);//ָ��ʹ�õ�Sql��id
	mySql13.addSubPara(fm.AppntOccupationCode.value);//ָ������Ĳ���
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


//��ȡͶ���˵ĵ�ַ��
function getAppntAddressNoData()
{
	var tCodeData = "0|";
//	strsql = "select addressno,postaladdress from lcaddress where customerno ='"+fm.AppntNo.value+"'";
	
	var sqlid14="DirectContInputSql14";
	var mySql14=new SqlClass();
	mySql14.setResourceName("app.DirectContInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql14.setSqlId(sqlid14);//ָ��ʹ�õ�Sql��id
	mySql14.addSubPara(fm.AppntNo.value);//ָ������Ĳ���
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
//У��Ͷ����֤������
function checkAppntIDNo()
{
	if(fm.AppntIDType.value=="" && fm.AppntIDNo.value!="")
	{
		alert("����ѡ��֤�����ͣ�");
		fm.AppntIDNo.value="";
		return ;
	}
	//�������֤��ȡ�����պ��Ա�
	if(document.all('AppntIDType').value=="0")
	{
		var tBirthday=getBirthdatByIdNo(fm.AppntIDNo.value);
		var tSex=getSexByIDNo(fm.AppntIDNo.value);
		if(tBirthday=="��������֤��λ������"||tSex=="��������֤��λ������")
		{
			alert("��������֤��λ������");
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
//Ͷ���˳�������У�麯��
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
			//��ȡͶ��������<Ͷ������ ���� Ͷ���˳�������>
			fm.AppntAge.value=calPolAppntAge(fm.AppntBirthday.value,fm.PolApplyDate.value);
		}
	    else
		{
			alert("¼���Ͷ���˳�����������");
			fm.AppntBirthday.value="";
			fm.AppntAge.value="";
		    return ;
		}
	}
}

//���ס������˺�һ�¡���ѡť---------���ѡ���������ʺ���Ϣ��ֵ�������ʺ�
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
########################������ҳ������Ҫ�õ�У�麯��#########################################
**************************************************************************************/
//��ȡ������ ְҵ�������ƺ�ְҵ���
function getInsuredOpName()
{
	fm.InsuredOccupationCodeName.value = "";
	showOneCodeName('occupationcode','InsuredOccupationCode','InsuredOccupationCodeName');
//	var strSql = "select occupationcode,occupationname,occupationtype from ldoccupation where occupationcode='" + fm.InsuredOccupationCode.value+"'";
	
	var sqlid15="DirectContInputSql15";
	var mySql15=new SqlClass();
	mySql15.setResourceName("app.DirectContInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql15.setSqlId(sqlid15);//ָ��ʹ�õ�Sql��id
	mySql15.addSubPara(fm.InsuredOccupationCode.value);//ָ������Ĳ���
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

//��ȡ�����˵ĵ�ַ��
function getInsuredAddressNoData()
{
	var tCodeData = "0|";
//	strsql = "select addressno,postaladdress from lcaddress where customerno ='"+fm.InsuredNo.value+"'";
	
	var sqlid16="DirectContInputSql16";
	var mySql16=new SqlClass();
	mySql16.setResourceName("app.DirectContInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql16.setSqlId(sqlid16);//ָ��ʹ�õ�Sql��id
	mySql16.addSubPara(fm.InsuredNo.value);//ָ������Ĳ���
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

//�����������Ϣ
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
//У�鱻����֤������
function checkInsuredIDNo()
{
	if(fm.InsuredIDType.value=="" && fm.InsuredIDNo.value!="")
	{
		alert("����ѡ��֤�����ͣ�");
		fm.InsuredIDNo.value="";
		return ;
	}
	//�������֤��ȡ�����պ��Ա�
	if(document.all('InsuredIDType').value=="0")
	{
		var tBirthday=getBirthdatByIdNo(fm.InsuredIDNo.value);
		var tSex=getSexByIDNo(fm.InsuredIDNo.value);
		if(tBirthday=="��������֤��λ������"||tSex=="��������֤��λ������")
		{
			alert("��������֤��λ������");
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

//�����˳�������У�麯��
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
			//��ȡ����������<Ͷ������ ���� Ͷ���˳�������>
			fm.InsuredAppAge.value=calPolAppntAge(fm.InsuredBirthday.value,fm.PolApplyDate.value);
		}
	    else
		{
			alert("¼��ı����˳�����������");
			fm.InsuredBirthday.value="";
			fm.InsuredAppAge.value="";
		    return ;
		}
	}
}
/*************************************************************************************
#####################################################################################
**************************************************************************************/

//������Ա��ѯ����������������ѯ����Ա�����Ϣ��������Ӧ����Ŀ
function queryAgent()
{
	if(document.all('ManageCom').value=="")
	{
		alert("����¼����������Ϣ��");
		return;
	}
	if(document.all('AgentCode').value == "")	
	{
		//laagent.branchtype = '5' and laagent.branchtype2 = '02'--����Ա
	    //laagent.branchtype = '5' and laagent.branchtype2 = '01'--����Ա
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
		mySql17.setResourceName("app.DirectContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql17.setSqlId(sqlid17);//ָ��ʹ�õ�Sql��id
		mySql17.addSubPara(cAgentCode);//ָ������Ĳ���
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
			alert("����Ϊ:["+document.all('AgentCode').value+"]������Ա�����ڣ���ȷ��!");
			fm.AgentCode.value="";
			fm.AgentBranchAttr.value = "";
			fm.AgentGroup.value = "";
			fm.AgentName.value = "";
			fm.AgentManageCom.value = "";
		}
	}
}

//������Ա��ѯ����������������ѯ����Ա�����Ϣ��������Ӧ����Ŀ
function queryTelephonist()
{
	if(document.all('ManageCom').value=="")
	{
		alert("����¼����������Ϣ��");
		return;
	}
	if(document.all('TelephonistCode').value == "")	
	{
		//laagent.branchtype = '5' and laagent.branchtype2 = '02'--����Ա
	    //laagent.branchtype = '5' and laagent.branchtype2 = '01'--����Ա
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
		mySql18.setResourceName("app.DirectContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql18.setSqlId(sqlid18);//ָ��ʹ�õ�Sql��id
		mySql18.addSubPara(cAgentCode);//ָ������Ĳ���
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
			alert("����Ϊ:["+document.all('TelephonistCode').value+"]�Ļ���Ա�����ڣ���ȷ��!");
			fm.TelephonistCode.value="";
			fm.TelephBranchAttr.value = "";
			fm.TelephGroup.value = "";
			fm.TelephonistName.value = "";
			fm.TelephManageCom.value = "";
		}
	}
}


//��ѯ��ҵ��Ա������ʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
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

//��ѯ������Ա������ʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
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

//��Ͷ���˲�ѯ����ť---------��ѯͶ����
//���û��Ͷ���˿ͻ��ţ�������ѯ���棬�������"Ͷ���˿ͻ���"��ѯ������Ϣ����������Ӧ������
function queryAppnt()
{
	if (LoadFlag != "1" && LoadFlag != "2")
	{
		alert("ֻ����Ͷ����¼��ʱ���в�����");
		return ;
	}
	else if (trim(document.all("AppntNo").value) == "" && LoadFlag == "1")
	{	//�����ѯ����
		showInfo = window.open( "../sys/LDPersonQueryNewMain.jsp?queryFlag=queryAppnt" );
	}
	else
	{
		queryAppntNo(trim(document.all("AppntNo").value));//����"Ͷ���˿ͻ���"��ѯ������Ϣ����������Ӧ������
	}
}
//��Ͷ���˲�ѯ������������������"Ͷ���˿ͻ���"��ѯ������Ϣ����������Ӧ������
function queryAppntNo(AppntNo)
{
	var tAppntNo=trim(AppntNo);
//	var sSQL="select vipvalue,customerno,name,sex,birthday,idtype,idno,marriage,nativeplace,licensetype,occupationcode,occupationtype"
//	 	+" from ldperson where customerno='"+ tAppntNo +"'";
	
	var sqlid19="DirectContInputSql19";
	var mySql19=new SqlClass();
	mySql19.setResourceName("app.DirectContInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql19.setSqlId(sqlid19);//ָ��ʹ�õ�Sql��id
	mySql19.addSubPara(tAppntNo);//ָ������Ĳ���
	var sSQL=mySql19.getString();
	
	var arrApp=easyExecSql(sSQL);
	if(arrApp==null)
	{
		alert("�ñ����˺��벻���ڣ�");
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
		getAppOpName();//��ȡͶ����ְҵ����-����,ְҵ������-����
		getAppntAge();	
	}
}

//�������˲�ѯ����ť---------��ѯ������
//���û�б����˿ͻ��ţ�������ѯ���棬�������"�����˿ͻ���"��ѯ������Ϣ����������Ӧ������
function queryInsured()
{
	
	if (LoadFlag != "1" && LoadFlag != "2")
	{
		alert("ֻ����Ͷ����¼��ʱ���в�����");
		return ;
	}
	else if (trim(document.all("InsuredNo").value) == "" && LoadFlag == "1")
	{   //�����ѯ����
		showInfo = window.open( "../sys/LDPersonQueryNewMain.jsp?queryFlag=queryInsured" );
	}
	else
	{
		queryInsuredNo(trim(document.all("InsuredNo").value));//����"Ͷ���˿ͻ���"��ѯ������Ϣ����������Ӧ������
	}
}

//��Ͷ���˲�ѯ������������������"Ͷ���˿ͻ���"��ѯ������Ϣ����������Ӧ������
function queryInsuredNo(InsuredNo)
{
	var tInsuredNo=trim(InsuredNo);
//	var sSQL="select vipvalue,customerno,name,sex,birthday,idtype,idno,marriage,nativeplace,licensetype,occupationcode,occupationtype"
//	 	+" from ldperson where customerno='"+ tInsuredNo +"'";
	
	var sqlid20="DirectContInputSql20";
	var mySql20=new SqlClass();
	mySql20.setResourceName("app.DirectContInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql20.setSqlId(sqlid20);//ָ��ʹ�õ�Sql��id
	mySql20.addSubPara(tInsuredNo);//ָ������Ĳ���
	var sSQL=mySql20.getString();
	
	var arrInsured=easyExecSql(sSQL);
	if(arrInsured==null)
	{
		alert("�ñ����˺��벻���ڣ�");
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
//���ҳ�淵��ʱ�Ľ������----Ŀǰֻ�� Ͷ���˲�ѯ���صĽ��,��������
function afterQuery(arrQueryResult)
{
	document.all("AppntNo").value=arrQueryResult[0][0];
	queryAppntNo(trim(document.all("AppntNo").value));
}
//���ҳ�淵��ʱ�Ľ������----Ŀǰֻ�� �����˲�ѯ���صĽ��,��������
function afterQuery21( arrQueryResult )
{
	document.all("InsuredNo").value=arrQueryResult[0][0];
	queryInsuredNo(trim(document.all("InsuredNo").value));
}

//����ѡ�� Ͷ����Ϊ�������˱����� ʱ����Ӧ����
function IsSamePerson()
{
	var SamePersonFlag=fm.SamePersonFlag.checked;
	if(SamePersonFlag==true)
	{
		//������ͬͶ���ˣ���Ͷ������Ϣ���뱻������Ϣ���У�������������Ϣ������
		fm.RelationToAppnt.value="00";
		showOneCodeName('Relation','RelationToAppnt','RelationToAppntName');
		getInsureFromAppnt();
		DivLCInsuredInfo.style.display="none";
	}
	else
	{
		//���������������Ϣ��,������������Ϣ����ʾ����
		fm.RelationToAppnt.value="";
		fm.RelationToAppntName.value="";
		clearInsurdInfo();
		DivLCInsuredInfo.style.display="";
	}
}

//��Ͷ������Ϣ���뱻������Ϣ���У�������������Ϣ������
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

//�����б�˫�������Ӧ����
function afterCodeSelect(CodeName, Field)
{
	//��ȡͶ���˵�ַ
	if(CodeName=="GetAppntAddressNo")
	{
//		var strSQL=" select customerno,addressno,province,city,county,postaladdress,zipcode,"
//		    +" mobile,companyphone,homephone,phone,fax,grpname,email "
//			+" from lcaddress where addressno='"+fm.AppntAddressNo.value+"' and customerno='"+fm.AppntNo.value+"'";
		
		var sqlid21="DirectContInputSql21";
		var mySql21=new SqlClass();
		mySql21.setResourceName("app.DirectContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql21.setSqlId(sqlid21);//ָ��ʹ�õ�Sql��id
		mySql21.addSubPara(fm.AppntAddressNo.value);//ָ������Ĳ���
		mySql21.addSubPara(fm.AppntNo.value);//ָ������Ĳ���
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
	
	//��ȡ�����˵�ַ
	if(CodeName=="GetInsuredAddressNo")
	{
		
//		var strSQL=" select customerno,addressno,province,city,county,postaladdress,zipcode,"
//		    +" mobile,companyphone,homephone,phone,fax,grpname,email "
//			+" from lcaddress where addressno='"+fm.InsuredAddressNo.value+"' and customerno='"+fm.InsuredNo.value+"'";
        
		var sqlid22="DirectContInputSql22";
		var mySql22=new SqlClass();
		mySql22.setResourceName("app.DirectContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql22.setSqlId(sqlid22);//ָ��ʹ�õ�Sql��id
		mySql22.addSubPara(fm.InsuredAddressNo.value);//ָ������Ĳ���
		mySql22.addSubPara(fm.InsuredNo.value);//ָ������Ĳ���
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
        ����Ϊ�����ϵİ�ť��������
******************************************************************************************/
//�жϴ������Ƿ�¼��<����Ա�뻰��Ա��������¼��һ��>,����Ӷ������ĵ���
function checkAgentInput()
{
	//�жϴ������Ƿ�¼��<����Ա�뻰��Ա��������¼��һ��>
	if(fm.AgentCode.value=="" && fm.TelephonistCode.value=="")
	{
		alert("����Ա�뻰��Ա�����Ϊ�ա�\n����Ա�뻰��Ա��������¼��һ����");
		fm.AgentBusiRate.value ="0.00";//����ԱӶ�����
		fm.TeleBusiRate.value  ="0.00";//����ԱӶ�����
		return false;
	}
	else if(fm.AgentCode.value!="" && fm.TelephonistCode.value!="")
	{//����Ա�뻰��Ա��¼������
		fm.AgentBusiRate.value ="0.50";//����ԱӶ�����
		fm.TeleBusiRate.value  ="0.50";//����ԱӶ�����
	}
	else if(fm.AgentCode.value!="" && fm.TelephonistCode.value=="")
	{//���ֻ¼������Ա�����
		fm.AgentBusiRate.value ="1.00";//����ԱӶ�����
		fm.TeleBusiRate.value  ="0.00";//����ԱӶ�����
	}
	else if(fm.AgentCode.value=="" && fm.TelephonistCode.value!="")
	{//���ֻ¼�뻰��Ա�����
	
		fm.AgentBusiRate.value ="0.00";//����ԱӶ�����
		fm.TeleBusiRate.value  ="1.00";//����ԱӶ�����
	}
	else {}
	return true;
}

//�����桿��ť������������¼���ǵĺ�ͬ��Ϣ��Ͷ������Ϣ��������Ϣ
function addClick()
{
	if(verifyWorkFlow(MissionID,SubMissionID,ActivityID)==false)
	{
		return false;
	}/////////////////У�鹫�����Ƿ��Ѿ���ת���
	if( verifyInputNB("1") == false ) {return false;}
	//�жϴ������Ƿ�¼��<����Ա�뻰��Ա��������¼��һ��>,����Ӷ������ĵ���
	if(checkAgentInput()==false) {return false;}
	//�ж��Ƿ��Ѿ���ӹ���ͬ��Ϣ��Ͷ����
	var ContNo=document.all("ContNo").value;
//	var sqlstr="select forceuwflag from lccont where contno='"+ContNo+"'" ;
	
	var sqlid23="DirectContInputSql23";
	var mySql23=new SqlClass();
	mySql23.setResourceName("app.DirectContInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql23.setSqlId(sqlid23);//ָ��ʹ�õ�Sql��id
	mySql23.addSubPara(ContNo);//ָ������Ĳ���
	var sqlstr=mySql23.getString();
	
	arrResult = easyExecSql(sqlstr,1,0);
	if(arrResult!=null)
	{
		alert("��ͬ��Ϣ��Ͷ������Ϣ�Ѿ����棡");
		return false;
	}
	//Ͷ���˿ͻ���Ϊ�գ������е�ַ����
	if(fm.AppntNo.value==''&&fm.AppntAddressNo.value!='')
	{
	alert("Ͷ���˿ͻ���Ϊ�գ������е�ַ����");
	return false;
	}
	var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");	
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	document.all( 'fmAction' ).value="INSERT";
	fm.action="./DirectContSave.jsp"
	fm.submit(); //�ύ
}

//���޸ġ���ť������������¼���ǵĺ�ͬ��Ϣ��Ͷ������Ϣ��������Ϣ
function updateClick()
{
	if(verifyWorkFlow(MissionID,SubMissionID,ActivityID)==false)
	{
		return false;
	}/////////////////У�鹫�����Ƿ��Ѿ���ת���
	if( verifyInputNB("1") == false ) return false;	
	//�жϴ������Ƿ�¼��<����Ա�뻰��Ա��������¼��һ��>,����Ӷ������ĵ���
	if(checkAgentInput()==false) {return false;}
	//�ж��Ƿ��Ѿ���ӹ���ͬ��Ϣ��Ͷ����
	var ContNo=document.all("ContNo").value;
//	var sqlstr="select forceuwflag from lccont where contno='"+ContNo+"'" ;
	
	var sqlid24="DirectContInputSql24";
	var mySql24=new SqlClass();
	mySql24.setResourceName("app.DirectContInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql24.setSqlId(sqlid24);//ָ��ʹ�õ�Sql��id
	mySql24.addSubPara(ContNo);//ָ������Ĳ���
	var sqlstr=mySql24.getString();
	
	arrResult = easyExecSql(sqlstr,1,0);
	if(arrResult==null)
	{
		alert("������Ӻ�ͬ��Ϣ��Ͷ������Ϣ��");
		return false;
	}
	//Ͷ���˿ͻ���Ϊ�գ������е�ַ����
	if(fm.AppntNo.value==''&&fm.AppntAddressNo.value!='')
	{
	alert("Ͷ���˿ͻ���Ϊ�գ������е�ַ����");
	return false;
	}
	
	var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");	
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	document.all( 'fmAction' ).value="UPDATE";
	fm.action="./DirectContSave.jsp"
	fm.submit(); //�ύ
}

//��ɾ������ť��������ɾ��¼���ǵĺ�ͬ��Ϣ��Ͷ������Ϣ��������Ϣ
function deleteClick()
{
	if(verifyWorkFlow(MissionID,SubMissionID,ActivityID)==false)
	{
		return false;
	}/////////////////У�鹫�����Ƿ��Ѿ���ת���
	//�ж��Ƿ��Ѿ���ӹ���ͬ��Ϣ��Ͷ����
	var ContNo=document.all("ContNo").value;
//	var sqlstr="select forceuwflag from lccont where contno='"+ContNo+"'" ;
	
	var sqlid25="DirectContInputSql25";
	var mySql25=new SqlClass();
	mySql25.setResourceName("app.DirectContInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql25.setSqlId(sqlid25);//ָ��ʹ�õ�Sql��id
	mySql25.addSubPara(ContNo);//ָ������Ĳ���
	var sqlstr=mySql25.getString();
	
	arrResult = easyExecSql(sqlstr,1,0);
	if(arrResult==null)
	{
		alert("������Ӻ�ͬ��Ϣ��Ͷ������Ϣ��");
		return false;
	}
	var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");	
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	
	document.all( 'fmAction' ).value="DELETE";
	fm.action="./DirectContSave.jsp"
	fm.submit(); //�ύ
}

//����ӱ����ˡ�
function addRecord()
{
	if(verifyWorkFlow(MissionID,SubMissionID,ActivityID)==false)
	{
		return false;
	}/////////////////У�鹫�����Ƿ��Ѿ���ת���
	//�ж��Ƿ��Ѿ���ӹ���ͬ��Ϣ��Ͷ����
	var ContNo=document.all("ContNo").value;
//	var sqlstr="select forceuwflag from lccont where contno='"+ContNo+"'" ;
	
	var sqlid26="DirectContInputSql26";
	var mySql26=new SqlClass();
	mySql26.setResourceName("app.DirectContInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql26.setSqlId(sqlid26);//ָ��ʹ�õ�Sql��id
	mySql26.addSubPara(ContNo);//ָ������Ĳ���
	var sqlstr=mySql26.getString();
	
	arrResult = easyExecSql(sqlstr,1,0);
	if(arrResult==null)
	{
		alert("������Ӻ�ͬ��Ϣ��Ͷ������Ϣ��");
		return false;
	}	
	//�������˿ͻ���Ϊ�գ������е�ַ����
	if(fm.InsuredNo.value==''&&fm.InsuredAddressNo.value!='')
	{
	alert("�������˿ͻ���Ϊ�գ������е�ַ����");
	return false;
	}
	//У���¼��Ŀ
	if( verifyInputNB('2') == false ) return false;
	//�ж���������Ϣ�Ƿ񱣴�
//	var sSQL="select 'X' from lcinsured where 1=1 and sequenceno='"+fm.SequenceNo.value+"'"
//		+" and contno='"+fm.ContNo.value+"' and insuredno='"+fm.InsuredNo.value+"'";
	
	var sqlid27="DirectContInputSql27";
	var mySql27=new SqlClass();
	mySql27.setResourceName("app.DirectContInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql27.setSqlId(sqlid27);//ָ��ʹ�õ�Sql��id
	mySql27.addSubPara(fm.SequenceNo.value);//ָ������Ĳ���
	mySql27.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	mySql27.addSubPara(fm.InsuredNo.value);//ָ������Ĳ���
	var sSQL=mySql27.getString();
	
	var arrInsured = easyExecSql(sSQL,1,0);
	if(arrInsured!=null)
	{
		alert("�ñ�������Ϣ�Ѿ����� �� �ÿͻ��ڲ������Ѿ����ڣ�");
		return false;
	}	
	var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");	
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	
	document.all('ContType').value=ContType;
	document.all( 'BQFlag' ).value = BQFlag;
	fm.action="DirectContInsuredSave.jsp";
	document.all('fmAction').value="INSERT||CONTINSURED";
	fm.submit();
}

//���޸ı����ˡ�
function modifyRecord()
{
	if(verifyWorkFlow(MissionID,SubMissionID,ActivityID)==false)
	{
		return false;
	}/////////////////У�鹫�����Ƿ��Ѿ���ת���
	//�ж��Ƿ��Ѿ���ӹ���ͬ��Ϣ��Ͷ����
	var ContNo=document.all("ContNo").value;
//	var sqlstr="select forceuwflag from lccont where contno='"+ContNo+"'" ;
	
	var sqlid28="DirectContInputSql28";
	var mySql28=new SqlClass();
	mySql28.setResourceName("app.DirectContInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql28.setSqlId(sqlid28);//ָ��ʹ�õ�Sql��id
	mySql28.addSubPara(ContNo);//ָ������Ĳ���
	var sqlstr=mySql28.getString();
	
	arrResult = easyExecSql(sqlstr,1,0);
	if(arrResult==null)
	{
		alert("������Ӻ�ͬ��Ϣ��Ͷ������Ϣ��");
		return false;
	}	
	//У���¼��Ŀ
	if (InsuredGrid.mulLineCount==0)
	{
		alert("�ñ������˻�û�б��棬�޷������޸ģ�");
		return false;
	}
	if(fm.InsuredNo.value==''&&fm.InsuredAddressNo.value!='')
	{
	alert("�������˿ͻ���Ϊ�գ������е�ַ����");
	return false;
	}
	if( verifyInputNB('2') == false ) return false;

	var tselno=InsuredGrid.getSelNo();
	if(tselno==0)
	{
		alert("��ѡ����Ҫ�޸ı����˼�¼");
		return false;
	}
	var tOldInsuredNo=InsuredGrid.getRowColData(tselno-1,1);
	//�ж��Ƿ�����˱����˵Ĺؼ���Ϣ
//	var SSQL=" select insuredno,name,sex,birthday,idtype,idno,occupationcode"
//		+" from lcinsured where contno='"+fm.ContNo.value+"' and insuredno='"+tOldInsuredNo+"'";
	
	var sqlid29="DirectContInputSql29";
	var mySql29=new SqlClass();
	mySql29.setResourceName("app.DirectContInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql29.setSqlId(sqlid29);//ָ��ʹ�õ�Sql��id
	mySql29.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	mySql29.addSubPara(tOldInsuredNo);//ָ������Ĳ���
	var SSQL=mySql29.getString();
	
	var tOldArr=easyExecSql(SSQL,1,0);
	//��ȡ׼���޸ĵ���Ϣ,ͬ��ѯ������Ϣ�Ƚϣ������һ�ͬ����ô��ѯ�Ƿ������֣�������ʾ����ɾ��������Ϣ
	if( fm.InsuredName.value!=tOldArr[0][1] || fm.InsuredIDType.value!=tOldArr[0][4] || fm.InsuredIDNo.value!=tOldArr[0][5] 
	  || fm.InsuredSex.value!=tOldArr[0][2] || fm.InsuredBirthday.value!=tOldArr[0][3] || fm.InsuredOccupationCode.value!=tOldArr[0][6])
	{
//		var sqlstr="select polno from lcpol where contno='"+fm.ContNo.value+"' and insuredno='"+tOldInsuredNo+"' ";
		
		var sqlid30="DirectContInputSql30";
		var mySql30=new SqlClass();
		mySql30.setResourceName("app.DirectContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql30.setSqlId(sqlid30);//ָ��ʹ�õ�Sql��id
		mySql30.addSubPara(fm.ContNo.value);//ָ������Ĳ���
		mySql30.addSubPara(tOldInsuredNo);//ָ������Ĳ���
		var sqlstr=mySql30.getString();
		
		arrResult=easyExecSql(sqlstr,1,0);
		if(arrResult!=null)
		{
			alert("������޸��˸ñ����˹ؼ���Ϣ:\n<�������Ա�֤�����ͻ���롢��������>��ְҵ���\n����ɾ���ñ������µ�������Ϣ��");
			return false;    
		}
	}

	var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");	
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
		
	document.all('ContType').value=ContType;
	document.all( 'BQFlag' ).value = BQFlag;
	document.all('fmAction').value="UPDATE||CONTINSURED";
	fm.action="DirectContInsuredSave.jsp";
	fm.submit();
}

//��ɾ�������ˡ�
function deleteRecord()
{
	if(verifyWorkFlow(MissionID,SubMissionID,ActivityID)==false)
	{
		return false;
	}/////////////////У�鹫�����Ƿ��Ѿ���ת���
	//�жϸñ������Ƿ��Ѿ�����
//	var sSQL="select 'X' from lcinsured where contno='"+fm.ContNo.value+"' and insuredno='"+fm.InsuredNo.value+"'";
	
	var sqlid31="DirectContInputSql31";
	var mySql31=new SqlClass();
	mySql31.setResourceName("app.DirectContInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql31.setSqlId(sqlid31);//ָ��ʹ�õ�Sql��id
	mySql31.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	mySql31.addSubPara(fm.InsuredNo.value);//ָ������Ĳ���
	var sSQL=mySql31.getString();
	
	var arrInsure = easyExecSql(sSQL,1,0);
	if(arrInsure==null)
	{
		alert("û�б����˻򱻱�����Ϣ��δ���棬���ȱ��汻������Ϣ��");
		return false;
	}	
	if(PolGrid.mulLineCount>0)
	{
		alert("�ñ������»���������Ϣ�����Ҫɾ�������ˣ�����ɾ��������Ϣ��");
		return false;
	}
	//�ύ		
	var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");	
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	
	document.all('ContType').value=ContType;
	document.all( 'BQFlag' ).value = BQFlag;
	document.all('fmAction').value="DELETE||CONTINSURED";
	fm.action="DirectContInsuredSave.jsp";
	fm.submit();
}


//�����Ӱ���ѯ
function QuestPicQuery()
{
	var ContNo = fm.ContNo.value;
	if (ContNo == "") return;
	window.open("../uw/ImageQueryMain.jsp?ContNo="+ContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");
}
//�����¼��
function QuestInput()
{
	if(verifyWorkFlow(MissionID,SubMissionID,ActivityID)==false)
	{
		return false;
	}/////////////////У�鹫�����Ƿ��Ѿ���ת���
	cContNo = fm.ContNo.value;  //�������� 
	if(cContNo == "")
	{
		alert("���޺�ͬͶ�����ţ����ȱ���!");
		return;
	}
	else
	{
		window.open("../uw/QuestInputMain.jsp?ContNo="+cContNo+"&Flag="+ LoadFlag+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID,"window1");
	}
}
//�������ѯ
function QuestQuery()
{
	cContNo = fm.ContNo.value;  //�������� 
	if(cContNo == "")
	{
		alert("���޺�ͬͶ�����ţ����ȱ���!");
	}
	else
	{
		window.open("../uw/QuestQueryMain.jsp?ContNo="+cContNo+"&Flag="+LoadFlag,"window1");
	}
}
//Ͷ����У��
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
	mySql32.setResourceName("app.DirectContInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql32.setSqlId(sqlid32);//ָ��ʹ�õ�Sql��id
	mySql32.addSubPara(fm.AppntName.value);//ָ������Ĳ���
	mySql32.addSubPara(fm.AppntNo.value);//ָ������Ĳ���
	mySql32.addSubPara(Sex);//ָ������Ĳ���
	mySql32.addSubPara(fm.AppntBirthday.value);//ָ������Ĳ���
	mySql32.addSubPara(fm.AppntIDNo.value);//ָ������Ĳ���
	mySql32.addSubPara(fm.AppntNo.value);//ָ������Ĳ���
	var sqlstr=mySql32.getString();
	
	arrResult = easyExecSql(sqlstr,1,0);
	if(arrResult==null)
	{
	alert("��û�����Ͷ�������ƵĿͻ�,����У��");
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
		alert("����ѡ�񱻱����ˣ�");
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
	mySql33.setResourceName("app.DirectContInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql33.setSqlId(sqlid33);//ָ��ʹ�õ�Sql��id
	mySql33.addSubPara(tInsuredName);//ָ������Ĳ���
	mySql33.addSubPara(tInsuredNo);//ָ������Ĳ���
	mySql33.addSubPara(tInsuredSex);//ָ������Ĳ���
	mySql33.addSubPara(tBirthday);//ָ������Ĳ���
	mySql33.addSubPara(fm.InsuredIDNo.value);//ָ������Ĳ���
	mySql33.addSubPara(tInsuredNo);//ָ������Ĳ���
	var sqlstr=mySql33.getString();
	
	var arrResult = easyExecSql(sqlstr,1,0);
	if(arrResult==null)
	{
	alert("��û����ñ����˱������ƵĿͻ�,����У��");
	return false;
	}
	window.open("../uw/AppntChkMain.jsp?ProposalNo1="+fm.ContNo.value+"&Flag=I&LoadFlag="+LoadFlag+"&InsuredNo="+tInsuredNo,"window1");
}

/*********************************************************************
 *  PolGrid��RadioBox����¼�����ñ�����������ϸ��Ϣ
 ********************************************************************/
function getPolDetail(parm1,parm2)
{
    var PolNo=document.all(parm1).all('PolGrid1').value
    try{mSwitch.deleteVar('PolNo')}catch(ex){};
    try{mSwitch.addVar('PolNo','',PolNo);}catch(ex){};
    fm.SelPolNo.value=PolNo;
}

/*********************************************************************
 *  ����������Ϣ¼��
 ********************************************************************/
function intoRiskInfo()
{
	//У���ͬ��Ϣ��Ͷ������Ϣ����������Ϣ�Ƿ��Ѿ����棨��
//	var strSQL="select 'X' from dual where 1=1" 
//  		+" and exists (select 'X' from lccont where contno='"+fm.ContNo.value+"')"
//  		+" and exists (select 'X' from lcappnt where contno='"+fm.ContNo.value+"')" 
//  		+" and exists (select 'X' from lcinsured where contno='"+fm.ContNo.value+"' and insuredno='"+fm.InsuredNo.value+"')";  
	
	var sqlid34="DirectContInputSql34";
	var mySql34=new SqlClass();
	mySql34.setResourceName("app.DirectContInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql34.setSqlId(sqlid34);//ָ��ʹ�õ�Sql��id
	mySql34.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	mySql34.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	mySql34.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	mySql34.addSubPara(fm.InsuredNo.value);//ָ������Ĳ���
 	var strSQL=mySql34.getString();
	
	var arrResult = easyExecSql(strSQL); 
	if(arrResult==null)
	{
		var errInfo="���ܷ������´���\n 1����ͬ��Ϣ��Ͷ������Ϣû�б��档\n 2����������Ϣû�б��档";
		alert(errInfo);
		return false;
	}
	////�Ѻ�ͬ��Ϣ�ͱ�������Ϣ�����ڴ�
	mSwitch = parent.VD.gVSwitch;  //���ݴ�
	delContVar();
	addIntoCont();
	delInsuredVar();
	addInsuredVar();
	try{mSwitch.deleteVar('mainRiskPolNo');}catch(ex){}	
	try{mSwitch.deleteVar('ContNo');}catch(ex){}
	try{mSwitch.addVar('ContNo','',fm.ContNo.value);}catch(ex){}
	try{mSwitch.deleteVar('CValiDate');}catch(ex){}
	try{mSwitch.addVar('CValiDate','',document.all('PolApplyDate').value);}catch(ex){}
	//ѡ�����ֵ��������ֽ�������ѱ������Ϣ
	try{mSwitch.deleteVar('PolNo')}catch(ex){};
    try{mSwitch.addVar('PolNo','',fm.SelPolNo.value);}catch(ex){};
	if ((LoadFlag=='5'||LoadFlag=='4'||LoadFlag=='6'||LoadFlag=='16' || LoadFlag=='25')&&(mSwitch.getVar( "PolNo" ) == null || mSwitch.getVar( "PolNo" ) == ""))
	{
		alert("����ѡ�񱻱�����������Ϣ��");
		return;
	}
	else if((LoadFlag=='1'||LoadFlag=='3') && (PolGrid.mulLineCount>0))
	{	//������ƣ���¼����������޸�ʱ������������ֱ�����ѡ��һ�����ֺ���ܽ���������Ϣ
		var tSelPol=PolGrid.getSelNo();
		if(tSelPol==null || tSelPol==0)
		{
			alert("����ѡ��һ�����ֺ��ٽ���������Ϣҳ�棡");
			return;
		}
	}
	else
	{}

	//����׼���������ֽ���
	var strUrl="./ProposalInput.jsp?LoadFlag=" + LoadFlag+"&ContType="+ContType+"&scantype=" + scantype;
	strUrl=strUrl+"&MissionID=" + MissionID+ "&SubMissionID=" + SubMissionID+"&ActivityID="+ActivityID;
	strUrl=strUrl+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&NoType="+NoType+"&hh=1&checktype="+checktype;
	strUrl=strUrl+"&ProposalContNo="+fm.ProposalContNo.value;
	strUrl=strUrl+"&ScanFlag="+ScanFlag+"&BankFlag=5";
	strUrl=strUrl+"&InsuredChkFlag="+document.all('InsuredChkButton').disabled;
	strUrl=strUrl+"&AppntChkFlag="+document.all('AppntChkButton').disabled;
  	parent.fraInterface.window.location =strUrl;
}
//ɾ������
function DelRiskInfo()
{
	if(verifyWorkFlow(MissionID,SubMissionID,ActivityID)==false)
	{
		return false;
	}/////////////////У�鹫�����Ƿ��Ѿ���ת���
	if(fm.InsuredNo.value=="")
	{
		alert("����ѡ�񱻱���");
		return false;
	}
	var tSel =PolGrid.getSelNo();
	if( tSel == 0 || tSel == null )
	{
		alert("�ÿͻ�û�����ֻ���������ѡ���ˣ�");
		return false;
	}
	else
	{
		var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
		//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");	
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		var tpolno=PolGrid.getRowColData(tSel-1,1)
		document.all('fmAction').value="DELETE||INSUREDRISK";
		fm.action="./DirectDelRiskSave.jsp?polno="+tpolno;
		fm.submit(); //�ύ
	}
	
}
//��ѯ���±�
function checkNotePad(prtNo,LoadFlag)
{
//	var strSQL="select count(*) from LWNotePad where otherno='"+prtNo+"'";
	
	var sqlid35="DirectContInputSql35";
	var mySql35=new SqlClass();
	mySql35.setResourceName("app.DirectContInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql35.setSqlId(sqlid35);//ָ��ʹ�õ�Sql��id
	mySql35.addSubPara(prtNo);//ָ������Ĳ���
 	var strSQL=mySql35.getString();
	
	var arrResult = easyExecSql(strSQL);
	eval("document.all('NotePadButton"+LoadFlag+"').value='���±��鿴 ����"+arrResult[0][0]+"����'");
}
//���±��鿴
function showNotePad()
{
	var tPrtNo = document.all.PrtNo.value;
	if(tPrtNo == null || tPrtNo == "")
	{
		alert("Ͷ������Ϊ�գ�");
		return;
	} 
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ tPrtNo + "&NoType=1";
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");
}


/*********************************************************************
 *  �Ѻ�ͬ������Ϣ¼�����ȷ��
*********************************************************************/
function inputConfirm(wFlag)
{
    if(checkOtherQuest()==false)
       return false;
	//У���ͬ��Ϣ��Ͷ������Ϣ����������Ϣ�Ƿ��Ѿ����棨��
//	var strSQL="select 'X' from dual where 1=1" 
//  		+" and exists (select 'X' from lccont where contno='"+fm.ContNo.value+"')"
//  		+" and exists (select 'X' from lcappnt where contno='"+fm.ContNo.value+"')" 
//  		+" and exists (select 'X' from lcinsured where contno='"+fm.ContNo.value+"')"
//  		+" and exists (select 'X' from lcpol where contno='"+fm.ContNo.value+"')" ;
	
	var sqlid36="DirectContInputSql36";
	var mySql36=new SqlClass();
	mySql36.setResourceName("app.DirectContInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql36.setSqlId(sqlid36);//ָ��ʹ�õ�Sql��id
	mySql36.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	mySql36.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	mySql36.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	mySql36.addSubPara(fm.ContNo.value);//ָ������Ĳ���
 	var strSQL=mySql36.getString();
	
	var arrResult = easyExecSql(strSQL); 
	if(arrResult==null)
	{
		var errInfo="���ܷ������´���\n 1����ͬ��Ϣ��Ͷ������Ϣû�б��档\n 2����������Ϣû�б��档\n 3��������Ϣδ���档";
		alert(errInfo);
		return false;
	}
	
	if (wFlag ==1 ) //¼�����ȷ��
	{
//		var tStr= "	select * from lwmission where 1=1 and lwmission.activityid in ('0000001001','0000001002','0000001220','0000001230') and lwmission.missionprop1 = '"+fm.ContNo.value+"'";
		
		var sqlid37="DirectContInputSql37";
		var mySql37=new SqlClass();
		mySql37.setResourceName("app.DirectContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql37.setSqlId(sqlid37);//ָ��ʹ�õ�Sql��id
		mySql37.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	 	var tStr=mySql37.getString();
		
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
			  fm.WorkFlowFlag.value = "0000001099";
		}
		else
		{
			  fm.WorkFlowFlag.value = "0000001098";
		}
		fm.MissionID.value = MissionID;
		fm.SubMissionID.value = SubMissionID;			//¼�����
	}
	else if (wFlag ==2)//�������ȷ��
	{
		if(document.all('ProposalContNo').value == "")
		{
		alert("δ��ѯ����ͬ��Ϣ,������������ [�������] ȷ�ϣ�");
		return;
		}
		if(fm.AppntChkButton.disabled==false)
		{ 
//			var strSql = "select * from LCIssuePol where contno = '"+fm.ContNo.value+"' and issuetype = '99' and questionobj = '"+document.all('AppntNo').value+"'";
			
			var sqlid38="DirectContInputSql38";
			var mySql38=new SqlClass();
			mySql38.setResourceName("app.DirectContInputSql"); //ָ��ʹ�õ�properties�ļ���
			mySql38.setSqlId(sqlid38);//ָ��ʹ�õ�Sql��id
			mySql38.addSubPara(fm.ContNo.value);//ָ������Ĳ���
			mySql38.addSubPara(document.all('AppntNo').value);//ָ������Ĳ���
		 	var strSql=mySql38.getString();
			
			var brrResult = easyExecSql(strSql);
			if(brrResult==null)	
			{
				if(confirm("�Ƿ����Ͷ����У�飿")) {return;}
			}
		}	
		if(fm.InsuredChkButton.disabled==false)
		{
//			strSql = "select * from LCIssuePol where contno = '"+fm.ContNo.value+"' and issuetype = '99' and questionobj in (select insuredno from lcinsured where contno='"+fm.ContNo.value+"')";
			
			var sqlid39="DirectContInputSql39";
			var mySql39=new SqlClass();
			mySql39.setResourceName("app.DirectContInputSql"); //ָ��ʹ�õ�properties�ļ���
			mySql39.setSqlId(sqlid39);//ָ��ʹ�õ�Sql��id
			mySql39.addSubPara(fm.ContNo.value);//ָ������Ĳ���
			mySql39.addSubPara(fm.ContNo.value);//ָ������Ĳ���
		 	var strSql=mySql39.getString();
			
			var crrResult = easyExecSql(strSql);
			if(crrResult==null)
			{
			if(confirm("�Ƿ���б�����У�飿")) {return;}
			}
		}
		fm.WorkFlowFlag.value = "0000001001";					//�������
		fm.MissionID.value = MissionID;
		fm.SubMissionID.value = SubMissionID;
		approvefalg="2";
	}
	  else if (wFlag ==3)
	{
		if(document.all('ProposalContNo').value == "")
		{
			alert("δ��ѯ����ͬ��Ϣ,������������ [�����޸����] ȷ�ϣ�");
			return;
		}
		fm.WorkFlowFlag.value = "0000001002";					//�����޸����
		fm.MissionID.value = MissionID;
		fm.SubMissionID.value = SubMissionID;
	}
	else if(wFlag == 4)
	{
		 if(document.all('ProposalContNo').value == "")
	   {
	   	  alert("δ��ѯ����ͬ��Ϣ,������������ [�޸����] ȷ�ϣ�");
	   	  return;
	   }
		fm.WorkFlowFlag.value = "0000001021";					//�����޸�
		fm.MissionID.value = MissionID;
		fm.SubMissionID.value = SubMissionID;
	}
	else
		return;

	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.action = "./InputConfirm.jsp";
	fm.submit(); //�ύ
}
function checkOtherQuest(){
   if(ActivityID=='0000001002')
   {
//   	var tSql ="select missionprop9 from lwmission where activityid = '0000001002' and missionprop1 ='"+prtNo+"' and defaultoperator='"+operator+"' and  processid = '0000000003'";
//	   	var tSql ="select missionprop9 from lwmission where activityid  in (select activityid from lwactivity  where functionid ='10010004') and missionprop1 ='"+prtNo+"' and defaultoperator='"+operator+"' ";
	   
		var sqlid40="DirectContInputSql40";
		var mySql40=new SqlClass();
		mySql40.setResourceName("app.DirectContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql40.setSqlId(sqlid40);//ָ��ʹ�õ�Sql��id
		mySql40.addSubPara(prtNo);//ָ������Ĳ���
		mySql40.addSubPara(operator);//ָ������Ĳ���
	 	var tSql=mySql40.getString();
	   	
	   	var tOtherQuestFlag = easyExecSql(tSql);
   	if(tOtherQuestFlag !=""&& tOtherQuestFlag!=null)
  	 {
   	   if(tOtherQuestFlag =="1")
   	   {
  	       //��δ�ظ��������
  	       alert("����δ�ظ�����������뽫���еĵ�������ظ����ٽ���[������޸����]������");
 	        return false;
  	    }
 	  }else{
  	      alert("û���ܹ���ѯ���Ƿ���δ�ظ������������ȷ�Ϻ��ٽ���[������޸����]����");
 	      if(confirm("�Ƿ�ȷ�ϴ˲�����")==false)
  	      {
  	        return false;
 	      }
	    }
   }
   return true;
}

//���ύ�󷵻ء��������������غ�ˢ��ҳ��
function afterSubmit1(FlagStr , Content )
{
	showInfo.close();
	if( FlagStr == "Fail" )
	{
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + Content ;
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
		Content = "����ɹ���";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + Content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		initQuery();//ˢ��ҳ��
	}
}

//���ύ�󷵻ء��������������غ�ˢ��ҳ��
function afterSubmit(FlagStr , Content,Contract )
{
	showInfo.close();
	if( FlagStr == "Fail" )
	{
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + Content ;
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
		Content = "����ɹ���";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + Content ;
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

/*********************************************************************
 *  �Ѽ�����Ϣ�ӱ�����ɾ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
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
 *  �Ѻ�ͬ��Ϣ����ӵ�������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
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
 *  ɾ�������б���������Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
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
 *  ������������Ϣ���뵽������
 *  ����  ��  ��
 *  ����ֵ��  ��
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
//��ȡͶ��������
function getAppntAge()
{
	fm.AppntAge.value=calPolAppntAge(fm.AppntBirthday.value,fm.PolApplyDate.value);
}
//��ȡ����������
function getInsuredAppAge()
{
	fm.InsuredAppAge.value=calPolAppntAge(fm.InsuredBirthday.value,fm.PolApplyDate.value);
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
//		var strSQL="select placecode,placename from ldaddress where placecode='"+PlaceCode+"' and placetype='"+PlaceType+"'";
		
		var sqlid41="DirectContInputSql41";
		var mySql41=new SqlClass();
		mySql41.setResourceName("app.DirectContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql41.setSqlId(sqlid41);//ָ��ʹ�õ�Sql��id
		mySql41.addSubPara(PlaceCode);//ָ������Ĳ���
		mySql41.addSubPara(PlaceType);//ָ������Ĳ���
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
��¼������ʱ��ϵͳ���ɵı���������¼��ı��ѽ���У�飬
�������¼��ı�����ϵͳ���ɵı��Ѳ�����ϵͳҪ������ʾ�����Կɱ���,�����Կ���ת
�����������ͬ��<Ͷ������>;     ����:true or false
*************************************************************************************/
function checkpayfee(ContNo)
{
	var tContNo=ContNo;
	var tTempFee="";//����¼��ı���
	var tPremFee="";//ϵͳ���ɵı���
	//��ѯ����¼��ı���
//	var tempfeeSQL="select sum(nvl(paymoney,0)) from ljtempfee where tempfeetype='1' and confdate is null "
//		+" and otherno=(select contno from lccont where prtno= '"+tContNo+"')";
	
	var sqlid42="DirectContInputSql42";
	var mySql42=new SqlClass();
	mySql42.setResourceName("app.DirectContInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql42.setSqlId(sqlid42);//ָ��ʹ�õ�Sql��id
	mySql42.addSubPara(tContNo);//ָ������Ĳ���
 	var tempfeeSQL=mySql42.getString();
	
	var TempFeeArr=easyExecSql(tempfeeSQL);	
	if(TempFeeArr!=null)
	{
		tTempFee=TempFeeArr[0][0];
	}
	//��ѯϵͳ���ɵı���
//	var premfeeSQL="select sum(nvl(prem,0)) from lcpol where 1=1 "
//		+" and contno=(select contno from lccont where prtno= '"+tContNo+"')";	
	
	var sqlid43="DirectContInputSql43";
	var mySql43=new SqlClass();
	mySql43.setResourceName("app.DirectContInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql43.setSqlId(sqlid43);//ָ��ʹ�õ�Sql��id
	mySql43.addSubPara(tContNo);//ָ������Ĳ���
 	var premfeeSQL=mySql43.getString();
	
	var PremFeeArr=easyExecSql(premfeeSQL);	
	tPremFee=PremFeeArr[0][0];	
	if(PremFeeArr!=null)
	{
		tPremFee=PremFeeArr[0][0];
		if(tPremFee==null || tPremFee=="" || tPremFee=="null")
		{
		alert("��ѯ��Ͷ������������Ϣ�����ɱ�������ʧ�ܣ�����");
		return false;	
		}
	}
	//�Ƚϡ���ѯ����¼��ı��ѡ� �� ����ѯϵͳ���ɵı��ѡ��Ƿ���ȣ��粻����򵯳���Ϣ��ʾ
	if(tTempFee!="" && tPremFee!="" && tTempFee!="null" && tPremFee!="null" && (tTempFee!=tPremFee))
	{
		var ErrInfo="ע�⣺����¼��ı���["+tTempFee+"]��ϵͳ���ɵı���["+tPremFee+"]���ȡ�\n";
		ErrInfo=ErrInfo+"ȷ����Ҫ������ȷ�豣���밴��ȷ���������򰴡�ȡ������";
		if(confirm(ErrInfo)==false)
		{
		   return false;	
		}
	}
	return true;
}

/******************************************************************************
* ���У�飬����ϵͳ��¼���Ͷ������У�飬У�����Ϊ��¼���Ͷ�����ڿ�����¼����ϵͳ��������60����
* ���������ContNo---��ͬ�ţ�ӡˢ�ţ���  PolAppntDate---Ͷ������
*******************************************************************************/
function checkPolDate(ContNo,PolAppntDate)
{
  	var tContNo=ContNo;//��ͬ��
	var tPolAppntDate=PolAppntDate;//Ͷ������
	if(tContNo=="" || tPolAppntDate=="") {return false;}
	//Ͷ������ֻ��Ϊ��ǰ������ǰ
    if (calAge(tPolAppntDate) < 0)
    {
        alert("Ͷ������ֻ��Ϊ��ǰ������ǰ!");
        return false;
    }
	var DayIntv=60;//¼��������Ͷ�����ڵı�׼���������Ĭ��Ϊ60��
    //��ѯ¼��������Ͷ�����ڵı�׼�������
//    var DayIntvArr = easyExecSql("select sysvarvalue from ldsysvar where sysvar='input_poldate_intv'");
    
	var sqlid44="DirectContInputSql44";
	var mySql44=new SqlClass();
	mySql44.setResourceName("app.DirectContInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql44.setSqlId(sqlid44);//ָ��ʹ�õ�Sql��id
 	var strSQL44=mySql44.getString();
 	var DayIntvArr = easyExecSql(strSQL44);
    
    if (DayIntvArr != null) { DayIntv = DayIntvArr[0][0]; }
    //���ݺ�ͬ�Ż�ȡ¼������<��ѯ��ͬ��������ȡ֮������ȡϵͳ��ǰ����>
    var tMakeDate = "";//¼����ϵͳ��������
    if(tContNo!=null && tContNo!="")
    {
//		var makedatesql = "select contno,prtno,makedate from lccont where prtno='" + tContNo + "'";
		
		var sqlid45="DirectContInputSql45";
		var mySql45=new SqlClass();
		mySql45.setResourceName("app.DirectContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql45.setSqlId(sqlid45);//ָ��ʹ�õ�Sql��id
		mySql45.addSubPara(tContNo);//ָ������Ĳ���
	 	var makedatesql=mySql45.getString();
		
		var makedatearr = easyExecSql(makedatesql);
		if (makedatearr != null){ tMakeDate = makedatearr[0][2];}
    }
    if(tMakeDate=="")  //¼����ϵͳ��������Ϊ�գ��� Ĭ��ϵͳ����
    {    
//    	var sysdatearr = easyExecSql("select to_date(sysdate) from dual");
    	
		var sqlid46="DirectContInputSql46";
		var mySql46=new SqlClass();
		mySql46.setResourceName("app.DirectContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql46.setSqlId(sqlid46);//ָ��ʹ�õ�Sql��id
	 	var strsql46=mySql46.getString();
	 	var sysdatearr = easyExecSql(strsql46);
	 	
    	tMakeDate = sysdatearr[0][0];//¼����ϵͳ�������ڣ�Ĭ��ϵͳ���ڡ�
    }
    var Days = dateDiff(tPolAppntDate, tMakeDate, "D");//¼��������Ͷ�����ڵļ��
    if (Days > DayIntv || Days < 0)
    {
        var strInfo = "Ͷ������Ӧ��¼����ϵͳ�������� "+DayIntv+" ���ڡ�";
        strInfo = strInfo +"\n��¼������["+tMakeDate+"] �� Ͷ������["+tPolAppntDate+"]=="+Days+" �졣";
		strInfo = strInfo +"\n��������дͶ�����ڡ�";
        alert(strInfo);
        return false;
    }
    return true;
}
/*****************************************************************************************
        ����Ϊ��ҳ��Ĺ���������������
******************************************************************************************/
//¼��У�鷽��
//���������verifyOrderУ���˳��
//ҵ�������ýӿڣ����ͨ��У�鷵��true�����򷵻�false
function verifyInputNB(verifyOrder)  
{
	var formsNum = 0;	//�����е�FORM��
	var elementsNum = 0;	//FORM�е�Ԫ����
	var passFlag = true;	//У��ͨ����־
	//��������FORM
	for (formsNum=0; formsNum<window.document.forms.length; formsNum++)
	{
		//����FORM�е�����ELEMENT
		for (elementsNum=0; elementsNum<window.document.forms[formsNum].elements.length; elementsNum++)
		{
			//Ԫ��У������verify��ΪNULL
			if (window.document.forms[formsNum].elements[elementsNum].verify != null && window.document.forms[formsNum].elements[elementsNum].verify != "" && window.document.forms[formsNum].elements[elementsNum].verifyorder == verifyOrder)
			{
				//����У��verifyElement
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
У�����ڸ�ʽ������ɹ����򷵻ظ�ʽ����������ַ����������ʽ��ʧ�ܣ��򷵻� null��
############���룺�����ַ���     ##############�������ʽ����������ַ���
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


//����ҳ�������Ĵ���������֣�����ʾ������
function getInputFieldName()
{
	//��ͬ��Ϣ����
	showOneCodeName('sellType','SellType','SellTypeName');
	showOneCodeName('comcode','ManageCom','ManageComName');
	showOneCodeName('highAmntFlag','highAmntFlag','highAmntFlagName');
	showOneCodeName('comcode','AgentManageCom','AgentManageComName');
	showOneCodeName('comcode','TelephManageCom','TelephManageComName');
	//showOneCodeName('directstyle','DirectStyle','DirectStyleName');
	showOneCodeName('commisionratio','CSplit','CSplitName');
	//Ͷ������Ϣ����
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
	//�ɷ���Ϣ����
	showOneCodeName('paymode','NewPayMode','NewPayModeName');
	showOneCodeName('bank','NewBankCode','NewBankCodeName');
	showOneCodeName('continuepaymode','SecPayMode','SecPayModeName');
	showOneCodeName('bank','SecBankCode','SecBankCodeName');
	//��������Ϣ����
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

//------------------------Beg
//Add By Chenrong
//Date:2006.5.15

//��ѯ����Ա�涯�ٶ�
function initQueryRollSpeed()
{
//    var strSQL = "select SYSVAR,SYSVARVALUE from LDSysvar where SYSVAR like 'ROLLSPEED%25' order by SYSVAR ASC";
	
	var sqlid47="DirectContInputSql47";
	var mySql47=new SqlClass();
	mySql47.setResourceName("app.DirectContInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql47.setSqlId(sqlid47);//ָ��ʹ�õ�Sql��id
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
	mySql48.setResourceName("app.DirectContInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql48.setSqlId(sqlid48);//ָ��ʹ�õ�Sql��id
	mySql48.addSubPara(fm.Operator.value);//ָ������Ĳ���
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

//���Զ���ý���Ŀؼ�����
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





