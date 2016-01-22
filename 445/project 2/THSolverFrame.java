import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class THSolverFrame
{
	public static void main(String[] args) throws InterruptedException
	{
            int counter = 0;
            while(counter == 0)
            {
                String puzzle = JOptionPane.showInputDialog("Type \"Q\" to do a Queens puzzle or \"T\" for Towers of Hanoi.");
                
                if( puzzle.equals("t") || puzzle.equals("T"))
                {
                    String N = JOptionPane.showInputDialog("Enter a value for N");
                    int num = Integer.parseInt(N);
                    int numberOfDiscs = num;
                    TowerOfHanoi towers = new TowerOfHanoi(numberOfDiscs);
                    THComponent thc = new THComponent(towers);


                    JFrame frame = new JFrame();
                    frame.setTitle("Tower of Hanoi");
                    frame.setSize(500,500);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                    frame.add(thc);

                    frame.setVisible(true);

                    Thread.sleep(5000);

                    solveTower(towers, thc, numberOfDiscs, 0,1,2);

                    System.out.println("DONE!!!");
                    
                    String restart = JOptionPane.showInputDialog("Type in \"Y\" to try a new number ");
                    int R = Integer.parseInt(N);
                    if(R = 1)
                }    
                else if(puzzle.equals("q") || puzzle.equals("Q"))
                {
                    String N = JOptionPane.showInputDialog("Enter a value for N");
                    int num = Integer.parseInt(N);
                    
                    Queens queens = new Queens(num);
                    queens.frame2.setSize(300,300);
                    queens.frame2.setVisible(true);
                    queens.frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    queens.callplaceNqueens();
                    
                    System.out.println("DONE!!!");
                    counter = 1;
                }
                else
                {
                    System.out.println("Invalid input restart Porgram");
                    
                }
            }
	}
	
	public static void solveTower(TowerOfHanoi towers, THComponent thc,int discs,int startPole,int tempPole,int endPole) throws InterruptedException
	{
            if(discs == 1)
            {
                towers.moveTopDisc(startPole, endPole);
                thc.repaint();
                Thread.sleep(100);
            }
            else
            {
                solveTower(towers, thc, discs - 1, startPole, endPole, tempPole);
                towers.moveTopDisc(startPole, endPole);
                thc.repaint();
                Thread.sleep(100);
                solveTower(towers, thc, discs - 1, tempPole, startPole, endPole);
            }
            
		// TO DO
	}
        
        
        public static class Queens 
        {

            int[] x;
            public JFrame frame2;
            public JTextArea text;
            
            public Queens(int N) 
            {
                x = new int[N];
                frame2= new JFrame("QUeens");
                text = new JTextArea("");
            } 
            public boolean canPlaceQueen(int r, int c) 
            {
                /**
                 * Returns TRUE if a queen can be placed in row r and column c.
                 * Otherwise it returns FALSE. x[] is a global array whose first (r-1)
                 * values have been set.
                 */
                for (int i = 0; i < r; i++) {
                    if (x[i] == c || (i - r) == (x[i] - c) ||(i - r) == (c - x[i])) 
                    {
                        return false;
                    }
                }
                return true;
            }

            public void printQueens(int[] x) 
            {
                int N = x.length;
                String temp = "";
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        if (x[i] == j) {
                            temp = temp + "Q ";
                        } else {
                            temp = temp + "* ";
                        }
                    }
                    temp = temp + "\n";
                }
                temp = temp + "\n";
                
                text.setText(temp);
                frame2.getContentPane().add(text);
            }

            public void placeNqueens(int r, int n) 
            {
                /**
                 * Using backtracking this method prints all possible placements of n
                 * queens on an n x n chessboard so that they are non-attacking.
                 */
                for (int c = 0; c < n; c++) {
                    if (canPlaceQueen(r, c)) {
                        x[r] = c;
                        if (r == n - 1) {
                            printQueens(x);
                        } else {
                            placeNqueens(r + 1, n);
                        }
                    }
                }
            }

            public void callplaceNqueens() 
            {
                placeNqueens(0, x.length);
            }
            
        }
        
        
        
     
}
