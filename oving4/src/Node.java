import java.util.Stack;

// Java program for expression tree
class Node {

    char value;
    Node left, right;

    Node(char item) {
        value = item;
        left = right = null;
    }
}
class ExpressionTree {
    void skrivInorder(Node newNode) {
        if (newNode != null) {
            skrivInorder(newNode.left);
            System.out.print(newNode.value + " ");
            skrivInorder(newNode.right);
        }
    }
    boolean erOperator(char c) {
        if (c == '+' || c == '-'
                || c == '*' || c == '/'
                || c == '^') {
            return true;
        }
        return false;
    }
    Node constructTree(char input[]) {
        Stack<Node> st = new Stack();
        Node t, t1, t2;

        for (int i = 0; i < input.length; i++) {

            // Hvis input er operand push til stack
            if (!erOperator(input[i])) {
                t = new Node(input[i]);
                st.push(t);
            } else // operator
            {
                t = new Node(input[i]);
                t1 = st.pop();      // slett top
                t2 = st.pop();

                //  la de være barna
                t.right = t1;
                t.left = t2;
                st.push(t); // push til stakk
            }
        }

        t = st.peek();
        st.pop();

        return t;
    }
    private int konver(char ch)
    {
        return ch - '0';
    }
    public double regnUt(Node ptr)
    {
        if (ptr.left == null && ptr.right == null)
            return konver(ptr.value);
        else
        {
            double resultat = 0.0;
            double venstre = regnUt(ptr.left);
            double høyre = regnUt(ptr.right);
            char operator = ptr.value;

            switch (operator)
            {
                case '+' : resultat = venstre + høyre; break;
                case '-' : resultat = venstre - høyre; break;
                case '*' : resultat = venstre * høyre; break;
                case '/' : resultat = venstre / høyre; break;
                default  : resultat = venstre + høyre; break;
            }
            return resultat;
        }
    }

    public static void main(String args[]) {

        ExpressionTree et = new ExpressionTree();
        String postfix = "111*2+4-91+-";
        char[] charArray = postfix.toCharArray();
        Node root = et.constructTree(charArray);
        System.out.println("infix expression is");
        et.skrivInorder(root);
        System.out.println( "\n"+"Resultatet er "+ et.regnUt( root));
    }
}