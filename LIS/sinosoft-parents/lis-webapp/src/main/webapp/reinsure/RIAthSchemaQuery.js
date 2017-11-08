//该文件中包含客户端需要处理的函数和事件

//程序名称：RIAthSchemaQuery.js
//程序功能：算法方案查询
//创建日期：2011/6/17
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容

var showInfo;
window.onfocus=myonfocus;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();

//查询按钮
function button106( )
{
  var strSQL = "select ArithmeticDefID,ArithmeticDefName," 
	  + "(select codename from ldcode where codetype = 'risolutiontype' and code = ArithType),ArithType ," 
	  + "(select codename from ldcode where codetype = 'ristate' and code = state) from RICalDef where 1=1 "		
	  + getWherePart("ArithmeticDefID","ArithmeticDefID")
	  + getWherePart("ArithmeticDefName","ArithmeticDefName")
	  + getWherePart("ArithType","RISolType");
  
  strSQL = strSQL +" order by ArithType,ArithmeticDefID ";
  
  turnPage.queryModal(strSQL, Mul10Grid)
}


//返回按钮
function button105( )
{
  var tRow=Mul10Grid.getSelNo();
  	if (tRow==0){
   		myAlert(""+"请您先进行选择!"+"");
  		return;
  	}
  	var strSQL = "select ArithmeticDefID,ArithmeticDefName,ArithType,(select codename from ldcode a where a.codetype = 'risolutiontype' and code = ArithType),state,(select codename from ldcode where codetype = 'ristate' and code = state)  from RICalDef where 1=1 "
    +" and ArithmeticDefID= '"+Mul10Grid.getRowColData(tRow-1,1)+"'";
  	strArray = easyExecSql(strSQL);
  	
  	if (strArray==null)
  	{
  		myAlert(""+"无法返回"+","+"该数据可能刚被删除!"+"");
  		return false;
  	}
  	var deTailFlag=strArray[0][2];
    top.opener.fm.all("ArithmeticDefID").value 		=strArray[0][0];
    top.opener.fm.all("ArithmeticDefName").value 	=strArray[0][1];
    top.opener.fm.all("RISolType").value 			=strArray[0][2];
    top.opener.fm.all("RISolTypeName").value 		=strArray[0][3];
    top.opener.fm.all("SchemaState").value 		    =strArray[0][4];
    top.opener.fm.all("SchemaStateName").value      =strArray[0][5];

	var tRow=Mul10Grid.getSelNo();
  	if (tRow>0)
  	{
  		var strSQL ="";
  		//数据采集
  		if(strArray[0][2]=="01"||strArray[0][2]=="02")
  		{
  	  		strSQL = "select a.ArithmeticDefID,a.ArithmeticDefName,"
  				+" (select codename from ldcode where codetype = 'risolutiontype' and code = a.ArithType),"
  				+"  b.ArithmeticID,b.calitemname,"			
  				+" (select codename from ldcode where codetype = 'riathbstype' and code =b.calitemid),"		
  				+" '"+"有效"+"' from RICalDef a, RIItemCal b where 1=1 "
  				+" and a.ArithmeticDefID = b.ArithmeticDefID "
  				+" and a.ArithmeticDefID = '"+Mul10Grid.getRowColData(tRow-1,1)+"'";
  		}
  		else
  		{
  	  		strSQL = "select distinct a.ArithmeticDefID,a.ArithmeticDefName,"
  				+" (select codename from ldcode where codetype = 'risolutiontype' and code = a.ArithType),"
  				+"  b.ArithmeticID,b.calitemname,"			
  				+" (select codename from ldcode where codetype = 'riatheventype' and code =b.Arithmetictype),"		
  				+" '"+"有效"+"',Arithmetictype,Calitemorder from RICalDef a, RIItemCal b where 1=1 "
  				+" and a.ArithmeticDefID = b.ArithmeticDefID "
  				+" and a.ArithmeticDefID = '"+Mul10Grid.getRowColData(tRow-1,1)+"' order by b.Arithmetictype,b.calitemorder";  			
  		}
  		
		strArray = easyExecSql(strSQL);
		top.opener.Mul10Grid.clearData();	
		top.opener.turnPage.queryModal(strSQL,top.opener.Mul10Grid);
  	}	
    top.close();
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

