var showInfo;
var mDebug="0";
var DealWithNam ;
var turnPage = new turnPageClass(); 
var turnPage1 = new turnPageClass(); 

function billQuery()
{
//	var strSQL = "select ArithmeticDefID,ArithmeticDefName,decode(ArithContType,'01','��ͬ�����㷨','02','�ٷַ����㷨'),standbystring2,CalFeeTerm,CalFeeType from RICalDef where 1=1 "
//		+getWherePart('ArithmeticDefID','ArithmeticDefID')
//		+getWherePart('ArithContType','ArithContType');

	var mySql1=new SqlClass();
		mySql1.setResourceName("reinsure.RIItemCalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId("RIItemCalInputSql001");//ָ��ʹ�õ�Sql��id
	    mySql1.addSubPara(fm.ArithmeticDefID.value);//ָ������Ĳ���
	    mySql1.addSubPara(fm.ArithContType.value);//ָ������Ĳ���
	var strSQL=mySql1.getString();
	
	turnPage.queryModal(strSQL,ResultGrid);
}

function queryDetial()
{
	var num = ResultGrid.getSelNo();
	var billNo = ResultGrid.getRowColData(num-1,1);
	//var strSQL = "select ArithmeticDefID,ArithmeticID,ArithmeticName,ArithmeticType,decode(ArithmeticType,'00','������ȡ�㷨','02','���������㷨','03','����׼���㷨','04','�ֱ��������������','05','�ֱ��������㷨','14','�����Ժ��㷨'),CalItemID,CalItemName,CalItemOrder,CalItemType,ItemCalType,DoubleValue,CalClass,CalSQL,TarGetClumn,ReMark,StandbyString1,StandbyString2,StandbyString3 from RIItemCal where ArithmeticDefID = '"+billNo+"' order by ArithmeticType,CalItemOrder ";
	
	var mySql1=new SqlClass();
		mySql1.setResourceName("reinsure.RIItemCalInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId("RIItemCalInputSql002");//ָ��ʹ�õ�Sql��id
	    mySql1.addSubPara(billNo);//ָ������Ĳ���
	var strSQL=mySql1.getString();
	
	turnPage1.queryModal(strSQL,KResultGrid);

}

function afterCodeSelect( cName, Filed)
{   
	if(fm.all('DistillMode').value=='1')
	{
		 classdiv.style.display='none';
		 sqldiv.style.display='none'; 
		 numdiv.style.display=''; 
		 fm.all('DistillClass').value = '';
		 fm.all('DistillSQL').value = '';
	}
	
	if(fm.all('DistillMode').value=='2')
	{
		 classdiv.style.display='none';
		 numdiv.style.display='none'; 
		 sqldiv.style.display=''; 
		 fm.all('DistillClass').value = '';
		 fm.all('DoubleValue').value = '';
	}
			
  if(fm.all('DistillMode').value=='3')
  {
		 classdiv.style.display='';
		 sqldiv.style.display='none';	
		 numdiv.style.display='none'; 
		 fm.all('DistillSQL').value = '';
		 fm.all('DoubleValue').value = '';
	}
	if((fm.all('DistillMode').value == null)||(fm.all('DistillMode').value == ''))
  {
		 classdiv.style.display='none';
		 sqldiv.style.display='none';	
		 numdiv.style.display='none'; 
		 fm.all('DistillSQL').value = '';
		 fm.all('DoubleValue').value = '';
		 fm.all('DistillClass').value = '';
	}			

}

function DKmis()
{
	fm.all('DistillMode').value = '';
	fm.all('DistillModeName').value = '';
	fm.all('KArithmeticDefID').value = '';
	fm.all('ArithmeticID').value = '';
	fm.all('ArithmeticName').value = '';
	fm.all('ArithmeticType').value = '';
	fm.all('CalItemID').value = '';
	fm.all('CalItemName').value = '';
	fm.all('CalItemOrder').value = '';
	fm.all('CalItemType').value = '';
	fm.all('TarGetClumn').value = '';
	fm.all('ReMark').value = '';
	fm.all('StandbyString1').value = '';
	fm.all('StandbyString2').value = '';
	fm.all('StandbyString3').value = '';
	fm.all('DistillClass').value = '';
		 fm.all('DistillSQL').value = '';
		 fm.all('DoubleValue').value = '';
	var num = KResultGrid.getSelNo();
	//var llm = KResultGrid.getRowColData(num-1,1);
	//alert(llm);
	fm.all('KArithmeticDefID').value = KResultGrid.getRowColData(num-1,1);
	fm.all('ArithmeticID').value = KResultGrid.getRowColData(num-1,2);
	fm.all('ArithmeticName').value = KResultGrid.getRowColData(num-1,3);
	fm.all('ArithmeticType').value = KResultGrid.getRowColData(num-1,4);
	fm.all('CalItemID').value = KResultGrid.getRowColData(num-1,6);
	fm.all('CalItemName').value = KResultGrid.getRowColData(num-1,7);
	fm.all('CalItemOrder').value = KResultGrid.getRowColData(num-1,8);
	fm.all('CalItemType').value = KResultGrid.getRowColData(num-1,9);
	fm.all('DistillMode').value = KResultGrid.getRowColData(num-1,10);
	if(fm.all('DistillMode').value=='1')
	{
		 classdiv.style.display='none';
		 sqldiv.style.display='none'; 
		 numdiv.style.display=''; 
		 fm.all('DistillClass').value = '';
		 fm.all('DistillSQL').value = '';
		 fm.all('DoubleValue').value = KResultGrid.getRowColData(num-1,11);
	}
	
	if(fm.all('DistillMode').value=='2')
	{
		 classdiv.style.display='none';
		 numdiv.style.display='none'; 
		 sqldiv.style.display=''; 
		 fm.all('DistillClass').value = '';
		 fm.all('DoubleValue').value = '';
		 fm.all('DistillSQL').value = KResultGrid.getRowColData(num-1,13);
	}
			
  if(fm.all('DistillMode').value=='3')
  {
		 classdiv.style.display='';
		 sqldiv.style.display='none';	
		 numdiv.style.display='none'; 
		 fm.all('DistillSQL').value = '';
		 fm.all('DoubleValue').value = '';
		 fm.all('DistillClass').value = KResultGrid.getRowColData(num-1,12);
	}
	if((fm.all('DistillMode').value == null)||(fm.all('DistillMode').value == ''))
  {
		 classdiv.style.display='none';
		 sqldiv.style.display='none';	
		 numdiv.style.display='none'; 
		 fm.all('DistillSQL').value = '';
		 fm.all('DoubleValue').value = '';
		 fm.all('DistillClass').value = '';
	}
	fm.all('TarGetClumn').value = KResultGrid.getRowColData(num-1,14);
	fm.all('ReMark').value = KResultGrid.getRowColData(num-1,14);
	fm.all('StandbyString1').value = KResultGrid.getRowColData(num-1,16);
	fm.all('StandbyString2').value = KResultGrid.getRowColData(num-1,17);
	fm.all('StandbyString3').value = KResultGrid.getRowColData(num-1,18);
	showCodeName();
}

function add()
{
	if((fm.all('KArithmeticDefID').value == null)||(fm.all('KArithmeticDefID').value == ''))
	{
		myAlert(""+"�㷨�������Ϊ�գ���������д"+"");
		return false;
	}
	if((fm.all('ArithmeticID').value == null)||(fm.all('ArithmeticID').value == ''))
	{
		myAlert(""+"�㷨����Ϊ�գ���������д"+"");
		return false;
	}
	if((fm.all('ArithmeticName').value == null)||(fm.all('ArithmeticName').value == ''))
	{
		myAlert(""+"�㷨����Ϊ�գ���������д"+"");
		return false;
	}
	if((fm.all('ArithmeticType').value == null)||(fm.all('ArithmeticType').value == ''))
	{
		myAlert(""+"�㷨����Ϊ�գ���������д"+"");
		return false;
	}
	if((fm.all('CalItemID').value == null)||(fm.all('CalItemID').value == ''))
	{
		myAlert(""+"���������Ϊ�գ���������д"+"");
		return false;
	}
	if((fm.all('CalItemName').value == null)||(fm.all('CalItemName').value == ''))
	{
		myAlert(""+"����������Ϊ�գ���������д"+"");
		return false;
	}
	if((fm.all('CalItemOrder').value == null)||(fm.all('CalItemOrder').value == ''))
	{
		myAlert(""+"���������Ϊ�գ���������д"+"");
		return false;
	}
	if((fm.all('TarGetClumn').value == null)||(fm.all('TarGetClumn').value == ''))
	{
		myAlert(""+"�������洢�ֶ�Ϊ�գ���������д"+"");
		return false;
	}
	
	var i = 0;
  var showStr=""+"���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

  fm.action="RIItemCalAdd.jsp";
  //lockButton(); 
  fm.submit(); //�ύ
	
	
}

function update()
{
	var num = KResultGrid.getSelNo();
	if((fm.all('KArithmeticDefID').value == null)||(fm.all('KArithmeticDefID').value == ''))
	{
		myAlert(""+"�㷨�������Ϊ�գ���������д"+"");
		return false;
	}
	if((fm.all('ArithmeticID').value == null)||(fm.all('ArithmeticID').value == ''))
	{
		myAlert(""+"�㷨����Ϊ�գ���������д"+"");
		return false;
	}
	if((fm.all('ArithmeticName').value == null)||(fm.all('ArithmeticName').value == ''))
	{
		myAlert(""+"�㷨����Ϊ�գ���������д"+"");
		return false;
	}
	if((fm.all('ArithmeticType').value == null)||(fm.all('ArithmeticType').value == ''))
	{
		myAlert(""+"�㷨����Ϊ�գ���������д"+"");
		return false;
	}
	if((fm.all('CalItemID').value == null)||(fm.all('CalItemID').value == ''))
	{
		myAlert(""+"���������Ϊ�գ���������д"+"");
		return false;
	}
	if((fm.all('CalItemName').value == null)||(fm.all('CalItemName').value == ''))
	{
		myAlert(""+"����������Ϊ�գ���������д"+"");
		return false;
	}
	if((fm.all('CalItemOrder').value == null)||(fm.all('CalItemOrder').value == ''))
	{
		myAlert(""+"���������Ϊ�գ���������д"+"");
		return false;
	}
	if((fm.all('TarGetClumn').value == null)||(fm.all('TarGetClumn').value == ''))
	{
		myAlert(""+"�������洢�ֶ�Ϊ�գ���������д"+"");
		return false;
	}
	
	fm.all('ArithmeticID').value = KResultGrid.getRowColData(num-1,2);
	fm.all('ArithmeticType').value = KResultGrid.getRowColData(num-1,4);
	fm.all('CalItemOrder').value = KResultGrid.getRowColData(num-1,8);
	var i = 0;
  var showStr=""+"���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

  fm.action="RIItemCalUpdate.jsp";
  //lockButton(); 
  fm.submit(); //�ύ
}

function cd()
{
	var num = KResultGrid.getSelNo();
	fm.all('ArithmeticID').value = KResultGrid.getRowColData(num-1,2);
	fm.all('ArithmeticType').value = KResultGrid.getRowColData(num-1,4);
	fm.all('CalItemOrder').value = KResultGrid.getRowColData(num-1,8);
	var i = 0;
  var showStr=""+"���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(showStr));
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

  fm.action="RIItemCalDelete.jsp";
  //lockButton(); 
  fm.submit(); //�ύ
}

function afterSubmit( FlagStr, content )
{
  showInfo.close();
  //�ͷš����ӡ���ť

  if (FlagStr == "Fail" )
  {
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(content));
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		mOperate="";
		
  }
  else
  {
    //alert(content);
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+encodeURI(encodeURI(content));
  	//parent.fraInterface.initForm();
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");

//    showDiv(operateButton,"true");
//    showDiv(inputButton,"false");
    //ִ����һ������
    //resetForm(); 
  }
  initForm();

}
