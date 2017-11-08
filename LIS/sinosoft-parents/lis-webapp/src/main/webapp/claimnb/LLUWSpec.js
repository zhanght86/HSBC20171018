//程序名称：LLUWSpec.js
//程序功能：二核特约承保
//创建日期：2005-11-04 
//创建人  ：万泽辉
//更新记录：  更新人    更新日期     更新原因/内容

//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var flag;
var str = "";
var turnPage = new turnPageClass();
var mySql = new SqlClass();
var turnPage1 ;
var turnPage2 ;
var k = 0;
var cflag = "1";  //问题件操作位置 1.核保
var mOperate ;

//提交，保存按钮对应操作
function submitForm() {
    var i = 0;
    var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.hideOperate.value = mOperate;
    fm.action = "./LLUWSpecSave.jsp";
    document.getElementById("fm").submit(); //提交
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
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
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
        if( mOperate == "DELETE")
        {
        	 LLUWSpecContGrid.delRadioTrueLine();	
             fm.SpecReason.value = "";
             fm.Remark.value     = "";
        }             
        getPolGridCho();
    }    
    
    mOperate = '';
}


//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
    if(cDebug=="1")
    {
		 parent.fraMain.rows = "0,0,50,82,*";
    }
 	else 
 	{
  		 parent.fraMain.rows = "0,0,0,82,*";
 	}
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


function manuchkspecmain()
{
	document.getElementById("fm").submit();
}
/**=========================================================================
    修改状态：开始
    修改原因：得到投保单信息列表
    修 改 人: 万泽辉
    修改日期：2005.11.28
   =========================================================================
**/
function QueryPolSpecGrid(tContNo){
	// 书写SQL语句
	var strSQL = "";
	var i, j, m, n; 
       //获取原保单信息
   /* strSQL = "select LCPol.ContNo,LCPol.PrtNo,LCPol.PolNo,LCPol.RiskCode,LCPol.RiskVersion,"
           + " LCPol.AppntName,LCPol.InsuredName from LCPol where "				 			
		   + " ContNo ='"+tContNo+"' and LCPol.PolNo = LCPol.MainPolNo" 
		   + " order by polno ";	*/		
	
	mySql = new SqlClass();
	mySql.setResourceName("claimnb.LLUWSpecInputSql");
	mySql.setSqlId("LLUWSpecSql1");
	mySql.addSubPara(tContNo); 
	turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1); 

  //判断是否查询成功
  if (turnPage.strQueryResult) {  
      //查询成功则拆分字符串，返回二维数组
      turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
       //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
       turnPage.pageDisplayGrid = LLUWSpecGrid;    
          
      //保存SQL语句
      turnPage.strQuerySql     = strSQL; 
  
      //设置查询起始位置
       turnPage.pageIndex       = 0;  
  
      //在查询结果数组中取出符合页面显示大小设置的数组
       var arrDataSet   = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
       //调用MULTILINE对象显示查询结果
       displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
    }
 
  return true;	
}
/**=========================================================================
    修改状态：开始
    修改原因：查询保单下的特约特约信息
    修 改 人: 万泽辉
    修改日期：2005.11.28
   =========================================================================
**/
function QueryPolSpecCont()
{
	var strSQL  = "";
    var arr     = new Array;
    var tPolNo  = LLUWSpecGrid.getRowColData(0,3);
    var tContNo = LLUWSpecGrid.getRowColData(0,1);
    var tProposalNo = fm.ProposalNo.value;
    
    //alert(document.all('BatNo').value);
    
    
   /* strSQL      = " select contno,batno,SpecContent,'A' from lluwspecmaster where 1=1"
	            //+ " polno = '"+tPolNo+"' "
	            + " and contno = '"+tContNo+"'"
	            + " and BatNo ='"+document.all('BatNo').value+"'"*/
	mySql = new SqlClass();
	mySql.setResourceName("claimnb.LLUWSpecInputSql");
	mySql.setSqlId("LLUWSpecSql2");
	mySql.addSubPara(tContNo); 
	mySql.addSubPara(document.all('BatNo').value); 
	arr         =  easyExecSql( mySql.getString() );
	if (arr)
    {
         displayMultiline(arr,LLUWSpecContGrid);
    }
	
    else
	{
		 //获取原保单信息
         /*strSQL = "select '', '',speccontent ,'B' from lcspec where "				 	
		        + "polno = '"+tPolNo+"'";	*/
		 mySql = new SqlClass();
		mySql.setResourceName("claimnb.LLUWSpecInputSql");
		mySql.setSqlId("LLUWSpecSql3");
		mySql.addSubPara(tPolNo); 
		 arr    =  easyExecSql( mySql.getString() );
	     if (arr)
         {
              displayMultiline(arr,LLUWSpecContGrid);
         }
         
    }
}
/**=========================================================================
    修改状态：开始
    修改原因：查询保单下的特约特约信息
    修 改 人: 万泽辉
    修改日期：2005.11.28
   =========================================================================
**/
function getPolGridCho()
{
	QueryPolSpecCont();
    var cPolNo  = LLUWSpecGrid.getRowColData(0,3);
    var tcontno = LLUWSpecGrid.getRowColData(0,1);
    var tProposalNo = fm.ProposalNo.value ;
    if(cPolNo != null && cPolNo != "" )
    {
        fm.PolNo.value = cPolNo;
        QuerySpecReason(tProposalNo); 
        QuerySpec(tcontno);
    }	
}


