package Filosofos;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ExecutionException;

import java.util.Scanner;

public class FiloTest {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		
		int num;//número de filosofos
		Scanner novo = new Scanner(System.in);
		Estado estado;//cria uma referencia ao es
		
		System.out.println("Digite o numero de filosofos a serem criados.");
		num = novo.nextInt();
		
		Semaphore semaphore = new Semaphore(num);//define um total de semafors igual ao numero de filosofos
		
		estado = new Estado(num,semaphore);//vetor que guarda os estados dos filósofos
		
		
		//cria um pool dinamico de gerenciadores de threads
		ExecutorService es = Executors.newCachedThreadPool();
		
		Filosofos[] filosofo = new Filosofos[num];
		
		Future<?> futures[] = new Future[num];
		
		for(int i=0;i<num;i++) {
			filosofo[i] = new Filosofos(estado,i);
			futures[i] = es.submit(filosofo[i]);
		}
		es.shutdown();

	}

}
