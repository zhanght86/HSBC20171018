//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var approvefalg;
var arrResult;
var arrResult1;
var mDebug = "0";
var mOperate = 0;
var mAction = "";
var mSwitch = parent.VD.gVSwitch;
var mShowCustomerDetail = "GROUPPOL";
var turnPage = new turnPageClass();
this.window.onfocus=myonfocus;
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var sqlresourcename = "appgrp.BankContInputSql";

//��¼��Ҫ¼������ʱ������ֵ
//var theFirstValue="";
//var theSecondValue="";

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
//ʹ�ôӸô��ڵ����Ĵ����ܹ��۽�


/*********************************************************************
 *  ���漯��Ͷ�������ύ 
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function submitForm()
{   
//	alert(fm.PolAppntDate.value);
//        return;
///////////////add by yaory
//alert(fm.PolAppntDate.value.substring(4,5));
if(fm.PolAppntDate.value.length!=10 || fm.PolAppntDate.value.substring(4,5)!='-' || fm.PolAppntDate.value.substring(7,8)!='-' || fm.PolAppntDate.value.substring(0,1)=='0')
{
alert("��������ȷ�����ڸ�ʽ��");
fm.PolAppntDate.focus();
return;
}
///////end add
	if(fm.AppntIDType.value!=""&&fm.AppntIDNo.value==""||fm.AppntIDType.value==""&&fm.AppntIDNo.value!=""){
		alert("֤�����ͺ�֤���������ͬʱ��д����");
		return false;
		}
//	if((fm.PayMode.value=="4"||fm.PayMode.value=="7")&&fm.AppntBankCode.value==""&&fm.AppntAccName.value==""&&fm.AppntBankAccNo.value=="")
//	{
//
//		alert("����������ת�ʿ����С������ʻ������������ʻ��ţ�");
//		return false;
//	}
//	if(fm.SecPayMode.value=="3"&&fm.SecAppntBankCode.value==""&&fm.SecAppntAccName.value==""&&fm.SecAppntBankAccNo.value=="")
//	{
//
//		alert("����������ת�ʿ����С������ʻ������������ʻ��ţ�");
//		return false;
//	}
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
  //      if (verifyInput2()== false) {
	//  if (!confirm("Ͷ����¼������д����Ƿ�������棿")) return false;
  //      }
  //alert("123456789,,mAction=="+mAction);
	if( mAction == "" )
	{
		//showSubmitFrame(mDebug);
		mAction = "INSERT";
		document.all( 'fmAction' ).value = mAction;
		ImpartGrid.delBlankLine();

		if (document.all('ProposalContNo').value == "") {
		  //alert("��ѯ���ֻ�ܽ����޸Ĳ�����");
		  mAction = "";
		} else {
		 // showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
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
 *  �������Ͷ�������ύ��Ĳ���,���������ݷ��غ�ִ�еĲ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
	//alert("here 1!");
	//showInfo.close();
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
		//showDiv(inputButton, "false");
		//alert("djfdjfldj")
		//window.top.parent.fraInterface.initForm();
		//alert(12313)
		//top.close();
		//alert("xingxing")


		if(approvefalg=="2")
		{
			//top.close();
		}
      top.close();
//	  try { goToPic(2) } catch(e) {}

	}
	mAction = "";
}



function afterSubmit4( FlagStr, content )
{
	//alert("here 1!");
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
		content = "�����ɹ���";
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
		//showDiv(inputButton, "false");
		//alert("djfdjfldj")
		//window.top.parent.fraInterface.initForm();
		//alert(12313)
		//top.close();
		//alert("xingxing")


		if(approvefalg=="2")
		{
			//top.close();
		}


	}
	mAction = "";

}
function afterSubmit5( FlagStr, content )
{
	//alert("here 1!");
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
		content = "�����ɹ���";
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

		top.close();


		if(approvefalg=="2")
		{
			//top.close();
		}


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
		document.all('PrtNo').value = prtNo;
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
/*��ʱ���Σ���ȫ������������	document.all('RiskCode').value = "";

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

    document.all('RiskCode').value = BQRiskCode;
    document.all('RiskCode').className = "readonly";
    document.all('RiskCode').readOnly = true;
    document.all('RiskCode').ondblclick = "";
  }
	document.all('ContNo').value = "";
	document.all('GrpProposalNo').value = "";*/
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
 *  Click�¼�����������޸ġ�ͼƬʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function updateClick()
{
	//alert(fm.Remark.value);
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

	var tAppntNo = fm.AppntNo.value;
	var tAppntName = fm.AppntName.value;
	fm.ContNo.value=fm.ProposalContNo.value;
	if( fm.ContNo.value == "" )
	{
		alert("��������¼���ͬ��Ϣ���ܽ��뱻��������Ϣ���֡�");
		return false;
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
	if (wFlag ==1 ) //¼�����ȷ��
	{
    //alert(ScanFlag);
   // var tStr= "	select * from lwmission where 1=1 and lwmission.activityid in ('0000001001','0000001002','0000001220','0000001230') and lwmission.missionprop1 = '"+fm.ContNo.value+"'";
		
		
		var sqlid1="BankContInputSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(fm.ContNo.value);
		
		turnPage.strQueryResult = easyQueryVer3(mySql1.getString(), 1, 0, 1);
		if (turnPage.strQueryResult) {
		  alert("�ú�ͬ�Ѿ��������棡");
		  return;
		}
		if(document.all('ProposalContNo').value == "")
	  {
	    alert("��ͬ��Ϣδ����,������������ [¼�����] ȷ�ϣ�");
	   	return;
	  }
    if(ScanFlag=="1"){
		  fm.WorkFlowFlag.value = "0000001099";
    }
    else{
		  fm.WorkFlowFlag.value = "0000001098";
	  }
		fm.MissionID.value = tMissionID;
		fm.SubMissionID.value = tSubMissionID;			//¼�����
  }
  else if (wFlag ==2)//�������ȷ��
  {
  	if(document.all('ProposalContNo').value == "")
	   {
	   	  alert("δ��ѯ����ͬ��Ϣ,������������ [�������] ȷ�ϣ�");
	   	  return;
	   }
		fm.WorkFlowFlag.value = "0000001001";					//�������
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
		fm.WorkFlowFlag.value = "0000001002";					//�����޸����
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
		fm.WorkFlowFlag.value = "0000001021";					//�����޸�
		fm.MissionID.value = tMissionID;
		fm.SubMissionID.value = tSubMissionID;
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

	if( arrQueryResult != null ) {
		arrResult = arrQueryResult;

		if( mOperate == 1 ){		// ��ѯͶ����
			document.all( 'ContNo' ).value = arrQueryResult[0][0];
		//	arrResult = easyExecSql("select ProposalContNo,PrtNo,ManageCom,SaleChnl,AgentCode,AgentGroup,AgentCode1,AgentCom,AgentType,Remark from LCCont where LCCont = '" + arrQueryResult[0][0] + "'", 1, 0);

	var sqlid2="BankContInputSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(arrQueryResult[0][0]);

arrResult = easyExecSql(mySql2.getString(), 1, 0);

			if (arrResult == null) {
			  alert("δ�鵽Ͷ������Ϣ");
			} else {
			   displayLCContPol(arrResult[0]);
			}
		}

		if( mOperate == 2 )	{		// Ͷ������Ϣ
			//alert("arrQueryResult[0][0]=="+arrQueryResult[0][0]);
		//arrResult = easyExecSql("select a.*  from LDPerson a where 1=1  and a.CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0);
		
		var sqlid3="BankContInputSql3";
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
	}

	mOperate = 0;		// �ָ���̬

	showCodeName();

}
/*********************************************************************
 *  Ͷ������Ϣ��ʼ����������loadFlag��־��Ϊ��֧
 *  ����  ��  Ͷ����ӡˢ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function detailInit(PrtNo){
  try{

    if(PrtNo==null) return;

    //��ѯ�����˺�
    //alert("PrtNo=="+PrtNo);
    /*
    var accSQL = "select a.BankCode,a.AccName,a.BankAccNo from LJTempFeeClass a,LJTempFee b "
               + "where trim(a.TempFeeNo)=trim(b.TempFeeNo) and a.PayMode='4' and b.TempFeeType='1'"
               + "and b.OtherNo='"+PrtNo+"' and b.operstate='0' ";
*/
var sqlid4="BankContInputSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName(sqlresourcename);
		mySql4.setSqlId(sqlid4);
		mySql4.addSubPara(PrtNo);

    arrResult = easyExecSql(mySql4.getString(),1,0);

    if(arrResult==null){
    	//return;
    	//Ĭ��Ϊ����Ϊ����ת��
//    	alert("aaa");
//      document.all('PayMode').value="3";
    }
    else{
      displayFirstAccount();
      //������ڽ��ѷ�ʽΪ����ת�ˣ�����ͬ��Ϊ����ת��
      document.all('PayMode').value="";
    }


   // arrResult=easyExecSql("select * from LCCont where PrtNo='"+PrtNo+"'",1,0);

var sqlid5="BankContInputSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName(sqlresourcename);
		mySql5.setSqlId(sqlid5);
		mySql5.addSubPara(PrtNo);

arrResult=easyExecSql(mySql5.getString(),1,0);
    if(arrResult==null){
      alert(δ�õ�Ͷ������Ϣ);
      return;
    }
    else{
      displayLCCont();       //��ʾͶ������ϸ����
    }


   // arrResult = easyExecSql("select a.* from LDPerson a where 1=1  and a.CustomerNo = '" + arrResult[0][19] + "'", 1, 0);

var sqlid6="BankContInputSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName(sqlresourcename);
		mySql6.setSqlId(sqlid6);
		mySql6.addSubPara(arrResult[0][19]);
arrResult = easyExecSql(mySql6.getString(), 1, 0);


    if (arrResult == null) {
      alert("δ�鵽Ͷ���˿ͻ�����Ϣ");
    }
    else{
      //��ʾͶ������Ϣ
      displayAppnt(arrResult[0]);
    }

 //   arrResult=easyExecSql("select * from LCAppnt where PrtNo='"+PrtNo+"'",1,0);

var sqlid7="BankContInputSql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName(sqlresourcename);
		mySql7.setSqlId(sqlid7);
		mySql7.addSubPara(PrtNo);

arrResult=easyExecSql(mySql7.getString(),1,0);
    if(arrResult==null){
      alert("δ�õ�Ͷ���˱�������Ϣ");
      return;
    }else{
      displayContAppnt();       //��ʾͶ���˵���ϸ����
    }
    getAge();
    var tContNo = arrResult[0][1];
    var tCustomerNo = arrResult[0][3];		// �õ�Ͷ���˿ͻ���
    var tAddressNo = arrResult[0][9]; 		// �õ�Ͷ���˵�ַ��

    //alert("--tContNo=="+tContNo+"--tCustomerNo--"+tCustomerNo+"--tAddressNo--"+tAddressNo);


/**************
    arrResult=easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo='"+tCustomerNo+"'",1,0);
    if(arrResult==null){
      alert("δ�õ��û���Ϣ");
      return;
    }else{
      displayAppnt();       //��ʾͶ������ϸ����
      emptyUndefined();
    }
******************/
/******************
    arrResult=easyExecSql("select * from LCAddress where AddressNo='"+tAddressNo+"' and CustomerNo='"+tCustomerNo+"'",1,0);
    if(arrResult==null){
      alert(δ�õ�Ͷ���˵�ַ��Ϣ);
      return;
    }else{
      displayAddress();       //��ʾͶ���˵�ַ��ϸ����
    }

*********************/

    fm.AppntAddressNo.value=tAddressNo;

    //��ѯ����ʾͶ���˵�ַ��ϸ����
    getdetailaddress();

    //alert("zzz");
    //��ѯͶ���˸�֪��Ϣ
  //  var strSQL0="select ImpartParam from LCCustomerImpartparams where CustomerNoType='0' and CustomerNo='"+tCustomerNo+"' and ContNo='"+tContNo+"' and impartver='01' and impartcode='001' and ImpartParamNo = '1'";
  //  var strSQL1="select ImpartParam from LCCustomerImpartparams where CustomerNoType='0' and CustomerNo='"+tCustomerNo+"' and ContNo='"+tContNo+"' and impartver='01' and impartcode='001' and ImpartParamNo = '2'";
    
    var sqlid8="BankContInputSql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName(sqlresourcename);
		mySql8.setSqlId(sqlid8);
		mySql8.addSubPara(tCustomerNo);
		mySql8.addSubPara(tContNo);
    
    arrResult = easyExecSql(mySql8.getString() ,1,0);
    
     var sqlid9="BankContInputSql9";
		var mySql9=new SqlClass();
		mySql9.setResourceName(sqlresourcename);
		mySql9.setSqlId(sqlid9);
		mySql9.addSubPara(tCustomerNo);
		mySql9.addSubPara(tContNo);
    
    arrResult1 = easyExecSql(mySql9.getString(),1,0);


	    try{document.all('Income0').value= arrResult[0][0];}catch(ex){};
	    try{document.all('IncomeWay0').value= arrResult1[0][0];}catch(ex){};

   // var strSQL1="select ImpartVer,ImpartCode,ImpartContent,ImpartParamModle from LCCustomerImpart where CustomerNoType='0' and CustomerNo='"+tCustomerNo+"' and ContNo='"+tContNo+"' and impartver in ('01','02') and impartcode<>'001'";

var sqlid10="BankContInputSql10";
		var mySql10=new SqlClass();
		mySql10.setResourceName(sqlresourcename);
		mySql10.setSqlId(sqlid10);
		mySql10.addSubPara(tCustomerNo);
		mySql10.addSubPara(tContNo);


    turnPage.strQueryResult = easyQueryVer3(mySql10.getString(), 1, 0, 1);

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

    //����з�֧
    if(loadFlag=="5"||loadFlag=="25"){
      //showCodeName();
    }
    //alert("tContNo=="+tContNo);

/***********************************����ȡ����ҵ��Ա��ȡ��
    //��ѯ���ж�ҵ��Ա��Ϣ
    var muliAgentSQL="select c.agentcode,d.name,d.managecom,b.name,c.busirate,a.agentgroup,b.branchattr "+
                     "from lccont a,labranchgroup b,lacommisiondetail c,laagent d "+
                     "where 1=1 "+
                     "and trim(a.contno)='" + tContNo + "' " +
                     "and trim(b.agentgroup)=trim(c.agentgroup) " +
                     "and trim(c.agentcode)!=trim(a.agentcode) " +
                     "and trim(d.agentcode)=trim(c.agentcode) " +
                     "and trim(d.agentcode)!=trim(a.agentcode) " +
                     "and trim(c.grpcontno)=trim(a.contno) "
                     ;
    //alert("--muliAgentSQL="+muliAgentSQL);
    turnPage.strQueryResult = easyQueryVer3(muliAgentSQL, 1, 0, 1);

    //alert("--turnPage.strQueryResult=="+turnPage.strQueryResult);
    //�ж��Ƿ��ѯ�ɹ�,�ɹ�����ʾ��֪��Ϣ
    if (turnPage.strQueryResult) {
      //��ѯ�ɹ������ַ��������ض�ά����
      turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
      //���ó�ʼ������MULTILINE����
      turnPage.pageDisplayGrid = MultiAgentGrid;
      //����SQL���
      turnPage.strQuerySql = muliAgentSQL;
      //���ò�ѯ��ʼλ��
      turnPage.pageIndex = 0;
      //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
      arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
      //����MULTILINE������ʾ��ѯ���
      displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
      //��ʾ��ҵ��Ա
      displayMultiAgent();
    }
    else{
      //��ʼ����ҵ��Ա�б�
      //alert("saafas");
      initMultiAgentGrid();
    }
********************************************/
/***********************************/
    //��ѯ��ҵ��Ա��Ϣ
//    arrResult=easyExecSql("select c.agentcode,d.name,d.managecom,b.name,c.busirate,a.agentgroup,b.branchattr " +
//                          "from lccont a,labranchgroup b,lacommisiondetail c,laagent d " +
//                          "where 1=1 " +
//                          "and trim(a.contno)='" + tContNo + "' "+
//                          "and trim(b.agentgroup)=trim(c.agentgroup) " +
//                          "and trim(c.agentcode)=trim(a.agentcode) " +
//                          "and trim(d.agentcode)=trim(a.agentcode) " +
//                          "and trim(c.grpcontno)=trim(a.contno)",1,0);
//
//    if(arrResult==null){
//      //alert("δ�õ���ҵ��Ա��Ϣ");
//      //return;
//    }else{
//      displayMainAgent();       //��ʾ��ҵ��Ա����ϸ����
//    }
/**************************************/
//alert();
 //////////////////////////////////////////
 //��ѯҵ��Ա��Ϣ
 //queryAgent(); 

//////////////////////////////////////////////
//��ѯҵ��Ա��֪


  // var aSQL="select ImpartVer,ImpartCode,ImpartContent,ImpartParamModle from LCCustomerImpart where CustomerNoType='2' and CustomerNo='"+tCustomerNo+"' and ContNo='"+tContNo+"'";
   
   var sqlid11="BankContInputSql11";
		var mySql11=new SqlClass();
		mySql11.setResourceName(sqlresourcename);
		mySql11.setSqlId(sqlid11);
		mySql11.addSubPara(tCustomerNo);
		mySql11.addSubPara(tContNo);
   
   turnPage.queryModal(mySql11.getString(), AgentImpartGrid);


//////////////////////////////////////////////////




  }
  catch(ex){}

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
//try{document.all('GrpName').value= arrResult[0][6];}catch(ex){};
try{document.all('AppntGrpPhone').value= arrResult[0][6];}catch(ex){};
try{document.all('CompanyAddress').value= arrResult[0][7];}catch(ex){};
try{document.all('AppntGrpZipCode').value= arrResult[0][8];}catch(ex){};
/*
        try{document.all('AppntPostalAddress').value= arrResult[0][2]; }catch(ex){};
	try{document.all('AppntZipCode').value= arrResult[0][3]; }catch(ex){};
	try{document.all('AppntPhone').value= arrResult[0][4]; }catch(ex){};
	try{document.all('AppntMobile').value= arrResult[0][14]; }catch(ex){};
	try{document.all('AppntEMail').value= arrResult[0][16]; }catch(ex){};
	//try{document.all('AppntGrpName').value= arrResult[0][2]; }catch(ex){};
	try{document.all('AppntGrpPhone').value= arrResult[0][12]; }catch(ex){};
	try{document.all('CompanyAddress').value= arrResult[0][10]; }catch(ex){};
	try{document.all('AppntGrpZipCode').value= arrResult[0][11]; }catch(ex){};
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
try{document.all('AppntRemark').value= arrResult[0][33];}catch(ex){};
try{document.all('AppntState').value= arrResult[0][34];}catch(ex){};
try{document.all('AppntVIPValue').value= arrResult[0][35];}catch(ex){};
try{document.all('AppntOperator').value= arrResult[0][36];}catch(ex){};
try{document.all('AppntMakeDate').value= arrResult[0][37];}catch(ex){};
try{document.all('AppntMakeTime').value= arrResult[0][38];}catch(ex){};
try{document.all('AppntModifyDate').value= arrResult[0][39];}catch(ex){};
try{document.all('AppntModifyTime').value= arrResult[0][40];}catch(ex){};
try{document.all('AppntGrpName').value= arrResult[0][41];}catch(ex){};
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
  //  arrResult = easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo = '" + document.all("AppntNo").value + "'", 1, 0);
    
       var sqlid12="BankContInputSql12";
		var mySql12=new SqlClass();
		mySql12.setResourceName(sqlresourcename);
		mySql12.setSqlId(sqlid12);
		mySql12.addSubPara(document.all("AppntNo").value);

    arrResult = easyExecSql(mySql12.getString(), 1, 0);
    
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

function queryAgent()
{
	//alert("document.all('AgentCode').value==="+document.all('AgentCode').value);
	if(document.all('ManageCom').value==""){
		 alert("����¼����������Ϣ��");
		 return;
	}
  if(document.all('AgentCode').value == "")	{
	  //var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+",AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	  //���ղ�ѯҵ��Աbranchtype = '2'
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+"&branchtype=2","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}
	if(document.all('AgentCode').value != ""){
	  var cAgentCode = fm.AgentCode.value;  //��������
    //alert("cAgentCode=="+cAgentCode);
    //���ҵ��Ա���볤��Ϊ8���Զ���ѯ����Ӧ�Ĵ������ֵ���Ϣ
    //alert("cAgentCode=="+cAgentCode);
    if(cAgentCode.length!=8){
    	return;
    }
	  //var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"' and ManageCom = '"+document.all('ManageCom').value+"'";
//   	var strSQL = "select a.AgentCode,a.AgentGroup,a.ManageCom,a.Name,c.BranchManager,b.IntroAgency,b.AgentSeries,b.AgentGrade,c.BranchAttr,b.AscriptSeries,c.name from LAAgent a,LATree b,LABranchGroup c where 1=1 "
	//         + "and a.AgentCode = b.AgentCode and a.AgentGroup = c.AgentGroup and a.AgentCode='"+cAgentCode+"'";
   
    var sqlid13="BankContInputSql13";
		var mySql13=new SqlClass();
		mySql13.setResourceName(sqlresourcename);
		mySql13.setSqlId(sqlid13);
		mySql13.addSubPara(cAgentCode);
   
   //alert(strSQL);
    var arrResult = easyExecSql(mySql13.getString()); 
    //alert(arrResult);
    if (arrResult != null) {
    	fm.AgentCode.value = arrResult[0][0];
    	fm.BranchAttr.value = arrResult[0][8];
    	fm.AgentGroup.value = arrResult[0][1];
  	  fm.AgentName.value = arrResult[0][3];
      fm.AgentManageCom.value = arrResult[0][2];
      //alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][1]+"]");
    }
    else
    {
     fm.AgentGroup.value="";
     alert("����Ϊ:["+document.all('AgentCode').value+"]��ҵ��Ա�����ڣ���ȷ��!");
    }
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
		window.open("../uwgrp/QuestInputMain.jsp?ContNo="+cContNo+"&Flag="+ LoadFlag,"window1",sFeatures);
	}
}

//��ѯ����ʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
function afterQuery1(arrQueryResult)
{
      //document.all("Donextbutton1").style.display="";
      //document.all("Donextbutton2").style.display="none";
      //document.all("Donextbutton3").style.display="";
      //document.all("butBack").style.display="none";
      //��ϸ��Ϣ��ʼ��
      detailInit(arrQueryResult);

}
//��ѯ����ʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
function afterQuery2(arrResult)
{
  
  if(arrResult!=null)
  {
//  	fm.AgentCode.value = arrResult[0][0];
//  	fm.BranchAttr.value = arrResult[0][93];
//  	fm.AgentGroup.value = arrResult[0][1];
//  	fm.AgentName.value = arrResult[0][5];
//  	fm.AgentManageCom.value = arrResult[0][2];
    	fm.AgentCode.value = arrResult[0][0];
    	fm.BranchAttr.value = arrResult[0][8];
    	fm.AgentGroup.value = arrResult[0][1];
  	  fm.AgentName.value = arrResult[0][3];
      fm.AgentManageCom.value = arrResult[0][2];

  	showContCodeName();
  	//showOneCodeName('comcode','AgentManageCom','ManageComName');

  }
}

//���ز�ѯ�����������ҵ��Աmultline
function afterQuery3(arrResult)
{
  if(arrResult!=null)
  {
		document.all( tField ).all( 'MultiAgentGrid1').value = arrResult[0][0];
		document.all( tField ).all( 'MultiAgentGrid2').value = arrResult[0][3];
		document.all( tField ).all( 'MultiAgentGrid3').value = arrResult[0][2];
		document.all( tField ).all( 'MultiAgentGrid4').value = arrResult[0][8];
		document.all( tField ).all( 'MultiAgentGrid6').value = arrResult[0][1];
		document.all( tField ).all( 'MultiAgentGrid7').value = arrResult[0][6];
  }

}



function queryAgent2()
{
	if(document.all('ManageCom').value==""){
		 alert("����¼����������Ϣ��");
		 return;
	}
	if(document.all('AgentCode').value != "" && document.all('AgentCode').value.length==10 )	 {
	var cAgentCode = fm.AgentCode.value;  //��������
	//var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"' and ManageCom = '"+document.all('ManageCom').value+"'";
  
   var sqlid14="BankContInputSql14";
		var mySql14=new SqlClass();
		mySql14.setResourceName(sqlresourcename);
		mySql14.setSqlId(sqlid14);
		mySql14.addSubPara(cAgentCode);
		mySql14.addSubPara(document.all('ManageCom').value);
  
    var arrResult = easyExecSql(mySql14.getString());
       //alert(arrResult);
    if (arrResult != null) {
      fm.AgentGroup.value = arrResult[0][2];
      alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][1]+"]");
    }
    else{
     fm.AgentGroup.value="";
     alert("����Ϊ:["+document.all('AgentCode').value+"]�Ĵ����˲����ڣ���ȷ��!");
     }
	}
}
function getdetail()
{
//alert("fm.AppntNo.value=="+fm.AppntNo.value);
//var strSql = "select BankCode,AccName from LCAccount where BankAccNo='" + fm.AppntBankAccNo.value+"' and customerno='"+fm.AppntNo.value+"'";

   var sqlid15="BankContInputSql15";
		var mySql15=new SqlClass();
		mySql15.setResourceName(sqlresourcename);
		mySql15.setSqlId(sqlid15);
		mySql15.addSubPara(fm.AppntBankAccNo.value);
		mySql15.addSubPara(fm.AppntNo.value);

var arrResult = easyExecSql(mySql15.getString());
if (arrResult != null) {
      fm.AppntBankCode.value = arrResult[0][0];
      fm.AppntAccName.value = arrResult[0][1];
    }
else{
     //fm.AppntBankCode.value = '';
     //fm.AppntAccName.value = '';
}
}
function getdetailwork()
{
  //alert("fm.AppntOccupationCode.value=="+fm.AppntOccupationCode.value);
  //var strSql = "select OccupationType from LDOccupation where OccupationCode='" + fm.AppntOccupationCode.value+"'";
  
  var sqlid16="BankContInputSql16";
		var mySql16=new SqlClass();
		mySql16.setResourceName(sqlresourcename);
		mySql16.setSqlId(sqlid16);
		mySql16.addSubPara(fm.AppntOccupationCode.value);
  
  
  var arrResult = easyExecSql(mySql16.getString());
  if (arrResult != null) {
    fm.AppntOccupationType.value = arrResult[0][0];

  }
  else{
    fm.AppntOccupationType.value = '';
  }
}

/********************
**
** ��ѯͶ���˵�ַ��Ϣ
**
*********************/
function getdetailaddress(){
 	//var strSQL="select b.* from LCAddress b where b.AddressNo='"+fm.AppntAddressNo.value+"' and b.CustomerNo='"+fm.AppntNo.value+"'";
     
       var sqlid17="BankContInputSql17";
		var mySql17=new SqlClass();
		mySql17.setResourceName(sqlresourcename);
		mySql17.setSqlId(sqlid17);
		mySql17.addSubPara(fm.AppntAddressNo.value);
        mySql17.addSubPara(fm.AppntNo.value);
        arrResult=easyExecSql(mySql17.getString());
   //alert("arrResult[0][1]=="+arrResult[0][1]);
   try{document.all('AppntNo').value= arrResult[0][0];}catch(ex){};
   try{document.all('AppntAddressNo').value= arrResult[0][1];}catch(ex){};
   try{document.all('AppntPostalAddress').value= arrResult[0][2];}catch(ex){};
   try{document.all('AppntZipCode').value= arrResult[0][3];}catch(ex){};
   try{document.all('AppntPhone').value= arrResult[0][4];}catch(ex){};
   try{document.all('AppntFax').value= arrResult[0][5];}catch(ex){};
   try{document.all('AppntHomeAddress').value= arrResult[0][6];}catch(ex){};
   try{document.all('AppntHomeZipCode').value= arrResult[0][7];}catch(ex){};
   try{document.all('AppntHomePhone').value= arrResult[0][8];}catch(ex){};
   try{document.all('AppntHomeFax').value= arrResult[0][9];}catch(ex){};
   try{document.all('CompanyAddress').value= arrResult[0][10];}catch(ex){};
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
   // strsql = "select AddressNo,PostalAddress from LCAddress where CustomerNo ='"+fm.AppntNo.value+"'";
    
     var sqlid18="BankContInputSql18";
		var mySql18=new SqlClass();
		mySql18.setResourceName(sqlresourcename);
		mySql18.setSqlId(sqlid18);
		mySql18.addSubPara(fm.AppntNo.value);
    
    //alert("strsql :" + strsql);
    turnPage.strQueryResult  = easyQueryVer3(mySql18.getString(), 1, 0, 1);
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
    document.all("AppntAddressNo").CodeData=tCodeData;
}
function afterCodeSelect( cCodeName, Field )
{
 //alert("afdasdf");
 if(cCodeName=="GetAddressNo"){
 //	var strSQL="select b.* from LCAddress b where b.AddressNo='"+fm.AppntAddressNo.value+"' and b.CustomerNo='"+fm.AppntNo.value+"'";
       var sqlid19="BankContInputSql19";
		var mySql19=new SqlClass();
		mySql19.setResourceName(sqlresourcename);
		mySql19.setSqlId(sqlid19);
		mySql19.addSubPara(fm.AppntAddressNo.value);
     	mySql19.addSubPara(fm.AppntNo.value); 
        arrResult=easyExecSql(mySql19.getString());
   try{document.all('AppntNo').value= arrResult[0][0];}catch(ex){};
   try{document.all('AppntAddressNo').value= arrResult[0][1];}catch(ex){};
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
   showOneCodeName('province','AppntProvince','AppntProvinceName');
 	 showOneCodeName('city','AppntCity','AppntCityName');
	 showOneCodeName('district','AppntDistrict','AppntDistrictName');
   return;
 }
 if(cCodeName=="paymode"){
   	if(document.all('PayMode').value=="4"){
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
     // var sqlstr="select name,agentgroup from labranchgroup where agentgroup=(select agentgroup from laagent where agentcode='" + tAgentCode+"')" ;
     
      var sqlid20="BankContInputSql20";
		var mySql20=new SqlClass();
		mySql20.setResourceName(sqlresourcename);
		mySql20.setSqlId(sqlid20);
		mySql20.addSubPara(tAgentCode);
     
      arrResult = easyExecSql(mySql20.getString(),1,0);
      if(arrResult==null)
        {
				  alert("ר��Ա��Ϣ��ѯ����");
				  return false;
        }else{
           
           try{document.all('BranchAttr').value= arrResult[0][0];}catch(ex){};
           try{document.all('AgentGroup').value= arrResult[0][1];}catch(ex){};
	           
				}

 	} 
 
 //alert("aaa");
 afterCodeSelect2( cCodeName, Field );
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
		//alert("tMissionID="+tMissionID);
		//alert("tSubMissionID="+tSubMissionID);
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
	 	document.all('AppntPostalAddress').value=document.all('CompanyAddress').value;
                document.all('AppntZipCode').value=document.all('AppntGrpZipCode').value;
                document.all('AppntPhone').value= document.all('AppntGrpPhone').value;
                document.all('AppntFax').value= document.all('AppntGrpFax').value;

	 }
	 else if(fm.CheckPostalAddress.value=="2")
	 {
	 	document.all('AppntPostalAddress').value=document.all('AppntHomeAddress').value;
                document.all('AppntZipCode').value=document.all('AppntHomeZipCode').value;
                document.all('AppntPhone').value= document.all('AppntHomePhone').value;
                document.all('AppntFax').value= document.all('AppntHomeFax').value;
	 }
	 else if(fm.CheckPostalAddress.value=="3")
	 {
	 	document.all('AppntPostalAddress').value="";
                document.all('AppntZipCode').value="";
                document.all('AppntPhone').value= "";
                document.all('AppntFax').value= "";
	 }
}
function AppntChk()
{
	var Sex=fm.AppntSex.value;
	//var i=Sex.indexOf("-");
	//Sex=Sex.substring(0,i);
	/*
  var sqlstr="select * from ldperson where Name='"+fm.AppntName.value
             + "' and Sex='"+Sex+"' and Birthday='"+fm.AppntBirthday.value
             + "' and CustomerNo<>'"+fm.AppntNo.value+"'"
             + " union select * from ldperson where IDType = '"+fm.AppntIDType.value
             + "' and IDType is not null and IDNo = '"+fm.AppntIDNo.value
             + "' and IDNo is not null and CustomerNo<>'"+fm.AppntNo.value+"'" ;
     */  
        var sqlid21="BankContInputSql21";
		var mySql21=new SqlClass();
		mySql21.setResourceName(sqlresourcename);
		mySql21.setSqlId(sqlid21);
		mySql21.addSubPara(fm.AppntName.value);
		mySql21.addSubPara(Sex);
		mySql21.addSubPara(fm.AppntBirthday.value);
		mySql21.addSubPara(fm.AppntNo.value);
		mySql21.addSubPara(fm.AppntIDType.value);
		mySql21.addSubPara(fm.AppntIDNo.value);
		mySql21.addSubPara(fm.AppntIDNo.value);
		mySql21.addSubPara(fm.AppntNo.value);
       
        arrResult = easyExecSql(mySql21.getString(),1,0);
        if(arrResult==null)
        {
				  alert("��û�����Ͷ�������ƵĿͻ�,����У��");
				  return false;
        }else{

					window.open("../uwgrp/AppntChkMain.jsp?ProposalNo1="+fm.ContNo.value+"&Flag=A&LoadFlag="+LoadFlag,"window1",sFeatures);
				}
}

//�ڳ�ʼ��bodyʱ�Զ�Ч��Ͷ������Ϣ��
//�ж�ϵͳ���Ƿ�����������Ա𡢳���������ͬ��֤������֤��������ͬ�ı�������Ϣ��
function AppntChkNew(){
  //alert("aa");
  var Sex=fm.AppntSex.value;
  /*
  var sqlstr="select * from ldperson where Name='"+fm.AppntName.value
             + "' and Sex='"+Sex+"' and Birthday='"+fm.AppntBirthday.value
             + "' and CustomerNo<>'"+fm.AppntNo.value+"'"
             + " union select * from ldperson where IDType = '"+fm.AppntIDType.value
             + "' and IDType is not null and IDNo = '"+fm.AppntIDNo.value
             + "' and IDNo is not null and CustomerNo<>'"+fm.AppntNo.value+"'" ;
  //alert("sqlstr="+sqlstr);
  */
var sqlid22="BankContInputSql22";
		var mySql22=new SqlClass();
		mySql22.setResourceName(sqlresourcename);
		mySql22.setSqlId(sqlid22);
		mySql22.addSubPara(fm.AppntName.value);
		mySql22.addSubPara(Sex);
		mySql22.addSubPara(fm.AppntBirthday.value);
		mySql22.addSubPara(fm.AppntNo.value);
		mySql22.addSubPara(fm.AppntIDType.value);
		mySql22.addSubPara(fm.AppntIDNo.value);
		mySql22.addSubPara(fm.AppntIDNo.value);
		mySql22.addSubPara(fm.AppntNo.value);  
  
  arrResult = easyExecSql(mySql22.getString(),1,0);
  if(arrResult==null)
  {
  	//disabled"Ͷ����Ч��"��ť   //�ж��Ƿ�����ظ������ˣ�
		fm.AppntChkButton.disabled = true;
		fm.AppntChkButton2.disabled = true;

  }
  else{
  	fm.AppntChkButton.disabled = "";
  	fm.AppntChkButton2.disabled = "";
	}
}

function checkidtype()
{
	//alert("haha!");
	if(fm.AppntIDType.value==""&&fm.AppntIDNo.value!="")
	{
		alert("����ѡ��֤�����ͣ�");
		fm.AppntIDNo.value="";
  }
}
/*********************************************************************
 *  �������֤��ȡ�ó������ں��Ա�
 *  ����  ��  ���֤��
 *  ����ֵ��  ��
 *********************************************************************
 */

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
function getCompanyCode()
{
    var strsql = "";
    var tCodeData = "";
   // strsql = "select CustomerNo,GrpName from LDgrp ";
    
    var sqlid23="BankContInputSql23";
		var mySql23=new SqlClass();
		mySql23.setResourceName(sqlresourcename);
		mySql23.setSqlId(sqlid23);
		mySql23.addSubPara("1");
 
    
    
    document.all("AppntGrpNo").CodeData=tCodeData+easyQueryVer3(strsql, 1, 0, 1);
}
function haveMultiAgent(){
	//alert("aa����"+document.all("multiagentflag").checked);
  if(document.all("multiagentflag").checked){
  	DivMultiAgent.style.display="";
  }
  else{
    DivMultiAgent.style.display="none";
  }

}

//Muline ����Ӷ����� parm1
function queryAgentGrid(Field)
{
	//alert("Field=="+Field);
	tField=Field;
	if(document.all('ManageCom').value==""){
		 alert("����¼����������Ϣ��");
		 return;
	}
	if(document.all(Field).all('MultiAgentGrid1').value==""){
	//�����²�ѯҵ��Աbranchtype='2' modify by liuqh 2008-12-02
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom=&queryflag=1&branchtype=2","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}

	if(document.all( Field ).all('MultiAgentGrid1').value != "")	 {
	var cAgentCode = document.all( Field ).all('MultiAgentGrid1').value;  //��������
//	var strSql = "select AgentCode from LAAgent where AgentCode='" + cAgentCode +"'";
   
    var sqlid24="BankContInputSql24";
		var mySql24=new SqlClass();
		mySql24.setResourceName(sqlresourcename);
		mySql24.setSqlId(sqlid24);
		mySql24.addSubPara(cAgentCode);
   
    var arrResult = easyExecSql(mySql24.getString());
       //alert(arrResult);
    if (arrResult == null) {
      alert("����Ϊ:["+document.all( Field ).all('MultiAgentGrid1').value+"]�Ĵ����˲����ڣ���ȷ��!");
    }
  }
}


//��ʼ����ҵ��Ա��Ϣ
function displayMainAgent(){
  document.all("BranchAttr").value = arrResult[0][3];
  document.all("AgentName").value = arrResult[0][1];
}

function displayMultiAgent(){
   document.all('multiagentflag').checked="true";
   haveMultiAgent();
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


//У��Ͷ������
function checkapplydate(){
  if(fm.PolAppntDate.value.length==8){
  if(fm.PolAppntDate.value.indexOf('-')==-1){ 
  	var Year =     fm.PolAppntDate.value.substring(0,4);
  	var Month =    fm.PolAppntDate.value.substring(4,6);
  	var Day =      fm.PolAppntDate.value.substring(6,8);
  	fm.PolAppntDate.value = Year+"-"+Month+"-"+Day;
  	if(Year=="0000"||Month=="00"||Day=="00"){
     	 alert("�������Ͷ����������!");
   	   fm.PolAppntDate.value = "";  
   	   return;
  	  }
  }
}

else {var Year =     fm.PolAppntDate.value.substring(0,4);
	    var Month =    fm.PolAppntDate.value.substring(5,7);
	    var Day =      fm.PolAppntDate.value.substring(8,10);
	    if(Year=="0000"||Month=="00"||Day=="00"){
     	 alert("�������Ͷ����������!");
   	   fm.PolAppntDate.value = "";
   	   return;
  	     }
  }

}

//У��Ͷ���˳�������
function checkappntbirthday(){
  if(fm.AppntBirthday.value.length==8){
  if(fm.AppntBirthday.value.indexOf('-')==-1){
  	var Year =     fm.AppntBirthday.value.substring(0,4);
  	var Month =    fm.AppntBirthday.value.substring(4,6);
  	var Day =      fm.AppntBirthday.value.substring(6,8);
  	fm.AppntBirthday.value = Year+"-"+Month+"-"+Day;
  	if(Year=="0000"||Month=="00"||Day=="00"){
     	 alert("�������Ͷ���˳�����������!");
   	   fm.AppntBirthday.value = "";
   	   return;
  	  }
  }
}

else {var Year =     fm.AppntBirthday.value.substring(0,4);
	    var Month =    fm.AppntBirthday.value.substring(5,7);
	    var Day =      fm.AppntBirthday.value.substring(8,10);
	    if(Year=="0000"||Month=="00"||Day=="00"){
     	 alert("�������Ͷ���˳�����������!");
   	   fm.AppntBirthday.value = "";
   	   return;
  	     }
  }
}


//У�鱻���˳�������
function checkinsuredbirthday(){
	if(fm.Birthday.value.length==8){
  if(fm.Birthday.value.indexOf('-')==-1){
  	var Year =     fm.Birthday.value.substring(0,4);
  	var Month =    fm.Birthday.value.substring(4,6);
  	var Day =      fm.Birthday.value.substring(6,8);
  	fm.Birthday.value = Year+"-"+Month+"-"+Day;
  	if(Year=="0000"||Month=="00"||Day=="00"){
     	 alert("������ı����˳�����������!");
   	   fm.Birthday.value = "";
   	   return;
  	  }
  }
}

else {var Year =     fm.Birthday.value.substring(0,4);
	    var Month =    fm.Birthday.value.substring(5,7);
	    var Day =      fm.Birthday.value.substring(8,10);
	    if(Year=="0000"||Month=="00"||Day=="00"){
     	 alert("������ı����˳�����������!");
   	   fm.Birthday.value = "";
   	   return;
  	     }
  }
}

//ͨ���������ڼ���Ͷ��������
function getAge(){
	//alert("fm.AppntBirthday.value=="+fm.AppntBirthday.value);
  if(fm.AppntBirthday.value==""){
  	return;
  }
  //alert("fm.AppntBirthday.value.indexOf('-')=="+fm.AppntBirthday.value.indexOf('-'));
  if(fm.AppntBirthday.value.indexOf('-')==-1){
  	var Year =     fm.AppntBirthday.value.substring(0,4);
  	var Month =    fm.AppntBirthday.value.substring(4,6);
  	var Day =      fm.AppntBirthday.value.substring(6,8);
  	fm.AppntBirthday.value = Year+"-"+Month+"-"+Day;
  	if(calAge(fm.AppntBirthday.value)<0)
  	{
      alert("��������ֻ��Ϊ��ǰ������ǰ");
      return;
    }
  	fm.AppntAge.value=calAge(fm.AppntBirthday.value);
    return;
  }
  if(calAge(fm.AppntBirthday.value)<0)
  	{
      alert("��������ֻ��Ϊ��ǰ������ǰ!");
      return;
    }
  fm.AppntAge.value=calAge(fm.AppntBirthday.value);
  return;
}

//ͨ���������ڼ��㱻��������
function getAge2(){
	//alert("fm.Birthday.value=="+fm.Birthday.value);
  if(fm.Birthday.value==""){
  	return;
  }
  //alert("fm.Birthday.value.indexOf('-')=="+fm.Birthday.value.indexOf('-'));
  if(fm.Birthday.value.indexOf('-')==-1){
  	var Year =     fm.Birthday.value.substring(0,4);
  	var Month =    fm.Birthday.value.substring(4,6);
  	var Day =      fm.Birthday.value.substring(6,8);
  	fm.Birthday.value = Year+"-"+Month+"-"+Day;
  	fm.InsuredAppAge.value=calAge(fm.Birthday.value);
    return;
  }
  fm.InsuredAppAge.value=calAge(fm.Birthday.value);
  return;
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

//***********************************************************


function focuswrap()
{
	myonfocus(showInfo1);
}

//�ύ�����水ť��Ӧ����
//function submitForm()
//{
//}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit2( FlagStr, content )
{
	//alert("here 2!");
  try { showInfo.close(); } catch(e) {}
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  if (FlagStr=="Succ" && mWFlag == 0)
  {
  //	if(confirm("�Ƿ����¼�������ͻ���"))
  //	{
	//    if(fm.InsuredSequencename.value=="������������") 
	//    {
	//      //emptyFormElements();
	//      param="122";
	//      fm.pagename.value="122";
	//      fm.SequenceNo.value="2";
  //      fm.InsuredSequencename.value="�ڶ�������������";
  //      if (scantype== "scan")
  //      {
  //      setFocus();
  //      }
  //      noneedhome();
  //      return false;
	//    }
	//    if(fm.InsuredSequencename.value=="�ڶ�������������")
	//    {
	//      //emptyFormElements();
	//      param="123";
	//      fm.pagename.value="123";
	//      fm.SequenceNo.value="3";
  //      fm.InsuredSequencename.value="����������������";
  //      if (scantype== "scan")
  //      {
  //      setFocus();
  //      }
  //      noneedhome();
  //      return false;
	//    }
	//    if(fm.InsuredSequencename.value=="����������������")
	//    {
	//      //emptyFormElements();
	//      param="124";
	//      fm.pagename.value="124";
	//      fm.SequenceNo.value="";
  //      fm.InsuredSequencename.value="���ı�����������";
  //      if (scantype== "scan")
  //      {
  //        setFocus();
  //      }
  //      return false;
	//    }
  //  }
  }

  if(InsuredGrid.mulLineCount>0){
    document.all('spanInsuredGrid0').all('InsuredGridSel').checked='true'
  }

}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit3( FlagStr, content )
{
	//alert("here 2!");
  try { showInfo.close(); } catch(e) {}
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  if (FlagStr=="Succ" && mWFlag == 0)
  {
/****************
  	if(confirm("�Ƿ����¼�������ͻ���"))
  	{
	    if(fm.InsuredSequencename.value=="��һ������������")
	    {
	      //emptyFormElements();
	      param="122";
	      fm.pagename.value="122";
	      fm.SequenceNo.value="2";
        fm.InsuredSequencename.value="�ڶ�������������";
        if (scantype== "scan")
        {
        setFocus();
        }
        noneedhome();
        return false;
	    }
	    if(fm.InsuredSequencename.value=="�ڶ�������������")
	    {
	      //emptyFormElements();
	      param="123";
	      fm.pagename.value="123";
	      fm.SequenceNo.value="3";
        fm.InsuredSequencename.value="����������������";
        if (scantype== "scan")
        {
        setFocus();
        }
        noneedhome();
        return false;
	    }
	    if(fm.InsuredSequencename.value=="����������������")
	    {
	      //emptyFormElements();
	      param="124";
	      fm.pagename.value="124";
	      fm.SequenceNo.value="";
        fm.InsuredSequencename.value="���ı�����������";
        if (scantype== "scan")
        {
          setFocus();
        }
        return false;
	    }
    }
***************************/
  }
  //Ĭ��ѡ���һ������
  document.all('spanInsuredGrid0').all('InsuredGridSel').checked="true";
  getInsuredDetail("spanInsuredGrid0","");
  mSwitch.deleteVar('PolNo');
  mSwitch.deleteVar('SelPolNo');

}



//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
    if(cDebug=="1")
    {
        parent.fraMain.rows = "0,0,50,82,*";
    }
 	else
 	{
        parent.fraMain.rows = "0,0,0,82,*";
 	}
}

//�����¼��
function QuestInput()
{

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
            window.open("../uwgrp/QuestInputMain.jsp?ContNo="+cContNo+"&Flag="+ LoadFlag,"window1",sFeatures);
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
      window.open("../uwgrp/QuestQueryMain.jsp?ContNo="+cContNo+"&Flag="+LoadFlag,"window1",sFeatures);
    }
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
	
	//mSwitch =parent.VD.gVSwitch;
	delInsuredVar();
	addInsuredVar();

  try{mSwitch.addVar('SelPonNo','',fm.SelPolNo.value);}catch(ex){} //ѡ�����ֵ��������ֽ�������ѱ������Ϣ
 
	if ((LoadFlag=='5'||LoadFlag=='4'||LoadFlag=='6'||LoadFlag=='16')&&(mSwitch.getVar( "PolNo" ) == null || mSwitch.getVar( "PolNo" ) == ""))
	{
		alert("����ѡ�񱻱�����������Ϣ��");
		return;
	}
	try{mSwitch.addVar('SelPolNo','',fm.SelPolNo.value);}catch(ex){}
	try{mSwitch.deleteVar('ContNo');}catch(ex){}
	try{mSwitch.addVar('ContNo','',fm.ContNo.value);}catch(ex){}
	try{mSwitch.updateVar('ContNo','',fm.ContNo.value);}catch(ex){}
	try{mSwitch.deleteVar('mainRiskPolNo');}catch(ex){}
	try{mSwitch.addVar('CValiDate','',document.all('PolAppntDate').value);}catch(ex){}
   
//  alert("LoadFlag="+LoadFlag);
//  alert("ContType="+ContType); 
//  alert("scantype="+scantype);
//  
//  alert("MissionID="+MissionID);
//  alert("SubMissionID="+SubMissionID); 
//  alert("BQFlag="+BQFlag);
//  
//  alert("EdorType="+EdorType);
//  alert("checktype="+checktype); 
//  alert("ScanFlag="+ScanFlag); 
   // alert("ActivityID="+ActivityID);
   // alert("NoType="+NoType);    
  parent.fraInterface.window.location = "./ProposalInput.jsp?LoadFlag=" + LoadFlag+"&ContType="+ContType+"&scantype=" + scantype+ "&MissionID=" + MissionID+ "&SubMissionID=" + SubMissionID+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&ActivityID="+ActivityID+"&NoType="+NoType+"&hh=1&checktype="+checktype+"&ProposalContNo="+fm.ProposalContNo.value+"&ScanFlag="+ScanFlag+"&BankFlag=1";
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

//	  var sqlstr="select SequenceNo from LCInsured where PrtNo='"+tPrtNo+"'";

var sqlid25="BankContInputSql25";
		var mySql25=new SqlClass();
		mySql25.setResourceName(sqlresourcename);
		mySql25.setSqlId(sqlid25);
		mySql25.addSubPara(tPrtNo);

    arrResult=easyExecSql(mySql25.getString(),1,0);

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

	//  var sqlstr="select SequenceNo from LCInsured where PrtNo='"+tPrtNo+"'";
var sqlid26="BankContInputSql26";
		var mySql26=new SqlClass();
		mySql26.setResourceName(sqlresourcename);
		mySql26.setSqlId(sqlid26);
		mySql26.addSubPara(tPrtNo);
    arrResult=easyExecSql(mySql26.getString(),1,0);

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

	 // var sqlstr="select SequenceNo from LCInsured where PrtNo='"+tPrtNo+"'";

var sqlid27="BankContInputSql27";
		var mySql27=new SqlClass();
		mySql27.setResourceName(sqlresourcename);
		mySql27.setSqlId(sqlid27);
		mySql27.addSubPara(tPrtNo);

    arrResult=easyExecSql(mySql27.getString(),1,0);

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

//	  var sqlstr="select SequenceNo from LCInsured where PrtNo='"+tPrtNo+"'";

var sqlid28="BankContInputSql28";
		var mySql28=new SqlClass();
		mySql28.setResourceName(sqlresourcename);
		mySql28.setSqlId(sqlid28);
		mySql28.addSubPara(tPrtNo);


    arrResult=easyExecSql(mySql28.getString(),1,0);

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


/***************************************************
  if(LoadFlag==1)
  {
    if(fm.Marriage.value=="")
    {
      alert("����д����״����");
    	return false;
    }
    if(fm.RelationToMainInsured.value=="")
    {
      alert("����д�����������˹�ϵ��");
    	return false;
    }
    if(fm.RelationToAppnt.value=="")
    {
      alert("����д��Ͷ���˹�ϵ����");
    	return false;
    }
  }

*******************************************************/

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
  //end hanlin
  document.all('ContType').value=ContType;
  document.all( 'BQFlag' ).value = BQFlag;
  document.all('fmAction').value="INSERT||CONTINSURED";
  fm.submit();
}

/*********************************************************************
 *  �޸ı�ѡ�еı�������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function modifyRecord()
{
/*
		    var tPrtNo=document.all("PrtNo").value;
      arrResult=easyExecSql("select SequenceNo from LCInsured where PrtNo='"+tPrtNo+"'",1,0);
      for(var sequencenocout=0; sequencenocout<arrResult[0].length;sequencenocout++ )
      {
      if(fm.SequenceNo.value==arrResult[0][sequencenocout]){
	  	alert("�Ѿ����ڸÿͻ��ڲ���");
	  	fm.SequenceNo.focus();
	  	return false;
	  	}
	    }
*/
	  //alert("here!");
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
    if(!checkself())
        return false;
    if (fm.Name.value=='')
    {
        alert("��ѡ����Ҫ�޸ĵĿͻ���")
        return false;
    }
    //alert("SelNo:"+InsuredGrid.getSelNo());
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
    if(ImpartGrid.checkValue2(ImpartGrid.name,ImpartGrid)== false)
    {
      return false;
    }
   // if(ImpartDetailGrid.checkValue2(ImpartDetailGrid.name,ImpartDetailGrid)== false)
   // {
  //    return false;
	//  }
	  ImpartGrid.delBlankLine();
	  //ImpartDetailGrid.delBlankLine();

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
    document.all('ContType').value=ContType;
    document.all('fmAction').value="DELETE||CONTINSURED";
    fm.action="BankContInsuredSave.jsp";
    fm.submit();
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
  	location.href="../appgrp/ContInput.jsp?LoadFlag="+ LoadFlag + "&prtNo=" + document.all("PrtNo").value;
  }
  if(LoadFlag=="5"||LoadFlag=="25")
	{
		//alert(document.all("PrtNo").value);
    location.href="../appgrp/ContInput.jsp?LoadFlag="+ LoadFlag + "&prtNo=" + document.all("PrtNo").value;
  }

	if(LoadFlag=="2")
	{
    location.href="../appgrp/ContGrpInsuredInput.jsp?LoadFlag="+ LoadFlag + "&polNo=" + document.all("GrpContNo").value+"&scantype="+scantype;
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
      parent.fraInterface.window.location = "../appgrp/ContGrpInsuredInput.jsp?LoadFlag="+LoadFlag+"&scantype="+scantype;
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
/*********************************************************************
 *  �������ּƻ�����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
 function grpRiskPlanInfo()
{
    var newWindow = window.open("../appgrp/GroupRiskPlan.jsp","",sFeatures);
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
 *  ��ʾ��ͥ���±������˵���Ϣ
 *  ����ֵ��  ��
 *********************************************************************
 */
function getInsuredInfo()
{
    //alert("hl in getInsuredInfo ContNo="+document.all("ContNo").value);
    //alert("hl in getInsuredInfo GrpName="+document.all("GrpName").value);
    var ContNo=document.all("ContNo").value;
    if(ContNo!=null&&ContNo!="")
    {
 //       var strSQL ="select InsuredNo,Name,Sex,Birthday,RelationToMainInsured,SequenceNo from LCInsured where ContNo='"+ContNo+"'";


var sqlid29="BankContInputSql29";
		var mySql29=new SqlClass();
		mySql29.setResourceName(sqlresourcename);
		mySql29.setSqlId(sqlid29);
		mySql29.addSubPara(ContNo);

        //alert("strSQL=="+strSQL);

        turnPage.strQueryResult  = easyQueryVer3(mySql29.getString(), 1, 0, 1);
        //alert("turnPage==="+turnPage.strQueryResult);
        //�ж��Ƿ��ѯ�ɹ�
        if (!turnPage.strQueryResult)
        {
            return false;
        }
        //��ѯ�ɹ������ַ��������ض�ά����
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        //���ó�ʼ������MULTILINE����
        turnPage.pageDisplayGrid = InsuredGrid;
        //����SQL���
        turnPage.strQuerySql = strSQL;
        //���ò�ѯ��ʼλ��
        turnPage.pageIndex = 0;
        //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
        arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
        //����MULTILINE������ʾ��ѯ���
        displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

        //Ĭ�Ͻ�����ָ���һ������
        //alert("fm.InsuredGrid=="+document.all('spanInsuredGrid0').all('InsuredGridSel').checked);
        document.all('spanInsuredGrid0').all('InsuredGridSel').checked=true;
        getInsuredDetail("spanInsuredGrid0","");
        showInsuredCodeName();

    }
}

/*********************************************************************
 *  ��ø�����ͬ�ı�������Ϣ
 *  ����ֵ��  ��
 *********************************************************************
 */
function getProposalInsuredInfo()
{
    //alert("ContNo=="+document.all("ContNo").value);
    var tContNo=document.all("ContNo").value;
  //  arrResult=easyExecSql("select * from LCInsured where ContNo='"+tContNo+"'",1,0);
  
  var sqlid30="BankContInputSql30";
		var mySql30=new SqlClass();
		mySql30.setResourceName(sqlresourcename);
		mySql30.setSqlId(sqlid30);
		mySql30.addSubPara(tContNo);
  arrResult=easyExecSql(mySql30.getString(),1,0);
    if(arrResult==null||InsuredGrid.mulLineCount>1)
    {
        return;
    }
    else
    {
    	if(InsuredGrid.mulLineCount=1){
        DisplayInsured();//�ú�ͬ�µı�Ͷ������Ϣ
        var tCustomerNo = arrResult[0][2];		// �õ������˿ͻ���
        var tAddressNo = arrResult[0][10]; 		// �õ������˵�ַ��
  //      arrResult=easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo='"+tCustomerNo+"'",1,0);
      
      var sqlid31="BankContInputSql31";
		var mySql31=new SqlClass();
		mySql31.setResourceName(sqlresourcename);
		mySql31.setSqlId(sqlid31);
		mySql31.addSubPara(tCustomerNo);
        arrResult=easyExecSql(mySql31.getString(),1,0);
      
      }
      if(arrResult==null)
      {
          //alert("δ�õ��û���Ϣ");
          //return;
      }
      else
      {
          //displayAppnt();       //��ʾ��������ϸ����
          emptyUndefined();
          fm.InsuredAddressNo.value=tAddressNo;
          getdetailaddress2();//��ʾ�����˵�ַ��ϸ����
      }
    }
    getInsuredPolInfo();
    getImpartInfo();
    getImpartDetailInfo();
}

/*********************************************************************
 *  MulLine��RadioBox����¼�����ñ�������ϸ��Ϣ�����뱻������Ϣ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function getInsuredDetail(parm1,parm2)
{
    //alert("---parm1=="+parm1+"---parm2=="+parm2);

    var InsuredNo=document.all(parm1).all('InsuredGrid1').value;

    var ContNo = fm.ContNo.value;

    //��������ϸ��Ϣ
   // var strSQL ="select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo='"+InsuredNo+"'";

  var sqlid32="BankContInputSql32";
		var mySql32=new SqlClass();
		mySql32.setResourceName(sqlresourcename);
		mySql32.setSqlId(sqlid32);
		mySql32.addSubPara(InsuredNo);

    arrResult=easyExecSql(mySql32.getString());

    if(arrResult!=null)
    {
        //displayAppnt();
        displayInsuredInfo();
    }

  //  strSQL ="select * from LCInsured where ContNo = '"+ContNo+"' and InsuredNo='"+InsuredNo+"'";

var sqlid33="BankContInputSql33";
		var mySql33=new SqlClass();
		mySql33.setResourceName(sqlresourcename);
		mySql33.setSqlId(sqlid33);
		mySql33.addSubPara(ContNo);
mySql33.addSubPara(InsuredNo);
    arrResult=easyExecSql(mySql33.getString());

    if(arrResult!=null)
    {
        DisplayInsured();
    }

    var tAddressNo = arrResult[0][10]; 		// �õ������˵�ַ��
    //alert("arrResult[0][10]=="+arrResult[0][10]);
    fm.InsuredAddressNo.value=tAddressNo;

    //��ʾ�����˵�ַ��Ϣ
    getdetailaddress2();

    getInsuredPolInfo();

    getImpartInfo();

    //getImpartDetailInfo();
    getAge2();
    //��¼�������в���Ҫ���пͻ��ϲ�����ע�͵���hl 20050505
    //����Ǹ���״̬��������ظ��ͻ�У��
    if(LoadFlag=="5"){
      InsuredChkNew();
      return;
    }
}
/*********************************************************************
 *  �Ѳ�ѯ���صĿͻ�������ʾ�������˲���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function DisplayCustomer2()
{
	try{document.all('Nationality').value= arrResult[0][8]; }catch(ex){};

}
/*********************************************************************
 *  �Ѳ�ѯ���صĿͻ���ַ������ʾ�������˲���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function DisplayAddress2()
{
	try{document.all('PostalAddress').value= arrResult[0][2]; }catch(ex){};
	try{document.all('ZipCode').value= arrResult[0][3]; }catch(ex){};
	try{document.all('Phone').value= arrResult[0][4]; }catch(ex){};
	try{document.all('Mobile').value= arrResult[0][14]; }catch(ex){};
	try{document.all('EMail').value= arrResult[0][16]; }catch(ex){};
	//try{document.all('GrpName').value= arrResult[0][2]; }catch(ex){};
	try{document.all('GrpPhone').value= arrResult[0][12]; }catch(ex){};
	try{document.all('CompanyAddress').value= arrResult[0][10]; }catch(ex){};
	try{document.all('GrpZipCode').value= arrResult[0][11]; }catch(ex){};
}
/*********************************************************************
 *  ��ʾ��������ϸ��Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function DisplayInsured()
{
    try{document.all('GrpContNo').value=arrResult[0][0];}catch(ex){};
    try{document.all('ContNo').value=arrResult[0][1];}catch(ex){};
    try{document.all('InsuredNo').value=arrResult[0][2];}catch(ex){};
    try{document.all('PrtNo').value=arrResult[0][3];}catch(ex){};
    try{document.all('AppntNo').value=arrResult[0][4];}catch(ex){};
    try{document.all('ManageCom').value=arrResult[0][5];}catch(ex){};
    try{document.all('ExecuteCom').value=arrResult[0][6];}catch(ex){};
    try{document.all('FamilyID').value=arrResult[0][7];}catch(ex){};
    try{document.all('RelationToMainInsured').value=arrResult[0][8];}catch(ex){};
    try{document.all('RelationToAppnt').value=arrResult[0][9];}catch(ex){};
    try{document.all('InsuredAddressNo').value=arrResult[0][10];}catch(ex){};
    try{document.all('SequenceNo').value=arrResult[0][11];}catch(ex){};
    try{document.all('Name').value=arrResult[0][12];}catch(ex){};
    try{document.all('Sex').value=arrResult[0][13];}catch(ex){};
    try{document.all('Birthday').value=arrResult[0][14];}catch(ex){};
    try{document.all('IDType').value=arrResult[0][15];}catch(ex){};
    try{document.all('IDNo').value=arrResult[0][16];}catch(ex){};
    try{document.all('NativePlace').value=arrResult[0][17];}catch(ex){};
    try{document.all('Nationality').value=arrResult[0][18];}catch(ex){};
    try{document.all('RgtAddress').value=arrResult[0][19];}catch(ex){};
    try{document.all('Marriage').value=arrResult[0][20];}catch(ex){};
    try{document.all('MarriageDate').value=arrResult[0][21];}catch(ex){};
    try{document.all('Health').value=arrResult[0][22];}catch(ex){};
    try{document.all('Stature').value=arrResult[0][23];}catch(ex){};
    try{document.all('Avoirdupois').value=arrResult[0][24];}catch(ex){};
    try{document.all('Degree').value=arrResult[0][25];}catch(ex){};
    try{document.all('CreditGrade').value=arrResult[0][26];}catch(ex){};
    try{document.all('BankCode').value=arrResult[0][27];}catch(ex){};
    try{document.all('BankAccNo').value=arrResult[0][28];}catch(ex){};
    try{document.all('AccName').value=arrResult[0][29];}catch(ex){};
    try{document.all('JoinCompanyDate').value=arrResult[0][30];}catch(ex){};
    try{document.all('StartWorkDate').value=arrResult[0][31];}catch(ex){};
    try{document.all('Position').value=arrResult[0][32];}catch(ex){};
    try{document.all('Salary').value=arrResult[0][33];}catch(ex){};
    try{document.all('OccupationType').value=arrResult[0][34];}catch(ex){};
    try{document.all('OccupationCode').value=arrResult[0][35];}catch(ex){};
    try{document.all('WorkType').value=arrResult[0][36];}catch(ex){};
    try{document.all('PluralityType').value=arrResult[0][37];}catch(ex){};
    try{document.all('SmokeFlag').value=arrResult[0][38];}catch(ex){};
    try{document.all('ContPlanCode').value=arrResult[0][39];}catch(ex){};
    try{document.all('Operator').value=arrResult[0][40];}catch(ex){};
    try{document.all('MakeDate').value=arrResult[0][42];}catch(ex){};
    try{document.all('MakeTime').value=arrResult[0][43];}catch(ex){};
    try{document.all('ModifyDate').value=arrResult[0][44];}catch(ex){};
    try{document.all('ModifyTime').value=arrResult[0][45];}catch(ex){};
    try{document.all('LicenseType').value=arrResult[0][53];}catch(ex){};
}
function displayissameperson()
{
	//alert("here!");
  //alert("document.all( 'AppntName').value="+document.all( "AppntName").value);
��try{document.all('InsuredNo').value= document.all("AppntNo").value;                 }catch(ex){};
��try{document.all('Name').value= document.all("AppntName").value;                    }catch(ex){};
��try{document.all('Sex').value= document.all("AppntSex").value;                      }catch(ex){};
��try{document.all('Birthday').value= document.all("AppntBirthday").value;            }catch(ex){};
��try{document.all('IDType').value= document.all("AppntIDType").value;                }catch(ex){};
��try{document.all('IDNo').value= document.all("AppntIDNo").value;                    }catch(ex){};
��try{document.all('Password').value= document.all( "AppntPassword" ).value;          }catch(ex){};
��try{document.all('NativePlace').value= document.all("AppntNativePlace").value;      }catch(ex){};
��try{document.all('Nationality').value= document.all("AppntNationality").value;      }catch(ex){};
��try{document.all('InsuredAddressNo').value= document.all("AppntAddressNo").value;   }catch(ex){};
��try{document.all('RgtAddress').value= document.all( "AppntRgtAddress" ).value;      }catch(ex){};
��try{document.all('Marriage').value= document.all( "AppntMarriage" ).value;          }catch(ex){};
��try{document.all('MarriageDate').value= document.all( "AppntMarriageDate" ).value;  }catch(ex){};
��try{document.all('Health').value= document.all( "AppntHealth" ).value;              }catch(ex){};
��try{document.all('Stature').value= document.all( "AppntStature" ).value;            }catch(ex){};
��try{document.all('Avoirdupois').value= document.all( "AppntAvoirdupois" ).value;    }catch(ex){};
��try{document.all('Degree').value= document.all( "AppntDegree" ).value;              }catch(ex){};
��try{document.all('CreditGrade').value= document.all( "AppntDegreeCreditGrade" ).value;}catch(ex){};
��try{document.all('OthIDType').value= document.all( "AppntOthIDType" ).value;}catch(ex){};
��try{document.all('OthIDNo').value= document.all( "AppntOthIDNo" ).value;}catch(ex){};
��try{document.all('ICNo').value= document.all("AppntICNo").value;}catch(ex){};
��try{document.all('GrpNo').value= document.all("AppntGrpNo").value;}catch(ex){};
��try{document.all( 'JoinCompanyDate' ).value = document.all("JoinCompanyDate").value; if(document.all( 'JoinCompanyDate' ).value=="false"){document.all( 'JoinCompanyDate' ).value="";} } catch(ex) { };
��try{document.all('StartWorkDate').value= document.all("AppntStartWorkDate").value;}catch(ex){};
��try{document.all('Position').value= document.all("AppntPosition").value;}catch(ex){};
��try{document.all( 'Position' ).value = document.all("Position").value;} catch(ex) { };
��try{document.all('Salary').value= document.all("AppntSalary").value;}catch(ex){};
��try{document.all('OccupationType').value= document.all("AppntOccupationType").value;}catch(ex){};
��try{document.all('OccupationCode').value= document.all("AppntOccupationCode").value;}catch(ex){};
��try{document.all('WorkType').value= document.all("AppntWorkType").value;}catch(ex){};
��try{document.all('PluralityType').value= document.all("AppntPluralityType").value;}catch(ex){};
��try{document.all('DeathDate').value= document.all("AppntDeathDate").value;}catch(ex){};
��try{document.all('SmokeFlag').value= document.all("AppntSmokeFlag").value;}catch(ex){};
��try{document.all('BlacklistFlag').value= document.all("AppntBlacklistFlag").value;}catch(ex){};
��try{document.all('Proterty').value= document.all("AppntProterty").value;}catch(ex){};
��//try{document.all('Remark').value= document.all("AppntRemark").value;}catch(ex){};
��try{document.all('State').value= document.all("AppntState").value;}catch(ex){};
��try{document.all('Operator').value= document.all("AppntOperator").value;}catch(ex){};
��try{document.all('MakeDate').value= document.all("AppntMakeDate").value;}catch(ex){};
��try{document.all('MakeTime').value= document.all("AppntMakeTime").value;}catch(ex){};
��try{document.all('ModifyDate').value= document.all("AppntModifyDate").value;}catch(ex){};
��try{document.all('ModifyTime').value= document.all("AppntModifyTime").value;}catch(ex){};
��try{document.all('PostalAddress').value= document.all("AppntPostalAddress").value;}catch(ex){};
��try{document.all('ZipCode').value= document.all("AppntZipCode").value;}catch(ex){};
��try{document.all('Phone').value= document.all("AppntPhone").value;}catch(ex){};
��try{document.all('Fax').value= document.all("AppntFax").value;}catch(ex){};
��try{document.all('Mobile').value= document.all("AppntMobile").value;}catch(ex){};
��try{document.all('EMail').value= document.all("AppntEMail").value;}catch(ex){};
��try{document.all('GrpName').value= document.all("AppntGrpName").value;}catch(ex){};
��try{document.all('GrpPhone').value= document.all("AppntGrpPhone").value;}catch(ex){};
��try{document.all('GrpAddress').value= document.all("CompanyAddress").value;}catch(ex){};
��try{document.all('GrpZipCode').value= document.all("AppntGrpZipCode").value;}catch(ex){};
��try{document.all('GrpFax').value= document.all("AppntGrpFax").value;}catch(ex){};
��try{document.all('HomeAddress').value= document.all("AppntHomeAddress").value;}catch(ex){};
��try{document.all('HomePhone').value= document.all("AppntHomePhone").value;}catch(ex){};
��try{document.all('HomeZipCode').value= document.all("AppntHomeZipCode").value;}catch(ex){};
��try{document.all('HomeFax').value= document.all("AppntHomeFax").value;}catch(ex){};
��try{document.all('RelationToAppnt').value="00";}catch(ex){};
��try{document.all('InsuredProvince').value= document.all("AppntProvince").value;}catch(ex){};
��try{document.all('InsuredCity').value= document.all("AppntCity").value;}catch(ex){};
��try{document.all('InsuredDistrict').value= document.all("AppntDistrict").value;}catch(ex){};
��try{document.all('LicenseType').value= document.all("AppntLicenseType").value;}catch(ex){};
  try{document.all('IncomeWay').value= document.all("IncomeWay0").value;}catch(ex){};
  try{document.all('Income').value= document.all("Income0").value;}catch(ex){};
  showOneCodeName('incomeway','IncomeWay','IncomeWayName');

/***************************
  if(document.all('Position').value=="false"){
	  document.all('Position').value="";
	}
  if(document.all('Salary').value=="false"){
  	document.all( 'Salary' ).value="";
  }
***************************/
}
/*********************************************************************
 *  ��ѯ�����˸�֪��Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function getImpartInfo()
{
    initImpartGrid2();
    var InsuredNo=document.all("InsuredNo").value;
    var ContNo=document.all("ContNo").value;
    //��֪��Ϣ��ʼ��
    if(InsuredNo!=null&&InsuredNo!="")
    {

    	//    var strSQL0="select ImpartParam from LCCustomerImpartparams where CustomerNoType='1' and CustomerNo='"+InsuredNo+"' and ContNo='"+ContNo+"' and impartver='01' and impartcode='001' and ImpartParamNo = '1'";

	   // var strSQL1="select ImpartParam from LCCustomerImpartparams where CustomerNoType='1' and CustomerNo='"+InsuredNo+"' and ContNo='"+ContNo+"' and impartver='01' and impartcode='001' and ImpartParamNo = '2'";

	    
	    var sqlid34="BankContInputSql34";
		var mySql34=new SqlClass();
		mySql34.setResourceName(sqlresourcename);
		mySql34.setSqlId(sqlid34);
		mySql34.addSubPara(InsuredNo);
		mySql34.addSubPara(ContNo);
	    
	    arrResult = easyExecSql(mySql34.getString(),1,0);
	      
	    var sqlid35="BankContInputSql35";
		var mySql35=new SqlClass();
		mySql35.setResourceName(sqlresourcename);
		mySql35.setSqlId(sqlid35);
		mySql35.addSubPara(InsuredNo);
		mySql35.addSubPara(ContNo);
		
	    arrResult1 = easyExecSql(mySql35.getString(),1,0);



	    try{document.all('Income').value= arrResult[0][0];}catch(ex){};
	    try{document.all('IncomeWay').value= arrResult1[0][0];}catch(ex){};

       // var strSQL ="select ImpartVer,ImpartCode,ImpartContent,ImpartParamModle from LCCustomerImpart where CustomerNo='"+InsuredNo+"' and ProposalContNo='"+ContNo+"' and CustomerNoType='1' and ((impartver='01' and impartcode<>'001') or (impartver='02'))";
        
         var sqlid36="BankContInputSql36";
		var mySql36=new SqlClass();
		mySql36.setResourceName(sqlresourcename);
		mySql36.setSqlId(sqlid36);
		mySql36.addSubPara(InsuredNo);
		mySql36.addSubPara(ContNo);
        
        turnPage.strQueryResult  = easyQueryVer3(mySql36.getString(), 1, 0, 1);
        //�ж��Ƿ��ѯ�ɹ�
        if (!turnPage.strQueryResult)
        {
            return false;
        }
        //��ѯ�ɹ������ַ��������ض�ά����
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        //���ó�ʼ������MULTILINE����
        turnPage.pageDisplayGrid = ImpartInsuredGrid;
        //����SQL���
        turnPage.strQuerySql = strSQL;
        //���ò�ѯ��ʼλ��
        turnPage.pageIndex = 0;
        //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
        arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
        //����MULTILINE������ʾ��ѯ���
        displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
    }
}
/*********************************************************************
 *  ��ѯ��֪��Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function getImpartDetailInfo()
{
    initImpartDetailGrid2();
    var InsuredNo=document.all("InsuredNo").value;
    var ContNo=document.all("ContNo").value;
    //��֪��Ϣ��ʼ��
    if(InsuredNo!=null&&InsuredNo!="")
    {
        //var strSQL ="select ImpartVer,ImpartCode,ImpartDetailContent from LCCustomerImpartDetail where CustomerNo='"+InsuredNo+"' and ContNo='"+ContNo+"' and CustomerNoType='I'";
       
       var sqlid37="BankContInputSql37";
		var mySql37=new SqlClass();
		mySql37.setResourceName(sqlresourcename);
		mySql37.setSqlId(sqlid37);
		mySql37.addSubPara(InsuredNo);
		mySql37.addSubPara(ContNo);
       
        turnPage.strQueryResult  = easyQueryVer3(mySql37.getString(), 1, 0, 1);
        //�ж��Ƿ��ѯ�ɹ�
        if (!turnPage.strQueryResult)
        {
            return false;
        }
        //��ѯ�ɹ������ַ��������ض�ά����
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        //���ó�ʼ������MULTILINE����
        turnPage.pageDisplayGrid = ImpartDetailGrid;
        //����SQL���
        turnPage.strQuerySql = strSQL;
        //���ò�ѯ��ʼλ��
        turnPage.pageIndex = 0;
        //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
        arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
        //����MULTILINE������ʾ��ѯ���
        displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
    }
}
/*********************************************************************
 *  ��ñ�����������Ϣ��д��MulLine
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function getInsuredPolInfo()
{
    initPolGrid();
    var InsuredNo=document.all("InsuredNo").value;
    var ContNo=document.all("ContNo").value;
    //������Ϣ��ʼ��
    if(InsuredNo!=null&&InsuredNo!="")
    {
        //alert("InsuredNo=="+InsuredNo+"--ContNo=="+ContNo);
     //   var strSQL ="select PolNo,RiskCode,Prem,Amnt from LCPol where InsuredNo='"+InsuredNo+"' and ContNo='"+ContNo+"'";
        
        var sqlid38="BankContInputSql38";
		var mySql38=new SqlClass();
		mySql38.setResourceName(sqlresourcename);
		mySql38.setSqlId(sqlid38);
		mySql38.addSubPara(InsuredNo);
		mySql38.addSubPara(ContNo);
        
        //alert(strSQL);
        turnPage.strQueryResult  = easyQueryVer3(mySql38.getString(), 1, 0, 1);
        //�ж��Ƿ��ѯ�ɹ�
        if (!turnPage.strQueryResult)
        {
            return false;
        }
        //��ѯ�ɹ������ַ��������ض�ά����
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        //���ó�ʼ������MULTILINE����
        turnPage.pageDisplayGrid = PolGrid;
        //����SQL���
        turnPage.strQuerySql = strSQL;
        //���ò�ѯ��ʼλ��
        turnPage.pageIndex = 0;
        //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
        arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
        //����MULTILINE������ʾ��ѯ���
        displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
    }
}
/*********************************************************************
 *  MulLine��RadioBox����¼�����ñ�����������ϸ��Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function getPolDetail(parm1,parm2)
{
    var PolNo=document.all(parm1).all('PolGrid1').value
    try{mSwitch.deleteVar('PolNo')}catch(ex){};
    try{mSwitch.addVar('PolNo','',PolNo);}catch(ex){};
    fm.SelPolNo.value=PolNo;
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
           // var strSQL="select * from LCInsured where contno='"+document.all('ContNo').value +"'";
           
             var sqlid39="BankContInputSql39";
		var mySql39=new SqlClass();
		mySql39.setResourceName(sqlresourcename);
		mySql39.setSqlId(sqlid39);
		mySql39.addSubPara(document.all('ContNo').value);

           
            turnPage.strQueryResult  = easyQueryVer3(mySql39.getString(), 1, 0, 1);
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
       //     var strSql="select * from LCInsured where contno='"+document.all('ContNo').value +"' and RelationToAppnt='00' ";
        var sqlid40="BankContInputSql40";
		var mySql40=new SqlClass();
		mySql40.setResourceName(sqlresourcename);
		mySql40.setSqlId(sqlid40);
		mySql40.addSubPara(document.all('ContNo').value);
            
            turnPage.strQueryResult  = easyQueryVer3(mySql40.getString(), 1, 0, 1);
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
      //try{document.all('Remark').value= "";}catch(ex){};
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
      //  arrResult = easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo = '" + document.all("InsuredNo").value + "'", 1, 0);
       
       var sqlid41="BankContInputSql41";
		var mySql41=new SqlClass();
		mySql41.setResourceName(sqlresourcename);
		mySql41.setSqlId(sqlid41);
		mySql41.addSubPara(document.all('InsuredNo').value);
      arrResult = easyExecSql(mySql41.getString(), 1, 0);
        
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
/*********************************************************************
 *  Ͷ���˲�ѯ��Ť�¼�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
//function showAppnt1()
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
 *  ��ʾͶ������Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
 //hanlin 20050503
//function displayAppnt()
function displayInsuredInfo()
{
	  //alert("asdfasfsf");
    try{document.all('InsuredNo').value= arrResult[0][0]; }catch(ex){};
    try{document.all('Name').value= arrResult[0][1]; }catch(ex){};
    try{document.all('Sex').value= arrResult[0][2]; }catch(ex){};
    try{document.all('Birthday').value= arrResult[0][3]; }catch(ex){};
    try{document.all('IDType').value= arrResult[0][4]; }catch(ex){};
    try{document.all('IDNo').value= arrResult[0][5]; }catch(ex){};
    try{document.all('Password').value= arrResult[0][6]; }catch(ex){};
    try{document.all('NativePlace').value= arrResult[0][7]; }catch(ex){};
    try{document.all('Nationality').value= arrResult[0][8]; }catch(ex){};
    try{document.all('RgtAddress').value= arrResult[0][9]; }catch(ex){};
    try{document.all('Marriage').value= arrResult[0][10];}catch(ex){};
    try{document.all('MarriageDate').value= arrResult[0][11];}catch(ex){};
    try{document.all('Health').value= arrResult[0][12];}catch(ex){};
    try{document.all('Stature').value= arrResult[0][13];}catch(ex){};
    try{document.all('Avoirdupois').value= arrResult[0][14];}catch(ex){};
    try{document.all('Degree').value= arrResult[0][15];}catch(ex){};
    try{document.all('CreditGrade').value= arrResult[0][16];}catch(ex){};
    try{document.all('OthIDType').value= arrResult[0][17];}catch(ex){};
    try{document.all('OthIDNo').value= arrResult[0][18];}catch(ex){};
    try{document.all('ICNo').value= arrResult[0][19];}catch(ex){};
    try{document.all('GrpNo').value= arrResult[0][20];}catch(ex){};
    try{document.all('JoinCompanyDate').value= arrResult[0][21];}catch(ex){};
    try{document.all('StartWorkDate').value= arrResult[0][22];}catch(ex){};
    try{document.all('Position').value= arrResult[0][23];}catch(ex){};
    try{document.all('Salary').value= arrResult[0][24];}catch(ex){};
    try{document.all('OccupationType').value= arrResult[0][25];}catch(ex){};
    try{document.all('OccupationCode').value= arrResult[0][26];}catch(ex){};
    try{document.all('WorkType').value= arrResult[0][27];}catch(ex){};
    try{document.all('PluralityType').value= arrResult[0][28];}catch(ex){};
    try{document.all('DeathDate').value= arrResult[0][29];}catch(ex){};
    try{document.all('SmokeFlag').value= arrResult[0][30];}catch(ex){};
    try{document.all('BlacklistFlag').value= arrResult[0][31];}catch(ex){};
    try{document.all('Proterty').value= arrResult[0][32];}catch(ex){};
    //try{document.all('Remark').value= arrResult[0][33];}catch(ex){};
    try{document.all('State').value= arrResult[0][34];}catch(ex){};
    try{document.all('VIPValue1').value= arrResult[0][35];}catch(ex){};
    try{document.all('Operator').value= arrResult[0][36];}catch(ex){};
    try{document.all('MakeDate').value= arrResult[0][37];}catch(ex){};
    try{document.all('MakeTime').value= arrResult[0][38];}catch(ex){};
    try{document.all('ModifyDate').value= arrResult[0][39];}catch(ex){};
    try{document.all('ModifyTime').value= arrResult[0][40];}catch(ex){};
    try{document.all('LicenseType').value= arrResult[0][43];}catch(ex){};
    try{document.all('GrpName').value= arrResult[0][44];}catch(ex){};



    //��ַ��ʾ���ֵı䶯
    try{document.all('InsuredAddressNo').value= "";}catch(ex){};
    try{document.all('PostalAddress').value=  "";}catch(ex){};
    try{document.all('ZipCode').value=  "";}catch(ex){};
    try{document.all('Phone').value=  "";}catch(ex){};
    try{document.all('Mobile').value=  "";}catch(ex){};
    try{document.all('EMail').value=  "";}catch(ex){};
    //try{document.all('GrpName').value= arrResult[0][46];}catch(ex){};
    try{document.all('GrpPhone').value=  "";}catch(ex){};
    try{document.all('GrpAddress').value=  "";}catch(ex){};
    try{document.all('GrpZipCode').value=  "";}catch(ex){};
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

         //   arrResult = easyExecSql("select ProposalContNo,PrtNo,ManageCom,SaleChnl,AgentCode,AgentGroup,AgentCode1,AgentCom,AgentType,Remark from LCCont where LCCont = '" + arrQueryResult[0][0] + "'", 1, 0);

var sqlid42="BankContInputSql42";
		var mySql42=new SqlClass();
		mySql42.setResourceName(sqlresourcename);
		mySql42.setSqlId(sqlid42);
		mySql42.addSubPara( arrQueryResult[0][0]);

 arrResult = easyExecSql(mySql42.getString(), 1, 0);

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
        //	arrResult = easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0);
        	
        	var sqlid43="BankContInputSql43";
		var mySql43=new SqlClass();
		mySql43.setResourceName(sqlresourcename);
		mySql43.setSqlId(sqlid43);
		mySql43.addSubPara( arrQueryResult[0][0]);
   arrResult = easyExecSql(mySql43.getString(), 1, 0);
        	     	
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
 *  ��ѯְҵ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function getdetailwork2()
{
   // var strSql = "select OccupationType from LDOccupation where OccupationCode='" + fm.OccupationCode.value+"'";
    
    
        	var sqlid44="BankContInputSql44";
		var mySql44=new SqlClass();
		mySql44.setResourceName(sqlresourcename);
		mySql44.setSqlId(sqlid44);
		mySql44.addSubPara(fm.OccupationCode.value);
    
    var arrResult = easyExecSql(mySql44.getString());
    if (arrResult != null)
    {
        fm.OccupationType.value = arrResult[0][0];
    }
    else
    {
        fm.OccupationType.value = '';
    }
}
/*��ø��˵���Ϣ��д��ҳ��ؼ�
function getProposalInsuredInfo(){
  var ContNo = fm.ContNo.value;
  //��������ϸ��Ϣ
  var strSQL ="select * from ldperson where CustomerNo in (select InsuredNo from LCInsured where ContNo='"+ContNo+"')";
  arrResult=easyExecSql(strSQL);
  if(arrResult!=null){
  	DisplayCustomer();
  }

  strSQL ="select * from LCInsured where ContNo = '"+ContNo+"'";
  arrResult=easyExecSql(strSQL);

  if(arrResult!=null){
  	   DisplayInsured();
  }else{


    return;
  }

  var tAddressNo = arrResult[0][10]; 		// �õ������˵�ַ��
  var InsuredNo=arrResult[0][2];
  var strSQL="select * from LCAddress where AddressNo='"+tAddressNo+"' and CustomerNo='"+InsuredNo+"'";
  arrResult=easyExecSql(strSQL);
    if(arrResult!=null){
  	DisplayAddress();
    }

    getInsuredPolInfo();

}*/


/*********************************************************************
 *  �Ѻ�ͬ������Ϣ¼�����ȷ��(����)
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function inputConfirm2(wFlag)
{
	alert("LoadFlag=="+LoadFlag);


    if (wFlag ==1 ) //¼�����ȷ��
    {
      //  var tStr= "	select * from lwmission where 1=1 and lwmission.activityid in ('0000001001','0000001002','0000001220','0000001230') and lwmission.missionprop1 = '"+fm.ContNo.value+"'";
        
        var sqlid45="BankContInputSql45";
		var mySql45=new SqlClass();
		mySql45.setResourceName(sqlresourcename);
		mySql45.setSqlId(sqlid45);
		mySql45.addSubPara(fm.ContNo.value);
        
        turnPage.strQueryResult = easyQueryVer3(mySql45.getString(), 1, 0, 1);
        if (turnPage.strQueryResult)
        {
		    alert("�ú�ͬ�Ѿ��������棡");
		    return;
		}
		fm.AppntNo.value = AppntNo;
		fm.AppntName.value = AppntName;
		fm.WorkFlowFlag.value = "7999999999";
    }
    else if (wFlag ==2)//�������ȷ��
    {
  	    if(document.all('ProposalContNo').value == "")
        {
            alert("δ��ѯ����ͬ��Ϣ,������������ [�������] ȷ�ϣ�");
            return;
        }
		fm.WorkFlowFlag.value = "0000001001";
		fm.MissionID.value = tMissionID;
		fm.SubMissionID.value = tSubMissionID;
    }
    else if (wFlag ==3)
    {
        if(document.all('ProposalContNo').value == "")
        {
            alert("δ��ѯ����ͬ��Ϣ,������������ [�����޸����] ȷ�ϣ�");
            return;
        }
		fm.WorkFlowFlag.value = "0000001002";
		fm.MissionID.value = tMissionID;
		fm.SubMissionID.value = tSubMissionID;
	}
	else
		return;

    var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    fm.action = "./InputConfirm.jsp";
    fm.submit(); //�ύ
}
/*********************************************************************
 *  ��ѯ����������ϸ��Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function getdetailaddress2()
{
    //alert("abc");
  //  var strSQL="select b.AddressNo,b.PostalAddress,b.ZipCode,b.Phone,b.Mobile,b.EMail,b.CompanyPhone,b.CompanyAddress,b.CompanyZipCode,b.fax,b.HomePhone,b.grpName,b.province,b.city,b.County from LCAddress b where b.AddressNo='"+fm.InsuredAddressNo.value+"' and b.CustomerNo='"+fm.InsuredNo.value+"'";
    
     var sqlid46="BankContInputSql46";
		var mySql46=new SqlClass();
		mySql46.setResourceName(sqlresourcename);
		mySql46.setSqlId(sqlid46);
		mySql46.addSubPara(fm.InsuredAddressNo.value);
		mySql46.addSubPara(fm.InsuredNo.value);
    
    arrResult=easyExecSql(mySql46.getString());
    try{document.all('InsuredAddressNo').value= arrResult[0][0];}catch(ex){};
    try{document.all('PostalAddress').value= arrResult[0][1];}catch(ex){};
    try{document.all('ZIPCODE').value= arrResult[0][2];}catch(ex){};
    try{document.all('Phone').value= arrResult[0][3];}catch(ex){};
    try{document.all('Mobile').value= arrResult[0][4];}catch(ex){};
    try{document.all('EMail').value= arrResult[0][5];}catch(ex){};
    //try{document.all('GrpName').value= arrResult[0][6];}catch(ex){};
    try{document.all('GrpPhone').value= arrResult[0][6];}catch(ex){};
    try{document.all('GrpAddress').value= arrResult[0][7];}catch(ex){};
    try{document.all('GrpZipCode').value= arrResult[0][8];}catch(ex){};
    try{document.all('Fax').value= arrResult[0][9];}catch(ex){};
    try{document.all('HomePhone').value= arrResult[0][10];}catch(ex){};
    //alert("arrResult[0][11]=="+arrResult[0][11]);
   // try{document.all('GrpName').value= arrResult[0][11];}catch(ex){};
    //alert("fm.GrpName="+fm.GrpName.value);
   try{document.all('InsuredProvince').value= arrResult[0][12];}catch(ex){};
   try{document.all('InsuredCity').value= arrResult[0][13];}catch(ex){};
   try{document.all('InsuredDistrict').value= arrResult[0][14];}catch(ex){};
   showOneCodeName('Province','InsuredProvince','InsuredProvinceName');
   showOneCodeName('City','InsuredCity','InsuredCityName');
   showOneCodeName('District','InsuredDistrict','InsuredDistrictName');
}
/*********************************************************************
 *  ��ѯ���ռƻ�
 *  ����  ��  �����ͬͶ������
 *  ����ֵ��  ��
 *********************************************************************
 */
function getContPlanCode(tProposalGrpContNo)
{
    var i = 0;
    var j = 0;
    var m = 0;
    var n = 0;
    var strsql = "";
    var tCodeData = "0|";
   // strsql = "select ContPlanCode,ContPlanName from LCContPlan where ContPlanCode!='00' and ProposalGrpContNo='"+tProposalGrpContNo+"'";
   
   var sqlid47="BankContInputSql47";
		var mySql47=new SqlClass();
		mySql47.setResourceName(sqlresourcename);
		mySql47.setSqlId(sqlid47);
		mySql47.addSubPara(tProposalGrpContNo);
   
    //alert("strsql :" + strsql);
    turnPage.strQueryResult  = easyQueryVer3(mySql47.getString(), 1, 0, 1);
    if (turnPage.strQueryResult != "")
    {
    	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    	m = turnPage.arrDataCacheSet.length;
    	for (i = 0; i < m; i++)
    	{
    		j = i + 1;
    		tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
    	}
    	 divContPlan.style.display="";
    }
    else
    {
      //alert("���ռƻ�û�鵽");
        divContPlan.style.display="none";
    }
    //alert ("tcodedata : " + tCodeData);
    return tCodeData;
}

/*********************************************************************
 *  ��ѯ�������
 *  ����  ��  �����ͬͶ������
 *  ����ֵ��  ��
 *********************************************************************
 */
function getExecuteCom(tProposalGrpContNo)
{
    	//alert("1");
    var i = 0;
	var j = 0;
	var m = 0;
	var n = 0;
	var strsql = "";
	var tCodeData = "0|";
	//strsql = "select ExecuteCom,Name from LCGeneral a,LDCom b where a.GrpContNo='"+tProposalGrpContNo+"' and a.ExecuteCom=b.ComCode";
	
	var sqlid48="BankContInputSql48";
		var mySql48=new SqlClass();
		mySql48.setResourceName(sqlresourcename);
		mySql48.setSqlId(sqlid48);
		mySql48.addSubPara(tProposalGrpContNo);
	
	//alert("strsql :" + strsql);
	turnPage.strQueryResult  = easyQueryVer3(mySql48.getString(), 1, 0, 1);
	if (turnPage.strQueryResult != "")
	{
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		m = turnPage.arrDataCacheSet.length;
		for (i = 0; i < m; i++)
		{
			j = i + 1;
//			tCodeData = tCodeData + "^" + j + "|" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
			tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
		}
		divExecuteCom.style.display="";
	}
	else
	{
	    divExecuteCom.style.display="none";
    }
	//alert ("tcodedata : " + tCodeData);

	return tCodeData;
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
	//try{document.all('OccupationType').value= ""; }catch(ex){};
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
	emptyAddress();
	ImpartGrid.clearData();
	ImpartGrid.addOne();
	//ImpartDetailGrid.clearData();
	//ImpartDetailGrid.addOne();
}

/*********************************************************************
 *  ��տͻ���ַ����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function emptyAddress()
{
	try{document.all('PostalAddress').value= "";  }catch(ex){};
	try{document.all('ZipCode').value= "";  }catch(ex){};
	try{document.all('Phone').value= "";  }catch(ex){};
	try{document.all('Mobile').value= "";  }catch(ex){};
	try{document.all('EMail').value= "";  }catch(ex){};
	//try{document.all('GrpName').value= arrResult[0][2]; }catch(ex){};
	try{document.all('GrpPhone').value= "";  }catch(ex){};
	try{document.all('GrpAddress').value= ""; }catch(ex){};
	try{document.all('GrpZipCode').value= "";  }catch(ex){};
}
/*********************************************************************
 *  �������֤��ȡ�ó������ں��Ա�
 *  ����  ��  ���֤��
 *  ����ֵ��  ��
 *********************************************************************
 */

function getBirthdaySexByIDNo2(iIdNo)
{
	//alert("aafsd");
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
/*********************************************************************
 *  ��ͬ��Ϣ¼�����ȷ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function GrpInputConfirm(wFlag)
{
	mWFlag = 1;
	if (wFlag ==1 ) //¼�����ȷ��
	{/*
	    var tStr= "	select * from lwmission where 1=1 "
	    					+" and lwmission.processid = '0000000004'"
	    					+" and lwmission.activityid = '0000002001'"
	    					+" and lwmission.missionprop1 = '"+fm.ProposalGrpContNo.value+"'";
	 */
	 var sqlid49="BankContInputSql49";
		var mySql49=new SqlClass();
		mySql49.setResourceName(sqlresourcename);
		mySql49.setSqlId(sqlid49);
		mySql49.addSubPara(fm.ProposalGrpContNo.value);
	 
	    turnPage.strQueryResult = easyQueryVer3(mySql49.getString(), 1, 0, 1);
	    if (turnPage.strQueryResult)
	    {
	        alert("���ŵ���ͬ�Ѿ��������棡");
	        return;
	    }
		if(document.all('ProposalGrpContNo').value == "")
	    {
	        alert("�ŵ���ͬ��Ϣδ����,������������ [¼�����] ȷ�ϣ�");
	        return;
	    }
		fm.WorkFlowFlag.value = "6999999999";			//¼�����
    }
    else if (wFlag ==2)//�������ȷ��
    {
        if(document.all('ProposalGrpContNo').value == "")
	    {
	        alert("δ��ѯ���ŵ���ͬ��Ϣ,������������ [�������] ȷ�ϣ�");
	        return;
	    }
		fm.WorkFlowFlag.value = "0000002002";					//�������
		fm.MissionID.value = MissionID;
		fm.SubMissionID.value = SubMissionID;
	}
	else if (wFlag ==3)
    {
  	    if(document.all('ProposalGrpContNo').value == "")
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
		if(document.all('ProposalGrpContNo').value == "")
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
	fm.action = "./GrpInputConfirm.jsp";
    fm.submit(); //�ύ
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
   // strsql = "select AddressNo,PostalAddress from LCAddress where CustomerNo ='"+fm.InsuredNo.value+"'";
   
    var sqlid50="BankContInputSql50";
		var mySql50=new SqlClass();
		mySql50.setResourceName(sqlresourcename);
		mySql50.setSqlId(sqlid50);
		mySql50.addSubPara(fm.InsuredNo.value);
   
    //alert("strsql :" + strsql);
    turnPage.strQueryResult  = easyQueryVer3(mySql50.getString(), 1, 0, 1);
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
    document.all("InsuredAddressNo").CodeData=tCodeData;
}

function getImpartCode(parm1,parm2){
  //alert("hehe:"+document.all(parm1).all('ImpartGrid1').value);
  var impartVer=document.all(parm1).all('ImpartGrid1').value;
  window.open("../appgrp/ImpartCodeSel.jsp?ImpartVer="+impartVer,"",sFeatures);
}
function checkidtype2()
{
	//alert("sdasf");
	if(fm.IDType.value=="")
	{
		alert("����ѡ��֤�����ͣ�");
	//	fm.IDNo.value="";
        }
}
function getallinfo()
{
 	if(fm.Name.value!=""&&fm.IDType.value!=""&&fm.IDNo.value!="")
 	{/*
	    strSQL = "select a.CustomerNo, a.Name, a.Sex, a.Birthday, a.IDType, a.IDNo from LDPerson a where 1=1 "
                +"  and Name='"+fm.Name.value
                +"' and IDType='"+fm.IDType.value
                +"' and IDNo='"+fm.IDNo.value
		+"' order by a.CustomerNo";
		
		*/
		 var sqlid51="BankContInputSql51";
		var mySql51=new SqlClass();
		mySql51.setResourceName(sqlresourcename);
		mySql51.setSqlId(sqlid51);
		mySql51.addSubPara(fm.Name.value);
		mySql51.addSubPara(fm.IDType.value);
		mySql51.addSubPara(fm.IDNo.value);
		
		
		
             turnPage.strQueryResult  = easyQueryVer3(mySql51.getString(), 1, 0, 1);
             if (turnPage.strQueryResult != "")
             {
 		  mOperate = 2;
 		  window.open("../sys/LDPersonQueryAll.html?Name="+fm.Name.value+"&IDType="+fm.IDType.value+"&IDNo="+fm.IDNo.value,"newwindow","height=10,width=1090,top=180,left=180, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no,status=no");
 	     }
 	     else
 	     return;
 	}
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
	/*
  var sqlstr="select * from ldperson where Name='"+tInsuredName
            +"' and Sex='"+tInsuredSex+"' and Birthday='"+tBirthday
            +"' and CustomerNo<>'"+tInsuredNo+"'"
            +" union select * from ldperson where IDType = '"+fm.IDType.value
            +"' and IDType is not null and IDNo = '"+fm.IDNo.value
            +"' and IDNo is not null and CustomerNo<>'"+tInsuredNo+"'" ;
         */   
            
        var sqlid52="BankContInputSql52";
		var mySql52=new SqlClass();
		mySql52.setResourceName(sqlresourcename);
		mySql52.setSqlId(sqlid52);
		mySql52.addSubPara(tInsuredName);
		mySql52.addSubPara(tInsuredSex);
		mySql52.addSubPara(tBirthday);
		mySql52.addSubPara(tInsuredNo);
		mySql52.addSubPara(fm.IDType.value);
		mySql52.addSubPara(fm.IDNo.value);
		mySql52.addSubPara(tInsuredNo);
		
 
            
        arrResult = easyExecSql(mySql52.getString(),1,0);

        if(arrResult==null)
        {
	   alert("��û����ñ����˱������ƵĿͻ�,����У��");
	   return false;
        }

    window.open("../uwgrp/AppntChkMain.jsp?ProposalNo1="+fm.ContNo.value+"&Flag=I&LoadFlag="+LoadFlag+"&InsuredNo="+tInsuredNo,"window1",sFeatures);
}
function getdetailaccount()
{
	if(fm.AccountNo.value=="1")
	{
           document.all('BankAccNo').value=mSwitch.getVar("AppntBankAccNo");
           document.all('BankCode').value=mSwitch.getVar("AppntBankCode");
           document.all('AccName').value=mSwitch.getVar("AppntAccName");
	}
	if(fm.AccountNo.value=="2")
	{
           document.all('BankAccNo').value="";
           document.all('BankCode').value="";
           document.all('AccName').value="";
	}

}
function AutoMoveForNext()
{
	if(fm.AutoMovePerson.value=="���Ƶڶ���������")
	{
		     //emptyFormElements();
		     param="122";
		     fm.pagename.value="122";
                     fm.AutoMovePerson.value="���Ƶ�����������";
                     return false;
	}
	if(fm.AutoMovePerson.value=="���Ƶ�����������")
	{
		     //emptyFormElements();
		     param="123";
		     fm.pagename.value="123";
                     fm.AutoMovePerson.value="���Ƶ�һ��������";
                     return false;
	}
		if(fm.AutoMovePerson.value=="���Ƶ�һ��������")
	{
		     //emptyFormElements();
		     param="121";
		     fm.pagename.value="121";
                     fm.AutoMovePerson.value="���Ƶڶ���������";
                     return false;
	}
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
    //   var strhomea="select HomeAddress,HomeZipCode,HomePhone from lcaddress where customerno='"+insuredno+"' and addressno=(select addressno from lcinsured where contno='"+fm.ContNo.value+"' and insuredno='"+insuredno+"')";
     
      var sqlid53="BankContInputSql53";
		var mySql53=new SqlClass();
		mySql53.setResourceName(sqlresourcename);
		mySql53.setSqlId(sqlid53);
		mySql53.addSubPara(insuredno);
		mySql53.addSubPara(fm.ContNo.value);
		mySql53.addSubPara(insuredno);

     
       arrResult=easyExecSql(mySql53.getString(),1,0);
       try{document.all('HomeAddress').value= arrResult[0][0];}catch(ex){};

       try{document.all('HomeZipCode').value= arrResult[0][1];}catch(ex){};

       try{document.all('HomePhone').value= arrResult[0][2];}catch(ex){};

       fm.InsuredAddressNo.value = "";
       fm.InsuredNo.value = "";
    }
}
function getdetail2()
{
//var strSql = "select BankCode,AccName from LCAccount where BankAccNo='" + fm.BankAccNo.value+"'";

 var sqlid54="BankContInputSql54";
		var mySql54=new SqlClass();
		mySql54.setResourceName(sqlresourcename);
		mySql54.setSqlId(sqlid54);
		mySql54.addSubPara(fm.BankAccNo.value);

		
arrResult = easyExecSql(mySql54.getString());
if (arrResult != null) {
      fm.BankCode.value = arrResult[0][0];
      fm.AccName.value = arrResult[0][1];
    }
}


