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
this.window.onfocus = myonfocus;
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
    //modify by zhangxing
    if (verifyInputNB("1") == false) return false;
    if (verifyInputNB("2") == false) return false;
    if (fm.PolAppntDate.value.length != 10 || fm.PolAppntDate.value.substring(4, 5) != '-' || fm.PolAppntDate.value.substring(7, 8) != '-' || fm.PolAppntDate.value.substring(0, 1) == '0')
    {
        alert("��������ȷ�����ڸ�ʽ��");
        fm.PolAppntDate.focus();
        return;
    }
    //���ź�Ͷ�����Ų�������ͬ
	if(trim(fm.ContCardNo.value)==trim(fm.ProposalContNo.value))
	{
		alert("���ź�Ͷ�����Ų�������ͬ��\n ������Ŀ��ź�Ͷ��������ͬ����������");
		return false;
	}
	
    if (fm.AppntIDType.value != "" && fm.AppntIDNo.value == "" || fm.AppntIDType.value == "" && fm.AppntIDNo.value != "") {
        alert("֤�����ͺ�֤���������ͬʱ��д����");
        return false;
    }

		if(trim(document.all('Mult').value)== "")
		{
			alert('��������Ϊ��!');
			return false;
		}
		if(!isNumeric(trim(document.all('Mult').value)))
		{
				alert('��������Ϊ����!');
				document.all('Mult').value = "";
				document.all('Mult').focus();
				return false;
		}
		if(parseFloat(trim(document.all('Mult').value)) == 0)
		{
			alert('��������Ϊ��!');
			document.all('Mult').value = "";
			document.all('Mult').focus();
			return false;
		}

    for (i = 0; i < BnfGrid.mulLineCount; i++)
    {

        //��������˵�����ݶ������˳��û��¼����Ĭ��Ϊ1
        if (BnfGrid.getRowColData(i, 7) == null || BnfGrid.getRowColData(i, 7) == '')
        {
            BnfGrid.setRowColData(i, 7, "1");
        }
        if (BnfGrid.getRowColData(i, 6) == null || BnfGrid.getRowColData(i, 6) == '')
        {
            BnfGrid.setRowColData(i, 6, "1");
        }
    }

		//������������Ϣ�б����Ϣ��ֵ�����������ؼ���
   setValueFromPolOtherGrid();

    getdetailwork();
    getdetailwork2();

    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.fmAction.value = "INSERT";
    fm.submit();
    //�ύ
}
function submitForm1()
{
    //�޸İ�Ŧ�ύ�¼�
    if (trim(fm.ProposalContNo.value) == "")
    {
        alert("������дͶ�����ţ�");
        return;
    }
	
		var sqlid1="CardContInputSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(fm.ProposalContNo.value);//ָ������Ĳ���
	    strSql=mySql1.getString();	
	
//    strSql = "select * from lccont where contno = '" + fm.ProposalContNo.value + "' and CardFlag='3'";
    var HaveTB = easyExecSql(strSql);
    if (HaveTB == null)
    {
        alert("�����������û�б��棡");
        return;
    }
    fm.fmAction.value = "EDIT";
    fm.submit();
}
function submitForm2()
{
    //�޸İ�Ŧ�ύ�¼�
    if (trim(fm.ProposalContNo.value) == "" || trim(fm.ContCardNo.value) == "")
    {
        alert("��ͬʱ��д���ź�Ͷ�����ţ�");
        return;
    }
    else
	{
		fm.ProposalContNo.value=trim(fm.ProposalContNo.value);
		fm.ContCardNo.value=trim(fm.ContCardNo.value);
	}
    //���ź�Ͷ�����Ų�������ͬ
	if(trim(fm.ContCardNo.value)==trim(fm.ProposalContNo.value))
	{
		alert("���ź�Ͷ�����Ų�������ͬ��\n ������Ŀ��ź�Ͷ��������ͬ����������");
		return false;
	}
	
		var sqlid2="CardContInputSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(fm.ProposalContNo.value);//ָ������Ĳ���
	    strSql=mySql2.getString();	
	
//    strSql = "select * from lccont where contno = '" + fm.ProposalContNo.value + "' and CardFlag='3'";
    var HaveTB = easyExecSql(strSql);
    if (HaveTB == null)
    {
        alert("�����������û�б��棡");
        return;
    }
    //�Ա��ѽ���У��:������ɵı��Ѻ�¼��ı��Ѳ����,��ǩ������ͨ��
    if (checkpayfee(fm.ProposalContNo.value) == false)
    {
        return false;
    }

    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action = "./CardSign.jsp";
    fm.submit();
}
/*********************************************************************
 *  �������Ͷ�������ύ��Ĳ���,���������ݷ��غ�ִ�еĲ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterSubmit(FlagStr, content, contract)
{
    //alert("here 1!");
    showInfo.close();

    if (FlagStr == "Fail")
    {
        var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
//        strSql = "select uwflag From lccont where contno='" + contract + "'";
        
    	var sqlid1="CardContInputSql79";
    	var mySql1=new SqlClass();
    	mySql1.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
    	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
    	mySql1.addSubPara(contract);//ָ������Ĳ���
    	strSql=mySql1.getString();
        
        
        var uwflag = easyExecSql(strSql);
        //alert(uwflag);
        if (uwflag == "9")
        {
            content = "�Ժ�ͨ�� !  " + content;
        }
        var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        //showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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


        if (approvefalg == "2")
        {
            //top.close();
        }

        //	  try { goToPic(2) } catch(e) {}

    }
    mAction = "";
}


function afterSubmit4(FlagStr, content)
{

    showInfo.close();
    if (FlagStr == "Fail")
    {
        var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
        //showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
//        var tt = "select amnt,prem from lcpol where contno='" + fm.ProposalContNo.value + "'";
        
        var ProposalContNo1 = fm.ProposalContNo.value;
    	var sqlid1="CardContInputSql80";
    	var mySql1=new SqlClass();
    	mySql1.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
    	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
    	mySql1.addSubPara(ProposalContNo1);//ָ������Ĳ���
    	var tt=mySql1.getString();
    	
        var Result = easyExecSql(tt, 1, 0);
        //alert(Result);
        if (Result != null)
        {
            fm.Amnt.value = Result[0][0];
            fm.Prem.value = Result[0][1];
        }

    }

}
function afterSubmit5(FlagStr, content)
{
    //alert("here 1!");
    showInfo.close();
    if (FlagStr == "Fail")
    {
        var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
        //showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();

        showDiv(operateButton, "true");

        //top.close();


        if (approvefalg == "2")
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
    catch(re)
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
    showDiv(operateButton, "true");
    showDiv(inputButton, "false");
}

/*********************************************************************
 *  ��ʾfrmSubmit��ܣ���������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showSubmitFrame(cDebug)
{
    if (cDebug == "1")
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
    showDiv(operateButton, "false");
    showDiv(inputButton, "true");
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
    if (verifyInput2() == false) return false;
}

/*********************************************************************
 *  Click�¼������������ѯ��ͼƬʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function queryClick()
{
    if (this.ScanFlag == "1") {
        alert("��ɨ���¼�벻�����ѯ!");
        return false;
    }
    if (mOperate == 0)
    {
        mOperate = 1;
        cContNo = document.all('ContNo').value;
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
    if (fm.AppntIDType.value != "" && fm.AppntIDNo.value == "" || fm.AppntIDType.value == "" && fm.AppntIDNo.value != "") {
        alert("֤�����ͺ�֤���������ͬʱ��д����");
        return false;
    }
    if ((fm.PayMode.value == "4" || fm.PayMode.value == "7") && fm.AppntBankCode.value == "" && fm.AppntAccName.value == "" && fm.AppntBankAccNo.value == "")
    {

        alert("����������ת�ʿ����С������ʻ������������ʻ��ţ�");
        return false;
    }
    if (fm.SecPayMode.value == "3" && fm.SecAppntBankCode.value == "" && fm.SecAppntAccName.value == "" && fm.SecAppntBankAccNo.value == "")
    {

        alert("����������ת�ʿ����С������ʻ������������ʻ��ţ�");
        return false;
    }
    if (verifyInputNB("1") == false) return false;

    var tGrpProposalNo = "";
    tGrpProposalNo = document.all('ProposalContNo').value;
    if (tGrpProposalNo == null || tGrpProposalNo == "")
        alert("������Ͷ������ѯ�������ٽ����޸�!");
    else
    {
        ImpartGrid.delBlankLine();
        var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
        var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;

        if (mAction == "")
        {
            //showSubmitFrame(mDebug);
            //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            mAction = "UPDATE";
            document.all('fmAction').value = mAction;
            fm.action = "ContSave.jsp";
            fm.submit();
            //�ύ
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
    if (LoadFlag == 1)
    {
        tGrpProposalNo = document.all('GrpContNo').value;
        if (tGrpProposalNo == null || tGrpProposalNo == "")
            alert("������Ͷ������ѯ�������ٽ���ɾ��!");
        else
        {
            var showStr = "����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
            var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;

            if (mAction == "")
            {
                //showSubmitFrame(mDebug);
                //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
				var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
				var iWidth=550;      //�������ڵĿ��; 
				var iHeight=250;     //�������ڵĸ߶�; 
				var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
				var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
				showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

				showInfo.focus();
                mAction = "DELETE";
                document.all('fmAction').value = mAction;
                fm.action = "ContSave.jsp";
                fm.submit();
                //�ύ
            }
        }

    }
    //top.close();
}

/*********************************************************************
 *  delete�¼����������ɾ������ťʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function deleteClick2()
{
    if (trim(fm.ProposalContNo.value) == "")
    {
        alert("����¼��Ͷ�����ţ��ٽ���ɾ������!")
        return;
    }
	
		var sqlid3="CardContInputSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
		mySql3.addSubPara(fm.ProposalContNo.value);//ָ������Ĳ���
	    strSql=mySql3.getString();	
	
//    strSql = "select * from lccont where contno = '" + fm.ProposalContNo.value + "' and CardFlag='3'";
    var HaveTB = easyExecSql(strSql);
    if (HaveTB == null)
    {
        alert("�ñ��������ڣ��޷�����ɾ��!");
        return;
    }
    else
    {
        var showStr = "����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
        var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;

        if (mAction == "")
        {
            //showSubmitFrame(mDebug);
            //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            mAction = "DELETE";
            document.all('fmAction').value = mAction;
            fm.action = "CardContSave.jsp";
            fm.submit();
            //�ύ
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
function showDiv(cDiv, cShow)
{
    if (cShow == "true")
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
    fm.ContNo.value = fm.ProposalContNo.value;
    if (fm.ContNo.value == "")
    {
        alert("��������¼���ͬ��Ϣ���ܽ��뱻��������Ϣ���֡�");
        return false;
    }

    //�Ѽ�����Ϣ�����ڴ�
    mSwitch = parent.VD.gVSwitch;
    //���ݴ�
    putCont();

    try {
        goToPic(2)
    } catch(e) {
    }

    try {
        parent.fraInterface.window.location = "./ContInsuredInput.jsp?LoadFlag=" + LoadFlag + "&type=" + type + "&MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID + "&AppntNo=" + tAppntNo + "&AppntName=" + tAppntName + "&checktype=1" + "&scantype=" + scantype;
    }
    catch (e) {
        parent.fraInterface.window.location = "./ContInsuredInput.jsp?LoadFlag=1&type=" + type + "&MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID + "&AppntNo=" + tAppntNo + "&AppntName=" + tAppntName;
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
    if (wFlag == 1) //¼�����ȷ��
    {
        //alert(ScanFlag);
		
		var sqlid4="CardContInputSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
		mySql4.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	     var tStr=mySql4.getString();	
		
//        var tStr = "	select * from lwmission where 1=1 and lwmission.activityid in ('0000001001','0000001002','0000001220','0000001230') and lwmission.missionprop1 = '" + fm.ContNo.value + "'";
        turnPage.strQueryResult = easyQueryVer3(tStr, 1, 0, 1);
        if (turnPage.strQueryResult) {
            alert("�ú�ͬ�Ѿ��������棡");
            return;
        }
        if (document.all('ProposalContNo').value == "")
        {
            alert("��ͬ��Ϣδ����,������������ [¼�����] ȷ�ϣ�");
            return;
        }
        if (ScanFlag == "1") {
            fm.WorkFlowFlag.value = "0000001099";
        }
        else {
            fm.WorkFlowFlag.value = "0000001098";
        }
        fm.MissionID.value = tMissionID;
        fm.SubMissionID.value = tSubMissionID;
        //¼�����
    }
    else if (wFlag == 2)//�������ȷ��
    {
        if (document.all('ProposalContNo').value == "")
        {
            alert("δ��ѯ����ͬ��Ϣ,������������ [�������] ȷ�ϣ�");
            return;
        }
        fm.WorkFlowFlag.value = "0000001001";
        //�������
        fm.MissionID.value = tMissionID;
        fm.SubMissionID.value = tSubMissionID;
        approvefalg = "2";
    }
    else if (wFlag == 3)
    {
        if (document.all('ProposalContNo').value == "")
        {
            alert("δ��ѯ����ͬ��Ϣ,������������ [�����޸����] ȷ�ϣ�");
            return;
        }
        fm.WorkFlowFlag.value = "0000001002";
        //�����޸����
        fm.MissionID.value = tMissionID;
        fm.SubMissionID.value = tSubMissionID;
    }
    else if (wFlag == 4)
    {
        if (document.all('ProposalContNo').value == "")
        {
            alert("δ��ѯ����ͬ��Ϣ,������������ [�޸����] ȷ�ϣ�");
            return;
        }
        fm.WorkFlowFlag.value = "0000001021";
        //�����޸�
        fm.MissionID.value = tMissionID;
        fm.SubMissionID.value = tSubMissionID;
    }
    else
        return;

    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action = "./InputConfirm.jsp";
    fm.submit();
    //�ύ
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
    try {
        mSwitch.addVar("BODY", "", window.document.body.innerHTML);
    } catch(ex) {
    }
    ;
    // ������Ϣ
    //��"./AutoCreatLDGrpInit.jsp"�Զ�����
    try {
        mSwitch.addVar('ContNo', '', fm.ContNo.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('ProposalContNo', '', fm.ProposalContNo.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('PrtNo', '', fm.PrtNo.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('GrpContNo', '', fm.GrpContNo.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('ContType', '', fm.ContType.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('FamilyType', '', fm.FamilyType.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('PolType', '', fm.PolType.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('CardFlag', '', fm.CardFlag.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('ManageCom', '', fm.ManageCom.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AgentCom', '', fm.AgentCom.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AgentCode', '', fm.AgentCode.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AgentGroup', '', fm.AgentGroup.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AgentCode1', '', fm.AgentCode1.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AgentType', '', fm.AgentType.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('SaleChnl', '', fm.SaleChnl.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('Handler', '', fm.Handler.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('Password', '', fm.Password.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntNo', '', fm.AppntNo.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntName', '', fm.AppntName.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntSex', '', fm.AppntSex.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntBirthday', '', fm.AppntBirthday.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntIDType', '', fm.AppntIDType.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntIDNo', '', fm.AppntIDNo.value);
    } catch(ex) {
    }
    ;

    try {
        mSwitch.addVar('InsuredNo', '', fm.InsuredNo.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('InsuredName', '', fm.InsuredName.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('InsuredSex', '', fm.InsuredSex.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('InsuredBirthday', '', fm.InsuredBirthday.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('InsuredIDType', '', fm.InsuredIDType.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('InsuredIDNo', '', fm.InsuredIDNo.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('PayIntv', '', fm.PayIntv.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('PayMode', '', fm.PayMode.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('PayLocation', '', fm.PayLocation.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('DisputedFlag', '', fm.DisputedFlag.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('OutPayFlag', '', fm.OutPayFlag.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('GetPolMode', '', fm.GetPolMode.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('SignCom', '', fm.SignCom.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('SignDate', '', fm.SignDate.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('SignTime', '', fm.SignTime.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('ConsignNo', '', fm.ConsignNo.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('BankCode', '', fm.BankCode.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('BankAccNo', '', fm.BankAccNo.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AccName', '', fm.AccName.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('PrintCount', '', fm.PrintCount.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('LostTimes', '', fm.LostTimes.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('Lang', '', fm.Lang.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('Currency', '', fm.Currency.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('Remark', '', fm.Remark.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('Peoples', '', fm.Peoples.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('Mult', '', fm.Mult.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('Prem', '', fm.Prem.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('Amnt', '', fm.Amnt.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('SumPrem', '', fm.SumPrem.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('Dif', '', fm.Dif.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('PaytoDate', '', fm.PaytoDate.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('FirstPayDate', '', fm.FirstPayDate.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('CValiDate', '', fm.CValiDate.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('InputOperator', '', fm.InputOperator.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('InputDate', '', fm.InputDate.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('InputTime', '', fm.InputTime.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('ApproveFlag', '', fm.ApproveFlag.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('ApproveCode', '', fm.ApproveCode.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('ApproveDate', '', fm.ApproveDate.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('ApproveTime', '', fm.ApproveTime.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('UWFlag', '', fm.UWFlag.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('UWOperator', '', fm.UWOperator.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('UWDate', '', fm.UWDate.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('UWTime', '', fm.UWTime.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppFlag', '', fm.AppFlag.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('PolApplyDate', '', fm.PolApplyDate.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('GetPolDate', '', fm.GetPolDate.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('GetPolTime', '', fm.GetPolTime.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('CustomGetPolDate', '', fm.CustomGetPolDate.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('State', '', fm.State.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('Operator', '', fm.Operator.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('MakeDate', '', fm.MakeDate.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('MakeTime', '', fm.MakeTime.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('ModifyDate', '', fm.ModifyDate.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('ModifyTime', '', fm.ModifyTime.value);
    } catch(ex) {
    }
    ;

    //�µ����ݴ���
    try {
        mSwitch.addVar('AppntNo', '', fm.AppntNo.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntName', '', fm.AppntName.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntSex', '', fm.AppntSex.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntBirthday', '', fm.AppntBirthday.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntIDType', '', fm.AppntIDType.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntIDNo', '', fm.AppntIDNo.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntPassword', '', fm.AppntPassword.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntNativePlace', '', fm.AppntNativePlace.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntNationality', '', fm.AppntNationality.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AddressNo', '', fm.AppntAddressNo.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntRgtAddress', '', fm.AppntRgtAddress.value);
    } catch(ex) {
    }
    ;
    //try { mSwitch.addVar('AppntMarriage','',fm.AppntMarriage.value); } catch(ex) { };
    //try { mSwitch.addVar('AppntMarriageDate','',fm.AppntMarriageDate.value); } catch(ex) { };
    try {
        mSwitch.addVar('AppntHealth', '', fm.AppntHealth.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntStature', '', fm.AppntStature.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntAvoirdupois', '', fm.AppntAvoirdupois.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntDegree', '', fm.AppntDegree.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntCreditGrade', '', fm.AppntCreditGrade.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntOthIDType', '', fm.AppntOthIDType.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntOthIDNo', '', fm.AppntOthIDNo.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntICNo', '', fm.AppntICNo.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntGrpNo', '', fm.AppntGrpNo.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntJoinCompanyDate', '', fm.AppntJoinCompanyDate.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntStartWorkDate', '', fm.AppntStartWorkDate.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntPosition', '', fm.AppntPosition.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntSalary', '', fm.AppntSalary.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntOccupationType', '', fm.AppntOccupationType.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntOccupationCode', '', fm.AppntOccupationCode.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntWorkType', '', fm.AppntWorkType.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntPluralityType', '', fm.AppntPluralityType.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntDeathDate', '', fm.AppntDeathDate.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntSmokeFlag', '', fm.AppntSmokeFlag.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntBlacklistFlag', '', fm.AppntBlacklistFlag.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntProterty', '', fm.AppntProterty.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntRemark', '', fm.AppntRemark.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntState', '', fm.AppntState.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntOperator', '', fm.AppntOperator.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntMakeDate', '', fm.AppntMakeDate.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntMakeTime', '', fm.AppntMakeTime.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntModifyDate', '', fm.AppntModifyDate.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntModifyTime', '', fm.AppntModifyTime.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntHomeAddress', '', fm.AppntHomeAddress.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntHomeZipCode', '', fm.AppntHomeZipCode.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntHomePhone', '', fm.AppntHomePhone.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntHomeFax', '', fm.AppntHomeFax.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntGrpName', '', fm.AppntGrpName.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntGrpPhone', '', fm.AppntGrpPhone.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('CompanyAddress', '', fm.CompanyAddress.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntGrpZipCode', '', fm.AppntGrpZipCode.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntGrpFax', '', fm.AppntGrpFax.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntPostalAddress', '', fm.AppntPostalAddress.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntZipCode', '', fm.AppntZipCode.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntPhone', '', fm.AppntPhone.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntMobile', '', fm.AppntMobile.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntFax', '', fm.AppntFax.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntEMail', '', fm.AppntEMail.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntBankAccNo', '', document.all('AppntBankAccNo').value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntBankCode', '', document.all('AppntBankCode').value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntAccName', '', document.all('AppntAccName').value);
    } catch(ex) {
    }
    ;


}

/*********************************************************************
 *  �Ѽ�����Ϣ�ӱ�����ɾ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function delContVar()
{
    try {
        mSwitch.deleteVar("intoPolFlag");
    } catch(ex) {
    }
    ;
    // body��Ϣ
    try {
        mSwitch.deleteVar("BODY");
    } catch(ex) {
    }
    ;
    // ������Ϣ
    //��"./AutoCreatLDGrpInit.jsp"�Զ�����
    try {
        mSwitch.deleteVar('ContNo');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('ProposalContNo');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('PrtNo');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('GrpContNo');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('ContType');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('FamilyType');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('PolType');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('CardFlag');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('ManageCom');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AgentCom');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AgentCode');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AgentGroup');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AgentCode1');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AgentType');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('SaleChnl');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('Handler');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('Password');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntNo');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntName');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntSex');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntBirthday');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntIDType');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntIDNo');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('InsuredNo');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('InsuredNam');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('InsuredSex');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('InsuredBirthday');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('InsuredIDType');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('InsuredIDNo');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('PayIntv');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('PayMode');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('PayLocation');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('DisputedFlag');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('OutPayFlag');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('GetPolMode');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('SignCom');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('SignDate');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('SignTime');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('ConsignNo');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('BankCode');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('BankAccNo');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AccName');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('PrintCount');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('LostTimes');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('Lang');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('Currency');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('Remark');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('Peoples');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('Mult');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('Prem');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('Amnt');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('SumPrem');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('Dif');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('PaytoDate');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('FirstPayDate');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('CValiDate');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('InputOperator');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('InputDate');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('InputTime');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('ApproveFlag');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('ApproveCode');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('ApproveDate');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('ApproveTime');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('UWFlag');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('UWOperator');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('UWDate');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('UWTime');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppFlag');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('PolApplyDate');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('GetPolDate');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('GetPolTime');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('CustomGetPolDate');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('State');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('Operator');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('MakeDate');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('MakeTime');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('ModifyDate');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('ModifyTime');
    } catch(ex) {
    }
    ;

    //�µ�ɾ�����ݴ���
    try {
        mSwitch.deleteVar('AppntNo');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntName');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntSex');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntBirthday');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntIDType');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntIDNo');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntPassword');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntNativePlace');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntNationality');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AddressNo');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntRgtAddress');
    } catch(ex) {
    }
    ;
    ////try { mSwitch.deleteVar  ('AppntMarriage'); } catch(ex) { };
    //try { mSwitch.deleteVar  ('AppntMarriageDate'); } catch(ex) { };
    try {
        mSwitch.deleteVar('AppntHealth');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntStature');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntAvoirdupois');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntDegree');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntCreditGrade');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntOthIDType');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntOthIDNo');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntICNo');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntGrpNo');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntJoinCompanyDate');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntStartWorkDate');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntPosition')
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntSalary');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntOccupationType');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntOccupationCode');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntWorkType');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntPluralityType');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntDeathDate');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntSmokeFlag');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntBlacklistFlag');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntProterty');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntRemark');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntState');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntOperator');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntMakeDate');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntMakeTime');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntModifyDate');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntModifyTime');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntHomeAddress');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntHomeZipCode');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntHomePhone');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntHomeFax');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntPostalAddress');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntZipCode');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntPhone');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntFax');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntMobile');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntEMail');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntGrpName');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntGrpPhone');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('CompanyAddress');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntGrpZipCode');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntGrpFax');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntBankAccNo');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntBankCode');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntAccName');
    } catch(ex) {
    }
    ;
}


/*********************************************************************
 *  ��ѯ������ϸ��Ϣʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
 *  ����  ��  ��ѯ���صĶ�ά����
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterQuery(arrQueryResult) {

    if (arrQueryResult != null) {
        arrResult = arrQueryResult;

        if (mOperate == 1) {        // ��ѯͶ����
            document.all('ContNo').value = arrQueryResult[0][0];
			
		var sqlid5="CardContInputSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
		mySql5.addSubPara(arrQueryResult[0][0] );//ָ������Ĳ���
	     var tStr5=mySql5.getString();	
			
			arrResult = easyExecSql(tStr5, 1, 0);
            //arrResult = easyExecSql("select ProposalContNo,PrtNo,ManageCom,SaleChnl,AgentCode,AgentGroup,AgentCode1,AgentCom,AgentType,Remark from LCCont where LCCont = '" + arrQueryResult[0][0] + "'", 1, 0);

            if (arrResult == null) {
                alert("δ�鵽Ͷ������Ϣ");
            } else {
                displayLCContPol(arrResult[0]);
            }
        }

        if (mOperate == 2) {        // Ͷ������Ϣ
            //alert("arrQueryResult[0][0]=="+arrQueryResult[0][0]);
			
		var sqlid6="CardContInputSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
		mySql6.addSubPara(arrQueryResult[0][0]);//ָ������Ĳ���
	     var tStr6=mySql6.getString();	
			
			arrResult = easyExecSql(tStr6, 1, 0);
            //arrResult = easyExecSql("select a.*  from LDPerson a where 1=1  and a.CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0);
            if (arrResult == null) {
                alert("δ�鵽Ͷ������Ϣ");
            } else {
                displayAppnt(arrResult[0]);
            }
        }
    }

    mOperate = 0;
    // �ָ���̬

    showCodeName();

}
/*********************************************************************
 *  Ͷ������Ϣ��ʼ����������loadFlag��־��Ϊ��֧
 *  ����  ��  Ͷ����ӡˢ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function detailInit(PrtNo) {
    try {

        if (PrtNo == null) return;

        //��ѯ�����˺�
        //alert("PrtNo=="+PrtNo);
		
		var sqlid7="CardContInputSql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
		mySql7.addSubPara(PrtNo);//ָ������Ĳ���
	     var accSQL=mySql7.getString();	
		
//        var accSQL = "select a.BankCode,a.AccName,a.BankAccNo from LJTempFeeClass a,LJTempFee b "
//                + "where trim(a.TempFeeNo)=trim(b.TempFeeNo) and a.PayMode='4' and b.TempFeeType='1' "
//                + "and b.OtherNo='" + PrtNo + "'";

        arrResult = easyExecSql(accSQL, 1, 0);

        if (arrResult == null) {
            //return;
            //Ĭ��Ϊ����Ϊ����ת��
            //    	alert("aaa");
            //      document.all('PayMode').value="3";
        }
        else {
            displayFirstAccount();
            //������ڽ��ѷ�ʽΪ����ת�ˣ�����ͬ��Ϊ����ת��
            document.all('PayMode').value = "";
        }

		var sqlid8="CardContInputSql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
		mySql8.addSubPara(PrtNo);//ָ������Ĳ���
	     var accSQL8=mySql8.getString();	
		 
		 arrResult = easyExecSql(accSQL8, 1, 0);
        //arrResult = easyExecSql("select * from LCCont where PrtNo='" + PrtNo + "'", 1, 0);

        if (arrResult == null) {
            alert(δ�õ�Ͷ������Ϣ);
            return;
        }
        else {
            displayLCCont();
            //��ʾͶ������ϸ����
        }

		var sqlid9="CardContInputSql9";
		var mySql9=new SqlClass();
		mySql9.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql9.setSqlId(sqlid9);//ָ��ʹ�õ�Sql��id
		mySql9.addSubPara(PrtNo);//ָ������Ĳ���
	     var accSQL9=mySql9.getString();	
		 
		  arrResult = easyExecSql(accSQL9, 1, 0);
       // arrResult = easyExecSql("select a.* from LDPerson a where 1=1  and a.CustomerNo = '" + arrResult[0][19] + "'", 1, 0);

        if (arrResult == null) {
            alert("δ�鵽Ͷ���˿ͻ�����Ϣ");
        }
        else {
            //��ʾͶ������Ϣ
            displayAppnt(arrResult[0]);
        }

		var sqlid10="CardContInputSql10";
		var mySql10=new SqlClass();
		mySql10.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql10.setSqlId(sqlid10);//ָ��ʹ�õ�Sql��id
		mySql10.addSubPara(PrtNo);//ָ������Ĳ���
	     var accSQL10=mySql10.getString();	

        arrResult = easyExecSql(accSQL10, 1, 0);
       // arrResult = easyExecSql("select * from LCAppnt where PrtNo='" + PrtNo + "'", 1, 0);

        if (arrResult == null) {
            alert("δ�õ�Ͷ���˱�������Ϣ");
            return;
        } else {
            displayContAppnt();
            //��ʾͶ���˵���ϸ����
        }
        getAge();
        var tContNo = arrResult[0][1];
        var tCustomerNo = arrResult[0][3];
        // �õ�Ͷ���˿ͻ���
        var tAddressNo = arrResult[0][9];
        // �õ�Ͷ���˵�ַ��

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

        fm.AppntAddressNo.value = tAddressNo;

        //��ѯ����ʾͶ���˵�ַ��ϸ����
        getdetailaddress();

        //alert("zzz");
        //��ѯͶ���˸�֪��Ϣ
		
		var sqlid11="CardContInputSql11";
		var mySql11=new SqlClass();
		mySql11.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql11.setSqlId(sqlid11);//ָ��ʹ�õ�Sql��id
		mySql11.addSubPara(tCustomerNo);//ָ������Ĳ���
		mySql11.addSubPara(tContNo);//ָ������Ĳ���
	     var strSQL0=mySql11.getString();	
		 
		 var sqlid12="CardContInputSql12";
		var mySql12=new SqlClass();
		mySql12.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql12.setSqlId(sqlid12);//ָ��ʹ�õ�Sql��id
		mySql12.addSubPara(tCustomerNo);//ָ������Ĳ���
		mySql12.addSubPara(tContNo);//ָ������Ĳ���
	     var strSQL1=mySql12.getString();	
		
//        var strSQL0 = "select ImpartParam from LCCustomerImpartparams where CustomerNoType='0' and CustomerNo='" + tCustomerNo + "' and ContNo='" + tContNo + "' and impartver='01' and impartcode='001' and ImpartParamNo = '1'";
//        var strSQL1 = "select ImpartParam from LCCustomerImpartparams where CustomerNoType='0' and CustomerNo='" + tCustomerNo + "' and ContNo='" + tContNo + "' and impartver='01' and impartcode='001' and ImpartParamNo = '2'";
        arrResult = easyExecSql(strSQL0, 1, 0);
        arrResult1 = easyExecSql(strSQL1, 1, 0);


        try {
            document.all('Income0').value = arrResult[0][0];
        } catch(ex) {
        }
        ;
        try {
            document.all('IncomeWay0').value = arrResult1[0][0];
        } catch(ex) {
        }
        ;

		 var sqlid13="CardContInputSql13";
		var mySql13=new SqlClass();
		mySql13.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql13.setSqlId(sqlid13);//ָ��ʹ�õ�Sql��id
		mySql13.addSubPara(tCustomerNo);//ָ������Ĳ���
		mySql13.addSubPara(tContNo);//ָ������Ĳ���
	     var strSQL1=mySql13.getString();	

       // var strSQL1 = "select ImpartVer,ImpartCode,ImpartContent,ImpartParamModle from LCCustomerImpart where CustomerNoType='0' and CustomerNo='" + tCustomerNo + "' and ContNo='" + tContNo + "' and impartver in ('01','02') and impartcode<>'001'";

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
        else {
            initImpartGrid();
        }

        //����з�֧
        if (loadFlag == "5" || loadFlag == "25") {
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
		
		var sqlid14="CardContInputSql14";
		var mySql14=new SqlClass();
		mySql14.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql14.setSqlId(sqlid14);//ָ��ʹ�õ�Sql��id
		mySql14.addSubPara(tCustomerNo);//ָ������Ĳ���
		mySql14.addSubPara(tContNo);//ָ������Ĳ���
	     var strSQL14=mySql14.getString();	
		
		 arrResult = easyExecSql(strSQL14, 1, 0);
//        arrResult = easyExecSql("select c.agentcode,d.name,d.managecom,b.name,c.busirate,a.agentgroup,b.branchattr " +
//                                "from lccont a,labranchgroup b,lacommisiondetail c,laagent d " +
//                                "where 1=1 " +
//                                "and trim(a.contno)='" + tContNo + "' " +
//                                "and trim(b.agentgroup)=trim(c.agentgroup) " +
//                                "and trim(c.agentcode)=trim(a.agentcode) " +
//                                "and trim(d.agentcode)=trim(a.agentcode) " +
//                                "and trim(c.grpcontno)=trim(a.contno)", 1, 0);

        if (arrResult == null) {
            //alert("δ�õ���ҵ��Ա��Ϣ");
            //return;
        } else {
            displayMainAgent();
            //��ʾ��ҵ��Ա����ϸ����
        }
        /**************************************/
        //alert();
        //////////////////////////////////////////

        displayAgent();
        //��ʾ��ҵ��Ա����ϸ����
        //��ѯҵ��Ա��Ϣ
        queryAgent();

        //////////////////////////////////////////////
        //��ѯҵ��Ա��֪

		var sqlid15="CardContInputSql15";
		var mySql15=new SqlClass();
		mySql15.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql15.setSqlId(sqlid15);//ָ��ʹ�õ�Sql��id
		mySql15.addSubPara(tCustomerNo);//ָ������Ĳ���
		mySql15.addSubPara(tContNo);//ָ������Ĳ���
	     var aSQL=mySql15.getString();	

       // var aSQL = "select ImpartVer,ImpartCode,ImpartContent,ImpartParamModle from LCCustomerImpart where CustomerNoType='2' and CustomerNo='" + tCustomerNo + "' and ContNo='" + tContNo + "'";
        turnPage.queryModal(aSQL, AgentImpartGrid);


        //////////////////////////////////////////////////


    }
    catch(ex) {
    }

}


