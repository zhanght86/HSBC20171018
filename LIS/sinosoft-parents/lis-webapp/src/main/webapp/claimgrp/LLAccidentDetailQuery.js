//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var turnPage=new turnPageClass();
var mySql = new SqlClass();

//�ύ�����水ť��Ӧ����
function submitForm()
{
  var i = 0;
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
  var iWidth=550;      //�������ڵĿ��; 
  var iHeight=250;     //�������ڵĸ߶�; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();

  initAccidentGrid();
  fm.submit(); //�ύ
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
//    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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


// ��ѯ��ť
function easyQueryClick()
{
   // ��ʼ�����
   initAccidentGrid();
   
   // ��дSQL���
   /*var strSQL = "";
   strSQL = "select * from LDPerson where 1=1 "
             + getWherePart( 'CustomerNo' )
             + getWherePart( 'Name' )
             + getWherePart( 'Birthday' )
             + getWherePart( 'IDType' )
             + getWherePart( 'IDNo' );*/
   mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLAccidentDetailQuerySql");
	mySql.setSqlId("LLAccidentDetailQuerySql1");
	mySql.addSubPara(fm.CustomerNo.value ); 
	mySql.addSubPara(fm.Name.value )
	mySql.addSubPara(fm.Birthday.value )
	mySql.addSubPara(fm.IDType.value )
	mySql.addSubPara(fm.IDNo.value )          
//alert(strSQL);
   execEasyQuery( mySql.getString() );
}

//ѡ��ҳ���ϲ�ѯ���ֶζ�Ӧ��"select *"�е�λ��
function getSelArray()
{
   var arrSel = new Array();
   
   arrSel[0] = 0;
   arrSel[1] = 2;
   arrSel[2] = 3;
   arrSel[3] = 4;
   arrSel[4] = 16;
   arrSel[5] = 18;

   return arrSel;
}

function displayEasyResult( arrQueryResult )
{
   var i, j, m, n;
   var arrSelected = new Array();
   var arrResult = new Array();

   if( arrQueryResult == null )
      alert( "û���ҵ���ص�����!" );
   else
   {
      // ��ʼ�����
      initAccidentGrid();
      AccidentGrid.recordNo = (currPageIndex - 1) * MAXSCREENLINES;
      AccidentGrid.loadMulLine(AccidentGrid.arraySave);

      arrGrid = arrQueryResult;
      // ת��ѡ��������
      arrSelected = getSelArray();
      arrResult = chooseArray( arrQueryResult, arrSelected );
      // ��ʾ��ѯ���
      n = arrResult.length;
      for( i = 0; i < n; i++ )
      {
         m = arrResult[i].length;
         for( j = 0; j < m; j++ )
         {
            HospitalGrid.setRowColData( i, j+1, arrResult[i][j] );
         } // end of for
      } // end of for
      //alert("result:"+arrResult);
      
      //HospitalGrid.delBlankLine();
   } // end of if
}

// ���ݷ��ظ�����
function returnParent()
{
   var arrReturn = new Array();
   var tSel = AccidentGrid.getSelNo();
   
   if( tSel == 0 || tSel == null )
      alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
   else
   {
      try
      {
         arrReturn = getQueryResult();
         if (fm.accidentDetailName.value!="")
         {
           top.opener.afterLLRegister( arrReturn );
         }else if (fm.AccResultName.value!="")
          {         
            top.opener.afterLLRegister4( arrReturn );
          }
      }
      catch(ex)
      {
         alert( "û�з��ָ����ڵ�afterQuery�ӿڡ�" + ex );
      }
      top.close();
   }
}

function getQueryResult()
{
   var arrSelected = null;
   tRow = AccidentGrid.getSelNo();
   //alert("111" + tRow);
   //edit by guo xiang at 2004-9-13 17:54
   //if( tRow == 0 || tRow == null || arrDataSet == null )
   if( tRow == 0 || tRow == null )
       return arrSelected;
   
   arrSelected = new Array();
   
   //������Ҫ���ص�����
   //edit by guo xiang at 2004-9-13 17:54
   arrSelected[0] = new Array();
   arrSelected[0] = AccidentGrid.getRowData(tRow-1);
   //arrSelected[0] = arrDataSet[tRow-1];
   
   return arrSelected;
}
function afterQuery()
{ 

	var strSQL;
 if (fm.accidentDetailName.value!="")
  {     
    // strSQL = " select ICDCode, ICDName from LDDisease where icdlevel <= 1 and ASCII(icdcode) >= 86 and ICDName like '%%"+fm.accidentDetailName.value+"%%' order by ICDCode";     
  	 mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLAccidentDetailQuerySql");
	mySql.setSqlId("LLAccidentDetailQuerySql2");
	mySql.addSubPara(fm.accidentDetailName.value ); 

  }else if (fm.AccResultName.value!="")
  {  
    if (fm.occurReason.value==1)
   {
     	//strSQL = "select ICDCode,ICDName from LDDisease where icdlevel = 1 and ASCII(icdcode) < 86 and ASCII(icdcode) > 82 and ICDName like '%%"+fm.AccResultName.value+"%%' order by ICDCode";
     	//strSQL = "select ICDCode,ICDName from LDDisease where icdlevel = 8 and ICDName like '%%"+fm.AccResultName.value+"%%' order by ICDCode";
   		 mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLAccidentDetailQuerySql");
		mySql.setSqlId("LLAccidentDetailQuerySql3");
		mySql.addSubPara(fm.AccResultName.value ); 
   }else 
   {
     	//strSQL = "select ICDCode,ICDName from LDDisease where icdlevel = 1 and ASCII(icdcode) < 83 and ICDName like '%%"+fm.AccResultName.value+"%%' order by ICDCode";
     	//strSQL = "select ICDCode,ICDName from LDDisease where icdlevel = 9 and ICDName like '%%"+fm.AccResultName.value+"%%' order by ICDCode";
   		 mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLAccidentDetailQuerySql");
		mySql.setSqlId("LLAccidentDetailQuerySql4");
		mySql.addSubPara(fm.AccResultName.value ); 
   }
  } 
   
  turnPage.queryModal(mySql.getString(), AccidentGrid,1,1);
   
}