// �ڳ�ʼ��bodyʱ�Զ�Ч��Ͷ������Ϣ
function InsuredChkNew(){
	//alert("aaa");
	var tRow = InsuredGrid.getSelNo() - 1;
	var tInsuredNo=InsuredGrid.getRowColData(tRow,1);
	var tInsuredName=InsuredGrid.getRowColData(tRow,2);
	var tInsuredSex=InsuredGrid.getRowColData(tRow,3);
	var tBirthday=InsuredGrid.getRowColData(tRow,4);
	/*
  var sqlstr="select * from ldperson where Name='"+tInsuredName
            +"' and Sex='"+tInsuredSex+"' and Birthday='"+tBirthday
            +"' and CustomerNo<>'"+tInsuredNo+"'"
            +" union select * from ldperson where IDType = '"+fm.IDType.value
            +"' and IDType is not null and IDNo = '"+fm.IDNo.value
            +"' and IDNo is not null and CustomerNo<>'"+tInsuredNo+"'" ;
  */
   var sqlid55="BankContInputSql55";
		var mySql55=new SqlClass();
		mySql55.setResourceName(sqlresourcename);
		mySql55.setSqlId(sqlid55);
		mySql55.addSubPara(tInsuredName);
		mySql55.addSubPara(tInsuredSex);
		mySql55.addSubPara(tBirthday);
		mySql55.addSubPara(tInsuredNo);
		mySql55.addSubPara(fm.IDType.value);
		mySql55.addSubPara(fm.IDNo.value);
		mySql55.addSubPara(tInsuredNo);
  
  arrResult = easyExecSql(mySql55.getString(),1,0);
  if(arrResult==null)
  {
  	//disabled"Ͷ����Ч��"��ť
		fm.InsuredChkButton.disabled = true;
//				  return false;
  }
  else
  {
		fm.InsuredChkButton.disabled = "";
					//�������ͬ�������Ա����ղ�ͬ�ͻ��ŵ��û���ʾ"Ͷ����У��"��ť
	}
}


