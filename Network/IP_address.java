        InetAddress IP;

        InetAddress[] IP_all;

        URL source;

        // �������� ����� LocalHost
        IP=InetAddress.getLocalHost();
        System.out.println("IP:"+IP);

        // �������� IP ����� �� ����� ����
        IP=InetAddress.getByName("www.ya.ru");
        System.out.println("IP:"+IP);

        // �������� ��� IP �� �����
       IP_all=InetAddress.getAllByName("mail");
        for(int i=0;i<IP_all.length;i++){
       System.out.println(IP_all[i]);
       }
