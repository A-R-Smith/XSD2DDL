package main.java.xsd.type;

public class XSDDecimalType extends XSDSimpleType {
	private int totalDigits;
	private int fractionDigits;
	private int minInclusive;
	private boolean hasMinInclusive;
	
	public int getFractionDigits() {
		return fractionDigits;
	}
	public void setFractionDigits(int fractionDigits) {
		this.fractionDigits = fractionDigits;
	}
	public boolean isHasMinInclusive() {
		return hasMinInclusive;
	}
	public void setHasMinInclusive(boolean hasMinInclusive) {
		this.hasMinInclusive = hasMinInclusive;
	}
	public int getMinInclusive() {
		return minInclusive;
	}
	public void setMinInclusive(int minInclusive) {
		this.minInclusive = minInclusive;
	}
	public int getTotalDigits() {
		return totalDigits;
	}
	public void setTotalDigits(int totalDigits) {
		this.totalDigits = totalDigits;
	}
//	@Override
//	public String getDDLType() {
//		return  "DECIMAL("
//				+totalDigits+","
//				+fractionDigits+")";
//	}
	@Override
	public String getPointNameSuffix() {
		return "-decimal";
	}

}
