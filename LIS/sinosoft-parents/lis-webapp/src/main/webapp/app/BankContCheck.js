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
var cacheWin=null;
var mShowCustomerDetail = "GROUPPOL";
this.window.onfocus=myonfocus;
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";

//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo1;

//�����Ӷ���ִ�еĴ���
var addAction = 0;
//�ݽ����ܽ��
var sumTempFee = 0.0;
//�ݽ�����Ϣ�н��ѽ���ۼ�
var tempFee = 0.0;
//�ݽ��ѷ�����Ϣ�н��ѽ���ۼ�
var tempClassFee = 0.0;
//����ȷ���󣬸ñ�����Ϊ�棬�������һ��ʱ�������ֵ�Ƿ�Ϊ�棬Ϊ�������Ȼ���ٽ��ñ����ü�
var confirmFlag = false;
//
var arrCardRisk;

//������flag
var mWFlag = 0;

//��ʼ��ʱ����ѯ��ͬ��Ϣ��Ͷ������Ϣ(����������Ϣ)����������Ϣ(������֪��Ϣ)��������Ϣ������ʾ�ڽ���
function initQuery()
{//alert(36);
    initQueryCont();//����ʼ��ʱ��ѯ��ͬ��Ϣ��-----������������Ϣ
	initQueryAgentImpartGrid();//��ѯ ҵ��Ա��֪��Ϣ
	initQueryAppnt();//����ѯͶ������Ϣ������������Ͷ���˵ĵ�ַ��Ϣ
	initQueryAccount();//���ɷ���Ϣ��ѯ��------��ѯ�����Ľɷ���Ϣ�������ɷѷ�ʽ���ʺ���Ϣ�ȡ�
    initQueryAppntImpartInfo();	//��ѯͶ���˸�֪��Ϣ
	initQueryInsuredInfo();//����ѯ��������Ϣ��----�����б�
    //alert("��û��");
	getInsuredInfo();//�����˻�����Ϣ����ַ��Ϣ
	getInsuredImpartInfo();//�����˸�֪��Ϣ
	getInsuredPolInfo();//������������Ϣ
	showCodeName();	//����ҳ�������Ĵ���������֣�����ʾ������
	//�ж�ϵͳ���Ƿ�����������Ա𡢳���������ͬ��֤������֤��������ͬ��Ͷ������Ϣ��
//	alert('1');
    AppntChkNew();
	// �ڳ�ʼ��bodyʱ�Զ�Ч�鱻������Ϣ
//	alert('2');
    InsuredChkNew();
	//�жϼ��±��еļ�¼����
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

//��ʼ��ʱ��ѯ��ͬ��Ϣ
function initQueryCont()
{
	var sSQL="";
	if(document.all('ContNo').value=="")
	{
		
		var sqlid1="BankContCheckSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
	    var sSQL=mySql1.getString();	
		
//		var sSQL="select contno,proposalcontno,prtno,polapplydate,selltype,salechnl,managecom,agentcode,cvalidate"
//			+" ,agentcom,agentbankcode,bankagent,remark,signname"
//			+" from lccont where prtno='"+fm.PrtNo.value+"' ";
	}
	else
	{
		
		var sqlid2="BankContCheckSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(fm.ContNo.value);//ָ������Ĳ���
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
	//ȥ������¼�����������
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
	queryAgent();//��ѯ ��������Ϣ
}
//��ѯ��������Ϣ
function queryAgent()
{
	if(document.all('AgentCode').value != "")
	{
		
		var cAgentCode = fm.AgentCode.value;  //��������
		//���ҵ��Ա���볤��Ϊ8���Զ���ѯ����Ӧ�Ĵ������ֵ���Ϣ
		//tongmeng 2007-09-10 modify
		//MSʹ�ô�����Ա���볤��Ϊ10
		//if(cAgentCode.length!=8){return;}
		if(cAgentCode.length!=10){return;}
		
	    var sqlid3="BankContCheckSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
		mySql3.addSubPara(cAgentCode);//ָ������Ĳ���
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
		mySql4.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
		mySql4.addSubPara(document.all('AgentCom').value);//ָ������Ĳ���
	    var strSQL=mySql4.getString();	
		
//		var strSQL = "select bankcode,name from lacom where agentcom='"+ document.all('AgentCom').value+"'";
		arrResult = easyExecSql(strSQL,1,0);
		if(arrResult!==null)
		{ 
			//tongmeng 2007-09-10 ���ػ�ȥ�����б���
			document.all('InputAgentComName').value=arrResult[0][1]; 
			//document.all('BankCode1').value=arrResult[0][0]; 
			//document.all('BankCodeName').value=arrResult[0][1]; 
		}
	}
}
//��ѯ ҵ��Ա��֪��Ϣ
function initQueryAgentImpartGrid()
{
	var turnPageAgent = new turnPageClass();
	var tContNo=fm.ContNo.value;
	var tAgentCode=fm.AgentCode.value;
	
		var sqlid5="BankContCheckSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
		mySql5.addSubPara(tAgentCode);//ָ������Ĳ���
		mySql5.addSubPara(tContNo);//ָ������Ĳ���
	    var sSQL=mySql5.getString();	
	
//	var sSQL="select impartver,impartcode,impartcontent,impartparammodle from lccustomerimpart"
//		+" where customernotype='2' and customerno='"+tAgentCode+"' and contno='"+tContNo+"'";
   	turnPageAgent.queryModal(sSQL, AgentImpartGrid);
}

//��ѯͶ������Ϣ����������Ͷ���˵ĵ�ַ��Ϣ
function initQueryAppnt()
{
	//����"��ͬ��"��ѯ��Ͷ������Ϣ����������Ӧ������
	
		var sqlid6="BankContCheckSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
		mySql6.addSubPara(document.all('ContNo').value);//ָ������Ĳ���
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
		getAge(); //����Ͷ��������
		getAppntAddressInfo();	//��ѯͶ���˵�ַ��Ϣ	
	}
}

//���ɷ���Ϣ��ѯ��------��ѯ�����Ľɷ���Ϣ�������ɷѷ�ʽ���ʺ���Ϣ�ȡ�
function initQueryAccount()
{
	
		var sqlid7="BankContCheckSql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
		mySql7.addSubPara(document.all('ContNo').value);//ָ������Ĳ���
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

//��ѯͶ���˸�֪��Ϣ
function initQueryAppntImpartInfo()
{
	//��ѯ Ͷ�������롢������Դ����ߡ����صȸ�֪��Ϣ����������Ӧ���������
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
	//��ѯͶ���˸�֪������������
	
		var sqlid8="BankContCheckSql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
		mySql8.addSubPara(tAppntNo);//ָ������Ĳ���
		mySql8.addSubPara(tContNo);//ָ������Ĳ���
	    var strSQL=mySql8.getString();	
	
//	var strSQL="select impartver,impartcode,impartcontent,impartparammodle "
//		+" from lccustomerimpart where customernotype='0'"
//		+" and ((impartver ='01' and impartcode<>'001') or (impartver ='02' and impartcode<>'000'))"
//		+" and customerno='"+tAppntNo+"' and contno='"+tContNo+"'";
	turnPage.queryModal(strSQL,ImpartGrid);	

	//��ѯ����������200���������ڣ�
	
		var sqlid9="BankContCheckSql9";
		var mySql9=new SqlClass();
		mySql9.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql9.setSqlId(sqlid9);//ָ��ʹ�õ�Sql��id
		mySql9.addSubPara(tContNo);//ָ������Ĳ���
	    var remarkSQL=mySql9.getString();	
	
	//var remarkSQL="select remark from lccont where contno='"+tContNo+"'";
	var remarkArr= easyExecSql(remarkSQL,1,0);
	//alert('111'+remarkArr[0][0]);
	try{document.all('Remark').value= remarkArr[0][0];}catch(ex){};
}

//����ѯ��������Ϣ��������
function initQueryInsuredInfo()
{
	
		var sqlid10="BankContCheckSql10";
		var mySql10=new SqlClass();
		mySql10.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql10.setSqlId(sqlid10);//ָ��ʹ�õ�Sql��id
		mySql10.addSubPara(fm.ContNo.value);//ָ������Ĳ���
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
 *  Ͷ���˵�ַ��Ϣ
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
		mySql11.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql11.setSqlId(sqlid11);//ָ��ʹ�õ�Sql��id
		mySql11.addSubPara(tAppntNo);//ָ������Ĳ���
		mySql11.addSubPara(tAppntAddressNo);//ָ������Ĳ���
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
 *  InsuredGrid��RadioBox����¼�����ñ�������ϸ��Ϣ�����뱻������Ϣ��
 **********************************************************************/
function getInsuredDetail(parm1,parm2)
{
    emptyInsured();//���������¼���������¸�ֵ
    fm.InsuredNo.value=document.all(parm1).all('InsuredGrid1').value;
    //��ѯѡ�еı�������Ϣ
	getInsuredInfo();//�����˻�����Ϣ����ַ��Ϣ
	getInsuredImpartInfo();//�����˸�֪��Ϣ
	getInsuredPolInfo();//������������Ϣ
}

//�����˻�����Ϣ����ַ��Ϣ
function getInsuredInfo()
{
	var tContNo=fm.ContNo.value;
	var tInsuredNo=document.all('InsuredNo').value;
	if(tContNo=="" || tInsuredNo=="")
	{
		return false;
	}
	//����"��ͬ��"�� "�����˺���"��ѯ����������Ϣ����������Ӧ������
	
		var sqlid12="BankContCheckSql12";
		var mySql12=new SqlClass();
		mySql12.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql12.setSqlId(sqlid12);//ָ��ʹ�õ�Sql��id
		mySql12.addSubPara(tContNo);//ָ������Ĳ���
		mySql12.addSubPara(tInsuredNo);//ָ������Ĳ���
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
		document.all('IDPeriodOfValidity').value=arrInsured[0][18];
	    getAge2(); //ȡ�ñ���������
		getInsuredAddressInfo();//ȡ�����˵�ַ��Ϣ
	}
}


/*********************************************************************
 *  �����˵�ַ��Ϣ
 **********************************************************************/
function getInsuredAddressInfo()
{
	var tInsuredNo=document.all('InsuredNo').value;
	var tInsuredAddressNo=document.all('InsuredAddressNo').value;
	if(tInsuredNo=="" && tInsuredAddressNo=="")
	{
		retrun;
	}
	//��ѯ�����˵ĵ�ַ��Ϣ
	
		var sqlid13="BankContCheckSql13";
		var mySql13=new SqlClass();
		mySql13.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql13.setSqlId(sqlid13);//ָ��ʹ�õ�Sql��id
		mySql13.addSubPara(tInsuredNo);//ָ������Ĳ���
		mySql13.addSubPara(tInsuredAddressNo);//ָ������Ĳ���
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

//��ѯ�����˸�֪��Ϣ��Ϣ
function getInsuredImpartInfo()
{
	//��ѯ ���������롢������Դ����ߡ����صȸ�֪��Ϣ����������Ӧ���������
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
	//��ѯ�����˸�֪������������
	
		var sqlid14="BankContCheckSql14";
		var mySql14=new SqlClass();
		mySql14.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql14.setSqlId(sqlid14);//ָ��ʹ�õ�Sql��id
		mySql14.addSubPara(tInsuredNo);//ָ������Ĳ���
		mySql14.addSubPara(tContNo);//ָ������Ĳ���
	    var sInsuredAddSQL=mySql14.getString();	
	
//	var strSQL="select impartver,impartcode,impartcontent,impartparammodle "
//		+" from lccustomerimpart where customernotype='1'"
//		+" and customerno='"+tInsuredNo+"' and contno='"+tContNo+"'";
	turnPage.queryModal(sInsuredAddSQL,ImpartInsuredGrid);	
	
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
	
			var sqlid15="BankContCheckSql15";
		var mySql15=new SqlClass();
		mySql15.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql15.setSqlId(sqlid15);//ָ��ʹ�õ�Sql��id
		mySql15.addSubPara(tInsuredNo);//ָ������Ĳ���
		mySql15.addSubPara(tContNo);//ָ������Ĳ���
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
    //��ѯ��������Ϣ
    getLCBnfInfo();
}

//�����������Ϣ
function getLCBnfInfo()
{
	initLCBnfGrid();
	var tPolNo=document.all('SelPolNo').value;
	var tContNo=document.all('ContNo').value;
	
		var sqlid16="BankContCheckSql16";
		var mySql16=new SqlClass();
		mySql16.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql16.setSqlId(sqlid16);//ָ��ʹ�õ�Sql��id
		mySql16.addSubPara(tContNo);//ָ������Ĳ���
		mySql16.addSubPara(tPolNo);//ָ������Ĳ���
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
		mySql17.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql17.setSqlId(sqlid17);//ָ��ʹ�õ�Sql��id
		mySql17.addSubPara(fm.AppntAddressNo.value);//ָ������Ĳ���
		mySql17.addSubPara(fm.AppntNo.value);//ָ������Ĳ���
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
		mySql18.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql18.setSqlId(sqlid18);//ָ��ʹ�õ�Sql��id
		mySql18.addSubPara(fm.InsuredAddressNo.value);//ָ������Ĳ���
		mySql18.addSubPara(fm.InsuredNo.value);//ָ������Ĳ���
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
		mySql19.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql19.setSqlId(sqlid19);//ָ��ʹ�õ�Sql��id
		mySql19.addSubPara(tAgentCode);//ָ������Ĳ���
	    var sqlstr=mySql19.getString();	
		
//		var sqlstr="select name,agentgroup,managecom from labranchgroup where agentgroup=(select agentgroup from laagent where agentcode='" + tAgentCode+"')" ;
		arrResult = easyExecSql(sqlstr,1,0);
		if(arrResult==null)
		{
			alert("ר��Ա��Ϣ��ѯ����");
			return false;
		}
		else
		{
			try{document.all('ManageCom').value= arrResult[0][2];}catch(ex){};//�������
			try{document.all('BranchAttr').value= arrResult[0][0];}catch(ex){};//Ӫҵ����Ӫҵ�� 
			try{document.all('AgentGroup').value= arrResult[0][1];}catch(ex){};//
			showOneCodeName('comcode','ManageCom','ManageComName');
		}

	} 

	afterCodeSelect2( cCodeName, Field );
}

function haveMultiAgent(){
	//alert("aa����"+document.all("multiagentflag").checked);
    if(document.all("multiagentflag").checked)
    {
        DivMultiAgent.style.display="";
    }
    else
    {
        DivMultiAgent.style.display="none";
    }

}

//Muline ����Ӷ����� parm1
function queryAgentGrid(Field)
{
	//alert("Field=="+Field);
	tField=Field;
	if(document.all('ManageCom').value=="")
	{
		 alert("����¼����������Ϣ��");
		 return;
	}
	if(document.all(Field).all('MultiAgentGrid1').value=="")
	{
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom=&queryflag=1","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0;'+sFeatures);
	}

	if(document.all( Field ).all('MultiAgentGrid1').value != "")	 
	{
    	var cAgentCode = document.all( Field ).all('MultiAgentGrid1').value;  //��������
    	
		var sqlid20="BankContCheckSql20";
		var mySql20=new SqlClass();
		mySql20.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql20.setSqlId(sqlid20);//ָ��ʹ�õ�Sql��id
		mySql20.addSubPara(cAgentCode);//ָ������Ĳ���
	    var strSql=mySql20.getString();	
		
//    	var strSql = "select AgentCode from LAAgent where AgentCode='" + cAgentCode +"'";
        var arrResult = easyExecSql(strSql);
           //alert(arrResult);
        if (arrResult == null) {
            alert("����Ϊ:["+document.all( Field ).all('MultiAgentGrid1').value+"]�Ĵ����˲����ڣ���ȷ��!");
        }
    }
}

//�������������У��
function confirmSecondInput1(aObject,aEvent){
	if(aEvent=="onkeyup"){
	  var theKey = window.event.keyCode;
	  if(theKey=="13"){
	    if(theFirstValue!=""){
      	theSecondValue = aObject.value;
	      if(theSecondValue==""){
	      	alert("���ٴ�¼�룡");
	        aObject.value="";
	        aObject.focus();
	        return;
	      }
	      if(theSecondValue==theFirstValue){
	        aObject.value = theSecondValue;
	        return;
	      }
	      else{
	        alert("����¼����������������¼�룡");
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
      	  alert("��¼�����ݣ�");
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
	    	alert("���ٴ�¼�룡");
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
	      alert("����¼����������������¼�룡");
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

//��ѯ�Ƿ��Ѿ���ӹ�Ͷ����
function checkAppnt(){
    var ContNo=document.all("ContNo").value;
	
		var sqlid21="BankContCheckSql21";
		var mySql21=new SqlClass();
		mySql21.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql21.setSqlId(sqlid21);//ָ��ʹ�õ�Sql��id
		mySql21.addSubPara(ContNo);//ָ������Ĳ���
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

//У��Ͷ������
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
			 alert("�������Ͷ����������!");
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
    		alert("�������Ͷ����������!");
    		fm.PolAppntDate.value = "";
    		return;
		}
	}
		//����ϵͳ��¼���Ͷ������У��
	if(checkPolDate(fm.ProposalContNo.value,fm.PolAppntDate.value)==false)
	{
    	fm.PolAppntDate.value="";
    	return;
	}

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
	if(calAge(tPolAppntDate)<0)
	{
		alert("Ͷ������ֻ��Ϊ��ǰ������ǰ!");
		return false;
	}
	var tMakeDate="";//���ݺ�ͬ�Ż�ȡ¼������<��ѯ��ͬ��������ȡ֮������ȡϵͳ��ǰ����>
	
			var sqlid22="BankContCheckSql22";
		var mySql22=new SqlClass();
		mySql22.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql22.setSqlId(sqlid22);//ָ��ʹ�õ�Sql��id
		mySql22.addSubPara(tContNo);//ָ������Ĳ���
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
		mySql23.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql23.setSqlId(sqlid23);//ָ��ʹ�õ�Sql��id
//		mySql23.addSubPara(tContNo);//ָ������Ĳ���
	    var sysdatearr=mySql23.getString();	
		
//		var sysdatearr=easyExecSql("select to_date(sysdate) from dual");//ȡϵͳ��ǰ����
		tMakeDate=sysdatearr[0][0];
	}
	var Days=dateDiff(tPolAppntDate,tMakeDate,"D");
	if(Days>60 || Days<0)
	{
		var strInfo="Ͷ������Ӧ��¼����ϵͳ��������60���ڡ���¼�����ڣ�"+tMakeDate+"������ Ͷ�����ڣ�"+tPolAppntDate+"��== "+Days+" ��";
		strInfo=strInfo+",��������дͶ�����ڣ�����";
		alert(strInfo);
		return false;
	}
}

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

//��ѯ���±�
function checkNotePad(prtNo,LoadFlag)
{
	var strSQL="select count(*) from LWNotePad where otherno='"+prtNo+"'";
	var arrResult = easyExecSql(strSQL);
	eval("document.all('NotePadButton"+LoadFlag+"').value=' ���±��鿴(��"+arrResult[0][0]+"��)'");
}
//�ڳ�ʼ��bodyʱ�Զ�Ч��Ͷ������Ϣ��
//�ж�ϵͳ���Ƿ�����������Ա𡢳���������ͬ��֤������֤��������ͬ�ı�������Ϣ��
function AppntChkNew()
{
	var Sex=fm.AppntSex.value;
	
		var sqlid24="BankContCheckSql24";
		var mySql24=new SqlClass();
		mySql24.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql24.setSqlId(sqlid24);//ָ��ʹ�õ�Sql��id
		mySql24.addSubPara(fm.AppntName.value);//ָ������Ĳ���
		mySql24.addSubPara(Sex);//ָ������Ĳ���
		mySql24.addSubPara(fm.AppntBirthday.value);//ָ������Ĳ���
		
		mySql24.addSubPara(fm.AppntNo.value);//ָ������Ĳ���
		mySql24.addSubPara(fm.AppntIDNo.value);//ָ������Ĳ���
		mySql24.addSubPara(fm.AppntNo.value);//ָ������Ĳ���
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
		//disabled"Ͷ����Ч��"��ť   //�ж��Ƿ�����ظ������ˣ�
		fm.AppntChkButton.disabled = true;
		//fm.AppntChkButton2.disabled = true;
	}
	else
	{
		fm.AppntChkButton.disabled = "";
		//fm.AppntChkButton2.disabled = "";
	}
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
	
			var sqlid25="BankContCheckSql25";
		var mySql25=new SqlClass();
		mySql25.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql25.setSqlId(sqlid25);//ָ��ʹ�õ�Sql��id
		mySql25.addSubPara(tInsuredName);//ָ������Ĳ���
		mySql25.addSubPara(tInsuredSex);//ָ������Ĳ���
		mySql25.addSubPara(tBirthday);//ָ������Ĳ���
		
		mySql25.addSubPara(tInsuredNo);//ָ������Ĳ���
		mySql25.addSubPara(fm.IDNo.value);//ָ������Ĳ���
		mySql25.addSubPara(tInsuredNo);//ָ������Ĳ���
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
 *  ���Ͷ��������
 *  ����  ��  ��
 *  ����ֵ��  ��
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
		var Year = fm.AppntBirthday.value.substring(0,4);
		var Month = fm.AppntBirthday.value.substring(5,7);
		var Day =  fm.AppntBirthday.value.substring(8,10);
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
		var Year =fm.Birthday.value.substring(0,4);
		var Month = fm.Birthday.value.substring(5,7);
		var Day = fm.Birthday.value.substring(8,10);
		if(Year=="0000"||Month=="00"||Day=="00")
		{
    		alert("������ı����˳�����������!");
    		fm.Birthday.value = "";
    		return;
		}
	}
}
//Ͷ����֤�����뼰����
function checkidtype()
{
	if(fm.AppntIDType.value=="" && fm.AppntIDNo.value!="")
	{
		alert("����ѡ��֤�����ͣ�");
		fm.AppntIDNo.value="";
	}
}
//������֤�����뼰����
function checkidtype2()
{
	if(fm.IDType.value=="" && fm.IDNo.value!="")
	{
		alert("����ѡ��֤�����ͣ�");
		fm.IDNo.value="";
    }
}

/****************Ͷ����  �������֤��ȡ�ó������ں��Ա�**************************/
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
			document.all('AppntBirthday').value=tBirthday;
			document.all('AppntSex').value=tSex;
		}
	}
}

