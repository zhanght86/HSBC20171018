//该文件中包含客户端需要处理的函数和事件 

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var sqlresourcename = "bq.PEdorTypeHIInputSql";

function edorTypePTReturn()
{
		initForm();
}

function edorTypeHISave()
{ 
	document.all('fmtransact').value ="";  
    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面！";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.action = "./PEdorTypeHISubmit.jsp";
    fm.target = "fraSubmit";
	fm.submit();
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content,Result )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  else
  { 
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  
    ShowOtherContNoInfo();
  }
}

   
//---------------------------
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


function ShowOtherContNoInfo()
{

	var rCMSQL="";
	// modify by jiaqiangli 2009-04-13 0投1被(为保持一致，不再用IA表示)
	if(fm.CustomerRole.value=='1')	
	//if(fm.CustomerRole.value=='I')
	{
/*		 rCMSQL = " select ContNo   "
	        +"	 from lccont    "
	        +"	 where "
	        +"		insuredno = '"+fm.CustomerNo.value+"' and appflag='1'";
*/
	var sqlid1="PEdorTypeHIInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(fm.CustomerNo.value);//指定传入的参数
	rCMSQL=mySql1.getString();


		}else
			{
/*   rCMSQL = " select ContNo   "
	        +"	 from lccont    "
	        +"	 where "
	        +"	appflag='1' and 	appntno ='"+fm.CustomerNo.value+"'";  
*/    
    var sqlid2="PEdorTypeHIInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
	mySql2.addSubPara(fm.CustomerNo.value);//指定传入的参数
	rCMSQL=mySql2.getString();
     
     }
var result;
   try
  {
     result = easyExecSql(rCMSQL, 1, 0);
  }
  catch(ex)
  {
   alert("查询客户下保单信息异常！");
   return false;
  }
  if (result != null )
   {
   	 var tTempContNo="";
   	 for(var t=0;t<result.length;t++)
   	 {
   	 	   if(fm.ContNo.value!=result[t][0])
   	 	   {
   	 	   	 tTempContNo+=result[t][0]+",";
   	 	   	}
   	 }

   	 	if(tTempContNo!="")
   	 	{
   	 		tTempContNo=tTempContNo.substr(0,tTempContNo.lastIndexOf(","));
   	 		alert("增补告知，本客户下尚有如下保单"+tTempContNo+"未进行增补告知，请继续处理完这些保单");
   	 		}     
      return true;
   }			
	}
/*********************************************************************
 *  显示frmSubmit框架，用来调试
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showSubmitFrame(cDebug)
{
	if( cDebug == "1" )
		parent.fraMain.rows = "0,0,50,82,*";
	else 
		parent.fraMain.rows = "0,0,0,72,*";
}

/*********************************************************************
 *  查询返回明细信息时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
 *  参数  ：  查询返回的二维数组
 *  返回值：  无
 *********************************************************************
 */

function afterQuery( arrQueryResult )
{

}

function returnParent()
{
	top.opener.initEdorItemGrid();
	top.opener.getEdorItemGrid();
	top.close();
	top.opener.focus();
}

function personQuery()
{
    //window.open("./LCPolQuery.html");
//    window.open("./LPTypeIAPersonQuery.html");
    window.open("./LPTypeIAPersonQuery.html","",sFeatures);
}


function showNotePad()
{
	var MissionID = top.opener.document.all("MissionID").value;
	var SubMissionID = top.opener.document.all("SubMissionID").value;
	var ActivityID = "0000000003";
	var OtherNo = top.opener.document.all("OtherNo").value;

	var OtherNoType = "1";
	if(MissionID == null || MissionID == "")
	{
		alert("MissionID为空！");
		return;
	}		
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"工作流记事本查看","left");	
}