//*******************************************************************
//�������˺Ÿ�ֵ�������˺���
function theSameToFirstAccount(){
	//alert("aasf");
  if(fm.theSameAccount.checked==true){
  	document.all('SecAppntBankCode').value=document.all('AppntBankCode').value;
  	document.all('SecAppntAccName').value=document.all('AppntAccName').value;
//    alert(document.all('AppntBankAccNo').value);
//    alert(document.all('SecAppntBankAccNo').value);
  	document.all('SecAppntBankAccNo').value=document.all('AppntBankAccNo').value;
    return;
  }
  if(fm.theSameAccount.checked==false){
//  	document.all('AppntBankCode').value='';
//  	document.all('AppntAccName').value='';
//  	document.all('AppntBankAccNo').value='';
    return;
  }
}

//��ʾ�����˺���Ϣ
function displayFirstAccount(){
  document.all('AppntBankCode').value = arrResult[0][0];
  document.all('AppntAccName').value = arrResult[0][1];
  document.all('AppntBankAccNo').value = arrResult[0][2];

  //��ѯ�������˺ź󣬲�ͬʱ��ֵ�������˺�
 	document.all('SecAppntBankCode').value=document.all('AppntBankCode').value;
	document.all('SecAppntAccName').value=document.all('AppntAccName').value;
 	document.all('SecAppntBankAccNo').value=document.all('AppntBankAccNo').value;

}

