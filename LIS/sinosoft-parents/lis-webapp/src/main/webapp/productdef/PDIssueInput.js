//程序名称：PDIssueInput.js
//程序功能：问题件录入
//创建日期�?009-4-2
//该文件中包含客户端需要处理的函数和事�?
var turnPage = new turnPageClass();
var showInfo;
//定义sql配置文件
var tResourceName = "productdef.PDIssueInputInputSql";
function submitForm()
{
  var showStr=""+"���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  
  fm.submit();
}

function afterSubmit( FlagStr, content )
{
  showInfo.close();

  if (FlagStr == "Fail" )
  {             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  }
  else
  {
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    initForm();    
  } 
}
var Mulline9GridTurnPage = new turnPageClass(); 
function ins()
{
	if(!verifyInput2()){
		return false;
	}
	
	//if( fm.all('PostCode').value == fm.all('BackPost').value )
	//{
	//	alert("不能将问题件发�?给自己！");
	//	return;
	//}
	
	var showStr=""+"���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
    showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  
    fm.all("operator").value="insert";
    //fm.all('IssueType').value = '0';
  	fm.submit();
}
function del()
{
	var selNo = Mulline9Grid.getSelNo();
	if( selNo == 0 || selNo == null )
	{
		myAlert(""+"��ѡ����Ҫɾ���������"+" "+"��"+"");
		return;
	}
	
	var tIssuestate = Mulline9Grid.getRowColData(selNo-1,4);
	if(tIssuestate!="0"){
		myAlert(""+"ֻ�����������������ɾ��"+","+"�Ѿ����ͻ��߻ظ��Ĳ�������ɾ��"+"");
		return;
	}
	
	var showStr=""+"���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  
  fm.all('SerialNo').value = Mulline9Grid.getRowColData(selNo-1,1);
 	fm.all("operator").value="delete";
 	fm.submit();
}
function DealIssue()
{
	//PostCode应当是�?中的MulLine中的�?
    var selNo = Mulline9Grid.getSelNo();
    if( selNo == 0 )
    {
    	myAlert(""+"��ѡ��һ���������¼"+"");
    	return;
    }
    // 判断问题件状�?
    if( Mulline9Grid.getRowColData(selNo-1,4) != '0' )
    {
    	myAlert(""+"��������ѷ��ͣ�"+"");
    	return;
    }
    //如果已有发�?了到01节点的问题件，则不能再发送到00节点的，反之亦然
    var mbackPost = ""; //已发送的岗位
    for( var i=1;i<=Mulline9Grid.mulLineCount;i++ )
    {
    	if( Mulline9Grid.getRowColData(i-1,4) == '1' )
    	{
    		mbackPost = Mulline9Grid.getRowColData(i-1,2);
    		break;
    	}
    }
    var nBackPost = Mulline9Grid.getRowColData(selNo-1,2);// 待发送的岗位
    
    if( mbackPost != "" )
    {
    	mbackPost = mbackPost.substring(0,1);
    	nBackPost = nBackPost.substring(0,1);
    	
    	if( mbackPost != nBackPost )
    	{
    		myAlert(""+"���ظ�λ���ܽ��棡"+"");
    		return;
    	}
    }
    
    fm.SerialNo.value = Mulline9Grid.getRowColData(selNo-1,1);
    fm.BackPost.value = nBackPost;
	var showStr=""+"���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
	
	fm.all("operator").value="deal";
	fm.submit();
}
function SendIssue2(FlagStr, content )
{
	//showInfo.close();
	var selNo = Mulline9Grid.getSelNo();
    fm.all('PostCode').value = Mulline9Grid.getRowColData(selNo-1,2);
	
  	if (FlagStr == "Fail" )
  	{             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+content;
    	showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    	return;
    }
	
	//top.opener?? 该页面的opener多了去了，并不仅仅是产品测试与发布页面PDTestDeployInput.jsp
	if(fm.all('PostCode').value=='00'){
		//直接调用工作流提�?
		//top.opener.baseInfoDone();
		baseInfoDone();
	}else if(fm.all('PostCode').value=='10'){
		//直接调用工作流提�?
		//top.opener.button119();
		button119();
	}else if(fm.all('PostCode').value=='12'){
		//直接调用工作流提�?
		//top.opener.btnClaimDone();
		btnClaimDone();
	}else if(fm.all('PostCode').value=='11'){
		//直接调用工作流提�?
		//top.opener.btnEdorDone();
		btnEdorDone();
	}else if(fm.all('PostCode').value=='20'){
		//top.opener.btnAuditDone();
		myAlert(""+"������������λ����"+"");
	}else if(fm.all('PostCode').value=='30'){
		//top.opener.btnDeployIssue();
		myAlert(""+"������������λ����"+"");		
	}else if(fm.all('PostCode').value=='40'){
		//top.opener.btnModifyIssue();
		myAlert(""+"������������λ����"+"");		
	}
	top.close();
}

