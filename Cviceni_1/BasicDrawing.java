import java.util.Scanner;
/**
 * 
 * @author Lukas Runt - A20B0226P
 *
 */
public class BasicDrawing {

	public static void main(String[] args) {
		System.out.println("Lukas Runt - A20B0226P");
		System.out.println("Vlajka Bahamy");
		int m;
		if(args.length != 0) {
			m = Integer.parseInt(args[0]);
		}
		else {
			Scanner sc = new Scanner(System.in);
			System.out.print("Zadej sirku m: ");
			m = sc.nextInt();
		}
		int n =  m / 2; //sirka:vyska = 1:2
		
		System.out.println("Sirka x vyska = " + m + "x" + n);
		
		for(int y = 0; y < n; y++) {
			for (int x = 0; x < m; x++) {
				int z1 = n*x - m*y;
				int z2 = n*x + m*y - m*n;
				if(y<=n/3 || y>=(2*n)/3) {
					System.out.print(z1 <= 0 && z2 < 0  ? "B" : "+");
				}
				else {
					System.out.print(z1 <= 0 && z2 <= 0  ? "B" : "-");
				}
				
			}
			System.out.println();
		}
		

	}

}
