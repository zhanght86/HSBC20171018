<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�EdorErrorUWQueryInit.jsp
//�����ܣ���ȫ�˱���ѯ
//�������ڣ�2005-07-21 19:10:36
//������  ��liurx
//���¼�¼��  ������    ��������     ����ԭ��/����
%> 
<%@page import="com.sinosoft.lis.pubfun.*"%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
	//���ҳ��ؼ��ĳ�ʼ����
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	
	if(globalInput == null) {
		out.println("session has expired");
		return;
	}
	
	String strOperator = globalInput.Operator;
%>                            

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 

  try
  {                              
    document.all('EdorNo').value =top.opener.document.all('EdorNo').value;

  }
  catch(ex)
  {
    alert("��EdorErrorUWQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}



function initForm()
{
  try
  {
    initInpBox();
		initEdorMainGrid();
		initInfoGrid();
		queryEdorMain();
		showApproveInfo();
				
  }
  catch(re)
  {
    alert("��EdorErrorUWQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

//������Ϣ��ʼ��
function initEdorMainGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=30;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="���κ˱�����";         		//����
      iArray[1][1]="80px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�˱���Ϣ";         		//����
      iArray[2][1]="400px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��ȫ�����";         		//����
      iArray[3][1]="145px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="������";         		//����
      iArray[4][1]="145px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[5]=new Array();
      iArray[5][0]="������Ŀ";         		//����
      iArray[5][1]="60px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="������";         		//����
      iArray[6][1]="145px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      

      


      
      EdorMainGrid = new MulLineEnter( "document" , "EdorMainGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      EdorMainGrid.mulLineCount = 5;   
      EdorMainGrid.displayTitle = 1;
      EdorMainGrid.locked = 1;
      EdorMainGrid.canSel = 0;
      EdorMainGrid.canChk = 0;
      EdorMainGrid.hiddenPlus = 1;
      EdorMainGrid.hiddenSubtraction = 1;
      EdorMainGrid.loadMulLine(iArray);       
      }
      catch(ex)
      { 
        alert(ex);
      }
}

function initInfoGrid() //��ʼ����ȫ��Ϣ����
    {
    var iArray = new Array();
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="���";                  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";                              //�п�
      iArray[0][2]=30;                      //�����ֵ
      iArray[0][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��ȫ�����";
      iArray[1][1]="145px";
      iArray[1][2]=150;
      iArray[1][3]=0;

      iArray[2]=new Array();
      iArray[2][0]="������";
      iArray[2][1]="70px";
      iArray[2][2]=100;
      iArray[2][3]=0;

      iArray[3]=new Array();
      iArray[3][0]="������";
      iArray[3][1]="70px";
      iArray[3][2]=100;
      iArray[3][3]=0;

      iArray[4]=new Array();
      iArray[4][0]="��������";
      iArray[4][1]="80px";
      iArray[4][2]=100;
      iArray[4][3]=0;

      iArray[5]=new Array();
      iArray[5][0]="�޸�ԭ��";
      iArray[5][1]="120px";
      iArray[5][2]=150;
      iArray[5][3]=0;

      iArray[6]=new Array();
      iArray[6][0]="�������";
      iArray[6][1]="490px";
      iArray[6][2]=300;
      iArray[6][3]=0;


      InfoGrid = new MulLineEnter("document", "InfoGrid");
      //��Щ���Ա�����loadMulLineǰ
      InfoGrid.mulLineCount = 5;
      InfoGrid.displayTitle = 1;
      InfoGrid.locked = 1;
      InfoGrid.canSel = 0;
      InfoGrid.canChk = 0;
      InfoGrid.hiddenPlus = 1;
      InfoGrid.hiddenSubtraction = 1;
      InfoGrid.loadMulLine(iArray);

      //��Щ����������loadMulLine����

    }
    catch(ex)
    {
        alert(ex);
    }
}



</script>
