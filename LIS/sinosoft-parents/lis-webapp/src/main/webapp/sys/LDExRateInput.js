//               ���ļ��а����ͻ�����Ҫ�����ĺ������¼�

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
	if (!verifyInput())
    return false;
  if (!beforeSubmit())
    return false;
  var i = 0;
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
//  showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;"); 
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
  var iWidth=550;      //�������ڵĿ���; 
  var iHeight=250;     //�������ڵĸ߶�; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();

  fm.hideOperate.value=mOperate;
  if (fm.hideOperate.value=="")
  {
    fm.hideOperate.value="INSERT||MAIN";
  }
  document.getElementById("fm").submit(); //�ύ
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
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
  	//parent.fraInterface.initForm();
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ���; 
    var iHeight=350;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

    showDiv(inputButton,"false"); 
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
  	alert("��LDExRateInput.js-->resetForm�����з����쳣:��ʼ���������!");
  }
} 

//ȡ����ť��Ӧ����
function cancelForm()
{
//  window.location="../common/html/Blank.html";
    showDiv(inputButton,"false"); 
}
 
//�ύǰ��У�顢����  
function beforeSubmit()
{
	if(document.all('EndDate').value!=null&&document.all('EndDate').value!="")
	{
		if(compareDate(document.all('MakeDate').value,document.all('EndDate').value)==1)
		{
			alert("�������ڲ������ڴ������ڣ����޸ĺ������ύ��");
			return false;
		}
	}
  //���Ӳ���
  if (mOperate == "INSERT||MAIN")
  {
  	if (!verifyInput())
  	  return false;
  }
  return true;
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


//Click�¼������������ͼƬʱ�����ú���
function addClick()
{
  //����������Ӧ�Ĵ���
  mOperate="INSERT||MAIN";
  submitForm();
  clearData();//�������
}           

//Click�¼�����������޸ġ�ͼƬʱ�����ú���
function updateClick()
{
  if (!verifyInput())
    return false;
  //����������Ӧ�Ĵ���
  if (confirm("��ȷʵ���޸ĸü�¼��?"))
  {
    mOperate="UPDATE||MAIN";
    submitForm();
    clearData();//�������
  }
  else
  {
    mOperate="";
    alert("��ȡ�����޸Ĳ�����");
  }
}           

//Click�¼������������ѯ��ͼƬʱ�����ú���
function queryClick()
{
  //����������Ӧ�Ĵ���
  mOperate="QUERY||MAIN";
  showInfo=window.open("./LDExRateQuery.html");
} 

//Click�¼������������ѯ��ͼƬʱ�����ú���
function queryClick2()
{
  //����������Ӧ�Ĵ���
  mOperate="QUERY||MAIN";
  showInfo=window.open("./LBExRateQuery.html");
}         

//Click�¼����������ɾ����ͼƬʱ�����ú���
function deleteClick()
{
	if(document.all('EndDate').value==null||document.all('EndDate').value=="")
	{
		alert("ת��ʱ������ͣ�����ڣ�");
		return false;	
	}
	if(document.all('EndTime').value==null||document.all('EndTime').value=="")
	{
		alert("ת��ʱ������ͣ��ʱ�䣡");
		return false;	
	}
  if (!verifyInput())
    return false;
  //����������Ӧ��ɾ������
  if (confirm("��ȷʵ��ת��ü�¼��?"))
  {
    mOperate="DELETE||MAIN";  
    submitForm();
    clearData();//�������
  }
  else
  {
    mOperate="";
    alert("��ȡ����ɾ��������");
  }
}           
//�������
function  clearData(){
	document.all("CurrCode").value="";
	document.all("CurrCodeName").value="";
	document.all("Per").value="";
	document.all("DestCode").value="";
	document.all("DestCodeName").value="";
	document.all("ExchBid").value="";
	document.all("ExchOffer").value="";
	document.all("CashBid").value="";
	document.all("CashOffer").value="";
	document.all("Middle").value="";
	document.all("MakeDate").value="";
	document.all("MakeTime").value="";
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
function afterQuery(arrQueryResult)
{	
	var arrResult = new Array();
	
	if( arrQueryResult != null )
	{
		arrResult = arrQueryResult;
    document.all('CurrCode').value = arrResult[0];
    document.all('Per').value = arrResult[1];
    document.all('DestCode').value = arrResult[2];
    document.all('ExchBid').value = arrResult[3];
    document.all('ExchOffer').value = arrResult[4];
    document.all('CashBid').value = arrResult[5];
    document.all('CashOffer').value = arrResult[6];
    document.all('Middle').value = arrResult[7];
    document.all('MakeDate').value = arrResult[8]; 
    document.all('MakeTime').value = arrResult[9];                                          
    emptyUndefined();                                                                                                                                                                                                                                   	
 	}
 	
}