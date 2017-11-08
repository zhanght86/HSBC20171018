//该文件中包含客户端需要处理的函数和事件
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();  //使用翻页功能，必须建立为全局变量
var mySql = new SqlClass();


// 查询
function queryGrid()
{

   /* var strSQL = "";
	strSQL = "select a.rgtno,(Select customerno From llcase where caseno=a.rgtno),"
           + " (Case a.IssueConclusion When '01' Then '初审通过' When '02' Then '材料不齐退回' When '03' Then '其它退回' Else '不明' End),"
           + "a.makedate,a.IssueReplyConclusion,AutditNo From LLRegisterIssue a where 1=1 and IssueConclusion in ('02','03')"
*/
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLRegisterIssueInputSql");
	mySql.setSqlId("LLRegisterIssueSql1");
	mySql.addSubPara(fm.RptNo.value ); 
	if (fm.RgtNo.value != null && fm.RgtNo.value != "")
	{
		//strSQL=strSQL+"and a.rgtno='"+fm.RgtNo.value+"'";
		mySql = new SqlClass();
		mySql.setResourceName("claim.LLRegisterIssueInputSql");
		mySql.setSqlId("LLRegisterIssueSql2");
		mySql.addSubPara(fm.RgtNo.value ); 
	}
	
	turnPage.queryModal(mySql.getString(),LLIssueGrid);
}


function LLIssueGridClick()
{
    var i = LLIssueGrid.getSelNo();
    if (i != '0')
    {
    	i = i - 1;
    	var Clmno = LLIssueGrid.getRowColData(i,1);//赔付结论
    	var AutditNo = LLIssueGrid.getRowColData(i,6);//赔付结论
	   /* var strSQL = "";
		strSQL = "select a.rgtno,(Select customerno From llcase where caseno=a.rgtno),"
			   + " (Case a.IssueConclusion When '01' Then '初审通过' When '02' Then '材料不齐退回' When '03' Then '其它退回' Else '不明' End),"
			   + " a.IssueReason,a.IssueBackDate,a.IssueBacker,a.IssueReplyDate,a.IssueReplyer from LLRegisterIssue a where rgtno='" + Clmno + "' and AutditNo='" + AutditNo +"'";*/
       mySql = new SqlClass();
		mySql.setResourceName("claim.LLRegisterIssueInputSql");
		mySql.setSqlId("LLRegisterIssueSql3");
		mySql.addSubPara(Clmno); 
		mySql.addSubPara(AutditNo); 
        var IssueContent = easyExecSql(mySql.getString());
        
        fm.RgtNo1.value = IssueContent[0][0];
        fm.CustomerNo.value = IssueContent[0][1];
        fm.IssueConclusion.value = IssueContent[0][2];
        fm.IssueReason.value = IssueContent[0][3];
        fm.IssueBackDate.value = IssueContent[0][4];
        fm.IssueBacker.value = IssueContent[0][5];
        fm.IssueReplyDate.value = IssueContent[0][6];
        fm.IssueReplyer.value = IssueContent[0][7];
    }
}


