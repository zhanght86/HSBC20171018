/*
  //�����ˣ�������
*/
var showInfo;
var mDebug="0";
var FlagDel;//��delete�������ж�ɾ�����Ƿ�ɹ�
var turnPage = new turnPageClass();

function displayQueryResult(strResult) {
  //��MULTILINE���,ʹMULTILINE��ʾʱ���ֶ�λ��ƥ�����ݿ���ֶ�λ��

  strResult = Conversion(strResult);
  var filterArray          = new Array(0,12,1);

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
    try { window.divPage.style.display = ""; } catch(ex) { }
  } else {
    try { window.divPage.style.display = "none"; } catch(ex) { }
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

//������ʼ���ڽ��в�ѯ��Ҫ�����ڷ�Χ�ڵĳɹ�����
function showSerialNo()
{
	
 	 var strSQL = "";
  if((fm.Station.value=="")||(fm.Station.value=="null"))
	{		
//  strSQL = " select OtherNo,StandbyFlag2 ,makedate "
//  			 + " from LOPRTManager where 1=1 "
//  			 + " and ManageCom like '"+ComCode+"%%'"
//  			 + " and code = '42' and StateFlag = '0'"
//  			 + getWherePart( 'LOPRTManager.MakeDate','StartDate','>=' )
//  			 + getWherePart( 'LOPRTManager.MakeDate','EndDate','<=')
  			 
  			var  StartDate0 = window.document.getElementsByName(trim("StartDate"))[0].value;
		  	var  EndDate0 = window.document.getElementsByName(trim("EndDate"))[0].value;
			var sqlid0="PPolPauseInputSql0";
			var mySql0=new SqlClass();
			mySql0.setResourceName("f1print.PPolPauseInputSql"); //ָ��ʹ�õ�properties�ļ���
			mySql0.setSqlId(sqlid0);//ָ��ʹ�õ�Sql��id
			mySql0.addSubPara(ComCode);//ָ������Ĳ���
			mySql0.addSubPara(StartDate0);//ָ������Ĳ���
			mySql0.addSubPara(EndDate0);//ָ������Ĳ���
			strSQL=mySql0.getString();
  			  
	}
	else
	{
//  strSQL = " select OtherNo,StandbyFlag2 ,makedate "
//  			 + " from LOPRTManager where 1=1 "
//  			 + " and ManageCom like '"+fm.Station.value+"%%'"
//  			 + " and code = '42' and StateFlag = '0'"
//  			 + getWherePart( 'LOPRTManager.MakeDate','StartDate','>=' )
//  			 + getWherePart( 'LOPRTManager.MakeDate','EndDate','<=')
  			 
  			var  StartDate1 = window.document.getElementsByName(trim("StartDate"))[0].value;
		  	var  EndDate1 = window.document.getElementsByName(trim("EndDate"))[0].value;
			var sqlid1="PPolPauseInputSql1";
			var mySql1=new SqlClass();
			mySql1.setResourceName("f1print.PPolPauseInputSql"); //ָ��ʹ�õ�properties�ļ���
			mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
			mySql1.addSubPara(fm.Station.value);//ָ������Ĳ���
			mySql1.addSubPara(StartDate1);//ָ������Ĳ���
			mySql1.addSubPara(EndDate1);//ָ������Ĳ���
			strSQL=mySql1.getString();
  			  
	}
	
	if(!verifyInput()) 
  {
  	return false;
  }
  initBillGrid();
 
  			
 	
 	
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
 		if (!turnPage.strQueryResult) 
 		{
    	alert("�ڸù��������û�����������ı�����ֹ��Ϣ��¼");
    	return "";
  	}
  
  	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  	turnPage.pageDisplayGrid = BillGrid;    
  	turnPage.strQuerySql     = strSQL; 
  	turnPage.pageIndex       = 0;  
  	var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  	displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  	return true;

  
}

//����ѡ�е����κš����б��롢����������в�ѯ����ִ�д�ӡ���ܣ�
function PrintBill()
{
	var MainPolNo = "";
	var tRow=BillGrid.getSelNo();
  	if (tRow==0)
   	{
   		alert("�����Ƚ���ѡ��");
  		return;
  	}
    else
    {
    	strGetNoticeNo = BillGrid.getRowColData(tRow-1,2);//�õ�����֪ͨ�����
//      alert("����֪ͨ�������"+strGetNoticeNo);
    	var i = 0;
    	fm.action = "./PPolPausePrint.jsp?MainPolNo="+strGetNoticeNo;
    	fm.target="f1print";
//    	showInfo.close();
    	document.getElementById("fm").submit();
    }
}

//����ѡ�е����κš����б��롢����������в�ѯ����ִ�д�ӡ���ܣ�
function QueryBill()
{
   fm.action = "./PPolPauseQuery.jsp";
   fm.target="f1print";
//   showInfo.close();
   document.getElementById("fm").submit();
}