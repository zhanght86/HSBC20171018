//���ļ��а����ͻ�����Ҫ�����ĺ������¼�

//�������ƣ�RIIBNRDefine.js
//�����ܣ�IBNR����ά��
//�������ڣ�2011-7-30
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����

var showInfo;
window.onfocus=myonfocus;
var turnPage = new turnPageClass();


//��  ѯ��ť
function button142( )
{

}

//��  �水ť
function button143( )
{

}


//�ύǰ��У�顢����  
function beforeSubmit()
{
  //���Ӳ��� 
}

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

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(content));
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  }
  else
  { 
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+encodeURI(encodeURI(content));
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    showDiv(operateButton,"true"); 
    showDiv(inputButton,"false"); 
    //ִ����һ������
  }
}

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
