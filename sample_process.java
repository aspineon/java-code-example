class sample_process{
	sample_process(){
		// �������� ������ �������� ������� ����������
		Runtime r=Runtime.getRuntime();
		try{
			System.out.println("run program");
			// ������� Process � ��������� ��� �� ����������
			Process p=r.exec("calc");
			// ��������� �������
			//p.destroy();
			// �������� ��������� ��������
			p.waitFor();
			System.out.println("end run program");
		}
		catch(Exception e){
			System.out.println("Error in run program \n"+e.getMessage());
		};
	}
}