/****************������  �������֤��ȡ�ó������ں��Ա�**************************/
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
		mySql26.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql26.setSqlId(sqlid26);//ָ��ʹ�õ�Sql��id
		mySql26.addSubPara(fm.AppntNo.value);//ָ������Ĳ���
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
		mySql27.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql27.setSqlId(sqlid27);//ָ��ʹ�õ�Sql��id
		mySql27.addSubPara(fm.InsuredNo.value);//ָ������Ĳ���
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

//�����¼��
function QuestInput()
{

    var cContNo = document.all('ContNo').value;  //��������
    if(cContNo=="" || cContNo=="null")
    {
        alert("���޺�ͬͶ�����ţ����ȱ���!");
    }
    else
    {
        window.open("../uw/MSQuestInputMain.jsp?ContNo="+cContNo+"&Flag="+ LoadFlag+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+ActivityID,"window1",sFeatures);
    }
}
//�������ѯ
function QuestQuery()
{
	cContNo = document.all('ContNo').value;  //��������
	if(cContNo == "" || cContNo=="null")
	{
	    alert("���޺�ͬͶ�����ţ����ȱ���!");
	}
	else
	{
	    window.open("../uw/QuestQueryMain.jsp?ContNo="+cContNo+"&Flag="+LoadFlag,"window1",sFeatures);
	}
}

