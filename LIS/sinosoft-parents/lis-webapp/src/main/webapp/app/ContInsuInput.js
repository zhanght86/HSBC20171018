//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo2;
var arrResult;
var mDebug = "0";
var mOperate = "";
var mAction = "";
window.onfocus=myonfocus;
var mSwitch = parent.VD.gVSwitch;
var mShowCustomerDetail = "GROUPPOL";
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
//ʹ�ôӸô��ڵ����Ĵ����ܹ��۽�
function myonfocus()
{
	if(showInfo2!=null)
	{
	  try
	  {
	    showInfo2.focus();  
	  }
	  catch(ex)
	  {
	    showInfo2=null;
	  }
	}
}

/*********************************************************************
 *  ���漯��Ͷ�������ύ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function submitForm()
{
	
}

/*********************************************************************
 *  �������Ͷ�������ύ��Ĳ���,���������ݷ��غ�ִ�еĲ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
	
}

/*********************************************************************
 *  "����"��ť��Ӧ����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function resetForm()
{
	
} 

/*********************************************************************
 *  "ȡ��"��ť��Ӧ����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function cancelForm()
{
	showDiv(operateButton,"true"); 
	showDiv(inputButton,"false"); 
}
 
/*********************************************************************
 *  ��ʾfrmSubmit��ܣ���������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showSubmitFrame(cDebug)
{
	if( cDebug == "1" )
		parent.fraMain.rows = "0,0,50,82,*";
	else 
		parent.fraMain.rows = "0,0,0,72,*";
}

/*********************************************************************
 *  Click�¼������������ͼƬʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function addClick()
{
	
}           

/*********************************************************************
 *  Click�¼������������ѯ��ͼƬʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function queryClick()
{
	
} 

/*********************************************************************
 *  Click�¼�����������޸ġ�ͼƬʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function updateClick()
{
	
}           

/*********************************************************************
 *  Click�¼����������ɾ����ͼƬʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function deleteClick()
{
	
}           

/*********************************************************************
 *  ��ʾdiv
 *  ����  ��  ��һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
 *  ����ֵ��  ��
 *********************************************************************
 */
function showDiv(cDiv,cShow)
{
	if( cShow == "true" )
		cDiv.style.display = "";
	else
		cDiv.style.display = "none";  
}

function InputPolicy()
{
	var newWindow = window.open("../app/NewProposal.jsp","",sFeatures);
}

function InputPolicyNoList()
{
	var newWindow = window.open("../app/NewProposal.jsp","",sFeatures);
}
function queryInsuRisk()
{
var newWindow = window.open("../app/ContInsuRiskInput.jsp","",sFeatures);	
}
 function returnparent()
 {
  	parent.close();
}
 function deleteRecord()
{
    
     LCInsuredPolGrid.delCheckTrueLine("LCInsuredPolGrid");     
     //LCInsuredPolGrid.setRowColData(LCInsuredPolGrid.mulLineCount-1,1,fm.all('RiskCode').value);
     // LCInsuredPolGrid.setRowColData(LCInsuredPolGrid.mulLineCount-1,2,"[��������]");
     //LCInsuredPolGrid.setRowColData(LCInsuredPolGrid.mulLineCount-1,3,0);
     //LCInsuredPolGrid.setRowColData(LCInsuredPolGrid.mulLineCount-1,4,0);
  }