//ǿ�ƽ����˹��˱�
function forceUW(){
	//��ѯ�Ƿ��Ѿ�����ѡ��ǿ�ƽ����˹��˱�
  var ContNo=document.all("ContNo").value;
//  var sqlstr="select forceuwflag from lccont where contno='"+ContNo+"'" ;
  
    var sqlid56="BankContInputSql56";
		var mySql56=new SqlClass();
		mySql56.setResourceName(sqlresourcename);
		mySql56.setSqlId(sqlid56);
		mySql56.addSubPara(ContNo);

  
  arrResult = easyExecSql(mySql56.getString(),1,0);
  if(arrResult==null){
  	alert("�����ڸ�Ͷ������");
  }
  else{
    window.open("../uwgrp/ForceUWMain.jsp?ContNo="+ContNo,"window1",sFeatures);
  }

}

//��ѯ�Ƿ��Ѿ���ӹ�Ͷ����
function checkAppnt(){
  var ContNo=document.all("ContNo").value;
  //var sqlstr="select forceuwflag from lccont where contno='"+ContNo+"'" ;
  
  var sqlid57="BankContInputSql57";
		var mySql57=new SqlClass();
		mySql57.setResourceName(sqlresourcename);
		mySql57.setSqlId(sqlid57);
		mySql57.addSubPara(ContNo);
  
  arrResult = easyExecSql(mySql57.getString(),1,0);
  if(arrResult==null){
  	return false;
  }
  else{
    return true;
  }

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
  if(confirm("ȷ���뿪��Ͷ����������޸Ľ��棿")){
    top.close();
  }
  else{
    return;
  }
}


