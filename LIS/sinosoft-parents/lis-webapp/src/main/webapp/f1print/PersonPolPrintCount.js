//               该文件中包含客户端需要处理的函数和事件


var showInfo;
var turnPage = new turnPageClass();
var sqlresourcename = "f1print.PersonPolPrintCountInputSql";
//简单查询
function easyQueryClick()
{
	
	if( fm.ComCode.value!="86")
	{
		alert("只有总公司有这种权限，请返回!");	
		return false;	
		}
	// 初始化表格
	initCodeGrid();
	
	if(fm.StartDate.value=="" || fm.EndDate.value=="" )
	{
		alert("请输入统计核销时间");	
		return false;	
	}
   if(dateDiff(document.all('StartDate').value,document.all('EndDate').value,"D")>=15)
   {
        	if(!confirm("统计时间过长，系统可能会很慢，是否继续"))
        	{
        		return;
        	}
   } 
	// 书写SQL语句
	/*
	var strSQL = "select prtno,grpcontno as a ,managecom,signdate,signtime"
						 + " from lcgrpcont a "
						 + " where appflag = '1' and printcount=0"
						 + getWherePart('managecom', 'ManageCom','like','0') 
						 + getWherePart('signdate','StartDate','>=','0')
						 + getWherePart('signdate','EndDate','<=','0') 
						 +" union "
						 +" select prtno,contno as a ,managecom,signdate,signtime"
						 + " from lccont a "
						 + " where grpcontno = '00000000000000000000' and appflag = '1' and printcount=0 "
						 + getWherePart('managecom', 'ManageCom','like','0') 
						 + getWherePart('signdate','StartDate','>=','0')
						 + getWherePart('signdate','EndDate','<=','0') 
						 + "order by signdate,ManageCom,a ";
*/
var sqlid1="PersonPolPrintCountInputSql1";
var mySql1=new SqlClass();
mySql1.setResourceName(sqlresourcename);
mySql1.setSqlId(sqlid1);
mySql1.addSubPara(fm.ManageCom.value);
mySql1.addSubPara(fm.StartDate.value);
mySql1.addSubPara(fm.EndDate.value);
mySql1.addSubPara(fm.ManageCom.value);
mySql1.addSubPara(fm.StartDate.value);
mySql1.addSubPara(fm.EndDate.value);
	
	
	turnPage.queryModal(mySql1.getString(), CodeGrid);
 if(CodeGrid.mulLineCount<=0)
 {
 		alert("没有查询记录，请返回");	
		return false;	
 	}
}

function easyPrint()
{
	if( fm.ComCode.value!="86")
	{
		alert("只有总公司有这种权限，请返回!");	
		return false;	
		}
 if(CodeGrid.mulLineCount<=0)
 {
 		alert("没有查询记录，请返回");	
		return false;	
 	}
	easyQueryPrint(2,'CodeGrid','turnPage');
}

//显示div，第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
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
