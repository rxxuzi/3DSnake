package main;
import com.sun.xml.internal.ws.developer.Serialization;
import vector.Vector;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.*;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.JPanel;
import vector.*;
import write.Error;

import static main.Main.saves;

/*
* Main.java��Frame�Ƀp�l���Ƃ���add����class
* */
public class Screen extends JPanel {
    @Serialization
	private static final long serialVersionUID = 1L;

	/**
	 * fpsMode��True�ɂ���ƈȉ��̂��Ƃ��N����܂�
	 * * 1.�d�͂̒ǉ�(���V�ł��Ȃ��Ȃ�)
	 * * 2.�J�����̍Œ�z���W��2�ƂȂ�
	 */
	public static final AtomicBoolean firstPersonMode = new AtomicBoolean(false);
	private static final double height = 4.0;
	private static final boolean debugMode = false;
	private static final double cameraSpeed = 0.002; //default => 0.25
	private static final long moveInterval = 10; // default => 0
	private static final double gravity = 0.001; // default => 0.001
	public static ArrayList<DPolygon> DPolygons = new ArrayList<>();
	public static ArrayList<Cube> Cube = new ArrayList<>();
	public static ArrayList<Pyramid> Pyramid = new ArrayList<>();
    private static final int[] colorBox = new int[256 * 256 * 256];
	private static int counter1 = 0;
	static Object PolygonOver = null ; //�J�[�\����̃|���S���̏��
	private static Object FocusPolygon = null;
	private static final long deleteInterval = 200;
	private static final long CubeGenerateInterval = 0; //default -> 100
	private static long LastMoveTime = 0;
	private static long LastCubeDeleteTime = 0;
	private static long LastCubeGenerateTime = 0 ;
	private static int  NumberOfDeleteCube = 0 ;
	private static String dCube = "NONE"; //�폜���ꂽ�L���[�u�̏��
	static final double[] FViewFrom = { -2 , -2 , 10 };
	static final double[] FViewTo = {  -2 , 0 ,  5 };
	public static double[] ViewFrom = FViewFrom.clone(); //�J�����̍��W
	public static double[] ViewTo   = FViewTo.clone();	  //�I�u�W�F�N�g�̍��W
	public static double zoom = 1000;
    static double MinZoom = 100;
    static double MaxZoom = 5000;
	static double MouseX = 0 , MouseY = 0; //�}�E�X�̍��W
	static double MovementSpeed = 0.5; //�}�E�X�̃X�s�[�h
	double drawFPS = 0;
	double MaxFPS = 2000;
	double LastFPSCheck = 0 , Checks = 0 , LastRefresh = 0;
	private double VerticalLook = -0.9; //0.99 ~ -0.99�܂ŁA���̒l�̎��͏�����B���̒l�̎��͉�����
	private double HorizontalLook = 0; // �C�ӂ̐��l���Ƃ�A���W�A���P�ʂň������
	double VerticalRotationSpeed = 1000; //������]�̑���
	double HorizontalRotationSpeed = 500; //������]�̑���
	static final double aimSight = 4;	// �Z���^�[�N���X�̑傫��
	int[] NewOrder; //�z��DPolygon�̕`�悷�鏇�Ԃ�ێ�����z��
	static boolean OutLines = true;
	boolean[] Control = new boolean[15];//�L�[���͂̏����i�[����z��
	private final static int FontSize = 15;
	private static String condition = "NONE";
	int Press = 10;
	public static long t ; //����
	Robot r ;
	Random random = new Random();
    Error ex = new Error();

	public Screen(){
		this.addKeyListener(new KeyTyped());
		setFocusable(true);
		this.addMouseListener(new AboutMouse());
		this.addMouseMotionListener(new AboutMouse());
		this.addMouseWheelListener(new AboutMouse());
		invisibleMouse();


		Cube.add(new Cube(5,5,5,2,2,2,Color.green));
		Cube.add(new Cube(5,3,6,2,2,2,Color.blue));
		Cube.add(new Cube(5,2,7,2,2,2,Color.red));



		new Ball(3,3,3,4,4,4,Color.MAGENTA);

		new Ground();
		new Floor();
        new TextToObject("./rsc/summon.txt");

	}
	/*�`��Ɋւ��郁�\�b�h*/

