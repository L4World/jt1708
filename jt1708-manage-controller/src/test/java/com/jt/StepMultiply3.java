package com.jt;

public class StepMultiply3 {

	private int[] arrays;

	public int arraysLength;

	private int zeroLength;
	
	{
		arrays = new int[1000000];
		arrays[0] = 1;
		arraysLength = 1;
		zeroLength = 0;
	}

	public static void main(String[] args) {
		StepMultiply3 calc = new StepMultiply3();
		int number = 10000;

		Long t1 = System.currentTimeMillis();
		calc.multiply(number);
		System.out.println(System.currentTimeMillis()-t1);
//		System.out.println(number + "???????? : " + calc.arraysLength + "λ");
//		System.out.println("????? : ");
//		for (int i = 0; i < calc.arraysLength; i++) {
//			if (i % 10 == 0) {
//				System.out.println();
//				System.out.print("" + i / 10 + " : ");
//			}
//			System.out.print(calc.arrays[calc.arraysLength - i - 1]);
//		}
	}

	public int[] multiply(int num) {

		for (int i = 2; i <= num; i++) {
			// if (i % 1000 == 0) {
			// System.out.println(i);
			// }
			multiply(arrays, i);
			checkZero(arrays);
		}
		return arrays;
	}

	private void checkZero(int[] arrays) {

		for (int i = zeroLength; i < arraysLength; i++) {
			if (arrays[i] == 0) {
				zeroLength++;
			} else {
				break;
			}
		}
	}

	private void multiply(int[] arrays, int num) {

		int high = 0;
		for (int i = zeroLength; i < arraysLength; i++) {
			int total = arrays[i] * num + high;
			arrays[i] = total % 10;
			high = total / 10;
		}
		while (high > 0) {
			arrays[arraysLength++] = high % 10;
			high = high / 10;
		}
	}
}