
var showInfo;
var mDebug="0";
var flag;
var k = 0;
var turnPage = new turnPageClass();

//�ύ�����水ť��Ӧ����
function submitForm()
{
	
  if(fm.Content.value=="")
   {
  	alert("��������Լ��Ϣ��");
  	return;
   }
  //var i = 0;
  //var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  //var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  fm.Action.value ="INSERT";
  fm.submit(); //�ύ
}
//ɾ��ĳЩ��Լ
function deleteInfo()
{
 var tSel = SpecInfoGrid.getSelNo()-1;
  if(tSel<0)
   {
  	alert("��ѡ��һ����Լ����ɾ����");
  	return;
   }
  //var i = 0;
  //var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  //var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  fm.Action.value ="DELETE";
  fm.submit(); //�ύ
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit(tFlag,tContent)
{
    alert(tFlag+tContent);
    querySpec();
}
function getSpecContent(){
   var tSel = SpecInfoGrid.getSelNo()-1;
   fm.Content.value = SpecInfoGrid.getRowColData(tSel,2);
}


//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
	parent.fraMain.rows = "0,0,50,82,*";
  }
  else 
  {
  	parent.fraMain.rows = "0,0,0,82,*";
  }
}
         
function querySpec(){
   var tSQL ="select grpcontno,speccontent,operator,makedate,serialno,proposalgrpcontno from lccgrpspec where grpcontno ='"+GrpContno+"'";
     turnPage.queryModal(tSQL,SpecInfoGrid);
     fm.Content.value = "";
}         

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


function afterSubmitB( FlagStr, content )
{
	if(FlagStr=="Fail")
	{
		fm.all("info").value="����ʧ�ܣ�"
	}else{
	fm.all("info").value="����ɹ���"
}
	
}