	public void paintComponent(Graphics g){
		//�`�惊�Z�b�g
		g.clearRect(0, 0, (int)Main.screenSize.getWidth(), (int)Main.screenSize.getHeight());

		//�J�����𓮂���
		KeyControl();

		//�t�H�[�J�X���ꂽ�|���S�����폜����
		deleteCube();

		//���̃J�����ʒu�ň�ʓI�Ȃ��̂����ׂČv�Z���܂��B
		Calculator.VectorInfo();

		// �|���S�������e���A�b�v�f�[�g
		for (DPolygon dPolygon : DPolygons) {
			dPolygon.updatePolygon();
		}

		//�S�Ẵ|���S���̋������\�[�g
		setOrder();

		//�}�E�X������Ă���|���S������肷��
		setPolygonOver();

		//setOrder�֐��Őݒ肳�ꂽ�����Ń|���S����`��
		for (int j : NewOrder) {
			DPolygons.get(j).DrawablePolygon.drawPolygon(g);
		}

		//��ʂ̒����ɃG�C��������
		drawMouseAim(g);

		//�t�H���g�̐ݒ�
		Font font = new Font(Font.DIALOG, Font.ITALIC,FontSize);
		//
		double VAngle =  Math.toDegrees(Math.tan(VerticalLook));

		g.setFont(font);

		snakeMove();

		g.drawString("FPS : " + (int)drawFPS , 10, 15);
		g.drawString("ELAPSED TIME : " + (System.currentTimeMillis() - Main.StartUpTime ) + "ms" , 10 , 30);
		g.drawString("OBJECT : " + Arrays.toString(ViewTo)   , 10 ,45);
		g.drawString("CAMERA : " + Arrays.toString(ViewFrom) , 10 ,60);
		g.drawString("ZOOM   : " + zoom , 10 , 75);
		g.drawString("Vertical   Look : " + VerticalLook , 10 , 90);
		g.drawString("Horizontal Look(rad) : " + HorizontalLook , 10 , 105);
		g.drawString("Vertical angle   	 : " + (int)VAngle + "��" , 10 ,120);
		g.drawString("Number Of Polygons : " + DPolygons.size() , 10 ,135);
		g.drawString("Number Of Cubes    : " + Cube.size() , 10 ,150);
		try{
			g.drawString("Focus Polys ID : " + FocusPolygon.toString() , 10 ,170);
		}catch (NullPointerException e){
            g.drawString("Focus Polys ID : " + "NULL" , 10 ,170);
            Error.write(e);
        }
		g.drawString(t +"s", 10,200);
		g.setFont(new Font(Font.SANS_SERIF , Font.BOLD , 20));
		g.drawString("CONDITION: " + condition , 10 ,190);

		if(Control[10]){
			Press++;
		}
		g.drawString(Press + "SIZE" , 10 , 220);

		//�`��X�V�̃C���^�[�o��
		SleepAndRefresh();

		if(firstPersonMode.get()){
			hitJudgment();
		}

		for(main.Cube c : Cube){
			c.setDisplayCube();
		}

	}

	private void snakeMove() {
		double dx = 0.1;
		double dy = 0.1;
		for(Cube c : Cube){
            if(c.move){
                c.reflection(dx,dy,0.1);
                c.updatePoly();
            }
        }
	}

	private void SleepAndRefresh(){
		long timeSLU = (long) (System.currentTimeMillis() - LastRefresh);

		Checks ++;

		if(Checks >= 15){
			drawFPS = Checks/((System.currentTimeMillis() - LastFPSCheck)/1000.0);
			LastFPSCheck = System.currentTimeMillis();
			Checks = 0;
//			System.gc();
		}

		if(timeSLU < 1000.0/MaxFPS){
			try {
				Thread.sleep((long) (1000.0/MaxFPS - timeSLU));
			} catch (InterruptedException e) {
                Error.write(e);
			}
		}
		LastRefresh = System.currentTimeMillis();
		repaint();
		t = (System.currentTimeMillis() - Main.StartUpTime) / 1000;
	}


