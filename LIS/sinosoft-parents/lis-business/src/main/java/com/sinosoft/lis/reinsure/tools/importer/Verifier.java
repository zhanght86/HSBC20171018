

// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(5) braces fieldsfirst noctor nonlb space lnc 
// Source File Name:   Verifier.java

package com.sinosoft.lis.reinsure.tools.importer;

public final class Verifier {

	private Verifier() {
	}

	public static final String checkAttributeName(String name) {
		String reason;
		/* 116 */if ((reason = checkXMLName(name)) != null) {
			/* 117 */return reason;
		}
		/* 121 */if (name.equals("xml:space") || name.equals("xml:lang")) {
			/* 123 */return null;
		}
		/* 128 */if (name.indexOf(":") != -1) {
			/* 129 */return "Attribute names cannot contain colons";
		} else {
			/* 133 */return null;
		}
	}

	public static final String checkCharacterData(String text) {
		/* 159 */for (int i = 0; i < text.length(); i++) {
			/* 160 */if (!isXMLCharacter(text.charAt(i))) {
				/* 164 */return "0x" + Integer.toHexString(text.charAt(i))
						+ " is not a legal XML character";
			}
		}

		/* 170 */return null;
	}

	public static final String checkCommentData(String data) {
		/* 310 */if (data == null) {
			/* 311 */return "Comments cannot be null";
		}
		/* 314 */if (data.indexOf("--") != -1) {
			/* 315 */return "Comments cannot contain double hyphens (--)";
		} else {
			/* 319 */return null;
		}
	}

	public static final String checkElementName(String name) {
		String reason;
		/* 90 */if ((reason = checkXMLName(name)) != null) {
			/* 91 */return reason;
		}
		/* 95 */if (name.indexOf(":") != -1) {
			/* 96 */return "Element names cannot contain colons";
		} else {
			/* 100 */return null;
		}
	}

	public static final String checkNamespacePrefix(String prefix) {
		/* 185 */if (prefix == null || prefix.equals("")) {
			/* 186 */return null;
		}
		/* 190 */char first = prefix.charAt(0);
		/* 191 */if (isXMLDigit(first)) {
			/* 192 */return "Namespace prefixes cannot begin with a number";
		}
		/* 195 */if (first == '$') {
			/* 196 */return "Namespace prefixes cannot begin with a dollar sign ($)";
		}
		/* 199 */if (first == '-') {
			/* 200 */return "Namespace prefixes cannot begin with a hyphen (-)";
		}
		/* 203 */if (first == '.') {
			/* 204 */return "Namespace prefixes cannot begin with a period (.)";
		}
		/* 207 */if (prefix.toLowerCase().startsWith("xml")) {
			/* 208 */return "Namespace prefixes cannot begin with \"xml\" in any combination of case";
		}
		/* 213 */int i = 0;
		/* 213 */for (int len = prefix.length(); i < len; i++) {
			/* 214 */char c = prefix.charAt(i);
			/* 215 */if (!isXMLNameCharacter(c)) {
				/* 216 */return "Namespace prefixes cannot contain the character \""
						+ c + "\"";
			}
		}

		/* 222 */if (prefix.indexOf(":") != -1) {
			/* 223 */return "Namespace prefixes cannot contain colons";
		} else {
			/* 227 */return null;
		}
	}

	public static final String checkNamespaceURI(String uri) {
		/* 245 */if (uri == null || uri.equals("")) {
			/* 246 */return null;
		}
		/* 250 */char first = uri.charAt(0);
		/* 251 */if (Character.isDigit(first)) {
			/* 252 */return "Namespace URIs cannot begin with a number";
		}
		/* 255 */if (first == '$') {
			/* 256 */return "Namespace URIs cannot begin with a dollar sign ($)";
		}
		/* 259 */if (first == '-') {
			/* 260 */return "Namespace URIs cannot begin with a hyphen (-)";
		} else {
			/* 264 */return null;
		}
	}

	public static final String checkProcessingInstructionTarget(String target) {
		String reason;
		/* 280 */if ((reason = checkXMLName(target)) != null) {
			/* 281 */return reason;
		}
		/* 285 */if (target.indexOf(":") != -1) {
			/* 286 */return "Processing instruction targets cannot contain colons";
		}
		/* 290 */if (target.equalsIgnoreCase("xml")) {
			/* 291 */return "Processing instructions cannot have a target of \"xml\" in any combination of case";
		} else {
			/* 296 */return null;
		}
	}

	private static String checkXMLName(String name) {
		/* 334 */if (name == null || name.length() == 0
				|| name.trim().equals("")) {
			/* 336 */return "XML names cannot be null or empty";
		}
		/* 341 */char first = name.charAt(0);
		/* 342 */if (!isXMLNameStartCharacter(first)) {
			/* 343 */return "XML names cannot begin with the character \""
					+ first + "\"";
		}
		/* 347 */int i = 0;
		/* 347 */for (int len = name.length(); i < len; i++) {
			/* 348 */char c = name.charAt(i);
			/* 349 */if (!isXMLNameCharacter(c)) {
				/* 350 */return "XML names cannot contain the character \"" + c
						+ "\"";
			}
		}

		/* 355 */return null;
	}

