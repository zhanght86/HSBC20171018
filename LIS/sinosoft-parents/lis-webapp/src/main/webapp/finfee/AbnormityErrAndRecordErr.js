//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
// ��ѯ��ť
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPage1 = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var queryBug = 1;
var showInfo;
var mDebug="0";

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

//����
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    initForm();
  }
}






function initQuery() 
{
	// ��ʼ�����
	initPolGrid();
	

// ��дSQL���			 
//	var strSql = "select bussno,errorcount,errorcontent,(select bponame from BPOServerInfo where BPOServerInfo.Lisoperator = BPOMissionDetailError.operator and bussNoType='"+bussNoType+"') bponame,MakeDate,MakeTime"
//	             +" from BPOMissionDetailError "
//	             +" where bussno='"+TempFeeNo+"'"
//	             +" and bussNoType='"+bussNoType+"'"
//	             +"  order by bussno,errorcount";
	
	var sqlid1="AbnormityErrAndRecordErrSql0";
	var mySql1=new SqlClass();
	mySql1.setResourceName("finfee.AbnormityErrAndRecordErrSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(bussNoType);//ָ������Ĳ���
	mySql1.addSubPara(TempFeeNo);//ָ������Ĳ���
	mySql1.addSubPara(bussNoType);//ָ������Ĳ���
	var strSql=mySql1.getString();
  //prompt("",strSql);
  //alert(strSql);
	//turnPage.queryModal(strSql, PolGrid);
	//��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);  
 

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {  
    //���MULTILINE��ʹ�÷�����MULTILINEʹ��˵�� 
    PolGrid.clearData();  
    return false;
  }
  
  //�������������������ͬ��ѯ����һ��turnPage����ʱ����ʹ�ã���ü��ϣ��ݴ�
  turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = PolGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSql; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, turnPage.pageLineNum);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  
  //�����Ƿ���ʾ��ҳ��ť
  if (turnPage.queryAllRecordCount > turnPage.pageLineNum) {
    try { window.divPage1.style.display = ""; } catch(ex) { }
  } else {
    try { window.divPage1.style.display = "none"; } catch(ex) { }
  }
}


function initErrQuery() 
{
	// ��ʼ�����
	initErrGrid();
	

// ��дSQL���			 
//	var strSql = "select errver,errcode,errorcontent from BPOErrLog"
//	             +" where bussno='"+TempFeeNo+"'"
//	             +" and bussNoType='"+bussNoType+"'";
	var sqlid2="AbnormityErrAndRecordErrSql1";
	var mySql2=new SqlClass();
	mySql2.setResourceName("finfee.AbnormityErrAndRecordErrSql"); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
    mySql2.addSubPara(TempFeeNo);//ָ������Ĳ���
	mySql2.addSubPara(bussNoType);//ָ������Ĳ���
	var strSql=mySql2.getString();
	
  //prompt("",strSql);
  //alert(strSql);
	//turnPage.queryModal(strSql, PolGrid);
	//��ѯSQL�����ؽ���ַ���
  turnPage1.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);  
  // alert("dddd");

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage1.strQueryResult) {  
    //���MULTILINE��ʹ�÷�����MULTILINEʹ��˵�� 
    ErrGrid.clearData();  
    return false;
  }

  
  //�������������������ͬ��ѯ����һ��turnPage����ʱ����ʹ�ã���ü��ϣ��ݴ�
  turnPage1.arrDataCacheSet = clearArrayElements(turnPage1.arrDataCacheSet);
  
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage1.arrDataCacheSet = decodeEasyQueryResult(turnPage1.strQueryResult);
  
  
  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage1.pageDisplayGrid = ErrGrid;       
          
  //����SQL���
  turnPage1.strQuerySql     = strSql; 
  
  //���ò�ѯ��ʼλ��
  turnPage1.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage1.getData(turnPage1.arrDataCacheSet, turnPage1.pageIndex, turnPage1.pageLineNum);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage1.pageDisplayGrid);
  
  //�����Ƿ���ʾ��ҳ��ť
  if (turnPage1.queryAllRecordCount > turnPage1.pageLineNum) {
    try { window.divPage.style.display = ""; } catch(ex) { }
  } else {
    try { window.divPage.style.display = "none"; } catch(ex) { }
  }
}


//�ύ
function submitForm()
{
   
  var aCount = ErrGrid.mulLineCount;
  	
  //alert("ѡ�е�����"+aCount);
  if(aCount<1)
  {
	 alert("��������дһ�в���¼");
	 return false;
  }
  
  
  if(TempFeeNo==null||TempFeeNo=="")
  {
  	alert("�����շѵ�֤ӡˢ��Ϊ�գ��޷��������¼");
	  return false;
  }
  
  var i = 0;
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  //alert(prtNo);
  fm.action = "./AbnormityErrAndRecordErrSave.jsp?TempFeeNo="+TempFeeNo+ "&bussNoType="+bussNoType;
  fm.submit(); //
}







