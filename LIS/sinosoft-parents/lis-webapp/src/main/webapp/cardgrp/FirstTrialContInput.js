//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var arrResult;
var mDebug = "0";
var mOperate = 0;
var mAction = "";
var mSwitch = parent.VD.gVSwitch;
var mShowCustomerDetail = "GROUPPOL";
var turnPage = new turnPageClass();
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var sqlresourcename = "cardgrp.FirstTrialContInputSql";
this.window.onfocus=myonfocus;
var flag = '0';
/*********************************************************************
 *  �������Ͷ�������ύ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function submitForm()
{
	if(fm.PrtNo.value=='')
	{ 
		 alert("������ӡˢ����Ϣ!!");
		 return;
	}
	if(fm.ContNo.value!="")
	{ 
		 alert("��ͬ��Ϣ�ѱ���!!");
		 return;
	}
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
	fm.stype.value = "1";
	fm.action="FirstTrialContSave.jsp"
	fm.submit();  
}

/*********************************************************************
 *  �������Ͷ�������ύ��Ĳ���,���������ݷ��غ�ִ�еĲ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
	initImpartGrid();
	initDetail();
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
		showDiv(inputButton, "false");	
	}
	mAction = ""; 
}

/*********************************************************************
 *  "����"��ť��Ӧ����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function resetForm()
{
	try
	{
		initForm();
		fm.all('PrtNo').value = prtNo;
	}
	catch( re )
	{
		alert("��GroupPolInput.js-->resetForm�����з����쳣:��ʼ���������!");
	}
} 

/*********************************************************************
 *  "ȡ��"��ť��Ӧ����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function cancelForm()
{
	showDiv(operateButton,"true"); 
	showDiv(inputButton,"false"); 
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
/*��ʱ���Σ���ȫ������������	fm.all('RiskCode').value = "";
	
//��ȫ���ûᴫ2����������Ĭ��Ϊ0������ֵ�ڱ������е�appflag�ֶ�
  if (BQFlag=="2") {
    var strSql = "select grppolno, grpno from lcgrppol where prtno='" + prtNo + "' and riskcode in (select riskcode from lmriskapp where subriskflag='M')";
    var arrResult = easyExecSql(strSql);
    //alert(arrResult);
    mOperate = 1;
    afterQuery(arrResult);
    //strSql = "select GrpNo,GrpName,GrpAddress,Satrap from LDGrp where GrpNo='" + arrResult[0][1] + "'";
    //arrResult = easyExecSql(strSql);
    //mOperate = 2;
    //afterQuery(arrResult);
    
    fm.all('RiskCode').value = BQRiskCode;
    fm.all('RiskCode').className = "readonly";
    fm.all('RiskCode').readOnly = true;
    fm.all('RiskCode').ondblclick = "";
  }
	fm.all('ContNo').value = "";
	fm.all('GrpProposalNo').value = "";*/
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
		cContNo = fm.all( 'ContNo' ).value;
		showInfo = window.open("./ContQueryMain.jsp?ContNo=" + cContNo,"",sFeatures);
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
		alert("֤�����ͺ�֤��������������");	
		return false;		
		}
	
	var tGrpProposalNo = "";
	tGrpProposalNo = fm.all( 'ProposalContNo' ).value;
	if( tGrpProposalNo == null || tGrpProposalNo == "" )
		alert( "������Ͷ������ѯ�������ٽ����޸�!" );
	else
	{
		var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
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
			mAction = "UPDATE";
			fm.all( 'fmAction' ).value = mAction;
			fm.submit(); //�ύ
		}
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
		tGrpProposalNo = fm.all( 'GrpContNo' ).value;
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
			fm.all( 'fmAction' ).value = mAction;
			fm.submit(); //�ύ
		}
	 }
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
 *  ����������������Ϣ����ťʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function intoInsured()
{
	//����������Ӧ�Ĵ���
	//alert("1");
	//alert(fm.ContNo.value);
	//alert(fm.FamilyType.value);
	var tAppntNo = fm.AppntNo.value;
	var tAppntName = fm.AppntName.value;
	fm.ContNo.value=fm.ProposalContNo.value;
	if( fm.ContNo.value == "" )
	{
		alert("��������¼���ͬ��Ϣ���ܽ��뱻��������Ϣ���֡�");
		return false
	}
    
	//�Ѽ�����Ϣ�����ڴ�
	mSwitch = parent.VD.gVSwitch;  //���ݴ�
	putCont();
	
	try { goToPic(2) } catch(e) {}
	
	try {
	  parent.fraInterface.window.location = "./ContInsuredInput.jsp?LoadFlag=" + LoadFlag + "&type=" + type+ "&MissionID=" + tMissionID+ "&SubMissionID=" + tSubMissionID+ "&AppntNo=" + tAppntNo+ "&AppntName=" + tAppntName+"&checktype=1"+"&scantype="+scantype ;
	}
	catch (e) {
		parent.fraInterface.window.location = "./ContInsuredInput.jsp?LoadFlag=1&type=" + type+ "&MissionID=" +tMissionID+ "&SubMissionID=" + tSubMissionID+ "&AppntNo=" + tAppntNo+ "&AppntName=" + tAppntName;
	}
}           