	private void hitJudgment() {
		
		if(
				Cube.get(0).x < ViewFrom[0] && Cube.get(0).dx >ViewFrom[0] && Cube.get(0).y < ViewFrom[1] && Cube.get(0).dy > ViewFrom[1]
		){
			condition = "in the BOX";
			if (Math.abs(Cube.get(0).x - ViewFrom[0]) > Math.abs(Cube.get(0).dx - ViewFrom[0])) {
				ViewFrom[0] += 0.1;
			}else{
				ViewFrom[0] -= 0.1;
			}

			if (Math.abs(Cube.get(0).y - ViewFrom[1]) > Math.abs(Cube.get(0).dy - ViewFrom[1])) {
				ViewFrom[1] += 0.1;
			}else{
				ViewFrom[1] -= 0.1;
			}
		}else {
			condition = "NONE";
		}
		
		
	}

	@SuppressWarnings("unused")
	private void lowerLimit() {
		if(ViewFrom[2] < 3.0) {
            ViewFrom[2] = 3.0;
        }
	}

	/**
	 * Control7�������ꂽ���A�t�H�[�J�X���Ă���L���[�u����肵�č폜����
	 *�@�A���ŏ�����̂�h���ׁAdelete interval��݂��Ă���
	 */

	private void deleteCube() {
		if(Control[7]) {
			if(System.currentTimeMillis() - LastCubeDeleteTime >= deleteInterval) {
				for(int i = 0 ; i < Cube.size() ; i ++) {
					for(int j = 0 ; j < Cube.get(i).Polys.length ; j ++) {
						if( Cube.get(i).Polys[j].DrawablePolygon.equals(FocusPolygon) ) {
							dCube = Cube.get(i).toString();
							Cube.get(i).removeCube();
							LastCubeDeleteTime = System.currentTimeMillis();
							NumberOfDeleteCube ++ ;
							condition = "CUBE DELETED : " + dCube;

							break;
						}
					}
				}
			}
		}

		//�S�폜
		if(Control[9]) {
			NumberOfDeleteCube += Cube.size();
			for(int i = 0 ; i < Cube.size() ; i ++ ) {
				Cube.get(i).removeCube();
				condition = "ALL DELETE";
			}
		}
	}

	//���������\�[�g
	private void setOrder(){
		//���������i�[����z��
		double[] k = new double[DPolygons.size()];
		//�\�[�g����������
		NewOrder = new int[DPolygons.size()];

		for(int i = 0 ; i < DPolygons.size() ; i++) {
			k[i] = DPolygons.get(i).AverageDistance;
			NewOrder[i] = i ;
		}

		/*
		���ϋ����̒Z�����Ƀ\�[�g����
		 (�o�u���\�[�g)
		*/
		double d_tmp ;
		int itmp;
		for(int i = 0 ; i < k.length - 1 ; i++) {
			for(int j = 0 ; j < k.length - 1 ; j++) {
				if(k[j] < k[j + 1]) {
					d_tmp = k[j];
					itmp = NewOrder[j];
					NewOrder[j] = NewOrder[j + 1];
					k[j] = k[j + 1];
					NewOrder[j + 1] = itmp;
					k[j + 1] = d_tmp;
				}
			}
		}
	}

	//�}�E�X���\��
	private void invisibleMouse(){
		 Toolkit toolkit = Toolkit.getDefaultToolkit();
		 BufferedImage cursorImage = new BufferedImage(1, 1, BufferedImage.TRANSLUCENT);
		 Cursor invisibleCursor = toolkit.createCustomCursor(cursorImage, new Point(0,0), "InvisibleCursor");
		 setCursor(invisibleCursor);
	}

