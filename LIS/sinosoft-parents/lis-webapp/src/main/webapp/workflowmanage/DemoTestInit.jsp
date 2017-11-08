<%
/**
 * Created by IntelliJ IDEA.
 * User: jinsh
 * Date: 2009-1-7
 * Time: 15:32:15
 * To change this template use File | Settings | File Templates.
 */

%>

<!--用户校验类-->

<script language="JavaScript">  

function initInpBox()
{

    try
    {
    }
    catch(ex)
    {
        alert("在InvAssOrderbuildDetailInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
        alert("InvAssOrderbuildDetailInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
} //成交后汇总表
function initDemoWorkFlowGrid()
{
    var iArray = new Array();
    var i = 0;
    try
    {
    		bg1 = new CGrid("fm", "DemoWorkFlowGrid", turnPage2);
  	bg1.setActivityId("30020001");
  	bg1.setProcessId("6300200001");
  	//正常模式
  	//bg1.addColumn("核保级别,被保险人,上报类型,星级业务员,扫描时间,最后回复时间,核保任务状态,管理机构,险种编码,印刷号,最近处理人,VIP客户,业务员,投保日期,复核日期,上报标志,到账日期,工作流任务号,工作流子任务号,工作流活动Id,核保任务状态,初审日期,投保人号");
  	//bg.setRadioFunction("IniteasyQueryAddClick");
  	//JSON模式
  	//核保级别,被保险人,上报类型,星级业务员,扫描时间,最后回复时间,核保任务状态,管理机构,险种编码,
  	//印刷号,最近处理人,VIP客户,业务员,投保日期,复核日期,上报标志,到账日期, 工作流任务号 ,工作流子任务号,
  	//工作流活动ID,核保任务状态,初审日期,投保人号         
/*
  	bg1.addColumn([//{title:'序号',width:'20px'},
  	{title:'核保级别',width:'20px'},
  	{title:'被保险人',width:'80px'},
  	{title:'上报类型',width:'80px'},
  	{title:'星级业务员',width:'0px'},
  	{title:'扫描时间',width:'150px'},
  	{title:'最后回复时间',width:'150px'},
  	{title:'核保任务状态',width:'40px'},
  	{title:'管理机构',width:'60px'},
  	{title:'险种编码',width:'120px'},
  	{title:'印刷号',width:'120px'},
  	{title:'最近处理人',width:'60px'},
  	{title:'VIP客户',width:'0px'},
  	{title:'业务员',width:'0px'},
  	{title:'投保日期',width:'0px'},
  	{title:'复核日期',width:'0px'},
  	{title:'上报标志',width:'0px'},
  	{title:'到账日期',width:'0px'},
  	{title:'工作流任务号',width:'0px'},
  	{title:'工作流子任务号',width:'0px'},
  	{title:'工作流活动Id',width:'0px'},
  	{title:'核保状态',width:'0px'},
  	{title:'初审日期',width:'0px'},
  	{title:'投保人号',width:'0px'}
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
