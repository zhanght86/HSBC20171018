<%
//PEdorTypeIAInit.jsp
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

function initInpBox()
{ 

  try
  {       	
    //document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
    //document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value; 
    document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;   
    //document.all('ContNo').value = top.opener.document.all('ContNoBak').value;
    document.all('EdorType').value = top.opener.document.all('EdorType').value;    
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
    //alert(document.all('EdorType').value);
    document.all('EdorTypeName').value = top.opener.document.all('EdorTypeName').value; 
    //document.all('InsuredNo').value = top.opener.document.all('CustomerNoBak').value;
    document.all('GrpContNo').value = top.opener.document.all('GrpContNo').value;   
    if(document.all('GrpContNo').value == null || document.all('GrpContNo').value == "")
    {
    	document.all('GrpContNo').value = top.opener.document.all('ContNoApp').value;
    }
  }
  catch(ex)
  {
    alert("��PEdorTypeIAInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}
                                        

function initForm()
{
  try
  {

    initInpBox();    
    initMoneyDetailGrid();
    queryMoneyDetail();
   
  }
  catch(re)
  {
    alert("PEdorTypeIAInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}


function initMoneyDetailGrid()
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
      iArray[1][0]="��/�˷�֪ͨ�����";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[1][1]="80px";         			//�п�
      iArray[1][2]=10;          			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��/�˷Ѳ�������";					//����1
      iArray[2][1]="60px";            		//�п�
      iArray[2][2]=50;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="���˺�ͬ����";         			//����2
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	

	 iArray[4]=new Array();
      iArray[4][0]="����";         			//����2
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=60;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="��/�˷�����";         		//����8
      iArray[5][1]="80px";            		//�п�
      iArray[5][2]=30;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="���ֱ���";     //����6
      iArray[6][1]="50px";            		//�п�
      iArray[6][2]=50;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="��/�˷ѽ��(Ԫ)";         		//����8
      iArray[7][1]="60px";            		//�п�
      iArray[7][2]=30;            			//�����ֵ
      iArray[7][3]=0;

      iArray[8]=new Array();
      iArray[8][0]="����";         		
      iArray[8][1]="40px";            		 
      iArray[8][2]=60;            			
      iArray[8][3]=2;              		
      iArray[8][4]="Currency";              	  
      iArray[8][9]="����|code:Currency";

      MoneyDetailGrid = new MulLineEnter( "fm" , "MoneyDetailGrid" ); 
      //��Щ���Ա�����loadMulLineǰ        
      MoneyDetailGrid.mulLineCount = 10;   
      MoneyDetailGrid.displayTitle = 1;
      MoneyDetailGrid.canSel=0;
      MoneyDetailGrid.hiddenPlus = 1; 
      MoneyDetailGrid.hiddenSubtraction = 1;
      MoneyDetailGrid.selBoxEventFuncName ="";
      MoneyDetailGrid.loadMulLine(iArray);  
      MoneyDetailGrid.detailInfo="������ʾ��ϸ��Ϣ";      
      MoneyDetailGrid.loadMulLine(iArray);        
      }
      catch(ex)
      {
        alert(ex);
      }
}

function returnParent(){
top.close();
}







</script>
