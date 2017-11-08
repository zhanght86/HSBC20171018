//               该文件中包含客户端需要处理的函数和事件

var turnPage ;
var turnPage2 ;
var turnPage3 ;
var turnPage4 ;
var turnPage5 ;
var turnPage6 ;
var turnPage7 ;
var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var tDisplay;
var arrDataSet;
var mySql = new SqlClass();

//历史工资查询结果函数

function LAwageClick()
{
  turnPage = new turnPageClass();
	// 书写SQL语句
	var strSQL = "";
   //strSQL = "(select a.indexcalno as indexcalno , a.agentcode as agentcode ,b.name as name,a.agentgroup, a.summoney,a.lastmoney ,a.currmoney from lawage a, laagent b where  a.agentcode = b.agentcode and a.agentcode in (select trim(agentcode)  from lacommisiondetail  where grpcontno = '"+tContNo+"') union select a.indexcalno as indexcalno,a.agentcode as agentcode ,b.name as name,a.agentgroup, a.summoney,a.lastmoney ,a.currmoney from lawage a, laagent b where  a.agentcode = b.agentcode and a.agentcode in (select trim(agentcode)  from lacommisiondetailb  where grpcontno = '"+tContNo+"')) order by name,indexcalno,agentcode ";
   mySql = new SqlClass();
	mySql.setResourceName("sys.ChannelQuerySql");
	mySql.setSqlId("ChannelQuerySql1");
	mySql.addSubPara(tContNo); 
	mySql.addSubPara(tContNo); 
   turnPage.queryModal(mySql.getString(),LAwageGrid);
 	   
}

//业务员基本信息查询函数
function LAAgentClick()
{
  turnPage2 = new turnPageClass();

// 书写SQL语句
var strSQL = "";
  
   //strSQL = "select a.agentcode ,a.agentgroup,a.name,(case a.sex when '0' then '男' when '1' then '女' end),a.managecom, b.agentgrade,a.mobile,a.email,a.idno from laagent a, latree b where  a.agentcode = b.agentcode and a.agentcode = (select trim(agentcode)  from lccont  where contno = '"+tContNo+"')";
    mySql = new SqlClass();
	mySql.setResourceName("sys.ChannelQuerySql");
	mySql.setSqlId("ChannelQuerySql2");
	mySql.addSubPara(tContNo);  
    turnPage2.queryModal(mySql.getString(),LAAgentGrid);
// 	   
}
 
//业务员历史信息查询函数
function LAAgentBClick()
{
  turnPage3 = new turnPageClass();

	// 书写SQL语句
	var strSQL = "";
  
  // strSQL = "select a.edorno,a.branchtype,a.agentcode,a.newagentcode ,a.name,(case  a.sex when '0' then '男' when '1' then '女' end),a.agentgroup,a.mobile,a.idno from laagentb a, latreeb b where  a.agentcode = b.agentcode and a.agentcode = (select trim(agentcode)  from lccont  where contno = '"+tContNo+"')";
   mySql = new SqlClass();
	mySql.setResourceName("sys.ChannelQuerySql");
	mySql.setSqlId("ChannelQuerySql3");
	mySql.addSubPara(tContNo);  
    turnPage3.queryModal(mySql.getString(),LAAgentBGrid);
 	   
}

//考核历史查询函数
function LAAssessAccessoryClick(){
	turnPage4 = new turnPageClass();
	var strSQL = "";
  //strSQL = "select  a.indexcalno,a.agentcode ,b.name,(case b.sex when '0' then '男' when '1' then '女' end ),a.managecom,a.agentgrade,a.agentgrade1  from  laassessaccessory a,laagent b where a.agentcode = (select trim(agentcode) from lccont where contno = '"+tContNo+"')and  a.assesstype = '00' and a.agentcode = b.agentcode order by indexcalno";
  mySql = new SqlClass();
	mySql.setResourceName("sys.ChannelQuerySql");
	mySql.setSqlId("ChannelQuerySql4");
	mySql.addSubPara(tContNo); 
  turnPage4.queryModal(mySql.getString(),LAAssessAccessoryGrid);
	}
	
// 代理人血缘关系查询函数
	function  LARearRelationClick(){
		turnPage5 = new turnPageClass();
		var strSQL = "";
	 // strSQL = "(select a.relalevel as relalevel,a.relagens,a.agentcode,(select tt.name from laagent tt where tt.agentcode=a.agentcode),a.relaagentcode,(select tt.name from laagent tt where tt.agentcode=a.relaagentcode),a.agentgroup,(case a.rearflag when '0' then '存在' when '1' then '不存在' end) from larelation a, laagent b where a.agentcode = b.agentcode and a.relaagentcode = (select trim(agentcode)  from lccont where contno = '"+tContNo+"') union select a.relalevel as relalevel,a.relagens,a.agentcode,(select tt.name from laagent tt where tt.agentcode=a.agentcode),a.relaagentcode,(select tt.name from laagent tt where tt.agentcode=a.relaagentcode),a.agentgroup ,(case a.rearflag when '0' then '存在' when '1' then '不存在' end) from larelation a, laagent b where a.agentcode = b.agentcode and  a.agentcode = (select trim(agentcode) from lccont  where contno = '"+tContNo+"')) order by relalevel";
    mySql = new SqlClass();
	mySql.setResourceName("sys.ChannelQuerySql");
	mySql.setSqlId("ChannelQuerySql5");
	mySql.addSubPara(tContNo); 
	mySql.addSubPara(tContNo); 
    turnPage5.queryModal(mySql.getString(),LARearRelationGrid);
    
		}
		
// 福利历史查询函数
  
  function LAwageWelareClick(){
  	 turnPage6 = new turnPageClass();
  	 var strSQL = "";
  	// strSQL = "select a.indexcalno,a.agentcode,a.agentgroup,b.name,a.k04,a.k05,a.k06,a.k08,a.k09 ,a.k10 from lawage a , laagent b where a.agentcode = b.agentcode and a.agentcode = (select trim(agentcode)  from lccont  where contno = '"+tContNo+"') order by a.indexcalno ";
  	mySql = new SqlClass();
	mySql.setResourceName("sys.ChannelQuerySql");
	mySql.setSqlId("ChannelQuerySql6");
	mySql.addSubPara(tContNo);  
  	 turnPage6.queryModal(mySql.getString(),LAwageWelareGrid);
  	}
 
 // 福利历史查询函数
  
  function AgentClick(){
  	 turnPage7 = new turnPageClass();
  	 var strSQL = "";
  	 //strSQL = "select c.branchattr,c.Name,a.Name,a.AgentCode,b.AgentGrade,a.EmployDate from LAAgent a,LATree b,LABranchGroup c  where '1130720569000'='1130720569000' and  a.agentcode = (select trim(agentcode)  from lccont  where contno = '"+tContNo+"') and (c.state<>'1' or c.state is null) and a.AgentCode = b.AgentCode  and b.agentgroup=c.AgentGroup and a.agentstate in ('01','02') and a.branchtype='1'  order by c.branchattr,b.agentgrade desc,a.agentcode asc ";
  	 mySql = new SqlClass();
	mySql.setResourceName("sys.ChannelQuerySql");
	mySql.setSqlId("ChannelQuerySql7");
	mySql.addSubPara(tContNo);  
  	 turnPage7.queryModal(mySql.getString(),AgentGrid);
  	} 	
//返回函数
function GoBack(){
	
	  top.close();
	
	}