/*********************************************************************
 *  �Ѻ�ͬ������Ϣ¼�����ȷ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function inputConfirm(wFlag)
{	
	//strSql = "select missionid from lwmission where activityid = '0000001062' and missionprop1 ='"+fm.PrtNo.value+"'";
	var sqlid1="FirstTrialContInputSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(fm.PrtNo.value);
	
	arrResult=easyExecSql(mySql1.getString());
	if(arrResult!=null)
	{
		alert('�ѱ���,�����ٴα���!!');
		return;
	}
	//var strSql = "select missionid from lwmission where (activityid = '0000001108' or activityid = '0000001106') and missionid = '" + fm.MissionID.value +"'";
	
	var sqlid2="FirstTrialContInputSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(fm.MissionID.value);
	
	arrResult=easyExecSql(mySql2.getString());
	if(arrResult!=null)
	{
		alert('��δ��ӡ��֪ͨ��!���ӡ!!');
		return;
	}
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
	fm.stype.value = "0";
	fm.action = "./FirstTrialContSave.jsp";
  fm.submit(); //�ύ
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
try { mSwitch.addVar('AddressNo','',fm.AddressNo.value); } catch(ex) { };		
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
try { mSwitch.addVar('AppntBankAccNo','',fm.all('AppntBankAccNo').value); } catch(ex) { };    
try { mSwitch.addVar('AppntBankCode','',fm.all('AppntBankCode').value); } catch(ex) { };    
try { mSwitch.addVar('AppntAccName','',fm.all('AppntAccName').value); } catch(ex) { };      
   
   
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
 *  ��ѯ������ϸ��Ϣʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
 *  ����  ��  ��ѯ���صĶ�ά����
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterQuery( arrQueryResult ) {
  //alert("here:" + arrQueryResult + "\n" + mOperate);
	if( arrQueryResult != null ) {
		arrResult = arrQueryResult;

		if( flag == 1 )	{		// ��ѯͶ����	
			//arrResult = easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a,LCAddress b where 1=1 and a.CustomerNo=b.CustomerNo and a.CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0);
			
		var sqlid3="FirstTrialContInputSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(sqlresourcename);
		mySql3.setSqlId(sqlid3);
		mySql3.addSubPara(arrQueryResult[0][0]);
		arrResult = easyExecSql(mySql3.getString(), 1, 0);
			
			if (arrResult == null) {
			  alert("δ�鵽Ͷ������Ϣ");
			} else {
			    displayAppnt(arrResult[0]);
			}
		}
		if( flag == 2 )	{		// Ͷ������Ϣ
			//arrResult = easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a,LCAddress b where 1=1 and a.CustomerNo=b.CustomerNo and a.CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0);
			
			var sqlid4="FirstTrialContInputSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName(sqlresourcename);
		mySql4.setSqlId(sqlid4);
		mySql4.addSubPara(arrQueryResult[0][0]);
			arrResult = easyExecSql(mySql4.getString(), 1, 0);
		
			if (arrResult == null) {
			  alert("δ�鵽Ͷ������Ϣ");
			} else {
			  displayInuserd(arrResult[0]);
			}
		}
	}
	
	mOperate = 0;		// �ָ���̬
}
/*********************************************************************
 *  Ͷ������Ϣ��ʼ����������loadFlag��־��Ϊ��֧
 *  ����  ��  Ͷ����ӡˢ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function detailInit(PrtNo){
try{
  if(PrtNo==null)
    return;
  //arrResult=easyExecSql("select * from LCCont where PrtNo='"+PrtNo+"'",1,0);
  
  var sqlid5="FirstTrialContInputSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName(sqlresourcename);
		mySql5.setSqlId(sqlid5);
		mySql5.addSubPara(PrtNo);
  arrResult=easyExecSql(mySql5.getString(),1,0);
  if(arrResult==null){
    alert(δ�õ�Ͷ������Ϣ);
    return;
  }else{
    displayLCCont();       //��ʾͶ������ϸ����
  }
  //arrResult=easyExecSql("select * from LCAppnt where PrtNo='"+PrtNo+"'",1,0);
  
  var sqlid6="FirstTrialContInputSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName(sqlresourcename);
		mySql6.setSqlId(sqlid6);
		mySql6.addSubPara(PrtNo);
  arrResult=easyExecSql(mySql6.getString(),1,0);
  if(arrResult==null){
    alert("δ�õ�Ͷ������Ϣ");
    return;
  }else{
    displayContAppnt();       //��ʾͶ���˵���ϸ����
    //displayAccount();			//��ʾͷͶ�����ʻ���Ϣ
  }
  var tContNo = arrResult[0][1];  
  var tCustomerNo = arrResult[0][3];		// �õ�Ͷ���˿ͻ���
  var tAddressNo = arrResult[0][9]; 		// �õ�Ͷ���˵�ַ��
  getdetailaddress();
/*  
  arrResult=easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo='"+tCustomerNo+"'",1,0);
  if(arrResult==null){
    alert("δ�õ��û���Ϣ");
    return;
  }else{
    displayAppnt();       //��ʾͶ������ϸ����
    emptyUndefined();
  } 
*/ 
 /*   
  arrResult=easyExecSql("select * from LCAddress where AddressNo='"+tAddressNo+"' and CustomerNo='"+tCustomerNo+"'",1,0);
  if(arrResult==null){
    alert(δ�õ�Ͷ���˵�ַ��Ϣ);
    return;
  }else{
    displayAddress();       //��ʾͶ���˵�ַ��ϸ����
  }*/
  fm.AddressNo.value=tAddressNo;
  getdetailaddress();//��ʾͶ���˵�ַ��ϸ����
  //var strSQL1="select ImpartVer,ImpartCode,ImpartContent,ImpartParamModle from LCCustomerImpart where CustomerNo='"+tCustomerNo+"' and ContNo='"+tContNo+"'";
 
  var sqlid7="FirstTrialContInputSql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName(sqlresourcename);
		mySql7.setSqlId(sqlid7);
		mySql7.addSubPara(tCustomerNo);
        mySql7.addSubPara(tContNo);
  turnPage.strQueryResult = easyQueryVer3(mySql7.getString(), 1, 0, 1);
  //�ж��Ƿ��ѯ�ɹ�,�ɹ�����ʾ��֪��Ϣ
  if (turnPage.strQueryResult) {
    //��ѯ�ɹ������ַ��������ض�ά����
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    //���ó�ʼ������MULTILINE����
    turnPage.pageDisplayGrid = ImpartGrid;    
    //����SQL���
    turnPage.strQuerySql = strSQL1; 
    //���ò�ѯ��ʼλ��
    turnPage.pageIndex = 0;  
    //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
    arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
    //����MULTILINE������ʾ��ѯ���
    displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  }
  else{
    initImpartGrid();
  }
  if(loadFlag=="5"||loadFlag=="25"){
    showCodeName();
  }
}catch(ex){}
 
}

/*********************************************************************
 *  �Ѳ�ѯ���صĿͻ�������ʾ��Ͷ���˲���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayAccount()
{
	try{fm.all('AppntBankAccNo').value= arrResult[0][24]; }catch(ex){}; 
	try{fm.all('AppntBankCode').value= arrResult[0][23]; }catch(ex){}; 
	try{fm.all('AppntAccName').value= arrResult[0][25]; }catch(ex){}; 
	
}
/*********************************************************************
 *  �Ѳ�ѯ���صĿͻ�������ʾ��Ͷ���˲���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayCustomer()
{
	try{fm.all('AppntNationality').value= arrResult[0][8]; }catch(ex){}; 
}
/*********************************************************************
 *  �Ѳ�ѯ���صĿͻ���ַ������ʾ��Ͷ���˲���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayAddress()
{
try{fm.all('AddressNo').value= arrResult[0][0];}catch(ex){}; 
try{fm.all('AppntPostalAddress').value= arrResult[0][1];}catch(ex){}; 
try{fm.all('AppntZipCode').value= arrResult[0][2];}catch(ex){}; 
try{fm.all('AppntPhone').value= arrResult[0][3];}catch(ex){}; 
try{fm.all('AppntMobile').value= arrResult[0][4];}catch(ex){}; 
try{fm.all('AppntEMail').value= arrResult[0][5];}catch(ex){}; 
//try{fm.all('GrpName').value= arrResult[0][6];}catch(ex){}; 
try{fm.all('AppntGrpPhone').value= arrResult[0][6];}catch(ex){}; 
try{fm.all('CompanyAddress').value= arrResult[0][7];}catch(ex){}; 
try{fm.all('AppntGrpZipCode').value= arrResult[0][8];}catch(ex){}; 
/*	
        try{fm.all('AppntPostalAddress').value= arrResult[0][2]; }catch(ex){}; 
	try{fm.all('AppntZipCode').value= arrResult[0][3]; }catch(ex){}; 
	try{fm.all('AppntPhone').value= arrResult[0][4]; }catch(ex){}; 
	try{fm.all('AppntMobile').value= arrResult[0][14]; }catch(ex){}; 
	try{fm.all('AppntEMail').value= arrResult[0][16]; }catch(ex){}; 
	//try{fm.all('AppntGrpName').value= arrResult[0][2]; }catch(ex){}; 
	try{fm.all('AppntGrpPhone').value= arrResult[0][12]; }catch(ex){}; 
	try{fm.all('CompanyAddress').value= arrResult[0][10]; }catch(ex){}; 
	try{fm.all('AppntGrpZipCode').value= arrResult[0][11]; }catch(ex){}; 
*/
}

