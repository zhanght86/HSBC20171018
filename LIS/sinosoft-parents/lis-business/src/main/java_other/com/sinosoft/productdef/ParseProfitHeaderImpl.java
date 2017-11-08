



package com.sinosoft.productdef;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


import com.sinosoft.lis.db.PD_LMRiskProfitHeadDB;
import com.sinosoft.lis.db.PD_LMTabHeadRelaDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.PD_LMRiskProfitHeadSchema;
import com.sinosoft.lis.vschema.PD_LMTabHeadRelaSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.StrTool;

public class ParseProfitHeaderImpl{
	
	private int maxLevel = 0;

	/**
	 * @author fengsn
	 * riskCode 险种代码
	 * type 展示类型 
	 * 获得收益数据表表头对象
	 * */ 
	public XmlTable getProfitHeader(String riskCode, String type) {
		List list = getHeaderList(riskCode,type);
		int deepth = getMaxDeepth(list);
		List<XmlTable> xmlList=new ArrayList<XmlTable>();
		for (int i = 0; i < list.size(); i++) {
			List<Header> headers=new ArrayList<Header>();
			Header header=(Header)list.get(i);
			if (header.getParentId().equals("-1")) {
				header.setLevel(1);
				XmlTable<Header> xmlTable=new XmlTable<Header>();
				headers.add(header);
				String id=header.getId();
				List _list = sortHeaderList(list,id,1);
				
				headers.addAll(_list);
//				headers = setHeaderRowAndCol(headers,deepth);
				xmlTable.setHeaders(headers);
				xmlList.add(xmlTable);
			}
		}
		
		XmlTable<XmlTable> xmlTable=new XmlTable<XmlTable>();
		xmlTable.setHeaders(xmlList);
		orgnizeData(xmlTable);
		return xmlTable;
	}
	
	private void orgnizeData(XmlTable<XmlTable> table){
		List result = new ArrayList();
		List<XmlTable> headers = table.getHeaders();
		XmlTable<Header> currentTable = null;
		List<Header> tempHeader = null;
		int maxLayer = 0;
		List<Header> head_list = new ArrayList();
		//获得最大的层数
		for (int i = 0; i < headers.size(); i++) {
			currentTable = headers.get(i);
			tempHeader = currentTable.getHeaders();
			if (tempHeader.size() == 0) {
				
			} else {
				tempHeader = setHeaderRowAndCol(tempHeader,maxLevel);
			}
		}
	}
	
	/**
	 * @author fengsn
	 * 获得对应类型的表头header对象的list
	 * */
	private List getHeaderList(String riskCode, String type){
		//根据类型来初始化表头Header对象
		List list = new ArrayList();
		PD_LMTabHeadRelaDB showinfo = new PD_LMTabHeadRelaDB();
		showinfo.setRiskCode(riskCode);
		showinfo.setType(type);
		PD_LMTabHeadRelaSet showset = showinfo.query();
		if(showset!=null&&showset.size()>0){
			for(int i=1;i<=showset.size();i++){
				String headId = showset.get(i).getHeadID();
				
				PD_LMRiskProfitHeadDB profit_db = new PD_LMRiskProfitHeadDB();
				profit_db.setID(headId);
				profit_db.setRiskCode(riskCode);
				if(profit_db.getInfo()){
					Header tmp_head = getHeader(profit_db.getSchema());
					list.add(tmp_head);
				}
			}
		}
		//排序
		Comparator<Header> comparator = new Comparator<Header>() {
			public int compare(Header header1, Header header2) {
				if (!header1.getOrder().equals(header2.getOrder())) {
					return Integer.parseInt(header1.getOrder())-Integer.parseInt(header2.getOrder());
				}
				if (!header1.getId().equals(header2.getId())) {
					return header1.getId().compareTo(header2.getId());
				}
				return 0;
			}
		};
		Collections.sort(list,comparator);
		return list;
	}
	
	//schema转化为Header对象
	private Header getHeader(PD_LMRiskProfitHeadSchema lmRiskProfitHeadSchema){
		Header header = new Header();
		header.setId(lmRiskProfitHeadSchema.getID());
		String profitCode = lmRiskProfitHeadSchema.getPrifitCode();
		String riskCode = lmRiskProfitHeadSchema.getRiskCode();
		if(profitCode!=null&&!profitCode.equals("")){
			header.setCode(profitCode);
		}
		String p_id = lmRiskProfitHeadSchema.getParentID();
		if(p_id!=null&&!p_id.equals("")){
			header.setParentId(p_id);
		}
		String order = lmRiskProfitHeadSchema.getShowOrder();
		if(order!=null&&!order.equals("")){
			header.setOrder(order);
		}

		String name = lmRiskProfitHeadSchema.getName();
		header.setName(name);
		
		return header;
	}

	
	/**
	 * @author fengsn
	 * 对表头的list进行递归分类
	 * */
	private List sortHeaderList(List list,String id,int index){
		List result = new ArrayList();
		for(int i = 0; i < list.size(); i++){
			Header header=(Header)list.get(i);
			String pid=header.getParentId();
			if(id.equals(pid)){
				if((index+1)>maxLevel){
					maxLevel = index+1;
				}
				header.setLevel(index+1);
				result.add(header);
				String tmp_id = header.getId();
				List tmp_list = sortHeaderList(list,tmp_id,index+1);
				result.addAll(tmp_list);
			}
		}
		
		return result;
	}
	
	/**
	 * @author fengsn
	 * 对表头设置所占的行数以及列数
	 * */
	private List setHeaderRowAndCol(List list,int deepth){
//		List result = new ArrayList();
		for(int i = 0; i < list.size(); i++){
			Header header=(Header)list.get(i);
			String id=header.getId();
			String p_id=header.getParentId();
			int leaf_no = getLeafNodeNo(list,id);
			if(leaf_no>0){
				header.setCol(leaf_no);
				header.setRow(1);
			}else if(leaf_no==0){
				header.setCol(1);
				int level = header.getLevel();
				header.setRow(deepth-level+1);
			}
		}
		return list;
	}
	
	/**
	 * @author fengsn
	 * 计算叶子节点数
	 * */
	private int getLeafNodeNo(List list,String id){
		int index=0;
		for(int i = 0; i < list.size(); i++){
			Header header=(Header)list.get(i);
			String pid=header.getParentId();
			if(id.equals(pid)){
				index = index + 1;
				String now_id=header.getId();
				int tmp =  getLeafNodeNo(list,now_id);
				if(tmp==0){
					
				}else{
					index = index + tmp - 1;
				}
			}
		}
		return index;
	}
	
	/**
	 * @author fengsn
	 * 计算结构的深度
	 * */
	private int getMaxDeepth(List list){
		if(list!=null&&list.size()>0){
			List dd = new ArrayList();
			for(int i=0;i<list.size();i++){
				Header header=(Header)list.get(i);
				dd.add(header.getLevel());
			}
			int maxNo =0 ;
			for(int j=0;j<dd.size();j++){
				int temp = Integer.parseInt(dd.get(j).toString());
				if(temp>maxNo){
					maxNo = temp;
				}
			}
			return maxNo;
		}else{
			return 0;
		}
	}

	
	public static void main(String args[]){
		
	}
}


