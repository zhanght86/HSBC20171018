/**
 * <p>��������: CertifyCommon.js</p>
 * <p>������: ��֤�����ú����������� </p>
 * <p>ע�͸�����: </p>
 * <p>�����������:</p>
*/

/*************************************************************************************
������ר�����ڵ�֤�����ѯ��ȡ�������ߡ����������ߡ���Ӧ����������
<���ݴ������ֵΪ����ֵ����ѯ�����Ƹ�ֵ�����Ʊ�>���Ա��ڲ����˶�
����������������[]�����Ʊ���[] 
     ע�⣺������ʽ������Form������.��������,����fm.ReceiveCom���м��ڱ����ƺ�ӡ�Value����  
����onblur="getSendReceiveName(fm.ReceiveCom,fm.ReceiveComName)"
   �� ���ݴ������ֵfm.ReceiveCom.valueΪ����ֵ����ѯ�����Ƹ�ֵ�����Ʊ� fm.ReceiveComName.value
*************************************************************************************/
function getSendReceiveName(CodeFiled,NameFiled)
{
	var strCode=trim(CodeFiled.value);//�������
	var strCodeName="";//��������
	var strCodeLenth=0;//�������ĳ���
	var strFirstCode="";//�������ĵ�һλֵ,�����ж��ǻ�������ҵ��Ա<A--�������룻D--ҵ��Ա����>
	var strLeaveCode="";//�������Ľص���һλ��ʣ�µĴ���ֵ�����ڲ�ѯ���ݿ�
	var querySQL="";//��ѯ��SQL���
	var tResourceName="certify.CertifyTrackInfoInputSql";
	if(strCode==null ||strCode=="")
	{
		strCodeName="";
	}
	else
	{
		strCodeLenth=strCode.length;//��ȡ�������ĳ���
		strFirstCode=strCode.substring(0,1);//��ȡ�������ĵ�һλֵ<A--�������룻D--ҵ��Ա����>
		strLeaveCode=trim(strCode.substring(1,strCodeLenth));//��ȡ�������Ľص���һλ��ʣ�µĴ���ֵ�����ڲ�ѯ���ݿ�
		//����׼����ѯ���ݿ������SQL�ַ���
		if(strFirstCode=="A" && strLeaveCode!="")
		{
			//querySQL="select comcode,name from ldcom where comcode='"+strLeaveCode+"' ";
			querySQL = wrapSql(tResourceName,"querysqldes1",[strLeaveCode]);
		}
		else if(strFirstCode=="D" && strLeaveCode!="")
		{
			//querySQL="select agentcode,name from laagent where agentcode='"+strLeaveCode+"'";
			querySQL = wrapSql(tResourceName,"querysqldes2",[strLeaveCode]);
		}
		else if(strFirstCode=="E" && strLeaveCode!="")
		{
			//querySQL="select agentcom,name from lacom where agentcom='"+strLeaveCode+"'";
			querySQL = wrapSql(tResourceName,"querysqldes3",[strLeaveCode]);
		}
		else 
		{
			querySQL="";
		}
		//����׼����ѯ���ݿ�
		if(querySQL!="")
		{	
			var QueryArr=easyExecSql(querySQL);
			if(QueryArr!=null)
			{
				strCodeName=QueryArr[0][1];
			}
			else				 
			{ 
			 	strCodeName="";
			} 
		}
		else
		{
			strCodeName="";
		}
	}
	NameFiled.value=strCodeName;
} 

/***************************************************
���º�������ר�����ڵ�֤�����ѯ�����д��������ʱʹ��
***************************************************/
var PuObjectName="";
//�ж��Ƿ���Ҫ��ʾ����ѯ���д����������ť
function chkQueryAgCom(objCheck)
{
	try
	{
		if(objCheck.checked == true) 
		{
			try{fm.btnSeQueryAgentCom.style.display = "";}catch(ex){}
			try{fm.btnReQueryAgentCom.style.display = "";} catch(e) {}
		}
		else 
		{
			try{fm.btnSeQueryAgentCom.style.display = "none";}catch(ex){}
			try{fm.btnReQueryAgentCom.style.display = "none";} catch(e) {}
		}
	}
	catch(ex)
	{
	}
}
//���ò�ѯ���д����������
function queryAgentCom(sObject)
{
	try{PuObjectName=sObject.name;}catch(ex){}
	showInfo=window.open('../treeCom/jsp/BankSelectCategory.jsp','newwindow','height=400, width=800, top='+(screen.availHeight-400)/2+',left='+(screen.availWidth-800)/2+', toolbar=no, menubar=no, scrollbars=yes, resizable=no, location=no, status=no');
}

//ѡ�����������󷵻ػ�������
function afterSelectBank(cAgentCom)
{
	try{cAgentCom="E"+cAgentCom;}catch(ex){}
	try{eval("fm.all('"+PuObjectName+"').value = cAgentCom;");}catch(ex){}
	try{eval("fm.all('"+PuObjectName+"').focus();");}catch(ex){}
}