/*********************************************************************
 *  �Ѳ�ѯ���صĺ�ͬ�б�����������ʾ��ҳ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayContAppnt()
{
try{fm.all('AppntGrpContNo').value= arrResult[0][0];}catch(ex){};             
try{fm.all('AppntContNo').value= arrResult[0][1];}catch(ex){};                 
try{fm.all('AppntPrtNo').value= arrResult[0][2];}catch(ex){};                  
try{fm.all('AppntNo').value= arrResult[0][3];}catch(ex){};                
try{fm.all('AppntGrade').value= arrResult[0][4];}catch(ex){};             
try{fm.all('AppntName').value= arrResult[0][5];}catch(ex){};              
try{fm.all('AppntSex').value= arrResult[0][6];}catch(ex){};               
try{fm.all('AppntBirthday').value= arrResult[0][7];}catch(ex){};          
try{fm.all('AppntType').value= arrResult[0][8];}catch(ex){};              
try{fm.all('AppntAddressNo').value= arrResult[0][9]; }catch(ex){};             
try{fm.all('AppntIDType').value= arrResult[0][10]; }catch(ex){};               
try{fm.all('AppntIDNo').value= arrResult[0][11]; }catch(ex){};                 
try{fm.all('AppntNativePlace').value= arrResult[0][12];}catch(ex){};           
try{fm.all('AppntNationality').value= arrResult[0][13];}catch(ex){};           
try{fm.all('AppntRgtAddress').value= arrResult[0][14];}catch(ex){};            
try{fm.all('AppntMarriage').value= arrResult[0][15];}catch(ex){};             
try{fm.all('AppntMarriageDate').value= arrResult[0][16];}catch(ex){};          
try{fm.all('AppntHealth').value= arrResult[0][17];}catch(ex){};                
try{fm.all('AppntStature').value= arrResult[0][18];}catch(ex){};               
try{fm.all('AppntAvoirdupois').value= arrResult[0][19];}catch(ex){};           
try{fm.all('AppntDegree').value= arrResult[0][20];}catch(ex){};                
try{fm.all('AppntCreditGrade').value= arrResult[0][21];}catch(ex){};           
try{fm.all('AppntBankCode').value= arrResult[0][22];}catch(ex){};              
try{fm.all('AppntBankAccNo').value= arrResult[0][23];}catch(ex){};             
try{fm.all('AppntAccName').value= arrResult[0][24]; }catch(ex){};            
try{fm.all('AppntJoinCompanyDate').value= arrResult[0][25];}catch(ex){};       
try{fm.all('AppntStartWorkDate').value= arrResult[0][26];}catch(ex){};         
try{fm.all('AppntPosition').value= arrResult[0][27];}catch(ex){};              
try{fm.all('AppntSalary').value= arrResult[0][28]; }catch(ex){};               
try{fm.all('AppntOccupationType').value= arrResult[0][29];}catch(ex){};        
try{fm.all('AppntOccupationCode').value= arrResult[0][30];}catch(ex){};        
try{fm.all('AppntWorkType').value= arrResult[0][31]; }catch(ex){};             
try{fm.all('AppntPluralityType').value= arrResult[0][32];}catch(ex){};         
try{fm.all('AppntSmokeFlag').value= arrResult[0][33];}catch(ex){};             
try{fm.all('AppntOperator').value= arrResult[0][34];}catch(ex){};              
try{fm.all('AppntManageCom').value= arrResult[0][35];}catch(ex){};             
try{fm.all('AppntMakeDate').value= arrResult[0][36];}catch(ex){};              
try{fm.all('AppntMakeTime').value= arrResult[0][37]; }catch(ex){};             
try{fm.all('AppntModifyDate').value= arrResult[0][38];}catch(ex){};            
try{fm.all('AppntModifyTime').value= arrResult[0][39];}catch(ex){};       
}
/*********************************************************************
 *  �Ѳ�ѯ���ص�Ͷ����������ʾ��Ͷ���˲���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayAppnt()
{
try{fm.all('AppntNo').value= arrResult[0][0]; }catch(ex){};              
try{fm.all('AppntName').value= arrResult[0][1]; }catch(ex){};            
try{fm.all('AppntSex').value= arrResult[0][2]; }catch(ex){};             
try{fm.all('AppntBirthday').value= arrResult[0][3]; }catch(ex){};        
try{fm.all('AppntIDType').value= arrResult[0][4]; }catch(ex){};          
try{fm.all('AppntIDNo').value= arrResult[0][5]; }catch(ex){};            
try{fm.all('AppntPassword').value= arrResult[0][6]; }catch(ex){};        
try{fm.all('AppntNativePlace').value= arrResult[0][7]; }catch(ex){};     
try{fm.all('AppntNationality').value= arrResult[0][8]; }catch(ex){};     
try{fm.all('AppntRgtAddress').value= arrResult[0][9]; }catch(ex){};      
try{fm.all('AppntMarriage').value= arrResult[0][10];}catch(ex){};        
try{fm.all('AppntMarriageDate').value= arrResult[0][11];}catch(ex){};    
try{fm.all('AppntHealth').value= arrResult[0][12];}catch(ex){};          
try{fm.all('AppntStature').value= arrResult[0][13];}catch(ex){};         
try{fm.all('AppntAvoirdupois').value= arrResult[0][14];}catch(ex){};     
try{fm.all('AppntDegree').value= arrResult[0][15];}catch(ex){};          
try{fm.all('AppntCreditGrade').value= arrResult[0][16];}catch(ex){};     
try{fm.all('AppntOthIDType').value= arrResult[0][17];}catch(ex){};       
try{fm.all('AppntOthIDNo').value= arrResult[0][18];}catch(ex){};         
try{fm.all('AppntICNo').value= arrResult[0][19];}catch(ex){};            
try{fm.all('AppntGrpNo').value= arrResult[0][20];}catch(ex){};           
try{fm.all('AppntJoinCompanyDate').value= arrResult[0][21];}catch(ex){}; 
try{fm.all('AppntStartWorkDate').value= arrResult[0][22];}catch(ex){};   
try{fm.all('AppntPosition').value= arrResult[0][23];}catch(ex){};        
try{fm.all('AppntSalary').value= arrResult[0][24];}catch(ex){};          
try{fm.all('AppntOccupationType').value= arrResult[0][25];}catch(ex){};  
try{fm.all('AppntOccupationCode').value= arrResult[0][26];}catch(ex){};  
try{fm.all('AppntWorkType').value= arrResult[0][27];}catch(ex){};        
try{fm.all('AppntPluralityType').value= arrResult[0][28];}catch(ex){};   
try{fm.all('AppntDeathDate').value= arrResult[0][29];}catch(ex){};       
try{fm.all('AppntSmokeFlag').value= arrResult[0][30];}catch(ex){};       
try{fm.all('AppntBlacklistFlag').value= arrResult[0][31];}catch(ex){};   
try{fm.all('AppntProterty').value= arrResult[0][32];}catch(ex){};        
try{fm.all('AppntRemark').value= arrResult[0][33];}catch(ex){};          
try{fm.all('AppntState').value= arrResult[0][34];}catch(ex){};
try{fm.all('VIPValue').value= arrResult[0][35];}catch(ex){};           
try{fm.all('AppntOperator').value= arrResult[0][36];}catch(ex){};        
try{fm.all('AppntMakeDate').value= arrResult[0][37];}catch(ex){};        
try{fm.all('AppntMakeTime').value= arrResult[0][38];}catch(ex){};      
try{fm.all('AppntModifyDate').value= arrResult[0][39];}catch(ex){};      
try{fm.all('AppntModifyTime').value= arrResult[0][40];}catch(ex){};      
try{fm.all('AppntGrpName').value= arrResult[0][41];}catch(ex){}; 

try{fm.all('AppntPostalAddress').value= "";}catch(ex){}; 
try{fm.all('AppntPostalAddress').value= "";}catch(ex){}; 
try{fm.all('AppntZipCode').value= "";}catch(ex){}; 
try{fm.all('AppntPhone').value= "";}catch(ex){}; 
try{fm.all('AppntFax').value= "";}catch(ex){}; 
try{fm.all('AppntMobile').value= "";}catch(ex){}; 
try{fm.all('AppntEMail').value= "";}catch(ex){}; 
//try{fm.all('AppntGrpName').value= "";}catch(ex){}; 
try{fm.all('AppntHomeAddress').value= "";}catch(ex){}; 
try{fm.all('AppntHomeZipCode').value= "";}catch(ex){}; 
try{fm.all('AppntHomePhone').value= "";}catch(ex){}; 
try{fm.all('AppntHomeFax').value= "";}catch(ex){}; 
try{fm.all('AppntGrpPhone').value= "";}catch(ex){}; 
try{fm.all('CompanyAddress').value= "";}catch(ex){}; 
try{fm.all('AppntGrpZipCode').value= "";}catch(ex){};  
try{fm.all('AppntGrpFax').value= "";}catch(ex){};  
}
/**
 *Ͷ������ϸ������ʾ
 */
