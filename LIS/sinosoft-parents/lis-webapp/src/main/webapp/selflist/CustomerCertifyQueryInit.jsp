<%@page import="com.sinosoft.lis.pubfun.*"%>

<%
//�������ƣ�CustomerCertifyQuery.jsp
//�����ܣ��ͻ���Ϣ��ѯ
//�������ڣ�2008-03-17
//������  ��zz
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
	String strCurDate = PubFun.getCurrentDate();
%>                            

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
                                      

function initForm()
{
  try
  {
	    document.all('CertifyNo').value = '';
        document.all('Name').value = '';
        document.all('Birthday').value = '';
        document.all('CustomerNo').value = '';
        document.all('IDType').value = '';
        document.all('IDNo').value = '';
        document.all('Sex').value = '';
        
        
		initCardInfo();
  }
  catch(re)
  {
    alert("CustomerCertifyQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initCardInfo()
{                               
	var iArray = new Array();
      
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="���";         			  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[0][1]="40px";            		//�п�
		iArray[0][2]=10;            			  //�����ֵ
		iArray[0][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[1]=new Array();
		iArray[1][0]="����";         		  //����
		iArray[1][1]="165px";            		//�п�
		iArray[1][2]=100;            			  //�����ֵ
		iArray[1][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[2]=new Array();
		iArray[2][0]="��������";          		//����
		iArray[2][1]="190px";            		//�п�
		iArray[2][2]=100;            			  //�����ֵ
		iArray[2][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[3]=new Array();
		iArray[3][0]="��Ч����"; //����
		iArray[3][1]="85px";            		//�п�
		iArray[3][2]=80;            			  //�����ֵ
		iArray[3][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[4]=new Array();
		iArray[4][0]="������ֹ����";            //����
		iArray[4][1]="85px";            		//�п�
		iArray[4][2]=80;            		 	  //�����ֵ
		iArray[4][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[5]=new Array();
		iArray[5][0]="��������";            //����
		iArray[5][1]="85px";            		//�п�
		iArray[5][2]=80;            		 	  //�����ֵ
		iArray[5][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[6]=new Array();
		iArray[6][0]="����״̬";         		//����
		iArray[6][1]="60px";            		//�п�
		iArray[6][2]=80;            			  //�����ֵ
		iArray[6][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[7]=new Array();
		iArray[7][0]="�ͻ�����";            //����
		iArray[7][1]="70px";            		//�п�
		iArray[7][2]=80;            			  //�����ֵ
		iArray[7][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������

		iArray[8]=new Array();
		iArray[8][0]="�ͻ���";         		    //����
		iArray[8][1]="85px";            		//�п�
		iArray[8][2]=100;            			  //�����ֵ
		iArray[8][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
	    iArray[9]=new Array();
		iArray[9][0]="��������";          		  //����
		iArray[9][1]="85px";            		//�п�
		iArray[9][2]=60;            			  //�����ֵ
		iArray[9][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[10]=new Array();
		iArray[10][0]="֤������";         		    //����
		iArray[10][1]="70px";            		//�п�
		iArray[10][2]=100;            			  //�����ֵ
		iArray[10][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
	    iArray[11]=new Array();
		iArray[11][0]="֤����";          		  //����
		iArray[11][1]="70px";            		//�п�
		iArray[11][2]=60;            			  //�����ֵ
		iArray[11][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[12]=new Array();
		iArray[12][0]="��ϵ�绰";          		  //����
		iArray[12][1]="70px";            		//�п�
		iArray[12][2]=60;            			  //�����ֵ
		iArray[12][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[13]=new Array();
		iArray[13][0]="��ϵ��ַ";         		    //����
		iArray[13][1]="70px";            		//�п�
		iArray[13][2]=100;            			  //�����ֵ
		iArray[13][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
	    iArray[14]=new Array();
		iArray[14][0]="��������";          		  //����
		iArray[14][1]="70px";            		//�п�
		iArray[14][2]=60;            			  //�����ֵ
		iArray[14][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������

		iArray[15]=new Array();
		iArray[15][0]="ְҵ����";          		  //����
		iArray[15][1]="100px";            		//�п�
		iArray[15][2]=60;            			  //�����ֵ
		iArray[15][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[16]=new Array();
		iArray[16][0]="Ͷ\�����˱�־";          		  //����
		iArray[16][1]="80px";            		//�п�
		iArray[16][2]=80;            			  //�����ֵ
		iArray[16][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		CardInfo = new MulLineEnter("fm" , "CardInfo"); 
		// ��Щ���Ա�����loadMulLineǰ
		CardInfo.mulLineCount = 5;   
		CardInfo.displayTitle = 1;
	    CardInfo.hiddenSubtraction=1;
	    CardInfo.hiddenPlus=1;
		CardInfo.locked = 1;
		CardInfo.canSel = 1;
		CardInfo.loadMulLine(iArray);  
		//��Щ����������loadMulLine����
		//CardInfo.setRowColData(1,1,"asdf");
	}
	catch(ex)
	{
		alert(ex);
	}
}

</script>
