var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
//��ʼ����ѯ
function initQuery()
{
	var strSQL="";
	strSQL="select t.usercode,t.username,t.comcode,t.userdescription,t.userstate from LDUser t";     
	turnPage.pageLineNum=10;    //ÿҳ10��
    turnPage.queryModal(strSQL, LLQueryUserGrid);
	//arrResult = easyExecSql(strSQL);
    //if (arrResult)
    //{
    //	displayMultiline(arrResult,LLQueryUserGrid);
    //}
}
//LLQueryUserGrid����Ӧ����
function LLQueryUserGridClick()
{
	//var i = LLQueryUserGrid.getSelNo();
    //i = i - 1;
    //fm.ContNo.value=LLQueryUserGrid.getRowColData(i,2);//��ͬ��
	return true;
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

        
    // ��дSQL���
	var strSQL = "";
	var strOperate="like";
	strSQL = "select t.usercode,t.username,t.comcode,t.userdescription,t.userstate from LDUser t where 1=1 "
				 + getWherePart( 't.usercode','UserCodeQ',strOperate )
				 + getWherePart( 't.username','UserNameQ',strOperate )
				 + getWherePart( 't.comcode','ComCodeQ',strOperate );
//alert(strSQL);

	turnPage.queryModal(strSQL, LLQueryUserGrid);  
}

// ���ݷ��ظ�����
function returnParent()
{
	//alert("aaa="+top.opener.location);
	var arrReturn = new Array();
	var tSel = LLQueryUserGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
		try
		{
			arrReturn = getQueryResult();
			  top.opener.afterQuery( arrReturn );
		  
			top.close();
		}
		catch(ex)
		{
			alert( "����ѡ��һ���ǿռ�¼���ٵ�����ذ�ť��");
			//alert( "û�з��ָ����ڵ�afterQuery�ӿڡ�" + ex );
		}
		
	}
}

function getQueryResult()
{
	//��ȡ��ȷ���к�
	var tRow = LLQueryUserGrid.getSelNo() - 1;

	var arrSelected = new Array();
	//������Ҫ���ص�����
	strSql = "select t.usercode,t.username,t.comcode,t.userdescription,t.userstate from LDUser t where 1=1"
	       + " and t.usercode = '" + LLQueryUserGrid.getRowColData(tRow, 1) + "'";
	
	//alert(strSql);
	var arrResult = easyExecSql(strSql);
	
	return arrResult;
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
//    showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
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
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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

