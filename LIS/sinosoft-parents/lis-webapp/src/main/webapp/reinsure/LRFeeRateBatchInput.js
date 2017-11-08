var showInfo; 
var turnPage = new turnPageClass(); 
window.onfocus=myonfocus; 
var ImportPath; 

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

function queryFeeRateBatch(){
	var mySql100=new SqlClass();
		mySql100.setResourceName("reinsure.LRFeeRateBatchInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql100.setSqlId("LRFeeRateBatchInputSql100");//ָ��ʹ�õ�Sql��id
		mySql100.addSubPara(fm.FeeTableNo.value);//ָ������Ĳ���
	var strSQL=mySql100.getString();
	
	//var strSQL = "select x.X1,x.X2,x.X3,x.X4,x.X5,x.X6,decode(x.X7,'1','�ѵ���','δ����'),x.X8,decode(x.X9,'1','��Ч','δ��Ч') "
	           + " from ( select a.FeeTableNo X1,a.FeeTableName X2,a.BatchNo X3,a.InureDate X4,a.LapseDate X5,a.SaveDataName X6,(select 1 from RIFeeRateTableTrace c where exists (select * from RIFeeRateTable d where c.feetableno=d.feetableno and d.batchno=c.batchno ) and c.feetableno=a.feetableno and c.batchno=a.batchno) X7,a.State X8,(select SubStr(a.state,1,1) from RIFeeRateTableTrace c where c.feetableno = a.feetableno and c.batchno = a.batchno) X9 from RIFeeRateTableTrace a where a.FeeTableNo='"+fm.FeeTableNo.value+"') x order by x.X1,x.X3"
	;                                                                                                                                                                                                                                                                                                                                             
	turnPage.queryModal(strSQL, FeeRateBatchGrid);                                                                                                                                                                                                                                                                                                
}

//�ύ�����水ť��Ӧ����
function submitForm()
{
	fm.OperateType.value="INSERT";
	if(fm.State.value=='01')
	{
      var strSQL = "";
      var mySql101=new SqlClass();
		mySql101.setResourceName("reinsure.LRFeeRateBatchInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql101.setSqlId("LRFeeRateBatchInputSql101");//ָ��ʹ�õ�Sql��id
		mySql101.addSubPara(fm.FeeTableNo.value);//ָ������Ĳ���
	    strSQL=mySql101.getString();
     //strSQL = "select state from RIFeeRateTableDef where feetableno='"+fm.FeeTableNo.value+"'";
      turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);
      turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
      var tState=turnPage.arrDataCacheSet[0][0];
      if(tState=='02'||tState==''||tState==null)
      {
      	myAlert(""+"���ʱ�״̬Ϊ��Ч������״̬����Ϊ��Ч��"+"");
      	return false;
      }
  }    
	try 
	{
		if( verifyInput() == true) 
		{
			if (veriryInput3()==true)
			{
		  	var i = 0;
		  	var showStr=""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
		  	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
				fm.action="./LRFeeRateBatchSave.jsp";
		  	fm.submit(); //�ύ
	  	}
	  }
  }catch(ex) {
  	showInfo.close( );
  	myAlert(ex);
  }
}

function updateClick(){
	fm.OperateType.value="UPDATE";
	if(fm.State.value=='01')
	{
      var strSQL = "";
      var mySql102=new SqlClass();
		mySql102.setResourceName("reinsure.LRFeeRateBatchInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql102.setSqlId("LRFeeRateBatchInputSql102");//ָ��ʹ�õ�Sql��id
		mySql102.addSubPara(fm.FeeTableNo.value);//ָ������Ĳ���
	    strSQL=mySql102.getString();
      //strSQL = "select state from RIFeeRateTableDef where feetableno='"+fm.FeeTableNo.value+"'";
      turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);
      turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
      var tState=turnPage.arrDataCacheSet[0][0];
      if(tState=='02'||tState==''||tState==null)
      {
      	myAlert(""+"���ʱ�״̬Ϊ��Ч������״̬����Ϊ��Ч��"+"");
      	return false;
      }
  }    
	try 
	{
		if( verifyInput() == true) 
		{
			if (veriryInput3()==true)
			{
		  	var i = 0;
		  	var showStr=""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
		  	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
				fm.action="./LRFeeRateBatchSave.jsp";
		  	fm.submit(); //�ύ
	  	}
	  }
  }catch(ex) {
  	showInfo.close( );
  	myAlert(ex);
  }
}

function deleteClick(){
	fm.OperateType.value="DELETE";
	if(!confirm(""+"��ȷ��Ҫɾ���÷��ʱ�������"+"")){
		return false;
	}
	try {
		if(verifyInput() == true){
		  	var i = 0;
		  	var showStr=""+"����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
		  	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
				
				fm.action="./LRFeeRateBatchSave.jsp";
		  	fm.submit(); //�ύ
	  }
  } catch(ex) {
  	showInfo.close( );
  	myAlert(ex);
  }
}

function veriryInput3(){
	return true;
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content)
{
  showInfo.close();
  if (FlagStr == "Fail" ) {             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(content));
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
    
  } else { 
	  //content="����ɹ���";
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+encodeURI(encodeURI(content));
	  showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	  if (fm.OperateType.value=="DELETE")
	  {
	  	resetForm();
	  }else{
	  	queryFeeRateBatch();
	  }
  }
}

