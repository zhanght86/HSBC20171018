package com.sinosoft.lis.pubfun;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.RiskStateDB;
import com.sinosoft.lis.schema.RiskStateSchema;
import com.sinosoft.lis.vschema.RiskStateSet;
import com.sinosoft.utility.VData;

public class RiskState {
private static Logger logger = Logger.getLogger(RiskState.class);
	
	private static int mNodeOrder = 0;
	
	/**
	 * 初始化节点时使用 请特别注意需要以父节点作为起始节点。 Url并不是必须存在
	 * 
	 * @param tRiskCode
	 * @param tPath
	 */
	public static void setStatePath(String tRiskCode, String tPath) {
		// tPath的形式是采取:父节点:URL->子节点1:URL->子节点2:URL->子节点3:URL->子节点4:URL
		if (tRiskCode == null || tRiskCode.equals("")) {
			return;
		}
		if (tPath == null || tPath.equals("")) {
			return;
		}
		logger.debug("开始进行节点处理。。。");

		String[] nodeUrl = tPath.split("->");
		if (nodeUrl == null || nodeUrl.length <= 0) {
			return;
		}
		String parent = null;
		RiskStateSet ttRiskStateSet = new RiskStateSet();
		for (int i = 0; i < nodeUrl.length; i++) {
			String node = null;
			String url = null;
			if (nodeUrl[i].indexOf(":") < 0) {
				node = nodeUrl[i];
			} else {
				String[] arr = nodeUrl[i].split(":");
				node = arr[0];
				url = arr[1];
			}
			
			int temp = i;
			
			if (i == 0) {// 父节点
				parent = node;
				temp = mNodeOrder;
			}
			
			RiskStateSchema tRiskStateSchema = new RiskStateSchema();
			tRiskStateSchema.setRiskNo(tRiskCode);
			tRiskStateSchema.setNodeCode(node);
			tRiskStateSchema.setNodeName(node);
			tRiskStateSchema.setNodeOrder(String.valueOf(temp));
			tRiskStateSchema.setNodeState("0");
			tRiskStateSchema.setNodeUrl(url);
			if(i == 0){
				tRiskStateSchema.setParentNodeCode("0");
			}else{
				tRiskStateSchema.setParentNodeCode(parent);
			}
			
			ttRiskStateSet.add(tRiskStateSchema);
			mNodeOrder ++;
		}

		MMap map = new MMap();
		map.put(ttRiskStateSet, "INSERT");
		submit(map);

	}

	/**
	 * 当节点需要删除时，可以进某一个父节点下多个节点删除。子节点不需要按照顺序。
	 * 
	 * @param tRiskCode
	 * @param tPath
	 */
	public static void removeNode(String tRiskCode, String tPath) {
		// tPath的形式是采取:父节点->子节点1->子节点3->子节点5
		if (tRiskCode == null || tRiskCode.equals("")) {
			return;
		}
		if (tPath == null || tPath.equals("")) {
			return;
		}
		logger.debug("开始进行节点处理。。。");

		String[] node = tPath.split("->");
		if (node == null || node.length <= 0) {
			return;
		}
		String parent = null;
		RiskStateSet ttRiskStateSet = new RiskStateSet();
		for (int i = 0; i < node.length; i++) {

			if (i == 0) {// 父节点
				parent = node[i];
			}
			RiskStateSchema tRiskStateSchema = new RiskStateSchema();
			tRiskStateSchema.setRiskNo(tRiskCode);
			tRiskStateSchema.setNodeCode(node[i]);
			ttRiskStateSet.add(tRiskStateSchema);
		}

		MMap map = new MMap();
		map.put(ttRiskStateSet, "DELETE");

		submit(map);
	}

	/**
	 * 递交数据库
	 * 
	 * @param map
	 */
	private static void submit(MMap map) {
		PubSubmit p = new PubSubmit();

		VData data = new VData();

		data.add(map);
		p.submitData(data, null);
	}

	/**

	    * 在某一个节点下增加新的节点，后续节点以此顺延

	    * 

	     * @param tRiskCode

	    * @param tPath

	    * @param tNodeName

	    */

