package vector;

import main.DPolygon;

public final class Plane {
	Vector V1, V2, NV;
	double[] P = new double[3];

	/**
	 * �v���[���x�N�g�����쐬����R���X�g���N�^�B
	 * @param DP polygon
	 */
	@SuppressWarnings("unused")
	public Plane(DPolygon DP){
		P[0] = DP.x[0]; 
		P[1] = DP.y[0]; 
		P[2] = DP.z[0]; 

		V1 = new Vector(DP.x[1] - DP.x[0], 
		        		DP.y[1] - DP.y[0], 
		        		DP.z[1] - DP.z[0]);

		V2 = new Vector(DP.x[2] - DP.x[0], 
		        		DP.y[2] - DP.y[0], 
		        		DP.z[2] - DP.z[0]);
		
		//V1��V2�𒼍s������
		NV = V1.CrossProduct(V2);
	}

	/**
	 *
	 * @param VE1 �x�N�g��1
	 * @param VE2 �x�N�g��2
	 * @param Z	  double�̃x�N�g���̒l��ۊǂ���z��
	 * NV : V1��V2�̃N���X��
	 *
	 */
	public Plane(Vector VE1, Vector VE2, double[] Z){
		//double[] Z = P
		P = Z;
		
		V1 = VE1;
		
		V2 = VE2;
		
		NV = V1.CrossProduct(V2);
	}
}