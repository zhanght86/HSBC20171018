//程序名称：EdorUWManuSpec.js
//程序功能：人工核保特约承保
//创建日期：2005-06-24 15:10:36
//创建人  ：liurongxiao
//更新记录：  更新人 钱林燕    更新日期  2006-10-27   更新原因/内容 大修

//               该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();
var showInfo;
var mDebug="0";
var flag;
var str = "";
var k = 0;
var cflag = "1";  //问题件操作位置 1.核保
var operate = "";
var proposalno = "";
var serialno = "";

//提交，保存按钮对应操作
function submitForm(tflag)
{
    if(tflag=="1")
       {
        fm.operate.value = "INSERT"
       }
    else if(tflag=="2")
         {
            var tSelNo = UWLPSpecGrid.getSelNo()-1;
            if(tSelNo=="-1")
              {
                alert("请选择要修改的特约信息！");
                return;
              }
            if(UWLPSpecGrid.getRowColData(tSelNo,3)=="0"||UWLPSpecGrid.getRowColData(tSelNo,3)=="1")
              {
                alert("只有在未发送或者已回收状态才能修改！");
                return;
              }
      fm.proposalno.value = UWLPSpecGrid.getRowColData(tSelNo,5);
      fm.serialno.value = UWLPSpecGrid.getRowColData(tSelNo,6);
            fm.operate.value = "UPDATE";
         }
  else
     {
            var tSelNo = UWLPSpecGrid.getSelNo()-1;
            if(tSelNo=="-1")
              {
                alert("请选择要删除的特约信息！");
                return;
              }
            if(UWLPSpecGrid.getRowColData(tSelNo,3)=="0"||UWLPSpecGrid.getRowColData(tSelNo,3)=="1")
              {
                alert("只有在未发送或者已回收状态才能删除！");
                return;
              }
      fm.proposalno.value = UWLPSpecGrid.getRowColData(tSelNo,5);
      fm.serialno.value = UWLPSpecGrid.getRowColData(tSelNo,6);
      fm.operate.value   = "DELETE";
     }

  var i = 0;
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  fm.submit(); //提交
}

/**
 * 提交后操作, 服务器数据返回后执行的操作
 */
function afterSubmit(DealFlag, MsgContent)
{
    try { showInfo.close(); } catch (ex) {}
    DealFlag = DealFlag.toLowerCase();
    var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=";
    switch (DealFlag)
    {
        case "fail":
            MsgPageURL = MsgPageURL + "F&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=250px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
        case "succ":
        case "success":
            MsgPageURL = MsgPageURL + "S&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=350px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=350;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
        default:
            MsgPageURL = MsgPageURL + "C&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=200;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
    }
    //本文件的特殊处理
    if (DealFlag == "succ" || DealFlag == "success")
    {
        try
        {
            var sEdorNo = document.getElementsByName("EdorNo")[0].value;
            var sPolNo = document.getElementsByName("PolNo")[0].value;
            QueryUWLPSpecGrid(sEdorNo, sPolNo);
        }
        catch (ex) {}
    }
}

//查询险种信息
function QueryPolGrid(tContNo,tEdorNo,tPolNo)
{
//   var strSql = "select EdorNo,ContNo,PolNo,RiskCode,RiskVersion,AppntName,InsuredName,EdorType from LPPol where "
//             + "  ContNo ='"+tContNo+"'"+" and EdorNo='"+tEdorNo+"' and PolNo='"+tPolNo+"'";
   var strSql = "";
   var sqlid1="EdorUWManuSpecSql1";
   var mySql1=new SqlClass();
   mySql1.setResourceName("bq.EdorUWManuSpecSql"); //指定使用的properties文件名
   mySql1.setSqlId(sqlid1);//指定使用的Sql的id
   mySql1.addSubPara(tContNo);//指定传入的参数
   mySql1.addSubPara(tEdorNo);//指定传入的参数
   mySql1.addSubPara(tPolNo);//指定传入的参数
   strSql=mySql1.getString();   
   var brr = easyExecSql(strSql);

   if(brr)
   {
     turnPage.queryModal(strSql,PolGrid);
   }
   else
   {
     alert("没有险种需要核保！");
     return;
   }
}

