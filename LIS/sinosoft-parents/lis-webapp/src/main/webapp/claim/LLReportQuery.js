//���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();
//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
    showInfo.close();
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
        alert("��LLLdPersonQuery.js-->resetForm�����з����쳣:��ʼ���������!");
    }
} 

//ȡ����ť��Ӧ����
function cancelForm()
{
  
}
 
//�ύǰ��У�顢����  
function beforeSubmit()
{
  //��Ӳ���  
}           

// ��ѯ��ť
function easyQueryClick()
{
    // ��ʼ�����
    initLLReportGrid();
    var strSQL = "";
//    select RptNo,RptorName,RptorPhone,RptorAddress,Relation,RptMode,AccidentSite,AccidentReason,RptDate,MngCom,Operator from LLReport where
    /*strSQL = "select RptNo,RptorName,RptorPhone,RptorAddress,Relation,RptMode,AccidentSite,RptDate,MngCom,AccidentReason from LLReport where 1=1 "
           + getWherePart( 'RptNo' )
           + getWherePart( 'RptorName' )
           + getWherePart( 'RptorPhone' )
           + getWherePart( 'RptorAddress' )
           + getWherePart( 'Relation' )
           + getWherePart( 'RptMode' )
           + " order by RptNo";*/
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLReportQueryInputSql");
	mySql.setSqlId("LLReportQuerySql1");
	mySql.addSubPara(fm.RptNo.value ); 
	mySql.addSubPara(fm.RptorName.value ); 
	mySql.addSubPara(fm.RptorPhone.value ); 
	mySql.addSubPara(fm.RptorAddress.value ); 
	mySql.addSubPara(fm.Relation.value ); 
	mySql.addSubPara(fm.RptMode.value ); 
//    alert(strSQL);
    turnPage.queryModal(mySql.getString(),LLReportGrid);

}

//////ѡ��ҳ���ϲ�ѯ���ֶζ�Ӧ��"select"�е�λ��
////function getSelArray()
////{
////    var arrSel = new Array();  
////    arrSel[0] = 0;
////    arrSel[1] = 1;
////    arrSel[2] = 2;
////    arrSel[3] = 3;
////    arrSel[4] = 4;
////    arrSel[5] = 5;
//////    arrSel[6] = 6;
////    return arrSel;  
////}
////
//////���ز�ѯ����������
////function displayEasyResult( arrQueryResult )
////{
////    var i, j, m, n;
////    var arrSelected = new Array();
////    var arrResult = new Array();
////
////    if( arrQueryResult == null )
////        alert( "û���ҵ���ص�����!" );
////    else
////    {
////        // ��ʼ�����
////        initLLReportGrid();
////        LLReportGrid.recordNo = (currBlockIndex - 1) * MAXMEMORYPAGES * MAXSCREENLINES + (currPageIndex - 1) * MAXSCREENLINES;
////        LLReportGrid.loadMulLine(LLReportGrid.arraySave);
////        arrGrid = arrQueryResult;
////        // ת��ѡ��������
////        arrSelected = getSelArray();
////        arrResult = chooseArray( arrQueryResult, arrSelected );
////        // ��ʾ��ѯ���
////        n = arrResult.length;
////        for( i = 0; i < n; i++ )
////        {
////            m = arrResult[i].length;
////            for( j = 0; j < m; j++ )
////            {
////                LLReportGrid.setRowColData( i, j+1, arrResult[i][j] );
////            }
////        }
////    //LLReportGrid.delBlankLine();
////    }
////}

////��ӦcheckBox�Ķ��¼����
//function returnParent()
//{
//  var len = LLReportGrid.mulLineCount-1;
//  var arr = new Array();
//
//  for ( i=0;i<len;i++)
//  {
//    if (LLReportGrid.getChkNo(i)==true)
//    {
//      
//      var alen = arr.length;
//      arr[arr.length] = new Array();
//      arr[alen][0] = LLReportGrid.getRowColData(i,1);
//      arr[alen][1] = LLReportGrid.getRowColData(i,2);    
//    }    
//  }
//  alert(arr);  
//  if ( arr.length>0)
//  {
//    top.opener.afterQueryLL(arr);
//    top.close();
//  }
//}

//��ӦRadioBox�ĵ���¼����
function returnParent()
{
    var i = LLReportGrid.getSelNo();
    if (i != 0)
    {      
        i = i - 1;
        var arr = new Array();
        arr[0] = LLReportGrid.getRowColData(i,1);
        arr[1] = LLReportGrid.getRowColData(i,2);  
        arr[2] = LLReportGrid.getRowColData(i,3);  
        arr[3] = LLReportGrid.getRowColData(i,4);
        arr[4] = LLReportGrid.getRowColData(i,5);
        arr[5] = LLReportGrid.getRowColData(i,6);
        arr[6] = LLReportGrid.getRowColData(i,7);  
        arr[7] = LLReportGrid.getRowColData(i,8);
        arr[8] = LLReportGrid.getRowColData(i,9);
        arr[9] = LLReportGrid.getRowColData(i,10);
    }
    if (arr)
    {
       top.opener.afterQueryLL2(arr);
       top.close();
    }
}
//
////����Ϊ��������,��1980-5-9 
//function getAge(birth)
//{
//    var now = new Date();
//    var nowYear = now.getFullYear();
//    var oneYear = birth.substring(0,4);
//    var age = nowYear - oneYear;
//    if (age <= 0)
//    {
//        age = 0
//    }
//    return age;
//}