function displayAgent()
{
    if (fm.AgentCode.value != "") {
        var cAgentCode = fm.AgentCode.value;
        //��������
		
		var sqlid16="CardContInputSql16";
		var mySql16=new SqlClass();
		mySql16.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql16.setSqlId(sqlid16);//ָ��ʹ�õ�Sql��id
		mySql16.addSubPara(cAgentCode);//ָ������Ĳ���
	     var strSQL=mySql16.getString();	
		
//        var strSQL = "select a.AgentCode,a.AgentGroup,a.ManageCom,a.Name,c.BranchManager,b.IntroAgency,b.AgentSeries,b.AgentGrade,c.BranchAttr,b.AscriptSeries,c.name from LAAgent a,LATree b,LABranchGroup c where 1=1 "
//                + "and a.AgentCode = b.AgentCode and a.agentstate!='03' and a.agentstate!='04' and a.AgentGroup = c.AgentGroup and a.AgentCode='" + cAgentCode + "'";
        //alert(strSQL);
        var arrResult = easyExecSql(strSQL);
        //alert(arrResult);
        if (arrResult != null) {
            fm.AgentCode.value = arrResult[0][0];
            fm.BranchAttr.value = arrResult[0][10];
            fm.AgentGroup.value = arrResult[0][1];
            fm.AgentName.value = arrResult[0][3];
            fm.AgentManageCom.value = arrResult[0][2];
            showContCodeName();
            //alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][1]+"]");
        }
        else
        {
            fm.AgentGroup.value = "";
            alert("����Ϊ:[" + document.all('AgentCode').value + "]��ҵ��Ա�����ڣ���ȷ��!");
        }
    }
    else
    {
        return;
    }
}


/*********************************************************************
*  �Ѳ�ѯ���صĿͻ�������ʾ��Ͷ���˲���
*  ����  ��  ��
*  ����ֵ��  ��
*********************************************************************
*/
function displayAccount()
{
    try {
        document.all('AppntBankAccNo').value = arrResult[0][24];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntBankCode').value = arrResult[0][23];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntAccName').value = arrResult[0][25];
    } catch(ex) {
    }
    ;

}
/*********************************************************************
 *  �Ѳ�ѯ���صĿͻ�������ʾ��Ͷ���˲���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayCustomer()
{
    try {
        document.all('AppntNationality').value = arrResult[0][8];
    } catch(ex) {
    }
    ;
}
/*********************************************************************
 *  �Ѳ�ѯ���صĿͻ���ַ������ʾ��Ͷ���˲���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayAddress()
{
    try {
        document.all('AppntAddressNo').value = arrResult[0][0];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntPostalAddress').value = arrResult[0][1];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntZipCode').value = arrResult[0][2];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntPhone').value = arrResult[0][3];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntMobile').value = arrResult[0][4];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntEMail').value = arrResult[0][5];
    } catch(ex) {
    }
    ;
    //try{document.all('GrpName').value= arrResult[0][6];}catch(ex){};
    try {
        document.all('AppntGrpPhone').value = arrResult[0][6];
    } catch(ex) {
    }
    ;
    try {
        document.all('CompanyAddress').value = arrResult[0][7];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntGrpZipCode').value = arrResult[0][8];
    } catch(ex) {
    }
    ;
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
    try {
        document.all('AppntGrpContNo').value = arrResult[0][0];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntContNo').value = arrResult[0][1];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntPrtNo').value = arrResult[0][2];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntNo').value = arrResult[0][3];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntGrade').value = arrResult[0][4];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntName').value = arrResult[0][5];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntSex').value = arrResult[0][6];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntBirthday').value = arrResult[0][7];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntType').value = arrResult[0][8];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntAddressNo').value = arrResult[0][9];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntIDType').value = arrResult[0][10];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntIDNo').value = arrResult[0][11];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntNativePlace').value = arrResult[0][12];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntNationality').value = arrResult[0][13];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntRgtAddress').value = arrResult[0][14];
    } catch(ex) {
    }
    ;
    //  try{document.all('AppntMarriage').value= arrResult[0][15];}catch(ex){};
    //  try{document.all('AppntMarriageDate').value= arrResult[0][16];}catch(ex){};
    try {
        document.all('AppntHealth').value = arrResult[0][17];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntStature').value = arrResult[0][18];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntAvoirdupois').value = arrResult[0][19];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntDegree').value = arrResult[0][20];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntCreditGrade').value = arrResult[0][21];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntBankCode').value = arrResult[0][22];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntBankAccNo').value = arrResult[0][23];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntAccName').value = arrResult[0][24];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntJoinCompanyDate').value = arrResult[0][25];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntStartWorkDate').value = arrResult[0][26];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntPosition').value = arrResult[0][27];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntSalary').value = arrResult[0][28];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntOccupationType').value = arrResult[0][29];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntOccupationCode').value = arrResult[0][30];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntWorkType').value = arrResult[0][31];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntPluralityType').value = arrResult[0][32];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntSmokeFlag').value = arrResult[0][33];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntOperator').value = arrResult[0][34];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntManageCom').value = arrResult[0][35];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntMakeDate').value = arrResult[0][36];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntMakeTime').value = arrResult[0][37];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntModifyDate').value = arrResult[0][38];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntModifyTime').value = arrResult[0][39];
    } catch(ex) {
    }
    ;
    //getAge();
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
    try {
        document.all('AppntNo').value = arrResult[0][0];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntName').value = arrResult[0][1];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntSex').value = arrResult[0][2];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntBirthday').value = arrResult[0][3];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntIDType').value = arrResult[0][4];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntIDNo').value = arrResult[0][5];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntPassword').value = arrResult[0][6];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntNativePlace').value = arrResult[0][7];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntNationality').value = arrResult[0][8];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntRgtAddress').value = arrResult[0][9];
    } catch(ex) {
    }
    ;
    //try{document.all('AppntMarriage').value= arrResult[0][10];}catch(ex){};
    //try{document.all('AppntMarriageDate').value= arrResult[0][11];}catch(ex){};
    try {
        document.all('AppntHealth').value = arrResult[0][12];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntStature').value = arrResult[0][13];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntAvoirdupois').value = arrResult[0][14];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntDegree').value = arrResult[0][15];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntCreditGrade').value = arrResult[0][16];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntOthIDType').value = arrResult[0][17];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntOthIDNo').value = arrResult[0][18];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntICNo').value = arrResult[0][19];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntGrpNo').value = arrResult[0][20];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntJoinCompanyDate').value = arrResult[0][21];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntStartWorkDate').value = arrResult[0][22];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntPosition').value = arrResult[0][23];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntSalary').value = arrResult[0][24];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntOccupationType').value = arrResult[0][25];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntOccupationCode').value = arrResult[0][26];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntWorkType').value = arrResult[0][27];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntPluralityType').value = arrResult[0][28];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntDeathDate').value = arrResult[0][29];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntSmokeFlag').value = arrResult[0][30];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntBlacklistFlag').value = arrResult[0][31];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntProterty').value = arrResult[0][32];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntRemark').value = arrResult[0][33];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntState').value = arrResult[0][34];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntVIPValue').value = arrResult[0][35];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntOperator').value = arrResult[0][36];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntMakeDate').value = arrResult[0][37];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntMakeTime').value = arrResult[0][38];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntModifyDate').value = arrResult[0][39];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntModifyTime').value = arrResult[0][40];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntGrpName').value = arrResult[0][41];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntLicenseType').value = arrResult[0][43];
    } catch(ex) {
    }
    ;


    //˳�㽫Ͷ���˵�ַ��Ϣ�Ƚ��г�ʼ��
    try {
        document.all('AppntPostalAddress').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntPostalAddress').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntZipCode').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntPhone').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntFax').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntMobile').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntEMail').value = "";
    } catch(ex) {
    }
    ;
    //try{document.all('AppntGrpName').value= "";}catch(ex){};
    try {
        document.all('AppntHomeAddress').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntHomeZipCode').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntHomePhone').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntHomeFax').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntGrpPhone').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('CompanyAddress').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntGrpZipCode').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntGrpFax').value = "";
    } catch(ex) {
    }
    ;
}
/**
 *Ͷ������ϸ������ʾ
 */
