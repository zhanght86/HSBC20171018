//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPage2 = new turnPageClass();  
var turnPage3 = new turnPageClass();
var GrpContNo = "";
var mSwitch = parent.VD.gVSwitch;

//��ʼ��������Ϣ����¼����Ϣ
function initQuery()
{	
	GrpContNo = document.all('GrpContNo').value;
  
	//LPInsuredGrid.clearData("LPInsuredGrid");
	//var strSql = "select a.InsuredNo, a.InsuredName, b.RiskCode, a.ContNo from LCCont a left join LCPol b on a.contno = b.contno where a.AppFlag='1' and a.GrpContNo='" + GrpContNo + "'";
	//var strSql = "select contno,InsuredNo,InsuredName,riskcode,prem,amnt,polno from lcpol where appflag='2' and contno in (select contno from lccont where grpcontno='"+ GrpContNo +"' and appflag='1') order by contno"
	
	var mySql001=new SqlClass();
		mySql001.setResourceName("bq.GrpEdorTypeNRInputSql");//ָ��ʹ�õ�properties�ļ���
		mySql001.setSqlId("DSHomeContSql001");//ָ��ʹ�õ�Sql��id
		mySql001.addSubPara(GrpContNo);//ָ������Ĳ���
	
	var strSql=mySql001.getString();
	
	turnPage3.queryModal(strSql, LPPolGrid);  
}

//������¼�����
function afterCodeSelect(cCodeName, Field) 
{
	if (LPInsuredGrid.mulLineCount >= 500)
	{
		alert("���������������Χ������������һ����ȫ��Ŀ��");
		return;
	}

	if (cCodeName == "EdorRisk")
	{
		//showInfo = window.open("./ProposalMain.jsp?risk="+Field.value+"&prtNo="+prtNo+"&loadFlag=7");
	}
}

//��������
function edorSave()
{
	if (LPPolGrid.mulLineCount == 0)
	{
		alert("����¼�����ܱ������룡");
		return;
	}

	//for (i=0; i<LPInsuredGrid.mulLineCount; i++)
	//{
		//alert(LPInsuredGrid.getRowColData(i, 3));
	//	if (LPInsuredGrid.getRowColData(i, 3) == "")
	//	{
	//	  alert("�����Ƿ����еı������¶���¼������!");
	//	  return;
	//	}
	//}

	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	document.all('fmtransact').value = "INSERT";
	fm.submit();
}

function edorNewInsured()
{
	delGrpVar();
	addGrpVar();
	var tContNo = document.all("ContNo").value;
	var EdorTypeCal=document.all("EdorTypeCal").value;
	var tCustomerNo = document.all("InsuredNo").value; 
	//alert(tContNo);
	if(tContNo=="")
	{
		alert("��ѡ��һ����������!");
		return;
	}
	parent.fraInterface.window.location = "../appgrp/ContInsuredInput.jsp?ContNo=" + tContNo + "&InsuredNo="+tCustomerNo+"&EdorTypeCal="+EdorTypeCal+"&EdorValiDate="+fm.EdorValiDate.value+"&BQFlag=2&LoadFlag=8&ContType=2&type=noScan&EdorType=NR";
}

//��ʾ�ύ���
function afterSubmit( FlagStr, content )
{
	showInfo.close();
           
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
	//showInfo = showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	QueryEdorMoney();
}

function QueryEdorMoney()
{
	divGetMoney.style.display="";
	//var moneysql = "select sum(getmoney) from LJSGetEndorse where EndorsementNo='"+document.all('EdorNo').value+"' and FeeOperationType='NR'";
  
	var mySql002=new SqlClass();
		mySql002.setResourceName("bq.GrpEdorTypeNRInputSql");//ָ��ʹ�õ�properties�ļ���
		mySql002.setSqlId("DSHomeContSql002");//ָ��ʹ�õ�Sql��id
		mySql002.addSubPara(document.all('EdorNo').value);//ָ������Ĳ���

	var moneysql=mySql002.getString();
	
	var arrResult = easyExecSql(moneysql);
	try {document.all('GetMoney').value = arrResult[0][0];} catch(ex) { };
	
	if(document.all('GetMoney').value=='null'||document.all('GetMoney').value=='')
	{
		document.all('GetMoney').value="0.00";
	}
}