	//�}�E�X�G�C����`��
	private void drawMouseAim(Graphics g){
		g.setColor(Color.black);
		g.drawLine((int)(Main.screenSize.getWidth()/2 - aimSight), (int)(Main.screenSize.getHeight()/2), (int)(Main.screenSize.getWidth()/2 + aimSight), (int)(Main.screenSize.getHeight()/2));
		g.drawLine((int)(Main.screenSize.getWidth()/2), (int)(Main.screenSize.getHeight()/2 - aimSight), (int)(Main.screenSize.getWidth()/2), (int)(Main.screenSize.getHeight()/2 + aimSight));
	}



	//�L�[���͂𐧌�
	private void KeyControl(){
		Vector ViewVector = new Vector(ViewTo[0] - ViewFrom[0], ViewTo[1] - ViewFrom[1], ViewTo[2] - ViewFrom[2]);
		double xMove = 0, yMove = 0, zMove = 0;

		//(����)�P�ʃx�N�g�����擾
		Vector VerticalVector = new Vector (0, 0, 1);
		//�����ɓ����x�N�g��
		Vector SideViewVector = ViewVector.CrossProduct(VerticalVector);
		//�����ۂ̎��Ԃ��擾
		long moveTime = System.currentTimeMillis();
		//�����X�s�[�h���v�Z
		if(moveTime - LastMoveTime > moveInterval){
			//�O�Ɉړ�
			if(Control[0]){
				xMove += ViewVector.x * cameraSpeed;
				yMove += ViewVector.y * cameraSpeed;
				zMove += ViewVector.z * cameraSpeed;
			}

			//���Ɉړ�
			if(Control[2]){
				xMove -= ViewVector.x * cameraSpeed;
				yMove -= ViewVector.y * cameraSpeed;
				zMove -= ViewVector.z * cameraSpeed;
			}
			//���Ɉړ�
			if(Control[1]){
				xMove += SideViewVector.x * cameraSpeed;
				yMove += SideViewVector.y * cameraSpeed;
				zMove += SideViewVector.z * cameraSpeed;
			}
			//�E�Ɉړ�
			if(Control[3]){
				xMove -= SideViewVector.x * cameraSpeed;
				yMove -= SideViewVector.y * cameraSpeed;
				zMove -= SideViewVector.z * cameraSpeed;
			}

            LastMoveTime = System.currentTimeMillis();
        }

		
		//�ړ�����x�N�g��
		Vector MoveVector = new Vector(xMove, yMove, zMove);
		double fx = MoveVector.x * MovementSpeed;
		double fy = MoveVector.y * MovementSpeed;
		double fz = MoveVector.z * MovementSpeed;
		
		MoveTo(ViewFrom[0] + fx, ViewFrom[1] + fy, ViewFrom[2] + fz);
		
		//�J�������W�����Z�b�g
		if(Control[4]) {
			for(int i = 0 ; i < FViewFrom.length ; i ++) {
				ViewFrom[i] = FViewFrom[i];
				ViewTo[i] = FViewTo[i];
			}
			zoom = 1000;
			condition = "View Reset";
		}
		
		//���̕�����z�ړ�
		if(Control[5]) {
			ViewFrom[2] += 0.4;
			ViewTo[2] += 0.4;	
			condition = "FLY";
		}
		
		//���̕�����z�ړ�
		if(Control[6]) {
			//z < 0�̏ꍇz = 0�Ŏ~�߂�
			if(ViewFrom[2] > 0.0) {
				ViewFrom[2] -= 0.4 + gravity;
				ViewTo[2] -= 0.4 + gravity;
			}else {
				ViewFrom[2] -= 0.1;
				ViewTo[2] -= 0.1;
			}
		}

		//�����_���ȃL���[�u�𐶐�
		if(Control[8]) {
			long generate = System.currentTimeMillis();
			if(Math.abs(LastCubeGenerateTime - generate) > CubeGenerateInterval) {
				int rx = random.nextInt(255);
				int ry = random.nextInt(255);
				int rz = random.nextInt(255);
				int xyz = rx * 1_000_000 + ry * 1000 + rz;

				colorBox[counter1] = xyz;

				if(CoordinateCheck(xyz)) {
					Cube.add(new Cube(-rx/25d , ry/25d , rz/25d + 2, 1, 1, 1, new Color(rx , ry , rz) ));
					condition = "CUBE GENERATED : (x,y,z) = " + rx /25 +"," + ry /25 + "," + rz /25;
					saves.write(rx /25 +"," + ry /25 + "," + rz /25 , new Color(rx , ry , rz));
				}
				
				counter1 ++ ;
				LastCubeGenerateTime = System.currentTimeMillis();
			}
		}
	}

