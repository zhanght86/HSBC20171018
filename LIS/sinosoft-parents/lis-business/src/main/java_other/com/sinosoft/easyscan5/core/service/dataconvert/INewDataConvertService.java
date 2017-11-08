package com.sinosoft.easyscan5.core.service.dataconvert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sinosoft.easyscan5.core.vo.easyscan.ProductVo;
import com.sinosoft.utility.VData;

public interface INewDataConvertService {
	/**
	 * 索引XML数据转换为vData
	  * @param strXML
	  * @param vData
	  * @return
	  */
	public String xmlToVData(String xml, VData vData);
	/**
	 * 索引VData转换为XML
	 * @param bufXML
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String vdataToXml(VData data, StringBuffer bufXML);
	
	/**
	 * 索引VData转换为XML
	 * @param bufXML
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String stringToXml(String dataStr, StringBuffer bufXML);
	/**
	 * 索引VData转换为XML
	 * @param bufXML
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String stringToXml(String[] dataStr, StringBuffer bufXML);
	/**
	 * 索引XML转换为map
	 * @param xml
	 * @param map
	 * @return
	 */
	public String xmlToMap(String xml,Map<String, String> map);
	/**
	 * map转换为XML
	 * @param map
	 * @param map1
	 * @param map2
	 * @param xml
	 * @return
	 */
	public String mapToXml(Map map,Map map1,StringBuffer xml,String path);

	/**
	 * list转换为XML
	 * @param esQcMainsList
	 * @param xmlBuffer
	 */
	public String listToXml(List esQcMainsList, StringBuffer xmlBuffer);
	public String listToXml(List list1,Map map, StringBuffer xmlBuffer);
	/**
	 * XML转为List
	 * @param indexStr
	 * @param queryList
	 * @return
	 */
	public String xmlToList(String indexStr,List queryList);
	/**
	 * xmlToProductVo
	 * @param indexXML
	 * @param productVo
	 * @return
	 */
	public String xmlToProductVo(String indexXML, ProductVo productVo);

}
