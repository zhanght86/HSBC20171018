<%
//�������ƣ�ReportQueryInit.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">
//����ʱ��ѯ
function reportDetailClick(cObj)
{
  	var ex,ey;
  	ex = window.event.clientX+document.body.scrollLeft;  //�õ��¼�������x
  	ey = window.event.clientY+document.body.scrollTop;   //�õ��¼�������y
  	divLJSGetEndorse.style.left=ex;
  	divLJSGetEndorse.style.top =ey;
    	divLJSGetEndorse.style.display ='';
}

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 

  try
  {                                   
	

  }
  catch(ex)
  {
    alert("��LJSGetEndorseInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

// ������ĳ�ʼ��
function initSelBox()
{  
  try                 
  {
//    setOption("t_sex","0=��&1=Ů&2=����");      
//    setOption("sex","0=��&1=Ů&2=����");        
//    setOption("reduce_flag","0=����״̬&1=�����");
//    setOption("pad_flag","0=����&1=�潻");   
  }
  catch(ex)
  {
    alert("��LCPolQueryInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        
// ��ѯ��ť
function easyQueryClick(Flag)
{
	var tEdorNo;
	var tPolNo;
	var tEdorType;
	var tGrpPolNo;
	// ��дSQL���
	var strSQL = "";
	var mySql1=new SqlClass();
	mySql1.setResourceName("bq.LJSGetEndorseInitSql"); //ָ��ʹ�õ�properties�ļ���
	//alert("---"); 
	tEdorNo = top.opener.document.all('EdorNo').value;
      	//alert(tEdorNo);
  tEdorType = top.opener.document.all('EdorType').value;
      	//alert(tEdorType);
  var queryFlag = "0";   	
  if (top.opener.document.all('ContType').value=='1')
	{
	     	tPolNo  = top.opener.document.all('PolNo').value;
// 	     	strSQL = "select PolNo,PayPlanCode,FeeFinaType,GetMoney from LJSGetEndorse where EndorsementNo='"+ tEdorNo + "' and FeeOperationType='" + tEdorType+"'";
	     	var sqlid1="LJSGetEndorseInitSql1";
	     	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	    	mySql1.addSubPara(tEdorNo);//ָ������Ĳ���
	    	mySql1.addSubPara(tEdorType);//ָ������Ĳ���
	}
	else
	{
		tGrpPolNo = top.opener.document.all('GrpPolNo').value;
		try
		{
		   	tPolNo  = top.opener.document.all('QueryPolNo').value;
				queryFlag="1";
		}
		catch(ex)
		{
		}
		if (queryFlag=="1")
		{
// 			strSQL = "select PolNo,PayPlanCode,FeeFinaType,GetMoney from LJSGetEndorse where EndorsementNo='"+ tEdorNo + "' and FeeOperationType='" + tEdorType+"' and PolNo='"+tPolNo+"'";
			var sqlid2="LJSGetEndorseInitSql2";
	     	mySql1.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	    	mySql1.addSubPara(tEdorNo);//ָ������Ĳ���
	    	mySql1.addSubPara(tEdorType);//ָ������Ĳ���
	    	mySql1.addSubPara(tPolNo);//ָ������Ĳ���
		}
		else
// 			strSQL = "select PolNo,PayPlanCode,FeeFinaType,GetMoney from LJSGetEndorse where EndorsementNo='"+ tEdorNo + "' and FeeOperationType='" + tEdorType+"'";
			var sqlid3="LJSGetEndorseInitSql3";
	     	mySql1.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	    	mySql1.addSubPara(tEdorNo);//ָ������Ĳ���
	    	mySql1.addSubPara(tEdorType);//ָ������Ĳ���
	}		
	//alert(strSQL);	  
	var strsql = "";
	switch(Flag) 
	{
		case "PolNo":
			strsql = " order by PolNo";
			break;
		case "PayPlanCode":
			strsql = "order by PayPlanCode";
			break;
		case "FeeFinaType":
			strsql = "order by FeeFinaType";
			break;
		case "GetMoney":
			strsql = "order by GetMoney";
			break;
		default:
			break;
		
	}		
	//alert(strSQL);	
		//alert("querybef");	
	mySql1.addSubPara(strsql);//ָ������Ĳ���
	strSQL=mySql1.getString();
	execEasyQuery( strSQL );
	//alert("query");
}

function displayEasyResult(arrResult )
{
	var i, j, m, n;
//alert("init");
	if( arrResult == null )
		alert( "û���ҵ���ص�����!" );
	else
	{
		// ��ʼ�����
		//alert("init");
		initLJSGetEndorseGrid();
		
		arrGrid = arrResult;
		// ��ʾ��ѯ���
		n = arrResult.length;
		//alert(n);
		for( i = 0; i < n; i++ )
		{
			m = arrResult[i].length;
			//alert(i);
			for( j = 0; j < m; j++ )
			{
				//alert("j;"+j);
				LJSGetEndorseGrid.setRowColData( i, j+1, arrResult[i][j] );
			} // end of for
		} // end of for
		//alert("result:"+arrResult);
	} // end of if
}

function initForm(Flag)
{
  try
  {
  //alert('111');
    initInpBox();
    initSelBox();    
    //alert('222');
    initLJSGetEndorseGrid();
   // alert('333');
    
    //easyQueryClick(Flag);
  }
  catch(re)
  {
    alert("LJSGetEndorseInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ��Ϣ�б�ĳ�ʼ��
function initLJSGetEndorseGrid()
{
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="������";    	//����
      iArray[1][1]="200px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="������Ŀ����";		//����
      iArray[2][1]="100px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="�������ͱ���";		//����
      iArray[3][1]="200px";            		//�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="���ý��";         		//����
      iArray[4][1]="200px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������


      LJSGetEndorseGrid = new MulLineEnter( "fm" , "LJSGetEndorseGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      LJSGetEndorseGrid.mulLineCount = 10;   
      LJSGetEndorseGrid.displayTitle = 1;
      LJSGetEndorseGrid.canSel=1;
      LJSGetEndorseGrid.loadMulLine(iArray);  
      LJSGetEndorseGrid.detailInfo="������ʾ��ϸ��Ϣ";
      LJSGetEndorseGrid.detailClick=reportDetailClick;
      //��Щ����������loadMulLine����
    
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>