	//�L���[�u���d�����Ă��Ȃ����`�F�b�N
	private boolean CoordinateCheck(Integer xyz) {
		for(int i = 0 ; i < counter1 ; i ++) {
			if(colorBox[i] == xyz) {
				return false;
			}
		}
		return true;
	}

	//�J�����̍��W�����߂郁�\�b�h
	private void MoveTo(double x, double y, double z){
		ViewFrom[0] = x;
		ViewFrom[1] = y;
		ViewFrom[2] = z;

		if(firstPersonMode.get()){
			ViewFrom[2] = height;
		}else{
			if(ViewFrom[2] < 0.0) ViewFrom[2] = 0.0;
		}

		//�`��X�V
		updateView();
	}
	
	//�}�E�X������Ă���|���S�������
	private void setPolygonOver(){
		//��xnull�ƒ�`
		PolygonOver = null;
		//�T��
		for(int i = NewOrder.length-1; i >= 0; i--) {
			if(DPolygons.get(NewOrder[i]).DrawablePolygon.MouseOver() && DPolygons.get(NewOrder[i]).draw && DPolygons.get(NewOrder[i]).DrawablePolygon.visible){
				FocusPolygon = DPolygons.get(NewOrder[i]).DrawablePolygon;
				PolygonOver = DPolygons.get(NewOrder[i]).DrawablePolygon;
				
				break;
			}
		}	
	}

	private void MouseMovement(double NewX, double NewY){		
		//�}�E�X��y��(�X�N���[���̒���)����ǂꂾ���͂Ȃꂽ���v��
		double difX = (NewX - Main.screenSize.getWidth()/2);
		//�}�E�X��x��(�X�N���[���̒���)����ǂꂾ���͂Ȃꂽ���v��
		double difY = (NewY - Main.screenSize.getHeight()/2);
		difY *= 6 - Math.abs(VerticalLook) * 5;
		
		VerticalLook   -= difY  / VerticalRotationSpeed;
		HorizontalLook += difX / HorizontalRotationSpeed;
		
		//VerticalLook�̐�Βl��1.0�ȏ�ɂȂ�Ȃ��悤�ɂ���
		if(VerticalLook>0.999) VerticalLook = 0.999;
		if(VerticalLook<-0.999) VerticalLook = -0.999;
		
		updateView();
	}
	
	//���_���A�b�v�f�[�g
	private void updateView(){
		double r = Math.sqrt(1 - (VerticalLook * VerticalLook));
		ViewTo[0] = ViewFrom[0] + r * Math.cos(HorizontalLook); // x���ړ�
		ViewTo[1] = ViewFrom[1] + r * Math.sin(HorizontalLook);	// y���ړ�
		ViewTo[2] = ViewFrom[2] + VerticalLook;					// z���ړ�	
	}
	
	/*�L�[���͗pclass*/
	class KeyTyped extends KeyAdapter{

		public boolean Tab = false;
		
