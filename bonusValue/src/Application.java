import java.util.Scanner;
import java.lang.Float;
public class Application 
{
	public static class ticket
	{
		static float price = (float) 2.75;
		static float discount = (float) 0.05;
			
		public static float buy(int numberTickets)
		{
			return numberTickets >= 2 ? price * numberTickets - price * numberTickets * discount : price * numberTickets;
		}

	}
	
	public static void main(String[] args) 
	{
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		float sumAtFirst = 0;
		float amountToAdd = 0;
		int numberOfTickets = 0;
		System.out.print("Set the amount at first ");
		sumAtFirst = input.nextFloat();
		metroCard card = new metroCard(sumAtFirst);
		do
		{
			System.out.print("Do you want to enter a add a new amount ? 0 - NO, any other number - YES and that is the amount ");
			amountToAdd = input.nextFloat();
			if(Float.compare(amountToAdd, (float)0) != 0)
				card.addMoney(amountToAdd);
			System.out.print("Enter the number of tickets to buy ");
			numberOfTickets = input.nextInt();
			card.takeMoney(ticket.buy(numberOfTickets));
			System.out.println("Money amount after buying " + card.checkBalance());
		}while(true);
	}

}
