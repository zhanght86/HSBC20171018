//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();  //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPage2 = new turnPageClass();
var mySql = new SqlClass();

//�ύ��ɺ���������
function afterSubmit5( FlagStr, content )
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
}


// ��ѯ
function querySelfGrid()
{

    /*var strSQL = "";
	strSQL = "select a.RptNo,a.RptorName,a.RptDate,b.customerno,b.customername,"
	       + "(Select codename From ldcode Where codetype='sex' And code=c.sex),a.AccidentDate, "
	       + " (Case (Select 1 From llregister where rgtobjno=a.rptno) When 1 Then '��' Else '��' end)"
	       + "from LLReport a,llsubreport b,ldperson c Where a.rptno=b.SubRptNo "
	       + "And b.customerno=c.customerno And a.rgtclass='1'";*/
	
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimReportQueryInputSql");
	mySql.setSqlId("LLClaimReportQuerySql1");
	mySql.addSubPara("1");

	if (fm.CustomerNo.value != null && fm.CustomerNo.value != "")
	{
		//strSQL=strSQL+"and b.customerno='"+fm.CustomerNo.value+"'";
		mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimReportQueryInputSql");
		mySql.setSqlId("LLClaimReportQuerySql2");
		mySql.addSubPara(fm.CustomerNo.value ); 
	}
	
    //prompt("������ʼ�����ѯ",strSQL);
    turnPage2.queryModal(mySql.getString(),SelfLLReportGrid);
}

//������ϸ��Ϣ��ѯ
function QueryRepDetail()
{
	 var i = SelfLLReportGrid.getSelNo();
	 if (i != '0')
	    {
	        i = i - 1;
	        var tClmNo = SelfLLReportGrid.getRowColData(i,1);
	    //  location.href="LLReportInput.jsp?claimNo="+tClmNo+"&isNew=3";
	        var strUrl="LLReportInputMain.jsp?claimNo="+tClmNo+"&isNew=3";
	        window.open(strUrl,"������ϸ��Ϣ��ѯ",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	    }
}


//�ύ����
function RepRegrelaSubmit()
{
	 if(!confirm("��ȷ�Ͻ�����������Ϣ�뱾���������й���?"))
     {
         return false;
     }
	 var i = SelfLLReportGrid.getSelNo();
	 if (i != '0')
	 {
		  i = i - 1;
		  var tRptNo = SelfLLReportGrid.getRowColData(i,1);
		  fm.RptNo.value=tRptNo;
	 }
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
    document.getElementById("fm").submit(); //�ύ
}