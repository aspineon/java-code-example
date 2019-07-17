package Utility;
import java.io.*;
import java.util.*;
import java.awt.image.BufferedImage;
import java.awt.image.renderable.*;
import javax.media.jai.*;
import com.sun.media.jai.codec.*;

/*
�������� ���������: Windows JDK Install � Windows JRE Install - �������� ����������� ����� � ��� �� ������:

    * checkmmx.exe
    * mlib_jai.dll
    * mlib_jai_mmx.dll

� ������� bin �

    * jai_codec.jar
    * jai_core.jar
    * mlibwrapper_jai.jar

� ������� lib\ext.

������� ����������� ������ � ���, ��� ��������� ��� JDK �������� � ��� ���� � �������������� JDK (��������, "C:\ j2sdk1.4.2_01"), � ��������� ��������� ��� JRE �������� ���� � JRE (��������, "C:\ j2sdk1.4.2_01\jre" ��� "C:\Program Files\Java\j2re1.4.2_01").
��������� ��������� Windows CLASSPATH Install ��-��������� ���������� ��������� ���� JAI ����� � �������� "C:\jai-1_1_2". ����� ��� �������� � ���������� ��������� CLASSPATH ������� �������� ���� "C:\jai-1_1_2\lib" (��� �������). ���� ������� ��������� ����� ������, ��� ��������� ��� JDK ��� JRE.

����� ��������, ��� ������� ������ checkmmx.exe, mlib_jai.dll � mlib_jai_mmx.dll �� �����������, �� �� ����������� ���-���� ����������� �������� � �������� ������ JAI. 
*/

public class ImageUtils {
	private RenderedOp image = null;
	private RenderedOp result = null;
	private int height = 0;
	private int width = 0;
	
	/** ��������� ����������� */
	public BufferedImage load(String file) throws IOException {
		FileSeekableStream fss = new FileSeekableStream(file);
		image = JAI.create("stream", fss);
		height = image.getHeight();
		width = image.getWidth();
		System.out.println("image:"+image);
		System.out.println("height:"+height);
		System.out.println("width:"+width);
		return image.getAsBufferedImage();
	}

	/** ��������� ����������� �� ����*/
	public void writeResult(String file, String type) throws IOException {
		FileOutputStream os = new FileOutputStream(file);
		JAI.create("encode", result, os, type, null);
	}

	/** ������� ����������� ����� */
	public BufferedImage thumbnail(float edgeLength) {
		boolean tall = (height > width);
		float modifier = edgeLength / (float) (tall ? height : width);
			
		ParameterBlock params = new ParameterBlock();
		params.addSource(image);
			
		params.add(modifier);//x scale factor
		params.add(modifier);//y scale factor
		params.add(0.0F);//x translate
		params.add(0.0F);//y translate
		params.add(new InterpolationNearest());//����� ������������ 
		
		return JAI.create("scale", params).getAsBufferedImage();
	}

	/** �������� ����������� */
	public BufferedImage crop(float edge) {
		ParameterBlock params = new ParameterBlock();
		params.addSource(image);
			
		params.add(edge);//���-�� ���������� �������� �� x 
		params.add(edge);// ���-�� ���������� �������� �� y
		params.add((float) width - edge);//������ ����� ���������
		params.add((float) height - edge);//������ ����� ���������
		
		return JAI.create("crop", params).getAsBufferedImage();
	}

	/** ����� ������ ����������� */
	public BufferedImage border(int edge, double edgeColor) {
		ParameterBlock params = new ParameterBlock();
		params.addSource(image);
			
		params.add(edge);//������ ����� �����
		params.add(edge);// ������ ����� ������
		params.add(edge);// ������ ����� ������
		params.add(edge);// ������ ����� �����
		double[] fill = {edgeColor};
		params.add(new BorderExtenderConstant(fill));//���
		params.add(edgeColor);//���� �����
		
		return JAI.create("border", params).getAsBufferedImage();
	}
	

}
