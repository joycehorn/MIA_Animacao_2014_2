package modelo;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import trabalho01.LancamentoObliquo;

public class Lancador {
	public boolean geraBala;
	public Vetor3D posicao;
	public float r,g,b;
	public float raioBase,raioTopo,altura;
	public GLUquadric cilindro;
	
	public Lancador(Vetor3D posicao, float r, float g, float b, float raioBase, float raioTopo, float altura, GLU glu){
		this.geraBala = false;
		this.posicao = posicao;
		this.r = r;
		this.g = g;
		this.b = b;
		this.raioBase = raioBase;
		this.raioTopo = raioTopo;
		this.altura = altura;
		this.cilindro = glu.gluNewQuadric();
		//TODO: posso fazer isso aqui e ele refletir l‡ fora?
		glu.gluQuadricDrawStyle(this.cilindro, GLU.GLU_FILL);
		
	}
	
	public Bala gerarBala(float velocidade, GLU glu){
		//TODO: onde eu gero a bala em relacao ao canh‹o?
		Vetor3D posicaoBala = new Vetor3D(this.posicao.x, this.altura, this.posicao.z);
		Bala b = new Bala(this.raioTopo/2.0f, posicaoBala, this.r, this.g, this.b, velocidade, glu);
		return b;
	}
	
	public void desenha(GL2 gl, GLU glu){
		gl.glColor3f(this.r, this.g, this.b);
		gl.glPushMatrix();
			gl.glTranslatef(this.posicao.x, this.posicao.y, this.posicao.z);
			gl.glRotatef(90, 1.0f, 0.0f, 0.0f);
			glu.gluCylinder(this.cilindro, this.raioBase, this.raioTopo, this.altura, 20, 20);
		gl.glPopMatrix();	
	}
}
