//***********************************************

//程序功能：核保查询

//***********************************************

//               该文件中包含客户端需要处理的函数和事件


//全局变量
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();

/*********************************************************************
 *  返回上一页
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */

function returnParent(){
  top.close();  
    
}


/*********************************************************************
 *  查询批单核保信息 
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */

function queryEdorMain() 
{
  
   var sqlid824100247="DSHomeContSql824100247";
var mySql824100247=new SqlClass();
mySql824100247.setResourceName("bq.EdorGrpUWQueryInputSql");//指定使用的properties文件名
mySql824100247.setSqlId(sqlid824100247);//指定使用的Sql的id
mySql824100247.addSubPara(EdorAcceptNo);//指定传入的参数
var strSql=mySql824100247.getString();
   
//   var strSql="select edorno,grpcontno,uwstate,"
//            + " (select trim(code)||'-'||trim(codename) from ldcode where codetype = 'edorgrpuwstate' and trim(code) = trim(uwstate) ),"
//            + " uwoperator,uwdate,uwtime "
//            + " from lpgrpedormain where edoracceptno='"+EdorAcceptNo+ "'";

    var crr = easyExecSql(strSql, 1, 0,"","",1);
    if (!crr)
    {
        //divEdorMainInfo.style.display='none';
        alert("保全申请下没有批单！");
        divPolInfo.style.display = 'none';
        return;
    }
    else
    {
        initEdorMainGrid();
        turnPage.queryModal(strSql, EdorMainGrid);
        divEdorMainInfo.style.display='';
        divPolInfo.style.display = 'none';
    }   
}

//显示保全险种信息
function  showRiskInfo()
{
    
    var tSelNo = EdorMainGrid.getSelNo()-1;
    var tEdorNo = EdorMainGrid.getRowColData(tSelNo,1); 
    var tContNo = EdorMainGrid.getRowColData(tSelNo,2); 
    var strSql ="" ;
    

    var sqlid824100400="DSHomeContSql824100400";
var mySql824100400=new SqlClass();
mySql824100400.setResourceName("bq.EdorGrpUWQueryInputSql");//指定使用的properties文件名
mySql824100400.setSqlId(sqlid824100400);//指定使用的Sql的id
mySql824100400.addSubPara(tEdorNo);//指定传入的参数
strSql=mySql824100400.getString();
    
//    strSql = "select uwno,appntname, "
//             + " (select trim(code)||'-'||trim(codename) from ldcode where codetype = 'edorgrpuwstate' and trim(code) = trim(passflag) ),uwidea,"
//             + " operator,makedate,maketime "
//             + " from lpguwsubmain a where 1=1"
//             + " and a.edorno='"+tEdorNo+"' and passflag in (select code from ldcode where codetype='edorgrpuwstate') order by makedate,maketime ";
    
        divPolInfo.style.display = '';
        initRiskGrid();                     
        turnPage1.queryModal(strSql, RiskGrid);

    
} 

function showPolUW()
{
	var tSelNo = RiskGrid.getSelNo()-1;
	var tEdorNo = RiskGrid.getRowColData(tSelNo,10);
	var tEdorType = RiskGrid.getRowColData(tSelNo,1);
	var tPolNo = RiskGrid.getRowColData(tSelNo,2);
	
	var sqlid824100546="DSHomeContSql824100546";
var mySql824100546=new SqlClass();
mySql824100546.setResourceName("bq.EdorGrpUWQueryInputSql");//指定使用的properties文件名
mySql824100546.setSqlId(sqlid824100546);//指定使用的Sql的id
mySql824100546.addSubPara(tEdorNo);//指定传入的参数
mySql824100546.addSubPara(tEdorType);//指定传入的参数
mySql824100546.addSubPara(tPolNo);//指定传入的参数
var strsql=mySql824100546.getString();
	
	
//	var strsql = "select uwidea from lpuwmaster where edorno = '"+tEdorNo+"' and edortype = '"+tEdorType+"' and polno = '"+tPolNo+"'";
	var brr = easyExecSql(strsql, 1, 0,"","",1);
	if(brr)
	{
		fm.PolUWIdea.value = brr[0][0];
	}
	divPolUWIdiea.style.display = '';
}