function displayLCCont() {
    //alert("aaa");
    try {
        document.all('GrpContNo').value = arrResult[0][0];
    } catch(ex) {
    }
    ;
    try {
        document.all('ContNo').value = arrResult[0][1];
    } catch(ex) {
    }
    ;
    try {
        document.all('ProposalContNo').value = arrResult[0][2];
    } catch(ex) {
    }
    ;
    try {
        document.all('PrtNo').value = arrResult[0][3];
    } catch(ex) {
    }
    ;
    try {
        document.all('ContType').value = arrResult[0][4];
    } catch(ex) {
    }
    ;
    try {
        document.all('FamilyType').value = arrResult[0][5];
    } catch(ex) {
    }
    ;
    try {
        document.all('FamilyID').value = arrResult[0][6];
    } catch(ex) {
    }
    ;
    try {
        document.all('PolType ').value = arrResult[0][7];
    } catch(ex) {
    }
    ;
    try {
        document.all('CardFlag').value = arrResult[0][8];
    } catch(ex) {
    }
    ;
    try {
        document.all('ManageCom').value = arrResult[0][9];
    } catch(ex) {
    }
    ;
    try {
        document.all('ExecuteCom ').value = arrResult[0][10];
    } catch(ex) {
    }
    ;
    try {
        document.all('AgentCom').value = arrResult[0][11];
    } catch(ex) {
    }
    ;
    try {
        document.all('AgentCode').value = arrResult[0][12];
    } catch(ex) {
    }
    ;
    try {
        document.all('AgentGroup').value = arrResult[0][13];
    } catch(ex) {
    }
    ;
    try {
        document.all('AgentCode1 ').value = arrResult[0][14];
    } catch(ex) {
    }
    ;
    try {
        document.all('AgentType').value = arrResult[0][15];
    } catch(ex) {
    }
    ;
    try {
        document.all('SaleChnl').value = arrResult[0][16];
    } catch(ex) {
    }
    ;
    try {
        document.all('Handler').value = arrResult[0][17];
    } catch(ex) {
    }
    ;
    try {
        document.all('Password').value = arrResult[0][18];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntNo').value = arrResult[0][19];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntName').value = arrResult[0][20];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntSex').value = arrResult[0][21];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntBirthday ').value = arrResult[0][22];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntIDType').value = arrResult[0][23];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntIDNo').value = arrResult[0][24];
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredNo').value = arrResult[0][25];
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredName').value = arrResult[0][26];
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredSex').value = arrResult[0][27];
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredBirthday').value = arrResult[0][28];
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredIDType ').value = arrResult[0][29];
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredIDNo').value = arrResult[0][30];
    } catch(ex) {
    }
    ;
    try {
        document.all('PayIntv').value = arrResult[0][31];
    } catch(ex) {
    }
    ;
    try {
        document.all('SecPayMode').value = arrResult[0][32];
    } catch(ex) {
    }
    ;
    try {
        document.all('PayLocation').value = arrResult[0][33];
    } catch(ex) {
    }
    ;
    try {
        document.all('DisputedFlag').value = arrResult[0][34];
    } catch(ex) {
    }
    ;
    try {
        document.all('OutPayFlag').value = arrResult[0][35];
    } catch(ex) {
    }
    ;
    try {
        document.all('GetPolMode').value = arrResult[0][36];
    } catch(ex) {
    }
    ;
    try {
        document.all('SignCom').value = arrResult[0][37];
    } catch(ex) {
    }
    ;
    try {
        document.all('SignDate').value = arrResult[0][38];
    } catch(ex) {
    }
    ;
    try {
        document.all('SignTime').value = arrResult[0][39];
    } catch(ex) {
    }
    ;
    try {
        document.all('ConsignNo').value = arrResult[0][40];
    } catch(ex) {
    }
    ;
    try {
        document.all('SecAppntBankCode').value = arrResult[0][41];
    } catch(ex) {
    }
    ;
    try {
        document.all('SecAppntBankAccNo').value = arrResult[0][42];
    } catch(ex) {
    }
    ;
    try {
        document.all('SecAppntAccName').value = arrResult[0][43];
    } catch(ex) {
    }
    ;
    try {
        document.all('PrintCount').value = arrResult[0][44];
    } catch(ex) {
    }
    ;
    try {
        document.all('LostTimes').value = arrResult[0][45];
    } catch(ex) {
    }
    ;
    try {
        document.all('Lang').value = arrResult[0][46];
    } catch(ex) {
    }
    ;
    try {
        document.all('Currency').value = arrResult[0][47];
    } catch(ex) {
    }
    ;
    try {
        document.all('Remark').value = arrResult[0][48];
    } catch(ex) {
    }
    ;
    try {
        document.all('Peoples ').value = arrResult[0][49];
    } catch(ex) {
    }
    ;
    try {
        document.all('Mult').value = arrResult[0][50];
    } catch(ex) {
    }
    ;
    try {
        document.all('Prem').value = arrResult[0][51];
    } catch(ex) {
    }
    ;
    try {
        document.all('Amnt').value = arrResult[0][52];
    } catch(ex) {
    }
    ;
    try {
        document.all('SumPrem').value = arrResult[0][53];
    } catch(ex) {
    }
    ;
    try {
        document.all('Dif').value = arrResult[0][54];
    } catch(ex) {
    }
    ;
    try {
        document.all('PaytoDate').value = arrResult[0][55];
    } catch(ex) {
    }
    ;
    try {
        document.all('FirstPayDate').value = arrResult[0][56];
    } catch(ex) {
    }
    ;
    try {
        document.all('CValiDate').value = arrResult[0][57];
    } catch(ex) {
    }
    ;
    try {
        document.all('InputOperator ').value = arrResult[0][58];
    } catch(ex) {
    }
    ;
    try {
        document.all('InputDate').value = arrResult[0][59];
    } catch(ex) {
    }
    ;
    try {
        document.all('InputTime').value = arrResult[0][60];
    } catch(ex) {
    }
    ;
    try {
        document.all('ApproveFlag').value = arrResult[0][61];
    } catch(ex) {
    }
    ;
    try {
        document.all('ApproveCode').value = arrResult[0][62];
    } catch(ex) {
    }
    ;
    try {
        document.all('ApproveDate').value = arrResult[0][63];
    } catch(ex) {
    }
    ;
    try {
        document.all('ApproveTime').value = arrResult[0][64];
    } catch(ex) {
    }
    ;
    try {
        document.all('UWFlag').value = arrResult[0][65];
    } catch(ex) {
    }
    ;
    try {
        document.all('UWOperator').value = arrResult[0][66];
    } catch(ex) {
    }
    ;
    try {
        document.all('UWDate').value = arrResult[0][67];
    } catch(ex) {
    }
    ;
    try {
        document.all('UWTime').value = arrResult[0][68];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppFlag').value = arrResult[0][69];
    } catch(ex) {
    }
    ;
    try {
        document.all('PolAppntDate').value = arrResult[0][70];
    } catch(ex) {
    }
    ;
    try {
        document.all('GetPolDate').value = arrResult[0][71];
    } catch(ex) {
    }
    ;
    try {
        document.all('GetPolTime').value = arrResult[0][72];
    } catch(ex) {
    }
    ;
    try {
        document.all('CustomGetPolDate').value = arrResult[0][73];
    } catch(ex) {
    }
    ;
    try {
        document.all('State').value = arrResult[0][74];
    } catch(ex) {
    }
    ;
    try {
        document.all('Operator').value = arrResult[0][75];
    } catch(ex) {
    }
    ;
    try {
        document.all('MakeDate').value = arrResult[0][76];
    } catch(ex) {
    }
    ;
    try {
        document.all('MakeTime').value = arrResult[0][77];
    } catch(ex) {
    }
    ;
    try {
        document.all('ModifyDate').value = arrResult[0][78];
    } catch(ex) {
    }
    ;
    try {
        document.all('ModifyTime').value = arrResult[0][79];
    } catch(ex) {
    }
    ;
    try {
        document.all('SellType').value = arrResult[0][87];
    } catch(ex) {
    }
    ;

    try {
        document.all('AppntBankCode').value = arrResult[0][90];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntBankAccNo').value = arrResult[0][91];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntAccName').value = arrResult[0][92];
    } catch(ex) {
    }
    ;
    try {
        document.all('PayMode').value = arrResult[0][93];
    } catch(ex) {
    }
    ;

}
//ʹ�ôӸô��ڵ����Ĵ����ܹ��۽�
function myonfocus()
{
    if (showInfo != null)
    {
        try
        {
            showInfo.focus();
        }
        ;
    catch
        (ex)
    {
        showInfo = null;
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
		
		var sqlid17="CardContInputSql17";
		var mySql17=new SqlClass();
		mySql17.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql17.setSqlId(sqlid17);//ָ��ʹ�õ�Sql��id
		mySql17.addSubPara(fm.AppntNo.value);//ָ������Ĳ���
	     var strSQL17=mySql17.getString();	
		
		arrResult = easyExecSql(strSQL17, 1, 0);
        //arrResult = easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo = '" + document.all("AppntNo").value + "'", 1, 0);
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
    if (mOperate == 0)
    {
        mOperate = 2;
        showInfo = window.open("../sys/LDPersonQueryNewMain.jsp?queryFlag=queryAppnt","",sFeatures);
    }
}

function queryAgent()
{

    if (document.all('ManageCom').value == "") {
        alert("����¼����������Ϣ��");
        return;
    }
    if (document.all('AgentCode').value == "") {
        //var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+",AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
        var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom=" + document.all('ManageCom').value, "AgentCommonQueryMain", 'width=' + screen.availWidth + ',height=' + screen.availHeight + ',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0;'+sFeatures);
    }
    //alert(0)
    if (document.all('AgentCode').value != "") {

        var cAgentCode = fm.AgentCode.value;
        //��������

        //���ҵ��Ա���볤��Ϊ8���Զ���ѯ����Ӧ�Ĵ������ֵ���Ϣ
        //alert("cAgentCode=="+cAgentCode);
        if (cAgentCode.length != 8) {
            return;
        }
        //var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"' and ManageCom = '"+document.all('ManageCom').value+"'";
      
//	  	var sqlid18="CardContInputSql18";
//		var mySql18=new SqlClass();
//		mySql18.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
//		mySql18.setSqlId(sqlid18);//ָ��ʹ�õ�Sql��id
//		mySql18.addSubPara(cAgentCode);//ָ������Ĳ���
//	     var strSQL=mySql18.getString();	
//	  
//	    var strSQL = "select a.AgentCode,a.AgentGroup,a.ManageCom,a.Name,c.BranchManager,b.IntroAgency,b.AgentSeries,b.AgentGrade,c.BranchAttr,b.AscriptSeries,c.name from LAAgent a,LATree b,LABranchGroup c where 1=1 "
//                + "and a.AgentCode = b.AgentCode and a.agentstate!='03' and a.agentstate!='04' and a.AgentGroup = c.AgentGroup and a.AgentCode='" + cAgentCode + "'";

	  	var sqlid81="CardContInputSql81";
		var mySql81=new SqlClass();
		mySql81.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql81.setSqlId(sqlid81);//ָ��ʹ�õ�Sql��id
		mySql81.addSubPara(cAgentCode);//ָ������Ĳ���
	    var strSQL=mySql81.getString();
	    
	    
        var arrResult = easyExecSql(strSQL);

        if (arrResult != null) {
            fm.AgentCode.value = arrResult[0][0];
            fm.BranchAttr.value = arrResult[0][10];
            fm.AgentGroup.value = arrResult[0][1];
            fm.AgentName.value = arrResult[0][3];
            fm.AgentManageCom.value = arrResult[0][2];

            if (fm.AgentManageCom.value != fm.ManageCom.value)
            {

                fm.ManageCom.value = fm.AgentManageCom.value;
                fm.ManageComName.value = fm.AgentManageComName.value;
                //showCodeName('comcode','ManageCom','AgentManageComName');  //���뺺��

            }
            showContCodeName();
            //alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][1]+"]");
        }
        else
        {
            fm.AgentGroup.value = "";
            alert("����Ϊ:[" + document.all('AgentCode').value + "]��ҵ��Ա�����ڣ���ȷ��!");
            return;
        }
    }
}

//�����¼��
function QuestInput2()
{
    cContNo = fm.ContNo.value;
    //��������
    if (cContNo == "")
    {
        alert("���޺�ͬͶ�����ţ����ȱ���!");
    }
    else
    {
        window.open("../uw/QuestInputMain.jsp?ContNo=" + cContNo + "&Flag=" + LoadFlag, "window1",sFeatures);
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

    if (arrResult != null)
    {
        //  	fm.AgentCode.value = arrResult[0][0];
        //  	fm.BranchAttr.value = arrResult[0][93];
        //  	fm.AgentGroup.value = arrResult[0][1];
        //  	fm.AgentName.value = arrResult[0][5];
        //  	fm.AgentManageCom.value = arrResult[0][2];
        fm.AgentCode.value = arrResult[0][0];
        fm.BranchAttr.value = arrResult[0][10];
        fm.AgentGroup.value = arrResult[0][1];
        fm.AgentName.value = arrResult[0][3];
        fm.AgentManageCom.value = arrResult[0][2];

        if (fm.AgentManageCom.value != fm.ManageCom.value)
        {

            fm.ManageCom.value = fm.AgentManageCom.value;
            fm.ManageComName.value = fm.AgentManageComName.value;
        }
        showContCodeName();
    }
}
//����showContCodeName();                                                          ��ѯ�����������ҵ��Աmultline
function afterQuery3(arrResult)
{
    if (arrResult != null)
    {
        document.all(tField).all('MultiAgentGrid1').value = arrResult[0][0];
        document.all(tField).all('MultiAgentGrid2').value = arrResult[0][3];
        document.all(tField).all('MultiAgentGrid3').value = arrResult[0][2];
        document.all(tField).all('MultiAgentGrid4').value = arrResult[0][10];
        document.all(tField).all('MultiAgentGrid6').value = arrResult[0][1];
        document.all(tField).all('MultiAgentGrid7').value = arrResult[0][6];
    }

}


function queryAgent2()
{

    if (document.all('ManageCom').value == "") {
        alert("����¼����������Ϣ��");
        return;
    }
    if (document.all('AgentCode').value != "" && document.all('AgentCode').value.length == 10) {
        var cAgentCode = fm.AgentCode.value;
        //��������
		
		var sqlid19="CardContInputSql19";
		var mySql19=new SqlClass();
		mySql19.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql19.setSqlId(sqlid19);//ָ��ʹ�õ�Sql��id
		mySql19.addSubPara(cAgentCode);//ָ������Ĳ���
		mySql19.addSubPara(document.all('ManageCom').value);//ָ������Ĳ���
	     var strSql=mySql19.getString();	
		
       // var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode + "' and ManageCom = '" + document.all('ManageCom').value + "'";
        var arrResult = easyExecSql(strSql);
        //alert(arrResult);
        if (arrResult != null) {
            fm.AgentGroup.value = arrResult[0][2];
            alert("��ѯ���:  ҵ��Ա����:[" + arrResult[0][0] + "] ҵ��Ա����Ϊ:[" + arrResult[0][1] + "]");
        }
        else {
            fm.AgentGroup.value = "";
            alert("����Ϊ:[" + document.all('AgentCode').value + "]��ҵ��Ա�����ڣ���ȷ��!");
        }
    }
}
function getdetail()
{
    //alert("fm.AppntNo.value=="+fm.AppntNo.value);
	
			var sqlid20="CardContInputSql20";
		var mySql20=new SqlClass();
		mySql20.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql20.setSqlId(sqlid20);//ָ��ʹ�õ�Sql��id
		mySql20.addSubPara(document.all('AppntBankAccNo').value);//ָ������Ĳ���
		mySql20.addSubPara(document.all('AppntNo').value);//ָ������Ĳ���
	     var strSql=mySql20.getString();	
	
 //   var strSql = "select BankCode,AccName from LCAccount where BankAccNo='" + fm.AppntBankAccNo.value + "' and customerno='" + fm.AppntNo.value + "'";
    var arrResult = easyExecSql(strSql);
    if (arrResult != null) {
        fm.AppntBankCode.value = arrResult[0][0];
        fm.AppntAccName.value = arrResult[0][1];
    }
    else {
        //fm.AppntBankCode.value = '';
        //fm.AppntAccName.value = '';
        return;
    }
}
function getdetailwork()
{

    showOneCodeName('OccupationCode', 'AppntOccupationCode', 'AppntOccupationCodeName');
	
		var sqlid21="CardContInputSql21";
		var mySql21=new SqlClass();
		mySql21.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql21.setSqlId(sqlid21);//ָ��ʹ�õ�Sql��id
		mySql21.addSubPara(document.all('AppntOccupationCode').value);//ָ������Ĳ���
	     var strSql=mySql21.getString();	
	
  //  var strSql = "select OccupationType from LDOccupation where OccupationCode='" + fm.AppntOccupationCode.value + "'";
    var arrResult = easyExecSql(strSql);
    if (arrResult != null)
    {
        fm.AppntOccupationType.value = arrResult[0][0];
        showOneCodeName('OccupationType', 'AppntOccupationType', 'AppntOccupationTypeName');
    }
    else
    {
        fm.AppntOccupationType.value = "";
        fm.AppntOccupationTypeName.value = "";
    }
}

/********************
**
** ��ѯͶ���˵�ַ��Ϣ
**
*********************/
function getdetailaddress() {
	
		var sqlid22="CardContInputSql22";
		var mySql22=new SqlClass();
		mySql22.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql22.setSqlId(sqlid22);//ָ��ʹ�õ�Sql��id
		mySql22.addSubPara(document.all('AppntAddressNo').value);//ָ������Ĳ���
		mySql22.addSubPara(document.all('AppntNo').value);//ָ������Ĳ���
	     var strSQL=mySql22.getString();	
	
   // var strSQL = "select b.* from LCAddress b where b.AddressNo='" + fm.AppntAddressNo.value + "' and b.CustomerNo='" + fm.AppntNo.value + "'";
    arrResult = easyExecSql(strSQL);
    //alert("arrResult[0][1]=="+arrResult[0][1]);
    try {
        document.all('AppntNo').value = arrResult[0][0];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntAddressNo').value = arrResult[0][1];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntPostalAddress').value = arrResult[0][2];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntZipCode').value = arrResult[0][3];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntPhone').value = arrResult[0][4];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntFax').value = arrResult[0][5];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntHomeAddress').value = arrResult[0][6];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntHomeZipCode').value = arrResult[0][7];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntHomePhone').value = arrResult[0][8];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntHomeFax').value = arrResult[0][9];
    } catch(ex) {
    }
    ;
    try {
        document.all('CompanyAddress').value = arrResult[0][10];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntGrpZipCode').value = arrResult[0][11];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntGrpPhone').value = arrResult[0][12];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntGrpFax').value = arrResult[0][13];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntMobile').value = arrResult[0][14];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntMobileChs').value = arrResult[0][15];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntEMail').value = arrResult[0][16];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntBP').value = arrResult[0][17];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntMobile2').value = arrResult[0][18];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntMobileChs2').value = arrResult[0][19];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntEMail2').value = arrResult[0][20];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntBP2').value = arrResult[0][21];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntProvince').value = arrResult[0][28];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntCity').value = arrResult[0][29];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntDistrict').value = arrResult[0][30];
    } catch(ex) {
    }
    ;
}
function getaddresscodedata()
{
    var i = 0;
    var j = 0;
    var m = 0;
    var n = 0;
    var strsql = "";
    var tCodeData = "0|";
    if (fm.AppntAddressNo.value == "")
    {
        fm.AppntPostalAddress.value = "";
        fm.AddressNoName.value = "";
        fm.AppntProvinceName.value = "";
        fm.AppntProvince.value = "";
        fm.AppntCityName.value = "";
        fm.AppntCity.value = "";
        fm.AppntDistrictName.value = "";
        fm.AppntDistrict.value = "";
        fm.AppntZipCode.value = "";
        fm.AppntFax.value = "";
        fm.AppntEMail.value = "";
        fm.AppntDistrict.value = "";
        //	fm.AppntGrpName.value="";
        fm.AppntHomePhone.value = "";
        fm.AppntMobile.value = "";
        fm.AppntGrpPhone.value = "";
    }
	
		var sqlid23="CardContInputSql23";
		var mySql23=new SqlClass();
		mySql23.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql23.setSqlId(sqlid23);//ָ��ʹ�õ�Sql��id
		mySql23.addSubPara(document.all('AppntNo').value);//ָ������Ĳ���
	     strsql=mySql23.getString();	
	
   // strsql = "select AddressNo,PostalAddress from LCAddress where CustomerNo ='" + fm.AppntNo.value + "'";
    //alert("strsql :" + strsql);
    turnPage.strQueryResult = easyQueryVer3(strsql, 1, 0, 1);
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
    document.all("AppntAddressNo").CodeData = tCodeData;
}
function afterCodeSelect(cCodeName, Field)
{
    //alert("afdasdf");
    if (cCodeName == "GetAddressNo") {
		
		var sqlid24="CardContInputSql24";
		var mySql24=new SqlClass();
		mySql24.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql24.setSqlId(sqlid24);//ָ��ʹ�õ�Sql��id
		mySql24.addSubPara(document.all('AppntAddressNo').value);//ָ������Ĳ���
		mySql24.addSubPara(document.all('AppntNo').value);//ָ������Ĳ���
	    var strSQL=mySql24.getString();	
		
        //var strSQL = "select b.* from LCAddress b where b.AddressNo='" + fm.AppntAddressNo.value + "' and b.CustomerNo='" + fm.AppntNo.value + "'";
        arrResult = easyExecSql(strSQL);
        try {
            document.all('AppntNo').value = arrResult[0][0];
        } catch(ex) {
        }
        ;
        try {
            document.all('AppntAddressNo').value = arrResult[0][1];
        } catch(ex) {
        }
        ;
        try {
            document.all('AppntPostalAddress').value = arrResult[0][2];
        } catch(ex) {
        }
        ;
        try {
            document.all('AppntZipCode').value = arrResult[0][3];
        } catch(ex) {
        }
        ;
        try {
            document.all('AppntPhone').value = arrResult[0][4];
        } catch(ex) {
        }
        ;
        try {
            document.all('AppntFax').value = arrResult[0][5];
        } catch(ex) {
        }
        ;
        try {
            document.all('AppntHomeAddress').value = arrResult[0][6];
        } catch(ex) {
        }
        ;
        try {
            document.all('AppntHomeZipCode').value = arrResult[0][7];
        } catch(ex) {
        }
        ;
        try {
            document.all('AppntHomePhone').value = arrResult[0][8];
        } catch(ex) {
        }
        ;
        try {
            document.all('AppntHomeFax').value = arrResult[0][9];
        } catch(ex) {
        }
        ;
        //try{document.all('CompanyAddress').value= arrResult[0][10];}catch(ex){};
        try {
            document.all('AppntGrpZipCode').value = arrResult[0][11];
        } catch(ex) {
        }
        ;
        try {
            document.all('AppntGrpPhone').value = arrResult[0][12];
        } catch(ex) {
        }
        ;
        try {
            document.all('AppntGrpFax').value = arrResult[0][13];
        } catch(ex) {
        }
        ;
        try {
            document.all('AppntMobile').value = arrResult[0][14];
        } catch(ex) {
        }
        ;
        try {
            document.all('AppntMobileChs').value = arrResult[0][15];
        } catch(ex) {
        }
        ;
        try {
            document.all('AppntEMail').value = arrResult[0][16];
        } catch(ex) {
        }
        ;
        try {
            document.all('AppntBP').value = arrResult[0][17];
        } catch(ex) {
        }
        ;
        try {
            document.all('AppntMobile2').value = arrResult[0][18];
        } catch(ex) {
        }
        ;
        try {
            document.all('AppntMobileChs2').value = arrResult[0][19];
        } catch(ex) {
        }
        ;
        try {
            document.all('AppntEMail2').value = arrResult[0][20];
        } catch(ex) {
        }
        ;
        try {
            document.all('AppntBP2').value = arrResult[0][21];
        } catch(ex) {
        }
        ;
        try {
            document.all('AppntProvince').value = arrResult[0][28];
        } catch(ex) {
        }
        ;
        try {
            document.all('AppntCity').value = arrResult[0][29];
        } catch(ex) {
        }
        ;
        try {
            document.all('AppntDistrict').value = arrResult[0][30];
        } catch(ex) {
        }
        ;
        showOneCodeName('province', 'AppntProvince', 'AppntProvinceName');
        showOneCodeName('city', 'AppntCity', 'AppntCityName');
        showOneCodeName('district', 'AppntDistrict', 'AppntDistrictName');
        return;
    }
    if (cCodeName == "paymode") {
        if (document.all('PayMode').value == "4") {
            //divLCAccount1.style.display="";
        }
        else
        {
            //divLCAccount1.style.display="none";
            //alert("accountImg===");
        }
    }

    //�Զ���д��������Ϣ
    if (cCodeName == "customertype") {
        if (Field.value == "A") {
            if (ContType != "2")
            {
                var index = BnfGrid.mulLineCount;
                BnfGrid.setRowColData(index - 1, 2, document.all("AppntName").value);
                BnfGrid.setRowColData(index - 1, 4, document.all("AppntIDType").value);

                BnfGrid.setRowColData(index - 1, 5, document.all("AppntIDNo").value);
                BnfGrid.setRowColData(index - 1, 3, document.all("RelationToAppnt").value);

                BnfGrid.setRowColData(index - 1, 8, document.all("AppntHomeAddress").value);
                //hl
                BnfGrid.setRowColData(index - 1, 9, document.all("AppntNo").value);
                //alert("toubaoren:"+document.all("AppntNo").value)

            }
            else
            {
                alert("Ͷ����Ϊ���壬������Ϊ�����ˣ�")
                var index = BnfGrid.mulLineCount;
                BnfGrid.setRowColData(index - 1, 2, "");
                BnfGrid.setRowColData(index - 1, 3, "");
                BnfGrid.setRowColData(index - 1, 4, "");
                BnfGrid.setRowColData(index - 1, 5, "");
                BnfGrid.setRowColData(index - 1, 8, "");
            }
        }
        else if (Field.value == "I") {
            var index = BnfGrid.mulLineCount;
            BnfGrid.setRowColData(index - 1, 2, document.all("Name").value);
            BnfGrid.setRowColData(index - 1, 4, document.all("IDType").value);
            BnfGrid.setRowColData(index - 1, 5, document.all("IDNo").value);
            BnfGrid.setRowColData(index - 1, 3, "00");
            //BnfGrid.setRowColData(index-1, 8, document.all("AppntHomeAddress").value);
            //hl
            BnfGrid.setRowColData(index - 1, 8, document.all("HomeAddress").value);

            BnfGrid.setRowColData(index - 1, 9, document.all("InsuredNo").value);
            //alert("4544564"+document.all("InsuredNo").value);
        }
        return;
    }
    
    if (cCodeName == "CardRiskCode" || cCodeName=="cardriskcode") 
    {
    	document.all('Mult').value="";
    	document.all('Amnt').value="";
    	document.all('Prem').value="";
    	queryPolOtherGrid();
    }
    if(cCodeName=="OccupationCode")
    {
			if(Field.name=="AppntOccupationCode")
			{
				
		        var sqlid25="CardContInputSql25";
				var mySql25=new SqlClass();
				mySql25.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql25.setSqlId(sqlid25);//ָ��ʹ�õ�Sql��id
				mySql25.addSubPara(document.all('AppntOccupationCode').value);//ָ������Ĳ���
			    var strSQL=mySql25.getString();	
				
//				var strSQL="select Occupationtype,Occupationname from LDOccupation  where OccupationCode='" + fm.AppntOccupationCode.value+"' ";
				var arrResult = easyExecSql(strSQL);
				if (arrResult != null)
				{
					fm.AppntOccupationType.value = arrResult[0][0];
					fm.AppntOccupationTypeName.value =arrResult[0][1];
					showOneCodeName('occupationtype','AppntOccupationType','AppntOccupationTypeName');
				}
			}
			else 	if(Field.name=="OccupationCode") 
			{
				
				 var sqlid26="CardContInputSql26";
				var mySql26=new SqlClass();
				mySql26.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql26.setSqlId(sqlid26);//ָ��ʹ�õ�Sql��id
				mySql26.addSubPara(document.all('OccupationCode').value);//ָ������Ĳ���
			    var strSQL=mySql26.getString();	
				
				//var strSQL="select Occupationtype,Occupationname from LDOccupation  where OccupationCode='" + fm.OccupationCode.value+"' ";
				var arrResult = easyExecSql(strSQL);
				if (arrResult != null)
				{
					fm.OccupationType.value = arrResult[0][0];
					fm.OccupationTypeName.value =arrResult[0][1];
					showOneCodeName('occupationtype','OccupationType','OccupationTypeName');
				}	
			}
			else {}
    	  	
    }
    //alert("aaa");
    afterCodeSelect2(cCodeName, Field);
}

/*********************************************************************
 *  ��ʼ��������MissionID
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */

function initMissionID()
{
    if (tMissionID == "null" || tSubMissionID == "null")
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
    if (fm.CheckPostalAddress.value == "1")
    {
        document.all('AppntPostalAddress').value = document.all('CompanyAddress').value;
        document.all('AppntZipCode').value = document.all('AppntGrpZipCode').value;
        document.all('AppntPhone').value = document.all('AppntGrpPhone').value;
        document.all('AppntFax').value = document.all('AppntGrpFax').value;

    }
    else if (fm.CheckPostalAddress.value == "2")
    {
        document.all('AppntPostalAddress').value = document.all('AppntHomeAddress').value;
        document.all('AppntZipCode').value = document.all('AppntHomeZipCode').value;
        document.all('AppntPhone').value = document.all('AppntHomePhone').value;
        document.all('AppntFax').value = document.all('AppntHomeFax').value;
    }
    else if (fm.CheckPostalAddress.value == "3")
    {
        document.all('AppntPostalAddress').value = "";
        document.all('AppntZipCode').value = "";
        document.all('AppntPhone').value = "";
        document.all('AppntFax').value = "";
    }
}
function AppntChk()
{
    var Sex = fm.AppntSex.value;
    //var i=Sex.indexOf("-");
    //Sex=Sex.substring(0,i);
	
				 var sqlid27="CardContInputSql27";
				var mySql27=new SqlClass();
				mySql27.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql27.setSqlId(sqlid27);//ָ��ʹ�õ�Sql��id
				mySql27.addSubPara(document.all('AppntName').value);//ָ������Ĳ���
				mySql27.addSubPara(Sex);//ָ������Ĳ���
				mySql27.addSubPara(document.all('AppntBirthday').value);//ָ������Ĳ���
				mySql27.addSubPara(document.all('AppntNo').value);//ָ������Ĳ���
				
				mySql27.addSubPara(document.all('AppntIDType').value);//ָ������Ĳ���
				mySql27.addSubPara(document.all('AppntIDNo').value);//ָ������Ĳ���
				mySql27.addSubPara(document.all('AppntNo').value);//ָ������Ĳ���
			    var sqlstr=mySql27.getString();	
	
//    var sqlstr = "select * from ldperson where Name='" + fm.AppntName.value
//            + "' and Sex='" + Sex + "' and Birthday='" + fm.AppntBirthday.value
//            + "' and CustomerNo<>'" + fm.AppntNo.value + "'"
//            + " union select * from ldperson where IDType = '" + fm.AppntIDType.value
//            + "' and IDType is not null and IDNo = '" + fm.AppntIDNo.value
//            + "' and IDNo is not null and CustomerNo<>'" + fm.AppntNo.value + "'" ;
			
    arrResult = easyExecSql(sqlstr, 1, 0);
    if (arrResult == null)
    {
        alert("��û�����Ͷ�������ƵĿͻ�,����У��");
        return false;
    } else {

        window.open("../uw/AppntChkMain.jsp?ProposalNo1=" + fm.ContNo.value + "&Flag=A&LoadFlag=" + LoadFlag, "window1",sFeatures);
    }
}

