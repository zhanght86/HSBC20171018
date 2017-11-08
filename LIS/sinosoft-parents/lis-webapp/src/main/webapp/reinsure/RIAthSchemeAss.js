//该文件中包含客户端需要处理的函数和事件

//程序名称：RIAthSchemeAss.js
//程序功能：方案算法关联
//创建日期：2011/6/17
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容

var showInfo;
window.onfocus=myonfocus;
var turnPage = new turnPageClass();


//查询按钮
function button110( )
{
	var tRISolType = fm.RISolType.value;
	if(tRISolType=="01"||tRISolType=="02")
	{
		var strSQL = "select 1,AccumulateDefNO,AccumulateDefName,ArithmeticID,(select ArithmeticDefName from RICalDef b where a.ArithmeticID=b.arithmeticdefid)from RIAccumulateDef a where 1=1 "
			  + getWherePart("AccumulateDefNO","RIAccDefNo")
			  + getWherePart("AccumulateDefName","RIAccDefName")
			  + getWherePart("state","RIPreceptState")
		strSQL = strSQL +" order by AccumulateDefNO";
	}
	else
	{
		var strSQL = "select 1,a.Ripreceptno,a.Ripreceptname,a.Arithmeticid,(select ArithmeticDefName from RICalDef b where a.Arithmeticid=b.arithmeticdefid) from RIPrecept a  where  1=1 "
			  + getWherePart("Ripreceptno","RIRreceptNo")
			  + getWherePart("Ripreceptname","RIRreceptName")
			  + getWherePart("state","RIPreceptState")
		strSQL = strSQL +" order by Ripreceptno";		
	}
	 
	turnPage.queryModal(strSQL, Mul9Grid);
}

//关联方案按钮
function button109( )
{

}


//提交前的校验、计算  
function beforeSubmit()
{
  //添加操作 
}

//使得从该窗口弹出的窗口能够聚焦
function myonfocus()
{
 if(showInfo!=null)
 {
   try
   {
     showInfo.focus();  
   }
   catch(ex)
   {
     showInfo=null;
   }
 }
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(content));
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  }
  else
  { 
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+encodeURI(encodeURI(content));
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    showDiv(operateButton,"true"); 
    showDiv(inputButton,"false"); 
    //执行下一步操作
  }
}

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

function ClosePage(){
	top.close();
}
