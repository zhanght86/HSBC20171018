//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;
var mDebug="0";
var Action;
var tRowNo=0;
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var currentTask;
var turnPage1 = new turnPageClass();  


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(content));
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
  }
  else
  {
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+encodeURI(encodeURI(content));
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	}
	initForm();
}

function initTaskList(){
	//var strSQL = " select count(*) from RIWFLog a where a.tasktype='01' and a.nodestate<>'99' ";
	var mySql100=new SqlClass();
		mySql100.setResourceName("reinsure.RiCalSql"); //ָ��ʹ�õ�properties�ļ���
	 	mySql100.setSqlId("RiCalSql100");//ָ��ʹ�õ�Sql��id
		mySql100.addSubPara("1");
	var strSQL=mySql100.getString();
	arrResult = easyExecSql(strSQL);
	if(arrResult=='0'){
		currentTask='0';
		//strSQL = " select '',a.AccumulateDefNO,'','','','' from RIAccumulateDef a where a.state='01' order by AccumulateDefNO";
		
		var mySql101=new SqlClass();
			mySql101.setResourceName("reinsure.RiCalSql"); //ָ��ʹ�õ�properties�ļ���
	 		mySql101.setSqlId("RiCalSql101");//ָ��ʹ�õ�Sql��id
	    	mySql101.addSubPara("1");
	    	strSQL=mySql101.getString();
		arrResult = easyExecSql(strSQL);
		displayMultiline(arrResult,TaskListGrid);
	}
	else{
		currentTask='1';
		//strSQL = " select a.batchno,a.taskcode,a.startdate,a.enddate,decode(a.nodestate,'00','��������','01','ҵ��������ȡ','02','����У��','03','���ռ���','04','�ٱ�������','08','�ֱ���������','10','��ʼ������','11','���������','12','�ӿڱ����ݱ���','99','�������','��֪��״̬'),a.nodestate from RIWFLog a where a.tasktype='01' and a.nodestate<>'99' ";
		
		var mySql102=new SqlClass();
			mySql102.setResourceName("reinsure.RiCalSql"); //ָ��ʹ�õ�properties�ļ���
	 		mySql102.setSqlId("RiCalSql102");//ָ��ʹ�õ�Sql��id
	    	mySql102.addSubPara("1");
	    	strSQL=mySql102.getString();		
		arrResult = easyExecSql(strSQL);
		displayMultiline(arrResult,TaskListGrid);
		fm.StartDate.value=arrResult[0][2];
		fm.EndDate.value=arrResult[0][3];
	}
	
	return true;
}

function SubmitForm()
{
	if(!verifyInput()){
		return false;
	}
	if(!verifyInput1()){
		return false;
	}
	if(fm.ProcessType.value=='00'){
		if(!taskApply()){
			return false;
		}
	}
	if(fm.ProcessType.value=='01'){
		if(!distillData()){
			return false;
		}
	}
	if(fm.ProcessType.value=='02'){
		if(!checkData()){
			return false;
		}
	}
	if(fm.ProcessType.value=='03'){
		if(!calData()){
			return false;
		}
	}
	if(fm.ProcessType.value=='98'){
		if(!delTask()){
			return false;
		}
	}
}

function taskApply(){
	if(currentTask=='1'){
		myAlert(""+"�Բ��𡣵�ǰ����δ��ɣ����������´�������"+"");
		return false;
	}
	
	if(fm.StartDate.value == "" || fm.EndDate.value == "")
	{
		return false;
		myAlert(""+"��¼����ʼ���ں���ֹ����!"+"");
	}
	var selCount=TaskListGrid.mulLineCount;
	var chkFlag=false; 
	
	for (i=0;i<selCount;i++){
		if(TaskListGrid.getChkNo(i)==true){
			chkFlag=true;
			if(TaskListGrid.getRowColData(i,6)>='01'){
				myAlert(""+"��ѡ����ȫ�����й�������ȡ����"+","+"�����ظ���ȡ��"+"");
				return false;
			}
		}
	}
	if (chkFlag==false)
	{
		myAlert(""+"��ѡ��Ҫ���������"+"");
		return false;
	}
	
	try {
		var i = 0;
    var showStr=""+"���ڽ��������������	�������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
    showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");  
    fm.submit();
    
  }catch(ex){
  	showInfo.close( );
  	myAlert(ex);
  }
}