	public static boolean isXMLCharacter(char c) {
		/* 372 */if (c >= ' ' && c <= '\uD7FF') {
			/* 372 */return true;
		}
		/* 373 */if (c >= '\uE000' && c <= '\uFFFD') {
			/* 373 */return true;
		}
		/* 374 */if (c >= '\0' && c <= '\0') {
			/* 374 */return true;
		}
		/* 376 */if (c == '\n') {
			/* 376 */return true;
		}
		/* 377 */if (c == '\r') {
			/* 377 */return true;
		}
		/* 378 */return c == '\t';
	}

	public static boolean isXMLCombiningChar(char c) {
		/* 677 */if (c >= '\u0300' && c <= '\u0345') {
			/* 677 */return true;
		}
		/* 678 */if (c >= '\u0360' && c <= '\u0361') {
			/* 678 */return true;
		}
		/* 679 */if (c >= '\u0483' && c <= '\u0486') {
			/* 679 */return true;
		}
		/* 680 */if (c >= '\u0591' && c <= '\u05A1') {
			/* 680 */return true;
		}
		/* 682 */if (c >= '\u05A3' && c <= '\u05B9') {
			/* 682 */return true;
		}
		/* 683 */if (c >= '\u05BB' && c <= '\u05BD') {
			/* 683 */return true;
		}
		/* 684 */if (c == '\u05BF') {
			/* 684 */return true;
		}
		/* 685 */if (c >= '\u05C1' && c <= '\u05C2') {
			/* 685 */return true;
		}
		/* 687 */if (c == '\u05C4') {
			/* 687 */return true;
		}
		/* 688 */if (c >= '\u064B' && c <= '\u0652') {
			/* 688 */return true;
		}
		/* 689 */if (c == '\u0670') {
			/* 689 */return true;
		}
		/* 690 */if (c >= '\u06D6' && c <= '\u06DC') {
			/* 690 */return true;
		}
		/* 692 */if (c >= '\u06DD' && c <= '\u06DF') {
			/* 692 */return true;
		}
		/* 693 */if (c >= '\u06E0' && c <= '\u06E4') {
			/* 693 */return true;
		}
		/* 694 */if (c >= '\u06E7' && c <= '\u06E8') {
			/* 694 */return true;
		}
		/* 696 */if (c >= '\u06EA' && c <= '\u06ED') {
			/* 696 */return true;
		}
		/* 697 */if (c >= '\u0901' && c <= '\u0903') {
			/* 697 */return true;
		}
		/* 698 */if (c == '\u093C') {
			/* 698 */return true;
		}
		/* 699 */if (c >= '\u093E' && c <= '\u094C') {
			/* 699 */return true;
		}
		/* 701 */if (c == '\u094D') {
			/* 701 */return true;
		}
		/* 702 */if (c >= '\u0951' && c <= '\u0954') {
			/* 702 */return true;
		}
		/* 703 */if (c >= '\u0962' && c <= '\u0963') {
			/* 703 */return true;
		}
		/* 704 */if (c >= '\u0981' && c <= '\u0983') {
			/* 704 */return true;
		}
		/* 706 */if (c == '\u09BC') {
			/* 706 */return true;
		}
		/* 707 */if (c == '\u09BE') {
			/* 707 */return true;
		}
		/* 708 */if (c == '\u09BF') {
			/* 708 */return true;
		}
		/* 709 */if (c >= '\u09C0' && c <= '\u09C4') {
			/* 709 */return true;
		}
		/* 710 */if (c >= '\u09C7' && c <= '\u09C8') {
			/* 710 */return true;
		}
		/* 712 */if (c >= '\u09CB' && c <= '\u09CD') {
			/* 712 */return true;
		}
		/* 713 */if (c == '\u09D7') {
			/* 713 */return true;
		}
		/* 714 */if (c >= '\u09E2' && c <= '\u09E3') {
			/* 714 */return true;
		}
		/* 715 */if (c == '\u0A02') {
			/* 715 */return true;
		}
		/* 716 */if (c == '\u0A3C') {
			/* 716 */return true;
		}
		/* 718 */if (c == '\u0A3E') {
			/* 718 */return true;
		}
		/* 719 */if (c == '\u0A3F') {
			/* 719 */return true;
		}
		/* 720 */if (c >= '\u0A40' && c <= '\u0A42') {
			/* 720 */return true;
		}
		/* 721 */if (c >= '\u0A47' && c <= '\u0A48') {
			/* 721 */return true;
		}
		/* 723 */if (c >= '\u0A4B' && c <= '\u0A4D') {
			/* 723 */return true;
		}
		/* 724 */if (c >= '\u0A70' && c <= '\u0A71') {
			/* 724 */return true;
		}
		/* 725 */if (c >= '\u0A81' && c <= '\u0A83') {
			/* 725 */return true;
		}
		/* 726 */if (c == '\u0ABC') {
			/* 726 */return true;
		}
		/* 728 */if (c >= '\u0ABE' && c <= '\u0AC5') {
			/* 728 */return true;
		}
		/* 729 */if (c >= '\u0AC7' && c <= '\u0AC9') {
			/* 729 */return true;
		}
		/* 730 */if (c >= '\u0ACB' && c <= '\u0ACD') {
			/* 730 */return true;
		}
		/* 732 */if (c >= '\u0B01' && c <= '\u0B03') {
			/* 732 */return true;
		}
		/* 733 */if (c == '\u0B3C') {
			/* 733 */return true;
		}
		/* 734 */if (c >= '\u0B3E' && c <= '\u0B43') {
			/* 734 */return true;
		}
		/* 735 */if (c >= '\u0B47' && c <= '\u0B48') {
			/* 735 */return true;
		}
		/* 737 */if (c >= '\u0B4B' && c <= '\u0B4D') {
			/* 737 */return true;
		}
		/* 738 */if (c >= '\u0B56' && c <= '\u0B57') {
			/* 738 */return true;
		}
		/* 739 */if (c >= '\u0B82' && c <= '\u0B83') {
			/* 739 */return true;
		}
		/* 741 */if (c >= '\u0BBE' && c <= '\u0BC2') {
			/* 741 */return true;
		}
		/* 742 */if (c >= '\u0BC6' && c <= '\u0BC8') {
			/* 742 */return true;
		}
		/* 743 */if (c >= '\u0BCA' && c <= '\u0BCD') {
			/* 743 */return true;
		}
		/* 744 */if (c == '\u0BD7') {
			/* 744 */return true;
		}
		/* 746 */if (c >= '\u0C01' && c <= '\u0C03') {
			/* 746 */return true;
		}
		/* 747 */if (c >= '\u0C3E' && c <= '\u0C44') {
			/* 747 */return true;
		}
		/* 748 */if (c >= '\u0C46' && c <= '\u0C48') {
			/* 748 */return true;
		}
		/* 750 */if (c >= '\u0C4A' && c <= '\u0C4D') {
			/* 750 */return true;
		}
		/* 751 */if (c >= '\u0C55' && c <= '\u0C56') {
			/* 751 */return true;
		}
		/* 752 */if (c >= '\u0C82' && c <= '\u0C83') {
			/* 752 */return true;
		}
		/* 754 */if (c >= '\u0CBE' && c <= '\u0CC4') {
			/* 754 */return true;
		}
		/* 755 */if (c >= '\u0CC6' && c <= '\u0CC8') {
			/* 755 */return true;
		}
		/* 756 */if (c >= '\u0CCA' && c <= '\u0CCD') {
			/* 756 */return true;
		}
		/* 758 */if (c >= '\u0CD5' && c <= '\u0CD6') {
			/* 758 */return true;
		}
		/* 759 */if (c >= '\u0D02' && c <= '\u0D03') {
			/* 759 */return true;
		}
		/* 760 */if (c >= '\u0D3E' && c <= '\u0D43') {
			/* 760 */return true;
		}
		/* 762 */if (c >= '\u0D46' && c <= '\u0D48') {
			/* 762 */return true;
		}
		/* 763 */if (c >= '\u0D4A' && c <= '\u0D4D') {
			/* 763 */return true;
		}
		/* 764 */if (c == '\u0D57') {
			/* 764 */return true;
		}
		/* 765 */if (c == '\u0E31') {
			/* 765 */return true;
		}
		/* 767 */if (c >= '\u0E34' && c <= '\u0E3A') {
			/* 767 */return true;
		}
		/* 768 */if (c >= '\u0E47' && c <= '\u0E4E') {
			/* 768 */return true;
		}
		/* 769 */if (c == '\u0EB1') {
			/* 769 */return true;
		}
		/* 770 */if (c >= '\u0EB4' && c <= '\u0EB9') {
			/* 770 */return true;
		}
		/* 772 */if (c >= '\u0EBB' && c <= '\u0EBC') {
			/* 772 */return true;
		}
		/* 773 */if (c >= '\u0EC8' && c <= '\u0ECD') {
			/* 773 */return true;
		}
		/* 774 */if (c >= '\u0F18' && c <= '\u0F19') {
			/* 774 */return true;
		}
		/* 775 */if (c == '\u0F35') {
			/* 775 */return true;
		}
		/* 777 */if (c == '\u0F37') {
			/* 777 */return true;
		}
		/* 778 */if (c == '\u0F39') {
			/* 778 */return true;
		}
		/* 779 */if (c == '\u0F3E') {
			/* 779 */return true;
		}
		/* 780 */if (c == '\u0F3F') {
			/* 780 */return true;
		}
		/* 781 */if (c >= '\u0F71' && c <= '\u0F84') {
			/* 781 */return true;
		}
		/* 783 */if (c >= '\u0F86' && c <= '\u0F8B') {
			/* 783 */return true;
		}
		/* 784 */if (c >= '\u0F90' && c <= '\u0F95') {
			/* 784 */return true;
		}
		/* 785 */if (c == '\u0F97') {
			/* 785 */return true;
		}
		/* 786 */if (c >= '\u0F99' && c <= '\u0FAD') {
			/* 786 */return true;
		}
		/* 788 */if (c >= '\u0FB1' && c <= '\u0FB7') {
			/* 788 */return true;
		}
		/* 789 */if (c == '\u0FB9') {
			/* 789 */return true;
		}
		/* 790 */if (c >= '\u20D0' && c <= '\u20DC') {
			/* 790 */return true;
		}
		/* 791 */if (c == '\u20E1') {
			/* 791 */return true;
		}
		/* 793 */if (c >= '\u302A' && c <= '\u302F') {
			/* 793 */return true;
		}
		/* 794 */if (c == '\u3099') {
			/* 794 */return true;
		}
		/* 795 */return c == '\u309A';
	}

