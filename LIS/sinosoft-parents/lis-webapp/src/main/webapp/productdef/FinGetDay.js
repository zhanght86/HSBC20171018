//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var mDebug="0";
var mOperate="";
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
	if (verifyInput())
	{
	  var i = 0;
	  var showStr=""+"����׼����ӡ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
	//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	  //fm.hideOperate.value=mOperate;
	  //if (fm.hideOperate.value=="")
	  //{
	  //  alert("�����������ݶ�ʧ��");
	  //}
	  //showSubmitFrame(mDebug);

	  fm.submit(); //�ύ
	}
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  fm.button1.disabled=false; 
  fm.button2.disabled=false; 
  if (FlagStr == "Fail" )
  {
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
  }
  else
  {

var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+content;
  	//parent.fraInterface.initForm();
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");

    showDiv(operateButton,"true");
    showDiv(inputButton,"false");
    //ִ����һ������
  }
}

//����������ϸ
function printPay(flag)
{
	fm.action="../f1print/FinGetDaySave.jsp";
	if(flag=='finget'){
		fm.fmtransact.value="PRINT";
		fm.button1.disabled=true; 
	}else{
		fm.fmtransact.value="CONFIRM";
		fm.button2.disabled=true; 
	}
	submitForm();
}



//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,50,82,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}





//Click�¼����������ɾ����ͼƬʱ�����ú���
function deleteClick()
{
  //����������Ӧ��ɾ������
  if (confirm(""+"��ȷʵ��ɾ���ü�¼��?"+""))
  {
    mOperate="DELETE||MAIN";
    submitForm();
  }
  else
  {
    mOperate="";
    myAlert(""+"��ȡ����ɾ��������"+"");
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

function downAfterSubmit(cfilePath) {
	showInfo.close();
  fm.button1.disabled=false; 
  fm.button2.disabled=false; 

    //ִ����һ������
	fileUrl.href = cfilePath; 
  fileUrl.click();
}
