//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var spanObj;
var mDebug = "100";
var mOperate = 0;
var mAction = "";
var arrResult = new Array();
var mShowCustomerDetail = "PROPOSAL";
var mCurOperateFlag = ""	// "1--¼�룬"2"--��ѯ
var mGrpFlag = ""; 	//���˼����־,"0"��ʾ����,"1"��ʾ����.
var mySql = new SqlClass();
window.onfocus = myonfocus;

/*********************************************************************
 *  ѡ�����ֺ�Ķ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterCodeSelect( cCodeName, Field )
{
	try	{
		if( cCodeName == "RiskInd" || cCodeName == "RiskGrp" )	
		{
			getRiskInput( Field.value, loadFlag );//loadFlag��ҳ���ʼ����ʱ������
		}
	}
	catch( ex ) {
	}
}

/*********************************************************************
 *  ����LoadFlag����һЩFlag����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function convertFlag( cFlag )
{
  //alert("cFlag:" + cFlag);
	if( cFlag == "1" )		// ����Ͷ����ֱ��¼��
	{
		mCurOperateFlag = "1";
		mGrpFlag = "0";
	}
	if( cFlag == "2" )		// �����¸���Ͷ����¼��
	{
		mCurOperateFlag = "1";
		mGrpFlag = "1";
	}
	if( cFlag == "3" )		// ����Ͷ������ϸ��ѯ
	{
		mCurOperateFlag = "2";
		mGrpFlag = "0";
	}
	if( cFlag == "4" )		// �����¸���Ͷ������ϸ��ѯ
	{
		mCurOperateFlag = "2";
		mGrpFlag = "1";
	}
	if( cFlag == "5" )		// �����¸���Ͷ������ϸ��ѯ
	{
		mCurOperateFlag = "2";
		mGrpFlag = "3";
	}
}

/*********************************************************************
 *  ���ݲ�ͬ������,��ȡ��ͬ�Ĵ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function getRiskInput( cRiskCode, cFlag ) {
	var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr = "";
	var tPolNo = "";

	convertFlag( cFlag );


	if( mGrpFlag == "0" )		// ����Ͷ����
		urlStr = "./riskinput/RiskAll.jsp";  

	if( mGrpFlag == "1" )		// �����¸���Ͷ����
		urlStr = "./riskgrp/RiskAll" + ".jsp";  
	if( mGrpFlag == "3" )		// �����¸���Ͷ����
		urlStr = "./riskinput/RiskAll.jsp"; 

	//��ȡ���ֵĽ�������
//	showInfo = window.showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;dialogTop:-800;dialogLeft:-800");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=1;      //�������ڵĿ��; 
	var iHeight=1;     //�������ڵĸ߶�; 
	var iTop = 1; //��ô��ڵĴ�ֱλ�� 
	var iLeft = 1;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	//��ʼ��������Ϣ
	emptyForm();

	fm.all( "RiskCode" ).value = cRiskCode;
	try	{
		showInfo.close();
	}	catch(ex){}

	if( mCurOperateFlag == "1" ) {             // ¼��
		if( mGrpFlag == "1" )	{                  // �����¸���Ͷ����			
			getGrpPolInfo();                       // ���벿�ּ�����Ϣ
		}
		
		if( isSubRisk( cRiskCode ) == true ) {   // ����
			tPolNo = getMainRiskNo( cRiskCode );   //����¼�븽�յĴ���,�õ����ձ�������
			
			try	{                                  //��ʼ��������Ϣ
				initPrivateRiskInfo( tPolNo );
			}	catch(ex1) {
				alert( "��ʼ�����ֳ���" + ex1 );
			}
		} // end of ����if
		
		showInfo = null;
	} // end of ¼��if
	
	mCurOperateFlag = "";
	mGrpFlag = "";
}

/*********************************************************************
 *  �жϸ������Ƿ��Ǹ���,������ȷ���ȿ���������,�ֿ��������յĴ���
 *  ����  ��  ���ִ���
 *  ����ֵ��  ��
 *********************************************************************
 */
