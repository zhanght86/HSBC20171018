//���ļ��а����ͻ�����Ҫ����ĺ������¼�

//�������ƣ�RIAccFeatures.js
//�����ܣ��ֳ����ζ���-�ֱ�����
//�������ڣ�2011/6/16
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����

var showInfo;
window.onfocus=myonfocus;
var turnPage = new turnPageClass();


//�������հ�ť
function button102( )
{

}


//�ύǰ��У�顢����  
function beforeSubmit()
{
  //��Ӳ��� 
}

function initData()
{

	var tSQL = "select a.StandbyFlag,(select AssociatedName from RIAccumulateRDCode where Associatedcode = a.StandbyFlag and rownum = 1)," 
		+ " b.StandbyFlag ,(select GetDutyName from RIAccumulateGetDuty where GetDutyCode = b.StandbyFlag and rownum = 1)" 
		+ " from RIAccumulateRDCode a,RIAccumulateGetDuty b " 
		+ " where a.Accumulatedefno = b.Accumulatedefno and a.Associatedcode = b.Associatedcode and a.Accumulatedefno ='"+fm.AccDefNo.value
		+ "' and a.Associatedcode ='"+fm.RIRiskCode.value
		+ "' and b.GetDutyCode = '"+fm.RIDutyCode.value+"'";
	
	var strArray = easyExecSql(tSQL);
	
	if (strArray!=null)
	{			
		for (var k=0;k<strArray.length;k++)
		{
			Mul10Grid.addOne("Mul10Grid");
			Mul10Grid.setRowColData(k,1,strArray[k][0]);
			Mul10Grid.setRowColData(k,2,strArray[k][1]);
			Mul10Grid.setRowColData(k,3,strArray[k][2]);
			Mul10Grid.setRowColData(k,4,strArray[k][3]);
		}
	} 	
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

function afterCodeSelect(codeName,Field)
{ 
	if(codeName=="ririskfeature")
	{
		if(Field.value=="01")
		{
			divFeature01.style.display='';
			divFeature02.style.display='none';
		}
		else if(Field.value=="02")
		{
			divFeature02.style.display='';
			divFeature01.style.display='none';
		}
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

