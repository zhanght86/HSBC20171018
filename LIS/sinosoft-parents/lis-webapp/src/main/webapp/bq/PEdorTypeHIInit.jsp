<%
//PEdorTypeHIInit.jsp
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
//����ʱ��ѯ
var str_sql = "",sql_id = "", my_sql = "";
function initInpBox()
{ 

  try
  { 
    Edorquery(); 
    document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;   
    document.all('ContNo').value = top.opener.document.all('ContNo').value;
    document.all('CustomerNo').value = top.opener.document.all('InsuredNo').value;
    document.all('EdorType').value = top.opener.document.all('EdorType').value;
    document.all('EdorAppDate').value = top.opener.document.all('EdorItemAppDate').value;
    document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;  
     
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
  	showOneCodeName('EdorCode','EdorType','EdorTypeName');
  }
  catch(ex)
  {
    alert("��PEdorTypeHIInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initSelBox()
{  
  try                 
  {
  }
  catch(ex)
  {
    alert("��PEdorTypeHIInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    QueryCustomerInfo();
    initContGrid();
    initImpartGrid();
    initNewImpartGrid();
    QueryImpartInfo();
    QueryNewImpartInfo();
    initInpRole();
  }
  catch(re)
  {
    alert("PEdorTypeHIInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}


function initImpartGrid() 
{                               
    var iArray = new Array();

    try {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��֪���";         		//����
      iArray[1][1]="60px";            		//�п�
      iArray[1][2]=60;            			//�����ֵ
      iArray[1][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��֪����";         		//����
      iArray[2][1]="60px";            		//�п�
      iArray[2][2]=60;            			//�����ֵ
      iArray[2][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[3]=new Array();
      iArray[3][0]="��֪����";         		//����
      iArray[3][1]="250px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      //iArray[3][4]="ImpartContent";

      iArray[4]=new Array();
      iArray[4][0]="��д����";         		//����
      iArray[4][1]="150px";            		//�п�
      iArray[4][2]=150;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      ImpartGrid = new MulLineEnter( "fm" , "ImpartGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      ImpartGrid.mulLineCount = 1;   
      ImpartGrid.displayTitle = 1;
      ImpartGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
	    ImpartGrid.hiddenSubtraction=1;
      ImpartGrid.loadMulLine(iArray);  
      
    }
    catch(ex) {
      alert(ex);
    }
}

function initNewImpartGrid() 
{                               
    var iArray = new Array();

    try {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��֪���";         		//����
      iArray[1][1]="60px";            		//�п�
      iArray[1][2]=60;            			//�����ֵ
      iArray[1][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[1][4]="impartver";
      iArray[1][9]="��֪���|len<=3";
      iArray[1][15]="1";
      iArray[1][16]="#1# and code in (#A05#,#A06#)";

      iArray[2]=new Array();
      iArray[2][0]="��֪����";         		//����
      iArray[2][1]="60px";            		//�п�
      iArray[2][2]=60;            			//�����ֵ
      iArray[2][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[2][4]="ImpartCode";
      iArray[2][5]="2|3|4";
      iArray[2][6]="0|1|2";
      iArray[2][9]="��֪����|len<=4";
      iArray[2][15]="ImpartVer";
      //iArray[2][16]="#02#";
      iArray[2][17]="1";
      iArray[2][18]=700;

      iArray[3]=new Array();
      iArray[3][0]="��֪����";         		//����
      iArray[3][1]="250px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      //iArray[3][4]="ImpartContent";

      iArray[4]=new Array();
      iArray[4][0]="��д����";         		//����
      iArray[4][1]="150px";            		//�п�
      iArray[4][2]=150;            			//�����ֵ
      iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      //iArray[4][4]="ImpartParamModle";
      iArray[4][9]="��д����|len<=200";

      NewImpartGrid = new MulLineEnter( "fm" , "NewImpartGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      NewImpartGrid.mulLineCount = 1;   
      NewImpartGrid.displayTitle = 1;
      NewImpartGrid.loadMulLine(iArray);  
      
    }
    catch(ex) {
      alert(ex);
    }
}


function QueryNewImpartInfo()
{
// 	var tSql = "SELECT distinct ImpartVer,ImpartCode,ImpartContent,ImpartParamModle"
// 					+ " FROM LPCustomerImpart a"
// 					+ " WHERE a.EdorType='HI' and exists(select 'X' from LPEdorItem where EdorNo=a.EdorNo and EdorAcceptNo='" + document.all('EdorAcceptNo').value + "')";
	sql_id = "PEdorTypeHIInputSql8";
	my_sql = new SqlClass();
	my_sql.setResourceName("bq.PEdorTypeHIInputSql"); //ָ��ʹ�õ�properties�ļ���
	my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
	my_sql.addSubPara(document.all('EdorAcceptNo').value);//ָ������Ĳ���
	str_sql = my_sql.getString();
	easyQueryVer3Modal(str_sql,NewImpartGrid);
}


function QueryImpartInfo()
{
// 	var tSql = "SELECT distinct ImpartVer,ImpartCode,ImpartContent,ImpartParamModle"
// 					+ " FROM LCCustomerImpart a"
// 					+ " WHERE a.Customerno='" + document.all('CustomerNo').value + "' and contno='"+document.all('ContNo').value+"'";
	sql_id = "PEdorTypeHIInputSql9";
	my_sql = new SqlClass();
	my_sql.setResourceName("bq.PEdorTypeHIInputSql"); //ָ��ʹ�õ�properties�ļ���
	my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
	my_sql.addSubPara(document.all('CustomerNo').value);//ָ������Ĳ���
	my_sql.addSubPara(document.all('ContNo').value);//ָ������Ĳ���
	str_sql = my_sql.getString();
	
	easyQueryVer3Modal(str_sql,ImpartGrid);
}

function QueryCustomerInfo() 
{
// 		var tSql = "SELECT"
// 						     + " Name as r0,"
// 						     + " Sex as r1,"
// 						     + " Marriage as r2,"
// 						     + " Birthday as r3,"
// 						     + " IDType as r6,"
// 						     + " IDNo as r7 "
// 						  + " FROM LDPerson a"
// 						  + " WHERE a.CustomerNo='" + document.all('CustomerNo').value + "'";
		sql_id = "PEdorTypeHIInputSql10";
		my_sql = new SqlClass();
		my_sql.setResourceName("bq.PEdorTypeHIInputSql"); //ָ��ʹ�õ�properties�ļ���
		my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
		my_sql.addSubPara(document.all('CustomerNo').value);//ָ������Ĳ���
		str_sql = my_sql.getString();
    var arrResult = easyExecSql(str_sql,1,0);
    if (arrResult==null)
    {
    	alert("��ѯ�ͻ���Ϣʧ�ܣ��뷵�أ�");
    	return;
    }
    //��ʾͶ������Ϣ
		displayCustomerBasicFC(arrResult);
}

//��ʾͶ���˵���ϸ��Ϣ����C����
function displayCustomerBasicFC(arrResult)
{
		try{document.all('Name').value= arrResult[0][0];}catch(ex){};
		try{document.all('Sex').value= arrResult[0][1];}catch(ex){};
		try{document.all('Marriage').value= arrResult[0][2];}catch(ex){};
		try{document.all('Birthday').value= arrResult[0][3];}catch(ex){};
		try{document.all('IDType').value= arrResult[0][4];}catch(ex){};
		try{document.all('IDNo').value= arrResult[0][5];}catch(ex){};

		//�ɱ���ȡ������
		showOneCodeName('sex','Sex','SexName');
		showOneCodeName('marriage','Marriage','MarriageName');
		showOneCodeName('idtype','IDType','IDTypeName');
		
		//alert("displayAppntBasicFC Finish!");
}

function initContGrid()
{                               
    var iArray = new Array();
      
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=100;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[1]=new Array();
      iArray[1][0]="���˱�����";
      iArray[1][1]="100px";
      //iArray[1][2]=100;
      iArray[1][3]=0;
      
      iArray[2]=new Array();
      iArray[2][0]="�ͻ���ɫ";
      iArray[2][1]="70px";
      //iArray[2][2]=100;
      iArray[2][3]=0;      
      
      iArray[3]=new Array();
      iArray[3][0]="���������������";
      iArray[3][1]="350px";
      //iArray[3][2]=20;
      iArray[3][3]=0;
      
	    ContGrid = new MulLineEnter( "fm" , "ContGrid" ); 
	    ContGrid.displayTitle = 1;
	    ContGrid.canChk=0;       
	    ContGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
	    ContGrid.hiddenSubtraction=1;
	    ContGrid.loadMulLine(iArray);
			selectContInfo();
    }
    catch(ex)
    {
      alert(ex);
    }
}

</script>