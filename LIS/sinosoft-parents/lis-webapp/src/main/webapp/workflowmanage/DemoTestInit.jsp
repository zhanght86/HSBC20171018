<%
/**
 * Created by IntelliJ IDEA.
 * User: jinsh
 * Date: 2009-1-7
 * Time: 15:32:15
 * To change this template use File | Settings | File Templates.
 */

%>

<!--�û�У����-->

<script language="JavaScript">  

function initInpBox()
{

    try
    {
    }
    catch(ex)
    {
        alert("��InvAssOrderbuildDetailInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
    }
}

function initForm()
{
    try
    {
        initInpBox();
        initDemoWorkFlowGrid();
    }
    catch(re)
    {
        alert("InvAssOrderbuildDetailInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
} //�ɽ�����ܱ�
function initDemoWorkFlowGrid()
{
    var iArray = new Array();
    var i = 0;
    try
    {
    		bg1 = new CGrid("fm", "DemoWorkFlowGrid", turnPage2);
  	bg1.setActivityId("30020001");
  	bg1.setProcessId("6300200001");
  	//����ģʽ
  	//bg1.addColumn("�˱�����,��������,�ϱ�����,�Ǽ�ҵ��Ա,ɨ��ʱ��,���ظ�ʱ��,�˱�����״̬,�������,���ֱ���,ӡˢ��,���������,VIP�ͻ�,ҵ��Ա,Ͷ������,��������,�ϱ���־,��������,�����������,�������������,�������Id,�˱�����״̬,��������,Ͷ���˺�");
  	//bg.setRadioFunction("IniteasyQueryAddClick");
  	//JSONģʽ
  	//�˱�����,��������,�ϱ�����,�Ǽ�ҵ��Ա,ɨ��ʱ��,���ظ�ʱ��,�˱�����״̬,�������,���ֱ���,
  	//ӡˢ��,���������,VIP�ͻ�,ҵ��Ա,Ͷ������,��������,�ϱ���־,��������, ����������� ,�������������,
  	//�������ID,�˱�����״̬,��������,Ͷ���˺�         
/*
  	bg1.addColumn([//{title:'���',width:'20px'},
  	{title:'�˱�����',width:'20px'},
  	{title:'��������',width:'80px'},
  	{title:'�ϱ�����',width:'80px'},
  	{title:'�Ǽ�ҵ��Ա',width:'0px'},
  	{title:'ɨ��ʱ��',width:'150px'},
  	{title:'���ظ�ʱ��',width:'150px'},
  	{title:'�˱�����״̬',width:'40px'},
  	{title:'�������',width:'60px'},
  	{title:'���ֱ���',width:'120px'},
  	{title:'ӡˢ��',width:'120px'},
  	{title:'���������',width:'60px'},
  	{title:'VIP�ͻ�',width:'0px'},
  	{title:'ҵ��Ա',width:'0px'},
  	{title:'Ͷ������',width:'0px'},
  	{title:'��������',width:'0px'},
  	{title:'�ϱ���־',width:'0px'},
  	{title:'��������',width:'0px'},
  	{title:'�����������',width:'0px'},
  	{title:'�������������',width:'0px'},
  	{title:'�������Id',width:'0px'},
  	{title:'�˱�״̬',width:'0px'},
  	{title:'��������',width:'0px'},
  	{title:'Ͷ���˺�',width:'0px'}
  	]); 	
  	*/
  	
  	//bg.addColumn([{name:'Missionid',readonly:'3'},{name:'submissionid',readonly:'3'},{name:'activityid',readonly:'3'}]);
  	//bg.addColumn("ManageCom,AgentCode");
  	//bg.addParam("ManageCom","86",'like');
  	//bg.setDefaultOperator(null);
  	bg1.showEmergency(1, 1, 1);
  	bg1.setRadioFunction("IniteasyQueryAddClick");
  	bg1.setEmergencyColor([[10, "yellow"], [50, "#33DEDB"], [100, "#F06147"]]);
  	bg1.initGrid();
    //bg1.queryData();
  	//changeColor(bg1);
  
  	DemoWorkFlowGrid = bg1.grid;
    }
    catch(ex)
    {
        alert(ex);
    }
}


</script>
