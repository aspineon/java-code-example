 try{
	// ����� ��� ������ � ���� ��������� ���������� �� ������ ������
        PrintStream ps=new PrintStream(new FileOutputStream("out.txt"));
        // ����� � ���� ������
	ps.println("This is a text to file");
        ps.println(" PrintStream to file");
	// �������� ������
        ps.close();
    }
    catch(IOException e){
       System.out.println(" ������ �����-������ "+e);
    }
    catch(Exception e){
       System.out.println("other Exception");
    }
