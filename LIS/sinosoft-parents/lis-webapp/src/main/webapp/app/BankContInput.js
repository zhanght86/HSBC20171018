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
	if(verifyWorkFlow(tMissionID,tSubMissionID,ActivityID)==false)
	{
		return false;
	}/////////////////У�鹫�����Ƿ��Ѿ���ת���


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

var tSql = ""//"select sysdate from dual";

    var sqlid1="BankContInput1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	tSql=mySql1.getString();

//turnPage.strQueryResult = easyQueryVer3(tSql, 1, 0, 1);
//turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
var tSysDate = easyExecSql(tSql);
   
//var tSysDate = turnPage.arrDataCacheSet[0][0];    

if(fm.PolAppntDate.value>tSysDate)
{
	alert("Ͷ������Ӧ��С�ڵ�ǰ����");
	return ;
}
///////end add
	if(fm.AppntIDType.value!=""&&fm.AppntIDNo.value==""||fm.AppntIDType.value==""&&fm.AppntIDNo.value!=""){
		if(fm.AppntIDType.value!="9"){
			alert("֤�����ͺ�֤���������ͬʱ��д����");
			return false;
		}
		}
		if((document.all('AppntBankCode').value!=null&&document.all('AppntBankCode').value!='')
		||(document.all('AppntBankCode').value!=null&&document.all('AppntAccName').value!='')
		||(document.all('AppntBankCode').value!=null&&document.all('AppntBankAccNo').value!='')
		)
		{
			fm.PayMode.value='0';
		}
//alert(fm.PayMode.value);
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
//tongmeng 2007-09-10 ��ʱע�͵��˴�
  if( verifyInputNB('1') == false ) return false;

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
		getdetailwork();
		mAction = "INSERT";
		document.all( 'fmAction' ).value = mAction;
		ImpartGrid.delBlankLine();

		if (document.all('ProposalContNo').value == "") {
		  //alert("��ѯ���ֻ�ܽ����޸Ĳ�����");
		  mAction = "";
		} else {
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
		  document.getElementById("fm").submit(); //�ύ
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
	//tongmeng 2007-10-12 modify
	//������ʾ����
	try { showInfo.close(); } catch(e) {}
	
	if( FlagStr == "Fail" )
	{
		//showInfo.close();
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
		//tongmeng 2009-06-02 modify
		//����ˢ�¸��˹�����
		try
		{
			//fm.WorkFlowFlag.value = "0000001002"
			 var tempFlag = fm.WorkFlowFlag.value ;
			 //alert(tempFlag);
			 if(tempFlag!=null&&tempFlag=='0000001002')
			 {
			 	 top.opener.initQuerySelf();
			 	 top.close();
			 }
		}
		catch(e)
		{
		}
      //top.close();
//	  try { goToPic(2) } catch(e) {}

	}
	mAction = "";
}



function afterSubmit4( FlagStr, content )
{
	//alert("here 1!");
	try { showInfo.close(); } catch(e) {}
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
	//start.����������޸Ļ����޸ĺ�ͬ�����ɹ�����У���޸ĺ��Ͷ�����Ƿ������ͬ���ƿͻ�����ʾ����Ͷ����У�顣
	//��Ͷ�����뱻���˵Ĺ�ϵΪ00�����ˣ���ѯ���Ƿ���±�������Ϣ��---yeshu,20071220
	else if(fm.fmAction.value=="UPDATE" && (LoadFlag=="3" || LoadFlag=="1"))
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
		
     if(fm.RelationToInsured.value == "00" )
     {
     //	if(confirm("Ͷ�����뱻���˹�ϵΪ���ˣ�ȷ�ϸ��±�������Ϣ��"))
     //	{
     var showStr="�����޸ı�������Ϣ�����Ժ�...";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

     		updateInsured();
     //}
    //else
    //	{ 
    //		alert("���޸�Ͷ�����뱻���˹�ϵ������������ݻ��ң�");
    //		}
    }
//  	var sqlstr = "select * from lcappnt a,ldperson b "
//		+" where a.appntname=b.name and a.appntsex=b.sex and a.appntbirthday=b.birthday"
//		+" and a.appntno='"+fm.AppntNo.value+"' and a.appntno<>b.customerno"
//		+" union "		
//		+" select * from lcappnt a,ldperson b "
//		+" where a.idtype=b.idtype and b.idtype is not null "
//		+" and b.idno=a.idno and b.idno is not null"
//		+" and a.appntno='"+fm.AppntNo.value+"' and a.appntno<>b.customerno";
		var sqlstr ="";
		
		var sqlid2="BankContInput2";
	    var mySql2=new SqlClass();
	    mySql2.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
    	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
    	mySql2.addSubPara(fm.AppntNo.value);//ָ������Ĳ���
	    mySql2.addSubPara(fm.AppntNo.value);
		sqlstr=mySql2.getString();
		
		var arrResult = easyExecSql(sqlstr,1,0);
        if(arrResult!=null)
        {
				  alert("Ͷ���˴������ƿͻ��������Ͷ����У�顣");
				}
  	
	}
	//end.---yeshu,20071220
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
	try { showInfo.close(); } catch(e) {}
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
		var iHeight=250;     //�������ڵĸ߶�; 
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
	//alert('11111');
	if(verifyWorkFlow(tMissionID,tSubMissionID,ActivityID)==false)
	{
		return false;
	}/////////////////У�鹫�����Ƿ��Ѿ���ת���
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
	if((document.all('AppntBankCode').value!=null&&document.all('AppntBankCode').value!='')
		||(document.all('AppntBankCode').value!=null&&document.all('AppntAccName').value!='')
		||(document.all('AppntBankCode').value!=null&&document.all('AppntBankAccNo').value!='')
		)
		{
			fm.PayMode.value='0';
		}
	//tongmeng 2007-12-05 modify
	//ȥ���˴�
	/*
	if(fm.AppntNo.value==''&&fm.AppntAddressNo.value!='')
  {
    alert("Ͷ���˿ͻ���Ϊ�գ������е�ַ����");
    return false;
  }*/
  //tongmeng 2007-09-11 modify

  //���ӡ��������ڡ���У�� --2010-03-18 --hanbin 
  //���� ��ServiceSpecification  ����֮��ʼУ�� hanbin 2010-04-29
	//2010-5-5 ȥ���������ڵ�У��
//  sql = "select sysvarvalue from ldsysvar where sysvar ='ServiceSpecification'";
//  var arrResult2 = easyExecSql(sql);
//  if(arrResult2!=null && (calAgeNew(arrResult2[0][0],document.all("PolAppntDate").value)>=0))
//  {
//	  if(fm.FirstTrialDate.value < fm.PolAppntDate.value)
//	  {
//	  	 alert("�������ڲ�������Ͷ���������ڣ�");
//	  	 fm.FirstTrialDate.value = "";
//	  	 return false;
//	  }
//	  strSql = "select makedate from es_doc_main a  where a.doccode = '"+fm.ProposalContNo.value+"' and a.busstype = 'TB' and a.subtype = 'UA001'"; 
//	  var scanDate = easyExecSql(strSql);
//	  if(fm.FirstTrialDate.value >  scanDate)
//	  {
//	  	 alert("�������ڲ�������ɨ�����ڣ�");
//	  	 fm.FirstTrialDate.value = "";
//	  	 return false;
//	  }
//  }
  
  if( verifyInputNB('1') == false ) return false;
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
			document.getElementById("fm").submit(); //�ύ
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
	if(verifyWorkFlow(tMissionID,tSubMissionID,ActivityID)==false)
	{
		return false;
	}/////////////////У�鹫�����Ƿ��Ѿ���ת���
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
			document.getElementById("fm").submit(); //�ύ
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
   var tempfeeSQL="";
   
   var sqlid3="BankContInput3";
	var mySql3=new SqlClass();
	mySql3.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
    mySql3.addSubPara(tContNo);
	tempfeeSQL=mySql3.getString();
   
	var TempFeeArr=easyExecSql(tempfeeSQL);	
	if(TempFeeArr!=null)
	{
		tTempFee=TempFeeArr[0][0];
	}
	//��ѯϵͳ���ɵı���
