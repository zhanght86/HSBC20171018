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

function queryFeeRateBatch()
{
	var tSQL = " select count(*) from RIAssociateFeeTable a where a.ripreceptno='"+fm.RIPreceptNo.value+"' and a.areaid='"+fm.AreaId.value+"' ";
	var result = easyExecSql(tSQL); 
	//alert(result);
	if(result=='0'){
		tSQL = " select count(*) from (select a.AccumulateDefNO, b.DeTailFlag,decode(b.DeTailFlag,'01','"+"���ּ���"+"','02','"+"���ּ���"+"'), '"+fm.RIPreceptNo.value+"', a.associatedcode, '"+fm.AreaId.value+"', '"+fm.IncomeCompanyNo.value+"',"
		+ " (select ComPanyName from RIComInfo r where r.ComPanyNo='"+fm.IncomeCompanyNo.value+"'), '"+fm.UpperLimit.value+"', '"+fm.LowerLimit.value+"','','','','' "
		+ " from RIAccumulateRDCode a,RIAccumulateDef b where a.accumulatedefno = b.accumulatedefno and a.accumulatedefno='" + fm.AccumulateDefNO.value + "' "
		+ " and a.AssociatedCode not in (select AssociateCode from RIAssociateFeeTable ass where ass.RIPreceptNo='"+fm.RIPreceptNo.value+"' and ass.AreaID='"+fm.AreaId.value+"') ) x "
		;
		var result = easyExecSql(tSQL); 
		
		tSQL = " select a.AccumulateDefNO, b.DeTailFlag,decode(b.DeTailFlag,'01','"+"���ּ���"+"','02','"+"���ּ���"+"'), '"+fm.RIPreceptNo.value+"', a.associatedcode, '"+fm.AreaId.value+"', '"+fm.IncomeCompanyNo.value+"',"
		+ " (select ComPanyName from RIComInfo r where r.ComPanyNo='"+fm.IncomeCompanyNo.value+"'), '"+fm.UpperLimit.value+"', '"+fm.LowerLimit.value+"','','','','' "
		+ " from RIAccumulateRDCode a,RIAccumulateDef b where a.accumulatedefno = b.accumulatedefno and a.accumulatedefno='" + fm.AccumulateDefNO.value + "' "
		+ " and a.AssociatedCode not in (select AssociateCode from RIAssociateFeeTable ass where ass.RIPreceptNo='"+fm.RIPreceptNo.value+"' and ass.AreaID='"+fm.AreaId.value+"') "
		;
		turnPage.queryModal(tSQL, FeeRateBatchGrid,"","",result); 
	}else{
		tSQL = " select count(*) from (select a.AccumulateDefNO,a.DeTailFlag,decode(a.DeTailFlag,'01','"+"���ּ���"+"','02','"+"���ּ���"+"'),a.RIPreceptNo,a.AssociateCode,'"+fm.AreaId.value+"',a.IncomeCompanyNo, "
		+ " (select ComPanyName from RIComInfo r where r.ComPanyNo=a.IncomeCompanyNo), a.UpperLimit||'', a.LowerLimit||'', " 
		+ " a.PremFeeTableNo, (select FeeTableName from RIFeeRateTableDef f where f.feetableno=a.PremFeeTableNo) ,a.ComFeeTableNo ,(select FeeTableName from RIFeeRateTableDef f where f.feetableno=a.ComFeeTableNo) "
		+ " from RIAssociateFeeTable a where a.ripreceptno='"+fm.RIPreceptNo.value+"' and a.areaid='"+fm.AreaId.value+"' " 
		+ " union all " 
		+ " select a.AccumulateDefNO,b.DeTailFlag,decode(b.DeTailFlag,'01','"+"���ּ���"+"','02','"+"���ּ���"+"'), '"+fm.RIPreceptNo.value+"', a.associatedcode, '"+fm.AreaId.value+"', '"+fm.IncomeCompanyNo.value+ "'," 
		+ " (select ComPanyName from RIComInfo r where r.ComPanyNo='"+fm.IncomeCompanyNo.value+"'), '"+fm.UpperLimit.value+"', '"+fm.LowerLimit.value+"','','','','' "
		+ " from RIAccumulateRDCode a,RIAccumulateDef b where a.accumulatedefno = b.accumulatedefno and a.accumulatedefno='" + fm.AccumulateDefNO.value + "' "
		+ " and a.AssociatedCode not in (select AssociateCode from RIAssociateFeeTable ass where ass.RIPreceptNo='"+fm.RIPreceptNo.value+"' and ass.AreaID='"+fm.AreaId.value+"') ) x "
		;
		var result = easyExecSql(tSQL); 
		
		tSQL = "select a.AccumulateDefNO,a.DeTailFlag,decode(a.DeTailFlag,'01','"+"���ּ���"+"','02','"+"���ּ���"+"'),a.RIPreceptNo,a.AssociateCode,'"+fm.AreaId.value+"',a.IncomeCompanyNo, "
		+ " (select ComPanyName from RIComInfo r where r.ComPanyNo=a.IncomeCompanyNo), a.UpperLimit||'', a.LowerLimit||'', "
		+ " a.PremFeeTableNo, (select FeeTableName from RIFeeRateTableDef f where f.feetableno=a.PremFeeTableNo) ,a.ComFeeTableNo ,(select FeeTableName from RIFeeRateTableDef f where f.feetableno=a.ComFeeTableNo) "
		+ " from RIAssociateFeeTable a where a.ripreceptno='"+fm.RIPreceptNo.value+"' and a.areaid='"+fm.AreaId.value+"' "
		+ " union all "
		+ " select a.AccumulateDefNO,b.DeTailFlag,decode(b.DeTailFlag,'01','"+"���ּ���"+"','02','"+"���ּ���"+"'), '"+fm.RIPreceptNo.value+"', a.associatedcode, '"+fm.AreaId.value+"', '"+fm.IncomeCompanyNo.value+"',"
		+ " (select ComPanyName from RIComInfo r where r.ComPanyNo='"+fm.IncomeCompanyNo.value+"'), '"+fm.UpperLimit.value+"', '"+fm.LowerLimit.value+"','','','','' "
		+ " from RIAccumulateRDCode a,RIAccumulateDef b where a.accumulatedefno = b.accumulatedefno and a.accumulatedefno='" + fm.AccumulateDefNO.value + "' "
		+ " and a.AssociatedCode not in (select AssociateCode from RIAssociateFeeTable ass where ass.RIPreceptNo='"+fm.RIPreceptNo.value+"' and ass.AreaID='"+fm.AreaId.value+"') "
		;
		turnPage.queryModal(tSQL, FeeRateBatchGrid,"","",result); 
	}
}


