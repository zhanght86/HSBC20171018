 //               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var mAction = "";
window.onfocus=myonfocus;
var mSwitch = top.opener.parent.VD.gVSwitch;

var showInfo1;
var mDebug="0";
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
//�����Ӷ���ִ�еĴ���
var addAction = 0;
//�ݽ����ܽ��
var sumTempFee = 0.0;
//�ݽ�����Ϣ�н��ѽ���ۼ�
var tempFee = 0.0;
//�ݽ��ѷ�����Ϣ�н��ѽ���ۼ�
var tempClassFee = 0.0;
//����ȷ���󣬸ñ�����Ϊ�棬�������һ��ʱ�������ֵ�Ƿ�Ϊ�棬Ϊ�������Ȼ���ٽ��ñ����ü�
var confirmFlag=false;
//
var arrCardRisk;
//window.onfocus=focuswrap;

//ʹ�ôӸô��ڵ����Ĵ����ܹ��۽�
function focuswrap()
{
	myonfocus(showInfo1);
}

//�ύ�����水ť��Ӧ����
function submitForm()
{
 }


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  
}




//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterQuery( FlagStr, content )
{
  
}

//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm()
{
 
} 

//ȡ����ť��Ӧ����
function cancelForm()
{

}
 
//�ύǰ��У�顢����  
function beforeSubmit()
{
  //��Ӳ���
	
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


//Click�¼������������ͼƬʱ�����ú���
function addClick()
{
 
}           

//Click�¼�����������޸ġ�ͼƬʱ�����ú���
function updateClick()
{
  
}           

//Click�¼������������ѯ��ͼƬʱ�����ú���
function queryClick()
{

}           

//Click�¼����������ɾ����ͼƬʱ�����ú���
function deleteClick()
{
  
}           

//��ʾdiv����һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
function showDiv(cDiv,cShow)
{
  
}

function InputPolicy()
{
	var newWindow = window.open("../app/NewProposal.jsp?RiskCode=111302","",sFeatures);
}
function InputPolicyNoList()
{
	var newWindow = window.open("../app/NewProposal.jsp?NoListFlag=1&RiskCode=111302","",sFeatures);
}
function grpInsuInfo()
{
	showInfo1 = window.open("../app/ContInsu.jsp","",sFeatures);
}



//���һ�ʼ�¼
function addRecord()
{
    RiskGrid.delBlankLine();
     document.all('fmAction').value="INSERT||GROUPRISK";
    // alert(mOperate);
     fm.submit();
  }
  function deleteRecord()
{
    
     TempGrid.delCheckTrueLine("TempGrid");     
     //TempGrid.setRowColData(TempGrid.mulLineCount-1,1,document.all('RiskCode').value);
     // TempGrid.setRowColData(TempGrid.mulLineCount-1,2,"[��������]");
     //TempGrid.setRowColData(TempGrid.mulLineCount-1,3,0);
     //TempGrid.setRowColData(TempGrid.mulLineCount-1,4,0);
  }
  function returnparent()
  {
  	parent.close();
}
  function grpFeeInput()
{
    
    parent.fraInterface.window.location = "../app/GrpFeeInput.jsp";     
}