//�ڳ�ʼ��bodyʱ�Զ�Ч��Ͷ������Ϣ��
//�ж�ϵͳ���Ƿ�����������Ա𡢳���������ͬ��֤������֤��������ͬ�ı�������Ϣ��
function AppntChkNew() {
    //alert("aa");
    var Sex = fm.AppntSex.value;
	
				var sqlid28="CardContInputSql28";
				var mySql28=new SqlClass();
				mySql28.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql28.setSqlId(sqlid28);//ָ��ʹ�õ�Sql��id
				mySql28.addSubPara(document.all('AppntName').value);//ָ������Ĳ���
				mySql28.addSubPara(Sex);//ָ������Ĳ���
				mySql28.addSubPara(document.all('AppntBirthday').value);//ָ������Ĳ���
				mySql28.addSubPara(document.all('AppntNo').value);//ָ������Ĳ���
				
				mySql28.addSubPara(document.all('AppntIDType').value);//ָ������Ĳ���
				mySql28.addSubPara(document.all('AppntIDNo').value);//ָ������Ĳ���
				mySql28.addSubPara(document.all('AppntNo').value);//ָ������Ĳ���
			    var sqlstr=mySql28.getString();	
	
//    var sqlstr = "select * from ldperson where Name='" + fm.AppntName.value
//            + "' and Sex='" + Sex + "' and Birthday='" + fm.AppntBirthday.value
//            + "' and CustomerNo<>'" + fm.AppntNo.value + "'"
//            + " union select * from ldperson where IDType = '" + fm.AppntIDType.value
//            + "' and IDType is not null and IDNo = '" + fm.AppntIDNo.value
//            + "' and IDNo is not null and CustomerNo<>'" + fm.AppntNo.value + "'" ;
    //alert("sqlstr="+sqlstr);
    arrResult = easyExecSql(sqlstr, 1, 0);
    if (arrResult == null)
    {
        //disabled"Ͷ����Ч��"��ť   //�ж��Ƿ�����ظ������ˣ�
        fm.AppntChkButton.disabled = true;
        fm.AppntChkButton2.disabled = true;

    }
    else {
        fm.AppntChkButton.disabled = "";
        fm.AppntChkButton2.disabled = "";
    }
}

