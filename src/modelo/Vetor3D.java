package modelo;

public class Vetor3D {
	public float x;
	public float y;
	public float z;
	
	public Vetor3D(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public Vetor3D() {
		this.x = 0.0f;
		this.y = 0.0f;
		this.z = 0.0f;
	}
	
	public float comprimento(){
		return (float) Math.sqrt(x * x + y * y + z * z);
	}
	public Vetor3D somaVetor(Vetor3D v){
		this.x+=v.x;
		this.y+=v.y;
		this.z+=v.z;
		return this;
	}
	
	public Vetor3D subtraiVetor(Vetor3D v){
		this.x-=v.x;
		this.y-=v.y;
		this.z-=v.z;
		return this;
	}
	
	public Vetor3D produtoPorEscalar(float e){
		x *= e;
		y *= e;
		z *= e;
		return this;
	}
	
	public void normalizar(){
		float comprimento = comprimento();
		x /= comprimento;
		y /= comprimento;
		z /= comprimento;
	}
}
