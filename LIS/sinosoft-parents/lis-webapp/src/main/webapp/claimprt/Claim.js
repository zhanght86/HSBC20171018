
var showInfo;
var turnPage = new turnPageClass(); 

// 查询按钮
function easyQueryClick()
{
	initPolGrid();	
	var strSQL = "select unique a.RgtNo, a.RealPay, a.EndCaseDate, b.grpname "
		+ " from LLClaim a,llreport b "
		+ " where a.ClmNo=b.rptno "
		+ " and b.grpcontno is not null "
		+ " and a.GiveType in ('0','1','4') "	//0给付、1拒付
        + " and a.ClmState  ='60' "//60 完成
        + getWherePart('a.ClmNo','RgtNo')
        + getWherePart('b.mngcom','ManageCom','like') 
        + getWherePart('a.EndCaseDate','StartDate','>=')
        + getWherePart('a.EndCaseDate','EndDate','<=')        
		+ " order by a.RgtNo, a.EndCaseDate ";
	//prompt("团险理赔批单打印案件查询:",strSQL);
	turnPage.queryModal(strSQL, PolGrid);
   	if(PolGrid.mulLineCount==0)
   	{
   		alert("没有查询到赔案信息！");
   		return false;
   	}
}

//1、理赔批单打印
function PrintClaimBatch()
{
	var selno = PolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要打印的赔案！");
	      return;	      
	}	
	
    document.all('RgtNo').value = PolGrid. getRowColData(selno,1);
    document.all('OperateFlag').value = 'ClaimBatch';
	fm.action = "./ClaimPrtSave.jsp";
    fm.target=".";
	document.getElementById("fm").submit();		
}

//2、支付清单
function PrintClaimPayBill()
{
	var selno = PolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要打印的赔案！");
	      return;	      
	}	
    document.all('RgtNo').value = PolGrid. getRowColData(selno,1);
    document.all('OperateFlag').value = 'ClaimPay';
	fm.action = "./ClaimPrtSave.jsp";
    fm.target=".";
	document.getElementById("fm").submit();
}

//3、赔款清单
function PrintClaimGetBill()
{
	var selno = PolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要打印的赔案！");
	      return;	      
	}	
    document.all('RgtNo').value = PolGrid. getRowColData(selno,1);
    document.all('OperateFlag').value = 'GetBill';
	fm.action = "./ClaimPrtSave.jsp";
    fm.target=".";
	document.getElementById("fm").submit();
}

//4、团险理赔决定通知书
function PrintClaimPayTR()
{
	var selno = PolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要打印的赔案！");
	      return;	      
	}	
    document.all('RgtNo').value = PolGrid. getRowColData(selno,1);
    document.all('OperateFlag').value = 'ClaimPayTR';
	fm.action = "./ClaimPrtSave.jsp";
    fm.target=".";
	document.getElementById("fm").submit();
}

