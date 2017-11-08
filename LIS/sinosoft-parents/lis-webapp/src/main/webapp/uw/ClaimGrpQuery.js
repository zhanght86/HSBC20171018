//**************************************************************************************************
//文件名称：ClaimGrpQuery.js
//程序功能：承保处理-团体保单-人工核保-既往理赔查询响应界面，团体信息的显示，当点击此界面单选按钮
//          时，连接到（ui/uw/ClaimQueryMain.jsp）个险层面的赔案查询。
//创建日期：2006-11-08
//创建人  ：zhaorx
//更新记录：
//**************************************************************************************************

var turnPage = new turnPageClass();
//初始化查询
function initQuery()
{
	var sqlid826143908="DSHomeContSql826143908";
var mySql826143908=new SqlClass();
mySql826143908.setResourceName("uw.ClaimGrpQueryInputSql");//指定使用的properties文件名
mySql826143908.setSqlId(sqlid826143908);//指定使用的Sql的id
mySql826143908.addSubPara(fm.GrpAppntNo.value);//指定传入的参数
var strSQLA=mySql826143908.getString();
	
//	var strSQLA = " select g.rgtobjno,g.rgtno,r.rgtno,r.rgtdate,"
//	            + " (select d.name from ldperson d where d.customerno=c.customerno),"
//	            + " (select codename from ldcode where codetype='llclaimstate' and r.clmstate=code ), "
//	            + " c.customerno "
//	            + " from llgrpregister g,llregister r,llcase c where 1=1 and g.rgtno=r.rgtobjno " 
//	            + " and c.caseno=r.rgtno and exists "
//              + " (select 'X' from lcgrpcont c where g.rgtobjno=c.grpcontno and c.appntno='"+fm.GrpAppntNo.value+"') "
//              + " order by g.rgtobjno,g.rgtno,r.rgtno ";             
        
	  turnPage.pageLineNum = 10;    //每页10条
    turnPage.queryModal(strSQLA, LLClaimGrpQueryGrid);
}

//LLClaimGrpQueryGrid的响应函数
function ClaimQueryMainClick()
{
    var i = LLClaimGrpQueryGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        var tAppntNo  = LLClaimGrpQueryGrid.getRowColData(i,7);//出险人编码    
    }
    window.open("./ClaimQueryMain.jsp?CustomerNo="+tAppntNo,"window2","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

