var showInfo;
var mDebug="0";
var arrDataSet;
window.onfocus=myonfocus;

//ʹ�ôӸô��ڵ����Ĵ����ܹ��۽�
function myonfocus()
{
	if(showInfo!=null) //shwoInfo��ʲô��
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

//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,50,82,*";
  }
 	else 
 	{
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}

function VersionQuery()
{
	  showInfo=window.open("./FrameVersionRuleQuery.jsp");
}

function DataJudge()
{
	
	if(!beforeSubmit())
	{
		return false;
    }
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
  fm.action = './FIRuleEngineServiceSave.jsp';
  document.getElementById("fm").submit(); //�ύ
  
}



function afterQuery( arrQueryResult )
{
	var arrResult = new Array();
		//alert(arrQueryResult);

	if( arrQueryResult != null )
	{
		arrResult = arrQueryResult;
		document.all('VersionNo').value = arrResult[0][0];
		document.all('VersionState').value = arrResult[0][5];
		document.all('StartDay').value = arrResult[0][1];
		document.all('EndDay').value = arrResult[0][2];
		document.all('VersionState2').value = arrResult[0][9];
		
		document.all('StartDate').value = '';
		document.all('EndDate').value = '';
		document.all('callpoint').value = '';
		document.all('callpointName').value = '';
		document.all('callpointB').value = '';
		document.all('callpointBName').value = '';
		
		//������Common\javascript\Common.js�����ݴ���ѡ��Ĵ�����Ҳ���ʾ����
		showCodeName(); 

	}
}

function beforeSubmit()
{
	if((document.all('VersionNo').value=="")||(document.all('VersionNo').value==null))
	 {
	 	alert("����ͨ����ѯȡ�ð汾��ţ�����");
	 	return false;
	 }
	 if(document.all('VersionState').value=="02")
	 {
	 	alert("�汾״̬����Ϊ����״̬������");
	 	return false;
	 }
	 if((document.all('StartDate').value=="")||(document.all('StartDate').value==null))
	 {
	 	alert("��ʼ���ڲ���Ϊ�գ�����");
	 	return false;
	 }
	 if((document.all('EndDate').value=="")||(document.all('EndDate').value==null))
	 {
	 	alert("�������ڲ���Ϊ�գ�����");
	 	return false;
	 }

	 if((document.all('StartDay').value >document.all('StartDate').value)||(document.all('EndDay').value <document.all('EndDate').value))
	 {
	 	alert("�������䲻�ڰ汾��Ч����֮�ڣ�����");
	 	return false;
	 }
	 if((document.all('callpoint').value=="")||(document.all('callpoint').value==null))
	 {
	 	alert("�¼�����ܲ���Ϊ�գ�����");
	 	return false;
	 }
	 if((document.all('EndDay').value=="")||(document.all('EndDay').value==null))
	 {
	 	alert("�汾��Ϣ����������ȷ����ѡ���İ汾�Ƿ���ȷ������");
	 	return false;
	 }
	 if((document.all('VersionState').value=="")||(document.all('VersionState').value==null))
	 {
	 	alert("�汾��Ϣ����������ȷ����ѡ���İ汾�Ƿ���ȷ������");
	 	return false;
	 }
	 return true;
}

function superaddClick()
{
	if((document.all('callpointB').value == null)||(document.all('callpointB').value == ''))
	{
		alert("�����Ϊ�գ���������д��");
		document.all('callpointB').value = '';
		document.all('callpointBName').value = '';
		return false;
	}
	if((document.all('callpoint').value == null)||(document.all('callpoint').value == ''))
	{
		document.all('callpoint').value = document.all('callpointB').value;
		document.all('callpointName').value = document.all('callpointBName').value;
		document.all('callpointB').value = '';
		document.all('callpointBName').value = '';
	}
	else
	{
		document.all('callpoint').value = document.all('callpoint').value + "," +document.all('callpointB').value;
		document.all('callpointName').value = document.all('callpointName').value + "," +document.all('callpointBName').value;
		document.all('callpointB').value = '';
		document.all('callpointBName').value = '';
	}
}

function clearClick()
{
		document.all('callpoint').value = '';
		document.all('callpointBName').value = '';
		document.all('callpoint').value = '';
		document.all('callpointName').value = '';
}

function afterSubmit( FlagStr, content )
{
  showInfo.close();
  //�ͷš����ӡ���ť

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
    //resetForm();
  }
  else
  {
    //alert(content);
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
  	//parent.fraInterface.initForm();
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