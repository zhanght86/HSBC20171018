//该文件中包含客户端需要处理的函数和事件

//程序名称：RIAccFeatures.js
//程序功能：分出责任定义-分保特性
//创建日期：2011/6/16
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容

var showInfo;
window.onfocus=myonfocus;
var turnPage = new turnPageClass();


//关联主险按钮
function button102( )
{

}


//提交前的校验、计算  
function beforeSubmit()
{
  //添加操作 
}

function initData()
{

	var tSQL = "select a.StandbyFlag,(select AssociatedName from RIAccumulateRDCode where Associatedcode = a.StandbyFlag and rownum = 1)," 
		+ " b.StandbyFlag ,(select GetDutyName from RIAccumulateGetDuty where GetDutyCode = b.StandbyFlag and rownum = 1)" 
		+ " from RIAccumulateRDCode a,RIAccumulateGetDuty b " 
		+ " where a.Accumulatedefno = b.Accumulatedefno and a.Associatedcode = b.Associatedcode and a.Accumulatedefno ='"+fm.AccDefNo.value
		+ "' and a.Associatedcode ='"+fm.RIRiskCode.value
		+ "' and b.GetDutyCode = '"+fm.RIDutyCode.value+"'";
	
	var strArray = easyExecSql(tSQL);
	
	if (strArray!=null)
	{			
		for (var k=0;k<strArray.length;k++)
		{
			Mul10Grid.addOne("Mul10Grid");
			Mul10Grid.setRowColData(k,1,strArray[k][0]);
			Mul10Grid.setRowColData(k,2,strArray[k][1]);
			Mul10Grid.setRowColData(k,3,strArray[k][2]);
			Mul10Grid.setRowColData(k,4,strArray[k][3]);
		}
	} 	
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

function afterCodeSelect(codeName,Field)
{ 
	if(codeName=="ririskfeature")
	{
		if(Field.value=="01")
		{
			divFeature01.style.display='';
			divFeature02.style.display='none';
		}
		else if(Field.value=="02")
		{
			divFeature02.style.display='';
			divFeature01.style.display='none';
		}
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

