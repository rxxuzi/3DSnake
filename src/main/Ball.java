package main;

import java.awt.*;
import java.awt.Graphics;

public class Ball extends Polyhedron{
    //���W
    double x, y, z, dx, dy, dz;
    double rotation = Math.PI*0.75;
    double[] RotAdd = new double[4];
    //�F���
    Color color;
    //��]��̍��W
    double x1, x2, x3, x4, y1, y2, y3, y4;
    DPolygon[] Polys = new DPolygon[6];
    //�p�x�����[����z��
    double[] angle;

    final static double e = 0.0001;
    //update�Ŏ��s����鏈��
    public boolean isDisplay = true;

    //x,y,z���W
    double a = x + dx;
    double b = y + dy;
    double c = z + dz;

    //�J�����Ɨ������ő勗��
    public static final double maxDis = 100d;

    public Ball(double x, double y, double z, double dx, double dy, double dz, Color color) {
        Polys[0] = new DPolygon(new double[]{x, a, a, x}, new double[]{y, y, b, b},  new double[]{z, z, z, z}, color, false);
        Polys[1] = new DPolygon(new double[]{x, a, a, x}, new double[]{y, y, b, b},  new double[]{c, c, c, c}, color, false);
        Polys[2] = new DPolygon(new double[]{x, x, x, x}, new double[]{y, y, b, b},  new double[]{z, c, c, z}, color, false);
        Polys[3] = new DPolygon(new double[]{a, a, a, a}, new double[]{y, y, b, b},  new double[]{z, c, c, z}, color, false);
        Polys[4] = new DPolygon(new double[]{x, x, a, a}, new double[]{y, y, y, y},  new double[]{z, c, c, z}, color, false);
        Polys[5] = new DPolygon(new double[]{x, x, a, a}, new double[]{b, b, b, b},  new double[]{z, c, c, z}, color, false);

        //Screen.java��DPolygons<List>�ɓ]��
        Screen.DPolygons.add(Polys[0]);
        Screen.DPolygons.add(Polys[1]);
        Screen.DPolygons.add(Polys[2]);
        Screen.DPolygons.add(Polys[3]);
        Screen.DPolygons.add(Polys[4]);
        Screen.DPolygons.add(Polys[5]);

        //�C���X�^���X�ϐ��ɑ��
        this.color = color;
        this.x = x;
        this.y = y;
        this.z = z;
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;

        //�p�x�����擾
        setRotAdd();

        setDisplayCube();

        if(isDisplay){
            updatePoly();
        }
    }



}
