//绋嬪簭鍚嶇О锛歅DRiskSortDefi.js
//绋嬪簭鍔熻兘锛氶櫓绉嶅垎绫诲畾涔?
//鍒涘缓鏃ユ湡锛?009-3-12
//璇ユ枃浠朵腑鍖呭惈瀹㈡埛绔渶瑕佸鐞嗙殑鍑芥暟鍜屼簨浠?
var turnPage = new turnPageClass();
var showInfo;
var tResourceName = "productdef.PDRiskSortDefiInputSql";
function submitForm()
{
if(fm.all("IsReadOnly").value == "1")
  {
  	myAlert(""+"您无权执行此操作"+"");
  	return;
  }
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
function save()
{
 fm.all("operator").value="save";
 if(Mulline9Grid.mulLineCount==0){
 		fm.all("operator").value="del";
 }
 submitForm();
}
function returnParent()
{
  top.opener.focus();
	top.close();
}

function queryMulline9Grid()
{
    var mySql=new SqlClass();
   mySql.setResourceName(tResourceName); //鎸囧畾浣跨敤鐨刾roperties鏂囦欢鍚?
	mySql.setSqlId("PDRiskSortDefiInputSql1");//鎸囧畾浣跨敤鐨凷ql鐨刬d
	mySql.addSubPara(fm.RiskCode.value);//鎸囧畾浼犲叆鐨勫弬鏁?
	var strSql = mySql.getString();
   Mulline9GridTurnPage.queryModal(strSql,Mulline9Grid);
}
function isshowbutton()
{   var value=getQueryState1();
	if(value=='0'||value==0){
	//document.getElementById('save4').style.display = 'none';
	document.getElementById('savabutton').disabled=true;
	}

}