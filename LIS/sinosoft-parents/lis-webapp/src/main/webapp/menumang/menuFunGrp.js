//更新记录： queryClick(),fillUserGrid(),insertClick()
//更新人:  周磊
//更新日期:  2005-5-4  
//更新原因/内容：增加菜单插入时的逻辑判断。

var showInfo;

//控制界面上的mulLine的显示条数
var mulLineShowCount = 15;

var sqlcount = 0;

var selectArray = new Array();
var unselectArray = new Array();

var userArray = new Array();
var userArrayLen = 0; 
var userCurPage = 0;	

var removeArray = new Array() //更新菜单组中的删除纪录
var showDiv = "false";

var turnPage = new turnPageClass();   
var mySql = new SqlClass();
//查询并列出所有菜单记录
function queryClick()
{
	document.all("checkbox1").checked = false;
	document.all("checkbox2").checked = false;
	if(document.all("NodeName").value.length ==0){
	//	var sqlStr = "select nodecode,ChildFlag,nodename,parentnodecode,runscript,nodeorder,nodesign from LDMenu order by nodeorder";  
	
	mySql = new SqlClass();
	mySql.setResourceName("menumang.menuFunManSql");
	mySql.setSqlId("menuFunManSql1");
	mySql.addSubPara(""); 
	
	}else{
		/*var sqlStr = "select nodecode,ChildFlag,nodename,parentnodecode,runscript,nodeorder,nodesign from LDMenu "
		           + " where nodename like  '%%"+ document.all("NodeName").value +"%%' "
				   + " order by nodeorder";*/
	mySql = new SqlClass();
	mySql.setResourceName("menumang.menuFunManSql");
	mySql.setSqlId("menuFunManSql2");
	mySql.addSubPara(document.all("NodeName").value ); 
	
	}
	var sqlStr=mySql.getString();
	turnPage.queryModal(sqlStr, QueryGrpGrid,1); 
}  

function fillUserGrid()
{
   QueryGrpGrid.clearData("QueryGrpGrid");

   for (var i = 0; i < mulLineShowCount; i++) {

        QueryGrpGrid.addOne("QueryGrpGrid");
   	var offset = i  + userCurPage*mulLineShowCount;

   	if (offset < userArrayLen) {
   	    QueryGrpGrid.setRowColData(i,1,userArray[offset][0]);
   	    QueryGrpGrid.setRowColData(i,2,userArray[offset][1]);
   	    QueryGrpGrid.setRowColData(i,3,userArray[offset][3]);
   	    QueryGrpGrid.setRowColData(i,4,userArray[offset][2]);
   	    QueryGrpGrid.setRowColData(i,5,userArray[offset][4]);
   	    QueryGrpGrid.setRowColData(i,6,userArray[offset][5]);
   	    QueryGrpGrid.setRowColData(i,7,userArray[offset][6]);//菜单标志
//   	    alert(userArray[offset][4]);
   	} else {
   	    QueryGrpGrid.setRowColData(i,1,"");
   	    QueryGrpGrid.setRowColData(i,2,"");
        QueryGrpGrid.setRowColData(i,3,"");
   	    QueryGrpGrid.setRowColData(i,4,"");
   	    QueryGrpGrid.setRowColData(i,5,"");
   	    QueryGrpGrid.setRowColData(i,6,"");
   	    QueryGrpGrid.setRowColData(i,7,"");//菜单标志
   	}  	        
   }

   //下面的代码是为了使翻页时序号能正确显示
   for (var i = 0; i < mulLineShowCount; i++) {
		var offset = i  + userCurPage*mulLineShowCount;
        document.all("QueryGrpGridNo")[i].value = offset + 1;
   }
		
}

function userFirstPage()
{
	turnPage.firstPage();
	/*if (userArrayLen == 0)
	    return;
	    
	userCurPage = 0;
	fillUserGrid();
	*/
}

function userLastPage()
{
	turnPage.lastPage();
	/*
	if (userArrayLen == 0)
	    return;
	    
	while ((userCurPage + 1)*mulLineShowCount < userArrayLen)
	    userCurPage++;

	fillUserGrid();
	*/
}