function displayLCCont() {
    try { fm.all('GrpContNo').value = arrResult[0][0]; } catch(ex) { };                    
    try { fm.all('ContNo').value = arrResult[0][1]; } catch(ex) { };                       
    try { fm.all('ProposalContNo').value = arrResult[0][2]; } catch(ex) { };               
    try { fm.all('PrtNo').value = arrResult[0][3]; } catch(ex) { };                        
    try { fm.all('ContType').value = arrResult[0][4]; } catch(ex) { };                     
    try { fm.all('FamilyType').value = arrResult[0][5]; } catch(ex) { };                
    try { fm.all('FamilyID').value = arrResult[0][6]; } catch(ex) { };                     
    try { fm.all('PolType ').value = arrResult[0][7]; } catch(ex) { };                     
    try { fm.all('CardFlag').value = arrResult[0][8]; } catch(ex) { };                     
    try { fm.all('ManageCom').value = arrResult[0][9]; } catch(ex) { };                    
    try { fm.all('ExecuteCom ').value = arrResult[0][10]; } catch(ex) { };                 
    try { fm.all('AgentCom').value = arrResult[0][11]; } catch(ex) { };                    
    try { fm.all('AgentCode').value = arrResult[0][12]; } catch(ex) { };
    try { fm.all('AgentGroup').value = arrResult[0][13]; } catch(ex) { };                 
    try { fm.all('AgentCode1 ').value = arrResult[0][14]; } catch(ex) { };                 
    try { fm.all('AgentType').value = arrResult[0][15]; } catch(ex) { };                   
    try { fm.all('SaleChnl').value = arrResult[0][16]; } catch(ex) { };                    
    try { fm.all('Handler ').value = arrResult[0][17]; } catch(ex) { };                    
    try { fm.all('Password').value = arrResult[0][18]; } catch(ex) { };                    
    try { fm.all('AppntNo ').value = arrResult[0][19]; } catch(ex) { };                    
    try { fm.all('AppntName').value = arrResult[0][20]; } catch(ex) { };                   
    try { fm.all('AppntSex').value = arrResult[0][21]; } catch(ex) { };                    
    try { fm.all('AppntBirthday ').value = arrResult[0][22]; } catch(ex) { };              
    try { fm.all('AppntIDType').value = arrResult[0][23]; } catch(ex) { };                 
    try { fm.all('AppntIDNo').value = arrResult[0][24]; } catch(ex) { };                   
    try { fm.all('InsuredNo').value = arrResult[0][25]; } catch(ex) { };                   
    try { fm.all('InsuredName').value = arrResult[0][26]; } catch(ex) { };                 
    try { fm.all('InsuredSex ').value = arrResult[0][27]; } catch(ex) { };                 
    try { fm.all('InsuredBirthday').value = arrResult[0][28]; } catch(ex) { };             
    try { fm.all('InsuredIDType ').value = arrResult[0][29]; } catch(ex) { };              
    try { fm.all('InsuredIDNo').value = arrResult[0][30]; } catch(ex) { };                 
    try { fm.all('PayIntv ').value = arrResult[0][31]; } catch(ex) { };                    
    try { fm.all('PayMode ').value = arrResult[0][32]; } catch(ex) { };                    
    try { fm.all('PayLocation').value = arrResult[0][33]; } catch(ex) { };                 
    try { fm.all('DisputedFlag').value = arrResult[0][34]; } catch(ex) { };                
    try { fm.all('OutPayFlag ').value = arrResult[0][35]; } catch(ex) { };                 
    try { fm.all('GetPolMode ').value = arrResult[0][36]; } catch(ex) { };                 
    try { fm.all('SignCom ').value = arrResult[0][37]; } catch(ex) { };                    
    try { fm.all('SignDate').value = arrResult[0][38]; } catch(ex) { };                    
    try { fm.all('SignTime').value = arrResult[0][39]; } catch(ex) { };                    
    try { fm.all('ConsignNo').value = arrResult[0][40]; } catch(ex) { };                   
    try { fm.all('BankCode').value = arrResult[0][41]; } catch(ex) { };                    
    try { fm.all('BankAccNo').value = arrResult[0][42]; } catch(ex) { };                   
    try { fm.all('AccName ').value = arrResult[0][43]; } catch(ex) { };                    
    try { fm.all('PrintCount ').value = arrResult[0][44]; } catch(ex) { };                 
    try { fm.all('LostTimes').value = arrResult[0][45]; } catch(ex) { };                   
    try { fm.all('Lang ').value = arrResult[0][46]; } catch(ex) { };                       
    try { fm.all('Currency').value = arrResult[0][47]; } catch(ex) { };                    
    try { fm.all('Remark').value = arrResult[0][48]; } catch(ex) { };                      
    try { fm.all('Peoples ').value = arrResult[0][49]; } catch(ex) { };                    
    try { fm.all('Mult ').value = arrResult[0][50]; } catch(ex) { };                       
    try { fm.all('Prem ').value = arrResult[0][51]; } catch(ex) { };                       
    try { fm.all('Amnt ').value = arrResult[0][52]; } catch(ex) { };                       
    try { fm.all('SumPrem ').value = arrResult[0][53]; } catch(ex) { };                    
    try { fm.all('Dif').value = arrResult[0][54]; } catch(ex) { };                         
    try { fm.all('PaytoDate').value = arrResult[0][55]; } catch(ex) { };                   
    try { fm.all('FirstPayDate').value = arrResult[0][56]; } catch(ex) { };                
    try { fm.all('CValiDate').value = arrResult[0][57]; } catch(ex) { };                   
    try { fm.all('InputOperator ').value = arrResult[0][58]; } catch(ex) { };              
    try { fm.all('InputDate').value = arrResult[0][59]; } catch(ex) { };                   
    try { fm.all('InputTime').value = arrResult[0][60]; } catch(ex) { };                   
    try { fm.all('ApproveFlag').value = arrResult[0][61]; } catch(ex) { };                 
    try { fm.all('ApproveCode').value = arrResult[0][62]; } catch(ex) { };                 
    try { fm.all('ApproveDate').value = arrResult[0][63]; } catch(ex) { };                 
    try { fm.all('ApproveTime').value = arrResult[0][64]; } catch(ex) { };                 
    try { fm.all('UWFlag').value = arrResult[0][65]; } catch(ex) { };                      
    try { fm.all('UWOperator ').value = arrResult[0][66]; } catch(ex) { };                 
    try { fm.all('UWDate').value = arrResult[0][67]; } catch(ex) { };                      
    try { fm.all('UWTime').value = arrResult[0][68]; } catch(ex) { };                      
    try { fm.all('AppFlag ').value = arrResult[0][69]; } catch(ex) { };                    
    try { fm.all('PolApplyDate').value = arrResult[0][70]; } catch(ex) { };                
    try { fm.all('GetPolDate ').value = arrResult[0][71]; } catch(ex) { };                 
    try { fm.all('GetPolTime ').value = arrResult[0][72]; } catch(ex) { };                 
    try { fm.all('CustomGetPolDate ').value = arrResult[0][73]; } catch(ex) { };           
    try { fm.all('State').value = arrResult[0][74]; } catch(ex) { };                       
    try { fm.all('Operator').value = arrResult[0][75]; } catch(ex) { };                    
    try { fm.all('MakeDate').value = arrResult[0][76]; } catch(ex) { };                    
    try { fm.all('MakeTime').value = arrResult[0][77]; } catch(ex) { };                    
    try { fm.all('ModifyDate ').value = arrResult[0][78]; } catch(ex) { };                 
    try { fm.all('ModifyTime ').value = arrResult[0][79]; } catch(ex) { };    
                                                                                             
}                                                                                                                                                        
//ʹ�ôӸô��ڵ����Ĵ����ܹ��۽�                                             
function myonfocus()                                                         
{                                                                            
	if(showInfo!=null)                                                           
	{                                                                            
	  try                                                                        
	  {                                                                          
	    showInfo.focus();                                                        
	  }                                                                      ;   
	  catch(ex)                                                                  
	  {                                                                          
	    showInfo=null;                                                           
	  }                                                                          
	}                                                                            
}                                                                            
                                                                             
                                                                             
                                                                             
