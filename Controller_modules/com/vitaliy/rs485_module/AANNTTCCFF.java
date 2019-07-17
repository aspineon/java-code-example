package com.vitaliy.rs485_module;
/**
 * ��������� ��������� ������������ ������
 */
public class AANNTTCCFF extends Command{
	private String value_AA;
        private String value_NN;
        private String value_TT;
        private String value_CC;
        private String value_FF;
        /**
	 * �����������, ������� ���������� ������ � �������,
	 * ��� ��������� ������������� ����������� ����������� ���������� 
	 * @param AA - ����� �������������� ������
	 * @param NN - ����� ����� �������������� ������
	 * @param TT - ��� ����� �������������� ������
	 * @param CC - ����� �������� �������� �������� �������������� ������
	 * @param FF - ����� ������ ������ �������������� ������
	 */
	public AANNTTCCFF(String _AA,String _NN, String _TT, String _CC, String _FF) throws Exception{
		if(utility.isHEX_byte(_AA)
                 &&utility.isHEX_byte(_NN)
                 &&TT.isTTkod(_TT)
                 &&CC.isCCkod(_CC)
                 &&FF.isFFkod(_FF)){
                    this.value_AA=_AA;
                    this.value_NN=_NN;
                    this.value_TT=_TT;
                    this.value_CC=_CC;
                    this.value_FF=_FF;
                }
                else{
                    throw new Exception ("Create error - parameter's not valid");
                }
	}
	/**
	 * ����������� ��� ����������
	 */
	public AANNTTCCFF(){
            this.value_AA="00";
            this.value_NN="00";
            this.value_TT=TT.getDefaultKod();
            this.value_CC=CC.getDefaultKod();
            this.value_FF=FF.getDefaultKod();
	}
        /**
         * ������� ���������� �������
         */
        public void clear(){
            this.value_AA="00";
            this.value_NN="00";
            this.value_TT=TT.getDefaultKod();
            this.value_CC=CC.getDefaultKod();
            this.value_FF=FF.getDefaultKod();
        }
        /**
         * ���������� AA - ����� �������������� ������
         */
        public boolean set_AA(String _AA){
            boolean result=false;
            if(utility.isHEX_byte(_AA)){
                this.value_AA=new String(_AA);
                result=true;
            }
            return result;
        }
        /**
         * ���������� NN - ����� ����� �������������� ������
         */
        public boolean set_NN(String _NN){
            boolean result=false;
            if(utility.isHEX_byte(_NN)){
                this.value_NN=new String(_NN);
                result=true;
            }
            return result;
        }
        /**
         * ���������� ��� ����� �������������� ������
         */
        public boolean set_TT(String _TT){
            boolean result=false;
            if(TT.isTTkod(_TT)){
                this.value_TT=new String(_TT);
                result=true;
            }
            return result;
        }
        /**
         * ���������� CC - ����� �������� �������� �������� �������������� ������
         */
        public boolean set_CC(String _CC){
            boolean result=false;
            if(CC.isCCkod(_CC)){
                this.value_CC=new String(_CC);
                result=true;
            }
            return result;
        }
        /**
         * ���������� FF - ����� ������ ������ �������������� ������
         */
        public boolean set_FF(String _FF){
            boolean result=false;
            if(FF.isFFkod(_FF)){
                this.value_FF=new String(_FF);
                result=true;
            }
            return result;
        }
       
        /**
         * �������� ������� ��� �������� ������
         */
        public String get_command(){
            return "%"+this.value_AA+this.value_NN+this.value_TT+this.value_CC+this.value_FF;
        }
        /**
         * ��������� ���������� ������
         */
        public boolean response_is_valid(String response){
            boolean result=false;
            if(response.length()>=3){
                if(response.charAt(0)=='!'){
                    result=true;
                }
            }
            else{
                // �������� �������� � ������ - ��� ������ �� ������� - ������ ��������
            }
            return result;
        }
        /**
         * 
         */
        public String get_name(){
        	return "AANNTTCCFF";
        }
}
