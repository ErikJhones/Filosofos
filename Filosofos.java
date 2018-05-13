package Filosofos;
import java.util.Random;

public class Filosofos implements Runnable{
	
	private Random generator = new Random();
	
	//vetor qe guarda o estado de cada thread
	private Estado estado;
	
	private int thread;
	private int sleepTime;
	
	public Filosofos(Estado estado,int thread) {
		this.estado = estado;
		this.thread = thread;
	}
	
	//m�todo que faz o fil�sofo pensar
	private void pensar() {
		System.out.printf("Filosofo %d esta %s!\n\n",thread,estado.getEstado(thread));
		sleepTime = generator.nextInt(6000);
		
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//m�todo que faz o fil�sofo comer
	private void comer() {
		//System.out.printf("Filosofo %d esta %s!\n",thread,estado.getEstado(thread));
		sleepTime = generator.nextInt(6000);
		
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//m�todo executado pela thread
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			pensar();
			estado.pegarGarfo(thread);
			comer();
			estado.devolverGarfo(thread);
		}
		
	}
}