function delGrpVar()
{
    //ɾ���������ڻ����еĸ��˺�ͬ��Ϣ
    try { mSwitch.deleteVar('ContNo'); } catch(ex) { };
    try { mSwitch.deleteVar('ProposalContNo'); } catch(ex) { };
    
    //�����ͬ��Ϣ
    try { mSwitch.deleteVar('GrpContNo'); } catch(ex) { };
    try { mSwitch.deleteVar('ProposalGrpContNo'); } catch(ex) { };
    try { mSwitch.deleteVar('PrtNo'); } catch(ex) { };
    try { mSwitch.deleteVar('SaleChnl'); } catch(ex) { };
    try { mSwitch.deleteVar('ManageCom'); } catch(ex) { };
    try { mSwitch.deleteVar('AgentCom'); } catch(ex) { };
    try { mSwitch.deleteVar('AgentType'); } catch(ex) { };
    try { mSwitch.deleteVar('AgentCode'); } catch(ex) { };
    try { mSwitch.deleteVar('AgentGroup'); } catch(ex) { };
    try { mSwitch.deleteVar('AgentCode1'); } catch(ex) { };
    try { mSwitch.deleteVar('Password'); } catch(ex) { };
    try { mSwitch.deleteVar('Password2'); } catch(ex) { };
    try { mSwitch.deleteVar('AppntNo'); } catch(ex) { };
    try { mSwitch.deleteVar('AddressNo'); } catch(ex) { };
    try { mSwitch.deleteVar('Peoples2'); } catch(ex) { };
    try { mSwitch.deleteVar('GrpName'); } catch(ex) { };
    try { mSwitch.deleteVar('BusinessType'); } catch(ex) { };
    try { mSwitch.deleteVar('GrpNature'); } catch(ex) { };
    try { mSwitch.deleteVar('RgtMoney'); } catch(ex) { };
    try { mSwitch.deleteVar('Asset'); } catch(ex) { };
    try { mSwitch.deleteVar('NetProfitRate'); } catch(ex) { };
    try { mSwitch.deleteVar('MainBussiness'); } catch(ex) { };
    try { mSwitch.deleteVar('Corporation'); } catch(ex) { };
    try { mSwitch.deleteVar('ComAera'); } catch(ex) { };
    try { mSwitch.deleteVar('Fax'); } catch(ex) { };
    try { mSwitch.deleteVar('Phone'); } catch(ex) { };
    try { mSwitch.deleteVar('GetFlag'); } catch(ex) { };
    try { mSwitch.deleteVar('Satrap'); } catch(ex) { };
    try { mSwitch.deleteVar('EMail'); } catch(ex) { };
    try { mSwitch.deleteVar('FoundDate'); } catch(ex) { };
    try { mSwitch.deleteVar('GrpGroupNo'); } catch(ex) { };
    try { mSwitch.deleteVar('BankCode'); } catch(ex) { };
    try { mSwitch.deleteVar('BankAccNo'); } catch(ex) { };
    try { mSwitch.deleteVar('AccName'); } catch(ex) { };
    try { mSwitch.deleteVar('DisputedFlag'); } catch(ex) { };
    try { mSwitch.deleteVar('OutPayFlag'); } catch(ex) { };
    try { mSwitch.deleteVar('GetPolMode'); } catch(ex) { };
    try { mSwitch.deleteVar('Lang'); } catch(ex) { };
    try { mSwitch.deleteVar('Currency'); } catch(ex) { };
    try { mSwitch.deleteVar('LostTimes'); } catch(ex) { };
    try { mSwitch.deleteVar('PrintCount'); } catch(ex) { };
    try { mSwitch.deleteVar('RegetDate'); } catch(ex) { };
    try { mSwitch.deleteVar('LastEdorDate'); } catch(ex) { };
    try { mSwitch.deleteVar('LastGetDate'); } catch(ex) { };
    try { mSwitch.deleteVar('LastLoanDate'); } catch(ex) { };
    try { mSwitch.deleteVar('SpecFlag'); } catch(ex) { };
    try { mSwitch.deleteVar('GrpSpec'); } catch(ex) { };
    try { mSwitch.deleteVar('PayMode'); } catch(ex) { };
    try { mSwitch.deleteVar('SignCom'); } catch(ex) { };
    try { mSwitch.deleteVar('SignDate'); } catch(ex) { };
    try { mSwitch.deleteVar('SignTime'); } catch(ex) { };
    try { mSwitch.deleteVar('CValiDate'); } catch(ex) { };
    try { mSwitch.deleteVar('PayIntv'); } catch(ex) { };
    try { mSwitch.deleteVar('ManageFeeRate'); } catch(ex) { };
    try { mSwitch.deleteVar('ExpPeoples'); } catch(ex) { };
    try { mSwitch.deleteVar('ExpPremium'); } catch(ex) { };
    try { mSwitch.deleteVar('ExpAmnt'); } catch(ex) { };
    try { mSwitch.deleteVar('Peoples'); } catch(ex) { };
    try { mSwitch.deleteVar('Mult'); } catch(ex) { };
    try { mSwitch.deleteVar('Prem'); } catch(ex) { };
    try { mSwitch.deleteVar('Amnt'); } catch(ex) { };
    try { mSwitch.deleteVar('SumPrem'); } catch(ex) { };
    try { mSwitch.deleteVar('SumPay'); } catch(ex) { };
    try { mSwitch.deleteVar('Dif'); } catch(ex) { };
    try { mSwitch.deleteVar('Remark'); } catch(ex) { };
    try { mSwitch.deleteVar('StandbyFlag1'); } catch(ex) { };
    try { mSwitch.deleteVar('StandbyFlag2'); } catch(ex) { };
    try { mSwitch.deleteVar('StandbyFlag3'); } catch(ex) { };
    try { mSwitch.deleteVar('InputOperator'); } catch(ex) { };
    try { mSwitch.deleteVar('InputDate'); } catch(ex) { };
    try { mSwitch.deleteVar('InputTime'); } catch(ex) { };
    try { mSwitch.deleteVar('ApproveFlag'); } catch(ex) { };
    try { mSwitch.deleteVar('ApproveCode'); } catch(ex) { };
    try { mSwitch.deleteVar('ApproveDate'); } catch(ex) { };
    try { mSwitch.deleteVar('ApproveTime'); } catch(ex) { };
    try { mSwitch.deleteVar('UWOperator'); } catch(ex) { };
    try { mSwitch.deleteVar('UWFlag'); } catch(ex) { };
    try { mSwitch.deleteVar('UWDate'); } catch(ex) { };
    try { mSwitch.deleteVar('UWTime'); } catch(ex) { };
    try { mSwitch.deleteVar('AppFlag'); } catch(ex) { };
    try { mSwitch.deleteVar('PolApplyDate'); } catch(ex) { };
    try { mSwitch.deleteVar('CustomGetPolDate'); } catch(ex) { };
    try { mSwitch.deleteVar('GetPolDate'); } catch(ex) { };
    try { mSwitch.deleteVar('GetPolTime'); } catch(ex) { };
    try { mSwitch.deleteVar('State'); } catch(ex) { };
    //����Ͷ������Ϣ
    try { mSwitch.deleteVar('GrpNo'); } catch(ex) { };
    try { mSwitch.deleteVar('AddressNo'); } catch(ex) { };
    try { mSwitch.deleteVar('AppntGrade'); } catch(ex) { };
    try { mSwitch.deleteVar('GrpName'); } catch(ex) { };
    try { mSwitch.deleteVar('PostalAddress'); } catch(ex) { };
    try { mSwitch.deleteVar('GrpZipCode'); } catch(ex) { };
    try { mSwitch.deleteVar('Phone'); } catch(ex) { };
    try { mSwitch.deleteVar('Password'); } catch(ex) { };
    try { mSwitch.deleteVar('State'); } catch(ex) { };
    try { mSwitch.deleteVar('AppntType'); } catch(ex) { };
    try { mSwitch.deleteVar('RelationToInsured'); } catch(ex) { };
}

