//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var turnPage = new turnPageClass(); 
function QueryAccmulate(){

	//var strSQL = " select a.accumulatedefno,a.accumulatedefname,a.arithmeticid,a.state from riaccumulatedef a where a.state='01' order by a.accumulatedefno";
	var mySql100=new SqlClass();
	 	mySql100.setResourceName("reinsure.RIDataImportInputSql"); //ָ��ʹ�õ�properties�ļ���
	  	mySql100.setSqlId("RIDataImportInputSql100");//ָ��ʹ�õ�Sql��id
		mySql100.addSubPara("1");
	var strSQL=mySql100.getString();
	
	var arrResult = easyExecSql(strSQL);
	displayMultiline(arrResult,AccmulateGrid);
	return true;
}

function QueryTaskList(){

	//var strSQL = " select a.arithmeticid,a.arithmeticname,a.arithmetictype,a.calitemorder,a.itemcaltype,a.calclass from riitemcal a where a.arithmeticdefid='impt000001' and a.arithmetictype='22'";
	var mySql101=new SqlClass();
	 	mySql101.setResourceName("reinsure.RIDataImportInputSql"); //ָ��ʹ�õ�properties�ļ���
	  	mySql101.setSqlId("RIDataImportInputSql101");//ָ��ʹ�õ�Sql��id
		mySql101.addSubPara("1");
	var strSQL=mySql101.getString();
	var arrResult = easyExecSql(strSQL);
	displayMultiline(arrResult,TaskListGrid);
	return true;
}

function afterCodeSelect( codeName, Field )
{   //ҵ�����ݵ���
	if(Field.value=='21'){
		divTitle.style.display='';
		divEndDate.style.display='';
		divList.style.display='';
		divAccmulate.style.display='';
		divTaskList.style.display='none';
		QueryAccmulate();
	}
	//�ֱ����ݵ���
	if(Field.value=='22'){
		divTitle.style.display='none';
		divEndDate.style.display='none';
		divList.style.display='';
		divAccmulate.style.display='none';
		divTaskList.style.display='';
		QueryTaskList();
	}
	//ҵ������ɾ��
	if(Field.value=='31'){
		divTitle.style.display='none';
		divEndDate.style.display='none';
		divList.style.display='none';
	}
	//�ֱ�����ɾ��
	if(Field.value=='32'){
		divTitle.style.display='none';
		divEndDate.style.display='none';		
		divList.style.display='none';
	}

}

function SubmitForm(){
	var endDate = fm.EndDate.value;
	var processType = fm.ProcessType.value;
	
	if(processType=='21'){
		var chkFlag=false; 
		if(endDate==''){
			myAlert(""+"��ѡ���ֹ���ڣ�"+"");
			return false;
		}
		if(processType==''){
			myAlert(""+"��ѡ�������ͣ�"+"");
			return false;
		}
	}
	if(processType=='21'){
		var selCount=AccmulateGrid.mulLineCount;
		for (i=0;i<selCount;i++){
			if(AccmulateGrid.getChkNo(i)==true){
				chkFlag=true;
			}
		}
		if(!chkFlag){
			myAlert(""+"��ѡ��Ҫ����ҵ��������ȡ���ۼƷ���!"+"");
			return false;
		}
	}
	if(processType=='22'){
		var selCount=TaskListGrid.mulLineCount;
		for (i=0;i<selCount;i++){
			if(TaskListGrid.getChkNo(i)==true){
				chkFlag=true;
			}
		}
		if(!chkFlag){
			myAlert(""+"��ѡ��Ҫ�����ٱ�������ȡ������!"+"");
			return false;
		}
	}
	try {
		var i = 0;
		var showStr=""+"���ڽ���ҵ�����ݵ������"+" "+"�������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
		showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");  
		fm.submit();
    
	}catch(ex){
		showInfo.close();
		myAlert(ex);
	}
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content ){
	showInfo.close();
	if (FlagStr == "Fail" ){
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(content));
		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	}else{
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+encodeURI(encodeURI(content));
		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	}
	initForm();
}
