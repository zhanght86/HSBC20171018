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
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
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
    initPersonGrid();
    var strSQL = "";
   /* strSQL = "select CustomerNo,Name,Sex,Birthday,IDType,IDNo,VIPValue from LDPerson where 1=1 "
           + getWherePart( 'CustomerNo' )
           + getWherePart( 'Name',null,'like' )
           + getWherePart( 'Sex' )
           + getWherePart( 'Birthday' )
           + getWherePart( 'IDType' )
           + getWherePart( 'IDNo' );*/
           mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLLdPersonQueryInputSql");
			mySql.setSqlId("LLLdPersonQuerySql1");
			mySql.addSubPara(fm.CustomerNo.value ); 
			mySql.addSubPara(fm.Name.value ); 
			mySql.addSubPara(fm.Sex.value ); 
			mySql.addSubPara(fm.Birthday.value ); 
			mySql.addSubPara(fm.IDType.value ); 
			mySql.addSubPara(fm.IDNo.value ); 
           if (fm.ContNo.value != "")
           {
//               strSQL = strSQL + " and CustomerNo in (select b.insuredno from LcCont a,LCInsured b where a.contno = b.contno and a.contno = '"+fm.ContNo.value+"' union select b.AppntNo from LcCont a,lcappnt b where a.contno = b.contno and a.contno = '"+fm.ContNo.value+"')";
              // strSQL = strSQL + " and CustomerNo in (select b.insuredno from LcCont a,LCInsured b where a.contno = b.contno and a.contno = '"+fm.ContNo.value+"')";
           	mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLLdPersonQueryInputSql");
			mySql.setSqlId("LLLdPersonQuerySql2");
			mySql.addSubPara(fm.CustomerNo.value ); 
			mySql.addSubPara(fm.Name.value ); 
			mySql.addSubPara(fm.Sex.value ); 
			mySql.addSubPara(fm.Birthday.value ); 
			mySql.addSubPara(fm.IDType.value ); 
			mySql.addSubPara(fm.IDNo.value ); 
			mySql.addSubPara(fm.ContNo.value ); 
           }
           if (fm.GrpContNo.value != "")
           {
//               strSQL = strSQL + " and CustomerNo in (select AppntNo from LCCont where GrpContNo = '"+fm.GrpContNo.value+"' union select InsuredNo from LCCont where GrpContNo = '"+fm.GrpContNo.value+"')";
             //  strSQL = strSQL + " and CustomerNo in (select b.insuredno from LcCont a,LCInsured b where a.contno = b.contno and a.GrpContNo = '"+fm.GrpContNo.value+"' union select b.AppntNo from LcCont a,lcappnt b where a.contno = b.contno and a.GrpContNo = '"+fm.GrpContNo.value+"')";
           	mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLLdPersonQueryInputSql");
			mySql.setSqlId("LLLdPersonQuerySql3");
			mySql.addSubPara(fm.CustomerNo.value ); 
			mySql.addSubPara(fm.Name.value ); 
			mySql.addSubPara(fm.Sex.value ); 
			mySql.addSubPara(fm.Birthday.value ); 
			mySql.addSubPara(fm.IDType.value ); 
			mySql.addSubPara(fm.IDNo.value ); 
			mySql.addSubPara(fm.ContNo.value ); 
			mySql.addSubPara(fm.GrpContNo.value ); 
			mySql.addSubPara(fm.GrpContNo.value ); 
           }
           //strSQL = strSQL + " order by CustomerNo";
//    alert(strSQL);
//    execEasyQuery(strSQL);
    turnPage.queryModal(mySql.getString(), PersonGrid);
}

////ѡ��ҳ���ϲ�ѯ���ֶζ�Ӧ��"select"�е�λ��
//function getSelArray()
//{
//    var arrSel = new Array();  
//    arrSel[0] = 0;
//    arrSel[1] = 1;
//    arrSel[2] = 2;
//    arrSel[3] = 3;
//    arrSel[4] = 4;
//    arrSel[5] = 5;
//    arrSel[6] = 6;
//    return arrSel;  
//}
//
////���ز�ѯ����������
//function displayEasyResult( arrQueryResult )
//{
//    var i, j, m, n;
//    var arrSelected = new Array();
//    var arrResult = new Array();
//
//    if( arrQueryResult == null )
//        alert( "û���ҵ���ص�����!" );
//    else
//    {
//        // ��ʼ�����
//        initPersonGrid();
//        PersonGrid.recordNo = (currBlockIndex - 1) * MAXMEMORYPAGES * MAXSCREENLINES + (currPageIndex - 1) * MAXSCREENLINES;
//        PersonGrid.loadMulLine(PersonGrid.arraySave);
//        arrGrid = arrQueryResult;
//        // ת��ѡ��������
//        arrSelected = getSelArray();
//        arrResult = chooseArray( arrQueryResult, arrSelected );
//        // ��ʾ��ѯ���
//        n = arrResult.length;
//        for( i = 0; i < n; i++ )
//        {
//            m = arrResult[i].length;
//            for( j = 0; j < m; j++ )
//            {
//                PersonGrid.setRowColData( i, j+1, arrResult[i][j] );
//            }
//        }
//    //PersonGrid.delBlankLine();
//    }
//}

////��ӦcheckBox�Ķ��¼����
//function returnParent()
//{
//  var len = PersonGrid.mulLineCount-1;
//  var arr = new Array();
//
//  for ( i=0;i<len;i++)
//  {
//    if (PersonGrid.getChkNo(i)==true)
//    {
//      
//      var alen = arr.length;
//      arr[arr.length] = new Array();
//      arr[alen][0] = PersonGrid.getRowColData(i,1);
//      arr[alen][1] = PersonGrid.getRowColData(i,2);    
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
    var i = PersonGrid.getSelNo();
    if (i != 0)
    {      
        i = i - 1;
        var arr = new Array();
        var birth = PersonGrid.getRowColData(i,4);
        arr[0] = PersonGrid.getRowColData(i,1);
        arr[1] = PersonGrid.getRowColData(i,2);  
        arr[2] = PersonGrid.getRowColData(i,3);  
        arr[3] = getAge(birth);
        arr[4] = PersonGrid.getRowColData(i,7);  
    }
    if (arr)
    {
       top.opener.afterQueryLL(arr);
    }
    top.close();
}

////��Ӧ���ز��ر�
//function returnParent2()
//{
//    var i = PersonGrid.getSelNo();
//    if (i != 0)
//    {      
//        i = i - 1;
//        var arr = new Array();
//        var birth = PersonGrid.getRowColData(i,4);
//        arr[0] = PersonGrid.getRowColData(i,1);
//        arr[1] = PersonGrid.getRowColData(i,2);  
//        arr[2] = PersonGrid.getRowColData(i,3);  
//        arr[3] = getAge(birth);
//        arr[4] = PersonGrid.getRowColData(i,7);  
//    }
//    if (arr)
//    {
//       top.opener.afterQueryLL(arr);
//       alert("��¼�ѷ���!");
////       top.close();
//    }
//}

//����Ϊ��������,��1980-5-9 
function getAge(birth)
{
    var now = new Date();
    var nowYear = now.getFullYear();
    var oneYear = birth.substring(0,4);
    var age = nowYear - oneYear;
    if (age <= 0)
    {
        age = 0
    }
    return age;
}