function addGrpVar()
{          
	var tContNo = document.all("ContNo").value;		   
	//var strSQL="select PolType, Peoples from LCCont where ContNo='" + tContNo + "'";
	
	var mySql003=new SqlClass();
		mySql003.setResourceName("bq.GrpEdorTypeNRInputSql");//ָ��ʹ�õ�properties�ļ���
		mySql003.setSqlId("DSHomeContSql003");//ָ��ʹ�õ�Sql��id
		mySql003.addSubPara(tContNo);//ָ������Ĳ���

	var strSQL=mySql003.getString();	
	
	var arrReturn = new Array();
	arrResult = easyExecSql(strSQL,1,0);  
	if(arrResult!=null)
	{    
		try
		{
			mSwitch.deleteVar("PolType");  
			mSwitch.addVar("PolType", "", arrResult[0][0]);     
			mSwitch.updateVar("PolType", "", arrResult[0][0]); 
				
			mSwitch.deleteVar("InsuredPeoples");     
			mSwitch.addVar("InsuredPeoples", "", arrResult[0][1]);     
			mSwitch.updateVar("InsuredPeoples", "", arrResult[0][1]); 
		}
		catch (ex)
		{}
	}

	try { mSwitch.addVar('ContNo','',tContNo); } catch(ex) { };   
	try { mSwitch.addVar('ProposalContNo','',tContNo); } catch(ex) { }; 

    //�����ͬ��Ϣ
	//var strsql = "select * from LCGrpCont where GrpContNo = '" + GrpContNo + "'";
	
	var mySql004=new SqlClass();
		mySql004.setResourceName("bq.GrpEdorTypeNRInputSql");//ָ��ʹ�õ�properties�ļ���
		mySql004.setSqlId("DSHomeContSql004");//ָ��ʹ�õ�Sql��id
		mySql004.addSubPara(GrpContNo);//ָ������Ĳ���

	var strsql=mySql004.getString();	
	
	turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);  
	if (turnPage.strQueryResult == "")
	{
		return;
	}
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

	try { mSwitch.addVar('GrpContNo','',turnPage.arrDataCacheSet[0][0]); } catch(ex) { }; //�����ͬ����
	try { mSwitch.addVar('ProposalGrpContNo','',turnPage.arrDataCacheSet[0][1]); } catch(ex) { }; //����Ͷ��������
	try { mSwitch.addVar('PrtNo','',turnPage.arrDataCacheSet[0][2]); } catch(ex) { }; //ӡˢ����
	try { mSwitch.addVar('SaleChnl','',turnPage.arrDataCacheSet[0][3]); } catch(ex) { }; //��������
	try { mSwitch.addVar('ManageCom','',turnPage.arrDataCacheSet[0][4]); } catch(ex) { }; //�������
	try { mSwitch.addVar('AgentCom','',turnPage.arrDataCacheSet[0][5]); } catch(ex) { }; //�������
	try { mSwitch.addVar('AgentType','',turnPage.arrDataCacheSet[0][6]); } catch(ex) { }; //��������ڲ�����
	try { mSwitch.addVar('AgentCode','',turnPage.arrDataCacheSet[0][7]); } catch(ex) { }; //�����˱���
	try { mSwitch.addVar('AgentGroup','',turnPage.arrDataCacheSet[0][8]); } catch(ex) { }; //���������
	try { mSwitch.addVar('AgentCode1','',turnPage.arrDataCacheSet[0][9]); } catch(ex) { }; //���ϴ����˴���
	try { mSwitch.addVar('Password','',turnPage.arrDataCacheSet[0][10]); } catch(ex) { }; //��������
	try { mSwitch.addVar('Password2','',turnPage.arrDataCacheSet[0][11]); } catch(ex) { }; //����
	try { mSwitch.addVar('AppntNo','',turnPage.arrDataCacheSet[0][12]); } catch(ex) { }; //�ͻ�����
	try { mSwitch.addVar('AddressNo','',turnPage.arrDataCacheSet[0][13]); } catch(ex) { }; //��ַ����
	try { mSwitch.addVar('Peoples2','',turnPage.arrDataCacheSet[0][14]); } catch(ex) { }; //Ͷ��������
	try { mSwitch.addVar('GrpName','',turnPage.arrDataCacheSet[0][15]); } catch(ex) { }; //��λ����
	try { mSwitch.addVar('BusinessType','',turnPage.arrDataCacheSet[0][16]); } catch(ex) { }; //��ҵ����
	try { mSwitch.addVar('GrpNature','',turnPage.arrDataCacheSet[0][17]); } catch(ex) { }; //��λ����
	try { mSwitch.addVar('RgtMoney','',turnPage.arrDataCacheSet[0][18]); } catch(ex) { }; //ע���ʱ�
	try { mSwitch.addVar('Asset','',turnPage.arrDataCacheSet[0][19]); } catch(ex) { }; //�ʲ��ܶ�
	try { mSwitch.addVar('NetProfitRate','',turnPage.arrDataCacheSet[0][20]); } catch(ex) { }; //���ʲ�������
	try { mSwitch.addVar('MainBussiness','',turnPage.arrDataCacheSet[0][21]); } catch(ex) { }; //��Ӫҵ��
	try { mSwitch.addVar('Corporation','',turnPage.arrDataCacheSet[0][22]); } catch(ex) { }; //����
	try { mSwitch.addVar('ComAera','',turnPage.arrDataCacheSet[0][23]); } catch(ex) { }; //�����ֲ�����
	try { mSwitch.addVar('Fax','',turnPage.arrDataCacheSet[0][24]); } catch(ex) { }; //��λ����
	try { mSwitch.addVar('Phone','',turnPage.arrDataCacheSet[0][25]); } catch(ex) { }; //��λ�绰
	try { mSwitch.addVar('GetFlag','',turnPage.arrDataCacheSet[0][26]); } catch(ex) { }; //���ʽ
	try { mSwitch.addVar('Satrap','',turnPage.arrDataCacheSet[0][27]); } catch(ex) { }; //������
	try { mSwitch.addVar('EMail','',turnPage.arrDataCacheSet[0][28]); } catch(ex) { }; //��˾e_mail
	try { mSwitch.addVar('FoundDate','',turnPage.arrDataCacheSet[0][29]); } catch(ex) { }; //��������
	try { mSwitch.addVar('GrpGroupNo','',turnPage.arrDataCacheSet[0][30]); } catch(ex) { }; //�ͻ������
	try { mSwitch.addVar('BankCode','',turnPage.arrDataCacheSet[0][31]); } catch(ex) { }; //���б���
	try { mSwitch.addVar('BankAccNo','',turnPage.arrDataCacheSet[0][32]); } catch(ex) { }; //�����ʺ�
	try { mSwitch.addVar('AccName','',turnPage.arrDataCacheSet[0][33]); } catch(ex) { }; //�����ʻ���
	try { mSwitch.addVar('DisputedFlag','',turnPage.arrDataCacheSet[0][34]); } catch(ex) { }; //��ͬ���鴦��ʽ
	try { mSwitch.addVar('OutPayFlag','',turnPage.arrDataCacheSet[0][35]); } catch(ex) { }; //�罻����ʽ
	try { mSwitch.addVar('GetPolMode','',turnPage.arrDataCacheSet[0][36]); } catch(ex) { }; //�����ʹ﷽ʽ
	try { mSwitch.addVar('Lang','',turnPage.arrDataCacheSet[0][37]); } catch(ex) { }; //���ֱ��
	try { mSwitch.addVar('Currency','',turnPage.arrDataCacheSet[0][38]); } catch(ex) { }; //�ұ�
	try { mSwitch.addVar('LostTimes','',turnPage.arrDataCacheSet[0][39]); } catch(ex) { }; //��ʧ��������
	try { mSwitch.addVar('PrintCount','',turnPage.arrDataCacheSet[0][40]); } catch(ex) { }; //������ӡ����
	try { mSwitch.addVar('RegetDate','',turnPage.arrDataCacheSet[0][41]); } catch(ex) { }; //���һ�δ�������
	try { mSwitch.addVar('LastEdorDate','',turnPage.arrDataCacheSet[0][42]); } catch(ex) { }; //���һ�α�ȫ����
	try { mSwitch.addVar('LastGetDate','',turnPage.arrDataCacheSet[0][43]); } catch(ex) { }; //���һ�θ�������
	try { mSwitch.addVar('LastLoanDate','',turnPage.arrDataCacheSet[0][44]); } catch(ex) { }; //���һ�ν������
	try { mSwitch.addVar('SpecFlag','',turnPage.arrDataCacheSet[0][45]); } catch(ex) { }; //��������ҵ���־
	try { mSwitch.addVar('GrpSpec','',turnPage.arrDataCacheSet[0][46]); } catch(ex) { }; //������Լ
	try { mSwitch.addVar('PayMode','',turnPage.arrDataCacheSet[0][47]); } catch(ex) { }; //���ѷ�ʽ
	try { mSwitch.addVar('SignCom','',turnPage.arrDataCacheSet[0][48]); } catch(ex) { }; //ǩ������
	try { mSwitch.addVar('SignDate','',turnPage.arrDataCacheSet[0][49]); } catch(ex) { }; //ǩ������
	try { mSwitch.addVar('SignTime','',turnPage.arrDataCacheSet[0][50]); } catch(ex) { }; //ǩ��ʱ��
//	try { mSwitch.addVar('CValiDate','',turnPage.arrDataCacheSet[0][51]); } catch(ex) { }; //������Ч����
	try { mSwitch.addVar('PayIntv','',turnPage.arrDataCacheSet[0][52]); } catch(ex) { }; //���Ѽ��
	try { mSwitch.addVar('ManageFeeRate','',turnPage.arrDataCacheSet[0][53]); } catch(ex) { }; //����ѱ���
	try { mSwitch.addVar('ExpPeoples','',turnPage.arrDataCacheSet[0][54]); } catch(ex) { }; //Ԥ������
	try { mSwitch.addVar('ExpPremium','',turnPage.arrDataCacheSet[0][55]); } catch(ex) { }; //Ԥ�Ʊ���
	try { mSwitch.addVar('ExpAmnt','',turnPage.arrDataCacheSet[0][56]); } catch(ex) { }; //Ԥ�Ʊ���
	try { mSwitch.addVar('Peoples','',turnPage.arrDataCacheSet[0][57]); } catch(ex) { }; //������
	try { mSwitch.addVar('Mult','',turnPage.arrDataCacheSet[0][58]); } catch(ex) { }; //�ܷ���
	try { mSwitch.addVar('Prem','',turnPage.arrDataCacheSet[0][59]); } catch(ex) { }; //�ܱ���
	try { mSwitch.addVar('Amnt','',turnPage.arrDataCacheSet[0][60]); } catch(ex) { }; //�ܱ���
	try { mSwitch.addVar('SumPrem','',turnPage.arrDataCacheSet[0][61]); } catch(ex) { }; //���ۼƱ���
	try { mSwitch.addVar('SumPay','',turnPage.arrDataCacheSet[0][62]); } catch(ex) { }; //���ۼƽ���
	try { mSwitch.addVar('Dif','',turnPage.arrDataCacheSet[0][63]); } catch(ex) { }; //���
	try { mSwitch.addVar('Remark','',turnPage.arrDataCacheSet[0][64]); } catch(ex) { }; //��ע
	try { mSwitch.addVar('StandbyFlag1','',turnPage.arrDataCacheSet[0][65]); } catch(ex) { }; //���������ֶ�1
	try { mSwitch.addVar('StandbyFlag2','',turnPage.arrDataCacheSet[0][66]); } catch(ex) { }; //���������ֶ�2
	try { mSwitch.addVar('StandbyFlag3','',turnPage.arrDataCacheSet[0][67]); } catch(ex) { }; //���������ֶ�3
	try { mSwitch.addVar('InputOperator','',turnPage.arrDataCacheSet[0][68]); } catch(ex) { }; //¼����
	try { mSwitch.addVar('InputDate','',turnPage.arrDataCacheSet[0][69]); } catch(ex) { }; //¼���������
	try { mSwitch.addVar('InputTime','',turnPage.arrDataCacheSet[0][70]); } catch(ex) { }; //¼�����ʱ��
	try { mSwitch.addVar('ApproveFlag','',turnPage.arrDataCacheSet[0][71]); } catch(ex) { }; //����״̬
	try { mSwitch.addVar('ApproveCode','',turnPage.arrDataCacheSet[0][72]); } catch(ex) { }; //�����˱���
	try { mSwitch.addVar('ApproveDate','',turnPage.arrDataCacheSet[0][73]); } catch(ex) { }; //��������
	try { mSwitch.addVar('ApproveTime','',turnPage.arrDataCacheSet[0][74]); } catch(ex) { }; //����ʱ��
	try { mSwitch.addVar('UWOperator','',turnPage.arrDataCacheSet[0][75]); } catch(ex) { }; //�˱���
	try { mSwitch.addVar('UWFlag','',turnPage.arrDataCacheSet[0][76]); } catch(ex) { }; //�˱�״̬
	try { mSwitch.addVar('UWDate','',turnPage.arrDataCacheSet[0][77]); } catch(ex) { }; //�˱��������
	try { mSwitch.addVar('UWTime','',turnPage.arrDataCacheSet[0][78]); } catch(ex) { }; //�˱����ʱ��
	try { mSwitch.addVar('AppFlag','',turnPage.arrDataCacheSet[0][79]); } catch(ex) { }; //Ͷ����/������־
	try { mSwitch.addVar('PolApplyDate','',turnPage.arrDataCacheSet[0][80]); } catch(ex) { }; //Ͷ������������
	try { mSwitch.addVar('CustomGetPolDate','',turnPage.arrDataCacheSet[0][81]); } catch(ex) { }; //������ִ�ͻ�ǩ������
	try { mSwitch.addVar('GetPolDate','',turnPage.arrDataCacheSet[0][82]); } catch(ex) { }; //�����ʹ�����
	try { mSwitch.addVar('GetPolTime','',turnPage.arrDataCacheSet[0][83]); } catch(ex) { }; //�����ʹ�ʱ��
	try { mSwitch.addVar('State','',turnPage.arrDataCacheSet[0][84]); } catch(ex) { }; //״̬
	try { mSwitch.addVar('Operator','',turnPage.arrDataCacheSet[0][85]); } catch(ex) { }; //����Ա
	try { mSwitch.addVar('MakeDate','',turnPage.arrDataCacheSet[0][86]); } catch(ex) { }; //�������
	try { mSwitch.addVar('MakeTime','',turnPage.arrDataCacheSet[0][87]); } catch(ex) { }; //���ʱ��
	try { mSwitch.addVar('ModifyDate','',turnPage.arrDataCacheSet[0][88]); } catch(ex) { }; //���һ���޸�����
	try { mSwitch.addVar('ModifyTime','',turnPage.arrDataCacheSet[0][89]); } catch(ex) { }; //���һ���޸�ʱ��
 
	//����Ͷ������Ϣ

	//strsql = "select * from LCGrpAppnt where GrpContNo = '" + GrpContNo + "'";
	
	var mySql005=new SqlClass();
		mySql005.setResourceName("bq.GrpEdorTypeNRInputSql");//ָ��ʹ�õ�properties�ļ���
		mySql005.setSqlId("DSHomeContSql005");//ָ��ʹ�õ�Sql��id
		mySql005.addSubPara(GrpContNo);//ָ������Ĳ���

	strsql=mySql005.getString();
	
	turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);  
	if (turnPage.strQueryResult == "")
	{
		//alert(1);
		return;
	}
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

	try { mSwitch.addVar('GrpContNo','',turnPage.arrDataCacheSet[0][0]); } catch(ex) { }; //�����ͬ����
	try { mSwitch.addVar('CustomerNo','',turnPage.arrDataCacheSet[0][1]); } catch(ex) { }; //�ͻ�����
	try { mSwitch.addVar('PrtNo','',turnPage.arrDataCacheSet[0][2]); } catch(ex) { }; //ӡˢ����
	try { mSwitch.addVar('AddressNo','',turnPage.arrDataCacheSet[0][3]); } catch(ex) { }; //�ͻ���ַ����
	try { mSwitch.addVar('AppntGrade','',turnPage.arrDataCacheSet[0][4]); } catch(ex) { }; //Ͷ���˼���
	try { mSwitch.addVar('Name','',turnPage.arrDataCacheSet[0][5]); } catch(ex) { }; //��λ����
	try { mSwitch.addVar('PostalAddress','',turnPage.arrDataCacheSet[0][6]); } catch(ex) { }; //ͨѶ��ַ
	try { mSwitch.addVar('ZipCode','',turnPage.arrDataCacheSet[0][7]); } catch(ex) { }; //��λ�ʱ�
	try { mSwitch.addVar('Phone','',turnPage.arrDataCacheSet[0][8]); } catch(ex) { }; //��λ�绰
	try { mSwitch.addVar('Password','',turnPage.arrDataCacheSet[0][9]); } catch(ex) { }; //����
	try { mSwitch.addVar('State','',turnPage.arrDataCacheSet[0][10]); } catch(ex) { }; //״̬
	try { mSwitch.addVar('AppntType','',turnPage.arrDataCacheSet[0][11]); } catch(ex) { }; //Ͷ��������
	try { mSwitch.addVar('RelationToInsured','',turnPage.arrDataCacheSet[0][12]); } catch(ex) { }; //��������Ͷ���˹�ϵ
	try { mSwitch.addVar('Operator','',turnPage.arrDataCacheSet[0][13]); } catch(ex) { }; //����Ա
	try { mSwitch.addVar('MakeDate','',turnPage.arrDataCacheSet[0][14]); } catch(ex) { }; //�������
	try { mSwitch.addVar('MakeTime','',turnPage.arrDataCacheSet[0][15]); } catch(ex) { }; //���ʱ��
	try { mSwitch.addVar('ModifyDate','',turnPage.arrDataCacheSet[0][16]); } catch(ex) { }; //���һ���޸�����
	try { mSwitch.addVar('ModifyTime','',turnPage.arrDataCacheSet[0][17]); } catch(ex) { }; //���һ���޸�ʱ��

	//strsql = "select EdorValiDate from LPGrpEdorMain where EdorNo = '" + document.all("EdorNo").value + "' and GrpContNo = '" + GrpContNo + "'";
	
	var mySql006=new SqlClass();
		mySql006.setResourceName("bq.GrpEdorTypeNRInputSql");//ָ��ʹ�õ�properties�ļ���
		mySql006.setSqlId("DSHomeContSql006");//ָ��ʹ�õ�Sql��id
		mySql006.addSubPara(document.all("EdorNo").value);//ָ������Ĳ���
		mySql006.addSubPara(GrpContNo);//ָ������Ĳ���

	strsql=mySql006.getString();
	
	turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);  
	if (turnPage.strQueryResult == "")
	{
		//alert(1);
		return;
	}
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	try { mSwitch.addVar('CValiDate','',turnPage.arrDataCacheSet[0][0]); } catch(ex) { }; //������Ч����

}               

