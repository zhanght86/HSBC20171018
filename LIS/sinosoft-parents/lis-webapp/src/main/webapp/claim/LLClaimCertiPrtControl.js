 //���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();
//��ʼ����ѯ--------------��ѯ��ǰ�ⰸ�ڴ�ӡ�����ĵ�֤��Ϣ   
function showCertiPrtControl()
{
	/*var strSQL="select a.prtseq,a.code,b.prtname,a.managecom,a.reqcom,a.reqoperator,a.prttype,a.stateflag"
			+" from loprtmanager a,llparaprint b where 1=1 and a.code=b.prtcode and a.stateflag='3' "
			+" and a.othernotype='05' and a.otherno='"+fm.ClmNo.value+"'"
			+" order by a.code";*/
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimCertiPrtControlInputSql");
	mySql.setSqlId("LLClaimCertiPrtControlSql1");
	mySql.addSubPara(fm.ClmNo.value ); 
	this.pageLineNum=10;		
	turnPage.queryModal(mySql.getString(),ClaimCertiGrid);
}

//[���水ť]
function showCertiPrtControlSave()
{    
	var row = ClaimCertiGrid.mulLineCount;
	var i = 0;
	for(var m=0;m<row;m++ )
	{
		if(ClaimCertiGrid.getChkNo(m))
		{
		  i=i+1;
		}
	}
	if(i==0)
	{
		alert("��ѡ������һ�����ݲ��ܱ��棡");
		return false;
	}
	fm.action="LLClaimCertiPrtControlSave.jsp";
	SubmitForm();  
}

//
function SubmitForm()
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
    fm.submit();  
}

function afterSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail" )
    {             
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
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
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
        showCertiPrtControl(); 
    }

}