var mDebug="1";
var mOperate="";
var showInfo;
var turnPage = new turnPageClass();  //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var mySql = new SqlClass();
window.onfocus=myonfocus;
var arrDataSet;
//ʹ�ôӸô��ڵ����Ĵ����ܹ��۽�

function myonfocus()
{
  if(showInfo!=null)
  {
    try
    {
      showInfo.focus();
    }
    catch(ex)
    {
      showInfo=null;
    }
  }
}

//�ύ�����水ť��Ӧ����
function submitForm()
{
    var i = 0;
    getImportPath();
    document.all('ImportPath').value = ImportPath;
    var tFlag = fm.Flag.value;
//    alert(tFlag);return false;
    if (document.all('FileName').value=="")
    {
       alert ("�������ļ���ַ!");	
       return false;
    }    
//    alert(tFlag);
//    return false;
    //add by wood
    var tRgtNo;
    
    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

    if(tFlag == "TOACC")
    {
        tRgtNo = fm.RgtNo.value;
        fm.action = "./GrpCustomerDiskForAccSave.jsp?Flag="+tFlag+"&RgtNo="+tRgtNo;//�ʻ����⵼��
    }
    else if(tFlag == 'TOSIMALL')
    {
        fm.action = "./GrpCustomerDiskForSimpleAllSave.jsp";//�����㵼��
    }
    else if(tFlag == 'TOCLASS')
    {
        fm.action = "./GrpCustomerForReceiptClassSave.jsp";//�籣�˵�����
    }
    else if(tFlag == 'TOSIM')
    {
        
        tRgtNo = fm.RgtNo.value;
        //alert(tRgtNo);
        fm.action = "./GrpCustomerDiskSave.jsp?Flag="+tFlag+"&RgtNo="+tRgtNo;//�������⵼��
    }
    else
    {
        fm.action = "./GrpCustomerDiskSave.jsp";//�������⵼��
    }
    fm.submit(); //�ύ
    tSaveFlag ="0";
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content ,Result )
{
    showInfo.close();
    if (FlagStr == "Fail")
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
        var iWidth=550;      //�������ڵĿ��; 
        var iHeight=350;     //�������ڵĸ߶�; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();
 

    }
//  top.opener.afterQuery(Result);
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
    alert("��ProposalCopy.js-->resetForm�����з����쳣:��ʼ���������!");
  }
}

function getImportPath ()
{
  // ��дSQL���
 /* var strSQL = "";
  strSQL = "select SysvarValue from ldsysvar where sysvar ='XmlPath'";*/
  mySql = new SqlClass();
mySql.setResourceName("claimgrp.GrpCustomerDiskInputSql");
mySql.setSqlId("GrpCustomerDiskSql1");
mySql.addSubPara("");

  turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 1, 1);
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("δ�ҵ��ϴ�·��");
    return;
  }
  //�������������������ͬ��ѯ����һ��turnPage����ʱ����ʹ�ã���ü��ϣ��ݴ�
  turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  ImportPath = turnPage.arrDataCacheSet[0][0];
}


