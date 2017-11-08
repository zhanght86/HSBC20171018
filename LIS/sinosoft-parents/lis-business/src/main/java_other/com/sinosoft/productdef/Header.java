



package com.sinosoft.productdef;

public class Header {
	/** 唯一标识 */
	private String id;
	/** 表头名称 */
	private String name;
	/** 列数 */
	private int col;
	/** 行数 */
	private int row;
	/** 若果该表头元素有上一层，则存储其id号。否则为-1 */
	private String parentId = "-1";
	/** 表头配置代码 */
	private String code;
	/**表头层数*/
	private int layers;
	/** 所在表头的层数 */
	private int level;
	/** 分组标识 */
	private String group;
	/** 顺序 */
	private String order;
	//以下属性主要用于建议书打印
	/** 字体大小 */
	private float fontSize;
	/** 字体名称 */
	private String fontName;
	/** 表头文字对齐方式 */
	private String align;
	/** 字体格式（如加粗 ）*/
	private String fontStyle;
	/** 边框类型 */
	private String border ;
	/** 列宽系数 */
	private String width;
	
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getLayers() {
		return layers;
	}
	public void setLayers(int layers) {
		this.layers = layers;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	
	public float getFontSize() {
		return fontSize;
	}
	public void setFontSize(float fontSize) {
		this.fontSize = fontSize;
	}
	public String getFontName() {
		return fontName;
	}
	public void setFontName(String fontName) {
		this.fontName = fontName;
	}
	public String getAlign() {
		return align;
	}
	public void setAlign(String align) {
		this.align = align;
	}
	public String getFontStyle() {
		return fontStyle;
	}
	public void setFontStyle(String fontStyle) {
		this.fontStyle = fontStyle;
	}
	public String getBorder() {
		return border;
	}
	public void setBorder(String border) {
		this.border = border;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}	
}