function showNotePad()
{

//	var selno = SelfGrpGrid.getSelNo()-1;
//	if (selno <0)
//	{
//	      alert("��ѡ��һ������");
//	      return;
//	}

//var MissionID = SelfGrpGrid.getRowColData(selno, 4);
  //var MissionID = MissionID;
  //alert("MissionID="+MissionID);

//var SubMissionID = SelfGrpGrid.getRowColData(selno, 5);
 // var SubMissionID = document.all.SubMissionID.value;
  //alert("SubMissionID="+SubMissionID);

//	var ActivityID = SelfGrpGrid.getRowColData(selno, 6);
	//var ActivityID = document.all.ActivityID.value;
	//alert("ActivityID="+ActivityID);

//	var PrtNo = SelfGrpGrid.getRowColData(selno, 2);
  var PrtNo = document.all.PrtNo2.value;
 // alert("PrtNo="+ document.all.PrtNo2.value);

	//var NoType = document.all.NoType.value;
	//alert("NoType="+NoType);
	if(PrtNo == null || PrtNo == "")
	{
		alert("Ͷ������Ϊ�գ�");
		return;
	} 
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ PrtNo + "&NoType=1";
	var newWindow = OpenWindowNew("../uwgrp/WorkFlowNotePadFrame.jsp?Interface=../uwgrp/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");

}