function onInsuredGridSelected()
{
	var tSel = LPInsuredGrid.getSelNo();	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
		try
		{
			var tRow = LPInsuredGrid.getSelNo() - 1;	
			document.all('ContNo').value = LPInsuredGrid.getRowColData(tRow, 1);
			document.all('InsuredNo').value = LPInsuredGrid.getRowColData(tRow, 2);
	    //alert(document.all('ContNo').value);
		}
		catch(ex)
		{
		}
		
	}	
}

//2005-5-10����,���뱻�����嵥����--Start--
function getin()
{
	if ( fm.GrpContNo.value =="")
	{
		alert("���Ȳ�ѯ������Ϣ");
		return ;
	}
   //var strUrl = "../app/DiskApplyInput3.jsp?grpcontno="+ fm.GrpContNo.value;
   var strUrl = "../appgrp/DiskApplyInputMainBq.jsp?grpcontno="+ fm.GrpContNo.value+"&EdorValiDate="+fm.EdorValiDate.value+"&EdorTypeCal="+fm.EdorTypeCal.value+"&EdorType="+fm.EdorType.value;
   showInfo=window.open(strUrl,"","width=400, height=150, top=150, left=250, toolbar=no, menubar=no, scrollbars=no,resizable=no,location=no, status=no");
}

