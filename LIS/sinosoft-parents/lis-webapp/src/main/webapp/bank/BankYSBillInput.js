/*
  //�����ˣ�������
  //�޸��ˣ�������
  //�޸����ݣ�

  //���ļ��а����ͻ�����Ҫ����ĺ������¼�
*/
var showInfo;
var mDebug="0";
var FlagDel;//��delete�������ж�ɾ�����Ƿ�ɹ�
var turnPage = new turnPageClass();

function displayQueryResult(strResult) {
  //��MULTILINE���,ʹMULTILINE��ʾʱ���ֶ�λ��ƥ�����ݿ���ֶ�λ��
  
  strResult = Conversion(strResult);
  var filterArray          = new Array(0, 1, 13, 14);
  
  //�����ѯ����ַ���
  turnPage.strQueryResult  = strResult;
  
  //ʹ��ģ������Դ
  turnPage.useSimulation   = 1;
  
  //��ѯ�ɹ������ַ��������ض�ά����
  var tArr = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���˶�ά���飬ʹ֮��MULTILINEƥ��
  turnPage.arrDataCacheSet = chooseArray(tArr, filterArray);
  //alert(turnPage.arrDataCacheSet);
  
  
  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = BillGrid;             
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);	
  
  //�����Ƿ���ʾ��ҳ��ť
  if (turnPage.queryAllRecordCount > turnPage.pageLineNum) {
    try { window.divPage.style.display = ""; } catch(ex) { alert(ex);}
  } else {
    try { window.divPage.style.display = "none"; } catch(ex) { alert(ex);}
  }
  //���뽫������������Ϊһ�����ݿ�
  turnPage.blockPageNum = turnPage.queryAllRecordCount / turnPage.pageLineNum;
  
}


function afterSubmit( FlagStr, content )
{
  FlagDel = FlagStr;

    showInfo.close();
    if (FlagStr == "Fail" )
    {
      var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
			//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();

    }
    else
    {
    //	var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
		//	showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
    	showDiv(inputButton,"false");

    	//ִ����һ������
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

//������ʼ���ڽ��в�ѯ��Ҫ�����ڷ�Χ�ڵ����κ���
function showSerialNo()
{
	if ((fm.StartDate.value == "") || (fm.EndDate.value == "")
			|| (fm.StartDate.value == "null") || (fm.EndDate.value == "null")) {
		alert("����������ʼ���ںͽ������ڣ�");
	}
	fm.Flag.value = "YS";
	fm.TFFlag.value = "F";
	var i = 0;
	var showStr = "���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr;
	//showInfo = window.showModelessDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	initBillGrid();
	fm.action = './ShowBill.jsp';
	document.getElementById("fm").submit(); // �ύ
}

// ����ѡ�е����κš����б��롢����������в�ѯ����ִ�д�ӡ���ܣ�
function PrintBill()
{
	var tRow=BillGrid.getSelNo();
  	if (tRow==0)
   	{
   		alert("�����Ƚ���ѡ��");
  		return;
  	}
    else
    {
    	var tCol=1;
    	tBillNo = BillGrid.getRowColData(tRow-1,tCol);
    	fm.BillNo.value=tBillNo;
    	fm.selBankCode.value=BillGrid.getRowColData(tRow-1,tCol+1);
    	fm.action = "./PrintBill.jsp";
    	fm.target="f1print";
    	showInfo.close();
    	document.getElementById("fm").submit();
    }
}