/*********************************************************************
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
 
	showOneCodeName('comcode','ManageCom','ManageComName');  //���뺺��

	//showOneCodeName('comcode','AgentManageCom','AgentManageComName');

	showOneCodeName('sellType','SellType','sellTypeName'); 

}
/*********************************************************************
 *  Ͷ���˴���򺺻�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showAppntCodeName()
{
	showOneCodeName('vipvalue','AppntVIPValue','AppntVIPFlagname');

	showOneCodeName('IDType','AppntIDType','AppntIDTypeName');

	showOneCodeName('Sex','AppntSex','AppntSexName');

	showOneCodeName('Marriage','AppntMarriage','AppntMarriageName');

	showOneCodeName('NativePlace','AppntNativePlace','AppntNativePlaceName');

	showOneCodeName('OccupationCode','AppntOccupationCode','AppntOccupationCodeName');

	showOneCodeName('LicenseType','AppntLicenseType','AppntLicenseTypeName');

	showOneCodeName('GetAddressNo','AppntAddressNo','AddressNoName');

	showOneCodeName('province','AppntProvince','AppntProvinceName');

	showOneCodeName('city','AppntCity','AppntCityName');

	showOneCodeName('district','AppntDistrict','AppntDistrictName');

	showOneCodeName('paymode','PayMode','FirstPayModeName');

	showOneCodeName('continuepaymode','SecPayMode','PayModeName');

	showOneCodeName('bank','AppntBankCode','FirstBankCodeName');

	showOneCodeName('bank','SecAppntBankCode','AppntBankCodeName');
	showOneCodeName('incomeway','IncomeWay0','IncomeWayName0');
	//
	//showOneCodeName('GetAddressNo','AppntAddressNo','AddressNoName');
}

/*********************************************************************
 *  �����˴���򺺻�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showInsuredCodeName()
{

	showOneCodeName('SequenceNo','SequenceNo','SequenceNoName');

	showOneCodeName('vipvalue','VIPValue1','AppntVIPFlagname1');

	showOneCodeName('Relation','RelationToMainInsured','RelationToMainInsuredName');

	showOneCodeName('Relation','RelationToAppnt','RelationToAppntName');

	showOneCodeName('IDType','IDType','IDTypeName');

	showOneCodeName('Sex','Sex','SexName');

	showOneCodeName('Marriage','Marriage','MarriageName');

	showOneCodeName('OccupationCode','OccupationCode','OccupationCodeName');

	showOneCodeName('NativePlace','NativePlace','NativePlaceName');

	showOneCodeName('LicenseType','LicenseType','LicenseTypeName');

	showOneCodeName('GetAddressNo','InsuredAddressNo','InsuredAddressNoName');

	showOneCodeName('Province','InsuredProvince','InsuredProvinceName'); 

	showOneCodeName('City','InsuredCity','InsuredCityName');

	showOneCodeName('District','InsuredDistrict','InsuredDistrictName');
	showOneCodeName('incomeway','IncomeWay','IncomeWayName');

}

//�����Ӱ���ѯ
function QuestPicQuery(){
		  var ContNo = fm.ContNo.value;
		if (ContNo == "")
		    return;
		  window.open("../uwgrp/ImageQueryMain.jsp?ContNo="+ContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");
	
}
//��ѯҵ��Ա��֪��Ϣ
function getImpartInitInfo()
{
	var tContNo = fm.ContNo.value;

	//var strSQL ="select impartver,impartcode,ImpartContent from LDImpart where impartver='05' ";

var sqlid58="BankContInputSql58";
		var mySql58=new SqlClass();
		mySql58.setResourceName(sqlresourcename);
		mySql58.setSqlId(sqlid58);
		mySql58.addSubPara("1");


	turnPage.queryModal(mySql58.getString(), AgentImpartGrid);
      /*
        var aSQL="SELECT distinct a.impartver,a.impartcode,a.ImpartContent,b.impartparammodle "
		+"FROM ldimpart a left join lccustomerimpart b on a.impartver=b.impartver and a.ImpartCode=b.ImpartCode and b.contno='"+tContNo+"' "
		+"WHERE a.impartver='05'";
*/
var sqlid59="BankContInputSql59";
		var mySql59=new SqlClass();
		mySql59.setResourceName(sqlresourcename);
		mySql59.setSqlId(sqlid59);
		mySql59.addSubPara(tContNo);

	turnPage.queryModal(mySql59.getString(),AgentImpartGrid);
	return true;
}

