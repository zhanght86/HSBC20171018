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
var presel = -1;
//����ʱ��ѯ
function reportDetailClick(parm1,parm2)
{
  	var ex,ey;
  	ex = window.event.clientX+document.body.scrollLeft;  //�õ��¼�������x
  	ey = window.event.clientY+document.body.scrollTop;   //�õ��¼�������y
  	divLPAppntIndDetail.style.left=ex;
  	divLPAppntIndDetail.style.top =ey;
   	detailQueryClick();
}
function initInpBox()
{ 

  try
  {      
  	
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
    
    document.all('PolNo').value = top.opener.document.all('PolNo').value;
    
    document.all('EdorType').value = top.opener.document.all('EdorType').value;
  
  }
  catch(ex)
  {
    alert("��PEdorTypeIAInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initSelBox()
{  
  try                 
  {
  }
  catch(ex)
  {
    alert("��PEdorTypeIAInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox(); 

    initDiv();

    initLCAppntIndGrid();

    initQuery();  
  }
  catch(re)
  {
    alert("PEdorTypeIAInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
// ��Ϣ�б�ĳ�ʼ��
function initLCAppntIndGrid()
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
      iArray[1][0]="�ͻ���";    	//����1
      iArray[1][1]="150px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�ͻ�����";    	//����1
      iArray[2][1]="50px";            		//�п�
      iArray[2][2]=50;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[3]=new Array();
      iArray[3][0]="�ͻ��Ա�";         			//����8
      iArray[3][1]="50px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="��������";         		//����5
      iArray[4][1]="100px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������

      iArray[5]=new Array();
      iArray[5][0]="����";         		//����6
      iArray[5][1]="50px";            		//�п�
      iArray[5][2]=50;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="֤������";         		//����6
      iArray[6][1]="50px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="֤������";         		//����7
      iArray[7][1]="150px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      LCAppntIndGrid = new MulLineEnter( "fm" , "LCAppntIndGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      LCAppntIndGrid.mulLineCount = 10;   
      LCAppntIndGrid.displayTitle = 1;
      LCAppntIndGrid.canSel=1;
      LCAppntIndGrid.hiddenPlus = 1;
      LCAppntIndGrid.hiddenSubtraction = 1;
      LCAppntIndGrid.selBoxEventFuncName ="reportDetailClick";
      LCAppntIndGrid.loadMulLine(iArray);  
      LCAppntIndGrid.detailInfo="������ʾ��ϸ��Ϣ";
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initQuery()
{	
	var i = 0;
	var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		
	document.all('fmtransact').value = "QUERY||MAIN";
	//alert("----begin---");
	//showSubmitFrame(mDebug);
	fm.submit();	  	 	 

}


function detailQueryClick()
{
    divPersonQuery.style.display = "";
    var sel = LCAppntIndGrid.getSelNo();

    if (sel != presel) {
        document.all("QueryCustomerNo").value = "";
        presel = sel;
    }
        
}

function initDiv()
{
	divLPAppntIndDetail.style.display ='none';

//    divDetail.style.display='none';
}

</script>