//	var premfeeSQL="select sum(nvl(prem,0)) from lcpol where 1=1 "
//		+" and contno=(select contno from lccont where prtno= '"+tContNo+"')";	
	var premfeeSQL="";	
	var sqlid4="BankContInput4";
	var mySql4=new SqlClass();
	mySql4.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
    mySql4.addSubPara(tContNo);
	premfeeSQL=mySql4.getString();
		
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
/*********************************************************************
 *  �Ѻ�ͬ������Ϣ¼�����ȷ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function inputConfirm(wFlag)
{   
    //У���Ƿ������������
    if(checkOtherQuest()==false)
      return false;
	if (wFlag ==1 ) //¼�����ȷ��
	{
    //alert(ScanFlag);
    //var tStr= "	select * from lwmission where 1=1 and lwmission.activityid in ('0000001001','0000001002','0000001220','0000001230') and lwmission.missionprop1 = '"+fm.ContNo.value+"'";
		var tStr="";
		
		var sqlid5="BankContInput5";
	    var mySql5=new SqlClass();
		mySql5.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
	    mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
	    mySql5.addSubPara(fm.ContNo.value);
		tSql=mySql5.getString();
		
		turnPage.strQueryResult = easyQueryVer3(tSql, 1, 0, 1);
		if (turnPage.strQueryResult) {
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
 
   //alert("BankContInput.js_specScanFlag: "+specScanFlag);
   if(ScanFlag=="1" && specScanFlag != "bposcan"){  //������ɨ���¼��
     fm.WorkFlowFlag.value = "0000001099";
    }
    else if(ScanFlag=="1" && specScanFlag == "bposcan"){   //����Ͷ����¼��
     fm.WorkFlowFlag.value = "0000001094";
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
		  //09-06-09 ����Ͷ�����˹�ϵ�Ǳ��˶�Ͷ��������Ϣ�Ƿ�һ�µ�У��
		  if(fm.RelationToInsured.value == "00" ){
			  if(fm.AppntNo.value!=fm.InsuredNo.value){
				  alert("Ͷ����"+fm.AppntNo.value+" �뱻����"+fm.InsuredNo.value+"��Ϣ��һ�£���ȷ��Ͷ�����뱻���˹�ϵ��");
				  return false;
			  }
		  }
  	if(document.all('ProposalContNo').value == "")
	   {
	   	  alert("δ��ѯ����ͬ��Ϣ,������������ [�����޸����] ȷ�ϣ�");
	   	  return;
	   }
	  //tongmeng 2008-01-18 add
	  //�������б���������У��
	  if(!verifyInputNB('1'))
	  {
	  	return false;
	  	}
	  if(!verifyInputNB('2'))
	  {
	  	return false;
	  }
		fm.WorkFlowFlag.value = "0000001002";					//�����޸����
		//tongmeng 2007-11-26 add 
		//���Ӷ��Ƿ�������������ظ��Ľ���
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
//	alert("û��У��ס��");return false;
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
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

function checkOtherQuest(){
   if(ActivityID=='0000001002')
   {
   	//var tSql ="select missionprop9 from lwmission where activityid = '0000001002' and missionprop1 ='"+prtNo+"' and defaultoperator='"+operator+"' and  processid = '0000000003' ";
	var tSql="";
	var sqlid6="BankContInput6";
	var mySql6=new SqlClass();
	mySql6.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
    mySql6.addSubPara(prtNo);
	mySql6.addSubPara(operator);
	tSql=mySql6.getString();
	
  	 var tOtherQuestFlag = easyExecSql(tSql);
   	if(tOtherQuestFlag !=""&& tOtherQuestFlag!=null)
  	 {
   	   if(tOtherQuestFlag !="1")
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
    try { mSwitch.addVar('RelationToInsured','',fm.RelationToInsured.value); } catch(ex) { };
    try{mSwitch.addVar('AppntName','',fm.AppntName.value);}catch(ex){};
    try{mSwitch.addVar('AppntSex','',fm.AppntSex.value);}catch(ex){};
    try{mSwitch.addVar('AppntBirthday','',fm.AppntBirthday.value);}catch(ex){};
    try{mSwitch.addVar('AppntIDType','',fm.AppntIDType.value);}catch(ex){};
    try{mSwitch.addVar('AppntIDNo','',fm.AppntIDNo.value);}catch(ex){};
    try{mSwitch.addVar('AppIDPeriodOfValidity','',fm.AppIDPeriodOfValidity.value);}catch(ex){};

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
    try{mSwitch.addVar('IDPeriodOfValidity','',fm.IDPeriodOfValidity.value);}catch(ex){};

   //�µ����ݴ���
try { mSwitch.addVar('AppntNo','',fm.AppntNo.value); } catch(ex) { };
//tongmeng 2007-12-18 add
try { mSwitch.addVar('RelationToInsured','',fm.RelationToInsured.value); } catch(ex) { };
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
try { mSwitch.addVar('AppIDPeriodOfValidity','',fm.AppIDPeriodOfValidity.value); } catch(ex) { };


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
   try { mSwitch.deleteVar('RelationToInsured'); } catch(ex) { };
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
   try { mSwitch.deleteVar('AppIDPeriodOfValidity'); } catch(ex) { };

    //�µ�ɾ�����ݴ���
try { mSwitch.deleteVar  ('AppntNo'); } catch(ex) { };
try { mSwitch.deleteVar  ('RelationToInsured'); } catch(ex) { };
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
try { mSwitch.deleteVar  ('AppntPhone'); } catch(ex) { };
try { mSwitch.deleteVar  ('CompanyAddress'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntGrpZipCode'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntGrpFax'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntBankAccNo'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntBankCode'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppntAccName'); } catch(ex) { };
try { mSwitch.deleteVar  ('AppIDPeriodOfValidity'); } catch(ex) { };
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
			
			var sqlid7="BankContInput7";
			var mySql7=new SqlClass();
			mySql7.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
		    mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
		    mySql7.addSubPara(arrQueryResult[0][0]);
			var tSql=mySql7.getString();
			
			//arrResult = easyExecSql("select ProposalContNo,PrtNo,ManageCom,SaleChnl,AgentCode,AgentGroup,AgentCode1,AgentCom,AgentType,Remark from LCCont where LCCont = '" + arrQueryResult[0][0] + "'", 1, 0);
			arrResult = easyExecSql(tSql, 1, 0);

			if (arrResult == null) {
			  alert("δ�鵽Ͷ������Ϣ");
			} else {
			   displayLCContPol(arrResult[0]);
			}
		}

		if( mOperate == 2 )	{		// Ͷ������Ϣ
			//alert("arrQueryResult[0][0]=="+arrQueryResult[0][0]);
			
			var sqlid8="BankContInput8";
			var mySql8=new SqlClass();
			mySql8.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
		    mySql8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
		    mySql8.addSubPara(arrQueryResult[0][0]);
			var tSql=mySql8.getString();
			
			//arrResult = easyExecSql("select a.*  from LDPerson a where 1=1  and a.CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0);
			arrResult = easyExecSql(tSql, 1, 0);
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
	
//	detailInitServer();
//	return;
	
	//alert('1');
  try{

    if(PrtNo==null||PrtNo=='') return;

    //��ѯ�����˺�
    //alert("PrtNo=="+PrtNo);
//    var accSQL = "select a.BankCode,a.AccName,a.BankAccNo from LJTempFeeClass a,LJTempFee b "
//               + "where trim(a.TempFeeNo)=trim(b.TempFeeNo) and a.PayMode='4' and b.TempFeeType='1' "
//               + "and b.OtherNo='"+PrtNo+"'";
    var accSQL="";
	var sqlid9="BankContInput9";
	var mySql9=new SqlClass();
	mySql9.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql9.setSqlId(sqlid9);//ָ��ʹ�õ�Sql��id
    mySql9.addSubPara(PrtNo);
	accSQL=mySql9.getString();
    arrResult = easyExecSql(accSQL,1,0);

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
//alert('10');
    var accSQL10="";
    var sqlid10="BankContInput10";
	var mySql10=new SqlClass();
	mySql10.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql10.setSqlId(sqlid10);//ָ��ʹ�õ�Sql��id
    mySql10.addSubPara(PrtNo);
	accSQL10=mySql10.getString();
	
    //arrResult=easyExecSql("select * from LCCont where PrtNo='"+PrtNo+"'",1,0);
    arrResult=easyExecSql(accSQL10,1,0);
	
    if(arrResult==null){
      alert(δ�õ�Ͷ������Ϣ);
      return;
    }
    else{
      displayLCCont();       //��ʾͶ������ϸ����
    }
//alert('11');
    var accSQL11="";
    var sqlid11="BankContInput11";
	var mySql11=new SqlClass();
	mySql11.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql11.setSqlId(sqlid11);//ָ��ʹ�õ�Sql��id
    mySql11.addSubPara(arrResult[0][19] );
	accSQL11=mySql11.getString();
    //arrResult = easyExecSql("select a.* from LDPerson a where 1=1  and a.CustomerNo = '" + arrResult[0][19] + "'", 1, 0);
	arrResult = easyExecSql(accSQL11, 1, 0);

    if (arrResult == null) {
      alert("δ�鵽Ͷ���˿ͻ�����Ϣ");
    }
    else{
      //��ʾͶ������Ϣ
      displayAppnt(arrResult[0]);
    }

    var accSQL12="";
    var sqlid12="BankContInput12";
	var mySql12=new SqlClass();
	mySql12.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql12.setSqlId(sqlid12);//ָ��ʹ�õ�Sql��id
    mySql12.addSubPara(PrtNo);
	accSQL12=mySql12.getString();

    //arrResult=easyExecSql("select * from LCAppnt where PrtNo='"+PrtNo+"'",1,0);
	arrResult=easyExecSql(accSQL12,1,0);

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
    var strSQL0="";//"select ImpartParam from LCCustomerImpartparams where CustomerNoType='0' and CustomerNo='"+tCustomerNo+"' and ContNo='"+tContNo+"' and impartver='01' and impartcode='001' and ImpartParamNo = '1'";
    var strSQL1="";//"select ImpartParam from LCCustomerImpartparams where CustomerNoType='0' and CustomerNo='"+tCustomerNo+"' and ContNo='"+tContNo+"' and impartver='01' and impartcode='001' and ImpartParamNo = '2'";
    
    var sqlid13="BankContInput13";
	var mySql13=new SqlClass();
	mySql13.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql13.setSqlId(sqlid13);//ָ��ʹ�õ�Sql��id
    mySql13.addSubPara(tCustomerNo);
	mySql13.addSubPara(tContNo);
	strSQL0=mySql13.getString();
	
    var sqlid14="BankContInput14";
	var mySql14=new SqlClass();
	mySql14.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql14.setSqlId(sqlid14);//ָ��ʹ�õ�Sql��id
    mySql14.addSubPara(tCustomerNo);
	mySql14.addSubPara(tContNo);
	strSQL1=mySql14.getString();
	
	arrResult = easyExecSql(strSQL0,1,0);
    arrResult1 = easyExecSql(strSQL1,1,0);


	    try{document.all('Income0').value= arrResult[0][0];}catch(ex){};
	    try{document.all('IncomeWay0').value= arrResult1[0][0];}catch(ex){};

    var strSQL1="";//"select ImpartVer,ImpartCode,ImpartContent,ImpartParamModle from LCCustomerImpart where CustomerNoType='0' and CustomerNo='"+tCustomerNo+"' and ContNo='"+tContNo+"' and impartver in ('01','02') and impartcode<>'001'";
    var sqlid15="BankContInput15";
	var mySql15=new SqlClass();
	mySql15.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql15.setSqlId(sqlid15);//ָ��ʹ�õ�Sql��id
    mySql15.addSubPara(tCustomerNo);
	mySql15.addSubPara(tContNo);
	strSQL1=mySql15.getString();
    turnPage.strQueryResult = easyQueryVer3(strSQL1, 1, 0, 1);

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


   var aSQL="";//"select ImpartVer,ImpartCode,ImpartContent,ImpartParamModle from LCCustomerImpart where CustomerNoType='2' and CustomerNo='"+tCustomerNo+"' and ContNo='"+tContNo+"'";
   var sqlid16="BankContInput16";
	var mySql16=new SqlClass();
	mySql16.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql16.setSqlId(sqlid16);//ָ��ʹ�õ�Sql��id
    mySql16.addSubPara(tCustomerNo);
	mySql16.addSubPara(tContNo);
	aSQL=mySql16.getString();
	
   turnPage.queryModal(aSQL, AgentImpartGrid);


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
try{document.all('AppntPhone').value= arrResult[0][6];}catch(ex){};
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
	//alert("111:"+arrResult[0][43]);
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
  //try{document.all('AppntBankCode').value= arrResult[0][22];}catch(ex){};
  //try{document.all('AppntBankAccNo').value= arrResult[0][23];}catch(ex){};
  //try{document.all('AppntAccName').value= arrResult[0][24]; }catch(ex){};
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
  try{document.all('RelationToInsured').value= arrResult[0][43];}catch(ex){};
  try{document.all('AppIDPeriodOfValidity').value= arrResult[0][45];}catch(ex){};
 
}
/*********************************************************************
 *  �Ѳ�ѯ���ص�Ͷ����������ʾ��Ͷ���˲���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayAppnt()
{
	//alert("here in 928 col"+arrResult[0][44]);
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
//alert(document.all('AppntGrpName').value);
try{document.all('AppntLicenseType').value= arrResult[0][43];}catch(ex){};
try{document.all('AppIDPeriodOfValidity').value= arrResult[0][47];}catch(ex){};
//tongmeng 2007-12-06 
//����Ͷ���˹�ϵ�ֶ�
//try{document.all('RelationToInsured').value= arrResult[0][44];}catch(ex){};

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
try{document.all('AppntPhone').value= "";}catch(ex){};
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
    try { document.all('PolType').value = arrResult[0][7]; } catch(ex) { };
    try { document.all('CardFlag').value = arrResult[0][8]; } catch(ex) { };
    try { document.all('ManageCom').value = arrResult[0][9]; } catch(ex) { };
    try { document.all('ExecuteCom ').value = arrResult[0][10]; } catch(ex) { };
    try { document.all('AgentCom').value = arrResult[0][11]; } catch(ex) { };
    try { document.all('AgentCode').value = arrResult[0][12]; } catch(ex) { };
    try { document.all('AgentGroup').value = arrResult[0][13]; } catch(ex) { }; 
    try { document.all('AgentCode1').value = arrResult[0][14]; } catch(ex) { };
    //alert("bb");
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
    try { document.all('AppntBankCode').value = arrResult[0][41]; } catch(ex) { };
    try { document.all('AppntBankAccNo').value = arrResult[0][42]; } catch(ex) { };
    try { document.all('AppntAccName').value = arrResult[0][43]; } catch(ex) { };
    try { document.all('PayMode').value = arrResult[0][93]; } catch(ex) { };
    //tongmeng 2009-04-15 Add
		//���ӳ���ǩ��
		try { document.all('SignName').value = arrResult[0][100]; } catch(ex) { };
		
	//���ӡ��������ڡ� 2010-03-18 -hanbin 
	
	try { document.all('FirstTrialDate').value = arrResult[0][81]; } catch(ex) { };		
	try { document.all('XQremindFlag').value = arrResult[0][108]; } catch(ex) { };	
	showOneCodeName('XQremindFlag','XQremindFlag','XQremindFlagName');

}
//ʹ�ôӸô��ڵ����Ĵ����ܹ��۽�
/*function myonfocus()
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

*/

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
  	var sql17="";
  	var sqlid17="BankContInput17";
	var mySql17=new SqlClass();
	mySql17.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql17.setSqlId(sqlid17);//ָ��ʹ�õ�Sql��id
    mySql17.addSubPara(fm.AppntNo.value);
	sql17=mySql17.getString();
	
   // arrResult = easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo = '" + document.all("AppntNo").value + "'", 1, 0);
    arrResult = easyExecSql(sql17, 1, 0);
    if (arrResult == null) {
      alert("δ�鵽Ͷ������Ϣ");
      displayAppnt(new Array());
      emptyUndefined();
    }
    else
    {
      displayAppnt(arrResult[0]);
      getdetailaddress();
      showCodeName();
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
	//add by wangyc
	//reutrn;
	
	//alert("document.all('AgentCode').value==="+document.all('AgentCode').value);
	/*if(document.all('ManageCom').value==""){
		 alert("����¼����������Ϣ��");
		 return;
	}*/
  if(document.all('AgentCode').value == "")	{
	  //var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+",AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+"&branchtype=3","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}
	if(document.all('AgentCode').value != ""&& document.all('AgentCode').value.length==10){
	  var cAgentCode = fm.AgentCode.value;  //��������
    //alert("cAgentCode=="+cAgentCode);
    //���ҵ��Ա���볤��Ϊ8���Զ���ѯ����Ӧ�Ĵ������ֵ���Ϣ
    //alert("cAgentCode=="+cAgentCode);
    /*if(cAgentCode.length!=8){
    	return;
    }*/
	  //var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"' and ManageCom = '"+document.all('ManageCom').value+"'";
   
   var sql18="";
  	var sqlid18="BankContInput18";
	var mySql18=new SqlClass();
	mySql18.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql18.setSqlId(sqlid18);//ָ��ʹ�õ�Sql��id
    mySql18.addSubPara(cAgentCode);
	sql18=mySql18.getString();
    var strSQL=sql18;
   	//var strSQL = "select a.AgentCode,a.AgentGroup,a.ManageCom,a.Name,c.BranchManager,b.IntroAgency,b.AgentSeries,b.AgentGrade,c.BranchAttr,b.AscriptSeries,c.name from LAAgent a,LATree b,LABranchGroup c where 1=1 "
	 //        + "and a.AgentCode = b.AgentCode and a.AgentGroup = c.AgentGroup and a.AgentState < '03'  and a.AgentCode='"+cAgentCode+"' ";//and a.managecom='"+document.all("ManageCom").value+"'";
   //alert(strSQL);
    var arrResult = easyExecSql(strSQL); 
    //alert(arrResult);
    if (arrResult != null) {
    	fm.AgentCode.value = arrResult[0][0];
    	fm.BranchAttr.value = arrResult[0][8];
    	fm.AgentGroup.value = arrResult[0][1];
  	  fm.AgentName.value = arrResult[0][3];
      //fm.AgentManageCom.value = arrResult[0][2];
//      alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][3]+"]");
    }
    else
    {
      fm.AgentCode.value ="";
    	fm.BranchAttr.value ="";
    	fm.AgentGroup.value ="";
  	  fm.AgentName.value ="";
     alert("����Ϊ:["+cAgentCode+"]��ҵ��Ա�����ڣ����߲��ڸù�������£������Ѿ���ְ�� ��ȷ��!");
    }
	}
	initManageCom();
}

//��ѯ�������
function initManageCom()
{	
	fm.ManageCom.value = '';
	fm.ManageComName.value = '';
	
			if(fm.AgentCom.value != null && fm.AgentCom.value != "")
			{
				var sql19="";
			  	var sqlid19="BankContInput19";
				var mySql19=new SqlClass();
				mySql19.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
			    mySql19.setSqlId(sqlid19);//ָ��ʹ�õ�Sql��id
			    mySql19.addSubPara(fm.AgentCom.value);
				sql19=mySql19.getString();
				var strSql=sql19;
				//var strSql = "select ManageCom,(select name from ldcom where comcode=managecom),branchtype from LACom where AgentCom='" + fm.AgentCom.value +"'";
			    var arrResult = easyExecSql(strSql);
			       //alert(arrResult);
			    if (arrResult != null) {      
			      fm.ManageCom.value = arrResult[0][0];
			      fm.ManageComName.value = arrResult[0][1];
			      if(arrResult[0][2]!=null &&��arrResult[0][2]=='6')
			      {
			      	var cManageCom = '';
			      	if(document.all('PrtNo').value != "")
					{
						var sql20="";
					  	var sqlid20="BankContInput20";
						var mySql20=new SqlClass();
						mySql20.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
					    mySql20.setSqlId(sqlid20);//ָ��ʹ�õ�Sql��id
					    mySql20.addSubPara(fm.PrtNo.value);
						sql20=mySql20.getString();
					    var sql=sql20;
						//var sql = "SELECT managecom FROM es_doc_main where doccode='"+ trim(document.all('PrtNo').value) + "' and subtype='UA001'";
						cManageCom = easyExecSql(sql);	
					}
			      	if(cManageCom != null && cManageCom[0][0] != "")
			      	{
			      		var length = cManageCom[0][0].length;
			      		for(var i=length;i<8;i++)
			      		{
			      			cManageCom[0][0] = cManageCom[0][0] +'0';
			      		}
			      		fm.ManageCom.value = cManageCom[0][0];
						
						var sql21="";
					  	var sqlid21="BankContInput21";
						var mySql21=new SqlClass();
						mySql21.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
					    mySql21.setSqlId(sqlid21);//ָ��ʹ�õ�Sql��id
					    mySql21.addSubPara(cManageCom[0][0]);
						sql21=mySql21.getString();
						var sql =sql21;
						
			      		//var sql = "select name from ldcom where comcode='"+cManageCom[0][0]+"'";
						var arrResult1 = easyExecSql(sql);	
						if(arrResult1 != null)
						{							
			     			fm.ManageComName.value = arrResult1[0][0];
						}						
			      	}			      	
			      }
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
		window.open("../uw/QuestInputMain.jsp?ContNo="+cContNo+"&Flag="+ LoadFlag,"window1",sFeatures);
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
  	  initManageCom();
      //fm.AgentManageCom.value = arrResult[0][2];

  	//showContCodeName();
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
//	var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"' and ManageCom = '"+document.all('ManageCom').value+"'";
   	
	var sql22="";
	var sqlid22="BankContInput22";
	var mySql22=new SqlClass();
	mySql22.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql22.setSqlId(sqlid22);//ָ��ʹ�õ�Sql��id
	mySql22.addSubPara(cAgentCode);
	mySql22.addSubPara(fm.ManageCom.value);
	sql22=mySql22.getString();
	var strSQL =sql22;
	
//	var strSQL = "select a.AgentCode,a.AgentGroup,a.ManageCom,a.Name,c.BranchManager,b.IntroAgency,b.AgentSeries,b.AgentGrade,c.BranchAttr,b.AscriptSeries,c.name from LAAgent a,LATree b,LABranchGroup c where 1=1 "
//	         + "and a.AgentCode = b.AgentCode and a.AgentGroup = c.AgentGroup and a.AgentState < '03' and a.AgentCode='"+cAgentCode+"' and a.managecom='"+document.all("ManageCom").value+"'";
    var arrResult = easyExecSql(strSQL);
       //alert(arrResult);
    if (arrResult != null) {
      fm.AgentCode.value = arrResult[0][0];
    	fm.BranchAttr.value = arrResult[0][8];
    	fm.AgentGroup.value = arrResult[0][1];
  	  fm.AgentName.value = arrResult[0][3];
      alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][3]+"]");
    }
    else{
     fm.AgentCode.value = "";
    	fm.BranchAttr.value = "";
    	fm.AgentGroup.value = "";
  	  fm.AgentName.value = "";
     alert("����Ϊ:["+cAgentCode+"]�Ĵ����˲����ڣ����߲��ڸù�������£������Ѿ���ְ����ȷ��!");
     }
	}
}
function getdetail()
{
	//alert("fm.AppntNo.value=="+fm.AppntNo.value);
	
	var sql23="";
	var sqlid23="BankContInput23";
	var mySql23=new SqlClass();
	mySql23.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql23.setSqlId(sqlid23);//ָ��ʹ�õ�Sql��id
	mySql23.addSubPara(fm.AppntBankAccNo.value);
	mySql23.addSubPara(fm.AppntNo.value);
	sql23=mySql23.getString();
	var strSql =sql23;
	
	//var strSql = "select BankCode,AccName from LCAccount where BankAccNo='" + fm.AppntBankAccNo.value+"' and customerno='"+fm.AppntNo.value+"'";
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
	var sql24="";
	var sqlid24="BankContInput24";
	var mySql24=new SqlClass();
	mySql24.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql24.setSqlId(sqlid24);//ָ��ʹ�õ�Sql��id
	mySql24.addSubPara(fm.AppntOccupationCode.value);
	sql24=mySql24.getString();
	var strSql =sql24;
	
  //var strSql = "select OccupationType from LDOccupation where OccupationCode='" + fm.AppntOccupationCode.value+"'";
  var arrResult = easyExecSql(strSql);
  if (arrResult != null) 
  {
    fm.AppntOccupationType.value = arrResult[0][0];
	showOneCodeName('occupationtype','AppntOccupationType','AppntOccupationTypeName');
  }
  else
  {
    fm.AppntOccupationType.value ="";
    fm.AppntOccupationTypeName.value ="";
  }
}