function userPageDown()
{
	turnPage.nextPage();
	/*
	if (userArrayLen == 0)
	    return;
	    
    if (userArrayLen <= (userCurPage + 1) * mulLineShowCount) {
    	alert("已达尾页");
    } else {
        userCurPage++;
        fillUserGrid();
    }
    */
}

function userPageUp()
{
	turnPage.previousPage();
	/*
	if (userArrayLen == 0)
	    return;

    if (userCurPage == 0) {
    	alert("已到首页");
    } else {
        userCurPage--;
        fillUserGrid();
    }
    */
}

function submitForm()
{
	//alert('document.all("isChild").value:'+document.all("isChild").value);
  var i = 0;
  var showStr="正在提交数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  document.getElementById("fm").submit(); //提交
  document.all("RunScript").value ="";
  //queryClick();
}

function insertClick()
{
	//取得菜单标志
	var selMenuGrpNo = QueryGrpGrid.getSelNo();
	if(selMenuGrpNo<1)
	{
		alert('请选择一条记录!');
		return false;
	}
	var selMenu = selMenuGrpNo - 1;
	var NodeSign = QueryGrpGrid.getRowColData(selMenu,7);
	var RunScript = QueryGrpGrid.getRowColData(selMenu,5);
	//alert(NodeSign);
  //alert(RunScript);
 	if (document.all("NodeName").value == ""){
 		alert ("请输入菜单节点名称!");
 	}
 	//排除页面功能菜单同级插入到一般菜单2005
	else 
	{
		if (NodeSign != 2 && document.all("checkbox2").checked == true){
			if(document.all("checkbox1").checked == false){
				alert("页面功能菜单不能同级插入到一般菜单,请重新选择！");
				}
			else {
				//判断是否末级菜单
				if(NodeSign != 1 && RunScript==null){
					alert("页面功能菜单不能下级插入到非叶子菜单,请重新选择！");
					}
				else{
					document.all("Action").value = "insert";   
				   //作为子菜单插入(不选则按照同级菜单插入)     	
				   if(document.all("checkbox1").checked == true) 
				      {     	
				      	document.all("isChild").value = "true";
				      }      
				   else
				      {document.all("isChild").value = "false";}
				   //是否作为页面权限菜单插入 2005  	
				   if(document.all("checkbox2").checked == true) 
				      {     	
				      	document.all("isChild2").value = "true";
				      }      
				   else
				      {document.all("isChild2").value = "false";}            
				   submitForm();
					}					
				}
			}
			else {
				if (NodeSign == 2 && document.all("checkbox1").checked == true){
					alert("页面功能菜单不能添加子菜单,请重新选择！");
					}
				else{		 
				   document.all("Action").value = "insert";   
				   //作为子菜单插入(不选则按照同级菜单插入)     	
				   if(document.all("checkbox1").checked == true) 
				      {     	
				      	document.all("isChild").value = "true";
				      }      
				   else
				      {document.all("isChild").value = "false";}
				   //是否作为页面权限菜单插入 2005  	
				   if(document.all("checkbox2").checked == true) 
				      {     	
				      	document.all("isChild2").value = "true";
				      }      
				   else
				      {document.all("isChild2").value = "false";}            
				   submitForm();
			    }
				}
  }
}

function deleteClick() 
{
	var selMenuGrpNo = QueryGrpGrid.getSelNo();
        if (selMenuGrpNo == 0) {
	  alert("您还没有选择需要删除的菜单");
	  return;
	}    	
	if (!confirm("您确实要删除这个菜单吗？"))
	  return;
	
	document.all("Action").value = "delete";

    submitForm();    	
}

function afterSubmit(FlagStr)
{
    showInfo.close();	
       
    if (document.all('Action').value == "insert") {
        if (FlagStr == "success")
            alert("增加菜单成功！");
        else
            alert("增加菜单失败，可能的原因是此菜单已存在");
    }
    
    if (document.all('Action').value == "delete") {
        if (FlagStr == "success")
            alert("删除菜单成功！");
        else
            alert("删除菜单失败,原因可能是不能删除有子菜单的菜单!");
    }		
    queryClick();
}