//选定一条P表已存特约来修改
function getOneToChg()
{
    var tContent = fm.Remark.value;
    var tSelNo = UWLPSpecGrid.getSelNo()-1;
    var tRemark = UWLPSpecGrid.getRowColData(tSelNo,1);
  fm.Remark.value = tContent + tRemark;
}

//选择一条特约模板来修改
function getOneModel()
{
    var tSelNo = UWModelGrid.getSelNo()-1;
    var tRemark = UWModelGrid.getRowColData(tSelNo,1);
  fm.Remark.value = tRemark ;
}

//查询LCSpec表中的特约信息
function QueryUWSpecGrid(tPolNo)
{
     var strSQL = "";
//             strSQL = "select a,b,c,case c when 'x' then '未发送' "
//                      + " when '0' then '已发送未打印'"
//                      + " when '1' then '已打印未回收'"
//                + " when '2' then '已回收'"
//                + " end,"
//                + " d,e"
//                + "   from (select s.speccontent as a,"
//                + "                s.prtseq as b,"
//                + "                (case when (select stateflag "
//                + "                            from loprtmanager p"
//                + "                            where p.prtseq = s.prtseq) is not null then (select stateflag "
//                + "                            from loprtmanager p"
//                + "                            where p.prtseq = s.prtseq) else 'x' end) as c,"
//                + "                s.proposalno as d,"
//                + "                s.serialno as e"
//                + "                from lcspec s "
//                + "                where s.polno = '"+tPolNo+"')";
             var sqlid2="EdorUWManuSpecSql2";
             var mySql2=new SqlClass();
             mySql2.setResourceName("bq.EdorUWManuSpecSql"); //指定使用的properties文件名
             mySql2.setSqlId(sqlid2);//指定使用的Sql的id
             mySql2.addSubPara(tPolNo);//指定传入的参数
             strSQL=mySql2.getString();   
             
   var brr = easyExecSql(strSQL);

   if(brr)
   {
     turnPage.queryModal(strSQL,UWSpecGrid);
   }
   return true;
}