function getLostInfo()
{
	//var strSQL = "select bankcode from lacom where agentcom='"+ document.all('AgentCom').value+"'";
	
	var sqlid60="BankContInputSql60";
		var mySql60=new SqlClass();
		mySql60.setResourceName(sqlresourcename);
		mySql60.setSqlId(sqlid60);
		mySql60.addSubPara(document.all('AgentCom').value);
	
	//alert("1:"+strSQL);
	arrResult = easyExecSql(mySql60.getString(),1,0);
	if(arrResult!==null)
	  { 
	  	//alert(arrResult[0][0]);
      document.all('BankCode1').value=arrResult[0][0]; 
		}
		
	//strSQL = "select name from laagent where agentcode='" + document.all('AgentCode').value +"'";
	//alert("2:"+strSQL);
	var sqlid61="BankContInputSql61";
		var mySql61=new SqlClass();
		mySql61.setResourceName(sqlresourcename);
		mySql61.setSqlId(sqlid61);
		mySql61.addSubPara(document.all('AgentCode').value);
	
	arrResult = easyExecSql(mySql61.getString(),1,0);
	
	if(arrResult!==null)
	  {
      document.all('AgentName').value=arrResult[0][0];
		}
		
	//strSQL = "select name from labranchgroup where agentgroup='" + document.all('AgentGroup').value +"'";
	//alert("3:"+strSQL);
	var sqlid62="BankContInputSql62";
		var mySql62=new SqlClass();
		mySql62.setResourceName(sqlresourcename);
		mySql62.setSqlId(sqlid62);
		mySql62.addSubPara(document.all('AgentGroup').value);
	
	arrResult = easyExecSql(mySql62.getString(),1,0);
	
	if(arrResult!==null)
	  {
      document.all('BranchAttr').value=arrResult[0][0];
		}
		
//	strSQL = "select BankAgent,AgentBankCode from lccont where contno='" + document.all('ProposalContNo').value +"'";
  
  var sqlid63="BankContInputSql63";
		var mySql63=new SqlClass();
		mySql63.setResourceName(sqlresourcename);
		mySql63.setSqlId(sqlid63);
		mySql63.addSubPara(document.all('ProposalContNo').value);
  
  arrResult = easyExecSql(mySql63.getString(),1,0);
  
  if (arrResult!==null)
    {
      document.all('CounterCode').value=arrResult[0][0];
      document.all('AgentBankCode').value=arrResult[0][1];      
  	}

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