	public static boolean isXMLDigit(char c) {
		/* 842 */if (c >= '0' && c <= '9') {
			/* 842 */return true;
		}
		/* 843 */if (c >= '\u0660' && c <= '\u0669') {
			/* 843 */return true;
		}
		/* 844 */if (c >= '\u06F0' && c <= '\u06F9') {
			/* 844 */return true;
		}
		/* 845 */if (c >= '\u0966' && c <= '\u096F') {
			/* 845 */return true;
		}
		/* 847 */if (c >= '\u09E6' && c <= '\u09EF') {
			/* 847 */return true;
		}
		/* 848 */if (c >= '\u0A66' && c <= '\u0A6F') {
			/* 848 */return true;
		}
		/* 849 */if (c >= '\u0AE6' && c <= '\u0AEF') {
			/* 849 */return true;
		}
		/* 851 */if (c >= '\u0B66' && c <= '\u0B6F') {
			/* 851 */return true;
		}
		/* 852 */if (c >= '\u0BE7' && c <= '\u0BEF') {
			/* 852 */return true;
		}
		/* 853 */if (c >= '\u0C66' && c <= '\u0C6F') {
			/* 853 */return true;
		}
		/* 855 */if (c >= '\u0CE6' && c <= '\u0CEF') {
			/* 855 */return true;
		}
		/* 856 */if (c >= '\u0D66' && c <= '\u0D6F') {
			/* 856 */return true;
		}
		/* 857 */if (c >= '\u0E50' && c <= '\u0E59') {
			/* 857 */return true;
		}
		/* 859 */if (c >= '\u0ED0' && c <= '\u0ED9') {
			/* 859 */return true;
		}
		/* 860 */return c >= '\u0F20' && c <= '\u0F29';
	}

