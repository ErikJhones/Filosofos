package Filosofos;
//problema do jantar dos filosofos para n threads fornecidas
//classe que gerencia os estados dos filosofos em um vetor compartilhado


//bibliotecas necessárias
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import java.util.Arrays;

public class Estado {
	
	private String[] estado2;  //vetor de estados
	
	private int totalThreads;  //total de threads
	
	private Semaphore semaphore;//define um semoforo
	
	private Lock accessLock = new ReentrantLock();  //variavel que garantira a exclusão mútua
	
	//construtor inicial
	public Estado(int num,Semaphore semaphore) {
		
		estado2 = new String[num];
		Arrays.fill(estado2,"pensando");  //inicia o vetor de estado com todas as posições em "pensando"
		this.semaphore = semaphore;
		totalThreads = num;
		
	}
	
	public Estado() {
		
	}
	
	public void setEstado(int thread,String novoEstado) {
		estado2[thread] = novoEstado;
		
	}
	
	public String getEstado(int thread) {
		return estado2[thread];
	}
	
    public void pegarGarfo(int thread) {
    	//trava aregião crítica
    	accessLock.lock();
    	
    	try {	
    		setEstado(thread,"faminto");
    		System.out.printf("Filosofo %d esta faminto.\n",thread);
    		intencao(thread);//verifica se a thread pode comer
    		
    		accessLock.unlock();  //sai da região crítica
    		
    		semaphore.acquire();	
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    //m�todo para devolver os garfos
	public void devolverGarfo(int thread) {
		
		//blequeia a regi�o cr�tica
		accessLock.lock();
		
		//muda estado do fil�sofo para penssando
		setEstado(thread,"pensando");
		System.out.printf("filosofo %d pensando.\n",thread);
		//verifica a inten��o do fil�sofo a esquerda
		intencao(antecessor(thread));
		//verifica a inten��o do fil�sofo a direita
		intencao(sucessor(thread));
		//destrava a regi�o cr�tica
		accessLock.unlock();
	}
	
	//m�todo que muda a intem��o do filosofo de faminto para comendo
	private void intencao(int thread) {
		//se o estado dos filosofos a direitra e esquerda forem diferente de comendo
		if((getEstado(thread) == "faminto") && (getEstado(sucessor(thread)) != "comendo") &&
			(getEstado(antecessor(thread)) != "comendo")) {
			
			//muda estado da trhead
			setEstado(thread,"comendo");
			System.out.printf("filosofo %d comendo.\n",thread);
						
			semaphore.release();
		}
		
	}
	
	//retorna o filosofo sucesssor ao atual
	private int sucessor(int thread) {
		if(thread == (totalThreads - 1)) {
			return 0;
		}
			return (thread + 1);
	}//fim do m�todo sucessor
		
	//retorna o filosofo antecesor ao atual
	private int antecessor(int thread) {
		if(thread == 0) {
			return (totalThreads - 1);
		}
			return (thread -1);
	}//fim do m�todo antecessor
		
}//fim da classe Estado
