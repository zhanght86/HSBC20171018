
var showInfo;
var mDebug="0";
var FlagDel;//��delete�������ж�ɾ�����Ƿ�ɹ�

function afterSubmit( FlagStr, content )
{
  FlagDel = FlagStr;

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
    	showDiv(inputButton,"false");
    }
}

function PrintData()
{
  if((fm.StartDate.value=="")||(fm.EndDate.value=="")||(fm.StartDate.value=="null")||(fm.EndDate.value=="null"))
  {
    alert("�����뿪ʼ���ںͽ�������");
    return false;
  } 
  
  //������Ч��ʼ���ڲ��ܴ��ڽ�������
  if(compareDate(fm.StartDate.value,fm.EndDate.value)==1) 
   {
     alert("��ʼ���ڲ��ܴ��ڽ������ڣ�");	
    return false;                       
   }
   
     if( fm.AppManageType.value == "" || fm.AppManageType.value ==null )
  {
  	alert("ͳ��������Ϊ�գ�");
    return false;
  }

  var tNoticeStr = "��ʼ����:  "+fm.StartDate.value+" \n��������:  "+fm.EndDate.value+"\n��������:  ���Ʒ������ͳ�Ʊ�\nͳ������:  "+fm.ManageName.value;
   
    if(!confirm("�������ͳ�Ƶ�����Ϊ��\n\n"+tNoticeStr))
      return false;
    	
    var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    //fm.action = './XQBankSuccPrint.jsp';
    fm.target="f1print";
    showInfo.close();
    document.getElementById("fm").submit();
  
}

