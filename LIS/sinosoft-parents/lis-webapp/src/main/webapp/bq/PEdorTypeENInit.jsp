<%
//PEdorTypeENInit.jsp
//�����ܣ�
//�������ڣ�2005-06-20 11:10:36
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>

<script language="JavaScript">
//����ʱ��ѯ
var str_sql = "",sql_id = "", my_sql = "";
function initInpBox()
{

  try
  {
    Edorquery();  
    document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
    document.all('ContNo').value = top.opener.document.all('ContNo').value;
    document.all('EdorType').value = top.opener.document.all('EdorType').value;
    document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
    document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;
    document.all('InsuredNo').value = top.opener.document.all('InsuredNo').value;
    document.all('PolNo').value = top.opener.document.all('PolNo').value;
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
    //alert (document.all('EdorNo').value);

  }
  catch(ex)
  {
    alert("��PEdorTypeENInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}

/*
function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    alert("��PEdorTypeENInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}
*/

function initForm()
{
  try
  {

    initInpBox();
    //initSelBox();
    //initLCInsuredGrid();
    initCustomerGrid();
    queryCustomerInfo();
    initMainPolGrid();
    //initPolGrid();
    //showRiskInfo();
    initPolInfoGrid();
    QueryEdorInfo();
  }
  catch(re)
  {
    alert("PEdorTypeENInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

//��������Ҫ�˺���
//function initQuery()
//{
//	var tContNo=document.all("ContNo").value;
//	var strSQL ="select PolNo from LCPol where 1=1 and MainPolNo <> PolNo and PolNo = '" + tContNo + "'";
//
//
//}


//�ͻ�������Ϣ�б�
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
      iArray[1][1]="40px";         			//�п�
      iArray[1][2]=10;          			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��ɫ";					//����1
      iArray[2][1]="30px";            		//�п�
      iArray[2][2]=50;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="����";         			//����2
      iArray[3][1]="40px";            		//�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="�Ա�";         		//����8
      iArray[4][1]="20px";            		//�п�
      iArray[4][2]=30;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="��������";     //����6
      iArray[5][1]="30px";            		//�п�
      iArray[5][2]=50;            			//�����ֵ
      iArray[5][3]=8;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="֤������";         		//����8
      iArray[6][1]="20px";            		//�п�
      iArray[6][2]=30;            			//�����ֵ
      iArray[6][3]=0;

      iArray[7]=new Array();
      iArray[7][0]="֤������";         		//����5
      iArray[7][1]="50px";            		//�п�
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
	  sql_id = "PEdorTypeENInputSql2";
	  my_sql = new SqlClass();
	  my_sql.setResourceName("bq.PEdorTypeENInputSql"); //ָ��ʹ�õ�properties�ļ���
	  my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
	  my_sql.addSubPara(tContNo);//ָ������Ĳ���
	  my_sql.addSubPara(tContNo);//ָ������Ĳ���
	  strSQL = my_sql.getString();
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
      iArray[1][1]="20px";         			//�п�
      //iArray[1][2]=10;          			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��������";					//����1
      iArray[2][1]="50px";            		//�п�
      //iArray[2][2]=50;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="�����˿ͻ���";
      iArray[3][1]="20px";
      iArray[3][2]=100;
      iArray[3][3]=0;

      iArray[4]=new Array();
      iArray[4][0]="����������";
      iArray[4][1]="20px";
      iArray[4][2]=100;
      iArray[4][3]=0;

      iArray[5]=new Array();
      iArray[5][0]="���ѱ�׼";         			//����2
      iArray[5][1]="30px";            		//�п�
      //iArray[5][2]=60;            			//�����ֵ
      iArray[5][3]=7;              			//�Ƿ���������,1��ʾ����0��ʾ������
			iArray[5][23]="0";

      iArray[6]=new Array();
      iArray[6][0]="����";         		//����8
      iArray[6][1]="20px";            		//�п�
      //iArray[6][2]=30;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="��������";         		//����5
      iArray[7][1]="30px";            		//�п�
      //iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=7;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������
      iArray[7][23]="0";
      
      iArray[8]=new Array();
      iArray[8][0]="��Ч����";     //����6
      iArray[8][1]="30px";            		//�п�
      //iArray[8][2]=50;            			//�����ֵ
      iArray[8][3]=8;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[9]=new Array();
      iArray[9][0]="���Ѷ�Ӧ��";         		//����8
      iArray[9][1]="30px";            		//�п�
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
				iArray[12][1]="20px";
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
// 		      + " WHERE ContNo='" + document.all('ContNo').value + "'";
      var tSql = "";
      sql_id = "PEdorTypeENInputSql3";
	  my_sql = new SqlClass();
	  my_sql.setResourceName("bq.PEdorTypeENInputSql"); //ָ��ʹ�õ�properties�ļ���
	  my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
	  my_sql.addSubPara(document.all('ContNo').value);//ָ������Ĳ���
	  tSql = my_sql.getString();
      
		   easyQueryVer3Modal(tSql,MainPolGrid);
    }
    catch(ex) 
    {
       alert(ex);
    }
}


function initPolGrid()
{
    var iArray = new Array();

      try
      {
        iArray[0]=new Array();
        iArray[0][0]="���";                    //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1]="30px";                    //�п�
        iArray[0][2]=10;                        //�����ֵ
        iArray[0][3]=0;                         //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[1]=new Array();
        iArray[1][0]="���ִ���";
        iArray[1][1]="20px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="���ֺ���";
        iArray[2][1]="60px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="�����˿ͻ���";
        iArray[3][1]="30px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="����������";
        iArray[4][1]="30px";
        iArray[4][2]=100;
        iArray[4][3]=0;


        iArray[5]=new Array();
        iArray[5][0]="��������/����";
        iArray[5][1]="30px";
        iArray[5][2]=100;
        iArray[5][3]=0;

        iArray[6]=new Array();
        iArray[6][0]="���ѱ�׼";
        iArray[6][1]="30px";
        iArray[6][2]=100;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="��Ч����"
        iArray[7][1]="30px";
        iArray[7][2]=100;
        iArray[7][3]=0;

        iArray[8]=new Array();
        iArray[8][0]="���Ѷ�Ӧ��";
        iArray[8][1]="30px";
        iArray[8][2]=100;
        iArray[8][3]=0;

        iArray[9]=new Array();
        iArray[9][0]="��������";
        iArray[9][1]="30px";
        iArray[9][2]=100;
        iArray[9][3]=3;

        iArray[10]=new Array();
        iArray[10][0]="���屣������";
        iArray[10][1]="30px";
        iArray[10][2]=100;
        iArray[10][3]=3;

        PolGrid = new MulLineEnter( "fm" , "PolGrid" );
        //��Щ���Ա�����loadMulLineǰ
        PolGrid.mulLineCount = 0;  //��ʾ��������0Ϊ���ݾ������ݶ�̬��ʾ
        PolGrid.displayTitle = 1;
        PolGrid.canChk=0;       //1-��ʾcheckboxѡ�0-����ʾcheckboxѡ��
        PolGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
        PolGrid.hiddenSubtraction=1;
        PolGrid.loadMulLine(iArray);

        //��Щ����������loadMulLine����

      }
      catch(ex)
      {
        alert(ex);
      }
}


/*********************************************************************
 *  ��ѯ������Ϣ��д��MulLine
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showRiskInfo()
{

    var tContNo=document.all("ContNo").value;
    var tEdorAcceptNo=document.all('EdorAcceptNo').value;
    var tContNo=document.all('ContNo').value;
    var tEdorType=document.all('EdorType').value;
    var tInsuredNo=document.all('InsuredNo').value;
    var tPolNo=document.all('PolNo').value;

    /*
    var strSQL = " select p.RiskCode, p.PolNo, p.InsuredNo, p.InsuredName,"
                + " p.amnt, p.prem, p.CValiDate, p.PayToDate, p.ContNo, p.GrpContNo "
                + " from LCPol p,LPEdorItem e where p.ContNo='" + tContNo + "' "
                + " and p.ContNo = e.ContNo and e.EdorAcceptNo = '" + tEdorAcceptNo + "'"
                + " and e.EdorType = '" + tEdorType + "'"
                + " and e.InsuredNo = '" + tInsuredNo + "'"
                + " order by p.makedate, p.maketime";
    */
//     var strSQL = " select RiskCode, PolNo, InsuredNo, InsuredName,"
//                 + " amnt, prem, CValiDate, PayToDate, ContNo, GrpContNo "
//                 + " from LCPol where PolNo='" + tPolNo + "' "
//                 + " order by makedate, maketime";

      var strSQL = "";
      sql_id = "PEdorTypeENInputSql4";
	  my_sql = new SqlClass();
	  my_sql.setResourceName("bq.PEdorTypeENInputSql"); //ָ��ʹ�õ�properties�ļ���
	  my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
	  my_sql.addSubPara(tPolNo);//ָ������Ĳ���
	  strSQL = my_sql.getString();
    //alert (strSQL);
    turnPage.queryModal(strSQL, PolGrid);

}


function initPolInfoGrid() 
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
      iArray[1][1]="30px";         			//�п�
      //iArray[1][2]=10;          			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��������";					//����1
      iArray[2][1]="180px";            		//�п�
      //iArray[2][2]=50;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

			iArray[3]=new Array();
      iArray[3][0]="���ձ���";         			//����2
      iArray[3][1]="30px";            		//�п�
      //iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      
      iArray[4]=new Array();
      iArray[4][0]="PolNo";             //������
      iArray[4][1]="0px";            		//�п�
      //iArray[4][2]=50;            			//�����ֵ
      iArray[4][3]=3;
      
      iArray[5]=new Array();
      iArray[5][0]="RnewFlag";             
      iArray[5][1]="0px";            		//�п�
      //iArray[4][2]=50;            			//�����ֵ
      iArray[5][3]=3;
      
      iArray[6]=new Array();
      iArray[6][0]="ԭ��������״̬";            
      iArray[6][1]="30px";            		//�п�
      //iArray[4][2]=50;            			//�����ֵ
      iArray[6][3]=0;
      
      iArray[7]=new Array();
      iArray[7][0]="����������״̬���";           
      iArray[7][1]="30px";            		//�п�
      //iArray[4][2]=50;            			//�����ֵ
      iArray[7][3]=2;
      iArray[7][4]="RnewFlag";
      iArray[7][5]="7|8";
      iArray[7][6]="0|1";;
      
      iArray[8]=new Array();
      iArray[8][0]="����������״̬���";           
      iArray[8][1]="30px";            		//�п�
      iArray[8][2]=50;            			//�����ֵ
      iArray[8][3]=0;
      
	    iArray[9]=new Array();
      iArray[9][0]="�û���ǰ�Ƿ�ѡ������ı�־";
      iArray[9][1]="0px";
      iArray[9][2]=1;
      iArray[9][3]=3;     			

      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      //PolGrid.mulLineCount = 1;   
      PolGrid.displayTitle = 1;
      PolGrid.canSel=0;
      PolGrid.canChk=1;
      PolGrid.hiddenPlus = 1;
      PolGrid.hiddenSubtraction = 1;
      //PolGrid.tableWidth   ="500px";
      PolGrid.loadMulLine(iArray); 
      PolGrid.chkBoxEventFuncName="";
      //PolGrid.chkBoxEventFuncName="checkMainPol"; 
      
      //��Щ����������loadMulLine���� RnewFlag
      tSql ="" 
           //+"select c1.riskcode,"
       		 //+" (select riskname from lmrisk where riskcode = c1.riskcode),"
       		 //+" (select riskcode from lcpol where polno = c1.mainpolno),"
      	   //+" c1.polno,"
       		 //+" c1.rnewflag,"
      	   //+" decode(c1.rnewflag, '-2', '������', '-1', '�Զ�����', 'δ֪')"
   				 //+" from lcpol c1 "
  				 //+" where c1.riskcode in (select r1.riskcode "
           //+" from lmriskedoritem r1, lmrisk a1 "
           //+" where r1.edorcode = 'EN' "
           //+" and a1.rnewflag = 'Y' "
           //+" and r1.riskcode = a1.riskcode) "
    			 //+" and c1.polno = c1.mainpolno "
   				 //+" and c1.riskcode in "
    		   //+" (select b1.riskcode from lmriskapp b1 where b1.riskperiod <> 'L') "
   				 //+" and c1.contno = '"+document.all('ContNo').value+"'"
 					 //+" union "
 					 
// 					 +" select c.riskcode,"
//            +" (select riskname from lmrisk where riskcode= c.riskcode),"
//            +" (select riskcode from lcpol where polno = c.mainpolno),"
//            +" c.polno,"
//            +" c.rnewflag,"
//            +" CASE c.rnewflag WHEN -1 THEN '�Զ�����' ELSE '������' END,"
//            +" CASE WHEN ( SELECT p1.rnewflag FROM lppol p1 WHERE p1.polno = c.polno AND p1.edorno = '"+document.all("EdorNo").value+"') IS NOT NULL THEN ( SELECT p1.rnewflag FROM lppol p1 WHERE p1.polno = c.polno AND p1.edorno = '"+document.all("EdorNo").value+"' ) ELSE -2 END, "
//            +" CASE ( SELECT p2.rnewflag FROM lppol p2 WHERE p2.polno = c.polno AND p2.edorno =  '"+document.all("EdorNo").value+"') WHEN -1 THEN '�Զ�����' ELSE '������' END, "
//            +" (select 1 from lppol p3 where p3.polno = c.polno and p3.edorno = '"+document.all("EdorNo").value+"') "
//            +" from lcpol c where c.riskcode in ("
// 					 +" select r.riskcode"
//            +" from lmriskedoritem r, lmrisk a"
// 				   +" where r.edorcode = 'EN'"
// 				   +" and a.rnewflag = 'Y'"
// 			     +" and r.riskcode = a.riskcode) and "
// 			     +" c.riskcode in "
// 			     +" (select b.riskcode from lmriskapp b where b.riskperiod <> 'L') and "
// 			     +" c.appflag='1' and c.contno='"+document.all('ContNo').value+"' order by c.polno";   
			 //alert("Begin");
	  var tSql = "";
      sql_id = "PEdorTypeENInputSql5";
	  my_sql = new SqlClass();
	  my_sql.setResourceName("bq.PEdorTypeENInputSql"); //ָ��ʹ�õ�properties�ļ���
	  my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
	  my_sql.addSubPara(document.all("EdorNo").value);//ָ������Ĳ���
	  my_sql.addSubPara(document.all("EdorNo").value);//ָ������Ĳ���
	  my_sql.addSubPara(document.all("EdorNo").value);//ָ������Ĳ���
	  my_sql.addSubPara(document.all("EdorNo").value);//ָ������Ĳ���
	  my_sql.addSubPara(document.all('ContNo').value);//ָ������Ĳ���
	  tSql = my_sql.getString();
	  easyQueryVer3Modal(tSql,PolGrid);
		   //alert("OK!");
		   var rowNum=PolGrid.mulLineCount; //����
		   if (rowNum <= 0)
		   {
		   		alert("��ǰ�����޿ɲ��������ּ������Ϣ��");
		   		return;
		   }
		   for (i=0;i<rowNum;i++)
		   {
					 if (PolGrid.getRowColData(i,9)=="1")
					 {
						 PolGrid.checkBoxSel (i+1);
					}
		   }
    }
    catch(ex) 
    {
       alert(ex);
    }
}

</script>
