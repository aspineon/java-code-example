   try{
        // ������� ������������ ������ � �����
        RandomAccessFile raf=new RandomAccessFile("2.txt","rw");
	// �������� ������ � ����
        for(int i=0;i<10;i++){
           raf.writeInt(i+10);
        };
        // ������� � ����� �� �������
        raf.seek(0);
	// ��������� ������ �� �����, �.�. �� ��������� � ������ �����
        for(int i=0;i<10;i++){
           System.out.println(raf.readInt());
        }
    }
    catch(java.io.FileNotFoundException e){
       System.out.println("File not found");
    }
    catch(Exception e){
       System.out.println("other Exception");
    }