//���أ��رյ�ǰ����
function returnParent()
{
	top.opener.getEdorItemGrid();
	top.close();
}

function QueryEdorInfo()
{
	var tEdortype=document.all('EdorType').value
	if(tEdortype!=null || tEdortype !='')
	{
		//var strSQL="select distinct edorcode, edorname from lmedoritem where edorcode = '" + tEdortype + "'";
		
		var mySql007=new SqlClass();
			mySql007.setResourceName("bq.GrpEdorTypeNRInputSql");//ָ��ʹ�õ�properties�ļ���
			mySql007.setSqlId("DSHomeContSql007");//ָ��ʹ�õ�Sql��id
			mySql007.addSubPara(tEdortype);//ָ������Ĳ���

		var strSQL=mySql007.getString();
    }
    else
	{
		alert('δ��ѯ����ȫ������Ŀ��Ϣ��');
	}
	
	var arrSelected = new Array();	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
    //try {document.all('EdorType').value = arrSelected[0][0];} catch(ex) { }; 
    try {document.all('EdorTypeName').value = arrSelected[0][1];} catch(ex) { }; 
}

function MoneyDetail()
{
  window.open("./GetLJSGetEndorse.jsp");  
}

function doEY()
{	
	divInsurAcc.style.display="none";
	//var strSQL="select dif from lcgrpcont where grpcontno = '" + document.all('GrpContNo').value + "'";
	
	var mySql008=new SqlClass();
		mySql008.setResourceName("bq.GrpEdorTypeNRInputSql");//ָ��ʹ�õ�properties�ļ���
		mySql008.setSqlId("DSHomeContSql008");//ָ��ʹ�õ�Sql��id
		mySql008.addSubPara(document.all('GrpContNo').value);//ָ������Ĳ���

	var strSQL=mySql008.getString();
	
	var arrResult=easyExecSql(strSQL);
	if(arrResult!=null && arrResult[0][0]>0)
  	{
  		initEYGrid();
	    //divEY.style.display="";
	    EYGrid.setRowColData(0, 1, document.all('GrpContNo').value);
	    EYGrid.setRowColData(0, 2, arrResult[0][0]);
	  }else{
	  alert("���ʽ��Ϊ0!����������ɷѷ�ʽ.");
	  return;
	}
	//var strSQL="Select sum(GetMoney) From LJSGetEndorse where EndorsementNo='" + document.all('EdorNo').value + "' and FeeOperationType='" + document.all('EdorType').value + "' and FeeFinaType in ('BF','GL')";
	
	var mySql009=new SqlClass();
		mySql009.setResourceName("bq.GrpEdorTypeNRInputSql");//ָ��ʹ�õ�properties�ļ���
		mySql009.setSqlId("DSHomeContSql009");//ָ��ʹ�õ�Sql��id
		mySql009.addSubPara(document.all('EdorNo').value);//ָ������Ĳ���
		mySql009.addSubPara(document.all('EdorType').value);//ָ������Ĳ���
	var strSQL=mySql009.getString();
	
	var arrResult=easyExecSql(strSQL);
	if(arrResult!=null && arrResult[0][0]>0)
  	{
  		divEY.style.display="";
  		EYGrid.setRowColData(0, 3, arrResult[0][0]);
	  }else{
	  //EYGrid.setRowColData(0, 3, 0);
	  divEY.style.display="none";
	  alert("���α�ȫʹ�ý��Ϊ0������ʹ��!");	  
	  return;
	}  
	  
}