//Ͷ���˿ͻ��Ų�ѯ��Ť�¼�                                                   
function queryAppntNo() { 
	 flag = '1';
	 if (fm.all("AppntNo").value == "" && loadFlag == "1") {                    
    showAppnt1();                                                            
    } else if (loadFlag != "1" && loadFlag != "2") {                         
     alert("ֻ����Ͷ����¼��ʱ���в�����");                                 
    } else {              
   //alert("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo = '" + fm.all("AppntNo").value + "'");                             
    if (arrResult == null) {                                                 
      alert("δ�鵽Ͷ������Ϣ");                                             
      displayAppnt(new Array());                                             
      emptyUndefined();                                                      
    } else {                                                                 
      displayAppnt(arrResult[0]);                                            
    }                                                                        
  }                                                                          
}                                                                            
                                                                             
function showAppnt1()                                                        
{                                                                            
	if( mOperate == 0 )                                                          
	{                                                                            
		mOperate = 2;                                                        
		showInfo = window.open( "../sys/LDPersonQueryNew.html" ,"",sFeatures);               
	}                                                                            
}                                                                            
function showInsured1()                                                        
{                                                                            
	if( mOperate == 0 )                                                          
	{                                                                            
		mOperate = 1;                                                        
		showInfo = window.open( "../sys/LDPersonQueryNew.html","",sFeatures );               
	}                                                                            
}                                                                                
function queryAgent()
{
	if(fm.all('ManageCom').value==""){
		 alert("����¼����������Ϣ��"); 
		 return;
	}
    if(fm.all('AgentCode').value == "")	{  
	  //var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+fm.all('ManageCom').value+",AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	  //����ҵ��Ա��ѯbranchtype='2' Modify by liuqh 2008-12-02
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+fm.all('ManageCom').value+"&branchtype=2","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	  
	  }
	if(fm.all('AgentCode').value != "")	 {
	var cAgentCode = fm.AgentCode.value;  //��������	
	//var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"' and ManageCom = '"+fm.all('ManageCom').value+"'";
    
    var sqlid8="FirstTrialContInputSql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName(sqlresourcename);
		mySql8.setSqlId(sqlid8);
		mySql8.addSubPara(cAgentCode);
        mySql8.addSubPara(fm.all('ManageCom').value);
    
    var arrResult = easyExecSql(mySql8.getString());
       //alert(arrResult);
    if (arrResult != null) {
      fm.AgentGroup.value = arrResult[0][2];
      alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][1]+"]");
    }
    else{
     fm.AgentGroup.value="";
     alert("����Ϊ:["+fm.all('AgentCode').value+"]�Ĵ����˲����ڣ���ȷ��!");
     }
	}	
}

//�����¼��
function QuestInput()
{
	cContNo = fm.ContNo.value;  //��������
	if(cContNo == "")
	{
		alert("���޺�ͬͶ�����ţ����ȱ���!");
	}
	else
	{	
		window.open("../uwgrp/QuestInputMain.jsp?ContNo="+cContNo+"&Flag="+ LoadFlag,"window1",sFeatures);
	}
}

//��ѯ����ʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
function afterQuery1(arrQueryResult)
{     
      //fm.all("Donextbutton1").style.display="";
      //fm.all("Donextbutton2").style.display="none";
      //fm.all("Donextbutton3").style.display="";
      //fm.all("butBack").style.display="none";
      //��ϸ��Ϣ��ʼ��
      detailInit(arrQueryResult); 
    
}
//��ѯ����ʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
function afterQuery2(arrResult)
{  
  if(arrResult!=null)
  {
  	fm.AgentCode.value = arrResult[0][0];
  	fm.AgentGroup.value = arrResult[0][1];
  }
}

