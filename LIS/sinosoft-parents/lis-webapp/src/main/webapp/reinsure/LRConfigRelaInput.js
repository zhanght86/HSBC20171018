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
		if(  verifyInput() == true&&RGrid.checkValue("RGrid")) 
		{
			
		  	var i = 0;
		  	var showStr=""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
		  	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
				fm.action="./LRConfigRelaSave.jsp";
		  	fm.submit(); //�ύ
	  	
	  }
  } catch(ex){
  	myAlert(ex);
  	showInfo.close( );
  	
  }
}
function qeuryGrid(){
	var tAccumulateno=fm.AccumulateDefNO.value;
	var mySql100=new SqlClass();
		mySql100.setResourceName("reinsure.LRConfigRelaInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql100.setSqlId("LRConfigRelaInputSql100");//ָ��ʹ�õ�Sql��id
		mySql100.addSubPara(tAccumulateno);//ָ������Ĳ���
	    sql=mySql100.getString();
	
	//sql="select distinct a.ACCUMULATEDEFNO,a.UNIONACCNO from RIUnionAccumulate a where a.ACCUMULATEDEFNO='"+tAccumulateno+"'";

	turnPage.queryModal(sql,RelateGrid);

	
}

function queryClick(){
  fm.OperateType.value="QUERY";
	
  
  window.open("./FrameLRConfigRelaQuery.jsp?Serial=");
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
	  initRelateGrid();
    qeuryGrid();
  }
}

function updateClick(){
	fm.OperateType.value="UPDATE";
	if(!confirm(""+"��ȷ��Ҫ�޸��ۼƷ�����"+"")){
		return false;
	}
	try {
		if( verifyInput() == true && RelateGrid.checkValue("RelateGrid")) {	
			
		  	var i = 0;
		  	var showStr=""+"���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
		  	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
				
				fm.action="./LRConfigRelaSave.jsp";
		  	fm.submit(); //�ύ
	  	
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
			fm.action="./LRConfigRelaSave.jsp";
		  fm.submit(); //�ύ
	  }else{
	  	
	  }
  } catch(ex){
  	showInfo.close( );
  	myAlert(ex);
  }
}

function configRelation(){
	window.open("./FrameConfigRelation.jsp?Serial=");
}


function veriryInput4(){
	return true;
}
function inputRela(){
	fm.UNIONACCNO.value="";
	RGrid.clearData();
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
function showRela(){

	var tRow=RelateGrid.getSelNo();
	fm.UNIONACCNO.value=RelateGrid.getRowColData(tRow-1,2);
	var mySql101=new SqlClass();
		mySql101.setResourceName("reinsure.LRConfigRelaInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql101.setSqlId("LRConfigRelaInputSql101");//ָ��ʹ�õ�Sql��id
		mySql101.addSubPara(RelateGrid.getRowColData(tRow-1,1));//ָ������Ĳ���
		mySql101.addSubPara(RelateGrid.getRowColData(tRow-1,2));//ָ������Ĳ���
	var strSQL=mySql101.getString();
	//var strSQL="select a.associatedcode,b.associatedname from RIUnionAccumulate a,RIAccumulateRDCode b where a.accumulatedefno=b.accumulatedefno and a.associatedcode=b.associatedcode and a.accumulatedefno='"+RelateGrid.getRowColData(tRow-1,1)+"' and a.unionaccno='"+RelateGrid.getRowColData(tRow-1,2)+"'";
	strArray = easyExecSql(strSQL);
	if(strArray!=null){
		RGrid.clearData();
		for (var k=0;k<strArray.length;k++){
					RGrid.addOne("RGrid");
					RGrid.setRowColData(k,1,strArray[k][0]);
					RGrid.setRowColData(k,2,strArray[k][1]);
					
				}
	}
	
	
	
}
function ClosePage(){
	top.close();
}