//�����Ӱ���ѯ
function QuestPicQuery()
{
	var ContNo = fm.ContNo.value;
	if (ContNo == "" || ContNo=="null")
	{
	    alert("���޺�ͬͶ�����ţ����ȱ���!");
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
	if(PrtNo == "null" || PrtNo == "")
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
	
		var sqlid28="BankContCheckSql28";
		var mySql28=new SqlClass();
		mySql28.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql28.setSqlId(sqlid28);//ָ��ʹ�õ�Sql��id
		mySql28.addSubPara(fm.AppntName.value);//ָ������Ĳ���
		mySql28.addSubPara(Sex);//ָ������Ĳ���
		mySql28.addSubPara(fm.AppntBirthday.value);//ָ������Ĳ���
		
		mySql28.addSubPara(fm.AppntNo.value);//ָ������Ĳ���
		mySql28.addSubPara(fm.AppntIDNo.value);//ָ������Ĳ���
		mySql28.addSubPara(fm.AppntNo.value);//ָ������Ĳ���
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
	
		var sqlid29="BankContCheckSql29";
		var mySql29=new SqlClass();
		mySql29.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql29.setSqlId(sqlid29);//ָ��ʹ�õ�Sql��id
		mySql29.addSubPara(tInsuredName);//ָ������Ĳ���
		mySql29.addSubPara(tInsuredSex);//ָ������Ĳ���
		mySql29.addSubPara(tBirthday);//ָ������Ĳ���
		
		mySql29.addSubPara(tInsuredNo);//ָ������Ĳ���
		mySql29.addSubPara(fm.IDNo.value);//ָ������Ĳ���
		mySql29.addSubPara(tInsuredNo);//ָ������Ĳ���
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
	   alert("û����ñ��������ƵĿͻ�,����У��");
	   return false;
    }

    window.open("../uw/AppntChkMain.jsp?ProposalNo1="+fm.ContNo.value+"&Flag=I&LoadFlag="+LoadFlag+"&InsuredNo="+tInsuredNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+ActivityID,"window1",sFeatures);
}