function doInsurAcc()
{
	divEY.style.display="none";
	//var strSQL="select sum(a.InsuAccBala) from LCInsureAcc a where a.grpcontno='" + document.all('GrpContNo').value + "' and a.contno=(select b.contno from lccont b where b.grpcontno='" + document.all('GrpContNo').value + "' and b.PolType='2')";
	
	var mySql010=new SqlClass();
		mySql010.setResourceName("bq.GrpEdorTypeNRInputSql");//ָ��ʹ�õ�properties�ļ���
		mySql010.setSqlId("DSHomeContSql010");//ָ��ʹ�õ�Sql��id
		mySql010.addSubPara(document.all('GrpContNo').value);//ָ������Ĳ���
		mySql010.addSubPara(document.all('GrpContNo').value);//ָ������Ĳ���
	var strSQL=mySql010.getString();
	
	var arrResult=easyExecSql(strSQL);
	if(arrResult!=null && arrResult[0][0]>0)
	{
  		initInsurAccGrid();
	    
	    InsurAccGrid.setRowColData(0, 1, document.all('GrpContNo').value);
	    InsurAccGrid.setRowColData(0, 2, arrResult[0][0]);
	}else{
	  alert("�����ʻ����Ϊ0!����������ɷѷ�ʽ.");
	  return;
	}
	//var strSQL="Select sum(GetMoney) From LJSGetEndorse where EndorsementNo='" + document.all('EdorNo').value + "' and FeeOperationType='" + document.all('EdorType').value + "' and FeeFinaType in ('BF','GL')";
	
	var mySql011=new SqlClass();
		mySql011.setResourceName("bq.GrpEdorTypeNRInputSql");//ָ��ʹ�õ�properties�ļ���
		mySql011.setSqlId("DSHomeContSql011");//ָ��ʹ�õ�Sql��id
		mySql011.addSubPara(document.all('EdorNo').value);//ָ������Ĳ���
		mySql011.addSubPara(document.all('EdorType').value);//ָ������Ĳ���
	var strSQL=mySql011.getString();
	
	var arrResult=easyExecSql(strSQL);
	if(arrResult!=null&& arrResult[0][0]>0)
  	{
  		divInsurAcc.style.display="";
  		InsurAccGrid.setRowColData(0, 3, arrResult[0][0]);
	  }else{
	  //InsurAccGrid.setRowColData(0, 3, 0);
	  divInsurAcc.style.display="none";
	  alert("���α�ȫʹ�ý��Ϊ0������ʹ��!");	  
	  return;
	}    
}

