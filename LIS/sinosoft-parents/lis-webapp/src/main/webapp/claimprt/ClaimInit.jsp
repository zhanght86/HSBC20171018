
<%
	//�������ƣ�EndorGrpQueryInit.jsp
	//�����ܣ�
	//�������ڣ�2002-12-16
	//������  ��lh
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
  try
  {         
    document.all('ManageCom').value = '<%=tG.ManageCom%>';
  }
  catch(ex)
  {
    alert("��ClaimInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

// ������ĳ�ʼ��
function initSelBox()
{  
  try                 
  {
	//setOption("pad_flag","0=����&1=�潻");   
  }
  catch(ex)
  {
    alert("��ClaimInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
   try
  {
    initInpBox();
    initSelBox(); 
 	initPolGrid();
 	//easyQueryClick()
   }	
  catch(re)
  {
    alert("ClaimInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initPolGrid()
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
      iArray[1][0]="������";         		//����
      iArray[1][1]="80px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="Ӧ�����";         		//����
      iArray[2][1]="50px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="�᰸����";         		//����
      iArray[3][1]="50px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="Ͷ����λ";         		//����
      iArray[4][1]="100px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      PolGrid   = new MulLineEnter( "fm" , "PolGrid" );
      PolGrid.mulLineCount = 10;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 0;
      PolGrid.canSel = 1;
      PolGrid.hiddenPlus=1;
	  PolGrid.hiddenSubtraction=1;
      PolGrid.loadMulLine(iArray); 
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
