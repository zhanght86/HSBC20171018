



package com.sinosoft.productdef;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;

public class HeaderConfImpl{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(HeaderConfImpl.class);
	
	/**
	 * 生成表体数据的HTML代码
	 * */
	public String getBodyData(SSRS dataSSRS) {
		StringBuffer sb = new StringBuffer();
		if (dataSSRS.MaxRow > 0) {
			// 表体
			sb.append("<tbody>");
			for (int h = 1; h <= dataSSRS.getMaxRow(); h++) {
				if (h % 2 == 0) {
					sb
							.append("<tr  align='right' valign='middle' bgcolor='#FFFFFF' class='code'  > ");

				} else {
					sb
							.append("<tr  align='right' valign='middle' bgcolor='#E9F3FE' class='black'> ");

				}
				for (int d = 1; d <= dataSSRS.getMaxCol(); d++) {
					String temp = dataSSRS.GetText(h, d);
					if (temp != null && temp.length() < 4) {
						temp = "&nbsp;" + temp + "&nbsp;";
					}
					sb
							.append("<td   align='center' valign='middle' class='hei' nowrap>"
									+ temp + "</td>");
				}
				sb.append("</tr> ");
			}
			sb.append("</tbody>");
			sb.append("</table>");
		}

		return sb.toString();
	}

	/**
	 * @author fsn
	 * 返回表头的Html代码
	 * */
	public String getheaderConfStr(XmlTable table) {
		List<XmlTable> headers = table.getHeaders();
		StringBuffer sb = new StringBuffer();
		sb = new StringBuffer("");
		List headlist = getHeadderListByLevel(table);
		if(headlist!=null&&headlist.size()>0){
			sb.append("<table width='100%' border='0' align='right' cellpadding='3' cellspacing='1' bgcolor='#BCDFFC' " 
					+ " id=tableShow class='scrollTable'    >");
	   
			sb.append("<thead   class='fixedHeader'>");
			for(int i=0;i<headlist.size();i++){
				List<Header> LayHeader = (List<Header>) headlist.get(i);
				if(LayHeader==null||LayHeader.size()==0){
					continue;
				}
				sb.append("<tr bgcolor='#BCDFFC'>");
				for(int j=0;j<LayHeader.size()&&LayHeader.size()>0;j++){
					Header header = LayHeader.get(j);
					sb.append("<th height='25'");
					sb.append("colspan='" + header.getCol() + "' ");
					sb.append("rowspan='" + header.getRow() + "' ");
					String headerName = header.getName();
					headerName=StrTool.replace(headerName, "\n", "<br>");
					
					sb.append("align='center' valign='middle' bgcolor='#A2CBF9' class='hei'>");
					sb.append("<strong>" + headerName + "</strong>");
					sb.append("</th>");
				}
				sb.append("</tr>");
			}
			sb.append("</thead>"); 
		}
		sb.append("</table>");
		return sb.toString();
	}
	
	/**
	 * 按照层次来区分表头 
	 * */
	private List getHeadderListByLevel(XmlTable table){
		List result = new ArrayList();
		List<XmlTable> headers = table.getHeaders();
		XmlTable<Header> currentTable = null;
		List<Header> tempHeader = null;
//		List<Header> LayHeader1 = new ArrayList<Header>();
//		List<Header> LayHeader2 = new ArrayList<Header>();
//		List<Header> LayHeader3 = new ArrayList<Header>();
		int maxLayer = 0;
		List<Header> head_list = new ArrayList();
		//获得最大的层数
		for (int i = 0; i < headers.size(); i++) {
			currentTable = headers.get(i);
			tempHeader = currentTable.getHeaders();
			if (tempHeader.size() == 0) {
				if (logger.isDebugEnabled()) {
					logger.debug("getheaderConfStr(XmlTable) - 表格数据为空，生成表格失败");
				}
			} else {
				List dd = new ArrayList();
				for (int j = 0; j < tempHeader.size(); j++) {
					Header head = tempHeader.get(j);
					head_list.add(head);
					int level = head.getLevel();
					if(level>maxLayer){
						maxLayer = level;
					}
				}
			}
		}
		if(maxLayer>=1){
			for(int i=1;i<=maxLayer;i++){
				List _list = new ArrayList();
				for (int j = 0; j < head_list.size(); j++) {
					Header head = head_list.get(j);
					int level = head.getLevel();
					if(level==i){
						_list.add(head);
					}
				}
				result.add(_list);
			}
		}

		return result;
	}

}