	    public static void addNode(String tRiskCode, String tPath, String tNodeUrl) {

	       // tPath的形式是采取:父节点->子节点4，表示在子节点4下添加一个新的节点。

	       // 在tPath下，再添加新的一个节点，格式：插入节点:Url

	       if (tRiskCode == null || tRiskCode.equals("")) {

	           return;

	       }

	       if (tPath == null || tPath.equals("")) {

	           return;

	       }

	       logger.debug("开始进行节点处理。。。");

	 

	       String[] node = tPath.split("->");

	       if (node == null || node.length <= 0) {

	           return;

	       }

	 

	       if (node.length != 2) {// 必须包含父节点、子节点

	           return;

	       }

	 

	       /**

	       * 处理插入节点

	       */

	       String newNode = null;

	       String tUrl = null;

	       if (tNodeUrl.indexOf(":") == -1) {

	           newNode = tNodeUrl;

	       } else {

	           String[] arr = tNodeUrl.split(":");

	           newNode = arr[0];

	           tUrl = arr[1];

	       }

	 

	       /**

	       * 处理Order

	       */

	       RiskStateDB tRiskStateDB = new RiskStateDB();

	       tRiskStateDB.setRiskNo(tRiskCode);

	       tRiskStateDB.setNodeCode(node[1]);

	       tRiskStateDB.setParentNodeCode(node[0]);

	       if (!tRiskStateDB.getInfo()) {// 查询子节点信息

	           return;

	       }

	       int order = tRiskStateDB.getNodeOrder();

	 

	       // 查询系统中大于Order的节点，并插入新节点Order，同时后续节点以此顺延

	       // Order1

	       // Order2

	       // NewNodeOrder = Order2+1

	       // Order3 = NewNodeOrder + 1

	 

	       RiskStateSchema tRiskStateSchema = new RiskStateSchema();

	       tRiskStateSchema.setRiskNo(tRiskCode);

	       tRiskStateSchema.setNodeCode(newNode);

	       tRiskStateSchema.setNodeName(newNode);

	       tRiskStateSchema.setNodeOrder(String.valueOf(order + 1));

	       tRiskStateSchema.setNodeState("0");

	       tRiskStateSchema.setNodeUrl(tUrl);

	       tRiskStateSchema.setParentNodeCode(node[0]);// 父节点

	 
	       RiskStateDB tRiskStateDB1 = new RiskStateDB();
	       tRiskStateDB1.setSchema(tRiskStateSchema);
	       
	       //如果该 节点 已经有数了,就不需要更新.
	       if(tRiskStateDB1.getInfo())
	       {
	    	   return ; 
	       }
	       /**

	       * 将原有后续的节点Order+2

	       */

	       RiskStateDB t2RiskStateDB = new RiskStateDB();

	       t2RiskStateDB.setParentNodeCode(node[0]);

	       t2RiskStateDB.setRiskNo(tRiskCode);

	       RiskStateSet tRiskStateSet = t2RiskStateDB.query();

	       for (int i = 1; i <= tRiskStateSet.size(); i++) {

	           if (tRiskStateSet.get(i).getNodeOrder() <= order) {

	              tRiskStateSet.removeRange(i, i);

	           } else {

	              tRiskStateSet.get(i).setNodeOrder(

	                     tRiskStateSet.get(i).getNodeOrder() + 2);

	           }

	       }

	 

	       MMap map = new MMap();

	       map.put(tRiskStateSet, "UPDATE");

	       map.put(tRiskStateSchema, "INSERT");

	       submit(map);

	    }


