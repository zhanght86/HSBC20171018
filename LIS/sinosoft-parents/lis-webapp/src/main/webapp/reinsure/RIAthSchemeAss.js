//���ļ��а����ͻ�����Ҫ����ĺ������¼�

//�������ƣ�RIAthSchemeAss.js
//�����ܣ������㷨����
//�������ڣ�2011/6/17
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����

var showInfo;
window.onfocus=myonfocus;
var turnPage = new turnPageClass();


//��ѯ��ť
function button110( )
{
	var tRISolType = fm.RISolType.value;
	if(tRISolType=="01"||tRISolType=="02")
	{
		var strSQL = "select 1,AccumulateDefNO,AccumulateDefName,ArithmeticID,(select ArithmeticDefName from RICalDef b where a.ArithmeticID=b.arithmeticdefid)from RIAccumulateDef a where 1=1 "
			  + getWherePart("AccumulateDefNO","RIAccDefNo")
			  + getWherePart("AccumulateDefName","RIAccDefName")
			  + getWherePart("state","RIPreceptState")
		strSQL = strSQL +" order by AccumulateDefNO";
	}
	else
	{
		var strSQL = "select 1,a.Ripreceptno,a.Ripreceptname,a.Arithmeticid,(select ArithmeticDefName from RICalDef b where a.Arithmeticid=b.arithmeticdefid) from RIPrecept a  where  1=1 "
			  + getWherePart("Ripreceptno","RIRreceptNo")
			  + getWherePart("Ripreceptname","RIRreceptName")
			  + getWherePart("state","RIPreceptState")
		strSQL = strSQL +" order by Ripreceptno";		
	}
	 
	turnPage.queryModal(strSQL, Mul9Grid);
}

//����������ť
function button109( )
{

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

function ClosePage(){
	top.close();
}