function isSubRisk(cRiskCode) {
  //var arrQueryResult = easyExecSql("select SubRiskFlag from LMRiskApp where RiskCode='" + cRiskCode + "'", 1, 0);
	mySql = new SqlClass();
	mySql.setResourceName("sys.AllProShow_BSql");
	mySql.setSqlId("AllProShow_BSql1");
	mySql.addSubPara(cRiskCode ); 
	var arrQueryResult = easyExecSql(mySql.getString(), 1, 0);
	if(arrQueryResult[0] == "S")    //��Ҫת�ɴ�д
		return true;
	if(arrQueryResult[0] == "M")
		return false;

	if (arrQueryResult[0].toUpperCase() == "A")
		if (confirm("�����ּȿ���������,�ֿ����Ǹ���!ѡ��ȷ����������¼��,ѡ��ȡ�����븽��¼��"))
			return false;
		else
			return true;

	return false;
}

/*********************************************************************
 *  ����¼�븽�յĴ���,�õ����ձ�������
 *  ����  ��  ���ִ���
 *  ����ֵ��  ��
 *********************************************************************
 */
function getMainRiskNo(cRiskCode)
{
	var urlStr = "../app/MainRiskNoInput.html";
	var tPolNo="";

//	tPolNo = window.showModalDialog(urlStr,tPolNo,"status:no;help:0;close:0;dialogWidth:310px;dialogHeight:100px;center:yes;");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=310;      //�������ڵĿ��; 
	var iHeight=100;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	return tPolNo;
}

/*********************************************************************
 *  ��ʼ��������Ϣ
 *  ����  ��  Ͷ������
 *  ����ֵ��  ��
 *********************************************************************
 */
function initPrivateRiskInfo(cPolNo) {
	if(cPolNo=="") {
		alert("û�����ձ�����,���ܽ��и�����¼��!");
		mCurOperateFlag="";        //��յ�ǰ��������,ʹ�ò��ܽ��е�ǰ����.
		return false
	}
	
	var arrLCPol = new Array(); 
	var arrQueryResult = null;
	// ��������Ϣ����
	//arrQueryResult = easyExecSql("select * from lbpol where polno='"+cPolNo+"'", 1, 0);
	mySql = new SqlClass();
	mySql.setResourceName("sys.AllProShow_BSql");
	mySql.setSqlId("AllProShow_BSql2");
	mySql.addSubPara(cPolNo );
	arrQueryResult = easyExecSql(mySql.getString(), 1, 0); 
	arrLCPol = arrQueryResult[0]; 	
	displayPol( arrLCPol );	
	
	if (arrLCPol == null)	{
		mCurOperateFlag="";        //��յ�ǰ��������,ʹ�ò��ܽ��е�ǰ����.
		alert("��ȡ������Ϣʧ��,���ܽ��и�����¼��!");
		return false
	}
	
	fm.all("MainPolNo").value = cPolNo;
	// Ͷ������Ϣ����    
	//���Ȳ�ѯ����Ӧ��Ͷ������Ϣ
	var tAR;
			
	// ��������Ϣ����
	arrQueryResult = null;
	//arrQueryResult = easyExecSql("select * from lcinsured where polno='"+cPolNo+"'"+" and customerno='"+arrLCPol[18]+"'", 1, 0);
	mySql = new SqlClass();
	mySql.setResourceName("sys.AllProShow_BSql");
	mySql.setSqlId("AllProShow_BSql3");
	mySql.addSubPara(cPolNo );
	mySql.addSubPara(arrLCPol[18] );
	arrQueryResult = easyExecSql(mySql.getString(), 1, 0);
	tAR = arrQueryResult[0];
	displayPolInsured(tAR);	
	
	if (arrLCPol[18] == arrLCPol[26]) {
	  	fm.all("SamePersonFlag").checked = true;
		parent.fraInterface.isSamePersonQuery();
    	parent.fraInterface.fm.all("AppntCustomerNo").value = arrLCPol[18];
	} else {
  	//Ͷ������Ϣ
  	if (arrLCPol[28]=="1") {     //����Ͷ������Ϣ
  	  arrQueryResult = null;
  	  //arrQueryResult = easyExecSql("select * from lcappntgrp where polno='"+cPolNo+"'"+" and grpno='"+arrLCPol[26]+"'", 1, 0);	  		
  	  mySql = new SqlClass();
		mySql.setResourceName("sys.AllProShow_BSql");
		mySql.setSqlId("AllProShow_BSql4");
		mySql.addSubPara(cPolNo );
		mySql.addSubPara(arrLCPol[26] );
		 arrQueryResult = easyExecSql(mySql.getString(), 1, 0);	  		
  	  tAR = arrQueryResult[0];
  	  displayPolAppntGrp(tAR);
  	} else {                     //����Ͷ������Ϣ
  	  arrQueryResult = null;
  	  //arrQueryResult = easyExecSql("select * from lcappntind where polno='"+cPolNo+"'"+" and customerno='"+arrLCPol[26]+"'", 1, 0);
  	  mySql = new SqlClass();
		mySql.setResourceName("sys.AllProShow_BSql");
		mySql.setSqlId("AllProShow_BSql5");
		mySql.addSubPara(cPolNo );
		mySql.addSubPara(arrLCPol[26] );
		 arrQueryResult = easyExecSql(mySql.getString(), 1, 0);	  
  	  tAR = arrQueryResult[0];
  	  displayPolAppnt(tAR);
  	}
	}

}