function distillData(){
	if(currentTask=='0'){
		myAlert(""+"�Բ��𡣵�ǰû�д��������񣬲��ܽ�������������"+"");
		return false;
	}
	var selCount=TaskListGrid.mulLineCount;
	var chkFlag=false; 
	for (i=0;i<selCount;i++){
		if(TaskListGrid.getChkNo(i)==true){
			chkFlag=true;
			if(TaskListGrid.getRowColData(i,6)>='01'){
				myAlert(""+"��ѡ����ȫ�����й�������ȡ����"+","+"�����ظ���ȡ��"+"");
				return false;
			}
		}
	}
	if (chkFlag==false)
	{
		myAlert(""+"��ѡ��Ҫ���������"+"");
		return false;
	}	
	try {
		var i = 0;
    var showStr=""+"���ڽ���ҵ��������ȡ�������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
    showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");  
    fm.submit();
    
  }catch(ex){
  	showInfo.close( );
  	myAlert(ex);
  }
	
}

function checkData(){
	if(currentTask=='0'){
		myAlert(""+"�Բ��𡣵�ǰû�д��������񣬲��ܽ�������У�������"+"");
		return false;
	}
	var selCount=TaskListGrid.mulLineCount;
	var chkFlag=false; 
	for (i=0;i<selCount;i++){
		if(TaskListGrid.getChkNo(i)==true){
			chkFlag=true;
		}
	}
	if (chkFlag==false)
	{
		myAlert(""+"��ѡ��Ҫ���������"+"");
		return false;
	}
	try {
		var i = 0;
    var showStr=""+"���ڽ�������У�飬�����Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
    showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");  
    fm.submit();
    
  }catch(ex){
  	showInfo.close( );
  	myAlert(ex);
  }
}

function calData(){
	if(currentTask=='0'){
		myAlert(""+"�Բ��𡣵�ǰû�д��������񣬲��ܽ�������У�������"+"");
		return false;
	}
	var chkFlag=false; 
	var selCount=TaskListGrid.mulLineCount;
	for (i=0;i<selCount;i++){
		if(TaskListGrid.getChkNo(i)==true){
			chkFlag=true;
			if(TaskListGrid.getRowColData(i,6)<'01'){
				myAlert(""+"����ȡҵ�����ݺ��ٽ����ٱ����㡣"+"");
				return false;
			}
		}
	}
	if (chkFlag==false)
	{
		myAlert(""+"��ѡ��Ҫ���������"+"");
		return false;
	}
	try {
		var i = 0;
    var showStr=""+"���ڽ����ٱ����㣬�����Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
    showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");  
    fm.submit();
    
  }catch(ex){
  	showInfo.close( );
  	myAlert(ex);
  }
}

function delTask(){
	if(currentTask=='0'){
		myAlert(""+"�Բ��𡣵�ǰû�д��������񣬲��ܽ���ɾ��������"+"");
		return false;
	}
	var chkFlag=false; 
	var selCount=TaskListGrid.mulLineCount;
	for (i=0;i<selCount;i++){
		if(TaskListGrid.getChkNo(i)==true){
			chkFlag=true;
		}
	}
	if (chkFlag==false)
	{
		myAlert(""+"��ѡ��Ҫ���������"+"");
		return false;
	}
	if (confirm(""+"��ɾ��ѡ�е�������־�����и������ҵ�����ݣ�ȷ����?"+"")) 
	{
    try {
		var i = 0;
    var showStr=""+"���ڽ�������ɾ�������������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
    showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");  
    fm.submit();
	  }catch(ex){
	  	showInfo.close( );
	  	myAlert(ex);
	  }
	}
}

function verifyInput1() //UPDATE У��
{	//alert(currentDate);
	if(compareDate(fm.StartDate.value,fm.EndDate.value)==1){
		myAlert(""+"��ʼ���ڲ��ܴ�����ֹ����!"+"");
		return false;
	}
	if(compareDate(fm.EndDate.value,currentDate)==1){
		myAlert(""+"��ֹ���ڲ��ܴ��ڵ�ǰ���ڣ�"+"");
		return false;
	}
	return true;
	
}
