<%
//�������ƣ�NormPayGrpChooseInit.jsp
//�����ܣ�
//�������ڣ�2002-10-08 08:49:52
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  String CurrentDate = PubFun.getCurrentDate();
  String CurrentTime = PubFun.getCurrentTime();
%> 
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">

function initInpBox()
{ 
  try
  { 
    document.all('GrpPolNo').value = '';     
    document.all('GrpContNo').value = '';
    document.all('PaytoDate').value = '';
    document.all('SumDuePayMoney').value = '';  
    document.all('PayDate').value = '<%=CurrentDate%>';
    document.all('ManageFeeRate').value = '';
    document.all('PolNo').value=''; //���صĺ��룬���ڱ��漯�屣���������ύ       
  }
  catch(ex)
  {
    alert("��NormPayGrpChooseInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initSelBox()
{  
  try                 
  {
 
  }
  catch(ex)
  {
    alert("��NormPayGrpChooseInputInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox(); 
    initNormPayGrpChooseGrid(); 
  }
  catch(re)
  {
    alert("NormPayGrpChooseInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

 
//��ʼ�����
   var NormPayGrpChooseGrid;
function initNormPayGrpChooseGrid()
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
      iArray[1][0]="��������";    	                //����
      iArray[1][1]="140px";            		        //�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="���α���";    	                //����
      iArray[2][1]="60px";            		        //�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="���Ѽƻ����� ";         		//����
      iArray[3][1]="60px";            		        //�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="���Ѽƻ��������� ";         		//����
      iArray[4][1]="100px";            		        //�п�
      iArray[4][2]=60;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="����������";         		//����
      iArray[5][1]="80px";            		        //�п�
      iArray[5][2]=60;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="Ӧ�����";          		//����
      iArray[6][1]="100px";            		        //�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="ʵ�����";          		//����
      iArray[7][1]="100px";            		        //�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="������";          		//����
      iArray[8][1]="50px";            		        //�п�
      iArray[8][2]=50;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
              
      iArray[9]=new Array();
      iArray[9][0]="�������Ա�";         		//����
      iArray[9][1]="50px";            		        //�п�
      iArray[9][2]=60;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[10]=new Array();
      iArray[10][0]="��������";         		//����
      iArray[10][1]="80px";            		        //�п�
      iArray[10][2]=60;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[11]=new Array();
      iArray[11][0]="�ͻ��ڲ�����";          		//����
      iArray[11][1]="150px";            		        //�п�
      iArray[11][2]=150;            			//�����ֵ
      iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
      NormPayGrpChooseGrid = new MulLineEnter( "fm" , "NormPayGrpChooseGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      NormPayGrpChooseGrid.mulLineCount = 5;     
      NormPayGrpChooseGrid.displayTitle = 1;
      NormPayGrpChooseGrid.canChk = 1;
      NormPayGrpChooseGrid.hiddenPlus=1;   //����"+"��ť
      NormPayGrpChooseGrid.hiddenSubtraction=1; //����"-"��ť
      NormPayGrpChooseGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
  }

</script>