<%
//�������ƣ�GEdorTypeNIInput.jsp
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

function initGrpEdor()
{
	document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
	document.all('GrpContNo').value = top.opener.document.all('OtherNo').value;
	document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
	document.all('EdorType').value = top.opener.document.all('EdorType').value;
	document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;
	//alert("---------"+document.all('EdorValiDate').value);
	document.all('EdorTypeCal').value = top.opener.document.all('EdorTypeCal').value;
	document.all('ContNo').value = "";
}
// �����ĳ�ʼ��������¼���֣�
function initInpBox() { 
  try 
  {                            
  
  } 
  catch(ex) 
  {
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
    alert("��GEdorTypeNIInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        


//�����������б�
function initLPInsuredGrid()
{
    var iArray = new Array();
      
      try
      {
    iArray[0]=new Array();
    iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";         			//�п�
    iArray[0][2]=10;          				//�����ֵ
    iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="���˱�����";    				//����1
    iArray[1][1]="150px";            		//�п�
    iArray[1][2]=100;            			//�����ֵ
    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="�ͻ���";         			//����2
    iArray[2][1]="80px";            		//�п�
    iArray[2][2]=100;            			//�����ֵ
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="����";         			//����8
    iArray[3][1]="80px";            		//�п�
    iArray[3][2]=100;            			//�����ֵ
    iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="�Ա�";         			//����5
    iArray[4][1]="50px";            		//�п�
    iArray[4][2]=100;            			//�����ֵ
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������

    iArray[5]=new Array();
    iArray[5][0]="��������";         		//����6
    iArray[5][1]="80px";            		//�п�
    iArray[5][2]=100;            			//�����ֵ
    iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[6]=new Array();
    iArray[6][0]="֤������";         		//����6
    iArray[6][1]="80px";            		//�п�
    iArray[6][2]=100;            			//�����ֵ
    iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[7]=new Array();
    iArray[7][0]="֤������";         		//����6
    iArray[7][1]="150px";            		//�п�
    iArray[7][2]=100;            			//�����ֵ
    iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      

      LPInsuredGrid = new MulLineEnter( "fm" , "LPInsuredGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      LPInsuredGrid.mulLineCount = 0;   
      LPInsuredGrid.displayTitle = 1;
	    LPInsuredGrid.canSel = 1;
      LPInsuredGrid.hiddenPlus = 1;
      LPInsuredGrid.hiddenSubtraction = 1;
      LPInsuredGrid.selBoxEventFuncName ="onInsuredGridSelected";
      LPInsuredGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����

      }
      catch(ex)
      {
        alert(ex);
      }
}

function initEYGrid()
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
      iArray[1][0]="���屣����";         			//����
      iArray[1][1]="80px";            		//�п�
      iArray[1][2]=80;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="ԭ���ʽ��";         			//����
      iArray[2][1]="80px";            		//�п�
      iArray[2][2]=80;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="����ʹ�ù��ʽ��";         			//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=80;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

		iArray[4]=new Array();
		iArray[4][0]="����";         		
		iArray[4][1]="50px";            		 
		iArray[4][2]=60;            			
		iArray[4][3]=2;              		
		iArray[4][4]="Currency";              	  
		iArray[4][9]="����|code:Currency";
      
      EYGrid = new MulLineEnter( "fm" , "EYGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      EYGrid.mulLineCount = 1;   
      EYGrid.displayTitle = 1;
	    EYGrid.canSel = 0;
      EYGrid.hiddenPlus = 1;
      EYGrid.hiddenSubtraction = 1;
      EYGrid.selBoxEventFuncName ="";
      EYGrid.loadMulLine(iArray);
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initInsurAccGrid()
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
      iArray[1][0]="���屣����";         			//����
      iArray[1][1]="80px";            		//�п�
      iArray[1][2]=80;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�����ʻ����";         			//����
      iArray[2][1]="80px";            		//�п�
      iArray[2][2]=80;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="���α�ȫʹ�ý��";         			//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=80;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

		iArray[4]=new Array();
		iArray[4][0]="����";         		
		iArray[4][1]="50px";            		 
		iArray[4][2]=60;            			
		iArray[4][3]=2;              		
		iArray[4][4]="Currency";              	  
		iArray[4][9]="����|code:Currency";
      
      InsurAccGrid = new MulLineEnter( "fm" , "InsurAccGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      InsurAccGrid.mulLineCount = 1;   
      InsurAccGrid.displayTitle = 1;
	    InsurAccGrid.canSel = 0;
      InsurAccGrid.hiddenPlus = 1;
      InsurAccGrid.hiddenSubtraction = 1;
      InsurAccGrid.selBoxEventFuncName ="";
      InsurAccGrid.loadMulLine(iArray);
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initBlogGrid()
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
      iArray[1][0]="�����ļ���";         			//����
      iArray[1][1]="80px";            		//�п�
      iArray[1][2]=80;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��ͬ���";         			//����
      iArray[2][1]="30px";            		//�п�
      iArray[2][2]=80;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="����������";         			//����
      iArray[3][1]="60px";            		//�п�
      iArray[3][2]=80;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="������Ϣ";         			//����
      iArray[4][1]="120px";            		//�п�
      iArray[4][2]=80;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="��������";         			//����
      iArray[5][1]="50px";            		//�п�
      iArray[5][2]=80;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="����ʱ��";         			//����
      iArray[6][1]="50px";            		//�п�
      iArray[6][2]=80;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      
      BlogGrid = new MulLineEnter( "fm" , "BlogGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      BlogGrid.mulLineCount = 0;   
      BlogGrid.displayTitle = 1;
	    BlogGrid.canSel = 0;
      BlogGrid.hiddenPlus = 1;
      BlogGrid.hiddenSubtraction = 1;
      BlogGrid.selBoxEventFuncName ="";
      BlogGrid.loadMulLine(iArray);
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initLPPolGrid()
{
    var iArray = new Array();
      
      try
      {
    iArray[0]=new Array();
    iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";         			//�п�
    iArray[0][2]=10;          				//�����ֵ
    iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="���˱�����";    				//����1
    iArray[1][1]="100px";            		//�п�
    iArray[1][2]=100;            			//�����ֵ
    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="�ͻ���";         			//����2
    iArray[2][1]="80px";            		//�п�
    iArray[2][2]=100;            			//�����ֵ
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="����";         			//����8
    iArray[3][1]="80px";            		//�п�
    iArray[3][2]=100;            			//�����ֵ
    iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="����";         			//����5
    iArray[4][1]="50px";            		//�п�
    iArray[4][2]=100;            			//�����ֵ
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������

    iArray[5]=new Array();
    iArray[5][0]="����";         		//����6
    iArray[5][1]="60px";            		//�п�
    iArray[5][2]=100;            			//�����ֵ
    iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������   
    
    iArray[6]=new Array();
    iArray[6][0]="����";         		//����6
    iArray[6][1]="60px";            		//�п�
    iArray[6][2]=100;            			//�����ֵ
    iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������   
    
    iArray[7]=new Array();
    iArray[7][0]="���ֱ�����";         		//����6
    iArray[7][1]="100px";            		//�п�
    iArray[7][2]=100;            			//�����ֵ
    iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������         

	iArray[8]=new Array();
	iArray[8][0]="����";         		
	iArray[8][1]="50px";            		 
	iArray[8][2]=60;            			
	iArray[8][3]=2;              		
	iArray[8][4]="Currency";              	  
	iArray[8][9]="����|code:Currency";

      LPPolGrid = new MulLineEnter( "fm" , "LPPolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      LPPolGrid.mulLineCount = 1;   
      LPPolGrid.displayTitle = 1;
	    LPPolGrid.canSel = 0;
      LPPolGrid.hiddenPlus = 1;
      LPPolGrid.hiddenSubtraction = 1;
      LPPolGrid.selBoxEventFuncName ="";
      LPPolGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����

      }
      catch(ex)
      {
        alert(ex);
      }
}
function initForm() 
{
	initGrpEdor();
	initLPInsuredGrid();
	initLPPolGrid();
	initQuery();
	QueryEdorInfo();
	//QueryEdorMoney();		
}

</script>