//ǿ�ƽ����˹��˱�
function forceUW()
{
	//��ѯ�Ƿ��Ѿ�����ѡ��ǿ�ƽ����˹��˱�
    var ContNo=document.all("ContNo").value;
    if (ContNo == "" || ContNo=="null")
	{
	    alert("δ��ѯ����ͬ��Ϣ,������������ [ǿ�ƽ����˹��˱�] ��");
	    return;
	}
	
	
		var sqlid30="BankContCheckSql30";
		var mySql30=new SqlClass();
		mySql30.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql30.setSqlId(sqlid30);//ָ��ʹ�õ�Sql��id
		mySql30.addSubPara(ContNo);//ָ������Ĳ���

	    var sqlstr=mySql30.getString();	
	
    var sqlstr="select forceuwflag from lccont where contno='"+ContNo+"'" ;
    arrResult = easyExecSql(sqlstr,1,0);
    if(arrResult==null){
  	    alert("�����ڸ�Ͷ������");
    }
    else
    {
        window.open("../uw/ForceUWMain.jsp?ContNo="+ContNo,"window1",sFeatures);
    }
}


/*********************************************************************
 *  �������Ͷ�������ύ��Ĳ���,���������ݷ��غ�ִ�еĲ���
 ********************************************************************/
function afterSubmit( FlagStr, content,contract )
{
	if( FlagStr == "Fail" )
	{
		showInfo.close();
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
		showInfo.close();
		content = "����ɹ���";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		showDiv(operateButton, "true");
		top.opener.easyQueryClickSelf();
		top.close();
	}
	mAction = "";
	
}

/*********************************************************************
 *  ��ʾfrmSubmit��ܣ���������
 *  ����  ��  ��
 *  ����ֵ��  ��
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
 *  Click�¼������������ͼƬʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function addClick()
{
	//����������Ӧ�Ĵ���
	showDiv( operateButton, "false" );
	showDiv( inputButton, "true" );
	if( verifyInput2() == false ) return false;
}

/*********************************************************************
 *  Click�¼������������ѯ��ͼƬʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function queryClick()
{
        if(this.ScanFlag == "1"){
	  alert( "��ɨ���¼�벻�����ѯ!" );
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
 *  ��ʾdiv
 *  ����  ��  ��һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
 *  ����ֵ��  ��
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
 *  ��ѯ������ϸ��Ϣʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
 *  ����  ��  ��ѯ���صĶ�ά����
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterQuery( arrQueryResult ) {

	if( arrQueryResult != null ) {
		arrResult = arrQueryResult;

		if( mOperate == 1 ){		// ��ѯͶ����
			document.all( 'ContNo' ).value = arrQueryResult[0][0];
			
		var sqlid31="BankContCheckSql31";
		var mySql31=new SqlClass();
		mySql31.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql31.setSqlId(sqlid31);//ָ��ʹ�õ�Sql��id
		mySql31.addSubPara(arrQueryResult[0][0]);//ָ������Ĳ���

	    var sqlstr=mySql31.getString();	
			
			arrResult = easyExecSql(sqlstr, 1, 0);
			//arrResult = easyExecSql("select ProposalContNo,PrtNo,ManageCom,SaleChnl,AgentCode,AgentGroup,AgentCode1,AgentCom,AgentType,Remark from LCCont where LCCont.ProposalContNo = '" + arrQueryResult[0][0] + "'", 1, 0);
			//prompt('',"select ProposalContNo,PrtNo,ManageCom,SaleChnl,AgentCode,AgentGroup,AgentCode1,AgentCom,AgentType,Remark from LCCont where LCCont.ProposalContNo = '" + arrQueryResult[0][0] + "'");
			if (arrResult == null) {
			  alert("δ�鵽Ͷ������Ϣ");
			} else {
			   displayLCContPol(arrResult[0]);
			}
		}

		if( mOperate == 2 )	{		// Ͷ������Ϣ
			//alert("arrQueryResult[0][0]=="+arrQueryResult[0][0]);
			
			arrResult = easyExecSql(sqlstr, 1, 0);
			
		var sqlid32="BankContCheckSql32";
		var mySql32=new SqlClass();
		mySql32.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql32.setSqlId(sqlid32);//ָ��ʹ�õ�Sql��id
		mySql32.addSubPara(arrQueryResult[0][0]);//ָ������Ĳ���

	    var sqlstr32=mySql32.getString();	
			
			arrResult = easyExecSql(sqlstr32, 1, 0);
			//arrResult = easyExecSql("select a.*  from LDPerson a where 1=1  and a.CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0);
			if (arrResult == null) {
			  alert("δ�鵽Ͷ������Ϣ");
			} else {
			   displayAppnt(arrResult[0]);
			}
		}
	}

	mOperate = 0;		// �ָ���̬

	showCodeName();

}

/*********************************************************************
 *  ��ѯ���غ󴥷�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterQuery21( arrQueryResult )
{
  //alert("1111here:" + arrQueryResult + "\n" + mOperate);

    if( arrQueryResult != null )
    {
        arrResult = arrQueryResult;

        if( mOperate == 1 )
        {		// ��ѯͶ����
            document.all( 'ContNo' ).value = arrQueryResult[0][0];

		var sqlid33="BankContCheckSql33";
		var mySql33=new SqlClass();
		mySql33.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql33.setSqlId(sqlid33);//ָ��ʹ�õ�Sql��id
		mySql33.addSubPara(arrQueryResult[0][0]);//ָ������Ĳ���

	    var sqlstr33=mySql33.getString();	

        arrResult = easyExecSql(sqlstr33, 1, 0);
           // arrResult = easyExecSql("select ProposalContNo,PrtNo,ManageCom,SaleChnl,AgentCode,AgentGroup,AgentCode1,AgentCom,AgentType,Remark from LCCont where LCCont.ProposalContNo = '" + arrQueryResult[0][0] + "'", 1, 0);

            if (arrResult == null)
            {
                alert("δ�鵽Ͷ������Ϣ");
            }
            else
            {
                displayLCContPol(arrResult[0]);
            }
        }

        if( mOperate == 3 )
        {		// ��������Ϣ
        	//arrResult = easyExecSql("select a.*,b.AddressNo,b.PostalAddress,b.ZipCode,b.HomePhone,b.Mobile,b.EMail,a.GrpNo,b.CompanyPhone,b.CompanyAddress,b.CompanyZipCode from LDPerson a,LCAddress b where 1=1 and a.CustomerNo=b.CustomerNo and a.CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0);
        	
		var sqlid34="BankContCheckSql34";
		var mySql34=new SqlClass();
		mySql34.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql34.setSqlId(sqlid34);//ָ��ʹ�õ�Sql��id
		mySql34.addSubPara(arrQueryResult[0][0] );//ָ������Ĳ���

	    var sqlstr34=mySql34.getString();	
			
			arrResult = easyExecSql(sqlstr34, 1, 0);
			//arrResult = easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0);
        	if (arrResult == null)
        	{
        	    alert("δ�鵽Ͷ������Ϣ");
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

	mOperate = 0;		// �ָ���̬
	showCodeName();
}

/*********************************************************************
 *  �Ѳ�ѯ���ص�Ͷ����������ʾ��Ͷ���˲���
 *  ����  ��  ��
 *  ����ֵ��  ��
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
	
	
	//˳�㽫Ͷ���˵�ַ��Ϣ�Ƚ��г�ʼ��
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
 *Ͷ������ϸ������ʾ
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
 *  �Ѳ�ѯ���صĿͻ�������ʾ��Ͷ���˲���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayAccount()
{
	try{document.all('AppntBankAccNo').value= arrResult[0][24]; }catch(ex){};
	try{document.all('AppntBankCode').value= arrResult[0][23]; }catch(ex){};
	try{document.all('AppntAccName').value= arrResult[0][25]; }catch(ex){};

}
/*********************************************************************
 *  �Ѳ�ѯ���صĿͻ�������ʾ��Ͷ���˲���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayCustomer()
{
	try{document.all('AppntNationality').value= arrResult[0][8]; }catch(ex){};
}
/*********************************************************************
 *  �Ѳ�ѯ���صĿͻ���ַ������ʾ��Ͷ���˲���
 *  ����  ��  ��
 *  ����ֵ��  ��
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
 *  �Ѳ�ѯ���صĺ�ͬ�б�����������ʾ��ҳ��
 *  ����  ��  ��
 *  ����ֵ��  ��
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

//ʹ�ôӸô��ڵ����Ĵ����ܹ��۽�
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

//Ͷ���˿ͻ��Ų�ѯ��Ť�¼�
function queryAppntNo() {

	if (document.all("AppntNo").value == "" && loadFlag == "1")
	{
    showAppnt1();
  }
  else if (loadFlag != "1" && loadFlag != "2")
  {
     alert("ֻ����Ͷ����¼��ʱ���в�����");
  }
  else
  {
  	
		var sqlid35="BankContCheckSql35";
		var mySql35=new SqlClass();
		mySql35.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql35.setSqlId(sqlid35);//ָ��ʹ�õ�Sql��id
		mySql35.addSubPara(arrQueryResult[0][0] );//ָ������Ĳ���

	    var sqlstr35=mySql35.getString();	
	
	 arrResult = easyExecSql(sqlstr35, 1, 0);
   // arrResult = easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo = '" + document.all("AppntNo").value + "'", 1, 0);
    if (arrResult == null) {
      alert("δ�鵽Ͷ������Ϣ");
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

//�����¼��
function QuestInput2()
{ 
	cContNo = fm.ContNo.value;  //��������
	if(cContNo == "")
	{
		alert("���޺�ͬͶ�����ţ����ȱ���!");
	}
	else
	{
		window.open("../uw/QuestInputMain.jsp?ContNo="+cContNo+"&Flag="+ LoadFlag,"window1",sFeatures);
	}
}

/********************************************************************
 *  ����򺺻�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showCodeName()
{

	showAppntCodeName();
	showContCodeName();
	showInsuredCodeName();
}

/*********************************************************************
 *  ��ͬ����򺺻�
 *  ����  ��  ��
 *  ����ֵ��  ��
 **********************************************************************/
 function showContCodeName()
{
	quickGetName('comcode',fm.ManageCom,fm.ManageComName);
	quickGetName('Relation',fm.RelationToInsured,fm.RelationToInsuredName);
	quickGetName('sellType',fm.SellType,fm.sellTypeName);
	//tongmeng 2007-09-10 modify
	//ȥ��Ӧ�б����ѯ
	//quickGetName('HeadOffice',fm.AgentBankCode,fm.AgentBankCodeName);
}

