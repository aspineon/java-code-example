       // ���������� �����
       source=new URL("http://www.ya.ru");

       // �������� �������� � ������� ����
       getAppletContext().showDocument(source,"_self");

       // ������� �������� ����� �� ������ source.openStream()
       InputStreamReader isr=new InputStreamReader(source.openStream());

       // ����� ��� ��������� ������
       BufferedReader br=new BufferedReader(isr);

       // ��������� ��� �� ������
       String line=br.readLine();
       while(line!=null){
          System.out.println(line);
          line=br.readLine();
       }