function ShowBatch(){
	var tSel = FeeRateBatchGrid.getSelNo();
	fm.BatchNo.value= FeeRateBatchGrid.getRowColData(tSel-1,3);
	fm.InureDate.value= FeeRateBatchGrid.getRowColData(tSel-1,4);
	fm.LapseDate.value= FeeRateBatchGrid.getRowColData(tSel-1,5);
	fm.SaveDataName.value= FeeRateBatchGrid.getRowColData(tSel-1,6);
	fm.State.value= FeeRateBatchGrid.getRowColData(tSel-1,8);
	if(fm.State.value=='01'){
		fm.stateName.value=""+"��Ч"+"";
	}else{
		fm.stateName.value=""+"δ��Ч"+"";
	}
	fm.SaveDataNameName.value=""+"ͨ�÷��ʱ�"+"";
	
}

function inputFeeRateBatch(){
	fm.all('BatchNo').value 	= '';    
	fm.all('InureDate').value  = '';
	fm.all('LapseDate').value 	= '';    
	fm.all('SaveDataName').value  = '';
	fm.all('SaveDataNameName').value  = '';
	fm.all('State').value  = '';
	fm.all('stateName').value  = '';
	fmImport.reset();
}
function FeeRateTableImp(){
	if(fmImport.FileName.value==""||fmImport.FileName.value==null)
	{
		myAlert(""+"¼�뵼���ļ�·����"+"");
		return false;
	}
	var i = 0;
  getImportPath();
  
  ImportFile = fmImport.all('FileName').value;  
  
  var showStr=""+"�����������ݡ���"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  fmImport.action = "./LRFeeRateImportSave.jsp?ImportPath="+ImportPath+"&FeeTableNo="+fm.FeeTableNo.value+"&BatchNo="+fm.BatchNo.value;
  fmImport.submit(); //�ύ
}

function getImportPath(){
  var strSQL = "";
 var mySql103=new SqlClass();
		mySql103.setResourceName("reinsure.LRFeeRateBatchInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql103.setSqlId("LRFeeRateBatchInputSql102");//ָ��ʹ�õ�Sql��id
	    mySql103.addSubPara("1");
	    strSQL=mySql103.getString();
 // strSQL = "select sysvarvalue from ldsysvar where sysvar='RIXmlPath'";
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    myAlert(""+"δ�ҵ��ϴ�·��"+"");
    return;
  }
  //�������������������ͬ��ѯ����һ��turnPage����ʱ����ʹ�ã���ü��ϣ��ݴ�
  turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);

  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

  ImportPath = turnPage.arrDataCacheSet[0][0];
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
  	myAlert(""+"��CertifySendOutInput.js"+"-->"+"resetForm�����з����쳣:��ʼ���������!"+"");
  }
} 

function ClosePage(){
	top.close();
} 

//�ύǰ��У�顢����  
function beforeSubmit()
{
  //��Ӳ���	
}           
function alink(){
	window.location.href="../temp/reinsure/feerateimp/LRFeeRateImport.xls";
}