/*********************************************************************
 *  Ͷ���˴���򺺻�
 *  ����  ��  ��
 *  ����ֵ��  ��
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
 *  �����˴���򺺻�
 *  ����  ��  ��
 *  ����ֵ��  ��
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

//���ݵ�ַ�����ѯ��ַ������Ϣ,��ѯ��ַ�����<ldaddress>
//�������,��ַ����<province--ʡ;city--��;district--��/��;>,��ַ����<�������>,��ַ������Ϣ<���ֱ���>
//����,ֱ��Ϊ--��ַ������Ϣ<���ֱ���>--��ֵ
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
		
		var sqlid36="BankContCheckSql36";
		var mySql36=new SqlClass();
		mySql36.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql36.setSqlId(sqlid36);//ָ��ʹ�õ�Sql��id
		mySql36.addSubPara(PlaceCode );//ָ������Ĳ���
		mySql36.addSubPara(PlaceType);//ָ������Ĳ���

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
* ���ݴ���ѡ��Ĵ�����Ҳ���ʾ���ƣ���ʾָ����һ��
* strCode - ����ѡ��Ĵ���
* showObjCode - �����ŵĽ������
* showObjName - Ҫ��ʾ���ƵĽ������
*/
function quickGetName(strCode, showObjc, showObjn) {
	var formsNum = 0;	//�����е�FORM��
	var elementsNum = 0;	//FORM�е�Ԫ����
	var urlStr = "";
	var sFeatures = "";
	var strQueryResult = "";	//����ѡ��Ĳ�ѯ�����
	var arrCode = null;	//�������
	var strCodeValue = "";	//����ֵ
	var cacheFlag = false;	//�ڴ��������ݱ�־
	var showObjn;
	var showObjc;
	//��������FORM


	//��������������ݲ�Ϊ�գ��Ų�ѯ���������κβ���
	if (showObjc.value != "")
	{
		//Ѱ��������
		if(cacheWin==null)
		{
			 cacheWin = searchMainWindow(this);
			if (cacheWin == false) { cacheWin = this; }
		}

		//��������������ݣ���������ȡ����
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
				//�������ݿ����CODE��ѯ�����ز�ѯ�����strQueryResult
				//strQueryResult = window.showModalDialog(urlStr, "", sFeatures);
				var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
				var iWidth=150;      //�������ڵĿ��; 
				var iHeight=0;     //�������ڵĸ߶�; 
				var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
				var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
				showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

				showInfo.focus();
			}
		}
		else if(strCode=='OccupationCode')
		{
			
					var sqlid37="BankContCheckSql37";
		var mySql37=new SqlClass();
		mySql37.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql37.setSqlId(sqlid37);//ָ��ʹ�õ�Sql��id
		mySql37.addSubPara(showObjc.value );//ָ������Ĳ���

	    var tSQL=mySql37.getString();	
			
//				var tSQL = "select trim(OccupationCode), trim(OccupationName), trim(OccupationType),  "
//				         + " (select codename from ldcode where codetype='occupationtype'  and trim(code)=trim(OccupationType)) from LDOccupation "
//				         + " where 1 = 1and worktype = 'GR' and OccupationCode='"+showObjc.value+"' order by OccupationCode ";
				strQueryResult = easyQueryVer3(tSQL, 1, 0, 1);        
				         
		}
		//��ֳ�����
		try {
			if (!cacheFlag)
			{
				try
				{
					arrCode = decodeEasyQueryResult(strQueryResult);
				}
				catch(ex)
				{
					alert("ҳ��ȱ������EasyQueryVer3.js");
				}
				strCodeSelect = "";
				for (i=0; i<arrCode.length; i++)
				{
					strCodeSelect = strCodeSelect + "<option value=" + arrCode[i][0] + ">";
					strCodeSelect = strCodeSelect + arrCode[i][0] + "-" + arrCode[i][1];
					strCodeSelect = strCodeSelect + "</option>";
				}
				//����ֺõ����ݷŵ��ڴ���
				cacheWin.parent.VD.gVCode.addArrVar(strCode, "", arrCode);
				//�����Ƿ������ݴӷ������˵õ�,�����øñ���
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
	
	var arrPolAppntDate = PolAppntDate.split("-");
	if (arrPolAppntDate[1].length == 1) arrPolAppntDate[1] = "0" + arrBirthDate[1];
	if (arrPolAppntDate[2].length == 1) arrPolAppntDate[2] = "0" + arrBirthDate[2];

	if(arrPolAppntDate[0]<=99)
	{
		arrBirthDate[0]=	arrBirthDate[0]-1900;
	}
	age = arrPolAppntDate[0] - arrBirthDate[0] - 1;
	
	//��ǰ�´��ڳ�����
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
		alert("��������ֻ��Ϊ��ǰ������ǰ!");
		return;
	}
	fm.AppntAge.value=calPolAppntAge(fm.AppntBirthday.value,fm.PolAppntDate.value);
  	return ;
}

//������������<����������Ӧ��ΪͶ������������֮��;2005-11-18��
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

