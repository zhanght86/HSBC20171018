//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var turnPage = new turnPageClass();
var Mulline9GridTurnPage = new turnPageClass(); 

//�ύ�����水ť��Ӧ����
function submitForm()
{
	if( fm.RiskCode.value == null || fm.RiskCode.value == "" )
	{
		myAlert(""+"��ѡ��Ҫ��ѯ�����֣�"+"");
		return;
	}
    fm.action = "./PDLBRiskInfoSave.jsp?riskcode=" + riskcode + "&batch=" + batchNo;
  	fm.submit(); //�ύ
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
}