/********************
**
** ��ѯͶ���˵�ַ��Ϣ
**
*********************/
function getdetailaddress(){
	
	var sql25="";
	var sqlid25="BankContInput25";
	var mySql25=new SqlClass();
	mySql25.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql25.setSqlId(sqlid25);//ָ��ʹ�õ�Sql��id
	mySql25.addSubPara(fm.AppntAddressNo.value);
	mySql25.addSubPara(fm.AppntNo.value);
	sql25=mySql25.getString();
	var strSQL =sql25;
	
 	//var strSQL="select b.* from LCAddress b where b.AddressNo='"+fm.AppntAddressNo.value+"' and b.CustomerNo='"+fm.AppntNo.value+"'";
        arrResult=easyExecSql(strSQL);
  //alert("arrResult[0][27]=="+arrResult[0][27]);
	displayDetailAddress(arrResult);
}

function displayDetailAddress(arrResult)
{
   try{document.all('AppntNo').value= arrResult[0][0];}catch(ex){};
   try{document.all('AppntAddressNo').value= arrResult[0][1];}catch(ex){};
   try{document.all('AddressNoName').value= arrResult[0][2];}catch(ex){};
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
   try{
   		document.all('AppntPhone').value= arrResult[0][4];
   		if(document.all('AppntPhone').value==null||document.all('AppntPhone').value=="")
   		{
   			document.all('AppntPhone').value=arrResult[0][4];
   		}
   	}catch(ex){};
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
		
	var sql26="";
	var sqlid26="BankContInput26";
	var mySql26=new SqlClass();
	mySql26.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql26.setSqlId(sqlid26);//ָ��ʹ�õ�Sql��id
	mySql26.addSubPara(fm.AppntNo.value);
	sql26=mySql26.getString();
	strsql =sql26;
		
    //strsql = "select AddressNo,PostalAddress from LCAddress where CustomerNo ='"+fm.AppntNo.value+"'";
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
    //return tCodeData;
    document.all("AppntAddressNo").CodeData=tCodeData;
}
function afterCodeSelect( cCodeName, Field )
{
 //alert("afdasdf");
 if(cCodeName=="GetAddressNo"){
 	var strSQL="select b.* from LCAddress b where b.AddressNo='"+fm.AppntAddressNo.value+"' and b.CustomerNo='"+fm.AppntNo.value+"'";
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
   try{document.all('AppntPhone').value= arrResult[0][12];}catch(ex){};
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
//	showOneCodeName('province','AppntProvince','AppntProvinceName');
//	showOneCodeName('city','AppntCity','AppntCityName');
//	showOneCodeName('district','AppntDistrict','AppntDistrictName');
	getAddressName('province','AppntProvince','AppntProvinceName');
	getAddressName('city','AppntCity','AppntCityName');
	getAddressName('district','AppntDistrict','AppntDistrictName');
   return;
 }
  if(cCodeName=="GetInsuredAddressNo"){
 	var strSQL="select b.* from LCAddress b where b.AddressNo='"+fm.InsuredAddressNo.value+"' and b.CustomerNo='"+fm.InsuredNo.value+"'";
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
 
	if(cCodeName=="BankAgentCode")
	{     
		var tAgentCode=document.all('AgentCode').value;
		//alert(tAgentCode); 
		
		var sql27="";
		var sqlid27="BankContInput27";
		var mySql27=new SqlClass();
		mySql27.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql27.setSqlId(sqlid27);//ָ��ʹ�õ�Sql��id
		mySql27.addSubPara(tAgentCode);
		sql27=mySql27.getString();
		var sqlstr =sql27;
		
		//var sqlstr="select name,agentgroup,managecom from labranchgroup where agentgroup=(select agentgroup from laagent where agentcode='" + tAgentCode+"')" ;
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
	 
	if (cCodeName == "AgentCom") {
    	initManageCom();
    }

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
                document.all('AppntPhone').value= document.all('AppntPhone').value;
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
  var sqlstr="select * from ldperson where Name='"+fm.AppntName.value
             + "' and Sex='"+Sex+"' and Birthday='"+fm.AppntBirthday.value
             + "' and CustomerNo<>'"+fm.AppntNo.value+"'"
             + " union select * from ldperson where 1=1"
             + " and IDType = '"+fm.AppntIDType.value
             + "' and IDType is not null "
             + " and IDNo = '"+fm.AppntIDNo.value
             + "' and IDNo is not null and CustomerNo<>'"+fm.AppntNo.value+"'" ;
        arrResult = easyExecSql(sqlstr,1,0);
        if(arrResult==null)
        {
				  alert("��û�����Ͷ�������ƵĿͻ�,����У��");
				  return false;
        }else{

					window.open("../uw/AppntChkMain.jsp?ProposalNo1="+fm.ContNo.value+"&Flag=A&LoadFlag="+LoadFlag,"window1",sFeatures);
				}
}

//�ڳ�ʼ��bodyʱ�Զ�Ч��Ͷ������Ϣ��
//�ж�ϵͳ���Ƿ�����������Ա𡢳���������ͬ��֤������֤��������ͬ�ı�������Ϣ��
function AppntChkNew(){
  //alert("aa");
  var Sex=fm.AppntSex.value;
  var sql28="";
  var sqlid28="BankContInput28";
  var mySql28=new SqlClass();
  mySql28.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
  mySql28.setSqlId(sqlid28);//ָ��ʹ�õ�Sql��id
  mySql28.addSubPara(fm.AppntName.value);
  mySql28.addSubPara(Sex);
  mySql28.addSubPara(fm.AppntBirthday.value);
  
  mySql28.addSubPara(fm.AppntNo.value);
  mySql28.addSubPara(fm.AppntIDNo.value);
  mySql28.addSubPara(fm.AppntNo.value);
  
  sql28=mySql28.getString();
  var sqlstr =sql28;
  
  
//  var sqlstr="select * from ldperson where Name='"+fm.AppntName.value
//             + "' and Sex='"+Sex+"' and Birthday='"+fm.AppntBirthday.value
//             + "' and CustomerNo<>'"+fm.AppntNo.value+"'"
//             + " union select * from ldperson where 1=1 " 
//             + " and IDNo = '"+fm.AppntIDNo.value
//             + "' and IDNo is not null and CustomerNo<>'"+fm.AppntNo.value+"'" ;
  //alert("sqlstr="+sqlstr);
  arrResult = easyExecSql(sqlstr,1,0);
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
	
  var sql29="";
  var sqlid29="BankContInput29";
  var mySql29=new SqlClass();
  mySql29.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
  mySql29.setSqlId(sqlid29);//ָ��ʹ�õ�Sql��id
  sql29=mySql29.getString();
  strsql =sql29;
	
    //strsql = "select CustomerNo,GrpName from LDgrp ";
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
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom=&queryflag=1","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}

	if(document.all( Field ).all('MultiAgentGrid1').value != "")	 {
	var cAgentCode = document.all( Field ).all('MultiAgentGrid1').value;  //��������
	
  var sql30="";
  var sqlid30="BankContInput30";
  var mySql30=new SqlClass();
  mySql30.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
  mySql30.setSqlId(sqlid30);//ָ��ʹ�õ�Sql��id
  mySql30.addSubPara(cAgentCode);
  sql30=mySql30.getString();
  var strSql =sql30;
	
	//var strSql = "select AgentCode from LAAgent where AgentCode='" + cAgentCode +"'";
    var arrResult = easyExecSql(strSql);
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
function checkapplydate()
{
	if(trim(fm.PolAppntDate.value)==""){return ;}
    else if (fm.PolAppntDate.value.length == 8)
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
		}else{
			alert("�������Ͷ�����ڸ�ʽ����!");
			fm.PolAppntDate.value = "";  
			return;
		}
	}
	else if(fm.PolAppntDate.value.length == 10)
	{
		var Year =     fm.PolAppntDate.value.substring(0,4);
		var Month =    fm.PolAppntDate.value.substring(5,7);
		var Day =      fm.PolAppntDate.value.substring(8,10);
		if(Year=="0000"||Month=="00"||Day=="00")
		{
		alert("�������Ͷ����������!");
		fm.PolAppntDate.value = "";
		fm.PolAppntDate.focus();
		return;
		}
	}
	else
	{
		alert("�������Ͷ����������!");
		fm.PolAppntDate.value = "";
		fm.PolAppntDate.focus();
	}
		//����ϵͳ��¼���Ͷ������У��
	if(checkPolDate(fm.ProposalContNo.value,fm.PolAppntDate.value)==false)
	{
	fm.PolAppntDate.value="";
	fm.PolAppntDate.focus();
	return;
	}

}


//У�顾�������ڡ� -2010-03-19 - hanbin
function checkFirstTrialDate()
{
	if(trim(fm.FirstTrialDate.value)==""){return ;}
    else if (fm.FirstTrialDate.value.length == 8)
	{
		if(fm.FirstTrialDate.value.indexOf('-')==-1)
		{ 
			var Year =     fm.FirstTrialDate.value.substring(0,4);
			var Month =    fm.FirstTrialDate.value.substring(4,6);
			var Day =      fm.FirstTrialDate.value.substring(6,8);
			fm.FirstTrialDate.value = Year+"-"+Month+"-"+Day;
			if(Year=="0000"||Month=="00"||Day=="00")
			{
			 alert("�������Ͷ����������!");
			fm.FirstTrialDate.value = "";  
			return;
			}
		}else{
			alert("�������Ͷ�����ڸ�ʽ����!");
			fm.FirstTrialDate.value = "";  
			return;
		}
	}
	else if(fm.FirstTrialDate.value.length == 10)
	{
		var Year =     fm.FirstTrialDate.value.substring(0,4);
		var Month =    fm.FirstTrialDate.value.substring(5,7);
		var Day =      fm.FirstTrialDate.value.substring(8,10);
		if(Year=="0000"||Month=="00"||Day=="00")
		{
		alert("�������Ͷ����������!");
		fm.FirstTrialDate.value = "";
		fm.FirstTrialDate.focus();
		return;
		}
	}
	else
	{
		alert("�������Ͷ����������!");
		fm.FirstTrialDate.value = "";
		fm.FirstTrialDate.focus();
	}

}
/******************************************************************************
* ���У�飬����ϵͳ��¼���Ͷ������У�飬У�����Ϊ��¼���Ͷ�����ڿ�����¼����ϵͳ��������60����
* ���������ContNo---��ͬ�ţ�ӡˢ�ţ���  PolAppntDate---Ͷ������
*******************************************************************************/
function checkPolDate(ContNo,PolAppntDate)
{
	var tContNo = ContNo;//��ͬ��
    var tPolAppntDate = PolAppntDate;//Ͷ������
     //Ͷ������ֻ��Ϊ��ǰ������ǰ
    if (calAge(tPolAppntDate) < 0)
    {
        alert("Ͷ������ֻ��Ϊ��ǰ������ǰ!");
        return false;
    }
    var DayIntv=60;//¼��������Ͷ�����ڵı�׼���������Ĭ��Ϊ60��
    //��ѯ¼��������Ͷ�����ڵı�׼�������
	
  var sql31="";
  var sqlid31="BankContInput31";
  var mySql31=new SqlClass();
  mySql31.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
  mySql31.setSqlId(sqlid31);//ָ��ʹ�õ�Sql��id
  sql31=mySql31.getString();
  
  var DayIntvArr = easyExecSql(sql31);
    //var DayIntvArr = easyExecSql("select sysvarvalue from ldsysvar where sysvar='input_poldate_intv'");
    if (DayIntvArr != null) { DayIntv = DayIntvArr[0][0]; }
    //���ݺ�ͬ�Ż�ȡ¼������<��ѯ��ͬ��������ȡ֮������ȡϵͳ��ǰ����>
    var tMakeDate = "";//¼����ϵͳ��������
    if(tContNo!=null && tContNo!="")
    {
		var sql32="";
        var sqlid32="BankContInput32";
        var mySql32=new SqlClass();
        mySql32.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
        mySql32.setSqlId(sqlid32);//ָ��ʹ�õ�Sql��id
        mySql32.addSubPara(tContNo);
        sql32=mySql32.getString();
        var makedatesql=sql32;
		
		//var makedatesql = "select contno,prtno,makedate from lccont where prtno='" + tContNo + "'";
		var makedatearr = easyExecSql(makedatesql);
		if (makedatearr != null){ tMakeDate = makedatearr[0][2];}
    }
    if(tMakeDate=="")  //¼����ϵͳ��������Ϊ�գ��� Ĭ��ϵͳ����
    {    
    	var sysdatearr = easyExecSql("select to_char(now(),'yyyy-mm-dd') from dual");
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
//	fm.AppntAge.value=calAge(fm.AppntBirthday.value);
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
//    fm.InsuredAppAge.value=calAge(fm.Birthday.value);
    fm.InsuredAppAge.value=calPolAppntAge(fm.Birthday.value,fm.PolAppntDate.value);
  	return ;
}



//¼��У�鷽��
//���������verifyOrderУ���˳��
//ҵ�������ýӿڣ����ͨ��У�鷵��true�����򷵻�false
function verifyInputNB(verifyOrder)  
{
	//alert('!!!:'+verifyOrder)
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
	unlockScreen('lkscreen');
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
  
//  alert();
//  if(InsuredGrid.mulLineCount>0){
//    document.all('spanInsuredGrid0').all('InsuredGridSel').checked='true'
//  }

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
	if(verifyWorkFlow(tMissionID,tSubMissionID,ActivityID)==false)
	{
		return false;
	}/////////////////У�鹫�����Ƿ��Ѿ���ת���
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
      window.open("../uw/QuestQueryMain.jsp?ContNo="+cContNo+"&Flag="+LoadFlag,"window1","",sFeatures);
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
	delInsuredVar();
	addInsuredVar();
  	try{mSwitch.addVar('SelPonNo','',fm.SelPolNo.value);}catch(ex){} //ѡ�����ֵ��������ֽ�������ѱ������Ϣ
	if ((LoadFlag=='5'||LoadFlag=='4'||LoadFlag=='6'||LoadFlag=='16')&&(mSwitch.getVar( "PolNo" ) == null || mSwitch.getVar( "PolNo" ) == ""))
	{
		alert("����ѡ�񱻱�����������Ϣ��");
		return;
	}
	//������ƣ���¼����������޸�ʱ������������ֱ�����ѡ��һ�����ֺ���ܽ���������Ϣ
	if((LoadFlag=='1'||LoadFlag=='3') && (PolGrid.mulLineCount>0))
	{
		var tSelPol=PolGrid.getSelNo();
		if(tSelPol==null || tSelPol==0)
		{
			alert("����ѡ��һ�����ֺ��ٽ���������Ϣҳ�棡");
			return;
		}
	}
	try{mSwitch.addVar('SelPolNo','',fm.SelPolNo.value);}catch(ex){}
	try{mSwitch.deleteVar('ContNo');}catch(ex){}
	try{mSwitch.addVar('ContNo','',fm.ContNo.value);}catch(ex){}
	try{mSwitch.updateVar('ContNo','',fm.ContNo.value);}catch(ex){}
	try{mSwitch.deleteVar('mainRiskPolNo');}catch(ex){}
	try{mSwitch.deleteVar('CValiDate');}catch(ex){}
	try{mSwitch.addVar('CValiDate','',document.all('PolAppntDate').value);}catch(ex){}
  
  	parent.fraInterface.window.location = "./ProposalInput.jsp?LoadFlag=" + LoadFlag+"&ContType="+ContType+"&scantype=" + scantype+ "&MissionID=" + MissionID+ "&SubMissionID=" + SubMissionID+"&BQFlag="+BQFlag+"&EdorType="+EdorType+"&ActivityID="+ActivityID+"&NoType="+NoType+"&hh=1&checktype="+checktype+"&ProposalContNo="+fm.ProposalContNo.value+"&ScanFlag="+ScanFlag+"&BankFlag=1"+"&InsuredChkFlag="+document.all('InsuredChkButton').disabled+"&AppntChkFlag="+document.all('AppntChkButton').disabled+"&specScanFlag="+specScanFlag;
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
    try{mSwitch.deleteVar('IDPeriodOfValidity');}catch(ex){};
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
    try{mSwitch.addVar('IDPeriodOfValidity','',fm.IDPeriodOfValidity.value);}catch(ex){};

}

/*********************************************************************
 *  ��ӱ�������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function addRecord()
{ 
	if(verifyWorkFlow(tMissionID,tSubMissionID,ActivityID)==false)
	{
		return false;
	}/////////////////У�鹫�����Ƿ��Ѿ���ת���
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

	  var sqlstr="select SequenceNo from LCInsured where PrtNo='"+tPrtNo+"'";

    arrResult=easyExecSql(sqlstr,1,0);

    if(arrResult!=null){
      for(var sequencenocout=0; sequencenocout<arrResult.length;sequencenocout++ )
      {
        if(fm.SequenceNo.value==arrResult[sequencenocout][0]){
	        alert("�Ѿ����ڸÿͻ��ڲ���");
	        //fm.SequenceNo.focus();
	        return false;
	      }
	    }
	  }
	}

  //2005.03.18 chenhq �Դ˽����޸�
  if(LoadFlag==3){

	  var tPrtNo=document.all("PrtNo").value;

	  var sqlstr="select SequenceNo from LCInsured where PrtNo='"+tPrtNo+"'";

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

     	var sql33="";
        var sqlid33="BankContInput33";
        var mySql33=new SqlClass();
        mySql33.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
        mySql33.setSqlId(sqlid33);//ָ��ʹ�õ�Sql��id
        mySql33.addSubPara(tContNo);
        sql33=mySql33.getString();
        var sqlstr=sql33;

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
  if(LoadFlag==6){

	  var tPrtNo=document.all("PrtNo").value;

        var sql34="";
        var sqlid34="BankContInput34";
        var mySql34=new SqlClass();
        mySql34.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
        mySql34.setSqlId(sqlid34);//ָ��ʹ�õ�Sql��id
        mySql34.addSubPara(tPrtNo);
        sql34=mySql34.getString();
        var sqlstr=sql34;

	 // var sqlstr="select SequenceNo from LCInsured where PrtNo='"+tPrtNo+"'";

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
	getdetailwork2();
  //end hanlin
  document.all('ContType').value=ContType;
  document.all( 'BQFlag' ).value = BQFlag;
  document.all('fmAction').value="INSERT||CONTINSURED";
  document.getElementById("fm").submit();
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
	lockScreen('lkscreen');  
	//���loadflag=3������޸ĸڣ��޸ı�������Ҫ��Ϣ��Ͷ�����˹�ϵΪ00-���������޸�Ͷ������Ϣ
	var tName = fm.Name.value;
	var tIDType = fm.IDType.value;
	var tIDNo = fm.IDNo.value;
	var tSex = fm.Sex.value;
	var tBirthday = fm.Birthday.value;
	var tContNo = fm.PrtNo.value;
	if(LoadFlag=="3"){
		if(fm.RelationToInsured.value=="00"){
			
			var sql35="";
            var sqlid35="BankContInput35";
            var mySql35=new SqlClass();
	        mySql35.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
	        mySql35.setSqlId(sqlid35);//ָ��ʹ�õ�Sql��id
	        mySql35.addSubPara(tContNo);
			mySql35.addSubPara(tName);
			mySql35.addSubPara(tBirthday);
			
			mySql35.addSubPara(tSex);
			mySql35.addSubPara(tIDType);
			mySql35.addSubPara(tIDNo);
	        sql35=mySql35.getString();
	        var tCheckSql=sql35;
			
//			var tCheckSql = "select * from lcappnt where prtno='"+tContNo+"' and appntname='"+tName+"'"
//						+ " and appntbirthday='"+tBirthday+"' and appntsex='"+tSex+"'"
//						+ " and idtype='"+tIDType+"' and idno='"+tIDNo+"'";
			var tArr = easyExecSql(tCheckSql,1,0);
			if(!tArr){
				alert("Ͷ�����˹�ϵΪ���ˣ����޸��˱�������Ҫ��Ϣ����Ͷ�����˿��ܲ���ͬһ�ˣ����޸�Ͷ�����˹�ϵ�����޸�Ͷ������Ϣ��");
				unlockScreen('lkscreen');
				return false;
			}
		}
	}
	if(verifyWorkFlow(tMissionID,tSubMissionID,ActivityID)==false)
	{
		unlockScreen('lkscreen');
		return false;
	}/////////////////У�鹫�����Ƿ��Ѿ���ת���
	if (InsuredGrid.mulLineCount==0)
	{
		alert("�ñ������˻�û�б��棬�޷������޸ģ�");
		unlockScreen('lkscreen');
		return false;
	}
	var tselno=InsuredGrid.getSelNo();
	if(tselno==0)
	{
		alert("��ѡ����Ҫ�޸ı����˼�¼");
		unlockScreen('lkscreen');
		return false;
	}
	/*start.ע�͡�MSҪ������ɾ�����ּ����޸ı�����---yeshu,20071224
	var tOldInsuredNo=InsuredGrid.getRowColData(tselno-1,1);
	var SSQL=" select t.insuredno,t.name,t.sex,t.birthday,t.idtype,t.idno,t.occupationcode"
		+" from lcinsured t where t.contno='"+fm.ContNo.value+"' and t.insuredno='"+tOldInsuredNo+"'";
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
	}	end.---yeshu,20071224*/
	if(fm.SamePersonFlag.checked==true&&fm.RelationToAppnt.value!="00")
	{
		alert("��Ͷ���˹�ϵֻ��ѡ���ˣ�");
		fm.RelationToAppnt.value="00";
		fm.RelationToAppntName.value="����";
		unlockScreen('lkscreen');
		return ;
	}
    if (document.all('PolTypeFlag').value==0)
    {
        if( verifyInputNB('2') == false ) {
        	unlockScreen('lkscreen');
        	return false;
        }
    }
    //У�鱻�����������������˹�ϵ
    if(!checkself()) { 
    	unlockScreen('lkscreen');
    	return false;
    }
   /* if (fm.InsuredNo.value==''&&fm.InsuredAddressNo.value!='')
    {
        alert("�������˿ͻ���Ϊ�գ������е�ַ����");
        return false;
    }*/
    if(ImpartGrid.checkValue2(ImpartGrid.name,ImpartGrid)== false)
    {
    	unlockScreen('lkscreen');
    	return false;
    }
	ImpartGrid.delBlankLine();
	getdetailwork2();
	document.all('ContType').value=ContType;
	document.all('fmAction').value="UPDATE||CONTINSURED";
	fm.action="BankContInsuredSave.jsp";
	document.getElementById("fm").submit();
}
/*********************************************************************
 *  ɾ����ѡ�еı�������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
 function deleteRecord()
{
	if(verifyWorkFlow(tMissionID,tSubMissionID,ActivityID)==false)
	{
		return false;
	}/////////////////У�鹫�����Ƿ��Ѿ���ת���
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
	
	        var sql36="";
            var sqlid36="BankContInput36";
            var mySql36=new SqlClass();
	        mySql36.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
	        mySql36.setSqlId(sqlid36);//ָ��ʹ�õ�Sql��id
	        mySql36.addSubPara(fm.ContNo.value);
	        sql36=mySql36.getString();
	        var sqlstr=sql36;
	
    //var sqlstr="select polno from  lcpol where contNo='"+fm.ContNo.value+"'";
    arrResult=easyExecSql(sqlstr,1,0);
    
    if(arrResult!=null)
    {
    	alert("����ɾ��������������Ϣ��");
        return false;    
    }
    document.all('ContType').value=ContType;
    document.all('fmAction').value="DELETE||CONTINSURED";
    fm.action="BankContInsuredSave.jsp";
    document.getElementById("fm").submit();
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
		
		   var sql37="";
            var sqlid37="BankContInput37";
            var mySql37=new SqlClass();
	        mySql37.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
	        mySql37.setSqlId(sqlid37);//ָ��ʹ�õ�Sql��id
	        mySql37.addSubPara(ContNo);
	        sql37=mySql37.getString();
	        var strSQL=sql37;
		
        //var strSQL ="select InsuredNo,Name,Sex,Birthday,RelationToMainInsured,SequenceNo from LCInsured where ContNo='"+ContNo+"'";

        //alert("strSQL=="+strSQL);
		InsuredGrid.clearData();//add by weikai ������� ����  displayMultiline(arrDataSet, turnPage.pageDisplayGrid) �����
        turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
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
//Yang Yalin Changed this,2005-10-8
         
	//			initInsuredServer();
				
        //showInsuredCodeName();  

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
	
	        var sql38="";
            var sqlid38="BankContInput38";
            var mySql38=new SqlClass();
	        mySql38.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
	        mySql38.setSqlId(sqlid38);//ָ��ʹ�õ�Sql��id
	        mySql38.addSubPara(ContNo);
	        sql38=mySql38.getString();
	        var strSQL=sql38;
			
	 arrResult=easyExecSql(strSQL,1,0);
   // arrResult=easyExecSql("select * from LCInsured where ContNo='"+tContNo+"'",1,0);
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
        
		   var sql39="";
            var sqlid39="BankContInput39";
            var mySql39=new SqlClass();
	        mySql39.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
	        mySql39.setSqlId(sqlid39);//ָ��ʹ�õ�Sql��id
	        mySql39.addSubPara(tCustomerNo);
	        sql39=mySql39.getString();
	      
		 arrResult=easyExecSql(sql39,1,0);
       // arrResult=easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo='"+tCustomerNo+"'",1,0);
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
 function getInsuredDetail1(ContNo,InsuredNo){
 	    //add by wangyc
    parent.fraSubmit.window.location = "./ConfInfoQuery1.jsp?ContNo="
																			+ ContNo+"&InsuredNo="+InsuredNo;
 	}
function getInsuredDetail(parm1,parm2)
{
    //alert("---parm1=="+parm1+"---parm2=="+parm2);

    var InsuredNo=document.all(parm1).all('InsuredGrid1').value;

    var ContNo = fm.ContNo.value;

    //��������ϸ��Ϣ
	
	        var sql40="";
            var sqlid40="BankContInput40";
            var mySql40=new SqlClass();
	        mySql40.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
	        mySql40.setSqlId(sqlid40);//ָ��ʹ�õ�Sql��id
	        mySql40.addSubPara(InsuredNo);
	        sql40=mySql40.getString();
	        var strSQL=sql40;
    //var strSQL ="select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo='"+InsuredNo+"'";

    arrResult=easyExecSql(strSQL);

    if(arrResult!=null)
    {
        //displayAppnt();
        displayInsuredInfo();
    }

            var sql41="";
            var sqlid41="BankContInput41";
            var mySql41=new SqlClass();
	        mySql41.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
	        mySql41.setSqlId(sqlid41);//ָ��ʹ�õ�Sql��id
	        mySql41.addSubPara(ContNo);
			mySql41.addSubPara(InsuredNo);
	        sql41=mySql41.getString();
	        strSQL=sql41;

    //strSQL ="select * from LCInsured where ContNo = '"+ContNo+"' and InsuredNo='"+InsuredNo+"'";

    arrResult=easyExecSql(strSQL);

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
	try{document.all('Phone').value= arrResult[0][12]; }catch(ex){};
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
    try{document.all('IDPeriodOfValidity').value=arrResult[0][64];}catch(ex){};
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
��try{document.all('Phone').value= document.all("AppntPhone").value;}catch(ex){};
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
  try{document.all('IDPeriodOfValidity').value= document.all("AppIDPeriodOfValidity").value;}catch(ex){};
  showOneCodeName('incomeway','IncomeWay','IncomeWayName');
  //alert(document.all('Mobile').value);
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

    	    var strSQL0="";//"select ImpartParam from LCCustomerImpartparams where CustomerNoType='1' and CustomerNo='"+InsuredNo+"' and ContNo='"+ContNo+"' and impartver='01' and impartcode='001' and ImpartParamNo = '1'";

	       var strSQL1="";//"select ImpartParam from LCCustomerImpartparams where CustomerNoType='1' and CustomerNo='"+InsuredNo+"' and ContNo='"+ContNo+"' and impartver='01' and impartcode='001' and ImpartParamNo = '2'";

            var sql42="";
            var sqlid42="BankContInput42";
            var mySql42=new SqlClass();
	        mySql42.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
	        mySql42.setSqlId(sqlid42);//ָ��ʹ�õ�Sql��id
	        mySql42.addSubPara(InsuredNo);
			mySql42.addSubPara(ContNo);
	        sql42=mySql42.getString();
	        strSQL0=sql42;
			
			var sql43="";
            var sqlid43="BankContInput43";
            var mySql43=new SqlClass();
	        mySql43.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
	        mySql43.setSqlId(sqlid43);//ָ��ʹ�õ�Sql��id
	        mySql43.addSubPara(InsuredNo);
			mySql43.addSubPara(ContNo);
	        sql43=mySql43.getString();
	        strSQL1=sql43;


	    arrResult = easyExecSql(strSQL0,1,0);
	    arrResult1 = easyExecSql(strSQL1,1,0);



	    try{document.all('Income').value= arrResult[0][0];}catch(ex){};
	    try{document.all('IncomeWay').value= arrResult1[0][0];}catch(ex){};

        //var strSQL ="select ImpartVer,ImpartCode,ImpartContent,ImpartParamModle from LCCustomerImpart where CustomerNo='"+InsuredNo+"' and ProposalContNo='"+ContNo+"' and CustomerNoType='1' ";
        //modify by jiaqiangli 2009-04-30
		
		    var sql44="";
            var sqlid44="BankContInput44";
            var mySql44=new SqlClass();
	        mySql44.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
	        mySql44.setSqlId(sqlid44);//ָ��ʹ�õ�Sql��id
	        mySql44.addSubPara(InsuredNo);
			mySql44.addSubPara(ContNo);
	        sql44=mySql44.getString();
	        var strSQL=sql44;
		
        //var strSQL ="select ImpartVer,ImpartCode,ImpartContent,ImpartParamModle from LCCustomerImpart where CustomerNo='"+InsuredNo+"' and ContNo='"+ContNo+"' and CustomerNoType='1' ";
        turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
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
		   var sql45="";
            var sqlid45="BankContInput45";
            var mySql45=new SqlClass();
	        mySql45.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
	        mySql45.setSqlId(sqlid45);//ָ��ʹ�õ�Sql��id
	        mySql45.addSubPara(InsuredNo);
			mySql45.addSubPara(ContNo);
	        sql45=mySql45.getString();
	        var strSQL=sql45;
			
        //var strSQL ="select ImpartVer,ImpartCode,ImpartDetailContent from LCCustomerImpartDetail where CustomerNo='"+InsuredNo+"' and ContNo='"+ContNo+"' and CustomerNoType='1'";
        turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
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
		
		    var sql46="";
            var sqlid46="BankContInput46";
            var mySql46=new SqlClass();
	        mySql46.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
	        mySql46.setSqlId(sqlid46);//ָ��ʹ�õ�Sql��id
	        mySql46.addSubPara(InsuredNo);
			mySql46.addSubPara(ContNo);
	        sql46=mySql46.getString();
	        var strSQL=sql46;
		
        //var strSQL ="select polno,riskcode,(select riskname from lmriskapp where riskcode=lcpol.riskcode),prem,amnt from lcpol "
        //	+" where insuredno='"+InsuredNo+"' and contno='"+ContNo+"'";
        turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
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
    if (fm.RelationToInsured.value!="00" && fm.SamePersonFlag.checked==true) 
    {
      document.all('DivLCInsured').style.display = "";
      fm.SamePersonFlag.checked = false;
      alert("Ͷ�����뱻���˹�ϵ���Ǳ��ˣ����ܽ��иò�����");
    }
    //��Ӧ��ͬһ�ˣ��ִ򹳵����
    else if (fm.SamePersonFlag.checked == true)
    {
      document.all('DivLCInsured').style.display = "none";
      divLCInsuredPerson.style.display = "none";
      divSalary.style.display = "none";

      displayissameperson();
      //alert("InsuredName: "+document.all('Name').value);
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
      try{document.all('Phone').value= "";}catch(ex){};
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
      try{document.all('IDPeriodOfValidity').value=  "";}catch(ex){};

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
       
	       var sql47="";
            var sqlid47="BankContInput47";
            var mySql47=new SqlClass();
	        mySql47.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
	        mySql47.setSqlId(sqlid47);//ָ��ʹ�õ�Sql��id
	        mySql47.addSubPara(fm.InsuredNo.value);
	        sql47=mySql47.getString();
	        var strSQL=sql47;
	   
	   arrResult = easyExecSqlstrSQL(strSQL, 1, 0);
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
          getdetailaddress2();
          showCodeName();
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
//	  alert("asdfasfsf");
	  //alert(arrResult[0][1]);
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
    try{document.all('IDPeriodOfValidity').value= arrResult[0][47];}catch(ex){};



    //��ַ��ʾ���ֵı䶯
    //try{document.all('InsuredAddressNo').value= "";}catch(ex){};
    try{document.all('PostalAddress').value=  "";}catch(ex){};
    try{document.all('ZipCode').value=  "";}catch(ex){};
    try{document.all('Phone').value=  "";}catch(ex){};
    try{document.all('Mobile').value=  "";}catch(ex){};
    try{document.all('EMail').value=  "";}catch(ex){};
    //try{document.all('GrpName').value= arrResult[0][46];}catch(ex){};
    try{document.all('Phone').value=  "";}catch(ex){};
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
             
			var sql48="";
            var sqlid48="BankContInput48";
            var mySql48=new SqlClass();
	        mySql48.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
	        mySql48.setSqlId(sqlid48);//ָ��ʹ�õ�Sql��id
	        mySql48.addSubPara(arrQueryResult[0][0] );
	        sql48=mySql48.getString();
	        var strSQL=sql48;
			 
			 arrResult = easyExecSql(strSQL, 1, 0);
            //arrResult = easyExecSql("select ProposalContNo,PrtNo,ManageCom,SaleChnl,AgentCode,AgentGroup,AgentCode1,AgentCom,AgentType,Remark from LCCont where LCCont = '" + arrQueryResult[0][0] + "'", 1, 0);

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
        	
			var sql49="";
            var sqlid49="BankContInput49";
            var mySql49=new SqlClass();
	        mySql49.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
	        mySql49.setSqlId(sqlid49);//ָ��ʹ�õ�Sql��id
	        mySql49.addSubPara(arrQueryResult[0][0]  );
	        sql49=mySql49.getString();
	        var strSQL=sql49;
			arrResult = easyExecSql(strSQL, 1, 0);
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
 *  ��ѯְҵ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function getdetailwork2()
{
	       var sql50="";
            var sqlid50="BankContInput50";
            var mySql50=new SqlClass();
	        mySql50.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
	        mySql50.setSqlId(sqlid50);//ָ��ʹ�õ�Sql��id
	        mySql50.addSubPara( fm.OccupationCode.value  );
	        sql50=mySql50.getString();
	        var strSQL=sql50;
	
   // var strSql = "select OccupationType from LDOccupation where OccupationCode='" + fm.OccupationCode.value+"'";
    var arrResult = easyExecSql(strSQL);
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
	//alert("LoadFlag=="+LoadFlag);


    if (wFlag ==1 ) //¼�����ȷ��
    {
		    var sql51="";
            var sqlid51="BankContInput51";
            var mySql51=new SqlClass();
	        mySql51.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
	        mySql51.setSqlId(sqlid51);//ָ��ʹ�õ�Sql��id
	        mySql51.addSubPara( fm.ContNo.value  );
	        sql51=mySql51.getString();
	        var tStr=sql51;
			    //alert('1');
        //var tStr= "	select * from lwmission where 1=1 and lwmission.activityid in ('0000001001','0000001002','0000001220','0000001230') and lwmission.missionprop1 = '"+fm.ContNo.value+"'";
        turnPage.strQueryResult = easyQueryVer3(tStr, 1, 0, 1);
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
 *  ��ѯ����������ϸ��Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function getdetailaddress2()
{
    //alert("fm.InsuredAddressNo.value"+fm.InsuredAddressNo.value);
	
	        var sql52="";
            var sqlid52="BankContInput52";
            var mySql52=new SqlClass();
	        mySql52.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
	        mySql52.setSqlId(sqlid52);//ָ��ʹ�õ�Sql��id
	        mySql52.addSubPara( fm.InsuredAddressNo.value  );
			mySql52.addSubPara( fm.InsuredNo.value  );
	        sql52=mySql52.getString();
	        var strSQL=sql52;
	
    //var strSQL="select b.AddressNo,b.PostalAddress,b.ZipCode,b.Phone,b.Mobile,b.EMail,b.CompanyPhone,b.CompanyAddress,b.CompanyZipCode,b.fax,b.HomePhone,b.grpName,b.province,b.city,b.County from LCAddress b where b.AddressNo='"+fm.InsuredAddressNo.value+"' and b.CustomerNo='"+fm.InsuredNo.value+"'";
    arrResult=easyExecSql(strSQL);
    displayDetailAddress2(arrResult);
}

function displayDetailAddress2(arrResult)
{
      //alert("fm.InsuredAddressNo.value"+fm.InsuredAddressNo.value);
    try{document.all('InsuredAddressNo').value= arrResult[0][0];}catch(ex){};
    try{document.all('InsuredAddressNoName').value= arrResult[0][1];}catch(ex){};
    try{document.all('PostalAddress').value= arrResult[0][1];}catch(ex){};
    try{document.all('ZIPCODE').value= arrResult[0][2];}catch(ex){};
    try{document.all('Phone').value= arrResult[0][3];}catch(ex){};
    try{document.all('Mobile').value= arrResult[0][4];}catch(ex){};
    try{document.all('EMail').value= arrResult[0][5];}catch(ex){};
    //try{document.all('GrpName').value= arrResult[0][6];}catch(ex){};
    try
      {
      	document.all('Phone').value= arrResult[0][6];
      	if(document.all('Phone').value==null||document.all('Phone').value=="")
      	{
      		document.all('Phone').value=arrResult[0][3];
      	}
    	}catch(ex){};
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
//   showOneCodeName('Province','InsuredProvince','InsuredProvinceName');
//   showOneCodeName('City','InsuredCity','InsuredCityName');
//   showOneCodeName('District','InsuredDistrict','InsuredDistrictName');
	getAddressName('province','InsuredProvince','InsuredProvinceName');
	getAddressName('city','InsuredCity','InsuredCityName');
	getAddressName('district','InsuredDistrict','InsuredDistrictName');
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
	
	        var sql53="";
            var sqlid53="BankContInput53";
            var mySql53=new SqlClass();
	        mySql53.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
	        mySql53.setSqlId(sqlid53);//ָ��ʹ�õ�Sql��id
	        mySql53.addSubPara( tProposalGrpContNo );
	        sql53=mySql53.getString();
	        strsql=sql53;
	
    //strsql = "select ContPlanCode,ContPlanName from LCContPlan where ContPlanCode!='00' and ProposalGrpContNo='"+tProposalGrpContNo+"'";
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
	
		     var sql54="";
            var sqlid54="BankContInput54";
            var mySql54=new SqlClass();
	        mySql54.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
	        mySql54.setSqlId(sqlid54);//ָ��ʹ�õ�Sql��id
	        mySql54.addSubPara( tProposalGrpContNo );
	        sql54=mySql54.getString();
	        strsql=sql54;
	
	//strsql = "select ExecuteCom,Name from LCGeneral a,LDCom b where a.GrpContNo='"+tProposalGrpContNo+"' and a.ExecuteCom=b.ComCode";
	//alert("strsql :" + strsql);
	turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);
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
        try{document.all('AppntPhone').value= ""; }catch(ex){};
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
        try{document.all('AppIDPeriodOfValidity').value= ""; }catch(ex){};
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
  try{document.all('IDPeriodOfValidity').value= ""; }catch(ex){};
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
	try{document.all('Phone').value= "";  }catch(ex){};
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
	{
		
		   var sql55="";
            var sqlid55="BankContInput55";
            var mySql55=new SqlClass();
	        mySql55.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
	        mySql55.setSqlId(sqlid55);//ָ��ʹ�õ�Sql��id
	        mySql55.addSubPara( fm.ProposalGrpContNo.value );
	        sql55=mySql55.getString();
	         var tStr=sql55;
		
//	    var tStr= "	select * from lwmission where 1=1 "
//	    					+" and lwmission.processid = '0000000004'"
//	    					+" and lwmission.activityid = '0000002001'"
//	    					+" and lwmission.missionprop1 = '"+fm.ProposalGrpContNo.value+"'";
	    turnPage.strQueryResult = easyQueryVer3(tStr, 1, 0, 1);
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
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.action = "./GrpInputConfirm.jsp";
    document.getElementById("fm").submit(); //�ύ
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
		
		    var sql56="";
            var sqlid56="BankContInput56";
            var mySql56=new SqlClass();
	        mySql56.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
	        mySql56.setSqlId(sqlid56);//ָ��ʹ�õ�Sql��id
	        mySql56.addSubPara( fm.InsuredNo.value );
	        sql56=mySql56.getString();
	        strsql=sql56;
		
    //strsql = "select AddressNo,PostalAddress from LCAddress where CustomerNo ='"+fm.InsuredNo.value+"'";
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
    //return tCodeData;
    document.all("InsuredAddressNo").CodeData=tCodeData;
}

function getImpartCode(parm1,parm2){
  //alert("hehe:"+document.all(parm1).all('ImpartGrid1').value);
  var impartVer=document.all(parm1).all('ImpartGrid1').value;
  window.open("../app/ImpartCodeSel.jsp?ImpartVer="+impartVer,"",sFeatures);
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
 	{
		
		    var sql57="";
            var sqlid57="BankContInput57";
            var mySql57=new SqlClass();
	        mySql57.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
	        mySql57.setSqlId(sqlid57);//ָ��ʹ�õ�Sql��id
	        mySql57.addSubPara( fm.Name.value );
			mySql57.addSubPara( fm.IDType.value );
			mySql57.addSubPara( fm.IDNo.value );
	        sql57=mySql57.getString();
	        strSQL=sql57;
		
//	    strSQL = "select a.CustomerNo, a.Name, a.Sex, a.Birthday, a.IDType, a.IDNo from LDPerson a where 1=1 "
//                +"  and Name='"+fm.Name.value
//                +"' and IDType='"+fm.IDType.value
//                +"' and IDNo='"+fm.IDNo.value
//		+"' order by a.CustomerNo";
             turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
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
	if(verifyWorkFlow(tMissionID,tSubMissionID,ActivityID)==false)
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
	var tRow = PolGrid.getSelNo() - 1;
	var tpolno=PolGrid.getRowColData(tRow,1)
	document.all('fmAction').value="DELETE||INSUREDRISK";
	fm.action="./DelIsuredRisk.jsp?polno="+tpolno;
	document.getElementById("fm").submit(); //�ύ

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
	
	        var sql58="";
            var sqlid58="BankContInput58";
            var mySql58=new SqlClass();
	        mySql58.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
	        mySql58.setSqlId(sqlid58);//ָ��ʹ�õ�Sql��id
	        mySql58.addSubPara( tInsuredName );
			mySql58.addSubPara( tInsuredSex );
			mySql58.addSubPara( tBirthday );
			 mySql58.addSubPara( tInsuredNo );
			mySql58.addSubPara( fm.IDNo.value );
			mySql58.addSubPara( tInsuredNo );
	        sql58=mySql58.getString();
	        var sqlstr=sql58;
	
	
//  var sqlstr= "select * from ldperson where Name='"+tInsuredName
//            + "' and Sex='"+tInsuredSex+"' and Birthday='"+tBirthday
//            + "' and CustomerNo<>'"+tInsuredNo+"'"
//            + " union select * from ldperson where 1=1 "
//
//            + " and IDNo = '"+fm.IDNo.value
//            +"' and IDNo is not null and CustomerNo<>'"+tInsuredNo+"'" ;
        arrResult = easyExecSql(sqlstr,1,0);

        if(arrResult==null)
        {
	   alert("��û����ñ����˱������ƵĿͻ�,����У��");
	   return false;
        }

    window.open("../uw/AppntChkMain.jsp?ProposalNo1="+fm.ContNo.value+"&Flag=I&LoadFlag="+LoadFlag+"&InsuredNo="+tInsuredNo,"window1",sFeatures);
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
		
		    var sql59="";
            var sqlid59="BankContInput59";
            var mySql59=new SqlClass();
	        mySql59.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
	        mySql59.setSqlId(sqlid59);//ָ��ʹ�õ�Sql��id
	        mySql59.addSubPara( insuredno );
			mySql59.addSubPara( fm.ContNo.value );
			mySql59.addSubPara( insuredno );
	        sql59=mySql59.getString();
	        var strhomea=sql59;
		
       //var strhomea="select HomeAddress,HomeZipCode,HomePhone from lcaddress where customerno='"+insuredno+"' and addressno=(select addressno from lcinsured where contno='"+fm.ContNo.value+"' and insuredno='"+insuredno+"')";
       arrResult=easyExecSql(strhomea,1,0);
       try{document.all('HomeAddress').value= arrResult[0][0];}catch(ex){};

       try{document.all('HomeZipCode').value= arrResult[0][1];}catch(ex){};

       try{document.all('HomePhone').value= arrResult[0][2];}catch(ex){};

       fm.InsuredAddressNo.value = "";
       fm.InsuredNo.value = "";
    }
}
function getdetail2()
{
	
	        var sql60="";
            var sqlid60="BankContInput60";
            var mySql60=new SqlClass();
	        mySql60.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
	        mySql60.setSqlId(sqlid60);//ָ��ʹ�õ�Sql��id
	        mySql60.addSubPara( fm.BankAccNo.value );
	        sql60=mySql60.getString();
	        var strSql=sql60;
	
//var strSql = "select BankCode,AccName from LCAccount where BankAccNo='" + fm.BankAccNo.value+"'";
arrResult = easyExecSql(strSql);
if (arrResult != null) {
      fm.BankCode.value = arrResult[0][0];
      fm.AccName.value = arrResult[0][1];
    }
}


// �ڳ�ʼ��bodyʱ�Զ�Ч��Ͷ������Ϣ
function InsuredChkNew(){
	//alert("aaa");
	//var tRow = InsuredGrid.getSelNo() - 1;
	var tInsuredNo=fm.InsuredNo.value;
	var tInsuredName=document.all("Name").value;
	var tInsuredSex=fm.Sex.value;
	var tBirthday=fm.Birthday.value;
  
            var sql61="";
            var sqlid61="BankContInput61";
            var mySql61=new SqlClass();
	        mySql61.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
	        mySql61.setSqlId(sqlid61);//ָ��ʹ�õ�Sql��id
	        
			mySql61.addSubPara(tInsuredName);
		    mySql61.addSubPara(tInsuredSex);
			mySql61.addSubPara(tBirthday);
		    mySql61.addSubPara( tInsuredNo);
			mySql61.addSubPara( fm.IDNo.value);
		    mySql61.addSubPara(tInsuredNo);
			  
	        sql61=mySql61.getString();
	        var sqlstr=sql61;
  
//  var sqlstr= "select * from ldperson where Name='"+tInsuredName
//            + "' and Sex='"+tInsuredSex+"' and Birthday='"+tBirthday
//            + "' and CustomerNo<>'"+tInsuredNo+"'"
//            + " union select * from ldperson where 1=1 "
//            + " and IDNo = '"+fm.IDNo.value
//            + "' and IDNo is not null and CustomerNo<>'"+tInsuredNo+"'" ;
  
  //alert(sqlstr);    
  
  arrResult = easyExecSql(sqlstr,1,0);
  
  if(arrResult==null)
  {
  	//disabled"Ͷ����Ч��"��ť
		fm.InsuredChkButton.disabled = true;
		fm.InsuredChkButton2.disabled = true;
		
//				  return false;
  }
  else
  {
		fm.InsuredChkButton.disabled = "";
		fm.InsuredChkButton2.disabled = "";
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
  //alert(document.all('AppntBankCode').value);
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
  
            var sql62="";
            var sqlid62="BankContInput62";
            var mySql62=new SqlClass();
	        mySql62.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
	        mySql62.setSqlId(sqlid62);//ָ��ʹ�õ�Sql��id
			mySql62.addSubPara(ContNo);
	        var sqlstr=mySql62.getString();
  
  //var sqlstr="select forceuwflag from lccont where contno='"+ContNo+"'" ;
  arrResult = easyExecSql(sqlstr,1,0);
  if(arrResult==null){
  	alert("�����ڸ�Ͷ������");
  }
  else{
    window.open("../uw/ForceUWMain.jsp?ContNo="+ContNo,"window1",sFeatures);
  }

}

//��ѯ�Ƿ��Ѿ���ӹ�Ͷ����
function checkAppnt(){
  var ContNo=document.all("ContNo").value;
  
            var sql63="";
            var sqlid63="BankContInput63";
            var mySql63=new SqlClass();
	        mySql63.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
	        mySql63.setSqlId(sqlid63);//ָ��ʹ�õ�Sql��id
			mySql63.addSubPara(ContNo);
	        var sqlstr=mySql63.getString();
  
  //var sqlstr="select forceuwflag from lccont where contno='"+ContNo+"'" ;
  arrResult = easyExecSql(sqlstr,1,0);
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
  var PrtNo = document.all('PrtNo2').value;
 // alert("PrtNo="+ document.all.PrtNo2.value);

	//var NoType = document.all.NoType.value;
	//alert("NoType="+NoType);
	if(PrtNo == null || PrtNo == "")
	{
		alert("Ͷ������Ϊ�գ�");
		return;
	} 
	
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ PrtNo + "&NoType=1";
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");

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
	
	//tongmeng 2008-01-03 add
	//����ְҵ����
	
}
/*********************************************************************
 *  ��ͬ����򺺻�
 *  ����  ��  ��
 *  ����ֵ��  ��
 **********************************************************************/
 function showContCodeName()
{
	quickGetName('comcode',fm.ManageCom,fm.ManageComName);
	quickGetName('SellType',fm.SellType,fm.sellTypeName);
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
	quickGetName('Relation',fm.RelationToInsured,fm.RelationToInsuredName);
	quickGetName('IDType',fm.AppntIDType,fm.AppntIDTypeName);
	quickGetName('Sex',fm.AppntSex,fm.AppntSexName);
	quickGetName('Marriage',fm.AppntMarriage,fm.AppntMarriageName);
	quickGetName('NativePlace',fm.AppntNativePlace,fm.AppntNativePlaceName);
	quickGetName('OccupationCode',fm.AppntOccupationCode,fm.AppntOccupationCodeName);
	quickGetName('occupationtype',fm.AppntOccupationType,fm.AppntOccupationTypeName);
	quickGetName('LicenseType',fm.AppntLicenseType,fm.AppntLicenseTypeName);
//  quickGetName('GetAddressNo',fm.AppntAddressNo,fm.AddressNoName);
//	quickGetName('province',fm.AppntProvince,fm.AppntProvinceName);
//	quickGetName('city',fm.AppntCity,fm.AppntCityName);
//	quickGetName('district',fm.AppntDistrict,fm.AppntDistrictName);
	getAddressName('province','AppntProvince','AppntProvinceName');
	getAddressName('city','AppntCity','AppntCityName');
	getAddressName('district','AppntDistrict','AppntDistrictName');
	quickGetName('paymode',fm.PayMode,fm.FirstPayModeName);
	quickGetName('continuepaymode',fm.SecPayMode,fm.PayModeName);
	quickGetName('bank',fm.AppntBankCode,fm.FirstBankCodeName);
	quickGetName('bank',fm.SecAppntBankCode,fm.AppntBankCodeName);
	quickGetName('incomeway',fm.IncomeWay0,fm.IncomeWayName0);
	
	
}

/*********************************************************************
 *  �����˴���򺺻�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showInsuredCodeName()
{
/*
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
*/
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
	//quickGetName('GetAddressNo',fm.InsuredAddressNo,fm.InsuredAddressNoName);
//	quickGetName('Province',fm.InsuredProvince,fm.InsuredProvinceName);
//	quickGetName('City',fm.InsuredCity,fm.InsuredCityName);
//	quickGetName('District',fm.InsuredDistrict,fm.InsuredDistrictName);
	getAddressName('province','InsuredProvince','InsuredProvinceName');
	getAddressName('city','InsuredCity','InsuredCityName');
	getAddressName('district','InsuredDistrict','InsuredDistrictName');
	quickGetName('incomeway',fm.IncomeWay,fm.IncomeWayName);
}