function checkidtype()
{
    //alert("haha!");
    if (fm.AppntIDType.value == "" && fm.AppntIDNo.value != "")
    {
        alert("����ѡ��֤�����ͣ�");
        fm.AppntIDNo.value = "";
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
    if (document.all('AppntIDType').value == "0")
    {
        var tBirthday = getBirthdatByIdNo(iIdNo);
        var tSex = getSexByIDNo(iIdNo);
        if (tBirthday == "��������֤��λ������" || tSex == "��������֤��λ������")
        {
            alert("��������֤��λ������");
            theFirstValue = "";
            theSecondValue = "";
            //document.all('AppntIDNo').focus();
            return;
        }
        else
        {
            document.all('AppntBirthday').value = tBirthday;
            document.all('AppntSex').value = tSex;
        }
    }
}
function getCompanyCode()
{
    var strsql = "";
    var tCodeData = "";
	
				var sqlid29="CardContInputSql29";
				var mySql29=new SqlClass();
				mySql29.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql29.setSqlId(sqlid29);//ָ��ʹ�õ�Sql��id
//				mySql29.addSubPara(document.all('AppntName').value);//ָ������Ĳ���

			    strsql=mySql28.getString();	
	
   // strsql = "select CustomerNo,GrpName from LDgrp ";
    document.all("AppntGrpNo").CodeData = tCodeData + easyQueryVer3(strsql, 1, 0, 1);
}
function haveMultiAgent() {
    //alert("aa����"+document.all("multiagentflag").checked);
    if (document.all("multiagentflag").checked) {
        DivMultiAgent.style.display = "";
    }
    else {
        DivMultiAgent.style.display = "none";
    }

}

//Muline ����Ӷ����� parm1
function queryAgentGrid(Field)
{
    //alert("Field=="+Field);
    tField = Field;
    if (document.all('ManageCom').value == "") {
        alert("����¼����������Ϣ��");
        return;
    }
    if (document.all(Field).all('MultiAgentGrid1').value == "") {
        var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom=&queryflag=1", "AgentCommonQueryMain", 'width=' + screen.availWidth + ',height=' + screen.availHeight + ',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    }

    if (document.all(Field).all('MultiAgentGrid1').value != "") {
        var cAgentCode = document.all(Field).all('MultiAgentGrid1').value;
        //��������
//        var strSql = "select AgentCode from LAAgent where AgentCode='" + cAgentCode + "'";
        
		var sqlid82="CardContInputSql82";
		var mySql82=new SqlClass();
		mySql82.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql82.setSqlId(sqlid82);//ָ��ʹ�õ�Sql��id
		mySql82.addSubPara(cAgentCode);//ָ������Ĳ���
		var strSql=mySql82.getString();	
        
        var arrResult = easyExecSql(strSql);
        //alert(arrResult);
        if (arrResult == null) {
            alert("����Ϊ:[" + document.all(Field).all('MultiAgentGrid1').value + "]�Ĵ����˲����ڣ���ȷ��!");
        }
    }
}


//��ʼ����ҵ��Ա��Ϣ
function displayMainAgent() {
    document.all("BranchAttr").value = arrResult[0][3];
    document.all("AgentName").value = arrResult[0][1];
}

function displayMultiAgent() {
    document.all('multiagentflag').checked = "true";
    haveMultiAgent();
}

//�������������У��
function confirmSecondInput1(aObject, aEvent) {
    if (aEvent == "onkeyup") {
        var theKey = window.event.keyCode;
        if (theKey == "13") {
            if (theFirstValue != "") {
                theSecondValue = aObject.value;
                if (theSecondValue == "") {
                    alert("���ٴ�¼�룡");
                    aObject.value = "";
                    aObject.focus();
                    return;
                }
                if (theSecondValue == theFirstValue) {
                    aObject.value = theSecondValue;
                    return;
                }
                else {
                    alert("����¼����������������¼�룡");
                    theFirstValue = "";
                    theSecondValue = "";
                    aObject.value = "";
                    aObject.focus();
                    return;
                }
            }
            else {
                theFirstValue = aObject.value;
                if (theFirstValue == "") {
                    theSecondValue = "";
                    alert("��¼�����ݣ�");
                    return;
                }
                aObject.value = "";
                aObject.focus();
                return;
            }
        }
    }
    else if (aEvent == "onblur") {
        //alert("theFirstValue="+theFirstValue);
        if (theFirstValue != "") {
            theSecondValue = aObject.value;
            if (theSecondValue == "") {
                alert("���ٴ�¼�룡");
                aObject.value = "";
                aObject.focus();
                return;
            }
            if (theSecondValue == theFirstValue) {
                aObject.value = theSecondValue;
                theSecondValue = "";
                theFirstValue = "";
                return;
            }
            else {
                alert("����¼����������������¼�룡");
                theFirstValue = "";
                theSecondValue = "";
                aObject.value = "";
                aObject.focus();
                return;
            }
        }
        else {
            theFirstValue = aObject.value;
            theSecondValue = "";
            if (theFirstValue == "") {
                //alert("aa");
                return;
            }
            aObject.value = "";
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
        if (fm.PolAppntDate.value.indexOf('-') == -1)
        {
            var Year = fm.PolAppntDate.value.substring(0, 4);
            var Month = fm.PolAppntDate.value.substring(4, 6);
            var Day = fm.PolAppntDate.value.substring(6, 8);
            fm.PolAppntDate.value = Year + "-" + Month + "-" + Day;
            if (Year == "0000" || Month == "00" || Day == "00")
            {
                alert("�������Ͷ����������!");
                fm.PolAppntDate.value = "";
                fm.PolAppntDate.focus();
                return;
            }
        }
    }
    else if(fm.PolAppntDate.value.length == 10)
    {
        var Year = fm.PolAppntDate.value.substring(0, 4);
        var Month = fm.PolAppntDate.value.substring(5, 7);
        var Day = fm.PolAppntDate.value.substring(8, 10);
        if (Year == "0000" || Month == "00" || Day == "00")
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
        return;
	}
    //����ϵͳ��¼���Ͷ������У��
    if (checkPolDate(fm.ProposalContNo.value, fm.PolAppntDate.value) == false)
    {
        fm.PolAppntDate.value = "";
        fm.PolAppntDate.focus();
        return;
    }
}

/******************************************************************************
* ���У�飬����ϵͳ��¼���Ͷ������У�飬У�����Ϊ��¼���Ͷ�����ڿ�����¼����ϵͳ��������60����
* ���������ContNo---��ͬ�ţ�ӡˢ�ţ���  PolAppntDate---Ͷ������
*******************************************************************************/
function checkPolDate(ContNo, PolAppntDate)
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
	
					var sqlid30="CardContInputSql30";
				var mySql30=new SqlClass();
				mySql30.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql30.setSqlId(sqlid30);//ָ��ʹ�õ�Sql��id
				mySql30.addSubPara(document.all('AppntName').value);//ָ������Ĳ���

			   var strsql30=mySql30.getString();	
			   
	 var DayIntvArr = easyExecSql(strsql30);
   // var DayIntvArr = easyExecSql("select sysvarvalue from ldsysvar where sysvar='input_poldate_intv'");
    if (DayIntvArr != null) { DayIntv = DayIntvArr[0][0]; }
    //���ݺ�ͬ�Ż�ȡ¼������<��ѯ��ͬ��������ȡ֮������ȡϵͳ��ǰ����>
    var tMakeDate = "";//¼����ϵͳ��������
    if(tContNo!=null && tContNo!="")
    {
		
				var sqlid31="CardContInputSql31";
				var mySql31=new SqlClass();
				mySql31.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql31.setSqlId(sqlid31);//ָ��ʹ�õ�Sql��id
				mySql31.addSubPara(tContNo);//ָ������Ĳ���
			   var makedatesql=mySql31.getString();	
		
		//var makedatesql = "select contno,prtno,makedate from lccont where prtno='" + tContNo + "'";
		var makedatearr = easyExecSql(makedatesql);
		if (makedatearr != null){ tMakeDate = makedatearr[0][2];}
    }
    if(tMakeDate=="")  //¼����ϵͳ��������Ϊ�գ��� Ĭ��ϵͳ����
    {    

//    	var sysdatearr = easyExecSql("select to_date(sysdate) from dual");
    	
		var sqlid83="CardContInputSql83";
		var mySql83=new SqlClass();
		mySql83.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql83.setSqlId(sqlid83);//ָ��ʹ�õ�Sql��id
		var sqlstr=mySql83.getString();
		var sysdatearr = easyExecSql(sqlstr);
    	
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
function checkappntbirthday() {
    if (fm.AppntBirthday.value.length == 8) {
        if (fm.AppntBirthday.value.indexOf('-') == -1) {
            var Year = fm.AppntBirthday.value.substring(0, 4);
            var Month = fm.AppntBirthday.value.substring(4, 6);
            var Day = fm.AppntBirthday.value.substring(6, 8);
            fm.AppntBirthday.value = Year + "-" + Month + "-" + Day;
            if (Year == "0000" || Month == "00" || Day == "00") {
                alert("�������Ͷ���˳�����������!");
                fm.AppntBirthday.value = "";
                return;
            }
        }
    }

    else {
        var Year = fm.AppntBirthday.value.substring(0, 4);
        var Month = fm.AppntBirthday.value.substring(5, 7);
        var Day = fm.AppntBirthday.value.substring(8, 10);
        if (Year == "0000" || Month == "00" || Day == "00") {
            alert("�������Ͷ���˳�����������!");
            fm.AppntBirthday.value = "";
            return;
        }
    }
}


//У�鱻���˳�������
function checkinsuredbirthday() {
    if (fm.Birthday.value.length == 8) {
        if (fm.Birthday.value.indexOf('-') == -1) {
            var Year = fm.Birthday.value.substring(0, 4);
            var Month = fm.Birthday.value.substring(4, 6);
            var Day = fm.Birthday.value.substring(6, 8);
            fm.Birthday.value = Year + "-" + Month + "-" + Day;
            if (Year == "0000" || Month == "00" || Day == "00") {
                alert("������ı����˳�����������!");
                fm.Birthday.value = "";
                return;
            }
        }
    }

    else {
        var Year = fm.Birthday.value.substring(0, 4);
        var Month = fm.Birthday.value.substring(5, 7);
        var Day = fm.Birthday.value.substring(8, 10);
        if (Year == "0000" || Month == "00" || Day == "00") {
            alert("������ı����˳�����������!");
            fm.Birthday.value = "";
            return;
        }
    }
}
//Ͷ��������<Ͷ���˱���������Ӧ��ΪͶ������������֮��;2005-11-18�޸�>
function getAge()
{
    if (fm.AppntBirthday.value == "")
    {
        return;
    }
    if (fm.AppntBirthday.value.indexOf('-') == -1)
    {
        var Year = fm.AppntBirthday.value.substring(0, 4);
        var Month = fm.AppntBirthday.value.substring(4, 6);
        var Day = fm.AppntBirthday.value.substring(6, 8);
        fm.AppntBirthday.value = Year + "-" + Month + "-" + Day;
    }
    if (calAge(fm.AppntBirthday.value) < 0)
    {
        alert("��������ֻ��Ϊ��ǰ������ǰ!");
        return;
    }
    //	fm.AppntAge.value=calAge(fm.AppntBirthday.value);
    fm.AppntAge.value = calPolAppntAge(fm.AppntBirthday.value, fm.PolAppntDate.value);
    return;
}
////������������<����������Ӧ��ΪͶ������������֮��;2005-11-18�޸�>
function getAge2()
{
    if (fm.Birthday.value == "")
    {
        return;
    }
    //alert("fm.Birthday.value.indexOf('-')=="+fm.Birthday.value.indexOf('-'));
    if (fm.Birthday.value.indexOf('-') == -1)
    {
        var Year = fm.Birthday.value.substring(0, 4);
        var Month = fm.Birthday.value.substring(4, 6);
        var Day = fm.Birthday.value.substring(6, 8);
        fm.Birthday.value = Year + "-" + Month + "-" + Day;
    }
    else
    {
        var Year1 = fm.Birthday.value.substring(0, 4);
        var Month1 = fm.Birthday.value.substring(5, 7);
        var Day1 = fm.Birthday.value.substring(8, 10);
        fm.Birthday.value = Year1 + "-" + Month1 + "-" + Day1;
    }
    if (calAge(fm.Birthday.value) < 0)
    {
        alert("��������ֻ��Ϊ��ǰ������ǰ");
        fm.InsuredAppAge.value = "";
        return;
    }
    //	fm.InsuredAppAge.value=calAge(fm.Birthday.value);
    fm.InsuredAppAge.value = calPolAppntAge(fm.Birthday.value, fm.PolAppntDate.value);
    return;
}


//¼��У�鷽��
//���������verifyOrderУ���˳��
//ҵ�������ýӿڣ����ͨ��У�鷵��true�����򷵻�false
function verifyInputNB(verifyOrder)
{
    var formsNum = 0;
    //�����е�FORM��
    var elementsNum = 0;
    //FORM�е�Ԫ����
    var passFlag = true;
    //У��ͨ����־
    //��������FORM
    for (formsNum = 0; formsNum < window.document.forms.length; formsNum++)
    {
        //����FORM�е�����ELEMENT
        for (elementsNum = 0; elementsNum < window.document.forms[formsNum].elements.length; elementsNum++)
        {
            //Ԫ��У������verify��ΪNULL
            if (window.document.forms[formsNum].elements[elementsNum].verify != null && window.document.forms[formsNum].elements[elementsNum].verify != "" && window.document.forms[formsNum].elements[elementsNum].verifyorder == verifyOrder)
            {
                //����У��verifyElement
                if (!verifyElementWrap2(window.document.forms[formsNum].elements[elementsNum].verify, window.document.forms[formsNum].elements[elementsNum].value, window.document.forms[formsNum].name + "." + window.document.forms[formsNum].elements[elementsNum].name))
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


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit2(FlagStr, content)
{
    //alert("here 2!");
    try {
        showInfo.close();
    } catch(e) {
    }
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    if (FlagStr == "Succ" && mWFlag == 0)
    {
        if (confirm("�Ƿ����¼�������ͻ���"))
        {
            if (fm.InsuredSequencename.value == "������������")
            {
                //emptyFormElements();
                param = "122";
                fm.pagename.value = "122";
                fm.SequenceNo.value = "2";
                fm.InsuredSequencename.value = "�ڶ�������������";
                if (scantype == "scan")
                {
                    setFocus();
                }
                noneedhome();
                return false;
            }
            if (fm.InsuredSequencename.value == "�ڶ�������������")
            {
                //emptyFormElements();
                param = "123";
                fm.pagename.value = "123";
                fm.SequenceNo.value = "3";
                fm.InsuredSequencename.value = "����������������";
                if (scantype == "scan")
                {
                    setFocus();
                }
                noneedhome();
                return false;
            }
            if (fm.InsuredSequencename.value == "����������������")
            {
                //emptyFormElements();
                param = "124";
                fm.pagename.value = "124";
                fm.SequenceNo.value = "";
                fm.InsuredSequencename.value = "���ı�����������";
                if (scantype == "scan")
                {
                    setFocus();
                }
                return false;
            }
        }
    }

    if (InsuredGrid.mulLineCount > 0) {
        document.all('spanInsuredGrid0').all('InsuredGridSel').checked = 'true'
    }

}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit3(FlagStr, content)
{
    showInfo.close();
    if (FlagStr == "Fail")
    {
        var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
        //showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
        top.close();
       	top.opener.easyQueryClick();
        //window.location = "./CardContInput.jsp";
    }

}


//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
    if (cDebug == "1")
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

    cContNo = fm.ContNo.value;
    //��������
    if (LoadFlag == "2" || LoadFlag == "4" || LoadFlag == "13")
    {
        if (mSwitch.getVar("ProposalGrpContNo") == "")
        {
            alert("���޼����ͬͶ�����ţ����ȱ���!");
        }
        else
        {
            window.open("./GrpQuestInputMain.jsp?GrpContNo=" + mSwitch.getVar("ProposalGrpContNo") + "&Flag=" + LoadFlag,"",sFeatures);
        }
    }
    else
    {
        if (cContNo == "")
        {
            alert("���޺�ͬͶ�����ţ����ȱ���!");
        }
        else
        {
            window.open("../uw/QuestInputMain.jsp?ContNo=" + cContNo + "&Flag=" + LoadFlag, "window1",sFeatures);
        }
    }
}
//�������ѯ
function QuestQuery()
{
    cContNo = document.all("ContNo").value;
    //��������
    if (LoadFlag == "2" || LoadFlag == "4" || LoadFlag == "13")
    {
        if (mSwitch.getVar("ProposalGrpContNo") == "" || mSwitch.getVar("ProposalGrpContNo") == null)
        {
            alert("����ѡ��һ����������Ͷ����!");
            return;
        }
        else
        {
            window.open("./GrpQuestQueryMain.jsp?GrpContNo=" + mSwitch.getVar("ProposalGrpContNo") + "&Flag=" + LoadFlag,"",sFeatures);
        }
    }
    else
    {
        if (cContNo == "")
        {
            alert("���޺�ͬͶ�����ţ����ȱ���!");
        }
        else
        {
            window.open("../uw/QuestQueryMain.jsp?ContNo=" + cContNo + "&Flag=" + LoadFlag, "window1",sFeatures);
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
    mSwitch = parent.VD.gVSwitch;
    //���ݴ�
    putCont();
    //ע��ú�����ContInput.js��

    if (fm.InsuredNo.value == "" || fm.ContNo.value == "" || InsuredGrid.mulLineCount == "0")
    {
        alert("������ӣ�ѡ�񱻱���");
        return false;
    }
    //mSwitch =parent.VD.gVSwitch;
    delInsuredVar();
    addInsuredVar();

    try {
        mSwitch.addVar('SelPonNo', '', fm.SelPolNo.value);
    } catch(ex) {
    } //ѡ�����ֵ��������ֽ�������ѱ������Ϣ

    if ((LoadFlag == '5' || LoadFlag == '4' || LoadFlag == '6' || LoadFlag == '16') && (mSwitch.getVar("PolNo") == null || mSwitch.getVar("PolNo") == ""))
    {
        alert("����ѡ�񱻱�����������Ϣ��");
        return;
    }
    try {
        mSwitch.addVar('SelPolNo', '', fm.SelPolNo.value);
    } catch(ex) {
    }
    try {
        mSwitch.deleteVar('ContNo');
    } catch(ex) {
    }
    try {
        mSwitch.addVar('ContNo', '', fm.ContNo.value);
    } catch(ex) {
    }
    try {
        mSwitch.updateVar('ContNo', '', fm.ContNo.value);
    } catch(ex) {
    }
    try {
        mSwitch.deleteVar('mainRiskPolNo');
    } catch(ex) {
    }
    try {
        mSwitch.addVar('CValiDate', '', document.all('PolAppntDate').value);
    } catch(ex) {
    }
    //add by yaory
    // alert(ActivityID);
    //  alert(NoType);
    parent.fraInterface.window.location = "./ProposalInput.jsp?LoadFlag=" + LoadFlag + "&ContType=" + ContType + "&scantype=" + scantype + "&MissionID=" + MissionID + "&SubMissionID=" + SubMissionID + "&BQFlag=" + BQFlag + "&EdorType=" + EdorType + "&ActivityID=" + ActivityID + "&NoType=" + NoType + "&hh=1&checktype=" + checktype + "&ProposalContNo=" + fm.ProposalContNo.value + "&ScanFlag=" + ScanFlag;
}

/*********************************************************************
 *  ɾ�������б���������Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function delInsuredVar()
{
    try {
        mSwitch.deleteVar('ContNo');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('InsuredNo');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('PrtNo');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('GrpContNo');
    } catch(ex) {
    }
    ;
    //   try{mSwitch.deleteVar('AppntNo');}catch(ex){};
    //   try{mSwitch.deleteVar('ManageCom');}catch(ex){};
    try {
        mSwitch.deleteVar('ExecuteCom');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('FamilyType');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('RelationToMainInsure');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('RelationToAppnt');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AddressNo');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('SequenceNo');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('Name');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('Sex');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('Birthday');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('IDType');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('IDNo');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('RgtAddress');
    } catch(ex) {
    }
    ;
    //    try{mSwitch.deleteVar('Marriage');}catch(ex){};
    //    try{mSwitch.deleteVar('MarriageDate');}catch(ex){};
    try {
        mSwitch.deleteVar('Health');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('Stature');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('Avoirdupois');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('Degree');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('CreditGrade');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('BankCode');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('BankAccNo');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AccName');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('JoinCompanyDate');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('StartWorkDate');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('Position');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('Salary');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('OccupationType');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('OccupationCode');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('WorkType');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('PluralityType');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('SmokeFlag');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('ContPlanCode');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('Operator');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('MakeDate');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('MakeTime');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('ModifyDate');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('ModifyTime');
    } catch(ex) {
    }
    ;
}

/*********************************************************************
 *  ������������Ϣ���뵽������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function addInsuredVar()
{
    try {
        mSwitch.addVar('ContNo', '', fm.ContNo.value);
    } catch(ex) {
    }
    ;
    //alert("ContNo:"+fm.ContNo.value);
    try {
        mSwitch.addVar('InsuredNo', '', fm.InsuredNo.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('PrtNo', '', fm.PrtNo.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('GrpContNo', '', fm.GrpContNo.value);
    } catch(ex) {
    }
    ;
    //   try{mSwitch.addVar('AppntNo','',fm.AppntNo.value);}catch(ex){};
    //   try{mSwitch.addVar('ManageCom','',fm.ManageCom.value);}catch(ex){};
    try {
        mSwitch.addVar('ExecuteCom', '', fm.ExecuteCom.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('FamilyType', '', fm.FamilyType.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('RelationToMainInsure', '', fm.RelationToMainInsure.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('RelationToAppnt', '', fm.RelationToAppnt.value);
    } catch(ex) {
    }
    ;

    try {
        mSwitch.addVar('AddressNo', '', fm.AppntAddressNo.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('SequenceNo', '', fm.SequenceNo.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('Name', '', fm.Name.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('Sex', '', fm.Sex.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('Birthday', '', fm.Birthday.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('IDType', '', fm.IDType.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('IDNo', '', fm.IDNo.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('RgtAddress', '', fm.RgtAddress.value);
    } catch(ex) {
    }
    ;
    //    try{mSwitch.addVar('Marriage','',fm.Marriage.value);}catch(ex){};
    //    try{mSwitch.addVar('MarriageDate','',fm.MarriageDate.value);}catch(ex){};
    try {
        mSwitch.addVar('Health', '', fm.Health.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('Stature', '', fm.Stature.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('Avoirdupois', '', fm.Avoirdupois.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('Degree', '', fm.Degree.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('CreditGrade', '', fm.CreditGrade.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('BankCode', '', fm.BankCode.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('BankAccNo', '', fm.BankAccNo.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AccName', '', fm.AccName.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('JoinCompanyDate', '', fm.JoinCompanyDate.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('StartWorkDate', '', fm.StartWorkDate.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('Position', '', fm.Position.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('Salary', '', fm.Salary.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('OccupationType', '', fm.OccupationType.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('OccupationCode', '', fm.OccupationCode.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('WorkType', '', fm.WorkType.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('PluralityType', '', fm.PluralityType.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('SmokeFlag', '', fm.SmokeFlag.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('ContPlanCode', '', fm.ContPlanCode.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('Operator', '', fm.Operator.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('MakeDate', '', fm.MakeDate.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('MakeTime', '', fm.MakeTime.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('ModifyDate', '', fm.ModifyDate.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('ModifyTime', '', fm.ModifyTime.value);
    } catch(ex) {
    }
    ;
    //    alert("=fm.PolAppntDate.value="+fm.PolAppntDate.value);
    //    try{mSwitch.addVar('CValidate','',fm.PolAppntDate.value);}catch(ex){};

}

/*********************************************************************
 *  ��ӱ�������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function addRecord()
{
    if (fm.AppntName.value == fm.Name.value && fm.AppntIDType.value == fm.IDType.value && fm.IDNo.value == fm.AppntIDNo.value && fm.Sex.value == fm.AppntSex.value && fm.Birthday.value == fm.AppntBirthday.value)
    {
        alert("����������Ͷ������ͬ��");
        fm.SamePersonFlag.checked = true;
        isSamePerson();

    }
    //�ж��Ƿ��Ѿ���ӹ�Ͷ���ˣ�û�еĻ���������ӱ�����
    if (!checkAppnt()) {
        alert("������Ӻ�ͬ��Ϣ��Ͷ������Ϣ��");
        fm.AppntName.focus();
        return;
    }

    //2005.03.18 chenhq �Դ˽����޸�
    if (LoadFlag == 1) {

        if (fm.SamePersonFlag.checked == true && fm.RelationToAppnt.value != "00")
        {
            alert("��Ͷ���˹�ϵֻ��ѡ���ˣ�");
            fm.RelationToAppnt.value = "00";
            fm.RelationToAppntName.value = "����";
            return;
        }
        var tPrtNo = document.all("PrtNo").value;

				var sqlid32="CardContInputSql32";
				var mySql32=new SqlClass();
				mySql32.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql32.setSqlId(sqlid32);//ָ��ʹ�õ�Sql��id
				mySql32.addSubPara(tPrtNo);//ָ������Ĳ���
			   var sqlstr=mySql32.getString();	


//        var sqlstr = "select SequenceNo from LCInsured where PrtNo='" + tPrtNo + "'";

        arrResult = easyExecSql(sqlstr, 1, 0);

        if (arrResult != null) {
            for (var sequencenocout = 0; sequencenocout < arrResult.length; sequencenocout++)
            {
                if (fm.SequenceNo.value == arrResult[sequencenocout][0]) {
                    alert("�Ѿ����ڸÿͻ��ڲ���");
                    fm.SequenceNo.focus();
                    return false;
                }
            }
        }
    }

    //2005.03.18 chenhq �Դ˽����޸�
    if (LoadFlag == 3) {

        var tPrtNo = document.all("PrtNo").value;

				var sqlid33="CardContInputSql33";
				var mySql33=new SqlClass();
				mySql33.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql33.setSqlId(sqlid33);//ָ��ʹ�õ�Sql��id
				mySql33.addSubPara(tPrtNo);//ָ������Ĳ���
			   var sqlstr=mySql33.getString();	


//        var sqlstr = "select SequenceNo from LCInsured where PrtNo='" + tPrtNo + "'";

        arrResult = easyExecSql(sqlstr, 1, 0);

        if (arrResult != null) {
            for (var sequencenocout = 0; sequencenocout < arrResult.length; sequencenocout++)
            {
                if (fm.SequenceNo.value == arrResult[sequencenocout][0]) {
                    alert("�Ѿ����ڸÿͻ��ڲ���");
                    fm.SequenceNo.focus();
                    return false;
                }
            }
        }
    }


    //2005.03.18 chenhq �Դ˽����޸�
    if (LoadFlag == 5) {

        var tPrtNo = document.all("PrtNo").value;


				var sqlid33="CardContInputSql33";
				var mySql33=new SqlClass();
				mySql33.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql33.setSqlId(sqlid33);//ָ��ʹ�õ�Sql��id
				mySql33.addSubPara(tPrtNo);//ָ������Ĳ���
			   var sqlstr=mySql33.getString();	

//        var sqlstr = "select SequenceNo from LCInsured where PrtNo='" + tPrtNo + "'";

        arrResult = easyExecSql(sqlstr, 1, 0);

        if (arrResult != null) {
            for (var sequencenocout = 0; sequencenocout < arrResult.length; sequencenocout++)
            {
                if (fm.SequenceNo.value == arrResult[sequencenocout][0]) {
                    alert("�Ѿ����ڸÿͻ��ڲ���");
                    fm.SequenceNo.focus();
                    return false;
                }
            }
        }
    }


    //2005.03.18 chenhq �Դ˽����޸�
    if (LoadFlag == 6) {

        var tPrtNo = document.all("PrtNo").value;

				var sqlid34="CardContInputSql34";
				var mySql34=new SqlClass();
				mySql34.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql34.setSqlId(sqlid34);//ָ��ʹ�õ�Sql��id
				mySql34.addSubPara(tPrtNo);//ָ������Ĳ���
			   var sqlstr=mySql34.getString();	


//        var sqlstr = "select SequenceNo from LCInsured where PrtNo='" + tPrtNo + "'";

        arrResult = easyExecSql(sqlstr, 1, 0);

        if (arrResult != null) {
            for (var sequencenocout = 0; sequencenocout < arrResult.length; sequencenocout++)
            {
                if (fm.SequenceNo.value == arrResult[sequencenocout][0]) {
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
    //    if(fm.Marriage.value=="")
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

    if (document.all('PolTypeFlag').value == 0)
    {
        if (verifyInputNB('2') == false) return false;
    }

    if (document.all('IDType').value == "0")
    {
        var strChkIdNo = chkIdNo(trim(document.all('IDNo').value), trim(document.all('Birthday').value), trim(document.all('Sex').value));
        if (strChkIdNo != "")
        {
            alert(strChkIdNo);
            return false;
        }
    }

    if (!checkself()) return false;

    if (!checkrelation()) return false;

    if (ImpartGrid.checkValue2(ImpartGrid.name, ImpartGrid) == false) return false;

    //if(ImpartDetailGrid.checkValue2(ImpartDetailGrid.name,ImpartDetailGrid)== false)return false;
    ImpartGrid.delBlankLine();
    //  ImpartDetailGrid.delBlankLine();
    //alert("fm.AddressNo.value=="+fm.InsuredAddressNo.value);
    if (fm.InsuredNo.value == '' && fm.InsuredAddressNo.value != '')
    {
        alert("�������˿ͻ���Ϊ�գ������е�ַ����");
        return false;
    }

    //hanlin 20050504
    fm.action = "ContInsuredSave.jsp";
    //end hanlin
    document.all('ContType').value = ContType;
    document.all('BQFlag').value = BQFlag;
    document.all('fmAction').value = "INSERT||CONTINSURED";
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
    if (fm.SamePersonFlag.checked == true && fm.RelationToAppnt.value != "00")
    {
        alert("��Ͷ���˹�ϵֻ��ѡ���ˣ�");
        fm.RelationToAppnt.value = "00";
        fm.RelationToAppntName.value = "����";
        return;
    }

    if (document.all('PolTypeFlag').value == 0)
    {
        if (verifyInputNB('2') == false) return false;
    }
    if (!checkself())
        return false;
    if (fm.Name.value == '')
    {
        alert("��ѡ����Ҫ�޸ĵĿͻ���")
        return false;
    }
    //alert("SelNo:"+InsuredGrid.getSelNo());
    if (InsuredGrid.mulLineCount == 0)
    {
        alert("�ñ������˻�û�б��棬�޷������޸ģ�");
        return false;
    }
    if (fm.InsuredNo.value == '' && fm.InsuredAddressNo.value != '')
    {
        alert("�������˿ͻ���Ϊ�գ������е�ַ����");
        return false;
    }
    if (ImpartGrid.checkValue2(ImpartGrid.name, ImpartGrid) == false)
    {
        return false;
    }
    // if(ImpartDetailGrid.checkValue2(ImpartDetailGrid.name,ImpartDetailGrid)== false)
    // {
    //    return false;
    //  }
    ImpartGrid.delBlankLine();
    //ImpartDetailGrid.delBlankLine();

    document.all('ContType').value = ContType;
    document.all('fmAction').value = "UPDATE||CONTINSURED";
    fm.action = "ContInsuredSave.jsp";
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
    if (fm.InsuredNo.value == '')
    {
        alert("��ѡ����Ҫɾ���Ŀͻ���")
        return false;
    }
    if (InsuredGrid.mulLineCount == 0)
    {
        alert("�ñ������˻�û�б��棬�޷������޸ģ�");
        return false;
    }
    if (fm.InsuredNo.value == '' && fm.InsuredAddressNo.value != '')
    {
        alert("�������˿ͻ���Ϊ�գ������е�ַ����");
        return false;
    }
	
				var sqlid35="CardContInputSql35";
				var mySql35=new SqlClass();
				mySql35.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql35.setSqlId(sqlid35);//ָ��ʹ�õ�Sql��id
				mySql35.addSubPara(fm.ContNo.value);//ָ������Ĳ���
			   var sqlstr=mySql35.getString();	
	
//    var sqlstr = "select polno from  lcpol where contNo='" + fm.ContNo.value + "'";
    arrResult = easyExecSql(sqlstr, 1, 0);

    if (arrResult != null)
    {
        alert("����ɾ�������˲�Ʒ��Ϣ");
        return false;
    }
    document.all('ContType').value = ContType;
    document.all('fmAction').value = "DELETE||CONTINSURED";
    fm.action = "ContInsuredSave.jsp";
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
    var backstr = document.all("ContNo").value;

    mSwitch.addVar("PolNo", "", backstr);
    mSwitch.updateVar("PolNo", "", backstr);
    try
    {
        mSwitch.deleteVar('ContNo');
    }
    catch(ex) {
    }
    ;
    if (LoadFlag == "1" || LoadFlag == "3")
    {
        //alert(document.all("PrtNo").value);
        location.href = "../app/ContInput.jsp?LoadFlag=" + LoadFlag + "&prtNo=" + document.all("PrtNo").value;
    }
    if (LoadFlag == "5" || LoadFlag == "25")
    {
        //alert(document.all("PrtNo").value);
        location.href = "../app/ContInput.jsp?LoadFlag=" + LoadFlag + "&prtNo=" + document.all("PrtNo").value;
    }

    if (LoadFlag == "2")
    {
        location.href = "../app/ContGrpInsuredInput.jsp?LoadFlag=" + LoadFlag + "&polNo=" + document.all("GrpContNo").value + "&scantype=" + scantype;
    }
    else if (LoadFlag == "6")
    {
        location.href = "ContInput.jsp?LoadFlag=" + LoadFlag + "&ContNo=" + backstr + "&prtNo=" + document.all("PrtNo").value;
        return;
    }
    else if (LoadFlag == "7")
    {
        location.href = "../bq/GEdorTypeNI.jsp?BQFlag=" + BQFlag;
        return;
    }
    else if (LoadFlag == "4" || LoadFlag == "16" || LoadFlag == "13" || LoadFlag == "14" || LoadFlag == "23")
    {
        if (Auditing == "1")
        {
            top.close();
        }
        else
        {
            mSwitch.deleteVar("PolNo");
            parent.fraInterface.window.location = "../app/ContGrpInsuredInput.jsp?LoadFlag=" + LoadFlag + "&scantype=" + scantype;
        }
    }
    else if (LoadFlag == "99")
    {
        location.href = "ContPolInput.jsp?LoadFlag=" + LoadFlag + "&scantype=" + scantype;
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
function afterCodeSelect2(cCodeName, Field)
{
    try
    {
        //�����������
        if (cCodeName == "PolTypeFlag")
        {
            if (Field.value != '0')
            {
                document.all('InsuredPeoples').readOnly = false;
                document.all('InsuredAppAge').readOnly = false;
            }
            else
            {
                document.all('InsuredPeoples').readOnly = true;
                document.all('InsuredAppAge').readOnly = true;
            }
        }
        if (cCodeName == "ImpartCode")
        {

        }
        if (cCodeName == "SequenceNo")
        {
            if (Field.value == "1" && fm.SamePersonFlag.checked == false)
            {
                //emptyInsured();
                param = "121";
                fm.pagename.value = "121";
                fm.InsuredSequencename.value = "������������";
                fm.RelationToMainInsured.value = "00";
            }
            if (Field.value == "2" && fm.SamePersonFlag.checked == false)
            {
                if (InsuredGrid.mulLineCount == 0)
                {
                    alert("������ӵ�һ������");
                    fm.SequenceNo.value = "1";
                    fm.SequenceNoName.value = "��������";
                    return false;
                }
                //emptyInsured();
                noneedhome();
                param = "122";
                fm.pagename.value = "122";
                fm.InsuredSequencename.value = "�ڶ�������������";
            }
            if (Field.value == "3" && fm.SamePersonFlag.checked == false)
            {
                if (InsuredGrid.mulLineCount == 0)
                {
                    alert("������ӵ�һ������");
                    Field.value = "1";
                    fm.SequenceNo.value = "1";
                    fm.SequenceNoName.value = "��������";

                    return false;
                }
                if (InsuredGrid.mulLineCount == 1)
                {
                    alert("������ӵڶ�������");
                    Field.value = "1";
                    fm.SequenceNo.value = "2";
                    fm.SequenceNoName.value = "�ڶ���������";
                    return false;
                }
                //emptyInsured();
                noneedhome();
                param = "123";
                fm.pagename.value = "123";
                fm.InsuredSequencename.value = "����������������";
            }
            if (scantype == "scan")
            {
                setFocus();
            }
        }
        if (cCodeName == "CheckPostalAddress")
        {
            if (fm.CheckPostalAddress.value == "1")
            {
                document.all('PostalAddress').value = document.all('GrpAddress').value;
                document.all('ZipCode').value = document.all('GrpZipCode').value;
                document.all('InsuredPhone').value = document.all('GrpPhone').value;

            }
            else if (fm.CheckPostalAddress.value == "2")
            {
                document.all('PostalAddress').value = document.all('HomeAddress').value;
                document.all('ZipCode').value = document.all('HomeZipCode').value;
                document.all('InsuredPhone').value = document.all('HomePhone').value;
            }
            else if (fm.CheckPostalAddress.value == "3")
            {
                document.all('PostalAddress').value = "";
                document.all('ZipCode').value = "";
                document.all('InsuredPhone').value = "";
            }
        }

    }
    catch(ex) {
    }

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
    var ContNo = document.all("ContNo").value;
    if (ContNo != null && ContNo != "")
    {
				var sqlid36="CardContInputSql36";
				var mySql36=new SqlClass();
				mySql36.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql36.setSqlId(sqlid36);//ָ��ʹ�õ�Sql��id
				mySql36.addSubPara(ContNo);//ָ������Ĳ���
			   var strSQL=mySql36.getString();	
		
		
//        var strSQL = "select InsuredNo,Name,Sex,Birthday,RelationToMainInsured,SequenceNo from LCInsured where ContNo='" + ContNo + "'";

        //alert("strSQL=="+strSQL);

        turnPage.strQueryResult = easyQueryVer3(strSQL, 1, 0, 1);
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
        document.all('spanInsuredGrid0').all('InsuredGridSel').checked = true;
        getInsuredDetail("spanInsuredGrid0", "");
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
    var tContNo = document.all("ContNo").value;
	
					var sqlid37="CardContInputSql37";
				var mySql37=new SqlClass();
				mySql37.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql37.setSqlId(sqlid37);//ָ��ʹ�õ�Sql��id
				mySql37.addSubPara(ContNo);//ָ������Ĳ���
			   var strSQL37=mySql37.getString();	
	
	arrResult = easyExecSql(strSQL37, 1, 0);
//    arrResult = easyExecSql("select * from LCInsured where ContNo='" + tContNo + "'", 1, 0);
    if (arrResult == null || InsuredGrid.mulLineCount > 1)
    {
        return;
    }
    else
    {
        if (InsuredGrid.mulLineCount = 1) {
            DisplayInsured();
            //�ú�ͬ�µı�Ͷ������Ϣ
            var tCustomerNo = arrResult[0][2];
            // �õ������˿ͻ���
            var tAddressNo = arrResult[0][10];
            // �õ������˵�ַ��
			
				var sqlid38="CardContInputSql38";
				var mySql38=new SqlClass();
				mySql38.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql38.setSqlId(sqlid38);//ָ��ʹ�õ�Sql��id
				mySql38.addSubPara(tCustomerNo);//ָ������Ĳ���
			   var strSQL38=mySql38.getString();	
			   
			arrResult = easyExecSql(strSQL38, 1, 0);
            //arrResult = easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo='" + tCustomerNo + "'", 1, 0);
        }
        if (arrResult == null)
        {
            //alert("δ�õ��û���Ϣ");
            //return;
        }
        else
        {
            //displayAppnt();       //��ʾ��������ϸ����
            emptyUndefined();
            fm.InsuredAddressNo.value = tAddressNo;
            getdetailaddress2();
            //��ʾ�����˵�ַ��ϸ����
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
function getInsuredDetail(parm1, parm2)
{
    //alert("---parm1=="+parm1+"---parm2=="+parm2);

    var InsuredNo = document.all(parm1).all('InsuredGrid1').value;

    var ContNo = fm.ContNo.value;

    //��������ϸ��Ϣ
	
				var sqlid39="CardContInputSql39";
				var mySql39=new SqlClass();
				mySql39.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql39.setSqlId(sqlid39);//ָ��ʹ�õ�Sql��id
				mySql39.addSubPara(InsuredNo);//ָ������Ĳ���
			   var strSQL=mySql39.getString();	
	
//    var strSQL = "select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo='" + InsuredNo + "'";

    arrResult = easyExecSql(strSQL);

    if (arrResult != null)
    {
        //displayAppnt();
        displayInsuredInfo();
    }

				var sqlid40="CardContInputSql40";
				var mySql40=new SqlClass();
				mySql40.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql40.setSqlId(sqlid40);//ָ��ʹ�õ�Sql��id
				mySql40.addSubPara(ContNo);//ָ������Ĳ���
				mySql40.addSubPara(InsuredNo);//ָ������Ĳ���
			    strSQL=mySql40.getString();	


//    strSQL = "select * from LCInsured where ContNo = '" + ContNo + "' and InsuredNo='" + InsuredNo + "'";

    arrResult = easyExecSql(strSQL);

    if (arrResult != null)
    {
        DisplayInsured();
    }

    var tAddressNo = arrResult[0][10];
    // �õ������˵�ַ��
    //alert("arrResult[0][10]=="+arrResult[0][10]);
    fm.InsuredAddressNo.value = tAddressNo;

    //��ʾ�����˵�ַ��Ϣ
    getdetailaddress2();

    getInsuredPolInfo();

    getImpartInfo();

    //getImpartDetailInfo();
    getAge2();
    //��¼�������в���Ҫ���пͻ��ϲ�����ע�͵���hl 20050505
    //����Ǹ���״̬��������ظ��ͻ�У��
    if (LoadFlag == "5") {
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
    try {
        document.all('Nationality').value = arrResult[0][8];
    } catch(ex) {
    }
    ;

}
/*********************************************************************
 *  �Ѳ�ѯ���صĿͻ���ַ������ʾ�������˲���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function DisplayAddress2()
{
    try {
        document.all('PostalAddress').value = arrResult[0][2];
    } catch(ex) {
    }
    ;
    try {
        document.all('ZipCode').value = arrResult[0][3];
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredPhone').value = arrResult[0][4];
    } catch(ex) {
    }
    ;
    try {
        document.all('Mobile').value = arrResult[0][14];
    } catch(ex) {
    }
    ;
    try {
        document.all('EMail').value = arrResult[0][16];
    } catch(ex) {
    }
    ;
    //try{document.all('GrpName').value= arrResult[0][2]; }catch(ex){};
    try {
        document.all('GrpPhone').value = arrResult[0][12];
    } catch(ex) {
    }
    ;
    try {
        document.all('CompanyAddress').value = arrResult[0][10];
    } catch(ex) {
    }
    ;
    try {
        document.all('GrpZipCode').value = arrResult[0][11];
    } catch(ex) {
    }
    ;
}
/*********************************************************************
 *  ��ʾ��������ϸ��Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function DisplayInsured()
{
    try {
        document.all('GrpContNo').value = arrResult[0][0];
    } catch(ex) {
    }
    ;
    try {
        document.all('ContNo').value = arrResult[0][1];
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredNo').value = arrResult[0][2];
    } catch(ex) {
    }
    ;
    try {
        document.all('PrtNo').value = arrResult[0][3];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntNo').value = arrResult[0][4];
    } catch(ex) {
    }
    ;
    try {
        document.all('ManageCom').value = arrResult[0][5];
    } catch(ex) {
    }
    ;
    try {
        document.all('ExecuteCom').value = arrResult[0][6];
    } catch(ex) {
    }
    ;
    try {
        document.all('FamilyID').value = arrResult[0][7];
    } catch(ex) {
    }
    ;
    try {
        document.all('RelationToMainInsured').value = arrResult[0][8];
    } catch(ex) {
    }
    ;
    try {
        document.all('RelationToAppnt').value = arrResult[0][9];
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredAddressNo').value = arrResult[0][10];
    } catch(ex) {
    }
    ;
    try {
        document.all('SequenceNo').value = arrResult[0][11];
    } catch(ex) {
    }
    ;
    try {
        document.all('Name').value = arrResult[0][12];
    } catch(ex) {
    }
    ;
    try {
        document.all('Sex').value = arrResult[0][13];
    } catch(ex) {
    }
    ;
    try {
        document.all('Birthday').value = arrResult[0][14];
    } catch(ex) {
    }
    ;
    try {
        document.all('IDType').value = arrResult[0][15];
    } catch(ex) {
    }
    ;
    try {
        document.all('IDNo').value = arrResult[0][16];
    } catch(ex) {
    }
    ;
    try {
        document.all('NativePlace').value = arrResult[0][17];
    } catch(ex) {
    }
    ;
    try {
        document.all('Nationality').value = arrResult[0][18];
    } catch(ex) {
    }
    ;
    try {
        document.all('RgtAddress').value = arrResult[0][19];
    } catch(ex) {
    }
    ;
    //    try{document.all('Marriage').value=arrResult[0][20];}catch(ex){};
    //    try{document.all('MarriageDate').value=arrResult[0][21];}catch(ex){};
    try {
        document.all('Health').value = arrResult[0][22];
    } catch(ex) {
    }
    ;
    try {
        document.all('Stature').value = arrResult[0][23];
    } catch(ex) {
    }
    ;
    try {
        document.all('Avoirdupois').value = arrResult[0][24];
    } catch(ex) {
    }
    ;
    try {
        document.all('Degree').value = arrResult[0][25];
    } catch(ex) {
    }
    ;
    try {
        document.all('CreditGrade').value = arrResult[0][26];
    } catch(ex) {
    }
    ;
    try {
        document.all('BankCode').value = arrResult[0][27];
    } catch(ex) {
    }
    ;
    try {
        document.all('BankAccNo').value = arrResult[0][28];
    } catch(ex) {
    }
    ;
    try {
        document.all('AccName').value = arrResult[0][29];
    } catch(ex) {
    }
    ;
    try {
        document.all('JoinCompanyDate').value = arrResult[0][30];
    } catch(ex) {
    }
    ;
    try {
        document.all('StartWorkDate').value = arrResult[0][31];
    } catch(ex) {
    }
    ;
    try {
        document.all('Position').value = arrResult[0][32];
    } catch(ex) {
    }
    ;
    try {
        document.all('Salary').value = arrResult[0][33];
    } catch(ex) {
    }
    ;
    try {
        document.all('OccupationType').value = arrResult[0][34];
    } catch(ex) {
    }
    ;
    try {
        document.all('OccupationCode').value = arrResult[0][35];
    } catch(ex) {
    }
    ;
    try {
        document.all('WorkType').value = arrResult[0][36];
    } catch(ex) {
    }
    ;
    try {
        document.all('PluralityType').value = arrResult[0][37];
    } catch(ex) {
    }
    ;
    try {
        document.all('SmokeFlag').value = arrResult[0][38];
    } catch(ex) {
    }
    ;
    try {
        document.all('ContPlanCode').value = arrResult[0][39];
    } catch(ex) {
    }
    ;
    try {
        document.all('Operator').value = arrResult[0][40];
    } catch(ex) {
    }
    ;
    try {
        document.all('MakeDate').value = arrResult[0][42];
    } catch(ex) {
    }
    ;
    try {
        document.all('MakeTime').value = arrResult[0][43];
    } catch(ex) {
    }
    ;
    try {
        document.all('ModifyDate').value = arrResult[0][44];
    } catch(ex) {
    }
    ;
    try {
        document.all('ModifyTime').value = arrResult[0][45];
    } catch(ex) {
    }
    ;
    try {
        document.all('LicenseType').value = arrResult[0][53];
    } catch(ex) {
    }
    ;
}
function displayissameperson()
{
    //alert("here!");
    //alert("document.all( 'AppntName').value="+document.all( "AppntName").value);
��
    try {
        document.all('InsuredNo').value = document.all("AppntNo").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('Name').value = document.all("AppntName").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('Sex').value = document.all("AppntSex").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('Birthday').value = document.all("AppntBirthday").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('IDType').value = document.all("AppntIDType").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('IDNo').value = document.all("AppntIDNo").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('Password').value = document.all("AppntPassword").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('NativePlace').value = document.all("AppntNativePlace").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('Nationality').value = document.all("AppntNationality").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('InsuredAddressNo').value = document.all("AppntAddressNo").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('RgtAddress').value = document.all("AppntRgtAddress").value;
    } catch(ex) {
    }
    ;
    //��try{document.all('Marriage').value= document.all( "AppntMarriage" ).value;          }catch(ex){};
    //��try{document.all('MarriageDate').value= document.all( "AppntMarriageDate" ).value;  }catch(ex){};
��
    try {
        document.all('Health').value = document.all("AppntHealth").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('Stature').value = document.all("AppntStature").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('Avoirdupois').value = document.all("AppntAvoirdupois").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('Degree').value = document.all("AppntDegree").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('CreditGrade').value = document.all("AppntDegreeCreditGrade").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('OthIDType').value = document.all("AppntOthIDType").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('OthIDNo').value = document.all("AppntOthIDNo").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('ICNo').value = document.all("AppntICNo").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('GrpNo').value = document.all("AppntGrpNo").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('JoinCompanyDate').value = document.all("JoinCompanyDate").value;
        if (document.all('JoinCompanyDate').value == "false") {
            document.all('JoinCompanyDate').value = "";
        }
    } catch(ex) {
    }
    ;
��
    try {
        document.all('StartWorkDate').value = document.all("AppntStartWorkDate").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('Position').value = document.all("AppntPosition").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('Position').value = document.all("Position").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('Salary').value = document.all("AppntSalary").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('OccupationType').value = document.all("AppntOccupationType").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('OccupationCode').value = document.all("AppntOccupationCode").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('WorkType').value = document.all("AppntWorkType").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('PluralityType').value = document.all("AppntPluralityType").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('DeathDate').value = document.all("AppntDeathDate").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('SmokeFlag').value = document.all("AppntSmokeFlag").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('BlacklistFlag').value = document.all("AppntBlacklistFlag").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('Proterty').value = document.all("AppntProterty").value;
    } catch(ex) {
    }
    ;
��//try{document.all('Remark').value= document.all("AppntRemark").value;}catch(ex){};
��
    try {
        document.all('State').value = document.all("AppntState").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('Operator').value = document.all("AppntOperator").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('MakeDate').value = document.all("AppntMakeDate").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('MakeTime').value = document.all("AppntMakeTime").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('ModifyDate').value = document.all("AppntModifyDate").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('ModifyTime').value = document.all("AppntModifyTime").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('PostalAddress').value = document.all("AppntPostalAddress").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('ZipCode').value = document.all("AppntZipCode").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('InsuredPhone').value = document.all("AppntPhone").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('Fax').value = document.all("AppntFax").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('Mobile').value = document.all("AppntMobile").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('EMail').value = document.all("AppntEMail").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('GrpName').value = document.all("AppntGrpName").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('GrpPhone').value = document.all("AppntGrpPhone").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('GrpAddress').value = document.all("CompanyAddress").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('GrpZipCode').value = document.all("AppntGrpZipCode").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('GrpFax').value = document.all("AppntGrpFax").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('HomeAddress').value = document.all("AppntHomeAddress").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('HomePhone').value = document.all("AppntHomePhone").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('HomeZipCode').value = document.all("AppntHomeZipCode").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('HomeFax').value = document.all("AppntHomeFax").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('RelationToAppnt').value = "00";
    } catch(ex) {
    }
    ;
��
    try {
        document.all('InsuredProvince').value = document.all("AppntProvince").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('InsuredCity').value = document.all("AppntCity").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('InsuredDistrict').value = document.all("AppntDistrict").value;
    } catch(ex) {
    }
    ;
��
    try {
        document.all('LicenseType').value = document.all("AppntLicenseType").value;
    } catch(ex) {
    }
    ;
    try {
        document.all('IncomeWay').value = document.all("IncomeWay0").value;
    } catch(ex) {
    }
    ;
    try {
        document.all('Income').value = document.all("Income0").value;
    } catch(ex) {
    }
    ;
    showOneCodeName('incomeway', 'IncomeWay', 'IncomeWayName');

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
    var InsuredNo = document.all("InsuredNo").value;
    var ContNo = document.all("ContNo").value;
    //��֪��Ϣ��ʼ��
    if (InsuredNo != null && InsuredNo != "")
    {

				var sqlid41="CardContInputSql41";
				var mySql41=new SqlClass();
				mySql41.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql41.setSqlId(sqlid41);//ָ��ʹ�õ�Sql��id
				mySql41.addSubPara(InsuredNo);//ָ������Ĳ���
				mySql41.addSubPara(ContNo);//ָ������Ĳ���
			     var strSQL0 =mySql41.getString();	

				var sqlid42="CardContInputSql42";
				var mySql42=new SqlClass();
				mySql42.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql42.setSqlId(sqlid42);//ָ��ʹ�õ�Sql��id
				mySql42.addSubPara(InsuredNo);//ָ������Ĳ���
				mySql42.addSubPara(ContNo);//ָ������Ĳ���
			     var strSQL1 =mySql42.getString();	

//        var strSQL0 = "select ImpartParam from LCCustomerImpartparams where CustomerNoType='1' and CustomerNo='" + InsuredNo + "' and ContNo='" + ContNo + "' and impartver='01' and impartcode='001' and ImpartParamNo = '1'";
//
//        var strSQL1 = "select ImpartParam from LCCustomerImpartparams where CustomerNoType='1' and CustomerNo='" + InsuredNo + "' and ContNo='" + ContNo + "' and impartver='01' and impartcode='001' and ImpartParamNo = '2'";

        arrResult = easyExecSql(strSQL0, 1, 0);
        arrResult1 = easyExecSql(strSQL1, 1, 0);


        try {
            document.all('Income').value = arrResult[0][0];
        } catch(ex) {
        }
        ;
        try {
            document.all('IncomeWay').value = arrResult1[0][0];
        } catch(ex) {
        }
        ;

				var sqlid43="CardContInputSql43";
				var mySql43=new SqlClass();
				mySql43.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql43.setSqlId(sqlid43);//ָ��ʹ�õ�Sql��id
				mySql43.addSubPara(InsuredNo);//ָ������Ĳ���
				mySql43.addSubPara(ContNo);//ָ������Ĳ���
			     var strSQL =mySql43.getString();	

//        var strSQL = "select ImpartVer,ImpartCode,ImpartContent,ImpartParamModle from LCCustomerImpart where CustomerNo='" + InsuredNo + "' and ProposalContNo='" + ContNo + "' and CustomerNoType='1' and ((impartver='01' and impartcode<>'001') or (impartver='02'))";
        turnPage.strQueryResult = easyQueryVer3(strSQL, 1, 0, 1);
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
    var InsuredNo = document.all("InsuredNo").value;
    var ContNo = document.all("ContNo").value;
    //��֪��Ϣ��ʼ��
    if (InsuredNo != null && InsuredNo != "")
    {
		
				var sqlid44="CardContInputSql44";
				var mySql44=new SqlClass();
				mySql44.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql44.setSqlId(sqlid44);//ָ��ʹ�õ�Sql��id
				mySql44.addSubPara(InsuredNo);//ָ������Ĳ���
				mySql44.addSubPara(ContNo);//ָ������Ĳ���
			     var strSQL =mySql44.getString();	
		
//        var strSQL = "select ImpartVer,ImpartCode,ImpartDetailContent from LCCustomerImpartDetail where CustomerNo='" + InsuredNo + "' and ContNo='" + ContNo + "' and CustomerNoType='I'";
        turnPage.strQueryResult = easyQueryVer3(strSQL, 1, 0, 1);
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
    var InsuredNo = document.all("InsuredNo").value;
    var ContNo = document.all("ContNo").value;
    //������Ϣ��ʼ��
    if (InsuredNo != null && InsuredNo != "")
    {
        //alert("InsuredNo=="+InsuredNo+"--ContNo=="+ContNo);
		
				var sqlid45="CardContInputSql45";
				var mySql45=new SqlClass();
				mySql45.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql45.setSqlId(sqlid45);//ָ��ʹ�õ�Sql��id
				mySql45.addSubPara(InsuredNo);//ָ������Ĳ���
				mySql45.addSubPara(ContNo);//ָ������Ĳ���
			     var strSQL =mySql45.getString();	
		
//        var strSQL = "select PolNo,RiskCode,Prem,Amnt from LCPol where InsuredNo='" + InsuredNo + "' and ContNo='" + ContNo + "'";
        //alert(strSQL);
        turnPage.strQueryResult = easyQueryVer3(strSQL, 1, 0, 1);
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
function getPolDetail(parm1, parm2)
{
    var PolNo = document.all(parm1).all('PolGrid1').value
    try {
        mSwitch.deleteVar('PolNo')
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('PolNo', '', PolNo);
    } catch(ex) {
    }
    ;
    fm.SelPolNo.value = PolNo;
}
/*********************************************************************
 *  ���ݼ�ͥ�����ͣ����ؽ���ؼ�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function choosetype() {
    if (fm.FamilyType.value == "1")
        divTempFeeInput.style.display = "";
    if (fm.FamilyType.value == "0")
        divTempFeeInput.style.display = "none";
}
/*********************************************************************
 *  У�鱻�����������������˹�ϵ
 *  ����  ��  ��
 *  ����ֵ��  true or false
 *********************************************************************
 */
function checkself()
{
    if (fm.FamilyType.value == "0" && fm.RelationToMainInsured.value == "")
    {
        fm.RelationToMainInsured.value = "00";
        return true;
    }
    else if (fm.FamilyType.value == "0" && fm.RelationToMainInsured.value != "00")
    {
        alert("���˵���'�����������˹�ϵ'ֻ����'����'");
        fm.RelationToMainInsured.value = "00";
        return false;
    }
    else if (fm.FamilyType.value == "1" && fm.RelationToMainInsured.value == "" && InsuredGrid.mulLineCount == 0)
    {
        fm.RelationToMainInsured.value = "00";
        return true;
    }
    else if (fm.FamilyType.value == "1" && fm.RelationToMainInsured.value != "00" && InsuredGrid.mulLineCount == 0)
    {
        alert("��ͥ���е�һλ�������˵�'�����������˹�ϵ'ֻ����'����'");
        fm.RelationToMainInsured.value = "00";
        return false;
    }
    else {
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
    if (LoadFlag == 2 || LoadFlag == 7)
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
        if (document.all('ContNo').value != "" && fm.FamilyType.value == "0")
        {
			
				var sqlid46="CardContInputSql46";
				var mySql46=new SqlClass();
				mySql46.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql46.setSqlId(sqlid46);//ָ��ʹ�õ�Sql��id
				mySql46.addSubPara(fm.ContNo.value);//ָ������Ĳ���
			    var strSQL =mySql46.getString();	
			
//            var strSQL = "select * from LCInsured where contno='" + document.all('ContNo').value + "'";
            turnPage.strQueryResult = easyQueryVer3(strSQL, 1, 0, 1);
            if (turnPage.strQueryResult)
            {
                alert("���������ж౻������");
                return false;
            }
            else
                return true;
        }
        else if (document.all('ContNo').value != "" && fm.FamilyType.value == "1" && InsuredGrid.mulLineCount > 0 && fm.RelationToMainInsured.value == "00")
        {
            alert("��ͥ��ֻ����һ������������");
            return false;
        }
        else if (document.all('ContNo').value != "" && fm.FamilyType.value == "1" && fm.RelationToAppnt.value == "00")
        {
//            var strSql = "select * from LCInsured where contno='" + document.all('ContNo').value + "' and RelationToAppnt='00' ";
            
			var sqlid84="CardContInputSql84";
			var mySql84=new SqlClass();
			mySql84.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
			mySql84.setSqlId(sqlid84);//ָ��ʹ�õ�Sql��id
			mySql84.addSubPara(document.all('ContNo').value);//ָ������Ĳ���
		    var strSql =mySql84.getString();
            
            turnPage.strQueryResult = easyQueryVer3(strSql, 1, 0, 1);
            if (turnPage.strQueryResult)
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
    if (fm.SamePersonFlag.checked == true && fm.RelationToAppnt.value != "00")
    {
        DivLCInsured.style.display = "none";
        divLCInsuredPerson.style.display = "none";
        divSalary.style.display = "none";
        fm.SamePersonFlag.checked = true;
        fm.RelationToAppnt.value = "00"
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
        try {
            document.all('Name').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('Sex').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('Birthday').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('IDType').value = "0";
        } catch(ex) {
        }
        ;
        try {
            document.all('IDNo').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('Password').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('NativePlace').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('Nationality').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('RgtAddress').value = "";
        } catch(ex) {
        }
        ;
        //      try{document.all('Marriage').value= "";}catch(ex){};
        //      try{document.all('MarriageDate').value= "";}catch(ex){};
        try {
            document.all('Health').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('Stature').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('Avoirdupois').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('Degree').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('CreditGrade').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('OthIDType').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('OthIDNo').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('ICNo').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('GrpNo').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('JoinCompanyDate').value = "";
        } catch(ex) {
        }
        try {
            document.all('StartWorkDate').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('Position').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('Salary').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('OccupationType').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('OccupationCode').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('WorkType').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('PluralityType').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('DeathDate').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('SmokeFlag').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('BlacklistFlag').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('Proterty').value = "";
        } catch(ex) {
        }
        ;
        //try{document.all('Remark').value= "";}catch(ex){};
        try {
            document.all('State').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('Operator').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('MakeDate').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('MakeTime').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('ModifyDate').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('ModifyTime').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('PostalAddress').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('PostalAddress').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('ZipCode').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('InsuredPhone').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('Mobile').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('EMail').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('GrpName').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('GrpPhone').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('GrpAddress').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('GrpZipCode').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('RelationToAppnt').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('Fax').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('HomePhone').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('IncomeWay').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('Income').value = "";
        } catch(ex) {
        }
        ;

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
        
				var sqlid47="CardContInputSql47";
				var mySql47=new SqlClass();
				mySql47.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql47.setSqlId(sqlid47);//ָ��ʹ�õ�Sql��id
				mySql47.addSubPara(fm.ContNo.value);//ָ������Ĳ���
			    var strSQL47 =mySql47.getString();	
		
		arrResult = easyExecSql(strSQL47, 1, 0);
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
/*********************************************************************
 *  Ͷ���˲�ѯ��Ť�¼�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
//function showAppnt1()
function showInsured1()
{
    if (mOperate == 0)
    {
        //mOperate = 2;
        mOperate = 3;
        //��������Ϣ��ѯ��mOperate = 3; hl20050503
        showInfo = window.open("../sys/LDPersonQueryNewMain.jsp?queryFlag=queryInsured","",sFeatures)
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
    try {
        document.all('InsuredNo').value = arrResult[0][0];
    } catch(ex) {
    }
    ;
    try {
        document.all('Name').value = arrResult[0][1];
    } catch(ex) {
    }
    ;
    try {
        document.all('Sex').value = arrResult[0][2];
    } catch(ex) {
    }
    ;
    try {
        document.all('Birthday').value = arrResult[0][3];
    } catch(ex) {
    }
    ;
    try {
        document.all('IDType').value = arrResult[0][4];
    } catch(ex) {
    }
    ;
    try {
        document.all('IDNo').value = arrResult[0][5];
    } catch(ex) {
    }
    ;
    try {
        document.all('Password').value = arrResult[0][6];
    } catch(ex) {
    }
    ;
    try {
        document.all('NativePlace').value = arrResult[0][7];
    } catch(ex) {
    }
    ;
    try {
        document.all('Nationality').value = arrResult[0][8];
    } catch(ex) {
    }
    ;
    try {
        document.all('RgtAddress').value = arrResult[0][9];
    } catch(ex) {
    }
    ;
    //    try{document.all('Marriage').value= arrResult[0][10];}catch(ex){};
    //    try{document.all('MarriageDate').value= arrResult[0][11];}catch(ex){};
    try {
        document.all('Health').value = arrResult[0][12];
    } catch(ex) {
    }
    ;
    try {
        document.all('Stature').value = arrResult[0][13];
    } catch(ex) {
    }
    ;
    try {
        document.all('Avoirdupois').value = arrResult[0][14];
    } catch(ex) {
    }
    ;
    try {
        document.all('Degree').value = arrResult[0][15];
    } catch(ex) {
    }
    ;
    try {
        document.all('CreditGrade').value = arrResult[0][16];
    } catch(ex) {
    }
    ;
    try {
        document.all('OthIDType').value = arrResult[0][17];
    } catch(ex) {
    }
    ;
    try {
        document.all('OthIDNo').value = arrResult[0][18];
    } catch(ex) {
    }
    ;
    try {
        document.all('ICNo').value = arrResult[0][19];
    } catch(ex) {
    }
    ;
    try {
        document.all('GrpNo').value = arrResult[0][20];
    } catch(ex) {
    }
    ;
    try {
        document.all('JoinCompanyDate').value = arrResult[0][21];
    } catch(ex) {
    }
    ;
    try {
        document.all('StartWorkDate').value = arrResult[0][22];
    } catch(ex) {
    }
    ;
    try {
        document.all('Position').value = arrResult[0][23];
    } catch(ex) {
    }
    ;
    try {
        document.all('Salary').value = arrResult[0][24];
    } catch(ex) {
    }
    ;
    try {
        document.all('OccupationType').value = arrResult[0][25];
    } catch(ex) {
    }
    ;
    try {
        document.all('OccupationCode').value = arrResult[0][26];
    } catch(ex) {
    }
    ;
    try {
        document.all('WorkType').value = arrResult[0][27];
    } catch(ex) {
    }
    ;
    try {
        document.all('PluralityType').value = arrResult[0][28];
    } catch(ex) {
    }
    ;
    try {
        document.all('DeathDate').value = arrResult[0][29];
    } catch(ex) {
    }
    ;
    try {
        document.all('SmokeFlag').value = arrResult[0][30];
    } catch(ex) {
    }
    ;
    try {
        document.all('BlacklistFlag').value = arrResult[0][31];
    } catch(ex) {
    }
    ;
    try {
        document.all('Proterty').value = arrResult[0][32];
    } catch(ex) {
    }
    ;
    //try{document.all('Remark').value= arrResult[0][33];}catch(ex){};
    try {
        document.all('State').value = arrResult[0][34];
    } catch(ex) {
    }
    ;
    try {
        document.all('VIPValue1').value = arrResult[0][35];
    } catch(ex) {
    }
    ;
    try {
        document.all('Operator').value = arrResult[0][36];
    } catch(ex) {
    }
    ;
    try {
        document.all('MakeDate').value = arrResult[0][37];
    } catch(ex) {
    }
    ;
    try {
        document.all('MakeTime').value = arrResult[0][38];
    } catch(ex) {
    }
    ;
    try {
        document.all('ModifyDate').value = arrResult[0][39];
    } catch(ex) {
    }
    ;
    try {
        document.all('ModifyTime').value = arrResult[0][40];
    } catch(ex) {
    }
    ;
    try {
        document.all('LicenseType').value = arrResult[0][43];
    } catch(ex) {
    }
    ;
    try {
        document.all('GrpName').value = arrResult[0][41];
    } catch(ex) {
    }
    ;


    //��ַ��ʾ���ֵı䶯
    try {
        document.all('InsuredAddressNo').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('PostalAddress').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('ZipCode').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredPhone').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('Mobile').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('EMail').value = "";
    } catch(ex) {
    }
    ;
    //try{document.all('GrpName').value= arrResult[0][46];}catch(ex){};
    try {
        document.all('GrpPhone').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('GrpAddress').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('GrpZipCode').value = "";
    } catch(ex) {
    }
    ;
}
/*********************************************************************
 *  ��ѯ���غ󴥷�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterQuery21(arrQueryResult)
{
    //alert("1111here:" + arrQueryResult + "\n" + mOperate);

    if (arrQueryResult != null)
    {
        arrResult = arrQueryResult;

        if (mOperate == 1)
        {        // ��ѯͶ����
            document.all('ContNo').value = arrQueryResult[0][0];

				var sqlid48="CardContInputSql48";
				var mySql48=new SqlClass();
				mySql48.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql48.setSqlId(sqlid48);//ָ��ʹ�õ�Sql��id
				mySql48.addSubPara(arrQueryResult[0][0] );//ָ������Ĳ���
			    var strSQL48 =mySql48.getString();	

              arrResult = easyExecSql(strSQL48, 1, 0);
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

        if (mOperate == 3)
        {        // ��������Ϣ
            //arrResult = easyExecSql("select a.*,b.AddressNo,b.PostalAddress,b.ZipCode,b.HomePhone,b.Mobile,b.EMail,a.GrpNo,b.CompanyPhone,b.CompanyAddress,b.CompanyZipCode from LDPerson a,LCAddress b where 1=1 and a.CustomerNo=b.CustomerNo and a.CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0);
           
		   		var sqlid49="CardContInputSql49";
				var mySql49=new SqlClass();
				mySql49.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql49.setSqlId(sqlid49);//ָ��ʹ�õ�Sql��id
				mySql49.addSubPara(arrQueryResult[0][0] );//ָ������Ĳ���
			    var strSQL49 =mySql49.getString();	
		   
		    arrResult = easyExecSql(strSQL49, 1, 0);
		   // arrResult = easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0);
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

    mOperate = 0;
    // �ָ���̬
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
    showOneCodeName('OccupationCode', 'OccupationCode', 'OccupationCodeName');
	
			   	var sqlid50="CardContInputSql50";
				var mySql50=new SqlClass();
				mySql50.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql50.setSqlId(sqlid50);//ָ��ʹ�õ�Sql��id
				mySql50.addSubPara( fm.OccupationCode.value );//ָ������Ĳ���
			    var strSql =mySql50.getString();	
	
//    var strSql = "select OccupationType from LDOccupation where OccupationCode='" + fm.OccupationCode.value + "'";
    var arrResult = easyExecSql(strSql);
    if (arrResult != null)
    {
        fm.OccupationType.value = arrResult[0][0];
        showOneCodeName('OccupationType', 'OccupationType', 'OccupationTypeName');
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
    alert("LoadFlag==" + LoadFlag);


    if (wFlag == 1) //¼�����ȷ��
    {
		
				var sqlid51="CardContInputSql51";
				var mySql51=new SqlClass();
				mySql51.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql51.setSqlId(sqlid51);//ָ��ʹ�õ�Sql��id
				mySql51.addSubPara( fm.ContNo.value );//ָ������Ĳ���
			    var tStr =mySql51.getString();	
		
//        var tStr = "	select * from lwmission where 1=1 and lwmission.activityid in ('0000001001','0000001002','0000001220','0000001230') and lwmission.missionprop1 = '" + fm.ContNo.value + "'";
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
    else if (wFlag == 2)//�������ȷ��
    {
        if (document.all('ProposalContNo').value == "")
        {
            alert("δ��ѯ����ͬ��Ϣ,������������ [�������] ȷ�ϣ�");
            return;
        }
        fm.WorkFlowFlag.value = "0000001001";
        fm.MissionID.value = tMissionID;
        fm.SubMissionID.value = tSubMissionID;
    }
    else if (wFlag == 3)
    {
        if (document.all('ProposalContNo').value == "")
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

    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action = "./InputConfirm.jsp";
    fm.submit();
    //�ύ
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
	
				var sqlid52="CardContInputSql52";
				var mySql52=new SqlClass();
				mySql52.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql52.setSqlId(sqlid52);//ָ��ʹ�õ�Sql��id
				mySql52.addSubPara( fm.InsuredAddressNo.value );//ָ������Ĳ���
				mySql52.addSubPara( fm.InsuredNo.value );//ָ������Ĳ���
			    var strSQL =mySql52.getString();	
	
//    var strSQL = "select b.AddressNo,b.PostalAddress,b.ZipCode,b.Phone,b.Mobile,b.EMail,b.CompanyPhone,b.CompanyAddress,b.CompanyZipCode,b.fax,b.HomePhone,b.grpName,b.province,b.city,b.County from LCAddress b where b.AddressNo='" + fm.InsuredAddressNo.value + "' and b.CustomerNo='" + fm.InsuredNo.value + "'";
    arrResult = easyExecSql(strSQL);
    try {
        document.all('InsuredAddressNo').value = arrResult[0][0];
    } catch(ex) {
    }
    ;
    try {
        document.all('PostalAddress').value = arrResult[0][1];
    } catch(ex) {
    }
    ;
    try {
        document.all('ZIPCODE').value = arrResult[0][2];
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredPhone').value = arrResult[0][3];
    } catch(ex) {
    }
    ;
    try {
        document.all('Mobile').value = arrResult[0][4];
    } catch(ex) {
    }
    ;
    try {
        document.all('EMail').value = arrResult[0][5];
    } catch(ex) {
    }
    ;
    //try{document.all('GrpName').value= arrResult[0][6];}catch(ex){};
    try {
        document.all('GrpPhone').value = arrResult[0][6];
    } catch(ex) {
    }
    ;
    try {
        document.all('GrpAddress').value = arrResult[0][7];
    } catch(ex) {
    }
    ;
    try {
        document.all('GrpZipCode').value = arrResult[0][8];
    } catch(ex) {
    }
    ;
    try {
        document.all('Fax').value = arrResult[0][9];
    } catch(ex) {
    }
    ;
    try {
        document.all('HomePhone').value = arrResult[0][10];
    } catch(ex) {
    }
    ;
    //alert("arrResult[0][11]=="+arrResult[0][11]);
    // try{document.all('GrpName').value= arrResult[0][11];}catch(ex){};
    //alert("fm.GrpName="+fm.GrpName.value);
    try {
        document.all('InsuredProvince').value = arrResult[0][12];
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredCity').value = arrResult[0][13];
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredDistrict').value = arrResult[0][14];
    } catch(ex) {
    }
    ;
    showOneCodeName('Province', 'InsuredProvince', 'InsuredProvinceName');
    showOneCodeName('City', 'InsuredCity', 'InsuredCityName');
    showOneCodeName('District', 'InsuredDistrict', 'InsuredDistrictName');
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
	
					var sqlid53="CardContInputSql53";
				var mySql53=new SqlClass();
				mySql53.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql53.setSqlId(sqlid53);//ָ��ʹ�õ�Sql��id
				mySql53.addSubPara( tProposalGrpContNo );//ָ������Ĳ���
			   strsql =mySql53.getString();	
	
//    strsql = "select ContPlanCode,ContPlanName from LCContPlan where ContPlanCode!='00' and ProposalGrpContNo='" + tProposalGrpContNo + "'";
    
    
    
    
    //alert("strsql :" + strsql);
    turnPage.strQueryResult = easyQueryVer3(strsql, 1, 0, 1);
    if (turnPage.strQueryResult != "")
    {
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        m = turnPage.arrDataCacheSet.length;
        for (i = 0; i < m; i++)
        {
            j = i + 1;
            tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
        }
        divContPlan.style.display = "";
    }
    else
    {
        //alert("���ռƻ�û�鵽");
        divContPlan.style.display = "none";
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
	
				var sqlid54="CardContInputSql54";
				var mySql54=new SqlClass();
				mySql54.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql54.setSqlId(sqlid54);//ָ��ʹ�õ�Sql��id
				mySql54.addSubPara( tProposalGrpContNo );//ָ������Ĳ���
			   strsql =mySql54.getString();	
	
//    strsql = "select ExecuteCom,Name from LCGeneral a,LDCom b where a.GrpContNo='" + tProposalGrpContNo + "' and a.ExecuteCom=b.ComCode";
    //alert("strsql :" + strsql);
    turnPage.strQueryResult = easyQueryVer3(strsql, 1, 0, 1);
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
        divExecuteCom.style.display = "";
    }
    else
    {
        divExecuteCom.style.display = "none";
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
    try {
        document.all('AppntVIPValue').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntVIPFlagname').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntIDNo').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntSex').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntSexName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntBirthday').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntAge').value = "";
    } catch(ex) {
    }
    ;
    //        try{document.all('AppntMarriage').value= ""; }catch(ex){};
    //        try{document.all('AppntMarriageName').value= ""; }catch(ex){};
    try {
        document.all('AppntNativePlace').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntNativePlaceName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntOccupationCode').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntOccupationCodeName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntLicenseType').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntLicenseTypeName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntAddressNo').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AddressNoName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntProvince').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntProvinceName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntCity').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntCityName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntDistrict').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntDistrictName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntPostalAddress').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntZipCode').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntMobile').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntGrpPhone').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntFax').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntHomePhone').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntGrpName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntEMail').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('PayMode').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('FirstPayModeName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntBankCode').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('FirstBankCodeName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntAccName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntBankAccNo').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('SecPayMode').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('PayModeName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('SecAppntBankCode').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntBankCodeName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('SecAppntAccName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('SecAppntBankAccNo').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('Income0').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('IncomeWay0').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('IncomeWayName0').value = "";
    } catch(ex) {
    }
    ;
    ImpartGrid.clearData();
    ImpartGrid.addOne();

}
function emptyInsured()
{

    try {
        document.all('InsuredNo').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('VIPValue1').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntVIPFlagname1').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('SequenceNo').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('ExecuteCom').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('FamilyID').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('RelationToMainInsured').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('RelationToAppnt').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('RelationToAppntName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredAddressNo').value = "";
    } catch(ex) {
    }
    ;
    //try{document.all('SequenceNo').value= ""; }catch(ex){};
    try {
        document.all('Name').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('Sex').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('SexName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('RelationToMainInsuredName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('SequenceNoName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('Birthday').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredAppAge').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('IDType').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('IDNo').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('IncomeWayName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredProvince').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredProvinceName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredCity').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredCityName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredDistrict').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredDistrictName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('Income').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('LicenseType').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('LicenseTypeName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('IDTypeName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredAddressNoName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('IncomeWay').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('NativePlace').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('NativePlaceName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('Nationality').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('RgtAddress').value = "";
    } catch(ex) {
    }
    ;
    //try{document.all('Marriage').value= ""; }catch(ex){};
    //try{document.all('MarriageName').value= ""; }catch(ex){};
    //try{document.all('MarriageDate').value= ""; }catch(ex){};
    try {
        document.all('Health').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('Stature').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('Avoirdupois').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('Degree').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('CreditGrade').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('BankCode').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('BankAccNo').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AccName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('JoinCompanyDate').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('StartWorkDate').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('Position').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('Salary').value = "";
    } catch(ex) {
    }
    ;
    //try{document.all('OccupationType').value= ""; }catch(ex){};
    try {
        document.all('OccupationCodeName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('OccupationCode').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('WorkType').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('PluralityType').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('SmokeFlag').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('ContPlanCode').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('GrpName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('HomeAddress').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('HomeZipCode').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('HomePhone').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('HomeFax').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('GrpFax').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('Fax').value = "";
    } catch(ex) {
    }
    ;
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
    try {
        document.all('PostalAddress').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('ZipCode').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredPhone').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('Mobile').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('EMail').value = "";
    } catch(ex) {
    }
    ;
    //try{document.all('GrpName').value= arrResult[0][2]; }catch(ex){};
    try {
        document.all('GrpPhone').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('GrpAddress').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('GrpZipCode').value = "";
    } catch(ex) {
    }
    ;
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
    if (document.all('IDType').value == "0")
    {
        var tBirthday = getBirthdatByIdNo(iIdNo);
        var tSex = getSexByIDNo(iIdNo);
        if (tBirthday == "��������֤��λ������" || tSex == "��������֤��λ������")
        {
            alert("��������֤��λ������");
            theFirstValue = "";
            theSecondValue = "";
            //document.all('IDNo').focus();
            return;

        }
        else
        {
            document.all('Birthday').value = tBirthday;
            document.all('Sex').value = tSex;
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
    if (wFlag == 1) //¼�����ȷ��
    {
//        var tStr = "	select * from lwmission where 1=1 "
//                + " and lwmission.processid = '0000000004'"
//                + " and lwmission.activityid = '0000002001'"
//                + " and lwmission.missionprop1 = '" + fm.ProposalGrpContNo.value + "'";
        
		var sqlid85="CardContInputSql85";
		var mySql85=new SqlClass();
		mySql85.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql85.setSqlId(sqlid85);//ָ��ʹ�õ�Sql��id
		mySql85.addSubPara(fm.ProposalGrpContNo.value);//ָ������Ĳ���
		var tStr =mySql85.getString();	
        
        turnPage.strQueryResult = easyQueryVer3(tStr, 1, 0, 1);
        if (turnPage.strQueryResult)
        {
            alert("���ŵ���ͬ�Ѿ��������棡");
            return;
        }
        if (document.all('ProposalGrpContNo').value == "")
        {
            alert("�ŵ���ͬ��Ϣδ����,������������ [¼�����] ȷ�ϣ�");
            return;
        }
        fm.WorkFlowFlag.value = "6999999999";
        //¼�����
    }
    else if (wFlag == 2)//�������ȷ��
    {
        if (document.all('ProposalGrpContNo').value == "")
        {
            alert("δ��ѯ���ŵ���ͬ��Ϣ,������������ [�������] ȷ�ϣ�");
            return;
        }
        fm.WorkFlowFlag.value = "0000002002";
        //�������
        fm.MissionID.value = MissionID;
        fm.SubMissionID.value = SubMissionID;
    }
    else if (wFlag == 3)
    {
        if (document.all('ProposalGrpContNo').value == "")
        {
            alert("δ��ѯ����ͬ��Ϣ,������������ [�����޸����] ȷ�ϣ�");
            return;
        }
        fm.WorkFlowFlag.value = "0000001002";
        //�����޸����
        fm.MissionID.value = MissionID;
        fm.SubMissionID.value = SubMissionID;
    }
    else if (wFlag == 4)
    {
        if (document.all('ProposalGrpContNo').value == "")
        {
            alert("δ��ѯ����ͬ��Ϣ,������������ [�޸����] ȷ�ϣ�");
            return;
        }
        fm.WorkFlowFlag.value = "0000001021";
        //�����޸�
        fm.MissionID.value = MissionID;
        fm.SubMissionID.value = SubMissionID;
    }
    else
        return;

    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action = "./GrpInputConfirm.jsp";
    fm.submit();
    //�ύ
}
function getaddresscodedata2()
{
    var i = 0;
    var j = 0;
    var m = 0;
    var n = 0;
    var strsql = "";
    var tCodeData = "0|";
    if (fm.InsuredAddressNo.value == "")
    {
        fm.PostalAddress.value = "";
        fm.InsuredAddressNoName.value = "";
        fm.InsuredProvinceName.value = "";
        fm.InsuredProvince.value = "";
        fm.InsuredCityName.value = "";
        fm.InsuredCity.value = "";
        fm.InsuredDistrictName.value = "";
        fm.InsuredDistrict.value = "";
        fm.PostalAddress.value = "";
        fm.ZIPCODE.value = "";
        fm.Mobile.value = "";
        fm.GrpPhone.value = "";
        fm.Fax.value = "";
        fm.HomePhone.value = "";
        fm.EMail.value = "";
    }
	
					var sqlid55="CardContInputSql55";
				var mySql55=new SqlClass();
				mySql55.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql55.setSqlId(sqlid55);//ָ��ʹ�õ�Sql��id
				mySql55.addSubPara( fm.InsuredNo.value );//ָ������Ĳ���
			   strsql =mySql55.getString();	
	
//    strsql = "select AddressNo,PostalAddress from LCAddress where CustomerNo ='" + fm.InsuredNo.value + "'";
    //alert("strsql :" + strsql);
    turnPage.strQueryResult = easyQueryVer3(strsql, 1, 0, 1);
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
    document.all("InsuredAddressNo").CodeData = tCodeData;
}

function getImpartCode(parm1, parm2) {
    //alert("hehe:"+document.all(parm1).all('ImpartGrid1').value);
    var impartVer = document.all(parm1).all('ImpartGrid1').value;
    window.open("../app/ImpartCodeSel.jsp?ImpartVer=" + impartVer,"",sFeatures);
}
function checkidtype2()
{
    //alert("sdasf");
    if (fm.IDType.value == "")
    {
        alert("����ѡ��֤�����ͣ�");
        //	fm.IDNo.value="";
    }
}
function getallinfo()
{
    if (fm.Name.value != "" && fm.IDType.value != "" && fm.IDNo.value != "")
    {
		
				var sqlid56="CardContInputSql56";
				var mySql56=new SqlClass();
				mySql56.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql56.setSqlId(sqlid56);//ָ��ʹ�õ�Sql��id
				mySql56.addSubPara( fm.Name.value );//ָ������Ĳ���
				mySql56.addSubPara( fm.IDType.value );//ָ������Ĳ���
				mySql56.addSubPara( fm.IDNo.value );//ָ������Ĳ���
			    strSQL =mySql56.getString();	
		
//        strSQL = "select a.CustomerNo, a.Name, a.Sex, a.Birthday, a.IDType, a.IDNo from LDPerson a where 1=1 "
//                + "  and Name='" + fm.Name.value
//                + "' and IDType='" + fm.IDType.value
//                + "' and IDNo='" + fm.IDNo.value
//                + "' order by a.CustomerNo";
        turnPage.strQueryResult = easyQueryVer3(strSQL, 1, 0, 1);
        if (turnPage.strQueryResult != "")
        {
            mOperate = 2;
            window.open("../sys/LDPersonQueryAll.html?Name=" + fm.Name.value + "&IDType=" + fm.IDType.value + "&IDNo=" + fm.IDNo.value, "newwindow", "height=10,width=1090,top=180,left=180, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no,status=no");
        }
        else
            return;
    }
}
function DelRiskInfo()
{
    if (fm.InsuredNo.value == "")
    {
        alert("����ѡ�񱻱���");
        return false;
    }
    var tSel = PolGrid.getSelNo();
    if (tSel == 0 || tSel == null)
    {
        alert("�ÿͻ�û�����ֻ���������ѡ���ˣ�");
        return false;
    }
    var tRow = PolGrid.getSelNo() - 1;
    var tpolno = PolGrid.getRowColData(tRow, 1)
    document.all('fmAction').value = "DELETE||INSUREDRISK";
    fm.action = "./DelIsuredRisk.jsp?polno=" + tpolno;
    fm.submit();
    //�ύ

}
function InsuredChk()
{
    var tSel = InsuredGrid.getSelNo();
    if (tSel == 0 || tSel == null)
    {
        alert("����ѡ�񱻱����ˣ�");
        return false;
    }
    var tRow = InsuredGrid.getSelNo() - 1;
    var tInsuredNo = InsuredGrid.getRowColData(tRow, 1);
    var tInsuredName = InsuredGrid.getRowColData(tRow, 2);
    var tInsuredSex = InsuredGrid.getRowColData(tRow, 3);
    var tBirthday = InsuredGrid.getRowColData(tRow, 4);
	
					var sqlid57="CardContInputSql57";
				var mySql57=new SqlClass();
				mySql57.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql57.setSqlId(sqlid57);//ָ��ʹ�õ�Sql��id
				mySql57.addSubPara(tInsuredName);//ָ������Ĳ���
				mySql57.addSubPara( tInsuredSex );//ָ������Ĳ���
				mySql57.addSubPara( tInsuredNo );//ָ������Ĳ���
				
				mySql57.addSubPara(tInsuredNo);//ָ������Ĳ���
				mySql57.addSubPara( fm.IDType.value );//ָ������Ĳ���
				mySql57.addSubPara( fm.IDNo.value );//ָ������Ĳ���
				mySql57.addSubPara( tInsuredNo );//ָ������Ĳ���
			     var sqlstr  =mySql57.getString();	
	
//    var sqlstr = "select * from ldperson where Name='" + tInsuredName
//            + "' and Sex='" + tInsuredSex + "' and Birthday='" + tBirthday
//            + "' and CustomerNo<>'" + tInsuredNo + "'"
//            + " union select * from ldperson where IDType = '" + fm.IDType.value
//            + "' and IDType is not null and IDNo = '" + fm.IDNo.value
//            + "' and IDNo is not null and CustomerNo<>'" + tInsuredNo + "'" ;
    arrResult = easyExecSql(sqlstr, 1, 0);

    if (arrResult == null)
    {
        alert("��û����ñ����˱������ƵĿͻ�,����У��");
        return false;
    }

    window.open("../uw/AppntChkMain.jsp?ProposalNo1=" + fm.ContNo.value + "&Flag=I&LoadFlag=" + LoadFlag + "&InsuredNo=" + tInsuredNo, "window1",sFeatures);
}
function getdetailaccount()
{
    if (fm.AccountNo.value == "1")
    {
        document.all('BankAccNo').value = mSwitch.getVar("AppntBankAccNo");
        document.all('BankCode').value = mSwitch.getVar("AppntBankCode");
        document.all('AccName').value = mSwitch.getVar("AppntAccName");
    }
    if (fm.AccountNo.value == "2")
    {
        document.all('BankAccNo').value = "";
        document.all('BankCode').value = "";
        document.all('AccName').value = "";
    }

}
function AutoMoveForNext()
{
    if (fm.AutoMovePerson.value == "���Ƶڶ���������")
    {
        //emptyFormElements();
        param = "122";
        fm.pagename.value = "122";
        fm.AutoMovePerson.value = "���Ƶ�����������";
        return false;
    }
    if (fm.AutoMovePerson.value == "���Ƶ�����������")
    {
        //emptyFormElements();
        param = "123";
        fm.pagename.value = "123";
        fm.AutoMovePerson.value = "���Ƶ�һ��������";
        return false;
    }
    if (fm.AutoMovePerson.value == "���Ƶ�һ��������")
    {
        //emptyFormElements();
        param = "121";
        fm.pagename.value = "121";
        fm.AutoMovePerson.value = "���Ƶڶ���������";
        return false;
    }
}
function noneedhome()
{

    var insuredno = "";
    if (InsuredGrid.mulLineCount >= 1)
    {
        for (var personcount = 0; personcount <= InsuredGrid.mulLineCount; personcount++)
        {
            if (InsuredGrid.getRowColData(personcount, 5) == "00")
            {
                insuredno = InsuredGrid.getRowColData(personcount, 1);

                break;
            }
        }
		
				var sqlid58="CardContInputSql58";
				var mySql58=new SqlClass();
				mySql58.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql58.setSqlId(sqlid58);//ָ��ʹ�õ�Sql��id
				mySql58.addSubPara(insuredno);//ָ������Ĳ���
				mySql58.addSubPara(  fm.ContNo.value );//ָ������Ĳ���
				mySql58.addSubPara( insuredno );//ָ������Ĳ���
				
			     var strhomea  =mySql58.getString();	
		
//        var strhomea = "select HomeAddress,HomeZipCode,HomePhone from lcaddress where customerno='" + insuredno + "' and addressno=(select addressno from lcinsured where contno='" + fm.ContNo.value + "' and insuredno='" + insuredno + "')";
        arrResult = easyExecSql(strhomea, 1, 0);
        try {
            document.all('HomeAddress').value = arrResult[0][0];
        } catch(ex) {
        }
        ;

        try {
            document.all('HomeZipCode').value = arrResult[0][1];
        } catch(ex) {
        }
        ;

        try {
            document.all('HomePhone').value = arrResult[0][2];
        } catch(ex) {
        }
        ;

        fm.InsuredAddressNo.value = "";
        fm.InsuredNo.value = "";
    }
}
function getdetail2()
{
	
					var sqlid59="CardContInputSql59";
				var mySql59=new SqlClass();
				mySql59.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql59.setSqlId(sqlid59);//ָ��ʹ�õ�Sql��id
				mySql59.addSubPara(  fm.BankAccNo.value );//ָ������Ĳ���
				
			     var strSql  =mySql59.getString();
	
//    var strSql = "select BankCode,AccName from LCAccount where BankAccNo='" + fm.BankAccNo.value + "'";
    arrResult = easyExecSql(strSql);
    if (arrResult != null) {
        fm.BankCode.value = arrResult[0][0];
        fm.AccName.value = arrResult[0][1];
    }
}


// �ڳ�ʼ��bodyʱ�Զ�Ч��Ͷ������Ϣ
function InsuredChkNew() {
    //alert("aaa");
    var tRow = InsuredGrid.getSelNo() - 1;
    var tInsuredNo = InsuredGrid.getRowColData(tRow, 1);
    var tInsuredName = InsuredGrid.getRowColData(tRow, 2);
    var tInsuredSex = InsuredGrid.getRowColData(tRow, 3);
    var tBirthday = InsuredGrid.getRowColData(tRow, 4);
	
				var sqlid60="CardContInputSql60";
				var mySql60=new SqlClass();
				mySql60.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql60.setSqlId(sqlid60);//ָ��ʹ�õ�Sql��id
				mySql60.addSubPara(  tInsuredName );//ָ������Ĳ���
				mySql60.addSubPara(  tInsuredSex );//ָ������Ĳ���
				mySql60.addSubPara(  tBirthday );//ָ������Ĳ���
				mySql60.addSubPara(  tInsuredNo );//ָ������Ĳ���
				mySql60.addSubPara(   fm.IDType.value );//ָ������Ĳ���
				mySql60.addSubPara(   fm.IDNo.value );//ָ������Ĳ���
				mySql60.addSubPara(  tInsuredNo );//ָ������Ĳ���
				
			     var sqlstr  =mySql60.getString();
	
//    var sqlstr = "select * from ldperson where Name='" + tInsuredName
//            + "' and Sex='" + tInsuredSex + "' and Birthday='" + tBirthday
//            + "' and CustomerNo<>'" + tInsuredNo + "'"
//            + " union select * from ldperson where IDType = '" + fm.IDType.value
//            + "' and IDType is not null and IDNo = '" + fm.IDNo.value
//            + "' and IDNo is not null and CustomerNo<>'" + tInsuredNo + "'" ;
    arrResult = easyExecSql(sqlstr, 1, 0);
    if (arrResult == null)
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
function theSameToFirstAccount() {
    //alert("aasf");
    if (fm.theSameAccount.checked == true) {
        document.all('SecAppntBankCode').value = document.all('AppntBankCode').value;
        document.all('SecAppntAccName').value = document.all('AppntAccName').value;
        //    alert(document.all('AppntBankAccNo').value);
        //    alert(document.all('SecAppntBankAccNo').value);
        document.all('SecAppntBankAccNo').value = document.all('AppntBankAccNo').value;
        return;
    }
    if (fm.theSameAccount.checked == false) {
        //  	document.all('AppntBankCode').value='';
        //  	document.all('AppntAccName').value='';
        //  	document.all('AppntBankAccNo').value='';
        return;
    }
}

//��ʾ�����˺���Ϣ
function displayFirstAccount() {
    document.all('AppntBankCode').value = arrResult[0][0];
    document.all('AppntAccName').value = arrResult[0][1];
    document.all('AppntBankAccNo').value = arrResult[0][2];

    //��ѯ�������˺ź󣬲�ͬʱ��ֵ�������˺�
    document.all('SecAppntBankCode').value = document.all('AppntBankCode').value;
    document.all('SecAppntAccName').value = document.all('AppntAccName').value;
    document.all('SecAppntBankAccNo').value = document.all('AppntBankAccNo').value;

}

//ǿ�ƽ����˹��˱�
function forceUW() {
    //��ѯ�Ƿ��Ѿ�����ѡ��ǿ�ƽ����˹��˱�
    var ContNo = document.all("ContNo").value;
	
					var sqlid61="CardContInputSql61";
				var mySql61=new SqlClass();
				mySql61.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql61.setSqlId(sqlid61);//ָ��ʹ�õ�Sql��id
				mySql61.addSubPara(  ContNo );//ָ������Ĳ���

				
			     var sqlstr  =mySql61.getString();
	
//    var sqlstr = "select forceuwflag from lccont where contno='" + ContNo + "'" ;
    arrResult = easyExecSql(sqlstr, 1, 0);
    if (arrResult == null) {
        alert("�����ڸ�Ͷ������");
    }
    else {
        window.open("../uw/ForceUWMain.jsp?ContNo=" + ContNo, "window1",sFeatures);
    }

}

//��ѯ�Ƿ��Ѿ���ӹ�Ͷ����
function checkAppnt() {
    var ContNo = document.all("ContNo").value;
	
						var sqlid62="CardContInputSql62";
				var mySql62=new SqlClass();
				mySql62.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql62.setSqlId(sqlid62);//ָ��ʹ�õ�Sql��id
				mySql62.addSubPara(  ContNo );//ָ������Ĳ���
				
			     var sqlstr  =mySql62.getString();
	
//    var sqlstr = "select forceuwflag from lccont where contno='" + ContNo + "'" ;
    arrResult = easyExecSql(sqlstr, 1, 0);
    if (arrResult == null) {
        return false;
    }
    else {
        return true;
    }

}

function exitAuditing() {
    if (confirm("ȷ�ϸ�Ͷ�����ĸ��˽��棿")) {
        top.close();
    }
    else {
        return;
    }
}

function exitAuditing2() {
    if (confirm("ȷ���뿪��Ͷ����������޸Ľ��棿")) {
        top.close();
    }
    else {
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
    var ActivityID = document.all.ActivityID.value;
    //alert("ActivityID="+ActivityID);

    //	var PrtNo = SelfGrpGrid.getRowColData(selno, 2);
    var PrtNo = document.all.PrtNo2.value;
    // alert("PrtNo="+ document.all.PrtNo2.value);

    var NoType = document.all.NoType.value;
    //alert("NoType="+NoType);
    if (PrtNo == null || PrtNo == "")
    {
        alert("Ͷ������Ϊ�գ�");
        return;
    }
    var varSrc = "&MissionID=" + MissionID + "&SubMissionID=" + SubMissionID + "&ActivityID=" + ActivityID + "&PrtNo=" + PrtNo + "&NoType=" + NoType;
    var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp" + varSrc, "���������±��鿴", "left");

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

    showOneCodeName('comcode', 'ManageCom', 'ManageComName');
    //���뺺��

    showOneCodeName('comcode', 'AgentManageCom', 'AgentManageComName');

    showOneCodeName('sellType', 'SellType', 'sellTypeName');

}
/*********************************************************************
 *  Ͷ���˴���򺺻�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showAppntCodeName()
{
    showOneCodeName('vipvalue', 'AppntVIPValue', 'AppntVIPFlagname');

    showOneCodeName('IDType', 'AppntIDType', 'AppntIDTypeName');

    showOneCodeName('Sex', 'AppntSex', 'AppntSexName');

    //showOneCodeName('Marriage','AppntMarriage','AppntMarriageName');

    showOneCodeName('NativePlace', 'AppntNativePlace', 'AppntNativePlaceName');

    showOneCodeName('OccupationCode', 'AppntOccupationCode', 'AppntOccupationCodeName');

    showOneCodeName('LicenseType', 'AppntLicenseType', 'AppntLicenseTypeName');

    showOneCodeName('GetAddressNo', 'AppntAddressNo', 'AddressNoName');

    showOneCodeName('province', 'AppntProvince', 'AppntProvinceName');

    showOneCodeName('city', 'AppntCity', 'AppntCityName');

    showOneCodeName('district', 'AppntDistrict', 'AppntDistrictName');

    showOneCodeName('paymode', 'PayMode', 'FirstPayModeName');

    showOneCodeName('continuepaymode', 'SecPayMode', 'PayModeName');

    showOneCodeName('bank', 'AppntBankCode', 'FirstBankCodeName');

    showOneCodeName('bank', 'SecAppntBankCode', 'AppntBankCodeName');
    showOneCodeName('incomeway', 'IncomeWay0', 'IncomeWayName0');
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

    showOneCodeName('SequenceNo', 'SequenceNo', 'SequenceNoName');

    showOneCodeName('vipvalue', 'VIPValue1', 'AppntVIPFlagname1');

    showOneCodeName('Relation', 'RelationToMainInsured', 'RelationToMainInsuredName');

    showOneCodeName('Relation', 'RelationToAppnt', 'RelationToAppntName');

    showOneCodeName('IDType', 'IDType', 'IDTypeName');

    showOneCodeName('Sex', 'Sex', 'SexName');

    //showOneCodeName('Marriage','Marriage','MarriageName');

    showOneCodeName('OccupationCode', 'OccupationCode', 'OccupationCodeName');

    showOneCodeName('NativePlace', 'NativePlace', 'NativePlaceName');

    showOneCodeName('LicenseType', 'LicenseType', 'LicenseTypeName');

    showOneCodeName('GetAddressNo', 'InsuredAddressNo', 'InsuredAddressNoName');

    showOneCodeName('Province', 'InsuredProvince', 'InsuredProvinceName');

    showOneCodeName('City', 'InsuredCity', 'InsuredCityName');

    showOneCodeName('District', 'InsuredDistrict', 'InsuredDistrictName');
    showOneCodeName('incomeway', 'IncomeWay', 'IncomeWayName');

}

//�����Ӱ���ѯ
function QuestPicQuery() {
    alert("�����С���");
    return;
}
//��ѯҵ��Ա��֪��Ϣ
function getImpartInitInfo()
{
    var tContNo = fm.ContNo.value;

//    var strSQL = "select impartver,impartcode,ImpartContent from LDImpart where impartver='05' ";
    
	var sqlid86="CardContInputSql86";
	var mySql86=new SqlClass();
	mySql86.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql86.setSqlId(sqlid86);//ָ��ʹ�õ�Sql��id
    var strSQL  =mySql86.getString();

    turnPage.queryModal(strSQL, AgentImpartGrid);
	
					var sqlid63="CardContInputSql63";
				var mySql63=new SqlClass();
				mySql63.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql63.setSqlId(sqlid63);//ָ��ʹ�õ�Sql��id
				mySql63.addSubPara(  tContNo );//ָ������Ĳ���
				
			     var aSQL  =mySql63.getString();
	
//    var aSQL = "SELECT distinct a.impartver,a.impartcode,a.ImpartContent,b.impartparammodle "
//            + "FROM ldimpart a left join lccustomerimpart b on a.impartver=b.impartver and a.ImpartCode=b.ImpartCode and b.contno='" + tContNo + "' "
//            + "WHERE a.impartver='05'";

    turnPage.queryModal(aSQL, AgentImpartGrid);
    return true;
}

function querycont()
{
    //alert(BankFlag);
    if (BankFlag != null && BankFlag == "4") {
        fm.addbutton.disabled = true;
        fm.Donextbutton9.disabled = true;
        fm.Donextbutton10.disabled = true;
    }
    var tContNo = fm.ProposalContNo.value;
	
	         	var sqlid64="CardContInputSql64";
				var mySql64=new SqlClass();
				mySql64.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql64.setSqlId(sqlid64);//ָ��ʹ�õ�Sql��id
				mySql64.addSubPara(  tContNo );//ָ������Ĳ���
				
			     var strSQL  =mySql64.getString();
	
//    var strSQL = "select decode(trim(appflag),'0',a.forceuwreason,a.contno),a.polapplydate,a.selltype,a.managecom,a.agentcode,(select b.name from laagent b where trim(b.agentcode)=trim(a.agentcode)),(select c.name from labranchgroup c where trim(c.agentgroup)=trim(a.agentgroup)) from lccont a where a.proposalcontno='" + tContNo + "'";

    var Arrayresult = easyExecSql(strSQL);
    if (Arrayresult != null)
    {
        document.all('ContCardNo').value = Arrayresult[0][0];
        document.all('PolAppntDate').value = Arrayresult[0][1];
        document.all('SellType').value = Arrayresult[0][2];
        document.all('ManageCom').value = Arrayresult[0][3];
        document.all('AgentCode').value = Arrayresult[0][4];
        document.all('AgentName').value = Arrayresult[0][5];
        document.all('AgentManageCom').value = Arrayresult[0][3];
    }


	         	var sqlid65="CardContInputSql65";
				var mySql65=new SqlClass();
				mySql65.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql65.setSqlId(sqlid65);//ָ��ʹ�õ�Sql��id
				mySql65.addSubPara(  document.all('AgentCode').value );//ָ������Ĳ���
				
			    strSQL  =mySql65.getString();

//    strSQL = "select a.name from labranchgroup a where a.agentgroup= (select  b.agentgroup from laagent b where b.agentcode='" + document.all('AgentCode').value + "')";
    var brray = easyExecSql(strSQL);
    if(brray!=null)
    document.all('BranchAttr').value = brray[0][0];

    showOneCodeName('sellType', 'SellType', 'sellTypeName');
    showOneCodeName('comcode', 'ManageCom', 'ManageComName');
    showOneCodeName('comcode', 'AgentManageCom', 'AgentManageComName');

}


function queryappnt()
{
    var tPrtNo = fm.ProposalContNo.value;
	
		         	var sqlid66="CardContInputSql66";
				var mySql66=new SqlClass();
				mySql66.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql66.setSqlId(sqlid66);//ָ��ʹ�õ�Sql��id
				mySql66.addSubPara( tPrtNo );//ָ������Ĳ���
				
			     var strSQL  =mySql66.getString();
	
//    var strSQL = "select a.insuredno,a.name,a.idtype,a.idno,a.sex,a.birthday,a.marriage,a.occupationcode,(select b.grpname from lcaddress b where b.customerno=a.insuredno and b.addressno=a.addressno ),a.addressno,(select c.province from lcaddress c where c.customerno=a.insuredno and c.addressno=a.addressno ),(select d.city from lcaddress d where d.customerno=a.insuredno and d.addressno=a.addressno ),(select e.county from lcaddress e where e.customerno=a.insuredno and e.addressno=a.addressno ),(select f.postaladdress from lcaddress f where f.customerno=a.insuredno and f.addressno=a.addressno ),(select g.zipcode from lcaddress g where g.customerno=a.insuredno and g.addressno=a.addressno ),a.relationtoappnt from lcinsured a where a.prtno='" + tPrtNo + "'";

    var crrayresult = easyExecSql(strSQL);
    if (crrayresult != null)
    {
        document.all('InsuredNo').value = crrayresult[0][0];
        document.all('Name').value = crrayresult[0][1];
        document.all('IDType').value = crrayresult[0][2];
        document.all('IDNo').value = crrayresult[0][3];
        document.all('Sex').value = crrayresult[0][4];
        document.all('Birthday').value = crrayresult[0][5];
        //document.all('Marriage').value = crrayresult[0][6];
        document.all('OccupationCode').value = crrayresult[0][7];
        document.all('GrpName').value = crrayresult[0][8];
        document.all('InsuredAddressNo').value = crrayresult[0][9];
        document.all('InsuredProvince').value = crrayresult[0][10];
        document.all('InsuredCity').value = crrayresult[0][11];
        document.all('InsuredDistrict').value = crrayresult[0][12];
        document.all('PostalAddress').value = crrayresult[0][13];
        document.all('InsuredAddressNoName').value = crrayresult[0][13];
        document.all('ZIPCODE').value = crrayresult[0][14];
        document.all('RelationToAppnt').value = crrayresult[0][15];

        getAge2();
        getdetailwork2();
        showOneCodeName('idtype', 'IDType', 'IDTypeName');
        showOneCodeName('sex', 'Sex', 'SexName');
        //showOneCodeName('marriage','Marriage','MarriageName');

		         	var sqlid67="CardContInputSql67";
				var mySql67=new SqlClass();
				mySql67.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql67.setSqlId(sqlid67);//ָ��ʹ�õ�Sql��id
				mySql67.addSubPara( document.all('InsuredProvince').value );//ָ������Ĳ���
				mySql67.addSubPara( document.all('InsuredCity').value );//ָ������Ĳ���
				mySql67.addSubPara( document.all('InsuredDistrict').value );//ָ������Ĳ���
			     strSQL =mySql67.getString();


//        strSQL = "select (select a.placename from LDAddress a where a.placetype='01' and a.placecode='" + document.all('InsuredProvince').value + "')," +
//                 "(select b.placename from LDAddress b where b.placetype='02' and b.placecode='" + document.all('InsuredCity').value + "')," +
//                 "(select c.placename from LDAddress c where c.placetype='03' and c.placecode='" + document.all('InsuredDistrict').value + "') from dual ";

        var drrayresult = easyExecSql(strSQL);
        if (drrayresult != null)
        {
            document.all('InsuredProvinceName').value = drrayresult[0][0];
            document.all('InsuredCityName').value = drrayresult[0][1];
            document.all('InsuredDistrictName').value = drrayresult[0][2];
        }

		         	var sqlid68="CardContInputSql68";
				var mySql68=new SqlClass();
				mySql68.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql68.setSqlId(sqlid68);//ָ��ʹ�õ�Sql��id
				mySql68.addSubPara( document.all('OccupationCode').value );//ָ������Ĳ���
			     strSQL =mySql68.getString();

//        strSQL = "select trim(OccupationName) from LDOccupation where occupationcode='" + document.all('OccupationCode').value + "' and worktype = 'GR' ";

        var drrayresult = easyExecSql(strSQL);
        if (drrayresult != null)
        {
            document.all('OccupationCodeName').value = drrayresult[0][0];
        }


    }

}

function queryinsured()
{
    var tPrtNo = fm.ProposalContNo.value;
	
			      var sqlid69="CardContInputSql69";
				var mySql69=new SqlClass();
				mySql69.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql69.setSqlId(sqlid69);//ָ��ʹ�õ�Sql��id
				mySql69.addSubPara( tPrtNo);//ָ������Ĳ���
			      var strSQL =mySql69.getString();
	
//    var strSQL = "select a.appntno,a.appntname,a.idtype,a.idno,a.appntsex,a.appntbirthday,a.marriage,a.occupationcode,(select b.grpname from lcaddress b where b.customerno=a.appntno and b.addressno=a.addressno ),a.addressno,(select c.province from lcaddress c where c.customerno=a.appntno and c.addressno=a.addressno ),(select d.city from lcaddress d where d.customerno=a.appntno and d.addressno=a.addressno ),(select e.county from lcaddress e where e.customerno=a.appntno and e.addressno=a.addressno ),(select f.postaladdress from lcaddress f where f.customerno=a.appntno and f.addressno=a.addressno ),(select g.zipcode from lcaddress g where g.customerno=a.appntno and g.addressno=a.addressno ) from lcappnt a where prtno='" + tPrtNo + "'";

    var crrayresult = easyExecSql(strSQL);
    if (crrayresult != null)
    {
        document.all('AppntNo').value = crrayresult[0][0];
        document.all('AppntName').value = crrayresult[0][1];
        document.all('AppntIDType').value = crrayresult[0][2];
        document.all('AppntIDNo').value = crrayresult[0][3];
        document.all('AppntSex').value = crrayresult[0][4];
        document.all('AppntBirthday').value = crrayresult[0][5];
        //document.all('AppntMarriage').value = crrayresult[0][6];
        document.all('AppntOccupationCode').value = crrayresult[0][7];
        document.all('AppntGrpName').value = crrayresult[0][8];
        document.all('AppntAddressNo').value = crrayresult[0][9];
        document.all('AppntProvince').value = crrayresult[0][10];
        document.all('AppntCity').value = crrayresult[0][11];
        document.all('AppntDistrict').value = crrayresult[0][12];
        document.all('AppntPostalAddress').value = crrayresult[0][13];
        document.all('AddressNoName').value = crrayresult[0][13];
        document.all('AppntZipCode').value = crrayresult[0][14];

        getAge();
        getdetailwork();
        showOneCodeName('idtype', 'AppntIDType', 'AppntIDTypeName');
        showOneCodeName('sex', 'AppntSex', 'AppntSexName');
        //showOneCodeName('marriage','AppntMarriage','AppntMarriageName');


			      var sqlid70="CardContInputSql70";
				var mySql70=new SqlClass();
				mySql70.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql70.setSqlId(sqlid70);//ָ��ʹ�õ�Sql��id
				mySql70.addSubPara( document.all('AppntProvince').value);//ָ������Ĳ���
				mySql70.addSubPara(  document.all('AppntCity').value);//ָ������Ĳ���
				mySql70.addSubPara( document.all('AppntDistrict').value);//ָ������Ĳ���
			     strSQL =mySql70.getString();

//        strSQL = "select (select a.placename from LDAddress a where a.placetype='01' and a.placecode='" + document.all('AppntProvince').value + "')," +
//                 "(select b.placename from LDAddress b where b.placetype='02' and b.placecode='" + document.all('AppntCity').value + "')," +
//                 "(select c.placename from LDAddress c where c.placetype='03' and c.placecode='" + document.all('AppntDistrict').value + "') from dual ";

        var drrayresult = easyExecSql(strSQL);
        if (drrayresult != null)
        {
            document.all('AppntProvinceName').value = drrayresult[0][0];
            document.all('AppntCityName').value = drrayresult[0][1];
            document.all('AppntDistrictName').value = drrayresult[0][2];
        }

			      var sqlid71="CardContInputSql71";
				var mySql71=new SqlClass();
				mySql71.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql71.setSqlId(sqlid71);//ָ��ʹ�õ�Sql��id
				mySql71.addSubPara( document.all('AppntOccupationCode').value);//ָ������Ĳ���
			     strSQL =mySql71.getString();

//        strSQL = "select trim(OccupationName) from LDOccupation where occupationcode='" + document.all('AppntOccupationCode').value + "' and worktype = 'GR' ";

        var drrayresult = easyExecSql(strSQL);
        if (drrayresult != null)
        {
            document.all('AppntOccupationCodeName').value = drrayresult[0][0];
        }

    }
}

function querybnf()
{
    //��Ϊ ������¼����Ϣ����������ֶ���ǰ���ʵ����������˲�ѯ���� �ֶ�˳��
    var tContNo = document.all('ContCardNo').value;
	
				      var sqlid72="CardContInputSql72";
				var mySql72=new SqlClass();
				mySql72.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql72.setSqlId(sqlid72);//ָ��ʹ�õ�Sql��id
				mySql72.addSubPara(tContNo);//ָ������Ĳ���
			       var tSql  =mySql72.getString();
	
//    var tSql = "select '',name,relationtoinsured,idtype,idno,bnfno,bnfgrade,'',1 from lcbnf where contno = '" + tContNo + "'";
    var turnPage = new turnPageClass();
    turnPage.queryModal(tSql, BnfGrid);

}

function queryrisk()
{
    var tContNo = document.all('ContCardNo').value;
	
				var sqlid73="CardContInputSql73";
				var mySql73=new SqlClass();
				mySql73.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql73.setSqlId(sqlid73);//ָ��ʹ�õ�Sql��id
				mySql73.addSubPara(tContNo);//ָ������Ĳ���
			     var tSql  =mySql73.getString();
	
//    var tSql = "select riskcode , mult,amnt,prem from lcpol where contno = '" + tContNo + "'";

    var xrrayresult = easyExecSql(tSql);
    if (xrrayresult != null)
    {
        document.all('RiskCode').value = xrrayresult[0][0];
        document.all('Mult').value = xrrayresult[0][1];
        document.all('Amnt').value = xrrayresult[0][2];
        document.all('Prem').value = xrrayresult[0][3];
    }

				var sqlid74="CardContInputSql74";
				var mySql74=new SqlClass();
				mySql74.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql74.setSqlId(sqlid74);//ָ��ʹ�õ�Sql��id
				mySql74.addSubPara( document.all('RiskCode').value);//ָ������Ĳ���
			     tSql  =mySql74.getString();

//    tSql = "select riskname from lmriskapp where poltype='C' and riskcode = '" + document.all('RiskCode').value + "'";
    var urrayresult = easyExecSql(tSql);
    document.all('RiskCodeName').value = urrayresult[0][0];

}


//�������ֳ�ʼ������������Ϣ�б�
function queryPolOtherGrid()
{
	var tRiskCode=trim(fm.RiskCode.value);
	
					var sqlid75="CardContInputSql75";
				var mySql75=new SqlClass();
				mySql75.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql75.setSqlId(sqlid75);//ָ��ʹ�õ�Sql��id
				mySql75.addSubPara(tRiskCode);//ָ������Ĳ���
			    var queryParamSQL  =mySql75.getString();
	
//	var queryParamSQL="select riskcode,dutycode,sourfieldname,sourfieldcname,destfieldcname"
//	 +" from lcpolotherfielddesc where riskcode='"+tRiskCode+"'";
	turnPage.queryModal(queryParamSQL,PolOtherGrid);
	if(PolOtherGrid.mulLineCount>=1)
	 {DivPolOtherGrid.style.display = "";}
	else
	 {DivPolOtherGrid.style.display = "none";}
}

//������������Ϣ�б����Ϣ��ֵ�����������ؼ���
function setValueFromPolOtherGrid()
{
			  
	 var pName="";	  
   var pValue="";
	 try
	 {
		for (i = 0; i < PolOtherGrid.mulLineCount; i++)
		{
		  pName=PolOtherGrid.getRowColData(i, 4);
		  pValue=PolOtherGrid.getRowColData(i, 6);
			var evalStr="document.all('"+pName+"').value='"+pValue+"'";
			try{eval(evalStr); }catch(ex){};	  
		}
	 }
	 catch(ex)
	 {}
}


/**
* ����Ͷ���˱��������䡶Ͷ������������֮��=Ͷ���˱��������䡷,2005-11-18�����
* ��������������yy��mm��dd��Ͷ������yy��mm��dd
* ����  ����
*/
function calPolAppntAge(BirthDate, PolAppntDate)
{
    var age = "";
    if (BirthDate == "" || PolAppntDate == "")
    {
        age = "";
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
    if (arrPolAppntDate[0] <= 99)
    {
        arrBirthDate[0] = arrBirthDate[0] - 1900;
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
    var PlaceType = "";
    var PlaceCode = "";
    //�жϵ�ַ����,<province--ʡ--01;city--��--02;district--��/��--03;>
    switch (strCode)
            {
        case "province":
            PlaceType = "01";
            break;
        case "city":
            PlaceType = "02";
            break;
        case "district":
            PlaceType = "03";
            break;
        default:
            PlaceType = "";
    }
    //����FORM�е�����ELEMENT
    for (formsNum = 0; formsNum < window.document.forms.length; formsNum++)
    {
        for (elementsNum = 0; elementsNum < window.document.forms[formsNum].elements.length; elementsNum++)
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
    PlaceCode = strObjCode.value;
    //	strObjName.value="";
    if (strObjCode.value != "" && PlaceCode.length == 6)
    {
		
				var sqlid76="CardContInputSql76";
				var mySql76=new SqlClass();
				mySql76.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql76.setSqlId(sqlid76);//ָ��ʹ�õ�Sql��id
				mySql76.addSubPara(PlaceCode);//ָ������Ĳ���PlaceType
				mySql76.addSubPara(PlaceType);//ָ������Ĳ���
			    var strSQL  =mySql76.getString();
		
//        var strSQL = "select placecode,placename from ldaddress where placecode='" + PlaceCode + "' and placetype='" + PlaceType + "'";
        var arrAddress = easyExecSql(strSQL);
        if (arrAddress != null)
        {
            strObjName.value = arrAddress[0][1];
        }

        else
        {
            strObjName.value = "";
        }
    }
}


/*************************************************************************************
�ڿ���ǩ������ʱ��ϵͳ���ɵı���������¼��ı��ѽ���У�飬
���ϵͳ���ɵı��Ѻ�����¼��ı��Ѳ�һ��,ǩ������ͨ��
�����������ͬ��<Ͷ������>;     ����:true or false
*************************************************************************************/
function checkpayfee(ContNo)
{
    var tContNo = ContNo;
    var tTempFee = "0";
    //����¼��ı���
    var tPremFee = "0";
    //ϵͳ���ɵı���
    //��ѯ����¼��ı���
	
					var sqlid77="CardContInputSql77";
				var mySql77=new SqlClass();
				mySql77.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql77.setSqlId(sqlid77);//ָ��ʹ�õ�Sql��id
				mySql77.addSubPara(tContNo);//ָ������Ĳ���PlaceType
			    var tempfeeSQL  =mySql77.getString();
	
//    var tempfeeSQL = "select sum(nvl(paymoney,0)) from ljtempfee where tempfeetype='1' "
//    		+ " and (enteraccdate is not null and enteraccdate <>'3000-01-01') and confdate is null "
//            + " and otherno=(select contno from lccont where prtno= '" + tContNo + "')";
    var TempFeeArr = easyExecSql(tempfeeSQL);
    if (TempFeeArr != null)
    {
        tTempFee = TempFeeArr[0][0];
        if (tTempFee == null || tTempFee == "" || tTempFee == "null")
        {
            alert("��ѯ��Ͷ��������¼��ı���ʧ��");
            return false;
        }
    }
    else
    {
        alert("��ѯ��Ͷ��������¼��ı���ʧ��");
        return false;
    }
    //��ѯϵͳ���ɵı���
	
						var sqlid78="CardContInputSql78";
				var mySql78=new SqlClass();
				mySql78.setResourceName("app.CardContInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql78.setSqlId(sqlid78);//ָ��ʹ�õ�Sql��id
				mySql78.addSubPara(tContNo);//ָ������Ĳ���PlaceType
			    var premfeeSQL  =mySql78.getString();
	
//    var premfeeSQL = "select sum(case when prem is not null then prem else 0 end) from lcpol where 1=1 "
//            + " and contno=(select contno from lccont where prtno= '" + tContNo + "')";
    var PremFeeArr = easyExecSql(premfeeSQL);
    tPremFee = PremFeeArr[0][0];
    if (PremFeeArr != null)
    {
        tPremFee = PremFeeArr[0][0];
        if (tPremFee == null || tPremFee == "" || tPremFee == "null")
        {
            alert("��ѯ��Ͷ������������Ϣ�����ɱ�������ʧ�ܣ�����");
            return false;
        }
    }
    else
    {
        alert("��ѯ��Ͷ������������Ϣ�����ɱ�������ʧ�ܣ�����");
        return false;
    }
    //�Ƚϡ���ѯ����¼��ı��ѡ� �� ����ѯϵͳ���ɵı��ѡ��Ƿ���ȣ��粻����򵯳���Ϣ��ʾ��������false
    if (parseFloat(tTempFee) - parseFloat(tPremFee) != 0)
    {
        alert("ϵͳ���ɵı��ѡ�" + parseFloat(tPremFee) + "��������¼��ı��ѡ�" + parseFloat(tTempFee) + "����һ��,ǩ������ͨ��!");
        return false;
    }
    return true;
}

