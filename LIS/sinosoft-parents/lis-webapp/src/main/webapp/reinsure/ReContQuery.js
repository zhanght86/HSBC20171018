//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���

//�ύ�����水ť��Ӧ����
function submitForm()
{
	var strSQL = "";
	if(fm.PageFlag.value=="CONT")
	{
	/**
	  strSQL = "select ricontno,conttype,ricontname,"
	  +" risktype,decode(conttype,'01','������ͬ','02','�ٷֺ�ͬ','03','�����ٷ�',''),"
	  +" reinsurancetype,decode(reinsurancetype,'01','�����ֱ�','02','�Ǳ����ֱ�',''),cvalidate,enddate, "
	  +" State,decode(State,'0','δ��Ч','1','��Ч',''),gitype,decode(gitype,'1','�¶�','3','����','12','���') from RIBarGainInfo where 1=1 "
	  + getWherePart("RIContNo","RIContNo")
	  ;
	  strSQL = strSQL +" order by RIContNo";
	  */
	  var mySql100=new SqlClass();
	 	  mySql100.setResourceName("reinsure.ReContQuerySql"); //ָ��ʹ�õ�properties�ļ���
	  	  mySql100.setSqlId("ReContQuerySql100");//ָ��ʹ�õ�Sql��id
	 	  //mySql100.addSubPara(getWherePart("RIContNo","RIContNo"));//ָ������Ĳ���
	  	  mySql100.addSubPara(fm.RIContNo.value);//ָ������Ĳ���
	  	  strSQL=mySql100.getString();
	  
	}else
	{
	/**
		strSQL = "select ricontno,conttype,decode(conttype,'1','������ͬ','2','�ٷֺ�ͬ','3','�����ٷ�',''),"
	  +" risktype,decode(conttype,'1','������ͬ','2','�ٷֺ�ͬ','03','�����ٷ�',''),"
	  +" reinsurancetype,decode(reinsurancetype,'01','�����ֱ�','02','�Ǳ����ֱ�',''),cvalidate,enddate,"
	  +" State,decode(State,'0','δ��Ч','1','��Ч',''),gitype,decode(gitype,'1','�¶�','3','����','12','���') from RIBarGainInfo where 1=1 "
	  + getWherePart("RIContNo","RIContNo")
	  ;
	  strSQL = strSQL +" order by RIContNo";
	  */
	  var mySql101=new SqlClass();
	 	  mySql101.setResourceName("reinsure.ReContQuerySql"); //ָ��ʹ�õ�properties�ļ���
	  	  mySql101.setSqlId("ReContQuerySql101");//ָ��ʹ�õ�Sql��id
	 	  //mySql101.addSubPara(getWherePart("RIContNo","RIContNo"));//ָ������Ĳ���
	  	  mySql101.addSubPara(fm.RIContNo.value);//ָ������Ĳ���
	  	  strSQL=mySql101.getString();
	}
	
	var arrResult = new Array();
	//arrResult = easyExecSql(strSQL);
		
	turnPage.queryModal(strSQL, ReContGrid);
  
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{

  //showInfo.close();
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

function ReturnData()
{
	if(fm.PageFlag.value=="CONT")
	{
		//��ѡ��һ��
		var tRow=ReContGrid.getSelNo();
  	if (tRow==0)
  	{
  		myAlert(""+"�����Ƚ���ѡ��!"+"");
  		return;
  	}
  	/**
  	var strSQL = "select RIContNo,RIContName,ContType,(case ContType when '01' then '������ͬ' when '02' then '�ٷֺ�ͬ' when '03' then '�����ٷ�' end), "
		+" ReInsuranceType,(case ReInsuranceType when '01' then '�����ֱ�' when '02' then '���ֱ�' when '03' then '�������ֱ�' end), "
		+" CValiDate,EndDate,RISignDate,state,decode(State,'0','δ��Ч','1','��Ч',''),gitype,decode(gitype,'1','�¶�','3','����','12','���') "
		+" from RIBarGainInfo "
  	+ "where 1=1 and RIContNo='"+ReContGrid.getRowColData(tRow-1,1)+"'"
  	;
  	*/
  	var mySql102=new SqlClass();
	 	mySql102.setResourceName("reinsure.ReContQuerySql"); //ָ��ʹ�õ�properties�ļ���
	  	mySql102.setSqlId("ReContQuerySql102");//ָ��ʹ�õ�Sql��id
	 	mySql102.addSubPara(ReContGrid.getRowColData(tRow-1,1));//ָ������Ĳ���
	var strSQL=mySql102.getString();
  	//ִ��SQL��ѯ��䣬��strArray ���� ��Ų�ѯ���
  	strArray=easyExecSql(strSQL);	
  	
  	
  	//�����ѯ���Ϊ�գ�
  	if (strArray==null)
  	{
  		myAlert(""+"�޷�����"+","+"�����ݿ��ܸձ�ɾ��!"+"");
  		return false;
  	}
  	
  	//�����ѯ�����Ϊ�գ���Ѳ�ѯ�Ľ�������Զ���䵽��ҳ���Ӧ�Ķ�����
  	top.opener.fm.all('RIContNo').value 					=strArray[0][0];
  	top.opener.fm.all('RIContName').value 				=strArray[0][1];
  	top.opener.fm.all('ContType').value 					=strArray[0][2];
  	top.opener.fm.all('ContTypeName').value	 			=strArray[0][3];
  	top.opener.fm.all('ReInsuranceType').value	 	=strArray[0][4];
  	top.opener.fm.all('ReInsuranceTypeName').value=strArray[0][5];
  	top.opener.fm.all('RValidate').value 					=strArray[0][6];
  	top.opener.fm.all('RInvalidate').value 				=strArray[0][7];
  	top.opener.fm.all('RSignDate').value					=strArray[0][8];
  	top.opener.fm.all('ContState').value					=strArray[0][9];    
  	top.opener.fm.all('ContStateName').value			=strArray[0][10];
  	top.opener.fm.all('BillingCycle').value			=strArray[0][11];
  	top.opener.fm.all('BillingCycleName').value			=strArray[0][12];
  	
  	//�������������ϵ�˱�RIBarGainSigner�в�ѯ��Ӧ�ļ�¼��
   	//strSQL="select (select ComPanyName from RIComInfo where ComPanyNo=a.recomcode),a.recomcode,a.RelaName,a.Duty,a.RelaTel,a.MobileTel,a.FaxNo,a.Email,a.relacode from RIBarGainSigner a where RIContNo='"+ReContGrid.getRowColData(tRow-1,1)+"'";
   	var mySql103=new SqlClass();
	 	mySql103.setResourceName("reinsure.ReContQuerySql"); //ָ��ʹ�õ�properties�ļ���
	  	mySql103.setSqlId("ReContQuerySql103");//ָ��ʹ�õ�Sql��id
	 	mySql103.addSubPara(ReContGrid.getRowColData(tRow-1,1));//ָ������Ĳ���
		strSQL=mySql103.getString();
   	strArray=easyExecSql(strSQL);
   	top.opener.SignerGrid.clearData();
	 	if (strArray!=null)
	 	{
			for (var k=0;k<strArray.length;k++)
			{	  
  			top.opener.SignerGrid.addOne("SignerGrid");
  			
				top.opener.SignerGrid.setRowColData(k,1,strArray[k][0]); 
				top.opener.SignerGrid.setRowColData(k,2,strArray[k][1]); 
				top.opener.SignerGrid.setRowColData(k,3,strArray[k][2]); 
				top.opener.SignerGrid.setRowColData(k,4,strArray[k][3]);
				top.opener.SignerGrid.setRowColData(k,5,strArray[k][4]);
				top.opener.SignerGrid.setRowColData(k,6,strArray[k][5]);
				top.opener.SignerGrid.setRowColData(k,7,strArray[k][6]);
				top.opener.SignerGrid.setRowColData(k,8,strArray[k][7]);
				top.opener.SignerGrid.setRowColData(k,9,strArray[k][8]);
			}
		}
		
		//strSQL="select a.FactorName,a.FactorCode,a.FactorValue from RICalFactorValue a where a.ReContCode='"+ReContGrid.getRowColData(tRow-1,1)+"' and a.FactorClass='01' ";
		var mySql104=new SqlClass();
	 		mySql104.setResourceName("reinsure.ReContQuerySql"); //ָ��ʹ�õ�properties�ļ���
	  		mySql104.setSqlId("ReContQuerySql104");//ָ��ʹ�õ�Sql��id
	 		mySql104.addSubPara(ReContGrid.getRowColData(tRow-1,1));//ָ������Ĳ���
			strSQL=mySql104.getString();
		strArray=easyExecSql(strSQL);
		top.opener.FactorGrid.clearData();
		if (strArray!=null)
		{
			for (var k=0;k<strArray.length;k++)
			{	
  			top.opener.FactorGrid.addOne("FactorGrid");
				top.opener.FactorGrid.setRowColData(k,1,strArray[k][0]);
				top.opener.FactorGrid.setRowColData(k,2,strArray[k][1]);
				top.opener.FactorGrid.setRowColData(k,3,strArray[k][2]);
			}
		}
  }
 	top.close(); 
}

function ClosePage()
{
	top.close();
}
