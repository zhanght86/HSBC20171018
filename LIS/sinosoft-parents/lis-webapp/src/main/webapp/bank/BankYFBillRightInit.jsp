<%
//�������ƣ�BankYSBillInit.jsp
//�����ܣ�
//�������ڣ�2003-3-26
//������  ��������
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%
	GlobalInput tGlobalInput = new GlobalInput(); 
	tGlobalInput = (GlobalInput)session.getValue("GI");
%>                            

<script language="JavaScript">
var comCode = "<%=tGlobalInput.ComCode%>";
//����ʱ��ѯ
function RegisterDetailClick(cObj)
{
  	var ex,ey;
  	ex = window.event.clientX+document.body.scrollLeft;  //�õ��¼�������x
  	ey = window.event.clientY+document.body.scrollTop;   //�õ��¼�������y
  	divDetailInfo.style.left=ex;
  	divDetailInfo.style.top =ey;
    divDetailInfo.style.display ='';
}

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 

  try
  {                                   

  }
  catch(ex)
  {
    alert("��BankYSBillInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��BankYSBillInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
		initBillGrid();
  }
  catch(re)
  {
    alert("BankYSBillInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initBillGrid()
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
      iArray[1][0]="���κ�";    	//����
      iArray[1][1]="150px";            		//�п�
      iArray[1][2]=150;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="���д���";         			//����
      iArray[2][1]="150px";            		//�п�
      iArray[2][2]=150;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

������	iArray[3]=new Array();
      iArray[3][0]="�ܽ��";         			//����
      iArray[3][1]="100px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="������";         			//����
      iArray[4][1]="100px";            		//�п�
      iArray[4][2]=60;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      BillGrid = new MulLineEnter( "fm" , "BillGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      BillGrid.mulLineCount = 10;   
      BillGrid.displayTitle = 1;
      BillGrid.canSel=1;
      BillGrid.locked = 1;
      BillGrid.loadMulLine(iArray);  
      BillGrid.detailInfo="������ʾ��ϸ��Ϣ";
      BillGrid.detailClick=RegisterDetailClick;
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
