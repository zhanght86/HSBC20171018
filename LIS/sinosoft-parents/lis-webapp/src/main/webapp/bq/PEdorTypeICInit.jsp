<%
//PEdorTypeICInit.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>

<script language="JavaScript">

var str_sql = "",sql_id = "", my_sql = "";
function initForm()
{
    try
    {
        initInpBox();
	      initPolNewGrid();
	      initInpPCustomerInfo();
        initInpCustomerInfo();
        queryRelationPol();
    }
    catch (ex)
    {
        alert("PEdorTypeICInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}

function initInpBox()
{

  try
  {
    Edorquery();
	  document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
	  document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
    document.all('EdorType').value = top.opener.document.all('EdorType').value;
    document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
    document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;
    document.all('ContNo').value = top.opener.document.all('ContNo').value;
    getInsuredNo();
    showOneCodeName('EdorType','EdorType','EdorTypeName');
  }
  catch(ex)
  {
    alert("��PEdorTypeCMInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}

function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    alert("��PEdorTypeICInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}


function queryRelationPol()
{
    var iArray;
//     var strSQL = "select a.polno,a.riskcode,  a.CValidate, " +
//                 "a.Prem, a.Amnt,a.paytodate,case a.payintv when 0 then '����' when 12 then '�꽻' when 6 then '���꽻' when 3 then '����' when 1 then '�½�' else '�����ڽ�' end,a.payenddate,a.currency " +       
//                 "from lcpol a where contno='" +document.all('ContNo').value+"' and appflag in ('1') and cvalidate <= '"+document.all('EdorValiDate').value +"' and enddate > '"+document.all('EdorValiDate').value +"'";
                sql_id = "PEdorTypeICInputSql5";
                my_sql = new SqlClass();
                my_sql.setResourceName("bq.PEdorTypeICInputSql"); //ָ��ʹ�õ�properties�ļ���
                my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
                my_sql.addSubPara(document.all('ContNo').value);//ָ������Ĳ���
                my_sql.addSubPara(document.all('EdorValiDate').value);//ָ������Ĳ���
                my_sql.addSubPara(document.all('EdorValiDate').value);//ָ������Ĳ���
                str_sql = my_sql.getString();

    turnPage.strQueryResult  = easyExecSql(str_sql);
    var brrResult = easyExecSql(str_sql);
    if (brrResult)
    {
    displayMultiline(brrResult,PolNewGrid);
    }else
    {
    	alert("��ѯ��ؿͻ���ر���ʧ�ܻ��߱��ͻ���û�б������볷��������������ѡ��ͻ���");
    	return false;
    }
}

function getInsuredNo()
{
	    var tRole="";
	    var tAppntNo="";
	    var tInsuredNo="";
// 		  var strSQL =  " select remark,appntno,insuredno from lpconttempinfo where state='0' and contno='"+document.all('ContNo').value+"' and edorno='"+getLastEdorNo()+"'";
		  sql_id = "PEdorTypeICInputSql6";
          my_sql = new SqlClass();
          my_sql.setResourceName("bq.PEdorTypeICInputSql"); //ָ��ʹ�õ�properties�ļ���
          my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
          my_sql.addSubPara(document.all('ContNo').value);//ָ������Ĳ���
          my_sql.addSubPara(getLastEdorNo());//ָ������Ĳ���
          str_sql = my_sql.getString();
		  var brr = easyExecSql(str_sql);
			if ( brr )
			{
				//alert("�Ѿ����뱣���");
				brr[0][0]==null||brr[0][0]=='null'?'0':tRole= brr[0][0];
				brr[0][1]==null||brr[0][1]=='null'?'0':tAppntNo= brr[0][1];
				brr[0][2]==null||brr[0][2]=='null'?'0':tInsuredNo= brr[0][2];				
			}
			else
			{
				alert("��ȡ�ͻ����ϱ����ʱ������ʧ��");
				return "";
			}	 
			//Ͷ����   
	    if(tRole=='0' || tRole==0)
	    {
	     	document.all('CustomerNo').value=tAppntNo;	    	
	    }else
	    {	    			
		    document.all('CustomerNo').value=tInsuredNo;	     			
	    }

	    

	
	}
function initPolNewGrid()
{
    var iArray = new Array();

      try
      {
        iArray[0]=new Array();
        iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1]="30px";            		//�п�
        iArray[0][2]=10;            			//�����ֵ
        iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

        iArray[1]=new Array();
        iArray[1][0]="��������";
        iArray[1][1]="100px";
        iArray[1][2]=60;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="���ִ���";
        iArray[2][1]="60px";
        iArray[2][2]=60;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="��Ч����";
        iArray[3][1]="60px";
        iArray[3][2]=80;
        iArray[3][3]=8;

        iArray[4]=new Array();
        iArray[4][0]="���ѱ�׼";
        iArray[4][1]="60px";
        iArray[4][2]=80;
        iArray[4][3]=7;
        iArray[4][23]="0";

        iArray[5]=new Array();
        iArray[5][0]="��������";
        iArray[5][1]="60px";
        iArray[5][2]=80;
        iArray[5][3]=7;
        iArray[5][23]="0";

        iArray[6]=new Array();
        iArray[6][0]="��������";
        iArray[6][1]="50px";
        iArray[6][2]=80;
        iArray[6][3]=8;

        iArray[7]=new Array();
        iArray[7][0]="�ɷѼ��";
        iArray[7][1]="60px";
        iArray[7][2]=80;
        iArray[7][3]=0;
        
        iArray[8]=new Array();
        iArray[8][0]="�ɷ��ڼ�";
        iArray[8][1]="60px";
        iArray[8][2]=80;
        iArray[8][3]=8;
        
        iArray[9]=new Array();
				iArray[9][0]="����";
				iArray[9][1]="60px";
				iArray[9][2]=100;
				iArray[9][3]=2;
				iArray[9][4]="currency";
        
      PolNewGrid = new MulLineEnter( "fm" , "PolNewGrid" );
      //��Щ���Ա�����loadMulLineǰ
      PolNewGrid.mulLineCount = 1;
      PolNewGrid.displayTitle = 1;
      PolNewGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      PolNewGrid.hiddenSubtraction=1;
      PolNewGrid.loadMulLine(iArray);
      PolNewGrid.selBoxEventFuncName ="queryRelationPol" ;
      //��Щ����������loadMulLine����

      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>