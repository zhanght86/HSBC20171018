var showInfo;

var turnPage = new turnPageClass(); 
window.onfocus=myonfocus;

function myonfocus(){
	if(showInfo!=null){
	  try{
	    showInfo.focus();  
	  }
	  catch(ex){
	    showInfo=null;
	  }
	}
}

//�ύ�����水ť��Ӧ����
function submitForm(){
	fm.OperateType.value="INSERT";
	try {
		if( verifyInput2() == true && RelateGrid.checkValue("RelateGrid")) 
		{
			if (veriryInput3()==true){
		  	var i = 0;
		  	var showStr=""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
		  	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
				fm.action="./LRAccRDSave.jsp";
		  	fm.submit(); //�ύ
	  	}
	  	else{
	  	}
	  }
  } catch(ex){
  	showInfo.close( );
  	myAlert(ex);
  }
}

function queryClick(){
  fm.OperateType.value="QUERY";
  
  window.open("./FrameAccRDQuery.jsp?Serial=");
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content, ReComCode, CertifyCode ){
  showInfo.close();
  if (FlagStr == "Fail" ) {             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(content));
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
  } else { 
	  //content="����ɹ���";
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+encodeURI(encodeURI(content));
	  showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	  if(fm.OperateType.value=="DELETE"){
	  	resetForm();
	  }
  }
}

function updateClick(){
	fm.OperateType.value="UPDATE";
	if(!confirm(""+"��ȷ��Ҫ�޸��ۼƷ�����"+"")){
		return false;
	}
	try {
		if( verifyInput() == true && RelateGrid.checkValue("RelateGrid")) {	
			if (veriryInput3()==true){
		  	var i = 0;
		  	var showStr=""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
		  	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
				
				fm.action="./LRAccRDSave.jsp";
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

function deleteClick(){
	fm.OperateType.value="DELETE";
	if(!confirm(""+"��ȷ��Ҫɾ�����ۼƷ�����"+"")){
		return false;
	}
	try {
		if( verifyInput() == true && RelateGrid.checkValue("RelateGrid")) {
			var i = 0;
		  var showStr=""+"����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
		  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
			fm.action="./LRAccRDSave.jsp";
		  fm.submit(); //�ύ
	  }else{
	  	
	  }
  } catch(ex){
  	showInfo.close( );
  	myAlert(ex);
  }
}

function configRelation(){
	var accumulateno=fm.AccumulateDefNO.value;
	SQL="select * from RIAccumulateDef where accumulatedefno='"+accumulateno+"'";
	var strArray = easyExecSql(SQL);
  	if (strArray==null){
  		myAlert(""+"���ȱ���ָ�����ۼƷ��գ���"+"")
  		return;
  	}
	window.open("./FrameConfigRelation.jsp?accumulatedefno="+accumulateno,true);
}

function veriryInput3(){
	var tGridLen ;
	if(fm.DeTailFlag.value=="01")
	{
		for(var i=0;i<RelateGrid.mulLineCount;i++) 
		{ 
			//var tSQL = "select AccumulateDefNO,AssociatedName from RIAccumulateRDCode where 1=1 and AssociatedCode='"+RelateGrid.getRowColData(i,1)+"' and AccumulateDefNO<>'"+fm.AccumulateDefNO.value+"'";
			
			var mySql1=new SqlClass();
				mySql1.setResourceName("reinsure.LRAccRDInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql1.setSqlId("LRAccRDInputSql001");//ָ��ʹ�õ�Sql��id
			    mySql1.addSubPara(RelateGrid.getRowColData(i,1));//ָ������Ĳ���
			    mySql1.addSubPara(fm.AccumulateDefNO.value);//ָ������Ĳ���
			tSQL=mySql1.getString();			
			
			
			var result = easyExecSql(tSQL);	
			if(result!=null&&result!="")
			{		
				if (!confirm(""+"���֣�"+""+RelateGrid.getRowColData(i,1)+""+"�Ѿ����ۼƷ���"+""+result[0][1]+""+"�ж��壬�Ƿ������"+"")) 
				{
					return false;
				}
			}

		}
	}else{
		for(var i=0;i<DutyGrid.mulLineCount;i++) 
		{ 
			//var tSQL = "select AccumulateDefNO,AssociatedName from RIAccumulateRDCode where 1=1 and AssociatedCode='"+DutyGrid.getRowColData(i,1)+"' and AccumulateDefNO<>'"+fm.AccumulateDefNO.value+"'";
			
			var mySql1=new SqlClass();
				mySql1.setResourceName("reinsure.LRAccRDInputSql"); //ָ��ʹ�õ�properties�ļ���
				mySql1.setSqlId("LRAccRDInputSql002");//ָ��ʹ�õ�Sql��id
			    mySql1.addSubPara(DutyGrid.getRowColData(i,1));//ָ������Ĳ���
			    mySql1.addSubPara(fm.AccumulateDefNO.value);//ָ������Ĳ���
			tSQL=mySql1.getString();	
						
			var result = easyExecSql(tSQL);
			if(result!=null&&result!="")
			{
				if (!confirm(""+"���Σ�"+""+RelateGrid.getRowColData(i,1)+""+"�Ѿ����ۼƷ���"+""+result[0][1]+""+"�ж��壬�Ƿ������"+"")) 
				{
					return false;
				}
			}
		}
	}
	return true;
}
function veriryInput4(){
	return true;
}

function afterQuery(){
}

//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm(){
  try{
	  initForm();
  }
  catch(re){
  	myAlert(""+"��CertifySendOutInput.js"+"-->"+"resetForm�����з����쳣:��ʼ���������!"+"");
  }
} 

//�ύǰ��У�顢����  
function beforeSubmit(){
  //��Ӳ���	
}   

function afterCodeSelect( cCodeName, Field ){
	//ѡ���˴���
	if( cCodeName == "detailflag"){
		switch(fm.all('DeTailFlag').value){
			case "01":
			//risk
			divCertifyType1.style.display='';
			divCertifyType2.style.display='none';
			fm.DeTailType.value="RISK";
			break;
			
			case "02":
			//duty
			divCertifyType1.style.display='none';
			divCertifyType2.style.display='';
			fm.DeTailType.value="DUTY";
			break;
		}
	}
}
