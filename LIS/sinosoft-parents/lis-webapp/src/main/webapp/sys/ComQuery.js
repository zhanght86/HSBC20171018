//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var arrDataSet
var showInfo;
var mDebug="0";
var tArr;
var turnPage = new turnPageClass();

//�ύ�����水ť��Ӧ����
function submitForm()
{
  var i = 0;
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//  showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
  var iWidth=550;      //�������ڵĿ��; 
  var iHeight=250;     //�������ڵĸ߶�; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();

//  initPolGrid();
  //showSubmitFrame(mDebug);
  document.getElementById("fm").submit(); //�ύ
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
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
    //var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
//	  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
//	  var iWidth=550;      //�������ڵĿ��; 
//	  var iHeight=350;     //�������ڵĸ߶�; 
//	  var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
//	  var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
//	  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
//
//	  showInfo.focus();

    //showDiv(operateButton,"true");
    //showDiv(inputButton,"false");
    //ִ����һ������
  }
}



//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm()
{
  try
  {
	  initForm();
  }
  catch(re)
  {
  	alert("��OLDComQuery.js-->resetForm�����з����쳣:��ʼ���������!");
  }
}

//ȡ����ť��Ӧ����
function cancelForm()
{
    showDiv(operateButton,"true");
    showDiv(inputButton,"false");
}

//�ύǰ��У�顢����
function beforeSubmit()
{
  //��Ӳ���
}

//Click�¼������������ͼƬʱ�����ú���
function addClick()
{
  //����������Ӧ�Ĵ���
  showDiv(operateButton,"false");
  showDiv(inputButton,"true");
}

//Click�¼�����������޸ġ�ͼƬʱ�����ú���
function updateClick()
{
  //����������Ӧ�Ĵ���
  alert("update click");
}

//Click�¼������������ѯ��ͼƬʱ�����ú���
function queryClick()
{
  //����������Ӧ�Ĵ���
  //window.showModalDialog("./ProposalQuery.jsp",window,"status:0;help:0;edge:sunken;dialogHide:0;dialogWidth=15cm;dialogHeight=12cm");
  //��ѯ���������һ��ģ̬�Ի��򣬲��ύ�������������ǲ�ͬ��
  //��ˣ����еĻ����Ҳ���Բ��ø�ֵ��
}

//Click�¼����������ɾ����ͼƬʱ�����ú���
function deleteClick()
{
  //����������Ӧ�Ĵ���
  alert("delete click");
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

//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,50,82,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}

function returnParent()
{
        var arrReturn = new Array();
	var tSel = ComGrid.getSelNo();



	if( tSel == 0 || tSel == null )
		top.close();
		//alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{

			try
			{
				//alert(tSel);
				arrReturn = getQueryResult();
				top.opener.afterQuery( arrReturn );
			}
			catch(ex)
			{
				alert( "û�з��ָ����ڵ�afterQuery�ӿڡ�" + ex );
			}
			top.close();

	}
}

function getQueryResult() {
	var arrSelected = null;
	tRow = ComGrid.getSelNo();
	if( tRow == 0 || tRow == null || arrDataSet == null )
		return arrSelected;
	arrSelected = new Array();
	var strSQL = "";
//	strSQL = "select ComCode,OutComCode,Name,ShortName,Address,ZipCode,Phone,Fax,EMail,WebAddress,SatrapName,Sign,ComCitySize,UpComCode,ComGrade,IsDirUnder from LDCom where ComCode='"+ComGrid.getRowColData(tRow-1,1)+"'"; 
	
	    var sqlid1="ComQuerySql1";
	 	var mySql1=new SqlClass();
	 	mySql1.setResourceName("sys.ComQuerySql");
	 	mySql1.setSqlId(sqlid1); //ָ��ʹ��SQL��id
	 	mySql1.addSubPara(ComGrid.getRowColData(tRow-1,1));//ָ���������
	 	strSQL = mySql1.getString();
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
	//�ж��Ƿ��ѯ�ɹ�
	if (!turnPage.strQueryResult) {
		alert("��ѯʧ�ܣ�");
		return false;
	}
	//��ѯ�ɹ������ַ��������ض�ά����
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
	return arrSelected;
}




// ��ѯ��ť
function easyQueryClick()
{
	// ��ʼ�����
	initComGrid();

	// ��дSQL���
	var strSQL = "";
//	strSQL = "select ComCode,Name,Address,Phone,SatrapName from LDCom where 1=1"
//	        + getWherePart('ComCode') 
//	        + getWherePart('OutComCode') 
//	        + getWherePart('Name') 
//	        + getWherePart('ShortName') 
//	        + getWherePart('Address') 
//	        + getWherePart('ZipCode') 
//	        + getWherePart('Phone') 
//	        + getWherePart('Fax') 
//	        + getWherePart('EMail') 
//	        + getWherePart('WebAddress') 
//	        + getWherePart('SatrapName') 
//	        + getWherePart('Sign') 
//	        + getWherePart('UpComCode') 
//	        + getWherePart('ComCitySize') 
//	        + getWherePart('IsDirUnder') 
//
//   
//	        + " order by ComCode";

	var sqlid2="ComQuerySql2";
 	var mySql2=new SqlClass();
 	mySql2.setResourceName("sys.ComQuerySql");
 	mySql2.setSqlId(sqlid2); //ָ��ʹ��SQL��id
 	mySql2.addSubPara(window.document.getElementsByName(trim("ComCode"))[0].value);//ָ���������
 	mySql2.addSubPara(window.document.getElementsByName(trim("OutComCode"))[0].value);
 	mySql2.addSubPara(window.document.getElementsByName(trim("Name"))[0].value);
 	mySql2.addSubPara(window.document.getElementsByName(trim("ShortName"))[0].value);
 	mySql2.addSubPara(window.document.getElementsByName(trim("Address"))[0].value);
 	mySql2.addSubPara(window.document.getElementsByName(trim("ZipCode"))[0].value);
 	mySql2.addSubPara(window.document.getElementsByName(trim("Phone"))[0].value);
 	mySql2.addSubPara(window.document.getElementsByName(trim("Fax"))[0].value);
 	mySql2.addSubPara(window.document.getElementsByName(trim("EMail"))[0].value);
 	mySql2.addSubPara(window.document.getElementsByName(trim("WebAddress"))[0].value);
 	mySql2.addSubPara(window.document.getElementsByName(trim("SatrapName"))[0].value);
 	mySql2.addSubPara(window.document.getElementsByName(trim("Sign"))[0].value);
 	mySql2.addSubPara(window.document.getElementsByName(trim("UpComCode"))[0].value);
 	mySql2.addSubPara(window.document.getElementsByName(trim("ComCitySize"))[0].value);
 	mySql2.addSubPara(window.document.getElementsByName(trim("IsDirUnder"))[0].value);
 	strSQL = mySql2.getString();
	
	
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("��ѯʧ�ܣ�");
    return false;
    }
//��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  //tArr = decodeEasyQueryResult(turnPage.strQueryResult);
  //turnPage.arrDataCacheSet = chooseArray(tArr,[0,2,4,6,10])
  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = ComGrid;

  //����SQL���
  turnPage.strQuerySql     = strSQL;

  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;

  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
 arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
 //tArr=chooseArray(arrDataSet,[0,2,4,6,10])
  //����MULTILINE������ʾ��ѯ���

  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  //displayMultiline(tArr, turnPage.pageDisplayGrid);
}