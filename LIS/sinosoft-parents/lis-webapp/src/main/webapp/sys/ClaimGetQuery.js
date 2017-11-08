var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";

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

//显示frmSubmit框架，用来调试 
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,0,0,*";
  }
 	else
   {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}

	parent.fraMain.rows = "0,0,0,0,*";
}


function showCaseInfo()
{
  initPolGrid();
  
  fm.action = "./ShowCaseInfo.jsp";
  fm.submit();
}
function displayQueryResult1(strResult)
{
  strResult = Conversion(strResult);
  var filterArray          = new Array(3,2,4,14,28,30);
  turnPage.strQueryResult  = strResult;
  turnPage.useSimulation   = 1;
  var tArr = decodeEasyQueryResult(turnPage.strQueryResult);
  turnPage.arrDataCacheSet = chooseArray(tArr, filterArray);
  turnPage.pageDisplayGrid = PolGrid;
  turnPage.pageIndex       = 0;
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  if (turnPage.queryAllRecordCount > turnPage.pageLineNum)
  {
    try
    {
      window.divPage.style.display = "";
    }
    catch(ex) { }
  }
  else
  {
    try { window.divPage.style.display = "none"; } catch(ex) { }
  }
  turnPage.blockPageNum = turnPage.queryAllRecordCount / turnPage.pageLineNum;
}
//将记录中的信息显示在下一个界面上
function ShowDetailInfo()
{
  if (PolGrid.getSelNo()==0)
  {
    alert("请您选择一个分案！");
    return;
  }
  else
  {
    var varSrc = "&RptNo=" + PolGrid.getRowColData(PolGrid.getSelNo()-1,1);
    varSrc += "&RgtNo=" + PolGrid.getRowColData(PolGrid.getSelNo()-1,2);
    varSrc += "&Type=" + PolGrid.getRowColData(PolGrid.getSelNo()-1,3);
    varSrc += "&InsuredName=" + PolGrid.getRowColData(PolGrid.getSelNo()-1,4);
    varSrc += "&PolNo=" +fm.PolNo.value;
    var newWindow = window.open("./FrameCaseInfo.jsp?Interface=CaseInfoInput.jsp"+varSrc,"CaseInfoInput",'width=800,height=500,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=0,status=0');
  }
}

/**=========================================================================
    修改状态：开始
    修改原因：根据报单号查询该报单号所对应的所有赔案信息
    修 改 人：万泽辉
    修改日期：2005.10.26
   =========================================================================
**/
function queryGrid()
{
    var strSQL = "";
    var tPolNo = fm.PolNo.value;
	
		var sqlid1="ClaimGetQuerySql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("sys.ClaimGetQuerySql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(tPolNo);//指定传入的参数
	    strSQL=mySql1.getString();	
	
//	strSQL = " select distinct(a.ClmNo),(select codename from ldcode where code = c.ClmState and codetype = 'llclaimstate'),b.CustomerNo,b.CustomerName,(case b.CustomerSex when '1' then '男' when '0' then '女' end),b.AccStartDate from llclaimpolicy a,llcase b,llregister c where 1=1 and a.CaseNo = b.CaseNo and c.RgtNo = b.CaseNo "
//	       + " and PolNo = '" + tPolNo + "'"
//           + " order by a.ClmNo ";
    turnPage.queryModal(strSQL,PolGrid);
    
}

/**=========================================================================
    修改状态：开始
    修改原因：相应PolGridClick点击事件，选择一条赔案记录
    修 改 人：万泽辉
    修改日期：2005.10.23
   =========================================================================
**/

function PolGridClick()
{
	  var selno = PolGrid.getSelNo() - 1;
	  //随机数的使用在于区分循环查询
	  var rand  = Math.random()*1000;
    	  rand = parseInt(rand);
	  var tClmNo = PolGrid.getRowColData(selno,1);
	  var strUrl="../claim/LLClaimQueryInput.jsp?claimNo="+tClmNo+"&phase=0";
	     window.open(strUrl,rand,'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

