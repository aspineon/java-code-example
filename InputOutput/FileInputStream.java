    try{
        // ������ � ����
	//FileOutputStream os= new FileOutputStream("2.txt");
        //os.write(48);os.write(48);os.write(49);os.close();
        
	// ������ �� �����
	FileInputStream is= new FileInputStream("2.txt");
        while((b=is.read())!=-1){
        System.out.print(b);}
    }
    // ������� Exception, ���� ���� �� ������
    catch(java.io.FileNotFoundException e){
       System.out.println("File not found");
    }
    // ������� ������ Exception
    catch(IOException e){
       System.out.println("other Input Output Exception");
    }
    catch(Exception e){
       System.out.println("other Exception");
    }

