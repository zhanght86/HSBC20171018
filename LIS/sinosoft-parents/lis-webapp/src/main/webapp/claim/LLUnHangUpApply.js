var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();
//��ʼ����ѯ
function initQuery()
{

//########################################################�˴�Ϊ�����ڶ������˵�########################################	
//	//----------------------1--------2-------------3-----------4--------5-----------6----------7-----------8---------9
//	strSQL = "(select a.grpcontno,a.ContNo,a.ProposalContNo,a.PrtNo,a.ContType,a.FamilyType,a.FamilyID,a.PolType,nvl((select b.PosFlag from LCContHangUpState b where b.ContNo = a.ContNo),0),nvl((select b.RNFlag from LCContHangUpState b where b.ContNo = a.ContNo),0)"
//		   + " from LcCont a,LCInsured b where 1=1 and a.contno = b.contno "
//		   + " and b.insuredno = '"+ document.all('InsuredNo').value +"')" 
//		   + " UNION "
//		   + "(select a.grpcontno,a.ContNo,a.ProposalContNo,a.PrtNo,a.ContType,a.FamilyType,a.FamilyID,a.PolType,nvl((select b.PosFlag from LCContHangUpState b where b.ContNo = a.ContNo),0),nvl((select b.RNFlag from LCContHangUpState b where b.ContNo = a.ContNo),0)"
//		   + " from LcCont a,lcappnt b where 1=1 and a.contno = b.contno "
//		   + " and b.AppntNo = '"+ document.all('InsuredNo').value +"')";//Ͷ����
//######################################################################################################################
	
    initLLLpContSuspendGrid();
    initLLLpContInGrid();

	/*var strSQL1=""; //���ⰸ�޹�
	var strSQL2=""; //���ⰸ���
	//----------------------1--------2-------------3-----------4--------5-----------6----------7-----------8---------9
	strSQL1 = "select a.grpcontno,a.ContNo,a.ProposalContNo,a.PrtNo,a.ContType,a.FamilyType,a.FamilyID,a.PolType,(case (nvl((select count(*) from LCContHangUpState b where b.ContNo = a.ContNo and b.PosFlag = '1'),0)) when 0 then 0 else 1 end),(case (nvl((select count(*) from LCContHangUpState b where b.ContNo = a.ContNo and b.RNFlag = '1'),0)) when 0 then 0 else 1 end)"
		   + " from LcCont a where 1=1 "
		   + " and (a.insuredno = '"+ document.all('InsuredNo').value +"'"
		   + " or a.AppntNo = '"+ document.all('InsuredNo').value +"')"  //Ͷ����
		   + " and a.contno not in (select distinct contno from lcconthangupstate b where b.hangupno = '" + document.all('ClmNo').value + "')";
	*/	
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLUnHangUpApplySql");
	mySql.setSqlId("LLUnHangUpApplySql1");
	mySql.addSubPara(document.all('InsuredNo').value ); 
	mySql.addSubPara(document.all('InsuredNo').value ); 
	mySql.addSubPara(document.all('ClmNo').value ); 
	var arrResult1 = easyExecSql(mySql.getString());
//	prompt("����-��ѯ�����������ⰸ�޹ر���",strSQL1);	   
	//----------------------1--------2-------------3-----------4--------5-----------6----------7-----------8---------9
	/*strSQL2 = "select a.grpcontno,a.ContNo,a.ProposalContNo,a.PrtNo,a.ContType,a.FamilyType,a.FamilyID,a.PolType,(case (nvl((select count(*) from LCContHangUpState b where b.ContNo = a.ContNo and b.PosFlag = '1'),0)) when 0 then 0 else 1 end),(case (nvl((select count(*) from LCContHangUpState b where b.ContNo = a.ContNo and b.RNFlag = '1'),0)) when 0 then 0 else 1 end)"
		   + " from LcCont a where 1=1 "
		   + " and a.contno in (select distinct contno from lcconthangupstate b where b.hangupno =  '" + document.all('ClmNo').value + "')"
		   + " and (a.insuredno='"+ document.all('InsuredNo').value +"' or a.appntno='"+ document.all('InsuredNo').value +"')";
//    prompt("����-��ѯ�����������ⰸ��ر���",strSQL2);*/	 
    
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLUnHangUpApplySql");
	mySql.setSqlId("LLUnHangUpApplySql2");
	mySql.addSubPara(document.all('ClmNo').value ); 
	mySql.addSubPara(document.all('InsuredNo').value ); 
	mySql.addSubPara(document.all('InsuredNo').value ); 
	
	var arrResult2 = easyExecSql(mySql.getString());
    if (arrResult1)
    {
    	displayMultiline(arrResult1,LLLpContSuspendGrid);
    }
    
    if (arrResult2)
    {
    	displayMultiline(arrResult2,LLLpContInGrid);
    }
}

//LLLpContSuspendGrid����Ӧ����
function LLLpContSuspendGridClick()
{
	var i = LLLpContSuspendGrid.getSelNo();
    i = i - 1;
    fm.ContNo.value=LLLpContSuspendGrid.getRowColData(i,2);//��ͬ��
}

//LLLcContInGrid����Ӧ����
function LLLcContInGridClick()
{
	var i = LLLcContInGrid.getSelNo();
    i = i - 1;
    fm.ContNo.value=LLLcContInGrid.getRowColData(i,2);//��ͬ��
}

//�����桱��ť��Ӧ����
function saveClick()
{
	submitForm();
}

//����ѯ����ť
function queryClick()
{
    if (fm.ContNo.value == "" || fm.ContNo.value == null)
    {
    	alert("��ѡ��һ����¼��");
        return;
    } 

    strUrl="../sys/PolDetailQueryMain.jsp?ContNo=" + fm.ContNo.value + "&IsCancelPolFlag=0";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//�ύ����
function submitForm()
{
	//alert("Content:   "+fm.Content.value);return false;
    var i = 0;
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
    fm.action = './LLUnHangUpApplySave.jsp';
    fm.submit(); //�ύ
    tSaveFlag ="0"; 
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail" )
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
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }
    //tSaveFlag ="0";
    initQuery();//ˢ��
}

