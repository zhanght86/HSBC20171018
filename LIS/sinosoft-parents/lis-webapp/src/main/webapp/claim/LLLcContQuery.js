var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();
//��ʼ����ѯ
function initQuery()
{
/*
	var strSQLA="select "
        +" a.grpcontno,a.ContNo,a.ManageCom,substr(to_char(a.CValiDate),0,10),"
        +" substr(to_char(a.PaytoDate),0,10),"
        +" a.SignCom,substr(to_char(a.SignDate),0,10),"
        +" (case a.PolType when '1' then '����' when '2' then '�ŵ�' end),"
		+" (case a.AppFlag when '0' then 'Ͷ��' when '1' then '�б�' when '4' then '��ֹ' end) "
		+" from LcCont a,LCInsured b "
        +" where a.contno=b.contno and b.InsuredNo = '"+ document.all('AppntNo').value +"'"
        ;
    var strSQLB = "select "
        +" a.grpcontno,a.ContNo,a.ManageCom,substr(to_char(a.CValiDate),0,10),"
        +" substr(to_char(a.PaytoDate),0,10),"
        +" a.SignCom,substr(to_char(a.SignDate),0,10),"
        +" (case a.PolType when '1' then '����' when '2' then '�ŵ�' end),"
        +" (case a.AppFlag when '0' then 'Ͷ��' when '1' then '�б�' when '4' then '��ֹ' end) "
        +" from LcCont a,LCAppnt b "
        +" where a.contno=b.contno and b.appntno = '"+ document.all('AppntNo').value +"'";     
        
    var strSQLC = strSQLA + " union " + strSQLB*/
	/*���ӶԲ�ѯ����Ϊ��ʱ���ж� 2011-10-18 16:13:04 modified by lils*/
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLLcContQueryInputSql");
	mySql.setSqlId("LLLcContQuerySql1");
	mySql.addSubPara(document.all('AppntNo').value);
	mySql.addSubPara(document.all('AppntNo').value);
	
	mySql3 = new SqlClass();
	mySql3.setResourceName("claim.LLLcContQueryInputSql");
	mySql3.setSqlId("LLLcContQuerySql3");
	var appntNo = document.all('AppntNo').value;
	if (null == appntNo || '' == appntNo) {
		arrResult = easyExecSql(mySql3.getString());
	} else {
		arrResult = easyExecSql(mySql.getString());
	}
        
	//turnPage.pageLineNum=10;    //ÿҳ10��
    //turnPage.queryModal(strSQL, LLLcContSuspendGrid);
    //prompt("������ѯ",strSQLC);
	//arrResult = easyExecSql(mySql.getString());
    if (arrResult)
    {
    	displayMultiline(arrResult,LLLcContSuspendGrid);
    }
}

//LLLcContSuspendGrid����Ӧ����
function LLLcContSuspendGridClick()
{
	var i = LLLcContSuspendGrid.getSelNo();
    i = i - 1;
    fm.ContNo.value = LLLcContSuspendGrid.getRowColData(i,2);//��ͬ��

//===zl===2005-10-31 11:31===����״̬��ѯ�ŵ�������ϸ��=================================================================   
//    //��ѯ״̬
//    var tSql = "select contno,insuredno,polno,(case statetype when 'Terminate' then '��ֹ' when 'Available' then 'ʧЧ' end ),(case state when '0' then '��' when '1' then '��' end),statereason,startdate,enddate,remark,operator,makedate from lccontstate "
//             + " where contno = '" + fm.ContNo.value + "'"
//             + " and statetype in ('Terminate','Available')";
////    var arrState = easyExecSql(tSql); 
//    turnPage.queryModal(tSql,LcContStateGrid);
//    
//    //��ʾ����
//    divLcContState.style.display = '';
//===zl===2005-10-31 11:31===����״̬��ѯ�ŵ�������ϸ��=================================================================
}

//�����桱��ť��Ӧ����
function saveClick()
{
    fm.isReportExist.value="false";
	fm.fmtransact.value="update";
	submitForm();
}

//����ѯ����ť
function queryClick()
{
//	//���ȼ����Ƿ�ѡ���¼  
	var row = LLLcContSuspendGrid.getSelNo();
	var tContNo="";
    if (row < 1)
    {
    	alert("��ѡ��һ����¼��");
        return;
    } 
    var tGrpContNo = LLLcContSuspendGrid.getRowColData(row-1,1);//Modify by zhaorx 2006-11-24
	 // var tSQLF = " select PrtNo from LCGrpCont where grpcontno='"+tGrpContNo+"'"; //appflag='1'�б�����û��
	      mySql = new SqlClass();
		mySql.setResourceName("claim.LLLcContQueryInputSql");
		mySql.setSqlId("LLLcContQuerySql2");
		mySql.addSubPara(tGrpContNo);

	  var tPrtNo = easyExecSql(mySql.getString());
	  if(tPrtNo!=null)
	  {
	  	  //window.open("../sys/GrpPolDetailQueryMain.jsp?PrtNo=" + tPrtNo + "&GrpContNo=" + tGrpContNo );
	  	   window.open("../sys/GrpPolDetailQueryMain.jsp?PrtNo=" + tPrtNo + "&GrpContNo=" + tGrpContNo,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes" );
	  }
	  else
	  {
		    tContNo=fm.ContNo.value;//��ͬ��    
		    var strUrl="../sys/PolDetailQueryMain.jsp?ContNo="+tContNo+"&IsCancelPolFlag=0";
		    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    }
}

//����
//function goToBack()
//{
//    location.href="LLReportInput.jsp";
//}

//�ύ����
function submitForm()
{
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
    fm.action = './LLLcContSuspendSave.jsp';
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