/**=========================================================================
    修改状态：开始
    修改原因：查询已经录入特约内容
    修 改 人: 万泽辉
    修改日期：2005.11.28
   =========================================================================
**/
function QuerySpec(tcontno)
{
	
	// 书写SQL语句
	var strSQL = "";
	/*strSQL     = " select speccontent from LLUWSpecMaster where contno='"+tcontno+"' "
	           //+ " and SerialNo in (select max(SerialNo) from LLUWSpecMaster where contno = '"+tcontno+"')";
    		   + " and BatNo ='"+document.all('BatNo').value+"' "
    		   + "union all "
    		   + " select speccontent from LLUWSpecSub where contno='"+tcontno+"' "
    		   + " and BatNo ='"+document.all('BatNo').value+"' "*/
	 mySql = new SqlClass();
		mySql.setResourceName("claimnb.LLUWSpecInputSql");
		mySql.setSqlId("LLUWSpecSql4");
		mySql.addSubPara(tcontno); 
		mySql.addSubPara(document.all('BatNo').value); 
		mySql.addSubPara(tcontno); 
		mySql.addSubPara(document.all('BatNo').value); 
	var i=0;
	var arrResult = easyQueryVer3(mySql.getString(), 1, 0, 1);
	if(arrResult != ""&& arrResult!= null)
	{
	    var test= new Array();
	     test = decodeEasyQueryResult(arrResult);
	
	     for(i=0;i<test.length;i++)
	     {
	      fm.Remark.value= fm.Remark.value+(i+1)+"."+test[i][0];
	     }
	   // fm.Remark.value= arrResult[0][0];
        return true;
    }
        
    else
    {
    	return false;
    } 

}

/**=========================================================================
    修改状态：开始
    修改原因：查询已经录入特约原因
    修 改 人: 万泽辉
    修改日期：2005.11.28
   =========================================================================
**/
function QuerySpecReason(tProposalNo)
{
	var strSQL = "";
//	strSQL = "select specreason from LCUWMaster where 1=1 "
//			 + " and proposalno = '"+tProposalNo+"'";
	/*strSQL = "select changepolreason from llcuwmaster where 1=1 "
		    + " and CaseNo='"+fm.ClmNo.value+"'"
		    + " and contno = '"+fm.ContNo.value+"'"
		    + " and batno = '"+fm.BatNo.value+"'";*/
	mySql = new SqlClass();
		mySql.setResourceName("claimnb.LLUWSpecInputSql");
		mySql.setSqlId("LLUWSpecSql5");
		mySql.addSubPara(fm.ClmNo.value); 
		mySql.addSubPara(fm.ContNo.value); 
		mySql.addSubPara(fm.BatNo.value); 
	var tResult = easyExecSql(mySql.getString());
	//prompt("",strSQL);
	if(tResult == null )
	{
		 return "";
	}
	fm.SpecReason.value = tResult[0][0];
    return true;	
}

