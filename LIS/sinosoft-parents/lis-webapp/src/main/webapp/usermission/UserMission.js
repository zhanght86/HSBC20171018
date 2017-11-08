// sunsheng 20110527 该文件中包含客户端需要处理的函数和事件
//jiyongtian  取得缓存帧

var win = searchMainWindow(this);
if (win == false) { win = this; }
 mCs=win.parent.VD.gVSwitch;
if(typeof(mCs)!="object")setTimeout("mCs=win.parent.VD.gVSwitch",100);


var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();


var NodeCode = 0;
var count=0;
var arrObject = new Array();  //用于存储菜单层节点
var page=1;
//var Opened = false;
/**
function easyQueryClickSelf()
{   
	// 初始化表格
	//initSelfGrpGrid();
	//var sqlid="QueryMenuPageSql1"; //指定properties文件中sql
	//var mySql=new SqlClass();
	//mySql.setResourceName("app.QueryMenuPageSql"); //指定使用的properties文件名
	//mySql.setSqlId(sqlid);//指定使用的Sql的id
	//mySql.addSubPara(operator);//指定传入的参数
	//strSQL=mySql.getString(); 
	  var arr=new Array();
	  var strSQL = "select distinct m.defaultoperator,(select count(1) from lwmission m where (case when  m.priorityid > m.SQLPriorityID then m.priorityid else m.SQLPriorityID end)='1' and  m.defaultoperator = '"+operator+"'),(select count(1) from lwmission m where (case when  m.priorityid > m.SQLPriorityID then m.priorityid else m.SQLPriorityID end)='2' and  m.defaultoperator = '"+operator+"'),(select count(1) from lwmission m where (case when  m.priorityid > m.SQLPriorityID then m.priorityid else m.SQLPriorityID end)='3' and  m.defaultoperator = '"+operator+"'),(select count(1) from lwmission m where (case when  m.priorityid > m.SQLPriorityID then m.priorityid else m.SQLPriorityID end)='4' and  m.defaultoperator = '"+operator+"') from lwmission m where m.defaultoperator = '"+operator+"'";
	  initSelfGrpGrid();
	  turnPage.queryModal(strSQL, SelfGrpGrid);
	  return true;
}
*/
//jiyongtian   个人工作池查询
function easyQueryClickOneself()
{   
	// 初始化表格
	initOneselfGrpGrid();
	var sqlid="QueryMenuPageSql3"; //指定properties文件中sql
	var mySql=new SqlClass();
	mySql.setResourceName("app.QueryMenuPageSql"); //指定使用的properties文件名
	mySql.setSqlId(sqlid);//指定使用的Sql的id
	mySql.addSubPara(operator);//指定传入的参数
	mySql.addSubPara(manageCom);//指定传入的参数
	strSQL=mySql.getString();
	turnPage.queryModal(strSQL, OneselfGrpGrid);
	return true;
}
//jiyongtian  公共工作池查询
function easyQueryClickPublic()
{   
	// 初始化表格
	initPublicGrpGrid();
	var sqlid="QueryMenuPageSql4"; //指定properties文件中sql
	var mySql=new SqlClass();
	mySql.setResourceName("app.QueryMenuPageSql"); //指定使用的properties文件名
	mySql.setSqlId(sqlid);//指定使用的Sql的id
	mySql.addSubPara(operator);//指定传入的参数
	mySql.addSubPara(manageCom);//指定传入的参数
	strSQL=mySql.getString();
	turnPage2.queryModal(strSQL, PublicGrpGrid);
	return true;
}
//个人工作池 选中一条记录调用的函数 jiyongtian
function QueryMenuInitOneSelf()
{  
     var tFunctionID = OneselfGrpGrid.getRowColData(OneselfGrpGrid.getSelNo() - 1, 3);	 
	 if (tFunctionID == null || tFunctionID == '')
	 {
		 alert("请先选择一条任务信息！");
		 return false;
	 }
	 var missionid=OneselfGrpGrid.getRowColData(OneselfGrpGrid.getSelNo() - 1, 9);
	 var submissionid=OneselfGrpGrid.getRowColData(OneselfGrpGrid.getSelNo() - 1, 10);
	 mCs.addVar("MissionID","",missionid);
	 mCs.addVar("SubMissionID","",submissionid);
	 mCs.addVar("ActivityID","",tFunctionID);
	 var sqlid="QueryMenuPageSql5"; //指定properties文件中sql
	 var mySql=new SqlClass();
	 mySql.setResourceName("app.QueryMenuPageSql"); //指定使用的properties文件名
	 mySql.setSqlId(sqlid);//指定使用的Sql的id
	 mySql.addSubPara(operator);//指定传入的参数
	 mySql.addSubPara(tFunctionID);//指定传入的参数
	 strSQL=mySql.getString();
	 var strArray = easyExecSql(strSQL);
	 if(strArray == null)
	 {
		 alert("根据功能名称查询菜单节点失败！");
		 return false;
	 }
	 var runscript= strArray[0][0];
	 var nodecode = strArray[0][1];
	 var parentnode = strArray[0][2];
	 
	  NodeCode = nodecode;
	  arrObject[0] = nodecode;//当前节点
	  arrObject[1] = parentnode; //父节点
	  count = 1;
	  openMenuLayer(parentnode);
	  window.location=runscript; 		  		 
}
//公共工作池 选中一条记录调用的函数 jiyongtian
function QueryMenuInitPublic()
{
	 if (PublicGrpGrid.getSelNo() == 0)
	 {
		    alert("根据功能名称查询菜单节点失败！");
		    return false;
      } 	
	 var tFunctionIDPublic = OneselfGrpGrid.getRowColData(PublicGrpGrid.getSelNo() - 1, 3);
	 var sqlid="QueryMenuPageSql5"; //指定properties文件中sql
	 var mySql=new SqlClass();
	 mySql.setResourceName("app.QueryMenuPageSql"); //指定使用的properties文件名
	 mySql.setSqlId(sqlid);//指定使用的Sql的id
	 mySql.addSubPara(operator);//指定传入的参数
	 mySql.addSubPara(tFunctionIDPublic);//指定传入的参数
	 strSQL=mySql.getString();
	 var strArray = easyExecSql(strSQL);
	 if(strArray == null)
	 {
		 alert("查询菜单节点失败！");
		 return false;
	 }
	 var runscript= strArray[0][0];
	 var nodecode = strArray[0][1];
	 var parentnode = strArray[0][2];
	  NodeCode = nodecode;
	  arrObject[0] = nodecode;//当前节点
	  arrObject[1] = parentnode; //父节点
	  count = 1;
	  openMenuLayer(parentnode);
	  window.location=runscript;	  		 
}
/* jiyongtian 2012-6-11 暂时没有用到
function initMenu() { //点击mulLine某行触发的方法
  if (SelfGrpGrid.getSelNo() === 0) {
    alert("请先选择一条任务信息！");
    return false;
  } 
  var polNo = SelfGrpGrid.getRowColData(SelfGrpGrid.getSelNo() - 1, 1);
  var prtNo = SelfGrpGrid.getRowColData(SelfGrpGrid.getSelNo() - 1, 2);
  var cMissionID=SelfGrpGrid.getRowColData(SelfGrpGrid.getSelNo() - 1, 3);
  var runscript=SelfGrpGrid.getRowColData(SelfGrpGrid.getSelNo() - 1, 4);
  var nodecode = SelfGrpGrid.getRowColData(SelfGrpGrid.getSelNo() - 1, 5);
  var parentnode = SelfGrpGrid.getRowColData(SelfGrpGrid.getSelNo() - 1, 6);
  NodeCode = nodecode;
  arrObject[0] = nodecode;//当前节点
  arrObject[1] = parentnode; //父节点
  count = 1;
  // alert("runscript==="+parentnode);
  openMenuLayer(parentnode);
 // Opened = true;
  window.location=runscript; 
  //fm + ".all('" + instanceName + 1 + "')[" + 1 + "].style.backgroundColor ='#EE82EE'"; 		
}
*/
//查询出触发菜单的节点，从父节点到子节点逐层展开
function openMenuLayer(sParentNode){
	//alert("sParentNode=="+sParentNode);
	if(sParentNode=="0"){
		for(var i=arrObject.length-1;i>=0;i--){	//遍历菜单节点，从父节点到子节点逐层展开
			var nodesrc = parent.fraMenu.window.document.getElementsByName(arrObject[i]); //菜单link地址链接
			//var nodeicon = parent.fraMenu.window.document.getElementsByName("s"+arrObject[i]);//菜单节点图片链接
			
			//var iconsrc = nodeicon[0].src;

		//	alert(nodesrc.isExpand);
			//var icontype = iconsrc.substring(iconsrc.indexOf("images/",0)+7,iconsrc.length);//对应菜单图片名称
			try{
			var usrc = nodesrc[0];	
			//if(icontype=="folderopen_blue.png"){//通过菜单图片判断该图片是否已打开
			if(nodesrc.isExpand){//通过菜单图片判断该图片是否已打开
				continue;
			}else{
  				usrc.click();
  			}
  		}
  		catch(e)
  		{
  			}
		}
  	}else{
  		var sqlid="QueryMenuPageSql2";
		var mySql=new SqlClass();
		mySql.setResourceName("app.QueryMenuPageSql"); //指定使用的properties文件名
		mySql.setSqlId(sqlid);//指定使用的Sql的id
		mySql.addSubPara(sParentNode);//指定传入的参数
		strSQL=mySql.getString();
		var strArray = easyExecSql(strSQL );
		sParentNode = strArray[0];
		if(strArray[0]!=0){		
		count = count+1;
		arrObject[count] = strArray[0];//arrObject数组用于存储节点
		}
  		 openMenuLayer(sParentNode);
  	}
}
function initPersonalWorkPool()
{  
	var sqlid="QueryMenuPageSql0"; //指定properties文件中sql
	var mySql=new SqlClass();
	mySql.setResourceName("app.QueryMenuPageSql"); //指定使用的properties文件名
	mySql.setSqlId(sqlid);//指定使用的Sql的id
	mySql.addSubPara(operator);//指定传入的参数
	mySql.addSubPara(operator);//指定传入的参数
//	mySql.addSubPara(manageCom);//指定传入的参数
	strSQL=mySql.getString();
	 var strArray = easyExecSql(strSQL);
	 if(strArray == null)
	 {
		 alert("查询用户即工作池信息出错！");
		 return false;
	 }
	 fm.UserName.value = strArray[0][0];

	 for ( var i = 0 ;i< strArray.length;i++ )
	 {   
		 var count = strArray[i][2];
		 if(count == '1')
		 {
			 fm.ExtendA.value = strArray[i][1] +"       件";
	     }
		 if(count == '2')
		 {
			 fm.EmergencyA.value = strArray[i][1] +"       件";
	     }
		 if(count == '3')
		 {
			 fm.SpeedUpA.value = strArray[i][1] +"       件";
		 }
		 if(count == '4')
		 {
			 fm.StandardA.value = strArray[i][1] +"       件";
		 }
	 }
	 return true;
}