/*********************************************************************
 *  У��Ͷ����������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function verifyProposal() {
	if( verifyInput() == false ) return false;
	
	BnfGrid.delBlankLine();
	if( BnfGrid.checkValue("BnfGrid") == false ) return false;
	return true;
}

/*********************************************************************
 *  �������Ͷ�������ύ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function submitForm()
{
	var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  

	showSubmitFrame(mDebug);
	// У��¼������
	if( verifyProposal() == false ) return;
	
	if( mAction == "" )
	{
//		showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();

		if (loadFlag=="1") 
		{
			mAction = "INSERTPERSON";
		}
		else
		{
			mAction = "INSERTGROUP";
		}
	
		fm.all( 'fmAction' ).value = mAction;
		fm.submit(); //�ύ
	}
}


/*********************************************************************
 *  �������Ͷ�������ύ��Ĳ���,���������ݷ��غ�ִ�еĲ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
	showInfo.close();
	if (FlagStr == "Fail" )
	{             
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
//		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
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
//		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	
		showDiv(operateButton,"true"); 
		showDiv(inputButton,"false");
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
	}
	catch(re)
	{
		alert("��ProposalInput.js-->resetForm�����з����쳣:��ʼ���������!");
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
		parent.fraMain.rows = "0,0,80,72,*";
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
}           

/*********************************************************************
 *  Click�¼������������ѯ��ͼƬʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function queryClick() {
  if (showInfo == null) 
    mOperate = 0;
    
	if( mOperate == 0 )
	{
		mOperate = 1;
		showInfo = window.open("../app/ProposalQuery.html","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
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
	var tProposalNo = "";
	tProposalNo = fm.all( 'ProposalNo' ).value;
	if( tProposalNo == null || tProposalNo == "" )
		alert( "������Ͷ������ѯ�������ٽ����޸�!" );
	else
	{
		// У��¼������
		if (fm.all('divLCAppntInd1').style.display == "none") {
      for (var elementsNum=0; elementsNum<fm.elements.length; elementsNum++) {    
    	  if (fm.elements[elementsNum].verify != null && fm.elements[elementsNum].name.indexOf("Appnt") != -1) {
    	    fm.elements[elementsNum].verify = "";
    	  }
    	} 
    }
		if( verifyProposal() == false ) return;

		var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		
		if( mAction == "" )
		{
			showSubmitFrame(mDebug);
//			showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();

			if (loadFlag=="1")
			{
				mAction = "UPDATEPERSON";
			}
			else
			{
				mAction = "UPDATEGROUP";
			}
			
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
function deleteClick() { 
	var tProposalNo = fm.all( 'ProposalNo' ).value;
	
	if( tProposalNo == null || tProposalNo == "" )
		alert( "������Ͷ������ѯ�������ٽ���ɾ��!" );
	else {
		var showStr = "����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		
		if( mAction == "" )	{
			//showSubmitFrame(mDebug);
//			showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
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

/*********************************************************************
 *  Click�¼����������ѡ�����Ρ�ͼƬʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function chooseDuty()
{
	cRiskCode = fm.RiskCode.value;
	cRiskVersion = fm.RiskVersion.value
	
	if( cRiskCode == "" || cRiskVersion == "" )
	{
		alert( "��������¼�����ֺ����ְ汾�����޸ĸ�Ͷ�����������" );
		return false
	}

	showInfo = window.open("../app/ChooseDutyInput.jsp?RiskCode="+cRiskCode+"&RiskVersion="+cRiskVersion,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
	return true
}           

/*********************************************************************
 *  Click�¼������������ѯ������Ϣ��ͼƬʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showDuty()
{
	//����������Ӧ�Ĵ���
	cPolNo = fm.ProposalNo.value;
	if( cPolNo == "" )
	{
		alert( "�������Ȳ�ѯͶ�������ܲ鿴��Ͷ�����������" );
		return false
	}
	
	var showStr = "���ڲ�ѯ���ݣ������Ժ�......";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//	showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

//	showModalDialog( "../app/ProposalDuty.jsp?PolNo="+cPolNo,window,"status:no;help:0;close:0;dialogWidth=18cm;dialogHeight=14cm");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=18cm;      //�������ڵĿ��; 
	var iHeight=14cm;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	showInfo.close();
}           

/*********************************************************************
 *  Click�¼���������������ݽ�����Ϣ��ͼƬʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showFee()
{
	cPolNo = fm.ProposalNo.value;
	if( cPolNo == "" )
	{
		alert( "�������Ȳ�ѯͶ�������ܽ����ݽ�����Ϣ���֡�" );
		return false
	}
	
	showInfo = window.open( "../app/ProposalFee.jsp?PolNo=" + cPolNo + "&polType=PROPOSAL" ,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}           

/*********************************************************************
 *  Click�¼�����˫����Ͷ���˿ͻ��š�¼���ʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showAppnt()
{
	if( mOperate == 0 )
	{
		mOperate = 2;
		showInfo = window.open( "../sys/LDPersonMain.html" ,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
	}
}           

/*********************************************************************
 *  Click�¼�����˫���������˿ͻ��š�¼���ʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showInsured()
{
	if( mOperate == 0 )
	{
		mOperate = 3;
		showInfo = window.open( "../sys/LDPersonMain.html","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes" );
	}
}           

/*********************************************************************
 *  Click�¼�����˫�������������˿ͻ��š�¼���ʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showSubInsured( span, arrPara )
{
	if( mOperate == 0 )
	{
		mOperate = 4;
		spanObj = span;
		showInfo = window.open( "../sys/LDPersonMain.html" ,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
	}
}           

/*********************************************************************
 *  �������е�������ʾ��Ͷ���˲���
 *  ����  ��  ���˿ͻ�����Ϣ
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayPol(cArr) 
{
	try 
	{
	    fm.all('ManageCom').value = cArr[12];
	    fm.all('SaleChnl').value = cArr[15];
	    fm.all('AgentCom').value = cArr[13];
	    fm.all('AgentCode').value = cArr[87];
	    fm.all('AgentGroup').value = cArr[88];
	    fm.all('Handler').value = cArr[82];
	    fm.all('AgentCode1').value = cArr[89];
	
	    fm.all('ContNo').value = cArr[1];
	    fm.all('GrpPolNo').value = cArr[2];

	    fm.all('Amnt').value = cArr[43];
	    fm.all('CValiDate').value = cArr[29];
	    fm.all('HealthCheckFlag').value = cArr[72];
	    fm.all('OutPayFlag').value = cArr[97];
	    fm.all('PayLocation').value = cArr[59];
	    fm.all('BankCode').value = cArr[102];
	    fm.all('BankAccNo').value = cArr[103];
	    fm.all('LiveGetMode').value = cArr[98];
	    fm.all('BonusGetMode').value = cArr[100];
	    fm.all('AutoPayFlag').value = cArr[65];
	    fm.all('InterestDifFlag').value = cArr[66];

	    fm.all('InsuYear').value = cArr[111];
	    fm.all('InsuYearFlag').value = cArr[110];
	    
	} catch(ex) {
	  alert("displayPol err:" + ex + "\ndata is:" + cArr);
	}
}

/*********************************************************************
 *  �ѱ����е�Ͷ������Ϣ��ʾ��Ͷ���˲���
 *  ����  ��  ���˿ͻ�����Ϣ
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayPolAppnt(cArr) {
try {
	fm.all( 'AppntCustomerNo' ).value          = cArr[1];
	fm.all( 'AppntName' ).value                = cArr[6];
	fm.all( 'AppntSex' ).value                 = cArr[7];
	fm.all( 'AppntBirthday' ).value            = cArr[8];
	fm.all( 'AppntAge' ).value                 = "";
	fm.all( 'AppntIDType' ).value              = cArr[20];
	fm.all( 'AppntIDNo' ).value                = cArr[22];
	fm.all( 'AppntNativePlace' ).value         = cArr[9];
	fm.all( 'AppntRgtAddress' ).value          = cArr[52];
	fm.all( 'AppntMarriage' ).value            = cArr[11];
	fm.all( 'AppntNationality' ).value         = cArr[10];
	fm.all( 'AppntDegree' ).value              = cArr[48];
	fm.all( 'AppntSmokeFlag' ).value           = cArr[51];
	fm.all( 'AppntPostalAddress' ).value       = cArr[28];
	fm.all( 'AppntZipCode' ).value             = cArr[29];
	fm.all( 'AppntPhone' ).value               = cArr[30];
	fm.all( 'AppntMobile' ).value              = cArr[32];
	fm.all( 'AppntEMail' ).value               = cArr[33];
	fm.all( 'AppntGrpName' ).value             = cArr[37];
	fm.all( 'AppntGrpPhone' ).value            = cArr[38];
	fm.all( 'AppntGrpAddress' ).value          = cArr[40];
	fm.all( 'AppntGrpZipCode' ).value          = cArr[50];
	fm.all( 'AppntWorkType' ).value            = cArr[46];
	fm.all( 'AppntPluralityType' ).value       = cArr[47];
	fm.all( 'AppntOccupationType' ).value      = cArr[13];
	fm.all( 'AppntOccupationCode' ).value      = cArr[49];
} catch(ex) {
  alert("displayAppnt err:" + ex + "\ndata is:" + cArr);
}
//  fm.all('RelationToInsured').value = '';
}

/*********************************************************************
 *  �ѱ����е�Ͷ����������ʾ��Ͷ���˲���
 *  ����  ��  ����ͻ�����Ϣ
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayPolAppntGrp( cArr )
{
	fm.all( 'AppntCustomerNo' ).value = cArr[0];
	fm.all( 'AppntName' ).value = cArr[2];
	fm.all( 'AppntSex' ).value = cArr[3];
	fm.all( 'AppntBirthday' ).value = cArr[4];
	fm.all( 'AppntIDType' ).value =cArr[19];
	fm.all( 'AppntIDNo' ).value = cArr[18];
//  fm.all('RelationToInsured').value = '';
	fm.all('AppntPhone').value = cArr[26];
	fm.all('AppntMobile').value = cArr[28];
	fm.all('AppntPostalAddress').value = cArr[24];
	fm.all('AppntZipCode').value = cArr[25];
	fm.all('AppntEMail').value = cArr[29];
}

/*********************************************************************
 *  �ѱ����еı�����������ʾ�������˲���
 *  ����  ��  �ͻ�����Ϣ
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayPolInsured(cArr) {
  
	fm.all( 'CustomerNo' ).value          = cArr[1];
	fm.all( 'Name' ).value                = cArr[6];
	fm.all( 'Sex' ).value                 = cArr[7];
	fm.all( 'Birthday' ).value            = cArr[8];
	fm.all( 'Age' ).value                 = "";
	fm.all( 'IDType' ).value              = cArr[20];
	fm.all( 'IDNo' ).value                = cArr[22];
	fm.all( 'NativePlace' ).value         = cArr[9];
	fm.all( 'RgtAddress' ).value          = cArr[52];
	fm.all( 'Marriage' ).value            = cArr[11];
	fm.all( 'Nationality' ).value         = cArr[10];
	fm.all( 'Degree' ).value              = cArr[49];
	fm.all( 'SmokeFlag' ).value           = cArr[51];
	fm.all( 'PostalAddress' ).value       = cArr[28];
	fm.all( 'ZipCode' ).value             = cArr[29];
	fm.all( 'Phone' ).value               = cArr[30];
	fm.all( 'Mobile' ).value              = cArr[32];
	fm.all( 'EMail' ).value               = cArr[33];
	fm.all( 'GrpName' ).value             = cArr[37];
	fm.all( 'GrpPhone' ).value            = cArr[38];
	fm.all( 'GrpAddress' ).value          = cArr[40];
	fm.all( 'GrpZipCode' ).value          = cArr[50];
	fm.all( 'WorkType' ).value            = cArr[46];
	fm.all( 'PluralityType' ).value       = cArr[47];
	fm.all( 'OccupationType' ).value      = cArr[13];
	fm.all( 'OccupationCode' ).value      = cArr[48];
	
//  fm.all('RelationToInsured').value = '';
}


/*********************************************************************
 *  �Ѳ�ѯ���صĿͻ�������ʾ�����������˲���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function displaySubInsured()
{
	fm.all( spanObj ).all( 'SubInsuredGrid1' ).value = arrResult[0][0];
	fm.all( spanObj ).all( 'SubInsuredGrid2' ).value = arrResult[0][1];
	fm.all( spanObj ).all( 'SubInsuredGrid3' ).value = arrResult[0][2];
	fm.all( spanObj ).all( 'SubInsuredGrid4' ).value = arrResult[0][3];
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
		
		if( mOperate == 1 )	{           // ��ѯ������ϸ
			var tPolNo = arrResult[0][0];
			
			// ��ѯ������ϸ
			queryDescPolDetail( tPolNo );
		}
		
		if( mOperate == 2 ) {		// Ͷ������Ϣ	  
		//	arrResult = easyExecSql("select * from LDPerson where CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0);
			mySql = new SqlClass();
		mySql.setResourceName("sys.AllProShow_BSql");
		mySql.setSqlId("AllProShow_BSql6");
		mySql.addSubPara(arrQueryResult[0][0]  ); 
		arrResult = easyExecSql(mySql.getString(), 1, 0);
			if (arrResult == null) {
			  alert("δ�鵽Ͷ������Ϣ");
			} else {
			   displayAppnt(arrResult[0]);
			}

	  }
		if( mOperate == 3 )	{		// ����������Ϣ
			//arrResult = easyExecSql("select * from LDPerson where CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0);
			mySql = new SqlClass();
		mySql.setResourceName("sys.AllProShow_BSql");
		mySql.setSqlId("AllProShow_BSql7");
		mySql.addSubPara(arrQueryResult[0][0]  ); 
		arrResult = easyExecSql(mySql.getString(), 1, 0);
			if (arrResult == null) {
			  alert("δ�鵽����������Ϣ");
			} else {
			   displayInsured(arrResult[0]);
			}

	  }
		if( mOperate == 4 )	{		// ������������Ϣ
			displaySubInsured(arrResult[0]);
	  }
	}
	mOperate = 0;		// �ָ���̬
	
	emptyUndefined(); 
}

/*********************************************************************
 *  ���ݲ�ѯ���ص���Ϣ��ѯͶ������ϸ��Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function queryPolDetail( cPolNo )
{

	emptyForm();
   	parent.fraTitle.window.location = "./AllProposalQueryDetail.jsp?PolNo=" + cPolNo;
	//parent.fraTitle.window.submit();
}

function queryDescPolDetail( cPolNo )
{

	emptyForm();
   
	parent.fraTitle.window.location = "./AllDescProposalQueryDetail.jsp?PolNo=" + cPolNo;
		
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

//*************************************************************
//�����˿ͻ��Ų�ѯ��Ť�¼�
function queryInsuredNo() {
  if (fm.all("CustomerNo").value == "") {
    showInsured1();
  }  else {
    //arrResult = easyExecSql("select * from LDPerson where CustomerNo = '" + fm.all("CustomerNo").value + "'", 1, 0);
    mySql = new SqlClass();
		mySql.setResourceName("sys.AllProShow_BSql");
		mySql.setSqlId("AllProShow_BSql8");
		mySql.addSubPara( fm.all("CustomerNo").value); 
		arrResult = easyExecSql(mySql.getString(), 1, 0);
    if (arrResult == null) {
      alert("δ�鵽����������Ϣ");
      displayInsured(new Array());
      emptyUndefined(); 
    } else {
      displayInsured(arrResult[0]);
    }
  }
}

//*************************************************************
//Ͷ���˿ͻ��Ų�ѯ��Ť�¼�
function queryAppntNo() {
  if (fm.all("AppntCustomerNo").value == "" && loadFlag == "1") {
    showAppnt1();
  } else if (loadFlag != "1") {
    alert("ֻ�ṩ����Ͷ�����ͻ��Ų�ѯ��");
  } else {
    //arrResult = easyExecSql("select * from LDPerson where CustomerNo = '" + fm.all("AppntCustomerNo").value + "'", 1, 0);
     mySql = new SqlClass();
		mySql.setResourceName("sys.AllProShow_BSql");
		mySql.setSqlId("AllProShow_BSql9");
		mySql.addSubPara( fm.all("AppntCustomerNo").value); 
		 arrResult = easyExecSql(mySql.getString(), 1, 0);
    if (arrResult == null) {
      alert("δ�鵽Ͷ������Ϣ");
      displayAppnt(new Array());
      emptyUndefined(); 
    } else {
      displayAppnt(arrResult[0]);
    }
  }
}

//*************************************************************
//Ͷ�����뱻������ͬѡ����¼�
var stackAppnt = new Array();
function isSamePerson() {
  showPage(this,divLCAppntInd1); 

  if (fm.all('divLCAppntInd1').style.display == "none") {
    for (var elementsNum=0; elementsNum<fm.elements.length; elementsNum++) {    
  	  if (fm.elements[elementsNum].verify != null && fm.elements[elementsNum].name.indexOf("Appnt") != -1) {
  	    stackAppnt.push(fm.elements[elementsNum].verify);
  	    fm.elements[elementsNum].verify = "";
  	  }
  	} 
  } else {
    //alert(stackAppnt);
    for (var elementsNum=0; elementsNum<fm.elements.length; elementsNum++) {    
  	  if (fm.elements[elementsNum].verify != null && fm.elements[elementsNum].name.indexOf("Appnt") != -1) { 	    
  	    fm.elements[elementsNum].verify = stackAppnt.shift();
  	  }
  	} 
  }
}  

/*********************************************************************
 *  �������е�������ʾ��Ͷ���˲���
 *  ����  ��  ���˿ͻ�����Ϣ
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayAppnt(cArr) {
try {
	fm.all( 'AppntCustomerNo' ).value          = cArr[0];
	fm.all( 'AppntName' ).value                = cArr[2];
	fm.all( 'AppntSex' ).value                 = cArr[3];
	fm.all( 'AppntBirthday' ).value            = cArr[4];
	fm.all( 'AppntAge' ).value                 = "i don't know";
	fm.all( 'AppntIDType' ).value              = cArr[16];
	fm.all( 'AppntIDNo' ).value                = cArr[18];
	fm.all( 'AppntNativePlace' ).value         = cArr[5];
	fm.all( 'AppntRgtAddress' ).value          = cArr[54];
	fm.all( 'AppntMarriage' ).value            = cArr[7];
	fm.all( 'AppntNationality' ).value         = cArr[6];
	fm.all( 'AppntDegree' ).value              = cArr[51];
	fm.all( 'AppntSmokeFlag' ).value           = cArr[53];
	fm.all( 'AppntPostalAddress' ).value       = cArr[24];
	fm.all( 'AppntZipCode' ).value             = cArr[25];
	fm.all( 'AppntPhone' ).value               = cArr[26];
	fm.all( 'AppntMobile' ).value              = cArr[28];
	fm.all( 'AppntEMail' ).value               = cArr[29];
	fm.all( 'AppntGrpName' ).value             = cArr[35];
	fm.all( 'AppntGrpPhone' ).value            = cArr[36];
	fm.all( 'AppntGrpAddress' ).value          = cArr[38];
	fm.all( 'AppntGrpZipCode' ).value          = cArr[52];
	fm.all( 'AppntWorkType' ).value            = cArr[48];
	fm.all( 'AppntPluralityType' ).value       = cArr[49];
	fm.all( 'AppntOccupationType' ).value      = cArr[9];
	fm.all( 'AppntOccupationCode' ).value      = cArr[50];
} catch(ex) {
  alert("displayAppnt err:" + ex + "\ndata is:" + cArr);
}
//  fm.all('RelationToInsured').value = '';
}

/*********************************************************************
 *  �������е�������ʾ��Ͷ���˲���
 *  ����  ��  ����ͻ�����Ϣ
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayAppntGrp( cArr )
{
	fm.all( 'AppntCustomerNo' ).value = cArr[0];
	fm.all( 'AppntName' ).value = cArr[2];
	fm.all( 'AppntSex' ).value = cArr[3];
	fm.all( 'AppntBirthday' ).value = cArr[4];
	fm.all( 'AppntIDType' ).value =cArr[19];
	fm.all( 'AppntIDNo' ).value = cArr[18];
//  fm.all('RelationToInsured').value = '';
	fm.all('AppntPhone').value = cArr[26];
	fm.all('AppntMobile').value = cArr[28];
	fm.all('AppntPostalAddress').value = cArr[24];
	fm.all('AppntZipCode').value = cArr[25];
	fm.all('AppntEMail').value = cArr[29];
}

/*********************************************************************
 *  �Ѳ�ѯ���صĿͻ�������ʾ�������˲���
 *  ����  ��  �ͻ�����Ϣ
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayInsured(cArr) {
  
try{    fm.all( 'CustomerNo' ).value          = cArr[0];}catch(ex){}
try{    fm.all( 'Name' ).value                = cArr[2];}catch(ex){}
try{    fm.all( 'Sex' ).value                 = cArr[3];}catch(ex){}
try{    fm.all( 'Birthday' ).value            = cArr[4];}catch(ex){}
        //try{  fm.all( 'Age' ).value                 = "i don't know";}catch(ex){}
try{    fm.all( 'IDType' ).value              = cArr[16];}catch(ex){}
try{	fm.all( 'IDNo' ).value                = cArr[18];}catch(ex){}
try{	fm.all( 'NativePlace' ).value         = cArr[5];}catch(ex){}
try{	fm.all( 'RgtAddress' ).value          = cArr[54];}catch(ex){}
try{	fm.all( 'Marriage' ).value            = cArr[7];}catch(ex){}
try{	fm.all( 'Nationality' ).value         = cArr[6];}catch(ex){}
try{	fm.all( 'Degree' ).value              = cArr[51];}catch(ex){}
try{	fm.all( 'SmokeFlag' ).value           = cArr[53];}catch(ex){}
try{	fm.all( 'PostalAddress' ).value       = cArr[24];}catch(ex){}
try{	fm.all( 'ZipCode' ).value             = cArr[25];}catch(ex){}
try{	fm.all( 'Phone' ).value               = cArr[26];}catch(ex){}
try{	fm.all( 'Mobile' ).value              = cArr[28];}catch(ex){}
try{	fm.all( 'EMail' ).value               = cArr[29];}catch(ex){}
try{	fm.all( 'GrpName' ).value             = cArr[35];}catch(ex){}
try{	fm.all( 'GrpPhone' ).value            = cArr[36];}catch(ex){}
try{	fm.all( 'GrpAddress' ).value          = cArr[38];}catch(ex){}
try{	fm.all( 'GrpZipCode' ).value          = cArr[52];}catch(ex){}
try{	fm.all( 'WorkType' ).value            = cArr[48];}catch(ex){}
try{	fm.all( 'PluralityType' ).value       = cArr[49];}catch(ex){}
try{	fm.all( 'OccupationType' ).value      = cArr[9];}catch(ex){}
try{	fm.all( 'OccupationCode' ).value      = cArr[50];}catch(ex){}	
//  fm.all('RelationToInsured').value = '';
}

//*********************************************************************
function showAppnt1()
{
	if( mOperate == 0 )
	{
		mOperate = 2;
		showInfo = window.open( "../sys/LDPersonQuery.html","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes" );
	}
}           

//*********************************************************************
function showInsured1()
{
	if( mOperate == 0 )
	{
		mOperate = 3;
		showInfo = window.open( "../sys/LDPersonQuery.html","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes" );
	}
}  

function isSamePersonQuery() {
//  divSamePerson.style.display = '';
  divLCAppntInd1.style.display = '';
}
