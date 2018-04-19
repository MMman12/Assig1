/**
 * Anytime before you compare scores, check to see
 * if there is an ace in the player/dealers cards
 */

public class Blackjack
{
	static RandIndexQueue<Card> theShoe;
	static RandIndexQueue<Card> shoe = new RandIndexQueue<Card>(1000);
	static RandIndexQueue<Card> discard = new RandIndexQueue<Card>(1000);
	static RandIndexQueue<Card> dealer = new RandIndexQueue<Card>(5);
	static RandIndexQueue<Card> player = new RandIndexQueue<Card>(5);
	static int rounds, decks;

	public static void main (String[] args)
	{
		int playerScore=0;
		int dealerScore=0;
		int dealerWins=0, playerWins=0, push=0;
		boolean playing=true;
		int ogShoeSize=0;

		rounds=Integer.parseInt(args[0]);
		decks=Integer.parseInt(args[1]);

		ogShoeSize=decks*52;



		theShoe = createShoe();


		int counter=0;

		while(counter<rounds)
		{
			System.out.println("Round " + counter + " beginning");
			for(int i=0; i<2; i++)
			{
				Card playerCard = theShoe.poll();

				if((playerScore + playerCard.value())>21)
				{
					playerScore+=playerCard.value2();
				}
				else
					playerScore+=playerCard.value();

				player.offer(playerCard);

				Card dealerCard = theShoe.poll();

				if((dealerScore + dealerCard.value())>21)
				{
					dealerScore+=dealerCard.value2();
				}
				else
					dealerScore+=dealerCard.value();

				dealer.offer(dealerCard);

			}
			System.out.println("Player: " + player + " :" + playerScore);
			System.out.println("Dealer: " + dealer + " :" + dealerScore);

			while(playerScore<17)
			{
				Card playerCard = theShoe.poll();

				if((playerScore + playerCard.value())>21)
				{
					playerScore+=playerCard.value2();
				}
				else
					playerScore+=playerCard.value();

				player.offer(playerCard);
				System.out.println("Player hits: " + playerCard);
			}

			while(playing)
			{
				if (playerScore > 21)
				{
					System.out.print("Player BUSTS: " + player);
					System.out.println(" :" + playerScore);
					System.out.println("Dealer WINS");
					dealerWins++;
					break;

				}
				else
				{
					System.out.println("Player STANDS.   " + player + " " + playerScore);

					while (dealerScore <17)
					{
						Card dealerCard = theShoe.poll();

						if((dealerScore + dealerCard.value())>21)
						{
							dealerScore+=dealerCard.value2();
						}
						else
							dealerScore+=dealerCard.value();

						dealer.offer(dealerCard);
						System.out.println("Dealer hits: " + dealerCard);
					}
				}


				if (dealerScore > 21)
				{

					System.out.println("Dealer BUSTS: " + dealer+ " :" + dealerScore);
					System.out.println("Player WINS");
					playerWins++;
					break;
				}
				else
				{
					System.out.println("Dealer STANDS.  "+ dealer+ " :" + dealerScore);

					if (dealerScore > playerScore)
					{
						System.out.println("Dealer WINS");
						dealerWins++;
						break;
					}
					else if (dealerScore < playerScore)
					{
						System.out.println("Player WINS");
						playerWins++;
						break;
					}
					else
					{
						System.out.println("PUSH");
						push++;
						break;
					}
				}

			}


			while(player.size()>0)
			{
				discard.offer(player.poll());
			}

			while(dealer.size()>0)
			{
				discard.offer(dealer.poll());
			}
			System.out.println("");


			playerScore=0;
			dealerScore=0;

			if(ogShoeSize/4>theShoe.size())
			{
				while(discard.size()!=0)
				{
					theShoe.offer(discard.poll());
				}
			}




			counter++;

		}
		System.out.println("After " + rounds + " rounds, here are the results:");
		System.out.println("\t Dealer Wins: " + dealerWins);
		System.out.println("\t Player  Wins: " + playerWins);
		System.out.println("\t Pushes: " + push);
	}

	/**
	 * check all player/dealer/hands and shit for aces
	 * @return true if it has an ace
	 * @return false if it doesn't have an ace
	 */

	//public static boolean checkForAce() {

	//}

	public static RandIndexQueue createShoe()
	{
		int totalSize = decks * 52;

		RandIndexQueue<Card> theShoe= new RandIndexQueue<Card>(totalSize);
		for(int i=0;i<decks;i++)
		{
			for (Card.Suits s : Card.Suits.values())
			{
				for (Card.Ranks r : Card.Ranks.values())
				{
					theShoe.offer(new Card(s, r));
				}
			}
		}
		theShoe.shuffle();
		return theShoe;
	}
}
