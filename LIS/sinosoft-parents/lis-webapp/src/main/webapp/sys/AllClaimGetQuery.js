var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();

//��ʾdiv����һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
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

//��ʾfrmSubmit��ܣ���������
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
	// ��ʼ�����
	initPolGrid();	
   fm.action = "./AllShowCaseInfo.jsp";
   fm.submit();
}


function displayQueryResult1(strResult)
{
  strResult = Conversion(strResult);
  var filterArray          = new Array(5,3,2,4,14,28,30);
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
//����¼�е���Ϣ��ʾ����һ��������
function ShowDetailInfo()
{
  if (PolGrid.getSelNo()==0)
  {
    alert("����ѡ��һ���ְ���");
    return;
  }
  else
  {
    var varSrc = "&RptNo=" + PolGrid.getRowColData(PolGrid.getSelNo()-1,2);
    varSrc += "&RgtNo=" + PolGrid.getRowColData(PolGrid.getSelNo()-1,3);
    varSrc += "&Type=" + PolGrid.getRowColData(PolGrid.getSelNo()-1,4);
    varSrc += "&InsuredName=" + PolGrid.getRowColData(PolGrid.getSelNo()-1,5);
    varSrc += "&PolNo=" +PolGrid.getRowColData(PolGrid.getSelNo()-1,1);
    var newWindow = window.open("./FrameCaseInfo.jsp?Interface=CaseInfoInput.jsp"+varSrc,"CaseInfoInput",'width=800,height=500,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=0,status=0');
  }
}