/*********************************************************************
 *  ���漯��Ͷ�������ύ 
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function submitForm()
{  
	if(fm.PolAppntDate.value.length!=10 || fm.PolAppntDate.value.substring(4,5)!='-' || fm.PolAppntDate.value.substring(7,8)!='-' || fm.PolAppntDate.value.substring(0,1)=='0')
	{
		alert("��������ȷ�����ڸ�ʽ��");
		fm.PolAppntDate.focus();
		return;
    }

	var tSql = "select now() from dual";
	turnPage.strQueryResult = easyQueryVer3(tSql, 1, 0, 1);
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	    
	var tSysDate = turnPage.arrDataCacheSet[0][0];    

	if(fm.PolAppntDate.value>tSysDate)
	{
		alert("Ͷ������Ӧ��С�ڵ�ǰ����")
		return ;
	}

	if(fm.AppntIDType.value!=""&&fm.AppntIDNo.value==""||fm.AppntIDType.value==""&&fm.AppntIDNo.value!=""){
		alert("֤�����ͺ�֤���������ͬʱ��д����");
		return false;
		}

    //hanlin ����У������⣬��ʱȥ������ͷ�ټ��ϣ�hl 20050502
    //У���Ѿ����� hl 20050512
    if( verifyInputNB("1") == false ) return false;

	/*����Ƿ����޸�ʱʹ�ã�ִ�и��²��� 20041125 wzw*/
	if(LoadFlag=="3"){
	  updateClick();
	  return;
	}

    if(document.all('PayMode').value=='7'){

    }
    if(document.all('SecPayMode').value=='7'){

    }
   //alert(fm.Remark.value);

	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  
	if( mAction == "" )
	{
		//showSubmitFrame(mDebug);
		getdetailwork();
		mAction = "INSERT";
		document.all( 'fmAction' ).value = mAction;
		ImpartGrid.delBlankLine();

		if (document.all('ProposalContNo').value == "") {
		  //alert("��ѯ���ֻ�ܽ����޸Ĳ�����");
		  mAction = "";
		} else {
		  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();

		  //��ȫ���ûᴫ2����������Ĭ��Ϊ0������ֵ�ڱ������е�appflag�ֶ�
		  tAction = fm.action;
		  //alert("taction==="+tAction);
		  fm.action = fm.action + "?BQFlag=" + BQFlag;
		  //alert("fm==="+fm.action);
		  fm.submit(); //�ύ
		  fm.action = tAction;
		}
	}
}

