//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
//window.onfocus=myonfocus;
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var sqlresourcename = "logmanage.LogComponentSql";
//�ύ�����水ť��Ӧ����

//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function DateReset()
{
  try
  {
	  initInpBox();
  }
  catch(re)
  {
  	alert("LogComponent.js-->resetForm�����з����쳣:��ʼ���������!");
  }
}

function ClickQuery()
{
	// ��ʼ�����
	initLogStateGrid();
    initLogTrackGrid();

	// ��дSQL���
	var mySql=new SqlClass();
	    mySql.setResourceName(sqlresourcename);
	    mySql.setSqlId("LogComponentSql2");
	    mySql.addSubPara(fm.SubjectID.value);
	    mySql.addSubPara(fm.ItemID.value);
	    mySql.addSubPara(fm.KeyNO.value);

	turnPage.queryModal(mySql.getString(), LogStateGrid);
	
	var mySql2=new SqlClass();
	    mySql2.setResourceName(sqlresourcename);
	    mySql2.setSqlId("LogComponentSql3");
	    mySql2.addSubPara(fm.SubjectID.value);
	    mySql2.addSubPara(fm.ItemID.value);
	    mySql2.addSubPara(fm.KeyNO.value);

	turnPage1.queryModal(mySql2.getString(), LogTrackGrid);
	if(LogStateGrid.mulLineCount <= 0 && LogTrackGrid.mulLineCount <= 0){
		alert("û�з�����������־���ݣ�");
		return false;
	}

}

