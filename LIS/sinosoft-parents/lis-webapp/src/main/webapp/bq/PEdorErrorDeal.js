
//<!-- JavaScript Document BGN -->


/*============================================================================*/

var showInfo;                                 //全局变量, 弹出提示窗口, 必须有
var turnPage = new turnPageClass();           //全局变量, 查询结果翻页, 必须有
var turnPageAllGrid = new turnPageClass();    //全局变量, 共享工作队列结果翻页
var turnPageSelfGrid = new turnPageClass();   //全局变量, 我的工作队列结果翻页
var sqlresourcename = "bq.PEdorErrorDealInputSql";
/*============================================================================*/

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
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
    }
    //本文件的特殊处理
    if (DealFlag == "succ" || DealFlag == "success")
    {
        queryAllGrid();
    }
}

/*============================================================================*/

/**
 * 查询共享队列
 */
function queryAllGrid()
{
	  //alert(11);
    var QuerySQL = "";
/*    var QuerySQL = "select StandbyFlag1, "
                 +        "OtherNo, "
                 +        "decode(code,'BQ33','保全拒绝通知书','BQ37','保全审核通知书','BQ42','保全撤销通知书','BQ99','保全转账失败通知书'), "
                 +        "(select edortype||'-'||(select edorname from lmedoritem  c where c.edorcode=a.edortype and appobj='I' ) from lpedoritem a where  a.EdorAcceptNo=b.StandbyFlag1 and rownum=1), "
                 +        "StandbyFlag4, "
                 +        "StandbyFlag6, "
                 +        "decode(StateFlag,'2','回销','未回销'),prtseq,(case when ((select floor(sysdate-b.makedate) from dual ) >=20 and b.StateFlag!='2') then '是' else '否' end ) ,code, "
                 +   " (select othersign from ldcode where codetype = 'lettertype' and code=b.code )"
                 +   "from LOPRTManager  b "
                 +  "where 1=1 and StateFlag!='2' and code in (select code from ldcode where 1 = 1 and codetype = 'lettertype') "
                 +  getWherePart("StandbyFlag1", "EdorAcceptNo1")
                 +  getWherePart("ManageCom", "ManageCom1", "like")
                 +  getWherePart("OtherNo", "OtherNo1")
                 +  getWherePart("Code", "LetterType")
                 +  getWherePart("StandbyFlag4", "AcceptOperator1")
                 +  getWherePart("StandbyFlag5", "MakeDate1","<=")
                 +  "order by StandbyFlag1 desc, StandbyFlag5 desc";
*/
    //alert(QuerySQL);
    try
    {
    	var sqlid1="PEdorErrorDealInputSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(fm.EdorAcceptNo1.value);//指定传入的参数
		mySql1.addSubPara(fm.ManageCom1.value);
		mySql1.addSubPara(fm.OtherNo1.value);
		mySql1.addSubPara(fm.LetterType.value);
		mySql1.addSubPara(fm.AcceptOperator1.value);
		mySql1.addSubPara(fm.MakeDate1.value);
		QuerySQL=mySql1.getString();
		
        turnPageAllGrid.pageDivName = "divTurnPageAllGrid";
        turnPageAllGrid.queryModal(QuerySQL, AllGrid);
    }
    catch (ex)
    {
        alert("警告：查询抽检保单信息出现异常！ ");
        return;
    }
    if (AllGrid.mulLineCount == 0)
    {
       alert("没有函件记录!");
    }
    document.all("divForceBankBack").style.display = "none";  
    document.all("divForceBack").style.display = "none";  
    document.all("divBack").style.display = "none";     
}

/*============================================================================*/

/*============================================================================*/

/**
 * 处理选中的抽检保单
 */
function dealSpotCheck()
{
    var nSelNo = AllGrid.getSelNo() - 1;
    if (nSelNo == null || nSelNo < 0)
    {
        alert("请先选择您要处理的函件！ ");
        return;
    }else if(AllGrid.getRowColData(nSelNo,10)=='BQ37') //保全审核通知书强制回销，原因判断
    	{
    		if(AllGrid.getRowColData(nSelNo,9)=='否')
    		{
    			  alert("函件未逾期不可以做逾期回销，如果要回销，可以进行正常回销");
            return ;
    		}
    		
      if(!verifyFormElements())return;
      if(fm.MCanclReason.value==null || fm.MCanclReason.value =="")
      {
      alert("请选择的回销原因");
      return ;
    	
    	}
    
    if(fm.MCanclReason.value=="G" && fm.CancelReasonContent.value=="")
    {
    	alert("选择的回销原因为其它，请录入撤销备注！ ");
      return ;
    }		
   }    	

       //alert(AllGrid.getRowColData(nSelNo,10));

    	  if(!confirm("确认需要强制回销函件"))
    	  {
    	  	return ;
        }else
       	{
          fm.PrtSerNo.value = AllGrid.getRowColData(nSelNo, 8);
          //alert(fm.PrtSerNo.value );
          fm.EdorAcceptNo1.value = AllGrid.getRowColData(nSelNo, 1);
          fm.LetterType.value = AllGrid.getRowColData(nSelNo, 10);

        if (fm.EdorAcceptNo1.value == null || trim(fm.EdorAcceptNo1.value) == "")
        {
            alert("警告：无法获取函件信息！ ");
            return;
        }
        else
        {
           var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
          var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
		  var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
         fm.ActionFlag.value="force"	
         document.getElementById("fm").submit();
        }
       		
      }
}


/**
 * 处理选中的抽检保单
 */
