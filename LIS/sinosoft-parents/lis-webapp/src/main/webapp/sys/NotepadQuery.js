//�������ƣ�NotepadQuery.js
//�����ܣ����±���ѯ
//�������ڣ�2005-09-29 16:10:36
//������  ��wuhao2
//���¼�¼��  ������    ��������     ����ԭ��/����

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var tPolNO = "";
var turnPage = new turnPageClass();
var mySql = new SqlClass();
function LLQuestGrid()
{   
	
   var strSQL = "";
   /*strSQL = " select P.OtherNo,P.NotePadCont, D.ActivityName, P.Operator, P.MakeDate, P.MakeTime " 
				 + " from lwnotepad P, lwactivity D" 
				 + " where D.ActivityID = P.ActivityID "
         + " and P.OtherNo='"+fm.all('PrtNo').value+"'"
         + " order by P.OtherNo";*/
         mySql = new SqlClass();
		mySql.setResourceName("sys.NotepadQuerySql");
		mySql.setSqlId("NotepadQuerySql1");
		mySql.addSubPara(document.all('PrtNo').value); 
//   var arr= easyExecSql(strSQL);
//    if(arr)
//    {
//        displayMultiline(arr,SpecGrid);
//    }

	turnPage.queryModal(mySql.getString(), QuestGrid);
}


//������ʾ�ı�����
function QuestGridClick()
{

 var tNo = QuestGrid.getSelNo()-1;
 fm.Content.value =QuestGrid.getRowColData(tNo,2); 


}

//����
function GoBack()
{
	top.close();
}





