<%
//�������ƣ�PrtAppEndorseInit.jsp
//�����ܣ�
//�������ڣ�2005-03-03 
//������  ��FanX
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">
var mLoadFlag = "<%=request.getParameter("LoadFlag")%>";

//����ʱ��ѯ
function reportDetailClick(cObj)
{
  	var ex,ey;
  	
  	ex = window.event.clientX+document.body.scrollLeft;  //�õ��¼�������x
  	ey = window.event.clientY+document.body.scrollTop;   //�õ��¼�������y
  	divLPEdor2.style.left=ex;
  	divLPEdor2.style.top =ey;
    divLPEdor2.style.display ='';
}

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 

  try
  {                                   
	 //document.all('ContType').value = '1'; 
  }
  catch(ex)
  {
    alert("��PEdorQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��PEdorQueryInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function fillGrid()
{
   if (document.all('EdorAcceptNo').value != "")
   		easyQueryClick();

}


function initForm()
{
  try
  {
    initInpBox();
    initSelBox();   
    initPEdorAppGrid();
    initManageCom();
    fillGrid();
    if (mLoadFlag == "TASK")  
    {
    	document.all("EdorAcceptNo").value = "<%=request.getParameter("DetailWorkNo")%>";
    	easyQueryClick();
    }
  }
  catch(re)
  {
    alert("PEdorQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}


function initPEdorAppGrid()
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
      iArray[1][0]="��ȫ�����";    	//����
      iArray[1][1]="200px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="���屣����";         			//����
      iArray[2][1]="200px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="����������";         			//����
      iArray[3][1]="100px";            		//�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="����״̬";         		//����
      iArray[4][1]="100px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������

      iArray[5]=new Array();
      iArray[5][0]="������������";         		//����
      iArray[5][1]="100px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="�ڲ�������";         		//����
      iArray[6][1]="0px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      


      PEdorAppGrid = new MulLineEnter( "fm" , "PEdorAppGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PEdorAppGrid.mulLineCount = 10;   
      PEdorAppGrid.displayTitle = 1;
      PEdorAppGrid.canSel=1;
      PEdorAppGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      PEdorAppGrid.hiddenSubtraction=1;
      PEdorAppGrid.loadMulLine(iArray);  

      PEdorAppGrid.detailInfo="������ʾ��ϸ��Ϣ";
      //PEdorAppGrid.detailClick=reportDetailClick;
      //��Щ����������loadMulLine����
      //PEdorAppGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>