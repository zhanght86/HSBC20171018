<%
//�������ƣ�ProposalQueryInit.jsp
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
//���ذ�ť��ʼ��
var str = "";
function initDisplayButton()
{

}
// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 

  try
  {                                   
	document.all('Reason').value='';
	document.all('ContNo').value=ContNo;
	document.all('PrtNo').value=prtNo;
	/*
	if(document.all('PayLocation').value!="0"||document.all('PayLocation').value!="8")
	{
		document.all('BankCode').readonly;
	}
	*/
  }
  catch(ex)
  {
    alert("��PolModifyInputInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

// ������ĳ�ʼ��
function initSelBox()
{  
  try                 
  {

  }
  catch(ex)
  {
    alert("��ProposalQueryInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
  	initInpBox();
  	initPolGrid();
  	//��������Ϣ
  	initLCBnfGrid();
  	initImpartGrid();
  	//��ѯ���ݿ�,��ʾ��ͬ����Ϣ
  	queryAllContInfo();
  	//��ʼ�������޸ĵĿؼ�Ϊֻ������
  	
  	
  }
  catch(re)
  {
    alert("ProposalQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}


// ��������Ϣ�б�ĳ�ʼ��
function initInsuredGrid()
  {                               
    var iArray = new Array();      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="40px"; 	           		//�п�
      iArray[0][2]=1;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�ͻ�����";          		//����
      iArray[1][1]="80px";      	      		//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=0;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�

      iArray[2]=new Array();
      iArray[2][0]="����";         			//����
      iArray[2][1]="60px";            			//�п�
      iArray[2][2]=20;            			//�����ֵ
      iArray[2][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="֤������";      	   		//����
      iArray[3][1]="40px";            			//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=0;              	//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[3][4]="idno"; 

      iArray[4]=new Array();
      iArray[4][0]="֤������";      	   		//����
      iArray[4][1]="40px";            			//�п�
      iArray[4][2]=20;            			//�����ֵ
      iArray[4][3]=1;              	//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="סַ";      	   		//����
      iArray[5][1]="80px";            			//�п�
      iArray[5][2]=20;            			//�����ֵ
      iArray[5][3]=1; 
      
      iArray[6]=new Array();
      iArray[6][0]="�ʱ�";      	   		//����
      iArray[6][1]="80px";            			//�п�
      iArray[6][2]=20;            			//�����ֵ
      iArray[6][3]=1; 
      
      iArray[7]=new Array();
      iArray[7][0]="��������";      	   		//����
      iArray[7][1]="100px";            			//�п�
      iArray[7][2]=20;            			//�����ֵ
      iArray[7][3]=1; 
      
      iArray[8]=new Array();
      iArray[8][0]="��ϵ�绰��1��";      	   		//����
      iArray[8][1]="100px";            			//�п�
      iArray[8][2]=20;            			//�����ֵ
      iArray[8][3]=1; 
      
      iArray[9]=new Array();
      iArray[9][0]="��ϵ�绰��2��";      	   		//����
      iArray[9][1]="100px";            			//�п�
      iArray[9][2]=20;            			//�����ֵ
      iArray[9][3]=1; 
      
      iArray[10]=new Array();
      iArray[10][0]="������λ";      	   		//����
      iArray[10][1]="100px";            			//�п�
      iArray[10][2]=20;            			//�����ֵ
      iArray[10][3]=1; 
      
      iArray[11]=new Array();
      iArray[11][0]="�ͻ��ڲ���";      	   		//����
      iArray[11][1]="80px";            			//�п�
      iArray[11][2]=20;            			//�����ֵ
      iArray[11][3]=3; 

      InsuredGrid = new MulLineEnter( "fm" , "InsuredGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      InsuredGrid.mulLineCount = 0;   
      InsuredGrid.displayTitle = 1;
      InsuredGrid.canSel =1;
      InsuredGrid. selBoxEventFuncName ="getInsuredDetail" ;     //���RadioBoxʱ��Ӧ��JS����
      InsuredGrid. hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      InsuredGrid. hiddenSubtraction=1;
      InsuredGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}

//������������Ϣ�б��ʼ��
function initPolGrid()
{
    var iArray = new Array();
    try 
    {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		        //�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="���ֱ�������";         		//����
      iArray[1][1]="0px";            		        //�п�
      iArray[1][2]=60;            			//�����ֵ
      iArray[1][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[2]=new Array();
      iArray[2][0]="���ֱ���";         		//����
      iArray[2][1]="40px";            		        //�п�
      iArray[2][2]=60;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      //iArray[2][4]="RiskCode";              			//�Ƿ���������,1��ʾ����0��ʾ������           
      
      iArray[3]=new Array();
      iArray[3][0]="��������";         		//����
      iArray[3][1]="150px";            		        //�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[4]=new Array();
      iArray[4][0]="����(Ԫ)";         		//����
      iArray[4][1]="40px";            		        //�п�
      iArray[4][2]=60;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[5]=new Array();
      iArray[5][0]="����(Ԫ)";         		//����
      iArray[5][1]="40px";            		        //�п�
      iArray[5][2]=60;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      PolGrid.mulLineCount = 0;   
      PolGrid.displayTitle = 1;
     // PolGrid.canSel =1;
      //PolGrid. selBoxEventFuncName ="getPolDetail";
      PolGrid. hiddenPlus=1;
      PolGrid. hiddenSubtraction=1;
      PolGrid.loadMulLine(iArray);  
    }
    catch(ex) {
      alert(ex);
    }
}
// ��������Ϣ�б�ĳ�ʼ��
function initLCBnfGrid() {                               
  var iArray = new Array();

  try {
    iArray[0]=new Array();
    iArray[0][0]="���"; 			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";		//�п�
    iArray[0][2]=10;			//�����ֵ
    iArray[0][3]=0;			//�Ƿ���������,1��ʾ����0��ʾ������
  
    iArray[1]=new Array();
    iArray[1][0]="���������"; 		//����
    iArray[1][1]="60px";		//�п�
    iArray[1][2]=60;			//�����ֵ
    iArray[1][3]=2;			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[1][4]="bnftype";
   
    iArray[2]=new Array();
    iArray[2][0]="����"; 	//����
    iArray[2][1]="80px";		//�п�
    iArray[2][2]=80;			//�����ֵ
    iArray[2][3]=1;			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[2][9]="����|len<=20"; //У��
  
    iArray[3]=new Array();
    iArray[3][0]="֤������"; 		//����
    iArray[3][1]="40px";		//�п�
    iArray[3][2]=40;			//�����ֵ
    iArray[3][3]=2;			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[3][4]="IDType";
    iArray[3][9]="֤������|code:IDType";
  
    iArray[4]=new Array();
    iArray[4][0]="֤������"; 		//����
    iArray[4][1]="140px";		//�п�
    iArray[4][2]=100;			//�����ֵ
    iArray[4][3]=1;			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[4][9]="֤������|len<=20";

    iArray[5]=new Array();
    iArray[5][0]="�뱻���˹�ϵ"; 	//����
    iArray[5][1]="60px";		//�п�
    iArray[5][2]=60;			//�����ֵ
    iArray[5][3]=2;			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[5][4]="Relation";
    iArray[5][9]="�뱻���˹�ϵ|code:Relation";
  
    iArray[6]=new Array();
    iArray[6][0]="�������"; 		//����
    iArray[6][1]="40px";		//�п�
    iArray[6][2]=40;			//�����ֵ
    iArray[6][3]=1;			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[6][9]="�������|num&len<=10";
  
    iArray[7]=new Array();
    iArray[7][0]="����˳��"; 		//����
    iArray[7][1]="40px";		//�п�
    iArray[7][2]=40;			//�����ֵ
    iArray[7][3]=2;			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[7][4]="OccupationType";
    iArray[7][9]="����˳��|code:OccupationType";
  
    iArray[8]=new Array();
    iArray[8][0]="סַ������ţ�"; 		//����
    iArray[8][1]="160px";		//�п�
    iArray[8][2]=100;			//�����ֵ
    iArray[8][3]=1;			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[8][9]="סַ|len<=80";
  
    iArray[9]=new Array();
    iArray[9][0]="����"; 		//����
    iArray[9][1]="30px";		//�п�
    iArray[9][2]=30;			//�����ֵ
    iArray[9][3]=2;			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[9][10]="customertypea";
    iArray[9][11]="0|^0|Ͷ����|^1|������";
    
    iArray[10]=new Array();
    iArray[10][0]="����Ա"; 		//����
    iArray[10][1]="0px";		//�п�
    iArray[10][2]=60;			//�����ֵ
    iArray[10][3]=0;			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[11]=new Array();
    iArray[11][0]="¼������"; 		//����
    iArray[11][1]="0px";		//�п�
    iArray[11][2]=60;			//�����ֵ
    iArray[11][3]=0;			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[12]=new Array();
    iArray[12][0]="¼��ʱ��"; 		//����
    iArray[12][1]="0px";		//�п�
    iArray[12][2]=60;			//�����ֵ
    iArray[12][3]=0;			//�Ƿ���������,1��ʾ����0��ʾ������
  
    LCBnfGrid = new MulLineEnter( "fm" , "LCBnfGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    LCBnfGrid.mulLineCount = 1; 
    LCBnfGrid.displayTitle = 1;
    //LCBnfGrid.hiddenPlus = 1;
    //LCBnfGrid.hiddenSubtraction = 1;
    LCBnfGrid.loadMulLine(iArray);    
  
    //��Щ����������loadMulLine����
    //BnfGrid.setRowColData(0,8,"1");
    //BnfGrid.setRowColData(0,9,"1");
  } catch(ex) {
    alert(ex);
  }
}

// ��֪��Ϣ�б�ĳ�ʼ��
function initImpartGrid() {                               
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
      iArray[1][4]="ImpartVer1";                     //�°�Ͷ������Ӧ��֪
      iArray[1][5]="1";
      iArray[1][6]="0";
      iArray[1][9]="��֪���|len<=5";
      iArray[1][18]=300;

      iArray[2]=new Array();             
      iArray[2][0]="��֪����";         	
      iArray[2][1]="60px";            	
      iArray[2][2]=60;            		
      iArray[2][3]=2;              		
      iArray[2][4]="ImpartCode";         
      iArray[2][5]="2|3";                
      iArray[2][6]="0|1";                
      iArray[2][9]="��֪����|len<=4";    
      iArray[2][15]="ImpartVer";     //��ѯ���ݿ��ж�Ӧ����
      iArray[2][17]="1";             //������6�е�ֵ��������Ĳ�ѯ����Ϊ ImpartVer ='001'  

      iArray[3]=new Array();
      iArray[3][0]="��֪����";         		//����
      iArray[3][1]="250px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="��д����";         		//����
      iArray[4][1]="150px";            		//�п�
      iArray[4][2]=150;            			//�����ֵ
      iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[4][9]="��д����|len<=200";

      iArray[5]=new Array();
      iArray[5][0]="��֪�ͻ�����";         		//����
      iArray[5][1]="60px";            		//�п�
      iArray[5][2]=90;            			//�����ֵ
      iArray[5][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[5][10]="CustomerNoType";
      iArray[5][11]="0|^0|Ͷ����|^1|������|^2|ҵ��Ա";      
      
      iArray[6]=new Array();                           //������
      iArray[6][0]="�ͻ��ڲ�����";         		//����
      iArray[6][1]="60px";            		//�п�
      iArray[6][2]=60;            			//�����ֵ
      iArray[6][3]=2;  
      iArray[6][10]="CustomerToInsured";
      iArray[6][11]="0|^1|������|^2|��2������|^3|��3������";   

      ImpartGrid = new MulLineEnter( "fm" , "ImpartGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      ImpartGrid.mulLineCount = 3;   
      ImpartGrid.displayTitle = 1;
      ImpartGrid.hiddenPlus=1;
      ImpartGrid.hiddenSubtraction=1;
      ImpartGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //ImpartGrid.setRowColData(1,1,"asdf");
    }
    catch(ex) {
      alert(ex);
    }
}


</script>
