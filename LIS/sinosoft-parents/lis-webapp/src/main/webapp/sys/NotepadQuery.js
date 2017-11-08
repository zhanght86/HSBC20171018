//程序名称：NotepadQuery.js
//程序功能：记事本查询
//创建日期：2005-09-29 16:10:36
//创建人  ：wuhao2
//更新记录：  更新人    更新日期     更新原因/内容

//               该文件中包含客户端需要处理的函数和事件

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


//触发显示文本内容
function QuestGridClick()
{

 var tNo = QuestGrid.getSelNo()-1;
 fm.Content.value =QuestGrid.getRowColData(tNo,2); 


}

//返回
function GoBack()
{
	top.close();
}