function selectContInfo()
{
/*		var strSql="SELECT distinct ContNo,"
								+ " case when exists(select 'X' from LCAppnt where ContNo=a.ContNo and AppntNo='" + document.all('CustomerNo').value + "') and exists(select 'X' from LCInsured where ContNo=a.ContNo and InsuredNo='" + document.all('CustomerNo').value + "') then '投保人,被保人'"
     								+ " when exists(select 'X' from LCAppnt where ContNo=a.ContNo and AppntNo='" + document.all('CustomerNo').value + "') then '投保人'"
     								+ " when exists(select 'X' from LCInsured where ContNo=a.ContNo and InsuredNo='" + document.all('CustomerNo').value + "') then '被保人'"
								+ " else '未知'"
								+ " end,"
								+ " (select Name from LDCom where ComCode=(select ManageCom from LCCont where ContNo=a.ContNo))"
								+ " FROM LPEdorItem a"
								+ " WHERE EdorAcceptNo='" + document.all('EdorAcceptNo').value + "' and EdorType='HI'"
								+ " ORDER BY ContNo";
*/
	var strSql="";
	var sqlid3="PEdorTypeHIInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql3.setSqlId(sqlid3);//指定使用的Sql的id
	mySql3.addSubPara(document.all('CustomerNo').value);//指定传入的参数
	mySql3.addSubPara(document.all('CustomerNo').value);
	mySql3.addSubPara(document.all('CustomerNo').value);
	mySql3.addSubPara(document.all('CustomerNo').value);
	mySql3.addSubPara(document.all('EdorAcceptNo').value);
	strSql=mySql3.getString();
		
		turnPage2.queryModal(strSql, ContGrid);
}


function Edorquery()
{
	var ButtonFlag = top.opener.document.all('ButtonFlag').value;
	if(ButtonFlag!=null && ButtonFlag=="1")
    {
       divEdorquery.style.display = "none";
    }
    else
 	{
 		divEdorquery.style.display = "";
 	}
}

//查询出客户角色
function initInpRole()
{
	  var tContNo=document.all('ContNo').value;
	  //判断其是否是投保人
//	  var sql = " select 1 from lcappnt where contno = '" + tContNo + "' and appntno ='"+document.all('CustomerNo').value+"'";
    
    var sql="";
	var sqlid4="PEdorTypeHIInputSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql4.setSqlId(sqlid4);//指定使用的Sql的id
	mySql4.addSubPara(tContNo);//指定传入的参数
	mySql4.addSubPara(document.all('CustomerNo').value);
	sql=mySql4.getString();
    
    var mrr = easyExecSql(sql);
    if ( mrr )
    {
        fm.CustomerRole.value="0";
    }

//    sql = " select 1 from lcinsured where contno = '" + tContNo + "' and insuredno ='"+document.all('CustomerNo').value+"'";
    
	var sqlid5="PEdorTypeHIInputSql5";
	var mySql5=new SqlClass();
	mySql5.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql5.setSqlId(sqlid5);//指定使用的Sql的id
	mySql5.addSubPara(tContNo);//指定传入的参数
	mySql5.addSubPara(document.all('CustomerNo').value);
	sql=mySql5.getString();
    
    mrr = easyExecSql(sql);
    if ( mrr )
    {
        fm.CustomerRole.value="1";
    }
//    var tsql = " select 1 from lcinsured where contno = '" + tContNo + "' and insuredno = (select appntno from lccont where contno = '" + tContNo + "')";
     
    var tsql = "";
    var sqlid6="PEdorTypeHIInputSql6";
	var mySql6=new SqlClass();
	mySql6.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql6.setSqlId(sqlid6);//指定使用的Sql的id
	mySql6.addSubPara(tContNo);//指定传入的参数
	mySql6.addSubPara(tContNo);
	tsql=mySql6.getString();
     
     mrr = easyExecSql(tsql);
    if ( mrr )
    {
        fm.CustomerRole.value="1";
    }
    //alert(fm.CustomerRole.value);
}

function InfoNoticePrint()
{
    var tContNo    = document.all('ContNo').value;
    var EdorAcceptNo    = document.all('EdorAcceptNo').value;
//    var strSQL = "select prtseq from loprtmanager where code = 'BQ27' and otherno = '"+EdorAcceptNo+"' and standbyflag1='"+tContNo+"'";
    
    var strSQL = "";
    var sqlid7="PEdorTypeHIInputSql7";
	var mySql7=new SqlClass();
	mySql7.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql7.setSqlId(sqlid7);//指定使用的Sql的id
	mySql7.addSubPara(EdorAcceptNo);//指定传入的参数
	mySql7.addSubPara(tContNo);
	strSQL=mySql7.getString();
    
    var sResult;
    sResult = easyExecSql(strSQL, 1, 0,"","",1);
    if(sResult == null){
        alert("查询保全补充告知信息失败,请先保存");
        return;
    }
    document.all('PrtSeq').value = sResult[0][0];
    fm.action = "./EdorNoticePrintSave.jsp?";
    fm.target = "f1print";
    fm.submit();
}