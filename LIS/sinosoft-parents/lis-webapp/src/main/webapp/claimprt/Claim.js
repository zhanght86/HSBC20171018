
var showInfo;
var turnPage = new turnPageClass(); 

// ��ѯ��ť
function easyQueryClick()
{
	initPolGrid();	
	var strSQL = "select unique a.RgtNo, a.RealPay, a.EndCaseDate, b.grpname "
		+ " from LLClaim a,llreport b "
		+ " where a.ClmNo=b.rptno "
		+ " and b.grpcontno is not null "
		+ " and a.GiveType in ('0','1','4') "	//0������1�ܸ�
        + " and a.ClmState  ='60' "//60 ���
        + getWherePart('a.ClmNo','RgtNo')
        + getWherePart('b.mngcom','ManageCom','like') 
        + getWherePart('a.EndCaseDate','StartDate','>=')
        + getWherePart('a.EndCaseDate','EndDate','<=')        
		+ " order by a.RgtNo, a.EndCaseDate ";
	//prompt("��������������ӡ������ѯ:",strSQL);
	turnPage.queryModal(strSQL, PolGrid);
   	if(PolGrid.mulLineCount==0)
   	{
   		alert("û�в�ѯ���ⰸ��Ϣ��");
   		return false;
   	}
}

//1������������ӡ
function PrintClaimBatch()
{
	var selno = PolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ��ӡ���ⰸ��");
	      return;	      
	}	
	
    document.all('RgtNo').value = PolGrid. getRowColData(selno,1);
    document.all('OperateFlag').value = 'ClaimBatch';
	fm.action = "./ClaimPrtSave.jsp";
    fm.target=".";
	document.getElementById("fm").submit();		
}

//2��֧���嵥
function PrintClaimPayBill()
{
	var selno = PolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ��ӡ���ⰸ��");
	      return;	      
	}	
    document.all('RgtNo').value = PolGrid. getRowColData(selno,1);
    document.all('OperateFlag').value = 'ClaimPay';
	fm.action = "./ClaimPrtSave.jsp";
    fm.target=".";
	document.getElementById("fm").submit();
}

//3������嵥
function PrintClaimGetBill()
{
	var selno = PolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ��ӡ���ⰸ��");
	      return;	      
	}	
    document.all('RgtNo').value = PolGrid. getRowColData(selno,1);
    document.all('OperateFlag').value = 'GetBill';
	fm.action = "./ClaimPrtSave.jsp";
    fm.target=".";
	document.getElementById("fm").submit();
}

//4�������������֪ͨ��
function PrintClaimPayTR()
{
	var selno = PolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ��ӡ���ⰸ��");
	      return;	      
	}	
    document.all('RgtNo').value = PolGrid. getRowColData(selno,1);
    document.all('OperateFlag').value = 'ClaimPayTR';
	fm.action = "./ClaimPrtSave.jsp";
    fm.target=".";
	document.getElementById("fm").submit();
}