//�����Ӱ���ѯ
function QuestPicQuery()
{
	var ContNo = fm.ContNo.value;
	if (ContNo == "") return;
	window.open("../uw/ImageQueryMain.jsp?ContNo="+ContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);
	
}
//��ѯҵ��Ա��֪��Ϣ
function getImpartInitInfo()
{
	var tContNo = fm.ContNo.value;

	var strSQL ="select impartver,impartcode,ImpartContent from LDImpart where impartver='05' ";

	turnPage.queryModal(strSQL, AgentImpartGrid);
	
	        var sql64="";
            var sqlid64="BankContInput64";
            var mySql64=new SqlClass();
	        mySql64.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
	        mySql64.setSqlId(sqlid64);//ָ��ʹ�õ�Sql��id
			mySql64.addSubPara(tContNo);
	        var aSQL=mySql64.getString();
	
//        var aSQL="SELECT distinct a.impartver,a.impartcode,a.ImpartContent,b.impartparammodle "
//		+"FROM ldimpart a left join lccustomerimpart b on a.impartver=b.impartver and a.ImpartCode=b.ImpartCode and b.contno='"+tContNo+"' "
//		+"WHERE a.impartver='05'";

	turnPage.queryModal(aSQL,AgentImpartGrid);
	return true;
}

