<%
//�������ƣ�CaseInfoInit.jsp
//�����ܣ�
//�������ڣ�2004-2-18
//������  ��LiuYansong
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<script language="JavaScript">

function initInpBox()
{
  try
  {
    
  }
  catch(ex)
  {
    alert("��ClaimGetQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��ClaimGetQueryInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}

function initForm()
{
  try
  {
  	initInpBox();
  	initSelBox();
  	initCheckGrid();
		ShowCheckInfo();
  }
  catch(re)
  {
    alert("SyS/CaseInfoInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initCheckGrid()
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
      iArray[1][0]="�������";    	//����
      iArray[1][1]="150px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�������";         			//����
      iArray[2][1]="150px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="���������";         			//����
      iArray[3][1]="150px";            		//�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

			iArray[4]=new Array();
      iArray[4][0]="����";         			//����
      iArray[4][1]="0px";            		//�п�
      iArray[4][2]=60;            			//�����ֵ
      iArray[4][3]=0;
      
      iArray[5]=new Array();
      iArray[5][0]="ǩ����";         			//����
      iArray[5][1]="0px";            		//�п�
      iArray[5][2]=60;            			//�����ֵ
      iArray[5][3]=0;
      
      CheckGrid = new MulLineEnter( "fm" , "CheckGrid" ); 
      CheckGrid.mulLineCount = 0;   
      CheckGrid.displayTitle = 1;
      CheckGrid.locked = 1;
      CheckGrid.canSel=1;
      CheckGrid.loadMulLine(iArray);  
    }
    catch(ex)
    {
    	alert("aaaaaaaaaa");
      alert(ex);
    }
}
</script>
