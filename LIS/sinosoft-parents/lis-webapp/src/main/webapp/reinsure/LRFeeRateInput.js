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

//�ύ�����水ť��Ӧ����
function submitForm()
{
	fm.OperateType.value="INSERT";
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
				
				fm.action="./LRFeeRateSave.jsp?OPER=";
		  	fm.submit(); //�ύ
	  	}
	  }
  } catch(ex) 
  {
  	showInfo.close( );
  	myAlert(ex);
  }
}

//insert
function veriryInput3()
{
	var rowNum=TableClumnDefGrid.mulLineCount ; //���� 
	if(rowNum==0){
		myAlert(""+"��¼����ʱ��ֶζ���"+"");
		return false;
	}
	for(var i=0;i<rowNum;i++)
	{
		num=i+1;
		if(TableClumnDefGrid.getRowColData(i,1)==null||TableClumnDefGrid.getRowColData(i,1)==""){
			myAlert(""+"��"+""+num+""+"�з��ʱ��ֶβ���Ϊ��"+"");
			return false;
		}
		if(TableClumnDefGrid.getRowColData(i,2)==null||TableClumnDefGrid.getRowColData(i,2)==""){
			myAlert(""+"��"+""+num+""+"�нӿڱ��ֶ�������Ϊ��"+"");
			return false;
		}
		if(TableClumnDefGrid.getRowColData(i,4)==null||TableClumnDefGrid.getRowColData(i,4)==""){
			myAlert(""+"��"+""+num+""+"�з��ʱ��ֶ�����������Ϊ��"+"");
			return false;
		}
		if(TableClumnDefGrid.getRowColData(i,4).substring(1,3)=="01"){
			if(TableClumnDefGrid.getRowColData(i,5)==null||TableClumnDefGrid.getRowColData(i,5)==""){
				myAlert(""+"��"+""+num+""+"�й̶�ֵӳ���ֶβ���Ϊ��"+"");
				return false;
			}
			if(TableClumnDefGrid.getRowColData(i,7)!=null&&TableClumnDefGrid.getRowColData(i,7)!=""){
				myAlert(""+"��"+""+num+""+"�����������ֶ�ӳ�䲻����ֵ"+"");
				return false;
			}
			if(TableClumnDefGrid.getRowColData(i,9)!=null&&TableClumnDefGrid.getRowColData(i,9)!=""){
				myAlert(""+"��"+""+num+""+"�����������ֶ�ӳ�䲻����ֵ"+"");
				return false;
			}
		}
		
		if(TableClumnDefGrid.getRowColData(i,4).substring(1,3)=="02"){
			if(TableClumnDefGrid.getRowColData(i,7)==null||TableClumnDefGrid.getRowColData(i,7)==""){
				myAlert(""+"��"+""+num+""+"�����������ֶ�ӳ�䲻��Ϊ��"+"");
				return false;
			}
			if(TableClumnDefGrid.getRowColData(i,9)==null||TableClumnDefGrid.getRowColData(i,9)==""){
				myAlert(""+"��"+""+num+""+"�����������ֶ�ӳ�䲻��Ϊ��"+"");
				return false;
			}
			if(TableClumnDefGrid.getRowColData(i,5)!=null&&TableClumnDefGrid.getRowColData(i,5)!=""){
				myAlert(""+"��"+""+num+""+"�й̶�ֵӳ���ֶβ�����ֵ"+"");
				return false;
			}
			if(TableClumnDefGrid.getRowColData(i,7)==TableClumnDefGrid.getRowColData(i,9)) 
			{
			    myAlert(""+"��"+""+num+""+"���������޺���������ӳ���ֶ���ͬ"+"");	
			    return false; 
			}
			if(TableClumnDefGrid.getRowColData(i,4).substring(0,1)=="A"){
			//�ַ����������������޶��岻��Ϊ��ֵ��
				if(TableClumnDefGrid.getRowColData(i,7).substring(0,1)=="N"||TableClumnDefGrid.getRowColData(i,9).substring(0,1)=="N"){
					myAlert(""+"�ַ������������޶��岻��Ϊ��ֵ���ֶ�"+"");
					return false;
				}
			}
			if(TableClumnDefGrid.getRowColData(i,4).substring(0,1)=="N"){
			//��ֵ���������������޶��岻��Ϊ�ַ���
				if(TableClumnDefGrid.getRowColData(i,7).substring(0,1)=="S"||TableClumnDefGrid.getRowColData(i,9).substring(0,1)=="S"){
					myAlert(""+"��ֵ�����������޶��岻��Ϊ�ַ����ֶ�"+"");
					return false;
				}
			}
		}
		
			//У�鲻��¼���ظ���ͬҪ��
		for(var n=0;n<TableClumnDefGrid.mulLineCount;n++) 
		{ 
		   for(var m=n+1;m<TableClumnDefGrid.mulLineCount;m++) 
		   { 
		   		var n1=n+1;
		   		var m1=m+1;
		     	if(TableClumnDefGrid.getRowColData(n,1)==TableClumnDefGrid.getRowColData(m,1)) 
		     	{
		     	    myAlert(""+"����¼���ظ����ʱ��ֶ�!"+"");	
		     	    return false; 
		     	}
		     	if(TableClumnDefGrid.getRowColData(n,2)==TableClumnDefGrid.getRowColData(m,2)) 
		     	{
		     	    myAlert(""+"����¼���ظ��ӿڱ��ֶ���!"+"");	
		     	    return false; 
		     	}
		     	if(TableClumnDefGrid.getRowColData(n,4).substring(1,3)=="01"&&TableClumnDefGrid.getRowColData(m,4).substring(1,3)=="01"){
		     		if(TableClumnDefGrid.getRowColData(n,5)==TableClumnDefGrid.getRowColData(m,5)) 
		     		{
		     		    myAlert(""+"��"+""+n1+""+"�к͵�"+""+m1+""+"�����ظ�ӳ���ֶ�!"+"");	
		     		    return false; 
		     		}
		    	}
		    	if(TableClumnDefGrid.getRowColData(n,4).substring(1,3)=="01"&&TableClumnDefGrid.getRowColData(m,4).substring(1,3)=="02"){
		     		if(TableClumnDefGrid.getRowColData(n,5)==TableClumnDefGrid.getRowColData(m,7)||TableClumnDefGrid.getRowColData(n,5)==TableClumnDefGrid.getRowColData(m,7)||TableClumnDefGrid.getRowColData(n,5)==TableClumnDefGrid.getRowColData(m,9)) 
		     		{
		     		    myAlert(""+"��"+""+n1+""+"�к͵�"+""+m1+""+"�����ظ�ӳ���ֶ�!"+"");	
		     		    return false; 
		     		}
		    	}
		    	if(TableClumnDefGrid.getRowColData(n,4).substring(1,3)=="02"&&TableClumnDefGrid.getRowColData(m,4).substring(1,3)=="01"){
		     		if(TableClumnDefGrid.getRowColData(n,7)==TableClumnDefGrid.getRowColData(m,5)||TableClumnDefGrid.getRowColData(n,9)==TableClumnDefGrid.getRowColData(m,5)) 
		     		{
		     		    myAlert(""+"��"+""+n1+""+"�к͵�"+""+m1+""+"�����ظ�ӳ���ֶ�!"+"");	
		     		    return false; 
		     		}
		    	}
		    	if(TableClumnDefGrid.getRowColData(n,4).substring(1,3)=="02"&&TableClumnDefGrid.getRowColData(m,4).substring(1,3)=="02"){
		     		if(TableClumnDefGrid.getRowColData(n,7)==TableClumnDefGrid.getRowColData(m,5)||TableClumnDefGrid.getRowColData(n,5)==TableClumnDefGrid.getRowColData(m,7)||TableClumnDefGrid.getRowColData(n,9)==TableClumnDefGrid.getRowColData(m,5)) 
		     		{
		     		    myAlert(""+"��"+""+n1+""+"�к͵�"+""+m1+""+"�����ظ�ӳ���ֶ�!"+"");	
		     		    return false; 
		     		}
		    	}
		   }
		}   
	}
	return true;
}