	public static boolean isXMLExtender(char c) {
		/* 813 */if (c == '\267') {
			/* 813 */return true;
		}
		/* 814 */if (c == '\u02D0') {
			/* 814 */return true;
		}
		/* 815 */if (c == '\u02D1') {
			/* 815 */return true;
		}
		/* 816 */if (c == '\u0387') {
			/* 816 */return true;
		}
		/* 817 */if (c == '\u0640') {
			/* 817 */return true;
		}
		/* 818 */if (c == '\u0E46') {
			/* 818 */return true;
		}
		/* 819 */if (c == '\u0EC6') {
			/* 819 */return true;
		}
		/* 820 */if (c == '\u3005') {
			/* 820 */return true;
		}
		/* 822 */if (c >= '\u3031' && c <= '\u3035') {
			/* 822 */return true;
		}
		/* 823 */if (c >= '\u309D' && c <= '\u309E') {
			/* 823 */return true;
		}
		/* 824 */return c >= '\u30FC' && c <= '\u30FE';
	}

	public static boolean isXMLLetter(char c) {
		/* 452 */if (c >= 'A' && c <= 'Z') {
			/* 452 */return true;
		}
		/* 453 */if (c >= 'a' && c <= 'z') {
			/* 453 */return true;
		}
		/* 454 */if (c >= '\300' && c <= '\326') {
			/* 454 */return true;
		}
		/* 455 */if (c >= '\330' && c <= '\366') {
			/* 455 */return true;
		}
		/* 456 */if (c >= '\370' && c <= '\377') {
			/* 456 */return true;
		}
		/* 457 */if (c >= '\u0100' && c <= '\u0131') {
			/* 457 */return true;
		}
		/* 458 */if (c >= '\u0134' && c <= '\u013E') {
			/* 458 */return true;
		}
		/* 459 */if (c >= '\u0141' && c <= '\u0148') {
			/* 459 */return true;
		}
		/* 460 */if (c >= '\u014A' && c <= '\u017E') {
			/* 460 */return true;
		}
		/* 461 */if (c >= '\u0180' && c <= '\u01C3') {
			/* 461 */return true;
		}
		/* 462 */if (c >= '\u01CD' && c <= '\u01F0') {
			/* 462 */return true;
		}
		/* 463 */if (c >= '\u01F4' && c <= '\u01F5') {
			/* 463 */return true;
		}
		/* 464 */if (c >= '\u01FA' && c <= '\u0217') {
			/* 464 */return true;
		}
		/* 465 */if (c >= '\u0250' && c <= '\u02A8') {
			/* 465 */return true;
		}
		/* 466 */if (c >= '\u02BB' && c <= '\u02C1') {
			/* 466 */return true;
		}
		/* 467 */if (c >= '\u0388' && c <= '\u038A') {
			/* 467 */return true;
		}
		/* 468 */if (c == '\u0386') {
			/* 468 */return true;
		}
		/* 469 */if (c == '\u038C') {
			/* 469 */return true;
		}
		/* 470 */if (c >= '\u038E' && c <= '\u03A1') {
			/* 470 */return true;
		}
		/* 471 */if (c >= '\u03A3' && c <= '\u03CE') {
			/* 471 */return true;
		}
		/* 472 */if (c >= '\u03D0' && c <= '\u03D6') {
			/* 472 */return true;
		}
		/* 473 */if (c == '\u03DA') {
			/* 473 */return true;
		}
		/* 474 */if (c == '\u03DC') {
			/* 474 */return true;
		}
		/* 475 */if (c == '\u03DE') {
			/* 475 */return true;
		}
		/* 476 */if (c == '\u03E0') {
			/* 476 */return true;
		}
		/* 477 */if (c >= '\u03E2' && c <= '\u03F3') {
			/* 477 */return true;
		}
		/* 478 */if (c >= '\u0401' && c <= '\u040C') {
			/* 478 */return true;
		}
		/* 479 */if (c >= '\u040E' && c <= '\u044F') {
			/* 479 */return true;
		}
		/* 480 */if (c >= '\u0451' && c <= '\u045C') {
			/* 480 */return true;
		}
		/* 481 */if (c >= '\u045E' && c <= '\u0481') {
			/* 481 */return true;
		}
		/* 482 */if (c >= '\u0490' && c <= '\u04C4') {
			/* 482 */return true;
		}
		/* 483 */if (c >= '\u04C7' && c <= '\u04C8') {
			/* 483 */return true;
		}
		/* 484 */if (c >= '\u04CB' && c <= '\u04CC') {
			/* 484 */return true;
		}
		/* 485 */if (c >= '\u04D0' && c <= '\u04EB') {
			/* 485 */return true;
		}
		/* 486 */if (c >= '\u04EE' && c <= '\u04F5') {
			/* 486 */return true;
		}
		/* 487 */if (c >= '\u04F8' && c <= '\u04F9') {
			/* 487 */return true;
		}
		/* 488 */if (c >= '\u0531' && c <= '\u0556') {
			/* 488 */return true;
		}
		/* 489 */if (c == '\u0559') {
			/* 489 */return true;
		}
		/* 490 */if (c >= '\u0561' && c <= '\u0586') {
			/* 490 */return true;
		}
		/* 491 */if (c >= '\u05D0' && c <= '\u05EA') {
			/* 491 */return true;
		}
		/* 492 */if (c >= '\u05F0' && c <= '\u05F2') {
			/* 492 */return true;
		}
		/* 493 */if (c >= '\u0621' && c <= '\u063A') {
			/* 493 */return true;
		}
		/* 494 */if (c >= '\u0641' && c <= '\u064A') {
			/* 494 */return true;
		}
		/* 495 */if (c >= '\u0671' && c <= '\u06B7') {
			/* 495 */return true;
		}
		/* 496 */if (c >= '\u06BA' && c <= '\u06BE') {
			/* 496 */return true;
		}
		/* 497 */if (c >= '\u06C0' && c <= '\u06CE') {
			/* 497 */return true;
		}
		/* 498 */if (c >= '\u06D0' && c <= '\u06D3') {
			/* 498 */return true;
		}
		/* 499 */if (c == '\u06D5') {
			/* 499 */return true;
		}
		/* 500 */if (c >= '\u06E5' && c <= '\u06E6') {
			/* 500 */return true;
		}
		/* 501 */if (c >= '\u0905' && c <= '\u0939') {
			/* 501 */return true;
		}
		/* 502 */if (c == '\u093D') {
			/* 502 */return true;
		}
		/* 503 */if (c >= '\u0958' && c <= '\u0961') {
			/* 503 */return true;
		}
		/* 504 */if (c >= '\u0985' && c <= '\u098C') {
			/* 504 */return true;
		}
		/* 505 */if (c >= '\u098F' && c <= '\u0990') {
			/* 505 */return true;
		}
		/* 506 */if (c >= '\u0993' && c <= '\u09A8') {
			/* 506 */return true;
		}
		/* 507 */if (c >= '\u09AA' && c <= '\u09B0') {
			/* 507 */return true;
		}
		/* 508 */if (c == '\u09B2') {
			/* 508 */return true;
		}
		/* 509 */if (c >= '\u09B6' && c <= '\u09B9') {
			/* 509 */return true;
		}
		/* 510 */if (c >= '\u09DC' && c <= '\u09DD') {
			/* 510 */return true;
		}
		/* 511 */if (c >= '\u09DF' && c <= '\u09E1') {
			/* 511 */return true;
		}
		/* 512 */if (c >= '\u09F0' && c <= '\u09F1') {
			/* 512 */return true;
		}
		/* 513 */if (c >= '\u0A05' && c <= '\u0A0A') {
			/* 513 */return true;
		}
		/* 514 */if (c >= '\u0A0F' && c <= '\u0A10') {
			/* 514 */return true;
		}
		/* 515 */if (c >= '\u0A13' && c <= '\u0A28') {
			/* 515 */return true;
		}
		/* 516 */if (c >= '\u0A2A' && c <= '\u0A30') {
			/* 516 */return true;
		}
		/* 517 */if (c >= '\u0A32' && c <= '\u0A33') {
			/* 517 */return true;
		}
		/* 518 */if (c >= '\u0A35' && c <= '\u0A36') {
			/* 518 */return true;
		}
		/* 519 */if (c >= '\u0A38' && c <= '\u0A39') {
			/* 519 */return true;
		}
		/* 520 */if (c >= '\u0A59' && c <= '\u0A5C') {
			/* 520 */return true;
		}
		/* 521 */if (c == '\u0A5E') {
			/* 521 */return true;
		}
		/* 522 */if (c >= '\u0A72' && c <= '\u0A74') {
			/* 522 */return true;
		}
		/* 523 */if (c >= '\u0A85' && c <= '\u0A8B') {
			/* 523 */return true;
		}
		/* 524 */if (c == '\u0A8D') {
			/* 524 */return true;
		}
		/* 525 */if (c >= '\u0A8F' && c <= '\u0A91') {
			/* 525 */return true;
		}
		/* 526 */if (c >= '\u0A93' && c <= '\u0AA8') {
			/* 526 */return true;
		}
		/* 527 */if (c >= '\u0AAA' && c <= '\u0AB0') {
			/* 527 */return true;
		}
		/* 528 */if (c >= '\u0AB2' && c <= '\u0AB3') {
			/* 528 */return true;
		}
		/* 529 */if (c >= '\u0AB5' && c <= '\u0AB9') {
			/* 529 */return true;
		}
		/* 530 */if (c == '\u0ABD') {
			/* 530 */return true;
		}
		/* 531 */if (c == '\u0AE0') {
			/* 531 */return true;
		}
		/* 532 */if (c >= '\u0B05' && c <= '\u0B0C') {
			/* 532 */return true;
		}
		/* 533 */if (c >= '\u0B0F' && c <= '\u0B10') {
			/* 533 */return true;
		}
		/* 534 */if (c >= '\u0B13' && c <= '\u0B28') {
			/* 534 */return true;
		}
		/* 535 */if (c >= '\u0B2A' && c <= '\u0B30') {
			/* 535 */return true;
		}
		/* 536 */if (c >= '\u0B32' && c <= '\u0B33') {
			/* 536 */return true;
		}
		/* 537 */if (c >= '\u0B36' && c <= '\u0B39') {
			/* 537 */return true;
		}
		/* 538 */if (c == '\u0B3D') {
			/* 538 */return true;
		}
		/* 539 */if (c >= '\u0B5C' && c <= '\u0B5D') {
			/* 539 */return true;
		}
		/* 540 */if (c >= '\u0B5F' && c <= '\u0B61') {
			/* 540 */return true;
		}
		/* 541 */if (c >= '\u0B85' && c <= '\u0B8A') {
			/* 541 */return true;
		}
		/* 542 */if (c >= '\u0B8E' && c <= '\u0B90') {
			/* 542 */return true;
		}
		/* 543 */if (c >= '\u0B92' && c <= '\u0B95') {
			/* 543 */return true;
		}
		/* 544 */if (c >= '\u0B99' && c <= '\u0B9A') {
			/* 544 */return true;
		}
		/* 545 */if (c == '\u0B9C') {
			/* 545 */return true;
		}
		/* 546 */if (c >= '\u0B9E' && c <= '\u0B9F') {
			/* 546 */return true;
		}
		/* 547 */if (c >= '\u0BA3' && c <= '\u0BA4') {
			/* 547 */return true;
		}
		/* 548 */if (c >= '\u0BA8' && c <= '\u0BAA') {
			/* 548 */return true;
		}
		/* 549 */if (c >= '\u0BAE' && c <= '\u0BB5') {
			/* 549 */return true;
		}
		/* 550 */if (c >= '\u0BB7' && c <= '\u0BB9') {
			/* 550 */return true;
		}
		/* 551 */if (c >= '\u0C05' && c <= '\u0C0C') {
			/* 551 */return true;
		}
		/* 552 */if (c >= '\u0C0E' && c <= '\u0C10') {
			/* 552 */return true;
		}
		/* 553 */if (c >= '\u0C12' && c <= '\u0C28') {
			/* 553 */return true;
		}
		/* 554 */if (c >= '\u0C2A' && c <= '\u0C33') {
			/* 554 */return true;
		}
		/* 555 */if (c >= '\u0C35' && c <= '\u0C39') {
			/* 555 */return true;
		}
		/* 556 */if (c >= '\u0C60' && c <= '\u0C61') {
			/* 556 */return true;
		}
		/* 557 */if (c >= '\u0C85' && c <= '\u0C8C') {
			/* 557 */return true;
		}
		/* 558 */if (c >= '\u0C8E' && c <= '\u0C90') {
			/* 558 */return true;
		}
		/* 559 */if (c >= '\u0C92' && c <= '\u0CA8') {
			/* 559 */return true;
		}
		/* 560 */if (c >= '\u0CAA' && c <= '\u0CB3') {
			/* 560 */return true;
		}
		/* 561 */if (c >= '\u0CB5' && c <= '\u0CB9') {
			/* 561 */return true;
		}
		/* 562 */if (c == '\u0CDE') {
			/* 562 */return true;
		}
		/* 563 */if (c >= '\u0CE0' && c <= '\u0CE1') {
			/* 563 */return true;
		}
		/* 564 */if (c >= '\u0D05' && c <= '\u0D0C') {
			/* 564 */return true;
		}
		/* 565 */if (c >= '\u0D0E' && c <= '\u0D10') {
			/* 565 */return true;
		}
		/* 566 */if (c >= '\u0D12' && c <= '\u0D28') {
			/* 566 */return true;
		}
		/* 567 */if (c >= '\u0D2A' && c <= '\u0D39') {
			/* 567 */return true;
		}
		/* 568 */if (c >= '\u0D60' && c <= '\u0D61') {
			/* 568 */return true;
		}
		/* 569 */if (c >= '\u0E01' && c <= '\u0E2E') {
			/* 569 */return true;
		}
		/* 570 */if (c == '\u0E30') {
			/* 570 */return true;
		}
		/* 571 */if (c >= '\u0E32' && c <= '\u0E33') {
			/* 571 */return true;
		}
		/* 572 */if (c >= '\u0E40' && c <= '\u0E45') {
			/* 572 */return true;
		}
		/* 573 */if (c >= '\u0E81' && c <= '\u0E82') {
			/* 573 */return true;
		}
		/* 574 */if (c == '\u0E84') {
			/* 574 */return true;
		}
		/* 575 */if (c >= '\u0E87' && c <= '\u0E88') {
			/* 575 */return true;
		}
		/* 576 */if (c == '\u0E8A') {
			/* 576 */return true;
		}
		/* 577 */if (c == '\u0E8D') {
			/* 577 */return true;
		}
		/* 578 */if (c >= '\u0E94' && c <= '\u0E97') {
			/* 578 */return true;
		}
		/* 579 */if (c >= '\u0E99' && c <= '\u0E9F') {
			/* 579 */return true;
		}
		/* 580 */if (c >= '\u0EA1' && c <= '\u0EA3') {
			/* 580 */return true;
		}
		/* 581 */if (c == '\u0EA5') {
			/* 581 */return true;
		}
		/* 582 */if (c == '\u0EA7') {
			/* 582 */return true;
		}
		/* 583 */if (c >= '\u0EAA' && c <= '\u0EAB') {
			/* 583 */return true;
		}
		/* 584 */if (c >= '\u0EAD' && c <= '\u0EAE') {
			/* 584 */return true;
		}
		/* 585 */if (c == '\u0EB0') {
			/* 585 */return true;
		}
		/* 586 */if (c >= '\u0EB2' && c <= '\u0EB3') {
			/* 586 */return true;
		}
		/* 587 */if (c == '\u0EBD') {
			/* 587 */return true;
		}
		/* 588 */if (c >= '\u0EC0' && c <= '\u0EC4') {
			/* 588 */return true;
		}
		/* 589 */if (c >= '\u0F40' && c <= '\u0F47') {
			/* 589 */return true;
		}
		/* 590 */if (c >= '\u0F49' && c <= '\u0F69') {
			/* 590 */return true;
		}
		/* 591 */if (c >= '\u10A0' && c <= '\u10C5') {
			/* 591 */return true;
		}
		/* 592 */if (c >= '\u10D0' && c <= '\u10F6') {
			/* 592 */return true;
		}
		/* 593 */if (c == '\u1100') {
			/* 593 */return true;
		}
		/* 594 */if (c >= '\u1102' && c <= '\u1103') {
			/* 594 */return true;
		}
		/* 595 */if (c >= '\u1105' && c <= '\u1107') {
			/* 595 */return true;
		}
		/* 596 */if (c == '\u1109') {
			/* 596 */return true;
		}
		/* 597 */if (c >= '\u110B' && c <= '\u110C') {
			/* 597 */return true;
		}
		/* 598 */if (c >= '\u110E' && c <= '\u1112') {
			/* 598 */return true;
		}
		/* 599 */if (c == '\u113C') {
			/* 599 */return true;
		}
		/* 600 */if (c == '\u113E') {
			/* 600 */return true;
		}
		/* 601 */if (c == '\u1140') {
			/* 601 */return true;
		}
		/* 602 */if (c == '\u114C') {
			/* 602 */return true;
		}
		/* 603 */if (c == '\u114E') {
			/* 603 */return true;
		}
		/* 604 */if (c == '\u1150') {
			/* 604 */return true;
		}
		/* 605 */if (c >= '\u1154' && c <= '\u1155') {
			/* 605 */return true;
		}
		/* 606 */if (c == '\u1159') {
			/* 606 */return true;
		}
		/* 607 */if (c >= '\u115F' && c <= '\u1161') {
			/* 607 */return true;
		}
		/* 608 */if (c == '\u1163') {
			/* 608 */return true;
		}
		/* 609 */if (c == '\u1165') {
			/* 609 */return true;
		}
		/* 610 */if (c == '\u1167') {
			/* 610 */return true;
		}
		/* 611 */if (c == '\u1169') {
			/* 611 */return true;
		}
		/* 612 */if (c >= '\u116D' && c <= '\u116E') {
			/* 612 */return true;
		}
		/* 613 */if (c >= '\u1172' && c <= '\u1173') {
			/* 613 */return true;
		}
		/* 614 */if (c == '\u1175') {
			/* 614 */return true;
		}
		/* 615 */if (c == '\u119E') {
			/* 615 */return true;
		}
		/* 616 */if (c == '\u11A8') {
			/* 616 */return true;
		}
		/* 617 */if (c == '\u11AB') {
			/* 617 */return true;
		}
		/* 618 */if (c >= '\u11AE' && c <= '\u11AF') {
			/* 618 */return true;
		}
		/* 619 */if (c >= '\u11B7' && c <= '\u11B8') {
			/* 619 */return true;
		}
		/* 620 */if (c == '\u11BA') {
			/* 620 */return true;
		}
		/* 621 */if (c >= '\u11BC' && c <= '\u11C2') {
			/* 621 */return true;
		}
		/* 622 */if (c == '\u11EB') {
			/* 622 */return true;
		}
		/* 623 */if (c == '\u11F0') {
			/* 623 */return true;
		}
		/* 624 */if (c == '\u11F9') {
			/* 624 */return true;
		}
		/* 625 */if (c >= '\u1E00' && c <= '\u1E9B') {
			/* 625 */return true;
		}
		/* 626 */if (c >= '\u1EA0' && c <= '\u1EF9') {
			/* 626 */return true;
		}
		/* 627 */if (c >= '\u1F00' && c <= '\u1F15') {
			/* 627 */return true;
		}
		/* 628 */if (c >= '\u1F18' && c <= '\u1F1D') {
			/* 628 */return true;
		}
		/* 629 */if (c >= '\u1F20' && c <= '\u1F45') {
			/* 629 */return true;
		}
		/* 630 */if (c >= '\u1F48' && c <= '\u1F4D') {
			/* 630 */return true;
		}
		/* 631 */if (c >= '\u1F50' && c <= '\u1F57') {
			/* 631 */return true;
		}
		/* 632 */if (c == '\u1F59') {
			/* 632 */return true;
		}
		/* 633 */if (c == '\u1F5B') {
			/* 633 */return true;
		}
		/* 634 */if (c == '\u1F5D') {
			/* 634 */return true;
		}
		/* 635 */if (c >= '\u1F5F' && c <= '\u1F7D') {
			/* 635 */return true;
		}
		/* 636 */if (c >= '\u1F80' && c <= '\u1FB4') {
			/* 636 */return true;
		}
		/* 637 */if (c >= '\u1FB6' && c <= '\u1FBC') {
			/* 637 */return true;
		}
		/* 638 */if (c == '\u1FBE') {
			/* 638 */return true;
		}
		/* 639 */if (c >= '\u1FC2' && c <= '\u1FC4') {
			/* 639 */return true;
		}
		/* 640 */if (c >= '\u1FC6' && c <= '\u1FCC') {
			/* 640 */return true;
		}
		/* 641 */if (c >= '\u1FD0' && c <= '\u1FD3') {
			/* 641 */return true;
		}
		/* 642 */if (c >= '\u1FD6' && c <= '\u1FDB') {
			/* 642 */return true;
		}
		/* 643 */if (c >= '\u1FE0' && c <= '\u1FEC') {
			/* 643 */return true;
		}
		/* 644 */if (c >= '\u1FF2' && c <= '\u1FF4') {
			/* 644 */return true;
		}
		/* 645 */if (c >= '\u1FF6' && c <= '\u1FFC') {
			/* 645 */return true;
		}
		/* 646 */if (c == '\u2126') {
			/* 646 */return true;
		}
		/* 647 */if (c >= '\u212A' && c <= '\u212B') {
			/* 647 */return true;
		}
		/* 648 */if (c == '\u212E') {
			/* 648 */return true;
		}
		/* 649 */if (c >= '\u2180' && c <= '\u2182') {
			/* 649 */return true;
		}
		/* 650 */if (c >= '\u3041' && c <= '\u3094') {
			/* 650 */return true;
		}
		/* 651 */if (c >= '\u30A1' && c <= '\u30FA') {
			/* 651 */return true;
		}
		/* 652 */if (c >= '\u3105' && c <= '\u312C') {
			/* 652 */return true;
		}
		/* 653 */if (c >= '\uAC00' && c <= '\uD7A3') {
			/* 653 */return true;
		}
		/* 656 */if (c >= '\u4E00' && c <= '\u9FA5') {
			/* 656 */return true;
		}
		/* 657 */if (c == '\u3007') {
			/* 657 */return true;
		}
		/* 658 */return c >= '\u3021' && c <= '\u3029';
	}

	public static boolean isXMLLetterOrDigit(char c) {
		/* 436 */return isXMLLetter(c) || isXMLDigit(c);
	}

	public static boolean isXMLNameCharacter(char c) {
		/* 398 */return isXMLLetter(c) || isXMLDigit(c) || c == '.' || c == '-'
				|| c == '_' || c == ':' || isXMLCombiningChar(c)
				|| isXMLExtender(c);
	}

	public static boolean isXMLNameStartCharacter(char c) {
		/* 419 */return isXMLLetter(c) || c == '_' || c == ':';
	}
}
