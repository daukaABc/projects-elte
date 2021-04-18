package model;

public interface BinaryOperation {
	int compute(int op1, int op2);
	
	public static BinaryOperation PLUS = (a, b) -> a + b;
	public static BinaryOperation MINUS = (a, b) -> a - b;
	public static BinaryOperation DIV = (a, b) -> a / b;
	public static BinaryOperation MUL = (a, b) -> a * b;
}
