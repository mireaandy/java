public class metroCard 
{
	float money;
	
	public metroCard(float amountAtFirst)
	{
		if((amountAtFirst * 100) % 5 == 0)
			this.money = amountAtFirst;
		else
			this.money = 0;
	}
	
	public void addMoney(float sum)
	{
		if((sum * 100) % 5 == 0)
			this.money += sum;
		else 
			this.money += 0;
	}
	
	public void takeMoney(float sum)
	{
		if(sum > this.money)
			System.out.println("Not enough money!");
		else
			this.money -= sum;
	}
	
	public float checkBalance()
	{
		return this.money;
	}
}
