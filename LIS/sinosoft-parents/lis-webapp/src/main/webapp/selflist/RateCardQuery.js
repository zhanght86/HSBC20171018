//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���


function EasyQueryClick()
{
    // ��дSQL���
	var str="";
	//�ѷ��ŵ����������еĵ�֤
//	var strSQL="select a.riskcode,(select riskname from lmrisk where a.riskcode=lmrisk.riskcode),a.insuyear,a.insuyearflag,a.prem,a.mult,a.productplan from ratecard a"
//	         +" where 1=1"
//             +getWherePart( 'a.riskcode','Riskcode' )
//             +getWherePart( 'a.ProductPlan','ProductPlan' )
//             +getWherePart( 'a.InsuYear','InsuYear' )
//             +getWherePart( 'a.InsuYearFlag','InsuYearFlag' )
//             +getWherePart( 'a.Prem','Prem' );

	var sqlid1="RateCardQuerySql1";
 	var mySql1=new SqlClass();
 	mySql1.setResourceName("selflist.RateCardQuerySql");
 	mySql1.setSqlId(sqlid1); //ָ��ʹ��SQL��id
 	mySql1.addSubPara(window.document.getElementsByName(trim("Riskcode"))[0].value);//ָ���������
 	mySql1.addSubPara(window.document.getElementsByName(trim("ProductPlan"))[0].value);
 	mySql1.addSubPara(window.document.getElementsByName(trim("InsuYear"))[0].value);
 	mySql1.addSubPara(window.document.getElementsByName(trim("InsuYearFlag"))[0].value);
 	mySql1.addSubPara(window.document.getElementsByName(trim("Prem"))[0].value);
 	var strSQL = mySql1.getString();
	
	
  	  //prompt("",strSQL);
	  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1); 
	  
	  //�ж��Ƿ��ѯ�ɹ�
	  if (!turnPage.strQueryResult) 
	  {
	  	queryflag=0;
	    alert("û����Ҫ��ѯ�Ŀ������ʶ�����Ϣ��");
	    return false;
	  }
	    
	  queryflag=1;
	  //��ѯ�ɹ������ַ��������ض�ά����
	  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	  
	  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
	  turnPage.pageDisplayGrid = RateCardGrid;    
	          
	  //����SQL���
	  turnPage.strQuerySql     = strSQL; 
	  
	  //���ò�ѯ��ʼλ��
	  turnPage.pageIndex       = 0;  
	  
	  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
	  arrDataSet= turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
	 //tArr=chooseArray(arrDataSet,[0]) 
	  //����MULTILINE������ʾ��ѯ���
	  
	  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

}

function displayQueryResult(strResult)
 {
  //��MULTILINE���,ʹMULTILINE��ʾʱ���ֶ�λ��ƥ�����ݿ���ֶ�λ��
  strResult = Conversion(strResult);
  var filterArray = new Array(0,1,2,3,4,5);
  //�����ѯ����ַ���
  turnPage.strQueryResult  = strResult;

  //ʹ��ģ������Դ
  turnPage.useSimulation   = 1;

  //��ѯ�ɹ������ַ��������ض�ά����
  var tArr = decodeEasyQueryResult(turnPage.strQueryResult);

  //���˶�ά���飬ʹ֮��MULTILINEƥ��
  turnPage.arrDataCacheSet = chooseArray(tArr, filterArray);
  turnPage.pageDisplayGrid = RateCardGrid;

  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;

  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);

  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

  //�����Ƿ���ʾ��ҳ��ť
  if (turnPage.queryAllRecordCount > turnPage.pageLineNum) 
  {
    try { window.divPage.style.display = ""; } catch(ex) { }
  } 
  else 
  {
    try { window.divPage.style.display = "none"; } catch(ex) { }
  }

  //���뽫������������Ϊһ�����ݿ�
  turnPage.blockPageNum = turnPage.queryAllRecordCount / turnPage.pageLineNum;

}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{

  //showInfo.close();
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
  	alert("��Proposal.js-->resetForm�����з����쳣:��ʼ���������!");
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
	alert("query click");
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
			parent.fraMain.rows = "0,0,0,0,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}

	parent.fraMain.rows = "0,0,0,0,*";
}

function ReturnData()
{
   
   fm.OperateType.value = "RETURNDATA";
   var tRow=RateCardGrid.getSelNo();


   if (tRow==0)
   {
   		alert("����ѡ��һ����¼");
  		return;
   }
  
   var tRiskCode = RateCardGrid.getRowColData(tRow-1,1);
   var tPrem   = RateCardGrid.getRowColData(tRow-1,5);
   top.location.href="./RateCardQueryDetail.jsp?RiskCode="+tRiskCode+"&Prem="+tPrem;
}