function getLostInfo()
{
	//tongmeng 2007-09-10  add
	//��ʾ�����������
	if(document.all('AgentCom').value!=null&&document.all('AgentCom').value!='')
	{
		        var sql65="";
	            var sqlid65="BankContInput65";
	            var mySql65=new SqlClass();
		        mySql65.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
		        mySql65.setSqlId(sqlid65);//ָ��ʹ�õ�Sql��id
				mySql65.addSubPara(document.all('AgentCom').value);
		        var strSQL=mySql65.getString();
		
		//var strSQL = "select name from lacom where agentcom='"+ document.all('AgentCom').value+"'";
		arrResult = easyExecSql(strSQL,1,0);
		if(arrResult!==null)
		{
			document.all('InputAgentComName').value=arrResult[0][0]; 
		}
	}
	//var strSQL = "select bankcode from lacom where agentcom='"+ document.all('AgentCom').value+"'";
	//alert("1:"+strSQL);
	//arrResult = easyExecSql(strSQL,1,0);
	//if(arrResult!==null)
	//  { 
	  	//alert(arrResult[0][0]);
      //tongmeng 2007-09-10 modify
      //���ػ�����ȡ�����б���
      //document.all('BankCode1').value=arrResult[0][0]; 
	//	}
	if(document.all('AgentCode').value!=null&&document.all('AgentCode').value!='')
	{	
				 var sql66="";
	            var sqlid66="BankContInput66";
	            var mySql66=new SqlClass();
		        mySql66.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
		        mySql66.setSqlId(sqlid66);//ָ��ʹ�õ�Sql��id
				mySql66.addSubPara(document.all('AgentCode').value);
		       strSQL=mySql66.getString();
			
		//strSQL = "select name from laagent where agentcode='" + document.all('AgentCode').value +"'";
		//alert("2:"+strSQL);
		arrResult = easyExecSql(strSQL,1,0);
		
		if(arrResult!==null)
		  {
	      document.all('AgentName').value=arrResult[0][0];
			}
	}
	
	if(document.all('AgentGroup').value!=null&&document.all('AgentGroup').value!='')
	{	
			 var sql67="";
            var sqlid67="BankContInput67";
            var mySql67=new SqlClass();
	        mySql67.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
	        mySql67.setSqlId(sqlid67);//ָ��ʹ�õ�Sql��id
			mySql67.addSubPara(document.all('AgentGroup').value);
	       strSQL=mySql67.getString();
		
	//strSQL = "select name from labranchgroup where agentgroup='" + document.all('AgentGroup').value +"'";
	//alert("3:"+strSQL);
	arrResult = easyExecSql(strSQL,1,0);
	
	if(arrResult!==null)
	  {
      document.all('BranchAttr').value=arrResult[0][0];
		}
	}
	
	if(document.all('ProposalContNo').value!=null&&document.all('ProposalContNo').value!='')
	{	
		    var sql68="";
            var sqlid68="BankContInput68";
            var mySql68=new SqlClass();
	        mySql68.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
	        mySql68.setSqlId(sqlid68);//ָ��ʹ�õ�Sql��id
			mySql68.addSubPara(document.all('ProposalContNo').value);
	       strSQL=mySql68.getString();
		
	//strSQL = "select BankAgent,AgentBankCode from lccont where ProposalContNo='" + document.all('ProposalContNo').value +"'";
  arrResult = easyExecSql(strSQL,1,0);
  
  if (arrResult!==null)
    {
      document.all('CounterCode').value=arrResult[0][0];
      //tongmeng 2007-09-10 ���ػ�ȡ����������
      //document.all('AgentBankCode').value=arrResult[0][1];      
  	}
  }

} 

    	





