//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���

//�ύ�����水ť��Ӧ����
function submitForm()
{
/**
  var strSQL = "select ComPanyNo,ComPanyName,GrpAddress,GrpZipCode,Fax from RIComInfo where 1=1 "
  + getWherePart("ComPanyNo","ReComCode")
  + getWherePart("ComPanyName","ReComName","like")
  ;
  strSQL = strSQL +" order by ComPanyNo";
  */
  var mySql100=new SqlClass();
	  mySql100.setResourceName("reinsure.ReComQuerySql"); //ָ��ʹ�õ�properties�ļ���
	  mySql100.setSqlId("ReComQuerySql100");//ָ��ʹ�õ�Sql��id
	  /**
	  mySql100.addSubPara(getWherePart("ComPanyNo","ReComCode"));//ָ������Ĳ���
	  mySql100.addSubPara(getWherePart("ComPanyName","ReComName","like"));//ָ������Ĳ���
	  */
	  mySql100.addSubPara(fm.ReComCode.value);//ָ������Ĳ���
	  mySql100.addSubPara(fm.ReComName.value);//ָ������Ĳ���
  var strSQL=mySql100.getString();
  
  var arrResult = new Array();
	//arrResult = easyExecSql(strSQL);
	
	turnPage.queryModal(strSQL, ReComGrid)
  
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(content));
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  }
  else
  {
  }
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
  	myAlert(""+"��Proposal.js"+"-->"+"resetForm�����з����쳣:��ʼ���������!"+"");
  }
}

//ȡ����ť��Ӧ����
function cancelForm()
{
    showDiv(operateButton,"true");
    showDiv(inputButton,"false");
}

//�ύǰ��У�顢����
function beforeSubmit()
{
  //��Ӳ���
}

//Click�¼������������ͼƬʱ�����ú���
function addClick()
{
  //����������Ӧ�Ĵ���
  showDiv(operateButton,"false");
  showDiv(inputButton,"true");
}

//Click�¼�����������޸ġ�ͼƬʱ�����ú���
function updateClick()
{
  //����������Ӧ�Ĵ���
  myAlert("update click");
}

//Click�¼������������ѯ��ͼƬʱ�����ú���
function queryClick()
{
  //����������Ӧ�Ĵ���
	myAlert("query click");
	  //��ѯ���������һ��ģ̬�Ի��򣬲��ύ�������������ǲ�ͬ��
  //��ˣ����еĻ����Ҳ���Բ��ø�ֵ��
}

//Click�¼����������ɾ����ͼƬʱ�����ú���
function deleteClick()
{
  //����������Ӧ�Ĵ���
  myAlert("delete click");
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

//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,0,0,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}

	parent.fraMain.rows = "0,0,0,0,*";
}

function ReturnData(){
		var tRow=ReComGrid.getSelNo();
  	if (tRow==0){
   		myAlert(""+"�����Ƚ���ѡ��!"+"");
  		return;
  	}
  	//var strSQL = "select ComPanyNo,ComPanyName,GrpZipCode,GrpAddress,Fax,Remark from RIComInfo  where ComPanyNo='"+ReComGrid.getRowColData(tRow-1,1)+"'";
  	var mySql101=new SqlClass();
	  	mySql101.setResourceName("reinsure.ReComQuerySql"); //ָ��ʹ�õ�properties�ļ���
	  	mySql101.setSqlId("ReComQuerySql101");//ָ��ʹ�õ�Sql��id
	  	mySql101.addSubPara(ReComGrid.getRowColData(tRow-1,1));//ָ������Ĳ���
  	var strSQL=mySql101.getString();
  	strArray = easyExecSql(strSQL);
  	
  	if (strArray==null){
  		myAlert(""+"�޷�����"+","+"�����ݿ��ܸձ�ɾ��!"+"");
  		return false;
  	}
    
    //var ComType=strArray[0][2];
    top.opener.fm.all('ReComCode').value 	=strArray[0][0];
    top.opener.fm.all('ReComName').value 	=strArray[0][1];
    top.opener.fm.all('PostalCode').value =strArray[0][2];
    top.opener.fm.all('Address').value 		=strArray[0][3];
    top.opener.fm.all('FaxNo').value			=strArray[0][4];
    top.opener.fm.all('Note').value 			=strArray[0][5];
    /**
    strSQL = "select RelaName,Department,Duty,RelaTel,MobileTel,FaxNo,Email,RelaCode"
    +"  from RIComLinkManInfo"
	  + "  where  RecomCode='"+ReComGrid.getRowColData(tRow-1,1)+"'";
	*/
	var mySql102=new SqlClass();
	  	mySql102.setResourceName("reinsure.ReComQuerySql"); //ָ��ʹ�õ�properties�ļ���
	  	mySql102.setSqlId("ReComQuerySql102");//ָ��ʹ�õ�Sql��id
	  	mySql102.addSubPara(ReComGrid.getRowColData(tRow-1,1));//ָ������Ĳ���
  		strSQL=mySql102.getString();	
		strArray = easyExecSql(strSQL);
		top.opener.RelateGrid.clearData();
		
		if (strArray!=null)
		{
			for (var k=0;k<strArray.length;k++)
			{ 
				
				top.opener.RelateGrid.addOne("RelateGrid");
				top.opener.RelateGrid.setRowColData(k,1,strArray[k][0]);
				top.opener.RelateGrid.setRowColData(k,2,strArray[k][1]);
				top.opener.RelateGrid.setRowColData(k,3,strArray[k][2]);
				top.opener.RelateGrid.setRowColData(k,4,strArray[k][3]);
				top.opener.RelateGrid.setRowColData(k,5,strArray[k][4]);
				top.opener.RelateGrid.setRowColData(k,6,strArray[k][5]);
				top.opener.RelateGrid.setRowColData(k,7,strArray[k][6]);
				top.opener.RelateGrid.setRowColData(k,8,strArray[k][7]);
			}
		}
   top.close();
  
}

function ClosePage()
{
	top.close();
}
