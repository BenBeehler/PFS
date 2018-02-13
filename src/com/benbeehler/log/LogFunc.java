package com.benbeehler.log;

import java.math.BigDecimal;

public class LogFunc {

	public static boolean bool = false;
	
	public static void main(String[] args) {
		double base = 10;
		double target = 3;
		
		double findbase = findbase(base, target);
		double exp = increment(base, findbase, target, true);
		
		int i = 0;
		
		while(Math.pow(base, exp) != target) {
			exp = increment(base, exp, target, false);
			
			if(i == 100) {
				break;
			}
			
			i++;
		}
		
		System.out.println("(Estimated) Result: " + exp);
	}
	
	public static double adjust(double val) {
		String str = String.valueOf(val);
		String integer = String.valueOf(str).split("\\.")[0];
		String decimal = String.valueOf(str).split("\\.")[1];
		
		String[] spl = decimal.split("");
		if(spl[0].equals("0")) {
			spl[0] = "";
			decimal = convert(spl);
		}
		
		val = Double.parseDouble(integer + "." + decimal);
		
		return val;
	}
	
	public static double assignEnd(Object base, Object n) {
		if(base == null) {
			return Double.parseDouble("." + String.valueOf(n));
		} else if(base instanceof Double) {
			return Double.parseDouble(String.valueOf(base) + String.valueOf(n));
		} else if(base instanceof Integer) {
			return Double.parseDouble(String.valueOf((int) base) + String.valueOf(n));
		} else if(n instanceof Double) {
			return Double.parseDouble(String.valueOf(base) + String.valueOf((int) n));
		}
		
		return Double.parseDouble(String.valueOf(base) + String.valueOf(n));
	}
	
	public static double assignEnd(Object base, Object[] add) {
		StringBuilder sb = new StringBuilder();
		for(Object obj : add) {
			sb.append(obj.toString());
		}
		Object n = sb.toString();
		
		if(base == null) {
			return Double.parseDouble("." + String.valueOf(n));
		} else if(base instanceof Double) {
			return Double.parseDouble(String.valueOf(base) + String.valueOf(n));
		} else if(base instanceof Integer) {
			return Double.parseDouble(String.valueOf((int) base) + String.valueOf(n));
		}
		
		return Double.parseDouble(String.valueOf(base) + String.valueOf(n));
	}
	
	public static double findbase(double base, double goal) {
		double exp = 0;
		
		while(Math.pow(base, exp) <= goal) {
			exp++;
		}
		
		return exp-1;
	}
	
	public static double increment(double base, double exp, double target, boolean form) {
		int additional = 0;
		
		while(additional < 10) {
			if(bool == true) {
				if(form) {
					if(Math.pow(base, adjust(assignEnd(exp, new Object[] {0, additional}))) > target) {
						break;
					}
				} else {
					if(Math.pow(base, (assignEnd(exp, new Object[] {0, additional}))) > target) {
						break;
					}
				}
				
				additional++;
			} else {
				if(form) {
					if(Math.pow(base, adjust(assignEnd(exp, additional))) > target) {
						break;
					}
				} else {
					if(Math.pow(base, (assignEnd(exp, additional))) > target) {
						break;
					}
				}
				
				additional++;
			}
		}
		
		if(additional != 0) additional--;
		
		//System.out.println("finished additional: " + additional);
		
		double result = 0;
		
		if(bool == true) {

			result = (assignEnd(exp, new Object[] {0, additional}));
			bool = false;
		} else {
			result = adjust(assignEnd(exp, additional));
		}
		
		if(additional == 0) {
			bool = true;
		}
		
		return result;
	}
	
	public static String convert(String[] spl) {
		StringBuilder sb = new StringBuilder();
		
		for(String str : spl) {
			sb.append(str);
		}
		
		return sb.toString();
	}
	
	public static double convert(String str) {
		return new BigDecimal(str).doubleValue();
	}
}
