import java.util.Arrays;
import java.util.Scanner;

public class BoothALgo{

	public static void main(String[] args) {
		Scanner t = new Scanner(System.in);
		int a = t.nextInt();
		int b = t.nextInt();
		Multiply m = new Multiply(a, b);
		System.out.println("Product of the two integers is: " + m.result());
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		Division d = new Division(Math.abs(a), Math.abs(b));
		int[] result = d.result();
		if(result.length == 2) {
			System.out.println("Remainder is: " + (result[0] * a * b)/ (Math.abs(a) * Math.abs(b)) );
			System.out.println("Quotient is: " + (result[1] * a * b)/ (Math.abs(a) * Math.abs(b)));
		}
		else {
			System.out.println("Zero Division Error.");
			
		}
	}

}

	class Multiply {
		static int n = 12;
		int M_decimal; //multiplicand
		int Q_decimal; //muliplier
		int[] A = new int[n];
		int q = 0;
		int[] M;
		int [] Q;
		int[] Mneg; //2s complement of M
		public Multiply(int a, int b) {
			Arrays.fill(A, 0);
			this.M_decimal = a;
			this.Q_decimal = b;
			if(a<0)
				this.M = calculateNeg(convertToBinary(-1*this.M_decimal));
			else 
				this.M = convertToBinary(this.M_decimal);
			
			if(b<0)
				this.Q = calculateNeg(convertToBinary(-1*this.Q_decimal));
			else
				this.Q = convertToBinary(this.Q_decimal);
	
			this.Mneg = calculateNeg(this.M);
			display(M, "M");display(Q, "Q");display(Mneg, "Mneg");
		}
		public int result() {
			for(int i=0; i<n; i++) {
				
				if((this.q^this.Q[0]) != 0) {
					if(this.q==0)
						this.A = add(this.A, this.Mneg);
					else
						this.A = add(this.A, this.M);
				}
				display(A, "A");display(Q, "Q");System.out.println(q);
				shiftRight();

				display(A, "A");display(Q, "Q");System.out.println(q);
			}
			return toDecimal();
		}
		private int toDecimal() {	
			int ans = 0;
			if(this.A[n-1]==1) {
				ans += this.Q[0];
				for (int i=1; i<n; i++) {
					ans += Math.pow(2, i)*(this.Q[i]^1);
				}
				for (int i=0; i<n; i++) {
					ans += Math.pow(2, i+n)*(this.A[i]^1);
				}
				return -1*ans;
			}
			for (int i=0; i<n; i++) {
				ans += Math.pow(2, i)*this.Q[i];
			}
			for (int i=0; i<n; i++) {
				ans += Math.pow(2, i+n)*this.A[i];
			}
			return ans;
		}
		private static void display(int[] arr, String m) {
			System.out.print(m+"   ");
			for (int i=n-1; i>=0; i--) {
				System.out.print(arr[i]+" ");
			}
			System.out.println();
		}
		private static int[] convertToBinary(int n1) {
			int[] arr = new int[n];
			Arrays.fill(arr, 0);
			for (int i=0; i<n; i++) {
				arr[i] = n1 % 2;
				n1 /= 2;
			}
			return arr;
		}
		private int[] copy(int[] arr) {
			int[] c = new int[n];
			for (int i=0; i<n; i++) {
				c[i]=arr[i];
			}
			return c;
		}
		private static int[] calculateNeg(int[] arr) {
			int[] neg = new int[n];
			for (int i=0; i<n; i++) {
				neg[i] = arr[i]^1;
			}
//			display(neg, "neg");
			int[] one = new int[n];
			Arrays.fill(one, 0);
			one[0] = 1;
//			display(one, "one");
			neg = add(neg, one);
			return neg;
		}
		private static int[] add(int[] arr1, int[] arr2) {
			int c = 0;
			int[] arr = new int[n];
			Arrays.fill(arr, 0);
			for (int i=0; i<n; i++) {
				int temp = arr1[i] + arr2[i] + c;
				if(temp==1 || temp==3) arr[i]=1;
				c = temp/2;
			}
			return arr;
		}
		private void shiftRight() {
			int[] A_old = copy(this.A);
			int[] Q_old = copy(this.Q);
			A_old[n-1] = this.A[n-1];
			for (int i=n-2; i>=0; i--) {
				this.A[i] = A_old[i+1];
			}
			this.Q[n-1] = A_old[0];
			for (int i=n-2; i>=0; i--) {
				this.Q[i] = Q_old[i+1];
			}
			this.q = Q_old[0];
			System.out.println("shiftright");
		}
	}
	
	
	
	class Division{
		static int n = 12;
		int B_decimal; //divisor
		int Q_decimal; //dividend 
		int[] A = new int[n+1];
//		int q = 0;
		int[] B;
		int[] B1 = new int[n+1];
		int [] Q;
		int[] Bneg; //2s complement of M
		public Division(int a, int b) {
			
			Arrays.fill(A, 0);
			this.Q_decimal = a;
			this.B_decimal = b;
			if(a<0)
				this.Q= calculateNeg(convertToBinary(-1*this.Q_decimal));
			else
				this.Q = convertToBinary(this.Q_decimal);
			if(b<0)
				this.B = calculateNeg(convertToBinary(-1*this.B_decimal));
			else
				this.B = convertToBinary(this.B_decimal);
			
			
			B1[n] = 0;
			for(int i = 0; i <n; i++) {
				B1[i] = B[i]; 
			}
			this.Bneg = calculateNeg(this.B1);
			display(Q, "Q");display(B, "B");display(Bneg, "Bneg");
		}
		
		public int[] result() {
			int sum = 0;
			for(int i = 0; i <n; i++) {
				sum = sum + B[i];
			}
			if(sum == 0) {
				System.out.println("Division by 0 not allowed");
				int arr[] = new int[0];
				return arr;
			}
			else {
				for(int i=0; i<n; i++) {
//					display(Q, "Q");display(A, "A");
					shiftLeft();
					display(Q, "Q");display(A, "A"); display(Bneg, "Bneg");
				    A = add(A, Bneg);
					System.out.println("adding A and Bneg");
					display(A, "A");
					
					if(A[n] == 0) {
						Q[0] = 1;
					}else {
//						System.out.println("yay");
						Q[0]= 0;
						this.A = add(this.A , this.B1);
					}
				}
				int rem = toDecimal(A);
				int qu = toDecimal(Q);
				int[] ansarr = new int[2];
				ansarr[0] = rem;
				ansarr[1] = qu;
				return ansarr;
				
			}
			
		}
		
		private int toDecimal(int A[]) {	
			int ans = 0;
			for (int i=0; i<n; i++) {
				ans += Math.pow(2, i)*A[i];
			}
			return ans;
		}
		
		
		private static void display(int[] arr, String m) {
			System.out.print(m+"   ");
			int x = arr.length;
			for (int i=x-1; i>=0; i--) {
				System.out.print(arr[i]+" ");
			}
			System.out.println();
		}
		private static int[] convertToBinary(int n1) {
			int[] arr = new int[n];
			Arrays.fill(arr, 0);
			for (int i=0; i<n; i++) {
				arr[i] = n1 % 2;
				n1 /= 2;
			}
			return arr;
		}
		private int[] copy(int[] arr) {
			int x = arr.length;
			int[] c = new int[x];
			for (int i=0; i<x; i++) {
				c[i]=arr[i];
			}
			return c;
		}
		private static int[] calculateNeg(int[] arr) {
			int x = arr.length;
			int[] neg = new int[x];
			for (int i=0; i<x; i++) {
				neg[i] = arr[i]^1;
			}
//			display(neg, "neg");
			int[] one = new int[x];
			Arrays.fill(one, 0);
			one[0] = 1;
//			display(one, "one");
			neg = add(neg, one);
			return neg;
		}
		private static int[] add(int[] arr1, int[] arr2) {
			int c = 0;
			int x = arr1.length;
			
			int[] arr = new int[x+1];
			Arrays.fill(arr, 0);
			for (int i=0; i<x; i++) {
//				System.out.println(arr1[i] + " to " + arr2[i]);
				int temp = arr1[i] + arr2[i] + c;
				if(temp==1 || temp==3) arr[i]=1;
				c = temp/2;
			}
			int[] ansarr = new int[x];
			for(int ii= 0; ii <x; ii++) {
				ansarr[ii] = arr[ii];
			}
			return ansarr;
		}
		  
		private void shiftLeft() {
			int[] A_old = copy(this.A);
			int[] Q_old = copy(this.Q);
			
//			A_old[n-1] = this.A[n-1];
			for (int i=n-2; i>=0; i--) {
				this.A[i+1] = A_old[i];
			}
			this.A[0] = Q_old[n-1];
			for (int i=n-2; i>=0; i--) {
				this.Q[i+1] = Q_old[i];
			}
//			this.q = Q_old[0];
			System.out.println("shiftleft");
		}
	
	}
	

