//���ļ��а����ͻ�����Ҫ�����ĺ������¼�
var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();  //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPage2 = new turnPageClass();

//�ύ��ɺ���������
function afterSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
        var iWidth=550;      //�������ڵĿ���; 
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
        var iWidth=550;      //�������ڵĿ���; 
        var iHeight=350;     //�������ڵĸ߶�; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

        
        //ֱ��ȡ��������ת����������
        location.href="LLAppealInput.jsp?claimNo="+fm.ClmNo.value;
    }
    tSaveFlag ="0";
}

// ��ʼ������1
function queryGrid()
{
    initLLAppealGrid();
    var strSQL = "";
    //
	strSQL = "select rgtno,rgtantname,rgtantsex,assigneename,assigneesex,accidentdate,endcasedate from llregister where 1=1 "
           + getWherePart( 'RgtNo' ,'RptNo')
           + getWherePart( 'RgtantName' ,'RptorName','like')
           + getWherePart( 'Relation' ,'Relation')
           + getWherePart( 'AssigneeType' ,'AssigneeType')
           + getWherePart( 'AssigneeCode' ,'AssigneeCode')
           + getWherePart( 'AssigneeName' ,'AssigneeName','like')
           + " and MngCom like '" + fm.ManageCom.value + "'"
           + " and ClmState = '60'"   //�ⰸΪ�����
           + " and EndCaseFlag = '1'"   //�ѽ᰸
           + " and RgtState <> '13' "
           + " order by RgtNo ";
//    alert(strSQL);
    turnPage.queryModal(strSQL,LLAppealGrid);
}

// ��ʼ������2
function querySelfGrid()
{
    initSelfLLAppealGrid();
    var strSQL = "";
	strSQL = " select appealno,appealname,appealsex,relation,idtype,idno,waitdate from LLAppeal where 1=1 "
           + " and Operator = '" + fm.Operator.value + "'"
           + " and AppealState != '1'"
           + " order by appealno ";
//    alert(strSQL);
    turnPage2.queryModal(strSQL,SelfLLAppealGrid);
}

//LLAppealGrid����¼�
function LLAppealGridClick()
{
}

//SelfLLAppealGrid����¼�
function SelfLLAppealGridClick()
{
    var i = SelfLLAppealGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        var tClmNo = SelfLLAppealGrid.getRowColData(i,1);
        location.href="LLAppealInput.jsp?claimNo="+tClmNo;
    }
}

//[����]��ť
function ApplyClaim()
{
	var selno = LLAppealGrid.getSelNo() - 1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ���봦�����ⰸ��");
	      return;
	}
	fm.ClmNo.value = LLAppealGrid.getRowColData(selno, 1);
	
	fm.action = "./LLAppealMissSave.jsp";
	submitForm(); //�ύ
}

//�ύ����
function submitForm()
{
    var i = 0;
    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ���; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();


    fm.submit(); //�ύ
    tSaveFlag ="0";
}