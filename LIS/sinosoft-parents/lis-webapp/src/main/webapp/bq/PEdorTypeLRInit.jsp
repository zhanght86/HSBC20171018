<%
//�������ƣ�PEdorInputInit.jsp
//�����ܣ�
//�������ڣ�2002-07-19 16:49:22
//������  ��Tjj
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->


<script language="JavaScript">

function initForm()
{
  try
  {
    initInpBox();
    initDivLayer();
    initCustomerGrid();
    queryCustomerInfo();
    initMainPolGrid();
    queryLostTimes();
    initLRQuery(); //��ѯ��¼�벹����Ϣ
    //showmoney();
  }
  catch(re)
  {
    alert("PEdorTypeLRInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initInpBox()
{

  try
  {
    Edorquery();
    try { document.getElementsByName("AppObj")[0].value = top.opener.document.getElementsByName("AppObj")[0].value; } catch (ex) {}
    document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
    document.all('ContNo').value = top.opener.document.all('ContNo').value;
    try { document.all('GrpContNo').value = top.opener.document.all('GrpContNo').value; } catch (ex) {}
    document.all('EdorType').value = top.opener.document.all('EdorType').value;
    document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
    document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;
    document.all('InsuredNo').value = top.opener.document.all('InsuredNo').value;
  }
  catch(ex)
  {
    alert("��PEdorTypeLRInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!" + ex);
  }
}

function initCustomerGrid()
{
    var iArray = new Array();

      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;

      iArray[1]=new Array();
      iArray[1][0]="�ͻ�����";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[1][1]="50px";         			//�п�
      iArray[1][2]=10;          			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��ɫ";					//����1
      iArray[2][1]="50px";            		//�п�
      iArray[2][2]=50;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="����";         			//����2
      iArray[3][1]="50px";            		//�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="�Ա�";         		//����8
      iArray[4][1]="60px";            		//�п�
      iArray[4][2]=30;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="��������";     //����6
      iArray[5][1]="50px";            		//�п�
      iArray[5][2]=50;            			//�����ֵ
      iArray[5][3]=8;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="֤������";         		//����8
      iArray[6][1]="60px";            		//�п�
      iArray[6][2]=30;            			//�����ֵ
      iArray[6][3]=0;

      iArray[7]=new Array();
      iArray[7][0]="֤������";         		//����5
      iArray[7][1]="120px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������

     

      CustomerGrid = new MulLineEnter( "fm" , "CustomerGrid" );
      //��Щ���Ա�����loadMulLineǰ
      CustomerGrid.mulLineCount = 1;
      CustomerGrid.displayTitle = 1;
      CustomerGrid.canSel=0;
      CustomerGrid.hiddenPlus = 1;
      CustomerGrid.hiddenSubtraction = 1;
      CustomerGrid.selBoxEventFuncName ="";
      CustomerGrid.loadMulLine(iArray);
      CustomerGrid.detailInfo="������ʾ��ϸ��Ϣ";
      CustomerGrid.loadMulLine(iArray);
      }
      catch(ex)
      {
        alert(ex);
      }
}

function queryCustomerInfo()
{
  var tContNo=top.opener.document.all('ContNo').value;
	var strSQL="";
	//alert("------"+tContNo+"---------");
	if(tContNo!=null || tContNo !='')
	  {
// 	  strSQL =  " Select a.appntno,'Ͷ����',a.appntname,a.appntsex||'-'||sex.codename,a.appntbirthday,a.idtype||'-'||x.codename,a.idno From lcappnt a "
// 										+" Left Join (Select code,codename From ldcode Where codetype='idtype') x On x.code = a.idtype "
// 										+" Left Join (Select code,codename From ldcode Where codetype='sex') sex On sex.code = a.appntsex  Where contno='"+tContNo+"'"
// 										+" Union"
// 										+" Select i.insuredno,'������',i.name,i.Sex||'-'||sex.codename,i.Birthday,i.IDType||'-'||xm.codename,i.IDNo From lcinsured i "
// 										+" Left Join (Select code,codename From ldcode Where codetype='idtype') xm On xm.code = i.idtype "
// 										+" Left Join (Select code,codename From ldcode Where codetype='sex') sex On sex.code = i.sex Where contno='"+tContNo+"'";
	//alert("-----------"+strSQL+"------------");
	  var sqlid1="PEdorTypeLRInputSql6";
	  var mySql1=new SqlClass();
	  mySql1.setResourceName("bq.PEdorTypeLRInputSql"); //ָ��ʹ�õ�properties�ļ���
	  mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	  mySql1.addSubPara(tContNo);//ָ������Ĳ���
	  mySql1.addSubPara(tContNo);//ָ������Ĳ���
	  strSQL=mySql1.getString();
	}
	else
	{
		alert('û����Ӧ��Ͷ���˻򱻱�����Ϣ��');
	}
	var arrSelected = new Array();
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);

	//�ж��Ƿ��ѯ�ɹ�
	if (!turnPage.strQueryResult) {
  //alert("û����Ӧ��Ͷ���˻򱻱�����Ϣ��");
		return false;
	}

	//�������������������ͬ��ѯ����һ��turnPage����ʱ����ʹ�ã���ü��ϣ��ݴ�
	turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
	//��ѯ�ɹ������ַ��������ض�ά����personQuery
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	//���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
	turnPage.pageDisplayGrid = CustomerGrid;
	//����SQL���
	turnPage.strQuerySql = strSQL;
	//���ò�ѯ��ʼλ��
	turnPage.pageIndex = 0;
	//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
	arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
	//����MULTILINE������ʾ��ѯ���
	displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}

function initMainPolGrid() 
{                               
    var iArray = new Array();

    try {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="���ֱ���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[1][1]="40px";         			//�п�
      //iArray[1][2]=10;          			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��������";					//����1
      iArray[2][1]="160px";            		//�п�
      //iArray[2][2]=50;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="�����˿ͻ���";
      iArray[3][1]="50px";
      iArray[3][2]=100;
      iArray[3][3]=0;

      iArray[4]=new Array();
      iArray[4][0]="����������";
      iArray[4][1]="50px";
      iArray[4][2]=100;
      iArray[4][3]=0;

      iArray[5]=new Array();
      iArray[5][0]="���ѱ�׼";         			//����2
      iArray[5][1]="40px";            		//�п�
      //iArray[5][2]=60;            			//�����ֵ
      iArray[5][3]=7;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[5][23]="0"; 
      
      iArray[6]=new Array();
      iArray[6][0]="����";         		//����8
      iArray[6][1]="40px";            		//�п�
      //iArray[6][2]=30;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="��������";         		//����5
      iArray[7][1]="40px";            		//�п�
      //iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=7;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������
      iArray[7][23]="0"; 
      
      iArray[8]=new Array();
      iArray[8][0]="��Ч����";     //����6
      iArray[8][1]="50px";            		//�п�
      //iArray[8][2]=50;            			//�����ֵ
      iArray[8][3]=8;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[9]=new Array();
      iArray[9][0]="���Ѷ�Ӧ��";         		//����8
      iArray[9][1]="50px";            		//�п�
      //iArray[9][2]=30;            			//�����ֵ
      iArray[9][3]=8;
      
      iArray[10]=new Array();
      iArray[10][0]="MainPolNo";             //������
      iArray[10][1]="0px";            		//�п�
      //iArray[10][2]=50;            			//�����ֵ
      iArray[10][3]=3;              			
      
      iArray[11]=new Array();
      iArray[11][0]="PolNo";             //������
      iArray[11][1]="0px";            		//�п�
      //iArray[11][2]=50;            			//�����ֵ
      iArray[11][3]=3;
      
      iArray[12]=new Array();
		  iArray[12][0]="����";
			iArray[12][1]="60px";
			iArray[12][2]=100;
			iArray[12][3]=2;
			iArray[12][4]="currency";
      
      MainPolGrid = new MulLineEnter( "fm" , "MainPolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      MainPolGrid.displayTitle = 1;
      MainPolGrid.canSel=0;
      MainPolGrid.hiddenPlus = 1;
      MainPolGrid.hiddenSubtraction = 1;
      MainPolGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
//       tSql = "SELECT distinct RiskCode,"
// 		             + " (select RiskName from LMRisk where RiskCode=a.RiskCode),"
// 		             + " InsuredNo,"
// 		             + " InsuredName,"
//        		       + " Prem,"
// 		 		         + " Mult,"
// 		 		         + " Amnt,"
// 		 		         + " CValiDate,"
// 		 		         + " PayToDate,"
// 		 		         + " MainPolNo,"
// 		 		         + " PolNo,"
// 		 		         + "currency"
// 		      + " FROM LCPol a"
// 		      + " WHERE ContNo='" + document.all('ContNo').value + "' order by polno";
      var tSql = "";
      var sqlid1="PEdorTypeLRInputSql7";
	  var mySql1=new SqlClass();
	  mySql1.setResourceName("bq.PEdorTypeLRInputSql"); //ָ��ʹ�õ�properties�ļ���
	  mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	  mySql1.addSubPara(document.all('ContNo').value);//ָ������Ĳ���
	  tSql=mySql1.getString();
		   easyQueryVer3Modal(tSql,MainPolGrid);
    }
    catch(ex) 
    {
       alert(ex);
    }
}
</script>
