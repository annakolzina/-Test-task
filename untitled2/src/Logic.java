import java.util.Stack;

public class Logic {

    public Stack write = new Stack();
    private static String string;
    private String operands = "";
    private static char[] numbers_array = {'1', '2','3', '4', '5', '6', '7', '8', '9'};
    private int count = 0;

    public Logic() {
    }

    public Logic(String string){
        this.string = string;
    }

    public static String getNewString() {
        return string;
    }

    public static void setNewString(String string) {
        Logic.string = string;
    }

    /*
    * @param проверяет есть ли совпадения с массивом
    * @return true/false*/
    private boolean proverka(char word) throws RuntimeException{
        for(char item: numbers_array){
            if(word == item)
                return true;
        }
        return false;
    }

    public void createWrite(){
        write.push("");
        for(int i = 0; i< string.length(); i++){
            count++;
            //проверка на словo
            if(proverka(string.charAt(i))){
                operands = operands + string.charAt(i);
            }
            else{
                if(write.peek().equals(""))
                    write.push(string.charAt(i));
                else if(string.charAt(i) == '/' || string.charAt(i)=='*'){
                    if(write.peek().equals('*') || write.peek().equals('/'))
                        write.push(string.charAt(i));
                    else if(write.peek().equals('+') || write.peek().equals('-')) {
                        operands = operands + string.charAt(i);
                        operands= operands + write.pop();}

                }
                else if(string.charAt(i) == '+' || string.charAt(i)=='-'){
                    if(write.peek().equals('+') || write.peek().equals('-'))
                        write.push(string.charAt(i));
                    else if(write.peek().equals('*') || write.peek().equals('/')) {
                        operands = operands + write.pop();
                        operands= operands + string.charAt(i);
                    }
                }
                else {
                    if(string.charAt(i)==')'){
                        while(write.peek().equals('(')){
                            operands = operands + write.pop();
                        }
                        if (write.peek().equals('('))
                            write.pop();
                    } else
                        write.push(string.charAt(i));
                }
            }

        }
        while(!write.empty()){
            operands = operands + write.pop();}
    }

    public void SetAll(){
        /*while(!operands.empty()){
            System.out.println(operands.peek());
            operands.pop();
        }*/
        System.out.print(operands);
    }


}