var cacheWin=null;
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
				var iHeight=1;     //�������ڵĸ߶�; 
				var iTop = 1; //��ô��ڵĴ�ֱλ�� 
				var iLeft = 1;  //��ô��ڵ�ˮƽλ�� 
				strQueryResult = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

				strQueryResult.focus();
			}
		}
		else if(strCode=='OccupationCode')
		{
			
			var sql69="";
            var sqlid69="BankContInput69";
            var mySql69=new SqlClass();
	        mySql69.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
	        mySql69.setSqlId(sqlid69);//ָ��ʹ�õ�Sql��id
			mySql69.addSubPara(showObjc.value);
	       var tSQL=mySql69.getString();
			
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
		
			var sql70="";
            var sqlid70="BankContInput70";
            var mySql70=new SqlClass();
	        mySql70.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
	        mySql70.setSqlId(sqlid70);//ָ��ʹ�õ�Sql��id
			mySql70.addSubPara(PlaceCode);
			mySql70.addSubPara(PlaceType);
	      var strSQL=mySql70.getString();
		
		//var strSQL="select placecode,placename from ldaddress where placecode='"+PlaceCode+"' and placetype='"+PlaceType+"'";
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

//��ѯ���±�
function checkNotePad(prtNo,LoadFlag){
	
			var sql71="";
            var sqlid71="BankContInput71";
            var mySql71=new SqlClass();
	        mySql71.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
	        mySql71.setSqlId(sqlid71);//ָ��ʹ�õ�Sql��id
			mySql71.addSubPara(prtNo);
	      var strSQL=mySql71.getString();
	
//	var strSQL="select count(*) from LWNotePad where otherno='"+prtNo+"'";
	
	var arrResult = easyExecSql(strSQL);
	
	eval("document.all('NotePadButton"+LoadFlag+"').value='���±��鿴 ����"+arrResult[0][0]+"����'");
	
}


//���ƽ��水ť��������ʾ
function ctrlButtonDisabled(tContNo,tLoadFlag){
  var tSQL = "";
  var arrResult;
  var arrButtonAndSQL = new Array;
  
  if(tLoadFlag==5){
    arrButtonAndSQL[0] = new Array;
    arrButtonAndSQL[0][0] = "ApproveQuestQuery";
    arrButtonAndSQL[0][1] = "�������ѯ";
	
			var sql72="";
            var sqlid72="BankContInput72";
            var mySql72=new SqlClass();
	        mySql72.setResourceName("app.BankContInputSql"); //ָ��ʹ�õ�properties�ļ���
	        mySql72.setSqlId(sqlid72);//ָ��ʹ�õ�Sql��id
			mySql72.addSubPara(tContNo);
	      var strSQL72=mySql72.getString();
	
    arrButtonAndSQL[0][2] = strSQL72;//"select * from lcissuepol where 2 = 2 and OperatePos in ('0', '1', '5', 'A', 'I') and ContNo = '"+tContNo+"'";
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

/**Ͷ������Ϣ���޸ģ�Ͷ�����뱻���˹�ϵΪ���ˣ�ͬ���޸ı�������Ϣ��
*---yeshu,20071220
*/
function updateInsured()
{
//	alert("��������2007-12-20���˹��ܴ�����");
	document.all('InsuredNo').value=document.all('AppntNo').value;
	displayissameperson();
	modifyRecord();
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

/**
 * Ͷ������ǿ�ƹ���
 * customerType: 0-Ͷ����  1-������
 */
function customerForceUnion(customerType){
	var customerno = "";
	var prtNo = fm.PrtNo.value;
	if(customerType=="0"){
		customerno = fm.AppntNo.value;
	} else if(customerType="1"){
		customerno = fm.InsuredNo.value;
	}
	var newWindow = window.open("../app/CustomerForceUnionMain.jsp?customerno="+customerno+"&prtno="+prtNo,"CustomerForceUnionMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	
}