function dealCheck()
{
    var nSelNo = AllGrid.getSelNo() - 1;
    if (nSelNo == null || nSelNo < 0)
    {
        alert("请先选择您要处理的函件！ ");
        return;
    }else if(AllGrid.getRowColData(nSelNo,10)=='BQ37') //保全审核通知书强制回销，原因判断
    	{
    		
    		if(AllGrid.getRowColData(nSelNo,9)=='是')
    		{
    			  alert("函件已经逾期不可以做正常回销，如果要回销，可以进行强制回销");
            return ;
    		}	
   }  
    		  	
   	  if(!confirm("确认需要回销函件"))
    	  {
    	  	return ;
        }else
       	{
          fm.PrtSerNo.value = AllGrid.getRowColData(nSelNo, 8);
          //alert(fm.PrtSerNo.value );
          fm.EdorAcceptNo1.value = AllGrid.getRowColData(nSelNo, 1);
          fm.LetterType.value = AllGrid.getRowColData(nSelNo, 10);
          //alert(fm.LetterType.value );
        if (fm.EdorAcceptNo1.value == null || trim(fm.EdorAcceptNo1.value) == "")
        {
            alert("警告：无法获取函件信息！ ");
            return;
        }
        else
        {

          var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
          var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
		  var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
          fm.ActionFlag.value="normal"
         document.getElementById("fm").submit();
        }       		
       		}
}


/*============================================================================*/

/**
 * 查看选中的抽检保单的保全明细
 */
function viewEdorDetail()
{
    var nSelNo = AllGrid.getSelNo() - 1;
    if (nSelNo == null || nSelNo < 0)
    {
        alert("请先选择您要查询的任务！ ");
        return;
    }
    else
    {
        var sEdorAcceptNo = AllGrid.getRowColData(nSelNo, 1);
        var sOtherNoType = AllGrid.getRowColData(nSelNo, 3);
        if (sEdorAcceptNo == null || trim(sEdorAcceptNo) == "" || sOtherNoType == null || trim(sOtherNoType) == "")
        {
            alert("警告：无法获取保全受理号、申请号码类型信息！ ");
            return;
        }
        else
        {
            var sOpenWinURL = "../sys/BqDetailQueryFrame.jsp?Interface=../sys/BqDetailQuery.jsp";
            var sParameters = "&EdorAcceptNo=" + sEdorAcceptNo + "&OtherNoType=" + sOtherNoType;
            OpenWindowNew(sOpenWinURL + sParameters, "保全抽检明细查询", "left");
        }
    } //nSelNo > 0
}

/*============================================================================*/

/**
* showCodeList 的回调函数
*/
function afterCodeSelect(sCodeListType, oCodeListField)
{
    sCodeListType = sCodeListType.toLowerCase();
    if (sCodeListType == "backreason")
    {
        if (oCodeListField.value =="d")
        {
            try
            {

                document.all("divConment").style.display = "";
            }
            catch (ex) {}
        }else
        	{
             try
            {

                document.all("divConment").style.display = "none";
            }
            catch (ex) {}       		
        		
         }
    } 
}

function afterCodeSelect(sCodeListType, oCodeListField)
{
    sCodeListType = sCodeListType.toLowerCase();
    if (sCodeListType == "edorcancelmreason")
    {
    	
    	
    	      try
            {
            	   var str = new Array('A','B','C','D','E','F','G');
                //str=('A','B','C','D','E','F','G');
                //alert(str);
                for(var t=0;t<str.length;t++)
                {
                	//alert("divAppCancel"+str[t]);
                	if(str[t]!=oCodeListField.value)
                	{
                		 document.all("divAppCancel"+str[t]).style.display = "none";
                	}
               }
                document.all("divAppCancel"+oCodeListField.value).style.display = "";
                
            }
            catch (ex) {}

    } //EdorApproveReason
}


//显示相关内容
function  disPlayBackInf()
{
	
	    var nSelNo = AllGrid.getSelNo() - 1;
      var sCode = AllGrid.getRowColData(nSelNo, 10);
      var sYQ = AllGrid.getRowColData(nSelNo, 9);
      //alert(sYQ);
    var strsql = "";
//    var strsql = "select othersign,code  from ldcode where codetype='lettertype' and  code='"+sCode+"'";
    
    	var sqlid2="PEdorErrorDealInputSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(sCode);//指定传入的参数
		strsql=mySql2.getString();
    
    var brr = easyExecSql(strsql);
    if("1"==brr[0][0]&&'BQ37'==sCode)
    {
     try
       {
       if('是'==sYQ)
       {
       	 document.all("divForceBack").style.display = "";
       	}else
       		{
       		 document.all("divForceBack").style.display = "none";	
       }

       document.all("divBack").style.display = "";
        }
       catch (ex) {}    	
    	
    	}
        
	    if("1"==brr[0][0]&&('BQ99'==sCode ||'LP01'==sCode))
    {
     try
       {

       document.all("divForceBankBack").style.display = "";  
        }
       catch (ex) {}    	
    	
    	}else
    		{
    	document.all("divForceBankBack").style.display = "none";  
			
    	}
	}
/*============================================================================*/

function verifyFormElements()
{
	
	  var selectedIndex = -1;
    var i = 0;
    
    for (i=0; i<fm.SCanclReason.length; i++)
    {
        if (fm.SCanclReason[i].checked)
        {
            selectedIndex = i;
            //alert("您选择项的 value 是：" + fm.SCanclReason[i].value);
            fm.CancelReasonCode.value=fm.SCanclReason[i].value;
            //alert("您选择项的 value 是：" + fm.CancelReasonCode.value);
            return true;
        }
    }    
    if (selectedIndex < 0)
    {
        alert("您没有选择任何撤销原因");
        return false ;
    }

}      
 

//<!-- JavaScript Document END -->
