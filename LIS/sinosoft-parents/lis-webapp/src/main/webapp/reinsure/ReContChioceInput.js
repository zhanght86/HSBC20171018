//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���

//�ύ�����水ť��Ӧ����
function submitForm()
{
	var strSQL = "";
	/**
	strSQL = "select a.ricontno,a.RIContName,"
	+" a.conttype,'',"
	+" a.risktype,'',"
	+" a.reinsurancetype,decode(a.reinsurancetype,'01','�����ֱ�','02','���ֱ�','03','�������ֱ�','��֪������'),"
	+" a.cvalidate,a.enddate from RIBarGainInfo a where 1=1 "
	*/
	var mySql100=new SqlClass();
	 	mySql100.setResourceName("reinsure.ReContChioceInputSql"); //ָ��ʹ�õ�properties�ļ���
	  	mySql100.setSqlId("ReContChioceInputSql100");//ָ��ʹ�õ�Sql��id
	 	mySql100.addSubPara("1");
	  	strSQL=mySql100.getString();
	
	if(fm.RIContName.value!=null&&fm.RIContName.value!=""){
		//strSQL = strSQL + " and a.RIContName like '%%"+fm.RIContName.value+"%%'" ;
	var mySql101=new SqlClass();
	 	mySql101.setResourceName("reinsure.ReContChioceInputSql"); //ָ��ʹ�õ�properties�ļ���
	  	mySql101.setSqlId("ReContChioceInputSql101");//ָ��ʹ�õ�Sql��id
	 	mySql101.addSubPara(fm.RIContName.value);
	  	strSQL=mySql101.getString();
	}
	
	//strSQL = strSQL +" order by RIContNo";
	/**
	var mySql102=new SqlClass();
	 	mySql102.setResourceName("reinsure.ReContChioceInputSql"); //ָ��ʹ�õ�properties�ļ���
	  	mySql102.setSqlId("ReContChioceInputSql102");//ָ��ʹ�õ�Sql��id
	 	mySql102.addSubPara(strSQL);
	  	strSQL=mySql102.getString();
	  	*/
	var arrResult = easyExecSql(strSQL);
	
	turnPage.queryModal(strSQL, ReContGrid);
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{

  //showInfo.close();
  if (FlagStr == "Fail" )
  {
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(content));
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  }
  else
  {
  }
}

//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm()
{
  try
  {
	  initForm();
  }
  catch(re)
  {
  	myAlert(""+"��Proposal.js"+"-->"+"resetForm�����з����쳣:��ʼ���������!"+"");
  }
}

//ȡ����ť��Ӧ����
function cancelForm()
{
    showDiv(operateButton,"true");
    showDiv(inputButton,"false");
}

//�ύǰ��У�顢����
function beforeSubmit()
{
  //��Ӳ���
}

//Click�¼������������ͼƬʱ�����ú���
function addClick()
{
  //����������Ӧ�Ĵ���
  showDiv(operateButton,"false");
  showDiv(inputButton,"true");
}

//Click�¼�����������޸ġ�ͼƬʱ�����ú���
function updateClick()
{
  //����������Ӧ�Ĵ���
  myAlert("update click");
}

//Click�¼������������ѯ��ͼƬʱ�����ú���
function queryClick()
{
  //����������Ӧ�Ĵ���
	myAlert("query click");
	//��ѯ���������һ��ģ̬�Ի��򣬲��ύ�������������ǲ�ͬ��
  //��ˣ����еĻ����Ҳ���Բ��ø�ֵ��
}

//Click�¼����������ɾ����ͼƬʱ�����ú���
function deleteClick()
{
  //����������Ӧ�Ĵ���
  myAlert("delete click");
}

//��ʾdiv����һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
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

//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,0,0,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}

	parent.fraMain.rows = "0,0,0,0,*";
}

function ReturnData()
{
	try{
		var tRow=ReContGrid.getSelNo();
  	if (tRow==0)
  	{
  		myAlert(""+"�����Ƚ���ѡ��!"+"");
  		return;
  	}
  	
		top.opener.fm.all('ModRIContNo').value 					=ReContGrid.getRowColData(tRow-1,1);
  	top.opener.fm.all('ModRIContName').value 				=ReContGrid.getRowColData(tRow-1,2);
  	top.close(); 
	}catch(ex){
		myAlert(""+"����ҳ��ʱ���ִ���"+"");
		return false;
	}
}

function ClosePage()
{
	top.close();
}