function doEYSave()
{
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	document.all('fmtransact').value = "INSERT||EY";
	fm.submit();
}

function doInsurAccSave()
{
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	document.all('fmtransact').value = "INSERT||InsurAcc";
	fm.submit();
}

function doreturn()
{
	divEY.style.display="none";
}
function doreturn1()
{
	divInsurAcc.style.display="none";
}
function doreturn2()
{
	divBlog.style.display="none";
}

function edorDelInsured()
{
	//alert(fm.ContNo.value);
	if(fm.ContNo.value=="")
	{
		alert("����ѡ����Ҫɾ���ı�����");
		return false;
	}
}

function getinblog()
{
	//var strSQL="select batchno,contid,insuredname,errorinfo,makedate,maketime from LCGrpImportLog where grpcontno='"+ fm.GrpContNo.value +"' order by maketime";
	//var arrResult=easyExecSql(strSQL);	
  initBlogGrid();
  divBlog.style.display="";
	//turnPage2.queryModal(strSQL, BlogGrid); 
}
function BlogQuery()
{
	var strSQL="";
	if(document.all('BatchNo').value==""||document.all('BatchNo').value==null)
	{
		alert("�����뵼����ļ���!");
		return false;
		//strSQL="select batchno,contid,insuredname,errorinfo,makedate,maketime from LCGrpImportLog where grpcontno='"+ fm.GrpContNo.value +"' and ErrorState='1' order by maketime";
		
	}
	else
	{	
		//strSQL="select batchno,contid,insuredname,errorinfo,makedate,maketime from LCGrpImportLog where grpcontno='"+ fm.GrpContNo.value +"' and BatchNo='"+ document.all('BatchNo').value +"' and ErrorState='1' order by maketime";
	
		var mySql012=new SqlClass();
			mySql012.setResourceName("bq.GrpEdorTypeNRInputSql");//ָ��ʹ�õ�properties�ļ���
			mySql012.setSqlId("DSHomeContSql012");//ָ��ʹ�õ�Sql��id
			mySql012.addSubPara(fm.GrpContNo.value);//ָ������Ĳ���
			mySql012.addSubPara(document.all('BatchNo').value);//ָ������Ĳ���
		strSQL=mySql012.getString();
	
	}

	turnPage2.queryModal(strSQL, BlogGrid); 
}

//��ѯ��ť
function queryClick()
{
/**	var strSql = "select ContNo, InsuredNo, Name, Sex, Birthday, IDType, IDNo from LCInsured where GrpContNo='" 
		   + fm.GrpContNo.value + "'" 
		   + "and Exists (select * from LCcont where appflag='1' and ContNo = LCInsured.ContNo)"
		   + "and Exists (select * from LCPol where appflag='1' and ContNo = LCInsured.ContNo and InsuredNo = LCInsured.InsuredNo)"
		   + getWherePart('ContNo', 'ContNo2')
		   + getWherePart('InsuredNo', 'CustomerNo2')
		   + getWherePart('Name', 'Name2', 'like', '0');
*/	
		var mySql013=new SqlClass();
			mySql013.setResourceName("bq.GrpEdorTypeNRInputSql");//ָ��ʹ�õ�properties�ļ���
			mySql013.setSqlId("DSHomeContSql013");//ָ��ʹ�õ�Sql��id
			mySql013.addSubPara(fm.GrpContNo.value);//ָ������Ĳ���
			
		var strSql=mySql013.getString()
			+ getWherePart('ContNo', 'ContNo2')
			+ getWherePart('InsuredNo', 'CustomerNo2')
			+ getWherePart('Name', 'Name2', 'like', '0');
	
	
	turnPage1.queryModal(strSql, LPInsuredGrid);	 
}