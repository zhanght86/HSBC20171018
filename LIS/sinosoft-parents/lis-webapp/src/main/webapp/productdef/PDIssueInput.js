//绋嬪簭鍚嶇О锛歅DIssueInput.js
//绋嬪簭鍔熻兘锛氶棶棰樹欢褰曞叆
//鍒涘缓鏃ユ湡锛?009-4-2
//璇ユ枃浠朵腑鍖呭惈瀹㈡埛绔渶瑕佸鐞嗙殑鍑芥暟鍜屼簨浠?
var turnPage = new turnPageClass();
var showInfo;
//瀹氫箟sql閰嶇疆鏂囦欢
var tResourceName = "productdef.PDIssueInputInputSql";
function submitForm()
{
  var showStr=""+"正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  
  fm.submit();
}

function afterSubmit( FlagStr, content )
{
  showInfo.close();

  if (FlagStr == "Fail" )
  {             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  }
  else
  {
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    initForm();    
  } 
}
var Mulline9GridTurnPage = new turnPageClass(); 
function ins()
{
	if(!verifyInput2()){
		return false;
	}
	
	//if( fm.all('PostCode').value == fm.all('BackPost').value )
	//{
	//	alert("涓嶈兘灏嗛棶棰樹欢鍙戦?缁欒嚜宸憋紒");
	//	return;
	//}
	
	var showStr=""+"正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
    showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  
    fm.all("operator").value="insert";
    //fm.all('IssueType').value = '0';
  	fm.submit();
}
function del()
{
	var selNo = Mulline9Grid.getSelNo();
	if( selNo == 0 || selNo == null )
	{
		myAlert(""+"请选择需要删除的问题件"+" "+"！"+"");
		return;
	}
	
	var tIssuestate = Mulline9Grid.getRowColData(selNo-1,4);
	if(tIssuestate!="0"){
		myAlert(""+"只有新增的问题件可以删除"+","+"已经发送或者回复的不可以再删除"+"");
		return;
	}
	
	var showStr=""+"正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  
  fm.all('SerialNo').value = Mulline9Grid.getRowColData(selNo-1,1);
 	fm.all("operator").value="delete";
 	fm.submit();
}
function DealIssue()
{
	//PostCode搴斿綋鏄?涓殑MulLine涓殑鍊?
    var selNo = Mulline9Grid.getSelNo();
    if( selNo == 0 )
    {
    	myAlert(""+"请选择一条问题件记录"+"");
    	return;
    }
    // 鍒ゆ柇闂浠剁姸鎬?
    if( Mulline9Grid.getRowColData(selNo-1,4) != '0' )
    {
    	myAlert(""+"该问题件已发送！"+"");
    	return;
    }
    //濡傛灉宸叉湁鍙戦?浜嗗埌01鑺傜偣鐨勯棶棰樹欢锛屽垯涓嶈兘鍐嶅彂閫佸埌00鑺傜偣鐨勶紝鍙嶄箣浜︾劧
    var mbackPost = ""; //宸插彂閫佺殑宀椾綅
    for( var i=1;i<=Mulline9Grid.mulLineCount;i++ )
    {
    	if( Mulline9Grid.getRowColData(i-1,4) == '1' )
    	{
    		mbackPost = Mulline9Grid.getRowColData(i-1,2);
    		break;
    	}
    }
    var nBackPost = Mulline9Grid.getRowColData(selNo-1,2);// 寰呭彂閫佺殑宀椾綅
    
    if( mbackPost != "" )
    {
    	mbackPost = mbackPost.substring(0,1);
    	nBackPost = nBackPost.substring(0,1);
    	
    	if( mbackPost != nBackPost )
    	{
    		myAlert(""+"返回岗位不能交叉！"+"");
    		return;
    	}
    }
    
    fm.SerialNo.value = Mulline9Grid.getRowColData(selNo-1,1);
    fm.BackPost.value = nBackPost;
	var showStr=""+"正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
	
	fm.all("operator").value="deal";
	fm.submit();
}
function SendIssue2(FlagStr, content )
{
	//showInfo.close();
	var selNo = Mulline9Grid.getSelNo();
    fm.all('PostCode').value = Mulline9Grid.getRowColData(selNo-1,2);
	
  	if (FlagStr == "Fail" )
  	{             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+content;
    	showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    	return;
    }
	
	//top.opener?? 璇ラ〉闈㈢殑opener澶氫簡鍘讳簡锛屽苟涓嶄粎浠呮槸浜у搧娴嬭瘯涓庡彂甯冮〉闈DTestDeployInput.jsp
	if(fm.all('PostCode').value=='00'){
		//鐩存帴璋冪敤宸ヤ綔娴佹彁浜?
		//top.opener.baseInfoDone();
		baseInfoDone();
	}else if(fm.all('PostCode').value=='10'){
		//鐩存帴璋冪敤宸ヤ綔娴佹彁浜?
		//top.opener.button119();
		button119();
	}else if(fm.all('PostCode').value=='12'){
		//鐩存帴璋冪敤宸ヤ綔娴佹彁浜?
		//top.opener.btnClaimDone();
		btnClaimDone();
	}else if(fm.all('PostCode').value=='11'){
		//鐩存帴璋冪敤宸ヤ綔娴佹彁浜?
		//top.opener.btnEdorDone();
		btnEdorDone();
	}else if(fm.all('PostCode').value=='20'){
		//top.opener.btnAuditDone();
		myAlert(""+"错误的问题件岗位流向"+"");
	}else if(fm.all('PostCode').value=='30'){
		//top.opener.btnDeployIssue();
		myAlert(""+"错误的问题件岗位流向"+"");		
	}else if(fm.all('PostCode').value=='40'){
		//top.opener.btnModifyIssue();
		myAlert(""+"错误的问题件岗位流向"+"");		
	}
	top.close();
}