//查询LPSpec表中的特约信息
function QueryUWLPSpecGrid(tEdorNo,tPolNo)
{
    var strSQL = "";
//             strSQL = "select a,b,c,case c when 'x' then '未发送' "
//                      + " when '0' then '已发送未打印'"
//                      + " when '1' then '已打印未回收'"
//                + " when '2' then '已回收'"
//                + " end,"
//                + " d,e"
//                + "   from (select s.speccontent as a,"
//                + "                s.prtseq as b,"
//                + "                (case when (select stateflag "
//                + "                            from loprtmanager p"
//                + "                            where p.prtseq = s.prtseq) is not null then (select stateflag "
//                + "                            from loprtmanager p"
//                + "                            where p.prtseq = s.prtseq) else 'x' end) as c,"
//                + "                s.proposalno as d,"
//                + "                s.serialno as e"
//                + "                from lpspec s "
//                + "                where s.polno = '"+tPolNo+"' and s.edorno='"+tEdorNo+"') h";

             var sqlid3="EdorUWManuSpecSql3";
             var mySql3=new SqlClass();
             mySql3.setResourceName("bq.EdorUWManuSpecSql"); //指定使用的properties文件名
             mySql3.setSqlId(sqlid3);//指定使用的Sql的id
             mySql3.addSubPara(tPolNo);//指定传入的参数
             mySql3.addSubPara(tEdorNo);//指定传入的参数
             strSQL=mySql3.getString();   
   var brr = easyExecSql(strSQL);

   if(brr)
   {
     turnPage.queryModal(strSQL,UWLPSpecGrid);
   }
   return true;
}
//查询特约模板
function QueryUWModelGrid(tContNo)
{
        var strSQL2 = "";
//        strSQL = "select substr(managecom,1,4) from lccont where contno=(select ContNo from LCCont where PrtNo = '"+tContNo+"')";
        var sqlid4="EdorUWManuSpecSql4";
        var mySql4=new SqlClass();
        mySql4.setResourceName("bq.EdorUWManuSpecSql"); //指定使用的properties文件名
        mySql4.setSqlId(sqlid4);//指定使用的Sql的id
        mySql4.addSubPara(tContNo);//指定传入的参数
        strSQL2=mySql4.getString();   
        var managecom = easyExecSql(strSQL2);

//        strSQL = "select substr(managecom,1,6) from lccont where contno=(select ContNo from LCCont where PrtNo = '"+tContNo+"')";
        var strSQL1="";
        var sqlid5="EdorUWManuSpecSql5";
        var mySql5=new SqlClass();
        mySql5.setResourceName("bq.EdorUWManuSpecSql"); //指定使用的properties文件名
        mySql5.setSqlId(sqlid5);//指定使用的Sql的id
        mySql5.addSubPara(tContNo);//指定传入的参数
        strSQL1=mySql5.getString();   
        var managecom2 = easyExecSql(strSQL1);
        var strSQL = "";
        if(managecom!=null){
            //如果是北分、上海、广州的投保单则显示十万
          if(managecom=="8621"||managecom=="8622"){
//       strSQL = "select cont from ldcodemod where "+k+"="+k
//                 + " and codetype = 'spectype2'";
	       var sqlid6="EdorUWManuSpecSql6";
	       var mySql6=new SqlClass();
	       mySql6.setResourceName("bq.EdorUWManuSpecSql"); //指定使用的properties文件名
	       mySql6.setSqlId(sqlid6);//指定使用的Sql的id
	       mySql6.addSubPara(k);//指定传入的参数
	       mySql6.addSubPara(k);//指定传入的参数
	       strSQL=mySql6.getString();   
          }
        else if (managecom=="8623"){
             if(managecom2!=null&&managecom2=="862300"){
//             strSQL = "select cont from ldcodemod where "+k+"="+k
//                 + " and codetype = 'spectype2'";
           var sqlid7="EdorUWManuSpecSql7";
  	       var mySql7=new SqlClass();
  	       mySql7.setResourceName("bq.EdorUWManuSpecSql"); //指定使用的properties文件名
  	       mySql7.setSqlId(sqlid7);//指定使用的Sql的id
  	       mySql7.addSubPara(k);//指定传入的参数
  	       mySql7.addSubPara(k);//指定传入的参数
  	       strSQL=mySql7.getString();   
                }
             else {
//       strSQL = "select cont from ldcodemod where "+k+"="+k
//                 + " and codetype = 'spectype'";
       	   var sqlid8="EdorUWManuSpecSql8";
	       var mySql8=new SqlClass();
	       sqlid8.setResourceName("bq.EdorUWManuSpecSql"); //指定使用的properties文件名
	       sqlid8.setSqlId(sqlid8);//指定使用的Sql的id
	       sqlid8.addSubPara(k);//指定传入的参数
	       sqlid8.addSubPara(k);//指定传入的参数
	       strSQL=sqlid8.getString();
                }
            }
          else{
//       strSQL = "select cont from ldcodemod where "+k+"="+k
//                 + " and codetype = 'spectype'";
       var sqlid9="EdorUWManuSpecSql9";
       var mySql9=new SqlClass();
       mySql9.setResourceName("bq.EdorUWManuSpecSql"); //指定使用的properties文件名
       mySql9.setSqlId(sqlid9);//指定使用的Sql的id
       mySql9.addSubPara(k);//指定传入的参数
       mySql9.addSubPara(k);//指定传入的参数
       strSQL=mySql9.getString();
          }
        }
     else{
//       strSQL = "select cont from ldcodemod where "+k+"="+k
//                 + " and codetype = 'spectype'";
       var sqlid10="EdorUWManuSpecSql10";
       var mySql10=new SqlClass();
       mySql10.setResourceName("bq.EdorUWManuSpecSql"); //指定使用的properties文件名
       mySql10.setSqlId(sqlid9);//指定使用的Sql的id
       mySql10.addSubPara(k);//指定传入的参数
       mySql10.addSubPara(k);//指定传入的参数
       strSQL=mySql10.getString();
     }

       //获取原保单信息
       // strSQL = "select cont from ldcodemod where 1 =1 and codetype='spectype'";

   var brr = easyExecSql(strSQL);

   if(brr)
   {
     turnPage.queryModal(strSQL,UWModelGrid);
   }
   return true;
}


/**
 * 返回主界面
 */
function returnParent()
{
    try
    {
        top.close();
        top.opener.focus();
    }
    catch (ex) {}
}