/*********************************************************************
 *  Click�¼�����������޸ġ�ͼƬʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function updateClick()
{
	
  if(fm.AppntIDType.value!=""&&fm.AppntIDNo.value==""||fm.AppntIDType.value==""&&fm.AppntIDNo.value!=""){
	  alert("֤�����ͺ�֤���������ͬʱ��д����");
	  return false;
	}
  if((fm.PayMode.value=="4"||fm.PayMode.value=="7")&&fm.AppntBankCode.value==""&&fm.AppntAccName.value==""&&fm.AppntBankAccNo.value=="")
	{

		alert("����������ת�ʿ����С������ʻ������������ʻ��ţ�");
		return false;
	}
  if(fm.SecPayMode.value=="3"&&fm.SecAppntBankCode.value==""&&fm.SecAppntAccName.value==""&&fm.SecAppntBankAccNo.value=="")
	{

		alert("����������ת�ʿ����С������ʻ������������ʻ��ţ�");
		return false;
	}
	
	if(fm.AppntNo.value==''&&fm.AppntAddressNo.value!='')
    {
        alert("Ͷ���˿ͻ���Ϊ�գ������е�ַ����");
        return false;
    }
    if( verifyInputNB("1") == false ) return false;
	var tGrpProposalNo = "";
	tGrpProposalNo = document.all( 'ProposalContNo' ).value;
	if( tGrpProposalNo == null || tGrpProposalNo == "" )
		alert( "������Ͷ������ѯ�������ٽ����޸�!" );
	else
	{
		ImpartGrid.delBlankLine();
		var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;

		if( mAction == "" )
		{
			//showSubmitFrame(mDebug);
			getdetailwork();
			//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
			mAction = "UPDATE";
			document.all( 'fmAction' ).value = mAction;
			fm.action="BankContSave.jsp"; 
			fm.submit(); //�ύ
		}
	}
}

function getdetail()
{
	//alert("fm.AppntNo.value=="+fm.AppntNo.value);
	
		var sqlid38="BankContCheckSql38";
		var mySql38=new SqlClass();
		mySql38.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql38.setSqlId(sqlid38);//ָ��ʹ�õ�Sql��id
		mySql38.addSubPara(fm.AppntBankAccNo.value );//ָ������Ĳ���
        mySql38.addSubPara(fm.AppntNo.value );//ָ������Ĳ���
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
		mySql39.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql39.setSqlId(sqlid39);//ָ��ʹ�õ�Sql��id
		mySql39.addSubPara(fm.AppntOccupationCode.value );//ָ������Ĳ���
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
 *  Click�¼����������ɾ����ͼƬʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
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
		alert( "������Ͷ������ѯ�������ٽ���ɾ��!" );
	else
	{
		var showStr = "����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;

		if( mAction == "" )
		{
			//showSubmitFrame(mDebug);
			//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
			mAction = "DELETE";
			document.all( 'fmAction' ).value = mAction;
			fm.action="BankContSave.jsp"; 
			fm.submit(); //�ύ
		}
	 }
      }
      //top.close();
}

/*********************************************************************
 *  ��ӱ�������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function addRecord()
{ 
	fm.SequenceNo.value="1";
	fm.RelationToMainInsured.value="00";
	//�ж��Ƿ��Ѿ���ӹ�Ͷ���ˣ�û�еĻ���������ӱ�����
	if(!checkAppnt()){
	  alert("������Ӻ�ͬ��Ϣ��Ͷ������Ϣ��");
	  fm.AppntName.focus();
	  return;
	}

  //2005.03.18 chenhq �Դ˽����޸�
  if(LoadFlag==1){

	 if(fm.SamePersonFlag.checked==true&&fm.RelationToAppnt.value!="00")
	  {
	   alert("��Ͷ���˹�ϵֻ��ѡ���ˣ�");
	   fm.RelationToAppnt.value="00";
	   fm.RelationToAppntName.value="����";
	   return ;
	  }
	  var tPrtNo=document.all("PrtNo").value;

		var sqlid40="BankContCheckSql40";
		var mySql40=new SqlClass();
		mySql40.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql40.setSqlId(sqlid40);//ָ��ʹ�õ�Sql��id
		mySql40.addSubPara(tPrtNo );//ָ������Ĳ���
	    var sqlstr=mySql40.getString();	

//	  var sqlstr="select SequenceNo from LCInsured where PrtNo='"+tPrtNo+"'";

    arrResult=easyExecSql(sqlstr,1,0);

    if(arrResult!=null){
      for(var sequencenocout=0; sequencenocout<arrResult.length;sequencenocout++ )
      {
        if(fm.SequenceNo.value==arrResult[sequencenocout][0]){
	        alert("�Ѿ����ڸÿͻ��ڲ���");
	        fm.SequenceNo.focus();
	        return false;
	      }
	    }
	  }
	}

  //2005.03.18 chenhq �Դ˽����޸�
  if(LoadFlag==3){

	  var tPrtNo=document.all("PrtNo").value;

		var sqlid41="BankContCheckSql41";
		var mySql41=new SqlClass();
		mySql41.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql41.setSqlId(sqlid41);//ָ��ʹ�õ�Sql��id
		mySql41.addSubPara(tPrtNo );//ָ������Ĳ���
	    var sqlstr=mySql41.getString();	

	  //var sqlstr="select SequenceNo from LCInsured where PrtNo='"+tPrtNo+"'";

    arrResult=easyExecSql(sqlstr,1,0);

    if(arrResult!=null){
      for(var sequencenocout=0; sequencenocout<arrResult.length;sequencenocout++ )
      {
        if(fm.SequenceNo.value==arrResult[sequencenocout][0]){
	        alert("�Ѿ����ڸÿͻ��ڲ���");
	        fm.SequenceNo.focus();
	        return false;
	      }
	    }
	  }
	}


  //2005.03.18 chenhq �Դ˽����޸�
  if(LoadFlag==5){

	  var tPrtNo=document.all("PrtNo").value;

		var sqlid42="BankContCheckSql42";
		var mySql42=new SqlClass();
		mySql42.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql42.setSqlId(sqlid42);//ָ��ʹ�õ�Sql��id
		mySql42.addSubPara(tPrtNo );//ָ������Ĳ���
	    var sqlstr=mySql42.getString();	

//	  var sqlstr="select SequenceNo from LCInsured where PrtNo='"+tPrtNo+"'";

    arrResult=easyExecSql(sqlstr,1,0);

    if(arrResult!=null){
      for(var sequencenocout=0; sequencenocout<arrResult.length;sequencenocout++ )
      {
        if(fm.SequenceNo.value==arrResult[sequencenocout][0]){
	        alert("�Ѿ����ڸÿͻ��ڲ���");
	        fm.SequenceNo.focus();
	        return false;
	      }
	    }
	  }
	}


  //2005.03.18 chenhq �Դ˽����޸�
  if(LoadFlag==6){

	  var tPrtNo=document.all("PrtNo").value;

		var sqlid43="BankContCheckSql43";
		var mySql43=new SqlClass();
		mySql43.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql43.setSqlId(sqlid43);//ָ��ʹ�õ�Sql��id
		mySql43.addSubPara(tPrtNo );//ָ������Ĳ���
	    var sqlstr=mySql43.getString();	

//	  var sqlstr="select SequenceNo from LCInsured where PrtNo='"+tPrtNo+"'";

    arrResult=easyExecSql(sqlstr,1,0);

    if(arrResult!=null){
      for(var sequencenocout=0; sequencenocout<arrResult.length;sequencenocout++ )
      {
        if(fm.SequenceNo.value==arrResult[sequencenocout][0]){
	        alert("�Ѿ����ڸÿͻ��ڲ���");
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
    alert("�������˿ͻ���Ϊ�գ������е�ַ����");
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
 *  �޸ı�ѡ�еı�������
 *  �˴���ӱ������޸�У�飬����޸��˹ؼ���Ϣ<�������Ա�֤�����ͻ���롢��������>���� ְҵ���
 *  ������ɾ��������Ϣ����Ϊ����ܻ����ͻ��Ż����¼��㱣��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function modifyRecord()
{
	if (InsuredGrid.mulLineCount==0)
	{
		alert("�ñ������˻�û�б��棬�޷������޸ģ�");
		return false;
	}
	var tselno=InsuredGrid.getSelNo();
	if(tselno==0)
	{
		alert("��ѡ����Ҫ�޸ı����˼�¼");
		return false;
	}
	var tOldInsuredNo=InsuredGrid.getRowColData(tselno-1,1);
	
		var sqlid44="BankContCheckSql44";
		var mySql44=new SqlClass();
		mySql44.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql44.setSqlId(sqlid44);//ָ��ʹ�õ�Sql��id
		mySql44.addSubPara(fm.ContNo.value );//ָ������Ĳ���
		mySql44.addSubPara(tOldInsuredNo );//ָ������Ĳ���
	    var SSQL=mySql44.getString();	
	
//	var SSQL=" select t.insuredno,t.name,t.sex,t.birthday,t.idtype,t.idno,t.occupationcode"
//		+" from lcinsured t where t.contno='"+fm.ContNo.value+"' and t.insuredno='"+tOldInsuredNo+"'";
	var tOldArr=easyExecSql(SSQL,1,0);
	//��ȡ׼���޸ĵ���Ϣ,ͬ��ѯ������Ϣ�Ƚϣ������һ�ͬ����ô��ѯ�Ƿ������֣�������ʾ����ɾ��������Ϣ
	if( fm.Name.value!=tOldArr[0][1] || fm.IDType.value!=tOldArr[0][4] || fm.IDNo.value!=tOldArr[0][5] 
	  || fm.Sex.value!=tOldArr[0][2] || fm.Birthday.value!=tOldArr[0][3] || fm.OccupationCode.value!=tOldArr[0][6])
	{
		var sqlstr="select polno from lcpol where contno='"+fm.ContNo.value+"' and insuredno='"+tOldInsuredNo+"' ";
		arrResult=easyExecSql(sqlstr,1,0);
		if(arrResult!=null)
		{
			alert("������޸��˸ñ����˹ؼ���Ϣ<�������Ա�֤�����ͻ���롢��������>��ְҵ�������ɾ���ñ������µ�������Ϣ��");
			return false;    
		}
	}	
	if(fm.SamePersonFlag.checked==true&&fm.RelationToAppnt.value!="00")
	{
		alert("��Ͷ���˹�ϵֻ��ѡ���ˣ�");
		fm.RelationToAppnt.value="00";
		fm.RelationToAppntName.value="����";
		return ;
	}
    if (document.all('PolTypeFlag').value==0)
    {
        if( verifyInputNB('2') == false ) return false;
    }
    //У�鱻�����������������˹�ϵ
    if(!checkself()) { return false;}
    if (fm.InsuredNo.value==''&&fm.InsuredAddressNo.value!='')
    {
        alert("�������˿ͻ���Ϊ�գ������е�ַ����");
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
 *  ɾ����ѡ�еı�������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
 function deleteRecord()
{
    if (fm.InsuredNo.value=='')
    {
        alert("��ѡ����Ҫɾ���Ŀͻ���")
        return false;
    }
    if (InsuredGrid.mulLineCount==0)
    {
        alert("�ñ������˻�û�б��棬�޷������޸ģ�");
        return false;
    }
     if (fm.InsuredNo.value==''&&fm.InsuredAddressNo.value!='')
    {
        alert("�������˿ͻ���Ϊ�գ������е�ַ����");
        return false;
    }
	
		var sqlid45="BankContCheckSql45";
		var mySql45=new SqlClass();
		mySql45.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql45.setSqlId(sqlid45);//ָ��ʹ�õ�Sql��id
		mySql45.addSubPara(fm.ContNo.value );//ָ������Ĳ���
	    var sqlstr=mySql45.getString();	
	
//    var sqlstr="select polno from  lcpol where contNo='"+fm.ContNo.value+"'";
    arrResult=easyExecSql(sqlstr,1,0);
    
    if(arrResult!=null)
    {
    	alert("����ɾ��������������Ϣ��");
        return false;    
    }
    document.all('ContType').value=ContType;
    document.all('fmAction').value="DELETE||CONTINSURED";
    fm.action="BankContInsuredSave.jsp";
    fm.submit();
}

/*********************************************************************
 *  ��ѯְҵ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function getdetailwork2()
{
	
			var sqlid46="BankContCheckSql46";
		var mySql46=new SqlClass();
		mySql46.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql46.setSqlId(sqlid46);//ָ��ʹ�õ�Sql��id
		mySql46.addSubPara(fm.OccupationCode.value );//ָ������Ĳ���
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
 *  ����������Ϣ¼��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function intoRiskInfo()
{
  
	//�Ѻ�ͬ��Ϣ�ͱ�������Ϣ�����ڴ�
	mSwitch = parent.VD.gVSwitch;  //���ݴ�
	putCont();   //ע��ú�����BankContInput.js��
	if(fm.InsuredNo.value==""||fm.ContNo.value==""||InsuredGrid.mulLineCount=="0")
	{
		alert("������ӣ�ѡ�񱻱���");
		return false;
	}
	delInsuredVar();
	addInsuredVar();

	try{mSwitch.deleteVar('PolNo')}catch(ex){};
	try{mSwitch.addVar('PolNo','',fm.SelPolNo.value);}catch(ex){}
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
  
  	parent.fraInterface.window.location = "./ProposalInput.jsp?LoadFlag=" + LoadFlag+"&ContType="+ContType+"&scantype=" + scantype+ "&MissionID=" + MissionID+ "&SubMissionID=" + SubMissionID+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&ActivityID="+ActivityID+"&NoType="+NoType+"&hh=1&checktype="+checktype+"&ProposalContNo="+fm.ProposalContNo.value+"&ScanFlag="+ScanFlag+"&BankFlag=1"+"&InsuredChkFlag="+document.all('InsuredChkButton').disabled+"&AppntChkFlag="+document.all('AppntChkButton').disabled;
}

/*********************************************************************
 *  �Ѻ�ͬ��Ϣ�����ڴ�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function putCont()
{
	delContVar();
	addIntoCont();
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
 *  �������ּƻ�����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
 function grpRiskPlanInfo()
{
    var newWindow = window.open("../app/GroupRiskPlan.jsp","",sFeatures);
}
/*********************************************************************
 *  ����ѡ��󴥷�ʱ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
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
		        param="121";
		        fm.pagename.value="121";
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
 *  ���ݼ�ͥ�����ͣ����ؽ���ؼ�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function choosetype(){
	if(fm.FamilyType.value=="1")
	divTempFeeInput.style.display="";
	if(fm.FamilyType.value=="0")
	divTempFeeInput.style.display="none";
}
/*********************************************************************
 *  У�鱻�����������������˹�ϵ
 *  ����  ��  ��
 *  ����ֵ��  true or false
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
	  alert("���˵���'�����������˹�ϵ'ֻ����'����'");
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
	  alert("��ͥ���е�һλ�������˵�'�����������˹�ϵ'ֻ����'����'");
	  fm.RelationToMainInsured.value="00";
	  return false;
  }
  else{
    return true;
  }
}
/*********************************************************************
 *  У�鱣����
 *  ����  ��  ��
 *  ����ֵ��  true or false
 *********************************************************************
 */
