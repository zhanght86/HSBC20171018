//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
window.onfocus=myonfocus;
var tResourceName="certify.SysCertSendOutInputSql";
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
    if(document.all('CertifyCode').value!=null&&document.all('CertifyCode').value!="")
    {
        //var sql="select * from lmcertifydes where JugAgtFlag in ('2','3') " +getWherePart('CertifyCode', 'CertifyCode');
    	var sql = wrapSql(tResourceName,"querysqldes1",[document.all('CertifyCode').value]);
        var arrResult = easyExecSql(sql);

	    if(arrResult!=null)
	    {
	        if(!confirm("�õ�֤����δ��У�飡�Ƿ񷢷ţ���"))
	        {
	           return; 
	        }
	    }
	            if( verifyInput() == true && CertifyList.checkValue("CertifyList") ) {
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
    else
    {
        alert("�����뵥֤���룡��");    
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
    content="����ɹ���";
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
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

// ��ѯ����
function query()
{
	fm.hideOperate.value = "QUERY";
	showInfo=window.open("./SysCertSendOutQuery.html","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
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
  	alert("SysCertSendOutInput.js-->resetForm�����з����쳣:��ʼ���������!");
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

/*********************************************************************
 *  Click�¼������������ͼƬʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function addClick()
{
	//����������Ӧ�Ĵ���
	showDiv( operateButton, "false" ); 
	showDiv( inputButton, "true" ); 
}           

/*********************************************************************
 *  Click�¼������������ѯ��ͼƬʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function queryClick()
{
	fm.hideOperation = "QUERY||MAIN";
	window.open("./SysCertSendQuery.html","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}           

//��ѯ����ʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
function afterQuery(arrResult)
{
  if(arrResult!=null)
  {
//    //document.all('ComCode').value     = arrResult[0][0];
//    //�˴���д���µĲ�ѯ���ڷ��ص�����
//    document.all('PrtNo').value = arrResult[0][0];
//    document.all('CertifyCode').value = arrResult[0][1];
//    document.all('MaxMoney').value = arrResult[0][5];
//    document.all('MaxDate').value = arrResult[0][6];
//    document.all('ComCode').value = arrResult[0][7];
//    document.all('Phone').value = arrResult[0][8];
//    document.all('LinkMan').value = arrResult[0][9];
//    document.all('CertifyPrice').value = arrResult[0][10];
//    document.all('ManageCom').value = arrResult[0][11];
//    document.all('OperatorInput').value = arrResult[0][12];
//    document.all('InputDate').value = arrResult[0][13];
//    document.all('InputMakeDate').value = arrResult[0][14];
//    document.all('GetMan').value = arrResult[0][15];
//    document.all('GetDate').value = arrResult[0][16];
//    document.all('OperatorGet').value = arrResult[0][17];
//    document.all('StartNo').value = arrResult[0][18];
//    document.all('EndNo').value = arrResult[0][19];
//    document.all('GetMakeDate').value = arrResult[0][20];
//    document.all('SumCount').value = arrResult[0][21];
//    document.all('State').value = arrResult[0][24];

		if( document.all('State').value == '0' ) {
			// �����ύ��Ϣ����CertifyPrintInit.jsp��
			setGetInfo();
		}
  }
}

function showDiv(cDiv,cShow)
{
	if( cShow == "true" )
		cDiv.style.display = "";
	else
		cDiv.style.display = "none";  
}
