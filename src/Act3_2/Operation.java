package Act3_2;

import java.io.Serializable;

public class Operation implements Serializable {
	private int op1, op2, result1;
	private char operation;

	public Operation(int op1, int op2, char operation) {
		this.op1 = op1;
		this.op2 = op2;
		this.operation = operation;
	}

	public int getOp1() {
		return this.op1;
	}

	public int getOp2() {
		return this.op2;
	}

	public int getResult1() {
		return this.result1;
	}

	public char getOperation() {
		return this.operation;
	}

	public void setOp1(int op1) {
		this.op1 = op1;
	}

	public void setOp2(int op2) {
		this.op2 = op2;
	}

	public void setResult1(int result1) {
		this.result1 = result1;
	}

	public void setOperation(char operation) {
		this.operation = operation;
	}
}