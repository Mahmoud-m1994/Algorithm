class Stakk {
    private char[] tab;
    private int antall=0;

    public Stakk(int str){
        tab= new char[str];
    }
    public boolean tom(){
        return antall==0;
    }
    public boolean full(){
        return antall==tab.length;
    }

    public void push (char o){
        if(!full()){
            tab[antall++]=o;
        }
    }
    public char pop(){
        if(!tom()) {
            System.out.println("pop  " + tab[antall - 1]);
            return tab[--antall];
        }
        else
            return ' ';
    }
    public char sjekkStakk(){
        if(!tom())
            return tab[antall-1];
        else
            return ' ';
    }
    boolean erOpenParantes(char x){
        if(x=='('||x=='{'||x=='['){
            return true;
        }
        return false;
    }
    boolean erLukkedeParantes(char x){
        if(x==')'||x=='}'||x==']'){
            return true;
        }
        return false;
    }
    public boolean sjekkProgram(char[] input){
        boolean ok=false;
        for (int i=0;i<input.length;i++){
            if(!(erOpenParantes(sjekkStakk())) && erLukkedeParantes(input[i])){
                return false;
            }

            if(erOpenParantes(input[i])){
                push(input[i]);
                System.out.println("push  "+input[i]);
                ok=false;
            }
                char var = input[i];
                switch (var){
                    case ')':
                        //System.out.println("input    "+input[i]);
                        if( sjekkStakk()=='(') {
                            System.out.println("sjekk1  " + sjekkStakk());
                            pop();
                            ok = true;
                        }
                        break;
                    case '}':
                        if(sjekkStakk()=='{') {
                            System.out.println("sjekk2  " + sjekkStakk());
                            pop();
                            ok = true;
                        }
                        break;
                    case ']':
                        if(sjekkStakk()=='[') {
                            System.out.println("sjekk3  " + sjekkStakk());
                            pop();
                            ok = true;
                        }
                        break;

            }
        }
        if(!tom()){
            ok= false;
        }
        return ok;
    }

    public static void main(String[]args){

        String postfix = "hei({}){";
        char[] charArray = postfix.toCharArray();
        Stakk st = new Stakk(charArray.length);

        System.out.println(st.sjekkProgram(charArray));
    }
}

