//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var mOperation = "";
window.onfocus=myonfocus;
var tResourceName="certify.SysCertTakeBackInputSql";
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

//�ύ�����水ť��Ӧ����
function submitForm()
{
	if(verifyInput() == true) {
	  var i = 0;
	  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();	
	  // showSubmitFrame(mDebug);
	  fm.hideOperation.value = "INSERT||MAIN";
	  document.getElementById("fm").submit(); //�ύ
	}
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
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  else
  { 
    content="����ɹ���";
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
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


//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1") {
	parent.fraMain.rows = "0,0,50,82,*";
  }	else {
  	parent.fraMain.rows = "0,0,0,82,*";
  }
}

//Click�¼������������ѯ��ͼƬʱ�����ú���
function queryClick()
{
	alert("���ܿ�����");
	return;
  //����������Ӧ�Ĵ���
  mOperation = "QUERY||MAIN";
  fm.sql_where.value = "";
  showInfo = window.open("../certify/SysCertTakeBackQuery.html","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

function query()
{
	if(document.all('CertifyNo').value==null||trim(document.all('CertifyNo').value)=='')
	{
		fm.CertifyCode.value = '';
			fm.CertifyNo.value ='';
			fm.ValidDate.value = '';
			fm.SendOutCom.value = '';
			fm.ReceiveCom.value = '';
			fm.Handler.value = '';
			fm.HandleDate.value = '';
			fm.SendNo.value = '';
			fm.TakeBackNo.value = '';
			fm.Operator.value = '';
			fm.MakeDate.value = '';
			fm.EdorNo.value = '';
			fm.ContNo.value = '';
			fm.MissionID.value  = '';
			fm.SubMissionID.value  = '';
		return ;
	}
	//mOperation = "QUERY||MAIN";
	//fm.sql_where.value = " StateFlag = '0' ";
	//showInfo = window.open("SysCertTakeBackQuery.html");
	
	//tongmeng 2007-10-22 modify
		var strSQL = "";	
    /*var strSQL = "SELECT LWMission.MissionProp4,LWMission.MissionProp3,LWMission.MissionProp5,LWMission.MissionProp6,LWMission.MissionProp7,LWMission.MissionProp8, LWMission.MissionProp9,LWMission.MissionProp10,LWMission.MissionProp11,LWMission.MissionProp12,LWMission.MissionProp13,LWMission.MissionProp1 ,LWMission.MissionProp2 ,LWMission.MissionID ,LWMission.SubMissionID FROM LWMission"
       +" WHERE  LWMission.ActivityID in "
       +"('0000005533','0000000303','0000000314','0000001111','0000001112','0000001019','0000001113','0000001025','0000001220','0000001230','0000001300','0000001260','0000001250','0000002250','0000002111','0000002311','0000000322','0000000353','0000007006','0000007009','0000007012','0000005553','0000005563')  " 
	   + "and LWMission.ProcessID in ('0000000000','0000000003','0000000004','0000000005','0000000007')"
	   + getWherePart('LWMission.MissionProp4', 'CertifyCode') 
	   + getWherePart('LWMission.MissionProp3', 'CertifyNo')*/

var mySql40=new SqlClass();
		var sqlid40="querysqldes1";
mySql40.setResourceName("uw.SysCertTakeBackInputSql"); //ָ��ʹ�õ�properties�ļ���
mySql40.setSqlId(sqlid40);//ָ��ʹ�õ�Sql��id
mySql40.addSubPara(document.all('CertifyCode').value);//ָ������Ĳ���
mySql40.addSubPara(document.all('CertifyNo').value);//ָ������Ĳ���

	var	strSQL=mySql40.getString();	
		

//alert('1');
	//var strSQL = wrapSql(tResourceName,"querysqldes1",[document.all('CertifyCode').value,document.all('CertifyNo').value]);
	   
    	var strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	if (!strQueryResult)
	{
		fm.CertifyCode.value = '';
			fm.CertifyNo.value ='';
			fm.ValidDate.value = '';
			fm.SendOutCom.value = '';
			fm.ReceiveCom.value = '';
			fm.Handler.value = '';
			fm.HandleDate.value = '';
			fm.SendNo.value = '';
			fm.TakeBackNo.value = '';
			fm.Operator.value = '';
			fm.MakeDate.value = '';
			fm.EdorNo.value = '';
			fm.ContNo.value = '';
			fm.MissionID.value  = '';
			fm.SubMissionID.value  = '';
		alert('��ѯ�޽��');
		return false;
	}
	var arr = decodeEasyQueryResult(strQueryResult);
	fm.CertifyCode.value = arr[0][0];
			fm.CertifyNo.value = arr[0][1];
			fm.ValidDate.value = arr[0][2];
			fm.SendOutCom.value = arr[0][3];
			fm.ReceiveCom.value = arr[0][4];
			fm.Handler.value = arr[0][5];
			fm.HandleDate.value = arr[0][6];
			fm.SendNo.value = arr[0][9];
			fm.TakeBackNo.value = arr[0][10];
			fm.Operator.value = arr[0][7];
			fm.MakeDate.value = arr[0][8];
			fm.EdorNo.value = arr[0][11];
			fm.ContNo.value = arr[0][12];
			fm.MissionID.value  = arr[0][13];
			fm.SubMissionID.value  = arr[0][14];//alert("fm.MissionID.value=="+fm.MissionID.value);alert("fm.SubMissionID.value=="+fm.SubMissionID.value);
			fm.ActivityID.value  = arr[0][15];
			fm.CodeType.value=arr[0][16];
	return true;
	
}

//��ѯ����ʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
function afterQuery(arrResult)
{
	try {
	  if(arrResult!=null)
	   {	  	   
			fm.CertifyCode.value = arrResult[0][0];
			fm.CertifyNo.value = arrResult[0][1];
			fm.ValidDate.value = arrResult[0][2];
			fm.SendOutCom.value = arrResult[0][3];
			fm.ReceiveCom.value = arrResult[0][4];
			fm.Handler.value = arrResult[0][5];
			fm.HandleDate.value = arrResult[0][6];
			fm.SendNo.value = arrResult[0][9];
			fm.TakeBackNo.value = arrResult[0][10];
			fm.Operator.value = arrResult[0][7];
			fm.MakeDate.value = arrResult[0][8];
			fm.EdorNo.value = arrResult[0][11];
			fm.ContNo.value = arrResult[0][12];
			fm.MissionID.value  = arrResult[0][13];
			fm.SubMissionID.value  = arrResult[0][14];
		}
	} catch (ex) {
		alert("��afterQuery�з�������");
	}
}