function queryMulline9Grid()
{
   var strSQL = "";
   var mySql=new SqlClass();
	 var sqlid = "PDIssueInputInputSql1";
	 mySql.setResourceName(tResourceName); //鎸囧畾浣跨敤鐨刾roperties鏂囦欢鍚?
	 mySql.setSqlId(sqlid);//鎸囧畾浣跨敤鐨凷ql鐨刬d
	 mySql.addSubPara(fm.all('RiskCode').value);//鎸囧畾浼犲叆鐨勫弬鏁?
	 mySql.addSubPara(fm.all('PostCode').value);//鎸囧畾浼犲叆鐨勫弬鏁?
   strSQL = mySql.getString();
   Mulline9GridTurnPage.pageLineNum  = 10;
   Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
}
function Upload() {
  var i = 0;
  var tImportFile = fmImport.all('FileName').value;                          

  if ( tImportFile.indexOf("\\")>0 )
    tImportFile =tImportFile.substring(tImportFile.lastIndexOf("\\")+1);
  if ( tImportFile.indexOf("/")>0 )
    tImportFile =tImportFile.substring(tImportFile.lastIndexOf("/")+1);
  if ( tImportFile.indexOf("_")>0)
    tImportFile = tImportFile.substring( 0,tImportFile.indexOf("_"));
  if ( tImportFile.indexOf(".")>0)
    tImportFile = tImportFile.substring( 0,tImportFile.indexOf("."));

	if(tImportFile==null||tImportFile==""){
  	myAlert(""+"尚未选择要导入的文件，请选中后再点击上载附件"+"");
  	return;
  }
  var showStr=""+"正在上载数据……"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  fmImport.action = "./UpLodeSave.jsp"; 
  fmImport.submit();
}
function afterUpLoad(FilePath,FileName){
	showInfo.close();
	var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+encodeURI(encodeURI(""+"上载成功！"+"")) ;    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	fm.all('Filepath2').value=FilePath;
	fm.all('Filename2').value=FileName;
}
function CancleUpload(){                              
	document.fmImport.all('fileName').select();
	document.execCommand('Delete'); 
	fmImport.reset();
	//fm.all("FileName").value="";
	//document.fmImport.all('fileName');
	fm.all('Filepath2').value="";
  fm.all('Filename2').value="";
}

//浜у搧鍩烘湰淇℃伅瀹氫箟瀹屾瘯 copy from PDTestDeploy.js
function baseInfoDone()
{
  fm.all("operator").value="workflow";
  var alStr = checkRules("00");
  if(alStr != "")
  {
  	myAlert(alStr);
  	return;
  }
  fm.action = "./PDRiskDefiSave.jsp";
  var showStr=""+"正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  
  fm.submit();  
}

// copy from PDTestDeploy.js
function btnEdorDone()
{
  var alStr = checkRules("11");
  if(alStr != "")
  {
  	myAlert(alStr);
  	return;
  }

  fm.all("operator").value = "edor";
  fm.action = "./PDContDefiEntrySave.jsp";

  var showStr=""+"正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  
  fm.submit();
}

// 鐞嗚禂宸ヤ綔娴佸鐞?copy from PDTestDeploy.js
function btnClaimDone()
{
   var alStr = checkRules("12"); 
   if(alStr != "")
   {
  	  myAlert(alStr);
  	  return;
   }
            
   fm.all("operator").value = "claim";
   fm.action = "./PDContDefiEntrySave.jsp";
   
   var showStr=""+"正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
   showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  
   fm.submit();
}


//濂戠害宸ヤ綔娴佸鐞?copy from PDTestDeploy.js
function button119()
{
  //fm.all("IsDealWorkflow").value = "1";
  fm.all("operator").value = "cont";
  
  var alStr = checkRules("10");
  if(alStr != "")
  {
  	myAlert(alStr);
  	return;
  }
  
  fm.action = "./PDContDefiEntrySave.jsp";
  var showStr=""+"正在处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
	
  fm.submit(); 
}
