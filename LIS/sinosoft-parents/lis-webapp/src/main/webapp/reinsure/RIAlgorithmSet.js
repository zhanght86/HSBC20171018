//���ļ��а����ͻ�����Ҫ����ĺ������¼�

//�������ƣ�RIAlgorithmSet.js
//�����ܣ������㷨������
//�������ڣ�2011/6/16
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����

var showInfo;
window.onfocus=myonfocus;
var turnPage = new turnPageClass();

var AlgLibMul11GridTurnPage = new turnPageClass(); 
var AlgSetMul11GridTurnPage = new turnPageClass(); 

//��ѯ��ť
function button113( )
{

}

//���ذ�ť
function button114( )
{

}

//��Ӱ�ť
function button115( )
{

}

//ɾ����ť
function button116( )
{

}

function queryMul11Grid()
{
   var strSQL = "";
   strSQL = "";
   Mul11GridTurnPage.queryModal(strSQL,Mul11Grid);
}
function queryMul11Grid()
{
   var strSQL = "";
   strSQL = "";
   Mul11GridTurnPage.queryModal(strSQL,Mul11Grid);
}

//�ύǰ��У�顢����  
function beforeSubmit()
{
  //��Ӳ��� 
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