		//�L�[������������true
		public void keyPressed(KeyEvent e) {
			
			switch(e.getKeyCode()) {
				case KeyEvent.VK_W : 	 Control[0] = true ; break; //�O�i
				case KeyEvent.VK_A : 	 Control[1] = true ; break; //���
				case KeyEvent.VK_S : 	 Control[2] = true ; break; //����
				case KeyEvent.VK_D :	 Control[3] = true ; break; //�E��
				case KeyEvent.VK_X : 	 Control[4] = true ; break; //���_���Z�b�g
				case KeyEvent.VK_SPACE:	 Control[5] = true ; break; //���
				case KeyEvent.VK_SHIFT:	 Control[6] = true ; break; //����
				case KeyEvent.VK_BACK_SPACE: Control[7] = true ; break; //����
				case KeyEvent.VK_R : 	 Control[8] = true ; break; //�L���[�u�𐶐�
				case KeyEvent.VK_DELETE :Control[9] = true ; break; //�L���[�u���폜
				case KeyEvent.VK_O 	:	OutLines = !OutLines; //���C���폜
					if(OutLines) {
						condition = "Show outer frame";
					}else {
						condition = "Hide outer frame";
					}
					break;
				case KeyEvent.VK_ENTER : Control[10] = true ; break;
				case KeyEvent.VK_ESCAPE : System.exit(0); break; //Escape�L�[�������ƏI��
				case KeyEvent.VK_TAB:
					Tab = !Tab;
					Ground.Debug =  !Ground.Debug;
					break; //�^�u�L�����Z�b�g
			}

		}
		
		//�L�[�𗣂�������false
		public void keyReleased(KeyEvent e) {
			
			switch(e.getKeyCode()) {
				case KeyEvent.VK_W : 	 Control[0] = false ; break;
				case KeyEvent.VK_A : 	 Control[1] = false ; break;
				case KeyEvent.VK_S : 	 Control[2] = false ; break;
				case KeyEvent.VK_D :	 Control[3] = false ; break;
				case KeyEvent.VK_X : 	 Control[4] = false ; break;
				case KeyEvent.VK_SPACE:	 Control[5] = false ; condition = "NONE"; break;
				case KeyEvent.VK_SHIFT:	 Control[6] = false ; break;
				case KeyEvent.VK_BACK_SPACE: Control[7] = false ; break;
				case KeyEvent.VK_R : 	 	 Control[8] = false ; break;
				case KeyEvent.VK_DELETE :Control[9] = false ; break;
				case KeyEvent.VK_ENTER: Control[10] = false ; break;
			}
		}
	}
	
	/*
	 * �}�E�X�̏��������N���X
	 * �}�E�X�̍��W�A�}�E�X�N���b�N���A�}�E�X�z�C�[���̏�������
	 */
	class AboutMouse implements MouseListener , MouseMotionListener, MouseWheelListener{
		
		//�}�E�X�𒆉��Ɉړ������郁�\�b�h
		void CenterMouse(){
			try {
				r = new Robot();
				r.mouseMove((int)Main.screenSize.getWidth()/2, (int)Main.screenSize.getHeight()/2);
			} catch (AWTException e) {
                Error.write(e);
			}
		}
		
		//�}�E�X���h���b�O�����Ƃ��̐���		
		public void mouseDragged(MouseEvent e) {
			MouseMovement(e.getX(), e.getY());
			MouseX = e.getX();
			MouseY = e.getY();
			CenterMouse();
		}

		//�}�E�X�̓����𐧌�
		public void mouseMoved(MouseEvent e) {
			MouseMovement(e.getX(), e.getY());
			MouseX = e.getX();
			MouseY = e.getY();
			CenterMouse();
		}
		
		public void mouseClicked(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}
		
		//�}�E�X�̃N���b�N�������Ƃ��̐���
		public void mousePressed(MouseEvent e) {
			//�E�N���b�N
			if(e.getButton() == MouseEvent.BUTTON1) {
				if(PolygonOver != null) PolygonOver.seeThrough = false;				
			}

			//���N���b�N
			if(e.getButton() == MouseEvent.BUTTON3) {				
				if(PolygonOver != null) PolygonOver.seeThrough = true;
			}
		}

		public void mouseReleased(MouseEvent e) {
		}
		public void mouseWheelMoved(MouseWheelEvent e) {
			if(e.getUnitsToScroll()>0){
				if(zoom > MinZoom) zoom -= 25 * e.getUnitsToScroll();
				condition = "Zoom out";
			}else{
				if(zoom < MaxZoom) zoom -= 25 * e.getUnitsToScroll();
				condition = "Zoom in";
			}
		}
	}

	@Override
	public String toString() {
		return " FPS MODE : " + firstPersonMode
				+ "\n DEBUG MODE : " + debugMode
				+ "\n GRAVITY	 : " + gravity;
	}
}