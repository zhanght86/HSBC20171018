//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
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
  if(cDebug=="1") {
    parent.fraMain.rows = "0,0,0,0,*";
  }
  else {
    parent.fraMain.rows = "0,0,82,82,*";
  }
}

// ��ѯ��ť
function easyQueryClick()
{          
   // ��ʼ�����
   
   initEdorPrintGrid();
   	
   // ��дSQL���  �Ȳ��Ҹ���
   var strSQL = "";
   var strSQL1 = "";	
//   strSQL = "select LPEdorItem.EdorAppNo,LPEdorItem.EdorNo,LPEdorItem.ContNo,LPEdorItem.EdorType from LPEdorItem  where  LPEdorItem.EdorNo in (select EdorNo from lpedorprint) "			 
//				 + getWherePart( 'LPEdorItem.EdorNo','EdorNo' )
//				 + getWherePart( 'LPEdorItem.ContNo','ContNo' )
//				 + getWherePart( 'LPEdorItem.EdorAppNo','EdorAppNo' )
//                 + getWherePart( 'LPEdorItem.EdorType','EdorType' )
//				 +" union all  ";
   
    var  EdorNo1 = window.document.getElementsByName(trim("EdorNo"))[0].value;
	var  ContNo1 = window.document.getElementsByName(trim("ContNo"))[0].value;
	var  EdorAppNo1 = window.document.getElementsByName(trim("EdorAppNo"))[0].value;
	var  EdorType1 = window.document.getElementsByName(trim("EdorType"))[0].value;
	 var sqlid1="ChangeEdorPrintSql1";
	 var mySql1=new SqlClass();
	 mySql1.setResourceName("bq.ChangeEdorPrintSql");
	 mySql1.setSqlId(sqlid1);//ָ��ʹ��SQL��id
	 mySql1.addSubPara(EdorNo1);//ָ���������
	 mySql1.addSubPara(ContNo1);//ָ���������
	 mySql1.addSubPara(EdorAppNo1);//ָ���������
	 mySql1.addSubPara(EdorType1);//ָ���������
	 strSQL = mySql1.getString();
   
				 
//   strSQL1 = "select LPGrpEdorItem.EdorAppNo,LPGrpEdorItem.EdorNo,LPGrpEdorItem.GrpContNo,LPGrpEdorItem.EdorType from LPGrpEdorItem  where  LPGrpEdorItem.EdorNo in (select EdorNo from lpedorprint)  "			 
//				 + getWherePart( 'LPGrpEdorItem.EdorNo','EdorNo' )
//				 + getWherePart( 'LPGrpEdorItem.GrpContNo','ContNo' )
//				 + getWherePart( 'LPGrpEdorItem.EdorAppNo','EdorAppNo' )
//				 + getWherePart( 'LPGrpEdorItem.EdorType','EdorType' )
//				 + " ";
	 
    var  EdorNo2 = window.document.getElementsByName(trim("EdorNo"))[0].value;
	var  ContNo2 = window.document.getElementsByName(trim("ContNo"))[0].value;
	var  EdorAppNo2 = window.document.getElementsByName(trim("EdorAppNo"))[0].value;
	var  EdorType2 = window.document.getElementsByName(trim("EdorType"))[0].value;
	 var sqlid2="ChangeEdorPrintSql2";
	 var mySql2=new SqlClass();
	 mySql2.setResourceName("bq.ChangeEdorPrintSql");
	 mySql2.setSqlId(sqlid2);//ָ��ʹ��SQL��id
	 mySql2.addSubPara(EdorNo2);//ָ���������
	 mySql2.addSubPara(ContNo2);//ָ���������
	 mySql2.addSubPara(EdorAppNo2);//ָ���������
	 mySql2.addSubPara(EdorType2);//ָ���������
	 strSQL1 = mySql2.getString();			 
   
    strSQL = strSQL + strSQL1;
        
    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 
    
    if (!turnPage.strQueryResult) {
            return false;	
    }
  

  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����
  turnPage.pageDisplayGrid = EdorPrintGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  arrGrid = arrDataSet;
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

}

function getQueryResult()
{
  var arrSelected = null;
  tRow = EdorPrintGrid.getSelNo();
  if( tRow == 0 || tRow == null || arrGrid == null )
      return arrSelected;
  arrSelected = new Array();
  //������Ҫ���ص�����
  arrSelected[0] = EdorPrintGrid.getRowData(tRow-1);
	
  return arrSelected;
}


function getbqPrintToXML()
{
    var arrReturn = new Array();
    //var tSel = EdorPrintGrid.getSelNo();
    if( document.all('EdorNo1').value == "" || document.all('FileName').value =="" )
        alert( "����������������ļ������ٵ���������ݰ�ť��" );
    else
    {
        //arrReturn = getQueryResult();
      	//document.all('PolNo').value = EdorPrintGrid.getRowColData(tSel-1,1);
      	//document.all('EdorAppNo').value=EdorPrintGrid.getRowColData(tSel-1,2);	
              //document.all('EdorNo').value = EdorPrintGrid.getRowColData(tSel-1,3);
      	document.all("Transact").value = "PrintToXML"
			
        var showStr="���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
        //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
        fm.submit();
    }
}

function setXMLTobqPrint()
{
    var arrReturn = new Array();
    //var tSel = EdorPrintGrid.getSelNo();
    if( document.all('FileName').value == "" || document.all('EdorNo1').value =="" )
        alert( "�������ļ������������룬�ٵ���������ݰ�ť��" );
    else
    {
      document.all("Transact").value = "XMLToPrint"
		
      var showStr="���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
      var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
      //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
      fm.submit();
    }
}

function afterSubmit( FlagStr, content, result )
{
  showInfo.close();
  
  if (result!="") {
    result = Conversion(result);
    fm.EdorXml.value = result;
  }
  else {
    if (FlagStr == "Fail" )
    {             
      var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
      //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
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
      var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
      //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    }
    
    fm.EdorXml.value = "";
  }

}

//����MultiLine�ĵ�ѡ��ťʱ����
function reportDetailClick(parm1, parm2) {	
  document.all('EdorNo1').value = EdorPrintGrid.getRowColData(EdorPrintGrid.getSelNo()-1, 2);
  document.all("Transact").value = "DisplayToXML"
  
  //showSubmitFrame("0");
  var showStr="���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  fm.submit();
}

function setXMLTobqPrint2()
{
    var arrReturn = new Array();
    if( document.all('EdorXml').value == "" || document.all('EdorNo1').value =="" )
        alert( "�������ļ������������룬�ٵ���������ݰ�ť��" );
    else
    {
      document.all("Transact").value = "DisplayXMLToPrint"
		
      var showStr="���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
      var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
      //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();	  
      fm.submit();
    }
}