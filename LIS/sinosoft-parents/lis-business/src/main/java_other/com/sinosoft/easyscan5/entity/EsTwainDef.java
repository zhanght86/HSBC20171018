package com.sinosoft.easyscan5.entity;

/**
 * EsTwainDef entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class EsTwainDef implements java.io.Serializable {

	// Fields

	private String id;
	private String defsettingcode;
	private String defsettingname;
	private String pages;
	private Long dpi;
	private String bitdepth;
	private String pagermode;
	private String duplex;
	private String brightness;
	private String contrast;
	private String autobright;
	private String automaticdeskew;
	private String isblackedge;
	private String isblank;
	private String blankvalue;
	private Long twVersion;

	// Constructors

	/** default constructor */
	public EsTwainDef() {
	}

	/** minimal constructor */
	public EsTwainDef(String id, String pages, Long dpi, String bitdepth,
			String pagermode, String duplex) {
		this.id = id;
		this.pages = pages;
		this.dpi = dpi;
		this.bitdepth = bitdepth;
		this.pagermode = pagermode;
		this.duplex = duplex;
	}

	/** full constructor */
	public EsTwainDef(String id, String defsettingcode, String defsettingname,
			String pages, Long dpi, String bitdepth, String pagermode,
			String duplex, String brightness, String contrast, String autobright,
			String automaticdeskew, String isblackedge, String isblank,
			String blankvalue, Long twVersion) {
		this.id = id;
		this.defsettingcode = defsettingcode;
		this.defsettingname = defsettingname;
		this.pages = pages;
		this.dpi = dpi;
		this.bitdepth = bitdepth;
		this.pagermode = pagermode;
		this.duplex = duplex;
		this.brightness = brightness;
		this.contrast = contrast;
		this.autobright = autobright;
		this.automaticdeskew = automaticdeskew;
		this.isblackedge = isblackedge;
		this.isblank = isblank;
		this.blankvalue = blankvalue;
		this.twVersion = twVersion;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDefsettingcode() {
		return this.defsettingcode;
	}

	public void setDefsettingcode(String defsettingcode) {
		this.defsettingcode = defsettingcode;
	}

	public String getDefsettingname() {
		return this.defsettingname;
	}

	public void setDefsettingname(String defsettingname) {
		this.defsettingname = defsettingname;
	}

	public String getPages() {
		return this.pages;
	}

	public void setPages(String pages) {
		this.pages = pages;
	}

	public Long getDpi() {
		return this.dpi;
	}

	public void setDpi(Long dpi) {
		this.dpi = dpi;
	}

	public String getBitdepth() {
		return this.bitdepth;
	}

	public void setBitdepth(String bitdepth) {
		this.bitdepth = bitdepth;
	}

	public String getPagermode() {
		return this.pagermode;
	}

	public void setPagermode(String pagermode) {
		this.pagermode = pagermode;
	}

	public String getDuplex() {
		return this.duplex;
	}

	public void setDuplex(String duplex) {
		this.duplex = duplex;
	}

	public String getBrightness() {
		return this.brightness;
	}

	public void setBrightness(String brightness) {
		this.brightness = brightness;
	}

	public String getContrast() {
		return this.contrast;
	}

	public void setContrast(String contrast) {
		this.contrast = contrast;
	}

	public String getAutobright() {
		return this.autobright;
	}

	public void setAutobright(String autobright) {
		this.autobright = autobright;
	}

	public String getAutomaticdeskew() {
		return this.automaticdeskew;
	}

	public void setAutomaticdeskew(String automaticdeskew) {
		this.automaticdeskew = automaticdeskew;
	}

	public String getIsblackedge() {
		return this.isblackedge;
	}

	public void setIsblackedge(String isblackedge) {
		this.isblackedge = isblackedge;
	}

	public String getIsblank() {
		return this.isblank;
	}

	public void setIsblank(String isblank) {
		this.isblank = isblank;
	}

	public String getBlankvalue() {
		return this.blankvalue;
	}

	public void setBlankvalue(String blankvalue) {
		this.blankvalue = blankvalue;
	}

	public Long getTwVersion() {
		return this.twVersion;
	}

	public void setTwVersion(Long twVersion) {
		this.twVersion = twVersion;
	}

}