function queryClick()
{
  fm.OperateType.value="QUERY";
  window.open("./FrameLRFeeRateQuery.jsp?FeeTableNo="+fm.FeeTableNo.value,"true1");
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content, ReComCode, CertifyCode)
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
	  }
  }
}

function updateClick()
{
	fm.OperateType.value="UPDATE";
	if(!confirm(""+"��ȷ��Ҫ�޸ĸú�ͬ��"+""))
	{
		return false;
	}
	
	if(fm.State.value=='02'||fm.State.value==''||fm.State.value==null)
	{
	
     var mySql100=new SqlClass();
		mySql100.setResourceName("reinsure.LRFeeRateInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql100.setSqlId("LRFeeRateInputSql100");//ָ��ʹ�õ�Sql��id
		mySql100.addSubPara(fm.FeeTableNo.value);//ָ������Ĳ���
	var strSQL12=mySql100.getString();
    
     // var strSQL12="select count(*) from RIFeeRateTableTrace where FeeTableNo='"+fm.FeeTableNo.value+"'";
      var arrResult=easyExecSql(strSQL12);
      
      for(i=0;i<arrResult;i++)
      {
      	var strSQL11 = "";
        var mySql101=new SqlClass();
		mySql101.setResourceName("reinsure.LRFeeRateInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql101.setSqlId("LRFeeRateInputSql101");//ָ��ʹ�õ�Sql��id
		mySql101.addSubPara(fm.FeeTableNo.value);//ָ������Ĳ���
	 	strSQL11=mySql101.getString();
        //strSQL11 = "select state from RIFeeRateTableTrace where feetableno='"+fm.FeeTableNo.value+"'";
        turnPage.strQueryResult  = easyQueryVer3(strSQL11, 1, 1, 1);
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        
        if(turnPage.arrDataCacheSet[i][0]=='01')
        {
      	   myAlert(""+"���ʱ�����״̬Ϊ��Ч�������޸ģ�"+"");
      	   return false;
        }
      }     
   }
	
	try {
		if( verifyInput() == true ) 
		{
			if (veriryInput3()==true)
			{
		  	var i = 0;
		  	var showStr=""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
		  	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");  
				fm.action="./LRFeeRateSave.jsp";
		  	fm.submit(); //�ύ
	  	}
	  	else{
	  	}
	  }
  } catch(ex) 
  {
  	showInfo.close( );
  	myAlert(ex);
  }
}

function veriryInput4() //UPDATE У��
{
	return true;
}

function deleteClick()
{
	var typeRadio="";
	var deleteType = "";
	fm.OperateType.value="DELETE";
	try 
	{
		if( verifyInput() == true ) 
		{
			if (veriryInput5()==true)
			{
		  	var i = 0;
		  	var showStr=""+"����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
		  	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
				fm.action="./LRFeeRateSave.jsp?DeleteType="+deleteType; 
		  	fm.submit(); 
	  	}
	  	else
	  	{
	  	}
	  }
  } catch(ex) 
  {
  	showInfo.close( );
  	myAlert(ex);
  }
}

function veriryInput5(){
	
	return true;
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
  fmImport.action = "./LRFeeRateImportSave.jsp?ImportPath="+ImportPath+"&FeeTableNo="+fm.FeeTableNo.value;
  fmImport.submit(); //�ύ
}

function nextStep()
{
	if (fm.FeeTableNo.value==''||fm.FeeTableNo.value==null)
	{
		myAlert(""+"���Ȳ�ѯ���ʱ�����Ϣ"+"");
		return false;
	}
	
	var mySql102=new SqlClass();
		mySql102.setResourceName("reinsure.LRFeeRateInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql102.setSqlId("LRFeeRateInputSql102");//ָ��ʹ�õ�Sql��id
		mySql102.addSubPara(fm.FeeTableNo.value);//ָ������Ĳ���
	var	strSQL=mySql102.getString();
	//var strSQL="select count(*) from RIFeeRateTableDef where FeeTableNo='"+fm.FeeTableNo.value+"'";
	var arrResult=easyExecSql(strSQL);
	if (arrResult==null||arrResult=="")
	{
		myAlert(""+"���ʱ��ѯʧ��"+"");
		return false;
	}
	if(arrResult[0][0]=='0'){
		myAlert(""+"���ʱ��ѯʧ��"+"");
		return false;
	}
  var varSrc="&FeeTableNo='"+ fm.FeeTableNo.value+"' " ;
  window.open("./FrameMainFeeRateBatch.jsp?Interface=LRFeeRateBatchInput.jsp"+varSrc,"true1"); //+varSrc,
}

function getImportPath(){
  var strSQL = "";
  var mySql103=new SqlClass();
		mySql103.setResourceName("reinsure.LRFeeRateInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql103.setSqlId("LRFeeRateInputSql103");//ָ��ʹ�õ�Sql��id
		mySql103.addSubPara("1");
		strSQL=mySql103.getString();
  
  //strSQL = "select sysvarvalue from ldsysvar where sysvar='RIXmlPath'";
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

function verifyFeeRateTableImp(){
	
	var mySql104=new SqlClass();
		mySql104.setResourceName("reinsure.LRFeeRateInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql104.setSqlId("LRFeeRateInputSql104");//ָ��ʹ�õ�Sql��id
		mySql104.addSubPara("1");
	var strSQL=mySql104.getString();
	//var strSQL = " select count(*) from RIFeeRateTableTrace a where not exists (select * from RIFeeRateTable b where a.feetableno=b.feetableno and a.batchno=b.batchno)";
	var arrResult=easyExecSql(strSQL);
	if(arrResult[0][0]!='0'){
		myAlert(""+"����δ������ʱ�ķ��ʱ����Σ��뼰ʱ������ʱ�"+"");
	}
	return true;
}

function afterQuery()
{
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

//�ύǰ��У�顢����  
function beforeSubmit()
{
  //��Ӳ���	
}           


