package trabalho01;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.opengl.DebugGL2;
import javax.media.opengl.GL2;
import javax.media.opengl.GL2GL3;
import javax.media.opengl.GLAnimatorControl;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import modelo.Bala;
import modelo.Lancador;
import modelo.Vetor3D;

import com.jogamp.opengl.util.FPSAnimator;

import static javax.media.opengl.GL.*;  // constantes GL
import static javax.media.opengl.GL2.*; // constantes GL2

@SuppressWarnings("serial")
public class LancamentoObliquo extends GLCanvas implements GLEventListener, KeyListener{
	private boolean sobe = true;
	
	public Lancador lancador1, lancador2, lancador3;
	public Bala bala1, bala2, bala3;
	public float tempoAtual = 0.0f;

	
	public GLU glu;
	private static float eyeX = 0.0f;
	private static float eyeY = 1.0f;
	private static float eyeZ = 2.0f;
	
	private static float atX = 0.0f;
	private static float atY = 0.0f;
	private static float atZ = 0.0f;
	public float step = 0.0f;
	
	 
	private static float fov = 45.0f; 
	//private static float distanciaAlvoDollyZoom = 2.1f;
	
	private static float zoom = 1.0f;
	private static float anguloX = 0.0f;
	private static float anguloY = 0.0f;
	private float zNear = 1.0f;
	private float zFar = 10000.0f;
	private float aspecto;
	private boolean zoomIn = false;
	private boolean zoomOut = false;
	private boolean panCima = false;
	private boolean panBaixo = false;
	private boolean panDireita = false;
	private boolean panEsquerda = false;
	private boolean dollyIn = false;
	private boolean dollyOut = false;
	private boolean rotacaoXE = false;
	private boolean rotacaoXD = false;
	private boolean rotacaoYE = false;
	private boolean rotacaoYD = false;
	
	public LancamentoObliquo(){
		this.addGLEventListener(this);
		this.addKeyListener(this);
		this.setFocusable(true);
	    this.requestFocus();
	}
	
	
	public void reshape(GLAutoDrawable drawable, int x, int y, int largura, int altura) {
		GL2 gl = drawable.getGL().getGL2();
		if (altura == 0) altura = 1; //evitar divisao por zero
		aspecto = (float)largura/altura;
		gl.glViewport(0, 0, largura, altura);
		setarCamera(gl);
	}
	
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		tempoAtual+=0.01;
		setarZoom();
		setarCamera(gl);
		
		
		gl.glClear(GL_COLOR_BUFFER_BIT |GL_DEPTH_BUFFER_BIT);//limpar os buffers de cor e profundidade
		gl.glLoadIdentity();
		
		//setarIluminacao(gl);
		
		
		gl.glBegin(GL2GL3.GL_QUADS); // of the color cube
		
	        
	      // Ch‹o
	      gl.glColor3f(1.0f, 0.5f, 0.0f); // orange
	      gl.glVertex3f(3.5f, -2.0f, -2.0f);
	      gl.glVertex3f(-3.5f, -2.0f, -2.0f);
	      gl.glVertex3f(-3.5f, -2.0f, -7.0f);
	      gl.glVertex3f(3.5f, -2.0f, -7.0f);
	 
	 
	      gl.glEnd(); // of the color cube
	  	/*
	  	
	      if (sobe) step+=0.01f;
	    else step-=0.01f;
	    gl.glTranslatef(-2.0f, -0.8f+step, -4.0f); 
	    
	    GLUquadric earth = glu.gluNewQuadric();
		gl.glColor3f(0.3f, 0.5f, 1f);
        glu.gluQuadricDrawStyle(earth, GLU.GLU_FILL);
        glu.gluQuadricNormals(earth, GLU.GLU_FLAT);
        glu.gluQuadricOrientation(earth, GLU.GLU_OUTSIDE);
        final float radius = 0.05f;
        final int slices = 16;
        final int stacks = 16;
        glu.gluSphere(earth, radius, slices, stacks);
        glu.gluDeleteQuadric(earth);
        if (sobe && step < 2.36f) sobe = true;
        else if (!sobe && step > 0.0f) sobe = false;
        else sobe = !sobe;
        */
		lancador1.desenha(gl, glu);
		lancador2.desenha(gl, glu);
		lancador3.desenha(gl, glu);
		
		if (lancador1.geraBala) bala1 = lancador1.gerarBala(0.01f,glu);
		if (lancador2.geraBala) bala2 = lancador2.gerarBala(0.01f,glu);
		if (lancador3.geraBala) bala3 = lancador3.gerarBala(0.01f, glu);
		
