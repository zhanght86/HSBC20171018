//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();  //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var mySql = new SqlClass();

//�ύ��ɺ���������
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
    queryGrid();
}


function initqueryGrid()
{
	initLLIssueGrid();
	/*
    var strSQLinit = "";
    strSQLinit = "Select RgtNo,IssueBackDate,IssueBacker,(Case IssueStage When '1' Then '����' When '2' Then '����' end),IssueReason,Autditno"
    	   + " From LLRegisterIssue where 1=1"
           + " and IssueBacker = '" + fm.Operator.value + "' and issueconclusion='03' and IssueReplyDate is null order by RgtNo";
   */
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLIssueReplyInputSql");
	mySql.setSqlId("LLIssueReplySql1");
	mySql.addSubPara(fm.Operator.value );
    turnPage.queryModal(mySql.getString(),LLIssueGrid);
}

// ��ʼ�����1
function queryGrid()
{
	if (fm.Rgtno.value == ""
	       && fm.IssueBackCom.value == ""
	       && fm.IssueBackStartDate.value == ""
	       && fm.IssueBackEndDate.value == "" )
	{
	        alert("����������һ������!");
	        return false;
	}
	
	initLLIssueGrid();
    var strSQL = "";
    /*
    strSQL = "Select RgtNo,IssueBackDate,IssueBacker,(Case IssueStage When '1' Then '����' When '2' Then '����' end),IssueReason,Autditno"
    	   + " From LLRegisterIssue where 1=1"
           + " and IssueBackCom like '" + document.all('ManageCom').value +"%' and issueconclusion='03' and IssueReplyDate is null"
           + getWherePart( 'RgtNo' ,'Rgtno');
           */
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLIssueReplyInputSql");
	mySql.setSqlId("LLIssueReplySql2");
	mySql.addSubPara(document.all('ManageCom').value  );
	mySql.addSubPara(fm.RgtNo.value );
	
    if (fm.IssueBackCom.value != "" && fm.IssueBackCom.value != null)    
    {
    	//strSQL =  strSQL + " and IssueBackCom like '" + fm.IssueBackCom.value + "%'";
    	 mySql = new SqlClass();
		mySql.setResourceName("claim.LLIssueReplyInputSql");
		mySql.setSqlId("LLIssueReplySql3");
		mySql.addSubPara(document.all('ManageCom').value  );
		mySql.addSubPara(fm.RgtNo.value );
		mySql.addSubPara(fm.IssueBackCom.value );

    }
           
    if (fm.IssueBackStartDate.value != "" && fm.IssueBackStartDate.value != null)    
    {
    	//strSQL =  strSQL + " and IssueBackDate>='" + fm.IssueBackStartDate.value + "'";
    	 mySql = new SqlClass();
		mySql.setResourceName("claim.LLIssueReplyInputSql");
		mySql.setSqlId("LLIssueReplySql4");
		mySql.addSubPara(document.all('ManageCom').value  );
		mySql.addSubPara(fm.IssueBackCom.value );
		mySql.addSubPara(fm.IssueBackStartDate.value );
    	
    }
    
    if (fm.IssueBackEndDate.value != "" && fm.IssueBackEndDate.value != null)    
    {
    	//strSQL =  strSQL + " and IssueBackDate<='" + fm.IssueBackEndDate.value + "'";
    	 mySql = new SqlClass();
		mySql.setResourceName("claim.LLIssueReplyInputSql");
		mySql.setSqlId("LLIssueReplySql5");
		mySql.addSubPara(document.all('ManageCom').value  );
		mySql.addSubPara(fm.RgtNo.value );   
		mySql.addSubPara(fm.IssueBackCom.value );
		mySql.addSubPara(fm.IssueBackStartDate.value );
		mySql.addSubPara(fm.IssueBackEndDate.value ); 
    }
    	
    //���������ֱ�ӵ����������	
    //strSQL = strSQL + " order by RgtNo";
          
    turnPage.queryModal(mySql.getString(),LLIssueGrid);
}


function LLIssueGridClick()
{
	 var tSel = LLIssueGrid.getSelNo();
	 var tIssueReason = LLIssueGrid.getRowColData(tSel-1, 5);
	 fm.IssueReason.value = tIssueReason;
}

//LLReportGrid����¼� ����֤����
function ReplySave()
{

    var i = LLIssueGrid.getSelNo();
    if(i < 1)
    {
    	alert("��ѡ��һ�м�¼��");
    	return;
    }	
    else
    {
        i = i - 1;
        fm.ClmNo.value = LLIssueGrid.getRowColData(i,1);
        fm.Autditno.value = LLIssueGrid.getRowColData(i,6);
    }

	 if (fm.IssueReplyConclusion.value == "" || fm.IssueReplyConclusion.value == null)
   {
        alert("������������ظ����ۣ�");
        return false;
   }
    
    fm.Operate.value="Issue||UPDATE";
    fm.action="LLIssueReplySave.jsp";
    SubmitForm();  

}

function SubmitForm()
{
    var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    document.getElementById("fm").submit();  
}
