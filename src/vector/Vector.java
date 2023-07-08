package vector;

/**
 * �x�N�g���v�Z�p�N���X
 * @since 1.0
 * @author rxxuzi
 *
 */
public class Vector {
	public double x;
	public double y;
	public double z;
	public Vector(double x, double y, double z){
		/*�x�N�g���̑傫�����v�Z*/
		double length = Length(x,y,z);
		/*���`�Ɨ��̎��Ɏ��s�B���`�]���̎��ɂ�x,y,z�̒l��0��*/
		if(length>0){
			this.x = x/length;
			this.y = y/length;
			this.z = z/length;
		}
	}
	/*�x�N�g���̑傫��*/
	double Length(double x,  double y, double z){
		return Math.sqrt(x*x + y*y + z*z);
	}
	/*�x�N�g���̒��p�Ɍ�������x�N�g���̍쐬*/
	public Vector CrossProduct(Vector V){
		return new Vector(
				y * V.z - z * V.y,
				z * V.x - x * V.z,
				x * V.y - y * V.x);
	}
	/*����*/
	double DotProduct(Vector V){
		return x * V.x + y * V.y + z * V.z;
	}
	/*�O��*/
	Vector VectorProduct(Vector V1 , Vector V2){
        return new Vector(
				V1.y * V2.z - V1.z * V2.y,
				V1.z * V2.x - V1.x * V2.z,
				V1.x * V2.y - V1.y * V2.x);
    }

	double Angle(Vector V){
		return Math.acos(DotProduct(V));
	}
	/*�x�C�A���̒��������߂�*/
	double Length(Vector V){
		return Math.sqrt(x*x + y*y + z*z);
	}
}