	/**
	 * 根据所列的父节点、至子节点的状态
	 *     
	 * @param tRiskCode
	 * @param tPath
	 * @param tState
	 */
	public static void setState(String tRiskCode, String tPath, String tState) {
		// tPath的形式是采取:父节点->子节点1->子节点2->子节点3->子节点4
		if (tRiskCode == null || tRiskCode.equals("")) {
			return;
		}
		if (tPath == null || tPath.equals("")) {
			return;
		}
		logger.debug("开始进行节点处理。。。");

		String[] node = tPath.split("->");
		if (node == null || node.length <= 0) {
			return;
		}

		RiskStateSet tRiskStateSet = new RiskStateSet();
		for (int i = 1; i < node.length; i++) {
			try {
				RiskStateDB tRiskStateDB = new RiskStateDB();
				tRiskStateDB.setRiskNo(tRiskCode);
				tRiskStateDB.setNodeCode(node[i]);
				tRiskStateDB.setParentNodeCode(node[0]);
				if(!tRiskStateDB.getInfo())
				{
					return ;
				}
				tRiskStateDB.setNodeState(tState);
				tRiskStateSet.add(tRiskStateDB);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		MMap map = new MMap();
		map.put(tRiskStateSet, "UPDATE");
		submit(map);
	}

	/**
	 * 首次登陆，初始化全部节点
	 * 
	 * @param tRiskCode
	 */
	public static void init(String tRiskCode) {
//		 tPath的形式是采取:父节点:URL->子节点1:URL->子节点2:URL->子节点3:URL->子节点4:URL
		setStatePath(tRiskCode, "产品条款定义->基本信息:../productdef/PDRiskDefiInput.jsp?riskcode=" + tRiskCode + "->宽限期信息:../productdef/PDRiskDefiInput.jsp?riskcode=" + tRiskCode + "->责任信息:../productdef/PDRiskDefiInput.jsp?riskcode=" + tRiskCode + "->缴费责任信息:../productdef/PDRiskDefiInput.jsp?riskcode=" + tRiskCode + "->给付责任信息:../productdef/PDRiskDefiInput.jsp?riskcode=" + tRiskCode+"->责任生存给付信息:../productdef/PDDutyGetAliveMain.jsp?riskcode="+tRiskCode);
		setStatePath(tRiskCode, "契约业务控制->缴费字段控制:../productdef/PDContDefiEntryInput.jsp?riskcode = " + tRiskCode + "->险种各角色定义:../productdef/PDRiskRoleInput.jsp?riskcode = " + tRiskCode + "->险种分类定义:../productdef/PDRiskSortDefiInput.jsp?riskcode = " + tRiskCode +"->险种销售机构控制:../productdef/PDSaleControlInput.jsp?riskcode = " + tRiskCode + "->险种责任加费定义:../productdef/PDDutyPayAddFeeMain.jsp?riskcode = " + tRiskCode + "->险种投保规则:../productdef/PDCheckFieldInput.jsp?riskcode = " + tRiskCode + "->险种核保规则:../productdef/PDUMInput.jsp?riskcode="+tRiskCode);
		setStatePath(tRiskCode, "保全业务控制->保全项目选择:../productdef/PDEdorDefiEntryMain.jsp?riskcode = " + tRiskCode + "->保全项目算法定义:../productdef/PDAlgoDefiMain.jsp?riskcode = " + tRiskCode );
		setStatePath(tRiskCode, "理赔业务控制->保障责任赔付明细:../productdef/PDDutyGetClmMain.jsp?riskcode=" + tRiskCode );
		//setStatePath(tRiskCode, "监管信息设定->保监会报表的产品分类:../productdef/PDLFRiskMain.jsp?riskcode=" + tRiskCode );
		setStatePath(tRiskCode, "产品审核->产品审核:../productdef/PDProdAudiDetailInput.jsp?riskcode=" + tRiskCode );
		setStatePath(tRiskCode, "测试发布->测试发布:../productdef/PDTestDeployInput.jsp");
	}
	
	public static void main(String[] args) {
		//new RiskState().addNode("10010", "理赔业务控制->保障责任赔付明细", "账户责任给付明细:../productdef/PDRiskAccGetInput.jsp?riskcode=" + "10010");
		//new RiskState().init("GLS");
		
		//RiskState.setState("GLS", "产品条款定义->责任生存给付信息", "1");
		new RiskState().addNode("YY01", "产品条款定义->宽限期信息", "宽限期信息:../productdef/PDRiskDefiInput.jsp?riskcode=" + "YY01");
		//new RiskState().init("YY01");
	}
}