function easyQueryClick()
{
  
  if (fmquery.tRgtNo.value==""&&fmquery.tCustomerNo.value==""&&fmquery.tCustomerName.value=="")
  {
  	alert("����������һ����ѯ������");
  	return;
  }  
  
  initDiskErrQueryGrid();
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


  
  // ��дSQL���
  //var strSql = "select rgtno,customerno,customername,ErrorInfo,BatchNo,ID,operator,makedate,maketime from lcGrpCustomerImportLog where 1=1 ";
    //+ "and ErrorState='0' "; �ݲ����������
	  mySql = new SqlClass();
	mySql.setResourceName("claimgrp.GrpCustomerDiskInputSql");
	mySql.setSqlId("GrpCustomerDiskSql2"); 
	mySql.addSubPara(document.all('Operator').value);  
  if(fmquery.all('tRgtNo').value!=null&&fmquery.all('tRgtNo').value!="")
  {
    //strSql=strSql+ "and RgtNo='"+fmquery.all('tRgtNo').value+"'";
      mySql = new SqlClass();
	mySql.setResourceName("claimgrp.GrpCustomerDiskInputSql");
	mySql.setSqlId("GrpCustomerDiskSql3");  
	mySql.addSubPara(fmquery.all('tRgtNo').value); 
	mySql.addSubPara(document.all('Operator').value); 
  }
  if(fmquery.all('tCustomerNo').value!=null&&fmquery.all('tCustomerNo').value!="")
  {
    //strSql=strSql+ "and CustomerNo='"+fmquery.all('tCustomerNo').value+"'";
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.GrpCustomerDiskInputSql");
	mySql.setSqlId("GrpCustomerDiskSql4");  
	mySql.addSubPara(fmquery.all('tRgtNo').value); 
	mySql.addSubPara(fmquery.all('tCustomerNo').value);
	mySql.addSubPara(document.all('Operator').value);  
  }
  if(fmquery.all('tCustomerName').value!=null&&fmquery.all('tCustomerName').value!="")
  {
    //strSql=strSql+ "and customername='"+fmquery.all('tCustomerName').value+"'";
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.GrpCustomerDiskInputSql");
	mySql.setSqlId("GrpCustomerDiskSql5");  
	mySql.addSubPara(fmquery.all('tRgtNo').value); 
	mySql.addSubPara(fmquery.all('tCustomerNo').value); 
	mySql.addSubPara(fmquery.all('tCustomerName').value); 
	mySql.addSubPara(document.all('Operator').value); 
  }
    /*strSql=strSql+ "and operator='"+document.all('Operator').value+"'";
  strSql=strSql+ " Order by rgtno,customerno";*/

  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    showInfo.close();
    alert("δ��ѯ���������������ݣ�");
    return false;
  }
  //���ò�ѯ��ʼλ��
  //turnPage.pageIndex = 0;
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  turnPage.pageLineNum = 20 ;
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  //���ó�ʼ������MULTILINE����
  turnPage.pageDisplayGrid = DiskErrQueryGrid;
  //����SQL���
  turnPage.strQuerySql = strSql
  arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  showInfo.close();
}


/*********************************************************************
 *  �����λ��屣����ɾ�����̵���ı�������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function deleteInsured()
{
    var i = DiskErrQueryGrid.getSelNo();
    var tRptNo = "";
    var BatchNo = "";
    var ID = "";
    if (i != '0')
    {
      i = i - 1;
      tRptNo = DiskErrQueryGrid.getRowColData(i,1);
      tBatchNo = DiskErrQueryGrid.getRowColData(i,5);
      tID = DiskErrQueryGrid.getRowColData(i,6);
      
    }else{
      alert("��ѡ��һ�����ݣ�");
      return false;
    }

/*
  var numFlag=false;
  var row = DiskErrQueryGrid.mulLineCount-1;
  for(var m=0;m<row;m++ )
  {
    if(DiskErrQueryGrid.getChkNo(m))
    {
      var i = DiskErrQueryGrid.getChkNo(m);//�õ�������
      if(i==true){
         numFlag = true;
      }
    }
  }
  if(numFlag==false)
  {
    alert("��û��ѡ��Ҫɾ�������ݣ�");
    return false;
  }
*/
  var showStr="����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
  var iWidth=550;      //�������ڵĿ��; 
  var iHeight=250;     //�������ڵĸ߶�; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();

  
  fm.action = "./DiskDeleteInsured.jsp?tRptNo="+tRptNo+"&tBatchNo="+tBatchNo+"&tID="+tID;
  fm.submit(); //�ύ
}
/*********************************************************************
 *  ����½�û�ɾ�����̵���ı�������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function deleteAll()
{
  var tOperator = fm.Operator.value ;
  var showStr="����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
  var iWidth=550;      //�������ڵĿ��; 
  var iHeight=250;     //�������ڵĸ߶�; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();

  
  fm.action = "./DiskDeleteInsured.jsp?Operator="+tOperator;
  fm.submit(); //�ύ
}