function queryAgent2()
{
	if(fm.all('ManageCom').value==""){
		 alert("����¼����������Ϣ��"); 
		 return;
	}
	if(fm.all('AgentCode').value != "" && fm.all('AgentCode').value.length==10 )	 {
	var cAgentCode = fm.AgentCode.value;  //��������	
	//var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"' and ManageCom = '"+fm.all('ManageCom').value+"'";
    
     var sqlid9="FirstTrialContInputSql9";
		var mySql9=new SqlClass();
		mySql9.setResourceName(sqlresourcename);
		mySql9.setSqlId(sqlid9);
		mySql9.addSubPara(cAgentCode);
        mySql9.addSubPara(fm.all('ManageCom').value);
    
    
    var arrResult = easyExecSql(mySql9.getString());
       //alert(arrResult);
    if (arrResult != null) {
      fm.AgentGroup.value = arrResult[0][2];
      alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][1]+"]");
    }
    else{
     fm.AgentGroup.value="";
     alert("����Ϊ:["+fm.all('AgentCode').value+"]�Ĵ����˲����ڣ���ȷ��!");
     }
	}	
}                                                                             
function getdetail()
{
//var strSql = "select BankCode,AccName from LCAccount where BankAccNo='" + fm.AppntBankAccNo.value+"'";

  var sqlid10="FirstTrialContInputSql10";
		var mySql10=new SqlClass();
		mySql10.setResourceName(sqlresourcename);
		mySql10.setSqlId(sqlid10);
		mySql10.addSubPara(fm.AppntBankAccNo.value);

var arrResult = easyExecSql(mySql10.getString());
if (arrResult != null) {
      fm.AppntBankCode.value = arrResult[0][0];
      fm.AppntAccName.value = arrResult[0][1];
    }
else{  
     fm.AppntBankCode.value = '';
     fm.AppntAccName.value = '';
}	
}	                                                                             
function getdetailwork()
{
//var strSql = "select OccupationType from LDOccupation where OccupationCode='" + fm.AppntOccupationCode.value+"'";

  var sqlid11="FirstTrialContInputSql11";
		var mySql11=new SqlClass();
		mySql11.setResourceName(sqlresourcename);
		mySql11.setSqlId(sqlid11);
		mySql11.addSubPara(fm.AppntOccupationCode.value);

var arrResult = easyExecSql(mySql11.getString());
if (arrResult != null) {
      fm.AppntOccupationType.value = arrResult[0][0];

    }
else{  
     fm.AppntOccupationType.value = '';
}	
}                                                                             
function getdetailaddress(){
 	//var strSQL="select b.* from LCAddress b where b.AddressNo='"+fm.AddressNo.value+"' and b.CustomerNo='"+fm.AppntNo.value+"'";
        
        var sqlid12="FirstTrialContInputSql12";
		var mySql12=new SqlClass();
		mySql12.setResourceName(sqlresourcename);
		mySql12.setSqlId(sqlid12);
		mySql12.addSubPara(fm.AddressNo.value);
        mySql12.addSubPara(fm.AppntNo.value);
        arrResult=easyExecSql(mySql12.getString()); 
                                
   try{fm.all('AppntNo').value= arrResult[0][0];}catch(ex){};         
   try{fm.all('AppntAddressNo').value= arrResult[0][1];}catch(ex){};          
   try{fm.all('AppntPostalAddress').value= arrResult[0][2];}catch(ex){};     
   try{fm.all('AppntZipCode').value= arrResult[0][3];}catch(ex){};            
   try{fm.all('AppntPhone').value= arrResult[0][4];}catch(ex){};              
   try{fm.all('AppntFax').value= arrResult[0][5];}catch(ex){};                
   try{fm.all('AppntHomeAddress').value= arrResult[0][6];}catch(ex){};        
   try{fm.all('AppntHomeZipCode').value= arrResult[0][7];}catch(ex){};        
   try{fm.all('AppntHomePhone').value= arrResult[0][8];}catch(ex){};          
   try{fm.all('AppntHomeFax').value= arrResult[0][9];}catch(ex){};            
   try{fm.all('CompanyAddress').value= arrResult[0][10];}catch(ex){};     
   try{fm.all('AppntGrpZipCode').value= arrResult[0][11];}catch(ex){};     
   try{fm.all('AppntGrpPhone').value= arrResult[0][12];}catch(ex){};       
   try{fm.all('AppntGrpFax').value= arrResult[0][13];}catch(ex){};         
   try{fm.all('AppntMobile').value= arrResult[0][14];}catch(ex){};             
   try{fm.all('AppntMobileChs').value= arrResult[0][15];}catch(ex){};          
   try{fm.all('AppntEMail').value= arrResult[0][16];}catch(ex){};              
   try{fm.all('AppntBP').value= arrResult[0][17];}catch(ex){};                 
   try{fm.all('AppntMobile2').value= arrResult[0][18];}catch(ex){};            
   try{fm.all('AppntMobileChs2').value= arrResult[0][19];}catch(ex){};         
   try{fm.all('AppntEMail2').value= arrResult[0][20];}catch(ex){};             
   try{fm.all('AppntBP2').value= arrResult[0][21];}catch(ex){}; 
}
function getaddresscodedata()
{
    var i = 0;
    var j = 0;
    var m = 0;
    var n = 0;
    var strsql = "";
    var tCodeData = "0|";
   // strsql = "select AddressNo,PostalAddress from LCAddress where CustomerNo ='"+fm.AppntNo.value+"'";
    
     var sqlid13="FirstTrialContInputSql13";
		var mySql13=new SqlClass();
		mySql13.setResourceName(sqlresourcename);
		mySql13.setSqlId(sqlid13);
		mySql13.addSubPara(fm.AppntNo.value);

    
    //alert("strsql :" + strsql);
    turnPage.strQueryResult  = easyQueryVer3(mySql13.getString(), 1, 0, 1);  
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
    //return tCodeData;
    fm.all("AddressNo").CodeData=tCodeData;
} 
function afterCodeSelect( cCodeName, Field )
{
 if(cCodeName=="GetAddressNo"){
 	//var strSQL="select b.* from LCAddress b where b.AddressNo='"+fm.AddressNo.value+"' and b.CustomerNo='"+fm.AppntNo.value+"'";
        
         var sqlid14="FirstTrialContInputSql14";
		var mySql14=new SqlClass();
		mySql14.setResourceName(sqlresourcename);
		mySql14.setSqlId(sqlid14);
		mySql14.addSubPara(fm.AddressNo.value);
        mySql14.addSubPara(fm.AppntNo.value);
        
        arrResult=easyExecSql(mySql14.getString());                         
   try{fm.all('AppntNo').value= arrResult[0][0];}catch(ex){};         
   try{fm.all('AppntAddressNo').value= arrResult[0][1];}catch(ex){};          
   try{fm.all('AppntPostalAddress').value= arrResult[0][2];}catch(ex){};     
   try{fm.all('AppntZipCode').value= arrResult[0][3];}catch(ex){};            
   try{fm.all('AppntPhone').value= arrResult[0][4];}catch(ex){};              
   try{fm.all('AppntFax').value= arrResult[0][5];}catch(ex){};                
   try{fm.all('AppntHomeAddress').value= arrResult[0][6];}catch(ex){};        
   try{fm.all('AppntHomeZipCode').value= arrResult[0][7];}catch(ex){};        
   try{fm.all('AppntHomePhone').value= arrResult[0][8];}catch(ex){};          
   try{fm.all('AppntHomeFax').value= arrResult[0][9];}catch(ex){};            
   try{fm.all('CompanyAddress').value= arrResult[0][10];}catch(ex){};     
   try{fm.all('AppntGrpZipCode').value= arrResult[0][11];}catch(ex){};     
   try{fm.all('AppntGrpPhone').value= arrResult[0][12];}catch(ex){};       
   try{fm.all('AppntGrpFax').value= arrResult[0][13];}catch(ex){};        
   try{fm.all('AppntMobile').value= arrResult[0][14];}catch(ex){};             
   try{fm.all('AppntMobileChs').value= arrResult[0][15];}catch(ex){};          
   try{fm.all('AppntEMail').value= arrResult[0][16];}catch(ex){};              
   try{fm.all('AppntBP').value= arrResult[0][17];}catch(ex){};                 
   try{fm.all('AppntMobile2').value= arrResult[0][18];}catch(ex){};            
   try{fm.all('AppntMobileChs2').value= arrResult[0][19];}catch(ex){};         
   try{fm.all('AppntEMail2').value= arrResult[0][20];}catch(ex){};             
   try{fm.all('AppntBP2').value= arrResult[0][21];}catch(ex){};          
 }	
}                                                                
                                                                             
/*********************************************************************
 *  ��ʼ��������MissionID
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */

