
// 该文件中包含客户端需要处理的函数和事件

var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量

var mDebug="0";
var mOperate="";
var showInfo;
var pageFlag="0";
var mySql=new SqlClass();
mySql.setJspName("../../i18n/i18nManagerSql.jsp");

//--- 查询
function msginfoquery()
{
 var trSQL="select msg_id,msg_cn,msg_ja from msg_mapping a where msg_id like 'M0%' and msg_cn not like '%|%' ";
 
 
 if(fm.chinese.value!="")
 {
 	mySql.setSqlId("i18nManagerSql_1");
 	mySql.addPara('msg_cn',fm.chinese.value);
 	trSQL+=mySql.getSQL();
 }
 if(fm.japanese.value!="")
 {
 	mySql.setSqlId("i18nManagerSql_2");
 	mySql.addPara('msg_ja',fm.japanese.value);
 	trSQL+=mySql.getSQL();
 }
 
 turnPage.pageLineNum = 10;    //每页5条

 turnPage.queryModal(trSQL,MsgGrid);
}

 
 function SaveClick()
 {
 		//添加校验
 	  submitForm();	
 }
 function submitForm()
{
 		var showStr=I18NMsg("M0000050069");
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
    showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    fm.submit(); //提交
}

 function afterSubmit( FlagStr, content )
{
	showInfo.close();
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(content));
	if (FlagStr == "Fail" )
	{                 
		myAlert(content);
	}
	else
	{ 
		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	}
}

//--- 医疗卡使用人信息单击事件 
 function MsgGridClick()
 {
 	 	var tNo = MsgGrid.getSelNo()-1;	
	
	  fm.msg_id.value=MsgGrid.getRowColData(tNo,1);
 	  fm.msg_cn.value=MsgGrid.getRowColData(tNo,2);
 	  fm.msg_ja.value=MsgGrid.getRowColData(tNo,3);

}
  
