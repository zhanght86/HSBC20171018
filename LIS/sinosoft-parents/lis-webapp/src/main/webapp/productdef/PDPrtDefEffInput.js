//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;

//�ύ�����水ť��Ӧ����
function submitForm()
{
   
    fm.action = "./PDPrtDefEffSave.jsp";
  	fm.target = "f1print";  
  	fm.submit(); //�ύ
 		
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
 
  if (FlagStr == "Fail" )
  {             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  }
  else
  {     
  	
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  }
}