function initMissionID()
{
	if(tMissionID == "null" || tSubMissionID == "null")
	{
	  tMissionID = mSwitch.getVar('MissionID');
	  tSubMissionID = mSwitch.getVar('SubMissionID');
	}
	else
	{
	  mSwitch.deleteVar("MissionID");
	  mSwitch.deleteVar("SubMissionID");
	  mSwitch.addVar("MissionID", "", tMissionID);
	  mSwitch.addVar("SubMissionID", "", tSubMissionID);
	  mSwitch.updateVar("MissionID", "", tMissionID);
	  mSwitch.updateVar("SubMissionID", "", tSubMissionID);
	}
}   
function FillPostalAddress()
{
	 if(fm.CheckPostalAddress.value=="1") 
	 {
	 	fm.all('AppntPostalAddress').value=fm.all('CompanyAddress').value;
                fm.all('AppntZipCode').value=fm.all('AppntGrpZipCode').value;
                fm.all('AppntPhone').value= fm.all('AppntGrpPhone').value;
                fm.all('AppntFax').value= fm.all('AppntGrpFax').value;

	 }      
	 else if(fm.CheckPostalAddress.value=="2") 
	 {      
	 	fm.all('AppntPostalAddress').value=fm.all('AppntHomeAddress').value;
                fm.all('AppntZipCode').value=fm.all('AppntHomeZipCode').value;
                fm.all('AppntPhone').value= fm.all('AppntHomePhone').value;
                fm.all('AppntFax').value= fm.all('AppntHomeFax').value;
	 }
	 else if(fm.CheckPostalAddress.value=="3") 
	 {
	 	fm.all('AppntPostalAddress').value="";
                fm.all('AppntZipCode').value="";
                fm.all('AppntPhone').value= "";
                fm.all('AppntFax').value= "";
	 }
}                                                                                                                                                   
function AppntChk()
{
	var Sex=fm.AppntSex.value;
	var i=Sex.indexOf("-");
	Sex=Sex.substring(0,i);
      //  var sqlstr="select *from ldperson where Name='"+fm.AppntName.value+"' and Sex='"+Sex+"' and Birthday='"+fm.AppntBirthday.value+"' and CustomerNo<>'"+fm.AppntNo.value+"'";
        
         var sqlid15="FirstTrialContInputSql15";
		var mySql15=new SqlClass();
		mySql15.setResourceName(sqlresourcename);
		mySql15.setSqlId(sqlid15);
		mySql15.addSubPara(fm.AppntName.value);
        mySql15.addSubPara(Sex);
        mySql15.addSubPara(fm.AppntBirthday.value);
        mySql15.addSubPara(fm.AppntNo.value);
        arrResult = easyExecSql(mySql15.getString(),1,0);
        if(arrResult==null)
        {
	  alert("��û�����Ͷ�������ƵĿͻ�,����У��");
	  return false;
        }	
	window.open("../uwgrp/AppntChkMain.jsp?ProposalNo1="+fm.ContNo.value+"&Flag=A","window1",sFeatures);
}                                                                             
function queryInsuredNo()
{
	 flag = '2';
	 if (fm.all("InsuredNo").value == "" && loadFlag == "1") {                    
    showInsured1();                                                            
    } else if (loadFlag != "1" && loadFlag != "2") {                         
     alert("ֻ����Ͷ����¼��ʱ���в�����");                                 
    } else {              
    if (arrResult == null) {                                                 
      alert("δ�鵽Ͷ������Ϣ");                                             
      //displayAppnt(new Array());                                             
      emptyUndefined();                                                      
    } else {                                                                 
      //displayAppnt(arrResult[0]);                                            
    }                                                                        
  } 	 
}                                                                             
function displayInuserd()                                                                         
{
try{fm.all('InsuredNo').value= arrResult[0][0]; }catch(ex){};              
try{fm.all('InsuredName').value= arrResult[0][1]; }catch(ex){};            
try{fm.all('InsuredSex').value= arrResult[0][2]; }catch(ex){};             
try{fm.all('InsuredtBirthday').value= arrResult[0][3]; }catch(ex){};        
try{fm.all('InsuredtIDType').value= arrResult[0][4]; }catch(ex){};          
try{fm.all('InsuredtIDNo').value= arrResult[0][5]; }catch(ex){};        
try{fm.all('InsuredMarriage').value= arrResult[0][11];}catch(ex){}; 
}  
function AddInsured()
{
	if(GrpGrid.mulLineCount==1)
	{
		 alert('��������ֻ����һ��������!!');
		 return;
	}
	if(fm.ContNo.value=="")
	 { 
	 	  alert("���ȱ�����ӱ���!!");
	 	  return;
	 }
	if(fm.InsuredName.value==""||fm.InsuredIDType.value==""||fm.InsuredIDNo.value==""||fm.InsuredBirthday.value==""||fm.InsuredSex.value=="")
	{
		alert("��������������!!");
		return;
	}
	
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
	fm.action="FirstTrialInsuredSave.jsp"
	fm.fmAction.value='INSERT||CONTINSURED';
	fm.submit();  
}                                                                             
//��֪ͨ��
function showHealth()
{
	var tSel = GrpGrid.mulLineCount;
	if(tSel=='0')
	{
		 alert("����¼�뱻����");
		 return;
	}
  MakeWorkFlow();
	//var strSQL = "select missionid from lwmission where activityid = '0000001106' and missionid = '" + fm.MissionID.value +"'";
	
	 var sqlid16="FirstTrialContInputSql16";
		var mySql16=new SqlClass();
		mySql16.setResourceName(sqlresourcename);
		mySql16.setSqlId(sqlid16);
		mySql16.addSubPara(fm.MissionID.value);

	
	arrResult=easyExecSql(mySql16.getString());
	if(arrResult!=null)
	{
		 alert('���ȴ�ӡ�ѷ����֪ͨ�飡��');
		 return;
	}
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  var cContNo = fm.ContNo.value;
  var cMissionID = fm.MissionID.value;
  var cSubMissionID = fm.SubMissionID.value;
  var cPrtNo = fm.PrtNo.value;
  
  if (cContNo != "")
  {
  	//var tSelNo = PolAddGrid.getSelNo()-1;
  	//var tNo = PolAddGrid.getRowColData(tSelNo,1);	
  	//showHealthFlag[tSelNo] = 1 ;
  	window.open("../uwgrp/UWManuHealthMain.jsp?ContNo1="+cContNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID+"&PrtNo="+cPrtNo,"window1",sFeatures);
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("����ѡ�񱣵�!");
  }
}
//����֪ͨ��¼��
function showReport()
{
	var tSel = GrpGrid.mulLineCount;

	if(tSel=='0')
	{
		 alert("����¼�뱻����");
		 return;
	}
	MakeWorkFlow();
//	var strSQL = "select missionid from lwmission where activityid = '0000001108' and missionid = '" + fm.MissionID.value +"'";
	
	
	 var sqlid17="FirstTrialContInputSql17";
		var mySql17=new SqlClass();
		mySql17.setResourceName(sqlresourcename);
		mySql17.setSqlId(sqlid17);
		mySql17.addSubPara(fm.MissionID.value);
	
	
	arrResult=easyExecSql(mySql17.getString());
	if(arrResult!=null)
	{
		 alert('���ȴ�ӡ�ѷ����֪ͨ�飡��');
		 return;
	}
	var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  var cContNo=fm.ContNo.value;

  var cMissionID =fm.MissionID.value; 
  var cSubMissionID =fm.SubMissionID.value; 
  var cPrtNo = fm.PrtNo.value;

  window.open("../uwgrp/UWManuRReportMain.jsp?ContNo="+cContNo+"&MissionID="+cMissionID+"&PrtNo="+cPrtNo+"&SubMissionID="+cSubMissionID,"window2",sFeatures);  	
  showInfo.close();  
}
//�Զ���������ʼ��
function MakeWorkFlow()
{
	fm.action="FirstTrialAutoChk.jsp"
	fm.submit();
}
//��ʼ��Ͷ����,��������Ϣ
function initDetail()
{
	 //	var strSQL = "select contno,agentcode,agentgroup,appntno,appntname,AppntSex,appntbirthday,appntidtype,appntidno,salechnl from lccont where prtno = '" + prtNo +"'";
   
    var sqlid18="FirstTrialContInputSql18";
		var mySql18=new SqlClass();
		mySql18.setResourceName(sqlresourcename);
		mySql18.setSqlId(sqlid18);
		mySql18.addSubPara(prtNo);
	
   
    arrResult=easyExecSql(mySql18.getString());
    if(arrResult!=null)
    {
    	fm.ContNo.value=arrResult[0][0];
    	fm.AgentCode.value=arrResult[0][1];
    	fm.AgentGroup.value=arrResult[0][2];
    	fm.AppntNo.value=arrResult[0][3];
    	fm.AppntName.value=arrResult[0][4];
    	fm.AppntSex.value=arrResult[0][5];
    	fm.AppntBirthday.value=arrResult[0][6];
    	fm.AppntIDType.value=arrResult[0][7];
    	fm.AppntIDNo.value=arrResult[0][8];
    	fm.SaleChnl.value=arrResult[0][9];
    }
    if(fm.ContNo.value!='')
    {
     // strSQL = "select insuredno,name from lcinsured where trim(contno) = '" + fm.ContNo.value +"'"
     
      var sqlid19="FirstTrialContInputSql19";
		var mySql19=new SqlClass();
		mySql19.setResourceName(sqlresourcename);
		mySql19.setSqlId(sqlid19);
		mySql19.addSubPara(fm.ContNo.value);
	
     
      arrResult=easyExecSql(mySql19.getString());
      if(arrResult!=null)
      {
      	for(i=0;i<arrResult.length;i++)
      	{
      		GrpGrid.addOne("GrpGrid");
      		GrpGrid.setRowColData(GrpGrid.mulLineCount-1,1,arrResult[i][0]);
      		GrpGrid.setRowColData(GrpGrid.mulLineCount-1,2,arrResult[i][1]);
      	}
      }
    }
}
//ɾ��������
function DelInsured()
{
  var i = 0;
  var checkFlag = 0;
  var state = "0";
  
  for (i=0; i<GrpGrid.mulLineCount; i++) {
    if (GrpGrid.getSelNo(i)) { 
      checkFlag = GrpGrid.getSelNo();
      break;
    }
  }
  
  if (checkFlag) { 
    fm.InsuredName.value = GrpGrid.getRowColData(checkFlag - 1, 2);
    fm.InsuredNo.value = GrpGrid.getRowColData(checkFlag - 1, 1);
    var showStr="����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action = 'FirstTrialInsuredSave.jsp';
    fm.fmAction.value='DELETE||CONTINSURED';
    fm.submit();
  }
  else {
    alert("����ѡ��һ��������Ϣ��"); 
  }
}
//��ӡ���֪ͨ��
function PrintHealth()
{
	//var strSQL = "select missionid,submissionid,missionprop3 from lwmission where activityid = '0000001106' and missionprop1 = '" + fm.PrtNo.value +"'";
	
	 var sqlid20="FirstTrialContInputSql20";
		var mySql20=new SqlClass();
		mySql20.setResourceName(sqlresourcename);
		mySql20.setSqlId(sqlid20);
		mySql20.addSubPara(fm.PrtNo.value);
	
	
	arrResult=easyExecSql(mySql20.getString());
	if(arrResult==null)
	{
		 alert('û����Ҫ��ӡ�����֪ͨ��!!');
	}
	window.open( "../uwgrp/BodyCheckPrintSave.jsp?PrtSeq="+arrResult[0][2]+"&MissionID="+arrResult[0][0]+"&SubMissionID="+arrResult[0][1] ,"",sFeatures); 
}                                                                           
//��ӡ����֪ͨ��
function PrintReport()
{
	//var strSQL = "select missionid,submissionid,missionprop3 from lwmission where activityid = '0000001108' and missionprop1 = '" + fm.PrtNo.value +"'";
	
	var sqlid21="FirstTrialContInputSql21";
		var mySql21=new SqlClass();
		mySql21.setResourceName(sqlresourcename);
		mySql21.setSqlId(sqlid21);
		mySql21.addSubPara(fm.PrtNo.value);
	
	
	arrResult=easyExecSql(mySql21.getString());
	if(arrResult==null)
	{
		 alert('û����Ҫ��ӡ�����֪ͨ��!!');
	}
	fm.PrtSeq.value = arrResult[0][2];
	fm.MissionID.value = arrResult[0][0];
	fm.SubMissionID.value = arrResult[0][1];
	fm.action = '../uwgrp/MeetF1PSave.jsp';
	fm.submit();
}                                                                               
//�ж��ǲ���Ͷ���˺ͱ������ǲ���ͬһ��
function isSamePerson()
{
	if (fm.SamePersonFlag.checked==true) 
  { 
  	  fm.all["DivLCInsuredInd"].style.display = 'none';
      displayissameperson();      
  }else if(fm.SamePersonFlag.checked==false)
  {
  	  fm.all["DivLCInsuredInd"].style.display = '';
      Quickissameperson();  	
  }
}                                                                            
function displayissameperson()
{
	    fm.InsuredNo.value = fm.AppntNo.value;
    	fm.InsuredName.value = fm.AppntName.value;
    	fm.InsuredSex.value= fm.AppntSex.value;
    	fm.InsuredBirthday.value=fm.AppntBirthday.value;
    	fm.InsuredIDType.value=fm.AppntIDType.value;
    	fm.InsuredIDNo.value=fm.AppntIDNo.value;
}                          
function Quickissameperson()
{
	    fm.InsuredNo.value = "";
    	fm.InsuredName.value = "";
    	fm.InsuredSex.value= "";
    	fm.InsuredBirthday.value="";
    	fm.InsuredIDType.value="";
    	fm.InsuredIDNo.value="";	
} 
/*********************************************************************
 *  �������֤��ȡ�ó������ں��Ա�
 *  ����  ��  ���֤��
 *  ����ֵ��  ��
 *********************************************************************
 */

function getBirthdaySexByIDNo(iIdNo)
{
	if(fm.all('AppntIDType').value=="0")
	{
		fm.all('AppntBirthday').value=getBirthdatByIdNo(iIdNo);
		fm.all('AppntSex').value=getSexByIDNo(iIdNo);
	}	
} 
function checkidtype()
{
	if(fm.AppntIDType.value==""&&fm.AppntIDNo.value!="")
	{
		alert("����ѡ��֤�����ͣ�");
		fm.AppntIDNo.value=""; 
  }
}   
function getBirthdaySexByIDNo1(iIdNo)
{
	if(fm.all('InsuredIDType').value=="0")
	{
		fm.all('InsuredBirthday').value=getBirthdatByIdNo(iIdNo);
		fm.all('InsuredSex').value=getSexByIDNo(iIdNo);
	}	
} 
function checkidtype1()
{
	if(fm.InsuredIDType.value==""&&fm.InsuredIDNo.value!="")
	{
		alert("����ѡ��֤�����ͣ�");
		fm.InsuredIDNo.value=""; 
  }
}                                                
                                                                             
                                                                             
                                                                             
                                                                             