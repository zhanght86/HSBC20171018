//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var turnPage = new turnPageClass();
var Mulline9GridTurnPage = new turnPageClass(); 
var Mulline10GridTurnPage = new turnPageClass(); 

//�ύ�����水ť��Ӧ����
function submitForm()
{
	if( fm.RiskCode.value == null || fm.RiskCode.value == "" )
	{
		myAlert(""+"��ѡ��Ҫ��ѯ�����֣�"+"");
		return;
	}
    fm.action = "./PDLBRiskInfoSave.jsp";
  	fm.submit(); //�ύ
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();

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

function queryMulline10(){
	var riskCode = document.getElementById("RiskCode").value;
	
	if( fm.RiskCode.value == null || fm.RiskCode.value == "" )
	{
		myAlert(""+"��ѡ��Ҫ��ѯ�����֣�"+"");
		return;
	}
	
	var sql = "select g.riskcode, g.deployversion, g.reason,g.deploydate, g.standbyflag8, g.opeartor, g.deploybatch from PD_DeployTrack g where g.riskcode = '" + riskCode + "'" + getWherePart('deploydate','StartDate', ">=") + getWherePart('deploydate','EndDate', "<=") + " order by to_number(g.deployversion)"  ;

	Mulline10GridTurnPage.queryModal(sql,Mulline10Grid);
}

//�鿴ѡ�а汾�뵱ǰ�汾������
function modifyInfoQuery()
{	
	var selNo = Mulline10Grid.getSelNo();
	
	if(selNo == 0){
		myAlert(''+"����ѡ��һ���汾��"+'');
	}
	
	//������κ�
	var batchNo = Mulline10Grid.getRowColData(selNo - 1, 7);
	
	var riskCode = Mulline10Grid.getRowColData(selNo - 1, 1);
	document.getElementById('modifyInfoID').style.display = '';
	
	//��ʾ
	var showStr=""+"���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
 	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
 	
    fm.action = "./PDLBRiskInfoSave.jsp?riskcode=" + riskCode + "&batch=" + batchNo;
  	fm.submit(); //�ύ
}

//�汾����
function productBack(){
	//���ѡ�е���
	var selNo = Mulline10Grid.getSelNo();
	
	if(selNo == 0){
		myAlert(''+"����ѡ��һ���汾��"+'');
	}
	
	//������κ�
	var batchNo = Mulline10Grid.getRowColData(selNo - 1, 7);
	var riskCode = Mulline10Grid.getRowColData(selNo - 1, 1);
	
	//��ʾ
	var showStr=""+"���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
 	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
 	
	fm.action = "./ProductCallBackSave.jsp?riskcode=" + riskCode + "&batch=" + batchNo;
	fm.submit();
}

//��Ʒ��Ϣ��ѯ
function queryRiskInfo(){
	fm.action = "PDIntegratedQueryInput.jsp";
	fm.target = "";
	fm.submit();
}