//�ύ�����水ť��Ӧ����
function submitForm()
{
	var preceptno=FeeRateBatchGrid.getRowColData(1,4);
	var areaid=FeeRateBatchGrid.getRowColData(1,6);
	var tSql="select 'X' from RIRiskDivide where ripreceptno='"+preceptno+"' and areaid='"+areaid+"' and (premfeetableno is not null or comfeetableno is not null)";
	var result=easyExecSql(tSql);
	//alert(result);
	if(result!=null){
		myAlert(""+"�Ѿ������˷������ķ���Ӷ���ʣ����ܶ������ּ��ķ���Ӷ����"+"");
		return false;
	}
	fm.OperateType.value="INSERT";
	
	var rowNum=FeeRateBatchGrid.mulLineCount ;
	                                            
  for(var i=0;i<rowNum;i++)
  {                                                        
		num=i+1;	
		//alert(FeeRateBatchGrid.getRowColData(i,11)+"||"+FeeRateBatchGrid.getRowColData(i,13));	                                                                       
    if(FeeRateBatchGrid.getRowColData(i,11)=="")           
		{                                                                                  
			myAlert(""+"��"+""+num+""+"�зֱ����ʱ���Ϊ�գ�"+"");                         
			return false;                                                                    
		}		                                                                               
   }

	try 
	{
		var i = 0;
		var showStr=""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
		showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
				
	  fm.action="./RIFeeRateBatchRiskSave.jsp?";
		fm.submit(); //�ύ
	  	
	  
  } catch(ex) 
  {
  	showInfo.close( );
  	myAlert(ex);
  }
}

function updateClick(){
	fm.OperateType.value="UPDATE";
	if(fm.State.value=='01')
	{
      var strSQL = "";
      strSQL = "select state from RIFeeRateTableDef where feetableno='"+fm.FeeTableNo.value+"'";
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
				fm.action="./RIFeeRateBatchSave.jsp";
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
				
				fm.action="./RIFeeRateBatchRiskSave.jsp";
		  	fm.submit(); //�ύ
	  }
  } catch(ex) {
  	showInfo.close( );
  	myAlert(ex);
  }
}

function veriryInput3()
{
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

function ShowBatch()
{
	var tSel = FeeRateBatchGrid.getSelNo();
	fm.BatchNo.value= FeeRateBatchGrid.getRowColData(tSel-1,3);
	fm.InureDate.value= FeeRateBatchGrid.getRowColData(tSel-1,4);
	fm.LapseDate.value= FeeRateBatchGrid.getRowColData(tSel-1,5);
	fm.SaveDataName.value= FeeRateBatchGrid.getRowColData(tSel-1,6);
	fm.State.value= FeeRateBatchGrid.getRowColData(tSel-1,8);
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
  fmImport.action = "./RIFeeRateImportSave.jsp?ImportPath="+ImportPath+"&FeeTableNo="+fm.FeeTableNo.value+"&BatchNo="+fm.BatchNo.value;
  fmImport.submit(); //�ύ
}

function getImportPath(){
  var strSQL = "";
  strSQL = "select sysvarvalue from ldsysvar where sysvar='RIXmlPath'";
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

function ClosePage()
{
	top.close();
} 
       
function alink(){
	window.location.href="./RIFeeRateImport.xls";
}

