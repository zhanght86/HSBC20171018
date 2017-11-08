//Name:LLRgtMAffixListIssue.js
//Function：单证回销js程序
//Date：2005-07-1 17:44:28
//Author ：yuejw
var mySql = new SqlClass();
//初始化[已经回销单证]查询
function initRgtAffixGridQuery()
{
    try
    {
        var Proc=fm.Proc.value;
       /* strSQL="select affixno,affixcode,affixname,subflag,(case needflag when '0' then '0-是' when '1' then '1-否'  end ),readycount,shortcount,"
               +"(case property when '0' then '0-原件' when '1' then '1-复印件' end ),"
               +"(case returnflag when '0' then '0-是' when '1' then '1-否' end ),"
               +"(case affixconclusion when '0' then '0-齐全' when '1' then '1-不齐全' end ),affixreason from llaffix where 1=1 "
               + " and rgtno='"+fm.ClmNo.value+"' "  	
	           + " and subflag='0' and ReAffixDate is not null and AffixState='03' and SupplyStage='01'";*/
	   mySql = new SqlClass();
		mySql.setResourceName("claim.LLRgtMAffixListIssueInputSql");
		mySql.setSqlId("LLRgtMAffixListIssueSql1");
		mySql.addSubPara(fm.ClmNo.value );  
	    //prompt("已单证回销初始化页面查询",strSQL);   
	    arr=easyExecSql(mySql.getString());
        if(arr!=null)
        {
            displayMultiline(arr,RgtAffixGrid);
        }
        else
        {
            RgtAffixGrid.clearData();
            return;
        }

    }
    catch(ex)
    {
      alert("LLRgtMAffixList.js-->initRgtAffixGridQuery函数中发生异常:数据查询错误!");
    }
}

//初始化[待回销单证]查询
function initAffixGridQuery()
{
    try
    {
    	//returnflag,affixconclusion,affixreason----查询语句删除的字段（2005-08-09修改）
        var Proc=fm.Proc.value;
       /* strSQL="select affixno,affixcode,affixname,subflag,(case needflag when '0' then '0-是' when '1' then '1-否'  end ),readycount,shortcount,property,'','','',SupplyStage from llaffix where 1=1 and"
               + " rgtno='"+fm.ClmNo.value+"' and"
	           + " (subflag is null or subflag='1') and ReAffixDate is null and AffixState='02' and SupplyStage='01'";   */
	     mySql = new SqlClass();
		mySql.setResourceName("claim.LLRgtMAffixListIssueInputSql");
		mySql.setSqlId("LLRgtMAffixListIssueSql2");
		mySql.addSubPara(fm.ClmNo.value );  
	    //prompt("待单证回销初始化页面查询",strSQL); 
	    arr=easyExecSql(mySql.getString());
        if(arr!=null)
        {
            displayMultiline(arr,AffixGrid);
        }
        else
        {
            AffixGrid.clearData();
            return;
        }

    }
    catch(ex)
    {
      alert("LLRgtMAffixList.js-->initAffixGridQueryt函数中发生异常:数据查询错误!");
    }
}

function clearData()
{
  fm.AffixCode.value="";
  fm.AffixName.value=""; 
  fm.AffixCode.disabled=false;	
  fm.AffixName.disabled=false;	
  fm.OtherAffix.checked=false;
  fm.OtherName.value="";
  fm.OtherName.disabled=true;
}

//[返回]按钮
function returnParent()
{
	top.opener.queryGrid();
	top.close();
}

//[判断是否有选中的行及检验选中行的数据填写是否完整]-----被AffixSave()调用
function CheckBeforeSave()
{
    var row = AffixGrid.mulLineCount;
    var i = 0;
	for(var m=0;m<row;m++ )
	{
		if(AffixGrid.getChkNo(m))
		{
			i=i+1;
			var n=m+1;
		  	var tAffixConclusion=AffixGrid.getRowColData(m,10);//[单证结论]
		  	var tAffixReason=AffixGrid.getRowColData(m,11);//[单证不齐全原因]
		  	if(tAffixConclusion=="" ||tAffixConclusion==null)//[检查是否选择了单证检查结论]
			{
				alert("第"+n+"行没有选择单证检查结论！");
				return false;
			}
			//[检查结论为齐全时，无需填写 不齐全原因]
			if(tAffixConclusion=="0")
			{
				AffixGrid.setRowColData(m,11,"")
			}
			//[检查为不齐全时，是否填写了不齐全原因]
			if(tAffixConclusion=="1" && tAffixReason=="")
			{
				alert("第"+n+"行没有填写单证检不齐全原因！");
				return false;
			}	
			AffixGrid.setRowColData(m,4,"0");//置“0--提交标志”
		}
	}
	if(i!=row)
	{
		alert("请选择全部待回销单证！");
		return false;
	}
}

//[保存]按钮-----保存选中的结论信息，进行回销
function AffixSave()
{
	 if (fm.ReplyDate.value == "" || fm.ReplyDate.value == null)
   {
        alert("请输入补充材料受理日期！");
        return false;
   }
    
	if(CheckBeforeSave()==false)//[回销保存前进行检查]
	{
		return;
	}
    fm.Operate.value="Rgt||UPDATE";
    fm.action="LLRgtMAffixListIssueSave.jsp";
    SubmitForm();  
}
function SubmitForm()
{
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
    fm.submit();  
}

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
		returnParent();    //[返回]按钮   
    }
}