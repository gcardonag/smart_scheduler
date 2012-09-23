package practiceclasses;


public class QueueWithPriorityQueueTester {


		public static void main(String[] args) {
			QueueWithtPriorityQueue<Integer, KeyedMessage> prtQueue = 
				new QueueWithtPriorityQueue<Integer, KeyedMessage>(
						new IntegerComparator2(), new IntegerKeyValidator()); 
			
			testInsert(prtQueue, new KeyedMessage(20, "Clase: Databases")); 
			testInsert(prtQueue, new KeyedMessage(33, "Reunion: Nayda")); 
			testInsert(prtQueue, new KeyedMessage(13, "Clase: Historia del Mundo Moderno")); 
			testInsert(prtQueue, new KeyedMessage(4, "Reunion: Grupo cloud")); 
			testInsert(prtQueue, new KeyedMessage(24, "Clase: Microprocessadores")); 
			testInsert(prtQueue, new KeyedMessage(37, "Clase: Electronica Digital")); 
			testInsert(prtQueue, new KeyedMessage(7, "Actividad: Recoger Nenes")); 
			testInsert(prtQueue, new KeyedMessage(40, "Actividad: Power Nap")); 
			testInsert(prtQueue, new KeyedMessage(224, "Actividad: Ejercicio")); 
										
		}

		private static void testInsert(QueueWithtPriorityQueue<Integer, KeyedMessage> prtQueue, 
										KeyedMessage p) 
		{
			prtQueue.insert(p.getNumber(), p);
			 
			System.out.println("Priority Queue after inserting <"+p+">: \n" + prtQueue); 
		}
		
		//...
	}


