//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var sqlresourcename = "bq.PEdorTypeGMInputSql";

window.onfocus=myonfocus;
//ʹ�ôӸô��ڵ����Ĵ����ܹ��۽�
function myonfocus()
{
	if(showInfo!=null)
	{
	  try
	  {
	    showInfo.focus();
	  }
	  catch(ex)
	  {
	    showInfo=null;
	  }
	}
}
//���ز���
function returnParent()
{
 top.opener.initEdorItemGrid();
 top.opener.getEdorItemGrid();
 top.close();
}

//���ظ�����
function returnParent()
{
 top.opener.initEdorItemGrid();
 top.opener.getEdorItemGrid();
 top.close();
}
function edorTypeGMQuery()
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
    document.all('fmtransact').value = "QUEYR||GETMONEY";
    fm.submit();
}

function edorTypeGMSave()
{
    if (fm.GetDutyKind.value == null || fm.GetDutyKind.value == '')
    {
        alert("��ѡ������ȡ��ʽ��");
        return false;
    }
    
    if (fm.GetDutyKind_S.value == fm.GetDutyKind.value)
    {
    	alert("��ѡ��������ȡ��ʽ���粻����볷�����α�ȫ������");
    	return false;
    }
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
    document.all('fmtransact').value = "UPDATE||MAIN";
    fm.submit(); 
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content, OldGetMoney, NewGetMoney )
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
    if (document.all('fmtransact').value == "UPDATE||MAIN")
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
        initForm();
//        showInfo.close();
    }
//    else if (document.all('fmtransact').value == "QUEYR||GETMONEY")
//    {
//        document.all('ChgBeforeStand').value = OldGetMoney;
//        document.all('ChgAfterStand').value = NewGetMoney;
//    }
  }
}



//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm()
{
  try
  {
	  initForm();
  }
  catch(re)
  {
  	alert("��Proposal.js-->resetForm�����з����쳣:��ʼ���������!");
  }
} 

//ȡ����ť��Ӧ����
function cancelForm()
{
    showDiv(operateButton,"true"); 
    showDiv(inputButton,"false"); 
}
 
//�ύǰ��У�顢����  
function beforeSubmit()
{
  //��Ӳ���	
}           

//Click�¼������������ͼƬʱ�����ú���
function addClick()
{
  //����������Ӧ�Ĵ���
  showDiv(operateButton,"false"); 
  showDiv(inputButton,"true"); 
}           

//Click�¼�����������޸ġ�ͼƬʱ�����ú���
function updateClick()
{
  //����������Ӧ�Ĵ���
  alert("update click");
}           

//Click�¼������������ѯ��ͼƬʱ�����ú���
function queryClick()
{
  //����������Ӧ�Ĵ���
	alert("query click");
	  //��ѯ���������һ��ģ̬�Ի��򣬲��ύ�������������ǲ�ͬ��
  //��ˣ����еĻ����Ҳ���Բ��ø�ֵ��
}           

//Click�¼����������ɾ����ͼƬʱ�����ú���
function deleteClick()
{
  //����������Ӧ�Ĵ���
  alert("delete click");
}           
//---------------------------
//��ʾdiv����һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
function showDiv(cDiv,cShow)
{
  if (cShow=="true")
  {
    cDiv.style.display="";
  }
  else
  {
    cDiv.style.display="none";  
  }
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
function showBankDiv()
{
	if(document.all('GoonGetMethod').value=='02')
	{
		BankInfo.style.display = "";
	}
	if(document.all('GoonGetMethod').value=='01' || document.all('GoonGetMethod').value=='03' )
	{
		BankInfo.style.display = "none";

	}
}
function AccountConfirm()
{
	var str1 = trim(document.all('Account').value);
	var str2 = trim(document.all('AccountAgain').value);
	if(str1!=str2)
	{
		alert('�����ʺ����벻һ�£�');
		return;
	}
}
function showNotePad()
{
	var MissionID = top.opener.document.all("MissionID").value;
	var SubMissionID = top.opener.document.all("SubMissionID").value;
	var ActivityID = "0000000003";
	var OtherNo = top.opener.document.all("OtherNo").value;

	var OtherNoType = "1";
	if(MissionID == null || MissionID == "")
	{
		alert("MissionIDΪ�գ�");
		return;
	}		
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");	
}

function QueryEdorInfo()
{
	var tEdortype=top.opener.document.all('EdorType').value;
	if(tEdortype!=null || tEdortype !='')
	{
//	var strSQL="select distinct edorcode, edorname from lmedoritem where edorcode = '"+tEdortype+"'";
    
    var strSQL = "";
	var sqlid1="PEdorTypeGMInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(tEdortype);//ָ������Ĳ���
	strSQL=mySql1.getString();
    
    }
    else
	{
		alert('δ��ѯ����ȫ������Ŀ��Ϣ��');
	}
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);	
	var arrSelected = new Array();	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
    try {document.all('EdorType').value = arrSelected[0][0];} catch(ex) { }; //ְҵ���
    try {document.all('EdorTypeName').value = arrSelected[0][1];} catch(ex) { }; //ְҵ���
}

function initGetDutyCode(cObjCode,cObjName)
{
	condition = "1 AND b.polno=#" + fm.PolNo.value +"#";
    showCodeList('livegetmode_GM',[cObjCode,cObjName],[0,1], null, condition, "1");
}

function keyUpGetDutyCode(cObjCode,cObjName)
{
	condition = "1 AND b.polno=#" + fm.PolNo.value +"#";
    showCodeListKey('livegetmode_GM',[cObjCode,cObjName],[0,1], null, condition, "1");
}

function Edorquery()
{
	var ButtonFlag = top.opener.document.all('ButtonFlag').value;
	if(ButtonFlag!=null && ButtonFlag=="1")
    {
       divEdorquery.style.display = "none";
    }
    else
 	{
 		divEdorquery.style.display = "";
 	}
}


