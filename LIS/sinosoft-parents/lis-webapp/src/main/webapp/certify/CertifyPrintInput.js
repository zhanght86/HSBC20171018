//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var mDebug="0";
var mOperate="";
var mQueryOperate="";   //��һ���������ж����Ҫ��������ʱ���ø�����
var showInfo;
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

//�ύ�����水ť��Ӧ����
function submitForm()
{
	if( verifyInput() == true ) {
	  var i = 0;
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
	  fm.submit(); //�ύ
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
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  else
  { 

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
    //ִ����һ������
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
  	alert("��CertifyPrintInput.js-->resetForm�����з����쳣:��ʼ���������!");
  }
} 

//�ύǰ��У�顢����  
function beforeSubmit()
{
  //��Ӳ���	
}           


// ��������
function requestClick()
{
	fm.InputDate.verify = "��������|DATE&NOTNULL";
	fm.GetDate.verify = null;
	
	fm.hideOperate.value = "INSERT||REQUEST";
	submitForm();
}           

// �ᵥ����
function confirmClick()
{
	fm.InputDate.verify = null;
	fm.GetDate.verify = "�ᵥ����|DATE&NOTNULL";
	
	fm.hideOperate.value = "INSERT||CONFIRM";
	submitForm();
}

//Click�¼������������ѯ��ͼƬʱ�����ú���
function queryClick()
{
  //����������Ӧ�Ĵ���
  mOperate="QUERY||MAIN";
  showInfo=window.open("./CertifyPrintQuery.html");
}

// �ᵥʱ�Ĳ�ѯ��ֻ�ܲ�ѯ�������ᵥ����Щ��֤
function query()
{
	mOperate = "QUERY||MAIN";
	fm.sql_where.value = " State = '0' order by modifydate , modifytime asc";
	showInfo = window.open("CertifyPrintQuery.html");
}

//��ѯ����ʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
function afterQuery(arrResult)
{
  if(arrResult!=null)
  {
    //�˴���д���µĲ�ѯ���ڷ��ص�����
    document.all('PrtNo').value = arrResult[0][0];
    document.all('CertifyCode').value = arrResult[0][1];
    document.all('MaxDate').value = arrResult[0][6];
    document.all('ComCode').value = arrResult[0][7];
    document.all('Phone').value = arrResult[0][8];
    document.all('LinkMan').value = arrResult[0][9];
    document.all('CertifyPrice').value = arrResult[0][10];
    document.all('ManageCom').value = arrResult[0][11];
    document.all('OperatorInput').value = arrResult[0][12];
    document.all('InputDate').value = arrResult[0][13];
    document.all('InputMakeDate').value = arrResult[0][14];
    document.all('GetMan').value = arrResult[0][15];
    document.all('GetDate').value = arrResult[0][16];
    document.all('OperatorGet').value = arrResult[0][17];
    document.all('StartNo').value = arrResult[0][18];
    document.all('EndNo').value = arrResult[0][19];
    document.all('GetMakeDate').value = arrResult[0][20];
    document.all('SumCount').value = arrResult[0][21];
    document.all('State').value = arrResult[0][24];

		if( document.all('State').value == '0' ) {
			// �����ᵥ��Ϣ����CertifyPrintInit.jsp��
			setGetInfo();
		}
  }
}