function checkrelation()
{
	if(LoadFlag==2||LoadFlag==7)
	{
        if (document.all('ContNo').value != "")
        {
            alert("�ŵ��ĸ��������ж౻������");
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
                alert("���������ж౻������");
                return false;
            }
            else
                return true;
        }
        else if (document.all('ContNo').value != ""&&fm.FamilyType.value=="1"&&InsuredGrid.mulLineCount>0&&fm.RelationToMainInsured.value=="00")
        {
            alert("��ͥ��ֻ����һ������������");
            return false;
        }
        else if (document.all('ContNo').value != ""&&fm.FamilyType.value=="1"&&fm.RelationToAppnt.value=="00")
        {
            var strSql="select * from LCInsured where contno='"+document.all('ContNo').value +"' and RelationToAppnt='00' ";
            turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1);
            if(turnPage.strQueryResult)
		    {
                alert("Ͷ�����Ѿ��Ǹú�ͬ���µı�������");
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
 *  Ͷ�����뱻������ͬѡ����¼�
 *  ����  ��  ��
 *  ����ֵ��  true or false
 *********************************************************************
 */
function isSamePerson()
{
    //��Ӧδѡͬһ�ˣ��ִ򹳵����
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
    //��Ӧ��ͬһ�ˣ��ִ򹳵����
    else if (fm.SamePersonFlag.checked == true)
    {
      document.all('DivLCInsured').style.display = "none";
      divLCInsuredPerson.style.display = "none";
      divSalary.style.display = "none";

      displayissameperson();
    }
    //��Ӧ��ѡͬһ�˵����
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
	��try{document.all('InsuredProvince').value= "";}catch(ex){};
	��try{document.all('InsuredCity').value="";}catch(ex){};
	��try{document.all('InsuredDistrict').value="";}catch(ex){};
      try{document.all('IncomeWay').value=  "";}catch(ex){};
      try{document.all('Income').value=  "";}catch(ex){};

    }
}
/*********************************************************************
 *  Ͷ���˿ͻ��Ų�ѯ��Ť�¼�
 *  ����  ��  ��
 *  ����ֵ��  ��
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
		mySql47.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql47.setSqlId(sqlid47);//ָ��ʹ�õ�Sql��id
		mySql47.addSubPara(fm.InsuredNo.value );//ָ������Ĳ���
	    var strSql=mySql47.getString();
	  
	  arrResult = easyExecSql(strSql, 1, 0);
	    //arrResult = easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo = '" + document.all("InsuredNo").value + "'", 1, 0);
        if (arrResult == null)
        {
          alert("δ�鵽��������Ϣ");
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
		mOperate = 3;  //��������Ϣ��ѯ��mOperate = 3; hl20050503
		showInfo = window.open( "../sys/LDPersonQueryNewMain.jsp?queryFlag=queryInsured" ,"",sFeatures);
	}
}

/*********************************************************************
 *  ������һҳ��
 *  ����  ��  ��
 *  ����ֵ��  ��
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
	}  ���	��������Ĳ�ͬ�ݲ�֧��else��ʽ
*/
}

function DelRiskInfo()
{
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
	var tRow = PolGrid.getSelNo() - 1;
	var tpolno=PolGrid.getRowColData(tRow,1)
	document.all('fmAction').value="DELETE||INSUREDRISK";
	fm.action="./DelIsuredRisk.jsp?polno="+tpolno;
	fm.submit(); //�ύ

}

/*********************************************************************
 *  �Ѻ�ͬ������Ϣ¼�����ȷ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function inputConfirm()
{
    if(document.all('ProposalContNo').value == "" || document.all('ProposalContNo').value == "null")
    {
   	    alert("δ��ѯ����ͬ��Ϣ,������������ [�������] ȷ�ϣ�");
   	    return;
    }
	fm.WorkFlowFlag.value = "0000001001";					//�������
	fm.MissionID.value = tMissionID;
	fm.SubMissionID.value = tSubMissionID;
	approvefalg="2";
	
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
	
		 var sqlid48="BankContCheckSql48";
		var mySql48=new SqlClass();
		mySql48.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql48.setSqlId(sqlid48);//ָ��ʹ�õ�Sql��id
		mySql48.addSubPara(tContNo );//ָ������Ĳ���
	    var tempfeeSQL=mySql48.getString();
	
//	var tempfeeSQL="select sum(nvl(paymoney,0)) from ljtempfee where tempfeetype='1' and confdate is null "
//		+" and otherno=(select contno from lccont where prtno= '"+tContNo+"')";
	var TempFeeArr=easyExecSql(tempfeeSQL);	
	if(TempFeeArr!=null)
	{
		tTempFee=TempFeeArr[0][0];
	}
	//��ѯϵͳ���ɵı���
	
		var sqlid49="BankContCheckSql49";
		var mySql49=new SqlClass();
		mySql49.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql49.setSqlId(sqlid49);//ָ��ʹ�õ�Sql��id
		mySql49.addSubPara(tContNo );//ָ������Ĳ���
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

function exitAuditing(){
  if(confirm("ȷ�ϸ�Ͷ�����ĸ��˽��棿")){
    top.close();
  }
  else{
    return;
  }
}


function exitAuditing2(){
  if(confirm("ȷ���뿪��Ͷ����������޸Ľ��棿"))
  {
    top.close();
  }
  else{
    return;
  }
}

//���ƽ��水ť��������ʾ
function ctrlButtonDisabled(tContNo,tLoadFlag){
  var tSQL = "";
  var arrResult;
  var arrButtonAndSQL = new Array();
  
  if(tLoadFlag==5){
  	
			var sqlid50="BankContCheckSql50";
		var mySql50=new SqlClass();
		mySql50.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql50.setSqlId(sqlid50);//ָ��ʹ�õ�Sql��id
		mySql50.addSubPara(tContNo );//ָ������Ĳ���
	    var premfeeSQL=mySql50.getString();
	
    arrButtonAndSQL[0] = new Array();
    arrButtonAndSQL[0][0] = "ApproveQuestQuery";
    arrButtonAndSQL[0][1] = "�������ѯ";
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

//��ѯ����Ա�涯�ٶ�
function initQueryRollSpeed()
{
	
		var sqlid51="BankContCheckSql51";
		var mySql51=new SqlClass();
		mySql51.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql51.setSqlId(sqlid51);//ָ��ʹ�õ�Sql��id
//		mySql51.addSubPara(tContNo );//ָ������Ĳ���
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
		mySql51.setResourceName("app.BankContCheckSql"); //ָ��ʹ�õ�properties�ļ���
		mySql51.setSqlId(sqlid51);//ָ��ʹ�õ�Sql��id
		mySql51.addSubPara(fm.Operator.value );//ָ������Ĳ���
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

//���Զ���ý���Ŀؼ�����
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
    	window.open("./CustomerMergeMain.jsp?ScanFlag=1&LoadFlag=1&prtNo="+ContNo+"&InsuredNo="+tInsuredNo+"&InsuredName="+tInsuredName+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&CustomerType=I;"+sFeatures);        
    //}
}

function UnionConfirm(){

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
	fm.action = "./UnionConfirmSave.jsp";
	fm.submit(); //�ύ
}

function afterSubmit2( FlagStr, content )
{
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
		top.opener.easyQueryClick();// ˢ��ҳ��
		top.opener.easyQueryClickSelf();// ˢ��ҳ��
		top.opener.easyQueryClick();// ˢ��ҳ��
		top.opener.easyQueryClickSelf();// ˢ��ҳ��
		window.close();
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
