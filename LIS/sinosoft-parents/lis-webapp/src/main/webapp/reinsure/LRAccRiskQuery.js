//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���

//�ύ�����水ť��Ӧ����
function submitForm()
{
  
}

function accQuery(){
	var mySql100=new SqlClass();
		mySql100.setResourceName("reinsure.LRAccRiskQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql100.setSqlId("LRAccRiskQuerySql100");//ָ��ʹ�õ�Sql��id
		mySql100.addSubPara(fm.AccumulateNo.value);//ָ������Ĳ���
	var tSQL=mySql100.getString();
	
	//var tSQL=" select a.AccumulateDefNO,(select AccumulateDefName from RIAccumulateDef where AccumulateDefNO=a.AccumulateDefNO),a.AssociatedCode,a.AssociatedName,a.StandbyFlag,(case a.StandbyFlag when '1' then 'GEB����' when '2' then '��GEB����' else '' end) from RIAccumulateRDCode a where AccumulateDefNO = '"+fm.AccumulateNo.value+"'";
	turnPage.queryModal(tSQL, AccumulateGrid);
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

function ClosePage()
{
	top.close();
}
