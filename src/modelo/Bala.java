package modelo;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;


public class Bala {
	public float raio;
	public Vetor3D posicaoInicial;
	public Vetor3D posicaoAtual;
	public float r,g,b;
	public GLUquadric esfera;
	public float velocidade;
	
	
	public Bala(float raio, Vetor3D posicaoInicial, float r, float g, float b,float velocidade, GLU glu){
		this.raio = raio;
		this.posicaoInicial = posicaoInicial;
		this.posicaoAtual = posicaoInicial;
		this.r = r;
		this.g = g;
		this.b = b;
		this.velocidade = velocidade;
		this.esfera = glu.gluNewQuadric();
		glu.gluQuadricDrawStyle(this.esfera, GLU.GLU_FILL);
	}
	
	public void desenha(GL2 gl, GLU glu){
		gl.glColor3f(this.r, this.g, this.b);
		gl.glPushMatrix();
			gl.glTranslatef(this.posicaoAtual.x, this.posicaoAtual.y, this.posicaoAtual.z);
			glu.gluSphere(this.esfera, this.raio, 20, 20);
		gl.glPopMatrix();	
		
	}
	
	public void atualizaBala(float tempo){
		System.out.println("posicaoInicial");
		System.out.println(this.posicaoInicial.x+","+this.posicaoInicial.y+","+this.posicaoInicial.z);
		System.out.println("ŠtualizaBala");
		this.posicaoAtual.x = this.posicaoInicial.x + (this.velocidade * tempo);
		this.posicaoAtual.z = this.posicaoInicial.z + (this.velocidade * tempo);
		this.posicaoAtual.y = this.posicaoInicial.y + (this.velocidade * tempo) + (9.8f/2.0f * (tempo * tempo));

		System.out.println(this.posicaoAtual.x+","+this.posicaoAtual.y+","+this.posicaoAtual.z);
	}
	
	
}