function queryMulline9Grid()
{
   var strSQL = "";
   var mySql=new SqlClass();
	 var sqlid = "PDIssueInputInputSql1";
	 mySql.setResourceName(tResourceName); //指定使用的properties文件�?
	 mySql.setSqlId(sqlid);//指定使用的Sql的id
	 mySql.addSubPara(fm.all('RiskCode').value);//指定传入的参�?
	 mySql.addSubPara(fm.all('PostCode').value);//指定传入的参�?
   strSQL = mySql.getString();
   Mulline9GridTurnPage.pageLineNum  = 10;
   Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
}
function Upload() {
  var i = 0;
  var tImportFile = fmImport.all('FileName').value;                          

  if ( tImportFile.indexOf("\\")>0 )
    tImportFile =tImportFile.substring(tImportFile.lastIndexOf("\\")+1);
  if ( tImportFile.indexOf("/")>0 )
    tImportFile =tImportFile.substring(tImportFile.lastIndexOf("/")+1);
  if ( tImportFile.indexOf("_")>0)
    tImportFile = tImportFile.substring( 0,tImportFile.indexOf("_"));
  if ( tImportFile.indexOf(".")>0)
    tImportFile = tImportFile.substring( 0,tImportFile.indexOf("."));

	if(tImportFile==null||tImportFile==""){
  	myAlert(""+"��δѡ��Ҫ������ļ�����ѡ�к��ٵ�����ظ���"+"");
  	return;
  }
  var showStr=""+"�����������ݡ���"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  fmImport.action = "./UpLodeSave.jsp"; 
  fmImport.submit();
}
function afterUpLoad(FilePath,FileName){
	showInfo.close();
	var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+encodeURI(encodeURI(""+"���سɹ���"+"")) ;    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	fm.all('Filepath2').value=FilePath;
	fm.all('Filename2').value=FileName;
}
function CancleUpload(){                              
	document.fmImport.all('fileName').select();
	document.execCommand('Delete'); 
	fmImport.reset();
	//fm.all("FileName").value="";
	//document.fmImport.all('fileName');
	fm.all('Filepath2').value="";
  fm.all('Filename2').value="";
}

//产品基本信息定义完毕 copy from PDTestDeploy.js
function baseInfoDone()
{
  fm.all("operator").value="workflow";
  var alStr = checkRules("00");
  if(alStr != "")
  {
  	myAlert(alStr);
  	return;
  }
  fm.action = "./PDRiskDefiSave.jsp";
  var showStr=""+"���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  
  fm.submit();  
}

// copy from PDTestDeploy.js
function btnEdorDone()
{
  var alStr = checkRules("11");
  if(alStr != "")
  {
  	myAlert(alStr);
  	return;
  }

  fm.all("operator").value = "edor";
  fm.action = "./PDContDefiEntrySave.jsp";

  var showStr=""+"���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  
  fm.submit();
}

// 理赔工作流处�?copy from PDTestDeploy.js
function btnClaimDone()
{
   var alStr = checkRules("12"); 
   if(alStr != "")
   {
  	  myAlert(alStr);
  	  return;
   }
            
   fm.all("operator").value = "claim";
   fm.action = "./PDContDefiEntrySave.jsp";
   
   var showStr=""+"���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
   showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  
   fm.submit();
}


//契约工作流处�?copy from PDTestDeploy.js
function button119()
{
  //fm.all("IsDealWorkflow").value = "1";
  fm.all("operator").value = "cont";
  
  var alStr = checkRules("10");
  if(alStr != "")
  {
  	myAlert(alStr);
  	return;
  }
  
  fm.action = "./PDContDefiEntrySave.jsp";
  var showStr=""+"���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
	
  fm.submit(); 
}
