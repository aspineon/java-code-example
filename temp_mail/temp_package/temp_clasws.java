package temp_package;
   class utility{
	   public static String convert(char ch){
		   String result=ch+"";
		   if(ch=='�')result="yo"+"";
		   if(ch=='�')result="ye"+"";
		   if(ch=='�')result="yi"+"";
		   if(ch=='�')result="A"+"";
		   if(ch=='�')result="B"+"";
		   if(ch=='�')result="V"+"";
		   if(ch=='�')result="G"+"";
		   if(ch=='�')result="D"+"";
		   if(ch=='�')result="E"+"";
		   if(ch=='�')result="G"+"";
		   if(ch=='�')result="Z"+"";
		   if(ch=='�')result="I"+"";
		   if(ch=='�')result="Y"+"";
		   if(ch=='�')result="K"+"";
		   if(ch=='�')result="L"+"";
		   if(ch=='�')result="M"+"";
		   if(ch=='�')result="N"+"";
		   if(ch=='�')result="O"+"";
		   if(ch=='�')result="P"+"";
		   if(ch=='�')result="R"+"";
		   if(ch=='�')result="S"+"";
		   if(ch=='�')result="T"+"";
		   if(ch=='�')result="U"+"";
		   if(ch=='�')result="F"+"";
		   if(ch=='�')result="H"+"";
		   if(ch=='�')result="C"+"";
		   if(ch=='�')result="CH"+"";
		   if(ch=='�')result="SH"+"";
		   if(ch=='�')result="SCH"+"";
		   if(ch=='�')result=""+"";
		   if(ch=='�')result="Y"+"";
		   if(ch=='�')result=""+"";
		   if(ch=='�')result="E"+"";
		   if(ch=='�')result="YU"+"";
		   if(ch=='�')result="YA"+"";
		   if(ch=='�')result="a"+"";
		   if(ch=='�')result="b"+"";
		   if(ch=='�')result="v"+"";
		   if(ch=='�')result="g"+"";
		   if(ch=='�')result="d"+"";
		   if(ch=='�')result="e"+"";
		   if(ch=='�')result="g"+"";
		   if(ch=='�')result="z"+"";
		   if(ch=='�')result="i"+"";
		   if(ch=='�')result="y"+"";
		   if(ch=='�')result="k"+"";
		   if(ch=='�')result="l"+"";
		   if(ch=='�')result="m"+"";
		   if(ch=='�')result="n"+"";
		   if(ch=='�')result="o"+"";
		   if(ch=='�')result="p"+"";
		   if(ch=='�')result="r"+"";
		   if(ch=='�')result="s"+"";
		   if(ch=='�')result="t"+"";
		   if(ch=='�')result="u"+"";
		   if(ch=='�')result="f"+"";
		   if(ch=='�')result="h"+"";
		   if(ch=='�')result="c"+"";
		   if(ch=='�')result="ch"+"";
		   if(ch=='�')result="sh"+"";
		   if(ch=='�')result="sch"+"";
		   if(ch=='�')result=""+"";
		   if(ch=='�')result="y"+"";
		   if(ch=='�')result=""+"";
		   if(ch=='�')result="e"+"";
		   if(ch=='�')result="yu"+"";
		   if(ch=='�')result="ya"+"";
		   if(ch=='�')result="Ga"+"";
		   if(ch=='�')result="E"+"";
		   if(ch=='�')result="E"+"";
		   if(ch=='�')result="YI"+"";
		   if(ch=='�')result="yi"+"";
		   if(ch=='�')result="ga"+"";
		   if(ch=='�')result="Ga"+"";
		   if(ch=='�')result="E"+"";
		   if(ch=='�')result="E"+"";
		   return result;
	   }
	   public static String convert_to_asc(String s){
		   String result="";
		   for(int i=0;i<s.length();i++){
			   result=result+convert(s.charAt(i));
		   }
		   return result;
	   }
   }
   
   public class temp_clasws {
   public static void main(String args[]){
	   
	   System.out.println(utility.convert_to_asc("�������"));
   }
}
