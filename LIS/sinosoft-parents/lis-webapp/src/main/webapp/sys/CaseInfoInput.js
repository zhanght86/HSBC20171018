//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var  turnPage = new turnPageClass();

//�ύ�����水ť��Ӧ����
function submitForm()
{
  var i = 0;
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
  var iWidth=550;      //�������ڵĿ��; 
  var iHeight=250;     //�������ڵĸ߶�; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();

  showSubmitFrame(mDebug);
  fm.action = './CasePolicySave.jsp';
  fm.submit(); //�ύ
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=350;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

  }
  else
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=350;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

  }
}

function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
    parent.fraMain.rows = "0,0,0,0,*";
  }
  else
  {
    parent.fraMain.rows = "0,0,0,0,*";
  }
}

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

function ShowCheckInfo()
{ 
	var RgtNo = fm.RgtNo.value;
	fm.action = "./ShowCheckInfo.jsp?RgtNo="+RgtNo;
	fm.submit();
}

function ShowCaseDetail()
{
	var varSrc ;
	var rowNum=CheckGrid.mulLineCount ;
	var tNum;
	if(rowNum!=0)
	{
		tNum = CheckGrid.getSelNo();
		if(tNum<1)
		{
			alert("����ѡ��һ����¼");
			return;
		}
		if(tNum>=1)
		{
			var varSrc 	= "&ClmUWNo=" + CheckGrid. getRowColData(tNum-1,4);
	    varSrc += "&InsuredName=" + fm.InsuredName.value;
    	varSrc += "&PolNo=" + fm.PolNo.value;
    	varSrc += "&RgtNo=" + fm.RgtNo.value;
    	varSrc += "&Clmer="      + CheckGrid.getRowColData(tNum-1,3);
    	varSrc += "&ClmUWer="    + CheckGrid.getRowColData(tNum-1,5);
			varSrc += "&CheckType="  + CheckGrid.getRowColData(tNum-1,1);
			varSrc += "&LPJC="  + CheckGrid.getRowColData(tNum-1,2);
			var newWindow = window.open("./FrameCaseDetail.jsp?Interface=CaseDetailInput.jsp"+varSrc,"CaseDetailInput",'width=800,height=500,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=0,status=0');
//			fm.submit();	
		}
	}
	else
	{
		
			var varSrc 	= "";
	    varSrc += "&InsuredName=" + fm.InsuredName.value;
  	  varSrc += "&RgtNo=" + fm.RgtNo.value;
    	varSrc += "&PolNo=" + fm.PolNo.value;
    	varSrc += "&RgtNo=" + fm.RgtNo.value;
    	varSrc += "&Conclusion=" + "";
    	varSrc += "&ClmUWer=" + "";
			var newWindow = window.open("./FrameCaseDetail.jsp?Interface=CaseDetailInput.jsp"+varSrc,"CaseDetailInput",'width=800,height=500,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=0,status=0');

	}
}

function displayQueryResult1(strResult)
{
//	alert("��ʼִ��");
  strResult = Conversion(strResult);
  var filterArray          = new Array(14,4,3,0,2);
  turnPage.strQueryResult  = strResult;
  turnPage.useSimulation   = 1;
  var tArr = decodeEasyQueryResult(turnPage.strQueryResult);
  turnPage.arrDataCacheSet = chooseArray(tArr, filterArray);
  turnPage.pageDisplayGrid = CheckGrid;
  turnPage.pageIndex       = 0;
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  if (turnPage.queryAllRecordCount > turnPage.pageLineNum) {
    try { window.divPage.style.display = ""; } catch(ex) { }
  } else {
    try { window.divPage.style.display = "none"; } catch(ex) { }
  }
  turnPage.blockPageNum = turnPage.queryAllRecordCount / turnPage.pageLineNum;
}
