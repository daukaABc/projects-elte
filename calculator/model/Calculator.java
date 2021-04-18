package model;

import java.io.Serializable;
import java.util.Optional;
import java.util.OptionalInt;

public class Calculator implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private boolean operand1set = false;
	private int operand1;
	
	private boolean optional1set = false;
	private int operand2;
	private Optional<BinaryOperation> op;
	
	public Calculator() {
		clear();
	}

	public void setNextOperand(int operand) {
		if (operand1.isPresent()) {
			operand2 = OptionalInt.of(operand);
		} else {
			operand1 = OptionalInt.of(operand);
		}
	}
	
	public void setOp(BinaryOperation op) {
		this.op = Optional.of(op);
	}
	
	public void clear() {
		operand1 = OptionalInt.empty();
		operand2 = OptionalInt.empty();
		op = Optional.empty();
	}
	
	public int calculate() throws CalculatorException {
		if (!operand2.isPresent())  {
			if (operand1.isPresent()) {
				return operand1.getAsInt();
			}
			return 0;
		}
		if (op.isPresent()) {
			int res = op.get().compute(operand1.getAsInt(), operand2.getAsInt());
			clear();
			operand1 = OptionalInt.of(res);
			return res;
		}

		throw new CalculatorException("Operand was not set");
	}
	
}
