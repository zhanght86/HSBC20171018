var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";

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
    var varSrc = "&RptNo=" + PolGrid.getRowColData(PolGrid.getSelNo()-1,1);
    varSrc += "&RgtNo=" + PolGrid.getRowColData(PolGrid.getSelNo()-1,2);
    varSrc += "&Type=" + PolGrid.getRowColData(PolGrid.getSelNo()-1,3);
    varSrc += "&InsuredName=" + PolGrid.getRowColData(PolGrid.getSelNo()-1,4);
    varSrc += "&PolNo=" +fm.PolNo.value;
    var newWindow = window.open("./FrameCaseInfo.jsp?Interface=CaseInfoInput.jsp"+varSrc,"CaseInfoInput",'width=800,height=500,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=0,status=0');
  }
}

/**=========================================================================
    �޸�״̬����ʼ
    �޸�ԭ�򣺸��ݱ����Ų�ѯ�ñ���������Ӧ�������ⰸ��Ϣ
    �� �� �ˣ������
    �޸����ڣ�2005.10.26
   =========================================================================
**/
function queryGrid()
{
    var strSQL = "";
    var tPolNo = fm.PolNo.value;
	
		var sqlid1="ClaimGetQuerySql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("sys.ClaimGetQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(tPolNo);//ָ������Ĳ���
	    strSQL=mySql1.getString();	
	
//	strSQL = " select distinct(a.ClmNo),(select codename from ldcode where code = c.ClmState and codetype = 'llclaimstate'),b.CustomerNo,b.CustomerName,(case b.CustomerSex when '1' then '��' when '0' then 'Ů' end),b.AccStartDate from llclaimpolicy a,llcase b,llregister c where 1=1 and a.CaseNo = b.CaseNo and c.RgtNo = b.CaseNo "
//	       + " and PolNo = '" + tPolNo + "'"
//           + " order by a.ClmNo ";
    turnPage.queryModal(strSQL,PolGrid);
    
}

/**=========================================================================
    �޸�״̬����ʼ
    �޸�ԭ����ӦPolGridClick����¼���ѡ��һ���ⰸ��¼
    �� �� �ˣ������
    �޸����ڣ�2005.10.23
   =========================================================================
**/

function PolGridClick()
{
	  var selno = PolGrid.getSelNo() - 1;
	  //�������ʹ����������ѭ����ѯ
	  var rand  = Math.random()*1000;
    	  rand = parseInt(rand);
	  var tClmNo = PolGrid.getRowColData(selno,1);
	  var strUrl="../claim/LLClaimQueryInput.jsp?claimNo="+tClmNo+"&phase=0";
	     window.open(strUrl,rand,'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