		if (bala1 != null) {
			bala1.desenha(gl, glu);
			bala1.atualizaBala(tempoAtual);
		}
		if (bala2 != null) {
			bala2.desenha(gl, glu);
			bala2.atualizaBala(tempoAtual);
		}
		if (bala3 != null) {
			bala3.desenha(gl, glu);
			bala3.atualizaBala(tempoAtual);
		}
	}

	
	
	
	public void setarCamera(GL2 gl){
		//TODO:marcacao
		gl.glMatrixMode(GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(fov*zoom, aspecto, zNear, zFar);
		glu.gluLookAt(eyeX, eyeY, eyeZ, atX, atY, atZ, 0, 1, 0);
		gl.glMatrixMode(GL_MODELVIEW);
        gl.glLoadIdentity();
	}
	public void setarZoom(){
		//TODO: testar os limites do zoom para os planos de corte
		if(zoomIn) zoom-=0.01;
		if(zoomOut) zoom+=0.01;
		if(panCima) eyeY-=0.001;
		if(panBaixo) eyeY+=0.001;
		if(panDireita) eyeX-=0.001f;
		if(panEsquerda) eyeX+=0.001f;
		
		if(dollyIn){
			zoom-=0.01;
			eyeZ+=0.5f;
			
		}
		if(dollyOut){
			zoom+=0.01;
			eyeZ-=0.5f;
			
		}
		if(rotacaoXE) anguloX-=1.01;
		if(rotacaoXD) anguloX+=1.01;
		if(rotacaoYE) anguloY-=1.01;
		if(rotacaoYD) anguloY+=1.01;
		
	}
	
			
/*
 * 
 * 
            //Calculate the width at the target distance
            float width = targetDistance * (float)Math.Atan(MathHelper.ToRadians(90));

            //Calculate the camera distance for intended target to remain a similar visible size           
            cameraDistance = width / (2 * (float)Math.Tan(fieldOfView) / 2);

            //Set camera matrices using newly calculated zoom and dolly values
            view = Matrix.CreateLookAt(new Vector3(-cameraDistance, 50, 0), new Vector3(0, 50, 0), Vector3.Up);            
            projection = Matrix.CreatePerspectiveFieldOfView(fieldOfView, GraphicsDevice.Viewport.AspectRatio, 0.1f, 10000.0f);  
 */
	
	public static void main(String args[]){
		//rodar o codigo da GUI na thread que despacha os eventos para seguranca de thread
				SwingUtilities.invokeLater(new Runnable(){
					public void run() {
						GLCanvas canvas = new LancamentoObliquo();
						final FPSAnimator animator = new FPSAnimator (canvas, 60, true);
						final JFrame frame = new JFrame();
						frame.getContentPane().add(canvas);
						frame.setUndecorated(true);//sem barras
						frame.setExtendedState(Frame.MAXIMIZED_BOTH);//tela cheia
						frame.setVisible(true);
						animator.start(); 						
					}					
				});
	}

	public void keyPressed(KeyEvent evento) {
		 int keyCode = evento.getKeyCode();
	      switch (keyCode) {
	         case KeyEvent.VK_ESCAPE: // parar Animator e sair
	            new Thread() {
	               public void run() {
	                  GLAnimatorControl animator = getAnimator();
	                  if (animator.isStarted()) animator.stop();
	                  System.exit(0);
	               }
	            }.start();
	            break;
	         case KeyEvent.VK_I: //zoom in
	        	 zoomIn = true;
	        	 break;
	         case KeyEvent.VK_O: //zoom out
	        	 zoomOut = true;
	        	 break;
	         case KeyEvent.VK_UP: //panoramica anti-horario em torno do eixo x
	        	 panCima = true;
	        	 break;
	         case KeyEvent.VK_DOWN: //panoramica horario em torno do eixo x
	        	 panBaixo = true;
	        	 break;
	         case KeyEvent.VK_LEFT: //panoramica anti-horario em torno do eixo y
	        	 panEsquerda = true;
	        	 break;
	         case KeyEvent.VK_RIGHT: //panoramica horario em torno do eixo y
	        	 panDireita = true;
	        	 break;
	         case KeyEvent.VK_Z: //dolly zoom in
	        	 dollyIn = true;
	        	 break;
	         case KeyEvent.VK_X: //dolly zoom out
	        	 dollyOut = true;
	        	 break;
	         case KeyEvent.VK_E:
	        	 rotacaoXE = true;
	        	 break;
	         case KeyEvent.VK_R:
	        	 rotacaoXD = true;
	        	 break;
	         case KeyEvent.VK_W:
	        	 rotacaoYE = true;
	        	 break;
	         case KeyEvent.VK_S:
	        	 rotacaoYD = true;
	        	 break;
	         case KeyEvent.VK_1:
	        	 lancador1.geraBala = true;
	        	 break;
	         case KeyEvent.VK_2:
	        	 lancador2.geraBala = true;
	        	 break;
	         case KeyEvent.VK_3:
	        	 lancador3.geraBala = true;
	        	 break;
	         	 
	        	 
	      }
		
	}


	public void keyReleased(KeyEvent evento) {
		 int keyCode = evento.getKeyCode();
	      switch (keyCode) {
	         case KeyEvent.VK_I:
	        	 zoomIn = false;
	        	 break;
	         case KeyEvent.VK_O: 
	        	 zoomOut = false;
	        	 break;
	         case KeyEvent.VK_UP:
	        	 panCima = false;
	        	 break;
	         case KeyEvent.VK_DOWN:
	        	 panBaixo = false;
	        	 break;
	         case KeyEvent.VK_LEFT:
	        	 panEsquerda = false;
	        	 break;
	         case KeyEvent.VK_RIGHT:
	        	 panDireita = false;
	         case KeyEvent.VK_Z:
	        	 dollyIn = false;
	        	 break;
	         case KeyEvent.VK_X:
	        	 dollyOut = false;
	        	 break;
	         case KeyEvent.VK_E:
	        	 rotacaoXE = false;
	        	 break;
	         case KeyEvent.VK_R:
	        	 rotacaoXD = false;
	        	 break;
	         case KeyEvent.VK_W:
	        	 rotacaoYE = false;
	        	 break;
	         case KeyEvent.VK_S:
	        	 rotacaoYD = false;
	        	 break;
	         case KeyEvent.VK_1:
	        	 lancador1.geraBala = false;
	        	 break;
	         case KeyEvent.VK_2:
	        	 lancador2.geraBala = false;
	        	 break;
	         case KeyEvent.VK_3:
	        	 lancador3.geraBala = false;
	        	 break;
	      }
	}

	public void keyTyped(KeyEvent arg0) {
	}
	
	public void setarIluminacao(GL2 gl){
		float SHINE_ALL_DIRECTIONS = 1;
        float[] lightPos = {-30, 0, 0, SHINE_ALL_DIRECTIONS};
        float[] lightColorAmbient = {0.3f, 0.3f, 0.3f, 1f};
        float[] lightColorSpecular = {0.8f, 0.8f, 0.8f, 1f};

        // Set light parameters.
        gl.glLightfv(GL_LIGHT1, GL_POSITION, lightPos, 0);
        gl.glLightfv(GL_LIGHT1, GL_AMBIENT, lightColorAmbient, 0);
        gl.glLightfv(GL_LIGHT1, GL_SPECULAR, lightColorSpecular, 0);

        // Enable lighting in GL.
        gl.glEnable(GL_LIGHT1);
        gl.glEnable(GL_LIGHTING);

        // Set material properties.
        float[] rgba = {0.3f, 0.5f, 1f};
        gl.glMaterialfv(GL_FRONT, GL_AMBIENT, rgba, 0);
        gl.glMaterialfv(GL_FRONT, GL_SPECULAR, rgba, 0);
        gl.glMaterialf(GL_FRONT, GL_SHININESS, 0.5f);
	}
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2(); 
		drawable.setGL(new DebugGL2(gl));
		glu = new GLU(); 
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glClearDepthf(1.0f);
		gl.glEnable(GL_DEPTH_TEST);
		gl.glDepthFunc(GL_LEQUAL);
		gl.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
		gl.glShadeModel(GL_SMOOTH);
		Vetor3D v1 = new Vetor3D(-1.0f, 0.0f, 0.0f);
		Vetor3D v2 = new Vetor3D(1.0f, 0.0f, 0.0f);
		Vetor3D v3 = new Vetor3D(0.0f, 0.0f, -1.0f);
		lancador1 = new Lancador(v1,1.0f,0.0f,0.0f,0.1f,0.1f,0.2f,glu);
		lancador2 = new Lancador(v2,0.0f,1.0f,0.0f,0.1f,0.1f,0.2f,glu);
		lancador3 = new Lancador(v3,0.0f,0.0f,1.0f,0.1f,0.1f,0.2f,glu);
		
		
	}

	
	public void dispose(GLAutoDrawable drawable) {
	}
	
	

}