/**=========================================================================
    修改状态：开始
    修改原因：得到一条特约信息
    修 改 人: 万泽辉
    修改日期：2005.11.28
   =========================================================================
**/
function getSpecGridCho()
{
	var tContent    = fm.Remark.value;
	var tSelNo      = LLUWSpecContGrid.getSelNo()-1;
	var tRemark     = LLUWSpecContGrid.getRowColData(tSelNo,3);	
	var tFlag       = LLUWSpecContGrid.getRowColData(tSelNo,4)
//	if(tFlag == "B")
//	{
	    fm.Remark.value = tRemark ;
	    //+ tContent;
//    }
//    else
//    {
//    	//var tSelNo  = LLUWSpecGrid.getSelNo()-1;
//        var tcontno = LLUWSpecGrid.getRowColData(0,1);
//        var tProposalNo = fm.ProposalNo.value ;
//        if(tProposalNo != null && tProposalNo != "" )
//        {
//            QuerySpecReason(tProposalNo); 
//            QuerySpecContent();
//        }	
//    }
}
/**=========================================================================
    修改状态：开始
    修改原因：查询特约内容
    修 改 人: 万泽辉
    修改日期：2005.11.28
   =========================================================================
**/
function QuerySpecContent()
{
	var tSelNo      = LLUWSpecContGrid.getSelNo()-1;
    var tProposalNo = LLUWSpecContGrid.getRowColData(tSelNo,1);
    var tSerialNo   = LLUWSpecContGrid.getRowColData(tSelNo,2);
    /*var tSql        = " select speccontent from lluwspecmaster where 1=1 and "
                    + " contno = '"+tProposalNo+"'" +
                    " and batno = '"+document.all('BatNo').value+"'";*/
  mySql = new SqlClass();
		mySql.setResourceName("claimnb.LLUWSpecInputSql");
		mySql.setSqlId("LLUWSpecSql6");
		mySql.addSubPara(tProposalNo); 
		mySql.addSubPara(document.all('BatNo').value); 
    var tResult     = easyExecSql(mySql.getString());
    if( tResult !="" && tResult != null)
    {
    	//fm.Remark.value = tResult[0][0];
    }
    else
    {
    	//fm.Remark.value = "";
    }
}
/**=========================================================================
    修改状态：提交删除按钮对应操作
    修改原因：
    修 改 人：万泽辉
    修改日期：2005.11.27
   =========================================================================
**/

function deleteClick()
{   
    var tSelNo  = LLUWSpecContGrid.getSelNo()-1;
    if( tSelNo < 0 )
    {
    	alert("请选择一条记录！");
    	return ;
    }
    var tSerialNo = LLUWSpecContGrid.getRowColData(tSelNo,2);
    fm.SerialNo.value = tSerialNo;
    var rRet = beforeSubmit();
    if (rRet==0)
    {
        mOperate='DELETE';
        submitForm();
    }
    
}

/**=========================================================================
    修改状态：提交保存按钮对应操作
    修改原因：
    修 改 人：万泽辉
    修改日期：2005.11.27
   =========================================================================
**/
function saveClick()
{
	var tSelNo  = LLUWSpecContGrid.getSelNo()-1;
    if( tSelNo >= 0 )
    {
    	  var tSerialNo     = LLUWSpecContGrid.getRowColData(tSelNo,2);
          if(tSerialNo!=""&&tSerialNo!=null)
          {
               fm.SerialNo.value = tSerialNo;
          }
          else
          {
          	   fm.SerialNo.value = "";
          }

    }
    
    var rRet = beforeSubmit();
    if (rRet==0)
    {
        mOperate='SAVE';
        submitForm();
    }
}
/**=========================================================================
    修改状态：提交前的数据校验操作
    修改原因：
    修 改 人: 万泽辉
    修改日期：2005.11.27
   =========================================================================
**/
function beforeSubmit()
{
    var i           = 0;
    var tRemark     = fm.Remark.value;
    var tSpecReason = fm.SpecReason.value;   
    if ( tRemark == null || tRemark ==''){
         alert('请录入[特别约定]！');
        return 1;
    }
    return 0;
}
  